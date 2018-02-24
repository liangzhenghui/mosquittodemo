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
            width:100px;
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newProduct()">新建产品</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" singleSelect="true" onclick="editProduct()">编辑产品基本信息</a>
<!--         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="uploadImg()">继续上传影片图片</a> -->
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="ImgManage()">产品图片管理</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyProduct()">删除产品</a>
    </div>
	<table id="productList" class="easyui-datagrid" 
			url="<%=contextPath%>/product-list.json"
			title="产品管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<th field="product_name" width="200">产品名称</th>
				<th field="lbZw" width="200" >类别</th>
				<th field="price" width="200">价格(元)</th>
				<th field="kc" width="200">库存</th>
				<th field="product_description" width="500">描述</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" title="产品管理">
        <form id="fm" method="post" novalidate enctype="multipart/form-data">
          <div class="fitem">
                <label>产品分类 :</label>
                <input id="product_lb_id" name="product_lb_id" class="easyui-validatebox"  style="width:300px">
            </div>
            <div class="fitem">
                <label>产品名称 :</label>
                <input id="product_name" name="product_name" class="easyui-validatebox" required="true" style="width:300px">
            </div>
             <div class="fitem">
                <label>产品价格 (元):</label>
                <input name="price" class="easyui-numberbox" required="true" style="width:300px">
            </div>
             <div class="fitem">
                <label>库存(间):</label>
                <input name="kc" class="easyui-numberbox" required="true" style="width:300px">
            </div>
            <div class="fitem">
                <label>描述:</label>
            	<input id="product_description" name="product_description" class="easyui-textbox" required="true" data-options="multiline:true" value="" style="width:100%;height:100px">
            </div>
            <input type="hidden" id="product_lb_zw" name="product_lb_zw">
            <input type="hidden" id="product_id" name="product_id">
       		<div class="fitem">
                <label>图片:</label>
            	<input type="file" name="image" id="file_upload" />
        	</div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="newProductSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    <div id="dlg1" class="easyui-dialog" style="width:600px;height:300px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons1" title="产品基本信息管理">
        <form id="fm1" method="post" novalidate enctype="multipart/form-data">
             <div class="fitem">
                <label>产品分类 :</label>
                <input id="lb_id" name="lb_id" class="easyui-validatebox" style="width:300px">
           		<input type="hidden" id="lb_zw" name="product_lb_zw">
            </div>
            <div class="fitem">
                <label>产品名称 :</label>
                <input name="product_name" class="easyui-validatebox" required="true" style="width:300px">
            </div>
            <div class="fitem">
                <label>产品价格 (元):</label>
                <input name="price" class="easyui-numberbox" required="true" style="width:300px">
            </div>
            <div class="fitem">
                <label>库存(间):</label>
                <input name="kc" class="easyui-numberbox" required="true" style="width:300px">
            </div>
            <div class="fitem">
                <label>描述:</label>
            	<input name="product_description" class="easyui-textbox" required="true" data-options="multiline:true" value="" style="width:100%;height:100px">
            </div>
        	<input type="hidden" id="productId" name="product_id">
        </form>
    </div>
    <div id="dlg-buttons1">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="editProductSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">取消</a>
    </div>
<script type="text/javascript">
var url;
var file_count=0;
$(function() {
	//新建时使用
    $('#file_upload').uploadify({
    	'auto':false,
    	'buttonText' : '请选择',
        'swf'      : '<%=contextPath%>/library/uploadify/uploadify.swf',
        'uploader' : '<%=contextPath%>/product-img-upload.json',
        'fileObjName'   : 'file',
        'onCancel' : function(file) {
            file_count--;
        },
        'onSelect' : function(file) {
            file_count++;
        },
        'onUploadStart' : function(file) {
            $("#file_upload").uploadify("settings", "formData",{ 'productId': $("#product_id").val()});
        },
        'onQueueComplete' : function(queueData) {
        	$.messager.show({
                title: '提示',
                msg: queueData.uploadsSuccessful+"个图片上传成功,"+queueData.uploadsErrored+"个图片上传失败"
            });
        	$('#dlg').dialog('close');// close the dialog
        	$('#productList').datagrid('reload');    // reload the user datac
        }
    });
});
function newProduct(){
    $('#dlg').dialog('open').dialog('setTitle','新建产品');
    $('#fm').form('clear');
    $('#product_lb_id').combobox({
        url:'<%=contextPath %>/get-product-lb-by-user.do',
        valueField:'code',
        textField:'detail',
        onSelect:function(record){
        	$('#product_lb_id').next().children("input[type='text']").removeClass('selectInValid');
        	$("#product_lb_zw").val(record.detail);
        }
    });
}
function editProduct(){
    var row = $('#productList').datagrid('getSelected');
    if (row){
        $('#dlg1').dialog('open').dialog('setTitle','编辑产品基本信息');
        $('#fm1').form('load',row);
        $("#productId").val(row.id);
        $("#lb_zw").val(row.lbZw);
        //注意这里不要和新建的product_lb_id相同
        $('#lb_id').combobox({
            url:'<%=contextPath %>/get-product-lb-by-user.do?productId='+row.id,
            valueField:'code',
            textField:'detail',
            onSelect:function(record){
            	$('#lb_id').next().children("input[type='text']").removeClass('selectInValid');
            	$("#lb_zw").val(record.detail);
            }
        });
    }else{
    	 $.messager.show({    // show error message
             title: '提示',
             msg: "请先选定一行"
         });
    }
}
function newProductSubmit(){
	url = '<%=contextPath%>/product-create.json';
	var data = form2JsonStr("fm");
	$('#product_lb_id').next().children("input[type='text']").addClass('selectInValid');
	$('#product_lb_id').next().tooltip({
	    position: 'right',
	    content: '<span style="color:#000000">必选</span>',
	    onShow: function(){
	        $(this).tooltip('tip').css({
	            backgroundColor: '#F0E68C',
	            borderColor: '#F0E68C'
	        });
	    }
	});
	var flag = $("#fm").form('validate');
	if(file_count<=0){
		$.messager.show({
            title: '提示',
            msg: "还没上传图片"
        });
		return;
	}
	if(flag&&($('#product_lb_id').combobox('getValue')!="")){
		$.post(url,{data:data},function(data){
			if(data.result){
				//产品信息保存成功后开始文件上传
				var product_id = data.id;
				$("#product_id").val(product_id);
				$('#file_upload').uploadify('upload','*');
			}else{
				$.messager.show({
	                title: '提示',
	                msg: "创建失败"
	            });
			}
		},"json");
	}
}

function editProductSubmit(){
	var url = '<%=contextPath%>/product-edit.json';
	var data = form2JsonStr("fm1");
	 $('#lb_id').next().children("input[type='text']").addClass('selectInValid');
 	$('#lb_id').next().tooltip({
 	    position: 'right',
 	    content: '<span style="color:#000000">必选</span>',
 	    onShow: function(){
 	        $(this).tooltip('tip').css({
 	            backgroundColor: '#F0E68C',
 	            borderColor: '#F0E68C'
 	        });
 	    }
 	});
	var flag = $("#fm1").form('validate');
	if(flag&&($('#lb_id').combobox('getValue')!="")){
		$.post(url,{data:data},function(data){
			if(data.result){
				$('#dlg1').dialog('close');// close the dialog
		        $('#productList').datagrid('reload');    // reload the user datac
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

function destroyProduct(){
    var row = $('#productList').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除功能','确定要删除么？',function(r){
            if (r){
                $.post('<%=contextPath%>/product-delete.json',{id:row.id},function(json){
                    if (json.result){
                    	$.messager.show({
        	                title: '提示',
        	                msg: "删除成功"
        	            });
                        $('#productList').datagrid('reload');    // reload the user data
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

function ImgManage(){
	var row = $('#productList').datagrid('getSelected');
	if(row){
		var url = '<%=contextPath%>/salesplatform/web/product-image-list.jsp?id='+row.id;
		window.parent.addTab("产品图片管理",url);
	}else{
		$.messager.show({    // show error message
            title: '提示',
            msg: "请先选定一行"
        });
	}
}

</script>
</body>
</html>
