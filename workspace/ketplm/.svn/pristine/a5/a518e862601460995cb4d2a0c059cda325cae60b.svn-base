<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript"
	src="/plm/portal/js/multicombo/jquery-ui.min.js"></script>
<script src="/plm/extcore/js/dashboard/FusionCharts.js"></script>
<script>
$(document).ready(function(){
	
	$("#chartDiv").height($("#chartDiv").width()*0.8);
	$("#chartDiv1").height($("#chartDiv1").width()*0.8);
	$("#chartDiv2").height($("#chartDiv2").width()*0.8);
	$("#chartFrame", parent.document).height($("#chartTable").height()+50);
	
	var chart = new FusionCharts({
	    type: 'pie2d',
	    id : 'chart',
	    renderAt: 'chartDiv',
	    width: '100%',
	    height: '100%',
	    dataFormat: 'json'
	}).render();
	
	var chart1 = new FusionCharts({
	    type: 'pie2d',
	    id : 'chart1',
	    renderAt: 'chartDiv1',
	    width: '100%',
	    height: '100%',
	    dataFormat: 'json'
	}).render();
	
	var chart2 = new FusionCharts({
	    type: 'zoomline',
	    id : 'chart2',
	    renderAt: 'chartDiv2',
	    width: '100%',
	    height: '100%',
	    dataFormat: 'json'
	}).render();
	
	chart2.addEventListener("DataUpdated", function(){
		loadChartData(chart);
		loadChartData(chart1);
	});
	chart2.addEventListener("DrawComplete", function(){
		
		$("#chart2 text").each(function(){
			var text = $(this).text();
			if(text != "DR단계별 원가" && text.indexOf("DR") >= 0){
				$(this).css("cursor", "pointer");
			}
		});
		
		$("#chart2 text").click(function(e){
			var text = $(this).children("tspan").text();
			
			if(text != "DR단계별 원가" && text.indexOf("DR") >= 0){

				window.drStepRevLabel = text;
				window.drStepRev = window.revdata[text];
				
				loadChartData(chart);
				loadChartData(chart1);
			}
		});
	});
	
	
	var pie2dProp = {
			chart : {
			    "caption" : "",
				"bgcolor" : "FFFFFF",
				"canvasBgColor" : "FFFFFF",
				"canvasBorderAlpha" : "0",
				"usePlotGradientColor" : "0",
				"showAlternateHGridColor" : "0",
				"showBorder" : "0",
				"animation" : "1",
				"showLegend" : "0",
				"legendPosition" : "RIGHT",
				"legendShadow" : "0",
				"legendBorderAlpha" : "0",
				"enableslicing" : "0",
				"plotBorderAlpha" : "10",
				"use3DLighting" : "0",
				"showShadow" : "0",
				"enableRotation" : "0",
				"legendShadow" : "0",
				"formatNumberScale" : "0",
				"baseFontSize" : "12",
				"interactiveLegend" : "0",
				"showLabels" : "1",
				"showPercentValues" : "1",
				"startingAngle" : "90",
				"baseFont" : 'NanumGothic,"나눔고딕",Nanumgo,NanumBold,Dotum,Verdana,Arial,Helvetica,sans-serif',
			},
	};
	var zoomlineProp = {
			chart : {
			    "caption" : "DR단계별 원가",
			    "captionFontSize" : "20",
				"bgcolor" : "FFFFFF",
				"canvasBgColor" : "FFFFFF",
				"canvasBorderAlpha" : "0",
				"usePlotGradientColor" : "0",
				"showAlternateHGridColor" : "0",
				"showBorder" : "0",
				"legendPosition" : "RIGHT",
				"legendBorderAlpha" : "0",
				"animation" : "0",
				"showLegend" : "1",
				"showShadow" : "0",
				"legendShadow" : "0",
				"baseFont" : 'NanumGothic,"나눔고딕",Nanumgo,NanumBold,Dotum,Verdana,Arial,Helvetica,sans-serif',
				"baseFontSize" : "12",
				"scrollheight": "10",
				"scrollColor" : "FFFFFF",
				"compactdatamode" : "1",
				"dataseparator" : "|",
				"formatnumber":"1",
			    "formatnumberscale":"0",
			    "sformatnumber":"1",
			    "sformatnumberscale":"0",
			    "numberPrefix" : "\\",
			    "rotateLabels" : "0",
			},
	}
	
	window.loadChartData = function(chart, flags){
		
		var chartID = chart.args.id;
		var divID = chart.options.containerElementId;
		if(flags == null){
			flags = "총원가";
		}
		
		var params = {
				chartID : chartID,
				oid : "${oid}",
				productNo : "${productNo}",
				drStepRev : window.drStepRev,
				flags : flags
		}
		
		$('#' + divID).loadMask('loading...');
		ajaxCallServer("/plm/ext/cost/loadChartData", params, function(data){
			
			var chartProp = pie2dProp;
			var chartID = data.chartID;
			
			
			if(chartID == "chart2"){
				
				chartProp = zoomlineProp;
				window.revdata = data.revdata;
				window.drStepRevLabel = data.lastDRStepRev;
				window.drStepRev = data.lastDRStepRev;
				chartProp.categories = data.categories;
				chartProp.dataset = data.dataset;
				
			}else if(chartID == "chart1"){
				
				chartProp.chart.caption = "유형별 " + flags + " 비율";
				chartProp.chart.subCaption = window.drStepRevLabel;
				
			}else if(chartID == "chart"){

				chartProp.chart.caption = "원가동인별 " + flags + " 비율";
				chartProp.chart.subCaption = window.drStepRevLabel;
			}
			
			//특정값(24930.0,24929.0,etc....) 하나 외에 나머지 0일 경우 차트 비정상 표시로 인한 값 변경 처리
			if(chartID != "chart2"){
				
				var chartData = data.data;
				
				var checkCnt = 0;
				var singleData = {};
				
				for(var i = 0; i < chartData.length; i++){
					
					var value = chartData[i].value;
					
					if(value != "0.0"){
						checkCnt++;
						singleData = chartData[i];
						singleData.idx = i;
					}
				}
				
				if(checkCnt == 1){
					singleData.value = "1";
					chartData[singleData.idx] = singleData;
				}
				
				chartProp.data = chartData;
			}
			
			FusionCharts(chartID).setJSONData(chartProp);
			
			$('#' + divID).unLoadMask();
			
        },false);
	};
	
	loadChartData(chart2);
	
	$(window).resize(function(){
		$("#chartDiv").height($("#chartDiv").width()*0.8);
		$("#chartDiv1").height($("#chartDiv1").width()*0.8);
		$("#chartDiv2").height($("#chartDiv2").width()*0.8);
		$("#chartFrame", parent.document).height($("#chartTable").height()+50);
	});
	
	window.onChartDataClick = function(chart, flags){
		var chartID = chart.options.containerElementId;
		loadChartData(chart, flags);
		
		if("chartDiv1" == chartID){
			loadChartData(FusionCharts('chart'));
		}else{
			loadChartData(FusionCharts('chart1'));
		}
	}
});
</script>
<table id="chartTable" style="width: 100%; table-layout: fixed;">
	<tr>
		<td><div id="chartDiv"></div></td>
		<td><div id="chartDiv1"></div></td> <!-- |Mold|Press|조립|구매품|W/H품|지정품|양산품 -->
		<td><div id="chartDiv2"></div> 
		<span style="font-weight: bold;color:#0099CC">※ DR 단계 선택시 원차트가 표현됩니다.</span></td>
	</tr>
</table>
