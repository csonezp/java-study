package com.csonezp.nio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NioServer {
    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8081;
    private static final int TIMEOUT = 10;
    private static Selector acceptSelector = null;
    private static Selector readSelector = null;
    private static Selector writeSelector = null;

    public static void main(String[] args) {
        selector();
    }

    public static void handleAccept(SelectionKey key) throws IOException {
        System.out.println("handle accept");
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);

        sc.register(readSelector, SelectionKey.OP_READ
                , ByteBuffer.allocateDirect(BUF_SIZE));
    }

    public static void handleRead(SelectionKey key) throws IOException {

        //获取此key对应的套接字通道
        SocketChannel channel = (SocketChannel) key.channel();
        //创建一个大小为1024k的缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuffer sb = new StringBuffer();
        //将通道的数据读到缓存区
        int count = channel.read(buffer);
        if (count > 0) {
            //翻转缓存区(将缓存区由写进数据模式变成读出数据模式)
            buffer.flip();
            //将缓存区的数据转成String
            sb.append(new String(buffer.array(), 0, count));
        }
        String str = sb.toString();
        if (StringUtils.isNotBlank(str)) {
            System.out.println("handle read");
            System.out.println(str);
        }


        channel.register(writeSelector, SelectionKey.OP_WRITE, str);


    }

    public static void handleWrite(SelectionKey key) throws IOException {

        SocketChannel sc = (SocketChannel) key.channel();
        String resp = (String) key.attachment();
        key.attach("");

        if (StringUtils.isEmpty(resp)) {
            return;
        }

        System.out.println("handle write");

        sc.write(ByteBuffer.wrap(resp.getBytes()));

        if (resp.equalsIgnoreCase("close") || resp.equalsIgnoreCase("close\n")) {
            key.cancel();
            sc.socket().close();
            sc.close();
            System.out.println("连接断开......");
            return;
        }
        //重设此key兴趣
        sc.register(readSelector, SelectionKey.OP_READ);
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

            // accept thread
            new Thread(() -> {
                try {
                    while (true) {
                        if (acceptSelector.select(TIMEOUT) == 0) {
                            continue;
                        }
                        Set<SelectionKey> selectedKeys = acceptSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            handleAccept(key);
                            keyIterator.remove();
                        }
                        selectedKeys.clear();
                    }
                } catch (Exception e) {

                }
            }).start();

            //read thread
            new Thread(() -> {
                try {
                    while (true) {
                        if (readSelector.select(TIMEOUT) == 0) {
                            continue;
                        }
                        Set<SelectionKey> selectedKeys = readSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey readKey = keyIterator.next();

                            handleRead(readKey);

                            keyIterator.remove();
                        }
                        selectedKeys.clear();
                    }
                } catch (Exception e) {

                }

            }).start();

            new Thread(() -> {
                try {
                    while (true) {
                        if (writeSelector.select(TIMEOUT) == 0) {
                            continue;
                        }
                        Set<SelectionKey> selectedKeys = writeSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey readKey = keyIterator.next();

                            handleWrite(readKey);

                            keyIterator.remove();
                        }
                        selectedKeys.clear();

                    }
                } catch (Exception e) {

                }
            }).start();

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