<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/projectReport.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid End -->


<%-- <jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<table border="1">
    <tr>
        <td rowspan="2">No</td>
        <td rowspan="2">개발구분</td>
        <td rowspan="2">Project No</td>
        <td rowspan="2">Project Name</td>
        <td rowspan="2">개발시작일</td>
        <td colspan="2">개발완료</td>
        <td rowspan="2">진행일</td>
        <td rowspan="2">주관부서</td>
        <td colspan="3">비용(단위:천원)</td>
        <td rowspan="2">진행현황</td>
    </tr>
    <tr>
        <td>계획일</td>
        <td>실적일</td>
        <td>예산</td>
        <td>실적1</td>
        <td>실적2</td>
    </tr>
    <c:forEach var="projectReportInfo" items="${projectReportInfo}" varStatus="state">
    <tr>
        <td>${state.count}</td>
        <td>${projectReportInfo.type}</td>
        <td><a href="javascript:viewProject('${projectReportInfo.mOid}');">${projectReportInfo.pjtNo}</a></td>
        <td>${projectReportInfo.pjtName}</td>
        <td><fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${projectReportInfo.planStartDate}" /></td>
        <td><fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${projectReportInfo.planEndDate}" /></td>
        <td><fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${projectReportInfo.execEndDate}" /></td>
        <td>${projectReportInfo.term}</td>
        <td>${projectReportInfo.outsourcing}</td>
        <td>${projectReportInfo.budget}</td>
        <td>${projectReportInfo.totalPrice}</td>
        <td>${projectReportInfo.moldTotalPrice}</td>
        <td>
        <c:if test="${projectReportInfo.stateState eq 'COMPLETED' }">
        <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_green_bar.gif">
        </c:if>
        <c:if test="${projectReportInfo.stateState eq 'PROGRESS' }">
            <c:if test="${projectReportInfo.pjtState eq '4'}">
                <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_red_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
            </c:if>
            <c:if test="${projectReportInfo.pjtState eq '3'}">
                <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_yellow_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
            </c:if>
            <c:if test="${projectReportInfo.pjtState ne '4' || taskReportInfo.pjtState ne '3'}">
                <img src="../../portal/images/state_blue_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
            </c:if>
        </c:if>
        <c:if test="${projectReportInfo.stateState ne 'COMPLETED' && projectReportInfo.stateState ne 'PROGRESS'}">
        <img src="../../portal/images/state_blue_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
        </c:if>
        </td>
    </tr>
    </c:forEach>
</table> --%>
<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07280") %></div>
<div align="right"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>
<form name="SampleSearchForm">
<!-- EJS TreeGrid Start -->
<div class="content-main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
        </div>
    </div>
</div>
<!-- EJS TreeGrid Start -->
</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
	//alert("${dashBoardDTO.type}"+"  "+"${dashBoardDTO.state}");
	
	Sample.createPaingGrid("${dashBoardDTO.type}","${dashBoardDTO.state}","${dashBoardDTO.year}","${dashBoardDTO.month}","${dashBoardDTO.moldType}","${dashBoardDTO.division}");
});
function viewProject(oid){
	  openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
	}
</script>