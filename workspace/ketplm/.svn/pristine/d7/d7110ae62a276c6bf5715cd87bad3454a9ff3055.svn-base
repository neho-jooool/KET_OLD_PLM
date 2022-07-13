<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.HashMap" %>
<%@page import="org.apache.commons.lang.*"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="resultData" class="java.util.ArrayList" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    int versionCount = resultData.size();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01483") %><%--버전이력 조회--%></title>

<%@include file="/jsp/common/processing.html" %>

<script language=JavaScript src="/plm/portal/js/common.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 5px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

</head>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01482") %><%--버전이력--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
      <tr>
          <td class="space5"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>        
        <tr>
          <td class="space5"></td>
        </tr>      
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td  class="tab_btm2"></td>
          <td valign="top"><table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <div style="width:100%;height:290px;overflow-x:hidden;overflow-y:auto;">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td  class="tdblueM" align="center"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td  class="tdblueM" align="center"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                <td  class="tdblueM" align="center"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td  class="tdblueM" align="center"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                <td  class="tdblueM" align="center"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                <%-- <td width="230" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632") %>비고</td> --%>
              </tr>
            
<%
        if( versionCount > 0 )
        {
            HashMap<String,String> objMap = new HashMap<String,String>();

            for( int row = 0; row < versionCount; row++ )
            {
                objMap = (HashMap<String,String>)resultData.get( row );
%>
                <tr>
                  <td class="tdwhiteM" align="center"><a href="javascript:;" onClick="<%=objMap.get("url")%>"><%=objMap.get("version")%></a></td>
                  <td class="tdwhiteM" align="center"><%=objMap.get("department")%></td>
                  <td class="tdwhiteM" align="center"><%=objMap.get("creator")%></td>
                  <td class="tdwhiteM" align="center"><%=objMap.get("createdDate")%></td>
                  <td class="tdwhiteM" align="center"><%=objMap.get("approver")%></td>
                  <%-- <td class="tdwhiteM0" title="<%=StringEscapeUtils.escapeHtml(objMap.get("description")) %>"><div class="ellipsis" style="width:220px;"><nobr><%=objMap.get("description")%></nobr></div></td> --%>
                </tr>
<%
            }
        }
        else
        {
%>
                <tr>
                  <td colspan="6" class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01485") %><%--버전이력이 존재하지 않습니다--%></td>
                </tr>
<%
        }
%>
             
              </table>
            </div></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="710" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
