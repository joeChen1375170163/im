/**
 * https://github.com/navyxie/idcard
 * notejs版 --> js版
 * service
 */
var area_service = {
	undefinedText : "未知",
	_checkCard: function (idcard) {
	  if (!util.isString(idcard) || idcard.length !== 18) {
	    return false;
	  }
	  return true;
	},
	
	_getSexual: function (idcard) {
	  if (!area_service._checkCard(idcard)) {
	    return undefinedText;
	  }
	  var sexualChar = idcard.slice(idcard.length - 2, idcard.length - 1);
	  if (isNaN(Number(sexualChar))) {
	    return undefinedText;
	  }
	  if (Number(sexualChar) % 2 === 0) {
	    return "F";
	  }
	  return "M";
	},
	
	_getBirthday: function (idcard) {
	  if (!area_service._checkCard(idcard)) {
	    return undefinedText;
	  }
	  var birthday = idcard.slice(6, 14);
	  if (isNaN(Number(birthday))) {
	    return undefinedText;
	  }
	  if (!util.verifyBirthday(birthday)) {
	    return undefinedText;
	  }
	  return Number(birthday);
	},
	//type,0:province,1:city,2:area
	_getData: function (idcard, type) {
	  if (!area_service._checkCard(idcard)) {
	    return "";
	  }
	  var len = 2,
	    obj = provinces;
	  switch (type) {
	    case 0:
	      len = 2;
	      obj = provinces;
	      break;
	    case 1:
	      len = 4;
	      obj = citys;
	      break;
	    case 2:
	      len = 6;
	      obj = areas;
	      break;
	  }
	  var code = idcard.slice(0, len);
	  if (isNaN(Number(code))) {
	    return "";
	  }
	  return obj[code] ? obj[code] : "";
	},
	
	_getArea: function (idcard) {
	  return area_service._getData(idcard, 2);
	},
	
	_getCity: function (idcard) {
	  return area_service._getData(idcard, 1);
	},
	
	_getProvince: function (idcard) {
	  return area_service._getData(idcard, 0);
	},
	
	_hitArea: function (idcard) {
	  if (!area_service._checkCard(idcard)) {
	    return false;
	  }
	  var areaCode = idcard.slice(0, 6);
	  if (isNaN(Number(areaCode))) {
	    return false;
	  }
	  if (areas[areaCode]) {
	    return areas[areaCode];
	  }
	  areaCode = areaCode.slice(0, 4);
	  if (citys[areaCode]) {
	    return citys[areaCode];
	  }
	  areaCode = areaCode.slice(0, 2);
	  if (provinces[areaCode]) {
	    return provinces[areaCode]
	  }
	  return false;
	},
	
	hit: function (idcard) {
	  return area_service._hitArea(idcard) ? true : false;
	},
	
	idcardinfo: function (idcard) {
	  if (!area_service.hit(idcard)) {
	    return util.getDefaultIdcardInfoReturn();
	  }
	  var info = {
	    valid: true,
	    gender: area_service._getSexual(idcard),
	    birthday: area_service._getBirthday(idcard),
	    province: area_service._getProvince(idcard),
	    city: area_service._getCity(idcard),
	    area: area_service._getArea(idcard),
	    cardType: 1,
	    cardText: '大陆'
	  }
	  info.age = area_service.getAge(info.birthday);
	  switch (idcard.slice(0, 6)) {
	    case '710000':
	      info.cardType = 2;
	      info.cardText = '中国台湾';
	      break;
	    case '810000':
	      info.cardType = 2;
	      info.cardText = '中国香港';
	      break;
	    case '820000':
	      info.cardType = 2;
	      info.cardText = '中国澳门';
	      break;
	  }
	  var address = "";
	  if (util.isObject(info.province)) {
	    address += info.province.text;
	  }
	  if (util.isObject(info.city)) {
	    address += info.city.text;
	  }
	  if (util.isObject(info.area)) {
	    address += info.area.text;
	  }
	  info.address = address;
	  return info;
	},
	
	getAge: function (birthday) {
	  var age;
	  if (!(util.isString(birthday) || util.isNumber(birthday))) {
	    return age;
	  }
	  birthday = birthday.toString();
	  var year = birthday.slice(0, 4);
	  var month = birthday.slice(4, 6);
	  var date = birthday.slice(6, 8);
	  var curDate = new Date();
	  try {
	    if ((1 + curDate.getMonth() < parseInt(month)) || (1 + curDate.getMonth() === parseInt(month) && curDate.getDate() < parseInt(date))) {
	      age = curDate.getFullYear() - parseInt(year) - 1;
	    } else {
	      age = curDate.getFullYear() - parseInt(year);
	    }
	  } catch (e) {
	    age = 0;
	  }
	  return isNaN(age) ? 0 : age;
	}
}