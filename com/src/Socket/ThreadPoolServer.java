package com.ajie.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {
	
	public static void main(String[] args) {
		ThreadPoolServer server = new ThreadPoolServer();
		server.listener();
	}

	ExecutorService threadPool = null;
	ServerSocket socket = null;

	public ThreadPoolServer() {
		/*threadPool = Executors.newCachedThreadPool();*/
		threadPool = Executors.newFixedThreadPool(10);
		try {
			socket = new ServerSocket(9999);
			System.out.println("服务器在9999端口侦听");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listener() {
		try {
			while(true){
				Socket s = socket.accept(); // 阻塞等待连接
				/*
				 * 下面这种写法 每次请求过来 当前线程要处理完所以的以下业务 此时不能再接受其他的线程请求 所以是单线程的 现实中很少用到
				 * InetAddress inet = s.getInetAddress();
				 * System.out.println("有一个客户端成功连接:"
				 * +inet.getHostAddress()+"  "+inet.getHostName()); InputStream
				 * inputStream = s.getInputStream(); byte[] bytes = new byte[1024];
				 * inputStream.read(bytes); System.out.println("接收到客户端的信息："+new
				 * String(bytes)); OutputStream outputStream = s.getOutputStream();
				 * outputStream.write("服务端回复客户端".getBytes());
				 */
				// 从线程池中获取一个线程 处理上述的业务
				Work work = new Work(s); // 把socket交给其他线程
				// 到这里 线程基本完成了它的任务 因为它把任务丢给其他的线程处理了 此时 该线程可以接受其他的请求 这个请求在也跟他没关系了
				threadPool.execute(work);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
