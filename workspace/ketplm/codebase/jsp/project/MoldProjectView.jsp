<%@page import="ext.ket.part.base.service.PartBaseHelper"%>
<%@page import="ext.ket.part.util.PartSpecEnum"%>
<%@page import="ext.ket.part.util.PartSpecGetter"%>
<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.ews.dao.PartnerDao"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="ext.ket.part.entity.KETPartClassification"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.AuthUtil"%>
<%@page import="java.util.List"%>
<%@page import="ext.ket.dashboard.entity.DashBoardDTO"%>
<%@page import="ext.ket.shared.util.SpringUtil"%>
<%@page import="ext.ket.dashboard.service.DashBoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.project.beans.ProgramManagerHelper"%>
<%@page import="e3ps.project.beans.ProjectTreeNode"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.Performance"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.beans.PerformanceHelper"%>
<%@page import="e3ps.project.material.MoldMaterial"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="java.util.Date"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="wt.part.WTPart"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Calendar"%>
<%@page import="e3ps.project.outputtype.ModelPlan"%>
<%@page import="java.lang.reflect.Method"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  String oid = request.getParameter("oid");
  String modifyFlag = request.getParameter("modifyFlag");
  String changePmOid = request.getParameter("temppmName");
  

  String productOid = "";
  
  String tab1Title = messageService.getString("e3ps.message.ket_message", "02536");
  String titleInfo = messageService.getString("e3ps.message.ket_message", "01027");
  
  String popup = "";
  if(request.getParameter("popup") != null && request.getParameter("popup").length() > 0){
    popup = request.getParameter("popup");
  }

  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
  boolean isCS = CommonUtil.isMember("공정감사");
  E3PSProjectData projectData = new E3PSProjectData(project);

  MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);
  MoldItemInfo moldInfo = moldProject.getMoldInfo();
  
  boolean isPurchase = false;
  
  if(moldInfo == null){
    moldInfo = MoldItemInfo.newMoldItemInfo();
  }
  ProductProject productProject = null;
  E3PSProjectData productData = null;
  
  String purchasePmName = "";
  String purchasePmDept = "";
  String purchasePmOid = "";
  String pjtPmName = "&nbsp;";
  String makingPlace = "";
  
  if(moldInfo != null){
    productProject = moldInfo.getProject();
    if(productProject != null){
      productData = new E3PSProjectData(productProject);
      productOid = CommonUtil.getOIDString(productProject);
      pjtPmName = productData.pjtPmName;
    }
    if("구매품".equals(moldInfo.getItemType())){
	   isPurchase = true;
	   tab1Title = "기본정보";
	   titleInfo = "구매품 프로젝트 정보";
	   
	   String purchaseStr = "";
       if ( moldInfo.getPurchasePlace() != null ) {
           purchaseStr = NumberCodeUtil.getNumberCodeValue("PRODUCTIONDEPT", moldInfo.getPurchasePlace().getCode(), messageService.getLocale().toString());
       }
       if ( "사내".equals(moldInfo.getProductionPlace()) ) {
           makingPlace = messageService.getString("e3ps.message.ket_message", "01655")/*사내*/ + " / " + purchaseStr;
       }else if ( moldInfo.getPartnerNo() != null && moldInfo.getPartnerNo().length() > 0 ) {
           PartnerDao pdao = new PartnerDao();
           String partnerName = pdao.ViewPartnerName(moldInfo.getPartnerNo());
           if ( partnerName == null || partnerName.length() == 0 ){
               partnerName = "&nbsp;";
           }
           makingPlace = messageService.getString("e3ps.message.ket_message", "02184")/*외주*/ + " / " + partnerName;
       }

       
	   if("modify".equals(modifyFlag)){
	       WTUser pmUser = (WTUser) CommonUtil.getObject(changePmOid);
	       ProjectUserHelper.manager.replacePM(moldProject, pmUser);
	       auth = new ProjectViewButtonAuth(project);
	   }
	   
	   
	   if(ProjectUserHelper.manager.getPM(moldProject) != null){
	        WTUser user = ProjectUserHelper.manager.getPM(moldProject);
	        purchasePmOid = CommonUtil.getOIDString(user);
	        PeopleData pData = new PeopleData(user);
	        purchasePmName = pData.name;
	        purchasePmDept = pData.departmentName;
	        pjtPmName = purchasePmName;
	   }
    }
  }
  
  E3PSProjectData moldData = new E3PSProjectData(moldProject);
  
  String projectStartDate = "";
  String projectEndDate = "";
  
  if(productData != null){
      if(productData.pjtExecStartDate != null){
	    projectStartDate = DateUtil.getDateString(productData.pjtExecStartDate, "D");
	  }else{
	    projectStartDate = DateUtil.getDateString(productData.pjtPlanStartDate, "D");
	  }

	  
	  if(productData.pjtExecEndDate != null){
	    projectEndDate = DateUtil.getDateString(productData.pjtExecEndDate, "D");
	  }else{
	    projectEndDate = DateUtil.getDateString(productData.pjtPlanEndDate, "D");
	  }

  }
  

  ExtendScheduleData data = (ExtendScheduleData)moldProject.getPjtSchedule().getObject();
  int result = 0;
  if(data.getExecStartDate() != null){
    Timestamp end = data.getExecEndDate();
    if(end == null){
      Calendar c = Calendar.getInstance();
      end = new Timestamp(c.getTimeInMillis());
    }
    result = DateUtil.getDuration(data.getExecStartDate(), end) + 1;
  }


  ProjectViewButtonAuth productAuth = null;
  
  if(productProject != null){
      productAuth = new ProjectViewButtonAuth(productProject);
  }
  

  String mType = "&nbsp;";
  String maker = "&nbsp;";
  String type = "&nbsp;";
  String grade = "&nbsp;";
  MoldMaterial material = null;

  if(moldInfo.getMaterial() != null){
    material = moldInfo.getMaterial();

    mType = material.getMaterial();
    maker = material.getMaterialMaker().getName();
    type = material.getMaterialType().getName();
    grade = material.getGrade();
  }

  String property = "&nbsp;";
  if(moldInfo.getProperty() != null){
    property = moldInfo.getProperty().getName();
  }

  String grade2 = "&nbsp;";

  if(mType.equals("비철")){
      WTPart part = PartBaseHelper.service.getLatestPart(moldInfo.getPartNo().toUpperCase());
      grade2 = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpMaterThick) ;
      //grade2 = moldInfo.getThickness() + "t" + "*" + moldInfo.getWidth() +"w";
      
  }

  String printMat = "Shrinkage";

  if("Press".equals(moldInfo.getItemType())){
    printMat = "Clearance";
  }

  String shrinkage = "&nbsp;";
  if(moldProject.getShrinkage() != null && moldProject.getShrinkage().length() > 0){
    shrinkage = moldProject.getShrinkage();
    if("Mold".equals(moldInfo.getItemType())){
      shrinkage += " %";
    }else{
      shrinkage += " mm";
    }

  }

  String etc = "&nbsp;";
  if(moldInfo.getEtc() != null && moldInfo.getEtc().length() > 0){
    etc = moldInfo.getEtc();
  }

  // 대상차종 정보
  String oemSOP = "&nbsp;";
  ModelPlan mp = null;
  int max = 0;
  int maxLife = 0;
  String opOid = "";

  QueryResult qr = null;
  
  if(productProject != null){
      OEMTypeHelper.getCustomerEvent(productProject.getOemType());
  }
  
  if(qr!=null){
    while(qr.hasMoreElements()){
      Object[] oo = (Object[])qr.nextElement();
      OEMPlan op = (OEMPlan)oo[0];
      opOid = op.getModelPlan().getPersistInfo().getObjectIdentifier().toString();
      OEMPlan oop = ProgramManagerHelper.manager.getCalendar(opOid, "SOP");
      if(oop != null) {
        oemSOP = DateUtil.getTimeFormat(oop.getOemEndDate(), "yyyy-MM-dd");
      }else {
        oemSOP = DateUtil.getTimeFormat(op.getOemEndDate(), "yyyy-MM-dd");
      }
      mp = op.getModelPlan();
    }
  }

  if(mp != null){
    for(int i = 1; i <= 10; i ++){
      Method method = mp.getClass().getMethod("getYield" + i, new Class[] {});
      String str = (String)method.invoke(mp, new Object[] {});

      if(str != null && str.length() > 0){
        max = max < Integer.parseInt(str)?Integer.parseInt(str):max;
        maxLife = i;
      }
    }
  }
  // 대상차종 정보

  String partOid = "";
  if(moldInfo.getPartNo() != null && moldInfo.getPartNo().length() > 0){
    WTPart part = MoldPartHelper.getWTPart(moldInfo.getPartNo().toUpperCase(), null);
    if(part != null){
      partOid = CommonUtil.getOIDString(part);
    }
  }

  //##################################### 성과 관리
  Performance pf = null;
  QueryResult qrtest = PerformanceHelper.manager.searchPerformance(moldProject.getPjtNo());
  Object[] pobj = null;
  if(qrtest.hasMoreElements()){
    pobj = (Object[])qrtest.nextElement();
    pf = (Performance)pobj[0];
  }
  //##################################### 성과 관리

  //차종/고객/주요 일정
  DashBoardService boardService = (DashBoardService)SpringUtil.getBean("dashBoardService");
  DashBoardDTO dashBoardDTO = new DashBoardDTO();
  
  if(productProject != null){
      dashBoardDTO.setPjtNo(productProject.getPjtNo());
    //dashBoardDTO.setPjtNo("A14B071"); //테스트
      request.setAttribute("carScheduleData", boardService.getCarSchedule(dashBoardDTO));
      request.setAttribute("mileStoneData", boardService.getMileStone(dashBoardDTO));
      request.setAttribute("programCustomScheduleData", boardService.getProgramCustomSchdule(dashBoardDTO));
  }
  
//예상작업시간/실제작업시간
  String planWorkTime = "";
  String execWorkTime = "";
  java.util.List<String> workTimeList = new java.util.ArrayList<String>();
  workTimeList = ext.ket.project.task.service.ProjectTaskCompHelper.service.getProjectWorkTimeSumList(CommonUtil.getOIDString(moldProject));
  if (workTimeList != null) {
      planWorkTime = workTimeList.get(0);
      execWorkTime = workTimeList.get(1);
  }
  

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%
//프로젝트 중지
String command = request.getParameter("command");
if ("stateChange".equals(command)) {
  String state = request.getParameter("state");
  ProjectHelper.changeProjectState(project, state);
//    if("PROGRESS".equals(state))
//      ProjectHelper.manager.projectSendMail(project, "restart");
%>
  <script language="JavaScript">
    <%if("popup".equals(popup)){%>
      parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=<%=popup%>";
    <%}else{%>
      parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>');
    <%}%>
  </script>
<%
}
%>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
}
.round {
    position:relative;
    float:left;
    top:4px;
    border: 2px solid #a1a1a1;
    padding: 4px 4px; 
    background: #0000ff;
    width: 1px;
    height: 1px;
    border-radius: 10px;
    z-index: 100;
} 

.line {
   width: 100%;
   margin-top:20px;
   margin-bottom:30px;
   border-bottom: 2px solid #008b8b;
   padding: 0px 0px; 
   background:#FFFFFF;
   height: 10px;
   z-index: 10;
}

.date {
    position:absolute;
    top:15px;
    left:-25px;
    font-size: 80%;
    width:60px;
    font-weight:bold;
}

.type {
    position:absolute;
    top:-15px;
    left:-26px;
    font-size: 80%;
    width: 60px;
    font-weight:bold;
}

-->
</style>

<!-- [START] [PLM System 1차개선] 일정변경 function 호출을 위한 Include, 2013-08-05, BoLee -->
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<!-- [END] [PLM System 1차개선] 일정변경 function 호출을 위한 Include, 2013-08-05, BoLee -->
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function goView(no){
  if(no == 1){
    showProcessing();
    location.href = "/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>";
  }else{
    //showProcessing();
    location.href = "/plm/jsp/project/MoldProjectView_" + no + ".jsp?oid=<%=oid%>&popup=<%=popup%>";
  }
}


//상태 변경 ...................................................................... begin
var ajaxType;
function onStateChange(s_obj, lcm_oid) {
  if(s_obj == "STOPED"){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03339") %><%--중지 하시겠습니까?--%>")) {
      return;
    }
  }else{
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03333") %><%--재시작 하시겠습니까?--%>")) {
      return;
    }
  }
/*
  if(!confirm("상태를 변경하시겠습니까?")) {
    return;
  }

  if(s_obj.value == '') {
    return;
  }

  var param = "?command=stateChange&oid=" + lcm_oid + "&state=" + s_obj.value;
*/
  var param = "?command=stateChange&oid=" + lcm_oid + "&state=" + s_obj;

  ajaxType = "stateChange";

  onProgressBar();

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
//상태 변경 ...................................................................... end


function viewProject(oid){
  openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
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

function getMyDiffDay(startDate, endDate) {
	var diffDay = 0;
    var start_yyyy = startDate.substring(0,4);
    var start_mm = startDate.substring(5,7);
    var start_dd = startDate.substring(8,startDate.length);
    var sDate = new Date(start_yyyy, start_mm-1, start_dd);
    var end_yyyy = endDate.substring(0,4);
    var end_mm = endDate.substring(5,7);
    var end_dd = endDate.substring(8,endDate.length);
    var eDate = new Date(end_yyyy, end_mm-1, end_dd);

    diffDay = Math.ceil((eDate.getTime() - sDate.getTime())/(1000*60*60*24));
            
    return diffDay;
}

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
    var department = document.getElementById("department");


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
        //department.value = infoArr[5];
    }
}


function delMember(rname){
    document.getElementById(rname).value = "";
    document.getElementById("temp" + rname).value = "";
}

function modifyPm(){
	
	if(document.frm.temppmName.value == ''){
		alert("PM을 지정하시기 바랍니다.");	
		return;
	}
	document.frm.modifyFlag.value = "modify";
    document.frm.method = "post";
    document.frm.submit();
	
}

//-->
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form id='frm' name='frm'>
        <input type="hidden" name="oid" value="<%=oid%>"></input> <input type="hidden" name="popup" value="<%=popup%>"></input>
        <input type="hidden" name="modifyFlag">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=titleInfo%><%--금형 프로젝트 상세정보--%></td>
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
                                        <td align="left"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=tab1Title%><%--제품--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table></td>
                                            
                                        <%
                                            if (!isPurchase) {
                                        %>    
                                        <td align="left"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="#"
                                                        onclick="javascript:goView('2');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
                                        <%
                                            if (!isCS && !isPurchase) {
                                        %>
                                        <td align="left"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="#"
                                                        onclick="javascript:goView('3');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02354")%><%--일정/비용--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
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
                                        <td valign="top">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td width="70" class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></td>
                                                    <td>[<%=messageService.getString("e3ps.message.ket_message", "00404")%><%--Project 일정--%>
                                                        : <%=projectStartDate%> ~ <%=projectEndDate%>]
                                                    </td>
                                                </tr>
                                                
                                                <tr>
                                                    <td colspan="5" height="10px"></td>
                                                </tr>
	                                            <tr>
	                                               <td colspan="5" align="right">
	                                                   
	                                                   <%
	                                                   if (isPurchase) {
                                                       %>
	                                                   <jsp:include page="/jsp/project/MoldProjectButton_include.jsp" flush="false">
	                                                       <jsp:param name="oid" value="<%=oid%>" />
	                                                       <jsp:param name="popup" value="<%=popup%>" />
	                                                   </jsp:include>
	                                                   <td width="5">&nbsp;</td>
	                                                   <%} %>
	                                                   
	                                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                   <td width="55" class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:openViewProjectSchedule('<%=oid%>');"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02355")%><%--일정Chart--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                       
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
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%;" style="table-layout: fixed;">
                                                <tr>
                                                    <td width="90px" height="130" class="tdblueL">Project No</td>
                                                    <td class="tdwhiteL">
                                                        <a href="#" onclick="javascript:openView('<%=productOid%>')"><%=productProject != null ? productProject.getPjtNo() : "&nbsp;" %></a></td>
                                                    <td width="90px" class="tdblueL">Project Name</td>
                                                    <td class="tdwhiteL">
                                                        <a href="#"onclick="javascript:openView('<%=productOid%>')"><%=productProject != null ? productProject.getPjtName() : "&nbsp;"%></a></td>
                                                    <td width="180px" class="tdblueM0">Project Image</td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL">Part No</td>
                                                    <td class="tdwhiteL"><%=moldInfo.getPartNo()==null?"":"<a href=javascript:openViewPart('"+moldInfo.getPartNo()+"')>"+moldInfo.getPartNo()+"</a>" %>
                                                    </td>
                                                    <td class="tdblueL">Part Name</td>
                                                    <td class="tdwhiteL"><%=moldInfo.getPartName() == null ? "&nbsp;" : moldInfo.getPartName()%></td>
                                                    <td rowspan="6" class="tdwhiteM0">
                                                        <%
                                                            if (partOid != null && partOid.length() > 0) {
                                                        %>
                                                        <div style="width:165px;height: 130px; overflow-x: hidden; overflow: auto; border: 1px; border-style: solid; border-color: #5F9EA0; padding: 0px; margin: 1px;">
                                                            <table border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td>
                                                                        <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
                                                                            <jsp:param name="oid" value="<%=partOid%>"/>
                                                                          </jsp:include>
                                                                       <%--  <%if(AuthUtil.isContentSecu(partOid)) {%>
                                                                          <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
                                                                            <jsp:param name="oid" value="<%=partOid%>"/>
                                                                          </jsp:include>
                                                                        <%}else{%>
                                                                          <jsp:include page="/jsp/edm/thumbImg.jsp" flush="false">
                                                                            <jsp:param name="oid" value="<%=partOid%>"/>
                                                                          </jsp:include>
                                                                        <%} %> --%>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div> 
                                                        <%
                                                             } else {
                                                         %> &nbsp; <%
                                                             }
                                                         %>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578")%><%--제품구분--%></td>
                                                    <%
                                                        String className = "";;
                                                        if(productProject != null && (StringUtil.checkString(productProject.getProductTypeLevel2()) && productProject.getProductTypeLevel2().indexOf("KETPartClassification") > 0)){
                                                            KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(productProject.getProductTypeLevel2());
                                                            if(partClaz!=null){
                                                               className = partClaz.getParent().getClassKrName() + "/" +partClaz.getClassKrName();
                                                            }
                                                        }
                                                    %>
                                                    <%if(isPurchase){ %>
                                                    <td class="tdwhiteL">
                                                        <%=className%>
                                                    </td>
                                                    <td class="tdblueL">상태</td>
                                                    <td class="tdwhiteL">
                                                        <%=project.getState().getState().getDisplay(SessionHelper.manager.getLocale()) %>
                                                    </td>
                                                    <%}else{ %>
                                                    <td class="tdwhiteL" colspan="3">
                                                        <%=className%>
                                                    </td>
                                                    <%} %>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL">
                                                        <%if (productProject != null && ("자동차 사업부".equals(productProject.getTeamType()) || "it".equals(productProject.getItDivision()) ) ) {%>
                                                        <%=messageService.getString("e3ps.message.ket_message", "01233")%><%--대상차종--%>
                                                        <%}else{ %>
                                                        <%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%>
                                                        <%} %>
                                                    </td>
                                                    <td class="tdwhiteL"><%=productData != null && productData.representModel == null || productData.representModel.length() == 0 ? "&nbsp;" : productData.representModel%></td>
                                                    <%if(isPurchase){ %>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00965")%><%--구매처--%></td>
                                                    <td class="tdwhiteL">
                                                    <%=makingPlace%>
                                                    </td>
                                                    <%}else{ %>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01920")%><%--소요량(Max)--%></td>
                                                    <td class="tdwhiteL">
                                                        <%
                                                            if (max == 0) {
                                                        %>&nbsp;<%
                                                            } else {
                                                        %><%=max%>000/EA <%
                                                            }
                                                        %>
                                                    </td>
                                                    <%} %>
                                                </tr>
                                                <%
                                                    String customer = "&nbsp;";
                                                    if (productData != null && productData.customereventVec != null && productData.customereventVec.size() > 0) {
                                                		customer = ((e3ps.common.code.NumberCode) productData.customereventVec.get(0)).getName();
                                                    }

                                                    String masterName = "&nbsp;";
                                                    if (productData != null && productData.subcontractorVec != null && productData.subcontractorVec.size() > 0) {
                                                		masterName = ((e3ps.common.code.NumberCode) productData.subcontractorVec.get(0)).getName();
                                                    }
                                                %>

                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%--최종사용처--%></td>
                                                    <td class="tdwhiteL"><%=customer%></td>
                                                    <%if(isPurchase){ %>
                                                    <td class="tdblueL">접점고객</td>
                                                    <%}else{ %>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00661")%><%--개발의뢰처--%></td>
                                                    <%} %>
                                                    <td class="tdwhiteL"><%=masterName%></td>
                                                </tr>
                                                <tr>
                                                    <%if (isPurchase){%>
                                                    <td class="tdblueL">구매담당부서</td>
                                                    <td class="tdwhiteL"><%=purchasePmDept%></td>
                                                    <td class="tdblueL">구매담당</td>
	                                                    <%if (auth.isPM){%>
	                                                    <input type="hidden" id="temppmName" name="temppmName" value="<%=purchasePmOid%>"></input>
	                                                    <td class="tdwhiteL">
	                                                    
	                                                    <table border="0" cellspacing="0" cellpadding="0">
	                                                    <tr>
	                                                    <td>
	                                                    <INPUT style="width: 45%" id="pmName" class="txt_field" name="pmName" value="<%=purchasePmName%>">&nbsp;
	                                                    <a href="javascript:addMember('pmName')"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;
	                                                    <a href="javascript:delMember('pmName')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                                                    </td>
	                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                    <td width="30" class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:modifyPm();" class="btn_blue" id="refresh">수정</a></td>
	                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                                    </tr>
	                                                    </table>
	                                                    
	                                                    </td>
	                                                    <%}else{ %>
	                                                    <td class="tdwhiteL"><%=pjtPmName%></td>
	                                                    <%} %>
                                                	<%}else{ %>
                                                	<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01201")%><%--부서--%></td>
                                                    <td class="tdwhiteL"><%=productData != null && productData.department == null || productData.department.length() == 0 ? "&nbsp;" : productData.department%></td>
                                                	<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02603")%><%--제품수명--%></td>
                                                    <td class="tdwhiteL"><%=maxLife == 0 ? "&nbsp;" : maxLife%><%=messageService.getString("e3ps.message.ket_message", "00015")%><%--{0}년--%></td>
                                                	<%} %>    
                                                    
                                                    
                                                </tr>
                                                <%if(!isPurchase){ %>                                                
                                                <tr>
                                                    <td class="tdblueL">SOP</td>
                                                    <td class="tdwhiteL"><%=oemSOP%></td>
                                                    <td class="tdblueL">PM</td>
                                                    <td class="tdwhiteL"><%=pjtPmName%></td>
                                                </tr>
                                                <%} %>
                                            </table>

                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            
                                            <%
                                            if (isPurchase) {
                                            %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space15"></td>
                                                </tr>
                                            </table>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03">개발구매정보</td>
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
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                                <colgroup>
                                                    <col width="120px">
                                                    <col>
                                                    <col width="120px">
                                                    <col>
                                                    <col width="130px">
                                                    <col>
                                                </colgroup>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817","")%><%--계획시작일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(moldData.pjtPlanStartDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02064")%><%--실제시작일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(moldData.pjtExecStartDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07121")%><%--예상작업시간(hr)--%></td>
                                                    <td class="tdwhiteL0"><%=planWorkTime %></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00820","")%><%--계획완료일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(moldData.pjtPlanEndDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02062")%><%--실제 완료일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(moldData.pjtExecEndDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07122")%><%--실제작업시간(hr)--%></td>
                                                    <td class="tdwhiteL0"><%=execWorkTime %></td>
                                                </tr>
                                            </table>
                                            <%
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
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02219")%><%--원재료 정보--%></td>
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
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                                    <td width="100" class="tdblueM">Maker</td>
                                                    <td width="100" class="tdblueM">Type</td>
                                                    <td width="130" colspan="2" class="tdblueM">Grade</td>
                                                    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02223")%><%--원재료특성--%></td>
                                                    <td width="130" class="tdblueM"><%=printMat%></td>
                                                    <td width="150" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632")%><%--비고--%></td>
                                                </tr>
                                                <tr>
                                                    <td width="70" class="tdwhiteM"><%=mType%></td>
                                                    <td width="100" class="tdwhiteM"><%=maker%></td>
                                                    <td width="100" class="tdwhiteM"><%=type%></td>
                                                    <td width="70" class="tdwhiteM"><%=grade%></td>
                                                    <td width="60" class="tdwhiteM"><%=grade2%></td>
                                                    <td width="100" class="tdwhiteM"><%=property%></td>
                                                    <td width="130" class="tdwhiteM"><%=shrinkage%></td>
                                                    <td width="150" class="tdwhiteM0 text-center"><%=etc%></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space15"></td>
                                                </tr>
                                            </table>
                                            <script type="text/javascript">
                                            $(document).ready(function(){
                                            	SuggestUtil.bind('USER', 'pmName','temppmName');
                                            });
                                            </script>
                                            <!-- ######################## 일정 타임라인 시작 ######################################## -->
										    <script>
										    
										    //기간 차이 계산
										    function getMyDiffDay(startDate, endDate) {
										        var diffDay = 0;
										        var start_yyyy = startDate.substring(0,4);
										        var start_mm = startDate.substring(5,7);
										        var start_dd = startDate.substring(8,startDate.length);
										        var sDate = new Date(start_yyyy, start_mm-1, start_dd);
										        var end_yyyy = endDate.substring(0,4);
										        var end_mm = endDate.substring(5,7);
										        var end_dd = endDate.substring(8,endDate.length);
										        var eDate = new Date(end_yyyy, end_mm-1, end_dd);
										    
										        diffDay = Math.ceil((eDate.getTime() - sDate.getTime())/(1000*60*60*24));
										                
										        return diffDay;
										    }
										    
										    function toggleTimeLine(obj){
										        
										        var label = $(obj).text().trim();
										        var type = $(obj).data('type');
										        
										        if("car" == type){
										            $("#carSchedule").toggle();
										            $("#carScheduleExpand").toggle();
										        }else if("program" == type){
										            $("#programScheduleExpand").toggle();
										            $("#programScheduleExpand").toggle();
										        }else if("mileStone" == type){
										            $("#mileStoneSchedule").toggle();
										            $("#mileStoneExpand").toggle();
										        }
										        
										        if(label == "펼치기") label = "접기";
										        else                  label = "펼치기";
										        
										        $(obj).text(label);
										    }
										    $(document).ready(function(){
										        
										        //최종사용처일정 없을시 삭제 처리
										        if($("#carSchedule .round").length == 0){
										            $("#carScheduleName").remove();
										            $("#carSchedule").remove();
										            $("#carScheduleExpand").remove();
										        }
										        //접점고객일정 없을시 삭제 처리
										        if($("#programSchedule .round").length == 0){
										            $("#programScheduleName").remove();
										            $("#programSchedule").remove();
										            $("#programScheduleExpand").remove();
										
										        }
										        
										        //KET마일스톤 없을시 삭제 처리
										        if($("#mileStoneSchedule .round").length == 0){
										            $("#mileStoneScheduleName").remove();
										            $("#mileStoneSchedule").remove();
										            $("#mileStoneExpand").remove();
										        }
										        
										        //현재날짜 화살표 표시 로직
										        var totalDate = new Array();
										        
										        $(".date").each(function(){
										            var planDate = $(this).text().replace(/-/gi,"");
										            totalDate.push(planDate);
										        });
										        
										        var minDate = String(Math.min.apply(this,totalDate));
										        var maxDate = String(Math.max.apply(this,totalDate));
										        
										        var minday = minDate.substring(0,4)+"-"+minDate.substring(4,6)+"-"+minDate.substring(6,8);
										        var maxday = maxDate.substring(0,4)+"-"+maxDate.substring(4,6)+"-"+maxDate.substring(6,8);
										        var diff = getMyDiffDay(minday,maxday);
										        
										        var pxlength = 560 / Number(diff);
										        
										        var newDate = new Date();
										        var yy = newDate.getFullYear();
										        var mm = newDate.getMonth()+1;
										        if(mm < 10) mm = "0"+mm;
										        
										        var dd = newDate.getDate();
										        if(dd < 10) dd = "0"+dd;
										        
										        var toDay = yy + "-" + mm + "-" + dd;
										        var vdiff = getMyDiffDay(minday, toDay);
										        var templen = vdiff * pxlength;
										        var day = Number(yy+""+mm+""+dd);
										        
										        if(day < Number(maxDate)){
										            var top = $(".line:first").offset().top;
										            $("#today").css("top", top-35);
										            $("#today").css("left", 40+templen);
										            $("#today").css("position", "absolute");
										        }else{
										            $("#today").remove("img");
										        }
										        
										        var carExpandCnt = 0;
										        var programExpandCnt = 0;
										        var mileStoneExpandCnt = 0;
										        var checkDupYear = "";
										        
										        $(".round").each(function(){
										            
										            var type = $(this).data("type");
										            var expanded = $(this).data("expanded");
										            var dateData = $(this).find(".date").text();
										            
										            var diffDay = getMyDiffDay(minday, dateData); 
										            var templength = diffDay * pxlength;
										            
										            //확장 타임라인 일정 갯수에 따라 위치 조정 START
										            if(expanded){
										                if(type == "car") {
										                    templength += 13 * carExpandCnt;
										                    carExpandCnt++;
										                }else if(type == "program"){
										                    templength += 13 * programExpandCnt;
										                    programExpandCnt++;
										                }else if(type == "mileStone"){
										                    templength += 13 * mileStoneExpandCnt;
										                    mileStoneExpandCnt++;
										                }
										            }
										            //확장 타임라인 일정 갯수에 따라 위치 조정 END
										            
										            $(this).css("left", 40 + templength);
										            
										            if(type != "mileStone"){
										                
										                var compareDay = Number(dateData.replace(/-/gi,""));
										                
										                if(compareDay  > day){
										                    $(this).css("background","#003B83");
										                }else{
										                    $(this).css("background","#A6A6A6");
										                }
										            }
										            
										            //년도 중복 제거 처리 START
										            var dataYY = Number(dateData.substring(0, 4));
										            var dataMMDD = dateData.substring(5, dateData.length);
										            
										            if(!expanded && (checkDupYear == dataYY)){
										                $(this).find(".date").text(dataMMDD);
										            }else{
										                checkDupYear = dataYY;
										            }
										            //년도 중복 제거 처리 END
										        });
										    });
										    </script>
										    <img alt="현재날짜" id="today" src="/plm/extcore/image/arrow_mark.png" />
										    <div class="sub-title-02 b-space15" id="carScheduleName">
										        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07245") %>
										        <span class="toggleBtn in-block v-middle r-space7">
										            <span class="pro-table">
										                <span class="pro-cell b-left"></span>
										                <span class="pro-cell b-center" >
										                    <a onclick="javascript:toggleTimeLine(this);" data-type="car" style="cursor:pointer;" class="btn_blue">
										                    펼치기
										                    </a>
										                </span>
										                <span class="pro-cell b-right"></span>
										            </span>
										        </span>
										    </div>
										    <div class="b-space10" id="carSchedule">
										        <div class="line">
										            <c:forEach var="data" items="${carScheduleData}" varStatus="idx">
										                <div data-type='car' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
										                    <div align='center'>
										                      <span class='type'>${data.type}</span>
										                    </div>
										                    <div align='center'>
										                      <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
										                    </div>
										                </div>
										            </c:forEach>
										        </div>
										    </div>
										    <div class="b-space10" id="carScheduleExpand" style="display:none;">
										        <c:forEach var="data" items="${carScheduleData}" varStatus="idx">
										        <div class="line">
										            <div data-type='car' data-expanded='true' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
										                    <div align='center'>
										                      <span class='type'>${data.type}</span>
										                    </div>
										                    <div align='center'>
										                      <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
										                    </div>
										                </div>
										        </div>
										        </c:forEach>
										    </div>
										    <div class="sub-title-02 b-space15" id="programScheduleName">
										        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07246") %>
										        <span class="toggleBtn in-block v-middle r-space7">
										            <span class="pro-table">
										                <span class="pro-cell b-left"></span>
										                <span class="pro-cell b-center" >
										                    <a onclick="javascript:toggleTimeLine(this);" data-type="program" style="cursor:pointer;" class="btn_blue">
										                    펼치기
										                    </a>
										                </span>
										                <span class="pro-cell b-right"></span>
										            </span>
										        </span>
										    </div>
										    <div class="b-space10" id="programSchedule">
										        <div class="line">
										            <c:forEach var="data" items="${programCustomScheduleData}" varStatus="idx">
										                <div data-type='program' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
										                    <div align='center'>
										                      <span class='type'>${data.type}</span>
										                    </div>
										                    <div align='center'>
										                      <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
										                    </div>
										                </div>
										            </c:forEach>
										        </div>
										    </div>
										    <div class="b-space10" id="programScheduleExpand" style="display:none;">
										        <c:forEach var="data" items="${programCustomScheduleData}" varStatus="idx">
										        <div class="line">
										            <div data-type='program' data-expanded='true' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
										                <div align='center'>
										                  <span class='type'>${data.type}</span>
										                </div>
										                <div align='center'>
										                  <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
										                </div>
										            </div>
										        </div>
										        </c:forEach>
										    </div>
										    <div class="sub-title-02 b-space15" id="mileStoneScheduleName">
										        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07262") %>
										        <span class="toggleBtn in-block v-middle r-space7">
										            <span class="pro-table">
										                <span class="pro-cell b-left"></span>
										                <span class="pro-cell b-center" >
										                    <a onclick="javascript:toggleTimeLine(this);" data-type="mileStone" style="cursor:pointer;" class="btn_blue">
										                    펼치기
										                    </a>
										                </span>
										                <span class="pro-cell b-right"></span>
										            </span>
										        </span>
										    </div>
										    <div class="b-space10" id="mileStoneSchedule">
										        <div class="line">
										        <c:forEach var="data" items="${mileStoneData}" varStatus="idx">
										            <c:if test="${data.state == 'G'}"><c:set var='dataColor' value="#00ff00"/></c:if>
										            <c:if test="${data.state == 'B'}"><c:set var='dataColor' value="#0000ff"/></c:if>
										            <c:if test="${data.state == 'Y'}"><c:set var='dataColor' value="#ffff00"/></c:if>
										            <c:if test="${data.state == 'R'}"><c:set var='dataColor' value="#ff0000"/></c:if>
										            <div data-type='mileStone' class='round' style="left:-${(idx.index) * 13}px;background:${dataColor};" title='${data.type}'>
										                <div align='center'>
										                  <span class='type'>${data.taskName}</span>
										                </div>
										                <div align='center'>
										                  <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.planEndDate}'/></span>
										                </div>
										            </div>
										        </c:forEach>
										        </div>
										    </div>
										    <div class="b-space10" id="mileStoneExpand" style="display:none;">
										        <c:forEach var="data" items="${mileStoneData}" varStatus="idx">
										        <div class="line">
										            <c:if test="${data.state == 'G'}"><c:set var='dataColor' value="#00ff00"/></c:if>
										            <c:if test="${data.state == 'B'}"><c:set var='dataColor' value="#0000ff"/></c:if>
										            <c:if test="${data.state == 'Y'}"><c:set var='dataColor' value="#ffff00"/></c:if>
										            <c:if test="${data.state == 'R'}"><c:set var='dataColor' value="#ff0000"/></c:if>
										            <div data-type='mileStone' data-expanded='true' class='round' style="left:-${(idx.index) * 13}px;background:${dataColor};" title='${data.type}'>
										                <div align='center'>
										                  <span class='type'>${data.taskName}</span>
										                </div>
										                <div align='center'>
										                  <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.planEndDate}'/></span>
										                </div>
										            </div>
										        </div>
										        </c:forEach>
										    </div>
										    <!-- ######################## 일정 타임라인 종료 ######################################## -->
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