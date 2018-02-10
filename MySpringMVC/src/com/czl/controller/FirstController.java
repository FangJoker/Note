package com.czl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.czl.model.*;


@Controller  
@RequestMapping("/MyController")  //访问标记

public class FirstController {
	 
	@RequestMapping("/hi")  
    public String hello(Model model) {  
        model.addAttribute("greeting", "Hello QiaoH ");  
        return "helloworld";   
    }
	
	@RequestMapping("/form")
	public String form(Model model){
		return "form";
	}
		
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public ModelAndView getTest(){
		String result ="this is get test";
		return new ModelAndView("/show", "result", result);
	}
	
	@RequestMapping(value="/post", method=RequestMethod.POST)
	public ModelAndView getPost(String Name,HttpSession session){   //POST传入的参数直接传入该函数
		String result ="userName is "+Name;
		//在Session里保存信息  
        session.setAttribute("username", Name);  
		return new ModelAndView("/show", "result",result);
	}
	
	@RequestMapping(value="/post2", method=RequestMethod.POST)
	public ModelAndView getPost2 (user user){  //POST传入的参数直接传入该函数
		
		List userList = new ArrayList();
		userList.add(user.getName());
		userList.add(user.getAge());
		userList.add(user.getSex());
		return new ModelAndView("/show", "result",userList);
	}
	
	
	@RequestMapping(value="/ajax", method=RequestMethod.POST)
	public void getPost3 (user user, HttpServletResponse response, String Name){  //POST传入的参数直接传入该函数
		String json	="{\"Name\" :\" "+Name+"\",\"Age\":\" "+user.getAge()+"\", \"Sex\":\" "+user.getSex()+"\"}";  //拼接json字符串
		PrintWriter out = null;
		response.setContentType("application/json");
		
		try{         	 //将json输出到Jsp页面全程捕获IOExcetion
			out = response.getWriter();  
			out.write(json);  //将json写入到字符输出流
		}catch (IOException e){
			e.printStackTrace();
		}
	}
   
   //RESTFul风格
   @ResponseBody
   @RequestMapping(value="/userInfo/{id}" ,method=RequestMethod.GET)
   public user userInfo(@PathVariable("id") int id){ //接收URL参数id
	   user u = new user();
	   u.setAge(20);
	   u.setName("巧");
	   u.setSex("男");
	   u.setId(id);
	   return u;               //返回user对象信息（自动转为json）	  
   }
   
   @ResponseBody
   @RequestMapping(value="/getUserInfo" ,method=RequestMethod.POST)
   public user getUserInfo(user u){ //接收URL参数id
	   u.setMsg("查询结果：用户Id:"+u.getId());
	   return u;               //返回user对象信息（自动转为json）	  
   }
   
   
   
   //创建处理异常的类,这个类会处理当前控制器下的myException这个异常
   @ExceptionHandler(myException.class)
   public ModelAndView exceptionHandler(Exception ex){
       ModelAndView mv = new ModelAndView("error");  
       System.out.println("in testExceptionHandler");  //处理异常的code
       System.out.println(ex.getMessage());        //异常信息
       return mv;          //返回error.jsp 视图
   }
   //局部异常   
   @RequestMapping("/error")
   public String error()throws myException {
       if(true){
    	   throw new myException();
       }
       return "exception";
   }
   
   //全局异常
   @RequestMapping("/errorAll")
   public String errorAll() {
       int i = 5/0;
       return "hello";
   }
   
}
