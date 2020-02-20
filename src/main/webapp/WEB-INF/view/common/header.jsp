<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark " >
  <div class="collapse navbar-collapse" id="navbarSupportedContent" >
    
    <ul class="navbar-nav mr-auto">
    	<li class="nav-item">
           <a class="nav-link" href="#"><img src="/resource/images/logo.png"> </a>
      </li> 
    </ul>
    
    <form class="form-inline my-2 my-lg-0" action="/list" method="post" style="margin-right:30%" >
      <input class="form-control mr-sm-2" type="text" name="key" value="${key}" placeholder="搜索" aria-label="搜索">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜索</button>
    </form>
    
    <div>
    	<ul class="nav">
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/pic/${user.url}"> </li>
    		
    		<c:if test="${user!=null}">
    		<li class="nav-item nav-link">
    		<font color="red">${user.nickname==''?user.username:user.nickname}</font>
    		
    		</li>
    		<li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	用户信息
		        </a>
		            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
			          <a class="dropdown-item" href="/user/home">进入个人中心</a>
			          <c:if test="${user.locked==1}">
			          <a class="dropdown-item" href="/user/admin">进入管理中心</a>
			          </c:if>
			          <a class="dropdown-item" href="#">个人设置</a>
			          <div class="dropdown-divider"></div>
			          <a class="dropdown-item" href="/user/exit">登出</a>
			        </div>
		      </li>
		      </c:if>
		     
		      <c:if test="${user==null}">
			       <li class="nav-item nav-link"><!-- <a href="/user/login">登录</a> -->
			       <input type="button" class="btn btn-primary" onclick="ssss()" value="登录">
			       </li>
		      </c:if>
      
    	</ul>
    </div>
  </div>
</nav><!--  头结束 -->

<script type="text/javascript">
	function ssss(){
		$('#articleContent').modal('show')
	}
	function login(){
		var name =$("[name=username]").val();
		var pwd =$("[name=password]").val();
		$.post("/user/tologin",{name:name,pwd:pwd},function(msg){
			//alert(JSON.stringify(msg))
			if(msg.code==1){
				alert(msg.data);
				$('#articleContent').modal('hide')
				location="index";
			}else{
				alert(msg.error);
			}
			

		})
	}
</script>

<div class="modal fade"   id="articleContent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document" ><!-- style="margin-left:100px;" -->
    <div class="modal-content" style="width:300px;" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">用户登录</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body ">
         	<div class="col-sm-9 col-md-9">					
						<div class="form-group">	<!-- placeholder="用户名" -->					
							<i class="fa fa-user fa-lg">用户名</i>${eror}<br>						
							<input type="text"  name="username" />
							
						</div>					
						<div class="form-group">							
							<i class="fa fa-lock fa-lg">输入你的密码</i>	<br>						
							<input type="password"  name="password"/>					
				
							
						</div>								
						<!-- <div class="form-group">						
								<input type="submit" class="btn btn-success pull-right" value="登录"/>		
								<a href="/user/register"><font color="red">没有账户，还不快去注册</font></a>
						</div>	 -->			
					</div>		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="login()">登录</button>
       <!--  <button type="button" class="btn btn-primary" onclick="setStatus(2)">审核拒绝</button>
        <button type="button" class="btn btn-primary" onclick="setHot(1)">设置热门</button>
        <button type="button" class="btn btn-primary" onclick="setHot(0)">取消热门</button>   -->
      </div>
    </div>
  </div>
</div>