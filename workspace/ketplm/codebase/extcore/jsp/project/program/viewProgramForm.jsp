<%@page import="ext.ket.project.program.entity.KETProgramSchedule"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="ext.ket.project.program.entity.ProgramDTO"%>
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
<script type="text/javascript" src="/plm/extcore/js/project/program/createProgram.js"></script>
<script type="text/javascript" src="/plm/extcore/js/project/program/updateProgram.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    //tab binding
	var tab = CommonUtil.tabs('tabs');
	tab.tabs({ heightStyle: "fill"});
	<c:if test="${selectTab == 'PROJECT'}">
    tab.tabs({active: 1});
    </c:if>
	//일정 create grid
	UpdateProgram.createEventGrid();
	//관련프로젝트 tab grid
	CreateProgram.createProjectLinkGrid();	
	$(document).attr('title','<%=messageService.getString("e3ps.message.ket_message", "09206") %>');//프로그램 조회
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09206") %><!-- 프로그램 조회 --></td>
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
                <%
                ProgramDTO program = (ProgramDTO)request.getAttribute("program");
                KETProgramSchedule programSchedule = (KETProgramSchedule)CommonUtil.getObject(program.getOid());
                WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
                %>
                <%if(sessionUser.equals(programSchedule.getProgramAdmin()) || CommonUtil.isMember("프로그램관리") || CommonUtil.isAdmin()){ %>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                href="javascript:UpdateProgram.deleteProgram();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><!-- 삭제 --></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
                <c:if test="${program.isNotify}">
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                href="javascript:UpdateProgram.goNotify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09207") %><!-- 공지 --></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
                </c:if>
                <%}%>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:window.close();try{opener.Program.search()}catch(e){}" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><!-- 닫기 --></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </ul>
    <div id="tabs-1" style="height:520px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">    
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><!-- 기본정보 --></td>
                            <c:if test="${program.notifyDTO.version != '0'}">
                                <td align="right">※ <%=messageService.getString("e3ps.message.ket_message", "09208") %><!-- 공지일 --> <a href="javascript:UpdateProgram.goHistory()">${program.notifyDTO.notifyDate}</a></td>
                            </c:if>
                            <td align="right" width="5">&nbsp;</td>
                            <td width="62" align="right">
                                <%if(sessionUser.equals(programSchedule.getProgramAdmin()) || CommonUtil.isMember("프로그램관리") || CommonUtil.isAdmin()){ %>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:UpdateProgram.goUpdate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><!-- 수정 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                                <%
                                }
                                %>
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
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
                            <tr>
                                <td width="100px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07295") %><!-- 프로그램 No --></td>
                                <td width="*" class="tdwhiteL">${program.programNo}</td>
                                <td width="100px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><!-- 상태 --></td>
                                <td width="*" class="tdwhiteL">${program.state}</td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09202") %><!-- 프로그램 명 --></td>
                                <td class="tdwhiteL" colspan="3">${program.carType} ${program.subContractor} ${program.programName}</td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><!-- 접점고객 --></td>
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
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01248") %><!-- 대표차종 --></td>
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
    </div>    
    <div id="tabs-2" style="height:520px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
                                            <%if(sessionUser.equals(programSchedule.getProgramAdmin()) || CommonUtil.isMember("프로그램관리") || CommonUtil.isAdmin()){ %>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:UpdateProgram.goUpdate('PROJECT');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><!-- 수정 --></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                            <%} %>
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
                    <!-- EJS TreeGrid Start -->
                    <div class="content-main">
                        <div class="container-fluid">
                            <div class="row-fluid" style="width:760px">
                                <div id="listGrid2"></div>
                            </div>
                        </div>
                    </div>
                    <!-- EJS TreeGrid End -->
                </td>
            </tr>
        </table>    
    </div>
</div>
