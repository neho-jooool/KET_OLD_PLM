<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/plm/extcore/js/dashboard/FusionCharts.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- 신규 html-->
<form id="canttChart">
<div class="contents-wrap">
    
    <input type="hidden" id="page" name="page" value="1"/>
    <input type="hidden" id="sortName" name="sortName" value=""/>
    <input type="hidden" id="year" name="year" value=""/>
    <input type="hidden" id="month" name="month" value=""/>
    
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07217") %></div> 
    <div class="current-location text-normal">
        <span><img src="/plm/portal/images/icon_navi.gif" />Home<img src="/plm/portal/images/icon_navi.gif" />DashBoard<img src="/plm/portal/images/icon_navi.gif" /><%=messageService.getString("e3ps.message.ket_message", "07217") %></span>
    </div>
    <div class="b-space10">
        <table class="table-style-01" cellpadding="0" summary="">
            <colgroup>
                <col width="8%" />
                <col width="12%" />
                <col width="8%" />
                <col width="12%" />
                <col width="8%" />
                <col width="12%" />
                <col width="8%" />
                <col width="19%" />
            </colgroup>
            <tbody>
                <tr>
                    <td colspan="8" class="search-title-01">
                        <span class="r-space7"><input type="radio" id="mBtn" name="termType" value="M" checked="checked" /><%=messageService.getString("e3ps.message.ket_message", "01175") %></span>
                        <span class="r-space7"><input type="radio" id="wBtn" name="termType" value="W" /><%=messageService.getString("e3ps.message.ket_message", "07218") %></span>
                        <span class="r-space7"><input type="radio" id="dBtn" name="termType" value="D" /><%=messageService.getString("e3ps.message.ket_message", "02224") %></span>
                        <span class="r-space20">
                           <select id="yearSetting" name="yearSetting" multiple="multiple"></select>
                           <select id="monthSetting" name="monthSetting" multiple="multiple" disabled="disabled">
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
                        <span class="r-space20"><%=messageService.getString("e3ps.message.ket_message", "01662") %> : 
	                        <select id="division" name="division">
			                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %></option>
			                    <option value="4"><%=messageService.getString("e3ps.message.ket_message", "02483") %></option>
			                    <option value="3"><%=messageService.getString("e3ps.message.ket_message", "02401") %></option>
			                </select>
                        </span>
                        <span class="r-space20">Die No:
                        <input type="text" id="dieNo3" name="dieNo3" value="" class="txt_field" style="width: 88px;">
                        <a href="javascript:selectPartNo('dieNo3');"><img src="/plm/portal/images/icon_5.png"></a>
                        <a href="javascript:clearPartNo('dieNo3');"><img src="/plm/portal/images/icon_delete.gif"></a>
                        </span>
                        <%-- <span class="r-space10"><%=messageService.getString("e3ps.message.ket_message", "07136") %> : <span id="clock"></span></span>
                        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="#" class="btn_blue" id="updateRefresh"><%=messageService.getString("e3ps.message.ket_message", "07308") %></a></span><span class="pro-cell b-right"></span></span></span> --%>
                    </td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01051") %></td>
                    <td>
                    <select id="moldType" name="vMoldType" multiple="multiple">
				        <option value="Mold">Mold</option>
				        <option value="Press">Press</option>
				    </select>
				    </td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02532") %></td>
                    <td>
                    <select id="making" name="vMaking" multiple="multiple" >
				        <option value="사내"><%=messageService.getString("e3ps.message.ket_message", "01655") %></option>
				        <option value="외주"><%=messageService.getString("e3ps.message.ket_message", "02184") %></option>
				    </select>
                    </td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01094") %></td>
                    <td>
                    <select id="moldCategory" name="vMoldCategory" multiple="multiple">
				        <option value="시작"><%=messageService.getString("e3ps.message.ket_message", "07219") %></option>
				        <option value="양산"><%=messageService.getString("e3ps.message.ket_message", "07220") %></option>
				        <option value="시작Mo"><%=messageService.getString("e3ps.message.ket_message", "07221") %></option>
				        <option value="양산Mo"><%=messageService.getString("e3ps.message.ket_message", "07222") %></option>
				        <option value="시작Fa"><%=messageService.getString("e3ps.message.ket_message", "07223") %></option>
				        <option value="양산Fa"><%=messageService.getString("e3ps.message.ket_message", "07224") %></option>
				    </select>
                    </td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07225") %></td>
                    <td>
                        <select id="taskName" name="vTaskCategory" multiple="multiple">
	                        <option value="금형설계"><%=messageService.getString("e3ps.message.ket_message", "01085") %></option>
	                        <option value="금형부품"><%=messageService.getString("e3ps.message.ket_message", "01064") %></option>
	                        <option value="금형조립"><%=messageService.getString("e3ps.message.ket_message", "07226") %></option>
	                        <option value="금형Try_[T0]"><%=messageService.getString("e3ps.message.ket_message", "07227") %></option>
	                        <option value="디버깅단계"><%=messageService.getString("e3ps.message.ket_message", "07228") %></option>
	                        <option value="금형검수"><%=messageService.getString("e3ps.message.ket_message", "01049") %></option>
	                        <option value="외주금형제작"><%=messageService.getString("e3ps.message.ket_message", "02187") %></option>
	                      <%--   <option value="금형입고"><%=messageService.getString("e3ps.message.ket_message", "01097") %></option> --%>
                        </select>
                        <span class="in-block v-middle l-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="#" class="btn_blue" id="search"><%=messageService.getString("e3ps.message.ket_message", "02652") %></a></span><span class="pro-cell b-right"></span></span></span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div id="chartdiv" style="width:100%;height:100%"></div>
    <!-- <div id="totalPageDiv" align="center" class="b-space10"></div> -->
    <div id="pagingDiv" align="center"></div>
</div>
</form>

<script type="text/javascript">

$(document).ready(function(){
	
	
	printTime();
	
	SuggestUtil.bind('DIENO', 'dieNo3');
	
	CommonUtil.singleSelect('yearSetting',70);
	CommonUtil.singleSelect('monthSetting',50);
	CommonUtil.singleSelect('division',60);

	var $moldType = $('#moldType');
	$moldType.multiselect();   // Initialize
	$moldType.multiselect("checkAll");  // CheckAll
	$moldType.multiselect({minWidth: 110});
	
	
	var $making = $('#making');
    $making.multiselect();   // Initialize
    $making.multiselect("checkAll");  // CheckAll
    $making.multiselect({minWidth: 110});
    
    var $moldCategory = $('#moldCategory');
    $moldCategory.multiselect();   // Initialize
    $moldCategory.multiselect("checkAll");  // CheckAll
    $moldCategory.multiselect({minWidth: 110});  // CheckAll
    
    var $taskName = $('#taskName');
    $taskName.multiselect();   // Initialize
    $taskName.multiselect("checkAll");  // CheckAll
    $taskName.multiselect({minWidth: 110});  // CheckAll
    
    
    
    var chart = new FusionCharts("/plm/extcore/swf/Gantt.swf", "ChartId", "960", "540", "0", "1" );

    chart.render("chartdiv");
    
    loadData(1, true);
    
    $("#mBtn").click(function(){
    	$("#monthSetting").multiselect("disable");
    	
    });
    
    $("#wBtn").click(function(){
    	$("#monthSetting").multiselect("enable");
    });
    
    $("#dBtn").click(function(){
    	$("#monthSetting").multiselect("enable");
    });
    
    $("#search").click(function(){
    	var year = $("#yearSetting option:selected").val();
        $("#year").val(year);
        var month = $("#monthSetting option:selected").val();
    	$("#month").val(month);
        loadData(1,true);
    });
    
    $("#importantPjtCount").click(function(){
    	var now = new Date();                                                  // 현재시간
    	var nowTime = now.getFullYear() + "-" + (now.getMonth()+1) + "-" + now.getDate();
    	var url = "/plm/ext/dashboard/mainScheduleProgressPjtCount?searchDate="+nowTime.toString();
    	var type = "_blank";
    	var option = "width=800, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no";
    	window.open(url,type,option);
    });
    
    $("#updateRefresh").click(function(){
    	window.location.reload(true);
    });
    
    
 });
 
function loadData(page, type){
	
	$('#chartdiv').loadMask('loading...'); 
	
	$("#page").val(page);
    $("a").css("color","");
    $("#page"+page).css("color","red");
    $("#pageNum").html(page);
    pageNum = page; 
    
	$.ajax({
        type       : "POST",
        url        : "/plm/ext/dashboard/changeGanttChartData",
        data       : $("#canttChart").serialize(),
        dataType   : "json",
        success    : function(data){
        	        $('#chartdiv').unLoadMask();
                    var chartReference = FusionCharts("ChartId");
                        chartReference.setJSONData(data);
                        groupPageCount=20;
                         totalPageCount = data.totalPageCount;
                        var divide = totalPageCount % groupPageCount
                        var pagecount = totalPageCount / groupPageCal;
                        if(divide < groupPageCount) {pagecount++;}
                        else{pagecount;}
                        
                        totalPageCount = parseInt(pagecount);
                       
                        if(type){pagingShow();} 
                        hideProcessing();
                        
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
}
 
function printTime() {
   
    var now = new Date();                                                  // 현재시간
    var nowTime = now.getFullYear() + "." + (now.getMonth()+1) + "." + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
    //$("#clock").html(nowTime);
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
    $("#monthSetting").val(now.getMonth()+1).attr("selected", "selected");
}


function linkPopUp(oid){
	//alert(oid);
	///plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value=e3ps.project.ReviewProject:841332699&key=popup&value=popup
	 var url = "/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value="+oid;
	    openOtherName(url, "process", "950", "550", "status=no,scrollbars=yes,resizable=no");
}

var sortType =1;
function sorting(column){
	var temp = "";
	if(sortType){
		temp = column;
		sortType = 0;
	}else{
		temp = "-"+column;
		sortType = 1;
	}
	
	$("#sortName").val(temp);
	loadData(pageNum,false);
	
	
}

function viewPart(v_poid){
	 
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=wt.part.WTPart:" + v_poid;
 //   openWindow(url,"","750","650","scrollbars=no,resizable=no,top=200,left=250");
    // 2014.06.12 tklee IE 11에서 잘림
    // 사이즈 조절함    
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
 }


var pageNum = 1; // 페이지 카운트 변수
var totalPageCount = 0; // 총페이지수
var groupPageCount = 20; // 한 화면에 보여줄 페이지수
var groupPageCal = 25; //한 화면에 보여줄  Row수
var groupCount = 1;
var pageCount =1;
var groupPageNum = 1;


function pagingShow(){
    
	var html = '';
	var pagehtml = '';
	   
	    html  = "<a href='javascript:beforePageGroup();'><img src='/plm/portal/images/ico_left_go.gif' /></a>&nbsp&nbsp";
	    html += "<a href='javascript:beforePage();'><img src='/plm/portal/images/ico_left_go2.gif' /></a>&nbsp;&nbsp&nbsp;&nbsp";
	    //html += "<span id=\"pageNum\">"+pageCount+"</span>/"+totalPageCount;
	    if(totalPageCount <= groupPageCount ) {groupPageCount = totalPageCount}
	    else{groupPageCount=20}
	    for(var i=1; i <= groupPageCount; i++){
	        if(i == 1){html += "<a href='javascript:loadData("+i+",false)' id='page"+i+"'; style='color:red'>"+i+"</a>&nbsp"}
	        else{html += "<a href='javascript:loadData("+i+")' id='page"+i+"';>"+i+"</a>&nbsp"}
	    }
	    html += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
        html += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
	
//	$("#totalPageDiv").html(html);
	$("#pagingDiv").html(html);
	
	
}
   
function beforePage(){
	var divide = pageNum;
	$("#page"+pageNum).css("color","");
	if(pageNum == 1) {pageNum}
	else {pageNum--};
	$("#pageNum").html(pageNum);
	$("#page"+pageNum).css("color","red");
	if(pageNum == Number(groupPageCount)){beforePageGroup();}
    else if(divide == pageNum) {}
    else{ loadData(pageNum,false);}
}

function nextPage(){
	var divide = pageNum;
	$("#page"+pageNum).css("color","");
	if(pageNum == totalPageCount) {pageNum}
	else{pageNum++;}	
	$("#page"+pageNum).css("color","red");
	$("#pageNum").html(pageNum);
	if(pageNum == Number(groupPageCount+1)){nextPageGroup();}
	else if(divide == pageNum) {}
	else{ loadData(pageNum,false);}
}


function beforePageGroup(){
	
	 groupPageNum--;
	if(groupPageNum <= 0) { groupPageNum =1; }
    
	var offSet = groupPageCount * (groupPageNum - 1) + 1;
    if(offSet < 0) {offSet = 1;}
    else{offSet};
    var limit = offSet + (groupPageCount - 1);
    
    var pagehtml='';
    groupCount = offSet;
    pagehtml  = "<a href='javascript:beforePageGroup();'><img src='/plm/portal/images/ico_left_go.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:beforePage();'><img src='/plm/portal/images/ico_left_go2.gif' /></a>&nbsp;&nbsp&nbsp;&nbsp";
    for(var i =offSet; offSet <= limit; offSet++){
        if(offSet == i){pagehtml += "<a href='javascript:loadData("+offSet+",false)' id='page"+offSet+"'; style='color:red'>"+offSet+"</a>&nbsp"}
        else{pagehtml += "<a href='javascript:loadData("+offSet+")' id='page"+offSet+"';>"+offSet+"</a>&nbsp"}
        
    }
    pagehtml += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
    
    if(totalPageCount > 20){
    $("#pageNum").html(groupCount);
    $("#pagingDiv").html(pagehtml);
    
    loadData(groupCount,false);
    }
}

function nextPageGroup(){
   
	
	groupPageNum++;
	var maxPageCount = parseInt(totalPageCount / groupPageCount);
	var plus = (groupPageCount > (totalPageCount % groupPageCount)) ?    1 : 0;

	if(groupPageNum >= parseInt(maxPageCount+plus)){groupPageNum = parseInt(maxPageCount+plus)}
	else{groupPageNum}
	
		
    var offSet = groupPageCount * (groupPageNum - 1) + 1;
    var limit = offSet + (groupPageCount - 1);
    if(limit > totalPageCount) {limit = totalPageCount;}
    else{limit};

    var pagehtml='';
    groupCount = offSet;
    pagehtml  = "<a href='javascript:beforePageGroup();'><img src='/plm/portal/images/ico_left_go.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:beforePage();'><img src='/plm/portal/images/ico_left_go2.gif' /></a>&nbsp;&nbsp&nbsp;&nbsp";
    for(var i =offSet; offSet <= limit; offSet++){
        if(offSet == i){pagehtml += "<a href='javascript:loadData("+offSet+",false)' id='page"+offSet+"'; style='color:red'>"+offSet+"</a>&nbsp"}
        else{pagehtml += "<a href='javascript:loadData("+offSet+")' id='page"+offSet+"';>"+offSet+"</a>&nbsp"}
        
    }
    pagehtml += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
    
   if(totalPageCount > 20){
    $("#pageNum").html(groupCount);
    $("#pagingDiv").html(pagehtml);
  
    loadData(groupCount,false);
   }
} 
var _callBackFuncTargetId = "";
function selectPartNo(targetId){
    var pType = "";
    if ( targetId == "dieNo3" ) {
        pType = "D";
    }
    else if ( targetId == "partNo3" ) {
        pType = "P";
    }
    else {
        pType = "A"
    }
    _callBackFuncTargetId = targetId;
    showProcessing();
    SearchUtil.selectPart("callBackFuncPartPopup", "pType=" + pType);
}

function callBackFuncPartPopup(objArr){
	 var partNo= "";
     for ( var i = 0; i < objArr.length; i++ ) {
         partNo+= objArr[i][1] + ",";
     }
     if(partNo.length > 0) partNo= partNo.substring(0, partNo.length-1);
     $('[name='+_callBackFuncTargetId+']').val(partNo);
}

function clearPartNo(targetId) {
    $("#" + targetId).val("");
  }

</script>

