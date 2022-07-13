<%@page import="e3ps.common.util.*"%>
<%@page import="e3ps.ecm.entity.*"%>

<%@ page import="wt.org.WTUser
                ,wt.fc.*
                ,wt.query.*
                ,wt.content.ContentHolder
                ,wt.content.ContentHelper
                ,wt.content.ContentRoleType
                ,wt.content.ContentItem
                ,wt.session.SessionHelper
                ,wt.change2.*
                ,wt.part.*"%>

<%@ page import= "java.util.*
                 ,java.net.URL
                 ,java.util.ArrayList
                 ,java.util.Hashtable" %>
                             
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>
                     

                             
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<jsp:useBean id="oid" class="java.lang.String" scope="request" />
<%
// 로그인 사용자
WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
String loginUserOid = (String)CommonUtil.getOIDString(user);
String loginUserName = user.getFullName();

WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(oid);
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "04136") %><%--실중량 입력 이력--%></title>

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
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    
})

</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "04136") %><%--실중량 입력 이력--%></td>
                <!-- td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02541") %><%--제품 ECR 등록--%>
                  </td -->
              </tr>
            </table>
          </td>
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
      
      
      <div style="width:100%; height:410px; overflow-x:auto; overflow-y:auto;">
          
           <table width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
               <td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
               <td width="40" class="tdgrayM">Rev</td>
               <td width="65" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04137") %><%--총중량--%></td>
               <td width="65" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04138") %><%--부품중량--%></td>
               <td width="65" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "04139") %><%--스크랩중량--%></td>
             </tr>
                    
             <%
             QuerySpec spec = new QuerySpec(KETEcnWeightHistory.class);
             spec.appendWhere(new SearchCondition(KETEcnWeightHistory.class, "ecoReference.key.id", "=", CommonUtil.getOIDLongValue(eco)), new int[] { 0 });
             QueryResult result = PersistenceHelper.manager.find(spec);
             if(result.size() > 0) {
	             while (result.hasMoreElements()) {
	                 KETEcnWeightHistory ketEcnWeightHistory = (KETEcnWeightHistory) result.nextElement();
	                 WTPart part = ketEcnWeightHistory.getPart();
	                 String spTotalWeight = StringUtils.stripToEmpty(ketEcnWeightHistory.getSpTotalWeight());
	                 String spNetWeight = StringUtils.stripToEmpty(ketEcnWeightHistory.getSpNetWeight());
	                 String spScrabWeight = StringUtils.stripToEmpty(ketEcnWeightHistory.getSpScrabWeight());
	
	                 String partNumber = part.getNumber();
	                 String partName = part.getName();
	                 String partRev = part.getVersionIdentifier().getValue();
             %>
	                 <tr>
	                   <td class="tdwhiteM" align="center"><%=partNumber %></td>
	                   <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(partName) %>"><div class="ellipsis" style="width:210px;"><nobr><%=partName %></nobr></div></td>
	                   <td class="tdwhiteM" align="center"><%=partRev %></td>
	                   <td class="tdwhiteM" align="center"><%=spTotalWeight %></td>
	                   <td class="tdwhiteM" align="center"><%=spNetWeight %></td>
	                   <td class="tdwhiteM0" align="center"><%=spScrabWeight %></td>                
	                 </tr> 
             <%
                 }

	         } else {    
             %>       
                 <tr>
                   <td colspan="6" class="tdwhiteM0" align="center"><%=messageService.getString("e3ps.message.ket_message", "04141") %><%--입력 이력이 없습니다.--%></td>                
                 </tr> 
             <%
             }
             %>
           </table>
          
      </div>    
          
    </td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</body>
</html>
    