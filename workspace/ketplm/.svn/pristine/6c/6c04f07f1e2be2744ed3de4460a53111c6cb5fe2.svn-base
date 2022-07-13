 /**
 * @(#)	table.js
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @author Seung-hwan Choi, skyprda@e3ps.com
 * @desc table.jsp? ???? ???? ?? ??/??? ??.
 */

// ???????????? ????????? ????????????.
function insertLine(tableName) 
{
	var tableObj = document.getElementById(tableName);
	var index = tableObj.rows.length;
	
	var originalTr = tableObj.rows[1];
	originalTr.style.display = 'block';

	trObj = tableObj.insertRow(index);
	trObj.replaceNode(originalTr.cloneNode(true));
	trObj = tableObj.children(0).children(index);
	
//	for(var i=0 ; i<trObj.children.length ; i++)
//		trObj.children(i).id = "tb_gray";

//	trObj.onmouseover = "this.style.backgroundColor='#efefef'";
//	trObj.onmouseout = "this.style.backgroundColor='#ffffff'";

	originalTr.style.display = 'none';

	return trObj;
}
	
// ??? ???? ??? ????.
function deleteLine(tableName) 
{
	var tableObj = document.getElementById(tableName);
	var trLength = tableObj.children(0).children.length;
	for (var i=trLength-1 ; i>0 ; i-- )
	{
		if(tableObj.children(0).children(i).children(0).children(0) == null ) continue;
		if( "checkbox" == tableObj.children(0).children(i).children(0).children(0).type )
			if(tableObj.children(0).children(i).children(0).children(0).checked)
				tableObj.deleteRow(i);
	}
}

// ??? ???? ??? ????.
function deleteLine2(tableName, delOid) 
{
	var tableObj = document.getElementById(tableName);
	var trLength = tableObj.children(0).children.length;

	for (var i=trLength-1 ; i>0 ; i-- )
	{
		if(tableObj.children(0).children(i).children(0).children(0) == null ) continue;
//		if( "checkbox" == tableObj.children(0).children(i).children(0).children(0).type )
		if(tableObj.children(0).children(i).children(0).children(0).value == delOid)
		{		
			tableObj.deleteRow(i);
			break;
		}
	}
}

// isInclude : true, notInclude : false
function isIncludeTableItem(tableName)
{
	var tableObj = document.getElementById(tableName);
	var trLength = tableObj.rows.length;
	
	for(var i=trLength-1 ; i>0 ; i--)
	{
		if(tableObj.children(0).children(i).children(0).children(0) != null)
			return true;
	}
	return false;
}

// View a Table Item
function viewTableItem(oid)
{
	// import viewObject.jsp
	openView(oid);
}

// Open a Search Window
function searchWindow(tableName, searchPage)
{
//	openWindow("", , "600", "400");
	document.getElementById('ACTIVE_E3PS_TABLE').value = tableName;
	
	width = 800;
	height = 510;
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
	rest = "width=" + width + ",height=" + height+',left=' + ((screen.width - width)/2) + ',top=' + ((screen.height - height)/2);
	var newwin = open( searchPage , 'E3PSTABLE', opts+rest);
	newwin.focus();
}

function addOID(tableName, oid)
{
	eval("dataArray"+tableName+".push(new Array('"+oid+"'))");
}
function addTableValue(tableName, value)
{
	if(value == "null") value = "";
	matchStr = new RegExp("'", "gi");
	value = value.replace( matchStr, "&#39;");

	var popArray = eval("dataArray"+tableName+"[dataArray"+tableName+".length-1]");
	popArray.push(""+value);
}
function printTable(tableName)
{
	var viewMode = document.getElementById('viewmode'+tableName).value;
	var addType = document.getElementById("addtype"+tableName).value;
	var length = eval("dataArray"+tableName+".length");
	
	for (var i=length-1 ; i>-1 ; i-- )
	{
		var objArray = eval("dataArray"+tableName+"[i]");

		if('text' == addType)
		{
			document.getElementById('oid'+tableName).value = objArray[0];
			document.getElementById(tableName).value = objArray[1];
		}
		else
		{
			var newRow = insertLine(tableName);
			if("view" == viewMode) 
			{
				newRow.children(0).innerHTML = "<p align=center>"+(i+1)+"<input type='hidden' name='oid"+tableName+"' value='"+objArray[0]+"'></p>";
			} 
			else 
			{
				newRow.children(0).innerHTML = "<input type='hidden' name='oid"+tableName+"' value='"+objArray[0]+"'>" + 
				"<img src=/plm/portal/icon/delete_x.gif border=0 title='delete' onClick=\"deleteLine2('"+tableName+"', '"+objArray[0]+"')\" style='cursor:hand'>" ;
//			"<input type='button' value='X'  id=innerbutton onClick=\"deleteLine2('"+tableName+"', '"+objArray[0]+"')\" style='width:100%;cursor:hand'>" ;
			}
			
			for (var j=objArray.length-1 ; j>0 ; j-- )
			{
				if(newRow.children(j) == null) continue;
				
				if(j == 1)
					newRow.children(j).innerHTML = "&nbsp;<a href='javascript:viewTableItem(\""+objArray[0] + "\")'>"+objArray[j]+"</a>";
				else
					newRow.children(j).innerHTML = "&nbsp;"+objArray[j];
			}
		}
	}
}
/**/
