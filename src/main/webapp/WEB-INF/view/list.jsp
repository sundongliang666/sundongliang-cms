<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="css/css.css">
</head>
<script type="text/javascript">
	function qx(){
		$(".ck").attr("checked",true);
	}
	function fx(){
		$(".ck").each(function(){
			this.checked=!this.checked;
		})
	}
	
	function delAll(){
		var ids = $(".ck:checked").map(function(){
		  return this.value;
		}).get().join();
		if(!ids){
			alert("请选择您要删除的数据")
			return;
		}
		if(confirm("您确定要删除ID为"+ids+"的数据吗?")){
			$.post("delAll.do",{ids:ids},function(obj){
				if(obj>0){
					alert("删除成功");
					location="list.do";
				}else{
					alert("删除失败");
				}
			},"json")
		}
	}
	function add(){
		location="add.jsp";
	}
	function toUpdate(id){
		location="update.jsp?id="+id;
	}
</script>
<body>
	<form action="list.do" method="post">
		姓名:<input type="text" name="name">
		性别:<select name="gender">
			<option value="">请选择</option>
			<option value="男">男</option>
			<option value="女">女</option>
		</select>
		<input type="submit" value="查询">
		<input type="button" value="批删" onclick="delAll()">
		<input type="button" value="添加" onclick="add()"> 
		<table>
			<tr>
			  <th>
			  	<input type="button" value="全选" onclick="qx()">
			  	<input type="button" value="反选" onclick="fx()">
			  </th>
			  <th>ID</th>
			  <th>姓名</th>
			  <th>性别</th>
			  <th>省市</th>
			  <th>申请注册单位</th>
			  <th>所学专业</th>
			  <th>申请注册专业1</th>
			  <th>申请注册专业2</th>
			  <th>执业资格证书</th>
			  <th>注册号</th>
			  <th>有效期</th>
			  <th>编辑</th>
			</tr>
			<c:forEach items="${list}"  var="e">
			
			<tr>
			  <td>
			  	<input type="checkbox" class="ck" value="${e.id}">
			  </td>
			  <td>${e.id }</td>
			  <td>${e.name}</td>
			  <td>${e.gender}</td>
			  <td>${e.city}</td>
			  <td>${e.institution}</td>
			  <td>${e.master_major}</td>
			  <td>${e.profession.pname}</td>
			  <td>${e.profession.pname}</td>
			  <td>${e.certificate_no}</td>
			  <td>${e.reg_no}</td>
			  <td>${e.indate}</td>
			  <td>
			  	<input type="button" value="修改" onclick="toUpdate(${e.id})">
			  </td>
			</tr>
		
			</c:forEach>
			<tr>
				<td colspan="100">
					总条数:${page.total}
					<button value="1" name="pageNum">首页</button>
					<button value="${page.prePage==0?1:page.prePage}" name="pageNum">上一页</button>
					<button value="${page.nextPage==0?page.pages:page.nextPage}" name="pageNum">下一页</button>
					<button value="${page.pages}" name="pageNum">尾页</button>
					${page.pageNum}/${page.pages}
				</td>
			</tr>
		</table>

	</form>

</body>
</html>