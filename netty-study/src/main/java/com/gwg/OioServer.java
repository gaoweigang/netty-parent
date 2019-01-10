package com.gwg;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统的Socket服务端
 * @author gaoweigang
 *
 */
public class OioServer {
	
	public static void main(String[] args) throws IOException {
		
		ExecutorService service = Executors.newCachedThreadPool();
		//创建socket服务，监听10101端口
		ServerSocket server = new ServerSocket(10101);
		System.out.println("服务器启动！");
	    Socket[] socketArr = new Socket[2];
		final Socket socket = server.accept();
		for(int i = 0; i< socketArr.length; i++){
			//获取一个套接字(阻塞)
			socketArr[i] = socket;
			System.out.println("来了一个新的 客户端！, socket:"+socket);
			if(i == 1){
				System.out.println("数组大小为2......");
				if(socketArr[0] == socketArr[1]){//每次来一个新的回话都会 重新创建一个Socket
					System.out.println("判断是不是每来一个新的回话都会创建一个新的Socket");
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
