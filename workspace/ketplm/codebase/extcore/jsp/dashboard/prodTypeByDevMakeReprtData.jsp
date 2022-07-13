<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<script>
 $(document).ready(function(){
    if("${typeDivide}"  == "C"){
        $("#carHeight > tr").height("");
    }else{
        $("#carHeight > tr").height("40");
    }
 });
</script>

<div style="width:518px">
<table cellpadding="0" class="table-style-03" style="width:518px">
	<colgroup>
		<col width="86px" />
		<col width="36px" />
        <col width="36px" />
        <col width="36px" />
		<col width="36px" />
		<col width="36px" />
		<col width="36px" />
		<col width="36px" />
		<col width="36px" />
		<col width="36px" />
		<col width="36px" />
		<col width="36px" />
		<col width="18px" />
	</colgroup>
	<thead>
		<tr>
			<td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
			<td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "00734") %></td>
			<td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "09486") %></td>
			<td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "07188") %></td>
			<td colspan="2"><%=messageService.getString("e3ps.message.ket_message", "07189") %></td>
			<td></td>
		</tr>
		<tr>
		    <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "02171") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "02726") %></td>
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
<div style="width: 518px; height: 200px; overflow-y: scroll; overflow-x: hidden; border-bottom: 1px solid #b7d1e2" class="b-space40">
	<table cellpadding="0" class="table-style-03 table-scroll" style="width: 500px">
		<colgroup>
			<col width="86px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
	        <col width="36px" />
		</colgroup>
		<tbody id="carHeight">
		   <c:forEach var="data" items="${prodTypeByDevMakeReprtList}" >
			<tr>
			    <td class="center" title="${data.level2}"><div style="width:85px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">${data.level2}</div></td>
			    <td class="center"><a href="javascript:prodTypeReviewTotalPopup('${data.level2}');">${data.reviewTotalCount}</a></td>
                <td class="center"><a href="javascript:prodTypeReviewCompPopup('${data.level2}');">${data.reviewCompCount}</a></td>
                <td class="center"><a href="javascript:prodTypeReviewProcPopup('${data.level2}');">${data.reviewProcCount}<c:if test="${data.reviewProcCount >= 100}"><br></c:if><c:if test="${data.reviewDelayCount != 0}">/<span style="color:#ff0000">${data.reviewDelayCount}</span></c:if></a></td> <!-- color:#ff0000; -->
				<td class="center"><a href="javascript:prodTypeProdTotalPopup('${data.level2}');">${data.prodTotalCount}</a></td>
				<td class="center"><a href="javascript:prodTypeProdCompPopup('${data.level2}');">${data.prodCompCount}</a></td>
				<td class="center"><a href="javascript:prodTypeProdProcPopup('${data.level2}');">${data.prodProcCount}<c:if test="${data.prodProcCount >= 100}"><br></c:if><c:if test="${data.prodDelayCount != 0}">/<span style="color:#ff0000">${data.prodDelayCount}</span></c:if></a></td>
				<td class="center"><a href="javascript:prodTypeMoldTotalPopup('${data.level2}');">${data.moldTotalCount}</a></td>
				<td class="center"><a href="javascript:prodTypeMoldCompPopup('${data.level2}');">${data.moldCompCount}</a></td>
				<td class="center"><a href="javascript:prodTypeMoldProcPopup('${data.level2}');">${data.moldProcCount}<c:if test="${data.moldProcCount >= 100}"><br></c:if><c:if test="${data.moldDelayCount != 0}">/<span style="color:#ff0000">${data.moldDelayCount}</span></c:if></a></td>
				<td class="center"><a href="javascript:prodTypeIssuePopup('${data.level2}');">${data.issueCount}</a></td>
                <td class="center"><a href="javascript:prodTypeQulityPopup('${data.level2}');">${data.quiltyCount}</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>	
