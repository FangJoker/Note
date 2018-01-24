package com.czl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller  
@RequestMapping("/MyController")  //∑√Œ ±Íº«

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
	public ModelAndView getPost(String userName){
		String result ="userName is "+userName;
		return new ModelAndView("/show", "result",result);
	}
	
	
}
