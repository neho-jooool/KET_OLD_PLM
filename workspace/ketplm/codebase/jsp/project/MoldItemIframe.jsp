<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*,
                wt.fc.*,
                wt.org.*,
                wt.project.Role,
                wt.query.*,
                wt.session.*,
                wt.team.*,
                e3ps.common.code.*,
                e3ps.common.jdf.config.Config,
                e3ps.common.jdf.config.ConfigImpl,
                e3ps.common.query.*,
                e3ps.common.util.*,
                e3ps.common.web.*,
                e3ps.ews.dao.PartnerDao,
                e3ps.groupware.company.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
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
</style>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<%
try{
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

    Vector tMap = null;
%>
<script type="text/javascript">
<!--
    var idNum = 0;

//==  [Start] 부품검색 팝업(PartNo)  == //
// mode = m / s
// pType=A(전체)/P(제품)/D(다이)/M(금형)
function selectPartNumber(targetId, arrIndex, pType) {
	_callBackFuncTargetId = targetId;
    _callBackFuncArrIndex = arrIndex;
    showProcessing();
    SearchUtil.selectOnePart("callBackFuncPartPopup", "pType="+pType);
}

var _callBackFuncTargetId;
var _callBackFuncArrIndex;
function callBackFuncPartPopup(selectedPartAry){
    if ( typeof selectedPartAry != "undefined" && selectedPartAry != null) {
    	acceptPartNo(_callBackFuncTargetId, _callBackFuncArrIndex, selectedPartAry);
    }
}

function acceptPartNo(targetId, arrIndex, parts) {
    if ( parts != undefined && parts.length > 0 ) {
        var partNo = "";
        var partName = "";
        for (var i = 0; i < parts.length; ++i) {
            partNo = parts[i][1];
            partName = parts[i][2];
        }
        document.getElementById(targetId + arrIndex).value = partNo;

        if ( targetId == "moldPartNo" ) {
            document.getElementById("partName" + arrIndex).value = partName;
        }
    }
}

//==  [Start] 부품 검색(PartNo)  == //

//원재료 검색 팝업 호출
function selMaterial(rname, arrIndex, itemTypeCheck) {
    var param = "";

    var displayName = document.getElementById("temp" + rname + arrIndex);
    var keyName = document.getElementById(rname + arrIndex);
    var poidvalueName = document.getElementById("poidvalue" + arrIndex);
    var heightName = document.getElementById("height" + arrIndex);
    var wideName = document.getElementById("wide" + arrIndex);
    var itemTypeName = document.getElementById("itemType" + arrIndex);
    var etcName = document.getElementById("etc" + arrIndex);

    if(keyName.value != ""){
        param = "materialOid=" + keyName.value + "&pOid=" + poidvalueName.value + "&height=" + heightName.value + "&wide=" +wideName.value + "&moldType=" + itemTypeName.value + "&etc=" +etcName.value;
    }else{
        if(itemTypeName != null) param = "moldType=" + itemTypeName.value;
    }
    param = param + "&itemTypeCheck=" + itemTypeCheck;

    var url = "/plm/jsp/project/material/SelectMaterialPopup.jsp?" + param;

    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=450px; dialogHeight:250px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }
    setMaterial(rname, arrIndex, attache);
}

function setMaterial(rname, arrIndex, objArr) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];

    var displayName = document.getElementById("temp" + rname + arrIndex);
    var keyName = document.getElementById(rname + arrIndex);
    var poidvalueName = document.getElementById("poidvalue" + arrIndex);
    var heightName = document.getElementById("height" + arrIndex);
    var wideName = document.getElementById("wide" + arrIndex);
    var etcName = document.getElementById("etc" + arrIndex);

    keyName.value = trArr[0];
    displayName.value = trArr[1];
    poidvalueName.value = trArr[2];
    heightName.value = trArr[3];
    wideName.value = trArr[4];
    etcName.value = trArr[5];

}
//검색 호출 끝

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//원재료 가져오기 시작 (협력업체)
function onMaterials(rname, arrIndex, codetype) {
    var mode = "single";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode+"&viewType=noTree";
    attache = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }

    addMaterials(rname, arrIndex, attache);

}

function addMaterials(rname, arrIndex, arr)
{
    if(arr.length == 0) {
        return;
    }

    var displayName = document.getElementById("temp" + rname + arrIndex);
    var keyName = document.getElementById(rname + arrIndex);

    for(var i = 0; i < arr.length; i++) {
        infoArr = arr[i];
        displayName.value = infoArr[2];
        keyName.value = infoArr[0];
    }
}
//원재료 가져오기 끝
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

 //부서 가져오기 시작 ........................................................................................
//............................................................................................................
function addDepartment(rname, arrIndex) {
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

    var param = "command=deptInfo&deptOid=" + attacheDept[0][0] + "&idName=" + rname + arrIndex;
    var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    callServer(url, acceptDept);
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
    var idName = showElements[0].getElementsByTagName("l_idname");

    var displayName = document.getElementById("temp" + idName[0].text);
    var keyName = document.getElementById(idName[0].text);

    displayName.value = l_name[0].text;
    keyName.value = l_oid[0].text;
}
//부서 가져오기 끝 ........................................................................................
//............................................................................................................

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//item 가져오기 시작

function chedkPartNo(partNoValue, partNoObj) {
    index = partNoObj.parentElement.parentElement.rowIndex - 2;
    if(parent.document.forms[0].partNoCost) {
        if(parent.document.forms[0].partNoCost.length) {
            parent.document.forms[0].partNoCost[index].value = partNoValue;
        }else {
            parent.document.forms[0].partNoCost.value = partNoValue;
        }
    }
}

function chedkPartName(partNameValue, partNameObj) {
    index = partNameObj.parentElement.parentElement.rowIndex - 2;
    if(parent.document.forms[0].costName) {
        if(parent.document.forms[0].costName.length) {
            parent.document.forms[0].costName[index].value = partNameValue;
        }else {
            parent.document.forms[0].costName.value = partNameValue;
        }
    }
}

function chedkDieNo(dieNoValue, dieNoObj) {
    index = dieNoObj.parentElement.parentElement.rowIndex - 2;
    if(parent.document.forms[0].dieNoCost) {
        if(parent.document.forms[0].dieNoCost.length) {
            parent.document.forms[0].dieNoCost[index].value = dieNoValue;
        }else {
            parent.document.forms[0].dieNoCost.value = dieNoValue;
        }
    }
}

function chedkMoldType(moldTypeValue, moldTypeObj) {
    index = moldTypeObj.parentElement.parentElement.rowIndex - 2;
    if(parent.document.forms[0].moldTypeCost) {
        if(parent.document.forms[0].moldTypeCost.length) {
            parent.document.forms[0].moldTypeCost[index].value = moldTypeValue;
        }else {
            parent.document.forms[0].moldTypeCost.value = moldTypeValue;
        }
    }
}

var partnerNameValue = null;
var partnerNoValue = null;

//협력사검색 팝업 Open
function selectPartner(rname, arrIndex){
    partnerNameValue = document.getElementById("temp" + rname + arrIndex);
    partnerNoValue = document.getElementById(rname + arrIndex);

    var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
    openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
  }

//협력사 검색결과 셋팅
function linkPartner(arr){
  if(arr.length == 0) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
        return;
    }

    partnerNoValue.value = arr[0][0];
    partnerNameValue.value = arr[0][1];

    partnerNameValue = null;
    partnerNoValue = null;

}

function getProductionPlace(arrIndex) {
    var productionPlaceId = document.getElementById("productionPlace" + arrIndex);
    var productionPlaceTdName = document.getElementById("productionPlaceTd" + arrIndex);
    productionPlaceTdName.innerHTML = "<input type=hidden name=\"productionPlaceTwo\" id=\"productionPlaceTwo" + arrIndex + "\">";
    productionPlaceTdName.innerHTML += "<input type=text name=\"tempproductionPlaceTwo\" id=\"tempproductionPlaceTwo" + arrIndex + "\" class=\"txt_field\" style=\"width:65px\" readonly>&nbsp;";
    if(document.forms[0].productionPlace.value == undefined || document.forms[0].productionPlace.value == "") {
        if(productionPlaceId.value == "사내") {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:onMaterials('productionPlaceTwo', " + arrIndex + ", 'PRODUCTIONDEPT');\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }else if(productionPlaceId.value == "외주") {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:selectPartner('productionPlaceTwo', " + arrIndex + ");\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }
    }else {
    	if(document.forms[0].productionPlace[0].selected){
    		productionPlaceTdName.innerHTML += "";
        }else if(document.forms[0].productionPlace[1].selected) {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:onMaterials('productionPlaceTwo', " + arrIndex + ", 'PRODUCTIONDEPT');\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }else if(document.forms[0].productionPlace[2].selected) {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:selectPartner('productionPlaceTwo', " + arrIndex + ");\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }
    }
}

function getMakerPlace(arrIndex) {
    var productionPlaceTdName = document.getElementById("makingPlaceTd" + arrIndex);
	var productionPlaceId = document.getElementById("making" + arrIndex);
    productionPlaceTdName.innerHTML = "<input type=hidden name=\"makingPlaceTwo\" id=\"makingPlaceTwo" + arrIndex + "\">";
    productionPlaceTdName.innerHTML += "<input type=text name=\"tempmakingPlaceTwo\" id=\"tempmakingPlaceTwo" + arrIndex + "\" class=\"txt_field\" style=\"width:65px\" readonly>&nbsp;";
    if(document.forms[0].making.value == undefined || document.forms[0].making.value == "") {
        if(productionPlaceId.value == "사내") {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:onMaterials('makingPlaceTwo', " + arrIndex + ", 'PRODUCTIONDEPT');\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }else if(productionPlaceId.value == "외주") {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:selectPartner('makingPlaceTwo', " + arrIndex + ");\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }
    }else {
    	if(document.forms[0].making[0].selected){
            productionPlaceTdName.innerHTML += "";
    	}else if(document.forms[0].making[1].selected) {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:onMaterials('makingPlaceTwo', " + arrIndex + ", 'PRODUCTIONDEPT');\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }else if(document.forms[0].making[2].selected) {
            productionPlaceTdName.innerHTML += "<a href=\"javascript:selectPartner('makingPlaceTwo', " + arrIndex + ");\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }
    }
}

//Number Code Ajax
function numCodeAjax(codeType, code, targetId) {
    $.ajax( {
        url : "/plm/servlet/e3ps/NumberCodeAjax",
        type : "POST",
        data : {codeType:codeType, name:code},
        dataType : 'json',
        async : false,
        success: function(data) {
            $.each(data.numCode, function() {
                $("#"+targetId).append("<option value='"+this.oid+"' >"+ this.value +"</option>");
            });
        }
    });
}

function selectSearch(svalue, sId) {

    $("#" + sId).empty().data('options');
    $("#" + sId).append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");
    numCodeAjax("MOLDPRODUCTSTYPE", svalue, sId);
}

function onSelectOption(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;

    var selectId = showElements[0].getElementsByTagName("l_selectId")[0].text;

    showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");

    var arr = new Array();
    for(var i = 0; i < l_oid.length; i++) {
        subArr = new Array();
        subArr[0] = decodeURIComponent(l_oid[i].text);
        subArr[1] = decodeURIComponent(l_name[i].text);
        arr[i] = subArr;
    }

    addSelectNode(arr, selectId);
}

function addSelectNode(vArr, selectId) {
    var fTD = document.all.item(selectId);
    var re = document.getElementById(selectId);
    var len = re.length;

    for(var j = len ; j > 0 ; j--){
        re.remove(j);
    }

    for(var i = 0; i < vArr.length; i++) {
    var newSpan = document.createElement("option");

    newSpan.innerHTML = vArr[i][1];
    newSpan.value= vArr[i][0];
    fTD.appendChild(newSpan);
    }
}

function hasDuplicatedPart(partNo){
	var hasDuplicatedPart = false;
    //중복체크
    $('[name=moldPartNo]').each(function(){
        if(partNo == $(this).val()){
            alert('['+partNo+'] 중복된 부품이 있습니다.');
            hasDuplicatedPart = true;
            return;
        }
    });
    return hasDuplicatedPart;
}

function addItem(attacheArray){
    
	
	for(var i=0; i<attacheArray.length;i++){
		/* if(hasDuplicatedPart(attacheArray[i][2])){
    		continue;
    	} */
        var targetTable = document.getElementById("itemtable");
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);

        //삭제 버튼
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href='#' onclick='$(this).parent().parent().remove();'><img src='/plm/portal/images/b-minus.png' border='0'></a><input name=\"moldItemOid\" type=\"hidden\"/ value=\"\">";

        //Part No
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input type=hidden name=\"moldPartNo\" id=\"moldPartNo"+idNum+"\" value=\""+attacheArray[i][2]+"\"><a href=javascript:openViewPart('"+ attacheArray[i][2] +"')>"+attacheArray[i][2]+"</a>";

        //Part Name
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM text-left";
        newTd.title = attacheArray[i][3];
        newTd.innerHTML = "<input type=hidden name=\"partName\" id=\"partName"+idNum+"\" value=\""+attacheArray[i][3]+"\"><div style='text-align:left;width: 95%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;'><nobr>"+attacheArray[i][3]+"</nobr></div>";

        //부품분류(분류체계)
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input name=\"moldProductType\" type=\"hidden\" id=\"moldProductType"+idNum+"\" value=\"" +attacheArray[i][7] +"\">" + attacheArray[i][1];
        
        //분류
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input id=\"itemType"+idNum+"\" type=hidden name=itemType>"; //금형분류(Mold/Press/구매품) --> Text로 들어감            
        newTd.innerHTML += "<div id=\"itemTypeDiv"+idNum+"\"></div>";
        
        //구분(신규/공용)
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<select name=\"newType\" style=\"width:99%\" onchange=\"setTypeAttr(this)\"><option value=\"신규\"><%=messageService.getString("e3ps.message.ket_message", "02022") %><%--신규--%></option><option value=\"신규(공용)\">신규(공용)</option><option value=\"양산품\">양산품</option></select>";
        //newTd.innerHTML += "<div id=\"newTypeDiv"+idNum+"\">"+(attacheArray[i][6] == 'Y'?'신규':'공용');
        
        //Die No
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<div id=\"dieNoDiv"+idNum+"\"></div>";

        //구분(codeType : MOLDTYPE) --> Code값이 들어감
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input id=\"moldType" + idNum + "\" type=hidden name=\"moldType\">";
        newTd.innerHTML += "<div id=\"moldTypeDiv"+idNum+"\"></div>";
        
        //C/V/Pitch
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input id=\"cVPitch" + idNum + "\" type=hidden name=\"cVPitch\" class=\"txt_field\" style=\"width:70px\">";
        newTd.innerHTML += "<div id=\"cVPitchDiv"+idNum+"\"></div>";
        
        //C/T/SPM
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input id=\"cTSPM" + idNum + "\" type=hidden name=\"cTSPM\" class=\"txt_field\" style=\"width:70px\">";
        newTd.innerHTML += "<div id=\"cTSPMDiv"+idNum+"\"></div>";
        
        //제작처(사내/외주)
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML =  "<input id=\"making" + idNum + "\" type=hidden name=\"making\">";
        newTd.innerHTML += "<input id=\"makingPlaceTwo" + idNum + "\" type=hidden name=\"makingPlaceTwo\">";
        newTd.innerHTML += "<div id=\"makingDiv"+idNum+"\"></div>";
        
        //생산처(구매처)
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<input id=\"productionPlace" + idNum + "\" type=hidden name=\"productionPlace\">";
        newTd.innerHTML += "<input id=\"productionPlaceTwo" + idNum + "\" type=hidden name=\"productionPlaceTwo\">";
        newTd.innerHTML += "<div id=\"productionPlaceDiv"+idNum+"\"></div>";

        //원재료
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM0";
        newTd.innerHTML =  "<input type=hidden name=\"materials\" id=\"materials"+idNum+"\">";
        newTd.innerHTML += "<div id=\"tempmaterialsDiv"+idNum+"\">";
        newTd.innerHTML += "<input type=hidden name=\"poidvalue\" id=\"poidvalue"+idNum+"\">";
        newTd.innerHTML += "<input type=hidden name=\"height\" id=\"height"+idNum+"\">";
        newTd.innerHTML += "<input type=hidden name=\"wide\" id=\"wide"+idNum+"\">";
        newTd.innerHTML += "<input type=hidden name=\"etc\" id=\"etc"+idNum+"\">";
        newTd.innerHTML += "<input type=hidden name=\"tempmaterials\" id=\"tempmaterials"+idNum+"\" class=\"txt_field\" style=\"width:65px\" readonly>";
        //newTd.innerHTML += "<a href=\"javascript:selMaterial('materials', " + idNum + ", 'true');\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        
        createDieNoCombo(idNum, attacheArray[i][2]);
        idNum++;
	}
}

//dieNo combo 생성
function createDieNoCombo(idNum, partNo){
	var index = idNum;
	//var index = $.inArray($('#dieNo'+idNum), $('[name=dieNo]'));
	var inputObj = '';	
	//구매품이면 dieNo를 직접입력 하도록 수정
	
	if($('#itemType'+idNum).val() == '구매품'){
		//Die No
		
       /*  inputObj = "<input id=\"dieNo"+idNum+"\" name=\"dieNo\" style=\"width:90%\" value=\"미입력\">";

        inputObj += "<input type=hidden name=\"costOid\">";
        $('#dieNoDiv'+idNum).html(inputObj); */
	}else{
		
    	$.ajax({
            type: 'post',
            url: '/plm/ext/project/product/getDieNoByPartNo.do',
            //async : false,
            data: {
                partNo : partNo
            },
            success: function (data) {
                //Die No
                if(data && data.length > 0){
                    inputObj = "<select id=\"dieNo"+idNum+"\" name=\"dieNo\" onchange=\"setPartAttr(this)\" style=\"width:90%\">";                    
                    inputObj += "<option value='''>선택</option>";
                    for(var d=0;d<data.length;d++){
                    	if($('[name=dieNo]').eq(index).val() == data[d]){
                    		inputObj += '<option value=\"'+data[d]+'\" selected>'+data[d]+'</option>';                    		
                    	}else{
                    		inputObj += '<option value=\"'+data[d]+'\">'+data[d]+'</option>';                    		
                    	}
                    	/* if($('#moldProductType'+idNum).val() == 'R'){
                    		inputObj += '<option value=\"'+partNo+'\">'+partNo+'</option>'; 
                    	} */
                    }
                    inputObj += "</select>";
                    inputObj += "<input type=hidden name=\"costOid\">";
                    //구매품
                    var itemType = $('[name=itemType]').eq(index).val()?$('[name=itemType]').eq(index).val():'';
                    $('#itemType'+idNum).parent().html('<input id=\"itemType'+idNum+'\" type=hidden name=itemType value=\"'+itemType+'\"><div id=\"itemTypeDiv'+idNum+'\">'+itemType+'</div>');
                    
                    if($('#moldProductType'+idNum).val() == 'R'){
                    	$('#itemType'+idNum).parent().html('<select id=\"itemType'+idNum+'\" name=\"itemType\" style=\"width:95%\" onchange=\"setPartAttr2(this)\"><option value=\"\">선택</option><option value=\"구매품\">구매품</option></select>');
                    	
                    }
                    
                }else{
                	//없으면 구매품 dieNo를 선택할 수 있도록 한다.
                	//Die No
               	    inputObj = "<input type=text id=\"dieNo"+idNum+"\" name=\"dieNo\" class=\"txt_field\" style=\"width:90%\" value=\"\" disabled>";
                    inputObj += "<input type=hidden name=\"costOid\">";
                    
                    //구매품
                    $('#itemType'+idNum).parent().html('<select id=\"itemType'+idNum+'\" name=\"itemType\" style=\"width:95%\" onchange=\"setPartAttr2(this)\"><option value=\"\">선택</option><option value=\"구매품\">구매품</option></select>');
                }
                $('#dieNoDiv'+idNum).parent().html(inputObj);
                //생선처,원재로 정보를 설정
                if(data && data.length == 0 && data != ''){
                	setPartAttr($('#dieNo'+idNum));
                }
            },
            error : function(){}
        });    		
	}
}



function setTypeAttr(obj){
	var dieNo = $(obj).parent().next().children().val();
	var flag = 0;
    var flag1 = 0;
    $('[name=dieNo]').each(function(index){
    	
        if(dieNo!= null && dieNo!= "" && dieNo!= "미입력" && dieNo == $(this).val()){
            flag++;
            var newTypeFlag = $('[name=newType]').eq(index).val();
            if("신규" == newTypeFlag){
                flag1++;
            }
        } 
    });
    if(flag > 1 && flag1 > 1){
        alert('중복된 DieNo가 있습니다.');
        //parent.saveCheck(false);
    }else{
        //parent.saveCheck(true);
    }
}

 function dupDieNoCheck(){
	 var flag = false;
/* 	 $('[name=dieNo]').each(function(idx1){
		 
		 var dieNo1 = $(this).val();
		 var newTypeFlag1 = $('[name=newType]').eq(idx1).val();
		 
		 if(dieNo1!= null && dieNo1!= "" && dieNo1!= "미입력" && newTypeFlag1 == '신규'){
			 $('[name=dieNo]').each(function(idx2){
				 
				 var dieNo2 = $(this).val();
				 var newTypeFlag2 = $('[name=newType]').eq(idx2).val();
				 if(idx1 != idx2 && newTypeFlag2 == '신규' && dieNo1 == dieNo2){
					 flag = true;
				 }
				 
			 });
		 }
	 }); */
	 
	 $('[name=dieNo]').each(function(idx1){
         
         //var flag1 = $(this).val()+$('[name=newType]').eq(idx1).val()+$('[name=moldPartNo]').eq(idx1).val();//partNo + 구분 + Die No
         var flag1 = $(this).val()+$('[name=moldPartNo]').eq(idx1).val();//partNo + 구분 + Die No
         $('[name=dieNo]').each(function(idx2){
             
             //var flag2 = $(this).val()+$('[name=newType]').eq(idx2).val()+$('[name=moldPartNo]').eq(idx2).val();
             var flag2 = $(this).val()+$('[name=moldPartNo]').eq(idx2).val();             
             if(idx1 != idx2 && flag1 == flag2){
                 flag = true;
             }
             
         });
     });
	 return flag;
 }
 
 
//부품속성 정보 설정
 function setPartAttr2(obj){
     
     var flag = 0;
     var flag1 = 0;
     $('[name=dieNo]').each(function(index){
         if($(this).val() != "" && obj.value == $(this).val()){
             
             flag++;
             var newTypeFlag = $('[name=newType]').eq(index).val();
             if("신규" == newTypeFlag){
                 flag1++;
             }
         } 
     });
     
     if(flag > 1 && flag1 > 1){
         alert('중복된 DieNo가 있습니다.');
         //parent.saveCheck(false);
     }else{
         //parent.saveCheck(true);
     }
     var index = $(obj).parent().parent().index();
     $.ajax({
         type: 'post',
         url: '/plm/ext/project/product/getPartAttributeByDieNo.do',
         async : false,
         data: {
             partNo : $('[name=moldPartNo]').eq(index).val(),
             dieNo : $('[name=moldPartNo]').eq(index).val()
         },
         success: function (data) {
             //$('[name=itemType]').eq(index).val((data.itemType?data.itemType:''));//부품분류(Mold/Press/구매품)
             //$('[name=itemType]').eq(index).next().html((data.itemType?data.itemType:''));//부품분류(Mold/Press/구매품)
             //$('[name=moldType]').eq(index).val(data.moldType);//금형구분(시작Fa/)
             //$('[name=moldType]').eq(index).next().html(data.moldTypeName);
             //$('[name=cVPitch]').eq(index).val(data.cavity);
             //$('[name=cVPitch]').eq(index).next().html(data.cavity);
             //$('[name=cTSPM]').eq(index).val(data.cycleTime);
             //$('[name=cTSPM]').eq(index).next().html(data.cycleTime);
             
             $('[name=making]').eq(index).val(data.making);
             $('[name=making]').eq(index).next().html(data.making);
             $('[name=makingPlaceTwo]').eq(index).val(data.makingPlace);
             $('[name=makingPlaceTwo]').eq(index).next().html(data.makingPlaceName);
             $('[name=productionPlace]').eq(index).val(data.productionPlace);
             //$('[name=productionPlace]').eq(index).val(data.productPlaceName);
             $('[name=productionPlaceTwo]').eq(index).val(data.purchasePlace);
             $('[name=productionPlaceTwo]').eq(index).next().html(data.purchasePlaceName);
             $('[name=materials]').eq(index).val(data.materials);
             $('[name=materials]').eq(index).next().html(data.tempmaterials);
             $('[name=poidvalue]').eq(index).val(data.poidvalue);
         },
         error : function(){}
     });     
         
          
 
 }
 
 

//부품속성 정보 설정
function setPartAttr(obj){
	
	var flag = 0;
	var flag1 = 0;
	$('[name=dieNo]').each(function(index){
		if($(this).val() != "" && obj.value == $(this).val()){
			
			flag++;
			var newTypeFlag = $('[name=newType]').eq(index).val();
			if("신규" == newTypeFlag){
				flag1++;
			}
        } 
    });
	
	if(flag > 1 && flag1 > 1){
        alert('중복된 DieNo가 있습니다.');
        //parent.saveCheck(false);
    }else{
    	//parent.saveCheck(true);
    }
	var index = $(obj).parent().parent().index();
	
	
	$('[name=itemType]').eq(index).next().html('');//부품분류(Mold/Press/구매품)	
    $('[name=moldType]').eq(index).next().html('');
    $('[name=cVPitch]').eq(index).next().html('');
    $('[name=cTSPM]').eq(index).next().html('');                
    $('[name=making]').eq(index).next().html('');
    $('[name=makingPlaceTwo]').eq(index).next().html('');
    $('[name=productionPlace]').eq(index).next().html('');
    $('[name=productionPlaceTwo]').eq(index).next().html('');
    $('[name=materials]').eq(index).next().html('');
    if($(obj).val() == ''){
    	//구매품
        //$('#itemType'+idNum).parent().html('<select id=\"itemType'+idNum+'\" name=\"itemType\" style=\"width:95%\ onchange=\"setProductPlaceField(this)\"><option name=\"\">선택</option><option name=\"구매품\">구매품</select>');
    	$('[name=itemType]').eq(index).parent().html('<select id=\"itemType'+idNum+'\" name=\"itemType\" style=\"width:95%\ onchange=\"setProductPlaceField(this)\"><option name=\"\">선택</option><option name=\"구매품\">구매품</select>');
    }else{
    	//구매품
    	
    	$('[name=itemType]').eq(index).parent().html('<input id=\"itemType'+idNum+'\" type=hidden name=itemType><div id=\"itemTypeDiv'+idNum+'\">');	
    	
        
    	$.ajax({
            type: 'post',
            url: '/plm/ext/project/product/getPartAttributeByDieNo.do',
            async : false,
            data: {
                partNo : $('[name=moldPartNo]').eq(index).val(),
                dieNo : $('[name=dieNo]').eq(index).val()
            },
            success: function (data) {
                $('[name=itemType]').eq(index).val((data.itemType?data.itemType:''));//부품분류(Mold/Press/구매품)
                $('[name=itemType]').eq(index).next().html((data.itemType?data.itemType:''));//부품분류(Mold/Press/구매품)
                $('[name=moldType]').eq(index).val(data.moldType);//금형구분(시작Fa/)
                $('[name=moldType]').eq(index).next().html(data.moldTypeName);
                $('[name=cVPitch]').eq(index).val(data.cavity);
                $('[name=cVPitch]').eq(index).next().html(data.cavity);
                $('[name=cTSPM]').eq(index).val(data.cycleTime);
                $('[name=cTSPM]').eq(index).next().html(data.cycleTime);                
                $('[name=making]').eq(index).val(data.making);
                $('[name=making]').eq(index).next().html(data.making);
                $('[name=makingPlaceTwo]').eq(index).val(data.makingPlace);
                $('[name=makingPlaceTwo]').eq(index).next().html(data.makingPlaceName);
                $('[name=productionPlace]').eq(index).val(data.productionPlace);
                //$('[name=productionPlace]').eq(index).val(data.productPlaceName);
                $('[name=productionPlaceTwo]').eq(index).val(data.purchasePlace);
                $('[name=productionPlaceTwo]').eq(index).next().html(data.purchasePlaceName);
                $('[name=materials]').eq(index).val(data.materials);
                $('[name=materials]').eq(index).next().html(data.tempmaterials);
                $('[name=poidvalue]').eq(index).val(data.poidvalue);
            },
            error : function(){}
        });    	
    }    
}


function getProductPlaceHtml(obj, rowId, placeType, placeTwo){
	var selectStr = '';
	if($(obj).val() == '구매품'){
        selectStr = "<select id=\""+ placeType + rowId + "\" name=\""+placeType +"\" style=\"width:50px\" onchange=\"$(this).next().val('');$(this).next().next().val('')\">";
        selectStr += "<option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>";
        selectStr += "<option value=\"사내\"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>"; 
        selectStr += "<option value=\"외주\"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option></select>";
        selectStr += "<input id=\""+placeTwo +rowId + "Name\" type=text name=\""+placeTwo +"Name\" style=\"width:70px\" class=\"txt_fieldRO\" readonly>";
        selectStr += "<input id=\""+placeTwo +rowId + "\" type=hidden name=\""+placeTwo+"\">";
        selectStr += "<a href='#' onclick='selectAssemplyPlace(this);'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
	}else{
		selectStr =  "<input id=\""+ placeType + rowId + "\" type=hidden name=\""+placeType +"\">";
		selectStr += "<input id=\""+placeTwo +rowId + "\" type=hidden name=\""+placeTwo +rowId + "\">";
		selectStr += "<div id=\""+placeTwo + "Div" + rowId + "\">";
	}
    return selectStr;
}

function setProductPlaceField(obj){
	
	var index = $.inArray(obj, $('[name=itemType]'));
	
	$('[name=making]').eq(index).parent().html(getProductPlaceHtml(obj,index,'making','makingPlaceTwo'));
	$('[name=productionPlace]').eq(index).parent().html(getProductPlaceHtml(obj,index,'productionPlace','productionPlaceTwo'));
	//$("#dieNo"+index).val("미입력");
	
	/* if(obj.value == '구매품'){
		$("#dieNo"+index).val($("#moldPartNo"+index).val());
        
	}else{
		$("#dieNo"+index).val("미입력");
	} */
}

var selectedObj;
function selectAssemplyPlace(obj) {
    if($(obj).prev().prev().prev().val() == '사내'){
        //
        var mode = "single";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode+"&viewType=noTree";
        var attache = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }
        $(obj).prev().val(attache[0][0]);
        $(obj).prev().prev().val(attache[0][2]);
    }else if($(obj).prev().prev().prev().val() == '외주'){
        //협력사검색 팝업 Open
        var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=setPartner&modal=N";        
        openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
        selectedObj = obj;
    }else{
    	$(obj).prev().val('');
        $(obj).prev().prev().val('');
    }
}

function setPartner(arr){       
    $(selectedObj).prev().prev().val(arr[0][1]);
    $(selectedObj).prev().val(arr[0][0]);
}
	
function deleteItem(oid) {
    var form = document.forms[0];

    $('#itemtable tr').each(function(i, row){
    	var row = $(row);
    	var deleteOid = $(row).find('[name=moldItemOid]');
    	if(oid == $(deleteOid).val()){
    		if($('[name=delItemOid]').val() == ''){
            	form.delItemOid.value = $(deleteOid).val();    			
    		}else{
            	form.delItemOid.value += ','+$(deleteOid).val();    			
    		}
    		row.remove();
    	}
    });
}

//item 가져오기 끝
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

function scrollAll() {
  document.all.topLine.scrollLeft = document.all.mainDisplay.scrollLeft;
}

function addPart(objArr){
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
}
//-->
</script>
</head>
<body style="width:100%;overflow-x:scroll;overflow-y:hidden;">
    <form method="post">
        <input type="hidden" name="delItemOid">
        <table id="topBLine" border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <TABLE cellSpacing=0 cellPadding=0 border=0>
            <TBODY>
                <TR>
                    <TD>
                        <div id=topLine style="OVERFLOW: hidden; WIDTH:100%;table-layout: fixed;">
                            <table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;min-width:1260px;width:100%;">
                                <COL width="30px">
                                <!-- 체크박스 -->
                                <COL width="80px">
                                <!-- Part No -->
                                <COL width="*">
                                <!-- Part Name -->
                                <COL width="60px">
                                <!-- 부품분류 -->
                                <COL width="70px">
                                <!-- 분류 -->
                                <COL width="60px">
                                <!--구분 -->                                
                                <COL width="100px">
                                <!-- Die  No -->
                                <COL width="80px">
                                <!-- 금형속성-구분 -->
                                <COL width="80px">
                                <!-- C/V -->
                                <COL width="80px">
                                <!-- C/T -->
                                <COL width="170px">
                                <!-- 제작 -->
                                <COL width="170px">
                                <!-- 생산처 -->
                                <COL width="100px">
                                <!-- 원재료 -->
                                <COL width="20px">
                                <!-- 원재료 -->
                                <tr>
                                    <td rowspan="2" class="tdblueM"><!-- <a href="javascript:parent.selectChildPart(addItem);"><img src="/plm/portal/images/b-plus.png"></a> --></td>
                                    <td rowspan="2" class="tdblueM">Part No</td>
                                    <td rowspan="2" class="tdblueM">Part Name</td>
                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "06112") %><%--부품분류--%></td>
                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01606")%><%--분류--%></td>
                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                    <td rowspan="2" class="tdblueM">Die No</td>
                                    <td colspan="4" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01090")%><%--금형속성--%></td>
                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01793", "<br>")%><%--생산처{0}(구매처)--%></td>
                                    <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02207")%><%--원재료--%></td>
                                    <td rowspan="2" class="tdblueM0">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                                    <td class="tdblueM">C/V</td>
                                    <td class="tdblueM">C/T</td>
                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
                                </tr>
                            </table>
                        </DIV>

                    </TD>
                </TR>
                <TR>
                    <TD>
                        <DIV id=mainDisplay style="overflow-y:scroll;overflow-x:hidden;width:100%;HEIGHT: 230px; table-layout: fixed;" onscroll=scrollAll()>
                            <table border="0" cellpadding="0" cellspacing="0" id="itemtable" style="table-layout: fixed;min-width:1250px;width:100%;">
                                <COL width="30px">
                                <COL width="80px">
                                <COL width="*">
                                <COL width="60px">
                                <COL width="70px">
                                <COL width="60px">
                                <COL width="100px">
                                <COL width="80px">
                                <COL width="80px">
                                <COL width="80px">
                                <COL width="170px">
                                <COL width="170px">
                                <COL width="100px">
                            </table>
                        </DIV>
                    </TD>
                </TR>
            </TBODY>
        </TABLE>
    </form>
    <script>
        $("#topBLine").width($("#mainDisplay").width());
        $(window).resize(function(){
            $("#topBLine").width($("#mainDisplay").width());
        });
    </script>
<%
    String oid = request.getParameter("oid");
    int rowCount = 0; 
    if(oid != null) {
        E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
        QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject)project);
        String beforeDieNo = "";
        rowCount = rt.size();
        while(rt.hasMoreElements()){
            Object[] obj = (Object[])rt.nextElement();
            MoldItemInfo miInfo = (MoldItemInfo)obj[0];
            String itemType = "";
            if(miInfo.getItemType() != null){
                itemType = miInfo.getItemType();
            }
            boolean isMoldProject = false;
            if(MoldProjectHelper.getMoldProject(miInfo) != null){
                isMoldProject = true;
            }

            QuerySpec spec2 = new QuerySpec();
            int idx_MoldProject = spec2.addClassList(MoldProject.class, true);
            SearchCondition sc2 = new SearchCondition(MoldProject.class, "moldInfoReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(miInfo) );
            spec2.appendWhere(sc2, new int[] { idx_MoldProject });
            QueryResult rt2 = PersistenceHelper.manager.find(spec2);

            String inSelected = "";
            String outSelected = "";
            String inoutNo = "";
            String inoutName = "";
            String makingPlaceNo = "";
            String makingPlaceName = "";
            String selected = "";
            
            PartnerDao partnerDao2 = new PartnerDao();
            if("사내".equals(miInfo.getProductionPlace())) {
                if(miInfo.getPurchasePlace() != null){
                inoutNo = CommonUtil.getOIDString(miInfo.getPurchasePlace());
                inoutName = StringUtil.checkNull(miInfo.getPurchasePlace().getName());
                }
            }else if(miInfo.getPartnerNo() != null && miInfo.getPartnerNo().length() > 0) {
                String partnerName = partnerDao2.ViewPartnerName(miInfo.getPartnerNo());
                if(partnerName == null || partnerName.length() == 0 ){
                    partnerName = "";
                }
                inoutNo = miInfo.getPartnerNo();
                outSelected = "selected";
                inoutName = partnerName;
            }
            if("사내".equals(miInfo.getMaking())) {
                if(miInfo.getMakingPlace() != null){                    
                    makingPlaceNo = CommonUtil.getOIDString(miInfo.getMakingPlace());
                    makingPlaceName = StringUtil.checkNull(miInfo.getMakingPlace().getName());
                }
            }else if(miInfo.getMakingPlacePartnerNo() != null && miInfo.getMakingPlacePartnerNo().length() > 0) {
                String partnerName = partnerDao2.ViewPartnerName(miInfo.getMakingPlacePartnerNo());
                if(partnerName == null || partnerName.length() == 0 ){
                    partnerName = "";
                }
                makingPlaceNo = miInfo.getMakingPlacePartnerNo();
                makingPlaceName = partnerName;
            }
%>
                <script>
                var targetTable = document.getElementById("itemtable");
                tableRows = targetTable.rows.length;
                newTr = targetTable.insertRow(tableRows);

                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input name=\"moldItemOid\" type=\"hidden\" value='<%=CommonUtil.getOIDString(miInfo)%>'/><%if(!isMoldProject){%><a href='javascript:deleteItem(\"<%=CommonUtil.getOIDString(miInfo)%>\")'><img src='/plm/portal/images/b-minus.png' border='0'></a><%}%>";

                //Part No
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type=hidden name=\"moldPartNo\" id=\"moldPartNo"+idNum+"\" value=\"<%=miInfo.getPartNo()!=null ? miInfo.getPartNo() : ""%>\"><%=miInfo.getPartNo()!=null ? "<a href=javascript:openViewPart('"+ miInfo.getPartNo() +"')>"+miInfo.getPartNo()+"</a>" : ""%>";

                //Part Name
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM text-left";
                newTd.title = '<%=miInfo.getPartName().replaceAll("'", "&#39;")%>';
                newTd.innerHTML = "<input type=hidden name=\"partName\" value=\"<%=miInfo.getPartName()!=null ? miInfo.getPartName() : ""%>\"><%=miInfo.getPartName()!=null ? "<div style='text-align:left;width: 95%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;'><nobr>"+miInfo.getPartName()+"</nobr></div>" : ""%>";

                //부품분류
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = '<input type=hidden name=\"moldProductType\" value=\"<%=miInfo.getProductType()==null?"":miInfo.getProductType().getCode() %>\"><%=miInfo.getProductType()==null?"":StringUtil.htmlCharEncode(miInfo.getProductType().getName())%>';
                
                //분류
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                if("구매품" == "<%=itemType%>") {
                	
                	<%if(isMoldProject){%>
                	newTd.innerHTML = "<input type=hidden name=\"itemType\" id=\"itemType"+idNum+"\" value=\"<%=itemType%>\">";
                    newTd.innerHTML += "<div id=\"itemTypeDiv"+idNum+"\"><%=itemType%></div>";
                	<%}else{%>
                	newTd.innerHTML ="<select id=\"itemType"+idNum+"\" name=\"itemType\" style=\"width:95%\" onchange=\"setPartAttr2(this)\"><option name=\"\">선택</option><option name=\"구매품\" selected>구매품</select>";
                	<%}%>
                }else{
                    newTd.innerHTML = "<input type=hidden name=\"itemType\" id=\"itemType"+idNum+"\" value=\"<%=itemType%>\">";
	                newTd.innerHTML += "<div id=\"itemTypeDiv"+idNum+"\"><%=itemType%></div>";
                }
                //구분
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                <%if(!isMoldProject){%>
                newTd.innerHTML = "<select name=\"newType\" style=\"width:99%\" onchange=\"setTypeAttr(this)\"><option value=\"신규\" <%="신규".equals(miInfo.getShrinkage())?"selected":""%>><%=messageService.getString("e3ps.message.ket_message", "02022") %><%--신규--%></option><option value=\"신규(공용)\"  <%="신규(공용)".equals(miInfo.getShrinkage())?"selected":""%>>신규(공용)</option> <option value=\"양산품\"  <%="양산품".equals(miInfo.getShrinkage())?"selected":""%>>양산품</option></select>";
				<%}else{%>
				newTd.innerHTML = "<input name=\"newType\" type=hidden value=\"<%=miInfo.getShrinkage()%>\"><%=miInfo.getShrinkage()%>";
				<%}%>
                //Die No
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                var dieDiv = '<div id=\"dieNoDiv'+idNum+'"\>';
                if("구매품" == "<%=itemType%>") {
                	<%if(!isMoldProject){%>
                	dieDiv += "<input type=text name=\"dieNo\" style=\"width:90px\"  id=\"dieNo"+idNum+"\" class=\"txt_field\" value=\"<%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : ""%>\" disabled>";
                    <%}else{%>
                    dieDiv += "<input type=hidden name=\"dieNo\" id=\"dieNo"+idNum+"\" value=\"<%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : ""%>\"><%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : ""%>";
                    <%}%>
                }else {
                	dieDiv += "<input type=hidden name=\"dieNo\" id=\"dieNo"+idNum+"\" value=\"<%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : ""%>\"><%=miInfo.getDieNo()!=null ? miInfo.getDieNo() : ""%>";
                }
                dieDiv += "<input type=hidden name=\"costOid\" value=\"<%=miInfo.getCostInfo()!=null ? CommonUtil.getOIDString(miInfo.getCostInfo()) : ""%>\">";
                dieDiv += '</div>';
                newTd.innerHTML = dieDiv;

                //구분
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type=hidden name=\"moldType\" value=\"<%=miInfo.getMoldType()==null?"":miInfo.getMoldType().getCode() %>\">";
                newTd.innerHTML += "<div id=\"moldTypeDiv"+idNum+"\"><%=miInfo.getMoldType()==null?"":miInfo.getMoldType().getName()%></div>";
                
                //C/V/Pitch
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type=hidden name=\"cVPitch\" class=\"txt_field\" value=\"<%=miInfo.getCVPitch()!=null ? miInfo.getCVPitch() : ""%>\">";
                newTd.innerHTML += "<div id=\"cVPitchDiv"+idNum+"\"><%=miInfo.getCVPitch()!=null ? miInfo.getCVPitch() : ""%></div>";
                
                //C/T/SPM
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input type=hidden name=\"cTSPM\" class=\"txt_field\" value=\"<%=miInfo.getCTSPM()!=null ? miInfo.getCTSPM() : ""%>\">";
                newTd.innerHTML += "<div id=\"cTSPMDiv"+idNum+"\"><%=miInfo.getCTSPM()!=null ? miInfo.getCTSPM() : ""%></div>";
                
                //제작처
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                var selectStr = "";
                <%-- if("구매품" == "<%=itemType%>") {
                	selectStr = "<select id=\"making" + idNum + "\" name=\"making\" style=\"width:50px\" onchange=\"$(this).next().val('');$(this).next().next().val('')\">";
                    selectStr += "<option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %>선택</option>";
                    selectStr += "<option value=\"사내\" <%="사내".equals(miInfo.getMaking())?" selected":""%>><%=messageService.getString("e3ps.message.ket_message", "01655") %>사내</option>"; 
                    selectStr += "<option value=\"외주\" <%="외주".equals(miInfo.getMaking())?" selected":""%>><%=messageService.getString("e3ps.message.ket_message", "02184") %>외주</option></select>";
                    selectStr += "<input id=\"makingPlaceTwo"+ idNum + "Name\" type=text name=\"makingPlaceTwoName\" style=\"width:70px\" class=\"txt_fieldRO\" readonly value=\"<%=makingPlaceName%>\">";
                    selectStr += "<input id=\"makingPlaceTwo" +idNum + "\" type=hidden name=\"makingPlaceTwo\" value=\"<%=makingPlaceNo%>\">";
                    selectStr += "<a href='#' onclick='selectAssemplyPlace(this);'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
                	newTd.innerHTML =  selectStr;
                }else{
                    newTd.innerHTML =  "<input type=hidden name=\"making\" value=\"<%=miInfo.getMaking()==null?"":miInfo.getMaking()%>\">";
                    newTd.innerHTML += "<input type=hidden name=\"makingPlaceTwo\" value=\"<%=makingPlaceNo%>\">";
                    newTd.innerHTML += "<div id=\"makingDiv"+idNum+"\"><%=miInfo.getMaking()==null?"":miInfo.getMaking()%>/<%=makingPlaceName%></div>";                	
                } --%>
                newTd.innerHTML =  "<input type=hidden name=\"making\" value=\"<%=miInfo.getMaking()==null?"":miInfo.getMaking()%>\">";
                newTd.innerHTML += "<input type=hidden name=\"makingPlaceTwo\" value=\"<%=makingPlaceNo%>\">";
                newTd.innerHTML += "<div id=\"makingDiv"+idNum+"\"><%=miInfo.getMaking()==null?"":miInfo.getMaking()%>/<%=makingPlaceName%></div>";
                
                //생산처(구매처)
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM";
                <%-- if("구매품" == "<%=itemType%>") {
                	selectStr = "<select id=\"productionPlace" + idNum + "\" name=\"productionPlace\" style=\"width:50px\" onchange=\"$(this).next().val('');$(this).next().next().val('')\">";
                    selectStr += "<option value=\"\"><%=messageService.getString("e3ps.message.ket_message", "01802") %>선택</option>";
                    selectStr += "<option value=\"사내\" <%="사내".equals(miInfo.getProductionPlace())?" selected":""%>><%=messageService.getString("e3ps.message.ket_message", "01655") %>사내</option>"; 
                    selectStr += "<option value=\"외주\" <%="외주".equals(miInfo.getProductionPlace())?" selected":""%>><%=messageService.getString("e3ps.message.ket_message", "02184") %>외주</option></select>";
                    selectStr += "<input id=\"productionPlaceTwo" +idNum + "Name\" type=text name=\"productionPlaceTwoName\" style=\"width:70px\" class=\"txt_fieldRO\" readonly value=\"<%=inoutName%>\">";
                    selectStr += "<input id=\"productionPlaceTwo" +idNum + "\" type=hidden name=\"productionPlaceTwo\" value=\"<%=inoutNo%>\">";
                    selectStr += "<a href='#' onclick='selectAssemplyPlace(this);'><img src='/plm/portal/images/icon_5.png' border='0'></a></div>";
                	newTd.innerHTML =  selectStr;
                }else{
                    newTd.innerHTML =  "<input type=hidden name=\"productionPlace\" value=\"<%=miInfo.getProductionPlace()!=null?miInfo.getProductionPlace():""%>\">";
                    newTd.innerHTML += "<input type=hidden name=\"productionPlaceTwo\" value=\"<%=inoutNo%>\">";
                    newTd.innerHTML += "<div id=\"productionPlaceDiv"+idNum+"\"><%=miInfo.getProductionPlace()!=null?miInfo.getProductionPlace():""%>/<%=inoutName%></div>";                	
                } --%>
                
                newTd.innerHTML =  "<input type=hidden name=\"productionPlace\" value=\"<%=miInfo.getProductionPlace()!=null?miInfo.getProductionPlace():""%>\">";
                newTd.innerHTML += "<input type=hidden name=\"productionPlaceTwo\" value=\"<%=inoutNo%>\">";
                newTd.innerHTML += "<div id=\"productionPlaceDiv"+idNum+"\"><%=miInfo.getProductionPlace()!=null?miInfo.getProductionPlace():""%>/<%=inoutName%></div>";        

                //원재료
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.className = "tdwhiteM0";
                newTd.innerHTML =  "<input type=hidden name=\"materials\" id=\"materials"+idNum+"\" value=\"<%=miInfo.getMaterial()!=null ? CommonUtil.getOIDString(miInfo.getMaterial()) : ""%>\">";
                newTd.innerHTML += "<div id=\"tempmaterialsDiv"+idNum+"\"><%=miInfo.getMaterial()!=null ? miInfo.getMaterial().getGrade() : ""%></div>";
                newTd.innerHTML += "<input type=hidden name=\"poidvalue\" id=\"poidvalue"+idNum+"\" value=\"<%=miInfo.getProperty()!=null ? CommonUtil.getOIDString(miInfo.getProperty()) : ""%>\">";
                newTd.innerHTML += "<input type=hidden name=\"height\" id=\"height"+idNum+"\" value=\"<%=miInfo.getThickness()!=null ? miInfo.getThickness() : ""%>\">";
                newTd.innerHTML += "<input type=hidden name=\"wide\" id=\"wide"+idNum+"\" value=\"<%=miInfo.getWidth()!=null ? miInfo.getWidth() : ""%>\">";
                newTd.innerHTML += "<input type=hidden name=\"etc\" id=\"etc"+idNum+"\" value=\"<%=miInfo.getEtc() != null ? miInfo.getEtc() : "" %>\">";
                //newTd.innerHTML += "<%=miInfo.getMaterial() !=null ? miInfo.getMaterial().getGrade() : ""%>";
                <%
                if(miInfo.getDieNo() != null && MoldProjectHelper.getMoldProject(miInfo) == null && !"구매품".equals(itemType)){
                %>
                createDieNoCombo(idNum, '<%=miInfo.getPartNo()!=null ? miInfo.getPartNo() : ""%>');
                <%
                }
                %>
                idNum++;
                </script>
<%

            beforeDieNo = (miInfo.getDieNo()==null)?"":miInfo.getDieNo();
        }
    }
%>
    <script type="text/javascript">
    var afterItemRowCount = <%=rowCount%>;
    </script>   
<%    

}catch(Exception e){
    Kogger.error(e);
}
%>
</body>
</html>