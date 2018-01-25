# PHP-Laravel5.~ #
## 控制器 ##

控制器位于*app\Htpp\Controllers*目录下
 里面有一个“鼻祖控制器” 也就是我们所有创建的控制器的父类
首先，在创建控制器前，先在该目录下创建两个文件夹“Admin” 与“Home”

这里拿Admin模块的来说

在"Admin目录下创建TextController"

     <?php

     \\注意命名空间Admin
     namespace App\Http\Controllers\Admin;
     
     \\注意引入鼻祖Controller!
     use App\Http\Controllers\Controller;

     class TextController extends Controller
     {
       public function hello(){
     	echo "Hello";
     }
    
    } 





## 路由模式 ##
laravel 所有的访问都要进行路由的配置
laravel的路由需要在 *app\Http\routes.php*中进行配置

### 两种最普通的路由配置 ###

 第一种模式，是直接使用function 访问
    
     Route::get('你想要的路由名字', function () {
    //转到视图模板
     return view('welcome');
    });


第二种模式， 为控制器配置路由
  **Route::get('路由名字','Controller@方法');**
  
示例：Route::get('hello','TextController@hello');

### 路由的传参 ###

这里先列出比较常用的



-  必选参数

有时我们需要在路由中捕获 URI 片段。比如，要从 URL 中捕获用户 ID，需要通过如下方式定义路由参数
    
    Route::get('user/{id}', function ($id) {
        //该方法是返回参数 $id值得就是user后面的参数id，但是一般建议$id赋初始值
        return 'User '.$id;
    });
   
  


- 可选参数
  
只需要在参数后面加个’?‘就可以啦！

    Route::get('user/{id?}', function ($id=0) {
       //这里的id 一般建议赋初始值
        return 'User '.$id;
    });


   
### 高级路由 ###



访问上述的Admin模块下的TextController 可以对这个路由进行命名  使用数组键 as 指定路由名称

    Route::get('hello'['as'=>'profile','uses'=>'Admin\TextController@hello']);

首先 我们得把同一个模块的路由用路由组归类

    Route::group(['namespace' => 'Admin','prefix'=> 'Admin'],function(){
    
    // 控制器在 "App\Http\Controllers\Admin" 命名空间下 Admin为前缀，

    //以下是配置的路由
    
    Route::get('hello','Admin\TextController@hello');


    });

![](http://i.imgur.com/22XRZiR.png)

### 中间件 ###
**
创建中间件**

第一步 
在 *app\Http\Middleware*创建文件 AdminLogin.php
    <?php

    namespace App\Http\Middleware;

    use Closure;

    class AdminLogin
    {
      public function handle($request, Closure $next)
       {
       
        echo "my middleware ";
        return $next($request);
       }
    }

第二步 
在 *app\Http\Kernel.phpe*中的

    protected $routeMiddleware = [
      
        
       ];
    }

加入'Admin.Login'=> \App\Http\Middleware\AdminLogin::class,

规则是 ：
**'中间件名称'=> \App\Http\Middleware\'中间件文件名'::class,**

下面来使用我们创建的中间件

    Route::group(['namespace' => 'Admin','prefix'=> 'Admin' 'middleware'=>['web','Admin.Login' ] ],function(){
    
    // 控制器在 "App\Http\Controllers\Admin" 命名空间下 Admin为前缀，使用了 web 和Admin.Login中间件   
   
    //以下是配置的路由
    
    Route::get('hello','Admin\TextController@hello');


    });

![](http://i.imgur.com/sT4SZp8.png)

**注意 如果要使用session 必须用web 中间件**

## 视图 ##

视图文件位于 *resources\views*目录下

- 命名方式

**文件命名方式 均以 Controllername.blade.php**形式命名！

 ### 控制器调用视图 ###

在*resources\views*目录下 创建自己的HTML视图文件
myView.blade.php 

在*app\Http\Controller\Admin* 下的TextController下添加方法 
    
    public function myView(){
    
     return view('MyView');
  	
     }

**配置路由  Route::get('myview','Admin\TextController@myView');**

就简单完成了 路由到控制器 控制器调用视图的 模式
![](http://i.imgur.com/1lUTt1Z.png)

### 控制器对视图的传参 ###

使用with 方法

    public function myView(){
   
    $name="ZHBITV";
     return view('MyView')->with('TV',$name);

    }

在myView.blade.php中 用试着原生php 语句输出参数

   <div class="title"><?php echo $TV;?></div>

完成传参 
![](http://i.imgur.com/TooiCtd.png)

### 控制器对视图多参数传参 ###

这里感觉有点像AJAX。。觉得类似
用data 进行参数封装。如下

    public function myView(){
   
    $data=[  
       'TV'=>"ZHBITV",
       'Name'=>'QiaoH'


    ];
      //这里就不需要使用with方法了。
      return view('MyView',$data);

    }

然后视图输出方面是没有改变。（为了避免与markDownPad的标签冲突 就把h2用空格隔开了）

            <div class="title"><?php echo $TV;?></div>
                <h 2 ><?php echo $Name;?></h 2 >

![](http://i.imgur.com/to1V5FM.png)

### 混合传参 ###

精彩会遇到不同类型的数据，总不能一起封装的。
尔compact（）方法就解决了这个问题。
直接上代码
    public function myView(){
   
    $data=[  
       'TV'=>"ZHBITV",
       'Name'=>'QiaoH'

    ];
     $yf="研发部";
  	   return view('MyView',compact('data','yf'));

    }

**视图方面就要有所改变了**
data封装好的就使用数组的形式。

       <div class="content">
                <div class="title"><?php echo $data['TV'];?></div>
                <h 2><?php echo $data['Name'];?></h 2>
                <p><?php echo $yf; ?></p>
         </div>

![](http://i.imgur.com/06c3EqB.png)

## Blade模板引擎的使用 ##

###  Blade模板引擎在视图上的简单使用 ###

回到上面的代码，用blade模板引擎代替原生php语句，会让代码书写的更加简洁。

在TextController下的方法

    public function myView(){
   
    /*$data=[  
       'TV'=>"ZHBITV",
       'Name'=>'QiaoH'

    ];*/

    $data = array(
       'TV'=>"ZHBITV",
       'Name'=>'QiaoH'

     );

    $yf="研发部";
  	return view('MyView',compact('data','yf'));

    }

**使用Blade模板引擎在视图上输出**

    <div class="container">
            <div class="content">
                <div class="title">{{ $data['TV'] }}</div>
                <h 2>{{$data['Name'] }}</h 2>
                <p>{{$yf}}</p>
            </div>
        </div>

效果是一样的

- Blade模板引擎的注释

**只要使用'@'就可以了。**

    <div class="container">
            <div class="content">
                <div class="title">{{ $data['TV'] }}</div>
               //使用@
                <h 2> @{{$data['Name'] }}</h 2>
                <p>{{$yf}}</p>
            </div>
        </div>

![](http://i.imgur.com/2Djz0GP.png)

- 当传到模板的的参数为Null或者不存在的处理方式

直接上示例代码

先在控制器 把$yf 变量注释掉

    <div class="container">
            <div class="content">
                <div class="title">{{ $data['TV'] }}</div>
                <h 2>{{$data['Name'] }}</h2>
               //三元表达式解决
                <p>{{isset($yf)? $yf: '无'}}</p>
            </div>
        </div>

![](http://i.imgur.com/RKEL2xT.png)

### Blade 模板引擎相关语法 ###

### if的使用 ###
 
直接看示例代码

控制器里面有这样一个参数 $nm=60;

 <h 2>@if($nm<60) 不及格 @else 及格 @endif </h2>

**注意@endif！**

![](http://i.imgur.com/qvUFdXl.png)

### foreach的使用 ###

控制器有数组data

    $data = array(
       'TV'=>"ZHBITV",
       'Name'=>'QiaoH',
       1=>"1st",
       2=>"2nd",
       3=>"3rd"

     );

在视图模板输出

<h 2>@foreach($data as $k=>$v) {{$v}} @endforeach</h2>

两者嵌套使用

     <h2>
            @foreach($data as $k=>$v) 
                @if($k>1)
                    {{$v}}
                     @else
                      null
                  @endif
             @endforeach
    </h2>

### 引入公公部分 ###

示例代码：
//引用View\commom\header.balde.php
@include('common.header');

@include 的传参

示例例代码
//对视图模板传入page参数，视图使用时候按照blade模板基本语法使用，即{{$page}}
@include('common.header',['page'=>"首页"]);

### 模板的继承 ###

在*resources\views*下创立一个layouts文件夹（个人喜好。不是必要）

在*resources\views\layouts*文件夹新建立一个被继承的模板**layouts.balde.php**

    <!DOCTYPE html>
    <html lang="en">
    <head>
	<meta charset="UTF-8">
	<title>layouts</title>
    <style type="text/css">

    .header{
    	 width: 100%;
    	 height: 100px;
    	 background: red;
    }

    .middle{
    	 width: 100%;
    	 height: 500px;
    	 background: blue;
    }

    .footer{
    	 width: 100%;
    	 height: 100px;
    	 background: pink;
    }

    </style>

    </head>
    <body>
	<div class="header">
		头部呢容
	</div>
    //使用yield标记'content'内容
	@yield('content')

	<div class="footer">尾部内容</div>
	
    </body>
    </html>


同目录下新建extends.balde.php

示例代码如下
    @extends('layouts.layouts')

    //使用section
    @section('content')
    <div class="middle">中间内容</div>
    @endsection

配置一下路由完成了

![](http://i.imgur.com/XqB4bUz.png)

### Env文件及其常量的设置 ###
    //开发相关配置
     APP_ENV=local
     APP_DEBUG=ture
     APP_KEY=FPg9LRkHTj5j14ig7ETzQhG29r5sEWNR
    
    //数据库相关配置
    DB_HOST=127.0.0.1
    DB_DATABASE=homestead
    DB_USERNAME=homestead
    DB_PASSWORD=secret
    DB_PREFIX=Think
    
    //驱动相关配置
    CACHE_DRIVER=file
    SESSION_DRIVER=file
    QUEUE_DRIVER=sync
    
    //linux相关配置
    REDIS_HOST=127.0.0.1
    REDIS_PASSWORD=null
    REDIS_PORT=6379
   
    //邮件相关配置
    MAIL_DRIVER=smtp
    MAIL_HOST=mailtrap.io
    MAIL_PORT=2525
    MAIL_USERNAME=null
    MAIL_PASSWORD=null
    MAIL_ENCRYPTION=null


这里拿配置数据表的前缀做个例子即

DB_PREFIX=Laravel

然后来到database.php下 找到connections下的mysql
添加一行'prefix'    => env('DB_PREFIX', ''),
**这里就是使用了env（）这个函数。所以整个常熟配置很好了解**

再在控制器使用config（）函数即可。 

**config('database.connections.mysql.prefix');**

### 数据库的连接 ###

在env文件配置好数据库信息后，就可以使用 DB门面的table方法，选择一张表。

## get方法 ##

使用DB门面的table方法，table方法为给定表返回一个查询构建器，允许你在查询上链接更多约束条件并最终返回查询结果。在本例中，我们使用get方法获取表中

示例代码

    <?php

    namespace App\Http\Controllers;

    use DB;
    use App\Http\Controllers\Controller;

    class UserController extends Controller{
    /**
     * 显示用户列表
     *
     * @return Response
     */
      public function index()
       {
        $users = DB::table('users')->get();

        return view('user.index', ['users' => $users]);
       }
    }

get方法返回结果集的数组，其中每一个结果都是PHP对象的StdClass实例。所以可以像访问对象的属性一样访问列的值：

    foreach ($users as $user) {
    echo $user->name;
    }

### first方法 ###

只是想要从数据表中获取一行数据，可以使用first方法，该方法将会返回单个StdClass对象：

     $user = DB::table('users')->where('name', 'John')->first();
     echo $user->name;

可以使用value方法从结果中获取单个值，该方法会直接返回指定列的值：

    $email = DB::table('users')->where('name', 'John')->value('email');

### list方法 ###

 如果想要获取包含单个列值的数组，可以使用lists方法

    public function extend(){
  
    $admin=DB::table('admin')->lists('Admin_id');
     //dd方法打印
    dd($admin);
  
    }
![](http://i.imgur.com/8qQdCoV.png)

### 插入insert方法 ###

    DB::table('users')->insert(
    ['email' => 'john@example.com', 'votes' => 0]);

如果数据表有自增ID，使用insertGetId方法来插入记录将会返回ID值：

    $id = DB::table('users')->insertGetId(
    ['email' => 'john@example.com', 'votes' => 0]
 
    );

### 删除delete方法 ###

    DB::table('users')->delete();

    DB::table('users')->where('votes', '<', 100)->delete();

### 连接where的or方法 ###

可以通过方法链将多个where约束链接到一起，也可以添加or子句到查询，orWhere方法和where方法接收参数一样：

      $users = DB::table('users')
                    ->where('votes', '>', 100)
                    ->orWhere('name', 'John')
                    ->get();

# Java-Spring MVC4.~ #

##部署Spring MVC##

###使用myEclipse配置Spring###



- **新建 web project**

![](https://i.imgur.com/rE6OTgf.png)

- **引入 Spring 包**


**右键项目目录，在project facets 中引入Spring 框架**


![](https://i.imgur.com/C7EFBtd.png)
63

- **配置**

Web.xml的配置

    <?xml version="1.0" encoding="UTF-8"?>  
    <web-app version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"  
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee   
        http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_1504272490903">  
        <!--configure the setting of springmvcDispatcherServlet and configure the   
            mapping -->  
        <servlet>  
            <servlet-name>springmvc</servlet-name>  /*配置servelt的名字*/
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>   /*框架基础类的包*/
            <init-param>  
                <param-name>contextConfigLocation</param-name>  
                <param-value>classpath:springmvc-servlet.xml</param-value>  /*配置springmvc-servelt*/
            </init-param>  
            <!-- <load-on-startup>1</load-on-startup> -->  /*1表示设置一启动容器就初始化此servelt*/ 
        </servlet>  
        <servlet-mapping>  /*URL拦截标记 srpingmvc*/
            <servlet-name>springmvc</servlet-name>  
            <url-pattern>/</url-pattern>   /* /表示拦截所有URL*/
        </servlet-mapping>  
 </web-app>  


- **配置SRC/applicationContext.xml**
  
	    <?xml version="1.0" encoding="UTF-8"?>
	    <beans
		xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:p="http://www.springframework.org/schema/p"
		xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.1.xsd">
	    
	     <!-- 设定控制器所在的包 -->  
	        <context:component-scan base-package="com.czl.controller" />  
	        <!-- 不操作的静态资源 -->  
	        <mvc:default-servlet-handler />  
	        <!-- 如果使用注解必须配置以下内容 -->  
	         <mvc:annotation-driven />  
	     
	        <!--视图解析-->
			<bean  
       			class="org.springframework.web.servlet.view.InternalResourceViewResolver"  
	            id="internalResourceViewResolver">  
	            <!-- 前缀 -- 配置视图的目录>  
	            <property name="prefix" value="/WEB-INF/pages/" />  
	            <!-- 后缀 -->  
	            <property name="suffix" value=".jsp" />  
	        </bean>  
	
	     </beans>
**改名为web.xml 里面配置的 springmvc-servlet.xml**

1. 接着在 SRC 目录下新建 包 com.czl.controller

2. 在WEB-INF目录下新建pages 存放视图目录

## hello world 输出 ##

控制器FirstController代码
 
		package com.czl.controller;
		
		import org.springframework.stereotype.Controller;
		import org.springframework.ui.Model;
		import org.springframework.web.bind.annotation.RequestMapping;
		
		@Controller  
		@RequestMapping("/MyController")  //访问标记
		
		public class FirstController {
		    
			@RequestMapping("/hello")  
		    public String hello(Model model) {  
		        model.addAttribute("greeting", "Hello QiaoH welcome to Spring MVC");  
		        return "helloworld";    //返回helloworld.jsp
		    }  
		}

helloworld.jsp 代码
		
		 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		    pageEncoding="ISO-8859-1"%>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>my first Srping MVC </title>
		</head>
		<body>
		    <h1>${greeting}</h1>  
		</body>
		</html>

**在控制台的Server下启动服务器 Tomact ，右键 Add Deploments 加入项目 MySpring**

在浏览器 输入
>  http://localhost:8080/MySpring/MyController/hello


![](https://i.imgur.com/XyyDIGp.png)


**应该注意，在给定的 URL 中，MySpring 是这个应用程序的名称，并且 MyController 是我们在控制器中使用 @RequestMapping("/MyController") 提到的虚拟子文件夹, hellos是FirstController下 hello的方法 注解@RequestMapping("/hello"），这里和以往的PHP框架 Controller/functionName 不太一样**


## 静态资源访问 ##
 

##1. 配置原因 ##
 
由于我们在 
> web.xml 

 下配置了  <url-pattern>/</url-pattern>,所以就算是请求静态资源，例如图片，css,js等也会被拦截。所以我们需要做 静态文件的映射配置

## 静态文件映射配置 ##

1.**在  springmvc-servlet.xml 作出修改**
 
  		 <!--静态资映射源配置-->
          <mvc:resources location="/images" mapping="images/**"/>
          <mvc:resources location="/js" mapping="js/**"/>
          <mvc:resources location="/css" mapping="css/**"/>         
         <!--静态资源映射配置-->

![](https://i.imgur.com/MGmfD0C.png)

2.**在WebRoot下创建对应目录**

![](https://i.imgur.com/2BqUetY.png)

最后便可以在jsp文件中引入静态文件，例如 : `<img src="../images/a.jpg">`

![](https://i.imgur.com/76vsr5D.png)



## 常用注解 ##
### @Controller ###
　　负责注册一个bean 到spring 上下文中
### @RequestMapping ###
　　注解为控制器指定可以处理哪些 URL 请求
### @RequestBody ###
　　该注解用于读取Request请求的body部分数据，使用系统默认配置的HttpMessageConverter进行解析，然后把相应的数据绑定 到要返回的对象上 ,再把HttpMessageConverter返回的对象数据绑定到 controller中方法的参数上
　### @ResponseBody ###
　　该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区
### @ModelAttribute 　
　　在方法定义上使用 @ModelAttribute 注解：Spring MVC 在调用目标处理方法前，会先逐个调用在方法级上标注了@ModelAttribute 的方法
　　在方法的入参前使用 @ModelAttribute 注解：可以从隐含对象中获取隐含的模型数据中获取对象，再将请求参数 –绑定到对象中，再传入入参将方法入参对象添加到模型中
### @RequestParam###
　　在处理方法入参处使用 @RequestParam 可以把请求参 数传递给请求方法
### @PathVariable ###
　　绑定 URL 占位符到入参
### @ExceptionHandler ###
　　注解到方法上，出现异常时会执行该方法
### @ControllerAdvice ###
　　使一个Contoller成为全局的异常处理类，类中用@ExceptionHandler方法注解的方法可以处理所有Controller发生的异常
## 传递参数 ##

首先得找到MyEclipse Tomcat -config的server.xml
将在 < Connector  connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" / >后面加上 URIEncoding="UTF-8"。 这样是为了页面向服务器传递参数的编码也修改为utf-8，编码POST过去是中文乱码

### GET ###
控制器:
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public ModelAndView getTest(){
		String result ="this is get test";
		return new ModelAndView("/show", "result", result);
	}
	
JSP:
	
	<body>
    < h1 >注解的使用< h1 >
    <br/>
     < h2>${result}< /h2>
	</body>
	</html>


![](https://i.imgur.com/hiUIAHS.png)

### POST ###
	@RequestMapping(value="/post", method=RequestMethod.POST)
	public ModelAndView getPost(String userName){
		String result ="userName is "+userName;
		return new ModelAndView("/show", "result",result);
	}

![](https://i.imgur.com/SI8DYup.png)
![](https://i.imgur.com/x2Ndx2J.png)

### 自动装箱 ###
新建包com.czl.model<br>
新建 user类
![](https://i.imgur.com/LTIWefl.png)
	package com.czl.model;

	public class user {
    private String name;
    private int age;
    private String sex;
    //每个属性都要有 get...和set...方法
	public String getName(){     //Name 后面的必须与表单的name属性一样
		return name;
	}
	public void setName(String name){   
		this.name = name;
	}
	public int getAge(){         //get 后面的也必须和表单的name属性一样
		return age;
	}
	public void setAge(int age){
		this.age = age;
		
	}
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex =sex;
	}
	
	}
**JSP:**
<br>表单部分：
	
	<form action="/MySpringMVC/MyController/post2" method="post">
	</br> Name:
	<input name="Name" >
	</br> Age:
	<input name="Age">
	</br> Sex：
	<input name="Sex">
	<button type="submit">提交</button>
	</form>
<br>
<br>展示数据部分：
	 
     < h1 >注解的使用< /h1>
     <br/>
     < h2>${result[0]}</ h2>
     <br/>
     < h2>${result[1]}</ h2>
     <br/>
     < h2>${result[2]}</ h2>

![](https://i.imgur.com/4sIIHYW.png)

**controller:**<br>
	
	@RequestMapping(value="/post2", method=RequestMethod.POST)
	public ModelAndView getPost2 (user user){  //传入 user类 自动装箱
		List userList = new ArrayList();
		userList.add(user.getName());
		userList.add(user.getAge());
		userList.add(user.getSex());
		return new ModelAndView("/show", "result",userList);  //传入数组实现传入多个参数
	}
**传参效果：**<br>
![](https://i.imgur.com/zlCUJEV.png)