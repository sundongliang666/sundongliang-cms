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
	function page(page){
		$("[name='page']").val(page);
		$("form").submit();
	}
</script>
<body>
 <!-- 导航条 -->
 
<%--  <jsp:include page="common/header.jsp"></jsp:include>  --%>
 
 <center>
 <div style="width: 1500px;height: 500px;">
 	<table class="table">
 		<tr>
 			<td>
 				<form action="list">
 				<input type="hidden" name="page">
 				<input type="text" name="key" value="${key}"><input type="submit" value="搜索">
 				</form>
 			</td>
 		</tr>
 		<c:forEach items="${info.list }" var="art">
 			<tr>
 				<td>
 					<a href="/article/detail?id=${art.id}" target="_blank"><font size="5">${art.title}</font></a>
 				</td>
 			</tr>
 		</c:forEach>
 		<tr>
 			<td>耗时：${haoshi}毫秒</td>
 		</tr>
 		<tr>
 			<td>
 				<center>
 					<input type="button" onclick="page(${info.pageNum-1>1?info.pageNum-1:1})" value="上一页">
 					<input type="button" onclick="page(${info.pageNum+1<info.pages?info.pageNum+1:info.pages})" value="下一页">
 				</center>
 			</td>
 		</tr>
 	</table>
 
 </div>
 </center>
 
 
 
 
 
 
 
 
 
 
 
 
</body>
</html>