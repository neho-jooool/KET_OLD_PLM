<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<style>
html{
	height:100%;
}
.contents-wrap {
	height:100%;
}
.infoTable {
	table-layout:fixed;
}
.infoTable td{
	padding:0;
	overflow:hidden;
	text-align:center;
}
.infoTable .edit{
	width:100% !important;
	height:30px !important;
	border:0 !important;
	margin:0;padding:0;
	padding:5px;
}
.infoTable .editSelect{
	width:100% !important;
	height:30px !important;
	border:0;
	margin:0;padding:0;
	float:right;
}
.addItem{
	cursor:pointer;
}
.deleteItem{
	cursor:pointer;
	margin:5px;
}
</style>
<script> 
$(document).ready(function(){
	
	$("select[name='version']").val("${oid}").prop("selected", true);
	$("select[name='version']").change(function(e){
		location.href="/plm/ext/cost/viewProfitCSIPopup?oid=" + $(this).val();
	});
});
window.reviseCSInfo = function(){
	if(confirm("개정하시겠습니까?")){
		var param = new Object();
	    param.mode = "REVISE";
	    param.oid = $("#oid").val();
	    ajaxCallServer("/plm/ext/cost/saveCSInfo", param, function(res){
	        location.href="/plm/ext/cost/viewProfitCSIPopup?oid=" + res.oid;
	    });
	}
	
}
</script>
<c:if test="${empty csInfo}">
<script>
location.href="/plm/ext/cost/saveProfitCSIPopup";
</script>
</c:if>
<body class="popup-background popup-space">
<input type="hidden" name="oid" id="oid" value="${oid}"/>
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />수지 원가기준정보
		</div>
		<div class="b-space5 float-r" style="text-align: right">
			<c:if test="${(oid eq latestOid) and not empty oid and ket:isCostAdmin() }">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:reviseCSInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00684")%><%--개정--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="/plm/ext/cost/saveProfitCSIPopup" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			</c:if>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<div class="sub-title-02 float-l b-space5">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>수지 기준단가 상세보기
		</div>
		<div class="b-space5" >
			<table summary="" id="stdInfo1" class="table-style-01 infoTable">
				<thead>
					<tr>
						<td class="center bgcol fontb">버전</td>
						<td colspan="2">
							<select name="version" class='edit'>
								<c:forEach var="version" items="${versionList}">
									<option value="${version.oid}">${version.version }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
					    <td class="center bgcol fontb">생산국</td>
						<td class="center bgcol fontb">항목</td>
						<td class="center bgcol fontb">단가</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="csInfoItem" items="${csInfoItemList}">
						<c:if test="${csInfoItem.itemType eq 'PROFITCOST' }">
						<tr>
						<td>${csInfoItem.value3CodeName }</td>
						<td>${csInfoItem.value1CodeName }</td>
						<td>${csInfoItem.value2 }</td>
						</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
