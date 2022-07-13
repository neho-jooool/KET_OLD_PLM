<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/jsp/common/processing.html" %>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/carDivisionProject.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid End -->
<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07193") %></div>
<div align="right"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue">닫기</a></span><span class="pro-cell b-right"></span></span></span></div>
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
    
  //  alert("${dashBoardDTO.customer}"+" "+"${dashBoardDTO.carType}"+"  "+"${dashBoardDTO.year}");
    
    carDivisionProject.createPaingGrid();
});

</script>


<!-- 
<script type="text/javascript">
$(document).ready(function(){
	
	showProcessing();
	
	$.ajax({
	    type       : "POST",
	    url        : "/plm/ext/dashboard/carDivisionProjectReportPopup",
	    data       : $("#overallStatus").serialize(),
	    dataType   : "html",
	    success    : function(data){
	    	              hideProcessing();
	                      $("#popupDiv").html(data);
	                    
	    },
	    error    : function(xhr, status, error){
	                 alert(xhr+"  "+status+"  "+error);
	                 
	    }
    });  
	
});
</script>

<body class="popup-background popup-space">
<div class="contents-wrap" id="carProjectReport">
    <div id="popupDiv"></div>    
</div>

</body> -->