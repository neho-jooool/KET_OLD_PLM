<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/project/trycondition/tryCondition.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
    $(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "09264") %>');//금형 Try 조건표 [MOLD] 수정
    CalendarUtil.dateInputFormat('tryDate',null,{maxDate : new Date()});
    CommonUtil.multiSelect('saftySensor', 100);
    CommonUtil.multiSelect('feederMachine', 100);
    $("input:text[name='cavityCount']").number(true);
    $("input:text[name='shotWeight']").number(true,3);
    $("input:text[name='sprue']").number(true,3);
    $("input:text[name='cvWeight']").number(true,3);
    $("input:text[name='insertWeight']").number(true,3);

    $("input:text[name='sprue'],input:text[name='cvWeight']").on("keyup change",function(event){
        
        var sprue = Number($("input:text[name='sprue']").val()) ;
        var cvWeight =  Number($("input:text[name='cvWeight']").val());

        var result = sprue+cvWeight;
        
        result = parseFloat(result).toFixed(3);
        
        $("input:text[name='shotWeight']").val(result);
    });
    
});
//-->
</script> 
<form name="TryConditionForm" enctype="multipart/form-data">
<input type="hidden" name="oid" value="${tryMold.getPersistInfo().getObjectIdentifier().getStringValue()}">
<input type="hidden" name="projectOid" value="${tryMold.moldProject.getPersistInfo().getObjectIdentifier().getStringValue()}">
<input type="hidden" name="tryNo" value="${tryMold.tryNo }">
<input type="hidden" name="subVer" value="${tryMold.subVer }">
<div class="contents-wrap">
    <table style="width: 100%;">
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
        <tr>            <td class="space5"></td>
        </tr>
    </table>
    <div class="try-title b-space20 float-l">Try No / ${tryMold.tryNo}&nbsp;&nbsp;&nbsp;Sub ver:${tryMold.subVer}</div>
    <div class="float-r" style="text-align:right">
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TryCondition.updateTryMold()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><!-- 저장 --></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:history.go(-1)" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><!-- 취소 --></a></span><span class="pro-cell b-right"></span></span></span>
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
                    <td>${tryMold.dieNo}<input type="hidden" name="dieNo" value="${tryMold.dieNo}"></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02599") %><!-- 제품설계 --></td>
                    <td>${tryMold.productDesignRole}<input type="hidden" name="productDesignRole" value="${tryMold.productDesignRole}"></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07215") %><!-- 금형제작 --></td>
                    <td>${tryMold.moldMake}<input type="hidden" name="moldMake" value="${tryMold.moldMake}"></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00348") %><!-- Part No --></td>
                    <td>${tryMold.partNo}<input type="hidden" name="partNo" value="${tryMold.partNo}"></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01085") %><!-- 금형설계 --></td>
                    <td>${tryMold.moldDesignRole}<input type="hidden" name="moldDesignRole" value="${tryMold.moldDesignRole}"></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07216") %><!-- 금형Try --></td>
                    <td>${tryMold.moldTryRole}<input type="hidden" name="moldTryRole" value="${tryMold.moldTryRole}"></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00347") %><!-- Part Name --></td>
                    <td colspan="5">${tryMold.partName}<input type="hidden" name="partName" value="${tryMold.partName}"></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09266") %><!-- 금형난이도 --></td>
                    <td>${tryMold.moldRank}<input type="hidden" name="moldRank" value="${tryMold.moldRank}"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09267") %><!-- Try일자 --></td>
                    <td><input type="text"  name="tryDate" style="width:70%" value="${tryMold.tryDate}"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09268") %><!-- Try장소 --></td>
                    <td><input type="text"  name="tryPlace" style="width:95%" value="${tryMold.tryPlace}"/></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "04035") %><!-- 참석자 --></td>
                    <td colspan="5"><input type="text" name="attendant" style="width:98%" value="${tryMold.attendant}" /></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09269") %><!-- 디버깅사유 --></td>
                    <td colspan="5">${tryMold.debugReason}<input type="hidden" name="debugReason" value="${tryMold.debugReason}"/></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03350") %><!-- 특이사항 --></td>
                    <td colspan="5">${tryMold.detail}<input type="hidden" name="detail" value="${tryMold.detail}"/></td>
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
                <col width="14%" />
                <col width="20%" />
                <col width="13%" />
                <col width="20%" />
                <col width="13%" />
                <col width="21%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><font size="1"><%=messageService.getString("e3ps.message.ket_message", "09271") %><!-- Maker[제작사] --></font><span class="red-star">*</span></td>
                    <td>
                        <input type="text"  value="${tryMold.material.materialMaker.name}" name="tempmaterial" style="width:80%" esse="true" esseLabel="Grade">
                        <input type="hidden" name="material" value="${tryMold.material.getPersistInfo().getObjectIdentifier().getStringValue()}">
                        <a href="javascript:TryCondition.selMaterial('Mold')"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                    </td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09272") %><!-- 종류/Grade --><span class="red-star">*</span></td>
                    <td><input type="text"  value="${tryMold.grade}" name="grade" style="width:95%" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09272") %>"></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09273") %><!-- Color[색상] --><span class="red-star">*</span></td>
                    <td><input type="text"  value="${tryMold.color}" name="color" style="width:95%" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09273") %>"></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09274") %><!-- 건조온도 --><span class="red-star">*</span></td>
                    <td><input type="text"  value="${tryMold.dryTemp}" name="dryTemp" value="120" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09274") %>"/> ℃</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09275") %><!-- 건조시간 --><span class="red-star">*</span></td>
                    <td><input type="text"  value="${tryMold.dryTime}" name="dryTime" value="4" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09275") %>"/> Hr</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09276") %><!-- 수분율 --><span class="red-star">*</span></td>
                    <td><input type="text"  value="${tryMold.moistureRate}" name="moistureRate" value="0.03" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09276") %>" /> %</td>
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
                <col width="21%" />
                <col width="15%" />
                <col width="18%" />
                <col width="13%" />
                <col width="20%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09278") %><!-- 금형구조 --><span class="red-star">*</span></td>
                    <td>
                        <ket:select name="moldStruc" codeType="MOLDTYPESTRUCTURE" useOidValue="true" esse="true" esseLabel="금형구조" value="${tryMold.moldStruc.getPersistInfo().getObjectIdentifier().getStringValue()}"/>
                        &nbsp;&nbsp;<input type="text"  value="${tryMold.moldStrucEtc}" name="moldStrucEtc" class="width30"/></td>
                    </td>
                    <td class="title">MoldBase Size<span class="red-star">*</span></td>
                    <td colspan="3">
                        <%=messageService.getString("e3ps.message.ket_message", "00574") %><!-- 가로 --> : <input type="text"  value="${tryMold.moldBaseSizeWidth}" name="moldBaseSizeWidth" class="width10" />&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "01908") %><!-- 세로 --> : <input type="text"  value="${tryMold.moldBaseSizeLength}" name="moldBaseSizeLength" class="width10" />&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09283") %><!-- 높이 --> : <input type="text"  value="${tryMold.moldBaseSizeHeight}" name="moldBaseSizeHeight"  class="width10" /> mm</td>
                </tr>
                <tr>
                    <td class="title">Weight<span class="red-star">*</span></td>
                    <td><input type="text"  value="${tryMold.weight}" name="weight" /> kg</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09279") %><!-- 게이트방식 --><span class="red-star">*</span></td>
                    <td><ket:select name="gateType" value="${tryMold.gateType.getPersistInfo().getObjectIdentifier().getStringValue()}" codeType="MOLDGATETYPE" useOidValue="true" esse="true" esseLabel="게이트방식"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09280") %><!-- Cavity 수 --><span class="red-star">*</span></td>
                    <td><input type="text" value="${tryMold.cavityCount}" name="cavityCount" class="width30" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "09280") %>"/></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09281") %><!-- 취부판 두께 --><span class="red-star">*</span></td>
                    <td><ket:select name="mountThickness" value="${tryMold.mountThickness.getPersistInfo().getObjectIdentifier().getStringValue()}" codeType="MOLDTHICKNESS" useOidValue="true" esse="true" esseLabel="취부판 두께"/></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09282") %><!-- 온도센서 홀 --><span class="red-star">*</span></td>
                    <td><ket:select name="temperatureSensor" value="${tryMold.temperatureSensor.getPersistInfo().getObjectIdentifier().getStringValue()}"  codeType="MOLDTEMPERATURESENSOR" useOidValue="true" esse="true" esseLabel="온도센서 홀"/></td>
                    <td class="title">Cavity <%=messageService.getString("e3ps.message.ket_message", "01632") %><!-- Cavity 비고 --></td>
                    <td><input type="text" value="${tryMold.cavityBigo}" name="cavityBigo" class="width30"/></td>
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
                <col width="12%" />
                <col width="18%" />
                <col width="12%" />
                <col width="20%" />
                <col width="13%" />
                <col width="26%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09285") %><!-- 기계사양 --></td>
                    <td><input type="text"  value="${tryMold.machineSpec}" name="machineSpec" /></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "Screw 직경") %><!-- Screw 직경 --></td>
                    <td>φ<input type="text"  value="${tryMold.screwDiameter}" name="screwDiameter" /></td>
                    <td class="title">Nozzle Type</td>
                    <td><input type="text"  value="${tryMold.nozzleType}" name="nozzleType" /></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09287") %><!-- 유온 --></td>
                    <td><input type="text"  value="${tryMold.oilTemp}" name="oilTemp" />  ℃</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09288") %><!-- 톤수 --></td>
                    <td><input type="text"  value="${tryMold.tone}" name="tone" /> t</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09289") %><!-- 타이바 간격 --></td>
                    <td><input type="text"  value="${tryMold.tiebarInterval}" name="tiebarInterval"/></td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09290") %><!-- 실린더 온도 --></td>
                    <td colspan="5">
                        NH : <input type="text"  value="${tryMold.cylinderTempNH}" name="cylinderTempNH" class="width10" />  ℃&nbsp;&nbsp;&nbsp;
                        H1 : <input type="text"  value="${tryMold.cylinderTempN1}" name="cylinderTempN1" class="width10" />  ℃&nbsp;&nbsp;&nbsp;
                        H2 : <input type="text"  value="${tryMold.cylinderTempN2}" name="cylinderTempN2" class="width10" />  ℃&nbsp;&nbsp;&nbsp;
                        H3 : <input type="text"  value="${tryMold.cylinderTempN3}" name="cylinderTempN3" class="width10" />  ℃&nbsp;&nbsp;&nbsp;
                        H4 : <input type="text"  value="${tryMold.cylinderTempN4}" name="cylinderTempN4" class="width10" />  ℃</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09291") %><!-- 냉각수 온도 --></td>
                    <td colspan="5">
                        <%=messageService.getString("e3ps.message.ket_message", "09292") %><!-- 상 --> : <input type="text"  value="${tryMold.coolWaterTempHigh}" name="coolWaterTempHigh" class="width10" />  ℃&nbsp;&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09293") %><!-- 하 --> : <input type="text"  value="${tryMold.coolWaterTempLow}" name="coolWaterTempLow" class="width10" />  ℃</td>
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
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09295") %><!-- 사출압력 --><span class="red-star">*</span></td>
                    <td colspan="7" >
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : <input type="text"  value="${tryMold.injectPress1}" name="injectPress1" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : <input type="text"  value="${tryMold.injectPress2}" name="injectPress2" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : <input type="text"  value="${tryMold.injectPress3}" name="injectPress3" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09299") %><!-- 4단 --> : <input type="text"  value="${tryMold.injectPress4}" name="injectPress4" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09301") %><!-- 5단 --> : <input type="text"  value="${tryMold.injectPress5}" name="injectPress5" class="width10" />&nbsp;&nbsp;
                        <ket:select name="injectPressUnit" value="${tryMold.injectPressUnit.getPersistInfo().getObjectIdentifier().getStringValue()}" codeType="MOLDUNITINJECTIONPRESSURE" useOidValue="true"/></td>
                </tr>
                <tr>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09302") %><!-- 사출속도 --><span class="red-star">*</span></td>
                    <td colspan="7" >
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : <input type="text"  value="${tryMold.injectSpeed1}" name="injectSpeed1" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : <input type="text"  value="${tryMold.injectSpeed2}" name="injectSpeed2" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : <input type="text"  value="${tryMold.injectSpeed3}" name="injectSpeed3" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09299") %><!-- 4단 --> : <input type="text"  value="${tryMold.injectSpeed4}" name="injectSpeed4" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09301") %><!-- 5단 --> : <input type="text"  value="${tryMold.injectSpeed5}" name="injectSpeed5" class="width10" />&nbsp;&nbsp;
                        mm/s</td>
                </tr>
                <tr>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09303") %><!-- 다단거리전환 --><span class="red-star">*</span></td>
                    <td colspan="7" >
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : <input type="text"  value="${tryMold.shortTransition1}" name="shortTransition1" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : <input type="text"  value="${tryMold.shortTransition2}" name="shortTransition2" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : <input type="text"  value="${tryMold.shortTransition3}" name="shortTransition3" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09299") %><!-- 4단 --> : <input type="text"  value="${tryMold.shortTransition4}" name="shortTransition4" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09301") %><!-- 5단 --> : <input type="text"  value="${tryMold.shortTransition5}" name="shortTransition5" class="width10" />&nbsp;&nbsp;
                        mm</td>
                </tr>
                <tr>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09304") %><!-- 보압 --><span class="red-star">*</span></td>
                    <td colspan="7" >
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : <input type="text"  value="${tryMold.holdPress1}" name="holdPress1" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : <input type="text"  value="${tryMold.holdPress2}" name="holdPress2" class="width10" /> &nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : <input type="text"  value="${tryMold.holdPress3}" name="holdPress3" class="width10" />&nbsp;&nbsp;
                        <ket:select name="packingPressUnit"  value="${tryMold.packingPressUnit.getPersistInfo().getObjectIdentifier().getStringValue()}" codeType="MOLDUNITPACKINGPRESSURE" useOidValue="true"/></td>
                </tr>
                <tr>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09305") %><!-- 보압속도 --><span class="red-star">*</span></td>
                    <td colspan="7" >
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : <input type="text"  value="${tryMold.holdPressSpeed1}" name="holdPressSpeed1" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : <input type="text"  value="${tryMold.holdPress2}" name="holdPressSpeed2" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : <input type="text"  value="${tryMold.holdPressSpeed3}" name="holdPressSpeed3" class="width10" />&nbsp;&nbsp;
                        mm/s</td>
                </tr>
                <tr>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09306") %><!-- 형개속도 --><span class="red-star">*</span></td>
                    <td colspan="7" >
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : <input type="text"  value="${tryMold.moldOpenSpeed1}" name="moldOpenSpeed1" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : <input type="text"  value="${tryMold.moldOpenSpeed2}" name="moldOpenSpeed2" class="width10" /> &nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : <input type="text"  value="${tryMold.moldOpenSpeed3}" name="moldOpenSpeed3" class="width10" />&nbsp;&nbsp;
                        mm/s</td>
                </tr>
                <tr>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09307") %><!-- 형개거리 --><span class="red-star">*</span></td>
                    <td colspan="7" >
                        <%=messageService.getString("e3ps.message.ket_message", "09296") %><!-- 1단 --> : <input type="text"  value="${tryMold.moldOpenDist1}" name="moldOpenDist1" class="width10" />&nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09297") %><!-- 2단 --> : <input type="text"  value="${tryMold.moldOpenDist2}" name="moldOpenDist2" class="width10" /> &nbsp;&nbsp;
                        <%=messageService.getString("e3ps.message.ket_message", "09298") %><!-- 3단 --> : <input type="text"  value="${tryMold.moldOpenDist3}" name="moldOpenDist3" class="width10" />&nbsp;&nbsp;
                        mm<span style ="color:#f60000;font-size:12px; margin-left:20px;"><b>※ Insert 사출물 일 경우 Insert 중량을 반드시 입력 바랍니다</b></span></td>
                </tr>
                <tr>
                    <td rowspan="6" class="bgcol fontb center">E<br>J<br>E<br>C<br>T</td>
                    <td class="bgcol fontb center" >Stroke</td>
                    <td><input type="text"  value="${tryMold.stroke}" name="stroke" /> mm</td>
                    <td rowspan="6" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09308") %><!-- 시간 --></td>
                    <td class="bgcol fontb center">Cycle Time</td>
                    <td><input type="text"  value="${tryMold.cycleTime}" name="cycleTime" /> sec</td>
                    <td rowspan="4" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09309") %><!-- 중량 --></td>
                    <td class="bgcol fontb center">Shot <%=messageService.getString("e3ps.message.ket_message", "09309") %><!-- 중량 --></td>
                    <td><input type="text"  value="${tryMold.shotWeight}" name="shotWeight" / readonly> g</td>
                </tr>
                <tr>
                    <td class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09310") %><!-- 속도 --></td>
                    <td><input type="text"  value="${tryMold.speed}" name="speed" /> mm/s</td>
                    <td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09311") %><!-- 사출시간 --></td>
                    <td><input type="text"  value="${tryMold.pressTime}" name="pressTime" /> sec</td>
                    <td class="bgcol fontb center">Scrap</td>
                    <td><input type="text"  value="${tryMold.sprue}" name="sprue" /> g</td>
                </tr>
                <tr>
                    <td rowspan="2" class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09312") %><!-- 압력 --></td>
                    <td rowspan="2"><input type="text"  value="${tryMold.pressures}" name="pressures" /> KN</td>
                    <td rowspan="2" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09313") %><!-- 냉각시간 --></td>
                    <td rowspan="2"><input type="text"  value="${tryMold.coolingTime}" name="coolingTime" /> sec</td>
                    <td class="bgcol fontb center">Insert<%=messageService.getString("e3ps.message.ket_message", "09309") %><!-- 중량 --></td>
                    <td><input type="text"  value="${tryMold.insertWeight}" name="insertWeight" /> g</td>
                </tr>
                <tr>
                    <td class="bgcol fontb center">(총)Net중량</td>
                    <td><input type="text"  value="${tryMold.cvWeight}" name="cvWeight" /> g</td>
                </tr>
                <tr>
                    <td class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09315") %><!-- 횟수 --></td>
                    <td><input type="text"  value="${tryMold.pressCount}" name="pressCount" /><%=messageService.getString("e3ps.message.ket_message", "09316") %><!-- 회 --></td>
                    <td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09317") %><!-- 보압시간 --></td>
                    <td><input type="text"  value="${tryMold.holdPressTime}" name="holdPressTime" /> sec</td>
                    <td rowspan="2" class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09318") %><!-- 금형온도 --></td>
                    <td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09319") %><!-- 고정측 -->(℃)</td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "09320") %><!-- 입력값 -->:<input type="text"  value="${tryMold.fixedSideTempInput}" name="fixedSideTempInput" class="width20" /> (<%=messageService.getString("e3ps.message.ket_message", "09321") %><!-- 실제값 -->:<input type="text"  value="${tryMold.fixedSideTemp}" name="fixedSideTemp" class="width20"  />)</td>
                </tr>
                <tr>
                    <td class="bgcol fontb center" ><%=messageService.getString("e3ps.message.ket_message", "09322") %><!-- 전진지연시간 --></td>
                    <td><input type="text"  value="${tryMold.delayTime}" name="delayTime" /> sec</td>
                    <td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09323") %><!-- 형폐시간 --></td>
                    <td><input type="text"  value="${tryMold.moldCloseTime}" name="moldCloseTime" /> sec</td>
                    <td class="bgcol fontb center"><%=messageService.getString("e3ps.message.ket_message", "09324") %><!-- 이동측 -->(℃)</td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "09320") %><!-- 입력값 -->:<input type="text"  value="${tryMold.movingSideTempInput}" name="movingSideTempInput" class="width20"/> (<%=messageService.getString("e3ps.message.ket_message", "09321") %><!-- 실제값 -->:<input type="text"  value="${tryMold.movingSideTemp}" name="movingSideTemp" class="width20"/>)</td>
                </tr>
                <tr>
                    <td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09325") %><!-- 배압 --></td>
                    <td><input type="text"  value="${tryMold.backPress}" name="backPress" style="width:80px"/> <ket:select name="backPressUnit" value="${tryMold.backPressUnit.getPersistInfo().getObjectIdentifier().getStringValue()}" codeType="MOLDUNITBACKPRESSURE" useOidValue="true"/></td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09326") %><!-- 계량 --></td>
                    <td><input type="text"  value="${tryMold.mensuration}" name="mensuration" /> rpm</td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09327") %><!-- 저압형체 --></td>
                    <td><input type="text"  value="${tryMold.lowPressShape}" name="lowPressShape" /> <ket:select name="lowPressShapeUnit" value="${tryMold.lowPressShapeUnit.getPersistInfo().getObjectIdentifier().getStringValue()}" codeType="MOLDUNITLOWPRESSURE" useOidValue="true"/></td>
                </tr>
                <tr>
                    <td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09328") %><!-- 보압전환점 --></td>
                    <td><input type="text"  value="${tryMold.holdPressTurnPoint}" name="holdPressTurnPoint" /> mm</td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09329") %><!-- 계량시간 --></td>
                    <td><input type="text"  value="${tryMold.mensurationTime}" name="mensurationTime" /> sec</td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09330") %><!-- 고압형체구간 --></td>
                    <td><input type="text"  value="${tryMold.highPressShapeSection}" name="highPressShapeSection" /> mm</td>
                </tr>
                <tr>
                    <td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09331") %><!-- 쿠션량 --></td>
                    <td><input type="text"  value="${tryMold.cushionAmount}" name="cushionAmount" /> mm</td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09332") %><!-- 흘림방지설정 --></td>
                    <td><input type="text"  value="${tryMold.spillResistentCfg}" name="spillResistentCfg" /> mm</td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09333") %><!-- Shot 수 --></td>
                    <td><input type="text"  value="${tryMold.shotCount}" name="shotCount" /></td>
                </tr>
                <tr>
                    <td colspan="2" class="title" ><%=messageService.getString("e3ps.message.ket_message", "09334") %><!-- 계량거리 --></td>
                    <td><input type="text"  value="${tryMold.mensurationDist}" name="mensurationDist" /> mm</td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09335") %><!-- 흘림방지속도 --></td>
                    <td><input type="text"  value="${tryMold.spillResistantSpeed}" name="spillResistantSpeed" /> mm/s</td>
                    <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "09336") %><!-- 최고충진압 --></td>
                    <td><input type="text" value="${tryMold.maxPress}" name="maxPress" /> Kg/㎠</td>
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
                    <td class="center"><textarea name="description" style="height:40px">${tryMold.description}</textarea></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space10 clear">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><span class="r-space20"><%=messageService.getString("e3ps.message.ket_message", "02796") %><!-- 첨부파일 --></span>
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