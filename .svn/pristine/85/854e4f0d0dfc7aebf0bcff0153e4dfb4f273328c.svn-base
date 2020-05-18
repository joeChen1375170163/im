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
    return Math.floor(days - weekEndCount);
  }

  /**
   * 获取开始天的工作时间
   * @param beginDay
   */
  function getStartDayMinutes(beginDay) {
    var end = new Date(Date.parse(beginDay.replace(/-/g, "/")));
    end.setHours(peh);
    end.setMinutes(pem);
    end.setMilliseconds(0);
    return getOneDayMinutes(beginDay, end.toString());
  }

  /**
   * 获取结束天的工作时间
   * @param endDay
   */
  function getEndDayMinutes(endDay) {
    var start = new Date(Date.parse(endDay.replace(/-/g, "/")));
    start.setHours(abh);
    start.setMinutes(abm);
    start.setMilliseconds(0);
    return getOneDayMinutes(start.toString(), endDay);
  }

  /**
   * 开始和结束是一天的情况，获取当天的工作时间
   * @param beginDay
   * @param endDay
   * @returns {number}
   */
  function getOneDayMinutes(beginDay, endDay) {
	
    var begin = new Date(Date.parse(beginDay.replace(/-/g, "/")));
    var end = new Date(Date.parse(endDay.replace(/-/g, "/")));
	
	//根据法定假日设置，计算时间段内周末的天数（包含法定假日）
    $.each(holiday, function (i, itemHoliday) {
      var itemDay = new Date(itemHoliday.split('-')[0] + "/" + itemHoliday.split('-')[1] + "/" + itemHoliday.split('-')[2]);
      //如果法定假日在时间段区间内，且为工作日时间（周一至周五），周末天数值+1
      if (itemDay.getTime() == begin.getTime()){
		begin.setHours(0);
		begin.setMinutes(0);
		begin.setSeconds(0);
	  }
	 if( itemDay.getTime() == end.getTime()){
		end.setHours(0);
		end.setMinutes(0);
		end.setSeconds(0);
	 }
    });

    // 先格式化时间
    if (begin.getHours() < abh) {
      begin.setHours(abh);
      begin.setMinutes(abm);
      begin.setSeconds(0);
    }
    if (end.getHours() < abh) {
      // 结束时间小于最开始的时间
      return 0;
    }
    if (begin.getHours() > peh || (begin.getHours() == peh && begin.getMinutes() > pem )) {
      // 开始时间在最晚时间之后
      return 0;
    }
    if (end.getHours() > peh || (end.getHours() == peh && end.getMinutes() > pem )) {
      end.setHours(peh);
      end.setMinutes(pem);
      end.setSeconds(0);
    }

	var workMinutes = 0;
    var beginHour = begin.getHours();
    var beginMinute = begin.getMinutes();
    var beginSecond = begin.getSeconds();
    var endHour = end.getHours();
    var endMinute = end.getMinutes();
    var endSecond = end.getSeconds();

    if (beginHour < aeh && endHour < aeh) {
      // 开始和结束都在上午
      workMinutes = (endHour - beginHour) * 60 + (endMinute - beginMinute);
    } else if ((beginHour > pbh || (beginHour == pbh && beginMinute > pbm) )
        && (endHour > pbh || (endHour == pbh && endMinute > pbm) )) {
      // 开始和结束都在下午
      workMinutes = (endHour - beginHour) * 60 + (endMinute - beginMinute);
    } else {
      var amMinutes = 0;
      var pmMinutes = 0;
      if (beginHour >= aeh && (beginHour < pbh || (beginHour == pbh && beginMinute < pbm) )) {
        // 开始时间在中午
        amMinutes = 0;
      } else {
        // 开始时间在上午
        amMinutes = (aeh - beginHour) * 60 + (aem - beginMinute);  // 上午时间
      }

      if (endHour >= aeh && (endHour < pbh || (endHour == pbh && endHour < pbm) )) {
        pmMinutes = 0;
      } else {
        pmMinutes = (endHour - pbh) * 60 + (endMinute - pbm);   // 下午时间
      }
      workMinutes = amMinutes + pmMinutes;
    }

    return workMinutes;
  }

  
  function minCalculate(start, end) {
    var days = getWorkDayCount(start, end);
	
	if(days == 0){
		return 0;
	}

	//只有一天，只需要算开始天的时间
    if (days == 1) {
      var startMinutes = getOneDayMinutes(start, end);
	  var oneMinutes = 0;
	  if(startMinutes%60 < 15){
		oneMinutes = startMinutes-startMinutes%60;
	  }else if(startMinutes%60 >= 15 && startMinutes%60 <= 45){
		oneMinutes = startMinutes-startMinutes%60 + 30;
	  }else if(startMinutes%60 > 45){
		oneMinutes = startMinutes-startMinutes%60+60;
	  }
      return oneMinutes/60;
    }

	//只有2天，只算开始和结束
    if (days == 2) {
      var startMinutes = getStartDayMinutes(start);
      var endMinutes = getEndDayMinutes(end);
	  var allTwo = startMinutes+endMinutes;
	  var twoMinutes = 0;
	  if(allTwo%60 < 15){
		twoMinutes = allTwo-allTwo%60;
	  }else if(allTwo%60 >= 15 && allTwo%60 <= 45){
		twoMinutes = allTwo-allTwo%60 + 30;
	  }else if(allTwo%60 > 45){
		twoMinutes = allTwo-allTwo%60+60;
	  }
      return twoMinutes/60;
    }

	//3天以上，首尾单独，中间也算
    if (days > 2) {
      var startMinutes = getStartDayMinutes(start);
      var endMinutes = getEndDayMinutes(end);
      var middleMinutes = (days - 2) * hoursPerDay * 60;
	  var allThree = startMinutes + middleMinutes + endMinutes;
	  var threeMinutes = 0;
	  console.log(allThree);
	  console.log(allThree%60);
	  console.log(allThree/60);
	  if(allThree%60 < 15){
		threeMinutes = allThree-allThree%60;
	  }else if(allThree%60 >= 15 && allThree%60 <= 45){
		threeMinutes = allThree-allThree%60 + 30;
	  }else if(allThree%60 > 45){
		threeMinutes = allThree-allThree%60+60;
	  }
      return threeMinutes/60;
    }

  }