package com.netty.transport.demo;

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

/**
 * 不使用Netty的NIO
 */
public class PlainNioServer {

    public void serve(int port) throws Exception{
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);//非阻塞
        ServerSocket ss = serverChannel.socket();
        ss.bind(new InetSocketAddress(port));//1服务器绑定到此地址来监听新的连接请求
        /*
         *Java NIO的选择器允许一个单独的线程来监视多个输入通道，你可以注册多个通道使用一个选择器，然后使用一个单独的线程来"选择"通道：这些通道里已经可以处理输入，或者选择已准备
         * 写入的通道。这种选择机制，使得一个单独的线程很容易来管理多个通道。
         */
        Selector selector = Selector.open();//2.打开selector处理channel
        final ByteBuffer buff = ByteBuffer.wrap("Hi!\r\n".getBytes());
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);//3.注册通道ServerChannel使用的选择器selector，你可以注册多个通道使用一个选择器
        for(;;){
            try{
                selector.select();//4.等待新的事件来处理。这将阻塞，直到一个事件传入。
            } catch (IOException e){
                e.printStackTrace();
                break;
            }
            Set<SelectionKey> readKeys = selector.selectedKeys();//5.从收到的所有事件中 获取SelectionKey实例。
            Iterator<SelectionKey> iterator = readKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if(key.isAcceptable()){//6.检查该事件是一个新的连接准备好接受。
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, buff.duplicate());//7
                        System.out.println("Accepted connection from "+channel);
                    }
                    if(key.isWritable()){//8
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while(buffer.hasRemaining()){
                            if(channel.write(buffer) == 0){//9
                                break;
                            }
                        }
                        channel.close();//10
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        key.channel().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


        }

    }



}
