var limitFileSize = 30;
function isFilePrimary()
{
	if(ocument.forms[0].PRIMARY.value.length == 0)
	{
		alert("%uC8FC%20%uCCA8%uBD80%uD30C%uC77C%uC774%20%uC5C6%uC2B5%uB2C8%uB2E4"); // It's not exist a primary file
		return false;
	}
	else
		return true;
}
function isValidPrimarySize(obj)
{
	var size = getFileSize(obj.value);
	if( limitFileSize*1024*1024 < size )
	{
		alert(unescape('%uD30C%uC77C%uC6A9%uB7C9%uC774%20'+limitFileSize+'MB%20%uCD08%uACFC%uD558%uC600%uC2B5%uB2C8%uB2E4')); 
		obj.select(); 
	    document.selection.clear();
		return false;
	}
    return true;
}

function isValidSecondarySize(obj)
{
	var limit = limitFileSize*1024*1024;
	var secondaryFileSize=0;
	
	var inputTag = document.getElementsByTagName('input');
	for(var x=inputTag.length-1 ; x>-1 ; x--)
	{
		
		if('file' == inputTag[x].type)
		{
			if( inputTag[x].name.search('filePath') == 0 )
			{
				if( inputTag[x].value=="") continue;
				secondaryFileSize = secondaryFileSize + parseInt(getFileSize(inputTag[x].value));
			}
		}
	}

	if(limit < secondaryFileSize)
	{
		alert(unescape('%uD30C%uC77C%20%uCD1D%uC6A9%uB7C9%uC774%20'+limitFileSize+'MB%uB97C%20%uCD08%uACFC%uD558%uC600%uC2B5%uB2C8%uB2E4')); 
		obj.select(); 
	    document.selection.clear();
		return false;
	}	
    return true;
}

function getFileSize(path)
{
	var img = new Image();
	//img.dynsrc = path;
	return img.fileSize;
}

function insertSecondaryFile() 
{
	var index = fileTable.rows.length;
	if(index > 10) return;

	if(index >= 2) {
		if(fileTableRow.style != null)
			fileTableRow.style.display = 'block';
		else
			fileTableRow[0].style.display = 'block';
	}
	trObj = fileTable.insertRow(index);
	if(trObj.replaceNode){
		trObj.replaceNode(fileTable.rows[1].cloneNode(true));	
	}else{
		trObj.parentNode.replaceChild(fileTable.rows[1].cloneNode(true),trObj);	
	}
	
	

	var tempFile = fileTable.rows[index].cells[1].children[0];
	tempFile.name = tempFile.name+index;

	fileTableRow[0].style.display = 'none';
}

function deleteSecondaryFile() {
	index = document.forms[0].fileDelete.length-1;
	
	for(i=index; i>=1; i--) {
		if(document.forms[0].fileDelete[i].checked == true) fileTable.deleteRow(i+1);
	}
}

function isFileSecondary()
{
	if(document.forms[0].filePath[1] != null)
	{
		if(document.forms[0].filePath[1].value.length == 0)
		{
			// It's not exist a secondary file
			alert(unescape("%uBD80%20%uCCA8%uBD80%uD30C%uC77C%uC774%20%uC5C6%uC2B5%uB2C8%uB2E4"));
			return false;
		}
		else
			return true;
	}
	else
	{
		// It's not exist a secondary file
		alert(unescape("%uBD80%20%uCCA8%uBD80%uD30C%uC77C%uC774%20%uC5C6%uC2B5%uB2C8%uB2E4"));
		return false;
	}
}
