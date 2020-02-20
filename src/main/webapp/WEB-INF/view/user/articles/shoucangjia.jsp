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
            <th>时间</th>
            <th>操作[<a href="/article/addshoucang">添加收藏</a>]</th>
          </tr>
        </thead>
        <tbody>
        	<c:forEach items="${pageInfo.list}" var="sc">
        		<tr>
        			<td>${sc.id}</td>
        			<td><a href="${sc.url}">${sc.text}</a></td>
        			<td>${sc.created}</td>
        			<td width="200px">
        				<input type="button" value="删除"  class="btn btn-danger" onclick="del(${sc.id},${sc.user_id })">
        				<%-- <input type="button" value="修改"  class="btn btn-warning" onclick="update(${article.id})" > --%>
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
		    <a class="page-link" href="#" onclick="gopage(${pageInfo.prePage==0?1:pageInfo.prePage})">上一页</a>
		    </li>
		    <c:forEach begin="${pageInfo.pageNum-2>=1?pageInfo.pageNum-2:1}" end="${pageInfo.pageNum+2>=pageInfo.pages?pageInfo.pages:pageInfo.pageNum+2}" varStatus="index">
					    	<!-- 当前页码的处理 -->
					    	<c:if test="${pageInfo.pageNum==index.index}">
					    		<li class="page-item"><a class="page-link" href="javascript:void()"><font color="red"> ${index.index} </font></a>  </li>
					  		</c:if>
					  		
					  		<!-- 非当前页码的处理 -->
							 <c:if test="${pageInfo.pageNum!=index.index}">
					    		<li class="page-item"><a class="page-link" onclick="gopage(${i.index})"> ${index.index}</a></li>
					  		
					    	</c:if>	 				  
					    </c:forEach>
		   	<%-- <c:forEach begin="1" end="${pageInfo.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach> --%>
		    
		   
		   <li class="page-item">
		   <a class="page-link" href="#" onclick="gopage(${pageInfo.nextPage==0?pageInfo.pages:pageInfo.nextPage})">下一页</a>
		   </li>
		    <li class="page-item">
		      <a class="page-link" href="#" onclick="gopage(${pageInfo.pages})">尾页</a>
		    </li>
		  </ul>
		</nav> 
	
<!-- </div>     -->
<script>
	function del(id,userid){
		/* alert(id) */
		if(!confirm("您确认删除么？"))
			return;
		
		$.post('/article/delshoucangjia',{id:id,userid:userid},
				function(data){
					if(data==true){
						alert("刪除成功")
						//location.href="#"
						$("#workcontent").load("/article/shoucangjia");
					}else{
						alert("刪除失敗")
					}
					
		},"json"				
		)
	}
	
	
	
	/**
	* 翻页
	*/
	function gopage(page){
		$("#workcontent").load("/article/shoucangjia?page="+page);
	}
	
</script>

    
    