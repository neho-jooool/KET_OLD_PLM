<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Hashtable"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.project.Role"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.content.ContentRoleType"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.common.util.KETStringUtil"%>
<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@page import="e3ps.common.content.uploader.FileUploader"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<%@page import="e3ps.common.content.E3PSContentHelper"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.CheckoutLink"%>
<%@page import="e3ps.project.historyprocess.HistoryHelper"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.beans.ProjectTaskScheduleHelper"%>
<%@page import="e3ps.project.beans.RoleComparator"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02360") %><%--일정변경사유--%></title>
<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/top_include.jsp"%>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/viewObject.js"></script>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<%@include file="/jsp/common/multicombo.jsp"%>
<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "HISTORYCHANGETYPE");
    List<Map<String, Object>> historyChangeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    FormUploader fileUploader = FormUploader.newFormUploader(request);
    KETParamMapUtil paramMap = KETParamMapUtil.getMap(fileUploader.getFormParameters());

    String oid             = paramMap.getString("oid");
    String cmd             = paramMap.getString("cmd");
    String mode            = paramMap.getString("mode");
    String popup           = paramMap.getString("popup");
    String historyNoteType = paramMap.getString("historyNoteType");
    String historyNote     = paramMap.getString("historyNote");
    // [START] [PLM System 1차개선] 호출 위치 추가, 2013-08-05, BoLee
    String from            = paramMap.getString("from");
    // [END] [PLM System 1차개선] 호출 위치 추가, 2013-08-05, BoLee

    // [START] [PLM System 1차개선] 추가, 2013-06-07, 김종호
    String historyNoteWebEditor     = paramMap.getString("historyNoteWebEditor");
    String historyNoteWebEditorText = paramMap.getString("historyNoteWebEditorText");
    String[] historyRole            = paramMap.getStringArray("historyRole");

    paramMap.put("historyRoleComma",  KETParamMapUtil.toString( historyRole ));
    // [END] [PLM System 1차개선] 추가, 2013-06-07, 김종호

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData data = new E3PSProjectData(project);
    String viewUrl = "";
    if ( project instanceof ReviewProject ) {
        viewUrl = "/plm/jsp/project/ReviewProjectView.jsp";
    }
    else if ( project instanceof ProductProject ) {
        viewUrl = "/plm/jsp/project/ProjectView.jsp";
    }
    else if ( project instanceof MoldProject ) {
        viewUrl = "/plm/jsp/project/MoldProjectView.jsp";
    }

    // [START] [PLM System 1차개선] Project 일정 변경 결재 대상 여부 체크, 2013-07-16, BoLee
    String isApproval = "";
    String proudctPlanEnd = "";
    boolean isCheckPlan = false;
    MoldProject moldProject = null;
    MoldItemInfo moldInfo = null;
    ProductProject productProject = null;
    E3PSProjectData ppdata = null;

    if ( mode.length() == 0 ) {
        isApproval = ProjectTaskScheduleHelper.isProjectScheduleForApproval(project);

        // [START] [PLM System 1차개선] 금형 Project일 경우 Project 계획완료일 체크(제품 Project 계획완료일 내에서 일정 수립), 2013-08-05, BoLee
        if ( project instanceof MoldProject ) {

            moldProject = (MoldProject)project;
            moldInfo = moldProject.getMoldInfo();

            if ( moldInfo == null ) {
                moldInfo = MoldItemInfo.newMoldItemInfo();
            }

            if ( moldInfo != null ) {
                productProject = moldInfo.getProject();
                ppdata = new E3PSProjectData(productProject);
            }

            if ( data.pjtPlanEndDate != null && ppdata.pjtPlanEndDate != null ) {

                if ( ppdata.pjtPlanEndDate.compareTo(data.pjtPlanEndDate) == -1 ) {

                    proudctPlanEnd = DateUtil.getDateString(ppdata.pjtPlanEndDate, "D");
                    isCheckPlan = true;
                }
            }
        }
        // [END] [PLM System 1차개선] 금형 Project일 경우 Project 계획완료일 체크(제품 Project 계획완료일 내에서 일정 수립), 2013-08-05, BoLee
    }
    // [END] [PLM System 1차개선] Project 일정 변경 결재 대상 여부 체크, 2013-07-16, BoLee

    String contentType = request.getContentType();
    FileUploader uploader = null;

    // [START] [PLM System 1차개선] 일정변경사유 저장 로직 수정, 2013-06-24, BoLee
    if ( "out".equals(cmd) || "outApproval".equals(cmd) || "outComplete".equals(cmd) ) {

        TemplateProject original = null;

        try {

            // [START] [PLM System 1차개선] 일정변경사유 저장 로직 수정, 2013-06-24, BoLee

            Map<String, Object> param = new HashMap<String, Object>();
            param = paramMap;
            original = (TemplateProject)HistoryHelper.saveChangeHistory(project, param);

            // [END] [PLM System 1차개선] 일정변경사유 저장 로직 수정, 2013-06-24, BoLee

            // [START] [PLM System 1차개선] 첨부파일 추가, 2013-06-07, 김종호
            
            String deleteOID = (String) paramMap.getString("deleteOID");
    
            deleteOID = StringUtils.removeEnd(deleteOID, "&");
            
            String deleteFiles[] = deleteOID.split("&");
            
            //기존 존재하던 모든 파일 목록
            Vector oldFiles = ContentUtil.getSecondaryContents(original);
            
            for(int i = 0; i < oldFiles.size(); i++) {
                ContentInfo info = (ContentInfo) oldFiles.elementAt(i);
                
                if(deleteFiles != null){
                    for(int j=0; j<deleteFiles.length; j++){
                        if(info.getContentOid().equals(deleteFiles[j])){
                            original = (TemplateProject)E3PSContentHelper.service.delete(original, (ApplicationData)CommonUtil.getObject(info.getContentOid()));
                        }
                    }    
                }
            }
            
            if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 && original != null ) {

                Vector files = fileUploader.getFiles();

                if ( files != null ) {
                    for ( int i = 0; i < files.size(); i++ ) {
                        E3PSContentHelper.service.attach(original, (WBFile)files.get(i), "historyChange", ContentRoleType.SECONDARY);
                    }
                }
            }
            // [END] [PLM System 1차개선] 첨부파일 추가, 2013-06-07, 김종호
        }
        catch (Exception e) {
            Kogger.error(e);
        }

        if ( original == null ) {
%>
        <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "02351") %><%--일정 변경 사유 저장을 실패하였습니다--%>');
            self.close();
        </script>
<%
        }
        else {
            String pboOid = CommonUtil.getOIDString(original);
%>
        <script>
<%
            if ( "out".equals(cmd) ) {                  // [저장] 버튼 클릭한 경우
%>
            alert('<%=messageService.getString("e3ps.message.ket_message", "02352") %><%--일정 변경 사유가 저장되었습니다--%>');
            self.close();
<%
            }
            else if ( "outApproval".equals(cmd) ) {     // [결재요청] 버튼 클릭한 경우, 결재요청 팝업 오픈
%>
            alert("<%=messageService.getString("e3ps.message.ket_message", "00779") %><%--결재요청 화면에서 결재 요청하시기 바랍니다--%>");
            opener.openRequestApprovalPopup('<%= pboOid %>', '<%= from %>');
            self.close();
<%
            }
            else if ( "outComplete".equals(cmd) ) {     // [완료] 버튼 클릭한 경우, 일정 변경 완료 처리
%>
            if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03280") %><%--Project 일정 변경을 완료하시겠습니까?--%>") ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00406") %><%--Project 일정 변경이 완료되어 Project 상태가 [진행] 상태로 변경됩니다--%>");
                opener.completeChangeProjectSchedule('<%= from %>');
            }

            self.close();
<%
            }
%>
        </script>
<%
        }
    }
    // [END] [PLM System 1차개선] 일정변경사유 저장 로직 수정, 2013-06-24, BoLee
    ArrayList historyRoleCondList = new ArrayList();
    //
    if ( project != null ) {
        historyNoteType = ""+project.getHistoryNoteType();
        if ( project.getHistoryNote() != null ) {
            historyNote = project.getHistoryNote();
        }
        if ( project.getHistoryNoteWebEditor() != null ) {
            historyNoteWebEditor = (String)project.getHistoryNoteWebEditor();
        }
        if ( project.getHistoryRole() != null ) {
            historyRoleCondList = KETStringUtil.getToken( project.getHistoryRole(), ", " );
        }
    }

    HashMap roleUser = new HashMap();
    ProjectMemberLink mlink = null;

    ReferenceFactory rf = new ReferenceFactory();
    TemplateProject templateProject = (TemplateProject)rf.getReference(oid).getObject();
    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.ROLEMEMBER);

    Object obj[] = null;
    while(qr.hasMoreElements()) {
        obj = (Object[])qr.nextElement();
        mlink = (ProjectMemberLink)obj[0];
        if(mlink.getPjtRole() != null && mlink.getPjtRole().length() > 0) {
            roleUser.put(mlink.getPjtRole(), mlink.getMember());
        }
    }

    Vector vecTeamStd = null;
    // 기준 Team
    Object pobj = CommonUtil.getObject(oid);

    TeamTemplate tempTeam = null;
    if(pobj instanceof ReviewProject){
        tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
    }else if(pobj instanceof ProductProject){
        if("자동차 사업부".equals( ((ProductProject)pobj).getTeamType() )){

            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");

        }else if("KETS".equals( ((ProductProject)pobj).getTeamType() ) || "KETS_PMO".equals( ((ProductProject)pobj).getTeamType() )) {

            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");

        }else{

            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");

        }
    }else if(pobj instanceof MoldProject){
        tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
    }


    if(tempTeam != null) {
        vecTeamStd = tempTeam.getRoles();
    }
%>

<script type="text/javascript">
<!--
    //첨부파일 시작 *****************************************************************************************************************
    function insertFileLine () {
        var tBody = document.getElementById("secondaryTable");
        var innerRow = tBody.insertRow();
        //var innerCell = innerRow.insertCell();
        var filePath = "secondaryFile"+tBody.rows.length;

        newTd = innerRow.insertCell();//delete
        newTd.className = "tdwhiteM";
        //newTd.innerHTML = "<input name='secondarySelect' type='checkbox'>";
        newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
            + "<div style=\"display:none;\"><input name='secondarySelect' type='checkbox' class='chkbox'></div>";

        newTd = innerRow.insertCell();//file
        newTd.className = "tdwhiteL0";
        //newTd.colSpan = 2;
        newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width: 520px;'>";
    }

    function deleteFileLine () {
        var body = document.getElementById("secondaryTable");
        if (body.rows.length == 0) return;
        var file_checks = document.forms[0].secondarySelect;
        if ( body.rows.length == 1 ) {
            body.deleteRow(0);
        }
        else {
            for (var i = body.rows.length; i > 0; i--) {
                if (file_checks[i-1].checked) body.deleteRow(i - 1);
            }
        }
    }
    
    function deleteFile(contentOid){
        document.forms[0].deleteOID.value += contentOid+"&";
    }
    // 첨부 파일 끝 *****************************************************************************************************************

    // [START] [PLM System 1차개선] [저장], [결재요청], [완료] 버튼 클릭 시 처리 수정, 2013-06-24, BoLee
    function changeHistory (oid, mode) {

        if ( !checkValidate() ) {
            return;
        }

        document.frm.historyNote.value = "";
        // innoditor WebEditor
        // 첫번째 매개변수 => true : < & 특수문자 처리,  false : 처리안함
        // 두번째 매개변수 => 이노디터 번호
        document.frm.historyNoteWebEditor.value = fnGetEditorHTMLCode(false, 0);
        document.frm.historyNoteWebEditorText.value = fnGetEditorText(0);

//         disabledAllBtn();
        showProcessing();

        if ( mode == "save" ) {                 // [저장] 버튼
            document.frm.cmd.value = "out";
        }
        else if ( mode == "approval" ) {        // [결재요청] 버튼
            document.frm.cmd.value = "outApproval";
        }
        else if ( mode == "complete" ) {        // [완료] 버튼
            document.frm.cmd.value = "outComplete";
        }

        document.frm.oid.value = oid;
        document.frm.submit();
    }
    // [END] [PLM System 1차개선] [저장], [결재요청], [완료] 버튼 클릭 시 처리 수정, 2013-06-24, BoLee

    function checkValidate () {
        if ( getCheckedValue(document.frm.historyNoteType) == "" ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02350") %><%--일정 변경 구분을 선택 하십시오--%>');
            return false;
        }

        var strHTMLCode = fnGetEditorHTMLCode(false, 0);
        if ( strHTMLCode == "" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02363") %><%--일정변경사유를 입력 하십시오--%>');
            return false;
        }
        return true;
    }

    function hisRole(v) {
        if ( v == "s" ) {
        	$('#trHisRole').show();
            //document.getElementById("trHisRole").style.display = "block";
            document.getElementById("hisRoleShow").style.display = "none";
            document.getElementById("hisRoleHide").style.display = "block";
        }
        else if ( v== "h" ) {
        	$('#trHisRole').hide();
            //document.getElementById("trHisRole").style.display = "none";
            document.getElementById("hisRoleShow").style.display = "block";
            document.getElementById("hisRoleHide").style.display = "none";
        }
    }

    function getCheckedValue(radioObj) {
        if(!radioObj)
            return "";
        var radioLength = radioObj.length;
        if(radioLength == undefined)
            if(radioObj.checked)
                return radioObj.value;
            else
                return "";
        for(var i = 0; i < radioLength; i++) {
            if(radioObj[i].checked) {
                return radioObj[i].value;
            }
        }
        return "";
    }


    function historyTypeChange(v) {
        if ( v == "5" ) {
            document.getElementById("etcDetail").style.display = "block";
        }
        else {
            document.getElementById("etcDetail").style.display = "none";
        }
    }
//-->
</script>

<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
//<![CDATA[

// -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
// -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
// -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
var g_arrSetEditorArea = new Array();
g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정

//]]>
</script>

<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>

<script type="text/javascript">
//<![CDATA[

// -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우

// Skin 재정의
//g_nSkinNumber = 0;

// 크기, 높이 재정의
g_nEditorWidth = 740;
g_nEditorHeight = 390;

//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<!-- 이노디터 JS Include End -->

<!-- 글 내용을 보여줄 외곽 style 설정 : 사이트 특성에 맞게 수정 가능 및 제거 가능 -->
<!-- 이노디터의 직접적인 설정과는 무관하며, View 영역을 표시하기 위한 Style 설정임 -->
<style type="text/css">
/*<![CDATA[*/
.outline    {border:0px solid #c4cad1;padding:5px;}
/*]]>*/
</style>

<!-- Start : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->
<!--
    이노디터 기본글꼴을 굴림, 기본글크기를 10pt 로 설정하였다면
    View 페이지에서도 작성된 곳의 보여주는 부분을 동일하게 설정
-->
<style type="text/css">
/*<![CDATA[*/
#divView            {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;text-align:left;overflow-x:auto;word-wrap:break-word;}
#divView TD         {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;}

#divView P          {margin-top:2px;margin-bottom:2px;}/* IE 에서 줄바꿈(엔터친 경우) style 설정 */
#divView BLOCKQUOTE {margin-top:2px;margin-bottom:2px;}/* IE 에서 들여쓰기 style 설정 */
/*]]>*/
</style>
<!-- End : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->

<script type="text/javascript">
//<![CDATA[
function init() {
    if ( document.getElementById("divView") != undefined ) {
        fnLoadContent ();
    }
    else {
        fnSetEditorHTML();
    }
    // 숨기기/보이기
    if ( document.getElementById("hisRoleShow") != null && document.getElementById("hisRoleHide") != null ) {
    document.getElementById("hisRoleShow").style.display = "none";
    document.getElementById("hisRoleHide").style.display = "block";
}
}

function fnLoadContent () {

    // 이노디터에서 작성된 내용을 전달
    var strContent = document.frm["historyNoteWebEditor"].value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.frm["hdnBackgroundColor"].value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.frm["hdnBackgroundImage"].value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = document.frm["hdnBackgroundRepeat"].value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
}

function fnSetEditorHTML() {
    var strHTMLCode = document.frm["historyNoteWebEditor"].value;

    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.frm["hdnBackgroundColor"].value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.frm["hdnBackgroundImage"].value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.frm["hdnBackgroundRepeat"].value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
}
//]]>
</script>
</head>

<body onload="javascript:init();">
<%@include file="/jsp/common/processing.html"%>
    <form id="frm" name="frm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="cmd" /> 
        <input type="hidden" name="oid" value="<%=oid%>" /> 
        <input type="hidden" name="popup" value="<%=popup%>" />
        <!-- [START] [PLM System 1차개선] 호출 위치 추가, 2013-08-05, BoLee -->
        <input type="hidden" name="from" value="<%=from%>" />
        <!-- [END] [PLM System 1차개선] 호출 위치 추가, 2013-08-05, BoLee -->
        <input type=hidden name=deleteOID>
        <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
        <textarea name="historyNoteWebEditor" rows="0" cols="0" style="display: none"><%=historyNoteWebEditor%></textarea>

        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
        <input type="hidden" name="hdnBackgroundColor" value="" /> 
        <input type="hidden" name="hdnBackgroundImage" value="" /> 
        <input type="hidden" name="hdnBackgroundRepeat" value="" />
        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
        <!-- 이노디터에서 작성된 내용을 보낼 Form End -->
        <table style="width: 100%;">
            <tr>
                <td valign="top">
                    <!-------------------------------------- 상단 제목 버튼 시작 //-------------------------------------->
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <table style="width: 100%;">
                                    <tr>
                                        <td background="/plm/portal/images/logo_popupbg.png">
                                            <table style="height: 28px;">
                                                <tr>
                                                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02360")%><%--일정변경사유--%></td>
                                                    <td style="width: 10px;"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                                    </tr>
                                </table>
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="space10"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table style="width: 100%;">
                        <tr>
                            <td align="right">
                                <table>
                                    <tr>
                                        <%
                                            if (mode.length() == 0) {
                                        %>
                                        <!-- [START] [PLM System 1차개선] 일정변경사유 입력 화면 버튼 수정, 2013-06-24, BoLee -->
                                        <!-- [저장] 버튼 : 일정변경사유 입력 내용 저장 -->
                                        <td>
                                            <table>
                                                <tr>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:changeHistory('<%=oid%>', 'save');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a>
                                                    </td>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <!-- 단계 일정 변경 시 [결재요청] 버튼, 단계 일정 내 일정 변경 시 [완료] 버튼 표시함 -->
                                        <%
                                        if (StringUtil.checkNull(isApproval).startsWith("T")) { // 일정 변경 범위가 결재 대상인 경우 [결재요청] 버튼 표시
                                        %>
                                        <!-- [결재요청] 버튼 : 결재요청 팝업 오픈 후 일정변경사유 입력 팝업 닫음 -->
                                        <td>&nbsp;</td>
                                        <td>
                                            <table>
                                                <tr>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                        <%
                                                            if (isCheckPlan) { // 금형 Project 계획완료일이 제품 Project 계획완료일보다 늦을 경우
                                                        %> <a
                                                        href="javascript:alert('<%=messageService.getString("e3ps.message.ket_message", "02554", proudctPlanEnd)%><%--제품 프로젝트의 계획 종료일({0}) 일정 안에 \n금형 일정을 수립해야 합니다 \n금형 프로젝트 일정을 조정 하시기 바랍니다--%>');"
                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778")%><%--결재요청--%></a>
                                                        <%
                                                            } else {
                                                        %> <a href="javascript:changeHistory('<%=oid%>', 'approval');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778")%><%--결재요청--%></a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%
                                            } else if ("F".equals(StringUtil.checkNull(isApproval))) { // 일정 변경 범위가 결재 대상이 아닌 경우 [완료] 버튼 표시
                                        %>
                                        <!-- [완료] 버튼 : Project 상태 변경(PROGRESS) 후 일정변경사유 입력 팝업 닫음 -->
                                        <td>&nbsp;</td>
                                        <td>
                                            <table>
                                                <tr>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                        <%
                                                            if (isCheckPlan) { // 금형 Project 계획완료일이 제품 Project 계획완료일보다 늦을 경우
                                                        %> <a
                                                        href="javascript:alert('<%=messageService.getString("e3ps.message.ket_message", "02554", proudctPlanEnd)%><%--제품 프로젝트의 계획 종료일({0}) 일정 안에 \n금형 일정을 수립해야 합니다 \n금형 프로젝트 일정을 조정 하시기 바랍니다--%>');"
                                                        class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171")%><%--완료--%></a>
                                                        <%
                                                            } else {
                                                        %> <a href="javascript:changeHistory('<%=oid%>', 'complete');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02171")%><%--완료--%></a>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%
                                            }
                                        %>
                                        <%
                                            }
                                        %>
                                        <!-- [닫기] 버튼 : 일정변경사유 입력 팝업 닫음 -->
                                        <td>&nbsp;</td>
                                        <td>
                                            <table>
                                                <tr>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                                                    </td>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <!-- [END] [PLM System 1차개선] 일정변경사유 입력 화면 버튼 수정, 2013-06-24, BoLee -->
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table> <!-------------------------------------- 상단 제목 버튼 끝 //-------------------------------------->
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <!------------------------------ 본문 Start //------------------------------>
                                <table>
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table> 
                                <%
                                     if (mode.length() == 0) {
                                %>
                                <table style="width: 100%; table-layout: fixed;">
                                    <tr>
                                        <td class="tdblueL" style="width: 100px"><%=messageService.getString("e3ps.message.ket_message", "02358")%><%--일정변경구분--%></td>
                                        <td class="tdwhiteL0">
                                            <%
                                                int cnt = 0;
                                        		for (int i = 0; i < historyChangeNumCode.size(); i++) {
                                        		    if (!historyChangeNumCode.get(i).get("code").toString().equals("0") && !historyChangeNumCode.get(i).get("code").toString().equals("5")) {
                                            %> 
                                            <input id="historyNoteType" name="historyNoteType" type="radio" class="Checkbox" value="<%=historyChangeNumCode.get(i).get("code")%>"
                                            <%if (historyNoteType.contains(historyChangeNumCode.get(i).get("code").toString())) {%>
                                            checked <%}%> onclick="javascript:historyTypeChange(this.value)"><%=historyChangeNumCode.get(i).get("value")%>
                                            <%
                                                    }
                                        		    if (cnt != 0 && cnt % 5 == 0) {
                                            %> <br> <%
                                                    }
                                         		    cnt++;
                                         		}
                                             %>
                                            <table>
                                                <tr>
                                                    <td>
                                                        <input id="historyNoteType" name="historyNoteType" type="radio" class="Checkbox" value="<%=historyChangeNumCode.get(historyChangeNumCode.size() - 1).get("code")%>"
                                                        <%if (historyNoteType.contains(historyChangeNumCode.get(historyChangeNumCode.size() - 1).get("code").toString())) {%>
                                                        checked <%}%> onclick="javascript:historyTypeChange(this.value)"><%=historyChangeNumCode.get(historyChangeNumCode.size() - 1).get("value")%>
                                                    </td>
                                                    <td><span id="etcDetail" style="display: none;">(<%=messageService.getString("e3ps.message.ket_message", "03397")%><%--사유필수입력--%>)
                                                    </span></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <!-- [START] [PLM System 1차개선] 단계 일정 변경 시 [합의자 지정] 영역 표시함, 2013-08-05, BoLee -->
                                    <%
                                        if (StringUtil.checkNull(isApproval).startsWith("T")) { // 일정 변경 범위가 결재 대상인 경우 합의자 지정 영역 표시
                                    %>
                                    <tr height="40px;">
                                        <td class="tdwhiteL0" colspan="2">
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td width="20">
                                                        <div id="hisRoleShow" style="display: none">
                                                            <a href="javascript:hisRole('s');" class="btn_blue"><img src="/plm/portal/images/btn_open_s.gif" /></a>
                                                        </div>
                                                        <div id="hisRoleHide" style="display: none">
                                                            <a href="javascript:hisRole('h');" class="btn_blue"><img src="/plm/portal/images/btn_open.gif" /></a>
                                                        </div>
                                                    </td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03164")%><%--합의자 지정--%></td>
                                                    <td style="width: 550px;" align="right">&nbsp;</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr id="trHisRole">
                                        <td colspan="2">
                                            <table style="width: 100%;">
                                                <COL width="130px">
                                                <COL>
                                                <COL width="130px">
                                                <COL>
                                                <COL width="130px">
                                                <COL>
                                                <tr>
                                                    <td class="tdblueM">Role</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                                    <td class="tdblueM">Role</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                                    <td class="tdblueM">Role</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                                </tr>
                                                <%
                                                    if (vecTeamStd != null && vecTeamStd.size() > 0) {
                                                			Collections.sort(vecTeamStd, new RoleComparator(true, true));
                                                			Role role = null;
                                                			String roleName_en = null;
                                                			String roleName_ko = null;
                                                			WTUser wtuser = null;

                                                			int roleIndex = 0;
                                                			int colspan = 1;

                                                			for (int i = vecTeamStd.size() - 1; i > -1; i--) {
                                                			    role = (Role) vecTeamStd.get(i);
                                                			    roleName_en = role.toString();
                                                			    roleName_ko = role.getDisplay(Locale.KOREA);
                                                			    wtuser = (WTUser) roleUser.get(roleName_en);
                                                			    if (i == 0 && roleIndex % 2 == 0) {
                                                				//colspan = 3;
                                                			    }

                                                			    if (roleIndex % 3 == 0) {
                                                				out.println("<tr>");
                                                			    }
                                                %>
                                                <td class="tdblueL" style="text-align: left;">
                                                    <input type="checkbox" name="historyRole" value="<%=roleName_en%>" <%if (historyRoleCondList.contains(roleName_en)) {%> checked
                                                    <%}%> <%if (wtuser == null) {%> disabled <%}%> />&nbsp;<%=roleName_ko%></td>
                                                <td class="tdwhiteM" colspan="<%=colspan%>"><%=wtuser == null ? "" : wtuser.getFullName()%> 
                                                    <input type="hidden" name="temp<%=roleName_en%>"
                                                    value="<%=wtuser == null ? "" : wtuser.getFullName()%>"> <input type="hidden"
                                                    name="<%=roleName_en%>" value="<%=wtuser == null ? "" : CommonUtil.getOIDString(wtuser)%>">
                                                </td>
                                                <%
                                                    if (i == 0) {
                                                				if (roleIndex % 3 == 0) { //2명
                                                %>
                                                <td class="tdwhiteM" colspan="4">&nbsp;</td>
                                                <%
                                                    } else if (roleIndex % 3 == 1) { // 1명
                                                %>
                                                <td class="tdwhiteM" colspan="2">&nbsp;</td>
                                                <td class="tdwhiteM">&nbsp;</td>
                                                <%
                                                    }
                                                			    }

                                                			    if (roleIndex % 3 == 2 || i == 0) {
                                                				out.println("</tr>");
                                                			    }

                                                			    roleIndex++;
                                                			}
                                                		    }
                                                %>
                                            </table>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                    <!-- [END] [PLM System 1차개선] 단계 일정 변경 시 [합의자 지정] 영역 표시함, 2013-08-05, BoLee -->
                                    <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                                        <td class="tdwhiteM0">
                                            <table style="width: 100%;" class="table_border">
                                                <tr>
                                                    <td style="width: 40px;" class="tdgrayM">
                                                        <a href="#" onclick="javascript:insertFileLine();return false;">
                                                        <img src="/plm/portal/images/b-plus.png"></a></td>
                                                    <td class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                                </tr>
                                                <tbody id="secondaryTable" />
                                                <%
                                                if (project != null) {
                                        		    ContentHolder contentHolder = ContentHelper.service.getContents(project);
                                        		    Vector secondaryFiles = ContentUtil.getSecondaryContents(contentHolder);

                                        		    if (secondaryFiles.size() > 0) {
                                            			for (int i = 0; i < secondaryFiles.size(); i++) {
                                            			    ContentInfo info = (ContentInfo) secondaryFiles.elementAt(i);

                                            			    String iconpath = "";
                                            			    String urlpath = "";
                                            			    if (info == null) {
                                                				iconpath = "";
                                                				urlpath = "";
                                            			    } else {
                                                				iconpath = info.getIconURLStr();
                                                				urlpath = info.getDownURLStr();
                                            			    }
                                                %>
                                                <tr>
                                                    <td style="width: 40px" class="tdwhiteM">
                                                        <a href="#" onclick="javascript:$(this).parent().parent().remove();deleteFile('<%=info.getContentOid()%>');"><img src="/plm/portal/images/b-minus.png"></a></td>
                                                    <td class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                                                </tr>
                                                <%
                                                         }
                                        		    }
                                        		}
                                                %>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <textarea name="historyNote" rows="0" cols="0" style="display: none"></textarea> 
                                            <textarea name="historyNoteWebEditorText" rows="0" cols="0" style="display: none"></textarea> <!-- Editor Area Container : Start -->
                                            <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
                                        </td>
                                    </tr>
                                </table> 
                                <%
                                     } else {
                                %>
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="tdblueL" style="width: 100px"><%=messageService.getString("e3ps.message.ket_message", "02358")%><%--일정변경구분--%></td>
                                        <td class="tdwhiteL">
                                            <%
                                                for (int i = 0; i < historyChangeNumCode.size(); i++) {
                                            %> <%=(historyNoteType.contains(historyChangeNumCode.get(i).get("code").toString())) ? historyChangeNumCode.get(i).get("value").toString() : ""%>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <td class="tdblueL" style="width: 128px">PM</td>
                                        <td class="tdwhiteL0" style="width: 232px"><%=(data.pjtPm == null) ? "" : data.pjtPm.getFullName()%>&nbsp;</td>
                                    </tr>
                                    <!-- [START] [PLM System 1차개선] 합의자 Role이 존재할 경우 [합의자 지정] 영역 표시함, 2013-08-05, BoLee -->
                                    <%-- <%
                                        if (historyRoleCondList != null && historyRoleCondList.size() > 0) {
                                    %>
                                    <tr height="40px;">
                                        <td class="tdwhiteL0" colspan="4">
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td width="20">
                                                        <div id="hisRoleShow" style="display: none">
                                                            <a href="javascript:hisRole('s');" class="btn_blue"><img
                                                                src="/plm/portal/images/btn_open_s.gif" /></a>
                                                        </div>
                                                        <div id="hisRoleHide" style="display: none">
                                                            <a href="javascript:hisRole('h');" class="btn_blue"><img
                                                                src="/plm/portal/images/btn_open.gif" /></a>
                                                        </div>
                                                    </td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03164")%>합의자 지정</td>
                                                    <td style="width: 450px;" align="right">&nbsp;</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr id="trHisRole">
                                        <td colspan="4">
                                            <table style="width: 100%;">
                                                <COL width="130px">
                                                <COL>
                                                <COL width="130px">
                                                <COL>
                                                <COL width="130px">
                                                <COL>
                                                <tr>
                                                    <td class="tdblueM">Role</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %>이름</td>
                                                    <td class="tdblueM">Role</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %>이름</td>
                                                    <td class="tdblueM">Role</td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %>이름</td>
                                                </tr>
                                                <%
                                                    if ( vecTeamStd != null && vecTeamStd.size() > 0 ) {
                                                        Collections.sort(vecTeamStd, new RoleComparator(true,true));
                                                        Role role = null;
                                                        String roleName_en = null;
                                                        String roleName_ko = null;
                                                        WTUser wtuser = null;
                        
                                                        int roleIndex = 0;
                                                        int colspan = 1;
                        
                                                        for (int i = vecTeamStd.size() - 1; i > -1; i--) {
                                                            role = (Role) vecTeamStd.get(i);
                                                            roleName_en = role.toString();
                                                            roleName_ko = role.getDisplay(Locale.KOREA);
                                                            wtuser = (WTUser)roleUser.get(roleName_en);
                                                            if(i == 0 && roleIndex%2 == 0){
                                                                //colspan = 3;
                                                            }
                        
                                                            if(roleIndex%3 == 0) {
                                                                out.println("<tr>");
                                                            }
                                                %>
                                                <td class="tdblueL" style="text-align: left;"><input type="checkbox" name="historyRole"
                                                    value="<%=roleName_en%>" <%if ( historyRoleCondList.contains( roleName_en ) ){%> checked
                                                    <%}%> disabled />&nbsp;<%=roleName_ko%></td>
                                                <td class="tdwhiteM" colspan="<%=colspan%>"><%=wtuser==null?"":wtuser.getFullName()%> <input
                                                    type="hidden" name="temp<%=roleName_en%>"
                                                    value="<%=wtuser==null?"":wtuser.getFullName()%>"> <input type="hidden"
                                                    name="<%=roleName_en%>" value="<%=wtuser==null?"":CommonUtil.getOIDString(wtuser)%>">
                                                </td>
                                                <%
                                                            if(i == 0){
                                                                if(roleIndex%3 == 0) { //2명
                                                                    %>
                                                                        <td class="tdwhiteM" colspan="4">&nbsp;</td>
                                                                        <%
                                                                }else if(roleIndex%3 == 1) { // 1명
                                                                    %>
                                                                        <td class="tdwhiteM" colspan="2">&nbsp;</td>
                                                                        <%
                                                                }
                                                            }
                        
                                                            if(roleIndex%3 == 2 || i == 0) {
                                                                out.println("</tr>");
                                                            }
                        
                                                            roleIndex++;
                                                        }
                                                    }
                                                %>
                                            </table>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %> --%>
                                    <!-- [END] [PLM System 1차개선] 합의자 Role이 존재할 경우 [합의자 지정] 영역 표시함, 2013-08-05, BoLee -->
                                    <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                        <td class="tdwhiteM0" colspan="4">
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table class="table_border" style="width: 100%;">
                                                <tr>
                                                    <td class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                                                </tr>
                                                <tbody id="secondaryTable" />
                                                <%
                                                    if ( project != null ) {
                                                        ContentHolder contentHolder = ContentHelper.service.getContents(project);
                                                        Vector secondaryFiles = ContentUtil.getSecondaryContents(contentHolder, "historyChange");
                        
                                                        if ( secondaryFiles.size() > 0 ) {
                                                            for ( int i=0; i<secondaryFiles.size(); i++ ) {
                                                                ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);
                        
                                                                String iconpath = "";
                                                                String urlpath = "";
                                                                if ( info == null ) {
                                                                    iconpath = "";
                                                                    urlpath = "";
                                                                }
                                                                else {
                                                                    iconpath = info.getIconURLStr();
                                                                    urlpath = info.getDownURLStr();
                                                                }
                                                %>
                                                <tr>
                                                    <td style="width: 520px" class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                                                </tr>
                                                <%
                                                            }
                                                        }
                                                    }
                                                %>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdwhiteL0" colspan="4">
                                            <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                                            <div id="divView" style="width: 100%;" class="outline"></div> <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                                        </td>
                                    </tr>
                                </table> <%} %>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
