<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<script type="text/javascript" src="/plm/extcore/js/dqm/dqm.js?ver=0.2"></script>
<script src="/plm/portal/js/jquery/jquery.form.js"></script>
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
<script type="text/javascript">
$(document).ready(function(){
	
	if ( $('[name=pjtoid]').val() != null &&  $('[name=pjtoid]').val() != "") {
        $.ajax({
            url : "/plm/ext/dqm/searchPjtInfo.do?oid="+$('[name=pjtoid]').val() ,
            type : "POST",
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data, function() {
                    if("true" == this.carTypeRequiredFlag){
                        $('[id=cartypeHeader]').html(LocaleUtil.getMessage('02745')+"<span class='red'>*</span>");
                        dqm.setCarTypeRequire(true);
                    }else{
                        $('[id=cartypeHeader]').html(LocaleUtil.getMessage('02745'));
                        dqm.setCarTypeRequire(false);
                    }
                });
            }
        });
    }
	
    CalendarUtil.dateInputFormat('requestDate');
    CalendarUtil.dateInputFormat('occurDate');
    
	var strHTMLCode = document.forms[0].webEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor.value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage.value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat.value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    var tab = CommonUtil.tabs('tabs');
    tab.tabs({disabled: [1,2]});
    
    SuggestUtil.bind('PRODUCTPARTNO', 'relatedPart', 'relatedPartOid');
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtno', 'pjtoid', 'pjtname');
    SuggestUtil.bind('CARTYPE', 'cartypeName', 'cartypeCode');
    SuggestUtil.bind('SPSERIES', 'seriesName', 'series');
    SuggestUtil.bind('USER','actionUserName', 'actionUserOid');
    SuggestUtil.bind('USER','closerName', 'closerOid');
    //SuggestUtil.bind('USER', 'raiserUserName', 'raiserUserOid');
    
    
    
    CommonUtil.singleSelect('occurCode',100);
    CommonUtil.singleSelect('occurDivCode',100);
    CommonUtil.singleSelect('occurStepCode',100);
    CommonUtil.singleSelect('urgencyCode',100);
    CommonUtil.singleSelect('defectDivCode',100);
    CommonUtil.singleSelect('defectTypeCode',200);
    
    CommonUtil.singleSelect('importanceCode',100);
    CommonUtil.singleSelect('issueCode',100);
    CommonUtil.singleSelect('occurPointCode',200);
    
    dqm.onloadDefectTypeCode('${dqm.defectTypeCode}');
    
    if('${dqm.issueCode}' != 'DQMIS8'){
    	$("#defectDivCode").multiselect('disable');
        $("#defectTypeCode").multiselect('disable');	
    }
    parent.$('#iframetab1').height(900);//크롬에서 에디터 글꼴 설정이 잘 안되기 때문에 사이즈를 늘려줘야한다..
    
})


//첨부파일 관련 스크립트 시작
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

function allCheck(checkboxName, isChecked) {
    var checkedList = document.getElementsByName(checkboxName);

    for ( var i = 0; i < checkedList.length; i++) {
        checkedList[i].checked = isChecked;
    }
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

function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];
        if(trArr[14] != '승인완료' ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "09024")%><%--승인완료된 부품만 추가 가능합니다.--%>');
            return;
        }
        
        $('[name=relatedPartOid]').val(trArr[0]).trigger('change');;
        $('[name=relatedPart]').val(trArr[1]).trigger('change');;
    }
}

//검토자
window.setUser1 = function(returnValue){
    var infoArr = returnValue[0];
    $('[name=actionUserOid]').val(infoArr[0]);
    $('[name=actionUserName]').val(infoArr[4]);
    
}

//종결담당자
window.setUser2 = function(returnValue){
    var infoArr = returnValue[0];
    $('[name=closerOid]').val(infoArr[0]);
    $('[name=closerName]').val(infoArr[4]);
    
}

window.setCarType = function(returnValue){
    
    $('[name=cartypeCode]').val(returnValue[0][0])//id
    $('[name=cartypeName]').val(returnValue[0][1])//name
    
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

function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];
        if(trArr[14] != '승인완료' ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "09024")%><%--승인완료된 부품만 추가 가능합니다.--%>');
            return;
        }
        
        $('[name=relatedPartOid]').val(trArr[0]).trigger('change');
        $('[name=relatedPart]').val(trArr[1]).trigger('change');
    }
}

window.setPartClaz = function(returnValue){
    
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    
    $('[name=partCategoryCode]').val(returnValue[0].id);//oid
    $('[name=partCategoryName]').val(returnValue[0].name);//kr name
    
}

window.setSeries = function(returnValue){
    
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    
    $('[name=series]').val(returnValue[0][1]);//id
    $('[name=seriesName]').val(returnValue[0][2]);//name
    
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
                                            <!-- <td>
			                                    <table border="0" cellspacing="0" cellpadding="0">
			                                        <tr>
			                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
			                                                href="javascript:dqm.remove();" class="btn_blue">삭제</a></td>
			                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                                        </tr>
			                                    </table>
			                                </td>
			                                <td width="5">&nbsp;</td>  -->
			                                <td>
			                                    <table border="0" cellspacing="0" cellpadding="0">
			                                        <tr>
			                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
			                                                href="javascript:dqm.update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
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
                                                            href="javascript:dqm.golocationView('${dqm.oid}');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
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
			                <table id="updateTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed">
			                    <colgroup>
	                               <col width="15%">
	                               <col width="35%">
	                               <col width="15%">
	                               <col width="35%">
	                           </colgroup>
	                           <tr>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09002") %><%--문제내용--%><span class="red">*</span></td>
	                               <td class="tdwhiteL" colspan="3"><input type="text" name="problem" class="txt_field" style="width:100%" value="${dqm.problem}"></td>
	                           </tr>
	                           <tr>
	                               <td class="tdblueL">Project No<span class="red">*</span></td>
	                               <td class="tdwhiteL">
	                                   <input type="text" name="pjtno" class="txt_field" style="width:70%" value="${dqm.pjtno}">
	                                   <input type="hidden" name="pjtoid" value="${dqm.pjtoid}" onchange="dqm.onChangeProjectOid();">
	                                   <a href="javascript:SearchUtil.selectOneProjectPopUp('dqm.setProjectNo','project_type=2');">
		                               <img src="/plm/portal/images/icon_5.png" border="0"></a> 
		                               <a href="javascript:CommonUtil.deleteValue('pjtno','pjtoid');">
		                               <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
		                           </td>
	                               <td class="tdblueL">Project Name</td>
	                               <td class="tdwhiteL"><input type="text" name="pjtname" class="txt_fieldRO" style="width:100%" value="${dqm.pjtname}" readonly="readonly"></td>
	                           </tr>
	                           <tr>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
	                               <td class="tdwhiteL">
	                                   <!-- 
	                                   <ket:select id="customerName" name="customerName" className="fm_jmp" style="width: 170px;" codeType="CUSTOMER" multiple="multiple"/>
	                                    -->
	                                   <input type="text" name="customerName" class="txt_fieldRO" style="width: 70%" value="${dqm.customerName}" readonly>
	                                   <input type="hidden" name="customerCode" value="${dqm.customerCode}">
	                                   <a href="javascript:SearchUtil.selectMultiSubContractorPopUp('selectMultiSubContractor');">
	                                   <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	                                   <a href="javascript:CommonUtil.deleteValue('customerCode','customerName');">
	                                   <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                                   
	                               </td>
	                               <td class="tdblueL" id="cartypeHeader"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
	                               <td class="tdwhiteL">
	                                   <input type="text" name="cartypeName" class="txt_field" style="width: 70%" value="${dqm.cartypeName}"> 
	                                   <input type="hidden" name="cartypeCode" value="${dqm.cartypeCode}">
	                                   <a href="javascript:SearchUtil.selectCarType('','','setCarType');">
	                                   <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	                                   <a href="javascript:CommonUtil.deleteValue('cartypeCode','cartypeName');">
	                                   <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                               </td>
	                           </tr>
	                           
	                           <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04630") %><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--문제점구분--%><span class="red">*</span></td>
                                <td class="tdwhiteL">
                                  <ket:select id="issueCode" name="issueCode" className="fm_jmp" style="width: 170px;" codeType="DQMISSUETYPE" multiple="multiple" useCodeValue="true" onChange="dqm.changeIssueCode();" value="${dqm.issueCode}"/>
                                  <!-- <input type="text" name="occurDivName" class="txt_field" style="width:100%" value=""> -->
                                </td>
                                <td class="tdblueL">발생시점<span class="red">*</span></td>
                                <td class="tdwhiteL">
                                  <ket:select id="occurPointCode" name="occurPointCode" className="fm_jmp" style="width: 170px;" codeType="DQMOCCURPOINT" multiple="multiple" useCodeValue="true" value="${dqm.occurPointCode}"/>
                                  <!-- <input type="text" name="occurStepName" class="txt_field" style="width:100%" value=""> -->
                                </td>
                               </tr>
	                           
	                           <tr>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09003") %><%--불량구분--%></td>
	                               <td class="tdwhiteL">
	                                   <ket:select id="defectDivCode" name="defectDivCode" className="fm_jmp" style="width: 170px;" codeType="PROBLEMDIVTYPE" multiple="multiple" useCodeValue="true" onChange="dqm.changeDefectDivCode();" value="${dqm.defectDivCode}"/>
	                               <!--<input type="text" name="defectDivName" class="txt_field" style="width:100%" value=""></td>
	                                
	                                   defectDivCode
	                                   defectDivName
	                                   defectTypeCode
	                                   defectTypeName
	                                -->
	                               </td>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09004") %><%--불량유형--%></td>
			                       <td class="tdwhiteL">
			                           <select id="defectTypeCode" name="defectTypeCode" class="fm_jmp" multiple="multiple" style="width:250px">
			                           </select>
			                           <!-- <input type="text" name="defectTypeName" class="txt_field" style="width:100%" value=""> -->
			                       </td>
                               </tr>
	                           <tr>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04126") %>/<%=messageService.getString("e3ps.message.ket_message", "02693") %><%--긴급도/중요도--%><span class="red">*</span></td>
	                               <td class="tdwhiteL">
	                                 <ket:select id="urgencyCode" name="urgencyCode" className="fm_jmp" style="width: 170px;" codeType="EMERGENCYPOSITION" multiple="multiple" useCodeValue="true" value="${dqm.urgencyCode}"/>
	                                 <ket:select id="importanceCode" name="importanceCode" className="fm_jmp" style="width: 170px;" codeType="EMERGENCYPOSITION" multiple="multiple" useCodeValue="true" value="${dqm.importanceCode}"/>
	                                 <!-- <input type="text" name="urgencyName" class="txt_field" style="width:100%" value="">  -->
	                               </td>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09025") %><%--적용부위 1--%></td>
	                               <td class="tdwhiteL"><input type="text" name="applyArea1" class="txt_field" style="width:100%" value="${dqm.applyArea1}"></td>
	                           </tr>
	                           <tr>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09026") %><%--적용부위 2--%></td>
	                               <td class="tdwhiteL"><input type="text" name="applyArea2" class="txt_field" style="width:100%" value="${dqm.applyArea2}"></td>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09027") %><%--적용부위 3--%></td>
	                               <td class="tdwhiteL"><input type="text" name="applyArea3" class="txt_field" style="width:100%" value="${dqm.applyArea3}"></td>
	                           </tr>
	                           <tr>
                                   <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%><span class="red">*</span></td>
                                   <td class="tdwhiteL">
                                       <input type="text" name="relatedPart" class="txt_field" style="width: 70%" value="${dqm.relatedPart}">
                                       <input type="hidden" name="relatedPartOid" value="${dqm.relatedPartOid}" onchange="dqm.onChangePartOid();">
                                        <a href="javascript:SearchUtil.selectOnePart('selectPartAfterFunc','pType=P');showProcessing();">
                                        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                        <a href="javascript:CommonUtil.deleteValue('relatedPart','relatedPartOid');">
                                        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                   </td>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "06112") %><%--부품분류--%><span class="red">*</span></td>
	                               <td class="tdwhiteL">
	                                   <input type="text" name="partCategoryName" class="txt_fieldRO" style="width:70%" value="${dqm.partCategoryName}" readonly>
									   <input type="hidden" name="partCategoryCode" value="${dqm.partCategoryCode}">
									   <a href="javascript:SearchUtil.selectOnePartClazPopUp('setPartClaz', 'onlyLeaf=Y');">
									   <img src="/plm/portal/images/icon_5.png" border="0"></a> 
									   <a href="javascript:CommonUtil.deleteValue('partCategoryCode', 'partCategoryName');">
									   <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
								   </td>
	                           </tr>
	                           <tr> 
	                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02004") %><%--시리즈--%></td>
	                                <td class="tdwhiteL">
	                                    <input type="text" name="seriesName" class="txt_field" style="width: 70%" value="<ket:codeValueByCode codeType="SPECSERIES" code="${dqm.series}"/>">
	                                    <input type="hidden" name="series" value="${dqm.series}">
	                                      <a href="javascript:SearchUtil.selectOneSpSeriesPopUp('setSeries');">
	                                      <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	                                      <a href="javascript:CommonUtil.deleteValue('series','seriesName');">
	                                      <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                                </td>
	                                <td class="tdblueL">완료목표일<span class="red">*</span></td>
	                                <td class="tdwhiteL">
	                                    <input type="text" name="requestDate" class="txt_field" style="width: 80px" value="${dqm.requestDate}" onchange="dqm.onChangeRequestDate();">
                                       <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('requestDate');" style="cursor: hand;">
	                                </td>
	                            </tr>
	                           <tr>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07139") %><%--발생구분--%><span class="red">*</span></td>
	                               <td class="tdwhiteL">
	                                 <ket:select id="occurDivCode" name="occurDivCode" className="fm_jmp" style="width: 170px;" codeType="OCCURTYPE" multiple="multiple" useCodeValue="true" value="${dqm.occurDivCode}"/>
	                                 <!-- <input type="text" name="occurDivName" class="txt_field" style="width:100%" value=""> -->
	                               </td>
	                               <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09028") %><%--발생단계--%><span class="red">*</span></td>
	                               <td class="tdwhiteL">
	                                 <ket:select id="occurStepCode" name="occurStepCode" className="fm_jmp" style="width: 170px;" codeType="DQMDEVSTEP" multiple="multiple" useCodeValue="true" value="${dqm.occurStepCode}"/>
	                                 <!-- <input type="text" name="occurStepName" class="txt_field" style="width:100%" value=""> -->
	                               </td>
	                           </tr>
	                           <tr>
                                   <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07137") %><%--발생처--%><span class="red">*</span></td>
	                               <td class="tdwhiteL">
	                                 <ket:select id="occurCode" name="occurCode" className="fm_jmp" style="width: 170px;" codeType="OCCURPLACE" multiple="multiple" onChange="dqm.occurCodeCheck();" useCodeValue="true" value="${dqm.occurCode}"/>
	                                 <!-- <input type="text" name="occurName" class="txt_field" style="width:100%" value="">  -->
	                               </td>
                                   <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07138") %><%--발생일--%><span class="red">*</span></td>
                                   <td class="tdwhiteL">
                                       <input type="text" name="occurDate" class="txt_field" style="width: 80px" value="${dqm.occurDate}">
	                                   <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('occurDate');" style="cursor: hand;">
	                               </td>
                               </tr>
	                           <tr>
	                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09029") %><%--발생장소--%></td>
	                                <td class="tdwhiteL"><input type="text" name="occurPlaceName" class="txt_field" style="width:100%" value="${dqm.occurPlaceName}"></td>
	                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
	                                <td class="tdwhiteL">
	                                    <input type="text" name="actionDeptName" class="txt_field" style="width: 170px;" value="${dqm.actionDeptName}">
                                        <!-- 
	                                    <input type="text" name="raiserUserName" class="txt_field" style="width: 70%" value="${dqm.raiserUserName}">
	                                    <input type="hidden" name="raiserUserOid" value="${dqm.raiserUserOid}"> 
	                                    <a href="javascript:SearchUtil.selectOneUser('raiserUserOid','raiserUserName');">
	                                    <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
	                                    <a href="javascript:CommonUtil.deleteValue('raiserUserOid','raiserUserName');">
	                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                                    -->
	                                </td>
                               </tr>
                               
                               <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09033") %><%--검토자--%><span class="red">*</span></td>
                                <td class="tdwhiteL">
                                    <input type="text" name="actionUserName" class="txt_field" style="width: 150px" value="${dqm.actionUserName}">
                                    <input type="hidden" name="actionUserOid" value="${dqm.actionUserOid}"> 
                                    <a href="javascript:SearchUtil.selectOneUser('','','setUser1');">
                                    <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('actionUserOid','actionUserName');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                </td>
                                
                                <td class="tdblueL">종결담당자<span class="red">*</span></td>
                                <td class="tdwhiteL">
                                    <input type="text" name="closerName" class="txt_field" style="width: 150px" value="${dqm.closerName}">
                                    <input type="hidden" name="closerOid" value="${dqm.closerOid}"> 
                                    <a href="javascript:SearchUtil.selectOneUser('','','setUser2');">
                                    <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('closerOid','closerName');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                </td>
                                
                                </tr>
                               
			                    <tr>
			                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01746") %><%--상세내용--%><span class="red">*</span></td>
			                        <td colspan="3" id="enoediter">
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
			                                g_nEditorWidth = $("#enoediter").width()-20;
			                                g_nEditorHeight = 300;
			                            </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
			                            <textarea name="webEditor" rows="0" cols="0" style="display: none">${dqm.webEditor}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
			                            <input type="hidden" name="hdnBackgroundColor" value="" /><input type="hidden" name="hdnBackgroundImage"
			                            value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
			                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
			                            <textarea name="webEditorText" rows="0" cols="0" style="display: none">${dqm.webEditorText}</textarea> <!-- Editor Area Container : Start -->
			                            <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
			                        </td>
			                    </tr>
			                    <tr>
			                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
			                        <td colspan="3" class="tdwhiteL">
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
			            <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
                    </td>
            </tr>
        </table>

