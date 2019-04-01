// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.sort;
/**
 * @author zhangpeng34
 * Created on 2019/3/9 上午1:24
**/ 
public class QS {
    private static void quickSort(int[] arr,int left,int right){
        //递归终止条件
        if(left>= right){
            return;
        }


        int i = left;
        int j = right;
        //找基准,最左侧的数
        int base = arr[i];

        while (i!=j){
            //从右往左找第一个小于base的
            while (arr[j] >= base && i < j){
                j--;
            }

            while (arr[i] <= base && i<j){
                i++;
            }

            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }

        arr[left] = arr[i];
        arr[i] = base;

        quickSort(arr,left,i-1);
        quickSort(arr,i+1,right);


    }

    public static void main(String[] args) {
        int[] a = {12,20,5,16,15,1,12,45,23,9,343,11,57,1,7974,23,11,6,1};
        quickSort(a,0,a.length-1);
        for (int i : a) {
            System.out.println(i);
        }
    }
}