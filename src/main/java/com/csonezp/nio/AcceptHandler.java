// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 下午3:31
**/ 
public class AcceptHandler extends Handler {

    public AcceptHandler(Selector currentSelector, Selector nextSelector) {
        super(currentSelector, nextSelector);
    }

    @Override
    protected void handle(SelectionKey key) throws IOException {
        System.out.println("handle accept");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(nextSelector, SelectionKey.OP_READ
                , ByteBuffer.allocateDirect(1024));
    }
}