<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>


<div id="chartdiv" align="center"></div>
<div id="chartdiv1" align="center"></div>
<div id="chartdiv2" align="center"></div>


<script type="text/javascript">

$(document).ready(function(){
    
	var chart = new FusionCharts("/plm/extcore/swf/StackedColumn3D.swf", "ChartId", "300", "300", "0", "1" );

    chart.render("chartdiv");
    
    loadData();
    
    var chart1 = new FusionCharts("/plm/extcore/swf/StackedColumn3D.swf", "ChartId1", "300", "300", "0", "1" );

    chart1.render("chartdiv1");
    
    loadData1();
    
    var chart2 = new FusionCharts("/plm/extcore/swf/StackedColumn3D.swf", "ChartId2", "300", "300", "0", "1" );

    chart2.render("chartdiv2");
    
    loadData2();
});



function loadData(){
    
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/electronChangeStackColumChartData",
        data       : $("#canttChart").serialize(),
        dataType   : "json",
        success    : function(data){
                    var chartReference = FusionCharts("ChartId");
                        chartReference.setJSONData(data);
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });    
}

function loadData1(){
    
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/electronChangeStackColumChartData1",
        data       : $("#canttChart").serialize(),
        dataType   : "json",
        success    : function(data){
                    var chartReference = FusionCharts("ChartId1");
                        chartReference.setJSONData(data);
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });    
}

function loadData2(){
    
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/electronChangeStackColumChartData2",
        data       : $("#canttChart").serialize(),
        dataType   : "json",
        success    : function(data){
                    var chartReference = FusionCharts("ChartId2");
                        chartReference.setJSONData(data);
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });    
}
</script>