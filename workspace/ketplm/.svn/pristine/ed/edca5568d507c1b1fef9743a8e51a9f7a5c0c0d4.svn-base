<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<script src='/plm/extcore/js/cost/costCommon.js?ver=0.1'></script>
<script src='/plm/extcore/js/cost/treeTable.js'></script>
<style>
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
html {
	height: 100%;
	font-family: NanumGothic, "나눔고딕", Nanumgo, NanumBold, Dotum, 돋움, seoul,
		arial, helvetica, Malgun gothic, "맑은고딕";
}

body {
	padding-top: 30px;
	padding-bottom: 30px;
}

.contents-wrap {
	height: 100%;
	text-align: center;
}

.report table {
	table-layout: fixed;
	border: 2px solid black;
	border-collapse: collapse;
}

.report th {
	background: #C0C0C0;
}

.report th,.report td {
	border: 1px solid black;
	padding: 5px !important;
	height: 25px;
	color: #000;
	font-size: 12px;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
}

table .editTd {
	padding: 0px !important;
}

input[type='text'] {
	width: 100%;
	height: 35px;
	padding-left: 5px;
	margin: 0px;
	border: 0px;
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	vertical-align: middle;
	text-align: center;
	font-family: NanumGothic, "나눔고딕", Nanumgo, NanumBold, Dotum, 돋움, seoul,
		arial, helvetica, Malgungothic, "맑은고딕";
}

textarea {
	width: 100%;
	padding-left: 5px;
	margin: 0px;
	border: 0px;
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	vertical-align: middle;
	font-family: NanumGothic, "나눔고딕", Nanumgo, NanumBold, Dotum, 돋움, seoul,
		arial, helvetica, Malgungothic, "맑은고딕";
}

.center {
	text-align: center;
}

.right {
	text-align: right;
}
.tabContent{position:relative;}
.headerTitle {
	width: 1500px;
	height: 150px;
	position: absolute;
	top: 20px;
	text-align: center;
	padding-top: 30px;
}

.border-top-double {
	border-top: 3px double #000 !important;
}

.border-top-bold {
	border-top: 3px #000;
}

.border-top-red{border-top:4px solid #FF0000 !important;}
.border-left-red{border-left:4px solid #FF0000 !important;}
.border-right-red{border-right:4px solid #FF0000 !important;}
.border-side-red{border-left:4px solid #FF0000 !important;border-right:4px solid #FF0000 !important;}
.border-bottom-red{border-bottom:4px solid #FF0000 !important;}

.bold {
	font-weight: bold;
}

.W100 {
	width: 100px;
}

.W120 {
	width: 120px;
}

.W140 {
	width: 140px;
}

.W160 {
	width: 160px;
}

.W180 {
	width: 180px;
}
</style>
<script> 
$(document).ready(function(){
	
	
	var error = '${ERROR}';
	if(error != ''){
		$('#reportBody').hide();
	}
	$("[name='releaseStep']").each(function(){
        if(this.value == '${report.releaseStep}'){
        	this.checked = true;
        }
    });
	
	var tab = CommonUtil.tabs('tabs');
    $('.tabContent').hide();
    $('.tabContent :first').show();
    
    $(".tabref").click(function(){
        $(".tabContent").hide();
        var ref = $(this).attr("href");
        $(ref).show();
    });
    
	$("#estimated tr").each(function(){
		$(this).find("th:first").css("border-left", 0);
		$(this).find("td:first").css("border-left", 0);
		$(this).find("th:last").css("border-right", 0);
        $(this).find("td:last").css("border-right", 0);
	});
	
	$("#estimated th").width(100);
	$("#estimated td").width(100);
	
	$(".num").each(function(){
		
		var text = $(this).text();
		
		if(text.indexOf("%") >= 0){
			text = text.substring(0, text.indexOf("%"));
		}
		
		if(text != "") {
			if($(this).hasClass("THOUSAND")){
	            text = calculateValue(text, 1000, "/");
	        }else if($(this).hasClass("MILLION")){
	            text = calculateValue(text, 1000000, "/");
	        }
			
	        if($(this).hasClass("nonFixed")){
	        	text = text.commaFormat();
	        }else{
	        	text = text.commaFormat(1);
	        }
		}
		
		$(this).text(text);
	});
	
	$("input[name='reportUS']").keyup(function(e){
		calcResultTotal();
	});
	
	
	window.calcResultTotal = function(){
		
		var reportUSList = new Array();
	    $("input[name='reportUS']").each(function(){
	    	reportUSList.push($(this).val());
	    });
	    
	    var cnt = 0;
	    window.console.log(reportUSList);
	    
	    var salesTargetTotal1 = "";
	    var salesTargetTotal2 = "";
	    var salesTargetTotal3 = "";
	    var totalCostTotal1 = "";
	    var totalCostTotal2 = "";
	    var totalCostTotal3 = "";
	    var totalProfitRate1 = "";
	    var totalProfitRate2 = "";
	    var totalProfitRate3 = "";
	    

	    $("#crTable tbody .totalInfo").each(function(i){
	    	
	        //window.console.log("totalCost1 : "+$("input[name=productCostTotal1]").eq(i).val() );
	        
	        //var salesTarget1 = $(this).find(".salesTarget1").text().commaRemove();
	        var salesTarget1 = $("input[name=salesTargetCostExpr1]").eq(i).val();
	        //var totalCost1 = $(this).find(".totalCost1").text().commaRemove();//요 방식은 총원가를 소수점 첫째 자리까지만 가져오기때문에 주석처리하고 아래 방식으로 변경함 2019.02.01
	        var totalCost1 = $("input[name=productCostTotal1]").eq(i).val();
	        //var salesTarget2 = $(this).find(".salesTarget2").text().commaRemove();
	        var salesTarget2 = $("input[name=salesTargetCostExpr2]").eq(i).val();
	        //var totalCost2 = $(this).find(".totalCost2").text().commaRemove();
	        var totalCost2 = $("input[name=productCostTotal2]").eq(i).val();
	        //var salesTarget3 = $(this).find(".salesTarget3").text().commaRemove();
	        var salesTarget3 = $("input[name=salesTargetCostExpr3]").eq(i).val();
	        //var totalCost3 = $(this).find(".totalCost3").text().commaRemove();
	        var totalCost3 = $("input[name=productCostTotal3]").eq(i).val();
	        var reportUS = reportUSList[cnt++].commaRemove();
	        
	        if(salesTarget1 != ""){
	        	salesTarget1 = calculateValue(salesTarget1, reportUS, "*");
	        	salesTargetTotal1 = calculateValue(salesTargetTotal1, salesTarget1, "+");
	        }
	        if(salesTarget2 != ""){
	        	salesTarget2 = calculateValue(salesTarget2, reportUS, "*");
	        	salesTargetTotal2 = calculateValue(salesTargetTotal2, salesTarget2, "+");
	        }
	        if(salesTarget3 != ""){
	        	salesTarget3 = calculateValue(salesTarget3, reportUS, "*");
	        	salesTargetTotal3 = calculateValue(salesTargetTotal3, salesTarget3, "+");
	        }
	        
	        if(totalCost1 != ""){
	        	totalCost1 = calculateValue(totalCost1, reportUS, "*");
	        	totalCostTotal1 = calculateValue(totalCostTotal1, totalCost1, "+");
	        }
	        if(totalCost2 != ""){
	        	totalCost2 = calculateValue(totalCost2, reportUS, "*");
	        	totalCostTotal2 = calculateValue(totalCostTotal2, totalCost2, "+");
	        }
	        if(totalCost3 != ""){
	        	totalCost3 = calculateValue(totalCost3, reportUS, "*");
	        	totalCostTotal3 = calculateValue(totalCostTotal3, totalCost3, "+");
	        }
	    });
	    
	    if(totalCostTotal1 != ""){
	    	totalProfitRate1 = calculateFormulaValue("1-(" + totalCostTotal1 + "/" + salesTargetTotal1 + ")");
	    	totalProfitRate1 = calculateValue(totalProfitRate1, 100, "*").commaFormat(1) + "%";
	    	salesTargetTotal1 = salesTargetTotal1.commaFormat(1);
	    	totalCostTotal1 = totalCostTotal1.commaFormat(1);
	    }
	    
	    if(totalCostTotal2 != ""){
            totalProfitRate2 = calculateFormulaValue("1-(" + totalCostTotal2 + "/" + salesTargetTotal2 + ")");
            totalProfitRate2 = calculateValue(totalProfitRate2, 100, "*").commaFormat(1) + "%";
            salesTargetTotal2 = salesTargetTotal2.commaFormat(1);
            totalCostTotal2 = totalCostTotal2.commaFormat(1);
        }
	    
	    if(totalCostTotal3 != ""){
            totalProfitRate3 = calculateFormulaValue("1-(" + totalCostTotal3 + "/" + salesTargetTotal3 + ")");
            totalProfitRate3 = calculateValue(totalProfitRate3, 100, "*").commaFormat(1) + "%";
            salesTargetTotal3 = salesTargetTotal3.commaFormat(1);
            totalCostTotal3 = totalCostTotal3.commaFormat(1);
        }
        
        $(".salesTargetTotal1").text(salesTargetTotal1);
        $(".salesTargetTotal2").text(salesTargetTotal2);
        $(".salesTargetTotal3").text(salesTargetTotal3);
        
        $(".totalCostTotal1").text(totalCostTotal1);
        $(".totalCostTotal2").text(totalCostTotal2);
        $(".totalCostTotal3").text(totalCostTotal3);
        
        $(".totalProfitRate1").text(totalProfitRate1);
        $(".totalProfitRate2").text(totalProfitRate2);
        $(".totalProfitRate3").text(totalProfitRate3);
        
        $(".compare").each(function(){
        	
        	var value = $(this).text();
        	if(value.indexOf("%") >= 0){
        		value = value.substring(0, value.indexOf("%"));
            }
        	value = value.commaRemove();
        	if(value != null && value.length > 0){
        		
        		var compare = eval("0<=" + value);
        		
                if(compare){
                	$(this).parent("td:first").css("color", "#0000FF");
                }else{
                	$(this).parent("td:first").css("color", "#FF0000");
                }
                $(this).parent("td:first").css("font-weight", "bold");
        	}
        });
	}
	
	calcResultTotal();
	
	window.releaseStepCheck = function(){
		
        var releaseStep = "";
        
        $("[name='releaseStep']").each(function(){
            if(this.checked == true){
                releaseStep = this.value;
            }
        });
        
        return releaseStep;
	}
	
	var beforeReleaseStep = '';
	
	window.approvalRequest = function(reportOid){
		
		$("[name='releaseStep']").each(function(){
	        if(this.value == '${report.releaseStep}'){
	        	beforeReleaseStep = this.value;
	        }
	    });
		
		if(beforeReleaseStep == ''){
			alert("먼저 보고서 배포구분을 지정하여 저장하십시오.");
			return;
		}else{
			goPage(reportOid);
		}
		
	}
	
    window.saveReport = function(){
    	
    	var releaseStep = releaseStepCheck();
    	
    	if(releaseStep == ''){
            alert("보고서 배포구분(정식배포/임시배포)을 선택하십시오.");
            return;
        }
    	
	    var param = new Object();
	    param.taskOid = "${taskOid}";
		param.oid = "${reportOid}";
		param.note = $("textarea[name='note']").val();
		param.logisticsFlow = $("input[name='logisticsFlow']").val();
		param.reviewPurpose = $("input[name='reviewPurpose']").val();
		param.packSpec = $("input[name='packSpec']").val();
		param.releaseStep = releaseStep;
		
		var cpList = new Array();
		
		$("input[name='partOid']").each(function(i){
			var cp = new Object();
			cp.oid = $(this).val();
			cp.reportUS = $("input[name='reportUS']:eq(" + i + ")").val();
			
			window.console.log(cp);
			cpList.push(cp);
		})
		
		param.cpList = cpList;
		
		ajaxCallServer("/plm/ext/cost/saveCostReport", param, function(data){
			beforeReleaseStep = data.releaseStep;
		});
	}
    
    window.openPartCalcuplatePopup = function(id){
    	getOpenWindow2("/plm/ext/cost/costBomEditor.do?isPopup=true&EDITMODE=VIEW&taskOid=${taskOid}&oid=" + id + "&authProjectCheckOid=${authProjectCheckOid}", "CALCULATEPART", 1280, 720);
    }
    
    $("#INVESTMENT_COST").load("/plm/ext/cost/costInvestGrid?taskOid=${taskOid}&caseOrder=${caseOrder}&EDITMODE=${EDITMODE}", function(){
        
    });
    
    var commentHeight = $('#comment_1').height();
    if($('#comment_2').height() >  commentHeight){
        commentHeight = $('#comment_2').height(); //[주요검토 기준 및 ISSUE 사항] 과 [경영층 지시사항] 높이 맞추기 위함 
    }
    
    $('#comment_1').height(commentHeight); //주요검토 기준 및 ISSUE 사항 내용 div
    $('#comment_2').height(commentHeight); //경영층 지시사항 내용 div
    
    
    var commentTop = $('#comment_1').offset().top;//주요검토 기준 및 ISSUE 사항 내용 div top
    var comment_titile1Top = $('#comment_titile1').offset().top;//주요검토 기준 및 ISSUE 사항 Text Top

    $('#comment_2').offset({top: commentTop}); //경영층 지시사항 내용 div top 맞추기
    $('#comment_titile2').offset({top: comment_titile1Top}); //경영층 지시사항 text div 맞추기
    
    
    $(".tabContent").height($(window).height() - 130);
    $(window).resize(function(){
        $(".tabContent").height($(window).height() - 130);
    });
    
    
    window.fnExcelReport = function() {
    	
    	getOpenWindow2("/plm/ext/cost/costReportExcelPopup.do?taskOid=${taskOid}&caseOrder=${caseOrder}", "reportTotalExcel", 400, 200);
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
    
    
    window.doDistribute = function() {

        var url = "/plm/jsp/common/loading.jsp?url=/plm/extcore/jsp/wfm/workflow/requestDistributePopup.jsp&key=pboOids&value=${reportOid }";
        getOpenWindow2(url, 'requestDistribute', 740, 550);
    }
    
    //만약 tree구조를 닫으려면 아래 주석 풀면됨
    //fnChildHide();

    
});
</script>
<c:if test="${not empty ERROR}">
<script>alert("${ERROR}");self.close();</script>
</c:if>
<body style="margin:0;padding:0;overflow:hidden;" id="reportBody">
	        <div class="b-space5" style="text-align:right;width:500px;position:absolute;right:20px;top:30px;z-index:999;" >
	            <c:if test="${'EDIT' eq EDITMODE and ('INWORK' eq report.lifeCycleState or 'REWORK' eq report.lifeCycleState) }">
		        <span class="in-block v-middle r-space7">
		            <span class="pro-table">
		                <span class="pro-cell b-left"></span>
		                <span class="pro-cell b-center">
		                    <a href="#top" onclick="javascript:approvalRequest('${reportOid }');" class="btn_blue">
		                        결재요청
		                    </a>
		                </span>
		                <span class="pro-cell b-right"></span>
		            </span>
		        </span>
		        <span class="in-block v-middle r-space7" style="z-index:999999">
		            <span class="pro-table">
		                <span class="pro-cell b-left"></span>
		                <span class="pro-cell b-center">
		                    <a href="#top" onclick="javascript:saveReport();" class="btn_blue">
		                        저장
		                    </a>
		                </span>
		                <span class="pro-cell b-right"></span>
		            </span>
		        </span>
		        </c:if>
		        <c:if test="${'EDIT' eq EDITMODE or  'VIEW_EXCEL' eq EDITMODE}">
		        <span class="in-block v-middle r-space7">
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center">
                            <a href="#top" onclick="javascript:fnExcelReport();" class="btn_blue">
                                Excel Export
                            </a>
                        </span>
                        <span class="pro-cell b-right"></span>
                    </span>
                </span>
                </c:if>
                <c:if test="${('EDIT' eq EDITMODE or isCreator) and 'APPROVED' eq report.lifeCycleState}">
                <span class="in-block v-middle r-space7">
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center">
                            <a href="#top" onclick="javascript:doDistribute();" class="btn_blue">
                            <%=messageService.getString("e3ps.message.ket_message", "05110")%><%--추가배포--%>
                            </a>
                        </span>
                        <span class="pro-cell b-right"></span>
                    </span>
                </span>
                </c:if>
                <span class="in-block v-middle r-space7">
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center">
                            <a href="#top" onclick="javascript:viewHistory('${reportOid }')" class="btn_blue">
                            <%=messageService.getString("e3ps.message.ket_message", "00785") %><%--결재이력--%>
                            </a>
                        </span>
                        <span class="pro-cell b-right"></span>
                    </span>
                </span>
		    </div>
       <div id="tabs" style="width:98%;margin:0 auto;margin-top:20px;">
	        <ul>
	            <li><a class="tabref" href="#COSTREPORT">개발원가 보고서</a></li>
	            <li><a class="tabref" href="#COSTTOTAL">개발원가 세부내역</a></li>
	            <li><a class="tabref" href="#COSTINVEST">투자비 종합내역</a></li>
	        </ul>
        <div id="COSTREPORT" class="tabContent report" style="padding-top:50px;border:0;border-top:2px solid #82BAD1;border-radius:0;overflow:auto;">
		   <div style="margin: 0 auto; width: 1500px;">
		        <table style="width: 400px;">
		            <tr>
		                <th colspan="3">검토부서</th>
		            </tr>
		            <tr>
		                <td class="center">개발</td>
		                <td class="center">${reportData.devDeptName }</td>
		                <td class="center">${reportData.devUserName }</td>
		            </tr>
		            <tr>
		                <td class="center">영업</td>
		                <td class="center">${reportData.salesDeptName }</td>
		                <td class="center">${reportData.salesName }</td>
		            </tr>
		            <tr>
		                <td class="center">원가</td>
		                <td class="center">${reportData.costDeptName }</td>
		                <td class="center">${reportData.costName }</td>
		            </tr>
		        </table>
		        <div class="headerTitle">
		            <span style="font-size: 40px; font-weight: bold;">개발원가 보고서</span><br>
		            <br> 
		            <span style="font-size: 12px;">원가산출단계 :${report.step} (Rev.${report.version})<input type="radio" name="releaseStep" value="정식배포">정식배포 <input type="radio" name="releaseStep" value="임시배포">임시배포</span>
		            <div style="border: 5px double #FF0000; position:relative;width:60px; left: 950px; top:-90px; color: #FF0000; font-weight: bold; padding: 5px; font-size: 20px;">
                        사외비<br>통제본
                    </div>
		        </div>
		        <br> 1. 개발배경 및 투자비 현황
		        <div class="b-space5 float-r" style="text-align: right;">
		            상태 : <a href="javascript:viewHistory('${reportOid }')">${report.lifeCycleState.display }</a>
		        </div>
		        <table style="width: 1500px; margin-top: 5px;">
		            <colgroup>
		                <col width="100" />
		                <col width="150" />
		                <col width="100" />
		                <col width="150" />
		                <col width="60" />
		                <col width="*" />
		                <col width="*" />
		                <col width="*" />
		                <col width="*" />
		                <col width="*" />
		                <col width="*" />
		                <col width="80" />
		            </colgroup>
		            <tr>
		                <th>Project No.</th>
		                <td class="center bold" colspan="3">
		                <img src="/plm/portal/images/icon_5.png" style="cursor:pointer;" onclick="javascript:openProject('${project.pjtNo}');">${project.pjtNo}
		                </td>
		                <th rowspan="7">신규<br>투자비<br>검토<br>현황<br>(천원)
		                </th>
		                <th>구분</th>
		                <th>수량</th>
		                <th class="border-top-red border-side-red" >투자비 1案</th>
		                <th>투자비 2案</th>
		                <th>투자비 3案</th>
		                <th>상각기준</th>
		                <th rowspan="6">투자비<br>회수<br>기간<br>(년)</th>
		            </tr>
		            <tr>
		                <th>제 품 명</th>
		                <td class="center bold" colspan="3">${reportData.pjtName }</td>
		                <td class="center">Mold</td>
		                <td class="right"><span class="num nonFixed">${reportData.moldinvestUnitTotal}</span>벌</td>
		                <td class="right border-side-red"><span class="num THOUSAND">${reportData['CASE1'].moldinvestPriceTotal}</span></td>
		               <td class="right"><span class="num THOUSAND">${reportData['CASE2'].moldinvestPriceTotal}</span></td>
		                <td class="right"><span class="num THOUSAND">${reportData['CASE3'].moldinvestPriceTotal}</span></td>
		                <td class="center">${reportData.moldreduceOptions}</td>
		            </tr>
		            <tr>
		                <th>고 객 사 명</th>
		                <td class="center bold">${reportData.customer }</td>
		                <th>적 용 차 종</th>
		                <td class="center bold">${reportData.repModel }</td>
		                <td class="center">PRESS</td>
		                <td class="right"><span class="num nonFixed">${reportData.pressinvestUnitTotal}</span> 벌</td>
		                <td class="right border-side-red"><span class="num THOUSAND">${reportData['CASE1'].pressinvestPriceTotal}</span></td>
		                <td class="right"><span class="num THOUSAND">${reportData['CASE2'].pressinvestPriceTotal}</span></td>
		                <td class="right"><span class="num THOUSAND">${reportData['CASE3'].pressinvestPriceTotal}</span></td>
		                <td class="center">${reportData.pressreduceOptions}</td>
		            </tr>
		            <tr>
		                <th>적 용 부 위</th>
		                <td class="center bold">${reportData.applyPart }</td>
		                <th>SOP</th>
		                <td class="center bold">${reportData.sop }</td>
		                <td class="center">조립 설비</td>
		                <td class="right"><span class="num nonFixed">${reportData.equipinvestUnitTotal}</span> Line</td>
		                <td class="right border-side-red"><span class="num THOUSAND">${reportData['CASE1'].equipinvestPriceTotal}</span></td>
		                <td class="right"><span class="num THOUSAND">${reportData['CASE2'].equipinvestPriceTotal}</span></td>
		                <td class="right"><span class="num THOUSAND">${reportData['CASE3'].equipinvestPriceTotal}</span></td>
		                <td class="center">${reportData.equipreduceOptions}</td>
		            </tr>

		            
		            <tr>
		                <th>포 장 사 양</th>
		                <td class="editTd" colspan="3"><input type="text" name="packSpec" value="${report.packSpec }" /></td>
                        <td class="center">구매 금형비</td>
                        <td class="right"><span class="num nonFixed">${reportData.purchaseinvestUnitTotal}</span> 벌</td>
                        <td class="right border-side-red"><span class="num THOUSAND">${reportData['CASE1'].purchaseinvestPriceTotal}</span></td>
                        <td class="right"><span class="num THOUSAND">${reportData['CASE2'].purchaseinvestPriceTotal}</span></td>
                        <td class="right"><span class="num THOUSAND">${reportData['CASE3'].purchaseinvestPriceTotal}</span></td>
                        <td class="center">${reportData.purchasereduceOptions}</td>
                    </tr>
		            
		            <tr>
		                <th>물 류 흐 름</th>
		                <td colspan="3" class="editTd"><input type="text" name="logisticsFlow" value="${report.logisticsFlow }" /></td>
		                <td class="center">기타 투자비</td>
		                <td class="right"><span class="num nonFixed">${reportData.etcinvestUnitTotal}</span>Set</td>
		                <td class="right border-side-red"><span class="num THOUSAND">${reportData['CASE1'].etcinvestPriceTotal}</span></td>
		                <td class="right"><span class="num THOUSAND">${reportData['CASE2'].etcinvestPriceTotal}</span></td>
		                <td class="right"><span class="num THOUSAND">${reportData['CASE3'].etcinvestPriceTotal}</span></td>
		                <td class="center">${reportData.etcreduceOptions}</td>
		            </tr>
		            <tr>
		                <th>검 토 목 적</th>
		                <td colspan="3" class="editTd"><input type="text" name="reviewPurpose" value="${report.reviewPurpose }" /></td>
		                <td class="center border-top-double bold">합계</td>
		                <td class="center border-top-double"></td>
		                <td class="right border-top-double bold border-side-red border-bottom-red"><span class="num THOUSAND">${reportData['CASE1'].investPriceTotal}</span></td>
		                <td class="right border-top-double bold"><span class="num THOUSAND">${reportData['CASE2'].investPriceTotal}</span></td>
		                <td class="right border-top-double bold"><span class="num THOUSAND">${reportData['CASE3'].investPriceTotal}</span></td>
		                <td class="center border-top-double"></td>
		                <th class="right border-top-double"><span class="num">${reportData.correctPeriod }</span>년</th>
		            </tr>
		        </table>
		        <br> 2. 예상 손익 및 원재료 가격 기준
			        <c:if test="${caseOrder == 1 }">
			        [최종안 기준]
			        </c:if>
			        <c:if test="${caseOrder == 2 }">
			        [2안 기준]
			        </c:if>
			        <c:if test="${caseOrder == 3 }">
			        [3안 기준]
			        </c:if>
		        <br>
		        <div style="width: 150px; margin-top: 5px; float: left;">
		            <table style="width: 150px;border-right:0;">
		                <colgroup>
		                  <col width="*">
		                  <col width="50">
		                </colgroup>
		                <tr>
		                    <th colspan="2">구분</th>
		                </tr>
		                <c:forEach items="${reportData.caDataList }" var="caData" varStatus="stat">
		                    <tr>
		                        <c:if test="${0 eq stat.index }">
		                            <th rowspan="${fn:length(reportData.caDataList)}">판 매 량(천개)</th>
		                        </c:if>
		                        <th>No. ${stat.count }</th>
		                    </tr>
		                </c:forEach>
		                <tr>
		                    <th colspan="2">매 출 액 (백만원)</th>
		                </tr>
		                <tr>
		                    <th colspan="2">영업이익(백만원)</th>
		                </tr>
		                <tr>
		                    <th colspan="2">영 업 이 익 율</th>
		                </tr>
		            </table>
		        </div>
		        <div style="width: 900px; margin-top: 5px;float:left;overflow-y:hidden;overflow-x:auto;">
		            <table id="estimated" style="width:100%;border-left:0;border-right:0;">
		                <tr>
		                    <c:forEach begin="1" end="${reportData.caYearSize }" varStatus="stat">
		                        <th>${stat.count}년차</th>
		                    </c:forEach>
		                    <c:if test="${reportData.caYearSize <= 8}">
								<c:forEach begin="${reportData.caYearSize + 1 }" end="8" varStatus="stat">
								    <th>${stat.index}년차</th>
								</c:forEach>
		                    </c:if>
		                </tr>
		                <c:forEach items="${reportData.caDataList }" var="caData" varStatus="stat">
		                    <tr>
		                        <c:forEach begin="1" end="${reportData.caYearSize}" varStatus="yearStat">
		                            <td class="right"><span class="num THOUSAND">${caData.salesQtyList[yearStat.index - 1]}</span></td>
		                        </c:forEach>
	                        <c:if test="${reportData.caYearSize <= 8}">
                                <c:forEach begin="${reportData.caYearSize + 1 }" end="8" varStatus="stat">
                                    <td></td>
                                </c:forEach>
                            </c:if>
		                    </tr>
		                </c:forEach>
		                <tr>
		                    <c:forEach begin="1" end="${reportData.caYearSize}" varStatus="stat">
		                        <c:choose>
                                    <c:when test="${report.releaseStep ne '임시배포' }">
                                       <td class="right"><span class="num MILLION">${reportData.caTotalSales[stat.count] }</span></td>
                                    </c:when>
                                    <c:otherwise> <td class="center">-</td> </c:otherwise>
                                </c:choose>
		                    </c:forEach>
		                    <c:if test="${reportData.caYearSize <= 8}">
                                <c:forEach begin="${reportData.caYearSize + 1 }" end="8" varStatus="stat">
                                    <td></td>
                                </c:forEach>
                            </c:if>
		                </tr>
		                <tr>
		                    <c:forEach begin="1" end="${reportData.caYearSize}" varStatus="stat">
		                        <c:choose>
                                    <c:when test="${report.releaseStep ne '임시배포' }">
                                       <td class="right"><span class="num MILLION">${reportData.caProfitCost[stat.count] }</span></td>
                                    </c:when>
                                    <c:otherwise> <td class="center">-</td> </c:otherwise>
                                </c:choose>
		                    </c:forEach>
		                    <c:if test="${reportData.caYearSize <= 8}">
                                <c:forEach begin="${reportData.caYearSize + 1 }" end="8" varStatus="stat">
                                    <td></td>
                                </c:forEach>
                            </c:if>
		                </tr>
		                <tr>
		                    <c:forEach begin="1" end="${reportData.caYearSize}" varStatus="stat">
		                        <c:choose>
                                    <c:when test="${report.releaseStep ne '임시배포' }">
                                       <td class="right"><span class="num compare">${reportData.caProfitRate[stat.count] * 100}</span>%</td>
                                    </c:when>
                                    <c:otherwise> <td class="center">-</td> </c:otherwise>
                                </c:choose>
		                    </c:forEach>
		                    <c:if test="${reportData.caYearSize <= 8}">
                                <c:forEach begin="${reportData.caYearSize + 1 }" end="8" varStatus="stat">
                                    <td></td>
                                </c:forEach>
                            </c:if>
		                </tr>
		            </table>
		        </div>
		        <table style="width: 450px; margin-top: 5px;border-left:0;">
		            <tr>
		                <th>합계</th>
		                <th>수지 재료 단가</th>
		                <th>비철 LME 시세</th>
		                <th>CR 적용 기준</th>
		            </tr>
		            <c:forEach items="${reportData.caDataList }" var="caData" varStatus="stat">
		                <tr>
		                    <td class="right bold"><span class="num THOUSAND">${caData.totalQty }</span></td>
		                    <td class="center"><fmt:formatDate value="${report.createTimestamp }" pattern="yy년 MM월" /></td>
		                    <td class="center">
								<c:choose>
									<c:when test="${not empty caData.metalLmeCost }">
									   <span class="num">${caData.metalLmeCost }</span>
									</c:when>
									<c:otherwise> - </c:otherwise>
								</c:choose>
		                    </td>
		                    <td class="center">
		                    <c:choose>
		                      <c:when test="${caData.cr ne '0' and caData.applyYear ne '0' }">
		                          ${caData.cr }%/${caData.applyYear }년
		                      </c:when>
		                      <c:otherwise> - </c:otherwise>
		                    </c:choose>
		                    </td>
		                </tr>
		            </c:forEach>
		            <tr>
						<c:choose>
							<c:when test="${report.releaseStep ne '임시배포' }">
								<td class="right bold"><span class="num MILLION">${reportData.totalSaleTotal }</span></td>
							</c:when>
							<c:otherwise>
								<td class="center">-</td>
							</c:otherwise>
						</c:choose>
		                <th colspan="3">비고</th>
		            </tr>
		            <tr>
		                <c:choose>
                            <c:when test="${report.releaseStep ne '임시배포' }">
                                <td class="right bold"><span class="num MILLION">${reportData.profitCostTotal }</span></td>
                            </c:when>
                            <c:otherwise>
                                <td class="center">-</td>
                            </c:otherwise>
                        </c:choose>
		                <td colspan="3" rowspan="2" style="padding: 0 !important;">
		                    <textarea name="note" style="height: 70px;">${report.bigo}</textarea>
		                </td>
		            </tr>
		            <tr>
		                <c:choose>
                            <c:when test="${report.releaseStep ne '임시배포' }">
                                <td class="right bold"><span class="num compare">${reportData.profitRateTotal  * 100}</span>%</td>
                            </c:when>
                            <c:otherwise>
                                <td class="center">-</td>
                            </c:otherwise>
                        </c:choose>
		            </tr>
		        </table>
		
		        <br> 3. 원가 계산 결과
		        <table id="crTable" style="width: 1500px; margin-top: 5px;">
		            <colgroup>
		                <col width="60" />
		                <col width="50" />
		                <col width="150" />
		                <col width="100" />
		                <col width="50" />
		            </colgroup>
		            <tr>
		                <th rowspan="${reportData.calcResultRow + 3 }">가격정보</th>
		                <th rowspan="2" colspan="3">제품명</th>
		                <th rowspan="2">Set<br>U/S
		                </th>
		                <th colspan="4" class="border-top-red border-side-red">
		                	<a href="/plm/ext/cost/costReportPopup?taskOid=${taskOid }" style="color:#000;">1案</a>
	                	</th>
		                <th colspan="4">
		                	<c:if test="${not empty reportData.case2Exist }">
			                <a href="/plm/ext/cost/costReportPopup?taskOid=${taskOid }&caseOrder=2" style="color:#000;">
			                	2案
			                </a>
			                </c:if>
			                <c:if test="${empty reportData.case2Exist }">
			                	2案
			                </c:if>
		                </th>
		                <th colspan="4">
		                	<c:if test="${not empty reportData.case3Exist }">
			                <a href="/plm/ext/cost/costReportPopup?taskOid=${taskOid }&caseOrder=3" style="color:#000;">
			                	3案
			                </a>
			                </c:if>
			                <c:if test="${empty reportData.case3Exist }">
			                	3案
			                </c:if>
		                </th>
		            </tr>
		            <tr>
		                <th class="border-left-red">판매목표가</th>
		                <th>총원가</th>
		                <th>수익율</th>
		                <th class="border-right-red">산출조건</th>
		                
		                <th>판매목표가</th>
		                <th>총원가</th>
		                <th>수익율</th>
		                <th>산출조건</th>
	                
		                <th>판매목표가</th>
		                <th>총원가</th>
		                <th>수익율</th>
		                <th>산출조건</th>
		            </tr>
		            <c:forEach items="${reportData.calcData }" var="calcData" varStatus="stat">
		                <c:choose>
		                    <c:when test="${calcData.isAppoint}">
		                        <tr>
		                            <td rowspan="3" class="center">No. ${stat.count }<input type="hidden" name="partOid" value="${calcData.partOid }" /></td>
		                            <td rowspan="3" style="border-right: 0px;" title="${calcData.partName }">
                                        <img src="/plm/portal/images/icon_5.png" style="cursor:pointer;" onclick="javascript:openPartCalcuplatePopup('${calcData.partOid}')">${calcData.partName }
		                            </td>
		                            <td class="center">KET품 합계</td>
		                            <td class="editTd" rowspan="3"><input type="text" name="reportUS" value="${calcData.reportUS }" /></td>
		                            
		                            
		                            <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
                                            <td class="right border-left-red"><span class="num compare">${calcData.ketSalesTargetCost1 }</span></td>
		                                    <td class="right"><span class="num compare">${calcData.ketCostAllTotal1 }</span></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.ketProfitRate1 }">
		                                          <span class="num compare">${calcData.ketProfitRate1 * 100 }</span>%
		                                        </c:if>
		                                    </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right border-left-red">-</td>
                                            <td class="right"><span class="num compare">${calcData.ketCostAllTotal1 }</span></td>
                                            <td class="right">-</td>
                                        </c:otherwise>
                                    </c:choose>
		                            
		                            
                                    
                                    
		                            <td class="center border-right-red" title="${calcData.caseNote1 }" rowspan="3">${calcData.caseNote1 }</td>
		                            
		                            
                                    
                                    
                                    <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
                                            <td class="right"><span class="num compare">${calcData.ketSalesTargetCost2 }</span></td>
		                                    <td class="right"><span class="num compare">${calcData.ketCostAllTotal2 }</span></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.ketProfitRate2 }">
		                                          <span class="num compare">${calcData.ketProfitRate2 * 100 }</span>%
		                                        </c:if>
		                                    </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right">-</td>
                                            <td class="right"><span class="num compare">${calcData.ketCostAllTotal2 }</span></td>
                                            <td class="right">-</td>
                                        </c:otherwise>
                                    </c:choose>
                                    
		                            <td class="center" title="${calcData.caseNote2 }" rowspan="3">${calcData.caseNote2 }</td>

                                    <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
                                            <td class="right"><span class="num compare">${calcData.ketSalesTargetCost3 }</span></td>
		                                    <td class="right"><span class="num compare">${calcData.ketCostAllTotal3 }</span></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.ketProfitRate3 }">
		                                          <span class="num compare">${calcData.ketProfitRate3 * 100 }</span>%
		                                        </c:if>
		                                    </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right">-</td>
                                            <td class="right"><span class="num compare">${calcData.ketCostAllTotal3 }</span></td>
                                            <td class="right">-</td>
                                        </c:otherwise>
                                    </c:choose>
                                    
		                            <td class="center" title="${calcData.caseNote3 }" rowspan="3">${calcData.caseNote3 }</td>
		                            
		                        </tr>
		                        <tr>
		                            
                                    <td class="center">지정품 합계</td>
                                    <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
		                                    <td class="right border-left-red"><span class="num compare">${calcData.appointSales1 }</span></td>
		                                    <td class="right"><span class="num compare">${calcData.subCostExceptTotal1 }</span></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.appProfitRate1 }">
		                                          <span class="num compare">${calcData.appProfitRate1 * 100 }</span>%
		                                        </c:if>
		                                    </td>
		                                    
		                                    <td class="right"><span class="num compare">${calcData.appointSales2 }</span></td>
		                                    <td class="right"><span class="num compare">${calcData.subCostExceptTotal2 }</span></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.appProfitRate2 }">
		                                          <span class="num compare">${calcData.appProfitRate2 * 100 }</span>%
		                                        </c:if>
		                                    </td>
                                    
		                                    <td class="right"><span class="num compare">${calcData.appointSales3 }</span></td>
		                                    <td class="right"><span class="num compare">${calcData.subCostExceptTotal3 }</span></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.appProfitRate3 }">
		                                          <span class="num compare">${calcData.appProfitRate3 * 100 }</span>%
		                                        </c:if>
		                                    </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right border-left-red">-</td>
                                            <td class="right"><span class="num compare">${calcData.subCostExceptTotal1 }</span></td>
                                            <td class="right">-</td>
                                            
                                            <td class="right">-</td>
                                            <td class="right"><span class="num compare">${calcData.subCostExceptTotal2 }</span></td>
                                            <td class="right">-</td>
                                            
                                            <td class="right">-</td>
                                            <td class="right"><span class="num compare">${calcData.subCostExceptTotal3 }</span></td>
                                            <td class="right">-</td>
                                        </c:otherwise>
                                    </c:choose>
		                            
		                            
	                            
		                            
		                        </tr>
		                        <tr class="totalInfo">
		                            <td style="border-left: 0px;"></td>
		                            
		                            <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
                                            <td class="right border-left-red"><span class="num compare salesTarget1">${calcData.salesTargetCostExpr1 }</span><input type="hidden" name="salesTargetCostExpr1" value="${calcData.salesTargetCostExpr1 }"/></td>
		                                    <td class="right"><span class="num compare totalCost1">${calcData.productCostTotal1 }</span><input type="hidden" name="productCostTotal1" value="${calcData.productCostTotal1 }"/></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.profitRateExpr1 }">
		                                          <span class="num compare">${calcData.profitRateExpr1 * 100 }</span>%
		                                        </c:if>
		                                    </td>
		                                    
		                                    <td class="right"><span class="num compare salesTarget2">${calcData.salesTargetCostExpr2 }</span><input type="hidden" name="salesTargetCostExpr2" value="${calcData.salesTargetCostExpr2 }"/</td>
		                                    <td class="right"><span class="num compare totalCost2">${calcData.productCostTotal2 }</span><input type="hidden" name="productCostTotal2" value="${calcData.productCostTotal2 }"/></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.profitRateExpr2 }">
		                                          <span class="num compare">${calcData.profitRateExpr2 * 100 }</span>%
		                                        </c:if>
		                                    </td>
		                                
		                                    <td class="right"><span class="num compare salesTarget3">${calcData.salesTargetCostExpr3 }</span><input type="hidden" name="salesTargetCostExpr3" value="${calcData.salesTargetCostExpr3 }"/</td>
		                                    <td class="right"><span class="num compare totalCost3">${calcData.productCostTotal3 }</span><input type="hidden" name="productCostTotal3" value="${calcData.productCostTotal3 }"/></td>
		                                    <td class="right">
		                                        <c:if test="${not empty calcData.profitRateExpr3 }">
		                                          <span class="num compare">${calcData.profitRateExpr3 * 100 }</span>%
		                                        </c:if>
		                                    </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right border-left-red"><span class="salesTarget1">-</span></td>
                                            <td class="right"><span class="num compare totalCost1">${calcData.productCostTotal1 }</span><input type="hidden" name="productCostTotal1" value="${calcData.productCostTotal1 }"/></td>
                                            <td class="right">
                                                -
                                            </td>
                                            
                                            <td class="right"><span class="salesTarget2">-</span></td>
                                            <td class="right"><span class="num compare totalCost2">${calcData.productCostTotal2 }</span><input type="hidden" name="productCostTotal2" value="${calcData.productCostTotal2 }"/></td>
                                            <td class="right">
                                                -
                                            </td>
                                        
                                            <td class="right"><span class="salesTarget3">-</span></td>
                                            <td class="right"><span class="num compare totalCost3">${calcData.productCostTotal3 }</span><input type="hidden" name="productCostTotal3" value="${calcData.productCostTotal3 }"/></td>
                                            <td class="right">
                                                -
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
		                            
		                            
		                        </tr>
		                    </c:when>
		                    <c:otherwise>
		                        <tr class="totalInfo">
		                            <td class="center">No. ${stat.count } <input type="hidden" name="partOid" value="${calcData.partOid }" /></td>
		                            <td colspan="2" title="${calcData.partName }">
			                            <img src="/plm/portal/images/icon_5.png" style="cursor:pointer;" onclick="javascript:openPartCalcuplatePopup('${calcData.partOid}')">${calcData.partName }
		                            </td>
		                            <td class="editTd"><input type="text" name="reportUS" value="${calcData.reportUS }" /></td>
		                            
		                            <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
                                            <td class="right border-left-red"><span class="num compare salesTarget1">${calcData.salesTargetCostExpr1 }</span><input type="hidden" name="salesTargetCostExpr1" value="${calcData.salesTargetCostExpr1 }"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right border-left-red"><span class="salesTarget1">-</span></td>
                                        </c:otherwise>
                                    </c:choose>
                                    
		                            <td class="right"><span class="num compare totalCost1">${calcData.productCostTotal1 }</span><input type="hidden" name="productCostTotal1" value="${calcData.productCostTotal1 }"/></td>

									<td class="right">
									   <c:choose>
											<c:when test="${report.releaseStep ne '임시배포' }">
												<c:if test="${not empty calcData.profitRateExpr1 }">
													<span class="num compare">${calcData.profitRateExpr1 * 100 }</span>%
                                                </c:if>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
									<td class="center border-right-red" title="${calcData.caseNote1 }">${calcData.caseNote1 }</td>
		                            
		                            
		                            
		                            <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
                                            <td class="right"><span class="num compare salesTarget2">${calcData.salesTargetCostExpr2 }</span><input type="hidden" name="salesTargetCostExpr2" value="${calcData.salesTargetCostExpr2 }"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right"><span class="salesTarget2">-</span></td>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    
		                            <td class="right"><span class="num compare totalCost2">${calcData.productCostTotal2 }</span><input type="hidden" name="productCostTotal2" value="${calcData.productCostTotal2 }"/></td>
		                            <td class="right">
                                        <c:choose>
                                            <c:when test="${report.releaseStep ne '임시배포' }">
                                                <c:if test="${not empty calcData.profitRateExpr2 }">
                                                    <span class="num compare">${calcData.profitRateExpr2 * 100 }</span>%
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
		                            <td class="center" title="${calcData.caseNote2 }">${calcData.caseNote2 }</td>
		                            
		                            <c:choose>
                                        <c:when test="${report.releaseStep ne '임시배포' }">
                                            <td class="right"><span class="num compare salesTarget3">${calcData.salesTargetCostExpr3 }</span><input type="hidden" name="salesTargetCostExpr3" value="${calcData.salesTargetCostExpr3 }"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="right"><span class="salesTarget3">-</span></td>
                                        </c:otherwise>
                                    </c:choose>
                                    
		                            <td class="right"><span class="num compare totalCost3">${calcData.productCostTotal3 }</span><input type="hidden" name="productCostTotal3" value="${calcData.productCostTotal3 }"/></td>
		                            <td class="right">
										<c:choose>
                                            <c:when test="${report.releaseStep ne '임시배포' }">
                                                <c:if test="${not empty calcData.profitRateExpr3 }">
                                                    <span class="num compare">${calcData.profitRateExpr3 * 100 }</span>%
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
	                                </td>
		                            <td class="center" title="${calcData.caseNote3 }">${calcData.caseNote3 }</td>
		                        </tr>
		                    </c:otherwise>
		                </c:choose>
		            </c:forEach>
		            <tr>
		                <th colspan="4">Set Total</th>
		                <td class="right border-top-double border-bottom-red border-left-red">
		                
		                <c:choose>
		                  <c:when test="${report.releaseStep ne '임시배포' }">
		                  <span class="num compare salesTargetTotal1"></span>
		                  </c:when>
		                  <c:otherwise>-</c:otherwise>
					    </c:choose>

						</td>
		                <td class="right border-top-double border-bottom-red"><span class="num compare totalCostTotal1"></span></td>
		                <td class="right border-top-double border-bottom-red">
		                <c:choose>
                          <c:when test="${report.releaseStep ne '임시배포' }">
                          <span class="num compare totalProfitRate1"></span>
                          </c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
		                </td>
		                <td class="center border-top-double border-bottom-red border-right-red"></td>
		                
		                <td class="right border-top-double">
		                <c:choose>
                          <c:when test="${report.releaseStep ne '임시배포' }">
                          <span class="num compare salesTargetTotal2"></span>
                          </c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
		                </td>
		                <td class="right border-top-double"><span class="num compare totalCostTotal2"></span></td>
		                <td class="right border-top-double">
		                <c:choose>
                          <c:when test="${report.releaseStep ne '임시배포' }">
                          <span class="num compare totalProfitRate2"></span>
                          </c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
		                </td>
		                <td class="center border-top-double"></td>
		                
		                <td class="right border-top-double">
		                <c:choose>
                          <c:when test="${report.releaseStep ne '임시배포' }">
                          <span class="num compare salesTargetTotal3"></span>
                          </c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
		                </td>
		                <td class="right border-top-double"><span class="num compare totalCostTotal3"></span></td>
		                <td class="right border-top-double">
		                <c:choose>
                          <c:when test="${report.releaseStep ne '임시배포' }">
                          <span class="num compare totalProfitRate3"></span>
                          </c:when>
                          <c:otherwise>-</c:otherwise>
                        </c:choose>
		                </td>
		                <td class="center border-top-double"></td>
		                
		            </tr>
		        </table>
		        <br>
		        <table style="width: 1500px; border: 0px; margin-top: 5px;">
		            <tr>
		                <td style="border:0px; font-size: 16px;">
		                    <div id="comment_titile1">4. 주요 검토 기준 및 ISSUE 사항</div>
		                    <c:if test="${not empty reportData.comment1}">
		                    <div id="comment_1" style="width: 100%; border: 1px solid #000; margin-top: 5px; height: auto; overflow:hidden;">${reportData.comment1 }</div>
		                    </c:if>
		                    <c:if test="${empty reportData.comment1}">
		                    <div id="comment_1" style="width: 100%; border: 1px solid #000; margin-top: 5px; height: 200px;">${reportData.comment1 }</div>
		                    </c:if>
		                    
		                </td>
		                <td style="border:0;width: 80px;"></td>
		                <td style="border:0;font-size: 16px;"><div id=comment_titile2>4. 경영층 지시사항</div>
		                    <c:if test="${not empty reportData.comment2}">
		                    <div id="comment_2" style="width: 100%; border: 1px solid #000; margin-top: 5px; height: auto; overflow:hidden;">${reportData.comment2 }</div>
		                    </c:if>
		                    <c:if test="${empty reportData.comment2}">
		                    <div id="comment_2" style="width: 100%; border: 1px solid #000; margin-top: 5px; height: 200px;">${reportData.comment2 }</div>
		                    </c:if>
		                    
		                </td>
		            </tr>
		        </table>
		    </div>
        </div>
        <div id="COSTTOTAL" class="tabContent report" style="padding-top:50px;border:0;border-top:2px solid #82BAD1;border-radius:0;overflow:auto;">      
			<div style="margin: 0 auto; width: 1500px;">
				<div class="headerTitle">
				<span style="font-size: 30px; font-weight: bold;">개발원가 세부내역
					<c:if test="${caseOrder eq 1 }">
						<c:if test="${not empty reportData.calcData[0].caseNote1}">(${reportData.calcData[0].caseNote1})</c:if>
					</c:if>
					<c:if test="${caseOrder eq 2 }">
						<c:if test="${not empty reportData.calcData[0].caseNote2}">(${reportData.calcData[0].caseNote2})</c:if>
					</c:if>
					<c:if test="${caseOrder eq 3 }">
						<c:if test="${not empty reportData.calcData[0].caseNote3}">(${reportData.calcData[0].caseNote3})</c:if>
					</c:if>
				</span>
				
				<br>
				</div>
				<table id="COSTTOTALTABLE" style="width:1500px;margin-top:50px;border:0;" class="tree-table">
				    <colgroup>
				        <col width="200"/>
				    </colgroup>
				    <thead>
                    <tr style="border-left:2px solid #000;border-top:2px solid #000;border-right:2px solid #000;">
                        <th rowspan="2">품명</th>
                        <th rowspan="2">US</th>
                        <th colspan="4" style="border-bottom:0;">제조원가</th>
                        <th rowspan="2" style="border-left:1px;">관리비</th>
                        <th rowspan="2">감가비</th>
                        <th rowspan="2">총원가</th>
                        <th rowspan="2">판매 목표가</th>
                        <th rowspan="2">수익율</th>
                        <th rowspan="2">원가비중</th>
                    </tr>
                    <tr style="border-left:2px solid #000;border-right:2px solid #000;">
                        <th>재료비</th>
                        <th>노무비</th>
                        <th>경비</th>
                        <th style="border-top:0;"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${reportData.cpList }" var="cp" varStatus="stat">
                    <c:if test="${not empty cp.masterReference }">
                        <c:if test="${stat.index ne 0 }">
                            <tr>
                                <th style="border-bottom:0;border-left:2px solid #000;" colspan="2" title="${product.partName }">${product.partName }</th>
		                        <td class="right"><span class="num">${product.materialCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.laborCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.expenseExpr }</span></td>
		                        <td class="right"><span class="num bold">${product.mfcCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.manageCostExpr }</span></td>
		                        <td class="right"><span class="num">${product.reduceCostExpr }</span></td>
		                        <td class="right"><span class="num compare">${product.totalCostExpr }</span></td>
		                        
		                        <c:choose>
                                <c:when test="${report.releaseStep ne '임시배포' }">
                                <td class="right"><span class="num bold">${product.salesTargetCostExpr }</span></td>
                                <td class="right"><span class="num compare">${product.profitRateExpr * 100}</span>%</td>
                                </c:when>
                                <c:otherwise>
                                <td class="right">-</td>
                                <td class="right">-</td> 
                                </c:otherwise>
                                </c:choose>
		                        
		                        <td class="center" style="border-right:2px solid #000;"><span class="num compare">100</span>%</td>
                            </tr>
                            <tr>
                                <th style="border-bottom:0;border-left:2px solid #000;" colspan="2">(총원가 대비 구성비)</th>
                                <td class="right">(<span class="num compare">${(product.materialCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
                                <td class="right">(<span class="num compare">${(product.laborCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
                                <td class="right">(<span class="num compare">${(product.expenseExpr / product.totalCostExpr) * 100 }</span>%)</td>
                                <td class="right">(<span class="num compare">${(product.mfcCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
                                <td class="right">(<span class="num compare">${(product.manageCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
                                <td class="right">(<span class="num compare">${(product.reduceCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
                                <td class="right">(<span class="num compare">100</span>%)</td>
                                <td colspan="3" style="border-right:2px solid #000;"></td>
                            </tr>
                            <tr>
                                <td style="border:2px solid #000;border-left:0;border-right:0;height:10px;" colspan="12"></td>
                            </tr>
                            <script>
                                $("tr[data-productoid='${productOid}']").each(function(){
                                	var totalCostExpr = $(this).find(".totalCostExpr").text().commaRemove();
                                	var ptotalCostExpr = "${product.totalCostExpr}";
                                	
                                	var percent = calculateValue(totalCostExpr, ptotalCostExpr, "/");
                                	percent = calculateValue(percent, 100, "*");
                                	if(isNaN(percent)) percent = "0"; 
                                	$(this).find("td:last").html("<span class='num compare'>" + percent +"</span>%");
                                });
                            </script>
                    </c:if>
                        <c:set value="${cp.masterReference }" var="productOid"></c:set>
                        <c:set value="${cp }" var="product"></c:set>
                        
                        <c:set target="${product}" property="materialCostExpr"  value="${product.materialCostExpr * product.us }" />
                        <c:set target="${product}" property="laborCostExpr"     value="${product.laborCostExpr * product.us }" />
                        <c:set target="${product}" property="expenseExpr"       value="${product.expenseExpr * product.us }" />
                        <c:set target="${product}" property="mfcCostExpr"       value="${product.mfcCostExpr * product.us }" />
                        <c:set target="${product}" property="manageCostExpr"    value="${product.manageCostExpr * product.us }" />
                        <c:set target="${product}" property="reduceCostExpr"    value="${product.reduceCostExpr * product.us }" />
                        <c:set target="${product}" property="totalCostExpr"     value="${product.totalCostExpr * product.us }" />
                </c:if>
	                    <tr data-id="${cp.oid }" data-parent="${cp.pOid }" data-level="${cp.bomLevel + 1 }" data-productoid="${productOid }">
	                        <td data-column="name" style="border-left:2px solid #000;" title="${cp.partName }">${cp.partName }</td>
	                        <td class="center"><span class="num nonFixed">${cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.materialCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.laborCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.expenseExpr * cp.us }</span></td>
	                        <td class="right"><span class="num bold">${cp.mfcCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.manageCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num">${cp.reduceCostExpr * cp.us }</span></td>
	                        <td class="right"><span class="num compare totalCostExpr">${cp.totalCostExpr * cp.us }</span></td>
	                        <td class="center" colspan="2">${cp.mftFactory2Display }<c:if test="${ not empty cp.company }"> / </c:if>${cp.company }</td>
	                        <td class="center" style="border-right:2px solid #000;"></td>
	                    </tr>
	                    <c:if test="${empty cp.masterReference }">
	                        <c:set target="${product}" property="materialCostExpr"  value="${product.materialCostExpr + (cp.materialCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="laborCostExpr"     value="${product.laborCostExpr + (cp.laborCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="expenseExpr"       value="${product.expenseExpr + (cp.expenseExpr * cp.us) }" />
	                        <c:set target="${product}" property="mfcCostExpr"       value="${product.mfcCostExpr + (cp.mfcCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="manageCostExpr"    value="${product.manageCostExpr + (cp.manageCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="reduceCostExpr"    value="${product.reduceCostExpr + (cp.reduceCostExpr * cp.us) }" />
	                        <c:set target="${product}" property="totalCostExpr"     value="${product.totalCostExpr + (cp.totalCostExpr * cp.us) }" />
	                    </c:if>
	                    </c:forEach>
	                    <tr>
	                        <th style="border-bottom:0;border-left:2px solid #000;" colspan="2" title="${product.partName }">${product.partName }</th>
	                        <td class="right"><span class="num">${product.materialCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.laborCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.expenseExpr }</span></td>
	                        <td class="right"><span class="num bold">${product.mfcCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.manageCostExpr }</span></td>
	                        <td class="right"><span class="num">${product.reduceCostExpr }</span></td>
	                        <td class="right"><span class="num compare">${product.totalCostExpr }</span></td>
	                        <c:choose>
                                <c:when test="${report.releaseStep ne '임시배포' }">
                                <td class="right"><span class="num bold">${product.salesTargetCostExpr }</span></td>
                                <td class="right"><span class="num compare">${product.profitRateExpr * 100}</span>%</td>
                                </c:when>
                                <c:otherwise>
                                <td class="right">-</td>
                                <td class="right">-</td> 
                                </c:otherwise>
                            </c:choose>
	                        
	                        <td class="center" style="border-right:2px solid #000;"><span class="num compare">100</span>%</td>
	                    </tr>
	                    <tr>
	                        <th style="border-bottom:0;border-left:2px solid #000;" colspan="2">(총원가 대비 구성비)</th>
	                        <td class="right">(<span class="num compare">${(product.materialCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
	                        <td class="right">(<span class="num compare">${(product.laborCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
	                        <td class="right">(<span class="num compare">${(product.expenseExpr / product.totalCostExpr) * 100 }</span>%)</td>
	                        <td class="right">(<span class="num compare">${(product.mfcCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
	                        <td class="right">(<span class="num compare">${(product.manageCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
	                        <td class="right">(<span class="num compare">${(product.reduceCostExpr / product.totalCostExpr) * 100 }</span>%)</td>
	                        <td class="right">(<span class="num compare">100</span>%)</td>
	                        <td colspan="3" style="border-right:2px solid #000;"></td>
	                    </tr>
	                    <tr>
	                        <td style="border:0;border-top:2px solid #000;height:10px;" colspan="12">
	                        <script>
	                            $("tr[data-productoid='${productOid}']").each(function(){
	                                var totalCostExpr = $(this).find(".totalCostExpr").text().commaRemove();
	                                var ptotalCostExpr = "${product.totalCostExpr}";
	                                
	                                var percent = calculateValue(totalCostExpr, ptotalCostExpr, "/");
	                                percent = calculateValue(percent, 100, "*");
	                                if(isNaN(percent)) percent = "0"; 
	                                $(this).find("td:last").html("<span class='num compare'>" + percent +"</span>%");
	                            });
	                        </script>
	                        </td>
	                    </tr>
                    </tbody>
                </table>
			</div>
        </div>
        <div id="COSTINVEST" class="tabContent" style="padding-top:50px;border:0;border-top:2px solid #82BAD1;border-radius:0;overflow:auto;">
            <div style="margin: 0 auto; width: 1500px;">
	            <div class="headerTitle">
	                <span style="font-size: 30px; font-weight: bold;">투자비 종합내역
	                <c:if test="${caseOrder eq 1 }">
						<c:if test="${not empty reportData.calcData[0].caseNote1}">(${reportData.calcData[0].caseNote1})</c:if>
					</c:if>
					<c:if test="${caseOrder eq 2 }">
						<c:if test="${not empty reportData.calcData[0].caseNote2}">(${reportData.calcData[0].caseNote2})</c:if>
					</c:if>
					<c:if test="${caseOrder eq 3 }">
						<c:if test="${not empty reportData.calcData[0].caseNote3}">(${reportData.calcData[0].caseNote3})</c:if>
					</c:if>
	                </span><br>
	                <div id="INVESTMENT_COST" style="width:1500px;margin-top:30px;"></div>
	            </div>
            </div>
        </div>
    </div>
</body>