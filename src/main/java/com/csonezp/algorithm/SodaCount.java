package com.csonezp.algorithm;

import java.util.Stack;

/**
 * Created by csonezp on 2019/6/27.
 */
public class SodaCount {

    /**
     * n元钱最多能喝几瓶汽水
     * @param n 金额数
     * @return 喝了几瓶汽水
     */
    public static int maxSodaCount(Integer n) {
        //放汽水的栈
        Stack sodas = new Stack();
        //放瓶盖的栈
        Stack caps = new Stack();

        //喝汽水的总数
        Integer count = 0;

        for (int i = 0; i < n; i++) {
            sodas.push(1);
        }

        while (!sodas.empty() || caps.size() > 1) {


            //如果有汽水
            while (!sodas.empty()) {
                //喝汽水
                sodas.pop();
                //喝了一瓶汽水，count+1
                count++;
                //放瓶盖
                caps.push(1);
            }
            //如果瓶盖有两个或者两个以上
            while (caps.size() > 1) {
                //用两个瓶盖换一瓶汽水
                caps.pop();
                caps.pop();
                sodas.push(1);
            }

        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(maxSodaCount(5));
        System.out.println(maxSodaCount(6));
    }
}
