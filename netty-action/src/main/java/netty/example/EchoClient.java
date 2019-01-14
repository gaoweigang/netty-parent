package netty.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
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
    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //1.创建BootStrap对象用来引导启动客户端
            Bootstrap b = new Bootstrap();
            //创建EventLoopGroup对象并设置到BootStrap中，EventLoopGroup可以理解为是一个线程池，这个线程池用来处理连接，接受数据，发送数据
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))//3.创建InetSocketAddress并设置到BootStrap中，InetSocketAddress是指定连接的服务器地址
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             *所有的Netty程序都是基于ChannelPipeline.ChannelPipeline和EventLoop和EventLoopGroup密切相关，因为它们三个都
                             * 和事件处理相关，所以这就是为什么它们处理IO的工作由EventLoop管理的原因
                             */
                            System.out.println("client 返回已绑定的远程SocketAddress: "+socketChannel.remoteAddress());
                            System.out.println("client 返回已绑定的本地SocketAddress: "+socketChannel.localAddress());
                            System.out.println("client 返回分配给Channel的EventLoop: "+socketChannel.eventLoop());
                            socketChannel.pipeline()//获得ChannelPipeline
                                    .addLast(new EchoClientHandler());//4.添加一个ChannelHandler，客户端成功连接到服务器后就会被执行

                        }

                    });
            ChannelFuture f = b.connect()//调用connect连接到服务器
                    .sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            group.shutdownGracefully().sync();//最后关闭group
        }


    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 6553).start();
    }
}
