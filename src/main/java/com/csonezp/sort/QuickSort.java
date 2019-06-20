// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.sort;

/**
 * @author zhangpeng34
 * Created on 2019/2/28 下午8:43
 **/
public class QuickSort {
    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        //基准
        int tmp = arr[left];

        int i = left;
        int j = right;

        while (i != j) {
            //从右往左找第一个小于基准的
            while (arr[j] >= tmp && i < j) {
                j--;
            }


            //再从左往右找第一个大于基准的
            while (arr[i] <= tmp && i < j) {
                i++;
            }

            //如果没有相遇，就交换i j对应的值
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
            //交换完毕后继续探测直到ij相遇
        }

        //i == j 后，将基准数的值挪到i位置，将i位置的值付给基准数的位置
        arr[left] = arr[i];
        arr[i] = tmp;

        //再对左右部分进行递归

        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    private static void quickSort2(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int tmp = arr[left];

        int i = left;
        int j = right;
        while (i != j) {

            while (arr[j] >= tmp && i < j) {
                j--;
            }

            while ((arr[i] <= tmp) && i < j) {
                i++;
            }

            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        arr[left] = arr[i];
        arr[i] = tmp;
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);

    }

    public static void main(String[] args) {
        int[] a = {12, 20, 5, 16, 15, 1, 30, 45, 23, 9};
        quickSort(a, 0, a.length - 1);
        for (int i : a) {
            System.out.println(i);
        }
    }
}