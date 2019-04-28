// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.LinkedHashMap;


/**
 * @author zhangpeng34
 * Created on 2019/4/25 下午7:34
**/ 
public class BloomFilterDemo {

    public static void main(String[] args) {
        int total = 10000;

        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), total);

        LinkedHashMap map =new LinkedHashMap();


        for (int i = 0; i < total; i++) {
            bloomFilter.put(i);
        }

        System.out.println(bloomFilter.mightContain(2002123));


    }
}