package com.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 引导服务器
 * 1.监听和接受进来的连接请求
 * 2.配置Channel来通知一个关于入站消息的EchoServerHandler实例
 */
public class EchoServerTest {

    private final int port = 8888;

    public static void main(String[] args) throws Exception {
        new EchoServerTest().start();
    }
    public void start() throws Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(2);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(3);//

        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup) //由于我们使用在NIO传输，我们指定NioEventLoopGroup接受和处理新连接
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
            System.out.println("before bind ...");
            ChannelFuture f = b.bind().sync();//阻塞，等待连接
            System.out.println(EchoServerTest.class.getName()+" started and listen on "+ f.channel().localAddress());

            f.channel().closeFuture().sync();//阻塞
        } finally {
            bossGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }
}
