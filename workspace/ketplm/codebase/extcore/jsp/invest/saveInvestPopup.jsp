<%@page import="java.util.*"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.

Map <String, Object> parameter = new HashMap<String, Object>();
parameter.put("locale", messageService.getLocale());
parameter.put("codeType", "INVESTCOLLECT");
List<Map<String, Object>> numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

%>
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/invest/invest.js?ver=0.3"></script>
<script type = "text/javascript" src="/plm/portal/js/jsAjax.js"></script>
<script>
$(document).ready(function(){
    
    CommonUtil.singleSelect('rank',130);
    CalendarUtil.dateInputFormat('requestDate');
    
    SuggestUtil.bind('USER', 'managerName', 'managerOid');
    //SuggestUtil.bind('CUSTOMEREVENT', 'lastCustomerName', 'lastCustomer');
    CommonUtil.singleSelect('investTypeCode',100);
    
    if(!${isModify}){
        
    }else{
        setInvestTaskList();
    }
    
    $("input[name=reqNo]").data("message", "투자품의번호를 입력하세요.");
    $("input[name=reqName]").data("message", "투자품의명을 입력하세요.");
    $("select[name=investTypeCode]").data("message", "유형을 선택하세요.");
    //$("select[name=reqCode]").data("message", "요청구분을 선택하세요.");
    
    if(${isManager} && !${isAdmin}){
        $("input[name=requestDate]").data("message", "회수 예정일자를 입력하세요.");    
    }
    
    $('#investExpense_1').number( true );
    $('#investExpense_2').number( true );
    
    invest.customerSetting();
    
    var data = ${pjtMap};
    if(data.pjtNo != null){
    	var listInfo = new Array();
        var row = {};
        row.pjtNo = data.pjtNo;
        row.customer = data.customer;
        listInfo.push(row);
        addProject(listInfo);
    }
    
    
    $("#requestDate").change(function(){
    	var oldRequestDate = $('#oldRequestDate').val();
    	var newRequestDate = $(this).val();
    	
    	if(oldRequestDate != '' && oldRequestDate < newRequestDate) {
    		alert('회수예정일자가 늦어지는 경우 변경사유를 입력하셔야합니다.');
    		$("#changeHistoryTr").show();
    		$("#changeHistory").focus();
    	}else{
    		$("#changeHistoryTr").hide();
    	}

    });
    
    
  /*   $("#attachFileDiv1").load("/plm/ext/common/attachFilesForm?ver=5.1&oid=${imDTO.oid}&tid=1", function(){
    	$("#attachFileDiv2").load("/plm/ext/common/attachFilesForm?ver=6.1&oid=${imDTO.oid}&tid=2", function(){
    		$("#attachFileDiv3").load("/plm/ext/common/attachFilesForm?ver=7.1&oid=${imDTO.oid}&tid=3", function(){
            });	
    	});
    });   */
    
});

window.valueTarget_ = "";
window.nameTarget_ = "";
	
window.addMemberCallBack = function(attacheMembers){
	var values = "";
    var names = "";
    
    for(var i = 0; i < attacheMembers.length; i++) {
        
        if(i != 0){
            values += ",";
            names += ",";
        }
        
        values += attacheMembers[i][0];
        names += attacheMembers[i][4];
        
        //wtuser oid
        //people oid
        //dept oid
        //uid
        //name
        //dept name
        //duty
        //duty code
        //email
    }    
    
    $('.'+valueTarget_).val(values);
    $('.'+nameTarget_).val(names);
}

window.addMember = function(valueTarget, nameTarget) {
	valueTarget_ = valueTarget;
	nameTarget_ = nameTarget;
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=addMemberCallBack";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=800,height=600";
    window.open(url,valueTarget,opts);
    
}

window.typeCheck = function(obj){
	if ( typeof obj == "undefined" || obj == null ) {
		return '';
	}else{
		return obj;
	}
}

window.valueField_ = "";
window.displayField_ = "";

window.setUser = function(attacheMembers){
	var infoArr = attacheMembers[0];
    $('.'+valueField_).val(infoArr[0]);
    $('.'+displayField_).val(infoArr[4]);
}

window.searchUser = function(tmUserOid,tmUserName){
	window.valueField_ = tmUserOid;
	window.displayField_ = tmUserName;
	SearchUtil.selectOneUserByClass('','','setUser');
}

window.addInvestTaskList = function(data){
    
    var key = $("#investTaskList .investTaskRow:last").data("key");
    
    if(key == null) key = 0;
    else            key++;
    
    var tmUserName = "tmUserName" + key;
    var tmUserOid = "tmUserOid" + key;
    var refUserNames = "refUserNames" + key;
    var refUserOids = "refUserOids" + key;
    var reqDate = "reqDate" + key;
    
    var investTaskRow = "<tr class='investTaskRow'>";
    
    if((data != null && data.investState == "IST004") || (data != null && ${isRegMember})){
    	
    	if ( typeof arrObj == "undefined" || arrObj == null ) {
    		
    	}
        investTaskRow += "<td class='td top'><input type='hidden' name='itOid' value='"+data.oid+"' /></td>";
        investTaskRow += "<td class='td top'><input type='hidden' name='subject' /><a href='javascript:openView(\"" + data.oid + "\")'>" + data.subject + "</a></td>";
        investTaskRow += "<td class='td top'><div class='webEditor'></div></td>";
        
        investTaskRow += "<td class='td top center'><input type='hidden' name='tmUserName' class='tmUserName' />" + data.workerName + " / " + data.workerDeptName;
        investTaskRow += "<input type='hidden' name='tmUserOid' class='tmUserOid' /></td>";
        
        investTaskRow += "<td class='td top center'><input type='hidden' name='refUserNames' class='refUserNames' />" + typeCheck(data.memberNames);
        investTaskRow += "<input type='hidden' name='refUserOids' class='refUserOids' /></td>";
        
        investTaskRow += "<td class='td top center'><input type='hidden' id='reqDate' name='reqDate' class='requestDate'>" + data.requestDate + "</td>";
        
    }else{
        investTaskRow += "<td class='td top'><input type='checkbox' name='selectDel' /><input type='hidden' name='itOid' /></td>";
        investTaskRow += "<td class='td top'><input type='text' name='subject' class='txt_field WP90' /></td>";
        investTaskRow += "<td class='td top'><div class='webEditor'></div></td>";
        
        investTaskRow += "<td class='td top'><input type='text' name='tmUserName' id='tmUserName"+key+"' class='tmUserName"+key+ " WP70 txt_field' />";
        investTaskRow += "<input type='hidden' name='tmUserOid' id='tmUserOid"+key+"' class='tmUserOid"+key+"' /> ";
        investTaskRow += "<a href='javascript:searchUser(\"" + tmUserOid + "\",\"" + tmUserName + "\");'>";
        investTaskRow += "<img src='/plm/portal/images/icon_user.gif' border='0'></a></td>";
        
        investTaskRow += "<td class='td top'><input type='text' name='refUserNames' class='refUserNames"+key+" WP70 txt_fieldRO' readonly='readonly' />";
        investTaskRow += "<input type='hidden' name='refUserOids' class='refUserOids"+key+"' /> ";
        investTaskRow += "<a href='javascript:addMember(\"" + refUserOids + "\",\"" + refUserNames + "\");'>";
        
        investTaskRow += "<img src='/plm/portal/images/icon_user.gif' border='0'></a> ";
        investTaskRow += "<a href='javascript:CommonUtil.deleteValueByClass(\"" + refUserOids + "\",\"" + refUserNames + "\");'>";
        investTaskRow += "<img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>";
        /* 
        investTaskRow += "<a href='javascript:CommonUtil.deleteValue(\"" + userOid + "\",\"" + userName + "\");'>";
        investTaskRow += "<img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>";
         */
         
        investTaskRow += "<td class='td top'><input type='text' id='reqDate"+key+"' name='reqDate' class='requestDate txt_field WP70'></td>";
        //investTaskRow += "<a href='javascript:CommonUtil.deleteValue(\"" + reqDate + "\");'><img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>";
    }
    
    investTaskRow += "<td  class='td top center'><span class='completeDate' ></span></td>";
    investTaskRow += "<td  class='td top center'><span class='fileCnt'></span></td>";
    investTaskRow += "</tr>";
    
    $("#investTaskList").append(investTaskRow);
    $("#investTaskList .investTaskRow:last").data("key", key);
    $("#investTaskList .investTaskRow:last").find("input[name='subject']").data("message", "[회수 증빙 자료(관련부서)] 요청사항을 입력하세요.");
    $("#investTaskList .investTaskRow:last").find("input[name='tmUserOid']").data("message", "[회수 증빙 자료(관련부서)] 담당자를 입력하세요.");
    $("#investTaskList .investTaskRow:last").find("input[name='reqDate']").data("message", "[회수 증빙 자료(관련부서)] 완료 요청일자를 입력하세요.");
    
    if(data != null){
        $("#investTaskList .investTaskRow:last").find("input[name='itOid']").val(data.oid);
        $("#investTaskList .investTaskRow:last").find("input[name='subject']").val(data.subject);
        $("#investTaskList .investTaskRow:last").find("input[name='tmUserName']").val(data.workerName);
        $("#investTaskList .investTaskRow:last").find("input[name='tmUserOid']").val(data.workerOid);
        $("#investTaskList .investTaskRow:last").find("input[name='refUserNames']").val(data.memberNames);
        $("#investTaskList .investTaskRow:last").find("input[name='refUserOids']").val(data.memberOids);
        $("#investTaskList .investTaskRow:last").find("input[name='reqDate']").val(data.requestDate);
        $("#investTaskList .investTaskRow:last").find(".completeDate").html(data.completeDateState);
        
        var fileView = data.fileCnt;
        if("-" != fileView && data.investState == "IST004") {
            $("#investTaskList .investTaskRow:last").css("padding", "0");
            fileView = "<a href='javascript:invest.investTaskFileDownload(\"" + data.oid + "\")'><img src='/plm/portal/images/icon_file.gif'></a> " + data.fileCnt;
        }
        
        $("#investTaskList .investTaskRow:last").find(".fileCnt").html(fileView);
        
        $("#investTaskList .investTaskRow:last").find(".webEditor").html(data.webEditor);
    }
    
    if(data == null || data.investState != "IST004"){
        
        SuggestUtil.bind('USER', tmUserName, tmUserOid);
    }
    
    CalendarUtil.dateInputFormat(reqDate);
}

var delTaskList = new Array();

window.delInvestTaskList = function(){
    $("input[name='selectDel']").each(function(){
        var isChecked = $(this).is(":checked");
        if(isChecked){
            var row = new Object();
            row.delOid = $(this).next().val();
            delTaskList.push(row);
            $(this).parents(".investTaskRow:first").remove();
        }
    });
}

window.delInvestTaskList2 = function(){
    $("input[name='selectDel2']").each(function(){
        var isChecked = $(this).is(":checked");
        if(isChecked){
            var row = new Object();
            row.delOid = $(this).next().val();
            delTaskList.push(row);
            $(this).parents(".investTaskRow2:first").remove();
        }
    });
}
window.getInvestTaskList = function(){
    
    var investTaskList = new Array();
    $("#investTaskList .investTaskRow").each(function(i){
        
        var tmUserName = "tmUserName" + i;
        var tmUserOid = "tmUserOid" + i;
        
        var refUserNames = "refUserNames" + i;
        var refUserOids = "refUserOids" + i;
        
        
        var reqDate = "requestDate" + i;
        
        var row = new Object();
        row.itOid = $(this).find("input[name='itOid']").val();
        row.subject = $(this).find("input[name='subject']").val();
        row.tmUserName = $(this).find("input[name='tmUserName']").val();
        row.tmUserOid = $(this).find("input[name='tmUserOid']").val();
        
        row.refUserNames = $(this).find("input[name='refUserNames']").val();
        row.refUserOids = $(this).find("input[name='refUserOids']").val();
        
        row.requestDate = $(this).find("input[name='reqDate']").val();
        investTaskList.push(row);
    });
    
    return investTaskList;
}
window.setInvestTaskList = function(){
    
    var param = new Object();
    param.oid = $("#oid").val();
    
    ajaxCallServer("/plm/ext/invest/getInvestTaskList", param, function(data){
        window.console.log(data.itList);
        
        var itList = data.itList;
        
        for(var i = 0; i < itList.length; i++){
            addInvestTaskList(itList[i]);
        }
        
        itList = data.itList2;
        
        for(var i = 0; i < itList.length; i++){
            addinvestTaskList2(itList[i]);
        }
    });
}


window.getInvestTaskList2 = function(){
    
    var investTaskList = new Array();
    $("#investTaskList2 .investTaskRow2").each(function(i){
        
        var collectCode = "collectCode" + i;
        var collectExpense = "collectExpense" + i;
        var collectDate = "collectDate" + i;
        
        var progressSubject = "progressSubject" + i;
        

        
        var row = new Object();
        row.itOid = $(this).find("input[name='itOid']").val();
        row.collectCode = $(this).find("select[name='collectCode']").val();
        row.collectExpense = $(this).find("input[name='collectExpense']").val();
        row.collectDate = $(this).find("input[name='collectDate']").val();
        row.progressSubject = $(this).find("input[name='progressSubject']").val();
        
        investTaskList.push(row);
    });
    
    return investTaskList;
}

window.addinvestTaskList2 = function(data){
var key = $("#investTaskList2 .investTaskRow2:last").data("key");
    if(key == null) key = 0;
    else            key++;
    
    var collectCode = "collectCode" + key;
    var collectExpense = "collectExpense" + key;
    var collectDate = "collectDate" + key;
    var progressSubject = "progressSubject" + key;
    
    var investTaskRow = "<tr class='investTaskRow2'>";
    if(data != null && '${masterState}'== "IST002" && ${isManager}){
    	investTaskRow += "<td class='td top'><input type='checkbox' name='selectDel2' /><input type='hidden' name='itOid' /></td>";
        investTaskRow +="<td class='td top'>";
        investTaskRow +="<select id='collectCode' name='collectCode' className='fm_jmp' style='width: 100%; border: none;'>";
        <%for (int i = 0; i < numCode.size(); i++) {%>
        investTaskRow += "<option value=<%=numCode.get(i).get("code") %>><%=numCode.get(i).get("value")%></option>";
        <%}%>
        investTaskRow +="</select>";
        investTaskRow +="</td>";
        
        investTaskRow += "<td class='td top center'><input type='text' id='collectExpense' name='collectExpense' class='txt_field WP90' /></td>";
        investTaskRow += "<td class='td top'><input type='text' id='"+collectDate+"' name='collectDate' class='collectDate txt_field WP70'></td>";
        investTaskRow += "<td class='td top'><input type='text' id='progressSubject' name='progressSubject' class='txt_field WP90' /></td>";
        investTaskRow += "<td class='td top center'>" + data.workerName +"</td>";
        investTaskRow += "<td class='td top center'>" + data.workerDeptName +"</td>";        
        
    }else{
        investTaskRow += "<td class='td top'><input type='checkbox' name='selectDel2' /><input type='hidden' name='itOid' /></td>";
        investTaskRow +="<td class='td top'>";
        investTaskRow +="<select id='collectCode' name='collectCode' className='fm_jmp' style='width: 100%; border: none;'>";
        <%for (int i = 0; i < numCode.size(); i++) {%>
        investTaskRow += "<option value=<%=numCode.get(i).get("code") %>><%=numCode.get(i).get("value")%></option>";
        <%}%>
        investTaskRow +="</select>";
        investTaskRow +="</td>";
        
        investTaskRow += "<td class='td top'><input type='text' id='collectExpense' name='collectExpense' class='txt_field WP90' /></td>";
        investTaskRow += "<td class='td top'><input type='text' id='"+collectDate+"' name='collectDate' class='collectDate txt_field WP70'></td>";
        investTaskRow += "<td class='td top'><input type='text' id='progressSubject' name='progressSubject' class='txt_field WP90' /></td>";
        investTaskRow += "<td class='td top center'>${creatorName}</td>";
        investTaskRow += "<td class='td top center'>${creatorDeptName}</td>";
    }
    
    investTaskRow += "</tr>";
    
    $("#investTaskList2").append(investTaskRow);
    $("#investTaskList2 .investTaskRow2:last").data("key", key);
    $("#investTaskList2 .investTaskRow2:last").find("select[name='collectCode']").data("message", "회수비구분을 선택하세요.");
    $("#investTaskList2 .investTaskRow2:last").find("input[name='collectExpense']").data("message", "회수금액을 입력하세요.");
    $("#investTaskList2 .investTaskRow2:last").find("input[name='collectDate']").data("message", "회수일자를 입력하세요.");
    $("#investTaskList2 .investTaskRow2:last").find("input[name='progressSubject']").data("message", "진행상세를 입력하세요.");
    
    if(data != null){
        $("#investTaskList2 .investTaskRow2:last").find("input[name='itOid']").val(data.oid);
        $("#investTaskList2 .investTaskRow2:last").find("select[name='collectCode']").val(data.collectCode);
        $("#investTaskList2 .investTaskRow2:last").find("input[name='collectExpense']").val(data.collectExpense);
        $("#investTaskList2 .investTaskRow2:last").find("input[name='collectDate']").val(data.collectDate);
        $("#investTaskList2 .investTaskRow2:last").find("input[name='progressSubject']").val(data.progressSubject);
    }
    
    if(data == null || data.investState != "IST004"){
        
        CalendarUtil.dateInputFormat(collectDate);
    }
    
    $("input[name='collectExpense']").number(true);
}

window.getPjtList = function(){
    
    var pjtList = new Array();
    
    $("[name='productPjtNos']").each(function(i){
        var row = new Object();
        row.pjtNo = $(this).val(); 
        pjtList.push(row);
    });
    
    
    return pjtList;
}

window.saveInvest = function(isReqProgress){
	var reqNo = $('#reqNo').val();
	
	if(reqNo.search(/\s/) != -1){
		alert("투자품의번호에 공백이 포함되어 있어 공백을 제거합니다.");
	}
	
	$('#reqNo').val(reqNo.replace(/(\s*)/g, ""));
	
    var confirmMsg = "저장하시겠습니까?";
    var completeMsg = "저장되었습니다.";
    
    if(isReqProgress == 'accept') {
        confirmMsg = "영업담당자에게 접수요청 하시겠습니까?";
        completeMsg = "접수요청되었습니다.";
    }else if(isReqProgress == 'progress') {
        confirmMsg = "진행요청 하시겠습니까?";
        completeMsg = "진행요청되었습니다.";
        
        /* if($("#investTaskList tr").length == 0 ||$( "#investTaskList2 tr").length == 0){
            alert("투자비 회수 진행현황 또는 회수 증빙 자료가 입력되지 않았습니다.");
            return;
        } */
        
        /* if($("#investTaskList tr").length == 0){ //2019.12.17 체크 해제 요청 (박상수 차장)
            alert("회수 증빙 자료가 입력되지 않았습니다.");
            return;
        } */
    }
    
    
    var oldRequestDate = $('#oldRequestDate').val();
    var newRequestDate = $('#requestDate').val();
    var changeHistory = $("#changeHistory").val();
    
    if(changeHistory == '' && oldRequestDate != '' &&oldRequestDate < newRequestDate) {
        alert('회수예정일자가 늦어지는 경우 변경사유를 입력하셔야합니다.');
        $("#changeHistory").focus();
        return;
    }
    
    
/*  if($("#productProjectTableBody tr").length == 0){
        alert("프로젝트 정보가 입력되지 않았습니다.");
        return;
    } */
    
    if(invest.needCheckAttribute() && confirm(confirmMsg)){
        
        var param = $("[name=uploadForm]").serializefiles();
        param.append("itList", JSON.stringify(getInvestTaskList()));
        param.append("itList2", JSON.stringify(getInvestTaskList2()));
        param.append("pjtList", JSON.stringify(getPjtList()));
        param.append("delTaskList", JSON.stringify(delTaskList));
        param.append("isReqProgress", isReqProgress);
        
        ajaxCallServer("/plm/ext/invest/saveInvestMaster", param, function(data){
            
            alert(completeMsg);
            
            if(${isModify}){
                location.href = '/plm/ext/invest/viewInvestPopup?oid=' + data.oid;
            }else{
                location.href = '/plm/ext/invest/saveInvestPopup?oid=' + data.oid;
            }
        });
    }
}
window.deleteInvest = function(){
    
    if(confirm("삭제 하시겠습니까?")){
        
        var param = new Object();
        param.oid = "${imDTO.oid}";

        ajaxCallServer("/plm/ext/invest/deleteInvest", param, function(data){
            self.close();
        });
    }
}

function selectDr(){
      //개발 검토  : developmentStep=R
      //제품     : developmentStep=D
        var url="/plm/jsp/dms/SearchDevRequestPop.jsp?method=selDr&developmentStep=D&mode=one";
        openWindow(url,"","800","600","status=1,scrollbars=no,resizable=no");
}

function selDr(arr){
    var param = "command=drSearch";
    for (var i = 0; i < arr.length; i++){
        param += "&drOid=" + encodeURIComponent(arr[i][0]);
    }
    var url = "/plm/jsp/project/ProjectDevRequestAction.jsp";
    doPostCallServer(url, param, onTargetSet, true);
}

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
    var l_reqNo = showElements[0].getElementsByTagName("l_drNumber");
    var l_drKeyOid = showElements[0].getElementsByTagName("l_drKeyOid");
    var l_name   = showElements[0].getElementsByTagName("l_name");
    var l_investExpense = showElements[0].getElementsByTagName("l_investExpense");
    var form = document.forms[0];
    form.reqNo.value = decodeURIComponent(l_reqNo[0].text);
    form.drKeyOid.value = decodeURIComponent(l_drKeyOid[0].text);
    form.reqName.value = decodeURIComponent(l_name[0].text);
    form.investExpense.value = decodeURIComponent(l_investExpense[0].text)/1000;
    $('#investExpense').number( true );
    //고객처
    var showElements_si = xmlDoc.selectNodes("//data_subinfo");
    var l_subOid = showElements_si[0].getElementsByTagName("l_subOid");
    var l_subName = showElements_si[0].getElementsByTagName("l_subName");
    
/*     var subName = "";
    for(var i = 0; i < l_subOid.length; i++){
        
        if(l_subOid.length-1 == i){
            subName += decodeURIComponent(l_subName[i].text);   
        }else{
            subName += decodeURIComponent(l_subName[i].text)+",";   
        }
        
    }
    
    form.subContractorName.value = subName; */
    
    addProjectList(form.reqNo.value);

}


window.addProjectList = function(drNumber){
    
    
    $("#productProjectTable tr:not(:first)").remove();  
    
    var param = new Object();
    param.drNumber = drNumber;
    ajaxCallServer("/plm/ext/invest/getProjectListByDevRequest", param, function(data){
        window.console.log(data.projectList);
        
        var itList = data.projectList;
        
        for(var i = 0; i < itList.length; i++){
            var row = "<tr>";
            
            row += "<td class=tdwhiteM><a href=javascript:openProject("+itList[i].pjtNo+");>"+itList[i].pjtNo+"</a></td>";
            row += "<td class=tdwhiteM>"+itList[i].pjtName+"</td>";
            row += "<td class=tdwhiteM>"+itList[i].salesName+"</td>";
            row += "<td class=tdwhiteM>"+itList[i].salesDep+"</td>";
            row += "<td class=tdwhiteM>"+itList[i].execEndDate+"</td>";
            row += "</tr>";
            //$('#productProjectTable > tbody:last').append(row);
            //$("#productProjectTableBody").append(row);
            
        }
    });
}

window.resetValue = function(){
    CommonUtil.deleteValue('reqNo','drKeyOid');
    $('[name=reqName]').val('');
    $('[name=investExpense]').val('');
    //$('[name=subContractorName]').val('');
    $("#productProjectTable tr:not(:first)").remove();
}



function selProjectNo () {
    
    //프로젝트검색창을 열어 프로젝트를 선택한다.
    var url="/plm/jsp/project/SearchPjtPop.jsp?mode=multi&modal=N";
    openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
}

function setProject(returnValue){
    
    if ( returnValue == null ) {
        return true;
    }
    var objArr = returnValue;
    var proj_number;
    var customer;
    var listInfo = new Array();
    
    
    
    for ( var i = 0; i < objArr.length; i++ ) {
        proj_number = objArr[i][1];
        
        proj_name = objArr[i][2];
        customer = objArr[i][16];
        
        var isInsert = true;
        
        $("[name='productPjtNos']").each(function(i){
            if($(this).val() == proj_number){
                isInsert=false;
            }
        });
        
        if(isInsert){
            var row = {};
            row.pjtNo = proj_number;
            row.customer = customer;
            listInfo.push(row);
        }
    }
    
    addProject(listInfo);
}


function addProject(listInfo){
    
    var param = new Object();
    param.data = listInfo;
    
    ajaxCallServer("/plm/ext/invest/getProjectInfoByPjtNo", param, function(data){
        
        var pjtList = data.pjtList;
        
        for(var i = 0; i < pjtList.length; i++){
            var row = "<tr>";
            row +="<td class=tdwhiteM><a href=\"#\" onclick=\"javascript:delProject(this);return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a></td>"; 
            
            row += "<td class=tdwhiteM><a href=javascript:openProject('"+pjtList[i].pjtNo+"');>"+pjtList[i].pjtNo+"</a></td>";
            row += "<td class=tdwhiteM>"+pjtList[i].pjtName+"</td>";
            row += "<td class=tdwhiteM>"+pjtList[i].pjt_customer+"</td>";
            row += "<td class=tdwhiteM>"+pjtList[i].salesName+"</td>";
            row += "<td class=tdwhiteM>"+pjtList[i].salesDept+"</td>";
            row += "<td class=tdwhiteM>"+pjtList[i].execEndDate+"</td>";
            
            row +="<input name='productPjtNos' id='productPjtNos' type='hidden' value='"+pjtList[i].pjtNo+"'>";
            /* row +="<input name='pjt_customer' id='pjt_customer' type='hidden' value='"+listInfo[i].customer.substr(1)+"'>"; */
            row +="</tr>";
            
            //$('#productProjectTable > tbody:last').append(row);
            $("#productProjectTableBody").append(row);
        }
        
        invest.customerSetting();
        
    });
}

function delProject(obj){
    
    $(obj).parent().parent().remove();
    invest.customerSetting();
}

</script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${imDTO.oid}"/>
<input type="hidden" id="oldRequestDate" name="oldRequestDate" value="${imDTO.requestDate }">
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />고객 투자비 회수관리 
            <c:if test="${isModify }">수정</c:if>
            <c:if test="${!isModify }">등록</c:if>
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <c:if test="${imDTO.investState eq 'IST000' and isRegMember}">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:saveInvest('accept');" class="btn_blue">
                            접수요청
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
            <c:if test="${imDTO.investState eq 'IST001' and isManager}">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:saveInvest('progress');" class="btn_blue">
                            진행요청
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:saveInvest('');" class="btn_blue">
                        <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%>
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <c:if test="${imDTO.investState eq 'IST000' and isRegMember }">
                <span class="in-block v-middle r-space7">
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center">
                            <a href="javascript:deleteInvest();" class="btn_blue">
                            <%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%>
                            </a>
                        </span>
                        <span class="pro-cell b-right"></span>
                    </span>
                </span>
            </c:if>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 float-l b-space5">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>기본정보
        </div>
        <div class="b-space5" >
            <table summary="" class="info">
                <colgroup>
                    <col width="150" />
                    <col width="*" />
                    <col width="150" />
                    <col width="*" />
                    <col width="150" />
                    <col width="*" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="tdblueL">투자품의번호<span class="red">*</span></td>
                        <td class="tdwhiteL">
                            <c:choose>
                            <c:when test="${isRegMember}">
                            <input type="text" name="reqNo" id="reqNo" value="${imDTO.reqNo }" class='WP100 txt_field'>
                            </c:when>
                            <c:otherwise><input type="text" name="reqNo" id="reqNo" value="${imDTO.reqNo }" class='WP100 txt_fieldRO' readonly="readonly"></c:otherwise>
                            </c:choose>
<%--                             <input type="hidden" name="drKeyOid" value="${imDTO.drKeyOid }">
                            <a href="javascript:selectDr();">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:resetValue();">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a> --%>
                        </td>
                        <td class="tdblueL">품의서명<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                           <c:choose>
                           <c:when test="${isRegMember}">
                           <input type="text" name="reqName" value="${imDTO.reqName }" class='WP100 txt_field'>
                           </c:when>
                           <c:otherwise><input type="text" name="reqName" value="${imDTO.reqName }" class='WP100 txt_fieldRO' readonly="readonly"></c:otherwise>
                           </c:choose>
                        </td>
                        
                        <td class="tdblueL">유형<span class="red">*</span></td>
                        <td class="tdwhiteL">
                        <ket:select id="investTypeCode" name="investTypeCode" className="fm_jmp" style="width: 170px;" codeType="INVESTTYPE" multiple="multiple" useCodeValue="true" value="${imDTO.investTypeCode}"/>
                        </td>
                        
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            투자비 (금형/설비)
                        </td>
                        <td class="tdwhiteL">
                           <c:choose>
                           <c:when test="${isRegMember}">
                           <input type="text" id = "investExpense_1" name="investExpense_1" value="<fmt:formatNumber>${imDTO.investExpense_1 }</fmt:formatNumber>" class='WP100 txt_field' style="text-align: right;">
                           </c:when>
                           <c:otherwise>
                           <input type="text" id = "investExpense_1" name="investExpense_1" value="<fmt:formatNumber>${imDTO.investExpense_1 }</fmt:formatNumber>" class='WP100 txt_fieldRO' readonly="readonly" style="text-align: right;">
                           </c:otherwise>
                           </c:choose>
                        </td>
                        <td class="tdblueL">
                            투자비 (QDM/기타)
                        </td>
                        <td class="tdwhiteL">
                           <c:choose>
                           <c:when test="${isRegMember}">
                           <input type="text" id = "investExpense_2" name="investExpense_2" value="<fmt:formatNumber>${imDTO.investExpense_2 }</fmt:formatNumber>" class='WP100 txt_field' style="text-align: right;">
                           </c:when>
                           <c:otherwise>
                           <input type="text" id = "investExpense_2" name="investExpense_2" value="<fmt:formatNumber>${imDTO.investExpense_2 }</fmt:formatNumber>" class='WP100 txt_fieldRO' readonly="readonly" style="text-align: right;">
                           </c:otherwise>
                           </c:choose>
                        </td>
                        <td class="tdblueL">
                            투자비 합계
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.investExpense }</fmt:formatNumber>
                        </td>
<%--                         <td class="tdblueL">
                            접점고객
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" id="subContractorName" name="subContractorName" value="${imDTO.subContractorName }" class='WP70 txt_fieldRO' readonly="readonly">
                        </td> --%>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            회수비 (금형/설비)
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.collectExpense_1}</fmt:formatNumber>
                        </td>
                        <td class="tdblueL">
                            회수비 (QDM/기타)
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.collectExpense_2}</fmt:formatNumber>
                        </td>
                        <td class="tdblueL">
                            회수비 합계
                        </td>
                        <td class="tdwhiteR">
                           <fmt:formatNumber>${imDTO.collectExpense}</fmt:formatNumber>
                        </td>
                    </tr>
                        <td class="tdblueL">회수 예정일자<c:if test="${isManager}"><span class="red">*</span></c:if></td>
                        
                        
                        <c:choose>
                            <c:when test="${isManager}">
                            <td class="tdwhiteL">
                                <input type="text" id="requestDate" name="requestDate" class="W90 txt_field" value="${imDTO.requestDate }">
                                <a href="javascript:CommonUtil.deleteValue('requestDate');">
                                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            </td>
                            </c:when>
                            <c:otherwise><td class="tdwhiteL">${imDTO.requestDate }</c:otherwise>
                        </c:choose>
                        
                        
                        <td class="tdblueL">회수 완료일
                        </td>
                        <td class="tdwhiteL">
                            ${imDTO.completeDate }
                        </td>
                        <td class="tdblueL">진행상태</td>
                        <td class="tdwhiteL">${imDTO.investStateName }</td>
                    <tr>
                        <td class="tdblueL">
                            영업 담당자<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="managerName" class="WP70 txt_field" value="${imDTO.managerName }">
                            <input type="hidden" name="managerOid" id="managerOid" value="${imDTO.managerOid }"> 
                            <a href="javascript:SearchUtil.selectOneUser('managerOid','managerName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('managerOid','managerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                            최초 등록자
                        </td>
                        <td class="tdwhiteL">
                            ${creatorName }
                        </td>
                        <td class="tdblueL">
                            등록일
                        </td>
                        <td class="tdwhiteL">
                            ${createDate }
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">관련 프로젝트</td>
                        <td class="tdwhiteL" colspan="5">
                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr><td class="space5"></td></tr>
                           </table>
                           
                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                <table id="productProjectTable" summary="" class="table-info fixed">
                                    
                                        <colgroup>
                                            <col width="2%" />
                                            <col width="13%" />
                                            <col width="30%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                        </colgroup>
                                    <tr>
                                        <td class="tdgrayM"><c:if test="${isRegMember }"><a href="#" onclick="javascript:selProjectNo();return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></c:if></td>
                                        <td class="tdgrayM">ProjectNo</td>
                                        <td class="tdgrayM">Proejct명</td>
                                        <td class="tdgrayM">접점고객</td>
                                        <td class="tdgrayM">영업</td>
                                        <td class="tdgrayM">영업부서</td>
                                        <td class="tdgrayM">프로젝트완료일</td>
                                    </tr>
                                    <tbody id="productProjectTableBody">
                                    <c:forEach items="${imDTO.productProjectList}" var="projectList" varStatus="i">
                                    <tr>
                                        <td class="tdwhiteM"><c:if test="${isRegMember }"><a href='#' onclick="javascript:delProject(this);return false;"><b><img src='/plm/portal/images/b-minus.png'></b></a></c:if></td>
                                        <td class="tdwhiteM"><a href="javascript:openProject('${projectList.pjtNo}');">${projectList.pjtNo}</a></td>
                                        <td class="tdwhiteM">${projectList.pjtName}</td>
                                        <td class="tdwhiteM">${projectList.pjt_customer}</td>
                                        <td class="tdwhiteM">${projectList.salesName}</td>
                                        <td class="tdwhiteM">${projectList.salesDept}</td>
                                        <td class="tdwhiteM">${projectList.execEndDate}</td>
                                        <input name='productPjtNos' id='productPjtNos' type='hidden' value='${projectList.pjtNo}'>
                                        <%-- <input name='pjt_customer' id='pjt_customer' type='hidden' value='${projectList.pjt_customer}'> --%>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr><td class="space5"></td></tr>
                            </table>
                         </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">
                            비고사항
                        </td>
                        <td class="tdwhiteL" colspan="5" >
                            <textarea name="bigo" class='txt_field' style="height:70px;width:100%;">${imDTO.bigo}</textarea>
                        </td>
                    </tr>
                    
                    <tr id="changeHistoryTr" style="display: none">
                        <td class="tdblueL">
                            회수일정 변경사유
                        </td>
                        <td class="tdwhiteL" colspan="5" >
                            <textarea name="changeHistory" id="changeHistory" class='txt_field' style="height:70px;width:100%;"></textarea>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">
                            투자품의 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv1"></div>
                            <script>
                            var mode = "";
                            if(!${isRegMember} && !${isAdmin}){
                                mode = "view";
                            }
                            
                            $("#attachFileDiv1").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&tid=1&mode="+mode);
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            회수증빙 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv2"></div>
                            <script>
                            var mode = "";
                            if(!${isManager} && !${isAdmin}){
                                mode = "view";
                            }
                            $("#attachFileDiv2").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&tid=2&mode="+mode);
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            일반 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv3"></div>
                            <script>
                            $("#attachFileDiv3").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&tid=3");
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${isModify}">
        <c:if test="${isManager}">
        <div class="b-space5 float-r" style="text-align: right; margin-top:10px;">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:addinvestTaskList2();" class="btn_blue">추가</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:delInvestTaskList2();" class="btn_blue">삭제</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        </c:if>
        <div class="sub-title-02 float-l b-space5" style="margin-top:10px;">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>투자비 회수 진행현황 (영업)
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="30" />
                    <col width="100" />
                    <col width="150" />
                    <col width="110" />
                    <col width="*" />
                    <col width="100" />
                    <col width="100" />
                </colgroup>
                <thead>
                    <tr>
                        <td>선택</td>
                        <td>회수비 구분<span class="red">*</span></td>
                        <td>회수금액<span class="red">*</span></td>
                        <td>회수일자<span class="red">*</span></td>
                        <td>진행상세</td>
                        <td>등록자</td>
                        <td>등록부서</td>
                    </tr>
                </thead>
                <tbody id="investTaskList2">
                </tbody>
            </table>
        </div>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr><td class="space20"></td></tr>
        </table>
        <c:if test="${isManager}">
        <div class="b-space5 float-r" style="text-align: right; margin-top:10px;">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:addInvestTaskList();" class="btn_blue">추가</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:delInvestTaskList();" class="btn_blue">삭제</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        </c:if>
        <div class="sub-title-02 float-l b-space5" style="margin-top:10px;">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>회수 증빙 자료 (관련부서)
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="30" />
                    <col width="200" />
                    <col width="*" />
                    <col width="120" />
                    <col width="150" />
                    <col width="110" />
                    <col width="100" />
                    <col width="80" />
                </colgroup>
                <thead>
                    <tr>
                        <td>선택</td>
                        <td>요청사항<span class="red">*</span></td>
                        <td>진행사항</td>
                        <td>담당자<span class="red">*</span></td>
                        <td>참조자</td>
                        <td>완료 요청일자<span class="red">*</span></td>
                        <td>완료일자(상태)</td>
                        <td>파일</td>
                    </tr>
                </thead>
                <tbody id="investTaskList">
                </tbody>
            </table>
        </div>
    </div>
    </c:if>
</form>
</body>
