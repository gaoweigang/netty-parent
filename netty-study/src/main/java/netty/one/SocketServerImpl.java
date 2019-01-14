package netty.one;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketServerImpl {

    public static void main(String[] args) throws IOException {
        //java随机切换
        Random random = new Random();
        int port =random.nextInt(9999 - 1000 + 1) + 1000;
        System.out.println("监听端口号："+ port);
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        System.out.println("客户端连接成功");
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        while(true){
            int count = inputStream.read(bytes);
        }

    }
}
