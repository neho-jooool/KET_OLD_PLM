<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.doc.*,wt.fc.*"%>
<%@page import = "wt.org.*, wt.epm.*, wt.part.*"%>
<%@page import = "e3ps.access.*,e3ps.access.service.*"%>
<%@page import = "e3ps.common.util.*,e3ps.common.web.ParamUtil,e3ps.common.obj.ObjectData"%>
<%@page import = "e3ps.groupware.company.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>
<%@page import="e3ps.edm.beans.EDMHelper"%>
<%@page import="e3ps.edm.util.EDMProperties"%>
<%@page import="e3ps.edm.util.EDMEnumeratedTypeUtil"%>
<%@page import="e3ps.edm.util.EDMAttributes"%>
<%@page import="wt.util.WTException"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.iba.IBAUtil"%>
<%@page import = "ext.ket.part.util.PartSpecGetter,ext.ket.part.util.PartSpecEnum,e3ps.common.util.StringUtil"%>
<%@page import="wt.session.SessionHelper"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<html>
<%!
    private static EPMDocument getAssociatedModels(EPMDocument epm, WTPart part,String referenceType, int required) {
        if( (epm == null) || (part == null) ) {
            return null;
        }

        EPMDocument model = null;
        try {
            ArrayList arrymodels = EDMHelper.getAssociatedModels(epm, part,referenceType, required);
            for(int i = 0; i < arrymodels.size(); i++) {
                model = (EPMDocument)arrymodels.get(i);
            }
        }
        catch(WTException e) {
            Kogger.error(e);
        }
        return model;
    }
%>
<%

    ProjectOutput output = null;
    TemplateTask task = null;
    TemplateProject project = null;
    ProjectOutputData data = null;

    WTUser chargeUser = null;//산출물 담당자
    WTUser roleCharge = null;//Role 담당자

    WTDocument document = null;//산출물 문서
    WTDocument LastDocument = null; // Last 산출물 문서

    EPMDocument epmDoc = null;//산출물 도면
    EPMDocument LastEpmDoc = null;//Last 산출물 도면
    boolean isEdit = false;//View 권한 추가/삭제 가능 여부.

    String name = "";
    String description = "";
    String role = "";
    String location = "";
    String userName = "";
    String userOid = "";
    String roleMember = "";
    String roleMemberOid = "";

    String oid = request.getParameter("oid");
    String taskOid = request.getParameter("taskOid");
    String projectOid = "";
    E3PSTask E3PSTask = null;

    TaskViewButtonAuth buttonAuth = null;

    if(taskOid == null){
        taskOid = "";
    }

    if(!taskOid.equals("")){
        E3PSTask = (E3PSTask)CommonUtil.getObject(taskOid);
    }

    if(E3PSTask != null) buttonAuth = new TaskViewButtonAuth(E3PSTask);

    ReferenceFactory rf = new ReferenceFactory();
    output = (ProjectOutput)rf.getReference(oid).getObject();
    task = output.getTask();
    project = output.getProject();

    taskOid = task.getPersistInfo().getObjectIdentifier().getStringValue();
    projectOid = project.getPersistInfo().getObjectIdentifier().getStringValue();

    data = new ProjectOutputData(output);
    name = data.name;
    description = data.description;
    role = data.role_en==null?"":data.role_en;
    location = data.location;

    String ojbType = output.getObjType();

    if(data.document instanceof WTDocument) {
        document = (WTDocument)data.document;
        LastDocument = (WTDocument)data.LastDocument;
    } else if(data.document instanceof EPMDocument) {
        epmDoc = (EPMDocument)data.document;
        LastEpmDoc = (EPMDocument)data.LastDocument;
    }

    //document = (WTDocument)data.document;
    //LastDocument = (WTDocument)data.LastDocument;
    chargeUser = data.registryUser;
    /*
        if(chargeUser == null && document != null) {
            chargeUser = (WTUser)document.getCreator().getPrincipal();
        }
     */

    if(role.length() > 0) {
        ProjectMemberLink roleUserLink = null;
        QueryResult roleResult = ProjectUserHelper.manager.getProjectRoleMember(project, null, role);
        if(roleResult.hasMoreElements()) {
            roleUserLink = (ProjectMemberLink)roleResult.nextElement();
            roleCharge = roleUserLink.getMember();
        }
    }


    //DocCodeType docType = null;

    if(location == null || location.length() == 0) {
        //docType = DocCodeTypeHelper.getRoot();
    }
    else {
        //docType = DocCodeTypeHelper.getDocCodeTypeToPath(location);
    }

    //ArrayList fullPath = DocCodeTypeHelper.getDocTypeParentPath(docType);

    if(chargeUser != null) {
        userName = chargeUser.getFullName();
        userOid = chargeUser.getPersistInfo().getObjectIdentifier().getStringValue();
    }

    if(roleCharge != null) {
        roleMember = roleCharge.getFullName();
        roleMemberOid = roleCharge.getPersistInfo().getObjectIdentifier().getStringValue();
    }

    boolean isSecu = true;

    boolean isPM = ProjectUserHelper.manager.isPM(project);
    if(CommonUtil.isAdmin() || isPM) {
        isEdit = true;
    }
    
    boolean isCost = false;
    WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
    
    String loginOid = CommonUtil.getOIDString(sessionUser);
    
    isCost = true;
    
    if(loginOid.equals(userOid) || CommonUtil.isAdmin()){
	   isCost = true; //산출물 담당자
    }
    
    if(loginOid.equals(roleMemberOid) || CommonUtil.isAdmin()){
	   isCost = true; //Role 담당
	}
    
    String costReview1 = "";
    String costReview2 = "";
    String costFinal1  = "";
    String costFinal2  = "";
    String tempName1   = "원가산출결과";
    String tempName2   = "총원가(원)";
    String tempName3   = "수익율(%)";
    
    if(ojbType.equals("COST")){
        costReview1 = data.totalCost;
        costFinal1 = data.totalCostFinal;
        costReview2 = data.rate;
        costFinal2 = data.rateFinal;
        
        if(CommonUtil.isAdmin() || isPM || CommonUtil.isMember("원가관리") || CommonUtil.isMember("원가_임원")) {
            
        }else{
            isSecu = false;
        }
        
    }else if(ojbType.equals("SALES")){
        costReview1 = data.salesTarget;
        costFinal1 = data.salesTargetFinal;
        costReview2 = data.yearAverageQty;
        costFinal2 = data.yearAverageQtyFinal;
        tempName1 = "판매목표가";
        tempName2 = "판매목표가(원)";
        tempName3 = "연평균수량(EA)";
    }
    
    String subjectType = data.subjectType;
    String gateCheckType_arr = StringUtil.checkNull(output.getGateCheckType()); 
%>

<head>

<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
.style1 {
        color: #FF0000
}
</style>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language='javascript'>

$(document).ready(function(){
    
    <% 
    if(!isSecu){
    %>
    alert('권한이 없습니다.');
    window.close();
    <% 
    }
    %>
});

<!--
 //사용자 가져오기 시작 ........................................................................................
//............................................................................................................
function addViewMember() {
    var url = "/plm/jsp/project/ProjectMemberViewPage.jsp?command=select&mode=multi&oid=<%=oid%>";
    attaches = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=620px; dialogHeight:460px; center:yes");
    if(typeof attaches == "undefined" || attaches == null) {
        return;
    }
    onViewMemberUpdate(attaches);
}

function onViewMemberUpdate(objArr) {
    var rtnParam = "";
    var paramLen = objArr.length;
    if(paramLen) {
        for(var i = 0; i < paramLen; i++) {
            infoArr = objArr[i];
            if(rtnParam.length > 0) {
                rtnParam += "&";
            }
            rtnParam += "viewMember" + "=" + infoArr[0];
        }
    }else {
        rtnParam += "viewMember" + "=" + infoArr[0];
    }

    onProgressBar();
    var param = "command=addViewMember";
    param += "&oid=<%=oid%>";
    param += "&" + rtnParam;

    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);

    onViewMemberRefresh();
}

function onDeleteViewMember(vid) {
    onProgressBar();
    var param = "command=deleteViewMember";
    param += "&oid=<%=oid%>";
    param += "&viewMemberOid=" + vid;

    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);

    onViewMemberRefresh();
}

function onMessage(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
    } else {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

}

function onViewMemberRefresh() {
    onProgressBar();
    var param = "command=searchViewMember";
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setViewMember, false);
}

function setViewMember(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_duty = showElements[0].getElementsByTagName("l_duty");
    var l_departmentName = showElements[0].getElementsByTagName("l_departmentName");
    var l_email = showElements[0].getElementsByTagName("l_email");
    var l_peopleOid = showElements[0].getElementsByTagName("l_peopleOid");
    var l_linkOid = showElements[0].getElementsByTagName("l_linkOid");

    var l_isView = showElements[0].getElementsByTagName("l_isView");
    var l_isDept = showElements[0].getElementsByTagName("l_isDept");


    var targetTable = document.getElementById("membertable");
    var targetTable1 = document.getElementById("depttable");

    var len = targetTable.rows.length;
    for(var i=len; i > 1; i--) {
        targetTable.deleteRow(i-1);
    }

    var len1 = targetTable1.rows.length;
    for(var i=len1; i > 1; i--) {
        targetTable1.deleteRow(i-1);
    }

    var loid = false;
    var lname = false;
    var lduty = false;
    var ldepartmentName = false;
    var lemail = false;
    var lpeopleOid = false;
    var llinkOid = false;

    var lisView = false;
    var lisDept = false;

    var deleteHtml;
    var nameHtml;

    for(var i = 0; i < l_oid.length; i++) {
        lisDept = decodeURIComponent(l_isDept[i].text);
        if(lisDept == 'true') {
            lname = decodeURIComponent(l_name[i].text);
            llinkOid = decodeURIComponent(l_linkOid[i].text);

            deleteHtml = "<a href=\"#\" onClick=\"javascript:onDeleteViewMember('" + llinkOid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";


            tableRows = targetTable1.rows.length;
            newTr = targetTable1.insertRow(tableRows);

            //부서명
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = lname;
<%
    if(isEdit) {
%>
            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = deleteHtml;
<%
    }
%>
        } else {
            loid = decodeURIComponent(l_oid[i].text);
            lname = decodeURIComponent(l_name[i].text);
            lduty = decodeURIComponent(l_duty[i].text);
            ldepartmentName = decodeURIComponent(l_departmentName[i].text);
            lemail = decodeURIComponent(l_email[i].text);
            lpeopleOid = decodeURIComponent(l_peopleOid[i].text);
            llinkOid = decodeURIComponent(l_linkOid[i].text);

            lisView = decodeURIComponent(l_isView[i].text);


            lnameStr = "<font color='#black'>" + lname + "</font>";
            if(lisView == 'true') {
                lnameStr = "<font color='#brown'>" + lname + "</font>";

            }
            nameHtml = "<a href=\"#\" onClick=\"JavaScript:viewPeople('"+lpeopleOid+"')\">" + lnameStr + "</a>";


            if(lduty.length == 0) {
                lduty = " ";
            }

            if(ldepartmentName.length == 0) {
                ldepartmentName = " ";
            }

            deleteHtml = "<a href=\"#\" onClick=\"javascript:onDeleteViewMember('" + llinkOid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";

            tableRows = targetTable.rows.length;
            newTr = targetTable.insertRow(tableRows);

            //이름
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = nameHtml;

            //직위
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = lduty;

            //부서
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = ldepartmentName;
<%
    if(isEdit) {
%>
            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = deleteHtml;
<%
    }
%>
        }
    }
}

    function ViewDoc(oid){
        openView(oid, 870, null, null);
    }


    function viewPeople(oid) {
        var url = "/plm/jsp/groupware/company/peopleView.jsp?oid="+oid;
        openWindow(url, 'peopleView', '850', '600');

        //var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
        //openSameName(url,"550","450","status=no,scrollbars=no,resizable=no");
    }

    function allVersionProject(oid,projectValue){
        openWindow("/plm/servlet/e3ps.common.obj.AllVersionServlet?oid="+oid+"&projectValue="+projectValue, "allversion", 870, 400);
    }

    function doViewPart(_poid) {
        alert("part view ....");
    }
    
    function onSave(){
    	
    	form = document.forms[0];
    	var opid = form.oid.value;
        var gateCheckType = "";
        
        for(i=0; i<form.gateCheckType.length; i++) {
        	if(form.gateCheckType[i].checked){
        		gateCheckType += form.gateCheckType[i].value+',';
        	}
        }

        var param = "command=updateGateCheckType";
            param += "&oid=" + opid;
            param += "&gateCheckType=" + gateCheckType;
            
        var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
            window.returnValue = true;
        postCallServer(url, param, onMessage, false);
    }
    
    function onMessage(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg == 'true') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
            //opener.location.reload();
            opener.parent.document.location.href="/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=taskOid%>&popup=popup"; 

        } else {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
            return;
        }

        window.returnValue = true;
        window.close();
    }

//-->
</script>
<style type="text/css">

body {
    margin-top: 0px;
    margin-left:10px;
    margin-right: 0px;
    margin-bottom: 5px;
}

</style>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form method="post">
<!-- hidden begin -->
<input type='hidden' name='docTypeOid' value=''>
<input type='hidden' name='taskOid' value='<%=taskOid%>'>
<input type='hidden' name='oid' value='<%=oid%>'><!-- ProjectOutput OID -->
<!-- hidden end -->
<!-- 산출물 정의 등록 layer 시작 -->
    <table border="0" cellpadding="0" cellspacing="0" width="640">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="640" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table width="640" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/logo_popupbg.png">
                      <table height="28" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01731") %><%--산출물 정보--%></td>
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
             <table width="640" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="640">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
                <col width='20%'><col width='80%'>
                <%if(data.location != null){ %>
                <tr>
                    <td class="tdblueL"><%=data.objType %><%=messageService.getString("e3ps.message.ket_message", "02239") %><%--위치--%> </td>
                    <td class="tdwhiteL0">&nbsp;<%=(data.location==null)?"":data.location%> </td>
                </tr>
                <%} %>
                <tr>
                    <td class="tdblueL"><%=data.objType %><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                    <td class="tdwhiteL0">&nbsp;<%=name%> </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td class="tdwhiteL0">
                    <textarea name="description" rows="5" class="fm_area" readonly style="border:0;width:99%"><%=description%></textarea></td>
                </tr>
            </table>
            
            <table border="0" cellspacing="0" cellpadding="0" width="640">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <%if(("원가".equals(data.objType) || "판가".equals(data.objType)) && isCost){ %>
            <table id="costInfo" border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
			    <td valign="top" style="padding:0px 0px 0px 0px">
			    <table width="100%" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
			            <td id="costLabel" class="font_03"><%=StringUtil.checkNull(tempName1) %></td>
			    
			        </tr>
			        <tr>
			        <td class="space5"></td>
			        </tr>
			    </table>
			    
			    
			    <table border="0" cellpadding="0" cellspacing="0" width="100%">
			       <tr><td class="tab_btm2"></td></tr>
			    </table>
			    
			    <table border="0" cellpadding="0" cellspacing="0" width="100%">
			       <tr>
			            <td class="tdblueM" width="20%">구분</td>
			            <td id="costTd1" class="tdblueM" width="40%"><%=StringUtil.checkNull(tempName2) %></td>
			            <td id="costTd2" class="tdblueM" width="40%"><%=StringUtil.checkNull(tempName3) %></td>
			        </tr>
			        <tr>
			            <td class="tdwhiteM">검토</td>
			            <td class="tdwhiteM"><%=StringUtil.checkNull(costReview1) %></td>
			            <td class="tdwhiteM"><%=StringUtil.checkNull(costReview2) %></td>
			        </tr>
			        <tr>
			            <td class="tdwhiteM">최종</td>
			            <td class="tdwhiteM"><%=StringUtil.checkNull(costFinal1) %></td>
			            <td class="tdwhiteM"><%=StringUtil.checkNull(costFinal2) %></td>
			        </tr>
			    </table>
			    </td>
			</tr>
			</table>
			<%} %>
<%
    PeopleData peopleData = null;

    if(document != null) {
        ObjectData docData = new ObjectData(document);
%>
                <table border="0" cellpadding="0" cellspacing="0" class="640">
                    <tr>
                        <td class="space15"></td>
                    </tr>
                </table>
                <!-- 문서정보 시작 -->
                <table border="0" cellpadding="0" cellspacing="0" width="640">

                    <tr>
                  <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                  <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01400") %><%--문서 정보--%></td>
                  <td align="right">&nbsp;

                    </tr>
                    <!--tr>
                        <td align="right">&nbsp;</td>
                        <td align="right"><a href="#" onClick="javascript:allVersionProject('<%//=docData.getOid()%>','false');">
                        <img src="/plm/portal/images/img_default/button/board_btn_allver.gif" alt="모든버전" width="76" height="20" border="0">
                        </a>
                        </td>
                    </tr-->

                </table>
                <table border="0" cellpadding="0" cellspacing="0" width="640">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="640">
                    <col width='20%'><col width='30%'><col width='20%'><col width='30%'>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                        <td class="tdwhiteL0" colspan="3"><a href="#" onClick="javascript:ViewDoc('<%=docData.getOid()%>')"><%=docData.getNumber()%></a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                        <td class="tdwhiteL0" colspan="3"><a href="#" onClick="javascript:ViewDoc('<%=docData.getOid()%>')"><font color="blue" ><%=docData.getName()%> </font></a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>

                        <td class="tdwhiteL"><a href="#" onClick="javascript:ViewDoc('<%=docData.getOid()%>')"><font color="blue" ><%=docData.getVersion().substring(0,1)%></font></a>
                        (<a href="#" onClick="javascript:ViewDoc('<%=CommonUtil.getOIDString(LastDocument)%>')"><font color="blue" ><%=LastDocument.getVersionDisplayIdentifier().toString() %></font></a>)</td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td class="tdwhiteL0"><%=docData.getState()%></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                        <td class="tdwhiteL">
<%
        WTUser docUser = (WTUser)document.getCreator().getPrincipal();
        peopleData = null;
        peopleData = new PeopleData(docUser);
%>
                            <a href="#" onClick="JavaScript:viewPeople('<%=(peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue()%>')">
                                <%=docUser.getFullName()%>
                            </a>
                        </td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                        <td class="tdwhiteL0"><%=docData.getCreateDate()%></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02853") %><%--최종수정자--%></td>
                        <td class="tdwhiteL">
<%
        docUser = (WTUser)document.getModifier().getPrincipal();
        peopleData = null;
        peopleData = new PeopleData(docUser);
%>
                            <a href="#" onClick="JavaScript:viewPeople('<%=(peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue()%>')">
                                <%=docUser.getFullName()%>
                            </a>
                        </td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
                        <td class="tdwhiteL0"><%=docData.getUpdateDate()%></td>
                    </tr>
                </table>
                <!-- 문서정보 끝 -->
<%
    }
%>

<%
    String epmOid = "";
    String rNumber = "";
    String rName = "";
    String rVer = "";
    String rState = "";
    String rFinalModifyDate = "";
    String rFinalModifier = "";
    String rCreator = "";
    String rLocation = "";
    String fileUrl = "";
    if(epmDoc != null) {
        epmOid = CommonUtil.getOIDString(epmDoc);
        rNumber = epmDoc.getNumber();
        rName = epmDoc.getName();
        rVer = epmDoc.getVersionInfo().getIdentifier().getValue();
        rVer += "." + epmDoc.getIterationInfo().getIdentifier().getValue();
        rState = epmDoc.getLifeCycleState().getDisplay();
        rFinalModifyDate = DateUtil.getDateString(epmDoc.getModifyTimestamp(), "d");
        rCreator = epmDoc.getCreatorFullName();
        rLocation = epmDoc.getLocation();
        rFinalModifier = epmDoc.getModifier().getFullName();
        ObjectData data3 = new ObjectData(epmDoc);
        fileUrl = data3.getFileUrl();


%>
                <table border="0" cellpadding="0" cellspacing="0" class="100%">
                    <tr>
                        <td class="space20"></td>
                    </tr>
                </table>
                <!-- 도면정보 시작 -->
                <table width="640" height="20" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="../../portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01293") %><%--도면정보--%></td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>

                <table border="0" cellpadding="0" cellspacing="0" width="640">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
                </table>
                <table width="640" border="0" cellspacing="0" cellpadding="0">
                <col width="15%"><col width="35%"><col width="15%"><col width="35%">
                <tr>
                    <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%> </td>
                    <td class="tdwhiteL"><a href="#" onClick="javascript:ViewDoc('<%=epmOid%>')"><%=rNumber%></a></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                    <td class="tdwhiteL0"><%=rName%></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                    <td class="tdwhiteL">
                        <%=epmDoc.getVersionInfo().getIdentifier().getValue()%>
                        <font color=black>.<%=wt.vc.VersionControlHelper.getIterationIdentifier(epmDoc).getSeries()%></font>
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%> </td>
                    <td class="tdwhiteL0"><%=rState%></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02239") %><%--위치--%></td>
                    <td class="tdwhiteL" colspan=3><%=rLocation%></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td class="tdwhiteL"><%=rCreator%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
                    <td class="tdwhiteL0"><%=rFinalModifyDate%></td>
                </tr>
                <tr>
                    <td height="22" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                    <td colspan="3" class="tdwhiteL">
                        <%=epmDoc.getDescription()==null?"&nbsp;":e3ps.common.util.StringUtil.convertEnterChar(epmDoc.getDescription())%>
                    </td>

                </tr>
                </table>

        <%

        EDMProperties edmProperties = EDMProperties.getInstance();
        EDMAttributes edmAttributes = EDMAttributes.getInstance();
        HashMap ibaValues = EDMAttributes.getIBAValues(epmDoc, Locale.KOREAN);

        String category = null;
        if(ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
            category = EDMEnumeratedTypeUtil.getCADCategory((String)ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),Locale.KOREAN);
        }



            WTPart part = null;
            EPMDocument model = null;
            ArrayList relateds = null;
            try {
                relateds = EDMHelper.getReferencedByParts(epmDoc, edmProperties.getReferenceType(category),EDMHelper.REQUIRED_STANDARD);
                if( (relateds != null) && (relateds.size() > 0) ) {
                    part = (WTPart)((Object[])relateds.get(0))[1];
                }

                if(part != null) {
                    model = getAssociatedModels(epmDoc, part,edmProperties.getReferenceType(category), EDMHelper.REQUIRED_STANDARD);
                }
            }
            catch(Exception e) {
        	Kogger.error(e);
            }
        %>
        <%  if(part != null) {%>
                <table border="0" cellspacing="0" cellpadding="0" width="640">
                    <tr>
                        <td class="space15"></td>
                    </tr>
                </table>
                <table width="640" height="20" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="../../portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01243") %><%--대표부품/3D 모델--%></td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="640">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="640">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="640">
                    <tr>
                        <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                        <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                        <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                        <td width="150" class="tdblueM">3D <%=messageService.getString("e3ps.message.ket_message", "01372") %><%--모델번호--%></td>
                        <td width="75" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                    </tr>
                    <tr>
                        <td width="140" class="tdwhiteM"><%if(part==null){%>&nbsp;<%}else{%><%=part.getNumber()%><%}%></td>
                        <td width="200" class="tdwhiteL"><%=(part==null)? "&nbsp;":part.getName()%></td>
                        <td width="75" class="tdwhiteM"><%=(part==null)? "&nbsp;":StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision))%></td>
                        <td width="150" class="tdwhiteM"><%if(model==null){%>&nbsp;<%}else{%><a href="#" onClick="javascript:doViewEPM('<%=PersistenceHelper.getObjectIdentifier(model).getStringValue()%>');"><%=model.getNumber()%></a><%}%></td>
                        <td width="75" class="tdwhiteM0"><%=(model==null)? "&nbsp;":model.getVersionIdentifier().getSeries().getValue()%></td>
                    </tr>
                </table>
        <%  } %>

        <%} %>
        </table>

        <% if("ETC".equals(ojbType)){

            Timestamp stamp = output.getEtcCompletion();
            String createTime = "";
            if(stamp != null){
                createTime = DateUtil.getDateString(stamp ,"d");
            }
            WTPrincipalReference wp = output.getOwner();
            String uNmae = "";
            if(wp != null){
                uNmae = wp.getFullName();
            }
            String completeReson = output.getComplete_reason();
            if(completeReson == null){
                completeReson = "";
            }


        %>
            <!-- ETC 시작 -->
            <table border="0" cellspacing="0" cellpadding="0" width="640">
                <tr>
                    <td class="space15"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="640">

                <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00992") %><%--근거사유--%></td>
              <td align="right">&nbsp;

                </tr>

            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="640">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
                <col width='20%'><col width='80%'>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td class="tdwhiteL0"><%=uNmae %></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%></td>
                    <td class="tdwhiteL0"><%=createTime %></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00992") %><%--근거사유--%></td>
                    <td class="tdwhiteL0"><pre><%=completeReson %></pre></td>
                </tr>
            </table>

        <%
        } %>
        <%if("대상".equals(subjectType)){ %>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
                <tr>
                    <td class="space15"></td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="640">
                <tr><td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03">Gate</td>
                    <td align="right">&nbsp;</td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="640">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
                </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
                <col width='10%'><col width='60%'><col width='30%'>
                <tr>
                    <td class="tdblueL">추가점검</td>
                    <td class="tdwhiteL0">
                    
                    
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        <%
                        Vector targetTypeVec = NumberCodeHelper.manager.getNumberCodeForSortingQuery("GATELEVELNAME");
                        String gateCheckType_arr_[] = {};
                        boolean isGate = false;
                        if (targetTypeVec != null) {
                            for (int i = 0; i < targetTypeVec.size(); i++) {
                                NumberCode code = (NumberCode) targetTypeVec.get(i);
                                String gateTypeStr = ((String)code.getName()).replaceAll("Gate", "");
                                
                                if(gateCheckType_arr != ""){
                                    gateCheckType_arr_ = gateCheckType_arr.split(",");
                                }
                                
                                for (int j = 0; j < gateCheckType_arr_.length;  j++) {
                                    if(gateTypeStr.equals(gateCheckType_arr_[j])){
                                        isGate = true;
                                    }
                                }
                        %>
                                                <input type="checkbox" name="gateCheckType" id ="gateCheckType" value="<%=gateTypeStr %>" <%if(isGate){ %>checked<%} %>><%=code.getName().replaceAll("Gate", "") %>
                        <%
                                isGate = false;
                            }
                        }
                        %>
                        </td>
                        <%if(isEdit){%>
                        <td width="10">&nbsp;</td>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        <%} %>
                      </tr>
                      </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
                <tr>
                    <td class="space15"></td>
                </tr>
            </table>
            <%} %>
        </table>
        </td>
        </tr>
        </table>

<!-- 산출물 정의 등록 layer 끝 -->
<!-- ############################################################################################################################## -->
</form>
</body>
</html>
