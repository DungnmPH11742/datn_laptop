package com.poly.controller.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.poly.DTO.CartItemDTO;
import com.poly.DTO.CheckOut;
import com.poly.DTO.CartDTO;
import com.poly.DTO.Countries;
import com.poly.helper.HeaderHelper;
import com.poly.helper.zalopay.HMACUtil;
import com.poly.service.*;
import com.poly.vo.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@Controller
public class CheckOutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    @Autowired
    private HeaderHelper headerHelper;
    @Autowired
    private DeliveryAddressService addressService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CountriesService countriesService;
    @Autowired
    private VouchersService vouchersService;
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private CartService cartService;

    private static String apptranid = "";

    @RequestMapping(value = "/checkout")
    public String view(Model model, HttpServletRequest request) throws JsonProcessingException {
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            if (request.getParameter("error") != null) {
                model.addAttribute("errorAddress", "Bạn chưa chọn địa chỉ");
            }
            String name = auth.getName();

            HttpSession httpSession = request.getSession();
            CartDTO cartDTO = cartService.findCart();
            if (httpSession.getAttribute("myCart") == null) {
                httpSession.setAttribute("myCart", cartDTO);
            }
            model.addAttribute("address", new CheckOut());
            String url = "https://provinces.open-api.vn/api/?depth=1";
            List<HashMap<String, ?>> countries = this.countriesService.getListCountries(url);
            model.addAttribute("countries", countries);
            model.addAttribute("formAddress", new DeliveryAddressVO());

            //Lấy list địa chỉ của user
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            List<DeliveryAddressVO> addressVOList = this.addressService.getListAddressByUser(accountVO.getId());
            DeliveryAddressVO voDe = null;
            for (DeliveryAddressVO vo : addressVOList) {
                if (vo.getSetAsDefault()) {
                    voDe = vo;
                }
            }
            model.addAttribute("addressDefault", voDe);
            model.addAttribute("listAddress", addressVOList);
            return "user/checkout";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/send-order")
    public String sendOrder(HttpServletRequest request, Model model) throws Exception {
        HttpSession httpSession = request.getSession();
        String apptransid = (String) httpSession.getAttribute("apptransid");
        JSONObject getStatusOrder = this.getStatusOrder(apptranid);
        String returnCode = getStatusOrder.get("returncode").toString();
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            if (Integer.parseInt(returnCode) == 1 && request.getParameter("code") == null) {
                CartDTO cartDTO = this.checkoutService.getListCartFromSession(request);
                if (cartDTO != null) {
                    CheckOut checkOut = (CheckOut) httpSession.getAttribute("addressUser");
                    DeliveryAddressVO deliveryAddressVO = this.addressService.getAddressByIdAndAccount(checkOut.getIdAddress(), accountVO.getId());
                    if (deliveryAddressVO == null) {
                        return "redirect:/error-page";
                    }
                    OrdersVO ordersVO = this.checkoutService.addOrderVo(deliveryAddressVO, true, checkOut, cartDTO.getIdOrder(), this.checkoutService.getDateNowSql());
                    ordersVO.setOrderCode(checkOut.getCode());
                    ordersVO.setPaymentMethods(1);
                    OrdersVO voOrder = this.orderService.updateOrders(ordersVO);
                    //Lưu vào order detail
                    List<CartItemDTO> cartItemDTOList = cartDTO.getListCartItem();
                    OrderDetailsVO orderDetailsVO = null;
                    ProductsVO productsVO = null;
                    for (CartItemDTO c : cartItemDTOList) {
                        orderDetailsVO = new OrderDetailsVO();
                        productsVO = this.productService.getOne(c.getIdProduct());
                        /*productsVO.setQuantity(productsVO.getQuantity() - c.getQuantityProduct());
                        if (productsVO.getQuantity() == 0) {
                            productsVO.setActive(false);
                        }*/
//                        this.productService.update(productsVO);
                        orderDetailsVO.setId(c.getIdOrderDetail());
                        orderDetailsVO.setProduct(productsVO);
                        orderDetailsVO.setIdOrder(ordersVO.getId());
                        orderDetailsVO.setQuantity(c.getQuantityProduct());
                        orderDetailsVO.setPrice(c.getTotalPriceCartItem());
                        orderDetailsVO.setStatus(0);
                        this.orderDetailService.saveOderDetail(orderDetailsVO);
                    }
                    checkoutService.sendEmailWhenSeccessOrder(accountVO,cartDTO,deliveryAddressVO,ordersVO.getDescription());
                    model.addAttribute("totalPriceOrder", cartDTO.getTotalPriceCart());
                    model.addAttribute("order", voOrder);
                    model.addAttribute("nameUser", deliveryAddressVO.getName());
                    model.addAttribute("message", "Thanh toán thành công");
                }
            } else if (request.getParameter("code") != null) {
                String code = request.getParameter("code");
                OrdersVO vo = this.orderService.getByOrderCode(code);
                List<OrderDetailsVO> detailsVO = this.orderDetailService.getOrderDetailByIdOrder(vo.getId());
                Float priceTotaleCart = new Float(0);
                if (detailsVO.isEmpty() || detailsVO != null) {
                    for (OrderDetailsVO deVo : detailsVO) {
                        priceTotaleCart += deVo.getPrice();
                    }
                }
                Integer nameInt = vo.getAddress().lastIndexOf('-');
                String name = vo.getAddress().substring(0, nameInt);
                model.addAttribute("order", vo);
                model.addAttribute("nameUser", name.trim());
                model.addAttribute("totalPriceOrder", priceTotaleCart);
                model.addAttribute("message", "Thanh toán thành công");
            } else {
                //Nếu return code = -217 thì do thẻ bị đánh cắp hoặc mất thẻ
                //Nếu return code = -319 do settimeout
                //Nếu return code = -63 do thẻ hết tiền
                model.addAttribute("messageError", "Lỗi thanh toán");
            }
            return "user/send-order";
        } else {
            return "redirect:/login";
        }


    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkOut(@ModelAttribute CheckOut checkOut, HttpServletRequest request) throws ParseException, Exception {
        Boolean checked = false;
        if (request.getParameter("idAddress").equals("") || request.getParameter("idAddress") == null) {
            return "redirect:/checkout?error=" + true;
        }
        checkOut.setIdAddress(Integer.parseInt(request.getParameter("idAddress")));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            if (checkOut.getPayment() == 1) {
                //Lưu vào Orders
                CartDTO cartDTO = this.checkoutService.getListCartFromSession(request);
                HttpSession httpSession = request.getSession();
                List<CartItemDTO> cartItemDTOList = cartDTO.getListCartItem();

                DeliveryAddressVO deliveryAddressVO = this.addressService.getAddressByIdAndAccount(checkOut.getIdAddress(), accountVO.getId());
                if (deliveryAddressVO == null) {
                    return "redirect:/error-page";
                }
                OrdersVO ordersVO = this.checkoutService.addOrderVo(deliveryAddressVO, false, checkOut, cartDTO.getIdOrder(), this.checkoutService.getDateNowSql());
                ordersVO.setPaymentMethods(0);
                this.orderService.updateOrders(ordersVO);
                //Lưu vào order detail
                OrderDetailsVO orderDetailsVO = null;
                ProductsVO productsVO = null;
                for (CartItemDTO c : cartItemDTOList) {
                    orderDetailsVO = new OrderDetailsVO();
                    productsVO = this.productService.getProductById(c.getIdProduct());
                    /*productsVO.setQuantity(productsVO.getQuantity() - c.getQuantityProduct());
                    if (productsVO.getQuantity() == 0) {
                        productsVO.setActive(false);
                    }*/
//                    this.productService.update(productsVO);
                    orderDetailsVO.setId(c.getIdOrderDetail());
                    orderDetailsVO.setProduct(productsVO);
                    orderDetailsVO.setIdOrder(ordersVO.getId());
                    orderDetailsVO.setQuantity(c.getQuantityProduct());
                    orderDetailsVO.setPrice(c.getTotalPriceCartItem());
                    orderDetailsVO.setStatus(0);
                    this.orderDetailService.updateOrderDetail(orderDetailsVO);
                }
                httpSession.removeAttribute("myCart");
                checkoutService.sendEmailWhenSeccessOrder(accountVO,cartDTO,deliveryAddressVO,ordersVO.getDescription());
                return "redirect:/send-order?code=" + ordersVO.getOrderCode();
            } else if (checkOut.getPayment() == 2) {
                HttpSession httpSession = request.getSession();
                CartDTO cartDTO = null;
                if (httpSession.getAttribute("myCart") != null) {
                    cartDTO = (CartDTO) httpSession.getAttribute("myCart");
                }
                if (httpSession.getAttribute("addressUser") != null) {
                    httpSession.removeAttribute("addressUser");
                }
                String randomCode = this.checkoutService.getRandomCodeOrder();
                if (this.orderService.getByOrderCode(randomCode) != null) {
                    randomCode = this.checkoutService.getRandomCodeOrder();
                }
                checkOut.setCode(randomCode);
                httpSession.setAttribute("addressUser", checkOut);
                JSONObject result = this.createOrder(request, checkOut, cartDTO, randomCode);
                String returnCode = result.get("returncode").toString();
                if (Integer.parseInt(returnCode) == 1) {
                    System.out.println("Create order ZaloPay success");
                }
                return "redirect:" + result.get("orderurl");
            }
        } else {
            return "redirect:/home";
        }
        return "redirect:/checkout";
    }


    //Thêm mới địa chỉ và sửa địa chỉ
    @PostMapping(value = "/add-address")
    public String addNewAddress(@ModelAttribute DeliveryAddressVO addressVO, HttpServletRequest request) {
        String city = request.getParameter("cityname");
        String distic = request.getParameter("districtname");
        String ward = request.getParameter("wardsname");
        String addressDetail = request.getParameter("addressDetail").trim();
        String address = addressDetail + ", " + ward + ", " + distic + ", " + city;
        addressVO.setAddress(address);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(name);
            if (accountVO != null) {
                addressVO.setIdAccount(accountVO.getId());
                if (addressVO.getId() == null) {
                    this.addressService.save(addressVO);
                } else {
                    this.addressService.update(addressVO);
                }
            }
        }
        return "redirect:/checkout";
    }


    @PostMapping(value = "/ajax/voucher")
    public ResponseEntity<Map<String, Object>> getVoucher(HttpServletRequest request) {
        String voucher = request.getParameter("voucher");
        CartDTO cartDTO = this.checkoutService.getListCartFromSession(request);
        List<CartItemDTO> cartItemSession = cartDTO.getListCartItem();
        VouchersVO vouchersVO = null;
        for (CartItemDTO vo : cartItemSession) {
            vouchersVO = this.vouchersService.getVoucherTrue(voucher, vo.getIdProduct());
        }
        if (vouchersVO != null) {
            System.out.println(vouchersVO.getIdProduct());
            System.out.println(vouchersVO.getId());
            System.out.println(vouchersVO.getEndDate());
        }
        Map<String, Object> map = new HashMap<>();
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "/ajax/getAddress")
    public ResponseEntity<Map<String, Object>> getAddressById(HttpServletRequest request) throws JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            String idAddress = request.getParameter("idAddress");
            DeliveryAddressVO vo = this.addressService.getAddressByIdAndAccount(Integer.parseInt(idAddress), accountVO.getId());

            Countries countries = this.countriesService.getCountry(vo.getAddress());
            String url = "https://provinces.open-api.vn/api/?depth=1";
            List<HashMap<String, ?>> cityList = this.countriesService.getListCountries(url);
            String urlDistrict = "https://provinces.open-api.vn/api/?depth=2";
            List<HashMap<String, ?>> mapListDistric = this.countriesService.getDistric(countries.getCity(), urlDistrict);
            String urlWard = "https://provinces.open-api.vn/api/?depth=3";
            List<HashMap<String, ?>> mapListWards = this.countriesService.getWards(countries.getDistrict(), urlWard, countries.getCity());
            Map<String, Object> getAddress = new HashMap<>();
            if (vo != null) {
                getAddress.put("address", vo);
                getAddress.put("countries", cityList);
                getAddress.put("country", countries);
                getAddress.put("listDistrict", mapListDistric);
                getAddress.put("listWard", mapListWards);
            }
            return ResponseEntity.ok(getAddress);
        }

        return null;
    }


    private JSONObject createOrder(HttpServletRequest request, CheckOut checkOut, CartDTO cartDTO, String random) throws Exception {
        final Map embeddata = new HashMap() {{
            put("merchantinfo", "embeddata123");
        }};
        final Map[] item = {
                new HashMap() {{
                    put("itemid", "knb");
                    put("itemname", "kim nguyen bao");
                    put("itemprice", 198400);
                    put("itemquantity", 1);
                }}
        };
        final String codeOrder = random;
        final List<CartItemDTO> itemDTOList = cartDTO.getListCartItem();
        Float totalPrice = cartDTO.getTotalPriceCart();
        final Integer price = Integer.parseInt(String.format("%.0f", totalPrice));
        Map<String, Object> order = new HashMap<String, Object>() {{
            put("appid", config.get("appid"));
            put("apptransid", getCurrentTimeString("yyMMdd") + "_" + codeOrder); // mã giao dich có định dạng yyMMdd_xxxx
            put("apptime", System.currentTimeMillis()); // miliseconds
            put("appuser", "demo");
            put("amount", price);
            put("description", "ZaloPay Intergration Demo");
            put("bankcode", "");
            put("item", new JSONObject(item).toString());
            put("embeddata", new JSONObject(embeddata).toString());
        }};
        String data = order.get("appid") + "|" + order.get("apptransid") + "|" + order.get("appuser") + "|" + order.get("amount")
                + "|" + order.get("apptime") + "|" + order.get("embeddata") + "|" + order.get("item");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.get("endpoint"));
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }
       /* HttpSession session = request.getSession();
        if (session.getAttribute("apptransid") != null){
            session.removeAttribute("apptransid");
        }
        session.setAttribute("apptransid",order.get("apptransid").toString());*/
        apptranid = order.get("apptransid").toString();
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
        result.append("appId", order.get("apptransid").toString());
        return result;
    }

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private JSONObject getStatusOrder(String apptransid) throws Exception {
        String data = config.get("appid") + "|" + apptransid + "|" + config.get("key1"); // appid|apptransid|key1
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", config.get("appid")));
        params.add(new BasicNameValuePair("apptransid", apptransid));
        params.add(new BasicNameValuePair("mac", mac));

        URIBuilder uri = new URIBuilder(config.get("endpointStatus"));
        uri.addParameters(params);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri.build());

        CloseableHttpResponse res = client.execute(get);
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

    private Map<String, String> config = new HashMap<String, String>() {{
        put("appid", "2554");
        put("key1", "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn");
        put("key2", "trMrHtvjo6myautxDUiAcYsVtaeQ8nhf");
        put("endpoint", "https://sandbox.zalopay.com.vn/v001/tpe/createorder");
        put("endpointStatus", "https://sandbox.zalopay.com.vn/v001/tpe/getstatusbyapptransid");
    }};

    public Integer getTotalItem(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        CartDTO cartDTO = null;
        if (httpSession.getAttribute("myCart") != null) {
            cartDTO = (CartDTO) httpSession.getAttribute("myCart");
            return cartDTO.getListCartItem().size();
        }
        return 0;
    }

}