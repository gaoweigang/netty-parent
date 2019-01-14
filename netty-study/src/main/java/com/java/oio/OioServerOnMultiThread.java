package com.java.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统socket服务端——多线程版
 * @author gaoweigang
 *
 */
public class OioServerOnMultiThread {
	
	public static void main(String[] args) throws IOException {
		
		ExecutorService service = Executors.newCachedThreadPool();
		//创建socket服务，监听10101端口
		ServerSocket server = new ServerSocket(10101);
		System.out.println("服务启动！");
	    while(true){
			final Socket socket = server.accept();//阻塞，等待一个新的连接到来
			System.out.println("来了一个新的客户端！");
			service.submit(new Runnable() {
				public void run() {
					handler(socket);
					
				}
			});
		}
		
	}
	public static void handler(Socket socket){
		
		try {
			byte[] bytes = new byte[1024];
			InputStream inputStream = socket.getInputStream();
			
			while(true){
				//读取数据(阻塞)
				int read = inputStream.read(bytes);
				if(read != -1){
					System.out.println(new String(bytes));
					
				}else{
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				System.out.println("socket关闭");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
