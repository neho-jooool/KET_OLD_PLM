<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "java.util.ArrayList,
                  java.util.Hashtable"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    ArrayList list = (ArrayList)request.getAttribute("productList");
    Hashtable condition = (Hashtable)request.getAttribute("condition");

    Hashtable productProject = null;
    int s11 = 0;
    int s12 = 0;
    int s21 = 0;
    int s22 = 0;
    int s31 = 0;
    int s32 = 0;
    int s41 = 0;
    int s42 = 0;
    int p11 = 0;
    int p12 = 0;
    int p13 = 0;
    int p14 = 0;
    int p21 = 0;
    int p22 = 0;
    int p23 = 0;
    int p24 = 0;
    int p31 = 0;
    int p32 = 0;
    int p33 = 0;
    int p34 = 0;
    int p41 = 0;
    int p42 = 0;
    int p43 = 0;
    int p44 = 0;
    int r11 = 0;
    int r12 = 0;
    int r13 = 0;
    int r21 = 0;
    int r22 = 0;
    int r23 = 0;
    int r31 = 0;
    int r32 = 0;
    int r33 = 0;
    int r41 = 0;
    int r42 = 0;
    int r43 = 0;

  if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            productProject = (Hashtable)list.get(inx);
            if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("전략개발") && ((String)productProject.get("state")).equals("N") ) {
                s11 = s11 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("전략개발") && ((String)productProject.get("state")).equals("O") ) {
              s12 = s12 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("수주개발") && ((String)productProject.get("state")).equals("N") ) {
              s21 = s21 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("수주개발") && ((String)productProject.get("state")).equals("O") ) {
              s22 = s22 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("연구개발") && ((String)productProject.get("state")).equals("N") ) {
              s31 = s31 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("연구개발") && ((String)productProject.get("state")).equals("O") ) {
              s32 = s32 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("추가금형") && ((String)productProject.get("state")).equals("N") ) {
              s41 = s41 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("S") && ((String)productProject.get("devType")).equals("추가금형") && ((String)productProject.get("state")).equals("O") ) {
              s42 = s42 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("전략개발")
                              && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              p11 = p11 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("전략개발")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                          || ((String)productProject.get("state")).equals("DEVASSIGN")
                          || ((String)productProject.get("state")).equals("INWORK")
                          || ((String)productProject.get("state")).equals("UNDERREVIEW")
                          || ((String)productProject.get("state")).equals("APPROVED")
                          || ((String)productProject.get("state")).equals("REJECTED")
                          || ((String)productProject.get("state")).equals("REWORK")
                          || ((String)productProject.get("state")).equals("PLANCHANGE")
                          || ((String)productProject.get("state")).equals("PROGRESS")
                          || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            p12 = p12 + Integer.parseInt((String)productProject.get("count"));
            if( (((String)productProject.get("state")).equals("INWORK")
                    || ((String)productProject.get("state")).equals("UNDERREVIEW")
                    || ((String)productProject.get("state")).equals("APPROVED")
                    || ((String)productProject.get("state")).equals("REJECTED")
                    || ((String)productProject.get("state")).equals("REWORK")
                    || ((String)productProject.get("state")).equals("PLANCHANGE")
                    || ((String)productProject.get("state")).equals("PROGRESS") )
                    && ((String)productProject.get("pjtState")).equals("지연") ) {
                  p14 = p14 + Integer.parseInt((String)productProject.get("count"));
              }
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("전략개발")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              p13 = p13 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("수주개발")
                      && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              p21 = p21 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("수주개발")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                          || ((String)productProject.get("state")).equals("DEVASSIGN")
                          || ((String)productProject.get("state")).equals("INWORK")
                          || ((String)productProject.get("state")).equals("UNDERREVIEW")
                          || ((String)productProject.get("state")).equals("APPROVED")
                          || ((String)productProject.get("state")).equals("REJECTED")
                          || ((String)productProject.get("state")).equals("REWORK")
                          || ((String)productProject.get("state")).equals("PLANCHANGE")
                          || ((String)productProject.get("state")).equals("PROGRESS")
                          || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            p22 = p22 + Integer.parseInt((String)productProject.get("count"));
            if( (((String)productProject.get("state")).equals("INWORK")
                    || ((String)productProject.get("state")).equals("UNDERREVIEW")
                    || ((String)productProject.get("state")).equals("APPROVED")
                    || ((String)productProject.get("state")).equals("REJECTED")
                    || ((String)productProject.get("state")).equals("REWORK")
                    || ((String)productProject.get("state")).equals("PLANCHANGE")
                    || ((String)productProject.get("state")).equals("PROGRESS") )
                    && ((String)productProject.get("pjtState")).equals("지연") ) {
                  p24 = p24 + Integer.parseInt((String)productProject.get("count"));
              }
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("수주개발")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              p23 = p23 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("연구개발")
                      && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              p31 = p31 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("연구개발")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                          || ((String)productProject.get("state")).equals("DEVASSIGN")
                          || ((String)productProject.get("state")).equals("INWORK")
                          || ((String)productProject.get("state")).equals("UNDERREVIEW")
                          || ((String)productProject.get("state")).equals("APPROVED")
                          || ((String)productProject.get("state")).equals("REJECTED")
                          || ((String)productProject.get("state")).equals("REWORK")
                          || ((String)productProject.get("state")).equals("PLANCHANGE")
                          || ((String)productProject.get("state")).equals("PROGRESS")
                          || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            p32 = p32 + Integer.parseInt((String)productProject.get("count"));
            if( (((String)productProject.get("state")).equals("INWORK")
                    || ((String)productProject.get("state")).equals("UNDERREVIEW")
                    || ((String)productProject.get("state")).equals("APPROVED")
                    || ((String)productProject.get("state")).equals("REJECTED")
                    || ((String)productProject.get("state")).equals("REWORK")
                    || ((String)productProject.get("state")).equals("PLANCHANGE")
                    || ((String)productProject.get("state")).equals("PROGRESS") )
                    && ((String)productProject.get("pjtState")).equals("지연") ) {
                  p34 = p34 + Integer.parseInt((String)productProject.get("count"));
              }
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("연구개발")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              p33 = p33 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("추가금형")
                      && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              p41 = p41 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("추가금형")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                          || ((String)productProject.get("state")).equals("DEVASSIGN")
                          || ((String)productProject.get("state")).equals("INWORK")
                          || ((String)productProject.get("state")).equals("UNDERREVIEW")
                          || ((String)productProject.get("state")).equals("APPROVED")
                          || ((String)productProject.get("state")).equals("REJECTED")
                          || ((String)productProject.get("state")).equals("REWORK")
                          || ((String)productProject.get("state")).equals("PLANCHANGE")
                          || ((String)productProject.get("state")).equals("PROGRESS")
                          || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            p42 = p42 + Integer.parseInt((String)productProject.get("count"));
            if( (((String)productProject.get("state")).equals("INWORK")
                    || ((String)productProject.get("state")).equals("UNDERREVIEW")
                    || ((String)productProject.get("state")).equals("APPROVED")
                    || ((String)productProject.get("state")).equals("REJECTED")
                    || ((String)productProject.get("state")).equals("REWORK")
                    || ((String)productProject.get("state")).equals("PLANCHANGE")
                    || ((String)productProject.get("state")).equals("PROGRESS") )
                    && ((String)productProject.get("pjtState")).equals("지연") ) {
                  p44 = p44 + Integer.parseInt((String)productProject.get("count"));
              }
          }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("devType")).equals("추가금형")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              p43 = p43 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("전략개발")
                      && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              r11 = r11 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("전략개발")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                           || ((String)productProject.get("state")).equals("DEVASSIGN")
                           || ((String)productProject.get("state")).equals("INWORK")
                           || ((String)productProject.get("state")).equals("UNDERREVIEW")
                           || ((String)productProject.get("state")).equals("APPROVED")
                           || ((String)productProject.get("state")).equals("REJECTED")
                           || ((String)productProject.get("state")).equals("REWORK")
                           || ((String)productProject.get("state")).equals("PLANCHANGE")
                           || ((String)productProject.get("state")).equals("PROGRESS")
                           || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            r12 = r12 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("전략개발")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              r13 = r13 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("수주개발")
                      && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              r21 = r21 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("수주개발")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                           || ((String)productProject.get("state")).equals("DEVASSIGN")
                           || ((String)productProject.get("state")).equals("INWORK")
                           || ((String)productProject.get("state")).equals("UNDERREVIEW")
                           || ((String)productProject.get("state")).equals("APPROVED")
                           || ((String)productProject.get("state")).equals("REJECTED")
                           || ((String)productProject.get("state")).equals("REWORK")
                           || ((String)productProject.get("state")).equals("PLANCHANGE")
                           || ((String)productProject.get("state")).equals("PROGRESS")
                           || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            r22 = r22 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("수주개발")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              r23 = r23 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("연구개발")
                      && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              r31 = r31 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("연구개발")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                           || ((String)productProject.get("state")).equals("DEVASSIGN")
                           || ((String)productProject.get("state")).equals("INWORK")
                           || ((String)productProject.get("state")).equals("UNDERREVIEW")
                           || ((String)productProject.get("state")).equals("APPROVED")
                           || ((String)productProject.get("state")).equals("REJECTED")
                           || ((String)productProject.get("state")).equals("REWORK")
                           || ((String)productProject.get("state")).equals("PLANCHANGE")
                           || ((String)productProject.get("state")).equals("PROGRESS")
                           || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            r32 = r32 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("연구개발")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              r33 = r33 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("추가금형")
                      && ((String)productProject.get("state")).equals("COMPLETED")
                              && ((String)productProject.get("endDate")).equals((String)condition.get("year")) ) {
              r41 = r41 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("추가금형")
                      && (((String)productProject.get("state")).equals("PMOINWORK")
                           || ((String)productProject.get("state")).equals("DEVASSIGN")
                           || ((String)productProject.get("state")).equals("INWORK")
                           || ((String)productProject.get("state")).equals("UNDERREVIEW")
                           || ((String)productProject.get("state")).equals("APPROVED")
                           || ((String)productProject.get("state")).equals("REJECTED")
                           || ((String)productProject.get("state")).equals("REWORK")
                           || ((String)productProject.get("state")).equals("PLANCHANGE")
                           || ((String)productProject.get("state")).equals("PROGRESS")
                           || (((String)productProject.get("state")).equals("COMPLETED") && !((String)productProject.get("endDate")).equals((String)condition.get("year"))) ) ) {
            r42 = r42 + Integer.parseInt((String)productProject.get("count"));
          }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("devType")).equals("추가금형")
                      && (((String)productProject.get("state")).equals("STOPED") || ((String)productProject.get("state")).equals("WITHDRAWN")) ) {
              r43 = r43 + Integer.parseInt((String)productProject.get("count"));
          }
        }
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style type="text/css">
<!--

-->
</style>

<script language='javascript'>
<!--
    //Project Report 화면 Open
    function goView(cmd, rowcon1, colcon1){
        var url = '/plm/jsp/project/ProjectReportList.jsp'
                   + '?cmd=' + cmd + '&devType=' + rowcon1 + '&statestate=' + colcon1
                   + '&year=<%=(String)condition.get("year")%>&division=<%=(String)condition.get("division")%>&dept=<%=(String)condition.get("dept")%>&customerNo=<%=(String)condition.get("customerNo")%>';
        openWindow(url, '',880,750);
    }

//-->
</script>

</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" width="780">
  <tr>
    <td width="65" rowspan='2' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
    <td colspan='7' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00672") %><%--개발프로젝트--%></td>
    <td colspan='4' class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00734") %><%--검토프로젝트--%></td>
  </tr>
  <tr>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02022") %><%--신규--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02321") %><%--이월--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02688") %><%--중단--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td width="65" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02688") %><%--중단--%></td>
  </tr>
  <tr>
      <td width="65" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02476") %><%--전략개발--%></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV001', 'N');"><b><%=s11%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV001', 'O');"><b><%=s12%></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList1', 'DEV001', '');"><b><%=s11+s12%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV001', 'C');"><b><%=p11%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV001', 'P');"><b><%=p12%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV001', 'S');"><b><%=p13%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV001', 'D');"><b><font color="red"><%=p14%></font></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList3', 'DEV001', '');"><b><%=r11+r12+r13%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV001', 'C');"><b><%=r11%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV001', 'P');"><b><%=r12%></b></a></td>
    <td width="65" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV001', 'S');"><b><%=r13%></b></a></td>
  </tr>
  <tr>
      <td width="65" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01963") %><%--수주개발--%></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV002', 'N');"><b><%=s21%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV002', 'O');"><b><%=s22%></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList1', 'DEV002', '');"><b><%=s21+s22%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV002', 'C');"><b><%=p21%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV002', 'P');"><b><%=p22%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV002', 'S');"><b><%=p23%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV002', 'D');"><b><font color="red"><%=p24%></font></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList3', 'DEV002', '');"><b><%=r21+r22+r23%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV002', 'C');"><b><%=r21%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV002', 'P');"><b><%=r22%></b></a></td>
    <td width="65" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV002', 'S');"><b><%=r23%></b></a></td>
  </tr>
  <tr>
      <td width="65" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02128") %><%--연구개발--%></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV003', 'N');"><b><%=s31%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV003', 'O');"><b><%=s32%></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList1', 'DEV003', '');"><b><%=s31+s32%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV003', 'C');"><b><%=p31%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV003', 'P');"><b><%=p32%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV003', 'S');"><b><%=p33%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV003', 'D');"><b><font color="red"><%=p34%></font></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList3', 'DEV003', '');"><b><%=r31+r32+r33%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV003', 'C');"><b><%=r31%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV003', 'P');"><b><%=r32%></b></a></td>
    <td width="65" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV003', 'S');"><b><%=r33%></b></a></td>
  </tr>
  <tr>
      <td width="65" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02865") %><%--추가금형--%></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV004', 'N');"><b><%=s41%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', 'DEV004', 'O');"><b><%=s42%></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList1', 'DEV004', '');"><b><%=s41+s42%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV004', 'C');"><b><%=p41%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV004', 'P');"><b><%=p42%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV004', 'S');"><b><%=p43%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV004', 'D');"><b><font color="red"><%=p44%></font></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList3', 'DEV004', '');"><b><%=r41+r42+r43%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV004', 'C');"><b><%=r41%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV004', 'P');"><b><%=r42%></b></a></td>
    <td width="65" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV004', 'S');"><b><%=r43%></b></a></td>
  </tr>
  <tr>
      <td width="65" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', '', 'N');"><b><%=s11+s21+s31+s41%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList1', '', 'O');"><b><%=s12+s22+s32+s42%></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList1', '', '');"><b><%=s11+s21+s31+s41+s12+s22+s32+s42%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', '', 'C');"><b><%=p11+p21+p31+p41%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', '', 'P');"><b><%=p12+p22+p32+p42%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', '', 'S');"><b><%=p13+p23+p33+p43%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList2', '', 'D');"><b><font color="red"><%=p14+p24+p34+p44%></font></b></a></td>
    <td width="65" class="tdblueM"><a href="javascript:goView('reportList3', '', '');"><b><%=r11+r21+r31+r41+r12+r22+r32+r42+r13+r23+r33+r43%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', '', 'C');"><b><%=r11+r21+r31+r41%></b></a></td>
    <td width="65" class="tdwhiteM"><a href="javascript:goView('reportList3', '', 'P');"><b><%=r12+r22+r32+r42%></b></a></td>
    <td width="65" class="tdwhiteM0"><a href="javascript:goView('reportList3', '', 'S');"><b><%=r13+r23+r33+r43%></b></a></td>
  </tr>
</table>
</body>
</html>
