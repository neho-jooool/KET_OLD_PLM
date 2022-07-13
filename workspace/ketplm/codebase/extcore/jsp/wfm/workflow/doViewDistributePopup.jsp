<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
    function doDistribute(oid) {

        var url = "/plm/jsp/common/loading.jsp?url=/plm/extcore/jsp/wfm/workflow/requestDistributePopup.jsp&key=pboOids&value=" + oid;
        getOpenWindow2(url, 'requestDistribute', 740, 550);
    }
</script>
</head>
<body class="popup-background body-space">
  <div class="contents-wrap">
    <div class="sub-title b-space20">
      <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "05125")%><!-- 배포처 조회 -->
    </div>
    <div class="b-space20 float-r" style="text-align: right">
      <c:if test="${availableDistribute }">
        <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
              href="javascript:doDistribute('${oid }');" class="btn_blue"
            ><%=messageService.getString("e3ps.message.ket_message", "05110")%><%--추가배포--%></a></span><span class="pro-cell b-right"></span></span></span>
      </c:if>
      <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a href="javascript:self.close();"
            class="btn_blue"
          ><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <div class="sub-title-02 b-space15 t-space30">
      <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01929")%><!-- 수신자 -->
      :
      <%=messageService.getString("e3ps.message.ket_message", "05124", request.getAttribute("recipient_cnt"))%><!--  명 -->
    </div>
    <div class="b-space30 clearfix">
      <div class="float-l" style="width: 48%; margin-right: 4%">
        <table cellpadding="0" class="table-style-01" summary="">
          <colgroup>
            <col width="30%" />
            <col width="40%" />
            <col width="30%" />
          </colgroup>
          <thead>
            <tr>
              <td><%=messageService.getString("e3ps.message.ket_message", "06147")%><!-- 이름 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "01538")%><!-- 부서 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "02715")%><!-- 직위 --></td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${recipientArrL }" var="recipient">
              <tr>
                <td class="center">${recipient.name }</td>
                <td class="center">${recipient.deptName }</td>
                <td class="center">${recipient.duty }</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="float-l" style="width: 48%">
        <table cellpadding="0" class="table-style-01" summary="">
          <colgroup>
            <col width="30%" />
            <col width="40%" />
            <col width="30%" />
          </colgroup>
          <thead>
            <tr>
              <td><%=messageService.getString("e3ps.message.ket_message", "06147")%><!-- 이름 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "01538")%><!-- 부서 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "02715")%><!-- 직위 --></td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${recipientArrR }" var="recipient">
              <tr>
                <td class="center">${recipient.name }</td>
                <td class="center">${recipient.deptName }</td>
                <td class="center">${recipient.duty }</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="sub-title-02 b-space15">
      <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "02768")%><!-- 참조자 -->
      :
      <%=messageService.getString("e3ps.message.ket_message", "05124", request.getAttribute("reference_cnt"))%><!--  명 -->
    </div>
    <div class="b-space30 clearfix">
      <div class="float-l" style="width: 48%; margin-right: 4%">
        <table cellpadding="0" class="table-style-01" summary="">
          <colgroup>
            <col width="30%" />
            <col width="40%" />
            <col width="30%" />
          </colgroup>
          <thead>
            <tr>
              <td><%=messageService.getString("e3ps.message.ket_message", "06147")%><!-- 이름 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "01538")%><!-- 부서 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "02715")%><!-- 직위 --></td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${referenceArrL }" var="reference">
              <tr>
                <td class="center">${reference.name }</td>
                <td class="center">${reference.deptName }</td>
                <td class="center">${reference.duty }</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="float-l" style="width: 48%">
        <table cellpadding="0" class="table-style-01" summary="">
          <colgroup>
            <col width="30%" />
            <col width="40%" />
            <col width="30%" />
          </colgroup>
          <thead>
            <tr>
              <td><%=messageService.getString("e3ps.message.ket_message", "06147")%><!-- 이름 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "01538")%><!-- 부서 --></td>
              <td><%=messageService.getString("e3ps.message.ket_message", "02715")%><!-- 직위 --></td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${referenceArrR }" var="reference">
              <tr>
                <td class="center">${reference.name }</td>
                <td class="center">${reference.deptName }</td>
                <td class="center">${reference.duty }</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>