<%@page import="e3ps.project.ProductProject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
String projectOid = request.getParameter("projectOid");
String carTypeName = "";
String carTypeOid = "";
if(projectOid != null){
    ProductProject project = (ProductProject)CommonUtil.getObject(projectOid);
    if(project.getOemType() != null){
        carTypeName = project.getOemType().getName();
        carTypeOid = CommonUtil.getOIDString(project.getOemType());
    }
}
%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/program/program.js"></script>
<script type="text/javascript">
var callBackFn = '<%=request.getParameter("callBackFn")%>';
$(document).ready(function(){	
	$(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "03343") %><%-- 프로그램 검색 --%>');
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
	Program.createPaingGridPopup();
	$("form[name=ProgramSearchForm]").keypress(function(e) {
        //Enter key
        if ( e.which == 13 ) {
            Program.search();
            return false;
        }
    });
});
</script>
<table style="width: 100%; height: 100%">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03343") %><%-- 프로그램 검색 --%></td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
                                    <input type="radio" name="divisionCode" value="CA" <%=(CommonUtil.isMember("자동차사업부")?"checked":"disabled")%>><%=messageService.getString("e3ps.message.ket_message", "02391") %><!-- 자동차 --> 
                                    <input type="radio" name="divisionCode" value="ER" <%=(CommonUtil.isMember("전자사업부")?"checked":"disabled")%>><%=messageService.getString("e3ps.message.ket_message", "02478") %><!-- 전자 --> 
                                    <input type="radio" name="divisionCode" value="KA" <%=(CommonUtil.isMember("KETS")?"checked":"disabled")%>>KETS<!-- KETS -->
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Program.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><!-- 초기화 --></a></td>
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
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:Program.selectProgram();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><!-- 선택 --></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><!-- 닫기 --></a></td>
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
                    <td width="80" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07295") %><!-- 프로그램 No --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="programNo" class="txt_field" style="width: 98%;"></td>
                    <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09201") %><!-- 프로그램 관리자 --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="programAdmin" class="txt_field" style="width: 69%;">
                        <a href="javascript:SearchUtil.selectOneUser(null,'programAdmin');">
                        <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('programAdmin','programAdminName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    </td>
                    <td width="50" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><!-- 상태 --></td>
                    <td class="tdwhiteL">
                        <select class="fm_jmp" id="state" name="state" style="width: 120px;" multiple="multiple" >
                            <option value="PLANNING"><%=messageService.getString("e3ps.message.ket_message", "00798") %><!-- 계획 --></option>
                            <option value="PROGRESS"><%=messageService.getString("e3ps.message.ket_message", "02726") %><!-- 진행 --></option>
                            <option value="COMPLETED"><%=messageService.getString("e3ps.message.ket_message", "02171") %><!-- 완료 --></option>
                            <option value="STOPED"><%=messageService.getString("e3ps.message.ket_message", "02695") %><!-- 중지 --></option>
                            <option value="WITHDRAWN"><%=messageService.getString("e3ps.message.ket_message", "02887") %><!-- 취소 --></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09202") %><!-- 프로그램 명 --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="programName" class="txt_field" style="width: 98%;"></td> 
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><!-- 차종 --></td>
                    <td class="tdwhiteL">
                        <input type="text" name="carTypeTxt" class="txt_field" style="width: 69%;" value="<%=carTypeName%>">
                        <input type="hidden" name="carType" value="<%=carTypeOid%>">
                        <a href="javascript:SearchUtil.selectCarType('carType','carTypeTxt');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a>
                        <a href="javascript:CommonUtil.deleteValue('carTypeTxt','carType');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><!-- 고객처 --></td>
                    <td class="tdwhiteL"><input type="text" name="subContractorTxt" class="txt_field" style="width: 70%">
                        <input type="hidden" name="subContractor">
                        <a href="javascript:SearchUtil.selectOneSubContractor('subContractor','subContractorTxt');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('subContractorTxt','subContractor');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><!-- 최종사용처 --></td>
                    <td class="tdwhiteL"><input type="text" name="lastUsingBuyerTxt" class="txt_field" style="width: 69%">
                        <input type="hidden" name="lastUsingBuyer"><a href="javascript:SearchUtil.selectOneLastUsingBuyer('lastUsingBuyer','lastUsingBuyerTxt');">
                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                        <a href="javascript:CommonUtil.deleteValue('lastUsingBuyerTxt','lastUsingBuyer');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09203") %><!-- 프로그램 시작일 --></td>
                    <td class="tdwhiteL" colspan="3">
                        <input type="text" name="startDate" class="txt_field" style="width: 99px;" value="">
                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('startDate');" style="cursor: hand;"> 
                        ~ 
                        <input type="text" name="endDate" class="txt_field" style="width: 99px;">
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
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
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