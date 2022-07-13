<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/klog/klog.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	/* 달력 */
    CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');
	
	/* ECO NO 자동완성*/
	SuggestUtil.bind('ECONO', 'ecoNo');
	
	/* 작성자 자동완성 */
	SuggestUtil.bind('USER', 'changeUserName', 'changeUserId');
	
	/* Grid */
	Klog.createPaingGrid();
	
	/* 상태 */
	CommonUtil.multiSelect('ecoState',200);
	
	/* 진행결과 */
	CommonUtil.multiSelect('eventResult',200);
	
	/* Enter key 작동 */
	$(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	Klog.search();
        }
    });
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
                                <td class="font_01">ECO 모니터링 검색</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home 
                                <img src="/plm/portal/images/icon_navi.gif">관리메뉴
                                <img src="/plm/portal/images/icon_navi.gif">ECO 모니터링</td>
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
                                                onclick="javascript:Klog.clear();" class="btn_blue">초기화</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Klog.search();" class="btn_blue">검색</a></td>
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
            <!-- [END] button -->
            <!-- [START] search condition -->
            <table style="width: 100%;">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
            <form name="KlogSearchForm">
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td width="100" class="tdblueL">ENO No</td>
                        <td width="*" class="tdwhiteL"><input type="text" id="ecoNo" name="ecoNo" class="txt_field" style="width: 98%"></td>
                        <td width="100" class="tdblueL">진행결과</td>
                        <td width="*" class="tdwhiteL">
                            <select name="eventResult" id="eventResult" class="fm_jmp" multiple="multiple">
                                <option value="Success">성공</option>
                                <option value="Fail">실패</option>
                              </select>
                        </td>                        
                        <td width="100" class="tdblueL">상태</td>
                        <td class="tdwhiteL">
                            <select name="ecoState" id="ecoState" class="fm_jmp" multiple="multiple">
				                <option value="PLANNING"><%=messageService.getString("e3ps.message.ket_message", "00815") %><%--계획수립--%></option>
				                <option value="EXCUTEACTIVITY"><%=messageService.getString("e3ps.message.ket_message", "03243") %><%--활동수행--%></option>
				                <option value="ACTIVITYCOMPLETED"><%=messageService.getString("e3ps.message.ket_message", "03244") %><%--활동완료--%></option>
				                <option value="UNDERREVIEW"><%=messageService.getString("e3ps.message.ket_message", "00732") %><%--검토중--%></option>
				                <option value="APPROVED"><%=messageService.getString("e3ps.message.ket_message", "01996") %><%--승인완료--%></option>
				                <option value="REJECTED"><%=messageService.getString("e3ps.message.ket_message", "01468") %><%--반려됨--%></option>
				                <option value="REWORK"><%=messageService.getString("e3ps.message.ket_message", "02451") %><%--재작업--%></option>
				              </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">작성자</td>
                        <td class="tdwhiteL">
                            <input type="text" id="changeUserName" name="changeUserName" class="txt_field" style="width: 98%;"></td>   
                            <input type="hidden" id="changeUserId" name="changeUserId" value="">                         
                        <td class="tdblueL">작성일자</td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="createStartDate" id="createStartDate" class="txt_field"  style="width:70px" value="">
				            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createStartDate');" style="cursor: hand;">
				                &nbsp;~&nbsp;
				            <input type="text" name="createEndDate" id="createEndDate" class="txt_field"  style="width:70px" value="">
				            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createEndDate');" style="cursor: hand;">
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
                        </div>
                        <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   