<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
    function doView(url) {

        var name = "ViewECO";
        if (url.indexOf('e3ps.ecm.entity.KETProdChangeOrder') > 0) {
            name += 'PROD';
        } else {
            name += 'MOLD';
        }
        var opt = launchCenter(1024, 768);
        opt += ", resizable=yes";
        var windowWin = window.open(url, name, opt);
        windowWin.focus();
    }

    function viewProject(oid) {
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=' + oid + "&popup=popup", '', 1150, 800);
    }
//-->
</script>
<div class="list-table02">
  <table cellpadding="0">
    <colgroup>
      <col width="8%" />
      <col width="8%" />
      <col width="30%" />
      <col width="10%" />
      <col width="20%" />
      <col width="8%" />
      <col width="8%" />
      <col width="8%" />
    </colgroup>
    <thead>
      <tr>
        <td><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01486")%><%--No--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02524")%><%--제목--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00397")%><%--Project No--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01854")%><%--설계변경사유--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02143")%><%--예산--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%></td>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty myECMs}">
          <c:forEach items="${myECMs}" var="myECM">
            <tr>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:doView('${myECM.detailInfoUrl}');">${myECM.ProdMoldCls}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:doView('${myECM.detailInfoUrl}');">${myECM.EcoNo}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:doView('${myECM.detailInfoUrl}');">${myECM.EcoName}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:viewProject('${myECM.proOid}')">${myECM.ProjectNo}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:doView('${myECM.detailInfoUrl}');">${myECM.ChangeReason}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:doView('${myECM.detailInfoUrl}');">${myECM.BudgetYn}</a></nobr></div></td>
              <td><div style="text-align:center; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:doView('${myECM.detailInfoUrl}');">${myECM.CreateDate}</a></nobr></div></td>
              <td><div style="text-align:left; width: 90%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;"><nobr><a href="javascript:doView('${myECM.detailInfoUrl}');">${myECM.StateAppro}</a></nobr></div></td>
            </tr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="8" style="text-align: center; padding-left: 10px;">No data</td>
          </tr>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>
</div>
