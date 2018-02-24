<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java"  import="ums.service.UserManager"%>
<%@ page language="java"  import="ums.model.User"%>
<%String contextPath = request.getContextPath();%>
<%String productId = (String)request.getParameter("id"); %>
<%UserManager userManager = new UserManager();
User user = userManager.getLoginUser(request);
String userId = "";
if(null!=user){
	userId = user.getUserid();
}
%>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>影片详细页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="<%=contextPath%>/css/base.css"/>
	<link rel="stylesheet" href="<%=contextPath%>/css/index.css"/>
	<style type="text/css">
		.p_rate{height:14px;position:relative;width:80px;overflow:hidden;display:inline-block;background:url(../images/veryhuo_rate.png) repeat-x;}
		.p_rate i{position:absolute;top:0;left:0;cursor:pointer;height:14px;width:16px;background:url(../images/veryhuo_rate.png) repeat-x 0 -500px}
		.p_rate .select{background-position:0 -32px}
		.p_rate .hover{background-position:0 -16px}
	</style>
    <!-- Le styles -->
   <%--  <link rel="stylesheet" type="text/css" href="<%=contextPath%>/library/bootstrap/css/bootstrap.min.css">
    <link href="<%=contextPath%>/library/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"> --%>
	<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
 </head>
  <body style="font-family:'Microsoft JhengHei'">
	<div class="header">
		<ul class="nav" id="lb">
			<li><a style="background-color:transparent" href='#'>电影票预定系统</a></li>
			<li><a style="background-color:transparent" href="<%=contextPath%>/main.jsp">首页</a></li>
		</ul>
	</div>
   	<table>
   		<tr>
   			<td><h1>电影:</h1></td>
   			<td><h1 id="product_name"></h1></td>
   		</tr>
   	</table>
	<div id="product_detail" style="float:left;width:20%"></div>
	<div id="product_description" style="float:left;width:80%"></div>
	<table style="width:80%" id="movie_plan">
		<tr
			style="">
			<td width="33%"><h3>观影场次安排</h3></td>
		</tr>
		<tr
			style="background-color:green;text-align: center; color: #fff">
			<td width="25%">放映时间</td>
			<td width="25%">结束时间</td>
			<td width="25%">价格</td>
			<td width="25%">购买</td>
		</tr>
	</table>
	<br><br><br>
	<table style="width:80%;margin: 0 auto">
		<tr>
			<td>评分
				<span class="p_rate" id="p_rate"> 
					<i title="1分"></i> 
					<i title="2分"></i> 
					<i title="3分"></i> 
					<i title="4分"></i>
					<i title="5分"></i>
				</span>
			</td>
		</tr>
		<tr>
			<td>
				<textarea cols="100" rows="8"  id="pl" name="pl"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<button onclick="pl()">确定</button>
			</td>
		</tr>
	</table>
	<div id="plHistory">
		
	</div>
    <script>
    $(function(){
    	plhistory();
    });
    var score;
	/*
	* 通用打分组件
	* callBack打分后执行的回调
	* this.Index:获取当前选中值
	*/
	var pRate = function(box,fs, callBack) {
		this.Index = null;
		var B = $("#" + box), rate = B.children("i"), w = rate.width(), n = rate.length, me = this;
		for ( var i = 0; i < n; i++) {
			rate.eq(i).css({
				'width' : w * (i + 1),
				'z-index' : n - i
			});
		}
		rate.hover(function() {
			var S = B.children("i.select");
			$(this).addClass("hover").siblings().removeClass("hover");
			if ($(this).index() > S.index()) {
				S.addClass("hover");
			}
		}, function() {
			rate.removeClass("hover");
		});
		rate.click(function() {
			rate.removeClass("select hover");
			$(this).addClass("select");
			me.Index = $(this).index() + 1;
			if (callBack) {
				callBack();
			}
		});
		rate.removeClass("select hover");
		$(rate[fs]).addClass("select");
		score = me.Index = $(rate[fs]).index() + 1;
	};

	 var Rate1 = new pRate("p_rate",-1, function() {
		score=Rate1.Index;
	});
	 
	 
	 var pRate1 = function(box,fs, callBack) {
			this.Index = null;
			var B = $("#" + box), rate = B.children("i"), w = rate.width(), n = rate.length, me = this;
			for ( var i = 0; i < n; i++) {
				rate.eq(i).css({
					'width' : w * (i + 1),
					'z-index' : n - i
				});
			}
			rate.removeClass("select hover");
			$(rate[fs]).addClass("select");
			me.Index = $(rate[fs]).index() + 1;
		};
    $(function(){
    	initProductBaseInfo();
    	initProductImages();
    	initMoviePlan();
    	$("#buyBtn").click(function(){
    		buy();
    	});
    });
    function initProductBaseInfo(){
    	var id = '<%=productId%>';
    	var url = "<%=contextPath%>/get-product-by-id.json";
   	 $.post(url,{id:id},function(json){
  		  var data = json.product;
  		  if(data){
  			  var product_name = data.product_name;
  			  var product_description = data.product_description;
  			  $("#product_name").text(product_name);
  			 $("#product_description").text(product_description);
  		  }
  		 });
    }
    function initProductImages(){
    	var id = '<%=productId%>';
    	var url = "<%=contextPath%>/get-images-of-product.json";
    	 $.post(url,{id:id},function(json){
   		  var data = json.list;
   		  if(data){
   		  	 for(var i = 0; i < data.length;i++){
   		  	 var hr = $("<hr class='featurette-divider'>");
			 var div = $("<div class='featurette'></div>");
			 var img;
			 img = $("<img src='' style='height:300px'>");
			 img.attr("src",'<%=contextPath%>/img.json?imgId='+data[i].id);
   	         div.append(img);
   	         $("#product_detail").append(div);
   		  	 }
   		  }
   		 });
    }
    
    function buy(object){
    	var moviePlanId=$(object).parents("tr").find("input").val();
   		var userId = "<%=userId%>";
    	if(userId!=""){
    		//去到购物车页面
    		window.location.href="<%=contextPath%>/add-to-shopping-cart.do?moviePlanId="+moviePlanId;
    	}else{
    		window.location.href="<%=contextPath%>/advertising-platform/login.jsp";
    	}
    }
    
    function initMoviePlan(){
    	var id = '<%=productId%>';
    	var url = "<%=contextPath%>/get-movie-plan.json";
    	$.post(url,{id:id},function(data){
    		var result = data.result;
    		for(var i = 0;i<result.length;i++){
    			var tr = $("<tr style='text-align: center;'><td width='25%'>"+result[i].play_start_time+
    			"</td><td width='25%'>"+result[i].play_end_time+"</td><td width='25%'>"+result[i].price+"元</td><td>"+
    			"<button onclick='buy(this)'>购买</button></td><input type='hidden' value="+result[i].id+"></tr>");
    			$("#movie_plan").append(tr);
    		}
    	});
    }
    
    function pl(){
    	var product_id = '<%=productId%>';
    	var userId='<%=userId%>';
    	var nr = $("#pl").val();
    	if(score==0){
			alert("请先评分");
			return;
		}
    	if(!nr){
			alert("评论内容不能为空");
			return;
		}
    	if(userId!=""){
    		var url="<%=contextPath%>/pl.json";
        	$.post(url,{score:score,nr:nr,product_id:product_id},function(data){
        		var result=data.result;
        		if(result){
        			alert("评论成功");
        			window.location.href="<%=contextPath%>/advertising-platform/product-detail.jsp?id="+product_id;
        		}
        	});
    	}else{
    		window.location.href="<%=contextPath%>/advertising-platform/login.jsp";
    	}
    }
    
    function plhistory(){
    	var product_id = '<%=productId%>';
    	var url ="<%=contextPath%>/pl-list.json";
    	$.post(url,{product_id:product_id},function(data){
    		var result = data.result;
    	    $.each(result,function(){
    	    	var pf=this.star;
    		    var nr=this.nr;
    		    var id=this.id;
    		    var creator=this.creator;
    		    var create_time=this.create_time;
    		    var table = $("<table style='width:40%;margin: 0 auto'></table>");
    		    var tr=$("<tr></tr>");
    		    var nr_tr=$("<tr></tr>");
    		    var nr_td=$("<td style='padding:20px'>"+nr+"</td>");
    		    var person_td=$("<td style='width:100px'>"+creator+"</td>");
    		    var star_td=$("<td></td>");
    		    var create_time_td=$("<td style='width: 100px;'>"+create_time+"</td>");
    		    var span = $("<span class='p_rate' id='"+id+"'></span>");
    		    for(var i = 1;i<=5;i++){
    		    	var li;
    		    	if(i==pf){
    		    		li = $("<i class='select'></i>");
    		    	}else{
    		    		li = $("<i></i>");
    		    	}
    		    	span.append(li);
    		    }
    		    var person_span=$("<span>"+creator+"</span>");
    		    star_td.append(person_span).append(span);
    		   // tr.append(person_td);
    		    tr.append(star_td);
    		    tr.append(create_time_td);
    		    nr_tr.append(nr_td);
    		    table.append(tr);
    		    table.append(nr_tr);
    		    $("#plHistory").append(table);
    		    var Rate1 = new pRate1(id,(pf-1), function() {
    				//score=Rate1.Index;
    			});
    	    });
    	});
    }
    </script>
</body></html>