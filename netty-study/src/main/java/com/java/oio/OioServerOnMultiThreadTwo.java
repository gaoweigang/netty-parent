package com.java.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统socket服务端
 * 证明：每次一个新的连接到来，accept()都会创建一个新的socket
 * @author gaoweigang
 *
 */
public class OioServerOnMultiThreadTwo {
	
	public static void main(String[] args) throws IOException {
		
		ExecutorService service = Executors.newCachedThreadPool();
		//创建socket服务，监听10101端口
		ServerSocket server = new ServerSocket(10101);
		System.out.println("服务启动！");
	    Socket[] socketArr = new Socket[2];//创建一个Socket数组，用于存放两次连接请求到来时创建的两个socket
		for(int i = 0; i< socketArr.length; i++){
			final Socket socket = server.accept();//阻塞，等待一个新的连接到来
			System.out.println("创建了一个新的链接,i = "+ i +" socket:"+socket);
			socketArr[i] = socket;
			if(i == 1){
				System.out.println("比较两次连接请求到来时创建的socket是否相等");
				if(socketArr[0] == socketArr[1]){//
					System.out.println("相同");
				}else{
					System.out.println("不同");
				}
			}
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
