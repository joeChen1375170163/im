
// 

function StringBuilder()  
{  
   this.data=Array("");  
}  
StringBuilder.prototype.append=function()  
{  
   this.data.push(arguments[0]);  
   return this;  
};  
StringBuilder.prototype.toString=function()  
{  
   return this.data.join("");  
};  

/**
 * 数据表分页
 * @returns {TablePage}
 */
function TablePage()
{
	this.pageSize = 10;
	this.pageNum = 0;
	this.pageSum = 0;
	this.rowSum  = 0;
	this.pageURL = "";
	
	this.getHtml = function(pageInfo)
	{
		this.pageSize = pageInfo.pageSize;
		this.pageNum = pageInfo.pageNum;
		this.pageSum = pageInfo.pageSum;
		this.rowSum = pageInfo.rowSum;
		var html = new StringBuilder();
		html.append("<div class='zxui-page'><a class='homePage' href='javascript:void(0);' onclick='table.onFristPage()' title='首页'></a>");
		if(parseInt(this.pageNum) > 1)
		{
			html.append("<a class='prev' href='javascript:void(0);' onclick='table.onLastPage();' title='上一页'></a>");
		}
		else
		{
			html.append("<a class='prev' href='javascript:void(0);' title='上一页'></a>");
		}
		html.append("<span class='zxui-pageSize'>");
		/*html.append("<select onchange='table.onPageSizeChanged(this);' id='zxui-pageSize' class='zzh' style='width:50px;'>");
		for(i = 1; i < 20; i ++)
		{
			var value = i * 5;
			if(value == parseInt(this.pageSize))
			{
				html.append("<option value='" + value + "' selected >" + value+ "</option>");
			}
			else
			{
				html.append("<option value='" + value + "'>" + value+ "</option>");
			}
		}
		html.append("</select>");*/
		html.append("<span class='paging'>" + this.pageNum +"</span>");
		html.append("<span style='height:26px;line-height:26px;color:#555a5d;'>/</span>");
		html.append("<span style='height:26px;line-height:26px;color:#555a5d;'>"+this.pageSum+"</span>");
		html.append("</span>");
		if(parseInt(this.pageNum) < parseInt(this.pageSum))
		{
			html.append("<a class='next' href='javascript:void(0);' onclick='table.onNextPage();' title='下一页'></a>");
		}
		else
		{
			html.append("<a class='next' href='javascript:void(0);' title='下一页'></a>");
		}
		html.append("<a class='endPage' title='尾页'  onclick='table.onTheLastPage();' href='javascript:void(0);'></a>");
		html.append("<span class='pageShow' style='height:26px;line-height:26px;margin-left:10px;color:#5a6d7f'>每页显示</span>" );
		html.append("<select onchange='table.onPageSizeChanged(this);' id='zxui-pageSize' class='zzh' style='width:70px;height:26px;line-height:26px;'>");
		for(i = 1; i < 7; i ++)
		{
			var value = i * 5;
			if(value == parseInt(this.pageSize))
			{
				html.append("<option value='" + value + "' selected >" + value+ "</option>");
			}
			else
			{
				html.append("<option value='" + value + "'>" + value+ "</option>");
			}
		}
		html.append("</select>");
		html.append("<span class='strip' style='height:26px;line-height:26px;color:#5a6d7f'>条</span>" );
		/*if(parseInt(this.pageNum) < parseInt(this.pageSum))
		{
			html.append("<a class='icon-pageNext-block' href='javascript:void(0);' onclick='table.onNextPage();' title='下一页'></a>");
		}
		else
		{
			html.append("<a class='icon-pageNext-block' href='javascript:void(0);' title='下一页'></a>");
		}
		
		html.append("<a class='icon-pageLast-block' title='尾页'  onclick='table.onTheLastPage();' href='javascript:void(0);'></a>");*/
		html.append("<span class='pageSizeJump'>");
		html.append("<span style='margin-left:10px;height:26px;line-height:26px;color:#5a6d7f'>跳转至</span>");
		html.append("<select onchange='table.onPageNumChanged(this);' style='width:70px;height:26px;line-height:26px;'>");
		var pageIndex = parseInt(this.pageNum);
		for(i = 1; i < parseInt(this.pageSum) + 1; i ++)
		{
			if(pageIndex == i)
			{
				html.append("<option value='" + i + "' selected>" + i + "</option>");
			}
			else
			{
				html.append("<option value='" + i + "'>" + i + "</option>");
			}
		}
		html.append("</select>");
		
		html.append("<span style='margin-right:20px;height:26px;line-height:26px;color:#5a6d7f'>页</span>");
		html.append("</span>");
		/*html.append("<span>第" + this.pageNum + "页/共" + this.pageSum + "页");*/
		html.append("<span style='color:#5a6d7f;height:26px;line-height:26px;'>共</span>");
		html.append("<span style='height:26px;line-height:26px;'>"+this.rowSum+"</span>");
		html.append("<span style='color:#5a6d7f;height:26px;line-height:26px;'>条记录</span>");
	    return html;
	};
}

// 数据表的单元格
function TableCell()
{
	
}
/**
 * 数据表头的单元格
 * @param name           列标题
 * @param propertyName   列绑定的属性名称
 * @param cellWidth      列宽度
 * @param html           html 默认显示属性值
 * @param title 是否显示tips效果
 */
function TableHeadCell(name,propertyName,cellWidth,html,title)
{
	this.width = cellWidth;
	this.name  = name;
	this.propertyName = propertyName;
	this.html = html;
	this.title = title;
}
// 数据表的头
function TableHeadRow()
{
	this.displayCol = 0;
	this.cells = new Array();
	this.addColumn = function(name,propertyName,colWidth,html,title)
	{
		var col = new TableHeadCell(name,propertyName,colWidth,html,title);
		if(colWidth != 0)
		{
			this.displayCol += 1;
		}
		this.cells.push(col);
	};
	this.delAllColumn = function()
	{
		this.cells = null;
		this.cells = new Array();
	};
	
	this.getHtml = function()
	{
		var html = "<thead><tr>";
		var colHtml = "";
		var cell;
		for(var i = 0; i < this.cells.length; i ++)
		{
			cell = this.cells[i];
			colHtml = "<th ";
			if(cell.width != null && cell.width != '')
			{
				if(cell.width == 0 || cell.width == '0%' || cell.width == '0px')
				{
					colHtml += " style='display: none;' ";
				}
				else
				{
					colHtml += " width = '" + cell.width + "'";
				}
				
			}
			//colHtml += " title='"+cell.name+"'>";
			//title只显示中文
			//colHtml += " title='"+cell.name.replace(/[^\u4e00-\u9fa5]/gi,"")+"'>";
			colHtml += " title=''>"; // 表头不加title
			colHtml += cell.name + "</th>";
			html = html + colHtml;
		}
		html += "</tr></thead>";
		return html;
	};
}
// 数据表的行
function TableRow()
{
	var cells;
	this.setCells = function(cells)
	{
		this.cells = cells;
	};
	this.getHtml = function(rowData,rowNo)
	{
		var html = "<tr>";
	
		var colHtml = "";
		var cell;
		var txt = "";
		for(var i = 0; i < this.cells.length; i ++)
		{
			txt = "";
			cell = this.cells[i];
			// 如果单元中填充的html将所有属性值替换
			if(typeof(cell.html) != "undefined" && cell.html != '')
			{
				if(cell.propertyName == "indexNo")
				{
					itemHtml = cell.html;
					var keys = cell.html.match(/\[.*?\]/g);
					for(k = 0; k < keys.length;k ++)
					{
						key = keys[k];
						key = key.replace("[","").replace("]","");
						if(key == "indexNo")
						{
							reg =  new RegExp("\\x5B"+ key +"\\x5D","g");
							itemHtml = itemHtml.replace(reg,rowNo);
						}
						else
						{
							reg =  new RegExp("\\x5B"+ key +"\\x5D","g");
							itemHtml = itemHtml.replace(reg,rowData[key]);
						}
						
					}
				}
				else
				{
					reg =  new RegExp("\\x5B"+ cell.propertyName +"\\x5D","g");
					itemHtml = cell.html.replace(reg,rowData[cell.propertyName]);
				}
				
			}
			else
			{
				if(cell.propertyName == "indexNo")
				{
					itemHtml = "" + rowNo;
				}
				else
				{
					if(cell.title === true)
					{
						txt = rowData[cell.propertyName];
					}
					
					itemHtml = "" + rowData[cell.propertyName];
				}
			}
			
			
			/**
			 * 2016-01-15
			 * add  如果返回undefined或空，则返回""
			 */
			if(itemHtml=="null" || itemHtml=="NULL" || itemHtml==undefined || itemHtml=="undefined"){
				itemHtml="";
			}
			if(txt=="null" || txt=="NULL" || txt==undefined || txt=="undefined"){
				txt="";
			}
			
			
			if(cell.width != null && (cell.width == 0 || cell.width == '0%' || cell.width == '0px'))
			{
				colHtml = "<td style='display: none;' >" +itemHtml + "</td>";
			}
			else
			{
				colHtml = "<td title=\""+txt+"\">" +itemHtml + "</td>";
			}
			
			html = html + colHtml;
		}
		html += "</tr>";
		return html;
	};
}
// 数据表对象

/**
 * 数据表类
 * @param entityName    类实体名称
 * @param containerId   显示数据表的容器ID
 * @param tableId       表的ID
 * @param hidePageCtrl  隐藏翻页控件
 */
function DataTable(entityName,containerId,tableId,hidePageCtrl)
{
	if(entityName == null || containerId == null)
	{
		alert("entityName和containerId不能为空！");
		return ;
	}
	this.entityName = entityName;
	this.containerId = containerId;
	this.tableId = tableId;
	this.hidePageCtrl = hidePageCtrl;
	this.tableHead = new TableHeadRow();
	var _self = this;
	
	/**
	 * 添加索引列
	 * @param colWidth 列宽
	 * @param html     显示的内容，默认为列号
	 */
	this.addIndexColumn=function(colWidth,html,headHtml)
	{
		if(null == headHtml)
		{
			this.addColumn("序号","indexNo",colWidth,html);
		}
		else
		{
			this.addColumn(headHtml,"indexNo",colWidth,html);
		}
		
	};
	
	/**
	 * 增加表列
	 * @param name 列标题
	 * @param propertyName 列绑定的类属性名
	 * @param colWidth 列宽
	 * @param html 列的内容，默认为属性值
	 * @param title 是否显示tips效果
	 */
	this.addColumn = function(name,propertyName,colWidth,html,title)
	{
		var titleTemp;
		if(title == undefined || title == null)
		{
			titleTemp = true;
		}
		else
		{
			titleTemp = title;
		}
		this.tableHead.addColumn(name, propertyName, colWidth,html,titleTemp);
	};
	/**
	 * 填充数据表的空行
	 * @param rowCount 要填充的行数
	 */
	this.fullTable = function(rowCount)
	{
		if(this.tableHead == null || this.tableHead.cells == null || this.tableHead.cells.length <= 0)
		{
			return;
		}
		var tableHtml = "<table border='0' cellspacing='0' cellpadding='0' class='table-bordered'";
		if(typeof(tableId) != "undefined" && tableId != null)
		{
			tableHtml += "id='" + tableId + "'";
		}
		else
		{
			tableHtml += ">";
		}
		
		tableHtml +=  this.tableHead.getHtml();
		
		
		var rowHtml = "";
		// 判断第一列是否为序号
		if(this.tableHead.cells[0].propertyName == "indexNo")
		{
			for(var col = 1; col < this.tableHead.cells.length; col ++)
			{
				rowHtml += "<td> </td>";
			}
			// 填充空行
			for(var i = 0; i < rowCount; i ++)
			{
				tableHtml +=  "<tr><td>" + (i + 1) + "</td>" + rowHtml + "</tr>";
			}
		}
		else
		{
			for(var col = 0; col < this.tableHead.cells.length; col ++)
			{
				rowHtml += "<td> </td>";
			}
			// 填充空行
			for(var i = 0; i < rowCount; i ++)
			{
				tableHtml +=  "<tr>" + rowHtml + "</tr>";
			}
		}
		
		
		var pageInfo = new Object();
		pageInfo.pageSize = 15;
		pageInfo.pageNum = 0;
		pageInfo.pageSum = 0;
		pageInfo.rowSum  = 0;
		
		var page = new TablePage();
		
		tableHtml += "<tfoot><tr><td align='right' colspan = '" + this.tableHead.displayCol + "'>" + page.getHtml(pageInfo) + "</td></tr></tfoot>";
		tableHtml += "</table>";
		
		$("#"+this.containerId).html(tableHtml);
	};
	
	/**
	 * 填充数据表
	 * @param dataPage 后台返回的数据对象
	 */
	this.fullData = function(dataPage)
	{
		
		this.tableRows = new Array();
		this.pageInfo = new Object();
		this.pageInfo.pageSize = dataPage.pageSize;
		this.pageInfo.pageNum = dataPage.pageNum;
		this.pageInfo.pageSum = dataPage.pageSum;
		this.pageInfo.rowSum = dataPage.rowSum;
		this.pageInfo.hql      = dataPage.hql;
		this.pageInfo.queryParameter =dataPage.queryParameter;
		var data = dataPage.dataList;
		var dataItem;
		var tableRow = new TableRow();
		tableRow.setCells(this.tableHead.cells);
		if(typeof(data) == "undefined" || data == null || data.length < 1)
		{
			this.tableRows.push("<tr><td  colspan='"+ this.tableHead.cells.length +"' align='center' >没有查到，请确认您当前的查询条件是否正确。</td></tr>");
		}
		else
		{
			for(var i = 0; i < data.length; i ++)
			{
				dataItem = data[i];
				this.tableRows.push(tableRow.getHtml(dataItem,i + 1));
			}
		}
		
		var page = new TablePage();
		this.tableFoot = "<tr><td align='right' colspan = '" + this.tableHead.displayCol + "'>" + page.getHtml(dataPage) + "</td></tr>";
		
		$("#"+this.containerId).html(this.getHtml());
		// 设置表格行点击函数
		var _this = this;
		$("#" + this.tableId + " tbody tr").click(
			function(e)
			{
				//$("#" + _this.tableId + " td").css("background","#FFF");
				//$(e.delegateTarget.cells).css("background","#FFF");
				if(_this.onRowClick != null)
				{
					// 调用行点击回调函数
					_this.onRowClick(e.delegateTarget.cells);
				}
			}
		);
	};

	/**
	 * 设置点击行的回调函数
	 * onRowClick回调函数
	 * onRowClick(cells) cells是td的数组
	 */
	this.setOnRowClick=function(onRowClick)
	{
		this.onRowClick = onRowClick;
	};
	/**
	 * 获取数据表的html
	 */
	this.getHtml = function()
	{
		
		var tableHtml = "<table border='0' cellspacing='0' cellpadding='0' class='table-bordered'";
		
		if(typeof(tableId) != "undefined" && tableId != null)
		{
			tableHtml += "id='" + tableId + "'>";
		}
		else
		{
			tableHtml += ">";
		}
		// 表头
		tableHtml +=  this.tableHead.getHtml();
		// 表行
		tableHtml += "<tbody>";
		for(var i = 0; i < this.tableRows.length; i ++)
		{
			dataRow = this.tableRows[i];
			tableHtml += dataRow;
		}
		tableHtml += "</tbody>";
		// 表尾
		// 填充分页
		if(this.hidePageCtrl == null && !this.hidePageCtrl)
		{
			tableHtml += "<tfoot>" + this.tableFoot + "</tfoot>";
		}
		tableHtml += "</table>";
		return tableHtml;
	};
	
	/**
	 * 处理数据表的pagesize改变
	 * @param select pageSize的select对象
	 */
	this.onPageSizeChanged = function(select)
	{
		this.pageQuery(select.value, this.pageInfo.pageNum);
	};
	/**
	 * 处理数据表的页数改变
	 * @param select pageNum的select对象
	 */
	this.onPageNumChanged =function (select)
	{
		this.pageQuery(this.pageInfo.pageSize, select.value);
	};

	/**
	 * 处理分页查询
	 * @param pageSize 页大小
	 * @param pageNum 显示的页号
	 */
	this.pageQuery = function(pageSize,pageNum)
	{
		var waitLayer  =  new LayerDialog();
		var _this = this;
		var entity = new Object();
		entity = this.pageInfo.queryParameter.params;
		entity["hql"] = this.pageInfo.hql;
		entity["pageSize"] = pageSize;
		entity["pageNum"] = pageNum;
		var isComplete = false;
		$.ajax({
			url:_this.pageUrl,
			async:true,
			type:'get',
			dataType:'json',
			data:entity,
			success:function(data)
			{
				_this.fullData(data,table.containerId);
				if(_this.OnFulledRowCallback != null)
				{
					_this.runfulledRowCallback();
				}
				if(_this.OnFulled != null)
				{
					_this.OnFulled(_this);
				}
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
	
	/**
	 * 显示首页
	 */
	this.onFristPage = function()
	{
		this.pageQuery(this.pageInfo.pageSize, 1);
	};
	/**
	 * 显示下一页
	 */
	this.onNextPage = function()
	{
		var pageNum = parseInt(this.pageInfo.pageNum);
		if(pageNum < parseInt(this.pageInfo.pageSum))
		{
			pageNum += 1;
			this.pageQuery(this.pageInfo.pageSize, pageNum);
		}
		else
		{
			alert("已经是最后一页了！");
		}
	};
	/**
	 * 显示上一页
	 */
	this.onLastPage =function()
	{
		var pageNum = parseInt(this.pageInfo.pageNum);
		if(pageNum >= 1)
		{
			pageNum -= 1;
			this.pageQuery(this.pageInfo.pageSize, pageNum);
		}
		else
		{
			alert("已经是第一页了！");
		}
	};
	
	/**
	 * 显示最后一页
	 */
	this.onTheLastPage =function()
	{
		this.pageQuery(this.pageInfo.pageSize, this.pageInfo.pageSum);
	};
	
	/**
	 * 根据条件查询
	 */
	this.queryDataByParameter=function(queryParamtemer,urlAction)
	{
		var waitLayer  =  new LayerDialog();
		if(urlAction == null)
		{
			this.pageUrl = "../" + this.entityName + "Action_queryList.action";
		}
		else
		{
			this.pageUrl = urlAction;
		}
		var _this = this;
    	var isComplete = false;
    	$.ajax({
				url:this.pageUrl,
				async:false,
				type:'post',
				dataType:'json',
				data:queryParamtemer,
				success:function(data)
				{
					_this.fullData(data);
					if(_this.OnFulledRowCallback != null)
					{
						_this.runfulledRowCallback();
					}
					if(_this.OnFulled != null)
					{
						_this.OnFulled(_this);
					}
				},
				error:function(event,xhr,options,exc)
				{
					// TODO 关闭alert
					//alert("Error");
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
	
	/**
	 * 查询数据
	 * @param queryDivID 查询条件的容器ID
	 * @param urlAction 查询处理的后台url 默认为实体Action的queryList函数
	 */
	this.queryData=function(queryDivId,urlAction)
	{
		if(queryDivId == null)
		{
			alert("queryDivId 不能为空!");
			return ;
		}
		var waitLayer  =  new LayerDialog();
		if(urlAction == null)
		{
			this.pageUrl = "/" + this.entityName + "/queryList";
		}
		else
		{
			this.pageUrl = urlAction;
		}
		var childs = $("#" + queryDivId + " span").children();
    	var queryItem = new Object;
    	for(i = 0; i < childs.length;i ++)
    	{
    		var obj = $(childs[i]);
    		if(typeof(obj.attr("id")) != "undefined")
    		{
    			queryItem[obj.attr("id")] = obj.val();
    		}
    	}
    	//查询时回传页数和每页个数
    	if(typeof(this.pageInfo)!="undefined" && this.pageInfo !=null)
    	{
    		if(this.pageInfo.hasOwnProperty("pageNum") && typeof(this.pageInfo.pageNum)!="undefined" &&this.pageInfo.pageNum != null)
    		{
    			queryItem['pageNum'] = this.pageInfo.pageNum;
    			
    		}
    		if(this.pageInfo.hasOwnProperty("pageSize") && typeof(this.pageInfo.pageSize)!="undefined"&& this.pageInfo.pageSize)
    		{
    			queryItem['pageSize'] = this.pageInfo.pageSize;
    		}
    	}
    	var _this = this;
    	var isComplete = false;
    	$.ajax({
				url:this.pageUrl,
				async:false,
				type:'get',
				dataType:'json',
				data:queryItem,
				success:function(data)
				{
					_this.fullData(data);
					if(_this.OnFulledRowCallback != null)
					{
						_this.runfulledRowCallback();
					}
					if(_this.OnFulled != null)
					{
						_this.OnFulled(_this);
					}
				},
				error:function(event,xhr,options,exc)
				{
					// TODO 关闭alert
					//alert("Error");
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
	
	/**
	 * 数据填充完成后，遍历行的回调函数
	 */
	this.setOnFulledRowCallback=function(onFulledRowCallback)
	{
		this.OnFulledRowCallback = onFulledRowCallback;
	};
	
	/**
	 * 1. 执行遍历行的回调函数
	 * 此函数在每次填充数据后都会判断运行（包含翻页填充）
	 */
	this.runfulledRowCallback=function()
	{
		$("#" + this.tableId + " tbody tr").each(this.OnFulledRowCallback);
	}
	
	/**
	 * 2. 数据填充完成后的回调函数（包含翻页填充）
	 */
	this.setOnFulled=function(onFulled)
	{
		this.OnFulled = onFulled;
	};
	
	
	//======以下两个函数已废弃，为了兼容，暂不删除
	// 增加遍历行的回调函数
	this.RowCallback=function(rowCallback)
	{
		$("#" + this.tableId + " tbody tr").each(rowCallback);
	};
	
	// 增加数据填充完成后，遍历行的回调函数
	this.setRowCallback=function(rowCallback)
	{
		this.OnFulled = function()
		{
			$("#" + this.tableId + " tbody tr").each(function(index,element){
				if (null != rowCallback) {
					rowCallback(index,element);
				}
			});
		}
	};
	
}