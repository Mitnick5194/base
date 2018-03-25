package com.ajie.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 传统的客户端 不知为什么 当线程超过500后会连接不上 不知是windows系统的连接数限制还是jvm 我想应该是windows系统的问题
 * 
 * @author Mitnick
 *
 */
public class TranditionalClient {

	Socket socket = null;
	OutputStream outputStream = null;
	InputStream inputStream = null;

	public TranditionalClient() {
		socket = new Socket();

	}

	public void connect() {
		try {
			socket.connect(new InetSocketAddress("127.0.0.1", 9999));
			// 服务端睡眠了 也能到这一步 所以说上面的connect不是阻塞的
			// 但是服务端不会处理请求 下面的逻辑就是阻塞的了
			System.out.println("客户端连接成功");
			outputStream = socket.getOutputStream();
			String info = "我是客户端";
			outputStream.write(info.getBytes());
			inputStream = socket.getInputStream(); // 会阻塞 直到有东西返回
			byte bytes[] = new byte[2014];
			inputStream.read(bytes);
			System.out.println("服务器回应:" + new String(bytes));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger();
		// 只用主线程创建1000个连接线程 这里执行到一定时期会报错 不能连接上 因为连接数有限
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					TranditionalClient client = new TranditionalClient();
					client.connect();
				}
			}, count.incrementAndGet() + "").start();
			;
		}
	}

}
