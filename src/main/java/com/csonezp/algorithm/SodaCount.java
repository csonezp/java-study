package com.csonezp.algorithm;

import java.util.Stack;

/**
 * Created by csonezp on 2019/6/27.
 */
public class SodaCount {

    public static int maxSodaCount(Integer n) {
        Stack sodas = new Stack();
        Stack caps = new Stack();

        Integer count = 0;

        for (int i = 0; i < n; i++) {
            sodas.push(1);
            count++;
        }

        while (!sodas.empty() || caps.size() > 1) {


            //如果有汽水
            while (!sodas.empty()) {
                //喝汽水
                sodas.pop();
                //放瓶盖
                caps.push(1);
            }
            //如果瓶盖有两个或者两个以上
            while (caps.size() > 1) {
                //用两个瓶盖换一瓶汽水
                caps.pop();
                caps.pop();
                sodas.push(1);
                count++;
            }

        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(maxSodaCount(5));
        System.out.println(maxSodaCount(6));
    }
}
