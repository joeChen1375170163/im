/*****************************************************************
                  表单校验工具类  (wsk)       
*****************************************************************/

/**
 * 匹配Email地址
 */
function isEmail(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/);
    if(result==null)return false;
    return true;
}     

/**
 * 只能输入数字[0-9]
 */
function isDigits(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^\d+$/);
    if(result==null)return false;
    return true;
}     

/**
 * 匹配money
 */
function isMoney(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^(([1-9]\d*)|(([0-9]{1}|[1-9]+)\.[0-9]{1,2}))$/);
    if(result==null)return false;
    return true;
} 
    
/**
 * 匹配phone
 */
function isPhone(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/);
    if(result==null)return false;
    return true;
}     

/**
 * 匹配mobile
 */
function isMobile(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^1[3|4|5|7|8][0-9]\d{4,8}$/);
    if(result==null)return false;
    return true;
}     

/**
 * 联系电话(手机/电话皆可)验证   
 */
function isTel(text)
{
    if(isMobile(text)||isPhone(text)) return true;
    return false;
}

/**
 * 匹配qq
 */
function isQq(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^[1-9]\d{4,12}$/);
    if(result==null)return false;
    return true;
}     

/**
 * 匹配english
 */
function isEnglish(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^[A-Za-z]+$/);
    if(result==null)return false;
    return true;
}     

/**
 * 匹配integer
 */
function isInteger(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^[-\+]?\d+$/);
    if(result==null)return false;
    return true;
}     

/**
 * 匹配double或float
 */
function isDouble(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^[-\+]?\d+(\.\d+)?$/);
    if(result==null)return false;
    return true;
}     

/**
 * 匹配邮政编码
 */
function isZipCode(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^[0-9]{6}$/);
    if(result==null)return false;
    return true;
} 

/**
 * 匹配URL
 */
function isUrl(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\’:+!]*([^<>\"])*$/);
    if(result==null)return false;
    return true;
}

/**
 * 判断是否为合法字符(a-zA-Z0-9-_)
 */
function isRightfulString(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^[A-Za-z0-9_-]+$/);
    if(result==null)return false;
    return true;
} 


/**
 * 匹配身份证号码
 */
function isIdCardNo(num)
{
	if(num==null || num=="")
	{
		alert("请输入身份证号码。");
		return false;
	}
	//去除字符串收尾空格
	num = num.replace(/\s/ig,'');
	//大小写转换，18位身份证最后一位可能为字母，统一转换为大写，方便验证
    num = num.toUpperCase();
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
    {
        alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
        return false;
    }
    //下面分别分析出生日期和校验位
    var len, re;
    len = num.length;
    if (len == 15)
    {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);
        //检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
        var bGoodDay;
        // 15位身份证无前两位，无需考虑千年虫问题
        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay)
        {
            alert('输入的身份证号里出生日期不对！');
            return false;
        }else
        {
            //将15位身份证转成18位
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0, i;
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
            for(i = 0; i < 17; i ++)
            {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            num += arrCh[nTemp % 11];
            return true;
        }
    }
    if (len == 18)
    {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = num.match(re);

        //检查生日日期是否正确
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
        var bGoodDay;
        // 这里用getFullYear()获取年份，避免千年虫问题
        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay)
        {
            alert('输入的身份证号里出生日期不对！');
            return false;
        }else
	    {
	        //检验18位身份证的校验码是否正确。
	        var valnum;
	        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	        var nTemp = 0, i;
	        for(i = 0; i < 17; i ++)
	        {
	            nTemp += num.substr(i, 1) * arrInt[i];
	        }
	        valnum = arrCh[nTemp % 11];
	        if (valnum != num.substr(17, 1))
	        {
	            alert('18位身份证不合法！');
	            return false;
	        }
	        return true;
	    }
    }
    return false;
}

/**
 * 通过身份证号获取出生年月
 */
function getBirthdayByCardNo(cardNo)
{
	var len = cardNo.length;
    var re;
    if (len == 15)
	{
	 	re =  "19" + cardNo.substring(6,12); 
	 
	}else
	{
		re = cardNo.substring(6,14); 
	}
    return re;
}

/**
 * 通过身份证号获取性别
 */
function getSexByCardNo(cardNo)
{
	var len = cardNo.length;
    var re;
    //15位身份证号，最后一位为奇数，对应性别男，偶数对应性别女
    if (len == 15)
	{
    	re = cardNo.substring(len-1,len);
	}else //如果是18位身份证号，第17位为奇数，性别男， 偶数，性别女
	{
		re = cardNo.substring(len-2,len-1);
	}
    if(re%2 == 0)
	{
		return 1;
	}else
	{
		return 0;
	}
}

/**
 * 通过身份证获取年龄
 * @param cardNo
 */
function getAgeByCardNo(cardNo)
{
	var now = new Date();
	var year = now.getFullYear();
	var len = cardNo.length;
    var re;
    if (len == 15)
	{
    	re = "19" + cardNo.substring(6,8); 
	}else
	{
		re = cardNo.substring(6,14); 
	}
    return  Number(year) - Number(re.substring(0, 4));
}

/**
 * 匹配汉字
 */
function isChinese(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[\u4e00-\u9fa5]+$/);
    if(result==null)return false;
    return true;
} 

/**
 * 匹配中文(包括汉字和字符)
 */
function isChineseChar(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^[\u0391-\uFFE5]+$/);
    if(result==null)return false;
    return true;
}     

/**
 * 字符验证，只能包含中文、英文、数字、下划线等字符。
 */
function stringCheck(str){
    if(str==null||str=="") return false;
    var result=str.match(/^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/);
    if(result==null)return false;
    return true;
}     

/**
 * 过滤中英文特殊字符，除英文"-_"字符外
 */
function stringFilter(str)
{
    var pattern = new RegExp("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
    var rs = "";
    for (var i = 0; i < str.length; i++) {
        rs = rs + str.substr(i, 1).replace(pattern, '');
    }
    return rs;
} 

/**
 * 判断是否包含中英文特殊字符，除英文"-_"字符外
 */
function isContainsSpecialChar(str)
{
    if(str==null||str=="") return false;
    var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
    return reg.test(str);    
}

/**
 * 验证内容是否为空或者undefined
 * @param str
 * @returns {Boolean}
 */
function isBlank(str1)
{
	var str = $.trim(str1);
	if("" == str || str== null || str == undefined)
	{
		return true;
	}
	return false;
}
/**
 * 2018-1-3
 * 是否是手机号：8位或者11位
 * @param str 待验证的手机号
 * true:是
 * false:否
 */
function validatePhone(str){
	var reg=/^\d{3,16}$/;
	return reg.test(str);
	
}
/**
 * 验证字符串长度是否符合要求，可区分中英文字符
 * @param controlIdStr 输入字符串
 * @param maxLength 允许的最大长度
 * @returns {Boolean}
 */
function checkAnswer(controlIdStr,maxLength)
{
	if(!isBlank(controlIdStr))
	{
		var str = controlIdStr.replace(/[^\x00-\xff]/g,"**");
		if(str.length > parseInt(maxLength,10))
		{
			return false;
		}
	}
	return true;
}

/**
 * 限制文本框只能输入数字或者数字、小数点，通过class属性绑定
 */
$(".numText").live('keyup',function()
{
	$(this).val($(this).val().replace(/\D/g,""));  
}).bind("paste",function()
{  //CTR+V事件处理    
    $(this).val($(this).val().replace(/\D/g,""));     
}).css("ime-mode", "disabled"); //CSS设置输入法不可用
	
$(".float").live('keyup',function()
{
	$(this).val($(this).val().replace(/[^\d.]/g,""));  
}).bind("paste",function()
{  //CTR+V事件处理    
    $(this).val($(this).val().replace(/[^\d.]/g,""));     
}).css("ime-mode", "disabled"); //CSS设置输入法不可用
	
//控制姓名不能含有特殊字符
$(".nameText").live('keyup',function()
{
	//var pattern = new RegExp("[`~!@#$%^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]","g");
	$(this).val($(this).val().replace(/[^\u4E00-\u9FA5]/g,'')); 
}).bind("paste",function()
{  //CTR+V事件处理    
	//var pattern = new RegExp("[`~!@#$%^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]","g");
	$(this).val($(this).val().replace(/[^\u4E00-\u9FA5]/g,'')); 
}).css("ime-mode", "disabled"); //CSS设置输入法不可用
/**
 * 限制文本框只能输入数字、小数点，通过class属性绑定
 * 只能输入一个小数点
 */
$(".IntegerText").live('keyup',function()
{
	$(this).val(Number($(this).val().replace(/\D/g,"")));  
}).bind("paste",function()
{  //CTR+V事件处理    
    $(this).val(Number($(this).val().replace(/\D/g,"")));     
}).css("ime-mode", "disabled"); //CSS设置输入法不可用



function obj2string(o)
{ 
	 var r=[]; 
	 if(typeof o=="string")
	 { 
		 return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\""; 
	 }
	 
	 if(typeof o=="object")
	 { 
		 if(!o.sort)
		 { 
			 for(var i in o)
			 {
				 r.push(i+":"+obj2string(o[i])); 
		     }
			 
		     if(!!document.all&&!/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString))
		     { 
		    	 r.push("toString:"+o.toString.toString()); 
		     } 
		     
		     r="{"+r.join()+"}";
		     
		 }
		 else
		 { 
			 for(var i=0;i<o.length;i++)
			 { 
				 r.push(obj2string(o[i]));
		     }
			 
		     r="["+r.join()+"]"; 
		  } 
		  return r; 
	 }
	 
	 return o.toString(); 
}
var abh = 09;//上午上班时间,小时
var abm = 00;//上午上班时间,分钟
var aeh = 12;//上午下班时间，小时
var aem = 00;//上午下班时间，分钟
var pbh = 13;//下午上班时间，小时
var pbm = 30;//下午上班时间，分钟
var peh = 17;//下午下班时间，小时
var pem = 30;//下午下班时间，分钟 
var hoursPerDay = 7;//每天上班小时数
var minbegin = 45;//大于45分钟按一小时算
var minEnd = 15;//大于15分钟按0.5小时算
var holiday = [
  "2016-01-01",
  "2016-01-02",
  "2016-01-03",
  "2016-02-07",
  "2016-02-08",
  "2016-02-09",
  "2016-02-10",
  "2016-02-11",
  "2016-02-12",
  "2016-02-13",
  "2016-04-02",
  "2016-04-03",
  "2016-04-04",
  "2016-04-30",
  "2016-05-01",
  "2016-05-02",
  "2016-06-09",
  "2016-06-10",
  "2016-06-11",
  "2016-09-15",
  "2016-09-16",
  "2016-09-17",
  "2016-10-01",
  "2016-10-02",
  "2016-10-03",
  "2016-10-04",
  "2016-10-05",
  "2016-10-06",
  "2016-10-07"
];  // 休假

var workday = [
  "2016-02-06",
  "2016-02-14",
  "2016-06-12",
  "2016-09-18",
  "2016-10-08",
  "2016-10-09"
];  // 调休日
  
 function nearlyWeeks(weekcount, end) {
  /*
	     功能：计算当前时间（或指定时间），向前推算周数(weekcount)，得到结果周的第一天的时期值；
	     参数：
   weekcount -表示周数（0-表示本周， 1-前一周，2-前两周，以此推算）；
   end -指定时间的字符串（未指定则取当前时间）；
   */

  if (weekcount == undefined) weekcount = 0;
  if (end != undefined)
    end = new Date(new Date(end).toDateString());
  else
    end = new Date(new Date().toDateString());

  var days = end.getDay();

  return new Date(end.getTime() - (days + weekcount * 7) * 24 * 60 * 60 * 1000);
}


function getWorkDayCount(beginDay, endDay) {
  /*
	     功能：计算一段时间内工作的天数。不包括周末和法定节假日，法定调休日为工作日，周末为周六、周日两天；
	     参数：
   beginDay -时间段开始日期；
   endDay -时间段结束日期；
   */
  var begin = new Date(Date.parse(beginDay.replace(/-/g, "/")));
  var end = new Date(Date.parse(endDay.replace(/-/g, "/")));
  //每天的毫秒总数，用于以下换算
  var daytime = 24 * 60 * 60 * 1000;
  //两个时间段相隔的总天数
  var days = (end - begin) / daytime + 1;
  //时间段起始时间所在周的第一天
  var beginWeekFirstDay = nearlyWeeks(0, begin.getTime()).getTime();
  //时间段结束时间所在周的最后天
  var endWeekOverDay = nearlyWeeks(0, end.getTime()).getTime() + 6 * daytime;

  //由beginWeekFirstDay和endWeekOverDay换算出，周末的天数
  var weekEndCount = ((endWeekOverDay - beginWeekFirstDay) / daytime + 1) / 7 * 2;

  if (end.getDay() < 6) weekEndCount -= 1;

  if (begin.getDay() > 0) weekEndCount -= 1;

  //根据调休设置，调整周末天数（排除调休日）
  $.each(workday, function (i, offitem) {
    var itemDay = new Date(offitem.split('-')[0] + "/" + offitem.split('-')[1] + "/" + offitem.split('-')[2]);
    //如果调休日在时间段区间内，且为周末时间（周六或周日），周末天数值-1
    if (itemDay.getTime() >= begin.getTime() && itemDay.getTime() <= end.getTime() && (itemDay.getDay() == 0 || itemDay.getDay() == 6))
      weekEndCount -= 1;
  });

  //根据法定假日设置，计算时间段内周末的天数（包含法定假日）
  $.each(holiday, function (i, itemHoliday) {
    var itemDay = new Date(itemHoliday.split('-')[0] + "/" + itemHoliday.split('-')[1] + "/" + itemHoliday.split('-')[2]);
    //如果法定假日在时间段区间内，且为工作日时间（周一至周五），周末天数值+1
    if (itemDay.getTime() >= begin.getTime() && itemDay.getTime() <= end.getTime() && itemDay.getDay() > 0 && itemDay.getDay() < 6)
      weekEndCount += 1;
  });

  //工作日 = 总天数 - 周末天数（包含法定假日并排除调休日）
  return days - weekEndCount;
}

//获取地址参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}
/**
 * 将日期字符串转为日期
 * @param str yyyy-mm-dd HH:mm:ss,比如'2018-01-04 15:22:31'
 */
function strToDate(str){
	
	var date=new Date(str.replace(/-/g,"/"));
	return date;
}
/**
 * 比较两个日期对象的大小
 * @param d1  日期对象
 * @param d2  日期对象
 * true 或 false
 */
function compareDateTime(d1,d2){
	return d1>d2;
}
Date.prototype.Format = function (fmt) 
{ //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * 判断是否为合法ip  
 */
function isIp(str)
{
    if(str==null||str=="") return false;
    var result=str.match(/^((25[0-5])|(2[0-4]\d)|(1\d\d)|([1-9]\d)|\d)(\.((25[0-5])|(2[0-4]\d)|(1\d\d)|([1-9]\d)|\d)){3}$/);
    if(result==null)return false;
    return true;
} 
/**
 * 是否是归属地
 * @param str
 * @returns
 */
function isAttributePhone(str){
	var reg=/^\d{7}$/;
	return reg.test(str);
	
}

//by函数接受一个成员名字符串和一个可选的次要比较函数做为参数
//并返回一个可以用来包含该成员的对象数组进行排序的比较函数
//当o[age] 和 p[age] 相等时，次要比较函数被用来决出高下
var by = function(name,minor){
	return function(o,p){
		 var a,b;
		 if(o && p && typeof o === 'object' && typeof p ==='object'){
		   a = o[name];
		   b = p[name];
		   if(a === b){
		     return typeof minor === 'function' ? minor(o,p):0;
		   }
		   if(typeof a === typeof b){
		     return a < b ? -1:1;
		   }
		   return typeof a < typeof b ? -1 : 1;
		 }else{
		   thro("error");
		 }
	};
};
