// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.bankeralgor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author zhangpeng34
 * Created on 2019/3/9 上午12:55
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    private String name; //进程名字

    private int[] max;  //进程的最大需求资源

    private int[] allocation; //进程的当前占有资源

    private int[] need; //进程的需要资源

    private int[] work; //进程当前可分配的资源

    private int[] wAndAllocation; // work + allocation

    private boolean isFinish; //进程是否能完成

    public void printProcess(){ //打印出输入的进程信息
        System.out.println(this.getName()+" | "
                + Arrays.toString(this.getMax())+" | "
                +Arrays.toString(this.getAllocation())+" | "
                +Arrays.toString(this.getNeed())+" | ");
    }
    public void printSafetyProcess(){ //打印出银行家算法分析后的进程情况
        System.out.println(this.getName()+" | "
                +Arrays.toString(this.getWork())+" | "
                +Arrays.toString(this.getNeed())+" | "
                +Arrays.toString(this.getAllocation())+" | "
                +Arrays.toString(this.getWAndAllocation())+" | "
                +this.isFinish());
    }
}