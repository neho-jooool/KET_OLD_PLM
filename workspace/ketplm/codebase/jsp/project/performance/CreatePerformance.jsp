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
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.CostInfo"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.sap.ProductPrice"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.MoldProjectHelper"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
  String cmd = StringUtil.checkNull(request.getParameter("cmd"));
  String oid = StringUtil.checkNull(request.getParameter("oid"));

  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  E3PSProjectData projectData = new E3PSProjectData(project);
  String pOid = StringUtil.checkNull(request.getParameter("pOid"));
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
    title = messageService.getString("e3ps.message.ket_message", "01310")/*등록*/;
  }


  Performance pf = null;
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




  String readOnlyStr = "";
  String readOnlyModify = "";
  if(pOid.length() > 0 ){
    title = messageService.getString("e3ps.message.ket_message", "01936")/*수정*/;

    if( project.getPjtType() == 3 ){
      if(!CommonUtil.isAdmin()){
      readOnlyStr = "readOnly";
      }
    }
    pf = (Performance)CommonUtil.getObject(pOid);
    pfRankOne = (pf.getRankOne()==null)?"":pf.getRankOne();
    pfRankTwo = (pf.getRankTwo()==null)?"":pf.getRankTwo();
    pfdescMsg = (pf.getDescMsg()==null)?"":pf.getDescMsg();

    wgQr = PerformanceHelper.manager.searchWeights(0, true, pf.getKeyNo());
    Object[] obj = null;
    if(wgQr.hasMoreElements()){
      obj = (Object[])wgQr.nextElement();
      wg = (Weights)obj[0];
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

    if(!pf.getState().toString().equals("INWORK")){
      //style='border:0;'
      //readOnlyModify = "readOnly";
    }

  }




  //##################################### KETWfmApprovalMaster

  String wfmApprovalMasterOid = "";
  if(pf != null){
    QuerySpec spec = new QuerySpec();
    int masterIdx = spec.addClassList(KETWfmApprovalMaster.class, true);
    if(spec.getConditionCount()>0) spec.appendAnd();
    spec.appendWhere(new SearchCondition(KETWfmApprovalMaster.class,
        "pboReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pf)), new int[] {masterIdx});
    spec.appendOrderBy(new OrderBy(new ClassAttribute(KETWfmApprovalMaster.class, "thePersistInfo.createStamp"),true), new int[]{masterIdx});
    QueryResult masterQr = PersistenceHelper.manager.find(spec);
    String test = String.valueOf(CommonUtil.getOIDLongValue(pf));
    while(masterQr.hasMoreElements()){
      Object[] master = (Object[])masterQr.nextElement();
      KETWfmApprovalMaster appMaster = (KETWfmApprovalMaster)master[0];
      wfmApprovalMasterOid = CommonUtil.getOIDString(appMaster);
    }
  }
  //  ##################################### KETWfmApprovalMaster END


  if(cmd.equals("save")){
    //oid = StringUtil.checkNull(request.getParameter("oid"));
    //pOid = StringUtil.checkNull(request.getParameter("pOid"));

    String rankOne = StringUtil.checkNull(request.getParameter("rankOne"));
    String rankTwo = StringUtil.checkNull(request.getParameter("rankTwo"));
    String descMsg = StringUtil.checkNull(request.getParameter("descMsg"));

    String planQuality = StringUtil.checkNull(request.getParameter("planQuality"));
    String planCost = StringUtil.checkNull(request.getParameter("planCost"));
    String planDelivery1 = StringUtil.checkNull(request.getParameter("planDelivery1"));
    String planDelivery2 = StringUtil.checkNull(request.getParameter("planDelivery2"));
    String planDelivery3 = StringUtil.checkNull(request.getParameter("planDelivery3"));

    String planTechnical = StringUtil.checkNull(request.getParameter("planTechnical"));
    String keyNo = StringUtil.checkNull(request.getParameter("keyNo"));

    String isBom = StringUtil.checkNull(request.getParameter("isBom"));
    String isPackage = StringUtil.checkNull(request.getParameter("isPackage"));
    String isMass = StringUtil.checkNull(request.getParameter("isMass"));
    String isAppraisal = StringUtil.checkNull(request.getParameter("isAppraisal"));
    String isPrecedence = StringUtil.checkNull(request.getParameter("isPrecedence"));
    String isEtc = StringUtil.checkNull(request.getParameter("isEtc"));

    HashMap map = new HashMap();
    map.put("oid", oid);
    map.put("pOid", pOid);

    map.put("rankOne", rankOne);
    map.put("rankTwo", rankTwo);
    map.put("descMsg", descMsg);


    map.put("planQuality", planQuality);
    map.put("planCost", planCost);
    map.put("planDelivery1", planDelivery1);
    map.put("planDelivery2", planDelivery2);
    map.put("planDelivery3", planDelivery3);

    map.put("planTechnical", planTechnical);
    map.put("keyNo", keyNo);

    map.put("isBom", isBom);
    map.put("isPackage", isPackage);
    map.put("isMass", isMass);
    map.put("isAppraisal", isAppraisal);
    map.put("isPrecedence", isPrecedence);
    map.put("isEtc", isEtc);


    try{
      pf = (Performance)PerformanceHelper.manager.savePerformanceAction(map);
    }catch(Exception e){ Kogger.error(e); }
    if(pf != null){
%>
    <script>
      alert("<%=messageService.getString("e3ps.message.ket_message", "03263", title) %><%--{0} 되었습니다.--%>");
      opener.document.forms[0].submit();
      //self.close();
      location.href="/plm/jsp/project/performance/ViewPerformance.jsp?oid=<%=oid%>&pOid=<%=CommonUtil.getOIDString(pf)%>";
    </script>
<%
    }else{
%>
    <script>
      alert("<%=messageService.getString("e3ps.message.ket_message", "00007", title) %><%--{0} 실패 (평가 기준 정보가 없습니다)--%>");
      opener.document.forms[0].submit();
      self.close();
    </script>
<%

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
  java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();


%>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=title%></title>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>

<style type="text/css">
<!--
body {
  margin-left: 15px;
  margin-top: 12px;
  margin-right: 0px;
  margin-bottom: 0px;
}
-->
</style>
<script language="JavaScript">

  function onSave(){
    form = document.forms[0];
<%if(project.getPjtType() == 2 || project.getPjtType() == 4){ %>
    if(form.rankOne.value ==''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>");
      form.rankOne.focus();
      return;
    }
    if(form.rankTwo.value ==''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>");
      form.rankTwo.focus();
      return;
    }
    if(form.planQuality.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00435") %><%--Quality 목표을(를) 선택해 주십시오--%>");
      form.planQuality.focus();
      return;
    }
    if(form.planCost.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00131") %><%--Cost 목표을(를) 선택해 주십시오--%>");
      form.planCost.focus();
      return;
    }
    if(form.planDelivery1.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00140") %><%--Delivery{0} 목표를 선택해 주십시오--%>");
      form.planDelivery1.focus();
      return;
    }
    if(!checkInt(form.planQuality.value )){return;}
    <%if(project.getPjtType() == 2){ %>
      if(form.planTechnical.value == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00505") %><%--Technical 목표을(를) 선택해 주십시오--%>");
        form.planTechnical.focus();
        return;
      }
    if(!checkInt(form.planTechnical.value)){return;}
    <%}%>

<%}else{
  if(project.getPjtType() != 3){
%>
    if(form.rankOne.value ==''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>");
      form.rankOne.focus();
      return;
    }
    if(form.rankTwo.value ==''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>");
      form.rankTwo.focus();
      return;
    }
    if(form.planQuality.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00435") %><%--Quality 목표을(를) 선택해 주십시오--%>");
      form.planQuality.focus();
      return;
    }
    if(form.planCost.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00131") %><%--Cost 목표을(를) 선택해 주십시오--%>");
      form.planCost.focus();
      return;
    }
    if(form.planDelivery1.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00140") %><%--Delivery{0} 목표를 선택해 주십시오--%>");
      form.planDelivery1.focus();
      return;
    }
    if(!checkInt(form.planQuality.value )){return;}

    <%if(project.getPjtType() == 2){ %>
      if(form.planTechnical.value == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00505") %><%--Technical 목표을(를) 선택해 주십시오--%>");
        form.planTechnical.focus();
        return;
      }

    if(!checkInt(form.planTechnical.value)){return;}
    <%}%>
<%
  }else{
%>
    if(form.planCost.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00131") %><%--Cost 목표을(를) 선택해 주십시오--%>");
      form.planCost.focus();
      return;
    }
    if(form.planDelivery1.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00140", 1) %><%--Delivery{0} 목표를 선택해 주십시오--%>");
      form.planDelivery1.focus();
      return;
    }
    if(form.planDelivery2.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00140", 2) %><%--Delivery{0} 목표를 선택해 주십시오--%>");
      form.planDelivery2.focus();
      return;
    }
    if(form.planDelivery3.value == ''){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00140", 3) %><%--Delivery{0} 목표를 선택해 주십시오--%>");
      form.planDelivery3.focus();
      return;
    }
  <%}%>
<%}%>


    form.cmd.value = "save";
    form.action = "/plm/jsp/project/performance/CreatePerformance.jsp";
    form.method = "post";
    form.submit();
  }
  function checkInt(v){
    if(!isNotNumData(v) && parseInt(v)<=100){
      return true;
    }else{
      alert("<%=messageService.getString("e3ps.message.ket_message", "00008") %><%--{0} 입력값이 잘못되었습니다\n입력값은 0~100 사이어야 합니다--%>");
      return false;
    }

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

  function isValidDate(obj, maxLength) {
      var retVal = true;
        var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
     //document.forms[0].duration.value = "";

    if(obj.value == "") {
      return;
    }

    val=obj.value;
    re=/[^0-9]/gi;
    obj.value=val.replace(re,"");

      var inputDate = funcReplaceStrAll(obj.value,  '<%=messageService.getString("e3ps.message.ket_message", "00015") %><%--{0}년--%>', '');
      inputDate     = funcReplaceStrAll(inputDate,  '<%=messageService.getString("e3ps.message.ket_message", "00039") %><%--{0}월--%>', '');
      inputDate     = funcReplaceStrAll(inputDate,  '<%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>', '');

      var yyyy = inputDate.substring(0, 4);
      var mm   = (maxLength >= 6)?inputDate.substring(4, 6):"01";
      var dd   = (maxLength == 8)?inputDate.substring(6, 8):"01";

      if (isNaN(yyyy) || parseInt(yyyy) < 1000) return viewErrMsg(obj, msg);
      if (isNaN(mm) || parseFloat(mm) > 12 || parseFloat(mm) < 1) return viewErrMsg(obj, msg);
      if (isNaN(dd) || parseFloat(dd) < 1 || (parseFloat(dd) > getEndOfMonthDay(parseFloat(yyyy.substring(2,4)), parseFloat(mm))) ) return viewErrMsg(obj, msg);

    if(inputDate.length == 8) {
        inputDate = inputDate.substring(0, 8); //미봉책
    }else{
            alert('<%=messageService.getString("e3ps.message.ket_message", "02383") %><%--입력된 값이 8자리 숫자가 아닙니다 8자리 숫자를 입력해주세요--%>');
      return;
    }

    obj.value = yyyy+ "-" +mm+ "-" +dd;
      return true;
  }
  function viewErrMsg(obj,msg) {
    alert(obj.value + " " + msg);
  }
  //********************************************************************
  //문자열 일괄전환 함수
  function funcReplaceStrAll(org_str, find_str, replace_str) {
      var pos = org_str.indexOf(find_str);
      while(pos != -1) {
          pre_str  = org_str.substring(0, pos);
          post_str = org_str.substring(pos + find_str.length, org_str.length);
          org_str  = pre_str + replace_str + post_str;
          pos = org_str.indexOf(find_str);
      }
      return org_str;
  }

  //*******************************************************************
  //년월 입력시 마지막 일자
  function  getEndOfMonthDay( yy, mm ) {
      var max_days=0;
      if(mm == 1) {
          max_days = 31 ;
      } else if(mm == 2) {
          if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))  max_days = 29;
          else                                                         max_days = 28;
      }
      else if (mm == 3)   max_days = 31;
      else if (mm == 4)   max_days = 30;
      else if (mm == 5)   max_days = 31;
      else if (mm == 6)   max_days = 30;
      else if (mm == 7)   max_days = 31;
      else if (mm == 8)   max_days = 31;
      else if (mm == 9)   max_days = 30;
      else if (mm == 10)  max_days = 31;
      else if (mm == 11)  max_days = 30;
      else if (mm == 12)  max_days = 31;
      else                return '';

      return max_days;
  }

</script>
</head>

<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form>
<body>

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
                      <td class="font_01">성과 관리 <%=title%></td>
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
        <%if(pf != null && CommonUtil.isAdmin()){ %>
           &nbsp;<%=pf.getLifeCycleState().getDisplay(messageService.getLocale())%><font color="red">
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
        <%} %>
                    &nbsp;
                    </td>
                     <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:onSave();" class="btn_blue"><%=title%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td>&nbsp;</td>
            <%if(pf != null){ %>
                     <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onClick="history.back();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
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
        //목표 Value
        int totalPlanValue = 0;

        String dept = "지정안됨";
        String duty = "지정안됨";
        if(projectData.pjtPm != null){
        PeopleData peopleData = new PeopleData(projectData.pjtPm);
          dept = peopleData.departmentName;
          duty = peopleData.duty;
        }
        %>

      <%if( project.getPjtType() == 2 ||  project.getPjtType() == 4){
        ProductProject productProject = (ProductProject)project;
        int targetCost1 = 0;
        int targetCost2 = 0;
        int targetCost3 = 0;
        int budget1 = 0;
        int budget2 = 0;
        int budget3 = 0;
        int resultsCost1 = 0;
        int resultsCost2 = 0;
        int resultsCost3 = 0;
        int balanceCost1 = 0;
        int balanceCost2 = 0;
        int balanceCost3 = 0;

        QuerySpec specCost = new QuerySpec();
        int idx_Cost = specCost.addClassList(CostInfo.class, true);
        SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(project) );
        specCost.appendWhere(scCost, new int[] { idx_Cost });

        QueryResult rtCost = PersistenceHelper.manager.find(specCost);
        while(rtCost.hasMoreElements()){
           Object[] obj = (Object[])rtCost.nextElement();
          CostInfo costInfo = (CostInfo)obj[0];

          int budget = 0;       //예산
          int executionCost = 0;    //초기집행가
          int editCost = 0;      //추가집행가
          int totalExpense = 0;    //총집행가
          int balanceCost = 0;    //잔액

          if(costInfo.getOrderInvest() != null){
            Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
            Integer totalPriceObj = (Integer)datas.get(ProductPrice.TOTALPRICE);
            Integer initExpenseObj = (Integer)datas.get(ProductPrice.INITEXPENSE);
            Integer addExpenseObj = (Integer)datas.get(ProductPrice.ADDEXPENSE);
            Integer totalExpenseObj = (Integer)datas.get(ProductPrice.TOTALEXPENSE);

            if(totalPriceObj != null) budget = totalPriceObj.intValue();          //예산
            if(initExpenseObj != null) executionCost = initExpenseObj.intValue();      //초기집행가
            if(addExpenseObj != null) editCost = addExpenseObj.intValue();          //추가집행가
            if(totalExpenseObj != null) totalExpense = totalExpenseObj.intValue();      //총집행가
            balanceCost = budget - totalExpense;                      //잔액
          }else {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
              budget = Integer.parseInt(costInfo.getTargetCost());            //예산

            if(costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
              executionCost = Integer.parseInt(costInfo.getExecutionCost());        //초기집행가
            else if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
              executionCost = Integer.parseInt(costInfo.getTargetCost());          //초기집행가

            if(costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
              editCost = Integer.parseInt(costInfo.getEditCost());            //추가집행가

            totalExpense = executionCost + editCost;                    //총집행가
            balanceCost = 0;                                //잔액
          }

          if("금형".equals(costInfo.getCostType())) {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
              targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost());
            budget1 = budget1 + budget;
            resultsCost1 = resultsCost1 + executionCost + editCost;
            balanceCost1 = balanceCost1 + balanceCost;
          }else if("설비".equals(costInfo.getCostType())) {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
              targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost());
            budget2 = budget2 +budget;
            resultsCost2 = resultsCost2 + executionCost + editCost;
            balanceCost2 = balanceCost2 + balanceCost;
          }else {
            if(costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
              targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost());
            budget3 = budget3 +budget;
            resultsCost3 = resultsCost3 + executionCost + editCost;
            balanceCost3 = balanceCost3 + balanceCost;
          }
        }
        totalPlanValue = budget1+budget2+budget3;

      %>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
      <COL width="15%"><COL width="35%"><COL width="15%"><COL width="35%">
        <tr>
                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
          <td class="tdwhiteM" >&nbsp;<%=project.getPjtNo() %> </td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%> </td>
          <td class="tdwhiteM" >&nbsp;<%=project.getDevelopentType()==null?"":StringUtil.checkNull(project.getDevelopentType().getName())%> </td>
              </tr>
        <tr>
          <td class="tdblueM" >Part No </td>
          <td class="tdwhiteM" >&nbsp; <%=(projectData.partNo==null)?"":projectData.partNo%></td>
                <td class="tdblueM" >PM </td>
          <td class="tdwhiteM" title="<%=projectData.pjtPmName%>/<%=duty%>/<%=dept%>"><div style="width:70;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><%=projectData.pjtPmName%>/<%=duty%>/<%=dept%>&nbsp;</div></td>
              </tr>
        <tr>
                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
          <td class="tdwhiteM" >&nbsp;<%=project.getPjtName() %> </td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%> </td>
          <td class="tdwhiteM" >&nbsp;
          <%=DateUtil.getDateString(projectData.pjtPlanStartDate,"D")%>
          </td>
              </tr>
            </table>
            <%} else if(project.getPjtType() == 3){
        MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);
        MoldItemInfo moldInfo = moldProject.getMoldInfo();
        ProductProject productProject = moldInfo.getProject();
        CostInfo costInfo = moldInfo.getCostInfo();
        String orderNumber = null;
        if(costInfo != null){
                   orderNumber = costInfo.getOrderInvest();
                }
                if(orderNumber != null && orderNumber.length() > 0){
                     Hashtable hash = ProductPrice.getPrice(orderNumber);
                    Integer integer = (Integer) hash.get(ProductPrice.TOTALPRICE);
                    if(integer != null){
                      totalPlanValue = integer.intValue();
                    }

                }

      %>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
      <COL width="13%"><COL width="20%"><COL width="13%"><COL width="20%"><COL width="14%"><COL width="20%">
        <tr>
          <td class="tdblueM" >Project No </td>
          <td class="tdwhiteM" >&nbsp;<%=productProject.getPjtNo() %> </td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01055") %><%--금형담당--%> </td>
          <td class="tdwhiteM" title="<%=projectData.pjtPmName%>/<%=duty%>/<%=dept%>">&nbsp;<div style="width:70;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><%=projectData.pjtPmName%>/<%=duty%>/<%=dept%></div></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00631") %><%--개발담당--%> </td>
          <td class="tdwhiteM0" title="<%=projectData.pjtPmName%>/<%=duty%>/<%=dept%>">&nbsp;<div style="width:70;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><%=projectData.pjtPmName%>/<%=duty%>/<%=dept%></div></td>

              </tr>
        <tr>
          <td class="tdblueM" >Die No </td>
          <td class="tdwhiteM" >&nbsp; <%=(moldInfo.getDieNo()==null)?"":moldInfo.getDieNo()%></td>
          <td class="tdblueM" >Part No </td>
          <td class="tdwhiteM" >&nbsp; <%=(moldInfo.getPartNo()==null)?"":moldInfo.getPartNo()%></td>
                <td class="tdblueM" >Rank </td>
          <td class="tdwhiteM0" >&nbsp;<%=productProject.getRank()==null?"":StringUtil.checkNull(productProject.getRank().getName())%>  </td>
              </tr>
        <tr>
          <td class="tdblueM" >Part Name </td>
          <td class="tdwhiteM" colspan=3>&nbsp;<%=(moldInfo.getPartName()==null)?"":moldInfo.getPartName()%></td>
                 <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%> </td>
          <td class="tdwhiteM0" >&nbsp;
          <%
          E3PSTask task2 = MoldProjectHelper.getTask((E3PSProject)project, "제품도면출도");
          if(task2 != null){
          E3PSTaskData taskData2 = new E3PSTaskData(task2);
          if(taskData2 != null){
          if(taskData2.taskExecEndDate != null){
          %>
          <%=DateUtil.getDateString(taskData2.taskExecEndDate,"D")%>
          <%}
          }
          }%>

          </td>
              </tr>
              <%
              String productTypeStr = "";
              if(productProject != null){
                if(productProject.getDevelopentType()!= null){
                  if(productProject.getDevelopentType().getName() != null){
                    productTypeStr = productProject.getDevelopentType().getName();
                  }
                }
              }
              %>
              <tr>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%> </td>
          <td class="tdwhiteM" >&nbsp;<%=productTypeStr %></td>
                      <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%></td>
          <td class="tdwhiteM" ><%=productProject.getProductType() == null?"&nbsp;" : productProject.getProductType().getName()%></td>
                  <td class="tdblueM" >&nbsp; </td>
          <td class="tdwhiteM0" >&nbsp; </td>
              </tr>

            </table>
            <%} %>
      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>

<%if(project != null){ %>
      <%if(project.getPjtType() != 3){ %>
      <table border="0" cellspacing="0" cellpadding="0" width="300">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="300">
        <tr>
              <td width="120" class="tdblueL">Rank<span class="red">*</span></td>
              <td width="180" class="tdwhiteL">
              <select name="rankOne" class="fm_jmp" style="width:100" <%//if(title.equals("수정")){out.print("disabled");} %>>
                <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>  ]</option>
        <option value="Connector" <%=(pfRankOne.equals("Connector") )?"selected":"" %> >Connector</option>
        <option value="Module" <%=(pfRankOne.equals("Module") )?"selected":"" %> >Module</option>
        </select>
        <select name="rankTwo" class="fm_jmp" style="width:80"  <%//if(title.equals("수정")){out.print("disabled");} %> >
                <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>  ]</option>
        <%if(project.getPjtType() == 3 || project.getPjtType() == 2){ %>
        <option value="S" <%=(pfRankTwo.equals("S") )?"selected":"" %> >S</option>
        <%} %>
        <option value="A" <%=(pfRankTwo.equals("A") )?"selected":"" %> >A</option>
        <option value="B" <%=(pfRankTwo.equals("B") )?"selected":"" %> >B</option>
        <option value="C" <%=(pfRankTwo.equals("C") )?"selected":"" %> >C</option>
        <%if(project.getPjtType() == 3 || project.getPjtType() == 0 || project.getPjtType() == 4 ){ %>
        <option value="D" <%=(pfRankTwo.equals("D") )?"selected":"" %> >D</option>
        <%} %>
        </select>
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
                      <td class="tdblueM" colspan='4' ><%=messageService.getString("e3ps.message.ket_message", "02988") %><%--평가요소--%></td>
        </tr>
        <%
        int rowspanSetting = 6;
        if(project.getPjtType() == 4){
          rowspanSetting = 5;
        }


              String dv1 = "";
              String dv2 = "";
              String dv3 = "";
              if(pf != null){
                if(pf.getPlanDelivery1() != null){
                  dv1  = pf.getPlanDelivery1();
                }
                if(pf.getPlanDelivery2() != null){
                  dv2  = pf.getPlanDelivery2();
                }
                if(pf.getPlanDelivery3() != null){
                  dv3 = pf.getPlanDelivery3();
                }
              }


        %>
        <tr>
          <td class="tdblueM" rowspan='<%=rowspanSetting%>' ><%=messageService.getString("e3ps.message.ket_message", "00400", "<br>") %><%--Project{0} 목표달성도--%></td>
          <td class="tdwhiteM" rowspan='<%=rowspanSetting%>'> 100%</td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02988") %><%--평가요소--%></td>
              </tr>
        <%if(project.getPjtType() == 2 || project.getPjtType() == 4){ %>

        <%
        	String drNum = "6";
        	E3PSTask ett3 = MoldProjectHelper.getTask((E3PSProject)project, "DR6");
        	if(ett3 == null){
        		drNum = "3";
        	}
        %>

        <tr>
          <td class="tdblueM" >Quality</td>
                    <td class="tdwhiteL" title="<%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%> : 0 ~ 100" ><input type=text  <%=readOnlyStr%> class="txt_field" name="planQuality" style="ime-mode:disabled;width:80%;text-align:right" onkeyup ="SetNum(this)" value="<%=(pf==null)?"":pf.getPlanQuality() %>" <%=readOnlyModify%> >&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%> </td>
                <td class="tdwhiteM" ><%=(pOid.length() > 0 )?wgQuality:"-"%>% </td>
          <td class="tdwhiteL" ><%if( project.getPjtType() == 2 ){ %> <%=messageService.getString("e3ps.message.ket_message", "03047", 6) %><%--프로젝트 DR{0} 점수--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "03047", drNum) %><%--프로젝트 DR{0} 점수--%> <%}%></td>
              </tr>
              <%} %>
        <tr>
          <td class="tdblueM" >Cost</td>
          <td class="tdwhiteL" > <input type=text  <%=readOnlyStr%> class="txt_field" name="planCost" style="ime-mode:disabled;width:80%;text-align:right;border:0" onkeyup ="SetNum(this)" value="<%=totalPlanValue%>" ReadOnly>&nbsp; </td>
                <td class="tdwhiteM" ><%=(pOid.length() > 0 )?wgCost:"-"%>% </td>
                    <td class="tdwhiteL" ><%if( project.getPjtType() != 3 ){ %><%=messageService.getString("e3ps.message.ket_message", "03083") %><%--프로젝트 예산 대비 실적--%><%}else{ %>목표 금형제작(디버깅포함)<br>비용 대비 실적 비율 <%}%> </td>
              </tr>
        <tr>
          <td class="tdblueM" title="입력 형식 : yyyyMMdd" >Delivery</td>
          <td class="tdwhiteL" title="입력 형식 : yyyyMMdd" > <input type=text  <%=readOnlyStr%> class="txt_field" name="planDelivery1" onChange="isValidDate(this, 8);" style="width:80%;text-align:right" value="<%=(pf==null)?"":pf.getPlanDelivery1() %>" <%=readOnlyModify%> ></td>
                <td class="tdwhiteM" ><%=(pOid.length() > 0 )?wgD1:"-"%>% </td>
                    <td class="tdwhiteL" ><%if( project.getPjtType() != 3 ){ %><%=messageService.getString("e3ps.message.ket_message", "03089") %><%--프로젝트 전체 완료 일정--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "03369") %><%--금형제작[설계~Try(T0)]--%> <%}%>  </td>
              </tr>
              <%if( project.getPjtType() == 3 ){ %>
        <tr>
          <td class="tdblueM" title="입력 형식 : yyyyMMdd" >Delivery2</td>
          <td class="tdwhiteL" title="입력 형식 : yyyyMMdd" > <input type=text  <%=readOnlyStr%> class="txt_field" name="planDelivery2" onChange="isValidDate(this, 8);" style="width:80%;text-align:right" value="<%=(pf==null)?"":dv2 %>" <%=readOnlyModify%> ></td>
                <td class="tdwhiteM" ><%=(pOid.length() > 0 )?wgD1:"-"%>% </td>
                    <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "01341") %><%--디버깅[Try(T0)~제품합격]--%></td>
              </tr>
        <tr>
          <td class="tdblueM" title="입력 형식 : yyyyMMdd" >Delivery3</td>
          <td class="tdwhiteL" title="입력 형식 : yyyyMMdd" > <input type=text  <%=readOnlyStr%> class="txt_field" name="planDelivery3" onChange="isValidDate(this, 8);" style="width:80%;text-align:right" value="<%=(pf==null)?"":dv3 %>" <%=readOnlyModify%> ></td>
                <td class="tdwhiteM" ><%=(pOid.length() > 0 )?wgD1:"-"%>% </td>
          <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "01096") %><%--금형이관[제품합격~금형이관]--%></td>
              </tr>
              <%} %>
              <%if( project.getPjtType() == 2 ){%>
              <tr>
          <td class="tdblueM" >Technical</td>
                    <td class="tdwhiteL" title="<%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%> : 0 ~ 100"> <input type=text  <%=readOnlyStr%> class="txt_field" name="planTechnical" style="ime-mode:disabled;width:80%;text-align:right"  onkeyup ="SetNum(this)" value="<%=(pf==null)?"":pf.getPlanTechnical() %>" <%=readOnlyModify%> >&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%> </td>
                <td class="tdwhiteM" ><%=(pOid.length() > 0 )?wgTechnical:"-"%>% </td>
          <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "03048", "1, 2, 4") %><%--프로젝트 DR{0} 점수(평균)--%></td>
              </tr>
        <%} %>
        <tr>
          <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01853") %><%--설계변경건수--%> </td>
          <td class="tdwhiteM" >- </td>
                <td class="tdwhiteM" >- </td>
                    <td class="tdwhiteL" ><%=messageService.getString("e3ps.message.ket_message", "03081") %><%--프로젝트 설계변경건수--%></td>
              </tr>
        <%if( project.getPjtType() != 3 ){%>
        <tr>
                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "03131") %><%--필수수행--%><br><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
          <td class="tdwhiteM" colspan='5'>
                    <input type=checkbox name="isBom" <%=(isBomCheck.equals("true"))?"checked":"" %> > <%=messageService.getString("e3ps.message.ket_message", "02100") %><%--양산전 BOM 등록(ERP)--%>
                    <input type=checkbox name="isPackage" <%=(isPackageCheck.equals("true"))?"checked":"" %> > <%=messageService.getString("e3ps.message.ket_message", "02992") %><%--포장사양 확정--%>
                    <input type=checkbox name="isMass" <%=(isMassCheck.equals("true"))?"checked":"" %> > <%=messageService.getString("e3ps.message.ket_message", "02101") %><%--양산처 확정--%>
                    <input type=checkbox name="isAppraisal" <%=(isAppraisalCheck.equals("true"))?"checked":"" %> > <%=messageService.getString("e3ps.message.ket_message", "02098") %><%--양산원가 산정--%>
          <input type=checkbox name="isPrecedence" <%=(isPrecedenceCheck.equals("true"))?"checked":"" %> > <%=messageService.getString("e3ps.message.ket_message", "01829") %><%--선행양산--%>
          <input type=checkbox name="isEtc" onclick="displayDesc(this);" <%=(isEtcCheck.equals("true"))?"checked":"" %> > <%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>
          </td>
              </tr>
              <%}else{ %>
         <tr>
                    <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "03131") %><%--필수수행--%><br><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
                <td class="tdwhiteL" colspan='5'>
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
            <textarea name="descMsg" style="width:100%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" ><%=pfdescMsg%></textarea>
          </td>
              </tr>
            </table>
      </div>

      <table border="0" cellspacing="0" cellpadding="0" width="810">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
       </td>
     </tr>

<%} %>
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


