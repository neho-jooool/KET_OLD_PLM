<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
    
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script type="text/javascript" src="/plm/extcore/js/cost/costErpInterfaceList.js?ver=1"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
	
    SuggestUtil.bind('PRODUCTPARTNO', 'partNo');
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
    
    CommonUtil.multiSelect('drSteps',130);
    CommonUtil.multiSelect('transferFlags',130);
    CommonUtil.singleSelect('lastest',130);
    cost.createPaingGrid();
    treeGridResize("#interfaceSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
            cost.search();
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
    var tempRelatedPart = $('[name=partNo]').val();
    
    if(tempRelatedPartOid != ""){
        tempRelatedPartOid = tempRelatedPartOid+",";
    }
    if(tempRelatedPart != ""){
        tempRelatedPart = tempRelatedPart+",";
    }
    
    $('[name=relatedPartOid]').val(tempRelatedPartOid+trArr[0]);
    $('[name=partNo]').val(tempRelatedPart+trArr[1]);
}

function updateCostHistoryLatest(){
	if(!selCheck()){
        return;
    }
    
    if(confirm("선택된 Part에 대한 최종 전송대상 플래그를 업데이트 하시겠습니까?\r\n\r\n이 작업은 기계산된 원가에 영향을 주지 않습니다.\r\n\r\n변경 데이터 ERP전송은 별도로 진행해야합니다.")){
        
        ajaxCallServer("/plm/ext/cost/updateCostHistoryLatest", getGridJsonData(), function(data){
            cost.search();
        });
    }
}

function costProductHistoryCreate(flag){
	var param = {state : flag};
	if(confirm("전송대상을 집계하시겠습니까?\r\n\r\n해당작업은 ERP전송이 아니며 전송대상만 집계합니다.")){
		
		ajaxCallServer("/plm/ext/cost/costProductHistoryCreate", param, function(data){
	        cost.search();
	    });
	}
}

function costProductSendErp(){

	var param = {};
    if(confirm("ERP 일괄전송하시겠습니까?\r\n\r\n* 전송대상 *\r\n\r\n(1)DR단계별 최신 버전의 데이터\r\n(2)미전송되었거나 전송실패한 데이터 ")){
        
        ajaxCallServer("/plm/ext/cost/costProductSendErp", param, function(data){
            cost.search();
        });
    }
}


function getGridJsonData(){
	var G = Grids[0];
    
    var arr = new Array();
    
    if( G != null ){
        
        var R = G.GetSelRows();
        
        for ( var i=0; i<R.length; i++ ) {
            
            var row = {};
            row.oid = R[i].oid;
            arr.push(row);
        }
    }
    var param = {};
    param.reSendArrList = JSON.stringify(arr);
        
    return param;
}

function deleteErpPart(){
	if(!selCheck()){
        return;
    }
    
    if(confirm("선택된 Part를 ERP에서 삭제하시겠습니끼?\r\n\r\n삭제 후 ERP전송은 별도로 진행해야합니다.")){
        
        ajaxCallServer("/plm/ext/cost/deleteErpPart", getGridJsonData(), function(data){
            cost.search();
        });
    }
}

function reCalaulate(){
	if(!selCheck()){
		return;
	}
	
	if(confirm("선택된 Part를 재계산하시겠습니까?\r\n\r\n전송대상 데이터에 대한 재계산이 진행되며 기계산된 원가에 영향을 주지 않습니다.\r\n\r\n변경 데이터 ERP전송은 별도로 진행해야합니다.")){
		
		ajaxCallServer("/plm/ext/cost/reCalaulate", getGridJsonData(), function(data){
            cost.search();
        });
    }
}

function reCalaulateAll(){
	var param = {};
    if(confirm("모든 부품에 대해 일괄 재계산하시겠습니까?\r\n\r\n모든 데이터에 대한 재계산이 진행되며 기계산된 원가에 영향을 주지 않습니다.\r\n\r\n변경 데이터 ERP전송은 별도로 진행해야합니다.")){
        
        ajaxCallServer("/plm/ext/cost/reCalaulateAll", param, function(data){
            cost.search();
        });
    }
}



function selCheck(){
	var R = Grids[0].GetSelRows();
       
    if( R.length == 0 ){
        alert('항목을 선택하세요.');
        return false;
    }
    
    return true;
}

function reSendErp(){

	if(!selCheck()){
        return;
    }
	if(!confirm("선택된 Part를 ERP 재전송하시겠습니까?")){
		return false;
	}
	
	ajaxCallServer("/plm/ext/cost/reSendErp", getGridJsonData(), function(data){
        
        cost.search();
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
                                <td class="font_01">ERP인터페이스</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">원가관리
                                <img src="/plm/portal/images/icon_navi.gif">Report
                                <img src="/plm/portal/images/icon_navi.gif">ERP인터페이스</td>
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
            <c:if>
            
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <c:if test="${isAdmin}">
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:deleteErpPart()" class="btn_blue">삭제(ERP)</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            </c:if>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:reCalaulate()" class="btn_blue">재계산</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            
                            <td id="bt1" name="bt1">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:reSendErp()" class="btn_blue">ERP재전송</a></td>
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
                                            onclick="javascript:costProductHistoryCreate('ALL')" class="btn_blue">집계(ALL)</a></td>
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
                                            onclick="javascript:costProductHistoryCreate('')" class="btn_blue">집계(미완료PJT)</a></td>
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
                                            onclick="javascript:reCalaulateAll()" class="btn_blue">일괄재계산</a></td>
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
                                            onclick="javascript:costProductSendErp()" class="btn_blue">ERP일괄전송</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            
                            <td width="5">&nbsp;</td>
                            
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:updateCostHistoryLatest()" class="btn_blue">최종여부 플래그 업데이트</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            
                            
                            <td id="bt2" name="bt2">
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:cost.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                   </tr>
                               </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td id="bt3" name="bt3">
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:cost.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
                <input type='hidden' id='costPartNos' name='costPartNos' value='${costPartNos}'/>
                <input type='hidden' id='pjtNos' name='pjtNos' value='${pjtNos}'/>
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                
                <table border="0" cellspacing="0" cellpadding="0" width="100%" id='searchTab'>
                    <colgroup>
                        <col width="8%" />
                        <col width="10%" />
                        <col width="5%" />
                        <col width="10%" />
                        <col width="5%" />
                        <col width="10%" />
                        <col width="5%" />
                        <col width="10%" />
                        <col width="7%" />
                        <col width="10%" />
                        <col width="5%" />
                        <col width="5%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL">
                          <input type="text" name="pjtNo" class="txt_field" style="width:70%" value="">
                          <input type="hidden" name="pjtOid" value="">
                          <a href="javascript:SearchUtil.selectOneProject(cost.setProjectNo);">
                          <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('pjtNo','pjtOid');">
                          <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09005") %><%--부품 NO--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="partNo" id="partNo" class="txt_field" style="width: 70%">
                            <input type="hidden" name="relatedPartOid">
                            <a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc','pType=P');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partNo', 'relatedPartOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">DR단계</td>
                        <td class="tdwhiteL">
                            <select onBlur="fm_jmp" id="drSteps" name="drSteps" multiple="multiple" style="width:170px">
                               <option value="DR0">DR0</option>
                               <option value="DR1">DR1</option>
                               <option value="DR2">DR2</option>
                               <option value="DR3">DR3</option>
                               <option value="DR4">DR4</option>
                               <option value="DR5">DR5</option>
                               <option value="DR6">DR6</option>
                            </select>
                        </td>
                        <td class="tdblueL">버전</td>
                        <td class="tdwhiteL">
                            <select onBlur="fm_jmp" id="lastest" name="lastest" style="width:170px">
                               <option value="1">최신</option>
                               <option value="">전체</option>
                            </select>
                        </td>
                        <td class="tdblueL">ERP전송여부</td>
                        <td class="tdwhiteL">
                            <select onBlur="fm_jmp" id="transferFlags" name="transferFlags" multiple="multiple" style="width:170px">
                               <option value="N">전송대기</option>
                               <option value="Y">전송완료</option>
                               <option value="E">전송오류</option>
                            </select>
                        </td>
                        <td class="tdblueL">원가차이</td>
                        <td class="tdwhiteL">
                        <input type="checkbox" name="gap" id="gap" value="1">
                        </td>
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

                            <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>