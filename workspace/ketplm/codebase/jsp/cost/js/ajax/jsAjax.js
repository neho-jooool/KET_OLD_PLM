var cbMethod;//callback 
function createHttpRequest() {
	/* Create a new XMLHttpRequest object to talk to the Web server */
	var xmlHttp = false;
	try {
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlHttp = false;
		}
	}

	if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
		xmlHttp = new XMLHttpRequest();
	}

	if (!xmlHttp) {
		alert('Cannot create XMLHTTP instance');
		return false;
	}
	return xmlHttp;	
}

function callServer(url, callbackMethod) {
	cbMethod = callbackMethod;
	xmlHttp = createHttpRequest();
	if(!xmlHttp) {
		return;
	}

	xmlHttp.open("GET", url, false);
	// Setup a function for the server to run when it's done	
	xmlHttp.onreadystatechange = callback;	
	// Send the request
	xmlHttp.send(null);
}

function postCallServer(url, param, callbackMethod, isSync) {
	cbMethod = callbackMethod;
	xmlHttp = createHttpRequest();
	if(!xmlHttp) {
		return;
	}

	if(isSync == null || isSync == "undefined") {
		isSync = true;
	}

	// Setup a function for the server to run when it's done	
	xmlHttp.onreadystatechange = callback;	


	xmlHttp.open("POST", url, isSync);
	//POST
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	xmlHttp.setRequestHeader("Content-length", param.length);
	xmlHttp.setRequestHeader("Connection", "close");

	
	// Send the request
	xmlHttp.send(param);
}

function callback() {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			cbMethod(xmlHttp);
		} else if (xmlHttp.status == 404) {
			alert ("Requested URL is not found.");
		} else if (xmlHttp.status == 403) {
			alert("Access denied.");
		} else {
			alert("status is " + xmlHttp.status);
		}
	}
}
