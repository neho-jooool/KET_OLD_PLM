<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/sample/sampleRequest.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript">
$(document).ready(function(e) {
    SuggestUtil.bind('CARTYPE', 'carTypeName');
    
    SuggestUtil.bind('USER', 'pmUserName');
    SuggestUtil.bind('DEPARTMENT', 'pmUserDeptName');
    
    SuggestUtil.bind('USER', 'createUserName');
    SuggestUtil.bind('DEPARTMENT', 'createUserDeptName');
    SuggestUtil.bind('PARTNO', 'requstPartName');
    
    
    CalendarUtil.dateInputFormat('createStartDate','createEndDate'); //기한 설정시 start와 end field를 설정한다.
    CalendarUtil.dateInputFormat('compStartDate','compEndDate'); //기한 설정시 start와 end field를 설정한다.
    
    sampleRequest.createPaingGrid();
    treeGridResize("#SampleRequestSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    CommonUtil.multiSelect('sampleRequestStateName',130);
    CommonUtil.multiSelect('developeStageCodeArr',130);

    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	sampleRequest.search();
        }
    });
});

function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    
    var partOid = "";
    var partName = "";
    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];
        if( i == 0){
        	partOid += trArr[0];
            partName += trArr[1];
        }
        else{
        	partOid += ","+trArr[0];
            partName += ","+trArr[1];
        }
        
    }
    $('[name=requstPartOidArr]').val(partOid);
    $('[name=requstPartName]').val(partName);
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
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09247") %><%--Sample 요청--%></td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "09071") %><%--부품관리--%>
                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "09247") %><%--Sample 요청--%></td>
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
                                            onclick="javascript:sampleRequest.goCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09100") %><%--요청서 작성--%></a></td>
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
                                            onclick="javascript:sampleRequest.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
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
                                            href="javascript:sampleRequest.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
            <form name="searchForm">
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <!-- [START] search condition -->
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <colgroup>
                        <col width="13%" />
                        <col width="20%" />
                        <col width="13%" />
                        <col width="20%" />
                        <col width="13%" />
                        <col width="21%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09078") %><%--요청서 No--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="requestNo" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="requestTitle" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="customerName" class="txt_field" style="width: 98%">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09081") %><%--적용차종--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="carTypeName" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="carTypeCode">
                            <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%>
                        </td>
                        <td class="tdwhiteL">
                            <select id="developeStageCodeArr" name="developeStageCodeArr" style="width: 180px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
			                    <option value="T-CAR">T-CAR</option>
			                    <option value="Pilot">Pilot</option>
			                    <option value="Proto">Proto</option>
			                    <option value="개발">개발</option>
			                    <option value="양산"><%=messageService.getString("e3ps.message.ket_message", "02078") %><%--양산--%></option>
		                    </select></td>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09101") %><%--요청부품--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="requstPartName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="requstPartOidArr">
                            <a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('requstPartName', 'requstPartOidArr');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "01201") %><%--담당부서--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" id="pmUserDeptName" class="txt_field" name="pmUserDeptName" style="width: 70%" >
                            <input type="hidden" id="pmUserDeptOid" name="pmUserDeptOid" value="">
                            <a href="javascript:SearchUtil.addDepartment(true,'pmUserDeptOid', 'pmUserDeptName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('pmUserDeptOid','pmUserDeptName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="pmUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="pmUserOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('pmUserOid','pmUserName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('pmUserOid','pmUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09102") %><%--요청부서--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" id="createUserDeptName" name="createUserDeptName" class="txt_field" style="width: 70%">
                            <input type="hidden" id="createUserDeptOid" name="createUserDeptOid" value="">
                            <a href="javascript:SearchUtil.addDepartment(true,'createUserDeptOid', 'createUserDeptName');"><img src="/plm/portal/images/icon_5.png"></a>
                            <a href="javascript:CommonUtil.deleteValue('createUserDeptName','createUserDeptOid');"><img src="/plm/portal/images/icon_delete.gif"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "02196") %><%--요청자--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="createUserName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="createUserOid"> 
                            <a href="javascript:SearchUtil.selectOneUser('createUserOid','createUserName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('createUserOid','createUserName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "09080") %><%--고객담당자--%>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="customerContractor" class="txt_field" style="width: 98%">
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td class="tdwhiteL">
                            <select onBlur="fm_jmp" id="sampleRequestStateName" name="sampleRequestStateName" multiple="multiple" style="width:180px">
                               <option value="작업중"><%=messageService.getString("e3ps.message.ket_message", "02441") %><%--작업중--%></option>
                               <option value="검토중"><%=messageService.getString("e3ps.message.ket_message", "00732") %><%--검토중--%></option>
                               <option value="요청"><%=messageService.getString("e3ps.message.ket_message", "02190") %><%--요청--%></option>
                               <option value="불출"><%=messageService.getString("e3ps.message.ket_message", "09103") %><%--불출--%></option>
                               <option value="완료"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></option>
                               <option value="반려됨"><%=messageService.getString("e3ps.message.ket_message", "01468") %><%--반려됨--%></option>
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