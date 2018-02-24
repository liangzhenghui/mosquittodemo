<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=contextPath%>/library/css/easyui/themes/metro/easyui.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/css/easyui/themes/color.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/css/easyui/themes/icon.css" rel="stylesheet" />
<link href="<%=contextPath%>/library/js/zTree/zTreeStyle.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/library/uploadify/uploadify.css">
<script src="<%=contextPath%>/library/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/easyui_extend_validate.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/common.js" type="text/javascript"></script>
<script src="<%=contextPath%>/library/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
	 <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</head>
<body>
	 <div id="toolbar">
	 	 <div>
	      <form id="queryForm">
				订单状态
				<input class="easyui-combobox" id="queryStatus" name="query_status">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="javascript:$('#queryForm').form('clear');">清空</a>
			</form>
		</div>
		<div>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="finishOrder()">完成订单</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteOrder()">删除订单</a>
    	</div>
    </div>
	<table id="orderList" class="easyui-datagrid" 
			url="<%=contextPath%>/order-list.json"
			title="订单管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<th field=orderNumber width="200">订单号</th>
				<th field="productName" width="200">影片名称</th>
				<th field="unitPrice" width="200">影片价格</th>
				<th field="count" width="200">订票数量</th>
				<th field="cost" width="200">订票总价</th>
				<th field="mobile" width="200">手机号</th>
				<th field="address" width="200">地址</th>
				<th field="status" width="200" formatter="orderStatus">订单状态</th>
			</tr>
		</thead>
	</table>
<script type="text/javascript">
var url;
$(function() {
	 $('#queryStatus').combobox({
	        url:'<%=contextPath %>/order-status.do?kind=order_status',
	        valueField:'code',
	        textField:'detail'
	 });
});

function editClassificationSubmit(){
	url = '<%=contextPath%>/classification-edit.json';
	var data = form2JsonStr("fm1");
	var flag = $("#fm1").form('validate');
	if(flag){
		$.post(url,{data:data},function(data){
			if(data.result){
				$.messager.show({
	                title: '提示',
	                msg: "修改成功"
	            });
				$('#dlg1').dialog('close');// close the dialog
		        $('#classificationList').datagrid('reload');    // reload the user data
			}else{
				$.messager.show({
	                title: '提示',
	                msg: "修改失败"
	            });
			}
		},"json");
	}
}

function finishOrder(){
    var row = $('#orderList').datagrid('getSelected');
    if (row){
        $.messager.confirm('完成订单','确定订单已经完成么？',function(r){
            if (r){
                $.post('<%=contextPath%>/order-finish.json',{id:row.id},function(json){
                    if (json.result){
                    	$.messager.show({
        	                title: '提示',
        	                msg: "已经结束该订单"
        	            });
                        $('#orderList').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: "操作失败"
                        });
                    }
                },'json');
            }
        });
    }else{
	   	 $.messager.show({    // show error message
	         title: '提示',
	         msg: "请先选定一行"
	     });
	}
}


function deleteOrder(){
    var row = $('#orderList').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除订单','确定要删除订单么？',function(r){
            if (r){
                $.post('<%=contextPath%>/order-delete.json',{id:row.id},function(json){
                    if (json.result){
                    	$.messager.show({
        	                title: '提示',
        	                msg: "已经删除该订单"
        	            });
                        $('#orderList').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: "删除失败"
                        });
                    }
                },'json');
            }
        });
    }else{
	   	 $.messager.show({    // show error message
	         title: '提示',
	         msg: "请先选定一行"
	     });
	}
}
function orderStatus(val,index){
	var result;
	if(val=="0"){
		result = "已经预定电影票";
	}
	if(val=="1"){
		result = "订单已完成";
	}
	return result;
}

function query(){
	$('#orderList').datagrid({
			queryParams: form2Json("queryForm") 
	});
}
</script>
</body>
</html>
