package com.csonezp;

import java.text.MessageFormat;

public class Test1 {
    public static void main(String[] args) {
        Long a = 1l;
        Long b =null;
        String tmp  = "sdasd:{1},sss:{2}";
        System.out.println(MessageFormat.format(tmp,a,b));
    }
}
