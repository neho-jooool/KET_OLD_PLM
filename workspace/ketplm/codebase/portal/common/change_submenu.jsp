<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil,
          e3ps.common.util.StringUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=messageService.getString("e3ps.message.ket_message", "01852") %><%--설계변경(좌측)--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language="JavaScript">
<!--
var contObj = parent.document.getElementById("cont");

function create(oneselectname) {
  var createFileName = "Create"+oneselectname.toUpperCase()+".jsp";
  contObj.src="/plm/jsp/common/loading.jsp?url=/plm/jsp/change/"+createFileName;
}

function list(oneselectname) {
  var listServletName = "Manage"+oneselectname.toUpperCase()+"Servlet";
  contObj.src="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.change.servlet."+listServletName;
}

function search(oneselectname) {
  var searchFileName = "Search"+oneselectname.toUpperCase()+".jsp";
  contObj.src="/plm/jsp/common/loading.jsp?url=/plm/jsp/change/"+searchFileName;
}

function worklist(oneselectname) {
  var processName = oneselectname.toUpperCase()+"Process";
  contObj.src = "/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.WorklistServlet&key=processName&value="+processName+"&key=type&value=all";
}
function bomedit(src)
{
  openWindow(src,"bomedit",300,300);
}
-->
</script>
<style type="text/css">
<!--
body {
  background-image: url(../images/img_default/left/left_bg2.gif);
  background-repeat:no-repeat
  background-color: #ffffff;
  margin-top: 0px;
  margin-left:0px;
}
-->
</style>
</head>
<%
  String oneselectname = request.getParameter("name0");
  if(!StringUtil.checkString(oneselectname)) oneselectname = "ecr";

  boolean isAdmin = CommonUtil.isAdmin();

  boolean isECR_C = true;
  boolean isECO_C = true;

//  isECR_C = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("EC", "ECR", "C");
//  isECO_C = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("EC", "ECO", "C");

  boolean isECR_V = true;
  boolean isECO_V = true;
  boolean isECN_V = true;

//  isECR_V = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("EC", "ECR", "V");
//  isECO_V = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("EC", "ECO", "V");
//  isECN_V = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("EC", "ECN", "V");

  boolean isERP_A = true;
//  isERP_A = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("EC", "ERP", "A");

%>
<body>
<table width="170" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="../images/img_default/left/left_img_top2.gif" width="170" height="24"></td>
  </tr>
</table>
<table width="100%" height="33" border="0" cellpadding="0" cellspacing="0" bgcolor="#1BBBB4" style="margin:0px 0px 10px 0px">
  <tr>
    <td width="30" align="center"><img src="../images/img_default/icon/icon_06.gif" width="11" height="11"></td>
    <td class="font_06leftmenu">
    <%if(!oneselectname.equals("EO")) {%>
    <%=oneselectname.toUpperCase() %>
    <%}else {%>
      <%=messageService.getString("e3ps.message.ket_message", "01872") %><%--설변 현황--%>
    <%} %>
    </td>
    <td width="10"><!--img src="../images/img_default/left/side_01.gif" width="10" height="33"--></td>
  </tr>
</table>


<table width="170" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">
  <!-- ECR/ECO 작성 -->
  <% if(!"ECN".equals(oneselectname.toUpperCase())) { %>
  <tr>


      <%
        String regURL = "";
        if("ECR".equals(oneselectname.toUpperCase())) {
          regURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/ecr/CreateECR.jsp";
      %>
        <%if(isECR_C || isAdmin){ %>
        <td height="20" style="padding-left:10">
        <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
        <a href="<%=regURL%>" target="contName" class="leftmenu1"><%=oneselectname.toUpperCase() %> <%=oneselectname.toUpperCase() %> <%=messageService.getString("e3ps.message.ket_message", "02423") %><%--작성--%></a>
        </td>
        <%} %>
      <%


        }
        else if("ECO".equals(oneselectname.toUpperCase())) {
          regURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/eco/CreateECO.jsp";
      %>
        <%if(isECO_C || isAdmin){ %>
        <td height="20" style="padding-left:10">
        <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
        <a href="<%=regURL%>" target="contName" class="leftmenu1"><%=oneselectname.toUpperCase() %> <%=oneselectname.toUpperCase() %> <%=messageService.getString("e3ps.message.ket_message", "02423") %><%--작성--%></a>
        </td>
        <%} %>
      <%
        }
      %>

  </tr>
  <% } %>
  <!-- ECR/ECO/ECN 목록 -->
  <!--
  <tr>
    <td height="20" style="padding-left:10">
      <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
      <%
        String listURL = "";
        if("ECR".equals(oneselectname.toUpperCase())) {
          listURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/ecr/ListECR.jsp";
        }
        else if("ECO".equals(oneselectname.toUpperCase())) {
          listURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/eco/ListECO.jsp";
        }
        else{
          listURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/ecn/ListECN.jsp";
        }
      %>
      <a href="<%=listURL%>" target="contName" class="leftmenu1"><%=oneselectname.toUpperCase() %> 목록</a>
    </td>
  </tr>
   -->
  <!-- ECR/ECO/ECN 검색 -->
  <tr>

      <%
        String searchURL = "";
        if("ECR".equals(oneselectname.toUpperCase())) {
          searchURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/ecr/SearchECR.jsp";
      %>
        <%if(isECR_V || isAdmin){ %>
        <td height="20" style="padding-left:10">
        <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
        <a href="<%=searchURL%>" target="contName" class="leftmenu1"><%=oneselectname.toUpperCase() %> <%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
        </td>
        <%} %>
      <%
        }
        else if("ECO".equals(oneselectname.toUpperCase())) {
          searchURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/eco/SearchECO.jsp";
          %>
          <%if(isECO_V || isAdmin){ %>
          <td height="20" style="padding-left:10">
          <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
          <a href="<%=searchURL%>" target="contName" class="leftmenu1"><%=oneselectname.toUpperCase() %> <%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
          </td>
          <%} %>
          <%
        }
        else if("ECN".equals(oneselectname.toUpperCase())) {
          searchURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/ecn/SearchECN.jsp";
          %>
          <%if(isECN_V || isAdmin){ %>
          <td height="20" style="padding-left:10">
          <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
          <a href="<%=searchURL%>" target="contName" class="leftmenu1"><%=oneselectname.toUpperCase() %> <%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
          </td>
          <%} %>
          <%

        }else{
          searchURL = "/plm/jsp/common/loading.jsp?url=/plm/jsp/change/progress/SearchProgress.jsp";
        %>
          <td height="20" style="padding-left:10">
          <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
          <a href="<%=searchURL%>" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01872") %><%--설변 현황--%> </a>
          </td>
          <%
        }
      %>
    </td>
  </tr>
  <%if( !"EO".equals(oneselectname.toUpperCase())){ %>
  <%  if(isAdmin) {%>
      <tr>
        <td height="20" style="padding-left:10">
          <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
          <%
            searchURL = "/plm/jsp/part/editor/BomEditorCnt.jsp";
          %>
          <a href="#"  class="leftmenu1" onclick="bomedit('<%=searchURL %>')">BOM Edit</a>

        </td>
      </tr>
  <%  } %>
  <!-- Begin ERP 전송이력  *********************************************************  -->
  <%if(isERP_A || isAdmin) { %>
  <tr>
    <td height="20" style="padding-left:10">
      <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
      <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/part/SearchSendHistory.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00221") %><%--ERP 전송목록--%></a>
    </td>
  </tr>
  <%} %>
  <%}%>
  <!-- End ERP 전송이력  *********************************************************  -->

  <!-- ECR/ECO/ECN 업무목록 -->
  <!--
  <tr>
    <td height="20" style="padding-left:10">
      <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
      <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/change/process/Worklist.jsp&key=type&value=<%=oneselectname.toUpperCase()%>" target="contName" class="leftmenu1"><%=oneselectname.toUpperCase() %> 업무목록</a>
    </td>
  </tr>
   -->
</table>
<table width="170" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:10px">
  <tr>
    <td><img src="../images/img_default/left/left_bg_line.gif" width="169" height="3"></td>
  </tr>
</table>
</body>
</html>
