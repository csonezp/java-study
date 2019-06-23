package com.csonezp.algorithm;

/**
 * Created by csonezp on 2019/6/23.
 */
public class Kanpsack {
    private static final int COUNT = 6;
    private static final int CAP = 21;

    static int[] w = new int[]{0, 2, 3, 4, 5, 9};
    static int[] v = new int[]{0, 3, 4, 5, 8, 10};
    static int[][] B = new int[6][21];

    static void kanpsack() {
        for (int k = 1; k < COUNT; k++) {//i是第几件
            for (int c = 1; c < CAP; c++) {//j是容量
                if (w[k] > c) {//如果第i件的重量大于目前容量
                    B[k][c] = B[k - 1][c];
                } else {   //如果可以放得下
                    int getValue = B[k - 1][c - w[k]] + v[k];//如果选择拿
                    int noGetValue = B[k - 1][c];
                    if(getValue > noGetValue){
                        B[k][c] = getValue;
                    } else {
                        B[k][c] = noGetValue;
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        kanpsack();
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B[i].length; j++) {
                System.out.print(B[i][j]+" ");
            }


            System.out.println();
        }

    }
}
