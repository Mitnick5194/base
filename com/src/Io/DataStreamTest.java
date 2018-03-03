package com.ajie.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * DataOutputStream DataInputStream的使用
 * 
 * DataOutputStream源码中的writeUTF(String str)方法、在写入真正的String str之前会先写入两个字节、用来表示这个字符串的长度
 * 
 * 所以在调用readUTF()方法时 能区分字符串的分割位置
 * 
 * 
 * @author niezhenjie
 * 
 */
public class DataStreamTest {

	public static void main(String[] args) throws IOException {
		OutputStream out = new FileOutputStream("/home/mitnick/test.txt");
		DataOutputStream dos = new DataOutputStream(out);
		dos.writeInt(1);
		dos.write(2);
		dos.writeBoolean(true);
		dos.writeByte(15);
		dos.writeUTF("世界你好，世界和平");
		dos.writeUTF("hello world!!!");
		dos.writeUTF("Java才是世界上最好的语言hahahahahaha");
		dos.close();
		//注意 读出的顺序应该 上面的读入顺序严格一致 
		InputStream in = new FileInputStream("/home/mitnick/test.txt");
		DataInputStream dis = new DataInputStream(in);
		int readInt = dis.readInt();
		System.out.println(readInt);
		int read = dis.read();
		System.out.println(read);
		boolean readBoolean = dis.readBoolean();
		System.out.println(readBoolean);
		byte readByte = dis.readByte();
		System.out.println(readByte);
		String s1 = dis.readUTF();
		System.out.println(s1);
		String s2 = dis.readUTF();
		System.out.println(s2);
		String s3 = dis.readUTF();
		System.out.println(s3);
		dis.close();
		
	}

}
