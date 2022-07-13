<%@page import="java.text.SimpleDateFormat"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<!-- 부품 공통 js -->
<script src="/plm/extcore/js/part/partUtil.js"></script>
<!-- 부품 그리드 -->
<script src="/plm/extcore/js/wfm/workspace/listMyPart.js"></script>
<%
  Calendar cal = Calendar.getInstance();
  cal.add(Calendar.MONTH, -1);
  String postdate = DateUtil.getCurrentDateString("d");
  String predate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
%>
<script type="text/javascript">

    // UI 초기화
    $(document).ready(function() {
        //------------ start suggest binding ------------//
        SuggestUtil.bind('PARTNO', 'partNumber');
        SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
        SuggestUtil.bind('ECONO', 'ecoNo');
        SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
        SuggestUtil.bind('EPMNO', 'relateEpmNo');
        //------------ end suggest binding ------------//
        // default 한달 설정
        $('[name=createStartDate]').val('<%=predate%>');
        $('[name=createEndDate]').val('<%=postdate%>');
        // Calener field 설정
        /*
        CalendarUtil.dateInputFormat('planDate');
        CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다.
         */
        // multiselect
        CommonUtil.multiSelect('partStateCode', 100);
        CommonUtil.multiSelect('spPartDevLevel', 100);
        /*
        CommonUtil.multiSelect('customerEvent2',100);
        CommonUtil.multiSelect('pjtState2',100);
        CommonUtil.multiSelect('authorStatus',100);
         */
        CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');
         
         $("[name=PartSearchForm]").keypress(function(e) {
             if ( e.which == 13 ) {
                 searchPart();
                 return false;
             }
         });
         
         //CommonUtil.setNumberField('spTotalWeight'); 
         //CommonUtil.setNumberField('spNetWeight'); 
         //CommonUtil.setNumberField('spScrabWeight'); 
         
        //client paging
        //Sample.createGrid();
        //server paging
        PartList.createPaingGrid();
        treeGridResize("#PartListSearchGrid","#PartListSearchGridDiv");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        //searchPart();
    });
    
    function _setPartNo(partNumbers){
    	if (typeof partNumbers != "undefined" && partNumbers.length > 0) {
            $("#partNumber").val(partNumbers);
        }
    }
    
    //[START] 부품번호 입력 팝업
    function inputPartNumberPopup() {
      SearchUtil.selectInputPartNumberPopup('_setPartNo');
    }

    $("form[name=PartSearchForm]").keypress(function(e) {
        if ( e.which == 13 ) {
            searchPart();
            return false;
        }
    });
    
    ////////////////////////////////////////////////////////////////////////////////////
    // 검색조건관련 function 시작
    ////////////////////////////////////////////////////////////////////////////////////

    function loadPartProperty(checkedNode) {

        var nodeOIdStr = '', nodeNameStr = '';
        for ( var i = 0; i < checkedNode.length; i++) {
            if (i == checkedNode.length - 1) {
                nodeOIdStr += checkedNode[i].id;
                nodeNameStr += checkedNode[i].name;
            } else {
                nodeOIdStr += checkedNode[i].id + ',';
                nodeNameStr += checkedNode[i].name + ',';
            }
        }

        var valueField = 'partClazOid';
        var displayField = 'partClazKrName';
        $('[name=' + valueField + ']').val(nodeOIdStr);//oid
        $('[name=' + displayField + ']').val(nodeNameStr);//kr name

        showProcessing();

        document.partPropsIfr.location = "/plm/ext/part/base/registPropsMix.do?oid=" + nodeOIdStr;
    }

    function setPartNo(objArr) {
        var partNo = "";
        for ( var i = 0; i < objArr.length; i++) {
            partNo += objArr[i][1] + ",";
        }
        if (partNo.length > 0) partNo = partNo.substring(0, partNo.length - 1);
        $('[name=partNumber]').val(partNo);
    }

    function setProjectNo(objArr) {
        var projectNo = "";
        var projectName = "";
        for ( var i = 0; i < objArr.length; i++) {
            projectNo += objArr[i][1] + ",";
            projectName = objArr[i][2] + ",";
        }

        if (projectNo.length > 0) projectNo = projectNo.substring(0, projectNo.length - 1);
        if (projectName.length > 0) projectName = projectName.substring(0, projectName.length - 1);

        //hideProcessing();

        $('[name=projectNo]').val(projectNo);
        $('[name=projectName]').val(projectName);

    }

    function setEpmNo(objArr) {
        var epmNo = "";
        for ( var i = 0; i < objArr.length; i++) {
            epmNo += objArr[i][1] + ",";
        }
        if (epmNo.length > 0) epmNo = epmNo.substring(0, epmNo.length - 1);
        $('[name=relateEpmNo]').val(epmNo);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //검색조건관련 function 끝
    ////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////
    // iframe - 분류체계관련된 function 시작
    ////////////////////////////////////////////////////////////////////////////////////

    //iframe 로딩후 table에 붙여 넣기
    function addToTable() {
        
    	/*
        $('#mainTable > #partSpecPropTr').remove();

        var trArrayVal = $('#partPropsIfr').contents().find("#partSpecPropTbody").html()
        if (trArrayVal) {
            //alert(trArrayVal);
            $('#mainTable').append(trArrayVal);
            $("select[ketMultiSelect=N]").each(function() {
                //CommonUtil.singleSelect(this.name, 100);
                CommonUtil.multiSelect(this.name, 100);
            });
            $("select[ketMultiSelect=Y]").each(function() {
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
        */

        hideProcessing();
    }

    //irame 로드후 채번 코드 입력
    function setPartNumber(numberingCodeByClaz) {
        //alert(numberingCodeByClaz);
        //$('#partNumber').val("");
        //$('#partNumber').val(numberingCodeByClaz);
    }

    //부품 검색 결과 저장
    function setPartNospMTerminal(objArr) {
        setPartNo(objArr, 'spMTerminal');
    }
    function setPartNospMatchBulb(objArr) {
        setPartNo(objArr, 'spMatchBulb');
    }
    function setPartNospMConnector(objArr) {
        setPartNo(objArr, 'spMConnector');
    }
    function setPartNospMClip(objArr) {
        setPartNo(objArr, 'spMClip');
    }
    function setPartNospMRH(objArr) {
        setPartNo(objArr, 'spMRH');
    }
    function setPartNospMCover(objArr) {
        setPartNo(objArr, 'spMCover');
    }
    function setPartNospMWireSeal(objArr) {
        setPartNo(objArr, 'spMWireSeal');
    }
    function setPartNo(objArr, name) {
        /*
        var partNo= "";
        for ( var i = 0; i < objArr.length; i++ ) {
            partNo+= objArr[i][1] + ",";
        }
        if(partNo.length > 0) partNo= partNo.substring(0, partNo.length-1);
        $('[name=' + name +']').val(partNo);
         */
    }

    //var autoGenOid = ""; // partName
    function setAutoGenOid(namingOid) {
        //if(namingOid && namingOid !="")
        //  alert("namingOid:" + namingOid);

        //autoGenOid = namingOid;
    }

	 // 생산처, 금형제작처 가져오기
	
	 // 일반제품의 경우 : 생산처구분 [spManufAt:NumberCode => SPECMSELFCONTRACTFLAG] 이 사내인가 외주인가에 따라서
	 // 생산처[spManufacPlace]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 
	 // 금형SET의 경우 : 사급구분 [spMContractSAt:NumberCode => SPECMSELFCONTRACTFLAG]이 사내인가 외주인가
	 // 금형제작처[spDieWhere]의 값이 사내면 사내생산처(NumberCode => PRODUCTIONDEPT), 외주면 외주생산처(Class : ERP CODE => KETPARTNER) 
	
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
	
	 // 사내 생산처 등록
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
    // 버튼 제어관련 function 시작
    ////////////////////////////////////////////////////////////////////////////////////

    // 초기화
    function initialize() {

        $('#mainTable > #partSpecPropTr').remove();
        $('[name=PartSearchForm]')[0].reset();
        $('#partClazOid').val("");
    }

    // 부품등록
    function createPartPopup() {

        PartUtil.regView();
    }

    function searchPart() {
    	
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
            
            //alert(checkTwoCount);
            if( checkTwoCount < 2){
                alert(" 부품번호, 도면번호, ECO 번호, 프로젝트 번호란에\n Full Code를 넣거나\n 검색 조건을 2가지 이상 추가해야 검색 가능 합니다.");
                return;
            }
        }

        var spPartTypeVal = "";
        $("input[name^=_spPartType]").each(function() {
            if (this.checked) {

                if (this.value == "A") {
                    spPartTypeVal = "";
                    return false;
                }

                if (spPartTypeVal == "")
                    spPartTypeVal = spPartTypeVal + this.value;
                else
                    spPartTypeVal = spPartTypeVal + "," + this.value;
            }
        });

        $("[name=spPartType]").val(spPartTypeVal);

        PartList.search();
    }

    function exportCatalog() {

        alert('test');
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //버튼 제어관련 function 끝
    ////////////////////////////////////////////////////////////////////////////////////
</script>
<div class="contents-wrap" style="margin-bottom: 0px;">
  <div class="sub-title">
    <img src="/plm/portal/images/icon_3.png" />My Part
  </div>
  <div class="current-location">
    <span><img src="/plm/portal/images/icon_navi.gif" />Home<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "02435")%><%--작업공간--%>
      <img src="/plm/portal/images/icon_navi.gif" />My Part</span>
  </div>
  <div class="b-space5" style="text-align: right">
    <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:initialize();"
          class="btn_blue"
        >초기화</a></span><span class="pro-cell b-right"></span></span></span> <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a
          href="javascript:searchPart();" class="btn_blue"
        >검색</a></span><span class="pro-cell b-right"></span></span></span>
  </div>
  <div>
    <form name="PartSearchForm">
      <input type="hidden" name="spPartType" value="" />
      <input type="hidden" name="fromMyPart" value="Y" />
      <!-- 부품 번호 검색에서 사용 -->
      <input type="hidden" id="inputPartNos" name="inputPartNos" value="">
      <!-- 검색영역 collapse/expand -->
      <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
      <div style="display: none">삭제하지 마세요</div>
      <table id="mainTable" cellpadding="0" class="table-style-01" summary="">
        <colgroup>
          <col width="10%" />
          <col width="40%" />
          <col width="10%" />
          <col width="40%" />
        </colgroup>
        <tbody>
          <tr>
            <td class="title">부품유형</td>
            <td colspan="3"><input id="_spPartType_A" name="_spPartType_A" type="checkbox" value="A" />전체&nbsp;&nbsp;&nbsp; <input id="_spPartType_F" name="_spPartType_F" type="checkbox"
                value="F"
              />제품&nbsp;&nbsp;&nbsp; <input id="_spPartType_H" name="_spPartType_H" type="checkbox" value="H" />반제품&nbsp;&nbsp;&nbsp; <input id="_spPartType_R" name="_spPartType_R" type="checkbox"
                value="R"
              />원자재&nbsp;&nbsp;&nbsp; <input id="_spPartType_S" name="_spPartType_S" type="checkbox" value="S" />스크랩&nbsp;&nbsp;&nbsp; <input id="_spPartType_K" name="_spPartType_K" type="checkbox"
                value="K"
              />포장재&nbsp;&nbsp;&nbsp; <input id="_spPartType_D" name="_spPartType_D" type="checkbox" value="D" />Die No&nbsp;&nbsp;&nbsp; <input id="_spPartType_M" name="_spPartType_M"
                type="checkbox" value="M"
              />금형부품&nbsp;&nbsp;&nbsp; <input id="_spPartType_W" name="_spPartType_W" type="checkbox" value="W" />상품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input id="spIsYazaki" name="spIsYazaki"
                type="checkbox" value="YES"
              />YAZAKI&nbsp;&nbsp;&nbsp;</td>
          </tr>
          <tr>
            <td class="title">부품분류</td>
            <td><input type="text" id="partClazKrName" name="partClazKrName" class="txt_field">&nbsp;&nbsp; <input type="hidden" id="partClazOid" name="partClazOid" value=""> <a
              href="javascript:SearchUtil.selectOnePartClazPopUp('loadPartProperty','onlyLeaf=Y&openAll=Y');"
            > <img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp; <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');"> <img
                src="/plm/portal/images/icon_delete.gif" border="0"
              ></a></td>
            <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
            <td><input type="text" id="partNumber" name="partNumber" class="txt_field" style="width: 70%" value="${number}"> <input type="text" id="partNumberTemp" name="partNumberTemp"
                class="txt_fieldRO" readonly style="width: 70%; display: none;"
              > <a href="javascript:inputPartNumberPopup('partNumber');"> <img src="/plm/portal/images/icon_5.png" border="0"></a> <a href="javascript:CommonUtil.deleteValue('partNumber');">
                <img src="/plm/portal/images/icon_delete.gif" border="0">
            </a></td>
          </tr>
          <tr>
            <td class="title">부품명</td>
            <td><input id="partName" name="partName" type="text" class="txt_field" style="width: 95%" value=""></td>
            <td class="title">Rev</td>
            <td><input id="partLatestReVision" name="partLatestReVision" type="radio" value="true" checked />최신&nbsp;&nbsp;&nbsp;<input id="partLatestReVision" name="partLatestReVision"
                type="radio" value="false"
              />전체</td>
          </tr>
          <tr>
            <td class="title">상태</td>
            <td><ket:select id="partStateCode" name="partStateCode" className="fm_jmp" style="width: 170px;" lifeCycleState="KET_PART_LC" multiple="multiple" otherHtml=" " /></td>
            <td class="title">개발단계</td>
            <td><select id="spPartDevLevel" name="spPartDevLevel" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
                <option value="PC003">개발</option>
                <option value="PC004">양산</option>
            </select></td>
          </tr>
          <tr>
            <td class="title">작성일</td>
            <td class="tdwhiteL" colspan="3"><input type="text" id="createStartDate" name="createStartDate" class="txt_field" style="width: 70px;" value=""> <img
              src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createStartDate');" style="cursor: hand;"
            > ~ <input type="text" id="createEndDate" name="createEndDate" class="txt_field" style="width: 70px;"> <img src="/plm/portal/images/icon_delete.gif" border="0"
              onclick="javascript:CommonUtil.deleteDateValue('createEndDate');" style="cursor: hand;"
            >
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="checkbox" name="isCreateByme" id="isCreateByme" value="Y">&nbsp;&nbsp;부품 생성자 기준&nbsp;
            </td>
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
          </table> <!-- EJS TreeGrid Start -->
          <div class="content-main">
            <div class="container-fluid">
              <div class="row-fluid">
                <div id="PartListSearchGridDiv" style="WIDTH: 100%; HEIGHT: 100%"></div>
              </div>
            </div>
          </div> <!-- EJS TreeGrid Start -->
        </td>
      </tr>
    </table>
  </div>
</div>
</html>