<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%String productId = (String)request.getParameter("id"); %>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" singleSelect="true" onclick="editImage()">编辑图片基本信息</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addImage()">继续添加图片</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" singleSelect="true" onclick="deleteImage()">删除图片</a>
    </div>
	<table id="productImageList" class="easyui-datagrid" 
			url="<%=contextPath%>/images-list-of-product.json?id=<%=productId%>"
			title="菜式图片管理" 
			rownumbers="true" pagination="true" toolbar="#toolbar"  singleSelect="true">
		<thead>
			<tr>
				<th field="fileName" name="image" width="200" formatter="showImg">图片</th>
				<th field="title" width="200">标题</th>
				<th field="description" width="200">描述</th>
				<th field="rollingImage" width="200" formatter="judgeRollingImage">是否为首页滚动图片</th>
				<th field="homeImage" width="200" formatter="judgeHomeImage">是否为首页底部图片</th>
				<th field="coverImage" width="200" formatter="judgeCoverImage">是否为封面图片</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" style="width:600px;height:500px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" title="菜式管理">
        <form id="fm" method="post" novalidate enctype="multipart/form-data">
          <div class="fitem">
                <label>菜式图片 :</label>
                <img id="product_img" width="100px" height="100px" src="">
            </div>
             <div class="fitem">
                <label>是否设置为滚动图片:</label>
            	<input id="rollingImage" name="rollingImage" class="easyui-validatebox" style="width:300px">
            </div>
              <div class="fitem">
                <label>是否设置为首页底部图片:</label>
            	<input id="homeImage" name="homeImage" class="easyui-validatebox" style="width:300px">
            </div>
             <div class="fitem">
                <label>是否设置为封面图片:</label>
                <input id="coverImage" name="coverImage" class="easyui-validatebox" style="width:300px">
            </div>
            <div class="fitem">
                <label>标题 :</label>
                <input id="image_title" name="title" class="easyui-validatebox"  style="width:100%">
            </div>
            <div class="fitem">
                <label>描述:</label>
            	<input id="image_description" name="description" class="easyui-textbox"  data-options="multiline:true" value="" style="width:100%;height:100px">
            </div>
            <input type="hidden" id="imageId" name="imageId">
            <input type="hidden" id="productId" name="productId" value="<%=productId%>">
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="editImageSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    <div id="dlg1" class="easyui-dialog" style="width:500px;height:150px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons1" title="继续添加图片">
        <form id="fm1" method="post" novalidate enctype="multipart/form-data">
            <div class="fitem">
                <label>添加图片:</label>
            	<input type="file" name="image" id="file_upload" />
        	</div>
        </form>
    </div>
    <div id="dlg-buttons1">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="addImageSubmit()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">取消</a>
    </div>
<script type="text/javascript">
$(function() {
	//新建时使用
    $('#file_upload').uploadify({
    	'auto':false,
    	'buttonText' : '请选择',
        'swf'      : '<%=contextPath%>/library/uploadify/uploadify.swf',
        'uploader' : '<%=contextPath%>/product-img-upload.json',
        'fileObjName'   : 'file',
        'onUploadStart' : function(file) {
            $("#file_upload").uploadify("settings", "formData",{ 'productId': $("#productId").val()});
        },
        'onQueueComplete' : function(queueData) {
        	$.messager.show({
                title: '提示',
                msg: queueData.uploadsSuccessful+"个图片上传成功,"+queueData.uploadsErrored+"个图片上传失败"
            });
        	$('#dlg1').dialog('close');// close the dialog
        	$('#productImageList').datagrid('reload');    // reload the user datac
        }
    });
});
function showImg(val,index){ 
	var url = '<%=contextPath%>/images/small/'+val;
	var img = "<img src='"+url+"' style='width:100px;height:100px'>";
    return img;
}
function editImage(){
	var row = $('#productImageList').datagrid('getSelected');
    if (row){
    	$("#imageId").val(row.id);
    	var url = '<%=contextPath%>/img.do?imgId='+row.id;
    	$("#product_img").attr("src",url);
        $('#dlg').dialog('open').dialog('setTitle','编辑图片基本信息');
        $('#fm').form('load',row);
        $('#rollingImage').combobox({
            url:"<%=contextPath %>/get-image-type.do?imageType=rolling_image&imageId="+row.id,
            valueField:'code',
            textField:'detail',
            onSelect:function(record){
            	$('#rollingImage').next().children("input[type='text']").removeClass('selectInValid');
            }
        });
        $('#homeImage').combobox({
            url:"<%=contextPath %>/get-image-type.do?imageType=home_image&imageId="+row.id,
            valueField:'code',
            textField:'detail',
            onSelect:function(record){
            	$('#homeImage').next().children("input[type='text']").removeClass('selectInValid');
            }
        });
        $('#coverImage').combobox({
            url:"<%=contextPath %>/get-image-type.do?imageType=cover_image&imageId="+row.id,
            valueField:'code',
            textField:'detail',
            onSelect:function(record){
            	$('#coverImage').next().children("input[type='text']").removeClass('selectInValid');
            }
        });
    }else{
    	 $.messager.show({    // show error message
             title: '提示',
             msg: "请先选定一行"
         });
    }
}

function editImageSubmit(){
	var url = '<%=contextPath%>/image-edit.json';
	var data = form2JsonStr("fm");
	 $('#coverImage').next().children("input[type='text']").addClass('selectInValid');
 	$('#coverImage').next().tooltip({
 	    position: 'right',
 	    content: '<span style="color:#000000">必选</span>',
 	    onShow: function(){
 	        $(this).tooltip('tip').css({
 	            backgroundColor: '#F0E68C',
 	            borderColor: '#F0E68C'
 	        });
 	    }
 	});
 	 $('#rollingImage').next().children("input[type='text']").addClass('selectInValid');
  	$('#rollingImage').next().tooltip({
  	    position: 'right',
  	    content: '<span style="color:#000000">必选</span>',
  	    onShow: function(){
  	        $(this).tooltip('tip').css({
  	            backgroundColor: '#F0E68C',
  	            borderColor: '#F0E68C'
  	        });
  	    }
  	});
  	 $('#homeImage').next().children("input[type='text']").addClass('selectInValid');
   	$('#homeImage').next().tooltip({
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
	if(flag&&($('#homeImage').combobox('getValue')!="")&&($('#coverImage').combobox('getValue')!="")&&($('#rollingImage').combobox('getValue')!="")){
		$.post(url,{data:data},function(data){
			if(data.result){
				$('#dlg').dialog('close');// close the dialog
		        $('#productImageList').datagrid('reload');    // reload the user datac
			}else{
				$.messager.show({
	                title: '提示',
	                msg: "修改失败"
	            });
			}
		},"json");
	}
}

function judgeRollingImage(val,index){
	var result = "否";
	if(val=="1"){
		result = "是";
	}
	return result;
}
function judgeHomeImage(val,index){
	var result = "否";
	if(val=="1"){
		result = "是";
	}
	return result;
}

function judgeCoverImage(val,index){
	var result = "否";
	if(val=="1"){
		result = "是";
	}
	return result;
}

function deleteImage(){
    var row = $('#productImageList').datagrid('getSelected');
    if (row){
        $.messager.confirm('删除图片','确定要删除么？',function(r){
            if (r){
                $.post('<%=contextPath%>/image-delete.json',{id:row.id},function(json){
                    if (json.result){
                    	$.messager.show({
        	                title: '提示',
        	                msg: "删除成功"
        	            });
                        $('#productImageList').datagrid('reload');    // reload the user data
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

function addImage(){
	$('#dlg1').dialog('open').dialog('setTitle','继续添加图片');
}

function addImageSubmit(){
	$('#file_upload').uploadify('upload','*');
}
</script>
</body>
</html>
