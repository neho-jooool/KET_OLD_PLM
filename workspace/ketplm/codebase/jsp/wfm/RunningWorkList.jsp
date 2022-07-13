<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="java.util.ArrayList"%>

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

  String predate = searchCondition.getString("predate");
  String postdate = searchCondition.getString("postdate");

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00787") %><%--결재진행함--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/jsp/wfm/viewPBO.js"></script>

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

function clearDate(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
}

function perPage(v) {
    document.form01.perPage.value = v;

    search();
}

function search(){
  var form01 = document.forms[0];
  var predate = form01.predate.value;
  var postdate = form01.postdate.value;

  if((predate!="") && (postdate!="")){
    if(predate>postdate){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02430") %><%--작성일자의 범위가 잘못 지정되어 있습니다--%>');
      form01.predate.value="";
      form01.postdate.value="";
      return;
    }
  }
  form01.method = "post";
  form01.action = "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchRunningAppr";
  form01.submit();
}

//]]>
</script>
</head>

<body>
<form name="form01" method="post">

<input type="hidden" name="perPage" value="<%=pagingSize %>" />

<table style="width:780px; height:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table style="width:780px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table style="width:780px; height:28px;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00787") %><%--결재진행함--%></td>
                <td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435") %><%--작업공간--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00787") %><%--결재진행함--%>
                </td>
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
      <table border="0" cellspacing="0" cellpadding="0" style="width:780px;">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:780px;">
        <tr>
          <td style="width:150px;" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
          <td class="tdwhiteL0">
            <input type="text" id="predate" name="predate" class="txt_fieldRO" style="width:200px;"  value="<%=predate %>" readOnly>
            &nbsp;<img src="/plm/portal/images/icon_6.png" border="0" onClick="javascript:showCal(predate)" style="cursor:hand;">
            &nbsp;<a href="javascript:clearDate('predate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            &nbsp;~&nbsp;
            <input type="text" id="postdate" name="postdate" class="txt_fieldRO" style="width:200px;" value="<%=postdate %>" readOnly>
            &nbsp;<img src="/plm/portal/images/icon_6.png" border="0" onClick="javascript:showCal(postdate)" style="cursor:hand;">
            &nbsp;<a href="javascript:clearDate('postdate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
          <td class="tdwhiteR0">
            <table border="0" align="right" cellpadding="0" cellspacing="0">
              <tr>
                <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table></td>
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
                                    Layout_Url="/plm/jsp/wfm/RunningWorkListGridLayout.jsp"
                                    Layout_Param_Pagingsize="<%=pagingSize %>"
                                    Data_Url="/plm/jsp/common/searchGridData.jsp"
                                    Data_Method="POST"
                                    Data_Param_Result="<%=tgData %>"
                                    Data_Param_Pagingsize="<%=pagingSize %>"
                                    Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                                    Export_Data="TGData"
                                    Export_Param_File="Running_Work_List"
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
