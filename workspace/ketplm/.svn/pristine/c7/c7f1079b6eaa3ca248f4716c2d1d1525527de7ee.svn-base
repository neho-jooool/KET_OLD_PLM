<%@ include file="/jsp/common/InitMsgSvc.jsp"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ page import="e3ps.common.util.StringUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEco" class="e3ps.ecm.entity.KETProdChangeOrder" scope="request"/>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script type="text/javascript">
function lfn_onload() {
    try {
    
        lfn_feedbackBeforePopupViewPart();
        
    } catch(e) {}
}

function lfn_onunload() {
    try {
    
        lfn_feedbackAfterPopupViewPart();
        
    } catch(e) {}
}

//부품상세조회 팝업에서 그 창이 열릴때 호출되는 Function
function lfn_feedbackBeforePopupViewPart() {
  try {
       opener.showProcessing();
       opener.disabledAllBtn();
  } catch(e) {}
}

//부품상세조회 팝업에서 그 창이 닫힐때 호출되는 Function
function lfn_feedbackAfterPopupViewPart() {
 try {
     opener.hideProcessing();
     opener.enabledAllBtn();
 } catch(e) {}
}

// BOM Editor에서 저장시 로딩바를 생성한다.
function lfn_feedbackBeforeSaveBomEditor() {
  try {
       showProcessing();
       disabledAllBtn();
  } catch(e) {}
}
function lfn_feedbackAfterSaveBomEditor() {
  try {
	   hideProcessing();
	   enabledAllBtn();
  } catch(e) {}
}

$(document).ready(function() {
	$('#content').height($(window).height()-100)
    $(window).resize(function(){
    	$('#content').height($(window).height()-100)
    });
})

</script>
</head>
<body onload="javascript:lfn_onload();" onunload="javascript:lfn_onunload();">
<%
String oid = request.getParameter("oid");
String iframeSrc = "KETBomEditor.jsp?oid="+oid;
String title = "BOM 편집기";

%>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=title%></td>
                </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="space5"></td>
        </tr>
        <tr>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
      </table>
    </td>
</tr>   
<tr>
  <td class="space5"></td>
</tr>
<tr>
    <td>      
       <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td height="100%"><iframe id="content" name="content" src="<%=iframeSrc %>" style="width:100%; height:600px;border:0px;" frameborder="0"></iframe></td>
        </tr>
      </table>
    </td>
</tr>
</table>
</body>
</html>