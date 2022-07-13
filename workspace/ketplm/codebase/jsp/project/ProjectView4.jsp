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
            e3ps.project.historyprocess.HistoryHelper,
            e3ps.project.outputtype.OEMPlan,
            e3ps.project.outputtype.OEMProjectType,
            e3ps.sap.*,
            e3ps.wfm.entity.KETWfmApprovalMaster
            " %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<!-- [START] [PLM System 1차개선] 일정변경 function 호출을 위한 Include, 2013-08-06, BoLee -->
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<!-- [END] [PLM System 1차개선] 일정변경 function 호출을 위한 Include, 2013-08-06, BoLee -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>

<%
  String popup = StringUtil.checkNull( request.getParameter("popup") );

  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  E3PSProjectData projectData = new E3PSProjectData(project);

  String teamType = "";
  if("자동차 사업부".equals(projectData.teamType)) {
    teamType = "자동차";
  }else {
    teamType = "전자";
  }
  boolean isCS = CommonUtil.isMember("공정감사");
  String projectStartDate = "";
  if(projectData.pjtExecStartDate != null){
    projectStartDate = DateUtil.getDateString(projectData.pjtExecStartDate, "D");
  }else{
    projectStartDate = DateUtil.getDateString(projectData.pjtPlanStartDate, "D");
  }

  String projectEndDate = "";
  if(projectData.pjtExecEndDate != null){
    projectEndDate = DateUtil.getDateString(projectData.pjtExecEndDate, "D");
  }else{
    projectEndDate = DateUtil.getDateString(projectData.pjtPlanEndDate, "D");
  }
  int pjtDuratoin = 0;
  if(projectData.pjtExecStartDate != null){
    pjtDuratoin = DateUtil.getDuration(projectData.pjtExecStartDate, DateUtil.getCurrentTimestamp());
  }
  if(projectData.pjtExecEndDate != null){
    pjtDuratoin = DateUtil.getDuration(projectData.pjtExecStartDate, projectData.pjtExecEndDate);
  }

%>

<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">
<!--

  function viewTotalProgress() {
    var url = "/plm/jsp/project/TotalProgress.jsp?oid=<%=oid%>";
    openSameName(url,"870","650","status=no,scrollbars=yes,resizable=yes");
  }

  function viewProjectPopUp(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
    openSameName(url,"1080","768","status=no,scrollbars=yes,resizable=no");
  }

  function viewChart(){
    var url = "/plm/jsp/project/gantt/Gantt.jsp?oid=<%=oid%>";
    openSameName(url,"1080","768","status=no,scrollbars=no,resizable=yes");
  }

  function printChart(){
    scr_width = screen.availWidth;
    scr_height = screen.availHeight;
    var url = "/plm/jsp/project/gantt/Gantt_print.jsp?oid=<%=oid%>";
    openSameName(url, scr_width, scr_height, "status=no,scrollbars=yes,resizable=no");
  }
//-->
</script>
</head>
<body>
<input type='hidden' name='popup' value="<%=popup %>">
<table width="820" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
    <table width="820" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="820" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551") %><%--제품 프로젝트 상세정보--%></td>
<%if(popup == null){ %>
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02401") %><%--자동차사업부--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03117") %><%--프로젝트상세정보--%></td>
                <%} %>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5"></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_4.png"></td>
              <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="ProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></a></td>
              <td><img src="../../portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_4.png"></td>
              <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="ProjectView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02328") %><%--인원 / Issue--%></a></td>
              <td><img src="../../portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <%if(!isCS){ %>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_4.png"></td>
              <td background="../../portal/images/tab_6.png" class="btn_tab"><a href="ProjectView3.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%></a></td>
              <td><img src="../../portal/images/tab_5.png""></td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="../../portal/images/tab_1.png"></td>
              <td background="../../portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%></td>
              <td><img src="../../portal/images/tab_2.png"></td>
            </tr>
          </table></td>
          <%} %>

        </tr>
      </table>
      <table width="820" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_9.gif"></td>
          <td height="10" background="../../portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="../../portal/images/box_13.gif">&nbsp;</td>
          <td valign="top"><!--내용-->
            <table width="800" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0" >
                  <tr>
                    <td width="20"><img src="../../portal/images/icon_4.png"></td>
                    <td width="70" class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02736") %><%--진행현황--%></td>
                    <td width="275">[<%=messageService.getString("e3ps.message.ket_message", "00404") %><%--Project 일정--%> : <%=projectStartDate %> ~ <%=projectEndDate %>] &nbsp; <%=pjtDuratoin%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                    <td width="415" align="right"><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="15" background="/plm/portal/images/stick_red.gif"></td>
                          <td><b>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></b></td>
                          <td width="10"></td>
                          <td width="15" background="/plm/portal/images/stick_green.gif"></td>
                          <td><b>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></b></td>
                        </tr>
                      </table></td>
                        <!-- [START] [PLM System 1차개선] [Chart] 버튼 제거 (주석 처리), 2013-06-20, BoLee -->
                        <!--
                        <td width="10"></td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:viewChart();" class="btn_blue">Chart</a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                        -->
                        <!-- [END] [PLM System 1차개선] [Chart] 버튼 제거 (주석 처리), 2013-06-20, BoLee -->
                        <!-- [START] [PLM System 1차개선] [일정Chart] 버튼 추가 - 버튼 클릭 시 Project 일정 조회 팝업 오픈, 2013-06-20, BoLee -->
                        <td width="10"></td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:openViewProjectSchedule('<%= oid %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02355") %><%--일정Chart--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                        <!-- [END] [PLM System 1차개선] [일정Chart] 버튼 추가 - 버튼 클릭 시 Project 일정 조회 팝업 오픈, 2013-06-20, BoLee -->
                      <!-- [START] [PLM System 1차개선] [Print] 버튼 제거 (주석 처리), 2013-06-20, BoLee -->
                      <!--
                      <td width="10"></td>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:printChart();" class="btn_blue">Print</a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                      -->
                        <!-- [END] [PLM System 1차개선] [Print] 버튼 제거 (주석 처리), 2013-06-20, BoLee -->
                      <%if("자동차".equals(teamType)){ %>
                      <td width="10"></td>
                      <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:viewTotalProgress();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02497") %><%--전체보기--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table></td>
                      <%} %>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td  class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                   <%if("자동차".equals(teamType)){ %>
                    <tr>
                      <td width="150" class="tdblueM" style="height:70"><%=messageService.getString("e3ps.message.ket_message", "02404") %><%--자동차일정--%></br>
                      <%
                      if(projectData.representModel.length()> 0){
                        out.println("(" + projectData.representModel +")");
                      }
                      %>
                      </td>
                      <td width="630" class="tdwhiteM0" style="height:70"><table width="630" style="height:70" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><table width="630" border="0" cellspacing="0" cellpadding="0" style="height:16" id="bar1">
                            <tr>
                              <td width="100%" background="/plm/portal/images/stick_gray.gif"></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table></td>
                    </tr>
                    <%} %>
                    <tr>
                      <td width="150" class="tdblueM" style="height:130"><%=messageService.getString("e3ps.message.ket_message", "02630") %><%--제품프로젝트--%><br>
                        <%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%><br>
                        <%=projectData.pjtCompletion%> / <%=projectData.getPreferCompStr()%></td>
                      <td width="630" class="tdwhiteM0" style="height:130"><table width="630" style="height:130" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><table width="630" border="0" cellspacing="0" cellpadding="0" style="height:16" id="bar2">
                            <tr>
                              <td width="<%=projectData.pjtCompletion%>%"
                              <% double perform = 0;
                                 try{
                                  perform = Double.parseDouble(projectData.getPreferCompStr());
                                 }catch(Exception ex){

                                 }
                              %>
                              <%if(projectData.pjtCompletion >= perform) {%>background="../../portal/images/stick_green.gif"
                              <%}else {%>background="../../portal/images/stick_red.gif"<%}%>
                              ></td>
                              <td width="<%=100-projectData.pjtCompletion%>%" background="../../portal/images/stick_gray.gif"></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td class="space15"></td>
                    </tr>
                </table>
                <table width="780" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20"><img src="../../portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02127") %><%--연관 Project(제품)--%></td>
                      <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td  class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
                      <td width="100" class="tdblueM">Project No</td>
                      <td width="100" class="tdblueM">Part No</td>
                      <td width="280" class="tdblueM">Project Name</td>
                      <td width="100" class="tdblueM">PM</td>
                      <td width="100" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00828") %><%--고객--%></td>
                    </tr>
            <%
              Hashtable hash = projectData.getDependencyProjects(project);
              Enumeration projectEnum = hash.elements();
          while(projectEnum != null && projectEnum.hasMoreElements()) {
            E3PSProjectData dependData = (E3PSProjectData)projectEnum.nextElement();


        %>
                    <tr>
                      <td class="tdwhiteM"><%=dependData.developenttypeName != null ? dependData.developenttypeName : ""%>&nbsp;</td>
                      <td class="tdwhiteM"><a href="#" onClick="javascript:viewProjectPopUp('<%=dependData.e3psPjtOID%>');" class="btn_blue"><%=dependData.pjtNo==null?"":dependData.pjtNo%></a>&nbsp;</td>
                      <td class="tdwhiteM"><%=dependData.partNo==null?"":dependData.partNo%>&nbsp;</td>
                      <td class="tdwhiteM"><%=dependData.pjtName==null?"":dependData.pjtName%>&nbsp;</td>
                      <td class="tdwhiteM"><%=dependData.pjtPmName%>&nbsp;</td>
                      <td class="tdwhiteM0" title="<%=dependData.customer%>"><div style="width:80;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr><%=dependData.customer%>&nbsp;</nobr></div></td>
                    </tr>
            <%
          }
        %>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
          </table>
        <%
        try{
          QueryResult qrChildDR = ProjectTaskHelper.manager.getChildDR(project);
        %>
                <table width="780" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                      <td width="20"><img src="../../portal/images/icon_4.png"></td>
                      <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00171") %><%--DR현황--%></td>
                      <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
        <table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td  class="tab_btm2"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td class="space5"><table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                  <td width="210" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00155") %><%--DR 단계--%></td>
                  <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                  <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02503") %><%--점수--%></td>
                  <td width="130" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02970") %><%--판정결과--%></td>
                </tr>
                <%
            while(qrChildDR.hasMoreElements()){

              Object[] taskObj = (Object[]) qrChildDR.nextElement();
              TemplateTask child = (TemplateTask) taskObj[0];
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

              e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(child);
              int drCheckPoint = 0;
              String drApprovalResult = "";
              if(drDocument != null) {
                drCheckPoint = drDocument.getDRCheckPoint();
                if(drDocument.getApprovalResult() != null) drApprovalResult = drDocument.getApprovalResult();
                if("승인".equals(drApprovalResult)) drApprovalResult = "GOOD";
                else if("반려".equals(drApprovalResult)) drApprovalResult = "<span class='red'>NG</span>";
              }
            %>
                  <tr>
                    <td width="210" class="tdwhiteM"><div style="width:190;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr><a href="#" class="btn_blue"><%=dependData.taskName%></a></nobr></div></td>
                    <td width="120" class="tdwhiteM">
                    <%if(dependData.taskExecEndDate == null){%><span class="gray"><%}%><%=childPlanEndStr%><%if(dependData.taskExecEndDate == null){%></span><%}%></td>
                    <td width="120" class="tdwhiteM"><%=childtaskExecEndDateStr%></td>
                    <td width="100" class="tdwhiteM"><%=dependData.drvalue%>&nbsp;</td>
                    <td width="100" class="tdwhiteM"><%=drCheckPoint == 0 ? "" : drCheckPoint%>&nbsp;</td>
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
      </table></td>
              </tr>
            </table></td>
          <td width="10" background="../../portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
          <td height="10" background="../../portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright<%if("popup".equals(popup)){ %>_t<%} %>.html" name="copyright" width="820" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>

<%
  ArrayList customerEventArr = new ArrayList();
  QueryResult opqr = OEMTypeHelper.getCustomerEvent(project.getOemType());
  int count = 0;

  if(opqr!=null && projectData.pjtPlanEndDate != null){
    while(opqr.hasMoreElements()){
      Object[] oo = (Object[])opqr.nextElement();
      OEMPlan op = (OEMPlan)oo[0];
      if(op.getOemEndDate() == null){
        continue;
      }
      Date oemEndDate = new Date(op.getOemEndDate().getTime());

      Date pjtStartDate = new Date(projectData.pjtPlanStartDate.getTime()-1);
      if(projectData.pjtExecStartDate != null) pjtStartDate = new Date(projectData.pjtExecStartDate.getTime()-1);
      Date pjtEndDate = new Date(projectData.pjtPlanEndDate.getTime()-1);
      if(projectData.pjtExecEndDate != null) pjtEndDate = new Date(projectData.pjtExecEndDate.getTime()-1);

      if( oemEndDate.after( pjtStartDate ) && oemEndDate.before( pjtEndDate ) ) {
        customerEventArr.add(op);

        double per = 0;

        if(projectData.pjtExecStartDate != null) {
          per = (DateUtil.getDuration(projectData.pjtExecStartDate, op.getOemEndDate()) + 1);
          if(projectData.pjtExecEndDate != null)
            per = per / (DateUtil.getDuration(projectData.pjtExecStartDate, projectData.pjtExecEndDate) + 1);
          else
            per = per / (DateUtil.getDuration(projectData.pjtExecStartDate, projectData.pjtPlanEndDate) + 1);
        }else {
          per = (DateUtil.getDuration(projectData.pjtPlanStartDate, op.getOemEndDate()) + 1);
          if(projectData.pjtExecEndDate != null)
            per = per / (DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtExecEndDate) + 1);
          else
            per = per / (DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtPlanEndDate) + 1);
        }
%>
<div id="stickLayerOne<%=count%>" style="position:absolute; z-index:1;" value="<%=per%>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="font_stick"><%=op.getOemType().getName()%></td>
    </tr>
    <tr>
      <td align="center"><img src="/plm/portal/icon/bar_arrow1.gif"><img src="/plm/portal/icon/bar_arrow2.gif"></td>
    </tr>
    <tr height="15">
      <td></td>
    </tr>
    <tr>
      <td align="center" class="font_stick"><%=DateUtil.getTimeFormat(op.getOemEndDate(), "yy/MM/dd")%></td>
    </tr>
  </table>
</div>
<%      count++;
      }
    }
  }

    %>

<%
  ArrayList milestoneArr = projectData.getMilestoneTasks();
    //if(td.getData() instanceof TemplateProject){
      /* TemplateProject 이다*/
      //continue;
    //}


  for(int i=0; i<milestoneArr.size(); i++) {
    double per = 0;
    E3PSTaskData taskData = new E3PSTaskData((E3PSTask)milestoneArr.get(i));
    String taskEndDate = DateUtil.getTimeFormat(taskData.taskPlanEndDate, "yy/MM/dd");

    if(projectData.pjtExecStartDate != null) {
      if(taskData.taskExecEndDate != null) {
        taskEndDate = DateUtil.getTimeFormat(taskData.taskExecEndDate, "yy/MM/dd");
        per = (DateUtil.getDuration(projectData.pjtExecStartDate, taskData.taskExecEndDate) + 1);
      }else {
        per = (DateUtil.getDuration(projectData.pjtExecStartDate, taskData.taskPlanEndDate) + 1);
      }

      if(projectData.pjtExecEndDate != null)
        per = per / (DateUtil.getDuration(projectData.pjtExecStartDate, projectData.pjtExecEndDate) + 1);
      else
        per = per / (DateUtil.getDuration(projectData.pjtExecStartDate, projectData.pjtPlanEndDate) + 1);
    }else {
      if(taskData.taskExecEndDate != null) {
        taskEndDate = DateUtil.getTimeFormat(taskData.taskExecEndDate, "yy/MM/dd");
        per = (DateUtil.getDuration(projectData.pjtPlanStartDate, taskData.taskExecEndDate) + 1);
      }else {
        per = (DateUtil.getDuration(projectData.pjtPlanStartDate, taskData.taskPlanEndDate) + 1);
      }

      if(projectData.pjtExecEndDate != null)
        per = per / (DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtExecEndDate) + 1);
      else
        per = per / (DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtPlanEndDate) + 1);
    }
%>
<div id="stickLayerTwoName<%=i%>" style="position:absolute; z-index:1;" value="<%=per%>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="font_stick" title="<%=taskData.taskName%>"><nobr style="width:100;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><%=taskData.taskName%></nobr></td>
    </tr>

  </table>
</div>
<div id="stickLayerTwo<%=i%>" style="position:absolute; z-index:1;" value="<%=per%>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="font_stick" ><nobr style="width:100;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"></nobr></td>
    </tr>
    <tr>
      <td align="center"><img src="/plm/portal/icon/bar_arrow1.gif"><img src="/plm/portal/icon/bar_arrow2.gif"></td>
    </tr>
    <tr height="15">
      <td></td>
    </tr>
    <tr>
      <td align="center" class="font_stick" title="<%=taskEndDate%>"><%=taskData.getStateStr2()%></td>
    </tr>
  </table>
</div>

<%}%>
</body>
</html>

<script>
  function showLayer(target, layer) {
    var layerElem = document.getElementById(layer);

    layerElem.style.display = "";
    layerElem.style.left = getDim(target).x - 50 + target.offsetWidth*eval(layerElem.value);
    layerElem.style.top = getDim(target).y - 25;
    layerElem.style.width = target.offsetWidth;
    layerElem.style.height = target.offsetHeight;
  }
  function showLayer2(target, layer, i) {
    var layerElem = document.getElementById(layer);
    var locationValue = 0;
    if(i%2 == 0){
      locationValue = 0;
    }else if(i%2 == 1){
      locationValue = 20;
    }

    layerElem.style.display = "";
    layerElem.style.left = getDim(target).x - 50 + target.offsetWidth*eval(layerElem.value);
    layerElem.style.top = getDim(target).y - 25 - locationValue;
    layerElem.style.width = target.offsetWidth;
    layerElem.style.height = target.offsetHeight;
  }



  // 좌표 계산
  function getDim(id) {
    var oTargetElem;
    if(typeof(id) == "string")
      oTargetElem = document.getElementById(id);
    else
      oTargetElem = id;
    var oElement = oTargetElem;
    var curleft = 0;
    var curtop = 0;

    if(oElement.offsetParent) {
      while(oElement.offsetParent) {
        curleft += ( oElement.offsetLeft - oElement.scrollLeft );
        curtop += ( oElement.offsetTop - oElement.scrollTop );
        oElement = oElement.offsetParent;
      }
    }

    return { x:curleft, y:curtop }
  }

  var customerEventCount = 0;
  customerEventCount = eval(<%=customerEventArr.size()%>);

  for(i=0; i<customerEventCount; i++) {
    showLayer(bar1, "stickLayerOne"+i);
  }

  var milestoneCount = 0;
  milestoneCount = eval(<%=milestoneArr.size()%>);

  for(i=0; i<milestoneCount; i++) {
    showLayer(bar2, "stickLayerTwo"+i);
    showLayer2(bar2, "stickLayerTwoName"+i, i);
  }



</script>
<%!
public void makeVector(ProjectTreeNode node, Vector vector){

  vector.add(node);
  for(int i = 0; i < node.getChildCount(); i++){
    makeVector((ProjectTreeNode)node.getChildAt(i), vector);
  }

}

%>
