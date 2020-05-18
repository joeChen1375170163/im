
/**
 * Ajax表单
 * 完成单个实体的增删查改操作
 * @param entityName   当前表单操作的实体类名称(类名第一个字母改小写)
 * @param containerId  变单获取或者显示实体属性的融洽
 * @returns {AjaxForm}
 */
function AjaxForm (entityName, containerId)
{
	this.entityName = entityName;
	this.containerId = containerId;
	this.entity = null;
	this.dataLoaded = null;
	/**
	 * 关闭表单
	 */
	this.close = function() {
		var iframe = $("#_frame_layer", parent.document);
		var event = {
			target : {
				id : iframe.attr("id"),
				_parent_layer_id : iframe.attr("_parent_layer_id")
			}
		};
		window.parent.closeCurrentLayerDialog(event);
	};
	/**
	 * 表单事件处理
	 * @param successCallBack 保存，修改成功后的回调函数
	 */
	this.action = function(successCallBack) {
		// 解析传参
		var queryParam = new Object;
		var locationUrl = window.location.href;
		var paramList = locationUrl.split("?")[1]; //取得参数
		if (paramList != null)
		{
			var paramItem = paramList.split("&");
			for (var i = 0; i < paramItem.length; i++)
			{
				if (paramItem[i] != null)
				{
					var param = paramItem[i].split("=");
					queryParam[param[0]] = param[1];
				}
			}
			if (queryParam["method"] == "update")
			{
				this.toUpdate(successCallBack, queryParam);
			} else if (queryParam["method"] == "query")
			{
				this.show(successCallBack, queryParam);
			} else
			{
				this.add(successCallBack, queryParam);
			}

		}

	};

	/**
	 * 获取模块的权限对象
	 * @param module 的代码
	 */
	this.getPower = function(module) {
		if (module == null)
		{
			module = this.entityName;
		}
		var actionUrl = 'powerAction_queryPower.action';
		var power = null;
		var queryItem = new Object();
		queryItem["module"] = module;
		$.ajax({
			url : actionUrl,
			async : false,
			type : 'post',
			dataType : 'json',
			data : queryItem,
			success : function(data) {
				power = data;
			},
			error : function(e) {
				if (e.responseText.indexOf('loginAction.action') != -1)
				{
					window.location.href = "../loginAction.action";
					return;
				}
			}
		});
		return power;
	};

	this.add = function(successCallBack, queryParam) {
		// 设置初始值
		for (var p in queryParam)
		{
			if (typeof (p) != "function")
			{
				$("#" + p).val(queryParam[p]);
				$("#" + p).attr("disabled", true);
			}
		}
		if (successCallBack != null)
		{
			successCallBack();
		}
	};

	this.executeAction = function(url, parameter, tip, success) {
		var dialog = new LayerDialog();
		var buttons = new Array();
		var button = new buttonInfo();
		button.caption = "确定";
		button.className = "btn btn-primary";
		button.id = "_AjaxForm_OK";
		button.click = function() {
			var queryItem = parameter;
			var actionUrl = url;
			var waitLayer = new LayerDialog();
			var isComplete = false;
			$.ajax({
				url : actionUrl,
				async : false,
				type : 'post',
				dataType : 'json',
				data : queryItem,
				success : function(data) {
					if (data.result == "success")
					{
						dialog.closeDialog();
						showMessage(data.message, function() {

							return true;
						});
						success();

					} else
					{
						// TODO 关闭alert
						//alert(data.message);
					}
				},
				beforeSend : function() {
					setTimeout(function() {
						if (!isComplete)
						{
							waitLayer.showWaitLayer("请稍后", 240, 160);
						}

					}, 500);

				},
				complete : function() {
					isComplete = true;
					waitLayer.closeDialog();
				}
			});

		};

		buttons.push(button);
		var button = new buttonInfo();
		button.caption = "取消";
		button.id = "_AjaxForm_Cancel";
		button.click = function() {
			return true;
		};
		buttons.push(button);
		dialog.showQuestion("系统提示", tip, 240, 150, buttons);
	};
	/**
	 * 删除记录
	 * @param id 要删除的实体ID
	 * @param success 删除成功后的回调函数
	 */
	this.dele = function(id, success) {
		var dialog = new LayerDialog();
		var buttons = new Array();
		var button = new buttonInfo();
		button.caption = "确定";
		button.className = "btn btn-primary";
		button.id = "_AjaxForm_OK";
		button.click = function() {
			var queryItem = new Object();
			queryItem["id"] = id;
			var actionUrl = "/" + entityName + "/delete/"+id;
			var waitLayer = new LayerDialog();
			var isComplete = false;
			$.ajax({
				url : actionUrl,
				async : false,
				type : 'put',
				dataType : 'json',
				data : queryItem,
				success : function(data) {
					if (data.result == "success")
					{
						dialog.closeDialog();
						showMessage(data.message, function() {

							return true;
						});
						success();

					} else
					{
						showWarningInfo(data.message, function() {
							return true;
						});
						dialog.closeDialog();
					}
				},
				beforeSend : function() {
					setTimeout(function() {
						if (!isComplete)
						{
							waitLayer.showWaitLayer("请稍后", 240, 160);
						}

					}, 500);

				},
				complete : function() {
					isComplete = true;
					waitLayer.closeDialog();
				}
			});

		};

		buttons.push(button);
		var button = new buttonInfo();
		button.caption = "取消";
		button.id = "_AjaxForm_Cancel";
		button.click = function() {
			return true;
		};
		buttons.push(button);
		dialog.showQuestion("系统提示", "是否删除该记录?", 240, 153, buttons);
	};
	/**
	 * 查询显示
	 * @param successCallBack 数据查询成功后的回调函数
	 * @param queryParam  查询参数
	 */
	this.show = function(successCallBack, queryParam) {
		var _this = this;
		var actionUrl = "/" + entityName + "/query/"+queryParam["id"];
		var waitLayer = new LayerDialog();
		var isComplete = false;
		$.ajax({
			url : actionUrl,
			async : false,
			type : 'get',
			dataType : 'json',
			data : queryParam,
			success : function(data) {
				if (data.result == "error")
				{
					showErrorInfo("查询异常！");
				} else
				{
					if (successCallBack != null)
					{
						successCallBack(data);
					} else
					{
						// 填充表单
						var childs = $("#" + entityName + " span").children();
						var queryItem = data;
						for (i = 0; i < childs.length; i++)
						{
							var obj = $(childs[i]);
							if (typeof (obj.attr("id")) != "undefined")
							{
								var value = $("<div/>").html(data[obj.attr("id")]).text();
								$(obj).val(value);
							}
						}
						if (_this.dataLoaded != null)
						{
							_this.dataLoaded(data);
						}
					}
				}
			},
			beforeSend : function() {
				setTimeout(function() {
					if (!isComplete)
					{
						waitLayer.showWaitLayer("请稍后", 240, 160);
					}

				}, 500);

			},
			complete : function() {
				isComplete = true;
				waitLayer.closeDialog();
			}
		});
	};

	/**
	 * 查询修改
	 * @param successCallBack 数据查询成功后的回调函数
	 * @param queryParam  查询参数
	 */
	this.toUpdate = function(successCallBack, queryParam) {
		var _this = this;
		var actionUrl = "/" + entityName + "/query/"+queryParam["id"];
		var waitLayer = new LayerDialog();
		var isComplete = false;
		$.ajax({
			url : actionUrl,
			async : false,
			type : 'get',
			dataType : 'json',
			data : queryParam,
			success : function(data) {
				if (data.result == "error")
				{
					showErrorInfo("查询异常!");
				} else
				{
					if (successCallBack != null)
					{
						successCallBack(data);
					} else
					{
						_this.entity = data;
						if (_this.dataLoaded != null)
						{
							_this.dataLoaded(data);
						}
						// 填充表单
						var childs = $("#" + entityName + " span").children();
						for (i = 0; i < childs.length; i++)
						{
							var obj = $(childs[i]);
							if (typeof (obj.attr("id")) != "undefined")
							{
								var value = $("<div/>").html(data[obj.attr("id")]).text();
								$(obj).val(value);
							}
						}

					}
				}
			},
			beforeSend : function() {
				setTimeout(function() {
					if (!isComplete)
					{
						waitLayer.showWaitLayer("请稍后", 240, 160);
					}

				}, 500);

			},
			complete : function() {
				isComplete = true;
				waitLayer.closeDialog();
			}
		});
	};


	/**
	 * 修改后保存
	 * @param successCallBack 数据保存成功后的回调函数
	 * @param checkData 数据保存前的数据检查回调函数 返回true才进行提交
	 */
	this.update = function(successCallBack, checkData) {
		if (checkData != null && !checkData())
		{
			return;
		}
		var childs = $("#" + entityName + " span").children();
		var queryItem = this.entity;
		for (i = 0; i < childs.length; i++)
		{
			var obj = $(childs[i]);
			if (typeof (obj.attr("id")) != "undefined")
			{
				queryItem[obj.attr("id")] = obj.val();
			}
		}
		var __this = this;
		var actionUrl = "/" + entityName + "/update";
		var waitLayer = new LayerDialog();
		var isComplete = false;
		$.ajax({
			url : actionUrl,
			async : false,
			type : 'post',
			dataType : 'json',
			data : queryItem,
			success : function(data) {
				if (data.result == "success")
				{
					if (successCallBack != null)
					{
						successCallBack(data);
					}
				} else
				{
					showMessage(data.message, function() {
						return true;
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// TODO 关闭alert
				//alert(XMLHttpRequest.status);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
			},
			beforeSend : function() {
				setTimeout(function() {
					if (!isComplete)
					{
						waitLayer.showWaitLayer("请稍后", 240, 160);
					}

				}, 500);

			},
			complete : function() {
				isComplete = true;
				waitLayer.closeDialog();
			}
		});
	};

	/**
	 * 新增保存
	 * @param successCallBack 数据保存成功后的回调函数
	 * @param checkData 数据保存前的数据检查回调函数 返回true才进行提交
	 */
	this.save = function(successCallBack, checkData) {
		if (checkData != null && !checkData())
		{
			return;
		}
		var childs = $("#" + entityName + " span").children();
		var queryItem = new Object;
		for (i = 0; i < childs.length; i++)
		{
			var obj = $(childs[i]);
			if (typeof (obj.attr("id")) != "undefined")
			{
				queryItem[obj.attr("id")] = obj.val();
			}
		}
		var actionUrl = "/" + entityName + "/save";
		var waitLayer = new LayerDialog();
		var isComplete = false;
		$.ajax({
			url : actionUrl,
			async : false,
			type : 'post',
			dataType : 'json',
			data : queryItem,
			success : function(data) {
				if (data.result == "success")
				{
					if (successCallBack != null)
					{
						successCallBack(data);
					}
				} else
				{
					showWarningInfo(data.message);
				}
			},
			beforeSend : function() {
				setTimeout(function() {
					if (!isComplete)
					{
						waitLayer.showWaitLayer("请稍后", 240, 160);
					}

				}, 500);

			},
			complete : function() {
				isComplete = true;
				waitLayer.closeDialog();
			}
		});
	};
}