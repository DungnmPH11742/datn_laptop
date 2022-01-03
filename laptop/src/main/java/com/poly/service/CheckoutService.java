package com.poly.service;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CheckOut;
import com.poly.vo.AccountVO;
import com.poly.vo.DeliveryAddressVO;
import com.poly.vo.OrdersVO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public interface CheckoutService {
    OrdersVO addOrderVo(DeliveryAddressVO deliveryAddressVO, Boolean paymentStatus, CheckOut checkOut, Integer idOrder, java.sql.Date date);

    java.sql.Date getDateNowSql() throws ParseException;

    String getRandomCodeOrder();

    CartDTO getListCartFromSession(HttpServletRequest request);

    void  sendEmailWhenSeccessOrder(AccountVO accountVO, CartDTO cartDTO, DeliveryAddressVO deliveryAddressVO, String description) throws MessagingException, UnsupportedEncodingException;
}