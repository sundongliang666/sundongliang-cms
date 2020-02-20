<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- <div class="container-fluid"> -->
	<table class="table">
		<!-- articlePage -->
	
	  <thead>
          <tr>
            <th>id</th>
            <th>文章id</th>
            <th>投诉人id</th>
            <th>分类</th>
            <th>分类</th>
            <th>url</th>
            <th>图片</th>
            <th>举报信息</th>
            <th>邮箱</th>
            <th>手机号</th>
            <th>时间</th>
          </tr>
        </thead>
        <tbody>
        	<c:forEach items="${pageinfocomplain.list}" var="complain">
        		<tr>
        			<td>${complain.id}</td>
        			<td>${complain.articleId}</td>
        			<td>${complain.userId}</td>
        			<td>${complain.complainType}</td>
        			<td>${complain.compainOption}</td>
        			<td>${complain.srcUrl}</td>
        			<td><img alt="" src="/pic/${complain.picture}"  width="50px" height="50px"> </td>
        			<td>${complain.content}</td>
        			<td>${complain.email}</td>
        			<td>${complain.mobile}</td>
        			<td>
        			<fmt:formatDate value="${complain.created}" pattern="yyyy-MM-dd"/>
        			</td>
        			<%-- <td>${complain.id}</td> --%>
        			
        			<td width="200px">
        				<input type="button" value="删除"  class="btn btn-danger" onclick="del(${complain.id})">
        				<input type="button" value="审核"  class="btn btn-warning" onclick="check(${complain.id})" >
        			</td>
        		</tr>
        	</c:forEach>
        </tbody>
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
		</nav> --%>
		
		 <!-- 审核举报 -->
<div class="modal fade"   id="articleContent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document" style="margin-left:100px;">
    <div class="modal-content" style="width:1200px;" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">举报审核</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body ">
         	<div class="row" id="divTitle">
         	
         	</div>
         	<div class="row" id="divOptions" ></div>
         	<div class="row" id="divContent"></div>		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(2)">举报通过</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(1)">举报拒绝</button>
      </div>
    </div>
  </div>
</div>
 <script type="text/javascript">
 var global_article_id;
 var global_complain_id;
	function check(id){
		alert(id)
		$.post("/article/getComplain",{id:id},function(msg){
			alert(JSON.stringify(msg))
			if(msg.code==1){
     			//
     			$("#divTitle").html(msg.data.id);
     			//
     			$("#divOptions").html("文章Id：" +msg.data.articleId + 
     					" 投诉人："+msg.data.user.username  );
     			//
     			$("#divContent").html(msg.data.content); 
     			$('#articleContent').modal('show')
     			 //文章id保存到全局变量当中
     			global_complain_id=msg.data.id;
     			global_article_id=msg.data.articleId
     			return;
     		}
     		alert(msg.error)
		},"json")
		/* $('#articleContent').modal('show') */
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
				//refreshPage();
				return;	
			}
			alert(msg.error);
		},
		"json")
	}
	
	$('#articleContent').on('hidden.bs.model', function (e) {
		refreshPage();
	 })
	/**
	* 翻页
	*/
	/* function gopage(page){
		$("#workcontent").load("/locked/article?page="+page + "&status="+'${status}');
	}*/
	function refreshPage(){
		//alert(1);
		$("#workcontent").load("/article/tocomplain");
	} 
</script> 

<!-- <script type="text/javascript">
function check(){
	var global_complain_id;
	function check(id){
		 alert(id)
     	$.post("/article/getComplain",{'id':id},function(msg){
     		//alert(JSON.stringify(msg))
     		if(msg.code==1){
     			//
     			$("#divTitle").html(msg.data.id);
     			//
     			$("#divOptions").html("文章Id：" +msg.data.articleId + 
     					" 投诉人："+msg.data.user.username  );
     			//
     			$("#divContent").html(msg.data.content); 
     			$('#articleContent').modal('show')
     			 //文章id保存到全局变量当中
     			global_complain_id=msg.data.id;
     			return;
     		}
     		alert(msg.error)
     		
     		
     	},"json"); 
		
		//$("#workcontent").load("/user/updateArticle?id="+id);
	}
} -->
	
</script>

	
<!-- </div>     -->
<!-- <script>
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
	 -->
	
	
	
  
  
	
<!-- </script> -->

    
    