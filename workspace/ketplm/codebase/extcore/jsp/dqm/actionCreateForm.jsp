<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    src="/plm/extcore/js/dqm/dqm.js"></script>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script>

//Jquery
$(document).ready(function(){
	CommonUtil.singleSelect('duplicateYn',100);
	SuggestUtil.bind('USER','actionUserName', 'actionUserOid');
    CommonUtil.singleSelect('problemReflectYn',100);
    CommonUtil.singleSelect('designChangeYn',100);
    //CalendarUtil.dateInputFormat('drawingOutDate');
    //CalendarUtil.dateInputFormat('moldModifyDate');
    //CalendarUtil.dateInputFormat('toDate');
    //CalendarUtil.dateInputFormat('trustTestDate');
});

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
    
    function addDepartment(targetId) {
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
        attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
        if(typeof attacheDept == "undefined" || attacheDept == null) {
            return;
        }

        if ( typeof(attacheDept) != "object" ) {
            var deptSplit = attacheDept.split(",");
            for ( var i=0; i<deptSplit.length-1; i++ ) {
                var param = "command=deptInfo&deptOid=" + deptSplit[i];
                var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;

                if ( targetId == "actionDeptName" ) {
                    callServer(url, acceptDept1);
                }
                else if ( targetId == "devUserDept2" ) {
                    callServer(url, acceptDept2);
                }
                else if ( targetId == "devUserDept3" ) {
                    callServer(url, acceptDept3);
                }
            }
        }
        else {
            var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;

            if ( targetId == "actionDeptName" ) {
                callServer(url, acceptDept1);
            }
            else if ( targetId == "devUserDept2" ) {
                callServer(url, acceptDept2);
            }
            else if ( targetId == "devUserDept3" ) {
                callServer(url, acceptDept3);
            }
        }
    }
    
    function acceptDept1(req) {
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

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "actionDeptCode", "actionDeptName");
    }
    
    function deptMerge(deptOid, deptName, targetId, targetName) {
        if ( $("#"+targetId).val() == "" ) {
            $("#"+targetId).val( deptOid );
            $("#"+targetName).val( deptName );
        }
        else {
            var deptOidArr = $("#"+targetId).val().split(", ");
            var deptNameArr = $("#"+targetName).val().split(", ");
            // 선택된 부서 push
            deptOidArr.push(deptOid);
            deptNameArr.push(deptName);
            // 중복 부서 정리
            deptOidArr = $.unique(deptOidArr.sort());
            deptNameArr = $.unique(deptNameArr.sort());

            $("#"+targetId).val( deptOidArr.join(", ") );
            $("#"+targetName).val( deptNameArr.join(", ") );
        }
    }
    
    function delDepartment(targetId, targetName) {
        $("#" + targetId).val("");
        $("#" + targetName).val("");
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

    function allCheck(checkboxName, isChecked) {
        var checkedList = document.getElementsByName(checkboxName);

        for ( var i = 0; i < checkedList.length; i++) {
            checkedList[i].checked = isChecked;
        }
    }
    
    function changeDate(param){
        var startDate;
        startDate = $("[name="+param+"]").val();
              
        if (startDate != "" ) {
            if ( !dateCheck(startDate,"-") ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>");
                $("[name="+param+"]").val("");
            }
        }
    }
    
    function clearDate(param){
    	$("[name="+param+"]").val("");
    }
    
    function selectDqmAfterFunc(rsltArrayObject){
        var code = "";
        var name = "";
        for(var i= 0 ; i < rsltArrayObject.length; i++){
            if(i != 0){
                code += ",";
                name += ",";
            }
            code += rsltArrayObject[i].OID;
            name += rsltArrayObject[i].PROBLEMNO;
        }
        $('[name=duplicateReportCode]').val(code);
        $('[name=duplicateReportName]').val(name);
    }
    
    function rejectReasonPopup(oid){
        var url = "/plm/ext/dqm/rejectReasonForm.do?oid="+oid;
        
        var rtnVal = window.showModalDialog(url, "rejectReasonPoup", "status=no; scroll=no; dialogWidth=650px; dialogHeight:390px; center:yes");  
        
        //var rtnVal = getOpenWindow2(url,'downloadReasonPopup', 650, 250);
        
        if(rtnVal == "OK"){
            //리프레쉬
            alert('<%=messageService.getString("e3ps.message.ket_message", "09058") %><%--기각 처리되었습니다.--%>');
            try {
                parent.opener.dqm.search();
            }catch(error){}
            parent.location.href = '/plm/ext/dqm/dqmMainForm.do?type=closeToGrid&oid=${dqm.oid}';
        }
    }

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
                                            <td>
			                                    <table border="0" cellspacing="0" cellpadding="0">
			                                        <tr>
			                                            <c:if test="${dqm.pmUserFlag == true}" >
				                                        <td>
				                                            <table border="0" cellspacing="0" cellpadding="0">
				                                                <tr>
				                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
				                                                    <td class="btn_blue"
				                                                        background="/plm/portal/images/btn_bg1.gif">
				                                                            <a href="javascript:rejectReasonPopup('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04101") %><%--기각--%></a>
				                                                           </td>
				                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
				                                                </tr>
				                                            </table>
				                                        </td>
				                                        <td width="5">&nbsp;</td>  
				                                        </c:if>
			                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                                            <td class="btn_blue"
			                                                background="/plm/portal/images/btn_bg1.gif"><a
			                                                href="javascript:dqm.actionCreate();"
			                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
			                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
                    <form name="createForm" method="post" enctype="multipart/form-data"> 
			            <input type="hidden" name="oid" value="${dqm.oid}">       
			            <input type="hidden" name="raiseOid" value="${dqm.raiseOid}">         
			                <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%">
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
			                         <%=messageService.getString("e3ps.message.ket_message", "09032") %><%--원인--%> (<input type='checkbox' name='causeCode' value='PT01' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "01840") %><%--설계--%>
                                    <input type='checkbox' name='causeCode' value='PT02' disabled="disabled" ><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%>
                                    <input type='checkbox' name='causeCode' value='PT03' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "09052") %><%--생기--%>
                                    <input type='checkbox' name='causeCode' value='PT04' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "01769") %><%--생산--%>
                                    <input type='checkbox' name='causeCode' value='PT05' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "07103") %><%--구매--%>
                                    <input type='checkbox' name='causeCode' value='PT06' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "03024") %><%--품질--%> )
			                     </td>
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
			                                src="/plm/portal/innoditor_u/js/customize_ui.js"></script> <script type="text/javascript">
			                                // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
			                                // Skin 재정의
			                                //g_nSkinNumber = 0;
			                                // 크기, 높이 재정의
			                                g_nEditorWidth = $("#createTable").width() / 2;
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
			                                src="/plm/portal/innoditor_u/js/customize_ui.js"></script> <script type="text/javascript">
			                                // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
			                                // Skin 재정의
			                                //g_nSkinNumber = 0;
			                                // 크기, 높이 재정의
			                                g_nEditorWidth = $("#createTable").width() / 2;
			                                g_nEditorHeight = 400;
			                                
			                                g_bCustomize_MenuBar_Display = false;
                                            // 1번 툴바 설정
                                            g_bCustomize_ToolBar1_Display = false;
                                            // 2번 툴바 설정
                                            g_bCustomize_ToolBar2_Display = false;
                                            // 3번 툴바 설정
                                            g_bCustomize_ToolBar3_Display = false;
                                            // 탭바 설정
                                            g_bCustomize_TabBar_Display = false;
                                            // 조회모드
                                            g_nCustomFormEditMode = parseInt('2');
			                            </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
			                            <textarea name="improveWebEditor" rows="0" cols="0" style="display: none"></textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
			                            <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
			                            value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
			                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
			                            <textarea name="improveWebEditorText" rows="0" cols="0" style="display: none"></textarea> <!-- Editor Area Container : Start -->
			                            <div id="EDITOR_AREA_CONTAINER2"></div> <!-- Editor Area Container : End -->
			                        </td>
			                    </tr>
			                    <tr>
			                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09039") %><%--중복--%></td>
                                    <td class="tdwhiteL" colspan="3">
                                        <select onBlur="fm_jmp" id="duplicateYn" name="duplicateYn" multiple="multiple" style="width:130px" disabled="disabled" >
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
	                                   <input type="text" name=duplicateReportName class="txt_fieldRO" style="width:70%" value="" readonly>
	                                   <input type="hidden" name="duplicateReportCode" value="">
	                                   <!-- 
	                                   <a href="javascript:SearchUtil.selectOneDqm('selectDqmAfterFunc');">
	                                   <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	                                   <a href="javascript:CommonUtil.deleteValue('duplicateReportName','duplicateReportCode');">
	                                   <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                                    -->
	                               </td>
                                    <td class="text-center-border">
                                        <input type="text" name="drawingOutDate" class="txt_fieldRO" style="width: 80px" readonly="readonly">
                                        <!-- 
                                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('drawingOutDate');" style="cursor: hand;"></td>
                                        -->
                                    </td>
                                    <td class="text-center-border">
                                        <input type="text" name="moldModifyDate" class="txt_fieldRO" style="width: 80px" readonly="readonly">
                                        <!-- 
                                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('moldModifyDate');" style="cursor: hand;"></td>
                                        -->
                                    </td>
                                    <td class="text-center-border">
                                        <input type="text" name="toDate" class="txt_fieldRO" style="width: 80px" readonly="readonly">
                                        <!-- 
                                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('toDate');" style="cursor: hand;"></td>
                                        -->
                                    </td>
                                    <td class="text-center-border">
                                        <input type="text" name="trustTestDate" class="txt_fieldRO" style="width: 80px" readonly="readonly">
                                        <!-- 
                                        <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('trustTestDate');" style="cursor: hand;"></td>
                                        -->
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09033") %><%--검토자--%><span class="red">*</span></td>
                                    <td class="tdwhiteL" colspan="3">
                                        <input type="text" name="actionUserName" class="txt_field" style="width: 150px">
                                        <input type="hidden" name="actionUserOid"> 
                                        <a href="javascript:SearchUtil.selectOneUser('actionUserOid','actionUserName');">
                                        <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                                        <a href="javascript:CommonUtil.deleteValue('actionUserOid','actionUserName');">
                                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                    </td>
                                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09141") %><%--설계변경반영--%></td>
                                    <td class="tdwhiteL" colspan="4">
                                       <select onBlur="fm_jmp" id="designChangeYn" name="designChangeYn" multiple="multiple" style="width:130px" disabled="disabled">
                                          <option value="YES">YES</option>
                                          <option value="NO">NO</option>
                                       </select>
                                    </td>
                                </tr>
                                <tr>
								    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09044") %><%--과거차문제점반영--%></td>
								    <td class="tdwhiteL" colspan="3">
								        <select onBlur="fm_jmp" id="problemReflectYn" name="problemReflectYn" multiple="multiple" style="width:130px" disabled="disabled">
								          <option value="YES">YES</option>
								          <option value="NO">NO</option>
								       </select>
								    </td>
								    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09045") %><%--유효성검증 예정--%></td>
								    <td class="tdwhiteL" colspan="3">
								        <input type="text" name="validationDate" class="txt_fieldRO" style="width: 80px" readonly="readonly">
								    </td>
								</tr>
								<tr>
								    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09046") %><%--설계반영여부--%></td>
								    <td class="tdwhiteL" colspan="7">
								       <input type='checkbox' name='designReflect' value='DR01' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "09053") %><%--설계가이드--%>
								       <input type='checkbox' name='designReflect' value='DR02' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "09054") %><%--설계SPEC--%>
								       <input type='checkbox' name='designReflect' value='DR03' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "09055") %><%--설계 CHECK SHEET--%>
								       <input type='checkbox' name='designReflect' value='DR04' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "09056") %><%--표준화--%>
								       <input type='checkbox' name='designReflect' value='DR05' disabled="disabled"><%=messageService.getString("e3ps.message.ket_message", "09057") %><%--공용화--%>
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
			                                                    <!-- <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine(); return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></td> -->
			                                                    <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
			                                                </tr>
			                                            </table>
			                                        </td>
			                                    </tr>
			                                    <table width="100%" cellpadding="0" cellspacing="0"
			                                        style="table-layout: fixed">
			                                        <!-- <col width="20"> -->
			                                        <col width="">
			                                        <tbody id="fileTable">
			                                        </tbody>
			                                    </table>
			                                    </div>
			                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
			                                        <tr>
			                                            <td class="space5"></td>
			                                        </tr>
			                                    </table>
			                                </table>
			                        </td>
			                    </tr>
		                    </tr> 
			                </table>
		            </form>
		            <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
            </td>
        </tr>
   </table>
             