<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.tabIframeWrapper {
    width: 100%;
    height: 330px;
    border: 0px;
    overflow : auto;
}
</style>
<!-- 메세지 Start-->
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- 메세지 End -->
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamWorkTimeReportProject.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamWorkTimeReportTask.js?ver=1.1"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamWorkTimeReportIssue.js"></script> 
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamWorkTimeReportQuality.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamWorkTimeReportPeople.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/teamWorkTimeReportTeam.js"></script>
<!-- EJS TreeGrid End -->
<!-- Fusion Chart Start -->
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>
<!-- Fusion Chart End -->

<form id="multiPieChart">
<input type="hidden" id="userName" name="userName" value=""/>
<input type="hidden" id="status" name="status" value="" />
<input type="hidden" id="displayCode" name="displayCode" value="" />


<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07229") %></div>    
    <div class="current-location text-normal">
        <span><img src="/plm/portal/images/icon_navi.gif" />Home<img src="/plm/portal/images/icon_navi.gif" />DashBoard<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "07229") %></span>
    </div>
               <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <!-- [START] search condition -->
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>  
    <div class="inquiry-table02 clearfix b-space30">
        <div class="float-l" style="position: absolute;  top: 20%; display: table-cell; vertical-align: middle; text-align: center;">
            <!-- 추가요청 사항 -프로젝트 유형별 검색 -->
            <input type="radio" name="pjtType" value="total"/>전체
            <input type="radio" name="pjtType" value="review" />검토
            <input type="radio" name="pjtType" value="product" checked="checked" />제품
            <input type="radio" name="pjtType" value="mold" />금형     
        </div>     
         
        <div class="float-r">
              <!-- 추가요청 사항 -프로젝트 유형별 검색 -->
            <!-- <select id="pjtType" name="pjtType" multiple="multiple">
                    <option value="total">전체</option>
                    <option value="review">검토</option>
                    <option value="product">제품</option>
                    <option value="mold">금형</option>
            </select> -->
            <!-- 날짜 -->
           <input type="text" name="startDate" id="startDate" class="txt_field" style="width: 80px;" value="">
          <!--  <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('startDate');" style="cursor: hand;"> --> 
           ~ 
           <input type="text" name="endDate" id="endDate" class="txt_field" style="width: 80px;">
           <!-- <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endDate');" style="cursor: hand;"></td> -->
            <!-- 사업부 : <span class="r-space15 v-middle">
                       <select id="division" name="division" multiple="multiple">
	                    <option value="total">전체</option>
	                    <option value="elect">전자사업부</option>
	                    <option value="car">자동차사업부</option>
	                   </select>
                  </span> -->
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <%=messageService.getString("e3ps.message.ket_message", "07230") %> : <span class="v-middle">
                    <input type="text" name="departName" id="departName" value="" class="txt_field" readonly="readonly">
                    <input type="hidden" name="deptCode" id="deptCode" value="" /> 
                    <!-- <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png" border="0"></a> -->
                    <a href="javascript:SearchUtil.addAllDepartment('deptCode', 'departName',true);" class="btn_blue">
                            <img src="/plm/portal/images/icon_5.png" border="0">
                    </a>
                    <a href="javascript:CommonUtil.deleteValue('deptCode','departName');" class="btn_blue">
                            <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
               </span>
               <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:teamSearch();" id="teamSearch" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02652") %></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
        <br>
        <div class="float-l"  style="display: table-cell; vertical-align: bottom; text-align: center;">
            <!-- 개발유형 -->
              개발유형 :
            <select id="vDevPattern" name="vDevPattern" class="fm_jmp" style="width: 120px;" multiple="multiple">
                    <option value="DEVPAT01">선진사B/M</option>
                    <option value="DEVPAT02">표준화개발</option>
                    <option value="DEVPAT03">친환경</option>
                    <option value="DEVPAT04">Global제품</option>
                    <option value="DEVPAT05">KETN</option>
                    <option value="DEVPAT06">중국법인</option>
            </select>
            
            <!-- 개발구분 -->
              개발구분 :
            <select id="vDevType" name="vDevType" class="fm_jmp" style="width: 120px;" multiple="multiple">
                    <option value="DEV001">전략개발</option>
                    <option value="DEV002">수주개발</option>
                    <option value="DEV003">연구개발</option>
                    <option value="DEV004">추가금형</option>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <!-- 대표차종 -->
              대표차종 :<input type="text" id="tempcarType" name="tempcarType" class="txt_field"> 
            <input type="hidden" id="carType" name="carType" value="">
            <a href="javascript:onModel('carType');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
            <a href="javascript:CommonUtil.deleteValue('tempcarType','carType');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>   

            <!-- 제품구분 -->
              제품구분 :
            <input type="text" name="productTypeLevel2Name" class="txt_field">
            <input type="hidden" name="level2" value="">
            <a href="javascript:SearchUtil.selectOnePartClaz('level2', 'productTypeLevel2Name','onlyLeaf=Y&openAll=N&depthLevel2=Y');">
            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('level2','productTypeLevel2Name');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
        </div>
    </div>
   
	    <div class="clearfix">
	        <div class="float-l" style="width:22%;margin-right:4%">
	            <div class="sub-title-02">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "03046") %>
	            </div>
	            <div class="clearfix">
	                <div id="chartdiv">
	            </div>
	            </div>
	        </div>
	        <div class="float-l" style="width:22%;margin-right:4%">
	            <div class="sub-title-02">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span> Task 
	            </div>
	            <div class="clearfix">
	                <div id="chartdiv1">
	            </div>
	            </div>
	        </div>
	        <div class="float-l" style="width:22%;margin-right:4%">
	            <div class="sub-title-02">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>CFT요청사항
	            </div>
	            <div class="clearfix">
	                <div id="chartdiv2">
	            </div>
	            </div>
	        </div>
	        <div class="float-l" style="width:22%">
	            <div class="sub-title-02">
	                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "02296") %>
	            </div>
	            <div class="clearfix">
	                <div id="chartdiv3">
	            </div>
	            </div>
	        </div>
	    </div>
   
</div>
 <div id="tabs">
        <div class="tab-part clearfix">
            <ul class="float-l">
                <li><a href="#tabs-1" class="tabref">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03046") %><span id="projectTotalCount"></span></a></li>
                <li><a href="#tabs-2" class="tabref">&nbsp;&nbsp; Task<span id="taskTotalCount"></span></a></li>
                <li><a href="#tabs-3" class="tabref">&nbsp;&nbsp;CFT요청사항<span id="issueTotalCount"></span></a></li>
                <li><a href="#tabs-4" class="tabref">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02296") %><span id="qulityTotalCount"></span></a></li>
                <%-- <li><a href="#tabs-5" class="tabref">&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "07232") %><span id="peopleTotalCount"></span></a></li>
                <li><a href="#tabs-6" class="tabref">&nbsp;&nbsp;팀별현황<span id="teamTotalCount"></span></a></li>  <!-- 차선책 - 팀별현황 추가 --> --%>
            </ul>
        </div>
        <div id="tabs-1">
            <div id="listGrid"  class="tabIframeWrapper"></div>       
        </div>
        <div id="tabs-2">
              <div id="listGrid1" class="tabIframeWrapper"></div>
        </div>
        <div id="tabs-3">
               <div id="listGrid2" class="tabIframeWrapper"></div>
        </div>
        <div id="tabs-4">
                <div id="listGrid3" class="tabIframeWrapper"></div>
        </div>
<!--         <div id="tabs-5">
                <div id="listGrid4" class="tabIframeWrapper"></div>
        </div>
        차선책 - 팀별현황 추가
        <div id="tabs-6">
                <div id="listGrid5" class="tabIframeWrapper"></div>
        </div> -->
   </div>
</form>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#teamSearch").attr("disabled", true);
	$("#teamSearch").attr("style","color:#aaa" );
	
	$("#departName").val("${dashBoardDTO.departName}");
	$("#deptCode").val("${dashBoardDTO.deptCode}");
	//SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
	
	$("[name=startDate]").val("${dashBoardDTO.startDate}");
	$("[name=endDate]").val("${dashBoardDTO.endDate}");
	//$("#division").val("total").attr("selected","selected");
	
	var pjtType = "${dashBoardDTO.pjtType}";
	var tabs = CommonUtil.tabs('tabs');
	tabs.tabs({ heightStyle: "fill" ,});
	$("#tabs").tabs({active : 1 });
	if($("#pjtType").val() == undefined){
	       $("#pjtType").val("total").attr("selected","selected");
	    }
	
	if(pjtType != ''){
		$('input[name=pjtType]').each(function(i) {
	        if ($(this).attr("value") == pjtType) {
	        	$(this).prop("checked",true);
	        }else{
	        	$(this).prop("checked",false);
	        }
	    });
	}
	// 검색조건 css 수정
	$(".inquiry-table02").css("border-top","0px");
	
	// 개발유형
    $("#vDevPattern").multiselect({
        minWidth: 80,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
	
	// 개발구분
    $("#vDevType").multiselect({
        minWidth: 80,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
	
	//대표차종
    SuggestUtil.bind('CARTYPE', 'tempmodel', 'carType');
    //프로젝트유형
	CommonUtil.singleSelect('pjtType',50);
	//날짜
	CalendarUtil.dateInputFormat('startDate','endDate');
	
	
	//CommonUtil.singleSelect('division',100);
	
	var chart = new FusionCharts("/plm/extcore/swf/Pie2D.swf", "ChartId", "105%", "120", "0", "1" );

    chart.render("chartdiv");
    
    loadData();
    
    var chart1 = new FusionCharts("/plm/extcore/swf/Pie2D.swf", "ChartId1", "105%", "120", "0", "1" );

    chart1.render("chartdiv1");
    
    loadData1();
    
    var chart2 = new FusionCharts("/plm/extcore/swf/Pie2D.swf", "ChartId2", "105%", "120", "0", "1" );

    chart2.render("chartdiv2");
    
    loadData2();
    
    var chart3 = new FusionCharts("/plm/extcore/swf/Pie2D.swf", "ChartId3", "105%", "120", "0", "1" );

    chart3.render("chartdiv3");
    
    loadData3();
    
    var pjtTypeVal = $(':radio[name="pjtType"]:checked').val();
    
    teamWorkTimeReportProject.createPaingGrid("${dashBoardDTO.startDate}", "${dashBoardDTO.endDate}", "${dashBoardDTO.deptCode}",pjtTypeVal);
    
    teamWorkTimeReportTask.createPaingGrid("${dashBoardDTO.startDate}", "${dashBoardDTO.endDate}", "${dashBoardDTO.deptCode}", pjtTypeVal);
    
    teamWorkTimeReportIssue.createPaingGrid("${dashBoardDTO.startDate}", "${dashBoardDTO.endDate}", "${dashBoardDTO.deptCode}", pjtTypeVal);

    teamWorkTimeReportQuality.createPaingGrid("${dashBoardDTO.startDate}", "${dashBoardDTO.endDate}", "${dashBoardDTO.deptCode}", pjtTypeVal);  
    
    //teamWorkTimeReportPeople.createPaingGrid("${dashBoardDTO.startDate}", "${dashBoardDTO.endDate}", "${dashBoardDTO.deptCode}");  
    
    /* 차선책 - 팀별현황 추가 */
    //teamWorkTimeReportTeam.createPaingGrid("${dashBoardDTO.startDate}", "${dashBoardDTO.endDate}", "${dashBoardDTO.deptCode}");
    
    /* $(window).resize(function(){
    	var deleteSize = 300;
    	var resizeHeight = $('body').height() - deleteSize;
    	alert(resizeHeight+'px');
    	$('#tabs-1').css('height',resizeHeight+'px');
    	$('#tabs-2').css('height',resizeHeight+'px');
    	$('#tabs-3').css('height',resizeHeight+'px');
    	$('#tabs-4').css('height',resizeHeight+'px');
    	$('#tabs-5').css('height',resizeHeight+'px');
    }); */
    
});
var flag=0;
function loadData(){
	
	$('#chartdiv').loadMask('loading...'); 
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changePieChartData",
        data       : $("#multiPieChart").serialize(),
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
        url        : "/plm/ext/dashboard/changePieChartData1",
        data       : $("#multiPieChart").serialize(),
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
        url        : "/plm/ext/dashboard/changePieChartData2",
        data       : $("#multiPieChart").serialize(),
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
        url        : "/plm/ext/dashboard/changePieChartData3",
        data       : $("#multiPieChart").serialize(),
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

 function teamSearch(){
	 
	 $("#teamSearch").attr("disabled", true);
	 $("#teamSearch").attr("style","color:#aaa" );
	 
	 loadData();
	 loadData1();
	 loadData2();
	 loadData3();
	  
	 teamWorkTimeReportProject.search();
	 teamWorkTimeReportTask.search();
     teamWorkTimeReportIssue.search();
     teamWorkTimeReportQuality.search();  
     //teamWorkTimeReportPeople.search(); 
     //teamWorkTimeReportTeam.search();
 }

 function btnEnabled(){
	 
	 //if(flag == 15){
	 if(flag == 12){
		 $("#teamSearch").attr("disabled", false);
		 $("#teamSearch").attr("style","color:#555" );
		 flag = 0;
	 }
	
 }

 //Pie Chart - Project List Popup 추가 요구사항 
 function linkPopUp(status){
	 $("#status").val(status);
	 window.open("","projectLinkPopUp","width=1000, height=700");
	 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamProjectListPopup", target:"projectLinkPopUp", method: "POST"}).submit();
 }

 //Pie Chart - Task List Popup 추가 요구사항
 function linkPopUp1(status){
	 $("#status").val(status);
	 window.open("","taskLinkPopUp","width=1000, height=700");
     $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamTaskListPopup", target:"taskLinkPopUp", method: "POST"}).submit();
 }
 
 //Pie Chart - Issue List Popup 추가 요구사항
 function linkPopup2(status){
	 $("#status").val(status);
     window.open("","issueLinkPopUp","width=1000, height=700");
     $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamIssueTotalListPopup", target:"issueLinkPopUp", method: "POST"}).submit();
 }
 
 //Pie Chart - Qulity List Popup 추가 요구사항
 function linkPopup3(status){
	 $("#status").val(status);
     window.open("","qulityLinkPopUp","width=1000, height=700");
     $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamQulityTotalListPopup", target:"qulityLinkPopUp", method: "POST"}).submit();
 }
 
 
//차종 가져오기 시작
 var rname_ = "";
 function onModel(rname) {
	 rname_ = rname;
 
	 SearchUtil.selectCarType('','','addModel');

 }

 function addModel(objArr) {
	 
   var rname = rname_;
   if(objArr.length == 0) {
     return;
   }

   var displayName = document.getElementById("temp" + rname);
   var keyName = document.getElementById(rname);
   for(var i = 0; i < objArr.length; i++) {
     infoArr = objArr[i];
     displayName.value = infoArr[1];
     keyName.value = infoArr[0];

   }
 }

 //차종 가져오기 끝
</script>