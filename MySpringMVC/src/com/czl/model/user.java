package com.czl.model;

public class user {
    private String name;
    private int age;
    private String sex;
    //ÿ�����Զ�Ҫ�� get...��set...����
	public String getName(){     //Name ����ı��������name����һ��
		return name;
	}
	public void setName(String name){   
		this.name = name;
	}
	public int getAge(){         //get �����Ҳ����ͱ���name����һ��
		return age;
	}
	public void setAge(int age){
		this.age = age;
		
	}
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex =sex;
	}
	
}
