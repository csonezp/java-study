// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.jvm;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author zhangpeng34
 * Created on 2019/2/28 下午6:16
**/ 
public class HeapOOM {
    static class OOMObject{
    }

    public static void main(String[] args) {
        List<OOMObject> list = Lists.newArrayList();
        while (true){
            list.add(new OOMObject());
        }
    }
}