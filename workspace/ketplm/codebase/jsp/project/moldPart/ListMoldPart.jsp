<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.fc.PagingQueryResult"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.groupware.board.beans.MyTestOption"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />

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
  // EJS TreeGrid End

  String dieNo  = searchCondition.getString("dieNo");
  String partUser = searchCondition.getString("partUser");
  String partUserName = searchCondition.getString("partUserName");
  String creator  = searchCondition.getString("creator");
  String creatorName = searchCondition.getString("creatorName");
  String createDateS = searchCondition.getString("createDateS");
  String createDateE = searchCondition.getString("createDateE");
  String moldDateS = searchCondition.getString("moldDateS");
  String moldDateE = searchCondition.getString("moldDateE");
  String planDateS = searchCondition.getString("planDateS");
  String planDateE = searchCondition.getString("planDateE");
  String createType = searchCondition.getString("createType");
  String[] levelTypeAry = searchCondition.getStringArray("levelType");
  String[] moldStateAry = searchCondition.getStringArray("moldState");

  // 4M완료여부
  String hasEcaCompleteDate = searchCondition.getString("hasEcaCompleteDate");
  
  Map<String, Object> parameter = new HashMap<String, Object>();
  parameter.put("locale",   messageService.getLocale());
  parameter.put("codeType", "MOLDPARTLEVELTYPE");
  List<Map<String, Object>> levelTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

  parameter.clear();
  parameter.put("locale",   messageService.getLocale());
  parameter.put("codeType", "MOLDPARTSTATE");
  List<Map<String, Object>> moldStateNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01069") %><%--금형부품관리 검색--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/jsp/groupware/js/board.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
</style>

<script type="text/javascript">
//<![CDATA[

    function perPage(v) {
        document.forms[0].perPage.value = v;

        search();
    }

	function search() {
	    var form01 = document.forms[0];
	
	//    onProgressBar();
	//    form01.command.value='search';
	//    form01.sessionid.value ="0";
	//    form01.tpage.value = "";
	
// 	   if(form01.sessionid != null){
// 	      form01.sessionid.value = "";
// 	   }
// 	   if(form01.page != null){
// 	      form01.page.value = "";
// 	   }
	
// 	   showProcessing();
// 	   disabledAllBtn();
// 	   form01.method = "post";
// 	   form01.command.value = "search";
// 	   form01.action = "/plm/servlet/e3ps/MoldPartSearchServlet";
// 	   form01.submit();
  
	   loadEjsGrid();
	}
	
	// function 추가
	function loadEjsGrid(){
	    try{
	       var idx = 0;
	       var D = Grids[idx].Data;
	       var formName = "form01";    //ProjectSearch 
	
	       D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
	
	       D.Data.Url = "/plm/servlet/e3ps/MoldPartSearchServlet"; 
	       D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
	       D.Data.Param.command = "searchGridData";
	
	       D.Page.Url = "/plm/servlet/e3ps/MoldPartSearchServlet";
	       D.Page.Params =  D.Data.Params;
	       D.Page.Param.command = "searchGridPage";
	
	       Grids[idx].Reload(D);
	
	       var S = document.getElementById("Status"+idx);
	       if(S) S.innerHTML="Loading";
	
	    }catch(e){
	        alert(e.message);
	    }
	}
  
  

  function goViewPage(projectOid, managerOid){
	  
    //location.href="/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid=" + projectOid + "&managerOid=" + managerOid;
	getOpenWindow2('/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid=' + projectOid + '&managerOid=' + managerOid, projectOid, 1024, 768, "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=1,resizable=0");
    
    
  }

  function sorting(key){
    var form01 = document.forms[0];
    
    form01.method = "post";
    form01.action = "/plm/servlet/e3ps/MoldPartSearchServlet";

    if(form01.sessionid != null){
      form01.sessionid.value = "";
      form01.page.value = "";
    }

    if(key == form01.sortKey.value){
      if(form01.dsc.value == "false"){

        form01.dsc.value = "true";

      }else{
        form01.dsc.value = "false";
      }
    }else{
      form01.dsc.value = "false";
    }
    form01.sortKey.value = key;
    form01.submit();
  }

  function excelDown(){
    var form01 = document.forms[0];

    if(form01.sessionid != null){
      form01.sessionid.value = "";
    }
        if(form01.page != null){
        form01.page.value = "";
        }
    form01.method = "post";
    form01.command.value = "excelDown";
    form01.action = "/plm/servlet/e3ps/MoldPartSearchServlet";
    form01.submit();
  }

  function dataReset(){
    var form01 = document.forms[0];

    //form01.reset();
    form01.dieNo.value = "";
    form01.createDateS.value = "";
    form01.createDateE.value = "";
    form01.moldDateS.value = "";
    form01.moldDateE.value = "";
    //form01.createType.value = "";
    //form01.levelType.value = "";
    //form01.moldState.value = "";
    form01.planDateS.value = "";
    form01.planDateE.value = "";

    // 4M완료여부
    $("input:checkbox[id='hasEcaCompleteDate']").attr("checked", false); 
    
    
    clearUser('partUser');

    $("#levelType").multiselect("uncheckAll");
    $("#moldState").multiselect("uncheckAll");

    // 결과내재검색 체크해제
    $("input:checkbox[id=searchInSearch]").attr("checked", false);
  }

  function delMember(rname){
    document.getElementById(rname).value = "";
    document.getElementById(rname + "Name").value = "";
  }

  /*****  날짜 check 시작  *******/

  function checkDate() {
    var cstartdate = document.getElementById('start' + i).value;
    var cenddate = document.getElementById('end' + i).value;

    if(document.getElementById('tr' + i).style.display == 'block'){
      if(cstartdate.length == 0 || cenddate.length == 0){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02347") %><%--일자를 입력해주세요--%>');
        return false;
      }
    }
    if(cstartdate.length > 0 && cenddate.length > 0) {
      if(cstartdate > cenddate) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02660") %><%--종료일자가 시작일자보다 늦습니다 다시 입력해 주시기 바랍니다--%>');
        document.getElementById('start'+i).value = "";
        document.getElementById('end'+i).value = "";
        return false
      }
    }
    return true;
  }
  //문자열 일괄전환 함수
  function funcReplaceStrAll(org_str, find_str, replace_str) {
    var pos = org_str.indexOf(find_str);
    while(pos != -1) {
        pre_str  = org_str.substring(0, pos);
        post_str = org_str.substring(pos + find_str.length, org_str.length);
        org_str  = pre_str + replace_str + post_str;
        pos = org_str.indexOf(find_str);
    }
    return org_str;
  }

  //*******************************************************************
  //년월 입력시 마지막 일자
  function  getEndOfMonthDay( yy, mm ) {
    var max_days=0;
    if(mm == 1) {
        max_days = 31 ;
    } else if(mm == 2) {
        if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))  max_days = 29;
        else                                                         max_days = 28;
    }
    else if (mm == 3)   max_days = 31;
    else if (mm == 4)   max_days = 30;
    else if (mm == 5)   max_days = 31;
    else if (mm == 6)   max_days = 30;
    else if (mm == 7)   max_days = 31;
    else if (mm == 8)   max_days = 31;
    else if (mm == 9)   max_days = 30;
    else if (mm == 10)  max_days = 31;
    else if (mm == 11)  max_days = 30;
    else if (mm == 12)  max_days = 31;
    else                return '';

    return max_days;
  }

  function isValidDate(obj, maxLength) {
    var retVal = true;
    var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
    //document.forms[0].duration.value = "";

    if(obj.value == "") {
      return;
    }

    val=obj.value;
    re=/[^0-9]/gi;
    obj.value=val.replace(re,"");

    var inputDate = funcReplaceStrAll(obj.value,  '년', '');
    inputDate     = funcReplaceStrAll(inputDate,  '월', '');
    inputDate     = funcReplaceStrAll(inputDate,  '일', '');

    var yyyy = inputDate.substring(0, 4);
    var mm   = (maxLength >= 6)?inputDate.substring(4, 6):"01";
    var dd   = (maxLength == 8)?inputDate.substring(6, 8):"01";

    if (isNaN(yyyy) || parseInt(yyyy) < 1000) return viewErrMsg(obj, msg);
    if (isNaN(mm) || parseFloat(mm) > 12 || parseFloat(mm) < 1) return viewErrMsg(obj, msg);
    if (isNaN(dd) || parseFloat(dd) < 1 || (parseFloat(dd) > getEndOfMonthDay(parseFloat(yyyy.substring(2,4)), parseFloat(mm))) ) return viewErrMsg(obj, msg);

    if(inputDate.length == 8) {
      inputDate = inputDate.substring(0, 8); //미봉책
    }else{
      alert('<%=messageService.getString("e3ps.message.ket_message", "02383") %><%--입력된 값이 8자리 숫자가 아닙니다 8자리 숫자를 입력해주세요--%>');
      return;
    }

    obj.value = yyyy+ "-" +mm+ "-" +dd;
    return true;
  }

  function viewErrMsg(obj,msg) {
    alert(msg);
  }
  /*****  날짜 check 끝 *******/
  
  function create(){
	  getOpenWindow2('/plm/jsp/project/moldPart/CreateMoldPart.jsp', 'MOLD_PART_POPUP', 800, 600, "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=1,resizable=0");
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

      $("#levelType").multiselect({
          minWidth: 180,
          noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
          checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
          uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
      });
      $("#moldState").multiselect({
    	  minWidth: 180,
          noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
          checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
          uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
      });
      //사용자 suggest
      SuggestUtil.bind('USER', 'partUserName', 'partUser');
      //사용자 suggest
      SuggestUtil.bind('DIENO', 'dieNo');
    
	  // Calener field 설정
	  CalendarUtil.dateInputFormat('createDateS');
	  CalendarUtil.dateInputFormat('createDateE');
	  CalendarUtil.dateInputFormat('planDateS');
	  CalendarUtil.dateInputFormat('planDateE');
	  CalendarUtil.dateInputFormat('moldDateS');
	  CalendarUtil.dateInputFormat('moldDateE');
	    
	  treeGridResize("#ListMoldPart","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
  });

//]]>
</script>
</head>

<body class="body-space">

<form name="form01" method="post">

<input type="hidden" name="command" value="">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />

<table style="width:100%; height:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <table style="width: 100%; height: 28px;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                    <td style="width: 20px;">
                        <img src="/plm/portal/images/icon_3.png">
                    </td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01068") %><%--금형부품관리--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01068") %><%--금형부품관리--%>
                    </td>
              </tr>
                </table>
            </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table style="width:100%;" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
            <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <!-- td>
                    <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                </td -->
                <td style="width: 15px;">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:create();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01071") %><%--금형부품관리 등록--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                        </table>
                    </td>
                <td style="width:5px;">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:dataReset();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                        </table>
                    </td>
                <td style="width:5px;">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                        </table>
                    </td>
              </tr>
                </table>
            </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <!-- 검색영역 collapse/expand -->
      <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <tr>
          <td style="width:110px;" class="tdblueL">Die No</td>
          <td class="tdwhiteL"><input type="text" name="dieNo" class="txt_field"  style="width:94%" id="dieNo" value="<%=dieNo%>"></td>
          <td style="width:110px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01583") %><%--부품공정--%></td>
          <td class="tdwhiteL">
            <input type="text" id="partUserName" name="partUserName" class="txt_field"  style="width:70%;" value="<%=partUserName%>">
            <input type="hidden" id="partUser" name="partUser" class="txt_field" value="" value="<%=partUser%>">
            <a href="javascript:selectUserOid('partUser');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
            &nbsp;<a href="javascript:clearUser('partUser');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
          <td style="width:110px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
          <td class="tdwhiteL0">
            <!-- MultiCombo -->
            <select id="levelType" name="levelType" class="fm_jmp" multiple="multiple">
                <%
                for ( int i=0; i<levelTypeNumCode.size(); i++ ) {
                %>
                    <option value="<%=levelTypeNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(levelTypeAry, levelTypeNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=levelTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
            </select>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
          <td class="tdwhiteL" colspan="3">
            <input type="text" id="createDateS" name="createDateS" class="txt_field" style="width:70px;" onChange="isValidDate(this, 8);" value="<%=createDateS%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createDateS');" style="cursor: hand;">
            ~
            <input type="text" id="createDateE" name="createDateE" class="txt_field" style="width:70px;" onChange="isValidDate(this, 8);" value="<%=createDateE%>">
           <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createDateE');" style="cursor: hand;">
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td class="tdwhiteL0" colspan="3">
            <!-- MultiCombo -->
            <select id="moldState" name="moldState" class="fm_jmp" multiple="multiple">
                <%
                for ( int i=0; i<moldStateNumCode.size(); i++ ) {
                %>
                    <option value="<%=moldStateNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(moldStateAry, moldStateNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=moldStateNumCode.get(i).get("value")%></option>
                <%
                }
                %>
            </select>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01594") %><%--부품완료 예정일--%></td>
          <td class="tdwhiteL" colspan="3">
            <input type="text" id="planDateS" name="planDateS" class="txt_field" style="width:70px;" onChange="isValidDate(this, 8);" value="<%=planDateS%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('planDateS');" style="cursor: hand;">
            ~
            <input type="text" id="planDateE" name="planDateE" class="txt_field" style="width:70px;" onChange="isValidDate(this, 8);" value="<%=planDateE%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('planDateE');" style="cursor: hand;">
          </td>
          <!-- td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04005") %><%--4M완료여부--%></td>
          <td class="tdwhiteL0"><input type="checkbox" name="hasEcaCompleteDate" id="hasEcaCompleteDate" value="<%=hasEcaCompleteDate %>" style="width:99%"></td -->
          <td class="tdwhiteL0" colspan="2">&nbsp;</td>
        </tr>        
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02880") %><%--출도일--%></td>
          <td class="tdwhiteL" colspan="3">
            <input type="text" id="moldDateS" name="moldDateS" class="txt_field"  style="width:70px;" onChange="isValidDate(this, 8);" value="<%=moldDateS%>" >
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('moldDateS');" style="cursor: hand;">
            ~
            <input type="text" id="moldDateE" name="moldDateE" class="txt_field"  style="width:70px;" onChange="isValidDate(this, 8);" value="<%=moldDateE%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('moldDateE');" style="cursor: hand;">
          </td>
          <td class="tdwhiteL0" colspan="2">&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/moldPart/ListMoldPartGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp?sortKey=DieNo" 
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Project_List" 
                            Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp" 
                            Page_Format="Internal" 
                            Page_Data="TGData" 
                            Page_Method="POST" 
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->

      </td>
  </tr>
  <tr>
    <td style="height:30px;" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" style="width:100%; height:24px;" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
