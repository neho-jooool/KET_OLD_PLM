<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}

input[type="radio"] {width:13px; height:13px; margin:0 0 0px; padding:0; border: 1px solid #FFF; vertical-align:middle;}
</style>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%


%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->

<!-- 부품 공통 js -->
<script src="/plm/extcore/js/part/partUtil.js"></script>
<!-- 부품 그리드 -->
<script src="/plm/extcore/js/part/partListPopup.js"></script>
<%--
<script type="text/javascript" src="/plm/jsp/bom/js/part_js.jsp"></script>
 --%>
<script type="text/javascript">

//UI 초기화
$(document).ready(function(){
    
    try{
    
    PartList.createPaingGrid();
        
    //------------ start suggest binding ------------//
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
    SuggestUtil.bind('ECONO', 'ecoNo');
    SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
    SuggestUtil.bind('EPMNO', 'relateEpmNo');
    SuggestUtil.bind('PARTNO', 'partNumber');
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
    /*
    CommonUtil.multiSelect('customerEvent2',100);
    CommonUtil.multiSelect('pjtState2',100);
    CommonUtil.multiSelect('authorStatus',100);
    */
    CalendarUtil.dateInputFormat('createStartDate','createEndDate'); 
    
    // 부품 유형 관련 체크 및 DISABLED 처리
    // if(partType == A ) 면 통과 - 아무일 안함
    // else if(partType == P ) 면 - 부품 유형에 die no, molde disable 및 나머지 체크
    // else 이면 해당하는 PartType 체크, 전체 diable
    <c:choose>  
    <c:when test="${partType == 'A'}">
        
    </c:when>
    <c:when test="${partType == 'P'}">
        $('#_spPartType_F').attr("checked", true);
        $('#_spPartType_H').attr("checked", true);
        $('#_spPartType_R').attr("checked", true);
        $('#_spPartType_S').attr("checked", true);
        $('#_spPartType_K').attr("checked", true);
        $('#_spPartType_W').attr("checked", true);
        $("#_spPartType_D").attr("disabled",true);
        $("#_spPartType_M").attr("disabled",true);
        $("#_spPartType_O").attr("disabled",true);
        $("#_spPartType_A").attr("disabled",true);
    </c:when>
    <c:otherwise>
	    <c:choose>  
	    <c:when test="${empty partType}">
	    </c:when>
	    <c:otherwise>
	        <c:if test="${fn:contains(partType,'F')}" >
	            $('#_spPartType_F').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'H')}" >
	            $('#_spPartType_H').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'R')}" >
	            $('#_spPartType_R').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'S')}" >
	            $('#_spPartType_S').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'K')}" >
	            $('#_spPartType_K').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'W')}" >
	            $('#_spPartType_W').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'D')}" >
	            $('#_spPartType_D').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'M')}" >
	            $('#_spPartType_M').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
	        <c:if test="${fn:contains(partType,'O')}" >
	            $('#_spPartType_O').attr("checked", true);
	            $("input[name^=_spPartType]" ).attr("disabled",true);
	        </c:if>
        </c:otherwise>
        </c:choose>
    </c:otherwise>
    </c:choose>
    
    // Die 부품 유형 체크박스 선택에 따라서 '제품번호 제품명' 보여줌
    $("input[name='_spPartType_D']").on("change", function () {
        if(this.checked){
            $("#dieProdTr").show();
        }else{
            $("#dieProdTr").hide();
        }
    }); 
    
    if( $("input[name='_spPartType_D']").is(":checked") ){
    	$("#dieProdTr").show();
    }
        
    $("[name=PartSearchForm]").keypress(function(e) {
        if ( e.which == 13 ) {
            e.preventDefault();
            searchPart();
        }
    });
    
    //client paging
    //Sample.createGrid();
    //server paging
    
    <c:choose>  
    <c:when test="${mode=='M'}">
       var G = Grids[0];
       G.Data.Layout.Data.Cfg.SelectingSingle = "0"; 
    </c:when>
    <c:otherwise>
       var G = Grids[0];
       G.Data.Layout.Data.Cfg.SelectingSingle = "1"; 
    </c:otherwise>
    </c:choose>
    
    $('body').addClass('popup-background02');
    
    }catch(e){
        alert(e.message);
    }
    
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
 <%--
 function clearPartNumber() {
    $("#inputPartNos").val("");
    $("#partNumber").val("");
    initPartNumberTextbox();
  }

  function initPartNumberTextbox() {
    var numberObj = document.getElementById("partNumber");
    var numbertempObj = document.getElementById("partNumberTemp");

    var inited = false;
    var inputPartNos = document.getElementById("inputPartNos").value;

    if (typeof partNumbers != "undefined" && inputPartNos != "") {
      var inputPartNosAry = inputPartNos.split(",");
      var inputPartNosAryLen = inputPartNosAry.length;
      if (inputPartNosAry != null && inputPartNosAryLen > 0) {
        var val = inputPartNosAry[0]
        if (inputPartNosAryLen > 1) {
          val = val + " 외 " + (inputPartNosAryLen-1) + "건";
        }
        numbertempObj.value = val;
        numberObj.value = "";
        numberObj.style.display = "none";
        numbertempObj.style.display = "inline";
        inited = true;
      }
    }

    if (inited == false) {
      numbertempObj.style.display = "none";
      numberObj.style.display = "inline";
    }
  }
  // [END] 부품번호 입력 팝업
  --%>

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
	
	alert('test');
}

// single 선택 확인 버튼 클릭 시
function selectPart(ending) {
	try{
	
    var G = Grids[0];
    
    var arr = new Array();
    var subArr = new Array();
    //var idx = 0;

    if( G != null ){
    	
        var R = G.GetSelRows();

        if( R.length == 0 ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01596") %><%--부품을 선택한 후 확인을 눌러주세요!--%>');
            return;
        }
        
        //NotpermissionErpProdCodeRange : 대표금형에 연결될 수 없는 자재그룹코드이다.해당 값들은 ERP인터페이스시 오류를 발생시킨다.
        var partNoList = new Array();
        var NotpermissionErpProdCodeRange = "201010,201020,201030,201040,201050,201080,201090,201095,202010,202020,202030,202040,202050,202090,202091,601000,602000";
		var Ispermission = "Y";
        for ( var i=0; i<R.length; i++ ) {
        	
             subArr = new Array();
             partNoList.push(R[i].partNumber.toString());
             subArr[0] = R[i].partOid; //oid
             subArr[1] = R[i].partNumber; //number
             subArr[2] = R[i].partName; //name
             subArr[3] = R[i].partRevision; //version - windchill versionida2versioninfo
             subArr[4] = R[i].spPartTypeName; //type  - ex) 반체품 numbercode 'SPECPARTTYPE' value 값 
             // TO-BE 점검 필요
             subArr[5] = R[i].spRepresentM; // R[i].dieNo dieno -> 대표 금형
             subArr[6] = R[i].spRepresentM; /* DieName -> 향후 인터페이스 How?? */
             if( typeof R[i].spRepresentM != "undefined" && R[i].spRepresentM != ""){
            	 subArr[7] = "1"; // DieCnt -> spRepresentM
             }else{
            	 subArr[7] = "0"; // DieCnt -> spRepresentM
             }
             subArr[8] = R[i].partMastOid; //wtpartmaster oid
             subArr[9] = R[i].partIteration; //wtpart iterationida2iterationinfo
             subArr[10]= R[i].spPartType; //  ex) 'H' : 반제품 의미
             subArr[11]= R[i].partDefaultUnit; //  단위 - BOM에서 사용하도록 추가함 ex) EA
             subArr[12]= R[i].spPartRevision; //  KET PART REVISION ex) D00, D01, D02, 0, 1, 2, 3 ....
             subArr[13]= R[i].spMaterDieCode; //  
             subArr[14]= R[i].partState; // STATE
             subArr[15]= R[i].partErpProdCode; // ErpProdCode
             
             if(R[i].spPartType != 'H' && R[i].spPartType != 'R' && R[i].spPartType != 'W'){
            	 Ispermission = "N";
             }
             
             if(R[i].spPartType != 'H' && NotpermissionErpProdCodeRange.indexOf(subArr[15]) > -1){
                 Ispermission = "N";
             }
             
             subArr[16]= Ispermission;
             if( typeof R[i].spMaterType != "undefined" && R[i].spMaterType != ""){
            	 subArr[17]= R[i].spMaterType;
             }
             if( typeof R[i].spColor != "undefined" && R[i].spColor != ""){
            	 subArr[18]= R[i].spColor;
             }
             if( typeof R[i].spMaterialInfo != "undefined" && R[i].spMaterialInfo != ""){
                 subArr[19]= R[i].spMaterialInfo;
             }
             
             if( typeof R[i].spWaterProof != "undefined" && R[i].spWaterProof != ""){
                 subArr[20]= R[i].spWaterProof;
             }
             
             if( typeof R[i].partClazNameKr != "undefined" && R[i].partClazNameKr != ""){
                 subArr[21]= R[i].partClazNameKr;
             }
             
             if( typeof R[i].spNoOfPole != "undefined" && R[i].spNoOfPole != ""){
                 subArr[22]= R[i].spNoOfPole;
             }
             
             if( typeof R[i].spSeries != "undefined" && R[i].spSeries != ""){
                 subArr[23]= R[i].spSeries;
             }
             
             if( typeof R[i].spMTerminal != "undefined" && R[i].spMTerminal != ""){
                 subArr[24]= R[i].spMTerminal;
             }
             
             if( typeof R[i].spMConnector != "undefined" && R[i].spMConnector != ""){
                 subArr[25]= R[i].spMConnector;
             }
             
             if( typeof R[i].spManufAt != "undefined" && R[i].spManufAt != ""){
                 subArr[26]= R[i].spManufAt;
             }
             
             if( typeof R[i].spManufacPlace != "undefined" && R[i].spManufacPlace != ""){
                 subArr[27]= R[i].spManufacPlace;
             }

             //alert(subArr[13]);

             arr.push(subArr);
        }
    }

    // if 모달이면, return array, 
    // else if 모달리스면,
    //       if   recall function 있으면,  recall function 호출,
    //       else recall function 없으면,  default 'selectedPart' function 호출
    <c:choose>  
    <c:when test="${modal=='Y'}">
       window.returnValue = arr;
       <c:if test="${mode=='S'}">
       try {
           hideProcessing();
       } catch(e) {}
       window.close();
       </c:if>
    </c:when>
    <c:otherwise>
	    <c:choose>  
	    <c:when test="${empty fncall}">
	        //alert('fncall:'+'${fncall}');
	        // 리턴 함수가 지정되어있지 않은 경우 기본 Opener 함수 호출
	        if(opener && opener.selectedPart){
	        	//alert('opener.selectedPart(arr);' + arr);
	        	opener.selectedPart(arr);
	        }
	        <c:if test="${mode=='S'}">
            try {
                opener.hideProcessing();
            } catch(e) {}
	        window.close();
	        </c:if>
	    </c:when>
	    <c:otherwise>
	        //alert('otherwise'+'${fncall}');
	        // 리턴 함수가 지정된 경우 해당 Opener 함수 호출%>
	        if(opener && opener.${fncall}){
	        	//alert('opener.${fncall}(arr);' + arr);
	        	var isError = opener.${fncall}(arr);
	        	
	        	if(isError){
	        		return;
	        	}
	        }
	        <c:if test="${mode=='S'}">
	        try {
                opener.hideProcessing();
            } catch(e) {}
	        window.close();
	        </c:if>
	    </c:otherwise>
	    </c:choose>
    </c:otherwise>
    </c:choose>
    
    <c:if test="${mode=='M'}">
     // G.ClearSelection();
    </c:if>
    try{
	    if(ending){
	    	
	    	try {
	            hideProcessing();
	        } catch(e) {}
	        window.close();j
	        
	    	try {
	            opener.hideProcessing();
	        } catch(e) {}
	        window.close();
	    }else{
	    	if(Ispermission == "Y"){
	    		alert("'선택'하신 Part가 추가되었습니다.");
	    	}
	    }
    }catch(e){}
    
  }catch(e){
	 alert(e.message);		
  }
}


////////////////////////////////////////////////////////////////////////////////////
//버튼 제어관련 function 끝
////////////////////////////////////////////////////////////////////////////////////

function helpTest(){
    var    text = " <<제품>> :\n H31~H33, H41~H43, H45~H49, H51~H67, H71~H79, \n"; 
    text = text + " H88, H89,H31~H33, H41~H43, H45~H49, H51~H67, \n";
    text = text + " H71~H79, H88, H89, HKW, HKS, HKH, HKP, HK, K, KD4, KR, \n";
    text = text + " 31, R40~R42, R44, R50, R60, R68 \n\n";
    text = text + " <<Die>> :\n 10,11,1A,1T,1Y,20,21,22,23,26,29,2A,2T,\n 30,40,60,LC,LI,LM,LP,YT \n\n"; 
    text = text + " <<Mold>> :\n 07,10,11,13,16,19,1A,1R,1T,1Y,20,21,22,\n23,24,25,26,27,29,2A,2T,30,40,D0 \n";
    text = text + " ,D1,D2,D3,D4,D5,D6,D7,D8,D9,EJ,ET,IY,L0,LI,LM,LP,\nP0,P1,P2,P3,P4,P5,P6,P7,P8 \n";
    text = text + " ,P9,R0,S1,S2,S3,S4,S7,S8,S9,ST,U0,UP,Z4 \n";
    //text = text + " 제품 : 610056-3, 477016-09, KL10024-00, H615390-2, S200406, R103081, 310464-AS \n";
    //text = text + " Die No : 1T389000 22089001 10860C00 22845A00 LP020000 1Y007000 10929000 1A171000 2T220000 \n";
    //text = text + " 금형 부품 : 11141000-P52, 11183000-STD27, 10861B00-P18-7 11133-D09-2 21906A00-019 1T389000-D09 \n";
    alert(text);
}

$(window).unload( function () { 
	
	 try {
	       hideProcessing();
	 } catch(e) {}
	    
	 try {
	       opener.hideProcessing();
	 } catch(e) {}
 
  }
);

<%--
window.onclose = function(){
	
	try {
	       hideProcessing();
	} catch(e) {}
	   
	try {
	      opener.hideProcessing();
	} catch(e) {}
}
--%>

function closeWindow(){

   try {
       hideProcessing();
   } catch(e) {}
   
   try {
       opener.hideProcessing();
   } catch(e) {}
   
   window.close();
    
}

$(window).onload = function initOnLoad(){
	try{
	<c:if test="${fromPage=='SBL'}">
	    <c:if test="${not empty number}">
	       searchPart();
	    </c:if>
   </c:if>
	}catch(e){
		alert(e.message);
	}
	
}

</script>
	<div class="contents-wrap">
		<div class="popup-title"><img src="/plm/portal/images/icon_3.png" />부품</div>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space10"></td>
            </tr>
            <tr>
                <td class="space2"></td>
            </tr>
        </table>
		<div class="b-space10" style="text-align:right">
		<%--
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:exportCatalog();" class="btn_blue">Catalog Export</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:createPartPopup();" class="btn_blue">부품등록</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:initialize();" class="btn_blue">초기화</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:searchPart();" class="btn_blue">검색</a></span><span class="pro-cell b-right"></span></span></span>
		 --%>
    <c:choose>  
    <c:when test="${mode=='M'}">
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:initialize();" class="btn_blue">초기화</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:searchPart();" class="btn_blue">검색</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:selectPart();" class="btn_blue">선택</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:selectPart(true);" class="btn_blue">확인</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:closeWindow();" class="btn_blue">닫기</a></span><span class="pro-cell b-right"></span></span></span>
    </c:when>
    <c:otherwise>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:initialize();" class="btn_blue">초기화</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:searchPart();" class="btn_blue">검색</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:selectPart();" class="btn_blue">확인</a></span><span class="pro-cell b-right"></span></span></span>
		<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:closeWindow();" class="btn_blue">닫기</a></span><span class="pro-cell b-right"></span></span></span>
    </c:otherwise>
    </c:choose>
		</div>
		<div class="b-space30">
		    <form name="PartSearchForm">
		    <input type="hidden" name="mode" value="${mode}">
            <input type="hidden" name="modal" value="${modal}">
            <input type="hidden" name="fncall" value="${fncall}">
            <input type="hidden" name="fromPage" value="${fromPage}">
            <input type="hidden" name="partTypeDisable" value="${partTypeDisable}">
            <input type="hidden" name="paramPartType" value="${partType}">
            <!-- 부품 번호 검색에서 사용 -->
            <input type="hidden" id="inputPartNos" name="inputPartNos" value="">
            
		    <input type="hidden" name="spPartType" value="" />
		    <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <div style="display: none">지우지 마세요</div>
			<table id="mainTable" cellpadding="0" class="table-style-01" summary="">
				<colgroup>
					<col width="12%" />
					<col width="21%" />
					<col width="12%" />
					<col width="21%" />
					<col width="12%" />
					<col width="22%" />
				</colgroup>
				<tbody>
					<tr>
						<td class="title">부품유형</td>
						<td colspan="5">
						<input id="_spPartType_A" name="_spPartType_A" type="checkbox" value="A" />전체&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_F" name="_spPartType_F" type="checkbox" value="F" />제품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_H" name="_spPartType_H" type="checkbox" value="H" />반제품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_R" name="_spPartType_R" type="checkbox" value="R" />원자재&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_S" name="_spPartType_S" type="checkbox" value="S" />스크랩&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_K" name="_spPartType_K" type="checkbox" value="K" />포장재&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_D" name="_spPartType_D" type="checkbox" value="D" />Die No&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_M" name="_spPartType_M" type="checkbox" value="M" />금형부품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_W" name="_spPartType_W" type="checkbox" value="W" />상품&nbsp;&nbsp;&nbsp;
						<input id="_spPartType_O" name="_spPartType_O" type="checkbox" value="O" />기타&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="spIsYazaki"    name="spIsYazaki"  type="checkbox" value="YES" />YAZAKI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="partLatestReVision" name="partLatestReVision" type="radio" value="true" checked />&nbsp;최신&nbsp;&nbsp;
						<input id="partLatestReVision" name="partLatestReVision" type="radio" value="false" />&nbsp;전체
						</td>
					</tr>
					<tr>
						<td class="title">부품분류</td>
						<td>
						<input type="text" id="partClazKrName" name="partClazKrName" class="txt_field" >
                        <input type="hidden" id="partClazOid" name="partClazOid" value="" >
                        <a href="javascript:SearchUtil.selectPartClaz(loadPartProperty,'onlyLeaf=N&openAll=N');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>
	                       &nbsp;<!-- input style="line-height:10px" type=button value='선택Test용' onclick='javascript:helpTest();' id='innerbutton' --></td>
	                    <td><input type="text" id="partNumber" name="partNumber" class="txt_field" style="width: 70%" value="${number}" >
	                       <input type="text" id="partNumberTemp" name="partNumberTemp" class="txt_fieldRO" readonly style="width: 70%; display: none;">
	                       <a href="javascript:inputPartNumberPopup('partNumber');">
	                       <img src="/plm/portal/images/icon_5.png" border="0"></a>
	                       <a href="javascript:CommonUtil.deleteValue('partNumber');">
	                       <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                   </td>
						<td class="title">부품명</td>
						<td><input id="partName" name="partName" type="text" class="txt_field" style="width: 98%" value="${name}" ></td>
					</tr>
					<tr>
						<td class="title">Rev</td>
						<td><input id="spPartRevision" name="spPartRevision" type="text" class="txt_field" style="width: 95%" value="" ></td>
						<td class="title">상태</td>
						<td><ket:select id="partStateCode" name="partStateCode" className="fm_jmp" style="width: 170px;" lifeCycleState="KET_PART_LC" multiple="multiple" otherHtml=" " /></td>
						<td class="title">프로젝트 번호</td>
						<td>
						<input type="text" id="projectNo" name="projectNo" value="${projectNo }" esse="true" esseLabel="프로젝트번호"  />
                        <a href="javascript:SearchUtil.selectOneProject(setProjectNo);">
                        <img src="/plm/portal/images/icon_5.png" /></a>
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
                            style="cursor: hand;">
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
		</div>
	</div>
