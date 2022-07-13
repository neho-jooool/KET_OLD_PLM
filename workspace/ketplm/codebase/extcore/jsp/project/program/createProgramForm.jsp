<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/program/program.js"></script>
<script type="text/javascript" src="/plm/extcore/js/project/program/createProgram.js"></script>
<script type="text/javascript" src="/plm/extcore/js/project/program/updateProgram.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    //------------ start suggest binding ------------//
    //사용자 suggest
    SuggestUtil.bind('USER', 'programAdminTxt', 'programAdmin');
    //차종 suggest
    SuggestUtil.bind('CARTYPE', 'carTypeTxt', 'carType');
    //고객처 suggest
    SuggestUtil.bind('CUSTOMER', 'subContractorTxt', 'subContractor');
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'lastUsingBuyerTxt', 'lastUsingBuyer');
    //------------ end suggest binding ------------//
    //tab binding
	var tab = CommonUtil.tabs('tabs');
    tab.tabs({disabled: [1]});
    
    //select binding
	CommonUtil.singleSelect('divisionCode','150');
	//일정 create grid
	CreateProgram.createEventGrid();
	//관련프로젝트 tab grid
	CreateProgram.createProjectLinkGrid(true);
	//차종에 따른 차종 이벤트 정보를 리스트업 한다.
	$('[name=carType]').change(function(){
        Grids[0].Source.Data.Param.carTypeOid=$(this).val();
        Grids[0].Reload();
        
        setInterval(CreateProgram.setPrefixName,200);
    });
	//고객처
	$('[name=subContractorTxt]').change(function(){
		setInterval(CreateProgram.setPrefixName,200);
	})
	$(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "03035") %>');//프로그램 등록
	treeGridResize("#ProgramEventGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

window.selectOneSubContractor = function(returnValue){
	$('[name=subContractor]').val(returnValue[0][0]);
    $('[name=subContractorTxt]').val(returnValue[0][2]);
}

window.setCarType = function(returnValue){
    
    $('[name=carType]').val(returnValue[0][0]);
    $('[name=carTypeTxt]').val(returnValue[0][1]);
    
    Grids[0].Source.Data.Param.carTypeOid=returnValue[0][0];
    Grids[0].Reload();
    
    setInterval(CreateProgram.setPrefixName,200);
    
}

window.setUser1 = function(returnValue){
    var infoArr = returnValue[0];
    $('[name=programAdmin]').val(infoArr[0]);
    $('[name=programAdminTxt]').val(infoArr[4]);
    
}


</script>
<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03035") %><!-- 프로그램 등록 --></td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>    
<div id="tabs">
    <ul>
        <li><a class="tabref" href="#tabs-1"><%=messageService.getString("e3ps.message.ket_message", "09204") %><!-- 기본정보/일정 --></a></li>
        <li><a class="tabref" href="#tabs-2"><%=messageService.getString("e3ps.message.ket_message", "09205") %><!-- 관련프로젝트 --></a></li>
        <table border="0" cellspacing="0" cellpadding="0" align="right">
            <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table id="programSaveBtn" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:CreateProgram.create();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><!-- 저장 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="5px"></td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><!-- 취소 --></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </ul>
    <div id="tabs-1">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">    
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><!-- 기본정보 --></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td  class="tab_btm2"></td>
                        </tr>
                    </table>
                    <form name="ProgramCreateForm">
                    <input type="hidden" name="oid">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="100px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09202") %><!-- 프로그램 명 --><span class="red">*</span></td>
                                <td class="tdwhiteL" colspan="3">
                                    <input type="text" name="programNamePrefix" class="txt_fieldRO" style="width: 190px" readonly>
                                    <input type="text" name="programName" class="txt_field" style="width: 65%"></td>
                            </tr>
                            <tr>
                                <td width="100px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><!-- 고객처 --><span class="red">*</span></td>
                                <td width="*" class="tdwhiteL">
                                    <input type="text" name="subContractorTxt" class="txt_field" style="width: 80%;">
                                    <input type="hidden" name="subContractor">
                                    <a href="javascript:SearchUtil.selectOneSubContractorPopUp('selectOneSubContractor');">
                                    <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('subContractor','subContractorTxt');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                                <td width="100px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><!-- 사업부 --><span class="red">*</span></td>
                                <td width="*" class="tdwhiteL">
                                    <%
                                    String divisionCode = "";
                                    if(CommonUtil.isMember("전자사업부")){
                                	   divisionCode = "ER";   
                                    }else if(CommonUtil.isMember("자동차사업부")){
                                	   divisionCode = "CA";
                                    }else if(CommonUtil.isMember("KETS") || CommonUtil.isMember("KETS_PMO")){
                                	   divisionCode = "KA";
                                    }
                                    %>
                                    <ket:select id="divisionCode" name="divisionCode" codeType="DIVISIONTYPE"  multiple="multiple" useCodeValue="true" value="<%=divisionCode %>"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><!-- 최종사용처 --><span class="red">*</span></td>
                                <td class="tdwhiteL">
                                    <input type="text" name="lastUsingBuyerTxt" class="txt_fieldRO" readonly style="width: 80%">
                                    <input type="hidden" name="lastUsingBuyer"><a href="javascript:SearchUtil.selectOneLastUsingBuyer('lastUsingBuyer','lastUsingBuyerTxt');">
                                    
                                    <!--  <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('lastUsingBuyer','lastUsingBuyerTxt');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>-->
                                </td>                            
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01248") %><!-- 대표차종 --><span class="red">*</span></td>
                                <td class="tdwhiteL">
                                    <input type="text" name="carTypeTxt" class="txt_field" style="width: 80%;"> 
                                    <input type="hidden" name="carType">
                                    <a href="javascript:SearchUtil.selectCarType('','','setCarType');">
                                    <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('carTypeTxt','carType');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09201") %><!-- 프로그램 관리자 --><span class="red">*</span></td>
                                <td class="tdwhiteL">
                                    <input type="text" name="programAdminTxt" class="txt_field" style="width: 80%;" value="<%=CommonUtil.getUsernameFromSession()%>"> 
                                    <input type="hidden" name="programAdmin" value="<%=CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal())%>">
                                    <a href="javascript:SearchUtil.selectOneUser('','','setUser1');">
                                    <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('programAdminTxt','programAdmin');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                                <td class="tdblueL non-back"></td>
                                <td class="tdwhiteL"></td>
                            </tr>                                                      
                        </table>
                    </form>
                </td>
            </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02348") %><!-- 일정 --></td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:CreateProgram.addRowAbove();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07151") %><!-- 상위추가 --></a></td>
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
                                                        <a href="javascript:CreateProgram.addRowBelow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152") %><!-- 하위추가 --></a>
                                                    </td>
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
                                                        <a href="javascript:CreateProgram.removeRow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "06122") %><!-- 제거 --></a>
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
                </td>
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
    </div>    
    <div id="tabs-2">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="right">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00920") %><!-- 관련 프로젝트 --></td>
                                        <td align="right">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:SearchUtil.selectProject(CreateProgram.addProjectLink);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><!-- 추가 --></a></td>
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
                                                                    <a href="javascript:CreateProgram.removeProjectLink();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "06122") %><!-- 제거 --></a>
                                                                </td>
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
                                                                    href="javascript:CreateProgram.saveProjectLink();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><!-- 저장 --></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <!-- EJS TreeGrid Start -->
                    <div class="content-main">
                        <div class="container-fluid">
                            <div class="row-fluid">
                                <div id="listGrid2" style="WIDTH: 748px; HEIGHT: 499px"></div>
                            </div>
                        </div>
                    </div>
                    <!-- EJS TreeGrid End -->
                </td>
            </tr>
        </table>    
    </div>
</div>
