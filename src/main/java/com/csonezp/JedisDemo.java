package com.csonezp;

import org.apache.commons.lang3.RandomUtils;
import redis.clients.jedis.Jedis;

public class JedisDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        for (int i = 0; i < 100000; i++) {
            String key = ""+i;
            String v = "{ \"q4_accept_time\" : 1852.0, \"avg_accept_time\" : 304.2986590816741, \"max_base_freight\" : 990.0, \"avg_bonus\" : 114.14262494920764, \"min_accept_time\" : 5.0, \"order_day_avg_num\" : 1465.5, \"q1_bonus\" : 0.0, \"q3_accept_time\" : 348.0, \"q1_loop_bonus\" : 0.0, \"q3_bonus\" : 200.0, \"max_loop_bonus\" : 220.0, \"q2_loop_bonus\" : 0.0, \"min_loop_bonus\" : 0.0, \"max_accept_time\" : 2397.0, \"q3_loop_bonus\" : 0.0, \"q2_accept_time\" : 159.0, \"avg_loop_bonus\" : 9.66680211296221, \"q1_accept_time\" : 102.0, \"q3_base_freight\" : 1290.0, \"order_accept_rate\" : 0.8367451381780963, \"max_bonus\" : 4500.0, \"stddev_bonus\" : 223.30916849573916, \"min_bonus\" : 0.0, \"q4_bonus\" : 900.0, \"stddev_loop_bonus\" : 30.345345323815867, \"q4_loop_bonus\" : 100.0, \"q4_base_freight\" : 2600.0, \"q1_base_freight\" : 1040.0, \"stddev_base_freight\" : 315.96381226421715, \"stddev_accept_time\" : 368.1544074768012, \"q2_base_freight\" : 1120.0, \"min_base_freight\" : 1000.0, \"avg_base_freight\" : 1209.1018123667377, \"q2_bonus\" : 0.0, \"order_day_accept_avg_num\" : 1226.25 }";
            jedis.set(key,v);
        }

        new Thread(() -> {

        });
        System.out.println("done");
        jedis.close();
    }
}
