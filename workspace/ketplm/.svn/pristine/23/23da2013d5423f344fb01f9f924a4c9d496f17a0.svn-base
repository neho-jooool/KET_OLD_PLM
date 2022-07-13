<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />

<script type="text/javascript">

$(document).ready(function(){
    var sMsg = "${bSuccess}";
    if(sMsg == 'Y') {
    	alert("저장하였습니다.");
    }else{
        alert("저장시 오류가 발생되었습니다.\로그파일을 확인해주십시요!");
    }
    parent.reload();

});
 
    
</script>

 