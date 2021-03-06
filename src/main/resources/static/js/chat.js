
var agentWebSocket = null; // 坐席端连接实例
var id = null;
var name = null;
var clientIdList = [];
var msgMap = new Map();
var url = window.location.host;
var msgType = "1";//消息发送类型 1:客服发给客户， 3:客户发给客服

function Map() {
	this.keys = new Array();
	this.data = new Object();
	this.set = function(key, value) {
		if (this.data[key] == null) {
			if (this.keys.indexOf(key) == -1) {
				this.keys.push(key);
			}
		}
		this.data[key] = value;
	}
	this.get = function(key) {
		return this.data[key];
	}
}

$(function() {

	id = decodeURIComponent(GetQueryString("workNo"));
	name = id;

	$("#senderName").val(name);
	$("#senderId").val(id);
	console.log(url);
	// 建立websocket连接
	onConnection();
});

/**
 * 发送连接请求
 */
function onConnection() {
	console.log("3333");
	id = $.trim(id);
	if (undefined == id || "" == id) {
		console.info("[onConnection] 参数：id，为空");
		return false;
	}

	name = name.trim();
	if (undefined == name || "" == name) {
		console.info("[onConnection] 参数：name，为空");
		return false;
	}
	console.log("11111");
	console.log(url);
	agentWebSocket = new WebSocket("ws://" + url
			+ "/im/websocket/im/agent?id=" + encodeURIComponent(id) + "&name="
			+ encodeURIComponent(name));

	agentWebSocket.onerror = onError;
	agentWebSocket.onopen = onOpen;
	agentWebSocket.onmessage = onMessage;
	agentWebSocket.onclose = onClose;

	console.log("[onConnection] 已发送连接请求，" + name);
}

/**
 * 用于指定连接关闭后的回调函数
 */
function onClose() {
	agentWebSocket = null;
	layer.confirm("连接已断开，是否重新连接？", {
		btn : [ "连接", "离开" ],
		closeBtn : 0
	}, function(index) {
		layer.close(index);
		onConnection();
	}, function(index) {
		layer.close(index);
		window.location.href = "about:blank";
		window.close();
	});
	console.log("[onClose] 连接已关闭");
}

/**
 * 用于指定连接失败后的回调函数
 */
function onError() {
	agentWebSocket = null;
	console.log("[onError] 连接出现错误");
}

/**
 * 用于指定当从服务器接受到信息时的回调函数
 */
function onMessage(event) {
	console.log("fdsaf");
	console.log(event);
	var data = JSON.parse(event.data);
	var myDate = new Date();
	var h = myDate.getHours(); // 获取当前小时数(0-23)
	var m = myDate.getMinutes(); // 获取当前分钟数(0-59)
	var s = myDate.getSeconds();
	var now = getNow(h) + ':' + getNow(m) + ":" + getNow(s);
	var receiverId = data.senderId;
	var chatHtml = "";
	if(data.msgType=="1" ){
	if (clientIdList.indexOf(receiverId) > -1) {
		// 更改客户列表状态
		if (data.contentType == 2) {
			for (var i = 0; i < $(".talkList").find("li").length; i++) {
				var clientOne = $(".talkList").find("li").get(i);
				if ($(clientOne).find(".clientId").val() == data.senderId) {
					$(clientOne).find(".offline").css("display", "inline");
				}
			}
		} else if (data.contentType == 3) {
			for (var i = 0; i < $(".talkList").find("li").length; i++) {
				var clientOne = $(".talkList").find("li").get(i);
				if ($(clientOne).find(".clientId").val() == data.senderId) {
					$(clientOne).find(".offline").css("display", "none");
				}
			}
		}
		for (var i = 0; i < $(".talkList").find("li").length; i++) {
			var talkOne = $(".talkList").find("li").get(i);
			
			if ($(talkOne).find(".clientId").val() == receiverId) {
				//控制页面消息数量
				var boxNum = $("#messageBox").find("*").length;
				if(boxNum>50){
					$("#messageBox").find("*").get(0).remove();
				}
				
				$(talkOne).find(".time").text(now);
				var textValue = "";
				if (textValue != null && textValue.length >= 14) {
					textValue = textValue.substring(0, 14) + "...";
				}
				if (data.contentType == 5) {
					textValue = "[图片]";
				} else if (data.contentType == 4) {
					textValue = "[文件]";
				} else {
					var oDiv = document.createElement('div');
					$(oDiv).html(data.message);
					textValue = oDiv.innerText;
					if (textValue != null && textValue != undefined
							&& textValue.length > 14) {
						textValue = textValue.substring(0, 14)
					}
				}
				var receiverName = data.senderName;
				if (receiverName != null && receiverName != undefined
						&& receiverName.length > 12) {
					receiverName = receiverName.substring(0, 12) + "...";
				}
				$(talkOne).find(".text").text(textValue);
				$(talkOne).find(".clientName").text(receiverName);
				if (receiverId != $("#receiverId").val()) {
					$(talkOne).find(".number").css("display", "");
					var newNumber = parseInt($(talkOne).find(".number").text()) + 1;
					if(newNumber>99){
						newNumber = "99+";
					}
					$(talkOne).find(".number").text(newNumber);
					$(talkOne).addClass("news");
				}
			}
		}
	} else {
		clientIdList.push(receiverId);
		var textValue = "";
		if (data.contentType == 5) {
			textValue = "[图片]";
		} else if (data.contentType == 4) {
			textValue = "[文件]";
		} else {
			var oDiv = document.createElement('div');
			$(oDiv).html(data.message);
			textValue = oDiv.innerText;
			if (textValue != null && textValue != undefined
					&& textValue.length > 14) {
				textValue = textValue.substring(0, 14)
			}
		}
		var receiverName = data.senderName;
		if (receiverName != null && receiverName != undefined
				&& receiverName.length > 12) {
			receiverName = receiverName.substring(0, 12) + "...";
		}
		chatHtml += '<span value="' + receiverId 
			+ '" onclick="deleteMessageList($(this));" class="closeLi"></span><div class="people"><input class="clientId" type="hidden" value='
			+ receiverId + '>' + '<div class="name"><span class="clientName">'
			+ receiverName + '</span>'
			+ '<span class="offline" style="color:gray;border:1px solid #ccc;display:none;">离线</span>'
			+ '&nbsp;&nbsp;<span class="time">' + now + '</span></div>'
			+ '<div style="position:relative;"><span class="text">'
			+ textValue + '</span><i class="number">1</i></div></div>';
		var obj = document.createElement('li');
		$(obj).addClass("pointer news");
		obj.setAttribute("id", "clientId_" + receiverId);
		$(obj).on("click", function() {
			changeNowId(event.data, $(obj));
		});
		$(obj).html(chatHtml);
		$(".talkList").append(obj);
	}
	}else if(data.msgType=="3" || data.msgType=="2"){
		for (var i = 0; i < $(".agentList").find("li").length; i++) {
			var talkOne = $(".agentList").find("li").get(i);
			if ($(talkOne).find(".nowAgentId").val() == receiverId) {
				var oDiv = document.createElement('div');
				$(oDiv).html(data.message);
				textValue = oDiv.innerText;
				//TODO 加载左侧坐席列表消息
			}
		}
	}
	
	var htmlList = [];
	if (msgMap.get(receiverId) != null) {
		htmlList = msgMap.get(receiverId);
	}
	var html = "";
	if (data.contentType == 2) {
		var dataMessage = data.message;
		html += "<p>--&nbsp;" + data.message + "&nbsp;--</p>";

	} else if (data.contentType == 3) {
		var dataMessage = data.message;
		html += "<p>--&nbsp;" + data.message + "&nbsp;--</p>";
	} else if (data.contentType == 4) {
		var num = data.message.lastIndexOf("|");
		var fileId = data.message.substring(0, num)
		var fileName = data.message.substring(num + 1, data.message.length);
		html = "<div class='message service'><div class='text'><i class='service-left'></i>"
				+ "<img style='float:left;margin-right:10px' id='" + fileId + "' name='" + fileName
				+ "' src='../../im/static/img/fileImg.png' onclick='loadFile(this)'><span style='float:left'>"
				+ fileName + "</span></div></div>";
	} else if (data.contentType == 5) {
		html = "<div class='message service'><div class='text'><i class='service-left'></i>"
				+ "<img style='max-width:200px;height:auto' src='" + data.message
				+ "' onclick='fullAllBigImg(\"c\",\"" + data.message + "\")'></div></div>";
	} else {
		var behMsg = faceChange(data.message);
		html += "<div class='message service'><div class='text'><i class='service-left'></i>"
				+ behMsg + "</div></div>";
	}
	if (receiverId == $("#receiverId").val()) {
		$(".messageBox").append(html);
		$("#chatDiv").scrollTop($("#chatDiv")[0].scrollHeight);
	}
	htmlList.push(html);
	//控制缓存数量
	if(htmlList.length>50){
		htmlList = curtail(htmlList);
	}
	msgMap.set(receiverId, htmlList);

	// console.log("[onMessage] 收到消息，message:"+event.data);
}

function curtail(arr) {
	var m = arr.slice(0);
	    m.splice(0,1);
	    return m;
	}

function changeNowId(jsonStr, obj) {
	$("#sendAgentMsg").css("display","none");
	$("#sendMsg").css("display","block");
	var data = JSON.parse(jsonStr);
	$(obj).find(".number").text(0);
	$(obj).find(".number").css("display", "none");
	$(obj).siblings("li").removeClass("select-background");
	$(obj).addClass("select-background");
	$("#outWord").attr("placeholder", "请输入...");
	var receiverName = data.senderName;
	if (receiverName != null && receiverName != undefined
			&& receiverName.length > 12) {
		receiverName = receiverName.substring(0, 12) + "...";
	}
	$("#receiverName").val(receiverName);
	$("#receiverId").val(data.senderId);
	// 开放div输入框
	$("#outWord").css("pointer-events", "");
	$("#clientId_" + data.senderId).addClass("active");
	$("#messageBox").empty();
	$("#clientName").text(receiverName);
	var htmls = msgMap.get(data.senderId.toString());
	if (htmls != undefined && htmls != null) {
		for (var i = 0; i < htmls.length; i++) {
			$("#messageBox").append(faceChange(htmls[i]));
		}
	}
	$("#chatDiv").scrollTop($("#chatDiv")[0].scrollHeight);
	if ($("#queryHistory")[0].className == "active") {
		history(1);
	}
}

function getNow(s) {
	return s < 10 ? '0' + s : s;
}

/**
 * 用于指定连接成功后的回调函数
 */
function onOpen(event) {
	queryCommonMessage();
	console.log("[onOpen] 连接成功");
}

window.onbeforeunload = function(event) {
	agentWebSocket.close();
}

// 获取地址参数
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 查询常用回复
 * @returns
 */
function queryCommonMessage() {
	console.log("fds");
	$.get("../commonMessage/queryList", {
		"type" : "2",
		"pageNum" : 1,
		"pageSize" : 100
	}, function(data) {
		var dataList = data.dataList;
		if (dataList != null) {
			$("#commonWord").empty();
			for (var i = 0; i < dataList.length; i++) {
				$("#commonWord").append("<li>" + dataList[i].message + "</li>")
			}
			$("#commonWord").find("li").each(function() {
				$(this).on("click", function() {
					if($("#receiverId").val() != ""){
						$("#outWord").text($(this).text());
					}
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