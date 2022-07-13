<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="e3ps.common.util.AuthUtil" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style>
.round {
    position:relative;
    float:left;
    top:4px;
    border: 2px solid #a1a1a1;
    padding: 4px 4px; 
    background: #0000ff;
    width: 1px;
    height: 1px;
    border-radius: 10px;
    z-index: 100;
}

.line {
   width: 100%;
   min-width:920px;
   margin-top:20px;
   margin-bottom:30px;
   border-bottom: 2px solid #008b8b;
   padding: 0px 0px; 
   background:#FFFFFF;
   height: 10px;
   z-index: 10;
}

.date {
    position:absolute;
    top:15px;
    left:-25px;
    font-size: 80%;
    width:60px;
    font-weight:bold;
}

.type {
    position:absolute;
    top:-15px;
    left:-26px;
    font-size: 80%;
    width: 60px;
    font-weight:bold;
}

div img { max-width: 130px;
          max-height : 140px;
        }
        
div #iepvmaindiv{
                    width : 130px !important;
                    height : 140px !important;
                }

.table-style-01-new input {vertical-align:middle}
.table-style-01-new {border-top:2px solid #779cb4;width:100%}
.table-style-01-new td {background-color:#F7F7F7;border:1px solid #b7d1e2;height:23px;padding-left:0px}
.table-style-01-new td.center {text-align:center;padding-left:0 !important}
.table-style-01-new td.right {text-align:right;padding-right:5px}
.table-style-01-new td.left {text-align:left;padding-left:20px !important}
.table-style-01-new td.bgcol {background-color:#e2edf4}
.table-style-01-new td.fontb {font-family: NanumBold !important;font-size: 13px;color: #34738d}
.table-style-01-new td.title {background-color:#e2edf4;border:1px solid #b7d1e2;padding-left: 25px;background: #e2edf4 url("../images/bul_table_td.png") no-repeat 15px 50%;
                                color: #34738d;text-align: left;font-family: NanumBold !important;font-size: 13px;}
.table-style-01-new td.search-title-01 {height:40px;background-color:#e2edf4;color: #34738d;font-size:13px;font-family:NanumBold !important;text-align:left;padding-left:30px}
.table-style-01-new td.title02 {background-color:#e2edf4;border:1px solid #b7d1e2;color:#34738d;font-size:12px;text-align:center;font-family:NanumBold !important;padding-left:0 !important}
.table-style-01-new input[type="radio"] {vertical-align:text-bottom}
.table-style-01-new thead td {background-color:#e2edf4;border:1px solid #b7d1e2;color: #34738d;text-align:center;font-family: NanumBold !important;font-size: 13px;padding-left:0 !important}
.table-style-01-new tfoot td {background-color:#e2edf4;border:1px solid #b7d1e2;color: #34738d;text-align:center;font-family: NanumBold !important;font-size: 13px;padding-left:0 !important}
.table-style-01-new input[type="text"] {width:60%;color:#444;height:20px;border:1px solid #b3d1dc;background-color:#fff}
.table-style-01-new input[readonly] {width:60%;border:1px solid #d1d1d1;background-color:#efefef;height:20px}
.table-style-01-new textarea {width:95%;color:#444;height:80px;border:1px solid #b3d1dc;background-color:#fff;padding:8px;margin:10px 0;font-size:12px;font-family:NanumGothic, "나눔고딕", Nanumgo, NanumBold, Dotum;color:#666}
.table-style-01-new tr td:first-child {border-left:none !important}
.table-style-01-new tr td:last-child {border-right:none !important}
.table-style-01-new-new td.t-space {padding:0px 0 !important}

</style>
<%
   String partOid = (String)request.getAttribute("partOid"); 
%>
<body style="overflow-Y:hideen" class="popup-background popup-space"> 
<OBJECT id=IEPageSetupX classid="clsid:41C5BC45-1BE8-42C5-AD9F-495D6C8D7586" codebase="/plm/extcore/js/dashboard/IEPageSetupX.cab#version=1,4,0,3" width=0 height=0><param name="copyright" value="http://isulnara.com">
</OBJECT>    
<form id="outputForm"> 
<input type="hidden" id="type" name="type" value=""/>
<input type="hidden" id="pjtNo" name="pjtNo" value=""/> 
<input type="hidden" id="pjtType" name="pjtType" value="" />
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" />Project Card</div>   
    <div class="float-r" id="buttonId">
        <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:doPrint();"  class="btn_blue">인쇄</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    <div class="sub-title-02 b-space15">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120") %>
    </div>
    <div class="clearfix b-space10">
        <div class="float-l" style="margin-right:3%;width:100%">
            <table cellpadding="0" class="table-style-01-new" summary="">
                <colgroup>
                    <col width="12%" />
                    <col width="12%" />
                    <col width="12%" />
                    <col width="12%" />
                    <col width="12%" />
                    <col width="12%" />
                    <col width="12%" />
                    <col width="10%" />
                    <col width="24%" />
                </colgroup>
                <tbody>
                <c:forEach var="data" items="${projectBasicInfoData}">               
                    <tr>
                        <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "03113") %></td>
                        <td class="center" colspan="3">${data.pjtName}</td>
                        <td class="fontb bgcol center">Project No.</td>
                        <td class="center">${data.pjtNo}</td>
                        <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "01760") %></td>
                        <td class="center">${data.stateState}</td>
                        <td rowspan="3">
                                <div style="width:150px;height: 140px;">   
                                <% if(partOid != null) {%>
                                    <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
                                            <jsp:param name="oid" value="<%=partOid%>"/>
                                        </jsp:include>
                               <%--  <% if(AuthUtil.isContentSecu(partOid)) {%>     
							            <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
							                <jsp:param name="oid" value="<%=partOid%>"/>
							            </jsp:include>
							    <%}else{%>
							           <jsp:include page="/jsp/edm/thumbImg.jsp" flush="false">
                                        <jsp:param name="oid" value="<%=partOid %>"/>
                                        </jsp:include>
                                        
                                 <%} --%><%  } else{%>
                                       <!--  <img src="/plm/portal/images/sample_component.png" /> -->
                                 <%} %>
							    </div>         
					    </td>
                    </tr>
                    <tr>
                        <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "00859") %></td>
                        <td class="center">${data.customCompany}</td>
                        <td class="fontb bgcol center" id="carTypeName"><%=messageService.getString("e3ps.message.ket_message", "04122") %></td>
                        <td class="center">${data.carType}</td>
                        <td class="fontb bgcol center" id="gateName"><%=messageService.getString("e3ps.message.ket_message", "07264") %></td>
                        <td class="center">
                                <c:if test="${geteResult ne ' '}">${geteResult}</c:if>
                                <c:if test="${geteResult1 ne ' '}">
                                        <c:if test="${geteResult1 eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
		                                <c:if test="${geteResult1 eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
		                                <c:if test="${geteResult1 eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
		                                <br>
                                </c:if>
                                <c:if test="${geteResult2 ne ' '}">${geteResult2}</c:if> 
                                <c:if test="${geteResult eq ' ' && geteResult1 eq ' ' && geteResult2 eq ' '  }">-</c:if>
                        </td>
                        <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "07124") %></td>
                        <td class="center" style="<c:if test="${type eq 'OK'}">background-color: #0BF00B;</c:if><c:if test="${type eq 'NG'}">background-color: red;</c:if>">${displayName}<br>${taskName}</td>
                    </tr>
                    <tr>
                        <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "01201") %></td>
                        <td class="center">${data.departName}</td>
                        <td class="fontb bgcol center">PM</td>
                        <td class="center">${data.userName}</td>
                        <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "07263") %></td>
                        <td class="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${data.planEndDate}"/></td>
                        <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "07121") %></td>
                        <td class="center">${data.expectTime}</td>
                    </tr>
                  </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
    <!-- ######################## 일정 타임라인 시작 ######################################## -->
    <script>
    
    //기간 차이 계산
	function getMyDiffDay(startDate, endDate) {
	    var diffDay = 0;
	    var start_yyyy = startDate.substring(0,4);
	    var start_mm = startDate.substring(5,7);
	    var start_dd = startDate.substring(8,startDate.length);
	    var sDate = new Date(start_yyyy, start_mm-1, start_dd);
	    var end_yyyy = endDate.substring(0,4);
	    var end_mm = endDate.substring(5,7);
	    var end_dd = endDate.substring(8,endDate.length);
	    var eDate = new Date(end_yyyy, end_mm-1, end_dd);
	
	    diffDay = Math.ceil((eDate.getTime() - sDate.getTime())/(1000*60*60*24));
	            
	    return diffDay;
	}
	function toggleTimeLine(obj){
		
		var label = $(obj).text().trim();
		var type = $(obj).data('type');
		
		if("car" == type){
			$("#carSchedule").toggle();
            $("#carScheduleExpand").toggle();
		}else if("program" == type){
            $("#programScheduleExpand").toggle();
            $("#programScheduleExpand").toggle();
		}else if("mileStone" == type){
			$("#mileStoneSchedule").toggle();
            $("#mileStoneExpand").toggle();
		}
		
		if(label == "펼치기") label = "접기";
        else                  label = "펼치기";
		
		$(obj).text(label);
	}
    $(document).ready(function(){
    	
    	//최종사용처일정 없을시 삭제 처리
        if($("#carSchedule .round").length == 0){
            $("#carScheduleName").remove();
            $("#carSchedule").remove();
            $("#carScheduleExpand").remove();
        }
        //접점고객일정 없을시 삭제 처리
        if($("#programSchedule .round").length == 0){
            $("#programScheduleName").remove();
            $("#programSchedule").remove();
            $("#programScheduleExpand").remove();
        }
        
        //KET마일스톤 없을시 삭제 처리
        if($("#mileStoneSchedule .round").length == 0){
            $("#mileStoneScheduleName").remove();
            $("#mileStoneSchedule").remove();
            $("#mileStoneExpand").remove();
        }
        
        //현재날짜 화살표 표시 로직
        var totalDate = new Array();
        
        $(".date").each(function(){
            var planDate = $(this).text().replace(/-/gi,"");
            totalDate.push(planDate);
        });
        
        var minDate = String(Math.min.apply(this,totalDate));
        var maxDate = String(Math.max.apply(this,totalDate));
        
        var minday = minDate.substring(0,4)+"-"+minDate.substring(4,6)+"-"+minDate.substring(6,8);
        var maxday = maxDate.substring(0,4)+"-"+maxDate.substring(4,6)+"-"+maxDate.substring(6,8);
        var diff = getMyDiffDay(minday,maxday);
        
        var pxlength = 820 / Number(diff);
        
        var newDate = new Date();
        var yy = newDate.getFullYear();
        var mm = newDate.getMonth()+1;
        if(mm < 10) mm = "0"+mm;
        
        var dd = newDate.getDate();
        if(dd < 10) dd = "0"+dd;
        
        var toDay = yy + "-" + mm + "-" + dd;
        var vdiff = getMyDiffDay(minday, toDay);
        var templen = vdiff * pxlength;
        var day = Number(yy+""+mm+""+dd);
        
        if(day < Number(maxDate)){
        	var top = $(".line:first").offset().top;
        	$("#today").css("top", top-55);
            $("#today").css("left", 40+templen);
            $("#today").css("position", "absolute");
        }else{
            $("#today").remove("img");
        }
        
        var carExpandCnt = 0;
        var programExpandCnt = 0;
        var mileStoneExpandCnt = 0;
        var checkDupYear = "";
        
        $(".round").each(function(){
        	
        	var type = $(this).data("type");
        	var expanded = $(this).data("expanded");
        	var dateData = $(this).find(".date").text();
        	
            var diffDay = getMyDiffDay(minday, dateData); 
            var templength = diffDay * pxlength;
            
            //확장 타임라인 일정 갯수에 따라 위치 조정 START
            if(expanded){
            	if(type == "car") {
            		templength += 13 * carExpandCnt;
            		carExpandCnt++;
            	}else if(type == "program"){
            		templength += 13 * programExpandCnt;
            		programExpandCnt++;
            	}else if(type == "mileStone"){
            		templength += 13 * mileStoneExpandCnt;
            		mileStoneExpandCnt++;
            	}
            }
            //확장 타임라인 일정 갯수에 따라 위치 조정 END
            
            $(this).css("left", 40 + templength);
            
            if(type != "mileStone"){
            	
            	var compareDay = Number(dateData.replace(/-/gi,""));
            	
                if(compareDay  > day){
                    $(this).css("background","#003B83");
                }else{
                    $(this).css("background","#A6A6A6");
                }
            }
            
            //년도 중복 제거 처리 START
            var dataYY = Number(dateData.substring(0, 4));
            var dataMMDD = dateData.substring(5, dateData.length);
            
            if(!expanded && (checkDupYear == dataYY)){
                $(this).find(".date").text(dataMMDD);
            }else{
            	checkDupYear = dataYY;
            }
            //년도 중복 제거 처리 END
        });
    });
    </script>
    <img alt="현재날짜" id="today" src="/plm/extcore/image/arrow_mark.png" />
    <div class="sub-title-02 b-space15" id="carScheduleName">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07245") %>
        <span class="toggleBtn in-block v-middle r-space7">
            <span class="pro-table">
                <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center" >
                    <a onclick="javascript:toggleTimeLine(this);" data-type="car" style="cursor:pointer;" class="btn_blue">
                    펼치기
                    </a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
        </span>
    </div>
    <div class="b-space10" id="carSchedule">
        <div class="line">
            <c:forEach var="data" items="${carScheduleData}" varStatus="idx">
                <div data-type='car' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
                    <div align='center'>
                      <span class='type'>${data.type}</span>
                    </div>
                    <div align='center'>
                      <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="b-space10" id="carScheduleExpand" style="display:none;">
        <c:forEach var="data" items="${carScheduleData}" varStatus="idx">
        <div class="line">
            <div data-type='car' data-expanded='true' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
                    <div align='center'>
                      <span class='type'>${data.type}</span>
                    </div>
                    <div align='center'>
                      <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
                    </div>
                </div>
        </div>
        </c:forEach>
    </div>
    <div class="sub-title-02 b-space15" id="programScheduleName">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07246") %>
        <span class="toggleBtn in-block v-middle r-space7">
            <span class="pro-table">
                <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center" >
                    <a onclick="javascript:toggleTimeLine(this);" data-type="program" style="cursor:pointer;" class="btn_blue">
                    펼치기
                    </a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
        </span>
    </div>
    <div class="b-space10" id="programSchedule">
        <div class="line">
            <c:forEach var="data" items="${programCustomScheduleData}" varStatus="idx">
                <div data-type='program' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
                    <div align='center'>
                      <span class='type'>${data.type}</span>
                    </div>
                    <div align='center'>
                      <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="b-space10" id="programScheduleExpand" style="display:none;">
        <c:forEach var="data" items="${programCustomScheduleData}" varStatus="idx">
        <div class="line">
            <div data-type='program' data-expanded='true' class='round' style="left:-${(idx.index) * 13}px;" title='${data.type}'>
                <div align='center'>
                  <span class='type'>${data.type}</span>
                </div>
                <div align='center'>
                  <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
    <div class="sub-title-02 b-space15" id="mileStoneScheduleName">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07262") %>
        <span class="toggleBtn in-block v-middle r-space7">
            <span class="pro-table">
                <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center" >
                    <a onclick="javascript:toggleTimeLine(this);" data-type="mileStone" style="cursor:pointer;" class="btn_blue">
                    펼치기
                    </a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
        </span>
    </div>
    <div class="b-space10" id="mileStoneSchedule">
        <div class="line">
        <c:forEach var="data" items="${mileStoneData}" varStatus="idx">
            <c:if test="${data.state == 'G'}"><c:set var='dataColor' value="#00ff00"/></c:if>
            <c:if test="${data.state == 'B'}"><c:set var='dataColor' value="#0000ff"/></c:if>
            <c:if test="${data.state == 'Y'}"><c:set var='dataColor' value="#ffff00"/></c:if>
            <c:if test="${data.state == 'R'}"><c:set var='dataColor' value="#ff0000"/></c:if>
	        <div data-type='mileStone' class='round' style="left:-${(idx.index) * 13}px;background:${dataColor};" title='${data.type}'>
	            <div align='center'>
	              <span class='type'>${data.taskName}</span>
	            </div>
	            <div align='center'>
	              <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.planEndDate}'/></span>
	            </div>
	        </div>
        </c:forEach>
        </div>
    </div>
    <div class="b-space10" id="mileStoneExpand" style="display:none;">
        <c:forEach var="data" items="${mileStoneData}" varStatus="idx">
        <div class="line">
            <c:if test="${data.state == 'G'}"><c:set var='dataColor' value="#00ff00"/></c:if>
            <c:if test="${data.state == 'B'}"><c:set var='dataColor' value="#0000ff"/></c:if>
            <c:if test="${data.state == 'Y'}"><c:set var='dataColor' value="#ffff00"/></c:if>
            <c:if test="${data.state == 'R'}"><c:set var='dataColor' value="#ff0000"/></c:if>
            <div data-type='mileStone' data-expanded='true' class='round' style="left:-${(idx.index) * 13}px;background:${dataColor};" title='${data.type}'>
                <div align='center'>
                  <span class='type'>${data.taskName}</span>
                </div>
                <div align='center'>
                  <span class='date'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.planEndDate}'/></span>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
    <!-- ######################## 일정 타임라인 종료 ######################################## -->
    <div class="clearfix b-space10">
        <div class="float-l" style="width:47%;margin-right:6%">
            <div class="sub-title-02 b-space15 float-l">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07247") %>
            </div>
            <p style="float:right;padding-right:10px" class="b-space7"><%=messageService.getString("e3ps.message.ket_message", "07248") %></p>
            <div>
                <table cellpadding="0" class="table-style-01-new" summary="">
                    <colgroup>
                        <col width="25%" />
                        <col width="25%" />
                        <col width="25%" />
                        <col width="25%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <td><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "01655") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "02184") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
                        </tr>
                    </thead>
                    <tbody>
							<c:forEach var="data" items="${produceAndMakeInfo}" varStatus="status">
								<tr>
									<td class="center">${data.type}</td>
									<td class="center"><a href="javascript:void(0);" onclick="temp('divPopupIn${status.index}')">${data.company}</a></td>
									<td class="center"><a href="javascript:void(0);" onclick="temp('divPopupOut${status.index}')">${data.outCompany}</a></td>
									<td class="center fontb bgcol"><a href="javascript:void(0);" onclick="temp('divPopupTotal${status.index}')">${data.total}</a></td>
								</tr>
							</c:forEach>
					</tbody>
                </table>
            </div>
        </div>
        <div id="divPopupIn0" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupIn0')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${assemData}" varStatus="status">
                <c:if test="${data.type eq '사내' }">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.type}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupIn1" style="display:none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupIn1')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${produceData}" varStatus="status">
                <c:if test="${data.productionPlace eq '사내' }">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.productionPlace}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupIn2" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupIn2')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${makeData}" varStatus="status">
                <c:if test="${data.making eq '사내'}">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.making}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupOut0" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupOut0')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${assemData}" varStatus="status">
                <c:if test="${data.type eq '외주' }">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.type}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupOut1" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupOut1')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${produceData}" varStatus="status">
                <c:if test="${data.productionPlace eq '외주' }">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.productionPlace}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupOut2" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupOut2')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${makeData}" varStatus="status">
                <c:if test="${data.making eq '외주'}">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.making}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupTotal0" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupTotal0')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${assemData}" varStatus="status">
                <c:if test="${data.type eq '사내' || data.type eq '외주'}">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.type}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupTotal1" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupTotal1')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${produceData}" varStatus="status">
                <c:if test="${data.productionPlace eq '사내' || data.productionPlace eq '외주'}">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.productionPlace}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupTotal2" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="150px;">PartName</td>
                    <td width="100px;" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01791") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupTotal2')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${makeData}" varStatus="status">
                <c:if test="${data.making eq '사내' || data.making eq '외주'}">
                <tr>
                    <td><a href="javascript:openViewPart('${data.partNo}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.making}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="float-l" style="width:47%">
            <div class="sub-title-02 b-space15 float-l">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01640") %>
            </div>
            <p style="float:right;padding-right:10px" class="b-space7"><%=messageService.getString("e3ps.message.ket_message", "07261") %></p>
            <div>
                <table cellpadding="0" class="table-style-01-new" summary="">
                    <colgroup>
                        <col width="20%" />
                        <col width="20%" />
                        <col width="20%" />
                        <col width="20%" />
                        <col width="20%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <td><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "01381") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "02143") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "02032") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "02443") %></td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="data" items="${costData}">
                        <tr>
                            <td class="center">${data.type}</td>
                            <td class="center"><fmt:formatNumber value="${data.targetCost}" groupingUsed="true" /></td>
                            <td class="center"><fmt:formatNumber value="${data.budgetCost}" groupingUsed="true" /></td>
                            <td class="center"><fmt:formatNumber value="${data.resultCost}" groupingUsed="true" /></td>
                            <td class="center"><fmt:formatNumber value="${data.balanceCost}" groupingUsed="true" /></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                    <!-- <tfoot>
                        <tr>
                            <td>계</td>
                            <td>1220</td>
                            <td>1110</td>
                            <td>748</td>
                            <td>362</td>
                        </tr>
                    </tfoot> -->
                </table>
            </div>
        </div>
    </div>
    <div class="sub-title-02 b-space15 float-l">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07252") %>
    </div>
    <p style="float:right;padding-right:10px" class="b-space7"><%=messageService.getString("e3ps.message.ket_message", "07248") %></p>
    <div class="b-space10">
        <table cellpadding="0" class="table-style-01" summary=""  style="table-layout:fixed;">
            <%-- <colgroup>
                <col width="10%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
                <col width="9%" />
            </colgroup> --%>
            <thead>
                <tr>
                    <td><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
                    <c:forEach var="data" items="${taskReportData}">
                    <td class="center" style="overflow: auto;">${data.taskName}</td>                    
                  </c:forEach>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="center"><%=messageService.getString("e3ps.message.ket_message", "07260") %></td>
                    <c:forEach var="data" items="${taskReportData}">
                    <td class="center">${data.complete}/${data.total}</td>
                    </c:forEach>
                </tr>
                <tr>
                    <td class="center"><%=messageService.getString("e3ps.message.ket_message", "02725") %></td>
                    <c:forEach var="data" items="${taskReportData}">
                    <td class="center">${data.processRate}%</td>
                    </c:forEach>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space15">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07259") %>
    </div>
    <div class="clearfix">
        <div class="float-l" style="width:15%;margin-right:3%">
            <table cellpadding="0" class="table-style-01-new" summary="">
                <colgroup>
                    <col width="50%" />
                    <col width="50%" />
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="2">Issue</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="data" items="${issueData}">
                    <tr>
                        <td class="center">${data.type}</td>
                        <td class="center"><a href="javascript:void(0);" onclick="issuePopup('${data.type}','${outputInfoData.pjtNo}')">${data.num}</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="float-l" style="width:15%;margin-right:3%">
            <table cellpadding="0" class="table-style-01-new" summary="">
                <colgroup>
                    <col width="50%" />
                    <col width="50%" />
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="2">개발품질문제</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="center">진행</td>
                        <td class="center"><a href="javascript:void(0);" onclick="dqmQulityPopup('Progress','${outputInfoData.pjtNo}');">${outputInfoData.dqmProcessCount}</a></td>
                    </tr>
                    <tr>
                        <td class="center">완료</td>
                        <td class="center"><a href="javascript:void(0);" onclick="dqmQulityPopup('Complete','${outputInfoData.pjtNo}');">${outputInfoData.dqmCompleteCount}</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="float-l" style="width:15%;margin-right:3%">
            <table cellpadding="0" class="table-style-01-new" summary="">
                <colgroup>
                    <col width="50%" />
                    <col width="50%" />
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01844") %></td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "02726") %></td>
                        <td class="center"><a href="javascript:void(0);" onclick="temp('divPopupProcess')">${ecoCount.ecoProcess}</a></td>
                    </tr>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "02171") %></td>
                        <td class="center"><a href="javascript:void(0);" onclick="temp('divPopupComplete')">${ecoCount.ecoComplete}</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="divPopupProcess" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="100px;" colspan="2">PartName&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupProcess')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${partInfo}" varStatus="status">
                <c:if test="${data.type eq '진행' }">
                <tr>
                    <td><a href="javascript:openViewEco('${data.ecoNo}');">${data.ecoNo}</a></td>
                    <td colspan="2">${data.ecoName}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupComplete" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="100px;" colspan="2">PartName&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="closeDivPopup('divPopupComplete')"><img src="/plm/extcore/image/close_btn18.png" /></a></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${partInfo}" varStatus="status">
                <c:if test="${data.type eq '완료' }">
                <tr>
                    <td><a href="javascript:openViewEco('${data.ecoNo}');">${data.ecoNo}</a></td>
                    <td colspan="2">${data.ecoName}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
                
            </table>
        </div>
        <div class="float-l" style="width:15%;margin-right:3%">
            <table cellpadding="0" class="table-style-01-new" summary="">
                <colgroup>
                    <col width="50%" />
                    <col width="50%" />
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="2"><%=messageService.getString("e3ps.message.ket_message", "02609") %></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="data" items="${productInfoData}" varStatus="status">
                    <tr>
                        <td class="center">${data.type}</td>
                        <td class="center"><a href="javascript:void(0)" onclick="temp('divPopupProduct${status.index}')">${data.num}</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="divPopupProduct0" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="200px;" colspan="2"><div><span>PartName</span><div class="float-r" align="right"><span><a href="javascript:void(0);" onclick="closeDivPopup('divPopupProduct0')"><img src="/plm/extcore/image/close_btn18.png" /></a></span></div></div></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${partData}" varStatus="status">
                <tr>
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
                    <td colspan="2">${data.partName}</td>
                </tr>
                </c:forEach>
                </tbody>
                
            </table>
        </div>
        <div id="divPopupProduct1" style="display: none;">
            <table cellpadding="0" class="table-style-01-new">
               <thead>
                <tr>
                    <td width="150px;">PartNo</td>
                    <td width="200px;" colspan="2"><div><span>PartName</span><div class="float-r" align="right"><span><a href="javascript:void(0);" onclick="closeDivPopup('divPopupProduct1')"><img src="/plm/extcore/image/close_btn18.png" /></a></span></div></div></td>
                </tr>
               </thead>
               <tbody> 
                <c:forEach var="data" items="${itemData}" varStatus="status">
                <tr>
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
                    <td colspan="2">${data.partName}</td>
                </tr>
                </c:forEach>
                </tbody>
                
            </table>
        </div>
        
        <div class="float-l" style="width:28%" id="gateResult">
            <table cellpadding="0" class="table-style-01-new" summary="">
                <colgroup>
                    <col width="30%" />
                    <col width="70%" />
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="2" id="gateResultName"><%=messageService.getString("e3ps.message.ket_message", "07258") %></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="data" items="${projectGateData}">
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "02322") %></td>
                        <td class="center">
                            <c:if test="${data.beforeTaskName ne ' '}">${data.beforeTaskName}</c:if>
                            <c:if test="${data.beforeGate ne ' '}">
                                <c:if test="${data.beforeGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.beforeGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.beforeGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if>
                            <c:if test="${data.beforeGateDate ne ' '}">${data.beforeGateDate}</c:if>
                            <c:if test="${data.beforeTaskName eq ' ' && data.beforeGate eq ' ' && data.beforeGateDate eq ' '}">-</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "07257") %></td>
                        <td class="center">
                            <c:if test="${data.currentTaskName ne ' '}">${data.currentTaskName}</c:if>
                            <c:if test="${data.currentGate ne ' '}">
                                <c:if test="${data.currentGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.currentGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.currentGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if>
                            <c:if test="${data.currentGateDate ne ' '}">${data.currentGateDate}</c:if>
                            <c:if test="${data.currentTaskName eq ' ' && data.currentGate eq ' ' && data.currentGateDate eq ' '}">-</c:if>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
  <%--       <div class="float-l" style="width:15%">
            <table cellpadding="0" class="table-style-01-new" summary="">
                <colgroup>
                    <col width="50%" />
                    <col width="50%" />
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="2"><%=messageService.getString("e3ps.message.ket_message", "07256") %></td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "00798") %></td>
                        <td class="center"><a href="javascript:void(0);" onclick="outputPopup('${productOid}');">${outputInfoData.defineCount}</a></td>
                    </tr>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "01310") %></td>
                        <td class="center"><a href="javascript:void(0);" onclick="outputPopup('${productOid}');">${outputInfoData.registryCount}</a></td>
                    </tr>
                </tbody>
            </table>
        </div> --%>
    </div>
</div>
</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
    
    //전자사업부 - 전자개발인 경우 프로젝트 보여주는 타입 다른게 표현
    if("${projectType}" == "dev"){
    	$("#carTypeName").text("적용기기");
    	$("#gateName").text("최근 DR");
    	$("#gateResultName").text("DR 평가결과");
    	//$("#gateResult").remove();
    }else if("${projectType}" == "it"){
    	$("#gateName").text("최근 DR");
    	$("#gateResultName").text("DR 평가결과");
    }
});

function temp(oid){
    document.getElementById(oid).style.height = "";
    document.getElementById(oid).style.width = "";
    document.getElementById(oid).style.display = "block";
    document.getElementById(oid).style.position = "absolute";
    document.getElementById(oid).style.zIndex = "9999";
    document.getElementById(oid).style.backgroundColor = "white";
    document.getElementById(oid).style.border = "1px solid black";
    document.getElementById(oid).style.left = (window.event.clientX-30)+"px";
    document.getElementById(oid).style.top = (window.event.clientY-20+$(window).scrollTop())+"px";
}

function closeDivPopup(oid){
    document.getElementById(oid).style.display = "none";
}

function partPopup(oid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function outputPopup(oid){
    
    var url = "/plm/servlet/e3ps/SearchProjectOutputServlet?oid="+oid+"&type=normal&popup=popup";
    
    //$("#type").val(type);
    //$("#pjtNo").val(pjtNo);
    window.open(url,"outputPopup","width=1000, height=500, scrollbars=yes");
    //$("#outputForm").attr({action:"/plm/ext/dashboard/outputTypeListPopup", target:"outputPopup", method: "POST"}).submit();
}

function issuePopup(type,pjtNo){
    $("#type").val(type);
    $("#pjtNo").val(pjtNo);
    window.open("","outputPopup","width=1000, height=500, scrollbars=yes");
    $("#outputForm").attr({action:"/plm/ext/dashboard/projectCardIssuePopup", target:"outputPopup", method: "POST"}).submit();
}

function dqmQulityPopup(type, pjtNo){
	$("#type").val(type);
    $("#pjtNo").val(pjtNo);
    window.open("","dqmQulityPopup","width=1000, height=500, scrollbars=yes");
    $("#outputForm").attr({action:"/plm/ext/dashboard/projectCardQulityPopup", target:"dqmQulityPopup", method: "POST"}).submit();
}

function doPrint(){
    $("#buttonId").hide();
    
    $(".toggleBtn").removeClass("in-block");
    $(".toggleBtn").hide();
    //여백설정
    IEPageSetupX.topMargin=0;
    IEPageSetupX.bottomMargin=0;
    //가로설정
    IEPageSetupX.Orientation = 0;
    //머리글 설정
    IEPageSetupX.header="";
    //바닥글 설정
    IEPageSetupX.footer="";
    //배경색
    IEPageSetupX.PrintBackground = true;
    //미리보기
    IEPageSetupX.Preview();
    //인쇄
   // IEPageSetupX.Print();
    
    donePrint();
}

function donePrint() { 
    $('#buttonId').show();
    $(".toggleBtn").addClass("in-block");
    $(".toggleBtn").show();
} 

</script>
