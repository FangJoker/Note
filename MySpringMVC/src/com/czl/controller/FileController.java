package com.czl.controller;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.*; //文件上传包

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
	public ModelAndView Upload(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request)throws IOException{ //使用@RequestParam注解传入参数"file"， "file"与表单的文件name属性应该相同。 传入CommonsMultipartFile 文件解析器
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
			return new ModelAndView("/success", "file", "找不到文件");
		}
	
	}
	
	@RequestMapping("/toUpload")
	public String toUpload(){
		return "/upload";	
	}

	@RequestMapping(value ="/upload2" ,method=RequestMethod.POST)
	public ModelAndView Upload2(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException{
		CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());  //加载文件解析器，传入配置信息
		if(multipartResolver.isMultipart(request)){  //判断request里是否有文件
			MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request; //将request转换为MultipartHttpServletRequest
			Iterator<String>  iter = multiRequest.getFileNames(); //启用迭代器获取文件名
			 while(iter.hasNext()){   //遍历文件
				 MultipartFile file = multiRequest.getFile((String)iter.next());  //取得文件
				  if(file != null){
					  String fileName = "Upload" + file.getOriginalFilename(); //文件名
					  String path = "C:/Users/59859/Workspaces/MyEclipse 2016 CI/MySpringMVC/WebRoot/images/" + fileName;      //储存路径
					  File localFile = new File(path);
					  file.transferTo(localFile);    //利用SpringMVC自带的方法上传文件到本地
				  }
			 }  
		}
		return new ModelAndView("/success", "file","文件上传成功！");
	}
	
}
