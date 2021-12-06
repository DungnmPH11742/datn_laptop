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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

<<<<<<< HEAD
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService detailService;
=======
    @Autowired private OrderService orderService;

    @Autowired private OrderDetailService detailService;
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06

    @Autowired
    private SaleProductService saleProductService;
    @Autowired
    private ModelMapper modelMapper;
<<<<<<< HEAD
=======

>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
    @Override
    public Map<String, Object> addTocart(CartItemDTO cartItemDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String,Object> map = new HashMap<>();
        // Chưa đăng nhập
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
<<<<<<< HEAD
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
=======

            session = request.getSession();
            List<CartItemDTO> cartItemDTOS = new ArrayList<>();
            if(session.getAttribute("myCart") !=null){
                // Lấy ra danh sách giỏ hàng CartItem từ session
                cartItemDTOS = (List<CartItemDTO>) session.getAttribute("myCart");
            }else {
                session.setAttribute("myCart",cartItemDTOS);
            }


            // Kiểm tra sản phẩm tồn tại trong giỏi hàng hay chưa
            Boolean isExits = false;
            System.out.println("Danh sách cartItem: " + cartItemDTOS);
            for (CartItemDTO dto1: cartItemDTOS){
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
                if(dto1.getIdProduct().equals(cartItemDTO.getIdProduct())) {
                    isExits = true;
                    dto1.setQuantityProduct(dto1.getQuantityProduct()+ cartItemDTO.getQuantityProduct());
                    dto1.setTotalPriceCartItem(dto1.getQuantityProduct() * dto1.getPriceSale());
                }
            }
<<<<<<< HEAD
            }
=======
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06

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

                cartItemDTO.setTotalPriceCartItem(cartItemDTO.getPriceUnit()*cartItemDTO.getQuantityProduct());
<<<<<<< HEAD
                cartDTO.getListCartItem().add(cartItemDTO);
            }
            session.setAttribute("myCart",cartDTO);
            map.put("code",200);
            map.put("totalItem",cartDTO.getListCartItem().size());
            System.out.println("KHi chưa đăng nhập:  " + cartItemDTO);
=======
                cartItemDTOS.add(cartItemDTO);
            }
            session.setAttribute("myCart",cartItemDTOS);
            map.put("code",200);
            map.put("totalItem",cartItemDTOS.size());
            System.out.println("KHi chưa đăng nhập:  " + cartItemDTOS);
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06

        }else{// KHi đã đăng nhập
            OrdersVO ordersVO = new OrdersVO();
            Boolean checked = false;
            // Nếu trong giỏ hàng có sản phẩm
<<<<<<< HEAD
            if(orderService.findOrdersByAccountCart(auth.getName()) != null){
                ordersVO = orderService.findOrdersByAccountCart(auth.getName());
                for (OrderDetailsVO vo: ordersVO.getOrderDetails()){
                    // Nếu sản phẩm đã tồn tại trong giỏ hàng
                    if(vo.getProducts().getId().equals(cartItemDTO.getIdProduct())){
                        checked = true;
                        detailService.updateQuantityOrderDetail(vo.getQuantity() + cartItemDTO.getQuantityProduct(),vo.getId());
                    }
=======
            if(orderService.findOrdersByAccount(auth.getName()).getId() != null){
                ordersVO = orderService.findOrdersByAccount(auth.getName());
                System.out.println("OrderVO:  "+ordersVO);
                for (OrderDetailsVO vo: ordersVO.getOrderDetailsVO()){
                    // Nếu sản phẩm đã tồn tại trong giỏ hàng
                    if(vo.getProductsVO().getId().equals(cartItemDTO.getIdProduct())){
                            checked = true;
                            detailService.updateQuantityOrderDetail(vo.getQuantity() + cartItemDTO.getQuantityProduct(),vo.getId());
                    }


>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
                }
                // Khi sản phẩm chưa có trong giỏ hàn
                if(!checked){
                    OrderDetailsVO detailsVO = new OrderDetailsVO();
<<<<<<< HEAD
                    detailsVO.setIdOrder(ordersVO.getId());
                    detailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                    ProductsVO productsVO = productService.getOne(cartItemDTO.getIdProduct());
                    detailsVO.setProducts(productsVO);
=======
                    detailsVO.setOrdersVO(ordersVO);
                    detailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                    ProductsVO productsVO = productService.getOne(cartItemDTO.getIdProduct());
                    detailsVO.setProductsVO(productsVO);
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
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
<<<<<<< HEAD
                map.put("totalItem",orderService.findOrdersByAccountCart(auth.getName()).getOrderDetails().size());

            }else { // nếu người dùng chưa có sản phẩm nào trong giỏ hàng
                AccountVO  accountVO = modelMapper.map(accountService.findByEmail(auth.getName()),AccountVO.class);
                ordersVO.setAccount(accountVO);
=======
                map.put("totalItem",orderService.findOrdersByAccount(auth.getName()).getOrderDetailsVO().size());

            }else { // nếu người dùng chưa có sản phẩm nào trong giỏ hàng
                AccountVO  accountVO = modelMapper.map(accountService.findByEmail(auth.getName()),AccountVO.class);
                ordersVO.setAccountVO(accountVO);
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
                ordersVO.setReceived(-2);
                ordersVO = orderService.saveOrders(ordersVO);
                ProductsVO productsVO = productService.getOne(cartItemDTO.getIdProduct());
                OrderDetailsVO detailsVO = new OrderDetailsVO();
<<<<<<< HEAD
                detailsVO.setProducts(productsVO);
                detailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                detailsVO.setIdOrder(ordersVO.getId());
=======
                detailsVO.setProductsVO(productsVO);
                detailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                detailsVO.setOrdersVO(ordersVO);
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
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
<<<<<<< HEAD
=======

>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
            }
            System.out.println("Khi đã đăng nhập: " + ordersVO);
        }
        return map;
    }

    @Override
<<<<<<< HEAD
    public CartDTO findCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
            CartDTO cartDTO = (CartDTO) session.getAttribute("myCart");
            return cartDTO;
        }else{
            List<CartItemDTO> list = new ArrayList<>();
            OrdersVO ordersVO = orderService.findOrdersByAccountCart(auth.getName());

            if (ordersVO != null){

                CartDTO cartDTO = new CartDTO();
                ordersVO.getOrderDetails().forEach(vo -> {
                    CartItemDTO cartItemDTO = new CartItemDTO();
                    cartItemDTO.setIdProduct(vo.getProducts().getId());
                    cartItemDTO.setNameProduct(vo.getProducts().getName());
                    cartItemDTO.setNameCate(vo.getProducts().getCategory().getName());
                    cartItemDTO.setImgUrl(vo.getProducts().getImgUrl());
                    cartItemDTO.setQuantityProduct(vo.getQuantity());
                    cartItemDTO.setPriceUnit(vo.getProducts().getOutputPrice());
                    cartItemDTO.setIdOrderDetail(vo.getId());
                    float price = 0;
                    if(vo.getProducts().getSaleProduct() != null){
                        price += vo.getProducts().getOutputPrice() - ( vo.getProducts().getOutputPrice()/100 * vo.getProducts().getSaleProduct().getPromotion());
                    }else{
                        price += vo.getProducts().getOutputPrice();
                    }
                    cartItemDTO.setPriceSale(price);
                    cartItemDTO.setTotalPriceCartItem(cartItemDTO.getQuantityProduct() * cartItemDTO.getPriceSale());
                    list.add(cartItemDTO);
                });
                float totalPriceOrder = 0;
                float totalPriceUnitOrder = 0;
                for (CartItemDTO dto:list) {
                    totalPriceOrder+=dto.getTotalPriceCartItem();
                    totalPriceUnitOrder+=dto.getPriceUnit();
                }
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

}
=======
    public List<CartItemDTO> findAllCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        if(auth == null || auth.getPrincipal().equals("anonymousUser")){
            List<CartItemDTO> list = (List<CartItemDTO>) session.getAttribute("myCart");
            return list;
        }else{
            List<CartItemDTO> list = new ArrayList<>();
            OrdersVO ordersVO = orderService.findOrdersByAccount(auth.getName());

            ordersVO.getOrderDetailsVO().forEach(vo -> {
                CartItemDTO cartItemDTO = new CartItemDTO();
                cartItemDTO.setIdProduct(vo.getProductsVO().getId());
                cartItemDTO.setNameProduct(vo.getProductsVO().getName());
                cartItemDTO.setImgUrl(vo.getProductsVO().getImgUrl());
                cartItemDTO.setQuantityProduct(vo.getQuantity());
                cartItemDTO.setPriceUnit(vo.getProductsVO().getOutputPrice());
                cartItemDTO.setTotalPriceCartItem(cartItemDTO.getQuantityProduct() * cartItemDTO.getPriceSale());
                list.add(cartItemDTO);
            });
            return list;
        }

    }
}



>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06
