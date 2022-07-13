var intYear;
var intMonth;
var intDate;

today = new Date();

intYear = today.getYear();
intMonth = today.getMonth() + 1;
intDate = today.getDate();

var wCal;

var objTarget;
var intTargetType;
var strReturnFunction;

var strCalHead = '<html><head><meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>Calendar</title><style>body, td, th { font-family: "돋움"; font-size: 12px; color: #333333; line-height: 12pt; text-align: center }a { text-decoration: none; color: black; }a:hover { text-decoration: none; color: red; }</style></head>';
var strCalBodyTop1 = '<body bgcolor="#ffffff"><table width="215" border="0" cellspacing="0" cellpadding="0"><tr><td height="20"><img src="/plm/portal/images/ca_title.gif" width="215" height="19"></td></tr><tr><td background="/plm/portal/images/ca_bg.gif" align="center" height="21">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.opener.change(\'Y\', -1);"><img src="/plm/portal/images/ic_d_back.gif" width="11" height="10" border=0></a>&nbsp;<a href="javascript:window.opener.change(\'M\', -1);"><img src="/plm/portal/images/ic_back.gif" width="5" height="10" border=0></a>&nbsp;&nbsp;&nbsp;';
var strCalBodyTop2 ='<a href="javascript:window.opener.change(\'M\', 1);"><img src="/plm/portal/images/ic_next.gif" width="5" height="10" border=0></a>&nbsp;<a href="javascript:window.opener.change(\'Y\', 1);"><img src="/plm/portal/images/ic_d_next.gif" width="11" height="10" border=0></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.opener.deleteDate();"><img src="/plm/portal/images/ca_clear.gif" width="16" height="16" border=0></a></td></tr></table><tr><td><table width="215" border="0" cellspacing="1" cellpadding="0" bgcolor="89A2CB"><tr><td align="center"><table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="D9D9FF">';
var strCalBodyCenter = '<tr align="center" valign="bottom"><td height="18" bgcolor="89A2CB"><b><font color="#FFFFFF">S </font></b></td><td bgcolor="89A2CB"><b><font color="#FFFFFF">M </font></b></td><td bgcolor="89A2CB"><b><font color="#FFFFFF">T </font></b></td><td bgcolor="89A2CB"><b><font color="#FFFFFF">W</font></b></td>	<td bgcolor="89A2CB"><b><font color="#FFFFFF">T </font></b></td><td bgcolor="89A2CB"><b><font color="#FFFFFF">F</font></b></td><td bgcolor="89A2CB"><b><font color="#FFFFFF">S</font></b></td></tr>';
var strCalBodyBottom = '</table>	</td></tr></table></td></tr></table></body></html>';

function addDate(interval, number)
{
  switch (interval)
  {
    case "M":
      intMonth += (number % 12);
      if(intMonth < 1) {
        intMonth += 12;
        intYear--;
      } else if(intMonth > 12) {
        intMonth -= 12;
        intYear++;
      }
      if((number / 12) < 1 && (number / 12) > -1) break;
      else number /= 12;
    case "Y":
      intYear += number; break;
    default:
  }

}

function getDay()
{
  var tmpDay = 0;
  for(id = 1; id < intYear; id++) tmpDay += (leapYear(id) + 1);
  for(id = 1; id < intMonth; id++) tmpDay += MonthLength(intYear, id);
  tmpDay += intDate;
  return (tmpDay % 7)
}

function toDataString()
{
  strDate = "" + intYear;
  if(intMonth < 10) strDate += "-" + "0" + intMonth;
  else strDate += "-" + intMonth;
  if(intDate < 10) strDate += "-" + "0" + intDate;
  else strDate += "-" + intDate;

  return strDate;
}

function MonthLength(yyyy, mm)
{
  switch(mm)
  {
    case 4:
    case 6:
    case 9:
    case 11:
      return 30;
    case 2:
      return 28 + leapYear(yyyy);
    default:
      return 31;
  }
}

function leapYear(yyyy)
{
  if(((yyyy % 4) == 0 && (yyyy % 100) != 0) || (yyyy % 400) == 0) return 1;
  else return 0;
}

function show_calendar_inpage(targetStr,targetType)
{
  target1 = eval(targetStr)
  if(target1.length)
  {
    show_calendar(eval(targetStr+"[0]"),targetType);
  }
  else
  {
    show_calendar(target1,targetType);
  }
}


//달력을 화면에 표시하는 함수들
function show_calendar(target, targetType)
{
  //20030522 나종원 수정
  if(typeof(target)=="object"){
      objTarget = eval(target);
  }else{
      alert("error "+target+" type is not object                             \n"
           +"Usage : show_calendar(target,targetType)                        \n"
           +"target : form.textElement,hiddenElement, form.selectElement etc \n"
           +"targetType :0 textElement,hiddenElement                         \n"
           +"            1 selectElement                                     \n"
           +"            2 ??                                                ");
        return false;
  }
  //여기까지

  intTargetType = targetType;
  noDashDate = objTarget.value.replace(/-/g, "");

  if ( noDashDate == "" )
  {
    intYear = today.getYear();
    intMonth = today.getMonth() + 1;
    intDate = today.getDate();
  }
  switch(intTargetType) {
  case 0: strReturnFunction = "setValue";
    if(isYYYYMMDD(noDashDate)) {
      intYear = toInteger(noDashDate.substring(0, 4));
      intMonth = toInteger(noDashDate.substring(4, 6));
      intDate = toInteger(noDashDate.substring(6));
    }
    break;
  case 1: strReturnFunction = "setOption"; break;
  case 2: strReturnFunction = "runFunction"; break;
  }
  //alert("intYear="+intYear+"intMonth="+intMonth+"intDate="+intDate);
    y = event.screenY+13;
    x = event.screenX-130;
  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"");
  wCal = window.open("", "wCal", "width=230,height=210,top="+y+",left="+x);

  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"; center:yes; help:no; scroll:no; resizable:no; status:no");

  wCal.focus();

  cals();
}

//달력을 화면에 표시하는 함수들
//add by 오승연
function showCal(target)
{
    var targetType = 0;
  //20030522 나종원 수정
  var paramObj;
  if(typeof(target)!="object"){
    paramObj = document.all.item(target);
    if(paramObj == null) {
      paramObj = eval("document.forms[0]." + target);
    }
  }else{
    paramObj = target;
  }
  if(typeof(paramObj)=="object"){
      objTarget = eval(paramObj);
  }else{
	  
	  
	  paramObj = eval("document.getElementById('" + target +"'");
	  
	  
	  if(typeof(paramObj)=="object"){
	      objTarget = eval(paramObj);
	  }else{
	      alert("error "+target+" type is not object                             \n"
	           +"Usage : show_calendar(target,targetType)                        \n"
	           +"target : form.textElement,hiddenElement, form.selectElement etc \n"
	           +"targetType :0 textElement,hiddenElement                         \n"
	           +"            1 selectElement                                     \n"
	           +"            2 ??                                                ");
	        return false;
	        
	  }
	  
	  
  }
  //여기까지

  intTargetType = targetType;
  noDashDate = objTarget.value.replace(/-/g, "");

  if ( noDashDate == "" )
  {
    intYear = today.getYear();
    intMonth = today.getMonth() + 1;
    intDate = today.getDate();
  }
  switch(intTargetType) {
  case 0: strReturnFunction = "setValue";
    if(isYYYYMMDD(noDashDate)) {

      intYear = toInteger(noDashDate.substring(0, 4));
      intMonth = toInteger(noDashDate.substring(4, 6));
      intDate = toInteger(noDashDate.substring(6));
    }
    break;
  case 1: strReturnFunction = "setOption"; break;
  case 2: strReturnFunction = "runFunction"; break;
  }
//	alert("intYear="+intYear+"intMonth="+intMonth+"intDate="+intDate);
  var x, y;
  try {
    y = event.screenY+13;
    x = event.screenX-130;
  } catch(e) {
    x = 0;
    y = 0;
  }
  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"");
  wCal = window.open("", "wCal", "width=230,height=210,top="+y+",left="+x);

  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"; center:yes; help:no; scroll:no; resizable:no; status:no");

  wCal.focus();

  cals();
}

function showCal2(target, func )
{
    var targetType = 0;
  //20030522 나종원 수정
  var paramObj;
  if(typeof(target)!="object"){
    paramObj = document.all.item(target);
    if(paramObj == null) {
      paramObj = eval("document.forms[0]." + target);
    }
  }else{
    paramObj = target;
  }
  if(typeof(paramObj)=="object"){
      objTarget = eval(paramObj);
  }else{
	  
	  
	  paramObj = eval("document.getElementById('" + target +"'");
	  
	  
	  if(typeof(paramObj)=="object"){
	      objTarget = eval(paramObj);
	  }else{
	      alert("error "+target+" type is not object                             \n"
	           +"Usage : show_calendar(target,targetType)                        \n"
	           +"target : form.textElement,hiddenElement, form.selectElement etc \n"
	           +"targetType :0 textElement,hiddenElement                         \n"
	           +"            1 selectElement                                     \n"
	           +"            2 ??                                                ");
	        return false;
	        
	  }
	  
	  
  }
  //여기까지

  intTargetType = targetType;
  noDashDate = objTarget.value.replace(/-/g, "");

  if ( noDashDate == "" )
  {
    intYear = today.getYear();
    intMonth = today.getMonth() + 1;
    intDate = today.getDate();
  }
  switch(intTargetType) {
  case 0: strReturnFunction = "setValue2";
    if(isYYYYMMDD(noDashDate)) {

      intYear = toInteger(noDashDate.substring(0, 4));
      intMonth = toInteger(noDashDate.substring(4, 6));
      intDate = toInteger(noDashDate.substring(6));
    }
    break;
  case 1: strReturnFunction = "setOption"; break;
  case 2: strReturnFunction = "runFunction"; break;
  }
  //alert("intYear="+intYear+"intMonth="+intMonth+"intDate="+intDate);
    y = event.screenY+13;
    x = event.screenX-130;
  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"");
  wCal = window.open("", "wCal", "width=230,height=210,top="+y+",left="+x);

  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"; center:yes; help:no; scroll:no; resizable:no; status:no");

  wCal.focus();

  show2( func );
}

function showCal3(target, func )
{
    var targetType = 0;

  var paramObj;
  if(typeof(target)!="object"){
    paramObj = document.all.item(target);
    if(paramObj == null) {
      paramObj = eval("document.forms[0]." + target);
    }
  }else{
    paramObj = target;
  }
  if(typeof(paramObj)=="object"){
      objTarget = eval(paramObj);
  }else{
	  
	  
	  
	  paramObj = eval("document.getElementById('" + target +"'");
	  
	  
	  if(typeof(paramObj)=="object"){
	      objTarget = eval(paramObj);
	  }else{	  
	      alert("error "+target+" type is not object                             \n"
	           +"Usage : show_calendar(target,targetType)                        \n"
	           +"target : form.textElement,hiddenElement, form.selectElement etc \n"
	           +"targetType :0 textElement,hiddenElement                         \n"
	           +"            1 selectElement                                     \n"
	           +"            2 ??                                                ");
	        return false;
	        
	  }
	  
	  
  }


  intTargetType = targetType;
  noDashDate = objTarget.value.replace(/-/g, "");

  if ( noDashDate == "" )
  {
    intYear = today.getYear();
    intMonth = today.getMonth() + 1;
    intDate = today.getDate();
  }
  switch(intTargetType) {
  case 0: strReturnFunction = "setValue3";
    if(isYYYYMMDD(noDashDate)) {

      intYear = toInteger(noDashDate.substring(0, 4));
      intMonth = toInteger(noDashDate.substring(4, 6));
      intDate = toInteger(noDashDate.substring(6));
    }
    break;
  case 1: strReturnFunction = "setOption"; break;
  case 2: strReturnFunction = "runFunction"; break;
  }
  //alert("intYear="+intYear+"intMonth="+intMonth+"intDate="+intDate);
    y = event.screenY+13;
    x = event.screenX-130;
  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"");
  wCal = window.open("", "wCal", "width=230,height=210,top="+y+",left="+x);

  //wCal = window.showModalDialog("", "wCal", "dialogHeight:315px; dialogWidth:238px; dialogTop:"+y+"; dialogLeft:"+x+"; center:yes; help:no; scroll:no; resizable:no; status:no");

  wCal.focus();

  show2( func );
}

function change(interval, number)
{
  addDate(interval, number);
  cals();
}

function change2(interval, number, func )
{
  addDate(interval, number);
  show2( func );
}

function deleteDate()
{
  objTarget.value = "";
  intYear = today.getYear();
  intMonth = today.getMonth() + 1;
  intDate = today.getDate();
  //alert("intYear="+intYear+"intMonth="+intMonth+"intDate="+intDate);
  wCal.close();
}

function cals()
{
  var i;
  wCal.document.open();
  wCal.document.writeln(strCalHead);
  wCal.document.writeln(strCalBodyTop1);
  wCal.document.writeln('<b><font color="#000066">' + intYear + '-' + intMonth + '</font></b>&nbsp;&nbsp;&nbsp;');
  wCal.document.writeln(strCalBodyTop2);
  wCal.document.writeln(strCalBodyCenter);
//alert("intDate="+intDate);
  for(dateCnt = intDate - getDay(); dateCnt > 1; dateCnt -= 7);
  lastDate = MonthLength(intYear, intMonth);

  while (dateCnt <= lastDate)
  {
    wCal.document.writeln('<tr>');

    for(i = 0; i < 7; i++, dateCnt++)
    {
      if(i == 0) wCal.document.writeln('<td bgcolor="DDE4EC" height="20">');
      else wCal.document.writeln('<td bgcolor="#FFFFFF" height="20">');

      if(dateCnt > 0 && dateCnt <= lastDate) {
        if(dateCnt == intDate) wCal.document.writeln('<a href="javascript:window.opener.' + strReturnFunction + '(' + dateCnt + ');"><font color="#ff0000"><b>' + dateCnt + '</b></font></a>');
        else wCal.document.writeln('<a href="javascript:window.opener.' + strReturnFunction + '(' + dateCnt + ');">' + dateCnt + '</a>');
      } else wCal.document.writeln('&nbsp;');

      wCal.document.writeln('</td>');
    }
    wCal.document.writeln('</tr>');
  }

  wCal.document.writeln(strCalBodyBottom);
  wCal.document.close();

}

function show2( func )
{
  var i;
  wCal.document.open();
  wCal.document.writeln(strCalHead);
  wCal.document.writeln('<body bgcolor="#ffffff"><table width="215" border="0" cellspacing="0" cellpadding="0"><tr><td height="20"><img src="/plm/portal/images/ca_title.gif" width="215" height="19"></td></tr><tr><td background="/plm/portal/images/ca_bg.gif" align="center" height="21">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.opener.change2(\'Y\', -1, \''+func+'\');"><img src="/plm/portal/images/ic_d_back.gif" width="11" height="10" border=0></a>&nbsp;<a href="javascript:window.opener.change2(\'M\', -1, \''+func+'\');"><img src="/plm/portal/images/ic_back.gif" width="5" height="10" border=0></a>&nbsp;&nbsp;&nbsp;');
  wCal.document.writeln('<b><font color="#000066">' + intYear + '년 ' + intMonth + '월</font></b>&nbsp;&nbsp;&nbsp;');
  wCal.document.writeln('<a href="javascript:window.opener.change2(\'M\', 1, \''+func+'\' );"><img src="/plm/portal/images/ic_next.gif" width="5" height="10" border=0></a>&nbsp;<a href="javascript:window.opener.change2(\'Y\', 1, \''+func+'\');"><img src="/plm/portal/images/ic_d_next.gif" width="11" height="10" border=0></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.opener.deleteDate();"><img src="/plm/portal/images/ca_clear.gif" width="16" height="16" border=0></a></td></tr></table><tr><td><table width="215" border="0" cellspacing="1" cellpadding="0" bgcolor="89A2CB"><tr><td align="center"><table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="D9D9FF">');
  wCal.document.writeln(strCalBodyCenter);
//alert("intDate="+intDate);
  for(dateCnt = intDate - getDay(); dateCnt > 1; dateCnt -= 7);
  lastDate = MonthLength(intYear, intMonth);

  while (dateCnt <= lastDate)
  {
    wCal.document.writeln('<tr>');

    for(i = 0; i < 7; i++, dateCnt++)
    {
      if(i == 0) wCal.document.writeln('<td bgcolor="DDE4EC" height="20">');
      else wCal.document.writeln('<td bgcolor="#FFFFFF" height="20">');

      if(dateCnt > 0 && dateCnt <= lastDate) {
        if(dateCnt == intDate) wCal.document.writeln('<a href=\"javascript:window.opener.' + strReturnFunction + '(' + dateCnt+ ',\''+func+'\');\"><font color="#ff0000"><b>' + dateCnt + '</b></font></a>');
        else wCal.document.writeln('<a href=\"javascript:window.opener.' + strReturnFunction + '(' + dateCnt +',\''+func+'\' );\">' + dateCnt + '</a>');
      } else wCal.document.writeln('&nbsp;');

      wCal.document.writeln('</td>');
    }
    wCal.document.writeln('</tr>');
  }

  wCal.document.writeln(strCalBodyBottom);
  wCal.document.close();

}

//값을 리턴하는 함수들
function setValue(dt) {
  objTarget.focus();
  wCal.close();
  intDate = dt;
  objTarget.value = toDataString();
}

// [PLM System 1차개선] 달력 닫은 후 후속 function 실행되도록 순서 변경, 2013-08-16, BoLee
function setValue2( dt, func ) {

  intDate = dt;
  objTarget.value = toDataString();
  objTarget.focus();
  wCal.close();
  setTimeout(function() {eval(func+"();");}, 50);
}

function setValue3( dt, func ) {

  intDate = dt;
  objTarget.value = toDataString();
  objTarget.focus();
  wCal.close();
  eval(func);
}

function setOption(dt) {
  objTarget.focus();
  wCal.close();
  intDate = dt;
  objTarget.options[objTarget.length] = new Option(toDataString(), toDataString());
}

function runFunction(dt) {
  objTarget.focus();
  wCal.close();
  intDate = dt;
  objTarget(toDataString());
}

//common.js 에 있는 내용 추가 나종원 20030613
//************************************
// 날짜가 YYYYMMDD 형식에 맞는지 체크
// 2001-07-03 inkkim
//************************************
function isYYYYMMDD(v_date)
{
  if(v_date.length != 8)
  {
    return false;
  }
  if(!alphanum(v_date))
  {
    return false;
  }
  if(!(isDate(v_date.substr(0, 4), v_date.substr(5, 2), v_date.substr(7, 2))))
  {
    return false;
  }
  if( v_date.substr(0, 4) < 1901 || v_date.substr(0, 4) > 2999 )
  {
    return false;
  }
  return true;
}

//**********************************************
//정확한 날짜인지 체크
//해당달의 마지막 날짜가 30일인지 31일인지 체크
//**********************************************
function isDate(yyyy, mm, dd)
{
  yyyy = parseInt(yyyy, 10);
  mm = parseInt(mm, 10);
  dd = parseInt(dd, 10);
  d = new Date(yyyy, mm - 1, dd);
  if(d == "NaN") return false;
  if(yyyy == d.getFullYear() && mm == d.getMonth() + 1 && dd == d.getDate()) return true;
  return false;
}

//*********************
//숫자만 받기위한 체크
//*********************
function alphanum(name)
{
  for (var i = 0; i < name.length; i++)
  {
    if( i != 4 && i != 7 )
    {
      if ((name.charAt(i) < '0') || (name.charAt(i) > '9')) return false;
    }
  }
  return true;
}

function toInteger(strNo)
{
  if(!alphanum(strNo)) return;
  var rValue = 0;
  for(i = strNo.length - 1, weight = 1; i >= 0; i--, weight *= 10)
  {
    switch (strNo.charAt(i))
    {
      case '9': rValue += (weight * 9); break;
      case '8': rValue += (weight * 8); break;
      case '7': rValue += (weight * 7); break;
      case '6': rValue += (weight * 6); break;
      case '5': rValue += (weight * 5); break;
      case '4': rValue += (weight * 4); break;
      case '3': rValue += (weight * 3); break;
      case '2': rValue += (weight * 2); break;
      case '1': rValue += weight; break;
      default:
    }
  }

  return rValue;
}

function clearDate(targetId) {
  var tartxt = document.getElementById(targetId);
  tartxt.value = "";
}

