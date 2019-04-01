package com.csonezp.nio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NioServer2 {
    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8081;
    private static final int TIMEOUT = 10;
    private static Selector acceptSelector = null;
    private static Selector readSelector = null;
    private static Selector writeSelector = null;

    public static void main(String[] args) {
        selector();
    }


    public static void selector() {
        ServerSocketChannel ssc = null;

        try {
            acceptSelector = Selector.open();
            readSelector = Selector.open();
            writeSelector = Selector.open();

            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            ssc.register(acceptSelector, SelectionKey.OP_ACCEPT);

            new Thread(new AcceptHandler(acceptSelector,readSelector)).start();
            new Thread(new ReadHandler(readSelector,writeSelector)).start();
            new Thread(new WriteHandler(writeSelector,readSelector)).start();

            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (acceptSelector != null) {
                    acceptSelector.close();
                }

                if (readSelector != null) {
                    readSelector.close();
                }

                if (writeSelector != null) {
                    writeSelector.close();
                }
                if (ssc != null) {
                    ssc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}