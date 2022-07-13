<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/invest/invest.js"></script>
<script>
$(document).ready(function(){

    invest.customerSetting();
    
});
window.completeInvestMaster = function(){
	if($("#investTaskList tr").length == 0 || $("#investTaskList2 tr").length == 0){
        alert("투자비 회수 진행현황 또는 회수 증빙 자료가 입력되지 않았습니다.");
        return;
    }
	if(confirm("완료하시겠습니까?")){
		var param = new Object();
	    param.oid = $("#oid").val();
	    
	    ajaxCallServer("/plm/ext/invest/completeInvestMaster", param, function(data){
	        location.reload();
	    });
	}
}

window.approvalRequest = function(oid){
	
	if(document.getElementById("iFileTableOld2") == null){
		alert("회수증빙 파일이 첨부되지 않았습니다.");
		return;
	}
	
	if(!${isAllComplete}){
		alert("증빙 자료 취합이 완료되지 않았습니다.");
		return;
	}
	
	var completeDate = "${imDTO.completeDate}";
/* 	if(completeDate == ''){
		alert("회수 완료일이 입력되지 않았습니다.");
	} */
	if(!${isApproveOk}){
		alert("투자비 회수총액 (회수+미회수)은 고객투자비 보다 적을 수 없습니다.\r\n미회수 금액이 있는 경우 투자비회수 진행현황에 추가 입력 바랍니다.");
	}else{
		goPage(oid);	
	}
}

window.reload = function(){
	location.reload();
}

window.changInvestPBO = function(data){
	if(data.length > 0) {
		
		
		var param = new Object();
		
		param.oid = "${imDTO.oid}";
		param.pboOid = data[0][0];
        
        ajaxCallServer("/plm/ext/invest/changInvestPBO", param, function(data){
        	location.reload();
        }, false);
    }
}

window.deleteInvest = function(){
    
    if(confirm("삭제 하시겠습니까?")){
        
        var param = new Object();
        param.oid = "${imDTO.oid}";

        ajaxCallServer("/plm/ext/invest/deleteInvest", param, function(data){
            self.close();
        });
    }
}

window.openInvestHistory = function(oid){
	var name = oid.replace(/-/g,"");
	getOpenWindow2('/plm/ext/invest/viewInvestDateHistoryPopup.do?oid='+oid,name,800,600);
}


</script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${imDTO.oid}"/>
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />고객투자비 회수관리 상세
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <c:if test="${isManager or isRegMember}">
	            <c:if test="${imDTO.investState eq 'IST002' and isManager }">
		            <span class="in-block v-middle r-space7">
		                <span class="pro-table">
		                    <span class="pro-cell b-left"></span>
		                    <span class="pro-cell b-center">
		                        <a href="javascript:approvalRequest('${imDTO.oid}')" class="btn_blue">
		                            결재요청
		                        </a>
		                    </span>
		                    <span class="pro-cell b-right"></span>
		                </span>
		            </span>
	            </c:if>
	            <c:if test="${imDTO.investState ne 'IST004' and imDTO.investState ne 'IST003'}">
		            <span class="in-block v-middle r-space7">
		                <span class="pro-table">
		                    <span class="pro-cell b-left"></span>
		                    <span class="pro-cell b-center">
		                        <a href="javascript:location.href='/plm/ext/invest/saveInvestPopup?oid=${imDTO.oid }';" class="btn_blue">
		                        <%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%>
		                        </a>
		                    </span>
		                    <span class="pro-cell b-right"></span>
		                </span>
		            </span>
	            </c:if>
	            <c:if test="${imDTO.investState eq 'IST000' and isRegMember }">
                    <span class="in-block v-middle r-space7">
                        <span class="pro-table">
                            <span class="pro-cell b-left"></span>
                            <span class="pro-cell b-center">
                                <a href="javascript:deleteInvest();" class="btn_blue">
                                <%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%>
                                </a>
                            </span>
                            <span class="pro-cell b-right"></span>
                        </span>
                    </span>
                </c:if>
            </c:if>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="#top" onclick="javascript:viewHistory('${imDTO.oid }')" class="btn_blue">
                        <%=messageService.getString("e3ps.message.ket_message", "00785") %><%--결재이력--%>
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                 </span>
            </span>
            
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
        <div class="sub-title-02 float-l b-space5">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>기본정보
        </div>
        <div class="b-space5" >
            <table summary="" class="info">
                <colgroup>
                    <col width="150" />
                    <col width="*" />
                    <col width="150" />
                    <col width="*" />
                    <col width="150" />
                    <col width="*" />
                </colgroup>
                <tbody>
                     <tr>
                        <td class="tdblueL">
                            투자품의번호<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">${imDTO.reqNo }</td>
                        <td class="tdblueL">
                            투자품의명<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">${imDTO.reqName }</td>
                        <td class="tdblueL">
                            유형<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">${imDTO.investTypeCodeName }</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            투자비 (금형/설비)
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.investExpense_1}</fmt:formatNumber>
                        </td>
                        <td class="tdblueL">
                            투자비 (QDM/기타)
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.investExpense_2}</fmt:formatNumber>
                        </td>
                        <td class="tdblueL">
                            투자비 합계
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.investExpense }</fmt:formatNumber>
                        </td>
                        <%-- <td class="tdblueL">
                            접점고객
                        </td>
                        <td class="tdwhiteL" id="subContractorNames">
                            ${imDTO.subContractorName }
                        </td> --%>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            회수비 (금형/설비)
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.collectExpense_1}</fmt:formatNumber>
                        </td>
                        <td class="tdblueL">
                            회수비 (QDM/기타)
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.collectExpense_2}</fmt:formatNumber>
                        </td>
                        <td class="tdblueL">
                            회수비 합계
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.collectExpense}</fmt:formatNumber>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">회수 예정일자<span class="red">*</span></td>
                        <td class="tdwhiteL">${imDTO.requestDate } 
                        <c:if test="${imDTO.dateChangeCount > 0 }">
                        <a href="javascript:openInvestHistory('${imDTO.oid}');">${imDTO.dateChangeComment}</a>
                        </c:if>
                        </td>
                        <td class="tdblueL">회수 완료일
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.completeDate }
                        </td>
                        <td class="tdblueL">진행상태</td>
                        <td class="tdwhiteL">${imDTO.investStateName }</td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            영업 담당자<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.managerName }
                        </td>
                        <td class="tdblueL">
                            최초 등록자
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.creatorName }
                        </td>
                        <td class="tdblueL">
                            등록일
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.createDate }
                        </td>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            투자품의 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv1"></div>
                            <script>
                            $("#attachFileDiv1").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&tid=1&mode=view");
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            회수증빙 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv2"></div>
                            <script>
                            $("#attachFileDiv2").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&tid=2&mode=view");
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            일반 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv3"></div>
                            <script>
                            $("#attachFileDiv3").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&tid=3&mode=view");
                            </script>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">관련 프로젝트</td>
                        <td class="tdwhiteL" colspan="5">
                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr><td class="space5"></td></tr>
                           </table>
                           
                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                <table id="productProjectTable" width="100%" cellpadding="0" cellspacing="0">
                                    <tbody id="productProjectTableBody">
                                        <colgroup>
                                            <col width="20%" />
                                            <col width="40%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                        </colgroup>
                                    <tr>
                                        <td class="tdgrayM">ProjectNo</td>
                                        <td class="tdgrayM">Proejct명</td>
                                        <td class="tdgrayM">접점고객</td>
                                        <td class="tdgrayM">영업</td>
                                        <td class="tdgrayM">영업부서</td>
                                        <td class="tdgrayM">프로젝트완료일</td>
                                    </tr>
                                           
                                    <c:forEach items="${imDTO.productProjectList}" var="projectList" varStatus="i">
                                    <tr>
                                        <td class="tdwhiteM"><a href="javascript:openProject('${projectList.pjtNo}');">${projectList.pjtNo}</a></td>
                                        <td class="tdwhiteM">${projectList.pjtName}</td>
                                        <td class="tdwhiteM">${projectList.pjt_customer}</td>
                                        <td class="tdwhiteM">${projectList.salesName}</td>
                                        <td class="tdwhiteM">${projectList.salesDept}</td>
                                        <td class="tdwhiteM">${projectList.execEndDate}</td>
                                        <input name='pjt_customer' id='pjt_customer' type='hidden' value='${projectList.pjt_customer}'>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr><td class="space5"></td></tr>
                            </table>
                         </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            비고사항
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            ${imDTO.bigoHtml }
                        </td>
                    </tr>                    
                </tbody>
            </table>
        </div>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr><td class="space20"></td></tr>
        </table>
        
        <div class="sub-title-02 float-l b-space5" style="margin-top:10px;">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>투자비 회수 진행현황 (영업)
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="150" />
                    <col width="100" />
                    <col width="100" />
                    <col width="*" />
                    <col width="100" />
                    <col width="100" />
                </colgroup>
                <thead>
                    <tr>
                        <td>회수비 구분<span class="red">*</span></td>
                        <td>회수금액<span class="red">*</span></td>
                        <td>회수일자<span class="red">*</span></td>
                        <td>진행상세</td>
                        <td>등록자</td>
                        <td>등록부서</td>
                    </tr>
                </thead>
                <tbody id="investTaskList2">
                <c:forEach items="${itList2 }" var="it">
                    <tr>
                        <td class='tdwhiteL'>${it.collectCodeName }</td>
                        <td class='tdwhiteR'><fmt:formatNumber>${it.collectExpense }</fmt:formatNumber></td>
                        <td class='td top center'>${it.collectDate }</td>
                        <td class='tdwhiteL'>${it.progressSubject }</td>
                        <td class='td top center'>${it.workerName }</td>
                        <td class='td top center'>${it.workerDeptName }</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr><td class="space20"></td></tr>
        </table>
        
        <div class="sub-title-02 float-l b-space5" style="margin-top:10px;">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>회수 증빙 자료 (관련부서)
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="200" />
                    <col width="*" />
                    <col width="150" />
                    <col width="150" />
                    <col width="100" />
                    <col width="100" />
                    <col width="80" />
                </colgroup>
                <thead>
                    <tr>
                        <td>요청사항<span class="red">*</span></td>
                        <td>진행사항</td>
                        <td>담당자<span class="red">*</span></td>
                        <td>참조자</td>
                        <td>완료 요청일자</td>
                        <td>완료일자(상태)</td>
                        <td>파일</td>
                    </tr>
                </thead>
                <tbody id="investTaskList">
                <c:forEach items="${itList }" var="it">
                    <tr>
                        <td class='td top'><a href='javascript:openView("${it.oid }");'>${it.subject }</a></td>
                        <td class='td top'><div class='webEditor'>${it.webEditor }</div></td>
                        <td class='td top center'>${it.workerName } / ${it.workerDeptName }</td>
                        <td class='td top center'>${it.memberNames }</td>
                        <td class='td top center'>${it.requestDate }</td>
                        <td class='td top center'>${it.completeDateState }</td>
                        <c:choose>
                            <c:when test="${it.fileCnt ne '-' and isManager}">
	                            <td class='td top center' style='padding:0px;'>
		                            <a href='javascript:invest.investTaskFileDownload("${it.oid }")'>
		                            <img src='/plm/portal/images/icon_file.gif'></a> ${it.fileCnt }
		                        </td>
                            </c:when>
                            <c:otherwise>
                                <td class='td top center'>${it.fileCnt }</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
