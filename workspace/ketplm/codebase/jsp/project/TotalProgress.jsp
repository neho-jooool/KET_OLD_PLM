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

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>

<%
try{
  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  E3PSProjectData projectData = new E3PSProjectData(project);
%>

<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="820" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="820" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02736") %><%--진행현황--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top" style="padding:10px 10px 10px 10px"><table width="820" height="400" border="0" cellspacing="0" cellpadding="0">
        <tr>

          <td valign="top"><table width="800" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="15" background="/plm/portal/images/stick_red.gif"></td>
                        <td><b>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></b></td>
                        <td width="10"></td>
                        <td width="15" background="/plm/portal/images/stick_green.gif"></td>
                        <td><b>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></b></td>
                      </tr>
                    </table></td>
                <td>&nbsp;</td>
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
                <td width="150" class="tdblueM" style="height:70"><%=messageService.getString("e3ps.message.ket_message", "02404") %><%--자동차일정--%></td>
                <td width="630" class="tdwhiteL0" style="height:70"><table width="630" style="height:70" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><table width="630" border="0" cellspacing="0" cellpadding="0" style="height:16" id="bar1">
                            <tr><td width="100%" background="/plm/portal/images/stick_gray.gif"></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table></td>
              </tr>
              <tr>
                <td width="150" class="tdblueM" style="height:100"><%=messageService.getString("e3ps.message.ket_message", "02630") %><%--제품프로젝트--%><br>
                  <%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%><br>
                  <%=projectData.pjtCompletion%> / <%=projectData.getPreferCompStr()%></td>
                <td width="630" class="tdwhiteL0" style="height:130"><table width="640" style="height:130" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><table width="630" border="0" cellspacing="0" cellpadding="0" style="height:16" id="bar2">
                            <tr><td width="<%=projectData.pjtCompletion%>%"
                              <%if(projectData.pjtCompletion >= Double.parseDouble(projectData.getPreferCompStr())) {%>background="../../portal/images/stick_green.gif"
                              <%}else {%>background="../../portal/images/stick_red.gif"<%}%>></td>
                              <td width="<%=100-projectData.pjtCompletion%>%" background="../../portal/images/stick_gray.gif"></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table></td>
              </tr>
              <%
                Vector moldVec = new Vector();
                int num = 0;
        int barCount = 2;
                QueryResult moldQr = MoldProjectHelper.getRelationMoldProject((ProductProject)project);

        if(moldQr != null){
          while(moldQr.hasMoreElements()){
            Object[] obj = (Object[])moldQr.nextElement();
            MoldProject moldProject = (MoldProject)obj[0];
            MoldItemInfo moldInfo = moldProject.getMoldInfo();
            E3PSProjectData moldProjectData = new E3PSProjectData(moldProject);
            //Kogger.debug("moldProjectData == " + moldProjectData);
            moldVec.add(moldProjectData);
            //Kogger.debug("########");
            num++;
            barCount++;
              %>
              <tr>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01024") %><%--금형 프로젝트--%><br><%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%><br>
                  <%=moldProjectData.pjtCompletion%> / <%=moldProjectData.getPreferCompStr()%><br>
                  <table width="95%" cellpadding="0" cellspacing="0" class="table_border">
                    <tr>
                      <td width="40%" class="tdgrayL">Part No</td>
                      <td width="60%" class="tdwhiteL"><%=moldInfo.getPartNo()==null?"&nbsp;":moldInfo.getPartNo() %></td>
                    </tr>
                    <tr>
                      <td width="40%" class="tdgrayL">Die No</td>
                      <td width="60%" class="tdwhiteL"><%=moldInfo.getDieNo()==null?"&nbsp;":moldInfo.getDieNo() %></td>
                    </tr>
                  </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="95%">
                    <tr>
                      <td class="space5"></td>
                    </tr>
                  </table></td>
                  <%
                  double moldStart = (DateUtil.getDuration(projectData.pjtPlanStartDate, moldProjectData.pjtPlanStartDate) + 1)/(DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtPlanEndDate) + 1)*100;
                  double moldEnd = 0;

                  if(projectData.pjtPlanEndDate == null  && projectData.pjtPlanStartDate != null && moldProjectData.pjtPlanEndDate == null  && moldProjectData.pjtPlanStartDate != null){
                  moldEnd = (DateUtil.getDuration(moldProjectData.pjtPlanStartDate, moldProjectData.pjtPlanEndDate) + 1)/(DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtPlanEndDate) + 1)*100;
                  }
                  double moldPrefer = 0;
                  if(!moldProjectData.getPreferCompStr().equals("0")){
                    moldPrefer = Double.parseDouble(moldProjectData.getPreferCompStr());
                  }

                  %>
                     <td width="630" class="tdwhiteL0" style="height:100">
                     <table style="height:5" valign="top"><tr valign="top"><td valign="top">Part Name : <%=moldInfo.getPartName()==null?"&nbsp;":moldInfo.getPartName() %></td></tr></table>
                     <table width="630" style="height:100" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><table width="630" border="0" cellspacing="0" cellpadding="0" style="height:16" id="bar<%=barCount%>">
                            <tr>
                              <td width="<%=moldStart%>%" background="../../portal/images/stick_gray.gif"></td>
                              <td width="<%=moldEnd*(moldProjectData.pjtCompletion/100)%>%"
                              <%if(moldProjectData.pjtCompletion >= moldPrefer) {%>background="../../portal/images/stick_green.gif"
                              <%}else {%>background="../../portal/images/stick_red.gif"<%}%>
                              ></td>
                              <td width="<%=100-moldStart-moldEnd*(moldProjectData.pjtCompletion/100)%>%" background="../../portal/images/stick_gray.gif"></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table></td>
              </tr>
              <%
                  }
                }
              %>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30"></td>
  </tr>
</table>

<%
  ArrayList customerEventArr = new ArrayList();
  QueryResult opqr = OEMTypeHelper.getCustomerEvent(project.getOemType());
  int count = 0;
  if(opqr!=null){
    while(opqr.hasMoreElements()){
      Object[] oo = (Object[])opqr.nextElement();
      OEMPlan op = (OEMPlan)oo[0];
      if(op.getOemEndDate() == null){
        continue;
      }
      Date oemEndDate = new Date(op.getOemEndDate().getTime());
      if( oemEndDate.after( new Date(projectData.pjtPlanStartDate.getTime()-1) ) && oemEndDate.before( new Date(projectData.pjtPlanEndDate.getTime()+1) ) ) {
        customerEventArr.add(op);

        double per = 0;
        per = (DateUtil.getDuration(projectData.pjtPlanStartDate, op.getOemEndDate()) + 1);
        per = per / (DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtPlanEndDate) + 1);
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
<div id="stickLayerTwo<%=i%>" style="position:absolute; z-index:1;" value="<%=per%>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="font_stick" title="<%//=taskData.taskName%>"><div style="width:100;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                <nobr>&nbsp;<%//=taskData.taskName%></nobr></div></td>
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
<div id="stickLayerTwoName<%=i%>" style="position:absolute; z-index:1;" value="<%=per%>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="font_stick" title="<%=taskData.taskName%>"><div style="width:100;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                <nobr><%=taskData.taskName%></nobr></div></td>
    </tr>
  </table>
</div>
<%} %>




<%

  Vector mileCount = new Vector();
    if(barCount > 2) {
      for(int k = 0; k < moldVec.size(); k++){
        E3PSProjectData moldProjectData = (E3PSProjectData)moldVec.get(k);
        ArrayList milestoneArr2 = moldProjectData.getMilestoneTasks();
        int j = 0;
        for(int i=0; i<milestoneArr2.size(); i++) {
          double per2 = 0;
          E3PSTaskData taskData2 = new E3PSTaskData((E3PSTask)milestoneArr2.get(i));
          String taskEndDate = DateUtil.getTimeFormat(taskData2.taskPlanEndDate, "yy/MM/dd");

          if(projectData.pjtExecStartDate != null) {
            if(taskData2.taskExecEndDate != null) {
              taskEndDate = DateUtil.getTimeFormat(taskData2.taskExecEndDate, "yy/MM/dd");
              per2 = (DateUtil.getDuration(projectData.pjtExecStartDate, taskData2.taskExecEndDate) + 1);
            }else {
              per2 = (DateUtil.getDuration(projectData.pjtExecStartDate, taskData2.taskPlanEndDate) + 1);
            }

            if(projectData.pjtExecEndDate != null)
              per2 = per2 / (DateUtil.getDuration(projectData.pjtExecStartDate, projectData.pjtExecEndDate) + 1);
            else
              per2 = per2 / (DateUtil.getDuration(projectData.pjtExecStartDate, projectData.pjtPlanEndDate) + 1);
          }else {
            if(taskData2.taskExecEndDate != null) {
              taskEndDate = DateUtil.getTimeFormat(taskData2.taskExecEndDate, "yy/MM/dd");
              //Kogger.debug("endDate =  " + taskEndDate);
              per2 = (DateUtil.getDuration(projectData.pjtPlanStartDate, taskData2.taskExecEndDate) + 1);
            }else {
              per2 = (DateUtil.getDuration(projectData.pjtPlanStartDate, taskData2.taskPlanEndDate) + 1);
            }

            if(projectData.pjtExecEndDate != null)
              per2 = per2 / (DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtExecEndDate) + 1);
            else
              per2 = per2 / (DateUtil.getDuration(projectData.pjtPlanStartDate, projectData.pjtPlanEndDate) + 1);
          }
          if(per2 > 1.0){
            continue;
          }
%>
<div id="<%=k+3%>stickLayerThree<%=j%>" style="position:absolute; z-index:1;" value="<%=per2%>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="font_stick" title="<%=taskData2.taskName%>"><div style="width:100;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                <nobr>&nbsp;</nobr></div></td>
    </tr>
    <tr>
      <td align="center"><img src="/plm/portal/icon/bar_arrow1.gif"><img src="/plm/portal/icon/bar_arrow2.gif"></td>
    </tr>
    <tr height="15">
      <td></td>
    </tr>
    <tr>
      <td align="center" class="font_stick" title="<%=taskEndDate%>"><%=taskData2.getStateStr2()%></td>
    </tr>
  </table>
</div>
<div id="<%=k+3%>stickLayerThreeName<%=j%>" style="position:absolute; z-index:1;" value="<%=per2%>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center" class="font_stick" title="<%=taskData2.taskName%>"><div style="width:100;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                <nobr><%=taskData2.taskName%></nobr></div></td>
    </tr>
  </table>
</div>
<%        j++;
        }
        mileCount.add(j);
      }
    }
  //}
%>

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
  function showLayer3(target, layer, i) {
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


  var moldCount = 0;
  moldCount = eval(<%=barCount%>);

  for(j=3; j<moldCount+1; j++) {
    <%
    for(int k = 0; k < moldVec.size(); k++){
    E3PSProjectData moldProjectData = (E3PSProjectData)moldVec.get(k);

    ArrayList milestoneArr2 = moldProjectData.getMilestoneTasks();

    if(milestoneArr2.size() > 0){
      Kogger.debug("countNum == " + mileCount.get(k));
    String countNum = mileCount.get(k).toString();
    for(int i = 0; i < Integer.parseInt(countNum); i++){

    %>
    if(j == <%=k+3 %>){
      showLayer(eval("bar"+j), j+"stickLayerThree"+<%=i%>);
      showLayer3(eval("bar"+j), j+"stickLayerThreeName"+<%=i%>, '<%=i%>');
    }
    <%
    }

    }}%>
  }
<%}catch(Exception e){Kogger.error(e);}%>
</script>
