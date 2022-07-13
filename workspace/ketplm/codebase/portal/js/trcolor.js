var selectObj;

// change the color of <TR> html-tag 
function changeTRColor(tr_id, _color)
{
	if( _color == null) _color='#f5f5f5';
	if(tr_id!=null)
	{
		var obj = document.getElementById(tr_id);
		if(selectObj!=null) 	
			selectObj.style.backgroundColor='#ffffff'

		selectObj = obj;
		selectObj.style.backgroundColor=_color;
	}
}

function onOver(obj)
{
	if(selectObj != null && obj != selectObj)
		obj.style.backgroundColor='#f5f5f5';
}
function onOut(obj)
{
	if(selectObj != null && obj != selectObj)
		obj.style.backgroundColor='#ffffff'
}


function trcolor(url, tr_id, tName, color)
{
	iframe.document.location=url;
	if( color == null) color='#f5f5f5';
	
	var obj = document.getElementById(tr_id);
	if(obj != null)
	{
		setPosition(tName);

		if(selectObj != null){
			selectObj.style.backgroundColor='#ffffff'
		}
		selectObj = obj;
		selectObj.style.backgroundColor = color;
	}
}



function or_trcolor(url,tr_id,tName){
iframe.document.location=url;
	if(tr_id!=null){
		var obj = document.getElementById(tr_id);
		setPosition(tName);

		if(selectObj!=null){
			selectObj.style.backgroundColor='#ffffff'
		}
		selectObj = obj;
		selectObj.style.backgroundColor='#FFD99A';
	}
}


function bg_trcolor(url,tr_id,tName){
iframe.document.location=url;
	if(tr_id!=null){
		var obj = document.getElementById(tr_id);
		setPosition(tName);

		if(selectObj!=null){
			selectObj.style.backgroundColor='#ffffff'
		}
		selectObj = obj;
		selectObj.style.backgroundColor='#E4F7F2';
	}
}


function pr_trcolor(url,tr_id,tName){
iframe.document.location=url;
	if(tr_id!=null){
		var obj = document.getElementById(tr_id);
		setPosition(tName);

		if(selectObj!=null){
			selectObj.style.backgroundColor='#ffffff'
		}
		selectObj = obj;
		selectObj.style.backgroundColor='#EAE1EE';
	}
}


function br_trcolor(url,tr_id,tName){
iframe.document.location=url;
	if(tr_id!=null){
		var obj = document.getElementById(tr_id);
		setPosition(tName);

		if(selectObj!=null){
			selectObj.style.backgroundColor='#ffffff'
		}
		selectObj = obj;
		selectObj.style.backgroundColor='#F0ECD1';
	}
}

function gr_trcolor(url,tr_id,tName){
iframe.document.location=url;
	if(tr_id!=null){
		var obj = document.getElementById(tr_id);
		setPosition(tName);

		if(selectObj!=null){
			selectObj.style.backgroundColor='#ffffff'
		}
		selectObj = obj;
		selectObj.style.backgroundColor='#E2EDB5';
	}
}

//????
function pk_trcolor(url,tr_id,tName){
iframe.document.location=url;
	if(tr_id!=null){
		var obj = document.getElementById(tr_id);
		setPosition(tName);

		if(selectObj!=null){
			selectObj.style.backgroundColor='#ffffff'
		}
		selectObj = obj;
		selectObj.style.backgroundColor='#FBE7ED';
	}
}
