//ecmUtil.js

//=============================================================================
// 문자열 절사
// param : obj 입력폼
// style='IME-MODE: disabled' : 입력창에 한글입력방지
//============================================================================
function getTruncStr(str, len) {
    if(str.length > len) {
        str = str.substring(0, len) + "...";
    }
    return str;
}

//=============================================================================
// 숫자를 1000단위 콤마로 구분된 문자열로 반환
// param : obj 입력폼
// style='IME-MODE: disabled' : 입력창에 한글입력방지
//============================================================================
function SetMoneyFormat(obj){
    val=obj.value;
    obj.value = money_format(val);
}

//=============================================================================
//숫자를 1000단위 콤마로 구분된 문자열로 반환
//param : obj 입력폼
//style='IME-MODE: disabled' : 입력창에 한글입력방지
//============================================================================
function SetNumberFormat(obj){
    val=obj.value;
    obj.value = val.replace(",", "");
}

//select box 추가
function addSelectBox(objSelectBox, addText, addValue , selectValue)
{
    var optItem = new Option();

    optItem.text     = addText;
    optItem.value    = addValue;

    if( selectValue == addValue )
    {
        optItem.selected = true;
    }

    objSelectBox.options[objSelectBox.options.length] = optItem;
}

//select box 전체 삭제
function deleteAllSelectBox(objSelectBox)
{
	if(typeof objSelectBox != 'undefined') {
	    for( var i = 0; i < objSelectBox.options.length; i++ )
	    {
	        objSelectBox.options[i].value = null;
	        objSelectBox.options[i].text  = null;
	    }
	
	    objSelectBox.options.length = 0;
	    
	}
}

// String의 Byte 수 체크하는 함수 (한글은 2, 영문/숫자는 1)
function getStringLength( str )
{
     var ls_str = str;
     var li_str_len = ls_str.length;

      var i = 0;
      var li_byte = 0;
      var ls_one_char = "";
      var ls_str2 = "";

      for(i=0; i< li_str_len; i++) {
            ls_one_char = ls_str.charAt(i);

            if (escape(ls_one_char).length > 4) {
                  li_byte += 2;
            } else {
                  li_byte++;
            }
      }
      return li_byte;
}

//숫자인지 확인하는 함수
function isNumber(input) {
    var chars = "0123456789";
    return containsCharsOnly(input,chars);
}

function containsCharsOnly(input,chars) {

    var isValid = true;
    for (var inx = 0; inx < input.length; inx++) {
       if (chars.indexOf(input.charAt(inx)) == -1)
           isValid =  false;
    }
    if( input.length < 1  )
    {
        isValid = false;
    }

    return isValid;
}
