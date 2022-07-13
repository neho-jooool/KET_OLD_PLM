<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil,
        e3ps.common.util.StringUtil,
        e3ps.groupware.company.Department,
        wt.folder.Folder,
        wt.query.QuerySpec,
        wt.fc.QueryResult,
        wt.fc.PersistenceHelper"%>
        
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
  String oid = request.getParameter("oid");
  Kogger.debug("ViewProjectType", null, oid, "ViewProjectType.jsp::oid = "+oid);
  ProjectOutPutType prjType = null;
  String tempName = null;
  String tempCode = null;
  String tempPath = null;
  String tempSort = null;
  String tempDesc = null;
  String tempFolder = "";
  String docCheck = null;
  String docCheckValue  = null;
  StringTokenizer tempAttr = null;
  String isDisabled = "";
  Hashtable attrHash = new Hashtable();
  boolean isModify = false;
  boolean isRoot = false;
  QueryResult qr = null;
  if(oid!=null)
  {
    prjType = (ProjectOutPutType)CommonUtil.getObject(oid);
    docCheck = new String(prjType.getPath());
    Kogger.debug(docCheck);

    //if(docCheck.equals("ROOT") ){
    //docCheckValue ="";
    //}else{
    //docCheckValue  = docCheck.substring(0,8);
    //}

    if(prjType.getName().equals("ROOT")) isRoot = true;
    tempName = prjType.getName();
    tempCode = prjType.getCode();
    tempPath = prjType.getPath();




  }


%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.Hashtable"%>


<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt" %>


<%@page import="e3ps.common.jdf.log.Logger"%>



<%@page import="e3ps.project.outputtype.ProjectOutPutType;"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01727") %><%--산출물 인증 타입 관리(보기)--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
<!--
function createType(oid)
{
  document.location.href="/plm/jsp/project/projectType/CreateProjectType.jsp?oid="+oid;
}

function modType(oid)
{
//alert(oid);
  document.location.href="/plm/jsp/project/projectType/UpdateProjectType.jsp?oid="+oid;
}

function delType(oid)
{
//alert(document.forms[0].oid.value);
  document.forms[0].cmd.value="delete";
  document.forms[0].action="/plm/servlet/e3ps/ProjectOutPutTypeServlet?oid="+oid;
  document.forms[0].submit();
}
//parent.tree.location.reload();
//-->
</script>
</head>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form method="post">
<input type="hidden" name="cmd">
<input type=hidden name=oid value="<%=oid%>">
<div class=div_title><script>printTitle('<%=messageService.getString("e3ps.message.ket_message", "01725") %><%--산출물 인증 타입 관리--%> <b>[<%=messageService.getString("e3ps.message.ket_message", "01527") %><%--보기--%>]</b>')</script></div>
<div class=div_title_action>
  <%
  if(isRoot||oid==null)
  {
    if(isRoot)
    {
  %>
            <input type="button" id="button2" value='<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>' onclick="javascript:createType('<%=oid%>')">
  <%
    }
    else
    {
    %>
        <input type="button" id="button2" value='<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>' disabled>
  <%} %>
    <input type="button" id="button2" value="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" disabled>
        <input type="button" id="button2" value="<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>" disabled>
  <%

  }else{ %>
        <input type="button" id="button2" value='<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>' onclick="javascript:createType('<%=oid%>')">
    <input type="button" id="button2" value="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" onclick="javascript:modType('<%=oid%>')">
        <input type="button" id="button2" value="<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>" onclick="javascript:delType('<%=oid%>')">
  <%} %>

</div>
<div class=div_body_title><%=messageService.getString("e3ps.message.ket_message", "01436") %><%--문서타입 관리[보기]--%></div>
<div class=div_body_row>
    <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></div>
  <div class=div_row_long><%=StringUtil.checkNull(tempName) %></div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></div>
  <div class=div_row_long><%=StringUtil.checkNull(tempCode) %></div>
</div>
<div class=div_body_row>
  <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "00795") %><%--경로--%></div>
  <div class=div_row_long><%=StringUtil.checkNull(tempPath) %></div>
</div>












</form>
</body>
</html>
