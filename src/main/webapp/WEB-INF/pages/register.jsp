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
<title>注册页面</title>
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
			display:block !important
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
		<h1>注册页面</h1>
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
			<tr>
				<td>确认密码</td>
			</tr>
			<tr>
				<td><input type="password" id="sure_password" name="sure_password"></td>
			</tr>
			<!-- <tr>
				<td>地址</td>
			</tr>
			<tr>
				<td><textarea style="width: 200px;" id="address" name="address"></textarea></td>
			</tr> -->
		</table>
		<br>
		<button id="registerBtn" class="comBtn">注册</button>
		<div style="margin: 0 auto;width: 200px;text-align: center;">
			<a href="<%=contextPath%>/advertising-platform/login.jsp" style="text-decoration: none;margin: 0 auto;">去登陆</a>
		</div>
</body>
<script type="text/javascript">
$(function(){
	$("#registerBtn").click(function() {
		register();
	});
});
function register() {
	var userId = $("#userid").val();
	var password = $("#password").val();
	var sure_password = $("#sure_password").val();
	/* var address = $("#address").val(); */
	var url = '<%=contextPath%>/customer-register.json';
	if(!userId){
		alert("请输入手机号");
		return;
	}
	if(!password){
		alert("请输入密码");
		return;
	}
	/* if(!address){
		alert("请输入地址");
		return;
	} */
	if(!sure_password){
		alert("请输入确认密码");
		return;
	}
	if(password!=sure_password){
		alert("两次密码不相同");
		return;
	}
	$.post(url,{userId:userId,password:password},function(json){
		var result =json.result;
		var isExits = json.isExits;
		if(isExits){
			alert("用户名已经存在");
			return;
		}else{
			if(result==1) {
			/* 	$.messager.show({    // show error message
		            title: '提示',
		            msg: "注册成功"
		        }); */
				alert("注册成功,点击确定后跳转到登陆页面");
				window.location.href="<%=contextPath%>/advertising-platform/login.jsp";
			}
			else {
				alert("注册失败");
			}
		}
	},"json");
}
</script>
</html>