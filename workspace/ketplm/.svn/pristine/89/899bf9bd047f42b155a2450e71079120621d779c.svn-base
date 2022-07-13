<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.*, java.sql.*"%>
<%@page import = "wt.fc.*,
                          wt.org.*,
                          wt.project.Role,
                          wt.query.*,
                          wt.session.*,
                          wt.team.*"%>
<%@page import = "e3ps.common.code.*,
                          e3ps.common.jdf.config.Config,
                          e3ps.common.jdf.config.ConfigImpl,
                          e3ps.common.query.SearchUtil,
                          e3ps.common.util.*,
                          e3ps.common.web.*,
                          e3ps.ews.dao.PartnerDao,
                          e3ps.groupware.company.*,
                          e3ps.project.*,
                          e3ps.project.beans.*,
                          e3ps.project.outputtype.OEMProjectType,
                          e3ps.common.db.DBCPManager"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String oid = request.getParameter("oid");
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);

    boolean isPM = ProjectUserHelper.manager.isPM(project);
    
    String initType = request.getParameter("initType");
    String projectConsignment = request.getParameter("projectConsignment");
    String projectSite = request.getParameter("projectSite");
    String projectAcceptanceType = request.getParameter("projectAcceptanceType");
    String projectSaleType = request.getParameter("projectSaleType");
    String projectType = "2";
    String pmoid = request.getParameter("pmoid");
    String tempOid = request.getParameter("tempOid");
    String product = request.getParameter("product");
    String pwlinkOid = request.getParameter("pwlinkOid");
    if(pwlinkOid == null){
        pwlinkOid = "";
    }

    if(product == null){
        product ="";
    }

    ProjectManager pm = null;
    if(pmoid == null){
        pmoid = "";
    }else{
        pm = (ProjectManager)CommonUtil.getObject(pmoid);
    }

    if(tempOid == null){
        tempOid = "";
    }

    String oemTypeValue = "";
    if(pm != null){
        OEMProjectType ot = pm.getOemType();
        oemTypeValue = CommonUtil.getOIDString(ot);
    }

    Config conf = ConfigImpl.getInstance();

    String lifecycle = conf.getString("lifecycle.newproject");

    if(initType == null) {
        initType = "produceproject";
    }

    if(projectConsignment == null) {
        projectConsignment = "";
    }

    if(projectSite == null) {
        projectSite = "";
    }

    if(projectAcceptanceType == null) {
        projectAcceptanceType = "";
    }

    if(projectSaleType == null) {
        projectSaleType = "";
    }

    Vector vecTeamStd = null;
    TeamTemplate tempTeam = null;
    if("자동차 사업부".equals( ((ProductProject)project).getTeamType() )){
        tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
    }else{
        tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
    }

    if(tempTeam != null) {
        vecTeamStd = tempTeam.getRoles();
    }

    String tmpTitle = "";
    Vector tMap = null;

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

    WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
    PeopleData pd = new PeopleData(sessionUser);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE><%=messageService.getString("e3ps.message.ket_message", "07123") %><%--제품정보 수정--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 5px;
}
-->
</style>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/common/context.jsp"%>
<script language="JavaScript">
<!--
// str은 0~9까지 숫자만 가능하다.
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
    
    function saveCheck(flag){
    	saveFlag  = flag;
    }
    var saveFlag = true;
    //Create Function
    function onSave(){
/*         if(!saveFlag){
        	alert("중복된 DieNo가 존재합니다.");
        	return;
        } */
        
        if(iframe.dupDieNoCheck()){
        	alert("중복된 DieNo가 존재합니다.");
            return;
        }
    	
        if(!checkValidate()) {
            return;
        }
        $("#iteminfoList").children().remove();
        var param = "";
        //item 현황

        var tBody = document.getElementById("iteminfoList");
        var rowId = genRowId();
        tableRows = tBody.rows.length;
        newTr = tBody.insertRow(tableRows);
        newTr.id = rowId;

        if(iframe.document.forms[0].itemType) {
            if(iframe.document.forms[0].itemType.value == undefined) {
                var moldItemOid =  getIframeParamValue("moldItemOid");
                var itemType =  getIframeParamValue("itemType");
                var moldProductType =  getIframeParamValue("moldProductType");
                var moldPartNo =  getIframeParamValue("moldPartNo");
                var partName =  getIframeParamValue("partName");
                var dieNo =  getIframeParamValue("dieNo");
                var costOid =  getIframeParamValue("costOid");
                var moldType =  getIframeParamValue("moldType");
                var cVPitch =  getIframeParamValue("cVPitch");
                var cTSPM =  getIframeParamValue("cTSPM");
                var making =  getIframeParamValue("making");
                var makingPlaceTwo =  getIframeParamValue("makingPlaceTwo");                
                var productionPlace =  getIframeParamValue("productionPlace");
                var productionPlaceTwo =  getIframeParamValue("productionPlaceTwo");
                var materials =  getIframeParamValue("materials");
                var poidvalue =  getIframeParamValue("poidvalue");
                var height =  getIframeParamValue("height");
                var wide =  getIframeParamValue("wide");
                var newType =  getIframeParamValue("newType");
                var etc =  getIframeParamValue("etc");
            }else {
                //name value set
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='moldItemOid' value='"+iframe.document.forms[0].moldItemOid.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='itemType' value='"+iframe.document.forms[0].itemType.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='moldPartNo' value='"+iframe.document.forms[0].moldPartNo.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='moldProductType' value='"+iframe.document.forms[0].moldProductType.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='partName' value='"+iframe.document.forms[0].partName.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='dieNo' value='"+iframe.document.forms[0].dieNo.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='costOid' value='"+iframe.document.forms[0].costOid.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='moldType' value='"+iframe.document.forms[0].moldType.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='cVPitch' value='"+iframe.document.forms[0].cVPitch.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='cTSPM' value='"+iframe.document.forms[0].cTSPM.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='making' value='"+iframe.document.forms[0].making.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                
                if(iframe.document.forms[0].makingPlaceTwo != null){
                	newTd.innerHTML += "<input type='hidden' name='makingPlaceTwo' value='"+iframe.document.forms[0].makingPlaceTwo.value+"'>";
                    newTd = newTr.insertCell(newTr.cells.length);   
                }
                
                newTd.innerHTML += "<input type='hidden' name='productionPlace' value='"+iframe.document.forms[0].productionPlace.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                
                if(iframe.document.forms[0].productionPlaceTwo != null){
                    newTd.innerHTML += "<input type='hidden' name='productionPlaceTwo' value='"+iframe.document.forms[0].productionPlaceTwo.value+"'>";
                    newTd = newTr.insertCell(newTr.cells.length);
                }
                newTd.innerHTML += "<input type='hidden' name='materials' value='"+iframe.document.forms[0].materials.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='poidvalue' value='"+iframe.document.forms[0].poidvalue.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='height' value='"+iframe.document.forms[0].height.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='wide' value='"+iframe.document.forms[0].wide.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='newType' value='"+iframe.document.forms[0].newType.value+"'>";
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.innerHTML += "<input type='hidden' name='etc' value='"+iframe.document.forms[0].etc.value+"'>";

                
                var moldItemOid =  getIframeParamValue("moldItemOid");
                var itemType =  getIframeParamValue("itemType");
                var moldProductType =  getIframeParamValue("moldProductType");
                var moldPartNo =  getIframeParamValue("moldPartNo");
                var partName =  getIframeParamValue("partName");
                var dieNo =  getIframeParamValue("dieNo");
                var costOid =  getIframeParamValue("costOid");
                var moldType =  getIframeParamValue("moldType");
                var cVPitch =  getIframeParamValue("cVPitch");
                var cTSPM =  getIframeParamValue("cTSPM");
                var making =  getIframeParamValue("making");
                var makingPlaceTwo =  getIframeParamValue("makingPlaceTwo");
                var productionPlace =  getIframeParamValue("productionPlace");
                var productionPlaceTwo =  getIframeParamValue("productionPlaceTwo");
                var materials =  getIframeParamValue("materials");
                var poidvalue =  getIframeParamValue("poidvalue");
                var height =  getIframeParamValue("height");
                var wide =  getIframeParamValue("wide");
                var newType =  getIframeParamValue("newType");
                var etc =  getIframeParamValue("etc");
                
                param += "moldItemOid=" +iframe.document.forms[0].moldItemOid.value;
                param += "&itemType=" +iframe.document.forms[0].itemType.value;
                param += "&moldProductType=" +iframe.document.forms[0].moldProductType.value;
                param += "&moldPartNo=" +iframe.document.forms[0].moldPartNo.value;
                param += "&partName=" +iframe.document.forms[0].partName.value;
                param += "&dieNo=" +iframe.document.forms[0].dieNo.value;
                param += "&costOid=" +iframe.document.forms[0].costOid.value;
                param += "&moldType=" +iframe.document.forms[0].moldType.value;
                param += "&cVPitch=" +iframe.document.forms[0].cVPitch.value;
                param += "&cTSPM=" +iframe.document.forms[0].cTSPM.value;
                param += "&making=" +iframe.document.forms[0].making.value;
                
                if(iframe.document.forms[0].makingPlaceTwo != null){
                	param += "&makingPlaceTwo=" +iframe.document.forms[0].makingPlaceTwo.value;	
                }
                
                param += "&productionPlace=" +iframe.document.forms[0].productionPlace.value;
                if(iframe.document.forms[0].productionPlaceTwo != null){
                    param += "&productionPlaceTwo=" +iframe.document.forms[0].productionPlaceTwo.value;
                }
                param += "&materials=" +iframe.document.forms[0].materials.value;
                param += "&poidvalue=" +iframe.document.forms[0].poidvalue.value;
                param += "&height=" +iframe.document.forms[0].height.value;
                param += "&wide=" +iframe.document.forms[0].wide.value;
                param += "&newType=" +iframe.document.forms[0].newType.value;
                param += "&etc=" +iframe.document.forms[0].etc.value;
               
                document.forms[0].moldItemOid.value = iframe.document.forms[0].moldItemOid.value;
                document.forms[0].itemType.value = iframe.document.forms[0].itemType.value;
                document.forms[0].moldProductType.value = iframe.document.forms[0].moldProductType.value;
                document.forms[0].moldPartNo.value = iframe.document.forms[0].moldPartNo.value;
                document.forms[0].partName.value = iframe.document.forms[0].partName.value;
                document.forms[0].dieNo.value = iframe.document.forms[0].dieNo.value;
                document.forms[0].costOid.value = iframe.document.forms[0].costOid.value;
                document.forms[0].moldType.value = iframe.document.forms[0].moldType.value;
                document.forms[0].cVPitch.value = iframe.document.forms[0].cVPitch.value;
                document.forms[0].cTSPM.value = iframe.document.forms[0].cTSPM.value;
                document.forms[0].making.value = iframe.document.forms[0].making.value;
                
                if(iframe.document.forms[0].makingPlaceTwo != null){
                    document.forms[0].makingPlaceTwo.value = iframe.document.forms[0].makingPlaceTwo.value;
                }
                
                document.forms[0].productionPlace.value = iframe.document.forms[0].productionPlace.value;
                
                if(iframe.document.forms[0].productionPlaceTwo != null){
                	document.forms[0].productionPlaceTwo.value = iframe.document.forms[0].productionPlaceTwo.value;	
                }
                
                document.forms[0].materials.value = iframe.document.forms[0].materials.value;
                document.forms[0].poidvalue.value = iframe.document.forms[0].poidvalue.value;
                document.forms[0].height.value = iframe.document.forms[0].height.value;
                document.forms[0].wide.value = iframe.document.forms[0].wide.value;
                document.forms[0].newType.value = iframe.document.forms[0].newType.value;
                document.forms[0].etc.value = iframe.document.forms[0].etc.value;
             }
        }



        //name value set
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.width = "60";
        newTd.innerHTML += "<input type='hidden' name='delItemOid' value='"+iframe.document.forms[0].delItemOid.value+"'>";
        //param += "&delItemOid=" +iframe.document.forms[0].delItemOid.value;

        if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>')) {
            return;
        }

        document.forms[0].method = "post";
        document.forms[0].command.value = "ProductInfoUpdate";
        document.forms[0].action = "/plm/jsp/project/ActionProductProject.jsp";
        //document.forms[0].submit();
        
        //제품정보 등록 이력이 있으면
        if(!isFirstUpdate && (afterProductRowCount != $('[name=pNum]').length || iframe.afterItemRowCount != iframe.$('[name=moldPartNo]').length)){
        	var url = "/plm/jsp/project/ProductChangeHistory.jsp?isModal=&invokeMethod=changeHistory&oid="+$('[name=oid]').val();
        	window.open(url, "changeHistory", "top=100px, left=100px, height=230px, width=500px");
        }else{
        	changeHistory("OK");
        }
                	
    }
    
    function changeHistory(attachedValue){
    	if(attachedValue != 'OK'){
            return;
        }
    	showProcessing();
        $.ajax( {
            url : "/plm/jsp/project/ActionProductProject.jsp",
            type : "POST",
            data : $('[name=prodForm]').serialize(),
            async : false,
            success: function(data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02460")%><%--저장되었습니다. --%>');
                try{
                    opener.showProcessing();
                    opener.location.href='/plm/jsp/project/ProjectExtView2.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';                    
                }catch(exception){}
                self.close();
            },
            fail : function(){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장 실패 하였습니다. --%>');
                hideProcessing();
            }
        });
    }

    //Check Function
    function checkValidate() {
        var form = document.forms[0];
        var rowId =  getParamValue("rowId");
        var isValid = true;
        if(rowId.length > 0) {

            var paramObj = document.all.item("pName");
            var paramLen = paramObj.length;
            if(paramLen) {
                for(var i = 0; i < paramLen; i++) {
                    if(paramObj[i].value == "") {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02611") %><%--제품정보의 Part Name을 입력하시기 바랍니다--%>');
                        paramObj[i].focus();
                        isValid = false;
                        break;
                        
                    }
                }
            }else {
                if(paramObj.value == "") {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "02611") %><%--제품정보의 Part Name을 입력하시기 바랍니다--%>');
                    paramObj.focus();
                    isValid = false;
                }
            }


        }else{
        	if(document.forms[0].deletePOid.value.length == 0){
        	    alert('제품정보에 등록된 제품이 없습니다.');
        	    isValid = false;
        	}
        }
        
        var itemTypeObj = $('#iframe').contents().find("input[name=itemType]");
        //var dieNoObj = $('#iframe').contents().find("input[name=dieNo]");
        
        var checkSize = iframe.document.getElementsByName("itemType").length;
        for(var i=0; i<checkSize; i++){
        	var itemTypeVal = iframe.document.getElementsByName("itemType")[i].value;
        	if(itemTypeVal != '구매품'){
        		var dieNoVal = iframe.document.getElementsByName("dieNo")[i].value;
                if(dieNoVal == ''){
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00286") %><%--Item현황의 Die No를 입력하시기 바랍니다--%>");
                    iframe.document.getElementsByName("dieNo")[i].focus();
                    isValid = false;
                    break;   
                }
        	}
        }


        return isValid;
    }

    //Move Function
    function onMovePage(req) {
        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;
        if(msg == 'false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01182") %><%--다시 등록하시기 바랍니다--%>');
            return;
        }

        //alert("프로젝트를 등록완료 했습니다.");
        showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = showElements[0].getElementsByTagName("l_oid");

//    opener.document.forms[0].submit();
        opener.document.location.reload();
        self.close();
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
    //ksm
    function getIframeParamValue(name_str) {
        var paramObj = iframe.document.all.item(name_str);
        var tBody = document.getElementById("iteminfoList");
        var rowId = genRowId();
        tableRows = tBody.rows.length;
        newTr = tBody.insertRow(tableRows);
        newTr.id = rowId;

        if(paramObj == null || paramObj == 'undefined') {
            return "";
        }
        var rtnParam = "";
        var paramLen = paramObj.length;
        if(paramLen) {
            dieNoSave = "";
            for(var i = 0; i < paramLen; i++) {
                if(name_str=="dieNo") {
                        newTd = newTr.insertCell(newTr.cells.length);
                        newTd.innerHTML += "<input type='hidden' name='" + name_str + "' value='"+paramObj[i].value+"'>";
                }else{
                    newTd = newTr.insertCell(newTr.cells.length);
                    newTd.innerHTML += "<input type='hidden' name='" + name_str + "' value='"+paramObj[i].value+"'>";
                }

            }
        }
        return rtnParam;
    }

    function getParamValue3(name_str) {

        var rowId = document.all.item("rowId");
        var rtnParam = "";

        if(rowId.length) {
            for(var j = 0; j < rowId.length; j++){
                var paramObj = document.all.item(name_str+""+rowId[j].value);
//        if(paramObj == null || paramObj == 'undefined') {
//          return "";
//        }
                if(paramObj != null && paramObj != 'undefined') {
                var paramLen = paramObj.length;
                if(paramLen) {
                    for(var k = 0; k < paramObj.length; k++){
                        if(rtnParam.length > 0) {
                                rtnParam += "&";
                            }

                        rtnParam += name_str +""+rowId[j].value+"=" + paramObj[k].value;
                    }
                }else{
                    rtnParam +="&"+ name_str +""+rowId[j].value+"=" + paramObj.value;
                }
                }
            }
        }else {
            var paramObj = document.all.item(name_str+""+rowId.value);
            if(paramObj == null || paramObj == 'undefined') {
                return "";
            }
            var paramLen = paramObj.length;
            if(paramLen) {
                for(var k = 0; k < paramObj.length; k++){
                    if(rtnParam.length > 0) {
                            rtnParam += "&";
                        }

                    rtnParam += name_str +""+rowId.value+"=" + paramObj[k].value;
                }
            }else{
                rtnParam +="&"+ name_str +""+rowId.value+"=" + paramObj.value;
            }
        }
        return rtnParam;

    }

    function projectNoSearch() {
        if(!checkField(document.forms[0].projectNo, "프로젝트NO"))  {
            if(document.forms[0].projectNo.value.length < 7) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "03051") %><%--프로젝트 NO를 다시 확인 후 기입해 주십시요 \n (예:0103104)--%>");
                document.forms[0].projectNo.focus();
            } else {
                document.forms[0].action = "CreateJELProject.jsp";
                document.forms[0].tempProjectNo.value = document.forms[0].projectNo.value;
                document.forms[0].method="post";
                document.forms[0].cmd.value = "search";
                disabledAllBtn();
                showProcessing();
                document.forms[0].submit();
            }
        }
    }

    <%
    if(tempOid.length() > 0){
        TemplateProject tempObj = (TemplateProject)CommonUtil.getObject(tempOid);
        TemplateProjectData data = new TemplateProjectData(tempObj);
        String tempobj_name = data.tempName;
        String tempobj_duration = ""+data.tempDuration;
    %>

    function loadTemp()
    {
/*
        var targetTable = document.getElementById("templatetable");
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);


        //Template 명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerText = " <%=tempobj_name%>";

        //기간
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "<%=tempobj_duration%>";

        //등록일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "<%=DateUtil.getDateString(data.tempCreateDate, "D")%>";

        //최종수정일
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "<%=DateUtil.getDateString(data.tempModifyDate, "D")%>";

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','<%=tempOid%>');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='templateOid' value='<%=tempOid%>'>";
*/

    }
    <%
    }%>
    //Project Template 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



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
        var isPM = document.getElementById("isPM").value;

        var departName = document.getElementById("tempdepartment");
        var departKey = document.getElementById("department");

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
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];

            if(rname == "devManager" && isPM == "false" && <%= "등록중".equals( project.getLifeCycleState().getDisplay(Locale.KOREA) )%>){
                departName.value = infoArr[5];
                departKey.value = infoArr[2];

            }
        }
    }

    function deleteRoleMember(rname) {
        document.getElementById("temp" + rname).value = "";
        document.getElementById(rname).value = "";
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

            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input type=\"checkbox\" name=\"membertag1\" value='" + infoArr[0] + "'><input type='hidden' name='"+membertag+"' value='" + infoArr[0] + "'>";

            //이름
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = infoArr[4];

            //직위
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = infoArr[6];

            //부서
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerText = infoArr[5];
        }
    }

    function deleteRows(tableid, chk_name) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        if(chkTag == "undefined" ||  chkTag == null){
            return;
        }else{
            var chkLen = chkTag.length;
            if(chkLen) {
                if(chkLen > 1) {
                    for(i=chkLen; i>0; i--) {
                        if(chkTag[i-1].checked == true) {
                            targetTable.deleteRow(i);
                        }
                    }
                }else{
                    if(chkTag.checked == true || chkTag[0].checked == true) {
                        targetTable.deleteRow(1);
                    }
                    return;
                }
            }
        }
    }

     //사용자 가져오기 끝 ........................................................................................

     //부서 가져오기 시작 ........................................................................................
    //............................................................................................................
    function addDepartment() {
        var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
        attacheDept = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=420px; dialogHeight:465px; center:yes");
        if(typeof attacheDept == "undefined" || attacheDept == null) {
            return;
        }

        if ( typeof(attacheDept) != "object" ) {
            var deptSplit = attacheDept.split(",");
            for ( var i=0; i<deptSplit.length-1; i++ ) {
                var param = "command=deptInfo&deptOid=" + deptSplit[i];
                var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
                callServer(url, acceptDept);
            }
        }
        else {
            var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
            callServer(url, acceptDept);
        }
    }

    function acceptDept(req) {
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

        document.forms[0].department.value = l_oid[0].text;
        document.forms[0].tempdepartment.value = l_name[0].text;

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

    function doSearchPJT(oTable_id) {
        part_table_obj = document.getElementById(oTable_id);
        openWindow("/plm/jsp/project/ProjectSelectPage.jsp?isOpen=false&isModal=false&mode=one&invokeMethod=ProjectInfo", "SelectProject", 1024, 768);
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

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //제품 가져오기 시작

    function onModelType() {

        var url = "/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=s";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:460px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

//    addDependence(attache);

    }

    <%-- function addProduct()
    {
        var targetTable = document.getElementById("producttable");
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);

        //제품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //차종
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onModelType();\"><%=messageService.getString("e3ps.message.ket_message", "02745") %>차종</a>";

        //적용부위
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //U/S
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = "U/S";

        //판매가
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //원가
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //수익률
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //고객예상가
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //목표수익률
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //목표투자비
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=text name=\"projectTypeName\" size=7>";

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('templatetable','templateOid','<%//=tempOid%>');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
    } --%>

    //제품 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //item 가져오기 시작

    function addItem(typeValue)
    {
        iframe.addItem(typeValue);
    }

    function deleteItem() {
        iframe.deleteItem();
    }

    //item 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    function checkAll2(obj, selectObj) {
    /*  form = document.forms[0];
        if(form.oid) {
            var chkLen = form.oid.length;
            if(chkLen) {
                for(var i = 0; i < chkLen; i++) {
                    form.oid[i].checked = form.chkAll.checked;
                }
            }else{
                form.oid.checked = form.chkAll.checked;
            }
        }
    */
        if(selectObj) {
            var chkLen = selectObj.length;
            if(chkLen) {
                for(var i = 0; i < chkLen; i++) {
                    selectObj[i].checked = obj.checked;
                }
            }else{
                selectObj.checked = obj.checked;
            }
        }
    
    }

    //project
    function onProject() {

        var url = "/plm/jsp/project/ProjectSelectPopUp.jsp?mode=multi&type=2&pjtType=2";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=750px; dialogHeight:620px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        acceptProject(attache);

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //차종 가져오기 시작

    function onModel(rname) {

        var url = "/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=s";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:460px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        addModel(rname, attache);

    }

    function addModel(rname, objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById("temp" + rname);
        var keyName = document.getElementById(rname);

        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[1];
            keyName.value = infoArr[0];

        }
    }

    //차종 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //조립처 가져오기 시작

    function onCustomer() {

        var url = "/plm/jsp/common/code/SelectNumberCode.jsp?mode=multi&codetype=CUSTOMEREVENT";
        attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=840px; dialogHeight:460px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }

        addCustomer(attache);

    }

    function addCustomer(arr)
    {
        var targetTable;
        targetTable = document.getElementById("customertable");

        for(var i = 0; i < arr.length; i++) {
            subArr = arr[i];

            tableRows = targetTable.rows.length;
            newTr = targetTable.insertRow(tableRows);

            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.innerHTML = "<input type=hidden name=customer value='"+subArr[0]+"'>" + subArr[1];

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM0";
            newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:deleteCustomer('customer', '"+subArr[0]+"');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
        }

    }

    function deleteCustomer(chk_name, chk_value) {
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(chkTag[i].value == chk_value) {
                    customertable.deleteRow(i);
                    return;
                }
            }
        }else {
            if(chkTag.value == chk_value) {
                customertable.deleteRow(0);
                return;
            }
        }
    }

    //조립처 가져오기 끝
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    // 멀티 속성 가져오기
    function addProcessM(type, depth) {
        var form = document.forms[0];
        var mode = "multi";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode;

        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
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
                newTd.innerHTML = "<a href=\"javascript:;\" onClick=\"javascript:onDeleteTableRow('"+type+"', '"+type+"Oid', '" + subArr[0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='"+type+"Oid' value='" + subArr[0] + "'>";
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

///////////////////////////////////////////
///////////자동차일정 선택////////////////
///////////////////////////////////////////
        var programRowId = "";
        function selectProgram( rowId ){
        programRowId = rowId;
        var param ="?oid=<%=oid%>&sid="+rowId;
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
        
        var rowId = programRowId;

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

    function changeAssembly(rowIndex) {
        $('#assemblyPlaceName'+rowIndex).val('');
        $('#assemblyPlace'+rowIndex).val('');
    }

    //제품추가
    function createProduct(objArr){
        var tBody = document.getElementById("productInfo");
        var hasDuplicatePartNumber = false;
        $('[name=pNum]').each(function(){
            for ( var i=0; i<objArr.length; i++ ) {
                if($(this).val() == objArr[i][1]){
                	alert('['+objArr[i][1] + '] 해당 제품이 이미 존재 합니다.');
                    hasDuplicatePartNumber = true;
                    objArr.splice(i, 1);
                    return;
                }
            }
        });
        /* if(hasDuplicatePartNumber){
        	continue;
        } */
        
        for(var i=0; i<objArr.length;i++){
            
            var rowId = genRowId();

            tableRows = tBody.rows.length;
            newTr = tBody.insertRow(tableRows);
            newTr.id = rowId;

            //삭제
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<a href=\"javascript:;\" onclick=\"javascript:onDeleteTableRow2('productInfo','rowId','" + rowId + "');\"><img src='/plm/portal/images/b-minus.png' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
            
            //제품번호
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input type='hidden' name='pNum' value='"+objArr[i][1]+"' id='pNum"+rowId+"'><a href=javascript:openViewPart('"+objArr[i][1]+"')>"+objArr[i][1]+"</a>";
            newTd.innerHTML += "<input type='hidden' name='pOid' id='reviewProjectNo"+rowId+"' value='"+objArr[i][0]+"'>";
            newTd.innerHTML += "<input type='hidden' name='reviewProjectNo' id='reviewProjectNo"+rowId+"' value=''>";
            newTd.innerHTML += "<input type='hidden' name='reviewSeqNo' id='reviewSeqNo"+rowId+"' value=''>";

            //제품명
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.style.textAlign = "left";
            newTd.innerHTML = "<input type='hidden' name='pName' id='pName"+rowId+"' value='"+objArr[i][2]+"''>"+objArr[i][2];

            //차종
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<div style='width:80%;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><span name='carName' id='carName"+rowId+"' style='align=right'></span></nobr>";
            <%if(isPM || CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO")){%>
            newTd.innerHTML += "<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
            <%}%>

            //조립처
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            var selectStr = "<select id=\"assemblyPlaceType" + rowId + "\" name=\"assemblyPlaceType\" style=\"width:70px\" onChange=\"javascript:changeAssembly(" + rowId + ")\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"사내\"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"외주\"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option></select>";
            selectStr += "<input id=\"assemblyPlaceName" + rowId + "\" type=text name=\"assemblyPlaceName\" style=\"width:90px\" class=\"txt_fieldRO\" readonly>";
            selectStr += "<input id=\"assemblyPlace" + rowId + "\" type=hidden name=\"assemblyPlace\">";
            selectStr += " <a href='javascript:selectAssemplyPlace("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
            newTd.innerHTML = selectStr;
            
            //조립구분
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            selectStr = "<select name=\"assembledType\" style=\"width: 90%\">";
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
            
            $("#assemblyPlaceType"+rowId).val(objArr[i][26]);
            $("#assemblyPlaceName"+rowId).val(objArr[i][27]);

            if(objArr[i][26] == '사내'){
            	numCodeAjaxByName("PRODUCTIONDEPT",objArr[i][27],"assemblyPlace"+rowId);
            }else{
            	$("#assemblyPlaceName"+rowId).val(objArr[i][27]);
            	
            	$("#assemblyPlace"+rowId).val(getPartSpec(objArr[i][0],"spManufacPlace"));
            }
        }
           
    }
    
    function getPartSpec(oid,attrCode){
        
    	var specValue = "";
        
        $.ajax({
            type: 'get',
            async: false,
            cache: false,
            url: '/plm/ext/part/base/getPartSpec.do?partOid='+oid+"&attrCode="+attrCode,
            success: function (data) {
            	specValue = data;
            },
            fail : function(){
            	specValue = "";
            }
        });
        
        return specValue;
     }
    
    function numCodeAjaxByName(codetype,name, targetId) {
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codetype, name:name},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.numCode, function() {
                    $("#"+targetId).val(this.oid);
                });
            }
        });
    } 

    function onDeleteTableRow2(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);

        var chkLen = chkTag.length;
        if ( chkLen ) {
            for ( var i = 0; i < chkLen; i++ ) {
                if ( chkTag[i].value == chk_value ) {
                    pOidObj = document.all.item("pOid"+chk_value);
                    if ( pOidObj ) {
                        if ( pOidObj.value != "" )
                            document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
                    }
                    targetTable.deleteRow(i+1);

                    // partNo에 값입력
                    partNoDuplicateCheck();

                    return;
                }
            }
        }else {
            if ( chkTag.value == chk_value ) {
                pOidObj = document.all.item("pOid"+chk_value);
                if ( pOidObj ) {
                    if ( pOidObj.value != "" )
                        document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
                }
                targetTable.deleteRow(1);

                // partNo에 값입력
                partNoDuplicateCheck();

                return;
            }
        }
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

    //==  [Start] 부품 검색(PartNo)  == //
    // mode = m / s
    // pType=A(전체)/P(제품)/D(다이)/M(금형)
    function selectPartNumber(targetId, arrIndex) {
        _callBackFuncTargetId = targetId;
        _callBackFuncArrIndex = arrIndex;
        showProcessing();
        SearchUtil.selectOnePart("callBackFuncPartPopup", "pType=P");
    }

    var _callBackFuncTargetId;
    var _callBackFuncArrIndex;
    function callBackFuncPartPopup(selectedPartAry){
        if ( typeof selectedPartAry != "undefined" && selectedPartAry != null) {
            setPartNo(_callBackFuncTargetId, _callBackFuncArrIndex, selectedPartAry);
        }
    }

    function setPartNo(targetId, arrIndex, parts) {
        if ( parts != undefined && parts.length > 0 ) {
            var partNo = "";
            var partName = "";
            for (var i = 0; i < parts.length; ++i) {
                partNo = parts[i][1];
                partName = parts[i][2];
            }
            document.getElementById(targetId + arrIndex).value = partNo;
            document.getElementById("pName" + arrIndex).value = partName;

            // partNo에 값입력
            partNoDuplicateCheck();
        }
    }

    function clearPartNumber(targetId, arrIndex) {
        document.getElementById(targetId + arrIndex).value = "";
        document.getElementById("pName" + arrIndex).value = "";
    }

    function partNoDuplicateCheck() {
        var tBody = document.getElementById("productInfo");
        var tableRows = tBody.rows.length;

        // Table TR length
        var tableTrRows = tBody.childNodes.length;

        var newPartNo = new Array();
        /* for ( var i=2; i<tableRows; i++ ) {
            newPartNo.push(tBody.childNodes[0].childNodes[i].childNodes[0].childNodes[0].value);
        } */
        $('#pNum').each(function(){
            newPartNo.push($(this).val());	
        });
        
        newPartNo = newPartNo.sort();
        var partNoDuplicate = new Array();
        for ( var i=0; i<newPartNo.length; i++ ) {
            var checkDu = 0;
            for ( j=0; j<newPartNo.length; j++ ) {
                if ( newPartNo[i] != newPartNo[j] ) {
                    continue;
                }
                else {
                    checkDu++;
                    if ( checkDu > 1 ) {
                        newPartNo.splice(j,1);
                    }
                }
            }
        }

        document.forms[0].partNo.value = newPartNo.join(", ");

        if ( document.forms[0].partNo.value == "" ) {
            document.forms[0].partNo.value = "미입력";
        }
    }
    //==  [Start] 부품 검색(PartNo)  == //

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

    //사내 or 협력사 검색팝업 Open
    function selectProduction(){
        var sel = document.getElementById("selInOut").value;
        if (sel == 'i'){
            selectDepartment();
        }else {
            selectPartner();
        }
    }

    //사내생산처 검색 팝업  Open
    function selectDepartment() {
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
            alert("<%=messageService.getString("e3ps.message.ket_message", "01553") %><%--부서를 선택하시기 바랍니다--%>");
            return;
        }

        document.getElementById("proteamNo").value = arr[0][0];
        document.getElementById("proteamName").value = arr[0][2];
  }

    //협력사검색 팝업 Open
    function selectPartner(){
        var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
        openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
  }

  //협력사 검색결과 셋팅
  function linkPartner(arr){
      if(arr.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
            return;
        }

        document.getElementById("proteamNo").value = arr[0][0];
        document.getElementById("proteamName").value = arr[0][1];
  }

  //필드 값 삭제
  function deleteValue(param){
      document.getElementById(param).value = "";
  }

    //생산처 변경 시 타생산처 비활성화 및 삭제
  function changeProteam(){
      deleteValue("proteamName");
      deleteValue("proteamNo");
  }

  //개발담당 or PM 변경
  function displayImg(){
      isPM = document.getElementById("isPM").value;
        if(isPM == "false"){
            document.getElementById("createImg").style.display = "none";
            document.getElementById("deleteImg").style.display = "none";
        }else{
            document.getElementById("createImg").style.display = "";
            document.getElementById("deleteImg").style.display = "";
        }
        document.getElementById("department").value = "";
        document.getElementById("tempdepartment").value = "";
        document.getElementById("devManager").value = "";
        document.getElementById("tempdevManager").value = "";
  }
  //제품 변경 사유 등록
  function pchangeCreate(oid){
          var url = "/plm/jsp/project/ProductChangeHistory.jsp?oid="+oid;
        openSameName(url,"500","230","status=no,scrollbars=no,resizable=no");

  }
  //제품 변경 사유 보기
  function pchangeView(oid){
          var url = "/plm/jsp/project/ProductChangeHistoryView.jsp?oid="+oid;
        openSameName(url,"520","500","status=no,scrollbars=yes,resizable=no");

  }
  //개발의뢰서 연계
  function selectDr(){
    //개발 검토  : developmentStep=R
    //제품   : developmentStep=D
        var url="/plm/jsp/dms/SearchDevRequestPop.jsp?method=selDr&developmentStep=D&mode=one";
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
  var targetReviewTableId = "productInfo";
  
    function onTargetSet(req){

        var xmlDoc = req.responseXML;
        var showElements = xmlDoc.selectNodes("//message");
        var msg = showElements[0].getElementsByTagName("l_message")[0].text;

        if(msg == 'false' && showElements[0].getElementsByTagName("l_result") != null && showElements[0].getElementsByTagName("l_result")[0].text != ""){
            alert(showElements[0].getElementsByTagName("l_result")[0].text);
            return;
        }
        if(msg == 'false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
            return;
        }

        var showElements = xmlDoc.selectNodes("//data_info");
        var l_drNumber = showElements[0].getElementsByTagName("l_drNumber");
        var l_drKeyOid = showElements[0].getElementsByTagName("l_drKeyOid");
        var form = document.forms[0];
        form.drNumber.value = decodeURIComponent(l_drNumber[0].text);
        form.drKeyOid.value = decodeURIComponent(l_drKeyOid[0].text);
    }

    //Number Code Ajax
    function numCodeAjax(codeType, code, targetId) {
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codeType, code:code},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.numCode, function() {
                    $("#"+targetId).append("<option value='"+this.code+"' vendercode='"+this.vendercode+"'>"+ this.value +"</option>");
                });
            }
        });
    }

    //Number Code Ajax
    var myCodeVendercode = "";
    function numCodeAjaxMyCode(codeType, myCode) {
        myCodeVendercode = "";
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codeType, myCode:myCode},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.numCode, function() {
                    myCodeVendercode = this.vendercode;
                });
            }
        });
    }

    function getSelect1(code) {

        $("#productTypeLevel2").empty().data('options');
        $("#productTypeLevel2").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
        // 제품구분에서 선택된 code로 공통코드 테이블 조회
        numCodeAjax("PRODUCTTYPELEVEL2", code, "productTypeLevel2");
        // 공통코드조회해서 제품구분 Level2가 존재하거나. 제품구분이 value가 공백이 아니면 제품구분Level2 표시
        // 아닌경우는 제품구분Level2, 방수여부를 초기화후 숨김처리
        if ( $("#productTypeLevel2 option").size() > 1 && $("#productType option:selected").val() != "" ) {
            $("#productTypeLevel2").show();
        }
        else {
            $("#productTypeLevel2").empty().data('options');
            $("#productTypeLevel2").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
            $("#productTypeLevel2").hide();
        }
    }

    // 사용안함
    function getSelect2(code) {
        $("#sealed").empty().data('options');
        $("#sealed").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
        $("#sealed").hide();

        // code로 PRODUCTTYPELEVEL2에 있는 vendercode 조회
        numCodeAjaxMyCode("PRODUCTTYPELEVEL2", code);

        if ( myCodeVendercode != undefined && myCodeVendercode != "" ) {
            var sp = myCodeVendercode.split("|");
            for ( var i=0; i<sp.length; i++ ) {
                if ( sp[i] == "SEALED" ) {
                    $("#sealed").empty().data('options');
                    $("#sealed").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
                    numCodeAjax("SEALED", "", "sealed");
                }

                if ( sp[i] == "SERIES" ) {
                    $("#series").empty().data('options');
                    $("#series").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
                    numCodeAjax("SERIES", "", "series");
                }
            }
        }

        // 방수여부가 selectbox의 size 1보다 클 경우
        if ( $("#sealed option").size() > 1 ) {
            $("#sealed").show();
        }
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
        	selectedRowId = rname;
    	}
    }
    
    function setPartner(arr){    	
    	$('#assemblyPlaceName'+selectedRowId).val(arr[0][1]);
        $('#assemblyPlace'+selectedRowId).val(arr[0][0]);
    }
    
    function selectChildPart(callBackFn){
    	if($('[name=pNum]').length == 0){
    		alert('추가된 제품정보가 없습니다.');
    		return;
    	}
    	var param = '';
    	if($('[name=pNum]').length > 0 ){
    		for(var i=0;i<$('[name=pNum]').length;i++){
       			param += '&pNums='+$('[name=pNum]')[i].value;
    		}
    	}
    	var url = '/plm/jsp/project/AddChildPartPopup.jsp?1=1'+param+"&invokeMethod="+callBackFn;
    	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    	
    	getOpenWindow2(url,'asdfsdf', 720, 600, opts);
    }
    
    //신규부품 등록시 callback
    function newpartaddFn(objArr){
    	if (typeof objArr == "undefined" || objArr == null || objArr.length == 0) {
            return;
        }
        var partArr = [];
        var partObj = [];
        partObj[0] = objArr[0]; //partOid
        partObj[1] = objArr[1]; //partNum
        partObj[2] = objArr[6]; //partName
        partArr[0] = partObj;
        if(objArr[7] == 'F'){ //partType(F:제품 나머지는 Item 현황에 추가되어야 함.)
            createProduct(partArr);
        }else{
        	partObj[0] = '';
        	partObj[1] = objArr[1]; //partNum
        	partObj[2] = objArr[6]; //partName
        	partObj[7] = objArr[7];
            if(objArr[7] == 'D'){
                partObj[4] = 'Die No';            	
            }else if(objArr[7] == 'M'){
                partObj[4] = '금형부품';            	
            }else if(objArr[7] == 'H'){
            	partObj[4] = '반제품';
            }else if(objArr[7] == 'R'){
            	partObj[4] = '원자재';
            }else if(objArr[7] == 'K'){
            	partObj[4] = '포장재';
            }else if(objArr[7] == 'S'){
            	partObj[4] = '스크랩';
            }else if(objArr[7] == 'W'){
            	partObj[4] = '상품';
            }            
            partArr[0] = partObj;
        	iframe.addPart(partArr);
        }
    }
    
    /* function addPart(objArr){
        var reArr = []; 
        for(var i=0; i<objArr.length;i++){
            subArr = new Array();
            subArr[0] = '';//oid
            subArr[1] = objArr[i][4];
            subArr[2] = objArr[i][1];
            subArr[3] = objArr[i][2];
            subArr[4] = '';
            subArr[5] = '';
            subArr[6] = 'Y';
            subArr[7] = objArr[i][10];
            
            reArr[i] = subArr;
        }
        addItem(reArr);
    } */
//-->
</script>
<!-- 부품 공통 js -->
<script src="/plm/extcore/js/part/partUtil.js"></script>
<style type="text/css">
#productInfo table {
    table-layout: fixed;
}
#productInfo td {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

select {
    color: #444444;
    height: 22px;
    border: 1px solid #b3d1dc;
    font-family: NanumGothic, "나눔고딕", Nanumgo, Dotum;
    font-size: 12px;
    background-color: #fff;
}
</style>
</head>
<%
    if (tempOid.length() > 0) {
%>
<body onload="javascript:loadTemp();">
    <%
        } else {
    %>
<body>
    <%
        }
    %>
    <%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form method="post" name="prodForm">
        <%
            String devROid = "";
            String devRNumber = "";
            try {
        		if (project.getDevRequest() != null) {
        		    devROid = CommonUtil.getOIDString(project.getDevRequest());
        		    devRNumber = project.getDevRequest().getNumber();
        		}
            } catch (Exception e) {
        	Kogger.error(e);
            }
        %>
        <!-- hidden begin -->
        <input type="hidden" name="drNumber" value="<%=devRNumber%>" class="txt_field" style="width: 180" id="textfield3" readonly>
        <input type="hidden" name="drKeyOid" value="<%=devROid%>"> 
        <input type="hidden" name="partNo" value="<%=projectData.partNo%>">
        <input type="hidden" name="initType" value="<%=initType%>"> 
        <input type="hidden" name="tempProjectNo" value=""> 
        <input type="hidden" name="lifecycle" value="Default"> 
        <input type="hidden" name="pmoid" value="<%=pmoid%>"> 
        <input type="hidden" name="pwlinkOid" value="<%=pwlinkOid%>"> 
        <input type="hidden" name="projectType" value="<%=projectType%>">
        <input type="hidden" name="oid" value="<%=oid%>"> 
        <input type="hidden" name="deletePOid" value=""> 
        <input type="hidden" name="command" value="Update">
        <!-- hidden end -->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07123")%><%--제품정보 수정--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <%if(CommonUtil.isMember("사양관리")){ %>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                            <a href="#" onClick="javascript:PartUtil.regView('<%=oid%>');" class="btn_blue">신규부품 등록</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>                                        
                                        <td width="5">&nbsp;</td>
                                        <%} %>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;"
                                            onClick="javascript:pchangeCreate('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02595")%><!-- 제품변경사유등록 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onClick="javascript:pchangeView('<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02597")%><!-- 제품변경사유이력 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#;"
                                            onClick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
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
                    <div style="width: 100%; height: 200px; overflow: auto">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="productInfo">
                            <tr>
                                <td width="30px" class="tdblueM">
                                    <a href="javascript:showProcessing();SearchUtil.selectPart('createProduct','pType=FW');"<%-- onclick="pchangeCreate($('[name=oid]').val())" --%>><img src="/plm/portal/images/b-plus.png"></a></td>
                                <td width="60px" class="tdblueM">Part No</td>
                                <td width="150px" class="tdblueM">Part Name</td>
                                <td width="80px" class="tdblueM">
                                    <%
                                      if(projectData.isCarDivisionMode){%><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%>
                                    <%}else{ %><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%>
                                    <%} %>
                                </td>
                                <td width="150px" class="tdblueM">조립처<%--조립처--%></td>
                                <td width="70px"  class="tdblueM">조립구분<%--조립구분--%></td>
                                <td width="40px" class="tdblueM">U/S</td>
                            </tr>
                        </table>
                    </div>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00283")%><%--Item 현황--%></td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onClick="javascript:selectChildPart('addItem');" class="btn_blue">자부품 추가</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onClick="javascript:SearchUtil.selectPart('iframe.addPart','pType=P');" class="btn_blue">부품 추가</a></td>
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
                            <td>
                            <iframe src="/plm/jsp/project/MoldItemIframe.jsp?oid=<%=oid%>" name="iframe" width="100%" height="305" frameborder="0" marginwidth="0" marginheight="0" scrolling="yes" id="iframe"></iframe>
                            <script>
                            
                            $("iframe[name=iframe]").width(window).width();
                            $(window).resize(function(){
                            	$("iframe[name=iframe]").width($(window).width());
                            });
                            </script>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="iteminfoList" style="display:none">
        </table>
    </form>
    
<%
    QuerySpec qs = new QuerySpec();

    int idxpi = qs.appendClassList(ProductInfo.class, true);

    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id" , "=", CommonUtil.getOIDLongValue(oid));
    qs.appendWhere(cs, new int[] { idxpi } );

    QueryResult qrpi = PersistenceHelper.manager.find(qs);
    PartnerDao partnerDao = new PartnerDao();
    QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject)project);
%>
    <script type="text/javascript">
    var isFirstUpdate = <%=qrpi.size()>0 || rt.size()>0?"false":"true" %>;
    var afterProductRowCount = <%=qrpi.size()%>;
    </script>
<%    
    while(qrpi.hasMoreElements()){
      Object o[] = (Object[])qrpi.nextElement();
      ProductInfo pi = (ProductInfo)o[0];

      String carName = ProductHelper.getCarName(pi.getPersistInfo().getObjectIdentifier().toString());
      String assemblyPlaceName = "";
      String assemblyPlace = "";

      String carName2 = "";
      if(pi.getAssemblyPlaceType() != null){
	      if(pi.getAssemblyPlace() != null){
		      assemblyPlace = CommonUtil.getOIDString(pi.getAssemblyPlace());
		      assemblyPlaceName = assemblyPlace!=null?pi.getAssemblyPlace().getName():"";
          }
          if(pi.getAssemblyPartnerNo() != null){
              assemblyPlace = pi.getAssemblyPartnerNo();
              assemblyPlaceName = assemblyPlace!=null?partnerDao.ViewPartnerName(pi.getAssemblyPartnerNo()):"";
          }
      }
      carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
%>
    <script>
        var tBody = document.getElementById("productInfo");
        var rowId = genRowId();

        tableRows = tBody.rows.length;
        newTr = tBody.insertRow(tableRows);
        
        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href=\"javascript:;\" onclick=\"javascript:onDeleteTableRow2('productInfo','rowId','" + rowId + "');\"><img src='/plm/portal/images/b-minus.png' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
        
        //제품번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type='hidden' name='pNum' id='pNum"+rowId+"' value='<%=pi.getPNum()==null?"":pi.getPNum() %>'><%=pi.getPNum()==null?"":"<a href=javascript:openViewPart('"+pi.getPNum()+"')>"+pi.getPNum() %>";
        newTd.innerHTML += "<input type='hidden' name='pOid"+rowId+"' id='pOid"+rowId+"' value='<%=pi.getPersistInfo().getObjectIdentifier().toString()%>'>";
        newTd.innerHTML += "<input type='hidden' name='reviewProjectNo' id='reviewProjectNo"+rowId+"' value='<%=pi.getReviewProjectNo()==null?"":pi.getReviewProjectNo() %>'>";
        newTd.innerHTML += "<input type='hidden' name='reviewSeqNo' id='reviewSeqNo"+rowId+"' value='<%=pi.getReviewSeqNo()==null?"":pi.getReviewSeqNo() %>'>";

        //제품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.style.textAlign = "left";
        newTd.innerHTML += "<input type='hidden' name='pName'id='pName"+rowId+"' value='<%=pi.getPName()==null?"":pi.getPName() %>'><%=pi.getPName()==null?"":pi.getPName() %>";

        //차종
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<div style='width:80%;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>";
        newTd.innerHTML += "<span name='carName' id='carName"+rowId+"' value='<%=carName==""?carName2:carName %>'><%=carName==""?carName2:carName %></span></nobr>&nbsp;";
        <%if(isPM || CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO")){%>
        newTd.innerHTML += "<a href='javascript:selectProgram("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
        <%}%>
        newTd.innerHTML += "<input type='hidden' name='y1T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y2T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y3T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y4T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y5T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y6T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y7T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y8T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y9T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='y10T' value=''>";
        newTd.innerHTML += "<input type='hidden' name='carName' id='carName"+rowId+"' value=''>";
        
        //조립처
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        var selectStr = "<select id=\"assemblyPlaceType" + rowId + "\" name=\"assemblyPlaceType\" style=\"width:70px\" onChange=\"javascript:changeAssembly(" + rowId + ")\"><option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option><option value=\"사내\" <%=pi.getAssemblyPlaceType()!=null&&pi.getAssemblyPlaceType().equals("사내")?"selected":""%>><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"외주\" <%=pi.getAssemblyPlaceType()!=null&&pi.getAssemblyPlaceType().equals("외주")?"selected":""%>><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option></select>";
        selectStr += "<input id=\"assemblyPlaceName" + rowId + "\" type=text name=\"assemblyPlaceName\" style=\"width:90px\" class=\"txt_fieldRO\" value=\"<%=assemblyPlaceName%>\" readonly>";
        selectStr += "<input id=\"assemblyPlace" + rowId + "\" type=hidden name=\"assemblyPlace\" value=\"<%=assemblyPlace%>\">";
        selectStr += " <a href='javascript:selectAssemplyPlace("+rowId+");'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
        newTd.innerHTML = selectStr;
        
        //조립구분
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        selectStr = "<select name=\"assembledType\" style=\"width: 90%\">";
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
        newTd.innerHTML = "<span name='usageT' id='usageT"+rowId+"' style='width:75%' value='<%=pi.getUsage()==null?"":pi.getUsage() %>'><%=pi.getUsage()==null?"":pi.getUsage() %><input type='hidden' name='usageT' value='<%=pi.getUsage()==null?"":pi.getUsage() %>'></span>&nbsp;";
    </script>
<%}%>    
</body>
</html>