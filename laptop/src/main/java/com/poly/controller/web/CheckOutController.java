package com.poly.controller.web;


import com.poly.DTO.CartItemDTO;
import com.poly.DTO.CheckOut;
import com.poly.DTO.CartDTO;
import com.poly.helper.zalopay.HMACUtil;
import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;
import com.poly.service.ProductService;
import com.poly.vo.OrderDetailsVO;
import com.poly.vo.OrdersVO;
import com.poly.vo.ProductsVO;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CheckOutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping(value = "/checkout")
    public String view(Model model, HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")){
            String name = auth.getName();
            Collection<?> authorities  = auth.getAuthorities();
            HttpSession httpSession = request.getSession();
            CartDTO cartDTO = null;
            if (httpSession.getAttribute("myCart") != null){
                cartDTO = (CartDTO) httpSession.getAttribute("myCart");
                model.addAttribute("myCart",cartDTO);
            }
            model.addAttribute("address",new CheckOut());
            System.out.println("Name: " + name);
            return "user/checkout";
        }else {
            return "redirect:/home";
        }
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkOut(@ModelAttribute CheckOut checkOut, HttpServletRequest request) throws ParseException, Exception{
        if (checkOut.getPayment() == 1){
            //Lưu vào Orders
            OrdersVO ordersVO = new OrdersVO();
            ordersVO.setOrderDate(getDateNowSql());

            //Format address dạng: " Tỉnh/Thành Phố, Quận/Huyện, Xã/Phưỡng, Số nhà, tên đường "
            String addressOrder = checkOut.getCity()+","+checkOut.getDistrict()+","+checkOut.getWard()+","+checkOut.getAddressDetail();
            ordersVO.setAddress(addressOrder);

            //Chưa check trùng code order
            String codeOrder = getRandomCodeOrder();
            ordersVO.setOrderCode(codeOrder);
            ordersVO.setDescription(checkOut.getDescription());
            ordersVO.setPhoneNumber(checkOut.getPhone());
            this.orderService.saveOrders(ordersVO);


            //Lưu vào order detail
            HttpSession httpSession = request.getSession();
            CartDTO cartDTO = null;
            if (httpSession.getAttribute("myCart") != null){
                cartDTO = (CartDTO) httpSession.getAttribute("myCart");
            }
            List<CartItemDTO> cartItemDTOList = cartDTO.getListCartItem();
            Integer idOrder= ordersVO.getId();
            OrderDetailsVO orderDetailsVO = null;
            ProductsVO productsVO = null;
            for (CartItemDTO c:cartItemDTOList) {
                orderDetailsVO = new OrderDetailsVO();
                productsVO = this.productService.getOne(c.getIdProduct());
                orderDetailsVO.setProductsVO(productsVO);
                orderDetailsVO.setOrdersVO(ordersVO);
                orderDetailsVO.setQuantity(c.getQuantityProduct());
                orderDetailsVO.setCompletionDate(getDateNowSql());
                orderDetailsVO.setPrice(c.getTotalPriceCartItem());
                orderDetailsVO.setPaymentMethods(0);
                orderDetailsVO.setReceived(0);
                this.orderDetailService.saveOderDetail(orderDetailsVO);
            }
            return "redirect:/checkout";
        }else if (checkOut.getPayment() == 2){
            JSONObject result = this.createOrder(request,checkOut);

            return "redirect:"+result.get("orderurl");
        }
        return null;
    }


    private JSONObject createOrder(HttpServletRequest request, CheckOut checkOut) throws Exception{
        HttpSession httpSession = request.getSession();
        CartDTO cartDTO = null;
        if (httpSession.getAttribute("myCart") != null){
            cartDTO = (CartDTO) httpSession.getAttribute("myCart");
        }

        final Map embeddata = new HashMap(){{
            put("merchantinfo", "embeddata123");
        }};

        final Map[] item = {
                new HashMap(){{
                    put("itemid", "knb");
                    put("itemname", "kim nguyen bao");
                    put("itemprice", 198400);
                    put("itemquantity", 1);
                }}
        };
        final List<CartItemDTO> itemDTOList = cartDTO.getListCartItem();
        Map<String, Object> order = new HashMap<String, Object>(){{
            put("appid", config.get("appid"));
            put("apptransid", getCurrentTimeString("yyMMdd") +"_"+ UUID.randomUUID()); // mã giao dich có định dạng yyMMdd_xxxx
            put("apptime", System.currentTimeMillis()); // miliseconds
            put("appuser", "demo");
            put("amount", 34500);
            put("description", "ZaloPay Intergration Demo");
            put("bankcode", "");
            put("item", new JSONObject(item).toString());
            put("embeddata", new JSONObject(embeddata).toString());

        }};
        String data = order.get("appid") +"|"+ order.get("apptransid") +"|"+ order.get("appuser") +"|"+ order.get("amount")
                +"|"+ order.get("apptime") +"|"+ order.get("embeddata") +"|"+ order.get("item");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.get("endpoint"));
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        // Content-Type: application/x-www-form-urlencoded
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }
        JSONObject result = new JSONObject(resultJsonStr.toString());

        return result;
    }

    public String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }
    private Map<String, String> config = new HashMap<String, String>(){{
        put("appid", "2554");
        put("key1", "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn");
        put("key2", "trMrHtvjo6myautxDUiAcYsVtaeQ8nhf");
        put("endpoint", "https://sandbox.zalopay.com.vn/v001/tpe/createorder");
    }};


    /**
     * @throws ParseException
     * @return Date now of sql
     */
    private java.sql.Date getDateNowSql() throws ParseException {
        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        java.sql.Date dateSql = new java.sql.Date(calendar.getTime().getTime());
        return dateSql;
    }

    public String getRandomCodeOrder(){
        SecureRandom secureRandom = new SecureRandom();
        Integer randomWithSecureRandomWithinARange = secureRandom.nextInt(99999999 - 1) + 1;
        String num =  "011"+randomWithSecureRandomWithinARange.toString();
        return num;
    }

}
