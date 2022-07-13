 /**
 * @(#)	tableScriptChild.js
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @author Seung-hwan Choi, skyprda@e3ps.com
 * @desc Add selected values to parent window.
  *          Import this file with checkbox2.js
 */

// Collection to be included in the set of Result
var dataArray = new Array(); 
var openWin;
// Add new Result Array to dataArray Collection Object
function addOID(oid)
{
	dataArray.push(new Array(oid));
}

// Add a displayed value to new Result Array
function addTableValue(value)
{
	if(value == "null") value = "";
	matchStr = new RegExp("'", "gi");
	value = value.replace( matchStr, "&#39;");

	var popArray = dataArray[dataArray.length-1];
	popArray.push(""+value);
}

// a event of a Select-Button
function addSelectedItem(cbox)
{	
	var result = '';
	openWin = opener==null ? parent.opener : opener;
	var openerTableName = openWin.document.getElementById('ACTIVE_E3PS_TABLE').value;

	var addType = openWin.document.getElementById("addtype"+openerTableName);
	if("one" == addType.value)
		result = setOne(cbox, openerTableName);
	else if("multi" == addType.value)
		result = setMulti(cbox, openerTableName);
	else if("text" == addType.value)
		result = setText(cbox, openerTableName);
		
	if(openWin.document.getElementById("submit"+openerTableName) != null
	  && 'true' == openWin.document.getElementById("submit"+openerTableName).value)
		openWin.document.forms[0].submit();

	if(result != 0)
		alert(result +unescape('%uCD94%uAC00%uB418%uC5C8%uC2B5%uB2C8%uB2E4'));		

	if("one" == addType.value || "text" == addType.value) self.close();		
}

// Add a Value in input type=text
function setText(cbox, openerTableName)
{
	var selectedCBox = getSelectedCBox(cbox);
	
	for (var i=dataArray.length-1 ; i>-1 ; i-- )
	{
		var objArray = dataArray[i];
		if(objArray[0] == selectedCBox.value)
		{
			openWin.document.getElementById('oid'+openerTableName).value = objArray[0];
			openWin.document.getElementById(openerTableName).value = objArray[1];

			return objArray[1]+'\n';
		}
	}
	return '';
}

// Add a value
function setOne(cbox, openerTableName)
{
	var parentTableObj = openWin.document.getElementById(openerTableName);
	var tableRowLength = parentTableObj.rows.length;

	if( tableRowLength > 2)
	{
		alert(unescape("%uC774%uBBF8%20%uD558%uB098%uAC00%20%uB4F1%uB85D%uB418%uC5B4%uC788%uC2B5%uB2C8%uB2E4."));
		return 0;
	}
	var selectedCBox = getSelectedCBox(cbox);
	return setData(openerTableName, selectedCBox);
}

// Add various value
function setMulti(cbox, openerTableName)
{
	var result = '';
	var parentTableObj = openWin.document.getElementById(openerTableName);
	var len = checkCbox(cbox);
	if(cbox != null) 
	{
		if(len > 1)
		{
			for(var i=0 ; i<len ; i++) 
			{
				if (cbox[i].checked && duplicateRow(openerTableName, cbox[i]))
					result += setData(openerTableName, cbox[i]);
			}
		}
		else
		{
			if (cbox.checked && duplicateRow(openerTableName, cbox)) 
				result = setData(openerTableName, cbox);
		}
	}
	return result;
}

// Check a duplication
function duplicateRow(openerTableName, selectedCBox)
{
	var tableObj = openWin.document.getElementById(openerTableName);
	var trLength = tableObj.children(0).children.length;

	for (var i=trLength-1 ; i>0 ; i-- )
	{
		if(tableObj.children(0).children(i).children(0).children(0) == null) continue;
//		if( "checkbox" == tableObj.children(0).children(i).children(0).children(0).type )
			if(tableObj.children(0).children(i).children(0).children(0).value == selectedCBox.value)
			{
				alert(unescape('%uC774%uBBF8%20%uB4F1%uB85D%uB418%uC5B4%20%uC788%uC2B5%uB2C8%uB2E4'));
				return false;
			}
	}
	return true;
}

function setData(openerTableName, selectedCBox)
{
	var result = '';
	var newRow = openWin.insertLine(openerTableName);

	for (var i=dataArray.length-1 ; i>-1 ; i-- )
	{
		var objArray = dataArray[i];
		if(objArray[0] == selectedCBox.value)
		{
//			newRow.children(0).innerHTML = "<input type='checkbox' name='select"+openerTableName+"' value='"+selectedCBox.value+"'>" 
//										+ "<input type='hidden' name='oid"+openerTableName+"' value='"+selectedCBox.value+"'>";

			// Make a Delete-Button and oid tag
			newRow.children(0).innerHTML = "<input type='hidden' name='oid"+openerTableName+"' value='"+selectedCBox.value+"'>" + 
				"<img src=/plm/portal/icon/delete_x.gif border=0 title='delete' onClick=\"deleteLine2('"+openerTableName+"', '"+selectedCBox.value+"')\" style='cursor:hand'>" ;
			//	"<input type='button' value='X' id=innerbutton onClick=\"deleteLine2('"+openerTableName+"', '"+selectedCBox.value+"')\" style='width:100%;cursor:hand'>" ;
			
			// Write a value to Table
/*			for (var j=objArray.length-1 ; j>0 ; j-- )
			{
				if(newRow.children(j) == null) continue;
				newRow.children(j).innerHTML = objArray[j];
			}
*/			
			for (var j=objArray.length-1 ; j>0 ; j-- )
			{
				if(newRow.children(j) == null) continue;
				
				if(j == 1)
				{
					newRow.children(j).innerHTML = "&nbsp;<a href='javascript:viewTableItem(\""+objArray[0] + "\")'>"+objArray[j]+"</a>";
					result = objArray[j]+'\n';
				}
				else
					newRow.children(j).innerHTML = "&nbsp;"+objArray[j];
			}
		}
	}
	return result;
}
