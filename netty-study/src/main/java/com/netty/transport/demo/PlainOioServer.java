package com.netty.transport.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 不使用Netty的OIO
 */
public class PlainOioServer {
    public void serve(int port) throws IOException{
        final ServerSocket socket = new ServerSocket(port);
        for(;;){
            final Socket clientSocket = socket.accept();//阻塞，等待客户端连接
            System.out.println("Accepted connection from "+clientSocket);

            new Thread(new Runnable() {
                public void run() {
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));//向客户端发送数据Hi
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }

    }
}
