<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/resource/bootstrap-4.3.1/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js" ></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
</head>
<script type="text/javascript">
	function add(){
		var data =$("form").serialize();
		alert(data)
		$.post("/article/addsc",data,function(msg){
			if(msg==true){
				alert("添加成功")
				location="/user/home";
			}else{
				alert("添加失败")
			}
		},"json")
	}
</script>
<body>
		<form >
        <table class="table">
        	<tr>
        		<td>文本</td>
        		<td><input type="text" name="text"></td>
        	</tr>
        	<tr>
        		<td>地址</td>
        		<td><input type="text" name="url"></td>
        	</tr>
        	<tr>
        		<td colspan="2">
        		<input type="button" onclick="add()" value="添加">
        		</td>
        	</tr>
        </table>
        </form>
</body>
</html>