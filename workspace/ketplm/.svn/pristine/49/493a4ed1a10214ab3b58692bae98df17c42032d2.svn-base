<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
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
.infoTable .edit{
	width:90% !important;
	height:20px !important;
	border:0 !important;
	margin:0;padding:0;
	padding:5px;
}
.pointer{
	cursor:pointer;
}
.calcAnalysis{
width:30px;height:20px;text-align:right;position:relative;top:-1px;
}
.appendCols{border-left:0 !important;}

.infoTD{padding:15px;vertical-align:top;}
.investDebugging{cursor:pointer;}
.activeTD{background:#EDEDED !important;}
.headerBtnDiv{float:right;z-index:1000;position:relative;top:-5px;}
.partInfoDiv{position:Relative;top:-7px;}
</style>
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<script>

var costGrid = null;
$(document).ready(function(){
	
	var editMode = "CALCULATE";
	if(${isBasePart}) editMode = "VIEW";
	
	//$("#BASIC_INFO").load("/plm/ext/cost/costBomEditor?taskOid=${taskOid}&oid=${oid}&subVersion=${part.subVersion}&EDITMODE=" + editMode, function(){$(this).ready(function(){
		/* $("#MATERIAL_COST").load("/plm/ext/cost/costCalculateGrid?taskOid=${taskOid}&COSTTYPE=MATERIAL_COST&oid=${oid}", function(){$(this).ready(function(){
			$("#LABOR_COST").load("/plm/ext/cost/costCalculateGrid?taskOid=${taskOid}&COSTTYPE=LABOR_COST&oid=${oid}", function(){$(this).ready(function(){
				$("#EXPENSE").load("/plm/ext/cost/costCalculateGrid?taskOid=${taskOid}&COSTTYPE=EXPENSE&oid=${oid}", function(){$(this).ready(function(){
					 $("#MAINTENENCE_COST").load("/plm/ext/cost/costCalculateGrid?taskOid=${taskOid}&COSTTYPE=MAINTENENCE_COST&oid=${oid}", function(){$(this).ready(function(){
						$("#CAPA").load("/plm/ext/cost/costCalculateGrid?taskOid=${taskOid}&COSTTYPE=CAPA&oid=${oid}", function(){
							$("#INVESTMENT_COST").load("/plm/ext/cost/costInvestGrid?taskOid=${taskOid}&oid=${oid}", function(){
								
							});
							
							
							
							
							setCostPartInfo();
							getCostCalcResult();
						});
					});});
				});});
			});});
		});}); */
	//});});
	
	window.setCostPartInfo = function(){
		
		var param = {
				oid : "${oid}",
		        taskOid : "${taskOid}",
		        partListOid : "${partListOid}",
		        DATATYPE : "COSTPART"
		}
		
		ajaxCallServer("/plm/ext/cost/getCostPartInfo", param, function(data){
			
			var exRateInfo = data.exRateInfo;
			var quantityInfo = data.quantityInfo;
			var partInfoList = data.partInfoList;
			
			$("#exRateInfo tbody").html("");
			$("#nMetalInfo tbody").html("");
			$("#profitInfo tbody").html("");
			
			for(var i = 0; i < exRateInfo.length; i++ ){
				
				var infoRow = exRateInfo[i];
				
				var row = "<tr>" +
							"<td class='center bgcol fontb' ><input type='hidden' name='id' value='' /> " + infoRow["name"] + "</td>" +
							"<td class='center' ><input type='text' name='value2' class='edit' value='' style='text-align:right;' /></td>" +
						  "</tr>";
						  
				$("#exRateInfo tbody").append(row);
				
				$("#exRateInfo input[name='id']:last").val(infoRow["id"]);
				$("#exRateInfo input[name='value2']:last").val(infoRow["value"].toFloat());
				$("#exRateInfo input[name='value2']:last").number( true , 2);
				if(${isBasePart}){
	                $("#exRateInfo input[name='value2']:last").attr("disabled", true);
				}
			}

			$("#payPlace").text(quantityInfo["payPlace"]);
			$("#deliverName").text(quantityInfo["deliverName"]);
			$("#sopYear").text(quantityInfo["sopYear"]);
			$("#quantityTotal").text(quantityInfo["quantityTotal"].commaFormat());
			$("#quantityAvg").text(quantityInfo["quantityAvg"].commaFormat());
			$("#quantityMax").text(quantityInfo["quantityMax"].commaFormat());
			
			var nMetalInfoHtml = "";
			var profitInfoHtml = "";
			var investInfoHtml = ""; 
			var metalScenario = "";

			for(var i = 0; i < partInfoList.length; i++){
				
				var partInfo = partInfoList[i];
				var profitInfo = partInfo.profitInfo;
				var investInfo = partInfo.investInfo;
				var oid = partInfo.oid;
				
				var partNo = partInfo.realPartNo;
				
				if(partNo == null || partNo.trim() == "") partNo = partInfo.partNo;
				
				var nMetalName = checkNull(partInfo.nMetalName);
				var platingName = checkNull(partInfo.platingName);
				/*var metalLmeStd = checkNull(partInfo.metalLmeStd).commaFormat();
				var mtrMetalPrice = checkNull(partInfo.mtrMetalPrice).commaFormat();
				var metalScrapRecycle = checkNull(partInfo.metalScrapRecycle).commaFormat();
				var metalScrapCost = checkNull(partInfo.metalScrapCost).commaFormat(); */
				metalScenario = partInfo.metalScenario;
				
				for(var j = 0; j < profitInfo.length; j++){
					
					var info = profitInfo[j];
					
					var partName = checkNull(info.partName);
					var grade = checkNull(info.grade);
					var color = checkNull(info.color);
					var materialPrice = checkNull(info.materialPrice).commaFormat();
					//var scrapCost = checkNull(info.scrapCost).commaFormat();
					//var scrapRecycle = checkNull(info.scrapRecycle).commaFormat();
					
					var profitInfoRow = "<tr>";
                    if(j == 0){
                    	profitInfoRow += "<td class='center' rowspan='" + profitInfo.length + "' >" + partNo + "</td>";
                    }
					
                    profitInfoRow += "<td class='center' ><input type='hidden' name='id' value='" + oid + "' />" + partName + "</td>" +
						"<td class='center' >" + grade + "</td>" +
						"<td class='center' >" + color + "</td>" +
						"<td class='right' >" + materialPrice + "</td>" +
						//"<td class='right' >" + scrapCost + "</td>" +
						"<td class='right' ><input type='text' name='sujiScrapCost' class='edit' style='text-align:right;' value='"+info.scrapCost+"' /></td>" +
						//"<td class='right' >" + scrapRecycle + "</td>" +
						"<td class='right' ><input type='text' name='sujiScrapRecycle' class='edit' style='text-align:right;' value='"+info.scrapRecycle+"' /></td>" +
					  "</tr>";
					
					$("#profitInfo tbody").append(profitInfoRow);
					
					$("#profitInfo input[name='sujiScrapCost']:last").number( true);
					$("#profitInfo input[name='sujiScrapRecycle']:last").number( true);
				}
				
				for(var j = 0; j < investInfo.length; j++){
					
					var info = investInfo[j];
					var infoOid = info.oid;
					var investNote = checkNull(info.investNote);
					var workUseHour = checkNull(info.workUseHour).commaFormat();
					var workUseDay = checkNull(info.workUseDay).commaFormat();
					var workUseYear = checkNull(info.workUseYear).commaFormat();
					var investReduceCost = calculateValue(info.investReduceCost, 1000, "/").commaFormat();
					var machineReduceCost = checkNull(info.machineReduceCost).commaFormat();
					var facReduceCtSpm = checkNull(info.facReduceCtSpm);
					var facReduceOutputExpr = checkNull(info.facReduceOutputExpr).commaFormat(2);
					var machineDpcCostExpr = checkNull(info.machineDpcCostExpr).commaFormat(2);
					
					investInfoHtml += "<tr>";
					if(j == 0){
						investInfoHtml += "<td class='center' rowspan='" + investInfo.length + "' >" + partNo + "</td>";
					}
					    
					investInfoHtml += "<td class='center' ><input type='hidden' name='partOid' value='" + oid + "' />" +
											"<input type='hidden' name='id' value='" + infoOid + "' />" + investNote + "</td>" +
						"<td class='center' ><input type='text' name='workUseHour' class='edit' style='text-align:right;' value='" + workUseHour + "' /></td>" +
						"<td class='center' ><input type='text' name='workUseDay' class='edit' style='text-align:right;' value='" + workUseDay + "' /></td>" +
						"<td class='center' ><input type='text' name='workUseYear' class='edit' style='text-align:right;' value='" + workUseYear + "' /></td>" +
						"<td class='right' >" + investReduceCost + "</td>" +
						"<td class='right' >" + machineReduceCost + "</td>" +
						"<td class='center' ><input type='text' name='facReduceCtSpm' class='edit' style='text-align:right;' value='" + facReduceCtSpm + "' /></td>" +
						"<td class='right investDebugging' data-attr='facReduceOutputExpr' data-oid='" + oid + "' data-frcs='" + facReduceCtSpm + "' data-mrc='" + machineReduceCost + "'>" + facReduceOutputExpr + "</td>" +
						"<td class='right investDebugging' data-attr='machineDpcCostExpr' data-oid='" + oid + "' data-frcs='" + facReduceCtSpm + "' data-mrc='" + machineReduceCost + "'>" + machineDpcCostExpr + "</td>";
					investInfoHtml += "</tr>";
				}
				
				if("" == nMetalName) continue;
				
			    var nMetalInfoRow = "<tr>" +
			                        "<td class='center' >" + partNo + "</td>" +
									"<td class='center' ><input type='hidden' name='id' value='" + oid + "' />" + nMetalName + "</td>" +
									"<td class='center' >" + platingName + "</td>" +
									//"<td class='center' >" + metalLmeStd + "</td>" +
									"<td class='center' ><input type='text' name='metalLmeStd' class='edit' style='text-align:right;' value='' /></td>" +
									//"<td class='right' >" + mtrMetalPrice + "</td>" +
									"<td class='right' ><input type='text' name='mtrMetalPrice' class='edit' style='text-align:right;' value=''></td>" +
									//"<td class='right' >" + metalScrapCost + "</td>" +
									"<td class='right' ><input type='text' name='metalScrapCost' class='edit' style='text-align:right;' value='' /></td>" +
									//"<td class='right' >" + metalScrapRecycle + "</td>" +
									"<td class='right' ><input type='text' name='metalScrapRecycle' class='edit' style='text-align:right;' value='' /></td>" +
								    "</tr>";
			    
				$("#nMetalInfo tbody").append(nMetalInfoRow);
				
				$("#nMetalInfo input[name='metalLmeStd']:last").val(partInfo.metalLmeStd);
				$("#nMetalInfo input[name='metalLmeStd']:last").number( true);
				
				$("#nMetalInfo input[name='mtrMetalPrice']:last").val(partInfo.mtrMetalPrice);
				$("#nMetalInfo input[name='mtrMetalPrice']:last").number( true);
				
				$("#nMetalInfo input[name='metalScrapCost']:last").val(partInfo.metalScrapCost);
				$("#nMetalInfo input[name='metalScrapCost']:last").number( true);
				
				$("#nMetalInfo input[name='metalScrapRecycle']:last").val(partInfo.metalScrapRecycle);
				$("#nMetalInfo input[name='metalScrapRecycle']:last").number( true);
			}
			
			
			$("select[name='metalScenario']").val(metalScenario);
			//$("#nMetalInfo tbody").html(nMetalInfoHtml);
			//$("#profitInfo tbody").html(profitInfoHtml);
			$("#investInfo tbody").html(investInfoHtml).ready(function(){
				
				$("input[name='facReduceCtSpm']").number(true, 1);
				
                $('.investDebugging').click(function(e){
                    $('.investDebugging').removeClass('activeTD');
                    $(this).addClass('activeTD');
                    
                    var param = {
                            oid : $(this).data("oid"),
                            code : $(this).data("attr"),
                            directAttr : {
                                facReduceCtSpm : $(this).data("frcs"),
                                machineReduceCost : $(this).data("mrc")
                            }
                    }
                    
                    var data = ajaxCallServer("/plm/ext/cost/getAttrCaluateFormula", param, null, false);
                    
                    if(data.keyFormula != null){
                        var resultHTML = checkNull(data.keyFormula) + "<br/>" 
                         + "실계산식 : " + checkNull(data.resultFormula);
                        $(".investInfoResFormula").html(resultHTML);
                    }else{
                        $(".investInfoResFormula").html("");
                    }
                });
			
            });
			
			if(${isBasePart}){
				$("#nMetalInfo input").attr("disabled", true);
				$("#investInfo input").attr("disabled", true);
				$("select[name='metalScenario']").attr("disabled", true);
			}
		});
		
	}
	
	var tab = CommonUtil.tabs('INFO_TAB');
	
	$("#INFO_TAB .tabref").click(function(){
		$("#INFO_TAB .tabContent").hide();
		var ref = $(this).attr("href");
		$(ref).show();
	});
	
	var tab = CommonUtil.tabs('COST_TAB');
	$("#COST_TAB .tabContent:first").show();
	
	$("#COST_TAB .tabref").click(function(){
		
		$("#COST_TAB .tabContent").hide();
		var ref = $(this).attr("href");
		$(ref).show();
		
		var src = $(ref +"F").attr("src");
		
		if(src == null || src == undefined || src == "undefined"){
			var type = ref.substring(1);
			$(ref +"F").attr("src", "/plm/ext/cost/costCalculateGrid?taskOid=${taskOid}&oid=${oid}&COSTTYPE=" + type);
		}
	});
	
	window.initializePage = function(){
		
		$(".tabFrame").each(function(){
			if(typeof($(this)[0].contentWindow.gridReload) == "function") {
				$(this)[0].contentWindow.gridReload();
			}
		});
		
		setCostPartInfo();
		getCostCalcResult();
	}
	
	window.getCostCalcResult = function(){
		
		var param = {
				taskOid : "${taskOid}",
		        partListOid : "${partListOid}",
		        oid : "${oid}",
		        DATATYPE : "COSTPART"
		}

		ajaxCallServer("/plm/ext/cost/getCostCalcResult", param, function(data){
			
			var dataList = data.dataList;
			var caList = data.caList;
			
			$("#costCalcResult tbody tr").remove();
			
			for( var i = 0; i < dataList.length; i++ ){
				
				var part = dataList[i];
				
				var partName = checkNull(part.partName);
				var materialCostExpr = checkNull(part.materialCostExpr).commaFormat(2);
				var laborCostExpr = checkNull(part.laborCostExpr).commaFormat(2);
				var expenseExpr = checkNull(part.expenseExpr).commaFormat(2);
				var manageCostExpr = checkNull(part.manageCostExpr).commaFormat(2);
				var mfcCostExpr = checkNull(part.mfcCostExpr).commaFormat(2);
				var reduceCostExpr = checkNull(part.reduceCostExpr).commaFormat(2);
				var totalCostExpr = checkNull(part.totalCostExpr).commaFormat(2);
				var salesTargetCostExpr = checkNull(part.salesTargetCostExpr).commaFormat(2);
				var profitRateExpr = calculateValue(data["profitRateExpr"], 100, "*").commaFormat(2);
				var tr = "<tr>" +
							"<td class='left'>" + partName + "</td>" +
							"<td class='right' >" + materialCostExpr + "</td>" +
							"<td class='right' >" + laborCostExpr + "</td>" +
							"<td class='right' >" + expenseExpr + "</td>" +
							"<td class='right' >" + mfcCostExpr + "</td>" +
							"<td class='right' >" + manageCostExpr + "</td>" +
							"<td class='right' >" + reduceCostExpr + "</td>" +
							"<td class='right' >" + totalCostExpr + "</td>";
				if(i == 0){
					tr += "<td class='center' rowspan='" + (dataList.length + 1) +"'>" + salesTargetCostExpr + "</td>" +
					"<td class='center' rowspan='" + (dataList.length + 1) +"'>" + profitRateExpr + "%</td>";
					
					$("input[name='salesTargetCostTotal']").val(salesTargetCostExpr);
				}
				
				$("#costCalcResult tbody").append(tr);
				
			}

			var materialCostTotal = data["materialCostTotal"].commaFormat(2);
			var laborCostTotal = data["laborCostTotal"].commaFormat(2);
			var expenseTotal = data["expenseTotal"].commaFormat(2);
			var mfcCostTotal = data["mfcCostTotal"].commaFormat(2);
			var manageCostTotal = data["manageCostTotal"].commaFormat(2);
			var reduceCostTotal = data["reduceCostTotal"].commaFormat(2);
			var productCostTotal = data["productCostTotal"].commaFormat(2);
			var tr = "<tr>" +
						"<td class='center bgcol fontb' >합계</td>" +
						"<td class='right' >" + materialCostTotal + "</td>" +
						"<td class='right' >" + laborCostTotal + "</td>" +
						"<td class='right' >" + expenseTotal + "</td>" +
						"<td class='right' >" + mfcCostTotal + "</td>" +
						"<td class='right' >" + manageCostTotal + "</td>" +
						"<td class='right reduceCostTotal' >" + reduceCostTotal + 
						"<input type='hidden' name='reduceCostTotal'/></td>" +
						"<td class='right' >" + productCostTotal + "</td>" +
					 "</tr>";

			$("#costCalcResult tbody").append(tr);
			
			$("input[name='reduceCostTotal']").val(data["reduceCostTotal"]);
			
			setCostAnalysis(caList, true);
			
			getCaseList();
		});
	}
	
	window.setCostAnalysis = function(caList, isInit){
		$("#salesProfit .appendCols").remove();
		
		var tableWidth			 = 240;
		var salesTargetCostTotal = 0;
		var productCostTotal	 = 0;
		var quantity			 = 0;
		var totalSales			 = 0;
		var profitCost			 = 0;
		var cashInFlow			 = 0;
		var profitRateTotal		 = 0;
		
		var cr					 = 0;
		var applyYear			 = 0;
		var appointTotal		 = 0;
		var appointSales		 = 0;
		var correctPeriod		 = 0;
		
		for(var i = 0; i < caList.length; i++){
			
			var ca = caList[i];
			
			var td1 = $("<td class='appendCols center' style='width:120px;'></td>");
			var td2 = $("<td class='appendCols right'></td>");
			var td3 = $("<td class='appendCols right'></td>");
			var td4 = $("<td class='appendCols right'></td>");
			var td5 = $("<td class='appendCols right'></td>");
			var td6 = $("<td class='appendCols right'></td>");
			var td7 = $("<td class='appendCols right'></td>");
			var td8 = $("<td class='appendCols right'></td>");
			
			$(td1).html(ca.year + "년차<input type='hidden' name='caOid' />");
			
			$("#salesProfit thead td:eq(" + i + ")").after(td1);
			$(td2).html("<span name='salesTargetCostTotal'></span>");
			$("#salesProfit tbody tr:eq(0) td:eq(" + i + ")").after(td2);
			$(td3).html("<span name='productCostTotal'></span>");
			$("#salesProfit tbody tr:eq(1) td:eq(" + i + ")").after(td3);
			$(td4).html("<span name='quantity'></span>");
			$("#salesProfit tbody tr:eq(2) td:eq(" + i + ")").after(td4);
			$(td5).html("<span name='totalSales'></span>");
			$("#salesProfit tbody tr:eq(3) td:eq(" + i + ")").after(td5);
			$(td6).html("<span name='profitCost'></span>");
			$("#salesProfit tbody tr:eq(4) td:eq(" + i + ")").after(td6);
			$(td7).html("<span name='cashInFlow'></span>");
			$("#salesProfit tbody tr:eq(5) td:eq(" + i + ")").after(td7);
			$(td8).html("<span name='profitRateTotal'></span>");
			$("#salesProfit tbody tr:eq(6) td:eq(" + i + ")").after(td8);
			
			$("#salesProfit input[name='caOid']:last").val(ca.id);
			
			var tempSalesTarget = ca.salesTargetCostTotal.commaFormat(2);
			var tempProduct = ca.productCostTotal.commaFormat(2);
			var tempQty = ca.quantity;
			var tempTotalSales = ca.totalSales;
			var tempProfitCost = ca.profitCost;
			var tempCashInFlow = ca.cashInFlow;
			var tempProfitRate = ca.profitRateTotal;
			
			tempQty = calculateValue(tempQty, 1000, "/").commaFormat(2);
			tempTotalSales = calculateValue(tempTotalSales, 1000000, "/").commaFormat(2);
			tempProfitCost = calculateValue(tempProfitCost, 1000000, "/").commaFormat(2);
			tempCashInFlow = calculateValue(tempCashInFlow, 1000000, "/").commaFormat(2);
			tempProfitRate = calculateValue(tempProfitRate, 100, "*").commaFormat(2);
			
			$("#salesProfit tbody span[name='salesTargetCostTotal']:last").html(tempSalesTarget);
			$("#salesProfit tbody span[name='productCostTotal']:last").html(tempProduct);
			$("#salesProfit tbody span[name='quantity']:last").html(tempQty);
			$("#salesProfit tbody span[name='totalSales']:last").html(tempTotalSales);
			$("#salesProfit tbody span[name='profitCost']:last").html(tempProfitCost);
			$("#salesProfit tbody span[name='cashInFlow']:last").html(tempCashInFlow);
			$("#salesProfit tbody span[name='profitRateTotal']:last").html(tempProfitRate + "%");
			
			salesTargetCostTotal	= calculateValue(salesTargetCostTotal, ca.salesTargetCostTotal, "+");
			productCostTotal		= calculateValue(productCostTotal, ca.productCostTotal, "+");
			quantity				= calculateValue(quantity, ca.quantity, "+");
			totalSales				= calculateValue(totalSales, ca.totalSales, "+");
			profitCost				= calculateValue(profitCost, ca.profitCost, "+");
			cashInFlow				= calculateValue(cashInFlow, ca.cashInFlow, "+");
			tableWidth += 120;
			
			cr = ca.cr;
			applyYear = ca.applyYear;
			appointSales = ca.appointSales;
			appointTotal = ca.appointTotal;
			correctPeriod = ca.correctPeriod;
			
		}
		
		if(isInit){
			$("input[name='appointSales']").val(appointSales);
			$("#appointTotal").text(appointTotal.toFloat().commaFormat());
			$("input[name='cr']").val(cr);
			$("input[name='applyYear']").val(applyYear);
		}
		
		if(${isBasePart}){
			$("input[name='appointSales']").attr("disabled", true);
            $("input[name='cr']").attr("disabled", true);
            $("input[name='applyYear']").attr("disabled", true);
        }
		
		$("span[name='correctPeriod']").text(correctPeriod.toFloat(1));
		
		quantity = calculateValue(quantity, 1000, "/");
		totalSales = calculateValue(totalSales, 1000000, "/");
		profitCost = calculateValue(profitCost, 1000000, "/");
		cashInFlow = calculateValue(cashInFlow, 1000000, "/");
		profitRateTotal         = calculateValue(profitCost, totalSales, "/");
		profitRateTotal         = calculateValue(profitRateTotal, 100, "*");
		
		$("#salesProfitTotal tbody .total:eq(0)").html("-");
		$("#salesProfitTotal tbody .total:eq(1)").html(productCostTotal.commaFormat(2));
		$("#salesProfitTotal tbody .total:eq(2)").html(quantity.commaFormat(2));
		$("#salesProfitTotal tbody .total:eq(3)").html(totalSales.commaFormat(2));
		$("#salesProfitTotal tbody .total:eq(4)").html(profitCost.commaFormat(2));
		$("#salesProfitTotal tbody .total:eq(5)").html(cashInFlow.commaFormat(2));
		$("#salesProfitTotal tbody .total:eq(6)").html(profitRateTotal.toFloat(1) + "%");
		
		if(tableWidth < 660){
			tableWidth = 660;
		}
		$("#salesProfit").width(tableWidth);
	}
	
	$(".calcAnalysis").keyup(function(){
		saveCostAnalysisInfo(false);
	});
	
	window.saveCostAnalysisInfo = function(isSave){
		
		var caOidList = [];
		$("input[name='caOid']").each(function(){
			caOidList.push($(this).val());
		});
		
		
	    var param = {
	    		caOidList       : caOidList,
	            oid             : "${oid}",
	            appointSales    : $("input[name='appointSales']").val(),
	            cr              : $("input[name='cr']").val(),
	            applyYear       : $("input[name='applyYear']").val(),
	            reduceCostTotal : $("input[name='reduceCostTotal']").val(),
	            isSave          : isSave
        };
		
		ajaxCallServer("/plm/ext/cost/saveCostAnalysisInfo", param, function(data){
			setCostAnalysis(data.caList);
		}, false);
	}
	
	window.syncPart = function(){
		if(!confirm("비철/수지 및 그 밖의 기준정보 항목에 대한 동기화가 진행됩니다.\r\n변경된 값으로 재계산은 진행되지 않으며,재계산 필요시\r\n재산출 버튼을 클릭해야합니다.\r\n진행하시겠습니까?")){
			return;
		}
		ajaxCallServer("/plm/ext/cost/code/syncPartByCodeMaster?partListOid=${partListOid}&version=${part.version}&subVersion=${part.subVersion}&partOid=${oid}", {}, function(data){
			initializePage();
		});
	}
	
	window.costPartInfoSave = function(infoType){
		
		var msg = "";
		
		if(infoType == 'exRateInfo'){
			msg = "환율 변경시 다음 작업이 자동진행됩니다.\r\n\r\n(1)비철원재료 동기화 \r\n\r\n(2)재산출";
		}else{
			msg = "변경 후 재산출 작업이 자동진행됩니다.";
		}
		
		alert(msg);
		
		var param = {
				oid : "${oid}",
		        infoType : infoType,
		        partListOid : "${partListOid}"
		}
		
		if("exRateInfo" == infoType || "investInfo" == infoType || "nMetalInfoByUserInput" == infoType || "profitInfo" == infoType){
			
			var listInfo = new Array();
			
			if("nMetalInfoByUserInput" == infoType){
				infoType = "nMetalInfo";
			}
			
			$("#" + infoType + " tbody tr").each(function(){
				
				var row = {};
				
				$(this).find("input").each(function(){
					
					var name = $(this).attr("name");
					var value = $(this).val();
					row[name] = value;
				});
				
				listInfo.push(row);
			});
			
			param.data = listInfo;
		
		}else if("nMetalInfo" == infoType){
			param.metalScenario = $("select[name='metalScenario']").val();
		}
		
		ajaxCallServer("/plm/ext/cost/saveCostPartInfo", param, function(data){
			initializePage();
		});
	}
	
	window.getCaseList = function(){
		var param = {
			oid : "${oid}",
			taskOid : "${taskOid}"
        }
		ajaxCallServer("/plm/ext/cost/getCaseList", param, function(data){
			
			var caseList = data.caseList;
			var caseListHtml = "";
			
			$("#caseList tbody").html("");
			for(var i = 0; i < caseList.length; i++){
				
				var casePart = caseList[i];
				
				var tr = "<tr><td class='center'>";
				if(i == 0){
					tr += "<input type='hidden' name='caseOid' value='" + casePart.id + "' /></td>";
				}else{
					tr += "<input type='hidden' name='caseOid' value='" + casePart.id + "' /><input type='checkbox' name='caseSelect' value='" + casePart.id + "' /></td>";
				}
	            if("${oid}" == casePart.id){
	            	tr += "<td class='center'><b><a href='javascript:location.href=\"/plm/ext/cost/costPartCalculatePopup?taskOid=${taskOid}&oid=" + casePart.id + "\"' >" +
                    casePart.partName + "</a></b></td>";
	            }else{
	            	tr += "<td class='center'><a href='javascript:location.href=\"/plm/ext/cost/costPartCalculatePopup?taskOid=${taskOid}&oid=" + casePart.id + "\"' >" +
                    casePart.partName + "</a></td>";
	            }
	            
	            var caseStyle = "";
	            	
	            if(0 == casePart.subVersion){
	            	caseStyle = "style='background-color:#ababab' onFocus='this.initialSelect = this.selectedIndex;' onChange='this.selectedIndex = this.initialSelect;'";
	            }
	            
                tr += "<td class='right' >" + casePart.salesTargetCostExpr.commaFormat(2) + "</td>" +
                      "<td class='right' >" + casePart.productCostTotal.commaFormat(2) + "</td>" +
	                  "<td class='right' >" + (casePart.profitRateExpr * 100).commaFormat(2) + "%</td>" +
	                  "<td class='center' >" + casePart.version + "." + casePart.subVersion + "</td>" +
	                  "<td class='center'><select name='caseOrder'"+caseStyle+"><option value=''></option><option value='1'>1案</option>" +"<option value='2'>2案</option><option value='3'>3案</option></select></td>" +
	                  "<td class='center' ><input type='text' class='edit' name='caseNote' value='" + checkNull(casePart.caseNote) + "' /></td></tr>";
				$("#caseList tbody").append(tr);
                $("#caseList tbody select[name='caseOrder']:last").val(casePart.caseOrder);
			}
        });
	}
	
	//############## CASE 생성 #############################
	window.createCase = function(){
		
		if($("input:checkbox[name='caseSelect']:checked").length > 1){
			alert("CASE 생성은 하나의 선택 CASE만 복사할 수 있습니다.");
			return;
		}
		
		var oid = $("input:checkbox[name='caseSelect']:checked").val();
		if(oid == null) oid = $("input[name=caseOid]:first").val();
		
		if(!confirm("CASE를 생성하시겠습니까?")) return;
		
		var param = {
			    oid : oid,
			    subVersion : $("input[name=caseOid]").length + ""
		}
		
		ajaxCallServer("/plm/ext/cost/createCasePart", param, function(data){
			getCaseList();
		});
	}
	//############## CASE 삭제 #############################
	window.deleteCase = function(){
		
		var basePartOid = $("input[name='caseOid']:first").val();
		
		var isdelete = false;
		
		var caseList = [basePartOid];
		var deleteList = [];
		
		$("input:checkbox[name='caseSelect']:checked").each(function(){
			deleteList.push($(this).val());
			if(!isdelete) isdelete = "${oid}" == $(this).val();
		});
		
		$("input:checkbox[name='caseSelect']:not(:checked)").each(function(){
			caseList.push($(this).val());
        });
		
		if(!confirm("CASE를 삭제하시겠습니까?")) return;
		
		var param = {
				deleteList : deleteList,
				caseList : caseList
        }
        
        ajaxCallServer("/plm/ext/cost/deleteCasePart", param, function(data){
        	if(isdelete){
        		location.href="/plm/ext/cost/costPartCalculatePopup.do?taskOid=${taskOid}&oid=" + basePartOid;
        	}else{
        		getCaseList();
        	}
        });
	}
	
	//############## 산출안 저장 #############################
	window.saveCase = function(){
		
		var basePartOid = "";
		var isSave = true;
		var caseOrderList = [];
		var saveList = [];
		
		$("#caseList tbody tr").each(function(idx){
			
			var caseOid = $(this).find("input[name='caseOid']").val();
			var caseNote = $(this).find("input[name='caseNote']").val();
			var caseOrder = $(this).find("select[name='caseOrder']").val();
			var lastest = $(this).find("input[name='lastest']").is(":checked");
			
			if("" != caseOrder){
				if(caseOrder == "1" || caseOrder == "2" || caseOrder == "3"){
					
					if(caseOrderList.contains(caseOrder)){
						alert("중복되는 CASE 순서가 있습니다.");
						isSave = false;
						return false;
					}else{
						caseOrderList.push(caseOrder);
					}
					
				}else{
					alert("잘못 입력된 CASE 순서가 있습니다.");
					isSave = false;
					return false;
				}
			}
			
			var data = {
					caseOid : caseOid,
	                caseNote : caseNote,
	                caseOrder : caseOrder,
	                lastest : lastest + ""
			};
			
			saveList.push(data);
		});
		var param = { saveList : saveList };
		
		if(isSave){
			ajaxCallServer("/plm/ext/cost/saveCasePart", param, function(data){
				getCaseList();
			});
		}
	}
	
	window.reCalculateCostPart = function(){
		
		if(!confirm("현재 설정된 값으로만 재계산이 진행되며\r\n기준정보 동기화 작업은 진행되지 않습니다.\r\n진행하시겠습니까?")){
			return;
		}
		
		param = {
			oid : "${oid}"
		};

		ajaxCallServer("/plm/ext/cost/reCalculateCostPart", param, function(data){
			initializePage();
		});
	}
	
	$(".commaFormat").each(function(){
		var caseSalesTargetCost = $(this).text();
		$(this).text(caseSalesTargetCost.commaFormat(2));
	})
	
    window.openBomEditor = function(mode){
        getOpenWindow2("/plm/ext/cost/costBomEditor?isPopup=true&taskOid=${taskOid}&EDITMODE=TREEEDIT&version=${part.version}&subVersion=${part.subVersion}&oid=${oid}","COSTBOMEDITOR", "1280", "720");
    }
    
    window.changeFormulaVersion = function(){
    	
    	var formulaVersion = $("#formulaVersion").val();
    	var param = {
    		oid : "${oid}",
    		formulaVersion : formulaVersion
    	}
    	
    	ajaxCallServer("/plm/ext/cost/changeFormulaVersion", param, function(data){
            initializePage();
            
            if("${version}" == formulaVersion){
                $("#versionAlert").hide();
            }else{
                $("#versionAlert").show();
            }
        });
    }
    
    if("${version}" == "${part.formulaVersion}"){
        $("#versionAlert").hide();
    }else{
        $("#versionAlert").show();
    }
    
    $("#formulaVersion").val("${part.formulaVersion}");
    
    if(${isBasePart}){
    	$(".headerBtnDiv").hide();
    	$(".partInfoDiv").css("top", 0);
    }
    
    $("#dataDiv").height($(window).height() - 150);
    $(window).resize(function(){
    	$("#dataDiv").height($(window).height() - 150);
    });
    
    
    window.iframeCalcHeight = function(id, height){//iframe resize (기본bomeditor의 크기에 맞춘다)
    	if(id == "BOMEDITOR"){
    		$("#COSTBOMBASIC").height(height);
    		$("#COSTBOMBASIC").css("overflow", "hidden");
    	}else{
    		$("#" + id + " .tabFrame").height(height);
    		$("#" + id + " .tabFrame").css("overflow", "hidden");
    	}
    }
    
    window.openCopyCase = function(){
		getOpenWindow2("/plm/ext/cost/copyCasePartPopup?taskOid=${taskOid}&oid=${oid}","COPYCASEPART", "1280", "720");
    }
    
    setCostPartInfo();
	getCostCalcResult();
});
</script>
<body class="popup-background">
	<input type="hidden" name="oid" value="${oid }" />
	<div class="contents-wrap" style="margin-top:20px;">
		<div class="sub-title b-space5">
			<img src="/plm/portal/images/icon_3.png" />원가산출화면 
			<span style="font-size:14px;color:#FF0000;font-weight:bold;">    원가산출 버전 : ${part.version}.${part.subVersion}<c:if test="${part.caseOrder != null}">  (${part.caseOrder}案)</c:if></span>
		</div>
		<div class="b-space5 float-r" style="text-align: right">
            <span id="versionAlert" style="color:#FF0000;font-weight:bold;">※ 제품에 사용된 수식이 최신버전이 아닙니다.</span>
            <c:if test="${isBasePart}">수식버전 : ${part.formulaVersion}</c:if>
            <c:if test="${!isBasePart}">
            수식버전 : 
	        <select name="formulaVersion" id="formulaVersion">
            <c:forEach begin="0" end="${version }" varStatus="stat" var="i">
              <c:set var="idx" value="${stat.end - stat.index}" scope="page"></c:set>
              <option value="${idx }">${idx }</option>
            </c:forEach>
            </select>
            
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"> <a href="javascript:changeFormulaVersion();" class="btn_blue">버전 변경</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            
		    <c:if test="${!isTaskComplete}">
			    <span class="in-block v-middle r-space7">
	                <span class="pro-table">
	                    <span class="pro-cell b-left"></span>
	                    <span class="pro-cell b-center"> <a href="javascript:openBomEditor();" class="btn_blue">BOM 구조 편집</a></span>
	                    <span class="pro-cell b-right"></span>
	                </span>
	            </span>
            </c:if>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:syncPart();" class="btn_blue">기준정보 동기화</a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:reCalculateCostPart();" class="btn_blue">재산출</a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
			</c:if>
			<span class="in-block v-middle r-space7">
				<span class="pro-table">
					<span class="pro-cell b-left"></span>
					<span class="pro-cell b-center"> <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span>
					<span class="pro-cell b-right"></span>
				</span>
			</span>
		</div>
		<div id="dataDiv" style="width:100%;overflow:auto;">
			<table>
	            <tr>
	                <td style="width:250px;" class="infoTD">
	                    <div class="sub-title-02 float-l b-space5" style="width: 100%;">
							<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>환율정보
							<div class="headerBtnDiv">
	                            <span class="in-block v-middle r-space7" >
	                                <span class="pro-table">
		                                <span class="pro-cell b-left"></span>
		                                <span class="pro-cell b-center"> <a href="javascript:costPartInfoSave('exRateInfo')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491")%><!-- 변경 --></a></span>
		                                <span class="pro-cell b-right"></span>
	                                </span>
	                            </span>
							</div>
						</div>
						<div class="b-space5 partInfoDiv">
							<table id="exRateInfo" summary="" class="table-style-01 infoTable">
								<colgroup>
									<col width="40%" />
									<col width="60%" />
								</colgroup>
								<tbody>
								</tbody>
							</table>
						</div>
					</td>
					<td style="width:250px;" class="infoTD">
					    <div class="sub-title-02 float-l b-space5" style="width:100%;height:25px;">
	                        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>물량정보(EA)
	                    </div>
	                    <div class="b-space5" style="position:relative;top:-7px;">
	                        <table id="quantityInfo" summary="" class="table-style-01 infoTable">
	                            <colgroup>
	                                <col width="40%" />
	                                <col width="60%" />
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <td class="center bgcol fontb" >Total 수량</td>
	                                    <td class="right"><span id="quantityTotal"></span></td>
	                                </tr>
	                                <tr>
	                                    <td class="center bgcol fontb" >Avg 수량</td>
	                                    <td class="right"><span id="quantityAvg"></span></td>
	                                </tr>
	                                <tr>
	                                    <td class="center bgcol fontb" >Max 수량</td>
	                                    <td class="right"><span id="quantityMax"></span></td>
	                                </tr>
	                                <tr>
	                                    <td class="center bgcol fontb" >SOP</td>
	                                    <td class="right"><span id="sopYear"></span></td>
	                                </tr>
	                                <tr>
	                                    <td class="center bgcol fontb" >완제품입고</td>
	                                    <td><span id="payPlace"></span></td>
	                                </tr>
	                                <tr>
                                        <td class="center bgcol fontb" >납입처</td>
                                        <td><span id="deliverName"></span></td>
                                    </tr>
	                                <!-- <tr>
                                        <td class="center bgcol fontb" >납입처</td>
                                        <td><span id="payPlace"></span></td>
                                    </tr> -->
	                            </tbody>
	                        </table>
	                    </div>
					</td>
					<td class="infoTD">
					   <div class="sub-title-02 float-l b-space5" style="width:100%;">
                            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>범용감가 / 기계감가 (표준)
                            <div class="headerBtnDiv">
                                <span class="in-block v-middle r-space7">
                                    <span class="pro-table">
                                        <span class="pro-cell b-left"></span>
                                        <span class="pro-cell b-center"> <a href="javascript:costPartInfoSave('investInfo')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491")%><!-- 변경 --></a></span>
                                        <span class="pro-cell b-right"></span>
                                    </span>
                                </span>
                            </div>
                        </div>
                        <div class="b-space5 partInfoDiv">
                            <table id="investInfo" summary="" class="table-style-01 infoTable">
                                <colgroup>
                                    <col width="*" />
                                    <col width="*" />
                                    <col width="60" />
                                    <col width="60" />
                                    <col width="60" />
                                    <col width="*" />
                                    <col width="*" />
                                    <col width="*" />
                                    <col width="*" />
                                </colgroup>
                                <thead>
                                    <tr style="height:20px;">
                                        <td class="center bgcol fontb" rowspan="2">부품번호</td>
                                        <td class="center bgcol fontb" rowspan="2">항목</td>
                                        <td class="center bgcol fontb" colspan="3">조업도</td>
                                        <td class="center bgcol fontb" rowspan="2">투자비<br/>(천원)</td>
                                        <td class="center bgcol fontb" rowspan="2">금액<br/>(원/Hr)</td>
                                        <td class="center bgcol fontb" rowspan="2">범용감가<br/>CT(SPM)</td>
                                        <td class="center bgcol fontb" rowspan="2">범용감가<br/>생산량</td>
                                        <td class="center bgcol fontb" rowspan="2">기계감가</td>
                                    </tr>
                                    <tr style="height:20px;">
                                        <td class="center bgcol fontb">Hr/日</td>
                                        <td class="center bgcol fontb">日/年</td>
                                        <td class="center bgcol fontb">적용년수</td>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                        <span class="investInfoResFormula" style="font-weight:bold;font-size:14px;height:50px;color:#FF0000;position:relative;right:0;"></span>
					</td>
	            </tr>
	        </table>
	        <table>
	            <tr>
	                <td class="infoTD">
	                    <div class="sub-title-02 float-l b-space5" style="width:100%;">
	                        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>비철원재료(원/kg)
	                        <span id="metalScenario"></span>
	                        <div class="headerBtnDiv">
	                            <span class="in-block v-middle r-space7">
	                                <select name="metalScenario">
	                                <c:forEach items="${scenario }" var="scen">
	                                    <option value="${scen.code }">${scen.name }</option>
	                                </c:forEach>
	                                </select>
	                            </span>
	                            <span class="in-block v-middle r-space7">
	                                <span class="pro-table">
	                                    <span class="pro-cell b-left"></span>
	                                    <span class="pro-cell b-center"> <a href="javascript:costPartInfoSave('nMetalInfo')" class="btn_blue">비철기준정보 동기화</a></span>
	                                    <span class="pro-cell b-right"></span>
	                                </span>
	                            </span>
	                            <span class="in-block v-middle r-space7">
	                                <span class="pro-table">
	                                    <span class="pro-cell b-left"></span>
	                                    <span class="pro-cell b-center"> <a href="javascript:costPartInfoSave('nMetalInfoByUserInput')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491")%><!-- 변경 --></a></span>
	                                    <span class="pro-cell b-right"></span>
	                                </span>
	                            </span>
	                            
	                        </div>
	                    </div>
	                    <div class="b-space5 partInfoDiv">
	                        <table id="nMetalInfo" summary="" class="table-style-01 infoTable">
	                            <colgroup>
	                                <col width="*" />
	                                <col width="15%" />
	                                <col width="15%" />
	                                <col width="15%" />
	                                <col width="*" />
	                                <col width="*" />
	                                <col width="*" />
	                            </colgroup>
	                            <thead>
	                                <tr>
	                                    <td class="center bgcol fontb" rowspan="2">부품번호</td>
	                                    <td class="center bgcol fontb" colspan="2">원재료</td>
	                                    <td class="center bgcol fontb" rowspan="2">LME</td>
	                                    <td class="center bgcol fontb" rowspan="2">원재료<br/>단가</td>
	                                    <td class="center bgcol fontb" rowspan="2">스크랩<br/>판매비</td>
	                                    <td class="center bgcol fontb" rowspan="2">스크랩<br/>재생비</td>
	                                </tr>
	                                <tr>
	                                    <td class="center bgcol fontb">원재료명</td>
	                                    <td class="center bgcol fontb">선도금사양</td>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            </tbody>
	                        </table>
	                    </div>
	                </td>
	                <td class="infoTD">
	                    <div class="sub-title-02 float-l b-space5" style="width:100%;">
                            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>수지원재료(원/kg)
                            <div class="headerBtnDiv">
	                            <span class="in-block v-middle r-space7">
	                                <span class="pro-table">
	                                    <span class="pro-cell b-left"></span>
	                                    <span class="pro-cell b-center"> <a href="javascript:costPartInfoSave('profitInfo')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491")%><!-- 변경 --></a></span>
	                                    <span class="pro-cell b-right"></span>
	                                </span>
	                            </span>
	                        </div>
                        </div>
                        <div class="b-space5">
                            <table id="profitInfo" summary="" class="table-style-01 infoTable">
                                <colgroup>
                                    <col width="*" />
                                    <col width="*" />
                                    <col width="10%" />
                                    <col width="*" />
                                    <col width="*" />
                                </colgroup>
                                <thead>
                                   <tr>
                                        <td class="center bgcol fontb" rowspan="2">부품번호</td>
                                        <td class="center bgcol fontb" colspan="3">원재료</td>
                                        <td class="center bgcol fontb" rowspan="2">원재료<br/>단가</td>
                                        <td class="center bgcol fontb" rowspan="2">스크랩<br/>판매비</td>
                                        <td class="center bgcol fontb" rowspan="2">스크랩<br/>재생비</td>
                                    </tr>
                                    <tr>
                                        <td class="center bgcol fontb">원재료명</td>
                                        <td class="center bgcol fontb">Grade</td>
                                        <td class="center bgcol fontb">Color</td>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
	                </td>
	            </tr>
	        </table>
	        <div id="INFO_TAB">
                <ul>
                    <li><a class="tabref" href="#BASIC_INFO">기본정보</a></li>
                </ul>
                <div id="BASIC_INFO" class="tabContent">
                <iframe class="tabFrame" name="COSTBOMBASIC" id="COSTBOMBASIC" width="99%" height="100%" src="/plm/ext/cost/costBomEditor?taskOid=${taskOid}&oid=${oid}&subVersion=${part.subVersion}&EDITMODE="+<c:if test="${isBasePart == true}">  "CALCULATE"</c:if><c:if test="${isBasePart == false}">  "VIEW"</c:if> scrolling="auto" frameborder="0"></iframe> 
                </div> 
            </div>
			<div id="COST_TAB" style="margin-top:10px;">
				<ul>
					<li><a class="tabref" href="#MATERIAL_COST">재료비</a></li>
					<li><a class="tabref" href="#LABOR_COST">노무비</a></li>
					<li><a class="tabref" href="#EXPENSE">경비</a></li>
					<li><a class="tabref" href="#MAINTENENCE_COST">관리비</a></li>
					<li class="ui-state-default ui-corner-top"><a onclick="javascript:getOpenWindow2('/plm/ext/cost/costReductionPopup?oid=${oid}&taskOid=${taskOid}', 'REDUCTION_COST', 1024, 768);" class="ui-tabs-anchor">감가비</a></li>
					<li><a class="tabref" href="#INVESTMENT_COST">투자비</a></li>
					<li><a class="tabref" href="#CAPA">Capa</a></li>
				</ul>
				<div id="MATERIAL_COST" class="tabContent">
				<iframe class="tabFrame" id="MATERIAL_COSTF" src="/plm/ext/cost/costCalculateGrid?taskOid=${taskOid}&COSTTYPE=MATERIAL_COST&oid=${oid}" width="99%" height="100%" scrolling="auto" frameborder="0"></iframe> 
				</div>
				<div id="LABOR_COST" class="tabContent">
				<iframe class="tabFrame" id="LABOR_COSTF" width="99%" height="100%" scrolling="auto" frameborder="0"></iframe> 
                </div>
				<div id="EXPENSE" class="tabContent">
				<iframe class="tabFrame" id="EXPENSEF" width="99%" height="100%" scrolling="auto" frameborder="0"></iframe> 
                </div>
				<div id="MAINTENENCE_COST" class="tabContent">
				<iframe class="tabFrame" id="MAINTENENCE_COSTF" width="99%" height="100%" scrolling="auto" frameborder="0"></iframe> 
                </div>
				<div id="INVESTMENT_COST" class="tabContent">
				<iframe class="tabFrame" id="INVESTMENT_COSTF" width="99%" height="100%" src="/plm/ext/cost/costInvestGrid?taskOid=${taskOid}&oid=${oid}&EDITMODE=VIEW_EXCEL" scrolling="auto" frameborder="0"></iframe> 
                </div>
				<div id="CAPA" class="tabContent">
				<iframe class="tabFrame" id="CAPAF" width="99%" height="100%" scrolling="auto" frameborder="0"></iframe> 
                </div>
			</div>
			
			<div style="width:100%;overflow-x:auto;overflow-y:hidden;">
				<table style="width:1820px;margin-top:30px;">
					<colgroup>
						<col width="50%" />
						<col width="50%" />
					</colgroup>
					<tbody>
						<tr>
							<td style="vertical-align:top;padding-right:20px;">
								<div class="sub-title-02 float-l b-space5" style="width:100%;">
									<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>원가산출결과
								</div>
								<div class="b-space5" style="position:relative;top:7px;">
									<table summary="" id="costCalcResult" class="table-style-01" style="table-layout:fixed;width:900px;">
									    <colgroup>
									        <col width="150" />
									    </colgroup>
										<thead>
											<tr>
												<td class="center bgcol fontb" rowspan="2">제품명</td>
												<td class="center bgcol fontb" colspan="4">제조원가</td>
												<td class="center bgcol fontb" rowspan="2">관리비</td>
												<td class="center bgcol fontb" rowspan="2">감가비</td>
												<td class="center bgcol fontb" rowspan="2">총원가</td>
												<td class="center bgcol fontb" rowspan="2">판매 목표가</td>
												<td class="center bgcol fontb" rowspan="2">수익율</td>
											</tr>
											<tr>
												<td class="center bgcol fontb">재료비</td>
												<td class="center bgcol fontb">노무비</td>
												<td class="center bgcol fontb">경비</td>
												<td class="center bgcol fontb">제조원가</td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</td>
							<td style="vertical-align:top;">
								<div class="sub-title-02 float-l b-space5" style="width:100%;">
									<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>매출액 및 영업이익
									<div style="float:right;z-index:1;position:relative;top:-5px;">
									    <span class="in-block v-middle r-space7" style="height:22px;">
	                                        지정품합계 : <span id="appointTotal" ></span>
	                                    </span>
										<span class="in-block v-middle r-space7">
											지정품판가 : <input type="text" class="calcAnalysis" name="appointSales" value="" style="width:50px;" />
										</span>
										<span class="in-block v-middle r-space7">
											CR(%) : <input type="text" class="calcAnalysis" name="cr" value="" /> %
										</span>
										<span class="in-block v-middle r-space7">
											적용년수 : <input type="text" class="calcAnalysis" name="applyYear" value=""  /> 년
										</span>
										<span class="in-block v-middle r-space7">
											회수기간 : <span name='correctPeriod'>0</span>
											<input type='hidden' name='salesTargetCostTotal' value='' />
										</span>
										<c:if test="${!isBasePart }">
										<span class="in-block v-middle r-space7" >
											<span class="pro-table">
												<span class="pro-cell b-left"></span>
												<span class="pro-cell b-center"> <a href="javascript:saveCostAnalysisInfo(true)" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><!-- 저장 --></a></span>
												<span class="pro-cell b-right"></span>
											</span>
										</span>
										</c:if>
									</div>
								</div>
								<div style="width:120px;float:left;">
				                    <table summary="" class="table-style-01" style="width:120px;">
		                                <thead>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="width:120px;border-right:1px solid #B7D1E2 !important;">구분</td>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="border-right:1px solid #B7D1E2 !important;">판매목표가</td>
		                                    </tr>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="border-right:1px solid #B7D1E2 !important;">총원가</td>
		                                    </tr>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="border-right:1px solid #B7D1E2 !important;">판매량(천개)</td>
		                                    </tr>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="border-right:1px solid #B7D1E2 !important;">매출액(백만원)</td>
		                                    </tr>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="border-right:1px solid #B7D1E2 !important;">영업이익(백만원)</td>
		                                    </tr>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="border-right:1px solid #B7D1E2 !important;">현급유입액(백만원)</td>
		                                    </tr>
		                                    <tr>
		                                        <td class="center bgcol fontb" style="border-right:1px solid #B7D1E2 !important;">영업이익율</td>
		                                    </tr>
		                                </tbody>
		                            </table>
			                    </div>
								<div style="width:660px;overflow-y:hidden;overflow-x:auto;float:left;">
									<table id="salesProfit" summary="" class="table-style-01">
										<thead>
											<tr><td style="display:none;"></td></tr>
										</thead>
										<tbody>
											<tr><td style="display:none;"></td></tr>
											<tr><td style="display:none;"></td></tr>
											<tr><td style="display:none;"></td></tr>
											<tr><td style="display:none;"></td></tr>
											<tr><td style="display:none;"></td></tr>
											<tr><td style="display:none;"></td></tr>
											<tr><td style="display:none;"></td></tr>
										</tbody>
									</table>
								</div>
								<div style="width:120px;overflow-y:hidden;overflow-x:auto;float:left;">
	                                <table id="salesProfitTotal" summary="" class="table-style-01">
	                                    <thead>
	                                        <tr>
	                                            <td class="center bgcol fontb" style="width:120px;border-left:1px solid #B7D1E2 !important;">합계</td>
	                                        </tr>
	                                    </thead>
	                                    <tbody>
	                                        <tr>
	                                            <td class="right total" style="border-left:1px solid #B7D1E2 !important;"></td>
	                                        </tr>
	                                        <tr>
	                                            <td class="right total" style="border-left:1px solid #B7D1E2 !important;"></td>
	                                        </tr>
	                                        <tr>
	                                            <td class="right total" style="border-left:1px solid #B7D1E2 !important;"></td>
	                                        </tr>
	                                        <tr>
	                                            <td class="right total" style="border-left:1px solid #B7D1E2 !important;"></td>
	                                        </tr>
	                                        <tr>
	                                            <td class="right total" style="border-left:1px solid #B7D1E2 !important;"></td>
	                                        </tr>
	                                        <tr>
	                                            <td class="right total" style="border-left:1px solid #B7D1E2 !important;"></td>
	                                        </tr>
	                                        <tr>
	                                            <td class="right total" style="border-left:1px solid #B7D1E2 !important;"></td>
	                                        </tr>
	                                    </tbody>
	                                </table>
	                            </div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="sub-title-02 float-l b-space5" style="width:900px;margin-top:20px;">
				<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>원가 산출 CASE
				<div class="b-space5"></div>
				<div style="float:right;z-index:1000;position:relative;top:-5px;">
				    <c:if test="${!isTaskComplete}">
				    <span class="in-block v-middle r-space7" >
						<span class="pro-table">
							<span class="pro-cell b-left"></span>
							<span class="pro-cell b-center"> <a href="javascript:openCopyCase()" class="btn_blue">산출안 복사</a></span>
							<span class="pro-cell b-right"></span>
						</span>
					</span>
					<span class="in-block v-middle r-space7" >
						<span class="pro-table">
							<span class="pro-cell b-left"></span>
							<span class="pro-cell b-center"> <a href="javascript:createCase()" class="btn_blue">CASE 생성</a></span>
							<span class="pro-cell b-right"></span>
						</span>
					</span>
					<span class="in-block v-middle r-space7" >
                        <span class="pro-table">
                            <span class="pro-cell b-left"></span>
                            <span class="pro-cell b-center"> <a href="javascript:deleteCase()" class="btn_blue">CASE 삭제</a></span>
                            <span class="pro-cell b-right"></span>
                        </span>
                    </span>
					<span class="in-block v-middle r-space7" >
						<span class="pro-table">
							<span class="pro-cell b-left"></span>
							<span class="pro-cell b-center"> <a href="javascript:saveCase()" class="btn_blue">산출안 저장</a></span>
							<span class="pro-cell b-right"></span>
						</span>
					</span>
					</c:if>
				</div>
			</div>
			<div class="b-space5" style="position:relative;top:-7px;width:900px;">
				<table id="caseList" summary="" class="table-style-01 infoTable">
					<thead>
						<tr>
							<td class="center bgcol fontb" style="width:30px;">선택</td>
							<td class="center bgcol fontb">제품명</td>
							<td class="center bgcol fontb">판매목표가</td>
							<td class="center bgcol fontb">총원가</td>
							<td class="center bgcol fontb">수익률</td>
							<td class="center bgcol fontb">버전</td>
							<td class="center bgcol fontb">CASE 순서</td>
							<td class="center bgcol fontb">비고</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<span style="font-size:14px;color:#FF0000;font-weight:bold;position:relative;top:10px;">
				※ CASE 1案은 최종안을 의미합니다. 보고서에는 CASE 1案~3案까지만 표기됩니다.
				</span>
			</div>
		</div>
	</div>
</body>
