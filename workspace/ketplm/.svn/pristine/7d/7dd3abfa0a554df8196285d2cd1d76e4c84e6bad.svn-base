<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<c:if test="${not empty ERROR}">
<script>alert("${ERROR}");self.close();</script>
</c:if>
<script>
$(document).ready(function(){
	$("body").removeClass("body-space");
	$("body").addClass("popup-background");
	$("body").addClass("popup-space");
	
	window.downloadExcelForm = function(){
		
		var param = new Object();
		param.taskOid = "${taskOid}";
		
		ajaxCallServer("/plm/ext/cost/createRealPartNoFormExcel", param, function(data){
            location.href = data.downloadLink;
        });
	}
	
	window.uploadExcelForm = function(){
        var param = $("[name=uploadForm]").serializefiles();
        ajaxCallServer("/plm/ext/cost/saveRealPartNo", param, function(data){
            self.close();        	
        });
	}
});
</script>
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="projectOid" value="${projectOid }" />
<input type="hidden" name="taskOid" value="${taskOid }" />
<div class="contents-wrap">
	<div class="sub-title b-space5">
		<img src="/plm/portal/images/icon_3.png" />품번 연계
	</div>
    <div class="b-space5">
        <img src="/plm/portal/images/iocn_excel.png" onclick="downloadExcelForm()" style="cursor:pointer;"/>
	    <input type="file" name="uploadFile" />
	    <span class="in-block v-middle r-space7">
	        <span class="pro-table">
	            <span class="pro-cell b-left"></span>
	            <span class="pro-cell b-center"> <a href="javascript:uploadExcelForm()" class="btn_blue">품번 연계</a></span>
	            <span class="pro-cell b-right"></span>
	        </span>
	    </span>
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></span>
				<span class="pro-cell b-right"></span>
			</span>
		</span>
	</div>
</div>
</form>

