<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/projectTaskDelayReport.js"></script>
<!-- EJS TreeGrid End -->

<%-- <table border="1">
    <tr>
        <td rowspan="2">No</td>
        <td rowspan="2">기술유형</td>
        <td rowspan="2">Project No</td>
        <td rowspan="2">Project Name</td>
        <td rowspan="2">진행현황</td>
        <td rowspan="2">Task</td>
        <td rowspan="2">Task 주관부서</td>
        <td rowspan="2">Task 담당자</td>
        <td colspan="2">Task 완료</td>
        <c:if test="${debuging eq 'debuging'}">
        <td rowspan="2">디버깅 사유</td>
        </c:if>
    </tr>
    <tr>
        <td>계획일</td>
        <td>실제일</td>
    </tr>
    <c:forEach var="taskReportInfo" items="${taskReportInfo}" varStatus="status" >
     <tr>  
        <td>${status.count}</td>
        <td>${taskReportInfo.moldCategory}</td>
        <td><a href="javascript:viewProjectPopup('${taskReportInfo.oid}');">${taskReportInfo.pjtNo}</a></td>
        <td>${taskReportInfo.pjtName}</td>
        <td>
        <c:if test="${taskReportInfo.state eq 'COMPLETED' }">
        <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_green_bar.gif">
        </c:if>
        <c:if test="${taskReportInfo.state eq 'PROGRESS' }">
            <c:if test="${taskReportInfo.pjtState eq '4'}">
                <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_red_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
            </c:if>
            <c:if test="${taskReportInfo.pjtState eq '3'}">
                <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_yellow_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
            </c:if>
            <c:if test="${taskReportInfo.pjtState ne '4' || taskReportInfo.pjtState ne '3'}">
                <img src="../../portal/images/state_blue_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
            </c:if>
        </c:if>
        <c:if test="${taskReportInfo.state ne 'PROGRESS' && taskReportInfo.state ne 'COMPLETED' }">
        <img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif"><img src="../../portal/images/state_blank_bar.gif">
        </c:if>
        </td>
        <td>${taskReportInfo.taskName}</td>
        <td>${taskReportInfo.departName}</td>
        <td>${taskReportInfo.userName}</td>
        <td><fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${taskReportInfo.planStartDate}" /></td>
        <td><fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${taskReportInfo.execStartDate}" /></td>
        <c:if test="${debuging eq 'debuging' }">
        <td>
            <c:if test="${taskReportInfo.reason eq '1'}">
                <%=messageService.getString("e3ps.message.ket_message", "00850")%>
            </c:if>
            <c:if test="${taskReportInfo.reason eq '2'}">
                <%=messageService.getString("e3ps.message.ket_message", "03027")%>
            </c:if>
            <c:if test="${taskReportInfo.reason eq '3'}">
                <%=messageService.getString("e3ps.message.ket_message", "02601")%>
            </c:if>
            <c:if test="${taskReportInfo.reason eq '4'}">
                <%=messageService.getString("e3ps.message.ket_message", "01063")%>
            </c:if>
            <c:if test="${taskReportInfo.reason eq '5'}">
                <%=messageService.getString("e3ps.message.ket_message", "01136")%>
            </c:if>
            <c:if test="${taskReportInfo.reason eq '6'}">
                <%=messageService.getString("e3ps.message.ket_message", "01784")%>
            </c:if>
        </td>
        </c:if>
     </tr>
    </c:forEach>
</table> --%>
<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" />${dashBoardDTO.state} <%=messageService.getString("e3ps.message.ket_message", "07279") %></div>
<div align="right"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>
<!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="taskListGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                        </div>
<!-- EJS TreeGrid Start -->
</body>                        
                        
<script type="text/javascript">
$(document).ready(function(){
    
   // alert("${dashBoardDTO.type}"+"  "+"${dashBoardDTO.state}"+" "+"${dashBoardDTO.step}"+" "+"${dashBoardDTO.year}"+" "+"{dashBoardDTO.month}");
    
    TaskDelay.createPaingGrid("${dashBoardDTO.type}","${dashBoardDTO.state}","${dashBoardDTO.step}","${dashBoardDTO.year}","${dashBoardDTO.month}","${dashBoardDTO.moldType}","${dashBoardDTO.useCompleteType}","${dashBoardDTO.division}");
});

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
}
</script>
