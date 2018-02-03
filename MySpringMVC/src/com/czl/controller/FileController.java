package com.czl.controller;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.*; //�ļ��ϴ���

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/File")
public class FileController {
   
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public ModelAndView Upload(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request)throws IOException{ //ʹ��@RequestParamע�⴫�����"file"�� "file"������ļ�name����Ӧ����ͬ�� ����CommonsMultipartFile �ļ�������
		if(!file.isEmpty()){
			try{
				SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
				FileOutputStream os = new FileOutputStream("C:/Users/59859/Workspaces/MyEclipse 2016 CI/MySpringMVC/WebRoot/images/" + df.format(new Date()) + file.getOriginalFilename());
				InputStream in = file.getInputStream();
				int b =0;
				while((b=in.read()) != -1){
					os.write(b);
				}
				os.flush();
				os.close();
				in.close();				
			}catch (FileNotFoundException e) {				
				e.printStackTrace();
			}
			return new ModelAndView("/success", "file", file.getOriginalFilename());
		}else{
			return new ModelAndView("/success", "file", "�Ҳ����ļ�");
		}
	
	}
	
	@RequestMapping("/toUpload")
	public String toUpload(){
		return "/upload";	
	}

	@RequestMapping(value ="/upload2" ,method=RequestMethod.POST)
	public ModelAndView Upload2(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException{
		CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());  //�����ļ�������������������Ϣ
		if(multipartResolver.isMultipart(request)){  //�ж�request���Ƿ����ļ�
			MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request; //��requestת��ΪMultipartHttpServletRequest
			Iterator<String>  iter = multiRequest.getFileNames(); //���õ�������ȡ�ļ���
			 while(iter.hasNext()){   //�����ļ�
				 MultipartFile file = multiRequest.getFile((String)iter.next());  //ȡ���ļ�
				  if(file != null){
					  String fileName = "Upload" + file.getOriginalFilename(); //�ļ���
					  String path = "C:/Users/59859/Workspaces/MyEclipse 2016 CI/MySpringMVC/WebRoot/images/" + fileName;      //����·��
					  File localFile = new File(path);
					  file.transferTo(localFile);    //����SpringMVC�Դ��ķ����ϴ��ļ�������
				  }
			 }  
		}
		return new ModelAndView("/success", "file","�ļ��ϴ��ɹ���");
	}
	
}
