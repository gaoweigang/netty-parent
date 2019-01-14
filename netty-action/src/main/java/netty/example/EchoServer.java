package netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void start() throws Exception {
        System.out.println("启动服务器端.........");
        //1.创建NioEventLoopGroup对象来处理事件，如接受新连接，接受数据，写数据等等
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建一个ServerBootStrap实例来引导绑定和启动服务器
            ServerBootstrap b = new ServerBootstrap();
            //Specifies	NIO	transport,	local	socket	address
            //Adds	handler	to	channel	pipeline
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port) //指定InetSocketAddress,服务器监听此端口
                    //设置childHandler执行所有的连接请求,
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            /**
                             *  你的channelHandler必须继承ChannelInboundHandlerAdapter并重写channelRead方法，这个方法在任何时候都会被调用来接受数据
                             */
                            System.out.println("server 返回已绑定的远程SocketAddress: "+ch.remoteAddress());
                            System.out.println("server 返回已绑定的本地SocketAddress: "+ch.localAddress());
                            System.out.println("server 返回分配给Channel的EventLoop: "+ch.eventLoop());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //Binds	server,	waits	for	server	to	close,	and	releases	resources
            ChannelFuture f = b.bind() //绑定服务等待直到绑定完成
                                .sync(); //调用sync()方法会阻塞直到服务器完成绑定
            System.out.println(EchoServer.class.getName() + "started	and	listen	on	“" + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();

        }
    }
    public	static	void	main(String[]	args)	throws	Exception	{
        new	EchoServer(6553).start();
    }
}
