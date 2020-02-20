<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- <div class="container-fluid"> -->
	<table class="table">
		<!-- articlePage -->
		<c:forEach items="${pageInfolink.list}" var="in" varStatus="count">
		<c:if test="${count.count%4==1}"><tr></c:if>
		<td><a href="${in.url}">${in.name}</a></td>
		<c:if test="${count.count%4==0}"></tr></c:if>
		
		</c:forEach>	
      </table>
      
    
      <%-- <nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item">
		      <a class="page-link" href="#"  onclick="gopage(1)">首页</a>
		    </li>
		    <li class="page-item">
		    <a class="page-link" href="#" onclick="gopage(${articlePage.prePage==0?1:articlePage.prePage})">上一页</a>
		    </li>
		    <c:forEach begin="${articlePage.pageNum-2>=1?articlePage.pageNum-2:1}" end="${articlePage.pageNum+2>=articlePage.pages?articlePage.pages:articlePage.pageNum+2}" varStatus="index">
					    	<!-- 当前页码的处理 -->
					    	<c:if test="${articlePage.pageNum==index.index}">
					    		<li class="page-item"><a class="page-link" href="javascript:void()"><font color="red"> ${index.index} </font></a>  </li>
					  		</c:if>
					  		
					  		<!-- 非当前页码的处理 -->
							<c:if test="${articlePage.pageNum!=index.index}">
					    		<li class="page-item"><a class="page-link" onclick="gopage(${i.index})"> ${index.index}</a></li>
					  		
					    	</c:if>					  
					    </c:forEach>
		   	<c:forEach begin="1" end="${articlePage.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    
		   
		   <li class="page-item">
		   <a class="page-link" href="#" onclick="gopage(${articlePage.nextPage==0?articlePage.pages:articlePage.nextPage})">下一页</a>
		   </li>
		    <li class="page-item">
		      <a class="page-link" href="#" onclick="gopage(${articlePage.pages})">尾页</a>
		    </li>
		  </ul>
		</nav>
		
		 <!-- 审核文章 -->
<div class="modal fade"   id="articleContent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document" style="margin-left:100px;">
    <div class="modal-content" style="width:1200px;" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">文章审核</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body ">
         	<div class="row" id="divTitle"></div>
         	<div class="row" id="divOptions" ></div>
         	<div class="row" id="divContent"></div>		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(1)">审核通过</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(2)">审核拒绝</button>
        <button type="button" class="btn btn-primary" onclick="setHot(1)">设置热门</button>
        <button type="button" class="btn btn-primary" onclick="setHot(0)">取消热门</button>
      </div>
    </div>
  </div>
</div>

	
<!-- </div>     -->
<script>
	var global_article_id;
	
	
	function del(id){
		//alert(id)
		if(!confirm("您确认删除么？")){
			return;
		}
		$.post('/user/deletearticle',{id:id},
				function(data){
					if(data==true){
						alert("刪除成功")
						//location.href="#"
						$("#workcontent").load("/user/articles");
					}else{
						alert("刪除失敗")
					}
					
		},"json"				
		)
	}
	
	function check(id){
		//alert(id)
     	$.post("/article/getDetail",{'id':id},function(msg){
     		//alert(JSON.stringify(msg))
     		if(msg.code==1){
     			//
     			$("#divTitle").html(msg.data.title);
     			//
     			$("#divOptions").html("栏目：" +msg.data.channel.name + 
     					" 分类："+msg.data.category.name + " 作者：" + msg.data.user.username );
     			//
     			$("#divContent").html(msg.data.content);
     			$('#articleContent').modal('show')
     			//文章id保存到全局变量当中
     			global_article_id=msg.data.id;
     			return;
     		}
     		alert(msg.error)
     		
     		
     	},"json");
		
		//$("#workcontent").load("/user/updateArticle?id="+id);
	}
	
	
	
	/**
	*  status 0  待审核  1 通过    2 拒绝 
	*/
	function setStatus(status){
		var id = global_article_id;
		$.post("/locked/setArticeStatus",{id:id,status:status},function(msg){
			//alert(JSON.stringify(msg))
			if(msg.code==1){
				alert('操作成功')
				//隐藏当前的模态框
				$('#articleContent').modal('hide')
				//刷新当前的页面
				refreshPage();
				return;	
			}
			alert(msg.error);
		},
		"json")
	}
	
	/**
	 0 非热门
	 1 热门
	*/
	function setHot(status){
		
		var id = global_article_id;// 文章id
		$.post("/locked/setArticeHot",{id:id,status:status},function(msg){
			if(msg.code==1){
				alert('操作成功')
				//隐藏当前的模态框
				$('#articleContent').modal('hide')
				//刷新当前的页面
				refreshPage();
				return;
			}
			alert(msg.error);
		},
		"json")
	}
	
	/**
	* 翻页
	*/
	function gopage(page){
		$("#workcontent").load("/locked/article?page="+page + "&status="+'${status}');
	}
	function refreshPage(){
		//alert(1);
		$("#workcontent").load("/locked/article?page=" + '${articlePage.pageNum}' + "&status="+'${status}');
	}
	
	
	 //$('#articleContent').on('hidden.bs.model', function (e) {
		//refreshPage();
	
	 //}) 
	 --%>
  
  
	
<!-- </script> -->

    
    