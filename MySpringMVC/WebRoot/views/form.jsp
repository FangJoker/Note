<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Form</title>

</head>
<body>

	</br> Name:
	<input name="Name" id="Name">
	</br> Age:
	<input name="Age" id="Age">
	</br> Sex：
	<input name="Sex" id="Sex">
	 <br> Id:
	 <input name="Id" id="Id">
	<button type="button"  OnClick="submit2()">提交</button>
	
	
<script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>	
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript">

      function submit(){
        var Name = $("#Name").val();
         var Age  = $("#Age").val();
         var Sex  = $ ("#Sex").val();
         
          $.ajax({
              url: "/MySpringMVC/MyController/ajax",
              type : 'json',
              method : 'post',
              data : {'Age': Age, 'Name': Name, 'Sex':Sex},
              success:function(data){   
                 console.log(data);
                 console.log(Name);
              },
              error :function(data){
                 console.log("error");
              }

          });
      }
      
      function submit2(){
        var id = $("#Id").val();
         
          $.ajax({
              url: "/MySpringMVC/MyController/getUserInfo",
              type : 'json',
              method : 'post',
              data : {'Id':id},
              success:function(data){   
                 console.log(data);
                 alert(data.msg);          
              },
              error :function(data){
                 console.log("error");
              }

          });
      }
</script>
</body>
</html>
