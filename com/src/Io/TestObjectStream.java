package com.ajie.testIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 
 * 读入了完整的一个类 方法和属性都会保存
 * 
 * @author 
 * 
 */
public class TestObjectStream {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		OutputStream os = new FileOutputStream(new File("/home/mitnick/test.txt"));
		User u1 = new User("hello1","gd",23);
		Book b1 = new Book("平凡的世界", "路遥", 199f);
		User u2 = new User("hello2",24);
		Book b2 = new Book("红楼梦", "曹雪芹", 99.8f);
		Book b3 = new Book("天龙八部", "金庸", 199.9f);
		User u3 = new User("hello3","gd3",25);
		
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(u1);
		oos.writeObject(u2);
		oos.writeObject(u3);
		oos.writeObject(b1);
		oos.writeObject(b2);
		oos.writeObject(b3);
		oos.close();
		os.close();
		InputStream is = new FileInputStream(new File("/home/mitnick/test.txt"));
		ObjectInputStream ois = new ObjectInputStream(is);
		User uu1 = (User)ois.readObject();
		User uu2 = (User)ois.readObject();
		User uu3 = (User)ois.readObject();
		Book bb1 = (Book)ois.readObject();
		Book bb2 = (Book)ois.readObject();
		Book bb3 = (Book)ois.readObject();
		
		System.out.println(uu1.toString());
		System.out.println(uu2.toString());
		System.out.println(uu3.toString());
		System.out.println(bb1.toString());
		System.out.println(bb2.toString());
		System.out.println(bb3.toString());
		
		uu1.sayHello(); //方法也是可行的
		String address = uu1.getAddress();
		System.out.println(address);
		ois.close();
		is.close();
	}

}

class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private int age;

	public User(String name, String address, int age) {
		this.name = name;
		this.address = address;
		this.age = age;
	}
	
	

	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public User() {
	}

	public void sayHello(){
		System.out.println("hello world");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString(){
		return "姓名："+name+" 年龄："+age+" 住址："+address;
	}

}

class Book implements Serializable{
	private static final long serialVersionUID = 1L;
	String bookName;
	String author;
	float price;
	public Book(String bookName, String author , float price){
		this.bookName = bookName;
		this.author = author;
		this.price = price;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String toString(){
		return "书名："+bookName+" 作者："+author+" 价格"+price;
	}
	
	
	
}
