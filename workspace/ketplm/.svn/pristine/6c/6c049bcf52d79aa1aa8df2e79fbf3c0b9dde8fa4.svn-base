<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamProjectTeamProcessList.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid End -->

<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07280") %></div>
<div align="right"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>
<!-- EJS TreeGrid Start -->
<div class="content-main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
        </div>
    </div>
</div>
<!-- EJS TreeGrid Start -->
</body>

<script type="text/javascript">
$(document).ready(function(){
	teamProjectTeamProcessList.createPaingGrid("${dashBoardDTO.departName}","${dashBoardDTO.displayCode}","${dashBoardDTO.startDate}","${dashBoardDTO.endDate}");
});
</script>