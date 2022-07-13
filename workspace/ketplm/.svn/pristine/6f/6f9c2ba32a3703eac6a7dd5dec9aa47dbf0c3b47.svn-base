<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.org.*,
                wt.fc.*,
                wt.query.*,
                java.util.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    //필수 항목
    String target = request.getParameter("target"); //Servlet에서 나누기 위해서 구분 하는 필드

    //TemplateProject & TemplateTask 로 변경(2005/03/05)
    String oid = request.getParameter("oid");
    Object obj = CommonUtil.getObject(oid);
    TemplateProject project = null;
    TemplateTask task = null;
    if ( obj instanceof TemplateProject) {
        project = (TemplateProject)obj;
    } else if ( obj instanceof TemplateTask ) {
        task = (TemplateTask)obj;
        project = task.getProject();
    }

    String command = StringUtil.checkNull(request.getParameter("command"));
    if("create".equals(command)){
        String name = StringUtil.checkNull(request.getParameter("name"));
        String description = StringUtil.checkNull(request.getParameter("description"));
        String location = StringUtil.checkNull(request.getParameter("Location"));
        String role = StringUtil.checkNull(request.getParameter("Role"));
        String registryUserOID = StringUtil.checkNull(request.getParameter("registryUserOID"));
        WTPrincipalReference registryUser = WTPrincipalReference.newWTPrincipalReference((WTUser)CommonUtil.getObject(registryUserOID)) ;

        ProjectOutput output = ProjectOutput.newProjectOutput();
        output.setOutputName(name);
        output.setOutputDesc(description);
        output.setOutputLocation(location);
        //output.setOutputRole(role);
        output.setOutputHistory(0);
        output.setProject(project);
        output.setOwner(registryUser);
        if ( task != null ) output.setTask(task);

        output = ProjectOutputHelper.manager.createProjectOutput(output);
    }
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01732") %><%--산출물 정의--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language=JavaScript>

<!--

function create() {
    if(checkCreate()) {
        if(document.forms[0].docClassify3.selectedIndex > 0) {
            document.forms[0].Location.value = document.forms[0].docClassify3.options[document.forms[0].docClassify3.selectedIndex].value;
        } else {
            if(document.forms[0].docClassify2.selectedIndex > 0) {
                document.forms[0].Location.value = document.forms[0].docClassify2.options[document.forms[0].docClassify2.selectedIndex].value;
            } else {
                if(document.forms[0].docClassify1.selectedIndex > 0) {
                    document.forms[0].Location.value = document.forms[0].docClassify1.options[document.forms[0].docClassify1.selectedIndex].value;
                } else {
                    if(document.forms[0].docClassify0.selectedIndex > 0) {
                        document.forms[0].Location.value = document.forms[0].docClassify0.options[document.forms[0].docClassify0.selectedIndex].value;
                    }
                }
            }
        }

        disabledAllBtn();
        showProcessing();
        document.forms[0].command.value = "create";
        document.forms[0].submit();
    }
}

function checkCreate() {
    if(checkField(document.forms[0].name, "문서제목")){
        return false;
    }
    return true;
}

function registryClose(){
    opener.document.forms[0].submit();
    self.close();
}

function registryUser(){
<%  if ( task != null ) {  %>
    var str="/plm/jsp/project/SelectTaskPeopleList.jsp?oid=<%=oid%>&mode=s&function=registryUserProjectOutput";
<%  } else {  %>
    var str="/plm/jsp/project/SelectProjectPeopleList.jsp?oid=<%=oid%>&mode=s&function=registryUserProjectOutput";
<%  }  %>
    newWin = window.open(str,"registryUser", "scrollbars=yes,status=yes,menubar=no,toolbar=no,location=no,directories=no,width=600,height=410,resizable=yes,mebar=no,left=80,top=105");
    newWin.focus();
}
<%if("create".equals(command)){%>registryClose();<%}%>
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<FORM method=post name="projectOutputCreate">

<!-- Hidden Values -->
<input type="hidden" name="target" value="<%=target%>">
<input type="hidden" name="number">
<input type=hidden name=oid value=<%=oid%>>
<input type=hidden name=command>
<input type=hidden name=Location>
<input type=hidden name=Role>

<table width="95%" height=40 border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td height="30">
            <!--title//-->
            <table border=0 cellpadding=0 cellspacing=0>
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01732") %><%--산출물 정의--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
            <!--title end//-->
        </td>
        <td align="right">
            <input type=button class="btnTras" id=button onclick="JavaScript:create();" value='<%=messageService.getString("e3ps.message.ket_message", "01795") %><%--생성--%>'>&nbsp;
            <input type=button class="btnTras" id=button value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onclick="JavaScript:self.close();">
        </td>
    </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
    <tr  bgcolor="#D8E0E7" align=center height=20><td width="1%" bgcolor="#D8E0E7" colspan=2><%=messageService.getString("e3ps.message.ket_message", "01735") %><%--산출물 정의시--%> <span class="style1"> *</span> <%=messageService.getString("e3ps.message.ket_message", "00026") %><%--{0}는 필수항목입니다--%>.</td></tr>
    <tr>
        <td width="20%" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01431") %><%--문서위치--%><span class="style1"> *</span></td>
        <td width="80%" bgcolor="#ffffff">
            <jsp:include page="/jsp/project/DocumentClassification_include.jsp" flush="false">
                <jsp:param name="mode" value="create"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td width="20%" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%><span class="style1"> *</span></td>
        <td width="80%" bgcolor="#ffffff" align="center">&nbsp;<input type="text" name="name" id=i style="width:99%"></td>
    </tr>
    <tr>
        <td width="20%" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
        <td width="80%" bgcolor="#ffffff">&nbsp;<textarea name="description" rows="13" style="width:99%" id=i></textarea></td>
    </tr>
    <tr>
        <td width="20%" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01200") %><%--담당--%></td>
        <td width="80%" bgcolor="#ffffff">
            <table border=0 cellpadding=0 cellspacing=0>
                <tr>
                    <td>&nbsp;<input type="text" disabled name="registryUserName" id=i size="20"><input type="hidden" name="registryUserOID">&nbsp;&nbsp;
                    </td>
                    <td><input type=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' id=innerbutton onclick="JavaScript:registryUser();"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!--//-->
</form>
</body>
</html>
