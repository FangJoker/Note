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
     * Handlerִ�����֮������������ 
     */  
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("afterCompletion");
	}

	@Override
    /** 
     * Handlerִ��֮��ModelAndView����֮ǰ����������� 
     */  
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object Obj, ModelAndView MV)
			throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("postHandle");
	}

	@Override
	/** 
     * Handlerִ��֮ǰ����������� 
     */  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Obj) throws Exception {
		//��ȡ�����URL  
        String url = request.getRequestURI(); 
        if( url.indexOf("/form")>=0){
        	return true;
        }
        //��ȡSession  
        HttpSession session = request.getSession();  
        String username = (String)session.getAttribute("username");  
          
        if(username != null){  
            return true;  
        }  
        System.out.println("preHandle");
        //ת������
        request.getRequestDispatcher("/MyController/form").forward(request, response);  
        //�ض���
        //response.sendRedirect("/MyController/form");

		return false;
	}
     
}
