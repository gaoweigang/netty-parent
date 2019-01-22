package com.netty.transport.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyNioServer {
    public void server(int port) throws Exception{
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();//1.创建一个ServerBootstrap
            b.group(bossGroup, workerGroup)//使用NioEventLoopGroup允许阻塞模式
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {//指定ChannelInitializer将给每个接受的连接调用
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {//添加ChannelHandler拦截事件，并允许他们做出反应
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //写信息到客户端，并添加ChannelFutureListener，一旦消息写入就关闭连接
                                    ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });

                        }
                    });
            ChannelFuture f = b.bind().sync();//绑定服务器来接受连接
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();//释放所有资源
            workerGroup.shutdownGracefully().sync();

        }
    }

}
