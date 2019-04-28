// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.lru;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhangpeng34
 * Created on 2019/4/28 下午8:20
**/
public class LRUNode {
    LRUNode before;
    LRUNode after;
    String key;
    Object value;

    public LRUNode(String key, Object value,LRUNode before, LRUNode after) {
        this.before = before;
        this.after = after;
        this.key = key;
        this.value = value;
    }

    public LRUNode getBefore() {
        return before;
    }

    public void setBefore(LRUNode before) {
        this.before = before;
    }

    public LRUNode getAfter() {
        return after;
    }

    public void setAfter(LRUNode after) {
        this.after = after;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toString(){
        return "key:"+key+",value:"+value;
    }
}