package com.ajie.testNIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * 利用NIO包下的对象实现文件复制
 * 
 * @author 
 * 
 */
public class NIOCopyFile {
	
	public static void main(String[] args) throws IOException {
		FileInputStream is = new FileInputStream(new File("/home/mitnick/test2.txt"));
		FileChannel inChannel = is.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		FileOutputStream os = new FileOutputStream(new File("/home/mitnick/test3.txt"));
		FileChannel outChannel = os.getChannel();
		while(true){
			buf.clear();
			int n = inChannel.read(buf);
			if(-1==n){
				break;
			}
			buf.flip();
			outChannel.write(buf);
		}
		is.close();
		os.close();
		
	}

}
