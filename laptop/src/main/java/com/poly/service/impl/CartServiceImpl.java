package com.poly.service.impl;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import com.poly.entity.ProductsDetail;
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
    private ProductDetailService productDetailService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private SaleProductService saleProductService;

    @Autowired
    private ModelMapper modelMapper;


    public Map<String,Object> addTocart(CartItemDTO cartItemDTO){
        System.out.println("cap nhat");
        Map<String, Object> map = new HashMap<>();
        CartDTO cartDTO = new CartDTO();
        Boolean isExits = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Giá cả sản phẩm sắp thêm

        if(session.getAttribute("myCart") !=null){
            // Lấy ra danh sách giỏ hàng CartItem từ session
            cartDTO = (CartDTO) session.getAttribute("myCart");
        }else {
            cartDTO = new CartDTO();
            session.setAttribute("myCart",cartDTO);
        }
        // Lấy thông tin sản phẩm
        ProductsDetailVO productsDetailVO = productDetailService.findBySku(cartItemDTO.getSku());
        ProductsVO productsVO = productService.getOne(productsDetailVO.getIdProduct());

        float price = 0;
        if(productsDetailVO.getSaleProduct()!=null){
            price = cartItemDTO.getQuantityProduct() * (productsDetailVO.getSaleProduct().getPromotion() / 100 * productsDetailVO.getPrice());
        }else{
            price = cartItemDTO.getQuantityProduct() * productsDetailVO.getPrice();
        }
        System.out.println("Price: " + price);
        // Duyệt For tìm kiếm sản phẩm trong giỏ hàng
        for(CartItemDTO cartItemDTO1: cartDTO.getListCartItem()){
            // Nếu sản phẩm đã có
            if(cartItemDTO.getSku().equalsIgnoreCase(cartItemDTO1.getSku())){
                isExits = true;
                if(cartItemDTO1.getQuantityProduct() + cartItemDTO.getQuantityProduct() > productsDetailVO.getQuantity()){
                    System.out.println("Số lượng vượt quá giới hạn");
                    map.put("message","Số lượng vượt quá giới hạn");
                }else if(cartDTO.getTotalPrice() + price > 150000000){
                    System.out.println("Tổng tiền vượt quá giới hạn: " + (cartDTO.getTotalPrice() + price));
                    map.put("message","Tổng tiền vượt quá giới hạn");
                }else{
                    System.out.println("Sản phẩm trùng ngon");
                    // Số lượng sản phẩm
                    Integer quan = cartItemDTO1.getQuantityProduct() + cartItemDTO.getQuantityProduct();
                    // Cập nhật số lượng sản phẩm trong giỏ hàng
                    if(auth == null || auth.getPrincipal().equals("anonymousUser")){
                        cartItemDTO1.setQuantityProduct(quan);
                    }else{
                        System.out.println("Đã đăng nhập + đã có sản phẩm + update");
                        orderDetailService.updateQuantityOrderDetail(quan, cartItemDTO1.getIdOrderDetail());
                        cartItemDTO1.setQuantityProduct(cartItemDTO1.getQuantityProduct()+cartItemDTO.getQuantityProduct());
                    }
                    // Tổng toàn bộ tiền
                    cartDTO.setTotalPrice((float) (Math.round(cartDTO.getTotalPrice()) + Math.round(price)));
                    // Tiền sản phẩm
                    cartItemDTO1.setTotalPriceCartItem(cartItemDTO1.getQuantityProduct() * cartItemDTO1.getPriceSale());
                    // Hiển thị trên giao diện
                    map.put("totalPrice",cartItemDTO1.getTotalPriceCartItem());
                    map.put("sumPrice",cartDTO.getTotalPrice());
                    map.put("message","Cập nhật thành công");

                }
            }
        }

        if (!isExits){
            // Khi chưa đăng nhâp
            if(auth == null || auth.getPrincipal().equals("anonymousUser")){

                cartItemDTO.setNameProduct(productService.getOne(productsDetailVO.getIdProduct()).getName());
                cartItemDTO.setNameCate(productService.getOne(productsDetailVO.getIdProduct()).getCategory().getName());
                cartItemDTO.setImgUrl(productsDetailVO.getImgUrl());
                cartItemDTO.setPriceUnit(productsDetailVO.getPrice());
                cartItemDTO.setQuantity(productsDetailVO.getQuantity());
                price = 0;
                if(productsDetailVO.getSaleProduct() != null){
                    price += productsDetailVO.getPrice() - ( productsDetailVO.getPrice()/100 * productsDetailVO.getSaleProduct().getPromotion());
                }else{
                    price += productsDetailVO.getPrice();
                }
                cartItemDTO.setPriceSale(price);
                cartItemDTO.setTotalPriceCartItem(cartItemDTO.getQuantityProduct() * cartItemDTO.getPriceSale());
                cartDTO.getListCartItem().add(cartItemDTO);
                map.put("totalPrice",cartItemDTO.getTotalPriceCartItem());
                // Set lại tổng tiền sau khi thêm vào giỏ hàng
                cartDTO.setTotalPrice(cartDTO.getTotalPrice() + cartItemDTO.getTotalPriceCartItem());
                map.put("sumPrice",cartDTO.getTotalPrice());
            }else{
                // Khi đã đăng nhập
                OrdersVO ordersVO = new OrdersVO();
                OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
                if(orderService.findOrdersByAccountCart(auth.getName()) ==null){
                    AccountVO  accountVO = modelMapper.map(accountService.findByEmailUser(auth.getName()),AccountVO.class);
                    ordersVO.setAccount(accountVO);
                    ordersVO.setReceived(-2);
                    ordersVO = orderService.saveOrders(ordersVO);
                }else{
                    ordersVO = orderService.findOrdersByAccountCart(auth.getName());
                }
// Thêm vào db
                // Order
                orderDetailsVO.setIdOrder(ordersVO.getId());
                // số lượng
                orderDetailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                orderDetailsVO.setProductsDetailVO(productsDetailVO);
                orderDetailsVO.setStatus(-2);
                orderDetailsVO = orderDetailService.saveOderDetail(orderDetailsVO);
                System.out.println("Cap nhat db thanh cong");


            }
            map.put("message","Thêm thành công");

        }

        map.put("totalItem",cartDTO.getListCartItem().size());
        map.put("code",200);
        return map;
    }


    @Override
    public CartDTO findCart() {
        System.out.println("findCart");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        float totalPrice = 0;
//        HttpSession session = request.getSession();
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
            CartDTO cartDTO = (CartDTO) session.getAttribute("myCart");

            if(cartDTO !=null){
                for (CartItemDTO dto: cartDTO.getListCartItem()){

                    totalPrice += Math.round(dto.getQuantityProduct()) * Math.round(dto.getPriceSale());
                    System.out.println("T : " + totalPrice);
                }
                cartDTO.setTotalPrice(totalPrice);
            }
            return cartDTO;
        }else{
            List<CartItemDTO> list = new ArrayList<>();
            OrdersVO ordersVO = orderService.findOrdersByAccountCart(auth.getName());

            if (ordersVO != null){

                CartDTO cartDTO = new CartDTO();
                ordersVO.getOrderDetails().forEach(vo -> {
                    ProductsDetailVO productsDetailVO = productDetailService.findBySku(vo.getProductsDetailVO().getSku());
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setSku(vo.getProductsDetailVO().getSku());
                    cartItemDTO.setNameProduct(productService.getOne(productsDetailVO.getIdProduct()).getName());
                    cartItemDTO.setNameCate(productService.getOne(productsDetailVO.getIdProduct()).getCategory().getName());
                    cartItemDTO.setImgUrl(productsDetailVO.getImgUrl());
                    cartItemDTO.setQuantityProduct(vo.getQuantity());
                    cartItemDTO.setPriceUnit(productsDetailVO.getPrice());
                    cartItemDTO.setIdOrderDetail(vo.getId());
                    cartItemDTO.setQuantity(productsDetailVO.getQuantity());
                    float price = 0;
                    if(productsDetailVO.getSaleProduct() != null){
                        price += productsDetailVO.getPrice() - ( productsDetailVO.getPrice()/100 * productsDetailVO.getSaleProduct().getPromotion());
                    }else{
                        price += productsDetailVO.getPrice();
                    }
                    cartItemDTO.setPriceSale(price);
                    System.out.println("price: " + price);
                    cartItemDTO.setTotalPriceCartItem(cartItemDTO.getQuantityProduct() * cartItemDTO.getPriceSale());
                    list.add(cartItemDTO);
                });
                float totalPriceOrder = 0;
                float totalPriceUnitOrder = 0;
                // Hiếu tạo
                for (CartItemDTO dto:list) {
                    totalPriceOrder+=dto.getTotalPriceCartItem();
                    totalPriceUnitOrder+=dto.getPriceUnit();
                    // Hiếu
                    totalPrice += Math.round(dto.getQuantityProduct()) * Math.round(dto.getPriceSale());
                }
                cartDTO.setTotalPrice(totalPrice);
                cartDTO.setTotalPriceCart(totalPriceOrder);
                cartDTO.setTotalPriceUnit(totalPriceUnitOrder);
                cartDTO.setIdOrder(ordersVO.getId());
                cartDTO.setListCartItem(list);
                session.setAttribute("myCart",cartDTO);
                return cartDTO;
            }
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
                System.out.println("Email: " + auth.getName());
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
                    if(ordersVO.getOrderDetails()!=null){
                        for (OrderDetailsVO vo: ordersVO.getOrderDetails()){
                            // Nếu sản phẩm đã có trong database
                            if(vo.getProductsDetailVO().getSku().equals(itemDTO.getSku())){
                                checked = true;
                                orderDetailService.updateQuantityOrderDetail(vo.getQuantity() + itemDTO.getQuantityProduct(),vo.getId());
                            }
                        }
                    }

                    if(!checked){
                        OrderDetailsVO detailsVO = new OrderDetailsVO();
                        detailsVO.setQuantity(itemDTO.getQuantityProduct());
                        detailsVO.setIdOrder(ordersVO.getId());
                        detailsVO.setProductsDetailVO(productDetailService.findBySku(itemDTO.getSku()));
                        detailsVO = orderDetailService.saveOderDetail(detailsVO);
                    }
                }
            }
        }

    }

}