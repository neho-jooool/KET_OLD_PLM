<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<div class="list-table02">
  <table cellpadding="0">
    <colgroup>
      <col width="10%" />
      <col width="25%" />
      <col width="4%" />
      <col width="8%" />
      <col width="10%" />
      <col width="8%" />
      <col width="15%" />
      <col width="10%" />
      <col width="10%" />
    </colgroup>
    <thead>
      <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "01569")%><!-- 부품번호 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01586")%><!-- 부품명 --></td>
        <td>Rev</td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00771")%><!-- 결재상태 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "03114")%><!-- 프로젝트번호 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01595")%><!-- 부품유형 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "06112")%><!-- 부품분류 --></td>
        <td>ECO NO</td>
        <td>Die NO</td>
       <%--  <td><%=messageService.getString("e3ps.message.ket_message", "02586")%><!-- 제품도면 --> NO</td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01335")%><!-- 등록일 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01951")%><!-- 수정일 --></td> --%>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty myParts}">
          <c:forEach items="${myParts}" var="myPart">
            <tr>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myPart.partOid}')">${myPart.partNumber}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myPart.partOid}')">${myPart.partName}</a></nobr></div></td>
              <td style="text-align:center; padding-left: 0px;"><a href="javascript:openView('${myPart.partOid}')">${myPart.spPartRevision}</a></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:viewHistory('${myPart.partOid}')">${myPart.partState}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openProject('${myPart.projectNo}')">${myPart.projectNo}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myPart.partOid}')">${myPart.spPartTypeName}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myPart.partOid}')">${myPart.partClazNameKr}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openViewEco('${myPart.ecoNo}')">${myPart.ecoNo}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr>${myPart.dieNo}</nobr></div></td>
            </tr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="9" style="text-align: center; padding-left: 10px;">No data</td>
          </tr>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>
</div>
