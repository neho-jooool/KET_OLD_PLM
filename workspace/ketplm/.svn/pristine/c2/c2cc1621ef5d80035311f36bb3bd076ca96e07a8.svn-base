<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style>
.round {
    position:absolute;
    top:106px;
    left:150px;
    border: 2px solid #a1a1a1;
    padding: 4px 4px; 
    background: #0000ff;
    width: 1px;
    height: 1px;
    border-radius: 10px;
    z-index: 100;
}


.line {
   position:absolute;
   top:115px;
   left:0px;
   border: 1px solid #008b8b;
   padding: 0px 0px; 
   background: #FFFFFF;
   width: 1120px;
   height: 0px;
   z-index: 10;
}

.date {
    position:absolute;
    top:15px;
    left:-26px;
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

</style>

<script type="text/javascript">
$(document).ready(function(){
	
	$("#customer").val("${dashBoardDTO.customer}");
	$("#carType").val("${dashBoardDTO.carType}");
	
	var carHtml = "";
	<c:forEach var="data" items="${carTypeScheduleInfoData}" varStatus="index">
        carHtml += "<div id=\"r${index.count}\" class=\"round\" title=\"${data.type}\"><div align='center'><span class='type'>${data.type}</span></div><div align='center'><span class='date' id='date${index.count}'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span></div></div>";
    </c:forEach>
    
    $("#carSchedule").append(carHtml);
    
     var date = new Array();
    <c:forEach var="data" items="${carTypeScheduleInfoData}" varStatus="index">
            date[${index.index}] = $("#date${index.count}").text();
           // alert(date[0].replace(/-/gi,""));
    </c:forEach>
    
    var totalDate = new Array();
    var totalIndex =0;

    for(var i=0; i < date.length; i++){
        totalDate[totalIndex] = date[i].replace(/-/gi,"");
        totalIndex++;
    }
    
    var minDate = String(Math.min.apply(this,totalDate));
    var maxDate = String(Math.max.apply(this,totalDate)); 
    
    
    var minday = minDate.substring(0,4)+"-"+minDate.substring(4,6)+"-"+minDate.substring(6,8);
    var maxday = maxDate.substring(0,4)+"-"+maxDate.substring(4,6)+"-"+maxDate.substring(6,8);
    var diff = getMyDiffDay(minday,maxday);
    var pxlength = 920 / Number(diff);
    
    $("#today").css("top",154);
    var newDate = new Date();
    var yy = newDate.getFullYear();
    var mm = newDate.getMonth()+1;
    if(mm < 10){
        mm = "0"+mm;
    }
    var dd = newDate.getDate();
    if(dd < 10){
        dd = "0"+dd;
    }
    var toDay = yy + "-" + mm + "-" + dd;
    var vdiff = getMyDiffDay(minday,toDay);
    var templen = vdiff * pxlength;
    var day = Number(yy+""+mm+""+dd);
    if(day < Number(maxDate)){
        $("#today").css("left", 40+templen);
        $("#today").css("position", "absolute");
    }else{
        $("#today").remove("img");
    }
    
   
    <c:forEach var="data" items="${carTypeScheduleInfoData}" varStatus="index">
    
    var diffDay = getMyDiffDay(minday, $("#date"+(Number("${index.count}"))).text()); 
    var templength = diffDay * pxlength;
  
    $("#r${index.count}").css("left", 40+templength);  
    
    var compareDay = Number($("#date"+(Number("${index.count}"))).text().replace(/-/gi,""));
    
    if(compareDay  > day){
        $("#r${index.count}").css("background","#003B83 ");
    }else{
        $("#r${index.count}").css("background","#A6A6A6 ");
    }
       
    /* <c:if test="${data.type eq 'Model고정'}">
    $("#r${index.count}").css("background","#ff0000");
    </c:if>
    <c:if test="${data.type eq 'All출도'}">
    $("#r${index.count}").css("background","#00ff00");
    </c:if>
    <c:if test="${data.type eq 'Proto'}">
    $("#r${index.count}").css("background","#0000ff");
    </c:if>
    <c:if test="${data.type eq 'MCAR'}">
    $("#r${index.count}").css("background","#ff00ff");
    </c:if>
    <c:if test="${data.type eq 'PILOT1'}">
    $("#r${index.count}").css("background","#00ffff");
    </c:if>
    <c:if test="${data.type eq 'PILOT2'}">
    $("#r${index.count}").css("background","#ffff00");
    </c:if>
    <c:if test="${data.type eq 'M1'}">
    $("#r${index.count}").css("background","#f03040");
    </c:if>
    <c:if test="${data.type eq 'M2'}">
    $("#r${index.count}").css("background","#ff5610");
    </c:if>
    <c:if test="${data.type eq 'SOP'}">
    $("#r${index.count}").css("background","#ff5990");
    </c:if> */
    
    $("#r${index.count}").css("top",170);
   
    </c:forEach>
 
    //년도 중복 제거
    var afterTempYear = "";
    var beforeTempYear = "";
    <c:forEach var="data" items="${carTypeScheduleInfoData}" varStatus="index">
     
      beforeTempYear = "<fmt:formatDate pattern='yyyy' value='${data.oemEndDate}'/>";
     if(beforeTempYear != afterTempYear){
         afterTempYear = beforeTempYear;
     }else{
         $("#date${index.count}").text("<fmt:formatDate pattern='MM-dd' value='${data.oemEndDate}'/>");
         afterTempYear = beforeTempYear;
     }
    </c:forEach>
    
    
    $("#l1").css("width",1120);
    $("#l1").css("top",175); 
 
});


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

 function aSemPopup(devType){
	 $("#devType").val(devType);
	 window.open("","aSemPopup","width=1000, height=700,scrollbars=yes");
     $("#carTyypeForm").attr({action:"/plm/ext/dashboard/carTypePopup", target:"aSemPopup", method: "POST"}).submit();
 }

 function singlePopup(devType, itemType){
     $("#devType").val(devType);
     $("#itemType").val(itemType);
     window.open("","singlePopup","width=1000, height=700,scrollbars=yes");
     $("#carTyypeForm").attr({action:"/plm/ext/dashboard/carType1Popup", target:"singlePopup", method: "POST"}).submit();
 }
 
 function itemTyeNewAndModifyPopup(itemType,moldCategory){
	     $("#moldCategory").val(moldCategory);
	     $("#itemType").val(itemType);
	     window.open("","singlePopup","width=1000, height=700,scrollbars=yes");
	     $("#carTyypeForm").attr({action:"/plm/ext/dashboard/carType2Popup", target:"singlePopup", method: "POST"}).submit();
 }
 
 function itemTyeNewAndModifyPopup1(pjtNo,itemType){
      $("#pjtNo").val(pjtNo);
      $("#itemType").val(itemType);
      window.open("","singlePopup","width=1000, height=700,scrollbars=yes");
      $("#carTyypeForm").attr({action:"/plm/ext/dashboard/carType3Popup", target:"singlePopup", method: "POST"}).submit();
}
 
 function carTaskCompletePopup(pjtNo, carType, customer){
	 $("#pjtNo").val(pjtNo);
	 $("#carType").val(carType);
	 $("#customer").val(customer);
	 window.open("","carTaskCompletePopup","width=1000, height=700,scrollbars=yes");
     $("#carTyypeForm").attr({action:"/plm/ext/dashboard/carTaskCompletePopup", target:"carTaskCompletePopup", method: "POST"}).submit();
 }
 
 function carTaskProcessPopup(pjtNo, carType, customer){
     $("#pjtNo").val(pjtNo);
     $("#carType").val(carType);
     $("#customer").val(customer);
     window.open("","carTaskProcessPopup","width=1000, height=700,scrollbars=yes");
     $("#carTyypeForm").attr({action:"/plm/ext/dashboard/carTaskProcessPopup", target:"carTaskProcessPopup", method: "POST"}).submit();
 }
 
 function carTaskTotalPopup(pjtNo, carType, customer){
     $("#pjtNo").val(pjtNo);
     $("#carType").val(carType);
     $("#customer").val(customer);
     window.open("","carTaskTotalPopup","width=1000, height=700,scrollbars=yes");
     $("#carTyypeForm").attr({action:"/plm/ext/dashboard/carTaskTotalPopup", target:"carTaskTotalPopup", method: "POST"}).submit();
 }
 
 function projectView(oid){
     var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
     openWindow(url,"","755","683","scrollbars=no,resizable=no,top=200,left=250");
 }
 
</script>

<form id="carTyypeForm">
<input type="hidden" id="customer" name="customer" value="" />
<input type="hidden" id="carType" name="carType" value="" />
<input type="hidden" id="devType" name="devType" value="" />
<input type="hidden" id="itemType" name="itemType" value=""/>
<input type="hidden" id="moldCategory" name="moldCategory" value=""/>
<input type="hidden" id="pjtNo" name="pjtNo" value=""/>
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07269") %></div>  
    <div class="float-r" id="buttonId">
        <span class="in-block v-middle r-space20 b-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span>
         <p style="float:right;padding-right:10px;font-size:11px;" ><%=messageService.getString("e3ps.message.ket_message", "02485") %>/<%=messageService.getString("e3ps.message.ket_message", "02171") %>/<%=messageService.getString("e3ps.message.ket_message", "02726") %>(<%=messageService.getString("e3ps.message.ket_message", "02703") %>)</p>
    </div>
    
    <div class="b-space30">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
                <col width="10%" />
            </colgroup>
            <tbody>
                <tr>
                    <c:forEach var="data" items="${carTypeBasicInfoData}">
                    <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "04122") %></td>
                    <td class="center">${data.carType}</td>
                    <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "02847") %></td>
                    <td class="center">${data.customer}</td>
                    <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "07268") %></td>
                    <td class="center"><fmt:formatNumber value="${data.total}" /></td>
                    <td class="fontb bgcol center">SOP</td>
                    <td class="center">${data.sop}</td>
                    <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "03124") %></td>
                    <td class="center">${data.productTotal}/${data.completeCount}/${data.processCount1}(${data.delayCount})</td>
                    </c:forEach>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="sub-title-02 b-space15">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07245") %>
    </div>
    <div class="b-space30" id="carSchedule">
        <img alt="현재날짜" id="today" src="/plm/extcore/image/arrow_mark.png" />
        <div id="l1" class="line"></div>
    </div>
    <div class="clearfix b-space30 t-space50">
        <div class="float-l" style="width:47%;margin-right:6%">
            <div class="sub-title-02 b-space15 float-l">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07247") %>
            </div>
            <p style="float:right;padding-right:10px" class="b-space7"><%=messageService.getString("e3ps.message.ket_message", "07248") %></p>
            <div>
                <table cellpadding="0" class="table-style-01" summary="">
                    <colgroup>
                        <col width="25%" />
                        <col width="25%" />
                        <col width="25%" />
                        <col width="25%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07234") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07267") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07266") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "00966") %></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="center"><%=messageService.getString("e3ps.message.ket_message", "01963") %></td>
                            <td class="center"><a href="javascript:aSemPopup('수주개발');">${suCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('수주개발','A');">${suSCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('수주개발','B');">${suDCount}</td>
                        </tr>
                        <tr>
                            <td class="center"><%=messageService.getString("e3ps.message.ket_message", "02476") %></td>
                            <td class="center"><a href="javascript:aSemPopup('전략개발');" >${jenCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('전략개발','A');">${jenSCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('전략개발','B');">${jenDCount}</td>
                        </tr>
                        <tr>
                            <td class="center"><%=messageService.getString("e3ps.message.ket_message", "09484") %></td>
                            <td class="center"><a href="javascript:aSemPopup('연구');" >${yenCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('연구','A');">${yenSCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('연구','B');">${yenDCount}</td>
                        </tr>
                        <tr>
                            <td class="center"><%=messageService.getString("e3ps.message.ket_message", "09485") %></td>
                            <td class="center"><a href="javascript:aSemPopup('4M');" >${cuCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('4M','A');">${cuSCount}</a></td>
                            <td class="center"><a href="javascript:singlePopup('4M','B');">${cuDCount}</td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07187") %></td>
                            <td><a href="javascript:aSemPopup('');" >${totalCount}</a></td>
                            <td><a href="javascript:singlePopup('','A');">${sTotalCount}</a></td>
                            <td><a href="javascript:singlePopup('','B');">${dTotalCount}</a></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
        <div class="float-l" style="width:47%">
            <div class="sub-title-02 b-space15 float-l">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07249") %>
            </div>
            <p style="float:right;padding-right:10px" class="b-space7"><%=messageService.getString("e3ps.message.ket_message", "07248") %></p>
            <div>
                <table cellpadding="0" class="table-style-01" summary="">
                    <colgroup>
                        <col width="14%" />
                        <col width="14%" />
                        <col width="14%" />
                        <col width="14%" />
                        <col width="14%" />
                        <col width="14%" />
                        <col width="16%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <td colspan="3">Mold</td>
                            <td colspan="3">Press</td>
                            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "07187") %></td>
                        </tr>
                        <tr>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07265") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "01936") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "07265") %></td>
                            <td><%=messageService.getString("e3ps.message.ket_message", "01936") %></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="fontb bgcol center"><a href="javascript:itemTyeNewAndModifyPopup('Mold','C')">${moldTotalCount}</a></td>
                            <td class="center"><a href="javascript:itemTyeNewAndModifyPopup('Mold','A')">${newMoldCount}</a></td>
                            <td class="center"><a href="javascript:itemTyeNewAndModifyPopup('Mold','B')">${modifyMoldCount}</a></td>
                            <td class="fontb bgcol center"><a href="javascript:itemTyeNewAndModifyPopup('Press','C')">${pressTotalCount}</a></td>
                            <td class="center"><a href="javascript:itemTyeNewAndModifyPopup('Press','A')">${newPressCount}</a></td>
                            <td class="center"><a href="javascript:itemTyeNewAndModifyPopup('Press','B')">${modifyPressCount}</a></td>
                            <td class="fontb bgcol center"><a href="javascript:itemTyeNewAndModifyPopup('ALL','')">${allTotalCount}</a></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="sub-title-02 b-space15">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "03046") %> List
    </div>
    <div class="b-space30">
        <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
                <col width="3%" />
               <%--  <col width="7%" /> --%>
                <col width="21%" />
                <col width="5%" />
                <col width="10%" />
                <col width="5%" />
                <col width="6%" />
                <col width="4%" />
                <col width="4%" />
                <col width="4%" />
                <col width="4%" />
                <col width="4%" />
                <col width="4%" />
                <col width="6%" />
                <col width="6%" />
                <col width="6%" />
            </colgroup>
            <thead>
                <tr>
                    <td rowspan="2">NO</td>
                   <!--  <td rowspan="2">조립구분</td> -->
                    <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "03113") %></td>
                    <td rowspan="2">PM</td>
                    <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00631") %></td>
                    <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00837") %></td>
                    <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01760") %></td>
                    <td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "07251") %></td>
                    <td colspan="3"><%=messageService.getString("e3ps.message.ket_message", "07252") %></td>
                    <td colspan="3">Gate</td>
                </tr>
                <tr>
                    <td>Mold</td>
                    <td>Press</td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "00966") %></td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "02171") %></td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "02726") %></td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "07253") %></td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "07254") %></td>
                    <td><%=messageService.getString("e3ps.message.ket_message", "07255") %></td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="data" items="${carTypeProjectListInfoData}" varStatus="status">
                <tr>
                    <td class="center">${status.count}</td>
                   <%--  <td class="center">${data.assemType}</td> --%>
                    <td class="left"><a href="javascript:projectView('${data.oid}')">${data.pjtName}</a></td>
                    <td class="center">${data.userName}</td>
                    <td class="center">${data.departName}</td>
                    <td class="center"><div style="width:90px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">${data.customCompany}</div></td>
                    <td class="center">${data.stateState}</td>
                    <td class="center"><a href="javascript:itemTyeNewAndModifyPopup1('${data.pjtNo}','Mold')">${data.moldCount}</a></td>
                    <td class="center"><a href="javascript:itemTyeNewAndModifyPopup1('${data.pjtNo}','Press')">${data.pressCount}</a></td>
                    <td class="center"><a href="javascript:itemTyeNewAndModifyPopup1('${data.pjtNo}','구매품')">${data.goodsCount}</a></td>
                    <td class="center"><a href="javascript:carTaskTotalPopup('${data.pjtNo}','${data.carType}','${data.customer}')">${data.totalCount}</a></td>
                    <td class="center"><a href="javascript:carTaskCompletePopup('${data.pjtNo}','${data.carType}','${data.customer}')">${data.taskCompleteCount}</a></td>
                    <td class="center"><a href="javascript:carTaskProcessPopup('${data.pjtNo}','${data.carType}','${data.customer}')">${data.taskProcessCount}/${data.taskDelayNum}</a></td>
                    <td class="center">
                           <c:if test="${data.beforeTaskName eq null && data.beforeGate eq null && data.beforeGateDate eq null }">-</c:if>
                           <c:if test="${data.beforeTaskName ne null}">${data.beforeTaskName}</c:if>
                            <c:if test="${data.beforeGate ne null}">
                                <c:if test="${data.beforeGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.beforeGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.beforeGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if><br>
                            <c:if test="${data.beforeGateDate ne null}">${data.beforeGateDate}</c:if>
                    </td>
                    <td class="center">
                            <c:if test="${data.currentTaskName eq null && data.currentGate eq null && data.currentGateDate eq null }">-</c:if>
                            <c:if test="${data.currentTaskName ne null}">${data.currentTaskName}</c:if>
                            <c:if test="${data.currentGate ne null}">
                                <c:if test="${data.currentGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.currentGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.currentGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if><br>
                            <c:if test="${data.currentGateDate ne null}">${data.currentGateDate}</c:if>
                    </td>
                    <td class="center">
                            <c:if test="${data.nextTaskName eq null && data.nextGate eq null && data.nextGateDate eq null }">-</c:if>
                            <c:if test="${data.nextTaskName ne null}">${data.nextTaskName}</c:if>
                            <c:if test="${data.nextGate ne null}">
                                <c:if test="${data.nextGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.nextGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.nextGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if><br>
                            <c:if test="${data.nextGateDate ne null}">${data.nextGateDate}</c:if>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</form>