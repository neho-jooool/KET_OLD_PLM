<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.code.*,
                 e3ps.common.jdf.config.*,
                 e3ps.common.util.WCUtil"%>
<%@page import="wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.PhaseTemplate,
                java.util.Vector,
                java.util.Hashtable"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    Vector tMap = null;
    Vector tMap2 = null;
%>

<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=messageService.getString("e3ps.message.ket_message", "00349") %><%--Part No 체계--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language="JavaScript" src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/plm/portal/js/org.js"></SCRIPT>
<script language="javaScript">


</script>
</head>
<body>
<form name="form">

<div id="productDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
          <table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00349") %><%--Part No 체계--%></td>
                <td width="10"></td>
              </tr>
          </table>
          </td>
          <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
  </td>
</tr>
<tr>
<td class="space10"></td>
</tr>
</table>
</br>
</br>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
<tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
<tr>
  <td  class="tab_btm2"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <%
      tMap = NumberCodeHelper.manager.getNumberCodeLevel("NEWPRODUCTTYPE", "top");

    for(int i = 0; i < tMap.size(); i++) {
        NumberCode tNum = (NumberCode)tMap.get(i);
    %>
    <td class="tdblueM0">
        <%=i+1%>. <%=tNum.getName()%>
    </td>
    <%
    }
    %>
    </tr>
    <tr>
    <%
    for(int i = 0; i < tMap.size(); i++) {
        NumberCode tNum = (NumberCode)tMap.get(i);
        tMap2 = NumberCodeHelper.manager.getNumberCodeLevelType("NEWPRODUCTTYPE", tNum.getName());
    %>
    <td class="tdwhiteL" style="vertical-align:text-top">
        </br>
    <%
        for(int ii = 0; ii < tMap2.size(); ii++) {
            NumberCode tNum2 = (NumberCode)tMap2.get(ii);
            %>
            <%=tNum2.getCode()%>:<%=tNum2.getName()%>
            </br>
    <%
        }
        %>
        </td>

        <%
    }
    %>

  </tr>
</table>
</div>
</br>
</br>
<div id="dieDiv">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
<tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
<tr>
  <td  class="tab_btm2"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
      <%
      tMap = NumberCodeHelper.manager.getNumberCodeLevel("NEWDIETYPE", "top");

    for(int i = 0; i < tMap.size(); i++) {
        NumberCode tNum = (NumberCode)tMap.get(i);
    %>
    <td class="tdblueM0">
        <%=i+1%>. <%=tNum.getName()%>
    </td>
    <%
    }
    %>
    </tr>
    <tr>
    <%
    for(int i = 0; i < tMap.size(); i++) {
        NumberCode tNum = (NumberCode)tMap.get(i);
        tMap2 = NumberCodeHelper.manager.getNumberCodeLevelType("NEWDIETYPE", tNum.getName());
    %>
    <td class="tdwhiteL" style="vertical-align:text-top">
        </br>
    <%
        for(int ii = 0; ii < tMap2.size(); ii++) {
            NumberCode tNum2 = (NumberCode)tMap2.get(ii);
            %>
            <%=tNum2.getCode()%>:<%=tNum2.getName()%>
            </br>
    <%
        }
        %>
        </td>

        <%
    }
    %>

  </tr>
</table>
</div>
</form>
</body>
</html>
