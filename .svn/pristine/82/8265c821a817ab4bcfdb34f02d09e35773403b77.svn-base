/**
 * 下拉选择
 * @returns {DataSelect}
 */
function DataSelect()
{
	var __this = this;
	this.readOnly = false;
	
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
			this.url = '../dictAction_getDict.action';
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
	/**
	 * 填充数据
	 * @param containerId 容器ID
	 * @param dictList    数据
	 * @param selectTip   select提示
	 * @param selectKey   默认选择
	 */
	this.fullData = function(containerId,dictList,selectTip,selectKey)
	{
		$("#"+containerId + " *").remove();
		$("#"+containerId).append(this.getSelect(dictList,selectTip,selectKey));
	};
	
	/**
	 * 获取控件的html
	 * @param dictList    数据
	 * @param selectTip   select提示
	 * @param selectKey   默认选择
	 */
	this.getSelect=function(dictList,selectTip,selectKey)
	{ 
		var selectSpan = $("<span class='field zxui-selectContainer'/>");
		selectSpan.click(function(event)
				{
					//取消事件冒泡  
			        event.stopPropagation();  
			        //设置弹出层的位置  
			        var offset = $(event.target).offset();  
			        //按钮的toggle,如果div是可见的,点击按钮切换为隐藏的;如果是隐藏的,切换为可见的。  
			        $(this).children(".zxui-selectDropdown").toggle();  
				});
		var nameInput =  $("<input type='text' class='zxui-selectShowText' value='--请选择--'/>");
		nameInput.blur(function(event)
				{
					$("#"+__this.selectId + "_dl").hide();
				});
		var valueInput = $("<input type='hidden'/>");
		valueInput.attr("id",this.selectId);
		nameInput.attr("id", this.selectId + "Name");
		valueInput.change=function(v)
				{
					alert(v);
				};
		var selectdl = $("<dl class='zxui-selectDropdown'/>");
		selectdl.attr("id",this.selectId + "_dl");
		// 增加一个固定项目
		var dddefalut = $("<dd>--请选择--</dd>");
		dddefalut.mousedown(function()
				{
					$("#" + __this.selectId + "Name").val("--请选择--");
					$("#" + __this.selectId).val("");
					event.stopPropagation();  
				});
		selectdl.append(dddefalut);
		
		for(i = 0; i < dictList.length; i ++)
		{
			var dd = $("<dd/>");
			if(selectKey != null && selectKey == dictList[i].value)
			{
				valueInput.val(dictList[i].value);
				nameInput.val(dictList[i].name);
			}
			dd.val(dictList[i].value);
			dd.text(dictList[i].name);
			dd.mousedown(function()
					{
						$("#" + __this.selectId + "Name").val($(this).text());
						$("#" + __this.selectId).val($(this).val());
						//event.stopPropagation();  
					});
			selectdl.append(dd);
		}
		selectSpan.append(nameInput);
		selectSpan.append(valueInput);
		selectSpan.append(selectdl);
		return selectSpan;
	};
	
}