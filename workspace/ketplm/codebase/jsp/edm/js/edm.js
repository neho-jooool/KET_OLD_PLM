function goCheckAll(_all,_itemName) {
	var chkItems = document.getElementsByName(_itemName);
	for(var i = 0; i < chkItems.length; i++) {
		chkItems[i].checked = _all.checked;
	}
}

function goCheckItem(_chkedItem) {
	if(_chkedItem.checked == false) {
		return;
	}

	var chkItems = document.getElementsByName(_chkedItem.name);
	
	for(var i = 0; i < chkItems.length; i++) {
		if(chkItems[i].value == _chkedItem.value) {
			continue;
		}

		if(chkItems[i].checked == true) {
			chkItems[i].checked = false;
			break;
		}
	}

	return;
}

function getSeledSelectText(str) {
	var lselectObj = document.getElementsByName(str);
	if(lselectObj.length > 0) {
		var _lobj = lselectObj[0];
		for (var i=0; i < _lobj.options.length; i++) {
			if(_lobj.options[i].selected) {
				return _lobj.options[i].text;
			}
		}
	}
	return '';
}

function initCheckBox(str) {
	var boxObj = document.getElementsByName(str);
	
	for(var i = 0; i < boxObj.length; i++) {
		boxObj[i].checked = false;
	}
	return false;
}

function seledCheckBox(str,strvalue) {
	var boxObj = document.getElementsByName(str);
	
	for(var i = 0; i < boxObj.length; i++) {
		if(boxObj[i].value == strvalue) {
			boxObj[i].checked = true;
			return true;
		}
	}
	return false;
}

function removeOptions(selectObj) {	
	removeOptionsNDefault(selectObj, false, "","");
}

function removeOptionsNDefault(selectObj,isDefault,iText,iValue) {	
	if (selectObj.length){
		for (var i=selectObj.options.length; i>0; i--){
			//selectObj.options.remove(i-1);
			selectObj.removeChild(selectObj.options[i-1]);
		}
	}

	if(isDefault == true) {
		addOptions(selectObj, iText, iValue, true);
	}
}

function addOptions(selectObj,itext,ivalue,iselected) {
	newOpt=document.createElement("OPTION");
	newOpt.text=itext;
	newOpt.value=ivalue;
	newOpt.selected=iselected;
	selectObj.add(newOpt);
}



function selectRadio(str, value) {
	var radioObj = document.getElementsByName(str);
	for(var i = 0; i < radioObj.length; i++) {
		if(radioObj[i].value == value) {
			radioObj[i].checked = true;
		} else {
			radioObj[i].checked = false;
		}
	}
	return true;
}

function disabledRadio(str, disableFlag) {
	var radioObj = document.getElementsByName(str);
	for(var i = 0; i < radioObj.length; i++) {
		radioObj[i].disabled = disableFlag;
	}
	return true;
}

function disabledRadioToValue(str, oValue, disableFlag) {
	var radioObj = document.getElementsByName(str);
	for(var i = 0; i < radioObj.length; i++) {
		if(radioObj[i].value == oValue) {
			radioObj[i].disabled = disableFlag;
		}
	}
	return true;
}

function getValueRadio(str) {
	var radioObj = document.getElementsByName(str);
	for(var i = 0; i < radioObj.length; i++) {
		if(radioObj[i].checked == true) {
			return radioObj[i].value;
		}
	}
	return '';
}

function isCheckedRadio(str) {
	var radioObj = document.getElementsByName(str);
	for(var i = 0; i < radioObj.length; i++) {
		if(radioObj[i].checked == true) {
			return true;
		}
	}
	return false;
}

function getObjectLength(obj) {
	var p, len=0;  // 한글문자열 체크를 위함
	for(p=0; p< obj.value.length; p++) {
		(obj.value.charCodeAt(p)  > 255) ? len+=2 : len++;  // 한글체크
	}
	return len;
}

/*
 * 문자열 길이 체크
 */
function lwGetByte(s) {
	var ls_str = s;
	var li_str_len = ls_str.length;

	var i = 0;
	var li_byte = 0;
	var ls_one_char = "";
	var ls_str2 = "";

	for(i=0; i< li_str_len; i++) {
		ls_one_char = ls_str.charAt(i);

		if (escape(ls_one_char).length > 4) {
			li_byte += 2;
		//} else if(ls_one_char == "\r" && ls_str.charAt(i+1) == "\n") {// \r\n 일 경우
		//	li_byte += 2;
		} else {
			li_byte++;
		}

	}

	return li_byte;
}

/*
 * 문자열 자르기
 */
function lwTrimByte(s, nMaxByte) {

	var ls_str = s;
	var li_str_len = ls_str.length;

	var li_max = nMaxByte;
	var i = 0;
	var li_len = 0;
	var li_byte = 0;
	var ls_one_char = "";
	var ls_str2 = "";

	for(i=0; i< li_str_len; i++) {
		ls_one_char = ls_str.charAt(i);

		if (escape(ls_one_char).length > 4) {
			li_byte += 2;
		//} else if(ls_one_char == "\r" && ls_str.charAt(i+1) == "\n") {// \r\n 일 경우
		//	li_byte += 2;
		} else {
			li_byte++;
		}
		if(li_byte <= li_max) {
			li_len = i + 1;
		}
		if(li_byte > li_max) {
			ls_str2 = ls_str.substr(0, li_len);
			return ls_str2;
		}
	}
}


/*****************************************************************************************	 
 * 날자 Format 체크(yyyy/mm/dd)
 * 시작일자/종료일자 체크 시작
 *****************************************************************************************/

function onValidCal(obj, warning) {
	var input_date = obj.value;

	var regexp=/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;//날짜 패턴
	input_date = input_date.match(regexp);

	/*
	if(input_date.length == 10) {
		input_date = input_date.match(regexp);
	}else{
		input_date = null;
	}
	*/
	if(input_date == null){
		if(warning == true) { alert("'YYYY-MM-DD' 형식의 숫자를 입력하시기 바랍니다."); }
		return false;
	}

	input_date = obj.value;

	var regexp1=/-/g; // 정규식 패턴 대신 리터럴로
	input_date = input_date.replace(regexp1, "");//yyyyMMdd

	input_yyyy = input_date.substring(0, 4);
	input_mm = input_date.substring(4, 6);
	input_dd = input_date.substring(6);
	
	var mm_num = new Number(input_mm);
	if( (mm_num < 1) || (mm_num > 12) ) {
		if(warning == true) { alert("'월'을 01에서 12까지만 입력하시기 바랍니다."); }
		return false;
	}
	var dd_num = new Number(input_dd);
	if( (dd_num < 1) || (dd_num > 31) ) {
		if(warning == true) { alert("'일'을 01에서 31까지만 입력하시기 바랍니다."); }
		return false;
	}
	if( (input_yyyy == '0000') || (input_mm == '00') || (input_dd == '00') ) {
		if(warning == true) { alert("'0000-00-00'은 입력할 수 없습니다."); }
		return false;
	}
	return true;
}

function onCalcTime(date_begin, date_end) {
	var day_time = 1000*60*60*24;//1일

	var lBeginDate = document.getElementById(date_begin);
	var lEndDate = document.getElementById(date_end);

	if(!onValidCal(lBeginDate, true)) { return false; }
	if(!onValidCal(lEndDate, true)) { return false; }
	
	var std_num = 1;		
	var start_point;
	var dest_point;	
	start_point = lBeginDate;
	dest_point = lEndDate;

	var start_date=dest_date=new Date();
	start_year = new Number(start_point.value.substring(0,4));
	start_month = new Number(start_point.value.substring(5,7))-1;
	start_day = new Number(start_point.value.substring(8,10));

	dest_year = new Number(dest_point.value.substring(0,4));
	dest_month = new Number(dest_point.value.substring(5,7))-1;
	dest_day = new Number(dest_point.value.substring(8,10));

	//alert(start_year + "\t" + start_month + "\t" + start_day +"\n" + dest_year + "\t" + dest_month + "\t" + dest_day);

	start_date = new Date(start_year, start_month, start_day);
	dest_date = new Date(dest_year, dest_month, dest_day);

	gap_time = dest_date.getTime() - start_date.getTime();
	gap_days = (gap_time/day_time) + 1;

	if(gap_days < 0) {
		alert("'"+lEndDate.datemsg+"'를(을) '"+lBeginDate.datemsg+"' 보다 빠른 날짜를 입력하셨습니다.\n확인 후 다시 입력하시기 바랍니다!!!");
		lBeginDate.value = '';
		lEndDate.value = '';
		return false;
	}
	return true;
}
