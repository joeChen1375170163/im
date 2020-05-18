/**
 * 下拉选择
 * @returns {DataSelect}
 */
function DataSelect()
{
	
	this.loadList=function(url,selectId,containerId,queryParameter,name,value,tip,selectKey)
	{
		var _this = this;
		$.ajax(
	    		{
					url:url,
					async:false,
					type:'post',
					data:queryParameter,
					dataType:'json',
					success:function(data)
					{
						_this.fullList(selectId,containerId, data,name,value,tip,selectKey);
					}
				});
	};
	/**
	 * 加载下拉选择的数据
	 * @param selectId    控件ID
	 * @param dictType    字典表的类型
	 * @param containerId 容器类型
	 * @param tip         select的提示信息
	 * @param selectKey   默认选择的key
	 * @param url         自定义数据url
	 */
	this.loadData=function(selectId,dictType,containerId,tip,selectKey,url)
	{
		this.selectId = selectId;
		if(null == url)
		{
			this.url = '../newDictAction_getDict.action';
		}
		else
		{
			this.url = url;
		}
		var _this = this;
		$.ajax({
			url:this.url,
			async:false,
			type:'post',
			data:{dictType:dictType},
			dataType:'json',
			success:function(data)
			{
				_this.fullData(containerId, data,tip,selectKey);
			}
		});	
	};
	/**
	 * 加载下拉选择的数据
	 * @param selectId    控件ID
	 * @param tableName   字典表的类型
	 * @param nameField   显示字段
	 * @param valueField  取值字段
	 * @param containerId 容器类型
	 * @param tip         select的提示信息
	 * @param selectKey   默认选择的key
	 * @param url         自定义数据url
	 */
	this.loadTableData=function(selectId,tableName,nameField,valueField,maxSize,containerId,tip,selectKey,url)
	{
		this.selectId = selectId;
		if(null == url)
		{
			this.url = '../dictAction_getTableData.action';
		}
		else
		{
			this.url = url;
		}
		var _this = this;
		$.ajax({
			url:this.url,
			async:false,
			type:'post',
			data:{tableName:tableName,nameField:nameField,valueField:valueField,maxSize:maxSize},
			dataType:'json',
			success:function(data)
			{
				_this.fullData(containerId, data,tip,selectKey);
			}
		});	
	};
	
	this.loadDataByAction=function(selectId,url,queryParameter,containerId,tip,selectKey)
	{
		this.selectId = selectId;
		if(null == url)
		{
			return ;
		}
		else
		{
			this.url = url;
		}
		var _this = this;
		$.ajax({
			url:this.url,
			async:false,
			type:'post',
			data:queryParameter,
			dataType:'json',
			success:function(data)
			{
				_this.fullData(containerId, data,tip,selectKey);
			}
		});	
	};
	/**
	 * 填充数据
	 * @param containerId 容器ID
	 * @param dictList    数据
	 * @param selectTip   select提示
	 * @param selectKey   默认选择
	 */
	this.fullData = function(containerId,dictList,selectTip,selectKey)
	{
		$("#"+containerId).html(this.getHtml(dictList,selectTip,selectKey));
	};
	
	this.fullSelect=function(selectId,containerId,selectTip)
	{
		var html = "<select class='selectStyle' ";
		
		if(selectId != null && selectId != '')
		{
			html += " id='" + selectId + "' ";
		}
		html += " >";
		
		if(selectTip != null && selectTip != '')
		{
			html += "<option value='' selected >" + selectTip + "</option>";
		}
		
		html+= "</select>";
		$("#"+containerId).html(html);
	};
	this.fullList=function(selectId,containerId,dictList,name,value,selectTip,selectKey)
	{
		var html = "<select class='selectStyle' ";
		
		if(selectId != null && selectId != '')
		{
			html += " id='" + selectId + "' ";
		}
		html += " >";
		
		if(selectTip != null && selectTip != '')
		{
			if(selectKey == null)
			{
				html += "<option value='' selected >" + selectTip + "</option>";
			}
			else
			{
				html += "<option value=''>" + selectTip + "</option>";
			}
		}
		// 添加option
		for(i = 0; i < dictList.length; i ++)
		{
			if(selectKey == dictList[i][value])
			{
				html += "<option value='" + dictList[i][value] + "' selected >" + dictList[i][name] + "</option>";
			}
			else
			{
				html += "<option value='" + dictList[i][value] + "'>" + dictList[i][name] + "</option>";	
			}
		}
		
		html+= "</select>";
		$("#"+containerId).html(html);
	};
	/**
	 * 获取控件的html
	 * @param dictList    数据
	 * @param selectTip   select提示
	 * @param selectKey   默认选择
	 */
	this.getHtml=function(dictList,selectTip,selectKey)
	{
		var html = "<select class='selectStyle' ";
	
		if(this.selectId != null && this.selectId != '')
		{
			html += " id='" + this.selectId + "' ";
		}
		html += " >";
		
		if(selectTip != null && selectTip != '')
		{
			if(selectKey == null)
			{
				html += "<option value='' selected >" + selectTip + "</option>";
			}
			else
			{
				html += "<option value=''>" + selectTip + "</option>";
			}
		}
		// 添加option
		for(i = 0; i < dictList.length; i ++)
		{
			if(selectKey == dictList[i].value)
			{
				html += "<option value='" + dictList[i].value + "' selected >" + dictList[i].name + "</option>";
			}
			else
			{
				html += "<option value='" + dictList[i].value + "'>" + dictList[i].name + "</option>";	
			}
		}
		
		html+= "</select>";
		return html;
	};
	
}