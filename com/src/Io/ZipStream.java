package com.ajie.testIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * 压缩文件
 * 
 * @author 
 * 
 */
public class ZipStream {

	public static void main(String[] args) throws IOException {
		File f = new File("/home/mitnick/test.csv");
		File f2 = new File("/home/mitnick/test4.zip");
		ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(f2));
		zipout.putNextEntry(new ZipEntry(f.getName()));
		InputStream in = new FileInputStream(f);
		byte[] buf = new byte[1024*5];
		int n = 0;
		while ((n = in.read(buf)) != -1) {
			zipout.write(buf, 0, n);
		}
	/*	String s = "hello world";
		byte[] bytes = s.getBytes();
		zipout.write(bytes);
		for(int i=0;i<bytes.length;i++){
			System.out.println((char)bytes[i]);
		}*/
		in.close();
		zipout.close();

	}

}
