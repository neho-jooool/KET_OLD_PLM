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
width:155;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}

.reasonCut{
width:115;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}
</style>
<script language="javascript">

//페이지 조회
function doSort(sortColId, sortColName){
    var objColId = document.getElementById(sortColId);
    var title = objColId.innerText;
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
String sortCol1 = "ECO No";
String sortSQL = "";
if(data.getTotalCount() > 0) {
    sortSQL = data.getSortColumn().replace(" ", "");
}
if(sortSQL.equals("1ASC")) {
    sortCol1 = "<u>ECO No</u>";
} else if(sortSQL.equals("1DESC")) {
    sortCol1 = "<u>ECO No</u>";
}
String sortCol2 = messageService.getString("e3ps.message.ket_message", "00193")/*ECO 제목*/;
if(sortSQL.equals("2ASC")) {
    sortCol2 = "<u>" + messageService.getString("e3ps.message.ket_message", "00193")/*ECO 제목*/ +"</u>";
} else if(sortSQL.equals("2DESC")) {
    sortCol2 = "<u>" + messageService.getString("e3ps.message.ket_message", "00193")/*ECO 제목*/ +"</u>";
}
String sortCol6 = messageService.getString("e3ps.message.ket_message", "02143")/*예산*/;
if(sortSQL.equals("6ASC")) {
    sortCol6 = "<u>" + messageService.getString("e3ps.message.ket_message", "02143")/*예산*/ + "</u>";
} else if(sortSQL.equals("6DESC")) {
    sortCol6 = "<u>" + messageService.getString("e3ps.message.ket_message", "02143")/*예산*/ + "</u>";
}
String sortCol7 = messageService.getString("e3ps.message.ket_message", "02431")/*작성자*/;
if(sortSQL.equals("7ASC")) {
    sortCol7 = "<u>" + messageService.getString("e3ps.message.ket_message", "02431")/*작성자*/ + "</u>";
} else if(sortSQL.equals("7DESC")) {
    sortCol7 = "<u>" + messageService.getString("e3ps.message.ket_message", "02431")/*작성자*/ + "</u>";
}
String sortCol8 = messageService.getString("e3ps.message.ket_message", "02429")/*작성일자*/;
if(sortSQL.equals("8ASC")) {
    sortCol8 = "<u>" + messageService.getString("e3ps.message.ket_message", "02429")/*작성일자*/ + "</u>";
} else if(sortSQL.equals("8DESC")) {
    sortCol8 = "<u>" + messageService.getString("e3ps.message.ket_message", "02429")/*작성일자*/ + "</u>";
}
String sortCol9 = messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/;
if(sortSQL.equals("9ASC")) {
    sortCol9 = "<u>" + messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/ + "</u>";
} else if(sortSQL.equals("9DESC")) {
    sortCol9 = "<u>" + messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/ + "</u>";
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
          <td width="40" class="tdblueM">No</td>
          <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="70" class="tdblueM"><a href="javascript:doSort('sortCol1', '1');"><span id="sortCol1"><%=sortCol1%></span><a></td>
          <td width="155" class="tdblueM"><a href="javascript:doSort('sortCol2', '2');"><span id="sortCol2"><%=sortCol2%></span><a></td>
          <td width="70" class="tdblueM">Project No</td>
          <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%></td>
          <td width="50" class="tdblueM"><a href="javascript:doSort('sortCol6', '6');"><span id="sortCol6"><%=sortCol6%></span><a></td>
          <td width="70" class="tdblueM"><a href="javascript:doSort('sortCol7', '7');"><span id="sortCol7"><%=sortCol7%></span><a></td>
          <td width="80" class="tdblueM"><a href="javascript:doSort('sortCol8', '8');"><span id="sortCol8"><%=sortCol8%></span><a></td>
          <% if (pagerow > 10) { %>
          <td width="68" class="tdblueM0">
          <% } else {%>
          <td width="86" class="tdblueM0">
          <% } %>
              <a href="javascript:doSort('sortCol9', '9');"><span id="sortCol9"><%=sortCol9%></span><a></td>
        </tr>
<%
int total = data.getTotalCount();
if(data.getTotalCount() > 0) {
    int size = data.getResultRows();
    KETSearchEcoDetailVO ketSearchEcoDetailVO = null;

    for(int i=0; i<data.getResultRows(); i++) {
        ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(i);
%>
        <tr>
            <td class="tdwhiteM"><%=total -( i + ( control.getInitPerPage() * (control.getCurrentPage() - 1) ) )%></td>
            <td class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getProdMoldClsName())%></td>
            <td class="tdwhiteM"><a href="javascript:parent.viewDetail('<%=ketSearchEcoDetailVO.getOid()%>','<%=ketSearchEcoDetailVO.getProdMoldClsName()%>');"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId())%></a></td>
            <td class="tdwhiteL"><span class="nameCut" title='<%=ketSearchEcoDetailVO.getEcoName() %>' ><%=ketSearchEcoDetailVO.getEcoName()%></span></td>
            <td class="tdwhiteM"><a href="javascript:parent.viewProjectPopup('<%=ketSearchEcoDetailVO.getProjectOid()%>');">&nbsp;<%=StringUtil.checkNull(ketSearchEcoDetailVO.getProjectNo())%>&nbsp;</a>&nbsp;</td>
            <td class="tdwhiteL"><span class="reasonCut" title='<%=StringUtil.checkNull(ketSearchEcoDetailVO.getChangeReason()+ketSearchEcoDetailVO.getOtherChgReason())%>' ><%=StringUtil.checkNull(ketSearchEcoDetailVO.getChangeReason()+ketSearchEcoDetailVO.getOtherChgReason())%></span></td>
            <td class="tdwhiteM">&nbsp;<%=StringUtil.checkNull(ketSearchEcoDetailVO.getSecureBudgetYn())%>&nbsp;</td>
            <td class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName())%>&nbsp;</td>
            <td class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDate())%>&nbsp;</td>
            <td class="tdwhiteM0"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag())%>&nbsp;</td>
        </tr>
<%
    }
} else {//자료가 존재하지 않은 경우
%>
        <tr>
            <td colspan="11" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
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
