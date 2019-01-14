package netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 实现客户端的业务逻辑
 * 客户端的业务逻辑实现依然很简单，更复杂的用法将在后面章节详细介绍，和编写服务器的ChannelHandler一样，在这里将自定义一个继承SimpleChannelInboundHandler
 * 的ChannelHandler来处理业务，通过重写父类的三个方法来处理感兴趣的事件
 * 1.channelActive();客户端连接服务器后被调用
 * 2.channelRead0():服务器端接受到数据后被调用
 * 3.exceptionCaught():发生异常时被调用
 *
 * 可能你会问为什么在这里使用的是SimpleChannelInboundHandler而不使用ChannelInboundHandlerAdapter?主要原因是ChannelInboundHandlerAdapter
 * 在处理完消息后需要负责释放资源。在这里将调用ByteBuf.release()来释放资源。SimpleChannelInboundHandler会在完成channelRead0后释放消息，
 * 这是通过Netty处理所有消息的ChannelHandler实现了R
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

    /**
     * 这个方法必须被重写？ 否则服务器没有接受到数据不会触发相应的事件，客户端向服务器端发数据
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(" channelActive start......");
        ctx.writeAndFlush(Unpooled.copiedBuffer("gwg Netty rocks!",
                CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println(
                "gwg Client received: " + in.toString(CharsetUtil.UTF_8));
    }

  /*  @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }*/
}
