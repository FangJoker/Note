package com.czl.controller;

import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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

}
