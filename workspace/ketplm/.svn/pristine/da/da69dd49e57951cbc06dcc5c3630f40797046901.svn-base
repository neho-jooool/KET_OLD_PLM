<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/issue/issue.js"></script>
<body class="popup-background popup-space">
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />변경이력
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="b-space5" >
            <table summary="" class="table-style-01" style="widtd:100%;">
                <colgroup>
                    <col width="40" />
                    <col width="*" />
                    <col width="650" />
                    <col width="60" />
                    <col width="90" />
                </colgroup>
                <thead>
                    <tr>
                        <td>
                            버전
                        </td>
                        <td>요청사항</td>
                        <td>진행사항</td>
                        <td>담당자</td>
                        <td>완료 요청일자</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${branchList}" var="branch">
                    <tr>
                        <td class="center">
                        <a href="javascript:openView('${branch.oid }');">${branch.version }</a>
                        </td>
                        <td class="padding">
                            ${branch.subject }
                        </td>
                        <td>
                            <div class='webEditor'>${branch.webEditor}</div>
                        </td>
                        <td class="center">
                            ${branch.workerName }
                        </td>
                        <td class="center">
                            ${branch.requestDate }
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
