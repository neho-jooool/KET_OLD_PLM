<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/project/bom/bomCheckList.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
html{width:100%;height:100%;}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
	
	SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
	
	bomCheck.createPaingGrid();
	treeGridResize("#searchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	bomCheck.trimPjtNo();
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	bomCheck.search();
        }
    });
    
    curDateSet();

});

window.bomCheckSearch = function(){
	bomCheck.trimPjtNo();
	bomCheck.search();
	curDateSet();
}

window.curDateSet = function(){
	var today = new Date();   

    var year = today.getFullYear(); // 년도
    var month = today.getMonth() + 1;  // 월
    var date = today.getDate();  // 날짜
    
    var hours = today.getHours(); // 시
    var minutes = today.getMinutes();  // 분
    
    if(minutes < 10){
    	minutes = '0'+minutes;
    }
    
    var curDate = year + "."+month +"." + date + "  "+hours+":"+minutes;
    
    $('#curDate').html("<b><font color='red' size='2px' >일자 : "+curDate+"</font></b>");
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
                                <td class="font_01">BOM정합성 검증
                                
                                
                                <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
                                <a href="/plm/ext/dms/redirectSG/SG-20-001/DOWN" download target="download"><!-- 시스템가이드 링크 -->
                                    <img src="/plm/portal/images/exclamation.png" class="detailInfo" style="cursor:pointer;" title="정보를 보려면 클릭하세요." />
                                </a>
                                <c:if test="${ket:isAdmin() }">
                                <a href="#" onClick="javascript:getOpenWindow2('/plm/ext/dms/redirectSG/SG-20-001/VIEW', 'SGDocumentPopup', 800, 450);"><!-- 시스템가이드 팝업 -->
                                    <img src="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/images/tree/leaf.gif" title="시스템 가이드 문서 상세정보">
                                </a>
                                </c:if>
                                
                                
                                </td>
                                <td align="right"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:bomCheckSearch();" class="btn_blue">
                                                   검색
                                            </a>
                                        </td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
            </table>
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            
            <form name="searchForm">
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <colgroup>
                        <col width="20%" />
                        <col width="60%" />
                        <col width="20%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL0"><input type="text" name="pjtNo" id="pjtNo" class="txt_field" style="width: 60%" value="${pjtNo}"></td>
                        <td class="tdwhiteR" id="curDate"></td>
		            </tr>
                </table>
            </form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		            <td align="right">
		                <table border="0" cellspacing="0" cellpadding="0" width="100%">
		                    <tr>
		                       <td class="space5"></td>
		                    </tr>
		                </table> <!-- EJS TreeGrid Start -->
		                <div class="content-main">
		                    <div class="container-fluid">
		                        <div class="row-fluid">
		                            <div class="tabContent" style="border: 0; border-radius: 0;padding-top:0 !important;">
		                                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
		                            </div>
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