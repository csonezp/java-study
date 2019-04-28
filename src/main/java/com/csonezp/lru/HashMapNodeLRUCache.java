// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.lru;

import lombok.ToString;

import java.util.HashMap;

/**
 * @author zhangpeng34
 * Created on 2019/4/28 下午8:22
 **/
@ToString
public class HashMapNodeLRUCache implements LRUCache {

    private int capacity;
    LRUNode head;
    //保留tail指针，方便移除
    LRUNode tail;
    HashMap<String, LRUNode> map = new HashMap<>();

    public HashMapNodeLRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<String, LRUNode>();
    }


    @Override
    public void put(String key, Object value) {

        LRUNode node = map.get(key);
        if (node != null) {
            node = map.get(key);
            node.value = value;
        } else {
            if (map.size() >= capacity) {
                removeLast();
            }
            node = new LRUNode(key, value, null, null);
            map.put(key, node);
        }

        moveNodeToHead(node);

    }

    @Override
    public Object get(String key) {
        LRUNode node = map.get(key);
        moveNodeToHead(node);
        return node.getValue();
    }

    private void moveNodeToHead(LRUNode node) {

        if (head != null) {
            node.after = head;
            head.before = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    private void removeLast() {
        String key = tail.getKey();
        if (tail == null) {
            return;
        }
        //如果tail == head，此时before为空
        if (tail.before == null) {
            head = null;
            tail = null;
        }else {
            //如果tail!=head,此时就是正常的移除尾部操作
            tail.before.after = null;
            tail = tail.before;
        }
        map.remove(key);

    }

    public static void main(String[] args) {
        LRUCache cache = new HashMapNodeLRUCache(2);
        cache.put("key1", "key1");
        cache.put("key2", "key2");
        cache.put("key3", "key3");
        System.out.println(cache.toString());
    }
}