<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/dms/sgDocument.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
    
	sgDocument.createPaingGrid();
	
	treeGridResize("#SGSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	sgDocument.search();
        }
    });
    
    CommonUtil.singleSelect('lastest', 150);
    CommonUtil.singleSelect('moduleCode', 200);
    
    window.openSaveSGDocumentPopup = function(){
    	getOpenWindow2("/plm/ext/dms/saveSGDocumentPopup", "SGDocumentPopup", 800, 300);
    }
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
                                <td class="font_01">시스템 사용설명서</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Document
                                <img src="/plm/portal/images/icon_navi.gif">시스템 사용설명서
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
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:openSaveSGDocumentPopup()" class="btn_blue">등록</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:sgDocument.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
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
                                            href="javascript:sgDocument.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
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
            <form name="searchForm">
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <colgroup>
                        <col width="10%" />
                        <col width="40%" />
                        <col width="10%" />
                        <col width="40%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">모듈</td>
                        <td class="tdwhiteL">
                          <select name="moduleCode" id="moduleCode" multiple="multiple" style="width:200px;">
                                <option value="">선택</option>
                                <option value="MODULE001">Workspace</option>
                                <option value="MODULE002">Project</option>
                                <option value="MODULE003">금형/구매품</option>
                                <option value="MODULE004">Document</option>
                                <option value="MODULE005">Drawing</option>
                                <option value="MODULE006">Part</option>
                                <option value="MODULE007">ECM</option>
                                <option value="MODULE008">EWS</option>
                                <option value="MODULE009">HelpDesk</option>
                                <option value="MODULE0010">관리메뉴</option>
                                <option value="MODULE0011">DashBoard</option>
                                <option value="MODULE0012">원가관리</option>
                            </select>
                        </td>
                        <td class="tdblueL">관련화면</td>
                        <td class="tdwhiteL">
                            <input type="text" name="refView" class="txt_field" style="width:98%" value="">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">문서번호</td>
                        <td class="tdwhiteL">
                            <input type="text" name="docNo" class="txt_field" style="width: 98%"> 
                        </td>
                        <td class="tdblueL">문서명</td>
                        <td class="tdwhiteL">
                            <input type="text" name="docName" class="txt_field" style="width: 98%"> 
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">양식설명</td>
                        <td class="tdwhiteL">
                            <input style="width:98%;" type="text" id="description" name="description" class="txt_field" value="">
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
                        <td class="tdwhiteL">
                            <select id="lastest" name="lastest" style="width:150px;">
                                <option value="true" selected="selected"><%=messageService.getString("e3ps.message.ket_message", "02839")%><%--최신--%></option>
                                <option value="false"><%=messageService.getString("e3ps.message.ket_message", "02485")%><%--전체--%></option>
                            </select>
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
                            <!-- EJS TreeGrid Start -->
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   