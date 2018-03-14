package com.ajie.testNIO;

import java.nio.ByteBuffer;

/**
 * @author 
 * 
 */
public class ByteBufferTest {

	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(10);
		for (int i = 0, len = buf.capacity(); i < len+10; i++) {
			buf.put((byte) i); // 向缓存中添加数据
			if(i==9){
				buf.clear();
			}
		}
		buf.position(3); // 将位置指针移到第四个元素
		buf.limit(7);// 限制读取到第7位
		// 从原buf中获取一个片段 注意 这不是真正意义上的复制
		// 它与原来的buf公用一个底层数组 哪个改变了底层数组的值 相应的都会改变
		ByteBuffer slice = buf.slice();
		for (int i = 0, len = slice.capacity(); i < len; i++) {
			byte b = slice.get(i); // 从缓存ByteBuffer底层的byte数组中获取一个元素
	//		System.out.println(b);
		}
		//改变复制片段的缓存的值
		for (int i = 0, len = slice.capacity(); i < len; i++) {
			byte b = slice.get(i); 
			b += 10; //每个值都加10
			slice.put(i, b);
		}
		//重设position 和capacity的值
		buf.position(0);
		buf.limit(buf.capacity());
		//查看原来的buf的值
		for(int i=0,len=buf.capacity();i<len;i++){
			//其实这是绝对定位 get会直接取缓存底层数据的坐标 所以不会设计到position的移动 但get()函数会移动position
			byte b = buf.get(i); 
	//		System.out.println(b);
		}
		buf.put((byte)125);
		buf.put((byte)125);
		buf.put((byte)125);
		buf.put((byte)125);
		System.out.println(buf.capacity());
		
	}

}
