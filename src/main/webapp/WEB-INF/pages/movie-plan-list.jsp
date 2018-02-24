<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%String productId = (String)request.getParameter("id");%>
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
         .selectInValid{
        border-color: #ffa8a8;
		background-color: #fff3f3;
		color: #404040;
        }
    </style>
</head>
<body>
	 <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newMoviePlan()">新建影片场次</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" singleSelect="true" onclick="editMoviePlan()">编辑影片场次基本信息</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyMoviePlan()">删除影片场次</a>
    </div>
	<table id="moviePlanList" class="easyui-datagrid" 
			url="<%=contextPath%>/movie-plan-list.json?productId=<%=productId%>"
			title="影片场次管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<!-- <th field="productName" width="200">影片名称</th> -->
				<th field="play_start_time"  width="200">播放开始时间</th>
				<th field="play_end_time"    width="200">播放结束时间</th>
				<th field="unitPrice" width="200">价格(元)</th>
				<!-- <th field="ticket_count" width="200">座位个数</th> -->
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" style="width:400px;height:250px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" title="影片管理">
        <form id="fm" method="post" novalidate enctype="multipart/form-data">
          <!-- <div class="fitem">
                <label>影片名称 :</label>
                <input id="productName" name="productName" class="easyui-validatebox"  style="width:100%">
            </div> -->
            <div class="fitem">
                <label>影片播放开始时间 :</label>
                 <input id="play_start_time" name="play_start_time" type="text" class="easyui-datetimebox" required="required">
            </div>
             <div class="fitem">
                <label>影片播放结束时间 :</label>
                 <input id="play_end_time"  type="text" name="play_end_time" class="easyui-datetimebox" required="required">
            </div>
            <div class="fitem">
                <label>影片价格 (元):</label>
                <input name="price" class="easyui-numberbox" required="true">
            </div>
          <!--  <!--  <div class="fitem">
                <label>座位数:</label>
                <input name="price" class="easyui-numberbox" required="true">
            </div> -->
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="newMovieSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    <div id="dlg1" class="easyui-dialog" style="width:600px;height:300px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons1" title="影片基本信息管理">
        <form id="fm1" method="post" novalidate enctype="multipart/form-data">
          <!-- <div class="fitem">
                <label>影片名称 :</label>
                <input id="productName" name="productName" class="easyui-validatebox"  style="width:100%">
            </div> -->
            <div class="fitem">
                <label>影片播放开始时间 :</label>
                 <input id="play_start_time"  name="play_start_time" type="text" class="easyui-datetimebox" required="required">
            </div>
             <div class="fitem">
                <label>影片播放结束时间 :</label>
                 <input id="play_end_time" type="text" name="play_end_time" class="easyui-datetimebox" required="required">
            </div>
            <div class="fitem">
                <label>影片价格 (元):</label>
                <input name="unitPrice" class="easyui-numberbox" required="true">
            </div>
          <!--  <!--  <div class="fitem">
                <label>座位数:</label>
                <input name="price" class="easyui-numberbox" required="true">
            </div> -->
        </form>
    </div>
    <div id="dlg-buttons1">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="editMovieSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">取消</a>
    </div>
     <input name="productId" id="product_id" type="hidden" value="<%=productId%>">
     <input name="movie_plan_id"  id="movie_plan_id" type="hidden">
<script type="text/javascript">
var url;
function newMoviePlan(){
    $('#dlg').dialog('open').dialog('setTitle','新建影片');
    $('#fm').form('clear');
	//注意这一句会清掉一些数据
}
function editMoviePlan(){
    var row = $('#moviePlanList').datagrid('getSelected');
    if (row){
    	$("#movie_plan_id").val(row.id);
        $('#dlg1').dialog('open').dialog('setTitle','编辑影片基本信息');
        $('#fm1').form('load',row);
    }else{
    	 $.messager.show({    // show error message
             title: '提示',
             msg: "请先选定一行"
         });
    }
}
function newMovieSubmit(){
	url = '<%=contextPath%>/movie-plan-create.json';
	var data = form2JsonStr("fm");
	//注意,使用这个方法的时候，不会将type="hidden"提交的，所以应该另外获取
	var flag = $("#fm").form('validate');
	if(flag){
		$.post(url,{data:data,productId:$("#product_id").val()},function(data){
			if(data.result){
				$('#dlg').dialog('close');// close the dialog
	        	$('#moviePlanList').datagrid('reload');    // reload the user datac
				$.messager.show({
	                title: '提示',
	                msg: "创建成功"
	            });
			}else{
				$.messager.show({
	                title: '提示',
	                msg: "创建失败"
	            });
			}
		},"json");
	}
}

function editMovieSubmit(){
	var url = '<%=contextPath%>/movie-plan-edit.json';
	var data = form2JsonStr("fm1");
	var flag = $("#fm1").form('validate');
	if(flag){
		$.post(url,{data:data,movie_plan_id:$("#movie_plan_id").val()},function(data){
			if(data.result){
				$('#dlg1').dialog('close');// close the dialog
		        $('#moviePlanList').datagrid('reload');    // reload the user datac
		        $.messager.show({
	                title: '提示',
	                msg: "修改成功"
	            });
			}else{
				$.messager.show({
	                title: '提示',
	                msg: "修改失败"
	            });
			}
		},"json");
	}
}

function destroyMoviePlan(){
    var row = $('#moviePlanList').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除功能','确定要删除么？',function(r){
            if (r){
                $.post('<%=contextPath%>/movie-plan-delete.json',{id:row.id},function(json){
                    if (json.result){
                    	$.messager.show({
        	                title: '提示',
        	                msg: "删除成功"
        	            });
                        $('#moviePlanList').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: "删除失败"
                        });
                    }
                },'json');
            }
        });
    }
    else{
	   	 $.messager.show({    // show error message
	         title: '提示',
	         msg: "请先选定一行"
	     });
	}
}


function json2TimeStamp(milliseconds,index){
    var datetime = new Date();
    datetime.setTime(milliseconds);
    var year=datetime.getFullYear();
         //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}
</script>
</body>
</html>
