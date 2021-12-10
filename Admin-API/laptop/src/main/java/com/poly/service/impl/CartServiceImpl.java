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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String,Object> map = new HashMap<>();
        // Chưa đăng nhập
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
            session = request.getSession();
            CartDTO cartDTO= null;
            if(session.getAttribute("myCart") !=null){
                // Lấy ra danh sách giỏ hàng CartItem từ session
                cartDTO = (CartDTO) session.getAttribute("myCart");
            }else {
                cartDTO = new CartDTO();
                session.setAttribute("myCart",cartDTO);
            }

            // Kiểm tra sản phẩm tồn tại trong giỏi hàng hay chưa
            Boolean isExits = false;
            if (cartDTO.getListCartItem().size()>0){
            for (CartItemDTO dto1: cartDTO.getListCartItem()){
                if(dto1.getIdProduct().equals(cartItemDTO.getIdProduct())) {
                    isExits = true;
                    dto1.setQuantityProduct(dto1.getQuantityProduct()+ cartItemDTO.getQuantityProduct());
                    dto1.setTotalPriceCartItem(dto1.getQuantityProduct() * dto1.getPriceSale());
                }
            }
            }

            // Nếu sản phẩm chưa tồn tại trong giỏ hàng
            if(!isExits){
                ProductsVO productsVO = new ProductsVO();
                if(cartItemDTO.getIdProduct() != null){
                    productsVO = this.productService.getOne(cartItemDTO.getIdProduct());
                    cartItemDTO.setIdProduct(productsVO.getId());
                    cartItemDTO.setNameProduct(productsVO.getName());
                    cartItemDTO.setImgUrl(productsVO.getImgUrl());
                }

                if(productsVO.getSaleProduct()!= null){
                    SaleProductVO saleProductVO = this.saleProductService.getSaleProduct(productsVO.getSaleProduct().getSaleCode());
                    Float priceUnit = productsVO.getOutputPrice();
                    Float priceSale = productsVO.getOutputPrice() * (100-productsVO.getSaleProduct().getPromotion())/100;
                    cartItemDTO.setPriceUnit(priceUnit);
                    cartItemDTO.setPriceSale(priceSale);
                }else {
                    cartItemDTO.setPriceUnit(productsVO.getOutputPrice());
                    cartItemDTO.setPriceSale(productsVO.getOutputPrice());
                }

                cartItemDTO.setTotalPriceCartItem(cartItemDTO.getPriceSale()*cartItemDTO.getQuantityProduct());
                cartDTO.getListCartItem().add(cartItemDTO);
            }
            session.setAttribute("myCart",cartDTO);
            map.put("code",200);
            map.put("totalItem",cartDTO.getListCartItem().size());
            System.out.println("KHi chưa đăng nhập:  " + cartItemDTO);

        }else{// KHi đã đăng nhập
            OrdersVO ordersVO = new OrdersVO();
            Boolean checked = false;
            // Nếu trong giỏ hàng có sản phẩm
            if(orderService.findOrdersByAccountCart(auth.getName()) != null){
                ordersVO = orderService.findOrdersByAccountCart(auth.getName());
                for (OrderDetailsVO vo: ordersVO.getOrderDetails()){
                    // Nếu sản phẩm đã tồn tại trong giỏ hàng
                    if(vo.getProduct().getId().equals(cartItemDTO.getIdProduct())){
                        checked = true;
                        detailService.updateQuantityOrderDetail(vo.getQuantity() + cartItemDTO.getQuantityProduct(),vo.getId());
                    }
                }
                // Khi sản phẩm chưa có trong giỏ hàn
                if(!checked){
                    OrderDetailsVO detailsVO = new OrderDetailsVO();
                    detailsVO.setIdOrder(ordersVO.getId());
                    detailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                    ProductsVO productsVO = productService.getOne(cartItemDTO.getIdProduct());
                    detailsVO.setProduct(productsVO);
                    float price = 0;

                    // Nếu sản phẩm có trong sale Product
                    if(productsVO.getSaleProduct()!= null){
                        price += productsVO.getOutputPrice() -( productsVO.getOutputPrice()/100 * productsVO.getSaleProduct().getPromotion() );
                    }else{
                        price += productsVO.getOutputPrice();
                    }
                    detailsVO.setPrice(price);
                    // lƯu vào db
                    detailsVO = detailService.saveOderDetail(detailsVO);

                }
                map.put("code",200);
                map.put("totalItem",orderService.findOrdersByAccountCart(auth.getName()).getOrderDetails().size());

            }else { // nếu người dùng chưa có sản phẩm nào trong giỏ hàng
                AccountVO accountVO = modelMapper.map(accountService.findByEmail(auth.getName()), AccountVO.class);
                ordersVO.setAccount(accountVO);
                ordersVO.setReceived(-2);
                ordersVO = orderService.saveOrders(ordersVO);
                ProductsVO productsVO = productService.getOne(cartItemDTO.getIdProduct());
                OrderDetailsVO detailsVO = new OrderDetailsVO();
                detailsVO.setProduct(productsVO);
                detailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                detailsVO.setIdOrder(ordersVO.getId());
                float price = 0;
                if(productsVO.getSaleProduct() != null){
                    price += productsVO.getOutputPrice() - ( productsVO.getOutputPrice()/100 * productsVO.getSaleProduct().getPromotion());
                }else{
                    price += productsVO.getOutputPrice();
                }
                detailsVO.setPrice(price);
                detailsVO = detailService.saveOderDetail(detailsVO);
                map.put("code",200);
                map.put("totalItem",detailsVO.getQuantity());
            }
            System.out.println("Khi đã đăng nhập: " + ordersVO);
        }
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
                    dto.setQuantity(productService.getOne(dto.getIdProduct()).getQuantity());
                    totalPrice += dto.getQuantityProduct() * dto.getPriceSale();
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
                if(orderService.findOrdersByAccountCart(auth.getName()) != null){
                    ordersVO = orderService.findOrdersByAccountCart(auth.getName());
                }else{
                    AccountVO accountVO = modelMapper.map(accountService.findByEmail(auth.getName()), AccountVO.class);
                    ordersVO.setAccount(accountVO);
                    ordersVO.setReceived(-2);
                    ordersVO = orderService.saveOrders(ordersVO);

                }
                System.out.println(ordersVO);
                // Nếu sản phẩm đã tồn tại trong giỏ hàng
                for (CartItemDTO itemDTO : dto.getListCartItem()){
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