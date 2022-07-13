<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/sales/project/salesCSProject.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<%
boolean isAdmin = CommonUtil.isAdmin();
%>
<script type="text/javascript">
$(document).ready(function(e) {
	
	printTime();
	
	CommonUtil.multiSelect('years',90);
    CommonUtil.multiSelect('months',90);
    CommonUtil.multiSelect('closeYNs',90);
    
    
    sales.createPaingGrid();
    treeGridResize("#SalesCSSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	sales.search();
        }
    });
});

function printTime() {
	   
    var now = new Date();                                                  // 현재시간
    //var nowTime = now.getFullYear() + "." + (now.getMonth()+1) + "." + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
    //$("#clock").html(nowTime);
    //$("#year").val(now.getFullYear());
    //$("#month").val((now.getMonth()+1));
    // setTimeout("printTime()",1000);         // setTimeout(“실행할함수”,시간) 시간은1초의 경우 1000
    var standardYear  = Number(now.getFullYear());
    var str ="";
    for(var i=0; i <= 10; i++){
        str += "<option value='"+standardYear+"'>"+standardYear+"</option>";
        standardYear--;
    }
    $("#years").append(str);
    $("#years").val(now.getFullYear()).attr("selected", "selected");
    var month = now.getMonth()+1;
    if(month < 10){
    	month = '0'+month;
    }
    $("#months").val(month).attr("selected", "selected");
}

</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">CS차수관리 검색</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Project
                                <img src="/plm/portal/images/icon_navi.gif">영업
                                <img src="/plm/portal/images/icon_navi.gif">CS차수관리 검색</td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>

            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            <!-- [END] button -->
            <!-- [START] search condition -->
            <form name="searchForm">                
                <table class="table-style-01" cellpadding="0" summary="">
                    <tr>
                    <td class="search-title-01">
                        <span class="r-space20">
                           <select id="years" name="years" multiple="multiple"></select>&nbsp;&nbsp;년
                           <select id="months" name="months" multiple="multiple">
                               <option>01</option>
                               <option>02</option>
                               <option>03</option>
                               <option>04</option>
                               <option>05</option>
                               <option>06</option>
                               <option>07</option>
                               <option>08</option>
                               <option>09</option>
                               <option>10</option>
                               <option>11</option>
                               <option>12</option>
                            </select>&nbsp;&nbsp;월
                        </span>
                        <span class="r-space20">마감여부 
                            <select id="closeYNs" name="closeYNs" multiple="multiple">
                            <option>Y</option>
                            <option>N</option>
                            </select>
                        </span>
                        <span class="in-block v-middle l-space20"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:sales.search()" class="btn_blue" id="search"><%=messageService.getString("e3ps.message.ket_message", "02652") %></a></span><span class="pro-cell b-right"></span></span></span>
                    </td>
                    </tr>
                
                
                </table>
                <iframe id="download" name="download" align="center" width="0" height="0" border="0" style="display:none" onreadystatechange="hideProcessing();"></iframe>
            </form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                            <!-- EJS TreeGrid Start -->
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   