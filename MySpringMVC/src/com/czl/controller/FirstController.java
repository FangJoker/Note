package com.czl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@RequestMapping("/person")
	public String toPerson(String name,double age){
	    System.out.println(name+" "+age);
	    return "helloworld";
	}
	
}
