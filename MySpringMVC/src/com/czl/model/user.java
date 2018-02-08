package com.czl.model;

public class user {
    
	private String name;
    private int age;
    private String sex;
    private int id;
    private String msg;
   
	public String getName(){     //Name 后面的必须与表单的name属性一样
		return name;
	}
	public void setName(String name){   
		this.name = name;
	}
	public int getAge(){         //get 后面的也必须和表单的name属性一样
		return age;
	}
	public void setAge(int age){
		this.age = age;
		
	}
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setMsg(String msg){
		this.msg = msg;
	}
	public String getMsg(){
		return this.msg;
	}
}
