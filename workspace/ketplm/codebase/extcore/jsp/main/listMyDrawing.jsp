<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<div class="list-table02">
  <table cellpadding="0" style="table-layout: fixed;">
    <colgroup>
      <col width="15%" />
      <col width="4%" />
      <col width="4%" />
      <col width="35%" />
      <col width="8%" />
      <col width="8%" />
      <col width="10%" />
      <col width="8%" />
      <col width="8%" />
    </colgroup>
    <thead>
      <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "01275")%><%--도면번호--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00137")%><%--D/W--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01270")%><%--도면명--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00102")%><%--CAD종류--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02431")%><%--작성자--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00173")%><%--Dummy--%></td>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty myDrawings}">
          <c:forEach items="${myDrawings}" var="myDrawing">
            <tr>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDrawing.Oid}')">${myDrawing.DrawingNo}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="${myDrawing.fileDoownloadUrl}" target="_blank"><img alt="" src="${myDrawing.FileIcon}"></a></nobr></div></td>
              <td style="text-align:center; padding-left: 0px;"><a href="javascript:openView('${myDrawing.Oid}')">${myDrawing.Ver}</a></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDrawing.Oid}')">${myDrawing.DrawingName}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDrawing.Oid}')">${myDrawing.CADType}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDrawing.Oid}')">${myDrawing.Creator}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDrawing.Oid}')">${myDrawing.CreateDate}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:viewHistory('${myDrawing.Oid}')">${myDrawing.Status}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:openView('${myDrawing.Oid}')">${myDrawing.Dummy}</a></nobr></div></td>
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
