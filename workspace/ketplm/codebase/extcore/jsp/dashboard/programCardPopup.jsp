<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style>
.round {
    position:absolute;
    top:108px;
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
   left:20px;
   border: 1px solid #008b8b;
   padding: 0px 0px; 
   background: #FFFFFF;
   width: 1460px;
   height: 0px;
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

.table-style-01-new input {vertical-align:middle}
.table-style-01-new {border-top:2px solid #779cb4;width:100%}
.table-style-01-new td {background-color:#F7F7F7;border:1px solid #b7d1e2;height:32px;padding-left:5px}
.table-style-01-new td.center {text-align:center;padding-left:0 !important}
.table-style-01-new td.right {text-align:right;padding-right:5px}
.table-style-01-new td.left {text-align:left;padding-left:20px !important}
.table-style-01-new td.bgcol {background-color:#e2edf4}
.table-style-01-new td.fontb {font-family: NanumBold !important;font-size: 11px;color: #34738d}
.table-style-01-new td.title {background-color:#e2edf4;border:1px solid #b7d1e2;padding-left: 25px;background: #e2edf4 url("../images/bul_table_td.png") no-repeat 15px 50%;
                                color: #34738d;text-align: left;font-family: NanumBold !important;font-size: 11px;}
.table-style-01-new td.search-title-01 {height:40px;background-color:#e2edf4;color: #34738d;font-size:11px;font-family:NanumBold !important;text-align:left;padding-left:30px}
.table-style-01-new td.title02 {background-color:#e2edf4;border:1px solid #b7d1e2;color:#34738d;font-size:12px;text-align:center;font-family:NanumBold !important;padding-left:0 !important}
.table-style-01-new input[type="radio"] {vertical-align:text-bottom}
.table-style-01-new thead td {background-color:#e2edf4;border:1px solid #b7d1e2;color: #34738d;text-align:center;font-family: NanumBold !important;font-size: 13px;padding-left:0 !important}
.table-style-01-new tfoot td {background-color:#e2edf4;border:1px solid #b7d1e2;color: #34738d;text-align:center;font-family: NanumBold !important;font-size: 13px;padding-left:0 !important}
.table-style-01-new input[type="text"] {width:60%;color:#444;height:20px;border:1px solid #b3d1dc;background-color:#fff}
.table-style-01-new input[readonly] {width:60%;border:1px solid #d1d1d1;background-color:#efefef;height:20px}
.table-style-01-new textarea {width:95%;color:#444;height:80px;border:1px solid #b3d1dc;background-color:#fff;padding:8px;margin:10px 0;font-size:12px;font-family:NanumGothic, "나눔고딕", Nanumgo, NanumBold, Dotum;color:#666}
.table-style-01-new tr td:first-child {border-left:none !important}
.table-style-01-new tr td:last-child {border-right:none !important}
.table-style-01-new td.t-space {padding:0px 0 !important}

</style>
<body class="popup-background popup-space">
<OBJECT id=IEPageSetupX classid="clsid:41C5BC45-1BE8-42C5-AD9F-495D6C8D7586" codebase="/plm/extcore/js/dashboard/IEPageSetupX.cab#version=1,4,0,3" width=0 height=0><param name="copyright" value="http://isulnara.com">
</OBJECT> 
<form id="programForm">
<input type="hidden" id="type" name="type" value=""/>
<input type="hidden" id="programNo" name="programNo" value="${programNo}" />
<input type="hidden" id="making" name="making" value="" />
<input type="hidden" id="itemType" name="itemType" value="" />
<input type="hidden" id="pjtNo" name="pjtNo" value="" />
<div class="contents-wrap">
    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "03034") %> Card</div>
    <div class="float-r" id="buttonId">
        <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:doPrint();"  class="btn_blue">인쇄</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span>
    </div>   
    <div class="sub-title-02 b-space15">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120") %>
    </div>
    <div class="b-space30">
        <table  cellpadding="0" class="table-style-01-new" summary="">
            <colgroup>
           <col width="11%" />
           <col width="11%" />
           <col width="11%" />
           <col width="11%" />
           <col width="11%" />
           <col width="11%" />
           <col width="11%" />
           <col width="13%" />
       </colgroup>
        <tbody>
            <c:forEach var="data" items="${programBasicInfoData}">
            <tr>
                <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "03044") %></td>
                <td class="center" colspan="3">${data.programName}</td>
                <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "03034") %> No</td>
                <td class="center">${data.programNo}</td>
                <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "00859") %></td>
                <td class="center">${data.customCompany}</td>
            </tr>
            <tr>
                <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "02847") %></td>
                <td class="center">${data.lastCompany}</td>
                <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "04122") %></td>
                <td class="center">${data.carName}</td>
                <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "00920") %></td>
                <td class="center">${data.projectCount}<%=messageService.getString("e3ps.message.ket_message", "00698") %></td>
                <td class="fontb bgcol center"><%=messageService.getString("e3ps.message.ket_message", "07244") %></td>
                <td class="center">${data.userName}</td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="sub-title-02 b-space15">
    <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07245") %>
</div>
<div class="b-space40" id="carSchedule" >
    <img alt="현재날짜" id="today" src="/plm/extcore/image/arrow_mark.png" />
    <div id="l1" class="line"></div>
</div>
<div class="sub-title-02 b-space15 t-space50" style="padding-bottom:8px;">
    <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07246") %>
</div>
<div class="b-space40" id="customSchedule">
    <div id="l2" class="line"></div>
</div>
<div class="b-space30 t-space30" style="table-layout: fixed;">
    <div class="float-l" style="width:47%;margin-right:6%">
       <div class="sub-title-02 b-space15 float-l">
           <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07247") %>
       </div>
       <p style="float:right;padding-right:10px" class="b-space7"><%=messageService.getString("e3ps.message.ket_message", "07248") %></p>
       <div>
           <table cellpadding="0" class="table-style-01-new" summary=""  style="table-layout: fixed;">
               <colgroup>
                   <col width="20%" />
                   <col width="20%" />
                   <col width="20%" />
                   <col width="20%" />
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
                  <c:forEach var="data" items="${productAndmakeInfoData}" varStatus="status">
                   <tr>
                       <td class="center">${data.type}</td>
                       <td class="center"><a href="javascript:void(0);" onclick="temp('divPopupIn${status.index}')">${data.typeByIn}</a></td>
                       <td class="center"><a href="javascript:void(0);" onclick="temp('divPopupOut${status.index}')">${data.typeByOut}</a></td>
                       <td class="center fontb bgcol"><a href="javascript:void(0);" onclick="temp('divPopupTotal${status.index}')">${data.typeByTotal}</a></td>
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
                <c:if test="${data.assemblyPlaceType eq '사내' }">
                <tr>
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.assemblyPlaceType}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
                
            </table>
        </div>
        <div id="divPopupIn1" style="display: none;">
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
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
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
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
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
                <c:if test="${data.assemblyPlaceType eq '외주' }">
                <tr>
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.assemblyPlaceType}/${data.customCompany}</td>
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
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
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
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
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
                 <c:if test="${data.assemblyPlaceType eq '사내' || data.assemblyPlaceType eq '외주' }">
                <tr>
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.assemblyPlaceType}/${data.customCompany}</td>
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
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
                    <td>${data.partName}</td>
                    <td colspan="2">${data.productionPlace}/${data.customCompany}</td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
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
                    <td><a href="javascript:partPopup('${data.oid}');">${data.partNo}</a></td>
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
           <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "07249") %>
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
                        <td><%=messageService.getString("e3ps.message.ket_message", "02533") %></td>
                        <td>Mold</td>
                        <td>Press</td>
                        <td><%=messageService.getString("e3ps.message.ket_message", "07187") %></td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "01655") %> </td>
                        <td class="center"><a href="javascript:moldReportPopup('사내','Mold')">${moldInCount}</a></td>
                        <td class="center"><a href="javascript:moldReportPopup('사내','Press')">${pressInCount}</a></td>
                        <td class="center fontb bgcol"><a href="javascript:moldReportPopup('사내','')">${moldInCount+pressInCount}</a></td>
                    </tr>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "02184") %></td>
                        <td class="center"><a href="javascript:moldReportPopup('외주','Mold')">${moldOutCount}</a></td>
                        <td class="center"><a href="javascript:moldReportPopup('외주','Press')">${pressOutCount}</a></td>
                        <td class="center fontb bgcol"><a href="javascript:moldReportPopup('외주','')">${moldOutCount+pressOutCount}</a></td>
                    </tr>
                    <tr>
                        <td class="center"><%=messageService.getString("e3ps.message.ket_message", "07250") %></td>
                        <td class="center"><a href="javascript:moldReportChinaPopup('Mold')">${moldInChinaCount}</a></td>
                        <td class="center"><a href="javascript:moldReportChinaPopup('Press')">${pressInChinaCount}</a></td>
                        <td class="center fontb bgcol"><a href="javascript:moldReportChinaPopup('')">${moldInChinaCount+pressInChinaCount}</a></td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
                        <td><a href="javascript:moldTotalReportPopup('Mold')">${moldInCount+moldOutCount+moldInChinaCount}</a></td>
                        <td><a href="javascript:moldTotalReportPopup('Press')">${pressInCount+pressOutCount+pressInChinaCount}</a></td>
                        <td><a href="javascript:moldTotalReportPopup('')">${moldInCount+pressInCount+moldOutCount+pressOutCount+moldInChinaCount+pressInChinaCount}</a></td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>
<div class="sub-title-02 b-space15">
    <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "03046") %> List
</div>
<div class="b-space30">
    <table cellpadding="0" class="table-style-01-new" style="font-size: 11px;" summary="">
        <colgroup>
           <col width="3%" />
           <col width="6%" />
           <col width="21%" />
           <col width="5%" />
           <%-- <col width="5%" /> --%>
           <col width="11%" />
           <col width="5%" />
           <col width="5%" />
           <col width="5%" />
           <col width="5%" />
           <col width="5%" />
           <col width="5%" />
           <col width="8%" />
           <col width="8%" />
           <col width="8%" />
       </colgroup>
            <thead>
                <tr>
                    <td class="fontb" rowspan="2">NO</td>
                    <td class="fontb" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "07234") %></td>
                    <td class="fontb" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "03113") %></td>
                    <td class="fontb" rowspan="2">PM</td>
                    <%-- <td class="fontb" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00631") %></td> --%>
                    <td class="fontb" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00837") %></td>
                    <td class="fontb" colspan="3"><%=messageService.getString("e3ps.message.ket_message", "07251") %></td>
                    <td class="fontb" colspan="3"><%=messageService.getString("e3ps.message.ket_message", "07252") %></td>
                    <td class="fontb" colspan="3">Gate</td>
                </tr>
                <tr>
                    <td class="fontb">Mold</td>
                    <td class="fontb">Press</td>
                    <td class="fontb"><%=messageService.getString("e3ps.message.ket_message", "00966") %></td>
                    <td class="fontb"><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
                    <td class="fontb"><%=messageService.getString("e3ps.message.ket_message", "02171") %></td>
                    <td class="fontb"><%=messageService.getString("e3ps.message.ket_message", "02726") %></td>
                    <td class="fontb"><%=messageService.getString("e3ps.message.ket_message", "07253") %></td>
                    <td class="fontb"><%=messageService.getString("e3ps.message.ket_message", "07254") %></td>
                    <td class="fontb"><%=messageService.getString("e3ps.message.ket_message", "07255") %></td>
                </tr>
            </thead>
            <tbody>
              <c:forEach var="data" items="${programByProjectListInfoData}" varStatus="status">
                <tr>
                    <td class="center">${status.count}</td>
                    <td class="center">${data.devType}</td>
                    <td class="left"><a href="javascript:projectView('${data.oid}')">${data.pjtName}</a></td>
                    <td class="center">${data.userName}</td>
                   <!--  <td class="center"></td> -->
                    <td class="center">${data.customCompany}</td>
                    <td class="center"><a href="javascript:projectItemPopup('${data.pjtNo}','${programNo}','Mold')">${data.moldCount}</a></td>
                    <td class="center"><a href="javascript:projectItemPopup('${data.pjtNo}','${programNo}','Press')">${data.pressCount}</a></td>
                    <td class="center"><a href="javascript:projectItemPopup('${data.pjtNo}','${programNo}','구매품')">${data.goodsCount}</a></td>
                    <td class="center"><a href="javascript:projectTaskTotalPopup('${programNo}','${data.pjtNo}');">${data.taskTotalCount}</a></td>
                    <td class="center"><a href="javascript:projectTaskCompletePopup('${programNo}','${data.pjtNo}');">${data.taskCompleteCount}</a></td>
                    <td class="center"><a href="javascript:projectTaskProcessPopup('${programNo}','${data.pjtNo}');">${data.taskProcessCount}/${data.taskProcessDelayCount}</a></td>
                    <td class="center">
                            <c:if test="${data.beforeTaskName ne null}">${data.beforeTaskName}</c:if><br>
                            <c:if test="${data.beforeGate ne null}">
                                <c:if test="${data.beforeGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.beforeGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.beforeGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if><br>
                            <c:if test="${data.beforeGateDate ne null}">${data.beforeGateDate}</c:if>
                    </td>
                    <td class="center">
                            <c:if test="${data.currentTaskName ne null}">${data.currentTaskName}</c:if><br>
                            <c:if test="${data.currentGate ne null}">
                                <c:if test="${data.currentGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.currentGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.currentGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if><br>
                            <c:if test="${data.currentGateDate ne null}">${data.currentGateDate}</c:if>
                    </td>
                    <td class="center">
                            <c:if test="${data.nextTaskName ne null}">${data.nextTaskName}</c:if><br>
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
</body>

<script type="text/javascript">
$(document).ready(function(){
    /* 차종 일정 */
    var carHtml = "";
	<c:forEach var="data" items="${carNameScheduleInfoData}" varStatus="index">
     carHtml += "<div id=\"r${index.count}\" class=\"round\" title=\"${data.type}\"><div align='center'><span class='type'>${data.type}</span></div><div align='center'><span class='date' id='date${index.count}'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span></div></div>";
    </c:forEach>
    
    $("#carSchedule").append(carHtml);
    
    /* 고객 일정*/
    var customHtml = "";
    <c:forEach var="data" items="${customScheduleInfoData}" varStatus="index">
     customHtml += "<div id=\"c${index.count}\" class=\"round\" title=\"${data.type}\"><div align='center'><span class='type'>${data.type}</span></div><div align='center'><span class='date' id='cdate${index.count}'><fmt:formatDate pattern='yyyy-MM-dd' value='${data.oemEndDate}'/></span></div></div>";
    </c:forEach>
    
    $("#customSchedule").append(customHtml);
    
    var date = new Array();
    <c:forEach var="data" items="${carNameScheduleInfoData}" varStatus="index">
            date[${index.index}] = $("#date${index.count}").text();
    </c:forEach>
    
    var date1 = new Array();
    <c:forEach var="data" items="${customScheduleInfoData}" varStatus="index">
            date1[${index.index}] = $("#cdate${index.count}").text();
    </c:forEach>
    
    var totalDate = new Array();
    var totalIndex =0;
    
    for(var i=0; i < date.length; i++){
        totalDate[totalIndex] = date[i].replace(/-/gi,"");
        totalIndex++;
    }
    
    for(var i1=0; i1 < date1.length; i1++){
        totalDate[totalIndex] = date1[i1].replace(/-/gi,"");
        totalIndex++;
    }
    
    
    var minDate = String(Math.min.apply(this,totalDate));
    var maxDate = String(Math.max.apply(this,totalDate)); 
    
    var minday = minDate.substring(0,4)+"-"+minDate.substring(4,6)+"-"+minDate.substring(6,8);
    var maxday = maxDate.substring(0,4)+"-"+maxDate.substring(4,6)+"-"+maxDate.substring(6,8);
    var diff = getMyDiffDay(minday,maxday);
    var pxlength = 920 / Number(diff);
    
    $("#today").css("top",190);
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
    var day = yy+""+mm+""+dd;
    if(Number(day) < Number(maxDate)){
        $("#today").css("left", 40+templen);
        $("#today").css("position", "absolute");
    }else{
        $("#today").remove("img");
    }
    
    
    <c:forEach var="data" items="${carNameScheduleInfoData}" varStatus="index">
    
    var diffDay = getMyDiffDay(minday, $("#date"+(Number("${index.count}"))).text()); 
    var templength = diffDay * pxlength;
   
    $("#r${index.count}").css("left", 40+templength); 
    
    var compareDay = Number($("#date"+(Number("${index.count}"))).text().replace(/-/gi,""));
     
    if(compareDay  > day){
        $("#r${index.count}").css("background","#003B83 ");
    }else{
        $("#r${index.count}").css("background","#A6A6A6 ");
    }
    
    $("#r${index.count}").css("top",209);
   
    </c:forEach>
    
    
    
    <c:forEach var="data" items="${customScheduleInfoData}" varStatus="index">
    
    var diffDay = getMyDiffDay(minday, $("#cdate"+(Number("${index.count}"))).text()); 
    var templength = diffDay * pxlength;
 
    $("#c${index.count}").css("left", 40+templength);  
    
    var compareDay = Number($("#cdate"+(Number("${index.count}"))).text().replace(/-/gi,""));
   
    if(compareDay  > day){
        $("#c${index.count}").css("background","#003B83 ");
    }else{
        $("#c${index.count}").css("background","#A6A6A6 ");
    }
    
    $("#c${index.count}").css("top",274);
    </c:forEach>
    
    //년도 중복 제거
    var afterTempYear = "";
    var beforeTempYear = "";
    <c:forEach var="data" items="${carNameScheduleInfoData}" varStatus="index">
     
      beforeTempYear = "<fmt:formatDate pattern='yyyy' value='${data.oemEndDate}'/>";
     if(beforeTempYear != afterTempYear){
         afterTempYear = beforeTempYear;
     }else{
    	 $("#date${index.count}").text("<fmt:formatDate pattern='MM-dd' value='${data.oemEndDate}'/>");
         afterTempYear = beforeTempYear;
     }
    </c:forEach>
    
    var afterTempYear1 = "";
    var beforeTempYear1 = "";
    <c:forEach var="data" items="${customScheduleInfoData}" varStatus="index">
	    beforeTempYear1 = "<fmt:formatDate pattern='yyyy' value='${data.oemEndDate}'/>";
	    if(beforeTempYear1 != afterTempYear1){
	        afterTempYear1 = beforeTempYear1;
	    }else{
	        $("#cdate${index.count}").text("<fmt:formatDate pattern='MM-dd' value='${data.oemEndDate}'/>");
	        afterTempYear1 = beforeTempYear1;
	    }
    </c:forEach>
    
 
	$("#l1").css("width",1000);
	$("#l1").css("top",215);
	 
	$("#l2").css("width",1000);
	$("#l2").css("top",280);
    
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

function assemblyNormalPopup(type){
	if(type == "일반"){
		$("#type").val("A");
	}else if(type == "설비"){
		$("#type").val("B");
	}else{
		$("#type").val("");
	}
	 window.open("","programCardPopup","width=650, height=500");
	 $("#programForm").attr({action:"/plm/ext/dashboard/assemblyNormalPopup", target:"programCardPopup", method: "POST"}).submit();
	
}


function singleNormalPopup(type){
	window.open("","programCardPopup","width=650, height=500");
	if(type == "일반"){
		 $("#programForm").attr({action:"/plm/ext/dashboard/singleNormalPopup", target:"programCardPopup", method: "POST"}).submit();
	}else if(type == "설비"){
		 $("#programForm").attr({action:"/plm/ext/dashboard/singleSystemPopup", target:"programCardPopup", method: "POST"}).submit();
	}else{
		 $("#programForm").attr({action:"/plm/ext/dashboard/singleTotalPopup", target:"programCardPopup", method: "POST"}).submit();
	}
}

function goodsNormarlPopup(type){
	window.open("","programCardPopup","width=650, height=500");
    if(type == "일반"){
         $("#programForm").attr({action:"/plm/ext/dashboard/goodsNormalPopup", target:"programCardPopup", method: "POST"}).submit();
    }else if(type == "설비"){
         $("#programForm").attr({action:"/plm/ext/dashboard/goodsSystemPopup", target:"programCardPopup", method: "POST"}).submit();
    }else{
         $("#programForm").attr({action:"/plm/ext/dashboard/goodsTotalPopup", target:"programCardPopup", method: "POST"}).submit();
    }
	
}


 function moldReportPopup(making, itemType){
	$("#making").val(making);
	$("#itemType").val(itemType);
	window.open("","programCardPopup","width=1000, height=700");
	$("#programForm").attr({action:"/plm/ext/dashboard/moldReportPopup", target:"programCardPopup", method: "POST"}).submit();
 }
 
 function moldReportChinaPopup(itemType){
	 $("#itemType").val(itemType);
     window.open("","programCardPopup","width=1000, height=700");
     $("#programForm").attr({action:"/plm/ext/dashboard/moldReportChinaPopup", target:"programCardPopup", method: "POST"}).submit(); 
 }
 
 function moldTotalReportPopup(itemType){
	 $("#itemType").val(itemType);
     window.open("","programCardPopup","width=1000, height=700");
     $("#programForm").attr({action:"/plm/ext/dashboard/moldTotalReportPopup", target:"programCardPopup", method: "POST"}).submit();
 }
 
 
 function projectItemPopup(pjtNo, programNo, itemType){
	 $("#pjtNo").val(pjtNo);
	 $("#programNo").val(programNo);
	 $("#itemType").val(itemType);
	 window.open("","projectItemPopup","width=1000, height=700");
	 $("#programForm").attr({action:"/plm/ext/dashboard/projectItemPopup", target:"projectItemPopup", method: "POST"}).submit();
 }
 
 function projectTaskTotalPopup(programNo, pjtNo) {
	 $("#programNo").val(programNo);
	 $("#pjtNo").val(pjtNo);
	 window.open("","projectTaskTotalPopup","width=1000, height=700");
     $("#programForm").attr({action:"/plm/ext/dashboard/projectTaskTotalPopup", target:"projectTaskTotalPopup", method: "POST"}).submit();
 }
 
 function projectTaskCompletePopup(programNo, pjtNo) {
     $("#programNo").val(programNo);
     $("#pjtNo").val(pjtNo);
     window.open("","projectTaskCompletePopup","width=1000, height=700");
     $("#programForm").attr({action:"/plm/ext/dashboard/projectTaskCompletePopup", target:"projectTaskCompletePopup", method: "POST"}).submit();
 }
 
 function projectTaskProcessPopup(programNo, pjtNo) {
     $("#programNo").val(programNo);
     $("#pjtNo").val(pjtNo);
     window.open("","projectTaskProcessPopup","width=1000, height=700");
     $("#programForm").attr({action:"/plm/ext/dashboard/projectTaskProcessPopup", target:"projectTaskProcessPopup", method: "POST"}).submit();
 }
 
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
 
 function projectView(oid){
	 var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
	 openWindow(url,"","755","683","scrollbars=no,resizable=no,top=200,left=250");
 }
 
 
 function doPrint(){
	    $("#buttonId").hide();
	    //여백설정
	    IEPageSetupX.topMargin=0;
	    IEPageSetupX.bottomMargin=0;
	    IEPageSetupX.leftMargin=0;
	    IEPageSetupX.rightMargin=0;
	    //세로설정
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
	    
	    doneyet();
	}

	function doneyet() { 
	    $('#buttonId').show();
	}
 
</script>