<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>

function openDoc(){
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;
    var rest = "width=" + (screen.availWidth * 0.9) + ",height="+ (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='+ toppos;
    var url = '${url}';
    window.open(url, '', (opts + rest));
    

    //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
    window.opener='nothing';
    window.open('','_parent','');
    window.close(); 
}



$(document).ready(function(){
    
	$("#KQISREDIRECT").load("/plm/ext/project/purchase/kqisRedirect.do?sabun=${sabun}", function(e){
		alert('이 메세지는 KQIS 자동로그인을 위해 1회만 발생합니다.');
		for(var i=0; i<300000; i++){
			
		}
		openDoc();
    });
	
	
    
});
</script>
<body>
<div id="KQISREDIRECT"></div>

</body>
</html>