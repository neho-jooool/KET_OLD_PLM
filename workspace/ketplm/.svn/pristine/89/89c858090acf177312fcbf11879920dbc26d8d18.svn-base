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
-->
</style>
<script language="javascript">

//페이지 조회
function doSort(sortColId, sortColName){
    var objColId = document.getElementById(sortColId);
    var title = objColId.innerText;
    var sortSQL = "";
    if(title.indexOf("▲") > -1) {
        sortSQL = sortColName + " DESC ";
        objColId.innerText = title.replace("▲", "▼");
    } else if(title.indexOf("▼") > -1) {
        sortSQL = sortColName + " ASC ";
        objColId.innerText = title.replace("▼", "▲");
    } else {
        sortSQL = sortColName + " ASC ";
        objColId.innerText = title + "(▲)";
    }
    parent.doSort(sortSQL);
}

</script>
</head>
<body>
<%
String sortCol1 = messageService.getString("e3ps.message.ket_message", "00199")/*ECO번호*/;
String sortSQL = data.getSortColumn().replace(" ", "");
if(sortSQL.equals("1ASC")) {
    sortCol1 += "(▲)";
} else if(sortSQL.equals("1DESC")) {
    sortCol1 += "(▼)";
}
String sortCol2 = messageService.getString("e3ps.message.ket_message", "00193")/*ECO 제목*/;
if(sortSQL.equals("2ASC")) {
    sortCol2 += "(▲)";
} else if(sortSQL.equals("2DESC")) {
    sortCol2 += "(▼)";
}
String sortCol6 = messageService.getString("e3ps.message.ket_message", "02147")/*예산확보여부*/;
if(sortSQL.equals("6ASC")) {
    sortCol6 += "(▲)";
} else if(sortSQL.equals("6DESC")) {
    sortCol6 += "(▼)";
}
String sortCol7 = messageService.getString("e3ps.message.ket_message", "02431")/*작성자*/;
if(sortSQL.equals("7ASC")) {
    sortCol7 += "(▲)";
} else if(sortSQL.equals("7DESC")) {
    sortCol7 += "(▼)";
}
String sortCol8 = messageService.getString("e3ps.message.ket_message", "02429")/*작성일자*/;
if(sortSQL.equals("8ASC")) {
    sortCol8 += "(▲)";
} else if(sortSQL.equals("8DESC")) {
    sortCol8 += "(▼)";
}
String sortCol9 = messageService.getString("e3ps.message.ket_message", "00771")/*결재상태*/;
if(sortSQL.equals("9ASC")) {
    sortCol9 += "(▲)";
} else if(sortSQL.equals("9DESC")) {
    sortCol9 += "(▼)";
}

int tableWidth = 40 + 130 + 200 + 150 + 100 + 100 + 100 + 100 + 80;
%>
<table border="0" cellspacing="0" cellpadding="0" width="<%=tableWidth%>">
<col width='40'><col width='130'><col width='200'><col width='150'><col width='100'><col width='100'><col width='100'><col width='100'><col width=''>
<tr>
  <td width="" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
  <td width="" class="tdblueM"><a href="javascript:doSort('sortCol1', '1');"><span id="sortCol1"><%=sortCol1%></span><a></td>
  <td width="" class="tdblueM"><a href="javascript:doSort('sortCol2', '2');"><span id="sortCol2"><%=sortCol2%></span><a></td>
  <td width="" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%></td>
  <td width="" class="tdblueM"><a href="javascript:doSort('sortCol6', '6');"><span id="sortCol6"><%=sortCol6%></span><a></td>
  <td width="" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
  <td width="" class="tdblueM"><a href="javascript:doSort('sortCol7', '7');"><span id="sortCol7"><%=sortCol7%></span><a></td>
  <td width="" class="tdblueM"><a href="javascript:doSort('sortCol8', '8');"><span id="sortCol8"><%=sortCol8%></span><a></td>
  <td width="" class="tdblueM0"><a href="javascript:doSort('sortCol9', '9');"><span id="sortCol9"><%=sortCol9%></span><a></td>
</tr>
<%
if(data.getTotalCount() > 0) {

    int size = data.getResultRows();
    KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
    int rowCount = (control.getCurrentPage() - 1) * control.getInitPerPage();
    for(int i=0; i<size; i++) {
        ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(i);
        rowCount++;
%>
<tr>
<td width="" class="tdwhiteM"><%=rowCount%></td>
<td width="" class="tdwhiteM"><a href="javascript:parent.viewDetail('<%=ketSearchEcoDetailVO.getOid()%>');"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId())%></a>&nbsp;</td>
<!--td width="" class="tdwhiteM"><a href="javascript:parent.viewRelEco('<%=ketSearchEcoDetailVO.getOid()%>');"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId())%></a>&nbsp;</td-->
<td width="" class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoName())%>&nbsp;</td>

<td width="" class="tdwhiteM"><%=StringUtil.checkNull(KETStringUtil.getTruncatedText(ketSearchEcoDetailVO.getChangeReason(), 5))%>&nbsp;</td>
<td width="" class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getSecureBudgetYn())%>&nbsp;</td>

<td width="" class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDeptName())%>&nbsp;</td>
<td width="" class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName())%>&nbsp;</td>
<td width="" class="tdwhiteM"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDate())%>&nbsp;</td>
<td width="" class="tdwhiteM0"><%=StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag())%>&nbsp;</td>
</tr>
<%
    }
} else {//자료가 존재하지 않은 경우
%>
<tr>
    <td colspan="9" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
</tr>
<%
}
%>
</table>
<%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
