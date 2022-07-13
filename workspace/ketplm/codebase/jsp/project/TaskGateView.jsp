<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="wt.pds.StatementSpec"%>
<%@page import="wt.query.SQLFunction"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>

<%@page import="ext.ket.project.task.entity.*"%>
<%@page import="ext.ket.project.task.service.*"%>
<%@page import="ext.ket.project.gate.entity.*"%>
<%@page import="ext.ket.dqm.entity.*"%>
<%@page import="wt.query.*"%>
<%@page import="wt.pds.*"%>
<%@page import="e3ps.common.code.*"%>

<%@page import="java.util.*, wt.fc.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
boolean trust_isbizAdmin = CommonUtil.isMember("Business Administrators");
boolean trust_isAdmin = CommonUtil.isAdmin() || trust_isbizAdmin;                      //PLM Admin
boolean trust_isPmo = CommonUtil.isMember("자동차PMO");
boolean trust_isPmo2 = CommonUtil.isMember("전자PMO");
boolean trust_isPmo3 = CommonUtil.isMember("KETS_PMO");
// boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
// boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin
boolean trust_isPM =false;                      //isPM

WTUser wtuser3 = (WTUser)SessionHelper.manager.getPrincipal();
//사용자가 Task의 책임자인지 확인
boolean trust_isTaskMaster = false;
boolean isProtoGate1Check = false;
boolean isProtoGate1Pass = true;
String ProtoGate1Result = "완료";
String ProtoGateTaskOid = "";
String protoProjectNo = "";
String proto_resultTotalStr = "";
String taskCategoryType = "";
  try{
    
  String oid =  request.getParameter("oid");
  String oldOid =  request.getParameter("compareTaskOid");
  

  E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
  String ppopProjectOid = CommonUtil.getOIDLongValue2Str(task.getProject());
  taskCategoryType = task.getTaskTypeCategory();
  //out.println(project.getPjtType());
  E3PSProject project2 = (E3PSProject)task.getProject();
  
  protoProjectNo = project2.getMaster().getLinkProjectNo();
  if("Gate".equals(task.getTaskType()) && "2".equals(task.getTaskTypeCategory()) && !StringUtil.isEmpty(protoProjectNo) && (project2.getProcess().getCode().equals("PC002") || project2.getProcess().getCode().equals("PC005"))){
      E3PSProject protoPjt = ProjectHelper.getProject(protoProjectNo);
      if(protoPjt != null){
          isProtoGate1Check = true;
          QueryResult rs = ProjectTaskHelper.manager.getTaskWithType(protoPjt, "Gate", "1");
          E3PSTask protoGate1 = null;
          
          while (rs.hasMoreElements()) {
                Object[] o = (Object[]) rs.nextElement();
                protoGate1 = (E3PSTask) o[0];
                ProtoGateTaskOid = CommonUtil.getOIDString(protoGate1);
                isProtoGate1Pass = protoGate1.getTaskState() == 5;
          }
          
          if(!isProtoGate1Pass){
              ProtoGate1Result = "진행중";
          }
          
          if(protoGate1 == null){
              isProtoGate1Pass = false;
              ProtoGate1Result = "미진행";
          }
          
          if("완료".equals(ProtoGate1Result)){
              String protoGate1TaskOid = CommonUtil.getOIDString(protoGate1);
              int proto_gate1_rev = ProjectTaskCompHelper.service.getMaxGateDegree(protoGate1TaskOid); 
              Hashtable protoGate1ResultTotalHash = new Hashtable();
              protoGate1ResultTotalHash = ProjectTaskCompHelper.service.getGateAssessResultInfoList(protoGate1TaskOid, true, proto_gate1_rev);
              if(protoGate1ResultTotalHash!=null) {
        	      proto_resultTotalStr = StringUtil.checkNull((String)protoGate1ResultTotalHash.get("resultTotalStr")) ;
              }
          }
          
      }
  }
  
  trust_isPM = ProjectUserHelper.manager.isPM(project2);                      //isPM
  
  QueryResult masterList2 = TaskUserHelper.manager.getMaster(task);
  //Kogger.debug(">>>>>> e333333333 : " + masterList.size());
  boolean trust_isRole = (task.getPersonRole() != null && task.getPersonRole().length() > 0);
  if(masterList2 != null) {
      while ( masterList2.hasMoreElements() ) {
          Object o[]=(Object[])masterList2.nextElement();
          TaskMemberLink link = (TaskMemberLink)o[0];
          PeopleData data = new PeopleData(link.getMember().getMember());
          
            if(wtuser3.equals(data.wtuser)) {
             trust_isTaskMaster = true;
            }
      }
  }

  //사용자가 Task의 참여자인지 확인
  boolean trust_isTaskMember =false;
  QueryResult memberlist3 = TaskUserHelper.manager.getMember(task);
  if(memberlist3 != null) {
      while ( memberlist3.hasMoreElements() ) {
          Object o[]=(Object[])memberlist3.nextElement();
          TaskMemberLink link = (TaskMemberLink)o[0];
          PeopleData data = new PeopleData(link.getMember().getMember());
          if(wtuser3.equals(data.wtuser)) {
              trust_isTaskMember = true;
          }
      }
  }
  
  // 제품측정현황 정보
  String checkDescPoint = task.getCheckDescPoint();
  String nonPassPoint = task.getNonPassPoint();
  String checkResult = task.getCheckResult();
  String checkEtc = task.getCheckEtc();

  if(checkDescPoint == null){
    checkDescPoint = "";
  }

  if(nonPassPoint == null){
    nonPassPoint = "";
  }

  if(checkResult == null){
    checkResult = "";
  }

  if(checkEtc == null){
    checkEtc = "";
  }

  boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
  boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin

  // 진행현황 % 입력
  TaskViewButtonAuth buttonAuth = new TaskViewButtonAuth(task);

  
  //이슈 삭제
  String deleteIssue = request.getParameter("deleteIssue");
 if ( deleteIssue != null ) {
   ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
 }

  //팝업 여부
  String popup = request.getParameter("popup");
  
  String pjtType2 = "";
  if(project2 != null){
    pjtType2 = "" + project2.getPjtType();
  }

  /*
    ####################  권한 설정  ####################
  */

  boolean isEdit2 = false;
  boolean isElectron2 = false;
  E3PSProjectData projectData2 = new E3PSProjectData(project2);
  if(projectData2.teamType.equals("전자 사업부") || projectData2.teamType.equals("전자사업부")){
    isElectron2 = true;
  }
  WTUser wtuser2 = (WTUser)SessionHelper.manager.getPrincipal();

  //Task 책임자  설정
  if(buttonAuth != null){
    QueryResult masterList = TaskUserHelper.manager.getMaster(task);
    while ( masterList.hasMoreElements() ) {
      Object o[]=(Object[])masterList.nextElement();
      TaskMemberLink link = (TaskMemberLink)o[0];
      PeopleData data = new PeopleData(link.getMember().getMember());
      
      if(wtuser2.getName().equals(data.id)){
        isEdit2 = true;
      }
    }
  }
  if(buttonAuth.isLatest && (CommonUtil.isAdmin() || buttonAuth.isPM || CommonUtil.isBizAdmin()) ){
    isEdit2 = true;
  }


  E3PSTaskData taskData2 = new E3PSTaskData(task);
  
  int gate_rev = ProjectTaskCompHelper.service.getMaxGateDegree(oid);
 
  int gateDegree = gate_rev;
  
  
  if(!StringUtils.stripToEmpty(request.getParameter("gateDegree")).equals("")){
      gateDegree = Integer.parseInt(request.getParameter("gateDegree"));
  }
  
  String DR_PASS_CHECK  = ""; //TaskViewCommon.jsp에서 변수로 사용(gate는 의미없음 dr불합격판정용)
  
%>
<html>
<head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<title></title>
<style type="text/css">
<!--
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<script type="text/javascript">
function goProtoTaskView(){
	var taskOid = '<%=ProtoGateTaskOid%>'; 
	var pjtNo = '<%=protoProjectNo%>';

    if(taskOid){
        openView(taskOid);
    }else{
        alert('Gate1이 존재하지 않습니다.');
        openProject(pjtNo);
    }
}
function linkStandardForm(docOid){
    var url = "/plm/jsp/dms/ViewStandardDoc.jsp?oid="+docOid;
    openOtherName(url,"window","850","650","status=no,scrollbars=no,resizable=no");
}

function deleteIssue(v) {
  if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
    document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?deleteIssue="+v;
    document.forms[0].method = "post";
    document.forms[0].submit();
  }
}

function gateResultAttatch() {
	
    var url = "/plm/ext/project/task/updateGateResultAttatchForm.do?oid=<%=oid%>";
    openOtherName(url,"GateResultAttatchPopup","750","550","status=no,scrollbars=auto,resizable=no");
}


function approvalAction(oid){
	
	var rtnBool = valideCheckResult();
	
	if(rtnBool==true) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07329") %><%--미점검 항목이 있습니다. 확인바랍니다--%>');
        //parent.goReloadPage();
        document.forms[0].method = "post";
        document.forms[0].submit();
        return;
	}
		
		
    if(oid=='' || oid == 'null') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07142") %><%--저장 후 결재요청바랍니다.--%>');
        return;
    }
    
    var resultTotalStr =$('input[name=resultTotalStr]').val();
    var passComment = $('input[name=passComment]').val();
    
    if(resultTotalStr == 'Y' && passComment == ''){
        alert('결과가 Yellow (조건부합격) 인 경우 조건부합격근거를 입력해주시기 바랍니다.');
        gateResultAttatch();
        return;
    }
    
    var isProtoGate1Check = <%=isProtoGate1Check%>;
    var isProtoGate1Pass = <%=isProtoGate1Pass%>;
    var protoGate1Check = "";
    if(isProtoGate1Check && !isProtoGate1Pass){
    	protoGate1Check = "OK";
        /* alert("Proto Gate1이 완료되지 않았습니다.\r\n선 종결 후 진행하시기 바랍니다.");
        return; */
    }
<%--     if( document.forms[0].resultTotalStr.value=='R') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07143") %>결과가 Red인 경우 Recovery Plan을 등록 하십시요');
        return;
    } --%>

    var url="/plm/jsp/wfm/RequestApproval.jsp?pboOid="+oid+"&popup=popup&mode=goTaskPage&protoGate1Check="+protoGate1Check;
    
    openOtherName(url,"approval","800","700","status=no,scrollbars=yes,resizable=no");
}

function viewDqmPopup(oid){
    getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=view&oid='+oid,oid,1100,768);
}

function ppopOpenPopup(){
	getOpenWindow2('/plm/ext/dms/pPapDocSearchView.do?pjtOid=<%=ppopProjectOid%>','<%=ppopProjectOid%>',1710,768);
}


function fn_changeCombo(idx) {
	//var tdComboObj = document.getElementByName("tdCombo");
    var tgTaskChecks = document.getElementsByName("tgTaskChecks");
	var bgColor = "";
	//alert(idx+','+tgTaskChecks.length);
	if(tgTaskChecks.length>0) {
		for(var i=0;i<tgTaskChecks.length;i++) {
			if(i==idx) {
				//alert(tgTaskChecks[i].value);
				if(tgTaskChecks[i].value=='G') bgColor = "green";
				else if(tgTaskChecks[i].value=='Y') bgColor = "yellow";
                else if(tgTaskChecks[i].value=='R') bgColor = "red";
		        tgTaskChecks[i].style.backgroundColor = bgColor;
		        
                var tdComboObj = document.getElementById("tdCombo"+i);
                tdComboObj.style.backgroundColor = bgColor;
			}
		}
	}else if(tgTaskChecks.length==1) {
        if(tgTaskChecks.value=='G') bgColor = "green";
        else if(tgTaskChecks.value=='Y') bgColor = "yellow";
        else if(tgTaskChecks.value=='R') bgColor = "red";
        //tdComboObj.style.backgroundColor = bgColor;
        tgTaskChecks.style.backgroundColor = bgColor;
        
        var tdComboObj = document.getElementById("tdCombo0");
        tdComboObj.style.backgroundColor = bgColor;
	}
	
	/*
	var selectedOption = select.options[select.selectedIndex];
	
	if($('#tgTaskChecks').val()=='G') {
	    $('#tdCombo').attr('style', 'background-color:green');
	    $('#tgTaskChecks').attr('style', 'background-color:green');
	}else if($('#tgTaskChecks').val()=='Y') {
	    $('#tdCombo').attr('style', 'background-color:yellow');
	    $('#tgTaskChecks').attr('style', 'background-color:yellow');
    }else if($('#tgTaskChecks').val()=='R') {
        $('#tdCombo').attr('style', 'background-color:red');
        $('#tgTaskChecks').attr('style', 'background-color:red');
    }
*/	
}

function fn_changeCombo2(idx) {
    //var tdComboObj = document.getElementByName("tdCombo");
    var tgTaskChecks = document.getElementsByName("tgGateChecks");
    
    var bgColor = "";
    //alert(idx+','+tgTaskChecks.length);
    if(tgTaskChecks.length>0) {
        for(var i=0;i<tgTaskChecks.length;i++) {
            if(i==idx) {
                //alert(tgTaskChecks[i].value);
                if(tgTaskChecks[i].value=='G') bgColor = "green";
                else if(tgTaskChecks[i].value=='Y') bgColor = "yellow";
                else if(tgTaskChecks[i].value=='R') bgColor = "red";
                tgTaskChecks[i].style.backgroundColor = bgColor;
                
                var tdComboCheck = document.getElementById("tdComboCheck"+i);
                tdComboCheck.style.backgroundColor = bgColor;
            }
        }
    }else if(tgTaskChecks.length==1) {
        if(tgTaskChecks.value=='G') bgColor = "green";
        else if(tgTaskChecks.value=='Y') bgColor = "yellow";
        else if(tgTaskChecks.value=='R') bgColor = "red";
        //tdComboObj.style.backgroundColor = bgColor;
        tgTaskChecks.style.backgroundColor = bgColor;
        
        var tdComboCheck = document.getElementById("tdComboCheck0");
        tdComboCheck.style.backgroundColor = bgColor;
    }
    
}

function fn_changeCombo3(idx) {
    var tgTaskChecks = document.getElementsByName("tgDqmHeaderChecks");
    var bgColor = "";
    //alert(idx+','+tgTaskChecks.length);
    if(tgTaskChecks.length>0) {
        for(var i=0;i<tgTaskChecks.length;i++) {
            if(i==idx) {
                //alert(tgTaskChecks[i].value);
                if(tgTaskChecks[i].value=='G') bgColor = "green";
                else if(tgTaskChecks[i].value=='Y') bgColor = "yellow";
                else if(tgTaskChecks[i].value=='R') bgColor = "red";
                tgTaskChecks[i].style.backgroundColor = bgColor;
                
                var tdComboDqm = document.getElementById("tdComboDqm"+i);
                tdComboDqm.style.backgroundColor = bgColor;
            }
        }
    }else if(tgTaskChecks.length==1) {
        if(tgTaskChecks.value=='G') bgColor = "green";
        else if(tgTaskChecks.value=='Y') bgColor = "yellow";
        else if(tgTaskChecks.value=='R') bgColor = "red";
        //tdComboObj.style.backgroundColor = bgColor;
        tgTaskChecks.style.backgroundColor = bgColor;
        
        var tdComboDqm = document.getElementById("tdComboDqm0");
        tdComboDqm.style.backgroundColor = bgColor;
    }
    
}

function execStartDateCheck() {

	var execStartDtVal = document.getElementById("taskExecStartDate").value;
    if(execStartDtVal=='') {
        if(checkStartEndDate()){

            alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
            document.forms[0].taskExecStartDate.focus();
            //$('form:first *:input[type!=hidden]:first').focus();
            $('#taskExecStartDate').focus();
            return;
          }

    }
}

function updateGateTarget(outputOid) {
    
    //alert(outputOid);
    //alert('checked:'+cb.checked+',value:'+cb.value+',output:'+outputOid);
    var gCheckList = document.getElementsByName('gatechecks');
    
    $.ajax( {
        url : "/plm/ext/project/task/deleteGateProjectOutputLink.do?outputOid="+outputOid,   //
        type : "POST",
        data : "", //$('[name=TaskViewForm]').serialize(),
        async : false,
        success: function(data) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다.--%>');
        },
        fail : function(){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다.--%>');
        }
    });
  }
  


function valideCheckResult() {
// 	alert('1');
	var tgTaskCommentsAll = document.getElementsByName('tgTaskComments');
 	var tgTaskChecksAll = document.getElementsByName('tgTaskChecks');

// 	var tgGateResultsAll = document.getElementsByName('tgGateResults');
 	var tgGateChecksAll = document.getElementsByName('tgGateChecks');

 	var tgDqmHeaderChecksAll = document.getElementsByName('tgDqmHeaderChecks');

	var checkBool = false;
	for ( var i = 0; i < tgTaskChecksAll.length; i++) {
		var checkVal = $('[name=tgTaskChecks]')[i].value.trim();
// 		alert('checkVal:'+checkVal);
		if(checkVal=='') checkBool=true;
	}
	
    for ( var i = 0; i < tgGateChecksAll.length; i++) {
        var checkVal2 = $('[name=tgGateChecks]')[i].value.trim();
//         alert('checkVal2:'+checkVal2);
        if(checkVal2=='') checkBool=true;
    }
	
    for ( var i = 0; i < tgDqmHeaderChecksAll.length; i++) {
        var checkVal3 = $('[name=tgDqmHeaderChecks]')[i].value.trim();
//         alert('checkVal3:'+checkVal3);
        if(checkVal3=='') checkBool=true;

    }
    
    //alert($('[name=checkValue1]').val());
    //산출물항목중 DB값이 빈값이 있을 경우
    if($('[name=checkValue1]').val()=='N') {
    	checkBool=true;
    }
    //평가항목중 DB값이 빈값이 있을 경우
    if($('[name=checkValue2]').val()=='N') {
        checkBool=true;
    }
    //품질항목중 DB값이 빈값이 있을 경우
    if($('[name=checkValue3]').val()=='N') {
        checkBool=true;
    }
    
    return checkBool;
}

//Gate 태스크에 붙은 항목들 모두 초기화
function resetOutputResult() {
	
	
    var outputLinkDiv = '';
    var gateLinkDiv = '';
    var dqmLinkDiv = '';
    var outputLinkOids = document.getElementsByName("outputLinkOids");
    var gateLinkOids = document.getElementsByName("gateLinkOids");
    var dqmLinkOids = document.getElementsByName("dqmLinkOids");
    
//     alert('outputLinkOids:'+outputLinkOids.length);
//     alert('gateLinkOids:'+gateLinkOids.length);
//     alert('dqmLinkOids:'+dqmLinkOids.length);
    if(outputLinkOids.length>0) {
        for(var i=0;i<outputLinkOids.length;i++) {
        	if(i==0) outputLinkDiv = outputLinkOids[i].value;
        	else outputLinkDiv += ","+outputLinkOids[i].value;
        }
//         alert(outputLinkDiv);
    }
    if(gateLinkOids.length>0) {
        for(var i=0;i<gateLinkOids.length;i++) {
            if(i==0) gateLinkDiv = gateLinkOids[i].value;
            else gateLinkDiv += ","+gateLinkOids[i].value;
        }
//         alert(gateLinkDiv);
    }
    if(dqmLinkOids.length>0) {
        for(var i=0;i<dqmLinkOids.length;i++) {
            if(i==0) dqmLinkDiv = dqmLinkOids[i].value;
            else dqmLinkDiv += ","+dqmLinkOids[i].value;
        }
//         alert(dqmLinkDiv);
    }

    $.ajax( {
        url : "/plm/ext/project/task/initiateGateResultLink.do?outputLinkDiv="+outputLinkDiv+"&gateLinkDiv="+gateLinkDiv+"&dqmLinkDiv="+dqmLinkDiv,   //
        type : "POST",
        data : "", //$('[name=TaskViewForm]').serialize(),
        async : false,
        success: function(data) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다.--%>');
            document.forms[0].method = "post";
            document.forms[0].submit();
        },
        fail : function(){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다.--%>');
            document.forms[0].method = "post";
            document.forms[0].submit();
        }
    });
}

function changeGateView(){
	showProcessing();
	var gateDegree = document.forms[0].gateDegree.value;
	
	var $tabs = $('#tabs').tabs();
    //get selected tab
    function getSelectedTabIndex() {
        return $tabs.tabs('option', 'active');
    }
    var url = "/plm/jsp/project/TaskGateView.jsp?oid=<%=oid%>&tabIndex="+getSelectedTabIndex()+"&popup=popup&gateDegree="+gateDegree;
    
    document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?oid=<%=oid%>&tabIndex="+getSelectedTabIndex()+"&popup=popup&gateDegree="+gateDegree;
    document.forms[0].method = "post";
    document.forms[0].submit();
    
}

function gateViewHistory(pboOid){
	var addr = "/plm/jsp/wfm/ApprovalHistory.jsp?pboOid=" + pboOid+"&gateDegree="+document.forms[0].gateDegree.value;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    var topener = getOpenWindow2(addr, "history", 1024, 800, opts);
    // var topener = window.open(addr, "history" ,"toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=1024,height=800");
    topener.focus();
}

function deleteDeptValue(targetId, targetClass) {
	$("." + targetClass).val('');
    $("#" + targetId).val('');
}

function gateAddDepartment(targetId, targetClass) {
	var execStartDtVal = document.getElementById("taskExecStartDate").value;
    if(execStartDtVal=='') {
        if(checkStartEndDate()){

            alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
            document.forms[0].taskExecStartDate.focus();
            //$('form:first *:input[type!=hidden]:first').focus();
            $('#taskExecStartDate').focus();
            return;
          }

    }
    
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
    
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

     if ( typeof(attacheDept) != "object" ) {
        var deptSplit = attacheDept.split(",");
        
        for ( var i = 0; i<deptSplit.length-1; i++ ) {
            var oid = deptSplit[i];
            var param = { oid : oid };
            var data = ajaxCallServer("/plm/ext/common/getObjectData", param, null, false);
            deptMerge(data.name, oid, targetId, targetClass);
        }
    } else {
        var oid = attacheDept[0][0];
        var param = { oid : oid };
        var data = ajaxCallServer("/plm/ext/common/getObjectData", param, null, false);
        deptMerge(data.name, oid, targetId, targetClass);
    }
}

function deptMerge(deptName, deptOid, targetId, targetClass) {
    window.console.log(deptOid);
    if ( $("." + targetClass).val() == "" ) {
        $("." + targetClass).val( deptOid );
        $("#" + targetId).val( deptName );
    }
    else {
        var deptOidArr = $("." + targetClass).val().split(", ");
        var deptNameArr = $("#" + targetId).val().split(", ");
        // 선택된 부서 push
        deptOidArr.push(deptOid);
        deptNameArr.push(deptName);
        // 중복 부서 정리
        deptOidArr = $.unique(deptOidArr.sort());
        deptNameArr = $.unique(deptNameArr.sort());
        
        $("#" + targetId).val( deptNameArr.join(", ") );
        $("." + targetClass).val( deptOidArr.join(", ") );
    }
}
</script>
</head>
<body style="padding-right:15px;scrollbar-base-color: #ccc;scrollbar-arrow-color: #ccc;scrollbar-face-color:#ccc;">

<form name="gateForm">
<!-- Hidden Value -->
<input type=hidden name=taskOid value='<%=oid%>'>
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=cmd >
<input type=hidden name=issueOid>
<input type=hidden name=popup value='<%=popup%>'>
<input type=hidden name=ExeDate>
<!-- //Hidden Value -->

<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top">
    <!-------------------------------------- 상단버튼 시작 //-------------------------------------->
       <table width="100%"  border="0" cellspacing="0" cellpadding="0" style="margin-left:10">
         <tr>
          <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02927") %><%--태스크 정보--%></td>
                      <td align="right"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
          <!-- -------------------- 공통 파일 START------------------- -->
          <%@include file="/jsp/project/TaskViewCommon.jsp"%>
          <!-- -------------------- 공통 파일 END------------------- -->
          
      <!------------------------------ 본문 끝 //------------------------------>


            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="space20"></td>
              </tr>
            </table>
<%
    List<String> gateTotalOutputSum = new ArrayList<String>();
    Hashtable gateResultTotalHash = new Hashtable(); 
    String resultOutputVal = "";
    String resultAssVal = "";
    String resultDqmVal = "";
    String resultTotalVal = "";
    String resultTotalStr = "";
    String resultState = "";
    String resultStateName = "";
    String resultTemplateUrl = "";
    String gateResultOid = "";
    String disabledVal = "";
    GateAssessResult gateAssResult = null;
    
    String recoveryFileCnt = "";
    oid = request.getParameter("oid");
    String passComment = "";
    try {

        //평가결과(모든 결과를 Hashtable에 담는다)
        gateResultTotalHash = ProjectTaskCompHelper.service.getGateAssessResultInfoList(oid, true, gateDegree);
        if(gateResultTotalHash!=null) {
            
             resultOutputVal = StringUtil.checkNull((String)gateResultTotalHash.get("resultOutputVal")) ;
             resultAssVal = StringUtil.checkNull((String)gateResultTotalHash.get("resultAssVal")) ; 
             resultDqmVal = StringUtil.checkNull((String)gateResultTotalHash.get("resultDqmVal")) ;
             resultTotalVal = StringUtil.checkNull((String)gateResultTotalHash.get("resultTotalVal")) ;
             resultTotalStr = StringUtil.checkNull((String)gateResultTotalHash.get("resultTotalStr")) ;
             resultState = StringUtil.checkNull((String)gateResultTotalHash.get("resultState")) ;
             resultStateName = StringUtil.checkNull((String)gateResultTotalHash.get("resultStateName")) ;
             resultTemplateUrl = (String)gateResultTotalHash.get("resultTemplateUrl") ;
             gateResultOid =  "ext.ket.project.gate.entity.GateAssessResult:" + (String)gateResultTotalHash.get("gateResultOid") ;
        }
        
        
        gateAssResult = ProjectTaskCompHelper.service.getGateAssessResultObj(oid);
        
        if (gateAssResult != null) {
            passComment = StringUtil.checkNull(gateAssResult.getPassComment());
            ArrayList<ContentDTO> secondArrList =  ext.ket.shared.content.service.KETContentHelper.manager.getSecondaryContents(gateAssResult);
            if(secondArrList!=null) {
                recoveryFileCnt = ""+secondArrList.size();
            }
        }
    }catch(Exception e) {
	Kogger.error(e);
    }
    
    if(gateAssResult!=null)
    {
       if(gate_rev != gateDegree){
           resultStateName = "승인완료";
           resultState = "APPROVED";
       }
        
    }
    if((!"".equals(resultState) && !"INWORK".equals(resultState) && !"REWORK".equals(resultState))) {
	   disabledVal = "disabled";
    }
    
    
%>
            <input type="hidden" name="resultTotalStr" value="<%=resultTotalStr%>"/>
            <input type="hidden" name="passComment" value="<%=passComment%>"/>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07141") %><%--평가결과--%></td>
                <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
                    <tr>
                      <td width="80" align="right"> 차수 : &nbsp; </td>
                      <td width="120">
                      
                      <select name="gateDegree" class="fm_jmp" style="width: 80" onchange='javaScript:changeGateView()'>
                      <%for(int i=gate_rev; i>0; i--) {
                	     String selected = "";
                	     if(i == gateDegree){
                		    selected = "selected";
                	     }
                      %>
                        <option value="<%=i%>" <%=selected%>><%=i%>&nbsp;차</option>
                      <%} %>
                      </select>
                      </td>
                      <td width="80" align="right"> <%=messageService.getString("e3ps.message.ket_message", "01760") %><%-- 상태 --%> : </td>
                      <td width="120"> &nbsp;
<%
    if(gateAssResult!=null)
    {
%>
                    <a href="javascript:gateViewHistory('<%=(CommonUtil.getOIDString(gateAssResult))%>')"><%=resultStateName %></a>
<%
    }else {
%>
                    <%=resultStateName %> 
<%
    }
%>
                      
                      </td>
<%
        ProjectOutput output2 = null;
        ProjectOutputData outputData2 = null;
        Object[] opObj = null;
        QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);
        KETStandardTemplate latestInfo = null;
        while (outputQr.hasMoreElements()) {
          opObj = (Object[]) outputQr.nextElement();
          output2 = (ProjectOutput) opObj[0];
          //outputUser = output.getOwner() == null ? null : (WTUser) output.getOwner().getPrincipal();
          outputData2 = new ProjectOutputData(output2);
          /* ContentHolder holder = (ContentHolder) output;
          Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
          int size = secondaryFiles.size(); */
          
          
          KETStandardTemplate ketStandardTemplate = (KETStandardTemplate)CommonUtil.getObject(output2.getOutputDocOid());
          
          if(ketStandardTemplate != null){
          RevisionControlled latestVersion = e3ps.common.obj.ObjectUtil.getLatestVersion(ketStandardTemplate);
          
              latestInfo = (KETStandardTemplate) latestVersion;
          }else{
              latestInfo = null;
          }
        }
        
        String templateTitle = "";
        String latestInfoTitle = (latestInfo != null) ? latestInfo.getTitle() : "";
        latestInfoTitle = org.apache.commons.lang.StringEscapeUtils.escapeHtml(latestInfoTitle);
%>
                      <td width="80" align="right"> Template : </td>
                      <td width="100" title="<%=latestInfoTitle %>"><div class="ellipsis" style="width:100px;">
	                      <a href="JavaScript:linkStandardForm('<%=latestInfo == null ? "" : latestInfo.toString()%>')"><%=latestInfo == null ? "" : latestInfoTitle %></a>
	                      </div>
                      </td>
                      <td width="5"> 
                      </td>
<%
String curStateStr = project2.getLifeCycleState().getDisplay(); //String curState = project.getLifeCycleState().toString();

    if("".equals(StringUtil.checkNull(resultState)) || "INWORK".equals(resultState) || "REWORK".equals(resultState)) {
		if(gateAssResult!=null && "진행".equals(curStateStr)) {
%>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:approvalAction('<%=gateResultOid %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
<%

		}
    }
    if("APPROVED".equals(resultState) && gate_rev == gateDegree){
	
	if( trust_isPM || isAdmin|| (trust_isPmo && projectData2.teamType.equals("자동차 사업부")) || (trust_isPmo2 && projectData2.teamType.equals("전자 사업부") || (trust_isPmo3 && projectData2.teamType.equals("KETS")) 
                || trust_isTaskMaster || trust_isTaskMember)) { 
	    
	    
	
	   if(gateAssResult!=null && "R".equals(resultTotalStr) && "진행".equals(curStateStr) && task.getTaskState() != 5) {//프로젝트가 진행 중, Task가 미완료, 평가결과 R인경우
%>
            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:gateDegreeCreate();" class="btn_blue">차수생성</a></td>
            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
<%
	   }
    }
	
    }
    
%>
                     
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%></td>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03027") %><%--품질문제--%></td>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07133") %><%--종합평가--%></td>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00747") %><%--결과--%></td>
                <% if("Y".equals(resultTotalStr) && isProtoGate1Check) { %>
                <td width="130" class="tdblueM">조건부합격 근거</td>
                <%} %>
                <td width="130" class="tdblueM">상세파일등록</td>
              </tr>

              <tr>
                <td width="130" class="tdwhiteM"><%=resultOutputVal %></td>
                <td width="130" class="tdwhiteM">&nbsp;<%=resultAssVal%></td>
                <td width="130" class="tdwhiteM">&nbsp;<%=resultDqmVal%></td>
                <td width="130" class="tdwhiteM">&nbsp;
                <% if(!StringUtil.isEmpty((String)gateResultTotalHash.get("gateResultOid")) ) { %>
                    <%=resultTotalVal %>
                <% } %>
                </td>
                <td width="130" class="tdwhiteM">&nbsp;
                <% if(!StringUtil.isEmpty((String)gateResultTotalHash.get("gateResultOid")) ) { %>
	                <% if("G".equals(resultTotalStr)) { %>
	                    <img src="/plm/extcore/image/ico_green.png" />
	                <% }else if("Y".equals(resultTotalStr)) { %>
	                    <img src="/plm/extcore/image/ico_yellow.png" />
	                <% }else if("R".equals(resultTotalStr)) { %>
	                    <img src="/plm/extcore/image/ico_red.png" />
	                <% } %>
                <% } %>
                </td>
                <% if("Y".equals(resultTotalStr) && isProtoGate1Check) { %>
                <td width="130" class="tdwhiteM">
                <% if(!"".equals(StringUtil.checkNull(passComment))) { %>
                <a href="JavaScript:gateResultAttatch();" title="<%=passComment%>" style="width:100px; overflow:hidden; display: inline-block; text-overflow:ellipsis; white-space:nowrap;"><%=passComment %></a>
                <%}else{ %>
                <input type="button" value="<%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%>" onclick="JavaScript:gateResultAttatch();" class="s_font">
                <%} %>
                
                </td>
                <%} %>
                <td width="130" class="tdwhiteM">&nbsp;
                <% if(!StringUtil.isEmpty((String)gateResultTotalHash.get("gateResultOid")) ) { %>
                    <a href="JavaScript:gateResultAttatch();"><%=recoveryFileCnt %></a>
<%
    if("".equals(StringUtil.checkNull(resultState)) || "INWORK".equals(resultState) || "REWORK".equals(resultState)) {
%>

                    <!--<input type="button" id="taskExecStartDateBtn" value="<%=messageService.getString("e3ps.message.ket_message", "02794") %><%--첨부--%>" onclick="JavaScript:gateResultAttatch();" class="s_font">  -->
<%
    }
%>

                <% } %>
                </td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="space20"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07140") %><%--평가대상--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
<!--             <div class="tab-part b-space10 clearfix"> -->
<!--                 <ul class="float-l"> -->
<!--                     <li class="activate">산출물</li> -->
<!--                     <li>평가항목</li> -->
<!--                     <li>품질문제</li> -->
<!--                 </ul> -->
<!--             </div> -->



<div id="tabs" style="width:100%;">
    <ul>
        <li><a class="tabref" href="#tabs-1"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></a></li>
        <li><a class="tabref" href="#tabs-2"><%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%></a></li>
        <li><a class="tabref" href="#tabs-3"><%=messageService.getString("e3ps.message.ket_message", "03027") %><%--품질문제--%></a></li>
        <%if(isProtoGate1Check){ %>
        <table border="0" cellspacing="0" cellpadding="0" align="left">
        <td width="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td width="120" style="text-align:center; font-family: NanumBold !important; font-size: 14px; color:blue;">
        <span onclick="javascript:goProtoTaskView()" style="cursor:pointer">Proto Gate1 결과 :</span>
        </td>
        <td width="5">&nbsp;</td>
        <%if(isProtoGate1Pass){ %>
        <td width="70" style="text-align:left; font-family: NanumBold !important; font-size: 14px; <%if("Y".equals(proto_resultTotalStr)){ %> color:red; <%}else{%>color:blue;<%}%>">
        <span onclick="javascript:goProtoTaskView()" style="cursor:pointer"><%=ProtoGate1Result %><%if("Y".equals(proto_resultTotalStr)){ %> &nbsp;&nbsp;<img src="/plm/extcore/image/ico_yellow.png" /><%} %></span>
        </td>
        <%}else{ %>
        <td width="70" style="text-align:left; font-family: NanumBold !important; font-size: 14px; color:red;">
        <span onclick="javascript:goProtoTaskView()" style="cursor:pointer"><%=ProtoGate1Result %></span>
        </td>
        <%} %>
        </table>
        <%} %>
        <table border="0" cellspacing="0" cellpadding="0" align="right">
            <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:ppopOpenPopup()" class="btn_blue">PPAP문서점검</a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td width="10">&nbsp;</td>
                  <% 
                  AssessSheet latestAssSheet = null;
                  E3PSProject latestPjt = E3PSProjectHelper.service.getProjectByProjectNo(project2.getPjtNo());
                  QueryResult qr3 = PersistenceHelper.manager.navigate(latestPjt, "assess", ProjectAssSheetLink.class);

                  String assessOidStr="";
                  String latestOid = CommonUtil.getOIDString(latestPjt);
                  while (qr3.hasMoreElements()) {
                      latestAssSheet = (AssessSheet) qr3.nextElement();
                      if(latestAssSheet!=null) assessOidStr = CommonUtil.getOIDString(latestAssSheet);
                  }
                  
                  String curState = project2.getLifeCycleState().getDisplay(); //String curState = project.getLifeCycleState().toString();
                  if ("진행".equals(curState) && latestAssSheet!=null && "APPROVED".equals(latestAssSheet.getLifeCycleState().toString())) {
                  
	                  if( trust_isPM || isAdmin|| (trust_isPmo && projectData2.teamType.equals("자동차 사업부")) || (trust_isPmo2 && projectData2.teamType.equals("전자 사업부") || (trust_isPmo3 && projectData2.teamType.equals("KETS")) 
	                      || trust_isTaskMaster || trust_isTaskMember)) { 
	
						    if("".equals(StringUtil.checkNull(resultState)) || "INWORK".equals(resultState) || "REWORK".equals(resultState)) {
%>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:resetOutputResult()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td width="10">&nbsp;</td>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:saveTaskOutputAll()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
<%
							}
	                  }
                  }
%>
                    </tr>
                  </table>
                </td>
            </tr>
        </table>
    </ul>
    <!-- 첫번째 tab 화면 -->
    <div id="tabs-1" class="tabMain"  style="height: 200px; overflow: auto; scrollbar-face-color:#ccc;">
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;" id="gateTab1">
              <tr>
                <td width="30" class="tdblueM">No</td>
                <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td width="160" class="tdblueM">Comment<%--Recovery Plan--%></td>
                <td width="50" class="tdblueM">Check<%--Check--%></td>
              </tr>
<%
String opDtOid = "";
String opDtName = "";
String version = "";
String lastVersionOid = "";
String outputNameStr = "";
RevisionControlled opDtDocument = null;
RevisionControlled opDtLastDocument = null;
KETDqmRaise dqmRaiseObj = null;
KETDqmHeader dqmHeaderObj = null;
String state = "";
String chargeNameStr = "";

List<GateTaskOutputDTO> gateTaskOutputList = new ArrayList<GateTaskOutputDTO>();
E3PSTask eTask = (E3PSTask)CommonUtil.getObject(oid);
String checkValue1 = "Y";

    try {
	
	   
        //이전 게이트에서 현재 게이트까지 테스크 산출물 리스트
//         gateTaskOutputList = ProjectTaskCompHelper.service.getProjectOutputList(oid);
        gateTaskOutputList = (List<GateTaskOutputDTO>)gateResultTotalHash.get("resultGateTaskOutputList") ;
//         out.println("gateTaskOutputList:"+gateTaskOutputList.size());
        if(gateTaskOutputList!=null) {
            
            //out.println("<tr><td>state:"+gateTaskOutputList.size()+"</td></td>");
            for(int i=0;i<gateTaskOutputList.size();i++) {
                lastVersionOid = "";
                outputNameStr = "";
                state = "";
                chargeNameStr = "";
                
                GateTaskOutputDTO gDto = gateTaskOutputList.get(i);
                String outputOidStr = "e3ps.project.ProjectOutput:"+gDto.getOutputOid();
                if( StringUtil.isEmpty(gDto.getOutputOid())) continue;
                ProjectOutput output = (ProjectOutput)CommonUtil.getObject(outputOidStr);
                ProjectOutputData outputData = new ProjectOutputData (output);
                opDtDocument = outputData.document;
                opDtLastDocument = outputData.LastDocument;

                opDtOid = outputData.oid;
                String objType = "";

                if(output!=null) {
                    objType = StringUtil.checkNull( output.getObjType());
                    

	                if(objType.equals("ECO") ){
	                    KETProdChangeOrder prodChangeOrderObj = null;
	                    KETMoldChangeOrder moldChangeOrderObj = null;
	                    QueryResult qr = PersistenceHelper.manager.navigate(output, "change", KETProdChangeOrderOutputLink.class);
	                
	                    while (qr.hasMoreElements()) {
	                        prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
	                    }
	                
	                    qr = PersistenceHelper.manager.navigate(output, "change", KETMoldChangeOrderOutputLink.class);
	                
	                    while (qr.hasMoreElements()) {
	                        moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
	                    }
	                    
	                    if(prodChangeOrderObj!=null) {
	                        lastVersionOid = CommonUtil.getOIDString(prodChangeOrderObj);
	                        outputNameStr = prodChangeOrderObj.getEcoName();
	                        state = prodChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
	                        chargeNameStr = outputData.changeOrder.getCreatorFullName();
	                    }else if(moldChangeOrderObj!=null) {
	                        lastVersionOid = CommonUtil.getOIDString(moldChangeOrderObj);
	                        outputNameStr = moldChangeOrderObj.getEcoName();
	                        state = moldChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
	                        chargeNameStr = outputData.changeOrder.getCreatorFullName();
	                    } else {
	                        lastVersionOid = "";
	                        outputNameStr = output.getOutputName();
	                        state = "";
	                    }

	                    
	
	                }else if(objType.equals("QLP") ){
                        
                        ext.ket.dqm.entity.KETDqmDTO dqmDto = ext.ket.project.task.service.ProjectTaskCompHelper.service.getOutputQLP(output);
                        String raiseOidStr =  dqmDto.getRaiseOid();
                        dqmRaiseObj = (raiseOidStr==null || "".equals(raiseOidStr))?null:(KETDqmRaise)CommonUtil.getObject(raiseOidStr);
                        dqmHeaderObj = (dqmDto==null || "".equals(dqmDto.getOid()))?null:(KETDqmHeader)CommonUtil.getObject(dqmDto.getOid());

                        if(dqmDto!=null) {
                            lastVersionOid = CommonUtil.getOIDString(dqmRaiseObj);
                            if(dqmHeaderObj!=null) {
                        	   outputNameStr = dqmHeaderObj.getProblem();
//                         	   state = dqmRaiseObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
                        	   state = dqmHeaderObj.getDqmStateName();
                               chargeNameStr = dqmRaiseObj.getCreatorFullName();
                            }else {
                        	   outputNameStr = output.getOutputName();
                        	   chargeNameStr = "";
                        	   state = "";
                            }
                        }else {
                            lastVersionOid = "";
                            outputNameStr = output.getOutputName();
                            state = "";
                        }
                            
	                }else if(objType.equals("TRY") ){
	                    KETTryMold moldTryConditionObj = null;
	                    KETTryPress pressTryConditionObj = null;
	                    
	                    QueryResult qr = PersistenceHelper.manager.navigate(output, "tryMold", KETTryMoldOutputLink.class);
	                
	                    while (qr.hasMoreElements()) {
	                        moldTryConditionObj = (KETTryMold) qr.nextElement();
	                    }
	                
	                    qr = PersistenceHelper.manager.navigate(output, "tryPress", KETTryPressOutputLink.class);
	                    
	                    while (qr.hasMoreElements()) {
	                        pressTryConditionObj = (KETTryPress) qr.nextElement();
	                    }
	                    
	                    if(moldTryConditionObj!=null) {
	                        lastVersionOid = CommonUtil.getOIDString(moldTryConditionObj);
	                        outputNameStr = moldTryConditionObj.getName();
	                        state = moldTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
		                    chargeNameStr = moldTryConditionObj.getCreatorFullName();
	                    }else if(pressTryConditionObj!=null) {
	                        lastVersionOid = CommonUtil.getOIDString(pressTryConditionObj);
	                        outputNameStr = pressTryConditionObj.getName();
	                        state = pressTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
                            chargeNameStr = pressTryConditionObj.getCreatorFullName();
	                    }else {
                            lastVersionOid = "";
                            outputNameStr = output.getOutputName();
                            state = "";
                        }
	                    
	                }else if(objType.equals("DOC") || objType.equals("DWG")){
	                    if(outputData.LastDocument!=null) {
	                	
		                    if(outputData.LastDocument instanceof KETProjectDocument)
		                    {
		                        outputNameStr =  StringUtil.checkNull(((KETProjectDocument)outputData.LastDocument).getTitle() );
		                    }else if(outputData.LastDocument instanceof EPMDocument) {
		                        outputNameStr =  StringUtil.checkNull(((EPMDocument)outputData.LastDocument).getName() );
		                    }
		//                     version = outputData.currentDocument.getVersionDisplayIdentifier().toString();
		                    if(opDtLastDocument!=null) {
			                    lastVersionOid = CommonUtil.getOIDString(opDtLastDocument);
		                    }
		                    chargeNameStr = outputData.LastDocument.getCreatorFullName();
	                    }else {
                            lastVersionOid = "";
                            outputNameStr = output.getOutputName();
                            state = "";
	                	
	                    }
                    }else if(objType.equals("ETC") || objType.equals("COST") || objType.equals("SALES")) {
                        WTUser owner = (WTUser)output.getOwner().getPrincipal();
                        if(owner != null){
                            chargeNameStr = owner.getFullName();
                        }
                        if(output.getCompletion()==100) {
                            state = "완료";
                        }
                    }
                }
                
                
                /*******************(시작) Gate객체 저장당시 연결된 산출물 중 AssessResultOutputLink객체에 산출물의 History정보가 있는 경우 가져오기****************/
                if(gateAssResult!=null) {
                    AssessResultOutputLink assRsltOutLink = null;
                    QueryResult qr = PersistenceHelper.manager.navigate(output, AssessResultOutputLink.ASSESS_ROLE, AssessResultOutputLink.class, false);
                    if (qr.hasMoreElements()) {
                      assRsltOutLink = (AssessResultOutputLink) qr.nextElement();
                      //if(!StringUtil.isEmpty(assRsltOutLink.getOutputOid())) lastVersionOid = assRsltOutLink.getOutputOid();
                      if(!StringUtil.isEmpty(assRsltOutLink.getOutputName())) outputNameStr = assRsltOutLink.getOutputName();
                      if(!StringUtil.isEmpty(assRsltOutLink.getOutputState())) state = assRsltOutLink.getOutputState();
                      if(!StringUtil.isEmpty(assRsltOutLink.getOutputCharge())) chargeNameStr = assRsltOutLink.getOutputCharge();
                    }
                }
                /*******************(끝) Gate객체 저장당시 연결된 산출물 중 AssessResultOutputLink객체에 산출물의 History정보가 있는 경우 가져오기****************/
                
                RevisionControlled rControll = outputData.currentDocument;
                if(rControll !=null) state = outputData.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
//              out.println("<tr><td>state:"+state+"</td></td>");

                String outputBgColor = "";
                if("G".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:green;";
                else if("Y".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:yellow;";
                else if("R".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:red;";
                
                
                if(StringUtil.isEmpty(gDto.getOutputCheck())) checkValue1 = "N";
                
                String stateStr = StringUtil.checkNull(state);
                if(StringUtil.isEmpty(stateStr)) stateStr = "-";
                
                String opDtNameFull = outputData.name;
                opDtName = StringUtil.checkNull(outputData.name);
                //opDtName = e3ps.common.util.StringUtil.cutStr(opDtName, 10);
                
                if(StringUtil.isEmpty(chargeNameStr)) chargeNameStr = "-";
                
                String outputNameStrFull = outputNameStr;
                //outputNameStr = e3ps.common.util.StringUtil.cutStr(outputNameStr, 25);
%>
              <tr>
                <td width="30" class="tdwhiteM"><%=i+1%></td>
            <% 
                if(opDtDocument!=null) {
                    if (lastVersionOid != null){
            %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div></a>
            <%      }else { %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(opDtDocument)%>')"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div></a>
            <%      
                    }
                }else if(objType.equals("ETC") || objType.equals("COST") || objType.equals("SALES")){
                    String strCompleteReason = output.getComplete_reason();
                    if(StringUtil.isEmpty(strCompleteReason)) {
            %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=opDtNameFull %>"><div class="ellipsis" style="width:100%;"><%=opDtName%></div>
            <%      
                    }else {
            %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=opDtNameFull %>"><a href="#" onClick="javascript:openCompleteReson('<%=opDtOid%>');"><div class="ellipsis" style="width:100%;"><%=opDtName%></div> </a>
			<%      
                    }
                }else if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETProdChangeOrder){  %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:openECO('<%=outputData.changeOrder%>', 'PROD');"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div> </a>
            <%      
                }else if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETMoldChangeOrder){  %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:openECO('<%=outputData.changeOrder%>', 'MOLD');"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div> </a>
            <%      
                }else if(objType.equals("TRY") && outputData.tryCondition!=null && outputData.tryCondition  instanceof KETTryMold) {  %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:openTry('<%=outputData.tryCondition%>', 'MOLD');"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div> </a>
            <%      
                }else if(objType.equals("TRY") && outputData.tryCondition!=null && outputData.tryCondition  instanceof KETTryPress) {  %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:openTry('<%=outputData.tryCondition%>', 'PRESS');"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div> </a>
            
            <%      
                }else if(objType.equals("QLP")) {
                    if(dqmHeaderObj==null) {  %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>">
                <div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div> </a>
                
            <%      }else {%>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>">
                <a href="#" onClick="javascript:openDQM('<%=CommonUtil.getOIDString(dqmHeaderObj)%>');"><div class="ellipsis" style="width:100%;"><nobr><%=dqmHeaderObj.getProblem() %></nobr></div> </a>
            <%      } %>
                
            <%  }else { 
        	    //outputNameStr = StringUtil.checkNull(gDto.getOutputName());
            %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=StringUtil.checkNull(gDto.getOutputName()) %>"><div class="ellipsis" style="width:100%;"><%=outputNameStr %></div>
                    
            <%  } %>
                </td>
                <td width="60" class="tdwhiteM">&nbsp;<%=chargeNameStr %></td>
                <td width="50" class="tdwhiteM">&nbsp;<%=stateStr %></td>
                <td width="160" class="tdwhiteM">&nbsp;
<%--                     <input type="text" name="tgTaskComments" value="<%=StringUtil.checkNull(gDto.getOutputComment()) %>" style="width:90%"/> --%>
<%
String outputLinkOidStr = gDto.getOutputLinkOid();
if(outputLinkOidStr ==null ) outputLinkOidStr = "";
else if(!StringUtil.isEmpty(outputLinkOidStr)) outputLinkOidStr = "ext.ket.project.gate.entity.AssessResultOutputLink:"+outputLinkOidStr;
%>
                    <input type="hidden" name="outputLinkOids" value="<%=outputLinkOidStr %>" />
                    <input type="text" id="tgTaskComments" name="tgTaskComments" class="txt_field" style="width:95%" <%=disabledVal %> value="<%=StringUtil.checkNull(gDto.getOutputComment()) %>"  onfocus="javascript:execStartDateCheck()"/>
                </td>
                <td width="50" id="tdCombo<%=i %>" width="80" class="tdwhiteM" style="<%=outputBgColor %>;text-align:<%=("disabled".equals(disabledVal))?"center":"right"%>">&nbsp;
                    <input type="hidden" name="tgOutputOids" value="<%=outputOidStr %>"/>
                    
                    <%
                    String colorStr = "";
                    if("G".equals(StringUtil.checkNull(gDto.getOutputCheck()))) colorStr = "green";
                    else if("Y".equals(StringUtil.checkNull(gDto.getOutputCheck()))) colorStr = "yellow";
                    else if("R".equals(StringUtil.checkNull(gDto.getOutputCheck()))) colorStr = "red";
                    %>
                    
                    <%
                    if("disabled".equals(disabledVal)) {
                    %>
                    <font color="#000000"><b><%= StringUtil.checkNull(gDto.getOutputCheck())%></b></font>
                    <%
                    }else {
                    %>
                    <select id="tgTaskChecks" name="tgTaskChecks" className="fm_jmp"  <%=disabledVal %> style="width: 60;background-color:<%=colorStr %>;border:none" onchange="javascript:fn_changeCombo(<%=i%>);" onfocus="javascript:execStartDateCheck()">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                        <option value="G" <% if("G".equals(StringUtil.checkNull(gDto.getOutputCheck()))) out.print("selected"); %>>G</option>
                        <option value="Y" <% if("Y".equals(StringUtil.checkNull(gDto.getOutputCheck()))) out.print("selected"); %>>Y</option>
                        <option value="R" <% if("R".equals(StringUtil.checkNull(gDto.getOutputCheck()))) out.print("selected"); %>>R</option>
                    </select>
                    <%
                    }
                    %>
                    
                </td>
              </tr>
<%
            }
        }
    }catch(Exception e){
	Kogger.error(e);
    }

%>
<input type="hidden" name="checkValue1" value="<%=checkValue1%>"/>
            </table>
    </div>
    <div id="tabs-2"  style="height: 200px; overflow: auto;scrollbar-face-color:#ccc;">
<%
  
    List<GateCheckSheetDTO>  taskGateCheckSheetList = new ArrayList<GateCheckSheetDTO>();
    String taskName = eTask.getTaskName();
    String taskCategory = eTask.getTaskTypeCategory();
    
    String checkValue2 = "Y";

    try {
        //이전 게이트에서 현재 게이트까지 테스크 산출물 리스트
//      taskGateCheckSheetList = ProjectTaskCompHelper.service.getProjectTaskGateCheckLinkList(oid);
        taskGateCheckSheetList = (List<GateCheckSheetDTO>)gateResultTotalHash.get("resultGateCheckSheetList") ;
%>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed">
              <colgroup>
                <col style="width:2%"></col>
                <col style="width:8%"></col>
                <col style="width:35%"></col>
                <col style="width:5%"></col>
                <col style="width:5%"></col>
                <col style="width:40%"></col>
                <col style="width:3%"></col>
                <col style="width:5%"></col>
              </colgroup>
              <tr>
                <td rowspan="2" class="tdblueM">No</td>
                <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07134") %><%--목표구분--%></td>
                <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%></td>
                <td rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07135") %><%--달성기준--%></td>
<%--                 <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07136") %>기준</td> --%>
                <td colspan="2" class="tdblueM"><%=taskName%><%--GateName--%></td>
                <td rowspan="2" class="tdblueM">Check<%--Check--%></td>
                <td rowspan="2" class="tdblueM">담당부서</td>
              </tr>
              <tr>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
              </tr>
<%



		int gateCheckRsltCnt = 0;
        if(taskGateCheckSheetList!=null) {

            for(int i=0;i<taskGateCheckSheetList.size();i++) {
                
                GateCheckSheetDTO gDto = taskGateCheckSheetList.get(i);
        	    String tCriteria = gDto.getCriteria();
                String gateCheckOidStr = "ext.ket.project.gate.entity.GateCheckSheet:"+gDto.getGateCheckSheetOid();
//                 if("0".equals(tCriteria)) tCriteria = ">=";
//                 else if("1".equals(tCriteria)) tCriteria = "=";
//                 else if("2".equals(tCriteria)) tCriteria = "<=";
                String checkBgColor = "";
                if("G".equals(StringUtil.checkNull(gDto.getTaskCheck()))) checkBgColor = "background-color:green;";
                else if("Y".equals(StringUtil.checkNull(gDto.getTaskCheck()))) checkBgColor = "background-color:yellow;";
                else if("R".equals(StringUtil.checkNull(gDto.getTaskCheck()))) checkBgColor = "background-color:red;";
                
                if(StringUtil.isEmpty(gDto.getTaskCheck())) checkValue2 = "N";


                String checkSheetStr = StringUtil.checkNull( gDto.getCheckSheetName() );
                //checkSheetStr = e3ps.common.util.StringUtil.cutStr(checkSheetStr, 50);
                
                String baseSheetStr = StringUtil.checkNull( gDto.getAchieveBase() );
                //baseSheetStr = e3ps.common.util.StringUtil.cutStr(baseSheetStr, 16);
%>
              <tr>
                <td width="30" class="tdwhiteM"><%=(gateCheckRsltCnt+1)%></td>
                <td width="80" class="tdwhiteM"><%=StringUtil.checkNull( gDto.getTargetTypeName() ) %></td>
                <td width="120" class="tdwhiteM" style="text-align:left" title="<%=gDto.getCheckSheetName() %>"><div class="ellipsis" style="width:100%;"><%=checkSheetStr %></div><%--평가항목--%></td>
                <td width="100" class="tdwhiteM" style="text-align:left" title="<%=StringUtil.checkNull( gDto.getAchieveBase()) %>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull( baseSheetStr ) %></div><%--달성기준--%></td>
<%--                 <td width="80" class="tdwhiteM"><%=StringUtil.checkNull( tCriteria ) %>기준</td> --%>
                <td width="80" class="tdwhiteM"><%=StringUtil.checkNull(gDto.getTaskTarget()) %><%--GateName--%></td>
                <td width="60" class="tdwhiteM">
                    <input type="hidden" name="tgGateRsltCodes" value="" />
                    <input type="hidden" name="tgGateCheckOids" value="<%=gateCheckOidStr %>" />
<%
String gateLinkOidStr = gDto.getGateLinkOid();
if(gateLinkOidStr ==null ) gateLinkOidStr = "";
else if(!StringUtil.isEmpty(gateLinkOidStr)) gateLinkOidStr = "ext.ket.project.gate.entity.AssessResultGateCheckLink:"+gateLinkOidStr;
%>
                    <input type="hidden" name="gateLinkOids" value="<%=gateLinkOidStr %>" />
                    <input type="text" id="tgGateResults" name="tgGateResults" <%=disabledVal %> class="txt_field" style="width:100%" value="<%=StringUtil.checkNull(gDto.getTaskResult()) %>"  onfocus="javascript:execStartDateCheck()"/>
                    
                </td>
                <td id="tdComboCheck<%=gateCheckRsltCnt %>" width="40" class="tdwhiteM" style="<%=checkBgColor %>;text-align:<%=("disabled".equals(disabledVal))?"center":"right"%>">
                    <%
                    String colorStr = "";
                    if("G".equals(StringUtil.checkNull(gDto.getTaskCheck()))) colorStr = "green";
                    else if("Y".equals(StringUtil.checkNull(gDto.getTaskCheck()))) colorStr = "yellow";
                    else if("R".equals(StringUtil.checkNull(gDto.getTaskCheck()))) colorStr = "red";
                    %>
                    <%
                    if("disabled".equals(disabledVal)) {
                    %>
                    <font color="#000000"><b><%= StringUtil.checkNull(gDto.getTaskCheck())%></b></font>
                    <%
                    }else {
                    %>
                    <select id="tgGateChecks" name="tgGateChecks" className="fm_jmp" <%=disabledVal %> style="width: 50;background-color:<%=colorStr %>;border:none"  onchange="javascript:fn_changeCombo2(<%=gateCheckRsltCnt%>);" onfocus="javascript:execStartDateCheck()">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                        <option value="G" <% if("G".equals(StringUtil.checkNull(gDto.getTaskCheck()))) out.println("selected"); %>>G</option>
                        <option value="Y" <% if("Y".equals(StringUtil.checkNull(gDto.getTaskCheck()))) out.println("selected"); %>>Y</option>
                        <option value="R" <% if("R".equals(StringUtil.checkNull(gDto.getTaskCheck()))) out.println("selected"); %>>R</option>
                    </select>
                    <%
                    }
                    %>
                </td>
                
                
                <%if("disabled".equals(disabledVal)) { %>
                <td width="140" class="tdwhiteM" style="text-align:left" title="<%=StringUtil.checkNull(gDto.getManagerDeptName()) %>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(gDto.getManagerDeptName())  %></div></td>
                <%}else{ %>
                <td width="140" class="tdwhiteM" style="text-align:left">
                <input style="width:77%;" type="text" id="managerDept<%=gateCheckRsltCnt%>" name="managerDept" class="txt_fieldRO" value="<%=StringUtil.checkNull(gDto.getManagerDeptName()) %>" readonly>
                            <input type=hidden id="managerDeptOids" name="managerDeptOids" value="" class="managerDeptOid<%=gateCheckRsltCnt%>">
                            <a href="javascript:gateAddDepartment('managerDept<%=gateCheckRsltCnt%>','managerDeptOid<%=gateCheckRsltCnt%>');"><img src="/plm/portal/images/icon_5.png"></a>
                            <a href="javascript:deleteDeptValue('managerDept<%=gateCheckRsltCnt%>','managerDeptOid<%=gateCheckRsltCnt%>');"><img src="/plm/portal/images/icon_delete.gif"></a>
                        </td>
                <%} %>
              </tr>
<%
				gateCheckRsltCnt++;
            }
        }
    }catch(Exception e){
	Kogger.error(e);
    }
%>
<input type="hidden" name="checkValue2" value="<%=checkValue2%>"/>
            </table>
    </div>
    <div id="tabs-3" style="height: 200px; overflow: auto;scrollbar-face-color:#ccc;">
<%
String checkValue3 = "Y";
    List<KETDqmDTO>  dqmList = new ArrayList<KETDqmDTO>();
    try {

%>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed">
              <tr>
                <td width="30" class="tdblueM">No</td>
                <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03027") %>No<%--품질문제No--%></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01624") %><%--불량유형--%></td>
                <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07137") %><%--발생처--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07138") %><%--발생일--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01564") %>No<%--부품No--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07139") %><%--발생구분--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04126") %><%--긴급도--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td width="60" class="tdblueM">Check<%--Check--%></td>
              </tr>
<%

//         List<KETDqmDTO>  taskQualityPrbmList = ProjectTaskCompHelper.service.getQualityProblemList(oid);
		List<KETDqmDTO>  taskQualityPrbmList = (List<KETDqmDTO>)gateResultTotalHash.get("resultKETDqmList") ;
        if(taskQualityPrbmList!=null) {

            for(int i=0;i<taskQualityPrbmList.size();i++) {
                //프로젝트 평가항목 리스트에서 해당 Gate명에 해당하는 체크항목이 체크된 것만 가져온다
                KETDqmDTO gDto = taskQualityPrbmList.get(i);
                
                String outputBgColor = "";
                if("G".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:green;";
                else if("Y".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:yellow;";
                else if("R".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:red;";
                
                if(StringUtil.isEmpty(gDto.getOutputCheck())) checkValue3 = "N";
                
                String prbmStr = StringUtil.checkNull(gDto.getProblem());
                //prbmStr = e3ps.common.util.StringUtil.cutStr(prbmStr, 15);
                
                String occurStr = StringUtil.checkNull(gDto.getOccurDate());
                if(occurStr!=null && occurStr.length()>2) occurStr = occurStr.substring(2);
                //occurStr = e3ps.common.util.StringUtil.cutStr(occurStr, 3);
                
                String appDateStr = StringUtil.checkNull(gDto.getRaiseApprovDate());
                
                String reviewDateStr = StringUtil.checkNull(gDto.getReviewDate());
                if(StringUtil.checkString(reviewDateStr) && reviewDateStr.length()>2) reviewDateStr = reviewDateStr.substring(2);
                //reviewDateStr = e3ps.common.util.StringUtil.cutStr(reviewDateStr, 3);
                
                String prbmStateStr = StringUtil.checkNull(gDto.getDqmStateName());
                //prbmStateStr = e3ps.common.util.StringUtil.cutStr(prbmStateStr, 3);

				/*******************(시작) Gate객체 저장당시 연결된 산출물 중 AssessResultDqmHeaderLink객체에 산출물의 History정보가 있는 경우 가져오기****************/
				//navigate 로 가져오는 상태값이 중복으로 가져오는 문제가 있어 주석처리
				/*if(gateAssResult!=null) {
				    AssessResultDqmHeaderLink dqmHeaderLink = null;
				    QueryResult qr = PersistenceHelper.manager.navigate(gateAssResult, AssessResultDqmHeaderLink.DQM_ROLE , AssessResultDqmHeaderLink.class, false);
				    if (qr.hasMoreElements()) {
					   dqmHeaderLink = (AssessResultDqmHeaderLink) qr.nextElement();
					   reviewDateStr = dqmHeaderLink.getDqmCompDate();
					   prbmStateStr = dqmHeaderLink.getDqmState();
					   
				    }
				}*/
				//navigate 로 가져오는 상태값이 중복으로 가져오는 문제가 있어 주석처리 하고 아래 구문으로 대체함 2015.04.06 황정태
				if(gateAssResult!=null) {
					QuerySpec select = new QuerySpec();
					int idx = select.addClassList(AssessResultDqmHeaderLink.class, true);
					select.appendWhere(new SearchCondition(AssessResultDqmHeaderLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(gateAssResult) ), new int[] { 0 });
					select.appendAnd();
					select.appendWhere(new SearchCondition(AssessResultDqmHeaderLink.class, "roleAObjectRef.key.id", "=", CommonUtil.getOIDLongValue(gDto.getOid())), new int[] { 0 });
					QueryResult re = PersistenceHelper.manager.find(select);
					
					while (re.hasMoreElements()) {
					    Object[] obj = (Object[]) re.nextElement();
					    AssessResultDqmHeaderLink dqmHeaderLink = (AssessResultDqmHeaderLink) obj[0];
					    reviewDateStr = dqmHeaderLink.getDqmCompDate();
						prbmStateStr = dqmHeaderLink.getDqmState();
					}
				}

				/*******************(끝) Gate객체 저장당시 연결된 산출물 중 AssessResultDqmHeaderLink객체에 산출물의 History정보가 있는 경우 가져오기****************/
%>
              <tr>
                <td width="30" class="tdwhiteM"><%=(i+1) %></td>
                <td width="70" class="tdwhiteM"><a href="#" style="color:#1660c7" onclick="javascript:viewDqmPopup('<%=gDto.getOid()%>');">
                <%=StringUtil.checkNull(gDto.getProblemNo()) %><%--품질문제No--%></a></td>
                <td width="100" style="text-align:left" title="<%=StringUtil.checkNull(gDto.getProblem()) %>" class="tdwhiteM"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(prbmStr) %></div><%--문제점--%></td>
                <td width="60" class="tdwhiteM" title="<%=StringUtil.checkNull(gDto.getDefectTypeName())%>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(gDto.getDefectTypeName())%><%--불량유형--%></div></td>
                <td width="40" class="tdwhiteM"><%=StringUtil.checkNull(gDto.getOccurName()) %><%--발생처--%></td>
                <td width="60" class="tdwhiteM" title="<%=StringUtil.checkNull(gDto.getOccurDate())%>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(occurStr)%></div><%--발생일--%></td>
                <td width="60" class="tdwhiteM"><%=StringUtil.checkNull(gDto.getRelatedPart())%><%--부품 No--%></td>
                <td width="50" class="tdwhiteM" title="<%=StringUtil.checkNull(gDto.getCartypeName())%>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(gDto.getCartypeName())%><%--차종--%></div></td>
                <td width="50" class="tdwhiteM" title="<%=StringUtil.checkNull(gDto.getOccurDivName())%>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(gDto.getOccurDivName())%><%--발생구분--%></div></td>
                <td width="60" class="tdwhiteM" title="<%=StringUtil.checkNull(gDto.getReviewDate())%>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(reviewDateStr)%></div><%--완료일--%></td>
                <td width="50" class="tdwhiteM"><%=StringUtil.checkNull(gDto.getUrgencyName())%><%--긴급도--%></td>
                <td width="50" class="tdwhiteM" title="<%=StringUtil.checkNull(gDto.getDqmStateName())%>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(prbmStateStr)%></div><%--상태--%></td>
                <td id="tdComboDqm<%=i %>" width="60" class="tdwhiteM" style="<%=outputBgColor %>;text-align:<%=("disabled".equals(disabledVal))?"center":"right"%>">
<%
String dqmLinkOidStr = gDto.getDqmLinkOid();
if(dqmLinkOidStr ==null ) dqmLinkOidStr = "";
else if(!StringUtil.isEmpty(dqmLinkOidStr)) dqmLinkOidStr = "ext.ket.project.gate.entity.AssessResultDqmHeaderLink:"+dqmLinkOidStr;
%>
                    <input type="hidden" name="dqmLinkOids" value="<%=dqmLinkOidStr %>" />

                    <%
                    String colorStr = "";
                    if("G".equals(StringUtil.checkNull(gDto.getOutputCheck()))) colorStr = "green";
                    else if("Y".equals(StringUtil.checkNull(gDto.getOutputCheck()))) colorStr = "yellow";
                    else if("R".equals(StringUtil.checkNull(gDto.getOutputCheck()))) colorStr = "red";
                    %>
                    <input type="hidden" name="tgDqmHeaderOids" value="<%=gDto.getOid() %>"/>
                    <%
                    if("disabled".equals(disabledVal)) {
                    %>
                    <font color="#000000"><b><%= StringUtil.checkNull(gDto.getOutputCheck())%></b></font>
                    <%
                    }else {
                    %>
                    <select id="tgDqmHeaderChecks" name="tgDqmHeaderChecks" className="fm_jmp" <%=disabledVal %> style="width:50;background-color:<%=colorStr %>;border:none"  onchange="javascript:fn_changeCombo3(<%=i%>);" onfocus="javascript:execStartDateCheck()">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                        <option value="G" <% if("G".equals(StringUtil.checkNull(gDto.getOutputCheck()))) out.println("selected"); %>>G</option>
                        <option value="Y" <% if("Y".equals(StringUtil.checkNull(gDto.getOutputCheck()))) out.println("selected"); %>>Y</option>
                        <option value="R" <% if("R".equals(StringUtil.checkNull(gDto.getOutputCheck()))) out.println("selected"); %>>R</option>
                    </select>
                    <%
                    }
                    %>
                </td>
              </tr>
<%
            }
        }
    }catch(Exception e){
	Kogger.error(e);
    }
%>
<input type="hidden" name="checkValue3" value="<%=checkValue3%>"/>
            </table>
    </div>
</div>


          </td>
        </tr>
      </table>
      <%if(!buttonAuth.isChild){ %>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
          <td class="tdwhiteL">
            <jsp:include page="/jsp/project/TaskDependencyInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oid%>"/>
              <jsp:param name="addTask" value=""/>
              <jsp:param name="deleteTask" value=""/>
              <jsp:param name="popup" value="<%=popup%>"/>
            </jsp:include>
          </td>
        </tr>
      </table>

      <%}%>
    </td>
  </tr>
<!--   <tr> -->
<%--     <td height="30" valign="bottom"><iframe src="../../portal/common/copyright<%Kogger.debug("popup = " + popup);if(popup != null && !("null".equals(popup))){ %>_t<%} %>.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td> --%>
<!--   </tr> -->
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
<%}catch(Exception e){
    Kogger.error(e);
}

%>


<%
String oid =  request.getParameter("oid");
String tabIndexStr =  request.getParameter("tabIndex");
if(StringUtil.isEmpty(tabIndexStr)) tabIndexStr = "0";
%>
<script type="text/javascript">
var isCheckTarget = true;
function goTargetTab() {
	var tab = CommonUtil.tabs('tabs');
	tab.tabs({active: <%=tabIndexStr%>});
	
	var taskCategoryType = '<%=taskCategoryType%>';
	
	if(taskCategoryType != 'p-2' && taskCategoryType.indexOf('p') != -1){//p Gate는 평가항목만 평가대상임(p-2 만 빼고)
		tab.tabs({active: 1});
		tab.tabs({disabled: [0,2]});
		isCheckTarget = false;
	}
}

var gateCommentCheck = function(){
	
	var check = function(trObj, checks, baseIdx, inputs){//tr List , selectBox List, 점검 대상 table 의 데이터 시작 rowIndex, input List 
		
		var checkResult = true;
	
	    for(var i=0; i<checks.length; i++){
	        var checkVal = checks[i].options[checks[i].selectedIndex].value;
            
            if( (checkVal == 'Y' ||checkVal == 'R') && inputs[i].value == ''){//Check값이 G가 아닐 때 Comment 또는 실적값이 비어있는지 체크한다
                checkResult = false;
                break;
            }
            baseIdx++;
	    }
	    
		return checkResult;
	}
	
	var TaskTR = document.querySelectorAll("#tabs-1 table tbody tr");//산출물 tab
	var GateTR = document.querySelectorAll("#tabs-2 table tbody tr");//평가항목 tab
	
	var GateChecks = document.getElementsByName("tgGateChecks");//산출물 tab의 selectBox
	var TaskChecks = document.getElementsByName("tgTaskChecks");//평가항목 tab의 selectBox
	
	var TgTaskComments = document.getElementsByName("tgTaskComments");//산출물 tab의 comment input List
	var TgGateResults = document.getElementsByName("tgGateResults");//평가항목 tab의 실적 input List
	
	if(!check(TaskTR,TaskChecks,1,TgTaskComments) || !check(GateTR,GateChecks,2,TgGateResults)){
		return false;
	}
	
	return true;
}

function saveTaskOutputAll() {

    //alert($('#tabs-1').val() + ":: "$('#tabs-2').val() + ":: "+$('#tabs-3').val());
    var $tabs = $('#tabs').tabs();

    //get selected tab
    function getSelectedTabIndex() {
        return $tabs.tabs('option', 'active');
    }
    
    var urlStr = '/plm/ext/project/task/saveTaskRelatedAllResult.do';
    /*
    if(getSelectedTabIndex()==0) {
        urlStr = '/plm/ext/project/task/saveTaskOutputResult.do';
    }else if(getSelectedTabIndex()==1) {
        urlStr = '/plm/ext/project/task/saveTaskGateCheckResult.do';
    }else if(getSelectedTabIndex()==2) {
        urlStr = '/plm/ext/project/task/saveTaskQualityResult.do';
    }
    */
    
    if(isCheckTarget && document.getElementById("gateTab1").rows.length < 2){
    	alert("점검대상 산출물이 지정되지 않았습니다.");
    	return;
    }
    
    if(isCheckTarget && !gateCommentCheck()){
    	alert("평가결과가 조건부(Y),불합격(R) 인 항목이 존재합니다.\r\n코멘트 or 실적 입력바랍니다.");
    	return;
    }
    
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>") ) {
//      document.forms[0].action = "/plm/ext/project/task/saveTaskOutputResult.do";
//      document.forms[0].method = "post";
//      document.forms[0].submit();
  
        $.ajax( {
            url : urlStr,
            type : "POST",
            data : $('[name=gateForm]').serialize(),
            async : false,
            success: function(data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다. --%>');
                document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?oid=<%=oid%>&tabIndex="+getSelectedTabIndex()+"&popup=popup";
                document.forms[0].method = "post";
                document.forms[0].submit();
//                 opener.location.href='/plm/jsp/project/TaskGateView.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
//                 self.close();
            },
            fail : function(){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다. --%>');
                hideProcessing();
            }
        });
    }
    
}

function gateDegreeCreate(){
	
	var $tabs = $('#tabs').tabs();

    //get selected tab
    function getSelectedTabIndex_() {
        return $tabs.tabs('option', 'active');
    }
    
    
	if ( confirm("평가대상 차수생성 하시겠습니까?") ) {
  
        $.ajax( {
        	url : "/plm/ext/project/task/createDegreeTaskRelatedAllResult.do",
            type : "POST",
            data : $('[name=gateForm]').serialize(),
            async : false,
            success: function(data) {
                alert(data+'차수 평가대상이 생성되었습니다.');
                document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?oid=<%=oid%>&tabIndex="+getSelectedTabIndex_()+"&popup=popup&gateDegree="+data;
                document.forms[0].method = "post";
                document.forms[0].submit();
//                 opener.location.href='/plm/jsp/project/TaskGateView.jsp?oid='+data.replace(/(^\s*)|(\s*$)/gi, '')+'&popup=popup';
//                 self.close();
            },
            fail : function(){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01943")%><%--수정 실패 하였습니다. --%>');
                hideProcessing();
            }
        });
    }
}

function viewTask(oid){
    openView(oid);
}




$(document).ready(function(){
	$(document).keydown(function(e){   
        if((e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA") || e.target.name == 'managerDept'){//화면에서 백스페이스로 뒤로 가기 막기 , 평가항목의 담당부서 input이 막혀있으므로 백스페이스로 화면이동하는것막기      
            if(e.keyCode === 8){   
            return false;
            }
        }
    });
    CommonUtil.tabs('tabs');
//     CommonUtil.singleSelect('tgTaskChecks',100);
//     CommonUtil.singleSelect('tgGateChecks',100);
//     CommonUtil.singleSelect('tgDqmHeaderChecks',100);
    
    goTargetTab();
    
});
</script>