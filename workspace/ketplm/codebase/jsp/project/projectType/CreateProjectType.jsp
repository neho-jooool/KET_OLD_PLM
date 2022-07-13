<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.outputtype.ProjectOutPutType"%>
<%@include file="/jsp/common/context.jsp"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    String parentCode = "";
    if("ROOT".equals(oid))
    {
        parentCode = "ROOT";
    }
    else
    {
        ProjectOutPutType docType = (ProjectOutPutType)CommonUtil.getObject(oid);
        parentCode = docType.getName();
    }
    //properties의 code & 이름
    //e3ps.common.jdf.config.Config conf = ConfigEx.getInstance("document");
%>






<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01726") %><%--산출물 인증 타입 관리(등록)--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
<!--
function createType() {
    if (!check()) return;
    else {
        document.forms[0].action = '/plm/servlet/e3ps/ProjectOutPutTypeServlet';
        document.forms[0].submit();
    }
}

function check() {
    var doc = document.forms[0];
    if( isNullData(doc.oid.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01756") %><%--상위 노드를 선택해주세요--%>');
        return false;
    }
    if ( isNullData(doc.NAME.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02515") %><%--정보를 입력하십시오--%>');
        return false;
    }
    return true;
}

function selectFolder(target)
{
    var url = "/plm/jsp/groupware/folder/SelectFolder.jsp?folderpath=/Default/Document&target="+target;
    openWindow(url, "SelectFolder", 300, 300);
}

function cancel(oid)
{
    document.location.href="/plm/jsp/doc/doctype/ViewDocType.jsp?oid="+oid;
}
//parent.tree.location.reload();
//-->
</script>
</head>

<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form method="post">
<input type="hidden" name="cmd" value="create">

<div class=div_title><script>printTitle('<%=messageService.getString("e3ps.message.ket_message", "01724") %><%--산출물 인증 타입--%> <b>[<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>]</b>')</script></div>
<div class=div_title_action>
    <input type="button" id="button2" value='<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>' onclick="javascript:createType()">
    <input type="button" id="button2" value="<%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%>" onclick="javascript:cancel('<%=oid%>')">
</div>
<div class=div_body_title>
    <input type="hidden" name="oid" value="<%=oid%>">
    <%=messageService.getString("e3ps.message.ket_message", "00040", parentCode) %><%--{0}의 하위에--%>
</div>
<div class=div_body_row>
    <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></div>
    <div class=div_row_long>
        <input type=text size=40 name="NAME" id=i>
    </div>
</div>
<div class=div_body_row>
    <div class=div_row_label><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></div>
    <div class=div_row_long>
        <input type=text size=40 name="CODE" id=i>
    </div>
</div>


</form>
</body>
</html>
