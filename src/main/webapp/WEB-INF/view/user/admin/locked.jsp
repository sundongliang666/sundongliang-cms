<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cms-个人中心</title>
<link href="/resource/bootstrap-4.3.1/css/bootstrap.css"
	rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js"></script>
<script src="https://cdn.bootcss.com/popper.js/1.14.7/umd/popper.min.js"></script>
<script type="text/javascript"
	src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript"
	src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript"
	src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>


<link rel="stylesheet"
	href="/resource/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="/resource/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8"
	src="/resource/kindeditor/plugins/code/prettify.js"></script>
<script charset="utf-8" src="/resource/kindeditor/kindeditor-all.js"></script>
    
<script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>

<style>
.menuselected {
	background: red;
}

.mymenuselected li:hover {
	background: blue;
}
</style>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"
		style="background:#6600FF"> <a class="navbar-brand" href="#">Navbar</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent"
		style="background: #6600FF">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link" href="#">Home
					<span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> Dropdown </a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="#">Action</a> <a
						class="dropdown-item" href="#">Another action</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="#">Something else here</a>
				</div></li>
			<li class="nav-item"><a class="nav-link disabled" href="#"
				tabindex="-1" aria-disabled="true">Disabled</a></li>
		</ul>
		<form class="form-inline my-2 my-lg-0">
			<input class="form-control mr-sm-2" type="search"
				placeholder="Search" aria-label="Search">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		</form>
		<div>
			<ul class="nav">
				<li class="nav-item nav-link"><img width="35px" height="35px"
					src="/pic/${user.url}"></li>

				<li class="nav-item nav-link"><font color="red">${user.nickname==''?user.username:user.nickname}</font></li>
				<li class="nav-item nav-link"><a href="/user/index">进入主页面</a></li>
				<li class="nav-item nav-link"><a href="/user/exit"><font
						color="red">退出</font></a></li>
			</ul>
		</div>
	</div>
	</nav>
	<!--  头结束 -->

	<div class="container row">
		<div class="col-md-2"
			style="margin-top: 20px; border-right: solid 2px">
			<!-- 左侧的菜单 -->
			<ul class="nav flex-column mymenuselected">
				<li class="nav-item ">
				    <a  class="nav-link active" href="#" onclick="showWork($(this),'/locked/article?status=-1&page=1')" >文章管理</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="#" onclick="showWork($(this),'/locked/comment')" >评论管理</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="#" onclick="showWork($(this),'/locked/link')" >友情链接管理</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="#" onclick="showWork($(this),'/locked/user')" >用户管理管理</a>
				  </li>
				  <!-- <li class="nav-item">
				    <a class="nav-link" href="#" onclick="showWork($(this),'/article/tocomplain')" >举报管理</a>
				  </li> -->
			</ul>
		</div>

		<div class="col-md-10" id="workcontent"></div>
	</div>

	<!-- 尾开始 -->
	<nav class="nav fixed-bottom justify-content-center "
		style="background:#6600FF" height="50px"> 此树是我栽 </nav>

	<script type="text/javascript">
		KindEditor.ready(function(K) {
			window.editor1 = K.create();
			prettyPrint();
		});

		function showWork(obj, url) {
			$(".mymenuselected li").removeClass("menuselected");
			obj.parent().addClass("menuselected")
			$("#workcontent").load(url);

		}
	</script>


</body>
</html>