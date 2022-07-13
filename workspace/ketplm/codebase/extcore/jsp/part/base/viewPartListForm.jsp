<%@page import="e3ps.common.util.StringUtil"%>
<%@ page import="ext.ket.part.entity.dto.PartListDTO"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%-- 화면에  기구쪽과 기구+전자쪽을 모두 넣어 두고 show hide로 제어함 --%>
<%

     PartListDTO resultList = (PartListDTO)request.getAttribute("result");
     if( resultList != null ){
	    Kogger.debug("viewPartListForm", null, null, resultList.getPartNo());
     }else{
	    Kogger.debug("viewPartListForm", null, null, "resultList empty");
     }

%>
<style type="text/css">
body {
    margin-left: 15px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

.BG_WHITE_RIGHT  {  font-size: 9pt; text-decoration: none; color: #000000; line-height: 12pt; font-family: "굴림"; background-color: #FFFFFF; text-align: right}
</style>
<script type="text/javascript">
<!--

$(document).ready(function(){
	
	$("input[name='partListType']").on("change", function () {
		if(this.value == 'M'){
			
			$("#ket_elec_body").hide();
			$("#ket_elec_body_contents").hide();
			$("#ket_elec_body_bottomLine").hide();
			
			$("#ket_mech_body").show();
			$("#ket_mech_body_contents").show();
			$("#ket_mech_body_bottomLine").show();
			
			$("#mechaFirstStatement").show();
			$("#elecFirstStatement").hide();
			
		}else{
			
			$("#ket_mech_body").hide();
            $("#ket_mech_body_contents").hide();
            $("#ket_mech_body_bottomLine").hide();
            
            $("#ket_elec_body").show();
            $("#ket_elec_body_contents").show();
            $("#ket_elec_body_bottomLine").show();
            
            $("#mechaFirstStatement").hide();
            $("#elecFirstStatement").show();
		}
	}); 	  
    
})

function goExcelExport(){
	
	var partOid = "<%=request.getParameter("partOid")%>";
	var partListType = "";
	
	if(document.all.partListType[0].checked==true)
		partListType = "M";
	
	if(document.all.partListType[1].checked==true)
        partListType = "E";
	
	//alert('개발 중...'+partListType);
	document.hiddenFrame.location.href = "/plm/extcore/jsp/part/base/excelPartList.jsp?partOid="+partOid+"&partListType="+partListType;
}

function ket_mech_body_scrollX(){
	document.all.ket_mech_body_contents.scrollLeft = document.all.ket_mech_body_bottomLine.scrollLeft;
    document.all.ket_mech_body.scrollLeft = document.all.ket_mech_body_bottomLine.scrollLeft;
}

function ket_elec_body_scrollX(){
	document.all.ket_elec_body_contents.scrollLeft = document.all.ket_elec_body_bottomLine.scrollLeft;
    document.all.ket_elec_body.scrollLeft = document.all.ket_elec_body_bottomLine.scrollLeft;
}

-->
</script>
</head>
<body>
<input type="hidden" id="partOid" name="partOid" value="${result.partOid}" />
<div class="contents-wrap">
	<div style="height:80px">
		<div style="position:fixed;left:0;top:0;width:100%;min-width:960px;padding-top:25px;background-color:#fff">
			<div class="inquiry-table03 clearfix box-size" style="margin-left:15px;margin-right:20px">
				<div class="float-l">
					<span class="r-space5"><input type="radio" id="partListType" name="partListType" value="M" checked="checked" /></span>기구
					<span class="r-space5 l-space30"><input type="radio" id="partListType" name="partListType" value="E" /></span>기구 + 전자소자
				</div>
				<div class="float-r">
					<span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:goExcelExport();" class="btn_blue">엑셀저장</a></span><span class="pro-cell b-right"></span></span></span>
					<span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:self.close();" class="btn_blue">닫기</a></span><span class="pro-cell b-right"></span></span></span>
				</div>
			</div>
		</div>
	</div>
	<div class="sub-title-02 b-space20">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>${result.partNo} [${result.partName}]  Rev [${result.partKetRev}]
	</div>
	<div class="b-space25" style="width:100%;display:block;overflow:auto;">
		<table id="ket_mech_top" cellpadding="0" class="table-style-01" style="width: 1500px" summary="">
			<colgroup>
				<col width="70px" />
				<col width="230px" />
				<col width="180px" />
				<col width="70px" />
				<col width="80px" />
				<col width="70px" />
				<col width="50px" />
				<col width="50px" />
				<col width="50px" />
				<col width="50px" />
				<col width="50px" />
				<col width="50px" />
				<col width="60px" />
				<col width="90px" />
				<col width="150px" />
			</colgroup>
			<tbody>
				<tr>
					<td class="center fontb bgcol">Project No</td>
					<td class="center fontb bgcol">Project Name</td>
					<td class="center fontb bgcol">제품구분</td>
					<td class="center fontb bgcol">적용차종</td>
					<td class="center fontb bgcol">SOP</td>
					<td class="center fontb bgcol" rowspan="2">예상수량<br />(천개/년)</td>
					<td class="center fontb bgcol">1년차</td>
					<td class="center fontb bgcol">2년차</td>
					<td class="center fontb bgcol">3년차</td>
					<td class="center fontb bgcol">4년차</td>
					<td class="center fontb bgcol">5년차</td>
					<td class="center fontb bgcol">6년차</td>
					<td class="center fontb bgcol">합계</td>
					<td class="center fontb bgcol">개발담당자</td>
					<td class="center fontb bgcol">개발담당부서</td>
				</tr>
				<tr id="mechaFirstStatement" style="display: bolock">
					<td class="center"><a href="javascript:openProject('${result.projectNo}');">${result.projectNo}</a></td>
					<td class="left">${result.projectName}</td>
					<td class="left">${result.partClazNameKr}</td>
					<td class="center">${result.projectApplyCarType}</td>
					<td class="center">${result.projectSOP}</td>
					<td class="center">${result.projectExpect1Qty}</td>
					<td class="center">${result.projectExpect2Qty}</td>
					<td class="center">${result.projectExpect3Qty}</td>
					<td class="center">${result.projectExpect4Qty}</td>
					<td class="center">${result.projectExpect5Qty}</td>
					<td class="center">${result.projectExpect6Qty}</td>
					<td class="center">${result.projectExpectSumQty}</td>
					<td class="center">${result.projectDevOwner}</td>
					<td class="left">${result.projectDevDept}</td>
				</tr>
				<tr id="elecFirstStatement" style="display: none">
					<td class="center"><a href="javascript:openProject('${result.projectNo}');">${result.projectNo}</a></td>
					<td class="left">${result.projectName}</td>
					<td class="left">${result.partClazNameKr}</td>
					<td class="center">${result.projectApplyCarType}</td>
					<td class="center">${result.projectSOP}</td>
					<td class="center">${result.projectExpect1Qty}</td>
					<td class="center">${result.projectExpect2Qty}</td>
					<td class="center">${result.projectExpect3Qty}</td>
					<td class="center">${result.projectExpect4Qty}</td>
					<td class="center">${result.projectExpect5Qty}</td>
					<td class="center">${result.projectExpect6Qty}</td>
					<td class="center">${result.projectExpectSumQty}</td>
					<td class="center">${result.projectDevHwOwner}</td>
                    <td class="left">${result.projectDevHwDept}</td>
				</tr>
			</tbody>
		</table>
	</div>
	
    <%-- 화면에  기구쪽과 기구+전자쪽을 모두 넣어 두고 show hide로 제어함 --%> 
    <!-- 기구 쪽 -->
	<div id="ket_mech_body" style="width:100%;display:block;overflow:hidden;">
		<table cellpadding="0" class="table-style-01" summary="" style="width:3100px;table-layout: fixed;">
			<colgroup>
				<col width="30px" />
				<c:forEach var="i" begin="0" end="${result.maxLevel}">
                    <col width="30px" />
	            </c:forEach>
				<col width="200px" />
				<col width="100px" />
				<col width="100px" />
				<col width="100px" />
				<col width="80px" />
				<col width="150px" />
				<col width="100px" />
				<col width="50px" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="70px" />
				<col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="70px" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="60px" />
				<col width="60px" />
				<col width="60px" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="80px" />
				<col width="100px" />
			</colgroup>
			<thead>
				<tr>
					<td rowspan="2">No</td>
					<td colspan="${result.maxLevel+1}">LEVEL</td>
					<td colspan="4">제/부품 내역</td>
					<td rowspan="2">단품/<br>Ass'y품 구분</td>
					<td rowspan="2">형상</td>
					<td rowspan="2">제품 SIZE</td>
					<td rowspan="2">U/S</td>
					<td colspan="4">시작금형(설비) 내역</td>
					<td colspan="4">양산금형(설비) 내역</td>
					<td colspan="2">양산 생산조건</td>
					<td colspan="3">Material</td>
					<td colspan="3">제품중량[g/EA]</td>
					<td rowspan="2">생산처</td>
					<td rowspan="2">납품처</td>
					<td rowspan="2">포장물류비(원)</td>
					<td rowspan="2">개발 구분<br />(신규/양산)</td>
					<td rowspan="2">임가공비<br>/<br>구매품 단가</td>
					<td rowspan="2">비 고<br />(특이사항, <br>신규 원재로 단가 등)</td>
				</tr>
				<tr>
				    <c:forEach var="i" begin="0" end="${result.maxLevel}">
                        <td><c:out value="${i}" /></td>
                    </c:forEach>
					<td>품명</td>
					<td>품번</td>
					<td>전산품번</td>
					<td>협력사품번</td>
					<td>Die No.</td>
					<td>C/V</td>
					<td>금형제작구분<br>(외주/사내)</td>
					<td>투자비<br>(천원)</td>
					<td>Die No.</td>
					<td>C/V</td>
					<td>금형제작구분<br>(외주/사내)</td>
					<td>투자비<br>(천원)</td>
					<td>설비Ton</td>
					<td>C/T(SPM)</td>
					<td>Grade</td>
					<td>Finish(Color)</td>
					<td>Maker</td>
					<td>부품</td>
					<td>Scrap</td>
					<td>Total</td>
				</tr>
			</thead>
		</table>
	</div>
	<div id="ket_mech_body_contents" style="width:100%;height:400px;display:block;overflow-y:scroll;overflow-x:hidden;">
	   <table cellpadding="0" class="table-style-01" summary="" style="width:3100px;table-layout: fixed;border-top:0px;">
            <colgroup>
                <col width="30px" />
                <c:forEach var="i" begin="0" end="${result.maxLevel}">
                    <col width="30px" />
                </c:forEach>
                <col width="200px" />
                <col width="100px" />
                <col width="100px" />
                <col width="100px" />
                <col width="80px" />
                <col width="150px" />
                <col width="100px" />
                <col width="50px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="60px" />
                <col width="60px" />
                <col width="60px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="100px" />
            </colgroup>
			<tbody>
			 <c:forEach var="resultItem" items="${result.itemList}" varStatus="status">
				<tr>
					<td class="center" style="height:100px;">${resultItem.indexNo}</td>
				    <c:forEach var="i" begin="0" end="${result.maxLevel}">
				        <c:set var="iStr">${i}</c:set>
                        <td class="center" style="height:100px;"><c:if test="${iStr eq resultItem.lvl}"><c:out value="${i}" /></c:if></td>
                    </c:forEach>
					<td class="left" style="height:100px;">${resultItem.partName}</td>
					<td class="left" style="height:100px;">${resultItem.partCombineNoAlink}</td>
					<td class="left" style="height:100px;"><a href="javascript:openView('${resultItem.partOid}')">${resultItem.partNo}</a></td>
					<td class="center" style="height:100px;">${resultItem.partSpManufPartNo}</td>
					<td class="left" style="height:100px;">${resultItem.partClassificType}</td>
					<td class="center" style="height:100px;"><img style="width:150px; height: 100px;" src="/plm/servlet/e3ps/EDMServlet?command=thumbview&oid=${resultItem.partOid}">
					<td class="left" style="height:100px;">${resultItem.spProdSize}</td>
					<td class="center" style="height:100px;">${resultItem.partUS}</td>
					<td class="left" style="height:100px;"><a href="javascript:openViewPart('${resultItem.partStartDieNo}')">${resultItem.partStartDieNo}</a></td>
					<td class="left" style="height:100px;">${resultItem.partStartCV}</td>
					<td class="left" style="height:100px;">${resultItem.partStartSpMContractSAt}</td>
					<td class="right" style="height:100px;">${resultItem.startInvestMoney}</td>
					<td class="left" style="height:100px;"><a href="javascript:openViewPart('${resultItem.partProdDieNo}')">${resultItem.partProdDieNo}</a></td>
                    <td class="left" style="height:100px;">${resultItem.partProdCV}</td>
                    <td class="left" style="height:100px;">${resultItem.partProdSpMContractSAt}</td>
                    <td class="right" style="height:100px;">${resultItem.prodInvestMoney}</td>
					<td class="left" style="height:100px;">${resultItem.partProdConditionEquipTon}</td>
					<td class="left" style="height:100px;">${resultItem.partProdConditionCTSPM}</td>
					<td class="center" style="height:100px;">${resultItem.partMaterialGrade}</td>
					<td class="center" style="height:100px;">${resultItem.partMaterialFinishColor}</td>
					<td class="center" style="height:100px;">${resultItem.partMaterialMaker}</td>
					<td class="right" style="height:100px;">${resultItem.partProdWeightPartNet}</td>
					<td class="right" style="height:100px;">${resultItem.partProdWeightScrap}</td>
					<td class="right" style="height:100px;">${resultItem.partProdWeightTotal}</td>
					<td class="left" style="height:100px;">${resultItem.partProductionWhere}</td>
					<td class="left" style="height:100px;">${resultItem.partSupplyContract}</td>
					<td class="center" style="height:100px;"><%-- 포장물류비(원) [ 삭제 --%></td>
					<td class="center" style="height:100px;">${resultItem.partDevLevel}</td>
					<td class="center" style="height:100px;"><%-- 임가공비 / 구매품단가 [ 삭제 --%></td>
					<td class="center" style="height:100px;">${resultItem.partNote}<%-- 비고 ( 특이사항, 신규 원재료 단가 등) --%></td>
				</tr>
             </c:forEach>				
			</tbody>
		</table>
      </div>
      <div id="ket_mech_body_bottomLine" style="width:100%;display:block;overflow-x:scroll;overflow-y:hidden;" onscroll="ket_mech_body_scrollX()">
        <table width="3100px" height="0" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td height="0">&nbsp;</td>
            </tr>
        </table>
      </div>
      
      <!-- 기구 + 전자 -->
      <div id="ket_elec_body" style="width:100%;display:none;overflow:hidden;">
		<table cellpadding="0" class="table-style-01" summary="" style="width:3700px;table-layout: fixed;">
            <colgroup>
                <col width="30px" />
                <c:forEach var="i" begin="0" end="${result.maxLevel}">
                    <col width="30px" />
                </c:forEach>
                <col width="200px" />
                <col width="100px" />
                <col width="100px" />
                <col width="100px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="150px" />
                <col width="100px" />
                <col width="50px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="60px" />
                <col width="60px" />
                <col width="60px" />
                <col width="80px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="30px" />
                <col width="30px" />
                <col width="30px" />
                <col width="70px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="100px" />
            </colgroup>
            <thead>
                <tr class="headerLock">
                    <td rowspan="2">No</td>
                    <td colspan="${result.maxLevel+1}">LEVEL</td>
                    <td colspan="4">제/부품 내역</td>
                    <td colspan="2">Reference No.</td>
                    <td rowspan="2">단품/<br>Ass'y폼 구분</td>
                    <td rowspan="2">형상</td>
                    <td rowspan="2">제품 SIZE</td>
                    <td rowspan="2">U/S</td>
                    <td colspan="4">시작금형(설비) 내역</td>
                    <td colspan="4">양산금형(설비) 내역</td>
                    <td colspan="2">양산 생산조건</td>
                    <td colspan="3">Material</td>
                    <td colspan="3">제품중량[g/EA]</td>
                    <td colspan="7">Specification</td>
                    <td colspan="3">MSL</td>
                    <td colspan="2">ESD</td>
                    <td rowspan="2">생산처</td>
                    <td rowspan="2">납품처</td>
                    <td rowspan="2">포장물류비(원)</td>
                    <td rowspan="2">개발 구분<br />(신규/양산)</td>
                    <td rowspan="2">임가공비<br>/<br>구매품 단가</td>
                    <td rowspan="2">비 고<br />(특이사항, <br>신규 원재로 단가 등)</td>
                </tr>
                <tr class="headerLock">
                    <c:forEach var="i" begin="0" end="${result.maxLevel}">
                        <td><c:out value="${i}" /></td>
                    </c:forEach>
                    <td>품명</td>
                    <td>품번</td>
                    <td>전산품번</td>
                    <td>협력사품번</td>
                    <td>TOP</td>
                    <td>BOTTOM</td>
                    <td>Die No.</td>
                    <td>C/V</td>
                    <td>금형제작구분<br>(외주/사내)</td>
                    <td>투자비<br>(천원)</td>
                    <td>Die No.</td>
                    <td>C/V</td>
                    <td>금형제작구분<br>(외주/사내)</td>
                    <td>투자비<br>(천원)</td>
                    <td>설비Ton</td>
                    <td>C/T(SPM)</td>
                    <td>Grade</td>
                    <td>Finish(Color)</td>
                    <td>Maker</td>
                    <td>부품</td>
                    <td>Scrap</td>
                    <td>Total</td>
                    <td>Value</td>
                    <td>Volt</td>
                    <td>Watt</td>
                    <td>Tolerance</td>
                    <td>Temp.(℃)</td>
                    <td>Package(mm)</td>
                    <td>Packing</td>
                    <td>1</td>
                    <td>2</td>
                    <td>3</td>
                    <td>HBM(V)</td>
                    <td>SDM(V)</td>
                </tr>
            </thead>
        </table>
    </div>
    <div id="ket_elec_body_contents" style="width:100%;height:400px;display:none;overflow-y:scroll;overflow-x:hidden;">
        <table cellpadding="0" class="table-style-01" summary="" style="width:3700px;table-layout: fixed;border-top:0px;">
            <colgroup>
                <col width="30px" />
                <c:forEach var="i" begin="0" end="${result.maxLevel}">
                    <col width="30px" />
                </c:forEach>
                <col width="200px" />
                <col width="100px" />
                <col width="100px" />
                <col width="100px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="150px" />
                <col width="100px" />
                <col width="50px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="60px" />
                <col width="60px" />
                <col width="60px" />
                <col width="80px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="70px" />
                <col width="30px" />
                <col width="30px" />
                <col width="30px" />
                <col width="70px" />
                <col width="70px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="80px" />
                <col width="100px" />
            </colgroup>
            <tbody>
                <c:forEach var="resultItem" items="${result.itemList}" varStatus="status">
                <tr>
                    <td class="center" style="height:100px;">${resultItem.indexNo}</td>
                    <c:forEach var="i" begin="0" end="${result.maxLevel}">
                        <c:set var="iStr">${i}</c:set>
                        <td class="center" style="height:100px;"><c:if test="${iStr eq resultItem.lvl}"><c:out value="${i}" /></c:if></td>
                    </c:forEach>
                    <td class="left" style="height:100px;">${resultItem.partName}</td>
                    <td class="left" style="height:100px;">${resultItem.partCombineNoAlink}</td>
                    <td class="left" style="height:100px;"><a href="javascript:openView('${resultItem.partOid}')">${resultItem.partNo}</a></td>
                    <td class="center" style="height:100px;">${resultItem.partSpManufPartNo}</td>
                    <%-- Reference top bottom 추가 --%>
                    <td class="center" style="height:100px;">${resultItem.referenceTop}</td>
                    <td class="center" style="height:100px;">${resultItem.referenceBottom}</td>
                    <%-- Reference top bottom 추가 --%>
                    <td class="left" style="height:100px;">${resultItem.partClassificType}</td>
                    <td class="left" style="height:100px;"><img style="width:150px; height: 100px;" src="/plm/servlet/e3ps/EDMServlet?command=thumbview&oid=${resultItem.partOid}"></td>
                    <td class="left" style="height:100px;">${resultItem.spProdSize}</td>
                    <td class="center" style="height:100px;">${resultItem.partUS}</td>
                    <td class="left" style="height:100px;"><a href="javascript:openViewPart('${resultItem.partStartDieNo}')">${resultItem.partStartDieNo}</a></td>
                    <td class="left" style="height:100px;">${resultItem.partStartCV}</td>
                    <td class="left" style="height:100px;">${resultItem.partStartSpMContractSAt}</td>
                    <td class="right" style="height:100px;">${resultItem.startInvestMoney}</td>
                    <td class="left" style="height:100px;"><a href="javascript:openViewPart('${resultItem.partProdDieNo}')">${resultItem.partProdDieNo}</a></td>
                    <td class="left" style="height:100px;">${resultItem.partProdCV}</td>
                    <td class="left" style="height:100px;">${resultItem.partProdSpMContractSAt}</td>
                    <td class="right" style="height:100px;">${resultItem.prodInvestMoney}</td>
                    <td class="left" style="height:100px;">${resultItem.partProdConditionEquipTon}</td>
                    <td class="left" style="height:100px;">${resultItem.partProdConditionCTSPM}</td>
                    <td class="center" style="height:100px;">${resultItem.partMaterialGrade}</td>
                    <td class="center" style="height:100px;">${resultItem.partMaterialFinishColor}</td>
                    <td class="center" style="height:100px;">${resultItem.partMaterialMaker}</td>
                    <td class="right" style="height:100px;">${resultItem.partProdWeightPartNet}</td>
                    <td class="right" style="height:100px;">${resultItem.partProdWeightScrap}</td>
                    <td class="right" style="height:100px;">${resultItem.partProdWeightTotal}</td>
                    <%-- Specification --%>
                    <td class="center" style="height:100px;">${resultItem.specValue}</td>
                    <td class="center" style="height:100px;">${resultItem.specVolt}</td>
                    <td class="center" style="height:100px;">${resultItem.specWatt}</td>
                    <td class="center" style="height:100px;">${resultItem.specTolerance}</td>
                    <td class="center" style="height:100px;">${resultItem.specTemp}</td>
                    <td class="center" style="height:100px;">${resultItem.specPackage}</td>
                    <td class="center" style="height:100px;">${resultItem.specPacking}</td>
                    <%-- Specification --%>
                    <%-- MSL --%>
                    <td class="center" style="height:100px;">${resultItem.msl1}</td>
                    <td class="center" style="height:100px;">${resultItem.msl2}</td>
                    <td class="center" style="height:100px;">${resultItem.msl3}</td>
                    <%-- MSL --%>
                    <%-- ESD --%>
                    <td class="center" style="height:100px;">${resultItem.hbm}</td>
                    <td class="center" style="height:100px;">${resultItem.sdm}</td>
                    <%-- ESD --%>
                    <td class="left" style="height:100px;">${resultItem.partProductionWhere}</td>
                    <td class="left" style="height:100px;">${resultItem.partSupplyContract}</td>
                    <td class="center" style="height:100px;"><%-- 포장물류비(원) [ 삭제 --%></td>
                    <td class="center" style="height:100px;">${resultItem.partDevLevel}</td>
                    <td class="center" style="height:100px;"><%-- 임가공비 / 구매품단가 [ 삭제 --%></td>
                    <td class="center" style="height:100px;">${resultItem.partNote}<%-- 비고 ( 특이사항, 신규 원재료 단가 등) --%></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
	</div>
	<div id="ket_elec_body_bottomLine" style="width:100%;display:none;overflow-x:scroll;overflow-y:hidden;" onscroll="ket_elec_body_scrollX()">
        <table width="3700px" height="0" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td height="0">&nbsp;</td>
            </tr>
        </table>
      </div>
</div>
<iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
</body>
</html>