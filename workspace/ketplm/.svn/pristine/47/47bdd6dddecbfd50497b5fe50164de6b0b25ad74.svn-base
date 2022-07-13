<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/sales/project/salesProject.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<%
boolean isAdmin = CommonUtil.isAdmin();
%>
<script type="text/javascript">
$(document).ready(function(e) {

    sales.createPaingGrid();
    
    var G = Grids[0];
    G.Data.Layout.Data.Cfg.SelectingSingle = "1"; 
    
    CommonUtil.multiSelect('stateCode',130); //결재상태
    
    CommonUtil.multiSelect('rankOid', 140); //중요도
    CommonUtil.multiSelect('developType', 140); //프로젝트 유형
    CommonUtil.multiSelect('competitorCompany', 140); //경쟁사
    CommonUtil.multiSelect('nation', 140); //국가
    CommonUtil.multiSelect('salesStateOid', 140); //프로젝트상태
    
    
    CalendarUtil.dateInputFormat('sopStartDate'); //sopStartDate
    CalendarUtil.dateInputFormat('sopEndDate'); //sopEndDate
    
    CalendarUtil.dateInputFormat('createDateFrom', 'createDateTo');
    // default 한달 설정
    $('[name=createDateFrom]').val(predate);
    $('[name=createDateTo]').val(postdate);
    
    
    SuggestUtil.bind('CARTYPE', 'tempmodel', 'model');
    SuggestUtil.bind('USER', 'pmName', 'pmOid');
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
            sales.search();
        }
    });
    treeGridResize("#SalesSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});


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

function loadPartProperty(checkedNode){
    
    var nodeOIdStr='', nodeNameStr='';
    for(var i=0; i < checkedNode.length; i++){
        if(i == checkedNode.length - 1){
            nodeOIdStr += checkedNode[i].id;
            nodeNameStr += checkedNode[i].name;
        }else{
            nodeOIdStr += checkedNode[i].id+','; 
            nodeNameStr += checkedNode[i].name+',';
        }
    }
    
    var valueField = 'partClazOid';
    var displayField = 'partClazKrName';
    $('[name='+valueField+']').val(nodeOIdStr);//oid
    $('[name='+displayField+']').val(nodeNameStr);//kr name
}

//single 선택 확인 버튼 클릭 시
function selectProject() {
    try{
        
        var G = Grids[0];
        
        var arr = new Array();
        var subArr = new Array();
        //var idx = 0;

        if( G != null ){
            
            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('프로젝트를 선택하십시오.');
                return;
            }
            

            for ( var i=0; i<R.length; i++ ) {
                 subArr = new Array();

                 subArr[0] = R[i].oid; //oid
                 subArr[1] = R[i].projectNo; //number
                 subArr[2] = R[i].projectName; //name
                 subArr[3] = R[i].stateName; //결재상태
                 subArr[4] = R[i].salesStateName; //프로젝트상태

                 arr.push(subArr);
            }
        }

        
        
        
        try {
        	if(opener && opener.${fncall}){
                //alert('opener.${fncall}(arr);' + arr);
                opener.${fncall}(arr);
            }
            opener.hideProcessing();
        } catch(e) {}
        window.close();
        
        
        
      }catch(e){
    	 opener.hideProcessing();
         alert(e.message);      
      }

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
                                <td class="font_01">영업프로젝트 검색</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Project
                                <img src="/plm/portal/images/icon_navi.gif">영업
                                <img src="/plm/portal/images/icon_navi.gif">영업프로젝트 검색</td>
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
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:selectProject();" class="btn_blue">확인</a></td>
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
                                            onclick="javascript:sales.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
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
                                            href="javascript:sales.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
                                            onclick="javascript:opener.hideProcessing(); window.close();" class="btn_blue">닫기</a></td>
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
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL">
                            <input type="text" name="projectNo" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL">프로젝트 명</td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="projectName" class="txt_field" style="width: 98%">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">Rev</td>
                        <td class="tdwhiteL">
                            <input type="text" name="rev" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL">중요도<span class="red">*</td>
                        <td class="tdwhiteL">
                          <ket:select id="rankOid" name="rankOid" className="fm_jmp" value="" codeType="NUMBERINGCHARICS" multiple='multiple'/>
                        </td>
                        <td class="tdblueL">프로젝트 유형<span class="red">*</td>
                        <td class="tdwhiteL">
                          <ket:select id="developType" name="developType" value="" className="fm_jmp"  codeType="DEVELOPENTTYPE" multiple='multiple' esse='true' esseLabel='프로젝트 유형' />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">적용부</td>
                        <td class="tdwhiteL">
                            <input type="text" name="occurPlaceName" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL">SOP</td>
                        <td class="tdwhiteL">
                            <input type="text" name="sopStartDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('sopStartDate');" style="cursor:pointer;cursor:hand;"> 
                            ~ 
                            <input type="text" name="sopEndDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('sopEndDate');" style="cursor:pointer;cursor:hand;">
                        </td>
                        <td class="tdblueL">국가</td>
                       <td class="tdwhiteL">
                           <ket:select id="nation" name="nation" value="" className="fm_jmp"  codeType="DEVELOPENTTYPE" multiple='multiple' esse='true' esseLabel='국가'/>
                       </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">경쟁사</td>
                        <td class="tdwhiteL">
                           <ket:select id="competitorCompany" name="competitorCompany" value="" className="fm_jmp"  codeType="DEVELOPENTTYPE" multiple='multiple' />
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
                        <td class="tdwhiteL">
                            <!-- 
                            <ket:select id="customerName" name="customerName" className="fm_jmp" style="width: 170px;" codeType="CUSTOMER" multiple="multiple"/>
                             -->
                            <input type="text" name="customerName" class="txt_fieldRO" style="width: 70%" value="${dqm.customerName}" readonly>
                            <input type="hidden" name="customerCode" value="${dqm.customerCode}">
                            <a href="javascript:SearchUtil.selectMultiSubContractor(selectMultiSubContractor);">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('customerCode','customerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01248")%><%--대표차종--%><span class="red">*</td>
                        <td class="tdwhiteL">
                          <div id="modelDiv">
                          <input type="text" id="tempmodel" name="tempmodel" class="txt_field" style="width: 70%">
                          <input type="hidden" id="model" name="model" value="" esse='true' esseLabel='대표차종'>
                          <a href="javascript:SearchUtil.selectCarType('model','tempmodel');"><img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('model','tempmodel');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">자동차사<span class="red">*</td>
                        <td class="tdwhiteL">
                            <input type="text" name="lastBuyerName" class="txt_fieldRO" style="width: 70%" readonly>
                            <input type="hidden" name="lastBuyerCode" esse='true' esseLabel='자동차사'>
                            <a href="javascript:SearchUtil.selectOneLastUsingBuyer('lastBuyerCode','lastBuyerName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('lastBuyerCode','lastBuyerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">부품분류</td>
                        <td class="tdwhiteL">
                        <input type="text" id="partClazKrName" name="partClazKrName" class="txt_field" >&nbsp;&nbsp;
                        <input type="hidden" id="partClazOid" name="partClazOid" value="" >
                        <a href="javascript:SearchUtil.selectPartClaz(loadPartProperty,'onlyLeaf=N&openAll=N');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;
                        <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="pmName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="pmOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('pmOid','pmName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('pmOid','pmName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429")%><%--작성일자--%></td>
                        <td class="tdwhiteL0"><input type="text" name="createDateFrom" class="txt_field" style="width: 80px;" value="">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateFrom');" style="cursor: hand;"> ~ 
                        <input type="text" name="createDateTo" class="txt_field" style="width: 80px;">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateTo');" style="cursor: hand;">
                        </td>
                        <td class="tdblueL">프로젝트 상태</td>
                        <td class="tdwhiteL">
                            <ket:select id="salesStateOid" name="salesStateOid" className="fm_jmp" value="" codeType="SALESPJTSTATE" multiple='multiple'/>
                        </td>
                        <td class="tdblueL">결재상태</td>
                        <td class="tdwhiteL">
                        <ket:select id="stateCode" name="stateCode" className="fm_jmp" style="width: 170px;" lifeCycleState="KET_COMMON_LC" multiple="multiple" />
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