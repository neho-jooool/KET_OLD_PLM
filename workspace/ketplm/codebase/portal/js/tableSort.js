 /**
 * @(#)	tableSort.js
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @author Seung-hwan Choi, skyprda@e3ps.com
 * @desc Sort rows of TABLE
 */
function tableSort(tableId, col) 
{
	var tableObj = document.getElementById(tableId);
	var tableBody = tableObj.tBodies[0];
	var tempTRs = new Array;

	var tableRows = tableBody.rows;
	if(tableRows.length < 2) return;

	var titleRow = null;
	for (var i=0; i < tableRows.length; i++)
	{
		if(i==0) titleRow = tableRows[i]
		else tempTRs.push(tableRows[i]);
	}

	var bubble = tempTRs.length;
	while(--bubble > 0)
	{
		for(var i=0 ; i<tempTRs.length-1 ; i++)
		{
			if(tempTRs[i].cells[ col ].innerText > tempTRs[i+1].cells[ col ].innerText)
			{
				var temp = tempTRs[i+1];
				tempTRs[i+1] = tempTRs[i];
				tempTRs[i] = temp;
			}
		}
	}

	if(tableRows[1].cells[col].innerText < tableRows[tableRows.length-1].cells[col].innerText) 
		tempTRs.reverse();

	var fragment = document.createDocumentFragment();
	fragment.appendChild(titleRow);
	for (var i=0; i < tempTRs.length; i++) {
		fragment.appendChild(tempTRs[i]);
	}

	tableBody.appendChild(fragment);
}
