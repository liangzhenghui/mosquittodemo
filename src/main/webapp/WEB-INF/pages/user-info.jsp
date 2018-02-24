<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="ums.service.UserManager"%>
<%@ page language="java" import="ums.model.User"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>个人资料页面</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="<%=contextPath%>/css/base.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/index.css" />
<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js"
	type="text/javascript"></script>
<style type="text/css">
	.comBtn {
			margin:0 auto; 
			width:200px; 
			height:40px; 
			font-size:16px;
			background-color:#529dfa; 
			border:none; 
			color:#fff; 
			border-radius:6px;
			margin-bottom:5px;
			display:block
		}
</style>
</head>
<body>
	<div class="header">
		<ul class="nav" id="lb">
			<li><a href='#'>电影票预定系统</a></li>
			<li><a href='<%=contextPath%>/main.jsp'>首页</a></li>
		</ul>
	</div>
	<div style="text-align: center">
		<h1>密码修改页面</h1>
	</div>
		<table style="margin: 0 auto">
			<tr>
				<td>原密码</td>
			</tr>
			<tr>
				<td><input type="password" id="password" name="password"></td>
			</tr>
			<tr>
				<td>新密码</td>
			</tr>
			<tr>
				<td><input type="password" id="new_password" name="new_password"></td>
			</tr>
			<tr>
				<td>确认密码</td>
			</tr>
			<tr>
				<td><input type="password" id="sure_password" name="sure_password"></td>
			</tr>
			
		</table>
		<button id="updatePasswordBtn" class="comBtn">修改密码</button>
		<div style="margin: 0 auto;width: 200px;text-align: center;">
			<a href="<%=contextPath%>/advertising-platform/register.jsp" style="text-decoration: none;margin: 0 auto;">去注册</a>
		</div>
</body>
<script type="text/javascript">
$(function(){
	$("#updatePasswordBtn").click(function() {
		updatePassword();
	});
});
function updatePassword() {
	var password = $("#password").val();
	var new_password = $("#new_password").val();
	var sure_password = $("#sure_password").val();
	var url = '<%=contextPath%>/update-password.json';
	if(!password){
		alert("原密码不能为空!");
		return;
	}
	if(!new_password){
		alert("新密码不能为空!");
		return;
	}
	if(!sure_password){
		alert("确认密码不能为空!");
		return;
	}
	if(new_password!=sure_password){
		alert("新密码和确认密码不一致!");
		return;
	}
	$.post(url,{password:password,new_password:new_password},function(json){
		var result =json.result;
		if(result=="1") {
			alert("修改成功!");
			window.location.href="<%=contextPath%>/main.jsp";
		}else if(result=="3"){
			alert("原密码错误!");
		}
		else {
			alert("修改失败!");
		}
	},"json");
}
</script>
</html>