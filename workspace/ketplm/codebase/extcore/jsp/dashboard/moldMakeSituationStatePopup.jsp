<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/jsp/common/processing.html" %>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<script type="text/javascript">
$(document).ready(function(){
	
	showProcessing();
	
	$.ajax({
	    type       : "POST",
	    url        : "/plm/ext/dashboard/moldMakeSituationPopup?division=${dashBoardDTO.division}",
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

</body>