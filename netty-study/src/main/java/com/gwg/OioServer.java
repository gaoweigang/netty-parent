package com.gwg;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ��ͳ��Socket�����
 * @author gaoweigang
 *
 */
public class OioServer {
	
	public static void main(String[] args) throws IOException {
		
		ExecutorService service = Executors.newCachedThreadPool();
		//����socket���񣬼���10101�˿�
		ServerSocket server = new ServerSocket(10101);
		System.out.println("������������");
	    Socket[] socketArr = new Socket[2];
		final Socket socket = server.accept();
		for(int i = 0; i< socketArr.length; i++){
			//��ȡһ���׽���(����)
			socketArr[i] = socket;
			System.out.println("����һ���µ� �ͻ��ˣ�, socket:"+socket);
			if(i == 1){
				System.out.println("�����СΪ2......");
				if(socketArr[0] == socketArr[1]){//ÿ����һ���µĻػ����� ���´���һ��Socket
					System.out.println("�ж��ǲ���ÿ��һ���µĻػ����ᴴ��һ���µ�Socket");
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
				//��ȡ����(����)
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
				System.out.println("socket�ر�");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
