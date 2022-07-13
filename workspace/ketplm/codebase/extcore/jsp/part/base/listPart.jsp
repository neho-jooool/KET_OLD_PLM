﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->

<style>
input[type="radio"] {width:13px; height:13px; margin:0 0 0px; padding:0; border: 1px solid #FFF; vertical-align:middle;}
</style>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->

<!-- 부품 공통 js -->
<script src="/plm/extcore/js/part/partUtil.js"></script>
<!-- 부품 그리드 -->
<script src="/plm/extcore/js/part/partList.js"></script>
<script type="text/javascript">

//UI 초기화
$(document).ready(function(){
    //------------ start suggest binding ------------//
    SuggestUtil.bind('PARTNO', 'partNumber');
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
    SuggestUtil.bind('ECONO', 'ecoNo');
    SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
    SuggestUtil.bind('EPMNO', 'relateEpmNo');
    SuggestUtil.bind('PARTNO', 'partProdNumber');
    //------------ end suggest binding ------------//
    // default 한달 설정
    //$('[name=startDate]').val(predate);
    //$('[name=endDate]').val(postdate);
    // Calener field 설정
    /*
    CalendarUtil.dateInputFormat('planDate');
    CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다.
    */
    // multiselect
    CommonUtil.multiSelect('partStateCode',100);
    CommonUtil.multiSelect('spPartDevLevel',100);
    CommonUtil.multiSelect('division',100);
    /*
    
    CommonUtil.multiSelect('customerEvent2',100);
    CommonUtil.multiSelect('pjtState2',100);
    CommonUtil.multiSelect('authorStatus',100);
    */
    CalendarUtil.dateInputFormat('createStartDate','createEndDate'); 
    //client paging
    //Sample.createGrid();
    //server paging
    
    $("[name=PartSearchForm]").keypress(function(e) {
        if ( e.which == 13 ) {
            searchPart();
            return false;
        }
    });
    
    // Die 부품 유형 체크박스 선택에 따라서 '제품번호 제품명' 보여줌
    $("input[name='_spPartType_D']").on("change", function () {
        if(this.checked){
            $("#dieProdTr").show();
        }else{
            $("#dieProdTr").hide();
        }
    });       
    
    PartList.createPaingGrid();
    
    treeGridResize("#PartListSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

// [START] 부품번호 입력 팝업
function inputPartNumberPopup() {
  var url = "/plm/jsp/bom/InputPartNumberPopup.jsp";
  var partNumbers = window.showModalDialog(url, window, "help=no; resizable=yes; status=no; scroll=yes; dialogWidth=520px; dialogHeight:600px; center:yes");
  if (typeof partNumbers != "undefined" && partNumbers.length > 0) {
    $("#partNumber").val(partNumbers);
    //initPartNumberTextbox();
  }
}

////////////////////////////////////////////////////////////////////////////////////
// 검색조건관련 function 시작
////////////////////////////////////////////////////////////////////////////////////

function loadPartProperty(checkedNode){
	
	var nodeOIdStr='', nodeNameStr='';
    for(var i=0; i < checkedNode.length; i++){
        if(i == checkedNode.length - 1){
            nodeOIdStr += checkedNode[i].id;
            nodeNameStr += checkedNode[i].name;
        }else{
            nodeOIdStr += checkedNode[i].id+','; 
            nodeNameStr += checkedNode[i].name+',';
        }
    }
    
    var valueField = 'partClazOid';
    var displayField = 'partClazKrName';
    $('[name='+valueField+']').val(nodeOIdStr);//oid
    $('[name='+displayField+']').val(nodeNameStr);//kr name
    
    showProcessing();
    
    document.partPropsIfr.location = "/plm/ext/part/base/registPropsMix.do?oid=" + nodeOIdStr;
}

function setPartNumbers(objArr){
	setPartNo(objArr, 'partNumber');
}

function setPartProdNumber(objArr){
    setPartNo(objArr, 'partProdNumber');
}

function setProjectNo(objArr){
    var projectNo= "";
    var projectName= "";
    for ( var i = 0; i < objArr.length; i++ ) {
        projectNo = objArr[i][1];
        projectName = objArr[i][2];
    }
    
    //hideProcessing();
    
    $('[name=projectNo]').val(projectNo);
    $('[name=projectName]').val(projectName);
    
}

function setEpmNo(objArr){
	setPartNo(objArr, 'relateEpmNo');
}

////////////////////////////////////////////////////////////////////////////////////
//검색조건관련 function 끝
////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
// iframe - 분류체계관련된 function 시작
////////////////////////////////////////////////////////////////////////////////////

//iframe 로딩후 table에 붙여 넣기
var spMMoldAt_check_val = "";
function addToTable(){
    
    $('#mainTable > #partSpecPropTr').remove();
    
    var trArrayVal = $('#partPropsIfr').contents().find("#partSpecPropTbody").html()
    if(trArrayVal) {
        //alert(trArrayVal);
        $('#mainTable').append(trArrayVal);
        $("select[ketMultiSelect=N]").each(function(){
            //CommonUtil.singleSelect(this.name, 100);
        	CommonUtil.multiSelect(this.name, 100);
        });
        $("select[ketMultiSelect=Y]").each(function(){
            CommonUtil.multiSelect(this.name, 100);
        });
        //$('#mainTable').html(trArrayVal);
        //$('#partPropsIfr').html("");
        //CommonUtil.singleSelect('spCertified',100);
        
        SuggestUtil.bind('PARTNO', 'spMTerminal');
        SuggestUtil.bind('PARTNO', 'spMatchBulb');
        SuggestUtil.bind('PARTNO', 'spMConnector');
        SuggestUtil.bind('PARTNO', 'spMClip');
        SuggestUtil.bind('PARTNO', 'spMRH');
        SuggestUtil.bind('PARTNO', 'spMCover');
        SuggestUtil.bind('PARTNO', 'spMWireSeal');
        
        SuggestUtil.bind('PARTNO', 'spScrabCode');
        SuggestUtil.bind('PARTNO', 'spRepresentM');
        
        SuggestUtil.bind('USER', 'spDevManNm', 'spDevManNmOid');
        SuggestUtil.bind('USER', 'spDesignManNm', 'spDesignManNmOid');
        SuggestUtil.bind('USER', 'spManufacManNm', 'spManufacManNmOid');
        
        CommonUtil.setNumberField('spNoOfPole');
        
        // 생산처 초기화
        $("#spManufAt").change(function(){
            $("#spManufacPlace").val("");
            $("#spManufacPlaceTemp").val("");
        });
        $("#spMContractSAt").change(function(){
             $("#spDieWhere").val("");
             $("#spDieWhereTemp").val("");
        });
    }
    
    hideProcessing();
}

//irame 로드후 채번 코드 입력
function setPartNumber(numberingCodeByClaz){
    //alert(numberingCodeByClaz);
    //$('#partNumber').val("");
    //$('#partNumber').val(numberingCodeByClaz);
}

//부품 검색 결과 저장
function setPartNospMTerminal(objArr){ setPartNo(objArr, 'spMTerminal'); }
function setPartNospMatchBulb(objArr){ setPartNo(objArr, 'spMatchBulb'); }
function setPartNospMConnector(objArr){ setPartNo(objArr, 'spMConnector'); }
function setPartNospMClip(objArr){ setPartNo(objArr, 'spMClip'); }
function setPartNospMRH(objArr){ setPartNo(objArr, 'spMRH'); }
function setPartNospMCover(objArr) { setPartNo(objArr, 'spMCover'); }
function setPartNospMWireSeal(objArr){ setPartNo(objArr, 'spMWireSeal'); }
function setPartNospScrabCode(objArr){ setPartNo(objArr, 'spScrabCode'); }
function setPartNospRepresentM(objArr){ setPartNo(objArr, 'spRepresentM'); }
function setPartNo(arrObj, targetId) {
    var isAppend = "Y";
    var partNoArr = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        partNoArr[i] = arrObj[i][1];
    }

    var tmpNo = new Array();
    var tmpName = new Array();
    if ( $("[name="+targetId+"]").val() != "" && isAppend == "Y" ) {
        tmpNo = $.merge($("[name="+targetId+"]").val().split(","), partNoArr);
        tmpNo = $.unique(tmpNo.sort());
        tmpNo = removeDups(tmpNo );
    }
    else {
        tmpNo = partNoArr.sort();
    }

    $("[name="+targetId+"]").val(tmpNo.join(","));
}
function removeDups(arr) {
    var result = [], map = {}, item;
    for (var i = 0; i < arr.length; i++) {
        item = arr[i];
        if (!map[item]) {
            result.push(item);
            map[item] = true;
        }
    }
    return(result);
}

//var autoGenOid = ""; // partName
function setAutoGenOid(namingOid){
    //if(namingOid && namingOid !="")
    //  alert("namingOid:" + namingOid);
        
    //autoGenOid = namingOid;
}

//생산처, 금형제작처 가져오기

//일반제품의 경우 : 생산처구분 [spManufAt:NumberCode => SPECMSELFCONTRACTFLAG] 이 사내인가 외주인가에 따라서
//생산처[spManufacPlace]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 
//금형SET의 경우 : 사급구분 [spMContractSAt:NumberCode => SPECMSELFCONTRACTFLAG]이 사내인가 외주인가
//금형제작처[spDieWhere]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 
var attrName_ = "";
var suffix_ = "";
function setManuFacPlace( attrName, suffix ) {
 attrName_ = attrName;
 suffix_ = suffix;
 var gubun = "";
 if(attrName == 'spManufacPlace'){
     gubun = $("#spManufAt").val();
     if( typeof gubun == "undefined" || gubun == null || gubun == ""){
         alert("'생산처 구분' 값에서 사내/외주를 먼저 선택해 주십시요.");
         return;
     } 
 }else if(attrName == 'spDieWhere'){
     gubun = $("#spMContractSAt").val();
     if( typeof gubun == "undefined" || gubun == null || gubun == ""){
         alert("'사급구분' 값에서 사내/외주를 먼저 선택해 주십시요.");
         return;
     }
 }
     
 if(gubun == "1"){
 
     var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode=single&viewType=noTree&invokeMethod=addManuFacPlace";
     var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
     
     getOpenWindow2(url,'partList', '900', '800', opts);
     
    
 }else if(gubun = "2"){
     
     var callBackFuc = "addKetPartner";
     var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method="+callBackFuc;
        openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
 }
 
}

//사내 생산처 등록
function addManuFacPlace(arr){
 var attrName = attrName_; 
 var suffix = suffix_; 
 if(arr.length == 0) { return; }

 var displayName = document.getElementById(attrName + suffix);
 var keyName = document.getElementById(attrName);

 for(var i = 0; i < arr.length; i++) {
     var infoArr = arr[i];
     displayName.value = infoArr[2];
     keyName.value = infoArr[1];
 }
}

//외주 협력사 선택
function addKetPartner(arr){

 if(arr.length == 0) {
     alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
     return;
 }
 
 $("#spDieWhere").val(arr[0][0]);
 $("#spDieWhereTemp").val(arr[0][1]);
 
 $("#spManufacPlace").val(arr[0][0]);
 $("#spManufacPlaceTemp").val(arr[0][1]);

}

////////////////////////////////////////////////////////////////////////////////////
//iframe - 분류체계관련된 function 끝
////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
// 버튼 제어관련 function 시작
////////////////////////////////////////////////////////////////////////////////////

// 초기화
function initialize(){
	
	$('#mainTable > #partSpecPropTr').remove();
	$('[name=PartSearchForm]')[0].reset();
	$('#partClazOid').val("");
}

// 부품등록
function createPartPopup(){

    PartUtil.regView();
}

function searchPart(){

	// 검색 조건 2가지
    var projectNo = $("#projectNo").val() + "";
    var relateEpmNo = $("#relateEpmNo").val() + "";
    var ecoNo = $("#ecoNo").val() + "";
    var partNumber = $("#partNumber").val() + "";

    if(
        ( projectNo != ""      && projectNo.indexOf('*') == -1 ) || 
        ( relateEpmNo != ""    && relateEpmNo.indexOf('*') == -1 ) ||
        ( ecoNo != ""          && ecoNo.indexOf('*') == -1 ) ||
        ( partNumber != ""     && partNumber.indexOf('*') == -1 ) 
    ){
    	//alert(projectNo);
    	//alert(relateEpmNo);
    	//alert(ecoNo);
    	//alert(partNumber);
    }else{
        
        var checkTwoCount = 0;
        // 검색 조건 2가지 이상
        // 부품 유형
        if(       $('input:checkbox[id="_spPartType_F"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_H"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_R"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_S"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_K"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_W"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_D"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_M"]').is(":checked") == true ){
            checkTwoCount++;
        }else if( $('input:checkbox[id="_spPartType_O"]').is(":checked") == true ){
            checkTwoCount++;
        }
        
        // 4M 선택
        if( $('input:checkbox[id="completed4m"]').is(":checked") == true ){
            checkTwoCount++;
        }
        
        if($("#partStateCode  option").index($("#partStateCode option:selected")) != -1 ){
            checkTwoCount++;
        }
        if($("#spPartDevLevel option").index($("#spPartDevLevel option:selected")) != -1 ){
            checkTwoCount++;
        }
        if($("#partClazOid").val() != "" ){
            checkTwoCount++;
        }
        if($("#partNumber").val() != "" ){
            checkTwoCount++;
        }
        if($("#partName").val() != "" ){
            checkTwoCount++;
        }
        if($("#spPartRevision").val() != "" ){
            checkTwoCount++;
        }
        if($("#projectNo").val() != "" ){
            checkTwoCount++;
        }
        if($("#ecoNo").val() != "" ){
            checkTwoCount++;
        }
        if($("#relateEpmNo").val() != "" ){
            checkTwoCount++;
        }
        if($("#creatorName").val() != "" ){
            checkTwoCount++;
        }
        if($("#createStartDate").val() != "" ){
            checkTwoCount++;
        }
        if($("#createEndDate").val() != "" ){
            checkTwoCount++;
        }
        if($("#partProdNumber").val() != "" ){
            checkTwoCount++;
        }
        if($("#partProdName").val() != "" ){
            checkTwoCount++;
        }
        
        // 검색 조건 2가지 이상  
        //alert(checkTwoCount);
        if( checkTwoCount < 2){
            alert(" 부품번호, 도면번호, ECO 번호, 프로젝트 번호란에\n Full Code를 넣거나\n 검색 조건을 2가지 이상 추가해야 검색 가능 합니다.");
            return;
        }
    }
    
    var spPartTypeVal = "";
    $("input[name^=_spPartType]" ).each(function(){
        if(this.checked){
        	
        	if(this.value == "A"){
        		spPartTypeVal = "";
        		return false;
        	}
        	
        	if(spPartTypeVal == "")
       		   spPartTypeVal = spPartTypeVal + this.value;
        	else
       		   spPartTypeVal =  spPartTypeVal + "," + this.value;
        }	
    });
    
    $("[name=spPartType]").val(spPartTypeVal);
    
    PartList.search();
}

function exportCatalog(){
	
	var url = "/plm/ext/part/base/catalogueList.do";
    document.forms[0].method = "post";
    document.forms[0].target = "excelDownIfr";
    document.forms[0].action = url;
    document.forms[0].submit();
}

////////////////////////////////////////////////////////////////////////////////////
//버튼 제어관련 function 끝
////////////////////////////////////////////////////////////////////////////////////



$(document).on("change", "[name=completed4m]", function() {
    var checked = $(this).prop("checked");
    if(checked) {
    	$("input:radio[name='partLatestReVision']:radio[value='true']").prop("checked",false);
    	$("input:radio[name='partLatestReVision']:radio[value='false']").prop("checked",true);
    	//$("input:radio[name='partLatestReVision']").prop("disabled", true);
    } else {
    	$("input:radio[name='partLatestReVision']:radio[value='true']").prop("checked",true);
        $("input:radio[name='partLatestReVision']:radio[value='false']").prop("checked",false);
        //$("input:radio[name='partLatestReVision']").prop("disabled", false);
    }
});

</script>
</head>
<body class="popup-background body-space" >
<table style="width:100%;" id="cndHeader" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table style="width:100%; height:28px;" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">부품</td>
                                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif">부품관리<%--작업공간--%><img src="/plm/portal/images/icon_navi.gif">부품</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td  class="head_line"></td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <c:if test="${isWcadmin}">
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:exportCatalog();" class="btn_blue">Catalog Export</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            </c:if>
                            <c:if test="${isSpecAdmin}"><td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:createPartPopup();" class="btn_blue">부품등록</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            </c:if>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:initialize();" class="btn_blue">초기화</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:searchPart();" class="btn_blue">검색</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
            </table>                
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>		 
		    <form name="PartSearchForm">
		    <input type="hidden" name="spPartType" value="" />
		    <input type="hidden" id="inputPartNos" name="inputPartNos" value="">
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <div style="display: none">지우지 마세요</div>
			<table id="mainTable" cellpadding="0" class="table-style-01" summary="">
				<colgroup>
					<col width="13%" />
					<col width="20%" />
					<col width="13%" />
					<col width="20%" />
					<col width="13%" />
					<col width="21%" />
				</colgroup>
				<tbody>
					<tr>
						<td class="title">부품유형</td>
						<td colspan="3">
						<input id="_spPartType_A" name="_spPartType_A" type="checkbox" value="A" />전체&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_F" name="_spPartType_F" type="checkbox" value="F" />제품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_H" name="_spPartType_H" type="checkbox" value="H" />반제품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_R" name="_spPartType_R" type="checkbox" value="R" />원자재&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_S" name="_spPartType_S" type="checkbox" value="S" />스크랩&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_K" name="_spPartType_K" type="checkbox" value="K" />포장재&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_D" name="_spPartType_D" type="checkbox" value="D" />Die No&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_M" name="_spPartType_M" type="checkbox" value="M" />금형부품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_W" name="_spPartType_W" type="checkbox" value="W" />상품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_O" name="_spPartType_O" type="checkbox" value="O" />기타&nbsp;&nbsp;&nbsp;
						<input id="spIsYazaki"    name="spIsYazaki"    type="checkbox" value="YES" />YAZAKI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;
                        <input id="partLatestReVision" name="partLatestReVision" type="radio" value="true" checked />&nbsp;최신&nbsp;&nbsp;
                        <input id="partLatestReVision" name="partLatestReVision" type="radio" value="false" />&nbsp;전체
					    </td>
					    <td class="title">사업부</td>
                        <td>
                        <select id="division" name="division" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="사업부">
                        <option value="C">자동차사업부</option>
                        <option value="E">전자사업부</option>
                        <option value="K">KETS</option>
                        <option value="N">KETN</option>
                        </select></td>
					</tr>
					<tr>
						<td class="title">부품분류</td>
						<td>
						<input type="text" id="partClazKrName" name="partClazKrName" class="txt_field" >&nbsp;&nbsp;
                        <input type="hidden" id="partClazOid" name="partClazOid" value="" >
                        <a href="javascript:SearchUtil.selectPartClaz('loadPartProperty','onlyLeaf=N&openAll=N');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;
                        <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                        <td><input type="text" id="partNumber" name="partNumber" class="txt_field" style="width: 70%" value="" >
                           <input type="text" id="partNumberTemp" name="partNumberTemp" class="txt_fieldRO" readonly style="width: 70%; display: none;">
                           <a href="javascript:inputPartNumberPopup('partNumber');">
                           <img src="/plm/portal/images/icon_5.png" border="0"></a>
                           <a href="javascript:CommonUtil.deleteValue('partNumber');">
                           <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                       </td>
                        <%--
                        <td class="title">부품번호</td>
                        <td><input type="text" id="partNumber" name="partNumber" class="txt_field" style="width: 70%" >
                            <a href="javascript:SearchUtil.selectPart('setPartNumbers');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partNumber');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                         --%>
						<td class="title">부품명</td>
						<td><input id="partName" name="partName" type="text" class="txt_field" style="width: 98%" value="" ></td>
					</tr>
					<tr>
						<td class="title">Rev</td>
						<td><input id="spPartRevision" name="spPartRevision" type="text" class="txt_field" style="width: 95%" value="" ></td>
						<td class="title">상태</td>
						<td><ket:select id="partStateCode" name="partStateCode" className="fm_jmp" style="width: 170px;" lifeCycleState="KET_PART_LC" multiple="multiple" otherHtml=" " /></td>
						<td class="title">프로젝트 번호</td>
						<td>
						<input type="text" id="projectNo" name="projectNo" value="" esse="true" esseLabel="프로젝트번호"  />&nbsp;&nbsp;
                        <a href="javascript:SearchUtil.selectOneProject('setProjectNo');">
                        <img src="/plm/portal/images/icon_5.png" /></a>&nbsp;&nbsp;
                        <a href="javascript:CommonUtil.deleteValue('projectNo','projectName');">
                        <img src="/plm/portal/images/icon_delete.gif" /></a>
                        </td>
					</tr>
					<tr>
						<td class="title">EO 번호</td>
						<td><input type="text"  id="ecoNo" name="ecoNo" value="" style="width:95%" /></td>
						<td class="title">도면번호</td>
						<td>
						<input type="text" id="relateEpmNo" name="relateEpmNo" class="txt_field" style="width: 70%" >
                            <a href="javascript:SearchUtil.selectDrawing(setEpmNo);">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('relateEpmNo');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="title">개발단계</td>
                        <td>
                        <select id="spPartDevLevel" name="spPartDevLevel" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
                        <option value="PC001">Proto</option>
                        <option value="PC003">개발</option>
                        <option value="PC004">양산</option>
                        </select></td>
					</tr>
					<tr>
						<td class="title">작성일</td>
						<td class="tdwhiteL" colspan="3">

	                            <input type="text" id="createStartDate" name="createStartDate" class="txt_field" style="width: 70px;" value="">
	                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createStartDate');" style="cursor: hand;"> 
	                            ~ 
	                            <input type="text" id="createEndDate" name="createEndDate" class="txt_field" style="width: 70px;">
	                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createEndDate');"
	                            style="cursor: hand;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            <input type="checkbox" name="completed4m" id="completed4m" value="Y">&nbsp;&nbsp;4M 완료여부&nbsp;
                        </td>
                        <td class="title">작성자</td>
                        <td>
                        <input type="text" id="creatorName" name="creatorName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="creatorOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('creatorOid','creatorName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('creatorOid','creatorName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
					</tr>
					<tr id="dieProdTr" style="display: none;">
						<td class="title">제품번호</td>
						<td><input type="text" id="partProdNumber" name="partProdNumber" class="txt_field" style="width: 70%" value="" >
	                        <a href="javascript:showProcessing();SearchUtil.selectPart('setPartProdNumber', 'pType=H');">
	                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	                        <a href="javascript:CommonUtil.deleteValue('partProdNumber');">
	                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="title">제품명</td>
                        <td colspan="3"><input id="partProdName" name="partProdName" type="text" class="txt_field" style="width: 98%" value="" ></td>
					</tr>
				</tbody>
			</table>
			</form>
			<iframe id="partPropsIfr" name="partPropsIfr" onLoad="javascript:addToTable();" src="" width="0px" height="0px" frameborder="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                        </div>
                        <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
           </td>
           </tr>
           </table>
            <!-- 
		</div>
	</div>
	 -->
	 <iframe id="excelDownIfr" name="excelDownIfr" src="" width="0px" height="0px" frameborder="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
</body>
</html>