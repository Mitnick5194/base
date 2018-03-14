package com.ajie.testNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 
 * 
 */
public class ClentChannel {

	/** 标识数字 */
	private static int flag = 0;
	/** 缓冲区大小 */
	private static  int BLOCK = 4096;
	/** 发送缓冲区 */
	private static ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/** 接收缓冲区 */
	private static ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	/** 服务器地址 */
	private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress("127.0.0.1",9999);
	
	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		Selector selector = Selector.open();
	//	SocketChannel.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(SERVER_ADDRESS);
        Set<SelectionKey> selectionKeys;  
        Iterator<SelectionKey> iterator;  
        SelectionKey selectionKey;  
        SocketChannel client;  
        String receiveText;  
        String sendText;  
        int count=0;
       
        while(selector.select()>0){
        
        	selectionKeys = selector.selectedKeys();
        	iterator = selectionKeys.iterator();
        	while(iterator.hasNext()){
        		selectionKey = iterator.next();
        		if(selectionKey.isConnectable()){
        			System.out.println("client connect");
        			client = (SocketChannel) selectionKey.channel();
        			if(client.isConnectionPending()){
        				client.finishConnect();
        				System.out.println("连接完成");
        				sendbuffer.clear();
        				sendbuffer.put("Hello,Server".getBytes());
        				sendbuffer.flip();
        				client.write(sendbuffer);
        			}
        		}else if(selectionKey.isReadable()){
        			client = (SocketChannel) selectionKey.channel();
        			receivebuffer.clear();
        			count = client.read(receivebuffer);
        			while(count>0){
        				receiveText = new String(receivebuffer.array(),0,count);
        				System.out.println("客户端接收服务器端数据："+receiveText);
        				client.register(selector, SelectionKey.OP_WRITE);
        			}
        		}else if(selectionKey.isWritable()){
        			sendbuffer.clear();
        			client  = (SocketChannel) selectionKey.channel();
        			sendText = "message from client: "+(flag++);
        			sendbuffer.put(sendText.getBytes());
        			sendbuffer.flip();
        			client.write(sendbuffer);
        			System.out.println("客户端想服务器段发送数据："+sendText);
        			client.register(selector, SelectionKey.OP_READ);
        		}
        		selectionKeys.clear();
        	}
        	
        }
		
	}
	
}
