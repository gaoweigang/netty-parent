package com.netty.demo.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 引导服务器
 * 1.监听和接受进来的连接请求
 * 2.配置Channel来通知一个关于入站消息的EchoServerHandler实例
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 1){
            System.err.println("Usage: "+ EchoServer.class.getSimpleName()+" <port>");
        }
        int port = Integer.parseInt(args[0]);//1
        new EchoServer(port).start();
    }
    public void start() throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();//

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group) //由于我们使用在NIO传输，我们指定NioEventLoopGroup接受和处理新连接
                    .channel(NioServerSocketChannel.class)//指定NioServerSocketChannel为信道类型
                    .localAddress(new InetSocketAddress(port)) //我们设置本地地址是InetSocketAddress与所选择的端口，服务器绑定到此地址来监听新的连接请求
                     /*在这里我们使用一个特殊的类，ChannelInitializer。当一个新的连接被接受，一个新的子Channel将被创建，ChannelInitializer会添加我们EchoServerHandler的实例到Channel
                    的ChannelPipeline。正如我们如前所述，如果有入站信息，这个处理器将被通知*/
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName()+" started and listen on "+ f.channel().localAddress());

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
