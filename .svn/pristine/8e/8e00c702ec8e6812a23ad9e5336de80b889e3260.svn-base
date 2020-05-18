//上传图片
function uploadFile(value) {
	value += $("#senderId").val();
	var fileName = $("#loadFile").val();
	fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length);
	
	if(fileName.indexOf("'") != -1 || fileName.indexOf("\"") != -1){
		showWarningInfo("文件名中不可带有单引号或双引号");
		return false;
	}
	
	if (fileName == "" || fileName == null){
		showWarningInfo("请选择文件！");
		return false;
	}
//	var url = "../file/importFile?fileName=" + encodeURI(fileName) + "&id=" + value;
	var url = "../file/importFile?id=" + value;
	var options = 
	{
		beforeSubmit:aSubmit,
		success:aSuccess,
		url:url
	};
	
	if (checkFile()) {
		$("#fileForm").ajaxSubmit(options);
	} else {
		return false;
	}
}

var waitLayer  =  new LayerDialog();
var timeId;
function aSubmit(formData, jqForm, options)
{
	var queryString = $("#fileForm").formSerialize(); 
	formData = queryString;
	timeId = setTimeout(function()
	{
		waitLayer.showWaitLayer("请稍后",240, 160);
	},500);
	return true;
}

function aSuccess(responseText, statusText, xhr, $form)
{
	waitLayer.closeDialog();
	clearTimeout(timeId);
	if (responseText.result == "success") {
		sendFile(responseText.message);
	} else {
		showWarningInfo("发送失败！");
	}
	$("#loadFile").val("");
}

function loadFile(obj)
{
	var id = $(obj).attr("id");
	var fileName = $(obj).attr("name");
//	var url = "../file/loadOutFile?fileId=" + id + "&fileName=" + encodeURIComponent(encodeURIComponent(fileName));
	var url = "../file/loadOutFile/" + id + "/" + encodeURIComponent(encodeURIComponent(fileName));
//	window.open(url);
	$("#download").attr("src", url);
}


//校验文件大小
function checkFile()
{
	var  browserCfg = {};  
     var ua = window.navigator.userAgent;  
     if (ua.indexOf("MSIE") >= 1) {  
         browserCfg.ie = true;  
     } else if (ua.indexOf("Firefox") >= 1) {  
         browserCfg.firefox = true;  
     } else if (ua.indexOf("Chrome") >= 1){  
         browserCfg.chrome = true;  
     }
     var file = $("#loadFile");
     var fileSize = 0;
     if (browserCfg.firefox || browserCfg.chrome ) {  
         fileSize = file[0].files[0].size;  
     } else if (browserCfg.ie) {
     	 fileSize = file[0].files[0].size; 
     } else {  
    	 showWarningInfo("您的浏览器暂不支持计算上传文件的大小，确保发送文件不要超过100M，建议使用IE、FireFox、Chrome浏览器！");  
     	 return false;  
     }
     if (fileSize > 100*1024*1024){
    	 showWarningInfo("发送文件过大，最大为100M！");
		 return false;
	 }
	 return true;
}

