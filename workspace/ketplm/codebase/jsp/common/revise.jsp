<%--
 /**
 * @(#)  revise.jsp
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @author Seung-hwan Choi, skyprda@e3ps.com
 * @desc ¼­º?¸´ ?³¸?½? ½?°??? ¼?¿???´? ??¾÷?º Loading ¸Þ¼¼??¸? ¶?¿?´?.
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="e3ps.common.obj.ObjectUtil,
          e3ps.common.util.CommonUtil" %>
<%@ page import="wt.enterprise.RevisionControlled" %>

<%@include file="/jsp/common/context.jsp"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<%
e3ps.common.jdf.message.Message message = e3ps.common.jdf.message.MessageBox.getInstance("message");

  String oid = request.getParameter("oid");
  String location = request.getParameter("loc");
  RevisionControlled rc = (RevisionControlled)CommonUtil.getObject(oid);
  if(!ObjectUtil.isLatestVersion(rc))
  {
%>
    <SCRIPT>
      alert('<%=messageService.getString("e3ps.message.ket_message", "02840") %><%--최신 버전이 아닙니다--%>');
      self.close();
    </SCRIPT>
<%  return;
  }

  String action = "/plm/servlet/e3ps.common.obj.CheckInOutServlet?cmd=revise&oid=";
  if(rc instanceof e3ps.doc.E3PSDocument)
      action = "/plm/servlet/e3ps.doc.servlet.DocumentReviseServlet?cmd=revise&oid=";
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSubmit(oid)
{
 var url = "/plm/servlet/e3ps.groupware.workprocess.servlet.SearchMyFolderServlet";
 if(!confirm("<%=message.getMessage("confirm.revise")%>")) return;

 showProcessing();
 disabledAllBtn();
 document.forms[0].action = '<%=action%>'+oid+'&loc=<%=location%>';
 window.close();
 document.forms[0].submit();
 opener.opener.parent.frames.location.href= url;
 opener.parent.close();
}
//-->
</SCRIPT>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form method=post>
<table border="0" cellspacing="0" cellpadding="0" class="tab_popup01">
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="popBox">
        <tr>
          <td class="boxTLeft"></td>
          <td class="boxTCenter"></td>
          <td class="boxTRight"></td>
        </tr>
        <tr>
          <td class="boxLeft"></td>
          <td>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></td>
                <td width="50">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class=fixLeft></td>
                      <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%>" onClick="JavaScript:doSubmit('<%=oid%>')"></td>
                      <td class=fixRight></td>
                    </tr>
                  </table>
                </td>
                <td width="50">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class=fixLeft></td>
                      <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="closeWindow()"></td>
                      <td class=fixRight></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td  class="tab_btm1"></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tdblueL0"><textarea name="note" cols="35" rows="5" class="fm_area"></textarea></td>
            </tr>
            </table>
          </td>
          <td class="boxRight"></td>
        </tr>
        <tr>
          <td class="boxBLeft"></td>
          <td valign="bottom" class="boxBCenter"></td>
          <td class="boxBRight"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
<%--
<body>
<%@include file="/jsp/common/processing.html"%>
<form method=post>
<table width="95%" height="" align=center border=0>
  <tr>
    <td width="50%" align="left"><SCRIPT>printTitle('개정')</SCRIPT></td>
    <td align=right>
      <input type="button" value="개정" onClick="JavaScript:doSubmit('<%=oid%>')" id=button2>
      <input type="button" value="취소" onClick="closeWindow()" id=button2>
    </td>
  </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
  <tr  bgcolor="#D8E0E7" align=center height=23>
      <td bgcolor="#D8E0E7" id=title width=100%>개정사유</td>
  </tr>
  <TR>
    <td bgcolor=EBEFF3><TEXTAREA name=note id=input rows=5></TEXTAREA></TD>
  </TR>
</table>
</form>
</body>
--%>
</html>
