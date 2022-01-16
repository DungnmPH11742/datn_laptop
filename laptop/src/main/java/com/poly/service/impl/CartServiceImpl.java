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
        Map<String, Object> map = new HashMap<>();
        CartDTO cartDTO = new CartDTO();
        // kiểm tra xem sản phẩm có tồn tại trong giỏ hàng hay không( == true: đã có sản phẩm trong giỏ gàng)
        Boolean isExitsCart = false;
        // Kiểm tra xem có nên thêm sản phảm vào giỏ hàng hay không(Nếu =true thì thêm vào giỏ hàng)
        Boolean checkCart = false;
        Integer indexCartItem = 0;
// ProductDetail
        ProductsDetailVO productsDetailVO = productDetailService.findBySku(cartItemDTO.getSku());
        ProductsVO productsVO = productService.getOne(productsDetailVO.getIdProduct());
//  Giá của sản phẩm sắp thêm( chưa nhân với số lượng)
        float priceSale = 0;
        if (productsDetailVO.getSaleProduct() != null) {
            priceSale = productsDetailVO.getPrice() - (productsDetailVO.getPrice() / 100 * productsDetailVO.getSaleProduct().getPromotion());
        }else {
            priceSale = productsDetailVO.getPrice();
        }
        System.out.println("Price chuẩn:  " +  priceSale);
        System.out.println("ProductDetail: "+ productsDetailVO);
// Kiểm tra My Cart
        if(session.getAttribute("myCart") !=null){
            cartDTO = (CartDTO) session.getAttribute("myCart");

            System.out.println("Tổng tiền là : " + cartDTO.getTotalPrice());
            System.out.println("Tổng tiền sau thêm là : " +  (cartDTO.getTotalPrice() + priceSale * cartItemDTO.getQuantityProduct()));
// Kiểm tra tổng tiền > 150.000.000đ
            if( (cartDTO.getTotalPrice() + priceSale * cartItemDTO.getQuantityProduct() ) > 150000000){
                map.put("message","maxPrice");
            }else {
                for (int i=0;i < cartDTO.getListCartItem().size(); i++){
                    if(cartDTO.getListCartItem().get(i).getSku().equalsIgnoreCase(cartItemDTO.getSku()) || cartDTO.getListCartItem().get(i).getSku().equals(cartItemDTO.getSku())){
// Sô lượng vượt quá giới hạn
                        if(cartDTO.getListCartItem().get(i).getQuantityProduct() + cartItemDTO.getQuantityProduct() > productsDetailVO.getQuantity()){
                            map.put("message","maxQuantity");
                            isExitsCart = true;
                        }else{
                            isExitsCart = true;
                            checkCart = true;
                            indexCartItem = i;
                        }
                    }
                }

                if(!isExitsCart){
                    if(cartItemDTO.getQuantityProduct() <= productsDetailVO.getQuantity()){
                        checkCart = true;
                    }
                }
            }
        }else {
            if(priceSale * cartItemDTO.getQuantityProduct() > 150000000){
                checkCart = false;
                map.put("message","maxPrice");
            }else if(cartItemDTO.getQuantityProduct() > productsDetailVO.getQuantity()) {
                checkCart = false;
                map.put("message","maxQuantity");
            }else {
                cartDTO = new CartDTO();
                session.setAttribute("myCart",cartDTO);
                checkCart = true;
            }

        }
// Nếu sản phẩm đã tồn tại trong giỏ hàng
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
// Nếu sản phẩm đã có trong giỏ hàng và đã thỏa mãn điều kiện
        if(checkCart){
            // Thông tin sản phẩm trùng trong giỏ hàng
            if(isExitsCart){
                System.out.println("Đã có trong giỏ hàng");

                CartItemDTO itemDTO = cartDTO.getListCartItem().get(indexCartItem);
                // Cập nhật số lượng sản phẩm trong giỏ hàng
                cartDTO.getListCartItem().get(indexCartItem).setQuantityProduct(itemDTO.getQuantityProduct() + cartItemDTO.getQuantityProduct());
                cartDTO.getListCartItem().get(indexCartItem).setQuantity(productsDetailVO.getQuantity());
                // Cập nhật lại tổng tiền của  sản phẩm đang trùng

                cartDTO.getListCartItem().get(indexCartItem).setTotalPriceCartItem( cartDTO.getListCartItem().get(indexCartItem).getQuantityProduct() * priceSale);
                System.out.println("TotalPriceCartItem : " + cartDTO.getListCartItem().get(indexCartItem).getTotalPriceCartItem());
                System.out.println("price: " + priceSale);
                System.out.println("Quantity: " + cartDTO.getListCartItem().get(indexCartItem).getQuantityProduct());

                // Khi đã đăng nhập  ( Cập nhật lại số lượng sản phẩm trong orderDetail )
                if(auth == null || auth.getPrincipal().equals("anonymousUser")) {
                }else {
                    orderDetailService.updateQuantityOrderDetail(cartDTO.getListCartItem().get(indexCartItem).getQuantityProduct(), itemDTO.getIdOrderDetail());
                }
                map.put("totalPriceProduct_dt",cartDTO.getListCartItem().get(indexCartItem).getTotalPriceCartItem());
                map.put("message","Cập nhật thành công");
            }
            // Sản phẩm chưa tồn tại
            else {
                cartItemDTO.setTotalPriceCartItem(cartItemDTO.getQuantityProduct() * priceSale);

                cartDTO.getListCartItem().add(cartItemDTO);
                // Đã đăng nhập
                if(auth == null || auth.getPrincipal().equals("anonymousUser")) {
                }else {
                    OrdersVO ordersVO = new OrdersVO();
                    // Chưa tồn tại Giỏ hàng
                    if(orderService.findOrdersByAccountCart(auth.getName()) ==null){
                        AccountVO  accountVO = modelMapper.map(accountService.findByEmailUser(auth.getName()),AccountVO.class);
                        ordersVO.setAccount(accountVO);
                        ordersVO.setReceived(-2);
                        ordersVO = orderService.saveOrders(ordersVO);

                    }else{
                        ordersVO = orderService.findOrdersByAccountCart(auth.getName());
                    }
// Thêm vào db
                    OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
                    // Order
                    orderDetailsVO.setIdOrder(ordersVO.getId());
                    // số lượng
                    orderDetailsVO.setQuantity(cartItemDTO.getQuantityProduct());
                    orderDetailsVO.setProductsDetailVO(productsDetailVO);
                    orderDetailsVO = orderDetailService.saveOderDetail(orderDetailsVO);
                    cartDTO.setIdOrder(ordersVO.getId());
                    cartItemDTO.setIdOrderDetail(orderDetailsVO.getId());
                }
                map.put("totalPriceProduct_dt",cartItemDTO.getTotalPriceCartItem());
                map.put("message","Thêm thành công");
            }
            float totalprice = 0;
            for (CartItemDTO cartItemDTO1: cartDTO.getListCartItem()){
                totalprice+= cartItemDTO1.getTotalPriceCartItem();
            }
            cartDTO.setTotalPrice(totalprice);
            map.put("totalpriceCart",cartDTO.getTotalPrice());
            map.put("totalItem",cartDTO.getListCartItem().size());
            session.setAttribute("myCart",cartDTO);
            map.put("code",200);
            System.out.println("Tổng số bản ghi là: " + cartDTO.getListCartItem().size());
        }

        return map;
    }


    @Override
    public CartDTO findCart() {
        System.out.println("FindCart: " + session.getAttribute("myCart"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(session.getAttribute("myCart")!= null) {
            CartDTO cartDTO = (CartDTO) session.getAttribute("myCart");
            List<CartItemDTO> itemDTOList = new ArrayList<>();
            float totalPrice = 0;
            float totalPriceUnit = 0;
            for (CartItemDTO cartItemDTO : cartDTO.getListCartItem()) {

                ProductsDetailVO productsDetailVO = productDetailService.findBySku(cartItemDTO.getSku());
                cartItemDTO.setSku(productsDetailVO.getSku());
                cartItemDTO.setNameProduct(productService.getOne(productsDetailVO.getIdProduct()).getName());
                cartItemDTO.setNameCate(productService.getOne(productsDetailVO.getIdProduct()).getCategory().getName());
                cartItemDTO.setImgUrl(productsDetailVO.getImgUrl());
                cartItemDTO.setQuantity(productsDetailVO.getQuantity());
                // Price
                cartItemDTO.setPriceUnit(productsDetailVO.getPrice());
                float priceSale = 0;
                if (productsDetailVO.getSaleProduct() != null) {
                    priceSale = productsDetailVO.getPrice() - (productsDetailVO.getPrice() / 100 * productsDetailVO.getSaleProduct().getPromotion());
                    System.out.println("Vafo 1");
                }else {
                    priceSale = productsDetailVO.getPrice();
                    System.out.println("vaof 2");
                }
                System.out.println("gias: " + priceSale);
                cartItemDTO.setPriceSale(priceSale);
                cartItemDTO.setTotalPriceCartItem(cartItemDTO.getQuantityProduct() * cartItemDTO.getPriceSale());
                itemDTOList.add(cartItemDTO);
                System.out.println("Cart: " + cartItemDTO);
                // Tỉnh tổng toàn bộ tiền của đơn hàng
                totalPrice += cartItemDTO.getTotalPriceCartItem();
                totalPriceUnit += cartItemDTO.getPriceUnit();

            }
            cartDTO.getListCartItem().removeAll(cartDTO.getListCartItem());
            cartDTO.setListCartItem(itemDTOList);
            cartDTO.setTotalPrice(totalPrice);
            cartDTO.setIdOrder(cartDTO.getIdOrder());
            cartDTO.setTotalPriceCart(totalPrice);
            cartDTO.setTotalPriceUnit(totalPriceUnit);
            session.setAttribute("myCart", cartDTO);
            return cartDTO;
        }
        session.setAttribute("myCart", null);
        return null;
    }



    @Override
    public void addCartFromSession() {
        System.out.println("Vào không vậy");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Chưa đăng nhập
        if(auth == null || auth.getPrincipal().equals("anonymousUser")) {
        }else {
            System.out.println("addCartFromSession: 1" );
            if(session.getAttribute("myCart") != null){
                System.out.println("addCartFromSession: 2" );
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
                System.out.println("addCartFromSession + ordersVO: " + ordersVO);
                // Nếu sản phẩm đã tồn tại trong giỏ hàng
                float totalPrice = 0;
                float priceSale = 0;
                if(ordersVO.getOrderDetails() != null) {
                    for (OrderDetailsVO vo : ordersVO.getOrderDetails()) {
                        ProductsDetailVO productsDetailVO = productDetailService.findBySku(vo.getProductsDetailVO().getSku());

                        if (productsDetailVO.getSaleProduct() != null) {
                            priceSale = productsDetailVO.getPrice() - (productsDetailVO.getPrice() / 100 * productsDetailVO.getSaleProduct().getPromotion());
                        } else {
                            priceSale = productsDetailVO.getPrice();
                        }
                        totalPrice = priceSale * vo.getQuantity();
                    }
                }

                System.out.println("TotalPrce: " + totalPrice);
                if(totalPrice + dto.getTotalPrice() < 150000000){
                    System.out.println("addCartFromSession: check giá" );
                    Boolean checked = false;
                    for (CartItemDTO  itemDTO : dto.getListCartItem()){
                        checked = false;
                        if(ordersVO.getOrderDetails()!=null){
                            for (OrderDetailsVO vo: ordersVO.getOrderDetails()){
                                // Nếu sản phẩm đã có trong database
                                System.out.println("addCartFromSession: 5");
                                if(vo.getProductsDetailVO().getSku().equals(itemDTO.getSku())){
                                    ProductsDetailVO productsDetailVO = productDetailService.findBySku(vo.getProductsDetailVO().getSku());
                                    if(productsDetailVO.getQuantity() >= vo.getQuantity() + itemDTO.getQuantityProduct()){
                                        checked = true;
                                        orderDetailService.updateQuantityOrderDetail(vo.getQuantity() + itemDTO.getQuantityProduct(),vo.getId());
                                    }

                                }
                            }
                        }

                        System.out.println("Check 2");
                        if(!checked && itemDTO.getQuantityProduct() <= productDetailService.findBySku(itemDTO.getSku()).getQuantity()){
                            System.out.println("Check 3");
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



    @Override
    public void transferFromOrderUpSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        float totalPrice = 0;
        float totalPriceUnit = 0;
        if(orderService.findOrdersByAccountCart(auth.getName()) != null){
            CartDTO cartDTO = new CartDTO();
            List<CartItemDTO> itemDTOList = new ArrayList<>();
            OrdersVO ordersVO = orderService.findOrdersByAccountCart(auth.getName());
            for (OrderDetailsVO vo: ordersVO.getOrderDetails()){
                CartItemDTO itemDTO = new CartItemDTO();
                ProductsDetailVO productsDetailVO = productDetailService.findBySku(vo.getProductsDetailVO().getSku());
                itemDTO.setSku(productsDetailVO.getSku());
                itemDTO.setNameProduct(productService.getOne(productsDetailVO.getIdProduct()).getName());
                itemDTO.setNameCate(productService.getOne(productsDetailVO.getIdProduct()).getCategory().getName());
                itemDTO.setImgUrl(productsDetailVO.getImgUrl());
                itemDTO.setQuantityProduct(vo.getQuantity());
                itemDTO.setQuantity(productsDetailVO.getQuantity());
                itemDTO.setIdOrderDetail(vo.getId());
                itemDTO.setPriceUnit(productsDetailVO.getPrice());
                float priceSale = 0;
                if(productsDetailVO.getSaleProduct() != null){
                    priceSale += productsDetailVO.getPrice() - ( productsDetailVO.getPrice()/100 * productsDetailVO.getSaleProduct().getPromotion());
                }else{
                    priceSale += productsDetailVO.getPrice();
                }
                itemDTO.setPriceSale(priceSale);
                itemDTO.setTotalPriceCartItem(vo.getQuantity() * priceSale);
                itemDTOList.add(itemDTO);
                // Tỉnh tổng toàn bộ tiền của đơn hàng
                totalPrice += itemDTO.getTotalPriceCartItem();
                totalPriceUnit += itemDTO.getPriceUnit();
            }
            cartDTO.setListCartItem(itemDTOList);
            cartDTO.setTotalPrice(totalPrice);
            cartDTO.setTotalPriceCart(totalPrice);
            cartDTO.setIdOrder(ordersVO.getId());
            cartDTO.setTotalPriceUnit(totalPriceUnit);
            session.setAttribute("myCart", cartDTO);
        }
    }


    @Override
    public List<CartItemDTO> getListCartItemOrder(Integer idOrder) {
        List<OrderDetailsVO> cartItemDTOList = this.orderDetailService.getOrderDetailByIdOrder(idOrder);
        List<CartItemDTO> itemDTO = new ArrayList<>();
        cartItemDTOList.forEach(vo ->{
            CartItemDTO cartItemDTO = new CartItemDTO();
            System.out.println("SKU: "+vo.getProductsDetailVO().getSku());
            ProductsDetailVO productsDetailVO = productDetailService.findBySku(vo.getProductsDetailVO().getSku());
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
            itemDTO.add(cartItemDTO);
        });
        return itemDTO;
    }

}