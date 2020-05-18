/**
/ * 弹出层的处理
 */

/**
 * 
 * @returns {buttonInfo}
 */
function buttonInfo()
{
	this.caption = "";// 按钮的标题
	this.id = "";     // 按钮的ID
	this.click = null;// 按钮的点击处理函数 
}

/*禁用enter键，并兼容ie8*/
//document.onkeydown=function(e)
//{
//	var e = event || window.event;
//	var tar = e.srcElement?e.srcElement:e.target;
//	//不允许触发按钮a,input[type="button"],button等三种形式的按钮
//	if(($(tar).is("a")) || ($(tar).is("input[type='button']")) || ($(tar).is("button"))){
//		return false;
//	}
//};

/**
 * 关闭层的事件处理函数
 * @param event 事件对象
 */
function closeCurrentLayerDialog(event)
{
	// 进行事件处理
	if(event != null)
	{
		var data = new Object;
		data.buttonId = $(event.target).attr("id");
		data.dialogId = $(event.target).attr("_parent_layer_id");//dialogId;
		var dialog = $("#"+ data.dialogId);
		dialog.trigger("eventCallBack",data);
	}
	else
	{
		//alert("event 为NULL");
	}
}

/**
 * 弹出对象
 * @returns {LayerDialog}
 */
function LayerDialog()
{
	// 窗口宽度
	this.width = 800;
	// 窗口高度
	this.height = 600;
	// 对话框标题栏
	this.dialogId = null;
	
	// 事件映射列表
	this.buttonsClick = new Object();
	
	/**
	 * 弹出层的事件分发
	 */
	this.eventDispath=function(event)
	{
		// 进行事件处理
		if(event != null)
		{
			var data = new Object;
			data.buttonId = $(event.target).attr("id");
			data.dialogId = $(event.target).attr("_parent_layer_id");//dialogId;
			var dialog = $("#"+ data.dialogId);
			dialog.trigger("eventCallBack",data);
		}
		else
		{
			//alert("event 为NULL");
		}
	};
	/**
	 * 对话框的事件处理
	 * @param event jquery事件对象
	 * @param data 事件数据
	 */
	this.eventCallBack = function(event,data)
	{
		var __this = event.data;
		if(__this != null)
		{
			var click = __this.buttonsClick[data.buttonId];
			if(click != null)
			{
				if(click())
				{
					$("input[type='text']:enabled").eq(0).focus();
					// 关闭弹出层
					$("#"+ data.dialogId).remove();
				}
			}
			else
			{
				$("input[type='text']:enabled").eq(0).focus();
				// 如果没关联处理函数就关闭弹出层
				$("#"+ data.dialogId).remove();
			}
		}
	};
	
	
	/**
	 * 创建层的背景
	 * @param styleClass 层的样式
	 */
	this.createBk = function(styleClass)
	{
		if(styleClass == null)
		{
			styleClass = "zxui-dialog";
		}
		var dialogBk = $("<div></div>");
		dialogBk.addClass(styleClass);
		dialogBk.bind("eventCallBack",this,this.eventCallBack);
		var dialogId = "dialogId" + (new Date()).valueOf();
		dialogBk.attr("id",dialogId);
		this.dialogId = dialogId;
		return dialogBk;
	};
	
	
	/**
	 * 关闭弹出层
	 */
	this.closeDialog = function()
	{
		$("input[type='text']:enabled").eq(0).focus();
		$("#" + this.dialogId ).remove();
	};
	
	/**
	 * 创建弹层的主显示层
	 */
	this.createDialog=function()
	{
		var dialog = $("<div></div>");
		dialog.addClass("zxui-dialog-container");
		if(null != this.width)
		{
			dialog.css("width",this.width);
		}
		
		if(null != this.height)
		{
			dialog.css("height",this.height+10);
		}
		
		var left = ($(window).width() - dialog.width())/2;
		dialog.css("left",left + "px");
		
		var top = ($(window).height() - dialog.height())/2;
		dialog.css("top",top + "px");
		return dialog;
	};
	
	/**
	 * 创建弹出标题层
	 * @param title层的标题内容
	 */
	this.createTitle=function(title, onClose)
	{
		var closeDiv = $("<div>×</div>");
		closeDiv.addClass("zxui-close");
		var buttonId = "btn_" +  (new Date()).valueOf();
		closeDiv.attr("id",buttonId);
		closeDiv.attr("_parent_layer_id",this.dialogId);
		closeDiv.click(this.eventDispath);
		if(null != onClose)
		{
			this.buttonsClick[buttonId] = onClose;
		}
		else
		{
			this.buttonsClick[buttonId] = function()
			{
				return true;
			};
		}
		var titleDiv = $("<div></div>");
		titleDiv.addClass("zxui-title");
		
		if(null != this.width)
		{
			titleDiv.css("width",this.width);
		}
		var titleh = $("<h1>" + title + "</h1>");
		titleDiv.append(titleh);
		//titleDiv.text(title);
		
		titleDiv.append(closeDiv);
		return titleDiv;
	};
	
	/**
	 * 创建嵌入页面的层
	 * @param url 嵌入的页面的url
	 * @param method 嵌入页面的操作类型 ["query","update","new"]
	 */
	this.createContent = function(url,method)
	{
		var content = $("<div align='center' style='-webkit-box-shadow:inset 0px -1px 6px rgba(0, 86, 150,0.14);'></div>");
		var iframe = $("<iframe frameborder='0' align='center' width='100%' height='"+ (this.height)+ "px' ></iframe>");
		if(url.indexOf("?") > 0)
		{
			iframe.attr("src",url + "&method=" + method);
		}
		else
		{
			iframe.attr("src",url + "?method=" + method);
		}
		iframe.attr("id","_frame_layer");
		iframe.attr("_parent_layer_id",this.dialogId);
		// 如果是IE内核将焦点设置到iframe页面中第一个input对象，解决页面无法输入的问题
		if($.browser.msie)
		{
			iframe[0].onreadystatechange=function()
			{
				$(iframe).contents().find("input[type='text']").eq(0).focus();
			};
		}
		
		content.append(iframe);
		return content;
	};
	
	/**
	 * 创建消息提示层
	 * @param message   消息内容
	 * @param type      消息类型{"","","",""}
	 * @param buttonCaption 按钮的标题，默认为确定
	 * @param click         按钮的点击事件函数 默认为关闭层
	 */
	this.createMessageContent = function(message,type,buttonCaption,click)
	{
		var content = $("<div class='zxui-prompt-container'></div>");
		if(this.width != null)
		{
			content.css("width",this.width);
		}
		// 创建提示图标
		var imageType = type;
		var imageDiv = $("<div class='zxui-prompt-content'></div>");
		if(imageType != null)
		{
			var image = $("<i></i>");
			image.attr("class","icon-" + imageType);
			imageDiv.append(image);
		}
		// 创建提示内容
		var contentDiv = $("<p class='zxui-prompt-inf'></p>");
		contentDiv.text(message);
		imageDiv.append(contentDiv);
		content.append(imageDiv);
		
		// 创建确定按钮
		var buttonDiv = $("<div class='btnGroup'></div>");
		buttonDiv.attr("align","center");
		
		if(null == buttonCaption)
		{
			buttonCaption = "确定";
		}
		var buttonId = "btn_" + + (new Date()).valueOf();
		var button = $("<input class='btn btn-primary' type='button' value='" + buttonCaption + "'>");
		button.click(this.eventDispath);
		button.attr("id",buttonId);
		button.attr("_parent_layer_id",this.dialogId);
		if(click == null)
		{
			this.buttonsClick[buttonId] = function()
			{
				return true;
			};
		}
		else
		{
			this.buttonsClick[buttonId] = click;
		}
		
		buttonDiv.append(button);
		content.append(buttonDiv);
		return content;
	};
	
	/**
	 * 创建问答层
	 * @param message  问题内容
	 * @param buttons  按钮信息(buttonInfo对象数组)
	 */
	this.createQuestionContent = function(message,buttons)
	{
		if(null == buttons || null == message )
		{
			return null;
		}
		var content = $("<div class='zxui-prompt-container'></div>");
		// 创建提示图标
		var imageDiv = $("<div class='zxui-prompt-content'></div>");
		var image = $("<i></i>");
		image.attr("class","icon-question");
		imageDiv.append(image);
		imageDiv.addClass("messageContentStyle");
		// 创建提示内容
		var contentDiv = $("<p  class='zxui-prompt-inf'></p>");
		contentDiv.text(message);
		imageDiv.append(contentDiv);
		content.append(imageDiv);
		
		var buttonDiv = $("<div  class='btnGroup'></div>");
		buttonDiv.attr("align","center");
		var buttonId = "";
		// 创建按钮
		for(var i = 0; i < buttons.length; i ++)
		{
			if(buttons[i].caption != null)
			{
				buttonId = "btn_" + + (Math.random()).valueOf();
				var button = $("<input class='btn' type='button' id='"+ buttonId +"' value='" + buttons[i].caption + "'>");
				if( buttons[i].caption == "确定")
                {
					button.attr("class","btn btn-primary");
				}
				button.attr("_parent_layer_id",this.dialogId);
				if(buttons[i].click != null)
				{
					// 注册事件函数
					this.buttonsClick[buttonId] = buttons[i].click;
				}
				button.click(this.eventDispath);
			}
			
			buttonDiv.append(button);
		}
		content.append(buttonDiv);
		return content;
	};
	/**
	 * 创建嵌入页面的弹出窗口
	 * @param title  弹出层的标题
	 * @param url    嵌入页面的url
	 * @param method 页面的方法["query","update","new"]
	 * @param width  层的宽度
	 * @param height 层的高度
	 * @param onClose 层关闭时的回调函数
	 */
	this.showDialog = function(title,url,method,width,height,onClose)
	{
		
		//生成窗口
		this.width = width;
		this.height = height;
		var dialogBk = this.createBk();
		var dialog = this.createDialog();
		var dialogTitle = this.createTitle(title, onClose);
		var dialogContent = this.createContent(url,method);
		dialog.append(dialogTitle);
		dialog.append(dialogContent);
		dialogBk.append(dialog);
		this.buttonsClick["_layer_Close"] =onClose;
		this.buttonsClick["_frame_layer"] =onClose;
		$("body").append(dialogBk);
	};
	

	/**
	 * 创建消息提示层
	 * @param title    消息提示框的标题
	 * @param message  消息内容
	 * @param type     消息类别
	 * @param width    层宽度
	 * @param height   层高度
	 * @param buttonCaption 按钮的标题
	 * @param click 按钮的点击事件处理函数
	 */
	this.showMessageTip = function(title,message,type,width,height,buttonCaption,click)
	{
		this.width = width;
		this.height = height;
		var dialogBk = this.createBk("zxui-tipBg");
		var dialog = this.createDialog();
		var dialogTitle = this.createTitle(title);
		var dialogContent = this.createMessageContent(message,type,buttonCaption,click);
		dialog.append(dialogTitle);
		dialog.append(dialogContent);
		dialogBk.append(dialog);
		$("body").append(dialogBk);
	};

	/**
	 * 创建问答层
	 * @param message  问题内容
	 * @param width    层宽度
	 * @param height   层高度
	 * @param buttons  按钮信息(buttonInfo对象数组)
	 */
	this.showQuestion=function(title,message,width,height,buttons)
	{
		this.width = width;
		this.height = height;
		var dialogBk = this.createBk("zxui-dialog",true);
		var dialog = this.createDialog();
		var dialogTitle = this.createTitle(title);
		var dialogContent = this.createQuestionContent(message,buttons);
		dialog.append(dialogTitle);
		dialog.append(dialogContent);
		dialogBk.append(dialog);
		$("body").append(dialogBk);
	};
	
	/**
	 * 显示一个等待层
	 * @param message 显示的内容
	 * @param width   层的宽度
	 * @param height  层的高度
	 */
	this.showWaitLayer = function(message,width,height)
	{
		this.width = width;
		this.height = height;
		var dialogBk = this.createBk("zxui-loading");
		var dialog = this.createDialog();
		var dialogContent = this.createMessageContent(message);
		dialog.append(dialogContent);
		dialogBk.append(dialog);
		$("body").append(dialogBk);
	};
	
	/**
	 * 显示一个等待加载层
	 * @param message 显示的内容
	 * @param width   层的宽度
	 * @param height  层的高度
	 */
	this.showLoadLayer = function(message,width,height)
	{
		this.width = width;
		this.height = height;
		var dialogBk = this.createBk("zxui-waiting");
		var dialog = this.createDialog();
		var dialogContent = this.createMessageContent1(message);
		dialog.append(dialogContent);
		dialogBk.append(dialog);
		$("body").append(dialogBk);
	};
	
	this.createMessageContent1 = function(message,type,buttonCaption,click)
	{
		var content = $("<div class='zxui-prompt-container'></div>");
		if(this.width != null)
		{
			content.css("width",this.width);
		}
		// 创建提示图标
		var imageType = type;
		var imageDiv = $("<div class='zxui-prompt-content'></div>");
		if(imageType != null)
		{
			var image = $("<i></i>");
			image.attr("class","icon-" + imageType);
			imageDiv.append(image);
		}
		// 创建提示内容
		var contentDiv = $("<p class='zxui-prompt-inf'></p>");
		contentDiv.text(message);
		imageDiv.append(contentDiv);
		content.append(imageDiv);
		return content;
	};
};

/**
 * 显示一个消息提示层
 * @param message 消息内容
 * @param click   点击事件处理函数
 * @param caption 按钮的标题
 * @returns {LayerDialog}
 */
function showMessage(message,click,caption, width,height)
{
	var layer = new LayerDialog();
	if(null == width)
	{
		width = 240;
	}
	if(null == height)
	{
		height = 150;
	}
	layer.showMessageTip("系统提示", message, "info", width, height, caption,click);
	$(":focus").blur();
	return layer;
}

/**
 * 显示一个警告消息提示层
 * @param message 消息内容
 * @param click   点击事件处理函数
 * @param caption 按钮的标题
 * @returns {LayerDialog}
 */
function showWarningInfo(message,click,caption, width,height)
{
	var layer = new LayerDialog();//warning
	if(null == width)
	{
		width = 240;
	}
	if(null == height)
	{
		height = 150;
	}
	layer.showMessageTip("系统警告", message, "warning", 240, 150, caption,click);
	$(":focus").blur();
	return layer;
}

/**
 * 显示一个错误消息提示层
 * @param message 消息内容
 * @param click   点击事件处理函数
 * @param caption 按钮的标题
 * @returns {LayerDialog}
 */
function showErrorInfo(message,click,caption, width, height)
{
	var layer = new LayerDialog();
	if(null == width)
	{
		width = 240;
	}
	if(null == height)
	{
		height = 150;
	}
	layer.showMessageTip("系统错误", message, "error", 240, 150, caption,click);
	$(":focus").blur();
	return layer;
}