// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 上午1:26
**/
@Slf4j
public class SocketAcceptHandler extends SocketHandler {

    public SocketAcceptHandler(ServerDispatcher dispatcher, ServerSocketChannel sc, Selector selector) throws IOException {
        super(dispatcher, sc, selector);
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT, this);
    }
    @Override
    public void runnerExecute(int readyKeyOps) throws IOException {
        if (readyKeyOps == SelectionKey.OP_ACCEPT)
        {
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            log.debug("Server accept");

            socketChannel.register(dispatcher.getReadSelector(), SelectionKey.OP_READ);
        }
    }
}