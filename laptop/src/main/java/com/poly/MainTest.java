package com.poly;

import com.poly.entity.DeliveryAddress;
import com.poly.repo.DeliveryAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainTest {
    @Autowired
    private static DeliveryAddressRepository repository;
    public static void main(String[] args) throws ParseException {
<<<<<<< HEAD
        String add = "duong bui - 232, Xã Phú Cường, Huyện Ba Vì, Thành phố Hà Nội";
        Integer nameInt = add.lastIndexOf('-');
        String name = add.substring(0,nameInt);
        System.out.println(name.trim());
=======
        String add = "232, Xã Phú Cường, Huyện Ba Vì, Thành phố Hà Nội";
//        String23:59:59
        String datePare = "2021-12-02 9:00:00";
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = df.format(now);
        Date date1 = df.parse(strDate);
        Date date = df.parse(datePare);
        System.out.println(date.compareTo(date1));

>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
    }

    public static Integer getRandomCodeOrder(){
        SecureRandom secureRandom = new SecureRandom();
        Integer randomWithSecureRandomWithinARange = secureRandom.nextInt(99999999 - 1) + 1;
        String num =  "011"+randomWithSecureRandomWithinARange.toString();
        return Integer.parseInt(num);
    }
}
