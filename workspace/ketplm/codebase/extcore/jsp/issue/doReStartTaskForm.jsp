<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
    $(document).ready(function() {
        // 첫번째 입력필드에 포커스
        $('#delegateForm :input:enabled:visible:first').focus();
    });

    function reStart() {
    	var comments = $('#comments').val();
    	if(opener && opener.doReStart){
    		if(confirm("선택한 세부진행항목이 재시작됩니다.\r\n재진행 요청하시겠습니까?")){
    			opener.doReStart(comments);	
    		}else{
    			return;
    		}
    	}else{
    		alert("잘못된 접근입니다.");
    	}
        
        self.close();
    }
    
    function lfn_feedbackBeforePopup() {
        try {
        	opener.showProcessing();
        	opener.disabledAllBtn();
    	} catch(e) {}
    }

    function lfn_feedbackAfterPopup() {
        try {
        	opener.hideProcessing();
        	opener.enabledAllBtn();
    	} catch(e) {}
    }
//-->
</script>
<body class="popup-background" style="margin:30px 15px" onload="javascript:lfn_feedbackBeforePopup();" onunload="javascript:lfn_feedbackAfterPopup();">
  <form id="delegateForm" name="delegateForm">
    <div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" />재진행요청
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 500px">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:reStart();" class="btn_blue" >실행</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:self.close();" class="btn_blue" >닫기</a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 480px" summary="">
          <colgroup>
            <col width="20%" />
            <col width="80%" />
          </colgroup>
          <tbody>
            <tr>
              <td class="title">재요청사유</td>
              <td>
                <textarea id="comments" name="comments" style="width: 93%; height: 60px;" maxlength="1000"></textarea>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </form>
</body>
