<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>

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
  // EJS TreeGrid End

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01066") %><%--금형부품 설계변경 목록--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>

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

  //사용자 검색 팝업  Open
  function selectUser(rname) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }
    acceptRoleMember(rname,attacheMembers);
  }

  function acceptRoleMember(rname, objArr) {
    if(objArr.length == 0) {
      return;
    }

    var displayName = document.getElementById(rname+'name');
    var keyName = document.getElementById(rname);

    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
      infoArr = objArr[i];
      displayName.value = infoArr[4];
      keyName.value = infoArr[0];
    }
  }

  //필드 값 삭제
  function deleteValue(param){
    document.getElementById(param).value = "";
  }

  //필드 값 초기화
  function deleteValueAll(){
    var form01 = document.forms[0];
    form01.partno.value = "";
    form01.predate.value = "";
    form01.postdate.value = "";

    // 결과내재검색 체크해제
    $("input:checkbox[id=searchInSearch]").attr("checked", false);
  }

  function perPage(v) {
      document.forms[0].perPage.value = v;

      search();
  }

  function search(){
    var form01 = document.forms[0];
    if(form01.partno.value == "")
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00145") %><%--Die No 는 필수 검색조건 입니다--%>");
      return;
    }
    showProcessing();
    disabledAllBtn();
    form01.cmd.value = 'search';
    form01.action =  '/plm/servlet/e3ps/MoldChangePlanServlet';
    form01.submit();
  }

  function excelDown()
  {
    var form01 = document.forms[0];
    if(form01.partno.value == "")
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00145") %><%--Die No 는 필수 검색조건 입니다--%>");
      return;
    }
//    showProcessing();
//    disabledAllBtn();
    form01.cmd.value = 'excel';
    form01.action =  '/plm/servlet/e3ps/MoldChangePlanServlet';
    form01.submit();
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

//]]>
</script>
</head>

<body>
<form name="form01" method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<!-- hidden end -->

<table style="width:780px; height:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table style="width:780px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table style="width:780px; height:28px;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01066") %><%--금형부품 설계변경 목록--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01065") %><%--금형부품 설계변경--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01066") %><%--금형부품 설계변경 목록--%></td>
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
      <table style="width:780px;" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                    <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                </td>
                <td style="width: 15px;">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteValueAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
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
          <td class="tdblueL" style="width:90px;">Die No.<span class="red">*</span></td>
          <td class="tdwhiteL" style="width:280px;"><input type="text" name="partno" value="<%=searchCondition.getString("partno") %>" class="txt_field" style="width:270px;"></td>
          <td class="tdblueL" style="width:110px;"><%=messageService.getString("e3ps.message.ket_message", "01521") %><%--변경일자--%></td>
          <td class="tdwhiteL0" style="width:280px;">
            <input type="text" id="predate" name="predate" value="<%=searchCondition.getString("predate") %>" class="txt_fieldRO" style="width:70px;" readonly>
            <img src="/plm/portal/images/icon_6.png" border="0" onclick="javascript:showCal(predate);" style="cursor:hand;" >
            <a href="javascript:clearDate('predate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            &nbsp;~&nbsp;
            <input type="text" id="postdate" name="postdate" value="<%=searchCondition.getString("postdate") %>" class="txt_fieldRO" style="width:70px;" readonly>
            <img src="/plm/portal/images/icon_6.png" border="0" onclick="javascript:showCal(postdate);" style="cursor:hand;">
            <a href="javascript:clearDate('postdate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:780px;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <!-- EJS TreeGrid Start -->
      <div class="content-main">
          <div class="container-fluid">
              <div class="row-fluid">
                  <div style="WIDTH:100%;HEIGHT:100%">
                      <bdo Debug="1" AlertError="0"
                          Layout_Url="/plm/jsp/ecm/MoldChangePlanGridLayout.jsp"
                          Layout_Param_Pagingsize="<%=pagingSize %>"
                          Data_Url="/plm/jsp/common/searchGridData.jsp"
                          Data_Method="POST"
                          Data_Param_Result="<%=tgData %>"
                          Data_Param_Pagingsize="<%=pagingSize %>"
                          Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                          Export_Data="TGData"
                          Export_Param_File="Mold_Change_Plan_List"
                      ></bdo>
                  </div>
              </div>
          </div>
      </div>
      <!-- EJS TreeGrid End -->

    </td>
  </tr>
  <tr>
    <td style="height:30px;" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" style="width:780px; height:24px;" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
