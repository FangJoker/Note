package com.czl.controller;

public class myException extends Exception {
	   myException(){
		   //调用父类Exception的带参构造方法，该参数为异常信息字符串
		   super("SpringMVC异常");
	   }
}