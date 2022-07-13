<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<div>
<h2><%=messageService.getString("e3ps.message.ket_message", "07305") %></h1>
<br>
<form id="searchForm">
조회 기준일 : <input type="text" id="searchDate" name="searchDate" value="${searchDate}" class="txt_field" style="width: 80px;" maxlength="10" />
 <a href="#" onclick="javascript:showCal(searchDate);"><img src="/plm/portal/images/icon_6.png"></a>
 <a href="JavaScript:clearDate('searchDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
<input type="button" id="search" value="검색" />
</form>
<br><br>
<table border="1">
    <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01085") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "07306") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01104") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01012") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01339") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01049") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01099") %></td>
    </tr>
 
    
    <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "01655") %></td>
        <c:forEach var="data" items="${mainProgressData}">
                <c:if test="${data.making eq '사내'}">
                <td><a href="">${data.mainSchePjtCount}</a></td>
                </c:if>
        </c:forEach>
    </tr>
</table>
<br><br>
<table border="1">
    <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "07215") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01097") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01012") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01339") %></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01049") %></td>
    </tr>
    <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "02184") %></td>
        <c:forEach var="data1" items="${mainProgressData}">
                <c:if test="${data1.making eq '외주'}">
                <td><a href="">${data1.mainSchePjtCount}</a></td>
                </c:if>
        </c:forEach>
    </tr>
</table>
</div>

<script type="text/javascript">

$(document).ready(function(){
		
	$("#search").click(function(){
		if($("#searchDate").val() != ""){ $("#searchForm").attr("action","/plm/ext/dashboard/mainScheduleProgressPjtCount").submit();}
	});
	
	
});

</script>