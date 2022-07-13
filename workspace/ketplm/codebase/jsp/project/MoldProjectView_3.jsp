<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.beans.ProductProjectHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.util.Map"%>
<%@page import="ext.ket.sysif.sap.service.SapService"%>
<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.beans.MoldProjectHelper"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.beans.DebugingData"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.CostInfo"%>
<%@page import="e3ps.project.sap.ProductPrice"%>
<%@page import="e3ps.project.sap.SAPMoldPrice"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.beans.ProjectOutputHelper"%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="wt.enterprise.RevisionControlled"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String oid = request.getParameter("oid");

  String popup = "";
  if(request.getParameter("popup") != null && request.getParameter("popup").length() > 0){
    popup = request.getParameter("popup");
  }

  MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);
  MoldItemInfo moldInfo = moldProject.getMoldInfo();
  String making = moldInfo.getMaking();
  boolean isMaking = false;

  if("사내".equals(making)){
    isMaking = true;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
}
-->
</style>
<%@include file="/jsp/common/processing.html" %>
<script language="JavaScript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function goView(no){
  if(no == 1){
    //showProcessing();
    location.href = "/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>";
  }else{
    //showProcessing();
    location.href = "/plm/jsp/project/MoldProjectView_" + no + ".jsp?oid=<%=oid%>&popup=<%=popup%>";
  }
}

function viewProject(oid){
  openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
}
//-->
</script>
</head>
<body>
<%!
  public String getSchdule2(E3PSTask task){

    if(task == null){
      return "&nbsp;";
    }

    boolean isCompleted = false;

    ExtendScheduleData data = (ExtendScheduleData)task.getTaskSchedule().getObject();

    String date = "";

    if(data.getExecStartDate() != null){
      isCompleted = true;
      date = DateUtil.getDateString(data.getExecStartDate(), "d");
    }else{
      //data.getPlanStartDate();
      date = DateUtil.getDateString(data.getPlanStartDate(), "d");
    }

    String color = "#B4B4B4";

    if(isCompleted){
      color = "black";
    }else{
      color = "#B4B4B4";
    }

    return "<FONT color='" + color +"'>" + date + "</FONT>";

  }
  public String getSchdule3(E3PSTask task){

    if(task == null){
      return "&nbsp;";
    }

    boolean isCompleted = false;

    ExtendScheduleData data = (ExtendScheduleData)task.getTaskSchedule().getObject();

    String date = "";

    if(data.getExecStartDate() != null){
      isCompleted = true;
      date = DateUtil.getDateString(data.getExecStartDate(), "d");
    }else{
      //data.getPlanStartDate();
      date = DateUtil.getDateString(data.getPlanStartDate(), "d");
    }
    return date;

  }
  public String getSchdule(E3PSTask task){

    if(task == null){
      return "&nbsp;";
    }

    boolean isCompleted = false;

    ExtendScheduleData data = (ExtendScheduleData)task.getTaskSchedule().getObject();

    String date = "";

    if(data.getExecEndDate() != null){
      isCompleted = true;
      date = DateUtil.getDateString(data.getExecEndDate(), "d");
    }else{
      //data.getPlanEndDate();
      date = DateUtil.getDateString(data.getPlanEndDate(), "d");
    }

    String color = "#B4B4B4";

    if(isCompleted){
      color = "black";
    }else{
      color = "#B4B4B4";
    }

    return "<FONT color='" + color +"'>" + date + "</FONT>";

  }

  public String getSchdule(ProjectOutput output){

    if(output == null){
      return "&nbsp;";
    }
    E3PSTask task = (E3PSTask)output.getTask();
    boolean isCompleted = false;

    ExtendScheduleData data = (ExtendScheduleData)task.getTaskSchedule().getObject();

    String date = "";

    if(data.getExecEndDate() != null){
      isCompleted = true;
      date = DateUtil.getDateString(data.getExecEndDate(), "d");
    }else{
      //data.getPlanEndDate();
      date = DateUtil.getDateString(data.getPlanEndDate(), "d");
    }

    String color = "#B4B4B4";


    RevisionControlled doc = ProjectOutputHelper.manager.getDocMasterForOutput(output);
    if(doc != null){
      isCompleted = true;
      date = DateUtil.getDateString(doc.getPersistInfo().getCreateStamp(),"d");
    }

    if(isCompleted){
      color = "black";
    }else{
      color = "#B4B4B4";
    }



    return "<FONT color='" + color +"'>" + date + "</FONT>";
  }

  public String getMoldeSchdule(MoldProject project, String taskName)throws Exception{

    E3PSTask task = MoldProjectHelper.getTask(project, taskName);
    return getSchdule(task);

  }

  public int getExDuration(E3PSTask task){
    if(task == null){
      return 0;
    }
    ExtendScheduleData data = (ExtendScheduleData)task.getTaskSchedule().getObject();
    int result = 0;
    if(data.getExecStartDate() != null){
      Timestamp end = data.getExecEndDate();
      if(end == null){
        Calendar c = Calendar.getInstance();
        end = new Timestamp(c.getTimeInMillis());
      }
      result = DateUtil.getDuration(data.getExecStartDate(), end) + 1;
    }
    return result;
  }

%>


<%
  ProductProject project = moldProject.getMoldInfo().getProject();

  boolean isProto = false;
  if(project.getProcess() != null && "Proto".equals(project.getProcess().getName() ) ){
    isProto = true;
  }

  E3PSTask task = MoldProjectHelper.getLikeTask(project, "DR1");
  //Kogger.debug("task1 == " + task);
  String schdule1 = getSchdule(task);

  //Kogger.debug("task111 === " + schdule1);
  String taskOid1 = CommonUtil.getOIDString(task);
  if(taskOid1 == null){
    taskOid1 = "";
  }

  task = MoldProjectHelper.getLikeTask(project, "DR2");
  String schdule2 = getSchdule(task);

  String taskOid2 = CommonUtil.getOIDString(task);
  if(taskOid2 == null){
    taskOid2 = "";
  }

  task = MoldProjectHelper.getLikeTask(moldProject, "제품도출도");
  String schdule3 = getSchdule(task);

  String taskOid3 = CommonUtil.getOIDString(task);
  if(taskOid3 == null){
    taskOid3 = "";
  }

  String schdule4 = "&nbsp";
  String schdule5 = "&nbsp";


  task = MoldProjectHelper.getLikeTask(moldProject, "금형설계");
  if(task != null){
    ProjectOutput output1 = ProjectOutputHelper.manager.getLikeTaskOutput(task, "금형제작시방서");
    ProjectOutput output2 = ProjectOutputHelper.manager.getLikeTaskOutput(task, "Lay-Out협의서");
    schdule4 = getSchdule(output1);
    schdule5 = getSchdule(output2);
  }
  task = MoldProjectHelper.getLikeTask(moldProject, "외주금형제작");
  if(task != null){
    ProjectOutput output1 = ProjectOutputHelper.manager.getLikeTaskOutput(task, "금형제작시방서");
    ProjectOutput output2 = ProjectOutputHelper.manager.getLikeTaskOutput(task, "Lay-Out협의서");
    schdule4 = getSchdule(output1);
    schdule5 = getSchdule(output2);
  }



  String taskOid4 = CommonUtil.getOIDString(task);
  if(taskOid4 == null){
    taskOid4 = "";
  }
  String taskOid5 = CommonUtil.getOIDString(task);
  if(taskOid5 == null){
    taskOid5 = "";
  }

  task = MoldProjectHelper.getLikeTask(project, "초도품제출");
  String schdule6 = getSchdule(task);
  String taskOid6 = CommonUtil.getOIDString(task);
  if(taskOid6 == null){
    taskOid6 = "";
  }

  task = MoldProjectHelper.getLikeTask(moldProject, "제품합격");
  String schdule7 = getSchdule(task);
  String taskOid7 = CommonUtil.getOIDString(task);
  if(taskOid7 == null){
    taskOid7 = "";
  }

  task = MoldProjectHelper.getLikeTask(moldProject, "금형검수");
  String schdule8 = "&nbsp;";
  if(task != null){
    //ProjectOutput output1 = ProjectOutputHelper.manager.getLikeTaskOutput(task, "신규제작금형Check Sheet");
    schdule8 = getSchdule2(task);
  }

  String taskOid8 = CommonUtil.getOIDString(task);
  if(taskOid8 == null){
    taskOid8 = "";
  }

  task = MoldProjectHelper.getLikeTask(moldProject, "금형이관");
  String schdule9 = getSchdule(task);

  String taskOid9 = CommonUtil.getOIDString(task);
  if(taskOid9 == null){
    taskOid9 = "";
  }

  String debugingTNames = "";
  String taskNames[] = null;

  Vector debugingNames = new Vector();

  if("사내".equals(making)){
    debugingTNames = ConfigImpl.getInstance().getString("debgingName_in");
    //taskNames = new String[]{"제품도", "금형설계", "금형부품", "금형조립","금형 Try","검사 의뢰", "검사 통보", "제품 검토 협의"};
  taskNames = new String[]{"제품도출도", "금형설계", "금형부품", "금형조립", "금형Try_[T0]", "제품측정[개발품_초도]", "제품검토협의[개발품_초도]"};

  }else{
    debugingTNames = ConfigImpl.getInstance().getString("debgingName_out");

  taskNames = new String[]{"제품도출도", "외주금형제작", "외주금형Try_[금형점검]", "금형Try_[T0]", "제품측정[개발품_초도]", "제품검토협의[개발품_초도]"};

  //taskNames = new String[]{"제품도", "외주 금형 수정", "금형 Try", "검사 의뢰", "검사 통보", "제품 검토 협의"};
  }

  //debgingName_in=제품도;금형설계;금형부품;금형조립;금형 Try;검사 의뢰;제품 측정;제품 검토 협의
  //debgingName_out=제품도;외주 금형 수정;금형 Try;검사 의뢰;제품 측정;제품 검토 협의

  StringTokenizer st = new StringTokenizer(debugingTNames, ";");

  while(st.hasMoreElements()){
    debugingNames.add(st.nextToken());
  }
  if(!isMaking){
    debugingNames.add(2, "pass");
  }

  Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);


  for(int i = 0; i < debugDatas.size(); i++){
    DebugingData dd = (DebugingData)debugDatas.get(i);
    //Kogger.debug("dd.nCha = " + dd.nCha);
  }

  java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();

  Vector initDesignV = new Vector(); //초도 태스크;

  Vector totalDurationV = new Vector();
  int initTodalDuration = 0;
  for(int i = 0; i < taskNames.length; i++){
      String taskName = taskNames[i];

      E3PSTask eTask = MoldProjectHelper.getTask(moldProject, taskName);
      //Kogger.debug("taskName = " + taskName + " eTask = " + eTask);
      initDesignV.add(eTask);
      int duration = getExDuration(eTask);
      totalDurationV.add(duration);
      initTodalDuration += duration;
  }
  totalDurationV.add(initTodalDuration);
  int debuginSunsu = 0 ;
  for(int i = 0; i < debugDatas.size(); i++){
    DebugingData dd = (DebugingData)debugDatas.get(i);
    Hashtable taskHash = dd.datas;

        int dtotal = 0;
        int reqDuration = 0; //검사의뢰
        int index = 0;
        E3PSTask nchaTask = dd.nchaTask;
        if(nchaTask != null){
          E3PSTaskData nchaDate = new E3PSTaskData(nchaTask);
          if(nchaDate.taskExecStartDate != null && nchaDate.taskExecEndDate  != null) {
        debuginSunsu = DateUtil.getDuration(nchaDate.taskExecStartDate, nchaDate.taskExecEndDate)+1;
      }
        }
        E3PSTaskData debuginTaskDatas = null;
        E3PSTaskData debuginTaskDatae = null;



        for(int j = 0; j < debugingNames.size(); j++){

          String dtaskName = (String)debugingNames.get(j);
          if(dtaskName.equals("pass")){
        index++;
        continue;
      }

          E3PSTask eTask = (E3PSTask)taskHash.get(dtaskName);

          int duration = getExDuration(eTask);
          dtotal += duration;
          if(dtaskName.equals("검사 의뢰") || dtaskName.equals("검사의뢰")){
            reqDuration = duration;
            continue;
          }
          if(dtaskName.equals("제품 측정") || dtaskName.equals("제품측정")){
            duration += reqDuration;
          }

          Integer integer = (Integer)totalDurationV.get(index);
          int totald = integer.intValue() + duration;
          totalDurationV.remove(index);
          totalDurationV.add(index, totald);
          index++;
        }
        //Kogger.debug("########################################### index " + index);
        //Kogger.debug("########################################### totalDurationV.get(index) " + totalDurationV.get(index));
        Integer integer = (Integer)totalDurationV.get(index);
      int totald = integer.intValue() + dtotal;
      totalDurationV.remove(index);
        totalDurationV.add(index, totald);
  }
  String totaldu = "-";
  String totaldusunsu = "";
  String totalduMain = "-";
  int durPlan1 = 0;
  int durPlan2 = 0;
    if(totalDurationV.size() > 0){

      Integer integer = (Integer)totalDurationV.get(totalDurationV.size() - 1) ;
      totaldu = nf.format(integer.intValue());
      E3PSTask etStart = null;
      E3PSTask etEnd = null;

      etStart = MoldProjectHelper.getLikeTask(moldProject, "제품도출도");
      etEnd = MoldProjectHelper.getLikeTask(moldProject, "제품검토협의[개발품_초도]");
      if(etStart != null && etEnd != null){
        E3PSTaskData etsData = new E3PSTaskData(etStart);
        E3PSTaskData eteData = new E3PSTaskData(etEnd);
        if(etsData.taskExecStartDate != null && eteData.taskExecEndDate  != null) {
         durPlan1 = DateUtil.getDuration(etsData.taskExecStartDate, eteData.taskExecEndDate)+1;
         //out.println("s :" + etsData.taskExecStartDate + "  e:" + eteData.taskExecEndDate + " dur :" + durPlan1);
       int durPlanTotal = durPlan1 + debuginSunsu;
         totaldu = nf.format(integer.intValue()) + " ("+durPlanTotal+")";
      }

      }


    E3PSTask etStartTop = null;
      E3PSTask etEndTop = null;
      etStartTop = MoldProjectHelper.getLikeTask(moldProject, "제품도출도");
      etEndTop = MoldProjectHelper.getLikeTask(moldProject, "금형양산이관");
      if(etStartTop != null){
        E3PSTaskData etsData = new E3PSTaskData(etStartTop);
        if(etsData.taskExecStartDate != null){
        durPlan2 = DateUtil.getDuration(etsData.taskExecStartDate, DateUtil.getCurrentTimestamp())+1;
        totalduMain = "" + durPlan2;
        }
      }

      if(etStartTop != null && etEndTop != null){
        E3PSTaskData etsData = new E3PSTaskData(etStartTop);
        E3PSTaskData eteData = new E3PSTaskData(etEndTop);
        if(etsData.taskExecStartDate != null && eteData.taskExecEndDate  != null) {
        durPlan2 = DateUtil.getDuration(etsData.taskExecStartDate, eteData.taskExecEndDate)+1;
        totalduMain = "" + durPlan2;
        }
      }



    }

%>

    <form>
        <input type="hidden" name="oid" value="<%=oid%>"></input> <input type="hidden" name="popup" value="<%=popup%>"></input>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01027")%><%--금형 프로젝트 상세정보--%></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="5"></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"
                                                        onclick="showProcessing();"><a href="javascript:goView('1');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"
                                                        onclick="showProcessing();"><a href="javascript:goView('2');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"
                                                        onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02354")%><%--일정/비용--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table></td>
                                        <td align="left"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="#"
                                                        onclick="javascript:goView('4');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원 --%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td align="left"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="#"
                                                        onclick="javascript:goView('5');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue --%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table></td>
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
                            <td valign="top">
                                <!--내용-->
                                <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                    <tr>
                                        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02678", totalduMain)%><%--주요일정 정보(금형 개발 일정 : {0}일)--%></td>
                                                    <td align="right">
                                                        <%-- <jsp:include page="/jsp/project/MoldProjectButton_include.jsp" flush="false">
                                                            <jsp:param name="oid" value="<%=oid%>" />
                                                            <jsp:param name="popup" value="<%=popup%>" />
                                                        </jsp:include> --%>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table>

                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td colspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%></td>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%></td>
                                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02822")%><%--초도품--%>
                                                        <br><%=messageService.getString("e3ps.message.ket_message", "02535")%><%--제출일--%></td>
                                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02634")%><%--제품합격--%></td>
                                                    <td colspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01095")%><%--금형이관--%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueM">DR1</td>
                                                    <td class="tdblueM">DR2</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02587")%><%--제품도면 등록--%>
                                                        <br><%=messageService.getString("e3ps.message.ket_message", "02878")%><%--출도--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02530")%><%--제작--%>
                                                        <br><%=messageService.getString("e3ps.message.ket_message", "02005")%><%--시방서--%></td>
                                                    <td class="tdblueM">Lay-out<br><%=messageService.getString("e3ps.message.ket_message", "03218")%><%--협의--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00714")%><%--검수--%></td>
                                                    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02268")%><%--이관--%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid1.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid1%>');">
                                                            <%
                                                                }
                                                            %><%=schdule1%>
                                                            <%
                                                                if (taskOid1.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid2.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid2%>');">
                                                            <%
                                                                }
                                                            %><%=schdule2%>
                                                            <%
                                                                if (taskOid2.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid3.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid3%>');">
                                                            <%
                                                                }
                                                            %><%=schdule3%>
                                                            <%
                                                                if (taskOid3.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <%
                                                        if (isProto) {
                                                    %>
                                                    <td class="tdblueM">&nbsp;</td>
                                                    <%
                                                        } else {
                                                    %>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid4.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid4%>');">
                                                            <%
                                                                }
                                                            %><%=schdule4%>
                                                            <%
                                                                if (taskOid4.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <%
                                                        }
                                                    %>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid5.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid5%>');">
                                                            <%
                                                                }
                                                            %><%=schdule5%>
                                                            <%
                                                                if (taskOid5.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid6.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid6%>');">
                                                            <%
                                                                }
                                                            %><%=schdule6%>
                                                            <%
                                                                if (taskOid6.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid7.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid7%>');">
                                                            <%
                                                                }
                                                            %><%=schdule7%>
                                                            <%
                                                                if (taskOid7.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <%
                                                        if (isProto) {
                                                    %>
                                                    <td class="tdblueM">&nbsp;</td>
                                                    <td class="tdblueM0">&nbsp;</td>
                                                    <%
                                                        } else {
                                                    %>
                                                    <td class="tdwhiteM">
                                                        <%
                                                            if (taskOid8.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid8%>');">
                                                            <%
                                                                }
                                                            %><%=schdule8%>
                                                            <%
                                                                if (taskOid8.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td class="tdwhiteM0">
                                                        <%
                                                            if (taskOid9.length() > 0) {
                                                        %><a href="#"
                                                        onclick="javascript:viewProject('<%=taskOid9%>');">
                                                            <%
                                                                }
                                                            %><%=schdule9%>
                                                            <%
                                                                if (taskOid9.length() > 0) {
                                                            %>
                                                    </a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <%
                                                        }
                                                    %>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space15"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table> <%
     if (isMaking) {
 %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02587")%><%--제품도면 등록--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01085")%><%--금형설계--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01064")%><%--금형부품--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01104")%><%--금형조립--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01012")%><%--금형 Try--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02624")%><%--제품측정--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00739")%><%--검토회의--%></td>
                                                    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01921")%><%--소요일정--%></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02831")%><%--총소요일--%></td>

                                                    <%
                                                        for (int i = 0; i < totalDurationV.size() - 1; i++) {
                                                    		    Integer integer = (Integer) totalDurationV.get(i);
                                                    		    String dur = nf.format(integer.intValue());
                                                    %>

                                                    <td class="tdwhiteM"><%=dur%></td>
                                                    <%
                                                        }
                                                    %>
                                                    <td class="tdwhiteM0"><%=totaldu%></td>
                                                </tr>

                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02821")%><%--초도--%></td>

                                                    <%
                                                        int total = 0;
                                                    		for (int i = 0; i < initDesignV.size(); i++) {
                                                    		    //String taskName = taskNames[i];
                                                    		    E3PSTask eTask = (E3PSTask) initDesignV.get(i);
                                                    		    String schdule = getSchdule(eTask);
                                                    		    String schduleStart = getSchdule3(eTask);
                                                    		    if (eTask != null) {
                                                    			int duration = getExDuration(eTask);
                                                    			total += duration;
                                                    		    }
                                                    %>
                                                    <td class="tdwhiteM" title="<%=schduleStart%>"><%=schdule%></td>
                                                    <%
                                                        }
                                                    		String totalStr = "-";
                                                    		if (total > 0) {
                                                    		    totalStr = String.valueOf(total);
                                                    		}
                                                    		String totalsunsu = "";
                                                    		if (durPlan1 > 0) {
                                                    		    totalsunsu = " (" + durPlan1 + ")";
                                                    		}
                                                    %>
                                                    <td class="tdwhiteM0"><%=totalStr%><%=totalsunsu%></td>
                                                </tr>
                                                <%
                                                    for (int i = 0; i < debugDatas.size(); i++) {
                                                		    DebugingData dd = (DebugingData) debugDatas.get(i);
                                                		    Hashtable taskHash = dd.datas;
                                                		    int cha = dd.nCha;
                                                %>

                                                <tr>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01339")%><%--디버깅--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", cha)%><%--{0}차--%></td>
                                                    <%
                                                        int dtotal = 0;
                                                    		    E3PSTaskData debuginTaskDatas = null;
                                                    		    E3PSTaskData debuginTaskDatae = null;
                                                    		    int debuginSunsuEach = 0;
                                                    		    E3PSTask nchaTask = dd.nchaTask;
                                                    		    if (nchaTask != null) {
                                                    			E3PSTaskData nchaDate = new E3PSTaskData(nchaTask);
                                                    			if (nchaDate.taskExecStartDate != null && nchaDate.taskExecEndDate != null) {
                                                    			    debuginSunsuEach = DateUtil.getDuration(nchaDate.taskExecStartDate, nchaDate.taskExecEndDate) + 1;
                                                    			}
                                                    		    }
                                                    		    for (int j = 0; j < debugingNames.size(); j++) {
                                                    			String dtaskName = (String) debugingNames.get(j);
                                                    			E3PSTask eTask = (E3PSTask) taskHash.get(dtaskName);
                                                    			String schdule = "&nbsp;";
                                                    			if (eTask != null) {
                                                    			    schdule = getSchdule(eTask);
                                                    			}
                                                    			String schduleStart = "";
                                                    			if (eTask != null) {
                                                    			    schduleStart = getSchdule3(eTask);
                                                    			}

                                                    			if (eTask != null) {
                                                    			    int duration = getExDuration(eTask);
                                                    			    dtotal += duration;
                                                    			}

                                                    			if (dtaskName.equals("검사 의뢰") || dtaskName.equals("검사의뢰")) {
                                                    			    continue;
                                                    			}
                                                    %>
                                                    <td class="tdwhiteM" title="<%=schduleStart%>"><%=schdule%></td>
                                                    <%
                                                        }
                                                    		    String dug_totalStr = "-";
                                                    		    if (total > 0) {
                                                    			dug_totalStr = String.valueOf(dtotal);
                                                    		    }
                                                    		    String debuginSunsuEachStr = "";
                                                    		    if (debuginSunsuEach > 0) {
                                                    			debuginSunsuEachStr = " (" + debuginSunsuEach + ")";
                                                    		    }
                                                    %>
                                                    <td class="tdwhiteM0"><%=dug_totalStr%><%=debuginSunsuEachStr%></td>
                                                </tr>

                                                <%
                                                    }
                                                %>
                                            </table> <%
     } else {
 %> <!-- 외주 -->

                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02587")%><%--제품도면 등록--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02187")%><%--외주금형제작--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02185")%><%--외주금형Try--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01012")%><%--금형 Try--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02624")%><%--제품측정--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00739")%><%--검토회의--%></td>
                                                    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01921")%><%--소요일정--%></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02831")%><%--총소요일--%></td>

                                                    <%
                                                        for (int i = 0; i < totalDurationV.size() - 1; i++) {
                                                    		    Integer integer = (Integer) totalDurationV.get(i);
                                                    		    String dur = nf.format(integer.intValue());
                                                    %>
                                                    <%
                                                        //if(isProto && i == 3){
                                                    %>
                                                    <!--td class="tdblueM">-</td-->
                                                    <%
                                                        //}else{
                                                    %>
                                                    <td class="tdwhiteM"><%=dur%></td>
                                                    <%
                                                        //}
                                                    %>
                                                    <%
                                                        }
                                                    %>
                                                    <td class="tdwhiteM0"><%=totaldu%></td>
                                                </tr>


                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02821")%><%--초도--%></td>

                                                    <%
                                                        int total = 0;
                                                    		for (int i = 0; i < initDesignV.size(); i++) {
                                                    		    //String taskName = taskNames[i];
                                                    		    E3PSTask eTask = (E3PSTask) initDesignV.get(i);
                                                    		    String schdule = getSchdule(eTask);
                                                    		    String schduleStart = getSchdule3(eTask);
                                                    		    if (eTask != null) {
                                                    			int duration = getExDuration(eTask);
                                                    			total += duration;
                                                    		    }
                                                    %>
                                                    <%
                                                        //if(isProto && i == 3){
                                                    %>
                                                    <!--td class="tdblueM">-</td-->
                                                    <%
                                                        //}else{
                                                    %>
                                                    <td class="tdwhiteM" title="<%=schduleStart%>"><%=schdule%></td>
                                                    <%
                                                        //}
                                                    %>
                                                    <%
                                                        }
                                                    		String totalStr = "-";
                                                    		if (total > 0) {
                                                    		    totalStr = String.valueOf(total);
                                                    		}
                                                    		String totalsunsu = "";
                                                    		if (durPlan1 > 0) {
                                                    		    totalsunsu = " (" + durPlan1 + ")";
                                                    		}
                                                    %>
                                                    <td class="tdwhiteM0"><%=totalStr%><%=totalsunsu%></td>
                                                </tr>

                                                <%
                                                    for (int i = 0; i < debugDatas.size(); i++) {
                                                		    DebugingData dd = (DebugingData) debugDatas.get(i);
                                                		    Hashtable taskHash = dd.datas;
                                                		    int cha = dd.nCha;
                                                %>

                                                <tr>
                                                    <td width="47" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01339")%><%--디버깅--%></td>
                                                    <td width="47" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", cha)%><%--{0}차--%></td>

                                                    <%
                                                        int dtotal = 0;
                                                    		    int debuginSunsuEach = 0;
                                                    		    E3PSTaskData debuginTaskDatas = null;
                                                    		    E3PSTaskData debuginTaskDatae = null;
                                                    		    E3PSTask nchaTask = dd.nchaTask;
                                                    		    if (nchaTask != null) {
                                                    			E3PSTaskData nchaDate = new E3PSTaskData(nchaTask);
                                                    			if (nchaDate.taskExecStartDate != null && nchaDate.taskExecEndDate != null) {
                                                    			    debuginSunsuEach = DateUtil.getDuration(nchaDate.taskExecStartDate, nchaDate.taskExecEndDate) + 1;
                                                    			}
                                                    		    }
                                                    		    for (int j = 0; j < debugingNames.size(); j++) {
                                                    			String dtaskName = (String) debugingNames.get(j);
                                                    			E3PSTask eTask = (E3PSTask) taskHash.get(dtaskName);

                                                    			String schdule = "&nbsp;";

                                                    			if (eTask != null) {
                                                    			    schdule = getSchdule(eTask);
                                                    			}
                                                    			String schduleStart = "";
                                                    			if (eTask != null) {
                                                    			    schduleStart = getSchdule3(eTask);
                                                    			}

                                                    			if (dtaskName.equals("pass")) {
                                                    			    schdule = "-";
                                                    			}

                                                    			if (eTask != null) {
                                                    			    int duration = getExDuration(eTask);
                                                    			    dtotal += duration;
                                                    			}

                                                    			if (dtaskName.equals("검사 의뢰") || dtaskName.equals("검사의뢰")) {
                                                    			    continue;
                                                    			}
                                                    %>
                                                    <%
                                                        if (isProto && j == 3) {
                                                    %>
                                                    <td class="tdblueM">-</td>
                                                    <%
                                                        } else {
                                                    %>
                                                    <td class="tdwhiteM" title="<%=schduleStart%>"><%=schdule%></td>
                                                    <%
                                                        }
                                                    %>
                                                    <%
                                                        }
                                                    		    String dug_totalStr = "-";
                                                    		    if (total > 0) {
                                                    			dug_totalStr = String.valueOf(dtotal);
                                                    		    }
                                                    		    String debuginSunsuEachStr = "";
                                                    		    if (debuginSunsuEach > 0) {
                                                    			debuginSunsuEachStr = " (" + debuginSunsuEach + ")";
                                                    		    }
                                                    %>
                                                    <td class="tdwhiteM0"><%=dug_totalStr%><%=debuginSunsuEachStr%></td>
                                                </tr>

                                                <%
                                                    }
                                                %>
                                            </table> <!-- 외주 끝 --> <%
     }
 %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space15"></td>
                                                </tr>
                                            </table>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01641")%><%--비용 정보--%></td>
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
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table> <%
     MoldItemInfo info = moldProject.getMoldInfo();
     CostInfo costInfo = info.getCostInfo();

     if (isProto) {

 		String targetCostStr = "-";
 		//  String planCostStr = "-";
 		String decideCostStr = "-";
 		String executionCostStr = "-";
 		String editCostStr = "-";
 		String totalStr = "-";
 		String exStr = "-";

 		int targetCost = 0;
 		if (costInfo != null) {
 		    try {
 			targetCost = Integer.parseInt(costInfo.getTargetCost());
 			targetCostStr = nf.format(Integer.parseInt(costInfo.getTargetCost()));
 		    } catch (Exception ex) {
 			Kogger.error(ex);
 		    }

 		    try {
 			decideCostStr = nf.format(Integer.parseInt(costInfo.getDecideCost()));
 		    } catch (Exception ex) {
 			Kogger.error(ex);
 		    }

 		    int exCost = 0;

 		    try {
 			exCost = Integer.parseInt(costInfo.getExecutionCost());
 			executionCostStr = nf.format(exCost); //초기집행가
 		    } catch (Exception ex) {
 			Kogger.error(ex);
 		    }

 		    int editCost = 0;

 		    try {
 			editCost = Integer.parseInt(costInfo.getEditCost());
 			editCostStr = nf.format(editCost); //추가집행가
 		    } catch (Exception ex) {
 			Kogger.error(ex);
 		    }

 		    int totalex = (exCost + editCost);
 		    totalStr = nf.format(totalex);

 		    int ex = targetCost - totalex;
 		    exStr = nf.format(ex);
 		}
 %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01387")%><%--목표가--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02143")%><%--예산--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03229")%><%--확정가--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02818")%><%--초기집행가--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02876")%><%--추가집행가--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02833")%><%--총집행가--%></td>
                                                    <td width="120" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02443")%><%--잔액--%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdwhiteM"><%=targetCostStr%></td>
                                                    <td class="tdwhiteM"><%=targetCostStr%></td>
                                                    <td class="tdwhiteM"><%=decideCostStr%></td>
                                                    <td class="tdwhiteM"><%=executionCostStr%></td>
                                                    <td class="tdwhiteM"><%=editCostStr%></td>
                                                    <td class="tdwhiteM"><%=totalStr%></td>
                                                    <td width="120" class="tdwhiteM0"><%=exStr%></td>
                                                </tr>
                                            </table> <%
     } else {
 %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02944")%><%--투자오더--%></td>
                                                    <td colspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02143")%><%--예산--%></td>
                                                    <td colspan="4" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "09495")%><%--발주실적--%></td>
                                                    <td colspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "09496")%><%--집행실적--%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "09498")%><%--초기--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "09497")%><%--증액/삭감--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834")%><%--총합--%>(A)</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02530")%><%--제작--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01339")%><%--디버깅--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834")%><%--총액--%>(B)</td>
                                                    <td class="tdorgM"><%=messageService.getString("e3ps.message.ket_message", "02443")%><%--잔액--%>(A-B)</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02834")%><%--총액--%>(C)</td>
                                                    <td class="tdorgM0"><%=messageService.getString("e3ps.message.ket_message", "02443")%><%--잔액--%>(A-C)</td>
                                                </tr>
                                                <%
                                                        QueryResult rtCost = ProductProjectHelper.manager.getCostInfo(moldProject.getPjtNo());
                                                        List<Vector> costList = new ArrayList();
		                                                boolean isOrder = false;
		                                                String orderNumber = null;
		                                                String targetCost = "";
		                                                int initPrice = 0;
		                                                int totalPrice = 0;
		                                                String initPlanPrice = "-";
		                                                String addPlanPrice = "-";
		                                                String totalPlanPrice = "-";
		
		                                                String initmoldTotalPrice = "-";
		                                                String debugingTotalPrice = "-";
		                                                String moldTotalPrice = "-";
		                                                String balancePrice = "-";
		                                                String orderPrice = "-";
		                                                String orderPriceBalance = "-";
		                                                boolean isTotal = false;
		                                                boolean isOrderPrcMinus = false;
		                                                boolean isOrderPbcMinus = false;
		                                                Vector priceV = new Vector();
		                                                Vector copyPriceV = new Vector();
		                                                
		                                                Map<String, String> checkMap = new HashMap<String,String>();
		                                                String exchange = "1.00";
		                                                
                                                        while (rtCost.hasMoreElements()) {
                                                            
                                                            Object[] obj = (Object[]) rtCost.nextElement();
                                                            costInfo = (CostInfo) obj[0];
                                                                                                                        
                                                            if (costInfo != null) {
                                                                orderNumber = costInfo.getOrderInvest();
                                                            }
                                                            String key = orderNumber + costInfo.getDieNo();
                                                            if(key.equals(checkMap.get(key))){
                                                        	   continue;
                                                            }
                                                            checkMap.put(key, key);
                                                            
                                                            //orderNumber = "402937";
                                                            
                                                            if (orderNumber != null && orderNumber.length() > 0) {
                                                                isOrder = true;
                                                                
                                                                Integer integer = 0;
                                                                
                                                                Hashtable hash = ProductPrice.getPrice(orderNumber,costInfo.getCostType());
                                                                Long longVal = (Long) hash.get(ProductPrice.INITPRICE);
                                                                if (longVal != null) {
                                                                initPlanPrice = nf.format(longVal.longValue());
                                                                }
                                                                longVal = (Long) hash.get(ProductPrice.ADDPRICE);
                                                                if (longVal != null) {
                                                                addPlanPrice = nf.format(longVal.longValue());
                                                                }

                                                                longVal = (Long) hash.get(ProductPrice.TOTALPRICE);
                                                                if (longVal != null) {
                                                                totalPlanPrice = nf.format(longVal.longValue());
                                                                }
                                                                Long totalExpenseObj = (Long) hash.get(ProductPrice.TOTALEXPENSE);
                                                                exchange = (String)hash.get(ProductPrice.EXCHANGE);
                                                                
                                                                if("KRW".equals(exchange)){
                                                                    exchange = "";
                                                                }
                                                                
                                                                priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
                                                                
                                                                if (priceV.size() > 0 && "금형(로컬)".equals(costInfo.getCostType()) && StringUtils.isNotEmpty(exchange) ) {
                                                                    
                                                                    for(int i=0; i<priceV.size(); i++){
                                                                	    Hashtable hhh = (Hashtable) priceV.get(i);
                                                                        
                                                                	    integer = (Integer) hhh.get(SAPMoldPrice.MOLD_DESIGN);
                                                                        Double doubleVal = Double.parseDouble(integer.toString()) * Double.parseDouble(exchange);//금형(로컬) 인 경우 환율 환산
                                                                        BigDecimal b = new BigDecimal(doubleVal.toString());
                                                                        hhh.put(SAPMoldPrice.MOLD_DESIGN, new Integer(b.intValue()));
                                                                        
                                                                        integer = (Integer) hhh.get(SAPMoldPrice.MOLD_PART);
                                                                        doubleVal = Double.parseDouble(integer.toString()) * Double.parseDouble(exchange);//금형(로컬) 인 경우 환율 환산
                                                                        b = new BigDecimal(doubleVal.toString());
                                                                        hhh.put(SAPMoldPrice.MOLD_PART, new Integer(b.intValue()));
                                                                        
                                                                        integer = (Integer) hhh.get(SAPMoldPrice.MOLD_ASSEMBLING);
                                                                        doubleVal = Double.parseDouble(integer.toString()) * Double.parseDouble(exchange);//금형(로컬) 인 경우 환율 환산
                                                                        b = new BigDecimal(doubleVal.toString());
                                                                        hhh.put(SAPMoldPrice.MOLD_ASSEMBLING, new Integer(b.intValue()));
                                                                        
                                                                        integer = (Integer) hhh.get(SAPMoldPrice.MOLD_TRY);
                                                                        doubleVal = Double.parseDouble(integer.toString()) * Double.parseDouble(exchange);//금형(로컬) 인 경우 환율 환산
                                                                        b = new BigDecimal(doubleVal.toString());
                                                                        hhh.put(SAPMoldPrice.MOLD_TRY, new Integer(b.intValue()));
                                                                        
                                                                        integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);
                                                                        doubleVal = Double.parseDouble(integer.toString()) * Double.parseDouble(exchange);//금형(로컬) 인 경우 환율 환산
                                                                        b = new BigDecimal(doubleVal.toString());
                                                                        hhh.put(SAPMoldPrice.TOTAL, new Integer(b.intValue()));
                                                                        
                                                                        copyPriceV.add(i, hhh);
                                                                    }
                                                                    priceV = copyPriceV;
                                                                }
                                                                costList.add(priceV);
                                                                
                                                                int initMold = 0;
                                                                if (priceV.size() > 0) {

	                                                                Hashtable hhh = (Hashtable) priceV.get(0);
	                                                                integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);
	
	                                                                if (integer != null) {
	                                                                    initMold = integer.intValue();
	                                                                }
                                                                }

                                                                initmoldTotalPrice = nf.format(initMold);

                                                                int debugingtotal = 0;
                                                                for (int i = 1; i < priceV.size(); i++) {
	                                                                Hashtable hhh = (Hashtable) priceV.get(i);
	                                                                integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);
	
	                                                                if (integer != null) {
	                                                                    debugingtotal += integer.intValue();
	                                                                }
                                                                }

                                                                debugingTotalPrice = nf.format(debugingtotal);
                                                                moldTotalPrice = nf.format(initMold + debugingtotal);

                                                                if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
                                                                isTotal = true;
                                                                }

                                                                
                                                                balancePrice = nf.format((Long) hash.get(ProductPrice.TOTALPRICE) - (initMold + debugingtotal));
                                                                
                                                                /* SapService service = new SapService();
                                                                Map<String, String> datas = service.getOrderByDieNo(moldProject.getPjtNo()); */
                                                                
                                                                String E_WOGBTR = "0";
                                                                if(totalExpenseObj != null){
                                                                    E_WOGBTR = Long.toString(totalExpenseObj);    
                                                                }
                                                                
                                                                orderPrice = nf.format(Integer.parseInt(E_WOGBTR));
                                                                
                                                                orderPriceBalance = nf.format((Long) hash.get(ProductPrice.TOTALPRICE) - (Integer.parseInt(E_WOGBTR)));
                                                                isOrderPrcMinus = false;
                                                                isOrderPbcMinus = false;
                                                                /* if(((Long) hash.get(ProductPrice.TOTALPRICE) - (Integer.parseInt(E_WOGBTR))) < 0){
                                                                    isOrderPrcMinus = true;
                                                                } */
                                                                
                                                                if(((Long) hash.get(ProductPrice.TOTALPRICE) - (initMold + debugingtotal)) < 0){
                                                                    isOrderPrcMinus = true;
                                                                }
                                                                
                                                                if( ((Long) hash.get(ProductPrice.TOTALPRICE) - (Integer.parseInt(E_WOGBTR))) < 0){
                                                                    isOrderPbcMinus = true;
                                                                }
                                                                
                                                            } else {
                                                                orderNumber = "-";
                                                            }
                                                        

                                                		//  String addPriceStr = nf.format(
                                                %>

                                                <tr>
                                                    <td class="tdwhiteM"><%=orderNumber%></td>
                                                    <td class="<%="-".equals(initPlanPrice)?"tdwhiteM":"tdwhiteR"%>"><%=initPlanPrice%></td>
                                                    <td class="<%="-".equals(addPlanPrice)?"tdwhiteM":"tdwhiteR"%>"><%=addPlanPrice%></td>
                                                    <td class="<%="-".equals(totalPlanPrice)?"tdwhiteM":"tdwhiteR"%>"><%=totalPlanPrice%></td>
                                                    <td class="<%="-".equals(initmoldTotalPrice)?"tdwhiteM":"tdwhiteR"%>"><%=initmoldTotalPrice%></td>
                                                    <td class="<%="-".equals(debugingTotalPrice)?"tdwhiteM":"tdwhiteR"%>"><%=debugingTotalPrice%></td>
                                                    <td class="<%="-".equals(moldTotalPrice)?"tdwhiteM":"tdwhiteR"%>"><%=moldTotalPrice%>
<%--                                                         <%
                                                            if (isTotal) {
                                                        %><font color=red>
                                                            <%
                                                                }
                                                            %><%=moldTotalPrice%>
                                                            <%
                                                                if (isTotal) {
                                                            %>
                                                    </font>
                                                        <%
                                                            }
                                                        %> --%>
                                                    </td>
                                                    <td class="<%="-".equals(balancePrice)?"tdwhiteM":"tdwhiteR"%>"><%if(isOrderPrcMinus){ %><font color=red><%}%><%=balancePrice%><%if(isOrderPrcMinus){ %></font><%} %></td>
                                                    <td class="tdwhiteM"><%=orderPrice%></td>
                                                    <td class="tdwhiteM0"><%if(isOrderPbcMinus){ %><font color=red><%}%><%=orderPriceBalance%><%if(isOrderPbcMinus){ %></font><%} %></td>
                                                </tr>
                                                <%} %>
                                            </table>
                                            
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space15"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table> 
                                            <%
                                                Vector debugingVector = new Vector();
                                                Hashtable debugingHash = new Hashtable();
                                                Map<String,Object> tempMap = new HashMap<String,Object>();
                                                
                                                Vector orgV = null;
                                                
                                                if(costList != null && costList.size() > 0){
                                                    orgV = (Vector)costList.get(0);//금형(로컬) 이 아닌 금형 데이터
                                                }
                                                
                                                if(costList.size() > 1){//금형(로컬) 데이터가 있으면
                                                    debugingVector.add(0, "");//디버깅 1차부터 시작하므로 무의미한 데이터를 add함    
                                                }
                                                
                                                
                                                for(int i=1; i < costList.size(); i++ ){//List 에는 두번째 부터 금형(로컬) 데이터가 적재되있으므로 1부터 시작
                                                    
                                                    priceV = (Vector)costList.get(i);
                                                    
                                                    //금형이 아닌 금형(로컬) 데이터 루프를 돌리며 같은 디버깅 차수에 해당하는 계산결과를 원본(금형) 에서 찾아 더해줌
                                                    for(int j = 1; j < priceV.size(); j++){
                                                	   
                                                	   debugingHash = new Hashtable();
                                                	   Hashtable orgHash = (Hashtable)orgV.get(j);
                                                	   Integer integer = (Integer)orgHash.get(SAPMoldPrice.MOLD_DESIGN);
                                                		   
                                                	   Hashtable hash = (Hashtable)priceV.get(j);
                                                	   Integer integer2 = (Integer)hash.get(SAPMoldPrice.MOLD_DESIGN);
                                                	   
                                                	   debugingHash.put(SAPMoldPrice.MOLD_DESIGN, integer.intValue() + integer2.intValue());
                                                	   
                                                	   
                                                	   integer = (Integer)orgHash.get(SAPMoldPrice.MOLD_PART);
                                                       hash = (Hashtable)priceV.get(j);
                                                       integer2 = (Integer)hash.get(SAPMoldPrice.MOLD_PART);
                                                       debugingHash.put(SAPMoldPrice.MOLD_PART, integer.intValue() + integer2.intValue());
                                                       
                                                       
                                                       integer = (Integer)orgHash.get(SAPMoldPrice.MOLD_ASSEMBLING);
                                                       hash = (Hashtable)priceV.get(j);
                                                       integer2 = (Integer)hash.get(SAPMoldPrice.MOLD_ASSEMBLING);
                                                       debugingHash.put(SAPMoldPrice.MOLD_ASSEMBLING, integer.intValue() + integer2.intValue());
                                                       
                                                       integer = (Integer)orgHash.get(SAPMoldPrice.MOLD_TRY);
                                                       hash = (Hashtable)priceV.get(j);
                                                       integer2 = (Integer)hash.get(SAPMoldPrice.MOLD_TRY);
                                                       debugingHash.put(SAPMoldPrice.MOLD_TRY, integer.intValue() + integer2.intValue());
                                                       
                                                       integer = (Integer)orgHash.get(SAPMoldPrice.TOTAL);
                                                       hash = (Hashtable)priceV.get(j);
                                                       integer2 = (Integer)hash.get(SAPMoldPrice.TOTAL);
                                                       debugingHash.put(SAPMoldPrice.TOTAL, integer.intValue() + integer2.intValue());
                                                	   
                                                	   debugingVector.add(j, debugingHash);
                                                    }
                                                    
                                                }

                                                if(debugingVector.size() < 1 && costList.size() > 0){//투자오더가 1건 뿐일 때.
                                                    debugingVector = costList.get(0);
                                                }
                                                
                                                
                                                %>
                                            
                                            <%
     if (isMaking && isOrder) {
 %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01085")%><%--금형설계--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01064")%><%--금형부품--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01104")%><%--금형조립--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01012")%><%--금형 Try--%></td>
                                                    <td class="tdblueM0">Total</td>
                                                </tr>

                                                <%
                                                            String designPriceStr = "-";
                                                		    String moldPartPriceStr = "-";
                                                		    String assPriceStr = "-";
                                                		    String tryPriceStr = "-";
                                                		    String totalPriceStr = "-";
                                                		    
                                                		    int designPrice = 0;
                                                            int moldPartPrice = 0;
                                                            int assPrice = 0;
                                                            int tryPrice = 0;
                                                            int total = 0;
                                                		    
                                                		    for(Vector v : costList){
                                                			    priceV = v;
                                                			    for (int i = 0; i < priceV.size(); i++) {
                                                				    Hashtable pHash = (Hashtable) priceV.get(i);
                                                				    Integer integer = (Integer) pHash.get(SAPMoldPrice.MOLD_DESIGN);
                                                                    if (integer != null) {
                                                                	    designPrice += integer.intValue();
                                                                	}
                                                                    integer = (Integer) pHash.get(SAPMoldPrice.MOLD_PART);
                                                                    
                                                                    if (integer != null) {
                                                                	    moldPartPrice += integer.intValue();
                                                                    //moldPartPriceStr = nf.format(integer.intValue());
                                                                    }
                                                                    integer = (Integer) pHash.get(SAPMoldPrice.MOLD_ASSEMBLING);
                                                                    
                                                                    if (integer != null) {
                                                                	    assPrice += integer.intValue();
                                                                    //assPriceStr = nf.format(integer.intValue());
                                                                    }

                                                                    integer = (Integer) pHash.get(SAPMoldPrice.MOLD_TRY);
                                                                    if (integer != null) {
                                                                	    tryPrice += integer.intValue();
                                                                    //tryPriceStr = nf.format(integer.intValue());
                                                                    }

                                                                    integer = (Integer) pHash.get(SAPMoldPrice.TOTAL);
                                                                    if (integer != null) {
                                                                	    total += integer.intValue();
                                                                    //totalPriceStr = nf.format(integer.intValue());
                                                                    }
                                                                }
                                                                designPriceStr = nf.format(designPrice);
                                                                moldPartPriceStr = nf.format(moldPartPrice);
                                                                assPriceStr = nf.format(assPrice);
                                                                tryPriceStr = nf.format(tryPrice);
                                                                totalPriceStr = nf.format(total);
                                                		    }

                                                		    
                                                %>
                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02829")%><%--총비용--%></td>
                                                    <td class="tdwhiteR"><%=designPriceStr%></td>
                                                    <td class="tdwhiteR"><%=moldPartPriceStr%></td>
                                                    <td class="tdwhiteR"><%=assPriceStr%></td>
                                                    <td class="tdwhiteR"><%=tryPriceStr%></td>
                                                    <td class="tdwhiteR0"><%=totalPriceStr%></td>
                                                </tr>

                                                <tr>
                                                    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02821") %><%--초도--%></td>

                                                    <%
                                                    
                                                    designPriceStr = "-";
                                                    moldPartPriceStr = "-";
                                                    assPriceStr = "-";
                                                    tryPriceStr = "-";
                                                    totalPriceStr = "-";
                                                    
                                                    int FdesignPrice = 0;
                                                    int FmoldPartPrice = 0;
                                                    int FassPrice = 0;
                                                    int FtryPrice = 0;
                                                    int FtotalPrice = 0;
                                                    
                                                    for(Vector v : costList){
                                                	   priceV = v;
                                                	   Hashtable hash = (Hashtable)priceV.get(0);
                                                	   Integer integer = (Integer)hash.get(SAPMoldPrice.MOLD_DESIGN);
                                                	   
                                                	   if(integer != null){
                                                	       FdesignPrice += integer.intValue();
                                                	       designPriceStr = nf.format(FdesignPrice);
                                                       }
                                                       
                                                	   integer = (Integer)hash.get(SAPMoldPrice.MOLD_PART);
                                                       
                                                	   if(integer != null){
                                                	       FmoldPartPrice += integer.intValue();
                                                	       moldPartPriceStr = nf.format(FmoldPartPrice);
                                                	   }
                                                	   
                                                	   integer = (Integer)hash.get(SAPMoldPrice.MOLD_ASSEMBLING);
                                                	   
                                                	   if(integer != null){
                                                	       FassPrice += integer.intValue();
                                                	       assPriceStr = nf.format(FassPrice);
                                                       }

                                                       integer = (Integer)hash.get(SAPMoldPrice.MOLD_TRY);
                                                       
                                                       if(integer != null){
                                                	       FtryPrice += integer.intValue();
                                                	       tryPriceStr = nf.format(FtryPrice);
                                                       }
                                                           
                                                       integer = (Integer)hash.get(SAPMoldPrice.TOTAL);
                                                           
                                                       if(integer != null){
                                                	       FtotalPrice += integer.intValue();
                                                	       totalPriceStr = nf.format(FtotalPrice);
                                                       }
                                                    }
                                                    %>

                                                    <td class="tdwhiteR"><%=designPriceStr%></td>
                                                    <td class="tdwhiteR"><%=moldPartPriceStr%></td>
                                                    <td class="tdwhiteR"><%=assPriceStr%></td>
                                                    <td class="tdwhiteR"><%=tryPriceStr%></td>
                                                    <td class="tdwhiteR0"><%=totalPriceStr%></td>
                                                </tr>
                                                
                                                
                                                <%for(int i = 1; i < debugingVector.size(); i++){
                                                    
                                                    designPriceStr = "-";                       
                                                    moldPartPriceStr = "-";
                                                    assPriceStr = "-";
                                                    tryPriceStr = "-";
                                                    totalPriceStr = "-";
                                                    
                                                    Hashtable pHash = (Hashtable)debugingVector.get(i);

                                                    Integer integer = (Integer)pHash.get(SAPMoldPrice.MOLD_DESIGN);
                      
                                                    if(integer != null){                        
                                                	    designPriceStr = nf.format(integer.intValue());
                                                    }

                      
                                                    integer = (Integer)pHash.get(SAPMoldPrice.MOLD_PART);
                      
                                                    if(integer != null){
                                                	    moldPartPriceStr = nf.format(integer.intValue());
                                                    }

                      
                                                    integer = (Integer)pHash.get(SAPMoldPrice.MOLD_ASSEMBLING);
                      
                                                    if(integer != null){
                                                	   assPriceStr = nf.format(integer.intValue());
                                                    }

                      
                                                    integer = (Integer)pHash.get(SAPMoldPrice.MOLD_TRY);
                      
                                                    if(integer != null){
                                                	    tryPriceStr = nf.format(integer.intValue());
                                                    }

                      
                                                    integer = (Integer)pHash.get(SAPMoldPrice.TOTAL);
                      
                                                    if(integer != null){
                                                	    totalPriceStr = nf.format(integer.intValue());
                                                    }

                    %>

                                                <tr>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01339") %><%--디버깅--%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", i) %><%--{0}차--%></td>
                                                    <td class="tdwhiteR"><%=designPriceStr%></td>
                                                    <td class="tdwhiteR"><%=moldPartPriceStr%></td>
                                                    <td class="tdwhiteR"><%=assPriceStr%></td>
                                                    <td class="tdwhiteR"><%=tryPriceStr%></td>
                                                    <td class="tdwhiteR0"><%=totalPriceStr%></td>
                                                </tr>
                                                <%}%>
                                            </table> <%}else if(!isMaking && isOrder){ %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space5"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                            <tr>
                                                                <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%></td>
                                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01099") %><%--금형제작--%></td>
                                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01012") %><%--금형 Try--%></td>
                                                                <td class="tdblueM0">Total</td>
                                                            </tr>
                                                            <%
                                                                String designPriceStr = "-";
                                                    		    String moldMakingPrice = "-";
                                                    		    String tryPriceStr = "-";
                                                    		    String totalPriceStr = "-";
                                                    		    
                                                    		for(Vector v : costList){
                                                    		    priceV = v;
                                                    		
                                                    		    if (priceV.size() > 0) {
                                                                    int designPrice = 0;
                                                                    int moldMaking = 0;
                                                                    int tryPrice = 0;
                                                                    int total = 0;

                                                                    for (int i = 0; i < priceV.size(); i++) {
                                                                        Hashtable pHash = (Hashtable) priceV.get(i);
                                                                        Integer integer = (Integer) pHash.get(SAPMoldPrice.MOLD_DESIGN);

                                                                        if (integer != null) {
                                                                        designPrice += integer.intValue();
                                                                        //designPriceStr = nf.format(integer.intValue());
                                                                        }

                                                                        integer = (Integer) pHash.get(SAPMoldPrice.MOLD_MAKING);
                                                                        if (integer != null) {
                                                                        moldMaking += integer.intValue();
                                                                        //moldMakingPrice = nf.format(integer.intValue());
                                                                        }

                                                                        integer = (Integer) pHash.get(SAPMoldPrice.MOLD_TRY);
                                                                        if (integer != null) {
                                                                        tryPrice += integer.intValue();
                                                                        //tryPriceStr = nf.format(integer.intValue());
                                                                        }

                                                                        integer = (Integer) pHash.get(SAPMoldPrice.TOTAL);
                                                                        if (integer != null) {
                                                                        total += integer.intValue();
                                                                        //totalPriceStr = nf.format(integer.intValue());
                                                                        }
                                                                    }
                                                                    designPriceStr = nf.format(designPrice);
                                                                    moldMakingPrice = nf.format(moldMaking);
                                                                    tryPriceStr = nf.format(tryPrice);
                                                                    totalPriceStr = nf.format(total);
                                                                }
                                                    		}
                                                            %>
                                                            <tr>
                                                                <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02829") %><%--총비용--%></td>
                                                                <td class="tdwhiteR"><%=designPriceStr%></td>
                                                                <td class="tdwhiteR"><%=moldMakingPrice%></td>
                                                                <td class="tdwhiteR"><%=tryPriceStr%></td>
                                                                <td class="tdwhiteR0"><%=totalPriceStr%></td>
                                                            </tr>
                                                            <%
                                                                designPriceStr = "-";
                                                    		    moldMakingPrice = "-";
                                                    		    tryPriceStr = "-";
                                                    		    totalPriceStr = "-";
                                                    		
                                                    		    for(Vector v : costList){
                                                    			 
                                                    		      if (priceV.size() > 0) {
                                                                  
                                                    			      Hashtable hash = (Hashtable) priceV.get(0);
                                                    			      Integer integer = (Integer) hash.get(SAPMoldPrice.MOLD_DESIGN);
                                                                      
                                                    			      if (integer != null) {
                                                    				    designPriceStr = nf.format(integer.intValue());
                                                    			      }
            
                                                                      
                                                    			      integer = (Integer) hash.get(SAPMoldPrice.MOLD_MAKING);
                                                                      
                                                    			      if (integer != null) {
                                                    				      moldMakingPrice = nf.format(integer.intValue());
                                                    			      }
            
                                                    			      
                                                                      
                                                    			      integer = (Integer) hash.get(SAPMoldPrice.MOLD_TRY);
                                                                      
                                                    			      if (integer != null) {
                                                    				      tryPriceStr = nf.format(integer.intValue());
                                                    			      }
            
                                                                      
                                                    			      integer = (Integer) hash.get(SAPMoldPrice.TOTAL);
                                                                      
                                                    			      if (integer != null) {
                                                    				      totalPriceStr = nf.format(integer.intValue());
                                                    			      }
                                                    			  }
                                                    		
                                                    		    }

                                                    		    
                                                            %>
                                                            <tr>
                                                                <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02821") %><%--초도--%></td>
                                                                <td class="<%="-".equals(designPriceStr)?"tdwhiteM":"tdwhiteR"%>"><%=designPriceStr%></td>
                                                                <td class="<%="-".equals(moldMakingPrice)?"tdwhiteM":"tdwhiteR"%>"><%=moldMakingPrice%></td>
                                                                <td class="<%="-".equals(tryPriceStr)?"tdwhiteM":"tdwhiteR"%>"><%=tryPriceStr%></td>
                                                                <td class="tdwhiteR0"><%=totalPriceStr%></td>
                                                            </tr>
                                                            <%
                                                                for(int i = 1; i < debugingVector.size(); i++){
                                                            			designPriceStr = "-";
                                                            			moldMakingPrice = "-";
                                                            			tryPriceStr = "-";
                                                            			totalPriceStr = "-";
                                                            			Hashtable pHash = (Hashtable) debugingVector.get(i);
                                                            			Integer integer = (Integer) pHash.get(SAPMoldPrice.MOLD_DESIGN);
                                                            			if (integer != null) {
                                                            			    designPriceStr = nf.format(integer.intValue());
                                                            			}

                                                            			integer = (Integer) pHash.get(SAPMoldPrice.MOLD_MAKING);
                                                            			if (integer != null) {
                                                            			    moldMakingPrice = nf.format(integer.intValue());
                                                            			}

                                                            			integer = (Integer) pHash.get(SAPMoldPrice.MOLD_TRY);
                                                            			if (integer != null) {
                                                            			    tryPriceStr = nf.format(integer.intValue());
                                                            			}

                                                            			integer = (Integer) pHash.get(SAPMoldPrice.TOTAL);
                                                            			if (integer != null) {
                                                            			    totalPriceStr = nf.format(integer.intValue());
                                                            			}
                                                            %>
                                                            <tr>
                                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01339")%><%--디버깅--%></td>
                                                                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", i)%><%--{0}차--%></td>
                                                                <td class="<%="-".equals(designPriceStr)?"tdwhiteM":"tdwhiteR"%>"><%=designPriceStr%></td>
                                                                <td class="<%="-".equals(moldMakingPrice)?"tdwhiteM":"tdwhiteR"%>"><%=moldMakingPrice%></td>
                                                                <td class="<%="-".equals(tryPriceStr)?"tdwhiteM":"tdwhiteR"%>"><%=tryPriceStr%></td>
                                                                <td class="tdwhiteR0"><%=totalPriceStr%></td>
                                                            </tr>

                                                            <%}%>

                                                        </table></td>
                                                </tr>
                                            </table> 
                                            <%
                                                }
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
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>
