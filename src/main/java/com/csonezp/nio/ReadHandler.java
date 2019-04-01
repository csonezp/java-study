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
 * Created on 2019/4/1 下午3:35
 **/
@SuppressWarnings("Duplicates")
public class ReadHandler extends Handler {
    public ReadHandler(Selector currentSelector, Selector nextSelector) {
        super(currentSelector, nextSelector);
    }

    @Override
    protected void handle(SelectionKey key) throws IOException {
        //获取此key对应的套接字通道
        SocketChannel socketChannel = (SocketChannel) key.channel();
        //创建一个大小为1024k的缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuffer sb = new StringBuffer();
        //将通道的数据读到缓存区
        int count = socketChannel.read(buffer);
        if (count > 0) {
            //翻转缓存区(将缓存区由写进数据模式变成读出数据模式)
            buffer.flip();
            //将缓存区的数据转成String
            sb.append(new String(buffer.array(), 0, count));
        }

        String str = sb.toString();
        if (StringUtils.isNotBlank(str)) {
            System.out.println("handle read:"+str);
            socketChannel.register(nextSelector, SelectionKey.OP_WRITE, str);
        }
    }
}