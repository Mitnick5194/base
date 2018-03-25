package com.ajie.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 传统的socket连接 一个请求一个线程
 * 
 * @author Mitnick
 *
 */
public class TranditionalServer {
	public static void main(String[] args) {
		// 使用主线程让服务端跑起来
		TranditionalServer server = new TranditionalServer();
		server.listener();
	}

	ServerSocket serverSocket = null;
	Thread thread = null;
	AtomicInteger count = new AtomicInteger();
	OutputStream outputStream = null;

	public TranditionalServer() {
		try {
			System.out.println("服务器正在9999端口侦听");
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void listener() {
		while (true) {
			try {
				Socket accept = serverSocket.accept();
				//这种做法不好 因为客户端拿到连接后还要处理下面的业务 在处理一下业务的时候 因为占用着线程 所以不能连接下一个进来 
				//应该拿到了socket 立刻把创建一个新的线程 然后再把socket丢给该线程处理 接着进入下一个循环监听下一个连进来的幸运儿
				
				//试一下让线程休眠 看看能不能处理下一个连接
				try {
					Thread.sleep(0000); //只能睡眠时间到 处理完当前线程的任务 才能接受下个请求
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String hostAddress = accept.getInetAddress().getHostAddress();
				String host = accept.getInetAddress().getHostName();
				System.out.println("侦听到一个连接 地址：" + hostAddress);
				// 为这个连接开启一个线程 处理请求任务
				Work work = new Work(host, hostAddress,
						accept.getInputStream(), accept.getOutputStream());
				// 每次一个连接 开启一个线程 这样非常消耗系统资源（线程的创建销毁和上下文切换都销毁资源和时间）
				thread = new Thread(work, "线程" + count.incrementAndGet());
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

//这里不用锁吧 以为每次调用这个任务 都会new一个实例对象 没存在多线程访问
class Work implements Runnable {

	protected String host;
	protected String address;
	protected InputStream stream;
	protected OutputStream out;
	byte bytes[] = new byte[1024];

	public Work(String host, String address, InputStream stream,
			OutputStream out) {
		this.host = host;
		this.address = address;
		this.stream = stream;
		this.out = out;
	}

	public Work(Socket s) {
		
		if (null == s) {
			return;
		}
		InetAddress inetAddress = s.getInetAddress();
		this.host = inetAddress.getHostName();
		this.address = inetAddress.getHostAddress();
		try {
			this.stream = s.getInputStream();
			this.out = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			//这里睡眠不会影响连接 上面的连接 因为已经将业务交给这些线程处理了 所以连接线程可以处理下个连接
			//所以客户端50个连接在上面瞬间完成 而这里10秒后 50个线程处理工作也是在瞬间完成
			Thread.sleep(10000); 
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("线程 【" + Thread.currentThread() + "】正在处理来自" + host
				+ "(" + address + ")的请求");
		if (null != stream) {
			System.out.println("拿到输入流" + stream.toString());
		}
		try {
			stream.read(bytes);
			System.out.println("收到客户端的请求数据:" + new String(bytes));
			// 回复
			out.write("客户端你好 我已经接受到你的请求并处理完成 这是给你的回复".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
