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
	function pages(key,li,page,pages){
		alert(page)
		
	}
</script>
<body>
     <jsp:include page="common/header.jsp"></jsp:include>
     <center>
     	<div style="width: 500px;height: 500px;">
     	<table>
     		<c:forEach items="${pageinfo.list}" var="art">
     		<tr>
     			<td style="width: 500px;">
     			<a href="/article/detail?id=${art.id}" target="_blank" >${art.title}</a>
     			</td>
     		</tr>
     		</c:forEach>
     		<tr>
     			<td>
     			${pageinfo.pageNum}${pageinfo.pages}
     				耗时：${time}s
     			</td>
     		</tr>
     		 <tr>
     			<td>
     				<center>
     				<input type="button" onclick="pages(${key},1,${pageinfo.pageNum},1)" value="上一页">
     				<input type="button" onclick="pages(${key},2,${pageinfo.pageNum},${pageinfo.pages})" value="下一页">
     					<%--  <button onclick="page(${key},1,${pageinfo.pageNum},1)">上一页</button>
     					<button onclick="page(${key},2,${pageinfo.pageNum},${pageinfo.pages})">下一页</button>  --%>
     				</center>
     			</td>
     		</tr> 
     	</table>
     	</div>
     </center> 
     	
     	  
</body>
</html>