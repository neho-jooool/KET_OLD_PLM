<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/dashboard/programProcessReport.js"></script>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid End -->

<!-- <body class="popup-background popup-space"> -->
<form id="programReportForm">
<input type="hidden" id="type" name="type" value=""/>
<input type="hidden" id="programNo" name="programNo" value=""/>
<input type="hidden" id="itemType" name="itemType" value=""/>
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07318") %></div>  
    <div class="inquiry-table02 clearfix b-space30">
        <div class="float-r">
            <span class="r-space15"><input type="radio" id="division" name="division" checked="checked" onclick="chgProgramReport()"  value="CA" /> <%=messageService.getString("e3ps.message.ket_message", "02391") %></span><span class="r-space15"><input type="radio" id="division" name="division" onclick="chgProgramReport()" value="ER" /> <%=messageService.getString("e3ps.message.ket_message", "02478") %></span><span class="r-space15"><%=messageService.getString("e3ps.message.ket_message", "07136") %> : <span id="clock"></span></span>
            <span class="in-block v-middle r-space10"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="#" class="btn_blue" onclick="javascript:programProcessReport.search();"><%=messageService.getString("e3ps.message.ket_message", "07308") %></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
    </div>
<!-- EJS TreeGrid Start -->
<div class="content-main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
        </div>
    </div>
</div>
</form>
<!-- EJS TreeGrid Start -->
<!-- </body> -->

<script type="text/javascript">
$(document).ready(function(){
	printTime();
    programProcessReport.createPaingGrid("${dashBoardDTO.type}");
    treeGridResize("#SampleSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

function printTime() {
    
    var now = new Date();                                                  // 현재시간
    var nowTime = now.getFullYear() + "." + (now.getMonth()+1) + "." + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
    $("#clock").html(nowTime);

}

function chgProgramReport(){
	programProcessReport.search();
}

function viewProject(oid){
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
}

function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","750","700","status=no,scrollbars=yes,resizable=no");
}
</script>