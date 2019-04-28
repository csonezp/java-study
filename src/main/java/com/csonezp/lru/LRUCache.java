// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.lru;
/**
 * @author zhangpeng34
 * Created on 2019/4/28 下午8:20
**/ 
public interface LRUCache {
    void put(String key,Object value);
    Object get(String key);
}