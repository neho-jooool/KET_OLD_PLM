<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.project.historyprocess.HistoryHelper"%>
<%@page import="e3ps.project.CheckoutLink"%>
<%@page import="e3ps.project.ProjectOemTypeLink"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Hashtable,
                java.util.Vector,
                java.util.StringTokenizer"%>

<%@page import="wt.content.ContentHelper,
                wt.content.ContentHolder,
                wt.content.ContentRoleType,
                wt.fc.PersistenceHelper,
                wt.session.SessionHelper,
                wt.query.QuerySpec,
                wt.fc.QueryResult,
                wt.query.SearchCondition,
                wt.content.ContentItem,
                e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.common.content.ContentInfo,
                e3ps.common.content.ContentUtil,
                e3ps.common.content.E3PSContentHelper,
                e3ps.common.content.uploader.WBFile,
                e3ps.common.util.DateUtil,
                e3ps.common.util.CharUtil,
                e3ps.common.content.fileuploader.FormUploader,
                e3ps.common.content.uploader.FileUploader,
                e3ps.common.util.CommonUtil,
                e3ps.project.cancel.ProjectCancelLink,
                e3ps.project.cancel.CancelProject,
                e3ps.project.cancel.cancelHistoryLink,
                e3ps.project.cancel.CancelCostHistory,
                e3ps.project.MoldProject,
                e3ps.project.ProductProject,
                e3ps.project.ReviewProject,
                e3ps.project.E3PSProject,
                e3ps.project.historyprocess.HistoryHelper,
                e3ps.project.ProjectOemTypeLink
                "%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PROJECTCANCELTYPE");
    List<Map<String, Object>> cancelReasonTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    String oid = request.getParameter("oid");
    String command = request.getParameter("command");

    String pjtType = "";
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    String State = project.getLifeCycleState().toString();
    QuerySpec qs = null;
    QueryResult qr = null;
    CancelProject cpModify = null;
    ProjectCancelLink ps = null;
    String pcsOid = null;
    boolean DelIs = true;
    String targetCost = "";
    try{
      long idLong = CommonUtil.getOIDLongValue(project.getMaster());
      qs = new QuerySpec();
      int idx = qs.addClassList(ProjectCancelLink.class, true);
      qs.appendWhere(new SearchCondition(ProjectCancelLink.class, "roleBObjectRef.key.id", "=", idLong), new int[] {idx});
      SearchUtil.setOrderBy(qs, ProjectCancelLink.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", true);
      qr = PersistenceHelper.manager.find(qs);
      
      if (qr != null || qr.size() != 0) {
	      Object[] obj = null;
	      while (qr.hasMoreElements()) {
			  obj = (Object[]) qr.nextElement();
			  ps = (ProjectCancelLink) obj[0];
			  cpModify = ps.getCancle();
			  pcsOid = CommonUtil.getOIDString(cpModify);
			  targetCost = cpModify.getTargetCost();
	      }
      }
      if(cpModify != null){
	      if("취소중".equals(cpModify.getCancelType()) || "CANCELING".equals(State) ){
		      DelIs = false;
	      }
      }
    }catch(Exception e){
    }
    
    
    ReviewProject rp = null;
    ProductProject pp = null;
    MoldProject mp = null;
    if ( project instanceof ReviewProject ) {
        pjtType = "review";
        rp = (ReviewProject)project;
    }
    else if( project instanceof ProductProject ) {
        pjtType = "product";
        pp = (ProductProject)project;
    }
    else if ( project instanceof MoldProject ) {
        pjtType = "mold";
        mp = (MoldProject)project;
    }

    String contentType = request.getContentType();
    FileUploader uploader = null;

    FormUploader fileUploader = null;
    String stopDetil = "";
    Object webEditor = "";
    Object webEditorText = "";
    String cancelReasonType = "";
    
    if(cpModify != null){
	   cancelReasonType = cpModify.getCancelReasonType();
	   webEditor = cpModify.getWebEditor();
	   webEditorText = cpModify.getWebEditorText();
	}
    Hashtable param = null;
    if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 ) {
        fileUploader = e3ps.common.content.fileuploader.FormUploader.newFormUploader(request);
        param = fileUploader.getFormParameters();

        command = (String)param.get("command");
        stopDetil = (String)param.get("stopDetil");
        oid = (String)param.get("oid");
        cancelReasonType = (String) param.get("cancelReasonType");
    }
    else {
        command = CharUtil.E2K(request.getParameter("command"));
        oid = CharUtil.E2K(request.getParameter("oid"));
    }
    
    

    if ( command.equals("create") ) {
        //project.setStopedDetil(stopDetil);
        
        //QueryResult oemTypeQr = PersistenceHelper.manager.navigate(project, ProjectOemTypeLink.OEM_PJT_TYPE_ROLE, ProjectOemTypeLink.class, false);
        // 일정변경 상태 변경 (체크아웃, Working Copy 생성) - 일정변경사유는 저장하지 않음
        CheckoutLink checkoutLink = (CheckoutLink) HistoryHelper.checkOut(project, "", 999, "CANCELING");

        // 일정변경 상태 변경 후 화면 이동

        if (checkoutLink != null) {

	        String copyOid = CommonUtil.getOIDString(checkoutLink.getWorkingCopy());
	        oid = copyOid;
	        project = (E3PSProject) CommonUtil.getObject(copyOid);
	        
	        // 파생차종 복사 .. 사용안함
/* 	        while (oemTypeQr.hasMoreElements()) {
	            ProjectOemTypeLink link = (ProjectOemTypeLink) oemTypeQr.nextElement();
	            ProjectOemTypeLink copyLink = ProjectOemTypeLink.newProjectOemTypeLink(link.getOemPjtType(), project);
	            PersistenceHelper.manager.save(copyLink);
	        } */
        }
        
        CancelProject cp = CancelProject.newCancelProject();
        cp.setCancelType("취소요청");
        cp.setCancelHistory(stopDetil);
        cp.setCancelReasonType(cancelReasonType);

        cp.setWebEditor( (String)param.get("webEditor") );
        cp.setWebEditorText( (String)param.get("webEditorText") );

        cp.setCancelDate(DateUtil.getCurrentTimestamp());
        cp.setOwner(SessionHelper.manager.getPrincipalReference());
        cp.setTargetCost(StringUtils.remove((String)param.get("targetCost"), ","));
        cp = (CancelProject)PersistenceHelper.manager.save(cp);

        if ( cp != null ) {
          ProjectCancelLink link = ProjectCancelLink.newProjectCancelLink(cp, project.getMaster());
          PersistenceHelper.manager.save(link);
        }

        Vector files = fileUploader.getFiles();
        if ( files != null ) {
            for(int i = 0; i < files.size(); i++) {
              cp = (CancelProject)E3PSContentHelper.service.attach(cp, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
            }
        }
    }else if(command.equals("Modify") ){
	    cpModify.setTargetCost(StringUtils.remove((String)param.get("targetCost"), ","));
		cpModify.setCancelHistory(stopDetil);
		cpModify.setCancelReasonType(cancelReasonType);
		cpModify.setCancelType("취소요청");
		cpModify.setWebEditor( (String)param.get("webEditor")  );
		cpModify.setWebEditorText( (String)param.get("webEditorText")  );
	
		cpModify.setCancelDate(DateUtil.getCurrentTimestamp());
		cpModify.setOwner(SessionHelper.manager.getPrincipalReference());
		cpModify = (CancelProject) PersistenceHelper.manager.modify(cpModify);
		
		Vector files = fileUploader.getFiles();
        if ( files != null ) {
            for(int i = 0; i < files.size(); i++) {
        	   cpModify = (CancelProject)E3PSContentHelper.service.attach(cpModify, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
            }
        }
        String sfiles = (String)param.get("isFileDel");
        
        if(!"".equals(sfiles) && sfiles!=null){
	        StringTokenizer st = new StringTokenizer(sfiles, "/");
	        while (st.hasMoreTokens()) {
	
	            String delsFile = st.nextToken();
	            ContentItem ctf = (ContentItem) CommonUtil.getObject(delsFile);
	            ContentInfo info = ContentUtil.getContentInfo((ContentHolder) cpModify, ctf);
	            String delsFileName = info.getName();
	
	            QueryResult rs = ContentHelper.service.getContentsByRole(cpModify, ContentRoleType.SECONDARY);
	
	            while (rs.hasMoreElements()) {
	                ContentItem ctf1 = (ContentItem) rs.nextElement();
	                ContentInfo info1 = ContentUtil.getContentInfo((ContentHolder) cpModify, ctf1);
	
	                String itemStr = info1.getName();
	                if (itemStr.equals(delsFileName)) {
	                    cpModify = (CancelProject) E3PSContentHelper.service.delete(cpModify, ctf1);
	                }
	            }
	        }
        }
    }
%>
<script>
<%    
    if ( command.equals("create") || command.equals("Modify")) {
	   E3PSProject newProject = (E3PSProject)CommonUtil.getObject(oid);
	   newProject = ProjectHelper.getLastProject(newProject.getMaster());
	   oid = CommonUtil.getOIDString(newProject);
%>
    alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>');
    <%if ( "review".equals(pjtType) ) {%>
        opener.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&command=cancelStart";
    <%}
      else if ( "product".equals(pjtType) ) {%>
        opener.location.href = "/plm/jsp/project/ProjectExtView.jsp?oid=<%=oid%>&command=cancelStart";
    <%}
      else if( "mold".equals(pjtType) ) {%>
          opener.location.href = "/plm/jsp/project/MoldProjectView_2.jsp?oid=<%=oid%>&command=cancelStart";
    <%}%>
    self.close();
<%
    }else if(command.equals("Delete")){
	   if(!DelIs){
%>	       
	       alert("취소결재가 진행중입니다.");
<%	       
	   }else{
	       String sfiles = (String)param.get("isFileDel");
	        
	        if(!"".equals(sfiles) && sfiles!=null){
	            StringTokenizer st = new StringTokenizer(sfiles, "/");
	            while (st.hasMoreTokens()) {
	    
	                String delsFile = st.nextToken();
	                ContentItem ctf = (ContentItem) CommonUtil.getObject(delsFile);
	                ContentInfo info = ContentUtil.getContentInfo((ContentHolder) cpModify, ctf);
	                String delsFileName = info.getName();
	    
	                QueryResult rs = ContentHelper.service.getContentsByRole(cpModify, ContentRoleType.SECONDARY);
	    
	                while (rs.hasMoreElements()) {
	                    ContentItem ctf1 = (ContentItem) rs.nextElement();
	                    ContentInfo info1 = ContentUtil.getContentInfo((ContentHolder) cpModify, ctf1);
	    
	                    String itemStr = info1.getName();
	                    if (itemStr.equals(delsFileName)) {
	                        cpModify = (CancelProject) E3PSContentHelper.service.delete(cpModify, ctf1);
	                    }
	                }
	            }
	        }
	       PersistenceHelper.manager.delete(ps);
	       //PersistenceHelper.manager.delete(cpModify);
%>         
           alert('<%=messageService.getString("e3ps.message.ket_message", "01692") %><%--삭제되었습니다--%>');
<%
	   }
%>
	 <%if ( "review".equals(pjtType) ) {%>
	    opener.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>";
	 <%}
	 else if ( "product".equals(pjtType) ) {%>
	    opener.location.href = "/plm/jsp/project/ProjectExtView.jsp?oid=<%=oid%>";
	 <%}
	 else if( "mold".equals(pjtType) ) {%>
	    opener.location.href = "/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>";
	 <%}%>
	 self.close();
<%}%>
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "03123") %><%--프로젝트취소--%></title>
<base target="_self">

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<script src="/plm/portal/js/script.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/select.js"></script>
<script src="/plm/portal/js/table.js"></script>
<script src="/plm/portal/js/viewObject.js"></script>
<script src="/plm/portal/js/ajax.js"></script>
<script src="/plm/portal/js/checkbox2.js"></script>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

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
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
</style>

<script type="text/javascript">

    // 첨부파일 시작 *****************************************************************************************************************
    function insertFileLine() {
        var tBody = document.getElementById("secondaryTable");
        var innerRow = tBody.insertRow();
        //var innerCell = innerRow.insertCell();
        var filePath = "secondaryFile"+tBody.rows.length;

        //innerCell.innerHTML = "<input name='secondarySelect' type='checkbox' class='Checkbox'>";
        //innerCell.innerHTML += "<input name='"+filePath+"' type='file' class='txt_field' style='width:95%;'>";

        //newTd = innerRow.insertCell();//delete
        //newTd.className = "tdwhiteM";
        //newTd.innerHTML = "<a href='#' onclick=''>-";
        
        newTd = innerRow.insertCell();//delete
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input name='secondarySelect' type='checkbox'>";

        newTd = innerRow.insertCell();//file
        newTd.className = "tdwhiteL0";
        //newTd.colSpan = 2;
        newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:550px;'>";
    }

    function deleteFileLine(obj) {
        var body = document.getElementById("secondaryTable");
        if (body.rows.length == 0) return;
        var file_checks = document.forms[0].secondarySelect;
        if (body.rows.length == 1) {
        	deleteFile(document.getElementsByName("contentOid")[0].value);
            body.deleteRow(0);
        } else {
            for (var i = body.rows.length; i > 0; i--) {
                if (file_checks[i-1].checked){
                	deleteFile(document.getElementsByName("contentOid")[i-1].value);
                	body.deleteRow(i - 1);
                }
            }
        }
    }
    
    function deleteFile(contentoid){
        document.forms[0].isFileDel.value += "/" + contentoid;
    }
    
    
    function deleteFileAll() {
        var body = document.getElementById("secondaryTable");
        if (body.rows.length == 0) return;
        
        for (var i = body.rows.length; i > 0; i--) {
            deleteFile(document.getElementsByName("contentOid")[i-1].value);
        }
        
    }
    
    function doDelete(){
    	if ( !confirm('취소 요청을 삭제하시겠습니까?') ) {
            return;
        }
    	document.forms[0].isFileDel.value = "";
    	deleteFileAll();
    	
    	document.forms[0].command.value = 'Delete';
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
    
    // 첨부 파일 끝 *****************************************************************************************************************

    function doCreateJsp(){
        if ( !checkValidate() ) {
            return;
        }
        if ( !confirm('<%=messageService.getString("e3ps.message.ket_message", "03116") %><%--프로젝트를 취소 하시겠습니까?--%>') ) {
            return;
        }

        document.forms[0].stopDetil.value = "";
        // innoditor WebEditor
        // 첫번째 매개변수 => true : < & 특수문자 처리,  false : 처리안함
        // 두번째 매개변수 => 이노디터 번호
        document.forms[0].webEditor.value = fnGetEditorHTMLCode(false, 0);
        document.forms[0].webEditorText.value = fnGetEditorText(0);
        <%
        if("CANCELING".equals(State) && cpModify!= null){
            command = "Modify";
        }else{
            command = "create";
        }
        %>
        document.forms[0].command.value = '<%=command%>';
        
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

    function checkValidate(){
    	
        if ( getCheckedValue(document.forms[0].cancelReasonType) == "" ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02889") %><%--취소 구분을 선택 하십시오--%>');
            return false;
        }
        
        if(document.forms[0].targetCost.value == ""){
            alert("목표 회수비를 입력하여주십시오");
            document.forms[0].targetCost.focus();
            return false;
        }
        
/*         if(!onlyNumber(document.forms[0].targetCost.value)){
            alert("목표 회수비는 숫자를 입력하셔야합니다.");
            document.forms[0].targetCost.focus();
            return false;
        } */

        var strHTMLCode = fnGetEditorHTMLCode(false, 0);
        if ( strHTMLCode == "" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02891") %><%--취소 사유를 입력 하십시오--%>');
            return false;
        }
        return true;
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
    
    function onlyNumber(num){
        num = String(num).replace(/^\s+|\s+$/g, "");
        var regex = /^[-]?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+){1}(\.[0-9]+)?$/g;
        
        if( regex.test(num) ){
            num = num.replace(/,/g, "");

            return isNaN(num) ? false : true;

        }else{ return false;  }
    }


    
    $(document).ready(function() {
        var strHTMLCode = document.forms[0].webEditor.value;
        // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
        // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
        // 세번째 매개변수 => 이노디터 번호
        fnSetEditorHTMLCode(strHTMLCode, false, 0);
        
        $('#targetCost').number( true );
        
    });
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
g_nEditorWidth = 751;
g_nEditorHeight = 390;

//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<script type="text/javascript">
//<![CDATA[
function fnSetEditorHTML() {
    var strHTMLCode = document.frm["webEditor"].value;
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
<!-- 이노디터 JS Include End -->
</head>

<body>
    <!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
    <form name = "frm" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="oid" value="<%=oid%>"> 
        <input type="hidden" name="command">
        <input type="hidden" name="isFileDel" value="">
        <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
        <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor%></textarea>

        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
        <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage" value="" /> <input
            type="hidden" name="hdnBackgroundRepeat" value="" />
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
                                                    <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03123") %><%--프로젝트취소--%></td>
                                                    <td style="width: 10px;"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                                    </tr>
                                </table>
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="space5"></td>
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
                                        <td>&nbsp;</td>
                                        <td>
                                            <table>
                                                <tr>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="Javascript:doCreateJsp();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                        <%if(cpModify != null){ %>
					                    <table border="0" cellspacing="0" cellpadding="0">
					                        <tr>
					                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
					                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
					                                href="javascript:doDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><!-- 삭제 --></a></td>
					                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
					                        </tr>
					                    </table>
					                    </td>
					                    <% }%>
                                        <td>
                                            <table>
                                                <tr>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="Javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
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
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="tdblueL" style="width: 120px"><%=messageService.getString("e3ps.message.ket_message", "02888") %><%--취소 구분--%></td>
                                        <td class="tdwhiteL0" style="width: 600px">
                                            <%
                        for ( int i=0; i<cancelReasonTypeNumCode.size(); i++ ) {
                        %> <input id="cancelReasonType" name="cancelReasonType" type="radio" class="Checkbox"
                                            value="<%=cancelReasonTypeNumCode.get(i).get("code") %>"
                                            <%if ( cancelReasonType.contains( cancelReasonTypeNumCode.get(i).get("code").toString() ) ){%>
                                            checked <%}%>><%=cancelReasonTypeNumCode.get(i).get("value")%> <%
                        }
                        %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL" style="width: 120px">목표 회수비
                                            <font color="red">*</font></td>
                                        <td class="tdwhiteL0"><input type="text" class="txt_field" style="width: 30%" value="<%=targetCost%>" name="targetCost" id="targetCost"/></td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL" style="width: 120px"><%=messageService.getString("e3ps.message.ket_message", "02895") %><%--취소일--%>
                                            <font color="red">*</font></td>
                                        <td class="tdwhiteL0"><%=DateUtil.getToDay() %></td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL" style="width: 120px"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                                        <td class="tdwhiteM0" style="padding: 5px">
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td>
                                                                    <table>
                                                                        <tr>
                                                                            <td style="width: 10px;"><img
                                                                                src="/plm/portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                                                <a href="#" onclick="javascript:insertFileLine();"
                                                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><!-- 추가 --></a>
                                                                            </td>
                                                                            <td style="width: 10px;"><img
                                                                                src="/plm/portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <td width="5">&nbsp;</td>
                                                                <td>
                                                                    <table>
                                                                        <tr>
                                                                            <td style="width: 10px;"><img
                                                                                src="/plm/portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                                                <a href="#" onclick="javascript:deleteFileLine();"
                                                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><!-- 삭제 --></a>
                                                                            </td>
                                                                            <td style="width: 10px;"><img
                                                                                src="/plm/portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td align="right">&nbsp;</td>
                                                </tr>
                                            </table>
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table style="width: 100%;" class="table_border">
                                                <tr>
                                                    <!-- <td style="width: 40px" class="tdgrayM"><a href="#" onclick="insertFileLine()">+</a></td> -->
                                                    <td style="width: 40px"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                                                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                                                </tr>
                                                <tbody id="secondaryTable" />
                                                <%
                            if ( cpModify != null ) {
                                ContentHolder contentHolder = ContentHelper.service.getContents(cpModify);
                                Vector secondaryFiles = ContentUtil.getSecondaryContents(contentHolder);

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
                                                    <td width="40" class="tdwhiteM"><input type="checkbox" name="secondarySelect" id="checkbox" value="<%=info.getContentOid()%>"></td>
                                                    <td width="450" class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                                                    <input type="hidden" name="contentOid" value="<%=info.getContentOid()%>">
                                                </tr>
                                                <%
                                    }
                                }
                            }
                        %>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdwhiteL0" colspan="2">
                                        <textarea name="stopDetil" rows="0" cols="0" style="display: none"></textarea> 
                                        <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=webEditorText%></textarea> <!-- Editor Area Container : Start -->
                                        <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End --></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
