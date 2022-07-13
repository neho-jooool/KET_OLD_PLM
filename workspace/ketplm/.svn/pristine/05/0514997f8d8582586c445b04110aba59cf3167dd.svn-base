<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.groupware.company.DepartmentHelper"%>
<%@page import="e3ps.groupware.company.CompanyState"%>
<%@page import="ext.ket.shared.content.service.KETContentHelper"%>
<%@page import="ext.ket.shared.content.entity.ContentDTO"%>
<%@page import="e3ps.dms.beans.DMSUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.dms.service.KETDmsHelper,
                        e3ps.dms.entity.*,
                        e3ps.project.*,
                        e3ps.project.beans.*,
                        wt.fc.QueryResult,
                        wt.fc.PersistenceHelper,
                        wt.doc.WTDocument,
                        wt.doc.WTDocumentMaster,
                        wt.query.QueryException,
                        wt.query.QuerySpec,
                        wt.query.SQLFunction,
                        wt.query.SearchCondition,
                        wt.content.*,
                        wt.part.WTPart,
                        wt.lifecycle.LifeCycleHelper,
                        wt.lifecycle.LifeCycleManaged,
                        e3ps.common.content.*,
                        java.util.Vector,
                        java.util.StringTokenizer,
                        java.util.ArrayList,
                        e3ps.common.util.CommonUtil,
                        e3ps.common.util.StringUtil,
                        e3ps.common.util.DateUtil,
                        org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;
    String SdocumentNo = StringUtil.checkNull(request.getParameter("documentNo"));
    String SdivisionCode = StringUtil.checkNull(request.getParameter("divisionCode"));
    String ScategoryCode = StringUtil.checkNull(request.getParameter("categoryCode"));
    String SdocumentName = StringUtil.checkNull(request.getParameter("documentName"));
    String SauthorStatus = StringUtil.checkNull(request.getParameter("authorStatus"));
    String ScreatorName = StringUtil.checkNull(request.getParameter("creatorName"));
    String Spredate = StringUtil.checkNull(request.getParameter("predate"));
    String Spostdate = StringUtil.checkNull(request.getParameter("postdate"));
    String SisBuyerSummit = StringUtil.checkNull(request.getParameter("isBuyerSummit"));
    String SbuyerCodeStr = StringUtil.checkNull(request.getParameter("buyerCodeStr"));
    String Sislastversion = StringUtil.checkNull(request.getParameter("islastversion"));
    String Squality = StringUtil.checkNull(request.getParameter("quality"));
    String SprojectNo = StringUtil.checkNull(request.getParameter("projectNo"));
    String SprojectName = StringUtil.checkNull(request.getParameter("projectName"));
    String SsortKey = StringUtil.checkNull(request.getParameter("sortKey"));
    String SsortKeyD = StringUtil.checkNull(request.getParameter("sortKeyD"));
    String SpageCnt = StringUtil.checkNull(request.getParameter("pageCnt"));
    String Spage = StringUtil.checkNull(request.getParameter("page"));
    String SnowBlock = StringUtil.checkNull(request.getParameter("nowBlock"));
    String isSer = StringUtil.checkNull(request.getParameter("isSer"));
    if ( (isSer == null) || (isSer.trim().length() == 0) ) {
        isSer = "F";
    }
    if ( SsortKeyD.equals("ASC") ) {
        SsortKeyD="DESC";
    }
    else {
        SsortKeyD="ASC";
    }
    String oid =  request.getParameter("oid");
    String cmd =  request.getParameter("cmd");
    String isPop =  request.getParameter("isPop");
    String buttenView = request.getParameter("buttenView");
    String isRefresh = request.getParameter("isRefresh");
    if ( ( buttenView == null) || (buttenView.trim().length() == 0) ) {
        buttenView = "F";
    }
    isPop = StringUtil.checkNull(isPop);
    String categoryCode1 = null;
    String categoryCode2 = null;
    String categoryCode3 = null;
    String categoryCode = null;
    String categoryName = null;
    String docCatePath = null;
    String docTypeCode = null;
    String isDRCheckSheet = null;
    String tmpStr = null;
    String documentNo = null;
    String documentName = null;
    String divisionCode = null;
    // 품질표준문서
    String qualityNo = "";
    String analysisCode = "";
    String analysisNo = "";
    
    String versionInfo = null;
    String iterationInfo = null;
    String deptName = null;
    String outputOid = "0";
    String firstRegistrationStage = null;
    Integer tmpInt = 0;
    String isBuyerSummit = null;
    String buyerCode = null;
    String dRCheckPoint = null;
    String documentDescription = null;
    String urlpath="";
    String iconpath="";
    String appDataOid="";
    String pjtNo = "";
    String pjtName = "";
    String taskName = "";
    String insUser = "who";
    String security = "";
    String securityCode ="";
    String pubDate = "";
    String pubCycle = "";
    String pubDateYn = "";
    
    String costComment = "";
    
    // Web Editor
    Object webEditor = null;
    Object webEditorText = null;
    KETProjectDocument docu = null;
    Object[] obj = null;
    List<Map<String, Object>> categoryList = null;
    boolean isEditable = true;
    String isQulityDoc = "N";
    ContentDTO primaryFile = null;
    String basicPart = "";
    if ( oid != null ) {
	    Kogger.debug("UpdateDocument", null, oid, "update revice oid ===>" + oid);
        if ( oid.equals("0000") ) {
            //불필요
        }
        else{
            //KETProjectDocument의 oid로 해당 객체의 정보를 화면에 나타낸다.
            docu = (KETProjectDocument)CommonUtil.getObject(oid);
            primaryFile = KETContentHelper.manager.getPrimaryContent(docu);
            documentNo =  docu.getDocumentNo();
            // 품질표준문서
            qualityNo = StringUtil.checkNull(docu.getAttribute1());
            analysisCode = StringUtil.checkNull(docu.getAttribute2()); 
            analysisNo = StringUtil.checkNull(docu.getAttribute3()); 
            versionInfo = docu.getVersionIdentifier().getValue();
            iterationInfo = docu.getIterationIdentifier().getValue();
            //divisionCode =  docu.getDivisionCode();
            KETDocumentCategory docCate = null;
            // 1 Level 분류체계 찾기
            parameter.clear();
            parameter.put("docTypeCode", "PD");
            parameter.put("locale",      messageService.getLocale().toString());
            parameter.put("parentCode",  "ROOT");
            categoryList = DMSUtil.getDocumentCategory(parameter);
            if ( categoryList.size() > 0 ) {
                categoryCode1 = categoryList.get(0).get("categoryCode").toString();
                categoryName = categoryList.get(0).get("categoryName").toString();
            }
            security =  StringUtil.checkNull(docu.getSecurity());
            if ( security.length() > 0 ) {
                NumberCode code = NumberCodeHelper.manager.getNumberCode( "SECURITYLEVEL",security);
                securityCode = code.getCode();
            }
            //KETProjectDocument의 해당 마스터객체로 링크된 분류체계를 검색한다.
            QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);
            if ( r.hasMoreElements() ) {
                docCate = (KETDocumentCategory) r.nextElement();
                categoryCode3=docCate.getCategoryCode();
                categoryCode2=docCate.getParentCategoryCode();
                //문서에 링크된 분류체계로 DR평가점수 필요여부를 결정한다.
                Boolean isDRCheck=docCate.getIsDRCheckSheet();
                if ( isDRCheck == true ) {
                    isDRCheckSheet = "t";
                }
                else {
                    isDRCheckSheet = "f";
                }
                
                if("Y".equals(docCate.getAttribute6())){
    		    	pubDateYn = "Y";
    		    }else{
    		    	pubDateYn = "N";
    		    }
                
                //문서에 링크된 분류체계로 분류체계path를 구한다.
                docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode3);
                Kogger.debug("UpdateDocument", null, null, "docCatePath ===>" + docCatePath);
                docCatePath=StringUtil.checkNull(docCatePath.substring(1));
                categoryCode=categoryCode3;
                if(StringUtils.isNotEmpty(docCate.getAttribute2())){
                    if(!docu.getDocumentNo().startsWith("PD")){
                	   isEditable = false;
                    }
                    
                    
                    
                    if(StringUtils.isEmpty(docCate.getAttribute3())){
                	   isQulityDoc = "Y";
                    }
                    
                    if(!docu.getDocumentNo().startsWith("PD")){
                	   basicPart = docu.getDocumentNo().split(docCate.getAttribute2()+"-")[1];
                    }
                    
                }
            }
            documentName = docu.getTitle();
            //deptName = docu.getDeptName();
            tmpInt = docu.getFirstRegistrationStage();
            firstRegistrationStage = tmpInt.toString();
            tmpInt = docu.getDRCheckPoint();
            dRCheckPoint = tmpInt.toString();
            isBuyerSummit = docu.getIsBuyerSummit();
            buyerCode = docu.getBuyerCode();
            documentDescription = docu.getDocumentDescription();
            // Web Editor
            webEditor = docu.getWebEditor();
            webEditorText = docu.getWebEditorText();
            
            //pubDateYn = docu.getAttribute8();
            pubDate = docu.getAttribute9();
            pubCycle = docu.getAttribute10();
            
            costComment = StringUtil.checkNull(docu.getCostComment());
            // 과거버전에 대해 webEditor Column으로 Migration 되지 않아서
            // null 인경우는 예전 Column에서 가져오도록 변경
            if ( webEditor == null ) {
                webEditor = docu.getDocumentDescription();
                webEditor = StringUtil.checkNull((String) webEditor);
            }
            //문서객체로 주첨부파일의 정보를 가져온다.
            if ( docu instanceof FormatContentHolder ) {
                FormatContentHolder holder = (FormatContentHolder)docu;
                ContentInfo info = ContentUtil.getPrimaryContent(holder);
                if ( info != null ) {
                    ContentItem ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());
                    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                    //urlpath = "/plm/servlet/e3ps.common.content.servlet.DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                    //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                    urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                    urlpath = "<a href=" + urlpath + " target='download'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                    iconpath = info.getIconURLStr();
                }
            }
            ProjectOutput po1;
            QueryResult r31 = PersistenceHelper.manager.navigate(docu, "output" , KETDocumentOutputLink.class);
            if ( r31.hasMoreElements() ) {
                po1 = (ProjectOutput)r31.nextElement();
                outputOid = CommonUtil.getOIDString(po1);
            }
        }
    }
    QueryResult qr = null;
    String tempRanke = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01430") %><%--문서수정--%></title>
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js?ver=0.1"></script>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<%@include file="/jsp/common/multicombo.jsp" %>
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
<script type="text/javascript">
var tempDocNo = "<%=documentNo%>";

$(document).ready(function() {
    $("[name=iFile0]").on("change", function(){
        
        f_name = $("[name=iFile0]").val().split("\\");
        s_name = f_name[(f_name.length-1)];
        if($("[name=documentName]").val().length > 0){
            $("[name=documentName]").val(s_name.substring(0, s_name.lastIndexOf(".")));
        }
    });
    var isQulityDoc = '<%=isQulityDoc%>';
    if(isQulityDoc != 'Y'){
        $("#quiltyTTR").css("display", "none");
    }
    
    if(part_no_check != ""){
    	
    	$("#quiltyTTR").css("display", "none");
    }
    
    resizeTo(1000, 900);
});

<!--

function selectPartAfterFunc(objArr){
    
    var srcPartNo= "";
    var srcPartName= "";
    var relatedPartOid = "";
    for ( var i = 0; i < objArr.length; i++ ) {
        
        srcPartNo = objArr[i][1];
        relatedPartOid = objArr[i][0];
    }
    
    var param = {
   		currentDocNo : "<%=documentNo%>",
       	categoryCode : $("input[name=categoryCode]").val(),
       	relatedPartOid : relatedPartOid,
       	partNoList : []
    }

    var data = ajaxCallServer("/plm/ext/dms/chkDupMatPart", param, null, false);
    var dupList = data.dupList;
       
   	if(dupList.length > 0){
       	
       	var msg = "동일 자재가 다른 문서에 기 등록되어 있습니다.\n";
   		
   		for(var i = 0; i < dupList.length; i++){
   			msg += dupList[i] + "\n";
   		}
   		
   		msg += "중복 등록 시 결재가 불가하오니 조치 후 진행 바랍니다.";
   		
   		alert(msg);
   		
  		return true;
   	}
    
    $('input[name="relatedPart"]').val(srcPartNo);
}

function changeCategory2 () {
    $("#category3").empty().data('options');
    //2단계 분류체계변경시 CateSelect.jsp를 호출하여 3단계를 화면에 나타내준다.
    numCodeAjax("PD", $("#category2").val(), "category3");
}

function changeCategory3 () {
    //3단계 분류체계변경시 선택된 분류체계를 categoryCode에 저장하고 DR평가점수 필요여부를 체크한다.
    var fm = document.frm
    var tStr = $("#category3").val();
    fm.categoryCode.value =tStr.substring(0,8);
    fm.isDRCheckSheet.value = tStr.substring(8,9);
}

function insertCustomLine() {
    //고객사 추가시 buyerCode select에 선택된 고객사정보를 추가한다.
    var fm = document.frm
    if ( fm.buyerCode.disabled == false ) {
        var mode = "multi";
        var subArr;
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SAPSUBCONTRACTOR&command=select&mode="+mode;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:500px; center:yes");
        if ( typeof returnValue == "undefined" || returnValue == null ) {
            return;
        }
        var pos = fm.buyerCode.length;
        for ( var i = 0; i < returnValue.length; i++ ) {
            subArr = returnValue[i];
            for ( var j = 0; j < pos; j++ ) {
                if ( fm.buyerCode.options[j].value==subArr[0] ) {
                    alert(subArr[2]+"<%=messageService.getString("e3ps.message.ket_message", "00022") %><%--{0}는 이미 존재하는 고객사입니다--%>");
                    return;
                }
            }
            fm.buyerCode.length = pos+1;
            fm.buyerCode.options[pos].value= subArr[0];
            fm.buyerCode.options[pos++].text = subArr[2];
        }
    }
    else{
        alert('<%=messageService.getString("e3ps.message.ket_message", "00845") %><%--고객사제출자료 대상문서일 경우 지정가능합니다--%>');
    }
}

function deleteCustomLine () {
    //고객사 삭제시 선택된 고객사정보를 삭제한다.
    var fm = document.frm;
    if ( fm.buyerCode.disabled == false ) {
        while ( fm.buyerCode.selectedIndex >= 0 ) {
            if ( (fm.buyerCode.length>0) && (fm.buyerCode.selectedIndex>=0) ) {
                var pos = fm.buyerCode.selectedIndex;
                fm.buyerCode.remove(pos);
            }
        }
    }
}

function enableBuyerCode () {
    //고객제출자료 여부에 따라 고객사select 를 enable한다.
    //var fm = document.frm;
    //fm.buyerCode.disabled == false;
    //fm.buyerCodeTxt.disabled = false;
    //$("#selectMultiSubContractorDiv").show();
    //$("[name=buyerCode]").attr("disabled", false);
    $("#customerText").html("<%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%> <span class='red'>*</span>");
}

function disableBuyerCode () {
    //고객제출자료 여부에 따라 고객사select 를 disabled한다.
    //var fm = document.frm;
    //fm.buyerCode.disabled == true;
    //fm.buyerCodeTxt.disabled = true;
    //$("#selectMultiSubContractorDiv").hide();
    //$("[name=buyerCode]").attr("disabled", true); 
    $("#customerText").html("<%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%>");
}

function changeSecu (rank) {
    form = document.forms[0];
    var ranklist="";
    var isSecu = true;
    ranklist = rank.split("|");
    for ( var i=0; i<ranklist.length; i++ ) {
        if ( ranklist[i] != "S" ) {
            isSecu = false;
            form.security.value="S1";
            break;
        }
    }
    if ( isSecu ) {
        form.security.value="S2";
        form.security.readonly = true;
    }
}

function selProjectNo () {
    //프로젝트검색창을 열어 프로젝트를 선택한다.
    var url="/plm/jsp/project/SearchPjtPop.jsp?mode=multi&modal=N";
    openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
}

function setProject(returnValue) {
    if ( returnValue == null ) {
        return;
    }
    var d = document.forms[0];
    var objArr = returnValue;
    var proj_oid;
    var proj_number;
    var proj_name;
    var body = document.getElementById("iProjTable");
    var proj_rank;
    var tempRank ="";
    var projectRanks = d.projectRank;
    <%-- if ( projectRanks != null ) {
        if ( projectRanks.length != null ) {
            for ( var i = 0 ; i< projectRanks.length ; i++ ) {
                for ( var h= 0; h < objArr.length; h++ ) {
                    proj_rank = objArr[h][9];
                    if ( h ==0 ) {
                        tempRank = proj_rank;
                    }
                    if ( tempRank != proj_rank ) {
                        alert("<%=messageService.getString("e3ps.message.ket_message", "00437") %>Rank가 서로 다른 프로젝트는 선택할수 없습니다");
                        return true;
                    }
                    if ( projectRanks[i].value != tempRank ) {
                        alert("<%=messageService.getString("e3ps.message.ket_message", "00437") %>Rank가 서로 다른 프로젝트는 선택할수 없습니다");
                        return true;
                    }
                }
            }
        }
        else {
            for ( var h= 0; h < objArr.length; h++ ) {
                proj_rank = objArr[h][9];
                if ( h == 0 ) {
                    tempRank = proj_rank;
                }
                if ( tempRank != proj_rank ) {
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00437") %>Rank가 서로 다른 프로젝트는 선택할수 없습니다");
                    return true;
                }
                if ( projectRanks.value != tempRank ) {
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00437") %>Rank가 서로 다른 프로젝트는 선택할수 없습니다");
                    return true;
                }
            }
        }
    }
    else {
        for ( var i = 0; i < objArr.length; i++ ) {
            proj_rank = objArr[i][9];
            if ( i == 0 ) {
                tempRank = proj_rank;
            }
            if ( tempRank != proj_rank ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00437") %>Rank가 서로 다른 프로젝트는 선택할수 없습니다");
                return true;
            }
        }
    } --%>
    
    var isQulityDoc = "<%=isQulityDoc%>";
    
    var dupPjtList = new Array();
    
    for ( var i = 0; i < objArr.length; i++ ) {
    	var isMold = false;
        
        proj_oid = objArr[i][0];
        if(proj_oid.indexOf('MoldProject') > -1){
            isMold = true;
        }
        
        proj_number = objArr[i][1];
        proj_name = objArr[i][2];
        proj_rank = objArr[i][9];
        task_name = objArr[i][14];
        if ( !isMold && proj_rank == "S" ) {//금형프로젝트 제외 2018.08.23 박상수 차장 요청
            d.security.value = "S2"; //대내비
            document.getElementById("security").disabled = true;
        }
        else {
            d.security.value = "S1"; //대외비
            document.getElementById("security").disabled = false;
        }
        
        var tBody = document.getElementById("iProjTable");
        if(tBody != null){
        	if(!pjtDupCheck(dupPjtList,proj_oid)){
        		var innerRow = tBody.insertRow();
                var innerCell = innerRow.insertCell();
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = ((isNull(task_name))?"<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>":"")
                + "<div style=\"display:none;\"><input name='iProjChk' type='checkbox' class='chkbox'></div><input type='hidden' name='projectOid' value='"+ proj_oid +"'><input type='hidden' name='proj_number' value='"+ proj_number +"'><input type='hidden' name=projectRank value="+proj_rank+">";
                innerCell = innerRow.insertCell();
                innerCell.className = "tdwhiteM";
                innerCell.innerText = proj_number;
                innerCell = innerRow.insertCell();
                innerCell.className = "tdwhiteM text-left";
                innerCell.innerText = proj_name;
                innerCell = innerRow.insertCell();
                innerCell.className = "tdwhiteM0";
                innerCell.innerText = task_name;
        	}
            
        }
        
        
        if(isQulityDoc != 'Y'){
            // 프로젝트 선택 시 관련제품정보 Ajax 처리
            //addProjectProductInfoAjax(proj_oid); 프로젝트 관련부품 연계기능 주석처리요청 2019.04.11 박상수 차장 요청
        }
        
        dupPjtList[i] = proj_oid;
        
        
        /* else{
            //alert("Duplicated Project ==>"+proj_number);
            return true;
        } */
    }
    return false;
}

function pjtDupCheck(dupPjtList,pjtOid){
	for(var i=0; i < dupPjtList.length; i++){
		if(dupPjtList[i] == pjtOid) {
			return true; // bool
		}
	}
	return false; // bool
}

function projDuplicateCheck (poid) {//사용안함
    var tBody = document.getElementById("iProjTable");
    var rowsLen = tBody.rows.length;
    if ( rowsLen > 0 ) {
        var primaryProj = document.forms[0].projectOid;
        var projLen = primaryProj.length;
        if ( projLen ) {
            for ( var i = 0; i < projLen; i++ ) {
                if ( primaryProj[i].value == poid ) {
                    return true;
                }
            }
        }
        else {
            if ( primaryProj.value == poid ) {
                return true;
            }
        }
    }
    return false;
}

function insertPartLine () {
<%--     var isQulityDoc = "<%=isQulityDoc%>";
    
    if(isQulityDoc == 'Y'){
    	alert("품질표준문서는 연계부품 수정이 불가능합니다.");
    	return false;
    } --%>
    
    var isQulityDoc = "<%=isQulityDoc%>";
    
    //아래 로직은 프로젝트번호를 부품검색팝업 호출시 파라미터로 전달하기 위함인데 제거한다 2021.05.20 박상수 차장 요청
    /* var proj_numbers = "";
    var proj_number = $("[name=proj_number]");
    if(proj_number.length > 0){
        for(var i=0; i < proj_number.length; i++){
            proj_numbers += proj_number[i].value + ",";
        }
    } */
    
    var categoryCodeName = $('[name=categoryCodeTxt]').val();
    
    //변경이력 
    // (1) 검사기준서(사내) 일 경우 관련부품 등록 유형 제한 (반제품,원자재,상품) 경영혁신팀 박상수 차장 요청 2019.08.14 by 황정태
    // 포장사양서(완제품) 추가 20201.05.20 박상수 차장 요청
    if(categoryCodeName.indexOf('포장사양서(완제품)') != -1){
    	SearchUtil.selectPart("setPartInfoSbl","fromPage=SBL&pType=F,R,W");
    }else if(categoryCodeName.indexOf('검사기준서(사내)') != -1 || categoryCodeName.indexOf('제조공정도') != -1 || categoryCodeName.indexOf('작업표준서') != -1 || categoryCodeName.indexOf('관리계획서') != -1 || categoryCodeName.indexOf('PFMEA') != -1){
    	//SearchUtil.selectPart("setPartInfoSbl","fromPage=SBL&pType=F,H,R,W&projectNo="+proj_numbers);
    	SearchUtil.selectPart("setPartInfoSbl","fromPage=SBL&pType=H,R,W");
    }else{
    	if(isQulityDoc == 'Y'){
    		SearchUtil.selectPart("setPartInfoSbl","fromPage=SBL&pType=F,H,R,W");
    	}else{
    		SearchUtil.selectPart("setPartInfoSbl","fromPage=SBL");	
    	}
    		
    }
    
}

function warning(row){
	var basicPart = "<%=basicPart%>";
	
	//데이터 정비를 위해 대표부품번호 체크 로직 임시적으로 해제 요청 2019.08.20 박상수 차장 요청
	if(basicPart == row.parent().parent()[0].cells[1].innerHTML){
		alert("대표부품번호는 삭제할 수 없습니다.");	
	}else{
		row.parent().parent().remove();
	}
	
	//row.parent().parent().remove();
}
var part_no_check = "";

function setPartInfoSbl(returnValue, isNonCheck){
    if ( typeof returnValue != "undefined" && returnValue.length > 0) {
        //부품추가시 선택된 부품정보를 추가한다. hidden값으로 해당Row에    partOid를 저장해둔다.
        
        var isQulityDoc = "<%=isQulityDoc%>";
        
      //문서중복체크######################
      
      if(isNonCheck == null) isNonCheck = false;
      
      if(!isNonCheck){
      		var partNoList = [];
            for ( var i = 0; i < returnValue.length; i++ ) {
                var partNoValue = returnValue[i][1];
                partNoList.push(partNoValue.toString());
            }
            
    		var param = {
    			currentDocNo : "<%=documentNo%>",
    			categoryCode : $("input[name=categoryCode]").val(),
    			relatedPartOid : $("input[name=relatedPartOid]").val(),
    			partNoList : partNoList
    		}
    		
    		var data = ajaxCallServer("/plm/ext/dms/chkDupMatPart", param, null, false);
    		var dupList = data.dupList;

    		if(dupList.length > 0){
    		
    			var msg = "동일 자재가 다른 문서에 기 등록되어 있습니다.\n";
    			for(var i = 0; i < dupList.length; i++){
    				msg += dupList[i] + "\n";
    			}
    			msg += "중복 등록 시 결재가 불가하오니 조치 후 진행 바랍니다.";
    			alert(msg);
    			
    			return true;
    		}
      	}
        
		//문서중복체크######################
        
        for ( var i = 0; i < returnValue.length; i++ ) {
            part_oid = returnValue[i][0];
            part_number = returnValue[i][1];
            part_name = returnValue[i][2];
            masterKey = returnValue[i][99];
            isLastest = returnValue[i][100];
            
            //먼저 중복체크
            if ( !partDuplicateCheck(part_oid) ) {
            	part_no_check = part_number;
            	
                var tBody = document.getElementById("iPartTable");
                if(tBody != null){
                	var innerRow = tBody.insertRow();
                	if(masterKey != null) innerRow.className = masterKey;
                	
                    var innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM";
                    
                    if(isQulityDoc == 'Y'){
                        innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:warning($(this));return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                            + "<div style=\"display:none;\"><input name='iPartChk' type='checkbox' class='chkbox'></div><input type='hidden' name='partOid' value='"+ part_oid +"'><input type='hidden' name='partNumber' value='"+ part_number +"'>";
                    }else{
                    	
                    	var innerHTML = "<a href=\"#\" onclick=\"javascript:";
                    	if(masterKey != null){
                    		innerHTML += "$('." + masterKey + "').remove();return false;";
                    	}else{
                    		innerHTML += "$(this).parent().parent().remove();return false;";
                    	}
                    	
                    	innerHTML += "\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                            + "<div style=\"display:none;\"><input name='iPartChk' type='checkbox' class='chkbox'></div><input type='hidden' name='partOid' value='"+ part_oid +"'><input type='hidden' name='partNumber' value='"+ part_number +"'>";
                    	
                    	innerCell.innerHTML = innerHTML;
                    }
                    
                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM";
                    innerCell.innerText = part_number;
                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM0 text-left";
                    innerCell.innerText = part_name;
                    
                    if(isLastest != null && !isLastest){
                    	$(innerRow).hide();
                    }
                }
            }
            else {
                //alert("Duplicated Part ==>"+part_number);
            }
        }
    }
}

function partDuplicateCheck (poid) {
    //중복체크
    var tBody = document.getElementById("iPartTable");
    if(tBody != null){
    	var rowsLen = tBody.rows.length;
        if ( rowsLen > 0 ) {
            var primaryPart = document.frm.partOid;
            var partLen = primaryPart.length;
            if ( partLen ) {
                for ( var i = 0; i < partLen; i++ ) {
                    if ( primaryPart[i].value == poid ) {
                        return true;
                    }
                }
            }
            else {
                if ( primaryPart.value == poid ) {
                    return true;
                }
            }
        }
    }
    
    return false;
}

function isNull (str) {
    if ( str == null || str == "" ) {
        return true;
    }
    return false;
}

function insertFileLine () {
    //첨부파일 라인을 추가한다.
    var iFileTableOld = $("#iFileTableOld")[0];
    var innerRow = iFileTableOld.insertRow();
    innerRow.height = "27";
    var filePath = "filePath"+iFileTableOld.rows.length;
    var innerCell;
    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";
        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteL0";
            innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_fieldRO' style='width: 100%;'>";
        }
    }
}

function deleteFile(contentoid){
    document.frm.isFileDel.value += "/" + contentoid;
}

function setContents(returnValue){

    if ( typeof returnValue != "undefined" && returnValue.length > 0) {
        for ( var i = 0; i < returnValue.length; i++ ) {
            var contentoid = returnValue[i][0];
            var downURLStr = returnValue[i][1];
            var iconURLStr = returnValue[i][2];
            var name = returnValue[i][3];
            var fileSizeKB = returnValue[i][4];
            var iFileTableOld = $("#iFileTableOld")[0];
            var innerRow = iFileTableOld.insertRow();
            innerRow.height = "27";
            var innerCell;
            for ( var k = 0; k < 2; k++) {
                innerCell = innerRow.insertCell();
                innerCell.height = "23";
                if (k == 0) {
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();deleteFile('"+contentoid+"');return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                          + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
                } else if (k == 1) {
                    innerCell.className = "tdwhiteL0";
                    innerCell.innerHTML = "<a target='download' href='"+downURLStr+"'>"+iconURLStr+"</a>&nbsp;"+
                    "<a href='"+downURLStr+"' target='download'>"+name+"</a>&nbsp;(&nbsp;"+fileSizeKB+"&nbsp;)";
                }
            }
        }
    }
}

function doCancel(isPop,buttenView,isRefresh){
    var dOid = document.frm.docuOid;
    if ( isPop=="Y" ) {
        document.location.href="/plm/jsp/dms/ViewDocumentPop.jsp?oid="+dOid.value+"&buttenView="+buttenView+"&isRefresh="+isRefresh;
    }
    else {
        document.location.href="/plm/jsp/dms/ViewDocument.jsp?oid="+dOid.value+"&nowBlock=<%=SnowBlock%>&page=<%=Spage%>&cmd=search&documentNo=<%=SdocumentNo%>&divisionCode=<%=SdivisionCode%>&categoryCode=<%=ScategoryCode%>&documentName=<%=SdocumentName%>&authorStatus=<%=SauthorStatus%>&creatorName=<%=ScreatorName%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&isBuyerSummit=<%=SisBuyerSummit%>&buyerCodeStr=<%=SbuyerCodeStr%>&quality=<%=Squality%>&projectNo=<%=SprojectNo%>&projectName=<%=SprojectName%>&islastversion=<%=Sislastversion%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T";
    }
}

function doSave(isPop,isRefresh){
    document.getElementById("security").disabled = false;
    //저장버튼 클릭시 valcheck체크후 hidden값인 dRCheckPoint값과 buyerCodeStr값을 지정하고 multipart/form-data형태로 submit한다.
    if ( !valcheck() ) return;
    else {
        var d = document.frm;
        if ( d.dRPoint.value == "00" ) {
            d.dRCheckPoint.value = "00";
        }
        else {
            if ( isNull(d.dRPoint.value) ) {
                d.dRCheckPoint.value = "0";
            }
            else {
                d.dRCheckPoint.value = d.dRPoint.value;
            }
        }
        if ( d.buyerCode.disabled == false ) {
            var buystr="";
            var code = d.buyerCode.value;
            for ( var i = 0; i < code.split(",").length; i++ ) {
                buystr = buystr + "," + code.split(",")[i];
            }
            d.buyerCodeStr.value = buystr.substring(1);
            d.buyerCodeStr.value = d.buyerCode.value;
            //alert(d.buyerCodeStr.value);
        }
        else {
            d.buyerCodeStr.value = "";
        }
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
        if ( isPop=="Y" ) {
            d.action = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=updatePop&isRefresh="+isRefresh;
        }
        else {
            d.action = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=update";
        }
        showProcessing();
        disabledAllBtn();
        d.submit();
    }
}

function isNotDigit(str) {
    var pattern = /^[0-9]+$/;
    str = str.replace(".", "0");
    if ( !pattern.test(str) ){
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
    function valcheck () {
        var d = document.frm;
        
        if($("input[name=categoryCode]").length > 1){
            for(var i=0;i < d.categoryCode.length; i++){
                if(isNull(d.categoryCode[i].checked)){
                }else{
                    checked_code = d.categoryCode[i].value;
                }
            }
        }else{
            checked_code = d.categoryCode.value;
        }
        
        /* if(checked_code == "PD303066" || checked_code == "PD303072" || checked_code == "PD303001" || checked_code == "PD303017" || checked_code == "PD303034" ||checked_code == "PD303046" || checked_code == "PD303047"){
            if(isNull(d.attribute1.value.replace(/ /gi,""))){
                alert('품질문서번호는 반드시 입력해야 합니다.');
                return false;
            }
        }  */
        
        if ( isNull(checked_code) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01426") %><%--문서분류는 최종Level까지 선택해야 합니다.--%>');
            return false;
        }
        else{
            var flag = checkDocCategoryAjax(checked_code);
            if ( !flag ){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01426") %><%--문서분류는 최종Level까지 선택해야 합니다.--%>');
                return false;
            }
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
            if ( s.length > 160 ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01417") %><%--문서명은 160자를 초과할 수 없습니다--%>');
                return false;
            }
        }
        
        /*
        if(d.categoryCode.value == "PD302015" || d.categoryCode.value == "PD303063"){
            if(isNull(d.analysisCode.value)){
                alert("해석의뢰 문서를 반드시 입력해야 합니다.");
                return false;
            }
        }
        */
        
        if ( d.buyerCode.length > 10 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00842") %><%--고객사는 10개를 초과할 수 없습니다--%>');
            return false;
        }
        
        if(isNull(d.devDeptCode.value) && $("[name=isDeptACL]").val() == "true"){
            alert("부서조회권한을 반드시 입력해야 합니다.");
            return false;
        }
 
        if ( ($(':radio[name=isBuyerSummit]:checked').val() == '1')&&($('[name=buyerCode]').val() == '' )) {
          alert('<%=messageService.getString("e3ps.message.ket_message", "00839") %><%--고객사 제출자료 대상일 경우 고객사는 반드시 입력해야 합니다.--%>');

            return false;
        }
        if ( d.isDRCheckSheet.value == "t" ) {
            if ( isNull(d.dRPoint.value) ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00170") %><%--DR평가점수는 반드시 입력해야 합니다--%>');
                return false;
            }
            if ( isNotDigit(d.dRPoint.value) ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00169") %><%--DR평가점수는 반드시 숫자여야 합니다--%>');
                return false;
            }
            if ( d.dRPoint.value>100 ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00168") %><%--DR평가점수는 100 이하여야 합니다--%>');
                return false;
            }
            if ( d.dRPoint.value < 0 ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00167") %><%--DR평가점수는 0 이상이여야 합니다--%>');
                return false;
            }
        }
        <%if(primaryFile == null){%>
        if ( isNull(d.iFile0.value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02685") %><%--주첨부파일을 첨부하여 주십시오.--%>');
            return false;
        }
        <%}%>
        
        if("Y" == $("input[name=pubDateYn]").val()){
        	
        	if(isNull($("input[name=pubDate]").val())){
        		alert('발행일자를 입력해야 합니다.');
                return false;
        	}
			if(isNull($("select[name=pubCycle]").val())){
				alert('발행주기를 입력해야 합니다.');
				return false;
        	}
        }
        
        //포장사양서 부품체크
        if("PD303057" == $("input[name=categoryCode]").val() && $("#iPartTable tr").length == 0){
            alert("포장사양서인 경우 관련 부품을 필수로 입력해야 합니다.");
            return false;
        }
        
        if(<%=firstRegistrationStage.equals("2")%> && $("#iPartTable tr").length == 0){
            alert("양산단계의 문서일 경우 관련 부품을 등록하세요.");
            return false;
        }
        
        var strHTMLCode = fnGetEditorHTMLCode(false, 0);
        if ( strHTMLCode == "" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01429") %>문서설명은 반드시 입력해야 합니다.');
            return false;
        }
        
        var partNoList = new Array();
        $("input[name=partNumber]").each(function(){
        	var partNoValue = $(this).val();
        	partNoList.push(partNoValue.toString());
        });
        
        var param = {
        	currentDocNo : "<%=documentNo%>",
        	categoryCode : $("input[name=categoryCode]").val(),
        	relatedPartOid : $("input[name=relatedPartOid]").val(),
        	partNoList : partNoList
        }
         
        var data = ajaxCallServer("/plm/ext/dms/chkDupMatPart", param, null, false);
        var dupList = data.dupList;
        
        if(data.isDupMatChk && partNoList.length == 0 && $("input[name=relatedPartOid]").val() == null){
        	alert("최소 하나 이상의 부품이 연계되어야 합니다.");
        	return false;
        }
        
        if(dupList.length > 0){
        	
        	var msg = "동일 자재가 다른 문서에 기 등록되어 있습니다.\n";
    		
    		for(var i = 0; i < dupList.length; i++){
    			msg += dupList[i] + "\n";
    		}
    		
    		msg += "중복 등록 시 결재가 불가하오니 조치 후 진행 바랍니다.";
    		
    		alert(msg);

    		if(<%=cmd.equals("revice")%>) return false;
        }
        
        return true;
    }
//-->
</script>
<%@include file="/jsp/common/processing.html" %>
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
                $("#"+targetId).append("<option value='"+this.categoryCode+this.isDrCheck+"'>"+ this.categoryName +"</option>");
            });
        }
    });
}


function setDevDocCategory(checkedNode){
    var nodeIdStr='', nodeNameStr='';
    for(var i=0; i < checkedNode.length; i++){
            if(i == checkedNode.length - 1){
                    nodeIdStr += checkedNode[i].id;
                    nodeNameStr += checkedNode[i].name;
            }else{
                    nodeIdStr += checkedNode[i].id+',';     
                    nodeNameStr += checkedNode[i].name+',';
            }
    }
    setDevDocCategoryAjax(nodeIdStr, nodeNameStr);
}

function setDevDocCategoryAjax(nodeIdStr, nodeNameStr){
	
    $.ajax({
        url : "/plm/ext/dms/setDevDocCategory.do",
        type : "POST",
        data : {
            devDocCagegoryCode : nodeIdStr
        },
        dataType : 'json',
        async : true,
        success : function(data) {

        	var isDRCheckSheet = eval(data.isDRCheckSheet);
            var isDeptACL = data.attribute1 == "Y";
            
            if(isDRCheckSheet) document.frm.isDRCheckSheet.value = "t";
            else document.frm.isDRCheckSheet.value = "f";
            var tBody = document.getElementById("dRPointTable");
            if ( document.frm.isDRCheckSheet.value == "f" ) {
                document.getElementById("dRPointTable").style.display = "none";
                document.frm.dRPoint.value = 0;
            }
            else{
                document.getElementById("dRPointTable").style.display = "";
            }

            if(nodeIdStr == "PD302015" || nodeIdStr == "PD303063"){
                $("#analysisTR").show();
            }else{
                $("#analysisTR").hide();
                $("[name=analysisCode]").val('');
                $("[name=analysisCodeTxt]").val('');
            }
            
            $("[name=isDeptACL]").val(isDeptACL);
            if(isDeptACL) $(".DEPTACLTR").show();
            else          $(".DEPTACLTR").hide();
            
            var param = {
        			categoryCode : nodeIdStr
        	}
        	
       		var securityYn = data.attribute4;
       		var pubDateYn = data.attribute6;
       		
       		if(pubDateYn == "Y"){
       			$("input[name=pubDateYn]").val("Y");
       			$("#pubDateTR").show();
       		}else{
       			$("input[name=pubDateYn]").val("N");
       			$("#pubDateTR").hide();
       		}
       		
       		if(securityYn == "Y"){
       			$("#security").val("S2");
       		}else{
       			$("#security").val("S1");
       		}
       		
       		if(nodeIdStr == "PD303148"){
                
                $("#costCommentTR").show();
            }else{
                
                $("#costCommentTR").hide();
            }
            //setSecurityDocu(nodeIdStr);
            //$('[name=categoryCode]').val(nodeIdStr);
            //$('[name=categoryCodeTxt]').val(nodeNameStr);
        }
    });
}

function resetCode(){
    $(".DEPTACLTR").hide();
}

function setSecurityDocu(categoryCode){
    var SecurityYn = getCategoryIsSecurity(categoryCode);
    
    if(SecurityYn == 'Y'){
        $("#security").val("S2");
    }else{
        $("#security").val("S1");
    }
}

function getCategoryIsSecurity(categoryCode){
    
    var rtn = '';
    $.ajax({
        url : "/plm/ext/dms/getCategoryIsSecurity.do",
        type : "POST",
        data : {
            CagegoryCode : categoryCode
        },
        dataType : 'json',
        async : false,
        success : function(data) {
            rtn = data;
        }
    });
    return rtn;
}

function selectDevDocCategoryCallBackFn(returnValue){
    
    $('[name=categoryCode]').val(returnValue[0].id);
    $('[name=categoryCodeTxt]').val(returnValue[0].name);
    setDevDocCategoryAjax($('[name=categoryCode]').val(), $('[name=categoryCodeTxt]').val());
}

function analysisCallBackFn(returnValue){
    $('[name=analysisCode]').val(returnValue[0][0]);
    $('[name=analysisCodeTxt]').val(returnValue[0][1]);
}

function selectMultiSubContractor(returnValue){
    var nodeIdStr='', nodeNameStr='';
    for(var i=0; i < returnValue.length; i++){
            if(i == returnValue.length - 1){
                    nodeIdStr += returnValue[i][0];
                    nodeNameStr += returnValue[i][2];
            }else{
                    nodeIdStr += returnValue[i][0]+',';     
                    nodeNameStr += returnValue[i][2]+',';
            }
    }
    $('[name=buyerCode]').val(nodeIdStr);
    $('[name=buyerCodeTxt]').val(nodeNameStr);
}

function checkDocCategoryAjax(categoryCode){
    var rtn = false;
    $.ajax({
        url : "/plm/ext/dms/doValidateDocCategory.do",
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

function addProjectProductInfoAjax(projectoid){
    $.ajax({
        url : "/plm/ext/dms/addProjectProductInfo.do",
        type : "POST",
        data : {
            projectoid : projectoid
        },
        dataType : 'json',
        async : false,
        success : function(data) {
            if(data != null){
                if(data != null && data.length > 0){
                    var partArrs = new Array();
                    for(var i=0; i < data.length; i++){
                        var partArr = new Array();
                        partArr[0] = data[i].partoid;
                        partArr[1] = data[i].partnumber;
                        partArr[2] = data[i].partname;
                        partArrs[i] = partArr;
                    }
                    setPartInfoSbl(partArrs);
                }
            }
        }
    });
}

$(document).ready(function() {
    var strHTMLCode = document.forms[0].webEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);
    
    // suggest
    SuggestUtil.bind('PROJECTDOCTYPE', 'categoryCodeTxt', 'categoryCode');
    SuggestUtil.bind('CUSTOMER', 'buyerCodeTxt', 'buyerCode');
    SuggestUtil.bind('DEPTUSER', 'devDeptName', 'devDeptCode');
    
    if("true" != $("[name=isDeptACL]").val()) $(".DEPTACLTR").hide();
    
    $("[name=categoryCode]").change(function() {
        setDevDocCategoryAjax($('[name=categoryCode]').val(), $('[name=categoryCodeTxt]').val());
    });
    setDevDocCategoryAjax($('[name=categoryCode]').val(), $('[name=categoryCodeTxt]').val());
    
   	CalendarUtil.dateInputFormat('pubDate');
    
    /* 
    if($("[name=isBuyerSummit]:checked").val() == "2"){
        $("#selectMultiSubContractorDiv").hide();
    } else {
        $("#selectMultiSubContractorDiv").show();
    }
     */
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
g_nEditorWidth = 943;
g_nEditorHeight = 340;
//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<!-- 이노디터 JS Include End -->
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
<script type="text/javascript">
//<![CDATA[
// -- body의 onload에 이벤트를 직접 설정하기 힘든 경우 아래처럼 이벤트 추가
if ( g_browserCHK.ie ) {
    window.addEventListener("onload", fnSetEditorHTML);
}
else if ( g_browserCHK.ff || g_browserCHK.wk ) {
    window.addEventListener("load", fnSetEditorHTML, false);
}

$(document).ready(function(){
	CommonUtil.setNumberField('dRPoint',false);
});
//]]>
</script>
</head>
<body onload="init()">
<form name="frm" method="post" >
<input type="hidden" name="cmd" value="<%=cmd%>">
<input type="hidden" name="docuOid" value="<%=oid%>">
<input type="hidden" name="isFileDel" value="0">
<input type="hidden" name="isDRCheckSheet" value="<%=isDRCheckSheet%>">
<input type="hidden" name="isDeptACL" value="<%=docu.getAttribute7()%>">
<input type="hidden" name="dRCheckPoint">
<input type="hidden" name="buyerCodeStr">
<input type="hidden" name="partOid" value="0">
<input type="hidden" name="projectOid" value="0">
<input type="hidden" name="outputOid" value="<%=outputOid%>">
<input type="hidden" name="analysisCode" value="<%=analysisCode%>">
<input type="hidden" name="pubDateYn" value="<%=pubDateYn%>">

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
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%if(!"".equals(qualityNo)){%>품질표준문서<%}else{%><%=messageService.getString("e3ps.message.ket_message", "00638") %><%--개발산출문서--%><%} %> <%if ( cmd.equals("revice") ) {%><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%><%} else {%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
       </tr>
        <tr>
          <td class="space10"></td>
        </tr>
       </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave('<%=isPop%>','<%=isRefresh%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel('<%=isPop%>','<%=buttenView%>','<%=isRefresh%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
      <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
        <colgroup><col width="15%"><col width="35%"><col width="15%"><col width="35%"></colgroup>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td colspan="3" class="tdwhiteL0"><%=documentNo%><input type="hidden" name="attribute1" value=""/></td>
       </tr>
       <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td colspan="3" class="tdwhiteL0"><%if(cmd.equals("revice")){%><%=Integer.parseInt(versionInfo)+1%><%}else{%><%=versionInfo%><%}%></td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%><span class="red">*</span></td>
            <td colspan="3" class="tdwhiteL0">
            
        	<%
            
        	    if(docCatePath!=null && docCatePath.length()>0) {
        		  if(isEditable){
        	    
            %>
                <input type="text" name="categoryCodeTxt" class="txt_field" style="width: 50%" value="<%= docCatePath.split("/")[docCatePath.split("/").length-1] %>">
            <%
        		  }else{
            %>
                <input type="text" name="categoryCodeTxt" class="txt_fieldRO" style="width: 50%" value="<%= docCatePath.split("/")[docCatePath.split("/").length-1] %>" disabled="disabled" readonly>
            <%		      
        		  }
        		      
                }else {
            %>
                <input type="text" name="categoryCodeTxt" class="txt_field" style="width: 50%" value="">
            <%
                }
        	%>
        	    <input type="hidden" name="categoryCode" value="<%=categoryCode%>">
        	    <%if(isEditable){ %>
                <a href="javascript:SearchUtil.selectOneDevDocCategory('selectDevDocCategoryCallBackFn');"> <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('categoryCodeTxt','categoryCode');resetCode();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                <%} %>        	
        	
            </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0"><input type="text" name="documentName" class="txt_field" id="textfield2" style="width:95%" value="<%=documentName%>"></td>
        </tr>
        <%//if(!"".equals(analysisNo)){ %>
        <%if("PD302015".equals(categoryCode3) || "PD303063".equals(categoryCode3)){%>
        <tr id="analysisTR">
            <td class="tdblueL">해석의뢰문서 <span class="red">*</span></td>
            <td colspan="3" class="tdwhiteL0"><input type="text" name="analysisCodeTxt" class="txt_field" style="width: 50%;" value="<%=analysisNo %>" readonly><a href="javascript:SearchUtil.selectAnalysisDocument(analysisCallBackFn);"> <img src="/plm/portal/images/icon_5.png" border="0"></a> <a href="javascript:CommonUtil.deleteValue('analysisCodeTxt','analysisCode');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
        </tr>
        <%} %>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02858") %><%--최초 등록시점--%></td>
          <%if(firstRegistrationStage.equals("1")){%>
          <td colspan="3" class="tdwhiteL0">
            <%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
          <%}else{%>
          <td colspan="3" class="tdwhiteL0">
            <%=messageService.getString("e3ps.message.ket_message", "02094") %><%--양산단계--%></td>
          <%}%>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00830") %><%--고객 제출자료--%><span class="red">*</span></td>
          <td class="tdwhiteL">
            <input name="isBuyerSummit" type="radio" class="Checkbox" value="2" onClick="disableBuyerCode()" <%=(isBuyerSummit.equals("2"))?"checked":"" %>>
              <%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input name="isBuyerSummit" type="radio" class="Checkbox" value="1" onClick="enableBuyerCode()" <%=(isBuyerSummit.equals("1"))?"checked":"" %>>
              <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
          </td>
          <td rowspan="2" class="tdblueL" id="customerText"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%><%if(isBuyerSummit.equals("1")){ %><span class="red">*</span><%} %></td>
          <td rowspan="2" height="30" class="tdwhiteL0">
          <%
             String bName="";
             if(StringUtil.checkString(buyerCode)){
               String[] buyerCodes = buyerCode.split(",");
               for(String bcode : buyerCodes){
          	     if(bcode.indexOf("NumberCode") == -1){
          		   bName += "," + bcode;
                   }
          	     else{
          		   NumberCode nCode = (NumberCode)CommonUtil.getObject(bcode);
                     bName +=  "," + nCode.getName();
                   }
               }
               bName = bName.substring(1);
             }
          %>
            <div id="selectMultiSubContractorDiv">
              <input type="text" name="buyerCodeTxt" class="txt_field" style="width: 50%" value="<%= bName %>">
              <input type="hidden" name="buyerCode" value="<%= StringUtil.checkNull(buyerCode) %>">
              <a href="javascript:SearchUtil.selectMultiSubContractor('selectMultiSubContractor');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
              <a href="javascript:CommonUtil.deleteValue('buyerCode','buyerCodeTxt');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </div>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01530") %><%--보안등급--%><span class="red">*</span></td>
          <td class="tdwhiteL">
          <select name="security" id="security" style="width: 95%">
              <%
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "SECURITYLEVEL");
              numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

              for ( int i=0; i<numCode.size(); i++ ) {
              %>
              <option value="<%=numCode.get(i).get("code") %>" <%if(security.equals(numCode.get(i).get("code"))){%>selected<%}%>><%=numCode.get(i).get("value")%></option>
              <%
              }
              %>
          </select>
          </td>
        </tr>
        <tr class="DEPTACLTR">
            <td class="tdblueL">부서조회권한<span class="red">*</span></td>
            <td class="tdwhiteL">
            <%
                String devDeptCode = StringUtil.checkNull(docu.getAttribute4());
            
                Department dept = null;
                
                if(StringUtils.isNotEmpty(devDeptCode)){
                    dept = DepartmentHelper.manager.getDepartment(devDeptCode);
                }
                
                String devDeptName = "";
                if(dept != null) devDeptName = dept.getName();
                
            %>
                <input type="text" name="devDeptName" class="txt_field" style="width: 30%" value="<%=devDeptName %>" > 
                <input type="hidden" name="devDeptCode" value="<%=devDeptCode%>"> 
                <a href="javascript:SearchUtil.addAllDepartment('devDeptCode', 'devDeptName','y');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('devDeptCode','devDeptName');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        
            <td class="tdblueL">사용자조회권한</td>
            <td class="tdwhiteL" colspan="2">
                <select name="duty" id="duty" style="width: 70%">
                  <option value="">선택</option>
                  <%
                  
                      String dutyCode = StringUtil.checkNull(docu.getAttribute5());
                        Vector dutyNameList = CompanyState.dutyNameList;
                        Vector dutyCodeList = CompanyState.dutyCodeList;
                      for (int i = 0; i < dutyCodeList.size(); i++) {
                          if(!"고문".equals(dutyNameList.get(i)) && !"감사".equals(dutyNameList.get(i))){
                  %>
                          <option value="<%=dutyCodeList.get(i)%>" <%if(dutyCode.equals((String)dutyCodeList.get(i)))out.print("selected");%>  ><%=dutyNameList.get(i)%></option>
                  <%
                          }
                      }
                  %>
                </select>
            </td>
        </tr>
        <tr id="quiltyTTR">
            <td class="tdblueL">품질문서번호<span class="red">*</span></td>
            <td colspan="3" class="tdwhiteL0">
                <input type="text" name="relatedPart" class="txt_fieldRO" style="width: 30%" readonly>
                <input type="hidden" name="relatedPartOid">
                <a href="javascript:SearchUtil.selectOnePart('selectPartAfterFunc','pType=F,H,R,W');showProcessing();">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('relatedPart','relatedPartOid');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        <tr id="pubDateTR" style="display: none">
			<td class="tdblueL">
				발행일자<span class="red">*</span>
			</td>
			<td colspan="3" class="tdwhiteL0">
				<input type="text" name="pubDate" id="pubDate" class="W90 txt_field" value="<%=pubDate%>">
				<a href="javascript:CommonUtil.deleteValue('pubDate');">
				<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
				<select name="pubCycle">
					<option value="">주기 (필수)</option>
					<option value="3">3개월</option>
					<option value="6">6개월</option>
					<option value="9">9개월</option>
					<option value="12">12개월</option>
				</select>
				<script>$("select[name=pubCycle]").val("<%=pubCycle%>");</script>
			</td>
		</tr>
        <tr id="dRPointTable" <%if ("f".equals(isDRCheckSheet)) out.print("style=\"display: none;\"");%>>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00166") %><%--DR평가점수--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0"><input type="text" name="dRPoint" value="<%=dRCheckPoint%>" class="txt_field"  style="width:50px" id="textfield3" ></td>
        </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03046")%><%--프로젝트--%><%if(firstRegistrationStage.equals("1")){ %><span class="red">*</span><%}%></td>
              <td colspan="3" class="tdwhiteL0">
                <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr class="headerLock3">
                      <td>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                          <colgroup>
                            <col width="25">
                            <col width="200">
                            <col width="350">
                            <col width="*">
                          </colgroup>
                          <tr>
                            <td class="tdgrayM"><a href="#" onclick="javascript:selProjectNo();return false;"><img src="/plm/portal/images/b-plus.png"></a></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03114")%><%--프로젝트번호--%></td>
                            <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로젝트명--%></td>
                            <td class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "00496")%><%--Task명--%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                  <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                    <colgroup>
                      <col width="25">
                      <col width="200">
                      <col width="350">
                      <col width="*">
                    </colgroup>
                    <tbody id="iProjTable">
                    </tbody>
                  </table>
                </div>
                <script type="text/javascript">
                  var projArr = new Array();
                  var projIdx = 0;
<%
            String rank ="";
            tempRanke ="";
            ProjectOutput po;
            int outputCount = 0;
            HashMap<String, E3PSProject> pjtMap = new HashMap<String, E3PSProject>();
            QueryResult r3 = PersistenceHelper.manager.navigate(docu, "output" , KETDocumentOutputLink.class);
            if ( r3.hasMoreElements() ) {
                while ( r3.hasMoreElements() ) {
                    po = (ProjectOutput)r3.nextElement();
                    outputOid = CommonUtil.getOIDString(po);
                    E3PSTask task = (E3PSTask)po.getTask();
                    E3PSProject project = (E3PSProject)task.getProject();
                    project = ProjectHelper.getLastProject(project.getMaster());
                    if(project.getRank()!=null){
                    	rank = project.getRank().getName();
                    }
//                    rank= project.getRank()==null?"":project.getRank().getName();
                    if ( outputCount == 0 ) {
                        tempRanke = rank;
                    }

                    if ( tempRanke != rank && tempRanke.equals("S1") ) {
                        tempRanke ="S1";
                    }

                    pjtNo = StringUtil.checkNull(project.getPjtNo());
                    pjtName = StringUtil.checkNull(project.getPjtName());
                    taskName = StringUtil.checkNull(task.getTaskName());
                    pjtMap.put(CommonUtil.getOIDString(project), project);
%>
                  var subArr = new Array();
                  subArr[0] = '<%= CommonUtil.getOIDString(project) %>'; //project oid
                  subArr[1] = '<%= StringUtil.checkNull(project.getPjtNo()) %>'; //project_no
                  
                  subArr[2] = '<%=StringUtil.checkNull(project.getPjtName()).replaceAll("'", "^quot^")%>'; //project name
                  subArr[2] = subArr[2].replace('^quot^', "\'");
                  
                  subArr[9] = '<%= rank %>'; //rank
                  subArr[14] = '<%= StringUtil.checkNull(task.getTaskName()) %>'; //task_name
                  projArr[projIdx] = subArr;
                  projIdx++;
<%
                }
            }
            String projectOid = "";
            E3PSProject project = null;
            qr = PersistenceHelper.manager.navigate(docu, "project" , KETDocumentProjectLink.class );
            while ( qr.hasMoreElements() ) {
                project = (E3PSProject) qr.nextElement();
                projectOid = CommonUtil.getOIDString(project);
                project = ProjectHelper.getLastProject(project.getMaster());
                pjtNo = StringUtil.checkNull(project.getPjtNo());
                pjtName = StringUtil.checkNull(project.getPjtName());
                taskName = "";

                rank = project.getRank()==null?"":project.getRank().getName();
                if ( outputCount == 0 && tempRanke.length() == 0 ) {
                    tempRanke = rank;
                }
                if ( tempRanke != rank && tempRanke.equals("S1") ) {
                    tempRanke ="S1";
                }
                if(pjtMap.containsKey(projectOid)){
                    continue;
                }
                else{
                    pjtMap.put(projectOid, project);
                }
%>
                var subArr = new Array();
                subArr[0] = '<%= CommonUtil.getOIDString(project) %>'; //project oid
                subArr[1] = '<%= StringUtil.checkNull(project.getPjtNo()) %>'; //project_no
                subArr[2] = '<%=StringUtil.checkNull(project.getPjtName()).replaceAll("'", "^quot^")%>'; //project name
                subArr[2] = subArr[2].replace('^quot^', "\'");
                subArr[9] = '<%= project.getRank().getName() %>'; //rank
                subArr[14] = '<%= taskName %>'; //task_name
                projArr[projIdx] = subArr;
                projIdx++;
<%
            }
%>
              setProject(projArr);
            </script>
          </tr>
        
          
        <tr id="costCommentTR" style="display: none">
            <td class="tdblueL">원가 비고사항</td>
            <td class="tdwhiteL0" colspan="3" >
                <textarea name="costComment" class='txt_field' style="height:70px;width:99%;"><%=costComment %></textarea>
            </td>
        </tr>
        
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01564") %><%--부품--%></td>
          <td colspan="3" class="tdwhiteL0">
            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr class="headerLock3">
                  <td>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                      <colgroup>
                        <col width="25">
                        <col width="200">
                        <col width="*">
                      </colgroup>
                      <tr>
                        <td class="tdgrayM"><a href="#" onclick="javascript:insertPartLine();return false;"><img src="/plm/portal/images/b-plus.png"></a></td>
                        <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569")%><!-- 부품번호 --></td>
                        <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586")%><!-- 부품명 --></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
              <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                <colgroup>
                  <col width="25">
                  <col width="200">
                  <col width="*">
                </colgroup>
                <tbody id="iPartTable">
                </tbody>
              </table>
            </div>
            <script>
              var partArrs = new Array();
              var p_idx = 0;
<%
			QuerySpec qs = new QuerySpec();
			qs.addClassList(WTPart.class, true);
			qs.addClassList(KETDocumentPartLink.class, false);
			
			SearchUtil.setOrderBy(qs, WTPart.class, 0, "versionInfo.identifier.versionSortId", true);
			qr = PersistenceHelper.manager.navigate(docu, KETDocumentPartLink.PART_ROLE, qs, true);
			
			Map<String, WTPart> partMap = new HashMap<String, WTPart>();
			List<WTPart> nlPartList = new ArrayList<WTPart>();
			
			while (qr.hasMoreElements()) {
			    WTPart part = (WTPart) qr.nextElement();
			    String partNo = part.getNumber();
			    if(!partMap.containsKey(partNo)) partMap.put(partNo, part);
			    else                             nlPartList.add(part);
			}
			TreeMap<String, WTPart> treePartMap = new TreeMap<String, WTPart>(partMap);
			Set<String> st = treePartMap.keySet();
			Iterator<String> it = st.iterator();
			
			//최신버전
			while(it.hasNext()){
			    String partNo = it.next();
			    WTPart part = partMap.get(partNo);
			    String partOid = CommonUtil.getOIDString(part);
			    String masterKey = CommonUtil.getOIDLongValue2Str(part.getMaster());
			%>
                var partArr = new Array();
                partArr[0] = '<%=partOid%>';
                partArr[1] = '<%=part.getNumber()%>';
                partArr[2] = '<%=part.getName().replaceAll("'", "^quot^")%>';
                partArr[2] = partArr[2].replace('^quot^', "\'");
                partArr[99] = '<%=masterKey%>';
                partArr[100] = true;
                
                partArrs[p_idx] = partArr;
                p_idx++;
            <%}
            
			//이전버전
            for(WTPart part : nlPartList){
                String partOid = CommonUtil.getOIDString(part);
                String masterKey = CommonUtil.getOIDLongValue2Str(part.getMaster());
            %>
                var partArr = new Array();
                partArr[0] = '<%=partOid%>';
                partArr[1] = '<%=part.getNumber()%>';
                partArr[2] = '<%=part.getName().replaceAll("'", "^quot^")%>';
                partArr[2] = partArr[2].replace('^quot^', "\'");
                partArr[99] = '<%=masterKey%>';
                partArr[100] = false;
                
                partArrs[p_idx] = partArr;
                p_idx++;
            <%}%>
              setPartInfoSbl(partArrs, true);
            </script>
          </td>
        </tr>

        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667") %><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0">
            <%if(primaryFile != null) {%>
            <a target='download' href='<%= primaryFile.getDownURLStr()%>'><%= primaryFile.getIconURLStr()%></a>&nbsp;
            <a href='<%=primaryFile.getDownURLStr()%>' target='download'><%=primaryFile.getName()%></a>&nbsp; ( <%=primaryFile.getFileSizeKB()%> )
            <%} %>
            <input name="iFile0" type="file" class="txt_fieldRO" style="width:100%">
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
          <td colspan="3" class="tdwhiteL0">
            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr class="headerLock3">
                  <td>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                      <tr>
                        <td width="25" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine();return false;"><img src="/plm/portal/images/b-plus.png"></a></td>
                        <td width="*" class="tdgrayM0">파일명</td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
              <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                <col width="25">
                <col width="*">
                <tbody id="iFileTableOld">
                </tbody>
              </table>
            </div>
            <script>
            var contentArrs = new Array();
            var contentArrs_idx = 0;
            <%
            ArrayList<ContentDTO> contents = KETContentHelper.manager.getSecondaryContents(docu);
            for(ContentDTO content : contents){
            %>
            var contentArr = new Array();
            contentArr[0] = "<%= content.getContentoid()%>";
            contentArr[1] = "<%= content.getDownURLStr()%>";
            contentArr[2] = "<%= content.getIconURLStr()%>";
            contentArr[3] = "<%= content.getName()%>";
            contentArr[4] = "<%= content.getFileSizeKB()%>";
            contentArrs[contentArrs_idx] = contentArr;
            contentArrs_idx++;
            <%
            }
            %>
            setContents(contentArrs);
            </script>
          </td>
        </tr>
        <tr>
          <td colspan="4" style="width:100%; height:500;">
<!--              <textarea name="documentDescription" class="txt_field" id="textfield3" style="width:640; height:96%"><%=documentDescription%></textarea>-->
                <textarea name="documentDescription" rows="0" cols="0" style="display:none"><%=documentDescription%></textarea>
                <textarea name="webEditorText" rows="0" cols="0" style="display:none"><%=webEditorText%></textarea>
                <!-- Editor Area Container : Start -->
                <div id="EDITOR_AREA_CONTAINER"></div>
                <!-- Editor Area Container : End -->
          </td>
        </tr>
      </table></td>
  </tr>
</table>
        <!-- download target iframe -->
        <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px solid #fff;"></iframe>
</form>
<script>
function init () {
    var tempRank ="<%=tempRanke%>";
    var security ="<%=securityCode%>";
    if ( tempRank =="S2" ) {
        document.getElementById("security").disabled = true;
    }
    else {
        if ( security =="S2" ) {
            document.getElementById("security").disabled = true;
        }
    }
}

</script>
</body>
</html>
