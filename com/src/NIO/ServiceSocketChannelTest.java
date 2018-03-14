package com.ajie.testNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 
 * 
 */
public class ServiceSocketChannelTest {
	/** 标识数字 */
	private int flag = 0;
	/** 缓冲区大小 */
	private int BLOCK = 4096;
	/** 发送缓冲区 */
	private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/** 接收缓冲区 */
	private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	/** 管理channel的选择器 */
	private Selector selector;
	
	public ServiceSocketChannelTest(int port) throws IOException{
		//打开服务器套接字通道 
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//设置为非阻塞
		serverSocketChannel.configureBlocking(false);
		//检测与此通道关联的套接字
		ServerSocket serverSocket = serverSocketChannel.socket();
		//服务端口的绑定
		serverSocket.bind(new InetSocketAddress(port));
		//得到一个selector
		selector = Selector.open();
		//注册serverSocketChannel到selector
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server Start..............");
	}
	
	//监听
	public void listen() throws IOException{
		while(selector.select()>0){
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while(iterator.hasNext()){
				SelectionKey key = iterator.next();
				iterator.remove();
				handleKey(key);
			}
		}
	}
	
	//处理请求
	public void handleKey(SelectionKey key) throws IOException{
		ServerSocketChannel server = null;
		SocketChannel client  = null;
		String receiveText;
		String sendText;
		int count = 0;
		//测试此键通道是否已经准备好接收新的套接字连接
		if(key.isAcceptable()){
			//返回为之创建的此键通道
			server = (ServerSocketChannel) key.channel();
			//接收此通道套接字的连接 如果有 返回套接字通道 且将处于阻塞模式
			client = server.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
		}else if(key.isReadable()){
			//返回为之创建主键的通道
			client = (SocketChannel)key.channel();
			//清空缓冲区 以被下次使用
			receivebuffer.clear();
			//读取服务器发来的数据到缓冲区
			count = client.read(receivebuffer);
			if(count>0){
				receiveText = new String(receivebuffer.array(), 0, count);
				System.out.println("服务器接收客户端的数据： "+receiveText);
				client.register(selector, SelectionKey.OP_WRITE);
			}
		}else if(key.isWritable()){
			//将缓冲区清空 以被下次使用
			sendbuffer.clear();
			client = (SocketChannel)key.channel();
			sendText = "message from server："+flag++;
			//想缓冲区写入数据
			sendbuffer.put(sendText.getBytes());
			sendbuffer.flip();
			System.out.println("服务端想客户端发送数据："+sendText);
			client.register(selector, SelectionKey.OP_READ);
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		int port = 9999;
		ServiceSocketChannelTest server = new ServiceSocketChannelTest(port);
		server.listen();
	}
	
}
