<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.sap.PJTCostInterface"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.doc.beans.DocumentUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
String tmpYear = request.getParameter("tmpYear");
String modelCode = request.getParameter("modelCode");
String devcompanyCode = request.getParameter("devcompanycode");
String productCode = request.getParameter("productcode");
String pjtnocode = request.getParameter("pjtnocode");
Hashtable hash = new Hashtable();
hash.put("tmpYear", StringUtil.checkNull(tmpYear));
hash.put("modelCode", StringUtil.checkNull(modelCode));

DocumentUtil docUtil = new DocumentUtil();

//개발조직
if(StringUtil.checkString(devcompanyCode)) {
  String[] devcompanyStr = docUtil.tokenDocType(devcompanyCode, "$");
  Vector devcompanyVec = new Vector();
  for(int i = 0; i < devcompanyStr.length; i++) {
    devcompanyVec.add(devcompanyStr[i]);
    //out.println("devcompanyStr>>> "+devcompanyStr[i]);
  }
  hash.put("devCompanyCode", devcompanyVec);
}

//제품군
if(StringUtil.checkString(productCode)) {
  String[] productStr = docUtil.tokenDocType(productCode, "$");
  Vector productVec = new Vector();
  for(int j = 0; j < productStr.length; j++) {
    productVec.add(productStr[j]);
    //out.println("productStr>>> "+productStr[j]);
  }
  hash.put("productCode", productVec);
}

//프로젝트 번호
if(StringUtil.checkString(pjtnocode)) {
  String[] pjtnoStr = docUtil.tokenDocType(pjtnocode, "$");
  Vector pjtnoVec = new Vector();
  for(int k = 0; k < pjtnoStr.length; k++) {
    pjtnoVec.add(pjtnoStr[k]);
    //out.println("pjtnoStr>>> "+pjtnoStr[k]);
  }
  hash.put("pjtNo", pjtnoVec);
}

PJTCostInterface pjtCodeInfo = new PJTCostInterface();
Vector returnVec = null;
try {
  returnVec = pjtCodeInfo.getCost(hash);
} catch(Exception e) {
Kogger.error(e);
}
%>
<head>
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "02145") %><%--예산대비 실적 현황--%></title>

</head>
<body>
<form>

<table border="0" cellpadding="1" cellspacing="1" width="3500">
  <tr>
    <td valign="top" >
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 검색 Start -->
      <!-- 검색 End -->
      <table border="0" cellpadding="0" cellspacing="0" align=center width="100%">
        <tr bgcolor="#D6DBE7" align=center>
          <td class=tdblueM rowspan=3>NO</td>
                    <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%></td>
                    <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "03065") %><%--프로젝트 내용--%></td>
                    <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%></td>
                    <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "01367") %><%--모델(차종) 내용--%></td>
                    <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%></td>
                    <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "02582") %><%--제품군 내용--%></td>
          <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%></td>
          <td class=tdblueM rowspan=3><%=messageService.getString("e3ps.message.ket_message", "00666") %><%--개발조직 내용--%></td>
          <td class=tdblueM colspan=24><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                    <td class=tdblueM colspan=24><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
        </tr>
        <tr>
          <!-- 계획 - Start -->
                    <td class=tdblueM colspan=8><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
                    <td class=tdblueM colspan=8><%=messageService.getString("e3ps.message.ket_message", "02474") %><%--전년도--%></td>
          <td class=tdblueM colspan=8><%=messageService.getString("e3ps.message.ket_message", "01217") %><%--당해년도--%></td>
          <!-- 계획 - End -->
          <!-- 실적 - Start -->
                    <td class=tdblueM colspan=8><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
                    <td class=tdblueM colspan=8><%=messageService.getString("e3ps.message.ket_message", "02474") %><%--전년도--%></td>
          <td class=tdblueM colspan=8><%=messageService.getString("e3ps.message.ket_message", "01217") %><%--당해년도--%></td>
          <!-- 실적 - End -->
        </tr>
        <tr>
          <!-- 계획(전체) - Start -->
          <td class=tdblueM>Total</td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00981") %><%--구축물--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01115") %><%--기계장치(설비)--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02741") %><%--집기비용--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01923") %><%--소프트웨어--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00636") %><%--개발비--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00637") %><%--개발비(기타)--%></td>
          <!-- 계획(전체) - End -->
          <!-- 계획(전년도) - Start -->
          <td class=tdblueM>Total</td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00981") %><%--구축물--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01115") %><%--기계장치(설비)--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02741") %><%--집기비용--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01923") %><%--소프트웨어--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00636") %><%--개발비--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00637") %><%--개발비(기타)--%></td>
          <!-- 계획(전년도) - End -->
          <!-- 계획당해년도) - Start -->
          <td class=tdblueM>Total</td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00981") %><%--구축물--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01115") %><%--기계장치(설비)--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02741") %><%--집기비용--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01923") %><%--소프트웨어--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00636") %><%--개발비--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00637") %><%--개발비(기타)--%></td>
          <!-- 계획(당해년도) - End -->
          <!-- 실적(전체) - Start -->
          <td class=tdblueM>Total</td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00981") %><%--구축물--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01115") %><%--기계장치(설비)--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02741") %><%--집기비용--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01923") %><%--소프트웨어--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00636") %><%--개발비--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00637") %><%--개발비(기타)--%></td>
          <!-- 실적(전체) - End -->
          <!-- 실적(전년도) - Start -->
          <td class=tdblueM>Total</td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00981") %><%--구축물--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01115") %><%--기계장치(설비)--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02741") %><%--집기비용--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01923") %><%--소프트웨어--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00636") %><%--개발비--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00637") %><%--개발비(기타)--%></td>
          <!-- 실적(전년도) - End -->
          <!-- 실적(당해년도) - Start -->
          <td class=tdblueM>Total</td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00981") %><%--구축물--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01115") %><%--기계장치(설비)--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></td>
                    <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "02741") %><%--집기비용--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "01923") %><%--소프트웨어--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00636") %><%--개발비--%></td>
          <td class=tdblueM><%=messageService.getString("e3ps.message.ket_message", "00637") %><%--개발비(기타)--%></td>
          <!-- 실적(당해년도) - End -->
        </tr>
          <%
          if(returnVec != null) {
            for(int i = 0; i < returnVec.size(); i++) {
              HashMap map = (HashMap) returnVec.get(i);
          %>
        <tr bgcolor="#D6DBE7" align=center>
          <td class=tdwhiteM>&nbsp;<%=i+1 %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("pspnr").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("post1").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zmodel").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("ztext1").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zjepum").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("ztext2").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zdvorg").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("ztext3").toString()) %></td>
          <!-- 계획(전체) - Start -->
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt001").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt002").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt003").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt004").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt005").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt006").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt007").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zptt008").toString()) %></td>
          <!-- 계획(전체) - End -->
          <!-- 계획(전년도) - Start -->
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy001").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy002").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy003").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy004").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy005").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy006").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy007").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zppy008").toString()) %></td>
          <!-- 계획(전년도) - End -->
          <!-- 계획(당해년도) - Start -->
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty001").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty002").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty003").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty004").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty005").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty006").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty007").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zpty008").toString()) %></td>
          <!-- 계획(당해년도) - End -->
          <!-- 실적(전체) - Start -->
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt001").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt002").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt003").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt004").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt005").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt006").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt007").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zatt008").toString()) %></td>
          <!-- 실적(전체) - End -->
          <!-- 실적(전년도) - Start -->
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy001").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy002").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy003").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy004").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy005").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy006").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy007").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zapy008").toString()) %></td>
          <!-- 실적(전년도) - End -->
          <!-- 실적(당해년도) - Start -->
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty001").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty002").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty003").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty004").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty005").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty006").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty007").toString()) %></td>
          <td class=tdwhiteM>&nbsp;<%=StringUtil.checkNull(map.get("zaty008").toString()) %></td>
          <!-- 실적(당해년도) - End -->
        </tr>
          <%
            }
          }

          if(returnVec == null){
          %>
        <tr bgcolor=white>
          <td class=tb_gray align=center colspan="56" ><font color="red">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
        </tr>
          <%
          }
          %>
      </table>
    </td>
  </tr>
</table>



</form>

</body>
</html>
