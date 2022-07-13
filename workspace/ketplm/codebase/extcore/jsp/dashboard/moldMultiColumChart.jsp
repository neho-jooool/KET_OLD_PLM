<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/processing.html" %>    
<form id="multiChart">

<!--  신규 html -->
<div class="contents-wrap">
    <input type="hidden" id="year" name="year" value=""/>
    <input type="hidden" id="month" name="month" value=""/>
     <input type="hidden" id="devType" name="devType" value=""/>
     <input type="hidden" id="type" name="type" value=""/>
     <input type="hidden" id="making" name="making" value=""/>
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07195") %></div> 
    <div class="current-location text-normal">
        <span><img src="/plm/portal/images/icon_navi.gif" />Home<img src="/plm/portal/images/icon_navi.gif" />DashBoard<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "07195") %></span>
    </div>
    <div class="inquiry-table02 clearfix b-space30">
        <div class="float-r">
            <span class="r-space7 v-middle"><%=messageService.getString("e3ps.message.ket_message", "01662") %> : 
                <select id="division" name="division" multiple="multiple">
				    <option value="total"><%=messageService.getString("e3ps.message.ket_message", "02485") %></option>
				    <option value="elect"><%=messageService.getString("e3ps.message.ket_message", "02483") %></option>
				    <option value="car"><%=messageService.getString("e3ps.message.ket_message", "02401") %></option>
				</select>
			</span>
            <span class="r-space7 v-middle">
                <select id="yearSetting" name="yearSetting" multiple="multiple"></select>
            </span>
            <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="#" class="btn_blue" id="search"><%=messageService.getString("e3ps.message.ket_message", "02652") %></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="#" class="btn_blue" id="annualStartState"><%=messageService.getString("e3ps.message.ket_message", "07196") %></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="#" class="btn_blue" id="stepMoldState"><%=messageService.getString("e3ps.message.ket_message", "07197") %></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="#" class="btn_blue" id="moldManufacture"><%=messageService.getString("e3ps.message.ket_message", "07198") %></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
    </div>
    <div class="second-section clearfix b-space40">
        <div class="float-l" style="width:46%;margin-right:8%">
            <div class="sub-title-02 b-space">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07199") %>
            </div>
            <div class="clearfix">
                <div id="chartdiv"></div>
               <!--  <div class="float-l" style="width:48%;height:200px;" id="chartdiv4"></div> -->
            </div>
        </div>
        <div class="float-l" style="width:46%">
            <div class="sub-title-02 b-space">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07200") %>
            </div>
            <div id="chartdiv1">
            </div>
        </div>
    </div>
    <div class="second-section clearfix">
        <div class="float-l" style="width:46%;margin-right:8%">
            <div class="sub-title-02 b-space">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07201") %><span class="text-normal"><span class="l-space40 r-space5"><input type="radio" id="moldTypeAll" name="moldTypeCheck" value="Total" class="v-text-bottom" checked="checked"/></span>전체<span class="l-space10 r-space5"><input type="radio" id="moldTypePress" name="moldTypeCheck" value="Press" class="v-text-bottom" /></span>Press<span class="l-space10 r-space5"><input type="radio" id="moldTypeMold" name="moldTypeCheck" value="Mold" class="v-text-bottom" /></span>Mold</span>
            </div>
            <div id="chartdiv2">
            </div>
        </div>
        <div class="float-l" style="width:46%">
            <div class="sub-title-02 b-space">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07202") %><span class="text-normal"><span class="l-space40 r-space5"><input type="radio" id="moldCategoryAll" name="moldCategoryCheck" value="Total" class="v-text-bottom" checked="checked"/></span>전체<span class="l-space10 r-space5"><input type="radio" id="moldCategoryPress" name="moldCategoryCheck" value="Press" class="v-text-bottom" /></span>Press<span class="l-space10 r-space5"><input type="radio" id="moldCategoryMold" name="moldCategoryCheck" value="Mold" class="v-text-bottom" /></span>Mold</span>
            </div>
            <div id="chartdiv3">
            </div>
        </div>
    </div>
</div>
<iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
</form>

<script type="text/javascript">

$(document).ready(function(){
	
	printTime();
	
	CommonUtil.singleSelect('division',100);
	CommonUtil.singleSelect('yearSetting',100);
	    
    var chart = new FusionCharts("/plm/extcore/swf/MSStackedColumn2D.swf", "ChartId", "100%", "200", "0", "1" );

    chart.render("chartdiv");
    
    loadData();
    
   // var chart4 = new FusionCharts("/plm/extcore/swf/StackedColumn2D.swf", "ChartId4", "100%", "300", "0", "1" );

    //chart4.render("chartdiv4");
    
   // loadData4();
    
    var chart1 = new FusionCharts("/plm/extcore/swf/MSColumn2D.swf", "ChartId1", "100%", "200", "0", "1" );

    chart1.render("chartdiv1");
    
    loadData1();

    
    var chart2 = new FusionCharts("/plm/extcore/swf/MSColumn2D.swf", "ChartId2", "100%", "200", "0", "1" );

    chart2.render("chartdiv2");
    
    loadData2();
    
    var chart3 = new FusionCharts("/plm/extcore/swf/MSColumn2D.swf", "ChartId3", "100%", "200", "0", "1" );

    chart3.render("chartdiv3");
    
    loadData3();
    
    
    $("#moldTypeAll").click(function(){
    	loadData2();
    });
    
    $("#moldTypePress").click(function(){
    	loadData2();
        
    });
    
    $("#moldTypeMold").click(function(){
    	loadData2();
    });

    $("#moldCategoryAll").click(function(){
    	loadData3();
	});
	 
	$("#moldCategoryPress").click(function(){
		loadData3();
	});
	 
	$("#moldCategoryMold").click(function(){
		loadData3();
	});
	
	$("#division").change(function(){
		/*사업부 별 데이터 재로딩*/
		//alert($("#division").val());
	});
	
    $("#search").click(function(){
    	var year = $("#yearSetting option:selected").val();
        $("#year").val(year);
        
    	loadData();
        loadData1();
        loadData2();
        loadData3();
        //loadData4();
    });
    
    $("#moldManufacture").click(function(){
        var url = "/plm/ext/dashboard/moldDoughnutChart?year="+$("#year").val()+"&month="+$("#month").val();
        var type = "_blank";
        var option = "width=1140, height=760, toolbar=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no";
        window.open(url,type,option);
    });
    
    $("#annualStartState").click(function(){
    	//alert($("#division option:selected").val());
        var url = "/plm/ext/dashboard/moldMultiColumChart1Popup?searchDate="+$("#yearSetting option:selected").val()+"&division="+$("#division option:selected").val();
        var type = "_blank";
        var option = "width=520, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no";
        window.open(url,type,option);
    });
    
    $("#stepMoldState").click(function(){
    	
    	
    	window.open("","stepMoldStatePopup","width=1200, height=800,scrollbars=yes");
        
        $("#multiChart").attr({action:"/plm/ext/dashboard/moldMakeSituationStatePopup", target:"stepMoldStatePopup", method: "POST"}).submit();
        
        
    	
    	/*  var url = "/plm/ext/dashboard/moldMakeSituationPopup?division="+$("#division option:selected").val();
         var type = "_blank";
         var option = "width=1100, height=750, toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no";
         window.open(url,type,option);  */
    });
    
});

/* function loadData(){
	$('#chartdiv').loadMask('loading...'); 
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumChartData",
        data       : $("#multiChart").serialize(),
        dataType   : "json",
        success    : function(data){
        	        $('#chartdiv').unLoadMask();
                    var chartReference = FusionCharts("ChartId");
                        chartReference.setJSONData(data);
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}
 */
 
 function loadData(){
	    $('#chartdiv').loadMask('loading...'); 
	    $.ajax({
	        type       : "POST",
	        url        : "/plm/ext/dashboard/msStackedColumn2DSetting1",
	        data       : $("#multiChart").serialize(),
	        dataType   : "json",
	        success    : function(data){
	                    $('#chartdiv').unLoadMask();
	                    var chartReference = FusionCharts("ChartId");
	                        chartReference.setJSONData(data);
	        },
	        error    : function(xhr, status, error){
	                     alert(xhr+"  "+status+"  "+error);
	                     
	        }
	    });
 }
 
function loadData1(){
	$('#chartdiv1').loadMask('loading...'); 
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumChartData",
        data       : $("#multiChart").serialize(),
        dataType   : "json",
        success    : function(data){
        	        $('#chartdiv1').unLoadMask();
                    var chartReference = FusionCharts("ChartId1");
                        chartReference.setJSONData(data);
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}

function loadData2(){
	$('#chartdiv2').loadMask('loading...'); 
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumChartData1",
        data       : $("#multiChart").serialize(),
        dataType   : "json",
        success    : function(data){
        	        $('#chartdiv2').unLoadMask();
                    var chartReference = FusionCharts("ChartId2");
                        chartReference.setJSONData(data);
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}

function loadData3(){
	$('#chartdiv3').loadMask('loading...'); 
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumChartData2",
        data       : $("#multiChart").serialize(),
        dataType   : "json",
        success    : function(data){
        	        $('#chartdiv3').unLoadMask();
                    var chartReference = FusionCharts("ChartId3");
                        chartReference.setJSONData(data);
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}

 /* function loadData4(){
	 $('#chartdiv4').loadMask('loading...');
     $.ajax({
         type       : "POST",
         url        : "/plm/ext/dashboard/changeStackColumChartData1",
         data       : $("#multiChart").serialize(),
         dataType   : "json",
         success    : function(data){
        	         $('#chartdiv4').unLoadMask();
        	         var chartReference = FusionCharts("ChartId4");
                         chartReference.setJSONData(data);
         },
         error    : function(xhr, status, error){
                      alert(xhr+"  "+status+"  "+error);
                      
         }
     });
 } */
    


function printTime() {
	   
    var now = new Date();                                                  // 현재시간
    var nowTime = now.getFullYear() + "." + (now.getMonth()+1) + "." + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
    $("#clock").html(nowTime);
    $("#year").val(now.getFullYear());
    $("#month").val((now.getMonth()+1));
   // setTimeout("printTime()",1000);         // setTimeout(“실행할함수”,시간) 시간은1초의 경우 1000
    var standardYear  = Number(now.getFullYear());
    var str ="";
    for(var i=0; i <= 10; i++){
        str += "<option value='"+standardYear+"'>"+standardYear+"</option>";
        standardYear--;
    }
    $("#yearSetting").append(str);
    $("#yearSetting").val(now.getFullYear()).attr("selected", "selected");
    if($("#divison").val() == undefined){
    $("#division").val("total").attr("selected","selected");
    }
}

function test(){
    alert("1");
}

function linkPopUp(type,devType){
	window.open("","projectReportPopup","width=1000, height=700");
	if(type == "lastYear"){
		if(devType == "Mold"){
			$("#devType").val(devType);
			$("#multiChart").attr({action:"/plm/ext/dashboard/lastYearMoldAndPressSetting", target:"projectReportPopup", method: "POST"}).submit(); 	
		}else{
			$("#devType").val(devType);
			$("#multiChart").attr({action:"/plm/ext/dashboard/lastYearMoldAndPressSetting", target:"projectReportPopup", method: "POST"}).submit(); 
		}
	}else{
	    if(devType == "Mold"){
	    	$("#devType").val(devType);
	        $("#multiChart").attr({action:"/plm/ext/dashboard/thisYearMoldAndPressSetting", target:"projectReportPopup", method: "POST"}).submit();    
        }else{
        	$("#devType").val(devType);
        	$("#multiChart").attr({action:"/plm/ext/dashboard/thisYearMoldAndPressSetting", target:"projectReportPopup", method: "POST"}).submit();
        }
	}
}

function linkPopUp1(type,devType){
	window.open("","projectReportPopup","width=1000, height=700");
	if(type == "C" && devType == "P"){
		$("#making").val("Press");
        $("#type").val("COMPLETED");
		$("#multiChart").attr({action:"/plm/ext/dashboard/completeProcessTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
	}else if(type == "P" && devType == "P"){
		$("#making").val("Press");
        $("#type").val("PROGRESS");
		$("#multiChart").attr({action:"/plm/ext/dashboard/completeProcessTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
	}else if(type == "C" && devType == "M"){
		$("#making").val("Mold");
        $("#type").val("COMPLETED");
		$("#multiChart").attr({action:"/plm/ext/dashboard/completeProcessTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "P" && devType == "M"){
    	$("#making").val("Mold");
        $("#type").val("PROGRESS");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/completeProcessTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }
}

function linkPopUp2(type,devType){
	//alert(type+"  "+devType);
    window.open("","projectReportPopup","width=1000, height=700");
    if(type == "T" && devType == "I"){
    	$("#making").val("사내");
    	$("#type").val("");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/makingDataTypeSetting", target:"projectReportPopup", method: "POST"}).submit(); 
    }else if(type == "C" && devType == "I"){
    	   $("#making").val("사내");
           $("#type").val("COMPLETED");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/makingDataTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "P" && devType == "I"){
    	   $("#making").val("사내");
           $("#type").val("PROGRESS");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/makingDataTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "T" && devType == "O"){
    	   $("#making").val("외주");
           $("#type").val("");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/makingDataTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "C" && devType == "O"){
    	   $("#making").val("외주");
           $("#type").val("COMPLETED");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/makingDataTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "P" && devType == "O"){
    	   $("#making").val("외주");
           $("#type").val("PROGRESS");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/makingDataTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }
	
}

function linkPopUp3(type,devType){
	//alert(type+"  "+devType);
	window.open("","projectReportPopup","width=1000, height=700");
	if(type == "T" && devType == "S"){
		$("#making").val("시작");
		$("#type").val("");
		$("#multiChart").attr({action:"/plm/ext/dashboard/startMassTypeSetting", target:"projectReportPopup", method: "POST"}).submit(); 
	}else if(type == "T" && devType == "M"){
		$("#making").val("양산");
		$("#type").val("");
		$("#multiChart").attr({action:"/plm/ext/dashboard/startMassTypeSetting", target:"projectReportPopup", method: "POST"}).submit(); 
	}else if(type == "C" && devType == "S"){
		$("#making").val("시작");
		$("#type").val("COMPLETED");
		$("#multiChart").attr({action:"/plm/ext/dashboard/startMassTypeSetting", target:"projectReportPopup", method: "POST"}).submit(); 
    }else if(type == "C" && devType == "M"){
    	$("#making").val("양산");
    	$("#type").val("COMPLETED");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/startMassTypeSetting", target:"projectReportPopup", method: "POST"}).submit(); 
    }else if(type == "P" && devType == "S"){
    	$("#making").val("시작");
    	$("#type").val("PROGRESS");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/startMassTypeSetting", target:"projectReportPopup", method: "POST"}).submit(); 
    }else if(type == "P" && devType == "M"){
    	$("#making").val("양산");
    	$("#type").val("PROGRESS");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/startMassTypeSetting", target:"projectReportPopup", method: "POST"}).submit(); 
    }
}

function linkPopUp4(type,devType){
	//alert(type+"  "+devType);
	window.open("","projectReportPopup","width=1000, height=700");
	if(type == "T" && devType == "M"){
		$("#making").val("Mold");
        $("#type").val("");
		$("#multiChart").attr({action:"/plm/ext/dashboard/moldPressTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
	}else if(type == "T" && devType == "P"){
		$("#making").val("Press");
        $("#type").val("");
		$("#multiChart").attr({action:"/plm/ext/dashboard/moldPressTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
	}else if(type == "C" && devType == "M"){
		$("#making").val("Mold");
        $("#type").val("COMPLETED");
		$("#multiChart").attr({action:"/plm/ext/dashboard/moldPressTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "C" && devType == "P"){
    	$("#making").val("Press");
        $("#type").val("COMPLETED");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/moldPressTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "P" && devType == "M"){
    	$("#making").val("Mold");
        $("#type").val("PROGRESS");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/moldPressTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }else if(type == "P" && devType == "P"){
    	$("#making").val("Press");
        $("#type").val("PROGRESS");
    	$("#multiChart").attr({action:"/plm/ext/dashboard/moldPressTypeSetting", target:"projectReportPopup", method: "POST"}).submit();
    }
}

function excelDown(){
	//alert("excelDown!!");
	$("#multiChart").attr({action:"/plm/ext/dashboard/excelDown", method:"post"}).submit();
	  /*  $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/excelDown",
        data       : $("#multiChart").serialize(),
        dataType   : "json",
        success    : function(data){
                    alert(data);
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    }); */   
}

</script>