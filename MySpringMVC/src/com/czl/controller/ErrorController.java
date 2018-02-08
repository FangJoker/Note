package com.czl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;



@ControllerAdvice
@Controller
public class ErrorController {
   
	 @ExceptionHandler
	 public ModelAndView exceptionHandler(Exception ex){
	        ModelAndView mv = new ModelAndView("errorAll");
	        mv.addObject("exception", ex);
	        System.out.println("È«¾ÖÒì³£");
	        return mv;
	 }
	
}
