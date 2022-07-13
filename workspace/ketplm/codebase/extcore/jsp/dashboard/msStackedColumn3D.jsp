<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>
<form id="test">
<div id="chartdiv"></div>
</form>

<script type="text/javascript">
$(document).ready(function(){
	
	var chart = new FusionCharts("/plm/extcore/swf/MSStackedColumn2D.swf", "ChartId", "400", "400", "0", "1" );

    chart.render("chartdiv");
    
    loadData();
	
});


function loadData(){
    $('#chartdiv').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/msStackedColumn3DSetting",
        data       : $("#test").serialize(),
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
</script>