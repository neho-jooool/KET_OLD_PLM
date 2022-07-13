<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="e3ps.project.historyprocess.*" %>
<%@page import = "wt.fc.*,wt.org.*"%>
<%@page import="e3ps.groupware.company.*,e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oids[] = request.getParameterValues("oid");

    String oid = oids[0];
    String oldOid = oids[1];

    TemplateProject template = (TemplateProject)CommonUtil.getObject(oid);
    TemplateProjectData projectData = new TemplateProjectData(template);


    TemplateProject template2 = (TemplateProject)CommonUtil.getObject(oldOid);
    TemplateProjectData projectData2 = new TemplateProjectData(template2);

    String infoStr = "";
    if(projectData2.lifeCycle.equals("작업중")){
        infoStr =messageService.getString("e3ps.message.ket_message", "02441")/*작업중*/;
    }

%>

<html>
<head>
<title></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">

</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<table border="0" cellpadding="1" cellspacing="1" width="100%">
    <tr>
        <td valign="top" style="padding-left:12">
<!-------------------------------------- 상단버튼 시작 //-------------------------------------->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:parent.window.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                        <!--table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                    <td class=fixLeft></td>
                                    <td ><input type=button class="btnTras" value="닫 기" onClick="javascript:parent.window.close()"></td>
                                    <td class=fixRight></td>
                            </tr>
                        </table-->
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm5"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <!-- Template 기본정보 -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="20%"><COL width="10%"><COL width="20%"><COL width="10%"><COL width="20%">
                <tr>
                    <td class="tdblueM" colspan=6>
                        <img src='/plm/portal/icon/project.gif'>&nbsp;<B>[Template]</B>&nbsp;<%=projectData.tempName%>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=projectData.pjtHistory%>) <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL">Template <%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=projectData.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02857") %><%--최초--%><br><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=DateUtil.getDateString(projectData.tempCreateDate, "D")%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02843") %><%--최종--%><br><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=DateUtil.getDateString(projectData.tempModifyDate, "D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td class="tdwhiteL" colspan=3>&nbsp;
                        <%=projectData.tempDesc%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=projectData.lifeCycle%>
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm5"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <!-- Template 기본정보 -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <COL width="15%"><COL width="20%"><COL width="10%"><COL width="20%"><COL width="10%"><COL width="20%">
                <tr>
                    <td class="tdblueM" colspan=6>
                        <img src='/plm/portal/icon/project.gif'>&nbsp;<B>[Template]</B>&nbsp;<%=projectData.tempName%>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>(<%=projectData2.pjtHistory%>) <%=infoStr%> <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%></B>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL">Template <%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=projectData2.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02857") %><%--최초--%><br><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=DateUtil.getDateString(projectData2.tempCreateDate, "D")%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02843") %><%--최종--%><br><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=DateUtil.getDateString(projectData2.tempModifyDate, "D")%>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td class="tdwhiteL" colspan=3>&nbsp;
                        <%=projectData2.tempDesc%>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td class="tdwhiteL">&nbsp;
                        <%=projectData2.lifeCycle%>
                    </td>
                </tr>

            </table>
            <!------------------------------ 본문 끝 //------------------------------>
        </td>
    </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
