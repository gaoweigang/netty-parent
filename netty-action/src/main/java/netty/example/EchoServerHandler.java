package netty.example;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 实现服务器业务逻辑
 * Netty使用Futures和回调概念，它的设计允许你处理不同的事件类型。更详细的介绍将在后面章节讲述。你的channelhandler必须继承
 * ChannelInboundHandlerAdapter并且重写ChannelRead方法，这个方法在任何时候都会被调用来接收数据
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    //实现ChannelInboundHandlerAdapter的handler,不会自动释放接收的消息对象
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server received : " + msg);
        ctx.write(msg);
        //手动释放消息
        ReferenceCountUtil.release(msg);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("===================");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 重写ChannelHandler的exceptionCaught方法可以捕获服务器的异常，比如客户端连接服务器后强制关闭，服务器会抛出"客户端主机强制关闭错误"，
     * 通过重写exceptionCaught方法就可以处理异常，比如发生异常后关闭ChannelHandlerContext
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
