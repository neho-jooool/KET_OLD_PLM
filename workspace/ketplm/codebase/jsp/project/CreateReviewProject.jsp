<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,
                  wt.fc.*,
                  wt.org.*,
                  wt.team.*,
                  wt.project.Role,
                  wt.session.*,
                  e3ps.project.*,
                  e3ps.project.beans.*,
                  e3ps.common.code.*,
                  e3ps.common.util.*,
                  e3ps.common.web.*,
                  e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp" %>
<%
    String tempOid = request.getParameter("tempOid");
    String pwlinkOid = request.getParameter("pwlinkOid");

    if(pwlinkOid == null){
        pwlinkOid = "";
    }

    if(tempOid == null){
        tempOid = "";
    }

    Config conf = ConfigImpl.getInstance();
    String lifecycle ="KET_REVIEW_PMS_LC"; //conf.getString("lifecycle.ReviewProject");



    Vector vecTeamStd = null;
    TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
    if(tempTeam != null) {
        vecTeamStd = tempTeam.getRoles();
    }
    boolean isType0 = CommonUtil.isMember("전자사업부");
    boolean isType1 = CommonUtil.isMember("자동차사업부");
    String projectType = "";
    String wbsType = "";
    if(isType0){
        projectType = "전자";
        wbsType = "0";
    }
    if(isType1){
        projectType = "자동차";
        wbsType = "1";
    }

    Vector tMap = null;

    WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
    PeopleData pd = new PeopleData(sessionUser);

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;
%>
<%@page import="e3ps.common.jdf.config.Config"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00723")%><%--검토 프로젝트 등록--%></title>
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
<!--
    //Check Function
    function checkValidate() {
        var form = document.forms[0];
        
        if(form.developePurpose1.value =='') {
        	alert('개발목적을 입력하시기 바랍니다.');
            form.developePurpose1.focus();
            return false;
        }
        
        if(form.developePurpose2.value =='') {
        	alert('개발목적을 입력하시기 바랍니다.');
            form.developePurpose2.focus();
            return false;
        }

        if(form.productTypeLevel2.value =='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02579") %><%--제품구분를(을) 입력하시기 바랍니다.--%>');
            form.productTypeLevel2.focus();
            return false;
        }
        
        if(form.developenttype.value =='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00626") %><%--개발구분을 입력하세요--%>');
            form.developenttype.focus();
            return false;
        }
        
        if(form.developenttype.value == '-'){//4M 또는 연구 일때 개발구분 선택은 -
            if(form.developePurpose1.value == 'DP2' || form.developePurpose1.value == 'DP3'){
                
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "00584") %><%--개발 구분을 입력하시기 바랍니다--%>");
                form.developenttype.focus();
                return false;
            }
        }
        
        if(form.devUser.value =='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00383") %><%--PM을 지정 하십시오 --%>");
            form.devUser.focus();
            return;
        }

        
<%--         if(form.devUserDept.value =='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00633") %>개발담당부서를(을) 입력하시기 바랍니다.');
            form.devUserDept.focus();
            return false;
        } --%>
        
        if(form.projectName.value =='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00396") %><%--Project Name을 입력하시기 바랍니다.--%>');
            form.projectName.focus();
            return false;
        }
        
        
        //수정사항 2014.12.08 - 개발제안서 제출일 , 개발원가 제출일 필수 표시 제거 요청
        /* if(form.proposalDate.value =='') {
          alert("개발제안서제출일를(을) 입력하시기 바랍니다.");
          form.proposalDate.focus();
          return false;
        }

        if(form.estimateDate.value =='') {
          alert("개발원가제출일를(을) 입력하시기 바랍니다.");
          form.estimateDate.focus();
          return false;
        } */

        if(form.rank.value =='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00438") %><%--Rank를 선택하시기 바랍니다--%>');
            form.rank.focus();
            return false;
        }

        if(form.planStartDate.value =='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00645") %><%--개발시작일을 입력하시기바랍니다--%>');
            form.planStartDate.focus();
            return false;
        }

        if(form.templateOid == null || form.templateOid == 'undefined' || form.templateOid.value == '') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03057") %><%--프로젝트 WBS를(을) 입력하시기 바랍니다.--%>');
            return false;
        }

        return true;
    }
    
    //save
    function actionSave(){
        if(!checkValidate()) {
            return;
        }

        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "03120") %><%--프로젝트를 생성하시겠습니까?--%>')) {
            return;
        }
        //최종사용처, 고객처 모두 선택되게 한다. 선택 않되면 등록이 않되는 multiple 속성
        $('[name=CUSTOMEREVENTOid] option').prop('selected', true);
        $('[name=sOid] option').prop('selected', true);  
        showProcessing();
        $.ajax({
            url : "/plm/jsp/project/ActionProject.jsp?command=Create",
            type : "POST",
            data : $('[name=prodForm]').serialize(),
            success: function(data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01315")%>');
                openView(data.replace(/(^\s*)|(\s*$)/gi, ''));
                //location.href="/plm/jsp/project/ProjectViewFrm.jsp?oid="+data.replace(/(^\s*)|(\s*$)/gi, '');
                self.close();
            },
            error : function(){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01317")%><%--등록실패 --%>');
                hideProcessing();
            }
        });
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
    //
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
    // 고객처 추가
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
                if(isDuplicateCheckBox('oid', subArr[0])) {
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
                newTd.innerHTML = "<input type=\"checkbox\" name=\"oid\" value=\"" + subArr[0] + "\"></input><input type='hidden' name='sOid' value='" + subArr[0] + "'>";

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
    //
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

        len = form.oid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.oid[i].checked == true) {
                    tableName = form.oid[i].value;
                    //alert(tableName);
                }
            }
        }else{
            tableName = form.oid.value;
        }

        acceptProcessM3(returnValue, tableName);
    }
    //
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
    //프로젝트 속성  가져오기 끝
    //*****************************************************************************************************************************************************************

    //Project Template 가져오기 시작
    function onProjectTemplate() {
    	SearchUtil.selectOneWBSTemplate('review','acceptProjectTemplate');
        <%-- var url = "/plm/jsp/project/template/ProjectTempSelectPopUp.jsp?mode=one&wType=1&selectReview=<%=wbsType%>";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=840px; dialogHeight:530px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        acceptProjectTemplate(attache); --%>
    }

    function acceptProjectTemplate(objArr) {
        if(objArr.length == 0) {
            return;
        }


        var targetTable = document.getElementById("templatetable");

        if(targetTable.rows.length > 1) {
            targetTable.deleteRow(1);
        }

        var trArr;
        var trArr;
        var i = 0;
        //for(var i = 0; i < objArr[objArr.length-1].length; i++) {
          tableRows = targetTable.rows.length;

          trArr = objArr[objArr.length-1];
          newTr = targetTable.insertRow(tableRows);
          //삭제
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdwhiteM";
          newTd.innerHTML = "<center><a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','" + trArr.ProjectOid + "');\"><img src=\"/plm/portal/images/b-minus.png\"></a><input type='hidden' name='templateOid' value='" + trArr.ProjectOid + "'>";
          for ( var j=0; j<objArr.length; j++) {
              if(objArr[j]['name'] == 'productType'){
                 newTd.innerHTML += '<input type=hidden name=wbsType value='+objArr[j].value+'>';
              }
              if(objArr[j]['name'] == 'category'){
                  newTd.innerHTML += '<input type=hidden name=category value='+objArr[j].value+'>';
              }
          }
          
          //Template 명
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdwhiteL";
          newTd.innerText = trArr.TempName;

          //기간
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdwhiteL text-center";
          newTd.innerText = trArr.TempDu;

          //등록일
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdwhiteL text-center";
          newTd.innerText =trArr.TempCreateDate;

          //최종수정일
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdwhiteL0 text-center";
          newTd.innerText = trArr.TempModifyDate;
        //}

    }
    <%if(tempOid.length() > 0){
    TemplateProject tempObj = (TemplateProject)CommonUtil.getObject(tempOid);
    TemplateProjectData data = new TemplateProjectData(tempObj);
    String tempobj_name = data.tempName;
    String tempobj_duration = ""+data.tempDuration;

    %>

    function loadTemp()
    {

        var targetTable = document.getElementById("templatetable");
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','<%=tempOid%>');\"><img src=\"/plm/portal/images/b-minus.png\"></a><input type='hidden' name='templateOid' value='<%=tempOid%>'>";
        
        //Template 명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerText = " <%=tempobj_name%>";

        //기간
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL text-center";
        newTd.innerText = "<%=tempobj_duration%>";

        //등록일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL text-center";
        newTd.innerText = "<%=DateUtil.getDateString(data.tempCreateDate, "D")%>";

        //최종수정일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL0 text-center";
        newTd.innerText = "<%=DateUtil.getDateString(data.tempModifyDate, "D")%>";
    }
    <%}%>
    //Project Template 가져오기 끝
    
    var targetUserId = "";
    function addRoleMemberDept(targetId) {
        targetUserId = targetId;
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=acceptRoleMemberDept";
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, 800, 710, opts);
        
    }


    function acceptRoleMemberDept(objArr) {
    	var rname = targetUserId;
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
        }
        document.forms[0].tempsalesUser.focus();
    }
    
    function addRoleMember() {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=acceptRoleMember";
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, 800, 710, opts);
        
    }
    

    function acceptRoleMember(objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById("tempdevUser");
        var keyName = document.getElementById("devUser");
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

     //사용자 가져오기 시작 ........................................................................................
    //............................................................................................................
    function addMember(tableid, membertag) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
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

     //부서 가져오기 시작 ........................................................................................
    //............................................................................................................
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
        getOpenWindow2(url,name, 430, 465, opts);
    }
    
    function deptCallBack(attacheDept){
    	document.forms[0].devDeptOid.value = attacheDept[0].OID;
    	document.forms[0].devUserDept.value = attacheDept[0].NAME;
    }

    //부서 가져오기 끝 ........................................................................................
    //............................................................................................................
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
                    targetTable.deleteRow(i+2);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                targetTable.deleteRow(2);
                return;
            }
        }
    }
    // 개발 검토 납입처 관련 삭제
    function onDeleteTableRow0(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
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
            alert('<%=messageService.getString("e3ps.message.ket_message", "01698") %><%--삭제 할 고객사를 선택 하십시오--%>');
            return;
        }

        form = document.forms[0];

        len = form.oid.length;
        if(len) {
            for(var i = len - 1; i >= 0; i--) {
                if(form.oid[i].checked == true) {
                    onDeleteTableRow('SUBCONTRACTOR', 'oid', form.oid[i].value);
                }
            }
        }else{
            onDeleteTableRow('SUBCONTRACTOR', 'oid', form.oid.value);
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
        if(form.oid == null) {
            return false;
        }

        len = form.oid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.oid[i].checked == true) {
                    return true;
                }
            }
        }else {
            if(form.oid.checked == true) {
                return true;
            }
        }

        return false;

    }

    function checkedCheck2() {

        form = document.forms[0];
        if(form.oid == null) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00843") %><%--고객사를 등록 하십시오--%> ");
            return false;
        }

        len = form.oid.length;
        if(len) {
            cnt = 0;
            for(var i = 0; i < len;i++) {
                if(form.oid[i].checked == true) {
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
            if(form.oid.checked == true) {
                return true;
            }
        }
        alert('<%=messageService.getString("e3ps.message.ket_message", "01170") %><%--납입처를 등록 할 고객사를 선택 하십시오--%>');

        return false;

    }


    function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
    }

    function onKeyPress() {

        if (window.event) {
            if (window.event.keyCode == 13) erpProjectNoSearch();
        } else return;

    }
    //document.onkeypress = onKeyPress;

    //프로젝트 팝업 검색
    function doSearchPJT(oTable_id) {
        part_table_obj = document.getElementById(oTable_id);
        openWindow("/plm/jsp/project/ProjectSelectPage.jsp?isOpen=false&isModal=false&mode=one&invokeMethod=ProjectInfo", "SelectProject", 1024, 768);
    }

    function ProjectInfo(mpArr){
        var arr = mpArr[0];
        projectoid = arr[0];          // Project OID
        projectno = arr[1];            // 프로젝트  NO
        projectname = arr[2];          // 프로세스
        projectModel = arr[10];          // 모텔 차종
        projectLevel = arr[11];          // 난이도
        projectCustomer = arr[12];        // 발주처
        projectMakecompany = arr[13];      // 생산조직
        projectProduct = arr[14];        // 제품군
        document.forms[0].dsProjectName.value = projectname
        document.forms[0].dsProjectOid.value = projectoid

        //모델/차종
        if( projectModel.length >0){
            for( var i = 0 ; document.forms[0].modelcode.options.length ; i ++){
                if(document.forms[0].modelcode.options[i].value == projectModel){
                    document.forms[0].modelcode.options[i].selected = true;
                    document.forms[0].modelcode.disabled =true;
                    break;
                }
            }
        }

    }

    function codeDelete(strvalue, strdisplay) {
        document.forms[0].dsProjectName.value = "";
        document.forms[0].dsProjectOid.value = "";
        document.forms[0].modelcode.disabled =false;
        document.forms[0].levelcode.disabled =false;
        document.forms[0].productcode.disabled =false;
        document.forms[0].customercode.disabled =false;
        document.forms[0].makecompanycode.disabled =false;
    }
    function clearData1(){
        document.forms[0].receiptNumber.value = "";
    }

    var programRowId = "";
    
///////////////////////////////////////////
///////////자동차일정 선택////////////////
///////////////////////////////////////////
        function selectProgram( rowId ){
            programRowId = rowId;

        var param ="?sid="+rowId;
        //자동차일정 oid
/*
        var carTypeOid =  getParamValue("carTypeOid"+rowId);
        if(carTypeOid.length > 0) {
            if(param.length > 0) {
                param += "&";
            }
            param += carTypeOid;
        }
*/
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

        var url = "/plm/jsp/project/SelectProgram.jsp"+param+"&invokeMethod=addProgram";
        
        window.open(url,'',"top=100px, left=100px, height=500px, width=720px");

        //openSameName(url,"760","600","status=no,scrollbars=yes,resizable=no");
/*         attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:500px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        } */


    }

    function addProgram(attache)
    {
    	var rowId = programRowId;
        if(attache.length == 0) {
            return;
        }
        var productInfo = document.getElementById("productInfo");
        //tableRows = productInfo.rows.length;
        //for(var i = 0 ; i < tableRows ; i++){
        //  productInfo.deleteRow(i);
        //}


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
        if(paramObj) paramObj.value = "";
    }

    // 제품 정보 추가
    function createProduct(){
            var tBody = document.getElementById("productInfo");

            var rowId = genRowId();

            tableRows = tBody.rows.length;
            newTr = tBody.insertRow(tableRows);
            newTr.id = rowId;
            
            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"#\" onclick=\"$(this).parent().parent().remove();\"><img src='/plm/portal/images/b-minus.png' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
            
            //제품명
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input type='text' name='pName' class='txt_field' style='width:95%' id='pName"+rowId+"'>";

            //차종
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<div style='width:145;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><span name='carName' id='carName"+rowId+"'></span></nobr>&nbsp;<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
            
            //조립처
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            var selectStr = "<select id=\"assemblyPlaceType" + rowId + "\" name=\"assemblyPlaceType\" style=\"width:70px\" class=\"fm_jmp\" onChange=\"javascript:changeAssembly(" + rowId + ")\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"사내\"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"외주\"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option></select> ";
            selectStr += "<input id=\"assemblyPlaceName" + rowId + "\" type=text name=\"assemblyPlaceName\" style=\"width:90px\" class=\"txt_field\">";
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
            newTd.innerHTML = "<span name='usageT' id='usageT"+rowId+"' style='width:25px'><input type='text' class='txt_fieldR'  style='width:90%;border:0' ReadOnly name='usageT' value=''></span>";
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
    
    function onDeleteTableRow2(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    targetTable.deleteRow(i+2);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                targetTable.deleteRow(2);
                return;
            }
        }
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

    function onDeleteMember(){
        document.forms[0].devUserDept.value = "";
        document.forms[0].devDeptOid.value = "";
    }
    //
    function checkAll() {
        form = document.forms[0];
        if(form.oid == null) {
            return;
        }

        len = form.oid.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                form.oid[i].checked = form.chkAll.checked;
            }
        }
        else {
            form.oid.checked = form.chkAll.checked;
        }
    }

    function selectDr(){
    //개발 검토  : developmentStep=R
    //제품   : developmentStep=D
        //var url="/plm/jsp/project/SelectDevRequestPop.jsp?method=selDr&developmentStep=R";
        //추가 팝업구분 파라미터 추가
        var url="/plm/jsp/dms/SearchDevRequestPop.jsp?method=selDr&developmentStep=R&mode=one&isPopup=yes";
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
    
    var targetReviewTableId = "productInfo";
    function onTargetSet(req){

        var xmlDoc = req.responseXML;
        //var showElements = xmlDoc.selectNodes("//message");
        var msg = xmlDoc.getElementsByTagName("l_message")[0];

        if(msg == 'false' && xmlDoc.getElementsByTagName("l_result") != null && getTagText(xmlDoc.getElementsByTagName("l_result")[0]) != ""){
            alert(getTagText(xmlDoc.getElementsByTagName("l_result")[0]));
            return;
        }
        if(msg == 'false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
            return;
        }

        //var showElements = xmlDoc.selectNodes("//data_info");
        var l_drNumber = xmlDoc.getElementsByTagName("l_drNumber");
        var l_drKeyOid = xmlDoc.getElementsByTagName("l_drKeyOid");

        var l_creatorName = xmlDoc.getElementsByTagName("l_creatorName");
        var l_creatorOid = xmlDoc.getElementsByTagName("l_creatorOid");
        var l_name = xmlDoc.getElementsByTagName("l_name");
        var l_proposalSubmitDate = xmlDoc.getElementsByTagName("l_proposalSubmitDate");
        var l_costSubmitDate = xmlDoc.getElementsByTagName("l_costSubmitDate");



        var form = document.forms[0];
        form.drNumber.value = decodeURIComponent(getTagText(l_drNumber[0]));
        form.drKeyOid.value = decodeURIComponent(getTagText(l_drKeyOid[0]));

        //영업 담당자/ 부서
        form.tempsalesUser.value = decodeURIComponent(getTagText(l_creatorName[0]));
        form.salesUser.value = decodeURIComponent(getTagText(l_creatorOid[0]));

        // 제품명
        form.projectName.value = decodeURIComponent(getTagText(l_name[0]));

        // 제안서 제출여부
        form.proposalDate.value = decodeURIComponent(getTagText(l_proposalSubmitDate[0]));

        // 개발원가 제출일자
        form.estimateDate.value = decodeURIComponent(getTagText(l_costSubmitDate[0]));

        //var showElements_ci = xmlDoc.selectNodes("//data_custinfo");
        var l_custOid = xmlDoc.getElementsByTagName("l_custOid");
        var l_custName = xmlDoc.getElementsByTagName("l_custName");

        // 최종 사용처
        // 기존 최종 사용처를 삭제한다.
        deleteLastUsingBuyer();
        var custOid = false;
        var custName = false;
        var pos = form.CUSTOMEREVENTOid.length;
        for(var i = 0; i < l_custOid.length; i++) {
            custOid = decodeURIComponent(getTagText(l_custOid[i]));
            custName = decodeURIComponent(getTagText(l_custName[i]));
            if(isDuplicateCheckBox('CUSTOMEREVENTOid',custOid)) {
                continue;
            }
            
            form.CUSTOMEREVENTOid.length = pos+i+1;
            form.CUSTOMEREVENTOid.options[pos+i].value = custOid;
            form.CUSTOMEREVENTOid.options[pos+i].text = custName;
        }

        //의뢰처를 삭제한다.
        deleteRequestBuyer();
        //var showElements_si = xmlDoc.selectNodes("//data_subinfo");
        var l_subOid = xmlDoc.getElementsByTagName("l_subOid");
        var l_subName = xmlDoc.getElementsByTagName("l_subName");
        
        var subOid = false;
        var subName = false;
        pos = form.sOid.length;
        for(var i = 0; i < l_subOid.length; i++) {
            subOid = decodeURIComponent(getTagText(l_subOid[i]));
            subName = decodeURIComponent(getTagText(l_subName[i]));

            if(isDuplicateCheckBox('oid', subOid)) {
                continue;
            }
            
            form.sOid.length = pos+i+1;
            form.sOid.options[pos+i].value= subOid;
            form.sOid.options[pos+i].text = subName;
        }

        //var showElements_product = xmlDoc.selectNodes("//data_productinfo");
        var l_partOid = xmlDoc.getElementsByTagName("l_partOid");
        var l_partName = xmlDoc.getElementsByTagName("l_partName");
        var l_appliedRegion = xmlDoc.getElementsByTagName("l_appliedRegion");
        var l_buyerAcceptPrince = xmlDoc.getElementsByTagName("l_buyerAcceptPrince");
        var l_targetPrice = xmlDoc.getElementsByTagName("l_targetPrice");
        var l_targetRate = xmlDoc.getElementsByTagName("l_targetRate");

        var lpartOid = false;
        var lpartName = false;
        var lappliedRegion = false;
        var lbuyerAcceptPrince = false;
        var ltargetPrice = false;
        var ltargetRate = false;
        for(var p = 0; p < l_partOid.length; p++) {
            lpartOid =  decodeURIComponent(getTagText(l_partOid[p]));
            lpartName =  decodeURIComponent(getTagText(l_partName[p]));
            lappliedRegion =  decodeURIComponent(getTagText(l_appliedRegion[p]));
            lbuyerAcceptPrince =  decodeURIComponent(getTagText(l_buyerAcceptPrince[p]));
            ltargetPrice =  decodeURIComponent(getTagText(l_targetPrice[p]));
            ltargetRate =  decodeURIComponent(getTagText(l_targetRate[p]));

            var tBody = document.getElementById("productInfo");
            var rowId = genRowId();

            tableRows = tBody.rows.length;
            newTr = tBody.insertRow(tableRows);

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"#\" onclick=\"$(this).parent().parent().remove();\"><img src='/plm/portal/images/b-minus.png' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
            
            //제품번호
           /*  newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "60";
            newTd.innerHTML = "<input type='text' name='pNum' class='txt_field'  style='width:55' id='pNum"+rowId+"' value='' >"; */

            //제품명
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input type='text' name='pName' class='txt_field'  style='width:95%' id='pName"+rowId+"' value='"+lpartName+"'>";

            //var showElements_Car = xmlDoc.selectNodes("//data_carinfo");
            var l_partKeyOid = xmlDoc.getElementsByTagName("l_partKeyOid");
            var l_carTypeOid = xmlDoc.getElementsByTagName("l_carTypeOid");
            var l_carTypeCode = xmlDoc.getElementsByTagName("l_carTypeCode");
            var l_y1 = xmlDoc.getElementsByTagName("l_y1");
            var l_y2 = xmlDoc.getElementsByTagName("l_y2");
            var l_y3 = xmlDoc.getElementsByTagName("l_y3");
            var l_y4 = xmlDoc.getElementsByTagName("l_y4");
            var l_y5 = xmlDoc.getElementsByTagName("l_y5");
            var l_y6 = xmlDoc.getElementsByTagName("l_y6");
            var l_y7 = xmlDoc.getElementsByTagName("l_y7");
            var l_y8 = xmlDoc.getElementsByTagName("l_y8");
            var l_y9 = xmlDoc.getElementsByTagName("l_y9");
            var l_y10 = xmlDoc.getElementsByTagName("l_y10");
            var l_usage = xmlDoc.getElementsByTagName("l_usage");
            var l_optionRate = xmlDoc.getElementsByTagName("l_optionRate");

            var lcarTypeOid = "";
            var lcarTypeCode = "";
            var ly1 =0;
            var ly2 =0;
            var ly3 =0;
            var ly4 =0;
            var ly5 =0;
            var ly6 =0;
            var ly7 =0;
            var ly8 =0;
            var ly9 =0;
            var ly10 =0;
            var lusage =0;
            var loptionRate =0;
            var carNames  = "";

            var total_ly1 = 0;
            var total_ly2 = 0;
            var total_ly3 = 0;
            var total_ly4 = 0;
            var total_ly5 = 0;
            var total_ly6 = 0;
            var total_ly7 = 0;
            var total_ly8 = 0;
            var total_ly9 = 0;
            var total_ly10 = 0;
            var total_lusage = 0;
            var total_loptionRate = 0;

            var key = 0;
            for(var i = 0; i < l_carTypeOid.length; i++) {
                var checkpartKey = decodeURIComponent(getTagText(l_partKeyOid[i]));
                    if(lpartOid == checkpartKey){
                    key++;
                }
            }
            var keyCheck = 1;
            for(var i = 0; i < l_carTypeOid.length; i++) {
                var checkpartKey = decodeURIComponent(getTagText(l_partKeyOid[i]));

                if(lpartOid == checkpartKey){
                    if(key == 1){
                            carNames = carNames + decodeURIComponent(getTagText(l_carTypeCode[i]));
                    }else if(key == keyCheck){
                            carNames = carNames + decodeURIComponent(getTagText(l_carTypeCode[i]));
                    }else{
                            carNames = carNames + decodeURIComponent(getTagText(l_carTypeCode[i]))+", ";
                    }


                    lusage = Math.ceil(decodeURIComponent(getTagText(l_usage[i])) );
                    loptionRate = Math.ceil(decodeURIComponent(getTagText(l_optionRate[i])) );
                    ly1 = Math.ceil(decodeURIComponent(l_y1[i].text) ) * lusage * ( loptionRate/100);
                    ly2 = Math.ceil(decodeURIComponent(l_y2[i].text) ) * lusage * ( loptionRate/100);
                    ly3 = Math.ceil(decodeURIComponent(l_y3[i].text) ) * lusage * ( loptionRate/100);
                    ly4 = Math.ceil(decodeURIComponent(l_y4[i].text) ) * lusage * ( loptionRate/100);
                    ly5 = Math.ceil(decodeURIComponent(l_y5[i].text) ) * lusage * ( loptionRate/100);
                    ly6 = Math.ceil(decodeURIComponent(l_y6[i].text) ) * lusage * ( loptionRate/100);
                    ly7 = Math.ceil(decodeURIComponent(l_y7[i].text) ) * lusage * ( loptionRate/100);
                    ly8 = Math.ceil(decodeURIComponent(l_y8[i].text) ) * lusage * ( loptionRate/100);
                    ly9 = Math.ceil(decodeURIComponent(l_y9[i].text) ) * lusage * ( loptionRate/100);
                    ly10 = Math.ceil(decodeURIComponent(l_y10[i].text) ) * lusage * ( loptionRate/100);
                    total_ly1 = total_ly1 + ly1;
                    total_ly2 = total_ly2 + ly2;
                    total_ly3 = total_ly3 + ly3;
                    total_ly4 = total_ly4 + ly4;
                    total_ly5 = total_ly5 + ly5;
                    total_ly6 = total_ly6 + ly6;
                    total_ly7 = total_ly7 + ly7;
                    total_ly8 = total_ly8 + ly8;
                    total_ly9 = total_ly9 + ly9;
                    total_ly10 = total_ly10 + ly10;
                    if(total_lusage < lusage){
                        total_lusage = lusage;
                    }

                    keyCheck++;
                }
            }

            //차종
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<div style='width:145;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>";
            newTd.innerHTML += "<span name='carName' id='carName"+rowId+"' value='"+carNames+"'>"+carNames+"</span></nobr>&nbsp;";
            newTd.innerHTML += "<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";

            newTd.innerHTML  += "<input type='hidden' name='y1T' value='"+Math.ceil(total_ly1)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y2T' value='"+Math.ceil(total_ly2)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y3T' value='"+Math.ceil(total_ly3)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y4T' value='"+Math.ceil(total_ly4)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y5T' value='"+Math.ceil(total_ly5)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y6T' value='"+Math.ceil(total_ly6)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y7T' value='"+Math.ceil(total_ly7)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y8T' value='"+Math.ceil(total_ly8)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y9T' value='"+Math.ceil(total_ly9)+"'>";
            newTd.innerHTML  += "<input type='hidden' name='y10T' value='"+Math.ceil(total_ly10)+"'>";

            //적용부위
            /* newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.align = "center";
            newTd.innerHTML = "<input type='text' name='areas' class='txt_field'  style='width:65' id='areas"+rowId+"' value='"+lappliedRegion+"'>"; */
          //조립처
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            var selectStr = "<select id=\"assemblyPlaceType" + rowId + "\" name=\"assemblyPlaceType\" style=\"width:70px\" class=\"fm_jmp\" onChange=\"javascript:changeAssembly(" + rowId + ")\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"사내\"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"외주\"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option></select> ";
            selectStr += "<input id=\"assemblyPlaceName" + rowId + "\" type=text name=\"assemblyPlaceName\" style=\"width:90px\" class=\"txt_field\">";
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
            newTd.innerHTML = "<span name='usageT' id='usageT"+rowId+"' style='width:25px'><input type='text' class='txt_fieldR'  style='width:25;border:0' ReadOnly name='usageT' value='"+Math.ceil(total_lusage)+"'></span>";
            var usageT = document.getElementById("usageT"+rowId);

            for(var y = 0; y < l_carTypeOid.length; y++) {
                var checkpartKey = decodeURIComponent(getTagText(l_partKeyOid[y]));
                var carTypeOid = decodeURIComponent(getTagText(l_carTypeOid[y]));
                lusage = Math.ceil(decodeURIComponent(getTagText(l_usage[y])) );
                loptionRate = Math.ceil(decodeURIComponent(getTagText(l_optionRate[y])) );
                ly1 = Math.ceil(decodeURIComponent(getTagText(l_y1[y])) );
                ly2 = Math.ceil(decodeURIComponent(getTagText(l_y2[y])) );
                ly3 = Math.ceil(decodeURIComponent(getTagText(l_y3[y])) );
                ly4 = Math.ceil(decodeURIComponent(getTagText(l_y4[y])) );
                ly5 = Math.ceil(decodeURIComponent(getTagText(l_y5[y])) );
                ly6 = Math.ceil(decodeURIComponent(getTagText(l_y6[y])) );
                ly7 = Math.ceil(decodeURIComponent(getTagText(l_y7[y])) );
                ly8 = Math.ceil(decodeURIComponent(getTagText(l_y8[y])) );
                ly9 = Math.ceil(decodeURIComponent(getTagText(l_y9[y])) );
                ly10 = Math.ceil(decodeURIComponent(getTagText(l_y10[y])));


                if(lpartOid == checkpartKey){
                usageT.innerHTML  += "<input type='hidden' name='optOid"+rowId+"' id='optOid"+rowId+"' value='"+carTypeOid+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y1"+rowId+"' value='"+ly1+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y2"+rowId+"' value='"+ly2+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y3"+rowId+"' value='"+ly3+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y4"+rowId+"' value='"+ly4+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y5"+rowId+"' value='"+ly5+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y6"+rowId+"' value='"+ly6+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y7"+rowId+"' value='"+ly7+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y8"+rowId+"' value='"+ly8+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y9"+rowId+"' value='"+ly9+"'>";
                usageT.innerHTML  += "<input type='hidden' name='y10"+rowId+"' value='"+ly10+"'><input type='hidden' name='carName' id='carName"+rowId+"' value='"+decodeURIComponent(getTagText(l_carTypeCode[y]))+"'>";
                usageT.innerHTML  += "<input type='hidden' name='sum' id='sum"+rowId+"' value=''>";
                usageT.innerHTML  += "<input type='hidden' name='usage"+rowId+"' id='usage"+rowId+"' value='"+ lusage +"'>";
                usageT.innerHTML  += "<input type='hidden' name='optionRate"+rowId+"' id='optionRate"+rowId+"' value='"+loptionRate +"'>";
                usageT.innerHTML  += "<input type='hidden' name='count"+rowId+"' id='count"+rowId+"' value='"+ y +"'>";
                }

            }

        }

    }

    function checkNumber(str) {
        var flag=true;
        if (str.length > 0) {
            for (var i = 0; i < str.length; i++) {
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
            for (var i = 0; i < str.length; i++) {
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

    function tagSet(obj){
        //obj.style.border = "1";
        //obj.style.borderColor="#FF0000";
        obj.style.borderColor="#A4511D";
    }
    function changeDate(obj){
        var devcode = obj.value;
        if(devcode == "DEV002"){
            saleId.style.display = "block";
        }else{
            saleId.style.display = "none";
        }
    }
    
    function insertLastUsingBuyer() {
        
        window.open("/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode=multi&designTeam=&invokeMethod=selectMultiCustomer", "CUSTOMEREVENT", "top=100px, left=100px, height=800px, width=1200px");
        
    }
    
  //최종사용처 검색화면 오픈하여 선택된 최종사용처를 입력한다.
    function selectMultiCustomer(arrObj) {
        var fm = document.prodForm;
        var pos = fm.CUSTOMEREVENTOid.length;
        var subArr;
        
        if(typeof arrObj == "undefined" || arrObj == null) {
          return;
        }
        
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
    
    function insertRequestBuyer(){
        window.open("/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SUBCONTRACTOR&command=select&mode=multi&viewType=&disable=&invokeMethod=selectMultiSubContractor", "MultiSubContractor", "top=100px, left=100px, height=800px, width=1200px");
    }
    
    //의뢰처 검색화면 오픈하여 선택된 의뢰처를 입력한다.
    function selectMultiSubContractor(arrObj) {

      var fm = document.prodForm
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
    
    function developePurposeChange(){
        
        if ( $("#developePurpose1").val() != null ) {
            var choiceCode  = $("#developePurpose1").val();
            
            if(choiceCode != ''){
                $("#developePurpose2").empty().data('options');
                
                $.ajax({
                    url : "/plm/ext/code/getChildCodeList.do?codeType=DEVELOPANDREVIEWGOAL&parentCode="+choiceCode,
                    type : "POST",
                    dataType : 'json',
                    async : false,
                    success: function(data) {
                        $("#developePurpose2").append("<option value=''>선택</option>");
                        $.each(data, function() {
                            $("#developePurpose2").append("<option value='"+this.code+"'>"+ this.value +"</option>");
                        });
                        
                        if(choiceCode == 'DP2' || choiceCode == 'DP3'){//4M 또는 연구 일때 개발구분 선택필요없음
                        	
                            $("#developenttype").val("-");
                            
                            $("#developenttype").prop("disabled", true);

                        }else{
                            $("#developenttype").prop("disabled", false);
                        }
                        
                        
                    }
                });
            }else{
                $("#developePurpose2").empty().data('options');
                $("#developePurpose2").append("<option value=''>선택</option>");
            }
        }
     }
    
    function setPartClaz(returnValue){
    	$('[name=productTypeLevel2]').val(returnValue[0].id);//oid
        $('[name=productTypeLevel2Name]').val(returnValue[0].name);//kr name
    }
    
    $(document).ready(function(){
        //영업담당자 suggest
        SuggestUtil.bind('USER', 'tempsalesUser', 'salesUser');
        //부서 suggest
        SuggestUtil.bind('DEPARTMENT', 'devUserDept', 'devDeptOid');
        //PM suggest
        SuggestUtil.bind('USER', 'tempdevUser', 'devUser');
        CalendarUtil.dateInputFormat('proposalDate');
        CalendarUtil.dateInputFormat('estimateDate');
        CalendarUtil.dateInputFormat('planStartDate');//개발시작일
        
        
        $("select[name='developePurpose1'] option[value='DP2']").remove();//검토프로젝트는 [4M] 필요없다
    });
    
    
//-->
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form method="post" name="prodForm">
        <!-- hidden begin -->
        <input type="hidden" name="tempProjectNo" value=""> 
        <input type="hidden" name="lifecycle" value=""> 
        <input type="hidden" name="pwlinkOid" value="<%=pwlinkOid%>">
        <input type="hidden" name="projectTypeName" value="<%=projectType%>사업부">
        <!-- hidden end -->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top" height="43">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00723")%><%--검토 프로젝트 등록--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="space5"></td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:actionSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310")%><%--등록--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:window.close();" class="btn_blue">취소</a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                       <tr>
                            <td width="140" class="tdblueL">개발목적<span class="red">*</span></td>
                            <td class="tdwhiteL0">
                                <select name="developePurpose1" id="developePurpose1" class="fm_jmp" style="width: 98%" onChange="developePurposeChange();">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DEVELOPANDREVIEWGOAL");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select>
                            </td>
                            <td class="tdwhiteL0" colspan="2">
                                <select name="developePurpose2" id="developePurpose2" class="fm_jmp" style="width: 99%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02506")%><%--접수번호--%></td>
                            <td width="240" class="tdwhiteL">
                                <input type=text name="drNumber" class="txt_fieldRO" style="width: 75%" readonly>
                                <input type=hidden name="drKeyOid" value=""> 
                                <a href="JavaScript:selectDr();"><img src="/plm/portal/images/icon_5.png" border=0></a> 
                                <a href="JavaScript:CommonUtil.deleteValue('drNumber','drKeyOid')"><img src="/plm/portal/images/icon_delete.gif" border=0></a></td>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625")%><%--개발구분--%>
                                <span class="red">*</span></td>
                            <td width="240" class="tdwhiteL0"><select name="developenttype" id="developenttype" class="fm_jmp" style="width: 98%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DEVELOPENTTYPE");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("code")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                                    <option value="-">-</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578")%><%--제품구분--%>
                                <span class="red">*</span></td>
                            <td width="240" class="tdwhiteL0" colspan='3'>
                                <input type="text" name="productTypeLevel2Name" class="txt_field" style="width: 30%">
                                <input type="hidden" name="productTypeLevel2" value="">
                                <a href="javascript:SearchUtil.selectOnePartClazPopUp('setPartClaz','onlyLeaf=Y&openAll=N&depthLevel2=Y');">
                                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                <a href="javascript:CommonUtil.deleteValue('productTypeLevel2','productTypeLevel2Name');">
                                <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
<%--                             <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01201")%>담당부서<span class="red">*</span></td>
                            <td width="240" class="tdwhiteL0">
                                <input type=text name="devUserDept" style="width: 75%" size=8 class='txt_field'>
                                <input type=hidden name="devDeptOid"> 
                                <a href="javascript:addDepartment2('devDept');"><img src="/plm/portal/images/icon_5.png" border=0></a> 
                                <a href="javascript:onDeleteMember();"><img src="/plm/portal/images/icon_delete.gif" border=0></a> 
                            </td> --%>
                        </tr>
                        <tr>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02139")%><%--영업담당자--%></td>
                            <td width="240" class="tdwhiteL">
                                <input style="width: 75%" type='text' size=8 name='tempsalesUser' value='' class='txt_field' id='tempsalesUser'>
                                <input type='hidden' id='salesUser' name='salesUser' value=''> 
                                <a href="javascript:addRoleMemberDept('salesUser');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                                <a href="javascript:CommonUtil.deleteValue('salesUser','tempsalesUser');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                            <td width="150" class="tdblueL">PM <span class="red">*</span></td>
                            <td width="240" class="tdwhiteL0">
                                <input type="text" name="tempdevUser" id="tempdevUser" class="txt_field" style="width: 75%"> 
                                <input type='hidden' name='devUser' id="devUser">
                                <a href="javascript:;" onClick="javascript:addRoleMember();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                                <a href="javascript:CommonUtil.deleteValue('tempdevUser','devUser');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            </td>
                        </tr>
                        <tr>
                            <td width="150" class="tdblueL">Project Name<span class="red">*</span></td>
                            <td width="" colspan=3 class="tdwhiteL0"><input type=text name="projectName" class="txt_field"
                                style="width: 100%"></td>
                        </tr>
                        <tr>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00663")%><%--개발제안서 제출일--%><!-- <span class="red">*</span> --></td>
                            <td width="240" class="tdwhiteL">
                                <input name="proposalDate" value="" class="txt_field" style="width: 75%" />
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('proposalDate');" style="cursor: hand;">
                            </td>
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00649")%><%--개발원가 제출일--%><!-- <span class="red">*</span> --></td>
                            <td width="240" class="tdwhiteL0">
                                <input name="estimateDate" value="" class="txt_field" style="width: 75%"/>
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('estimateDate');" style="cursor: hand;">
                            </td>
                        </tr>
                        <tr>
                            <td width="150" class="tdblueL">Rank<span class="red">*</span></td>
                            <td width="240" class="tdwhiteL"><select name="rank" class="fm_jmp" style="width: 98%">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "RANK");
                                        if("자동차".equals(projectType)){
                                            parameter.put("code",     "RAN1000");                                                   
                                        }else if("전자".equals(projectType)){
                                            parameter.put("code",     "RAN2000");                                                    
                                        }else{
                                            parameter.put("code",     "RAN3000");                                                    
                                        }
                                        parameter.put("depthLevel", "child");
                                        //parameter.put("name", projectType);
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {
                                    %>
                                    <option value="<%=numCode.get(i).get("oid")%>"><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select></td>
                            <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817","")%><!-- 계획시작일 --><span class="red">*</span></td>
                            <td class="tdwhiteL0" colspan="3"><input type="text" name="planStartDate" class="txt_field"
                                style="width: 75%"> <img src="/plm/portal/images/icon_delete.gif" border="0"
                                onclick="javascript:CommonUtil.deleteDateValue('planStartDate');" style="cursor: hand;"></td>
                        </tr>
                        <tr>
                            <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%--최종사용처--%></td>
                            <td class="tdwhiteL">
                                <select name="CUSTOMEREVENTOid" style="width: 75%;" size=2 multiple></select>
                                 <a href="javascript:insertLastUsingBuyer()"><img src="../../portal/images/icon_5.png" border="0" align="top"></a>
                                 <a href="javascript:deleteLastUsingBuyer()"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                            <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                            <td class="tdwhiteL0"><select name="sOid" style="width: 75%;" size=2 multiple></select>
                                <a href="javascript:insertRequestBuyer()"><img src="../../portal/images/icon_5.png" border="0" align="top"></a> 
                                <a href="javascript:deleteRequestBuyer()"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></td>
                            <td align="right"></td>
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
                                <a href="javascript:createProduct();"><img src="/plm/portal/images/b-plus.png"></a>
                            </td>
                            <td width="150px" class="tdblueM">Part Name</td>
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
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03">WBS<span class="red">*</span></td>
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
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" id="templatetable">
                        <tr>
                            <td class="tdblueM" width="30"><a href="javascript:onProjectTemplate()"><img src="/plm/portal/images/b-plus.png"></a></td>
                            <td class="tdblueM">WBS</td>
                            <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01111")%><%--기간--%></td>
                            <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "01335")%><%--등록일--%></td>
                            <td class="tdblueM0" width="100"><%=messageService.getString("e3ps.message.ket_message", "02852")%><%--최종수정일--%></td>
                        </tr>
                    </table>
                 </td>
            </tr>
        </table>
   </form>
</body>
</html>
