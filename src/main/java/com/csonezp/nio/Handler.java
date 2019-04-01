// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 下午3:23
 **/
public abstract class Handler implements Runnable {
    Selector currentSelector;
    Selector nextSelector;

    SocketChannel socketChannel;

    public Handler(Selector currentSelector, Selector nextSelector) {
        this.currentSelector = currentSelector;
        this.nextSelector = nextSelector;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<SelectionKey> keyOps = selectKeys();

                for (SelectionKey keyOp : keyOps) {
                    handle(keyOp);
                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }
    }

    private List<SelectionKey> selectKeys() throws IOException {
        List<SelectionKey> list = new ArrayList<SelectionKey>();

        int keyOps = currentSelector.select(100);
        boolean flag = keyOps > 0;

        if (flag) {
            Set<SelectionKey> readyKeySet = currentSelector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if(!key.isValid()){
                    continue;
                }
                keyOps = key.readyOps();
                if (keyOps == SelectionKey.OP_READ || keyOps == SelectionKey.OP_WRITE) {
                    socketChannel = (SocketChannel) key.channel();

                    if(socketChannel!=null&& socketChannel.isConnected()){
                        socketChannel.configureBlocking(false);
                    }

                }
                list.add(key);
            }
        }
        return list;

    }

    protected abstract void handle(SelectionKey key) throws IOException;
}