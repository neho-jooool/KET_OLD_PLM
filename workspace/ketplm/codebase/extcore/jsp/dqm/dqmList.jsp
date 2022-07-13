<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/dqm/dqm.js?ver=0.5"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<%
boolean isAdmin = CommonUtil.isAdmin();
%>
<script type="text/javascript">
$(document).ready(function(e) {
	SuggestUtil.bind('PRODUCTPARTNO', 'relatedPart');
	SuggestUtil.bind('PRODUCTPROJNO', 'pjtno');
	SuggestUtil.bind('CARTYPE', 'cartypeName');
	SuggestUtil.bind('USER', 'raiserUserName');
	SuggestUtil.bind('USER', 'actionUserName');
    
	CalendarUtil.dateInputFormat('createStartDate','createEndDate'); //기한 설정시 start와 end field를 설정한다.
	CalendarUtil.dateInputFormat('compStartDate','compEndDate'); //기한 설정시 start와 end field를 설정한다.
	
	CommonUtil.multiSelect('occurCode',170);
    CommonUtil.multiSelect('defectDivCode',170);
    CommonUtil.multiSelect('defectTypeCode',170);
    CommonUtil.multiSelect('causeCode',170);
    CommonUtil.multiSelect('issueCode',170);
    CommonUtil.multiSelect('occurPointCode',170);
    
    dqm.createPaingGrid();
    treeGridResize("#DqmSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    CommonUtil.multiSelect('dqmStateName',130);
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	dqm.search();
        }
    });
});

function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    for(var i = 0; i < objArr.length; i++)
    {
    	setPart(objArr[i]);
    }
}

function setPart(objArr){
	trArr = objArr;
	var tempRelatedPartOid = $('[name=relatedPartOid]').val();
    var tempRelatedPart = $('[name=relatedPart]').val();
	
	if(tempRelatedPartOid != ""){
		tempRelatedPartOid = tempRelatedPartOid+",";
	}
	if(tempRelatedPart != ""){
		tempRelatedPart = tempRelatedPart+",";
    }
	
    $('[name=relatedPartOid]').val(tempRelatedPartOid+trArr[0]);
    $('[name=relatedPart]').val(tempRelatedPart+trArr[1]);
}

function selectMultiSubContractor(returnValue){
    var nodeIdStr='', nodeNameStr='';
    for(var i=0; i < returnValue.length; i++){
            if(i == returnValue.length - 1){
                    nodeIdStr += returnValue[i][0];
                    nodeNameStr += returnValue[i][2];
            }else{
                    nodeIdStr += returnValue[i][0]+',';     
                    nodeNameStr += returnValue[i][2]+',';
            }
    }
    $('[name=customerCode]').val(nodeIdStr);
    $('[name=customerName]').val(nodeNameStr);
}

function migExcelImport(){
	url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp";
	getOpenWindow2('/plm/extcore/jsp/dqm/dqmMigrationForm.jsp','uploadPopup',400,50);
}

function deleteAll(){
	if( confirm("전체삭제하시겠습니까?") ){
		
		$.ajax({
	        url : "/plm/ext/dqm/deleteAll.do",
	        type : "POST",
	        dataType : 'json',
	        async : false,
	        success: function(data) {
	            alert("전체 삭제 하였습니다.");
	        }
	    });
	}
}

window.setCarType = function(returnValue){
	
	$('[name=cartypeCode]').val(returnValue[0][0])//id
    $('[name=cartypeName]').val(returnValue[0][1])//name
	
}

window.setUser1 = function(returnValue){
	
	var infoArr = returnValue[0];
    $('[name=raiserUserOid]').val(infoArr[0]);
    $('[name=raiserUserName]').val(infoArr[4]);
	
}

window.setUser2 = function(returnValue){
    
    var infoArr = returnValue[0];
    $('[name=actionUserOid]').val(infoArr[0]);
    $('[name=actionUserName]').val(infoArr[4]);
    
}

window.setPartClaz = function(returnValue){
	
	if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
	
	$('[name=partCategoryCode]').val(returnValue[0].id);//oid
    $('[name=partCategoryName]').val(returnValue[0].name);//kr name
	
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
                                <td class="font_01">Issue 검색</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Project
                                <img src="/plm/portal/images/icon_navi.gif">Issue 검색</td>
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
                            <%
                            if(isAdmin){
                            %>
                            <!-- 
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:migExcelImport();" class="btn_blue">Migration</a></td>
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
                                            onclick="javascript:deleteAll();" class="btn_blue">품질문제전체삭제</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                             -->
                            <%
                            }
                            %>
	                        <td>
	                            <table border="0" cellspacing="0" cellpadding="0">
	                                <tr>
				                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
				                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
				                            onclick="javascript:dqm.goCreate();" class="btn_blue">Issue 제기</a></td>
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
				                            onclick="javascript:dqm.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
				                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
	                               </tr>
	                           </table>
	                        </td>
			                <td width="5">&nbsp;</td>
			                <td>
			                     <table border="0" cellspacing="0" cellpadding="0">
			                        <tr>
			                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
			                                href="javascript:dqm.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
	                    <col width="13%" />
	                    <col width="20%" />
	                    <col width="13%" />
	                    <col width="20%" />
	                    <col width="13%" />
	                    <col width="21%" />
	                </colgroup>
                    <tr>
                        <td class="tdblueL">
                                Issue No
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="problemNo" class="txt_field" style="width: 98%">
                        </td>
                        
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04630") %><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--문제점구분--%></td>
                            <td class="tdwhiteL">
                            <ket:select id="issueCode" name="issueCode" className="fm_jmp" style="width: 170px;" codeType="DQMISSUETYPE" multiple="multiple" useCodeValue="true"/>
                        </td>
                        <td class="tdblueL">발생시점</td>
                            <td class="tdwhiteL">
                            <ket:select id="occurPointCode" name="occurPointCode" className="fm_jmp" style="width: 170px;" codeType="DQMOCCURPOINT" multiple="multiple" useCodeValue="true" value="${dqm.occurPointCode}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL">
                          <input type="text" name="pjtno" class="txt_field" style="width:70%" value="">
                          <input type="hidden" name="pjtoid" value="">
                          <a href="javascript:showProcessing();javascript:SearchUtil.selectOneProjectPopUp('dqm.setProjectNo');">
                          <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('pjtno','pjtoid');">
                          <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09003") %><%--불량구분--%></td>
                        <td class="tdwhiteL">
                            <ket:select id="defectDivCode" name="defectDivCode" className="fm_jmp" style="width: 170px;" codeType="PROBLEMDIVTYPE" multiple="multiple" useCodeValue="true" onChange="dqm.changeDefectDivCode();" value="${dqm.defectDivCode}"/>
                        <!--<input type="text" name="defectDivName" class="txt_field" style="width:100%" value=""></td>
                         
                            defectDivCode
                            defectDivName
                            defectTypeCode
                            defectTypeName
                         -->
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09004") %><%--불량유형--%></td>
                        <td class="tdwhiteL">
                            <select id="defectTypeCode" name="defectTypeCode" class="fm_jmp" multiple="multiple" style="width:170px">
                            </select>
                            <!-- <input type="text" name="defectTypeName" class="txt_field" style="width:100%" value=""> -->
                        </td>
                    </tr>
                    <tr>
                        <%-- <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09029") %>발생장소
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="occurPlaceName" class="txt_field" style="width: 98%">
                        </td> --%>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09002") %><%--문제내용--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="problem" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07137") %><%--발생처--%></td>
                        <td class="tdwhiteL">
                          <ket:select id="occurCode" name="occurCode" className="fm_jmp" style="width: 170px;" codeType="OCCURPLACE" multiple="multiple" useCodeValue="true" value="${dqm.occurCode}"/>
                          <!-- <input type="text" name="occurName" class="txt_field" style="width:100%" value="">  -->
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09032") %><%--원인--%></td>
                        <td class="tdwhiteL">
                            <ket:select id="causeCode" name="causeCode" className="fm_jmp" style="width: 170px;" codeType="PROBLEMTEAM" multiple="multiple" useCodeValue="true"/>
                        </td>
                    </tr>
                    <%-- <tr>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09025") %>적용부위 1
                        </td>
                        <td  class="tdwhiteL">
                            <input type="text" name="applyArea1" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09026") %>적용부위 2
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="applyArea2" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09027") %>적용부위 3
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="applyArea3" class="txt_field" style="width: 98%">
                        </td>
                    </tr> --%>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%></td>
                        <td class="tdwhiteL">
                            <!-- 
                            <ket:select id="customerName" name="customerName" className="fm_jmp" style="width: 170px;" codeType="CUSTOMER" multiple="multiple"/>
                             -->
                            <input type="text" name="customerName" class="txt_fieldRO" style="width: 70%" value="${dqm.customerName}" readonly>
                            <input type="hidden" name="customerCode" value="${dqm.customerCode}">
                            <a href="javascript:SearchUtil.selectMultiSubContractorPopUp('selectMultiSubContractor');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('customerCode','customerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="cartypeName" class="txt_field" style="width: 30%"> 
                            <input type="hidden" name="cartypeCode">
                            <a href="javascript:SearchUtil.selectCarType('','','setCarType');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('cartypeCode','cartypeName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        
                        <%-- <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "02523") %>제기자
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="actionDeptName" class="txt_field" style="width: 98%">
                            <!--  
                            <input type="text" name="raiserUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="raiserUserOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('raiserUserOid','raiserUserName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('raiserUserOid','raiserUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            -->
                        </td> --%>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09005") %><%--부품 NO--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="relatedPart" class="txt_field" style="width: 70%">
                            <input type="hidden" name="relatedPartOid">
                            <a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc','pType=P');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('relatedPart', 'relatedPartOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "06112") %><%--부품분류--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="partCategoryName" class="txt_fieldRO" style="width:70%" value="${dqm.partCategoryName}" readonly>
                            <input type="hidden" name="partCategoryCode" value="${dqm.partCategoryCode}">
                            <a href="javascript:SearchUtil.selectOnePartClazPopUp('setPartClaz', 'onlyLeaf=Y');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partCategoryCode', 'partCategoryName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09033") %><%--검토자--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="actionUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="actionUserOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('','','setUser2');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('actionUserOid','actionUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="createStartDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createStartDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="createEndDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createEndDate');"
                            style="cursor: hand;">
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="raiserUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="raiserUserOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('','','setUser1');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('raiserUserOid','raiserUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>    
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04600") %><%--완료일--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="compStartDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('compStartDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="compEndDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('compEndDate');"
                            style="cursor: hand;">
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%>
                        </td>
                        <td class="tdwhiteL">
                            <select onBlur="fm_jmp" id="dqmStateName" name="dqmStateName" multiple="multiple" style="width:170px">
                               <option value="지연"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></option>
                               <option value="등록중"><%=messageService.getString("e3ps.message.ket_message", "09034") %><%--등록중--%></option>
                               <option value="제기승인"><%=messageService.getString("e3ps.message.ket_message", "04830") %><%--제기승인--%></option>
                               <option value="담당자접수"><%=messageService.getString("e3ps.message.ket_message", "09035") %><%--담당자접수--%></option>
                               <option value="검토승인"><%=messageService.getString("e3ps.message.ket_message", "09036") %><%--검토승인--%></option>
                               <option value="개선안진행">개선안진행</option>
                               <option value="검토확인"><%=messageService.getString("e3ps.message.ket_message", "09037") %><%--검토확인--%></option>
                               <option value="반려됨"><%=messageService.getString("e3ps.message.ket_message", "01468") %><%--반려됨--%></option>
                               <option value="종결"><%=messageService.getString("e3ps.message.ket_message", "09038") %><%--종결--%></option>
                            </select>
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