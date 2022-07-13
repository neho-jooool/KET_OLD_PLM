<%@page import="e3ps.project.cancel.ProjectCancelLink"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.project.cancel.CancelProject"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="ext.ket.part.util.PartSpecEnum"%>
<%@page import="ext.ket.part.util.PartSpecGetter"%>
<%@page import="wt.part.WTPart"%>
<%@page import="ext.ket.part.base.service.PartBaseHelper"%>
<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
public String getPjtStateColor(int state){
        String color = "black";
        switch(state){
            case ProjectStateFlag.PROJECT_STATE_COMPLETE : {color = "green"; break;}
            case ProjectStateFlag.PROJECT_STATE_DELAY : {color = "red"; break;}
            case ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS : {color = "orange"; break;}
            case ProjectStateFlag.PROJECT_STATE_PROGRESS : {color = "blue"; break;}
        }

        return color;

    }
%>
<%
    String oid = request.getParameter("oid");

    String popup = "";
    if(request.getParameter("popup") != null && request.getParameter("popup").length() > 0){
        popup = request.getParameter("popup");
    }

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
    E3PSProjectData data = new E3PSProjectData(project);
    MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);
    MoldItemInfo moldInfo = moldProject.getMoldInfo();
    boolean isCS = CommonUtil.isMember("공정감사");
    if(moldInfo == null){
//    Kogger.debug("@@@@@@@@@@@@@");
        moldInfo = MoldItemInfo.newMoldItemInfo();
    }
    ProductProject productProject = null;
    E3PSProjectData productData = null;
    if(moldInfo != null){
//    Kogger.debug("$$$$$$$@@@@@@$$$$$");
        productProject = moldInfo.getProject();
        if(productProject != null){
            productData = new E3PSProjectData(productProject);
        }
    }
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    String moldProjectStartDate = "";
    if(data.pjtExecStartDate != null){
        moldProjectStartDate = DateUtil.getDateString(data.pjtExecStartDate, "D");
    }else{
        moldProjectStartDate = DateUtil.getDateString(data.pjtPlanStartDate, "D");
    }

    String moldProjectEndDate = "";
    if(data.pjtExecEndDate != null){
        moldProjectEndDate = DateUtil.getDateString(data.pjtExecEndDate, "D");
    }else{
        moldProjectEndDate = DateUtil.getDateString(data.pjtPlanEndDate, "D");
    }

    String itemType = moldInfo.getItemType();

    boolean isMold = false;

    if(itemType.equals("Mold")){
        isMold = true;
    }

    String moldType = "&nbsp;";
    if(moldInfo.getMoldType() != null){
        moldType = moldInfo.getMoldType().getName();
    }

    String productType = "&nbsp;";
    if(moldInfo.getProductType() != null){
        productType = moldInfo.getProductType().getName();
    }

    String place = "&nbsp;";
    if(moldInfo.getProductionPlace() != null){
        place = moldInfo.getProductionPlace();
    }

    String making = "&nbsp;";
    if(moldInfo.getMaking() != null){
        making = moldInfo.getMaking();
    }

    String rank = "&nbsp;";
    if(moldProject.getRank() != null){
        rank = moldProject.getRank().getName();
    }

    String productWeight = "&nbsp;";
    double intProduct = 0;
/*     if(moldProject.getProductWeight() != null){
        productWeight = moldProject.getProductWeight() + " g";
        try{
            intProduct = Double.parseDouble(moldProject.getProductWeight());
        }catch(Exception e){
            Kogger.debug("intProduct = " + moldProject.getProductWeight());
        }

    } */
    String scrapWeight = "&nbsp;";
    double intScrap = 0;
/*     if(moldProject.getScrapWeight() != null){
        scrapWeight = moldProject.getScrapWeight() + " g";
        try{
            intScrap = Double.parseDouble(moldProject.getScrapWeight());
        }catch(Exception e){
            Kogger.debug("intScrap = " + moldProject.getScrapWeight());
        }
    } */
    
    WTPart partMaster = PartBaseHelper.service.getLatestPart(moldInfo.getPartNo());
    productWeight = PartSpecGetter.getPartSpec(partMaster, PartSpecEnum.SpNetWeight);
    if(StringUtils.isNotEmpty(productWeight)){
        try{
            intProduct = Double.parseDouble(productWeight);
            productWeight = productWeight + " g";
        }catch(Exception e){
            Kogger.debug("intProduct = " + productWeight);
        }
    }
    scrapWeight = PartSpecGetter.getPartSpec(partMaster, PartSpecEnum.SpScrabWeight);
    if(StringUtils.isNotEmpty(scrapWeight)){
       
        try{
            intScrap = Double.parseDouble(scrapWeight);
            scrapWeight = scrapWeight + " g";
        }catch(Exception e){
            Kogger.debug("intScrap = " + moldProject.getScrapWeight());
        }
    }

    String cavity = "&nbsp;";
    double intCavity = 0;
    
    PartSpecGetter.getPartSpec(partMaster, PartSpecEnum.SpNetWeight);
    
    if(moldInfo.getCVPitch() != null){
        cavity = moldInfo.getCVPitch();
        StringTokenizer st = new StringTokenizer(cavity, "*");
        intCavity = 1;
        while(st.hasMoreElements()){
            String value = st.nextToken();
            int value_i = 0;
            try{
                value_i = Integer.parseInt(value);
            }catch(Exception e){
                Kogger.debug("value = " + value);
            }
            intCavity *= value_i;
        }
    }

    String ctSpm = "&nbsp;";
    if(moldInfo.getCTSPM() != null){
        ctSpm = moldInfo.getCTSPM();
    }

    double totalWeight = (intProduct + intScrap) * intCavity;
    DecimalFormat df= new DecimalFormat("##,##0.00");
    String totalWeightStr = df.format(totalWeight);

    String specialSpec = "&nbsp;";
    if(moldProject.getSpecialSpec() != null && moldProject.getSpecialSpec().length() > 0){
        specialSpec = moldProject.getSpecialSpec();
    }

    //금형담당자, 부서
    String pmName = "&nbsp;";
    String pmDept = "&nbsp;";

    WTUser user =  null;
    if(ProjectUserHelper.manager.getPM(project) != null){
        user = ProjectUserHelper.manager.getPM(project);
        PeopleData pData = new PeopleData(user);
        pmName = pData.name;
        pmDept = pData.departmentName;
    }

    //제작처
    String outSourcing = "&nbsp;";
    /* if(moldProject.getOutSourcing() != null && moldProject.getOutSourcing().length() > 0){
        outSourcing = moldProject.getOutSourcing();
    } */
/*     if("사내".equals(moldInfo.getMaking()) && moldInfo.getMakingPlace() != null){
	    outSourcing = moldInfo.getMakingPlace().getName();
    }else{
	   PartnerDao partnerDao = new PartnerDao();
	   outSourcing = partnerDao.ViewPartnerName(moldInfo.getMakingPlacePartnerNo());
    }
     */
    WTPart dieMaster = PartBaseHelper.service.getLatestPart(moldInfo.getDieNo());
    outSourcing = PartSpecGetter.getPartSpec(dieMaster, PartSpecEnum.SpDieWhere);
    NumberCode outSourcingCode = NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", outSourcing);
    if(outSourcingCode != null){
	   outSourcing = outSourcingCode.getName();
    }else{
	   outSourcing = "&nbsp;";
    }
    
    //기계설비 정보
    MoldMachine machine =  null;

    String machineType = "&nbsp;";
    String machineMaker = "&nbsp;";
    String machineTon = "&nbsp;";
    String model = "&nbsp;";
    String remark = "&nbsp;";

    if(moldProject.getMoldMachine() != null){
        machine = moldProject.getMoldMachine();
        machineType = machine.getMachineType().getName();
        machineMaker = machine.getMachineMaker().getName();
        machineTon = machine.getTon().getName();
        model = machine.getModel();
    }

    if(moldProject.getRemark() != null && moldProject.getRemark().length() > 0){
        remark = moldProject.getRemark();
    }

    // 생산처
    String inoutName1 = "&nbsp;";
    String inoutName2 = "&nbsp;";
    PartnerDao partnerDao2 = new PartnerDao();
    if(moldInfo.getPurchasePlace() == null && moldInfo.getPartnerNo() != null && moldInfo.getPartnerNo().length() > 0) {
           inoutName1 = "외주";
        inoutName2 = partnerDao2.ViewPartnerName(moldInfo.getPartnerNo());
    }else if(moldInfo.getPurchasePlace() != null) {
        inoutName1 = "사내";
        inoutName2 = StringUtil.checkNull(moldInfo.getPurchasePlace().getName());
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
    
    
    String command = StringUtil.checkNull(request.getParameter("command"));
    
    if("cancelStart".equals(command)){
%>
<script language="JavaScript">
alert("결재를 진행해야 프로젝트가 취소 됩니다.");
var addr = "/plm/jsp/wfm/RequestApproval.jsp?pboOid=<%=oid%>"+"&popup=popup&mode=cancelStart";
var topener = window.open(addr,"approval","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
topener.focus();
</script>
<%
    }

%>

<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.ProductProject"%>


<%@page import="e3ps.project.machine.MoldMachine"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.lifecycle.LifeCycleTemplate"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.lifecycle.LifeCycleHelper"%>
<%@page import="wt.lifecycle.PhaseTemplate"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.beans.MoldProjectHelper"%>
<%@page import="e3ps.ews.dao.PartnerDao"%>
<%@page import="e3ps.common.util.StringUtil"%>

<%@page import="java.util.StringTokenizer"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.ProjectStateFlag"%><%@page import="java.text.DecimalFormat"%>
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
<script src="/plm/portal/js/common.js" type="text/javascript"></script>

<!-- [START] [PLM System 1차개선] 일정변경 function 호출을 위한 Include, 2013-08-05, BoLee -->
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<!-- [END] [PLM System 1차개선] 일정변경 function 호출을 위한 Include, 2013-08-05, BoLee -->

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

function modify(){
    var url = "/plm/jsp/project/ModifyMoldProject.jsp?oid=<%=oid%>";
	openWindow2(url,'ProfileUpdatePopup',800,600);
}

//상태 변경 ...................................................................... begin
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

function viewMoldProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
}

//-->
</script>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
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
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="javascript:goView('1');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isCS) {
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="javascript:goView('3');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02354")%><%--일정/비용--%></a></td>
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
                                        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                   <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                   <td width="70" class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01098")%><%--금형정보--%></td>
                                                   <td>[<%=messageService.getString("e3ps.message.ket_message", "00404")%><%--Project 일정--%>
                                                       : <%=moldProjectStartDate%> ~ <%=moldProjectEndDate%>]
                                                   </td>
                                                   <td></td>
                                               </tr>
                                               <tr>
                                                    <td colspan="4" height="10px"></td>
                                               </tr>
                                               <tr>
	                                                <td colspan="4" align="right">
	                                                    <jsp:include page="/jsp/project/MoldProjectButton_include.jsp" flush="false">
	                                                        <jsp:param name="oid" value="<%=oid%>" />
	                                                        <jsp:param name="popup" value="<%=popup%>" />
	                                                    </jsp:include>
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
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;">
                                                <colgroup>
                                                    <col width="120px">
                                                    <col>
                                                    <col width="120px">
                                                    <col>
                                                    <col width="130px">
                                                    <col>
                                                </colgroup>
                                                <tr>
                                                    <td class="tdblueL">Die No</td>
                                                    <td class="tdwhiteL"><%=moldInfo.getDieNo() == null ? "&nbsp;" : moldInfo.getDieNo()%></td>
                                                    <td class="tdblueL">Part Name</td>
                                                    <td class="tdwhiteL0" colspan="3"><%=moldInfo.getPartName() == null ? "&nbsp;" : moldInfo.getPartName()%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01051")%><%--금형구분--%></td>
                                                    <td class="tdwhiteL"><%=itemType%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01078")%><%--금형분류--%></td>
                                                    <td class="tdwhiteL"><%=productType%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01094")%><%--금형유형--%></td>
                                                    <td class="tdwhiteL0"><%=moldType%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01010")%><%--금형 Rank--%></td>
                                                    <td class="tdwhiteL"><%=rank%></td>
                                                    <td class="tdblueL"><%
                                                            if (isMold) {
                                                        %>Cavity<%
                                                            } else {
                                                        %>Line*Pcs<%
                                                            }
                                                        %></td>
                                                    <td class="tdwhiteL"><%=cavity%></td>
                                                    <td class="tdblueL"><%
                                                            if (isMold) {
                                                        %>Target C/T<%
                                                            } else {
                                                        %>SPM<%
                                                            }
                                                        %></td>
                                                    </td>
                                                    <td class="tdwhiteL0"><%=ctSpm%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02604")%><%--제품예상중량--%></td>
                                                    <td class="tdwhiteL"><%=productWeight%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00464")%><%--Scrap예상중량--%></td>
                                                    <td class="tdwhiteL"><%=scrapWeight%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00526")%><%--Total 중량--%></td>
                                                    <td class="tdwhiteL0"><%=totalWeightStr + " g"%></td>
                                                </tr>
                                                <tr>
                                                    <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532")%><%--제작구분--%></td>
                                                    <td class="tdwhiteL"><%=making%></td>
                                                    <%} %>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01058")%><%--금형담당자--%></td>
                                                    <td class="tdwhiteL"><%=pmName%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01057")%><%--금형담당부서--%></td>
                                                    <td class="tdwhiteL0"><%=pmDept%></td>
                                                    <%if(CommonUtil.isMember("SQ인증감사")){ %>
                                                    <td class="tdwhiteL0" colspan="2"></td>
                                                    <%} %>
                                                </tr>
                                                <tr>
                                                    <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533")%><%--제작처--%></td>
                                                    <td class="tdwhiteL"><%=outSourcing%></td>
                                                    <%} %>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%></td>
                                                    <td class="tdwhiteL"><%=project.getLifeCycleState().getDisplay(messageService.getLocale())%><!-- <font color="red"><%=(project.isCheckOut() == true) ? messageService.getString("e3ps.message.ket_message", "00120")/*CheckOut됨*/: ""%></font> -->
                                                        <%-- <%
                                                            if (CommonUtil.isAdmin() || isbizAdmin) {
                                                        %> <select name="state0" class="fm_jmp"
                                                        style="width: 90"
                                                        onchange="javascript:onStateChange(this, '<%=project.getPersistInfo().getObjectIdentifier().getStringValue()%>');">
                                                            <%
                                                                String curState = project.getLifeCycleState().toString();
                                                            		try {
                                                            		    //LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("LC_ECA");
                                                            		    LifeCycleTemplate lct = (LifeCycleTemplate) project.getLifeCycleTemplate().getObject();
                                                            		    Vector states = LifeCycleHelper.service.getPhaseTemplates(lct);
                                                            		    PhaseTemplate pt = null;
                                                            		    wt.lifecycle.State lcState = null;
                                                            		    for (int i = 0; i < states.size(); i++) {
                                                            			pt = (PhaseTemplate) states.get(i);
                                                            			lcState = pt.getPhaseState();
                                                            %>
                                                            <OPTION VALUE="<%=lcState.toString()%>"
                                                                <%if (curState.equals(lcState.toString())) {%> selected <%}%>><%=lcState.getDisplay(messageService.getLocale())%></OPTION>
                                                            <%
                                                                }
                                                            		} catch (Exception e) {
                                                            		    e.printStackTrace();
                                                            		}
                                                            %>
                                                    </select> <%
                                                       }
                                                    %> --%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07234")%><%--개발구분--%></td>
                                                    <td class="tdwhiteL0"><%=productProject.getDevelopentType() == null ? "&nbsp;" : StringUtil.checkNull(productProject.getDevelopentType().getName())%></td>
                                                    <%if(CommonUtil.isMember("SQ인증감사")){ %>
                                                    <td class="tdwhiteL0" colspan="2"></td>
                                                    <%} %>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07249")%><%--금형 현황--%></td>
                                                    <td colspan="5" class="tdwhiteL0" style="height: 60">
                                                        <textarea name="remark" cols="110" rows="3" class="fm_area" style="width: 99%; height: 97%; border: 0" readOnly><%=specialSpec%></textarea></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653","")%><%--개발유형--%></td>
                                                    <td class="tdwhiteL0" colspan="5"><%=productProject.getDevelopPattern() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPPATTERN", productProject.getDevelopPattern().getCode(), messageService.getLocale().toString()))%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07165","")%><%--계획시작일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(data.pjtPlanStartDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02064")%><%--실제시작일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(data.pjtExecStartDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07121")%><%--예상작업시간(hr)--%></td>
                                                    <td class="tdwhiteL0"><%=planWorkTime %></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07166","")%><%--계획완료일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(data.pjtPlanEndDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02062")%><%--실제 완료일--%></td>
                                                    <td class="tdwhiteL"><%=productData!=null?DateUtil.getDateString(data.pjtExecEndDate, "D"):"&nbsp;"%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07122")%><%--실제작업시간(hr)--%></td>
                                                    <td class="tdwhiteL0"><%=execWorkTime %></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space10"></td>
                                                </tr>
                                            </table>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01875")%><%--설비 정보--%></td>
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
                                                    <td class="tdblueL">Type</td>
                                                    <td class="tdwhiteL"><%=machineType%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00523")%><%--Ton(형체력)--%></td>
                                                    <td class="tdwhiteL"><%=machineTon%></td>
                                                    <td class="tdblueL">Maker</td>
                                                    <td class="tdwhiteL0"><%=machineMaker%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL">Model</td>
                                                    <td class="tdwhiteL"><%=model%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01778")%><%--생산구분--%></td>
                                                    <td class="tdwhiteL"><%=inoutName1%></td>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791")%><%--생산처--%></td>
                                                    <td class="tdwhiteL0"><%=inoutName2%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01632")%><%--비고--%></td>
                                                    <td colspan="5" class="tdwhiteL0" style="height: 60">
                                                    <textarea name="remark" cols="110" rows="3" class="fm_area" style="width: 100%; height: 97%; border: 0" readOnly><%=remark%></textarea></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space10"></td>
                                                </tr>
                                            </table>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00908")%><%--관련 ITEM 현황--%></td>
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
                                                    <td class="space5"><table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                                            <tr>
                                                                <td width="45px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                                                <td width="50px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02578")%><%--제품구분--%></td>
                                                                <td width="50px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01606")%><%--분류--%></td>
                                                                <td width="80px" rowspan="2" class="tdblueM">Part No</td>
                                                                <td width="100px" rowspan="2" class="tdblueM">Part Name</td>
                                                                <td width="60px" rowspan="2" class="tdblueM">Die No</td>
                                                                <td width="230px" <%if(CommonUtil.isMember("SQ인증감사")){ %> colspan="3" <%}else{%> colspan="4" <%}%> class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01090")%><%--금형속성--%></td>
                                                                <td width="110px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01791")%><%--생산처--%>
                                                                    <br> (<%=messageService.getString("e3ps.message.ket_message", "00965")%><%--구매처--%>)</td>
                                                                <td width="60px" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="40px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                                                                <td width="40px" class="tdblueM">C/V</td>
                                                                <td width="40px" class="tdblueM">C/T<br>(SPM)</td>
                                                                <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                                                                <td width="110px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
                                                                <%} %>
                                                            </tr>
                                                            <%
                                                                ArrayList moldItemArr= new ArrayList();
                                    
                                                                QuerySpec specMoldItem = new QuerySpec();
                                                                int idx = specMoldItem.addClassList(MoldItemInfo.class, true);
                                                                SearchCondition sc = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(productProject) );
                                                                specMoldItem.appendWhere(sc, new int[] { idx });
                                    
                                                                SearchUtil.setOrderBy(specMoldItem, MoldItemInfo.class, 0, "thePersistInfo.createStamp", false);
                                    
                                                                QueryResult rt = PersistenceHelper.manager.find(specMoldItem);
                                                                while(rt.hasMoreElements()){
                                                                     Object[] obj = (Object[])rt.nextElement();
                                                                    MoldItemInfo miInfo = (MoldItemInfo)obj[0];
                                                                    moldItemArr.add(miInfo);
                                                                }
                                    
                                                                   int plusNum = 1;
                                                                   int count = 1;
                                                                for(int i=0; i<moldItemArr.size(); i++) {
                                                                    MoldItemInfo miInfo = (MoldItemInfo)moldItemArr.get(i);
                                                                    boolean isMoldProject = false;
                                                                    MoldProject mProject = MoldProjectHelper.getMoldProject(miInfo);
                                                                    String mProjectOid = "";
                                                                    String state = "&nbsp;";
                                                                    String color = "black";
                                                                    if(mProject != null){
                                                                        isMoldProject = true;
                                                                        mProjectOid = CommonUtil.getOIDString(mProject);
                                                                        if(oid.equals(mProjectOid)){
                                                                            continue;
                                                                        }
                                                                        E3PSProjectData moldData = new E3PSProjectData(mProject);
                                                                        state = moldData.stateKorea;
                                                                        color = getPjtStateColor(moldData.getPjtState());
                                                                    }
                                    
                                                            %>
                                                            <tr>
                                                                <td class="tdwhiteM"><%=miInfo.getItemType()!=null?miInfo.getItemType():"&nbsp;"%></td>
                                                                <td class="tdwhiteM"><%=miInfo.getProductType()!=null ? miInfo.getProductType().getName() : "&nbsp;"%></td>
                                                                <td class="tdwhiteM"><%=miInfo.getShrinkage() != null ? miInfo.getShrinkage() : "&nbsp;" %></td>
                                                                <td class="tdwhiteM"><%=miInfo.getPartNo() != null ? miInfo.getPartNo() : "&nbsp;"%></td>
                                                                <td class="tdwhiteM" title="<%=miInfo.getPartName() != null ? miInfo.getPartName() : "&nbsp;"%>">
                                                                    <div style="text-align:left;width: 95%; border: 0; padding: 0; margin: 1; text-overflow: ellipsis; overflow: hidden;">
                                                                        <nobr><%=miInfo.getPartName() != null ? miInfo.getPartName() : "&nbsp;"%></nobr>
                                                                    </div>
                                                                </td>
                                                                <td class="tdwhiteM">
                                                                    <%if(isMoldProject){%>
                                                                    <a href="javascript:;" onClick="javascript:viewMoldProject('<%=mProjectOid%>')" class="btn_blue">
                                                                    <%} %>
                                                                    <%=(miInfo.getDieNo()==null)?"&nbsp;":miInfo.getDieNo()%>
                                                                    <%if(isMoldProject){%></a><%} %>
                                                                </td>
                                                                <td class="tdwhiteM"><%=miInfo.getMoldType()!=null ? miInfo.getMoldType().getName() : "&nbsp;"%></td>
                                                                <td class="tdwhiteM"><%=miInfo.getCVPitch() != null ? miInfo.getCVPitch() : "&nbsp;"%></td>
                                                                <td class="tdwhiteM"><%=miInfo.getCTSPM() != null ? miInfo.getCTSPM() : "&nbsp;"%></td>
                                                                <%
                                                                    String purchaseStr = "";
                                                                    if ( miInfo.getMakingPlace() != null ) {
                                                                        purchaseStr = NumberCodeUtil.getNumberCodeValue("PRODUCTIONDEPT", miInfo.getMakingPlace().getCode(), messageService.getLocale().toString());
                                                                    }
                                                    
                                                                    if ( "사내".equals(miInfo.getMaking())) {
                                                                        inoutName2 = messageService.getString("e3ps.message.ket_message", "01655")/*사내*/ + "   /" + purchaseStr;
                                                                    }
                                                                    else if ( miInfo.getMakingPlacePartnerNo() != null && miInfo.getMakingPlacePartnerNo().length() > 0 ) {
                                                                        String partnerName = partnerDao2.ViewPartnerName(miInfo.getMakingPlacePartnerNo());
                                                                        if ( partnerName == null || partnerName.length() == 0 ){
                                                                            partnerName = "&nbsp;";
                                                                        }
                                                                        inoutName2 = messageService.getString("e3ps.message.ket_message", "02184")/*외주*/ + "   /" + partnerName;
                                                                    }
                                                                %>
                                                                <%if(!CommonUtil.isMember("SQ인증감사")){ %>
                                                                <td class="tdwhiteM" title="<%=inoutName2%>"><div
                                                                        style="width: 95%; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                                        <nobr><%=inoutName2%></nobr>
                                                                    </div></td>
                                                                <%} %>
                                                                <%
                                                                    inoutName2 = "&nbsp;";
                                                                    if(miInfo.getPartnerNo() != null && miInfo.getPartnerNo().length() > 0) {
                                                                        partnerDao2 = new PartnerDao();
                                                                        inoutName2 = messageService.getString("e3ps.message.ket_message", "02184")/*외주*/ + " /" + partnerDao2.ViewPartnerName(miInfo.getPartnerNo());
                                                                    }else if(miInfo.getPurchasePlace() != null) {
                                                                        inoutName2 = messageService.getString("e3ps.message.ket_message", "01655")/*사내*/ + " /" + StringUtil.checkNull(miInfo.getPurchasePlace().getName());
                                                                    }
                                                                    %>
                                                                <td class="tdwhiteM" title="<%=inoutName2%>"><div
                                                                        style="width: 95%; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                                        <nobr><%=inoutName2%></nobr>
                                                                    </div></td>
                                                                <%
                                                                      if("Press".equals(miInfo.getItemType()) || "Mold".equals(miInfo.getItemType()) ) {
                                                                          if(plusNum == 1) {
                                                                              while( moldItemArr.size() > i + plusNum && miInfo.getDieNo().equals( ((MoldItemInfo)moldItemArr.get(i + plusNum)).getDieNo() ) )
                                                                                  plusNum++;
                                                                  %>
                                                                <td rowspan="<%=plusNum%>" class="tdwhiteM0 txt-center">
                                                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                        <tr>
                                                                            <%if(isMoldProject){%>

                                                                            <td class="btn_blue"><a
                                                                                href="javascript:viewMoldProject('<%=mProjectOid%>')"
                                                                                class="btn_blue"><font color="<%=color %>"><%=state %></font></a>
                                                                            </td>

                                                                            <%}else{%>
                                                                            <td>&nbsp;</td>
                                                                            <%}%>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <%
                                                                          }else
                                                                              plusNum--;
                                                                      }else{
                                                                %>
                                                                <td class="tdwhiteM0">&nbsp;</td>
                                                                <%} %>
                                                            </tr>
                                                            <%
                                                                }
                                                            %>
                                                        </table></td>
                                                </tr>
                                            </table></td>
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
