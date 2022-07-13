<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoDetailVO" %>
<%@page import="e3ps.common.web.PageControl" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
PageControl control = data.getPageControl();
%>

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
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.nameCut{
width:175;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}
.deptCut{
width:80;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}
</style>
<script language="javascript">

//페이지 조회
function doSort(sortColId, sortColName){
    var sortSQL = "";
    var currentSort = parent.getSort();

    if(currentSort.indexOf(sortColName) > -1)
    {
        if( currentSort.indexOf("DESC") > -1 )
        {
            sortSQL = sortColName + " ASC ";
        }
        else
        {
            sortSQL = sortColName + " DESC ";
        }
    }
    else
    {
        sortSQL = sortColName + " ASC";
    }
    //test
    parent.doSort(sortSQL);
}

</script>
</head>
<body>
<%
String sortCol1 = "ECR No";
String sortSQL = "";
if(data.getTotalCount() > 0) {
    sortSQL = data.getSortColumn().replace(" ", "");
}
if(sortSQL.equals("1ASC")) {
    sortCol1 = "<u>ECR No</u>";
} else if(sortSQL.equals("1DESC")) {
    sortCol1 = "<u>ECR No</u>";
}
String sortCol2 = messageService.getString("e3ps.message.ket_message", "00209")/*ECR 제목*/;
if(sortSQL.equals("2ASC")) {
    sortCol2 = "<u>" + messageService.getString("e3ps.message.ket_message", "00209")/*ECR 제목*/ + "</u>";
} else if(sortSQL.equals("2DESC")) {
    sortCol2 = "<u>" + messageService.getString("e3ps.message.ket_message", "00209")/*ECR 제목*/ + "</u>";
}
String sortCol5 = messageService.getString("e3ps.message.ket_message", "02431")/*작성자*/;
if(sortSQL.equals("5ASC")) {
    sortCol5 = "<u>" + messageService.getString("e3ps.message.ket_message", "02431")/*작성자*/ + "</u>";
} else if(sortSQL.equals("5DESC")) {
    sortCol5 = "<u>" + messageService.getString("e3ps.message.ket_message", "02431")/*작성자*/ + "</u>";
}
String sortCol6 = messageService.getString("e3ps.message.ket_message", "02429")/*작성일자*/;
if(sortSQL.equals("6ASC")) {
    sortCol6 = "<u>" + messageService.getString("e3ps.message.ket_message", "02429")/*작성일자*/ + "</u>";
} else if(sortSQL.equals("6DESC")) {
    sortCol6 = "<u>" + messageService.getString("e3ps.message.ket_message", "02429")/*작성일자*/ + "</u>";
}
String sortCol7 = messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/;
if(sortSQL.equals("7ASC")) {
    sortCol7 = "<u>" + messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/ + "</u>";
} else if(sortSQL.equals("7DESC")) {
    sortCol7 = "<u>" + messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/ + "</u>";
}

int pagerow = 0;
if(data.getTotalCount() > 0) {
    pagerow = data.getResultRows();
}

%>
<% if (pagerow > 10) { %>
<div id="div_scroll" style="width:780px; height:245px; overflow:auto;">
<table border="0" cellspacing="0" cellpadding="0" width="762" >
<% } else { %>
    <table border="0" cellspacing="0" cellpadding="0" width="780" >
<% } %>
        <% if (pagerow > 10) { %>
        <tr class="headerLock">
        <% } else {%>
        <tr>
    <% } %>
          <td width='40' class="tdblueM">No1111</td>
          <td width='40' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width='70' class="tdblueM"><a href=""><span id="sortCol1"><%=sortCol1%></span><a></td>
          <% if (pagerow > 10) { %>
          <td width='212' class="tdblueM">
          <% } else {%>
          <td width='230' class="tdblueM">
          <% } %>
          <a href=""><span id="sortCol2"><%=sortCol2%></span><a></td>
          <td width='80' class="tdblueM">Project No</td>
          <td width='80' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
          <td width='80' class="tdblueM"><a href=""><span id="sortCol5"><%=sortCol5%></span><a></td>
          <td width='80' class="tdblueM"><a href=""><span id="sortCol6"><%=sortCol6%></span><a></td>
          <td width='80' class="tdblueM0"><a href=""><span id="sortCol7"><%=sortCol7%></span><a></td>
        </tr>
<%
int total = data.getTotalCount();
if(data.getTotalCount() > 0) {
    int size = data.getResultRows();
    KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
    int rowCount = (control.getCurrentPage() - 1) * control.getInitPerPage();
    for(int i=0; i<size; i++) {
        ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(i);
        rowCount++;
%>
        <tr>
            <td class="tdwhiteM"><%=total -( i + ( control.getInitPerPage() * (control.getCurrentPage() - 1) ) )%></td>
            <td class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getProdMoldClsName())%></td>
            <td class="tdwhiteM"><a href="javascript:parent.viewDetail('<%=ketSearchEcoDetailVO.getOid()%>','<%=ketSearchEcoDetailVO.getProdMoldClsName()%>');"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId())%></a></td>
            <td class="tdwhiteL"><span class="nameCut" title='<%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoName()) %>' ><%=ketSearchEcoDetailVO.getEcoName()%></span></td>
            <td class="tdwhiteM"><a href="javascript:parent.viewProjectPopup('<%=ketSearchEcoDetailVO.getProjectOid()%>');">&nbsp;<%=StringUtil.checkNull(ketSearchEcoDetailVO.getProjectNo())%>&nbsp;</a>&nbsp;</td>
            <td class="tdwhiteM"><span class="deptCut" title='<%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDeptName()) %>' ><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDeptName())%></span></td>
            <td class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName())%></td>
            <td class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDate())%></td>
            <td class="tdwhiteM0"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag())%></td>
        </tr>
<%
    }
} else {
%>
        <tr>
            <td colspan="9" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
        </tr>
<%
}
%>
    </table>
<% if (pagerow > 10) { %>
</div>
<% } %>

<%if(control != null) {%>
<%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
<%}%>
