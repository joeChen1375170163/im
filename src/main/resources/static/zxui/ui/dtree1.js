
/**
 * 处理
 */

/**
 * 存储过程返回的节点必须包含一下属性
 * id				当前节点的ID 
 * name             节点的名称
 * url              点击节点跳转的URL
 * parentId        父级节点的ID
 * type             节点的类型
 * childrenSize    包含子节点的数量
 * selected         节点是否选中
 * 
 */

function zxwlTree(procedureName,actionUrl,maxSize)
{
	this.onTitleClick = null;
	this.onSelected = null;
	this.onCancelSelect=null;
	this.procedureName = procedureName;
	this.maxSize = 1000;
	if(actionUrl != null)
	{
		this.actionUrl = actionUrl;
	}
	else
	{
		this.actionUrl = "./treeNodeAction_queryChildNode.action";
	}
	
	if(maxSize != null)
	{
		this.maxSize = maxSize;
	}
	
	this.showTree=function(treeId,containerId,node)
	{
		this.treeId = treeId;
		this.treeContainer = containerId;
		// 加载树的根节点
		this.loadRootNode(node);
	};
	
	this.insertRootNode = function(rootNode)
	{
		//湖北省委数据有特殊符号出来
		var rid = rootNode.id+"";
		rid = rid.replace(/@/,"安");
		rid = rid.replace(".","点");
		rootNode.id = rid;
		__this = this;
		// 查找父节点
		var ulNode = $("#_TN_U" + rootNode.parentId);
		//生成顶级节点
		var HeardNode = $("<div class='node-heard'></div>");
		$(ulNode).append(HeardNode);
		//判断有没有父节点
		if(ulNode != null && ulNode.length > 0)
		{
			// 已经存在UL
			var liNode = $("<li></li>");
			$(liNode).attr("id","_TN_L" + rootNode.id);
			var childContext = null;
			if(rootNode.childrenSize > 0)
			{
				//判断最后一个节点
				if(rootNode.isLast)
				{
					childContext = $("<div class='node-parent father last-node'></div>");
				}else
				{
					childContext = $("<div class='node-parent father'></div>");
				}
			}
			else
			{
				//判断最后一个节点
				if(rootNode.isLast){
					childContext = $("<div class='node-line children last-node'></div>");
				}else{
					childContext = $("<div class='node-line children'></div>");
				}
			}
			
			var lineSpan = $("<span class='node-child'></span>");
			$(lineSpan).click(function(){return __this.onNodeClick(rootNode);});
			$(childContext).append(lineSpan);
			var dName=rootNode.departmentName;
			if(dName.length>8){
				dName=dName.substring(0,8)+"...";
			}
			$(childContext).attr("id","_TN_C" + rootNode.id);
			var checkBox = $("<input type='checkbox' class='node-checkbox'></input>");
			var context = $("<a class='node-text'>" + dName + "</a>");
			$(context).attr("title",rootNode.departmentName);
			$(context).click(function(event){return __this._onTitleClick(rootNode);});
			$(checkBox).attr("id","_TN_CK" + rootNode.id);
			$(checkBox).attr("treeNodeId",rootNode.id);
			$(checkBox).attr("treeNodeName",rootNode.departmentName);
			$(checkBox).attr("treeNodeUrl",rootNode.url);
			$(checkBox).click(function(event){return __this.onCheckBoxClick(rootNode);});
			
			if(rootNode.selected > 0)
			{
				$(checkBox).addClass("node-checkbox-active");
			}
			
			$(childContext).append(checkBox);
			$(childContext).append(context);
			$(liNode).append(childContext);
			$(ulNode).append(liNode);
		}
		else 
		{
			// 先增加UL
			var ulNode = $("<ul></ul>");
			$(ulNode).attr("id","_TN_U" + rootNode.parentId);
			//生成顶级节点
			var HeardNode = $("<div class='node-heard'></div>");
			$(ulNode).append(HeardNode);
			//判断有没有父节点
			// 已经存在UL
			var liNode = $("<li></li>");
			$(liNode).attr("id","_TN_L" + rootNode.id);
			var childContext = null;
			if(rootNode.childrenSize >0)
			{
				//判断最后一个节点
				if(rootNode.isLast){
					childContext = $("<div class='node-parent father last-node'></div>");
				}else{
					childContext = $("<div class='node-parent father'></div>");
				}
			}
			else
			{
				//判断最后一个节点
				if(rootNode.isLast){
					childContext = $("<div class='node-line children last-node'></div>");
				}else{
					childContext = $("<div class='node-line children'></div>");
				}
			}
			
			var lineSpan = $("<span class='node-child'></span>");
			$(lineSpan).click(function(){return __this.onNodeClick(rootNode);});
			$(childContext).append(lineSpan);
			$(childContext).attr("id","_TN_C" + rootNode.id);
			var dName=rootNode.departmentName;
			if(dName.length>8){
				dName=dName.substring(0,8)+"...";
			}
			var checkBox = $("<input type='checkbox' class='node-checkbox'></input>");
			var context = $("<a class='node-text'>" + dName + "</a>");
			$(context).attr("title",rootNode.departmentName);
			$(context).click(function(event){return __this._onTitleClick(rootNode);});
			$(checkBox).attr("id","_TN_CK" + rootNode.id);
			$(checkBox).attr("treeNodeId",rootNode.id);
			$(checkBox).attr("treeNodeName",rootNode.departmentName);
			$(checkBox).attr("treeNodeUrl",rootNode.url);
			$(checkBox).click(function(event){return __this.onCheckBoxClick(rootNode);});
			if(rootNode.selected > 0)
			{
				$(checkBox).addClass("node-checkbox-active");
			}
			
			$(childContext).append(checkBox);
			$(childContext).append(context);
			$(liNode).append(childContext);
			$(ulNode).append(liNode);
			$("#"+this.treeContainer).html(ulNode);
		}
				
	};
	this.insertNodeToTree= function(node)
	{
		//湖北省委数据有特殊符号出来
		var parentId = node.parentId;
		parentId = parentId.replace(/@/,"安");
		parentId = parentId.replace(".","点");
		node.parentId = parentId;
		
		var rid = node.id+"";
		rid = rid.replace(/@/,"安");
		rid = rid.replace(".","点");
		node.id = rid;
		
		__this = this;
		// 查找父节点
		var ulNode = $("#_TN_U" + node.parentId);
		
		if(ulNode != null && ulNode.length > 0)
		{
			// 已经存在UL
			var liNode = $("<li></li>");
			$(liNode).attr("id","_TN_L" + node.id);
			var childContext = null;
			if(node.childrenSize >0)
			{
				//判断最后一个节点
				if(node.isLast)
				{
					childContext = $("<div class='node-parent father last-node'></div>");
				}else
				{
					childContext = $("<div class='node-parent father'></div>");
				}
			}
			else
			{
				//判断最后一个节点
				if(node.isLast){
					childContext = $("<div class='node-line children last-node'></div>");
				}else{
					childContext = $("<div class='node-line children'></div>");
				}
			}
			
			
			var lineSpan = $("<span class='node-child'></span>");
			$(lineSpan).click(function(){return __this.onNodeClick(node);});
			$(childContext).append(lineSpan);
			var dName=node.departmentName;
			if(dName.length>8){
				dName=dName.substring(0,8)+"...";
			}
			$(childContext).attr("id","_TN_C" + node.id);
			
			var checkBox = $("<input type='checkbox' class='node-checkbox'></input>");
			var context = $("<a class='node-text'>" + dName + "</a>");
			$(context).attr("title",node.departmentName);
			$(context).click(function(event){return __this._onTitleClick(node);});
			$(checkBox).attr("id","_TN_CK" + node.id);
			$(checkBox).attr("treeNodeId",node.id);
			$(checkBox).attr("treeNodeName",node.departmentName);
			$(checkBox).attr("treeNodeUrl",node.url);
			$(checkBox).click(function(event){return __this.onCheckBoxClick(node);});
			
			if(node.selected > 0)
			{
				$(checkBox).addClass("node-checkbox-active");
			}
//			if(node.areaType == "2" || node.areaType == "5" || node.areaType == "8" ){
//				$(checkBox).css("display","none");
//			}
			$(childContext).append(checkBox);
			$(childContext).append(context);
			$(liNode).append(childContext);
			$(ulNode).append(liNode);
		}
		else 
		{
			parentNode =  $("#_TN_L" + node.parentId);
			
			if(parentNode != null && parentNode.length > 0)
			{
				parentNode = parentNode[0];
				// 先增加UL
				var ulNode = $("<ul></ul>");
				$(ulNode).attr("id","_TN_U" + node.parentId);
				
				// 已经存在UL
				var liNode = $("<li></li>");
				$(liNode).attr("id","_TN_L" + node.id);
				var childContext = null;
				if(node.childrenSize >0)
				{
					//判断最后一个节点
					if(node.isLast)
					{
						childContext = $("<div class='node-parent father last-node'></div>");
					}else
					{
						childContext = $("<div class='node-parent father'></div>");
					}
				}
				else
				{
					//判断最后一个节点
					if(node.isLast)
					{
						childContext = $("<div class='node-line children last-node'></div>");
					}else
					{
						childContext = $("<div class='node-line children'></div>");
					}
				}
				
				var lineSpan = $("<span class='node-child'></span>");
				$(lineSpan).click(function(){return __this.onNodeClick(node);});
				$(childContext).append(lineSpan);
				var dName=node.departmentName;
				if(dName.length>8){
					dName=dName.substring(0,8)+"...";
				}
				$(childContext).attr("id","_TN_C" + node.id);
				var checkBox = $("<input type='checkbox' class='node-checkbox'></input>");
				var context = $("<a class='node-text'>" + dName + "</a>");
				$(context).attr("title",node.departmentName);
				$(context).click(function(event){return __this._onTitleClick(node);});
				$(checkBox).attr("id","_TN_CK" + node.id);
				$(checkBox).attr("treeNodeId",node.id);
				$(checkBox).attr("treeNodeName",node.departmentName);
				$(checkBox).attr("treeNodeUrl",node.url);
				$(checkBox).click(function(event){return __this.onCheckBoxClick(node);});
				
				if(node.selected > 0)
				{
					$(checkBox).addClass("node-checkbox-active");
				}
//				if(node.areaType == "2" || node.areaType == "5" || node.areaType == "8" ){
//					$(checkBox).css("display","none");
//				}
				$(childContext).append(checkBox);
				$(childContext).append(context);
				$(liNode).append(childContext);
				$(ulNode).append(liNode);
				$(parentNode).append(ulNode);
			}
		}
		
	};
	
	
	this.insertNodes = function(nodes,parentId)
	{
		
		var _length = 0;
		var _index = 0;
		if(nodes.length > 0)
		{
			_length = parseInt(nodes.length, 10);
			for(index in nodes)
			{
				_index = 1 + parseInt(index, 10);
				if(_length <= _index)
				{
					nodes[index].isLast = true;	
				}
				else
				{
					nodes[index].isLast = false;
				}
				this.insertNodeToTree(nodes[index]);
			}
		}
		else
		{
			// 没有节点
			// 去掉有子节点的样式
			$("#_TN_C" + parentId).removeClass("node-parent father");
			// 增加没子节点的样式
			$("#_TN_C" + parentId).addClass("node-line children");
		}
		//全选 反选
//		$("input[type='checkbox']").on("click",function(){
//			if($(this).parent().next().html()==null||$(this).parent().next().html()==""){
//				$(this).prev().click();
//			}
//			if($(this).is(":checked")){
//				$(this).parent().next().find("input").attr("checked",true);
//			}else{
//				$(this).parent().next().find("input").attr("checked",false);
//			}
//		});
	};
	
	this.insertRootNodes=function(rootNodes)
	{
		//for(index in rootNodes)
		//{
			//this.insertRootNode(rootNodes[index]);
		//}
		var _length = 0;
		var _index = 0;
		if(rootNodes.length > 0)
		{
			_length = parseInt(rootNodes.length, 10);
			for(index in rootNodes)
			{
				_index = 1 + parseInt(index, 10);
				if(_length <= _index)
				{
					rootNodes[index].isLast = true;	
				}
				else
				{
					rootNodes[index].isLast = false;
				}
				this.insertRootNode(rootNodes[index]);
			}
		}
	};
	
	/**
	 * 加载子节点
	 */
	this.loadChildNode=function(parentNode)
	{
		var ulNode = $("#_TN_U" + parentNode.id);
		if(ulNode.length > 0)
		{
			return ;
		}
		var waitLayer  =  new LayerDialog();
		var _this = this;
		//var entity = new Object();
		var entity = parentNode;
		entity["procedureName"] = this.procedureName;
		entity["parentId"] = parentNode.id;
		entity["maxSize"] = this.maxSize;
		var isComplete = false;
		$.ajax({
			url:this.actionUrl,
			async:false,
			type:'post',
			dataType:'json',
			data:entity,
			success:function(data)
			{
				_this.insertNodes(data,parentNode.id);
			},
			beforeSend:function()
			{
				setTimeout(function()
				{
					if(!isComplete)
					{
						waitLayer.showWaitLayer("请稍后",240, 160);
					}
					
				},500);
				
			},
			complete:function()
			{
				isComplete = true;
				waitLayer.closeDialog();
			}
		});	
	};
	
	
	this.loadRootNode=function(rootNode)
	{
		var waitLayer  =  new LayerDialog();
		var _this = this;
		var entity = rootNode;
		entity["procedureName"] = this.procedureName;
		entity["parentId"] = rootNode.parentId;
		entity["maxSize"] = this.maxSize;
		var isComplete = false;
		$.ajax({
			url:this.actionUrl,
			async:false,
			type:'post',
			dataType:'json',
			data:entity,
			success:function(data)
			{
				_this.insertRootNodes(data);
			},
			beforeSend:function()
			{
				setTimeout(function()
				{
					if(!isComplete)
					{
						waitLayer.showWaitLayer("请稍后",240, 160);
					}
					
				},500);
				
			},
			complete:function()
			{
				isComplete = true;
				waitLayer.closeDialog();
			}
		});	
	};
	// 事件处理函数
	// 点击节点事件
	this.onNodeClick=function(node)
	{
		//湖北省委数据有特殊符号出来
		var nodeId = node.id;
		nodeId = nodeId.replace("@","安");
		nodeId = nodeId.replace(".","点");
		node.id = nodeId;
		var ulNode = $("#_TN_U" + node.id);
		if(ulNode.length > 0 )
		{
			// 如果子节点已经加载就直接切换
			$(ulNode).toggle();
			$("#_TN_C" + node.id + " .node-child").toggleClass("node-open");
		}
		else
		{
			var nodeId2 = node.id;
			nodeId2 = nodeId2.replace("安","@");
			nodeId2 = nodeId2.replace("点",".");
			node.id = nodeId2;
			__this.loadChildNode(node);
			var nodeId3 = node.id;
			nodeId3 = nodeId3.replace("@","安");
			nodeId3 = nodeId3.replace(".","点");
			node.id = nodeId3;
			var div =  $("#_TN_C" + node.id);
			$("#_TN_C" + node.id + " .node-child").addClass("node-open");
		}
		$(".node-checkbox").hide();
		return false;
	};
	// 点击chechBox事件
	this.onCheckBoxClick=function(node)
	{
		var nodeCheckbox = $("#_TN_CK" + node.id);
		$(nodeCheckbox).toggleClass("node-checkbox-active");
		if($(nodeCheckbox).hasClass("node-checkbox-active"))
		{
			if(__this.onSelected != null)
			{
				var node = new Object();
				node.id = $(nodeCheckbox).attr("treeNodeId");
				node.name = $(nodeCheckbox).attr("treeNodeName");
				node.url = $(nodeCheckbox).attr("treeNodeUrl");
				__this.onSelected(node);
			}
		}
		else
		{
			if(__this.onCancelSelect != null)
			{
				var node = new Object();
				node.id = $(nodeCheckbox).attr("treeNodeId");
				node.name = $(nodeCheckbox).attr("treeNodeName");
				node.url = $(nodeCheckbox).attr("treeNodeUrl");
				__this.onCancelSelect(node);
			}
		}
	};
	
	this.getSelectNodes=function()
	{
		var nodes = new Array();
		var selectNode =  $(".node-checkbox-active");
		$(selectNode).each(function(index,obj)
				{
					var node = new Object();
					node.id = $(obj).attr("treeNodeId");
					node.name = $(obj).attr("treeNodeName");
					node.url = $(obj).attr("treeNodeUrl");
					nodes.push(node);
				});
		return nodes;
	};
	
	this._onTitleClick=function(node)
	{
		if(__this.onTitleClick != null)
		{
			__this.onTitleClick(node);
		}
	};
	
	
};