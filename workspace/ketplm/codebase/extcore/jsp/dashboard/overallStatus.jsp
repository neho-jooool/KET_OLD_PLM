<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>

<form id="overallStatus">
<input type="hidden" id="year" name="year" value=""/>
<input type="hidden" id="month" name="month" value=""/>
<input type="hidden" id="pjtType" name="pjtType" value="2"/>
<input type="hidden" id="pjtType1" name="pjtType1" value="1"/>
<input type="hidden" id="pjtType2" name="pjtType2" value="3"/>
<input type="hidden" id="type" name="type" value="" />
<input type="hidden" id="devType" name="devType" value=""/>
<input type="hidden" id="customer" name="customer" value=""/>
<input type="hidden" id="carType" name="carType" value=""/>
<input type="hidden" id="itemType" name="itemType" value=""/>
<input type="hidden" id="level2" name="level2" value=""/>
<input type="hidden" id="divisionFlag" name="divisionFlag" value="C"/>
<input type="hidden" id="typeDivide" name="typeDivide" value="C" />
<input type="hidden" id="updateDivision" name="updateDivision" value="" />
<input type="hidden" id="sortName" name="sortName" value=""/>

<div class="contents-wrap" style="margin-bottom:0px">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "02664") %></div>  
    <div class="current-location">
        <span><img src="/plm/portal/images/icon_navi.gif" />Home<img src="/plm/portal/images/icon_navi.gif" />DashBoard<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "02664") %></span>
    </div>
    <div class="inquiry-table02 clearfix b-space10">
        <div class="float-r">
            <span class="r-space7 v-middle"><input type="radio" onclick="divisionSetting();" id="division" name="division"  value="car"/></span><%=messageService.getString("e3ps.message.ket_message", "02391") %>
            <span class="r-space7 l-space20 v-middle"><input type="radio" onclick="divisionSetting();" id="division" name="division" value="electon"/></span><%=messageService.getString("e3ps.message.ket_message", "02478") %>
            <span class="l-space20 r-space15"><%=messageService.getString("e3ps.message.ket_message", "07136") %> : <span id="clock"></span></span>
            <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:update();" id="updateSearch" class="btn_blue">Refresh<%-- <%=messageService.getString("e3ps.message.ket_message", "07308") %> --%></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
    </div>
    <div class="clearfix" style="width:100%">
	    <div style="width:960px;margin:0 auto">
	        <div class="section01 float-l">
	            <div class="sub-title-02 float-l">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07309") %>
	            </div>
	            <div style="text-align:right" class="float-r">
	                <span style="display:inline-block;padding-top:5px;font-size:8.5px;"><%=messageService.getString("e3ps.message.ket_message", "07248") %></span>
	            </div> 
	            <div class="clear" id="chartdiv"></div>
	              <!--   <div style="height:160px;width:48%" class="b-space30 float-l" id="chartdiv1"></div> -->
	            <div class="sub-title-02 float-l">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07310") %>
	            </div>
	            <div style="text-align:right" class="float-r">
	                <span style="display:inline-block;padding-top:5px;font-size:8.5px;"><%=messageService.getString("e3ps.message.ket_message", "07248") %></span>
	            </div>
		        <div style="width:100%;" class="b-space10 clear" id="chartdiv3"></div>
		          <!--   <div style="height:160px;width:48%" class="b-space30 float-l" id="chartdiv4"></div> -->
	            <div class="sub-title-02 float-l">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span></span><!-- <span id="currentYear"></span> --> <%=messageService.getString("e3ps.message.ket_message", "07311") %>
	            </div>
	            <div style="text-align:right;" class="float-r">
	                <span style="display:inline-block;padding-top:5px;font-size:8.5px;"><%=messageService.getString("e3ps.message.ket_message", "07314") %></span>
	            </div>
	            <div style="width:100%;" class="clear" id="chartdiv5"></div>
	        </div>
	        <div class="section02 float-l">
	            <div class="float-r" id="carIcon">
	                <span class="r-space7 v-middle"><input type="radio" id="carTypeSort" name="carTypeSort" value="customer"><%=messageService.getString("e3ps.message.ket_message", "00837") %><span id="customerArrow"></span></span>
                    <span class="r-space7 v-middle"><input type="radio" id="carTypeSort" name="carTypeSort" value="sop" checked="checked">SOP<span id="sopArrow">▲</span></span>
                    <span class="r-space7 v-middle"><input type="radio" id="carTypeSort" name="carTypeSort" value="currentGate"><%=messageService.getString("e3ps.message.ket_message", "03210") %><span id="currentGateArrow"></span></span>
	                <a href="javascript:carProjectReport();"><img style="display:inline-block;padding-top:12px;" src="/plm/extcore/image/right_check.png" /></a>
	            </div>
	            <div class="sub-title-02 b-space15">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><span  id="typeName"><%=messageService.getString("e3ps.message.ket_message", "07312") %></span>
	            </div>
	            <div style="width:518px;height:369px;" class="b-space30" id="carTypeByDevMakeReport">
	            </div>
	             <div class="float-r b-space7">
	                <span class="r-space7">년</span><span class="r-space15"><select id="yearSetting" name="yearSetting" multiple="multiple"></select></span>
	                <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02652") %></a></span><span class="pro-cell b-right"></span></span></span>
	            </div>
	            <div class="sub-title-02 float-l">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07313") %>
	            </div>
	            <div style="margin-bottom:10px;width:518px;" id="typeByDevMakeReport">
	            </div>
	            <div><img src="/plm/portal/images/color_mark.gif" /></div>
	        </div>
	        <div class="section03 float-l" >
	            <div class="sub-title-02 float-l">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07315") %>
	            </div>
	            <div style="text-align:right" class="float-r">
	                <span style="display:inline-block;padding-top:5px;font-size:8.5px;"><%=messageService.getString("e3ps.message.ket_message", "07248") %></span>
	            </div>
	            <div class="clear" id="chartdiv2"></div>
	            <div class="sub-title-02 float-l">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07316") %>
	            </div>
	            <div style="text-align:right" class="float-r">
	                <span style="display:inline-block;padding-top:5px;font-size:8.5px;"><%=messageService.getString("e3ps.message.ket_message", "07248") %></span>
	            </div>
	            <div style="width:100%;" class="b-space10 clear" id="chartdiv7"></div>
	            <div class="sub-title-02 float-l">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07317") %>
	            </div>
	            <div style="text-align:right" class="float-r">
                <span style="display:inline-block;padding-top:5px;font-size:8.5px;"><%=messageService.getString("e3ps.message.ket_message", "07248") %></span>
                </div>
	            <div style="text-align:right" class="float-r">
	                <span style="display:inline-block;padding-top:5px;padding-left:15px;font-size:8.5px;"><%=messageService.getString("e3ps.message.ket_message", "08030") %>: <a href="javascript:linkPopup9();"><span style="color:#ff0000;" id="delayCount"></span></a><%=messageService.getString("e3ps.message.ket_message", "00698") %></span>
	            </div>
	            <div style="width:100%;" class="clear" id="chartdiv6"></div>
	        </div>
	    </div>
	 </div>
</div>
</form>

<script type="text/javascript">
var divisionBtn = 0;

$(document).ready(function(){
	
	/* $("[name=division]").click(function(){
		//alert($(':radio[name="division"]:checked').val());
		$("#updateDivision").val($(':radio[name="division"]:checked').val());	
	}); */
	
	
	$("[name=division]").attr("disabled", true);
	
	printTime();
	
	CommonUtil.singleSelect('yearSetting',100);

    /* var updateDivide = "${dashBoardDTO.updateDivision}";
    
    //alert(updateDivide);
    
    if(updateDivide == "electon"){
    	$("[name=division]").attr("checked",true);
          $("[name=division]").attr("disabled",true);
            $("#typeDivide").val("E");
            $("#pjtType").val("4");
             $("#pjtType1").val("0");
            $("#pjtType2").val("5");
            $("#divisionFlag").val("E");
            $("#typeName").text("제품군별 개발 진행현황");
        // 사업부에 따라 현황 높이 유동적
            $("#carTypeByDevMakeReport").height("270");
            prodTypeByDevMakeReprtData();
            typeByDevMakeReportData();
            $("#carIcon").hide();
            loadData();
         // loadData1();
            loadData2();
            loadData3();
            loadData5();
            loadData6();
            loadData7();
    }else{
        carTypeByDevMakeReportData();
    } */
    //carTypeByDevMakeReportData();
	
    /* var chart = new FusionCharts("/plm/extcore/swf/StackedColumn3D.swf", "ChartId", "160", "200", "0", "1" );

    chart.render("chartdiv");
    
    loadData();
    
    var chart1 = new FusionCharts("/plm/extcore/swf/StackedColumn3D.swf", "ChartId1", "160", "200", "0", "1" );

    chart1.render("chartdiv1");
    
    loadData1();  */
    
    var chart = new FusionCharts("/plm/extcore/swf/MSStackedColumn2D.swf", "ChartId", "100%", "200", "0", "1" );

    chart.render("chartdiv");
    
    //loadData();
    
    
    var chart2 = new FusionCharts("/plm/extcore/swf/MSStackedColumn2D.swf", "ChartId2", "100%", "200", "0", "1" );
    chart2.render("chartdiv2");
    
    //loadData2();
    
    var chart3 = new FusionCharts("/plm/extcore/swf/MSStackedColumn2D.swf", "ChartId3", "100%", "190", "0", "1" );
    
    chart3.render("chartdiv3");
    
   // loadData3();
    
    /* var chart4 = new FusionCharts("/plm/extcore/swf/MSColumn2D.swf", "ChartId4", "100%", "100%", "0", "1" );
    
    chart4.render("chartdiv4");
    
    loadData4();  */
    
    var chart6 = new FusionCharts("/plm/extcore/swf/MSStackedColumn2D.swf", "ChartId6", "100%", "200", "0", "1" );
    
    chart6.render("chartdiv6");
    
    //loadData6();  
    
    var chart5 = new FusionCharts("/plm/extcore/swf/MSColumn2D.swf", "ChartId5", "100%", "200", "0", "1" );
    
    chart5.render("chartdiv5");
    
   // loadData5();   
    
    
    var chart7 = new FusionCharts("/plm/extcore/swf/MSStackedColumn2D.swf", "ChartId7", "100%", "190", "0", "1" );
    chart7.render("chartdiv7");
    
    //loadData7();
        
    //typeByDevMakeReportData();
    if("car" == "${dashBoardDTO.division}"){
        $("input:radio[name='division']:radio[value='car']").attr("checked",true)
        divisionSetting();
    }else{
        $("input:radio[name='division']:radio[value='electon']").attr("checked",true)
        divisionSetting();
    }
    
    var sortType =1;
    $("[name=carTypeSort]").click(function(){
    	var column = $("[name=carTypeSort]:checked").val();
    	var temp = "";
        if(sortType){
        	temp = "-"+column;
            sortType = 0;
            if(temp == "-sop"){
                $("#sopArrow").text("▼");
                $("#customerArrow").text("");
                $("#currentGateArrow").text("");
            }else if(temp == "-customer"){
                $("#customerArrow").text("▼");
                $("#sopArrow").text("");
                $("#currentGateArrow").text("");
            }else if(temp == "-currentGate"){
                $("#currentGateArrow").text("▼");
                $("#sopArrow").text("");
                $("#customerArrow").text("");
            }
        }else{
            temp = column;
            sortType = 1;
            if(temp == "sop"){
                $("#sopArrow").text("▲");
                $("#customerArrow").text("");
                $("#currentGateArrow").text("");
            }else if(temp == "customer"){
            	$("#customerArrow").text("▲");
            	$("#sopArrow").text("");
            	$("#currentGateArrow").text("");
            }else if(temp == "currentGate"){
            	$("#currentGateArrow").text("▲");
            	 $("#sopArrow").text("");
            	 $("#customerArrow").text("");
            }
        }
        
        $("#sortName").val(temp);
        carTypeByDevMakeReportData();
    });
    
});


function typeByDevMakeReportData(){
	$('#typeByDevMakeReport').loadMask('loading...');
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/typeByDevMakeReportData",
        data       : $("#overallStatus").serialize(),
        dataType   : "html",
        success    : function(data){
        	              $('#typeByDevMakeReport').unLoadMask();
                          $("#typeByDevMakeReport").html(data);
                          divisionBtn++;
                          if(divisionBtn == 8){
                              $("[name=division]").attr("disabled",false);
                              $("#updateSearch").attr("disabled", false);
                              $("#updateSearch").attr("style","color:#555" );
                              
                              divisionBtn =0;
                          } 
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    }); 
}

function carTypeByDevMakeReportData(){

	$('#carTypeByDevMakeReport').loadMask('loading...');
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/carTypeByDevMakeReportData",
        data       : $("#overallStatus").serialize(),
        dataType   : "html",
        success    : function(data){
                          $('#carTypeByDevMakeReport').unLoadMask();
                          $("#carTypeByDevMakeReport").html(data);
                          divisionBtn++;
                          if(divisionBtn == 8){
                              $("[name=division]").attr("disabled",false);
                             
                              $("#updateSearch").attr("disabled", false);
                              $("#updateSearch").attr("style","color:#555" );
                              
                              divisionBtn =0;
                          } 
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}

function prodTypeByDevMakeReprtData(){
	$('#carTypeByDevMakeReport').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/prodTypeByDevMakeReprtData",
        data       : $("#overallStatus").serialize(),
        dataType   : "html",
        success    : function(data){
                          $('#carTypeByDevMakeReport').unLoadMask();
                          $("#carTypeByDevMakeReport").html(data);
                          divisionBtn++;
                          if(divisionBtn == 8){
                              $("[name=division]").attr("disabled",false);
                              
                              $("#updateSearch").attr("disabled", false);
                              $("#updateSearch").attr("style","color:#555" );
                              
                              divisionBtn = 0;
                          } 
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}

function carTypePopup(carType, customer){
	 window.open("","projectReportPopup","width=1200, height=800,scrollbars=yes");
	   
     $("#carType").val(carType);
     $("#customer").val(customer);
     $("#overallStatus").attr({action:"/plm/ext/dashboard/carTypeCardPopup", target:"projectReportPopup", method: "POST"}).submit();
}

function update(){
	/* $("#updateDivision").val($(':radio[name="division"]:checked').val());  
	var url = "/plm/ext/dashboard/overallStatus";
	$("#overallStatus").attr({action:"/plm/ext/dashboard/overallStatus", method:"POST"}).submit();  */
	printTime();
	
	divisionSetting();
}

/* function loadData(){
	$('#chartdiv').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumOverallStatusChartData",
        data       : $("#overallStatus").serialize(),
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
} */

/*샘플차트 */
function loadData(){
	
    $('#chartdiv').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/msStackedColumn2DSetting",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
                    $('#chartdiv').unLoadMask();
                    var chartReference = FusionCharts("ChartId");
                        chartReference.setJSONData(data);
                        divisionBtn++;
                        if(divisionBtn == 8){
                        	$("[name=division]").attr("disabled",false);
                        	$("#updateSearch").attr("disabled", false);
                            $("#updateSearch").attr("style","color:#555" );
                            
                        	divisionBtn = 0;
                        }      
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });    
}
 

/* function loadData_(){
	$('#chartdiv').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumOverallStatusChartData_",
        data       : $("#overallStatus").serialize(),
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
} */

function loadData1(){
	
	$('#chartdiv1').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumOverallStatusChartData1",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
                    $('#chartdiv1').unLoadMask();
                    var chartReference = FusionCharts("ChartId1");
                        chartReference.setJSONData(data);
                        divisionBtn++;
                        if(divisionBtn == 8){
                            $("[name=division]").attr("disabled",false);
                            $("#updateSearch").attr("disabled", false);
                            $("#updateSearch").attr("style","color:#555" );
                            divisionBtn = 0;
                        }  
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });    
}

/* function loadData1_(){
    $('#chartdiv1').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumOverallStatusChartData1_",
        data       : $("#overallStatus").serialize(),
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
 */


function loadData2(){
	
	$('#chartdiv2').loadMask('loading...');
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumOverallStatusChartData",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
                     $('#chartdiv2').unLoadMask();
                     var chartReference = FusionCharts("ChartId2");
                         chartReference.setJSONData(data);
                         divisionBtn++;
                         if(divisionBtn == 8){
                             $("[name=division]").attr("disabled",false);
                             $("#updateSearch").attr("disabled", false);
                             $("#updateSearch").attr("style","color:#555" );
                             divisionBtn = 0;
                         }  
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  
}

/* function loadData2_(){
    $('#chartdiv2').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumOverallStatusChartData_",
        data       : $("#overallStatus").serialize(),
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
} */

/*  function loadData3(){
	$('#chartdiv3').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumOverallStatusChartData2",
        data       : $("#overallStatus").serialize(),
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
}  */

 function loadData3(){
	
    $('#chartdiv3').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/msStackedColumn2DSetting2",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
                    $('#chartdiv3').unLoadMask();
                    var chartReference = FusionCharts("ChartId3");
                        chartReference.setJSONData(data);
                        divisionBtn++;
                        if(divisionBtn == 8){
                            $("[name=division]").attr("disabled",false);
                            $("#updateSearch").attr("disabled", false);
                            $("#updateSearch").attr("style","color:#555" );
                            divisionBtn = 0;
                        }
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  
} 

/* function loadData3_(){
    $('#chartdiv3').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumOverallStatusChartData2_",
        data       : $("#overallStatus").serialize(),
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
} */

 function loadData4(){
	
	$('#chartdiv4').loadMask('loading...');
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumOverallStatusChartData1",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
        	        $('#chartdiv4').unLoadMask();
                    var chartReference = FusionCharts("ChartId4");
                        chartReference.setJSONData(data);
                        divisionBtn++;
                        if(divisionBtn == 8){
                            $("[name=division]").attr("disabled",false);
                            $("#updateSearch").attr("disabled", false);
                            $("#updateSearch").attr("style","color:#555" );
                            divisionBtn = 0;
                        }
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  
} 

/* function loadData4_(){
    $('#chartdiv4').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumOverallStatusChartData1_",
        data       : $("#overallStatus").serialize(),
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

function loadData5(){
	$("#currentYear").text($("#yearSetting").val());
	$('#chartdiv5').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumOverallStatusChartData2",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
                    $('#chartdiv5').unLoadMask();
                    var chartReference = FusionCharts("ChartId5");
                        chartReference.setJSONData(data);
                        divisionBtn++;
                        if(divisionBtn == 8){
                            $("[name=division]").attr("disabled",false);
                            $("#updateSearch").attr("disabled", false);
                            $("#updateSearch").attr("style","color:#555" );
                            divisionBtn = 0;
                        }  
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  
} 

/* function loadData5(){
    $('#chartdiv5').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumOverallStatusChartData3",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
                    $('#chartdiv5').unLoadMask();
                    var chartReference = FusionCharts("ChartId5");
                        chartReference.setJSONData(data);
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  
} */


function loadData6(){
	
	$('#chartdiv6').loadMask('loading...');
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeMultiColumOverallStatusChartData3",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
        	        $('#chartdiv6').unLoadMask();
                    var chartReference = FusionCharts("ChartId6");
                        chartReference.setJSONData(data);
                        
                    var count = 0;    
                        if(data.delayCount > 0){
                             count = data.delayCount;
                        }else{
                        	count ="";
                        }
                        $("#delayCount").text(count);
                   divisionBtn++;
                   if(divisionBtn == 8){
                       $("[name=division]").attr("disabled",false);
                       $("#updateSearch").attr("disabled", false);
                       $("#updateSearch").attr("style","color:#555" );
                       divisionBtn = 0;
                   }    
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  
}


function loadData7(){
	
    $('#chartdiv7').loadMask('loading...');
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeStackColumOverallStatusChartData",
        data       : $("#overallStatus").serialize(),
        dataType   : "json",
        success    : function(data){
                     $('#chartdiv7').unLoadMask();
                     var chartReference = FusionCharts("ChartId7");
                         chartReference.setJSONData(data);
                         divisionBtn++;
                         if(divisionBtn == 8){
                             $("[name=division]").attr("disabled",false);
                             $("#updateSearch").attr("disabled", false);
                             $("#updateSearch").attr("style","color:#555" );
                             divisionBtn = 0;
                         } 
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  
}

function linkPopUp(type,devType,year){
	window.open("","linkPopUp","width=1000, height=700");
	$("#year").val(year);
	if(type == "lastYear"){
			$("#overallStatus").attr({action:"/plm/ext/dashboard/newAndoldSetting", target:"linkPopUp", method: "POST"}).submit();
	}else{
            $("#overallStatus").attr({action:"/plm/ext/dashboard/newAndoldSetting1", target:"linkPopUp", method: "POST"}).submit();
	}
}

function linkPopUp1(type,devType,year){
    window.open("","linkPopUp1","width=1000, height=700");
    $("#year").val(year);
    if(type == "C"){
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndprocessSetting", target:"linkPopUp1", method: "POST"}).submit();
    }else{
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndprocessSetting1", target:"linkPopUp1", method: "POST"}).submit();
    }
}

function linkPopUp2(type,devType){
	//alert(type+" "+devType);
	window.open("","linkPopUp2","width=1000, height=700");
	if(type == "Y"){
		if(devType == "PRODUCT"){
			$("#devType").val("PRODUCT");
			$("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting", target:"linkPopUp2", method: "POST"}).submit();
		}else if(devType == "MOLD"){
			$("#devType").val("MOLD");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting", target:"linkPopUp2", method: "POST"}).submit();
		}else if(devType == "CUSTOMER"){
			$("#devType").val("CUSTOMER");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "QUALITY"){
        	$("#devType").val("QUALITY");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "COST"){
        	$("#devType").val("COST");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "SCHEDULE"){
        	$("#devType").val("SCHEDULE");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "ETC"){
        	$("#devType").val("ETC");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting", target:"linkPopUp2", method: "POST"}).submit();
        }
	}else{
		if(devType == "PRODUCT"){
            $("#devType").val("PRODUCT");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting1", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "MOLD"){
            $("#devType").val("MOLD");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting1", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "CUSTOMER"){
            $("#devType").val("CUSTOMER");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting1", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "QUALITY"){
            $("#devType").val("QUALITY");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting1", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "COST"){
            $("#devType").val("COST");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting1", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "SCHEDULE"){
            $("#devType").val("SCHEDULE");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting1", target:"linkPopUp2", method: "POST"}).submit();
        }else if(devType == "ETC"){
            $("#devType").val("ETC");
            $("#overallStatus").attr({action:"/plm/ext/dashboard/completeAndnotCompleteSetting1", target:"linkPopUp2", method: "POST"}).submit();
        }
	}
	
}

function linkPopUp3(type,devType,month, year){
	$("#month").val(month);
	$("#year").val(year);
    window.open("","linkPopUp3","width=1000, height=700");
	
    $("#overallStatus").attr({action:"/plm/ext/dashboard/afterAndtwoafterSetting", target:"linkPopUp3", method: "POST"}).submit();
}

function linkPopUp4(type,devType,month,year){
    window.open("","linkPopUp4","width=1000, height=700");
    $("#month").val(month);
    $("#year").val(year);
    if(type == "A"){
	        $("#overallStatus").attr({action:"/plm/ext/dashboard/currentSettingPopup", target:"linkPopUp4", method: "POST"}).submit();
    }else{
            $("#overallStatus").attr({action:"/plm/ext/dashboard/currentSetting1Popup", target:"linkPopUp4", method: "POST"}).submit();
    }
    
}

function linkPopup5(devType){
    window.open("","linkPopup5","width=1000, height=700");
    
    if(devType == "S"){
        $("#devType").val("수주개발");
        $("#overallStatus").attr({action:"/plm/ext/dashboard/prodDevCostReportPopup", target:"linkPopup5", method: "POST"}).submit();
    }else if(devType == "Y"){
        $("#devType").val("연구개발");
        $("#overallStatus").attr({action:"/plm/ext/dashboard/prodDevCostReportPopup", target:"linkPopup5", method: "POST"}).submit();
    }else if(devType == "J"){
        $("#devType").val("전략개발");
           $("#overallStatus").attr({action:"/plm/ext/dashboard/prodDevCostReportPopup", target:"linkPopup5", method: "POST"}).submit();
    }else if(devType == "C"){
        $("#devType").val("추가금형");
        $("#overallStatus").attr({action:"/plm/ext/dashboard/prodDevCostReportPopup", target:"linkPopup5", method: "POST"}).submit();
    }
}

function linkPopup6(type){
    window.open("","linkPopup6","width=1000, height=700");
    
    if(type == "C"){
        $("#devType").val("완료");
        $("#overallStatus").attr({action:"/plm/ext/dashboard/ecrProjectListPopup", target:"linkPopup6", method: "POST"}).submit();
    }else if(type == "P"){
        $("#devType").val("진행");
        $("#overallStatus").attr({action:"/plm/ext/dashboard/ecrProjectListPopup", target:"linkPopup6", method: "POST"}).submit();
    }else {
        $("#devType").val("");
           $("#overallStatus").attr({action:"/plm/ext/dashboard/ecrProjectListPopup", target:"linkPopup6", method: "POST"}).submit();
    }
}

function linkPopup7(type){
    window.open("","linkPopup7","width=1000, height=700");
    
    if(type == "C"){
        $("#devType").val("완료");
        $("#overallStatus").attr({action:"/plm/ext/dashboard/ecoProjectListPopup", target:"linkPopup7", method: "POST"}).submit();
    }else if(type == "P"){
        $("#devType").val("진행");
        $("#overallStatus").attr({action:"/plm/ext/dashboard/ecoProjectListPopup", target:"linkPopup7", method: "POST"}).submit();
    }else {
        $("#devType").val("");
           $("#overallStatus").attr({action:"/plm/ext/dashboard/ecoProjectListPopup", target:"linkPopup7", method: "POST"}).submit();
    }
}

function linkPopup8(type){
	 window.open("","linkPopup8","width=1000, height=700");
	    if(type == "C"){
	        $("#devType").val("활동완료");
	        $("#overallStatus").attr({action:"/plm/ext/dashboard/ecnProjectListPopup", target:"linkPopup8", method: "POST"}).submit();
	    }else if(type == "P"){
	        $("#devType").val("활동수행");
	        $("#overallStatus").attr({action:"/plm/ext/dashboard/ecnProjectListPopup", target:"linkPopup8", method: "POST"}).submit();
	    }else {
	        $("#devType").val("");
	           $("#overallStatus").attr({action:"/plm/ext/dashboard/ecnProjectListPopup", target:"linkPopup8", method: "POST"}).submit();
	    }
}

function linkPopup9(){
    window.open("","linkPopup9","width=1000, height=700");
       
    $("#overallStatus").attr({action:"/plm/ext/dashboard/ecnProjectListDelayPopup", target:"linkPopup9", method: "POST"}).submit();
       
}

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
}

function search(){
	
	var year = $("#yearSetting option:selected").val();
	$("#year").val(year);
	typeByDevMakeReportData();
	loadData5();
	//carTypeByDevMakeReportData();
}

function divisionSetting(){
	printTime();
	
	var division = $(':radio[name="division"]:checked').val();
	if(division == "car"){
		$("[name=division]").attr("disabled",true);
		
		$("#updateSearch").attr("disabled", true);
	    $("#updateSearch").attr("style","color:#aaa" );
		
        $("#typeDivide").val("C");
		$("#carIcon").show();
		$("#typeName").text("<%=messageService.getString("e3ps.message.ket_message", "07312") %>");
		$("#pjtType").val("2");
		$("#pjtType1").val("1");
		$("#pjtType2").val("3");
		$("#divisionFlag").val("C");
		$("#carTypeByDevMakeReport").height("369");
		carTypeByDevMakeReportData();
		typeByDevMakeReportData();
		loadData();
	   // loadData1();
	    loadData2();
	    loadData3();
	    loadData5();
	    loadData6();
	    loadData7();
	}else{
		$("[name=division]").attr("disabled",true);
		
		$("#updateSearch").attr("disabled", true);
	    $("#updateSearch").attr("style","color:#aaa" );
		
		$("#typeDivide").val("E");
		$("#pjtType").val("4");
		 $("#pjtType1").val("0");
	    $("#pjtType2").val("4");
	    $("#divisionFlag").val("E");
		$("#typeName").text("<%=messageService.getString("e3ps.message.ket_message", "07338") %>");
	// 사업부에 따라 현황 높이 유동적
	    $("#carTypeByDevMakeReport").height("270");
		prodTypeByDevMakeReprtData();
		typeByDevMakeReportData();
		$("#carIcon").hide();
		loadData();
	 //	loadData1();
		loadData2();
		loadData3();
		loadData5();
		loadData6();;
		loadData7();
	}
	
}

function typeByTotalProject(type,devType){
	//alert($("#yearSetting option:selected").val());
	$("#year").val($("#yearSetting option:selected").val());
	//alert(type+"  "+devType);
	$("#type").val(type);
	$("#devType").val(devType);
	window.open("","typeByTotalProject","width=1000, height=700");
	if(type == "review" && devType != "전체"){
		$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByTotalProjectReviewSetting", target:"typeByTotalProject", method: "POST"}).submit();
	}else if(type == "product" && devType != "전체"){
		$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByTotalProjectProductSetting", target:"typeByTotalProject", method: "POST"}).submit();
	}else if(type == "mold" && devType != "전체"){
		$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByTotalProjectMoldSetting", target:"typeByTotalProject", method: "POST"}).submit();
	}else if(type == "review" && devType == "전체"){
		$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByTotalProjectTotalSetting", target:"typeByTotalProject", method: "POST"}).submit();
	}else if(type == "product" && devType == "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeByTotalProjectTotalSetting1", target:"typeByTotalProject", method: "POST"}).submit();
    }else if(type == "mold" && devType == "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeByTotalProjectTotalSetting2", target:"typeByTotalProject", method: "POST"}).submit();
    }
	
}


function typeByCompleteProject(type,devType){
	$("#year").val($("#yearSetting option:selected").val());
	$("#type").val(type);
    $("#devType").val(devType);
    window.open("","typeByCompleteProject","width=1000, height=700");
    if(type == "review" && devType != "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeByCompleteProjectReviewSetting", target:"typeByCompleteProject", method: "POST"}).submit();
    }else if(type == "product" && devType != "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeByCompleteProjectProductSetting", target:"typeByCompleteProject", method: "POST"}).submit();
    }else if(type == "mold" && devType != "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeByCompleteProjectMoldSetting", target:"typeByCompleteProject", method: "POST"}).submit();
    }else if(type == "review" && devType == "전체"){
    	$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByCompleteProjectTotalSetting", target:"typeByCompleteProject", method: "POST"}).submit();
    }else if(type == "product" && devType == "전체"){
    	$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByCompleteProjectTotalSetting1", target:"typeByCompleteProject", method: "POST"}).submit();
    }else if(type == "mold" && devType == "전체"){
    	$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByCompleteProjectTotalSetting2", target:"typeByCompleteProject", method: "POST"}).submit();
    }
}

function typeByProcessProject(type,devType){
	$("#year").val($("#yearSetting option:selected").val());
	$("#type").val(type);
    $("#devType").val(devType);
	window.open("","typeByProcessProject","width=1000, height=700");
    if(type == "review" && devType != "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeBypProcessProjectReviewSetting", target:"typeByProcessProject", method: "POST"}).submit();
    }else if(type == "product" && devType != "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeByProcessProjectProductSetting", target:"typeByProcessProject", method: "POST"}).submit();
    }else if(type == "mold" && devType != "전체"){
        $("#overallStatus").attr({action:"/plm/ext/dashboard/typeByProcessProjectMoldSetting", target:"typeByProcessProject", method: "POST"}).submit();
    }else if(type == "review" && devType == "전체"){
    	$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByProcessProjectTotalSetting", target:"typeByProcessProject", method: "POST"}).submit();
    }else if(type == "product" && devType == "전체"){
    	$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByProcessProjectTotalSetting1", target:"typeByProcessProject", method: "POST"}).submit();
    }else if(type == "mold" && devType == "전체"){
    	$("#overallStatus").attr({action:"/plm/ext/dashboard/typeByProcessProjectTotalSetting2", target:"typeByProcessProject", method: "POST"}).submit();
    }
}


function productTotalProjectPopup(customer, carType){
    $("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productTotalProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productTotalProjectSettingPopup", target:"productTotalProjectPopup", method: "POST"}).submit();
    
}

function productCompleteProjectPopup(customer, carType){
	$("#customer").val(customer);
	$("#carType").val(carType);
	window.open("","productCompleteProjectPopup","width=1000, height=700");
	$("#overallStatus").attr({action:"/plm/ext/dashboard/productCompleteProjectSettingPopup", target:"productCompleteProjectPopup", method: "POST"}).submit();
	
}

function productProcessProjectPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productProcessProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productProcessProjectSettingPopup", target:"productProcessProjectPopup", method: "POST"}).submit();
}

function productDelayProjectPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productDelayProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productDelayProjectSettingPopup", target:"productDelayProjectPopup", method: "POST"}).submit();
}

function productDelayProjectSettingPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productDelayProjectSettingPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productDelayProjectSettingPopup", target:"productDelayProjectSettingPopup", method: "POST"}).submit();	
}


function moldTotalProjectPopup(customer, carType){
    $("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","moldTotalProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/moldTotalProjectSettingPopup", target:"moldTotalProjectPopup", method: "POST"}).submit();
}


function moldCompleteProjectPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","moldCompleteProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/moldCompleteProjectSettingPopup", target:"moldCompleteProjectPopup", method: "POST"}).submit();
}

function moldProcessProjectPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","moldProcessProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/moldProcessProjectSettingPopup", target:"moldProcessProjectPopup", method: "POST"}).submit();
}

function moldDelayProjectPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","moldDelayProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/moldDelayProjectSettingPopup", target:"moldDelayProjectPopup", method: "POST"}).submit();
}

function issueProjectPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","issueProjectPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/issueProjectSettingPopup", target:"issueProjectPopup", method: "POST"}).submit();
}

function quiltyProblemPopup(customer, carType){
    $("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","quiltyProblemPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/quiltyProblemSettingPopup", target:"quiltyProblemPopup", method: "POST"}).submit();
}


/* function test(){
	alert("1");
} */

function carProjectReport(){
	
   /*  $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/carDivisionProjectReportPopup",
        data       : $("#overallStatus").serialize(),
        dataType   : "html",
        success    : function(data){
                          alert(data);
                         // $("#typeByDevMakeReport").html(data);
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });  */
	
	window.open("","carDivisionProjectReport","width=1200, height=700, scrollbars=yes");
	$("#overallStatus").attr({action:"/plm/ext/dashboard/carDivisionProjectPopup", target:"carDivisionProjectReport", method: "POST"}).submit();
}

function productTotalTaskPopup(customer, carType){
    $("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productTotalTaskPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productTotalTaskPopup", target:"productTotalTaskPopup", method: "POST"}).submit();
}


function productCompleteTaskPopup(customer, carType){
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productCompleteTaskPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productCompleteTaskPopup", target:"productCompleteTaskPopup", method: "POST"}).submit();
}

function productProcessTaskPopup(customer, carType){
    $("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productProcessTaskPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productProcessTaskPopup", target:"productProcessTaskPopup", method: "POST"}).submit();
}

function productDelayTaskPopup(customer, carType){
    $("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","productDelayTaskPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/productDelayTaskPopup", target:"productDelayTaskPopup", method: "POST"}).submit();
}

function itemMoldListPopup(customer, carType, itemType){
	$("#itemType").val(itemType);
	$("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","itemMoldListPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/itemMoldListPopup", target:"itemMoldListPopup", method: "POST"}).submit();
}

function itemGoodsListPopup(customer, carType, itemType){
    $("#itemType").val(itemType);
    $("#customer").val(customer);
    $("#carType").val(carType);
    window.open("","itemGoodsListPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/itemGoodsListPopup", target:"itemGoodsListPopup", method: "POST"}).submit();
}

function devQulityProblemPopup(type,level2){
	if("F" == type){
		$("#type").val("기능불량");
	}else if("O" == type){
		$("#type").val("외관불량");
	}else if("J" == type){
        $("#type").val("조립불량");
    }else if("M" == type){
        $("#type").val("관리불량");
    }
	
	if("I" == level2){
        $("#level2").val("사내");
    }else if("C" == level2){
        $("#level2").val("고객");
    }else if("O" == level2){
        $("#level2").val("외주");
    }
	
	window.open("","devQulityProblemPopup","width=1000, height=700");
    $("#overallStatus").attr({action:"/plm/ext/dashboard/devQulityProblemPopup", target:"devQulityProblemPopup", method: "POST"}).submit();
}

function prodTypeReviewTotalPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeReviewTotalPopup","width=1050, height=700");
	 $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeReviewTotalPopup", target:"prodTypeReviewTotalPopup", method: "POST"}).submit();
}
 
function prodTypeReviewCompPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeReviewCompPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeReviewCompPopup", target:"prodTypeReviewCompPopup", method: "POST"}).submit();
}
 
function prodTypeReviewProcPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeReviewProcPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeReviewProcPopup", target:"prodTypeReviewProcPopup", method: "POST"}).submit();
}

function prodTypeProdTotalPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeProdTotalPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeProdTotalPopup", target:"prodTypeProdTotalPopup", method: "POST"}).submit();
}
function prodTypeProdCompPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeProdCompPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeProdCompPopup", target:"prodTypeProdCompPopup", method: "POST"}).submit();
}
function prodTypeProdProcPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeProdProcPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeProdProcPopup", target:"prodTypeProdProcPopup", method: "POST"}).submit();
}
function prodTypeMoldTotalPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeMoldTotalPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeMoldTotalPopup", target:"prodTypeMoldTotalPopup", method: "POST"}).submit();
}
function prodTypeMoldCompPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeMoldCompPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeMoldCompPopup", target:"prodTypeMoldCompPopup", method: "POST"}).submit();
}
function prodTypeMoldProcPopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeMoldProcPopup","width=1050, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeMoldProcPopup", target:"prodTypeMoldProcPopup", method: "POST"}).submit();
}
function prodTypeIssuePopup(level2){
	 $("#level2").val(level2);
	 window.open("","prodTypeIssuePopup","width=1000, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeIssuePopup", target:"prodTypeIssuePopup", method: "POST"}).submit();
}
function prodTypeQulityPopup(level2){
	 $("#level2").val(level2); 
	 window.open("","prodTypeQulityPopup","width=1000, height=700");
     $("#overallStatus").attr({action:"/plm/ext/dashboard/prodTypeQulityPopup", target:"prodTypeQulityPopup", method: "POST"}).submit();
}

</script>
