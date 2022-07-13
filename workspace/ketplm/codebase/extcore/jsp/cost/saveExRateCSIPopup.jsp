<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
var MONETARYUNIT = ${MONETARYUNIT}
$(document).ready(function(){
	
	onResizeTables();
	
	$(window).resize(function(){
		onResizeTables();
	});
});


window.onResizeTables = function(){
	
	var tdWidthList = new Array();
	
	$("#exRateStdInfo tr:first td").each(function(i){
		tdWidthList.push($(this).width());
	}); 
	
	$("#stdInfo1 tr:first td:eq(1)").width(tdWidthList[1] - 25);
	$("#stdInfo1 tr:first td:eq(2)").width(tdWidthList[2]);
}
window.addItem = function(id){
	var tr = "<tr>";
	
	tr += "<td style='width:25px;'><img src='/plm/portal/images/iconMinus.gif' class='deleteItem' onclick='deleteItem(this)'/></td>";
	tr += "<td><select name='item' class='editSelect'><option value=''>선택</option>";
	for(var i = 0; i < MONETARYUNIT.length; i++){
		var monetaryUnit = MONETARYUNIT[i];
		tr += "<option value='" + monetaryUnit.code + "'>" + monetaryUnit.name +"</option>";
	}
	
	tr += "</select></td>";
	tr += "<td><input type='text' name='cost' class='edit'/></td>";
	tr += "</tr>";
	$("#" + id + " tbody").append(tr);
}
window.deleteItem = function(obj){
	$(obj).parents("tr:first").remove();
}


//입력정보 가져오기
window.getCSInfoItems = function(){
	
	var stdInfo1 = new Array();
	
	$("#stdInfo1 tbody tr").each(function(){
		var var1 = $(this).find("select[name='item']").val();
		var var2 = $(this).find("input[name='cost']").val();
		
		stdInfo1.push({ value1 : var1, value2 : var2, itemType : "EXRATECOST" });
	});
	

	var data = {
			stdInfo1 : stdInfo1
	}
	return data;
}

window.saveCSInfo = function(){
	
	if(confirm("저장하시겠습니까?")){
		var param = new Object();
	    param = getCSInfoItems();
	    param.mode = "SAVE";
	    param.infoType = "EXRATE";
	    
	    ajaxCallServer("/plm/ext/cost/saveCSInfo", param, function(res){
	        location.href="/plm/ext/cost/viewExRateCSIPopup";
	    });
	}
	
}

window.deleteCSInfo = function(){
	
	if(confirm("삭제하시겠습니까?")){
		var param = new Object();
	    param.infoType = "EXRATE";
	    ajaxCallServer("/plm/ext/cost/deleteCSInfo", param, function(res){
	        location.href="/plm/ext/cost/viewExRateCSIPopup";
	    });
	}
}
</script>
<body class="popup-background popup-space">
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />환율정보
		</div>
		<div class="b-space5 float-r" style="text-align: right">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:saveCSInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<c:if test="${!empty csInfo}">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:deleteCSInfo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:location.href='/plm/ext/cost/viewExRateCSIPopup';" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			</c:if>
			<c:if test="${empty csInfo}">
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			</c:if>
		</div>
		<div class="sub-title-02 float-l b-space5">
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>환율정보 등록/수정
		</div>
		<div class="b-space5" >
			<table summary="" id="exRateStdInfo" class="table-style-01 infoTable">
					<cols>
						<col width="30%"/>
						<col width="35%"/>
						<col width="35%"/>
					</cols>
					<tbody>
						<tr>
							<td class="center bgcol fontb" rowspan="2">환율단가</td>
							<td class="center bgcol fontb">버전</td>
							<td>
								<c:if test="${empty csInfo}">
									신규
								</c:if>
								${version }
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table summary="" id="stdInfo1" class="table-style-01">
									<thead>
										<tr>
											<td class="" width="25px"></td>
											<td class="center bgcol fontb">단위 <img src="/plm/portal/images/iconPlus.gif" class="addItem" onclick="addItem('stdInfo1')"/></td>
											<td class="center bgcol fontb">금액</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="csInfoItem" items="${csInfoItemList}">
											<c:if test="${csInfoItem.itemType eq 'EXRATECOST' }">
											<tr>
											<td style='width:25px'><img src='/plm/portal/images/iconMinus.gif' class="deleteItem" onclick='deleteItem(this)'/></td>
											<td>
												<select name='item' class='editSelect'><option value=''>선택</option></select>
												<script>
													for(var i = 0; i < MONETARYUNIT.length; i++){
														var item = MONETARYUNIT[i];
														tr = "<option value='" + item.code + "'>" + item.name +"</option>";
														$("select[name='item']:last").append(tr);
													}
													$("select[name='item']:last").val("${csInfoItem.value1}").prop("selected", true);
												</script>
											</td>
											<td><input type='text' name='cost' class='edit' value='${csInfoItem.value2 }' /></td>
											</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
</body>
