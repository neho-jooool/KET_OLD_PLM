 /**
 * @(#)	checkbox.js
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @author Seung-hwan Choi, skyprda@e3ps.com 한글.
 */
 
var cboxAll = document.forms[0].checkboxAll;
var cbox = document.forms[0].checkboxEach;
var len;

function checkCbox()
{
	cbox==null ? len=0 : len=cbox.length;
	if(len == null) len = 0;
}


function selectAll() 
{	
	checkCbox();
	if(cbox != null)
		if(len > 1){
			for(var i=0 ; i<len ; i++) 
			{
				if ( cboxAll.checked == true ) cbox.checkboxEach[i].checked=true;
				else	cbox.checkboxEach[i].checked=false;
			}
		}else{
			if ( cboxAll.checked == true ) cbox.checked=true;
			else cbox.checked=false;
		}
} 

function selectAllUnChecked()
{
	checkCbox();
	if(cbox != null) {
		if (len > 1){
			if ( cboxAll.checked == true) cboxAll.checked = false;
		} else{
			if ( cboxAll.checked == true) cboxAll.checked = false;
			else cboxAll.checked = true;
		}

		for (var i=0 ; i<len ; i++ )
		{
			if(len>1) {
				if( cbox.checkboxEach[i].checked != true) break;
				if ( (i+1) == len) cboxAll.checked = true;
			}
		}
	}
}

function isChecked()
{
	checkCbox();
	if(cbox != null) {
		if(len > 1){
			for(var i=0 ; i<len ; i++) 
			{
				if( cbox.checkboxEach[i].checked == true )
					return true;
			}
		}else{
			if ( cbox.checked == true )
					return true;
		}
	}
	alert('???? ???? ??????????');
	return false;
}

function getSelectedList()
{
	var selectedList = "";
	checkCbox();
	if(cbox != null) 
	{
		if(len > 1)
		{
			for(var i=0 ; i<len ; i++) 
			{			
				if (cbox.checkboxEach[i].checked==true)
					selectedList += "oid="+cbox.checkboxEach[i].value+"&";
			}
		}else{
			if (cbox.checked==true) selectedList += "oid="+cbox.value+"&";
		}	
	}
	return selectedList;
}

function readyToDelete()
{
	var list = getSelectedList();
	if(list.length == 0)
	{
		alert("?????? ?????? ??????????");
		return false;
	}

//	if(!confirm('????!!\n?????? ?????????????????'))
//		return false;

	return true;
}