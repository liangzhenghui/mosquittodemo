<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="ums.service.UserManager"%>
<%@ page language="java" import="ums.model.User"%>
<%
	String contextPath = request.getContextPath();
%>
<%
	String foodId = (String) request.getParameter("id");
	UserManager userManager = new UserManager();
	User user = userManager.getLoginUser(request);
	String userId = user.getUserid();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>订单列表</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="<%=contextPath%>/css/base.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/index.css" />
<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js"
	type="text/javascript"></script>
<body>
	<div class="header">
		<ul class="nav" id="lb">
			<li><a href='#'>电影票在线预订系统</a></li>
			<li><a href='<%=contextPath%>/main.jsp'>首页</a></li>
		</ul>
	</div>
	<table style="margin: 0 auto; width: 80%">
		<tr>
			<td><h1>订单列表</h1></td>
		</tr>
	</table>
	<c:if test="${empty result}">
		<table style="margin: 0 auto; width: 80%">
			<tr>
				<td style="border-top: 5px dashed #817E7E;"><h2>订单列表是空的哦~快去下个订单吧!</h2></td>
			</tr>
		</table>
	</c:if>
	<c:if test="${not empty result}">
		<table style="margin: 0 auto; width: 80%">
			<tr
				style="background-color: #000; background: rgba(0, 0, 0, 0.6); text-align: center; color: #fff">
				<td width="30%">订单号</td>
				<td width="30%">商品名称</td>
				<td width="10%">价格</td>
				<td width="10%">数量</td>
				<td width="10%">总价</td>
				<td width="10%">订单状态</td>
			</tr>
			<c:forEach items="${result}" var="item">
				<tr style="background: beige; text-align: center">
					<td width="30%">${item.order_number}</td>
					<td width="30%">${item.product_name}</td>
					<td width="10%">${item.unit_price}元</td>
					<td width="10%">${item.count}</td>
					<td width="10%">${item.count*item.unit_price}元</td>
					<td width="10%">
						<c:if test="${item.status==0}">
							已经预定电影票
						</c:if>
						<c:if test="${item.status==1}">
							已完成
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>