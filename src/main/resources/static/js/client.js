

var clientWebSocket = null; // 客户端连接实例
var name;
var id;
var isWait = true; // 为true表示连接时进入等待，为false表示客服断开后进入等待
var timeId; // 排队等待定时器id
var url = window.location.host;
$(function(){
	name = decodeURIComponent(GetQueryString("name"));
	id = decodeURIComponent(GetQueryString("phoneNo"));
	$("#senderName").val(name);
	$("#senderId").val(id);
	setTimeout('onConnection()', 500);
});

/**
 * 发送连接请求
 */
function onConnection()
{
	name = $.trim(name);
	if(undefined == name || "" == name)
	{
		console.info("[onConnection] 参数：name，为空");
		showWarningInfo("[onConnection] 参数：name，为空");
		return false;
	}
	id = $.trim(id);
	if(undefined == id || "" == id)
	{
		console.info("[onConnection] 参数：id，为空");
		showWarningInfo("[onConnection] 参数：id，为空");
		return false;
	}
	
	clientWebSocket = new WebSocket("ws://" + url + "/im/websocket/im/client?id="+id+"&name="+encodeURIComponent(name));
	clientWebSocket.onerror = onError;
	clientWebSocket.onopen = onOpen;
	clientWebSocket.onmessage = onMessage;
	clientWebSocket.onclose = onClose;
	
	console.log("[onConnection] 已发送连接请求，"+name);
}

/**
 * 用于指定连接关闭后的回调函数
 */
function onClose()
{
	clientWebSocket = null;
	layer.confirm("连接已断开，是否重新连接？", {
		btn: ["连接","离开"], closeBtn: 0
	}, function(index){
		layer.close(index);
		onConnection();
	}, function(index){
		layer.close(index);
		window.location.href="about:blank";
		window.close();
	});
	console.log("[onClose] 连接已关闭");
}

/**
 * 用于指定连接失败后的回调函数
 */
function onError()
{
	clientWebSocket = null;
	console.log("[onError] 连接出现错误");
}
/**
 * 用于指定当从服务器接受到信息时的回调函数
 */
function onMessage(event)
{
	var data = JSON.parse(event.data);
	
	// 拼接对话信息
	var befData = data.message.replace(/\\+n/g,"<br/>");
	var behData = faceChange(befData);
	var html = "";
	
	//控制页面消息数量
	var boxNum = $("#messageBox").find("*").length;
	if(boxNum>50){
		$("#messageBox").find("*").get(0).remove();
	}
	if (data.contentType == 2) {
		$("#receiverId").val("");
		isWait = false;
		layer.confirm(data.message + "，是否进入等待？", {
			btn: ["等待","离开"], closeBtn: 0
		}, function(index){
			layer.close(index);
			setWait();
		}, function(index){
			layer.close(index);
			window.location.href="about:blank";
			window.close();
		});
		
//		console.log("[onMessage] 收到消息，message:"+event.data);
		return;
	} else if (data.contentType == 3) {
		isWait = true;
		$(".coverWait").hide();
		clearInterval(timeId);
		layer.close(layer.index);
		html += "<p>--&nbsp;" + behData + "&nbsp;--</p>";
	} else if (data.contentType == 4) {
		var num = data.message.lastIndexOf("|");
		var fileId = data.message.substring(0, num)
		var fileName = data.message.substring(num + 1, data.message.length);
		html += "<div class='message agent'><div class='text'><i class='agent-left'></i>" 
			+ "<img style='float:left;margin-right:10px' id='" + fileId + "' name='" 
			+ fileName + "' src='../../im/static/img/fileImg.png' onclick='loadFile(this)'>" 
			+ "<span style='float:left'>" + fileName + "</span>" + "</div></div>";
	} else if (data.contentType == 5) {
		html += "<div class='message agent'><div class='text'><i class='agent-left'></i>" 
			+ "<img style='max-width:200px;height:auto' src='" + data.message 
			+ "' onclick='fullAllBigImg(\"a\",\"" + data.message + "\")'>"  + "</div></div>";
	} 
	else {
		html += "<div class='message agent'><div class='text'><i class='agent-left'></i>" 
			+ behData + "</div></div>";
	}
	
	$(".messageBox").append(html);
	// 让对话框显示最新的消息
	$(".chatDiv").scrollTop($(".chatDiv")[0].scrollHeight);
	
	// 填充信息
	$("#receiverId").val(data.senderId);
	$("#receiverName").val(data.senderName);
	$("#senderId").val(data.receiverId);
	$("#senderName").val(data.receiverName);
//	console.log("[onMessage] 收到消息，message:"+event.data);
}

/**
 * 用于指定连接成功后的回调函数
 */
function onOpen(event)
{
	setWait();
	queryCommonMessage();
	console.log("[onOpen] 连接成功");
}


//获取地址参数
function GetQueryString(name)
{
	 var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	 var r = window.location.search.substr(1).match(reg);
	 if(r!=null)return  unescape(r[2]); return null;
}

function getWaitNum() {
	$.ajax({
		url : "../file/queryWaitNum",
		type : "post",
		dataType : "json",
		data : { "id" : id, "isWait" : isWait },
		success : function(data) {
			if (data.message == 0) {
				$(".coverWait").find("p").html("客服即将为您服务，请等待...");
			} else {
				$(".coverWait").find("p").html("您前面还有" + data.message + "人在等待...");
			}
		}
	});
}

/**
 * 查询常用问题
 * @returns
 */
function queryCommonMessage() {
	$.get("../commonMessage/queryList",{"type":"1","pageNum":1,"pageSize":100},function(data){
		var dataList = data.dataList;
		if (dataList != null) {
			$("#commonWord").empty();
			for (var i = 0; i < dataList.length; i++) {
				$("#commonWord").append("<li>" + dataList[i].message + "</li>")
			}
			$("#commonWord").find("li").each(function(){
				$(this).on("click",function(){
					$("#outWord").text($(this).text());
				});
			});
		}
	});
}

//在光标位置插入html代码，如果dom没有获取到焦点则追加到最后
function insertAtCursor(jsDom, html) {
    if (jsDom != document.activeElement) { // 如果dom没有获取到焦点，追加
        jsDom.innerHTML = jsDom.innerHTML + html;
        return;
    }
    var sel, range;
    if (window.getSelection) {
        // IE9 或 非IE浏览器
        sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            range = sel.getRangeAt(0);
            range.deleteContents();
            var el = document.createElement("div");
            el.innerHTML = html;
            var frag = document.createDocumentFragment(),
                node, lastNode;
            while ((node = el.firstChild)) {
                lastNode = frag.appendChild(node);
            }
            range.insertNode(frag);
            // Preserve the selection
            if (lastNode) {
                range = range.cloneRange();
                range.setStartAfter(lastNode);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
            }
        }
    } else if (document.selection && document.selection.type != "Control") {
        // IE < 9
        document.selection.createRange().pasteHTML(html);
    }
}

