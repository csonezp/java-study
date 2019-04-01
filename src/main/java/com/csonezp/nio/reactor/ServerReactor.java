// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 上午1:26
**/
@Slf4j
public class ServerReactor implements Runnable {

    private SelectorProvider selectorProvider = SelectorProvider.provider();
    private ServerSocketChannel serverSocketChannel;

    public ServerReactor(int port) throws IOException {
        serverSocketChannel = selectorProvider.openServerSocketChannel();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress("localhost", port), 1024);
        serverSocketChannel.configureBlocking(false);
        log.debug(String.format("Server : Server Start.----%d", port));
    }
    @Override
    public void run() {
        try {
            new ServerDispatcher(serverSocketChannel, SelectorProvider.provider()).execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}