// <SCRIPT language=JavaScript src="/plm/portal/js/contents.js"></SCRIPT>
 /**
 * @(#)	contents.js
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @author Seung-hwan Choi, skyprda@e3ps.com
 */
 
 
function switchToFile()
{
	document.forms[0].primaryURL.value='';
	document.forms[0].PRIMARY.value=document.forms[0].rememberedFile.value;
	document.forms[0].PRIMARY.focus();
	document.forms[0].targetType.value="FILE";
	document.forms[0].File.value=document.forms[0].PRIMARY.value;
}

function switchToURL() 
{
	document.forms[0].PRIMARY.value='';
	document.forms[0].primaryURL.value=document.forms[0].rememberedURL.value;
	document.forms[0].primaryURL.focus();
	document.forms[0].targetType.value="URL";
	document.forms[0].File.value=document.forms[0].primaryURL.value;
}

function switchToNone() 
{
	document.forms[0].PRIMARY.value='';
	document.forms[0].primaryURL.value='';
	document.forms[0].targetType.value='NONE';
	document.forms[0].File.value='';
}

function changedFile() 
{
	document.forms[0].rememberedFile.value=document.forms[0].PRIMARY.value;
	document.forms[0].primaryURL.value='';
	document.forms[0].primaryContentType[0].checked=true;
	document.forms[0].targetType.value="FILE";
	document.forms[0].File.value=document.forms[0].PRIMARY.value;
}

function changedURL() 
{
	document.forms[0].rememberedURL.value=document.forms[0].primaryURL.value;
	document.forms[0].PRIMARY.value='';
	document.forms[0].primaryContentType[1].checked=true;
	document.forms[0].targetType.value="URL";
	document.forms[0].File.value=document.forms[0].primaryURL.value;
}

function setPrimaryContentValue() 
{
	if (document.forms[0].primaryContentType[0].checked==true) {
		document.forms[0].File.value=document.forms[0].PRIMARY.value;
		document.forms[0].targetType.value="FILE";
	} else if (document.forms[0].primaryContentType[1].checked==true) {
		document.forms[0].File.value=document.forms[0].primaryURL.value;
		document.forms[0].targetType.value="URL";
	} else if (document.forms[0].primaryContentType[2].checked==true) {
		document.forms[0].File.value="";
		document.forms[0].targetType.value="NONE";
	}
	
	if (document.forms[0].File.value == "") {
		document.forms[0].targetType.value="NONE";
	}
}

/*
function setPrimaryContent() 
{
	if ( document.forms[0].targetType.value == "NONE" || document.forms[0].targetType.value == "" ) 
	{
    	document.forms[0].primaryContentType[2].checked=true;
	    switchToNone()
	} else if ( document.forms[0].targetType.value=="URL" ) {
	    document.forms[0].primaryContentType[1].checked=true;
	    document.forms[0].primaryURL.value=document.forms[0].File.value;
	    document.forms[0].rememberedURL.value=document.forms[0].File.value;
	    switchToURL();
	} else {
	    document.forms[0].primaryContentType[0].checked=true;
	    document.forms[0].PRIMARY.value=document.forms[0].File.value;
	    document.forms[0].rememberedFile.value=document.forms[0].File.value;
	    switchToFile();
	}
}

function getPath() 
{
	var value = document.forms[0].PRIMARY.value;
    return ((value!=null) ? value : "");
}

function setPath(newValue,fileSep) 
{
    document.forms[0].PRIMARY.value=CheckFilePathSeparators(newValue,fileSep);
    changedFile();
}
*/

