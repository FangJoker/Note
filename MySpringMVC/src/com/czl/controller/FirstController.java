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
@RequestMapping("/MyController")  //���ʱ��

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
	public ModelAndView getPost(String Name,HttpSession session){   //POST����Ĳ���ֱ�Ӵ���ú���
		String result ="userName is "+Name;
		//��Session�ﱣ����Ϣ  
        session.setAttribute("username", Name);  
		return new ModelAndView("/show", "result",result);
	}
	
	@RequestMapping(value="/post2", method=RequestMethod.POST)
	public ModelAndView getPost2 (user user){  //POST����Ĳ���ֱ�Ӵ���ú���
		
		List userList = new ArrayList();
		userList.add(user.getName());
		userList.add(user.getAge());
		userList.add(user.getSex());
		return new ModelAndView("/show", "result",userList);
	}
	
	
	@RequestMapping(value="/ajax", method=RequestMethod.POST)
	public void getPost3 (user user, HttpServletResponse response, String Name){  //POST����Ĳ���ֱ�Ӵ���ú���
		String json	="{\"Name\" :\" "+Name+"\",\"Age\":\" "+user.getAge()+"\", \"Sex\":\" "+user.getSex()+"\"}";  //ƴ��json�ַ���
		PrintWriter out = null;
		response.setContentType("application/json");
		
		try{         	 //��json�����Jspҳ��ȫ�̲���IOExcetion
			out = response.getWriter();  
			out.write(json);  //��jsonд�뵽�ַ������
		}catch (IOException e){
			e.printStackTrace();
		}
	}
   
   //RESTFul���
   @ResponseBody
   @RequestMapping(value="/userInfo/{id}" ,method=RequestMethod.GET)
   public user userInfo(@PathVariable("id") int id){ //����URL����id
	   user u = new user();
	   u.setAge(20);
	   u.setName("��");
	   u.setSex("��");
	   u.setId(id);
	   return u;               //����user������Ϣ���Զ�תΪjson��	  
   }
   
   @ResponseBody
   @RequestMapping(value="/getUserInfo" ,method=RequestMethod.POST)
   public user getUserInfo(user u){ //����URL����id
	   u.setMsg("��ѯ������û�Id:"+u.getId());
	   return u;               //����user������Ϣ���Զ�תΪjson��	  
   }
   
   
   
   //���������쳣����,�����ᴦ��ǰ�������µ�myException����쳣
   @ExceptionHandler(myException.class)
   public ModelAndView exceptionHandler(Exception ex){
       ModelAndView mv = new ModelAndView("error");  
       System.out.println("in testExceptionHandler");  //�����쳣��code
       System.out.println(ex.getMessage());        //�쳣��Ϣ
       return mv;          //����error.jsp ��ͼ
   }
   //�ֲ��쳣   
   @RequestMapping("/error")
   public String error()throws myException {
       if(true){
    	   throw new myException();
       }
       return "exception";
   }
   
   //ȫ���쳣
   @RequestMapping("/errorAll")
   public String errorAll() {
       int i = 5/0;
       return "hello";
   }
   
}
