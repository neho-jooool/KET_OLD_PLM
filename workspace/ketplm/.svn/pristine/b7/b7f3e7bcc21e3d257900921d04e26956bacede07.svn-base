Number.prototype.commaFormat = function(fixed){
 
    var reg = /(^[+-]?\d+)(\d{3})/;
    
    if(fixed == null) fixed = 0;
    var n = this.toFixed(fixed);
    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
 
    return n;
};

String.prototype.commaFormat = function(fixed){
	
    var num = parseFloat(this);
    if( isNaN(num) ) return "0";
 
    return num.commaFormat(fixed);
};

String.prototype.commaRemove = function(){
	
	var n = this;
	
	if ( typeof n == "undefined" || n == null || n == "" ) {
        return "";
    }
	
    var txtNumber = '' + n;
    return txtNumber.replace(/(,)/g, "");
};

/**
 * 
 */
Array.prototype.contains = function(element) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == element) {
			return true;
		}
	}
	return false;
}

Array.prototype.remove = function() {
    var what, a = arguments, L = a.length, ax;
    while (L && this.length) {
        what = a[--L];
        while ((ax = this.indexOf(what)) !== -1) {
            this.splice(ax, 1);
        }
    }
    return this;
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

function sleep(milliseconds) {
	var start = new Date().getTime();
	for (var i = 0; i < 1e7; i++) {
		if ((new Date().getTime() - start) > milliseconds){
			break;
		}
	}
}

window.ajaxCallServer = function(requestURL, param, cbMethod, isProgress){
	
	var retVal = new Object();
	var isSync = false;
	if(cbMethod != null) isSync = true;
	if(isProgress == null) isProgress = true;
	if(isProgress){
		showProcessing();
	}
	
	var ajaxParam = {
		type : "POST",
		url : requestURL,
		data : param,
		async : isSync,
		success : function(res){
			
			if(res.message){
				alert(res.message);
			}
			var result = eval(res.result);
			
			if(result){
				if(cbMethod != null ) {
					cbMethod(res)
				}else{
					retVal = res;
				}
			}
			if(isProgress){
				hideProcessing();
			}
		},
		error : function(res, stat, error){
			window.console.log("ERROR PARAMETER : ", res);
			hideProcessing();
			alert("CODE : " + stat + "\n" + "MESSAGE : " + res.responseText + "\n" + "ERROR : " + error);
		}
	}
	
	if(param instanceof FormData){
		ajaxParam.processData = false;
		ajaxParam.contentType = false;
	}else if(param instanceof Object){
		ajaxParam.contentType = "application/json";
		ajaxParam.data = JSON.stringify(param);
		ajaxParam.dataType = "json";
	}
	
	$.ajax(ajaxParam);
	
	return retVal;
}

window.generateGridColumn = function(columnList){
	
	var colunmProp = new Object();
	var cols = new Array();
	var leftCols = new Array();
	var kHeader1Lv = new Object();
	var kHeader2Lv = new Object();
	var kHeader3Lv = new Object();
	var kHeader4Lv = new Object();

	kHeader1Lv["Kind"] = "Header";
	kHeader1Lv["id"] = "Header1";
	kHeader1Lv["Spanned"] = 1;
	kHeader2Lv["Kind"] = "Header";
	kHeader2Lv["id"] = "Header2";
	kHeader2Lv["Spanned"] = 1;
	kHeader3Lv["Kind"] = "Header";
	kHeader3Lv["id"] = "Header3";
	kHeader3Lv["Spanned"] = 1;
	kHeader4Lv["Kind"] = "Header";
	kHeader4Lv["id"] = "Header4";
	kHeader4Lv["Spanned"] = 1;
	
	for(var i = 0; i < columnList.length; i++){
		
		var column = columnList[i];
		
		var level = column["LEVEL"];
		var key = column["KEY"];
		var label = column["LABEL"];
		
		var col = new Object();
		col["Name"] = key;
		
		var columnKeys = Object.keys(column);
		
		for(var j = 0; j < columnKeys.length; j++){
			var optionKey = columnKeys[j];
			if("LABEL" == optionKey || "KEY" == optionKey || "LEVEL" == optionKey || "COLSPAN" == optionKey) continue;
			col[optionKey] = column[optionKey];
		}
		
		switch(level) {
			case 1 :
				kHeader1Lv[key] = label;
				kHeader1Lv[key + "Type"] = "Html";
				if(column["COLSPAN"] != null) {
					var colspan = column["COLSPAN"];
					kHeader1Lv[key + "Span"] = colspan;
					col["Visible"] = 0;
					col["CanHide"] = 0;
					col["CanExport"] = 0;
				}else{
					kHeader1Lv[key + "RowSpan"] = 4;
					col["Spanned"] = 1;
					kHeader2Lv[key] = label;
					kHeader3Lv[key] = label;
					kHeader4Lv[key] = label;
				}
				kHeader1Lv[key + "Align"] = "Scroll";
				
				break;
			case 2 :
				kHeader2Lv[key] = label;
				kHeader2Lv[key + "Type"] = "Html";
				if(column["COLSPAN"] != null) {
					var colspan = column["COLSPAN"];
					kHeader2Lv[key + "Span"] = colspan;
					col["Visible"] = 0;
					col["CanHide"] = 0;
					col["CanExport"] = 0;
				}else{
					kHeader2Lv[key + "RowSpan"] = 3;
					col["Spanned"] = 1;
					kHeader3Lv[key] = label;
					kHeader4Lv[key] = label;
				}
				kHeader2Lv[key + "Align"] = "Scroll";
				
				break;
			case 3 :
				kHeader3Lv[key] = label;
				kHeader3Lv[key + "Type"] = "Html";
				if(column["COLSPAN"] != null) {
					var colspan = column["COLSPAN"];
					kHeader3Lv[key + "Span"] = colspan;
					col["Visible"] = 0;
					col["CanHide"] = 0;
					col["CanExport"] = 0;
				}else{
					kHeader3Lv[key + "RowSpan"] = 2;
					col["Spanned"] = 1;
					kHeader4Lv[key] = label;
				}
				kHeader3Lv[key + "Align"] = "Scroll";
				
				break;
			default :
				kHeader4Lv[key] = label;
				kHeader4Lv[key + "Type"] = "Html";
				kHeader4Lv[key + "Align"] = "Scroll";
				break;
		}
		
		if(col.Visible == 0) col["CanExport"] = 0;
		
		if(column["LEFTCOLS"]){
			leftCols.push(col);
		}else{
			cols.push(col);
		}
	}
	
	var colunmProp = new Object();
	colunmProp["cols"] = cols;
	colunmProp["leftCols"] = leftCols;
	colunmProp["kHeader1Lv"] = kHeader1Lv;
	colunmProp["kHeader2Lv"] = kHeader2Lv;
	colunmProp["kHeader3Lv"] = kHeader3Lv;
	colunmProp["kHeader4Lv"] = kHeader4Lv;
	
	return colunmProp;
}
window.calculateFormulaValue = function(str, fixed){
	var text = str;
	text = eval(text).toFloat(fixed).toString();
	
	if(isNaN(text) || !isFinite(text)){
		text = 0;
    }
	
	return text;
}

window.calculateValue = function(value1, value2, calc, fixed){
	
	if(value1 == "") value1 = 0;
	if(value2 == "") value2 = 0;

	value1 = parseFloat(value1).toFloat(fixed);
	value2 = parseFloat(value2).toFloat(fixed);
	
	var text = "(" + value1 + ")" + calc + "(" + value2 + ")";
	
	return eval(text).toFloat(fixed).toString();
}

window.checkNull = function(str){
	
	var type = typeof str;
	if('number' == type) str = str.toString();
	if('boolean' == type) str = str.toString();
	
	if (str == null || "undefined" == str.trim() || "null" == str.trim()) return "";
	
	return str;
}

String.prototype.toFloat = function(fixed){
	
	var result = this;
	result = parseFloat(result);
	
	if(!isNaN(result)){
		result = result.toFloat(fixed);
		return result;
	}
	
	return "0";
}

Number.prototype.toFloat = function(fixed){
	
	if(fixed == null) fixed = 4;
	
	var result = this + "";
	
	if(result.indexOf(".") != -1){

		result = parseFloat(result).toFixed(fixed);

		result = parseFloat(result.replace(/(0+$)/, ""));
	}
	
	return result;
}

if(window.Grids != null){
	Grids.OnDataSend = function(grid, source, data, func){
	    showProcessing();
	}
	Grids.OnDataReceive = function(grid, source){
	    hideProcessing();
	}
	Grids.OnDataError = function(grid, source, result, message, data){
	    hideProcessing();
	    alert(message);
	}
}