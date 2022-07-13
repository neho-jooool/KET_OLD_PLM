﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%

// TODO TKLEE 전장모듈 표준구매품[식별하는 방법]의 경우 제조사, 제조사품번으로 중복 체크
// 동일하게 등록되어 있는 기존 품번 리스트 Popup Open
// 무시하고 등록 또는 취소

// TODO TKLEE 하위 1레벨 반제품의 Thumbnail

%>
<script type="text/javascript">
<!--

var spPartType_options = "";
$(document).ready(function(){

    // eco 추가
    SuggestUtil.bind('ECONO', 'ecoNo');
    
    CommonUtil.singleSelect('spPartType',100);
    spPartType_options = $('#spPartType').html();
    //alert(spPartType_options);
    
    CommonUtil.singleSelect('spPartDevLevel',100);
    CommonUtil.singleSelect('partDefaultUnit',100);
    
   var tdWidth = 100;
    <c:forEach var="result" items="${baseDto.partClassAttrLinkDTOList}" varStatus="status">
    <c:if test="${result.partAttributeDTO.attrInputType=='SELECT'}">
        <c:choose>  
        <c:when test="${result.partAttributeDTO.attrMultiSelect}">
         CommonUtil.multiSelect('${result.partAttributeDTO.attrCode}',tdWidth);
        </c:when>
        <c:otherwise>
	        <c:choose>  
	        <c:when test="${result.partAttributeDTO.attrCode == 'spSeries'}">
	        </c:when>
	        <c:otherwise>
	            CommonUtil.singleSelect('${result.partAttributeDTO.attrCode}',tdWidth);
	        </c:otherwise>
	        </c:choose>
        </c:otherwise>
        </c:choose>
    </c:if>
    </c:forEach>

    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', null, 'projectName'); // REVIEWPROJNO, PRODUCTPROJNO
    
    // 부품번호 자동생성 체크할 때 이벤트 처리
    // changed
    $('input:checkbox[id="partNumberAutoGen"]').change(function(){
        if($(this).is(":checked")){
            $('#partNumber').attr("readonly", true).val(numberingCodeByClaz);
        } else {
            var partNumber = "${baseDto.partNumber}";
            $('#partNumber').attr("readonly", false).val(partNumber);
        }
    });
    
    // === 프로젝트, 부품중량 필수 체크 ===
    // 프로젝트
    <c:choose>  
    <c:when test="${isEsseProject}">
        $("#projectNoSpan").removeClass("blue-star").addClass("red-star").show();
        $('#projectNo').attr("esse","true");
    </c:when>
    <c:when test="${isCheckedProject}">
        $("#projectNoSpan").removeClass("red-star").addClass("blue-star").show();
        $('#projectNo').attr("esse","false");
    </c:when>
    <c:otherwise>
        $("#projectNoSpan").removeClass("blue-star").removeClass("red-star").hide();
        $('#projectNo').attr("esse","false");
    </c:otherwise>
    </c:choose>

    // 부품 중량
    <c:choose>  
    <c:when test="${isEsseWeight}">
        $("#spNetWeightSpan").removeClass("blue-star").addClass("red-star").show();
        $('#spNetWeight').attr("esse","true");
    </c:when>
    <c:when test="${isCheckedWeight}">
        $("#spNetWeightSpan").removeClass("red-star").addClass("blue-star").show();
        $('#spNetWeight').attr("esse","false");
    </c:when>
    <c:otherwise>
        $("#spNetWeightSpan").removeClass("blue-star").removeClass("red-star").hide();
        $('#spNetWeight').attr("esse","false");
    </c:otherwise>
    </c:choose>
    
    // changed
    // 부품유형 제한 : 부품분류의 [ERP 코드] 가 제품 혹은 반제품 코드만 가지고 있을 경우, 부품유형도 [ERP 코드]가 존재하는 제품 혹은 반제품만 선택 가능
    // 부품유형 : 초기화
    
   
    <c:forEach var="result" items="${baseDto.partClassAttrLinkDTOList}" varStatus="status">
    <c:if test="${result.essential}">
    $('#${result.partAttributeDTO.attrCode}').attr("esse","true").attr("esseLabel","<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>");
    </c:if>
    </c:forEach>
    
    $('body').addClass('popup-background body-space')
    
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
    CommonUtil.setNumberField('spTotalWeight'); 
    CommonUtil.setNumberField('spNetWeight'); 
    CommonUtil.setNumberField('spScrabWeight');
    
    $('#spTotalWeight').number( true ,3 );
    $('#spNetWeight').number( true ,3 );
    $('#spScrabWeight').number( true ,3 );
    
    SuggestUtil.bind('SPSERIES', 'spSeriesName', 'spSeries');
    // 시리즈 초기화
    $("#spSeries").change(function(){
        if($(this).val() == ""){
            $("#spSeriesName").val("");
        }else{
        	selectSeriesUse($(this).val());
        }
    });
    
    // 생산처 초기화
    $("#spManufAt").change(function(){
        $("#spManufacPlace").val("");
        $("#spManufacPlaceTemp").val("");
    });
    $("#spMContractSAt").change(function(){
         $("#spDieWhere").val("");
         $("#spDieWhereTemp").val("");
    });
    
    
    // 원재료 Maker 먼저선택 > 원재료 Type > 재질(수지), 재질(비철)
    $("#spMaterMaker").change(function(){
        if($(this).val() == ""){
            
        }else{
            var clazOid = $('#partClazOid').val();
            var spMaterMaker = $(this).val();
            selectCommonCode("spMaterType", "/plm/ext/part/base/getMaterialTypeList.do?clazOid=" + clazOid + "&spMaterMaker=" + spMaterMaker );
        }
    });
    
    $("#spMaterType").change(function(){
        if($(this).val() == ""){
            
        }else{
            var clazOid = $('#partClazOid').val();
            var spMaterMaker = $('#spMaterMaker').val();
            var spMaterType = $(this).val();
            if(document.forms[0].spMaterialInfo){
            selectCommonCode("spMaterialInfo", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=" + clazOid + "&spMaterMaker=" + spMaterMaker + "&spMaterType=" + spMaterType );
            }
            if(document.forms[0].spMaterNotFe){
            selectCommonCode("spMaterNotFe", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=" + clazOid + "&spMaterMaker=" + spMaterMaker + "&spMaterType=" + spMaterType );
            }
        }
    });
    
    SuggestUtil.bind('PARTCLAZ', 'partClazKrName', 'partClazOid'); //  부품 분류 BIND
    $('#partClazOid').change(function(){
        if( $(this).val() == '' ){
            CommonUtil.deleteValue('partClazKrName','partClazOid');
            $('#mainTable #partSpecPropTr').remove();
        }else{
            showProcessing();
            document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?oid=" + $(this).val();
        }
    });
    
    displayAttachImgArea();
    
    $('#spPartType').change(function(){
        
        $("#spPartType option:selected").each(function () {
            displayHp($(this).val());
        });
    });
    
    
    if('${baseDto.spPartType}' == 'F' ||  '${baseDto.spPartType}' == 'H'  ){
    	
    	$("#hompageTd").css("display","table-row");
         CommonUtil.singleSelect('homepageIF',100);
         CommonUtil.singleSelect('homepage2DIF',100);
         CommonUtil.singleSelect('hompage3DIF',100);            
   }
    
    
}); // end ready

function displayHp(val){
    try{
        if(val == 'F' || val == 'H' ){
            $("#hompageTd").css("display","table-row");
            CommonUtil.singleSelect('homepageIF',100);
            CommonUtil.singleSelect('homepage2DIF',100);
            CommonUtil.singleSelect('hompage3DIF',100);            
            
        }else{
            $("#hompageTd").css("display","none");
        }
    }catch(e){
    }
}



function displayAttachImgArea(){
    try{
        if ($("#hompage3DIF").val() == "YES" || $("#hompage3DIF").val() == "" ){
            $("#partSpecPropTrhompageImgIF").css("display","none");
        }else{
            $("#partSpecPropTrhompageImgIF").css("display","table-row");
        }
    }catch(e){
    }
}

function selectCommonCode(target, targetUrl){
    
    $.ajax({        
        url: targetUrl,     
        async: false,
        dataType: "json",       
        success: function(data) {
            var options, index, select, option;
            // Get the raw DOM object for the select box
            select = document.getElementById(target);
            // Clear the old options
            select.options.length = 0;
            // Load the new options
            options = data.options; // Or whatever source information you're working with
            for (index = 0; index < options.length; ++index) {
                option = options[index];
                select.options.add(new Option(option.text, option.value));
            }
            select.selectedIndex = -1;
            $("#" + target).multiselect('refresh');
            if(target && target == "spMaterType"){
                $("#spMaterialInfo").empty();
                $("#spMaterialInfo").multiselect("refresh");
                $("#spMaterNotFe").empty();
                $("#spMaterNotFe").multiselect("refresh");
            }
       }
    });
}

function checkProjectDevLevel(projectNo){
    var ret = "N";
    $.ajax({
        type: 'get',
        async: false,
        url: '/plm/ext/part/base/getProjectDevLevel.do?projectNumber='+projectNo,
        success: function (data) {
            if(data != 'Fail'){
                ret = data;
            }else{
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
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
    <%--
    var checkStr = checkProjectDevLevel($('[name=projectNo]').val());
    //alert(checkStr);
    if("E" == checkStr){
    }else {
        if(checkStr && ( checkStr=="PC003" || checkStr =="PC004" )){
            $("#spPartDevLevel").val(checkStr);
            $("#spPartDevLevel").multiselect('refresh');
        }
    }
    --%>
}

function loadPartProperty(returnValue){
	var valueField = 'partClazOid';
	var displayField = 'partClazKrName';
	$('[name='+valueField+']').val(returnValue[0].id);//oid
    $('[name='+displayField+']').val(returnValue[0].name);//kr name
    
    showProcessing();
    document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?oid=" + returnValue[0].id;
    
}

var testId = 1;
function savePart(){
    var data1 = "";
    var data2 = "";
    var data3 = "";
    
    if($("#partNumber").val().search(/\s/) != -1) {
    	alert('부품번호에 공백이 포함되서는 안됩니다.');
        return;
    }
    
    var partType = $("#spPartType").val();
    
    var partName = $("#partName").val();
    
    var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크
    
    if(pattern_kor.test(partName)) {
        alert("부품명에 한글을 포함 할 수 없습니다.");
        return;
    }
    
    var length = $("#partName").val().length;
    
    var partPrefix = $("#partName").val().substring(0,1);
    var partSuffix = $("#partName").val().substring(length-1,length);
    
    if(partPrefix == ' ' || partSuffix == ' '){
        alert('부품명의 처음이나 마지막에 공백이 들어 갈 수 없습니다.');
        return;
    }
    
    if(length > 40){
        alert('부품명의 길이는 40자를 초과할수없습니다. 입력된 자릿수 : '+length);
        return;
    }
    
    if(partName.indexOf("'") > -1 || partName.indexOf('"') > -1){//품명에 홑따옴표, 쌍따옴표 제한 2017.06.28 김명국 차장 요청 by 황정태
        alert("부품명에 홑따옴표 또는 쌍따옴표가 포함되면 안됩니다.");
        $("#partName").focus();
        return;
    }
    
    if(partType == 'F' || partType == 'H' || partType == 'W') {//차주원 차장님 요청 2018.05.04
        if("KET_EA" != $("#partDefaultUnit option:selected").val()){
            alert("제품/반제품/상품은 부품 기본단위로 'EA'를 선택해야 합니다.");    
            return;
        }   
    }
    
    
    //요청에 의해 한시적으로 mm입력을 제한함(차주원 차장님) 2016.05.04 by 황정태
    if("KET_MM" == $("#partDefaultUnit option:selected").val()){
        alert("기본단위 mm은 선택할 수 없습니다.");    
        return;
    }
    //mg, g 입력 제한(g의 경우 거의 발생하지 않으므로 일단 차단 2017.05.02 by 황정태 - 차주원 차장님 요청)
    if("KET_MG" == $("#partDefaultUnit option:selected").val()){
        alert("기본단위 mg은 선택할 수 없습니다.");    
        return;
    }
    
    if("KET_G" == $("#partDefaultUnit option:selected").val()){
        alert("기본단위 g은 선택할 수 없습니다.");    
        return;
    }
    
    if(partType == 'F' || partType == 'H') {
        $("#homepageIFTd button span").each(function (index) {
            if(index == "1") data1 = $(this).text();
        });
        $("#homepage2DIFTd button span").each(function (index) {
            if(index == "1") data2 = $(this).text();
        });
        $("#hompage3DIFTd button span").each(function (index) {
            if(index == "1") data3 = $(this).text();
        });
        
        if(data1 == '선택') data1 = "";
        if(data2 == '선택') data2 = "";
        if(data3 == '선택') data3 = "";
        
        
        $("#homepageIF").val(data1);
        $("#homepage2DIF").val(data2);
        $("#hompage3DIF").val(data3);
    
    
        if($("#homepageIF").val() == null || $("#homepageIF").val() == ''){
            alert("홈페이지 등록은 필수입니다.");
            return;
        }

        if($("#homepage2DIF").val() == null || $("#homepage2DIF").val() == ''){
            alert("홈페이지(2D) 등록은 필수입니다.");
            return;
        }
        
        if($("#hompage3DIF").val() == null || $("#hompage3DIF").val() == ''){
            alert("홈페이지(3D) 등록은 필수입니다.");
            return;
        }
        
        
    }
    
	var form = document.forms[0];
	//저장시에는 사용자의 판단에 따르도록 해야할 것 같아서 아래 강제 지정 로직은 주석처리함
	/* if(form.spStandardConnect && form.spSeries){//선택한 시리즈에 따라 용도를 세팅한다
        selectSeriesUse($("#spSeries").val());
    } */
	
	// validation check
	if(!CommonUtil.checkEsseOk()){
		return;
	}
    
	$("#partName").focus();
    if( $("#spTotalWeight").val() == "" && $("#spNetWeight").val() == "" && $("#spScrabWeight").val() == ""){
        
    }else{
        
        // 부품중량
        var partWeight = $("#spNetWeight").val();
        if(partWeight == ""){ partWeight = "0";}
        
        if(isNaN(partWeight)){
            alert("부품중량 '" + partWeight + "'이 숫자포멧이 아닙니다.");
            $("#spNetWeight").focus();
            return;
        }
        
        if(partWeight.indexOf('.') != -1){
            var partWeightTemp = partWeight.substring(partWeight.indexOf('.')+1);
            if(typeof partWeightTemp != "undefined" && partWeightTemp.length > 3){
                partWeight = partWeight.substring(0, partWeight.indexOf('.')+1) + partWeightTemp.substring(0,3);
                $("#spNetWeight").val(partWeight);
            }
        }
        var partWeightF = parseFloat(partWeight);
        
        // 총중량
        var total = $("#spTotalWeight").val();
        if(total == ""){ total = "0";}
        
        if(isNaN(total)){
            alert("총중량 '" + total + "'이 숫자포멧이 아닙니다.");
            $("#spTotalWeight").focus();
            return;
        }
        
        if(total.indexOf('.') != -1){
            var totalTemp = total.substring(total.indexOf('.')+1);
            if(typeof totalTemp != "undefined" && totalTemp.length > 3){
                total = total.substring(0, total.indexOf('.')+1) + totalTemp.substring(0,3);
                $("#spTotalWeight").val(total);
            }
        }
        var totalF = parseFloat(total);
        
        // 스크랩중량
        var scrabWeight = $("#spScrabWeight").val();
        if(scrabWeight == ""){ scrabWeight = "0";}
        
        if(isNaN(scrabWeight)){
            alert("스크랩중량 '" + scrabWeight + "'이 숫자포멧이 아닙니다.");
            $("#spScrabWeight").focus();
           return;
        }
        
        if(scrabWeight.indexOf('.') != -1){
            var scrabWeightTemp = scrabWeight.substring(scrabWeight.indexOf('.')+1);
            if(typeof scrabWeightTemp != "undefined" && scrabWeightTemp.length > 3){
                scrabWeight = scrabWeight.substring(0, scrabWeight.indexOf('.')+1) + scrabWeightTemp.substring(0,3);
                $("#spScrabWeight").val( scrabWeight );
            }
        }
        var scrabWeightF = parseFloat(scrabWeight).toFixed(3);
        
        // 계산식
        if( parseFloat(totalF - partWeightF).toFixed(3) < 0) {
            alert("'총중량'이 '부품중량' 보다 작을 수 없습니다.");
            return;
        }
        
        if( parseFloat(totalF - partWeightF).toFixed(3) != scrabWeightF ) {
            alert("'총중량' - '부품중량'은 '스크랩중량'과 일치해야 합니다.");
            return;
        }
    }
	
	// 금형부품일경우 8자리 + '-' + @
	// 금형부품은 EA만 선택
    ///*
    var checkMoldLength = true;
    $("#spPartType option:selected").each(function () {
        if( $(this).val() == 'M' ){
            if($('#partNumber').val().length < 10){
                alert("금형부품은 Die No(8자리)에 '-'를 붙이고 여기에 추가 코드를 입력해야 합니다.");
                $('#partNumber').focus();
                checkMoldLength = false;
            }
            
            if("KET_EA" != $("#partDefaultUnit option:selected").val()){
                alert("금형부품은 부품 기본단위로 'EA'를 선택해야 합니다.");   
                checkMoldLength = false;
            }
        }
    });
    if(!checkMoldLength) return;
    //*/
    
    // Die No, 금형부품 일경우 개발단계는 'T-Car' 선택하면 안됨.
    var checkDieMoldDevLevel = true;
    $("#spPartType option:selected").each(function () {
        if( $(this).val() == 'M' || $(this).val() == 'D' ){
            $('#spPartDevLevel option:selected').each(function () {
                if( $(this).val() == 'PC001'){
                    alert("Die No와 금형부품은 'T-CAR' 단계를 선택할 수 없습니다.");
                    checkDieMoldDevLevel = false;
                }
            });
                    
            if(!checkDieMoldDevLevel) return false;
        }
    });
    if(!checkDieMoldDevLevel) return;
    
    // 원자재 G등록 방지
    var checkRohDefaultG = true;
    $("#spPartType option:selected").each(function () {
        if( $(this).val() == 'R' ){
        	if("KET_G" == $("#partDefaultUnit option:selected").val()){
                alert("'원재료'는 기본단위 'g'을 선택할 수 없습니다.\n다른 기본 단위를 선택해 주세요.");
                hideProcessing();
                checkRohDefaultG = false;
                return;
            }
        }
    });
    if(!checkRohDefaultG) return;
    
    // 부품의 YAZAKI제번 체크
    if(form.spIsYazaki && form.spYazakiNo && $('#spIsYazaki').val() == "YES"){
        if("" == $('#spYazakiNo').val()){
           alert("'YAZAKI여부'가 'YES'이면 'YAZAKI제번'에 값을 입력해야 합니다.");
           return;
        }
    }
    
    // 매칭터미널, 매칭 커넥터, 매칭Clip, 매칭R/H', 매칭 Wire Seal 부품 번호 체크로직 추가
    showProcessing();
    if(form.spMTerminal && $('#spMTerminal').val() != ""){
        var checkStr = checkPartNumber($('#spMTerminal').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'매칭터미널'의 값이 존재하지 않는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }

    if(form.spMatchBulb && $('#spMatchBulb').val() != ""){
        var checkStr = checkPartNumber($('#spMatchBulb').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'매칭전구'의 값이 존재하지 않는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }
    
    if(form.spMConnector && $('#spMConnector').val() != ""){
        var checkStr = checkPartNumber($('#spMConnector').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'매칭커넥터'의 값이 존재하지 않는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }
    
    if(form.spMClip && $('#spMClip').val() != ""){
        var checkStr = checkPartNumber($('#spMClip').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'매칭Clip'의 값이 존재하지 않는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }
    
    if(form.spMRH && $('#spMRH').val() != ""){
        var checkStr = checkPartNumber($('#spMRH').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'매칭R/H'의 값이 존재하지 않는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }
    
    if(form.spMCover && $('#spMCover').val() != ""){
        var checkStr = checkPartNumber($('#spMCover').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'매칭 Cover'의 값이 존재하지 않는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }
    
    if(form.spMWireSeal && $('#spMWireSeal').val() != ""){
        var checkStr = checkPartNumber($('#spMWireSeal').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'매칭 Wire Seal'의 값이 존재하지 않는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }
    
    if(form.spScrabCode && $('#spScrabCode').val() != ""){
        
        if($('#spScrabCode').val().indexOf(',') != -1){
            alert("스크랩코드는 한 개의 부품번호만 선택할 수 있습니다.");
            hideProcessing();
            return;
        }
        
        var checkStr = checkPartNumber($('#spScrabCode').val(), 'S');
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
            alert("'스크랩코드'의 값이 스크랩 부품번호가 아닙니다.\n적합한 스크랩 코드를 입력해 주십시요.");
            hideProcessing();
            return;
        }
        
        // 비철은 S1으로 시작 체크 - 여러 개인지 하나인지 결정 필요
        if(typeof numberingCodeByClaz != "undefined" && numberingCodeByClaz != null  
                && numberingCodeByClaz.length > 2 &&  numberingCodeByClaz.substring(0, 2) == "R1"){
            
            if($('#spScrabCode').val().indexOf('S1') == -1){
                alert("'비철'일 경우 스크랩코드는 'S1'으로 시작해야 합니다.");
                hideProcessing();
                return;
            }
        }
        
        // 수지는 S2으로 시작 체크 - 여러 개인지 하나인지 결정 필요
        if(typeof numberingCodeByClaz != "undefined" && numberingCodeByClaz != null  
                && numberingCodeByClaz.length > 2 &&  numberingCodeByClaz.substring(0, 2) == "R2"){
            
            if($('#spScrabCode').val().indexOf('S2') == -1){
                alert("'수지'일 경우 스크랩코드는 'S2'으로 시작해야 합니다.");
                hideProcessing();
                return;
            }
        }
    }
    
    // 대표 금형 부품 번호 체크로직 추가
    if(form.spRepresentM && $('#spRepresentM').val() != ""){
    	
        if($('#spRepresentM').val().indexOf(',') != -1){
        	alert("대표금형은 한 개의 부품번호만 선택할 수 있습니다.");
            hideProcessing();
            return;
        }
        
        var checkStr = checkPartNumber($('#spRepresentM').val(), 'D');
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        }else if("N" == checkStr){
        	alert("'대표금형'의 값이 금형(SET) 부품번호가 아닙니다.\n적합한 대표금형 번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }
    }
    
    // 부품 번호 체크 로직 추가
    if(form.partNumber && $('#partNumber').val() != ""){
        var checkStr = checkPartNumber($('#partNumber').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
        	alert("'부품번호'는 이미 존재하는 부품번호입니다.\n적합한 부품번호를 입력해 주십시요.");
            hideProcessing();
            return;
        }else if("N" == checkStr){
        }
    }
    
    // ERP 부품 번호 체크 로직 추가
    if(form.partNumber && $('#partNumber').val() != ""){
        var checkStr = checkErpPartNumber($('#partNumber').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
            alert("이미 ERP에 존재하는 반제품 번호가 있습니다.\n반제품을 등록할 수 없습니다.");
            hideProcessing();
            return;
        }else if("N" == checkStr){
        }
    }
    
/*     try{
        if($('#hompage3DIF').val() == 'NO' && document.getElementById("primaryFile").value == ""){
                   alert("제품 이미지를 등록해 주세요!");
                    hideProcessing();
                    return;
        }
    }   catch(e){}  */
   
    
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02463")%><%--저장하시겠습니다--%>')){ 
    	hideProcessing();
        return;
    }
    
    // BOM 생성시에는 ECO NO는 필수 입력
    if ($('#partCopyBom').is(":checked")){
    	if($('#ecoNo').val() == ''){
    		alert("'BOM' Copy의 경우에 ECO NO의 값은 필수 입니다.");
    		hideProcessing();
    		return;
    	}
	}
    
    
    
	showProcessing();
	
	$.ajax({
        type: 'post',
        async: false,
        url: '/plm/ext/part/base/insertCopyPart.do',
        data: $('[name=partForm]').serialize(),
        success: function (data) {
        	if(data != 'Fail'){
        		disabledAllBtn();
        	    alert('<%=messageService.getString("e3ps.message.ket_message", "02460")%><%--저장되었습니다--%>');
        	    location.replace("/plm/jsp/bom/ViewPart.jsp?poid=" + data);
        	}else{
        		alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
        	}
           
        	hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            hideProcessing();
        }
    });
	
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
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
 }
 
 function checkErpPartNumber(partNo){
    var ret = "N";
    $.ajax({
        type: 'get',
        async: false,
        url: '/plm/ext/part/base/existErpPartNumber.do?partNumber='+partNo,
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
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
 }
 
////////////////////////////////////////////////////////////////////////////////////
//iframe - 분류체계관련된 function 시작
////////////////////////////////////////////////////////////////////////////////////

// iframe 로딩후 table에 붙여 넣기
var addTableCount = 0;
var spMMoldAt_check_val = "";
function addToTable(){
	
	if(addTableCount != 0){
		
		$('#mainTable #partSpecPropTr').remove();
		
		var trArrayVal = $('#partPropsIfr').contents().find("#partSpecPropTbody").html()
		if(trArrayVal) {
			//alert(trArrayVal);
			$('#mainTable').append(trArrayVal);
			$("select[ketMultiSelect=N]").each(function(){
				CommonUtil.singleSelect(this.name, 100);
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
	        
	        SuggestUtil.bind('SPSERIES', 'spSeriesName', 'spSeries');
	        // 시리즈 초기화
	        $("#spSeries").change(function(){
	            if($(this).val() == ""){
	                $("#spSeriesName").val("");
	            }else{
	            
	            }
	        });
	        
	        // 생산처 초기화
	        $("#spManufAt").change(function(){
	            $("#spManufacPlace").val("");
	            $("#spManufacPlaceTemp").val("");
	        });
	        $("#spMContractSAt").change(function(){
	             $("#spDieWhere").val("");
	             $("#spDieWhereTemp").val("");
	        });
	        
	        // 금형구분 분류체계에 따라 자동 선택
	        if(spMMoldAt_check_val && spMMoldAt_check_val != null && spMMoldAt_check_val != ""){
	            $("#spMMoldAt").val(spMMoldAt_check_val);
	            $("#spMMoldAt").multiselect('refresh');
	        }
	        
	        // 원재료 Maker 먼저선택 > 원재료 Type > 재질(수지), 재질(비철)
	        $("#spMaterMaker").change(function(){
	            if($(this).val() == ""){
	                
	            }else{
	                var clazOid = $('#partClazOid').val();
	                var spMaterMaker = $(this).val();
	                selectCommonCode("spMaterType", "/plm/ext/part/base/getMaterialTypeList.do?clazOid=" + clazOid + "&spMaterMaker=" + spMaterMaker );
	            }
	        });
	        
	        $("#spMaterType").change(function(){
	            if($(this).val() == ""){
	                
	            }else{
	                var clazOid = $('#partClazOid').val();
	                var spMaterMaker = $('#spMaterMaker').val();
	                var spMaterType = $(this).val();
	                if(document.forms[0].spMaterialInfo){
	                selectCommonCode("spMaterialInfo", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=" + clazOid + "&spMaterMaker=" + spMaterMaker + "&spMaterType=" + spMaterType );
	                }
	                if(document.forms[0].spMaterNotFe){
	                selectCommonCode("spMaterNotFe", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=" + clazOid + "&spMaterMaker=" + spMaterMaker + "&spMaterType=" + spMaterType );
	                }
	            }
	        });
	        
		}
		
		// $$ changed
		// partNumberAutoGen
		// 수정 가능하도록 수정
		//$('input:checkbox[id="partNumberAutoGen"]').attr("checked", true).attr("disabled", true); 
		//$('input:text[id="partNumber"]').attr("readonly", true); 
		$('input:text[id="partNumber"]').val($('input:text[id="partNumber"]').prop("defaultValue"));
		$('input:text[id="partNumber"]').attr("readonly", false); 
		
		hideProcessing();
	
	}
	
	addTableCount++;
}

// changed
// irame 로드후 채번 코드 입력
var numberingCodeByClaz = "${baseDto.numberingCode}";
function setPartNumber(_numberingCodeByClaz){
	//alert(numberingCodeByClaz);
	$('#partNumber').val("");
	numberingCodeByClaz = _numberingCodeByClaz;
	$('#partNumber').val(numberingCodeByClaz);
}

window.setSeries = function(returnValue){
    
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    
    $('[name=spSeries]').val(returnValue[0][1]);//id
    $('[name=spSeriesName]').val(returnValue[0][2]);//name
    
}

// 부품 검색 결과 저장
function setPartNospMTerminal(objArr){ setPartNo(objArr, 'spMTerminal'); }
function setPartNospMatchBulb(objArr){ setPartNo(objArr, 'spMatchBulb'); }
function setPartNospMConnector(objArr){ setPartNo(objArr, 'spMConnector'); }
function setPartNospMClip(objArr){ setPartNo(objArr, 'spMClip'); }
function setPartNospMRH(objArr){ setPartNo(objArr, 'spMRH'); }
function setPartNospMCover(objArr) { setPartNo(objArr, 'spMCover'); }
function setPartNospMWireSeal(objArr){ setPartNo(objArr, 'spMWireSeal'); }
function setPartNospScrabCode(objArr){ setPartOneNo(objArr, 'spScrabCode'); }
function setPartNospRepresentM(objArr){ setPartOneNo(objArr, 'spRepresentM'); }

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

function setPartOneNo(objArr, name){
    var partNo= "";
    for ( var i = 0; i < objArr.length; i++ ) {
        partNo = objArr[i][1];
    }
    $('[name=' + name +']').val(partNo);
}

var autoGenOid = "${baseDto.partNamingOid}"; // partName
function setAutoGenOid(namingOid){
	//if(namingOid && namingOid !="")
	//	alert("namingOid:" + namingOid);
		
	autoGenOid = namingOid;
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

function selectSeriesUse(code){
	var ret = "N";
    
    $.ajax({
        type: 'get',
        async: false,
        url: '/plm/ext/part/base/getNumberCodeDescription.do?code='+code,
        success: function (data) {
            try{
                if(document.forms[0].spStandardConnect){//용도
                    if(data=='Y'){
                        $("#spStandardConnect").val("700"); //자동차(표준커넥터)
                        $("#spStandardConnect").multiselect('refresh');
                        //$("select[name=spStandardConnect]").siblings().attr('disabled', true);
                    }else{
                    	if($("#spStandardConnect").val() == '700'){
                            $("#spStandardConnect").val("");
                        }
                        $("#spStandardConnect").multiselect('refresh');
                        //$("select[name=spStandardConnect]").siblings().attr('disabled', false);
                    }
                }
                
            }catch(e){alert(e.message); ret = "E"; }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
   });
}
    

////////////////////////////////////////////////////////////////////////////////////
//iframe - 분류체계관련된 function 끝
////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
//iframe - 부품명 자동완성 function 시작
////////////////////////////////////////////////////////////////////////////////////

function autoGenerate(){
	
	if(autoGenOid == ""){
		
		if($('#partClazOid').val() == ""){
		   alert('먼저 분류체계를 선택해 주십시요.');
		}else{
		   alert('해당 분류체계는 자동생성을 사용하지 않습니다.');
		}
	
		return;
	}
    
    var url = "/plm/ext/part/base/genNamePopup.do?oid=" + autoGenOid; // partNaming oid
    window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,top=20,left=50,width=1150,height=400,center:yes");
    /*
    var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:400px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    
    $('[name=partName]').val(returnValue);
    */
}

function setPartName(returnValue){
	$('[name=partName]').val(returnValue);
}

////////////////////////////////////////////////////////////////////////////////////
//iframe - 부품명 자동완성 function 끝
////////////////////////////////////////////////////////////////////////////////////

-->
</script>
<form name="partForm">

<input type="hidden" id="partCopyOid" name="partCopyOid" value="${baseDto.partOid}" />
	<div class="contents-wrap" >
		<div class="sub-title b-space20"><img src="/plm/portal/images/icon_3.png" />부품 복사 등록</div>
		<div class="b-space20" style="text-align:right">
		<span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:savePart();" class="btn_blue">저장</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
		</div>
		<div class="b-space30">
			<table id="mainTable" cellpadding="0" class="table-style-01" summary="">
				<colgroup>
					<col width="13%" />
					<col width="20%" />
					<col width="13%" />
					<col width="20%" />
					<col width="13%" />
					<col width="21%" />
				</colgroup>
				<tbody id="partSpecPropTbody2" >
					<tr>
						<td class="title">관련정보 복사</td>
						<td colspan="5" class="bgcol fontb"><input id="partCopyEpm" name="partCopyEpm"  type="checkbox" value="Y" />도면&nbsp;&nbsp;&nbsp;
						<input id="partCopyDoc" name="partCopyDoc"  type="checkbox" value="Y" />문서&nbsp;&nbsp;&nbsp;
						<input id="partCopyBom" name="partCopyBom"  type="checkbox" value="Y" />BOM&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EO NO&nbsp;<input type="text"  id="ecoNo" name="ecoNo" value="" style="width:150px" />
						</td>
					</tr>
					<tr>
						<td class="title">부품번호<span class="red-star">*</span></td>
						<td colspan="5" class="bgcol fontb"><input id="partNumber" name="partNumber" type="text" class="txt_field" style="width: 65%" value="${baseDto.partNumber}" esse="true" esseLabel="부품번호"  >
						&nbsp;&nbsp;&nbsp;<input id="partNumberAutoGen" name="partNumberAutoGen"  type="checkbox" value="Y" />부품번호 자동생성</td>
					</tr>
					<tr>
						<td class="title">부품명<span class="red-star">*</span></td>
						<td colspan="5"><input id="partName" name="partName" type="text" class="txt_field" style="width: 65%" value="${baseDto.partName}" esse="true" esseLabel="부품명" >&nbsp;&nbsp;&nbsp;&nbsp;<span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
						<a href="javascript:autoGenerate();" class="btn_blue">자동생성</a></span><span class="pro-cell b-right"></span></span></span></td>
					</tr>
					<tr>
                        <td class="title">부품분류<span class="red-star">*</span></td>
                        <td><input type="text" id="partClazKrName" name="partClazKrName" class="txt_field" style="width: 70%" value="${baseDto.partClazNameKr}" >
                        <input type="hidden" id="partClazOid" name="partClazOid" value="${baseDto.partClazOid}" esse="true" esseLabel="부품분류체계" >
                        <a href="javascript:SearchUtil.selectOnePartClazPopUp('loadPartProperty','onlyLeaf=Y&openAll=N');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
					    <td class="title">부품유형<span class="red-star">*</span></td>
                        <td><ket:select id="spPartType" name="spPartType" value="${baseDto.spPartType}" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="SPECPARTTYPE" multiple="multiple" otherHtml=" esse='true' esseLabel='부품유형' " /></td>
						<td class="title">개발단계<span class="red-star">*</span></td>
                        <td><select id="spPartDevLevel" name="spPartDevLevel" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
                        <c:choose><c:when test="${baseDto.spPartDevLevel=='PC003'}"><option value="PC003" selected>개발</option></c:when><c:otherwise><option value="PC003">개발</option></c:otherwise></c:choose>
                        <c:choose><c:when test="${baseDto.spPartDevLevel=='PC004'}"><option value="PC004" selected>양산</option></c:when><c:otherwise><option value="PC004">양산</option></c:otherwise></c:choose>
                        </select></td>
					</tr>
					<tr>
                        <td class="title">기본단위<span class="red-star">*</span></td>
                        <td><ket:partUnit selectCode="${baseDto.partDefaultUnit}" htmlAttribute=" id='partDefaultUnit' name='partDefaultUnit' style='width: 170px; display: none;' onblur='fm_jmp' multiple='multiple' esse='true' esseLabel='기본단위' "  /></td>
                        <td class="title">프로젝트 번호<span id="projectNoSpan"  class="red-star" style="display:none;" >*</span></td>
                        <td><input type="text" id="projectNo" name="projectNo" value="" style="width: 70%" esse="false" esseLabel="프로젝트번호" /><%-- 복사시 뺀다. ${baseDto.projectNo} --%>
                        <a href="javascript:SearchUtil.selectOneProject('setProjectNo');">
                        <img src="/plm/portal/images/icon_5.png" /></a>
                        <a href="javascript:CommonUtil.deleteValue('projectNo','projectName');">
                        <img src="/plm/portal/images/icon_delete.gif" /></a></td>
                        <td class="title">프로젝트 명</td>
                        <td><input id="projectName" name="projectName" type="text" class="txt_field" style="width: 98%" value="" readonly></td><%-- 복사시 뺀다. ${baseDto.projectName} --%>
                    </tr>
                    <tr id="hompageTd" style="display:none">
                        <td class="title">홈페이지 등록</td>
                        <td id="homepageIFTd"> 
                            <select name="homepageIF" id="homepageIF" style="width: 170px;">
                                <option value="">선택</option>
                                <option value="YES">YES</option>
                                <option value="NO">NO</option>
                            </select>
                        </td>
                        <td class="title">홈페이지(2D)<br/>등록</td>
                        <td id="homepage2DIFTd"> 
                            <select name="homepage2DIF" id="homepage2DIF" style="width: 170px;">
                                <option value="">선택</option>
                                <option value="YES">YES</option>
                                <option value="NO">NO</option>
                            </select>
                        </td>
                        <td class="title">홈페이지(3D)<br/>등록</td>
                        <td id="hompage3DIFTd">  
                            <select name="hompage3DIF" id="hompage3DIF" style="width: 170px;" onChange="displayAttachImgArea();">
                                <option value="">선택</option>
                                <option value="YES">YES</option>
                                <option value="NO">NO</option>
                            </select>
                        </td>
                    </tr>
                    <tr> 
                        <td class="title">총중량</td>
                        <td><input id="spTotalWeight" name="spTotalWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spTotalWeight}" ></td>
                        <td class="title">부품중량<span id="spNetWeightSpan" class="red-star" style="display:none;" >*</span></td>
                        <td><input id="spNetWeight" name="spNetWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spNetWeight}" esse="false" esseLabel="부품중량" ></td>
                        <td class="title">스크랩중량</td>
                        <td><input id="spScrabWeight" name="spScrabWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spScrabWeight}" ></td>
                    </tr>
        <c:forEach var="result" items="${baseDto.partClassAttrLinkDTOList}" varStatus="status">
            <c:if test="${result.partAttributeDTO.attrCode ne 'spDevSpec' && result.partAttributeDTO.attrCode ne 'hompageImgIF'  && result.partAttributeDTO.attrCode != 'spTotalWeight' && result.partAttributeDTO.attrCode != 'spNetWeight' && result.partAttributeDTO.attrCode != 'spScrabWeight'} " >
            
            <c:if test="${status.index%3==0}" ><tr id="partSpecPropTr"></c:if>
                <td class="title">${fn:replace(result.partAttributeDTO.attrName, '(', '<br/>(')}
                <c:choose>  
                   <c:when test="${result.essential}"><span class="red-star">*</span></c:when>
                   <c:when test="${result.checked}"><span class="blue-star">*</span></c:when>
                   <c:otherwise></c:otherwise>
                </c:choose>
                </td>
                <td>
                <c:choose>  
                  <c:when test="${result.partAttributeDTO.attrInputType=='TEXT'}">
                      <c:choose>  
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMTerminal' || result.partAttributeDTO.attrCode == 'spMConnector' 
                                    ||result.partAttributeDTO.attrCode == 'spMClip'     || result.partAttributeDTO.attrCode == 'spMRH'
                                    || result.partAttributeDTO.attrCode == 'spMCover'
                                    ||result.partAttributeDTO.attrCode == 'spMWireSeal' || result.partAttributeDTO.attrCode == 'spMatchBulb'}">
                              <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="${result.partStandardValue}" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectPart('setPartNo${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spScrabCode'}">
                              <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="${result.partStandardValue}" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNo${result.partAttributeDTO.attrCode}', 'pType=S');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spRepresentM'}"> <%-- 대표금형도 복사시 값을 뺀다. ${result.partStandardValue} --%>
                              <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNo${result.partAttributeDTO.attrCode}', 'pType=D');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spDevManNm' || result.partAttributeDTO.attrCode == 'spManufacManNm' || result.partAttributeDTO.attrCode == 'spDesignManNm'}">
                            <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="${result.partStandardValue}" class="txt_field" style="width: 70%">
                            <input type="hidden" name="${result.partAttributeDTO.attrCode}Oid">
                            <a href="javascript:SearchUtil.selectOneUser('${result.partAttributeDTO.attrCode}Oid','${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}Oid','${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spManufacPlace' || result.partAttributeDTO.attrCode == 'spDieWhere'}">
                            <input type="hidden" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="${result.partStandardValue}" >
                            <!-- 사내 외주 구분 -->
                            <input type="text" id="${result.partAttributeDTO.attrCode}Temp" name="${result.partAttributeDTO.attrCode}Temp" value='<ket:manufPlace code="${result.partStandardValue}" />'" class="txt_field" readonly="readonly" style="width: 70%" >
                            <a href="javascript:setManuFacPlace('${result.partAttributeDTO.attrCode}', 'Temp');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}', '${result.partAttributeDTO.attrCode}Temp');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:otherwise>
                            <input id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="text" class="txt_field" value="${result.partStandardValue}" style="width: 95%" value="" >
                      </c:otherwise>
                      </c:choose>
                  </c:when>
                  <c:when test="${result.partAttributeDTO.attrInputType=='SELECT'}">
                      <c:choose>  
                      <%-- 시리즈 SpSeries numberCodeTypeUse="N" numberCodeType=""  ketMultiSelect="N" multiple="multiple" value="" --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spSeries'}">
                          <input type="hidden" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="${result.partStandardValue}">
                          <input type="text" id="${result.partAttributeDTO.attrCode}Name" name="${result.partAttributeDTO.attrCode}Name" class="txt_field" style="width: 70%"
                           value="<ket:codeValueByCode codeType="${result.partAttributeDTO.attrCodeType}" code="${result.partStandardValue}" />" >
                            <a href="javascript:SearchUtil.selectOneSpSeries('spSeries', 'spSeriesName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('spSeries', 'spSeriesName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <%-- 원재료 Maker --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterMaker'}">
                        <ket:material id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="MAKER" clazOid="${baseDto.partClazOid}" className="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numberCodeType="" ketMultiSelect="N" multiple="multiple" value="${result.partStandardValue}" />
                      </c:when>
                      <%-- 원재료 Type --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterType'}">
                        <ket:material id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="TYPE" clazOid="${baseDto.partClazOid}" partOid="${baseDto.partOid}" className="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numberCodeType="" ketMultiSelect="N" multiple="multiple" value="${result.partStandardValue}" />
                      </c:when>
                      <%-- 재질:수지, 재질:비철 --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterialInfo' || result.partAttributeDTO.attrCode == 'spMaterNotFe'}">
                        <ket:material id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="GRADE" clazOid="${baseDto.partClazOid}" partOid="${baseDto.partOid}" className="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numberCodeType="" ketMultiSelect="N" multiple="multiple" value="${result.partStandardValue}" />
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrMultiSelect}">
                        <ket:select id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="${result.partAttributeDTO.attrCodeType}" value="${result.partStandardValue}" multiple="multiple" otherHtml="ketMultiSelect='Y'" />
                      </c:when>
                      <c:otherwise>
                       <ket:select id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="${result.partAttributeDTO.attrCodeType}" value="${result.partStandardValue}" multiple="multiple" otherHtml="ketMultiSelect='N'" />
                      </c:otherwise>
                      </c:choose>
                  </c:when>
                  
                  <c:otherwise>
                      <input type="text" value="" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_5.png" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_delete.gif" />
                  </c:otherwise>
               </c:choose>
            <c:if test="${status.index%3==2}" ></tr></c:if>
            </c:if>
        </c:forEach>
        
        <c:forEach var="result2" items="${baseDto.partClassAttrLinkDTOList}" varStatus="status">
            <c:if test="${result2.partAttributeDTO.attrCode eq 'spDevSpec'}" >
	        <tr>
                <td class="title">${fn:replace(result2.partAttributeDTO.attrName, '(', '<br/>(')}
                <c:choose>  
                   <c:when test="${result2.essential}"><span class="red-star">*</span></c:when>
                   <c:when test="${result2.checked}"><span class="blue-star">*</span></c:when>
                   <c:otherwise></c:otherwise>
                </c:choose>
                </td>
	            <td colspan="5" >
	                <textarea id="${result2.partAttributeDTO.attrCode}" name="${result2.partAttributeDTO.attrCode}" type="textline" class="txt_field" value="${result2.partStandardValue}" style="width: 95%" value="" >${result2.partStandardValue}</textarea>
	            </td>                 
	        </tr>
            </c:if>
            <c:if test="${result2.partAttributeDTO.attrCode eq 'hompageImgIF'}" >
	        <tr id="partSpecPropTrhompageImgIF">
                <td class="title">${fn:replace(result2.partAttributeDTO.attrName, '(', '<br/>(')}
                <c:choose>  
                   <c:when test="${result2.essential}"><span class="red-star">*</span></c:when>
                   <c:when test="${result2.checked}"><span class="blue-star">*</span></c:when>
                   <c:otherwise></c:otherwise>
                </c:choose>
                </td>
	            <td colspan="5" >
	                <input name="primaryFile" id="primaryFile" type="file"  style="width: 100%" >
	            </td>                 
	        </tr>
            </c:if>
        </c:forEach>    
        
        <c:if test="${fn:length(baseDto.partClassAttrLinkDTOList)%3==0}" ></c:if>
        <c:if test="${fn:length(baseDto.partClassAttrLinkDTOList)%3==1}" >
            </tr>
            <%-- 끝에 colspan 추가 및 사이즈 조절 --%>
            <script lang="javascript">
                $('#partSpecPropTbody2 tr:last').each(function(){
                    $(this).find('td:last').attr('colspan',5);
                    
                    var hasImageTd = false;
                    $(this).find("td:last img").each(function(){
                        hasImageTd = true;
                    });
                    
                    $(this).find("td:last [type='text']").each(function(){
                       //alert($(this).width());
                        if( !hasImageTd ){ // 95%
                            $(this).width("21.4%");
                        }else if( hasImageTd ){ // 70%
                            $(this).width("15.7%");
                        }
                    });
                });
            </script>
        </c:if>
        <c:if test="${fn:length(baseDto.partClassAttrLinkDTOList)%3==2}" >
            </tr>
            <%-- 끝에 colspan 추가 --%>
            <script lang="javascript">
                $('#partSpecPropTbody2 tr:last').each(function(){
                    $(this).find('td:last').attr('colspan',3);
                    
                    var hasImageTd = false;
                    $(this).find("td:last img").each(function(){
                        hasImageTd = true;
                    });
                    
                    $(this).find("td:last [type='text']").each(function(){
                        //alert($(this).width());
                        if( !hasImageTd ){ // 95%
                            $(this).width("34.5%"); 
                        }else if( hasImageTd){ // 70%
                            $(this).width("25.4%"); 
                        }
                    });
                });
            </script>
        </c:if>
				</tbody>
			</table>
            <iframe id="partPropsIfr" name="partPropsIfr" onLoad="javascript:addToTable();" src="" width="0px" height="0px" frameborder="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
		</div>
	</div>
</form>



<script type="text/javascript">

    var data1 = "${baseDto.homepageIF}";
    var data2 = "${baseDto.homepage2DIF}";
    var data3 = "${baseDto.hompage3DIF}";

    if(data1 == '') data1 = "선택";
    if(data2 == '') data2 = "선택";
    if(data3 == '') data3 = "선택";
    
    setTimeout("setHpYn('"+ data1 +"')" , 100);
    setTimeout("setHp2DYn('"+ data2 +"')" , 100);
    setTimeout("setHp3DYn('"+ data3 +"')" , 100);
    


function setHpYn(val){
    $("#homepageIFTd button span").each(function (index) {
        if(index == "1") $(this).text(val)
    });
}
function setHp2DYn(val){
    $("#homepage2DIFTd button span").each(function (index) {
       if(index == "1") $(this).text(val);
    });
}
function setHp3DYn(val){
    $("#hompage3DIFTd button span").each(function (index) {
       if(index == "1") $(this).text(val);
    });
}


</script>
