package com.poly.controller.web;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CartItemDTO;
import com.poly.service.ProductService;
import com.poly.service.SaleProductService;
import com.poly.vo.ProductsVO;
import com.poly.vo.SaleProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SaleProductService saleProductService;

    @GetMapping(value = "/user/cart")
    public String cartIndex(Model model, HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        CartDTO myCart = null;
        if (httpSession.getAttribute("myCart") != null){
            myCart = (CartDTO) httpSession.getAttribute("myCart");
            myCart.getListCartItem().forEach(p ->{
                System.out.println(p.getIdProduct());
                System.out.println(p.getQuantityProduct());
            });
            model.addAttribute("listCart",myCart.getListCartItem());
        }
        return "user/cart";
    }

    @PostMapping(value = "/addToCart")
    public ResponseEntity<Map<String,Object>> addToCart(@RequestBody CartItemDTO cartItemDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        CartDTO cartDTO = null;
        if (session.getAttribute("myCart") != null){
            cartDTO = (CartDTO) session.getAttribute("myCart");
        }else {
            cartDTO = new CartDTO();
            session.setAttribute("myCart",cartDTO);
        }
        //Lấy ra danh sách giỏ hàng từ session
        List<CartItemDTO> listCartItem = cartDTO.getListCartItem();
        //Kiểm tra sản phẩm đã có trong giỏ hàng hay chưa
        boolean isExists = false;

        for (CartItemDTO item:listCartItem){
            if (item.getIdProduct().equals(cartItemDTO.getIdProduct()) || item.getIdProduct() == cartItemDTO.getIdProduct()){
                isExists = true;
                item.setQuantityProduct(item.getQuantityProduct()+ cartItemDTO.getQuantityProduct());
                item.setTotalPriceCartItem(item.getQuantityProduct()*item.getPriceUnit());
            }
        }

        //Nếu sản phẩm chưa có trong giỏ hàng
        if (!isExists){
            ProductsVO productsVO = null;
            SaleProductVO saleProductVO = null;
            if (cartItemDTO.getIdProduct() != null){
                productsVO = this.productService.getOne(cartItemDTO.getIdProduct());
            }
            cartItemDTO.setIdProduct(productsVO.getId());
            cartItemDTO.setNameProduct(productsVO.getName());
            if (productsVO.getSaleProduct() != null){
                saleProductVO = this.saleProductService.getSaleProduct(productsVO.getSaleProduct().getSaleCode());
                Float priceUnit = productsVO.getOutputPrice() * (100-productsVO.getSaleProduct().getPromotion())/100;
                Float priceSale = productsVO.getOutputPrice();
                cartItemDTO.setPriceUnit(priceUnit);
                cartItemDTO.setPriceSale(priceSale);
                cartItemDTO.setReducerdPrice(priceUnit-priceSale);
            }else {
                cartItemDTO.setPriceUnit(productsVO.getOutputPrice());
                cartItemDTO.setPriceSale(null);
            }
            cartItemDTO.setImgUrl(productsVO.getImgUrl());
            cartItemDTO.setTotalPriceCartItem(cartItemDTO.getPriceUnit()*cartItemDTO.getQuantityProduct());
            cartDTO.getListCartItem().add(cartItemDTO);
        }
        Float totalPrice = new Float(0);
        for (CartItemDTO c:cartDTO.getListCartItem()) {
            totalPrice += c.getTotalPriceCartItem();
        }
        cartDTO.setTotalPriceCart(totalPrice);
        Float totalReducer = null;
        Float totalPriceUnit = null;
        /*if (cartItemDTO.getReducerdPrice() != null){
            for (CartItemDTO c:cartDTO.getListCartItem()) {
                totalReducer += c.getReducerdPrice();
            }
        }*/

//        cartDTO.setTotalReducerPrice(totalReducer);
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("totalItem",cartDTO.getListCartItem().size());
        return ResponseEntity.ok(map);
    }

}
