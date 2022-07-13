<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script type="text/javascript" src="/plm/extcore/js/issue/issue.js?ver=0.1"></script>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
html{width:100%;height:100%;}
</style>
<script type="text/javascript">
$(document).ready(function(e) {
    
    issue.createPaingGrid();
    issue.createTaskPaingGrid();
    
    CommonUtil.singleSelect('issueState',130);
    CommonUtil.singleSelect('issueTaskState',130);
    CommonUtil.singleSelect('reqCode',130);
    CommonUtil.multiSelect('detailCode',130);
    CommonUtil.multiSelect('divisionCode', 100);
    
    CalendarUtil.dateInputFormat('requestStartDate'); //requestStartDate
    CalendarUtil.dateInputFormat('requestEndDate'); //requestEndDate
    
    CalendarUtil.dateInputFormat('completeStartDate'); //completeStartDate
    CalendarUtil.dateInputFormat('completeEndDate'); //completeEndDate

    SuggestUtil.bind('USERDEPT', 'managerName', 'managerOid');
    SuggestUtil.bind('USERDEPT', 'tmUserName', 'tmUserOid');
    
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    SuggestUtil.bind('DEPARTMENT', 'taskDeptName', 'taskDeptCode');
    
    //SuggestUtil.bind('CUSTOMEREVENT', 'lastCustomerName', 'lastCustomer');
    SuggestUtil.bind('CARTYPE', 'oemName', 'oemOid');
    SuggestUtil.bind('PRODUCTPARTNO', 'partNos');
    
    
    $("select[name=issueTaskState]").change(function(){
        
        var value = $(this).val();
        
        if(value == "IST002") $(".delayState").show();
        else                  $(".delayState").hide();
    });
    
    window.activeTabFlag = "ISSUEMASTER";
    
    if(!${isSalesAuth}) window.activeTabFlag = "ISSUETASK";
    
    if("ISSUETASK" == activeTabFlag){
        $(".taskTD").show();
        $(".masterTD").hide();
    }else{
        $(".masterTD").show();
        $(".taskTD").hide();
    }
    
    var tab = null;
    if(window.activeTabFlag == "ISSUETASK") {
        tab = CommonUtil.tabs('tabs', 1);
         $('.tabContent').hide();
         $('.tabContent :eq(1)').show();
    }else {
        tab = CommonUtil.tabs('tabs');
         $('.tabContent').hide();
         $('.tabContent :first').show();
    }
    
    var isGridLoad = false;
    
    $(".tabref").click(function(){
        $(".tabContent").hide();
        var ref = $(this).attr("href");
        window.activeTabFlag = $(ref).attr("id");
        
        $(ref).show(0, function(){
            if("ISSUETASK" == activeTabFlag && !isGridLoad){
                Grids['taskSearchGrid'].Update(); 
                isGridLoad = true;
            }else if(!isGridLoad){
                Grids['searchGrid'].Update(); 
                isGridLoad = true;
            }
        });
        
        if("ISSUETASK" == activeTabFlag){
            $(".taskTD").show();
            $(".masterTD").hide();
        }else{
            $(".masterTD").show();
            $(".taskTD").hide();
        }
    });
    
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
            if("ISSUETASK" == activeTabFlag){
                issue.searchTask();
            }else{
                issue.search();
            }
        }
    });
    
    
    Grids.OnDownloadPage = function(grid,row){
        grid.Data.Page.Format = 'Form';
        grid.Data.Page.Method = 'Post';
        
        if("ISSUETASK" == activeTabFlag){
            grid.Data.Page.Url = '/plm/ext/issue/findTaskPagingList.do?sortName=*Sort0';
        }else{
            grid.Data.Page.Url = '/plm/ext/issue/findPagingList.do?sortName=*Sort0';
        }
        
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
    
    $("#reqCode").change(function(){
        
        issue.initDetailCode($(this).val());

    });
    
    treeGridResize("#searchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    treeGridResize("#taskSearchGrid","#taskListGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    
});

window.issueSearch = function(){
    
    if("ISSUETASK" == activeTabFlag){
        issue.searchTask();
    }else{
        issue.search();
    }
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

function selectDeptRsltFunc(rsltArray){
    var name = "";
    var oid = "";
    var code = "";
    
    for(var i = 0; i < rsltArray.length ; i++){
         if(i > 0){
             oid += ",";
             name += ",";
             code += ",";
         }
        name += rsltArray[i].NAME;
        oid += rsltArray[i].OID;
        code += rsltArray[i].CODE;
    }
    
    $("#" + window.deptTagetCode).val(oid);
    $("#" + window.deptTagetName).val(name);
}

function addDepartment(codeTarget, nameTarget) {
    
    window.deptTagetName = nameTarget;
    window.deptTagetCode = codeTarget;
    
    SearchUtil.addDepartmentAfterFunc(false, 'selectDeptRsltFunc');
}


function delDepartment(targetId, targetName) {
    $("#" + targetId).val("");
    $("#" + targetName).val("");
}
window.openIssueSave = function(){
    getOpenWindow2("/plm/ext/issue/saveIssuePopup", "IssuePopup", 1280, 720);
}

function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    for(var i = 0; i < objArr.length; i++)
    {
        setPart(objArr[i]);
    }
}

function setPart(objArr){
    trArr = objArr;
    var tempRelatedPartOid = $('[name=relatedPartOid]').val();
    var tempRelatedPart = $('[name=partNos]').val();
    
    if(tempRelatedPartOid != ""){
        tempRelatedPartOid = tempRelatedPartOid+",";
    }
    if(tempRelatedPart != ""){
        tempRelatedPart = tempRelatedPart+",";
    }
    
    $('[name=relatedPartOid]').val(tempRelatedPartOid+trArr[0]);
    $('[name=partNos]').val(tempRelatedPart+trArr[1]);
}

function insertLastUsingBuyer(arrObj){
    $('[name=lastCustomer]').val(arrObj[0][1]);
    $('[name=lastCustomerName]').val(arrObj[0][2]);
}
function setUser1(returnValue){
	var infoArr = returnValue[0];
    $('[name=managerOid]').val(infoArr[0]);
    $('[name=managerName]').val(infoArr[4]);
}
function setUser2(returnValue){
    var infoArr = returnValue[0];
    $('[name=tmUserOid]').val(infoArr[0]);
    $('[name=tmUserName]').val(infoArr[4]);
}

window.setCarType = function(returnValue){
    
    $('[name=oemOid]').val(returnValue[0][0])//id
    $('[name=oemName]').val(returnValue[0][1])//name
    
}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01">고객/사내 요구사항 검색
                    <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>
                    <a href="/plm/ext/dms/redirectSG/SG-18-001/DOWN" target="download"><!-- 시스템가이드 링크 -->
                        <img src="/plm/portal/images/exclamation.png" class="detailInfo" style="cursor:pointer;" title="정보를 보려면 클릭하세요." />
                    </a>
                    <c:if test="${ket:isAdmin() }">
                    <a href="#" onClick="javascript:getOpenWindow2('/plm/ext/dms/redirectSG/SG-18-001/VIEW', 'SGDocumentPopup', 800, 450);"><!-- 시스템가이드 팝업 -->
                        <img src="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/images/tree/leaf.gif" title="시스템 가이드 문서 상세정보">
                    </a>
                    </c:if>
                    </td>
                    <td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">HOME
                    <img src="/plm/portal/images/icon_navi.gif">Project
                    <img src="/plm/portal/images/icon_navi.gif">고객/사내 요구사항
                    </td>
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
<!-- [START] search condition -->
<div id="tabs" style="margin:0 auto;">
    <ul>
        <li><a class="tabref" href="#ISSUEMASTER">요청서</a></li>
        <li><a class="tabref" href="#ISSUETASK">세부진행항목</a></li>
    </ul>
    <div class="float-r" style="position:relative;top:-30px;">
       <%-- <c:if test="${isSalesAuth }"> --%>
       <span class="in-block v-middle r-space7">
            <span class="pro-table">
                <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center">
                    <a href="javascript:openIssueSave();" class="btn_blue">
                    등록
                    </a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
        </span>
        <%-- </c:if> --%>
       <span class="in-block v-middle r-space7">
            <span class="pro-table">
                <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center">
                    <a href="javascript:issue.clear();" class="btn_blue">
                    <%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%>
                    </a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
        </span>
        <span class="in-block v-middle r-space7">
            <span class="pro-table">
                <span class="pro-cell b-left"></span>
                <span class="pro-cell b-center">
                    <a href="javascript:issueSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
                </span>
                <span class="pro-cell b-right"></span>
            </span>
        </span>
    </div>
    <form name="searchForm">
        <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center" style="position:relative;top:-5px;">
            <img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon">
        </div>
        <table style="width: 100%;position:relative;top:-24px;">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <table style="width:100%;position:relative;top:-24px;">
            <colgroup>
                <col width="8%" />
                <col width="20%" />
                <col width="8%" />
                <col width="20%" />
                <col width="8%%" />
                <col width="20%" />
                <col width="10%" />
                <col width="*" />
            </colgroup>
            <tr>
                <!-- <td class="tdblueL">Project No</td>
                <td class="tdwhiteL">
                    <input type="text" name="pboNo" style="width: 70%" class="txt_field" value="">
                    <input type="hidden" name="pboOid" value="">
                    <a href="javascript:SearchUtil.selectOneSalesProject('issue.setPboNo');">
                        <img src="/plm/portal/images/icon_5.png" border="0">
                    </a>
                    <a href="javascript:CommonUtil.deleteValue('pboNo','pboOid');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
                </td>
                <td class="tdblueL">프로젝트명</td>
                <td class="tdwhiteL">
                    <input type="text" name="pboName" class="txt_field" style="width: 70%" value=""></td> -->
                
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09005") %><%--부품 NO--%></td>
                <td class="tdwhiteL">
                    <input type="text" name="partNos" class="txt_field" style="width: 70%">
                    <input type="hidden" name="relatedPartOid">
                    <a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc','pType=');">
                    <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                    <a href="javascript:CommonUtil.deleteValue('partNos', 'relatedPartOid');">
                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                </td>
                
                <td class="tdblueL">상세구분<span class="red">*</span></td>
                <td class="tdwhiteL">
                    <ket:select id="reqCode" name="reqCode" className="fm_jmp" codeType="ISSUEREQ" multiple="multiple" useCodeValue="true" value=""  />
                    <ket:select id="detailCode" name="detailCode" className="fm_jmp" codeType="" multiple="multiple" useCodeValue="true" value=""  />
                </td>
                
                <td class="tdblueL">요청부서</td>
                <td class="tdwhiteL" colspan="3">
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
                <td class="tdblueL">요청명</td>
                <td class="tdwhiteL">
                    <input type="text" name="reqName" class="txt_field" style="width: 98%">
                </td>
                <td class="tdblueL">자동차사</td>
                <td class="tdwhiteL">
                    <input type="text" name="lastCustomerName" style="width: 70%;" class="txt_field" value="">
                    <input type="hidden" name="lastCustomer" value="">
                    <a href="javascript:SearchUtil.selectOneLastUsingBuyerPopup('insertLastUsingBuyer');">
                        <img src="/plm/portal/images/icon_5.png" border="0">
                    </a>
                    <a href="javascript:CommonUtil.deleteValue('lastCustomer','lastCustomerName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
                </td>
                <td class="tdblueL">고객사</td>
                <td class="tdwhiteL" colspan="3">
                    <input type="text" name="subContractorName" style="width: 70%;" class="txt_field" value="">
                    <input type="hidden" name="subContractor" value="">
                    <a href="javascript:SearchUtil.selectMultiSubContractorPopUp('setSubContractor');">
                        <img src="/plm/portal/images/icon_5.png" border="0">
                    </a>
                    <a href="javascript:CommonUtil.deleteValue('subContractorName','subContractor');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
                </td>
            <tr>
                <td class="tdblueL">요청자</td>
                <td class="tdwhiteL">
                    <input type="text" name="managerName" style="width: 70%;" class="txt_field" value="">
                    <input type="hidden" name="managerOid" value="">
                    <a href="javascript:SearchUtil.selectOneUser('managerOid','managerName','setUser1');">
                        <img src="/plm/portal/images/icon_user.gif" border="0">
                    </a>
                    <a href="javascript:CommonUtil.deleteValue('managerOid','managerName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
                </td>
                <td class="tdblueL">세부 담당자</td>
                <td class="tdwhiteL">
                    <input type="text" name="tmUserName" style="width: 70%;" class="txt_field" value="">
                    <input type="hidden" name="tmUserOid" value="">
                    <a href="javascript:SearchUtil.selectOneUser('tmUserOid','tmUserName','setUser2');">
                        <img src="/plm/portal/images/icon_user.gif" border="0">
                    </a>
                    <a href="javascript:CommonUtil.deleteValue('tmUserOid','tmUserName');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
                </td>
                <td class="tdblueL masterTD">요청서 상태</td>
                <td class="tdwhiteL masterTD" colspan="3">
                    <ket:select id="issueState" name="issueState" className="fm_jmp" codeType="ISSUESTATE" multiple="multiple" useNullValue="true" useCodeValue="true" />
                </td>
                <td class="tdblueL taskTD">세부 담당부서</td>
                <td class="tdwhiteL taskTD" colspan="3">
                    <input style="width: 70%;" type="text" id="taskDeptName" name="taskDeptName" class="txt_field" value="${deptName }">
                    <input type=hidden id="taskDeptCode" name="taskDeptCode" value="${deptOid }">
                    <a href="javascript:addDepartment('taskDeptCode', 'taskDeptName');">
                        <img src="/plm/portal/images/icon_5.png">
                    </a>
                    <a href="javascript:delDepartment('taskDeptCode','taskDeptName');">
                    <img src="/plm/portal/images/icon_delete.gif">
                    </a>
                </td>
            </tr>
            <tr>
                <td class="tdblueL">등록일자</td>
                <td class="tdwhiteL">
                    <input type="text" name="requestStartDate" class="txt_field" style="width: 70px;" value="">
                    <a href="javascript:CommonUtil.deleteValue('requestStartDate');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>~ 
                    <input type="text" name="requestEndDate" class="txt_field" style="width: 70px;" value="">
                    <a href="javascript:CommonUtil.deleteValue('requestEndDate');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
                </td>
                <td class="tdblueL masterTD">완료일자</td>
                <td class="tdwhiteL masterTD">
                    <input type="text" name="completeStartDate" class="txt_field" style="width: 70px;" value="">
                    <a href="javascript:CommonUtil.deleteValue('completeStartDate');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>~ 
                    <input type="text" name="completeEndDate" class="txt_field" style="width: 70px;" value="">
                    <a href="javascript:CommonUtil.deleteValue('completeEndDate');">
                        <img src="/plm/portal/images/icon_delete.gif" border="0">
                    </a>
                </td>
                <td class="tdblueL taskTD">세부항목 상태</td>
                <td class="tdwhiteL taskTD">
                    <ket:select id="issueTaskState" name="issueTaskState" className="fm_jmp" codeType="ISSUESTATE" multiple="multiple" useNullValue="true" value="IST002" useCodeValue="true" />
                    <label class="delayState" style="font-weight:bold;color:#FF0000;"><input type="checkbox" name="delayState" value="true" />지연</label>
                </td>
                
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
                <td class="tdwhiteL"><ket:select id="divisionCode" name="divisionCode" className="fm_jmp" style="width: 170px;" multiple="multiple" codeType="DIVISIONTYPE" useCodeValue="true"  value="${divisionFlag }"/>
                </td>
                
                <td class="tdblueL">회의대상</td>
                <td class="tdwhiteL" style="text-align: center;"><input type="checkbox" id="meetingTarget" name="meetingTarget" value="Y">
                </td>
                
                
                
                <!-- <th class="tdwhiteL" colspan="2"></th> -->
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
                            <div id="ISSUEMASTER" class="tabContent" style="border: 0; border-radius: 0;padding-top:0 !important;">
                                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%; min-height:200px"></div>
                            </div>
                            <div id="ISSUETASK" class="tabContent" style="border: 0; border-radius: 0;padding-top:0 !important;">
                                <div id="taskListGrid" style="WIDTH: 100%; HEIGHT: 100%; min-height:200px"></div>
                            </div>
                        </div>
                    </div>
                <!-- EJS TreeGrid Start -->
                </div>
            </td>
        </tr>
    </table>
</div>