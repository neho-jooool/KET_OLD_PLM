<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<div class="list-table02">
  <table cellpadding="0">
    <colgroup>
      <col width="9%" />
      <col width="20%" />
      <col width="46%" />
      <col width="6%" />
      <col width="3%" />
      <col width="8%" />
      <col width="8%" />
    </colgroup>
    <thead>
      <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "01420")%><!-- 문서번호 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01424")%><!-- 문서분류 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01415")%><!-- 문서명 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01760")%><!-- 상태 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01481")%><!-- 버전 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02428")%><!-- 작성일 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00854")%><!-- 고객제출 --></td>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty myDocuments}">
          <c:forEach items="${myDocuments}" var="myDocument">
            <tr>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDocument.oid}')">${myDocument.documentNo}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDocument.oid}')">${myDocument.documentCategory}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDocument.oid}')">${myDocument.documentName}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:viewHistory('${myDocument.oid}')">${myDocument.state}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDocument.oid}')">${myDocument.version}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDocument.oid}')">${myDocument.createDate}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDocument.oid}')">${myDocument.buyerSummit}</a></nobr></div></td>
            </tr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="7" style="text-align: center; padding-left: 10px;">No data</td>
          </tr>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>
</div>
