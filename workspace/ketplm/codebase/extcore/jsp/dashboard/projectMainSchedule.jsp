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
<script type="text/javascript" src="/plm/extcore/js/dashboard/projectMainSchedule.js?ver=1.1"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
.headerDiv{text-align:center;line-height:18px;}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
	
    SuggestUtil.bind('USER', 'salesManager', 'salesManagerOid');
    SuggestUtil.bind('USER', 'pm', 'pmOid');
    SuggestUtil.bind('DEPARTMENT', 'pmDept', 'pmDeptOid');
    SuggestUtil.bind('DEPARTMENT', 'salesDept', 'salesDeptOid');
    SuggestUtil.bind('CUSTOMER', 'subContractorName', 'subContractor');
    SuggestUtil.bind('CARTYPE', 'carType', 'carTypeOid');
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNo');
    
    //상태
    $("#state").multiselect({
        minWidth: 110,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    //개발단계
    $("#process").multiselect({
        minWidth: 110,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    //개발목적1
    $("#devPurpose1").multiselect({
        minWidth: 80,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    //개발목적2
    $("#devPurpose2").multiselect({
        minWidth: 80,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    //개발시작일/완료일
    $("#devDateType").multiselect({
        minWidth: 80,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
    });
    
    
    CalendarUtil.dateInputFormat('selectStartDate','selectEndDate');
    
    
    pmschedule.createPaingGrid();
    treeGridResize("#projectMainScheduleGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	pmschedule.search();
        }
    });
});
//####################### 개발목적 #########################################
function devPurpose() {
    if ( $("#devPurpose1").val() != null ) {
        var choiceCode  = $("#devPurpose1").val();
        if(choiceCode.length > 1){
            $("#devPurpose2").multiselect("uncheckAll");
            $("#devPurpose2").empty().data('options');
            for(var i = 0; i < choiceCode.length; i++){
                $.ajax({
                    url : "/plm/ext/code/getChildCodeList.do?codeType=DEVELOPANDREVIEWGOAL&parentCode="+choiceCode[i],
                    type : "POST",
                    dataType : 'json',
                    async : false,
                    success: function(data) {
                        $.each(data, function() {
                            $("#devPurpose2").append("<option value='"+this.code+"'>"+ this.value +"</option>");
                        });
                        
                        $("#devPurpose2").multiselect('refresh');
                    }
                });
            }
            
        }
        else{
            $("#devPurpose2").multiselect("uncheckAll");
            $("#devPurpose2").empty().data('options');
            $.ajax({
                url : "/plm/ext/code/getChildCodeList.do?codeType=DEVELOPANDREVIEWGOAL&parentCode="+choiceCode,
                type : "POST",
                dataType : 'json',
                async : false,
                success: function(data) {
                    $.each(data, function() {
                        $("#devPurpose2").append("<option value='"+this.code+"'>"+ this.value +"</option>");
                    });
                    
                    $("#devPurpose2").multiselect('refresh');
                }
            });
        }
     }
}


//############################ 담당자 #########################################################
var userTargetName = "";
var userTargetId = "";
function addUser(targetName, targetId) {
	userTargetName = targetName;
	userTargetId = targetId;
    SearchUtil.selectOneUser('','','acceptUser');
    
}

function acceptUser(arrObj) {
	targetName = userTargetName;
	targetId = userTargetId;
	
	var isAppend = "Y";
    var userId = new Array();     // Id
    var userName = new Array();   // Nmae
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtuser oid // [1] - people oid // [2] - dept oid
        // [3] - user id    // [4] - name       // [5] - dept name
        // [6] - duty       // [7] - duty code  // [8] - email

        var infoArr = arrObj[i];
        userId[i] = infoArr[0];
        userName[i] = infoArr[4];
    }

    var tmpId = new Array();
    var tmpName = new Array();
    if ( $("#" + targetId).val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpId = $.merge($("#" + targetId).val().split(", "), userId);
        tmpId = $.unique(tmpId.sort());

        tmpName = $.merge($("#temp" + targetId).val().split(", "), userName);
        tmpName = $.unique(tmpName.sort());
    }
    else {
        tmpId = userId.sort();
        tmpName = userName.sort();
    }
    
    $("#" + targetName).val(tmpName.join(", "));
    $("#" + targetId).val(tmpId.join(", "));
}

//######################################### 부서 ########################################################
var _targetName = "";
var _targetId = "";

function callBackFunctDepartment(rsltArrayObject){
	var code = "";
    var name = "";
    for(var i= 0 ; i < rsltArrayObject.length; i++){
        if(i != 0){
            code += ",";
            name += ",";
        }
        code += rsltArrayObject[i].OID;
        name += rsltArrayObject[i].NAME;
    }
    deptMerge(name, code, _targetName, _targetId);
}

function addDepartment(targetName, targetId) {

    _targetName = targetName;
    _targetId = targetId;
	SearchUtil.addDepartmentAfterFunc(true,'callBackFunctDepartment');
	
}

function deptMerge(deptName, deptOid, targetName, targetId) {
	window.console.log(deptOid);
    if ( $("#" + targetId).val() == "" ) {
        $("#" + targetId).val( deptOid );
        $("#" + targetName).val( deptName );
    }
    else {
        var deptOidArr = $("#" + targetId).val().split(", ");
        var deptNameArr = $("#" + targetName).val().split(", ");
        // 선택된 부서 push
        deptOidArr.push(deptOid);
        deptNameArr.push(deptName);
        // 중복 부서 정리
        deptOidArr = $.unique(deptOidArr.sort());
        deptNameArr = $.unique(deptNameArr.sort());
        
        $("#" + targetName).val( deptNameArr.join(", ") );
        $("#" + targetId).val( deptOidArr.join(", ") );
    }
    
    window.console.log($("#" + targetId).val());
}
window.setCarTypeMulti = function(data){
    
    var oemNames = $("#carType").val();
    var oemOids = $("#carTypeOid").val();
    
    for(var i = 0; i < data.length; i++){
        
        var oemOid = data[i][0];
        var oemName = data[i][1];
        
        if(oemOids.indexOf(oemOid) < 0){
            if(oemNames.length != 0) {
                oemNames += ",";
                oemOids += ",";
            }
            
            oemOids += oemOid;
            oemNames += oemName;
        }
    }
    
    $("#carType").val(oemNames);
    $("#carTypeOid").val(oemOids);
}
//################# 접점고객 ##############
window.setsubContractorName = function(data){
    
    var nodeCodeStr='', nodeNameStr='';
    
    window.console.log(data);
    
    for(var i=0; i < data.length; i++){
        if(i == data.length - 1){
            nodeCodeStr += data[i][0];
            nodeNameStr += data[i][2];
        }else{
            nodeCodeStr += data[i][0]+',';     
            nodeNameStr += data[i][2]+',';
        }
    }
    $('[name=subContractorName]').val(nodeNameStr);
    $('[name=subContractor]').val(nodeCodeStr);
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
                                <td class="font_01">프로젝트 주요일정</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Dashboard
                                <img src="/plm/portal/images/icon_navi.gif">Report
                                <img src="/plm/portal/images/icon_navi.gif">프로젝트 주요일정</td>
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
                                            onclick="javascript:pmschedule.clear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
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
                                            href="javascript:pmschedule.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
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
                <input type="hidden" name="sortKey" id="sortKey" value="">
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
                    </colgroup>
                    <tr>
                        <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
			            <td class="tdwhiteL">
			                <select id="state" name="state" class="fm_jmp" style="width: 160px;" multiple="multiple">
				                <c:forEach items="${stateList }" var="state">
				                    <c:choose>
				                        <c:when test="${state.key eq 'PROGRESS' or state.key eq 'PLANCHANGE' or state.key eq 'UNDERREVIEW' }">
				                            <option value="${state.key }" selected>${state.display }</option>
				                        </c:when>
				                        <c:otherwise>
				                            <option value="${state.key }">${state.display }</option>
				                        </c:otherwise>
				                    </c:choose>
				                </c:forEach>
			                </select>
			            </td>
			            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                        <td class="tdwhiteL">
                            <ket:select id="process" name="process" className="fm_jmp" style="width:130px;" codeType="PROCESS" multiple="multiple" useCodeValue="true" value=""/>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09483") %><%--개발목적--%></td>
			            <td class="tdwhiteL">
				            <ket:select id="devPurpose1" name="devPurpose1" className="fm_jmp" style="width:130px;" codeType="DEVELOPANDREVIEWGOAL" multiple="multiple" useCodeValue="true" onChange="devPurpose();" value=""/>
				            &nbsp;<select id="devPurpose2" name="devPurpose2" class="fm_jmp" multiple="multiple" style="width:130px"></select>
			            </td>
                    </tr>
                    <tr>
                        <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--PM담당부서--%></td>
			            <td class="tdwhiteL">
			                <input style="width:70%;" type="text" id="pmDept" name="pmDept" class="txt_field" value="">
			                <input type=hidden id="pmDeptOid" name="pmDeptOid" value="">
			                <a href="javascript:addDepartment('pmDept','pmDeptOid');"><img src="/plm/portal/images/icon_5.png"></a>
			                <a href="javascript:CommonUtil.deleteValue('pmDept','pmDeptOid');"><img src="/plm/portal/images/icon_delete.gif"></a>
			            </td>
			            <td class="tdblueL" style="width: 110px;">영업부서<%--영업부서--%></td>
                        <td class="tdwhiteL">
                            <input style="width:70%;" type="text" id="salesDept" name="salesDept" class="txt_field" value="">
                            <input type=hidden id="salesDeptOid" name="salesDeptOid" value="">
                            <a href="javascript:addDepartment('salesDept','salesDeptOid');"><img src="/plm/portal/images/icon_5.png"></a>
                            <a href="javascript:CommonUtil.deleteValue('salesDept','salesDeptOid');"><img src="/plm/portal/images/icon_delete.gif"></a>
                        </td>
                        <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="carType" id="carType" class="txt_field" style="width: 70%">
                            <input type="hidden" name="carTypeOid" id="carTypeOid"> 
		                    <a href="javascript:SearchUtil.selectCarTypeMulti('setCarTypeMulti');">
		                        <img src="/plm/portal/images/icon_5.png" border="0">
		                    </a>
		                    <a href="javascript:CommonUtil.deleteValue('carTypeOid','carType');">
		                        <img src="/plm/portal/images/icon_delete.gif" border="0">
		                    </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00370") %><%-- PM --%></td>
			            <td class="tdwhiteL">
			                <input type="text" name="pm" id="pm" class="txt_field" style="width: 70%">
                            <input type="hidden" name="pmOid" id="pmOid"> 
			                <a href="javascript:addUser('pm','pmOid')">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('pm','pmOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
			            </td                                                                                                                                                                                                                                                                                                                          >
			            <td class="tdblueL">
                            영업담당자
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="salesManager" id="salesManager" class="txt_field" style="width: 70%">
                            <input type="hidden" name="salesManagerOid" id="salesManagerOid"> 
                            <a href="javascript:addUser('salesManager','salesManagerOid')">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('salesManager','salesManagerOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%></td>
                        <td class="tdwhiteL">
                            <input type="text" name="subContractorName" id="subContractorName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="subContractor" id="subContractor"> 
                            <a href="javascript:SearchUtil.selectMultiSubContractor('setsubContractorName');">
		                        <img src="/plm/portal/images/icon_5.png" border="0">
		                    </a>
		                    <a href="javascript:CommonUtil.deleteValue('subContractorName','subContractor');">
		                        <img src="/plm/portal/images/icon_delete.gif" border="0">
		                    </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL" style="width: 110px;">Project No</td>
	                        <td class="tdwhiteL">
	                            <input type="text" name="projectNo" class="txt_field" style="width:70%;" value="">
	                        </td>
		                <td class="tdblueL"  style="width: 110px;">
		                    <ket:select id="devDateType" name="devDateType" className="fm_jmp" style="width:130px;" codeType="DEVELOPDATETYPE" multiple="multiple" useCodeValue="true"  value="DEVELOPDATESTART" />
			            </td>
			            <td class="tdwhiteL0">
			                <input id="selectStartDate" name="selectStartDate" value="" class="txt_field" style="width: 80px;" maxlength="10"/>
			                <a href="JavaScript:clearDate('selectStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;~&nbsp;
			                <input id="selectEndDate" name="selectEndDate" value="<fmt:formatDate value='${toDay}' pattern='yyyy-MM-dd' />" class="txt_field" style="width: 80px;" maxlength="10"/>
			                <a href="JavaScript:clearDate('selectEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
			            </td>
                        <td class="tdwhiteL" colspan="2">
                            <label>
                            <input type="checkbox" name="isNotProto" value="true" onClick="pmschedule.hideCols($(this).is(':checked'));"/>
                            PROTO 일정 제외
                            </label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <label>
                            <input type="checkbox" name="isRole" id="isRole" value="Y"/>
                                주요 Role 포함
                            </label>
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