package com.ajie.test;

/*
 * 基本类型包括：byte,short,int,long,char,float,double,Boolean,returnAddress，
*  引用类型包括：类类型，接口类型和数组
 * 
 *1. java的基本数据类型是传值调用，对象引用类型是传引用(即传地址 形参和实参指向同一个内存地址)。
 * 
 * 2.当传值调用时，改变的是形参的值，并没有改变实参的值，实参的值可以传递给形参，但是，这个传递是单向的，形参不能传递回实参。
 * 
 * 3.当引用调用时，如果参数是对象，无论对对象做了何种操作，都不会改变实参对象的引用(见changeString例子)，但是如果改变了对象的内容，就会改变实参对象的内容。
 * 
 * @author niezhenjie
 * 
 */
public class TestCanShu {

	public static void main(String[] args) {
		TestCanShu tcs = new TestCanShu();
		// 对象引用
		byte[] b = new byte[16];
		tcs.change(b);
		for (byte c : b) {
			System.out.print(c);
		}
		System.out.println();
		System.out.println("================================");
		int[] i = new int[8];
		tcs.changeInt(i);
		for (int j : i) {
			System.out.print(j);
		}
		System.out.println();
		System.out.println("================================");
		Message mes = new Message("hello world");
		System.out.println(mes.getContent());
		tcs.changeMessage(mes);
		System.out.println(mes.getContent());
		System.out.println("================================");
		String s = "hello i am String";
		tcs.changeString(s);
		/*输出hello i am String 证明changeString方法里没有改变s 因为String是只想常量池里的常量
		 *  所以改变String的值就以为这改变了String的指向（地址）
		 */
		System.out.println(s);

	}

	// 形参是对象 属于引用对想传递 函数体内对形参的内容操作操作 实参的内容也会做相应的改变
	public void change(byte[] buf) {
		if (null == buf || buf.length == 0) {
			buf = new byte[16];
		}
		buf[10] = 10;
		buf[11] = 11;
		buf[12] = 12;
	}

	public void changeInt(int[] i) {
		i[1] = 2;
		i[2] = 3;
	}

	// 这也是对象 同上
	public void changeMessage(Message mes) {
		mes.setContent("世界 你好");
	}

	public void changeString(String str) {
		str = "new String";
	}

}

class Message {
	private String content;

	public Message(String c) {
		content = c;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
