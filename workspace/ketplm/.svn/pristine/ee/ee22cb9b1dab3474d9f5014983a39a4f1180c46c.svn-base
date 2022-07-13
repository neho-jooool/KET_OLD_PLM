<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import = "java.util.ArrayList,
                  java.util.Hashtable,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.web.PageControl"%>

<%
String itemcode = request.getParameter("itemcode");

Hashtable condition = (Hashtable)request.getAttribute("condition");
System.out.println("----------------------condition"+condition);
ArrayList list = (ArrayList)request.getAttribute("list");
PageControl control = (PageControl)request.getAttribute("control");
System.out.println("----------------------control"+control);

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01067") %><%--금형부품 설계변경 조회--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language='javascript'>

// 검색
function search(){
  document.forms[0].action =  '/plm/servlet/MoldChangePlanServlet';
  document.forms[0].submit();
}

function serachPartPopup(){
  var url = "/plm/jsp/bom/MoldChangHistoryPopup.jsp?itemcode=10117000-022";
  openWindow(url,"","630","600","scrollbars=auto,resizable=no,top=200,left=450");
}

</script>
</head>

<% if(condition == null){%>
<body onload="search();">
<%}else{%>
<body>
<%}%>

<form method=post>
<!-- hidden -->
<input type="hidden" name="cmd" value="popup">
<input type="hidden" name="itemcode" value="<%=itemcode%>">
<!-- hidden end -->

<table width="100%" height="570" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01067") %><%--금형부품 설계변경 조회--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>

    <td valign="top">
      <table width="600" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="15">&nbsp;</td>
          <td valign="top" width='585'>

           <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td  class="tab_btm2"></td>
            </tr>
          </table>

            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="5%" class="tdblueM">No</td>
                <td width="20%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01075") %><%--금형부품번호--%></td>
                <td width="5%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01873") %><%--설변횟수--%></td>
                <td width="15%" class="tdblueM">REV.DATE</td>
                <!--td width="10%" class="tdblueM">사원번호</td-->
        <td width="10%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01522") %><%--변경자--%></td>
                <td width="45%" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01506") %><%--변경내역--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
<%
        Hashtable moldChange = null;
      if( list !=null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            moldChange = (Hashtable)list.get(inx);
%>
        <tr>
          <td width="5%"  class="tdwhiteM"><%=moldChange.get("row_id") %></td>
          <td width="20%" class="tdwhiteL"><%=moldChange.get("itemcode") %>&nbsp;</a></td>
          <td width="5%" class="tdwhiteM"><%=moldChange.get("mocnt") %>&nbsp;</td>
          <td width="15%" class="tdwhiteM"><%=moldChange.get("revisiondate") %>&nbsp;</td>
          <!--td width="10%" class="tdwhiteM"><%=moldChange.get("empno") %>&nbsp;</td-->
          <td width="10%" class="tdwhiteM"><%=moldChange.get("mouser") %>&nbsp;</td>
          <td width="45%"  class="tdwhiteL0" title="<%=moldChange.get("modesc")%>">
            <div class="ellipsis" style="width:263;"><nobr>
                <%=moldChange.get("modesc") %>&nbsp;</nobr></div>
              </td>
        </tr>
<%
        }
      }else{
%>
         <tr>
          <td colspan="6"  class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
         </tr>
<%
      }
%>

              </table>
             </td>
             </tr>
            </table>
            </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="15">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="585" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
