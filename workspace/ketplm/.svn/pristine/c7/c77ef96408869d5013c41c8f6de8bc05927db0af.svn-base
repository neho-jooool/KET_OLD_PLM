// XMLHttpRequest 오브젝트 생성 함수
// @return XMLHttpRequest 오브젝트
var cbMethod;//callback 메소드 객체
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


function doGetCallServer(url, callbackMethod, isSync) {
	cbMethod = callbackMethod;
	xmlHttp = createHttpRequest();
	if(!xmlHttp) {
		return;
	}

	if(isSync == null || isSync == "undefined") {
		isSync = true;
	}

	xmlHttp.open("GET", url, isSync);
	// Setup a function for the server to run when it's done	
	xmlHttp.onreadystatechange = callback;	
	// Send the request
	xmlHttp.send(null);
}


function doPostCallServer(url, param, callbackMethod, isSync) {
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
	//POST 방식인 경우 반드시 작성해줘야함.
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

/*
 * Returns a new XMLHttpRequest object, or false if this browser
 * doesn't support it
 */
/*
function newXMLHttpRequest() {
	var xmlHttp = false;
	if (window.XMLHttpRequest) {
		// Create XMLHttpRequest object in non-Microsoft browsers
		xmlHttp = new XMLHttpRequest();
	
	} else if (window.ActiveXObject) {
		// Create XMLHttpRequest via MS ActiveX
		try {
			// Try to create XMLHttpRequest in later versions
			// of Internet Explorer
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");

		} catch (e1) {
			// Failed to create required ActiveXObject
			try {
				// Try version supported by older versions
				// of Internet Explorer
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");

			} catch (e2) {
				// Unable to create an XMLHttpRequest with ActiveX
			}
		}
	}
	return xmlHttp;
}
*/
