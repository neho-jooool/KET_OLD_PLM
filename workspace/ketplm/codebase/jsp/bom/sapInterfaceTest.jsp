<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.BufferedReader,
                 java.io.File,
                 java.io.FileNotFoundException,
                 java.io.FileReader,
                 java.io.IOException,
                 e3ps.bom.service.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    // TEST NO : H500148-2, H500149, H500150-4
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script language='javascript'>

$(document).ready(function(){

	SuggestUtil.bind('PARTNO', 'partNumber');
	
    $("[name=PartIFSearchForm]").keypress(function(e) {
        if ( e.which == 13 ) {
        	exeMigration();
            return false;
        }
    });
	
});

//저장
function exeMigration(){

	var partNo = $('#partNumber').val();
	
	if(partNo == ""){
		alert("부품 번호를 입력해 주세요.");
		return;
	}
	
	var url = "/plm/ext/part/base/migPartFromErpForm.do?partNumber=" + partNo; // partNaming oid
	var name = "";
	var defaultWidth = 1150; // 1150;
    var defaultHeight = 800; // 800;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url, name, defaultWidth, defaultHeight, opts);
    //window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,top=20,left=50,width=800,height=400,center:yes");

}

<%--
--%>

</script>
</head>
<body class="body-space">
<form name="PartIFSearchForm" >
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02417") %><%--자재 I/F Test--%></td>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif">BOM<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02417") %><%--자재 I/F Test--%></td>
              </tr>
              </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <!-- START :: ERP 자재 I/F 테스트  -->
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
<%

%>
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td background="../../portal/images/btn_bg1.gif"><a href="javascript:exeMigration();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02068") %><%--실행--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
              </table>
              </td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02421") %><%--자재번호--%></td>
          <td width="290" class="tdwhiteL"><input type="text" id="partNumber" name="partNumber" class="txt_field"  style="width:270" id="textfield2"></td>
        </tr>

      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <!-- END ::  ERP 자재 I/F 테스트  -->
  </tr>
</table>
</form>
</body>
</html>
