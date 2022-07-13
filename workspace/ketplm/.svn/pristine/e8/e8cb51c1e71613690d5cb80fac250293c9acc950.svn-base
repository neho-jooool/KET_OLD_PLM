<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/project/trycondition/tryCondition.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
    $(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "09260") %>');//금형 Try 조건표 [PRESS]
    CalendarUtil.dateInputFormat('tryDate');
});
//-->
</script>
<div class="contents-wrap">
    <table style="width: 100%; <c:if test="${param.isIframe == true}">display:none;</c:if>">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;">
                    <tr>
                        <td width="7"></td>
                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09260") %><!-- 금형 Try 조건표 [PRESS] --></td>
                    </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td class="space5"></td>
        </tr>
    </table>
    <div class="try-title b-space15 float-l">Try No / ${tryPress.tryNo}&nbsp;&nbsp;&nbsp;Sub ver : ${tryPress.subVer}</div>
        <div class="float-r" style="text-align:right; <c:if test="${param.isIframe == true}">display:none;</c:if>">
            <span class="r-space10"><a href="javascript:TryCondition.exportTryCondition('${tryPress.getPersistInfo().getObjectIdentifier().getStringValue()}')"><img src="/plm/portal/images/excel.png" /></a></span>
            <c:if test="${tryPress.lifeCycleState == 'INWORK' || tryPress.lifeCycleState == 'REWORK'}">
                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.deleteTryCondition('${tryPress.getPersistInfo().getObjectIdentifier().getStringValue()}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><!-- 삭제 --></a></span><span class="pro-cell b-right"></span></span></span>
                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.goUpdateTryPress('${tryPress.getPersistInfo().getObjectIdentifier().getStringValue()}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><!-- 수정 --></a></span><span class="pro-cell b-right"></span></span></span>
                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:goPage('${tryPress.getPersistInfo().getObjectIdentifier().getStringValue()}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08003") %><!-- 결재요청 --></a></span><span class="pro-cell b-right"></span></span></span>
            </c:if>
            <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><!-- 닫기 --></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="21%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02425") %><!-- 작성부서 --></td>
                    <td>${tryCondition.createdDeptName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02431") %><!-- 작성자 --></td>
                    <td>${tryPress.creatorFullName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02429") %><!-- 작성일자 --></td>
                    <td><fmt:formatDate value="${tryPress.createTimestamp}" pattern="yyyy-MM-dd"/> </td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09361") %><!-- 승인부서 --></td>
                    <td>${tryCondition.approvedDeptName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02000") %><!-- 승인자 --></td>
                    <td>${tryCondition.approver}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01999") %><!-- 승인일자 --></td>
                    <td>${tryCondition.approvedDate}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01760") %><!-- 상태 --></td>
                    <td colspan="5"><a href="javascript:viewHistory('${tryCondition.oid}')">${tryPress.lifeCycleState.display}</a></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space15 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09337") %><!-- General Specification [일반사항] -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="21%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00144") %><!-- Die No --></td>
                    <td>${tryPress.dieNo}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02599") %><!-- 제품설계 --></td>
                    <td>${tryPress.productDesignRole}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07215") %><!-- 금형제작 --></td>
                    <td>${tryPress.moldMake}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00348") %><!-- Part No --></td>
                    <td>${tryPress.partNo}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01085") %><!-- 금형설계 --></td>
                    <td>${tryPress.moldDesignRole}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07216") %><!-- 금형Try --></td>
                    <td>${tryPress.moldTryRole}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00347") %><!-- Part Name --></td>
                    <td colspan="5">${tryPress.partName}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09266") %><!-- 금형난이도 --></td>
                    <td>${tryPress.moldRank}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09267") %><!-- Try일자 --></td>
                    <td><fmt:formatDate value="${tryPress.tryDate}" pattern="yyyy-MM-dd"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09268") %><!-- Try장소 --></td>
                    <td>${tryPress.tryPlace}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "04035") %><!-- 참석자 --></td>
                    <td colspan="5">${tryPress.attendant}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09269") %><!-- 디버깅사유 --></td>
                    <td colspan="5">${tryPress.debugReason}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03350") %><!-- 특이사항 --></td>
                    <td colspan="5">${tryPress.detail}</td>
                </tr>
            </tbody>
        </table>
    </div>
        <div class="sub-title-02 b-space15 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09270") %><!-- Material [원재료 사항] -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="21%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title">Grade</td>
                    <td>${tryPress.material.grade}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01306") %><!-- 두께 --> x <%=messageService.getString("e3ps.message.ket_message", "02994") %><!-- 폭 --></td>
                    <td>${tryPress.thickness}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09339") %><!-- 도금 --></td>
                    <td>${tryPress.plating}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space15 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09340") %><!-- Press [금형 사항] -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="21%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09278") %><!-- 금형구조 --></td>
                    <td>
                        ${tryPress.moldStruc.name}
                         ${tryPress.moldStrucEtc}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09341") %><!-- 금형 Size --></td>
                    <td colspan="3">
                        <%=messageService.getString("e3ps.message.ket_message", "00574") %><!-- 가로 -->: ${tryPress.moldSizeWidth} mm 
                        <%=messageService.getString("e3ps.message.ket_message", "01908") %><!-- 세로 --> :${tryPress.moldSizeLength} mm  
                        <%=messageService.getString("e3ps.message.ket_message", "09283") %><!-- 높이 --> : ${tryPress.moldSizeHeight} mm</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09342") %><!-- 제품방식 --></td>
                    <td>
                        ${tryPress.productMethod.name}
                        ${tryPress.productMethodEtc}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09343") %><!-- 금형중량 --></td>
                    <td colspan="3">
                        <%=messageService.getString("e3ps.message.ket_message", "02485") %><!-- 전체 -->:${tryPress.moldWeightTop + tryPress.moldWeightLower} 
                        <%=messageService.getString("e3ps.message.ket_message", "09344") %><!-- 상형 -->:${tryPress.moldWeightTop} kg  
                        <%=messageService.getString("e3ps.message.ket_message", "09345") %><!-- 하형 -->:${tryPress.moldWeightLower} kg</td>
                </tr>
                <tr>
                    <td class="title">Die Height</td>
                    <td>${tryPress.dieHeight} mm</td>
                    <td class="title">Pitch</td>
                    <td>${tryPress.pitch}  mm</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09346") %><!-- 타발속도 --></td>
                    <td>${tryPress.punchingSpeed}  SPM</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09347") %><!-- 제품 취출수 --></td>
                    <td>${tryPress.productOutputCol} 열 X ${tryPress.productOutputPitch} 피치</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09349") %><!-- 스크랩 처리 --></td>
                    <td>${tryPress.scrapProcess.name}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09350") %><!-- 안전센서 --></td>
                    <td><ket:codeValueByCode codeType="PRESSSAFETYSENSOR" code="${tryPress.saftySensor}"/></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09351") %><!-- 타발유 --></td>
                    <td>${tryPress.punchingOil.name}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09352") %><!-- 타발수량 --></td>
                    <td colspan="3">${tryPress.punchingCount} EA</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space15 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09353") %><!-- Press Machine [프레스 사양] -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="21%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title">Press</td>
                    <td>${tryPress.press}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09288") %><!-- 톤수 --></td>
                    <td>${tryPress.tone}t</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09354") %><!-- 설비정보 --></td>
                    <td>Stroke : ${tryPress.stroke}   mm   SPM : ${tryPress.spm}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09355") %><!-- Bolster 정보 --></td>
                    <td>
                        <%=messageService.getString("e3ps.message.ket_message", "00574") %><!-- 가로 --> : ${tryPress.bolsterWidth} mm &nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "01908") %><!-- 세로 --> : ${tryPress.bolsterHeight} mm</td>
                    <td class="title">피더기</td>
                    <td colspan="3"><ket:codeValueByCode codeType="PRESSFIDER" code="${tryPress.feederMachine}${tryPress.feederMachineEtc}"/></td>
                </tr>
            </tbody>
        </table>
    </div>
    
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>제품 중량정보
    </div>
    <div class="b-space15" >
        <table summary="" class="table-style-01">
        <colgroup>
            <col width="10%" />
            <col width="30%" />
            <col width="15%" />
            <col width="15%" />
            <col width="15%" />
            <col width="*" />
        </colgroup>
        <thead>
            <tr>
                <td>품번</td>
                <td>자재명</td>
                <td>총중량</td>
                <td>Scrap</td>
                <td>Scrap(캐리어)</td>
                <td>제품중량</td>
            </tr>
        </thead>
        <tbody id="partList">
        <c:forEach items="${partList }" var="list" varStatus="status" >
        <tr>
        <td>${list.partNo}</td>
        <td>${list.partName}</td>
        <td name='totalWeight'>${list.totalWeight}</td>
        <td name='scrabWeight'>${list.scrabWeight}</td>
        <td name='scrabCarrierWeight'>${list.scrabCarrierWeight}</td>
        <td name='netWeight'>${list.netWeight}</td>
        
        <script>
        $("td[name=totalWeight]").eq(${status.count-1 }).number(true,3);
        $("td[name=totalWeight]").eq(${status.count-1 }).html($("td[name=totalWeight]").eq(${status.count-1 }).text()+" g");
        $("td[name=scrabWeight]").eq(${status.count-1 }).number(true,3);
        $("td[name=scrabWeight]").eq(${status.count-1 }).html($("td[name=scrabWeight]").eq(${status.count-1 }).text()+" g");
        $("td[name=scrabCarrierWeight]").eq(${status.count-1 }).number(true,3);
        $("td[name=scrabCarrierWeight]").eq(${status.count-1 }).html($("td[name=scrabCarrierWeight]").eq(${status.count-1 }).text()+" g");
        $("td[name=netWeight]").eq(${status.count-1 }).number(true,3);
        $("td[name=netWeight]").eq(${status.count-1 }).html($("td[name=netWeight]").eq(${status.count-1 }).text()+" g");
        </script>
        
        
        </tr>
        </c:forEach>
        </tbody>
        </table>
    </div>
    
    <div class="sub-title-02 b-space15 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09357") %><!-- Sample 검사 결과 -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="20%" />
                <col width="80%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09358") %><!-- 검사결과 --></td>
                    <td class="left" style="padding:5px 0"><pre>${tryPress.testResult}</pre></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01632") %><!-- 비고 -->
    </div>
    <div class="b-space15">
        <div class="b-space15">
            <table cellpadding="0" class="table-style-01" summary="">
                <colgroup>
                    <col width="20%" />
                    <col width="80%" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="left" style="padding:5px 0"><pre>${tryPress.description}</pre></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /><%=messageService.getString("e3ps.message.ket_message", "02796") %><!-- 첨부파일 -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="20%" />
                <col width="80%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "02796") %><!-- 첨부파일 --></td>
                    <td class="left" style="padding:10px">
                        <c:forEach var="content" items="${secondaryFiles}">
                            <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
                            <a href='${content.downURLStr}' target='_blank'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)<br />
                        </c:forEach>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>