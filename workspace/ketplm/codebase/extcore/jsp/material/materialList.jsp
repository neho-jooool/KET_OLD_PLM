<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/material/material.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
html{width:100%;height:100%;}
</style>
<script type="text/javascript">
var reloadFlag = 0;
$(document).ready(function(e) {
    
	material.createPaingGrid();
	material.createPaingGrid2();
	
	treeGridResize("#searchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	
    CommonUtil.multiSelect('spMaterMaker',130);
    CommonUtil.multiSelect('spMaterMaker2',130);
    CommonUtil.multiSelect('spMaterType',130);
    CommonUtil.multiSelect('spMaterialInfo',130);
    CommonUtil.multiSelect('spMaterNotFe',130);
    
    CalendarUtil.dateInputFormat('requestStartDate'); //requestStartDate
    CalendarUtil.dateInputFormat('requestEndDate'); //requestEndDate
    
    CalendarUtil.dateInputFormat('sopStartDate'); //sopStartDate
    CalendarUtil.dateInputFormat('sopEndDate'); //sopEndDate

    SuggestUtil.bind('USERDEPT', 'managerName', 'managerOid');
    SuggestUtil.bind('USERDEPT', 'tmUserName', 'tmUserOid');
    
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    SuggestUtil.bind('DEPARTMENT', 'taskDeptName', 'taskDeptCode');
    
    //SuggestUtil.bind('CUSTOMEREVENT', 'lastCustomerName', 'lastCustomer');
    SuggestUtil.bind('CARTYPE', 'oemName', 'oemOid');
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	material.search();
        }
    });
    
    selectCommonCode("spMaterType", "/plm/ext/part/base/getMaterialTypeList.do?clazOid=MAT2000" + "&spMaterMaker="); //비철
    
    selectCommonCode("spMaterNotFe", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=MAT2000" + "&spMaterMaker=&spMaterType=" );//비철
    
    
    Grids.OnDownloadPage = function(grid,row){
        grid.Data.Page.Format = 'Form';
        grid.Data.Page.Method = 'Post';
        
        grid.Data.Page.Url = '/plm/ext/material/findPagingList.do?sortName=*Sort0';
        
        var param = {
            page : grid.GetPageNum(row),
            formPage : grid.Source.Layout.Data.Cfg.PageLength
        }

        $('input,select').each(function(){
            var name = $(this).attr('name');
            var value = $(this).val();
            param[name] = value;
        });
        grid.Data.Page.Param = param;
    }
    
    $("input:radio[name=gubun]").click(function() {
    	
    	$('#spMaterMaker').prop('selectedIndex', -1);
    	$("#spMaterMaker").multiselect("refresh");
    	
    	$('#spMaterMaker2').prop('selectedIndex', -1);
    	$("#spMaterMaker2").multiselect("refresh");
    	
    	$("#spMaterType").empty();
        $("#spMaterType").multiselect("refresh");
        $("#spMaterialInfo").empty();
        $("#spMaterialInfo").multiselect("refresh");
        $("#spMaterNotFe").empty();
        $("#spMaterNotFe").multiselect("refresh");
        
    	if($(this).val() == '비철'){
    		selectCommonCode("spMaterType", "/plm/ext/part/base/getMaterialTypeList.do?clazOid=MAT2000" + "&spMaterMaker=");
    		
    	    selectCommonCode("spMaterNotFe", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=MAT2000" + "&spMaterMaker=&spMaterType=" );//비철
    		$("#makerTd1").show();
    		$("#makerTd2").hide();
    		
    		$("#spMaterial1").hide();
    		$("#spMaterial2").show();
    		
    		//material.createPaingGrid();
    		$(".delayState").show();
    		
    		$("#gridTab2").hide();
            $("#gridTab1").show();
    	}
        if($(this).val() == '수지'){
        	selectCommonCode("spMaterType", "/plm/ext/part/base/getMaterialTypeList.do?clazOid=MAT1000" + "&spMaterMaker=");
        	
        	selectCommonCode("spMaterialInfo", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=MAT1000" + "&spMaterMaker=&spMaterType=" );//수지
        	$("#makerTd2").show();
        	$("#makerTd1").hide();
        	
        	$("#spMaterial2").hide();
            $("#spMaterial1").show();
        	//material.createPaingGrid2();
        	
            $("#gridTab1").hide();
            $("#gridTab2").show();
            
            if(reloadFlag == 0){
            	Grids[1].Reload();
            	reloadFlag++;
            	treeGridResize("#searchGrid2","#listGrid2");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
            }
            
        }
    });
    
    Grids.OnAfterValueChanged = function(grid, row, col, val){
    	//alert(val);
    }
    
    
    // 원재료 Maker 먼저선택 > 원재료 Type > 재질(수지), 재질(비철)
/*     $("#spMaterMaker").change(function(){//비철
        if($(this).val() == ""){
            
        }else{
            var spMaterMaker = $(this).val();
            selectCommonCode("spMaterType", "/plm/ext/part/base/getMaterialTypeList.do?clazOid=MAT2000" + "&spMaterMaker=" + spMaterMaker );
        }
    });
    
    $("#spMaterMaker2").change(function(){//수지
        if($(this).val() == ""){
            
        }else{
            var spMaterMaker = $(this).val();
            selectCommonCode("spMaterType", "/plm/ext/part/base/getMaterialTypeList.do?clazOid=MAT1000" + "&spMaterMaker=" + spMaterMaker );
        }
    });
    
    $("#spMaterType").change(function(){
        if($(this).val() == ""){
            
        }else{
        	
        	$("input:radio[name='gubun']").each(function(){
                var isChecked = $(this).is(":checked");
                if(isChecked){
                	gubun = $(this).val();
                }
            });
        	
        	var spMaterMaker = $("select[name=spMaterMaker2]").val();	
        	if(gubun == '비철'){
        		spMaterMaker = $("select[name=spMaterMaker]").val();
        	}
            
            var spMaterType = $(this).val();
            
            if(gubun == '수지'){
            	
                selectCommonCode("spMaterialInfo", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=MAT1000" + "&spMaterMaker=" + spMaterMaker + "&spMaterType=" + spMaterType );//수지
            }
            
            if(gubun = '비철'){
                selectCommonCode("spMaterNotFe", "/plm/ext/part/base/getMaterialInfoList.do?clazOid=MAT2000" + "&spMaterMaker=" + spMaterMaker + "&spMaterType=" + spMaterType );//비철
            }
        }
    }); */
    
});

window.syncPart = function(){
	ajaxCallServer("/plm/ext/material/syncMaterialForPartLink", "", function(data){        
    });
}

window.materialSearch = function(){

	material.search();
}

window.openInvestSave = function(){
    getOpenWindow2("/plm/ext/invest/saveInvestPopup", "InvestPopup", 1280, 720);
}

function selectCommonCode(target, targetUrl){
    
    $.ajax({        
        url: targetUrl,     
        async: false,
        dataType: "json",       
        success: function(data) {
            var options, index, select, option;
            // Get the raw DOM object for the select box
            select = document.getElementById(target);
            // Clear the old options
            select.options.length = 0;
            // Load the new options
            options = data.options; // Or whatever source information you're working with
            for (index = 0; index < options.length; ++index) {
                option = options[index];
                select.options.add(new Option(option.text, option.value));
            }
            select.selectedIndex = -1;
            $("#" + target).multiselect('refresh');
            if(target && target == "spMaterType"){
                $("#spMaterialInfo").empty();
                $("#spMaterialInfo").multiselect("refresh");
                $("#spMaterNotFe").empty();
                $("#spMaterNotFe").multiselect("refresh");
            }
       }
    });
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
                                <td class="font_01">원재료DB관리
                                <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
<%-- 			                    <a href="/plm/ext/dms/redirectSG/SG-19-002/DOWN" download target="download"><!-- 시스템가이드 링크 -->
			                        <img src="/plm/portal/images/exclamation.png" class="detailInfo" style="cursor:pointer;" title="정보를 보려면 클릭하세요." />
			                    </a>
			                    <c:if test="${ket:isAdmin() }">
			                    <a href="#" onClick="javascript:getOpenWindow2('/plm/ext/dms/redirectSG/SG-19-002/VIEW', 'SGDocumentPopup', 800, 450);"><!-- 시스템가이드 팝업 -->
			                        <img src="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/images/tree/leaf.gif" title="시스템 가이드 문서 상세정보">
			                    </a>
			                    </c:if> --%>
                                </td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Part
                                <img src="/plm/portal/images/icon_navi.gif">원재료DB관리</td>
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
                        <tr>
                            <c:if test="${AUTH == true}">
                            <td width="5">&nbsp;</td>
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:material.gridSave();" class="btn_blue">
                                                   저장
                                            </a>
                                        </td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <!-- <td width="5">&nbsp;</td>
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:syncPart();" class="btn_blue">
                                                   부품정보수집
                                            </a>
                                        </td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td> -->
                            </c:if>
                            <td width="5">&nbsp;</td>
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:materialSearch();" class="btn_blue">
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
                        <col width="11%" />
                        <col width="15%" />
                        <col width="11%" />
                        <col width="15%" />
                        <col width="11%" />
                        <col width="15%" />
                        <col width="11%" />
                        <col width="15%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">구분</td>
                        <td class="tdwhiteL" colspan="5">
                        <input type="radio" class="Checkbox" name="gubun" id="gubun" value="비철" checked></input>비철 
                        <input type="radio" class="Checkbox" name="gubun" id="gubun" value="수지"></input>수지
                        </td>
		            </tr>
		            <tr>
		                <td class="tdblueL">Maker</td>
		                <td class="tdwhiteL" id="makerTd1" name="makerTd1">
		                    <ket:material id="spMaterMaker" name="spMaterMaker" type="MAKER" clazOid="MAT2000" className="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numberCodeType=""  ketMultiSelect="N" multiple="multiple" value="" />
		                </td>
		                
		                <td class="tdwhiteL" id="makerTd2" name="makerTd2" style="display:none" >
                            <ket:material id="spMaterMaker2" name="spMaterMaker2" type="MAKER" clazOid="MAT1000" className="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numberCodeType=""  ketMultiSelect="N" multiple="multiple" value=""/>
                        </td>
                        
		                <td class="tdblueL masterTD">Type</td>
		                <td class="tdwhiteL masterTD">
                            <select id="spMaterType" name="spMaterType" class="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numbercodetype="" ketmultiselect="N" multiple="multiple"></select>
                        </td>
		                
		                <td class="tdblueL">Grade</td>
		                <td class="tdwhiteL" name="spMaterial1" id="spMaterial1" style="display:none">
		                    <select id="spMaterialInfo" name="spMaterialInfo" class="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numbercodetype="" ketmultiselect="N" multiple="multiple"></select>
		                </td>
		                <td class="tdwhiteL" name="spMaterial2" id="spMaterial2">
                            <select id="spMaterNotFe" name="spMaterNotFe" class="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numbercodetype="" ketmultiselect="N" multiple="multiple"></select>
                        </td>
		                
		            </tr>
                </table>
                <iframe id="download" name="download" align="center" width="0" height="0" border="0" style="display:none" onreadystatechange="hideProcessing();"></iframe>
            </form>
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="gridTab1" name="gridTab1">
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
		    
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="gridTab2" name="gridTab2" style="display:none">
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
                                        <div id="listGrid2" style="WIDTH: 100%; HEIGHT: 100%"></div>
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