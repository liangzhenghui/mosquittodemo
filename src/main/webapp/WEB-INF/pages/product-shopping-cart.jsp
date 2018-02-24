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
<title>购物车</title>
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
			<td><h1>观影清单</h1></td>
		</tr>
	</table>
	<c:if test="${empty result}">
		<table style="margin: 0 auto; width: 80%">
			<tr>
				<td style="border-top: 5px dashed #817E7E;"><h2>观影清单是空的哦~</h2></td>
			</tr>
		</table>
	</c:if>
	<c:if test="${not empty result}">
		<table style="margin: 0 auto; width: 80%">
			<tr
				style="background-color: green;text-align: center; color: #fff">
				<td width="20%">影片名称</td>
				<td width="20%">价格</td>
				<td width="20%">数量</td>
				<td width="20%">总价</td>
				<td width="20%">操作</td>
			</tr>
			<c:forEach items="${result}" var="item">
				<tr style="background: rgb(222, 245, 220); text-align: center">
					<td width="20%">${item.product_name}</td>
					<td width="20%">${item.unit_price}元</td>
					<td width="20%"><button onclick="add(this)">+</button><input style="text-align:center" type="text" value="${item.count}"><button onclick="minus(this)">-</button></td>
					<td width="20%">${item.count*item.unit_price}元</td>
					<td width="20%"><a href="#" onclick="deleteMoviePlan('${item.id}')">删除</a></td>
					<input type="hidden" class="allPrice" value="${item.count*item.unit_price}"/>
					<input type="hidden" class="shoppingCartId" value="${item.id}"/>
				</tr>
			</c:forEach>
		</table>
		<div style="margin: 0 auto; width: 80%">
			<button
				style="float: right; height: 40px; background-color: rgb(34, 187, 117); text-align: center;" onclick="order()">下单</button>
			<div style="float: right; margin-right: 50px; margin-top: 10px;" id="allPrice"></div>
		</div>
	</c:if>
</body>
<script type="text/javascript">
	$(function(){
		var allPrice=0;
		$(".allPrice").each(function(){
			var price = parseInt($(this).val());
			allPrice=allPrice+price;
			$("#allPrice").text(allPrice+"元");
		});
	});
	
	function order(){
		var url = '<%=contextPath%>/order.json';
		var shoppingCartIdArray=[];
		$(".shoppingCartId").each(function(){
			shoppingCartIdArray.push($(this).val());
		});
		$.post(url,{data:shoppingCartIdArray},function(data){
			var result = data.result;
			if(result){
				alert("下单成功");
				window.location.href="<%=contextPath%>/shopping-cart.do";
			}else{
				alert("下单失败");
			}
		});
	}
	
	function add(object){
		var shopcartId = $(object).parents("tr").find("input[class='shoppingCartId']").val();
		var url="<%=contextPath%>/update-shopping-cart-count.json";
		flag="add";
		$.post(url,{id:shopcartId,flag:flag},function(data){
			if(data.result){
				window.location.href="<%=contextPath%>/shopping-cart.do";
			}
		});
	}
	function minus(object){
		var shopcartId = $(object).parents("tr").find("input[class='shoppingCartId']").val();
		var url="<%=contextPath%>/update-shopping-cart-count.json";
		flag="minus";
		$.post(url,{id:shopcartId,flag:flag},function(data){
			if(data.result){
				window.location.href="<%=contextPath%>/shopping-cart.do";
			}
		});
	}
	function deleteMoviePlan(id){
		$.post('<%=contextPath%>/shopping-cart-delete.json',{id:id},function(json){
            if (json.result){
            	alert("删除成功");
            	window.location.href="<%=contextPath%>/shopping-cart.do";
            } else {
            	alert("删除失败");
            	window.location.href="<%=contextPath%>/shopping-cart.do";
            }
        },'json');
	}
</script>
</html>