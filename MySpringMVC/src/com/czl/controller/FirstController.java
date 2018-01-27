package com.czl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public ModelAndView getPost(String Name){   //POST传入的参数直接传入该函数
		String result ="userName is "+Name;
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
}
