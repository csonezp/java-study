// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * @author zhangpeng34
 * Created on 2019/4/1 下午5:54
**/ 
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger
            .getLogger(TimeClientHandler.class.getName());

    private int counter;

    private byte[] req;


    /**
     * Creates a client-side handler.
     */
    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator"))
                .getBytes();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        String body = msg.toString();
        System.out.println("Now is : " + body+",counter : "+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 释放资源
        logger.warning("Unexpected exception from downstream : "
                + cause.getMessage());
        ctx.close();
    }
}