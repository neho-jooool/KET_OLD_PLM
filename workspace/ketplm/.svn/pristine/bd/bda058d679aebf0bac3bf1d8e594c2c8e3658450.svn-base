<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<table cellpadding="0" summary="" class="table-style-01" style="width:1088px">
    <colgroup>
        <col width="82px" />
        <col width="159px" />
        <col width="82px" />
        <col width="78px" />
        <col width="77px" />
        <col width="77px" />
        <col width="78px" />
        <col width="77px" />
        <col width="78px" />
        <col width="77px" />
        <col width="77px" />
        <col width="73px" />
        <col width="18px" />
    </colgroup>
    <thead>
        <tr>
            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02110") %></td>
            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "07214") %></td>
            <td colspan="8"><%=messageService.getString("e3ps.message.ket_message", "07205") %></td>
            <td rowspan="2" style="border-right-color: #e2edf4"><%=messageService.getString("e3ps.message.ket_message", "02296") %></td>
            <td rowspan="2"></td>
        </tr>
        <tr>
            <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07215") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07216") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07209") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07210") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07211") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07212") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "01095") %></td>
        </tr>
    </thead>
</table>    
<div style="width:1088px;height:260px;overflow-y:scroll;overflow-x:hidden">
    <table cellpadding="0" class="table-style-01 table-scroll" sytle="width:1020px">
        <colgroup>
            <col width="82px" />
            <col width="157px" />
	        <col width="82px" />
	        <col width="77px" />
	        <col width="77px" />
	        <col width="76px" />
	        <col width="77px" />
	        <col width="77px" />
	        <col width="77px" />
	        <col width="78px" />
	        <col width="76px" />
            <col width="74px" />
        </colgroup>
        <tbody>
        <c:forEach var="data" items="${moldManufactureData}">
             <tr>
                <td class="bgcol fontb center">${data.itemType}</td>
                <td class="bgcol fontb left" title="${data.outsourcing}"><div style="width:116px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">${data.outsourcing}</div></td>
                <td class="center"><a href="javascript:projectPopUp('${data.itemType}','${data.outsourcing}');">${data.projectCount}/<span style="color:#ff0000">${data.projectDelayCount}</span></a></td>
                <td class="center"><a href="javascript:projectTotalStep('${data.itemType}','${data.outsourcing}')">${data.taskTotalCount}<c:if test="${data.taskTotalDelayCount > 0}">/<span style="color:#ff0000">${data.taskTotalDelayCount}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldStepPopUp('${data.itemType}','${data.outsourcing}','외주금형제작')">${data.plan}<c:if test="${data.planDelay > 0}">/<span style="color:#ff0000">${data.planDelay}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldStepPopUp('${data.itemType}','${data.outsourcing}','금형Try_[T0]')">${data.firstTry}<c:if test="${data.firstTryDelay > 0}">/<span style="color:#ff0000">${data.firstTryDelay}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldStepPopUp('${data.itemType}','${data.outsourcing}','1 차 디버깅')">${data.debuging1}<c:if test="${data.debugingDelay1 > 0}">/<span style="color:#ff0000">${data.debugingDelay1}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldStepPopUp('${data.itemType}','${data.outsourcing}','2 차 디버깅')">${data.debuging2}<c:if test="${data.debugingDelay2 > 0}">/<span style="color:#ff0000">${data.debugingDelay2}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldStepPopUp('${data.itemType}','${data.outsourcing}','3 차 디버깅')">${data.debuging3}<c:if test="${data.debugingDelay3 > 0}">/<span style="color:#ff0000">${data.debugingDelay3}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldStepPopUp1('${data.itemType}','${data.outsourcing}')">${data.debuging4}<c:if test="${data.debugingDelay4 > 0}">/<span style="color:#ff0000">${data.debugingDelay4}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldStepPopUp('${data.itemType}','${data.outsourcing}','금형이관단계')">${data.moldtransfer}<c:if test="${data.moldtransferDelay > 0}">/<span style="color:#ff0000">${data.moldtransferDelay}</span></a></c:if></td>
                <td class="center"><a href="javascript:moldIssuePopUp('${data.itemType}','${data.outsourcing}')">${data.issueCount}</a></td>
             </tr> 
          </c:forEach>  
        </tbody>
    </table>
</div>
<div style="width:1088px">
    <table cellpadding=0 class="table-style-01" summary="" style="width:1088px;border-top-width:1px">  
        <colgroup>
	        <col width="82px" />
	        <col width="159px" />
	        <col width="82px" />
	        <col width="78px" />
	        <col width="77px" />
	        <col width="77px" />
	        <col width="78px" />
	        <col width="77px" />
	        <col width="77px" />
	        <col width="78px" />
	        <col width="77px" />
	        <col width="73px" />
	        <col width="18px" />
        </colgroup>
        <tfoot>
        <tr>
            <td colspan="2">계</td>
            <td><a href="javascript:projectPopUp('','')">${totalData.projectCount}/${totalData.projectDelayCount}</a></td>
            <td><a href="javascript:projectTotalStep('','')">${totalData.taskTotalCount}/${totalData.taskTotalDelayCount}</a></td>
            <td><a href="javascript:moldStepPopUp('','','외주금형제작')"">${totalData.plan}/${totalData.planDelay}</a></td>
            <td><a href="javascript:moldStepPopUp('','','금형Try_[T0]')"">${totalData.firstTry}/${totalData.firstTryDelay}</a></td>
            <td><a href="javascript:moldStepPopUp('','','1 차 디버깅')"">${totalData.debuging1}/${totalData.debugingDelay1}</a></td>
            <td><a href="javascript:moldStepPopUp('','','2 차 디버깅')"">${totalData.debuging2}/${totalData.debugingDelay2}</a></td>
            <td><a href="javascript:moldStepPopUp('','','3 차 디버깅')"">${totalData.debuging3}/${totalData.debugingDelay3}</a></td>
            <td><a href="javascript:moldStepPopUp1('','')">${totalData.debuging4}/${totalData.debugingDelay4}</a></td>
            <td><a href="javascript:moldStepPopUp('','','금형이관단계')"">${totalData.moldtransfer}/${totalData.moldtransferDelay}</a></td>
            <td style="border-right-color: #e2edf4"><a href="javascript:moldIssuePopUp('','')">${totalData.issueCount}</a></td>
            <td></td>
        </tr>
        </tfoot>
    </table>
</div>