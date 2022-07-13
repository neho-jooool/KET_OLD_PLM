<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.project.*,
                 e3ps.project.beans.*,
                 e3ps.common.code.GenNumberCode,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.groupware.company.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<html>

<%
GenNumberCode genCode = new GenNumberCode();

String oid = request.getParameter("oid");

String valuePN = "";
String valuePCustomer = "";
String valuePProcess = "";
String valuePProduct = "";
String valuePConsignment = "";
String valuePChamber = "";
String valuePFabName = "";
String valuePAcceptanceDate = "";
String valuePDuration = "";
String valuePPlanStartDate = "";
String valuePPlanEndDate = "";

E3PSProject project = null;
//E3PSProjectData projectData = null;

if( StringUtil.checkString(oid) )
{
    project = (E3PSProject)CommonUtil.getObject(oid);
    //projectData = new E3PSProjectData(E3PSProject);
    valuePN = project.getPjtNo();
    valuePCustomer = project.pjtCustomer;
    //valuePProcess = projectData.pjtProcess;
    valuePProduct = project.pjtProduct;
    valuePConsignment = project.pjtConsignment;
    valuePChamber = String.valueOf(project.pjtChamberNo);
    valuePFabName = project.pjtFabName;
    valuePAcceptanceDate = DateUtil.getDateString(project.pjtAcceptanceDate,"D");
    valuePDuration = String.valueOf(project.pjtDuration);
    valuePPlanStartDate = DateUtil.getDateString(project.pjtPlanStartDate, "D");
    valuePPlanEndDate = DateUtil.getDateString(project.pjtPlanEndDate, "D");
}
%>

<head>
<TITLE>프로젝트 수정</TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language="JavaScript">
function update(){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03335") %><%--정말 수정하시겠습니까?--%>")) {
        return;
    } else {
        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
        document.forms[0].method = "post";
        document.forms[0].command.value = "update";
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

function closeForm() {
    self.close();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="/jsp/common/processing.html"%>
<form>

<!-- Hidden Values -->
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=StringUtil.checkNull(oid)%>">
<input type="hidden" name = "" value = "">


<!-- //Hidden Vlaues -->



<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "03082") %><%--프로젝트 수정--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align=right height="40">
            <input type=button onclick='javascript:update()' id=button value='<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>'>&nbsp;
            <input type=button onclick="javascript:closeForm()" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" id=button>
        </td>
    </tr>
</table>
<table width="95%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABDC6 align=center>
    <tr height=23 bgcolor="#D8E0E7" align=center>
        <td colspan=6 width=100% align=center id=textblue><%=messageService.getString("e3ps.message.ket_message", "03096") %><%--프로젝트 정보수정--%></td>
    </tr>
    <!-- ERP Interface 항목 들 -->
    <tr>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%><span class="style1"> *</span></td>
        <td width="35%" bgcolor="ffffff" colspan=5>&nbsp;<input name="projectNo" id=i style="width:69%" value="<%=valuePN%>"><input type=button onclick='javascript:search()' id=button value='<%=messageService.getString("e3ps.message.ket_message", "02652") %><%--조회--%>'></td>
    </tr>
    <tr>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00697") %><%--거래처 명--%></td>
        <td width="35%" bgcolor="ffffff">&nbsp;<input name="projectCustomer" id=i style="width:69%"  value="<%=valuePCustomer%>"></td>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;PROCESS</td>
        <td width="35%" bgcolor="ffffff" colspan=3>&nbsp;<input name="projectProcess" id=i style="width:69%"  value="<%=valuePProcess%>"></td>
    </tr>
    <tr>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;PRODUCT</td>
        <td width="35%" bgcolor="ffffff">&nbsp;<input name="projectProduct" id=i style="width:69%"  value="<%=valuePProduct%>"></td>
        <td width="10%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02886") %><%--출하조건--%></td>
        <td width="35%" bgcolor="ffffff" colspan=3>&nbsp;<input name="projectConsignment" id=i style="width:69%"  value="<%=valuePConsignment%>"></td>
    </tr>
    <tr>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00114") %><%--Chamber 수--%></td>
        <td width="35%" bgcolor="ffffff">&nbsp;<input name="projectChamber" id=i style="width:69%"  value="<%=valuePChamber%>"> EA</td>
        <td width="10%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01966") %><%--수주일자--%></td>
        <td width="35%" bgcolor="ffffff" colspan=3>&nbsp;<input name="projectAcceptanceDate" id=i style="width:69%" readonly onclick="javascript:openCal('projectAcceptanceDate');"  value="<%=valuePAcceptanceDate%>">&nbsp;&nbsp;<a href="javascript:openCal('projectAcceptanceDate')"><img src="/plm/portal/icon/icon_cal.gif" border=0 onfocus="this.blur();" ></a></td>
    </tr>
    <!-- //ERP Interface 항목 들 -->
    <!-- FAB Name -->
    <tr>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;FAB Name<span class="style1"> *</span></td>
        <td width="35%" bgcolor="ffffff" colspan=5>&nbsp;<input name="projectFab" id=i style="width:69%" value="<%=valuePFabName %>"></td>
    </tr>
    <!-- //FAB Name -->
    <!-- 기타 속성 -->
    <tr>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%><span class="style1"> *</span></td>
        <td width="20%" bgcolor="ffffff">&nbsp;<input name="planStartDate" id=i size=20 maxlength=20 readonly onclick="javascript:openCal('planStartDate');" value="<%=valuePPlanStartDate%>">&nbsp;&nbsp;<a href="javascript:openCal('planStartDate')"><img src="/plm/portal/icon/icon_cal.gif" border=0 onfocus="this.blur();" ></a></td>
        <td width="10%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%><span class="style1"> *</span></td>
        <td width="20%" bgcolor="ffffff">&nbsp;<input name="planEndDate" id=i size=20 maxlength=20 readonly onclick="javascript:openCal('planEndDate');" value="<%=valuePPlanEndDate%>">&nbsp;&nbsp;<a href="javascript:openCal('planEndDate')"><img src="/plm/portal/icon/icon_cal.gif" border=0 onfocus="this.blur();" ></a></td>
        <td width="10%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02828") %><%--총기간--%><span class="style1"> *</span></td>
        <td width="20%" bgcolor="ffffff">&nbsp;<input name="projectDuration" id=i style="width:69%" value="<%=valuePDuration%>"></td>
    </tr>
    <!-- //기타 속성 -->
    <!-- 설명 -->
    <tr>
        <td width="15%" bgcolor=#EBEFF3 id=textblue>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
        <td width="85%" bgcolor="ffffff" align="center" colspan="5"><textarea name="projectDesc" rows=8 style="width:99%" id=i><%=project.pjtDesc%></textarea></td>
    </tr>
    <!-- //설명 -->
</table>
</form>
</body>
</html>
