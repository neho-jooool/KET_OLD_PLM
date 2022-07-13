<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.fc.ReferenceFactory,
                        wt.iba.value.IBAHolder,
                        wt.inf.container.WTContainerRef,
                        wt.lifecycle.LifeCycleHelper,
                        wt.lifecycle.LifeCycleTemplate,
                        wt.lifecycle.PhaseTemplate,
                        wt.lifecycle.State,
                        wt.org.WTUser"%>
<%@page import="e3ps.edm.CADCategory,
                        e3ps.edm.CADManageType,
                        e3ps.edm.CADAppType,
                        e3ps.edm.DevStage,
                        e3ps.edm.beans.EDMHelper,
                        e3ps.edm.util.EDMProperties,
                        e3ps.edm.util.EDMUtil,
                        e3ps.common.code.NumberCode,
                        e3ps.common.code.NumberCodeHelper,
                        e3ps.common.util.CommonUtil,
                        e3ps.common.util.DateUtil,
                        e3ps.common.util.PropertiesUtil,
                        e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.KETStringUtil"%>
<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@ page import="java.util.ArrayList,
         java.util.Hashtable,
         e3ps.bom.common.util.*,
         e3ps.bom.service.KETBOMHeaderQueryBean,
         e3ps.bom.common.iba.IBAUtil,
         e3ps.common.util.StringUtil,
         e3ps.project.beans.MoldProjectHelper,
         e3ps.ecm.beans.EcmSearchHelper,
         e3ps.common.web.PageControl"%>
<%@page import="e3ps.groupware.board.beans.MyTestOption"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");

	String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
	String pagingSize = null;
	
	if( searchCondition != null && !searchCondition.isEmpty() ) {
	    pagingSize = searchCondition.getString("perPage");
	}
	
	if ( pagingSize == null || pagingSize.trim().length() == 0 ) {
	    pagingSize = pagingSizeDefault;
	}
	 
  // 날짜 3개월
  Calendar cal = Calendar.getInstance();
  searchCondition.put("create_end", new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
  cal.add(Calendar.MONTH, -1);
  searchCondition.put("create_start", new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));

  String mode = searchCondition.getString("mode"); // [multi|one](legacy), [m|s] 가능
  boolean multi = ("multi".equalsIgnoreCase(mode) || "m".equalsIgnoreCase(mode)); // default: s(ingle)
  String fncall = searchCondition.getString("fncall");      // Opener 리턴 함수 지정
  String modal = searchCondition.getString("modal");      // Modal 여부 지정
  String fromPage = searchCondition.getString("fromPage");

  //Kogger.debug("@@@@@@@@@@@@@ mode: [" + mode + "](multi=" + multi + ")" + ",  fncall: [" + fncall + "], modal: [" + modal + "], fromPage: [" + fromPage + "]");

  // 검색조건
  
	EDMProperties edmProperties = EDMProperties.getInstance();
	
	WTContainerRef wtContainerRef = EDMUtil.getWTContainerRef(edmProperties.getContainer());
	String edmLC = edmProperties.getEPMDefaultLC();
	LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(edmLC, wtContainerRef);
	Vector stateVec = LifeCycleHelper.service.getPhaseTemplates(lct);
	
	String command = searchCondition.getString("command");
	String number = searchCondition.getString("number");
	String name = searchCondition.getString("name");
	String partNumber = searchCondition.getString("partNumber");
	
	String[] state = searchCondition.getStringArray("state"); // MultiCombo
	String latest = searchCondition.getString("latest");
	
	String[] businessType = searchCondition.getStringArray("businessType"); // MultiCombo
	
	String[] devStage = searchCondition.getStringArray("devStage"); // MultiCombo
	String[] category = searchCondition.getStringArray("category"); // MultiCombo
	String[] cadAppType = searchCondition.getStringArray("cadAppType"); // MultiCombo
	String isDummyFile = searchCondition.getString("isDummyFile");
	
	String createStart = searchCondition.getString("create_start");
	String createEnd = searchCondition.getString("create_end");
	String modifyStart = searchCondition.getString("modify_start");
	String modifyEnd = searchCondition.getString("modify_end");
	
	String creator = searchCondition.getString("creator");
	String modifier = searchCondition.getString("modifier");
	
	String edmCreateDeptName = searchCondition.getString("edmCreateDeptName");
	String edmModifyDeptName = searchCondition.getString("edmModifyDeptName");
	
	String projectNo = searchCondition.getString("projectNo");
	
	if ( latest.length() == 0 ) {
	  latest = "true";
	}
	
	String from = searchCondition.getString("from");
	
	// 공통코드
	Map<String, Object> parameter = new HashMap<String, Object>();
	parameter.put("locale",   messageService.getLocale());
	parameter.put("codeType", "DIVISIONFLAG");
	List<Map<String, Object>> businessTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
	
	

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<base target="_self">
<title><%=messageService.getString("e3ps.message.ket_message", "01262") %><%--도면 검색 팝업--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/jsp/groupware/js/board.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<!-- 자동완성 스크립트 -->
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>

<style type="text/css">
body {
    margin-left: 5px;
    margin-right: 5px;
    margin-bottom: 5px;
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
-->
</style>

<script type="text/javascript" charset="UTF-8">

   function perPage(v) {
    document.forms[0].perPage.value = v;
    
    //search();
    loadEjsGrid();
   }

  // 검색
  function search(){
    var form01 = document.forms[0];
    var form = document.forms[0];
    var nn = form.number.value;

    // 검색조건 미입력 validation
    <%--
    if (form01.number.value == "" && form01.name.value == "" && form01.partNumber.value == ""
    	//&& form01.state.value == "" && form01.cadAppType.value == "" && form01.category.value == ""
        ){
      alert("도면번호, 도면명, 부품번호 셋 중 하나는 필수로 입력해야 합니다.");
      //alert('<%=messageService.getString("e3ps.message.ket_message", "03135") %><%--하나 이상의 검색조건을 입력해 주십시오-- %>');
      return;
    }
    --%>
    var checkTwoCount = 0;
    // 검색 조건 2가지 이상
    var epm_No = $("#number").val();
    var part_No = $("#partNumber").val();
    if( ( epm_No == "" || epm_No.indexOf('*') != -1 ) && ( part_No == "" || part_No.indexOf('*') != -1 ) ){
    
	    if($("#businessType option").index($("#businessType option:selected")) != -1 ){
	        checkTwoCount++;
	    };
	    if($("#devStage option").index($("#devStage option:selected")) != -1 ){
	        checkTwoCount++;
	    };
	    if($("#category option").index($("#category option:selected")) != -1 ){
	        checkTwoCount++;
	    };
	    if($("#cadAppType option").index($("#cadAppType option:selected")) != -1 ){
	        checkTwoCount++;
	    };
	    if($("#state option").index($("#state option:selected")) != -1 ){
	        checkTwoCount++;
	    };
	    if($("#partNumber").val() != "" ){
	        //alert("partNumber:" + $("#partNumber").val());
	        checkTwoCount++;
	    };
	    if($("#number").val() != "" ){
	        //alert("number:" + $("#number").val());
	        checkTwoCount++;
	    };
	    if($("#name").val() != "" ){
	        //alert("name:" + $("#name").val());
	        checkTwoCount++;
	    };
	    if($("#projectNo").val() != "" ){
	        //alert("projectNo:" + $("#projectNo").val());
	        checkTwoCount++;
	    };
	    if($("#creator").val() != "" ){
	        //alert("creator:" + $("#creator").val());
	        checkTwoCount++;
	    };
	    if($("#edmCreateDeptName").val() != "" ){
	        //alert("edmCreateDeptName:" + $("#edmCreateDeptName").val());
	        checkTwoCount++;
	    };
	    if($("#create_start").val() != "" ||  $("#create_end").val() != "" ){
            checkTwoCount++;
        };
        
	    //alert(checkTwoCount);
	    if( checkTwoCount < 2){
	    	alert(" 부품번호, 도면번호 란에\n Full Code를 넣거나\n 검색 조건을 2가지 이상 추가해야 검색 가능 합니다.");
	        return;
	    }
    }
    
    // 부품번호 검색조건 입력 시, 자릿수 체크
    <%--
    if ( form01.number.value != "" ) {
      var numberOnly = form01.number.value.split("*").join("");
      if (numberOnly.length < 3) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00712") % ><%--검색조건은 '*'를 제외하고 3자리 이상입니다--% >");
        return;
      }
    }

    // 부품명 검색조건 입력 시, 자릿수 체크
    if ( form01.name.value != "" ) {
      if (form01.name.value.indexOf("*") == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00711") % ><%--검색조건은 '*'로 시작할 수 없습니다--% >");
        form01.name.value = "";
        return;
      }

      var nameOnly = form01.name.value.split("*").join("");
      if (nameOnly.length < 3) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00712") % ><%--검색조건은 '*'를 제외하고 3자리 이상입니다--% >");
        return;
      }
    } 
    --%>

    /*
    if((form.create_start.value != '') && !onValidCal(form.create_start,false)) {
        alert("'작성일자'를 'YYYY-MM-DD' 형식의 숫자를 입력하시기 바랍니다.");
        return;
    }
    if((form.create_end.value != '') && !onValidCal(form.create_end,false)) {
        alert("'작성일자'를 'YYYY-MM-DD' 형식의 숫자를 입력하시기 바랍니다.");
        return;
    }

    if((form.create_start.value != '') && (form.create_end.value != '') && !onCalcTime('create_start','create_end')) {
        return;
    }
    */
    
    //disabledAllBtn();
    //showProcessing();

    //form01.cmd.value="searchPop";
    //form01.action = 
    //form01.submit();
    
    loadEjsGrid();

  }
  
  function loadEjsGrid(){
	    
	    try{
	       
	       var idx = 0;
	       var D = Grids[idx].Data;
	       var formName = "form01";
	       
	       D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
	       
	       D.Data.Url = "/plm/servlet/e3ps/EDMServlet";
	       D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
	       D.Data.Param.command = "searchGridPagePopup"; // searchGridDataPopup
	      	       
	       //D.Page.Url = "/plm/servlet/e3ps/EDMServlet";
	       //D.Page.Params =  D.Data.Params;
	       //D.Page.Param.command = "searchGridPagePopup";
	       
	       Grids[idx].Reload(D);
	       
	       var S = document.getElementById("Status"+idx);
	       if(S) S.innerHTML="Loading";
	    
	    }catch(e){
	        alert(e.message);
	    }
  }

  // 초기화
  function deleteValue(){
    var form01 = document.forms[0];
    form01.number.value = "";
    form01.name.value = "";
    form01.inputPartNos.value = "";
    // 결과내재검색 체크해제
    $("input:checkbox[id=searchInSearch]").attr("checked", false);

  }

   // 검색조건 입력 후 검색 엔터처리
   function checkKey(){
       if(window.event.keyCode == 13){
           search();
       }else{
           return;
       }
   }

  function selectEpm() {
    var G = Grids[0];
    var arr = new Array();
    var subArr = new Array();
    var idx = 0;

    if( G != null ){
        var R = G.GetSelRows();

        if( R.length == 0 ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01596") %><%--부품을 선택한 후 확인을 눌러주세요!--%>');
            return;
        }

        for ( var i=0; i<R.length; i++ ) {
             subArr = new Array();

             subArr[0] = R[i].Oid;//oid
             subArr[1] = R[i].DrawingNo;//number
             subArr[2] = R[i].DrawingName;//name
             subArr[3] = R[i].Ver;//version
             subArr[4] = R[i].CADType;//type
             subArr[5] = R[i].Status;//
             subArr[6] = R[i].File;
             subArr[7] = R[i].Dummy;
             subArr[8] = R[i].OidMaster;//wtpartmaster oid
             subArr[9] = R[i].Creator;
             subArr[10] = R[i].CreateDate;
             subArr[11] = R[i].Security; // S2 대내비 S1 대외비
             subArr[12] = R[i].category;
             
             //alert(subArr[12]);

             arr.push(subArr);
        }
    }

    if( document.forms[0].modal.value == 'Y' ){
        window.returnValue = arr;
        window.close();
      } else {
        <%if (fncall != null && fncall.equals("")) {  // 리턴 함수가 지정되어있지 않은 경우 기본 Opener 함수 호출%>
          opener.selectedPart(arr);
        <%}else {                    // 리턴 함수가 지정된 경우 해당 Opener 함수 호출%>
          opener.<%=fncall%>(arr);
        <%}%>
        window.close();
      }
  }

  // 전체 체크박스 선택
  function checkAll(obj)
  {
    var form01 = document.forms[0];
    if (form01.checkedRecord) {
      // 있는데 1개일 경우
      if (form01.checkedRecord.length == undefined) {
        if (obj.checked) {
          form01.checkedRecord.checked = true;
        } else {
          form01.checkedRecord.checked = false;
        }
      // 2개 이상일 경우
      } else {
        for (i=0; i<form01.checkedRecord.length; i++) {
          if (obj.checked) {
            form01.checkedRecord[i].checked = true;
          } else {
            form01.checkedRecord[i].checked = false;
          }
        }
      }
    }
  }

  //처리중 화면 전환
  /*function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
  }*/

  <%--
  function doViewEPM(_oid) {

    var vparam = "";
    vparam += "frompage=SearchPage";
    vparam += "&oid="+_oid;

    document.forms[0].method = "post";
    document.forms[0].target = "_self";
    document.forms[0].action = "/plm/jsp/edm/ViewEPMDocument.jsp?"+vparam;;
    document.forms[0].submit();
    
  }
  --%>

  //==  [Start] 부품검색 팝업  == //
  function selectPartNo(targetId, mode){ // mode: m | s
	  _callBackFuncTargetId = targetId;
	  if( typeof mode != 'undefined' && ('m' == mode || 'M' == mode )){
	      showProcessing();
	      SearchUtil.selectPart("callBackFuncPartPopup");
	  }else{
	      showProcessing();
	      SearchUtil.selectOnePart("callBackFuncPartPopup");
	  }
  }
  
  var _callBackFuncTargetId;
  function callBackFuncPartPopup(selectedPartAry){
      if ( typeof selectedPartAry != "undefined" && selectedPartAry.length > 0) {
          var isAppend = "N";
          acceptPartNo(_callBackFuncTargetId, selectedPartAry, isAppend);
      }
  }

  function acceptPartNo(targetId, arrObj, isAppend) {
      var partData = new Array();
      var partMasterOid = new Array();
      for ( var i=0; i < arrObj.length; i++ ) {
          // [0] - wtpart oid       // [1] - part number    // [2] - part name
          // [3] - part version     // [4] - part type      // [5] - die number
          // [6] - die name         // [7] - die cnt        // [8] - wtpartmaster oid

          var infoArr = arrObj[i];
          partData[i] = infoArr[1];
          partMasterOid[i] = infoArr[8];
      }

      var tmpNo  = new Array();
      var tmpMasterOid = new Array();
      if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
          // ID 중복 제거
          tmpNo = $.merge($("#"+targetId).val().split(", "), partData);
          tmpNo = $.unique(tmpNo.sort());

          tmpMasterOid = $.merge($("#"+targetId+"MasterOid").val().split(", "), partMasterOid);
          tmpMasterOid = $.unique(tmpMasterOid.sort());
      }
      else {
          tmpNo  = partData.sort();
          tmpMasterOid = partMasterOid.sort();
      }

      $("#"+targetId).val(tmpNo.join(", "));
      $("#"+targetId+"MasterOid").val(tmpMasterOid.join(", "));
  }

  function clearPartNo(targetId) {
      $("#" + targetId).val("");
      $("#" + targetId+"MasterOid").val("");
  }
  //==  [End] 부품검색 팝업  == //

 var projectIdTemp = "";
// 프로젝트 검색 팝업 Open
function popupProject(targetId) {
  projectIdTemp = targetId;
  var url = "/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P&modal=&mode=m&fncall=setProject";
  var opts = "toolbar=0, location=0, directory=0, status=1, menubar=0, scrollbars=1, resizable=0";
    
  getOpenWindow2(url,"searchEpmDocument", '1024', '768', opts);
}
function setProject(arrObj) {
	var targetId = projectIdTemp;
	isAppend = "Y";
    var projectNoData = new Array();
    var projectOidData = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] -  oid       // [1] - project number
        var infoArr = arrObj[i];
        projectNoData[i] = infoArr[1];
        projectOidData[i] = infoArr[0];
    }

    var tmpNo = new Array();
    var tmpOid = new Array();
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpNo = $.merge($("#"+targetId).val().split(", "), projectNoData);
        tmpNo = $.unique(tmpNo.sort());

        tmpOid = $.merge($("#"+targetId+"Oid").val().split(", "), projectOidData);
        tmpOid = $.unique(tmpOid.sort());
    }
    else {
        tmpNo = projectNoData.sort();
        tmpOid = projectOidData.sort();
    }

    $("#"+targetId).val(tmpNo.join(", "));
    $("#"+targetId+"Oid").val(tmpOid.join(", "));
}
function deleteProject() {
    document.forms[0].projectNo.value = "";
    document.forms[0].projectNoOid.value = "";
}


function addDepartment() {
    var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?mode=m&invokeMethod=setDepartMent";
    var opts = "toolbar=0, location=0, directory=0, status=1, menubar=0, scrollbars=1, resizable=0";
    getOpenWindow2(url,"searchEpmDocumentSetDept", '430', '465', opts);
}

function setDepartMent(arrObj){
    var departOid = "";
    var departName = "";
    for(var i= 0 ; i < arrObj.length; i++){
        departOid += arrObj[i].OID+",";
        departName += arrObj[i].NAME+",";
    }
    deptMerge(departOid, departName, "edmCreateDept", "edmCreateDeptName");
}

function deptMerge(deptOid, deptName, targetId, targetName) {
    if ( $("#"+targetId).val() == "" ) {
        $("#"+targetId).val( deptOid );
        $("#"+targetName).val( deptName );
    }
    else {
        var deptOidArr = $("#"+targetId).val().split(", ");
        var deptNameArr = $("#"+targetName).val().split(", ");
        // 선택된 부서 push
        deptOidArr.push(deptOid);
        deptNameArr.push(deptName);
        // 중복 부서 정리
        deptOidArr = $.unique(deptOidArr.sort());
        deptNameArr = $.unique(deptNameArr.sort());

        $("#"+targetId).val( deptOidArr.join(", ") );
        $("#"+targetName).val( deptNameArr.join(", ") );
    }
}
//==  [Start] 부서 검색  == //
function doDeleteDepartment() {
    document.forms[0].edmCreateDept.value = "";
    document.forms[0].edmCreateDeptName.value = "";
}
  //Jquery
  $(document).ready(function(){
      // Enter 검색
      $("form[name=form01]").keypress(function(e) {
          if ( e.which == 13 ) {
              search();
              return false;
          }
      });
  });

  window.onload = function() {
    
  }
  
  function helpTest(){
	  var    text = " EM41~EM49,JB51~JB59,MG60~MG68,ST71~ST79, \n"; //,PG88~PG89
	  text = text + " R40,R50,R60, \n";
	  text = text + " KP,KR, \n"; //,KH ,KD4,K,KW,KS,
	  text = text + " WH,WH31~WH32, \n\n"; // WH33
	  
	  //text = text + " 제품 : 610056-3, 477016-09, KL10024-00, H615390-2, S200406, R103081, 310464-AS \n";
	  //text = text + " Die No : 1T389000 22089001 10860C00 22845A00 LP020000 1Y007000 10929000 1A171000 2T220000 \n";
	  //text = text + " 금형 부품 : 11141000-P52, 11183000-STD27, 10861B00-P18-7 11133-D09-2 21906A00-019 1T389000-D09 \n";
	  alert(text);
  }
  
  function helpTestPart(){
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
  
  function reset() {
	  
	  $("#partNumber").val("");
	  $("#number").val("");
	  $("#name").val("");
	  $("#projectNo").val("");
	  //$("#creator").val("");
	  //$("#creatorName").val("");
	  //$("#edmCreateDeptName").val("");
	  //$("#create_start").val("");
	  //$("#create_end").val("");
	  //$("#latest").chacked = false;
	  $("#isDummyFile").chacked = false;

	  // MultiCombo
	  $("#businessType").multiselect("uncheckAll");
	  $("#devStage").multiselect("uncheckAll");
	  $("#category").multiselect("uncheckAll");
	  $("#cadAppType").multiselect("uncheckAll");
	  $("#state").multiselect("uncheckAll");

	  //결과내재검색 체크해제
	  $("input:checkbox[id=searchInSearch]").attr("checked", false);
  }

  //Jquery
  $(document).ready(function(){
	  // Enter 검색
	  $("form[name=form01]").keypress(function(e) {
	      if ( e.which == 13 ) {
	          search();
	          return false;
	      }
	  });

	  $("#businessType").multiselect({
	      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	  });
	  $("#devStage").multiselect({
	      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	  });
	  $("#category").multiselect({
	      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	  });
	  $("#cadAppType").multiselect({
	      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	  });
	  $("#state").multiselect({
	      noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	      checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	      uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	  });
	  
	  //부품 suggest
      SuggestUtil.bind('PARTNO', 'partNumber');
      SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
      SuggestUtil.bind('EPMNO', 'number');
      SuggestUtil.bind('USER', 'creatorName', 'creator');
      SuggestUtil.bind('DEPARTMENT', 'edmCreateDeptName', 'edmCreateDept');
      
      CalendarUtil.dateInputFormat('create_start','create_end'); 
	
  });
  

  function downCadFile( url ){
      var name = "";
      defaultWidth = 500;
      defaultHeight = 500;
      var opts = ",scrollbars=1,resizable=0";
      getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
  }

</script>
</head>

<body class="body" >
<form name="form01" method="post">
<input type="hidden" name="cmd">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="fncall" value="<%=fncall%>">
<input type="hidden" name="modal" value="<%=modal%>">
<input type="hidden" name="fromPage" value="<%=fromPage%>">
                                    
        <table style="width: 100%; height: 100%;">
            <tr>
                <td height="50" valign="top">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01262") %><%--부품 검색--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <table style="width:1000px; height: 100%;">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top">
                                <table style="width: 100%;">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <table>
                                                <tr>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:reset();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td align="center">
                                                        <table>
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:selectEpm();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><%--확인--%></a></td>
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
                                <table style="width: 100%;">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>										
								<table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
					                <tr>
					                    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
					                    <td  class="tdwhiteL"><input type="text" name="number" class="txt_field"  style="width:98%" value="<%=number%>" id="number"></td>
					                    <td style="width:110px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
					                    <td  class="tdwhiteL0"><input type="text" name="name" class="txt_field"  style="width:98%" value="<%=name%>" id="name"></td>
					                </tr>
					                <tr>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></td>
                                        <td class="tdwhiteL">
                                            <select id="cadAppType" name="cadAppType" class="fm_jmp" style="width:270px" onFocus='this.initialSelect = this.selectedIndex;' multiple="multiple"> <!-- onChange='this.selectedIndex = this.initialSelect;'>  -->
                                                <%
                                                    CADAppType[] cadAppTypeArr = CADAppType.getCADAppTypeSet();
                                                    for(int i = 0; i < cadAppTypeArr.length; i++) {
                                                %>
                                                        <OPTION VALUE="<%=cadAppTypeArr[i].toString()%>" <%=(KETParamMapUtil.contains(cadAppType, cadAppTypeArr[i].toString())) ? " selected" : "" %>><%=cadAppTypeArr[i].getDisplay(messageService.getLocale())%></OPTION>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </td>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                                        <td class="tdwhiteL0">
                                            <select id="state" name="state" class="fm_jmp" style="width:270px" multiple="multiple">
                                                <%
                                                    PhaseTemplate pt = null;
                                                    State lcState = null;
                                                    for(int i = 0; i < stateVec.size(); i++) {
                                                        pt = (PhaseTemplate)stateVec.get(i);
                                                        lcState = pt.getPhaseState();
                                                %>
                                                        <option value="<%=lcState.toString()%>" <%=(KETParamMapUtil.contains(state, lcState.toString())) ? " selected" : "" %>><%=lcState.getDisplay(messageService.getLocale())%></option>
                                                <%  }
                                                %>
                                            </select>
                                        </td>
                                    </tr>
					                <tr>
					                    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
					                    <td  class="tdwhiteL">
					                        <input type="hidden" id="partNumberMasterOid" name="partNumberMasterOid" value="<%=searchCondition.getString("partNumberMasterOid")%>">
					                        <input type="text" id="partNumber" name="partNumber" class="txt_field" style="width:80%" value="<%=partNumber%>" >
					                        <a href="javascript:;" onClick="javascript:selectPartNo('partNumber', 'm');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
					                        &nbsp;<a href="javascript:;" onClick="javascript:clearPartNo('partNumber');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
					                    </td>
					                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                                        <td class="tdwhiteL0">
                                            <input type="hidden" id="projectNoOid" name="projectNoOid" value="<%=searchCondition.getString("projectNoOid")%>">
                                            <input type="text" id="projectNo" name="projectNo" class="txt_field" style="width:80%" value="<%=projectNo%>">
                                            <a href="javascript:;" onClick="javascript:popupProject('projectNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                                            &nbsp;<a href="javascript:;" onClick="javascript:deleteProject();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                        </td>
					                </tr>
					                <tr>
					                    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
                                        <td  class="tdwhiteL">
                                            <select id="businessType" name="businessType" class="fm_jmp" style="width:270px" multiple="multiple">
                                            <%
                                            for ( int i=0; i<businessTypeNumCode.size(); i++ ) {
                                            %>
                                                <option value="<%=businessTypeNumCode.get(i).get("oid") %>" <%=(KETParamMapUtil.contains(businessType, businessTypeNumCode.get(i).get("oid")) ) ? " selected" : "" %>><%=businessTypeNumCode.get(i).get("value")%></option>
                                            <%
                                            }
                                            %>
                                            </select>
                                        </td>
                                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
                                        <td class="tdwhiteL0">
                                            <select id="category" name="category" class="fm_jmp" style="width:270px" onFocus='this.initialSelect = this.selectedIndex;' multiple="multiple"> <!-- onChange='this.selectedIndex = this.initialSelect;'>  -->
                                                <%
                                                    CADManageType[] cmtArr = CADManageType.getCADManageTypeSet();
                                                    for(int i = 0; i < cmtArr.length; i++) {
                                                        CADManageType cmt = cmtArr[i];
                                                %>
                                                        <OPTION VALUE="">-----<%=cmt.getDisplay(messageService.getLocale())%>-----</OPTION>
                                                <%
                                                        CADCategory[] catArr = edmProperties.getCADCategorySet(cmt.toString());
                                                        for(int k = 0; k < catArr.length; k++) {
                                                %>
                                                            <OPTION VALUE="<%=catArr[k].toString()%>" <%=(KETParamMapUtil.contains(category, catArr[k].toString())) ? " selected" : "" %>><%=catArr[k].getDisplay(messageService.getLocale())%></OPTION>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </td>
					                </tr>
					                <tr>
					                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
					                    <td class="tdwhiteL">
					                        <input type="hidden" id="creator" name="creator" value="<%=searchCondition.getString("creator")%>">
					                        <input type="text" id="creatorName" name="creatorName"  class="txt_field"  style="width:80%;" value="<%=searchCondition.getString("creatorName")%>">
					                        <a href="javascript:;" onClick="javascript:selectUserOid('creator','creatorName');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
					                        &nbsp;<a href="javascript:;" onClick="javascript:CommonUtil.deleteValue('creator','creatorName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
					                    </td>
					                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
					                    <td class="tdwhiteL0">
					                        <input type="hidden" id="edmCreateDept" name="edmCreateDept" value="<%=searchCondition.getString("edmCreateDept")%>">
					                        <input type="text" id="edmCreateDeptName" name="edmCreateDeptName" class="txt_field"  style="width:80%" value="<%=searchCondition.getString("edmCreateDeptName")%>">
					                        <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
					                        &nbsp;<a href="#" onfocus="this.blur();" onClick="javascript:doDeleteDepartment();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
					                    </td>
					                </tr>
					                <tr>
					                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
					                    <td class="tdwhiteL">
					                        <input type="text" id="create_start" name="create_start" class="txt_field" style="cursor: hand;" value="<%=createStart%>">
					                        &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('create_start');">
					                        &nbsp;~&nbsp;
					                        <input type="text" id="create_end" name="create_end" class="txt_field" style="cursor: hand;" value="<%=createEnd%>">
					                        &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('create_end');" >
					                    </td>
					                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01264") %><%--도면구분--%></td>
                                        <td class="tdwhiteL0">
                                            <select id="devStage" name="devStage" class="fm_jmp" style="width:270px" onFocus='this.initialSelect = this.selectedIndex;' multiple="multiple"> <!-- onChange='this.selectedIndex = this.initialSelect;'>  -->
                                                <%
                                                    DevStage[] dsArr = DevStage.getDevStageSet();
                                                    for(int i = 0; i < dsArr.length; i++) {
                                                %>
                                                        <OPTION VALUE="<%=dsArr[i].toString()%>" <%=(KETParamMapUtil.contains(devStage, dsArr[i].toString())) ? " selected" : "" %>><%=dsArr[i].getDisplay(messageService.getLocale())%></OPTION>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </td>
					                </tr>
					                <tr>
					                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
					                    <td class="tdwhiteL"><input name="latest" type="radio" class="Checkbox" value="true" <%if("true".equals(latest)){%>checked<%}%>><%=messageService.getString("e3ps.message.ket_message", "02839") %><%--최신--%><input name="latest" type="radio" class="Checkbox" value="false" <%if("false".equals(latest)){%>checked<%}%>><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> </td>
					                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00174") %><%--Dummy File 여부--%></td>
					                    <td class="tdwhiteL0">
					                        <input name="isDummyFile" type="radio" class="Checkbox" value="" <%if(isDummyFile.equals("")){%>checked<%}%>><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>
					                        <!--
					                        <input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_YES%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_YES)){%>checked<%}%>>Y
					                        <input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_NO%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_NO)){%>checked<%}%>>N
					                      r-->
					                    </td>
					                </tr>
					            </table>
					            <br>
								<!-- 
								 -->
								<!-- EJS TreeGrid Start -->
					            <div class="content-main">
					                <div class="container-fluid">
					                    <div class="row-fluid">
					                        <div style="WIDTH:100%;HEIGHT:100%">
					                            <bdo 
					                                <%--
					                                Debug='3' 
					                                
					                                Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp"
                                                    Page_Format="Internal"
                                                    Page_Data="TGData"
                                                    Page_Method="POST"
					                                --%>
					                                Debug="1"
					                                AlertError="0"
					                                
					                                Layout_Url="/plm/jsp/edm/SearchEpmPopupGridLayout.jsp"
					                                Layout_Param_Pagingsize="<%=pagingSize %>"
					                                Layout_Param_Multi="<%=multi %>"
					
					                                Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
					                                Data_Format="Internal"
					                                Data_Data="TGData"
					                                
					                                Data_Timeout="0"
					                                
					                                Data_Method="POST"
					                                
					                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Drawing_List"
					                            ></bdo>
					                        </div>
					                    </div>
					                </div>
					            </div>
					            <!-- EJS TreeGrid End -->
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
