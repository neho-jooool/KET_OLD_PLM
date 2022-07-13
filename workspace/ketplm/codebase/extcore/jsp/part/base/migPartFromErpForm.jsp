<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script type="text/javascript">
<!--

var spPartType_options = "";
$(document).ready(function(){

    CommonUtil.singleSelect('spPartType',100);
    spPartType_options = $('#spPartType').html();
    // 고정
    $("select[name=spPartType]").siblings().attr('disabled', 'disabled');
    //alert(spPartType_options);
    
    CommonUtil.singleSelect('spPartDevLevel',100);
    CommonUtil.singleSelect('partDefaultUnit',100);
    // 고정
    $("select[name=partDefaultUnit]").siblings().attr('disabled', 'disabled');
    
    CommonUtil.setNumberField('spTotalWeight'); 
    CommonUtil.setNumberField('spNetWeight'); 
    CommonUtil.setNumberField('spScrabWeight'); 
    
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', null, 'projectName'); // REVIEWPROJNO, PRODUCTPROJNO
    
    //document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?oid=ext.ket.part.entity.KETPartClassification:897950025";
    //$('#partPropsIfr').onload=addToTable;
    <%--
    $('#spPartType').change(function(){
        var isNotMold = true;
        $("#spPartType option:selected").each(function () {
            if( $(this).val() == 'M' ){
                isNotMold = false;
                return false;
            }
        });
        
        $('#partNumber').attr("readonly", isNotMold);
    });
    --%>
    
    // 반제품에서 금형(Set)등록을 클릭한 경우에 처리함.
    <%--
    <c:if test="${not empty partProdOid}" >
        $("select[name=spPartType]").children('option').each(function() {
            if ( $(this).val() != 'D' ){
                   $(this).remove();
            }
        });
        CommonUtil.singleSelect('spPartType', 100);
    </c:if>
    --%>
    
    // 생산처 초기화
    $("#spManufAt").change(function(){
        $("#spManufacPlace").val("");
        $("#spManufacPlaceTemp").val("");
    });
    $("#spMContractSAt").change(function(){
         $("#spDieWhere").val("");
         $("#spDieWhereTemp").val("");
    });
     
    // 프로젝트 개발단계 자동 연계
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
    
    // 총중량, 부품중량, 스크랩중량 계산 처리
    /*
    $("#spTotalWeight").blur(function(){
        
        // 총중량
        var total = $(this).val();
        if(total == ""){ total = "0";}
        
        if(isNaN(total)){
            alert("총중량 '" + total + "'이 숫자포멧이 아닙니다.");
            $(this).val("");
            $("#spNetWeight").trigger("blur");
            return false;
        }
        
        if(total.indexOf('.') != -1){
            var totalTemp = total.substring(total.indexOf('.')+1);
            if(typeof totalTemp != "undefined" && totalTemp.length > 3){
                total = total.substring(0, total.indexOf('.')+1) + totalTemp.substring(0,3);
                $(this).val(total);
            }
        }
        
        var totalF = parseFloat(total);
        
        // 부품중량
        var partWeight = $("#spNetWeight").val();
        if(partWeight == ""){ partWeight = "0";}
        
        if(isNaN(partWeight)){
            alert("부품중량 '" + partWeight + "'이 숫자포멧이 아닙니다.");
            $("#spNetWeight").val("").trigger("blur");
            return false;
        }
        
        if(partWeight.indexOf('.') != -1){
            var partWeightTemp = partWeight.substring(partWeight.indexOf('.')+1);
            if(typeof partWeightTemp != "undefined" && partWeightTemp.length > 3){
                partWeight = partWeight.substring(0, partWeight.indexOf('.')+1) + partWeightTemp.substring(0,3);
                $("#spNetWeight").val(partWeight);
            }
        }
        
        var partWeightF = parseFloat(partWeight);

        // 스크랩중량        
        if(totalF - partWeightF < 0){
            alert("'총중량'이 '부품중량'보다 작아서 '총중량'을 '부품중량'으로 변경합니다.");
            $(this).val(partWeight);
            $("#spScrabWeight").val("0");
            return;
        }
        
        var scrabWeightF = parseFloat(( totalF - partWeightF )).toFixed(3)+ "";
        $("#spScrabWeight").val( scrabWeightF );
        
    });
    
    $("#spNetWeight").blur(function(){

        // 부품중량
        var partWeight = $(this).val();
        if(partWeight == ""){ partWeight = "0";}
        
        if(isNaN(partWeight)){
            alert("부품중량 '" + partWeight + "'이 숫자포멧이 아닙니다.");
            $(this).val("").trigger("blur");
            return false;
        }
        
        if(partWeight.indexOf('.') != -1){
            var partWeightTemp = partWeight.substring(partWeight.indexOf('.')+1);
            if(typeof partWeightTemp != "undefined" && partWeightTemp.length > 3){
                partWeight = partWeight.substring(0, partWeight.indexOf('.')+1) + partWeightTemp.substring(0,3);
                $(this).val(partWeight);
            }
        }
        
        var partWeightF = parseFloat(partWeight);
        
        // 스크랩중량
        var scrabWeight = $("#spScrabWeight").val();
        if(scrabWeight == ""){ scrabWeight = "0";}
        
        if(isNaN(scrabWeight)){
            alert("스크랩중량 '" + scrabWeight + "'이 숫자포멧이 아닙니다.");
            $("#spScrabWeight").val("");
            $(this).val(partWeight).trigger("blur");
           return false;
        }
        
        if(scrabWeight.indexOf('.') != -1){
            var scrabWeightTemp = scrabWeight.substring(scrabWeight.indexOf('.')+1);
            if(typeof scrabWeightTemp != "undefined" && scrabWeightTemp.length > 3){
                scrabWeight = scrabWeight.substring(0, scrabWeight.indexOf('.')+1) + scrabWeightTemp.substring(0,3);
                $("#spScrabWeight").val( scrabWeight );
            }
        }
        
        var scrabWeightF = parseFloat(scrabWeight);
        
        // 총중량
        var totalF = parseFloat( partWeightF + scrabWeightF ).toFixed(3) + ""; 
        $("#spTotalWeight").val( totalF );
        
    });
    */
    
});

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

/**
 * 부품분류 특정 조건만 OK
 */
function selectOnePartClazWithCallBackErp(callBackFn, dataParam) {
    var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
    var url = "/plm/jsp/dms/partClazTreePopup.jsp?singleSel=true&" + _dataParam;;
    var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=700px; dialogHeight:700px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    callBackFn(returnValue);
}

function loadPartProperty(returnValue){
    
    var valueField = 'partClazOid';
    var displayField = 'partClazKrName';
    $('[name='+valueField+']').val(returnValue[0].id);//oid
    $('[name='+displayField+']').val(returnValue[0].name);//kr name
    
    showProcessing();
    document.partPropsIfr.location = "/plm/ext/part/base/registProps.do?call=&oid=" + returnValue[0].id + "&classPartType=" + '${baseDto.spPartType}';
    
}

var testId = 2;
function savePart(){
    
    var form = document.forms[0];
    
    /*
    var txtVal = "22";
    var selIdx = 0;
    
    if($('[name=projectNo]').val() == "") $('[name=projectNo]').val("A11B02" + testId);
    
    testId++;
    
    $("[esse=true]").each(function(){
        
        if (this.type == "select-multiple" && this.options.selectedIndex == -1 ) {
            this.options.selectedIndex = selIdx;
        } else if (this.type == "select-one" &&  this.options.selectedIndex == -1) {
            this.options.selectedIndex = selIdx;
        } else if (this.type == "checkbox") {
            var obj = document.getElementsByName(this.name);
            obj[j].checked = true;
        } else if (this.type == "radio") {
            var obj = document.getElementsByName(this.name);
            for ( var j = 0; j < obj.length; j++) {
                if (obj[j].checked == true) {
                }else{
                    obj[j].checked = true;
                    break;
                }
            }
        } else if (this.type == "hidden" && this.value.replace(/\s*$/, '') == "") {
            
        } else if (this.type == "textarea" && this.value.replace(/\s*$/, '') == "") {
            this.value = txtVal;
        } else if (this.type == "text" && this.value.replace(/\s*$/, '') == "") {
            this.value = txtVal;
        }
    });
    */
    
    // PLM 부품 번호 존재하는지 체크
    if(form.partNumber && $('#partNumber').val() != ""){
        var checkStr = checkPartNumber($('#partNumber').val());
        if("E" == checkStr){
            hideProcessing();
            return;
        }else if("Y" == checkStr){
            alert("이미 존재하는 부품 번호가 있습니다.\n해당 부품을 등록할 수 없습니다.");
            hideProcessing();
            return;
        }else if("N" == checkStr){
        }
    }
    
    // validation check
    if(!CommonUtil.checkEsseOk()){
        return;
    }
    
    /*
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
    */
    
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
            
            // 금형 부품번호 존재하는지 체크 ( PLM 만 )
            // 위에서 체크함
            <%--
            if(form.partNumber && $('#partNumber').val() != ""){
                var checkStr = checkPartNumber($('#partNumber').val(), 'M');
                if("E" == checkStr){
                    checkMoldLength = false;
                    hideProcessing();
                    return;
                }else if("Y" == checkStr){
                    alert("이미 존재하는 부품 번호가 있습니다.\n부품을 등록할 수 없습니다.");
                    checkMoldLength = false;
                    hideProcessing();
                    return;
                }else if("N" == checkStr){
                }
            }
            --%>
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
            if( $('#spMProdAt').val() == "2" || $('#spMProdAt').val() == "3" ){
                if( $('#spRepresentM').val() == "" ){
                    alert("Die No의 생산구분이 '추가' 이거나 'Modify'일 경우는 대표금형값을 반드시 입력해야 합니다.");
                    checkDiespRepresentM = false;
                }else if($('#spRepresentM').val().length != 8){
                    alert("대표금형은 8자 이어야 합니다.");
                    checkDiespRepresentM = false;
                }
            }
            
            if(!checkDiespRepresentM) return false;
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
        
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02463")%><%--저장하시겠습니다--%>')){ 
        hideProcessing();
        return;
    }
    
    $.ajax({
        type: 'post',
        async: false,
        url: '/plm/ext/part/base/insertErpPart.do',
        data: $('[name=partForm]').serialize(),
        success: function (data) {
            if(data != 'Fail'){
                disabledAllBtn();
                alert('<%=messageService.getString("e3ps.message.ket_message", "02460")%><%--저장되었습니다--%>');
                var mOid = data;
                //alert(mOid + ":" + data);
                location.replace("/plm/jsp/bom/ViewPart.jsp?poid=" + mOid);
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
        
        SuggestUtil.bind('PARTNO', 'spScrabCode');
        SuggestUtil.bind('PARTNO', 'spRepresentM');
        
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
        
        <%-- 부품 등록값 자동 세팅 --%>
        var type = "${baseDto.spPartType}";
        if(type == "M"){
        	
        	inputErpSelectValue( "spMaterDie", "${baseDto.spMaterDie}");
        	
        }else if(type == "D"){
        	
            inputErpSelectValue( "spMContractSAt", "${baseDto.spMContractSAt}");
            inputErpSelectValue( "spMMoldAt", "${baseDto.spMMoldAt}");
            inputErpSelectValue( "spMMakingAt", "${baseDto.spMMakingAt}");
            inputErpSelectValue( "spMProdAt", "${baseDto.spMProdAt}");
            inputErpSelectValue( "spPuchaseGroup", "${baseDto.spPuchaseGroup}");
            inputErpSelectValue( "spPlant", "${baseDto.spPlant}");
            
            inputErpTextValue( "spDevManNm", "${baseDto.spDevManNm}", true);
            inputErpTextValue( "spDesignManNm", "${baseDto.spDesignManNm}", true);
            inputErpTextValue( "spManufacManNm", "${baseDto.spManufacManNm}", true);
            inputErpTextValue( "spDieWhere", "${baseDto.spDieWhere}", false);
            inputErpTextValue( "spObjectiveCT", "${baseDto.spObjectiveCT}", false);
            inputErpTextValue( "spCavityStd", "${baseDto.spCavityStd}", false);
            
        }else{

            inputErpTextValue( "spProdSize", "${baseDto.spProdSize}", false);
            inputErpTextValue( "spScrabCode", "${baseDto.spScrabCode}", true);

            inputErpSelectValue( "spSeries", "${baseDto.spSeries}");
            inputErpSelectValue( "spWaterProof", "${baseDto.spWaterProof}");
        	
            // 재질 3가지
            // 원재료 Maker, 원재료 Type이 없으므로 넣을 수 없다.
            //inputErpSelectValue( "spMaterialInfo", "${baseDto.spMaterialInfo}");
            //inputErpSelectValue( "spMaterNotFe", "${baseDto.spMaterialInfo}");
            inputErpSelectValue( "spMaterPurch", "${baseDto.spMaterialInfo}");
            
            // spcolor > 컬러 3가지 적용
            inputErpSelectValue( "spColor", "${baseDto.spColor}");
            inputErpSelectValue( "spColorPurch", "${baseDto.spColor}");
            inputErpSelectValue( "spColorElec", "${baseDto.spColor}");

            inputErpSelectValue( "spPlant", "${baseDto.spPlant}");
            
            inputErpTextValue( "spRepresentM", "${baseDto.spRepresentM}", true);
            
            inputErpSelectValue( "spIsYazaki", "${baseDto.spIsYazaki}");
            
            inputErpTextValue( "spYazakiNo", "${baseDto.spYazakiNo}", false);

        }
        

        //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에
        // -. Set, Famil를 선택할 때 -> Family disable 및 빈값으로
        // -. Modify 선택할 때는 검색하는 모양으로 변경
        // -. 추가 선택할 경우에는 Select 박스로 변경 및 제품 Oid가 있으면 Option 생성해 줌
        if(document.forms[0] && typeof document.forms[0].spRepresentM_select != "undefined"){
            
            CommonUtil.singleSelect("spRepresentM_select", 100);
            $("#spRepresentM_select").siblings().hide();
            $("#spRepresentM").show();
            $("#spRepresentM").attr("readonly", false);
            $("#spRepresentM").val("");
            $("#spRepresentM_search_img").show();
            $("#spRepresentM_search_img_a").show();
            $("#spRepresentM_del_img").show();
            $("#spRepresentM_del_img_a").show();
                       
            //Die - 제품 - 대표금형 자동 선택 처리 : 일반일 경우에 대표금형 자동완성 처리
            SuggestUtil.bind('PARTNO', 'spRepresentM');
        }
    }
    
    hideProcessing();
}

function inputErpSelectValue(name, value){
	
	if(typeof value == "undefined" || value == null || value == ""){
		
	}else{	
	  $("[name=" + name +"]").val(value);
	  CommonUtil.singleSelect(name, 100);
	  $("[name=" + name +"]").siblings().attr("disabled", "disabled");
	}
}

function inputErpTextValue(name, value, disableWithSibling){
	
    if(typeof value == "undefined" || value == null || value == ""){
        
    }else{
    	
    	$("[name=" + name +"]").val(value);
    	$("[name=" + name +"]").attr("readonly", true);
    	if(typeof value != "undefined" && disableWithSibling != null && disableWithSibling == true){
    	$("[name=" + name +"]").siblings().attr("disabled", "disabled");
    	$("[name=" + name +"]").siblings().filter("a").bind('click', function(e){
            e.preventDefault();
    	 });
    	}    	
    }
}

// irame 로드후 채번 코드 입력
var numberingCodeByClaz = "";
function setPartNumber(_numberingCodeByClaz){
    //alert(_numberingCodeByClaz);
    <%-- 부품 마이그레이션 등록시는 불필요함
    $('#partNumber').val("");
    numberingCodeByClaz = _numberingCodeByClaz;
    $('#partNumber').val(_numberingCodeByClaz);
    --%>
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

var autoGenOid = ""; // partName
function setAutoGenOid(namingOid){
    //if(namingOid && namingOid !="")
    //  alert("namingOid:" + namingOid);
        
    autoGenOid = namingOid;
}

//생산처, 금형제작처 가져오기

//일반제품의 경우 : 생산처구분 [spManufAt:NumberCode => SPECMSELFCONTRACTFLAG] 이 사내인가 외주인가에 따라서
//생산처[spManufacPlace]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 
//금형SET의 경우 : 사급구분 [spMContractSAt:NumberCode => SPECMSELFCONTRACTFLAG]이 사내인가 외주인가
//금형제작처[spDieWhere]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 

function setManuFacPlace( attrName, suffix ) {
 
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
 
     var mode = "single";
     var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode=" + mode + "&viewType=noTree";
     var attache = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
     if(typeof attache == "undefined" || attache == null) {
         return;
     }
     
     addManuFacPlace(attrName, suffix, attache);
    
 }else if(gubun = "2"){
     
     var callBackFuc = "addKetPartner";
     var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method="+callBackFuc;
        openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
 }
 
}

//사내 생산처 등록
function addManuFacPlace(attrName, suffix, arr){
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
<div class="popup-background" style="padding-top:20px">
<form name="partForm">
    <input type="hidden" id="partProdMigNumber" name="partProdMigNumber" value="${baseDto.partProdMigNumber}" >
    <input type="hidden" id="partNumberFromErp" name="partNumberFromErp" value="Y" >
    <%--
    <input type="hidden" id="partProdOid" name="partProdOid" value="${partProdOid}" >
     --%>
    <div class="contents-wrap" >
        <div class="popup-title"><img src="/plm/portal/images/icon_3.png" />부품 등록</div>
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
                        <td colspan="5" class="txt_field"><input id="partNumber" name="partNumber" type="text" class="txt_field" style="width: 95%" value="${baseDto.partNumber}"  readonly="readonly" ></td>
                    </tr>
                    <tr>
                        <td class="title">부품명<span class="red-star">*</span></td>
                        <td colspan="5" class="txt_field"><input type="text" id="partName" name="partName" value="${baseDto.partName}" class="txt_field" style="width: 95%" readonly="readonly" esse="true" esseLabel="부품명" ></td>
                    </tr>
                    <tr>
                        <td class="title">부품분류<span class="red-star">*</span></td>
                        <td><input type="text" id="partClazKrName" name="partClazKrName" class="txt_field" >&nbsp;&nbsp;
                        <input type="hidden" id="partClazOid" name="partClazOid" value="" esse="true" esseLabel="부품분류체계" >
                        <a href="javascript:SearchUtil.selectOnePartClazWithCallBack(loadPartProperty,'onlyLeaf=Y&openAll=N');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;
                        <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>

                        <td class="title">부품유형<span class="red-star">*</span></td>
                        <td>
                        <ket:select id="spPartType" name="spPartType" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="SPECPARTTYPE" value="${baseDto.spPartType}" multiple="multiple" otherHtml=" esse='true' esseLabel='부품유형' " />
                        </td>
                        <td class="title">개발단계<span class="red-star">*</span></td>
                        <td><select id="spPartDevLevel" name="spPartDevLevel" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
                        <option value="PC003" <c:if test="${baseDto.spPartDevLevel=='PC003'}">selected</c:if> >개발</option>
                        <option value="PC004" <c:if test="${baseDto.spPartDevLevel=='PC004'}">selected</c:if> >양산</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td class="title">기본단위<span class="red-star">*</span></td>
                        <td><ket:partUnit selectCode="${baseDto.partDefaultUnit}" htmlAttribute=" id='partDefaultUnit' name='partDefaultUnit' style='width: 170px; display: none;' onblur='fm_jmp' multiple='multiple' esse='true' esseLabel='기본단위' "  /></td>
                        <td class="title">프로젝트 번호<span id="projectNoSpan"  class="red-star" style="display:none;" >*</span></td>
                        <td><input type="text" id="projectNo" name="projectNo" value="" esse="false" esseLabel="프로젝트번호"  />&nbsp;&nbsp;
                        <a href="javascript:SearchUtil.selectOneProject(setProjectNo);">
                        <img src="/plm/portal/images/icon_5.png" /></a>&nbsp;&nbsp;
                        <a href="javascript:CommonUtil.deleteValue('projectNo','projectName');">
                        <img src="/plm/portal/images/icon_delete.gif" /></a></td>
                        <td class="title">프로젝트 명</td>
                        <td><input id="projectName" name="projectName" type="text" class="txt_field" style="width: 95%" value="" readonly></td>
                    </tr>
                    <tr> 
                        <td class="title">총중량</td>
                        <td><input id="spTotalWeight" name="spTotalWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spTotalWeight}" readonly="readonly" ></td>
                        <td class="title">부품중량<span id="spNetWeightSpan" class="red-star" style="display:none;" >*</span></td>
                        <td><input id="spNetWeight" name="spNetWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spNetWeight}" readonly="readonly" esse="false" esseLabel="부품중량" ></td>
                        <td class="title">스크랩중량</td>
                        <td><input id="spScrabWeight" name="spScrabWeight" type="text" class="txt_field" style="width: 95%" value="${baseDto.spScrabWeight}" readonly="readonly" ></td>
                    </tr>
                </tbody>
            </table>
            <iframe id="partPropsIfr" name="partPropsIfr" onLoad="javascript:addToTable();" src="" width="0px" height="0px" frameborder="0" marginwidth="0" marginheight="0" scrolling="yes"></iframe>
        </div>
    </div>
</form>
</div>