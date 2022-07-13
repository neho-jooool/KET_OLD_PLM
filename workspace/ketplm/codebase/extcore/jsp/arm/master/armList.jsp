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
	Arm.createPaingGrid();
	treeGridResize("#ArmSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	
	// Enter 검색
    $("form[name=ArmSearchForm]").keypress(function(e) {
        if (e.which == 13) {
        	Arm.search();
            return false;
        }
    });
});

function setSubContractor(returnValue){
    $('[name=customerDepartment]').val(returnValue[0][0]);//id
    $('[name=customerDepartmentName]').val(returnValue[0][2]);//name  
}

function setCarType(returnValue){
	$('[name=carType]').val(returnValue[0][0]);//id
    $('[name=carTypeText]').val(returnValue[0][1]);//name
}

function setDept(rsltArrayObject){
	var code = "";
    var name = "";
    for(var i= 0 ; i < rsltArrayObject.length; i++){
        if(i != 0){
            code += ",";
            name += ",";
        }
        code += rsltArrayObject[i].CODE;
        name += rsltArrayObject[i].NAME;
    }
    $('[name=ketChargeDepartment]').val(code);
    $('[name=ketChargeDepartmentName]').val(name);
}

function setDept2(rsltArrayObject){
    var code = "";
    var name = "";
    for(var i= 0 ; i < rsltArrayObject.length; i++){
        if(i != 0){
            code += ",";
            name += ",";
        }
        code += rsltArrayObject[i].CODE;
        name += rsltArrayObject[i].NAME;
    }
    $('[name=ketClientDepartment]').val(code);
    $('[name=ketClientDepartmentName]').val(name);
}

function setUser1(attacheMembers){
	var infoArr = attacheMembers[0];
    $('[name=ketChargeUser]').val(infoArr[0]);
    $('[name=ketChargeUserName]').val(infoArr[4]);
}

function setUser2(attacheMembers){
	
    var infoArr = attacheMembers[0];
    $('[name=ketClientUser]').val(infoArr[0]);
    $('[name=ketClientUserName]').val(infoArr[4]);
}

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
                                <td class="font_01">해석의뢰 검색</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home 
                                <img src="/plm/portal/images/icon_navi.gif">관리메뉴
                                <img src="/plm/portal/images/icon_navi.gif">해석의뢰</td>
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
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:Arm.goCreate();" class="btn_blue">등록</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <td width="5">&nbsp;</td>
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
                                                href="javascript:Arm.search();" class="btn_blue">검색</a></td>
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
                        <td class="tdblueL">의뢰서NO<%-- 해석의뢰서NO --%></td>
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
                        <td class="tdblueL">접점고객</td>
                        <td class="tdwhiteL">
                            <input type="text" name="customerDepartmentName" class="txt_fieldRO" style="width: 70%" readonly>
                            <input type="hidden" name="customerDepartment">
                            <a href="javascript:SearchUtil.selectOneSubContractorPopUp('setSubContractor');">
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
                            <a href="javascript:SearchUtil.selectCarType('carType','carTypeText','setCarType');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('carType','carTypeText');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">담당부서</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ketChargeDepartmentName" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="ketChargeDepartment"> 
                            <a href="javascript:SearchUtil.addDepartmentAfterFunc(true,'setDept');">
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
                            <a href="javascript:SearchUtil.selectOneUser('ketChargeUser','ketChargeUserName','setUser1');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('ketChargeUser','ketChargeUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">의뢰부서</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ketClientDepartmentName" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="ketClientDepartment"> 
                            <a href="javascript:SearchUtil.addDepartmentAfterFunc(true,'setDept2');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('ketClientDepartment','ketClientDepartmentName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">의뢰자</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ketClientUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="ketClientUser"> 
                            <a href="javascript:SearchUtil.selectOneUser('ketClientUser','ketClientUserName','setUser2');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('ketClientUser','ketClientUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">의뢰일</td>
                        <td class="tdwhiteL" colspan="5">
                            <input type="text" name="createStartDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createStartDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="createEndDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createEndDate');" style="cursor: hand;">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">완료일</td>
                        <td class="tdwhiteL" colspan="5">
                            <input type="text" name="endStartDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endStartDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="endEndDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endEndDate');" style="cursor: hand;">
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