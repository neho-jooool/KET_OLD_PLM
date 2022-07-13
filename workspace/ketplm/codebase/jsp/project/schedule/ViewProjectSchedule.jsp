<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.project.E3PSProject,
                e3ps.project.beans.ProjectViewButtonAuth" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<jsp:useBean id="screenWidth"           class="java.lang.String" scope="request"/>
<jsp:useBean id="screenHeight"          class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataHead"            class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataHead1"            class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataBody"            class="java.lang.String" scope="request"/>
<jsp:useBean id="personRoleEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="personRoleEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="milestoneTypeEnumKey"  class="java.lang.String" scope="request"/>
<jsp:useBean id="milestoneTypeEnum"     class="java.lang.String" scope="request"/>
<jsp:useBean id="optionTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="optionTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="taskTypeEnumKey"       class="java.lang.String" scope="request"/>
<jsp:useBean id="taskTypeEnum"          class="java.lang.String" scope="request"/>
<jsp:useBean id="ganttChartMaxStart"    class="java.lang.String" scope="request"/>
<jsp:useBean id="ganttChartMinEnd"      class="java.lang.String" scope="request"/>
<%@include file="/jsp/common/multicombo.jsp" %>
<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");
    // EJS TreeGrid End

    String oid = StringUtil.checkNull(request.getParameter("oid"));
    String title = "";
    String pjtNo = "";
    String pjtName = "";
    int pjtVersion = 0;
    Object obj = CommonUtil.getObject(oid);
    E3PSProject project = null;
    ProjectViewButtonAuth auth = null;
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = false;

    if ( obj instanceof E3PSProject ) {

        project = (E3PSProject)obj;

        if ( project != null ) {

            // Project 일정 조회 화면 Title - Project : PJT_NO (PJT_NAME)
            pjtNo = StringUtil.checkNull(project.getPjtNo());
            pjtName = StringUtil.checkNull(project.getPjtName());
            title = "Project Name : " + pjtNo + " (" + pjtName + ")";

            // Project Version
            pjtVersion = project.getPjtHistory();

            // Project 권한
            auth = new ProjectViewButtonAuth(project);
            isAdmin = CommonUtil.isAdmin() || isbizAdmin;
        }
    }
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><%=messageService.getString("e3ps.message.ket_message", "00407") %><%--Project 일정 조회--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- EJS TreeGrid End -->



<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<%@include file="/jsp/project/schedule/ProjectScheduleGridJs.jsp" %>
<script src="/plm/portal/js/common.js"></script>

<style type="text/css">
/* body {
    margin-left: 15px;
    margin-top: 15px;
    margin-right: 15px;
    margin-bottom: 15px;
} */
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}

/* Grid CSS */
/* --- Removes border and backround from main and run boxes in all styles --- */
.GEFGanttNavyRunBox,.GEGanttNavyRunBoxIn,.GEGanttNavyRunBoxOut,
.GEFGanttRunBox,.GEGanttRunBoxIn,.GEGanttRunBoxOut
   { background: none; border: 0px; height: 16px; }
</style>

<script type="text/javascript">
//<![CDATA[
$(document).ready(function(e) {
    treeGridResize("#ViewProjectScheduleGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

    // MS Project Download
    function downloadMSProject() {
        document.form01.cmd.value = "mpp_download";

        document.form01.action = "/plm/servlet/e3ps/ProjectScheduleDownloadServlet";
        document.form01.target = "_self";
        document.form01.submit();
    }

    // Project 일정 조회
    function doSubmit() {

        document.form01.cmd.value = "view";

        document.form01.action = "/plm/servlet/e3ps/ProjectScheduleServlet";
        document.form01.target = "_self";
        document.form01.submit();
    }

//]]>
</script>

</head>

<body>

<form name="form01" method="post">

<input type="hidden" id="oid" name="oid" value="<%= oid %>" />
<input type="hidden" name="cmd" value="" />

<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                            <tr>
                                <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%= title %></td>
                                <td style="width: 10px;"></td>
                            </tr>
                        </table>
                    </td>
                    <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
                </table>
                <table style="width: 100%;">
                <tr>
                    <td class="space10"> </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <!-- [END] title & position -->

        <!-- [START] 버튼 -->
        <table style="width: 100%;">
        <tr>
            <td align="right">
                <table>
                <tr>
                    <!-- [MS Project Download] 버튼 -->
                    <%
                        // MS Project Download 버튼 표시 조건
                        // PM, PMO, Biz. Admin 또는 Admin일 경우에만 표시
                        if ( auth.isPM || auth.isPMO || isAdmin ) {
                    %>
                    <td align="right">
                        <table>
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:downloadMSProject();" class="btn_blue">MS Project Download</a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%
                        }
                    %>
                    <!-- [닫기] 버튼 -->
                    <td width="10"></td>
                    <td align="right">
                        <table>
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <table style="width: 100%">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] 버튼 -->

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/schedule/ViewProjectScheduleGridLayout.jsp"
                            Layout_Method="POST"
                            Layout_Param_Screenwidth="<%= screenWidth %>"
                            Layout_Param_Screenheight="<%= screenHeight %>"
                            Layout_Param_Personroleenumkey="<%= personRoleEnumKey %>"
                            Layout_Param_Personroleenum="<%= personRoleEnum %>"
                            Layout_Param_Personroleenumkey="<%= personRoleEnumKey %>"
                            Layout_Param_Personroleenum="<%= personRoleEnum %>"
                            Layout_Param_Milestonetypeenumkey="<%= milestoneTypeEnumKey %>"
                            Layout_Param_Milestonetypeenum="<%= milestoneTypeEnum %>"
                            Layout_Param_Optiontypeenumkey="<%= optionTypeEnumKey %>"
                            Layout_Param_Optiontypeenum="<%= optionTypeEnum %>"
                            Layout_Param_Tasktypeenumkey="<%= taskTypeEnumKey %>"
                            Layout_Param_Tasktypeenum="<%= taskTypeEnum %>"
                            Layout_Param_Ganttchartmaxstart="<%= ganttChartMaxStart %>"
                            Layout_Param_Ganttchartminend="<%= ganttChartMinEnd %>"
                            Layout_Param_Oid="<%= oid %>"
                            Data_Url="/plm/jsp/project/schedule/ViewProjectScheduleGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Resulthead="<%= tgDataHead %>"
                            Data_Param_Resulthead1="<%= tgDataHead1 %>"
                            Data_Param_Resultbody="<%= tgDataBody %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="View_Project_Schedule"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
    </td>
</tr>
</table>

</form>

</body>
</html>
