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
