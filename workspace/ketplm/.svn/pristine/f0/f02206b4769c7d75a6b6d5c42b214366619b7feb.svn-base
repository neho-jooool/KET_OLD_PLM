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
<script type="text/javascript" src="/plm/extcore/js/project/program/updateProgram.js"></script>
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09238") %><!-- 프로그램 공지 --></td>
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
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td  class="space5"></td>
    </tr>
</table>    
<table border="0" cellspacing="0" cellpadding="0" align="right">
    <tr>
        <td>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                        href="javascript:UpdateProgram.notify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09207") %><!-- 공지 --></a></td>
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
                        <a href="javascript:opener.UpdateProgram.updateData();window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><!-- 취소 --></a>
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
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td  class="tab_btm2"></td>
    </tr>
</table>
<form name="ProgramNotifyForm">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09226") %><!-- 공지사유 --><span class="red"></td>
            <td width="*" class="tdwhiteL"><textarea name="notifyReason" class="txt_field" rows="3" cols="1" style="width:98%;height:90%"></textarea></td>
        </tr>
    </table>
</form>