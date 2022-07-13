<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/edm/drawingDistRequest.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript">
$(document).ready(function(e) {
	SuggestUtil.bind('USER', 'creator');
    //부서 suggest
    SuggestUtil.bind('DEPARTMENT', 'distDeptName');
    SuggestUtil.bind('EPMNO', 'drawingNo');
    SuggestUtil.bind('ECONO', 'drawingDistEoArray');
    
    
    
    
    drawingDistRequest.createPaingGrid();
    treeGridResize("#drawingDistRequestSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    CommonUtil.singleSelect('distType',170);
    
    CalendarUtil.dateInputFormat('createStartDate','createEndDate');
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	drawingDistRequest.search();
        }
    });
});

function selectOneDrawingCallBackFn(arr){
	var no = "";
	for(var i = 0; i < arr.length ; i++){
	    var subArr = arr[i];
	    if(i != 0){
	    	no += ",";
        }
	    no += subArr[1];
    }

    $('[name=drawingNo]').val(no);
}

function popupRelEco() {
    var url = "/plm/jsp/ecm/SearchEcoPopupForm.jsp?prodMoldCls=0&mode=multi";
    
    var defaultWidth = 740;
    var defaultHeight = 550;
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'', defaultWidth, defaultHeight, opts);
    
}

function selectModalDialog(objArr) {
    if(objArr.length == 0) {
        return;
    }
    
    var ecoNoStr = "";
    var ecoOidStr = "";
    
    for(var i = 0; i < objArr.length; i++){
        var obj = objArr[i];
        if(i != 0){
            ecoNoStr += ", ";
            ecoOidStr += ", ";
        }
        ecoNoStr += obj[1];
        ecoOidStr += obj[0];
    }
    
    $("[name='drawingDistEoArray']").val(ecoNoStr);
    $("[name='drawingDistEoArrayOid']").val(ecoOidStr);
}

function selectDocumentNoCallBackFn(rsltArray){
	var no = "";
	for(var i = 0; i < rsltArray.length ; i++){
		if(i != 0){
            no += ",";
        }
        no += rsltArray[i].documentNo
	}
	
	$('[name=documentNo]').val(no);
	
}

function selectDeptRsltFunc(rsltArray){
    var name = "";
    var oid = "";
    var code = "";
    
    for(var i = 0; i < rsltArray.length ; i++){
         if(i != 0){
             oid += ",";
             name += ",";
             code += ",";
         }
        name += rsltArray[i].NAME;
        oid += rsltArray[i].OID;
        code += rsltArray[i].CODE;
    }
    
    $('[name=drawingDistDeptArray]').val(code);
    $('[name=distDeptName]').val(name);
}

function setUser(attacheMembers){
	var infoArr = attacheMembers[0];
    $('[name=creator]').val(infoArr[4]);
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
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09121") %><%--배포요청서 검색--%></td>
                                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "09121") %><%--배포요청서 검색--%></td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:drawingDistRequest.goCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09122") %><%--배포요청서 등록--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:drawingDistRequest.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table></td>
                            <td width="5">&nbsp;</td>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:drawingDistRequest.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table>
                </td>
            </table>
            <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <form name="searchForm">
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
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
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09111") %><%--배포요청서 No--%></td>
                        <td width="*" class="tdwhiteL">
                            <input type="text" name="distReqNumber" class="txt_field" style="width: 98%">
                        </td>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                        <td width="*" class="tdwhiteL">
                            <input type="text" name="title" class="txt_field" style="width: 98%">
                        </td>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09112") %><%--배포유형--%></td>
                        <td width="*" class="tdwhiteL">
                            <ket:select id="distType" name="distType" className="fm_jmp" style="width: 170px;" codeType="DISTTYPE" multiple="multiple" useCodeValue="true"/>
                            <!-- <select onBlur="fm_jmp" id="distType" name="distType" >
                                <option value="">전체</option>
                                <option value="REVIEW">검토</option>
                                <option value="APPROVED">승인</option>
                            </select>  -->
                        </td>           
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="drawingNo" class="txt_field" style="width:70%">
                            <a href="javascript:SearchUtil.selectDrawing('selectOneDrawingCallBackFn');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('drawingNo');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="drawingName" class="txt_field" style="width:98%">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="documentNo" class="txt_field" style="width:70%">
                            <a href="javascript:SearchUtil.selectTotalDocument('selectDocumentNoCallBackFn');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('documentNo');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>                         
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="documentName" class="txt_field" style="width:98%">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">EO No</td>
                        <td class="tdwhiteL">
                          <input type="text" name="drawingDistEoArray" class="txt_field" style="width:70%" value="">
                          <input type="hidden" name="drawingDistEoArrayOid" value="">
                          <a href="javascript:popupRelEco();">
                          <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('drawingDistEoArray', 'drawingDistEoArrayOid');">
                          <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09113") %><%--사내배포처--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="distDeptName" class="txt_field" style="width:70%" value="">
                            <input type="hidden" name="drawingDistDeptArray" value="">
                            <a href="javascript:SearchUtil.addDepartmentAfterFunc(true, 'selectDeptRsltFunc');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('drawingDistDeptArray','distDeptName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09114") %><%--외주배포처--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="distSubcontractor" class="txt_field" style="width:70%">
                        </td>
                         
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="creator" class="txt_field" style="width:70%">
                            <a href="javascript:SearchUtil.selectOneUser('','creator','setUser');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('','creator');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>                         
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                        <td class="tdwhiteL" colspan="3">
                            <input type="text" name="createStartDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createStartDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="createEndDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createEndDate');"
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