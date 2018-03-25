package com.ajie.socket;

/**
 * 到了600多个线程时 就报错了 应该是系统的端口限制问题
 * @author Mitnick
 *
 */
public class ThreadPoolClient {

	//客户端不用改变 直接使用传统的就行
	public static void main(String[] args) {
		//使用当前的主线创建一个客户端
		/*TranditionalClient client = new TranditionalClient();
		client.connect();//连接*/
		
		//使用主线程创建50个线程 每个线程就是一个客户端
		for(int i=0;i<80;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					TranditionalClient client = new TranditionalClient();
					client.connect();
				}
			}).start();
		}
}
}
