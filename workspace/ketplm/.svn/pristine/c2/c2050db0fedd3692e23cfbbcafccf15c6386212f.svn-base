<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>
<script type="text/javascript"
    src="/plm/extcore/js/dqm/dqm.js?ver=0.4"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("[name='duplicateYn']").val("${dqm.duplicateYn}");
	$("[name='problemReflectYn']").val("${dqm.problemReflectYn}");
	$("[name='designChangeYn']").val("${dqm.designChangeYn}");
    SuggestUtil.bind('USER','actionUserName', 'actionUserOid');
	CommonUtil.singleSelect('duplicateYn',100);
	CalendarUtil.dateInputFormat('drawingOutDate');
    CalendarUtil.dateInputFormat('moldModifyDate');
    CalendarUtil.dateInputFormat('toDate');
    CalendarUtil.dateInputFormat('trustTestDate');
    CommonUtil.singleSelect('problemReflectYn',100);
    CommonUtil.singleSelect('designChangeYn',100);
    CalendarUtil.dateInputFormat('validationDate');
    CalendarUtil.dateInputFormat('execEndDate');
    
	var causeStrHTMLCode = document.forms[0].causeWebEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(causeStrHTMLCode, false, 0);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor[0].value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage[0].value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat[0].value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    var improveStrHTMLCode = document.forms[0].improveWebEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(improveStrHTMLCode, false, 1);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor[1].value, 1);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage[1].value, 1);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat[1].value, 1);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    var causeCode = "${dqm.causeCode}";
    
    $("[name='causeCode']").each(function(){
        if(causeCode.indexOf(this.value) != -1){
            this.checked = true;    
        }
    });
    
    var designReflectCode = "${dqm.designReflect}";
    
    $("[name='designReflect']").each(function(){
        if(designReflectCode.indexOf(this.value) != -1){
            this.checked = true;    
        }
    });
})

var selDownUrl = "";
var oid = "${dqm.oid}";

function requestApprove()
{
    var form = document.forms[0];
    goPage(form.oid.value);
}

function goModify(){
    location.href="/plm/ext/dqm/updateForm.do?oid=${dqm.oid}&raiseOid=${dqm.raiseOid}";
}

function allCheck(checkboxName, isChecked) {
    var checkedList = document.getElementsByName(checkboxName);

    for ( var i = 0; i < checkedList.length; i++) {
        checkedList[i].checked = isChecked;
    }
}

function selDownload(){
    //get 방식
    /*
    selDownUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet/selected?oid="+oid;
    var checkedList = document.getElementsByName('fileSelect');

    var checkFlag = false;
    
    for ( var i = 0; i < checkedList.length; i++) {
        if(checkedList[i].checked){
            checkFlag = true;
            selDownUrl += "&soid=OR:" + $('[name=secondaryFileOids]')[i].value;
        }
    }
    if(!checkFlag){
        alert("선택해");
        return;
    }
    openWindow(selDownUrl,"","200","200","scrollbars=no,resizable=no");
    */
    
    //post 방식
    document.getElementById("selDownloadForm").innerHTML = "";
    selDownUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet/selected";
    var checkedList = document.getElementsByName('fileSelect');

    var checkFlag = false;
    var str = "";
    var subStr = "";
    for ( var i = 0; i < checkedList.length; i++) {
        if(checkedList[i].checked){
            checkFlag = true;
            subStr += "<input type='hidden' name='soid' value=\'"+"OR:" + $('[name=secondaryFileOids]')[i].value+"\'/>";
        }
    }
    if(!checkFlag){
        alert("선택해");
        return;
    }
    str +=  "<input type='hidden' name='oid' value=\'"+oid+"\'/>";
    str +=  subStr;
    
    document.getElementById("selDownloadForm").innerHTML = str;
    
    var multiDownForm = document.selDownloadForm;
    window.open("" ,"selDownloadForm", 
          "toolbar=no, width=200, height=200, directories=no, status=no,    scrollorbars=no, resizable=no"); 
    multiDownForm.action =selDownUrl; 
    multiDownForm.method="post";
    multiDownForm.target="selDownloadForm";
    multiDownForm.submit();
}

function insertFileLine() {
    var innerRow = fileTable.insertRow();
    innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>"
                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteL0";
            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' size='100%'>";
        }
    }
}

function deleteFileLine() {
    var body = document.getElementById("fileTable");
    if (body.rows.length == 0)
        return;
    var file_checks = document.forms[0].fileSelect;
    if (body.rows.length == 1) {
        body.deleteRow(0);
    } else {
        for ( var i = body.rows.length; i > 0; i--) {
            if (file_checks[i - 1].checked)
                body.deleteRow(i - 1);
        }
    }
}

function selectDqmAfterFunc(rsltArrayObject){
	var code = "";
	var name = "";
	for(var i= 0 ; i < rsltArrayObject.length; i++){
        if(i != 0){
            code += ",";
            name += ",";
        }
        //if(oid == rsltArrayObject[i].OID){
        //    alert("");
        //	return;
        //}
        
        if(rsltArrayObject[i].STATECODE != 'END'){
            alert('<%=messageService.getString("e3ps.message.ket_message", "09051") %><%--종결된 품질문제만 추가 가능합니다.--%>');
            return;
        }
        
        code += rsltArrayObject[i].OID;
        name += rsltArrayObject[i].PROBLEMNO;
    }
	
	
	
    $('[name=duplicateReportCode]').val(code);
    $('[name=duplicateReportName]').val(name);
}

//검토자
window.setUser1 = function(returnValue){
    var infoArr = returnValue[0];
    $('[name=actionUserOid]').val(infoArr[0]);
    $('[name=actionUserName]').val(infoArr[4]);
    
}

/*
var checkCnt = 0;


g_strCustomFocusEventFunction = "fnTestFocusEventFunction";

function fnTestFocusEventFunction(EditNumber, FocusYN)
{
	checkCnt++;
	if(checkCnt==1){
		$("[name='causeCode']").focus(); 
	}
}
*/

</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">    
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <!-- <td>
			                                    <table border="0" cellspacing="0" cellpadding="0">
			                                        <tr>
			                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
			                                                href="javascript:dqm.actionDelete();" class="btn_blue">삭제</a></td>
			                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                                        </tr>
			                                    </table>
			                                </td>
			                                <td width="5">&nbsp;</td> -->
			                                <td>
			                                    <table border="0" cellspacing="0" cellpadding="0">
			                                        <tr>
			                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
			                                                href="javascript:dqm.actionUpdate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
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
                                                            href="javascript:dqm.golocationActionView('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                           </table>
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
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td  class="tab_btm2"></td>
                        </tr>
                    </table>
                    <form name="updateForm" method="post" enctype="multipart/form-data">
		               <input type="hidden" name="oid" value="${dqm.oid}">
		               <input type="hidden" name="raiseOid" value="${dqm.raiseOid}">
		               <input type="hidden" name="actionOid" value="${dqm.actionOid}">
		               <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
		                   <colgroup>
                           <col width="12.5%">
                           <col width="12.5%">
                           <col width="12.5%">
                           <col width="12.5%">
                           <col width="12.5%">
                           <col width="12.5%">
                           <col width="12.5%">
                           <col width="12.5%">
                           </colgroup>
		                   <tr>
	                           <td colspan="4" class="tdblueL">
	                               <%=messageService.getString("e3ps.message.ket_message", "09032") %><%--원인--%><span class="red">*</span>(<input type='checkbox' name='causeCode' value='PT01' ><%=messageService.getString("e3ps.message.ket_message", "01840") %><%--설계--%>
	                              <input type='checkbox' name='causeCode' value='PT02' ><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%>
	                              <input type='checkbox' name='causeCode' value='PT03' ><%=messageService.getString("e3ps.message.ket_message", "09052") %><%--생기--%>
	                              <input type='checkbox' name='causeCode' value='PT04' ><%=messageService.getString("e3ps.message.ket_message", "01769") %><%--생산--%>
	                              <input type='checkbox' name='causeCode' value='PT05' ><%=messageService.getString("e3ps.message.ket_message", "07103") %><%--구매--%>
                                  <input type='checkbox' name='causeCode' value='PT06' ><%=messageService.getString("e3ps.message.ket_message", "03024") %><%--품질--%> )
	                           </td>
	                           <td colspan="4" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03433") %><%--개선 대책--%></td>
	                           </td>
                           </tr>
		                   <tr>
		                       <td colspan="4">
		                           <!-- 이노디터 JS Include Start --> <script type="text/javascript">
		                               // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
		                               // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
		                               // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
		                               var g_arrSetEditorArea = new Array();
		                               g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
		                           </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script> <script type="text/javascript"
		                               src="/plm/portal/innoditor_u/js/customize_ui_half.js"></script> <script type="text/javascript">
		                               // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
		                               // Skin 재정의
		                               //g_nSkinNumber = 0;
		                               // 크기, 높이 재정의
		                               g_nEditorWidth = $("#viewTable").width()  / 2 - 8;
		                               g_nEditorHeight = 400;
		                           </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
		                           <textarea name="causeWebEditor" rows="0" cols="0" style="display: none">${dqm.causeWebEditor}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
		                           <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
		                           value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
		                           <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
		                           <textarea name="causeWebEditorText" rows="0" cols="0" style="display: none">${dqm.causeWebEditorText}</textarea> <!-- Editor Area Container : Start -->
		                           <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
		                       </td>
		                       <td colspan="4">
		                           <!-- 이노디터 JS Include Start --> <script type="text/javascript">
		                               // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
		                               // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
		                               // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
		                               g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER2";// 이노디터를 위치시킬 영역의 ID값 설정
		                           </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script> <script type="text/javascript"
		                               src="/plm/portal/innoditor_u/js/customize_ui_half.js"></script> <script type="text/javascript">
		                               // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
		                               // Skin 재정의
		                               //g_nSkinNumber = 0;
		                               // 크기, 높이 재정의
		                               g_nEditorWidth = $("#viewTable").width() / 2 - 8;
		                               g_nEditorHeight = 400;
		                           </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
		                           <textarea name="improveWebEditor" rows="0" cols="0" style="display: none">${dqm.improveWebEditor}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
		                           <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
		                           value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
		                           <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
		                           <textarea name="improveWebEditorText" rows="0" cols="0" style="display: none">${dqm.improveWebEditorText}</textarea> <!-- Editor Area Container : Start -->
		                           <div id="EDITOR_AREA_CONTAINER2"></div> <!-- Editor Area Container : End -->
		                       </td>
		                   </tr>
		                   <tr>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09039") %><%--중복--%></td>
                               <td class="tdwhiteL" colspan="3">
                                   <select onBlur="fm_jmp" id="duplicateYn" name="duplicateYn" multiple="multiple" onchange="dqm.duplicateYnCheck();" style="width:130px">
                                      <option value="YES">YES</option>
                                      <option value="NO">NO</option>
                                   </select>
                               </td>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09040") %><%--도면출도--%></td>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09041") %><%--금형수정--%></td>
                               <td class="tdblueL">T/O</td>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09042") %><%--신뢰성시험--%></td>
                           </tr>
                           <tr>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09043") %><%--중복문제보고--%></td>
                               <td class="tdwhiteL" colspan="3">
                                   <input type="text" name=duplicateReportName class="txt_fieldRO" style="width:70%" value="${dqm.duplicateReportName}" readonly>
                                   <input type="hidden" name="duplicateReportCode" value="${dqm.duplicateReportCode}">
                                   <a href="javascript:SearchUtil.selectOneDqm('selectDqmAfterFunc');">
                                   <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                   <a href="javascript:CommonUtil.deleteValue('duplicateReportName','duplicateReportCode');">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                               </td>
                               <td class="text-center-border">
                                   <input type="text" name="drawingOutDate" class="txt_field" style="width: 73px" value="${dqm.drawingOutDate}">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('drawingOutDate');" style="cursor: hand;"></td>
                               </td>
                               <td class="text-center-border">
                                   <input type="text" name="moldModifyDate" class="txt_field" style="width: 73px" value="${dqm.moldModifyDate}">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('moldModifyDate');" style="cursor: hand;"></td>
                               </td>
                               <td class="text-center-border">
                                   <input type="text" name="toDate" class="txt_field" style="width: 73px" value="${dqm.toDate}">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('toDate');" style="cursor: hand;"></td>
                               </td>
                               <td class="text-center-border">
                                   <input type="text" name="trustTestDate" class="txt_field" style="width: 73px" value="${dqm.trustTestDate}">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('trustTestDate');" style="cursor: hand;"></td>
                               </td>
                           </tr>
                           <tr>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09033") %><%--검토자--%><span class="red">*</span></td>
                               <td class="tdwhiteL" colspan="3">
                                   <input type="text" name="actionUserName" class="txt_field" style="width: 150px" value="${dqm.actionUserName}">
                                   <input type="hidden" name="actionUserOid" value="${dqm.actionUserOid}"> 
                                   <a href="javascript:SearchUtil.selectOneUser('','','setUser1');">
                                   <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                                   <a href="javascript:CommonUtil.deleteValue('actionUserOid','actionUserName');">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                               </td>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09141") %><%--설계변경반영--%></td>
                               <td class="tdwhiteL" colspan="4">
                                    <select onBlur="fm_jmp" id="designChangeYn" name="designChangeYn" multiple="multiple" style="width:130px">
                                        <option value="YES">YES</option>
                                        <option value="NO">NO</option>
                                    </select>
                               </td>
                           </tr>
                           <tr>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09044") %><%--과거차문제점반영--%></td>
                               <td class="tdwhiteL" colspan="3">
                                   <select onBlur="fm_jmp" id="problemReflectYn" name="problemReflectYn" multiple="multiple" style="width:130px">
                                     <option value="YES">YES</option>
                                     <option value="NO">NO</option>
                                  </select>
                               </td>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09045") %><%--유효성검증 예정--%><span class="red">*</span></td>
                               <td class="tdwhiteL">
                                   <input type="text" name="validationDate" class="txt_field" style="width: 73px" value="${dqm.validationDate}">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('validationDate');" style="cursor: hand;"></td>
                               </td>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02062") %><%--실제완료일--%></td>
                               <td class="tdwhiteL">
                                   <input type="text" name="execEndDate" class="txt_field" style="width: 73px" value="${dqm.execEndDate}">
                                   <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('execEndDate');" style="cursor: hand;"></td>
                               </td>
                           </tr>
                           <tr>
                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09046") %><%--설계반영여부--%></td>
                               <td class="tdwhiteL" colspan="7">
                                  <input type='checkbox' name='designReflect' value='DR01' ><%=messageService.getString("e3ps.message.ket_message", "09053") %><%--설계가이드--%>
                                  <input type='checkbox' name='designReflect' value='DR02' ><%=messageService.getString("e3ps.message.ket_message", "09054") %><%--설계SPEC--%>
                                  <input type='checkbox' name='designReflect' value='DR03' ><%=messageService.getString("e3ps.message.ket_message", "09055") %><%--설계 CHECK SHEET--%>
                                  <input type='checkbox' name='designReflect' value='DR04' ><%=messageService.getString("e3ps.message.ket_message", "09056") %><%--표준화--%>
                                  <input type='checkbox' name='designReflect' value='DR05' ><%=messageService.getString("e3ps.message.ket_message", "09057") %><%--공용화--%>
                               </td>
                           </tr>
                           <tr>
                                <td class="tdblueL">진행사항</td>
                                <td class="tdwhiteL" colspan="7" >
                                    <textarea name="mainSubject" class='txt_field' style="height:70px;width:99%;">${dqm.mainSubject}</textarea>
                                </td>
		                    </tr>
		                    <tr>
		                       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
		                       <td colspan="7" class="tdwhiteL">
		                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
		                               <tr>
		                                   <td class="space5"></td>
		                               </tr>
		                           </table>
		                           <div id="div_scroll3"
		                               style="overflow-x: hidden; overflow-y: auto;"
		                               class="table_border">
		                               <table width="100%" cellpadding="0" cellspacing="0">
		                                   <tr class="headerLock3">
		                                       <td>
		                                           <table border="0" cellpadding="0" cellspacing="0"
		                                               width="100%" style="table-layout: fixed">
		                                               <tr>
		                                                   <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine(); return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
		                                                   <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
		                                               </tr>
		                                           </table>
		                                       </td>
		                                   </tr>
		                                   <table width="100%" cellpadding="0" cellspacing="0"
		                                       style="table-layout: fixed">
		                                       <col width="20">
		                                       <col width="">
		                                       <tbody id="fileTable">
		                                           <c:forEach var="content" items="${secondaryFiles}">
		                                                   <tr>
		                                                       <td class="tdwhiteM">
		                                                           <a href="#" onclick="javascript:$(this).parent().parent().remove();return false;"><b><img src="/plm/portal/images/b-minus.png"></b></a>
		                                                       </td>
		                                                       <td class="tdwhiteL0">
		                                                           <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
		                                                           <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
		                                                           <a href='${content.downURLStr}' target='_blank'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
		                                                       </td>
		                                                   </tr>
		                                               </c:forEach>
		                                       </tbody>
		                                   </table>
		                                   </div>
		                                   <table border="0" cellspacing="0" cellpadding="0" width="100%">
		                                       <tr>
		                                           <td class="space5"></td>
		                                       </tr>
		                                   </table>
		                               </table>
		                          </div>
		                       </td>
		                   </tr>
		               </table>
		           </form>
		           <form id="selDownloadForm" name="selDownloadForm" method="post">
		           </form>
		           <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
           </td>
        </tr>
   </table>