<%@page import="wt.org.WTUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = "";

    if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
        oid = request.getParameter("oid");
    }

    QueryResult qr = null;
    QuerySpec qs = null;
    try{
        qs = new QuerySpec(TryChange.class);
        long mtLong = CommonUtil.getOIDLongValue(oid);
        qs.appendWhere(new SearchCondition(TryChange.class, TryChange.CHANGE_ATTR1, "=", ""+mtLong), new int[] {0});
        qr = PersistenceHelper.manager.find(qs);
    }catch(Exception e){
	Kogger.error(e);
    }
%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.trySchdule.TryChange"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 5px;
    margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">

</script>
</head>
<body>
<form>
<input type="hidden" name="oid" value="<%=oid %>"></input>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02364") %><%--일정변경사유이력--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
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
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                  <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "01520") %><%--변경일--%></td>
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01522") %><%--변경자--%></td>
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01498") %><%--변경 사유--%></td>
              </tr>
              <%
              if(qr != null){
                 while(qr.hasMoreElements()){
                     TryChange tc = (TryChange)qr.nextElement();
                     String creator = "";
                      String pcDate = "";
                     String pcDesc = "";
                       if(tc.getChangeDate() != null){
                          pcDate = DateUtil.getDateString(tc.getChangeDate() , "d");
                       }
                       if(tc.getChangeDetil() != null){
                          pcDesc  = tc.getChangeDetil();
                       }
                       if(tc.getOwner().toString() != null){
                          //Kogger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ddd"+tc.getOwner());
                           WTUser user = (WTUser)tc.getOwner().getPrincipal();
                           creator = user.getFullName();
                       }else{
                           creator = "-";
                       }

              %>
              <tr>
                <td class='tdwhiteL'><%=pcDate%></td>
                <td class='tdwhiteM'><%=creator%></td>
                <td class='tdwhiteL'>
                <textarea name="reason" style="width:100%" rows="2" class="fm_area" readOnly><%=pcDesc%></textarea>
                </td>
              </tr>
                  <%} %>
                  <%if(qr.size() == 0 ){ %>
                  <tr>
                    <td class='tdwhiteM0' align='center' colspan='3'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>
                  <%} %>
              <%} %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>

</form>
</body>
</html>
