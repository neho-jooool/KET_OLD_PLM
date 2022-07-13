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
<script type="text/javascript">

$(document).ready(function(){
	//일정 create grid
	HistoryProgram.createEventGrid();
});

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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09237") %><!-- 공지내용 조회 --></td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
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
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><!-- 기본정보 --></td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
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
            <form name="ProgramViewForm">
            <input type="hidden" name="oid" value="${program.oid}">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07295") %><!-- 프로그램 No --></td>
                        <td width="*" class="tdwhiteL">${program.programNo}</td>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><!-- 상태 --></td>
                        <td width="*" class="tdwhiteL">${program.state}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09202") %><!-- 프로그램 명 --></td>
                        <td class="tdwhiteL" colspan="3">${program.programName}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><!-- 고객처 --></td>
                        <td class="tdwhiteL">${program.subContractor}</td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><!-- 사업부 --></td>
                        <td class="tdwhiteL">
                            <c:if test="${program.divisionCode == 'ER'}">
                                <%=messageService.getString("e3ps.message.ket_message", "02483") %><!-- 전자사업부 -->
                            </c:if>
                            <c:if test="${program.divisionCode == 'CA'}">
                               <%=messageService.getString("e3ps.message.ket_message", "02401") %><!-- 자동차사업부 -->
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><!-- 최종사용처 --></td>
                        <td class="tdwhiteL">${program.lastUsingBuyer}</td>                            
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04122") %><!-- 차종 --></td>
                        <td class="tdwhiteL">${program.carType}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09201") %><!-- 프로그램 관리자 --></td>
                        <td class="tdwhiteL">${program.programAdmin}</td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03223") %><%--형태--%></td>
                        <td class="tdwhiteL">${program.formType}</td>
                    </tr>                                                      
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09203") %><!-- 프로그램 시작일 --></td>
                        <td class="tdwhiteL">${program.startDate}</td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09209") %><!-- 프로그램 종료일 --></td>
                        <td class="tdwhiteL">${program.endDate}</td>
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
<!-- EJS TreeGrid End -->
