// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 上午1:27
**/
@Slf4j
public abstract class SocketHandler implements Runnable {
    protected Selector selector;
    protected SocketChannel socketChannel = null;
    protected ServerSocketChannel serverSocketChannel;
    protected ServerDispatcher dispatcher;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public SocketHandler(ServerDispatcher dispatcher, ServerSocketChannel sc, Selector selector) throws IOException {
        this.selector = selector;
        this.serverSocketChannel = sc;
        this.dispatcher = dispatcher;
    }

    public abstract void runnerExecute(int readyKeyOps) throws IOException;

    @Override
    public void run() {
        while (true){
            try {
                int keyOps = this.Select();

                runnerExecute(keyOps);

                Thread.sleep(10);
            } catch (Exception e) {

                log.debug(e.getMessage());
            }
            finally {
                readWriteLock.readLock().unlock();
            }

        }
    }
    private int Select() throws IOException
    {
        int keyOps = this.selector.selectNow();

        boolean flag = keyOps > 0;

        if (flag)
        {
            Set<SelectionKey> readyKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                keyOps = key.readyOps();
                if (keyOps == SelectionKey.OP_READ || keyOps == SelectionKey.OP_WRITE)
                {
                    socketChannel = (SocketChannel)key.channel();
                    socketChannel.configureBlocking(false);
                }
            }
        }

        return keyOps;
    }
}