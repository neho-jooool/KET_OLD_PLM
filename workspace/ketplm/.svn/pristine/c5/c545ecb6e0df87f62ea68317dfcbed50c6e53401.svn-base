/*********************************
 * Utility Javascript
 *
 * last modified : 2004-09-21
 * author        : 김선일, 주영덕
 *********************************/

/*
 * 왼쪽에 위치한 whitespace 문자를 제거
 */
function ltrim(str)
{
    return str.replace(/^\s+/, "");
}

/* 
 *  오른쪽에 위치한 whitespace 문자를 제거
 */
function rtrim(str)
{
    return str.replace(/\s+$/, "");
}

/* 
 *  양쪽의 whitespace 문자를 제거
 */
function trim(str)
{
    return rtrim(ltrim(str));
}

/* 
 * 주어진 문자열의 왼쪽을 padding 문자로 채운다
 */
function lpad(str, n, padding)
{
    if (str.length >= n)
        return str;
    else
    {
        var len = n - str.length;
        var pad_str = str;
        for (var i=0; i<len; i++)
            pad_str = padding + pad_str;

        return pad_str;
    }
}

/* 
 * 주어진 문자열의 오른쪽을 padding 문자로 채운다
 */
function rpad(str, n, padding)
{
    if (str.length >= n)
        return str;
    else
    {
        var len = n - str.length;
        var pad_str = str;
        for (var i=0; i<len; i++)
            pad_str = pad_str + padding;

        return pad_str;
    }
}

/* 
 * 숫자로만 이루어진 문자열인지 검사
 * cf) 음수부호는 포함하지 않음
 */
function is_number(x)
{
    var reg = /^\d+$/;
    return reg.test(x);
}

/*
 * 부동소수점 숫자인지 음수부호도 포함하여 검사
 */
function is_double(x)
{
    var reg = /^[-|+]?\d+\.?\d*$/;
    return reg.test(x);
}

/*
 * 정수인지 음수부호도 포함하여 검사
 */
function is_integer(x)
{
    var reg = /^[-|+]?\d+$/;
    return reg.test(x);
}

/*
 * 숫자(0~9)와 문자(A(a)~Z(z))로만 이루어진 문자열인지 검사
 * cf) space가 true면 공백문자를 포함한다.
 */
function is_alpha_numeric(x, space)
{
    if (space)
    {
        var reg = /^[a-z A-Z0-9]+$/;
        return reg.test(x);
    }
    else
    {
        var reg = /^[a-zA-Z0-9]+$/;
        return reg.test(x);
    }
}

/*
 * 문자(A(a)~Z(z))로만 이루어진 문자열인지 검사
 * cf) space가 true면 공백문자를 포함한다.
 */
function is_alphabetic(x, space)
{
    if (space)
    {
        var reg = /^[a-z A-Z]+$/;
        return reg.test(x);
    }
    else
    {
        var reg = /^[a-zA-Z]+$/;
        return reg.test(x);
    }
}

/*
 * 문자열의 Byte 수를 반환한다.
 */
function get_byte(str) 
{ 
    var len = 0;
    for (var idx=0; idx<str.length; idx++) 
    { 
        if ( (str.charCodeAt(idx)<0) || (str.charCodeAt(idx)>127) ) 
            len += 2;
        else
            len ++;
    }

    return len;
}

/* 
 *  email 형식의 문자열인지 검사
 */
function email_str(str)
{
    var regEmil = /[a-z0-9]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/i
    return regEmil.test(str);
}

/* 
 * 문자열을 find문자열을 찾아서 rps로 대체
 */
function replace_str(str, find, rps)
{
    var reg = new RegExp(find, "ig");
    return str.replace(reg, rps);
}

/* 
 * 숫자를 1000단위 콤마로 구분된 문자열로 반환
 */
function money_format(num)
{
    var comma = new RegExp("([0-9])([0-9][0-9][0-9][,.])");
    var data = String(num).split(".");

    data[0] += ".";
    do 
    {
        data[0] = data[0].replace(comma, "$1,$2");
    } 
    while (comma.test(data[0]));

    if (data.length > 1)
        return data.join("");
    else
        return data[0].split(".")[0];
}

/* 
 * 1000단위 콤마로 구분된 문자열를 숫자로 반환
 */
function money_to_num(str)
{
    var num = replace_str(String(str), ",", "");
    return Number(num);
}

/*
 * 숫자 num을 n자릿수까지 반올림
 */
function round_x(num, n)
{
    // 1234.675, 2 -> 1234.68
    var i = Math.pow(10, n);
    return Math.round(num * i) / i;
}

/*
 * 숫자 num을 n자릿수까지 올림
 */
function ceil_x(num, n)
{
    // 1234.674, 2 -> 1234.68
    var i = Math.pow(10, n);
    return Math.ceil(num * i) / i;
}

/*
 * 숫자 num을 n자릿수까지 내림
 */
function floor_x(num, n)
{
    // 1234.675, 2 -> 1234.67
    var i = Math.pow(10, n);
    return Math.floor(num * i) / i;
}

/*
 * 주어진 이름으로 된 쿠키 값을 반환
 */
function get_cookie(name)
{
    var cookies = document.cookie.split("; ");
    for (var i=0; i<cookies.length; i++)
    {
        cookie_idx = cookies[i].indexOf(name + "=");
        if (cookie_idx != -1)
        {
            var tmp = cookies[i].split("=");
            return unescape(tmp[1]);
        }
    }
        
    return null;
}

/*
 * 주어진 이름과 값으로 expires(일단위) 기간동안 유효한 쿠키를 저장
 * cf) expires를 양수가 아닌값으로 셋팅할 경우 쿠키값은 삭제됨
 */
function set_cookie(name, value, expires) 
{
    var per_day = 1000 * 60 * 60 * 24;
    var now = new Date();
    var then = new Date(now.getTime() + expires * per_day);
    document.cookie = name + "=" + escape(value) + "; expires=" + then.toGMTString() + "; path=/";
}

/*
 * 입력한 날짜(yyyyMMdd)가 유효한 날짜인지 검사
 */
function is_valid_date(date_str)
{
    var yyyyMMdd = String(date_str);
    var year = yyyyMMdd.substring(0,4);
    var month = yyyyMMdd.substring(4,6);
    var day = yyyyMMdd.substring(6,8);

    if (!is_number(date_str) || date_str.length!=8)
        return false;

    if (Number(month)>12 || Number(month)<1)
        return false;

    if (Number(last_day(date_str))<day)
        return false;

    return true;
}

/*
 * yyyy-MM-dd 날짜문자열을 Date형으로 반환
 */
function to_date2(date_str)
{
    var yyyyMMdd = String(date_str);
    var sYear = yyyyMMdd.substring(0,4);
    var sMonth = yyyyMMdd.substring(5,7);
    var sDate = yyyyMMdd.substring(8,10);

    //alert("sYear :"+sYear +"   sMonth :"+sMonth + "   sDate :"+sDate);
    return new Date(Number(sYear), Number(sMonth)-1, Number(sDate));
} 

/*
 * 일차이 : date2(일) - date1(일)
 */
function between_date(date1, date2)
{    
    var y1970 = new Date(1970, 0, 1).getTime();
    var time1 = null;
    var time2 = null;

    if(date1.length > 8)
        time1 = to_date2(date1).getTime() - y1970;
    else 
        time1 = to_date(date1).getTime() - y1970;
    
    if(date2.length > 8) 
        time2 = to_date2(date2).getTime() - y1970;
    else 
        time2 = to_date(date2).getTime() - y1970;

    var per_day = 1000 * 60 * 60 * 24;              // 1일 밀리초

    return Math.floor(time1/per_day) - Math.floor(time2/per_day);
}

/*
 * yyyyMMdd 날짜문자열을 Date형으로 반환
 */
function to_date(date_str)
{
    var yyyyMMdd = String(date_str);
    var sYear = yyyyMMdd.substring(0,4);
    var sMonth = yyyyMMdd.substring(4,6);
    var sDate = yyyyMMdd.substring(6,8);

    return new Date(Number(sYear), Number(sMonth)-1, Number(sDate));
}

/*
 * yyyyMMdd 날짜문자열을 gubun으로 포맷을 변경
 */
function to_date_format(date_str, gubun)
{
    var yyyyMMdd = String(date_str);
    var sYear = yyyyMMdd.substring(0,4);
    var sMonth = yyyyMMdd.substring(4,6);
    var sDate = yyyyMMdd.substring(6,8);

    return sYear + gubun + sMonth + gubun + sDate;
}

/*
 * Date형을 yyyyMMdd형의 문자열로 변환
 */
function get_date_str(date)
{
    var sYear = date.getFullYear();
    var sMonth = date.getMonth() + 1;
    var sDate = date.getDate();

    sMonth = sMonth > 9 ? sMonth : "0" + sMonth;
    sDate  = sDate > 9 ? sDate : "0" + sDate;
    return sYear + sMonth + sDate;
}

/*
 * Date형을 구분자로 구분된 형식의 날짜 문자열 변환
 */
function get_date_str_gubun(date, gubun)
{
    var sYear = date.getFullYear();
    var sMonth = date.getMonth() + 1;
    var sDate = date.getDate();

    sMonth = sMonth > 9 ? sMonth : "0" + sMonth;
    sDate  = sDate > 9 ? sDate : "0" + sDate;
    return sYear + gubun + sMonth + gubun + sDate;
}

/*
 * 오늘날짜를 yyyyMMdd형의 문자열로 변환
 */
function get_today()
{
    return get_date_str(new Date());
}

/*
 * 주어진 날짜가 윤년인지를 검사
 */
function is_leap_year(date_str)
{
    var year = date_str.substring(0,4);
    if (year%4 == 0)
    {
        if (year%100 == 0)
            return (year%400 == 0);
        else
            return true;
    }
    else
        return false;
}

/*
 * 주어진 날짜(yyyyMMdd, yyyyMM) 그 달의 마지막 날짜를 반환
 */
function last_day(date_str)
{
    var yyyyMMdd = String(date_str);
    var days = "31";
    var year = yyyyMMdd.substring(0,4);
    var month = yyyyMMdd.substring(4,6);

    if (Number(month) == 2)
    {
        if (is_leap_year(year+month+"01"))
            days = "29";
        else
            days = "28";
    }
    else if (Number(month) == 4 || Number(month) == 6 || Number(month) == 9 || Number(month) == 11)
        days = "30";

    return days;
}

/*
 * 오늘날짜 중 Year반환
 */
function get_today_year()
{
	var today = new Date();
    return today.getYear();
}

/*
 * 오늘날짜 중 Month반환. format: MM
 */
function get_today_month()
{
	var today = new Date();
    return (today.getMonth()+1) > 9 ?  (today.getMonth()+1) : "0" + (today.getMonth()+1)
}

/*
 * 메세지를 출력후에 이전 페이지로 되돌린다.
 */
function alert_back(msg)
{
    alert(msg);
    history.back();
}

/*
 * 메세지를 출력후에 특정 페이지로 이동한다.
 */
function alert_location(msg, url)
{
    alert(msg);
    location.href = url;
}

/*
 * 메세지를 출력후에 특정 객체를 Focus 한다.
 */
function alert_focus(msg, obj)
{
    alert(msg);
    if(obj)
        obj.focus();
}

/*
 * checkbox object checked 속성 체크
 */
function all_check(obj, true_or_false)
{
    if (obj)
    {
        if (obj.length)
        {
            for (var i=0; i<obj.length; i++)
                obj[i].checked = true_or_false;
        }
        else
            obj.checked = true_or_false;
    }
}

/*
 * radio, checkbox object checked=true count
 */
function checked_count(obj)
{
    var count = 0;

    if (!obj)
        return false;

    if (obj.length)
    {
        for (var i=0; i<obj.length; i++)
        {
            if (obj[i].checked)
                count++;
        }
    }
    else
    {
        if (obj.checked)
            count++;
    }

    return count;
}

/*
 * radio, checkbox object 체크 여부
 */
function is_checked(obj)
{
    return checked_count(obj)>0;
}

/*
 * radio object checked=true value
 */
function get_radio_value(obj)
{
    if (!obj)
        return null;

    if (obj.length)
    {
        for (var i=0; i<obj.length; i++)
        {
            if (obj[i].checked)
                return obj[i].value;
        }
    }
    else
    {
        if (obj.checked)
            return obj.value;
    }

    return null;
}

/*
 * select box value
 */
function get_select_value(obj)
{
    if (!obj)
        return null;

    return obj.options[obj.selectedIndex].value;
}

/*
 * select box all option selected 속성 체크
 */
function all_selected(obj, true_or_false)
{
    for (var i=0; i<obj.length; i++)
        obj.options[i].selected = true_or_false;
}

/*
 * select box object selected=true count
 */
function selected_count(obj)
{
    var count = 0;

    for (var i=0; i<obj.options.length; i++)
    {
        if (obj.options[i].selected)
            count++;
    }

    return count;
}

/*
 * add select box option object 
 */
function add_select_box(obj, txt, value , select_value)
{
    var optItem = new Option();

    optItem.text = txt;
    optItem.value = value;

    if(select_value == value)
        optItem.selected = true;

    obj.options[obj.options.length] = optItem;
}  

/*
 * remove select box list
 */
function remove_select_box(obj)
{
    for(var i=0; i<obj.options.length; i++)
    {
        obj.options[i].value = null;
        obj.options[i].text = null;
    }

    obj.options.length = 0;
}

/*
 * html tag object의 수를 반환
 */
function obj_length(obj)
{
    if (!obj) 
        return 0;

    if (obj.length)
        return obj.length;
    else
        return 1;
}


/*
 * yyyy-MM-dd 날짜문자열을 YYYYMMDD형으로 반환
 */
function to_date3(date_str)
{
    var yyyyMMdd = String(date_str);
    var sYear = yyyyMMdd.substring(0,4);
    var sMonth = yyyyMMdd.substring(5,7);
    var sDate = yyyyMMdd.substring(8,10);

    //alert("sYear :"+sYear +"   sMonth :"+sMonth + "   sDate :"+sDate);
    return sYear+sMonth+sDate;
} 


/*
 * URL Encoding
 */
function urlencode(url)
{ 
	return escape(url).replace(/\+/g, "%2B"); 
}


/*
 * JavaScript에서 사용하기 위해 특수문자 치환
 */
function toScriptValue(s)
{
	s = s.replace(/\'/g, "\'");
	s = s.replace(/\"/g, "&#34;"); // &quot;
	
	return s;
}

/*
 * 특수문자 제거
 */
function removeSpecialChars(s)
{
	s = s.replace(/\\/, "");
	s = s.replace(/\'/, "");
	s = s.replace(/\"/, "");
	s = s.replace(/\&/, "");
	
	return s;
}


function enableButton(buttonName)
{
	var buttons = document.getElementsByName(buttonName);

	for(var i=0; i<buttons.length; i++)
		buttons[i].disabled = false;
}


function disableButton(buttonName)
{
	var buttons = document.getElementsByName(buttonName);

	for(var i=0; i<buttons.length; i++)
		buttons[i].disabled = true;
}
