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

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00760") %><%--결재대기함--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/common.js"></script>

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

function perPage(v) {
    document.form01.perPage.value = v;

    search();
}

function search(){
  var form01 = document.forms[0];
  form01.method = "post";
  form01.action = "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
  form01.submit();
}

//]]>
</script>
</head>

<body>
<form name="form01" method="post">

<input type="hidden" name="perPage" value="<%=pagingSize %>" />

<table style="width:780px;" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table style="width:780px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table style="width:780px;" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00760") %><%--결재대기함--%></td>
                <td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435") %><%--작업공간--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00760") %><%--결재대기함--%>
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
                <!-- EJS TreeGrid Start -->
                <div class="content-main">
                    <div class="container-fluid">
                        <div class="row-fluid">
                            <div style="WIDTH:100%;HEIGHT:100%">
                                <bdo Debug="1" AlertError="0"
                                    Layout_Url="/plm/jsp/wfm/WorkitemListGridLayout.jsp"
                                    Layout_Param_Pagingsize="<%=pagingSize %>"
                                    Data_Url="/plm/jsp/common/searchGridData.jsp"
                                    Data_Method="POST"
                                    Data_Param_Result="<%=tgData %>"
                                    Data_Param_Pagingsize="<%=pagingSize %>"
                                    Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                                    Export_Data="TGData"
                                    Export_Param_File="Workitem_List"
                                ></bdo>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- EJS TreeGrid End -->

  </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" style="width:780px;" height="24" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
