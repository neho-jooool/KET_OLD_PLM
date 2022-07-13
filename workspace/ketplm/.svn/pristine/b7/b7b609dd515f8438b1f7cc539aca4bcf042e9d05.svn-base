<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
    function viewIssue(v) {
        var url = "/plm/jsp/project/projectIssueView.jsp?oid=" + v;
        openOtherName(url, "", "750", "700", "status=yes,scrollbars=yes,resizable=no");
    }

    function viewProject(oid, type) {
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=' + oid + "&popup=popup&issueType=" + type, '', 1150, 800);
    }
//-->
</script>
<div class="list-table02">
  <table cellpadding="0">
    <colgroup>
      <col width="10%" />
      <col width="23%" />
      <col width="8%" />
      <col width="25%" />
      <col width="8%" />
      <col width="5%" />
      <col width="5%" />
      <col width="8%" />
      <col width="8%" />
    </colgroup>
    <thead>
      <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "00397")%><!-- Project No --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00395")%><!-- Project Name --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00969")%><!-- 구분 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02524")%><!-- 제목 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01158")%><!-- 긴급도 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02693")%><!-- 중요도 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02523")%><!-- 제기자 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02521")%><!-- 제기일 --></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02176")%><!-- 완료여부 --></td>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty myIssueList}">
          <c:forEach items="${myIssueList}" var="myIssue">
            <tr>
              <td><div style="text-align: left; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewProject('${myIssue.taskOid}', '${myIssue.type}')">${myIssue.pjtNo}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: left; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewProject('${myIssue.taskOid}', '${myIssue.type}')">${myIssue.pjtName}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: left; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewIssue('${myIssue.issueOid}')">${myIssue.issueType}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: left; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewIssue('${myIssue.issueOid}')">${myIssue.subject}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: center; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewIssue('${myIssue.issueOid}')">${myIssue.urgency}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: center; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewIssue('${myIssue.issueOid}')">${myIssue.importance}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: center; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewIssue('${myIssue.issueOid}')">${myIssue.ownerName}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: center; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewIssue('${myIssue.issueOid}')">${myIssue.createDate}</a>
                  </nobr>
                </div></td>
              <td><div style="text-align: center; width: 90%; border: 0; padding-left: 3px; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                  <nobr>
                    <a href="javascript:viewIssue('${myIssue.issueOid}')">${myIssue.isDone}</a>
                  </nobr>
                </div></td>
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