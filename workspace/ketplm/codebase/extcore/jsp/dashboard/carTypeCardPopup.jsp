<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/jsp/common/processing.html" %>

<script type="text/javascript">
$(document).ready(function(){
	
    showProcessing();
    
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/carTypeCard",
        data       : { customer: "${dashBoardDTO.customer}", carType: "${dashBoardDTO.carType}" },
        dataType   : "html",
        success    : function(data){
                          hideProcessing();
                          $("#carTypeCardPopup").html(data);
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    }); 

 
});

</script>

<body class="popup-background popup-space">
<div class="contents-wrap" id="carTypeCardPopup">
        
</div>
</body>