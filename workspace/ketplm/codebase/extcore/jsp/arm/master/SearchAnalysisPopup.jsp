<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/arm/arm.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    //------------ start suggest binding ------------//
    //사용자 suggest
    SuggestUtil.bind('USER', 'fstChargeName', 'fstCharge');
    //부서 suggest
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    //제품 Project No suggest
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
    //차종 suggest
    SuggestUtil.bind('CARTYPE', 'carTypeText', 'carType');
    //------------ end suggest binding ------------//
    // Calener field 설정
        // Calener field 설정
    CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');
    CalendarUtil.dateInputFormat('endStartDate', 'endEndDate');

    // multiselect
    CommonUtil.singleSelect('process',100);
    CommonUtil.singleSelect('analysisUse',100);
    CommonUtil.multiSelect('analysisDivision',100);

    //server paging
    Arm.createPaingPopupGrid();
    
    // Enter 검색
    $("form[name=ArmSearchForm]").keypress(function(e) {
        if (e.which == 13) {
            Arm.search();
            return false;
        }
    });
});
</script>
<form name="ArmSearchForm">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">제품검토의뢰 검색</td>
                            </tr>
                        </table></td>
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
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:Arm.clear();" class="btn_blue">초기화</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Arm.searchPopup();" class="btn_blue">검색</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Arm.selectArm();" class="btn_blue">확인</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <!-- [END] button -->
            <!-- [START] search condition -->
            <table style="width: 100%;">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="tdblueL">제품검토의뢰서NO<%-- 해석의뢰서NO --%></td>
                        <td width="*" class="tdwhiteL"><input type="text" name="analysisRequestNo" class="txt_field" style="width: 100%"></td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03104") %><%-- 프로젝트NO --%></td>
                        <td width="*" class="tdwhiteL"><input type="text" name="projectNo" class="txt_field" style="width: 100%"><input type="hidden" name="projectOid"></td>
                        <td class="tdblueL">용도<%-- 해석용도 --%></td>
                        <td width="*" class="tdwhiteL"><ket:select id="analysisUse" name="analysisUse" className="fm_jmp" style="width: 170px;" codeType="ANALYSISUSE" multiple="multiple"/></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">구분</td>
                        <td class="tdwhiteL"><ket:select id="analysisDivision" name="analysisDivision" className="fm_jmp" style="width: 170px;" codeType="ANALYSISDIVISION" multiple="multiple"/></td>                
                        <td class="tdblueL">개발단계</td>
                        <td class="tdwhiteL"><ket:select id="process" name="process" className="fm_jmp" style="width: 170px;" codeType="PROCESS" multiple="multiple"/></td>
                        <td class="tdblueL">고객처</td>
                        <td class="tdwhiteL">
                            <input type="text" name="customerDepartmentName" class="txt_fieldRO" style="width: 70%" readonly>
                            <input type="hidden" name="customerDepartment">
                            <a href="javascript:SearchUtil.selectOneSubContractor('customerDepartment','customerDepartmentName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('customerDepartment','customerDepartmentName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">고객담당자</td>
                        <td class="tdwhiteL"><input type="text" name="customerChargeName" class="txt_field" style="width: 100%"></td>
                        <td class="tdblueL">차종</td>
                        <td class="tdwhiteL">
                            <input type="text" name="carTypeText" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="carType">
                            <a href="javascript:SearchUtil.selectCarType('carType','carTypeText');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('carType','carTypeText');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">담당부서</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ketChargeDepartmentName" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="ketChargeDepartment"> 
                            <a href="javascript:SearchUtil.addDepartment(true,'ketChargeDepartment', 'ketChargeDepartmentName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('ketChargeDepartment','ketChargeDepartmentName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">담당자</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ketChargeUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="ketChargeUser"> 
                            <a href="javascript:SearchUtil.selectOneUser('ketChargeUser','ketChargeUserName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('ketChargeUser','ketChargeUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">의뢰부서</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ketClientDepartmentName" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="ketClientDepartment"> 
                            <a href="javascript:SearchUtil.addDepartment(true,'ketClientDepartment', 'ketClientDepartmentName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('ketClientDepartment','ketClientDepartmentName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">의뢰자</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ketClientUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="ketClientUser"> 
                            <a href="javascript:SearchUtil.selectOneUser('ketClientUser','ketClientUserName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('ketClientUser','ketClientUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                </table>
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
                        <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>   