<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
    $(document).ready(function() {
        // 첫번째 입력필드에 포커스
        $('#delegateForm :input:enabled:visible:first').focus();
    });

    function doBatchCompleteWorkItem() {
        if (confirm(LocaleUtil.getMessage('05131'))) {//일괄승인 하시겠습니까?
            showProcessing();
            var url = "/plm/ext/wfm/workflow/doBatchCompleteWorkItem.do";
            $.ajax({
                url : url,
                type : "POST",
                data : decodeURIComponent($('[name=delegateForm]').serialize()),
                dataType : 'json',
                async : false,
                success : function(flag) {
                    hideProcessing();
                    if (flag) {
                        alert(LocaleUtil.getMessage('05132'));//정상적으로 승인 되었습니다.
                        opener.parent.Ext.getCmp('workspace').getStore().reload();
                        opener.location.reload();
                        self.close();
                    } else {
                        alert(LocaleUtil.getMessage('05139') + "\n" + LocaleUtil.getMessage('05140'));//처리중 에러가 발생하였습니다.\n관리자에게 문의하여 주십시오.
                    }
                }
            });
        }
    }
//-->
</script>
<body class="popup-background" style="margin:30px 15px">
  <form id="delegateForm" name="delegateForm">
  <c:forEach items="${workItemoids }" var="workItemoid">
    <input type="hidden" name="workItemoids[]" id="workItemoids" value="${workItemoid }">
    <input type="hidden" name="riskCheck" value="false" />
  </c:forEach>
    <div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "05105")%><!-- 일괄승인 -->
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 500px">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:doBatchCompleteWorkItem();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "05105")%><%--일괄승인--%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:self.close();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 480px" summary="">
          <colgroup>
            <col width="20%" />
            <col width="80%" />
          </colgroup>
          <tbody>
            <tr>
              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00783")%><%--결재의견--%></td>
              <td>
                <textarea name="comments" style="width: 93%; height: 60px;" maxlength="1000"></textarea>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </form>
</body>
