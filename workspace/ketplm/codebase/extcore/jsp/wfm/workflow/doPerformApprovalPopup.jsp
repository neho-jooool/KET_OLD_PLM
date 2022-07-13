<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script src="/plm/extcore/js/wfm/workflow/doPerformApprovalPopup.js"></script>
<script type="text/javascript">
<!--
var isDRR = ${isDRR};
$(document).ready(function(e) {
    //resizeTo(1024, 900);
});
//-->
</script>
<style>
.bubble {
position: absolute;
width: 13px;
height: 10px;
padding: 1px;
background: #FFBB00;
-webkit-border-radius: 6px;
-moz-border-radius: 6px;
border-radius: 6px;
right:3px;
top:3px;
}
.bubble.check {
background: #FF0000;
}
.bubble:after {
content: '';
position: absolute;
border-style: solid;
border-width: 5px 2px 0;
border-color: #FFBB00 transparent;
display: block;
width: 0;
z-index: 1;
margin-left: -4px;
bottom: -4px;
left: 41%;
-webkit-transform: rotate(30deg);
-ms-transform: rotate(30deg);
transform: rotate(30deg);
}
.bubble.check:after {
border-color: #FF0000 transparent;
}
</style>
<body class="popup-background popup-space">
  <form>
    <div class="contents-wrap">
      <div class="sub-title b-space5">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "00776")%><%--결재수행--%>
      </div>
      <div class="b-space5 float-r" style="text-align: right">
        <c:if test="${!receiver && !confirmReject && !noWorkflow}">
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.complete('accept', '${workItemDTO.masterOid}', '${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "01987")%><%--승인--%></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <c:if test="${conAccept && !receiver && !confirmReject && !noWorkflow}">
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.complete('conAccept', '${workItemDTO.masterOid}', '${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "02636")%><%--조건부승인--%></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <c:if test="${isDRR && !receiver && !confirmReject && !noWorkflow}">
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.complete('cancel', '${workItemDTO.masterOid}', '${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "00733")%><!-- 검토취소 --></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <c:if test="${!receiver && !confirmReject && !noWorkflow}">
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.complete('reject', '${workItemDTO.masterOid}', '${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "01466")%><%--반려--%></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <c:if test="${receiver && !confirmReject && !noWorkflow}">
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.doDelegateWorkItem('${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "05127")%><%--수신자변경--%></a></span><span class="pro-cell b-right"></span></span></span>
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.complete('confirm', '${workItemDTO.masterOid}', '${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "01931")%><%--수신확인--%></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <c:if test="${!receiver && confirmReject && !noWorkflow}">
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.complete('update', '${workItemDTO.masterOid}', '${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "02451")%><%--재작업--%></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <c:if test="${noWorkflow}">
          <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
                href="javascript:Approval.complete('taskComplete', '${workItemDTO.masterOid}', '${workItemDTO.oid}');" class="btn_blue"
              ><%=messageService.getString("e3ps.message.ket_message", "01978")%><%--수행완료--%></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a href="javascript:self.close();"
              class="btn_blue"
            ><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="sub-title-02 float-l b-space5">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "03353")%><!-- 결재선 -->
        (의견 : ${commentCnt } &nbsp;&nbsp; Risk 점검 요청 : <c:if test="${riskCheckCnt > 0}"><span class="red"></c:if> ${riskCheckCnt }<c:if test="${riskCheckCnt > 0}"></span></c:if>)
      </div>
      <div class="process b-space5" style="width: 100%; height: 125px; table-layout: fixed; overflow-x: hidden; overflow-y: auto;">
        <c:forEach items="${lineArr}" var="histories">
          <div class="clearfix">
            <c:forEach items="${histories}" var="history" varStatus="status">
              <c:if test="${!empty history}">
                <div ${history.activateStyle}>
                  <ul>
                    <li style="position:relative;">
                    <c:if test="${not empty history.comment}">
                      <div class="bubble <c:if test="${history.riskCheck }">check</c:if>" ></div>
                    </c:if>
                    ${history.deptName}
                    <br>${history.ownerName}
                    <br>${history.completeDate}
                    </li>
                    <li ${history.classStyle}>
                    	${history.activityName}
                    </li>
                  </ul>
                </div>
                <c:if test="${!status.last}">
                  <div></div>
                </c:if>
              </c:if>
            </c:forEach>
          </div>
        </c:forEach>
      </div>
      <div class="sub-title-02 b-space5 float-l" style="<c:if test="${isDRR || receiver || confirmReject}">display:none;</c:if>">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "03354")%><!-- 배포처 -->
      </div>
      <div style="<c:if test="${isDRR || receiver || confirmReject}">display:none;</c:if>">
        <span class="r-space7" style="margin-left:30px;">▶</span><%=messageService.getString("e3ps.message.ket_message", "01929")%><!-- 수신자 -->
        : <a href="javascript:Approval.doViewDistribute('${workItemDTO.pboOid}')"><%=messageService.getString("e3ps.message.ket_message", "05124", request.getAttribute("recipient_cnt"))%></a>
        <!--  명 -->
        <span class="r-space7 l-space20">▶</span><%=messageService.getString("e3ps.message.ket_message", "02768")%><!-- 참조자 -->
        : <a href="javascript:Approval.doViewDistribute('${workItemDTO.pboOid}')"><%=messageService.getString("e3ps.message.ket_message", "05124", request.getAttribute("reference_cnt"))%></a>
        <!--  명 -->
      </div>
      <div class="b-space5" style="<c:if test="${receiver || confirmReject}">display:none;</c:if>">
        <table cellpadding="0" summary="" class="table-style-01">
          <colgroup>
            <col width="20%" />
            <col width="80%" />
          </colgroup>
          <tbody>
            <tr>
              <td class="center bgcol fontb">
              	<%=messageService.getString("e3ps.message.ket_message", "00783")%><!-- 결재의견 -->
              	<label>(<input type="checkbox" name="riskCheck" value="true" />Risk 점검 요청)</label>
              </td>
              <td class="center"><textarea id="acomment" name="acomment" style="height: 50px" maxlength="1333" onblur="Limit(this)"></textarea></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="sub-title-02 b-space5 float-l">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "00762")%><!-- 결재대상 -->
      </div>
      <div class="float-r" style="text-align: right;position:relative;top:-2px;">
        <c:if test="${isProjectPBO }">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a
              href="javascript:Approval.HistoryChangeView('${workItemDTO.pboOid}');" class="btn_blue">
              일정변경근거문서<%--일정변경근거문서--%></a></span><span class="pro-cell b-right"></span></span></span>
        </c:if>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a
              href="javascript:viewHistory('${workItemDTO.pboOid}');" class="btn_blue">
              <%=messageService.getString("e3ps.message.ket_message", "00785")%><%--결재이력--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="b-space5 clear">
        <iframe id="ifrPBO" name="ifrPBO" style="width: 100%; height: 600px; border: 0px;" src="${workItemDTO.pboViewUrl }"></iframe>
      </div>
    </div>
  </form>
</body>
