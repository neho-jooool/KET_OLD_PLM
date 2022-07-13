<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
    
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.

String userId = CommonUtil.getUserIDFromSession();
boolean isAdmin = CommonUtil.isAdmin() || ("jindol76".equals(userId) || "sspark".equals(userId));

%>
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/part/partMasList.js?ver=1"></script>

<script type="text/javascript">
$(document).ready(function(e) {
	
    SuggestUtil.bind('PRODUCTPARTNO', 'partNo');
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
    
    CommonUtil.multiSelect('devSteps',130);
    CalendarUtil.dateInputFormat('masStartDate','masEndDate'); //기한 설정시 start와 end field를 설정한다.
    
    mass.createPaingGrid();
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
            mass.search();
        }
    });
    
    treeGridResize("#massPartSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId

});


function migPartMass(){

	var param = {};
    if(confirm("마이그레이션 하시겠습니까?(1회만 실행하십시오.) ")){
        
        ajaxCallServer("/plm/ext/part/base/migPartMass", param, function(data){
            mass.search();
        });
    }
}

function partMassSync(){

    var param = {};
    if(confirm("동기화하시겠습니까?(이 작업은 시간이 걸립니다.)")){
        
        ajaxCallServer("/plm/ext/part/base/partMassSync", param, function(data){
            mass.search();
        });
    }
}

function setProject(arr){
	$('#pjtNo').val(arr[0][1]);
	$('#pjtOid').val(arr[0][0]);
	
}

</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">자재양산시작일관리</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">부품관리
                                <img src="/plm/portal/images/icon_navi.gif">자재양산시작일관리</td>
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
            <c:if>
            
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <%if(isAdmin){ %>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:mass.gridSave()" class="btn_blue">저장</a></td>
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
                                            onclick="javascript:partMassSync()" class="btn_blue">동기화</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            
                            <%} %>
                            
                            <td id="bt2" name="bt2">
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:mass.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                   </tr>
                               </table>
                            </td>
                            <td width="5">&nbsp;</td>
                            <td id="bt3" name="bt3">
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:mass.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
                <input type='hidden' id='pjtNos' name='pjtNos' value='${pjtNos}'/>
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                
                <table border="0" cellspacing="0" cellpadding="0" width="100%" id='searchTab'>
                    <colgroup>
                        <col width="5%" />
                        <col width="10%" />
                        <col width="5%" />
                        <col width="10%" />
                        <col width="5%" />
                        <col width="10%" />
                        <col width="5%" />
                        <col width="30%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL">
                          <input type="text" name="pjtNo" id="pjtNo" class="txt_field" style="width:70%" value="">
                          <input type="hidden" name="pjtOid" id="pjtOid" value="">
                          <a href="javascript:SearchUtil.selectOneProject('setProject');">
                          <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('pjtNo','pjtOid');">
                          <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09005") %><%--부품 NO--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="partNo" id="partNo" class="txt_field" style="width: 70%">
                            <input type="hidden" name="relatedPartOid">
                            <a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc','pType=P');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partNo', 'relatedPartOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">신제품구분</td>
                        <td class="tdwhiteL">
                            <select onBlur="fm_jmp" id="devSteps" name="devSteps" multiple="multiple" style="width:170px">
                               <option value="신제품">신제품</option>
                               <!-- <option value="양산">양산</option> -->
                            </select>
                        </td>
                        
                        
                        <td class="tdblueL">양산시작일</td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="masStartDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('masStartDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="masEndDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('masEndDate');"
                            style="cursor: hand;">
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