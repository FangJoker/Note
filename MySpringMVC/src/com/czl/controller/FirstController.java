package com.czl.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public ModelAndView getPost(String userName){  //POST����Ĳ���ֱ�Ӵ���ú���
		String result ="userName is "+userName;
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
}
