<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/issue/issue.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>

<c:if test="${authCheckFlag == false}" >
<script>
$(document).ready(function() {
    alert('<%=messageService.getString("e3ps.message.ket_message", "00990") %>');
    window.close();
});
</script>
</c:if>
<c:if test="${authCheckFlag == true}" >
<script type="text/javascript" src="/plm/extcore/js/sales/project/salesProject.js"></script>
<script type="text/javascript">

<!--
    $(document).ready(function() {
        window.document.body.scroll = "auto";//메일에서 열릴때 스크롤주기위함
        
        $(document).attr('title', "영업수주현황 View");//'금형 Try 조건표 [MOLD] 등록'
        var strContent = document.innoditorDataForm["webEditor"].value;

        var objView = document.getElementById("divView");
        objView.innerHTML = strContent;

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
        var strBackgroundColor = document.innoditorDataForm["hdnBackgroundColor"].value;
        if("" != strBackgroundColor)
        {
            objView.style.backgroundColor = strBackgroundColor;
        }

        var strBackgroundImage = document.innoditorDataForm["hdnBackgroundImage"].value;
        if("" != strBackgroundImage)
        {
            var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

            if("none" == strCopyBackgroundImage)
            {
                objView.style.backgroundImage = strBackgroundImage;
            }
            else
            {
                objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
            }
        }

        var strBackgroundRepeat = document.innoditorDataForm["hdnBackgroundRepeat"].value;
        if("" != strBackgroundRepeat)
        {
            objView.style.backgroundRepeat = strBackgroundRepeat;
        }

    });
//-->
</script>
<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
    //<![CDATA[
    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
    var g_arrSetEditorArea = new Array();
    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정

    //]]>
</script>
<script type="text/javascript"
    src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript"
    src="/plm/portal/innoditor_u/js/customize_ui.js"></script>
<script type="text/javascript">
    //<![CDATA[
    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
    // Skin 재정의
    //g_nSkinNumber = 0;
    // 크기, 높이 재정의
    g_nEditorWidth = 900;
    g_nEditorHeight = 340;
    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<script>
function openTaskWebEditor(idx){
    $('[name="idx"]').val(idx);
    $('[name="stepName"]').val("Step "+idx);
    
    $('[name=salesProjectForm]').attr('target', 'salesTaskViewFormPopup');
    $('[name=salesProjectForm]').attr('action', '/plm/ext/sales/project/salesTaskViewForm.do');
    
    //getOpenWindow2('/plm/ext/sales/project/salesTaskCreateForm.do?planCheck='+planCheck+'&stepName=Step '+idx+'&idx='+idx,'salesTaskCreateFormPopup',1300,500);
    
    window.open("", "salesTaskViewFormPopup", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1300,height=500");
    
    $('[name=salesProjectForm]').submit();
    
}

function openTaskHistory(oid, taskCnt){
    if(taskCnt!=0){
        //getOpenWindow2('/plm/ext/sales/project/salesTaskHistoryForm.do?oid='+oid,'salesTaskHistoryFormPopup'+oid,1300,800);
        
        window.open('/plm/ext/sales/project/salesTaskHistoryForm.do?oid='+oid, 'salesTaskHistoryFormPopup'+oid, "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1300,height=800");
    }
    
}


function afterStateSave(oid,stateCode){
    
    if(stateCode == 'F' && !CommonUtil.checkEsseOk()){ 
        return;
    }
    
    if($('[name="projectResult"]').val() == ''){
        alert('프로젝트 결과를 입력하셔야합니다.');
        return;
    }
    
    if(!confirm("결재를 요청하시겠습니까?")){
        return;
    }
    $.ajax({
        
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/sales/project/updateAfterState.do?oid='+oid+"&stateCode="+stateCode,
        //data: $('[name=partForm]').serialize(),
        success: function (data) {
            
            if(data != 'Fail'){
                
                try{
                    if(data=='Y'){
                         
                        goPage('${sales.vrOid}');
                         
                    }else if(data=='N'){
                        alert("(N)결재요청 중 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
                    }else if(data=='E'){
                        alert("(E)결재요청 중 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
                    }
                }catch(e){alert(e.message); }
                
            }else{
                alert("결재요청 중 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
            }
        },
        fail : function(){
            alert("(F)결재요청 중 오류가 발생했습니다.\n관리자에게 문의하시기 바랍니다.");
        }
        
    });
}

$(document).ready(function(){
    var tab = CommonUtil.tabs('tabs');
    $('.tabContent').hide();
    $('.tabContent :first').show();
    
    $(".tabref").click(function(){
        $(".tabContent").hide();
        var ref = $(this).attr("href");
        $(ref).show();
    });
    
    issue.createPaingGrid(true);
    Grids.OnRenderFinish = function(grid){
    	issue.search();
    }
});

window.openIssueSave = function(){
    getOpenWindow2("/plm/ext/issue/saveIssuePopup?projectOid=${sales.oid}", "IssuePopup", 1280, 720);
}
</script>
<form name="searchForm">
<input type="hidden" name="pboNo" value="${sales.projectNo}" />
</form>
<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${sales.webEditor}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>
<!-- 이노디터 JS Include End -->
<form name="salesProjectForm" method="post" enctype="multipart/form-data">
    <input type="hidden" name="idx" value="">
    <input type="hidden" name="stepName" value="">
    
    <div class="contents-wrap">
        <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
        <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea>
        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
        <input type="hidden" name="hdnBackgroundColor" value="" />
        <input type="hidden" name="hdnBackgroundImage" value="" /> 
        <input type="hidden" name="hdnBackgroundRepeat" value="" />
        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
        <!-- 이노디터에서 작성된 내용을 보낼 Form End -->
        <table style="width: 100%;">
            <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                    <table style="height: 28px;">
                        <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">영업수주현황 상세화면</td>
                        </tr>
                    </table>
                </td>
                <td width="136" align="right"><img
                    src="/plm/portal/images/logo_popup.png"></td>
            </tr>
        </table>
        <div id="tabs" style="margin:0 auto;">
        <ul>
            <li><a class="tabref" href="#PROJECTINFO">기본정보</a></li>
            <li><a class="tabref" href="#ISSUELIST">고객대응관리</a></li>
        </ul>
        <div id="PROJECTINFO" class="tabContent" style="border:0;border-top:2px solid #82BAD1;border-radius:0;padding-top:15px;">
	        <div class="sub-title-02 b-space15 clear">
	            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120")%><!-- 기본정보 -->
	        <div class="b-space20" style="text-align:right">
	        <c:if test="${sales.isLastRev == 'ok' && sales.csYN != 'Y'}" >
	        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:sales.csMettingUpdate('${sales.oid}')" class="btn_blue">CS회의지정</a></span><span class="pro-cell b-right"></span></span></span>
	        </c:if>
	        <c:if test="${sales.isLastRev == 'ok' && sales.csYN == 'Y'}" >
	        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:sales.csMettingCancel('${sales.oid}')" class="btn_blue">CS회의취소</a></span><span class="pro-cell b-right"></span></span></span>
	        </c:if>
	        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:sales.csView('${sales.oid}')" class="btn_blue">CS보고용 View</a></span><span class="pro-cell b-right"></span></span></span>
	        <c:if test="${(sales.basicEditAuth=='ok' || sales.taskEdit=='ok' || sales.seniorAuth == 'ok') && sales.isLastRev == 'ok' && (sales.stateName=='작업중' || sales.stateName=='재작업' || (sales.salesStateName=='성공' && sales.stateName=='승인완료'))}" >
	            
	            <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:sales.update('${sales.oid}','')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%></a></span><span class="pro-cell b-right"></span></span></span>
	            
	            <c:if test="${(sales.stateName=='작업중' || sales.stateName=='재작업') && sales.basicEditAuth=='ok' }" >
	                <c:choose>
	                <c:when test="${sales.salesStateName == '진행'}">
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:afterStateSave('${sales.oid}','S');" class="btn_blue">프로젝트 성공</a></span><span class="pro-cell b-right"></span></span></span>
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:afterStateSave('${sales.oid}','C');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08012")%><%--취소--%></a></span><span class="pro-cell b-right"></span></span></span>
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:afterStateSave('${sales.oid}','H');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08011")%><%--중지--%></a></span><span class="pro-cell b-right"></span></span></span>
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:afterStateSave('${sales.oid}','F');" class="btn_blue">프로젝트 실패</a></span><span class="pro-cell b-right"></span></span></span>
	                </c:when>
	                <c:when test="${sales.salesStateName == '중지'}">
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:afterStateSave('${sales.oid}','C');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08012")%><%--취소--%></a></span><span class="pro-cell b-right"></span></span></span>
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:afterStateSave('${sales.oid}','I');" class="btn_blue">프로젝트 진행</a></span><span class="pro-cell b-right"></span></span></span>
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:afterStateSave('${sales.oid}','F');" class="btn_blue">프로젝트 실패</a></span><span class="pro-cell b-right"></span></span></span>
	                </c:when>
	                </c:choose>
	            </c:if>
	            
	        </c:if>
	        <c:if test="${sales.stateName =='승인완료' && sales.salesStateName == '중지' && sales.isLastRev == 'ok' && sales.csYN != 'Y'}" >
	            <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:sales.update('${sales.oid}','R');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02446")%><%--재시작--%></a></span><span class="pro-cell b-right"></span></span></span>
	        </c:if>
	        </div>
	        </div>
	        <div class="b-space30">
	            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                <tr>
	                    <td class="tab_btm2"></td>
	                </tr>
	            </table>
	            <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed" border="1">
	                <colgroup>
	                    <col width="15%" />
	                    <col width="35%" />
	                    <col width="15%" />
	                    <col width="35%" />
	                </colgroup>
	                <tbody>
	                    <tr>
	                        <td class="tdblueL">프로젝트 명<span class="red">*</span></td>
	                        <td class="tdwhiteL">${sales.projectName}</td>
	                        <td class="tdblueL">ProjectNo<span class="red">*</span></td>
	                        <td class="tdwhiteL">${sales.projectNo}</td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">Rev</td>
	                        <td class="tdwhiteL"><a href="javascript:sales.viewVerHistory('${sales.oid}');">${sales.rev}</a></td>
	                        <td class="tdblueL">중요도<span class="red">*</span></td>
	                        <td class="tdwhiteL">${sales.rankName}</td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">작성자</td>
	                        <td class="tdwhiteL">${sales.pmName}</td>
	                        <td class="tdblueL">작성부서</td>
	                        <td class="tdwhiteL">${sales.pmdept}</td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%><span class="red">*</td>
	                        <td class="tdwhiteL">${sales.customerName}</td>
	                        
	                        <td class="tdblueL">자동차사<span class="red">*</span></td>
	                        <td class="tdwhiteL">${sales.lastBuyerName}</td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">프로젝트 유형<span class="red">*</span></td>
	                        <td class="tdwhiteL">${sales.devTypeName}</td>
	                        <td class="tdblueL">적용부</td>
	                        <td class="tdwhiteL">${sales.applyArea}</td>
	                    </tr>
	
	                    <tr>
	                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01248")%><%--대표차종--%><span class="red">*</td>
	                        <td class="tdwhiteL">${sales.oemName}</td>
	                        <td class="tdblueL">SOP</td>
	                        <td class="tdwhiteL">${sales.sopDate}</td>
	                    </tr>
	                    <tr>
	                       <td class="tdblueL">경쟁사</td>
	                       <td class="tdwhiteL">${sales.competitorName}</td>
	                       <td class="tdblueL">국가<span class="red">*</span></td>
	                       <td class="tdwhiteL">${sales.nationName}</td>
	                    </tr>
	                    <tr>
	                       <td class="tdblueL">프로젝트 상태</td>
	                       <td class="tdwhiteL">${sales.salesStateName}</td>
	                       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771")%><%--결재상태--%></td>
	                       <td class="tdwhiteL"><a href="javascript:viewHistory('${sales.vrOid}')">${sales.stateName}</a></td>
	                    </tr>
	                    
	                    <tr>
	                       <td class="tdblueL">제품군 대분류<span class="red">*</span></td>
	                       <td class="tdwhiteL">${sales.mainCategoryName}</td>
	                       
	                       <td class="tdblueL">WorkShop</td>
	                       <td class="tdwhiteL">${sales.workShopYN}</td>
	                    </tr>
	                    
	                    <tr>
	                       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%><span class="red">*</span></td>
	                       <td class="tdwhiteL" colspan="3">
	                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                               <tr>
	                                   <td class="space5"></td>
	                               </tr>
	                           </table>
	                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
	                               <table id="partClassTable" width="100%" cellpadding="0" cellspacing="0">
	                                   <tbody id="partclassTableBody">
	                                       <colgroup>
	                                       <col width="3%" />
	                                       <col width="19%" />
	                                       <col width="8%" />
	                                       <col width="10%" />
	                                       <col width="10%" />
	                                       <col width="10%" />
	                                       <col width="10%" />
	                                       </colgroup>
	                                       <tr>
	                                           <td class="tdgrayM" rowspan="2">구분</td>
	                                           <td class="tdgrayM" rowspan="2">부품분류</td>
	                                           <td class="tdgrayM" rowspan="2">투자비<br>(백만원)</td>
	                                           <td class="tdgrayM" colspan="2">기획 대수(만대)</td>
	                                           <td class="tdgrayM" colspan="2">예상 매출(백만원)</td>
	                                       </tr>
	                                       <tr>
	                                           <td class="tdgrayM">총 대수</td>
	                                           <td class="tdgrayM">년 대수</td>
	                                           <td class="tdgrayM">총 매출</td>
	                                           <td class="tdgrayM">년 매출</td>
	                                       </tr>
	                                       
	                                       <c:forEach items="${sales.partClzList}" var="clzList" varStatus="i">
	                                       <tr>
	                                           <td class="tdwhiteM"><c:if test="${clzList.mainYN=='Y'}" >Main</c:if></td>
	                                           <td class="tdwhiteM">${clzList.clzName}</td>
	                                           <td class="tdwhiteM"><fmt:formatNumber>${clzList.investCost}</fmt:formatNumber></td>
	                                           <td class="tdwhiteM"><fmt:formatNumber>${clzList.planTotal}</fmt:formatNumber></td>
	                                           <td class="tdwhiteM"><fmt:formatNumber>${clzList.planYear}</fmt:formatNumber></td>
	                                           <td class="tdwhiteM"><fmt:formatNumber>${clzList.expectSalesTotal } </fmt:formatNumber></td>
	                                           <td class="tdwhiteM"><fmt:formatNumber>${clzList.expectSalesYear}</fmt:formatNumber></td>
	                                       </tr>
	                                       </c:forEach>
	
	                                       
	                                  </tbody>
	                               </table>
	                           </div>
	                              <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                                  <tr>
	                                      <td class="space5"></td>
	                                  </tr>
	                              </table>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td class="tdblueL">영업<br>목표</td>
	                        <td colspan="3" class="tdwhiteL">
	                            <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
	                                <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;"></div>
	                            <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
	                        </td>
	                        
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">수주사</td>
	                        <td class="tdwhiteL">${sales.obtainCompanyName}<input type='hidden' name='obtainCompanyName' id='obtainCompanyName' value='${sales.obtainCompanyName}'></td>
	                        <td class="tdblueL">실패유형</td>
	                        <td class="tdwhiteL"">${sales.failtypeName}<input type='hidden' name='failtypeName' id='failtypeName' value='${sales.failtypeName}' esse='true' esseLabel='실패유형'></td>
	                    </tr>
	                    
	                    <tr>
	                        <td class="tdblueL">프로젝트 결과</td>
	                        <td class="tdwhiteL" colspan="3">
	                        ${sales.projectResult}
	                        <input type='hidden' name='projectResult' id='projectResult' value='${sales.projectResult}'>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td class="tdblueL">실패사유</td>
	                        <td class="tdwhiteL" colspan="3">
	                          ${sales.failReason}
	                        <input type='hidden' name='failReason' id='failReason' value='${sales.failReason}' esse='true' esseLabel='실패사유'>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                       <td class="tdblueL">개발프로젝트</td>
	                       <td class="tdwhiteL" colspan="3">
	                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                               <tr>
	                                   <td class="space5"></td>
	                               </tr>
	                           </table>
	                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
	                               <table id="productProjectTable" width="100%" cellpadding="0" cellspacing="0">
	                                   <tbody id="productProjectTableBody">
	                                       <colgroup>
	                                       <col width="30%" />
	                                       <col width="70%" />
	                                       </colgroup>
	                                       <tr>
	                                           <td class="tdgrayM">ProjectNo</td>
	                                           <td class="tdgrayM">ProejctName</td>
	                                       </tr>
	                                       
	                                       <c:forEach items="${sales.productProjectList}" var="projectList" varStatus="i">
	                                           <tr>
	                                               <td class="tdwhiteM"><a href="javascript:openProject('${projectList.pjtNo}');">${projectList.pjtNo}</a></td>
	                                               <td class="tdwhiteM">${projectList.pjtName}</td>
	                                           </tr>
	                                       </c:forEach>
	                                  </tbody>
	                               </table>
	                           </div>
	                              <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                                  <tr>
	                                      <td class="space5"></td>
	                                  </tr>
	                              </table>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                       <td class="tdblueL">프로젝트 참여자<br>(조회권한)</td>
	                       <td class="tdwhiteL" colspan="3">
	                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                               <tr>
	                                   <td class="space5"></td>
	                               </tr>
	                           </table>
	                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
	                               <table id="projectMemberTable" width="100%" cellpadding="0" cellspacing="0">
	                                       <colgroup>
	                                       <col width="30%" />
	                                       <col width="40%" />
	                                       <col width="30%" />
	                                       </colgroup>
	                                   </tr>
	
	                                           <td class="tdgrayM" rowspan="2">이름</td>
	                                           <td class="tdgrayM" rowspan="2">부서</td>
	                                           <td class="tdgrayM" rowspan="2">직위</td>
	                                       </tr>
	                                   <tbody>
	                                       <c:forEach items="${sales.memberList}" var="memberList" varStatus="i">
	                                       <tr>
	                                           <td class="tdwhiteM">${memberList.name}</td>
	                                           <td class="tdwhiteM">${memberList.departmentName}</td>
	                                           <td class="tdwhiteM">${memberList.duty}</td>
	                                       </tr>
	                                       </c:forEach>
	                                   </tbody>
	                               </table>
	                           </div>
	                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                                <tr>
	                                    <td class="space5"></td>
	                                </tr>
	                           </table>
	                        </td>
	                    </tr>
	                    
	                    <!--  
	                    <tr>
	                        <td class="tdblueL">비고</td>
	                        <td class="tdwhiteL" colspan="3">
	                          ${sales.bigo}
	                        </td>
	                    </tr>
	                    -->
	                    
	                    <tr>
	                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
	                        <td colspan="3" class="tdwhiteL">
	                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                                <tr>
	                                    <td class="space5"></td>
	                                </tr>
	                            </table>
	
	                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                                <tr>
	                                    <td class="space5"></td>
	                                </tr>
	                            </table>
	                            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
	                                <table width="100%" cellpadding="0" cellspacing="0">
	                                    <tr class="headerLock3">
	                                        <td>
	                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
	                                                <tr>
	                                                    <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
	                                                </tr>
	                                            </table>
	                                        </td>
	                                    </tr>
	                                    <tr>
	                                        <td>
	                                            <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
			                                        <tbody id="fileTable">
			                                        <c:forEach var="content" items="${secondaryFiles}">
			                                            <tr>
			                                                <td class="tdwhiteL">
			                                                    <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
			                                                    <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
			                                                    <a href='${content.downURLStr}' target='_blank'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
			                                                </td>
			                                            </tr>
			                                        </c:forEach>
			                                        </tbody>
			                                    </table>
			                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
			                                        <tr>
			                                            <td class="space5"></td>
			                                        </tr>
			                                    </table>
	                                        </td>
	                                    </tr>
	                                </table>
	                            </div>
	                        </td>
	                    </tr>
<%-- 	                    <tr>
	                        <td class="tdblueL">관련 Tag</td>
	                        <td colspan="3" class="tdwhiteL">
	                            <span class="REFTAG">
	                            <c:forEach items="${sales.refTagList}" var="refTag" varStatus="stat">
	                                #${refTag.name }<input type="hidden" name="refTag" value="${refTag.id }" /><c:if test="${!stat.last}">,</c:if>
	                            </c:forEach>
	                            </span>
	                        </td>
	                    </tr> --%>
	                </tbody>
	            </table>
	        </div>
	        <div class="sub-title-02 b-space15 clear">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>영업추진현황
	            </div>
	            <div class="b-space30">
	                <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                    <tr>
	                        <td class="tab_btm2"></td>
	                    </tr>
	                </table>
	                <table id="salesTargetTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;" border="1">
	                    <colgroup>
	                        <col width="7%" />
	                        <col width="3%" />
	                        <col width="21%" />
	                        <col width="24%" />
	                        <col width="11%" />
	                        <col width="13%" />
	                        <col width="13%" />
	                        <col width="*" />
	                    </colgroup>
	                    <tbody>
	                    <tr>
	                        <td rowspan="2" class="tdblueM">단계</td>
	                        <td rowspan="2" class="tdblueM">이력</td>
	                        <td rowspan="2" class="tdblueM">Subject</td>
	                        <td rowspan="2" class="tdblueM">영업 추진 계획</td>
	                        <td rowspan="2" class="tdblueM">담당자</td>
	                        <td colspan="2" class="tdblueM">완료일정</td>
	                        <td rowspan="2" class="tdblueM">진행상황<br>(G,Y,R)</td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueM">계획</td>
	                        <td class="tdblueM">실적</td>
	                    </tr>
	                    <c:forEach items="${sales.salesTaskList}" var="taskList" varStatus="status">
	                        <tr>
	                        <td class="tdwhiteM"><a href='#' onclick="javascript:openTaskWebEditor('${status.index+1}'); return false;">Step ${status.index+1}</a></td>
	                        <td class="tdwhiteM"><a href='#' onclick="javascript:openTaskHistory('${taskList.taskOid}','${taskList.historyCnt}'); return false;">${taskList.historyCnt}</a></td>
	                        <td class="tdwhiteM">${taskList.subject}</td>
	                        <td class="tdwhiteM">${taskList.salesPlan}</td>
	                        <td class="tdwhiteM">${taskList.userName}</td>
	                        <td class="tdwhiteM">${taskList.planDate}</td>
	                        <td class="tdwhiteM">${taskList.resultDate}</td>
	                        <td width="50" width="80" class="tdwhiteM" style="background-color:${taskList.planCheck};text-align:center">&nbsp;
	                            <font color="#000000"><b>
	                            <c:if test="${taskList.planCheck=='green'}" >G</c:if>
	                            <c:if test="${taskList.planCheck=='red'}" >R</c:if>
	                            <c:if test="${taskList.planCheck=='yellow'}" >Y</c:if>
	                            <c:if test="${taskList.planCheck=='gray'}" >P</c:if>
	                            </b></font>
	                            <textarea name='propelwebEditor' rows='0' cols='0' style='display: none'>${taskList.propelwebEditor}</textarea>
	                            <textarea name='propelwebEditorText' rows='0' cols='0' style='display: none'>${taskList.propelwebEditorText}</textarea>
	                            <textarea name='problemwebEditor' rows='0' cols='0' style='display: none'>${taskList.problemwebEditor}</textarea>
	                            <textarea name='problemwebEditorText' rows='0' cols='0' style='display: none'>${taskList.problemwebEditorText}</textarea>
	                            <textarea name='planwebEditor' rows='0' cols='0' style='display: none'>${taskList.planwebEditor}</textarea>
	                            <textarea name='planwebEditorText' rows='0' cols='0' style='display: none'>${taskList.planwebEditorText}</textarea>
	                            <input type="hidden" name="planCheck" value="${taskList.planCheck}">
	                        </td>
	                        </tr>
	                    </c:forEach>
	                    </tbody>
	                </table>
	            </div>
	        </div>
	        <div id="ISSUELIST" class="tabContent" style="border:0;border-top:2px solid #82BAD1;border-radius:0;padding-top:15px;top:-2px;">
		        <div class="sub-title-02 b-space15 clear">
		                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>고객대응관리
		            <div class="b-space20" style="text-align:right">
		                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span>
		                <span class="pro-cell b-center">
		                <a href="javascript:openIssueSave();" class="btn_blue">등록</a>
		                </span><span class="pro-cell b-right"></span></span></span>
		            </div>
		            <!-- EJS TreeGrid Start -->
		            <div class="content-main">
		                <div class="container-fluid">
		                    <div class="row-fluid">
		                        <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
		                    </div>
		                </div>
		                <!-- EJS TreeGrid Start -->
		            </div>
		        </div>
	        </div>
        </div>
    </div>
    <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>
</c:if>