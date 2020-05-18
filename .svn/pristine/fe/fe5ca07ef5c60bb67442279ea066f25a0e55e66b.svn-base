//上传图片
function uploadImg(obj, str)
{
	var imgId = str + $("#senderId").val();
	var imgName = $("#file").val();
	imgName = imgName.substring(imgName.lastIndexOf("."), imgName.length);
	var url="../file/importImg";
	if(imgId != null)
	{
		url=url+"?id=" + imgId;
	}
	var options = 
	{
		beforeSubmit:bSubmit,
		success:bSuccess,
		url:url
	};
	
	if(checkImg(obj))
	{
		$("#imgForm").ajaxSubmit(options);
	}
}
var waitLayer  =  new LayerDialog();
    var timeId;
    function bSubmit(formData, jqForm, options)
    {
    	var queryString = $("#imgForm").formSerialize(); 
		formData = queryString;
		timeId = setTimeout(function()
		{
			waitLayer.showWaitLayer("请稍后",240, 160);
		},500);
		return true;
    }
    
    function bSuccess(responseText, statusText, xhr, $form)
    {
    	waitLayer.closeDialog();
    	clearTimeout(timeId);
    	if(responseText.result == "success")
    	{
//    		var imgId=responseText.message.imgId;
//    		var emgUrl=responseText.message.url;
//    		var emg="<div style='width:200px;height:120px;overflow:hidden'><img style='width:100%;height:auto' id='"+imgId+"' src='"+emgUrl+"' onclick='fullAllBigImg(\""+imgId+"\",\""+emgUrl+"\")'/></div>";
//    		var emg="<img style='width:200px;height:auto' id='"+imgId+"' src='"+emgUrl+"' onclick='fullAllBigImg(\""+imgId+"\",\""+emgUrl+"\")'/>";
//    		$("#outWord").append(emg);
    		sendImg(responseText.message);
    	}
    	else
    	{
    		showWarningInfo(responseText.message);
    	}
    	$("#file").val("");//设置空是为了防止第二次上传同一张图片失效地问题
    }
//验证文档
function checkImg(obj)
{
	var file=obj.value;
	if(file == null)
	{
		showErrorInfo('请选择图片！');
  		return false;
	}
	var fileType = file.substring(file.lastIndexOf('.')+1,file.length);
	fileType = fileType.toLowerCase();
	if(fileType != 'gif'
		&&fileType != 'jpg'
		&&fileType != 'png'
		&&fileType!='bmp'
		&&fileType!='jpeg')
	{
		showWarningInfo('不支持该格式图片！');
		return false;
  	}
  	return true;
}
/**
	填充图片
 */
var imgNum=0;
function fullAllBigImg(imgId,imgSrc)
{
	var html="<img src='"+imgSrc+"'/>";
	$("#showHistoryFlag").html(html);
	$("#zoomInContainer").show();
}

//点击放大后图片的关闭按钮关闭当前图片
$(function(){
	$("#zoomInClose").click(function(){
		$("#zoomInContainer").hide();
		$(".historyImg").empty();
	})
});


