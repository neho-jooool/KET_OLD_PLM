<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/issue/issue.js?ver=0.1"></script>
<script>
var initFlag = 0;
var oldRowCnt=0;
$(document).ready(function(){
	
	CommonUtil.singleSelect('reqCode',130);
	CommonUtil.singleSelect('detailCode',170);
	CommonUtil.singleSelect('rank',130);
	CalendarUtil.dateInputFormat('requestDate');
	
	SuggestUtil.bind('USER', 'managerName', 'managerOid');
	SuggestUtil.bind('CUSTOMEREVENT', 'lastCustomerName', 'lastCustomer');
	SuggestUtil.bind('CUSTOMER', 'subContractorName', 'subContractor');
	
	SuggestUtil.bind('CARTYPE', 'oemName', 'oemOid');
    
	if(!${isModify}){
		setProjectInfo("${projectOid}");
		
		//alert(divisionCode);
		//this.close();
	}else{
		setIssueTaskList();
		setIssuePartList();
		
		
		setSpanToggle('${imDTO.reqCode}','customerSpan');
        setSpanToggle('${imDTO.reqCode}','subConSpan');
        setSpanToggle('${imDTO.reqCode}','oemSpan');
	}
	
	$("input[name=reqName]").data("message", "요청명을 입력하세요.");
	$("select[name=reqCode]").data("message", "요청구분을 선택하세요.");
	$("select[name=detailCode]").data("message", "상세구분을 선택하세요.");
	$("select[name=rank]").data("message", "등급을 선택하세요.");
	$("input[name=managerOid]").data("message", "요청자를 입력하세요.");
	$("input[name=requestDate]").data("message", "완료 요청일자를 입력하세요.");
	//$("input[name=lastCustomer]").data("message", "자동차사를 입력하세요.");
	//$("input[name=subContractor]").data("message", "고객사를 입력하세요.");
	//$("input[name=oemOid]").data("message", "대표차종을 입력하세요.");
	
	
	$("#selectPartAll").change(function(){

        var isAllChecked = $("#selectPartAll").is(":checked");
        
        $("input[name=selectPartDel]").prop("checked",isAllChecked);

    });
	
	$("#reqCode").change(function(){
		
		issue.initDetailCode($(this).val());
		setSpanToggle($(this).val(),'customerSpan');
		setSpanToggle($(this).val(),'subConSpan');
		setSpanToggle($(this).val(),'oemSpan');

	});
	var reqCode = '${imDTO.reqCode}';
	if(reqCode != ''){
		issue.initDetailCode('${imDTO.reqCode}','${imDTO.detailCode }');
	}
	
	
});

window.setSpanToggle = function(reqCode,spanId){
	if(reqCode == "REQ001") {
        if(!$('#'+spanId).hasClass("red")) {
            $('#'+spanId).addClass("red");
            $('#'+spanId).text('*');
        }
        
    } else {
        if($('#'+spanId).hasClass("red")) {
            $('#'+spanId).removeClass("red");
            $('#'+spanId).text('');
        }
    }
}


window.setProjectInfoCall = function(data){
	
	var projectNo= "";
	
    if(data.length > 0) {
    	var oid = data[0][0];
    	setProjectInfo(oid);
    }
}
window.setProjectInfo = function(oid){
	
	if(!${isModify}){
        if('${divisionCode}' == ''){
        	alert('사용자의 계정에 사업부가 지정되어 있지 않습니다.\r\n관리자에게 사업부등록을 요청바랍니다.');
        	window.close();
        }
    }
	
    
    var param = new Object();
    param.oid = oid;
    
    ajaxCallServer("/plm/ext/issue/getProjectInfo", param, function(data){
    	
    	$("input[name=pboNo]").val(checkNull(data["projectNo"]));
    	$("input[name=pboName]").val(checkNull(data["projectName"]));
    	$("#pboName").text(checkNull(data["projectName"]));
    	$("input[name=pboOid]").val(oid);
    	
    	$("input[name=lastCustomerName]").val(checkNull(data["lastCustomerName"]));
    	$("input[name=lastCustomer]").val(checkNull(data["lastCustomerCode"]));
        
    	$("input[name=subContractorName]").val(checkNull(data["subContractorName"]));
    	$("input[name=subContractor]").val(checkNull(data["subContractorCode"]));
        
    	$("input[name=oemName]").val(checkNull(data["oemName"]));
    	$("input[name=oemOid]").val(checkNull(data["oemOid"]));
    	
    }, false);
}

window.acceptRoleMember = function(attacheMembers){
	if(typeof attacheMembers == "undefined" || attacheMembers == null) {
		return;
	}
	    
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
	    
	$("input[name=" + window.memberVal + "]").val(values);
	$("input[name=" + window.memberName  + "]").val(names);
}

window.addMember = function(valueTarget, nameTarget) {

	window.memberVal = valueTarget;
	window.memberName = nameTarget;
	
	var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=acceptRoleMember";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'addMember', 800, 710, opts);
    
}

window.setSubContractor = function(data){
	
    var nodeCodeStr='', nodeNameStr='';
    
    window.console.log(data);
    
    for(var i=0; i < data.length; i++){
        if(i == data.length - 1){
        	nodeCodeStr += data[i][1];
        	nodeNameStr += data[i][2];
        }else{
        	nodeCodeStr += data[i][1]+',';     
        	nodeNameStr += data[i][2]+',';
        }
    }
    $('[name=subContractorName]').val(nodeNameStr);
    $('[name=subContractor]').val(nodeCodeStr);
}

window.addIssuePartList = function(){
	
	showProcessing();
	SearchUtil.selectPart('addPartAfterFunc','pType=');	

}

function addPartAfterFunc( rtnArr )
{
    for(var i = 0; i < rtnArr.length ; i++){
        var arr = new Array();
        arr = rtnArr[i];
        addPart(arr);
    }   
}

function addPart(arr){
    var paramOid = arr[8];
    
    var rtnFlag = false;
    
    $.each($('[name=partOid]'), function(i, val){
        if(val.value == paramOid) {
            alert(LocaleUtil.getMessage('09104',arr[1]));//"선택한 부품은 이미 추가된 부품입니다."
            rtnFlag = true;
            return;
        }
    });
    
    if(rtnFlag){
        return;
    }
    
    /**
     * 부품 검색
     * 
     * type(부품분류) : 
        'A' 모두 
        ,'P' 제품
        ,'D' DIE NO 선택
        ,'M' 금형부품
     * 
     * fncall :
     *  후처리 함수명(objectArray 로 리턴함)
         subArr[0] = "wt.part.WTPart:" + R[i].Oid;//oid
         subArr[1] = R[i].PartNumber;//number
         subArr[2] = R[i].PartName;//name
         subArr[3] = R[i].PartVersion;//version
         subArr[4] = R[i].PartType;//type
         subArr[5] = R[i].DieNo;//dieno
         subArr[6] = R[i].DieName;
         subArr[7] = R[i].DieCnt;
         subArr[8] = R[i].OidMaster;//wtpartmaster oid
     */
     var addRow = "<tr class='partRow'>";
    
    addRow += "<td class='td top'><input type='checkbox' name='selectPartDel' /><input type='hidden' name='partOid' value='"+arr[8]+"'/></td>";
    addRow += "<td class='td top'>"+arr[1]+"</td>";
    addRow += "<td class='td top'>"+arr[2]+"</td>";
    addRow += "</tr>";
    
    $("#issuePartList").append(addRow);
}

function addPartRow(data){
    var addRow = "<tr class='partRow'>";
    
    addRow += "<td class='td top'><input type='checkbox' name='selectPartDel' /><input type='hidden' name='partOid' value='"+data.partOid+"'/></td>";
    addRow += "<td class='td top'>"+data.partNo+"</td>";
    addRow += "<td class='td top'>"+data.partName+"</td>";
    addRow += "</tr>";
    
    $("#issuePartList").append(addRow);
}

window.delIssuePartList = function(){
    $("input[name='selectPartDel']").each(function(){
        var isChecked = $(this).is(":checked");
        if(isChecked){
            //$(this).parents(".partRow:first").remove();
            $(this).parent().parent().remove();
        }
    });
}

window.getIssuePartList = function(){
    
    var partList = new Array();

    $("input[name='partOid']").each(function(){
    	
        var row = new Object();
        row.partOid = $(this).val();
        partList.push(row);
    });
    
    return partList;
}

function addUser(tmUserOid, tmUserName, fnCall){
	window.CtmUserOid = tmUserOid;
	window.CtmUserName = tmUserName;
	SearchUtil.selectOneUser(tmUserOid,tmUserName,fnCall);
}

function setUser(returnValue){
    var infoArr = returnValue[0];
    $('[name='+window.CtmUserOid+']').val(infoArr[0]);
    $('[name='+window.CtmUserName+']').val(infoArr[4]);
}

window.addIssueTaskList = function(data){
	
	var key = $("#issueTaskList .issuTaskRow:last").data("key");
	
	if(key == null) key = 0;
	else            key++;
	
	var tmUserName = "tmUserName" + key;
	var tmUserOid = "tmUserOid" + key;
	var refUserNames = "refUserNames" + key;
    var refUserOids = "refUserOids" + key;
	var reqDate = "requestDate" + key;
	
	var issuTaskRow = "<tr class='issuTaskRow'>";
	
	if(data != null && data.issueState == "IST004"){
		issuTaskRow += "<td class='td top'><input type='hidden' name='itOid' /></td>";
		issuTaskRow += "<td class='td top'><input type='hidden' name='subject' /><a href='javascript:openView(\"" + data.oid + "\")'>" + data.subject + "</a></td>";
		issuTaskRow += "<td class='td top'><div class='webEditor'></div></td>";
		
		issuTaskRow += "<td class='td top center'><input type='hidden' name='" + tmUserName + "' class='tmUserName' />" + data.workerName + " / " + data.workerDeptName;
        issuTaskRow += "<input type='hidden' name='" + tmUserOid + "' class='tmUserOid' /></td>";
        
        issuTaskRow += "<td class='td top center'><input type='hidden' name='" + refUserNames + "' class='refUserNames' />" + data.workerName + " / " + data.workerDeptName;
        issuTaskRow += "<input type='hidden' name='" + refUserOids + "' class='refUserOids' /></td>";
        
        issuTaskRow += "<td class='td top center'><input type='hidden' id='" + reqDate + "' name='" + reqDate + "' class='requestDate'>" + data.requestDate + "</td>";
        
    }else{
    	
    	issuTaskRow += "<td class='td top'><input type='checkbox' name='selectDel' /><input type='hidden' name='itOid' /></td>";
    	issuTaskRow += "<td class='td top'><input type='text' name='subject' class='txt_field WP90' /></td>";
        issuTaskRow += "<td class='td top'><div class='webEditor'></div></td>";
        
        issuTaskRow += "<td class='td top'><input type='text' name='" + tmUserName + "' class='tmUserName WP80 txt_field' />";
        issuTaskRow += "<input type='hidden' name='" + tmUserOid + "' class='tmUserOid' /> ";
        issuTaskRow += "<a href='javascript:addUser(\"" + tmUserOid + "\",\"" + tmUserName + "\",\"setUser\");'>";
        issuTaskRow += "<img src='/plm/portal/images/icon_user.gif' border='0'></a></td>";
        
        issuTaskRow += "<td class='td top'><input type='text' name='" + refUserNames + "' class='refUserNames WP70 txt_fieldRO' readonly='readonly' />";
        issuTaskRow += "<input type='hidden' name='" + refUserOids + "' class='refUserOids' /> ";
        issuTaskRow += "<a href='javascript:addMember(\"" + refUserOids + "\",\"" + refUserNames + "\");'>";
        
        issuTaskRow += "<img src='/plm/portal/images/icon_user.gif' border='0'></a> ";
       	issuTaskRow += "<a href='javascript:CommonUtil.deleteValue(\"" + refUserOids + "\",\"" + refUserNames + "\");'>";
     	issuTaskRow += "<img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>";
        /* 
        issuTaskRow += "<a href='javascript:CommonUtil.deleteValue(\"" + userOid + "\",\"" + userName + "\");'>";
        issuTaskRow += "<img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>";
         */
         
        issuTaskRow += "<td class='td top'><input type='text' id='" + reqDate + "' name='" + reqDate + "' class='requestDate txt_field WP70'></td>";
        //issuTaskRow += "<a href='javascript:CommonUtil.deleteValue(\"" + reqDate + "\");'><img src='/plm/portal/images/icon_delete.gif' border='0'></a></td>";
    }
	
	issuTaskRow += "<td  class='td top center'><span class='completeDate' ></span></td>";
	issuTaskRow += "<td  class='td top center'><span class='fileCnt'></span><input type='hidden' name='changeData' value='' /><input type='hidden' name='rejectCheck' value='' /></td>";
	issuTaskRow += "</tr>";
	
	$("#issueTaskList").append(issuTaskRow);
	$("#issueTaskList .issuTaskRow:last").data("key", key);
	$("#issueTaskList .issuTaskRow:last").find("input[name='subject']").data("message", "세부 요청사항을 입력하세요.");
	$("#issueTaskList .issuTaskRow:last").find("input[name='" + tmUserOid + "']").data("message", "세부 담당자를 입력하세요.");
	$("#issueTaskList .issuTaskRow:last").find("input[name='" + reqDate + "']").data("message", "세부 완료 요청일자를 입력하세요.");
	
	if(data != null){
		oldRowCnt++;
		$("#issueTaskList .issuTaskRow:last").find("input[name='changeData']").val(data.subject+data.workerOid+data.memberOids+data.requestDate);
		$("#issueTaskList .issuTaskRow:last").find("input[name='itOid']").val(data.oid);
		$("#issueTaskList .issuTaskRow:last").find("input[name='subject']").val(data.subject);
		$("#issueTaskList .issuTaskRow:last").find("input[name='" + tmUserName + "']").val(data.workerName);
		$("#issueTaskList .issuTaskRow:last").find("input[name='" + tmUserOid + "']").val(data.workerOid);
		$("#issueTaskList .issuTaskRow:last").find("input[name='" + refUserNames + "']").val(data.memberNames);
        $("#issueTaskList .issuTaskRow:last").find("input[name='" + refUserOids + "']").val(data.memberOids);
		$("#issueTaskList .issuTaskRow:last").find("input[name='" + reqDate + "']").val(data.requestDate);
		$("#issueTaskList .issuTaskRow:last").find(".completeDate").html(data.completeDateState);
		
		var fileView = data.fileCnt;
		if("-" != fileView && data.issueState == "IST004") {
			$("#issueTaskList .issuTaskRow:last").css("padding", "0");
	        fileView = "<a href='javascript:issue.issueTaskFileDownload(\"" + data.oid + "\")'><img src='/plm/portal/images/icon_file.gif'></a> " + data.fileCnt;
		}
		
		$("#issueTaskList .issuTaskRow:last").find(".fileCnt").html(fileView);
		
		$("#issueTaskList .issuTaskRow:last").find(".webEditor").html(data.webEditor);
	}
	
	if(data == null || data.issueState != "IST004"){
		SuggestUtil.bind('USER', tmUserName, tmUserOid);
	    CalendarUtil.dateInputFormat(reqDate);
	}
}

window.delIssueTaskList = function(){
	$("input[name='selectDel']").each(function(){
		var isChecked = $(this).is(":checked");
		if(isChecked){
			$(this).parents(".issuTaskRow:first").remove();
		}
	});
}


window.reStartIssueTaskList = function(){
	
	var reStartIssueTaskList = new Array();
	var ErrMsg = "";
	var checkCount = 0;
	
	var isChange = false;
    $("input[name='selectDel']").each(function(cnt){
    	var curRow = $(this).closest('tr').prevAll().length;
    	var oldData = $(this).parents(".issuTaskRow:first").find("input[name='changeData']").val();
    	oldData = oldData.replace(/undefined/g,"");
    	var newData = $(this).parents(".issuTaskRow:first").find("input[name='subject']").val() + $(this).parents(".issuTaskRow:first").find("input[name=tmUserOid"+curRow+"]").val() + $(this).parents(".issuTaskRow:first").find("input[name=refUserOids"+curRow+"]").val() + $(this).parents(".issuTaskRow:first").find("input[name=requestDate"+curRow+"]").val();
    	
    	if(oldData != newData){
    		window.console.log("oldData : " + oldData);
    		window.console.log("newData : " + newData + " 그리고 curRow : "+curRow);
    		isChange = true;
    	}
    	
        var isChecked = $(this).is(":checked");
        if(isChecked){
            var itOid = $(this).parents(".issuTaskRow:first").find("input[name='itOid']").val();
            var state = $(this).parents(".issuTaskRow:first").find(".completeDate").text();
            if(itOid == '' || state.indexOf('반려') < 0 ){
            	
            	ErrMsg += cnt+1 + ' 행은 재요청 대상이 아닙니다.\n\r';
            }
            
            var row = {};
            row.itOid = itOid;
            
            reStartIssueTaskList.push(row);
            checkCount++;
            
        }
    });
    
    $("#issueTaskList .issuTaskRow").each(function(i){
        var itOid = $(this).find("input[name='itOid']").val();
        if(itOid == ''){
        	isChange = true;
        }
    });
    
    if(isChange){
    	window.console.log("oldRowCnt : " + oldRowCnt);
    	alert("변경된 데이터가 있으므로 저장 후 진행하시기 바랍니다.");
    	return;
    }
    
    if(ErrMsg != ''){
    	alert(ErrMsg);
    	return;
    }
    
    if(checkCount == 0){
    	alert('선택된 건이 없습니다.');
    	return;
    }
    
    var param = {};
    param.reStartIssueTaskList = JSON.stringify(reStartIssueTaskList);
    
    ajaxCallServer("/plm/ext/issue/reStartIssueTask", param, function(data){
        
        alert('재요청되었습니다.');
        
        location.href = '/plm/ext/issue/viewIssuePopup?oid=' + data.oid;
    });
}

window.isRejectTaskList = function(){
	var isReject = false;
	$("#issueTaskList .issuTaskRow").each(function(i){
		var state = $(this).find(".completeDate").text();
		$(this).find("input[name='rejectCheck']").val("");
		if(state.indexOf('반려') != -1 ){
			
			var curRow = $(this).closest('tr').prevAll().length;
	        var oldData = $(this).find("input[name='changeData']").val();
	        oldData = oldData.replace(/undefined/g,"");
	        $(this).find("input[name='subject']").val()
	        var newData =  $(this).find("input[name='subject']").val() + $(this).find("input[name^=tmUserOid]").val() + $(this).find("input[name^=refUserOids]").val() + $(this).find("input[name^=requestDate]").val();
	        
	        if(oldData != newData){
	            window.console.log("oldData : " + oldData);
	            window.console.log("newData : " + newData + " 그리고 curRow : "+curRow);
	            $(this).find("input[name='rejectCheck']").val("START");
	            isReject = true;
	        }

		}
	});
	return isReject;
}

window.getIssueTaskList = function(){
	
	var issueTaskList = new Array();
	$("#issueTaskList .issuTaskRow").each(function(i){
		
		var tmUserName = "tmUserName"; // + i;
	    var tmUserOid = "tmUserOid"; // + i;
	    
	    var refUserNames = "refUserNames"; // + i;
        var refUserOids = "refUserOids"; // + i;
	    
	    
	    var reqDate = "requestDate"; // + i;
	    
		var row = new Object();
		
		row.itOid = $(this).find("input[name^='itOid']").val();
		row.subject = $(this).find("input[name^='subject']").val();
		row.tmUserName = $(this).find("input[name^='" + tmUserName + "']").val();
		row.tmUserOid = $(this).find("input[name^='" + tmUserOid + "']").val();
		
		row.refUserNames = $(this).find("input[name^='" + refUserNames + "']").val();
        row.refUserOids = $(this).find("input[name^='" + refUserOids + "']").val();
		
		row.requestDate = $(this).find("input[name^='" + reqDate + "']").val();
		row.rejectCheck = $(this).find("input[name^='rejectCheck']").val();
		issueTaskList.push(row);
	});
	
	return issueTaskList;
}

window.setIssuePartList = function(){
    
    var param = new Object();
    param.oid = $("#oid").val();
    
    ajaxCallServer("/plm/ext/issue/getIssuePartList", param, function(data){
        window.console.log(data.partList);
        
        var partList = data.partList;
        
        for(var i = 0; i < partList.length; i++){
        	addPartRow(partList[i]);
        }
    });
}

window.setIssueTaskList = function(){
	
	var param = new Object();
    param.oid = $("#oid").val();
    
    ajaxCallServer("/plm/ext/issue/getIssueTaskList", param, function(data){
    	window.console.log(data.itList);
    	
    	var itList = data.itList;
    	
    	for(var i = 0; i < itList.length; i++){
    		addIssueTaskList(itList[i]);
    	}
    	if(initFlag == 0){
    		var rejectCheckCnt = 0;
            $("#issueTaskList .issuTaskRow").each(function(i){
                console.log('state : init');
                var state = $(this).find(".completeDate").text();
                console.log('state : '+state);
                if(state.indexOf('반려') >= 0 ){
                    
                    rejectCheckCnt++;
                }
            });
            console.log('rejectCheckCnt : '+rejectCheckCnt);
            if(rejectCheckCnt == 0){
                $("#reStart").remove();
            }
    	}
    	
        
        initFlag++;
    });
}

window.saveIssue = function(isReqProgress){
	
	var confirmMsg = "저장하시겠습니까?";
	var completeMsg = "저장되었습니다.";
    if(isReqProgress) {
    	confirmMsg = "진행요청 하시겠습니까?";
    	completeMsg = "진행요청되었습니다.";
    }
    
    var reqCode = $("#reqCode").val();
    
    if(reqCode == 'REQ001'){
        $("input[name=lastCustomer]").data("message", "자동차사를 입력하세요.");
        $("input[name=subContractor]").data("message", "고객사를 입력하세요.");
        $("input[name=oemOid]").data("message", "대표차종을 입력하세요.");
    }else{
    	$("input[name=lastCustomer]").data("message", "");
        $("input[name=subContractor]").data("message", "");
        $("input[name=oemOid]").data("message", "");
    }
    
	if(issue.needCheckAttribute()){
	    
		var detailCode =  $("select[name=detailCode]").val();
	    
	    if(detailCode == 'DTI007' || detailCode == 'DTI008' || detailCode == 'DTI009'){//PPAP(신규) , PPAP(4M), 정기성적서
	        if(getIssuePartList().length < 1){
	            alert("요청품목이 입력되지 않았습니다.");
	            return; 
	        }
	    }
	    if(isRejectTaskList()){
	    	confirmMsg = "반려된 세부진행 항목이 존재합니다.\r\n저장 시 자동으로 재진행요청됩니다.\r\n그래도 저장하시겠습니까?";
	    }
	    if(confirm(confirmMsg)){
	    	var param = $("[name=uploadForm]").serializefiles();
	        param.append("itList", JSON.stringify(getIssueTaskList()));
	        param.append("partList", JSON.stringify(getIssuePartList()));
	        param.append("isReqProgress", isReqProgress);
	        
	        ajaxCallServer("/plm/ext/issue/saveIssueMaster", param, function(data){
	            
	            alert(completeMsg);
	            
	            if(${isModify}){
	                location.href = '/plm/ext/issue/viewIssuePopup?oid=' + data.oid;
	            }else{
	                location.href = '/plm/ext/issue/saveIssuePopup?oid=' + data.oid;
	            }
	        });
        }
		
	}
}
window.deleteIssue = function(){
    
    if(confirm("삭제 하시겠습니까?")){
        
        var param = new Object();
        param.oid = "${imDTO.oid}";

        ajaxCallServer("/plm/ext/issue/deleteIssue", param, function(data){
        	self.close();
        });
    }
}

function insertLastUsingBuyer(arrObj){
    $('[name=lastCustomer]').val(arrObj[0][0]);
    $('[name=lastCustomerName]').val(arrObj[0][2]);
}

window.setCarType = function(returnValue){
    
    $('[name=oemOid]').val(returnValue[0][0])//id
    $('[name=oemName]').val(returnValue[0][1])//name
    
}
</script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${imDTO.oid}"/>
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />고객/사내 요구사항 
            <c:if test="${isModify }">수정</c:if>
            <c:if test="${!isModify }">등록</c:if>
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <c:if test="${imDTO.issueState eq 'IST001'}">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:saveIssue(true);" class="btn_blue">
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
                        <a href="javascript:saveIssue(false);" class="btn_blue">
                        <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%>
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <c:if test="${imDTO.issueState eq 'IST001' }">
                <span class="in-block v-middle r-space7">
                    <span class="pro-table">
                        <span class="pro-cell b-left"></span>
                        <span class="pro-cell b-center">
                            <a href="javascript:deleteIssue();" class="btn_blue">
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
						<td class="tdblueL">
                            요청명<span class="red">*</span>
						</td>
						<c:if test="${!isModify }">
	                        <td class="tdwhiteL" colspan="5">
	                            <input type="text" name="reqName" class="txt_field WP95" value="${imDTO.reqName }">
	                        </td>
                        </c:if>
						<c:if test="${isModify }">
							<td class="tdwhiteL" colspan="3">
	                            <input type="text" name="reqName" class="txt_field WP90" value="${imDTO.reqName }">
	                        </td>
	                        <td class="tdblueL">요청 No</td>
	                        <td class="tdwhiteL">${imDTO.reqNo }</td>
						</c:if>
					</tr>
					<tr>
                        <td class="tdblueL">
                            요청구분<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            <ket:select id="reqCode" name="reqCode" className="fm_jmp" codeType="ISSUEREQ" multiple="multiple" useCodeValue="true" value="${imDTO.reqCode }"  />
                        </td>
                        <td class="tdblueL">
                            상세구분<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            <ket:select id="detailCode" name="detailCode" className="fm_jmp" codeType="" multiple="multiple" useCodeValue="true" value="${imDTO.detailCode }"  style="width:170px" />
                        </td>
                        <td class="tdblueL">
                            등급<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            <ket:select id="rank" name="rank" className="fm_jmp"  codeType="ISSUERANK" multiple="multiple" useCodeValue="true" value="${imDTO.rank }"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            요청자<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="managerName" class="WP70 txt_field" value="${imDTO.managerName }">
                            <input type="hidden" name="managerOid" value="${imDTO.managerOid }"> 
                            <a href="javascript:addUser('managerOid','managerName','setUser');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('managerOid','managerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                            완료 요청일자<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="requestDate" class="W90 txt_field" value="${imDTO.requestDate }">
                            <a href="javascript:CommonUtil.deleteValue('requestDate');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <c:if test="${!isModify }"><td class="tdwhiteL"></td><td class="tdwhiteL"></td></c:if>
                        <c:if test="${isModify }">
                            <td class="tdblueL">
                                진행상태
                            </td>
                            <td class="tdwhiteL">
                                ${imDTO.issueStateName }
                            </td>
                        </c:if>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            Project No
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="pboNo" class='WP70 txt_fieldRO' readonly="readonly" value="${imDTO.pboNo }">
                            <input type="hidden" name="pboOid" value="${imDTO.pboOid }">
                            <!-- <a href="javascript:SearchUtil.selectOneSalesProject('setProjectInfoCall');"> -->
                            <a href="javascript:SearchUtil.selectOneProjectPopUp('setProjectInfoCall','project_type=2');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('pboNo','pboOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </td>
                        <td class="tdblueL">
                            프로젝트 명
                        </td>
                        <td colspan="3" class="tdwhiteL">
                            <input type="hidden" name="pboName" value="${imDTO.pboName }">
                            <span id="pboName">${imDTO.pboName }</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            자동차사<span class="" id="customerSpan"></span>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="lastCustomerName" class='WP70 txt_field' value="${imDTO.lastCustomerName }">
                            <input type="hidden" name="lastCustomer" value="${imDTO.lastCustomer }">
                            <a href="javascript:SearchUtil.selectOneLastUsingBuyerPopup('insertLastUsingBuyer');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('lastCustomer','lastCustomerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                            고객사<span class="" id="subConSpan"></span>
                        </td>
                        <td class="tdwhiteL">
                            <input type="text" name="subContractorName" value="${imDTO.subContractorName }" class='WP70 txt_field'>
                            <input type="hidden" name="subContractor" value="${imDTO.subContractor }">
                            <a href="javascript:SearchUtil.selectMultiSubContractorPopUp('setSubContractor');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('subContractorName','subContractor');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <td class="tdblueL">
                            대표차종<span class="" id="oemSpan"></span>
                        </td>
                        <td class="tdwhiteL">
                          <input type="text" id="oemName" name="oemName"  class='WP70 txt_field' value="${imDTO.oemName }">
                          <input type="hidden" id="oemOid" name="oemOid" value="${imDTO.oemOid }">
                          <a href="javascript:SearchUtil.selectCarType('oemOid','oemName','setCarType');"><img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('oemOid','oemName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            등록자
                        </td>
                        <td class="tdwhiteL">
                            ${creatorName }
                        </td>
                        <td class="tdblueL">
                            등록일자
                        </td>
                        <td class="tdwhiteL" colspan="3">
                            ${createDate }
                        </td>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            요청사항
                        </td>
                        <td class="tdwhiteL" colspan="5" >
                            <textarea name="mainSubject" class='txt_field' style="height:70px;width:100%;">${imDTO.mainSubject }</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv"></div>
                            <script>
                            $("#attachFileDiv").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}");
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="b-space5 float-r" style="text-align: right; margin-top:10px;">
            <c:if test="${isModify }">
<!--             <span class="in-block v-middle r-space7" id='reStart'>
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:reStartIssueTaskList();" class="btn_blue">반려 ▶ 재진행</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span> -->
            </c:if>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:addIssueTaskList();" class="btn_blue">추가</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:delIssueTaskList();" class="btn_blue">삭제</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 float-l b-space5" style="margin-top:10px;">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>세부 진행 항목
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="30" />
                    <col width="200" />
                    <col width="*" />
                    <col width="130" />
                    <col width="150" />
                    <col width="100" />
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
                <tbody id="issueTaskList">
                </tbody>
            </table>
        </div>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr><td class="space20"></td></tr>
        </table>
        <div class="b-space5 float-r" style="text-align: right; margin-top:10px;">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:addIssuePartList();" class="btn_blue">추가</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:delIssuePartList();" class="btn_blue">삭제</a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 float-l b-space5" style="margin-top:10px;">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>요청품목
        </div>
        <div class="b-space5" >
            <table summary="" class="table-info fixed">
                <colgroup>
                    <col width="30" />
                    <col width="200" />
                    <col width="*" />
                </colgroup>
                <thead>
                    <tr>
                        <td><input type='checkbox' name='selectPartAll' id="selectPartAll"/></td>
                        <td>품번</td>
                        <td>자재명</td>
                    </tr>
                </thead>
                <tbody id="issuePartList">
                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
