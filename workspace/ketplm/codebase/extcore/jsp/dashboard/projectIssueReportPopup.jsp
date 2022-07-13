<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/projectIssueReport.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid End -->

<%-- <table border="1">
    <tr>
        <td>No</td>
        <td>Project No</td>
        <td>Project Name</td>
        <td>구분</td>
        <td>제목</td>
        <td>긴급도</td>
        <td>중요도</td>
        <td>제기자</td>
        <td>제기일</td>
        <td>완료여부</td>
    </tr>
    <c:forEach var="issueReportInfo" items="${issueReportInfo}" varStatus="issueState">
    <tr>
        <td>${issueState.count}</td>
        <td><a href="javascript:viewProject('${issueReportInfo.oid}')">${issueReportInfo.pjtNo}</a></td>
        <td>${issueReportInfo.pjtName}</td>
        <td>
            <c:if test="${issueReportInfo.issueType eq 'PRODUCT'}">
            <%=messageService.getString("e3ps.message.ket_message", "02536")%>
            </c:if>
            <c:if test="${issueReportInfo.issueType eq 'MOLD'}">
            <%=messageService.getString("e3ps.message.ket_message", "00997")%>
            </c:if>
            <c:if test="${issueReportInfo.issueType eq 'CUSTOMER'}">
            <%=messageService.getString("e3ps.message.ket_message", "00828")%>
            </c:if>
            <c:if test="${issueReportInfo.issueType eq 'QUALITY'}">
            <%=messageService.getString("e3ps.message.ket_message", "03024")%>
            </c:if>
            <c:if test="${issueReportInfo.issueType eq 'COST'}">
            <%=messageService.getString("e3ps.message.ket_message", "01640")%>
            </c:if>
            <c:if test="${issueReportInfo.issueType eq 'SCHEDULE'}">
            <%=messageService.getString("e3ps.message.ket_message", "02348")%>
            </c:if>
            <c:if test="${issueReportInfo.issueType eq 'ETC'}">
            <%=messageService.getString("e3ps.message.ket_message", "01136")%>
            </c:if>
        </td>
        <td><a href="javascript:viewIssue('${issueReportInfo.issueOid}');">${issueReportInfo.subject}</a></td>
        <td>${issueReportInfo.urgency}</td>
        <td>${issueReportInfo.importance}</td>
        <td>${issueReportInfo.userName}</td>
        <td>${issueReportInfo.createDate}</td>
        <td>
            <c:if test="${issueReportInfo.isDone eq '1'}">
            <%=messageService.getString("e3ps.message.ket_message", "02171")%>
            </c:if>
            <c:if test="${issueReportInfo.isDone eq '0'}">
            <%=messageService.getString("e3ps.message.ket_message", "02726")%>
            </c:if>
        </td>
    </tr>
    </c:forEach>
</table> --%>
<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" />이슈 <%=messageService.getString("e3ps.message.ket_message", "07279") %></div>
<div align="right"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>
<!-- EJS TreeGrid Start -->
<div class="content-main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="issueGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
        </div>
    </div>
</div>
<!-- EJS TreeGrid Start -->
</body>
<script type="text/javascript">
$(document).ready(function(){
    
    //alert("${dashBoardDTO.type}"+"  "+"${dashBoardDTO.state}"+"  "+"${dashBoardDTO.year}"+"  "+"${dashBoardDTO.month}");
    
    Issue.createPaingGrid("${dashBoardDTO.type}","${dashBoardDTO.state}","${dashBoardDTO.year}","${dashBoardDTO.month}","${dashBoardDTO.moldType}","${dashBoardDTO.division}");
});

function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
}

function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","750","700","status=no,scrollbars=yes,resizable=no");
}
</script>