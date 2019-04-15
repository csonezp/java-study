// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.nio;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 下午3:37
**/ 
@SuppressWarnings("ALL")
public class WriteHandler extends Handler {
    public WriteHandler(Selector currentSelector, Selector nextSelector) {
        super(currentSelector, nextSelector);
    }

    @Override
    protected void handle(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        String resp = (String) key.attachment();
        key.attach("");

        if (StringUtils.isEmpty(resp)) {
            return;
        }

        System.out.println("handle write:"+resp);

        sc.write(ByteBuffer.wrap(resp.getBytes()));

        if (resp.equalsIgnoreCase("close") || resp.equalsIgnoreCase("close\n")) {
            key.cancel();
            sc.socket().close();
            sc.close();
            System.out.println("连接断开......");
            return;
        }
        //重设此key兴趣
        sc.register(nextSelector, SelectionKey.OP_READ);
    }
}