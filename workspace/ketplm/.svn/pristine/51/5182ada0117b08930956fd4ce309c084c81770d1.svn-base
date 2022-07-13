<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02090")%><%--양산금형 일정등록--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>

<script type="text/javascript">
<!--
    //첨부파일 관련 스크립트 시작
    function insertFileLine()
    {
        var innerRow = fileTable.insertRow();
        var innerCell;

        var filePath = "filePath";
        var filehtml = "";

        for( var k=0 ; k < 2 ; k++ )
        {
            innerCell = innerRow.insertCell();
            innerCell.height = "23";

            if (k == 0)
            {
                    innerCell.className = "tdwhiteM";
                     innerCell.innerHTML = "<input name='fileSelect' type='checkbox' class='chkbox'>";
            }
            else if (k == 1)
            {
                    innerCell.className = "tdwhiteL0";
                    innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field'  style='width:99%'>";
            }
        }

    }

    function deleteFileLine()
    {

        var body = document.getElementById("fileTable");
        if (body.rows.length == 0) return;
        var file_checks = document.forms[0].fileSelect;
        if (body.rows.length == 1)
        {
            body.deleteRow(0);
        } else
        {
            for (var i = body.rows.length; i > 0; i--)
            {
                if (file_checks[i-1].checked) body.deleteRow(i - 1);
            }
        }
    }

    function allCheck( checkboxName, isChecked )
    {
        var checkedList = document.getElementsByName( checkboxName );

        for( var i=0; i < checkedList.length ; i++ )
        {
            checkedList[i].checked = isChecked;
        }
    }

    function deleteDataLine(formName, tableElementId, checkboxName, allCheckName, listVarName)
      {
        var body = document.getElementById(tableElementId);
        if (body.rows.length == 0) return;
        var formNameStr = "document." + formName + ".";
        var objChecks = eval(formNameStr + checkboxName);
        var objAllChecks = eval(formNameStr + allCheckName);

        var listVal = "";
        var objList;
        if(listVarName != "") {
            objList = eval(formNameStr + listVarName);
            listVal = objList.value;
        }

        if (body.rows.length == 1) {
            if (objChecks.checked || objChecks[0].checked) {
                if(objChecks.checked) {
                    if(listVal == "") {
                        listVal = objChecks.value;
                    } else {
                        listVal += "*" + objChecks.value;
                    }
                } else if(objChecks[0].checked) {
                    if(listVal == "") {
                        listVal = objChecks[0].value;
                    } else {
                        listVal += "*" + objChecks[0].value;
                    }
                }
                body.deleteRow(0);
            }
        } else {
            for (var i = body.rows.length; i > 0; i--) {
                if (objChecks[i-1].checked) {
                    if(listVal == "") {
                        listVal = objChecks[i-1].value;
                    } else {
                        listVal += "*" + objChecks[i-1].value;
                    }
                    body.deleteRow(i - 1);
                }
            }
        }
        if (body.rows.length < 1) {
            objAllChecks.checked = false;
        }
        if(listVarName != "") {
            objList.value = listVal;
        }
    }

    function check()
    {
        var form = document.CreatePlanForm;
        var isChecked = true;
        if( form.die_no.value == '' )
        {
             isChecked = false;
             alert("<%=messageService.getString("e3ps.message.ket_message", "00146") %><%--Die No를 입력하세요--%>");
        }else if( form.type.value == '' )
        {
             isChecked = false;
             alert("<%=messageService.getString("e3ps.message.ket_message", "00972") %><%--구분을 선택해 주십시오--%>");
        }else if( form.reg_basis.value == '' )
        {
             isChecked = false;
             alert('<%=messageService.getString("e3ps.message.ket_message", "01323") %><%--등록근거를 선택하세요--%>');
        }else if( form.ATTRIBUTE1.value == '' )
        {
             isChecked = false;
             alert("<%=messageService.getString("e3ps.message.ket_message", "00841") %><%--고객사4M을 선택하세요--%>");
        }
        return isChecked;
    }

    function save()
    {
        var form = document.CreatePlanForm;

        if( check() )
        {
            form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
            form.submit();
        }

    }

    function cancel()
    {
        document.forms[0].action="/plm/jsp/ecm/SearchMoldPlan.jsp";
        document.forms[0].target="_self";
        document.forms[0].submit();
    }

    function popupPart(type, fncall)
    {
        var url = "/plm/ext/part/base/listPartPopup.do?mode=one&pType="+type+"&fncall="+fncall;
        openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
    }

    function selectDieNo( objArr )
    {
        var trArr;
        if(objArr.length == 0) {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];

            document.forms[0].die_no.value = trArr[1];

            if( trArr[5] != "" && trArr[6] != "" )
            {
                document.forms[0].part_no.value = trArr[5];
                document.forms[0].part_name.value = trArr[6];
            }
        }
    }

    function selectPartNo( objArr )
    {
        var trArr;
        if(objArr.length == 0) {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];

            document.forms[0].part_no.value = trArr[1];
            document.forms[0].part_name.value = trArr[2];

            if( trArr[5] != "" )
            {
                document.forms[0].die_no.value = trArr[5];
            }
        }
    }

    function deletePartNo( flag )
    {
        if( flag == 'D' )
        {
            document.forms[0].die_no.value = '';
        }
        else
        {
            document.forms[0].die_no.value = '';
            document.forms[0].part_no.value = '';
            document.forms[0].part_name.value = '';
        }
    }

    function popupDoc()
    {
        var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog";
        attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=500px; dialogHeight:500px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }
        setDoc(attache);
    }

    function setDoc( objArr )
    {
        var trArr;
        var targetTable = document.getElementById("docTable");

        if(objArr.length == 0)
        {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];
            var tableRows = targetTable.rows.length;
            var newTr = targetTable.insertRow(tableRows);

            //전체선택 checkbox
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "40";
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input name='chkSelectDoc' type='checkbox' value=''>";

            //문서명
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "343";
            newTd.className = "tdwhiteL";
            newTd.innerHTML = trArr[2];
            newTd.innerHTML += "<input type='hidden' name='docOid' value='"+trArr[0]+"'>";

            //작성부서
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "150";
            newTd.className = "tdwhiteM";
            newTd.innerHTML = trArr[4];

            //작성자
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "130";
            newTd.className = "tdwhiteM";
            newTd.innerHTML = trArr[5];
        }
    }

    //부서 검색 팝업  Open
function selectDepartment() {
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

    var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
    var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    callServer(url, acceptDepartment);
}

function acceptDepartment(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_oid = showElements[0].getElementsByTagName("l_oid");

    document.forms[0].prod_dept_name.value = decodeURIComponent(l_name[0].text);
    //document.forms[0].orgOid.value = decodeURIComponent(l_oid[0].text);
}

//생산처 검색 팝업
function popupVendor(){
    var form = document.forms[0];
    if(form.vendor_flag.value == "i") {//사내
        selectInnerDepartment();
    } else {//외주
        selectPartner();
    }
}

//생산처 초기화
function clearVendor(){
    var form = document.forms[0];
    form.vendor_code.value = '';
    form.prodVendorName.value = '';
}

//협력사검색 팝업 Open
function selectPartner() {
    var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
    openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
}

//협력사 검색결과 셋팅
function linkPartner(arr){
    if(arr.length == 0) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
        return;
    }
    var form = document.forms[0];
    form.vendor_code.value = arr[0][0];
    form.vendor_name.value = arr[0][1];
}

//사내생산처 검색 팝업  Open
function selectInnerDepartment() {
    var mode = "single";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode;
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    linkDept(returnValue);
}

//사내생산처 검색결과 셋팅
function linkDept(arr){
    if(arr.length == 0) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01553") %><%--부서를 선택하시기 바랍니다--%>');
        return;
    }
    var form = document.forms[0];
    form.vendor_name.value = arr[0][2];
    form.vendor_code.value = arr[0][1];
}

function deletePlan( objName )
{
    eval( "document.forms[0]."+objName+".value = ''");
    /*var table = document.getElementById("planTable");
    var pos = table.clickedRowIndex;

    var imgobj  = document.getElementsByName("img_"+objName);
    var planObj;
    var actualObj;

    if( objName.indexOf("plan_date") )
    {
        planObj = eval( "document.forms[0]."+objName);
        actualObj = eval( "document.forms[0]."+objName.substring(0, objName.indexOf("plan_date"))+"actual_date" );
    }
    else
    {
        actualObj = eval( "document.forms[0]."+objName);
        planObj = eval( "document.forms[0]."+objName.substring(0, objName.indexOf("plan_date"))+"plan_date" );

    }

    actualObj.value = '';
    planObj.value = '';

    if( planObj.readOnly )
    {
        planObj.readOnly = false;
    }
    else
    {
        planObj.readOnly = true;
    }

    if( actualObj.readOnly )
    {
        actualObj.readOnly = false;
    }
    else
    {
        actualObj.readOnly = true;
    }

    if( planObj.className == "txt_fieldRO" )
    {
        planObj.className = "txt_field";
    }
    else
    {
        planObj.className = "txt_fieldRO";
    }

    if( actualObj.className == "txt_fieldRO" )
    {
        actualObj.className = "txt_field";
    }
    else
    {
        actualObj.className = "txt_fieldRO";
    }

    for( var i=0; i < imgobj.length ; i++ )
    {
        if( imgobj[i].style.display == "none")
        {
            imgobj[i].style.display = "";
        }
        else
        {
            imgobj[i].style.display = "none";
        }
    }*/
}

//ECO담당자검색 팝업
function popupEcoUser( flag ){
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    setEcoUser(attacheMembers, flag );
}

//표준품 담당자 검색 팝업 리턴 포맷
function setEcoUser(objArr, flag ) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];

    if( flag == 'P' )
    {
        form.p_owner_oid.value = trArr[0];
        form.p_owner_name.value = trArr[4];
    }
    else
    {
        form.m_owner_oid.value = trArr[0];
        form.m_owner_name.value = trArr[4];
    }
}

//ECO담당자 초기화
function clearEcoUser( flag ){
    var form = document.forms[0];

    if( flag == 'P' )
    {
        form.p_owner_oid.value = '';
        form.p_owner_name.value ='';
    }
    else
    {
        form.m_owner_oid.value = '';
        form.m_owner_name.value = '';
    }
}

function setDateOnChange(flag, sDate){
    var goFlag;
    if(sDate != ""){
        sDate = sDate.replace(/-/gi,"");
        var year = sDate.substring(0, 4);
        var month = sDate.substring(4, 6);
        var day = sDate.substring(6, 8);
        var newDate = year+"-"+month+"-"+day;
        goFlag = eval( "document.forms[0]."+flag+".value=newDate");
        goFlag;
        if(!pointCheck(goFlag) || !dateCheck(goFlag,'-') ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>');
            eval( "document.forms[0]."+flag+".value=''");
            setTimeout(function(){
                eval( "document.forms[0]."+flag+".focus()");
            });
        }
    }else{
        goFlag;
    }
}

function pointCheck(point){
    var string = point;
    var isChecked = true;
     if(string.substr(4,1) != '-' || string.substr(7,1) != '-'){
         isChecked =  false;
     }
     return isChecked;
}

$(document).ready(function(){
	CalendarUtil.dateInputFormat('basis_date');
	CalendarUtil.dateInputFormat('prod_plan_date');
	CalendarUtil.dateInputFormat('mold_eco_plan_date');
	CalendarUtil.dateInputFormat('store_plan_date');
	CalendarUtil.dateInputFormat('work_plan_date');
	CalendarUtil.dateInputFormat('ass_plan_date');
	CalendarUtil.dateInputFormat('try_plan_date');
	CalendarUtil.dateInputFormat('test_plan_date');
	CalendarUtil.dateInputFormat('approve_plan_date');
	
	//Die no suggest
    SuggestUtil.bind('DIENO', 'die_no');
    //부품 suggest
    SuggestUtil.bind('PARTNO', 'part_no');
    //개발담당부서
    SuggestUtil.bind('DEPARTMENT', 'prod_dept_name', 'devUserDeptOid1');
    //제품ECO 담당
    SuggestUtil.bind('USER', 'p_owner_name', 'p_owner_oid');
    //금형ECO 담당
    SuggestUtil.bind('USER', 'm_owner_name', 'm_owner_oid');
    
	
});
-->
</script>
</head>
<body>
    <form name="CreatePlanForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="cmd" value="Create"> <input type="hidden" name="deleteDocList">
        <table style="width: 100%; height: 100%;" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02090")%><%--양산금형 일정등록--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="170" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>&nbsp;</td>
                            <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td width="5">&nbsp;</td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="90px" class="tdblueL">Die No<span class="red">*</span></td>
                            <td class="tdwhiteL">
                                <input type="text" name="die_no" class="txt_field" style="width: 99%" id="textfield"></td>
                            <td width="80px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569")%><%--부품번호--%></td>
                            <td class="tdwhiteL">
                                <input type="text" name="part_no" class="txt_field" style="width: 99%" id="textfield2"></td>
                            <td width="80px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586")%><%--부품명--%></td>
                            <td class="tdwhiteL0">
                                <input type="text" name="part_name" class="txt_field" style="width: 99%" id="textfield2"></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL"><select name="type" class="fm_jmp" style="width: 97%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <option value="개조"><%=messageService.getString("e3ps.message.ket_message", "00693")%><%--개조--%></option>
                                    <option value="개선"><%=messageService.getString("e3ps.message.ket_message", "00676")%><%--개선--%></option>
                            </select></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00632")%><%--개발담당부서--%></td>
                            <td class="tdwhiteL"><input type="text" name="prod_dept_name" class="txt_field"
                                style="width: 85" id="textfield"> <a href="javascript:selectDepartment()"><img
                                    src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:deletePartNo('D');"><img
                                    src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791")%><%--생산처--%></td>
                            <td class="tdwhiteL0"><select name="vendor_flag" class="fm_jmp" style="width: 50">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <option value="i"><%=messageService.getString("e3ps.message.ket_message", "01655")%><%--사내--%></option>
                                    <option value="o"><%=messageService.getString("e3ps.message.ket_message", "02184")%><%--외주--%></option>
                            </select> <input type="text" name="vendor_name" class="txt_fieldRO" style="width: 95" id="textfield2" readonly> <input
                                type="hidden" name="vendor_code" value=''> <a href="javascript:popupVendor();"><img
                                    src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:deletePartNo('P');"><img
                                    src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01322")%><%--등록근거--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL">
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr valign="top">
                                        <td><select name="reg_basis" class="fm_jmp" style="width: 60">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                                <option value="회의록"><%=messageService.getString("e3ps.message.ket_message", "03251")%><%--회의록--%></option>
                                                <option value="협조전"><%=messageService.getString("e3ps.message.ket_message", "03219")%><%--협조전--%></option>
                                                <option value="기타"><%=messageService.getString("e3ps.message.ket_message", "01136")%><%--기타--%></option>
                                        </select></td>
                                        <td><input type="text" name="basis_date" class="txt_field" style="width: 65" id="textfield16">
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02562")%><%--제품ECO담당--%></td>
                            <td class="tdwhiteL"><input type="text" name="p_owner_name" class="txt_field"
                                style="width: 85" value='' > <input type="hidden" name="p_owner_oid" value=''> <a
                                href="javascript:popupEcoUser('P');" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif"
                                    border="0"></a> <a href="javascript:clearEcoUser('P');" onfocus="this.blur();"><img
                                    src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01033")%><%--금형ECO담당--%>
                            <td class="tdwhiteL0"><input type="text" name="m_owner_name" class="txt_field"
                                style="width: 150" value=''> <input type="hidden" name="m_owner_oid" value=''> <a
                                href="javascript:popupEcoUser('M');" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif"
                                    border="0"></a> <a href="javascript:clearEcoUser('M');" onfocus="this.blur();"><img
                                    src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00840")%><%--고객사4M--%>
                                <span class="red">*</span></td>
                            <td colspan="5" class="tdwhiteL0"><select name="ATTRIBUTE1" class="fm_jmp" style="width: 97%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01946")%><%--수정내용--%></td>
                            <td class="tdwhiteL0" colspan=5><textarea name="modify_desc"
                                    style='overflow: auto; width: 697; height: 50px; padding: 10;' class="fm_area"></textarea></td>
                        </tr>


                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02370")%><%--일정정보--%></td>
                            <td colspan="5" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="670">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table width="100%" cellpadding="0" cellspacing="0" class="table_border" id="planTable">
                                    <tr>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02585")%><%--제품도--%></td>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01085")%><%--금형설계--%></td>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01097")%><%--금형입고--%>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01064")%><%--금형부품--%></td>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01104")%><%--금형조립--%></td>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01037")%><%--금형Try--%></td>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02624")%><%--제품측정--%></td>
                                        <td width="83px" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00737")%><%--검토협의--%></td>
                                    </tr>
                                    <tr onMouseOver='planTable.clickedRowIndex=this.rowIndex'>
                                        <td class="tdwhiteM"><input type="text" name="prod_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('prod_plan_date', this.value);"></td>
                                        <td class="tdwhiteM"><input type="text" name="mold_eco_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('mold_eco_plan_date', this.value);"></td>
                                        <td class="tdwhiteM"><input type="text" name="store_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('store_plan_date', this.value);"></td>
                                        <td class="tdwhiteM"><input type="text" name="work_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('work_plan_date', this.value);"></td>
                                        <td class="tdwhiteM"><input type="text" name="ass_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('ass_plan_date', this.value);"></td>
                                        <td class="tdwhiteM"><input type="text" name="try_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('try_plan_date', this.value);"></td>
                                        <td class="tdwhiteM"><input type="text" name="test_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('test_plan_date', this.value);"></td>
                                        <td class="tdwhiteM"><input type="text" name="approve_plan_date" class="txt_field"
                                            style="width: 78px" onChange="javascript:setDateOnChange('approve_plan_date', this.value);"></td>
                                    </tr>
                                </table>
                            </td>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01632")%><%--비고--%></td>
                            <td colspan="5" class="tdwhiteL0"><textarea name="plan_desc"
                                    style='overflow: auto; width: 697; height: 50px; padding: 10;' class="fm_area"></textarea></td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02124")%><%--연계산출물--%></td>
                            <td colspan="5" class="tdwhiteL0">
                                <table border="0" cellspacing="0" cellpadding="0" width="670">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:popupDoc();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><%--추가--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:deleteDataLine('forms[0]', 'docTable', 'chkSelectDoc', 'chkDocAll', 'deleteDocList');"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="670">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                                    <tr>
                                        <td width="40" class="tdgrayM"><input type="checkbox" name="chkDocAll"
                                            onclick="javascript:allCheck('chkSelectDoc',this.checked);"></td>
                                        <td width="350" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                                        <!-- 문서명 -->
                                        <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                                        <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                    </tr>
                                </table>
                                <div style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                    <table width="100%" cellpadding="0" cellspacing="0" id="docTable">
                                    </table>
                                </div>
                                <table border="0" cellspacing="0" cellpadding="0" width="670">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                            <td colspan="5" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="670">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:insertFileLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:deleteFileLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="670">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                                    <tr>
                                        <td width="40" class="tdgrayM"><input type="checkbox" name="chkFileAll" id="checkbox14"
                                            onclick="javascript:allCheck('fileSelect',this.checked);"></td>
                                        <td width="655" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                                    </tr>
                                </table>
                                <div style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                    <table width="100%" cellpadding="0" cellspacing="0">
                                        <col width=40>
                                        <col width=655>
                                        <tbody id="fileTable"></tbody>
                                    </table>
                                </div>
                                <table border="0" cellspacing="0" cellpadding="0" width="670">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
