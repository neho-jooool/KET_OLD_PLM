<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.web.ParamUtil" %>
<%@page import="e3ps.ecm.dao.MoldChangeOrderDao" %>

<%@ page import="java.util.*
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.project.ProjectIssue
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.QueryResult
              ,wt.fc.PersistenceHelper
              ,wt.query.*
              ,wt.content.ContentHolder
              ,wt.content.ContentHelper
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>
              
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>
              
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="ketMoldChangeRequestVO" class="e3ps.ecm.entity.KETMoldChangeRequestVO" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
//코드목록 조회(미적용:정렬순서에 문제가 있음.)
MoldChangeOrderDao dao = new MoldChangeOrderDao();
KETCodeDetailVO ketCodeDetailVO = null;
KETCodeVO divisionFlagCodeVO = dao.getCodeList("DIVISIONFLAG", "ko");//사업부구분
KETCodeVO changeTypeCodeVO = dao.getCodeList("CHANGETYPE", messageService.getLocale().toString());

String userGroup = KETObjectUtil.getCurrentUserGroup();
boolean isCreate = true;

int size = 0;
int i = 0;
int idx = 0;
String chkChangeType[] = new String[12];//설계변경유형
String codeChangeType[] = new String[12];
String rdoDevYn[] = {"checked", "", ""};//개발/양산구분
String rdoDivisionFlag[] = {"selected", ""};//사업부구분
String rdoRequestType[] = {"checked", "", ""};//의뢰구분
String rdoProcessType[] = {"checked", ""};//처리구분
String cmbVendorFlag[] = {"selected", ""};
if(ketMoldChangeRequestVO.getTotalCount() > 0) {

    isCreate = false;
    //설계변경유형
    ArrayList arrChangeType = KETStringUtil.getToken(ketMoldChangeRequestVO.getKetMoldChangeRequest().getChangeType(), "|");
    for(i=0; i<12; i++) {
        chkChangeType[i] = "";
    if(i+1 <10)
    {
        codeChangeType[i] = "TYPE_0" + (i+1);
    }
    else
    {
        codeChangeType[i] = "TYPE_" + (i+1);
    }
    }
    size = arrChangeType.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrChangeType.get(i), codeChangeType);
        if(idx >= 0) {
            chkChangeType[idx] = "checked";
        }
    }
    //개발/양산구분
    if("P".equals(ketMoldChangeRequestVO.getKetMoldChangeRequest().getDevYn())) {//양산
        rdoDevYn[0] = "";
        rdoDevYn[1] = "checked";
        rdoDevYn[2] = "";
    } else if("N".equals(ketMoldChangeRequestVO.getKetMoldChangeRequest().getDevYn())) {//미지정
        rdoDevYn[0] = "";
        rdoDevYn[1] = "";
        rdoDevYn[2] = "checked";
    }
    //사업부구분
    if("E".equals(ketMoldChangeRequestVO.getKetMoldChangeRequest().getDivisionFlag())) {
        rdoDivisionFlag[0] = "";
        rdoDivisionFlag[1] = "selected";
    }
    //의뢰구분
    if("REQ_2".equals(ketMoldChangeRequestVO.getKetMoldChangeRequest().getRequestType())) {//금형설계도면처리
        rdoRequestType[0] = "";
        rdoRequestType[1] = "checked";
        rdoRequestType[2] = "";
    } else if("REQ_3".equals(ketMoldChangeRequestVO.getKetMoldChangeRequest().getRequestType())) {//기타
        rdoRequestType[0] = "";
        rdoRequestType[1] = "";
        rdoRequestType[2] = "checked";
    }
    //처리구분
    if("PROC_2".equals(ketMoldChangeRequestVO.getKetMoldChangeRequest().getProcessType())) {
        rdoProcessType[0] = "";
        rdoProcessType[1] = "checked";
    }
    if("o".equals(ketMoldChangeRequestVO.getKetMoldChangeRequest().getVendorFlag())) {
        cmbVendorFlag[0] = "";
        cmbVendorFlag[1] = "selected";
    }
}


//제품, 금형 ECR 확장팩
KETChangeRequestExpansion expansion = null;
// ECR 로 찾는다.
KETMoldChangeRequest moldEcr = ketMoldChangeRequestVO.getKetMoldChangeRequest();
if(moldEcr != null) {
	QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
	spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(moldEcr)), new int[] { 0 });
	QueryResult result = PersistenceHelper.manager.find(spec);
	if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
	  expansion = (KETChangeRequestExpansion) result.nextElement();
	}
}
	
String reviewRequestDate = "";

String subjectDisplayName = "";
String subjectOid = "";
String subjectCode = "";
String chargeDisplayName = "";
String chargeOid = "";
String chargeName = "";

String webEditor = "";
String webEditorText = "";

String webEditor1 = "";
String webEditorText1 = "";
  
if (PersistenceHelper.isPersistent(expansion)) {
    
    // 검토요청기한
    reviewRequestDate = DateUtil.getTimeFormat(expansion.getReviewRequestDate(), "yyyy-MM-dd"); 
    
    // 주관부서
    Department subject = expansion.getSubject();
    subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
    subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
    subjectCode = StringUtils.stripToEmpty(expansion.getSubjectCode());
    
    // 담당자
    WTUser charge = expansion.getCharge();
    chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
    chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
    chargeName = StringUtils.stripToEmpty(expansion.getChargeName());
    
    // 현상
    webEditor = (String) expansion.getWebEditor();
    webEditorText = (String) expansion.getWebEditorText();
     
    // 문제점 및 요구사항
    webEditor1 = (String) expansion.getWebEditor1();
    webEditorText1 = (String) expansion.getWebEditorText1();

}


//검토요청기한이 없을 경우 완료요청일을 보여준다.
String completeReqDate = ketMoldChangeRequestVO.getCompleteReqDateFormat();
if((reviewRequestDate == null || reviewRequestDate.equals("")) && (completeReqDate != null && !completeReqDate.equals(""))) {
 reviewRequestDate = completeReqDate;
}


%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01035") %><%--금형ECR 등록/수정--%></title>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
}

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.headerLock2 {
  position: relative;
  top:expression(document.getElementById("div_scroll2").scrollTop);
  z-index:99;
 }

 .headerLock3 {
  position: relative;
  top:expression(document.getElementById("div_scroll3").scrollTop);
  z-index:99;
 }
-->
</style>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>

<!-- script language=JavaScript src="/plm/jsp/ecm/CreateMoldEcr.js"></script -->
<script language="javascript">

$(document).ready(function(){
    CalendarUtil.dateInputFormat('reviewRequestDate');
    CalendarUtil.dateInputFormat('completeReqDate');
    

    //SuggestUtil.bind('DEPARTMENT', 'subjectDisplayName', 'subjectOid');
    SuggestUtil.multiBind('DEPTUSER', 'subjectDisplayName', 'subjectOid', 'chargeDisplayName', 'chargeOid');
    SuggestUtil.multiBind('USERDEPT', 'chargeDisplayName', 'chargeOid', 'subjectDisplayName', 'subjectOid');
    //SuggestUtil.multiBindCallBackFunc('USERDEPT', 'chargeDisplayName', 'chargeOid', deptCallBackFunc); 콜백 함수로 OBJECT 리턴
    //SuggestUtil.bind('USER', 'chargeDisplayName', 'chargeOid');
    
    
    SuggestUtil.bind('DIENO', 'projectNo', 'projectOid', 'projectName');
});

//프로젝트 선택시 관련 정보를 물고와 정의된 엘레멘트에 set 한다.
$(document).on("change", "[name=projectOid]", function() {
 var oid = $(this).val();
 if(oid == null || oid == '') return;
 
 // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
 lfn_setInfosRelatedProject(oid);
});

//ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
function lfn_setInfosRelatedProject(oid) {
 if(oid == null || oid == '') return;
 
 showProcessing();
 $.ajax({
     type: 'get',
     url: '/plm/ext/project/program/getProject.do',
     data: {
         projectOid : oid
     },
     success: function (data) {
         
         $("input[name='projectOid']").val(data.projectOid);
         $("input[name='projectNo']").val(data.projectNo);
         $("input[name='projectName']").val(data.projectName);
         

         // 단계구분이 선택되어 있을 경우
         if(($("input[name='rdoDevYn']").is(":checked"))) {
           // 생산처
           searchProjectVendor();
         }
         
           
         
         // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
         if(data.projectInfos4ECM != null) {
             
             try {
                 // 제품정보를 위한 선처리
                 var arrParts = null;
                 // 프로젝트 상태
                 var projectState = "";
                 
                 var projectInfos4ECM = data.projectInfos4ECM;
                 var arrPInfos = projectInfos4ECM.split("↕");
                 var arrPInfosLength = (arrPInfos != null) ? arrPInfos.length : 0;
                 for(var l=0; l < arrPInfosLength; l++) {
                     
                     var arrPartInfo13 = null;
                     
                     var arrP = arrPInfos[l].split("↔");
                     var arrPLength = (arrP != null) ? arrP.length : 0;
                
                     // 프로젝트 상태
                     if(l == 0) { 
                         projectState = arrP[0];
                         
                     }
                     // 개발단계 = 단계구분
                     else if(l == 1 && arrPLength == 3) { 
                         var devYnOid = arrP[0];
                         var devYnName = arrP[1];
                         var devYnCode = arrP[2];
                         
                         /* 
                         Process
                         PC001 Proto 
                         PC002 Pilot 
                         PC003 T-CAR 
                         
                              개발/양산구분
                         PROTO PROTO 
                         PILOT PILOT 
                         TCAR T-CAR 
                         PRODUCTION 양산 
                         */
                         var checkDevYnCode = "";
                         if(devYnCode == 'PC001') {
                             checkDevYnCode = "D";
                         } else if(devYnCode == 'PC002') {
                             checkDevYnCode = "D";
                         } else if(devYnCode == 'PC003') {
                             checkDevYnCode = "D";
                         } else {
                        	 checkDevYnCode = "D";
                         }
                         
                         if(projectState == 'COMPLETED') {
                             checkDevYnCode = "P";
                         }
                         $("input:radio[name='rdoDevYn']:radio[value='"+ checkDevYnCode +"']").prop('checked',true);
                     } 
                     // 대표 차종
                     else if(l == 2 && arrPLength == 3) {
                         var carTypeOid = arrP[0];
                         var carTypeName = arrP[1];
                         var carTypeCode = arrP[2];
                         
                         $("input[name='carTypeName']").val(carTypeName);
                         $("input[name='carTypeCode']").val(carTypeOid);
                     } 
                     // 제품정보 
                     else if(l >= 3 && arrPLength == 4) {
                         var relPartOid = arrP[0];
                         var pNum = arrP[1];
                         var pName = arrP[2];
                         var relPartVersion = arrP[3];
                         
                         arrPartInfo13 = [relPartOid, pNum, pName, relPartVersion];
                         //alert('arrPartInfo13 is '+ arrPartInfo13);
                     }
                 
                     
                     if(arrPartInfo13 != null) {
                         if(arrParts == null) arrParts = new Array();
                         arrParts[arrParts.length] = arrPartInfo13;
                         //alert('arrParts['+ (arrParts.length - 1) +'] is '+ arrParts[ (arrParts.length - 1) ]);
                     }
                     
                 }
                 
                 // 제품정보를 위한 후처리
                 selectedPart(arrParts);
                 
             } catch(e) { alert(e); }
             hideProcessing();
         }
                         
     },
     fail : function(){
         hideProcessing();
     }
             
 });
 hideProcessing();
 
}

/**
 * @(#)  createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//초기화면세팅
function onLoad() {
  var form = document.forms[0];
  /* 
  var stat = form.chkChangeType[11].checked;
  var objName = "otherChangeDesc";
  setEtcValueStatus(objName, stat);
   */
  disable( form.requestType.value );
  
  
  var strHTMLCode = document.forms[0].webEditor.value;
  // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
  // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
  // 세번째 매개변수 => 이노디터 번호
  fnSetEditorHTMLCode(strHTMLCode, false, 0);

  var strHTMLCode1 = document.forms[0].webEditor1.value;
  // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
  // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
  // 세번째 매개변수 => 이노디터 번호
  fnSetEditorHTMLCode(strHTMLCode1, false, 1);

}

//기타입력항목 입력상태 처리
function setEtcValueStatus(objName, stat) {
  var obj = eval("document.forms[0]." + objName);
  if(stat) {
    obj.disabled = false;
  } else {
    obj.disabled = true;
  }
}

//프로젝트상세조회 팝업
function viewProjectPopup(projectNo) {
  alert("프로젝트상세조회:" + projectNo);
}

//프로젝트상세조회 팝업
/*
function popupProject() {
  var form = document.forms[0];
  var devflag;
  //개발/양산구분
  if(form.rdoDevYn[0].checked) {
    devflag = form.rdoDevYn[0].value;
  } else {
    devflag = form.rdoDevYn[1].value;
  }
//  var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&dev_flag="+devflag+"&status=P&type=P";
  var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=M";
  openWindow(url,"","725","500","status=0,scrollbars=no,resizable=no");
}
*/
function popupProject()
{
  SearchUtil.selectOneProject('setProject','status=P&type=M&modal=Y');
}

function setProject(objArr) {
  if(objArr == null || objArr.length == 0) {
    return;
  }

  var trArr;
  var str = "";
  var form = document.forms[0];
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    
    /* 
    form.projectOid.value = trArr[0];
    form.projectNo.value = trArr[1];
    form.projectName.value= trArr[2];
    */
    
    // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
    lfn_setInfosRelatedProject(trArr[0]);

    
  }
  
  hideProcessing();
}


//개발단계인 경우 관련 프로젝트가 선택되면 생산처를 조회한다.
function searchProjectVendor() {
  var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
  var form = document.forms[0];
  form.cmd.value = "searchProjectVendor";
  form.target = "download";
  form.action = url;
  form.method = "post";
  disabledAllBtn();
  showProcessing();
  form.submit();
}

//프로젝트상세조회 팝업
function clearProject() {
  var form = document.forms[0];
  form.projectOid.value = "";
  form.projectNo.value = "";
  form.projectName.value = "";
}


//처리중 화면 전환
/*
function hideProcessing() {
  var div1 = document.getElementById('div1');
  var div2 = document.getElementById('div2');
  div1.style.display = "none";
  div2.style.display = "none";
}
*/

//자료 저장
function doSave(){
  var url = "/plm/servlet/e3ps/MoldEcrServlet";
  var form = document.forms[0];

  //개발/양산구분
  if(form.rdoDevYn[0].checked) {
    form.devYn.value = form.rdoDevYn[0].value;
  } else {
    form.devYn.value = form.rdoDevYn[1].value;
  }

  //설계변경 유형
  var cnt = 0;
  form.changeType.value = "";
  for(var i=0; i<form.chkChangeType.length; i++) {
    if(form.chkChangeType[i].checked) {
      cnt++;
      if(cnt < 2) {
        form.changeType.value = form.chkChangeType[i].value;
      } else {
        form.changeType.value += "|" + form.chkChangeType[i].value;
      }
    }
  }
  
  /* 
  if(form.changeType.value.indexOf("11") < 0) {
    form.otherChangeDesc.value = "";
  }
  */
  
  if(checkValidate()){
    if(form.oid.value == "") {
      form.cmd.value = "create";
    } else {
      form.cmd.value = "update";
    }

      
    // 이노디터 처리
    $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
    $('[name=webEditorText]').val(fnGetEditorText(0));

    // 이노디터 처리
    $('[name=webEditor1]').val(fnGetEditorHTMLCode(false, 1));
    $('[name=webEditorText1]').val(fnGetEditorText(1));

    
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.encoding = 'multipart/form-data';

    disabledAllBtn();
    showProcessing();
    form.submit();
  }
}

//연계부품 검색 팝업 호출
function popupRelPart() {
  var url="/plm/ext/part/base/listPartPopup.do?mode=multi&pType=D&modal=N";
  openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
}

function isDuplicatePart( partOid )
{
  var isDuplicatePart = false;

  var partList = document.getElementsByName("relPartOid");
  for( var cnt=0; cnt < partList.length; cnt++ )
  {
    if( partOid == partList[cnt].value )
    {
      isDuplicatePart = true;
    }
  }

  return isDuplicatePart;
}

//연계부품 라인추가(projectIssueCreate.jsp 참조)
function selectedPart(objArr) {
  if(objArr == null || objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relPartTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    if( !isDuplicatePart( trArr[0]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.height="27";

      //선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "20";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<div style='display:none'><input type='text' name='changeReqComment' value=''></div>"
          
                      + "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                      + "<div style=\"display:none;\"><input name='chkSelectRelPart' type='checkbox' value=''></div>"
                      ;

      //Die No
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "120";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='relPartLinkOid' value=''>";
      newTd.innerHTML = str;

      //Part Name
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      str = "";
      str +="<font title=\""+trArr[2]+"\">";
      str += "<div class='ellipsis' style='width:217;'><nobr>";
      str += trArr[2] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //Rev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "50";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = ((typeof trArr[12] != 'undefined') ? trArr[12] : trArr[3]) + "&nbsp;"; // Display Revision

      /*
      //요청내용
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "200";
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<input type='text' name='changeReqComment' class='txt_field' style='width:190'>";
      */
    }
  }
}

//(공통)목록전체 선택/해제
function checkAll(formName, checkboxName, allCheckName) {
  var formNameStr = "document." + formName + ".";
  var objChecks = eval(formNameStr + checkboxName);
  var objAllChecks = eval(formNameStr + allCheckName);
  if(objChecks) {
    var chkLen = objChecks.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        objChecks[i].checked = objAllChecks.checked;
      }
    }else{
      objChecks.checked = objAllChecks.checked;
    }
  }
}


//생산처 검색 팝업
function popupVendor(){
  var form = document.forms[0];
  if(form.vendorFlag.value == "i") {//사내
    selectDepartment();
  } else {//외주
    selectPartner();
  }
}

//생산처 초기화
function clearVendor(){
  var form = document.forms[0];
  form.prodVendor.value = '';
  form.prodVendorName.value = '';
}

//생산처 초기화
function clearReqDate(){
  var form = document.forms[0];
  form.completeReqDate.value = '';
}

//협력사검색 팝업 Open
function selectPartner() {
  var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
  openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
}

//협력사 검색결과 셋팅
function linkPartner(arr){
  if(arr.length == 0) {
    alert("협력사를 선택하시기 바랍니다.");
    return;
  }
  var form = document.forms[0];
  form.prodVendor.value = arr[0][0];
  form.prodVendorName.value = arr[0][1];
}

//사내생산처 검색 팝업  Open
function selectDepartment() {
	var mode = "single";
	var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&invokeMethod=linkDept&mode="+mode;
	
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    
    getOpenWindow2(url,'', 900, 600, opts);
    
}

//사내생산처 검색결과 셋팅
function linkDept(arr){
  if(arr.length == 0) {
    alert("부서를 선택하시기 바랍니다.");
    return;
  }
  var form = document.forms[0];
  form.prodVendorName.value = arr[0][2];
  form.prodVendor.value = arr[0][1];
}

//부품 상세조회 팝업
function viewRelPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function viewRelIssue(oid){
  var url = '/plm/jsp/project/projectIssueView.jsp?oid='+oid;
  openWindow(url,"","750","400","scrollbars=no,resizable=no,top=200,left=250");
}


    function disable( value ) {
        if( value == 'REQ_3' ) {
            document.forms[0].otherReqType.disabled = false;
        }
        else {
            document.forms[0].otherReqType.disabled = true;
        }
    }

    //자료 취소
    function doCancel(){
        if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") ) {
        	
        	/* 
            var url = "/plm/servlet/e3ps/MoldEcrServlet";
            var form = document.forms[0];
            if(form.autoSearch.value == "Y") {
              form.cmd.value = "cancel";
              form.target = "download";
              form.action = url;
              form.method = "post";
              form.encoding = 'multipart/form-data';
              disabledAllBtn();
              showProcessing();
              form.submit();
            } else {
              location.href = "/plm/jsp/ecm/SearchEcrForm.jsp";
            }
            */
            
            if(opener) {
            	opener.feedbackAfterPopup("doReSearching");
            	window.close();
                
            } else {
	            history.back();
            
            }
            
        }
        else {
          return;
        }
    }

    //입력항목 체크
    function checkValidate(){
        var form = document.forms[0];
        var targetTable = document.getElementById("relPartTable");
        var tableRows = targetTable.rows.length;
        var dev_yn = document.getElementsByName("rdoDevYn");
        var str_dev_yn = '';
        for( var inx=0; inx < dev_yn.length ; inx++) {
            if( dev_yn[inx].checked ) {
                str_dev_yn  = dev_yn[inx].value;
            }
        }
        if(isNullData(form.ecrName.value)) {
          alert("<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>");
          form.ecrName.focus();
          return false;
        } else if( str_dev_yn == ''  ){
          alert("<%=messageService.getString("e3ps.message.ket_message", "01191") %><%--단계 구분을 선택하세요--%>");
          return false;
        } else if( str_dev_yn == 'D' && form.projectOid.value == ''   ){
          alert("<%=messageService.getString("e3ps.message.ket_message", "04930") %><%--개발단계가 \'양산\'이 아닐 경우 프로젝트 정보를 입력하셔야 합니다.--%>");
          popupProject('projectNo');
          return false;
        } 
        
        else if( $('input[name=reviewRequestDate]').val() == '' )
        {
          alert('<%=messageService.getString("e3ps.message.ket_message", "04810") %><%--회신기한을 입력하여 주십시오.--%>');
          $('input[name=reviewRequestDate]').focus();
          return false;
        }
        else if( $('input[name=subjectOid]').val() == '' )
        {
          alert('<%=messageService.getString("e3ps.message.ket_message", "04790") %><%--주관부서를 선택하여 주십시오.--%>');
          $('input[name=subjectDisplayName]').focus();
          lfn_addDepartment2();
          return false;
        }
        
        else if(isNullData(form.changeType.value)) {
          alert("<%=messageService.getString("e3ps.message.ket_message", "01861") %><%--설계변경유형을 입력하여 주십시오--%>");
          $('input[name=chkChangeType]').eq(0).focus();
          return false;
        } else if( form.processType[1].checked && form.otherProcessDesc.value == '' ){
          alert("<%=messageService.getString("e3ps.message.ket_message", "02783") %><%--처리구분의 설명을 입력하여 주십시오--%>");
          form.otherProcessDesc.focus();
          return false;
        } 
        <%-- 
        else if( getStringLength(form.ecrDesc.value) > 200 ){
          alert("<%=messageService.getString("e3ps.message.ket_message", "01869") %>설명을 200byte 이내로 작성하여 주십시오");
          return false;
        }else if( getStringLength(form.expectEffect.value) > 200){
          alert('<%=messageService.getString("e3ps.message.ket_message", "01117") %>기대효과를 200byte 이내로 작성하여 주십시오');
          return false;
        } 
        --%>
        
        else if(tableRows <= 1){
          //alert("<%=messageService.getString("e3ps.message.ket_message", "00935") %><%--관련부품을 1개이상 입력하여 주십시오--%>");
          alert('<%=messageService.getString("e3ps.message.ket_message", "00914") %><%--제품을 추가하세요--%>');
          popupRelPart();
          return false;
        }
        return true;
    }

    //관련이슈 검색 팝업 호출
    function popupRelIssue() {
        var form = document.forms[0];
        if(form.projectOid.value == "") {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00921") %><%--관련 프로젝트가 없습니다--%>");
            return;
        }
        var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/ProjectIssueViewListPopup.jsp";
        url += "&obj=projectOid^" + form.projectOid.value;
        attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        insertRelIssueLine(attache);
    }

    function isDuplicate( issueOid ) {
        var isDuplicate = false;

        var issueIdList = document.getElementsByName("relIssueOid");
        for(var idCnt=0; idCnt < issueIdList.length ; idCnt++ ) {
            if( issueOid == issueIdList[idCnt].value ) {
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }

    //관련이슈 라인추가(projectIssueCreate.jsp 참조)
    function insertRelIssueLine(objArr) {
        if(objArr.length == 0) {
          return;
        }
        var targetTable = document.getElementById("relIssueTable");

        var trArr;
        var str = "";
        for(var i = 0; i < objArr.length; i++) {
            trArr = objArr[i];

            if( !isDuplicate(trArr[0]) ) {
                var tableRows = targetTable.rows.length;
                var newTr = targetTable.insertRow(tableRows);

                //전체선택 checkbox
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "20";
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                                + "<div style=\"display:none;\"><input name='chkSelectRelIssue' type='checkbox' value=''></div>"
                                ;

                //이슈명
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "";
                newTd.className = "tdwhiteL";
                newTd.setAttribute("title", trArr[1]);
                str = "<div class='ellipsis' style='width:336;'><nobr>";
                str += "<a href=\"javascript:viewIssue('" + trArr[0] + "');\">" + trArr[1] + "</a>";
                str += "<input type='hidden' name='relIssueOid' value='" + trArr[0] + "'>";
                str += "<input type='hidden' name='relIssueLinkOid' value=''></nobr></div>";
                newTd.innerHTML = str;

                //작성자
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "140";
                newTd.className = "tdwhiteM";
                newTd.innerHTML = trArr[2];

                //작성일자
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM0";
                newTd.width = "115";
                newTd.innerHTML = trArr[3];
            }
        }
    }

  //첨부파일 라인추가(projectIssueCreate.jsp 참조)
    function insertFileLine() {
      var targetTable = document.getElementById("filetable");
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.height = "27";

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "20";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                      + "<div style=\"display:none;\"><input name='addFileSelect' type='checkbox' value=''></div>"
                      ;

      //첨부파일
      var filePath = "filePath" + tableRows;
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "";
      newTd.className = "tdwhiteL0";
      newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:100%'>";
    }

    //(공통)선택자료삭제(projectIssueCreate.jsp 참조)
    //1.form name
    //2.dhtml tableElementId
    //3.개별 checkboxName
    //4.타이틀바의 전체 checkboxName
    //5.삭제oid문자열:oid1*oid2
    //function deleteDataLine(formName, tableElementId, checkboxName, allCheckName) {
    //  deleteDataLine(formName, tableElementId, checkboxName, allCheckName, '');
    //}

    function isChecked( checkboxName )
    {
      var isChecked = false;
       var objCheck = document.getElementsByName(checkboxName);

       for( var i=0; i < objCheck.length; i++ )
       {
         if( objCheck[i].checked )
         {
           isChecked = true;
         }
       }

       return isChecked;
    }

    function deleteDataLine2(formName, tableElementId, checkboxName, allCheckName, listVarName)
    {
      var body = document.getElementById(tableElementId);
      if (body.rows.length == 1) return;
      var formNameStr = "document." + formName + ".";
      var objChecks = eval(formNameStr + checkboxName);
      var objAllChecks = eval(formNameStr + allCheckName);

      if( isChecked(checkboxName) )
      {
        var listVal = "";
        var objList;
        if(listVarName != "") {
          objList = eval(formNameStr + listVarName);
          listVal = objList.value;
        }

        if (body.rows.length == 2) {
          if ((typeof objChecks != 'undefined' && objChecks.checked) || (typeof objChecks[0] != 'undefined' && objChecks[0].checked)) {
            if(typeof objChecks != 'undefined' && objChecks.checked) {
              if(listVal == "") {
                listVal = objChecks.value;
              } else {
                listVal += "*" + objChecks.value;
              }
            } else if(typeof objChecks[0] != 'undefined' && objChecks[0].checked) {
              if(listVal == "") {
                listVal = objChecks[0].value;
              } else {
                listVal += "*" + objChecks[0].value;
              }
            }
            body.deleteRow(1);
          }
        } else {
          for (var i = objChecks.length; i > 0; i--) {
            
            if (objChecks[i-1].checked) {
              if(listVal == "") {
                listVal = objChecks[i-1].value;
              } else {
                listVal += "*" + objChecks[i-1].value;
              }
              body.deleteRow(i);
            }
          }
        }
        if (body.rows.length < 2) {
          //objAllChecks.checked = false;
        }
        if(listVarName != "") {
          objList.value = listVal;
        }
      }
      else
      {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
        return;
      }
    }
    
    function deleteDataLine(formName, tableElementId, checkboxName, allCheckName, listVarName) {
      var body = document.getElementById(tableElementId);
      if (body.rows.length == 0) return;
      var formNameStr = "document." + formName + ".";
      var objChecks = eval(formNameStr + checkboxName);
      var objAllChecks = eval(formNameStr + allCheckName);

      if( isChecked(checkboxName) )
      {
        var listVal = "";
        var objList;
        if(listVarName != "") {
          objList = eval(formNameStr + listVarName);
          listVal = objList.value;
        }

        if (body.rows.length == 1) {
          if (objChecks.checked || objChecks[0].checked) {
            if(objChecks.checked) {
              if(listVal == "") {
                listVal = objChecks.value;
              } else {
                listVal += "*" + objChecks.value;
              }
            } else if(objChecks[0].checked) {
              if(listVal == "") {
                listVal = objChecks[0].value;
              } else {
                listVal += "*" + objChecks[0].value;
              }
            }
            body.deleteRow(0);
          }
        } else {
          for (var i = body.rows.length; i > 0; i--) {
            if (objChecks[i-1].checked) {
              if(listVal == "") {
                listVal = objChecks[i-1].value;
              } else {
                listVal += "*" + objChecks[i-1].value;
              }
              body.deleteRow(i - 1);
            }
          }
        }
        if (body.rows.length < 1) {
          objAllChecks.checked = false;
        }
        if(listVarName != "") {
          objList.value = listVal;
        }
      }
      else
      {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
        return;
      }
    }
</script>

<script type="text/javascript">

    <%--주관부서--%>
    function lfn_addDepartment2() {
        
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s&invokeMethod=addDepartmentCallBack";
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=430,height=600";
        
        window.open(url,'',opts);
    }
    
    function addDepartmentCallBack(attacheDept){
        var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
        var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
        callServer(url, lfn_acceptDept2);
    }
    
    function getTagText(xmlDoc){
        return xmlDoc.textContent || xmlDoc.text || '';
    }
    
    
    function lfn_acceptDept2(req) {
        var xmlDoc = req.responseXML;
        var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
        if(msg != 'true') {
            alert('다시 시도하시기 바랍니다');
            return;
        }
        var l_oid = xmlDoc.getElementsByTagName("l_oid");
        var l_name = xmlDoc.getElementsByTagName("l_name");
        var l_code = xmlDoc.getElementsByTagName("l_code");
        var l_chiefOid = xmlDoc.getElementsByTagName("l_chiefOid");
        var l_chiefName = xmlDoc.getElementsByTagName("l_chiefName");
        
        document.forms[0].subjectDisplayName.value = decodeURIComponent(getTagText(l_name[0]));
        document.forms[0].subjectOid.value = decodeURIComponent(getTagText(l_oid[0]));
        document.forms[0].subjectCode.value = decodeURIComponent(getTagText(l_code[0]));

        // chiefName, chiefOid 추가
        document.forms[0].chargeDisplayName.value = decodeURIComponent(getTagText(l_chiefName[0]));
        document.forms[0].chargeOid.value = decodeURIComponent(getTagText(l_chiefOid[0]));

    }    
    function lfn_deleteDept(){
        document.forms[0].subjectDisplayName.value = "";
        document.forms[0].subjectOid.value = "";
        document.forms[0].subjectCode.value = "";
    }
    
    <%--담당자--%>
    function lfn_addCharge() {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
        attacheMembers = window.showModalDialog(url,window,"help=no; toolbar=no; location=no; directory=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if(typeof attacheMembers == "undefined" || attacheMembers == null) {
            return;
        }
        lfn_acceptCharge(attacheMembers);
    }

    function lfn_acceptCharge(objArr) {
        if(objArr.length == 0) {
            return;
        }

        // 담당자
        var chargeDisplayName = document.forms[0].chargeDisplayName;
        var chargeOid = document.forms[0].chargeOid;
        var chargeName = document.forms[0].chargeName;
        
        // 주관부서
        var subjectDisplayName = document.forms[0].subjectDisplayName;
        var subjectOid = document.forms[0].subjectOid;
        var subjectCode = document.forms[0].subjectCode;
        
        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            /* 
            for(var j=0; j < infoArr.length; j++) {
                alert('infoArr['+ j +'] is '+ infoArr[j]);
            }
            */
            // 담당자
            chargeDisplayName.value = infoArr[4];
            chargeOid.value = infoArr[0];
            chargeName.value = infoArr[3];
            
            // 주관부서
            subjectDisplayName.value = infoArr[5];
            subjectOid.value = infoArr[2];
            subjectCode.value = "";
        }
        
    }    
    function lfn_deleteCharge(){
        document.forms[0].chargeDisplayName.value = "";
        document.forms[0].chargeOid.value = "";
        document.forms[0].chargeName.value = "";
        
        // lfn_deleteDept();
    }    
    
    <%--회신기한--%>
    function lfn_feedback_showCal_reviewRequestDate() {
        // Do nothing..!!   
    }    

    function lfn_reloadWhole() {
        try {
            parent.lfn_reloadWhole();
            
        } catch(e) {}
    }
-->
</script>

<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
    //<![CDATA[

    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
    var g_arrSetEditorArea = new Array();
    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
    g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER1";// 이노디터를 위치시킬 영역의 ID값 설정

    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui_half.js"></script>
<script type="text/javascript">
    //<![CDATA[

    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우

    // Skin 재정의
    //g_nSkinNumber = 0;

    // 크기, 높이 재정의
    <%
    if(moldEcr == null) {
	%>
	g_nEditorWidth = 485;
    <%
    } else {
	%>
	g_nEditorWidth = 453;
    <%
    }
    %>
    g_nEditorHeight = 200;

    //g_bCustomEditorWidthPercentageYN = true;

//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>

<script language=javascript>
<% /* deprecated */ %>
/* 
function fnCustomerFunction_1(EditNumber)
{
    //alert("사용자 정의 툴바버튼 1");
    var object = document.getElementById("EDITOR_AREA_CONTAINER"+ ((EditNumber == 0) ? "" : EditNumber));
    object.style.position = "absolute";
    object.style.top = "100px";
    object.style.left = "60px";
    object.style.zIndex = 9999;
    //object.style.width = 1000;
    //object.style.height = 660;
    
    fnResizeEditor(EditNumber, 900, 600);
    
}
*/
 
function fnCustomerFunction_2(EditNumber)
{
    //alert("사용자 정의 툴바버튼 2");
    fnResizeEditor(0, g_nEditorWidth, 600);
    fnResizeEditor(1, g_nEditorWidth, 600);
}

function fnCustomerFunction_3(EditNumber)
{
    //alert("사용자 정의 툴바버튼 3");
    /* 
    var object = document.getElementById("EDITOR_AREA_CONTAINER"+ ((EditNumber == 0) ? "" : EditNumber));
    object.style.position = "";
    */
     
    fnResizeEditor(0, g_nEditorWidth, 200);
    fnResizeEditor(1, g_nEditorWidth, 200);
}
</script>

<!-- 이노디터 JS Include End -->

</head>
<body onload="javascript:onLoad();">
<form method="post" action="/plm/servlet/e3ps/MoldEcrServlet">
<input type="hidden" name="cmd" value="create">
<input type="hidden" name="oid" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest()));}%>">
<input type="hidden" name="ecoId" value="">
<input type="hidden" name="devYn" value="">
<input type="hidden" name="changeType" value="">
<input type="hidden" name="deleteFileList" value="">
<input type="hidden" name="deleteRelIssueList" value="">
<input type="hidden" name="deleteRelPartList" value="">

<input type="hidden" name="page" value="<%=ParamUtil.getParameter(request, "page")%>">
<input type="hidden" name="totalCount" value="<%=ParamUtil.getParameter(request, "totalCount")%>">
<input type="hidden" name="sortColumn" value="<%=ParamUtil.getParameter(request, "sortColumn")%>">
<input type="hidden" name="param" value="<%=ParamUtil.getParameter(request, "param")%>">
<input type="hidden" name="perPage" value="<%=ParamUtil.getParameter(request, "perPage")%>">
<input type="hidden" name="autoSearch" value="<%=ParamUtil.getParameter(request, "autoSearch")%>">
<input type="hidden" name="srchpartOid" value="<%=ParamUtil.getParameter(request, "partOid")%>">
<input type="hidden" name="srchpartNo" value="<%=ParamUtil.getParameter(request, "srchpartNo")%>">
<input type="hidden" name="srchpartName" value="<%=ParamUtil.getParameter(request, "srchpartName")%>">
<input type="hidden" name="srchprojectOid" value="<%=ParamUtil.getParameter(request, "srchprojectOid")%>">
<input type="hidden" name="srchprojectNo" value="<%=ParamUtil.getParameter(request, "srchprojectNo")%>">
<input type="hidden" name="srchprojectName" value="<%=ParamUtil.getParameter(request, "srchprojectName")%>">
<input type="hidden" name="srchorgName" value="<%=ParamUtil.getParameter(request, "srchorgName")%>">
<input type="hidden" name="srchcreatorOid" value="<%=ParamUtil.getParameter(request, "srchcreatorOid")%>">
<input type="hidden" name="srchcreatorName" value="<%=ParamUtil.getParameter(request, "srchcreatorName")%>">
<input type="hidden" name="srchecrId" value="<%=ParamUtil.getParameter(request, "srchecrId")%>">
<input type="hidden" name="srchdivisionFlag" value="<%=ParamUtil.getParameter(request, "srchdivisionFlag")%>">
<input type="hidden" name="srchdevYn" value="<%=ParamUtil.getParameter(request, "srchdevYn")%>">
<input type="hidden" name="srchsancStateFlag" value="<%=ParamUtil.getParameter(request, "srchsancStateFlag")%>">
<input type="hidden" name="srchecrName" value="<%=ParamUtil.getParameter(request, "srchecrName")%>">
<input type="hidden" name="srchprodMoldCls" value="<%=ParamUtil.getParameter(request, "srchprodMoldCls")%>">
<input type="hidden" name="srchcreateStartDate" value="<%=ParamUtil.getParameter(request, "srchcreateStartDate")%>">
<input type="hidden" name="srchcreateEndDate" value="<%=ParamUtil.getParameter(request, "srchcreateEndDate")%>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <%
        if( isCreate ) {
        %>
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%if( isCreate ){ %> <%=messageService.getString("e3ps.message.ket_message", "01005") %><%--금형 ECR 등록--%> <%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01007") %><%--금형 ECR 수정--%><%} %></td>
                <!-- td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%if( isCreate ){ %><%=messageService.getString("e3ps.message.ket_message", "01005") %><%--금형 ECR 등록--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01007") %><%--금형 ECR 수정--%><%} %>
                </td -->
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <tr>
          <td class="space5"></td>
        </tr>
        <%
        }
        %>        
        <!-- tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" -->
        <tr>
          <td>&nbsp;</td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onclick="javascript:doSave();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onclick="javascript:doCancel();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      
      
      <div style="width:100%; height:680px; overflow-x:auto; overflow-y:auto;">
	      <table border="0" cellspacing="0" cellpadding="0" width="100%">
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
	          <td colspan="3" class="tdwhiteL0"><input name="ecrName" type="text" class="txt_field" style="width:100%" maxlength="100" 
	                    value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrName()));}%>"></td>
	        </tr>
	        <tr>
	          <td class="tdblueL" style="width:15%;"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%><span class="red">*</span></td>
	          <td class="tdwhiteL" style="width:35%;">
	            <input name="rdoDevYn" type="radio" class="Checkbox" value="D" <%=rdoDevYn[0]%>><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%>
	            <input name="rdoDevYn" type="radio" class="Checkbox" value="P" <%=rdoDevYn[1]%>><%=messageService.getString("e3ps.message.ket_message", "02094") %><%--양산단계--%>
	            <!-- input name="rdoDevYn" type="radio" class="Checkbox" value="N" <%=rdoDevYn[2]%>><%=messageService.getString("e3ps.message.ket_message", "01461") %><%--미지정--%> -->
	          </td>
	          <td class="tdblueL" style="width:15%;"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%><span class="red">*</span></td>
	          <td class="tdwhiteL0" style="width:35%;">
	          <select name="divisionFlag" class="Checkbox" style="width:217" style="text-align:center;">
	          <%
	          if( userGroup.equals("지원조직") )
	          {
	              out.println("<option value=''>" + messageService.getString("e3ps.message.ket_message", "02485") /*전체*/ + "</option>");
	          }
	          %>
	                  <%for(i=0; i<divisionFlagCodeVO.getTotalCount(); i++) {
	                      ketCodeDetailVO = (KETCodeDetailVO)divisionFlagCodeVO.getKetCodeDetailVOList().get(i);
	                      if( userGroup.equals(ketCodeDetailVO.getName()) || userGroup.equals("지원조직") )
	                      {
	                    out.println("<option value='" + ketCodeDetailVO.getCode() + "'"+rdoDivisionFlag[i]+">" + ketCodeDetailVO.getName() + "</option>");
	                      }
	                  }%>
	              </select>
	              </td>
	        </tr>
	        <tr>
	          <td class="tdblueL">Project No</td>
	          <td class="tdwhiteL">
                <input type="text" name="projectNo" class="txt_field" style="width:80%;" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeRequestVO.getProjectNo()));}%>">
	            <a href="javascript:popupProject('projectNo');">
                   <img src="/plm/portal/images/icon_5.png" border="0">
                </a>
                <a href="#" onfocus="this.blur();" onClick="javascript:clearProject();">
                   <img src="/plm/portal/images/icon_delete.gif" border="0">
                 </a>
              </td>
              <td class="tdblueL">Project Name</td>
              <td class="tdwhiteL0">
	            <input type="text" name="projectName" class="txt_fieldRO" style="width:100%;" readOnly value="<%=StringUtil.checkNull(ketMoldChangeRequestVO.getProjectName())%>">
                <input type="hidden" name="projectOid" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getProjectOid()));}%>">
	          </td>
	        </tr>
	        
	        <%-- 
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00939") %>관련이슈</td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
	              <tr>
	                <td align="right">
	                    <table border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td>
	                          <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:popupRelIssue();"><%=messageService.getString("e3ps.message.ket_message", "02861") %>추가</a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table>
	                      </td>
	                      <td width="5">&nbsp;</td>
	                      <td>
	                          <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:deleteDataLine('forms[0]', 'relIssueTable', 'chkSelectRelIssue', 'chkAllRelIssue', 'deleteRelIssueList');"><%=messageService.getString("e3ps.message.ket_message", "01690") %>삭제</a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table>
	                      </td>
	                    </tr>
	                  </table>
	                </td>
	                <td width="13">&nbsp;</td>
	              </tr>
	            </table>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <div id="div_scroll" style="width=645;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0" id="relIssueTable">
	                <tr class="">

	                      <td width="20" class="tdgrayM"><a href="javascript:popupRelIssue();"><img src="/plm/portal/images/b-plus.png"></a></td>
                          <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02317") %>이슈명</td>
	                      <td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %>작성자</td>
	                      <td width="115" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %>작성일자</td>

                    </tr>

	                  <%
	                  if(ketMoldChangeRequestVO.getTotalCount() > 0) {
	                      ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = ketMoldChangeRequestVO.getKetMoldECRIssueLinkVOList();
	                      if(ketMoldECRIssueLinkVOList == null) {
	                          size = 0;
	                      } else {
	                          size = ketMoldECRIssueLinkVOList.size();
	                      }
	                      KETMoldECRIssueLinkVO ketMoldECRIssueLinkVO = null;
	                      for (i = 0 ; i<size; i++ ) {
	                          ketMoldECRIssueLinkVO = (KETMoldECRIssueLinkVO)ketMoldECRIssueLinkVOList.get(i);
	                  %>
	                    <tr>
	                      <td width="40" class="tdwhiteM">
	                        <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relIssueTable', 'chkSelectRelIssue', 'chkAllRelIssue', 'deleteRelIssueList');"><img src="/plm/portal/images/b-minus.png"></a>
                            <div style="display:none;"><input type="checkbox" name="chkSelectRelIssue" value="<%=ketMoldECRIssueLinkVO.getRelIssueLinkOid()%>"></div>
                          </td>
	                      <td width="" class="tdwhiteL" title='<%=ketMoldECRIssueLinkVO.getRelIssueName()%>'><div class="ellipsis" style="width:336;"><nobr><a href="javascript:viewRelIssue('<%=ketMoldECRIssueLinkVO.getRelIssueOid()%>');"><%=ketMoldECRIssueLinkVO.getRelIssueName()%>&nbsp;</a></nobr></div>
	                        <input type='hidden' name='relIssueOid' value='<%=ketMoldECRIssueLinkVO.getRelIssueOid()%>'>
	                        <input type='hidden' name='relIssueLinkOid' value='<%=ketMoldECRIssueLinkVO.getRelIssueLinkOid()%>'></td>
	                      <td width="140" class="tdwhiteM"><%=ketMoldECRIssueLinkVO.getRelIssueCreatorName()%></td>
	                      <td width="115" class="tdwhiteM0"><%=ketMoldECRIssueLinkVO.getRelIssueCreateDate()%></td>
	                    </tr>
	                  <%
	                      }
	                  }
	                  %>

	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table -->
	          </td>
	        </tr>
	        --%>
	        
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02247") %><%--의뢰구분--%><span class="red">*</span></td>
	          <td colspan="3" class="tdwhiteL0"><input name="requestType" type="radio" class="Checkbox" value="REQ_1" <%=rdoRequestType[0]%> onclick="javascript:disable(this.value);">
	            <%=messageService.getString("e3ps.message.ket_message", "01087") %><%--금형설계 변경--%>
	            &nbsp;<input name="requestType" type="radio" class="Checkbox" value="REQ_2" <%=rdoRequestType[1]%> onclick="javascript:disable(this.value);">
	            <%=messageService.getString("e3ps.message.ket_message", "01086") %><%--금형설계 도면정리--%>
	            &nbsp;<input name="requestType" type="radio" class="Checkbox" value="REQ_3" <%=rdoRequestType[2]%> onclick="javascript:disable(this.value);">
	            <%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>
	            &nbsp;<input type="text" name="otherReqType" class="txt_field" style="width:349" maxlength="50"
	                value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) {out.println(StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getOtherReqType()));}%>">
	          </td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01860") %><%--설계변경유형--%><span class="red">*</span></td>
	          <td colspan="3" class="tdwhiteL0">
	              <table cellpadding="0" cellspacing="0" width='100%' >
	              
					<%
					for(i=0; i<changeTypeCodeVO.getTotalCount(); i++) {
					    ketCodeDetailVO = (KETCodeDetailVO)changeTypeCodeVO.getKetCodeDetailVOList().get(i);
					    if((i % 6) == 0) {
					        out.println("<tr>");
					    }
					    if( (i/11) != 1 )
					    {
					        out.println("<td width='10'><input type='checkbox' name='chkChangeType' value='" + ketCodeDetailVO.getCode() + "' " + chkChangeType[i] + "></td>");
					    }
					    else
					    {
					        out.println("<td width='10'><input type='checkbox' name='chkChangeType' value='" + ketCodeDetailVO.getCode() + "' " + chkChangeType[i] + " onclick=\"javascript:setEtcValueStatus('otherChangeDesc', this.checked);\"></td>");
					    }
					    out.println("<td width='70' valign='bottom'>" + ketCodeDetailVO.getName() + "</td>");
					    if( (i / 11) ==   1 ){
					     if( ketMoldChangeRequestVO.getTotalCount() > 0 )
					     {
					         out.println("<td><input type='hidden' name='otherChangeDesc' class='txt_field' style='width:104' value='"+StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getOtherChangeDesc())+"'></td>");
					     }
					     else
					     {
					         out.println("<td><input type='hidden' name='otherChangeDesc' class='txt_field' style='width:104' value=''></td>");
					     }
					    }
					    if((i % 6) == 5) {
					        out.println("</tr>");
					    }
					}%>
	
	            </table>
	          </td>
	        </tr>
	        <tr>
	          <!-- 
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
	          <td width="295" class="tdwhiteL">
	            <input type="text" name="completeReqDate" id="completeReqDate" class="txt_field"  style="width:70px" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.println(StringUtil.checkNull(ketMoldChangeRequestVO.getCompleteReqDateFormat()));}%>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('completeReqDate');" style="cursor: hand;">
              </td>
               -->
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04102") %><%--회신기한--%><span class="red">*</span></td>
              <td class="tdwhiteL">
                <input type="text" name="reviewRequestDate" id="reviewRequestDate" class="txt_field"  style="width:90px" value="<%=reviewRequestDate %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('reviewRequestDate');" style="cursor: hand;">
              </td>
	          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
	          <td  width="230" class="tdwhiteL0">
	              <table border="0" width="100%" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td width="55">
	                    <select name="vendorFlag" class="Checkbox" style="width:50" style="text-align:center;">
	                      <option value="i" <%=cmbVendorFlag[0]%>><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>
	                      <option value="o" <%=cmbVendorFlag[1]%>><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option>
	                    </select>
	                  </td>
	                  <td width="">
	                    <input type="hidden" name="prodVendor"
	                           value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getProdVendor()));}%>">
	                    <input type="text" name="prodVendorName" class="txt_fieldRO" style="width:95%" onclick="this.blur();" readonly
	                           value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeRequestVO.getProdVendorName()));}%>">
	                  </td>
	                  <td width="20"><a href="javascript:popupVendor();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a></td>
	                  <td width="30"><a href="javascript:clearVendor();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
	                </tr>
	              </table>
	          </td>
	        </tr>
	        <tr>
	          <!-- 처리구분 -->
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02782") %><%--처리구분--%><span class="red">*</span></td>
	          <td class="tdwhiteL" colspan="3">
	            <table width='100%' border='0' cellspacing="0" cellpadding="0">
	              <tr>
	                <td width="20px"><input name="processType" type="radio" class="Checkbox" value="PROC_1" <%=rdoProcessType[0]%>></td>
	                <td width="45px"><%=messageService.getString("e3ps.message.ket_message", "02345") %><%--일반--%></td>
	                <td width="20px"><input name="processType" type="radio" class="Checkbox" value="PROC_2" <%=rdoProcessType[1]%>></td>
	                <td width="45px"><%=messageService.getString("e3ps.message.ket_message", "01156") %><%--긴급--%></td>
	                <td width="*"><input type="text" name="otherProcessDesc" class="txt_field" style="width:60%"
	                           value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.println(StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getOtherProcessDesc()));}%>"></td>
	              </tr>
	            </table>
	          </td>
	        </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04103") %><%--주관부서--%><span class="red">*</span></td>
              <td class="tdwhiteL">
                <input type="text" name="subjectDisplayName" value="<%=subjectDisplayName %>" style="width:80%" class='txt_field'>
                <input type="hidden" name="subjectOid" value="<%=subjectOid %>" >
                <input type="hidden" name="subjectCode" value="<%=subjectCode %>" >
                <a href="javascript:lfn_addDepartment2();">
                    <img src="/plm/portal/images/icon_5.png" border=0>
                </a>
                <a href="javascript:lfn_deleteDept();">
                    <img src="/plm/portal/images/icon_delete.gif" border=0>
                </a>       
              </td>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
              <td class="tdwhiteL0">
                <input type="text" name="chargeDisplayName" value="<%=chargeDisplayName %>" style="width:80%" class="txt_field">
                <input type="hidden" name="chargeOid" value="<%=chargeOid %>" >
                <input type="hidden" name="chargeName" value="<%=chargeName %>" >
                <a href="javascript:lfn_addCharge();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                <a href="javascript:lfn_deleteCharge();"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
              </td>
            </tr>       
	        <!-- tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%><span class="red"></span></td>
	          <td colspan="3" class="tdwhiteL0">
	              <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	              <textarea name="ecrDesc" style='overflow: auto; width:100%; height:50px; padding: 2;' class="txt_field"
	            ><%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrDesc()));}%></textarea>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	          </td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01116") %><%--기대효과--%><span class="red"></span></td>
	          <td colspan="3" class="tdwhiteL0">
	              <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	              <textarea name="expectEffect" style='overflow: auto; width:100%; height:50px; padding: 2;' class="txt_field"
	                        ><%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getExpectEffect()));}%></textarea>
	                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	                  </td>
	        </tr -->
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><span class="red">*</span></td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
	              <tr>
	                <td>&nbsp;</td>
	                <td align="right">
	                    <table border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td>
	                          <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
	                                ><a href="javascript:popupRelPart();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table>
	                      </td>
	                      <td width="5">&nbsp;</td>
	                      <td>
	                          <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
	                                ><a href="javascript:deleteDataLine('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
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
	            <div id="div_scroll2" style="width=645;height=81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0" id="relPartTable">
	                <tr class="">

                          <td width="20" class="tdgrayM"><a href="javascript:popupRelPart();"><img src="/plm/portal/images/b-plus.png"></a></td>
                          <td width="120" class="tdgrayM">Die No</td>
	                      <td width=""    class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
	                      <td width="50"  class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
	                      <!-- td width="200" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02191") %><%--요청내용--%></td -->

                    </tr>

	                  <%
	                  if(ketMoldChangeRequestVO.getTotalCount() > 0) {
	                      ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeRequestVO.getKetMoldECOPartLinkVOList();
	                      if(ketMoldECOPartLinkVOList != null) {
		                      size = ketMoldECOPartLinkVOList.size();
		                      KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
		                      for(i = 0 ; i<size; i++) {
		                          ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO)ketMoldECOPartLinkVOList.get(i);
	                  %>
	                    <tr height="27">
	                      <td width="40" class="tdwhiteM">
                            <div style="display:none;"><input type="text" name="req_comment" value='<%=ketMoldECOPartLinkVO.getChangeReqComment() %>'></div>
                          
                            <a href="#" onclick="javascript:$(this).next().children().attr('checked',true); deleteDataLine2('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');"><img src="/plm/portal/images/b-minus.png"></a>
                            <div style="display:none;"><input type="checkbox" name="chkSelectRelPart" value='<%=StringUtil.checkNull(ketMoldECOPartLinkVO.getRelPartLinkOid())%>'></div>
	                      </td>
	                      <td width="120" class="tdwhiteM" title="<%=ketMoldECOPartLinkVO.getRelPartNumber()%>"><div class="ellipsis" style="width:115;"><nobr><a href="javascript:viewRelPart('<%=ketMoldECOPartLinkVO.getRelPartOid()%>');"><%=ketMoldECOPartLinkVO.getRelPartNumber()%>&nbsp;</a></nobr></div>
	                      <input type='hidden' name='relPartOid' value='<%=ketMoldECOPartLinkVO.getRelPartOid()%>'>
	                      <input type='hidden' name='relPartLinkOid' value='<%=StringUtil.checkNull(ketMoldECOPartLinkVO.getRelPartLinkOid())%>'></td>
	                      <td width="" class="tdwhiteL" title="<%=ketMoldECOPartLinkVO.getRelPartName()%>"><div class="ellipsis" style="width:217;"><nobr><%=ketMoldECOPartLinkVO.getRelPartName()%>&nbsp;</nobr></div></td>
	                      <td width="50" class="tdwhiteM0"><%=ketMoldECOPartLinkVO.getRelPartRev()%>&nbsp;</td>
	                      <!-- td width="200" class="tdwhiteL0"><input type='text' name='changeReqComment' class='txt_field' style='width:190' value="<%=ketMoldECOPartLinkVO.getChangeReqComment()%>"></td -->
	                    </tr>
	                    <%
		                      }
	                      }
	                  }
	                  %>
	                  
	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table -->
	          </td>
	        </tr>
            <tr>
              <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04127") %><%--현상--%></td>
              <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04128") %><%--문제점 및 요구사항--%></td>
            </tr>   	        
            <tr>
              <td colspan="2" class="tdwhiteL">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor %></textarea> 
                            <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=webEditorText %></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER"></div> 
                            <!-- Editor Area Container : End -->
                            
                            
              </td>
              <td colspan="2" class="tdwhiteL0">
              
              
                            <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor1" rows="0" cols="0" style="display: none"><%=webEditor1 %></textarea> 
                            <textarea name="webEditorText1" rows="0" cols="0" style="display: none"><%=webEditorText1 %></textarea> 
                            <!-- Editor Area Container : Start -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                            <div id="EDITOR_AREA_CONTAINER1"></div> 
                            <!-- Editor Area Container : End -->
                            
                            
              </td>            
            </tr>               
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
	          <td colspan="3" class="tdwhiteL0">
	            <!-- table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table>
	            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
	              <tr>
	                <td>&nbsp;</td>
	                <td align="right">
	                    <table border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td>
	                          <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
	                                ><a href="javascript:insertFileLine();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
	                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                          </tr>
	                        </table>
	                      </td>
	                      <td width="5">&nbsp;</td>
	                      <td>
	                          <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"
	                                ><a href="javascript:deleteDataLine('forms[0]', 'filetable', 'addFileSelect', 'chkFileAll', 'deleteFileList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
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
	            <div id="div_scroll3" style="width=645;height=81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
	            <table width="100%" cellpadding="0" cellspacing="0" id="filetable">
	              <tr class="">

	                      <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine();"><img src="/plm/portal/images/b-plus.png"></a></td>
                          <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
	                    
	              </tr>

			            <%
			            if(ketMoldChangeRequestVO.getTotalCount() > 0) {
			            Vector moldEcrAttacheVec = new Vector();
			            //moldEcrAttacheVec = ContentUtil.getSecondaryContents(ketMoldChangeRequestVO.getKetMoldChangeRequest());
			                  ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)ketMoldChangeRequestVO.getKetMoldChangeRequest() );
			                  moldEcrAttacheVec = ContentUtil.getSecondaryContents(holder);
			            size = moldEcrAttacheVec.size();
			              if( moldEcrAttacheVec != null )
			              {
			                ContentInfo cntInfo = null;
			                ContentItem ctf = null;
			                String appDataOid = "";
			                String url = "";
			
			                for ( int j = 0 ; j<size; j++ ) {
			                      cntInfo = (ContentInfo)moldEcrAttacheVec.elementAt(j);
			                      ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
			                      appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
			
			                      //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
			                      url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
			                      url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
			            %>
	                      <tr height="27">
	                        <td class="tdwhiteM"><input type="checkbox" name="addFileSelect" value="<%=cntInfo.getContentOid()%>"></td>
	                        <td class="tdwhiteL0"><%=url%>&nbsp;</td>
	                      </tr>
			            <%
			                }
			                }
			            }
			            %>

	            </table>
	            <!-- /div>
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	              <tr>
	                <td class="space5"></td>
	              </tr>
	            </table -->
	          </td>
	        </tr>
	      </table>
	  </div>
	  
	      
    </td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>
</body>
</html>
