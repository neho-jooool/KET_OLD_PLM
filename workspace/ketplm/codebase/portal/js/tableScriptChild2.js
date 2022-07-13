 /**
 * @(#)	tableScriptChild.js
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @author Seung-hwan Choi, skyprda@e3ps.com
 * @desc Add selected values to parent window.
  *          Import this file with checkbox2.js
 */
var openWin;

// a event of a Select-Button
function addSelectedItem(tableId)
{
	var result = '';
	openWin = opener==null ? parent.opener : opener;

	var openerTableName = openWin.document.getElementById('ACTIVE_E3PS_TABLE').value;

	var addType = openWin.document.getElementById("addtype"+openerTableName);

//	if("text" != addType.value) printTableTitle(tableId, openerTableName);

	// Set the value in Table
	if("one" == addType.value)
		result = setOne(tableId, openerTableName);
	else if("multi" == addType.value)
		result = setMulti(tableId, openerTableName);
	else if("text" == addType.value)
		result = setText(tableId, openerTableName);
	else if("iframeOne" == addType.value)
		result = setIframeOne(tableId, openerTableName);
		
	if(openWin.document.getElementById("submit"+openerTableName) != null
	  && 'true' == openWin.document.getElementById("submit"+openerTableName).value)
		openWin.document.forms[0].submit();

	// Alert the Result Message
	if(result != 0)
		alert(result +unescape('%uCD94%uAC00%uB418%uC5C8%uC2B5%uB2C8%uB2E4'));		

	if("one" == addType.value || "text" == addType.value) self.close();		
}

// Add a Value in input type=text
function setText(tableId, openerTableName)
{
	var tableObj = document.getElementById(tableId);
	var trLength = tableObj.rows.length;

	var result = '';
	for(var i=1 ; i<trLength ; i++)
	{
		if( tableObj.rows[i].cells[0].children[0].checked )
		{
			openWin.document.getElementById('oid'+openerTableName).value = tableObj.rows[i].cells[0].children[0].value;
			openWin.document.getElementById(openerTableName).value = tableObj.rows[i].cells[1].innerText;

			return tableObj.rows[i].cells[1].innerText+'\n'
		}
	}

	return '';
}

// Add a value
function setOne(tableId, openerTableName)
{
	var parentTableObj = openWin.document.getElementById(openerTableName);
	var tableRowLength = parentTableObj.rows.length;

	if( tableRowLength > 2)
	{
		alert(unescape("%uC774%uBBF8%20%uD558%uB098%uAC00%20%uB4F1%uB85D%uB418%uC5B4%uC788%uC2B5%uB2C8%uB2E4."));
		return 0;
	}

	var tableObj = document.getElementById(tableId);
	var trLength = tableObj.rows.length;

	var result = '';
	for(var i=1 ; i<trLength ; i++)
	{
		if(i==0)
		{
			var idxField = openWin.document.getElementById('idxfield'+openerTableName).value.split('$');
			for(var f=idxField.length-1 ; f>0 ; f--)
			{
				parentTableObj.rows[0].children( idxField[f] ).innerHTML = tableObj.rows[0].children( idxField[f] ).innerText;
			}
		}
		else
		{
			if( tableObj.rows[i].cells[0].children[0].checked )
			{
				result = setData(openerTableName, tableObj.rows[i]);
				break;
			}
		}
	}

	return result;
}

function setIframeOne(tableId, openerTableName)
{
	var parentTableObj = openWin.document.getElementById(openerTableName);
	var tableRowLength = parentTableObj.rows.length;

	if( tableRowLength > 2)
	{
		alert(unescape("%uC774%uBBF8%20%uD558%uB098%uAC00%20%uB4F1%uB85D%uB418%uC5B4%uC788%uC2B5%uB2C8%uB2E4."));
		return 0;
	}

	var tableObj = document.cen.document.getElementById(tableId);
	var trLength = tableObj.rows.length;

	var result = '';
	for(var i=1 ; i<trLength ; i++)
	{
		if(i==0)
		{
			var idxField = openWin.document.getElementById('idxfield'+openerTableName).value.split('$');
			for(var f=idxField.length-1 ; f>0 ; f--)
			{
				parentTableObj.rows[0].children( idxField[f] ).innerHTML = tableObj.rows[0].children( idxField[f] ).innerText;
			}
		}
		else
		{
			if( tableObj.rows[i].cells[0].children[0].checked )
			{
				result = setData(openerTableName, tableObj.rows[i]);
				break;
			}
		}
	}

	return result;
}

// Add various value
function setMulti(tableId, openerTableName)
{
	var parentTableObj = openWin.document.getElementById(openerTableName);
	var result = '';

	var tableObj = document.getElementById(tableId);
	var trLength = tableObj.rows.length;

	for(var i=1 ; i<trLength ; i++)
	{
		var oid = tableObj.rows[i].cells[0].children[0].value;
		if( tableObj.rows[i].cells[0].children[0].checked && duplicateRow(openerTableName, oid))
		{
			result += setData(openerTableName, tableObj.rows[i]);
		}
	}
	return result;
}

function printTableTitle(tableId, openerTableName)
{
	if(tableId == null) return;
	
	var parentTableObj = openWin.document.getElementById(openerTableName);
	var tableObj = document.getElementById(tableId);
	
	var idxField = openWin.document.getElementById('idxfield'+openerTableName).value.split('$');
	for(var f=idxField.length-1 ; f>-1 ; f--)
	{
		parentTableObj.rows[0].cells[ f+1 ].innerHTML = tableObj.rows[0].cells[ parseInt(idxField[f]) ].innerText;
	}
}

// Check a duplication
function duplicateRow(openerTableName, oid)
{
	var parentTableObj = openWin.document.getElementById(openerTableName);
	var trLength = parentTableObj.children(0).children.length;

	for (var i=trLength-1 ; i>0 ; i-- )
	{
		if(parentTableObj.children(0).children(i).children(0).children(0) == null) continue;
//		if( "checkbox" == parentTableObj.children(0).children(i).children(0).children(0).type )
			if(parentTableObj.children(0).children(i).children(0).children(0).value == oid)
			{
				alert(unescape('%uC774%uBBF8%20%uB4F1%uB85D%uB418%uC5B4%20%uC788%uC2B5%uB2C8%uB2E4'));
				return false;
			}
	}
	return true;
}

function setData(openerTableName, row)
{
	var result = '';
	var newRow = openWin.insertLine(openerTableName);

	var oid = row.cells[0].children[0].value;
//	newRow.children(0).innerHTML = "<input type='hidden' name='oid"+openerTableName+"' value='"+oid+"'>" + 
//		"<input type='button' value='X' id=innerbutton onClick=\"deleteLine2('"+openerTableName+"', '"+oid+"')\" style='width:100%;cursor:hand'>" ;
		
	newRow.children(0).innerHTML = "<input type='hidden' name='oid"+openerTableName+"' value='"+oid+"'>" + 
	"<img src=/plm/portal/icon/delete_x.gif border=0 title='delete' onClick=\"deleteLine2('"+openerTableName+"', '"+oid+"')\" style='cursor:hand'>" ;
	
//	newRow.children(1).innerHTML = row.cells[1].innerHTML;

	var parentTableObj = openWin.document.getElementById(openerTableName);
	var idxField = openWin.document.getElementById('idxfield'+openerTableName).value.split('$');
	for(var f=idxField.length-1 ; f>-1 ; f--)
	{
		if(f==0)
		{
			newRow.children(1).innerHTML = "&nbsp;<a href='javascript:viewTableItem(\""+oid + "\")'>"+row.cells[1].innerText+"</a>";
			result = row.cells[1].innerText+'\n';
		}
		else
		{
//			newRow.children( f+1 ).innerHTML = row.cells[parseInt(idxField[f])].innerText;
			newRow.children( f+1 ).innerHTML = row.cells[parseInt(idxField[f])].innerHTML;
		}
	}
	return result;
}
