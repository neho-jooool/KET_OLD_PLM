<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "java.util.ArrayList,
                e3ps.common.util.*,
                  java.util.Hashtable"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String checkDept = StringUtil.checkNull(request.getParameter("checkDept"));

    ArrayList list = (ArrayList)request.getAttribute("productList");
    Hashtable condition = (Hashtable)request.getAttribute("condition");

    Hashtable productProject = null;
    /*  프로젝트 TASK START  */
    int p11 = 0;
    int p12 = 0;
    int p13 = 0;
    int p21 = 0;
    int p22 = 0;
    int p23 = 0;
    int p31 = 0;
    int p32 = 0;
    int p33 = 0;
    int p41 = 0;
    int p42 = 0;
    int p43 = 0;
    /*  프로젝트 TASK END  */
    /*  검토프로젝트 TASK START  */
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
    /*  검토프로젝트 TASK END  */

  if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            productProject = (Hashtable)list.get(inx);
            if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p11 = p11 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p12 = p12 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("지연")
                 && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                p13 = p13 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p21 = p21 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p22 = p22 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && (  !"PMOINWORK".equals( (String)productProject.get("statestate") ) || !"DEVASSIGN".equals(productProject.get("statestate"))  )  ){
                p23 = p23 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p31 = p31 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p32 = p32 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                p33 = p33 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("완료")  ){
                p41 = p41 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("P") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("진행")  ){
                p42 = p42 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("D") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                p43 = p43 +  Integer.parseInt((String)productProject.get("count"));
            }
            else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r11 = r11 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r12 = r12 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("전략개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r13 = r13 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r21 = r21 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r22 = r22 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("수주개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r23 = r23 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r31 = r31 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r32 = r32 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("연구개발") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r33 = r33 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("완료")  ){
                r41 = r41 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("R") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("진행")  ){
                r42 = r42 +  Integer.parseInt((String)productProject.get("count"));
            }else if( ((String)productProject.get("pjtType")).equals("J") && ((String)productProject.get("name")).equals("추가금형") && ((String)productProject.get("taskstate")).equals("지연")
                     && !"PMOINWORK".equals( (String)productProject.get("statestate") ) && !"DEVASSIGN".equals(productProject.get("statestate")) ){
                r43 = r43 +  Integer.parseInt((String)productProject.get("count"));
            }else{}
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
        var url = '/plm/jsp/project/TaskReportList.jsp'
            + '?cmd=' + cmd + '&devType=' + rowcon1 + '&statestate=' + colcon1+ '&checkDept=<%=checkDept%>&year=<%=(String)condition.get("year")%>&dept=<%=(String)condition.get("dept")%>&dept1=<%=(String)condition.get("dept1")%>&dept2=<%=(String)condition.get("dept2")%>&dept3=<%=(String)condition.get("dept3")%>&creator=<%=(String)condition.get("creator")%>&pTaskName=<%=(String)condition.get("pTaskName")%>';
        openWindow(url, '',880,750);
    }

//-->
</script>

</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" width="780">
  <tr>
    <td width="86" rowspan='2' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
    <td colspan='4' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00672") %><%--개발프로젝트--%></td>
    <td colspan='4' class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00734") %><%--검토프로젝트--%></td>
  </tr>
  <tr>
    <td width="86" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td width="86" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td width="86" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
    <td width="86" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td width="86" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td width="86" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td width="86" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
    <td width="86" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
  </tr>
  <tr>
      <td width="86" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02476") %><%--전략개발--%></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV001', 'C');"><b><%=p11%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV001', 'P');"><b><%=p12%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV001', 'D');"><b><font color="red"><%=p13%></font></b></a></td>
    <td width="86" class="tdblueM"><a href="javascript:goView('reportList2', 'DEV001', '');"><b><%=p11+p12%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV001', 'C');"><b><%=r11%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV001', 'P');"><b><%=r12%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV001', 'D');"><b><font color="red"><%=r13%></font></b></a></td>
    <td width="86" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV001', '');"><b><%=r11+r12%></b></a></td>
  </tr>
  <tr>
      <td width="86" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01963") %><%--수주개발--%></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV002', 'C');"><b><%=p21%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV002', 'P');"><b><%=p22%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV002', 'D');"><b><font color="red"><%=p23%></font></b></a></td>
    <td width="86" class="tdblueM"><a href="javascript:goView('reportList2', 'DEV002', '');"><b><%=p21+p22%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV002', 'C');"><b><%=r21%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV002', 'P');"><b><%=r22%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV002', 'D');"><b><font color="red"><%=r23%></font></b></a></td>
    <td width="86" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV002', '');"><b><%=r21+r22%></b></a></td>
  </tr>
  <tr>
      <td width="86" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02128") %><%--연구개발--%></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV003', 'C');"><b><%=p31%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV003', 'P');"><b><%=p32%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV003', 'D');"><b><font color="red"><%=p33%></font></b></a></td>
    <td width="86" class="tdblueM"><a href="javascript:goView('reportList2', 'DEV003', '');"><b><%=p31+p32%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV003', 'C');"><b><%=r31%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV003', 'P');"><b><%=r32%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV003', 'D');"><b><font color="red"><%=r33%></font></b></a></td>
    <td width="86" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV003', '');"><b><%=r31+r32%></b></a></td>
  </tr>
  <tr>
      <td width="86" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02865") %><%--추가금형--%></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV004', 'C');"><b><%=p41%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV004', 'P');"><b><%=p42%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', 'DEV004', 'D');"><b><font color="red"><%=p43%></font></b></a></td>
    <td width="86" class="tdblueM"><a href="javascript:goView('reportList2', 'DEV004', '');"><b><%=p41+p42%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV004', 'C');"><b><%=r41%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV004', 'P');"><b><%=r42%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', 'DEV005', 'D');"><b><font color="red"><%=r43%></font></b></a></td>
    <td width="86" class="tdwhiteM0"><a href="javascript:goView('reportList3', 'DEV004', '');"><b><%=r41+r42%></b></a></td>
  </tr>
  <tr>
      <td width="86" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', '', 'C');"><b><%=p11+p21+p31+p41%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', '', 'P');"><b><%=p12+p22+p32+p42%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList2', '', 'D');"><b><font color="red"><%=p13+p23+p33+p43%></font></b></a></td>
    <td width="86" class="tdblueM"><a href="javascript:goView('reportList2', '', '');"><b><%=p11+p12+p21+p22+p31+p32+p41+p42%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', '', 'C');"><b><%=r11+r21+r31+r41%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', '', 'P');"><b><%=r12+r22+r32+r42%></b></a></td>
    <td width="86" class="tdwhiteM"><a href="javascript:goView('reportList3', '', 'D');"><b><font color="red"><%=r13+r23+r33+r43%></font></b></a></td>
    <td width="86" class="tdwhiteM0"><a href="javascript:goView('reportList3', '', '');"><b><%=r11+r12+r21+r22+r31+r32+r41+r42%></b></a></td>
  </tr>
</table>
</body>
</html>
