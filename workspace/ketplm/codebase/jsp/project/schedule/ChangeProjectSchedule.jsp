<%@page import="e3ps.project.WorkProject"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="wt.workflow.work.WorkflowServerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.StringUtil,
                e3ps.common.util.DateUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.KETGridUtil,
                e3ps.common.util.KETParamMapUtil,
                e3ps.project.beans.E3PSProjectData,
                e3ps.project.E3PSProject,
                e3ps.project.ReviewProject,
                e3ps.project.ProductProject,
                e3ps.project.MoldProject,
                e3ps.project.beans.ProjectViewButtonAuth,
                e3ps.wfm.entity.KETWfmApprovalMaster,
                e3ps.wfm.util.WorkflowSearchHelper" %>
                
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<jsp:useBean id="screenWidth"           class="java.lang.String" scope="request"/>
<jsp:useBean id="screenHeight"          class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataHead"            class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataHead1"           class="java.lang.String" scope="request"/>
<jsp:useBean id="tgDataBody"            class="java.lang.String" scope="request"/>
<jsp:useBean id="personRoleEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="personRoleEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="milestoneTypeEnumKey"  class="java.lang.String" scope="request"/>
<jsp:useBean id="milestoneTypeEnum"     class="java.lang.String" scope="request"/>
<jsp:useBean id="optionTypeEnumKey"     class="java.lang.String" scope="request"/>
<jsp:useBean id="optionTypeEnum"        class="java.lang.String" scope="request"/>
<jsp:useBean id="taskTypeEnumKey"       class="java.lang.String" scope="request"/>
<jsp:useBean id="taskTypeEnum"          class="java.lang.String" scope="request"/>
<jsp:useBean id="scheduleTypeEnumKey"   class="java.lang.String" scope="request"/>
<jsp:useBean id="scheduleTypeEnum"      class="java.lang.String" scope="request"/>
<jsp:useBean id="priorityControlEnumKey"   class="java.lang.String" scope="request"/>
<jsp:useBean id="priorityControlEnum"      class="java.lang.String" scope="request"/>
<jsp:useBean id="mainScheduleCodeEnumKey"   class="java.lang.String" scope="request"/>
<jsp:useBean id="mainScheduleCodeEnum"      class="java.lang.String" scope="request"/>
<jsp:useBean id="debugSubEnumKey"       class="java.lang.String" scope="request"/>
<jsp:useBean id="debugSubEnum"          class="java.lang.String" scope="request"/>
<jsp:useBean id="debugNEnumKey"         class="java.lang.String" scope="request"/>
<jsp:useBean id="debugNEnum"            class="java.lang.String" scope="request"/>
<jsp:useBean id="debugTaskEnumKey"      class="java.lang.String" scope="request"/>
<jsp:useBean id="debugTaskEnum"         class="java.lang.String" scope="request"/>
<jsp:useBean id="ganttChartMaxStart"    class="java.lang.String" scope="request"/>
<jsp:useBean id="ganttChartMinEnd"      class="java.lang.String" scope="request"/>

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");
    // EJS TreeGrid End

    String oid = StringUtil.checkNull(request.getParameter("oid"));
    String popup = StringUtil.checkNull(request.getParameter("popup"));
    String title = "";
    String pjtNo = "";
    String pjtName = "";
    String isMold = "";
    String pjtPlanEndDate = "";
    int historyNoteType = 0;
    Object obj = CommonUtil.getObject(oid);
    Persistable obj_ = CommonUtil.getObject(oid);
    E3PSProject project = null;
    ProjectViewButtonAuth auth = null;
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = false;
    int btnDivide = 0;

    KETWfmApprovalMaster master =  WorkflowSearchHelper.manager.getMaster(obj_);
    
    if(master != null && master.getCompletedDate() != null){
       btnDivide = 1;
    }
    
    
    if ( obj instanceof E3PSProject ) {

        project = (E3PSProject)obj;

        if ( project != null ) {
            
            // Project 일정 변경 화면 Title - Project : PJT_NO (PJT_NAME)
            pjtNo = StringUtil.checkNull( project.getPjtNo() );
            pjtName = StringUtil.checkNull( project.getPjtName() );
            title = "Project Name : " + pjtNo + " (" + pjtName + ")";

            // Project 일정변경구분
            historyNoteType = project.getHistoryNoteType();

            // Project 권한
            auth = new ProjectViewButtonAuth(project);
            isAdmin = CommonUtil.isAdmin() || isbizAdmin;
            
            String state = project.getLifeCycleState().getDisplay();
            Kogger.debug(state);
            
            if ( project instanceof MoldProject ) {
        	   isMold = "MOLD";
        	   ProductProject pjt = ((MoldProject) project).getMoldInfo().getProject();
        	   
        	   int pjttype = pjt.getPjtType();
        	   
        	   String process = NumberCodeUtil.getNumberCodeValue("PROCESS", pjt.getProcess().getCode(), messageService.getLocale().toString());
        	   E3PSTaskData taskData = null;
        	   
        	   if(pjttype!=4 && "Pilot".equals(process)){//1. 전자사업부 제외 2.개발단계가 Pilot 3. Task유형이 Gate4일때 계획완료일자를 구하는데, 만약 Gate4가 없다면 프로젝트의 계획완료일자를 구한다
	        	   QueryResult rs = ProjectTaskHelper.manager.getTaskWithType(pjt, "Gate");
	
	        	   E3PSTask Task = null;
	        	   
	        	   if (rs.hasMoreElements()) {
	        	       Object[] o = (Object[]) rs.nextElement();
	        	       Task = (E3PSTask) o[0];
	        	       
	        	       String taskType = Task.getTaskType()+Task.getTaskTypeCategory();
	        	        
	        	        
	        	       if("Gate4".equals(taskType)){
	        		      taskData = new E3PSTaskData(Task);
	        		      pjtPlanEndDate = DateUtil.getTimeFormat(taskData.taskPlanEndDate, "yyyyMMdd");
	        	       }
	        	   }
	        	   
	        	   if(StringUtils.isEmpty(pjtPlanEndDate)){
	        	       E3PSProjectData data = new E3PSProjectData(pjt);
	        	       pjtPlanEndDate = DateUtil.getTimeFormat(data.pjtPlanEndDate, "yyyyMMdd");
	        	   }
               }
        	   
            }
            
        }
    }
    
   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00405") %><%--Project 일정 변경--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script type="text/javascript">
var callType = "";
</script>
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- EJS TreeGrid End -->
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<%@include file="/jsp/project/schedule/ProjectScheduleGridJs.jsp" %>
<%@include file="/jsp/common/processing.html" %>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
/* Grid CSS */
/* --- Removes border and backround from main and run boxes in all styles --- */
.GEFGanttNavyRunBox,.GEGanttNavyRunBoxIn,.GEGanttNavyRunBoxOut,
.GEFGanttRunBox,.GEGanttRunBoxIn,.GEGanttRunBoxOut
   { background: none; border: 0px; height: 16px; }
</style>

<script type="text/javascript">
//<![CDATA[

    // Excel Download
    function downloadExcel() {

        document.form01.method = "post";
        document.form01.action = "/plm/jsp/project/manage/ExcelDownLoad.jsp";
        document.form01.submit();
    }

    // Excel Upload
    function uploadExcel() {

        var oid = document.form01.oid.value;
        var url = "/plm/jsp/project/TaskExcelUp.jsp?oid=" + oid;
        openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");
    }

    // MS Project Download
    function downloadMSProject() {

        document.form01.cmd.value = "mpp_download";

        document.form01.action = "/plm/servlet/e3ps/ProjectScheduleDownloadServlet";
        document.form01.target = "_self";
        document.form01.submit();
    }

    // MS Project Upload
    function uploadMSProject() {

        if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03278") %><%--MS Project File 업로드 시 기존 일정 정보는 삭제됩니다.\nMS Project File을 업로드하시겠습니까?--%>") ) {

            var url = "/plm/jsp/project/schedule/UploadSchedule.jsp";
            var fileFullPath = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=580px; dialogHeight:150px; center:yes");

            if ( fileFullPath != "" && fileFullPath != undefined ) {
                showProcessing();   // 진행 상태바 표시 (processing.html)
                disabledAllBtn();   // 버튼 비활성화

                document.form01.cmd.value = "mpp_upload";

                document.form01.action = "/plm/servlet/e3ps/ProjectScheduleDownloadServlet?fileFullPath=" + fileFullPath;
                document.form01.target = "_self";
                document.form01.submit();
            }
        }
    }

    // Project 일정 조회
    function doSubmit() {

        document.form01.cmd.value = "search";

        document.form01.action = "/plm/servlet/e3ps/ProjectScheduleServlet";
        document.form01.target = "_self";
        document.form01.submit();
    }
    
    function selectProject(oid){
    	if(confirm("<%=messageService.getString("e3ps.message.ket_message", "07119") %>")){
            
    		if(oid.indexOf("Review") != -1){
    			SearchUtil.selectOneProjectPopUp('copyProject','project_type=1&paramRadio=1&pjtState=pjtState1&pjtStateValue=PMOINWORK');
    		}else if(oid.indexOf("Mold") != -1){
    			SearchUtil.selectOneProjectPopUp('copyProject','project_type=3&paramRadio=3&pjtState=pjtState3&pjtStateValue=PMOINWORK');
    		}else if(oid.indexOf("Product") != -1){
    			SearchUtil.selectOneProjectPopUp('copyProject','project_type=2&paramRadio=2&pjtState=pjtState2&pjtStateValue=PMOINWORK');	
    		}
    		
    		
    		
            //window.open(url, title, "800", "600", "status=no,scrollbars=yes,resizable=no");
        }
    }
    
    function copyProject(objArr){
    	//var projectNo = objArr[0][1];
        //var oid = objArr[0][0];
        
        var msg = '';
        
        $.ajax({
            type: 'post',
            url : '/plm/ext/project/product/copyProject.do',
            async : false,
            cache:false,
            data : {
            	sourceOid : document.form01.oid.value,
                targetOid : objArr[0][0]
            },
            success : function(result){
                if(result.msg != ''){
                    msg = result.msg;
                }
            }
        });
        
        if(msg != ''){
            alert(msg);
            return;
        }else{
        	opener.location.reload();
            location.reload();
        }
    }
    
    //WBS template 팝업
    function selectWBSTemplate(oid){
    	if(confirm("<%=messageService.getString("e3ps.message.ket_message", "07119") %>")){
    		var url = "/plm/servlet/e3ps/SearchProjectTemplateServlet?popup=yes&selector=project&origin="+oid+'&callBackFn=wbsReSetting';
    	    var title = "wbsCopySearchPopup";
    	    openWindow2(url, title, 800, 600);
    	    //window.open(url, title, "800", "600", "status=no,scrollbars=yes,resizable=no");
    	}
    }
    
    //WBS재설정 
    function wbsReSetting(jsonData){
        showProcessing();
        $.ajax({
            type       : "POST",
            url        : "/plm/servlet/e3ps/SearchProjectTemplateServlet",
            data       : jsonData,
            //dataType   : "json",
            success    : function(data){
                location.reload();
                //window.close();
                //opener.window.close();
            },
            error    : function(xhr, status, error){
                alert(xhr+"  "+status+"  "+error);                         
            }
        });
        
        /* $("#reSetting").attr({action:"/plm/servlet/e3ps/SearchProjectTemplateServlet",method:"post"}).submit();  
        window.opener.opener.location.reload();
        window.close();
        opener.window.close(); */
    }
    
    function gridReload(){
    	var G = Grids[0];
    	G.Reload();
    }

    function btnDisabled(){
    	$("#completedBtn").attr("style","color:#aaa" );
    	$("#completedBtn").attr("disabled", true);
    }
    
    function btnEnabled(){
    	$("#completedBtn").attr("style","color:#555" );
        $("#completedBtn").attr("disabled", false);
    }
    
    function getPProjectSchedule(oid){
    	
    	var param = "oid=" + oid;
        ajaxCallServer("/plm/servlet/e3ps/ProjectScheduleServlet?cmd=getPProjectSchedule", param, function(data){
        	
        	var taskList = data.taskList;
        	var grid = Grids[0];
        	
            for ( var row = grid.GetFirstVisible(); row; row = grid.GetNextVisible(row)) {
            	
            	var scheduleType = row.ScheduleType;
            	
            	if(scheduleType != null && scheduleType.trim().length > 0){
            		for(var i = 0; i < taskList.length; i++){
                        var task = taskList[i];

                        if(task.scheduleType == scheduleType){
                        	
                        	var planStartDate = new Date(task.planStartDate).getTime();
                        	var planEndDate = new Date(task.planEndDate).getTime();
                        	
                        	if(!isNaN(planStartDate) && planStartDate != 0){
                        		grid.SetValue(row, "PlanStartDate", planStartDate, 1);
                        	}
                        	if(!isNaN(planStartDate) && planEndDate != 0){
                        		grid.SetValue(row, "PlanEndDate", planEndDate, 1);
                            }
                            break;
                        }
                    }
            	}
            }
        });
    }
    
    $(document).ready(function(){
    	treeGridResize("#ChangeProjectScheduleGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    	btnDisabled();
        Grids.OnRenderFinish = function(G) {
        	btnEnabled();
      }
    
    });
//]]>
</script>
</head>
<body>
    <form name="form01" method="post">
        <input type="hidden" id="oid" name="oid" value="<%=oid%>" /> 
        <input type="hidden" name="mode" value="ChangeSchedule" /> 
        <input type="hidden" name="cmd" value="" /> 
        <input type="hidden" name="command" value="" /> 
        <input type="hidden" name="isMold" value="<%=isMold%>" />
        <input type="hidden" name="pjtPlanEndDate" value="<%=pjtPlanEndDate%>" />
        <input type="hidden" name="popup" value="<%=popup%>" />
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
                                                    <td class="font_01"><%=title%></td>
                                                    <td style="width: 10px;"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                                    </tr>
                                </table>
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="space10"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table> <!-- [END] title & position --> <!-- [START] 버튼 -->
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <table>
                                    <tr>
                                        <!-- [Project 인원] 버튼 -->
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:openProjectMemberPopup('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00403")%><%--Project 인원--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <!-- [Excel Download] 버튼 - 기존 기능 -->
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:downloadExcel();" class="btn_blue">Excel Download</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <!-- [Excel Upload] 버튼 - 기존 기능 -->
                                        <%
                                            // Excel Upload 버튼 표시 조건 - 최초 버전일 경우에만 표시
                                            if (project != null && auth.isLatest && auth.isFirst && !auth.isWorkingCopy) {
                                        %>
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:uploadExcel();" class="btn_blue">Excel Upload</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%
                                            }
                                        %>
                                        <!-- [MS Project Download] 버튼 -->
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:downloadMSProject();" class="btn_blue">MS Project Download</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <!-- [MS Project Upload] 버튼 -->
                                        <%
                                            // MS Project Upload 버튼 표시 조건 - 최초 버전일 경우에만 표시
                                            if (project != null && project.getPjtHistory() == 0 && project.getPjtIteration() == 0) {
                                        %>
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:uploadMSProject();" class="btn_blue">MS Project Upload</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%
                                            }
                                        %>
                                    </tr>
                                </table>
                            </td>
                            <td>&nbsp;</td>
                            <td align="right">
                                <table>
                                    <tr>
                                        <%if (project instanceof MoldProject) { %>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:getPProjectSchedule('<%=oid%>');" class="btn_blue">제품 프로젝트 일정 동기화</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%} %>
                                        <%
                                            if (btnDivide == 0){
                                            if (!project.getLifeCycleState().toString().equals("UNDERREVIEW") && (project.getLifeCycleState().getDisplay().equals("등록중"))) {
                                        %>
                                        
                                        <%if (project instanceof ProductProject || project instanceof ReviewProject || project instanceof MoldProject) { %>
                                        <!-- [Project WBS Copy]  버튼 -->
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:selectProject('<%=oid%>');" class="btn_blue">Project WBS Copy</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="10">&nbsp;</td>
                                        <%} %>
                                        
                                        <!-- [WBS 재설정]  버튼 -->
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:selectWBSTemplate('<%=oid%>');" class="btn_blue">WBS 재설정<%-- WBS 재설정 --%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="10">&nbsp;</td>
                                        <%
                                            }
                                            }
                                        %>
                                        <% if (!project.getLifeCycleState().toString().equals("UNDERREVIEW") && (project.getLifeCycleState().getDisplay().equals("일정변경") || project.getLifeCycleState().getDisplay().equals("등록중") || project.isWorkingCopy())) {%>
                                        <!-- [저장] 버튼 -->
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:saveGrid('<%=oid%>','<%=pjtNo %>');btnDisabled();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <% }%>
                                        <!-- [완료] 버튼 -->
                                        <%
                                            // 완료 버튼 표시 조건
                                            // [검토 Project] 최신 버전이고, PM 또는 Admin이고, 일정변경 또는 재작업 상태일 경우에만 표시
                                            // [제품 Project] 최신 버전이고, PM이고, 일정변경 또는 재작업 상태일 경우에만 표시 - 추가 Admin , PMO
                                            // [금형 Project] 최신 버전이고, PM 또는 Biz. Admin이고, 일정변경 또는 재작업 상태일 경우에만 표시
                                            if ((project instanceof ReviewProject && auth.isLatest && (auth.isPM || CommonUtil.isAdmin()) && (auth.isPMOInWork || auth.isPlanChange || auth.isReWork))
                                        		    || (project instanceof ProductProject && auth.isLatest && (auth.isPM || auth.isPMO ||CommonUtil.isAdmin()) && (auth.isPMOInWork || auth.isPlanChange || auth.isReWork))
                                        		    || (project instanceof WorkProject && auth.isLatest && (auth.isPM ||CommonUtil.isAdmin()) && (auth.isPMOInWork || auth.isPlanChange || auth.isReWork))
                                        		    || (project instanceof MoldProject && auth.isLatest && (auth.isPM || isbizAdmin) && (auth.isPMOInWork || auth.isPlanChange || auth.isReWork))) {
                                        %>
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" id="completedBtn"
                                                        onClick="javascript:completeChangeSchedule('<%=oid%>', '<%=historyNoteType%>','<%=pjtNo %>');"
                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171")%><%--완료--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%
                                            }
                                        %>
                                        <!-- [일정변경 취소] 버튼 -->
                                        <%
                                            // 일정변경 취소 버튼 표시 조건
                                            // [검토 Project] Working Copy이고, 최신 버전이고, Admin이고, 일정변경 상태일 경우에만 표시
                                            // [제품 Project] Working Copy이고, 최신 버전이고, Admin 또는 PM이고, 일정변경 또는 재작업 상태일 경우에만 표시
                                            // [금형 Project] Working Copy이고, 최신 버전이고, PM 또는 Biz. Admin이고, 일정변경 상태일 경우에만 표시
                                            // [Admin] 항상 표시
                                            if (!project.getLifeCycleState().toString().equals("UNDERREVIEW") && (project.getLifeCycleState().toString().equals("PLANCHANGE") || project.isWorkingCopy())) {

                                        		if ((project instanceof ReviewProject && auth.isWorkingCopy && auth.isLatest && isAdmin && auth.isPlanChange)
                                        		        || (project instanceof ProductProject && auth.isWorkingCopy && auth.isLatest && (isAdmin || auth.isPM) && (auth.isPlanChange || auth.isReWork))
                                        		        || (project instanceof MoldProject && auth.isWorkingCopy && auth.isLatest && (auth.isPM || isbizAdmin) && auth.isPlanChange) || CommonUtil.isAdmin()) {
                                        %>
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:cancelChangeSchedule();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02357")%><%--일정변경 취소--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%
                                            }
                                            }
                                        %>
                                        <!-- [닫기] 버튼 -->
                                        <td width="10">&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onClick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
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
                    </table> <!-- [END] 버튼 --> <!-- EJS TreeGrid Start -->
                    <div class="content-main">
                        <div class="container-fluid">
                            <div class="row-fluid">
                                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%">
                                    <bdo Debug="0" AlertError="0" Layout_Url="/plm/jsp/project/schedule/ChangeProjectScheduleGridLayout.jsp"
                                        Layout_Method="POST" Layout_Param_Screenwidth="<%= screenWidth %>"
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
                                        Layout_Param_Scheduletypeenumkey="<%= scheduleTypeEnumKey %>"
                                        Layout_Param_Scheduletypeenum="<%= scheduleTypeEnum %>"
                                        Layout_Param_Prioritycontrolenumkey="<%= priorityControlEnumKey %>"
                                        Layout_Param_Prioritycontrolenum="<%= priorityControlEnum %>"
                                        Layout_Param_MainScheduleCodeenumkey="<%= mainScheduleCodeEnumKey %>"
                                        Layout_Param_MainScheduleCodeenum="<%= mainScheduleCodeEnum %>"
                                        Layout_Param_Debugsubenumkey="<%= debugSubEnumKey %>"
                                        Layout_Param_Debugsubenum="<%= debugSubEnum %>" Layout_Param_Debugnenumkey="<%= debugNEnumKey %>"
                                        Layout_Param_Debugnenum="<%= debugNEnum %>" Layout_Param_Debugtaskenumkey="<%= debugTaskEnumKey %>"
                                        Layout_Param_Debugtaskenum="<%= debugTaskEnum %>"
                                        Layout_Param_Ganttchartmaxstart="<%= ganttChartMaxStart %>"
                                        Layout_Param_Ganttchartminend="<%= ganttChartMinEnd %>" Layout_Param_Oid="<%= oid %>"
                                        Data_Url="/plm/jsp/project/schedule/ChangeProjectScheduleGridData.jsp" Data_Method="POST"
                                        Data_Param_Resulthead="<%= tgDataHead %>" Data_Param_Resulthead1="<%= tgDataHead1 %>" Data_Param_Resultbody="<%= tgDataBody %>"
                                        Upload_Url="/plm/servlet/e3ps/ProjectScheduleServlet?cmd=save" Upload_Method="POST"
                                        Upload_Format="Internal" Upload_Data="TGData" Upload_Flags="AllCols" Upload_Type="All"
                                        Upload_Param_Oid="<%= oid %>" Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                                        Export_Data="TGData" Export_Param_File="Change_Project_Schedule"></bdo>
                                </div>
                            </div>
                        </div>
                    </div> <!-- EJS TreeGrid End -->
                </td>
            </tr>
        </table>
    </form>
</body>
</html>