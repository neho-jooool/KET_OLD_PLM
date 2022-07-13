<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- 신규 html -->

<body class="popup-background popup-space">
<form id="moldManufacture">
<input type="hidden" id="year" name="year" value="" />
<input type="hidden" id="month" name="month" value="" />
<input type="hidden" id="outsourcing" name="outsourcing" value="" />
<input type="hidden" id="itemType" name="itemType" value="" />
<input type="hidden" id="moldType" name="moldType" value="" />
<div class="contents-wrap">
    <div class="sub-title t-space20" style="border-bottom:none;"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07213") %></div>
    <div align="right" class="b-space10"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div> 
    <div class="inquiry-table02 clearfix b-space30">
        <div class="float-l">
             <span class="text-normal"><span class="l-space40 r-space5"><input type="radio" id="moldTypeAll" name="moldTypeCheck" value="Total" class="v-text-bottom" checked="checked"/></span><%=messageService.getString("e3ps.message.ket_message", "02485") %><span class="l-space10 r-space5"><input type="radio" id="moldTypePress" name="moldTypeCheck" value="Press" class="v-text-bottom" /></span>Press<span class="l-space10 r-space5"><input type="radio" id="moldTypeMold" name="moldTypeCheck" value="Mold" class="v-text-bottom" /></span>Mold</span>
        </div>
        <div class="float-r">
            <%=messageService.getString("e3ps.message.ket_message", "01175") %> <span class="r-space15 v-middle">
                <select id="yearSetting" name="yearSetting" multiple="multiple">
                </select>
             </span>
            <%=messageService.getString("e3ps.message.ket_message", "02224") %> <span class="r-space15 v-middle">
                <select id="monthSetting" name="monthSetting" multiple="multiple">
                               <option>1</option>
                               <option>2</option>
                               <option>3</option>
                               <option>4</option>
                               <option>5</option>
                               <option>6</option>
                               <option>7</option>
                               <option>8</option>
                               <option>9</option>
                               <option>10</option>
                               <option>11</option>
                               <option>12</option>
                            </select>
             </span>
      <%=messageService.getString("e3ps.message.ket_message", "01662") %> : <span class="r-space15 v-middle">
                <select id="division" name="division" multiple="multiple">
                    <option value="total"><%=messageService.getString("e3ps.message.ket_message", "02485") %></option>
                    <option value="elect"><%=messageService.getString("e3ps.message.ket_message", "02483") %></option>
                    <option value="car"><%=messageService.getString("e3ps.message.ket_message", "02401") %></option>
                </select></span>
            <span class="in-block v-middle r-space10"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02652") %></a></span><span class="pro-cell b-right"></span></span></span><!-- <span class="r-space20"><img src="/plm/portal/images/excel.png" /></span> -->
        </div>
    </div>
    <div class="clearfix">
        <div class="float-l" style="width:46%;margin-right:8%">
            <div class="sub-title-02">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>Mold
            </div>
            <div class="clearfix">
                <div id="chartdiv">
            </div>
            </div>
        </div>
        <div class="float-l" style="width:46%">
            <div class="sub-title-02">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>Press
            </div>
            <div id="chartdiv1">
            </div>
        </div>
    </div>
    <div  id="moldManufactureList" style="height:363px;width:1088px">
    </div>
</div>
</form>
</body>

<script type="text/javascript">

$(document).ready(function(){
	
	
	printTime();
	
	
	CommonUtil.singleSelect('yearSetting',100);
	CommonUtil.singleSelect('monthSetting',100);
	CommonUtil.singleSelect('division',100);
	
    
    var chart = new FusionCharts("/plm/extcore/swf/Doughnut2D.swf", "ChartId", "500px", "300px", "0", "1" );

    chart.render("chartdiv");
    
    loadData();
    
    var chart1 = new FusionCharts("/plm/extcore/swf/Doughnut2D.swf", "ChartId1", "500px", "300px", "0", "1" );

    chart1.render("chartdiv1");
    
    loadData1();
    
    moldManufactureList();
    
});

function printTime() {
    
    var now = new Date();                                                  // 현재시간
    var nowTime = now.getFullYear() + "." + (now.getMonth()+1) + "." + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
    $("#clock").html(nowTime);
    $("#year").val(now.getFullYear());
    $("#month").val((now.getMonth()+1));
   // setTimeout("printTime()",1000);         // setTimeout(“실행할함수”,시간) 시간은1초의 경우 1000
    var standardYear  = Number(now.getFullYear()) - 10;
    var str ="";
    for(var i=0; i <= 10; i++){
        str += "<option value='"+standardYear+"'>"+standardYear+"</option>";
        standardYear++;
    }
    $("#yearSetting").append(str);
    $("#yearSetting").val(now.getFullYear()).attr("selected", "selected");
    $("#monthSetting").val(now.getMonth()+1).attr("selected", "selected");
    $("#division").val("total").attr("selected","selected");
}

function loadData(){
	$('#chartdiv').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeDoughnutChartData",
        data       : $("#moldManufacture").serialize(),
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
        url        : "/plm/ext/dashboard/changeDoughnutChartData1",
        data       : $("#moldManufacture").serialize(),
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

function moldManufactureList(){
	$('#moldManufactureList').loadMask('loading...');
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/moldManufactureList",
        data       : $("#moldManufacture").serialize(),
        dataType   : "html",
        success    : function(data){
        	          $('#moldManufactureList').unLoadMask();
                      $("#moldManufactureList").html(data);
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}

function linkPopUp(type,outsourcing){
	alert(type+" "+outsourcing);
	/* $("#outsourcing").val(outsourcing);
	window.open("","projectReportPopup","width=1000, height=500");
	$("#moldManufacture").attr({action:"/plm/ext/dashboard/manufactureListSetting", target:"projectReportPopup", method: "POST"}).submit(); */
	
}

function projectPopUp(itemType, outsourcing){
	//alert(itemType+"  "+$("[name=moldTypeCheck]:checked").val());
	if(itemType == ""){
		 if($("[name=moldTypeCheck]:checked").val() == "Total"){
			 $("#itemType").val(itemType);
		 }else{
			 $("#itemType").val($("[name=moldTypeCheck]:checked").val()); 
		 }
	}else{
		   $("#itemType").val(itemType);
    }
	$("#outsourcing").val(outsourcing);
	window.open("","projectPopup","width=1000, height=700");
	$("#moldManufacture").attr({action:"/plm/ext/dashboard/manufactureProjectListSettingPopup", target:"projectPopup", method: "POST"}).submit();
}

function moldStepPopUp(itemType, outsourcing, moldType){
	$("#itemType").val(itemType);
    $("#outsourcing").val(outsourcing);
    $("#moldType").val(moldType);
    window.open("","projectPopup","width=1000, height=700");
    $("#moldManufacture").attr({action:"/plm/ext/dashboard/moldTypeProjectListSettingPopup", target:"projectPopup", method: "POST"}).submit();
}

function moldStepPopUp1(itemType, outsourcing){
	$("#itemType").val(itemType);
    $("#outsourcing").val(outsourcing);
    window.open("","projectPopup","width=1000, height=700");
    $("#moldManufacture").attr({action:"/plm/ext/dashboard/moldTypeDebugProjectListSettingPopup", target:"projectPopup", method: "POST"}).submit();
}

function moldIssuePopUp(itemType, outsourcing){
	$("#itemType").val(itemType);
    $("#outsourcing").val(outsourcing);
    window.open("","projectPopup","width=1000, height=700");
    $("#moldManufacture").attr({action:"/plm/ext/dashboard/moldIssueProjectListSettingPopup", target:"projectPopup", method: "POST"}).submit();
}

function projectTotalStep(itemType, outsourcing){
	$("#itemType").val(itemType);
    $("#outsourcing").val(outsourcing);
    window.open("","projectPopup","width=1000, height=700");
    $("#moldManufacture").attr({action:"/plm/ext/dashboard/moldProjectTotalStepListSettingPopup", target:"projectPopup", method: "POST"}).submit();
}

function search(){
	var year = $("#yearSetting option:selected").val();
    var month = $("#monthSetting option:selected").val();
    $("#year").val(year);
    $("#month").val(month);
    loadData();
    loadData1();
    moldManufactureList();
}
</script>