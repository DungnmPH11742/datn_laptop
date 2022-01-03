package com.poly.service.impl;

import com.poly.DTO.CartDTO;
import com.poly.DTO.CheckOut;
import com.poly.service.CheckoutService;
import com.poly.service.OrderService;
import com.poly.vo.AccountVO;
import com.poly.vo.DeliveryAddressVO;
import com.poly.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Properties;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

    @Autowired
    private OrderService orderService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String nameMail;
    @Value("${config.mail.password}")
    private String password;

    @Autowired private ThymeleafService thymeleafService;

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
     public Date getDateNowSql() throws ParseException {
        java.util.Date dateNow = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        Date dateSql = new Date(calendar.getTime().getTime());
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

    public void  sendEmailWhenSeccessOrder(AccountVO accountVO, CartDTO cartDTO, DeliveryAddressVO deliveryAddressVO, String description) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(nameMail, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(accountVO.getEmail())});
            message.setFrom(new InternetAddress(accountVO.getEmail()));
            message.setSubject("Đơn hàng của bạn");
            message.setContent(thymeleafService.getContent(accountVO,cartDTO,deliveryAddressVO,description), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        /*String toAddress = accountVO.getEmail();
        String fromAddress = nameMail;
        String senderName = "Vua của Laptop";
        String subject = "Thông tin đơn hàng";
        String content = "Dear [[name]],<br>"
                + "Bạn đã mua hàng thành công:<br>"
                + "Thank you,<br>"
                + "Kingdom of Laptop.";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", accountVO.getFullName());
        helper.setText(content, true);
        // inside your getSalesUserData() method
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    javaMailSender.send(message);
                } catch (Exception e) {
                    System.out.println("error mail"+e);
                }
            }
        });
        emailExecutor.shutdown(); // it is very important to shutdown your non-singleton ExecutorService.
*/
    }
}
