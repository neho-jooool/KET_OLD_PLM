<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@page import = "java.util.*"%>
<%@page import="
            wt.fc.*,
            wt.folder.*,
            wt.lifecycle.*,
            wt.org.*,
            wt.part.*,
            wt.project.Role,
            wt.query.*,
            wt.session.*,
            wt.team.*,
            wt.vc.*,
            wt.vc.wip.*,
            wt.workflow.engine.WfActivity,
            wt.workflow.engine.WfProcess,
            wt.workflow.work.WorkItem"%>
<%@page import="
            e3ps.common.content.*,
            e3ps.common.jdf.config.Config,
            e3ps.common.jdf.config.ConfigImpl,
            e3ps.common.jdf.log.Logger,
            e3ps.common.query.*,
            e3ps.common.util.*,
            e3ps.groupware.company.*,
            e3ps.project.*,
            e3ps.project.beans.*,
            e3ps.common.web.*,
            e3ps.common.code.*,
            e3ps.common.util.KETStringUtil,
            e3ps.project.historyprocess.HistoryHelper,
            e3ps.project.outputtype.OEMPlan,
            e3ps.project.outputtype.OEMProjectType,
            e3ps.sap.*,
            e3ps.wfm.entity.KETWfmApprovalMaster,
            ext.ket.project.gate.entity.*,
            ext.ket.project.gate.entity.*,
            ext.ket.project.task.entity.*,
            ext.ket.project.task.service.*,
            wt.content.*,
            java.util.*,
            java.sql.*
            " %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

                 
<!-- MultiCombo Start -->
<%@include file="/jsp/common/multicombo.jsp" %>

<%
    //Department 가져오기
    e3ps.groupware.company.Department department = null;
    String departmentName = "";
    String gateDrTabName = "";
	//로그인 사용자 정보 (OID)
	try {
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    
        QueryResult qr = PersistenceHelper.manager.navigate(wtuser, "people", e3ps.groupware.company.WTUserPeopleLink.class);
        if (qr.hasMoreElements()) {
            e3ps.groupware.company.People people = (e3ps.groupware.company.People) qr.nextElement();
            department = people.getDepartment();
            departmentName = department.getName();
        }	
	}catch(Exception e){
        Kogger.error(e);
    }
	
/* 	if(departmentName!=null && "전자개발팀".equals(departmentName)) {
	    gateDrTabName = "DR";
	}else {
	    gateDrTabName = "Gate/DR";
	}
 */
   gateDrTabName = "평가관리";
 
   String oid = request.getParameter("oid");
   E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
   E3PSProjectData projectData = new E3PSProjectData(project);
   String popup = StringUtil.checkNull( request.getParameter("popup") );
   
   ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
   boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
   boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
   boolean isCS = CommonUtil.isMember("공정감사");
   
   
   List<GateTaskOutputDTO> projectTaskTotalList = ProjectTaskCompHelper.service.getProjectTaskTotalList(oid);

   List<GateTaskOutputDTO> gateTaskOutputList = ProjectTaskCompHelper.service.getProjectGateCheckSheetList(oid);
   
   if (gateTaskOutputList != null) {
       for (int i = 0; i < gateTaskOutputList.size(); i++) {
	       GateTaskOutputDTO gateTaskOutDto = gateTaskOutputList.get(i);
	       String taskOid = gateTaskOutDto.getTaskOid();
	       if (projectTaskTotalList != null) {
	           String genFirstStage = "";
	           for (int j = 0; j < projectTaskTotalList.size(); j++) {
		           GateTaskOutputDTO generalTaskImsi = projectTaskTotalList.get(j);
		           String genTaskOid = generalTaskImsi.getTaskOid();
		           String genTaskName = generalTaskImsi.getOutputTaskName();
		           String genLevel = generalTaskImsi.getOutputLevel();
		           if ("1".equals(genLevel)) {
		               genFirstStage = genTaskName;
		           }
		           if (taskOid != null && taskOid.equals(genTaskOid)) {
		               gateTaskOutDto.setTaskStageName(genFirstStage);
		           }
	           }
	       }
	       int gate_rev = ProjectTaskCompHelper.service.getMaxGateDegree("e3ps.project.E3PSTask:" + taskOid);
	       Hashtable haTotalResult = ProjectTaskCompHelper.service.getGateAssessResultInfoList("e3ps.project.E3PSTask:" + taskOid,false,gate_rev);
           E3PSTask task2 = (E3PSTask)CommonUtil.getObject("e3ps.project.E3PSTask:" + taskOid);
           if(task2!=null) {
    	       ExtendScheduleData schedule = (ExtendScheduleData) task2.getTaskSchedule().getObject();
               gateTaskOutDto.setResultPlanVal(schedule.getPlanEndDate().toString());  //계획
           }
	       gateTaskOutDto.setResultOutputVal( StringUtil.checkNull((String)haTotalResult.get("resultOutputVal"))); //산출물
	       gateTaskOutDto.setResultAssVal( StringUtil.checkNull((String)haTotalResult.get("resultAssVal")));       //평가항목
	       gateTaskOutDto.setResultDqmVal( StringUtil.checkNull((String)haTotalResult.get("resultDqmVal")));       //품질문제
	       gateTaskOutDto.setResultTotalVal( StringUtil.checkNull((String)haTotalResult.get("resultTotalVal")));   //종합평가
	       gateTaskOutDto.setResultTotalStr( StringUtil.checkNull((String)haTotalResult.get("resultTotalStr")));
	       gateTaskOutDto.setRev(Integer.toString(gate_rev)+"차");
	       String gateRsltOid = (String) haTotalResult.get("gateResultOid");
	       int recoveryPlanCnt = 0;
	       if (!StringUtil.isEmpty(gateRsltOid)) {
	           gateTaskOutDto.setResultOid("ext.ket.project.gate.entity.GateAssessResult:" + gateRsltOid);
	
	           GateAssessResult gateAssRsltObj = (GateAssessResult) CommonUtil
	               .getObject("ext.ket.project.gate.entity.GateAssessResult:" + gateRsltOid);
	
               if(gateAssRsltObj!=null) {
        	       String assRsltState = gateAssRsltObj.getLifeCycleState().getDisplay(Locale.KOREA);
        	       
                   String stateDate = WorkflowSearchHelper.manager.getLastApprovalDate(gateAssRsltObj);
        	       gateTaskOutDto.setResultExecVal(stateDate);  //실적
        	       gateTaskOutDto.setResultStateVal(assRsltState); //상태
     	           QueryResult qrRs = ContentHelper.service.getContentsByRole((ContentHolder) gateAssRsltObj, ContentRoleType.SECONDARY);
     	           recoveryPlanCnt = qrRs.size();
               }
	       }
	       gateTaskOutDto.setResultAttatchCnt("" + recoveryPlanCnt);
	       /* 
 */
       }
   }
   
   
   List<GateTaskOutputDTO> gateResultSummaryList = gateTaskOutputList;
%>


<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->


<script type="text/javascript">
<!--
function openCostHistory(oid){
    var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
    openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
}
$(document).ready(function(){

//     CommonUtil.tabs('tabs');
});

function excelDown(){
    location.href = "/plm/jsp/project/CFTRoleExcelDown.jsp?oid=<%=oid%>";
}

function excelUp() {
    var url = "/plm/jsp/project/CFTRoleUp.jsp?oid=<%=oid%>";
    openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");

}


function gateResultAttatch(gateAssResultOid) {
	var url = "/plm/ext/project/task/updateGateResultAttatchForm.do?oid="+gateAssResultOid;
	openOtherName(url,"GateResultAttatchPopup","750","550","status=no,scrollbars=auto,resizable=no");
}

function gateViewPopup(taskOid) {
	
    var url = "/plm/jsp/project/TaskGateView.jsp?oid=e3ps.project.E3PSTask:"+taskOid+"&popup=popup";
    openOtherName(url,"GateResultAttatchPopup","980","650","status=no,scrollbars=auto,resizable=no");
}

function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+'&popup=popup', '',1150,800);
    //openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=ext.ket.project.gate.entity.AssessSheet:100000231419&popup=popup', '',1150,800);
    //openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=ext.ket.project.gate.entity.GateAssessResult:100000361325&popup=popup', '',1150,800);
}

//-->
</script>

<form name="ViewProjectDRSearchForm" method="post">
<input type='hidden' name='popup' value="<%=popup %>">
<input type='hidden' name='command' value="">
<input type='hidden' name='oid' value="<%=oid%>">

<div style="margin:0 10px">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551") %><%--제품 프로젝트 상세정보--%></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="5"></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png"></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02327") %><%--인원--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <%if(!isCS){ %>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <%} %>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><img src="/plm/portal/images/tab_4.png"></td>
                        <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                            href="/plm/jsp/project/ProjectExtView10.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">Issue</a></td>
                        <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><img src="/plm/portal/images/tab_4.png"></td>
                        <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                            href="/plm/jsp/project/ProjectExtView5.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">CFT요청</a></td>
                        <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                          <td><img src="/plm/portal/images/tab_4.png"></td>
                          <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                              href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>&popup=<%=popup%>"
                              class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01381")%><%--목표--%></a></td>
                          <td><img src="/plm/portal/images/tab_5.png""></td>
                      </tr>
                  </table></td>
        
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_1.png"></td>
                      <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09500") %><%--평가관리--%></td>
                      <td><img src="/plm/portal/images/tab_2.png""></td>
                    </tr>
                  </table></td>
                  
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/plm/portal/images/tab_4.png"></td>
                      <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a href="/plm/jsp/project/ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "03034") %><%--프로그램--%></a></td>
                      <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                  </table></td>
<%--                   <%if(CommonUtil.isAdmin() || CommonUtil.isMember("원가_임원")){%>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                href="/plm/jsp/project/ProjectExtView8.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">원가</a></td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                    </table></td>
                  <%} %> --%>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr><td><img src="/plm/portal/images/tab_4.png"></td>
                        <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                        <a href="/plm/jsp/project/ProjectExtView9.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">ATFT</a>
                        </td>
                        <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                    </table></td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:openCostHistory('<%=oid %>')" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09518") %><%--개발원가--%></a></td>
                                <td><img src="/plm/portal/images/tab_5.png""></td>
                            </tr>
                        </table>
                    </td>
                </tr>
              </table>
           </td>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                            href="#" onClick="javascript:top.close();"
                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                </table>
            </td>    
        </tr>
      </table> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top"><!--내용-->
            <table width="100%" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">
                  
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                      <td class="font_03">Gate 현황
                      </td>
                      <td align="right">
                        
                      </td>
                    </tr>
                  </table>
                  
                    
	            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td class="space10"></td>
	              </tr>
	            </table>
          
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                      <td  class="tab_btm2"></td>
                    </tr>
                </table>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td width="8%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
<%--                     <td width="14%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01190") %>단계</td> --%>
                    <td width="10%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                    <td width="10%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
                    <td width="8%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                    <td width="8%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
	                <td width="9%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%></td>
	                <td width="10%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03027") %><%--품질문제--%></td>
                    <td width="10%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07133") %><%--종합평가--%></td>
                    <td width="5%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07125") %><%--차수--%></td>
	                <td width="8%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07148") %><%--최종평가--%></td>
	                <td width="14%" class="tdblueM">Recovery Plan<%--Recovery Plan--%></td>
	              </tr>
<%
	if(gateResultSummaryList != null) 
	{
	    for(int i=0;i<gateResultSummaryList.size();i++) 
	    {
		      GateTaskOutputDTO gateTaskOutDto = gateResultSummaryList.get(i);
		      
		      String planDateVal = gateTaskOutDto.getResultPlanVal();
              if(!StringUtil.isEmpty(planDateVal) && planDateVal.length()>10) planDateVal = planDateVal.substring(0,10);
              String execDateVal = gateTaskOutDto.getResultExecVal();
              if(!StringUtil.isEmpty(execDateVal) && execDateVal.length()>10) execDateVal = execDateVal.substring(0,10);
%>
	              <tr>
	                <td width="8%" class="tdwhiteM"><a href="javascript:viewProject('e3ps.project.E3PSTask:<%=gateTaskOutDto.getTaskOid() %>')"><%=gateTaskOutDto.getOutputTaskName() %></a></td>
<%-- 	                <td width="14%" class="tdwhiteM">&nbsp;<%=gateTaskOutDto.getTaskStageName() %></td> --%>
                    <td width="10%" class="tdwhiteM">&nbsp;<%=planDateVal %></td>   <%--계획 --%>
                    <td width="10%" class="tdwhiteM">&nbsp;<%=execDateVal %></td>     <%--실적 --%>
                    <td width="8%" class="tdwhiteM">&nbsp;<%=gateTaskOutDto.getResultStateVal() %></td>   <%--상태 --%>
                    <td width="8%" class="tdwhiteM">&nbsp;<%=gateTaskOutDto.getResultOutputVal() %></td>
                    <td width="9%" class="tdwhiteM">&nbsp;<%=gateTaskOutDto.getResultAssVal() %></td>
	                <td width="10%" class="tdwhiteM">&nbsp;<%=gateTaskOutDto.getResultDqmVal() %></td>
                    <td width="10%" class="tdwhiteM">&nbsp;<%=gateTaskOutDto.getResultTotalVal() %></td>
                    <td width="5%" class="tdwhiteM">&nbsp;<%=gateTaskOutDto.getRev() %></td>
	                <td width="8%" class="tdwhiteM">&nbsp;
                      <% 
                         if(!StringUtil.isEmpty(gateTaskOutDto.getResultOid())) {
                      %>
	                      <% if("G".equals(gateTaskOutDto.getResultTotalStr())) { %>
	                          <img src="/plm/extcore/image/ico_green.png" />
	                      <% }else if("Y".equals(gateTaskOutDto.getResultTotalStr())) { %>
	                          <img src="/plm/extcore/image/ico_yellow.png" />
	                      <% }else if("R".equals(gateTaskOutDto.getResultTotalStr())) { %>
	                          <img src="/plm/extcore/image/ico_red.png" />
	                      <% } %>
                      <% } %>
    
	                </td>
	                <td width="14%" class="tdwhiteM">&nbsp;
	                <%
	                String rstAttCntStr = StringUtil.checkNull(gateTaskOutDto.getResultAttatchCnt()).trim();
	                if(StringUtil.isEmpty(rstAttCntStr) || "0".equals(rstAttCntStr)) {
	                %>
                    -
                    <%
	                }else {
                    %>
	                    <a href="JavaScript:gateResultAttatch('<%="e3ps.project.E3PSTask:"+gateTaskOutDto.getTaskOid() %>');"><%=gateTaskOutDto.getResultAttatchCnt() %></a>
                    <%
                    }
                    %>
	                </td>
	              </tr>
<%
	    }
	}
%>
	            </table>
                  
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="right">
                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                          <td class="space5"></td>
                        </tr>
                      </table>
                      <!-- EJS TreeGrid Start -->
                      <div class="content-main">
                        <div class="container-fluid">
                          <div class="row-fluid">
                            <div id="listAssessGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                          </div>
                        </div>
                      </div>
                      <!-- EJS TreeGrid Start -->
                    </td>
                  </tr>
                </table>
        
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
                
		        <%
	        if(!"전자개발팀".equals(departmentName)) {
		        try{
		          QueryResult qrChildDR = ProjectTaskHelper.manager.getChildDR(project);
		        %>
			        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
			            <tr>
			              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
			              <td class="font_03">단계별 점검 현황</td>
			              <td align="right">&nbsp;</td>
			            </tr>
			        </table>
			        <table border="0" cellspacing="0" cellpadding="0" width="100%">
			          <tr>
			            <td class="space5"></td>
			          </tr>
			        </table>
			        <table border="0" cellspacing="0" cellpadding="0" width="100%">
			            <tr>
			              <td  class="tab_btm2"></td>
			            </tr>
			        </table>
			        <table border="0" cellspacing="0" cellpadding="0" width="100%">
			            <tr>
			              <td class="space5"><table border="0" cellspacing="0" cellpadding="0" width="100%">
			                <tr>
			                  <td width="210" class="tdblueM">구분</td>
			                  <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
			                  <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
			                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
			                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02503") %><%--점수--%></td>
			                  <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07125") %><%--차수--%></td>
			                  <td width="130" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02970") %><%--판정결과--%></td>
			                </tr>
			                <%
			                String rev = "";
			            while(qrChildDR.hasMoreElements()){
			        	    int drCheckPoint = 0;
		                    int drTarget = 0;
		                    String drApprovalResult = "";
		                          
			                 Object[] taskObj = (Object[]) qrChildDR.nextElement();
			                 TemplateTask child = (TemplateTask) taskObj[0];
			                 String taskOid = CommonUtil.getOIDString(child);
			              
			                 List<AssessTaskResultDTO>  taskAssessResultList = ProjectTaskCompHelper.service.getTaskAssessResultList(taskOid);
			              
				              if(taskAssessResultList.size() > 0){
				        	      for(int i=0;i<taskAssessResultList.size();i++) {
				        		      AssessTaskResultDTO gDto = taskAssessResultList.get(i);
	                                  if(StringUtils.isNotEmpty(gDto.getAssResult())){
	                                      rev = gDto.getRev()+"차";
	                                      drApprovalResult = gDto.getAssResult();
	                                      drCheckPoint     = Integer.parseInt(gDto.getResultScore());
	                                  }
	                              }
		                      }        
			              
			              E3PSTaskData dependData = new E3PSTaskData( (E3PSTask)child);
			
			              String childtaskExecStartDateStr = "&nbsp;";
			              String childtaskExecEndDateStr = "&nbsp;";
			
			              String childPlanStartStr = DateUtil.getDateString(dependData.taskPlanStartDate, "D");
			              String childPlanEndStr = DateUtil.getDateString(dependData.taskPlanEndDate, "D");
			
			              if(dependData.taskExecStartDate != null){
			                childtaskExecStartDateStr = DateUtil.getDateString(dependData.taskExecStartDate, "D");
			              }
			              if(dependData.taskExecEndDate != null){
			                childtaskExecEndDateStr = DateUtil.getDateString(dependData.taskExecEndDate, "D");
			              }
			              
			              if(StringUtils.isEmpty(drApprovalResult)){
			        	          e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(child);
		                          
		                          if(drDocument != null) {
		                            drCheckPoint = drDocument.getDRCheckPoint();
		                            drTarget = (dependData!=null && !StringUtil.isEmpty(dependData.drvalue))?Integer.parseInt(dependData.drvalue):0;
		                            if(drCheckPoint>0 && drCheckPoint>=drTarget) drApprovalResult = "GOOD";
		                            else if(drCheckPoint<drTarget) drApprovalResult = "<span class='red'>NG</span>";
//		                          if(drDocument.getApprovalResult() != null) drApprovalResult = drDocument.getApprovalResult();
//		                          if("승인".equals(drApprovalResult)) drApprovalResult = "GOOD";
//		                          else if("반려".equals(drApprovalResult)) drApprovalResult = "<span class='red'>NG</span>";
		                          }
			              }else{
			        	    if(drApprovalResult.equals("NG")){
			        		   drApprovalResult = "<span class='red'>NG</span>";
			        	    }
			        	    if(drApprovalResult.equals("NONE")){
			        		   drApprovalResult = " ";
			        	    }
			              }
			              
			              
			                      
		                          
			            %>
			                  <tr>
			                    <td width="210" class="tdwhiteM"><div style="width:190;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
			            <nobr><a href="#" class="btn_blue"><%=dependData.taskName%></a></nobr></div></td>
			                    <td width="120" class="tdwhiteM">
			                    <%if(dependData.taskExecEndDate == null){%><span class="gray"><%}%><%=childPlanEndStr%><%if(dependData.taskExecEndDate == null){%></span><%}%></td>
			                    <td width="120" class="tdwhiteM"><%=childtaskExecEndDateStr%></td>
			                    <td width="100" class="tdwhiteM"><%=dependData.drvalue%>&nbsp;</td>
			                    <%-- <td width="100" class="tdwhiteM"><%=drCheckPoint == 0 ? "" : drCheckPoint%>&nbsp;</td> --%>
			                    <td width="100" class="tdwhiteM"><%=drCheckPoint%>&nbsp;</td>
			                    <td width="100" class="tdwhiteM"><%=rev%>&nbsp;</td>
			                    <td width="130" class="tdwhiteM0"><%=drApprovalResult%>&nbsp;</td>
			                  </tr>
			                <%
			            }
			            %>
			              </table>
			        <%
			        }catch(Exception ee){
			            Kogger.error(ee);
			        }
			        %>
			            </td>
			        </tr>
			      </table>                  
                    <%
                }
                    %>
                  
                </td>
              </tr>
            </table>
          </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
</form>
