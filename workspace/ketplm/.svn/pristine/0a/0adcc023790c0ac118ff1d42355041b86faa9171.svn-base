<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="toDay" class="java.util.Date" />
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/replacePart/replacePart.js?ver=1"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
.headerDiv{text-align:center;line-height:18px;}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
	
	$(document).attr("title","커넥터 대체품 관리"); 
	
	$(document).bind("contextmenu", function(e){
        return false;
    });

    //상태
    $("#rival").multiselect({
        minWidth: 110,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    $("#replaceGb").multiselect({
        minWidth: 110,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    replacePart.createPaingGrid();
    
    document.addEventListener('keydown', function(event) {
        if (event.keyCode === 13) {
        	replacePart.search();
            event.preventDefault();
        }
    }, true);
    

});

window.replacePartUpload = function(){
    
    if($("input[name='uploadFile']").val().trim() == ""){
        alert("업로드할 파일을 추가하세요.");
        return;
    }
    
    var param = $("[name=uploadForm]").serializefiles();
    
    if(confirm("업로드 진행하시겠습니까?")){
    	
        ajaxCallServer("/plm/ext/replacePart/replacePartExcelUpload", param, function(data){
        	replacePart.search();
        });
    }
}

</script>
<c:if test="${not empty ERROR}">
<script>alert("${ERROR}");self.close();</script>
</c:if>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">커넥터 대체품 관리</td>
<!--                                 <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Dashboard
                                <img src="/plm/portal/images/icon_navi.gif">Report
                                <img src="/plm/portal/images/icon_navi.gif">프로젝트 주요일정</td> -->
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
            <form enctype="multipart/form-data" name="uploadForm">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <c:if test="${isAdmin}">
                    <td align="left">
                    <img src="/plm/portal/images/iocn_excel.png" onclick="location.href='/plm/ext/download?path=/replacePart/REPLACE_PART_UPLOAD.xlsx'" style="cursor:pointer;"/>
                    <input type="file" name="uploadFile" />
                    <span class="in-block v-middle r-space7">
                        <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center"> <a href="javascript:replacePartUpload()" class="btn_blue">Multi업로드</a></span>
                        <span class="pro-cell b-right"></span>
                    </span>
                    </span>
                    <input type="text" name="newPartNo" id="newPartNo"/>
                    <span class="in-block v-middle r-space7">
                        <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center"> <a href="javascript:replacePart.newRivalPart()" class="btn_blue">신규등록(단건)</a></span>
                        <span class="pro-cell b-right"></span>
                    </span>
                    </span>
                    </td>
                    </c:if>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <c:if test="${isAdmin}">
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:replacePart.deletePart(1);" class="btn_blue">경쟁사Part삭제</a></td>
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
                                            onclick="javascript:replacePart.deletePart(2);" class="btn_blue">KET부품삭제</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                   </tr>
                               </table>
                            </td>
                            </c:if>
                            <td width="5">&nbsp;</td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:replacePart.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
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
                                            href="javascript:replacePart.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
            </table>
            </form>
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            <!-- [END] button -->
            <!-- [START] search condition -->
            <form enctype="multipart/form-data" name="searchForm">
                <input type="hidden" name="sortKey" id="sortKey" value="">
                <input type="hidden" name="DATATYPE" id="DATATYPE" value="REPLACEPARTLIST">
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
                        <col width="*" />
                        <col width="10%" />
                        <col width="*" />
                        <col width="10%" />
                        <col width="*" />
                        <col width="10%" />
                        <col width="*" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL" style="width: 110px;">경쟁사 PartNo</td>
                        <td class="tdwhiteL"><input type="text" name="partNo" class="txt_field" style="width:70%;" value=""></td>
                        <td class="tdblueL">경쟁사</td>
                        <td class="tdwhiteL">
                            <ket:select id="rival" name="rival" className="fm_jmp" style="width:130px;" codeType="SALESCOMPETITOR" multiple="multiple" useCodeValue="true" value=""/>
                        </td>
                        <td class="tdblueL" style="width: 110px;">경쟁사 PartNo<BR> 다중검색</td>
                        <td class="tdwhiteL"><input type="file" name="multiSearchFile" /></td>
                        <td class="tdblueL">Replace</td>
                        <td class="tdwhiteL">
                        <select id="replaceGb" name="replaceGb" class="fm_jmp" multiple='multiple'>
                            <option value="O" >O</option>
                            <option value="△" >△</option>
                            <option value="X" >X</option>
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
                         </div>
                         <!-- EJS TreeGrid End -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              