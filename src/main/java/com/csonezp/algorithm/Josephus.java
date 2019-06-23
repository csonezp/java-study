package com.csonezp.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csonezp on 2019/6/24.
 */
public class Josephus {

    /**
     * @param totalCount 总人数
     * @param countNum   报数到几
     * @param aliveCount 最终存活几个
     */
    private static void josephus(int totalCount, int countNum, int aliveCount, int startIndex) {
        List<Integer> people = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            people.add(i);
        }
        //每次报数结果index
        int index = startIndex;
        while (people.size() > aliveCount) {
            //计算本次报数最终需要kill的人的index
            index = index + countNum;
            index = index % people.size() - 1;
            //如果该人是在list的最后一个位置，需要将index置零
            if (index < 0) {
                System.out.println("kill:" + people.get(people.size() - 1));
                people.remove(people.size() - 1);
                index = 0;
            } else {
                //如果不是，直接移除，index不变
                //因为list特性，移除后此index自动指向一下个人，无需再做移动指针操作
                System.out.println("kill:" + people.get(index));
                people.remove(index);
            }
        }

        System.out.println("alive:");
        people.forEach(System.out::println);
    }

    public static void main(String[] args) {
        josephus(41, 3, 2, 0);
    }
}
