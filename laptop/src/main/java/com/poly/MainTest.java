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
        String add = "duong bui - 232, Xã Phú Cường, Huyện Ba Vì, Thành phố Hà Nội";
        Integer nameInt = add.lastIndexOf('-');
        String name = add.substring(0,nameInt);
        System.out.println(name.trim());
    }

    public static Integer getRandomCodeOrder(){
        SecureRandom secureRandom = new SecureRandom();
        Integer randomWithSecureRandomWithinARange = secureRandom.nextInt(99999999 - 1) + 1;
        String num =  "011"+randomWithSecureRandomWithinARange.toString();
        return Integer.parseInt(num);
    }
}
