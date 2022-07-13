<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<script src='/plm/extcore/js/cost/costCommon.js'></script>
<style>
.bold {
	font-weight: bold;
}

.back-red {
	background-color: #FCD !important;
}

.back-blue {
	background-color: #BDF !important;
}

.back-yellow {
	background-color: #FFA !important;
}

.red {
	color: #F00;
}

.blue {
	color: #00F;
}

.pointer {
	cursor: pointer;
}
</style>
<script>
$(document).ready(function() {
	$("body").removeClass("body-space");
	$("body").addClass("popup-background");
	$("body").addClass("popup-space");
	
	CalendarUtil.monthInputFormat('startMonth','endMonth'); //startMonth, endMonth

	var columnList = new Array();
    //####################################### 제품정보 ##################################################################
    columnList.push({LABEL : "구분", KEY : "PROD", LEVEL : 1, COLSPAN : 4 });
    columnList.push({LABEL : "품번", KEY : "realPartNo", Align : "Center", MinWidth : 100, RelWidth : 1, LEVEL : 2 });
    columnList.push({LABEL : "부품명", KEY : "partName", Align : "Center", MinWidth : 100, RelWidth : 1, LEVEL : 2 });
    columnList.push({LABEL : "속성명", KEY : "attrName", Align : "Center", MinWidth : 100, RelWidth : 1, LEVEL : 2 });
    columnList.push({LABEL : "원가정보", KEY : "COSTINFO", LEVEL : 1, COLSPAN : 4 });
    columnList.push({LABEL : "변경전", KEY : "leftValue", Format : "0,###.##", Type : "Float", MinWidth : 100, RelWidth : 1, LEVEL : 2 });
    columnList.push({LABEL : "변경후", KEY : "rightValue", Format : "0,###.##", Type : "Float", MinWidth : 100, RelWidth : 1, LEVEL : 2 });
    columnList.push({LABEL : "변동", KEY : "change", Format : "0,###.##", Type : "Float", MinWidth : 100,  RelWidth : 1, LEVEL : 2 });
    columnList.push({LABEL : "원가 변동액", KEY : "costChangePrice", Format : "0,###.##", Type : "Float", MinWidth : 100, RelWidth : 1, LEVEL : 1 });
    columnList.push({LABEL : "수익변동율", KEY : "profitChange", Format : "0,###.##%", Type : "Float", MinWidth : 100, RelWidth : 1, LEVEL : 1 });
    columnList.push({LABEL : "Best & Worst", KEY : "bestWorst", Type : "Text", Align : "Center", MinWidth : 100, RelWidth : 1, LEVEL : 1 });
    
	var columnProp = generateGridColumn(columnList);
	
	window.checkBestWorst = function(grid, thisRow){
		
		var rows = grid.Rows;
        var rowKeys = Object.keys(rows);
        var bestValue1 = null;
        var bestValue2 = null;
        var bestValue3 = null;
        var worstValue1 = null;
        var worstValue2 = null;
        var worstValue3 = null;
        
        for(var i = 0; i < rowKeys.length; i++){
            
            var id = rowKeys[i];
            var row = rows[id];
            
            if("Data" == row.Kind && row.realPartNo != "합계"){

                var costChangePrice = row.costChangePrice;
                var profitChange = row.profitChange;
                if(profitChange < 0.01 && profitChange > -0.01) continue;
                
                if(bestValue1 == null){
                	
                	bestValue1 = costChangePrice;
                	
                }else if(bestValue1 > costChangePrice){
                	
                	bestValue3 = bestValue2;
                	bestValue2 = bestValue1;
                	bestValue1 = costChangePrice;
                	
                }else if(bestValue2 > costChangePrice){
                	
                	bestValue3 = bestValue2;
                	bestValue2 = costChangePrice;
                	
                }else if(bestValue3 > costChangePrice){
                	
                	bestValue3 = costChangePrice;
                	
                }
                
                if(worstValue1 == null){
                	
                	worstValue3 = costChangePrice;
                	
                }if(worstValue1 < costChangePrice){
                	
                	worstValue3 = worstValue2;
                	worstValue2 = worstValue1;
                	worstValue1 = costChangePrice;
                	
                }else if(worstValue2 < costChangePrice){
                	
                	worstValue3 = worstValue2;
                	worstValue2 = costChangePrice;
                	
                }else if(worstValue3 < costChangePrice){
                	
                	worstValue3 = costChangePrice;
                	
                }
            }
        }
        /* 
        window.console.log('bestValue1 ############################# ', bestValue1);
        window.console.log('bestValue2 ############################# ', bestValue2);
        window.console.log('bestValue3 ############################# ', bestValue3);
        
        window.console.log('worstValue1 ############################# ', worstValue1);
        window.console.log('worstValue2 ############################# ', worstValue2);
        window.console.log('worstValue3 ############################# ', worstValue3);
         */
        for(var i = 0; i < rowKeys.length; i++){
            
            var id = rowKeys[i];
            var row = rows[id];
            
            if("Data" == row.Kind && row.realPartNo != "합계"){

                var costChangePrice = row.costChangePrice;
                var profitChange = row.profitChange;
                if(profitChange < 0.01 && profitChange > -0.01) continue;
                
                var prefix = "<span>";
                var value = "";
                
                if(bestValue1 == costChangePrice){
                    value = "Best1";
                    prefix = "<span class='blue'>";
                    
                }else if(bestValue2 == costChangePrice){
                    
                    value = "Best2";
                    prefix = "<span class='blue'>";
                    
                }else if(bestValue3 == costChangePrice){
                    
                    value = "Best3";
                    prefix = "<span class='blue'>";
                    
                }else if(worstValue1 == costChangePrice){
                    
                    value = "Worst1";
                    prefix = "<span class='red'>";
                    
                }else if(worstValue2 == costChangePrice){
                    
                    value = "Worst2";
                    prefix = "<span class='red'>";
                    
                }else if(worstValue3 == costChangePrice){
                    
                    value = "Worst3";
                    prefix = "<span class='red'>";
                    
                }
                
                grid.SetValue(row, "bestWorst", value, 1);
                grid.SetAttribute(row, "bestWorst", "HtmlPrefix", prefix, 1);
                grid.SetAttribute(row, "bestWorst", "Postfix", "</span>", 1);
                grid.SetAttribute(row, "bestWorst", "Changed", 0, 1);

            }
        }
	}
	
	var costGridProp = {
        Debug : 0,
        Export : {
            Url : "/plm/jsp/common/treegrid/ExcelExport.jsp",
            Data : "TGData",
            Param : {
                File : "KPLUS_Grid_list"
            }
        },
        Layout : {
            Data : {
                Cfg : {
                    id : "costAnalysisGrid",
                    Style : gridStyle,
                    SuppressCfg : 1,
                    IdPrefix : "T",
                    IdChars : "0123456789",
                    NumberId : 1,
                    Undo : 1,
                    Sorting : 0,
                    Editing : 0,
                    Deleting : 0,
                    ShowDeleted : 0,
                    Selecting : 0,
                    CopySelected : 0,
                    CopyPasteTree : 0,
                    Pasting : 0,
                    Dragging : 0,
                    Dropping : 0,
                    ExportFormat : 1,
                    ExportType : "Filtered,Hidden,Strings,Dates",
                    SaveSession : 1,
                    UsePrefix : 1,
                    Alternate : 0,
                    SuppressMessage : 1,
                    FastColumns : 1,
                    ExportFormat : 1,
                    ExportType : "Filtered,Hidden,Strings,Dates"
                },
                Toolbar : { Visible : 0 },
                LeftCols : columnProp.leftCols,
                Cols : columnProp.cols,
                Head : [{
                    Kind : "Topbar",
                    Cells : "Save,ExpandAll,CollapseAll,Reload,Export",
                    Styles : 2,
                    Calculated : 1,
                    Link : 0,
                },{
                    Kind : "Header",
                    id : "Header",
                    Visible : 0
                },
                columnProp.kHeader1Lv,
                columnProp.kHeader2Lv,
                ],
                Body : [ { Pos : 0 } ],
            }
        }
    }
	
    var costAnalysisGrid = TreeGrid(costGridProp, 'costAnalysisGrid');
	
	Grids.OnDataError = function(grid, source, result, message, data){
		hideProcessing();
		alert("ERROR : " + message);
	}
	
	$("select[name=rightPart]").change(function(){
		$('select[name=rightPart] option[value=ERP]').remove();
	});
	
	$('.compareSelect').on('focus', function () {
		
		window.templOid = $('select[name=leftPart]').val();
	    window.temprOid = $('select[name=rightPart]').val();
	    
	}).change(function(){
		
		var leftPartOid = $('select[name=leftPart]').val();
		var rightPartOid = $('select[name=rightPart]').val();
		
	    $('.drNameLeft').text($('select[name=leftPart] option:selected').text());
	    $('.drNameRight').text($('select[name=rightPart] option:selected').text());
	    
		compareDRInfo(leftPartOid, rightPartOid);
	});
	
	$('.drNameLeft').text($('select[name=leftPart] option:selected').text());
    $('.drNameRight').text($('select[name=rightPart] option:selected').text());
    
    window.erpCompare = function(){
    	
    	var startMonth = $("input[name=startMonth]").val();
    	var endMonth = $("input[name=endMonth]").val();
    	
    	if("" == startMonth.trim() || "" == endMonth.trim()){
    		alert("시작월/종료월을 기입하십시오.");
    		return;
    	}
	    
    	window.templOid = $('select[name=leftPart]').val();
	    window.temprOid = $('select[name=rightPart]').val();
	    
	    $('select[name=rightPart] option[value=ERP]').remove();
		$('select[name=rightPart]').append("<option value='ERP'>양산원가</option>");
		$('select[name=rightPart]').val("ERP");
	    $('.drNameLeft').text($('select[name=leftPart] option:selected').text());
	    $('.drNameRight').text("양산원가");
	    
	    var leftPartOid = $('select[name=leftPart]').val();
	    var rightPartOid = $('select[name=rightPart]').val();
		
		compareDRInfo(leftPartOid, rightPartOid);
	
    }
    
    /* $("input[name=isUpdateSync]").click(function(){
    	var leftPartOid = $('select[name=leftPart]').val();
	    var rightPartOid = $('select[name=rightPart]').val();
		compareDRInfo(leftPartOid, rightPartOid);
    }); */
	
	window.compareDRInfo = function(leftPartOid, rightPartOid){
		
		var param = new Object();
		param.leftPartOid = leftPartOid;
		param.rightPartOid = rightPartOid;
		param.startMonth = $("input[name=startMonth]").val();
		param.endMonth = $("input[name=endMonth]").val();
		param.isUpdateSync = $("input[name=isUpdateSync]:checked").val();
		
		ajaxCallServer("/plm/ext/cost/getCompareDRInfo", param, function(data){
			
			if(data.errorMsg != null){
				
				alert(data.errorMsg);
				
				$('select[name=leftPart]').val(window.templOid);
			    $('select[name=rightPart]').val(window.temprOid);
			    $('.drNameLeft').text($('select[name=leftPart] option:selected').text());
			    $('.drNameRight').text($('select[name=rightPart] option:selected').text());
			    
			    return;
			}
			
			var drAnalysisHtml = "";
			var eqAnalysisHtml = "";
			var drAnalysisList = data.drAnalysisList;
			var eqAnalysisList = data.eqAnalysisList;
			
			
           
            for(var i = 0; i < drAnalysisList.length; i++){
            	var row = drAnalysisList[i];
            	window.console.log("LPART === " + row.ltotalCostExpr);
            }
            
            for(var i = 0; i < drAnalysisList.length; i++){
            	var row = drAnalysisList[i];
            	 window.console.log("RPART === " + row.rtotalCostExpr);
            }

			for(var i = 0; i < drAnalysisList.length; i++){
				
				var isTotal = (drAnalysisList.length - 1  == i);
				
				var row = drAnalysisList[i];
				var cssClass = isTotal ? ' bold ' : '';
				
				drAnalysisHtml += '<tr>';
				
				if(row.lpartExist){
					
					if(row.rpartNo == "합계") {
						$(".lfVersion").text(" (수식버전 : " + row.lformulaVersion + ")" );
						drAnalysisHtml += '<td class="' + cssClass + ' center" colspan="2" >' + checkNull(row.lpartNo) + '</td>';
					}else {
						drAnalysisHtml += '<td class="' + cssClass + '" >' + checkNull(row.lpartNo) + '</td>';
						drAnalysisHtml += '<td class="' + cssClass + '" >' + checkNull(row.lpartName) + '</td>';
					}
					
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.lmaterialCostExpr.commaFormat() + '</td>';
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.llaborCostExpr.commaFormat() + '</td>';
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.lexpenseExpr.commaFormat() + '</td>';
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.lmanageCostExpr.commaFormat() + '</td>';
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.lreduceCostExpr.commaFormat() + '</td>';
	                drAnalysisHtml += '<td class="right' + cssClass + '" style="font-weight:bold;">' + row.ltotalCostExpr.commaFormat() + '</td>';
	                
				}else{
					drAnalysisHtml += '<td class="center' + cssClass + ' bold back-red" colspan="8" >[BOM 추가]</td>';
				}
				
				if(i == 0){
					drAnalysisHtml += '<td class="center bold" rowspan="' + drAnalysisList.length + '">' + row.lsalesTargetCostExpr.commaFormat() + '</td>';
					drAnalysisHtml += '<td class="center bold" rowspan="' + drAnalysisList.length + '">' + row.lprofitRateExpr.toFloat(2) + '%</td>';
				}
				
				drAnalysisHtml += '<td style="background:none;border:0;">&nbsp;</td>';
				
				if(row.rpartExist){
					if(row.rpartNo == "합계") {
						$(".rfVersion").text(" (수식버전 : " + row.rformulaVersion + ")" );
                        drAnalysisHtml += '<td class="' + cssClass + ' center" colspan="2" >' + checkNull(row.rpartNo) + '</td>';
                    }else {
                        drAnalysisHtml += '<td class="' + cssClass + '" >' + checkNull(row.rpartNo) + '</td>';
                        drAnalysisHtml += '<td class="' + cssClass + '" >' + checkNull(row.rpartName) + '</td>';
                    }
					
	                var val = parseFloat(calculateValue(row.rmaterialCostExpr, row.lmaterialCostExpr, "-", 4));
	                cssClass = isTotal ? ' bold ' : '';
	                if(val > 0)         cssClass += ' back-red ';
	                else if(val < 0)    cssClass += ' back-blue ';
	                
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.rmaterialCostExpr.commaFormat() + '</td>';
	                
	                val = parseFloat(calculateValue(row.rlaborCostExpr, row.llaborCostExpr, "-", 4));
	                cssClass = isTotal ? ' bold ' : '';
	                if(val > 0)         cssClass += ' back-red ';
	                else if(val < 0)    cssClass += ' back-blue ';
	                
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.rlaborCostExpr.commaFormat() + '</td>';
	                
	                val = parseFloat(calculateValue(row.rexpenseExpr, row.lexpenseExpr, "-", 4));
	                cssClass = isTotal ? ' bold ' : '';
	                if(val > 0)         cssClass += ' back-red ';
	                else if(val < 0)    cssClass += ' back-blue ';
	                
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.rexpenseExpr.commaFormat() + '</td>';
	                
	                val = parseFloat(calculateValue(row.rmanageCostExpr, row.lmanageCostExpr, "-", 4));
	                cssClass = isTotal ? ' bold ' : '';
	                if(val > 0)         cssClass += ' back-red ';
	                else if(val < 0)    cssClass += ' back-blue ';
	                
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.rmanageCostExpr.commaFormat() + '</td>';
	                
	                val = parseFloat(calculateValue(row.rreduceCostExpr, row.lreduceCostExpr, "-", 4));
	                cssClass = isTotal ? ' bold ' : '';
	                if(val > 0)         cssClass += ' back-red ';
	                else if(val < 0)    cssClass += ' back-blue ';
	                
	                drAnalysisHtml += '<td class="right' + cssClass + '">' + row.rreduceCostExpr.commaFormat() + '</td>';
	                
	                val = parseFloat(calculateValue(row.rtotalCostExpr, row.ltotalCostExpr, "-", 4));
	                cssClass = '';
	                if(val > 0)         cssClass += ' back-red ';
	                else if(val < 0)    cssClass += ' back-blue ';
	                
	                drAnalysisHtml += '<td class="right bold ' + cssClass + '">' + row.rtotalCostExpr.commaFormat() + '</td>';
	                
	                if(i == 0){
	                    val = parseFloat(calculateValue(row.rsalesTargetCostExpr, row.lsalesTargetCostExpr, "-", 4));
	                    cssClass = '';
	                    if(val > 0)         cssClass = ' back-red ';
	                    else if(val < 0)    cssClass = ' back-blue ';
	                    drAnalysisHtml += '<td class="center bold ' + cssClass + '" rowspan="' + drAnalysisList.length + '" >' + row.rsalesTargetCostExpr.commaFormat() + '</td>';
	                    
	                    val = parseFloat(calculateValue(row.rprofitRateExpr, row.lprofitRateExpr, "-", 4));
	                    cssClass = '';
	                    if(val > 0)         cssClass = ' back-blue ';
	                    else if(val < 0)    cssClass = ' back-red ';
	                    drAnalysisHtml += '<td class="center bold ' + cssClass + '" rowspan="' + drAnalysisList.length + '" >' + row.rprofitRateExpr.toFloat(2) + '%</td>';
	                }
	                
				}else{
                    drAnalysisHtml += '<td class="center' + cssClass + ' bold back-blue" colspan="8" >[BOM 삭제]</td>';
                }
                
                drAnalysisHtml += '<td style="background:none;border:0;">&nbsp;</td>';

                val = parseFloat(row.costChangePrice.toFloat(2));
                cssClass = '';
                var prefix = "";
                if(val > 0){
                	cssClass = ' red ';
                	prefix = '▲ ';
                }else if(val < 0){
                	cssClass = ' blue ';
                	prefix = '▼ ';
                }
                
                var oidData = 'data-leftoid="' + row.leftPartOid + '" data-rightoid="' + row.rightPartOid + '" data-istotal="' + isTotal + '"';
                
                drAnalysisHtml += '<td class="costChangePrice right pointer ' + cssClass + '" ' + oidData + '>' + prefix + row.costChangePrice.commaFormat() + '</td>';
                
                if(i == 0){
                	
                	val = parseFloat(row.saleCostChangePrice.toFloat(2));
                    cssClass = '';
                    var prefix = "";
                    if(val > 0){
                        cssClass = ' red ';
                        prefix = '▲ ';
                    }else if(val < 0){
                        cssClass = ' blue ';
                        prefix = '▼ ';
                    }
                	
                    drAnalysisHtml += '<td class="center' + cssClass + '" rowspan="' + drAnalysisList.length + '">' + prefix + row.saleCostChangePrice.commaFormat() + '</td>';
	                
	                val = parseFloat(row.profitChangeRate.toFloat(2));
                    cssClass = '';
                    var prefix = "";
                    if(val > 0){
                        cssClass = ' blue ';
                        prefix = '▲ ';
                    }else if(val < 0){
                        cssClass = ' red ';
                        prefix = '▼ ';
                    }
                    
                    drAnalysisHtml += '<td class="center' + cssClass + '" rowspan="' + drAnalysisList.length + '">' + prefix + row.profitChangeRate.toFloat(2) + '%</td>';
                }
                drAnalysisHtml += '</tr>';
			}
			
			$("#drAnalysis tbody").html(drAnalysisHtml);

			if(eqAnalysisList == null){
				$('.eqAnalysis').hide();
			}else{
				$('.eqAnalysis').show();

				var rowspan = eqAnalysisList.length - 2;
				
			    for(var i = 0; i < eqAnalysisList.length; i++){
	                
	                var row = eqAnalysisList[i];

	                eqAnalysisHtml += '<tr>';
	                
	                if(rowspan == i){
	                	eqAnalysisHtml += '<td class="center" rowspan="2">' + row.DIVISION + '</td>';
	                }else if(0 == i){
	                	eqAnalysisHtml += '<td class="center" rowspan="' + rowspan + '">' + row.DIVISION + '</td>';
	                }
	                
	                eqAnalysisHtml += '<td class="center">' + row.NAME + '</td>';
	                eqAnalysisHtml += '<td class="center">' + row.BEFORE_VAL.commaFormat() + '</td>';
	                eqAnalysisHtml += '<td class="center">' + row.AFTER_VAL.commaFormat() + '</td>';
	                eqAnalysisHtml += '<td class="center">' + row.CHANGE_VAL.commaFormat() + '</td>';
	                eqAnalysisHtml += '</tr>';
	            }
				
				$('#eqAnalysis tbody').html(eqAnalysisHtml);
			}
			
			
			Grids['costAnalysisGrid'].Source.Data.Url = '/plm/jsp/common/treegrid/InitGridData.jsp';
	        Grids['costAnalysisGrid'].Source.Data.Param = {};
	        Grids['costAnalysisGrid'].ReloadBody(function(){
	        
	        });
			
			$('.costChangePrice').click(function(e){
				
		        $('.costChangePrice').removeClass('back-yellow');
		        $(this).addClass('back-yellow');
		        
		        var leftPartOid = $(this).data('leftoid');
		        var rightPartOid = $(this).data('rightoid');
		        var isTotal = $(this).data('istotal');

		        var param = new Object();
		        param.leftProductOid = $('select[name=leftPart]').val();
		        param.rightProductOid = $('select[name=rightPart]').val();
		        param.leftPartOid = checkNull(leftPartOid);
		        param.rightPartOid = checkNull(rightPartOid);
		        param.isTotal = isTotal;
		        param.startMonth = $("input[name=startMonth]").val();
				param.endMonth = $("input[name=endMonth]").val();
				param.isUpdateSync = $("input[name=isUpdateSync]:checked").val();
		        
		        Grids['costAnalysisGrid'].Source.Data.Url = '/plm/ext/cost/getAnalysisList';
		        Grids['costAnalysisGrid'].Source.Data.Param = param;
		        //Grids['costAnalysisGrid'].Source.Data.Repeat = 3;
		        Grids['costAnalysisGrid'].Source.Data.Timeout = 0;
		        
		        showProcessing();
		        
		        Grids['costAnalysisGrid'].ReloadBody(function(){
		        	
		        	var grid = Grids['costAnalysisGrid'];
		        	
		        	checkBestWorst(grid);
		        	hideProcessing();
		        });
		    });
		});
	}

    setTimeout(function(){
        compareDRInfo($('select[name=leftPart]').val(), $('select[name=rightPart]').val());
    }, 500);
});
</script>
<style>
.compareSelect{
    width:150px;
    height:30px;
}
</style>
<div class="contents-wrap">
	<div class="sub-title b-space5">
		<img src="/plm/portal/images/icon_3.png" /> ${trackingDTO.pjtName }
		수익율 분석
	</div>
	<div class="b-space5 float-r" style="text-align: right;">
		<span class="in-block v-middle r-space7"> 
            <span class="pro-table">
            	<label><input type="checkbox" name="isUpdateSync" value="true" checked />최신 수식으로 계산 비교</label>
            	<input type="text" name="startMonth" class="txt_field" style="width: 70px;" value="">
                <a href="javascript:CommonUtil.deleteValue('startMonth');">
                    <img src="/plm/portal/images/icon_delete.gif" border="0">
                </a>~ 
                <input type="text" name="endMonth" class="txt_field" style="width: 70px;" value="">
                <a href="javascript:CommonUtil.deleteValue('endMonth');">
                    <img src="/plm/portal/images/icon_delete.gif" border="0">
                </a>
                <select class="compareSelect" name="leftPart">
					<c:forEach items="${trackingDTO.cpMapList}" var="cp" varStatus="stat">
                    <option value="${cp.oid }">${cp.drStep } (${cp.version }.${cp.subVersion }) ${cp.caseNote }</option>
					</c:forEach>
                </select>
                <select class="compareSelect" name="rightPart">
					<c:forEach items="${trackingDTO.cpMapList}" var="cp" varStatus="stat">
                    <option value="${cp.oid }">${cp.drStep } (${cp.version }.${cp.subVersion }) ${cp.caseNote }</option>
                    </c:forEach>
                </select>
                <c:if test="${not empty lpartOid}">
                <script>
                $('select[name=leftPart]').val('${lpartOid}');
                $('select[name=rightPart]').val('${rPartOid}');
                </script>
                </c:if>
            </span>
		</span>
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center">
				    <a href="javascript:erpCompare();" class="btn_blue">양산원가 비교</a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
		</span>
		<span class="in-block v-middle r-space7">
			<span class="pro-table">
				<span class="pro-cell b-left"></span>
				<span class="pro-cell b-center">
				    <a href="javascript:self.close();" class="btn_blue"> <%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
		</span>
	</div>

	<div class="sub-title-02 float-l b-space5 eqAnalysis">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>
		환율/물량 분석
	</div>
	<div class="b-space5">
		<table summary="" class="table-style-01 eqAnalysis" id="eqAnalysis">
			<thead>
				<tr>
					<td class="center bgcol fontb" colspan="2">구분</td>
					<td class="center bgcol fontb"><span class="drNameLeft"></span></td>
					<td class="center bgcol fontb"><span class="drNameRight"></span></td>
					<td class="center bgcol fontb">변동</td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div class="sub-title-02 float-l b-space5" style="margin-top: 10px;">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>
		DR 단계별 원가변동 분석
	</div>
	<div class="b-space5">
		<div style="width: 100%; overflow-x: auto; overflow-y: hidden;">
			<table summary="" class="table-style-01" style="min-width: 1750px;"
				id="drAnalysis">
				<colgroup>
				    <col width="120">
					<col width="150">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="15">
					<col width="120">
					<col width="150">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="80">
					<col width="15">
					<col width="100">
					<col width="100">
					<col width="100">
				</colgroup>
				<thead>
					<tr>
					    <td class="center bgcol fontb" rowspan="2">품번</td>
						<td class="center bgcol fontb" rowspan="2">구분</td>
						<td class="center bgcol fontb" colspan="6"><span class="drNameLeft"></span><span class="lfVersion"></span></td>
						<td class="center bgcol fontb" rowspan="2">판매 목표가</td>
						<td class="center bgcol fontb" rowspan="2">수익율</td>
						<td style="background: none; border: 0;" rowspan="2">&nbsp;</td>
						<td class="center bgcol fontb" rowspan="2">품번</td>
						<td class="center bgcol fontb" rowspan="2">구분</td>
						<td class="center bgcol fontb" colspan="6"><span class="drNameRight"></span><span class="rfVersion"></span></td>
						<td class="center bgcol fontb" rowspan="2">판매 목표가</td>
						<td class="center bgcol fontb" rowspan="2">수익율</td>

						<td style="background: none; border: 0;" rowspan="2">&nbsp;</td>

						<td class="center bgcol fontb" rowspan="2">원가 변동액</td>
						<td class="center bgcol fontb" rowspan="2">판가 변동액</td>
						<td class="center bgcol fontb" rowspan="2">수익 변동율</td>
					</tr>
					<tr>
						<td class="center bgcol fontb">재료비</td>
						<td class="center bgcol fontb">노무비</td>
						<td class="center bgcol fontb">경비</td>
						<td class="center bgcol fontb">관리비</td>
						<td class="center bgcol fontb">감가비</td>
						<td class="center bgcol fontb">합계</td>

						<td class="center bgcol fontb">재료비</td>
						<td class="center bgcol fontb">노무비</td>
						<td class="center bgcol fontb">경비</td>
						<td class="center bgcol fontb">관리비</td>
						<td class="center bgcol fontb">감가비</td>
						<td class="center bgcol fontb">합계</td>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>

	<div class="sub-title-02 float-l b-space5" style="margin-top: 10px;">
		<span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>
		원가동인별 변동 분석
	</div>
	<div class="b-space5">
		<div id='costAnalysisGrid'
			style='width: 100%; height: 100%; margin-top: 20px;'></div>
	</div>
</div>



