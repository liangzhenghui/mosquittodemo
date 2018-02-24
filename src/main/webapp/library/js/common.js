function form2Json(id) {
	var arr = $("#" + id).serializeArray();
	console.log(arr);
	var jsonStr = "";
	jsonStr += '{';
	for (var i = 0; i < arr.length; i++) {
		jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
	}
	jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
	jsonStr += '}';
	var json;
    if (typeof(JSON) == 'undefined'){  
 	   json = eval("("+jsonStr+")");  
 	}else{  
 		json = JSON.parse(jsonStr);  
 	}  
    console.log(json);
	return json;
	//return jsonStr;
};

function form2JsonStr(id) {
	var arr = $("#" + id).serializeArray();
	var jsonStr = "";
	jsonStr += '{';
	for (var i = 0; i < arr.length; i++) {
		jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
	}
	jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
	jsonStr += '}';
	return jsonStr;
};

function formatDate(now)   {       
    var   year=now.getFullYear();       
    var   month=now.getMonth()+1;       
    var   date=now.getDate();       
    return   year+"-"+month+"-"+date;    
 }  
