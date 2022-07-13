<%@page import="e3ps.dms.beans.DMSUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.dms.service.KETDmsHelper,
                        e3ps.dms.entity.*,
                        wt.fc.QueryResult,
                        wt.fc.PersistenceHelper,
                        wt.doc.WTDocument,
                        wt.doc.WTDocumentMaster,
                        wt.content.*,
                        wt.lifecycle.LifeCycleHelper,
                        wt.lifecycle.LifeCycleManaged,
                        e3ps.common.content.*,
                        java.util.Vector,
                        java.util.StringTokenizer,
                        java.util.ArrayList,
                        org.apache.commons.io.FilenameUtils,
	        			e3ps.common.content.ContentUtil,
                        e3ps.common.util.CommonUtil,
                        e3ps.common.util.StringUtil,
                        e3ps.groupware.company.*,
                        e3ps.common.util.DateUtil,
                        org.apache.commons.lang.StringUtils"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />


<%
    //검색조건
    String SdocumentNo = StringUtil.checkNull(request.getParameter("documentNo"));
    String SdivisionCode = StringUtil.checkNull(request.getParameter("divisionCode"));
    String ScategoryCode = StringUtil.checkNull(request.getParameter("categoryCode"));
    String SdocumentName = StringUtil.checkNull(request.getParameter("documentName"));
    String SauthorStatus = StringUtil.checkNull(request.getParameter("authorStatus"));
    String ScreatorName = StringUtil.checkNull(request.getParameter("creatorName"));
    String Spredate = StringUtil.checkNull(request.getParameter("predate"));
    String Spostdate = StringUtil.checkNull(request.getParameter("postdate"));
    String Sislastversion = StringUtil.checkNull(request.getParameter("islastversion"));
    String SsortKey = StringUtil.checkNull(request.getParameter("sortKey"));
    String SsortKeyD = StringUtil.checkNull(request.getParameter("sortKeyD"));
    String SpageCnt = StringUtil.checkNull(request.getParameter("pageCnt"));
    String Spage = StringUtil.checkNull(request.getParameter("page"));
    String SnowBlock = StringUtil.checkNull(request.getParameter("nowBlock"));
    String isSer = StringUtil.checkNull(request.getParameter("isSer"));
    if ( (isSer == null) || (isSer.trim().length() == 0) ) {
        isSer = "F";
    }

    String oid =  request.getParameter("oid");
    String cmd =  request.getParameter("cmd");
    String isPop =  request.getParameter("isPop");
    // 2차 고도화 TKLEE 수정
    isPop = "Y";
    String buttenView = request.getParameter("buttenView");
    if ( (buttenView == null) || (buttenView.trim().length() == 0) ) {
        buttenView = "F";
    }
    isPop = StringUtil.checkNull(isPop);
    String categoryCode0 = "0";
    String categoryCode1 = "0";
    String categoryCode2 = "0";
    String categoryCode3 = "0";
    String categoryCode = "0";
    String categoryCodeTxt = "";
    String categoryName = null;
    String docCatePath = null;
    String docTypeCode = null;

    String tmpStr = null;

    String documentNo = null;
    String documentName = null;
    String divisionCode = null;
    String versionInfo = null;
    String iterationInfo = null;
    String deptName = null;

    Integer tmpInt = 0;
    String documentDescription = null;
    String urlpath="";
    String iconpath="";
    String appDataOid="";
    String insUser = "who";

    // Web Editor
    Object webEditor = null;
    Object webEditorText = null;

    KETTechnicalDocument docu = null;
    Object[] obj = null;
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> categoryList = null;
    boolean isEditable = true;

    if ( oid != null ) {
	    Kogger.debug("UpdateTechDocument", null, oid, "update revice oid ===>" + oid);
        if ( oid.equals("0000") ) {
            docCatePath = "";
            documentName = "";
            divisionCode = "";
            deptName = "";
            documentDescription = "";
        }
        else{
            //KETTechnicalDocument의 oid로 해당 객체의 정보를 화면에 나타낸다.
            docu = (KETTechnicalDocument)CommonUtil.getObject(oid);
            documentNo =  docu.getNumber();
            versionInfo = docu.getVersionIdentifier().getValue();
            iterationInfo = docu.getIterationIdentifier().getValue();
            //divisionCode =  docu.getDivisionCode();
            KETDocumentCategory docCate = null;

            // 1 Level 분류체계 찾기
            parameter.clear();
            parameter.put("docTypeCode", "TD");
            parameter.put("locale",      messageService.getLocale().toString());
            parameter.put("parentCode",  "ROOT");

            categoryList = DMSUtil.getDocumentCategory(parameter);
            if ( categoryList.size() > 0 ) {
                categoryCode0 = categoryList.get(0).get("categoryCode").toString();
                categoryName = categoryList.get(0).get("categoryName").toString();
            }

            //KETTechnicalDocument의 해당 마스터객체로 링크된 분류체계를 검색한다.
            QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETTechnicalCategoryLink.class);
            if ( r.hasMoreElements() ) {
                docCate = (KETDocumentCategory) r.nextElement();
                categoryCode3=docCate.getCategoryCode();
                categoryCode2=docCate.getParentCategoryCode();

                KETDocumentCategory docCate1 = KETDmsHelper.service.selectDocCategory(categoryCode2);
                categoryCode1=docCate1.getParentCategoryCode();

                //문서에 링크된 분류체계로 분류체계path를 구한다.
                docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode3);
                Kogger.debug("UpdateTechDocument", null, null, "docCatePath ===>" + docCatePath);
                docCatePath=docCatePath.substring(1);
                categoryCode=categoryCode3;
                categoryCodeTxt=docCate.getCategoryName();
                
                if(StringUtils.isNotEmpty(docCate.getAttribute2())){
                    isEditable = false;
                }
            }

            documentName = docu.getTitle();
            documentDescription = docu.getDocumentDescription();

            // Web Editor
            webEditor = docu.getWebEditor();
            webEditorText = docu.getWebEditorText();

            // 과거버전에 대해 webEditor Column으로 Migration 되지 않아서
            // null 인경우는 예전 Column에서 가져오도록 변경
            if ( webEditor == null ) {
                webEditor = docu.getDocumentDescription();
            }

            //문서객체로 주첨부파일의 정보를 가져온다.
            if ( docu instanceof FormatContentHolder ) {
                FormatContentHolder holder = (FormatContentHolder)docu;
                ContentInfo info = ContentUtil.getPrimaryContent(holder);
                ContentItem ctf = (ContentItem)CommonUtil.getObject(info.getContentOid());

                appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                String ext = FilenameUtils.getExtension(info.getName());
                //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                if("Y".equals(docu.getAttribute1())){
                	urlpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')>"	+ info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
                	iconpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')><img src='"	+ ContentUtil.getContentIconPath(ext) + "' border=0></a>";
				}else{
					urlpath = "<a href=" + urlpath + " target='_blank'>"+ info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
					iconpath = info.getIconURLStr();
				}
                
                
            }
        }
    }

%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01430") %><%--문서수정--%></title>
<%@include file="/jsp/common/processing.html" %>
<script type="text/javascript" src="../../portal/js/org.js"></script>
<script type="text/javascript" src="../../portal/js/common.js"></script>
<link rel="stylesheet" href="../../portal/css/e3ps.css" type="text/css">
<script language="javascript" src="/plm/jsp/dms/js/techdocFile.js"></script>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
<!--
<%-- 이거 주석으로 막으면 문제(팝업 화면 상단 이상하게 보임) 생기니까 말해줘요. 2차고도화 YJLEE --%>
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">
<!--

$(document).ready(function() {
	   
    // 이거 건들면 수정 팝업에서 위에 2개 나오는 등 미쳐...
    //$("body").removeClass("body").removeClass("body-space").addClass("popup-background02 body-space");
    var hasBodyClass = $("body").hasClass('body');
    //var hasBodySpaceClass = $("body").hasClass('body-space');
    //var hasPobBackClass = $("body").hasClass('popup-background');
    var hasPopClass = $("body").hasClass('pop');
    
   	if(hasBodyClass && hasPopClass){
   		//$("body").removeClass("body").removeClass("pop").addClass("body-space");
   		//$("body").css({"margin-top": "0px;"});
   		//document.getElementsByTagName("body").style.marginTop = "0px";
   	}
    
    //기술 문서 분류 suggest
    SuggestUtil.bind('TECHDOCTYPE', 'categoryCodeTxt', 'categoryCode');
       
    $("#categoryCode").change(function(){
        if($(this).val() == ""){
            $("[name=categoryCodeTxt]").val("");
        }else{
        }
    });
    
});

function isNull (str) {
    if ( str == null || str == "" ) {
        return true;
    }
    return false;
}

function insertFileLine () {
    //첨부파일 추가시 첨부파일 라인을 추가한다.
    var tBody = document.getElementById("iFileTable");
    var innerRow = tBody.insertRow();
    var innerCell = innerRow.insertCell();
    var filePath = "filePath"+tBody.rows.length;

    var filehtml = "";
    filehtml += "  <input type='checkbox' name='iFileChk' id='checkbox' >";
    filehtml += "  <input name='"+filePath+"' type='file' class='txt_fieldRO' size='85'>";
    innerCell.innerHTML = filehtml;
}

function deleteFileLine () {
    var body;
    var file_checks;

    //첨부파일 삭제시 선택된 첨부파일 라인을 삭제한다.
    body = document.getElementById("iFileTableOld");
    //alert("ContentOid.length=====>"+document.form01.all.ContentOid.length);
    if ( body.rows.length > 0 ) {
        file_checks = document.form01.iFileChkOld;

        if ( body.rows.length == 1 ) {
            //첨부파일 라인수가 1일경우 iFileChkOld를 배열상태일수도 아닐수도 있으므로 아래와 같이 처리한다.
            if ( file_checks[0]=="[object]" ) {
                if ( file_checks[0].checked ) {
                    //원래 있던 첨부파일 이라면 히든값인 isFileDel에 삭제된 첨부팔일 정보를 저장한다.
                    document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid[0].value;
                    body.deleteRow(0);
                }
            }
            else{
                if ( file_checks.checked ) {
                    document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid.value;
                    body.deleteRow(0);
                }
            }
        }
        else {
            for ( var i = body.rows.length; i > 0; i-- ) {
                if ( file_checks[i-1].checked ) {
                    document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid[i-1].value;
                    body.deleteRow(i - 1);
                }
            }
        }
    }
    //alert("isFileDel=====>"+document.form01.isFileDel.value);

    body = document.getElementById("iFileTable");
    if ( body.rows.length > 0 ) {
        file_checks = document.form01.iFileChk;
        if ( body.rows.length == 1 ) {
            if ( file_checks[0]=="[object]" ) {
                //원래 있던 첨부파일이 아니라면 해당라인만 삭제처리한다.
                if (file_checks[0].checked) body.deleteRow(0);
            }
            else {
                if (file_checks.checked) body.deleteRow(0);
            }
        }
        else {
            for ( var i = body.rows.length; i > 0; i-- ) {
                if (file_checks[i-1].checked) body.deleteRow(i - 1);
            }
        }
    }
}

function doCancel (isPop,buttenView) {
    var dOid = document.form01.docuOid;
    if ( isPop=="Y" ) {
        document.location.href="/plm/jsp/dms/ViewTechDocumentPop.jsp?oid="+dOid.value+"&buttenView="+buttenView;
    }
    else {
        document.location.href="/plm/jsp/dms/ViewTechDocument.jsp?oid="+dOid.value+"&nowBlock=<%=SnowBlock%>&page=<%=Spage%>&cmd=search&documentNo=<%=SdocumentNo%>&divisionCode=<%=SdivisionCode%>&categoryCode=<%=ScategoryCode%>&documentName=<%=SdocumentName%>&authorStatus=<%=SauthorStatus%>&creatorName=<%=ScreatorName%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&islastversion=<%=Sislastversion%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T";
    }
}

function doSave (isPop) {
    //저장버튼 클릭시 valcheck체크후 multipart/form-data형태로 submit한다.
    if ( !valcheck() ) return;
    else {
        var d = document.form01;

        d.documentDescription.value = "";
        // innoditor WebEditor
        // 첫번째 매개변수 => true : < & 특수문자 처리,  false : 처리안함
        // 두번째 매개변수 => 이노디터 번호
        d.webEditor.value = fnGetEditorHTMLCode(false, 0);
        d.webEditorText.value = fnGetEditorText(0);

       ////////////////////////////////////////////////////////////////////////////////////////////////////
       // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
       d.hdnBackgroundColor.value = fnGetBodyStyleValue(1, 0);// 배경색, 이노디터 번호
       d.hdnBackgroundImage.value = fnGetBodyStyleValue(2, 0);// 배경이미지, 이노디터 번호
       d.hdnBackgroundRepeat.value = fnGetBodyStyleValue(3, 0);// 배경이미지 반복옵션, 이노디터 번호
       ////////////////////////////////////////////////////////////////////////////////////////////////////

        d.encoding = 'multipart/form-data';
        if ( isPop == "Y" ) {
            d.action = "/plm/servlet/e3ps/TechDocumentServlet?cmd=updatePop";
        }
        else{
            d.action = "/plm/servlet/e3ps/TechDocumentServlet?cmd=update";
        }
        showProcessing();
        disabledAllBtn();
        d.submit();
    }
}

function isNotDigit (str) {
    var pattern = /^[0-9]+$/;
    str = str.replace(".", "0");
    if ( !pattern.test(str) ) {
        return true;
    }
    return false;
}
function checkDisabledCategory(categoryCode){
    var rtn = '';
    $.ajax({
        url : "/plm/ext/dms/isDisabledCategory.do",
        type : "POST",
        data : {
            devDocCagegoryCode : categoryCode
        },
        dataType : 'json',
        async : false,
        success : function(data) {
            rtn = data;
        }
    });
    return rtn;
}

    //필수입력체크
    function valcheck() {
    	
        var d = document.form01;

        if ( ( d.categoryCode.value=="0" ) || ( isNull(d.categoryCode.value) ) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01425") %><%--문서분류는 반드시 입력해야 합니다--%>');
            return false;
        }
        var msg = checkDisabledCategory(d.categoryCode.value);
        
        if(msg != ''){
            alert(msg);
            return false;
        }

        if ( isNull(d.documentName.value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01418") %><%--문서명은 반드시 입력해야 합니다.--%>');
            return false;
        }
        else{
            var s = d.documentName.value;
            if (s.length > 160){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01417") %><%--문서명은 160자를 초과할 수 없습니다--%>');
                return false;
            }
        }
        
        if (d.isDesign.value == "Y" && isNull(d.techDeptCode.value) ) {
        	alert('부서조회권한을 설정해야합니다.');
            return false;
        }

        var strHTMLCode = fnGetEditorHTMLCode(false, 0);
        if ( strHTMLCode == "" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01429") %><%--문서설명은 반드시 입력해야 합니다.--%>');
            return false;
        }
        return true;
    }
    function fileOpenAjax(oid,appDataOid) {
		
		var start_con = "";
		var bat_file  = "";
		var filePath  = "";
		var end_con   = "";
	    $.ajax( {
	        url  : "/plm/servlet/e3ps/TechDocumentServlet?cmd=designFileOpen&oid="+oid+"&appDataOid="+appDataOid,
	        type : "POST",
	        async : false,
	        dataType : 'json',
	        success: function(data) {
	        	$.each(data.returnObj, function() {
	        		start_con = this.start_con;
	        		bat_file  = this.bat_file;
	        		filePath  = this.filePath;
	        		fileName  = this.fileName;
	        		end_con   = this.end_con;
                });
	        	
	        	start_con = decode(start_con);
	        	bat_file = decode(bat_file);
	        	filePath = decode(filePath)+fileName;
	        	end_con = decode(end_con);
	        	fnRunConnectFile(start_con, bat_file, filePath, end_con);
	        }
	    });
	}
//-->
</script>

<script type="text/javascript">
//Document Category Ajax
function numCodeAjax(docTypeCode, parentCode, targetId) {
    $.ajax( {
        url : "/plm/servlet/e3ps/DocumentCategoryAjax",
        type : "POST",
        data : {docTypeCode:docTypeCode, parentCode:parentCode},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.returnObj, function() {
                $("#"+targetId).append("<option value='"+this.categoryCode+"'>"+ this.categoryName +"</option>");
            });
        }
    });
}
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
g_nEditorWidth = 770;
g_nEditorHeight = 500;

//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<!-- 이노디터 JS Include End -->

<script type="text/javascript">
//<![CDATA[
function fnSetEditorHTML() {
    var strHTMLCode = document.form01["webEditor"].value;

    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.form01["hdnBackgroundColor"].value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.form01["hdnBackgroundImage"].value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.form01["hdnBackgroundRepeat"].value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
}
//]]>
</script>

<script type="text/javascript">
//<![CDATA[

// -- body의 onload에 이벤트를 직접 설정하기 힘든 경우 아래처럼 이벤트 추가
if ( g_browserCHK.ie ) {
    window.attachEvent("onload", fnSetEditorHTML);
}
else if ( g_browserCHK.ff || g_browserCHK.wk ) {
    window.addEventListener("load", fnSetEditorHTML, false);
}


//]]>

function setCategoryCallBack(returnValue){
    
    $('[name=categoryCode]').val(returnValue[0].id);//id
    $('[name=categoryCodeTxt]').val(returnValue[0].name);//name
    $('[name=isDesign]').val(returnValue[0].attribute1);//설계가이드 여부
    
    $("[name=techDeptName]").val("");           
    $("[name=techDeptCode]").val("");
    
    if($('[name='+idDesignField+']').val() == 'Y'){
        //$("#standardTR").hide();
        $("#deptTR").show();
    }else{
        //$("#standardTR").show();
        $("#deptTR").hide();
    }
}
function setDocCategory(){
    
    var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?docRoot=TECH&singleSel=true&onlyLeaf=Y&modal=N&fnCall=setCategoryCallBack";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    
    getOpenWindow2(url,'techDocCreate', 450, 350, opts);
    
    
    
}
</script>
</head>

<body class="body pop">
<form name="form01" method="post" >
<input type="hidden" name="cmd" value="<%=cmd%>">
<input type="hidden" name="docuOid" value="<%=oid%>">
<input type="hidden" name="isFileDel" value="0">

<!-- 이노디터에서 작성된 내용을 읽어온 후 수정시에 내용을 보낼 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"><%=webEditor%></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
    <input type="hidden" name="hdnBackgroundColor" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF -->
    <input type="hidden" name="hdnBackgroundImage" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 읽어온 후 수정시에 내용을 보낼 Form End -->

<table width="100%" height="100%" border="0" cellspacingf="0" cellpadding="0">
  <tr>
    <td valign="top">
       <%if(isPop.equals("Y")){%>
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="../../portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01123") %><%--기술문서--%> <%if(cmd.equals("revice")){%><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%> <%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%> <%}%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
       </tr>
       </table>
       <%}else{%>
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01123") %><%--기술문서--%> <%if(cmd.equals("revice")){%><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%> <%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%> <%}%></td>
                
              </tr>
            </table></td>
        </tr>
        
      </table>
      <%}%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSave('<%=isPop%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
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
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td colspan="3" class="tdwhiteL0"><%=documentNo%></td>
       </tr>
       <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td colspan="3" class="tdwhiteL0"><%if(cmd.equals("revice")){%><%=Integer.parseInt(versionInfo)+1%><%}else{%><%=versionInfo%><%}%></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%><span class="red">*</span></td><!--  문서분류 -->
          <td colspan="3" class="tdwhiteL0">
                 <%
                if(cmd.equals("revice")){
                %>
                    <input type="hidden" name="categoryCode" value="<%=categoryCode%>">
                    <input type="hidden" id="isDesign" name="isDesign" value="<%=docu.getAttribute1()%>">
                    <%=docCatePath %>
                <%
                }else{
                	if(isEditable){
                %>
            		<input type="text" id="categoryCodeTxt" name="categoryCodeTxt" value="<%=categoryCodeTxt%>" class="txt_field" style="width: 380px">
            	<% 
                	}else{
            	%>
            		<input type="text" id="categoryCodeTxt" name="categoryCodeTxt" value="<%=categoryCodeTxt%>" class="txt_fieldRO" style="width: 380px" disabled="disabled" readonly>
            	<%
                	}
            	%>
            <input type="hidden" id="categoryCode" name="categoryCode" value="<%=categoryCode%>">
            <input type="hidden" id="isDesign" name="isDesign" value="<%=docu.getAttribute1()%>">
            		<%if(isEditable){ %>
            		<a href="javascript:setDocCategory();">
            		<img src="/plm/portal/images/icon_5.png" border="0"></a>
            		<a href="javascript:CommonUtil.deleteValue('categoryCode', 'categoryCodeTxt', 'isDesign');">
            		<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                <%
            		}
                }
                %>
          </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%><span class="red">*</span></td> <!--  문서명 -->
          <td colspan="3" class="tdwhiteL0"><input type="text" name="documentName" class="txt_field" id="textfield2" style="width:635" value="<%=documentName%>"></td>
        </tr>
        <%if("Y".equals(docu.getAttribute1())){
            Department dept = DepartmentHelper.manager.getDepartment(docu.getAttribute2());
        %>
		<tr>
			<td width="130" class="tdblueL">부서조회권한<span class="red">*</span></td>
			<td class="tdwhiteL">
            <input type="text" name="techDeptName" value="<%=dept.getName() %>" class="txt_field" style="width: 30%" readonly> 
            <input type="hidden" name="techDeptCode" value="<%=docu.getAttribute2() %>" > 
            <a href="javascript:SearchUtil.addAllDepartment('techDeptCode', 'techDeptName','y');">
            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('techDeptCode','techDeptName');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
		</tr>
		<tr>
			<td class="tdblueL">사용자조회권한<span class="red">*</span></td>
			 <td class="tdwhiteL" colspan="2"><select name="duty" id="duty" style="width: 30%">
                  <%
                  		Vector dutyNameList = CompanyState.dutyNameList;
                  		Vector dutyCodeList = CompanyState.dutyCodeList;
                      for (int i = 0; i < dutyCodeList.size(); i++) {
                    	  if(!"고문".equals(dutyNameList.get(i)) && !"감사".equals(dutyNameList.get(i))){
                  %>
                  		  <option value="<%=dutyCodeList.get(i)%>" <%if(docu.getAttribute3().equals((String)dutyCodeList.get(i)))out.print("selected");%> ><%=dutyNameList.get(i)%></option>
                  <%
                      	  }
                      }
                  %>
              </select></td>
		</tr>
		<%}%>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667") %><%--주 첨부파일--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0"><table border="0" cellpadding="0" cellspacing="0">
              <tr>

                <td><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <%=iconpath%>&nbsp;<%=urlpath%>
                  </tr>
                  <tr>
                    <input name="iFile0" type="file" class="txt_fieldRO" style="width:636;">
                  </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
          <td colspan="3" class="tdwhiteL0"><table width="640" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>
            <table width="640" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertFileLine();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                      </tr>
                    </table></td>
                    <td width="5">&nbsp;</td>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteFileLine();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
                <td align="right">&nbsp;</td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
            <tbody id="iFileTableOld"/>
<%
          if ( docu instanceof FormatContentHolder ) {
                FormatContentHolder holder = (FormatContentHolder)docu;
                Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);

                if(secondaryFiles.size()>0){
                	String ext = "";
                    for(int i=0; i<secondaryFiles.size(); i++) {
                        ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);

                        if( info == null) {
                            iconpath = "";
                            urlpath = "";
                        } else {
                            ContentItem ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());
                            appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                            ext = FilenameUtils.getExtension(info.getName());
                            //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                            urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                            if("Y".equals(docu.getAttribute1())){
                            	urlpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')>"	+ info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
                            	iconpath = "<a href=javascript:fileOpenAjax('"+oid+"'"+",'"+appDataOid+"')><img src='"	+ ContentUtil.getContentIconPath(ext) + "' border=0></a>";
            				}else{
            					urlpath = "<a href=" + urlpath + " target='_blank'>"+ info.getName() + "</a>&nbsp;(&nbsp;"+ info.getFileSize() + "&nbsp;)";
            					iconpath = info.getIconURLStr();
            				}
                        }
%>
              <tr><td><p><input type='checkbox' name='iFileChkOld' id='checkbox' ><input name='ContentOid' type='hidden' value='<%=info.getContentOid()%>'><%=iconpath%>&nbsp;<%=urlpath%></p></td></tr>
<%
                    }
                }
            }
%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="640">
                <tbody id="iFileTable"/>
            </table>
            <table width="640" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td colspan="4" class="tdwhiteL0" style="width:100%; height:500;">
<!--              <textarea name="documentDescription" class="txt_field" id="textfield3" style="width:635; height:96%"><%=documentDescription%></textarea>-->

                <textarea name="documentDescription" rows="0" cols="0" style="display:none"><%=documentDescription%></textarea>
                <textarea name="webEditorText" rows="0" cols="0" style="display:none"><%=webEditorText%></textarea>
                <!-- Editor Area Container : Start -->
                <div id="EDITOR_AREA_CONTAINER"></div>
                <!-- Editor Area Container : End -->
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <%if(isPop.equals("Y")){%>
      <td height="30" valign="bottom"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  <%}else{%>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  <%}%>
  </tr>
</table>
</form>
</body>
</html>
