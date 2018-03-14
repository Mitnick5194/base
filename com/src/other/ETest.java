package com.ajie.fanxing;


/**
 * @author 
 * 
 */
public class ETest{
	public static void main(String[] args) {
		FanX<User> f = new FanX<User>();
	/*	User user = f.getElement(); //user为null;
		System.out.println(user.getAge()); //抛空指针异常
		*/
		User u = new User();
		u.sayHello(); //继承是实例化了子类 父类也会随着实例化
	}
}
class  FanX<E>{
	E ele; //由始至终都没有实例化 所以在调用getElement返还的E其实是null
	public E getElement(){
		return ele;
	}
}
class User extends Person{
	public String name;
	public int age;
	public User(){
		this.name = "ajie";
		this.age = 24;
	}
	public String getName(){
		return name;
	}
	public int getAge(){
		return age;
	}
}
class Person{
	public void sayHello(){
		System.out.println("hello world");
	}
}
