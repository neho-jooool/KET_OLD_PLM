<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.DateUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoReportDetailVO" %>
<%@page import="e3ps.common.web.PageControl" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoReportVO" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00184") %><%--ECO 검색--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }
-->
</style>
<script language="javascript">
</script>
</head>
<body>
<%
PageControl control = data.getPageControl();

int cols = 17;

int pagerow = 0;
if(data.getTotalCount() > 0) {
    pagerow = data.getResultRows();
}
%>

<div id="div_scroll" style="width:780px; height:271px; overflow:auto;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr class="headerLock">
        <td>
            <% if (pagerow > 10) { %>
            <table border="0" cellspacing="0" cellpadding="0" width="762" style=table-layout:fixed >
            <% } else { %>
        <table border="0" cellspacing="0" cellpadding="0" width="780" style=table-layout:fixed >
        <% } %>
                <tr>
                    <td rowspan='2' width='40' class="tdblueM">No</td>
                    <% if (pagerow > 10) { %>
                    <td rowspan='2' width='97' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                    <% } else { %>
                    <td rowspan='2' width='115' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                    <% } %>
                    <td rowspan='2' width='95' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                    <td width='110' colspan='2' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%></td>
                    <td colspan='12' class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00015", DateUtil.getThisYear()) %><%--{0}년--%></td>
                </tr>
                <tr>
                  <td width='55' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02473") %><%--전년--%></td>
                  <td width='55' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00995") %><%--금년--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 1) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 2) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 3) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 4) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 5) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 6) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 7) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 8) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 9) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 10) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00039", 11) %><%--{0}월--%></td>
                  <td width='35' class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00039", 12) %><%--{0}월--%></td>
                </tr>
            </table>
      </td>
    </tr>
    <tr>
        <td>
            <% if (pagerow > 10) { %>
            <table border="0" cellspacing="0" cellpadding="0" width="762" style=table-layout:fixed >
            <% } else { %>
        <table border="0" cellspacing="0" cellpadding="0" width="780" style=table-layout:fixed >
        <% } %>
                <%
                if(data.getTotalCount() > 0) {
                    int size = data.getResultRows();
                    KETSearchEcoReportDetailVO ketSearchEcoReportDetailVO = null;
                    int rowCount = (control.getCurrentPage() - 1) * control.getInitPerPage();
                    for(int i=0; i<size; i++) {
                        ketSearchEcoReportDetailVO =  data.getKetSearchEcoReportDetailVOList().get(i);
                        rowCount++;
                %>
                <tr>
                    <td width='40' class="tdwhiteM"><%=rowCount%></td>
                    <% if (pagerow > 10) { %>
                    <td width='97' class="tdwhiteM">
                    <% } else { %>
                    <td width='115' class="tdwhiteM">
                    <% } %>
                      <%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getObjectNo())%>&nbsp;</td>
                    <td width='95' class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoReportDetailVO.getObjectName())%>&nbsp;</td>
                    <td width='55' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getOldEcoCount())%>&nbsp;</td>
                    <td width='55' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getEcoCount())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt1())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt2())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt3())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt4())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt5())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt6())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt7())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt8())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt9())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt10())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt11())%>&nbsp;</td>
                    <td width='35' class="tdwhiteR0"><%=KETStringUtil.money(ketSearchEcoReportDetailVO.getDataCnt12())%>&nbsp;</td>
                </tr>
                <%
                    }
                } else {//자료가 존재하지 않은 경우
                %>
                <tr>
                    <td colspan="<%=cols%>" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
                </tr>
                <%
                }
                %>
            </table>
        </td>
    </tr>
</table>
</div>
<%if(control != null) {%>
<%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
<%}%>
