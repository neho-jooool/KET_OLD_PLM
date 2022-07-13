<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/project/trycondition/tryCondition.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
    $(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "09259") %>');//금형 Try 조건표 [MOLD]
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
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09259") %><!-- 금형 Try 조건표 [MOLD] --></td>
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
    <div class="try-title b-space15 float-l">Try No / ${tryMold.tryNo}&nbsp;&nbsp;&nbsp;Sub ver : ${tryMold.subVer}</div>
        <div class="float-r" style="text-align:right; <c:if test="${param.isIframe == true}">display:none;</c:if>">
            <span class="r-space10"><a href="javascript:TryCondition.exportTryCondition('${tryMold.getPersistInfo().getObjectIdentifier().getStringValue()}')"><img src="/plm/portal/images/excel.png" /></a></span>
            <c:if test="${tryMold.lifeCycleState == 'INWORK' || tryMold.lifeCycleState == 'REWORK'}">
            <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.deleteTryCondition('${tryMold.getPersistInfo().getObjectIdentifier().getStringValue()}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><!-- 삭제 --></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.goUpdateTryMold('${tryMold.getPersistInfo().getObjectIdentifier().getStringValue()}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><!-- 수정 --></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:goPage('${tryMold.getPersistInfo().getObjectIdentifier().getStringValue()}')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "08003") %><!-- 결재요청 --></a></span><span class="pro-cell b-right"></span></span></span>
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
                    <td>${tryMold.creatorFullName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02429") %><!-- 작성일자 --></td>
                    <td><fmt:formatDate value="${tryMold.createTimestamp}" pattern="yyyy-MM-dd"/> </td>
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
                    <td colspan="5"><a href="javascript:viewHistory('${tryCondition.oid}')">${tryMold.lifeCycleState.display}</a></td>
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
                    <td>${tryMold.dieNo}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02599") %><!-- 제품설계 --></td>
                    <td>${tryMold.productDesignRole}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07215") %><!-- 금형제작 --></td>
                    <td>${tryMold.moldMake}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00348") %><!-- Part No --></td>
                    <td>${tryMold.partNo}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01085") %><!-- 금형설계 --></td>
                    <td>${tryMold.moldDesignRole}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07216") %><!-- 금형Try --></td>
                    <td>${tryMold.moldTryRole}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00347") %><!-- Part Name --></td>
                    <td colspan="5">${tryMold.partName}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09266") %><!-- 금형난이도 --></td>
                    <td>${tryMold.moldRank}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09267") %><!-- Try일자 --></td>
                    <td><fmt:formatDate value="${tryMold.tryDate}" pattern="yyyy-MM-dd"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09268") %><!-- Try장소 --></td>
                    <td>${tryMold.tryPlace}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "04035") %><!-- 참석자 --></td>
                    <td colspan="5">${tryMold.attendant}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09269") %><!-- 디버깅사유 --></td>
                    <td colspan="5">${tryMold.debugReason}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03350") %><!-- 특이사항 --></td>
                    <td colspan="5">${tryMold.detail}</td>
                </tr>
            </tbody>
        </table>
    </div>
	<div class="sub-title-02 b-space15 clear">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09270") %><!-- Material [원재료 사항] -->
	</div>
	<div class="b-space30">
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
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09271") %><!-- Maker[제작사] --></td>
					<td>${tryMold.material.materialMaker.name}</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09272") %><!-- 종류/Grade --><span class="red-star"></td>
					<td>${tryMold.grade}</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09273") %><!-- Color[색상] --></td>
					<td>${tryMold.color}</td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09274") %><!-- 건조온도 --></td>
					<td>${tryMold.dryTemp} ℃</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09275") %><!-- 건조시간 --></td>
					<td>${tryMold.dryTime} Hr</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09276") %><!-- 수분율 --></td>
					<td>${tryMold.moistureRate} %</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="sub-title-02 b-space15 clear">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09277") %><!-- Mold Item [금형 사항] -->
	</div>
	<div class="b-space30">
		<table cellpadding="0" class="table-style-01" summary="">
			<colgroup>
				<col width="13%" />
                <col width="20%" />
                <col width="15%" />
                <col width="18%" />
                <col width="13%" />
                <col width="21%" />
			</colgroup>
			<tbody>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09278") %><!-- 금형구조 --></td>
					<td>${tryMold.moldStruc.getName()}&nbsp;&nbsp;&nbsp;${tryMold.moldStrucEtc}</td>
					<td class="title">MoldBase Size</td>
					<td colspan="3">
                        <%=messageService.getString("e3ps.message.ket_message", "00574") %><!-- 가로 --> : ${tryMold.moldBaseSizeWidth}&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "01908") %><!-- 세로 --> : ${tryMold.moldBaseSizeLength}&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09283") %><!-- 높이 --> : ${tryMold.moldBaseSizeHeight} mm</td>
				</tr>
				<tr>
					<td class="title">Weight</td>
					<td>${tryMold.weight} kg</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09279") %><!-- 게이트방식 --></td>
					<td>${tryMold.gateType.getName()}</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09280") %><!-- Cavity 수 --><br>[1Shot]</td>
					<td>${tryMold.cavityCount}</td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09281") %><!-- 취부판 두께 --></td>
					<td ${tryMold.mountThickness.name}</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09282") %><!-- 온도센서 홀 --></td>
					<td>${tryMold.temperatureSensor.name}</td>
					<td class="title">총 Cavity 수</td>
                    <td>${tryMold.cavityBigo}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="sub-title-02 b-space15 clear">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09284") %><!-- Molding Injection Machine [성형기 사양] -->
	</div>
	<div class="b-space30">
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
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09285") %><!-- 기계사양 --></td>
					<td>${tryMold.machineSpec}</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "Screw 직경") %><!-- Screw 직경 --></td>
					<td>φ ${tryMold.screwDiameter}</td>
					<td class="title">Nozzle Type</td>
					<td>${tryMold.nozzleType}</td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09287") %><!-- 유온 --></td>
					<td>${tryMold.oilTemp}  ℃</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09288") %><!-- 톤수 --></td>
					<td>${tryMold.tone} t</td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09289") %><!-- 타이바 간격 --></td>
					<td>${tryMold.tiebarInterval}</td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09290") %><!-- 실린더 온도 --></td>
					<td colspan="5">
                        <c:if test="${not empty tryMold.cylinderTempNH}">
                        NH : ${tryMold.cylinderTempNH}  ℃&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.cylinderTempN1}">
                        H1 : ${tryMold.cylinderTempN1}  ℃&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.cylinderTempN2}">
                        H2 : ${tryMold.cylinderTempN2}  ℃&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.cylinderTempN3}">
                        H3 : ${tryMold.cylinderTempN3}  ℃&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.cylinderTempN4}">
                        H4 : ${tryMold.cylinderTempN4}  ℃
                        </c:if>
                    </td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09291") %><!-- 냉각수 온도 --></td>
					<td colspan="5">
                        <%=messageService.getString("e3ps.message.ket_message", "09292") %><!-- 상 --> :${tryMold.coolWaterTempHigh}  ℃&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09293") %><!-- 하 --> : ${tryMold.coolWaterTempLow}  ℃</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="sub-title-02 b-space15 clear">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09294") %><!-- Molding Injection Setting [성형 조건] -->
	</div>
	<div class="b-space30">
		<table cellpadding="0" class="table-style-01" summary="">
			<colgroup>
				<col width="3%" />
				<col width="11%" />
				<col width="19%" />
				<col width="3%" />
				<col width="11%" />
				<col width="19%" />
				<col width="3%" />
				<col width="11%" />
				<col width="20%" />
			</colgroup>
			<tbody>
				<tr>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09295") %><!-- 사출압력 -->(${tryMold.injectPressUnit.name})</td>
					<td colspan="7" >
                        <c:if test="${not empty tryMold.injectPress1}">
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : ${tryMold.injectPress1}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectPress2}">
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : ${tryMold.injectPress2}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectPress3}">
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : ${tryMold.injectPress3}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectPress4}">
                        <%=messageService.getString("e3ps.message.ket_message", "09299") %><!-- 4단 --> : ${tryMold.injectPress4}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectPress5}">
                        <%=messageService.getString("e3ps.message.ket_message", "09301") %><!-- 5단 --> : ${tryMold.injectPress5}
                        </c:if></td>
				</tr>
				<tr>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09302") %><!-- 사출속도 -->(mm/s)</td>
					<td colspan="7" >
                        <c:if test="${not empty tryMold.injectSpeed1}">
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : ${tryMold.injectSpeed1}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectSpeed2}">
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : ${tryMold.injectSpeed2} &nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectSpeed3}">
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : ${tryMold.injectSpeed3}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectSpeed4}">
                        <%=messageService.getString("e3ps.message.ket_message", "09299") %><!-- 4단 --> : ${tryMold.injectSpeed4}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.injectSpeed5}">
                        <%=messageService.getString("e3ps.message.ket_message", "09301") %><!-- 5단 --> : ${tryMold.injectSpeed5}
                        </c:if>
                    </td>
				</tr>
				<tr>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09303") %><!-- 다단거리전환 -->(mm)</td>
					<td colspan="7" >
                        <c:if test="${not empty tryMold.shortTransition1}">
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : ${tryMold.shortTransition1}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.shortTransition2}">
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : ${tryMold.shortTransition2}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.shortTransition3}">
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : ${tryMold.shortTransition3}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.shortTransition4}">
                        <%=messageService.getString("e3ps.message.ket_message", "09299") %><!-- 4단 --> : ${tryMold.shortTransition4}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.shortTransition5}">
                        <%=messageService.getString("e3ps.message.ket_message", "09301") %><!-- 5단 --> : ${tryMold.shortTransition5}
                        </c:if>
                    </td>
				</tr>
				<tr>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09304") %><!-- 보압 -->(${tryMold.packingPressUnit.name})</td>
					<td colspan="7" >
                        <c:if test="${not empty tryMold.holdPress1}">
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : ${tryMold.holdPress1}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.holdPress2}">
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : ${tryMold.holdPress2} &nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.holdPress3}">
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : ${tryMold.holdPress3}
                        </c:if>
                    </td>
				</tr>
				<tr>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09305") %><!-- 보압속도 -->(mm/s)</td>
					<td colspan="7" >
                        <c:if test="${not empty tryMold.holdPressSpeed1}">
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : ${tryMold.holdPressSpeed1}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.holdPressSpeed2}">
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : ${tryMold.holdPressSpeed2} &nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.holdPressSpeed3}">
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : ${tryMold.holdPressSpeed3}
                        </c:if>
                    </td>
				</tr>
				<tr>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09306") %><!-- 형개속도 -->(mm/s)</td>
					<td colspan="7" >
                        <c:if test="${not empty tryMold.moldOpenSpeed1}">
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : ${tryMold.moldOpenSpeed1}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.moldOpenSpeed2}">
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : ${tryMold.moldOpenSpeed2}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.moldOpenSpeed3}">
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : ${tryMold.moldOpenSpeed3}
                        </c:if>
                    </td>
				</tr>
				<tr>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09307") %><!-- 형개거리 -->(mm)</td>
					<td colspan="7" >
                        <c:if test="${not empty tryMold.moldOpenDist1}">
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : ${tryMold.moldOpenDist1}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.moldOpenDist2}">
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : ${tryMold.moldOpenDist2}&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${not empty tryMold.moldOpenDist3}">
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : ${tryMold.moldOpenDist3}
                        </c:if>
                    </td>
				</tr>
				<tr>
					<td rowspan="6" class="bgcol fontb center">E<br>J<br>E<br>C<br>T</td>
					<td class="bgcol fontb center" >Stroke</td>
					<td>${tryMold.stroke} mm</td>
					<td rowspan="6" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09308") %><!-- 시간 --></td>
					<td class="bgcol fontb center">Cycle Time</td>
					<td>${tryMold.cycleTime} sec</td>
					<td rowspan="4" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09309") %><!-- 중량 --></td>
					<td class="bgcol fontb center">Shot <%=messageService.getString("e3ps.message.ket_message", "09309") %><!-- 중량 --></td>
					<td>${tryMold.shotWeight} g</td>
				</tr>
				<tr>
					<td class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09310") %><!-- 속도 --></td>
					<td>${tryMold.speed} mm/s</td>
					<td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09311") %><!-- 사출시간 --></td>
					<td>${tryMold.pressTime} sec</td>
					<td class="bgcol fontb center">Scrap</td>
					<td>${tryMold.sprue} g</td>
				</tr>
				<tr>
					<td rowspan="2" class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09312") %><!-- 압력 --></td>
					<td rowspan="2">${tryMold.pressures} KN</td>
					<td rowspan="2" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09313") %><!-- 냉각시간 --></td>
					<td rowspan="2">${tryMold.coolingTime} sec</td>
					<td class="bgcol fontb center">Insert <%=messageService.getString("e3ps.message.ket_message", "09309") %><!-- 중량 --></td>
                    <td>${tryMold.insertWeight} g</td>
				</tr>
				<tr>
				    <td class="bgcol fontb center">(총)Net중량</td>
                    <td>${tryMold.cvWeight} g</td>
				</tr>
				<tr>
					<td class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09315") %><!-- 횟수 --></td>
					<td>${tryMold.pressCount} <%=messageService.getString("e3ps.message.ket_message", "09316") %><!-- 회 --></td>
					<td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09317") %><!-- 보압시간 --></td>
					<td>${tryMold.holdPressTime} sec</td>
					<td rowspan="2" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09318") %><!-- 금형온도 --></td>
					<td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09319") %><!-- 고정측 -->(℃)</td>
					<td>입력값:${tryMold.fixedSideTempInput} (실제값:${tryMold.fixedSideTemp})</td>
				</tr>
				<tr>
					<td class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09322") %><!-- 전진지연시간 --></td>
					<td>${tryMold.delayTime} sec</td>
					<td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09323") %><!-- 형폐시간 --></td>
					<td>${tryMold.moldCloseTime} sec</td>
					<td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09324") %><!-- 이동측 -->(℃)</td>
					<td><%=messageService.getString("e3ps.message.ket_message", "09320") %><!-- 입력값 -->:${tryMold.movingSideTempInput} (<%=messageService.getString("e3ps.message.ket_message", "09321") %><!-- 실제값 -->:${tryMold.movingSideTemp})</td>
				</tr>
				<tr>
					<td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09325") %><!-- 배압 --></td>
					<td>${tryMold.backPress} ${tryMold.backPressUnit.name}</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09326") %><!-- 계량 --></td>
					<td>${tryMold.mensuration} rpm</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09327") %><!-- 저압형체 --></td>
					<td>${tryMold.lowPressShape} ${tryMold.lowPressShapeUnit.name}</td>
				</tr>
				<tr>
					<td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09328") %><!-- 보압전환점 --></td>
					<td>${tryMold.holdPressTurnPoint} mm</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09329") %><!-- 계량시간 --></td>
					<td>${tryMold.mensurationTime} sec</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09330") %><!-- 고압형체구간 --></td>
					<td>${tryMold.highPressShapeSection} mm</td>
				</tr>
				<tr>
					<td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09331") %><!-- 쿠션량 --></td>
					<td>${tryMold.cushionAmount} mm</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09332") %><!-- 흘림방지설정 --></td>
					<td>${tryMold.spillResistentCfg} mm</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09333") %><!-- Shot 수 --></td>
					<td>${tryMold.shotCount}</td>
				</tr>
				<tr>
					<td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09334") %><!-- 계량거리 --></td>
					<td>${tryMold.mensurationDist} mm</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09335") %><!-- 흘림방지속도 --></td>
					<td>${tryMold.spillResistantSpeed} mm/s</td>
					<td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09336") %><!-- 최고충진압 --></td>
					<td>${tryMold.maxPress} Kg/㎠</td>
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
                        <td class="left" style="padding:5px 0"><pre>${tryMold.description}</pre></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /> <%=messageService.getString("e3ps.message.ket_message", "02796") %><!-- 첨부파일 -->
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