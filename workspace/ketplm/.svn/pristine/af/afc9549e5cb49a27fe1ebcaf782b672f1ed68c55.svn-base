<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page import="e3ps.ecm.entity.KETMoldChangeOrder" %>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrderVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLink" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECALinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldEcoEcrLinkVO" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.beans.EcmSearchHelper" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.Hashtable" %>
<%@page import="java.util.ArrayList" %>
<%@page import="wt.content.ContentHolder
                            ,wt.content.ContentHelper" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.KETObjectUtil" %>
<%@page import="e3ps.ecm.servlet.MoldEcoServlet" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.web.ParamUtil" %>
<%@page import="e3ps.ecm.beans.MoldEcoBeans" %>
<%@page import="wt.org.WTUser" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="ketMoldChangeOrderVO" class="e3ps.ecm.entity.KETMoldChangeOrderVO" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
Map<String, Object> parameter = new HashMap<String, Object>();
parameter.put("locale", messageService.getLocale());

MoldEcoBeans beans = new MoldEcoBeans();
Hashtable<String, ArrayList<Hashtable<String,String>>> codeHash = beans.getInitCodeList(parameter);
ArrayList<Hashtable<String, String>> divList = codeHash.get("division");
ArrayList<Hashtable<String, String>> chgReasonList = codeHash.get("changeReason");
ArrayList<Hashtable<String, String>> incProdType = codeHash.get("incProdType");

WTUser loginUser = KETObjectUtil.getLoginUser();
String loginUserOid = KETObjectUtil.getOidString(loginUser);


int size = 0;
String chkIncreaseProdType[] = new String[12];

int chgReasonListSize = (chgReasonList != null) ? chgReasonList.size() : 0;
String codeChangeReason[] = new String[chgReasonListSize];
String chkChangeReason[] = new String[chgReasonListSize];

String codeIncreaseProdType[] = new String[12];
boolean isCreate = true;
String fromPage = StringUtil.checkNull(request.getParameter("from"));
boolean isActivityCompleted = false;
boolean isApproved = false;

ContentHolder holder = null;

boolean isViewableState = false;

ArrayList arrChangeReason = null;

if(ketMoldChangeOrderVO.getTotalCount() > 0) {
    isCreate = false;

    if( fromPage.equals("") )
    {
        holder = ContentHelper.service.getContents( (ContentHolder)ketMoldChangeOrderVO.getKetMoldChangeOrder() );
    }

    if( fromPage.equals("prod") || fromPage.equals("ecr") )
    {
        isCreate = true;
    }
    if( StringUtil.checkNull(ketMoldChangeOrderVO.getProgressState()).equalsIgnoreCase("ACTIVITYCOMPLETED") )
    {
        isActivityCompleted = true;
    } else if( StringUtil.checkNull(ketMoldChangeOrderVO.getProgressState()).equalsIgnoreCase("APPROVED") )
    {
	    isApproved = true;
    }
    
    arrChangeReason = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getChangeReason(), "|");
    ArrayList arrIncreaseProdType = KETStringUtil.getToken(ketMoldChangeOrderVO.getKetMoldChangeOrder().getIncreaseProdType(), "|");
    int i = 0;
    int idx = 0;
    for(i=0; i<chgReasonListSize; i++) {
        chkChangeReason[i] = "";
        codeChangeReason[i] = "REASON_" + (i+1);
    }
    for(i=0; i<12; i++) {
        chkIncreaseProdType[i] = "";
        if( i+1 > 9 )
        {
            codeIncreaseProdType[i] ="INCR_" + (i+1);
        }
        else
        {
            codeIncreaseProdType[i] ="INCR_0" + (i+1);
        }
    }
    size = arrChangeReason.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrChangeReason.get(i), codeChangeReason);
        if(idx >= 0) {
            chkChangeReason[idx] = "checked";
        }
    }
    size = arrIncreaseProdType.size();
    for(i=0; i<size; i++) {
        idx = EcmUtil.getMatchArrIndex((String)arrIncreaseProdType.get(i), codeIncreaseProdType);
        if(idx >= 0) {
            chkIncreaseProdType[idx] = "checked";
        }
    }
}


// 변경 전
String webEditor = "";
String webEditorText = "";

// 변경 후
String webEditor1 = "";
String webEditorText1 = "";

if(ketMoldChangeOrderVO.getKetMoldChangeOrder() != null) {
    webEditor = StringUtils.stripToEmpty(((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditor()));
    webEditorText = StringUtils.stripToEmpty(((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditorText()));

    webEditor1 = StringUtils.stripToEmpty(((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditor1()));
    webEditorText1 = StringUtils.stripToEmpty(((String) ketMoldChangeOrderVO.getKetMoldChangeOrder().getWebEditorText1()));
}
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01034") %><%--금형ECO등록/수정--%></title>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<!-- script language=JavaScript src="/plm/jsp/ecm/CreateMoldEco.js"></script -->
<script language="javascript">
function clickTabBtn(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabActivity = document.getElementById("tabActivity");
    var tabEcn = document.getElementById("tabEcn");
    
    if(tabId == 1) {
        tabBasic.style.display = "block";
        tabActivity.style.display = "none";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 2;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabBasic';
        
    } else if(tabId == 2) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "block";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "block";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 2;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabActivity';
        
    } else if(tabId == 3) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "none";
        tabEcn.style.display = "block";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "block";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 2;
        
        document.forms[0].tabName.value = 'tabEcn';

      }
}

//초기화면세팅
function onLoad() {
  var form = document.forms[0];

  var stat = form.chkChangeReason[6].checked;
  var objName = "otherReasonDesc";
  setEtcValueStatus(objName, stat);

  stat = form.chkIncreaseProdType[11].checked;
  objName = "otherIncreaseProdType";
  setEtcValueStatus(objName, stat);

  clickTabBtn(1);
  
}

//기타입력항목 입력상태 처리
function setEtcValueStatus(objName, stat) {
  var obj = eval("document.forms[0]." + objName);
  if ( obj != undefined ) {
      if(stat) {
        obj.disabled = false;
      } else {
        obj.disabled = true;
      }
    }
}

//프로젝트상세조회 팝업
function viewProjectPopup(projectNo) {
  alert("프로젝트상세조회:" + projectNo);
}

//프로젝트상세조회 팝업
function clearProject() {
  var form = document.forms[0];
  form.projectOid.value = "";
  form.projectNo.value = "";
  form.projectName.value = "";
}

//자료 저장
function doSave( flag ){
  // flag : 활동완료이면 true, 아니면 false
  
  var url = "/plm/servlet/e3ps/MoldEcoServlet";
  var form = document.forms[0];

  //개발/양산구분
  if(form.rdoDevYn[0].checked) {
    form.devYn.value = form.rdoDevYn[0].value;
  } else {
    form.devYn.value = form.rdoDevYn[1].value;
  }

  //설계변경 사유
  var cnt = 0;
  form.changeReason.value = "";
  for(var i=0; i<form.chkChangeReason.length; i++) {
    if(form.chkChangeReason[i].checked) {
      cnt++;
      if(cnt < 2) {
        form.changeReason.value = form.chkChangeReason[i].value;
      } else {
        form.changeReason.value += "|" + form.chkChangeReason[i].value;
      }
    }
  }
  if(form.changeReason.value.indexOf("7") < 0) {
    form.otherReasonDesc.value = "";
  }

  //생산성 향상 유형
  cnt = 0;
  form.increaseProdType.value = "";
  for(var i=0; i<form.chkIncreaseProdType.length; i++) {
    if(form.chkIncreaseProdType[i].checked) {
      cnt++;
      if(cnt < 2) {
        form.increaseProdType.value = form.chkIncreaseProdType[i].value;
      } else {
        form.increaseProdType.value += "|" + form.chkIncreaseProdType[i].value;
      }

    }
  }
  if(form.increaseProdType.value.indexOf("12") < 0) {
    form.otherIncreaseProdType.value = "";
  } 
  

   if(checkValidate(flag) && check4MCharge()){

    
      // 이노디터 처리
      $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
      $('[name=webEditorText]').val(fnGetEditorText(0));

      // 이노디터 처리
      $('[name=webEditor1]').val(fnGetEditorHTMLCode(false, 1));
      $('[name=webEditorText1]').val(fnGetEditorText(1));

      
    if(form.oid.value == "") {
      form.cmd.value = "create";
    } else {
      form.cmd.value = "update";
    }

    if( flag )
    {
      // 활동완료 후 수정일 경우
      form.isCompleteModify.value ="Y";
      form.cmd.value = "completeModify";
    }
    else
    {
      form.isCompleteModify.value ="N";
    }

    form.target = "download";
    form.action = url;
    form.method = "post";
    form.encoding = 'multipart/form-data';

    disabledAllBtn();
    showProcessing();
    form.submit();
  } 
}

//금형 ECO 등록시 설계변경 사유가 고객요청 체크시 ECN 4M 담당자가 입력되었는지 체크
function check4MCharge(){
	var flag = true;
	$("[name=chkChangeReason]").each(function(){
		var value = $(this).val();
		if(value == "REASON_1"){
			if($(this).is(":checked")){
				var workerName = $("[name=relEcaDocWorkerName]").val();
				if(workerName == ""){
					alert("ECN탭에 4M 담당자를 지정하세요.");
					flag = false;
					return false;
				}
			}
		}
	});
	
	return flag;
}

function lfn_feedbackAfterSave(message) {
	alert(message);

    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    document.forms[0].cmd.value = "updateview";
    document.forms[0].target = "_self";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
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

//변경예정일 팝업
function popupCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  showCal(objName);
}

//변경예정일 초기화
function clearCal(objName, tableId){
  var pos = eval(tableId).clickedRowIndex;
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  eval("document.forms[0]." + objName).value = '';
}

//변경예정일 팝업
function popupEpmCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  showCal(objName);
}

//변경예정일 초기화
function clearEpmCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  eval("document.forms[0]." + objName).value = '';
}

//ECO담당자검색 팝업
function popupEcoUser(){
  SearchUtil.selectOneUser('','','setEcoUser');
}

//표준품 담당자 검색 팝업 리턴 포맷
function setEcoUser(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];
  form.ecoWorkerId.value = trArr[0];
  form.ecoWorkerName.value = trArr[4];
}

//ECO담당자 초기화
function clearEcoUser(){
  var form = document.forms[0];
  form.ecoWorkerId.value = '';
  form.ecoWorkerName.value = '';
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
	
  SearchUtil.selectPlan('prodVendor','prodVendorName');

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
function viewPart(v_poid){
	viewRelPart(v_poid);
}

function viewRelPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function checkBudget()
{
  var form = document.forms[0];
  var pos = relPartTable.clickedRowIndex;
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  var division  = "";
  var dev_yn = "";
  var dieno = "";
  var expectCost = "";

  if( form.divisionFlag.value == 'C' )
  {
    division = "1";
  }
  else
  {
    division = "2";
  }

  var dev_yn = document.all("rdoDevYn");
  var str_dev_yn = '';
  for( var inx=0; inx < dev_yn.length ; inx++)
  {
    if( document.forms[0].rdoDevYn[inx].checked )
    {
      str_dev_yn  = document.forms[0].rdoDevYn[inx].value;
    }
  }

  if( str_dev_yn == "D" )
  {
    dev_yn = "1";
  }
  else
  {
    dev_yn = "2";
  }

  if( tableRows > 1 )
  {
    dieno = form.relPartNumber[pos].value;
    expectCost = form.expectCost[pos].value;
    expectCost = expectCost.replace(",","");
  }
  else
  {
    dieno = form.relPartNumber.value;
    expectCost =form.expectCost.value;
    expectCost = expectCost.replace(",","");
  }

  /* 
  if( isNumber(expectCost) )
  {
  */
  
    showProcessing();
  
    document.forms[0].target="download";
    document.forms[0].action="/plm/jsp/ecm/BudgetInterfaceCheck.jsp?devYn="+dev_yn+"&division="+division+"&dieNo="+dieno+"&cost="+expectCost+"&rowInx="+pos;
    document.forms[0].submit();
  
  /* 
  }
  else
  {
      alert("비용이 존재하지 않아서 예산을 확인할 수 없습니다.");
      if( tableRows > 1 )
      {
        form.expectCost[pos].focus();
      }
      else
      {
        form.expectCost.focus();
      }

      return;
  }
  */
  
}

function setBudgetYn( budget_yn, row_inx, msg )
{
  //alert('in CreateMoldEco.js, '+  budget_yn +','+ row_inx +','+ msg );
  
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;
  
  tableRows -= 1;
  //row_inx -= 1;
  
  /* 
  alert(tableRows);
  alert(row_inx);
  */ 
  
  if( tableRows > 1 )
  {
      if( budget_yn == 'N' )
      {
          document.forms[0].budgetYnName[row_inx].value = '미확보';
      }
      else
      {
          document.forms[0].budgetYnName[row_inx].value = '확보';
      }

      document.forms[0].secureBudgetYn[row_inx].value = budget_yn;
      if( msg != '' )
      {
          alert(msg);
      }
  }
  else
  {
      if( budget_yn == 'N' )
      {
          document.forms[0].budgetYnName.value = '미확보';
      }
      else
      {
          document.forms[0].budgetYnName.value = '확보';
      }

      document.forms[0].secureBudgetYn.value = budget_yn;

      if( msg != '' )
      {
          alert(msg);
      }
  }
  
  hideProcessing();
}

</script>

<script language="javascript">
//자료 취소
function doCancel( flag ) {
  var url = "/plm/servlet/e3ps/MoldEcoServlet";
  var form = document.forms[0];

  if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") )
  {
    if( flag ) {
    	
      /* 
      //form.cmd.value = "cancel";
      form.target = "_self";
      form.action =  "/plm/jsp/ecm/SearchEcoForm.jsp";
      form.method = "post";
      form.encoding = 'multipart/form-data';
      disabledAllBtn();
      showProcessing();
      form.submit();
      //location.href="/plm/jsp/ecm/SearchEcoForm.jsp";
      */
      window.close();
      
    } else {
      history.back();
    }
  }
  else
  {
    return;
  }
}

//입력항목 체크
function checkValidate(flag)
{
  var form = document.forms[0];
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;

  tableRows -= 1;
  /*
  alert(tableRows);
  */
  
  var dev_yn = document.getElementsByName("rdoDevYn");
  var str_dev_yn = '';
  for( var inx=0; inx < dev_yn.length ; inx++)
  {
    if( dev_yn[inx].checked )
    {
      str_dev_yn  = dev_yn[inx].value;
    }
  }

  var chk_chg_reason = document.getElementsByName("chkChangeReason");
  var str_chk_chg_reason = '';
  for( var inx=0; inx < chk_chg_reason.length ; inx++)
  {
      if( chk_chg_reason[inx].checked )
      {
          str_chk_chg_reason  += chk_chg_reason[inx].value;
      }
  }
  
  
  if(isNullData(form.ecoName.value)) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>");
    clickTabBtn(1);
    form.ecoName.focus();
    return false;
  } else if( str_dev_yn == ''  ){
    alert("<%=messageService.getString("e3ps.message.ket_message", "01191") %><%--단계 구분을 선택하세요--%>");
    clickTabBtn(1);
    return false;
  } else if( str_dev_yn == 'D' && form.projectOid.value == ''   ){
    alert("<%=messageService.getString("e3ps.message.ket_message", "04930") %><%--개발단계가 \'양산\'이 아닐 경우 프로젝트 정보를 입력하셔야 합니다.--%>");
    clickTabBtn(1);
    popupProject();
    return false;
  } else if(isNullData(form.changeReason.value)) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "04750") %><%--설계변경사유를 선택하여 주십시오.--%>");
    clickTabBtn(1);
    chk_chg_reason[0].focus();
    return false;
  } else if( str_chk_chg_reason != '' && str_chk_chg_reason.indexOf('REASON_7') > -1 && form.otherReasonDesc.value == '' )
  {
    alert("설계변경 기타사유를 입력하여 주십시오");
    clickTabBtn(1);
    form.otherReasonDesc.focus();
    return false;
  } else if(tableRows < 1){
    alert("<%=messageService.getString("e3ps.message.ket_message", "00935") %><%--관련부품을 1개이상 입력하여 주십시오--%>");
    clickTabBtn(1);
    popupRelPart();
    return false;
  } else if(isNullData(form.ecoWorkerId.value)) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00188") %><%--ECO 담당자를 입력하여 주십시오--%>");
    clickTabBtn(1);
    return false;
  }


  var isPass = true;
  
  // 연계 제품ECO 정보 필수 추가
  // HEENEETODO : 초도배포시 반드시 제품ECO 필수
  <%-- 
  $('input[name=chkChangeReason]').each(function(i) {
        if($(this).val() == 'REASON_10' && $(this).is(':checked')) {
            
            $('input[name=chkChangeReason]').each(function(j) {
                if($(this).val() != 'REASON_10') {

                	var hasRelProdEco = 0;
                	  $("#RelProdEcoTable").find("input[name=relProdEcoOid]").each(function(k) {
                	      hasRelProdEco++;
                	      
                	  });
                	  if(hasRelProdEco == 0) {
                		  var spanRelEcoHtml = $('#spanRelEco').html();
                		  if(spanRelEcoHtml.lastIndexOf('*') > 0) {
                			    //alert('<%=messageService.getString("e3ps.message.ket_message", "04023") %>초도배포일경우 연계 제품ECO 정보를 반드시 추가하십시오.');
                			    if(confirm('연계 제품ECO 정보를 추가하시겠습니까?')) {
                			        popupRelProdEco();
                			        isPass = false;
                			        return false;
                			    } else {
                			    	return false;
                			    }
                			    
                		  } else {
                			  // Do nothing..!!
                		  }
                          
                	  }
                	  
                } 
            });
            
            if(!isPass) return false;
        } 
        
        if(!isPass) return false;
  });
  --%>

  if(!isPass) return false;
  
  
  //표준품 입력항목 체크
  if(true)//if( !flag )
  {
	  
	    $("#relDocTable").find("[name=relEcaDocOid]").each(function(i) {
	        if($(this).val() == '') {
	        	alert("<%=messageService.getString("e3ps.message.ket_message", "03020") %><%--표준품을 입력하세요--%>");
	        	isPass = false;
	        	return;
	        }
	        
	    });
	    
	    if(isPass) {
		    $("#relDocTable").find("[name=relEcaDocWorkerOid]").each(function(i) {
	            if($(this).val() == '') {
	            	alert("<%=messageService.getString("e3ps.message.ket_message", "03018") %><%--표준품 담당자를 입력하세요--%>");
	            	isPass = false;
	            	return;
	            }
	            
	        });
	    
	    }
	    
	    if(!isPass) return false;
	    
	    
	    // 변경계획일
	    var now = new Date();
        var year= now.getFullYear();
        var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
        var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
	    $("#relEpmTable").find("[name=relEcaEpmPlanDate]").each(function(i) {
            var relEcaEpmPlanDate = $(this).val();
            if(relEcaEpmPlanDate != '') {
	            var toDate = year +"-"+ mon +"-"+ day;
	            
	            if(relEcaEpmPlanDate < toDate) {
	            	alert('<%=messageService.getString("e3ps.message.ket_message", "04060") %><%--변경계획일은 오늘 이전날짜를 기입할 수 없습니다.--%>');
	            	isPass = false;
	                return false;
	            }
            }
        });
	    
	    if(!isPass) return false;
	    
	    
	<%--     
    var relDocTable = document.getElementById("relDocTable");
    var relDocTableRows = relDocTable.rows.length;
    
    relDocTableRows -= 1;
    /* 
    alert(relDocTableRows);
    */
    
    var workerCnt = 0;
    var docCnt = 0;
    if(relDocTableRows > 0) {
      if(relDocTableRows > 1) {
        for(var i = 0; i < relDocTableRows; i++) {
          if(form.relEcaDocWorkerOid[i].value != "") {
            workerCnt++;
          }
          if(form.relEcaDocOid[i].value != "") {
            docCnt++;
          }
        }
      }else{
        if(form.relEcaDocWorkerOid.value != "") {
          workerCnt++;
        }
        if(form.relEcaDocOid.value != "") {
          docCnt++;
        }
      }
      
      if(docCnt != relDocTableRows) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "03020") %>표준품을 입력하세요");
        return false;
      }
      if(workerCnt != relDocTableRows) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "03018") %>표준품 담당자를 입력하세요");
        return false;
      }
      
    }
    --%>
    
  }
  return true;
}

//(공통)선택자료삭제
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

<% /* deprecated */ %>
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

    if(body.rows.length == 1) {
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

function deleteDataLineMinus1(formName, tableElementId, checkboxName, allCheckName, listVarName) {
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

    var size = body.rows.length - 1;
    var curRow = 0;
    if(size == 1) {
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
        body.deleteRow(1);
      }
    } else {
      for (var i = body.rows.length; i > 1; i--) {
        curRow = i - 2;
        if (objChecks[curRow].checked) {
          if(listVal == "") {
            listVal = objChecks[curRow].value;
          } else {
            listVal += "*" + objChecks[curRow].value;
          }
          body.deleteRow(i - 1);
        }
      }
    }
    if (size < 1) {
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

//부품상세조회 팝업에서 그 창이 닫힐때 호출되는 Function
function lfn_feedbackAfterPopupViewPart() {
    try {
        hideProcessing();
        enabledAllBtn();
    } catch(e) {}
}

</script>

</head>
<body onload="javascript:onLoad();">
<form method="post" action="/plm/servlet/e3ps/MoldEcoServlet">
<input type="hidden" name="cmd" value="create">
<input type="hidden" name="oid" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0) { out.print(ketMoldChangeOrderVO.getOid());}%>">
<input type="hidden" name="ecoId" value="<%if(ketMoldChangeOrderVO.getTotalCount() > 0 && ketMoldChangeOrderVO.getKetMoldChangeOrder() != null) { out.print(ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoId());}%>">
<input type="hidden" name="devYn" value="">
<input type="hidden" name="changeReason" value="">
<input type="hidden" name="increaseProdType" value="">
<input type="hidden" name="deleteFileList" value="">
<input type="hidden" name="deleteRelEcrList" value="">
<input type="hidden" name="deleteRelProdEcoList" value="">
<input type="hidden" name="deleteRelPartList" value="">
<input type="hidden" name="deleteRelBomList" value="">
<input type="hidden" name="deleteRelEpmList" value="">
<input type="hidden" name="deleteRelDocList" value="">
<input type="hidden" name="isToDo" value="N">
<input type='hidden'  name="isCompleteModify">
<input type='hidden'  name="prePage">
<input type="hidden" name="tabName" value="">

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
<input type="hidden" name="srchecoId" value="<%=ParamUtil.getParameter(request, "srchecoId")%>">
<input type="hidden" name="srchdivisionFlag" value="<%=ParamUtil.getParameter(request, "srchdivisionFlag")%>">
<input type="hidden" name="srchdevYn" value="<%=ParamUtil.getParameter(request, "srchdevYn")%>">
<input type="hidden" name="srchsancStateFlag" value="<%=ParamUtil.getParameter(request, "srchsancStateFlag")%>">
<input type="hidden" name="srchecoName" value="<%=ParamUtil.getParameter(request, "srchecoName")%>">
<input type="hidden" name="srchprodMoldCls" value="<%=ParamUtil.getParameter(request, "srchprodMoldCls")%>">
<input type="hidden" name="srchcreateStartDate" value="<%=ParamUtil.getParameter(request, "srchcreateStartDate")%>">
<input type="hidden" name="srchcreateEndDate" value="<%=ParamUtil.getParameter(request, "srchcreateEndDate")%>">

<!-- 프로젝트에서 산출물로 ECO 직접작성 -->
<!-- input type="hidden" name="projectOid" value="" -->
<input type="hidden" name="projectOutputOid" value="">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%if( isCreate ){ %><%=messageService.getString("e3ps.message.ket_message", "01000") %><%--금형 ECO 등록--%><%}else{ %>금형ECO 수정<%} %></td>
                  <!-- td align="right">
                       <img src="/plm/portal/images/icon_navi.gif">Home
                       <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                       <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                       <img src="/plm/portal/images/icon_navi.gif"><%if(isCreate){%><%=messageService.getString("e3ps.message.ket_message", "01000") %><%--금형 ECO 등록--%><%}else{ %>금형ECO 수정<%} %>
                </tr -->
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>

      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">&nbsp;</td>
            <td class="font_03">
              <table id="infoShow" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>   
                </tr>
              </table>
              <table id="infoHide" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>   
                </tr>
              </table>
              <table id="infoEcn" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                                       
                </tr>
                </table>              
            </td>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave(<%=isActivityCompleted%>);" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel(<%=isCreate %>);" class="btn_blue" onfocus="this.blur();">
                            <%
                            if(ketMoldChangeOrderVO.getKetMoldChangeOrder() == null || ketMoldChangeOrderVO.getKetMoldChangeOrder().getEcoId() == null) { 
                            %>
                              <%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>
                            <%
                            } else { 
                            %>
                              <%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%>
                            <%
                            }
                            %>  
                            </a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
      </table>
      
      <table width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">
            <!-- table width="100%" height="100%" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">&nbsp;</td>
              </tr>
            </table -->
            
            <!--내용-->
            <div style="position:; width:100%; height:670px; overflow-x:auto; overflow-y:auto;">
            <div id="tabBasic" style="position:; display:none; z-index:2; ">

                <%@include file="/jsp/ecm/CreateMoldEcoBasicForm.jsp" %>

            </div>
            <div id="tabActivity" style="position:; display:none; z-index:1; ">
                
              <%
              if( !isActivityCompleted ){
              %>
                <%@include file="/jsp/ecm/CreateMoldEcoActivityForm.jsp" %>
              <%
              } else {
              %>
                <%@include file="/jsp/ecm/ViewMoldEcoActivityForm.jsp" %>
              <%
              }
              %>
              
            </div>
            <div id="tabEcn" style="position:; display:none; z-index:1; ">
              
              <%
              if( isApproved ){
              %>
                <%@include file="/jsp/ecm/reform/ViewMoldEcnForm.jsp" %>
              <%
              } else {
              %>
                <%@include file="/jsp/ecm/reform/ModifyMoldEcnForm.jsp" %>
              <%
              }
              %>
              
            </div>  
            </div>             

          </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table></td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</body>
</html>
