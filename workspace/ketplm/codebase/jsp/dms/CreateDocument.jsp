<%@page import="e3ps.groupware.company.CompanyState"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.pds.StatementSpec"%>
<%@page import="e3ps.dms.beans.DMSUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page
  import="java.util.List,
                java.util.HashMap,
                java.util.Map,
                java.util.ArrayList,
                java.util.Vector,
                e3ps.dms.service.KETDmsHelper,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.common.code.*,
                e3ps.common.query.*,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper,
                wt.query.QueryException,
                wt.query.QuerySpec,
                wt.query.SQLFunction,
                wt.query.SearchCondition,
                e3ps.dms.entity.KETDocumentCategory,
                org.apache.commons.lang.StringUtils"
%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

    String categoryCode1 = "";
    String categoryCode2 = "";
    String categoryCode3 = "";
    String categoryCode = "";
    String analysisCode = "";
    String docCatePath = "";
    String categoryName = messageService.getString("e3ps.message.ket_message", "01445")/*미등록되어 있습니다*/;
    String docTypeCode = null;
    String isDRCheckSheet = "f";
    String tmpStr = null;
    String docuTitle = "";
    String firstRegistrationStage = "1";
    String pjtType = "";
    String pjtNo = "";
    String pjtName = "";
    String taskName = "";
    String mPartName = "";
    String mPartNo = "";
    String qulityYn = "";
    String pubDateYn = "";

    boolean registrationStage = false;
    if (firstRegistrationStage.equals("1")) {
		registrationStage = true;
    }

    String isBuyer = "f";
    Vector<NumberCode> vt = null;

    String divisionCode = "";
    if (CommonUtil.isMember("전자사업부")) {
		divisionCode = "ER";
    } else if (CommonUtil.isMember("자동차사업부")) {
		divisionCode = "CA";
    } else {
		divisionCode = "SU";
    }

    String isPop = request.getParameter("isPop");
    isPop = StringUtil.checkNull(isPop);

    //분류체계 중 개발산출문서를 선택하여 화면에 나타낸다.
    KETDocumentCategory docCate = null;

    // 1 Level 분류체계 찾기
    /* parameter.clear();
    parameter.put("docTypeCode", "PD");
    parameter.put("locale", messageService.getLocale().toString());
    parameter.put("parentCode", "ROOT");

    List<Map<String, Object>> categoryList = DMSUtil.getDocumentCategory(parameter);
    if (categoryList.size() > 0) {
    	categoryCode1 = categoryList.get(0).get("categoryCode").toString();
    	categoryName = categoryList.get(0).get("categoryName").toString();
    } */

    //대외비변수
    // 프로젝트 S 이면 문서는 대내비 S2
    String security = request.getParameter("security");
    if (security == null) {
		security = "";
    } else if (security.equals("S")) {
		security = "S2";
    }

    //WBS TASK로부터의 문서등록여부
    //String outputOid =  request.getParameter("projectOid");
    String[] outputOids = request.getParameterValues("projectOid");

    E3PSProject project = null;
    E3PSProjectData projectData = null;
    ProjectOutput[] outputs = null;
    if (outputOids == null) {
		//outputOid = "0";
		categoryCode2 = "0";
		categoryCode3 = "0";
    } else {
		outputs = new ProjectOutput[outputOids.length];
		for (int i = 0; i < outputOids.length; i++) {
		    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(outputOids[i]);
		    outputs[i] = output;
		}
		ProjectOutput po = (ProjectOutput) outputs[0];
		E3PSTask task = (E3PSTask) po.getTask();
		project = (E3PSProject) task.getProject();
		projectData = new E3PSProjectData(project);

		//WBS TASK로부터의 문서등록일 경우 데이터입력
		pjtNo = StringUtil.checkNull(project.getPjtNo());
		pjtName = StringUtil.checkNull(project.getPjtName());
		taskName = StringUtil.checkNull(task.getTaskName());
		String outputKeyType = StringUtil.checkNull(po.getOutputKeyType());

		QuerySpec query = new QuerySpec(KETDocumentCategory.class);
		query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", outputKeyType), new int[] { 0 });
		QueryResult qr = PersistenceHelper.manager.find(query);

		if (qr.hasMoreElements()) {
		    docCate = (KETDocumentCategory) qr.nextElement();
		    
		    if(StringUtils.isNotEmpty(docCate.getAttribute2()) && StringUtils.isEmpty(docCate.getAttribute3())){
			    qulityYn = "Y";
		    }else{
			    qulityYn = "N";
		    }
		    
		    if("Y".equals(docCate.getAttribute6())){
		    	pubDateYn = "Y";
		    }else{
		    	pubDateYn = "N";
		    }
		    
		    categoryCode3 = docCate.getCategoryCode();
		    categoryCode2 = docCate.getParentCategoryCode();
		    //문서에 링크된 분류체계로 DR평가점수 필요여부를 결정한다.
		    Boolean isDRCheck = docCate.getIsDRCheckSheet();
		    if (isDRCheck == true) {
			isDRCheckSheet = "t";
		    } else {
			isDRCheckSheet = "f";
		    }

		    Boolean isAPQP = docCate.getIsAPQP();
		    Boolean isANPQP = docCate.getIsANPQP();
		    Boolean isESIR = docCate.getIsESIR();
		    Boolean isISIRCar = docCate.getIsISIRCar();
		    Boolean isISIRElec = docCate.getIsISIRElec();
		    Boolean isPSO10 = docCate.getIsPSO10();
		    Boolean isSYMC = docCate.getIsSYMC();
		    //분류체계에 따라 고객사는 자동 지정한다.
		    if ((isESIR == true) || (isISIRCar == true) || (isISIRElec == true)) {
			isBuyer = "t";
			vt = new Vector<NumberCode>();
			for (ProjectOutput output : outputs) {
			    E3PSTask task2 = (E3PSTask) po.getTask();
			    project = (E3PSProject) task2.getProject();

			    QuerySpec psspec = new QuerySpec(ProjectSapSubcontractorLink.class);
			    SearchUtil.appendEQUAL(psspec, ProjectSapSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(project), 0);
			    QueryResult psresult = PersistenceHelper.manager.find((StatementSpec) psspec);

			    while (psresult != null && psresult.hasMoreElements()) {
				ProjectSapSubcontractorLink link = (ProjectSapSubcontractorLink) psresult.nextElement();
				if (link != null) {
				    vt.add(link.getSapsubcontractor());
				}
			    }
			}
		    }

		    //문서에 링크된 분류체계로 분류체계path를 구한다.
		    docCatePath = KETDmsHelper.service.selectCategoryPath(categoryCode3);
		    docCatePath = docCatePath.substring(1);
		    categoryCode = categoryCode3;
		}
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title><%=messageService.getString("e3ps.message.ket_message", "01411")%><%--문서등록--%></title>
<%@include file="/jsp/common/processing.html"%>
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js?ver=0.1"></script>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<%@include file="/jsp/common/multicombo.jsp"%>
<style type="text/css">
<!--
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 5px;
}

img {
	vertical-align: middle;
}

input {
	vertical-align: middle;
	line-height: 22px;
}
-->
</style>

<script type="text/javascript">
<!--
    function isNull (str) {
        if ( str==null || str=="" ) {
            return true;
        }
        return false;
    }

    function changeCategory2 () {

        $("#category3").empty().data('options');

        //2단계 분류체계변경시 CateSelect.jsp를 호출하여 3단계를 화면에 나타내준다.
        numCodeAjax("PD", $("#category2").val(), "category3");
    }

    function changeCategory3 (){
        //3단계 분류체계변경시 선택된 분류체계를 categoryCode에 저장하고 DR평가점수 필요여부를 체크한다.
        var fm = document.frm
        var tStr = $("#category3").val();

        fm.categoryCode.value =tStr.substring(0,8);
        fm.isDRCheckSheet.value = tStr.substring(8,9);

        var tBody = document.getElementById("dRPointTable");
        if ( fm.isDRCheckSheet.value == "f" ) {
            document.getElementById("dRPointTable").style.display = "none";
        }
        else{
            document.getElementById("dRPointTable").style.display = "";
        }
    }

    function changeDivisionCode () {
        //사업부 변경시 divisionCodeStr에 저장한다.
        var fm = document.frm
        var tStr = fm.divisionCode.options[fm.divisionCode.selectedIndex].value;
        fm.divisionCodeStr.value =tStr;
    }

    function insertCustomLine () {
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
                    if ( fm.buyerCode.options[j].value == subArr[0] ) {
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
/* 
    function enableBuyerCode () {
        //고객제출자료 여부에 따라 고객사select 를 enable한다.
        var fm = document.frm;
        fm.buyerCode.disabled == false;
        fm.buyerCodeTxt.disabled = false;
        $("#selectMultiSubContractorDiv").show();
        $("[name=buyerCode]").attr("disabled", false); 
    }

    function disableBuyerCode () {
        //고객제출자료 여부에 따라 고객사select 를 disabled한다.
        var fm = document.frm;
        fm.buyerCode.disabled == true;
        fm.buyerCodeTxt.disabled = true;
        $("#selectMultiSubContractorDiv").hide();
        $("[name=buyerCode]").attr("disabled", true); 
    }
 */
    function insertFileLine () {
        //첨부파일 라인을 추가한다.
        var fileTable = $("#fileTable")[0];
        var innerRow = fileTable.insertRow();
        innerRow.height = "27";
        var innerCell;
        var filePath = "filePath"+fileTable.rows.length;
        var filehtml = "";
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

    function selProjectNo () {
    	if(document.frm.categoryCode.value == ''){
            alert("문서분류를 먼저 선택하시기 바랍니다.");
            return false;
        }
        //프로젝트검색창을 열어 프로젝트를 선택한다.
        var url="/plm/jsp/project/SearchPjtPop.jsp?mode=multi&modal=N";
        openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
    }
    
    function setProject(returnValue){
        if ( returnValue == null ) {
            return true;
        }
        var d = document.frm;
        var objArr = returnValue;
        var proj_oid;
        var proj_number;
        var proj_name;
        var task_name;
        var proj_rank;
        var body = document.getElementById("iProjTable");
        var tempRank ="";
        var projectRanks = d.projectRank;
        var customerCode;
        var customerName;
        <%-- if ( projectRanks != null ) {
            if ( projectRanks.length != null ) {
                for ( var i = 0 ; i< projectRanks.length ; i++ ) {
                    for ( var h= 0; h < objArr.length; h++ ) {
                        proj_rank = objArr[h][9];
                        if ( h == 0 ) {
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
                    if ( h==0 ) {
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
        else{
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
            customerCode = objArr[i][15];
            customerName = objArr[i][16];
            if ( !isMold && proj_rank == "S" ) {//금형프로젝트 제외 2018.08.23 박상수 차장 요청
                d.security.value = "S2"; //대내비
                document.getElementById("security").disabled = true;
            }
            else {
                d.security.value = "S1";//대외비
                document.getElementById("security").disabled = false;
            }
            if(customerCode != null){
                d.buyerCode.value = customerCode.substring(1);
            }
            if(customerName != null){
                d.buyerCodeTxt.value = customerName.substring(1);
            }
            if (!projDuplicateCheck(proj_oid) ) {
                var tBody = document.getElementById("iProjTable");
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
                
                if(d.qulityYn.value != 'Y'){
                	// 프로젝트 선택 시 관련제품정보 Ajax 처리
                    //addProjectProductInfoAjax(proj_oid); 프로젝트 관련부품 연계기능 주석처리요청 2019.04.11 박상수 차장 요청	
                } 
                
            }
            else {
                //alert("Duplicated Project ==>"+proj_number);
                return true;
            }
        }
        return false;
    }

    function projDuplicateCheck (poid) {
        //프로젝트의 중복여부를 체크한다.
        var tBody = document.getElementById("iProjTable");
        var rowsLen = tBody.rows.length;
        if ( rowsLen > 0 ) {
            var primaryProj = document.frm.projectOid;
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

<%
String taskOid = request.getParameter("taskOid");

E3PSTask maintask = (E3PSTask)CommonUtil.getObject(taskOid);
String mainTaskName = "";
if(maintask != null){
    mainTaskName = maintask.getTaskName();    
}

String partNoStr = "";
if(project != null){
  List<HashMap<String, String>> listPart = E3PSProjectHelper.service.getProductInfoWTPart(CommonUtil.getOIDString(project));
  if (listPart != null) {
  	for (HashMap<String, String> partMap : listPart) {
  	    partNoStr = partMap.get("partnumber");
  	    break;
  	}
  }
}
%>
    function partTypeCheckByCategory(){
    	var categoryCodeName = $('[name=categoryCodeTxt]').val();
    	var qulityYn = document.frm.qulityYn.value;
    	
    	//변경이력 
    	// (1) 검사기준서(사내) 일 경우 관련부품 등록 유형 제한 (반제품,원자재,상품) 경영혁신팀 박상수 차장 요청 2019.08.14 by 황정태
    	// 포장사양서(완제품) 추가 20201.05.20 박상수 차장 요청
    	
    	if(categoryCodeName.indexOf('포장사양서(완제품)') != -1){
    		return "F,R,W";
    	}else if(categoryCodeName.indexOf('검사기준서(사내)') != -1 || categoryCodeName.indexOf('제조공정도') != -1 || categoryCodeName.indexOf('작업표준서') != -1 || categoryCodeName.indexOf('관리계획서') != -1 || categoryCodeName.indexOf('PFMEA') != -1){
        	return "H,R,W";
        }else{
        	if(qulityYn == "Y"){
        		return "F,H,R,W";
        	}else{
        		return "";	
        	}
        }
    }

    function searchOnePart(){
    	var pType = partTypeCheckByCategory();
    	SearchUtil.selectOnePart('selectPartAfterFunc','pType='+pType);
    	showProcessing();
    }
    
    function insertPartLine (){
    	
    	if(document.frm.categoryCode.value == ''){
    		alert("문서분류를 먼저 선택하시기 바랍니다.");
    		return false;
    	}
    	
    	var qulityYn = document.frm.qulityYn.value;
    	var categoryCodeName = $('[name=categoryCodeTxt]').val();
    	
    	if(qulityYn == 'Y' && isNull($("input[name=relatedPart]").val())){
    		alert("먼저 [품질문서번호] 항목에 대표품번을 입력하십시오.");
    		$('input[name="relatedPart"]').focus();
    		return false;
    	}
    	
    	if(categoryCodeName == "검사기준서(사내)" || categoryCodeName == "제조공정도" || categoryCodeName == "작업표준서" || categoryCodeName.indexOf('관리계획서') != -1 || categoryCodeName.indexOf('PFMEA') != -1){
    		qulityYn = "N";
    	}
    	
    	if(qulityYn == 'Y'){
    		//if(isNull($("input[name=relatedPart]").val())){
        		alert("품질문서일 경우 [품질문서번호] 항목에만 대표품번 입력이 허용됩니다.");
                $('input[name="relatedPart"]').focus();
                return false;
        	//}
    	}
    	
        //부품을 추가하기 위해 검색팝업을 호출한다.
        
        //아래 로직은 프로젝트번호를 부품검색팝업 호출시 파라미터로 전달하기 위함인데 제거한다 2021.05.20 박상수 차장 요청
        <%-- var number = "<%=partNoStr%>";    H31*
        var proj_numbers = "";
        var proj_number = $("[name=proj_number]");
        if(proj_number.length > 0){
            for(var i=0; i < proj_number.length; i++){
                proj_numbers += proj_number[i].value + ",";
            }
        } --%>
        var pType = partTypeCheckByCategory();
        //SearchUtil.selectPart("setPartInfoSbl","fromPage=SBL&pType="+pType+"&number="+number+"&projectNo="+proj_numbers);
        SearchUtil.selectPart("setPartInfoSbl","fromPage=SBL&pType="+pType);
    }
    /*
     아 오늘도 밤샜다.
     죽겠다.... tklee
    */
    function setPartInfoSbl(returnValue){
    	
    	if ( typeof returnValue == "undefined" || returnValue == null ) {
            return;
        }
        
        //문서중복체크######################
        var partNoList = [];
        for ( var i = 0; i < returnValue.length; i++ ) {
            var partNoValue = returnValue[i][1];
        	partNoList.push(partNoValue.toString());
        }
        
		var param = {
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
			
			//return true;
		}
		//문서중복체크######################
		
    	//부품추가시 선택된 부품정보를 추가한다. hidden값으로 해당Row에    partOid를 저장해둔다.
        for ( var i = 0; i < returnValue.length; i++ ) {
            part_oid = returnValue[i][0];
            part_number = returnValue[i][1];
            part_name = returnValue[i][2];
            if ( !partDuplicateCheck(part_oid) ) {
                var tBody = document.getElementById("iPartTable");
                var innerRow = tBody.insertRow();
                var innerCell = innerRow.insertCell();
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                    + "<div style=\"display:none;\"><input name='iPartChk' type='checkbox' class='chkbox'></div><input type='hidden' name='partOid' value='"+ part_oid +"'><input type='hidden' name='partNumber' value='"+part_number+"'>";
                innerCell = innerRow.insertCell();
                innerCell.className = "tdwhiteM";
                innerCell.innerText = part_number;
                innerCell = innerRow.insertCell();
                innerCell.className = "tdwhiteM0 text-left";
                innerCell.innerText = part_name;
            }
            else{
                //alert("Duplicated Part ==>"+part_number);
            }
        }
    }
    
    function resetCode(){
        $("#analysisTR").hide();
        $("[name=analysisCode]").val('');
        $("[name=analysisCodeTxt]").val('');
        $("#quiltyTTR").hide();
        $(".DEPTACLTR").hide();
        $("#pubDateTR").hide();
        $("#costCommentTR").hide();
        document.frm.qulityYn.value = "";
    }

    function partDuplicateCheck (poid) {
        //부품추가시 선택된 부품정보를 중복체크한다.
        var tBody = document.getElementById("iPartTable");
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
        return false;
    }

    function doCancel () {

        if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "01412") %><%--문서등록을 취소하시겠습니까?--%>') ) {
            parent.self.close();
            //document.location.href="/plm/jsp/dms/SearchDocument.jsp";
        }
    }

    function selectDocu (){
        var url="/plm/jsp/dms/SearchDocumentPop.jsp?method=selDocu";
        openWindow(url,"","500","500","status=1,scrollbars=no,resizable=no");
    }

    function selDocu (arr) {
        for ( var i = 0; i < arr.length; i++ ) {
           var docuOid = arr[i];
           alert(docuOid);
        }
    }

    function chgFirstRegist(str){
        //최초등록시점 변경시 체크값도 같이 변경한다.
        var fm = document.frm;
        if ( fm.firstRegistrationStage.value != str ) {
            if ( str=="1" ) {
                fm.firstRegistrationStage1.checked = true;
                fm.firstRegistrationStage2.checked = false;
            }
            else {
                fm.firstRegistrationStage1.checked = false;
                fm.firstRegistrationStage2.checked = true;
            }
            fm.firstRegistrationStage.value= str;
        }
    }

    function doSave(){
    	//저장버튼 클릭시 valcheck체크후 hidden값인 dRCheckPoint값과 buyerCodeStr값을 지정하고 multipart/form-data형태로 submit한다.
        if ( !valcheck() ) {
            return;
        }
        else {
        	
        	var taskName = '<%=mainTaskName.replaceAll("'", "^quot^")%>';
        	taskName = taskName.replace('^quot^', "\'");
        	
            var categoryCodeTxt = $('[name=categoryCodeTxt]').val();
            	
            if(taskName != '' && categoryCodeTxt.indexOf('검사기준서') != -1 || categoryCodeTxt.indexOf('수입검사기준서') != -1){
                if(!confirm('해당 Task [ '+taskName+' ] 에 등록하고자 하는 문서분류의 종류를 확인하셨습니까? 문서분류 : [ '+categoryCodeTxt+' ] \r\n확인을 클릭하면 저장됩니다.')){
                    return;
                }   
            }
        	
            var d = document.frm;

            if ( d.isDRCheckSheet.value=="t" ) {
                if ( isNull(d.dRPoint.value) ) {
                    d.dRCheckPoint.value = "0";
                }
                else {
                    d.dRCheckPoint.value = d.dRPoint.value;
                }
            }
            else {
                d.dRCheckPoint.value = "00";
            }

            if ( d.buyerCode.disabled == false ) {
                var buystr="";
                var code = d.buyerCode.value;
                for ( var i = 0; i < code.split(",").length; i++ ) {
                    buystr = buystr + "," + code.split(",")[i];
                }
                d.buyerCodeStr.value = buystr.substring(1);
                //alert(d.buyerCodeStr.value);
            }
            else{
                d.buyerCodeStr.value = "";
            }

            if ( d.security.value == "" ) {
                d.security.value == "S1";
            }

            d.documentDescription.value = "";
            // innoditor WebEditor
            // 첫번째 매개변수 => true : < & 특수문자 처리,  false : 처리안함
            // 두번째 매개변수 => 이노디터 번호
            d.webEditor.value = fnGetEditorHTMLCode(false, 0);
            d.webEditorText.value = fnGetEditorText(0);

            d.encoding = 'multipart/form-data';
            document.getElementById("security").disabled = false;
            
            var from = "";
            if(<%=!StringUtil.isEmpty(taskOid)%>) from = "TaskOutput";
            else if(<%=(outputOids != null)%>) from = "MyTaskOutput";
            //if ( d.outputOid == null ) {
                d.cmd.value = 'create';
                d.action = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=create&from="+from;
            //}
            //else{
            //    d.cmd.value = 'createPop';
            //    d.action = "/plm/servlet/e3ps/ProjectDocumentServlet?cmd=createPop";
            //}
            showProcessing();
            disabledAllBtn();
            d.submit();
            <%-- 
            <%
            if(!StringUtil.isEmpty(taskOid)) {
%>
            parent.opener.parent.document.location.href = '/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=taskOid%>';
                parent.opener.goTaskPage();
<%
            }else if(project != null) {
%>
				parent.opener.document.location.reload();
<%          }
%>
//             parent.opener.document.location.reload();
         --%>
        }
    }

    //숫자판별
    function isNotDigit (str) {
        var pattern = /^[0-9]+$/;

        if ( !pattern.test(str) ){
            return true;
        }
        return false;
    }
    
    function checkPartNumber(partNo, partType){
        var ret = "N";
        $.ajax({
            type: 'get',
            async: false,
            url: '/plm/ext/part/base/existPartNumber.do?partNumber='+partNo+"&partType="+((typeof(partType)!= "undefined")?partType:""),
            success: function (data) {
                if(data != 'Fail'){
                    try{
                        if(data=='Y'){
                            ret = 'Y';
                        }else{
                            ret = 'N';
                        }
                    }catch(e){alert(e.message); ret = "E"; }
                }else{
                    ret = "E";
                }
            },
            fail : function(){
                ret = "E";
            }
        });
        
        return ret;
     }

    //필수입력체크
    function valcheck () {
        var d = document.frm;
        //alert("--"+d.quiltyNo.value.replace(/ /gi,"")+"--");
        
        var basicPartNo = $('input[name="relatedPart"]').val();
        var isDup = false;
        if(d.qulityYn.value == 'Y'){
        	if(isNull(d.relatedPart.value)){
        		alert("품질문서일 경우 [품질문서번호] 항목에 품번을 입력하셔야합니다.");
                $('input[name="relatedPart"]').focus();
                return false;
        	}else{
        		if(checkPartNumber(d.relatedPart.value,'') != 'Y'){
        			alert("존재하지 않는 품번입니다.다시 입력하시기 바랍니다.");
                    $('input[name="relatedPart"]').focus();
                    return false;
        		}
        	}
        	
        	$('#iPartTable tr').each(function() {
    			if(basicPartNo == this.cells[1].innerHTML){
    				isDup = true;
    			}
    		});
        }
        
        if(isDup){
        	alert("품번 [" + basicPartNo + "] 가 관련부품에 중복으로 존재합니다.\n중복되는 부품을 제거하십시오.");
    		return false;	
        }
        
        
        if( isNull(d.categoryCode.value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01426") %><%--문서분류는 최종Level까지 선택해야 합니다.--%>');
            return false;
        }
        else{
            var flag = checkDocCategoryAjax(d.categoryCode.value);
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
        else {
            var s = d.documentName.value;
            if ( s.length > 160 ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01417") %><%--문서명은 160자를 초과할 수 없습니다--%>');
                return false;
            }
        }

        var buyercodevalue = d.buyerCode.value;
        if ( buyercodevalue.split(",").length > 10 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00842") %><%--고객사는 10개를 초과할 수 없습니다--%>');
            return false;
        }
        
        
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
        
        if(isNull(d.devDeptCode.value) && $("[name=isDeptACL]").val() == "true"){
        	alert("부서조회권한을 반드시 입력해야 합니다.");
        	return false;
        }
        
/*         if(d.categoryCode.value == "PD302015" || d.categoryCode.value == "PD303063"){
        	if(isNull(d.analysisCode.value)){
        		alert("해석의뢰 문서를 반드시 입력해야 합니다.");
        		return false;
        	}
        } */
        

//         fm.buyerCode.disabled == false;
//         fm.buyerCodeTxt.disabled = false;
        
        var buyerLen = d.isBuyerSummit.length;
        var isBuyerSummitVal = '';
		for ( var i  = 0;i < buyerLen; i++)
		{
			if (d.isBuyerSummit[i].checked == true)
			{
			   isBuyerSummitVal = d.isBuyerSummit[i].value;
			   break;
			}
		}

//         alert(d.buyerCode.disabled+':'+buyercodevalue+':'+ isBuyerSummitVal  );
        if ( (isBuyerSummitVal =='1')&&(buyercodevalue.length == 0 )) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00839") %><%--고객사 제출자료 대상일 경우 고객사는 반드시 입력해야 합니다.--%>');
            return false;
        }

        if ( isNull(d.divisionCodeStr.value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01665") %><%--사업부는 반드시 선택해야 합니다--%>');
            return false;
        }

        if ( d.isDRCheckSheet.value=="t" ) {
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

            if ( d.dRPoint.value<0 ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00167") %><%--DR평가점수는 0 이상이여야 합니다--%>');
                return false;
            }
        }
        
        if ( d.firstRegistrationStage.value == "1" ) {
            var d = document.frm;
            var projectRanks = d.projectRank;
            <%-- var outputOid ="<%=outputOid%>"; --%>
            var outputOid = d.outputOid;

            if ( outputOid == null ) {
                if ( projectRanks == null ) {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "00922") %><%--관련 프로젝트를 반드시 선택해야 합니다.--%>');
                    return false;
                }
            }
        }
        
/*         //포장사양서 부품체크
        if("PD303057" == $("input[name=categoryCode]").val() && $("#iPartTable tr").length == 0){
            alert("포장사양서인 경우 관련 부품을 필수로 입력해야 합니다.");
            return false;
        }
         */
        if(d.qulityYn.value != 'Y'){
        	if($("input[name=firstRegistrationStage2]").is(":checked") && $("#iPartTable tr").length == 0){
                alert("양산단계의 문서일 경우 관련 부품을 등록하세요.");
                return false;
            }	
        }
         
        var partNoList = new Array();
        $("input[name=partNumber]").each(function(){
        	var partNoValue = $(this).val();
        	partNoList.push(partNoValue.toString());
        });
        
        var param = {
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
        }
         
        if ( isNull(d.iFile0.value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02685") %><%--주첨부파일을 첨부하여 주십시오.--%>');
            return false;
        }
        
        var strHTMLCode = fnGetEditorHTMLCode(false, 0);
        if ( strHTMLCode == "" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01429") %><%--문서설명은 반드시 입력해야 합니다.--%>');
            return false;
        }
        
        return true;
    }

    function init (){
        var security ='<%=security%>';
        if ( security =="S2" ){
            document.frm.security.disabled = true;
        }
    }
    
    
    
    
    
    //
    -->
</script>
<%-- <script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/custom-jquery.css" />
<script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/ajax.js"></script>
<script src="/plm/jsp/common/content/content.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script> --%>
<script type="text/javascript">
    //Document Category Ajax
    function numCodeAjax(docTypeCode, parentCode, targetId) {
        $.ajax({
            url : "/plm/servlet/e3ps/DocumentCategoryAjax",
            type : "POST",
            data : {
                docTypeCode : docTypeCode,
                parentCode : parentCode
            },
            dataType : 'json',
            async : false,
            success : function(data) {
                $.each(data.returnObj, function() {
                    $("#" + targetId).append("<option value='"+this.categoryCode+this.isDrCheck+"'>" + this.categoryName + "</option>");
                });
            }
        });
    }
    
    function setDevDocCategoryAjax(categoryCode, categoryCodeTxt){
    	
        $.ajax({
            url : "/plm/ext/dms/setDevDocCategory.do",
            type : "POST",
            data : {
            	devDocCagegoryCode : categoryCode
            },
            dataType : 'json',
            success : function(data) {
            	
            	var isDRCheckSheet = eval(data.isDRCheckSheet);
            	var isDeptACL = data.attribute1 == "Y";
            	
                if(isDRCheckSheet) document.frm.isDRCheckSheet.value = "t";
                else document.frm.isDRCheckSheet.value = "f";
                var tBody = document.getElementById("dRPointTable");
                if ( document.frm.isDRCheckSheet.value == "f" ) {
                    document.getElementById("dRPointTable").style.display = "none";
                }
                else{
                    document.getElementById("dRPointTable").style.display = "";
                }
                if(categoryCode == "PD302015" || categoryCode == "PD303063"){
                	$("#analysisTR").show();
                }else{
                	$("#analysisTR").hide();
                	$("[name=analysisCode]").val('');
                	$("[name=analysisCodeTxt]").val('');
                }
                
                $("[name=isDeptACL]").val(isDeptACL);
                if(isDeptACL) $(".DEPTACLTR").show();
                else          $(".DEPTACLTR").hide();
                
                
           		var qulityYn = data.attribute2 != null && data.attribute3 == null ? "Y" : "N";
           		var securityYn = data.attribute4;
           		var pubDateYn = data.attribute6;
           		
           		if(qulityYn == "Y"){
           			$("input[name=qulityYn]").val("Y");
           			$("#quiltyTTR").show();
           			//$("#iPartTable").remove();
           		}else{
           			$("input[name=qulityYn]").val("N");
           			$("#quiltyTTR").hide();
           		}
           		
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
           		
           		if(categoryCode == "PD303148"){
                    
                    $("#costCommentTR").show();
                }else{
                    
                    $("#costCommentTR").hide();
                }
           		
           		var categoryCodeName = $('[name=categoryCodeTxt]').val();
           		
                if(categoryCode != '' && (categoryCodeName.indexOf('검사기준서') != -1 || categoryCodeName.indexOf('수입검사기준서') != -1)){
                	
                    alert("작성하고자 하는 검사기준서의 분류(사내, 고객)가 맞는지 반드시 확인 후 진행 바랍니다.");
                }
            		
                //setQulityDocu(categoryCode);
                //setSecurityDocu(categoryCode);
                
                /* result_code = "";
                if(categoryCode == "PD303066"){
                	$("#quiltyTTR").show();
                    result_code = "DF-";
                }else if(categoryCode == "PD303072"){
                	$("#quiltyTTR").show();
                    result_code = "PF-";
                }else if(categoryCode == "PD303001"){
                	$("#quiltyTTR").show();
                    result_code = "KIS-";
                }else if(categoryCode == "PD303017"){
                	$("#quiltyTTR").show();
                    result_code = "CP-";
                }else if(categoryCode == "PD303034"){
                	$("#quiltyTTR").show();
                    result_code = "KEQ-";
                }else if(categoryCode == "PD303046"){
                	$("#quiltyTTR").show();
                    result_code = "KETS-";
                }else if(categoryCode == "PD303047"){
                	$("#quiltyTTR").show();
                    result_code = "PFC-";
                }else{
                	$("#quiltyTTR").hide();
                	document.frm.quiltyNo.value = "";
                }
                $("#quiltyTR").text(result_code); */

                //$('[name=categoryCode]').val(categoryCode);
                //$('[name=categoryCodeTxt]').val(categoryCodeTxt);
            }
        });
    }
    
    function setQulityDocu(categoryCode){
    	var qulityYn = document.frm.qulityYn.value;
    	
    	if(qulityYn == ''){
    		qulityYn = getCategoryIsQulity(categoryCode);
    	}
    	document.frm.qulityYn.value = qulityYn;
    	if(qulityYn == 'Y'){
    		$("#quiltyTTR").css("display", "block");
    	}else{
    		$("#quiltyTTR").css("display", "none");
    	}
    }
    
    function getCategoryIsQulity(categoryCode){
    	
        var rtn = '';
        $.ajax({
            url : "/plm/ext/dms/getCategoryIsQulity.do",
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
    	
    	if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        if(returnValue[0].id == "PD302015" || returnValue[0].id == "PD303063"){
            $("#analysisTR").show();
        }else{
            $("#analysisTR").hide();
        }
        
        var isDeptACL = returnValue[0].attribute1 == "Y";
        $('[name=isDeptACL]').val(isDeptACL);
        
        $('[name=categoryCode]').val(returnValue[0].id);
        $('[name=categoryCodeTxt]').val(returnValue[0].name);
        $('[name=qulityYn]').val(returnValue[0].qulityYn);
        
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
    
    function addProjectProductInfoAjax(projectoid){
        $.ajax({
            url : "/plm/ext/dms/addProjectProductInfo.do",
            type : "POST",
            data : {
                projectoid : projectoid
            },
            dataType : 'json',
            async : true,
            success : function(data) {
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
        });
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
    
    function selectPartAfterFunc(objArr){
        
        var srcPartNo= "";
        var srcPartName= "";
        var relatedPartOid = "";
        for ( var i = 0; i < objArr.length; i++ ) {
            
            srcPartNo = objArr[i][1];
            relatedPartOid = objArr[i][0];
        }
        
        var param = {
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
       		
       		//msg += "중복 등록 시 결재가 불가하오니 조치 후 진행 바랍니다.";
       		
       		alert(msg);
       		
      		return msg;
       	}else{
       		$('input[name="relatedPart"]').val(srcPartNo);	
       	}
        
       	
    }
    
    $(document).ready(function() {
        // suggest
        SuggestUtil.bind('PROJECTDOCTYPE', 'categoryCodeTxt', 'categoryCode');
        SuggestUtil.bind('CUSTOMER', 'buyerCodeTxt', 'buyerCode');
        SuggestUtil.bind('PARTNO', 'relatedPart');
        SuggestUtil.bind('DEPTUSER', 'devDeptName', 'devDeptCode');
        
        $("[name=categoryCode]").change(function() {
            setDevDocCategoryAjax($('[name=categoryCode]').val(), $('[name=categoryCodeTxt]').val());
        });
        CalendarUtil.dateInputFormat('pubDate');
        
        
        $("#analysisTR").hide();
        $("#quiltyTTR").hide();
        $(".DEPTACLTR").hide();
        //setQulityDocu();
        
        setDevDocCategoryAjax($('[name=categoryCode]').val(), $('[name=categoryCodeTxt]').val());
        
        
        <%-- 
        <%if (isBuyer.equals("t")) {%>
            $("#selectMultiSubContractorDiv").show();
            $("[name=buyerCode]").attr("disabled", false); 
        <%}else {%>
	        $("#selectMultiSubContractorDiv").hide();
            $("[name=buyerCode]").attr("disabled", true); 
        <%}%>
         --%>
//         if($("[name=isBuyerSummit]").val() == "2"){
//             $("#selectMultiSubContractorDiv").hide();
//         } else {
//             $("#selectMultiSubContractorDiv").show();
//         }
        resizeTo(1000, 900);
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
</head>
<body>
  <form name="frm" method="post">
    <input type="hidden" name="cmd" value="create">
    <input type="hidden" name="categoryCode" value="<%=categoryCode%>">
    <input type="hidden" name="analysisCode" value="<%=analysisCode%>">
    <input type="hidden" name="isDRCheckSheet" value="<%=isDRCheckSheet%>">
    <input type="hidden" name="isDeptACL" value="false">
    <input type="hidden" name="dRCheckPoint">
    <input type="hidden" name="partOid" value="0">
    <input type="hidden" name="buyerCodeStr">
    <input type="hidden" name="taskOid" value="<%= taskOid %>">
    <input type="hidden" name="projectOid" value="0">
    <input type="hidden" name="firstRegistrationStage" value="<%=firstRegistrationStage%>">
    <input type="hidden" name="qulityYn" value="<%=qulityYn%>">
    <input type="hidden" name="pubDateYn" value="<%=pubDateYn%>">
    
    <%
        if (outputOids != null) {
            for (String outputOid : outputOids) {
    %>
    <input type="hidden" name="outputOid" value="<%=outputOid%>">
    <%
            }
        }
    %>
    <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea>
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 보낼 Form End -->
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
          <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="50" valign="top">
                <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                      <table style="height: 28px;">
                        <tr>
                          <td width="7"></td>
                          <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                          <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00641")%><%--개발산출문서 등록--%></td>
                        </tr>
                      </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
              <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
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
          <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
            <colgroup>
              <col width="15%" />
              <col width="35%" />
              <col width="15%" />
              <col width="35%" />
            </colgroup>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%> <span class="red">*</span></td>
              <td colspan="3" class="tdwhiteL0"><input type="text" name="categoryCodeTxt" class="txt_field" style="width: 50%;"
                  value="<%=docCatePath.split("/")[docCatePath.split("/").length - 1]%>"
                > <a href="javascript:SearchUtil.selectOneDevDocCategory('selectDevDocCategoryCallBackFn');"> <img src="/plm/portal/images/icon_5.png" border="0"></a> <a
                href="javascript:CommonUtil.deleteValue('categoryCodeTxt','categoryCode');resetCode();"
              ><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415")%><%--문서명--%> <span class="red">*</span></td>
              <td colspan="3" class="tdwhiteL0"><input type="text" name="documentName" class="txt_field" style="width: 95%" value="<%=docuTitle%>"></td>
            </tr>
            <tr id="analysisTR" style="display: none">
              <td class="tdblueL">해석의뢰문서 <span class="red">*</span></td>
              <td colspan="3" class="tdwhiteL0"><input type="text" name="analysisCodeTxt" class="txt_field" style="width: 50%;" readonly><a href="javascript:SearchUtil.selectAnalysisDocument(analysisCallBackFn);"> <img src="/plm/portal/images/icon_5.png" border="0"></a> <a href="javascript:CommonUtil.deleteValue('analysisCodeTxt','analysisCode');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02858")%><%--최초 등록시점--%> <span class="red">*</span></td>
              <td class="tdwhiteL">
                <label><input name="firstRegistrationStage1" <%if (outputOids != null) {%> disabled <%}%> type="radio" class="Checkbox" value="1" checked
                  onClick="javascript:chgFirstRegist('1')"
                ><%=messageService.getString("e3ps.message.ket_message", "00629")%><%--개발단계--%></label>&nbsp;
                <label><input name="firstRegistrationStage2" <%if (outputOids != null) {%> disabled <%}%> type="radio"
                  class="Checkbox" value="2" onClick="javascript:chgFirstRegist('2')"
                ><%=messageService.getString("e3ps.message.ket_message", "02094")%><%--양산단계--%></label></td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%> <span class="red">*</span></td>
              <td class="tdwhiteL0">
                <%
                    if (divisionCode != null && (divisionCode.equals("CA") || divisionCode.equals("EA"))) {
                %> <%
     parameter.clear();
        parameter.put("locale", messageService.getLocale());
        parameter.put("codeType", "DIVISIONTYPE");
        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

        for (int i = 0; i < numCode.size(); i++) {
            if (divisionCode.equals(numCode.get(i).get("code"))) {
 %> <input type="hidden" name="divisionCodeStr" value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%> <%
     }
        }
 %> <%
     } else {
 %> <select name="divisionCodeStr">
                  <%
                      parameter.clear();
                        parameter.put("locale", messageService.getLocale());
                        parameter.put("codeType", "DIVISIONTYPE");
                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                        for (int i = 0; i < numCode.size(); i++) {
                  %>
                  <option value="<%=numCode.get(i).get("code")%>" <%if (numCode.get(i).get("code").equals(divisionCode)) {%> selected <%}%>><%=numCode.get(i).get("value")%></option>
                  <%
                      }
                  %>
              </select> <%
     }
 %>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00830")%><%--고객 제출자료--%> <span class="red">*</span></td>
              <td class="tdwhiteL">
                <input name="isBuyerSummit" type="radio" class="Checkbox" value="2" <%if (isBuyer.equals("f")) {%> checked <%}%>> <!-- onClick="disableBuyerCode()" -->
                <%=messageService.getString("e3ps.message.ket_message", "01637")%><%--비대상--%>
                &nbsp;&nbsp;&nbsp; 
                <input name="isBuyerSummit" type="radio" class="Checkbox" value="1" <%if (isBuyer.equals("t")) {%> checked <%}%>> <!-- onClick="enableBuyerCode()" -->
                <%=messageService.getString("e3ps.message.ket_message", "01227")%><%--대상--%>
              </td>
              <td rowspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%> <span class="red">*</span></td>
              <td rowspan="2" height="30" class="tdwhiteL0">
              <div id="selectMultiSubContractorDiv">
                <input type="text" name="buyerCodeTxt" class="txt_field" style="width: 50%">
                <input type="hidden" name="buyerCode">
                <a href="javascript:SearchUtil.selectMultiSubContractor('selectMultiSubContractor');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                <a href="javascript:CommonUtil.deleteValue('buyerCode','buyerCodeTxt');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
              </div>
              </td>
              <%-- 
              <td rowspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837")%>고객사 <span class="red">*</span></td>
              <td rowspan="2" height="30" class="tdwhiteL0">
              <div id="selectMultiSubContractorDiv">
                <input type="text" name="buyerCodeTxt" class="txt_field" style="width: 50%" <%if (isBuyer.equals("f")) {%> disabled <%}%>>
                <input type="hidden" name="buyerCode" <%if (isBuyer.equals("f")) {%> disabled <%}%>>
                <a href="javascript:SearchUtil.selectMultiSubContractor(selectMultiSubContractor);"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                <a href="javascript:CommonUtil.deleteValue('buyerCode','buyerCodeTxt');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
              </div>
              </td> --%>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01530")%><%--보안등급--%> <span class="red">*</span></td>
              <td class="tdwhiteL"><select name="security" id="security" style="width: 95%">
                  <%
                      parameter.clear();
                      parameter.put("locale", messageService.getLocale());
                      parameter.put("codeType", "SECURITYLEVEL");
                      numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                      for (int i = 0; i < numCode.size(); i++) {
                  %>
                  <option value="<%=numCode.get(i).get("code")%>" <%if (security.equals(numCode.get(i).get("code"))) {%> selected <%}%>><%=numCode.get(i).get("value")%></option>
                  <%
                      }
                  %>
              </select></td>
            </tr>
            <tr class="DEPTACLTR">
                <td class="tdblueL">부서조회권한<span class="red">*</span></td>
                <td class="tdwhiteL">
	                <input type="text" name="devDeptName" class="txt_field" style="width: 30%" >
					<input type="hidden" name="devDeptCode"> 
					<a href="javascript:SearchUtil.addAllDepartment('devDeptCode','devDeptName', 'y');">
					<img src="/plm/portal/images/icon_5.png" border="0"></a> 
					<a href="javascript:CommonUtil.deleteValue('devDeptCode','devDeptName');">
					<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
				</td>
                <td class="tdblueL">사용자조회권한</td>
                <td class="tdwhiteL" colspan="2">
                    <select name="duty" id="duty" style="width: 70%">
	                  <%
	                        Vector dutyNameList = CompanyState.dutyNameList;
	                        Vector dutyCodeList = CompanyState.dutyCodeList;
	                      for (int i = 0; i < dutyCodeList.size(); i++) {
	                          if(!"고문".equals(dutyNameList.get(i)) && !"감사".equals(dutyNameList.get(i))){
	                  %>
	                          <option value="<%=dutyCodeList.get(i)%>" <%if("연구원".equals((String)dutyNameList.get(i)))out.print("selected");%> ><%=dutyNameList.get(i)%></option>
	                  <%
	                          }
	                      }
	                  %>
                    </select>
                </td>
            </tr>
            <tr id="quiltyTTR" style="display: none">
              <td class="tdblueL">품질문서번호<span class="red">*</span></td>
              <td colspan="3" class="tdwhiteL0">
                <input type="text" name="relatedPart" class="txt_fieldRO" style="width: 30%" readonly>
                <input type="hidden" name="relatedPartOid">
                <a href="#" onclick="javascript:searchOnePart();return false;">
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
					<input type="text" name="pubDate" id="pubDate" class="W90 txt_field" value="">
					<a href="javascript:CommonUtil.deleteValue('pubDate');">
					<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
					<select name="pubCycle">
						<option value="">주기 (필수)</option>
						<option value="3">3개월</option>
						<option value="6">6개월</option>
						<option value="9">9개월</option>
						<option value="12">12개월</option>
					</select>
				</td>
            </tr>
            <tr id="dRPointTable" <%if (isDRCheckSheet.equals("f")) out.print("style=\"display: none;\"");%>>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00166")%><%--DR평가점수--%> <span class="red">*</span></td>
              <td colspan="3" class="tdwhiteL0"><input type='text' name='dRPoint' class='txt_field' style="width: 50px"></td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03046")%><%--프로젝트--%> <span class="red">*</span></td>
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
                      <col width="345">
                      <col width="*">
                    </colgroup>
                    <tbody id="iProjTable">
                    </tbody>
                  </table>
                </div>
                  <%
                      if (outputOids != null) {
                  %>
                  <script>
                    var projArr = new Array();
                    var projIdx = 0;
                  <%
                        for (String outputoid : outputOids) {
                            ProjectOutput po = (ProjectOutput) CommonUtil.getObject(outputoid);
                            E3PSTask task = (E3PSTask) po.getTask();
                            project = (E3PSProject) task.getProject();
                            String maker = "";
                            String category = "";
                            String domain = "";
                            String pjtPmName = "";
                            String customerCode = "";
                            String customerName = "";
                            WTUser pm = ProjectUserHelper.manager.getPM(project);
                            if (pm != null) {
                                pjtPmName = pm.getFullName();
                            }
                            if (project.getOemType() != null) {
                                maker = project.getOemType().getMaker().getCode();
                                category = project.getOemType().getCode();
                                if (maker.startsWith("10")) {
                                domain = "1000";
                                } else {
                                domain = "2000";
                                }
                            }
                            String state = project.getState().getState().getDisplay(messageService.getLocale());
                            ExtendScheduleData esdata = (ExtendScheduleData) project.getPjtSchedule().getObject();

                            E3PSProject s_project = null;
                            if (project instanceof MoldProject) {
                                MoldProject moldProject = (MoldProject) project;
                                s_project = moldProject.getMoldInfo().getProject();
                            }
                            else {
                                s_project = project;
                            }
                            QuerySpec psspec = new QuerySpec(ProjectSubcontractorLink.class);
                            SearchUtil.appendEQUAL(psspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(s_project), 0);
                            QueryResult psresult = PersistenceHelper.manager.find(psspec);
                            while (psresult != null && psresult.hasMoreElements()) {
                                ProjectSubcontractorLink link = (ProjectSubcontractorLink) psresult.nextElement();
                                if (link != null) {
                                NumberCode ncode = link.getSubcontractor();
                                customerCode += "," + CommonUtil.getOIDString(ncode);
                                customerName += "," + ncode.getName();
                                }
                            }
                  %>
                    var subArr = new Array();
                    subArr[0] = '<%= CommonUtil.getOIDString(project) %>'; //project oid
                    subArr[1] = '<%= StringUtil.checkNull(project.getPjtNo()) %>'; //project_no
                    subArr[2] = "<%= StringUtil.checkNull(project.getPjtName()) %>"; //project name
                    subArr[3] = '<%= DateUtil.getDateString(esdata.getPlanStartDate(), "d") %>'; //plan_start_date
                    subArr[4] = '<%= DateUtil.getDateString(esdata.getPlanEndDate(), "d") %>'; //plan_end_date
                    subArr[5] = '<%= state %>'; //status
                    subArr[6] = '<%= domain %>'; //domain
                    subArr[7] = '<%= maker %>'; //maker
                    subArr[8] = '<%= category %>'; //category
                    subArr[9] = '<%= (project.getRank() == null)?"":project.getRank().getName() %>'; //rank
                    subArr[10] = '<%= pjtPmName %>'; //pm
                    subArr[14] = "<%= StringUtil.checkNull(task.getTaskName()) %>"; //task_name
                    subArr[15] = '<%= customerCode%>'; //customerCode
                    subArr[16] = "<%= customerName%>"; //customerName
                    projArr[projIdx] = subArr;
                    projIdx++;
                  <%
                        }
                  %>
                  setProject(projArr);
                  </script>
                  <%
                      }
                  %>
            </tr>
            
            <tr id="costCommentTR" style="display: none">
                <td class="tdblueL">원가 비고사항</td>
                <td class="tdwhiteL0" colspan="3" >
                    <textarea name="costComment" class='txt_field' style="height:70px;width:99%;"></textarea>
                </td>
            </tr>
            
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01564")%><%--부품--%></td>
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
                  <%
                      if (outputOids != null) {
                        for (ProjectOutput output : outputs) {
                            E3PSTask task2 = (E3PSTask) output.getTask();
                            project = (E3PSProject) task2.getProject();
                            List<HashMap<String, String>> list = E3PSProjectHelper.service.getProductInfoWTPart(CommonUtil.getOIDString(project));
                            if (list != null) {
                  %>
                  <script>
                      var partArrs = new Array();
                      var p_idx = 0;
                  <%
                        	    for (HashMap<String, String> partMap : list) {
                  %>
                      var partArr = new Array();
                      partArr[0] = '<%=partMap.get("partoid")%>';
                      partArr[1] = '<%=partMap.get("partnumber")%>';
                      partArr[2] = "<%=partMap.get("partname")%>";
                      partArrs[p_idx] = partArr;
                      p_idx++;
                  <%
                                }
                  %>
                      //setPartInfoSbl(partArrs);
                  <%
                            }
                        }
                      }
                  %>
                  </script>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667")%><%--주 첨부파일--%> <span class="red">*</span></td>
              <td colspan="3" class="tdwhiteL0"><input name="iFile0" type="file" class="txt_fieldRO" style="width: 100%"></td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
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
                    <tbody id="fileTable">
                    </tbody>
                  </table>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="4">
                <textarea name="documentDescription" rows="0" cols="0" style="display: none" ></textarea> 
                <textarea name="webEditorText" rows="0" cols="0" style="display: none"></textarea> <!-- Editor Area Container : Start -->
                <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</body>
</html>
