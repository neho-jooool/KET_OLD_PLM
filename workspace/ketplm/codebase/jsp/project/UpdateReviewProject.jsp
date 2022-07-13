<%@page import="ext.ket.part.entity.KETPartClassification"%>
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
<TITLE><%=messageService.getString("e3ps.message.ket_message", "03096") %><%--프로젝트 정보수정--%></TITLE>
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
        //if(form.receiptNumber.value =="") {
        //  alert("접수 번호를(을) 입력하시기 바랍니다.");
        //  form.receiptNumber.focus();
        //  return;
        //}

        if(form.planStartDate.value =="") {
          alert("개발시작일를(을) 입력하시기 바랍니다.");
          form.planStartDate.focus();
          return;
        }

        if(form.developenttype.value =="") {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00626") %><%--개발구분을 입력하세요--%>');
            form.developenttype.focus();
            return;
        }

        <%-- if(form.developenttype.value =="DEV002") {
            if(form.salesUser.value =="") {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02137") %>영업담당를(을) 입력하시기 바랍니다');
                return;
            }
        } --%>

/*         if(form.devDeptOid.value =="") {
          alert("담당부서를(을) 입력하시기 바랍니다.");
          form.devUserDept.focus();
          return;
        } */

        /* if(form.proposalDate.value =="") {
          alert("개발제안서제출일를(을) 입력하시기 바랍니다.");
          form.proposalDate.focus();
          return;
        }

        if(form.estimateDate.value =="") {
          alert("개발원가제출일를(을) 입력하시기 바랍니다.");
          form.estimateDate.focus();
          return;
        }
 */
        if(form.projectName.value =="") {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02592") %><%--제품명를(을) 입력하시기 바랍니다--%>');
            form.projectName.focus();
            return;
        }
        if(form.rank.value =="") {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>');
            form.rank.focus();
            return;
        }

        if(form.productTypeLevel2.value =="") {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02579") %><%--제품구분를(을) 입력하시기 바랍니다.--%>');
            form.productTypeLevel2.focus();
            return;
        }
        
        if(form.devUser.value =='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00383") %><%--PM을 지정 하십시오 --%>");
            form.devUser.focus();
            return;
        }

        /*
        if(form.CUSTOMEREVENTOid == null || form.CUSTOMEREVENTOid == 'undefined' || form.CUSTOMEREVENTOid.value =="") {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02851") %><%--최종사용처를(을) 입력하시기 바랍니다.--%>');
            return;
        }

        if(form.sOid == null || form.sOid == 'undefined' || form.sOid .value =="") {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00731") %><%--검토의뢰처를(을) 입력하시기 바랍니다--%>");
            return;
        }*/


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
        }

        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "03119") %><%--프로젝트를 수정 하시겠습니까?--%>')) {
            return;
        } else {
            //document.forms[0].action = "/plm/servlet/e3ps.project.servlet.ProjectServlet";
            document.forms[0].cmd.value = "update";
            document.forms[0].method = "post";
            //document.forms[0].submit();
            
            showProcessing();
            
            $.ajax( {
                url : "/plm/jsp/project/ActionProject.jsp?command=update",
                type : "POST",
                data : $('[name=prodForm]').serialize(),
                async : false,
                success: function(data) {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다. --%>');
                    opener.location.href='/plm/jsp/project/ReviewProjectView.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
                    self.close();
                },
                error : function(){
                    alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다. --%>');
                    hideProcessing();
                }
            });
        }
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
                    targetTable.deleteRow(i+2);
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
                targetTable.deleteRow(2);
                return;
            }
        }
    }

    var memberFormId = "";
    
    function acceptRoleMember(objArr) {
    	var rname = memberFormId;
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById("temp" + rname);
        var keyName = document.getElementById(rname);
        var DeptName = document.getElementById(rname+"Dept");
        /* var DeptOid = null;
        if(rname == 'devUser'){
        	DeptOid = document.getElementById("devDeptOid");	
        } */
        
        
        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];
            /* if(DeptOid != null){
            	DeptName.value = infoArr[5];
                DeptOid.value = infoArr[2];	
            } */
            
        }
    }
    
    function addRoleMember(rname) {
    	memberFormId = rname;
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=acceptRoleMember";
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,rname, 800, 710, opts);
        
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
        var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?type=all&mode=s&invokeMethod=deptCallBack";
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        
        getOpenWindow2(url,'', 430, 465, opts);
    }
    
    function deptCallBack(attacheDept){
        document.forms[0].devDeptOid.value = attacheDept[0].OID;
        document.forms[0].devUserDept.value = attacheDept[0].NAME;
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
  
  function insertLastUsingBuyer() {
      
      window.open("/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode=multi&designTeam=&invokeMethod=selectMultiCustomer", "CUSTOMEREVENT", "top=100px, left=100px, height=800px, width=1200px");
      
  }
  
//최종사용처 검색화면 오픈하여 선택된 최종사용처를 입력한다.
  function selectMultiCustomer(arrObj) {
      var fm = document.prodForm;
      var pos = fm.CUSTOMEREVENTOid.length;
      
      var subArr;
      
      
      for(var i = 0; i < arrObj.length; i++) {
        subArr = arrObj[i];
      
        for(var j = 0; j < pos; j++) {
          if(fm.CUSTOMEREVENTOid.options[j].value==subArr[0]){
            alert(subArr[2]+"는 이미 존재하는 최종 사용처입니다");
            return;
          }
        }
      
        fm.CUSTOMEREVENTOid.length = pos+i+1;
        fm.CUSTOMEREVENTOid.options[pos+i].value = subArr[0];
        fm.CUSTOMEREVENTOid.options[pos+i].text = subArr[2];
        fm.CUSTOMEREVENTOid.options[pos+i].selected = true;
      }
  }
      
  //선택된 최종사용처를 삭제한다.
  function deleteLastUsingBuyer() {
      var fm = document.prodForm;
      while(fm.CUSTOMEREVENTOid.selectedIndex>=0){
        if((fm.CUSTOMEREVENTOid.length>0)&&(fm.CUSTOMEREVENTOid.selectedIndex>=0)){
          var pos = fm.CUSTOMEREVENTOid.selectedIndex;
          fm.CUSTOMEREVENTOid.remove(pos);
        }
      }
  }
  
  function selectMultiSubContractor(arrObj) {
	  var fm = document.prodForm;
	  var pos = fm.sOid.length;
	  var subArr;
	  
	  if(typeof arrObj == "undefined" || arrObj == null) {
	      return;
	  }

	  for(var i = 0; i < arrObj.length; i++) {
		  subArr = arrObj[i];

	      for(var j = 0; j < pos; j++) {
	        if(fm.sOid.options[j].value==subArr[0]){
	          alert(subArr[2]+"는 이미 존재하는 의뢰처입니다");
	          return;
	        }
	      }
	      fm.sOid.length = pos+i+1;
	      fm.sOid.options[pos+i].value= subArr[0];
	      fm.sOid.options[pos+i].text = subArr[2];
	      fm.sOid.options[pos+i].selected = true;
	 }
  }
  
  //의뢰처 검색화면 오픈하여 선택된 의뢰처를 입력한다.
  function insertRequestBuyer() {

    

    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SUBCONTRACTOR&command=select&mode=multi&viewType=&disable=&invokeMethod=selectMultiSubContractor";

    window.open(url, "MultiSubContractor", "top=100px, left=100px, height=500px, width=850px");
    
  }

  //선택된 의뢰처를 삭제한다.
  function deleteRequestBuyer() {
    var fm = document.prodForm;

    while(fm.sOid.selectedIndex>=0){
      if((fm.sOid.length>0)&&(fm.sOid.selectedIndex>=0)){
        var pos = fm.sOid.selectedIndex;
        fm.sOid.remove(pos);
      }
    }

  }
  
  function selectDr(){
    //개발 검토  : developmentStep=R
    //제품   : developmentStep=D
        var url="/plm/jsp/dms/SearchDevRequestPop.jsp?method=selDr&developmentStep=R&mode=one";
        openWindow(url,"","800","600","status=1,scrollbars=no,resizable=no");
  }
  
  function selDr(arr){
        var param = "command=drSearch";
        for (var i = 0; i < arr.length; i++){
           param += "&drOid=" + encodeURIComponent(arr[i][0]);
        }
        var url = "/plm/jsp/project/ProjectDevRequestAction.jsp";
        postCallServer(url, param, onTargetSet, true);
  }
  
  window.getTagText = function(xmlDoc){
      return xmlDoc.textContent || xmlDoc.text || '';
  }
  
  function onTargetSet(req){

    var xmlDoc = req.responseXML;
    var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
    
    if(msg == 'false' && xmlDoc.getElementsByTagName("l_result") != null && getTagText(xmlDoc.getElementsByTagName("l_result")[0]) != ""){
      alert(showElements[0].getElementsByTagName("l_result")[0].text);
      return;
    }
    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

    
    var l_drNumber = xmlDoc.getElementsByTagName("l_drNumber");
    var l_drKeyOid = xmlDoc.getElementsByTagName("l_drKeyOid");

    var l_creatorName = xmlDoc.getElementsByTagName("l_creatorName");
    var l_creatorOid = xmlDoc.getElementsByTagName("l_creatorOid");
    var l_salesUserDept = xmlDoc.getElementsByTagName("l_salesUserDept");
    var l_name = xmlDoc.getElementsByTagName("l_name");
    var l_proposalSubmitDate = xmlDoc.getElementsByTagName("l_proposalSubmitDate");
    var l_costSubmitDate = xmlDoc.getElementsByTagName("l_costSubmitDate");
    var form = document.forms[0];
    form.drNumber.value = decodeURIComponent(getTagText(l_drNumber[0]));
    form.drKeyOid.value = decodeURIComponent(getTagText(l_drKeyOid[0]));
    
    //영업 담당자
    form.tempsalesUser.value = decodeURIComponent(getTagText(l_creatorName[0]));
    form.salesUser.value = decodeURIComponent(getTagText(l_creatorOid[0]));

    
    var l_custOid = xmlDoc.getElementsByTagName("l_custOid");
    var l_custName = xmlDoc.getElementsByTagName("l_custName");

    var goFlag = true;
    
    // 최종 사용처
    var targetTable = document.getElementById("CUSTOMEREVENTOid");
    var subOid = false;
    var subName = false;
    var fm = document.prodForm;
    var pos = fm.CUSTOMEREVENTOid.length;
    for(var i = 0; i < l_custOid.length; i++){
        for(var j = 0; j < pos; j++) {
          if(fm.CUSTOMEREVENTOid.options[j].value== getTagText(l_custOid[i])){
            //alert(getTagText(l_custName[i])+"는 이미 존재하는 최종 사용처입니다");
            goFlag = false;
            //return;
          }
        }
        
        if(goFlag){
        	fm.CUSTOMEREVENTOid.length = pos+i+1;
            fm.CUSTOMEREVENTOid.options[pos+i].value = getTagText(l_custOid[i]);
            fm.CUSTOMEREVENTOid.options[pos+i].text = getTagText(l_custName[i]);
            fm.CUSTOMEREVENTOid.options[pos+i].selected = true;	
        }
        
    }
    goFlag = true;
    //고객처
    var l_subOid = xmlDoc.getElementsByTagName("l_subOid");
    var l_subName = xmlDoc.getElementsByTagName("l_subName");

    var targetTable2 = document.getElementById("sOid");
    //deleteLastUsingBuyer();

    var subOid = false;
    var subName = false;
    var fm = document.prodForm;
    var pos = fm.sOid.length;
    
    
    
    for(var i = 0; i < l_subOid.length; i++){
        //subArr = arrObj[i];
      
        for(var j = 0; j < pos; j++) {
          if(fm.sOid.options[j].value==getTagText(l_subOid[i])){
        	  goFlag = false;
            //alert(getTagText(l_subName[i])+"는 이미 존재하는 고객처입니다");
            //return;
          }
        }
        if(goFlag){
        	fm.sOid.length = pos+i+1;
            fm.sOid.options[pos+i].value = getTagText(l_subOid[i]);
            fm.sOid.options[pos+i].text = getTagText(l_subName[i]);
            fm.sOid.options[pos+i].selected = true;
        }
        
    }

  }
  
  $(document).ready(function(){
      //영업담당자 suggest
      SuggestUtil.bind('USER', 'tempsalesUser', 'salesUser');
      //PM suggest
      SuggestUtil.bind('USER', 'tempdevUser', 'devUser');
      //부서 suggest
      //SuggestUtil.bind('DEPARTMENT', 'devUserDept', 'devDeptOid');
      CalendarUtil.dateInputFormat('proposalDate');
      CalendarUtil.dateInputFormat('estimateDate');
      CalendarUtil.dateInputFormat('planStartDate');//개발시작일
  });
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
                                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03082")%><%--프로젝트 수정--%></td>
                                                    <td width="10"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue">취소</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
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
                <td class="space5"></td>
            </tr>
        </table>

        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm1"></td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <COL width="22%">
            <COL width="28%">
            <COL width="22%">
            <COL width="28%">
            <tr>
                <td class="tdblueL">개발목적</td>
                <td class="tdwhiteL0" colspan="3">
                <%=project.getMainGoalType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPANDREVIEWGOAL", project.getMainGoalType().getCode(), messageService.getLocale().toString()))+ " / "%>
                <%=project.getSubGoalType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPANDREVIEWGOAL", project.getSubGoalType().getCode(), messageService.getLocale().toString()))%>
                </td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02506")%><%--접수번호--%></td>
                <td class="tdwhiteL">
                    <input type="text" name="drNumber" value="<%=project.getDevRequest()!=null?project.getDevRequest().getNumber():""%>" class="txt_field" style="width: 70%" id="textfield3" readonly>
                    <input type=hidden name="drKeyOid" value="<%=CommonUtil.getOIDString(project.getDevRequest())%>">
                    <a href="javascript:;" onClick="javascript:selectDr();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                    <a href="javascript:;" onClick="javascript:document.forms[0].drNumber.value='';document.forms[0].drKeyOid.value='';">
                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625")%><%--개발구분--%>
                    <span class="style1"> *</span></td>
                <%
                
                String MainGoalType = "";
                
                %>
                <%
                
                if(project.getMainGoalType() != null){ //개발목적이 4M 또는 연구 일때 개발구분은 선택불가
                    MainGoalType = project.getMainGoalType().getCode(); 
                }
                
                if(!"".equals(MainGoalType) && !"DP2".equals(MainGoalType) && !"DP3".equals(MainGoalType)){ %>
                <td class="tdwhiteL0"><select name="developenttype" style="width: 98%">
                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <%
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "DEVELOPENTTYPE");
                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
                
                for ( int i=0; i<numCode.size(); i++ ) {
                    String selectedValue = "";
                    if ( project.getDevelopentType() != null && project.getDevelopentType().getCode().equals(numCode.get(i).get("code")) )
                	selectedValue = "selected";
                %>
                
                <option value="<%=numCode.get(i).get("code") %>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select></td>
                <%}else{ %>
                <input type="hidden" name="developenttype" value="<%=project.getDevelopentType() %>">
                <td class="tdwhiteL"><%=project.getDevelopentType() == null ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", project.getDevelopentType().getCode(), messageService.getLocale().toString()))%></td>
                <%} %>          
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578")%><%--제품구분--%>
                    <span class="style1"> *</span></td>
                <td class="tdwhiteL0" colspan='3'>
                    <%
                    String className = "";
                    try{
                        if(StringUtil.checkString(project.getProductTypeLevel2())){
                            KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(project.getProductTypeLevel2());
                            className = partClaz.getClassKrName();
                        }
                    }catch(Exception e){}
                    %>
                    <input type="text" name="productTypeLevel2Name" class="txt_field" style="width: 30%" value="<%=className%>">
                    <input type="hidden" name="productTypeLevel2" value="<%=StringUtil.checkNull(project.getProductTypeLevel2())%>">
                    <a href="javascript:SearchUtil.selectOnePartClaz('productTypeLevel2', 'productTypeLevel2Name','onlyLeaf=Y&openAll=Y&depthLevel2=Y');">
                    <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                    <a href="javascript:CommonUtil.deleteValue('productTypeLevel2','productTypeLevel2Name');">
                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                </td>
                <%-- <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01201")%>담당부서
                    <span class="style1"> *</span></td>
                <td class="tdwhiteL0">
                    <input type=text name="devUserDept" id="devUserDept" style="width: 70%;" class='txt_field' value="<%=projectData.devDept%>">
                    <input type=hidden name="devDeptOid" id="devDeptOid" value="<%=projectData.devDeptOid%>"> 
                    <a href="javascript:addDepartment2();"><img src="/plm/portal/images/icon_5.png" border=0></a>
                    <a href="javascript:onDeleteMember();"><img src="/plm/portal/images/icon_delete.gif" border=0></a></td> --%>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02139")%><%--영업담당자--%></td>
                <td class="tdwhiteL">
                    <input style="width: 70%;" type='text' id='tempsalesUser' name='tempsalesUser' value='<%=projectData.salesName%>' class='txt_field'>
                    <input type='hidden' id='salesUser' name='salesUser' value='<%=(projectData.salesOid == null) ? "" : projectData.salesOid%>'> 
                    <a href="javascript:addRoleMember('salesUser');"> <img src="/plm/portal/images/icon_user.gif" border=0></a> 
                    <a href="javascript:deleteRoleMember2('salesUser');"> <img src="/plm/portal/images/icon_delete.gif" border=0></a></td>
                <td class="tdblueL">PM<span class="style1"> *</span></td>
                <td class="tdwhiteL0">
                    <input style="width: 70%;" type='text' id='tempdevUser' name='tempdevUser' value='<%=projectData.pjtPmName%>' class='txt_field'>
                    <input type='hidden' id='devUser' name='devUser' value='<%=(projectData.pjtPm == null) ? "" : CommonUtil.getOIDString(projectData.pjtPm)%>'>
                    <a href="javascript:addRoleMember('devUser');"> <img src="/plm/portal/images/icon_user.gif"></a> 
                    <a href="javascript:deleteRoleMember2('devUser');"> <img src="/plm/portal/images/icon_delete.gif"></a>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로젝트명 --%><span class="style1"> *</span></td>
                <td colspan=3 class="tdwhiteL0"><input type=text name="projectName" class="txt_field" style="width: 98%"
                    value="<%=projectData.pjtName%>"></td>
            </tr>
            <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00663")%><%--개발제안서 제출일--%></td>
                <td class="tdwhiteL">
                    <input name="proposalDate" value="<%=DateUtil.getDateString(rproject.getProposalDate(), "D")%>" class="txt_field" style="width: 70%;"/>
                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('proposalDate');" style="cursor: hand;"></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00649")%><%--개발원가 제출일--%></td>
                <td class="tdwhiteL0">
                    <input name="estimateDate" value="<%=DateUtil.getDateString(rproject.getEstimateDate(), "D")%>" class="txt_field" style="width: 70%;"  />
                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('estimateDate');" style="cursor: hand;"></td>
            </tr>
            
            <tr>
                <td class="tdblueL">Rank <span class="style1"> *</span></td>
                <td class="tdwhiteL"><select name="rank" style="width: 98%">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                        <%
                            parameter.clear();
                            parameter.put("locale", messageService.getLocale());
                            parameter.put("codeType", "RANK");
                            if("자동차".equals(projectTypeValue)){
                                parameter.put("code",     "RAN1000");                                                   
                            }else if("전자".equals(projectTypeValue)){
                                parameter.put("code",     "RAN2000");                                                    
                            }else{
                                parameter.put("code",     "RAN3000");                                                    
                            }
                            parameter.put("depthLevel", "child");
                            //parameter.put("name", projectTypeValue);
                            numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                            for (int i = 0; i < numCode.size(); i++) {
                        		String selectedValue = "";
                        		if (projectData.rankOID != null && projectData.rankOID.equals(numCode.get(i).get("oid")))
                        		    selectedValue = "selected";
                        %>
                        <option value="<%=numCode.get(i).get("oid")%>" <%=selectedValue%>><%=numCode.get(i).get("value")%></option>
                        <%
                            }
                        %>
                </select></td>
                <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817","")%><!-- 계획시작일 --> <span class="red">*</span></td>
                <td class="tdwhiteL0" colspan="3"><input type="text" name="planStartDate" class="txt_field"
                    style="width: 70%" value="<%=DateUtil.getDateString(projectData.pjtPlanStartDate, "D")%>"> <img src="/plm/portal/images/icon_delete.gif" border="0"
                    onclick="javascript:CommonUtil.deleteDateValue('planStartDate');" style="cursor: hand;"></td>
            </tr>
            <tr>
                <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%--최종사용처--%></td>
                <td class="tdwhiteL">
                    <select id="CUSTOMEREVENTOid" name="CUSTOMEREVENTOid" style="width: 75%;" size=2 multiple="multiple">
                    <%
                        if (projectData.customereventVec != null && projectData.customereventVec.size() > 0) {
                            
                            for (int i = 0; i < projectData.customereventVec.size(); i++) {
                                NumberCode nc = (NumberCode) projectData.customereventVec.get(i);
                                String ncOid = (String)CommonUtil.getOIDString(nc);
                                String masterName = NumberCodeUtil.getNumberCodeValue("CUSTOMEREVENT", nc.getCode(), messageService.getLocale().toString());
                                out.print("<option value='"+ncOid+"' selected>"+masterName+"</option>");
                            }
                        }
                    %>
                    </select>
                    <a href="javascript:insertLastUsingBuyer()"><img src="../../portal/images/icon_5.png"
                        border="0" align="top"></a> <a href="javascript:deleteLastUsingBuyer()"><img
                        src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                <td class="tdwhiteL0">
                    <select name="sOid" style="width: 75%;" size=2 multiple="multiple">
                        <%
                            if (projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
                                for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
                                    NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
                                    String ncOid = (String)CommonUtil.getOIDString(nc);
                                    String masterName = NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode(), messageService.getLocale().toString());
                                    out.print("<option value='"+ncOid+"' selected>"+masterName+"</option>");
                                }
                            }
                        %>
                    </select>  
                    <a href="javascript:insertRequestBuyer()"><img src="../../portal/images/icon_5.png" border="0"
                        align="top"></a> <a href="javascript:deleteRequestBuyer()"><img
                        src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
            </tr>
            <%if("전자".equals(projectTypeValue)){%>
            <tr>
            <td width="140" class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "04106")%><%--검토결과--%><span class="style1"> *</span></td>
                <td class="tdwhiteL" colspan="5">
                    <select id="reviewResult" name="reviewResult" style="width: 30%;">
                    <option value='' selected>선택</option>
                    <option value='진행' <%if("진행".equals(rproject.getReviewResult())){ %> selected <%} %>>진행</option>
                    <option value='미진행' <%if("미진행".equals(rproject.getReviewResult())){ %> selected <%} %>>미진행</option>
                    </select>
                </td>
            </tr>
            <%} %>
        </table>
    </form>
</body>
</html>