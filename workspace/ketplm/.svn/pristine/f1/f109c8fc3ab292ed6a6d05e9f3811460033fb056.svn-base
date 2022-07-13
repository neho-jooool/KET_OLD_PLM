<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--

    $(document).ready(function() {
        SuggestUtil.bind('USER', 'delegateUserName', 'delegateUser');
        // 첫번째 입력필드에 포커스
        $('#delegateForm :input:enabled:visible:first').focus();
        // Enter
        $("form[name=delegateForm]").keypress(function(e) {
            if (e.which == 13) {
                delegateWorkItem();
                return false;
            }
        });
    });

    function delegateWorkItem() {
        if (!CommonUtil.checkEsseOk()) {
            return;
        }
        if (confirm(LocaleUtil.getMessage('05129'))) {
            showProcessing();
            $.ajax({
                url : "/plm/ext/wfm/workflow/delegateWorkItem.do",
                type : "POST",
                data : decodeURIComponent($('[name=delegateForm]').serialize()),
                dataType : 'json',
                async : false,
                success : function(msg) {
                    hideProcessing();
                    if (msg == '05161') {
                        alert(LocaleUtil.getMessage('05161'));
                    } else {
                        alert(LocaleUtil.getMessage('01496'));
                        try {
                            opener.opener.location.reload();
                            opener.close();
                            self.close();
                        } catch (e) {
                            opener.close();
                            self.close();
                        }
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
  </c:forEach>
    <div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "05127")%><!-- 수신자변경 -->
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 500px">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:delegateWorkItem();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:self.close();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 480px" summary="">
          <colgroup>
            <col width="20%" />
            <col width="80%" />
          </colgroup>
          <tbody>
            <tr>
              <td class="title"><%=messageService.getString("e3ps.message.ket_message", "05128")%><%--수신대상자--%></td>
              <td>
                <input type="text" name="delegateUserName" class="txt_field" style="width: 70%; ime-mode: active;"><input type="hidden" name="delegateUser" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "05128")%>">
                <a href="javascript:SearchUtil.selectOneUser('delegateUser','delegateUserName');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                <a href="javascript:CommonUtil.deleteValue('delegateUser','delegateUserName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </form>
</body>
