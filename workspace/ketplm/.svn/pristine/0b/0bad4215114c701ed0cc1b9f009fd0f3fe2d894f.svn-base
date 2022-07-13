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
	
	onResizeTables();
	
	$(window).resize(function(){
		onResizeTables();
	});
	
	$("select[name='version']").val("${oid}").prop("selected", true);
	$("select[name='rMatCode']").val("${rMatCode}").prop("selected", true);
	
	$("select[name='version']").change(function(e){
		location.href="/plm/ext/cost/viewNMetalCSIPopup?oid=" + $("select[name='version']").val() + "&rMatCode=" +$("select[name='rMatCode']").val();
	});
	$("select[name='rMatCode']").change(function(e){
		location.href="/plm/ext/cost/viewNMetalCSIPopup?rMatCode=" + $("select[name='rMatCode']").val();
	});
});

window.onResizeTables = function(){
	
	var tdWidthList = new Array();
	
	$("#nMetalStdInfo tr:first td").each(function(i){
	    tdWidthList.push($(this).width());
	}); 
	
	$("#stdInfo1 tr:first td:eq(0)").width(tdWidthList[1]);
	$("#stdInfo1 tr:first td:eq(1)").width(tdWidthList[2]);
	
	var width = tdWidthList[3] + tdWidthList[4];
	var width2 = tdWidthList[5];
	$("#stdInfo1 tr:first td:eq(2)").width(width + 1);
	$("#stdInfo1 tr:first td:eq(3)").width(width2);
	
	$("#stdInfo2 tr:first td:eq(0)").width(tdWidthList[1] + tdWidthList[2]);
	$("#stdInfo2 tr:first td:eq(1)").width(width + 1);
	$("#stdInfo2 tr:first td:eq(2)").width(width2);
	
	$("#stdInfo3 tr:first td:eq(0)").width(tdWidthList[1]);
	$("#stdInfo3 tr:first td:eq(1)").width(tdWidthList[2]);
	$("#stdInfo3 tr:first td:eq(2)").width(width + 1);
	$("#stdInfo3 tr:first td:eq(3)").width(width2);
	
	$("#stdInfo4 tr:first td:eq(0)").width(tdWidthList[0]);
	$("#stdInfo4 tr:first td:eq(1)").width(tdWidthList[1]);
	$("#stdInfo4 tr:first td:eq(2)").width(tdWidthList[2]);
	$("#stdInfo4 tr:first td:eq(3)").width(width + 1);
	$("#stdInfo4 tr:first td:eq(4)").width(width2);
	
	$("#stdInfo5 tr:first td:eq(0)").width(tdWidthList[0]);
    $("#stdInfo5 tr:first td:eq(1)").width(tdWidthList[1]);
    $("#stdInfo5 tr:first td:eq(2)").width(tdWidthList[2]);
    $("#stdInfo5 tr:first td:eq(3)").width(width + 1);
    $("#stdInfo5 tr:first td:eq(4)").width(width2);
	
}

window.reviseCSInfo = function(){
	if(confirm("개정하시겠습니까?")){
		var param = new Object();
	    param.mode = "REVISE";
	    param.oid = $("#oid").val();
	    ajaxCallServer("/plm/ext/cost/saveCSInfo", param, function(res){
	        location.href="/plm/ext/cost/viewNMetalCSIPopup?&rMatCode=${rMatCode}&oid=" + res.oid;
	    });
	}
}

</script>
<c:if test="${empty csInfo}">
<script>
location.href="/plm/ext/cost/saveNMetalCSIPopup?oid=${oid }&rMatCode=${rMatCode}";
</script>
</c:if>
<body class="popup-background popup-space">
<input type="hidden" name="oid" id="oid" value="${oid}"/>
	<div class="contents-wrap">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />비철 원가기준정보
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
					<span class="pro-cell b-center"> <a href="/plm/ext/cost/saveNMetalCSIPopup?oid=${oid }&rMatCode=${rMatCode}" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%></a></span>
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
			<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>비철원가기준정보 상세보기
		</div>
		<div class="b-space5" >
			<table id="nMetalStdInfo" summary="" class="table-style-01 infoTable">
				<colgroup>
					<col width="15%" />
					<col width="25%" />
					<col width="25%" />
					<col width="10%" />
					<col width="10%" />
					<col width="20%" />
				</colgroup>
				<tbody>
					<tr>
						<td class="center bgcol fontb">원재료 코드</td>
						<td>
							<select name="rMatCode" class='edit'>
								<c:forEach var="rMatCode" items="${rMatCodeList}">
									<option value="${rMatCode.code}">${rMatCode.name }</option>
								</c:forEach>
							</select>
						</td>
						<td class="center bgcol fontb">비중</td>
						<td>
							${csInfo.importance}
						</td>
						<td class="center bgcol fontb">버전</td>
						<td>
							<select name="version" class='edit'>
								<c:forEach var="version" items="${versionList}">
									<option value="${version.oid}">${version.version }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="center bgcol fontb">비철단가</td>
						<td colspan="5" >
							<table id="stdInfo1" class="infoTable">
								<thead>
									<tr>
										<td class="center bgcol fontb" >시나리오</td>
										<td class="center bgcol fontb">LME 기준</td>
										<td class="center bgcol fontb">금액</td>
										<td class="center bgcol fontb">화폐단위</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="csInfoItem" items="${csInfoItemList}">
										<c:if test="${csInfoItem.itemType eq 'NMETALCOST' }">
											<tr>
												<td>${csInfoItem.value1CodeName }</td>
												<td>${csInfoItem.value2 }</td>
												<td>${csInfoItem.value3 }</td>
												<td>${csInfoItem.mUnitCodeName }</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td class="center bgcol fontb">도금단가</td>
						<td colspan="5" >
							<table id="stdInfo2" class="infoTable">
								<thead>
									<tr>
										<td class="center bgcol fontb" >두께</td>
										<td class="center bgcol fontb">금액</td>
										<td class="center bgcol fontb">화폐단위</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="csInfoItem" items="${csInfoItemList}">
										<c:if test="${csInfoItem.itemType eq 'PLATINGCOST' }">
											<tr>
												<td>${csInfoItem.value1CodeName }</td>
												<td>${csInfoItem.value2 }</td>
												<td>${csInfoItem.mUnitCodeName }</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td class="center bgcol fontb">절단비</td>
						<td colspan="5" >
							<table id="stdInfo3" class="infoTable">
								<thead>
									<tr>
										<td class="center bgcol fontb" >폭</td>
										<td class="center bgcol fontb" >두께</td>
										<td class="center bgcol fontb" >금액</td>
										<td class="center bgcol fontb">화폐단위</td>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="csInfoItem" items="${csInfoItemList}">
									<c:if test="${csInfoItem.itemType eq 'CUTTINGCOST' }">
									<c:if test="${cuttingWidth ne csInfoItem.value1 }">
										<c:set var="cuttingWidth" value="${csInfoItem.value1}" ></c:set>
										<tr data-rowspan="true">
										<td rowspan='1'>${csInfoItem.value1CodeName }</td>
									</c:if>
									<script>
									var rowspan = $("#stdInfo3 tr[data-rowspan]:last td:eq(0)").attr("rowspan");
									if(rowspan){
										window.console.log(rowspan);
										rowspan = parseInt(rowspan) + 1;
										$("#stdInfo3 tr[data-rowspan]:last td:eq(0)").attr("rowspan", rowspan);
									}
									</script>
									<c:if test="${cuttingWidth eq csInfoItem.value1 }"><tr></c:if>
									<td>${csInfoItem.value2CodeName }</td>
									<td>${csInfoItem.value3 }</td>
									<td>${csInfoItem.mUnitCodeName }</td>
									</tr>
									</c:if>
								</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
<%-- 					<tr>
						<td class="center bgcol fontb">재생단가(국내)</td>
						<td>
							${csInfo.scrapInternal}
						</td>
						<td class="center bgcol fontb">스크랩(중국)</td>
						<td colspan="3">
							${csInfo.scrapChina}
						</td>
					</tr> --%>
				</tbody>
			</table>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space20"></td>
                </tr>
            </table>
            <div class="sub-title-02 b-space15 clear">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>스크랩 재생비 설정
            </div>
			<table id="stdInfo4" summary="" class="table-style-01 infoTable">
				<thead>
					<tr>
					    <td class="center bgcol fontb">생산국</td>
						<td class="center bgcol fontb">선도금사양</td>
						<td class="center bgcol fontb">De-Tin</td>
						<td class="center bgcol fontb">임가공비</td>
						<td class="center bgcol fontb">화폐단위</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="csInfoItem" items="${csInfoItemList}">
						<c:if test="${csInfoItem.itemType eq 'SCRAPRECYCLECOST' }">
						<tr>
						<td>${csInfoItem.value4CodeName }</td>
						<td>${csInfoItem.value1CodeName }</td>
						<td>${csInfoItem.value2 }</td>
						<td>${csInfoItem.value3 }</td>
						<td>${csInfoItem.mUnitCodeName }</td>
						</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space20"></td>
                </tr>
            </table>
            <div class="sub-title-02 b-space15 clear">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>스크랩 판매비 설정
            </div>
			<table id="stdInfo5" summary="" class="table-style-01 infoTable">
                <thead>
                    <tr>
                        <td class="center bgcol fontb">시나리오</td>
                        <td class="center bgcol fontb">생산국</td>
                        <td class="center bgcol fontb">선도금사양</td>
                        <td class="center bgcol fontb">스크랩판매비</td>
                        <td class="center bgcol fontb">화폐단위</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="csInfoItem" items="${csInfoItemList}">
                        <c:if test="${csInfoItem.itemType eq 'SCRAPSALESCOST' }">
                        <tr>
                        <td>${csInfoItem.value5CodeName }</td>
                        <td>${csInfoItem.value1CodeName }</td>
                        <td>${csInfoItem.value2CodeName }</td>
                        <td>${csInfoItem.value3 }</td>
                        <td>${csInfoItem.mUnitCodeName }</td>
                        </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
		</div>
	</div>
</body>
