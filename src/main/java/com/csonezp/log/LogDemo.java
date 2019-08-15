package com.csonezp.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class LogDemo {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Date date = new Date();
            Calendar calendar =Calendar.getInstance();
            int a = RandomUtils.nextInt(1,20);
            calendar.setTime(date);
            calendar.set(Calendar.HOUR,a);
            calendar.set(Calendar.MINUTE,a);
            calendar.set(Calendar.SECOND,a);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end-start);

    }
}
