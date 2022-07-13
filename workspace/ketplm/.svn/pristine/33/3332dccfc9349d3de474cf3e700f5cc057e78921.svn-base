<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.org.*,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper,
                wt.query.QuerySpec,
                wt.query.SearchCondition,
                java.util.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
    ProjectOutput output = (ProjectOutput)CommonUtil.getObject(oid);
    ProjectOutputData outputData = new ProjectOutputData(output);

    String command = StringUtil.checkNull(request.getParameter("command"));
    if("update".equals(command)) {
        String name = StringUtil.checkNull(request.getParameter("name"));
        String description = StringUtil.checkNull(request.getParameter("description"));
        String location = StringUtil.checkNull(request.getParameter("Location"));
        String role = StringUtil.checkNull(request.getParameter("Role"));
        String registryUserOID = StringUtil.checkNull(request.getParameter("registryUserOID"));
        WTPrincipalReference registryUser = null;
        if(StringUtil.checkString(registryUserOID)) {
            registryUser = WTPrincipalReference.newWTPrincipalReference((WTUser)CommonUtil.getObject(registryUserOID)) ;
        }

        output.setOutputName(name);
        output.setOutputDesc(description);
        output.setOutputLocation(location);
        if(StringUtil.checkString(role)) {
            output.setOutputRole(role);
        }
        if(registryUser != null) {
            output.setOwner(registryUser);
        }
        output.setProject(outputData.project);
        if ( outputData.task != null ) output.setTask(outputData.task);

        output = ProjectOutputHelper.manager.modifyProjectOutput(output);
    }
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01722") %><%--산출물 수정--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language=JavaScript>
<!--

function update() {
    if(checkClassification()) {
        if(checkUpdate()) {
            //if(checkRole()) {
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

                //if(document.forms[0].role.selectedIndex > 0) {
                    //document.forms[0].Role.value = document.forms[0].role.options[document.forms[0].role.selectedIndex].value;
                //}

                disabledAllBtn();
                showProcessing();
                document.forms[0].command.value = "update";
                document.forms[0].submit();
            //} else {
                //alert("Role을 선택하지 않았습니다. \nRole을 선택하십시요.");
            //}
        }
    } else {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01355") %><%--마지막 문서분류체계를 선택하지 않았습니다 \n문서분류체계를 마지막 단계까지 꼭 선택 하십시요--%>");
    }
}

function checkUpdate() {
    if(checkField(document.forms[0].name, "문서제목")){
        return false;
    }
    return true;
}

function checkRole() {
    var form = document.forms[0];
    if(form.role.selectedIndex > 0) {
        return true;
    }
    return false;
}

function registryClose(){
    opener.document.forms[0].submit();
    self.close();
}

function registryUser(){
<%  if ( outputData.task != null ) {  %>
    var str="/plm/jsp/project/SelectTaskPeopleList.jsp?oid=<%=CommonUtil.getOIDString(outputData.task)%>&mode=s&function=registryUserProjectOutput";
<%  } else {  %>
    var str="/plm/jsp/project/SelectProjectPeopleList.jsp?oid=<%=oid%>&mode=s&function=registryUserProjectOutput";
<%  }  %>
    newWin = window.open(str,"registryUser", "scrollbars=yes,status=yes,menubar=no,toolbar=no,location=no,directories=no,width=600,height=410,resizable=yes,mebar=no,left=80,top=105");
    newWin.focus();
}

<%if("update".equals(command)){%>registryClose();<%}%>
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<FORM method=post name="projectOutputupdate">

<!-- Hidden Value -->
<input type=hidden name=oid value=<%=oid%>>
<input type=hidden name=command>
<input type=hidden name=Location>
<input type=hidden name=Role>

<table width="95%" height=40 border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td height="30">
            <table border=0 cellpadding=0 cellspacing=0>
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01733") %><%--산출물 정의 수정--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button id=button class="btnTras" onclick="JavaScript:update();" value='<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>'>&nbsp;
            <input type=button id=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onclick="JavaScript:self.close();">
        </td>
    </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
    <tr  bgcolor="#D8E0E7" align=center height=23>
        <td width="1%" bgcolor="#D8E0E7" id=title colspan=2><%=messageService.getString("e3ps.message.ket_message", "01734") %><%--산출물 정의 수정시--%> <span class="style1"> *</span> <%=messageService.getString("e3ps.message.ket_message", "00026") %><%--{0}는 필수항목입니다--%>.</td>
    </tr>
    <tr>
        <td  width="20%" bgcolor="#EBEFF3" align="left">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01431") %><%--문서위치--%> <span class="style1"> *</span></td>
        <td  width="80%" bgcolor="#ffffff">
            <jsp:include page="/jsp/project/DocumentClassification_include.jsp" flush="false">
                <jsp:param name="mode" value="update"/>
                <jsp:param name="location" value="<%=output.getOutputLocation()%>"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td  width="100" bgcolor="#EBEFF3" align="left">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%> <span class="style1"> *</span></td>
        <td  width="450" bgcolor="#ffffff">&nbsp;<input type="text" name="name" id=input  size="62" value="<%=StringUtil.checkNull(outputData.name)%>"></td>
    </tr>
    <tr>
        <td  width="100" bgcolor="#EBEFF3" align="left">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%> </td>
        <td  width="450" bgcolor="#ffffff">&nbsp;<textarea name="description" rows="13" cols="70" id=input><%=StringUtil.checkNull(outputData.description)%></textarea></td>
    </tr>
    <tr>
        <td width="20%" bgcolor="#EBEFF3">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01200") %><%--담당--%></td>
        <td width="80%" bgcolor="#ffffff">
            <table border=0 cellpadding=0 cellspacing=0>
                <tr>
                    <td>&nbsp;
                        <input type="text" disabled name="registryUserName" id=i size="20" value="<%=output.getOwner()==null?"":StringUtil.checkNull(output.getOwner().getFullName())%>">
                        <input type="hidden" name="registryUserOID" value="<%=output.getOwner()==null?"":CommonUtil.getOIDString(output.getOwner().getPrincipal())%>">&nbsp;&nbsp;
                    </td>
                    <td><input type=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' id=innerbutton onclick="JavaScript:registryUser();"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
