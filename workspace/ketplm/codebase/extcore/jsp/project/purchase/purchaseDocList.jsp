<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/project/purchase/purchaseDoc.js"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
html{width:100%;height:100%;}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
	SuggestUtil.bind('PRODUCTPARTNO', 'partNo');
	SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo');
    SuggestUtil.bind('USER', 'pm', 'pmOid');
    SuggestUtil.bind('DEPARTMENT', 'pmDept', 'pmDeptOid');
    CommonUtil.multiSelect('state',100);
    CommonUtil.multiSelect('pjtState',100);
    CommonUtil.multiSelect('devDateType',100);
    CalendarUtil.dateInputFormat('selectStartDate','selectEndDate');
    var url = '/plm/ext/project/purchase/findDocList.do';
    if('${pjtNo}' == ''){
        url = '/plm/jsp/common/treegrid/InitGridData.jsp';
    }
    
    var flag = '${pjtNo}' == '' ? '1' : '2';
    
    $('input[name="selectGb"]').each(function() {
        var value = $(this).val();
        if(flag == value){
        	$(this).prop("checked", true)
        }
    });
    
	purchase.createPaingGrid(url);
	treeGridResize("#searchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	purchase.trimPjtNo();
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
        	purchase.search();
        }
    });
    
    $('#pjtNo').focus();
});

window.purchaseSearch = function(){
	purchase.trimPjtNo();
	purchase.search();
}

//############################ 담당자 #########################################################
function addUser(targetName, targetId) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
    acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
        return;
    }
    // isAppend : Y - 이전 값에 현재 선택된 값 추가
    // isAppend : N - 현재 선택 된 값만 추가
    var isAppend = "Y";
    acceptUser(targetName, targetId, acceptUsers, isAppend);
}

function acceptUser(targetName, targetId, arrObj, isAppend) {
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
function addDepartment(targetName, targetId) {
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

    if ( typeof(attacheDept) != "object" ) {
        var deptSplit = attacheDept.split(",");
        
        for ( var i = 0; i<deptSplit.length-1; i++ ) {
            var oid = deptSplit[i];
            var param = { oid : oid };
            var data = ajaxCallServer("/plm/ext/common/getObjectData", param, null, false);
            deptMerge(data.name, oid, targetName, targetId);
        }
    } else {
        var oid = attacheDept[0][0];
        var param = { oid : oid };
        var data = ajaxCallServer("/plm/ext/common/getObjectData", param, null, false);
        deptMerge(data.name, oid, targetName, targetId);
    }
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
                                <td class="font_01">외주개발품/구매품 개발현황(산출물 조회)                            
                                </td>
                                <td align="right"></td>
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
                            <td>
                                 <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="javascript:purchaseSearch();" class="btn_blue">
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
                <input type="hidden" name="searchParam" id="searchParam" value="main">
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
                        <col width="10%" />
                        <col width="10%" />
                        <col width="15%" />
                        <col width="10%" />
                        <col width="15%" />
                        <col width="10%" />
                        <col width="30%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL0"><input type="text" name="pjtNo" id="pjtNo" class="txt_field" style="width: 80%" value="${pjtNo}"></td>
                        
                        <td class="tdblueL" >Project <%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td class="tdwhiteL">
                            <select id="state" name="state" class="fm_jmp" style="width: 160px;" multiple="multiple">
                                <c:forEach items="${stateList }" var="state">
                                    <option value="${state.key }">${state.display }</option>
                                </c:forEach>
                            </select>
                        </td>
                        
                        <td class="tdblueL">담당자</td>
                        <td class="tdwhiteL">
                        <input type="text" name="pm" id="pm" class="txt_field" style="width: 70%">
                            <input type="hidden" name="pmOid" id="pmOid"> 
                            <a href="javascript:addUser('pm','pmOid')">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('pm','pmOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">담당부서</td>
                        <td class="tdwhiteL0">
                        <input style="width:70%;" type="text" id="pmDept" name="pmDept" class="txt_field" value="">
                            <input type=hidden id="pmDeptOid" name="pmDeptOid" value="">
                            <a href="javascript:addDepartment('pmDept','pmDeptOid');"><img src="/plm/portal/images/icon_5.png"></a>
                            <a href="javascript:CommonUtil.deleteValue('pmDept','pmDeptOid');"><img src="/plm/portal/images/icon_delete.gif"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">조회대상 기준</td>
                        <td class="tdwhiteL0">
                        <input type="radio" name="selectGb" id="selectGb" value="1">구매품
                        <input type="radio" name="selectGb" id="selectGb" value="2">신규 Item
                        </td>
                        
                        <td class="tdblueL">품번</td>
                        <td class="tdwhiteL0"><input type="text" name="partNo" id="partNo" class="txt_field" style="width: 60%" value=""></td>
                        
                        <td class="tdblueL" >일정관리 <%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td class="tdwhiteL">
                            <select id="pjtState" name="pjtState" class="fm_jmp" style="width: 160px;" multiple="multiple">
                                <option value="PMOINWORK">등록중</option>
                                <option value="PROGRESS">진행</option>
                                <option value="DELAY">지연</option>
                                <option value="COMPLETED">완료</option>
                            </select>
                        </td>
                        
                        <td class="tdblueL">
                            <ket:select id="devDateType" name="devDateType" className="fm_jmp" style="width:130px;" codeType="DEVELOPDATETYPE" multiple="multiple" useCodeValue="true"  value="DEVELOPDATESTART" />
                        </td>
                        <td class="tdwhiteL0">
                            <input id="selectStartDate" name="selectStartDate" value="" class="txt_field" style="width: 80px;" maxlength="10"/>
                            <a href="JavaScript:clearDate('selectStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;~&nbsp;
                            <input id="selectEndDate" name="selectEndDate" value="" class="txt_field" style="width: 80px;" maxlength="10"/>
                            <a href="JavaScript:clearDate('selectEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
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
		                </table> <!-- EJS TreeGrid Start -->
		                <div class="content-main">
		                    <div class="container-fluid">
		                        <div class="row-fluid">
		                            <div class="tabContent" style="border: 0; border-radius: 0;padding-top:0 !important;">
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