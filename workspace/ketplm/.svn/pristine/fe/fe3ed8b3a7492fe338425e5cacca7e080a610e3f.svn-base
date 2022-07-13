<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<script>
$(document).ready(function(){
	$("body").removeClass("body-space");
	$("body").addClass("popup-background");
	$("body").addClass("popup-space");
	
	$("input:checkbox[name='oid']").click(function(){
		var isChecked = $(this).is(":checked");
		$("input:checkbox[name='oid']").prop("checked", false);
		$(this).prop("checked", isChecked);
	});
	$("label").css("cursor", "pointer");
	
	$(".commaFormat").each(function(){
		var caseSalesTargetCost = $(this).text();
		$(this).text(caseSalesTargetCost.commaFormat(1));
	})
	
	//############## CASE 생성 #############################
	window.createCase = function(){
		var oid = $("input:checkbox[name='oid']:checked").val();
		
		if(oid == null){
			alert("복사할 산출안을 선택하세요.");
			return;
		}
		
		if(!confirm("CASE를 생성하시겠습니까?")) return;
		
		var param = {
			    oid : oid,
			    baseOid : "${oid}",
		}
		
		ajaxCallServer("/plm/ext/cost/createCasePart", param, function(data){
			location.reload();
			
			if(opener != null && typeof(opener.getCaseList) == "function"){
				opener.getCaseList();
			}
		});
	}
	
	window.openCostCalcPopup = function(oid, taskOid){
		getOpenWindow2("/plm/ext/cost/costPartCalculatePopup.do?&oid=" + oid + "&taskOid=" + taskOid, oid, 1280, 720);
	}
});
</script>
<div class="contents-wrap">
	<div class="sub-title b-space5">
		<img src="/plm/portal/images/icon_3.png" />산출案  Copy 대상 조회
	</div>
    <div class="b-space5 float-r">
    	<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center"> <a href="javascript:createCase();" class="btn_blue">CASE 생성</a></span>
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
	<div class="b-space5">
		<table summary="" class="table-style-01">
			<thead>
				<tr>
					<td class="center bgcol fontb">임시품번</td>
					<td class="center bgcol fontb">제품명</td>
					<td class="center bgcol fontb">버전</td>
					<td class="center bgcol fontb">판매목표가</td>
					<td class="center bgcol fontb">총원가</td>
					<td class="center bgcol fontb">수익률</td>
					<td class="center bgcol fontb">CASE 순서</td>
					<td class="center bgcol fontb">비고(산출조건)</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="part" varStatus="stat">
				<tr>
					<td class="center">
						<label><input type="checkbox" name="oid" value="${part.oid }" style="position:relative;top:-1px;"/> 
						${part.partNo }</label>
					</td>
					<td class="center">${part.partName }</td>
					<td class="center"><a href="javascript:openCostCalcPopup('${part.oid }','${part.taskOid }');">${part.version }.${part.subVersion }</a></td>
					<td class="center commaFormat">${part.salesTargetCostExpr}</td>
					<td class="center commaFormat">${part.productCostTotal}</td>
					<td class="center "><span class="commaFormat">${part.profitRateExpr * 100}</span>%</td>
					<td class="center">${part.caseOrder}</td>
					<td class="center">${part.caseNote }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

