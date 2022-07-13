<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/dms/sgDocument.js"></script>
<body class="popup-background popup-space">
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />이력조회
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
                    <col width="50" />
                    <col width="*" />
                    <col width="100" />
                    <col width="100" />
                    <col width="50" />
                </colgroup>
                <thead>
                    <tr>
                        <td>
                            Rev.
                        </td>
                        <td>문서명</td>
                        <td>작성자</td>
                        <td>배포일</td>
                        <td>파일</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${branchList}" var="branch">
                    <tr>
                         <td class="center">
                        <a href="javascript:sgDocument.openView('${branch.oid }');">${branch.version }</a>
                        </td>
                        <td class="padding">
                            ${branch.docName }
                        </td>
                        <td class="center">
                            ${branch.creatorName }
                        </td>
                        <td class="center">
                            ${branch.modifyDate }
                        </td>
                        <td class="center">
                            <c:if test="${ not empty branch.primaryDTOFile }">
                            	<a href='${branch.primaryDTOFile.downURLStr}' target='download' download>
					                ${branch.primaryDTOFile.iconURLStr}
				                </a>
				            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
</body>
