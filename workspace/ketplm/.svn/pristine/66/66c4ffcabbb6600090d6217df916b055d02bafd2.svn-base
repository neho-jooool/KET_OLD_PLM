<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartManagerData"%>
<%@page import="e3ps.project.moldPart.beans.MoldSubPartData"%>
<%@page import="e3ps.project.moldPart.MoldPartManager"%>
<%@page import="e3ps.project.moldPart.MoldSubPart"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if("popup".equals(popup)) {
        tmpPopUp = "&popup=popup";
    }
    boolean isMeasuring = CommonUtil.isMember("자동차사업부_금형부품측정");

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
-->
</style>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<%@include file="/jsp/common/multicombo.jsp" %>
<script>

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

  var inputDate = funcReplaceStrAll(obj.value,  '년', '');
  inputDate     = funcReplaceStrAll(inputDate,  '월', '');
  inputDate     = funcReplaceStrAll(inputDate,  '일', '');

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
    alert(msg);
}


/* 부품 공정 */
function addMember(rname) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        //alert("error");
        return;
    }

    //document.getElementById('requester').value = "";
    acceptMember(rname, attacheMembers);
}

function acceptMember(rname, objArr) {
    if(objArr.length == 0) {
        return;
    }

    var keyName = document.getElementById("temp" + rname);
    var displayName = document.getElementById(rname);

    /*
        subArr[0] = chkobj[i].value;//wtuser oid
        subArr[1] = chkobj[i].poid;//people oid
        subArr[2] = chkobj[i].doid;//dept oid
        subArr[3] = chkobj[i].uid;//uid
        subArr[4] = chkobj[i].sname;//name
        subArr[5] = chkobj[i].dname;//dept name
        subArr[6] = chkobj[i].duty;//duty
        subArr[7] = chkobj[i].dutycode;//duty code
        subArr[8] = chkobj[i].email;//email
    */

    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
        infoArr = objArr[i];
        displayName.value = infoArr[4];
        keyName.value = infoArr[0];

    }
}
function goViewPage(projectOid, managerOid){
    location.href="/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid=" + projectOid + "&managerOid=" + managerOid + "<%=tmpPopUp %>";
}

function excelDown(oid){

    document.forms[0].method = "post";
    document.forms[0].managerOid.value =oid
    document.forms[0].action = "/plm/jsp/project/moldPart/ExcelDownLoad.jsp?isModify=true";
    document.forms[0].submit();
}

function onUpload(projectOid, managerOid){
    var url="/plm/jsp/project/moldPart/ExcelUpload.jsp?projectOid=" + projectOid + "&managerOid=" + managerOid;
    openOtherName(url, "popup", 500, 250, "status=yes,scrollbars=no,resizable=yes");

}

function onModify(){
    if(!checkData()){
        return;
    }
    document.forms[0].action="/plm/jsp/project/moldPart/MoldPartAction.jsp";
    document.forms[0].submit();
}

function delDate(rname){
    document.getElementById(rname).value = "";
}

function isMType(){
    mType = document.getElementsByName("mType");
    actionType = document.getElementsByName("actionType");
    transferDate = document.getElementsByName("transferDate");

    len = mType.length;
    for(i = 0; i < len; i++){
        if(mType[i].value == "합격"){
            transferDate[i].style.display = "";
            actionType[i].style.display = "none";
            actionType[i].value = "";
        }else if(mType[i].value == "불합격"){
            actionType[i].style.display = "";
            if(actionType[i].value == "특채"){
                transferDate[i].style.display = "";
            }else{
            	alert("!");
                transferDate[i].style.display = "none";
                transferDate[i].value = "";
                $("#transferDate").datepicker( "destroy" );
                $("[name=trashImg]").hide();
            }
        }else{
            transferDate[i].style.display = "none";
            transferDate[i].value = "";
            $("#transferDate").datepicker( "destroy" );
            $("[name=trashImg]").hide();
            actionType[i].style.display = "none";
            actionType[i].value = "";
        }
    }
}

function check(obj, maxLength){
    if(!isValidDate(obj, maxLength)){
        return;
    }

    var now = new Date();

    var today = now.getYear()+'-'+fncLPAD((now.getMonth()+1))+'-'+fncLPAD(now.getDate());

    if(today > obj.value) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02161") %><%--오늘 일자보다 이전날자를 입력하였습니다. 다시 입력해 주시기 바랍니다--%>');
        obj.value = "";
    }
}

function fncLPAD(num)
{
    if(num<10) return '0'+num;
    else return ''+num;
}

function inputEtc(obj){

    var text = obj.value;
    var url = "/plm/jsp/project/moldPart/PopupEtc.jsp";
    etcData = window.showModalDialog(url,text,"help=no; resizable=no; status=no; scroll=no; dialogWidth=500px; dialogHeight:220px; center:yes");
    if(typeof etcData == "undefined" || etcData == null) {
        //return;
    }else{
        obj.value = etcData;
    }
}

function checkData(){
    mType = document.getElementsByName("mType");
    endDate = document.getElementsByName("endDate");
    len = mType.length;
    for(i = 0; i < len; i++){
        if(mType[i].value != ""){
            if(endDate[i].value == ""){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01602") %><%--부품측정 완료일을 입력하십시오--%>');
                endDate[i].focus();
                return false;
            }
        }
    }
    return true;
}

function viewMoldProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
}

function isPartClass(){
    partClass = document.getElementsByName("partClass");
    partType = document.getElementsByName("partType");
    endDate = document.getElementsByName("endDate");
    mType = document.getElementsByName("mType");
    actionType = document.getElementsByName("actionType");
    transferDate = document.getElementsByName("transferDate");

    len = partClass.length;
    for(i = 0; i < len; i++){
        if(partClass[i].value == "도면정리"){
            partType[i].style.display = "none";
            endDate[i].style.display = "none";
            endDate[i].value = "";
            $("#endDate").datepicker( "destroy" );
            $("#[name=trashImg1]").hide();
            mType[i].style.display = "none";
            mType[i].value = "";
            transferDate[i].style.display = "none";
            transferDate[i].value = "";
            $("#transferDate").datepicker( "destroy" );
            $("#[name=trashImg]").hide();
            actionType[i].style.display = "none";
            actionType[i].value = "";
        }else{
            partType[i].style.display = "";
            //endDate[i].style.display = "";
            //endDate[i].value = "";
            mType[i].style.display = "";
            //mType[i].value = "";
        }
    }
}
$(document).ready(function(){
    //사용자 suggest
    SuggestUtil.bind('USER', 'partProcess', 'temppartProcess');
    
    CalendarUtil.dateInputFormat('exhibitDate');
    CalendarUtil.dateInputFormat('completeDate');
    CalendarUtil.dateInputFormat('endDate');
    CalendarUtil.dateInputFormat('transferDate');
    
    isMType();
    isPartClass();
});
</script>
</head>
<%
    String projectOid = request.getParameter("projectOid");
    //projectOid = "e3ps.project.MoldProject:162931";
    String managerOid = request.getParameter("managerOid");
    //managerOid = "e3ps.project.moldPart.MoldPartManager:208462";

    MoldPartManager manager = (MoldPartManager)CommonUtil.getObject(managerOid);

    MoldPartHelper.settingSubParts(manager);
    MoldPartManagerData data = new MoldPartManagerData(manager);

    Vector subParts = manager.getSubParts();
    //Kogger.debug("subParts.size() = " + subParts.size());

    String ecoNo = "&nbsp;";
    if(manager.getEcoNo() != null && manager.getEcoNo().length() > 0){
        ecoNo = manager.getEcoNo();
    }
    String user = CommonUtil.getOIDString(data.partWTUser);
    //Kogger.debug("user = " + user);
%>
<body class="popup-background popup-space">
<form method=post>

<input type="hidden" name="mode" value="modi"></input>
<input type="hidden" name="managerOid" value="<%=managerOid %>"></input>
<input type="hidden" name="projectOid" value="<%=projectOid %>"></input>
<input type="hidden" name="popup" value="<%=popup %>"></input>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01073") %><%--금형부품관리 수정--%></td>
                <%if(!("popup".equals(popup))){ %>
                
                <%} %>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><a href="javascript:;" onclick="javaScript:excelDown('<%=data.managerOid%>')"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                <td width="10">&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onUpload('<%=data.projectOid %>', '<%=data.managerOid %>');" class="btn_blue">Excel Upload</a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                <td width="10">&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onModify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                <td width="10">&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
       <table border="0" cellspacing="0" class="table-style-01" cellpadding="0" width="100%">
        <tr>
          <td width="130" class="title">Die No</td>
          <td width="260" class="tdwhiteL"><a href="#" onclick="javascript:viewMoldProject('<%=projectOid %>');"><%=data.dieNo %></a></td>
          <td width="130" class="title">ECO No</td>
          <td width="260" class="tdwhiteL0"><%=ecoNo %></td>
        </tr>
        <tr>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
          <td width="260" class="tdwhiteL"><%=data.levelType %></td>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "01583") %><%--부품공정--%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL0"><input type="text" name="partProcess" class="txt_field"  style="width:200" id="partProcess" value="<%=data.partUser%>">
            <input type="hidden" name="temppartProcess" value="<%=user %>">
&nbsp;<a href="#" onclick="javascript:addMember('partProcess');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "02880") %><%--출도일--%></td>
          <td width="260" class="tdwhiteL"><input type="text" name="exhibitDate" class="txt_field"  style="width:200" id="exhibitDate" onChange="isValidDate(this, 8);" value="<%=data.moldDate%>">
&nbsp;<a href="#" onclick="javascript:delDate('exhibitDate');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "01594") %><%--부품완료 예정일--%></td>
          <td width="260" class="tdwhiteL0"><input type="text" name="completeDate" class="txt_field"  style="width:200" id="completeDate" onChange="isValidDate(this, 8);" value="<%=data.planDate%>">
&nbsp;<a href="#" onclick="javascript:delDate('completeDate');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
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
          <td width="30" rowspan="2" class="tdblueM">No</td>
          <td width="110" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%><br>
            <%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
          <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01076") %><%--금형부품설명--%></td>
          <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
          <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01600") %><%--부품측정--%><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%><br><%=messageService.getString("e3ps.message.ket_message", "02650") %><%--조치사항--%></td>
          <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02326") %><%--인계일--%></td>
          <td width="40" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
        </tr>
        <tr>
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>
        </tr>
        <%
        //int total = subParts.size();
        for(int j = 0; j < subParts.size(); j++){
            MoldSubPart subPart = (MoldSubPart)subParts.get(j);
            MoldSubPartData subData = new MoldSubPartData(subPart);
            String material = subData.material;
            if(material == null || material.length() == 0){
                material = "";
            }

            String partClass = subData.partClass;
            if(partClass == null || partClass.length() == 0){
                partClass = "";
            }

            String partType = subData.partType;
            if(partType == null || partType.length() == 0){
                partType = "";
            }

            String endDate = subData.endDate;
            if(endDate == null || endDate.length() == 0){
                endDate = "";
            }

            String mType = subData.mType;
            if(mType == null || mType.length() == 0){
                mType = "";
            }

            String actionType = subData.actionType;
            if(actionType == null || actionType.length() == 0){
                actionType = "";
            }

            String etc = subData.etc;
            if(etc == null || etc.length() == 0){
                etc = "";
            }

            String transferDate = subData.transferDate;
            String sea = "";
               if(subData.quantity != null){
                   double dea = Double.parseDouble(subData.quantity);
                   int iea = (int)dea;
                   sea = String.valueOf(iea);
               }
            if(transferDate != null && transferDate.length() > 0){



        %>
        <tr>
          <td width="30" class="tdwhiteM"><%=j + 1%><input type="hidden" name="subPartOid" value="<%=subData.oid %>"></input></td>
          <td width="110" class="tdwhiteL" title="<%=subData.partNumber%>"><div style="width:105;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partNumber%></nobr></div></td>
          <td width="130" class="tdwhiteL" title="<%=subData.partName%>"><div style="width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partName%></nobr></div></td>
          <td width="45" class="tdwhiteM"><%=sea%></td>
          <td width="45" class="tdwhiteM"><%=material%><br></td>
          <td width="60" class="tdwhiteM"><%=partClass %><input type="hidden" name="partClass" value="<%=partClass %>"></input></td>
          <td width="60" class="tdwhiteM"><%=partType %><input type="hidden" name="partType" value="<%=partType %>"></input></td>
          <td width="130" class="tdwhiteM"><%=endDate%><%-- <br><input type="hidden" name="endDate" value="<%=endDate %>"></input> --%></td>
          <td width="60" class="tdwhiteM"><%=mType %><input type="hidden" name="mType" value="<%=mType %>"></input></td>
          <td width="60" class="tdwhiteM"><%=actionType %><br><input type="hidden" name="actionType" value="<%=actionType %>"></input></td>
          <td width="130" class="tdwhiteM"><%=transferDate%><%-- <input type="hidden" name="transferDate" value="<%=transferDate %>"></input> --%></td>
          <td width="40" class="tdwhiteM0"><textarea name="etc" style="overflow:hidden;width:95%" rows="1" class="fm_area" readOnly onclick="javascript:inputEtc(this);"><%=etc %></textarea><!-- <input type="text" name="etc" class="txt_field"  style="width:95%;height:90%" onclick="javascript:inputEtc(this);" readOnly id="etc" value="<%=etc%>"> --></td>
        </tr>
        <%
            }else if("가공".equals(actionType) || "수정".equals(actionType) || "도면정리".equals(partClass)){
                transferDate = "";
        %>
        <tr>
          <td width="30" class="tdwhiteM"><%=j + 1%><input type="hidden" name="subPartOid" value="<%=subData.oid %>"></input></td>
          <td width="110" class="tdwhiteL" title="<%=subData.partNumber%>"><div style="width:105;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partNumber%></nobr></div></td>
          <td width="130" class="tdwhiteL" title="<%=subData.partName%>"><div style="width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partName%></nobr></div></td>
          <td width="45" class="tdwhiteM"><%=sea%></td>
          <td width="45" class="tdwhiteM"><%=material%><br></td>
          <td width="60" class="tdwhiteM"><%=partClass %><input type="hidden" name="partClass" value="<%=partClass %>"></input></td>
          <td width="60" class="tdwhiteM"><%=partType %><br><input type="hidden" name="partType" value="<%=partType %>"></input></td>
          <td width="130" class="tdwhiteM"><%=endDate%><%-- <br><input type="hidden" name="endDate" value="<%=endDate %>"></input> --%></td>
          <td width="60" class="tdwhiteM"><%=mType %><br><input type="hidden" name="mType" value="<%=mType %>"></input></td>
          <td width="60" class="tdwhiteM"><%=actionType %><br><input type="hidden" name="actionType" value="<%=actionType %>"></input></td>
          <td width="130" class="tdwhiteM"><%=transferDate %><%-- <input type="hidden" name="transferDate" value="<%=transferDate %>"></input> --%></td>
          <td width="40" class="tdwhiteM0"><textarea name="etc" style="overflow:hidden;width:95%" rows="1" class="fm_area" readOnly onclick="javascript:inputEtc(this);"><%=etc %></textarea><!-- <input type="text" name="etc" class="txt_field"  style="width:95%;height:90%" onclick="javascript:inputEtc(this);" readOnly id="etc" value="<%=etc%>"> --></td>
        </tr>
        <%
            }else{
                transferDate = "";
                //isMeasuring
        %>
        <tr>
          <td width="30" class="tdwhiteM"><%=j + 1%><input type="hidden" name="subPartOid" value="<%=subData.oid %>"></input></td>
          <td width="110" class="tdwhiteL" title="<%=subData.partNumber%>"><div style="width:105;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partNumber%></nobr></div></td>
          <td width="130" class="tdwhiteL" title="<%=subData.partName%>"><div style="width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partName%></nobr></div></td>
          <td width="45" class="tdwhiteM"><%=subData.quantity%></td>
          <td width="45" class="tdwhiteM"><%=material%><br></td>
           <%if(!isMeasuring){ %>
          <td width="60" class="tdwhiteM">
            <select name="partClass" class="fm_jmp" style="width:100%" onChange="javascript:isPartClass();">
              <option value="가공" <%if(partClass.equals("가공")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>
              <option value="수정" <%if(partClass.equals("수정")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>
              <option value="도면정리" <%if(partClass.equals("도면정리")){ %>selected<%} %>>도면정리</option>
              <option value="가공+수정" <%if(partClass.equals("가공+수정")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option>
            </select></td>
          <td width="60" class="tdwhiteM">
            <select name="partType" class="fm_jmp" style="width:100%">
              <option value="사내" <%if(partType.equals("사내")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>
              <option value="사외" <%if(partType.equals("사외")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "01668") %><%--사외--%></option>
              <option value="사내+사외" <%if(partType.equals("사내+사외")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "01656") %><%--사내+사외--%></option>
              <option value="중국" <%if(partType.equals("중국")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "02687") %><%--중국--%></option>
            </select><br></td>
          <%}else { %>
          <td width="60" class="tdwhiteM"><%=partClass %><input type="hidden" name="partClass" value="<%=partClass %>"></input></td>
          <td width="60" class="tdwhiteM"><%=partType %><input type="hidden" name="partType" value="<%=partType %>"></input></td>

          <%} %>
          <td width="130" class="tdwhiteM"><input type="text" style="width:80px;" name="endDate" class="txt_field" onChange="isValidDate(this, 8);" value="<%=endDate%>"><img src="/plm/portal/images/icon_delete.gif" name="trashImg1" onclick="javascript:CommonUtil.deleteDateValue('endDate');" border="0"></td>
          <td width="60" class="tdwhiteM">
            <select name="mType" class="fm_jmp" style="width:100%" onChange="javascript:isMType();">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
              <option value="합격" <%if(mType.equals("합격")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%></option>
              <option value="불합격" <%if(mType.equals("불합격")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%></option>
            </select><br></td>

          <td width="60" class="tdwhiteM">
            <select name="actionType" class="fm_jmp" style="width:100%;display:none" id="actionType" onChange="javascript:isMType()">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
              <option value="특채" <%if(actionType.equals("특채")){ %>selected<%} %>>특채</option>
              <option value="가공" <%if(actionType.equals("가공")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>
              <option value="수정" <%if(actionType.equals("수정")){ %>selected<%} %>><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>
            </select><br></td>
          <td width="130" class="tdwhiteM"><input type="text" style="width:80px;" name="transferDate" id="transferDate" class="txt_field" onChange="isValidDate(this, 8);" value="<%=transferDate%>" /><img src="/plm/portal/images/icon_delete.gif" name="trashImg" onclick="javascript:CommonUtil.deleteDateValue('transferDate');" border="0"></td>
          <td width="40" class="tdwhiteM0"><textarea name="etc" style="overflow:hidden;width:95%" rows="1" class="fm_area" readOnly onclick="javascript:inputEtc(this);"><%=etc %></textarea><!-- <input type="text" name="etc" class="txt_field"  style="width:95%;height:90%" onclick="javascript:inputEtc(this);" readOnly id="etc" value="<%=etc%>"> --></td>
        </tr>
        <%
            }
        }
        %>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright<%if("popup".equals(popup)){ %>_t<%} %>.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
<script type="text/javascript">
    
</script>
</form>
</body>
</html>
