function changeSelectField(obj, idx, startPos, valueLength)
{
	var val = obj.value;
	var number = document.forms[0].number;

	if( number[idx].value.length < startPos )
	{
		alert("앞자리 번호를 먼저 선택하세요");
		obj.selectedIndex = 0;
		return;
	}

	if(number[idx].value.length == startPos)
	{
		document.forms[0].number[idx].value = number[idx].value + val;
	}
	else if (number[idx].value.length > startPos && number[idx].value.length <= (startPos+valueLength))
	{
		document.forms[0].number[idx].value = number[idx].value.substring(0, startPos) + val;
	}
	else
	{
		document.forms[0].number[idx].value = number[idx].value.substring(0, startPos) + val + number[idx].value.substring( startPos+valueLength , number[idx].value.length);
	}
}

function checkNumCode(obj, idx, tagName)
{
	var no = obj.value;
	no = no.toUpperCase();
	var number = document.forms[0].number;

	document.forms[0].number[idx].value = no;

	if(tagName == "carCode")
	{
		document.forms[0].carCode.value = no;
	}
	else if(tagName == "itemCode")
	{
		document.forms[0].itemCode.value = no;
	}
	else if(tagName == "machineCode")
	{
		document.forms[0].machineCode.value = no;
	}
	else if(tagName == "middleCode")
	{
		document.forms[0].middleCode.value = no;
	}
	else if(tagName == "projectCarCode")
	{
		document.forms[0].projectCarCode.value = no;
	}
	else if(tagName == "selectCode")
	{
		document.forms[0].selectCode.value = no;
	}
	else if(tagName == "setNum")
	{
		document.forms[0].setNum.value = no;
	}
	else if(tagName == "setNo")
	{
		document.forms[0].setNo.value = no;
	}

}
