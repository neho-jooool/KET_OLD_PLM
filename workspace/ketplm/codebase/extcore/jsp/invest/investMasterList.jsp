<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/invest/invest.js?ver=0.2"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
html{width:100%;height:100%;}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
    
    invest.createPaingGrid();
    treeGridResize("#searchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    CommonUtil.singleSelect('investState',130);
    CommonUtil.singleSelect('state',130);
    
    CalendarUtil.dateInputFormat('requestStartDate'); //requestStartDate
    CalendarUtil.dateInputFormat('requestEndDate'); //requestEndDate
    
    CalendarUtil.dateInputFormat('sopStartDate'); //sopStartDate
    CalendarUtil.dateInputFormat('sopEndDate'); //sopEndDate

    SuggestUtil.bind('USERDEPT', 'managerName', 'managerOid');
    SuggestUtil.bind('USERDEPT', 'tmUserName', 'tmUserOid');
    
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    SuggestUtil.bind('DEPARTMENT', 'taskDeptName', 'taskDeptCode');
    
    //SuggestUtil.bind('CUSTOMEREVENT', 'lastCustomerName', 'lastCustomer');
    SuggestUtil.bind('CARTYPE', 'oemName', 'oemOid');
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	invest.search();
        }
    });
    
    
    Grids.OnDownloadPage = function(grid,row){
        grid.Data.Page.Format = 'Form';
        grid.Data.Page.Method = 'Post';
        
        grid.Data.Page.Url = '/plm/ext/invest/findPagingList.do?sortName=*Sort0';
        
        var param = {
            page : grid.GetPageNum(row),
            formPage : grid.Source.Layout.Data.Cfg.PageLength
        }

        $('input,select').each(function(){
            var name = $(this).attr('name');
            var value = $(this).val();
            param[name] = value;
        });
        grid.Data.Page.Param = param;
    }
    
    $("#state").prepend("<option value=''>선택</option>");
    $("#state").multiselect('refresh');
});

window.setProjectNo = function(objArr){
    var projectNo= "";
    if(objArr[0][0].indexOf("ProductProject")== -1) {
        alert(LocaleUtil.getMessage('09023'));//"제품 프로젝트만 선택 가능합니다.");
        return;
    }
    
    $('[name=pjtNo]').val(objArr[0][1]);
}

window.investSearch = function(){
    
	invest.search();
}

window.setSubContractor = function(data){
    
    var nodeCodeStr='', nodeNameStr='';
    
    window.console.log(data);
    
    for(var i=0; i < data.length; i++){
        if(i == data.length - 1){
            nodeCodeStr += data[i][1];
            nodeNameStr += data[i][2];
        }else{
            nodeCodeStr += data[i][1]+',';     
            nodeNameStr += data[i][2]+',';
        }
    }
    $('[name=subContractorName]').val(nodeNameStr);
    $('[name=subContractor]').val(nodeCodeStr);
}

var userName_investMaster = '';
var userOid_investMaster = '';

window.selectOneUser_ = function(oid,name){
	userName_investMaster = name;
	userOid_investMaster = oid;
	
	SearchUtil.selectOneUser('','','setUser');
}

window.setUser = function(attacheMembers){
	var infoArr = attacheMembers[0];
    $('[name='+userOid_investMaster+']').val(infoArr[0]);
    $('[name='+userName_investMaster+']').val(infoArr[4]);
}

function selectMultiSubContractor(returnValue){
    var nodeIdStr='', nodeNameStr='';
    for(var i=0; i < returnValue.length; i++){
            if(i == returnValue.length - 1){
                    nodeIdStr += returnValue[i][0];
                    nodeNameStr += returnValue[i][2];
            }else{
                    nodeIdStr += returnValue[i][0]+',';     
                    nodeNameStr += returnValue[i][2]+',';
            }
    }
    $('[name=customerCode]').val(nodeIdStr);
    $('[name=customerName]').val(nodeNameStr);
}

function addDepartment(codeTarget, nameTarget) {
    
    window.deptTagetName = nameTarget;
    window.deptTagetCode = codeTarget;
    
    var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?type=all&mode=m&invokeMethod=setDept";
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, 430, 465, opts);
}

function setDept(attacheDept){
	$("#" + deptTagetCode).val( attacheDept[0].OID );
    $("#" + deptTagetName).val( attacheDept[0].NAME );
}

function acceptDept(req) {
    
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    
    if(msg != 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_code = showElements[0].getElementsByTagName("l_code");
    var l_chiefOid = showElements[0].getElementsByTagName("l_chiefOid");
    var l_chiefName = showElements[0].getElementsByTagName("l_chiefName");

    $("#" + deptTagetCode).val( decodeURIComponent(l_code[0].text) );
    $("#" + deptTagetName).val( decodeURIComponent(l_name[0].text) );
}

function delDepartment(targetId, targetName) {
    $("#" + targetId).val("");
    $("#" + targetName).val("");
}
window.openInvestSave = function(){
    getOpenWindow2("/plm/ext/invest/saveInvestPopup", "InvestPopup", 1280, 720);
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
                                <td class="font_01">고객 투자비 회수 관리 검색
                                <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
			                    <a href="/plm/ext/dms/redirectSG/SG-19-002/DOWN" target="download"><!-- 시스템가이드 링크 -->
			                        <img src="/plm/portal/images/exclamation.png" class="detailInfo" style="cursor:pointer;" title="정보를 보려면 클릭하세요." />
			                    </a>
			                    <c:if test="${ket:isAdmin() }">
			                    <a href="#" onClick="javascript:getOpenWindow2('/plm/ext/dms/redirectSG/SG-19-002/VIEW', 'SGDocumentPopup', 800, 450);"><!-- 시스템가이드 팝업 -->
			                        <img src="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/images/tree/leaf.gif" title="시스템 가이드 문서 상세정보">
			                    </a>
			                    </c:if>
                                </td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home
                                <img src="/plm/portal/images/icon_navi.gif">Project
                                <img src="/plm/portal/images/icon_navi.gif">고객 투자비 회수 관리</td>
                            </tr>
                        </table>
                    </td>
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
                            <c:if test="${isRegMember }">
                            <td width="5">&nbsp;</td>
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:openInvestSave();" class="btn_blue">
                                                    등록
                                            </a>
                                        </td>
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
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:invest.clear();" class="btn_blue">
                                                   초기화
                                            </a>
                                        </td>
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
                                            <a href="javascript:investSearch();" class="btn_blue">
                                                   검색
                                            </a>
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
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            
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
                        <col width="8%" />
                        <col width="10%" />
                        <col width="8%" />
                        <col width="10%" />
                        <col width="8%" />
                        <col width="10%" />
                        <col width="8%" />
                        <col width="10%" />
                        <col width="8%" />
                        <col width="10%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">ProjectNo</td>
                        <td class="tdwhiteL">
                        <input type="text" name="pjtNo" class="txt_field" style="width:70%" value="">
                          <a href="javascript:SearchUtil.selectOneProjectPopUp('setProjectNo');">
                          <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('pjtNo');">
                          <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">투자품의번호</td>
                        <td class="tdwhiteL"><input type="text" name=reqNo class="txt_field" style="width: 90%" value=""></td>
		                <td class="tdblueL">투자품의명</td>
		                <td class="tdwhiteL"><input type="text" name=reqName class="txt_field" style="width: 90%" value=""></td>
		                <td class="tdblueL">영업 담당자</td>
		                <td class="tdwhiteL">
		                    <input type="text" name="managerName" style="width: 70%;" class="txt_field" value="">
		                    <input type="hidden" name="managerOid" value="">
		                    <a href="javascript:selectOneUser_('managerOid','managerName');">
		                        <img src="/plm/portal/images/icon_user.gif" border="0">
		                    </a>
		                    <a href="javascript:CommonUtil.deleteValue('managerOid','managerName');">
		                        <img src="/plm/portal/images/icon_delete.gif" border="0">
		                    </a>
		                </td>
		                <td class="tdblueL">영업부서</td>
		                <td class="tdwhiteL">
		                    <input style="width: 70%;" type="text" id="deptName" name="deptName" class="txt_field" value="">
		                    <input type=hidden id="deptCode" name="deptCode" value="">
		                    <a href="javascript:addDepartment('deptCode', 'deptName');">
		                        <img src="/plm/portal/images/icon_5.png">
		                    </a>
		                    <a href="javascript:delDepartment('deptCode', 'deptName');">
		                    <img src="/plm/portal/images/icon_delete.gif">
		                    </a>
		                </td>
		            </tr>
		            <tr>
		                <td class="tdblueL">관련부서 담당자</td>
		                <td class="tdwhiteL">
		                    <input type="text" name="tmUserName" style="width: 70%;" class="txt_field" value="">
		                    <input type="hidden" name="tmUserOid" value="">
		                    <a href="javascript:selectOneUser_('tmUserOid','tmUserName');">
		                        <img src="/plm/portal/images/icon_user.gif" border="0">
		                    </a>
		                    <a href="javascript:CommonUtil.deleteValue('tmUserOid','tmUserName');">
		                        <img src="/plm/portal/images/icon_delete.gif" border="0">
		                    </a>
		                </td>
		                <td class="tdblueL masterTD">신호등</td>
		                <td class="tdwhiteL masterTD">
                            <select onBlur="fm_jmp" id="investState" name="investState" multiple="multiple" style="width:170px">
                               <option value="">선택</option> 
                               <option value="red">위험</option>
                               <option value="orange">주의</option>
                               <option value="gray">완료</option>
                               <option value="blue">정상</option>
                            </select>
                        </td>
		                
		                <td class="tdblueL masterTD">상태</td>
                        <td class="tdwhiteL masterTD">
                            <ket:select id="state" name="state" value="" className="fm_jmp"  codeType="INVESTSTATE" multiple='multiple' useCodeValue="true" />
                        </td>
                        
		                <td class="tdblueL">회수완료 예정일</td>
		                <td class="tdwhiteL" colspan="3">
		                    <input type="text" name="requestStartDate" class="txt_field" style="width: 70px;" value="">
		                    <a href="javascript:CommonUtil.deleteValue('requestStartDate');">
		                        <img src="/plm/portal/images/icon_delete.gif" border="0">
		                    </a>~ 
		                    <input type="text" name="requestEndDate" class="txt_field" style="width: 70px;" value="">
		                    <a href="javascript:CommonUtil.deleteValue('requestEndDate');">
		                        <img src="/plm/portal/images/icon_delete.gif" border="0">
		                    </a>
		                </td>
		            </tr>
                </table>
                <iframe id="download" name="download" align="center" width="0" height="0" border="0" style="display:none" onreadystatechange="hideProcessing();"></iframe>
            </form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		            <td align="right">
		                <table border="0" cellspacing="0" cellpadding="0" width="100%">
		                    <tr>
		                       <td class="space5"></td>
		                    </tr>
		                </table> <!-- EJS TreeGrid Start -->
		                <div class="content-main">
		                    <div class="container-fluid">
		                        <div class="row-fluid">
		                            <div id="ISSUEMASTER" class="tabContent" style="border: 0; border-radius: 0;padding-top:0 !important;">
		                                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
		                            </div>
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