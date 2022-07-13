<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/program/historyProgram.js"></script>
<script>
$(document).ready(function(){
	HistoryProgram.createNotifyHistoryGrid(${historyJson});
});
</script>
<input type="hidden" name="programScheduleOid" value="${programScheduleOid}">
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09235") %><!-- 변경 공지 이력 조회 --></td>
                          </tr>
                        </table>
                    </td>
                    <td width="100" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
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
<table border="0" cellspacing="0" cellpadding="0" align="right">
    <tr>
        <td>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                        href="javascript:HistoryProgram.goCompareNotify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09236") %><!-- 비교조회 --></a></td>
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
                        <a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><!-- 닫기 --></a>
                    </td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
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