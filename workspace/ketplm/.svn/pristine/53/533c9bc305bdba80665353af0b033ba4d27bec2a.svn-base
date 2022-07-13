<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<div style="width:516px">
<table cellpadding="0" class="table-style-03" style="width: 518px">
	<colgroup>
		<col width="66px" />
		<col width="58px" />
		<col width="48px" />
		<col width="32px" />
		<col width="26px" />
		<col width="26px" />
		<col width="36px" />
		<col width="26px" />
		<col width="26px" />
		<col width="36px" />
		<col width="26px" />
		<col width="26px" />
		<col width="16px" />
	</colgroup>
	<thead>
		<tr>
			<td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02745") %></td>
			<td colspan="2"><%=messageService.getString("e3ps.message.ket_message", "02729") %></td>
            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "03210") %></td>			
			<td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "09486") %></td>
			<td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "07188") %></td>
			<td colspan="2"><%=messageService.getString("e3ps.message.ket_message", "07189") %></td>
			<td></td>
		</tr>
		<tr>
			<td><%=messageService.getString("e3ps.message.ket_message", "07190") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "07191") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "02171") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "02726") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "02171") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "02726") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "02296") %></td>
			<td><%=messageService.getString("e3ps.message.ket_message", "03024") %></td>
			<td></td>
		</tr>
	</thead>
</table>
</div>
<div style="width: 518px; height: 292px; overflow-y: scroll; overflow-x: hidden; border-bottom: 1px solid #b7d1e2" class="b-space40">
	<table cellpadding="0" class="table-style-03 table-scroll" style="width: 500px; table-layout: fixed;">
		<colgroup>
        <col width="66px" />
        <col width="58px" />
        <col width="48px" />
        <col width="32px" />
        <col width="26px" />
        <col width="26px" />
        <col width="36px" />
        <col width="26px" />
        <col width="26px" />
        <col width="36px" />
        <col width="26px" />
        <col width="26px" />
    </colgroup>
		<tbody>
			<tr>
				<c:forEach var="data" items="${carTypeByDevMakeReportList}">
					<tr>
						<td class="title02" title="${data.carType}(${data.customer})"><a href="javascript:carTypePopup('${data.carType}','${data.customer}')">${data.carType}<br>(<c:if test="${data.customer.length() >= 10}">${data.customerEllipsis}</c:if><c:if test="${data.customer.length() < 10}">${data.customer}</c:if>)</a></td><!--  -->
						<td class="center"  title="${data.currentDate}">${data.currentStep}</br>${data.currentDate1}</td>
						<c:if test="${data.nextDate ne '-'}">
						<td class="center" title="${data.nextDate}">${data.nextStep}</br>${data.nextDate1}</td>
						</c:if>
						<c:if test="${data.nextDate eq '-'}">
                        <td class="center">-</td>
                        </c:if>
                        <td class="center">
                        <c:choose>
                            <c:when test="${data.sopDay <= data.today && data.currentStep == 'SOP'}">
                                <c:if test="${data.sopDay != 0}">
                                    <img src="/plm/extcore/image/ico_red.png" />
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${data.currentGate ne null}">
	                                <c:if test="${data.currentGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
	                                <c:if test="${data.currentGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
	                                <c:if test="${data.currentGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                                 </c:if>
                            </c:otherwise>
                        </c:choose>
                        </td>
						<td class="center"><a href="javascript:productTotalProjectPopup('${data.customer}','${data.carType}')">${data.productTotal}</a></td>
						<td class="center"><a href="javascript:productCompleteProjectPopup('${data.customer}','${data.carType}')">${data.completeCount}</a></td>
						<td class="center"><a href="javascript:productProcessProjectPopup('${data.customer}','${data.carType}')">${data.processCount1}<c:if test="${data.processCount >= 100}"><br></c:if></a><c:if test="${data.delayCount > 0}">/<a href="javascript:productDelayProjectPopup('${data.customer}','${data.carType}')"><span style="color:#ff0000">${data.delayCount}</span></a></c:if></td> <!--  -->
						<td class="center"><a href="javascript:moldTotalProjectPopup('${data.customer}','${data.carType}')">${data.moldTotal}</a></td>
						<td class="center"><a href="javascript:moldCompleteProjectPopup('${data.customer}','${data.carType}')">${data.moldCompleteCount}</a></td>
						<td class="center"><a href="javascript:moldProcessProjectPopup('${data.customer}','${data.carType}')">${data.moldProcessCount1}<c:if test="${data.moldProcessCount >= 100}"><br></c:if></a><c:if test="${data.moldDelayCount > 0}">/<a href="javascript:moldDelayProjectPopup('${data.customer}','${data.carType}')"><span style="color:#ff0000">${data.moldDelayCount}</span></a></c:if></td>
						<td class="center"><a href="javascript:issueProjectPopup('${data.customer}','${data.carType}')">${data.issueTotal}</a></td>
						<td class="center"><a href="javascript:quiltyProblemPopup('${data.customer}','${data.carType}')">${data.qualityTotal}</a></td>
					</tr>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>	
