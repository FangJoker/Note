package com.czl.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.czl.model.user;

public  class MyInterceptor implements HandlerInterceptor{

	@Override
	 /** 
     * Handler执行完成之后调用这个方法 
     */  
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("afterCompletion");
	}

	@Override
    /** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */  
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object Obj, ModelAndView MV)
			throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("postHandle");
	}

	@Override
	/** 
     * Handler执行之前调用这个方法 
     */  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Obj) throws Exception {
		//获取请求的URL  
        String url = request.getRequestURI(); 
        if( url.indexOf("/form")>=0){
        	return true;
        }
        //获取Session  
        HttpSession session = request.getSession();  
        String username = (String)session.getAttribute("username");  
          
        if(username != null){  
            return true;  
        }  
        System.out.println("preHandle");
        //转发请求
        request.getRequestDispatcher("/MyController/form").forward(request, response);  
        //重定向
        //response.sendRedirect("/MyController/form");

		return false;
	}
     
}
