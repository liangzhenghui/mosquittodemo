<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="common.service.UserManager"%>
<%@ page language="java" import="common.model.User"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>登陆页面</title>
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
			<li><a href='#'>FSL</a></li>
			<li><a href='<%=contextPath%>/main.jsp'>首页</a></li>
		</ul>
	</div>
	<div style="text-align: center">
		<h1>登陆页面</h1>
	</div>
		<table style="margin: 0 auto">
			<tr>
				<td>手机号</td>
			</tr>
			<tr>
				<td><input type="text" id="userid" name="userid"></td>
			</tr>
			<tr>
				<td>密码</td>
			</tr>
			<tr>
				<td><input type="password" id="password" name="password"></td>
			</tr>
			
		</table>
		<button id="loginBtn" class="comBtn">登陆</button>
		<div style="margin: 0 auto;width: 200px;text-align: center;">
			<a href="<%=contextPath%>/advertising-platform/register.jsp" style="text-decoration: none;margin: 0 auto;">去注册</a>
		</div>
</body>
<script type="text/javascript">
$(function(){
	$("#loginBtn").click(function() {
		login();
	});
});
function login() {
	var userId = $("#userid").val();
	var password = $("#password").val();
	var url = '<%=contextPath%>/login.json';
	if(!userId){
		alert("账号不能为空!");
		return;
	}
	if(!password){
		alert("密码不能为空!");
		return;
	}
	$.post(url,{userId:userId,password:password},function(json){
		var result =json.result;
		if(result) {
			alert("登陆成功!");
			window.location.href="<%=contextPath%>/main.jsp";
		}
		else {
			alert("用户名或者密码错误!");
		}
	},"json");
}
</script>
</html>