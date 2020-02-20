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
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js"></script>
<script src="https://cdn.bootcss.com/popper.js/1.14.7/umd/popper.min.js"></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
</head>
<style>
		.form{background: rgba(255,255,255,0.2);width:400px;margin:100px auto;}
</style>
<script type="text/javascript">
</script>
<body>
	<div class="form row">  
			<form:form action="login" modelAttribute="user" method="post" class="form-horizontal col-sm-offset-3 col-md-offset-3  form" id="register_form">
					
				<h3 class="form-title">登入你的帐户</h3>				
					<div class="col-sm-9 col-md-9">					
						<div class="form-group">	<!-- placeholder="用户名" -->					
							<i class="fa fa-user fa-lg">用户名</i>${eror}<br>						
							<form:input   path="username" />
							<form:errors path="username"></form:errors>
						</div>					
						<div class="form-group">							
							<i class="fa fa-lock fa-lg">输入你的密码</i>							
							<form:password  path="password"/>					
							<form:errors path="password"></form:errors>
							<form:errors path="id"></form:errors>
						</div>
						<div class="form-group">							
							<i class="fa fa-lock fa-lg"><input type="checkbox" name="ck" value="2">是否记住</i>							
						</div>								
						<div class="form-group">						
								<input type="submit" class="btn btn-success pull-right" value="登录"/>		
								<a href="/user/register"><font color="red">没有账户，还不快去注册</font></a>
						</div>				
					</div>			
				</form:form>		
			</div>	
				
			


   
</body>
</html>
<!-- <div class="form-group">							
							<i class="fa fa-check fa-lg"></i>							
							<input class="form-control required" type="password" placeholder="确认你的密码" name="rpassword"/>					
						</div>					
						<div class="form-group">							
							<i class="fa fa-envelope fa-lg"></i>							
							<input class="form-control eamil" type="text" placeholder="邮箱" name="email"/>					
						</div> -->	