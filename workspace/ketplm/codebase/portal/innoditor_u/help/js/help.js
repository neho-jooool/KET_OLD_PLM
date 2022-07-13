﻿var nSelectedItemIndex = 0;
var nItemLength = 0;
var strItemClassName = "";

function fnHelpItemOver(objItem, nItemIndex)
{
	if(nSelectedItemIndex != nItemIndex)
	{
		objItem.className = strItemClassName + "_r";
	}
}

function fnHelpItemOut(objItem, nItemIndex)
{
	if(nSelectedItemIndex != nItemIndex)
	{
		objItem.className = strItemClassName;
	}
}

function fnHelpItemClick(nItemIndex)
{
	if(nSelectedItemIndex != nItemIndex)
	{
		var objItem_Prev = document.getElementById("helpItem_" + nSelectedItemIndex);
		objItem_Prev.className = strItemClassName;

		var objItem_Next = document.getElementById("helpItem_" + nItemIndex);
		objItem_Next.className = strItemClassName + "_r";


		nSelectedItemIndex = nItemIndex;

		for(var i=0; i<nItemLength; i++)
		{
			var objItem = document.getElementById("helpItem_" + i);
			var objItemMain = document.getElementById("helpItemMain_" + i);

			if(i != nItemIndex)
			{
				objItem.className = strItemClassName;
				objItemMain.style.display = "none";
			}
			else
			{
				objItemMain.style.display = "";
			}
		}
	}
}
