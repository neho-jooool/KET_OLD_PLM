<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/issue/issue.js"></script>
<script>

window.completeIssueMaster = function(){
	
	if(confirm("완료하시겠습니까?")){
		var param = new Object();
	    param.oid = $("#oid").val();
	    
	    ajaxCallServer("/plm/ext/issue/completeIssueMaster", param, function(data){
	        location.reload();
	    });
	}
}

window.reload = function(){
	location.reload();
}

window.changeIssuePBO = function(data){
	if(data.length > 0) {
		
		
		var param = new Object();
		
		param.oid = "${imDTO.oid}";
		param.pboOid = data[0][0];
        
        ajaxCallServer("/plm/ext/issue/changeIssuePBO", param, function(data){
        	location.reload();
        }, false);
    }
}

window.deleteIssue = function(){
    
    if(confirm("삭제 하시겠습니까?")){
        
        var param = new Object();
        param.oid = "${imDTO.oid}";

        ajaxCallServer("/plm/ext/issue/deleteIssue", param, function(data){
            self.close();
        });
    }
}

window.meetingTarget = function(){
	
	var meetingTarget = "${meetingTarget}";
	var msg = "회의대상으로 지정하시겠습니까?";
	var meetingTargetYN = "Y";
	if(meetingTarget == 'Y'){
		msg = "회의대상에서 제외하시겠습니까?";
		meetingTargetYN = "";
	}
	
    if(confirm(msg)){
        
        var param = new Object();
        
        param.oid = "${imDTO.oid}";
        param.meetingTarget = meetingTargetYN;
        
        ajaxCallServer("/plm/ext/issue/meetingTarget", param, function(data){
            location.reload();
        }, false);
    }
}

window.doReStart = function(comments){
	
	var reStartIssueTaskList = new Array();
    var checkCount = 0;

    $("input[name='selectIt']").each(function(cnt){
        
        var isChecked = $(this).is(":checked");
        if(isChecked){
            var itOid = $(this).parents(".issueTaskRow:first").find("input[name='itOid']").val();
            var row = {};
            row.itOid = itOid;
            row.comments = comments;
            reStartIssueTaskList.push(row);
            checkCount++;
        }
    });
    
    
    if(checkCount == 0){
        alert('선택된 건이 없습니다.');
        return;
    }
    showProcessing();
    var param = {};
    param.reStartIssueTaskList = JSON.stringify(reStartIssueTaskList);
    
    ajaxCallServer("/plm/ext/issue/reStartIssueTask", param, function(data){
    	hideProcessing();
        alert('재요청되었습니다.');
        
        location.reload();
    }); 
}

window.reStartIssueTaskList = function(comments){
	var issueState = '${imDTO.issueState }';
	
	if(issueState == 'IST004'){
		alert('요청서가 완료되었으므로 재진행 할 수 없습니다.');
		return;
	}
	
	var checkCount = 0;

    $("input[name='selectIt']").each(function(cnt){
        
        var isChecked = $(this).is(":checked");
        if(isChecked){
            checkCount++;
        }
    });
    
    
    if(checkCount == 0){
        alert('선택된 건이 없습니다.');
        return;
    }
    
    var _width = 500;
    var _height = 300;

    var _top = window.outerHeight / 2 + window.screenY - ( _height / 2);
    var _left = window.outerWidth / 2 + window.screenX - ( _width / 2);
    
    var opts = "toolbar=0, location=0, directory=0, status=1, menubar=0, scrollbars=1, resizable=0, left="+_left+", top="+_top;
    var url = "/plm/ext/issue/doReStartTaskForm";
    var oid = "${imDTO.oid}";
    getOpenWindow2(url,oid, _width, _height, opts);
    
    
}
</script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${imDTO.oid}"/>
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />고객/사내 요구사항 상세
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <c:if test="${isManager }">
	            <c:if test="${imDTO.issueState eq 'IST002' and isAllComplete }">
		            <span class="in-block v-middle r-space7">
		                <span class="pro-table">
		                    <span class="pro-cell b-left"></span>
		                    <span class="pro-cell b-center">
		                        <a href="javascript:completeIssueMaster()" class="btn_blue">
		                        <%=messageService.getString("e3ps.message.ket_message", "02171")%><%-- 완료 --%>
		                        </a>
		                    </span>
		                    <span class="pro-cell b-right"></span>
		                </span>
		            </span>
	            </c:if>
	            <c:if test="${meetingAuth}">
                    <span class="in-block v-middle r-space7">
                        <span class="pro-table">
                            <span class="pro-cell b-left"></span>
                            <span class="pro-cell b-center">
                                <a href="javascript:meetingTarget();" class="btn_blue">
                                <c:if test="${meetingTarget eq '' }">
                                        회의대상지정
                                </c:if>
                                <c:if test="${meetingTarget eq 'Y' }">
                                        회의대상해제
                                </c:if>
                                </a>
                            </span>
                            <span class="pro-cell b-right"></span>
                        </span>
                    </span>
                </c:if>
	            <c:if test="${imDTO.issueState ne 'IST004' }">
		            <span class="in-block v-middle r-space7">
		                <span class="pro-table">
		                    <span class="pro-cell b-left"></span>
		                    <span class="pro-cell b-center">
		                        <a href="javascript:location.href='/plm/ext/issue/saveIssuePopup?oid=${imDTO.oid }';" class="btn_blue">
		                        <%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%>
		                        </a>
		                    </span>
		                    <span class="pro-cell b-right"></span>
		                </span>
		            </span>
	            </c:if>
	            <c:if test="${imDTO.issueState eq 'IST001' }">
                    <span class="in-block v-middle r-space7">
                        <span class="pro-table">
                            <span class="pro-cell b-left"></span>
                            <span class="pro-cell b-center">
                                <a href="javascript:deleteIssue();" class="btn_blue">
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
                            요청명<span class="red">*</span>
                        </td>
                        <td colspan="3" class="tdwhiteL">
                            ${imDTO.reqName }
                        </td>
                        <td class="tdblueL">
                            요청 No
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.reqNo }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            요청구분<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.reqCodeName }
                        </td>
                        <td class="tdblueL">
                            상세구분<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.detailCodeName }
                        </td>
                        <td class="tdblueL">
                            등급<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.rankName }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            요청자<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.managerName } / ${imDTO.mDeptName }
                        </td>
                        <td class="tdblueL">
                            완료 요청일자<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.requestDate }
                        </td>
                        <td class="tdblueL">
                            진행상태
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.issueStateName }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            Project No
                        </td>
                        <td class="tdwhiteL">
                            <a href="javascript:openView('${imDTO.pboOid }&key=popup&value=popup')">${imDTO.pboNo }</a>
                            <c:if test="${imDTO.issueState eq 'IST004' }">
                            <span class="in-block v-middle r-space7" style="float:right;">
				                <span class="pro-table">
				                    <span class="pro-cell b-left"></span>
				                    <span class="pro-cell b-center">
				                        <a href="javascript:SearchUtil.selectOneSalesProject('changeIssuePBO')" class="btn_blue">프로젝트 변경</a>
				                    </span>
				                    <span class="pro-cell b-right"></span>
				                </span>
				            </span>
                            </c:if>
                        </td>
                        <td class="tdblueL">
                            프로젝트 명
                        </td>
                        <td colspan="3" class="tdwhiteL">
                            ${imDTO.pboName }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            자동차사<c:if test="${imDTO.reqCode eq 'REQ001'}"><span class="red">*</span></c:if>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.lastCustomerName }
                        </td>
                        <td class="tdblueL">
                            고객사<c:if test="${imDTO.reqCode eq 'REQ001'}"><span class="red">*</span></c:if>
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.subContractorName }
                        </td>
                        <td class="tdblueL">
                            대표차종<c:if test="${imDTO.reqCode eq 'REQ001'}"><span class="red">*</span></c:if>
                        </td>
                        <td class="tdwhiteL">
                          ${imDTO.oemName }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            등록자
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.creatorName }
                        </td>
                        <td class="tdblueL">
                            등록일자
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.createDate }
                        </td>
                        <td class="tdblueL">
                            완료일자
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.completeDate }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            요청사항
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            ${imDTO.mainSubjectHtml }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv"></div>
                            <script>
                            $("#attachFileDiv").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&mode=view");
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <div class="b-space5 float-r" style="text-align: right">
            <c:if test="${isManager }">
                <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:reStartIssueTaskList();" class="btn_blue">재진행 요청</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
        </div>
        
        <div class="sub-title-02 float-l b-space5" style="margin-top:10px;">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>세부 진행 항목
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="30" />
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
                        <td>선택</td>
                        <td>요청사항<span class="red">*</span></td>
                        <td>진행사항</td>
                        <td>담당자<span class="red">*</span></td>
                        <td>참조자</td>
                        <td>완료 요청일자</td>
                        <td>완료일자(상태)</td>
                        <td>파일</td>
                    </tr>
                </thead>
                <tbody id="issueTaskList">
                <c:forEach items="${itList }" var="it">
                    <tr class="issueTaskRow">
                        <td class='td top'><c:if test="${it.issueState eq 'IST004' }"><input type='checkbox' name='selectIt' /></c:if><input type='hidden' name='itOid' value="${it.oid }"></td>
                        <td class='td top'><a href='javascript:openView("${it.oid }");'>${it.subject }</a></td>
                        <td class='td top'><div class='webEditor'>${it.webEditor }</div></td>
                        <td class='td top center'>${it.workerName } / ${it.workerDeptName }</td>
                        <td class='td top center'>${it.memberNames }</td>
                        <td class='td top center'>${it.requestDate }</td>
                        <td class='td top center'>${it.completeDateState }</td>
                        <c:choose>
                            <c:when test="${it.fileCnt ne '-' and it.issueState eq 'IST004' and isManager}">
	                            <td class='td top center' style='padding:0px;'>
		                            <a href='javascript:issue.issueTaskFileDownload("${it.oid }")'>
		                            <img src='/plm/portal/images/icon_file.gif'></a> ${it.fileCnt }
		                        </td>
                            </c:when>
                            <c:when test="${it.fileCnt ne '-' and it.issueState eq 'IST004' and isManager}">
                                <td class='td top center' style='padding:0px;'>
                                    <a href='javascript:issue.issueTaskFileDownload("${it.oid }")'>
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
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr><td class="space20"></td></tr>
        </table>
        <div class="sub-title-02 float-l b-space5">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>요청품목
        </div>
        <div class="b-space5 float-r" style="text-align: right">
        <b>품목수 : ${imDTO.partCount } 건</b> 
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="30" />
                    <col width="200" />
                    <col width="*" />
                </colgroup>
                <thead>
                    <tr>
                        <td>순서</td>
                        <td>품번</td>
                        <td>자재명</td>
                    </tr>
                </thead>
                <tbody id="issuePartList">
                <c:forEach items="${partList }" var="list" varStatus="status" >
                <tr>
                <td class='td top center'>${status.count }</td>
                <td class='td top left'>${list.partNo }</td>
                <td class='td top left'>${list.partName }</td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
