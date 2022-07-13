<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<script src='/plm/extcore/js/cost/treeTable.js'></script>
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

.icon-arrow-right {
    transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
}
.icon-arrow-left {
    transform: rotate(135deg);
    -webkit-transform: rotate(135deg);
}
.icon-arrow-up {
    transform: rotate(-135deg);
    -webkit-transform: rotate(-135deg);
}
.icon-arrow-down {
    transform: rotate(45deg);
    -webkit-transform: rotate(45deg);
}
.treegrid-indent {
    width: 0px;
    height: 16px;
    display: inline-block;
    position: relative;
}
.treegrid-expander {
    display: inline-block;
    cursor: pointer;
    border: solid black;
    border-width: 0 3px 3px 0;
    padding: 3px;
    position:relative;
    left:-10px;
}

</style>
<script>
$(document).ready(function(){
	var url = window.location.href;
	if(url.indexOf('viewCostHistoryPopup') != -1){
		//window.location.replace('http://plmapdev.ket.com/plm/ext/cost/costReqHistoryPopup.jsp?pjtOid=${pjtOid}');
		location.href = 'http://${hostName}/plm/ext/cost/costReqHistoryPopup.jsp?pjtOid=${pjtOid}';
	}
	
	window.fnChildHide = function(){
        var treeRows = $('.tree-table').find('tr');

        treeRows.each(function (index, row) {
            var level = $(row).data('level');
            var id = $(row).data('id');
            
            if(level == 1){
                reverseHide($('.tree-table'),$(row));   
            }

        });
    }
	
	fnChildHide();
});
function partPopup(oid,state,taskOid){
 	if(state != 'APPROVED'){
		alert("원가담당자의 산출작업이 아직 진행중입니다.\n원가담당자에게 산출일정을 확인하십시오.");
		return;
	}
	getOpenWindow2("/plm/ext/cost/costRequestList.do?isPopup=true&EDITMODE=VIEW_COMMON&taskOid="+taskOid+"&oid=" + oid, oid, 1280, 720);
}
</script>
<body>
    <div class="contents-wrap" style="margin-top:20px;">
    <div class="popup-title b-space25"><img src="/plm/portal/images/icon_3.png" />원가요청 이력</div>
	<div class="b-space5" style="position: relative; top: -7px; width: 100%;">
		<table id="caseList" summary="" class="table-style-01 infoTable tree-table">
			<thead>
			    <colgroup>
                    <col width="35%" />
                    <col width="15%" />
                    <col width="10%" />
                    <col width="5%" />
                    <col width="5%" />
                    <col width="5%" />
                    <col width="20%" />
                </colgroup>
				<tr>
					<td class="center bgcol fontb">제품명</td>
					<td class="center bgcol fontb">임시품번</td>
					<td class="center bgcol fontb">품번</td>
					<td class="center bgcol fontb">버전</td>
					<td class="center bgcol fontb">DR단계</td>
					<td class="center bgcol fontb">산출안</td>
					<td class="center bgcol fontb">비고(산출조건)</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${caseList}" var="cp" varStatus="stat">
			     <c:if test="${cp.parentId eq '0' }">
                    <c:if test="${stat.index ne 0 }">
                    <tr>
                        <td style="background-color:#FFFFFF; height:5px;" colspan="7"></td>
                    </tr>
                    </c:if>
                </c:if>
                <tr data-id="${cp.id }" data-parent="${cp.parentId }" data-level="${cp.level }" >
                    <td data-column='name' class='left'>${cp.partName }</td>
                    <td class='center'>${cp.partNo }</td>
                    <td class='center'>${cp.realPartNo }</td>
                    <td class='center'><a href="javascript:partPopup('${cp.id }','${cp.reportState }','${cp.taskOid }')">${cp.version }.${cp.subVersion}</a></td>
                    <td class='center'>${cp.drStep }</td>
                    <td class='center'><c:if test="${cp.caseOrder != null}">${cp.caseOrder}案</c:if></td>
                    <td class='left'>${cp.caseNote }</td>
                </tr>
                
            </c:forEach>
            <c:if test="${caseList.size() < 1}" >
                <tr><td colspan=7 style="text-align:center; font-family: NanumBold !important; font-size: 14px; color:red;"><br><b>원가요청 이력이 없거나, 아직 확정된 산출안이 없습니다. <br><br>원가산출담당자에게 산출일정을 확인하시기 바랍니다.</b><br><br></td></tr>
            </c:if>
			</tbody>
		</table>
	</div>
	</div>
</body>
