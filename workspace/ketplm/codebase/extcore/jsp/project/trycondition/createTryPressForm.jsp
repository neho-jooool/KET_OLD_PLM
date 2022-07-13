<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/project/trycondition/tryCondition.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
    $(document).attr('title',LocaleUtil.getMessage('09261'));//'금형 Try 조건표 [PRESS] 등록'
    CalendarUtil.dateInputFormat('tryDate',null,{maxDate:new Date()});
    CommonUtil.multiSelect('saftySensor',120);
    CommonUtil.multiSelect('feederMachine',120);
    
    /* var arr = new Array();
    arr.push("${partOid}");
    arr.push("${moldProject.moldInfo.partNo}");
    arr.push("${moldProject.moldInfo.partName}");
    arr.push("temp");
    arr.push("temp");
    arr.push("temp");
    arr.push("temp");
    arr.push("temp");
    arr.push("${partOid}");
    
    TryCondition.addPart(arr,true); */
    
});
//-->


</script>
<form name="TryConditionForm" enctype="multipart/form-data">
<input type="hidden" name="projectOid" value="${moldProject.getPersistInfo().getObjectIdentifier().getStringValue()}">
<input type="hidden" name="projectOutputOid" value="${param.projectOutputOid}">
<input type="hidden" name="parentTaskOid" value="${param.parentTaskOid}">
<input type="hidden" name="tryNo" value="${tryNo}">
<input type="hidden" name="subVer" value="${maxSubVer}">
<div class="contents-wrap">
    <table style="width: 100%;">
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
	<div class="try-title b-space15 float-l">Try No / ${tryNo}&nbsp;&nbsp;&nbsp;Sub ver:${maxSubVer}</div>
	<div class="float-r" style="text-align:right">
    	<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.goCopyTryConditionPopup()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09263") %><!-- 복사하기 --></a></span><span class="pro-cell b-right"></span></span></span>
    	<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.createTryPress()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><!-- 저장 --></a></span><span class="pro-cell b-right"></span></span></span>
    	<span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><!-- 취소 --></a></span><span class="pro-cell b-right"></span></span></span>
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
					<td>${moldProject.moldInfo.dieNo}<input type="hidden" name="dieNo" value="${moldProject.moldInfo.dieNo}"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "02599") %><!-- 제품설계 --></td>
					<td>${role['Team_MOLD14']}<input type="hidden" name="productDesignRole" value="${role['Team_MOLD14']}"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "07215") %><!-- 금형제작 --></td>
					<td>${moldProject.outSourcing}<input type="hidden" name="moldMake" value="${moldProject.outSourcing}"></td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "00348") %><!-- Part No --></td>
					<td>${moldProject.moldInfo.partNo}<input type="hidden" name="partNo" value="${moldProject.moldInfo.partNo}"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "01085") %><!-- 금형설계 --></td>
					<td>${role['Team_MOLD01']}<input type="hidden" name="moldDesignRole" value="${role['Team_MOLD01']}"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "07216") %><!-- 금형Try --></td>
					<td>${role['Team_MOLD04']}<input type="hidden" name="moldTryRole" value="${role['Team_MOLD04']}"></td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "00347") %><!-- Part Name --></td>
					<td colspan="5">${moldProject.moldInfo.partName}<input type="hidden" name="partName" value="${moldProject.moldInfo.partName}"></td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09266") %><!-- 금형난이도 --></td>
					<td>${moldProject.rank.name}<input type="hidden" name="moldRank" value="${moldProject.rank.name}"></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09267") %><!-- Try일자 --></td>
					<td><input type="text" name="tryDate" style="width:70%"  /></td>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09268") %><!-- Try장소 --></td>
					<td><input type="text" name="tryPlace" style="width:95%" /></td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "04035") %><!-- 참석자 --></td>
					<td colspan="5"><input type="text" name="attendant" style="width:98%" /></td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "09269") %><!-- 디버깅사유 --></td>
					<td colspan="5">
                        <ket:codeValueByCode codeType="HISTORYCHANGETYPE" code="${parentTask.reason}"/>
                        <input type="hidden" name="debugReason" value="<ket:codeValueByCode codeType="HISTORYCHANGETYPE.value" code="${parentTask.reason}"/>"/>
                    </td>
				</tr>
				<tr>
					<td class="title"><%=messageService.getString("e3ps.message.ket_message", "03350") %><!-- 특이사항 --></td>
					<td colspan="5">${parentTask.special}<input type="hidden" name="detail" value="${parentTask.special}"/></td>
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
                    <td class="title">Grade<span class="red-star">*</span></td>
                    <td>
                        <input type="text" name="tempmaterial" style="width:80%" esse="true" esseLabel="Grade" value="${tryPress.material.grade}">
                        <input type="hidden" name="material" value="${tryPress.material.getPersistInfo().getObjectIdentifier().getStringValue()}">
                        <a href="javascript:TryCondition.selMaterial('Press')"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                    </td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01306") %><!-- 두께 --> x <%=messageService.getString("e3ps.message.ket_message", "02994") %><!-- 폭 --><span class="red-star">*</span></td>
                    <td><input type="text" name="thickness" style="width:95%" esse="true" esseLabel="두께 x 폭" value="${tryPress.thickness}"></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09339") %><!-- 도금 --><span class="red-star">*</span></td>
                    <td><input type="text" name="plating" style="width:95%" esse="true" esseLabel="도금" value="${tryPress.plating}"></td>
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
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09278") %><!-- 금형구조 --><span class="red-star">*</span></td>
                    <td>
                        <ket:select useOidValue="true" name="moldStruc" codeType="PRESSTYPESTRUCTURE" esse="true" esseLabel="금형구조" onChange="TryCondition.setEnableEtcField('moldStruc','moldStrucEtc')" value="${tryPress.moldStruc.getPersistInfo().getObjectIdentifier().getStringValue()}"/>
                        &nbsp;<input type="text" name="moldStrucEtc" disabled value="${tryPress.moldStrucEtc }" class="width30"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09341") %><!-- 금형 Size --><span class="red-star">*</span></td>
                    <td colspan="3">
                    <%=messageService.getString("e3ps.message.ket_message", "00574") %><!-- 가로 --> : <input type="text" name="moldSizeWidth" class="width10" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "00574") %>" value="${tryPress.moldSizeWidth }"/>&nbsp; 
                    <%=messageService.getString("e3ps.message.ket_message", "01908") %><!-- 세로 --> : <input type="text" name="moldSizeLength" class="width10"  esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "01908") %>" value="${tryPress.moldSizeLength }"/>&nbsp; 
                    <%=messageService.getString("e3ps.message.ket_message", "09283") %><!-- 높이 --> <input type="text" name="moldSizeHeight" class="width10"  esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09283") %>" value="${tryPress.moldSizeHeight }"/> mm</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09342") %><!-- 제품방식 --><span class="red-star">*</span></td>
                    <td>
                        <ket:select useOidValue="true" name="productMethod" codeType="PRESSPRODUCTMETHOD" esse="true" esseLabel="제품방식" onChange="TryCondition.setEnableEtcField('productMethod','productMethodEtc')" value="${tryPress.productMethod.getPersistInfo().getObjectIdentifier().getStringValue()}"/>
                        &nbsp;&nbsp;<input type="text" name="productMethodEtc" disabled value="${tryPress.productMethodEtc}" class="width30"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09343") %><!-- 금형중량 --><span class="red-star">*</span></td>
                    <td colspan="3">
                        <%=messageService.getString("e3ps.message.ket_message", "02485") %><!-- 전체 --> : ${tryPress.moldWeightTop + tryPress.moldWeightLower}&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09344") %><!-- 상형 --> : <input type="text" name="moldWeightTop" class="width10" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09344") %>" value="${tryPress.moldWeightTop}"/> 
                        <%=messageService.getString("e3ps.message.ket_message", "09345") %><!-- 하형 --> : <input type="text" name="moldWeightLower" class="width10" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09344") %>" value="${tryPress.moldWeightLower}" /> kg</td>
                </tr>
                <tr>
                    <td class="title">Die Height<span class="red-star">*</span></td>
                    <td><input type="text" name="dieHeight" esse="true" esseLabel="Die Height" value="${tryPress.dieHeight}"/> mm</td>
                    <td class="title">Pitch<span class="red-star">*</span></td>
                    <td><input type="text" name="pitch" esse="true" esseLabel="Pitch" value="${tryPress.pitch}"/> mm</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09346") %><!-- 타발속도 --><span class="red-star">*</span></td>
                    <td><input type="text" name="punchingSpeed" esse="true" esseLabel="SPM" value="${tryPress.punchingSpeed}"/> SPM</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09347") %><!-- 제품 취출수 --><span class="red-star">*</span></td>
                    <td><input type="text" name="productOutputCol"  class="width20" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "06126") %>" value="${tryPress.productOutputCol}"/><%=messageService.getString("e3ps.message.ket_message", "06126") %><!-- 열 --> X <input type="text" name="productOutputPitch"  class="width20" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09348") %>" value="${tryPress.productOutputPitch}"/> <%=messageService.getString("e3ps.message.ket_message", "09348") %><!-- 피치 --></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09349") %><!-- 스크랩 처리 --><span class="red-star">*</span></td>
                    <td>
                        <ket:select useOidValue="true" name="scrapProcess" codeType="PRESSSCRAPTYPE" esse="true" esseLabel="스크랩 처리" value="${tryPress.scrapProcess.getPersistInfo().getObjectIdentifier().getStringValue()}"/>
                    </td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09350") %><!-- 안전센서 --><span class="red-star">*</span></td>
                    <td>
                        <ket:select id="saftySensor" useCodeValue="true" name="saftySensor" multiple="multiple" codeType="PRESSSAFETYSENSOR" esse="true" esseLabel="안전센서" value="${tryPress.saftySensor}"/>
                    </td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09351") %><!-- 타발유 --><span class="red-star">*</span></td>
                    <td >
                        <ket:select useOidValue="true" name="punchingOil" codeType="PRESSBLANKINGOIL" esse="true" esseLabel="타발유" value="${tryPress.punchingOil.getPersistInfo().getObjectIdentifier().getStringValue()}"/>
                    </td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09352") %><!-- 타발수량 --><span class="red-star">*</span></td>
                    <td colspan="3">
                        <input type="text" name="punchingCount" style="width:100px" value="${tryPress.punchingCount}" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09352") %>"/> EA
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "09353") %><!-- Press Machine [프레스 사양] -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="13%" />
                <col width="20%" />
                <col width="10%" />
                <col width="20%" />
                <col width="12%" />
                <col width="25%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title">Press<span class="red-star">*</span></td>
                    <td><input type="text" name="press"  esse="true" esseLabel="Press" value="${tryPress.press}"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09288") %><!-- 톤수 --><span class="red-star">*</span></td>
                    <td><input type="text" name="tone"  esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09288") %>" value="${tryPress.tone}"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09354") %><!-- 설비정보 --><span class="red-star">*</span></td>
                    <td>Stroke : <input type="text" name="stroke" class="width15" esse="true" esseLabel="Stroke" value="${tryPress.stroke}"/>mm&nbsp;&nbsp;SPM : <input type="text" name="spm" class="width15" esse="true" esseLabel="SPM" value="${tryPress.spm}"/></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09355") %><!-- Bolster 정보 --><span class="red-star">*</span></td>
                    <td>
                        <%=messageService.getString("e3ps.message.ket_message", "00574") %><!-- 가로 --> : <input type="text" name="bolsterWidth" class="width15"  esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "00574") %>" value="${tryPress.bolsterWidth}"/>&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "01908") %><!-- 세로 --> : <input type="text" name="bolsterHeight" class="width15"  esse="true" esseLabel="세로" value="${tryPress.bolsterHeight}"/> mm</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09356") %><!-- 피더기 --><span class="red-star">*</span></td>
                    <td colspan="3">
                        <ket:select id="feederMachine" useCodeValue="true" name="feederMachine" codeType="PRESSFIDER" multiple="multiple" esse="true" esseLabel="피더기" onChange="TryCondition.setEnableEtcField('feederMachine','feederMachineEtc')" value="${tryPress.feederMachine}"/>                            
                        &nbsp;&nbsp;<input type="text" name="feederMachineEtc" style="margin:5px 0" disabled/>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
        
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>제품 중량정보
        <div class="float-r" style="text-align:right">
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.addPartList()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><!-- 추가  --></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.delPartList()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><!-- 삭제 --></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
    </div>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr><td class="space10"></td></tr>
    </table>
    <div class="b-space15" >
        <table summary="" class="table-style-01">
        <colgroup>
            <col width="3%" />
            <col width="10%" />
            <col width="30%" />
            <col width="15%" />
            <col width="15%" />
            <col width="15%" />
            <col width="*" />
        </colgroup>
        <thead>
            <tr>
                <td><input type='checkbox' name='selectPartAll' id="selectPartAll"/></td>
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
        <tr class='partRow'>
        <td class='td center'><input type='checkbox' name='selectPartDel' /><input type='hidden' name='partOid' value='${list.partOid}'/></td>
        <td class='td top' name='partNo'>${list.partNo}</td>
        <td class='td top'>${list.partName}</td>
        <td class='td top'><input type='text' name='totalWeight' id='totalWeight' class='txt_fieldRO' style='width: 80%' readonly value='${list.totalWeight}'> g</td>
        <td class='td top'><input type='text' name='scrabWeight' id='scrabWeight' style='width:80%' value='${list.scrabWeight}'> g</td>
        <td class='td top'><input type='text' name='scrabCarrierWeight' id='scrabCarrierWeight' style='width:80%' value='${list.scrabCarrierWeight}'> g</td></td>
        <td class='td top'><input type='text' name='netWeight' id='netWeight' style='width:80%' value='${list.netWeight}'> g</td></td>
        
        </tr>
        <script>
        $("input[name=scrabWeight]").eq(${status.count-1 }).keyup(function(){
            
            TryCondition.totalWeightSet(${status.count-1 });
        });
        $("input[name=scrabCarrierWeight]").eq(${status.count-1 }).keyup(function(){
            
            TryCondition.totalWeightSet(${status.count-1 });
        });
        $("input[name=netWeight]").eq(${status.count-1 }).keyup(function(){
            
            TryCondition.totalWeightSet(${status.count-1 });
        });
        </script>
        </c:forEach>
        <script>
        
        
        $("[id$='totalWeight']").number(true,3);
        $("[id$='scrabWeight']").number(true,3);
        $("[id$='scrabCarrierWeight']").number(true,3);
        $("[id$='netWeight']").number(true,3);
        </script>
        </tbody>
        
        </table>
    </div>
    
    <div class="sub-title-02 b-space10 clear">
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
                    <td class="center"><textarea name="testResult" style="height:40px">${tryPress.testResult}</textarea></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01632") %><!-- 비고 -->
    </div>
    <div class="b-space15">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="100%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="center"><textarea name="description" style="height:40px">${tryPress.description}</textarea></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><span class="r-space20"><%=messageService.getString("e3ps.message.ket_message", "02796") %><!-- 첨부파일 --></span></span>
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
                        <div id="div_scroll3" style="overflow-x: auto; overflow-y: auto;">
                            <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                                            <col width="30">
                                            <col width="*">
                                            <tr>
                                                <td style="border:none;border-right:1px solid #b7d1e2;" align="center">
                                                    <a href="#" onclick="TryCondition.insertFileLine();return false;"><img src="/plm/portal/images/b-plus.png"></a></td>
                                                <td width="*" style="border:none" align="center"><%=messageService.getString("e3ps.message.ket_message", "02961") %><!-- 파일명 --></td>
                                            </tr>
                                        </table>
                                        <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                            <col width="30">
                                            <col width="*">
                                            <tbody id="fileTable">
                                                <c:forEach var="content" items="${secondaryFiles}">
                                                    <tr>
                                                        <td style="border:none;border-right:1px solid #b7d1e2;border-top:1px solid #b7d1c2"><a href="#" onclick="javascript:$(this).parent().parent().remove();"><img src="/plm/portal/images/b-minus.png"></a>
                                                            <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
                                                        </td>
                                                        <td width="*" style="border:none;border-top:1px solid #b7d1e2">
                                                            <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
                                                            <a href='${content.downURLStr}' target='_blank'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
</form>