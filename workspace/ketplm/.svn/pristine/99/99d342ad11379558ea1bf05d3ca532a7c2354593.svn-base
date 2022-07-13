<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<style>
html{
    height:100%;
}
body{
    padding-left: 25px !important;
    padding-right: 25px !important;
    padding-top: 20px !important;
}
.infoTable {
    table-layout:fixed;
}
.infoTable td{
    padding:0;
    overflow:hidden;
    text-align:center;
}
.pointer{
    cursor:pointer;
}

</style>
<script>

window.openBomEditor = function(){
    getOpenWindow2("/plm/ext/cost/costBomEditor?isPopup=true&taskOid=${taskOid}&EDITMODE=TREEEDIT&EDITTYPE=NEWPART&oid=1&version=0","COSTNEWPARTADD", "1280", "720");
}

function partPopup(oid){
	getOpenWindow2("/plm/ext/cost/costPartCalculatePopup.do?&taskOid=${taskOid}&oid=" + oid, oid, 1280, 720);
}
</script>
<body>
    <div class="contents-wrap" style="margin-top:20px;">
    <div class="popup-title b-space25"><img src="/plm/portal/images/icon_3.png" />원가산출대상
        <c:if test="${ProductCanAdd eq 'OK'}">
	    <div class="b-space5 float-r" style="text-align: right;">
	        <span class="in-block v-middle r-space7">
	            <span class="pro-table">
	                <span class="pro-cell b-left"></span>
	                <span class="pro-cell b-center"> <a href="javascript:openBomEditor();" class="btn_blue">신규제품추가</a></span>
	                <span class="pro-cell b-right"></span>
	            </span>
	        </span>
	    </div>
	    </c:if>
    </div>
	<div class="b-space5" style="position: relative; top: -7px; width: 100%;">
		<table id="caseList" summary="" class="table-style-01 infoTable">
			<thead>
				<tr>
					<td class="center bgcol fontb">임시품번</td>
					<td class="center bgcol fontb">제품명</td>
					<td class="center bgcol fontb">버전</td>
					<td class="center bgcol fontb">Case순서</td>
					<td class="center bgcol fontb">비고(산출조건)</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${caseList}" var="cp" varStatus="stat">
                <tr>
                    <td class='center'>${cp.partNo }</td>
                    <td class='center'>${cp.partName }</td>
                    <td class='center'><a href="javascript:partPopup('${cp.id }')">${cp.version }.${cp.subVersion}</a></td>
                    <td class='center'><c:if test="${cp.caseOrder != null}">${cp.caseOrder}案</c:if></td>
                    <td class='center'>${cp.caseNote }</td>
                </tr>
            </c:forEach>
			</tbody>
		</table>
	</div>
	</div>
</body>
