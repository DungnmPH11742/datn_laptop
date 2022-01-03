package com.poly.service.impl;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import com.poly.service.*;
import com.poly.vo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private SaleProductService saleProductService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Map<String, Object> addTocart(CartItemDTO cartItemDTO) {
        float sumPrice = 0;
        Boolean isExits = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String,Object> map = new HashMap<>();
        CartDTO cartDTO= null;
        if(session.getAttribute("myCart") !=null){
            // Lấy ra danh sách giỏ hàng CartItem từ session
            cartDTO = (CartDTO) session.getAttribute("myCart");
        }else {
            cartDTO = new CartDTO();
            session.setAttribute("myCart",cartDTO);
        }


        ProductsVO productsVO = productService.getOne(cartItemDTO.getIdProduct());
        for(CartItemDTO cartItemDTO1: cartDTO.getListCartItem()){
            // Nếu đã tồn tại sản phẩm trong giỏ hàng
            if(cartItemDTO1.getIdProduct().equals(cartItemDTO.getIdProduct())){
                //
                isExits = true;
                System.out.println("Sản phẩm trùng");
//                System.out.println((cartDTO.getTotalPrice()+(cartItemDTO.getQuantityProduct() + cartItemDTO1.getQuantityProduct())*cartItemDTO1.getPriceSale()));
                System.out.println(cartItemDTO.getQuantityProduct());
                System.out.println(cartItemDTO1.getQuantityProduct());
                System.out.println(cartItemDTO1.getPriceSale());
                System.out.println(cartDTO.getTotalPrice());
                System.out.println(cartDTO);
                /*if ((cartItemDTO.getQuantityProduct()+cartItemDTO1.getQuantityProduct())> productsVO.getQuantity()){
                    System.out.println("Số lượng vượt quá giới hạn");
                    map.put("message","Số lượng vượt quá giới hạn");
                }else if(cartDTO.getTotalPrice()+(cartItemDTO.getQuantityProduct()*cartItemDTO1.getPriceSale())> 150000000){

                    System.out.println("Tổng tiền vượt quá giới hạn: " + (cartDTO.getTotalPrice()+(cartItemDTO.getQuantityProduct() + cartItemDTO1.getQuantityProduct())*cartItemDTO1.getPriceSale()));
                    map.put("message","Tổng tiền vượt quá giới hạn");
                }else{

                    System.out.println("Sản phẩm trùng ngon");
                    Integer quan = cartItemDTO1.getQuantityProduct() + cartItemDTO.getQuantityProduct();
                    cartItemDTO1.setQuantityProduct(quan);
                    // Nếu đã đăng nhập
                    if(auth == null || auth.getPrincipal().equals("anonymousUser")){

                    }else{
                        System.out.println("Đã đăng nhập + đã có sản phẩm + update");
                        detailService.updateQuantityOrderDetail(quan, cartItemDTO1.getIdOrderDetail());
                    }
                    cartItemDTO1.setTotalPriceCartItem(cartItemDTO1.getQuantityProduct() * cartItemDTO1.getPriceSale());

                    map.put("message","Cập nhật thành công");

                }*/
                map.put("totalPrice",cartItemDTO1.getTotalPriceCartItem());

            }
            sumPrice += cartItemDTO1.getPriceSale() * cartItemDTO1.getQuantityProduct();
        }
        map.put("sumPrice",sumPrice);
        cartDTO.setTotalPrice(sumPrice);
        // Nếu sản phẩm chưa có trong giỏ hàng
        /*if(!isExits){
            System.out.println("Sản phẩm chưa có");
            cartItemDTO.setIdProduct(productsVO.getId());
            cartItemDTO.setNameProduct(productsVO.getName());
            cartItemDTO.setImgUrl(productsVO.getImgUrl());
            // Nếu có sale
            Float priceUnit = productsVO.getOutputPrice();
            cartItemDTO.setPriceUnit(priceUnit);
            if(productsVO.getSaleProduct()!= null){
                SaleProductVO saleProductVO = this.saleProductService.getSaleProduct(productsVO.getSaleProduct().getSaleCode());
                Float priceSale = productsVO.getOutputPrice() * (100-productsVO.getSaleProduct().getPromotion())/100;
                cartItemDTO.setPriceSale(priceSale);
            }else {
                cartItemDTO.setPriceSale(productsVO.getOutputPrice());
            }

            cartItemDTO.setTotalPriceCartItem(cartItemDTO.getPriceSale()*cartItemDTO.getQuantityProduct());
            cartDTO.getListCartItem().add(cartItemDTO);
            cartDTO.setTotalPrice(cartItemDTO.getPriceSale()*cartItemDTO.getQuantityProduct());
            if(auth == null || auth.getPrincipal().equals("anonymousUser")) {
            }else{
                //
                System.out.println("Sản phẩm chưa có khi đã đăng nhập");
                OrdersVO ordersVO = new OrdersVO();
                if(orderService.findOrdersByAccountCart(auth.getName()) != null){
                    ordersVO = orderService.findOrdersByAccountCart(auth.getName());
                    System.out.println("Chưa tồn tại order");
                }else{
                    AccountVO  accountVO = modelMapper.map(accountService.findByEmailUser(auth.getName()),AccountVO.class);
                    ordersVO.setAccount(accountVO);
                    ordersVO.setReceived(-2);
                    ordersVO = orderService.saveOrders(ordersVO);
                    System.out.println("Lấy order Vừa tạo:  ");
                }
                OrderDetailsVO detailsVO = new OrderDetailsVO();
                detailsVO.setIdOrder(ordersVO.getId());
                detailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                detailsVO.setProduct(productsVO);

                float price = 0;
                if(productsVO.getSaleProduct()!= null){
                    price += productsVO.getOutputPrice() -( productsVO.getOutputPrice()/100 * productsVO.getSaleProduct().getPromotion() );
                }else{
                    price += productsVO.getOutputPrice();
                }
                detailsVO.setPrice(price);
            }
            map.put("message","Cập nhật thành công");
        }*/

        map.put("code",200);
        session.setAttribute("myCart",cartDTO);
        map.put("totalItem",cartDTO.getListCartItem().size());
        return map;
    }

    @Override
    public CartDTO findCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
            CartDTO cartDTO = (CartDTO) session.getAttribute("myCart");
            float totalPrice = 0;
            if(cartDTO !=null){
                for (CartItemDTO dto: cartDTO.getListCartItem()){
//                    dto.setQuantity(productService.getOne(dto.getIdProduct()).getQuantity());
                    totalPrice += dto.getQuantityProduct() * dto.getPriceSale();
                }
                cartDTO.setTotalPrice(totalPrice);
            }
            return cartDTO;
        }else{
            List<CartItemDTO> list = new ArrayList<>();
            OrdersVO ordersVO = orderService.findOrdersByAccountCart(auth.getName());

            /*if (ordersVO != null){

                CartDTO cartDTO = new CartDTO();
                ordersVO.getOrderDetails().forEach(vo -> {
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setIdProduct(vo.getProduct().getId());
                    cartItemDTO.setNameProduct(vo.getProduct().getName());
                    cartItemDTO.setNameCate(vo.getProduct().getCategory().getName());
                    cartItemDTO.setImgUrl(vo.getProduct().getImgUrl());
                    cartItemDTO.setQuantityProduct(vo.getQuantity());
                    cartItemDTO.setPriceUnit(vo.getProduct().getOutputPrice());
                    cartItemDTO.setIdOrderDetail(vo.getId());
                    cartItemDTO.setQuantity(vo.getProduct().getQuantity());
                    float price = 0;
                    if(vo.getProduct().getSaleProduct() != null){
                        price += vo.getProduct().getOutputPrice() - ( vo.getProduct().getOutputPrice()/100 * vo.getProduct().getSaleProduct().getPromotion());
                    }else{
                        price += vo.getProduct().getOutputPrice();
                    }
                    cartItemDTO.setPriceSale(price);
                    cartItemDTO.setTotalPriceCartItem(cartItemDTO.getQuantityProduct() * cartItemDTO.getPriceSale());
                    list.add(cartItemDTO);
                });
                float totalPriceOrder = 0;
                float totalPriceUnitOrder = 0;
                // Hiếu tạo
                float totalPrice = 0;
                for (CartItemDTO dto:list) {
                    totalPriceOrder+=dto.getTotalPriceCartItem();
                    totalPriceUnitOrder+=dto.getPriceUnit();
                    // Hiếu
                    totalPrice += dto.getQuantityProduct() * dto.getPriceSale();
                }
                cartDTO.setTotalPrice(totalPrice);
                cartDTO.setTotalPriceCart(totalPriceOrder);
                cartDTO.setTotalPriceUnit(totalPriceUnitOrder);
                cartDTO.setIdOrder(ordersVO.getId());
                cartDTO.setListCartItem(list);
                session.setAttribute("myCart",cartDTO);
                return cartDTO;
            }*/
        }
        return null;
    }

    @Override
    public void addCartFromSession() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Chưa đăng nhập
        if(auth != null || !auth.getPrincipal().equals("anonymousUser")){

            if(session.getAttribute("myCart") != null){

                CartDTO dto = (CartDTO) session.getAttribute("myCart");
                OrdersVO ordersVO = new OrdersVO();
                // Nếu User đăng nhập đã có dữ liệu trong giỏ hàng
                if(orderService.findOrdersByAccountCart(auth.getName()) != null){
                    ordersVO = orderService.findOrdersByAccountCart(auth.getName());
                }else{
                    AccountVO  accountVO = modelMapper.map(accountService.findByEmailUser(auth.getName()),AccountVO.class);
                    ordersVO.setAccount(accountVO);
                    ordersVO.setReceived(-2);
                    ordersVO = orderService.saveOrders(ordersVO);

                }
                System.out.println(ordersVO);
                // Nếu sản phẩm đã tồn tại trong giỏ hàng
                for (CartItemDTO  itemDTO : dto.getListCartItem()){
                    Boolean checked = false;
                    for (OrderDetailsVO vo: ordersVO.getOrderDetails()){
                        // Nếu sản phẩm đã có trong database
                        if(vo.getProduct().getId().equals(itemDTO.getIdProduct())){
                            checked = true;
                            detailService.updateQuantityOrderDetail(vo.getQuantity() + itemDTO.getQuantityProduct(),vo.getId());
                        }
                    }
                    if(!checked){
                        OrderDetailsVO detailsVO = new OrderDetailsVO();
                        detailsVO.setQuantity(itemDTO.getQuantityProduct());
                        detailsVO.setIdOrder(ordersVO.getId());
                        detailsVO.setPrice(itemDTO.getPriceSale());
                        detailsVO.setProduct(productService.getOne(itemDTO.getIdProduct()));
                        detailsVO = detailService.saveOderDetail(detailsVO);
                    }
                }
            }
        }

    }

}