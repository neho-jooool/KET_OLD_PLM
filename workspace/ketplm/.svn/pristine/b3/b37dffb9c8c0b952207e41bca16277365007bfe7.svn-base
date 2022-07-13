<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<head>
<title>loading...</title>
<%@include file="/jsp/common/context.jsp"%>
<!-- MultiCombo Start -->
<%@include file="/jsp/common/multicombo.jsp" %>
<!-- MultiCombo End -->
<%
  String img = request.getParameter("img");
  String url = request.getParameter("url");
  String[] keys = request.getParameterValues("key");
  String[] values = request.getParameterValues("value");

  if(img == null) img = "/plm/portal/icon/loading.gif";

  String parameter = "";
  if(keys != null)
  {
    for (int i = 0 ; i<keys.length ; i++ ){
	  if(i == 0){
	      parameter += (keys[i] + "=" + values[i] );
	  }else{
	      parameter += ( "&" + keys[i] + "=" + values[i] );
	  }
    }
  }

  /*
   * [PLM System 1차개선] 수정내용 : target url 형식 오류 수정 , 2013. 07. 04, 김무준
   */
  String targetUrl = url;
  if (parameter.length() > 0) {
    if (targetUrl.indexOf("?") != -1) {
      targetUrl = targetUrl + "&" + parameter;
    }
    else {
      targetUrl = targetUrl + "?" + parameter;
    }
  }
%>
<script>
/********************************************************************************
Copyright (C) 1999 Thomas Brattli
This script is made by and copyrighted to Thomas Brattli at www.bratta.com
Visit for more great scripts.
This may be used freely as long as this msg is intact!
********************************************************************************/
 
//브라우저를 체크한다.
  n=document.layers
  ie=document.all
//로드되기전 페이지를 숨긴다.
  function hideIt(){
        /* if(ie || n){
                if(n) document.divLoadCont.visibility="hidden"
                else divLoadCont.style.visibility="hidden"
        } */
        hideProcessing();
  }
</script>
<body onLoad="setTimeout('hideIt()',1000)">
<script src="/plm/portal/js/common.js"></script>
<%@include file="/jsp/common/processing.html" %>
<SCRIPT LANGUAGE="JavaScript">
<!--
showProcessing(); //Progressbar 보이기
location.href = "<%=targetUrl%>";
//-->
</SCRIPT>
</body>
</html>