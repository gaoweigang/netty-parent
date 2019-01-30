package com.netty.demo.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)//指定channel类型是NIO传输
                    .remoteAddress(new InetSocketAddress(host, port))
                    //当一个新连接被建立之后，一个新的子Channel将被创建。ChannelInitializer会添加我们EchoClientHandler的实例到Channelpipeline
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();//连接到远程，阻塞，等待连接完成
            f.channel().closeFuture().sync();//阻塞，直到channel关闭
        } finally {
            group.shutdownGracefully().sync();//调用shutdownGracefully()来关闭线程池和释放所有资源
        }

    }

    public static void main(String[] args) throws Exception {
        if(args.length != 2){
            System.err.println("Usage: "+EchoClient.class.getSimpleName()+"<host><port>");
            return;
        }
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }
}
