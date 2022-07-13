<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>


<body class="popup-background popup-space">
<form id="annualStartState">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07196") %></div>
<div align="right"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>   
<div>
    <div id="chartdiv" style="float: left;"></div>
    <input type="hidden" name="year" value="${searchDate}"/>
    <input type="hidden" name="division" value="${division}"/>
</div>
</form>
</body>
<script type="text/javascript">

$(document).ready(function(){

	 var chart = new FusionCharts("/plm/extcore/swf/MSColumn2D.swf", "ChartId", "441", "300", "0", "1" );

	    chart.render("chartdiv");
	    
	    loadData();
});



function loadData(){
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumChartData3",
        data       : $("#annualStartState").serialize(),
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

</script>