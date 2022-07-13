<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<style>
</style>
<script type="text/javascript">
<!--
	$(document).ready(function() {
		
		$(document).bind('keypress', function(e) {
            if (e.which == 13) {
                search();
                event.preventDefault();
            }
        });
		
		$(document).attr('title', "잔여업무조회");
		SuggestUtil.bind('USER_ALL', 'userName', 'userOid');
		SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
		CalendarUtil.dateInputFormat('createDateFrom', 'createDateTo');
		
		$("#retireCheck").change(function(){

	        if($("#retireCheck").is(":checked")){

	        	$("#retireDiv").show();

	        }else{

	        	$("#retireDiv").hide();

	        }

	    });
		
	});
//-->

function search(){
	
/* 	    if (!CommonUtil.checkEsseOk()) {
			return;
		} */
	    
		if($("#retireCheck").is(":checked")){
			if($("input[name=createDateFrom]").val() == '' || $("input[name=createDateTo]").val() == ''){
				alert("퇴사기간을 설정하여 주십시오.");
				return;	
			}
		}else{
			if($("#deptCode").val() == '' && $("#userOid").val() == ''){
				alert("부서 또는 사용자를 입력하십시오");
				return;
			}
		}
		
		
		
		
		$("#targetTable tr:not(:first)").remove();

		//var param = $("input[name=userOid]").val();
		var param = $("[name=uploadForm]").serialize();
		
		ajaxCallServer("/plm/ext/wfm/workspace/listTotalWorkData.do", param, function(data) {
			
			var totalList = data.totalList;
			
			$.each(totalList, function(i) {
                
				addContent = "<tr>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].deptName + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].userName + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].workitem + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].todo + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].task + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].project + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].doc + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].issue + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].epm + "</td>";
	            //addContent += "<td class=tdwhiteM>" + data.part + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].ecm + "</td>";
	            addContent += "<td class=tdwhiteM>" + totalList[i].retire + "</td>";
	            addContent += "</tr>";

	            $('#targetTable > tbody:last').append(addContent);
                
            });
			
			
		});
}

function excelDownLoad(){
	
	var param = $("[name=uploadForm]").serialize();
    
    ajaxCallServer("/plm/ext/wfm/workspace/createWorkListExcel.do", param, function(data){
        location.href = data.downloadLink;
    });
}

function addDepartment(codeTarget, nameTarget) {
    
    window.deptTagetName = nameTarget;
    window.deptTagetCode = codeTarget;
    
    var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?type=all&mode=s&invokeMethod=deptCallBack";
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'AddProjectDeptFrm', 430, 465, opts);
    
/*     var rsltArrayObject = window.showModalDialog(url, "addDepartmentAfterFunc", "resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
    
    
    for(var i= 0 ; i < rsltArrayObject.length; i++){
        $("#deptCode").val( rsltArrayObject[i].OID);
        $("#deptName").val( rsltArrayObject[i].NAME);
    } */
    
}

function deptCallBack(rsltArrayObject){
	for(var i= 0 ; i < rsltArrayObject.length; i++){
        $("#deptCode").val( rsltArrayObject[i].OID);
        $("#deptName").val( rsltArrayObject[i].NAME);
    }
}

function delDepartment(targetId, targetName) {
    $("#" + targetId).val("");
    $("#" + targetName).val("");
}

function addUser(){
	var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=setUser1";

	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'AddProjectPeopleFrm', 800, 600, opts);
}

window.setUser1 = function(returnValue){
    var infoArr = returnValue[0];
    $('[name=userOid]').val(infoArr[0]);
    $('[name=userName]').val(infoArr[4]);
    
}


</script>
<form name="uploadForm" method="post" >
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
	<div class="sub-title-02 b-space15 clear">
	    <div class="float-r" style="text-align: right">
	       부서 : 
	       <span class="in-block v-middle">
	       <input style="width: 70%;" type="text" id="deptName" name="deptName" class="txt_field">
                    <input type=hidden id="deptCode" name="deptCode">
                    <a href="javascript:addDepartment('deptCode', 'deptName');">
                        <img src="/plm/portal/images/icon_5.png">
                    </a>
                    <a href="javascript:delDepartment('deptCode','deptName');">
                    <img src="/plm/portal/images/icon_delete.gif">
                    </a>
           </span>
	       <span class="r-space20"></span>
	       대상자 :
	       <span class="in-block v-middle">
	       <input type="text" name="userName" class="txt_field" style="width: 70%" esse='true' esseLabel='대상자'>
                            <input type="hidden" name="userOid" id="userOid"> 
                            <a href="javascript:addUser();">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('userOid','userName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	       </span>
	       <span class="in-block v-middle">
	           <span class="pro-table">
	               <span class="pro-cell b-left"></span>
	               <span class="pro-cell b-center"><a href="javascript:search();" class="btn_blue">조회</a></span>
	               <span class="pro-cell b-right"></span>
               </span>
           </span>
        </div>
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>잔여업무현황
     </div>
     <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td align="right">
                    	<span id="retireDiv" style="display:none">
	                              퇴사기간 : <input type="text" name="createDateFrom" class="txt_field" style="width: 80px;" value="">
	                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateFrom');" style="cursor: hand;"> ~ 
	                    <input type="text" name="createDateTo" class="txt_field" style="width: 80px;">
	                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createDateTo');" style="cursor: hand;">
	                    </span>
	                              퇴사자만 검색하려면 체크하십시오 :<input type="checkBox"id="retireCheck" name="retireCheck" />
                    </td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td class="space5"></td>
				</tr>
				<tr>
					<td align="right">
					<img src="/plm/portal/images/iocn_excel.png" onclick="excelDownLoad();" style="cursor: pointer;" />
				    </td>
				</tr>
				<tr>
					<td class="space5"></td>
				</tr>
				<tr>
					<td class="tab_btm2"></td>
				</tr>
			</table>

			<table id="targetTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;" border="1">
                <colgroup>
                    <col width="18%" />
                    <col width="10%" />
                    <col width="8%" />
                    <col width="8%" />
                    <col width="8%" />
                    <col width="8%" />
                    <col width="8%" />
                    <col width="8%" />
                    <col width="8%" />
                    <col width="8%" />
                    <col width="8%" />
                </colgroup>
                <tbody>
                <tr>
                    <td class="tdblueM">부서</td>
                    <td class="tdblueM">이름</td>
                    <td class="tdblueM">결재대기함</td>
                    <td class="tdblueM">TO-DO</td>
                    <td class="tdblueM">Task</td>
                    <td class="tdblueM">Project</td>
                    <td class="tdblueM">Document</td>
                    <td class="tdblueM">CFT요청</td>
                    <td class="tdblueM">도면</td>
                    <td class="tdblueM">ECM</td>
                    <td class="tdblueM">퇴사여부</td>
                </tr>
                </tbody>
            </table>
    </div>  
    <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe> 
    </table>
</form>