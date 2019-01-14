package netty.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketClientImpl {


    public static void main(String[] args) throws IOException {
        //初始化socket
        Socket socket = new Socket();
        //初始化远程连接地址
        SocketAddress remote = new InetSocketAddress("127.0.0.1", 8888);
        //建立连接
        socket.connect(remote);
    }
}
