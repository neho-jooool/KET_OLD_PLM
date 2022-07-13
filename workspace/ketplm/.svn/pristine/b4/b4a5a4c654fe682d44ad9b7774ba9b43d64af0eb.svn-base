<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/program/program.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//------------ start suggest binding ------------//
    //사용자 suggest
    SuggestUtil.bind('USER', 'programAdmin');
    //차종 suggest
    SuggestUtil.bind('CARTYPE', 'carTypeTxt', 'carType');
    //고객처 suggest
    SuggestUtil.bind('CUSTOMER', 'subContractorTxt', 'subContractor');
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'lastUsingBuyerTxt', 'lastUsingBuyer');
    //------------ end suggest binding ------------//
    // default 한달 설정
    //$('[name=startDate]').val(predate);
    //$('[name=endDate]').val(postdate);
    // Calener field 설정
    CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다.
    //multi select bind
    CommonUtil.multiSelect('state',100);
    //server paging
	Program.createPaingGrid();
	$("form[name=ProgramSearchForm]").keypress(function(e) {
        //Enter key
        if ( e.which == 13 ) {
        	Program.search();
            return false;
        }
    });
	
	treeGridResize("#ProgramGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	
});

window.selectOneSubContractor = function(returnValue){
    $('[name=subContractor]').val(returnValue[0][0]);
    $('[name=subContractorTxt]').val(returnValue[0][2]);
}

window.setCarType = function(returnValue){
    
    $('[name=carType]').val(returnValue[0][0]);
    $('[name=carTypeTxt]').val(returnValue[0][1]);
    
}

window.setUser1 = function(returnValue){
    var infoArr = returnValue[0];
    $('[name=programAdmin]').val(infoArr[4]);
    
}

window.insertLastUsingBuyer = function(arrObj){
    $('[name=lastUsingBuyer]').val(arrObj[0][0]);
    $('[name=lastUsingBuyerTxt]').val(arrObj[0][2]);
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
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03343") %><!-- 프로그램 검색 --></td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home 
                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "09200") %><!-- 프로그램 관리 -->
                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03343") %><!-- 프로그램 검색 --></td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <form name="ProgramSearchForm">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <input type="radio" name="divisionCode" value="CA" <%--<%=(CommonUtil.isMember("자동차사업부")?"checked":"disabled")%>--%>><%=messageService.getString("e3ps.message.ket_message", "02391") %><!-- 자동차 --> 
                                    <input type="radio" name="divisionCode" value="ER" <%--<%=(CommonUtil.isMember("전자사업부")?"checked":"disabled")%>--%>><%=messageService.getString("e3ps.message.ket_message", "02478") %><!-- 전자 --> 
                                    <input type="radio" name="divisionCode" value="KA" <%=(CommonUtil.isMember("KETS")?"checked":"disabled")%>>KETS<!-- KETS --> 
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <%
                                boolean isPMO = CommonUtil.isMember("프로그램관리") || CommonUtil.isAdmin();
                                if(isPMO){
                                %>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:Program.goCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03035") %><!-- 프로그램 등록 --></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <%} %>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:Program.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><!-- 초기화 --></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Program.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><!-- 검색 --></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
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
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <table style="width: 100%;">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07295") %><!-- 프로그램 No --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="programNo" class="txt_field" style="width: 98%;"></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09201") %><!-- 프로그램 관리자 --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="programAdmin" class="txt_field" style="width: 77%;">
                        <a href="javascript:SearchUtil.selectOneUser('','','setUser1');">
                        <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('programAdmin','programAdminName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><!-- 상태 --></td>
                    <td class="tdwhiteL">
                        <select class="fm_jmp" id="state" name="state" style="width: 170px;" multiple="multiple" >
                            <option value="PLAN"><%=messageService.getString("e3ps.message.ket_message", "00798") %></option><!-- 계획 -->
                            <option value="PROGRESS"><%=messageService.getString("e3ps.message.ket_message", "02726") %></option><!-- 진행 -->
                            <option value="COMPLETED"><%=messageService.getString("e3ps.message.ket_message", "02171") %></option><!-- 완료 -->
                            <option value="STOPED"><%=messageService.getString("e3ps.message.ket_message", "02695") %></option><!-- 중지 -->
                            <option value="WITHDRAWN"><%=messageService.getString("e3ps.message.ket_message", "02887") %></option><!-- 취소 -->
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09202") %><!-- 프로그램 명 --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="programName" class="txt_field" style="width: 98%;"></td> 
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01248") %><!-- 대표차종 --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="carTypeTxt" class="txt_field" style="width: 77%;"> 
                        <input type="hidden" name="carType">
                        <a href="javascript:SearchUtil.selectCarType('','','setCarType');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('carTypeTxt','carType');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>                           
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><!-- 고객처 --></td>
                    <td class="tdwhiteL"><input type="text" name="subContractorTxt" class="txt_field" style="width: 77%">
                        <input type="hidden" name="subContractor">
                        <a href="javascript:SearchUtil.selectOneSubContractorPopUp('selectOneSubContractor');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('subContractorTxt','subContractor');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><!-- 최종사용처 --></td>
                    <td class="tdwhiteL"><input type="text" name="lastUsingBuyerTxt" class="txt_field" style="width: 77%">
                        <input type="hidden" name="lastUsingBuyer"><a href="javascript:SearchUtil.selectOneLastUsingBuyerPopup('insertLastUsingBuyer');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('lastUsingBuyerTxt','lastUsingBuyer');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09203") %><!-- 프로그램 시작일 --></td>
                    <td class="tdwhiteL" colspan="3">
                        <input type="text" name="startDate" class="txt_field" style="width: 80px;" value="">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('startDate');" style="cursor: hand;"> 
                        ~ 
                        <input type="text" name="endDate" class="txt_field" style="width: 80px;">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('endDate');"
                        style="cursor: hand;"></td>
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
                                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%;min-height:200px;"></div>
                                </div>
                            </div>
                        </div>
                        <!-- EJS TreeGrid End -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   