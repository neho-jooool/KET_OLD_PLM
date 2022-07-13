<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/jsp/dashboard/standard/js/updateStandard.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$(document).attr('title', '공수관리 기준정보');
    setEnumYear();

    //tab binding
    var tab = CommonUtil.tabs('tabs');
    tab.tabs({
        heightStyle : "fill"
    });

    var tabIndex = "${selectTab}";

    changeActiveTab(tabIndex);

    //공통기준 tab grid
    updateStandard.createStandardGrid();

    //팀별 공통업무(H/월) tab grid
    updateStandard.createStandardGrid2();

    

});

function setEnumYear() {

    var now = new Date();

    var standardYear = Number(now.getFullYear());
    var yearEnumKey = "";
    for ( var i = 0; i <= 10; i++) {
        yearEnumKey += "|" + standardYear;
        standardYear--;
    }

    $('[name=yearEnumKey]').attr('value', yearEnumKey);
    $('[name=yearTypeEnum]').attr('value', yearEnumKey);
}

//탭 활성화 및 selected
function changeActiveTab(index) {
    $("#tabs").tabs({
        active : index
    });
}

function search(oid) {

    $('[name=departmentOid]').attr('value', oid);

    Grids[1].Source.Data.Url = '/plm/ext/dashboard/standard/findStandardByDashboard2.do?departmentOid='+ oid;
    //Grids[1].Source.Data.Params = "programOid=ext.ket.project.program.entity.KETProgramSchedule:99209110";
    Grids[1].ReloadBody();
}
</script>
<form name="StandardViewForm">
<input type="hidden" name="departmentOid" value="" />
<input type="hidden" name="yearEnumKey" value="" />
<input type="hidden" name="yearTypeEnum" value="" />
<input type="hidden" name="delOids" value="" />
</form>
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09210") %><!-- 프로그램 수정 --></td>
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
        <li><a class="tabref" href="#tabs-1"><%=messageService.getString("e3ps.message.ket_message", "01120") %><!-- 기본정보 --></a></li>
        <li><a class="tabref" href="#tabs-2"><%=messageService.getString("e3ps.message.ket_message", "09205") %><!-- 관련프로젝트 --></a></li>
        <table border="0" cellspacing="0" cellpadding="0" align="right">
            <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:UpdateProgram.goView();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><!-- 취소 --></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </ul>
    <div id="tabs-1" style="height:520px">
        <table width="100%" border="1" cellspacing="0" cellpadding="0">
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
                                                        <a href="javascript:updateStandard.addRow(0);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152") %><!-- 하위추가 --></a>
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
                    <div id="listGrid"></div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
    </div>    
    <div id="tabs-2" style="height:520px">
        <table width="100%" border="1" cellspacing="0" cellpadding="0">
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
                                                        <a href="javascript:updateStandard.addRow(1);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152") %><!-- 하위추가 --></a>
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
                    <div id="listGrid2"></div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->
    </div>
</div>
