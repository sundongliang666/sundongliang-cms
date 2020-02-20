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
            <th>标题</th>
            <th>栏目</th>
            <th>分类</th>
            <th>发布时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        	<c:forEach items="${articlePage.list}" var="article">
        		<tr>
        			<td>${article.id}</td>
        			<td>${article.title}</td>
        			<td>${article.channel.name}</td>
        			<td>${article.category.name}</td>
        			<td><fmt:formatDate value="${article.created}" pattern="yyyy年MM月dd日"/></td>
        			<td>
        				<c:choose>
        					<c:when test="${article.status==0}"> 待审核</c:when>
        					<c:when test="${article.status==1}"> 审核通过</c:when>
        					<c:when test="${article.status==2}"> 审核被拒</c:when>
        					<c:otherwise>
        						未知
        					</c:otherwise>
        				</c:choose>
        			</td>
        			<td width="200px">
        				<input type="button" value="删除"  class="btn btn-danger" onclick="del(${article.id})">
        				<input type="button" value="修改"  class="btn btn-warning" onclick="update(${article.id})" >
        			</td>
        		</tr>
        	</c:forEach>
        </tbody>
      </table>
      
      <nav aria-label="Page navigation example">
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
		   	<%-- <c:forEach begin="1" end="${articlePage.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach> --%>
		    
		   
		   <li class="page-item">
		   <a class="page-link" href="#" onclick="gopage(${articlePage.nextPage==0?articlePage.pages:articlePage.nextPage})">下一页</a>
		   </li>
		    <li class="page-item">
		      <a class="page-link" href="#" onclick="gopage(${articlePage.pages})">尾页</a>
		    </li>
		  </ul>
		</nav>
	
<!-- </div>     -->
<script>
	function del(id){
		/* alert(id) */
		if(!confirm("您确认删除么？"))
			return;
		
		$.post('/article/deletearticle',{id:id},
				function(data){
					if(data==true){
						alert("刪除成功")
						//location.href="#"
						$("#workcontent").load("/article/articles");
					}else{
						alert("刪除失敗")
					}
					
		},"json"				
		)
	}
	
	function update(id){
		$("#workcontent").load("/article/update?id="+id);
	}
	
	/**
	* 翻页
	*/
	function gopage(page){
		$("#workcontent").load("/article/articles?page="+page);
	}
	
</script>

    
    