<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.beans.PerformanceHelper"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.Performance"%>
<%@page import="e3ps.project.Weights"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.PerformType"%>
<%@page import="java.util.Locale"%>
<%@page import="wt.lifecycle.LifeCycleTemplate"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.lifecycle.LifeCycleHelper"%>
<%@page import="wt.lifecycle.PhaseTemplate"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.project.beans.MoldProjectHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
  String cmd = StringUtil.checkNull(request.getParameter("cmd"));
  String oid = StringUtil.checkNull(request.getParameter("oid"));
  String pOid = StringUtil.checkNull(request.getParameter("pOid"));

  Performance pf = null;
  //pf = (Performance)CommonUtil.getObject(pOid);               //2013.04.10 e3ps shkim 주석
  if(pOid.length() > 0){
    pf = (Performance)CommonUtil.getObject(pOid);
  }
  E3PSProject project = null;
  if(StringUtil.checkString(oid) && oid.indexOf("Project") != -1){
   	project = (E3PSProject)CommonUtil.getObject(oid);
  //}else if(pOid.length() > 0){                                //2013.04.10 e3ps shkim 주석
  //  project = (E3PSProject)CommonUtil.getObject(pOid);        //2013.04.10 e3ps shkim 주석
  }else{
    project = ProjectHelper.getProject(pf.getKeyNo());
  }

  Kogger.debug("project ------------------------------- > " + project);
  
  E3PSProjectData projectData = new E3PSProjectData(project);
  boolean isPM = ProjectUserHelper.manager.isPM(project);
  String pfRankOne = "";
  String pfRankTwo = "";
  String pfdescMsg = "";
  String isBomCheck = "";
  String isPackageCheck = "";
  String isMassCheck = "";
  String isAppraisalCheck = "";
  String isPrecedenceCheck = "";
  String isEtcCheck = "";
  String title = messageService.getString("e3ps.message.ket_message", "01310")/*등록*/;
  if(pOid == null || pOid.length() == 0){
    pOid = "";
    title = "-";
  }



  Weights wg = null;
  PerformType pt = null;
  QueryResult wgQr = null;
  QueryResult ptQr = null;

  int wgQuality = 0;
  int wgCost = 0;
  int wgD1 =0;
  int wgD2 = 0;
  int wgD3 = 0;
  int wgTechnical = 0;
  String  wgOid = "";
  if(pOid.length() > 0 ){
    title = messageService.getString("e3ps.message.ket_message", "01527")/*보기*/;
    //pf = (Performance)CommonUtil.getObject(pOid);
    pfRankOne = (pf.getRankOne()==null)?"":pf.getRankOne();
    pfRankTwo = (pf.getRankTwo()==null)?"":pf.getRankTwo();
    pfdescMsg = (pf.getDescMsg()==null)?"":pf.getDescMsg();

    wgQr = PerformanceHelper.manager.searchWeights(0, true, pf.getKeyNo());
    Object[] obj = null;

    if(wgQr.hasMoreElements()){
      obj = (Object[])wgQr.nextElement();
      wg = (Weights)obj[0];
      wgOid = CommonUtil.getOIDString(wg);
    }
    if(wg != null){
      wgQuality = wg.getQuality();
      wgCost = wg.getCost();
      wgD1 = wg.getDeliveryOne();
      wgD2 = wg.getDeliveryTwo();
      wgD3 = wg.getDeliveryThree();
      wgTechnical = wg.getTechnical();
    }


    ptQr = PerformanceHelper.manager.searchPerformType(CommonUtil.getOIDLongValue(pf));
    Object[] ptObj = null;
    if(ptQr.hasMoreElements()){
      ptObj = (Object[])ptQr.nextElement();
      pt = (PerformType)ptObj[0];
    }
    if(pt != null){
      isBomCheck = "" + pt.isIsBom();
      isPackageCheck = "" + pt.isIsPackage();
      isMassCheck = "" + pt.isIsMass();
      isAppraisalCheck = "" + pt.isIsAppraisal();
      isPrecedenceCheck = "" + pt.isIsPrecedence();
      isEtcCheck = "" + pt.isIsEtc();
    }
  }

  String msg = "";
  if(cmd.equals("delete")){
    try{
      msg = PerformanceHelper.manager.deleteAction(pOid);
    }catch(Exception e){ Kogger.error(e); }
          if(msg.equals("S")){
      %>
          <script>
            alert("<%=messageService.getString("e3ps.message.ket_message", "01692") %><%--삭제 되었습니다--%>");
            opener.document.forms[0].submit();
            self.close();
          </script>
      <%
          }else{
      %>
          <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "01694") %><%--삭제 실패--%>');
            opener.document.forms[0].submit();
            self.close();
          </script>
      <%
          }
  }

  int totalScore = 0;
  String totalEvaluate = "";
  if(wg != null ){
    if(wg.getWType() == 1 || wg.getWType() == 2){
      if(pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null && pf.getScoreTechnical() != null){
        totalScore = Integer.parseInt(pf.getScoreQuality()) +Integer.parseInt(pf.getScoreCost())+Integer.parseInt(pf.getScoreDelivery1())+Integer.parseInt(pf.getScoreTechnical());
      }
    }if(wg.getWType() == 3){
      if(pf.getScoreCost() != null && pf.getScoreDelivery1() != null && pf.getScoreDelivery2() != null && pf.getScoreDelivery3() != null){
        totalScore = Integer.parseInt(pf.getScoreCost())+Integer.parseInt(pf.getScoreDelivery1())+Integer.parseInt(pf.getScoreDelivery2())+Integer.parseInt(pf.getScoreDelivery3());
      }
    }if(wg.getWType() == 4 || wg.getWType() == 5){
      if(pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null){
        totalScore = Integer.parseInt(pf.getScoreQuality()) +Integer.parseInt(pf.getScoreCost())+Integer.parseInt(pf.getScoreDelivery1());
      }
    }
    if(totalScore != 0 ){
      if(wg.getTotalS() <= totalScore){
        totalEvaluate = "S";
      }else if(wg.getTotalA() <= totalScore){
        totalEvaluate = "A";
      }else if(wg.getTotalB() <= totalScore){
        totalEvaluate = "B";
      }else if(wg.getTotalC() <= totalScore){
        totalEvaluate = "C";
      }else {
        totalEvaluate = "D";
      }
    }
  }


  java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();



%>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=title %></title>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="JavaScript" src="/plm/portal/js/Calendar.js"></script>

<style type="text/css">

body {
  margin-left: 15px;
  margin-top: 12px;
  margin-right: 0px;
  margin-bottom: 0px;
}

</style>
<script language="JavaScript">

  function onSaveMove(){
    form = document.forms[0];
    form.action = "/plm/jsp/project/performance/CreatePerformance.jsp?oid=<%=oid%>&pOid=<%=pOid%>";
    form.submit();
  }
  function onDelete(){
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')) {
      return;
    }
    form = document.forms[0];
    form.cmd.value = "delete";
    form.action = "/plm/jsp/project/performance/CreatePerformance.jsp";
    form.submit();
  }

  function displayDesc(obj){
    var objcheck = obj.checked;
    if(objcheck){
      desc.style.display='block';
    }else{
      desc.style.display='none';
    }
  }
  var ajaxType;
  function onStateChange(s_obj, lcm_oid) {
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03314") %><%--상태를 변경하시겠습니까?--%>")) {
      return;
    }

    if(s_obj.value == '') {
      return;
    }

    var param = "?command=stateChange&oid=" + lcm_oid + "&state=" + s_obj.value;


    ajaxType = "stateChange";

    //onProgressBar();

    var url = "/plm/jsp/project/WorkProcessAjaxAction.jsp" + param;
    callServer(url, onStateMessage);
  }
  function onStateMessage(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;

    var alertMsg = "";
    if(msg == 'true') {
      if(ajaxType == "stateChange") {
        alertMsg = "<%=messageService.getString("e3ps.message.ket_message", "01764") %><%--상태변경을 완료했습니다\n화면을 다시 로드하겠습니다--%>";
      }
    } else {
      if(ajaxType == "stateChange") {
        alertMsg = "<%=messageService.getString("e3ps.message.ket_message", "01763") %><%--상태변경을 완료하지 못했습니다\n다시 시도하시기 바랍니다\n화면을 다시 로드하겠습니다--%>";
      }
    }


    alert(alertMsg);

    document.forms[0].submit();
  }
  function approvalAction(oid){
    var url="/plm/jsp/wfm/RequestApproval.jsp?pboOid="+oid;
    openOtherName(url,"approval","800","700","status=no,scrollbars=yes,resizable=no");
  }
  /* function viewHistory(pboOid){
    var url = '/plm/jsp/wfm/ApprovalHistory.jsp?pboOid='+pboOid;
    window.open(url,'결재이력',"status=1, menu=no, width=730, height=800");
  } */

  function valuationAction(oid){
    var url="/plm/jsp/project/performance/ViewValuation.jsp?oid="+oid;
    openOtherName(url,"approval","810","420","status=no,scrollbars=no,resizable=no");
  }

</script>
</head>

<%@include file="/jsp/project/template/ajaxProgress.html"%>
<body>
<form>

<!-- hidden begin -->
<input type="hidden" name="cmd" value="">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="pOid" value="<%=pOid%>">
<!-- hidden end -->

<!-- ############################################################################################################ START -->
<table width="810" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top">
      <table width="810" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01894") %><%--성과 관리 보기--%></td>
                      <td width="10"></td>
                    </tr>
                </table>
                </td>
                <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
              </tr>
            </table>
              </td>
          </tr>
          <tr>
            <td class="space10"></td>
          </tr>
          </table>
         <table width="810" border="0" cellspacing="0" cellpadding="0">
        <tr>
                <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                <td>&nbsp;
        <%if(pf != null && CommonUtil.isAdmin() ){%>
           &nbsp;<%=pf.getLifeCycleState().getDisplay(messageService.getLocale())%><font color="red">
        <%if(CommonUtil.isAdmin() && false){ // 상태 변경처리 오류로 주석처리%>
        <select name="state0" class="fm_jmp" style="width:110" onchange="javascript:onStateChange(this, '<%=pf.getPersistInfo().getObjectIdentifier().getStringValue()%>');">
        <%
          String curState = pf.getLifeCycleState().toString();
          try {
            //LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_ECA");
            LifeCycleTemplate lct = (LifeCycleTemplate)pf.getLifeCycleTemplate().getObject();
            Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
            PhaseTemplate ptt = null;
            wt.lifecycle.State lcState = null;
            for(int i = 0; i < states.size(); i++) {
              ptt = (PhaseTemplate)states.get(i);
              lcState = ptt.getPhaseState();
        %>
            <OPTION VALUE="<%=lcState.toString()%>" <%if(curState.equals(lcState.toString())){%>selected<%}%>><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
        <%
            }
          } catch(Exception e) {
              Kogger.error(e);
          }
        %>
        </select>
        <%}%>
        <%}%>
                &nbsp;
                </td>
                    <%
                    if(pf.getState().toString().equals("INWORK") || pf.getState().toString().equals("REWORK")) {
                    %>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:approvalAction('<%=pOid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td>&nbsp;</td>
                    <%} %>
                    <%if(wg.getWType() != 3){  %>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="Javascript:viewHistory('<%=pOid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00785") %><%--결재이력--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <%} %>
                    <td>&nbsp;</td>
                     <%if(isPM || CommonUtil.isAdmin() || wg.getWType() == 3 && isPM){ %>
                     <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSaveMove();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <%if(pf.getState().toString().equals("INWORK")&& isPM || CommonUtil.isAdmin()){ %>
                    <td>&nbsp;</td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>

                    <%  }
                    }%>
                    <%if(CommonUtil.isAdmin() || isPM || true){ %>
                    <td>&nbsp;</td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:valuationAction('<%=wgOid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02987") %><%--평가기준표--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td>&nbsp;</td>
                    <%} %>

                    </tr>
                  </table>
                </td>
        </tr>
      </table>
    </td>
     </tr>

<!-- ############################################################################################################ START -->

     <!-- ###################################            Main    Start -->

     <tr>
       <td>

      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
        <%
        String dept = "지정안됨";
        String duty = "지정안됨";
        if(projectData.pjtPm != null){
        PeopleData peopleData = new PeopleData(projectData.pjtPm);
          dept = peopleData.departmentName;
          duty = peopleData.duty;
        }
        %>

      <%if( wg.getWType() == 1 || wg.getWType() == 2 || wg.getWType() == 4 || wg.getWType() == 5){ %>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
      <COL width="15%"><COL width="35%"><COL width="15%"><COL width="35%">

        <tr>
                    <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
          <td class="tdwhiteL" >&nbsp;<%=project.getPjtNo() %> </td>
                <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%> </td>
          <td class="tdwhiteL" >&nbsp;<%=project.getDevelopentType()==null?"":StringUtil.checkNull(project.getDevelopentType().getName())%> </td>
              </tr>
        <tr>
          <td class="tdblueL" >Part No </td>
          <td class="tdwhiteL" >&nbsp;<%=(projectData.partNo==null)?"":projectData.partNo%></td>
                <td class="tdblueL" >PM </td>
          <td class="tdwhiteL" title="<%=projectData.pjtPmName%>/<%=duty%>/<%=dept%>"><div style="width:90;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=projectData.pjtPmName%>/<%=duty%>/<%=dept%></nobr></div></td>
              </tr>
        <tr>
                    <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
          <td class="tdwhiteL" >&nbsp;<%=project.getPjtName() %> </td>
                <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%> </td>
          <td class="tdwhiteL" >&nbsp;

          <%=DateUtil.getDateString(projectData.pjtPlanStartDate,"D")%>

          </td>
              </tr>
            </table>
      <%} else if(wg.getWType() == 3){

        Object obj = (Object)CommonUtil.getObject(oid);
        MoldProject moldProject = null;
        MoldItemInfo moldInfo = null;
        ProductProject productProject = null;
        E3PSProjectData productData2 = null;
        PeopleData peopleData2 = null;

        String dieNo = "";
        String partNo = "";
        String partName = "";

        if(obj instanceof MoldProject){
          moldProject = (MoldProject)obj;
          moldInfo = moldProject.getMoldInfo();
          productProject = moldInfo.getProject();
          productData2 = new E3PSProjectData(productProject);
          peopleData2 = new PeopleData(productData2.pjtPm);
          dieNo = moldInfo.getDieNo();
          partNo = moldInfo.getPartNo();
          partName = moldInfo.getPartName();
        }else if(obj instanceof ProductProject){
          productProject = (ProductProject)obj;
          productData2 = new E3PSProjectData(productProject);
          peopleData2 = new PeopleData(productData2.pjtPm);
        }

        //MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);
        //MoldItemInfo moldInfo = moldProject.getMoldInfo();
        //ProductProject productProject = moldInfo.getProject();
        //E3PSProjectData productData2 = new E3PSProjectData(productProject);
        //PeopleData peopleData2 = new PeopleData(productData2.pjtPm);
        String dept2 = "지정안됨";
        String duty2 = "지정안됨";
        if(productData2.pjtPm != null){
          dept2 = peopleData2.departmentName;
          duty2 = peopleData2.duty;
        }
      %>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
      <COL width="13%"><COL width="20%"><COL width="13%"><COL width="20%"><COL width="14%"><COL width="20%">
        <tr>
          <td class="tdblueL" >Project No </td>
          <td class="tdwhiteL" >&nbsp;<%=productProject.getPjtNo() %> </td>
                <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01055") %><%--금형담당--%> </td>
          <td class="tdwhiteL" title="<%=projectData.pjtPmName%>/<%=duty%>/<%=dept%>"><div style="width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=projectData.pjtPmName%>/<%=duty%>/<%=dept%></nobr></div></td>
                <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "00631") %><%--개발담당--%> </td>
          <td class="tdwhiteL0" title="<%=productData2.pjtPmName%>/<%=duty2%>/<%=dept2%>"><div style="width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=productData2.pjtPmName%>/<%=duty2%>/<%=dept2%></nobr></div></td>

              </tr>
        <tr>
          <td class="tdblueL" >Die No </td>
          <td class="tdwhiteL" >&nbsp;<%=dieNo%></td>
          <td class="tdblueL" >Part No </td>
          <td class="tdwhiteL" >&nbsp;<%=partNo%></td>
                <td class="tdblueL" >Rank</td>
          <td class="tdwhiteL0" >&nbsp;<%=productProject.getRank()==null?"":StringUtil.checkNull(productProject.getRank().getName())%> </td>

              </tr>
        <tr>
          <td class="tdblueL" >Part Name </td>
          <td class="tdwhiteL" colspan=3 title="<%=partName%>"><div style="width:60;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr>&nbsp;<%=partName%></nobr></div></td>
                 <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%> </td>
          <td class="tdwhiteL0" >&nbsp;
          <%
          E3PSTask task = MoldProjectHelper.getTask((E3PSProject)project, "제품도출도");
          if(task != null){
          E3PSTaskData taskData = new E3PSTaskData(task);
            if(taskData.taskExecEndDate != null){
          %>
            <%=DateUtil.getDateString(taskData.taskExecEndDate,"D")%>
            <%}
          }%>
          </td>
              </tr>
              <tr>
                <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%> </td>
          <td class="tdwhiteL" >&nbsp;<%=productProject.getDevelopentType()==null?"":StringUtil.checkNull(productProject.getDevelopentType().getName())%> </td>
                      <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%></td>
          <td class="tdwhiteL" >&nbsp;<%=productProject.getProductType() == null?"" : productProject.getProductType().getName()%></td>
                      <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td class="tdwhiteL0">&nbsp;<%=(pf==null)?"":pf.getCreatorFullName()%></td>
              </tr>

            </table>
      <%} %>

      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <%if(wg.getWType() != 3){  %>
      <table border="0" cellspacing="0" cellpadding="0" width="300">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="300">
        <tr>
              <td width="120" class="tdblueL">Rank</td>
              <td width="180" class="tdwhiteL"><%=pfRankTwo%>_<%=pfRankOne%>
              </td>
              </tr>
      </table>
      <%} %>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
                    <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%></td>
          <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%> </td>
                      <td class="tdblueM" colspan='7' ><%=messageService.getString("e3ps.message.ket_message", "02988") %><%--평가요소--%></td>
        </tr>
        <%
        int rowspanSetting = 7;
        if(project.getPjtType() == 4){
          rowspanSetting = 6;
        }
        if(project.getPjtType() == 3){
          rowspanSetting = 7;
        }

        %>

        <tr>
          <td class="tdblueM" rowspan='<%=rowspanSetting%>' ><%=messageService.getString("e3ps.message.ket_message", "00400", "<br>") %><%--Project{0} 목표달성도--%></td>
          <td class="tdwhiteM" rowspan='<%=rowspanSetting%>'> 100%</td>
          <td class="tdblueM" width="90"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
          <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%></td>
          <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
          <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "02503") %><%--점수--%></td>
          <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "02982") %><%--평가--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02988") %><%--평가요소--%></td>
              </tr>
<%if(wg != null){ %>
        <%if( wg.getWType() == 1 || wg.getWType() == 2 || wg.getWType() == 4 || wg.getWType() == 5){ %>

        <%
        	String drNum = "6";
        	E3PSTask ett3 = MoldProjectHelper.getTask((E3PSProject)project, "DR6");
        	if(ett3 == null){
        		drNum = "3";
        	}
        %>

              <tr>
          <td class="tdblueM" >Quality</td>
          <td class="tdwhiteM" ><%=(pf.getPlanQuality()==null)?"&nbsp;":pf.getPlanQuality() %></td>
                <td class="tdwhiteM" ><%=wgQuality%>% </td>
          <td class="tdwhiteM"><%=(pf.getResultQuality()==null)?"&nbsp;":pf.getResultQuality()%> </td>
          <td class="tdyellowM"><%=(pf.getScoreQuality()==null)?"&nbsp;":pf.getScoreQuality()%> </td>
          <td class="tdyellowM"><%=(pf.getEvaluateQuality()==null)?"&nbsp;":pf.getEvaluateQuality()%>  </td>
          <td class="tdwhiteL" ><%if( wg.getWType() == 1 || wg.getWType() == 2 ){ %> <%=messageService.getString("e3ps.message.ket_message", "03047", 6) %><%--프로젝트 DR{0} 점수--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "03047", drNum) %><%--프로젝트 DR{0} 점수--%><%}%></td>
              </tr>
              <%} %>
        <tr>
          <td class="tdblueM" >Cost</td>
          <td class="tdwhiteM" ><%=(pf.getPlanCost()==null)?"&nbsp;":nf.format( Long.parseLong( pf.getPlanCost() )) %></td>
                <td class="tdwhiteM" ><%=wgCost%>% </td>
          <td class="tdwhiteM"><%=(pf.getResultCost()==null)?"&nbsp;":nf.format(Long.parseLong( pf.getResultCost() ))%> </td>
          <td class="tdyellowM"><%=(pf.getScoreCost()==null)?"&nbsp;":pf.getScoreCost()%> </td>
          <td class="tdyellowM"><%=(pf.getEvaluateCost()==null)?"&nbsp;":pf.getEvaluateCost()%> </td>
                    <td class="tdwhiteL" ><%if( wg.getWType() != 3 ){ %><%=messageService.getString("e3ps.message.ket_message", "03083") %><%--프로젝트 예산 대비 실적--%><%}else{ %>목표 금형제작(디버깅포함)<br>비용 대비 실적 비율 <%}%>  </td>
              </tr>
        <tr>
          <%if( wg.getWType() == 3 ){ %>
          <td class="tdblueM" rowspan=3>Delivery</td>
          <%}else{ %>
          <td class="tdblueM">Delivery</td>
          <%} %>
          <td class="tdwhiteM" ><%=(pf.getPlanDelivery1()==null)?"&nbsp;":pf.getPlanDelivery1() %></td>
                <td class="tdwhiteM" ><%=wgD1%>% </td>
          <td class="tdwhiteM"><%=(pf.getResultDelivery1()==null)?"&nbsp;":pf.getResultDelivery1()%> </td>
          <td class="tdyellowM"><%=(pf.getScoreDelivery1()==null)?"&nbsp;":pf.getScoreDelivery1()%>  </td>
          <td class="tdyellowM"><%=(pf.getEvaluateDelivery1()==null)?"&nbsp;":pf.getEvaluateDelivery1()%> </td>
                    <td class="tdwhiteL" ><%if( wg.getWType() != 3 ){ %><%=messageService.getString("e3ps.message.ket_message", "03089") %><%--프로젝트 전체 완료 일정--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "03369") %><%--금형제작[설계~Try(T0)]--%> <%}%> </td>
              </tr>
              <%if( wg.getWType() == 3 ){ %>
        <tr>
          <td class="tdwhiteM" ><%=(pf.getPlanDelivery2()==null)?"&nbsp;":pf.getPlanDelivery2() %></td>
                <td class="tdwhiteM" ><%=wgD2%>% </td>
          <td class="tdwhiteM"><%=(pf.getResultDelivery2()==null)?"&nbsp;":pf.getResultDelivery2()%> </td>
          <td class="tdyellowM"><%=(pf.getScoreDelivery2()==null)?"&nbsp;":pf.getScoreDelivery2()%>  </td>
          <td class="tdyellowM"><%=(pf.getEvaluateDelivery2()==null)?"&nbsp;":pf.getEvaluateDelivery2()%> </td>
                    <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "01341") %><%--디버깅[Try(T0)~제품합격]--%></td>
              </tr>
        <tr>
          <td class="tdwhiteM" ><%=(pf.getPlanDelivery3()==null)?"&nbsp;":pf.getPlanDelivery3() %></td>
                <td class="tdwhiteM" ><%=wgD3%>% </td>
          <td class="tdwhiteM"><%=(pf.getResultDelivery3()==null)?"&nbsp;":pf.getResultDelivery3()%> </td>
          <td class="tdyellowM"><%=(pf.getScoreDelivery3()==null)?"&nbsp;":pf.getScoreDelivery3()%>  </td>
          <td class="tdyellowM"><%=(pf.getEvaluateDelivery3()==null)?"&nbsp;":pf.getEvaluateDelivery3()%> </td>
          <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "01096") %><%--금형이관[제품합격~금형이관]--%></td>
              </tr>
              <%} %>
              <%if( wg.getWType() == 1 || wg.getWType() == 2 ){%>
        <tr>
          <td class="tdblueM" >Technical</td>
          <td class="tdwhiteM" > <%=(pf.getPlanTechnical()==null)?"&nbsp;":pf.getPlanTechnical() %></td>
                <td class="tdwhiteM" ><%=wgTechnical%>% </td>
          <td class="tdwhiteM"><%=(pf.getResultTechnical()==null)?"&nbsp;":pf.getResultTechnical()%> </td>
          <td class="tdyellowM"><%=(pf.getScoreTechnical()==null)?"&nbsp;":pf.getScoreTechnical()%>  </td>
          <td class="tdyellowM"><%=(pf.getEvaluateTechnical()==null)?"&nbsp;":pf.getEvaluateTechnical()%> </td>
          <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "03048", "1, 2, 4") %><%--프로젝트 DR{0} 점수(평균)--%></td>
              </tr>
              <%} %>
              <%
              int ecoCount = PerformanceHelper.manager.ecogetMax(project);
              %>
        <tr>
          <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01853") %><%--설계변경건수--%> </td>
          <td class="tdwhiteM">&nbsp;</td>
                <td class="tdwhiteM">- </td>
          <td class="tdwhiteM">&nbsp;<%=ecoCount%><%=messageService.getString("e3ps.message.ket_message", "00698") %><%--건--%></td>
          <td class="tdyellowM">&nbsp;  </td>
          <td class="tdyellowM">-</td>
                    <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "03081") %><%--프로젝트 설계변경건수--%></td>
              </tr>
              <tr>
                      <td class="tdblueM" colspan='4'><%=messageService.getString("e3ps.message.ket_message", "02832") %><%--총점--%></td>
                <td class="tdyellowM">&nbsp;<%=totalScore%></td>
          <td class="tdyellowM">&nbsp;<%=totalEvaluate%></td>
          <td class="tdwhiteM" >&nbsp;</td>
              </tr>
        <%if( wg.getWType() != 3){%>
        <tr>
                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "03131") %><%--필수수행--%><br><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
          <td class="tdwhiteM" colspan='8'>
                    <input type=checkbox name="isBom" <%=(isBomCheck.equals("true"))?"checked":"" %> disabled> <%=messageService.getString("e3ps.message.ket_message", "02100") %><%--양산전 BOM 등록(ERP)--%>
                    <input type=checkbox name="isPackage" <%=(isPackageCheck.equals("true"))?"checked":"" %> disabled> <%=messageService.getString("e3ps.message.ket_message", "02992") %><%--포장사양 확정--%>
                    <input type=checkbox name="isMass" <%=(isMassCheck.equals("true"))?"checked":"" %> disabled> <%=messageService.getString("e3ps.message.ket_message", "02101") %><%--양산처 확정--%>
                    <input type=checkbox name="isAppraisal" <%=(isAppraisalCheck.equals("true"))?"checked":"" %> disabled> <%=messageService.getString("e3ps.message.ket_message", "02098") %><%--양산원가 산정--%>
          <input type=checkbox name="isPrecedence" <%=(isPrecedenceCheck.equals("true"))?"checked":"" %> disabled> <%=messageService.getString("e3ps.message.ket_message", "01829") %><%--선행양산--%>
          <input type=checkbox name="isEtc" onclick="displayDesc(this);" <%=(isEtcCheck.equals("true"))?"checked":"" %> disabled> <%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>
          </td>
              </tr>
              <%} %>
            </table>

            <%if(isEtcCheck.equals("true")){ %>
              <div id=desc style="display:block" >
            <%}else{ %>
              <div id=desc style="display:none" >
            <%} %>
            <table border="0" cellspacing="0" cellpadding="0" width="810">
              <tr>
          <td class="tdblueM" width="120"> Comment</td>
          <td class="tdwhiteM" colspan=5>
            <textarea name="descMsg" style="width:100%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" ReadOnly><%=pfdescMsg%></textarea>
          </td>
              </tr>
            </table>
      </div>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
<%} %>
       </td>
     </tr>

     <!-- ###################################            Main    End -->


<!-- ############################################################################################################ END -->
  <tr>
    <td height="30" valign="bottom"><table width="810" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10">&nbsp;</td>
        <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="810" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
      </tr>
    </table></td>
  </tr>
</table>
<!-- ############################################################################################################ END -->

</body>
</form>
</html>


