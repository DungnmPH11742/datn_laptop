package com.poly.service.impl;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CheckOut;
import com.poly.service.CheckoutService;
import com.poly.service.OrderService;
import com.poly.vo.DeliveryAddressVO;
import com.poly.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrdersVO addOrderVo(DeliveryAddressVO deliveryAddressVO, Boolean paymentStatus, CheckOut checkOut, Integer idOrder, Date date) {
        OrdersVO ordersVO = new OrdersVO();
        ordersVO.setOrderDate(date);
        String addressOder = deliveryAddressVO.getName()+" - "+deliveryAddressVO.getAddress();
        ordersVO.setAddress(addressOder);
        ordersVO.setId(idOrder);
        // check trùng code order
        String codeOrder = getRandomCodeOrder();
        OrdersVO voOrder = this.orderService.getByOrderCode(codeOrder);
        if (voOrder != null){
            codeOrder = getRandomCodeOrder();
        }
        ordersVO.setOrderCode(codeOrder);
        ordersVO.setDescription(checkOut.getDescription());
        ordersVO.setPaymentStatus(paymentStatus);
        ordersVO.setPhoneNumber(deliveryAddressVO.getPhone());
        ordersVO.setReceived(0);
        return ordersVO;
    }
    /**
     * @throws ParseException
     * @return Date now of sql
     */
    @Override
     public java.sql.Date getDateNowSql() throws ParseException {
        java.util.Date dateNow = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        java.sql.Date dateSql = new java.sql.Date(calendar.getTime().getTime());
        return dateSql;
    }

    @Override
    public String getRandomCodeOrder(){
        SecureRandom secureRandom = new SecureRandom();
        Integer randomWithSecureRandomWithinARange = secureRandom.nextInt(99999999 - 1) + 1;
        String num =  "011"+randomWithSecureRandomWithinARange.toString();
        return num;
    }

    @Override
    public CartDTO getListCartFromSession(HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();
            CartDTO cartDTO = null;
            if (httpSession.getAttribute("myCart") != null){
                cartDTO = (CartDTO) httpSession.getAttribute("myCart");
            }
            return cartDTO;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
