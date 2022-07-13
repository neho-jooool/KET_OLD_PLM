<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.*" %>
<%@page import="wt.fc.*,
                wt.query.QuerySpec,
                wt.query.SearchCondition,
                e3ps.common.util.*,
                e3ps.common.web.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.project.outputtype.ModelPlan,
                e3ps.project.outputtype.OEMProjectType,
                e3ps.common.code.NumberCodeHelper
                " %>
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                
                
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    String oid = request.getParameter("oid");
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = null;
    if(project != null){
	   projectData = new E3PSProjectData(project);
    }
    
    Map<String, Object> parameter = new HashMap<String, Object>();
    // 구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "CUSTOMEREVENT");
    List<Map<String, Object>> customerEventNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    String sid = request.getParameter("sid");
    String optOid[] = request.getParameterValues("optOid"+sid);
    String optOidOne = request.getParameter("optOid"+sid);

    String usage[] = request.getParameterValues("usage"+sid);
    String optionRate[] = request.getParameterValues("optionRate"+sid);
    String pOid[] = request.getParameterValues("pOid"+sid);
    String carName[] = request.getParameterValues("carName"+sid);


    //Kogger.debug("optOidOne ==>" + optOidOne);
    //Kogger.debug("optOid ==>" + optOid);
    //Kogger.debug("pOid ==>" + pOid);

    String projectType = "";
    if(projectData != null){
        if(projectData.isCarDivisionMode){
            projectType = messageService.getString("e3ps.message.ket_message", "02745") /*차종*/;
        }else{
            projectType = messageService.getString("e3ps.message.ket_message", "02466") /*적용기기*/;
        }
    }else{
	    boolean isType0 = CommonUtil.isMember("전자사업부");
	    boolean isType1 = CommonUtil.isMember("자동차사업부");
	    if(isType0){
	        projectType = messageService.getString("e3ps.message.ket_message", "02466") /*적용기기*/;
	    }
	    if(isType1){
	        projectType = messageService.getString("e3ps.message.ket_message", "02745") /*차종*/;
	    }   
    }

    String check = "";
    if(pOid != null && pOid[0].toString().length() > 0){
        if(ProductHelper.checkDirectInputProductInfo(pOid[0].toString()))
            check = "1";
    }
    if(usage != null){
        if(optOid != null && optOid[0] != null && (optOid[0].length() == 0 || ( optOid[0].length() > 0 && "nodata".equals(optOid[0]) ) ) ) {
            if( ( pOid != null && pOid[0] != null && pOid[0].length() == 0 ) || pOid == null )
                check = "1";
        }
    }
    
    String invokeMethod = request.getParameter("invokeMethod");
    if ( invokeMethod == null || invokeMethod.length() == 0 ){
        invokeMethod = "";
    }
    
    String y1V = request.getParameter("y1"+sid);
    String y2V = request.getParameter("y2"+sid);
    String y3V = request.getParameter("y3"+sid);
    String y4V = request.getParameter("y4"+sid);
    String y5V = request.getParameter("y5"+sid);
    String y6V = request.getParameter("y6"+sid);
    String y7V = request.getParameter("y7"+sid);
    String y8V = request.getParameter("y8"+sid);
    String y9V = request.getParameter("y9"+sid);
    String y10V = request.getParameter("y10"+sid);

    if(y1V==null || y1V.length() == 0) y1V="0" ;
    if(y2V==null || y2V.length() == 0) y2V="0" ;
    if(y3V==null || y3V.length() == 0) y3V="0" ;
    if(y4V==null || y4V.length() == 0) y4V="0" ;
    if(y5V==null || y5V.length() == 0) y5V="0" ;
    if(y6V==null || y6V.length() == 0) y6V="0" ;
    if(y7V==null || y7V.length() == 0) y7V="0" ;
    if(y8V==null || y8V.length() == 0) y8V="0" ;
    if(y9V==null || y9V.length() == 0) y9V="0" ;
    if(y10V==null || y10V.length() == 0) y10V="0" ;

    String sumV = Integer.toString(Integer.parseInt(y1V) + Integer.parseInt(y2V) + Integer.parseInt(y3V) + Integer.parseInt(y4V) + Integer.parseInt(y5V) + Integer.parseInt(y6V) + Integer.parseInt(y7V) + Integer.parseInt(y8V) + Integer.parseInt(y8V) + Integer.parseInt(y10V)) ;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Select Program</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<script>
//================================================/
//==================고객 스크립트 시작==================
//================================================/
//Number Code Ajax
function numCodeAjax(codeType, code, venderCode, targetId) {
    $.ajax( {
        url : "/plm/servlet/e3ps/NumberCodeAjax",
        type : "POST",
        data : {codeType:codeType, code:code, venderCode:venderCode},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.numCode, function() {
                $("#"+targetId).append("<option value='"+this.oid+"' >"+ this.value +"</option>");
            });
        }
    });
}

function onClickNation( fObj ) {
    $("#customer").empty().data('options');
    $("#customer").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");

    // 자동차일정 등록 메뉴 클릭시
    if ( fObj == "국내" ){
        numCodeAjax("CUSTOMEREVENT", "1000", "자동차", "customer");
    }
    else{
        if ( fObj.value != '' ) {
            numCodeAjax("CUSTOMEREVENT", fObj.value, "자동차", "customer");
        }
    }
}

/*****************************************************
*****************고객사별 차종 선택 스크립트******************
*****************************************************/

function onChangeCustomer(fObj) {
    form = document.CreateProgram;
    if(fObj.value != '') {
        selectSearch2(fObj.value);
    }
}

function selectSearch2(svalue) {
    onProgressBar();

    var param = "command=select2&mode=1&oemtype=CARTYPE&oid=" + svalue;
    var url = "/plm/jsp/project/OEMAjaxAction.jsp?" + param;
    callServer(url, onSelectOption2);
}

window.getTagText = function(xmlDoc){
    return xmlDoc.textContent || xmlDoc.text || '';
}

function onSelectOption2(req) {
    var xmlDoc = req.responseXML;
    //var showElements = xmlDoc.selectNodes("//message");
    var msg = xmlDoc.getElementsByTagName("l_message")[0];

    //showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = xmlDoc.getElementsByTagName("l_oid");
    var l_name = xmlDoc.getElementsByTagName("l_name");

    var arr = new Array();
    for(var i = 0; i < l_oid.length; i++) {
        subArr = new Array();
        subArr[0] = decodeURIComponent(getTagText(l_oid[i]));
        subArr[1] = decodeURIComponent(getTagText(l_name[i]));
        arr[i] = subArr;
    }
    addSelectNode2(arr);
}

function addSelectNode2(vArr) {

        var fTD2 = document.all.item("cartype");
        var len2 = fTD2.length;
        for(var j = len2 ;j > 0 ; j--){
            fTD2.remove(j);
        }
        for(var i = 0; i < vArr.length; i++) {
            var newSpan = document.createElement("option");
            newSpan.innerHTML = vArr[i][1];
            newSpan.value= vArr[i][0];
            fTD2.appendChild(newSpan);
        }
}

function clearCartype(){
    var aa = document.all.item("cartype");

    for(var j = 1 ; j < aa.length ; j++){
        aa.remove(j);
    }

}

// str은 0~9까지 숫자만 가능하다.
function checkNumber(str) {
    var flag=true;
    if (str.length > 0) {
        for (i = 0; i < str.length; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                flag=false;
                return;
            }
        }
    }
    return flag;
}

// 0이상의 값
function checkNumber2(str) {
    var flag=true;
    if (str.length > 0) {
            if (str.charAt(0) < '1') {
                flag=false;
                alert("<%=messageService.getString("e3ps.message.ket_message", "00035", 0) %><%--{0}보다 큰수를 입력해 주세요--%>");
                return;
            }
        }
    return flag;
}

/*****************************************************
*****************자동차일정 가져오는 스크립트******************
*****************************************************/

function displayChangeOEMType(){

    if(document.SelectProgram.selfcheck.checked==false){

        var cartype = document.getElementById("cartype");
        if(cartype.value != ""){
            var fTD4 = document.all.item("cartype");

            if(fTD4.length) {
                for(i=0; i < fTD4.length; i++) {
                    if(fTD4[i].selected == true)
                        var oidd = fTD4[i].value;
                }
            }


            var param = "command=select3&oid=" + oidd;
            var url = "/plm/jsp/project/OEMAjaxAction.jsp?" + param;
            callServer(url, setLayerOEMType);
        }else{
            alert('<%=messageService.getString("e3ps.message.ket_message", "02752") %><%--차종을 선택하여 주십시오--%>');
            return;
        }
    }else{
        alert('<%=messageService.getString("e3ps.message.ket_message", "02721") %><%--직접입력상태에선 추가 하실 수 없습니다--%>');
    }
}

    function setLayerOEMType(req) {
        var xmlDoc = req.responseXML;
        //var showElements = xmlDoc.selectNodes("//message");
        //var msg = showElements[0].getElementsByTagName("l_message")[0].text;

        //showElements = xmlDoc.selectNodes("//data_info");
        var l_oid = xmlDoc.getElementsByTagName("l_oid");
        var l_name = xmlDoc.getElementsByTagName("l_name");
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
        var l_sum = xmlDoc.getElementsByTagName("l_sum");
        var l_custom = xmlDoc.getElementsByTagName("l_custom");

        var arr = new Array();
        for(var i = 0; i < l_oid.length; i++) {
            subArr = new Array();
            subArr[0] = decodeURIComponent(getTagText(l_oid[i]));
            subArr[1] = decodeURIComponent(getTagText(l_name[i]));
            subArr[2] = decodeURIComponent(getTagText(l_y1[i]));
            subArr[3] = decodeURIComponent(getTagText(l_y2[i]));
            subArr[4] = decodeURIComponent(getTagText(l_y3[i]));
            subArr[5] = decodeURIComponent(getTagText(l_y4[i]));
            subArr[6] = decodeURIComponent(getTagText(l_y5[i]));
            subArr[7] = decodeURIComponent(getTagText(l_y6[i]));
            subArr[8] = decodeURIComponent(getTagText(l_y7[i]));
            subArr[9] = decodeURIComponent(getTagText(l_y8[i]));
            subArr[10] = decodeURIComponent(getTagText(l_y9[i]));
            subArr[11] = decodeURIComponent(getTagText(l_y10[i]));
            subArr[12] = decodeURIComponent(getTagText(l_sum[i]));
            subArr[13] = decodeURIComponent(getTagText(l_custom[i]));

            arr[i] = subArr;
        }
        if(arr.length){
            addSelectNode3(arr);
        }else{
            alert('<%=messageService.getString("e3ps.message.ket_message", "02411") %><%--자동차일정이 등록되지 않은 차종입니다--%>');
            return;
        }
    }

function addSelectNode3(vArr) {

        subarr = vArr[0];

        var insertCheck = true;
        if(document.SelectProgram.itemtableOid) {
            if(document.SelectProgram.itemtableOid.length) {
                index = document.SelectProgram.itemtableOid.length;
                for(j=index; j>0; j--) {
                    if(document.SelectProgram.itemtableOid[j-1].value == subarr[0]) insertCheck = false;
                }
            }else {
                if(document.SelectProgram.itemtableOid.value == subarr[0]) insertCheck = false;
            }
        }

        if(insertCheck) {

            var tBody = document.getElementById("itemtable");

            tableRows = tBody.rows.length;
            newTr = tBody.insertRow(tableRows);
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "30";
            newTd.innerHTML = "<input name=\"itemtableOid\" type=\"checkbox\" value=" + vArr[0][0] + " />";

            //고객사
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "90";
            newTd.style.textAlign = 'left';
            newTd.innerHTML = vArr[0][13];

            //차종
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "90";
            newTd.style.textAlign = 'left';
            newTd.innerHTML = "<input name=\"carName\" value="+vArr[0][1]+" type=\"hidden\"  />"+vArr[0][1];

            //1년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y1\" value="+vArr[0][2]+" type=\"hidden\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\"/>"+vArr[0][2];

            //2년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y2\" value="+vArr[0][3]+" type=\"hidden\"  style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\"/>"+vArr[0][3];

            //3년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y3\" value="+vArr[0][4]+" type=\"hidden\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />"+vArr[0][4];

            //4년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y4\" value="+vArr[0][5]+" type=\"hidden\"  style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\"/>"+vArr[0][5];

            //5년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y5\" value="+vArr[0][6]+" type=\"hidden\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />"+vArr[0][6];

            //6년차
            var sumY6 = parseInt(vArr[0][7]) + parseInt(vArr[0][8]) + parseInt(vArr[0][9]) + parseInt(vArr[0][10]) + parseInt(vArr[0][11]) ;

            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            //newTd.innerHTML = "<input name=\"y6\" value="+sumY6+" type=\"hidden\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";
            newTd.innerHTML += "<input name=\"y6\" value="+vArr[0][7]+" type=\"hidden\" />"
            newTd.innerHTML += "<input name=\"y7\" value="+vArr[0][8]+" type=\"hidden\" />"
            newTd.innerHTML += "<input name=\"y8\" value="+vArr[0][9]+" type=\"hidden\" />"
            newTd.innerHTML += "<input name=\"y9\" value="+vArr[0][10]+" type=\"hidden\" />"
            newTd.innerHTML += "<input name=\"y10\" value="+vArr[0][11]+" type=\"hidden\" />"+sumY6;
            /*
            //7년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y7\" value="+vArr[0][8]+" type=\"hidden\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />"+vArr[0][8];

            //8년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y8\" value="+vArr[0][9]+" type=\"hidden\"  onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\"/>"+vArr[0][9];

            //9년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y9\" value="+vArr[0][10]+" type=\"hidden\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />"+vArr[0][10];

            //10년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y10\" value="+vArr[0][11]+" type=\"hidden\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />"+vArr[0][11];
            */

            //합계
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdgrayR";
            newTd.width = "50";
            newTd.innerHTML ="<input name=\"sum\" value="+vArr[0][12]+" style=\"width:45\" type=\"hidden\"  />"+vArr[0][12];

            //차종 별 U/S
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"usage\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //옵션률
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR0";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"optionRate\" type=\"text\" class=\"txt_fieldR\" style=\"width:35\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />%";

            //삭제
            //newTd = newTr.insertCell(newTr.cells.length);
            //newTd.className = "tdwhiteM0";
            //newTd.width = "50";
            //newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('itemtable','itemtableOid','" + vArr[0][0] + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='itemtableOid' value='" + vArr[0][0] + "' >";
        }
}



    function checkAll2(obj, selectObj) {

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


    function onDeleteTableRow(tableid, chk_name, chk_value) {
        targetTable = document.getElementById(tableid);
        var chkTag = document.all.item(chk_name);
        var chkLen = targetTable.rows.length;
        if(chkLen>1){
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

function selDisable(){
    if(document.all.customer.disabled == true){
        document.all.radio.disabled = false
        document.all.customer.disabled = false
        document.all.cartype.disabled = false
    }else{
        document.all.radio.disabled = true
        document.all.customer.disabled = true
        document.all.cartype.disabled = true
    }
}

function selfCreate(){

    var selfcheck = document.SelectProgram.selfcheck;

    if(selfcheck.checked == true){
        targetTable = document.getElementById("itemtable");
        var ttlength = targetTable.rows.length;
        for(i = ttlength ; i>0 ; i-- ){
            targetTable.deleteRow(i-1);
        }

        var tBody = document.getElementById("itemtable");

            tableRows = tBody.rows.length;
            newTr = tBody.insertRow(tableRows-1);

            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "30";
            newTd.align = "center";
            newTd.innerHTML = "&nbsp;";

            //고객사
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "90";
            newTd.align = "center";
            newTd.innerHTML = "-";

            //차종
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "90";
            newTd.id = "carName";
            newTd.innerHTML = "<input name=\"carName\" type=\"text\" class=\"txt_field\" style=\"width:90\" />";

            //1년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y1\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\"/>";

            //2년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y2\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\"/>";

            //3년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y3\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //4년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y4\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //5년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y5\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //6년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"y6\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            /*
            //7년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y7\" type=\"text\" style=\"width:90%\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //8년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y8\" type=\"text\" style=\"width:90%\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //9년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y9\" type=\"text\" style=\"width:90%\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //10년차
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteM";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"y10\" type=\"text\" style=\"width:90%\"onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";
            */
            //합계
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdgrayR";
            newTd.width = "50";
            newTd.innerHTML = "&nbsp;<span id=\"total2\"></span>";

            //차종 별 U/S
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR";
            newTd.width = "50";
            newTd.innerHTML = "<input name=\"usage\" type=\"text\" class=\"txt_fieldR\" style=\"width:45\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />";

            //옵션률
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteR0";
            newTd.width = "70";
            newTd.innerHTML = "<input name=\"optionRate\" type=\"text\" class=\"txt_fieldR\" style=\"width:35\" onKeyUp=\"javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }\" />%";



        }else{
            var tBody = document.getElementById("itemtable");
            var ttlength = tBody.rows.length;
            tBody.deleteRow(ttlength-2);
        }
}

function sumObj(obj){

    resetClear();

    if(document.getElementById("itemtable").rows.length<1 && document.SelectProgram.selfcheck.checked == false) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02515") %><%--정보를 입력하십시오--%>');
        return;
    }

    var sumcarName = "";
    var sumy1 = 0 ;
    var sumy2 = 0 ;
    var sumy3 = 0 ;
    var sumy4 = 0 ;
    var sumy5 = 0 ;
    var sumy6 = 0 ;
    var sumy7 = 0 ;
    var sumy8 = 0 ;
    var sumy9 = 0 ;
    var sumy10 = 0 ;
    var sumsum = 0 ;
    var sumsum2 = 0 ;

    var totalcarName = "";
    var totaly1 = 0 ;
    var totaly2 = 0 ;
    var totaly3 = 0 ;
    var totaly4 = 0 ;
    var totaly5 = 0 ;
    var totaly6 = 0 ;
    var totaly7 = 0 ;
    var totaly8 = 0 ;
    var totaly9 = 0 ;
    var totaly10 = 0 ;
    var total = 0 ;
    var totalusage = 0 ;

    var sumusage = 0 ;
    var sumoptionRate = 0 ;
    var carName = document.all.item("carName");

    var y1 = document.all.item("y1");
    var y2 = document.all.item("y2");
    var y3 = document.all.item("y3");
    var y4 = document.all.item("y4");
    var y5 = document.all.item("y5");
    var y6 = document.all.item("y6");
    var y7 = document.all.item("y7");
    var y8 = document.all.item("y8");
    var y9 = document.all.item("y9");
    var y10 = document.all.item("y10");

    //alert(carName+" : " +y1+" : " +y2+" : " +y3+" : " +y4+" : " +y5+" : " +y6);


    var sum = document.all.item("sum");
    var usage = document.all.item("usage");
    var optionRate = document.all.item("optionRate");

    if(document.SelectProgram.selfcheck.checked == true){
        if (document.SelectProgram.carName.value == ''){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02753") %><%--차종을 입력해 주십시오--%>');
            return;
        }
        if (document.SelectProgram.usage.value == ''){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02747") %><%--차종 별 U/S를 입력해 주십시오--%>');
            return;
        }
        if (document.SelectProgram.optionRate.value == ''){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02170") %><%--옵션률을 입력해 주십시오--%>');
            return;
        }

        var form = document.SelectProgram;
        sumcarName = form.carName.value;
        sumy1 = parseInt(form.y1.value);
        sumy2 = parseInt(form.y2.value);
        sumy3 = parseInt(form.y3.value);
        sumy4 = parseInt(form.y4.value);
        sumy5 = parseInt(form.y5.value);
        sumy6 = parseInt(form.y6.value);
        /*
        sumy7 = parseInt(form.y7.value);
        sumy8 = parseInt(form.y8.value);
        sumy9 = parseInt(form.y9.value);
        sumy10 = parseInt(form.y10.lue);
        */
        sumy7 = 0 ;
        sumy8 = 0 ;
        sumy9 = 0 ;
        sumy10 = 0 ;


        sumusage = parseInt(form.usage.value);
        sumoptionRate = parseInt(form.optionRate.value);

        if(sumy1==Infinity || isNaN(sumy1)) sumy1 = 0;
        if(sumy2==Infinity || isNaN(sumy2)) sumy2 = 0;
        if(sumy3==Infinity || isNaN(sumy3)) sumy3 = 0;
        if(sumy4==Infinity || isNaN(sumy4)) sumy4 = 0;
        if(sumy5==Infinity || isNaN(sumy5)) sumy5 = 0;
        if(sumy6==Infinity || isNaN(sumy6)) sumy6 = 0;
        /*
        if(sumy7==Infinity || isNaN(sumy7)) sumy7 = 0;
        if(sumy8==Infinity || isNaN(sumy8)) sumy8 = 0;
        if(sumy9==Infinity || isNaN(sumy9)) sumy9 = 0;
        if(sumy10==Infinity || isNaN(sumy10)) sumy10 = 0;
        */

        if(sumusage==Infinity || isNaN(sumusage)) sumusage = 0;
        if(sumoptionRate==Infinity || isNaN(sumoptionRate)) sumoptionRate = 0;
        if(sumsum==Infinity || isNaN(sumsum)) sumsum = 0;

        sumsum = parseInt(sumy1 + sumy2 + sumy3 + sumy4 + sumy5 + sumy6 + sumy7 + sumy8 + sumy9 + sumy10 );

        totalusage=sumusage;

        sumy1 = Math.round( (sumy1 * sumusage * sumoptionRate) /100 );
        sumy2 = Math.round( (sumy2 * sumusage *  sumoptionRate) /100 );
        sumy3 = Math.round( (sumy3 * sumusage *  sumoptionRate) /100 );
        sumy4 = Math.round( (sumy4 * sumusage *  sumoptionRate) /100 );
        sumy5 = Math.round( (sumy5 * sumusage *  sumoptionRate) /100 );
        sumy6 = Math.round( (sumy6 * sumusage *  sumoptionRate) /100 );
        sumy7 = Math.round( (sumy7 * sumusage *  sumoptionRate) /100 );
        sumy8 = Math.round( (sumy8 * sumusage *  sumoptionRate) /100 );
        sumy9 = Math.round( (sumy9 * sumusage *  sumoptionRate) /100 );
        sumy10 = Math.round( (sumy10 * sumusage *  sumoptionRate) /100 );

        sumsum2 = Math.round( (sumsum * sumusage *  sumoptionRate)/100 );

        if(totalcarName==""){
            totalcarName = sumcarName ;
        }

        totaly1 = parseInt(totaly1) + parseInt(sumy1);
        totaly2 = parseInt(totaly2) + parseInt(sumy2);
        totaly3 = parseInt(totaly3) + parseInt(sumy3);
        totaly4 = parseInt(totaly4) + parseInt(sumy4);
        totaly5 = parseInt(totaly5) + parseInt(sumy5);
        totaly6 = parseInt(totaly6) + parseInt(sumy6);
        totaly7 = parseInt(totaly7) + parseInt(sumy7);
        totaly8 = parseInt(totaly8) + parseInt(sumy8);
        totaly9 = parseInt(totaly9) + parseInt(sumy9);
        totaly10 = parseInt(totaly10) + parseInt(sumy10);

        total = parseInt(total) + parseInt(sumsum2);

    }else{
        var target = document.getElementById("itemtable");
        if(target.rows.length>1){

            for(var i = 0 ; i < sum.length ; i++){
                if (usage[i].value == ''){
                    alert(i+1+'<%=messageService.getString("e3ps.message.ket_message", "00034") %><%--{0}번째 차종 별 U/S를 입력해 주십시오--%>');
                    return;
                }
                if (optionRate[i].value == ''){
                    alert(i+1+'<%=messageService.getString("e3ps.message.ket_message", "00033") %><%--{0}번째 옵션률을 입력해 주십시오--%>');
                    return;
                }

                sumcarName = carName[i].value;
                sumy1 = parseInt(y1[i].value);
                sumy2 = parseInt(y2[i].value);
                sumy3 = parseInt(y3[i].value);
                sumy4 = parseInt(y4[i].value);
                sumy5 = parseInt(y5[i].value);
                sumy6 = parseInt(y6[i].value);
                sumy7 = parseInt(y7[i].value);
                sumy8 = parseInt(y8[i].value);
                sumy9 = parseInt(y9[i].value);
                sumy10 = parseInt(y10[i].value);

                sumsum = parseInt(sum[i].value);

                sumusage = parseInt(usage[i].value);
                sumoptionRate = parseInt(optionRate[i].value);

                if(sumy1==Infinity || isNaN(sumy1)) sumy1 = 0;
                if(sumy2==Infinity || isNaN(sumy2)) sumy2 = 0;
                if(sumy3==Infinity || isNaN(sumy3)) sumy3 = 0;
                if(sumy4==Infinity || isNaN(sumy4)) sumy4 = 0;
                if(sumy5==Infinity || isNaN(sumy5)) sumy5 = 0;
                if(sumy6==Infinity || isNaN(sumy6)) sumy6 = 0;
                if(sumy7==Infinity || isNaN(sumy7)) sumy7 = 0;
                if(sumy8==Infinity || isNaN(sumy8)) sumy8 = 0;
                if(sumy9==Infinity || isNaN(sumy9)) sumy9 = 0;
                if(sumy10==Infinity || isNaN(sumy10)) sumy10 = 0;

                if(sumusage==Infinity || isNaN(sumusage)) sumusage = 0;
                if(sumoptionRate==Infinity || isNaN(sumoptionRate)) sumoptionRate = 0;
                if(sumsum==Infinity || isNaN(sumsum)) sumsum = 0;

                if(totalusage<sumusage){
                    totalusage=sumusage;
                }

                sumy1 = Math.round( (sumy1 * sumusage *  sumoptionRate)/100);
                sumy2 = Math.round( (sumy2 * sumusage *  sumoptionRate)/100);
                sumy3 = Math.round( (sumy3 * sumusage *  sumoptionRate)/100);
                sumy4 = Math.round( (sumy4 * sumusage *  sumoptionRate)/100);
                sumy5 = Math.round( (sumy5 * sumusage *  sumoptionRate)/100);
                sumy6 = Math.round( (sumy6 * sumusage *  sumoptionRate)/100);
                sumy7 = Math.round( (sumy7 * sumusage *  sumoptionRate)/100);
                sumy8 = Math.round( (sumy8 * sumusage *  sumoptionRate)/100);
                sumy9 = Math.round( (sumy9 * sumusage *  sumoptionRate)/100);
                sumy10 = Math.round( (sumy10 * sumusage *  sumoptionRate)/100);

                sumsum2 = Math.round( (sumsum * sumusage * sumoptionRate) /100);

                if(totalcarName==""){
                    totalcarName = sumcarName ;
                }else{
                    totalcarName = totalcarName + ", " + sumcarName;
                }

                if(totaly1==Infinity || isNaN(totaly1)) totaly1 = 0;
                if(totaly2==Infinity || isNaN(totaly2)) totaly2 = 0;
                if(totaly3==Infinity || isNaN(totaly3)) totaly3 = 0;
                if(totaly4==Infinity || isNaN(totaly4)) totaly4 = 0;
                if(totaly5==Infinity || isNaN(totaly5)) totaly5 = 0;
                if(totaly6==Infinity || isNaN(totaly6)) totaly6 = 0;
                if(totaly7==Infinity || isNaN(totaly7)) totaly7 = 0;
                if(totaly8==Infinity || isNaN(totaly8)) totaly8 = 0;
                if(totaly9==Infinity || isNaN(totaly9)) totaly9 = 0;
                if(totaly10==Infinity || isNaN(totaly10)) totaly10 = 0;



                if(total==Infinity || isNaN(total)) total = 0;
                if(sumsum2==Infinity || isNaN(sumsum2)) sumsum2 = 0;

                totaly1 = parseInt(totaly1) + parseInt(sumy1);
                totaly2 = parseInt(totaly2) + parseInt(sumy2);
                totaly3 = parseInt(totaly3) + parseInt(sumy3);
                totaly4 = parseInt(totaly4) + parseInt(sumy4);
                totaly5 = parseInt(totaly5) + parseInt(sumy5);
                totaly6 = parseInt(totaly6) + parseInt(sumy6);
                totaly7 = parseInt(totaly7) + parseInt(sumy7);
                totaly8 = parseInt(totaly8) + parseInt(sumy8);
                totaly9 = parseInt(totaly9) + parseInt(sumy9);
                totaly10 = parseInt(totaly10) + parseInt(sumy10);

                total = parseInt(total) + parseInt(sumsum2);
            }
        }else{
            if (usage.value == ''){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02747") %><%--차종 별 U/S를 입력해 주십시오--%>');
                return;
            }
            if (optionRate.value == ''){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02170") %><%--옵션률을 입력해 주십시오--%>');
                return;
            }

            sumcarName = document.SelectProgram.carName.value;
            sumy1 = parseInt(document.SelectProgram.y1.value);
            sumy2 = parseInt(document.SelectProgram.y2.value);
            sumy3 = parseInt(document.SelectProgram.y3.value);
            sumy4 = parseInt(document.SelectProgram.y4.value);
            sumy5 = parseInt(document.SelectProgram.y5.value);
            sumy6 = parseInt(document.SelectProgram.y6.value);
            sumy7 = parseInt(document.SelectProgram.y7.value);
            sumy8 = parseInt(document.SelectProgram.y8.value);
            sumy9 = parseInt(document.SelectProgram.y9.value);
            sumy10 = parseInt(document.SelectProgram.y10.value);


            sumsum = parseInt(document.SelectProgram.sum.value);

            sumusage = parseInt(document.SelectProgram.usage.value);
            sumoptionRate = parseInt(document.SelectProgram.optionRate.value);

            if(sumy1==Infinity || isNaN(sumy1)) sumy1 = 0;
            if(sumy2==Infinity || isNaN(sumy2)) sumy2 = 0;
            if(sumy3==Infinity || isNaN(sumy3)) sumy3 = 0;
            if(sumy4==Infinity || isNaN(sumy4)) sumy4 = 0;
            if(sumy5==Infinity || isNaN(sumy5)) sumy5 = 0;
            if(sumy6==Infinity || isNaN(sumy6)) sumy6 = 0;
            if(sumy7==Infinity || isNaN(sumy7)) sumy7 = 0;
            if(sumy8==Infinity || isNaN(sumy8)) sumy8 = 0;
            if(sumy9==Infinity || isNaN(sumy9)) sumy9 = 0;
            if(sumy10==Infinity || isNaN(sumy10)) sumy10 = 0;
            if(sumusage==Infinity || isNaN(sumusage)) sumusage = 0;
            if(sumoptionRate==Infinity || isNaN(sumoptionRate)) sumoptionRate = 0;
            if(sumsum==Infinity || isNaN(sumsum)) sumsum = 0;



            if(totalusage<sumusage){
                totalusage=parseInt(sumusage);
            }

            sumy1 = Math.round( (sumy1 * sumusage *  sumoptionRate) /100 );
            sumy2 = Math.round( (sumy2 * sumusage *  sumoptionRate) /100 );
            sumy3 = Math.round( (sumy3 * sumusage *  sumoptionRate) /100 );
            sumy4 = Math.round( (sumy4 * sumusage *  sumoptionRate) /100 );
            sumy5 = Math.round( (sumy5 * sumusage *  sumoptionRate) /100 );
            sumy6 = Math.round( (sumy6 * sumusage *  sumoptionRate) /100 );
            sumy7 = Math.round( (sumy7 * sumusage *  sumoptionRate) /100 );
            sumy8 = Math.round( (sumy8 * sumusage *  sumoptionRate) /100 );
            sumy9 = Math.round( (sumy9 * sumusage *  sumoptionRate) /100 );
            sumy10 = Math.round( (sumy10 * sumusage *  sumoptionRate) /100 );
            sumsum2 = Math.round( (sumsum * sumusage *  sumoptionRate) /100 );
            if(totalcarName==""){
                totalcarName = sumcarName ;
            }else{
                totalcarName = totalcarName + ", " + sumcarName;
            }

            totaly1 = totaly1 + sumy1;
            totaly2 = totaly2 + sumy2;
            totaly3 = totaly3 + sumy3;
            totaly4 = totaly4 + sumy4;
            totaly5 = totaly5 + sumy5;
            totaly6 = totaly6 + sumy6;
            totaly7 = totaly7 + sumy7;
            totaly8 = totaly8 + sumy8;
            totaly9 = totaly9 + sumy9;
            totaly10 = totaly10 + sumy10;

            total = total + sumsum2;
        }
    }

    if(totalcarName == "undefined"){
        totalcarName = document.SelectProgram.carName.value;
    }
    var printCar =  document.getElementById("totalcarName");
    printCar.innerHTML  ="<span style='width:75;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'>"+totalcarName+"</span>";

    var printy1 =  document.getElementById("totaly1");
    //printy1.innerHTML  = totaly1;
    printy1.innerHTML = "<input type='text' name = 'totaly1' class='txt_fieldR' style='width:42' value=" + totaly1 + ">";
    var printy2 =  document.getElementById("totaly2");
    //printy2.innerHTML  = totaly2;
    printy2.innerHTML = "<input type='text' name = 'totaly2' class='txt_fieldR' style='width:42' value=" + totaly2 + ">";

    var printy3 =  document.getElementById("totaly3");
    //printy3.innerHTML  = totaly3;
    printy3.innerHTML = "<input type='text' name = 'totaly3' class='txt_fieldR' style='width:42' value=" + totaly3 + ">";

    var printy4 =  document.getElementById("totaly4");
    //printy4.innerHTML  = totaly4;
    printy4.innerHTML = "<input type='text' name = 'totaly4' class='txt_fieldR' style='width:42' value=" + totaly4 + ">";

    var printy5 =  document.getElementById("totaly5");
    //printy5.innerHTML  = totaly5;
    printy5.innerHTML = "<input type='text' name = 'totaly5' class='txt_fieldR' style='width:42' value=" + totaly5 + ">";

    var printy6 =  document.getElementById("totaly6");

    var printy7 =  document.getElementById("totaly7");
    var printy8 =  document.getElementById("totaly8");
    var printy9 =  document.getElementById("totaly9");
    var printy10 =  document.getElementById("totaly10");

    var printyTotal678910 = parseInt(totaly6)+ parseInt(totaly7) + parseInt(totaly8) + parseInt(totaly9) + parseInt(totaly10) ;

    printy6.innerHTML = "<input type='text' name = 'totaly678910' class='txt_fieldR' style='width:42' value=" + printyTotal678910 + ">";

    var printtotal =  document.getElementById("total");
    printtotal.innerHTML = "<input type='text' name = 'total' class='txt_fieldR' style='width:42' value=" + total + ">";


    if(document.SelectProgram.selfcheck.checked == true){
        var printtotal2 =  document.getElementById("total2");
        if(printtotal2){
            printtotal2.innerHTML = "<input type='text' name = 'sumsum' class='txt_fieldR' style='width:42' value=" + total + ">";
        }
    }

    var printusage =  document.getElementById("totalusage");
    //printusage.innerHTML  = totalusage;
    printusage.innerHTML = "<input type='text' name = 'totalusage' class='txt_fieldR' style='width:42' value=" + totalusage + ">";

    var arr = new Array();
    arr[0] = totalcarName;
    if(totalcarName == "") arr[0] = "nodata";
    arr[1] = totaly1;
    arr[2] = totaly2;
    arr[3] = totaly3;
    arr[4] = totaly4;
    arr[5] = totaly5;
    arr[6] = totaly6;
    arr[7] = totaly7;
    arr[8] = totaly8;
    arr[9] = totaly9;
    arr[10] = totaly10;
    arr[11] = total;
    arr[12] = totalusage;
    if(document.SelectProgram.selfcheck.checked == true){
        arr[13] = sumsum;
    }
    if(obj == "set"){
        Complete(arr);
    }
}

function Complete(objArr) {

    var form = document.SelectProgram;
    if(form.total.value == 0 ){
        resetClear();
        alert('<%=messageService.getString("e3ps.message.ket_message", "03154") %><%--합계 계산 버튼을 클릭 하여 주십시오--%>');
    }
    var arr = new Array();
    var subArr = new Array();
    var idx = 0;
    var target = document.getElementById("itemtable");
    var len = target.rows.length;
    if(len>1){
        arr[0] = objArr;
        for(var i = 0; i < len;i++) {
            subArr = new Array();
            subArr[0] = form.itemtableOid[i].value;
            subArr[1] = form.carName[i].value;
            subArr[2] = form.y1[i].value;
            subArr[3] = form.y2[i].value;
            subArr[4] = form.y3[i].value;
            subArr[5] = form.y4[i].value;
            subArr[6] = form.y5[i].value;
            subArr[7] = form.y6[i].value;
            subArr[8] = 0;
            subArr[9] = 0;
            subArr[10] = 0;
            subArr[11] = 0;
            subArr[12] = form.sum[i].value;
            subArr[13] = form.usage[i].value;
            subArr[14] = form.optionRate[i].value;
            arr[i+1] = subArr;

        }
    }else {
        arr[0] = objArr;
        if(form.selfcheck.checked == true) {
            subArr = new Array();
            subArr[0] = "nodata";
            subArr[1] = form.carName.value;
            subArr[2] = form.y1.value;
            subArr[3] = form.y2.value;
            subArr[4] = form.y3.value;
            subArr[5] = form.y4.value;
            subArr[6] = form.y5.value;
            subArr[7] = form.y6.value;
            subArr[8] = 0;
            subArr[9] = 0;
            subArr[10] = 0;
            subArr[11] = 0;
            subArr[12] = arr[0][13];
            subArr[13] = form.usage.value;
            subArr[14] = form.optionRate.value;

            arr[1] = subArr;
        }else{
                subArr = new Array();
                subArr[0] = form.itemtableOid.value
                subArr[1] = form.carName.value;
                subArr[2] = form.y1.value;
                subArr[3] = form.y2.value;
                subArr[4] = form.y3.value;
                subArr[5] = form.y4.value;
                subArr[6] = form.y5.value;
                subArr[7] = form.y6.value;
                subArr[8] = 0;
                subArr[9] = 0;
                subArr[10] = 0;
                subArr[11] = 0;
                subArr[12] = arr[0][13];
                subArr[13] = form.usage.value;
                subArr[14] = form.optionRate.value;
        }
                arr[1] = subArr;
    }
    
    <%  if ( invokeMethod.length() == 0 ) {  %>
	    //modal dialog
	    selectModalDialog(arr);
	<%  } else {  %>
	    //open window
	    selectOpenWindow(arr);
	<%  }  %>

}

function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
    window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function selectOpenWindow(arrObj) {
    //...이하 원하는 스크립트를 만들어서 작업...
    if(opener) {
        if(opener.<%=invokeMethod%>) {
            opener.<%=invokeMethod%>(arrObj);
        }
    }else if(parent.opener) {
        if(parent.opener.<%=invokeMethod%>) {
            parent.opener.<%=invokeMethod%>(arrObj);
        }
    }
    self.close();
}

<%  }  %>

    function deleteProjectInfo() {
        var form = document.SelectProgram;
        var tBody = document.getElementById("itemtable");
        var index = tBody.rows.length;
        if(index == 1){
            if(form.itemtableOid.checked){
                tBody.deleteRow(0);
            }

        }else{
            for(var i = index-1; i >= 0; i--){
                if(form.itemtableOid[i].checked){
                    tBody.deleteRow(i);
                }
            }

        }
        resetClear();
        return;
    }

    function onDeleteTableRow2(tableid, chk_name, chk_value) {
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
    function resetClear(){
        //clearCartype();
        var totalcarName = document.getElementById("totalcarName");
        var totaly1 = document.getElementById("totaly1");
        var totaly2 = document.getElementById("totaly2");
        var totaly3 = document.getElementById("totaly3");
        var totaly4 = document.getElementById("totaly4");
        var totaly5 = document.getElementById("totaly5");
        var totaly6 = document.getElementById("totaly6");
        var total = document.getElementById("total");
        var totalusage = document.getElementById("totalusage");

        totalcarName.innerHTML  = "&nbsp;";
        totaly1.innerHTML  = "&nbsp;";
        totaly2.innerHTML  = "&nbsp;";
        totaly3.innerHTML  = "&nbsp;";
        totaly4.innerHTML  = "&nbsp;";
        totaly5.innerHTML  = "&nbsp;";
        totaly6.innerHTML  = "&nbsp;";
        total.innerHTML  = "&nbsp;";
        totalusage.innerHTML  = "&nbsp;";

    }
</script>
</head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%if(check.equals("1")){ %>
<body onLoad="selDisable();">
<%}else{ %>
<body onLoad="javascript:clearCartype();onClickNation('국내');">
    <%
        }
    %>
    <form name="SelectProgram">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01178")%><%--년도별 예상수량 지정--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top">
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table width="100%">
                                    <tr>
                                        <td></td>
                                        <td align="right">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="">&nbsp;</td>
                                                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onclick="javascript:displayChangeOEMType();"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><%--추가--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onclick="javascript:self.close();"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%>
                                            <span class="red">*</span></td>
                                        <td width="140" class="tdwhiteM">
                                            <%
                                                for (int i = 0; i < customerEventNumCode.size(); i++) {
                                            %> <input id="countryType" name="radio" type="radio" class="Checkbox"
                                            value="<%=customerEventNumCode.get(i).get("code")%>"
                                            onclick="javascript:onClickNation(this);javascript:clearCartype();"
                                            <%if (customerEventNumCode.get(i).get("code").equals("1000")) {%> checked <%}%>><%=customerEventNumCode.get(i).get("value")%>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%>
                                            <span class="red">*</span></td>
                                        <td width="100" class="tdwhiteM"><select name='customer' id='customer' class="fm_jmp"
                                            style="width: 80" onchange="javascript:onChangeCustomer(this);">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                        </select></td>
                                        <td width="90" class="tdblueM"><%=projectType == null ? " " : projectType%><span class="red">*</span></td>
                                        <td width="100" class="tdwhiteL"><select name='cartype' id='cartype' class="fm_jmp"
                                            style="width: 80">
                                                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                        </select></td>
                                        <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02720")%><%--직접입력--%></td>
                                        <td width="30" class="tdwhiteM0">
                                            <input type="checkbox" name="selfcheck" id="selfcheck" <%if (check.equals("1")) {%> checked <%}%> onClick="selDisable();selfCreate();"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space15"></td>
                                    </tr>
                                </table>
                                <table width="700" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <!--<td>● 선택한 <%=projectType == null ? " " : projectType%>목록&nbsp;&nbsp;&nbsp;&nbsp;[단위:천대]</td>-->
                                        <td>● <%=messageService.getString("e3ps.message.ket_message", "01820", (projectType == null ? " " : projectType))%><%--선택한 {0} 목록 [단위:천대]--%></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:sumObj('sum');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03152") %><%--합계 계산--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:sumObj('set');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03228") %><%--확정--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    <td width="5">&nbsp;</td>
                                                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:;" onclick="javascript:deleteProjectInfo();"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>    
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>                               
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td>

                                            <DIV style="width: 700; height: 280; overflow: auto">
                                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                                    <tr>
                                                        <td class="tab_btm2"></td>
                                                    </tr>
                                                </table>
                                                <%
                                                    //style="table-layout:fixed;"
                                                %>
                                                <table width="700" border="0" cellpadding="0" cellspacing="0">
                                                    <tr height="20">
                                                        <td width="30" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></td>
                                                        <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%></td>
                                                        <td width="90" class="tdblueM"><%=projectType == null ? " " : projectType%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 1)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 2)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 3)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 4)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 5)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 6)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03151")%><%--합계--%></td>
                                                        <td width="50" class="tdblueM">U/S<span class="red">*</span></td>
                                                        <td width="70" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02166")%><%--옵션률--%>
                                                            <span class="red">*</span></td>
                                                    </tr>
                                                    <TBODY id="itemtable">
                                                        <%
                                                            int count = 0;
                                                            if (optOid != null) {
                                                        		Kogger.debug("############################################### SelectProgram List Type 1");
                                                        		Kogger.debug("########optOid.length :" + optOid.length);
                                                        		for (int i = 0; i < optOid.length; i++) {
                                                        		    if (optOid[i] != null && optOid[i].length() > 0) {
                                                        			if ("nodata".equals(optOid[i])) {

                                                        			} else {

                                                        			    long oidLong = 0;

                                                        			    if (!optOid[i].equals("undefined")) {
                                                        				oidLong = CommonUtil.getOIDLongValue(optOid[i]);
                                                        			    } else if (optOidOne != null) {
                                                        				//oidLong = CommonUtil.getOIDLongValue(optOidOne);
                                                        			    }
                                                        			    QuerySpec qs = new QuerySpec();
                                                        			    int idx = qs.addClassList(ModelPlan.class, true);

                                                        			    SearchCondition sc = new SearchCondition(ModelPlan.class, "carTypeReference.key.id", "=", oidLong);
                                                        			    qs.appendWhere(sc, new int[] { idx });

                                                        			    QueryResult qr = PersistenceHelper.manager.find(qs);
                                                        			    if (qr.hasMoreElements()) {
                                                        				Object o[] = (Object[]) qr.nextElement();
                                                        				ModelPlan mp = (ModelPlan) o[0];
                                                        				Kogger.debug("########### itemtableOid :" + mp.getCarType().getPersistInfo().getObjectIdentifier().getStringValue());

                                                        				int y1 = Integer.parseInt(mp.getYield1() == null ? "0" : mp.getYield1());
                                                        				int y2 = Integer.parseInt(mp.getYield2() == null ? "0" : mp.getYield2());
                                                        				int y3 = Integer.parseInt(mp.getYield3() == null ? "0" : mp.getYield3());
                                                        				int y4 = Integer.parseInt(mp.getYield4() == null ? "0" : mp.getYield4());
                                                        				int y5 = Integer.parseInt(mp.getYield5() == null ? "0" : mp.getYield5());

                                                        				int y6 = Integer.parseInt(mp.getYield6() == null ? "0" : mp.getYield6());
                                                        				int y7 = Integer.parseInt(mp.getYield7() == null ? "0" : mp.getYield7());
                                                        				int y8 = Integer.parseInt(mp.getYield8() == null ? "0" : mp.getYield8());
                                                        				int y9 = Integer.parseInt(mp.getYield9() == null ? "0" : mp.getYield9());
                                                        				int y10 = Integer.parseInt(mp.getYield10() == null ? "0" : mp.getYield10());

                                                        				int sum2 = y6 + y7 + y8 + y9 + y10;
                                                        				int sum = y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10;
                                                        %>
                                                        <tr>
                                                            <td width="30" class="tdwhiteM"><input name="itemtableOid" type="checkbox"
                                                                value='<%=mp.getCarType().getPersistInfo().getObjectIdentifier().getStringValue()%>'>
                                                                <input name="count" type="hidden" value='<%=count%>'></td>

                                                            <td width="90" class="tdwhiteL"><%=mp.getCarType().getMaker().getName()%>&nbsp;</td>
                                                            <td width="90" class="tdwhiteL"><input type="hidden" name="carName"
                                                                value="<%=mp.getCarType().getName() == null ? "" : mp.getCarType().getName()%>">
                                                                <%=mp.getCarType().getName() == null ? "" : mp.getCarType().getName()%>&nbsp;</td>
                                                            <td width="50" class="tdwhiteR"><input type="hidden" name="y1"
                                                                value="<%=mp.getYield1() == null ? "" : mp.getYield1()%>"> &nbsp;<%=mp.getYield1() == null ? "" : mp.getYield1()%></td>
                                                            <td width="50" class="tdwhiteR"><input type="hidden" name="y2"
                                                                value="<%=mp.getYield2() == null ? "" : mp.getYield2()%>"> &nbsp;<%=mp.getYield2() == null ? "" : mp.getYield2()%></td>
                                                            <td width="50" class="tdwhiteR"><input type="hidden" name="y3"
                                                                value="<%=mp.getYield3() == null ? "" : mp.getYield3()%>"> &nbsp;<%=mp.getYield3() == null ? "" : mp.getYield3()%></td>
                                                            <td width="50" class="tdwhiteR"><input type="hidden" name="y4"
                                                                value="<%=mp.getYield4() == null ? "" : mp.getYield4()%>"> &nbsp;<%=mp.getYield4() == null ? "" : mp.getYield4()%></td>
                                                            <td width="50" class="tdwhiteR"><input type="hidden" name="y5"
                                                                value="<%=mp.getYield5() == null ? "" : mp.getYield5()%>"> &nbsp;<%=mp.getYield5() == null ? "" : mp.getYield5()%></td>
                                                            <td width="50" class="tdwhiteR">&nbsp;<%=sum2%> <input type="hidden"
                                                                name="y6" value="<%=mp.getYield6() == null ? "" : mp.getYield6()%>"> <input
                                                                type="hidden" name="y7" value="<%=mp.getYield7() == null ? "" : mp.getYield7()%>">
                                                                <input type="hidden" name="y8"
                                                                value="<%=mp.getYield8() == null ? "" : mp.getYield8()%>"> <input
                                                                type="hidden" name="y9" value="<%=mp.getYield9() == null ? "" : mp.getYield9()%>">
                                                                <input type="hidden" name="y10"
                                                                value="<%=mp.getYield10() == null ? "" : mp.getYield10()%>"></td>
                                                            <td width="50" class="tdgrayR"><input type="hidden" name="sum"
                                                                value="<%=sum%>"><%=sum%></td>
                                                            <td width="50" class="tdwhiteR"><input type="text" class="txt_fieldR"
                                                                name="usage" value="<%=usage[i].toString()%>" style="width: 45"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td width="70" class="tdwhiteR0"><input type="text" class="txt_fieldR"
                                                                name="optionRate" value="<%=optionRate[i].toString()%>" style="width: 35"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }">%</td>
                                                        </tr>
                                                        <%
                                                            }//if
                                                        			}//else
                                                        		    }//if
                                                        		    count++;
                                                        		}//for
                                                            }

                                                            if (pOid != null) {
                                                        		Kogger.debug("############################################### SelectProgram List Type 2");
                                                        		Kogger.debug("########pOid.length :" + pOid.length);

                                                        		for (int i = 0; i < pOid.length; i++) {
                                                        		    if (pOid[i] != null && pOid[i].length() > 0) {
                                                        			Long oidLong = CommonUtil.getOIDLongValue(pOid[i]);

                                                        			QuerySpec qs = new QuerySpec();
                                                        			int idx = qs.addClassList(ModelInfo.class, true);

                                                        			SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id", "=", oidLong);
                                                        			qs.appendWhere(sc, new int[] { idx });

                                                        			QueryResult qr = PersistenceHelper.manager.find(qs);
                                                        			Kogger.debug("########ModelInfo qr :" + qr.size());
                                                        			while (qr.hasMoreElements()) {
                                                        			    Object o[] = (Object[]) qr.nextElement();
                                                        			    ModelInfo mi = (ModelInfo) o[0];
                                                        			    int y1 = Integer.parseInt(mi.getYear1() == null ? "0" : mi.getYear1());
                                                        			    int y2 = Integer.parseInt(mi.getYear2() == null ? "0" : mi.getYear2());
                                                        			    int y3 = Integer.parseInt(mi.getYear3() == null ? "0" : mi.getYear3());
                                                        			    int y4 = Integer.parseInt(mi.getYear4() == null ? "0" : mi.getYear4());
                                                        			    int y5 = Integer.parseInt(mi.getYear5() == null ? "0" : mi.getYear5());
                                                        			    int y6 = Integer.parseInt(mi.getYear6() == null ? "0" : mi.getYear6());
                                                        			    int y7 = Integer.parseInt(mi.getYear7() == null ? "0" : mi.getYear7());
                                                        			    int y8 = Integer.parseInt(mi.getYear8() == null ? "0" : mi.getYear8());
                                                        			    int y9 = Integer.parseInt(mi.getYear9() == null ? "0" : mi.getYear9());
                                                        			    int y10 = Integer.parseInt(mi.getYear10() == null ? "0" : mi.getYear10());
                                                        			    int sum = y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10;
                                                        			    int sumY6 = y6 + y7 + y8 + y9 + y10;
                                                        			    if (mi.getModel() == null) {
                                                        %>
                                                        <tr>
                                                            <td class="tdwhiteM" colspan=2>-</td>
                                                            <td class="tdwhiteL"><input name="carName" type="text" class="txt_field"
                                                                style="width: 85" value="<%=mi.getName() == null ? "" : mi.getName()%>"></td>
                                                            <td class="tdwhiteR"><input type="text" style="width: 45"
                                                                class="txt_fieldR" name="y1" value="<%=y1%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td class="tdwhiteR"><input type="text" style="width: 45"
                                                                class="txt_fieldR" name="y2" value="<%=y2%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td class="tdwhiteR"><input type="text" style="width: 45"
                                                                class="txt_fieldR" name="y3" value="<%=y3%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td class="tdwhiteR"><input type="text" style="width: 45"
                                                                class="txt_fieldR" name="y4" value="<%=y4%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td class="tdwhiteR"><input type="text" style="width: 45"
                                                                class="txt_fieldR" name="y5" value="<%=y5%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td class="tdwhiteR"><input type="text" style="width: 45"
                                                                class="txt_fieldR" name="y6" value="<%=sumY6%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td class="tdgrayR"><input type="hidden" style="width: 45" name="sum"
                                                                value="<%=sum%>">&nbsp;<%=sum%></td>
                                                            <td class="tdwhiteR"><input name="usage" type="text" class="txt_fieldR"
                                                                style="width: 45" value="<%=mi.getUsage()%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }" /></td>
                                                            <td class="tdwhiteR0"><input name="optionRate" type="text"
                                                                class="txt_fieldR" style="width: 35" value="<%=mi.getOptionRate()%>"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }" />%</td>

                                                        </tr>
                                                        <%
                                                            } else {
                                                        				Kogger.debug("############################################### SelectProgram List Type 2 - 1");
                                                        				Kogger.debug("########### itemtableOid :" + mi.getModel().getPersistInfo().getObjectIdentifier().getStringValue());
                                                        %>
                                                        <tr>
                                                            <td class="tdwhiteM"><input type="checkbox" name="itemtableOid"
                                                                value="<%=mi.getModel().getPersistInfo().getObjectIdentifier().getStringValue()%>">
                                                                <input type="hidden" name="count" value="<%=count%>"></td>
                                                            <td class="tdwhiteR" style="text-align: left"><%=mi.getModel().getMaker().getName()%>&nbsp;</td>
                                                            <td class="tdwhiteR" style="text-align: left"><input type="hidden" name="carName"
                                                                value="<%=mi.getModel().getName() == null ? "" : mi.getModel().getName()%>">
                                                                <%=mi.getModel().getName() == null ? "" : mi.getModel().getName()%>&nbsp;</td>
                                                            <td class="tdwhiteR"><input type="hidden" name="y1" value="<%=y1%>">
                                                                &nbsp;<%=y1%></td>
                                                            <td class="tdwhiteR"><input type="hidden" name="y2" value="<%=y2%>">
                                                                &nbsp;<%=y2%></td>
                                                            <td class="tdwhiteR"><input type="hidden" name="y3" value="<%=y3%>">
                                                                &nbsp;<%=y3%></td>
                                                            <td class="tdwhiteR"><input type="hidden" name="y4" value="<%=y4%>">
                                                                &nbsp;<%=y4%></td>
                                                            <td class="tdwhiteR"><input type="hidden" name="y5" value="<%=y5%>">
                                                                &nbsp;<%=y5%></td>
                                                            <td class="tdwhiteR"><input type="hidden" name="y6" value="<%=y6%>">
                                                                <input type="hidden" name="y7" value="<%=y7%>"> <input
                                                                type="hidden" name="y8" value="<%=y8%>"> <input type="hidden"
                                                                name="y9" value="<%=y9%>"> <input type="hidden" name="y10"
                                                                value="<%=y10%>"> &nbsp;<%=sumY6%></td>
                                                            <td class="tdgrayR">&nbsp;<%=sum%><input type="hidden" name="sum"
                                                                value="<%=sum %>"></td>
                                                            <td class="tdwhiteR"><input type="text" class="txt_fieldR" name="usage"
                                                                value="<%=mi.getUsage()%>" style="width: 45"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td class="tdwhiteR0"><input type="text" class="txt_fieldR"
                                                                name="optionRate" value="<%=mi.getOptionRate()!=null?mi.getOptionRate():""%>" style="width: 35"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }">%</td>
                                                        </tr>
                                                        <%
                                                                          }//else
                                                                      }//while
                                                                      count++;
                                                                    }//if
                                                                  }//for
                                                          }
                                    
                                                          if(usage != null){
                                                            Kogger.debug("############################################### SelectProgram List Type 3");
                                                            Kogger.debug("########### usage :" + usage);
                                                            Kogger.debug("########### pOid :" + pOid);
                                    
                                                            if(optOid != null && optOid[0] != null && (optOid[0].length() == 0
                                                                    || ( optOid[0].length() > 0 && "nodata".equals(optOid[0]) ) ) ) {
                                    
                                                                    if( ( pOid != null && pOid[0] != null && pOid[0].length() == 0 )
                                                                        || pOid == null ) {
                                                                        Kogger.debug("########### List Type 3 start ");
                                    
                                                        %>
                                                        <tr>
                                                            <td width="120" class="tdwhiteR" colspan=2>
                                                                <input type="hidden" name="itemtableOid">
                                                                <input type="hidden" name="count" value="<%=count%>">-</td>
                                                            <td width="90" class="tdwhiteR">
                                                                <input name="carName" type="text" class="txt_field" style="width: 85" value="<%=carName[1]%>">&nbsp;</td>
                                                            <td width="50" class="tdwhiteR">&nbsp;<input type="text" class="txt_fieldR"
                                                                name="y1" style="width: 45" value="<%=y1V%>"></td>
                                                            <td width="50" class="tdwhiteR">&nbsp;<input type="text" class="txt_fieldR"
                                                                name="y2" style="width: 45" value="<%=y2V%>"></td>
                                                            <td width="50" class="tdwhiteR">&nbsp;<input type="text" class="txt_fieldR"
                                                                name="y3" style="width: 45" value="<%=y3V%>"></td>
                                                            <td width="50" class="tdwhiteR">&nbsp;<input type="text" class="txt_fieldR"
                                                                name="y4" style="width: 45" value="<%=y4V%>"></td>
                                                            <td width="50" class="tdwhiteR">&nbsp;<input type="text" class="txt_fieldR"
                                                                name="y5" style="width: 45" value="<%=y5V%>"></td>
                                                            <td width="50" class="tdwhiteR">&nbsp;<input type="text" class="txt_fieldR"
                                                                name="y6" style="width: 45" value="<%=y6V%>"></td>
                                                            <td width="50" class="tdgrayR"><span id="total2"><%=sumV%></span></td>
                                                            <td width="50" class="tdwhiteR"><input type="text" class="txt_fieldR"
                                                                name="usage" value="<%=usage[0]%>" style="width: 45"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }"></td>
                                                            <td width="70" class="tdwhiteR0"><input type="text" class="txt_fieldR"
                                                                name="optionRate" value="<%=optionRate[0]%>" style="width: 35"
                                                                onKeyUp="javascript:if(!checkNumber(this.value)) { alert('숫자를 입력하세요.'); this.value = ''; this.focus(); }">%</td>
                                                        </tr>
                                                        <%
                                }
                        }
                      }
                      %>
                                                    </TBODY>

                                                    <tr>
                                                        <td width="120" class="tdgrayM" colspan="2">-</td>
                                                        <td width="90" class="tdgrayL"><span id="totalcarName">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="totaly1">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="totaly2">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="totaly3">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="totaly4">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="totaly5">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="totaly6">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="total">&nbsp;</span></td>
                                                        <td width="50" class="tdgrayR"><span id="totalusage">&nbsp;</span></td>
                                                        <td width="70" class="tdgrayM0">-</td>
                                                    </tr>

                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>
