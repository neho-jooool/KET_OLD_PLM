<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<div class="list-table02">
  <table cellpadding="0">
    <colgroup>
      <col width="13%" />
      <col width="" />
      <col width="10%" />
      <col width="9%" />
      <%-- <col width="10%" /> --%>
      <col width="10%" />
    </colgroup>
    <thead>
      <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "02242")%><!-- 유형 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00756")%><!-- 결재 대상명 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00771")%><!-- 결재상태 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01133")%><!-- 기안자 --></td>
        <%-- <td><%=messageService.getString("e3ps.message.ket_message", "01335")%><!-- 등록일 --></td> --%>
        <td><%=messageService.getString("e3ps.message.ket_message", "01300")%><!-- 도착일 --></td>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty workItemList}">
          <c:forEach items="${workItemList}" var="workItem">
            <tr>
              <td><div style="text-align: left; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <c:choose>
                      <c:when test="${empty workItem.viewTaskUrl && workItem.multiApproval}">
                        <a href="javascript:openView2('${workItem.pboOid}')">${workItem.taskType}</a>
                      </c:when>
                      <c:when test="${empty workItem.viewTaskUrl && !workItem.multiApproval}">
                        <a href="javascript:openView('${workItem.pboOid}')">${workItem.taskType}</a>
                      </c:when>
                      <c:otherwise>
                        <a href="javascript:openTask('${workItem.viewTaskUrl}')">${workItem.taskType}</a>
                      </c:otherwise>
                    </c:choose>
                  </nobr>
                </div></td>
              <td><div style="text-align: left; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <c:choose>
                      <c:when test="${empty workItem.viewTaskUrl && workItem.multiApproval}">
                        <a href="javascript:openView2('${workItem.pboOid}')">${workItem.title}</a>
                      </c:when>
                      <c:when test="${empty workItem.viewTaskUrl && !workItem.multiApproval}">
                        <a href="javascript:openView('${workItem.pboOid}')">${workItem.title}</a>
                      </c:when>
                      <c:otherwise>
                        <a href="javascript:openTask('${workItem.viewTaskUrl}')">${workItem.title}</a>
                      </c:otherwise>
                    </c:choose>
                  </nobr>
                </div></td>
              <td><div style="text-align: left; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewHistory('${workItem.pboOid}')">${workItem.status}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: center; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <c:choose>
                      <c:when test="${empty workItem.viewTaskUrl && workItem.multiApproval}">
                        <a href="javascript:openView2('${workItem.pboOid}')">${workItem.creator}</a>
                      </c:when>
                      <c:when test="${empty workItem.viewTaskUrl && !workItem.multiApproval}">
                        <a href="javascript:openView('${workItem.pboOid}')">${workItem.creator}</a>
                      </c:when>
                      <c:otherwise>
                        <a href="javascript:openTask('${workItem.viewTaskUrl}')">${workItem.creator}</a>
                      </c:otherwise>
                    </c:choose>
                  </nobr>
                </div></td>
              <%-- <td><div style="text-align:left;width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr>${workItem.createDate}</nobr></div></td> --%>
              <td><div style="text-align: center; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <c:choose>
                      <c:when test="${empty workItem.viewTaskUrl && workItem.multiApproval}">
                        <a href="javascript:openView2('${workItem.pboOid}')">${workItem.arriveDate}</a>
                      </c:when>
                      <c:when test="${empty workItem.viewTaskUrl && !workItem.multiApproval}">
                        <a href="javascript:openView('${workItem.pboOid}')">${workItem.arriveDate}</a>
                      </c:when>
                      <c:otherwise>
                        <a href="javascript:openTask('${workItem.viewTaskUrl}')">${workItem.arriveDate}</a>
                      </c:otherwise>
                    </c:choose>
                  </nobr>
                </div></td>
            </tr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="6" style="text-align: center; padding-left: 10px;">No data</td>
          </tr>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>
</div>
