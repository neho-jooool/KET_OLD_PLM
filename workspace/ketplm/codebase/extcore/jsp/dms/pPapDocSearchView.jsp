<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
	
	window.selectPartNoPopup = function(targetId){
	    showProcessing();
	    SearchUtil.selectPart("selectPartNo", "pType=P");
	}
	
	window.selectPartNo = function(partArr){
		
		var partNoVal = $("input[name=partNo]").val();
		
		var partNos = "";
		for(var i = 0; i < partArr.length; i++){
			var partNo = partArr[i][1];
			
			if(partNoVal.indexOf(partNo) >= 0){
				continue;
			}
			
			partNos += "," + partNo;
		}
		
		if(partNoVal.length > 0){
			partNoVal += partNos;
		}else{
			partNoVal = partNos.substring(1);
		}
		
		$("input[name=partNo]").val(partNoVal);
	}
	
	window.partListExcelUpload = function(){
		
		if($('input[name=upload]').val() == ""){
			
			alert("엑셀 파일을 첨부하세요.");
			return false;
		}

		var formData = new FormData();
		formData.append('partNoListExcel', $('input[name=upload]')[0].files[0]);
		
		ajaxCallServer("/plm/ext/dms/getPartNoListFromExcel", formData, function(data){
			$("input[name=partNo]").val(data.partNoList);
        });
	}
	
	window.downloadDocFiles = function(isDecrypt){
		
		if(!eval("${AUTH}") && isDecrypt){
			alert("복호화 권한이 없습니다.");
			return;
		}

		if(window.getDownloadItemList){
			
			var dlItemList = window.getDownloadItemList();
			
			if(dlItemList.length == 0){
				alert("다운로드할 파일을 선택하세요.");
				return;
			}
			var param = {
					dlItemList : dlItemList,
					isDecrypt : isDecrypt
			}
			
			if(isDecrypt){
				alert("복호화 다운로드 기록이 서버에 보존됩니다.");
			}
			ajaxCallServer("/plm/ext/dms/downloadDocFileZip", param, function(data){
	            //location.href = data.downloadLink;
	            $("#download")[0].src = data.downloadLink;
	        });
		}
	}
    
	window.search = function(){
		
		var partNo = $("input[name=partNo]").val().replace(/ /g, '');
		var categoryName = $("select[name=categoryName]").val();
		
		var categoryNameMessage = new Array();

        $("select[name=categoryName] option:selected").each(function(i, selected){
        	categoryNameMessage[i] = $(selected).val();
        });
        
		if(partNo == ""){
			alert("부품번호를 입력하세요.");
			return;
		}
		
		if(categoryName == null){
			alert("조회대상을 선택하세요.");
			return;
		}else{
			categoryName = categoryName.join(",");
		}
		
		var url = "/plm/ext/dms/pPapDocSearchList";

		var param = {
				partNo : partNo,
				categoryName : categoryName,
				categoryNameMessage : categoryNameMessage.join(",")
		}
		
		showProcessing();
		$("#categoryName").multiselect("destroy");
		$(".content-main").load(url, param, function(e){
			
			hideProcessing();
			
			$(".content-main").height(getContentmainHeight());
			$(".content-main").width($("body").width());
			
			CommonUtil.multiSelect('categoryName', 200);
		});
	}
	
	window.clearPartNo = function(){
		$("input[name=partNo]").val("");
	}
	
	window.searchClear = function(){
		$("input[name=partNo]").val("");
		$("#categoryName").val("");
		$('#categoryName').multiselect('refresh');
		$("input[name=upload]").val("");
	}
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	search();
        }
    });
    
    CommonUtil.multiSelect('categoryName', 200);

    SuggestUtil.bind('PARTNO', 'partNo');
    
    var partNos = '${partNos}';
    if(partNos != ''){
        var optionList = document.getElementById('categoryName').getElementsByTagName('option');
        for(var i=0; i < optionList.length; i++){
            if(optionList[i].getAttribute('stddoc')){
                optionList[i].selected = 'selected';
            }
        }
        $('#categoryName').multiselect('refresh');  
        search();
    }
    
    $(window).resize(function(){
    	$(".content-main").height(getContentmainHeight());
    	$(".content-main").width($("body").width());
    });
    
    window.getContentmainHeight = function(){
        return $('#document06', parent.document).height() == null ? window.innerHeight-220 : $('#document06', parent.document).height()-220;
    }
    
});
</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09501") %><%--PPAP자료 검색--%>
                                <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
			                    <a href="/plm/ext/dms/redirectSG/SG-19-001/DOWN" download target="download"><!-- 시스템가이드 링크 -->
			                        <img src="/plm/portal/images/exclamation.png" class="detailInfo" style="cursor:pointer;" title="정보를 보려면 클릭하세요." />
			                    </a>
			                    <c:if test="${ket:isAdmin() }">
			                    <a href="#" onClick="javascript:getOpenWindow2('/plm/ext/dms/redirectSG/SG-18-004/VIEW', 'SGDocumentPopup', 800, 450);"><!-- 시스템가이드 팝업 -->
			                        <img src="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/images/tree/leaf.gif" title="시스템 가이드 문서 상세정보">
			                    </a>
			                    </c:if>
                                </td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Document
                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "09501") %><%--PPAP자료 검색--%>
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
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                        	<td><input type="file" name="upload"/></td>
                        	<td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10">
                                        <img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                        <a href="#" onclick="javascript:partListExcelUpload()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09502") %><%--부품 Excel upload--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:searchClear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                   </tr>
                               </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                        <a href="javascript:search();" id="searchBtn" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
            <!-- [END] button -->
            <!-- [START] search condition -->
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <colgroup>
                    <col width="10%" />
                    <col width="20%" />
                    <col width="10%" />
                    <col width="20%" />
                    <col width="10%" />
                    <col width="30%" />
                </colgroup>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호 --%><span class="red">*</span></td>
                    <td class="tdwhiteL" colspan="5">
                    	<input type="text" name="partNo" class="txt_field" style="width:70%" value="${partNos}">
                    	<a href="javascript:selectPartNoPopup();"><img src="/plm/portal/images/icon_5.png"></a>
            			<a href="javascript:clearPartNo();"><img src="/plm/portal/images/icon_delete.gif"></a>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09503") %><%--부품번호 --%> <span class="red">*</span></td>
                    <td class="tdwhiteL">
                    	<select id="categoryName" name="categoryName" class="fm_jmp" multiple="multiple">
                    	    <option value="관리계획서" stddoc='true'><%=messageService.getString("e3ps.message.ket_message", "00944") %><%--관리계획서 --%></option>
                    	    <option value="PFMEA" stddoc='true'>PFMEA</option>
                    	    <option value="제조공정도" stddoc='true'><%=messageService.getString("e3ps.message.ket_message", "02534") %><%--제조공정도 --%></option>
                    	    <option value="작업표준서" stddoc='true'><%=messageService.getString("e3ps.message.ket_message", "02442") %><%--작업표준서 --%></option>
                    	    <option value="검사기준서(사내)" stddoc='true'><%=messageService.getString("e3ps.message.ket_message", "09513") %><%--검사기준서(사내) --%></option>
                            <option value="검사기준서(고객)"><%=messageService.getString("e3ps.message.ket_message", "09514") %><%--검사기준서(고객) --%></option>
                            <option value="DFMEA">DFMEA</option>
                    		<option value="재질성적서"><%=messageService.getString("e3ps.message.ket_message", "09507") %><%--재질성적서 --%></option>
                    		<option value="중금속성적서"><%=messageService.getString("e3ps.message.ket_message", "09508") %><%--중금속성적서 --%></option>
                    		<option value="연소성성적서"><%=messageService.getString("e3ps.message.ket_message", "09510") %><%--연소성성적서 --%></option>
                            <option value="냄새성적서"><%=messageService.getString("e3ps.message.ket_message", "09511") %><%--냄새성적서 --%></option>
                            <option value="VOCs성적서"><%=messageService.getString("e3ps.message.ket_message", "09512") %><%--VOCs성적서 --%></option>
                    		<option value="정기신뢰성성적서"><%=messageService.getString("e3ps.message.ket_message", "09509") %><%--정기신뢰성성적서 --%></option>
                    	</select>
                    </td>
                    <td class="tdwhiteL"></td>
                    <td class="tdwhiteL"></td>
                    <td class="tdwhiteL"></td>
                    <td class="tdwhiteL"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                    	<table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                    	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	                        <tr>
	                            <td style="width:140px;">
	                                <table border="0" cellspacing="0" cellpadding="0">
	                                   <tr>
	                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
	                                            onclick="javascript:downloadDocFiles(false);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09504") %><%--문서다운로드 --%></a></td>
	                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                   </tr>
	                               </table>
	                            </td>
	                            <c:if test="${AUTH == true}">
	                            <td style="width:120px;">
	                                 <table border="0" cellspacing="0" cellpadding="0">
	                                    <tr>
	                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
	                                            href="javascript:downloadDocFiles(true);" class="btn_blue">복호화다운로드</a></td>
	                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                                    </tr>
	                                </table>
	                            </td>
	                            </c:if>
	                            <td width="5">&nbsp;</td>
	                            <td><span style="color:#FF0000;font-size:14px;font-weight:bold;"><%=messageService.getString("e3ps.message.ket_message", "09505") %><%--※ 최종 승인완료된 파일이 다운로드됩니다. --%></span></td>
	                            <td style="text-align:right;"><%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체 개수 --%> : <span class="totalCnt">0</span> <%=messageService.getString("e3ps.message.ket_message", "09506") %><%--(검색 부품기준) --%></td>
	                        </tr>
	                    </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <div class="content-main" style="overflow-x:hidden;overflow-y:auto;">
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
