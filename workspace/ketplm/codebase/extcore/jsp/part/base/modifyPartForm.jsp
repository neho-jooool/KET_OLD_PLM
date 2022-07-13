﻿<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.part.WTPart"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="wt.content.ContentRoleType"%>
<%@page import="wt.fc.QueryResult"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%

String poid = request.getParameter("partOid");
boolean isAdmin = CommonUtil.isAdmin();
%>


<c:set var="bAdmin" value="<%=isAdmin %>"/>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

/* 재정의 Iframe에 들어가므로 */
.body-space {
    margin-left: 0px !important;
    margin-top: 0px !important;
    margin-right: 0px !important;
    min-width: 0px;   
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

/* 재정의 frame height 재정의함 */
.tabIframeWrapper {width:100%;height:600px;border:0px; margin:0px;position:relative;top:0px;}

</style>


<script type="text/javascript">
<!--

$(document).ready(function(){
	
	$('#spTotalWeight').number( true ,3 );
	$('#spNetWeight').number( true ,3 );
	$('#spScrabWeight').number( true ,3 );

    CommonUtil.tabs('tabs');
    
    //CommonUtil.singleSelect('spPartType',100);
    //CommonUtil.singleSelect('spPartDevLevel',100);
    
    //현재는 ea,kg,mm 외에는 기본단위를 선택할수없게 해놓음. 그런데 과거에 L등으로 입력된 PART의 경우 selectBox 선택이 안되기 때문에 필수값 체크에 걸리게 된다
    //그래서 ea,kg,mm 이 아닌 경우는 selectBox에 해당값을 append 하도록 함
    
    var isDefalutUnit = '${baseDto.partDefaultUnit}' == 'KET_EA' || '${baseDto.partDefaultUnit}' == 'KET_KG' || '${baseDto.partDefaultUnit}' == 'KET_M';
    
    CommonUtil.singleSelect('partDefaultUnit',100);
    
    if(!isDefalutUnit){
    	$("#partDefaultUnit").append("<option value='"+'${baseDto.partDefaultUnit}'+"'>"+ '${baseDto.partDefaultUnit}'.replace("KET_", "") +"</option>");
        $("#partDefaultUnit").val('${baseDto.partDefaultUnit}');
        $("#partDefaultUnit").multiselect('refresh');	
    }
    
    $(document).on("keyup", "#partName", function() {$(this).val( $(this).val().replace(/[^\!-z\s]/gi,"") );});
    
    
    // Approved된 후에는 단위 수정 불가
    <c:choose>  
    <c:when test="${baseDto.partRevision=='0' && fn:endsWith( baseDto.partStateCode, 'INWORK')}">
    </c:when>
    <c:otherwise>
    //$("select[name=partDefaultUnit]").siblings().attr('disabled', 'disabled');
    $("#partDefaultUnit").multiselect("disable");
    </c:otherwise>
    </c:choose>
    
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
                  <c:choose>
                  <c:when test="${result.partAttributeDTO.attrCode != 'spPartDevLevel'}">
                  CommonUtil.singleSelect('${result.partAttributeDTO.attrCode}',tdWidth);
                  </c:when>
                  </c:choose>
              </c:otherwise>
              </c:choose>
        </c:otherwise>
        </c:choose>
    </c:if>
    </c:forEach>

    if( $("#spPartType").val() == 'D' || $("#spPartType").val() == 'M' ){
        SuggestUtil.bind('MOLDPROJNO', 'projectNo', null, 'projectName'); // REVIEWPROJNO, PRODUCTPROJNO, MOLDPROJNO
    }else{
        SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', null, 'projectName'); // REVIEWPROJNO, PRODUCTPROJNO, MOLDPROJNO
    }
    
    // Die No 채번 코드 변경 못하도록
    if( $("#spPartType").val() == 'D' ){
        if( $('#spMProdAt').val() == "1"  || $('#spMProdAt').val() == "2" || $('#spMProdAt').val() == "3" ){
            $('#spRepresentM').prop('readonly', true);
            //$("select[name=spMProdAt]").siblings().attr('disabled', 'disabled');
            $("#spMProdAt").multiselect("disable");
        }
    }
    
    <c:forEach var="result" items="${baseDto.partClassAttrLinkDTOList}" varStatus="status">
	    <c:if test="${result.essential}">
	        $('#${result.partAttributeDTO.attrCode}').attr("esse","true").attr("esseLabel","<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>");
	    </c:if>
	    <c:if test="${result.partAttributeDTO.attrCode == 'hompageImgIF' && result.essential}">
	        $('#primaryFile').attr("esse","true").attr("esseLabel","<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>");
	        $('#primaryFileOid').attr("esse","true").attr("esseLabel","<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>");
	    </c:if>
    </c:forEach>
    
    // Color 도금 - 채번 사용된 것 : 수정 금지
    if( $("#spPartType").val() == 'F' || $("#spPartType").val() == 'H' ){
        <c:if test="${ fn:startsWith(baseDto.partNumber, 'H6') || fn:startsWith(baseDto.partNumber, '6') }" >
           <c:if test="${not empty baseDto.spColor}" >
            //$("select[name=spColor]").siblings().attr('disabled', 'disabled');
            $("#spColor").multiselect("disable");
           </c:if> 
        </c:if>
        <c:if test="${ fn:startsWith(baseDto.partNumber, 'H7') || fn:startsWith(baseDto.partNumber, '7') }" >
           <c:if test="${not empty baseDto.spPlating}" >
            //$("select[name=spPlating]").siblings().attr('disabled', 'disabled');
            $("#spPlating").multiselect("disable");
           </c:if>
        </c:if>
    }
    
    $("select[name=ratedVoltage]").siblings().attr('disabled', 'disabled');
    $("select[name=ratedCurrent]").siblings().attr('disabled', 'disabled');
    $("select[name=mountingType]").siblings().attr('disabled', 'disabled');

    var numberingCharics = "${baseDto.numberingCharics}";
    if(numberingCharics == 'CS'){
    	$('#spNoOfPole').prop('readonly', true);
    }
    //document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?oid=ext.ket.part.entity.KETPartClassification:897950025";
    //$('#partPropsIfr').onload=addToTable;
    
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
/*     CommonUtil.setNumberField('spTotalWeight'); 
    CommonUtil.setNumberField('spNetWeight'); 
    CommonUtil.setNumberField('spScrabWeight');  */
    
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
     
     try{
    	 displayAttachImgArea();
     }catch(e){}
  
     <c:if test = "${bAdmin eq 'true'}">
	     if( $("#spPartType").val() == 'F' || $("#spPartType").val() == 'H' ){
	         $("#hompageTd").css("display","table-row");
	         CommonUtil.singleSelect('homepageIF',100);
	         CommonUtil.singleSelect('homepage2DIF',100);
	         CommonUtil.singleSelect('hompage3DIF',100);         
	     }
     </c:if>
     
}); // end ready


function displayAttachImgArea(){
    try{
        if($("#hompage3DIF").val() == "YES"){
            $("#partSpecPropTrHPImg").css("display","none");
        }else{
            $("#partSpecPropTrHPImg").css("display","table-row");
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

/*
function loadPartProperty(returnValue){
    var valueField = 'partClazOid';
    var displayField = 'partClazKrName';
    $('[name='+valueField+']').val(returnValue[0].id);//oid
    $('[name='+displayField+']').val(returnValue[0].name);//kr name
    
    showProcessing();
    document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?oid=" + returnValue[0].id;
    
}
*/

var testId = 1;
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
    
    
	<c:if test = "${bAdmin eq 'true'}">
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
	    	alert("홈페이지 등록은 필수입니다.\n관리자에게 문의하십시요.");
	    	return;
	    }

	    if($("#homepage2DIF").val() == null || $("#homepage2DIF").val() == ''){
	    	alert("홈페이지(2D) 등록은 필수입니다.\n관리자에게 문의하십시요.");
	    	return;
	    }
	    
	    if($("#hompage3DIF").val() == null || $("#hompage3DIF").val() == ''){
	    	alert("홈페이지(3D) 등록은 필수입니다.\n관리자에게 문의하십시요.");
	    	return;
	    }
	    
	    
	}
	</c:if>
    
    var form = document.forms[0];
  //저장시에는 사용자의 판단에 따르도록 해야할 것 같아서 아래 강제 지정 로직은 주석처리함
/*     if(form.spStandardConnect && form.spSeries){//선택한 시리즈에 따라 용도를 세팅한다
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
        var partWeight = $("#spNetWeight").val().replace(/\s/gi, '');
        
        //if(partWeight == ""){ partWeight = "0";}

        if(isNaN(partWeight) || partWeight == ""){
            alert("부품중량 '" + partWeight + "'이 숫자포멧이 아닙니다.");
            $("#spNetWeight").focus();
            return;
        }
        
/*         if(partWeight.indexOf('.') != -1){
            var partWeightTemp = partWeight.substring(partWeight.indexOf('.')+1);
            if(typeof partWeightTemp != "undefined" && partWeightTemp.length > 3){
                partWeight = partWeight.substring(0, partWeight.indexOf('.')+1) + partWeightTemp.substring(0,3);
                $("#spNetWeight").val(partWeight);
            }
        } */
        var partWeightF = parseFloat(partWeight);
        
        // 총중량
        var total = $("#spTotalWeight").val().replace(/\s/gi, '');
        //if(total == ""){ total = "0";}
        
        if(isNaN(total) || total == ""){
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
        var scrabWeight = $("#spScrabWeight").val().replace(/\s/gi, '');
        //if(scrabWeight == ""){ scrabWeight = "0";}
        
        if(isNaN(scrabWeight) || scrabWeight == ""){
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
        
    // Die No의 생산구분이 '추가' 이거나 'Modify'일 경우는 대표금형값을 반드시 입력
    // Disable 시켜서 필요 없는 로직
    <%--
    if( $("#spPartType").val() == 'D' ){
         if( $('#spMProdAt').val() == "2" || $('#spMProdAt').val() == "3" ){
             if( $('#spRepresentM').val() == "" ){
                 alert("Die No의 생산구분이 '추가' 이거나 'Modify'일 경우는 대표금형값을 반드시 입력해야 합니다.");
                 return;
             }else if($('#partNumber').val().length != 8){
                 alert("대표금형은 8자 이어야 합니다.");
                 return;
             }
         }
    }
    --%>
    
    // 금형부품은 EA만 선택
    var checkMoldLength = true;
    $("#spPartType option:selected").each(function () {
        if( $(this).val() == 'M' ){

            if("KET_EA" != $("#partDefaultUnit option:selected").val()){
                alert("금형부품은 부품 기본단위로 'EA'를 선택해야 합니다.");   
                checkMoldLength = false;
            }
        }
    });
    if(!checkMoldLength) return;
    
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
    /*
    try{
    	
	    if($('#hompage3DIF').val() == 'NO' && document.getElementById("primaryFile").value == ""){
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
    
    //select 태그 disabled 속성을 제거한다. submit시 값이 전달되지 않는 현상 방지
    selectAttributeRemove();

    $('[name=partForm]').attr('action', '/plm/ext/part/base/updatePart.do');
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
 
////////////////////////////////////////////////////////////////////////////////////
//iframe - 분류체계관련된 function 시작
////////////////////////////////////////////////////////////////////////////////////

// iframe 로딩후 table에 붙여 넣기
<%--
var spMMoldAt_check_val = "";
function addToTable(){
    
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
        
        CommonUtil.setNumberField('spNoOfPole');
        
        // 금형구분 분류체계에 따라 자동 선택
        if(spMMoldAt_check_val && spMMoldAt_check_val != null && spMMoldAt_check_val != ""){
            $("#spMMoldAt").val(spMMoldAt_check_val);
            $("#spMMoldAt").multiselect('refresh');
        }
    }
    
    hideProcessing();
}

// irame 로드후 채번 코드 입력
function setPartNumber(numberingCodeByClaz){
    //alert(numberingCodeByClaz);
    $('#partNumber').val("");
    $('#partNumber').val(numberingCodeByClaz);
}

var autoGenOid = ""; // partName
function setAutoGenOid(namingOid){
    //if(namingOid && namingOid !="")
    //  alert("namingOid:" + namingOid);
        
    autoGenOid = namingOid;
}
--%>

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
        tmpNo = $.merge($("[name="+targetId+"]").val().split(", "), partNoArr);
        tmpNo = $.unique(tmpNo.sort());
    }
    else {
        tmpNo = partNoArr.sort();
    }

    $("[name="+targetId+"]").val(tmpNo.join(", "));
}

function setPartOneNo(objArr, name){
    var partNo= "";
    for ( var i = 0; i < objArr.length; i++ ) {
        partNo = objArr[i][1];
    }
    $('[name=' + name +']').val(partNo);
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


//첨부파일 관련 스크립트 시작
function insertFileLine() {
	
	var esseCK = document.getElementById("primaryFileOid").getAttribute('esse');
	
	document.getElementById("primaryFileOid").value = null;
    //alert(document.getElementById("primaryFileOid").value);
    var tr = document.getElementById("partSpecPropTrHPImg");
    var td = document.getElementById("partSpecPropTdHPImg");
    tr.removeChild(td);

    var newTd = document.createElement("td");
    
    if((esseCK == "true")){
    	newTd.innerHTML =  "<input name='primaryFile' id='primaryFile' type='file' esse='true' class='txt_fieldRO' style='width: 100%' >";
    }else{
    	newTd.innerHTML =  "<input name='primaryFile' id='primaryFile' type='file' class='txt_fieldRO' style='width: 100%' >";
    }
    
    
    newTd.setAttribute("colspan", "5");
    newTd.setAttribute("class", "tdwhiteL0");

    
    tr.appendChild(newTd);


   /* var innerRow = fileTable.insertRow();
    innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>"
                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteM0";
            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
        }
    }*/
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

window.setSeries = function(returnValue){
    
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    
    $('[name=spSeries]').val(returnValue[0][1]);//id
    $('[name=spSeriesName]').val(returnValue[0][2]);//name
    
}

////////////////////////////////////////////////////////////////////////////////////
//iframe - 분류체계관련된 function 끝
////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
//iframe - 부품명 자동완성 function 시작
////////////////////////////////////////////////////////////////////////////////////

/*
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
    
    //var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:400px; center:yes");
    //if(typeof returnValue == "undefined" || returnValue == null) {
    //   return;
    //}
    
    //$('[name=partName]').val(returnValue);
    //
}

function setPartName(returnValue){
    $('[name=partName]').val(returnValue);
}
*/
////////////////////////////////////////////////////////////////////////////////////
//iframe - 부품명 자동완성 function 끝
////////////////////////////////////////////////////////////////////////////////////

-->


  


</script>
<form name="partForm" method="post" enctype="multipart/form-data">

<c:if test = "${bAdmin ne 'true'}">
    <input type="hidden" id="homepageIF" name="homepageIF" value = "${baseDto.homepageIF}" />
    <input type="hidden" id="homepage2DIF" name="homepage2DIF" value = "${baseDto.homepage2DIF}" />
    <input type="hidden" id="hompage3DIF" name="hompage3DIF" value = "${baseDto.hompage3DIF}" />
</c:if>

    <input type="hidden" id="partOid" name="partOid" value = "${baseDto.partOid}" />
    <div class="contents-wrap" >
        <%-- 
        <div class="sub-title b-space20"><img src="/plm/portal/images/icon_3.png" />부품 수정</div>
         --%>
        <div class="b-space5" style="text-align:right">
        <!--  latest 버전만 할 수 있는 일  -->
        <c:if test="${baseDto.partLatestVersion}" >
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:savePart();" class="btn_blue">저장</a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        </div>
        <div class="b-space30">
            <table id="mainTable" cellpadding="0" class="table-style-01" summary="" style="table-layout: fixed;">
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
                        <td class="title">부품번호<span class="red-star">*</span></td>
                        <td>${baseDto.partNumber}</td>
                        <td class="title">Rev</td>
                        <td>${baseDto.spPartRevision}</td>
                        <td colspan="2" rowspan="6" class="center" valign="middle">
                           <%--
                               <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
                                    <jsp:param name="oid" value="${baseDto.partOid}"/>
                                </jsp:include>
                                <td width="25%" rowspan="5">
                                    <iframe marginwidth="0" marginheight="10" scrolling="no"  width="300" height="200"  src="/Windchill/extcore/jsp/caddoc/thumnail.jsp?oid=< broker.getOid() >" frameborder="0"></iframe>
                                </td>
                            --%>
                            <iframe marginwidth="0" marginheight="10" scrolling="no"  width="300" height="200"  src="/plm/jsp/edm/thumnail.jsp?oid=${baseDto.partOid}" frameborder="0"></iframe>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">부품명<span class="red-star">*</span></td>
                        <td colspan="3" ><input type="text" id="partName" name="partName" value="${baseDto.partName}" class="txt_field" style="width: 98%" esse="true" esseLabel="부품명" ></td>
                    </tr>
                    <tr>
                        <td class="title">프로젝트 번호<ket:partSpecialEsse attrCode="spProjectNo"  partClazCode="${baseDto.partClazOid}" isEsse="false" /></td>
                        <td><input type="text" id="projectNo" name="projectNo" value="${baseDto.projectNo}" style="width: 70%" esse="<ket:partSpecialEsse attrCode="spProjectNo"  partClazCode="${baseDto.partClazOid}" isEsse="true" />" esseLabel="프로젝트번호"  />
                        <a href="javascript:SearchUtil.selectOneProject('setProjectNo');">
                        <img src="/plm/portal/images/icon_5.png" /></a>
                        <a href="javascript:CommonUtil.deleteValue('projectNo','projectName');">
                        <img src="/plm/portal/images/icon_delete.gif" /></a>
                        </td>
                        <td class="title">개발단계<span class="red-star">*</span></td>
                        <td><input type="hidden" id="spPartDevLevel" name="spPartDevLevel" value = "${baseDto.spPartDevLevel}" />
                        <c:choose>
                        <c:when test="${baseDto.spPartDevLevel=='PC001'}">Proto</c:when>
                        <c:when test="${baseDto.spPartDevLevel=='PC003'}">개발</c:when>
                        <c:when test="${baseDto.spPartDevLevel=='PC004'}">양산</c:when>
                        <c:otherwise></c:otherwise>
                        </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">프로젝트 명</td>
                        <td colspan="3"><input id="projectName" name="projectName" type="text" class="txt_field" style="width: 98%" value="${baseDto.projectName}" readonly></td>
                    </tr>
                    <tr>
                        <td class="title">부품분류<span class="red-star">*</span></td>
                        <input type="hidden" id="partClazOid" name="partClazOid" value="${baseDto.partClazOid}" />
                        <td>${baseDto.partClazNameKr}</td>
                        <td class="title">부품유형<span class="red-star">*</span></td>
                        <td><input type="hidden" id="spPartType" name="spPartType" value = "${baseDto.spPartType}" />
                        <%-- <ket:codeValueByCode codeType="SPECPARTTYPE" code="${baseDto.spPartType}"/> --%>
                            <c:choose>  
                            <c:when test="${baseDto.spPartType=='F'}">제품</c:when>
                            <c:when test="${baseDto.spPartType=='H'}">반제품</c:when>
                            <c:when test="${baseDto.spPartType=='R'}">원자재</c:when>
                            <c:when test="${baseDto.spPartType=='K'}">포장재</c:when>
                            <c:when test="${baseDto.spPartType=='S'}">스크랩</c:when>
                            <c:when test="${baseDto.spPartType=='W'}">상품</c:when>
                            <c:when test="${baseDto.spPartType=='D'}">금형Set</c:when>
                            <c:when test="${baseDto.spPartType=='M'}">금형부품</c:when>
                            <c:when test="${baseDto.spPartType=='P'}">제품</c:when>
                            <c:otherwise>기타</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">기본단위<span class="red-star">*</span></td>
                        <td>
                        <ket:partUnit selectCode="${baseDto.partDefaultUnit}" htmlAttribute=" id='partDefaultUnit' name='partDefaultUnit' style='width: 170px; display: none;' onblur='fm_jmp' multiple='multiple' esse='true' esseLabel='기본단위' "  />
                        </td>
                        <td class="title">상태</td>
                        <td>${baseDto.partState}</td>
                    </tr>
<c:if test = "${bAdmin eq true}">
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

</c:if>                    
                    <tr> 
                        <td class="title">총중량</td>
                        <td><input id="spTotalWeight" name="spTotalWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spTotalWeight}" ></td>
                        <td class="title">부품중량<ket:partSpecialEsse attrCode="spNetWeight"  partClazCode="${baseDto.partClazOid}" isEsse="false" /><span id="spNetWeightSpan" class="red-star" style="display:none;" >*</span></td>
                        <td><input id="spNetWeight" name="spNetWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spNetWeight}" esse="<ket:partSpecialEsse attrCode="spNetWeight"  partClazCode="${baseDto.partClazOid}" isEsse="true" />" esseLabel="부품중량" ></td>
                        <td class="title">스크랩중량</td>
                        <td><input id="spScrabWeight" name="spScrabWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spScrabWeight}" ></td>
                    </tr>
                    <tr>
                         <td class="title">작성부서</td>
                         <td>${baseDto.writeDeptKrName}</td>
                         <td class="title">작성자</td>
                         <td>${baseDto.creatorName}</td>
                         <td class="title">작성일</td>
                         <td>${baseDto.createDate}</td>
                     </tr>
        <c:forEach var="result" items="${baseDto.partClassAttrLinkDTOList}" varStatus="status">
            <c:choose> 
            <c:when test="${result.partAttributeDTO.attrCode != 'spDevSpec' && result.partAttributeDTO.attrCode != 'hompageImgIF' && result.partAttributeDTO.attrCode != 'spTotalWeight' && result.partAttributeDTO.attrCode != 'spNetWeight' && result.partAttributeDTO.attrCode != 'spScrabWeight'}">
                            
        
	            <c:if test="${status.index%3==0}" >
	                <tr id="partSpecPropTr"></c:if>
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
	                      <c:when test="${result.partAttributeDTO.attrCode == 'spRepresentM'}">
	                              <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="${result.partStandardValue}" class="txt_field" style="width: 70%" >
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
	                          <input type="hidden" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="${result.partStandardValue}" >
	                          <input type="text" id="${result.partAttributeDTO.attrCode}Name" name="${result.partAttributeDTO.attrCode}Name" class="txt_field" style="width: 70%"
	                           value="<ket:codeValueByCode codeType="${result.partAttributeDTO.attrCodeType}" code="${result.partStandardValue}" />" >
	                            <a href="javascript:SearchUtil.selectOneSpSeriesPopUp('setSeries');">
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
         </c:when>
         
           <c:when test="${result.partAttributeDTO.attrCode == 'spDevSpec'}">
           <c:if test="${result.partAttributeDTO.attrCode == 'spDevSpec'}" ></tbody><tbody></c:if>
           <tr id="partSpecPropTrDevSpec">
                <td class="title">${fn:replace(result.partAttributeDTO.attrName, '(', '<br/>(')}
                    <c:choose>  
                        <c:when test="${result.essential}"><span class="red-star">*</span></c:when>
                        <c:when test="${result.checked}"><span class="blue-star">*</span></c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
                <td colspan="5" class="tdwhiteL0">
                    <textarea id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="textline" class="txt_field" value="${result.partStandardValue}" style="width: 95%" value="" >${result.partStandardValue}</textarea>
                </td>
            </tr>  
         </c:when>
         <c:when test="${result.partAttributeDTO.attrCode == 'hompageImgIF'}">
          </tbody>
           <tr id="partSpecPropTrHPImg">
               <td class="title">제품 이미지
                    <c:choose>  
                        <c:when test="${result.essential}"><span class="red-star">*</span></c:when>
                        <c:when test="${result.checked}"><span class="blue-star">*</span></c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
                <td colspan="5" class="tdwhiteL0" id="partSpecPropTdHPImg">
                 <c:choose> 
                  <c:when test="${primaryFile.contentoid != null}">
                   <input name='primaryFileOid' id='primaryFileOid' type='hidden' value='${primaryFile.contentoid}'>
                      <a target='download' href='${primaryFile.downURLStr}'>${primaryFile.iconURLStr}</a>&nbsp;
                      <a href='${primaryFile.downURLStr}' target='_blank'>${primaryFile.name}</a>&nbsp;(&nbsp;${primaryFile.fileSizeKB}&nbsp;)&nbsp;
                      <a href="#" onclick="javascript:insertFileLine(); return false;"><b><img src="/plm/portal/images/b-minus.png"></b></a>
                  </c:when>
                  <c:otherwise>
                   <input name='primaryFile' id='primaryFile' type='file' class='txt_fieldRO' style='width: 100%' >
                  </c:otherwise>
                 </c:choose>
                </td>
            </tr>

         </c:when>
          <c:otherwise>
            <input type="text" value="" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_5.png" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_delete.gif" />
          </c:otherwise>
          
         </c:choose>
        </c:forEach>
        
                </tbody>
            <c:if test="${status.index%3==2}" ></c:if>
            <c:if test="${status.index%3==1}" >
                </tr>
                <%-- 끝에 colspan 추가 및 사이즈 조절 --%>
                <script lang="javascript">
                    $('#partSpecPropTbody2 tr#partSpecPropTr:last').each(function(){
                        $(this).find('td:last').attr('colspan',3);
                        
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
            <c:if test="${status.index%3==0}" >
                </tr>
                <%-- 끝에 colspan 추가 --%>
                <script lang="javascript">
                    $('#partSpecPropTbody2 tr#partSpecPropTr:last').each(function(){
                        $(this).find('td:last').attr('colspan',5);
                        
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
                
            </table>
            <%--
            <iframe id="partPropsIfr" name="partPropsIfr" onLoad="javascript:addToTable();" src="" width="0px" height="0px" frameborder="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
             --%>
        </div>
    </div>
</form>
<div id="tabs">
    <ul>
        <li><a class="tabref" href="#tabs-1" rel="/plm/jsp/part/reform/drawingList.jsp?oid=<%=poid%>">도면</a></li>
        <li><a class="tabref" href="#tabs-2" rel="/plm/jsp/part/reform/documentList.jsp?oid=<%=poid%>">문서</a></li>
        <li><a class="tabref" href="#tabs-3" rel="/plm/jsp/part/reform/projectList.jsp?oid=<%=poid%>">프로젝트</a></li>
        <li><a class="tabref" href="#tabs-4" rel="/plm/jsp/part/reform/ecoList.jsp?oid=<%=poid%>">설계변경</a></li>
        <c:choose>  
        <c:when test="${baseDto.spPartType=='D' || baseDto.spPartType=='M'}">
        <li><a class="tabref" href="#tabs-5" rel="/plm/jsp/part/reform/partModList.jsp?oid=<%=poid%>">부품</a></li>
        </c:when>
        <c:otherwise>
        <li><a class="tabref" href="#tabs-6" rel="/plm/jsp/part/reform/moldList.jsp?oid=<%=poid%>">금형</a></li>
        </c:otherwise>
        </c:choose>
        <li><a class="tabref" href="#tabs-7" rel="/plm/jsp/part/reform/dqmList.jsp?oid=<%=poid%>">개발품질문제</a></li>
        <li><a class="tabref" href="#tabs-8" rel="/plm/jsp/part/reform/sampleHistoryList.jsp?oid=<%=poid%>">Sample 이력</a></li>
        <c:choose>  
        <c:when test="${baseDto.spPartType=='D' || baseDto.spPartType=='M'}">
        <li><a class="tabref" href="#tabs-9" rel="/plm/ext/project/trycondition/listTryCondtionByDieNo.do?dieNo=${baseDto.partNumber}">금형 TRY</a></li>
        </c:when>
        <c:otherwise>
        </c:otherwise>
        </c:choose>
    </ul>
    <!-- 첫번째 tab 화면 -->
    <div id="tabs-1" class="tabMain">
        <div class="tabIframeWrapper">
            <iframe class="iframetab" src="/plm/jsp/part/reform/drawingList.jsp?oid=<%=poid%>">Load Failed?</iframe>
        </div>
    </div>
    <!-- 2번째 tab 화면부터는 rel속성에 정의된 url을 호출합니다. -->   
    <div id="tabs-2">
    </div>
    <div id="tabs-3">
    </div>
    <div id="tabs-4">
    </div>
    <div id="tabs-5">
    </div>
    <div id="tabs-6">
    </div>
    <div id="tabs-7">
    </div>
    <div id="tabs-8">
    </div>
    <div id="tabs-9">
    </div>
</div>


<script type="text/javascript">

<c:if test = "${bAdmin eq true}">
    var data1 = "${baseDto.homepageIF}";
    var data2 = "${baseDto.homepage2DIF}";
    var data3 = "${baseDto.hompage3DIF}";

    if(data1 == '') data1 = "선택";
    if(data2 == '') data2 = "선택";
    if(data3 == '') data3 = "선택";
    
    setTimeout("setHpYn('"+ data1 +"')" , 100);
    setTimeout("setHp2DYn('"+ data2 +"')" , 100);
    setTimeout("setHp3DYn('"+ data3 +"')" , 100);
    
</c:if>


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


