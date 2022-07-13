<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
$(document).ready(function(){
});
//-->
</script>
<body class="popup-background" style="margin: 30px 15px">
  <form name="delegateForm">
    <div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "05151")%><!-- 작업 위임 이력 -->
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 500px">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:self.close();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <c:forEach items="${list }" var="history">
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 480px" summary="">
          <colgroup>
            <col width="15%" />
            <col width="35%" />
            <col width="15%" />
            <col width="35%" />
          </colgroup>
          <tbody>
            <tr>
              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "05152")%><%--위임일--%></td>
              <td colspan="3">${history.delegateDate }</td>
            </tr>
            <tr>
              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "05153")%><%--변경 전--%></td>
              <td>${history.fromUserName }</td>
              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "05154")%><%--변경 후--%></td>
              <td>${history.toUserName }</td>
            </tr>
            <tr>
              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "05149")%><%--사유--%></td>
              <td colspan="3"><textarea name="reason" style="width: 93%; height: 60px;" readonly="readonly">${history.reason }</textarea></td>
            </tr>
          </tbody>
        </table>
      </div>
      </c:forEach>
    </div>
  </form>
</body>
