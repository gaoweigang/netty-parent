package com.netty.oio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOExample {

    public static void main(String[] args) throws IOException {
        int portNumber = 8888; //端口号
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("阻塞，等待客户端的连接！");
        Socket clientSocket = serverSocket.accept();//阻塞，等待客户端的连接
        System.out.println("客户端连接成功！");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        System.out.println("阻塞，等待客户端输入数据");
        while((request = in.readLine()) != null){//阻塞，等待客户端输入数据
            System.out.println("客户端输入："+request);//打印出客户端的输入
            if("done".equals(request)){
                break;
            }
        }
        response = "success";
        out.println(response);//给客户端响应数据
    }
}
