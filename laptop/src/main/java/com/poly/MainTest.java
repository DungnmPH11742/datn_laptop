package com.poly;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

public class MainTest {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
//        System.out.println(date);
        System.out.println(getRandomCodeOrder());
    }

    public static Integer getRandomCodeOrder(){
        SecureRandom secureRandom = new SecureRandom();
        Integer randomWithSecureRandomWithinARange = secureRandom.nextInt(99999999 - 1) + 1;
        String num =  "011"+randomWithSecureRandomWithinARange.toString();
        return Integer.parseInt(num);
    }
}
