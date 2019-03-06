package com.java.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO服务端
 *
 */
public class NioServer {

    //通道管理器,监听连接请求，数据是否准备好
    private Selector selector;

    /**
     * 获得一个ServerSocket通道，并对该通道做一些初始化的工作
     *
     * @param port 绑定的端口号
     */
    public void initServer(int port) throws Exception{
        //获得一个ServerSocket通道/ 打开一个通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        System.out.println("serverChannel isOpen "+serverChannel.isOpen());//判断该通道是否处于打开状态
        //设置通道为非阻塞
        serverChannel.configureBlocking(false);
        System.out.println("before  "+serverChannel.socket().isBound());
        //将该通道对应的ServerSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("after  "+serverChannel.socket().isBound());

        //获得一个通道管理器
        this.selector = Selector.open();
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，注册事件后，
        //当该事件达到时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);//selector监听和它绑定的通道serverChannel的OP_ACCEPT事件
        System.out.println("serverChannel isOpen -> "+serverChannel.isOpen());

    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     */
    public void listen() throws Exception{
        System.out.println("服务端启动成功！");
        //轮询访问selector
        while(true){
            //当注册的事件到达时，方法返回；否则，该方法会一直阻塞
            selector.select();//selector会监听和它绑定的通道Channel里面发生的特定事件
            Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
            while(ite.hasNext()){
                System.out.println("遍历selectedKeys start..");
                SelectionKey key = (SelectionKey) ite.next();
                System.out.println("key  "+ key);
                //删除已选择的key,以防重复处理
                ite.remove();
                handler(key);

            }
        }
    }

    /**
     * 处理请求
     */
    public void handler(SelectionKey key) throws Exception{
        //客户端请求连接事件
        if(key.isAcceptable()){
            handlerAccept(key);
        }else if(key.isReadable()){//获得了可读事件
            handlerRead(key);

        }else if(key.isWritable()){//获得了可写事件

        }
    }

    public void handlerAccept(SelectionKey key) throws Exception{
        ServerSocketChannel server = (ServerSocketChannel)key.channel();
        //获得和客户端连接的通道
        SocketChannel channel = server.accept();
        System.out.println("isOpen -> "+channel.isOpen());
        System.out.println("isConnected -> "+ channel.isConnected());
        channel.configureBlocking(false);
        //在这里可以给客户端发送消息哦
        System.out.println("新的客户端连接");
        //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限
        channel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

    }

    /**
     * 处理读的事件
     * @param key
     * @throws Exception
     */
    public void handlerRead(SelectionKey key) throws Exception{
        System.out.println(key);
        //服务器可读取消息：得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println(key+"服务端收到信息："+msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);//将消息回送给客户端

    }

    public void handlerWrite(SelectionKey key) throws Exception{
        System.out.println(key);

    }



    /**
     * 启动非服务端测试
     */
    public static void main(String[] args) throws Exception{
        NioServer server = new NioServer();
        server.initServer(8000);
        server.listen();
    }



}
