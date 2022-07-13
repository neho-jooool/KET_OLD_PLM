<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.ArrayList,
                java.util.Hashtable"%>
<%@ page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="e3ps.groupware.board.beans.MyTestOption"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

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
    // EJS TreeGrid End

  String partYazaki = searchCondition.getString("partYazaki");
  String partType = searchCondition.getString("partType");
  String inputPartNos = searchCondition.getString("inputPartNos");

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/jsp/bom/js/part_js.jsp"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
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

  function validation(){
    var form01 = document.forms[0];
    // 검색조건 미입력 validation
    if (form01.number.value == "" && form01.name.value == "" && form01.inputPartNos.value == "") {
      alert('<%=messageService.getString("e3ps.message.ket_message", "03135") %><%--하나 이상의 검색조건을 입력해 주십시오--%>');
      return false;
    }

    // 부품번호 검색조건 입력 시, 자릿수 체크
    if ( form01.number.value != "" ) {
      var numberOnly = form01.number.value.split("*").join("");
      if (numberOnly.length < 3) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00712") %><%--검색조건은 '*'를 제외하고 3자리 이상입니다--%>");
        return false;
      }
    }

  // 부품명 검색조건 입력 시, 자릿수 체크
    if ( form01.name.value != "" ) {
      if (form01.name.value.indexOf("*") == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00711") %><%--검색조건은 '*'로 시작할 수 없습니다--%>");
        form01.name.value = "";
        return false;
      }

      var nameOnly = form01.name.value.split("*").join("");
      if (nameOnly.length < 3) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00712") %><%--검색조건은 '*'를 제외하고 3자리 이상입니다--%>");
        return false;
      }
    }
    return true;
  }

  function perPage(v) {
      document.forms[0].perPage.value = v;

      search();
  }

  // 검색
  function search(){

    if (!validation()) {
      return;
    }

    var form01 = document.forms[0];

    if(form01.partYazaki.checked){
      form01.partYazaki.value = "Y";
    }

    disabledAllBtn();
    showProcessing();

    form01.cmd.value="search";
    form01.action = "/plm/servlet/e3ps/PartServlet";
    form01.target = "_self";
    form01.submit();

  }

  // 초기화
  function deleteValue() {
    var form01 = document.forms[0];
    document.getElementsByName("partType")[0].checked = true;
    form01.partYazaki.checked = false;

    // 결과내재검색 체크해제
    $("input:checkbox[id=searchInSearch]").attr("checked", false);

    form01.number.value = "";
    form01.name.value = "";
    clearPartNumber();
  }

  // Excel 출력
  function excelDown(){
    var form01 = document.forms[0];

    if(form01.partYazaki.checked){
      form01.partYazaki.value = "Y";
    }

    form01.cmd.value = "searchExcel";
    form01.target = "download";
    form01.method = "post";
    form01.action = "/plm/servlet/e3ps/PartServlet";

    disabledAllBtn();
    showProcessing();
    form01.submit();
    enabledAllBtn();
    hideProcessing();
  }

  //처리중 화면 전환
  function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
  }

  // 부품 상세조회 화면 호출
  function viewPart(v_poid){

    var url = "/plm/jsp/bom/ViewPart.jsp?poid=wt.part.WTPart:" + v_poid;
 //   openWindow(url,"","750","650","scrollbars=no,resizable=no,top=200,left=250");
    // 2014.06.12 tklee IE 11에서 잘림
    // 사이즈 조절함    
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
  }

  var winPop;

  // 부품검색 팝업 호출
  function serachPartPopup(){
    document.forms[0].modal.value = "N";
    var url = "/plm/ext/part/base/listPartPopup.do?mode=one&modal=N";
    openWindow(url,"","1024","768","scrollbars=yes,resizable=no,top=200,left=250");
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  // 팝업창에서 선택된 부품 목록을 받아 화면에 display 하는 함수
  //
  // arrayData[inx][jnx]
  //  :: inx :: 부품검색 팝업에서 선택한 부품의 갯수
  //  :: jnx :: 0(oid), 1(부품번호), 2(부품명), 3(부품버전), 4(부품유형: 'P'제품, 'D':DieNo, 'M':금형부품)
  // , 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'P', '포장재', 'S', '스크랩', 'W', '상품','제품'('P'-DB 없음)
  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  function selectedPart(arrayData){

    // 멀티선택
    //var strTemp ="";
    //for( var inx=0; inx < arrayData.length ; inx++ ){
    //  strTemp += arrayData[inx][1] + ",";
  //}
  //document.forms[0].number.value = strTemp;

  // 단일선택
  document.forms[0].number.value = arrayData[0][1];

    // 팝업창 닫기
  //if( winPop != null ) winPop.close();
  //winPop = null;
  }

  // SAP Interface 테스트
  function sap(){

    var url = "/plm/jsp/bom/sapInterfaceTest.jsp";
    openWindow(url,"","600","400","scrollbars=yes,resizable=no,top=200,left=450");
  }

  // SAP Interface 테스트
  function logResult(){

    var url = "/plm/jsp/bom/sapInterfaceTest.jsp";
    openWindow(url,"","600","400","scrollbars=yes,resizable=no,top=200,left=450");
  }

  // SAP Interface 테스트
  function updateMaster(){

    var url = "/plm/jsp/bom/sapInterfaceTest.jsp";
    openWindow(url,"","600","400","scrollbars=yes,resizable=no,top=200,left=450");
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
    initPartNumberTextbox();
  }

//]]>
</script>
</head>

<body>
<form name="form01" method="post">
<!-- hidden -->
<input type="hidden" name="mode">
<input type="hidden" name="modal">
<input type="hidden" name="poid">
<input type="hidden" name="cmd" value="search">
<input type="hidden" id="inputPartNos" name="inputPartNos" value="<%=inputPartNos %>">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<!-- hidden end -->

<table style="width:780px; height:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table style="width:780px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table style="width:780px; height:28px;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif">BOM<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table style="width:780px;" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <!-- START :: SAP Interface 테스트를 위한 임시 버튼 -->
              <!--td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:updateMaster();" class="btn_blue">테스트</a></td>
                  <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td style="width:5px;">&nbsp;</td>
              <!-- END ::  SAP Interface 테스트를 위한 임시 버튼 -->
              <td>
                  <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
              </td>
              <td style="width: 15px;">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteValue();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                  <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
              </table></td>
              <td style="width:5px;">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                  <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:780px;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:780px;">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:780px;">
        <tr>
          <td class="tdblueL" style="width:100px;"><%=messageService.getString("e3ps.message.ket_message", "01595") %><%--부품유형--%></td>
          <td class="tdwhiteL0" colspan="3">
            <input name="partType" type="radio" class="Checkbox" value="A" checked><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>&nbsp;&nbsp;
            <input name="partType" type="radio" class="Checkbox" value="P" <%=("P".equals(partType))?"checked":"" %>><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%>&nbsp;&nbsp;
            <input name="partType" type="radio" class="Checkbox" value="D" <%=("D".equals(partType))?"checked":"" %>>Die No&nbsp;&nbsp;
            <input name="partType" type="radio" class="Checkbox" value="M" <%=("M".equals(partType))?"checked":"" %>><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%>
            <input id="partYazaki" name="partYazaki" type="checkbox" class="Checkbox" value="" <% if( "Y".equals(partYazaki) ) { %> Checked <% } %>>YAZAKI
          </td>
        </tr>
        <tr>
          <td style="width:100px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td style="width:290px;" class="tdwhiteL">
            <input type="text" id="number" name="number" value="<%=searchCondition.getString("number") %>" class="txt_field" style="width:220px;">
            <input type="text" id="numbertemp" name="numbertemp" class="txt_fieldRO" readonly style="width:220px; display:none;">
            &nbsp;<a href="javascript:;" onClick="javascript:inputPartNumberPopup();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
            &nbsp;<a href="javascript:;" onClick="javascript:clearPartNumber();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
          <!-- 부품검색 팝업 아이콘 주석처리 -->
          <!-- td style="width:290px;" class="tdwhiteL"><input type="text" name="number" class="txt_field" style="width:250">
            &nbsp;<a href="javascript:serachPartPopup();"><img src="/plm/portal/images/icon_5.png" border="0"></a></td -->
          <td style="width:100px;" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td style="width:290px;" class="tdwhiteL0">
            <input type="text" id="name" name="name" value="<%=searchCondition.getString("name") %>" class="txt_field" style="width:290px;">
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:780px;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

        <!--  EJS TreeGrid Start -->
    <div class="content-main">
        <div class="container-fluid">
            <div class="row-fluid">
                <div style="WIDTH:100%;HEIGHT:100%">
                    <bdo Debug="1" AlertError="0"
                        Layout_Url="/plm/jsp/bom/SearchPartGridLayout.jsp"
                        Layout_Param_Pagingsize="<%=pagingSize %>"
                        Data_Url="/plm/jsp/common/searchGridData.jsp"
                        Data_Method="POST"
                        Data_Param_Result="<%=tgData %>"
                        Data_Param_Pagingsize="<%=pagingSize %>"
                        Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                        Export_Data="TGData"
                        Export_Param_File="Search_Part_List"
                    ></bdo>
                </div>
            </div>
        </div>
    </div>
        <!--  EJS TreeGrid End -->

  </td>
  </tr>
  <tr>
    <td style="height:30px;" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" style="width:780px; height:24px;" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>

</form>
</body>
</html>
