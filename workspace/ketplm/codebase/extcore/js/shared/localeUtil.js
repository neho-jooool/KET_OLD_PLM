/**
 * Javascript 다국어 util script
 */
var LocaleUtil = {
	message : {},
	isLoaded : false,
	getMessage : function(messageCode) {
		try{
			if(!isLoaded && top.LocaleUtil){
				if(arguments.length == 2){
					return top.LocaleUtil.getMessage(messageCode, arguments[1]);					
				}else if(arguments.length == 3){
					return top.LocaleUtil.getMessage(messageCode, arguments[1], arguments[2]);
				}else if(arguments.length == 4){
					return top.LocaleUtil.getMessage(messageCode, arguments[1], arguments[2], arguments[3]);
				}else{
					return top.LocaleUtil.getMessage(messageCode);					
				}
			}
		}catch(e){}
		
		var isArr = new Array();
		defaultMessage = "";
		// 매개변수의 개수에 따라 역할을 지정한다.
		if (arguments.length > 1) {
			defaultMessage = arguments[2];
			for(var i=1;i<arguments.length;i++){
				isArr[isArr.length] = arguments[i];				
			}
		} else {
			isArr = new Array();
		}

		var myExp;
		var result, temp;
		var haveMessage = null;
		try {
			haveMessage = this.message[messageCode]; // 메세지 번들을 받아온다.
			if (haveMessage != null) {
				result = haveMessage;
			} else {
				result = defaultMessage;
			}
		} catch (e) {
			result = defaultMessage;
		}

		// 메세지를 치환한다.
		for ( var i = 0; i < isArr.length; i++) {
			myExp = new RegExp("\\{" + i + "\\}", "gi");
			result = result.replace(myExp, isArr[i]);
		}

		return result;
	}
};
var xmlUrl;
$.ajaxSetup({cache: true});
if(locale == 'zh_CN') xmlUrl = '/plm/e3ps/message/ket_message_zh.xml';
else if(locale == 'en') xmlUrl = '/plm/e3ps/message/ket_message.xml';
else if(locale == 'ja') xmlUrl = '/plm/e3ps/message/ket_message_ja.xml';
else xmlUrl ='/plm/e3ps/message/ket_message_ko.xml';
$.ajax({
	type : 'GET',
	url : xmlUrl,
	dataType : 'xml',
	async : false,
	success : function(xmlDoc){
		if (xmlDoc != null) {
			var entry = $(xmlDoc).find("entry");
			$(entry).each(function(){
				var key = $(this)[0].getAttributeNode('key').value;
				var value = $(this).text();
				LocaleUtil.message[key]=value;			
			});
			LocaleUtil.isLoaded = true;
		}			
	}
});
