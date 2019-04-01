// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 上午1:41
 **/
@Slf4j
public class SocketWriteHandler extends SocketHandler {

    private int BLOCK = 4096;
    private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
    private static int Index = 1;

    public SocketWriteHandler(ServerDispatcher dispatcher, ServerSocketChannel sc, Selector selector) throws IOException {
        super(dispatcher, sc, selector);
    }

    @Override
    public void runnerExecute(int readyKeyOps) throws IOException {
        if (readyKeyOps == SelectionKey.OP_WRITE) {
            log.debug("Server : Writable.");
            String data = String.format("%d", Index);
            byte[] req = data.getBytes();
            sendbuffer.clear();

            log.debug(String.format("Server : Write %s", data));

            sendbuffer = ByteBuffer.allocate(req.length);
            sendbuffer.put(req);
            sendbuffer.flip();
            socketChannel.write(sendbuffer);
            Index++;
            socketChannel.register(dispatcher.getReadSelector(), SelectionKey.OP_READ);
        }
    }
}