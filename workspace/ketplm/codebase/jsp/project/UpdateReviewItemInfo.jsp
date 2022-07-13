<%@page import="e3ps.ews.dao.PartnerDao"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.groupware.company.*"%>
<%@page import="e3ps.common.jdf.config.Config"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.project.E3PSProjectHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.ProductInfo"%>
<%@page import="e3ps.project.beans.ProductHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

    String oid = request.getParameter("oid");
    String cmd = request.getParameter("cmd");
    if(oid == null){ oid = ""; }
    if(cmd == null){ cmd = ""; }
    int pjtType = 1;

    E3PSProject project = null;
    E3PSProjectData projectData = null;
    ReviewProject rproject = null;
    if(oid != null){
        rproject =(ReviewProject)CommonUtil.getObject(oid);
        project = (E3PSProject)CommonUtil.getObject(oid);
        projectData = new E3PSProjectData(project);
        if(rproject.getAttr1().equals("자동차")){
            pjtType = 1;
        }else{
            pjtType = 0;
        }
    }
    String projectTypeValue = "";
    if(rproject.getAttr1().equals("자동차사업부")){
        projectTypeValue = "자동차";
    }else if(rproject.getAttr1().equals("자동차")){
        projectTypeValue = "자동차";
    }
    if(rproject.getAttr1().equals("전자사업부")){
        projectTypeValue = "전자";
    }else if(rproject.getAttr1().equals("전자")){
        projectTypeValue = "전자";
    }

    Vector tMap = null;

    WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
    PeopleData pd = new PeopleData(sessionUser);

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE><%=messageService.getString("e3ps.message.ket_message", "07123") %><%--제품정보 수정--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
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
<%@include file="/jsp/common/multicombo.jsp"%>
<script language="JavaScript">

    function update(){
        var form = document.forms[0];

        var rowId =  getParamValue("rowId");
        if(rowId.length > 0) {

            var paramObj = document.all.item("pName");

            if( paramObj != null ){
                var paramLen = paramObj.length;
                if(paramLen) {
                    for(var i = 0; i < paramLen; i++) {
                        if(paramObj[i].value == "") {
                            alert('<%=messageService.getString("e3ps.message.ket_message", "02614") %><%--제품정보의 제품명은 반드시 입력해야 합니다--%>');
                            paramObj[i].focus();
                            return;
                        }
                    }
                }else {
                    if(paramObj.value == "") {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02614") %><%--제품정보의 제품명은 반드시 입력해야 합니다--%>');
                        paramObj.focus();
                        return;
                    }
                }
            }

            var paramObj2 = document.all.item("pNum");
            if( paramObj2 != null){
                var paramLen2 = paramObj2.length;
                if(paramLen2) {
                    for(var i = 0; i < paramLen2; i++) {
                        if(paramObj2[i].value == "") {
                            //alert("제품정보의 품번을 입력하시기 바랍니다.");
                            //paramObj2[i].focus();
                            //return false;
                        }
                    }
                }else {
                    if(paramObj2.value == "") {
                        //alert("제품정보의 품번을 입력하시기 바랍니다.");
                        //paramObj2.focus();
                        //return;
                    }
                }
            }
            var paramObj3 = document.all.item("rowId");
            var paramLen3 = paramObj3.length;
            if(paramLen3) {
                for(var i = 0; i < paramLen3; i++) {
                    if(paramObj3[i].value != "") {
                        var optObj = document.all.item("optOid"+paramObj3[i].value);
                        var pOidObj = document.all.item("pOid"+paramObj3[i].value);
                        if(optObj==null && pOidObj==null) {
                            alert('<%=messageService.getString("e3ps.message.ket_message", "02617") %><%--제품정보의 차종을 선택하시기 바랍니다--%>');
                            return;
                        }
                    }
                }
            }else {
                if(paramObj3.value != "") {
                    var optObj = document.all.item("optOid"+paramObj3.value);
                    var pOidObj = document.all.item("pOid"+paramObj3.value);
                    if(optObj==null && pOidObj==null) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02617") %><%--제품정보의 차종을 선택하시기 바랍니다--%>');
                        return;
                    }
                }
            }
        }else{
        	return;
        }
        
        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>')) {
            return;
        }
        
        if(afterProductRowCount != $('[name=pName]').length){
            var url = "/plm/jsp/project/ProductChangeHistory.jsp?isModal=&oid="+$('[name=oid]').val()+"&invokeMethod=ProductInfoUpdate";
            window.open(url,'ProductInfoUpdate',"top=100px, left=100px, height=230px, width=500px");
        }
        
    }
    
    function ProductInfoUpdate(attachedValue){
    	if(attachedValue != 'OK'){
            return;
        }
    	showProcessing();
        //document.forms[0].action = "/plm/servlet/e3ps.project.servlet.ProjectServlet";
        //document.forms[0].command.value = "ProductInfoUpdate";
        document.forms[0].method = "post";
        //document.forms[0].submit();
        $.ajax( {
            url : "/plm/jsp/project/ActionProject.jsp?command=ProductInfoUpdate",
            type : "POST",
            data : $('[name=prodForm]').serialize(),
            async : false,
            success: function(data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다. --%>');
                opener.location.href='/plm/jsp/project/ReviewProjectView2.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
                self.close();
            },
            fail : function(){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다. --%>');
                hideProcessing();
            }
        });
    }
    
    function closeForm() {
        self.close();
    }
    //*********************************************************************************************************************************************************
    //프로젝트 속성  가져오기 시작
    function addProcess(type, depth) {
        var form = document.forms[0];

        var mode = "one";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        acceptProcess(returnValue, type);
    }

    function acceptProcess(arrObj, type){
        var subArr;
        var form = document.forms[0];

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];

                if(type==("DEVELOPENTTYPE")) {
                    form.developenttype.value = subArr[1];
                    form.developenttype.name =subArr[2];
                } else if(type==("PRODUCTTYPE")) {
                    form.producttype.value = subArr[1];
                    form.producttype.name =subArr[2];
                }else if(type==("ASSEMBLEDTYPE")) {
                    form.assembledtype.value = subArr[1];
                    form.assembledtype.name =subArr[2];
                }
            }
    }

    // 멀티 속성 가져오기
    function addProcessM(type, depth, viewType) {
        var form = document.forms[0];
        var mode = "multi";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode+"&viewType="+viewType;

        if(viewType == 'noTree')
            returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
        else
            returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=860px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        if(type == "SUBCONTRACTOR"){
            acceptProcessM2(returnValue, type);
        }else{
            acceptProcessM(returnValue, type);
        }
    }
    //etc
    function acceptProcessM(arrObj, type){
        var subArr;
        var form = document.forms[0];


        var targetTable = document.getElementById(type);
        var subArr;
        //var chkTag = document.all.item("processoid");
        var form = document.forms[0];

        //targetTable.deleteRow(1);

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];
                if(isDuplicateCheckBox(type+'Oid',subArr[0])) {
                    continue;
                }
                tableRows = targetTable.rows.length;
                newTr = targetTable.insertRow(tableRows);

                //Code | Name
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL";
                newTd.innerText = subArr[2];

                //삭제
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM0";
                newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+type+"', '"+type+"Oid', '" + subArr[0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+type+"Oid' value='" + subArr[0] + "'>";
            }

    }
    //SUBCONTRACTOR
    function acceptProcessM2(arrObj, type){
        var subArr;
        var form = document.forms[0];


        var targetTable = document.getElementById(type);
        var subArr;
        //var chkTag = document.all.item("processoid");
        var form = document.forms[0];

        //targetTable.deleteRow(1);

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];
                if(isDuplicateCheckBox('uOid', subArr[0])) {
                    continue;
                }
                //alert(subArr[0]);
                tableRows = targetTable.rows.length;
                newTr = targetTable.insertRow(tableRows);

                //checkbox
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.width = 20;
                newTd.valign = "top";
                newTd.innerHTML = "<input type=\"checkbox\" name=\"uOid\" value=\"" + subArr[0] + "\"></input><input type='hidden' name='sOid' value='" + subArr[0] + "'>";

                //Code | Name
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL";
                newTd.width = 110;
                newTd.height = "100%";
                newTd.valign = "top";
                newTd.innerText = subArr[2];

                //납입처 Table
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL";
                newTd.innerHTML = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" id=\"" + subArr[0] + "\"></table>";
            }

    }
    //고객처
    function addProcessM2(type, depth, viewType) {
        var form = document.forms[0];
        var mode = "multi";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode+"&viewType="+viewType;

        if(viewType == 'noTree')
            returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
        else
            returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=860px; dialogHeight:500px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }

        var tableName = "";

        len = form.uOid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.uOid[i].checked == true) {
                    tableName = form.uOid[i].value;
                    //alert(tableName);
                }
            }
        }else{
            tableName = form.uOid.value;
        }
        //alert(tableName);
        acceptProcessM3(returnValue, tableName);
    }
    //납입처
    function acceptProcessM3(arrObj, type){
        var subArr;
        var form = document.forms[0];


        var targetTable = document.getElementById(type);
        var subArr;
        //var chkTag = document.all.item("processoid");
        var form = document.forms[0];

        //targetTable.deleteRow(1);

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];
                if(isDuplicateCheckBox(type+'Oid', subArr[0])) {
                    continue;
                }
                tableRows = targetTable.rows.length;
                newTr = targetTable.insertRow(tableRows);

                //Code | Name
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteL";
                newTd.innerText = subArr[2];
                //alert(type + "@" + subArr[0]);
                //삭제
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM0";
                newTd.width = "20";
                newTd.innerHTML = "<input type=\"hidden\" name=\"nOid\" value=\"" + type + "@" + subArr[0] + "\"><a href=\"#\" onClick=\"javascript:onDeleteTableRow0('"+type+"', '"+type+"Oid', '" + subArr[0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+type+"Oid' value='" + subArr[0] + "'>";
            }

    }
    // 개발 검토 납입처 관련 삭제
    function onDeleteTableRow0(tableid, chk_name, chk_value) {
        var targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                //alert(chkTag[i].value +" : "+chk_value);
                if(chkTag[i].value == chk_value) {
                    targetTable.deleteRow(i);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                targetTable.deleteRow(0);
                return;
            }
        }
    }
    // 개발 검토 납입처 관련 삭제
    function onDelete(){
        if(!checkedCheck()){
            alert("<%=messageService.getString("e3ps.message.ket_message", "01698") %><%--삭제 할 고객사를 선택 하십시오--%> ");
            return;
        }

        form = document.forms[0];

        len = form.uOid.length;
        //alert(len);
        if(len) {
            for(var i = len - 1; i >= 0; i--) {
                if(form.uOid[i].checked == true) {
                    onDeleteTableRow('SUBCONTRACTOR', 'uOid', form.uOid[i].value);
                }
            }
        }else{
            onDeleteTableRow('SUBCONTRACTOR', 'uOid', form.uOid.value);
        }
    }
    // 개발 검토 납입처 관련 추가
    function onAdd(){
        if(!checkedCheck2()){
            return;
        }

        addProcessM2('SAPSUBCONTRACTOR', '0', 'noTree');
    }

    function checkedCheck() {
        form = document.forms[0];
        if(form.uOid == null) {
            return false;
        }

        len = form.uOid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.uOid[i].checked == true) {
                    return true;
                }
            }
        }else {
            if(form.uOid.checked == true) {
                return true;
            }
        }

        return false;

    }

    function checkedCheck2() {

        form = document.forms[0];
        if(form.uOid == null) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00843") %><%--고객사를 등록 하십시오--%> ");
            return false;
        }

        len = form.uOid.length;
        if(len) {
            cnt = 0;
            for(var i = 0; i < len;i++) {
                if(form.uOid[i].checked == true) {
                    cnt++;
                }
            }
            if(cnt == 1){
                return true;
            }else{
                alert('<%=messageService.getString("e3ps.message.ket_message", "03137") %><%--하나의 고객사를 선택 하십시오--%>');
                return false;
            }
        }else {
            if(form.uOid.checked == true) {
                return true;
            }
        }
        alert('<%=messageService.getString("e3ps.message.ket_message", "01170") %><%--납입처를 등록 할 고객사를 선택 하십시오--%>');

        return false;

    }



    //프로젝트 속성  가져오기 끝
    //*****************************************************************************************************************************************************************

    //프로젝트 속성  가져오기 끝
    //*****************************************************************************************************************************************************************
    /*
        onDeleteTableRow(tableid, chk_name, chk_value)
        tableid : 테이블 아이디
        chk_name : item name
        chk_value : item value

    */
    function isDuplicateCheckBox(chk_name, chk_value) {
        var chkTag = document.all.item(chk_name);//eval("document.forms[0]." + membertag);
        if(chkTag == null || chkTag == 'undefined') {
            return false;
        }

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    return true;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                return true;
            }
        }

        return false;
    }

    function onDeleteTableRow(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    targetTable.deleteRow(i+1);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                targetTable.deleteRow(1);
                return;
            }
        }
    }
    function onDeleteTableRow2(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    pOidObj = document.all.item("pOid"+chk_value);
                    if(pOidObj) {
                        if(pOidObj.value != "")
                            document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
                    }
                    targetTable.deleteRow(i+1);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                pOidObj = document.all.item("pOid"+chk_value);
                if(pOidObj) {
                    if(pOidObj.value != "")
                        document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
                }
                targetTable.deleteRow(1);
                return;
            }
        }
    }

    function addRoleMember(rname) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if(typeof attacheMembers == "undefined" || attacheMembers == null) {
            return;
        }
        acceptRoleMember(rname,attacheMembers);
    }

    function acceptRoleMember(rname, objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById("temp" + rname);
        var keyName = document.getElementById(rname);
        var DeptName = document.getElementById(rname+"Dept");
        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];
            DeptName.value = infoArr[5];
        }
    }
    function addRoleMember2(rname) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if(typeof attacheMembers == "undefined" || attacheMembers == null) {
            return;
        }
        acceptRoleMember(rname,attacheMembers);
    }

    function acceptRoleMember2(rname, objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById("temp" + rname);
        var keyName = document.getElementById(rname);
        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];

        }
    }

    function deleteRoleMember(rname) {
        document.getElementById("temp" + rname).value = "";
        document.getElementById(rname).value = "";
    }
    function deleteRoleMember2(rname) {
        document.getElementById("temp" + rname).value = "";
        document.getElementById(rname).value = "";
        document.getElementById(rname+"Dept").value = "";
    }
    function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
    }

     //사용자 가져오기 시작 ........................................................................................
    //............................................................................................................
    function addMember(tableid, membertag) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if(typeof attacheMembers == "undefined" || attacheMembers == null) {
            return;
        }
        acceptMember(tableid, membertag, attacheMembers);
    }

    function acceptMember(tableid, membertag, objArr) {
        if(objArr.length == 0) {
            return;
        }

        /*
            subArr[0] = chkobj[i].value;//wtuser oid
            subArr[1] = chkobj[i].poid;//people oid
            subArr[2] = chkobj[i].doid;//dept oid
            subArr[3] = chkobj[i].uid;//uid
            subArr[4] = chkobj[i].sname;//name
            subArr[5] = chkobj[i].dname;//dept name
            subArr[6] = chkobj[i].duty;//duty
            subArr[7] = chkobj[i].dutycode;//duty code
            subArr[8] = chkobj[i].email;//email
        */

        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            if(isDuplicateCheckBox(membertag, infoArr[0])) {
                continue;
            }

            nonUserArr[nonUserArr.length] = infoArr;
        }

        if(nonUserArr.length == 0) {
            return;
        }

        var targetTable = document.getElementById(tableid);

        for(var i = 0; i < nonUserArr.length; i++) {
            tableRows = targetTable.rows.length;

            infoArr = nonUserArr[i];
            newTr = targetTable.insertRow(tableRows);

            //이름
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = infoArr[4];

            //직위
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = infoArr[6];

            //부서
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerText = infoArr[5];

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+tableid+"','"+membertag+"','" + infoArr[0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+membertag+"' value='" + infoArr[0] + "'>";
        }

    }

     //사용자 가져오기 끝 ........................................................................................

    function onDeleteMember(){
        document.forms[0].devUserDept.value = "";
        document.forms[0].devDeptOid.value = "";
    }


    function addDepartment1() {
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
        attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
        if(typeof attacheDept == "undefined" || attacheDept == null) {
            return;
        }

        var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
        var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
        callServer(url, acceptDept1);
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

        document.forms[0].salesDept.value = decodeURIComponent(l_name[0].text);
        document.forms[0].salesDeptOid.value = decodeURIComponent(l_oid[0].text);

    }

    function addDepartment2() {
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
        attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
        if(typeof attacheDept == "undefined" || attacheDept == null) {
            return;
        }

        var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
        var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
        callServer(url, acceptDept2);
    }

    function acceptDept2(req) {
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

        document.forms[0].devUserDept.value = decodeURIComponent(l_name[0].text);
        document.forms[0].devDeptOid.value = decodeURIComponent(l_oid[0].text);
    }
    //
    function checkAll() {
        form = document.forms[0];
        if(form.uOid == null) {
            return;
        }

        len = form.uOid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                form.uOid[i].checked = form.chkAll.checked;
            }
        }
        else {
            form.uOid.checked = form.chkAll.checked;
        }
    }
    function getParamValue(name_str) {
        var paramObj = document.all.item(name_str);

        if(paramObj == null || paramObj == 'undefined') {
            return "";
        }

        var rtnParam = "";
        var paramLen = paramObj.length;
        if(paramLen) {
            for(var i = 0; i < paramLen; i++) {
                if(rtnParam.length > 0) {
                    rtnParam += "&";
                }
                rtnParam += name_str + "=" + paramObj[i].value;
            }
        }else {
            rtnParam += name_str + "=" + paramObj.value;
        }

        return rtnParam;
    }
    
    var programRowId = "";

///////////////////////////////////////////
///////////자동차일정 선택////////////////
///////////////////////////////////////////
        function selectProgram( rowId ){
    	programRowId = rowId;
        var param ="?sid="+rowId;
        //자동차일정 oid

        var optOid =  getParamValue("optOid"+rowId);
        if(optOid.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += optOid;
        }
        var carName =  getParamValue("carName"+rowId);
        if(carName.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += carName;
        }
        //y1
        var y1 =  getParamValue("y1"+rowId);
        if(y1.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y1;
        }
        //y2
        var y2 =  getParamValue("y2"+rowId);
        if(y2.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y2;
        }
        //y3
        var y3 =  getParamValue("y3"+rowId);
        if(y3.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y3;
        }
        //y4
        var y4 =  getParamValue("y4"+rowId);
        if(y4.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y4;
        }
        //y5
        var y5 =  getParamValue("y5"+rowId);
        if(y5.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y5;
        }
        //y6
        var y6 =  getParamValue("y6"+rowId);
        if(y6.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y6;
        }
        //y7
        var y7 =  getParamValue("y7"+rowId);
        if(y7.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y7;
        }
        //y8
        var y8 =  getParamValue("y8"+rowId);
        if(y8.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y8;
        }
        //y9
        var y9 =  getParamValue("y9"+rowId);
        if(y9.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y9;
        }
        //y10
        var y10 =  getParamValue("y10"+rowId);
        if(y10.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += y10;
        }

        //usage
        var usage =  getParamValue("usage"+rowId);
        if(usage.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += usage;
        }

        //optionRate
        var optionRate =  getParamValue("optionRate"+rowId);
        if(optionRate.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += optionRate;
        }

        var pOid =  getParamValue("pOid"+rowId);
        if(pOid.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += pOid;
        }

        var url = "/plm/jsp/project/SelectProgram.jsp"+param;
        var url = "/plm/jsp/project/SelectProgram.jsp"+param+"&invokeMethod=addProgram";
        window.open(url,'',"top=100px, left=100px, height=500px, width=720px");


    }

    function addProgram(attache)
    {
    	
    	
    	if(typeof attache == "undefined" || attache == null) {
            return;
        }
    	
        if(attache.length == 0) {
            return;
        }
        
        rowId = programRowId;
        

        var productInfo = document.getElementById("productInfo");
//    productInfo.deleteRow(tablerows);
//    tableRows = productInfo.rows.length;

        var carName = document.getElementById("carName"+rowId);
        carName.title = attache[0][0];
        carName.innerHTML  = attache[0][0]+"<input type='hidden' name='carName' id='carName"+rowId+"' value='"+attache[0][0]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y1T' id='y1T"+rowId+"' value='"+attache[0][1]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y2T' id='y2T"+rowId+"' value='"+attache[0][2]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y3T' id='y3T"+rowId+"' value='"+attache[0][3]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y4T' id='y4T"+rowId+"' value='"+attache[0][4]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y5T' id='y5T"+rowId+"' value='"+attache[0][5]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y6T' id='y6T"+rowId+"' value='"+attache[0][6]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y7T' id='y7T"+rowId+"' value='"+attache[0][7]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y8T' id='y8T"+rowId+"' value='"+attache[0][8]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y9T' id='y9T"+rowId+"' value='"+attache[0][9]+"'>";
        carName.innerHTML  += "<input type='hidden' name='y10T' id='y10T"+rowId+"' value='"+attache[0][10]+"'>";

        var usageT = document.getElementById("usageT"+rowId);
        usageT.innerHTML  = attache[0][12]+"<input type='hidden' name='usageT' value='"+attache[0][12]+"'>";
        for(var i = 0; i < attache.length-1; i++) {
            infoArr = attache[i+1];
            usageT.innerHTML  += "<input type='hidden' name='optOid"+rowId+"' id='optOid"+rowId+"' value='"+infoArr[0]+"'>";
            //<input type='hidden' name='pOid"+rowId+"' id='pOid"+rowId+"' value=''>";
            usageT.innerHTML  += "<input type='hidden' name='y1"+rowId+"' id='y1"+rowId+"' value='"+infoArr[2]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y2"+rowId+"' id='y2"+rowId+"' value='"+infoArr[3]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y3"+rowId+"' id='y3"+rowId+"' value='"+infoArr[4]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y4"+rowId+"' id='y4"+rowId+"' value='"+infoArr[5]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y5"+rowId+"' id='y5"+rowId+"' value='"+infoArr[6]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y6"+rowId+"' id='y6"+rowId+"' value='"+infoArr[7]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y7"+rowId+"' id='y7"+rowId+"' value='"+infoArr[8]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y8"+rowId+"' id='y8"+rowId+"' value='"+infoArr[9]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y9"+rowId+"' id='y9"+rowId+"' value='"+infoArr[10]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='y10"+rowId+"' id='y10"+rowId+"' value='"+infoArr[11]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='sum' id='sum"+rowId+"' value='"+infoArr[12]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='usage"+rowId+"' id='usage"+rowId+"' value='"+infoArr[13]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='optionRate"+rowId+"' id='optionRate"+rowId+"' value='"+infoArr[14]+"'>";
            usageT.innerHTML  += "<input type='hidden' name='count"+rowId+"' id='count"+rowId+"' value='"+ i +"'>";

        }
        
        var paramObj = document.all.item("pOid"+rowId);
        if(paramObj) {
            document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + paramObj.value;
            //paramObj.value = "";
        }
    }

    function createProduct(oid){
        var tBody = document.getElementById("productInfo");

        var rowId = genRowId();

        tableRows = tBody.rows.length;
        newTr = tBody.insertRow(tableRows);
        newTr.id = rowId;

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "30";
        newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:onDeleteTableRow2('productInfo','rowId','" + rowId + "');\"><img src='/plm/portal/images/b-minus.png' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
        
        //제품번호
        /* newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "90";
        newTd.innerHTML = "<input type='text' name='pNum' class='txt_field'  style='width:95%' id='pNum"+rowId+"'>"; */

        //제품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "150";
        newTd.innerHTML = "<input type='text' name='pName' class='txt_field'  style='width:95%' id='pName"+rowId+"'>";

        //차종
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "150";
        newTd.innerHTML = "<div style='width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><span name='carName' id='carName"+rowId+"'></span></nobr>&nbsp;<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
        
      //조립처
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        var selectStr = "<select id=\"assemblyPlaceType" + rowId + "\" name=\"assemblyPlaceType\" style=\"width:70px\" class=\"fm_jmp\" onChange=\"javascript:changeAssembly(" + rowId + ")\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"사내\"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"외주\"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option></select> ";
        selectStr += "<input id=\"assemblyPlaceName" + rowId + "\" type=text name=\"assemblyPlaceName\" style=\"width:90px\" class=\"txt_fieldRO\" readonly>";
        selectStr += "<input id=\"assemblyPlace" + rowId + "\" type=hidden name=\"assemblyPlace\">";
        selectStr += " <a href='javascript:selectAssemplyPlace("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
        newTd.innerHTML = selectStr;
        
        //조립구분
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        selectStr = "<select name=\"assembledType\" class=\"fm_jmp\" style=\"width: 90%\">";
        selectStr += "<option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>";
        <%
            parameter.clear();
            parameter.put("locale", messageService.getLocale());
            parameter.put("codeType", "ASSEMBLEDTYPE");
            numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

            for (int i = 0; i < numCode.size(); i++) {
        %>
        selectStr += "<option value=\"<%=numCode.get(i).get("oid")%>\"><%=numCode.get(i).get("value")%></option>";
        <%
            }
        %>
        selectStr += "</select>";
        newTd.innerHTML = selectStr;
        
        //usage
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<span name='usageT' id='usageT"+rowId+"' style='width:75%'></span>&nbsp;";
    }
    
    function changeAssembly(rowIndex) {
        $('#assemblyPlaceName'+rowIndex).val('');
        $('#assemblyPlace'+rowIndex).val('');
    }
    
    var selectedRowId;
    
    function setAssemplyPlace(attache){
        if(typeof attache == "undefined" || attache == null) {
            return;
        }
        $('#assemblyPlaceName'+selectedRowId).val(attache[0][2]);
        $('#assemblyPlace'+selectedRowId).val(attache[0][0]);
    }
    
    function selectAssemplyPlace(rname) {
    	selectedRowId = rname;
        if($('#assemblyPlaceType'+rname).val() == '사내'){
            //
            var mode = "single";
            var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode+"&viewType=noTree";
            
            window.open(url+"&invokeMethod=setAssemplyPlace", "MultiAssemplyPlace", "top=100px, left=100px, height=500px, width=680px");
            
        }else if($('#assemblyPlaceType'+rname).val() == '외주'){
            //협력사검색 팝업 Open
            var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=setPartner&modal=N";
            openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
            
        }
    }
    
    function setPartner(arr){       
        $('#assemblyPlaceName'+selectedRowId).val(arr[0][1]);
        $('#assemblyPlace'+selectedRowId).val(arr[0][0]);
    }


    function sleep(num){
      var now = new Date();
      var stop = now.getTime() + num;
      while(true){
        now = new Date();
        if(now.getTime() > stop) { return; }
      }
    }

    function genRowId(){
        sleep(1);
        return (new Date()).getTime();
    }

    function checkNumber(str) {
        var flag=true;
        if (str.length > 0) {
            for (i = 0; i < str.length; i++) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    flag=false;
                }
            }
        }
        return flag;
    }

    function checkNumber2(str) {
        var flag=true;
        if (str.length > 0) {
            for (i = 0; i < str.length; i++) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    flag=false;
                }
            }
        }
        if(str == "0"){
              flag=false;
        }
        return flag;
    }

    function requestPop(oid){
        var url = '/plm/jsp/dms/ViewDevRequestPop.jsp?oid='+oid;
        window.open(url,'의뢰서',"status=1, menu=no, width=830, height=800");
    }
  //제품 변경 사유 등록
  function pchangeCreate(oid){
          var url = "/plm/jsp/project/ProductChangeHistory.jsp?oid="+oid;
        openSameName(url,"500","300","status=no,scrollbars=no,resizable=no");

  }
  //제품 변경 사유 보기
  function pchangeView(oid){
          var url = "/plm/jsp/project/ProductChangeHistoryView.jsp?oid="+oid;
        openSameName(url,"520","500","status=no,scrollbars=yes,resizable=no");

  }
</script>
</head>
<body>
    <%@include file="/jsp/common/processing.html"%>
    <%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form name="prodForm">
        <!-- Hidden Values -->
        <%
            String command = request.getParameter("command");
        %>
        <input type="hidden" name="cmd"> 
        <input type="hidden" name="oid" value="<%=oid%>"> 
        <input type="hidden" name="deletePOid" value="">
        <!-- //Hidden Vlaues -->
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td valign="top" style="padding: 0px 0px 0px 0px">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td background="/plm/portal/images/logo_popupbg.png">
                                            <table height="28" border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07123") %><%--제품정보 수정--%></td>
                                                    <td width="10"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space10"></td>
            </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></td>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                href="javascript:update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            <td width="5">&nbsp;</td>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;"
                                onClick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="productInfo">
            <tr>
                <td width="30px" class="tdblueM">
                    <a href="javascript:createProduct('<%=oid%>');"><img src="/plm/portal/images/b-plus.png"></a>
                </td>
                <td width="200px" class="tdblueM">Part Name</td>
                <td width="100px" class="tdblueM">
                    <%
                        if (CommonUtil.isMember("자동차사업부")) {
                    %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%>
                    <%
                        } else {
                    %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%>
                    <%
                        }
                    %>
                </td>
                <td width="200px" class="tdblueM">조립처</td>
                <td width="100px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02640") %><%--조립구분--%></td>
                <td width="50px" class="tdblueM0">U/S</td>
            </tr>
        </table>
    </form>
</body>
</html>
<%
    QuerySpec qs = new QuerySpec();

    int idxpi = qs.appendClassList(ProductInfo.class, true);

    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id" , "=", CommonUtil.getOIDLongValue(oid));
    qs.appendWhere(cs, new int[] { idxpi } );
    SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "thePersistInfo.theObjectIdentifier.id", "idA2A2", false);
    //SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "pName", "pName", false);


    QueryResult qrpi = PersistenceHelper.manager.find(qs);
    PartnerDao partnerDao = new PartnerDao();
%>
    <script type="text/javascript">
    var afterProductRowCount = <%=qrpi.size()%>;
    </script>
<%        
    while(qrpi.hasMoreElements()){
      Object o[] = (Object[])qrpi.nextElement();
      ProductInfo pi = (ProductInfo)o[0];
      String carName = ProductHelper.getCarName(pi.getPersistInfo().getObjectIdentifier().toString());
      String carName2 = "";
      String assemblyPlaceName = "";
      String assemblyPlace = "";
      try{
	     if(pi.getAssemblyPlaceType() != null){
              if(pi.getAssemblyPlaceType() != null && pi.getAssemblyPlaceType().equals("사내")){
                  assemblyPlaceName = pi.getAssemblyPlace().getName();
                  assemblyPlace = CommonUtil.getOIDString(pi.getAssemblyPlace());
              }else if(pi.getAssemblyPlaceType() != null && pi.getAssemblyPlaceType().equals("외주")){
                  assemblyPlaceName = partnerDao.ViewPartnerName(pi.getAssemblyPartnerNo());
                  assemblyPlace = pi.getAssemblyPartnerNo();
              }
          }
          carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
      }catch(Exception e){
        carName2 = "";
      }
%>
    <script>
        var tBody = document.getElementById("productInfo");
        var rowId = genRowId();

        tableRows = tBody.rows.length;
        newTr = tBody.insertRow(tableRows);

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "30";
        newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:onDeleteTableRow2('productInfo','rowId','" + rowId + "');\"><img src='/plm/portal/images/b-minus.png' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
        
        //제품번호
        <%-- newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "90";
        newTd.innerHTML = "<input type='text' name='pNum' class='txt_field'  style='width:95%' id='pNum"+rowId+"' value='<%=pi.getPNum()==null?"":pi.getPNum() %>' >"; --%>

        //제품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "150";
        newTd.innerHTML = "<input type='hidden' name='pOid"+rowId+"' id='pOid"+rowId+"' value='<%=pi.getPersistInfo().getObjectIdentifier().toString()%>'>";
        newTd.innerHTML += "<input type='text' name='pName' class='txt_field'  style='width:95%' id='pName"+rowId+"' value='<%=pi.getPName()==null?"":pi.getPName() %>'>";

        //차종
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "150";
        newTd.innerHTML = "<div style='width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>";
        newTd.innerHTML += "<span name='carName' id='carName"+rowId+"' value='<%=carName==""?carName2:carName %>'><%=carName==""?carName2:carName %></span></nobr>&nbsp;";
        newTd.innerHTML += "<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";

        newTd.innerHTML  += "<input type='hidden' name='y1T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y2T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y3T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y4T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y5T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y6T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y7T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y8T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y9T' value=''>";
        newTd.innerHTML  += "<input type='hidden' name='y10T' value=''><input type='hidden' name='carName' id='carName"+rowId+"' value=''>";
        
        //조립처
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        var selectStr = "<select id=\"assemblyPlaceType" + rowId + "\" name=\"assemblyPlaceType\" style=\"width:70px\" class=\"fm_jmp\" onChange=\"javascript:changeAssembly(" + rowId + ")\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"사내\" <%=pi.getAssemblyPlaceType()!=null&&pi.getAssemblyPlaceType().equals("사내")?"selected":""%>><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"외주\" <%=pi.getAssemblyPlaceType()!=null&&pi.getAssemblyPlaceType().equals("외주")?"selected":""%>><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option></select> ";
        selectStr += "<input id=\"assemblyPlaceName" + rowId + "\" type=text name=\"assemblyPlaceName\" style=\"width:90px\" class=\"txt_fieldRO\" value=\"<%=assemblyPlaceName%>\" readonly>";
        selectStr += "<input id=\"assemblyPlace" + rowId + "\" type=hidden name=\"assemblyPlace\" value=\"<%=assemblyPlace%>\">";
        selectStr += " <a href='javascript:selectAssemplyPlace("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
        newTd.innerHTML = selectStr;
        
        //조립구분
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        selectStr = "<select name=\"assembledType\" class=\"fm_jmp\" style=\"width: 90%\">";
        selectStr += "<option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>";
        <%
            parameter.clear();
            parameter.put("locale", messageService.getLocale());
            parameter.put("codeType", "ASSEMBLEDTYPE");
            numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
            String assembledTypeOid = "";
            if(pi.getAssembledType() != null){            
                assembledTypeOid = CommonUtil.getOIDString(pi.getAssembledType());
            }
            for (int i = 0; i < numCode.size(); i++) {
        %>
        selectStr += "<option value=\"<%=numCode.get(i).get("oid")%>\" <%=numCode.get(i).get("oid").equals(assembledTypeOid)?" selected":""%>><%=numCode.get(i).get("value")%></option>";
        <%
            }
        %>
        selectStr += "</select>";
        newTd.innerHTML = selectStr;
        
        //usage
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "70";
        newTd.innerHTML = "<span name='usageT' id='usageT"+rowId+"' style='width:75%' value='<%=pi.getUsage()==null?"":pi.getUsage() %>'><%=pi.getUsage()==null?"":pi.getUsage() %><input type='hidden' name='usageT' value='<%=pi.getUsage()==null?"":pi.getUsage() %>'></span>&nbsp;";
    </script>
<%
    }
%>