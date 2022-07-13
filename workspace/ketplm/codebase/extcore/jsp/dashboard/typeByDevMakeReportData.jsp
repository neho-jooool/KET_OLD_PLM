<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<script>
 $(document).ready(function(){
	if("${typeDivide}"  == "C"){
		$("#typeHeight > tr").height("");
	}else{
		$("#typeHeight > tr").height("40");
	}
 });
</script>

 <table cellpadding="0" class="table-style-02" summary="">
                    <colgroup>
                        <col width="13%" />
                        <col width="9%" />
                        <col width="10%" />
                        <col width="10%" />
                        <col width="9%" />
                        <col width="10%" />
                        <col width="10%" />
                        <col width="9%" />
                        <col width="10%" />
                        <col width="10%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
                            <td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "00734") %></td>
                            <td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "09486") %></td>
                            <td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "07188") %></td>
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
                        </tr>
                    </thead>
                    <tbody id="typeHeight">
                        <c:forEach var="list" items="${typeByDevMakeReportList}">
                            <tr>
                                <td class="title02">${list.devType}</td>
                                <td class="center"><a href="javascript:typeByTotalProject('review','${list.devType}')">${list.reviewTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByCompleteProject('review','${list.devType}')">${list.reviewComTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByProcessProject('review','${list.devType}')">${list.reviewProTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByTotalProject('product','${list.devType}')">${list.productTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByCompleteProject('product','${list.devType}')">${list.productComTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByProcessProject('product','${list.devType}')">${list.productProTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByTotalProject('mold','${list.devType}')">${list.moldTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByCompleteProject('mold','${list.devType}')">${list.moldComTotalCount}</a></td>
                                <td class="center"><a href="javascript:typeByProcessProject('mold','${list.devType}')">${list.moldProTotalCount}</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
   </table> 


