<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>沦落人-${article.id}</title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js" ></script>
<link href="/resource/bootstrap-4.3.1/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	</nav>
	<div class="container">
		<div class="row justify-content-center" >
			<h3>${article.title}</h3>
		</div>
		<div class="row justify-content-center">
			<h5>
			作者：${article.user.username} &nbsp;&nbsp;&nbsp;
			栏目：${article.channel.name}  &nbsp;&nbsp;&nbsp;
			分类：${article.category.name}&nbsp;&nbsp;&nbsp;
			发表时间：<fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/> &nbsp;&nbsp;&nbsp;
			浏览次数：${article.hits+1}
			</h5>
			
		</div>
		<div style="margin-top:30px">
			${article.content}
		</div>
		<div>
			<nav aria-label="...">
					  <ul class="pagination">
					    <li class="page-item ">
					      <input type="button" class="btn btn-primary" onclick="pagearticle(${article.id-1},${article.id})" value="上一篇">
					    </li>
					    <li class="page-item">
					    	<input type="button" class="btn btn-primary" onclick="pagearticle(${article.id+1},${article.id})" value="下一篇">
					    </li>
					    <%-- <li class="page-item">
					    	<input type="button" class="btn btn-primary" style="color:red;" onclick="ssss(${article.id})" value="举报">
					    </li> --%>
					  </ul>
					</nav>
		</div>
	
		<div>
			发布评论
			<textarea rows="5" cols="160" id="commentText">
				
			</textarea>
			<input type="button" class="btn btn-primary" onclick="addComment()" value="发表评论">
		</div>
		<div id="comment">
			
		</div>
		</div>
	<script type="text/javascript">
	function ssss(id){
		
		 location="/article/complain?articleId="+id;
		
	}
	
		function pagearticle(id,articleid){
			//alert(id)
			//alert(articleid)
			$.post("/article/pagearticle",{id:id,articleid:articleid},function(msg){
				//alert(JSON.stringify(msg))
				if(msg.code==1){
					location="/article/detail?id="+msg.data;
				}else{
					alert(msg.error)
				}
			},"json")
		}
		
		function gopage(page){
			showComment(page)
		}
		
		function showComment(page){
			$("#comment").load("/article/comments?id=${article.id}&page="+page)
		}
		
		$(document).ready(function(){
			// 显示第一页的评论
			showComment(1)
		})
		
		function addComment(){
			alert($("#commentText").val());
			
			 $.post("/article/postcomment",
					{articleId:'${article.id}',content:$("#commentText").val()},
				function(msg){
					if(msg.code==1){
						alert('发布成功')
						$("#commentText").val("");
						showComment(1);
					}else{
						alert(msg.error)
					}
					
				},
				"json") 
		}
	</script>
	
	
<%-- <div class="modal fade"   id="articleContent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document" ><!-- style="margin-left:100px;" -->
    <div class="modal-content" style="width:300px;" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">投诉</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body ">
         	<div class="col-sm-9 col-md-9">					
			<div class="form-group">
		   <label >文章标题</label><br> ${article.title}<input type="hidden" name="articleId" value="${article.id}">
		  	</div>										
				<div class="form-group">
		   <label >投诉类型</label>
		    <select name="complainType" >
		    	<option value="0">请选择</option>
		    	<option value="1">政治敏感</option>
		    	<option value="2">反社会</option>
		    	<option value="3">涉毒</option>
		    	<option value="4">涉黄 </option>
		    </select>
		     
		 </div>
		 <div class="form-group">
		   <label >投诉类型</label><br>
		   		<input type="checkbox" name="compainOption" value="1"> 标题夸张<br>
		   		 <input type="checkbox" name="compainOption" value="2">与事实不符 <br>
		   		 <input type="checkbox" name="compainOption" value="3"> 疑似抄袭<br>
		 	 
		  </div>		
		  <div class="form-group">
		   <label >内容</label>
		   	<textarea name="content" cols="30" rows="3"></textarea>
		   	 
		  </div>
		  			
			</div>		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="sss()">提交</button>
      </div>
    </div>
  </div>
</div> --%>

</body>
</html>