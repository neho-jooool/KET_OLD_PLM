﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%

//  TODO TKLEE 전장모듈 표준구매품[식별하는 방법]의 경우 제조사, 제조사품번으로 중복 체크
// 동일하게 등록되어 있는 기존 품번 리스트 Popup Open
// 무시하고 등록 또는 취소

// TODO TKLEE 하위 1레벨 반제품의 Thumbnail

%>
<!-- 부품 공통 js -->
<script src="/plm/extcore/js/part/partUtil.js"></script>

<script type="text/javascript">
<!--

var spPartType_options = "";
$(document).ready(function(){

	$('#spTotalWeight').number( true ,3 );
    $('#spNetWeight').number( true ,3 );
    $('#spScrabWeight').number( true ,3 );
    
    CommonUtil.singleSelect('spPartType',100);
    spPartType_options = $('#spPartType').html();
    //alert(spPartType_options);
    
    CommonUtil.singleSelect('spPartDevLevel',100);
    CommonUtil.singleSelect('partDefaultUnit',100);

    
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', null, 'projectName'); // REVIEWPROJNO, PRODUCTPROJNO
    
    //$(document).on("keyup", "#partName", function() {$(this).val( $(this).val().replace(/[^\!-z\s]/gi,"") );});
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Die No에 제품 정보 추가
    // -. 제품No 입력되도록 Field 추가 - ok
    // -. 제품명은 품명 field 사용 - ok
    // -. Die 선택할 때만, 제품No가 보이도록 수정 - ok
    // -. 제품No(partProdNumber), partProdOid, PartName : Halb 자동완성 및 연결 - ok
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // -. Set, Famil를 선택할 때 -> Family disable 및 빈값으로 - ok
    // -. Modify 선택할 때는 검색하는 모양으로 변경 - ok
    // -. 추가 선택할 경우에는 Select 박스로 변경 - ok
    // -. Select box를 미리 만들고 만들어서 제품이 변경되면 제품에 연결된 die정보를 select 박스에 넣어준다. - ok
    // -. 대표금형을 선택할 경우 속성 업데이트 ( Select box를 선택할 경우, Modify의 부품을 찾아 넣을 경우 ) - ok 
    // -. 해당하는 대표금형이 들어가면 해당 대표금형부품의 속성을 가져와서 모두 업데이트한다. - ok 
    // -. 저장할 때 value 들을 모두 저장하기
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // -. Halb에서 반제품 등록으로 넘어온 경우 문제 없게 값 넣고 처리
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 선택시 oid 값이 변경되면 자동으로 select box 변경
    $("#partProdOid").change(function(){
        if($(this).val() == ""){
            $("select[name=spRepresentM_select]").empty();
            CommonUtil.singleSelect("spRepresentM_select", 100);
        }else{
            $("select[name=spRepresentM_select]").empty();
            var partProdOidVal = $("#partProdOid").val();
            selectCommonCode("spRepresentM_select", "/plm/ext/part/base/getDieListOfHalb.do?halbOid=" + partProdOidVal );
        }
    });
    //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 선택시 제품번호가 보이고, 부품명을 제품명으로
    SuggestUtil.bind("PARTNO", "partProdNumber", "partProdOid", "partName");
    
/*     CommonUtil.setNumberField('spTotalWeight'); 
    CommonUtil.setNumberField('spNetWeight'); 
    CommonUtil.setNumberField('spScrabWeight');  */
    
    //document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?oid=ext.ket.part.entity.KETPartClassification:897950025";
    //$('#partPropsIfr').onload=addToTable;
    
    $('#spPartType').change(function(){
    	
        var isNotMold = true;
        $("#spPartType option:selected").each(function () {
	    	displayHp($(this).val());
            if( $(this).val() == 'M' ){
                isNotMold = false;
                return false;
            }
        });
        
        $('#partNumber').attr("readonly", isNotMold);
    });
    
    // 반제품에서 금형(Set)등록을 클릭한 경우에 처리함.
    <c:if test="${not empty partProdOid}" >
        $("select[name=spPartType]").children('option').each(function() {
            if ( $(this).val() != 'D' ){
                   $(this).remove();
            }
        });
        CommonUtil.singleSelect('spPartType', 100);
        
        $('#partProdOfDieTr').show();
        $('#partName').attr("readonly", true);
    </c:if>
    
    $("#projectName").change(function(){
        if($(this).val() == ""){
            
        }else{
            //var devLevelVal = $("#spPartDevLevel").val();
            //if(devLevelVal == 'PC003' || devLevelVal == 'PC004'){ return; }
            var checkStr = checkProjectDevLevel($('[name=projectNo]').val());
            //alert(checkStr);
            if("E" == checkStr){
            }else {
                if(checkStr && ( checkStr=="PC003" || checkStr =="PC004" )){
                    $("#spPartDevLevel").val(checkStr);
                    $("#spPartDevLevel").multiselect('refresh');
                }
            }
        }
    });
    
    <c:choose>  
    <c:when test="${empty partProdOid}" >
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
    </c:when>
    <c:otherwise>
        $('#partClazKrName').attr("readonly", true);
    </c:otherwise>
    </c:choose>    
    
    // 금형부품의 die No가 비어 있지 않을 경우에
    // 자동으로 금형부품 분류체계만 선택되어 지고, 선택하도록 하고, No가 들어가도록 수정
    <c:if test="${not empty moldDieNo}" >
          var clazArray = new Array();

          clazArrayIn = new ClazNode("${moldDieNoClazOid}", "${moldDieNoClazKrName}");
          clazArray[0] = clazArrayIn;
          
          if(clazArrayIn.id != ""){
              //alert(clazArrayIn.id);
              loadPartProperty(clazArray);
          }
          
    </c:if>
 
    homepageImgAreaSetting();
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




function homepageImgAreaSetting(){
	try{
		$("#partSpecPropTrhompageImgIF").css("display","none");
	}catch(e){
	}
}

function displayAttachImgArea(){
	try{
	    if($("#hompage3DIF").val() == "YES"){
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
 
function getPermissionPartErp(partnumber){
	
	if( $('#spMMakingAt').val() == "2" ){//제작구분이 OEM이 아닐때 체크한다 
		isPermission = "Y";
		return;
	}
    
    $.ajax({
        type: 'get',
        async: false,
        cache: false,
        url: '/plm/ext/part/base/getPermissionPartErp.do?partnumber='+partnumber,
        success: function (data) {
            isPermission = data;
        },
        fail : function(){
            isPermission = "N";
        }
    });
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
    var checkStr = checkProjectDevLevel($('[name=projectNo]').val());
    //alert(checkStr);
    if("E" == checkStr){
    }else {
        if(checkStr && ( checkStr=="PC003" || checkStr =="PC004" )){
            $("#spPartDevLevel").val(checkStr);
            $("#spPartDevLevel").multiselect('refresh');
        }
    }
    
}

function loadPartProperty(returnValue){
    //alert('a');
    var valueField = 'partClazOid';
    var displayField = 'partClazKrName';
    $('[name='+valueField+']').val(returnValue[0].id);//oid
    $('[name='+displayField+']').val(returnValue[0].name);//kr name
    
    showProcessing();
    document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?oid=" + returnValue[0].id;
    
    
    homepageImgAreaSetting();
}

var testId = 2;
function savePart(){
	
	var partType = $("#spPartType").val();
	
	var partName = $("#partName").val();
	
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
	
    var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크
    
    if(pattern_kor.test(partName)) {
        alert("부품명에 한글을 포함 할 수 없습니다.");
        return;
    }
	
	if(partName.indexOf("'") > -1 || partName.indexOf('"') > -1){//품명에 홑따옴표, 쌍따옴표 제한 2017.06.28 김명국 차장 요청 by 황정태
		alert("부품명에 홑따옴표 또는 쌍따옴표가 포함되면 안됩니다.");
		$("#partName").focus();
		return;
	}
	
	if(partType == 'F' || partType == 'H' || partType == 'W') { //차주원 차장님 요청 2018.05.04
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
   
    var data1 = "";
    var data2 = "";
    var data3 = "";
    
    
    
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
   
    
    if($('#partProdOfDieTr').is(':visible') && $('#partProdNumber').val() == "" ){
        alert("제품 번호를 입력해 주세요!");
        return;
    }
    
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
        
       /*  if(partWeight.indexOf('.') != -1){
            var partWeightTemp = partWeight.substring(partWeight.indexOf('.')+1);
            if(typeof partWeightTemp != "undefined" && partWeightTemp.length > 3){
                partWeight = partWeight.substring(0, partWeight.indexOf('.')+1) + partWeightTemp.substring(0,3);
                $("#spNetWeight").val(partWeight);
            }
        } */
        var partWeightF = parseFloat(partWeight);
        
        // 총중량
        var total = $("#spTotalWeight").val();
        if(total == ""){ total = "0";}
        
        if(isNaN(total)){
            alert("총중량 '" + total + "'이 숫자포멧이 아닙니다.");
            $("#spTotalWeight").focus();
            return;
        }
        
/*         if(total.indexOf('.') != -1){
            var totalTemp = total.substring(total.indexOf('.')+1);
            if(typeof totalTemp != "undefined" && totalTemp.length > 3){
                total = total.substring(0, total.indexOf('.')+1) + totalTemp.substring(0,3);
                $("#spTotalWeight").val(total);
            }
        } */
        var totalF = parseFloat(total);
        
        // 스크랩중량
        var scrabWeight = $("#spScrabWeight").val();
        if(scrabWeight == ""){ scrabWeight = "0";}
        
        if(isNaN(scrabWeight)){
            alert("스크랩중량 '" + scrabWeight + "'이 숫자포멧이 아닙니다.");
            $("#spScrabWeight").focus();
           return;
        }
        
/*         if(scrabWeight.indexOf('.') != -1){
            var scrabWeightTemp = scrabWeight.substring(scrabWeight.indexOf('.')+1);
            if(typeof scrabWeightTemp != "undefined" && scrabWeightTemp.length > 3){
                scrabWeight = scrabWeight.substring(0, scrabWeight.indexOf('.')+1) + scrabWeightTemp.substring(0,3);
                $("#spScrabWeight").val( scrabWeight );
            }
        } */
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
    
    // 부품의 YAZAKI제번 체크
    if(form.spIsYazaki && form.spYazakiNo && $('#spIsYazaki').val() == "YES"){
        if("" == $('#spYazakiNo').val()){
           alert("'YAZAKI여부'가 'YES'이면 'YAZAKI제번'에 값을 입력해야 합니다.");
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
            
            // 금형 부품번호 존재하는지 체크 ( PLM, ERP )
            if(form.partNumber && $('#partNumber').val() != ""){
                var checkStr = checkPartNumber($('#partNumber').val(), 'M');
                if("E" == checkStr){
                    checkMoldLength = false;
                    hideProcessing();
                    return;
                }else if("Y" == checkStr){
                    alert("이미 존재하는 금형부품 번호가 있습니다.\n금형부품을 등록할 수 없습니다.");
                    checkMoldLength = false;
                    hideProcessing();
                    return;
                }else if("N" == checkStr){
                }
            }
            
            if(form.partNumber && $('#partNumber').val() != ""){
                var checkStr = checkErpPartNumber($('#partNumber').val());
                if("E" == checkStr){
                    checkMoldLength = false;
                    hideProcessing();
                    return;
                }else if("Y" == checkStr){
                    alert("이미 ERP에 존재하는 부품 번호가 있습니다.\n부품을 등록할 수 없습니다.");
                    checkMoldLength = false;
                    hideProcessing();
                    return;
                }else if("N" == checkStr){
                }
            }
        }
    });
    if(!checkMoldLength) return;
    //*/
    
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
    
    // Die No의 생산구분이 '추가' 이거나 'Modify'일 경우는 대표금형값을 반드시 입력
    var checkDiespRepresentM = true;
    $("#spPartType option:selected").each(function () {
        if( $(this).val() == 'D' ){
            //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 선택시 oid 값이 변경되면 자동으로 select box 변경
            if( $('#spMProdAt').val() == "2" ){ // 추가
                if( $('#spRepresentM_select').val() == "" ){
                    alert("Die No의 생산구분이 '추가' 이거나 'Modify'일 경우는 대표금형값을 반드시 입력해야 합니다.");
                    checkDiespRepresentM = false;
                }else{
                    if($('#spRepresentM_select option:selected').text().length != 8){
                          alert("대표금형은 8자 이어야 합니다.");
                          checkDiespRepresentM = false;
                    }
                }
            }else if( $('#spMProdAt').val() == "3" ){ // Modify
                if( $('#spRepresentM').val() == "" ){
                    alert("Die No의 생산구분이 '추가' 이거나 'Modify'일 경우는 대표금형값을 반드시 입력해야 합니다.");
                    checkDiespRepresentM = false;
                }else if($('#spRepresentM').val().length != 8){
                    alert("대표금형은 8자 이어야 합니다.");
                    checkDiespRepresentM = false;
                }
            }
        
            if(!checkDiespRepresentM){
                return false;
            }else{
                if( $('#partProdOid').val() == "" ){
                    alert("'제품 번호'를 입력해 주세요.");
                    checkDiespRepresentM = false;
                    return false;
                }
            }
            showProcessing();
            getPermissionPartErp($('#partProdNumber').val());
            if(isPermission != "Y"){
                alert("부품 ["+ $('#partProdNumber').val() +"]은 대표금형에 연결할 수 없는 부품유형입니다.");
                hideProcessing();
                checkDiespRepresentM = false;
            }
        }
    });
    if(!checkDiespRepresentM) return;
    
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
        
        //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 선택시 oid 값이 변경되면 자동으로 select box 변경
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
    /*
    try{
        
        if($('#homepageIF').val() == 'YES' && $('#hompage3DIF').val() == 'NO' && document.getElementById("primaryFile").value == ""){
                   alert("제품 이미지를 등록해 주세요!");
                    hideProcessing();
                    return;
        }
    }   catch(e){} 
    */
    
      
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02463")%><%--저장하시겠습니다--%>')){ 
        hideProcessing();
        return;
    }
    
    // Die No의 생산구분이 '추가'일 경우는 대표금형값을 변경하여 저장한다.
    $("#spPartType option:selected").each(function () {
        if( $(this).val() == 'D' ){
            //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 선택시 oid 값이 변경되면 자동으로 select box 변경
            if( $('#spMProdAt').val() == "2" ){ // 추가
                $('#spRepresentM').val($('#spRepresentM_select option:selected').text());
            }
        }
    });
    
    
    
    

    $('[name=partForm]').attr('enctype', 'multipart/form-data');
    $('[name=partForm]').attr('action', '/plm/ext/part/base/insertPart.do');
    showProcessing();
    disabledAllBtn();
    $('[name=partForm]').submit();
 
 
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
var spMMoldAt_check_val = "";
function addToTable(){
    
    $('#mainTable #partSpecPropTr').remove();
    $('#mainTable #partSpecPropTrspDevSpec').remove();
    $('#mainTable #partSpecPropTrhompageImgIF').remove();
    
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
        
        //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에
        // -. Set, Famil를 선택할 때 -> Family disable 및 빈값으로
        // -. Modify 선택할 때는 검색하는 모양으로 변경
        // -. 추가 선택할 경우에는 Select 박스로 변경 및 제품 Oid가 있으면 Option 생성해 줌
        if(document.forms[0] && typeof document.forms[0].spRepresentM_select != "undefined"){
            
            CommonUtil.singleSelect("spRepresentM_select", 100);
            $("#spRepresentM_select").siblings().hide();
            $("#spRepresentM").show();
            $("#spRepresentM").attr("readonly", true);
            $("#spRepresentM").val("");
            $("#spRepresentM_search_img").hide();
            $("#spRepresentM_search_img_a").hide();
            $("#spRepresentM_del_img").hide();
            $("#spRepresentM_del_img_a").hide();
            
            $("#spMProdAt").change(function(){
                if($(this).val() == ""){
                    $("#spRepresentM_select").siblings().hide();
                    $("#spRepresentM").attr("readonly", false);
                    $("#spRepresentM").show();
                    $("#spRepresentM_search_img").show();
                    $("#spRepresentM_search_img_a").show();
                    $("#spRepresentM_del_img").show();
                    $("#spRepresentM_del_img_a").show();
                    
                }else if($(this).val() == "1"){ // SET
                    $("#spRepresentM_select").siblings().hide();
                    $("#spRepresentM").show();
                    $("#spRepresentM").attr("readonly", true);
                    $("#spRepresentM").val("");
                    $("#spRepresentM_search_img").hide();
                    $("#spRepresentM_search_img_a").hide();
                    $("#spRepresentM_del_img").hide();
                    $("#spRepresentM_del_img_a").hide();
                    
                }else if($(this).val() == "2"){ // 추가
                    if($("#partProdOid").val() == ""){
                        $("select[name=spRepresentM_select]").empty();
                        CommonUtil.singleSelect("spRepresentM_select", 170);
                    }else{
                        $("select[name=spRepresentM_select]").empty();
                        var partProdOidVal = $("#partProdOid").val();
                        selectCommonCode("spRepresentM_select", "/plm/ext/part/base/getDieListOfHalb.do?halbOid=" + partProdOidVal );
                    }
                    $("#spRepresentM_select").siblings().show();
                    $("#spRepresentM").hide();
                    $("#spRepresentM").attr("readonly", true);
                    $("#spRepresentM_search_img").hide();
                    $("#spRepresentM_search_img_a").hide();
                    $("#spRepresentM_del_img").hide();
                    $("#spRepresentM_del_img_a").hide();
                    
                }else if($(this).val() == "3"){ // Modify
                    $("#spRepresentM_select").siblings().hide();
                    $("#spRepresentM").show();
                    $("#spRepresentM").attr("readonly", false);
                    $("#spRepresentM_search_img").show();
                    $("#spRepresentM_search_img_a").show();
                    $("#spRepresentM_del_img").show();
                    $("#spRepresentM_del_img_a").show();
                }
            });
            
            //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 대표금형 선택시[추가] 사양 속성들 업데이트
            $("#spRepresentM_select").change(function(){
                if($(this).val() == ""){
                    
                }else{
                    selectDieProps("/plm/ext/part/base/getDieDetailOfHalb.do?dieOid=" + $(this).val());
                }
            });
            
            //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 대표금형 선택시[Modify] 사양 속성들 업데이트
            $("#spRepresentMOid").change(function(){
                if($(this).val() == ""){
                    
                }else{
                    selectDieProps("/plm/ext/part/base/getDieDetailOfHalb.do?dieOid=" + $(this).val());
                }
            });
            
            //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 대표금형 자동완성 처리
            SuggestUtil.bind('PARTNO', 'spRepresentM', 'spRepresentMOid');
            
        }else{
            
            //Die - 제품 - 대표금형 자동 선택 처리 : 일반일 경우에 대표금형 자동완성 처리
            SuggestUtil.bind('PARTNO', 'spRepresentM');
        }
        var form = document.forms[0];
        
        if(clazDivision == 'E'){//전자사업부는 친환경은 NO, 용도는 전자로 고정. 지금은 속성으로 관리안하기 때문에 의미는 없다 2016.07.21 황정태
        	
        	if(form.spEnvFriend){//친환경
        		$("#spEnvFriend").val("No");
                $("#spEnvFriend").multiselect('refresh');
                $("select[name=spEnvFriend]").siblings().attr('disabled', 'disabled'); 
        	}
        	
        	if(form.spStandardConnect){//용도
        		$("#spStandardConnect").val("720");
                $("#spStandardConnect").multiselect('refresh');
                $("select[name=spStandardConnect]").siblings().attr('disabled', 'disabled'); 
        	}
        }
        
/*         if(form.spWaterProof && form.spMWireSeal){
        	$("#spWaterProof").change(function(){
                if($(this).val() == "SEALED020" || $(this).val() == "SEALED030"){
                	$("#spMWireSeal").val("N/A");
                }
            });
        } */
        
    }
    
    hideProcessing();
}
var clazDivision = "";
//Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 삭제시 Select 대표금형 trigger로 삭제
function deleteHalbInfo( partProdNumber, partProdOid, partName ){
    $("#" + partProdNumber).val("");
    $("#" + partProdOid).val("").trigger("change");
    $("#" + partName).val("");
}

//Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 대표금형 선택시[추가, Modify] 사양 속성들 업데이트
function selectDieProps(targetUrl){
    
    $.ajax({        
        url: targetUrl,     
        async: false,
        dataType: "json",       
        success: function(data) {
            try{
                var props, index, prop;
                props = data.props;
                for (index = 0; index < props.length; ++index) {
                    prop = props[index];
                    if(prop.type == "SELECT"){
                        if(prop.value == 'spMProdAt'){ // 현재 선택중
                            continue;
                        }else if(prop.value == 'spMMoldAt'){ // 분류체계 지정
                            continue;
                        }else{
                            if(!prop.text || prop.text == ""){
                                eval("document.forms[0]." + prop.value ).selectedIndex = -1;
                                $("#" + prop.value).multiselect('refresh');
                            }else{
                                $("#" + prop.value).val(prop.text);
                                $("#" + prop.value).multiselect('refresh');
                            }
                        }
                    }else if(prop.type == "TEXT"){
                        if(prop.value == 'spRepresentM'){ // 현재 선택중
                            continue;
                        }else{ 
                            // spDieWhere, spDieWhereTemp 서버에서 처리함
                            if(!prop.text || prop.text == ""){
                                $("#" + prop.value).val("");
                            }else{
                                $("#" + prop.value).val(prop.text);
                            }
                        }
                    }else{
                        //alert("else2: " + prop.value + " : " + prop.text + " : " + prop.type);
                    }
                }
            }catch(e){
                alert(e.message);
            }
       }
    });
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

// iframe 로드후 채번 코드 입력
var numberingCodeByClaz = "";
function setPartNumber(_numberingCodeByClaz){
    //alert(_numberingCodeByClaz);
    $('#partNumber').val("");
    numberingCodeByClaz = _numberingCodeByClaz;
    $('#partNumber').val(_numberingCodeByClaz);
    
    <c:if test="${not empty moldDieNo}" >
     if($('#partNumber').val() == ""){
       $('#partNumber').val("${moldDieNo}-");
     }
    </c:if>
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
//Die 제품 선택 처리
function setPartNospRepresentMNOid(objArr){ setPartOneNoNOid(objArr, 'spRepresentM', 'spRepresentMOid'); }

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

//Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 선택시 CallBack 함수
function setPartNoProdOfDie(objArr){ setPartOneNoNOid(objArr, 'partProdNumber', 'partProdOid', 'partName'); }
//Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품 선택시 CallBack 함수 처리
function setPartOneNoNOid(objArr, ptnumber, ptoid, ptname){
    var partNo= "";
    var partOid= "";
    var partName= "";
    for ( var i = 0; i < objArr.length; i++ ) {
        partNo = objArr[i][1];
        partOid = objArr[i][8];
        partName = objArr[i][2];
        isPermission = objArr[i][16];
    }
    if(isPermission != "Y"){
        alert("부품 ["+ partNo +"]은 대표금형에 연결할 수 없는 부품유형입니다.");
        return;
    }
    $('[name=' + ptnumber +']').val(partNo);
    $('[name=' + ptoid +']').val(partOid).trigger('change');
    if(ptname && ptname != null){
        $('[name=' + ptname +']').val(partName);
        //alert(partName);
    }
}
var isPermission = "";
var autoGenOid = ""; // partName
function setAutoGenOid(namingOid){
    //if(namingOid && namingOid !="")
    //  alert("namingOid:" + namingOid);
        
    autoGenOid = namingOid;
}

// 생산처, 금형제작처 가져오기

// 일반제품의 경우 : 생산처구분 [spManufAt:NumberCode => SPECMSELFCONTRACTFLAG] 이 사내인가 외주인가에 따라서
// 생산처[spManufacPlace]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 
// 금형SET의 경우 : 사급구분 [spMContractSAt:NumberCode => SPECMSELFCONTRACTFLAG]이 사내인가 외주인가
// 금형제작처[spDieWhere]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 
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

// 사내 생산처 등록
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

// 외주 협력사 선택
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
//iframe - 부품명 자동완성 function 시작
////////////////////////////////////////////////////////////////////////////////////
var popUp = null;
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
    popUp = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,top=20,left=50,width=1150,height=400,center:yes");
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

function setNumberCodeValue(numberCode, selVal){
    $("[numberCodeType=" + numberCode + "]").each(function(){
        //alert(numberCode);
        $(this).val(selVal);
        //alert(selVal);
        $(this).multiselect('refresh');
    });
}

////////////////////////////////////////////////////////////////////////////////////
//iframe - 부품명 자동완성 function 끝
////////////////////////////////////////////////////////////////////////////////////
-->
</script>
<div class="popup-background" style="padding-top:20px">
<form name="partForm"  method="post" enctype="multipart/form-data">
    <div class="contents-wrap" >
        <div class="popup-title"><img src="/plm/portal/images/icon_3.png" /><c:if test="${empty partProdOid}">부품 등록</c:if><c:if test="${not empty partProdOid}">금형(SET) 등록</c:if></div>
        <div class="b-space5" style="text-align:right">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:savePart();" class="btn_blue">저장</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
        <div class="b-space20">
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
                        <td class="title">부품번호</td>
                        <td colspan="5" class="bgcol fontb"><input id="partNumber" name="partNumber" type="text" class="txt_field" style="width: 95%" value="" readonly="readonly" ></td>
                    </tr>
                    <tr>
                        <td class="title">부품명<span class="red-star">*</span></td>
                        <td colspan="5" class="txt_field"><input type="text" style="ime-mode:disabled;" id="partName" name="partName" class="txt_field" style="width: 65%" value="${partName}" esse="true" esseLabel="부품명" >&nbsp;&nbsp;&nbsp;&nbsp;<span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
                        <a href="javascript:autoGenerate();" class="btn_blue">자동생성</a></span><span class="pro-cell b-right"></span></span></span></td>
                    </tr>
                    <input type="hidden" id="partProdOid" name="partProdOid" value="${partProdOid}" >
                    <tr id="partProdOfDieTr" style="display: none;"> 
                        <td class="title">제품번호<span class="red-star">*</span></td>
                        <td colspan="5"><!-- prod number -->
                        <input type="text" id="partProdNumber" name="partProdNumber" class="txt_field" style="width: 65%" value="${partProdNumber}" >
                        <a href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNoProdOfDie', 'pType=HR');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:deleteHalbInfo('partProdNumber', 'partProdOid', 'partName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        <!-- prod number -->
                        </td>
                    </tr>
                    <tr>
                        <td class="title">부품분류<span class="red-star">*</span></td>
                        <td><input type="text" id="partClazKrName" name="partClazKrName" class="txt_field" style="width: 70%" >
                        <input type="hidden" id="partClazOid" name="partClazOid" value="" esse="true" esseLabel="부품분류체계" >
                        <a href="javascript:SearchUtil.selectOnePartClazPopUp('loadPartProperty','onlyLeaf=Y&openAll=N<c:if test="${not empty partProdOid}" >&classCode=S</c:if><c:if test="${not empty moldDieNo}" >&classCode=T</c:if>');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="title">부품유형<span class="red-star">*</span></td>
                        <td><ket:select id="spPartType" name="spPartType" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="SPECPARTTYPE" multiple="multiple" otherHtml=" esse='true' esseLabel='부품유형' " /></td>
                        <td class="title">개발단계<span class="red-star">*</span></td>
                        <td><select id="spPartDevLevel" name="spPartDevLevel" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
                        <option value="PC001" <c:if test="${spPartDevLevel=='PC001'}">selected</c:if> >Proto</option>
                        <option value="PC003" <c:if test="${spPartDevLevel=='PC003'}">selected</c:if> >개발</option>
                        <option value="PC004" <c:if test="${spPartDevLevel=='PC004'}">selected</c:if> >양산</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td class="title">기본단위<span class="red-star">*</span></td>
                        <td><ket:partUnit selectCode="" htmlAttribute=" id='partDefaultUnit' name='partDefaultUnit' style='width: 170px; display: none;' onblur='fm_jmp' multiple='multiple' esse='true' esseLabel='기본단위' "  /></td>
                        <td class="title">프로젝트 번호<span id="projectNoSpan"  class="red-star" style="display:none;" >*</span></td>
                        <td><input type="text" id="projectNo" name="projectNo" value="${projectNo}" style="width: 70%" esse="false" esseLabel="프로젝트번호" />
                        <a href="javascript:SearchUtil.selectOneProject('setProjectNo');">
                        <img src="/plm/portal/images/icon_5.png" /></a>
                        <a href="javascript:CommonUtil.deleteValue('projectNo','projectName');">
                        <img src="/plm/portal/images/icon_delete.gif" /></a></td>
                        <td class="title">프로젝트 명</td>
                        <td><input id="projectName" name="projectName" type="text" class="txt_field" style="width: 95%" value="${projectName}" readonly></td>
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
                            <select name="hompage3DIF" id="hompage3DIF" style="width: 170px;" onChange="displayAttachImgArea()">
                                <option value="">선택</option>
                                <option value="YES">YES</option>
                                <option value="NO">NO</option>
                            </select>
                        </td>
                    </tr>
                    <tr> 
                        <td class="title">총중량</td>
                        <td><input id="spTotalWeight" name="spTotalWeight" type="text" class="txt_field" style="width: 95%;" value="" ></td>
                        <td class="title">부품중량<span id="spNetWeightSpan" class="red-star" style="display:none;" >*</span></td>
                        <td><input id="spNetWeight" name="spNetWeight" type="text" class="txt_field" style="width: 95%;" value="" esse="false" esseLabel="부품중량" ></td>
                        <td class="title">스크랩중량</td>
                        <td><input id="spScrabWeight" name="spScrabWeight" type="text" class="txt_field" style="width: 95%" value="" ></td>
                    </tr>
                </tbody>
            </table>
            <!-- <iframe id="partPropsIfr" name="partPropsIfr" onLoad="javascript:addToTable();" src="" frameborder="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe> -->
            <iframe id="partPropsIfr" name="partPropsIfr" onLoad="javascript:addToTable();" src=""  width="0px" height="0px"  frameborder="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
        </div>
    </div>
</form>
</div>