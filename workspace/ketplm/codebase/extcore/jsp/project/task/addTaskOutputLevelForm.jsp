<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%@ page import = "wt.vc.Versioned"%>
<%@ page import = "wt.vc.VersionControlHelper"%>
<%@ page import="java.util.*, java.sql.*" %>
<%@ page import = "wt.org.*,wt.session.*"%>
<%@ page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.project.*,
                 e3ps.common.util.KETStringUtil,
                 ext.ket.project.gate.entity.*,
                 ext.ket.project.task.entity.*,
                 ext.ket.project.task.service.*,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%
//팝업 여부
String popup = request.getParameter("popup");
String isClose = request.getParameter("isClose");

String oid = request.getParameter("oid");
List<TrustTaskResultDTO> assessSheetDto = (List<TrustTaskResultDTO>)request.getAttribute("taskLevelResult");

E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
ExtendScheduleData taskSchedule = null;
taskSchedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
String execStartDate = "";
if(taskSchedule!=null) {
    Timestamp execStartDateTime =  taskSchedule.getExecStartDate();
	if(execStartDateTime!=null) execStartDate = DateUtil.getTimeFormat(execStartDateTime,"yyyy-MM-dd");
}

List<TrustTaskResultDTO>  taskTrustResultList = ProjectTaskCompHelper.service.getTaskTrustLevelList(oid);
List<TrustTaskResultDTO> taskTrustResultAllList = ProjectTaskCompHelper.service.getTaskTrustResultList(oid);
boolean isOutputStatusLast = false;
int outputCompleteCnt = 0;
int outputCompleteSum = 0;
boolean isOutputComp = false;
if(taskTrustResultList!=null) {
    
	for(int i=0;i<taskTrustResultList.size();i++) {
	    //신뢰성 평가 결과 리스트를 가져온다
	    TrustTaskResultDTO gDto = taskTrustResultList.get(i);
	    if(i==taskTrustResultList.size()-1) {
	        
	        String taskTrustRstOid = gDto.getTrustOid();
	
	        for(int k=0;k<taskTrustResultAllList.size();k++) {
	           TrustTaskResultDTO gDtoTagt = taskTrustResultAllList.get(k);
	           String taskTrustRstOidTgt = gDtoTagt.getTrustOid();
	           String outputObjStatus = StringUtil.checkNull(gDtoTagt.getObjStatus());
	           if(taskTrustRstOid.equals(taskTrustRstOidTgt) ) {
	               //out.println("out:"+gDtoTagt.getOutputOid());
	               
	               e3ps.project.ProjectOutput outputRstObj = (e3ps.project.ProjectOutput) CommonUtil.getObject(gDtoTagt.getOutputOid());
	               if(outputRstObj!=null) {
	                   //out.println("out:"+gDtoTagt.getOutputOid());
		               outputCompleteCnt++;
		               outputCompleteSum +=  outputRstObj.getCompletion();
                       if(outputRstObj.getCompletion()==100) isOutputComp = true;
	               }
	               if( !StringUtil.isEmpty(outputObjStatus) && !"작업중".equals(outputObjStatus) && !"재작업".equals(outputObjStatus)) {
	                isOutputStatusLast = true;
	               }
	           }
	        }
	    }
	}
}
if(outputCompleteSum>0) {
    outputCompleteSum = outputCompleteSum/outputCompleteCnt;
}
%>





<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language="javascript">

function selectAll() {
    //var chkAll = document.TaskViewForm.trustOidCheckbox;
    var chkAll = document.getElementsByName('trustOidCheckbox');
    var len = chkAll.length;
    //alert('diabled:'+$('[name=trustOidCheckbox]')[0].value);
    if (len > 1) {
        for( var i = 0 ; i < len ; i++ ) {
            //alert('diabled:'+$('[name=trustOidCheckbox]')[i].value);
            //if(i!=len-1) continue;
            
            var viewCheck = $('[name=viewCheck]')[i].value;
            //alert('viewCheck :'+viewCheck +':'+viewCheck);
            if(viewCheck=='Y') {
// 	            if ( document.TaskViewForm.allCheck.checked == true ) document.TaskViewForm.trustOidCheckbox[i].checked=true;
// 	            else document.TaskViewForm.trustOidCheckbox[i].checked=false;
            }
        }
    } else if ( len == 1 ) {
    	var viewCheck = $('[name=viewCheck]').value;
        if(viewCheck=='Y') {
// 	        if ( document.TaskViewForm.allCheck.checked == true ) document.TaskViewForm.trustOidCheckbox.checked=true;
// 	        else document.TaskViewForm.trustOidCheckbox.checked=false;
        }
    }
}

function saveTaskTrustResult() {
	var execStartDate = '<%=execStartDate%>';
    var okCntList = document.getElementsByName('okCnts');
    var ngCntList = document.getElementsByName('ngCnts');

    var chkCnt = 0;
    var temp = 0;
    var isLit = false;
    var isZero = false;
    var okNg = '';
    for ( var i = 0; i < okCntList.length; i++) {
    	if($('[name=estimateDates]').eq(i).val() == ''){
    		continue;
    	}
    	
    	$('[name=ngReasons]')[i].value = $('[name=ngReasons]')[i].value.trim();
//       	alert( 'reason:'+$('[name=ngReasons]')[i].value.length+':'+charByteSize($('[name=ngReasons]')[i].value) + ':');

        if(charByteSize($('[name=ngReasons]')[i].value) > 4000) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "07282") %><%--NG사유 및 항목은 4000byte를 초과하여 입력할 수 없습니다--%>');
            $('[name=ngReasons]')[i].focus();
            return;
        }
    	
    	<%-- if(i==okCntList.length-1) {
            temp = $('[name=isOutputStatusComp]')[i].value;
            if(temp == '1') {   //산출물이 결재중이라서 저장할 수 없습니다.

                alert('<%=messageService.getString("e3ps.message.ket_message", "07283") %>산출물이 결재중이라서 저장할 수 없습니다');
                return;
            }
    	} --%>
    	
//         alert(execStartDate +' VS ' + $('[name=estimateDates]')[i].value + ' = ' + (execStartDate>$('[name=estimateDates]')[i].value));
    	if(execStartDate && execStartDate > $('[name=estimateDates]')[i].value) {
    		alert('<%=messageService.getString("e3ps.message.ket_message", "07292") %><%--평가일은 태스크의 실제 작업시간보다 빠를수 없습니다--%>');
    		$('[name=estimateDates]')[i].focus();
            return;
    	}
    	
    	temp = $('[name=estimateDates]')[i].value;
        if(temp == '') {

            alert('<%=messageService.getString("e3ps.message.ket_message", "07184") %> <%--평가일을 입력해주세요--%>');
            $('[name=estimateDates]')[i].focus();
            return;
        }
    	
    	
        temp = $('[name=okCnts]')[i].value;
	    if(isNaN(temp) == true) {
            isLit = true;

            alert('<%=messageService.getString("e3ps.message.ket_message", "07180") %> <%--OK는 숫자만 입력가능합니다--%>');
            $('[name=okCnts]')[i].focus();
            return;
	    }
        if(temp<0) {
        	isZero = true;

            alert('<%=messageService.getString("e3ps.message.ket_message", "07180") %> <%--OK는 숫자만 입력가능합니다--%>');
            $('[name=okCnts]')[i].focus();
            return;
        }
        if(temp=='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "07181") %> <%--OK 값이 없습니다--%>');
            $('[name=okCnts]')[i].focus();
            return;
        }
	    
        temp = $('[name=ngCnts]')[i].value;
        if(isNaN(temp) == true) {
            isLit = true;

            alert('<%=messageService.getString("e3ps.message.ket_message", "07182") %> <%--NG는 숫자만 입력가능합니다--%>');
            $('[name=ngCnts]')[i].focus();
            return;
        }
        if(temp<0) {
            isZero = true;

            alert('<%=messageService.getString("e3ps.message.ket_message", "07182") %> <%--NG는 숫자만 입력가능합니다--%>');
            $('[name=ngCnts]')[i].focus();
            return;
        }

        if(temp=='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "07183") %> <%--NG 값이 없습니다--%>');
            $('[name=ngCnts]')[i].focus();
            return;
        }
        chkCnt++;
    }
    
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>") ) {
        //document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?deleteIssue="+v;
        window.name="trust";
        document.forms[0].action = "/plm/ext/project/task/saveTaskTrustResult.do";
        document.forms[0].isClose.value = "Y";
        document.forms[0].method = "post";
        document.forms[0].target = "trust";
        document.forms[0].submit();

        window.returnValue = true;
        window.close();
        
    }
}
function deleteTaskTrustResult() {
    var checkedList = document.getElementsByName('trustOidChks');
//     var checkedBoxList = document.getElementsByName('trustOidCheckbox');
    var checkedDeleteRows = new Array();
//     checkedDeleteRows[totalIndex] = date[i].replace(/-/gi,"");
    
    var chkCnt = 0;
    var chksOidYesDiv = false;
    var chksOidNoDiv = false;
    var checkedDeleteRowCnt = 0; 

//  alert(checkedOid);
if(checkedList.length==1) {
    var checkedOid = $('[name=trustOidHidden]').value;
    if(checkedOid == '' || checkedOid == 'on') {
        chksOidNoDiv = true;
    }else {
        chksOidYesDiv = true;
    }
     $('[name=trustOidChks]').val('1');
}else if(checkedList.length>1) {
    var checkedOid = $('[name=trustOidHidden]')[checkedList.length-1].value;
    if(checkedOid == '' || checkedOid == 'on') {
        chksOidNoDiv = true;
    }else {
        chksOidYesDiv = true;
    }
     $('[name=trustOidChks]')[checkedList.length-1].value = '1';
}

<%
if(isOutputComp ) {
%>
alert('<%=messageService.getString("e3ps.message.ket_message", "07328") %><%--최총 차수 산출물 중 100% 완료된 산출물이 있어 삭제 할 수 없습니다--%>');
return;
<%
}
%>
    
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "01707") %><%--삭제하시겠습니까?--%>") ) {
//     	alert(chksOidYesDiv + ':' + chksOidNoDiv);
    	if(chksOidYesDiv == true) {
    		var tableId = document.getElementById("outputTable");

            if(checkedOid == '' || checkedOid == 'on') {
                tableId.deleteRow(checkedList.length);
            }
            window.name='trust';
	        //document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?deleteIssue="+v;
	        document.forms[0].action = "/plm/ext/project/task/deleteTaskTrustResult.do";
	        document.forms[0].method = "post";
 	        document.forms[0].target = "trust";
	        document.forms[0].submit();
	        
// 	        var param = "";
// 	        var onMessage = "";
// 	        var url = "/plm/ext/project/task/deleteTaskTrustResult.do";
// 	        postCallServer(url, param, onMessage, false);
    	}else {
    		var tableId = document.getElementById("outputTable");

            if(checkedOid == '' || checkedOid == 'on') {
                tableId.deleteRow(checkedList.length);
            }
            $('[name=estimateDates]').eq(i-1).removeAttr("disabled");
            $('[name=okCnts]').eq(i-1).removeAttr("disabled");
            $('[name=ngCnts]').eq(i-1).removeAttr("disabled");
            $('[name=ngReasons]').eq(i-1).removeAttr("disabled");
            $('[name=estimateDates]').eq(i-1).attr('readonly',true);
            $('[name=okCnts]').eq(i-1).attr('readonly',true);
            $('[name=ngCnts]').eq(i-1).attr('readonly',true);
            $('[name=ngReasons]').eq(i-1).attr('readonly',true);
            $('[name=estimateDates]').eq(i-1).attr('class','txt_field');
            $('[name=okCnts]').eq(i-1).attr('class','txt_field');
            $('[name=ngCnts]').eq(i-1).attr('class','txt_field');
            $('[name=ngReasons]').eq(i-1).attr('class','txt_field');
             
            location.reload();

    	}
    }
    window.returnValue = true;
    window.close();
//opener.document.location.reload();
}

function createRowTable()
{

<%
	if(taskTrustResultList!=null && taskTrustResultList.size()>0 && outputCompleteCnt==0) {
%>
    alert('<%=messageService.getString("e3ps.message.ket_message", "07290") %><%--산출물이 없는 경우 차수를 추가할 수 없습니다--%>');
    return;
<%
    }
%>

<%
    if(outputCompleteCnt>0 && outputCompleteSum!=100) {
%>
    alert('<%=messageService.getString("e3ps.message.ket_message", "07285") %><%--최총 차수 산출물의 진행률이 모두 100%가 추가할 수 있습니다--%>');
    return;
<%
    }
%>
	
	var tableId = document.getElementById("outputTable");
    var lastLen =  $('[name=estimateDates]').length;
    //alert('lastLen:'+lastLen+':'+$('[name=trustOidHidden]').eq(lastLen-1).val());
    if($('[name=trustOidHidden]').eq(lastLen-1).val()=='') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07287") %><%--차수는 하나만 추가할 수 있습니다--%>');
        return;
    }
    var index = tableId.rows.length;
    {
      var row = tableId.insertRow(index);
      var cell1 = row.insertCell(0);
      var cell2 = row.insertCell(1);
      var cell3 = row.insertCell(2);
      var cell4 = row.insertCell(3);
      var cell5 = row.insertCell(4);
      var cell6 = row.insertCell(5);
      var cell7 = row.insertCell(6);
      
      cell1.className = "tdwhiteM";
      cell2.className = "tdwhiteM";
      cell3.className = "tdwhiteM";
      cell4.className = "tdwhiteM";
      cell5.className = "tdwhiteM";
      cell6.className = "tdwhiteM";
      cell7.className = "tdwhiteM";
      
      
      
      cell1.innerHTML = "<input type='hidden' name='trustOidChks' /><input type='hidden' name='isOutputStatusComp' />"
          + "<input type='hidden' name='trustOidHidden' />"
          + "<input type='hidden' name='viewCheck' value='Y'/>"+index;     //차수
      cell2.innerHTML = "";     //결과
      cell3.innerHTML = "<input type='text' name='estimateDates' value='' class='txt_field' style='width: 80px' size='8'/>"   //평가일
      + "&nbsp;<img src='/plm/portal/images/icon_delete.gif' border='0' onclick=javascript:CommonUtil.deleteDateValue('estimateDates'); style='cursor: hand;'>"
      ;


      cell4.innerHTML = ""; //항목
      cell5.innerHTML = "<input type='text' name='okCnts' class='txt_field'  style='width: 40px' value='' onkeyup ='SetNum(this)'/>";  //OK
      cell6.innerHTML = "<input type='text' name='ngCnts' class='txt_field'  style='width: 40px' value='' onkeyup ='SetNum(this)'/>";  //NG
      cell7.innerHTML = "<textarea name='ngReasons' class='txt_field' style='height:42px;width:90%' rows='3' cols='22'>";    //NG사유및 항목

      
      //날짜 바인딩하기
      CalendarUtil.dateInputFormat('estimateDates', null, {maxDate : new Date()});
      for(var i=0;$('[name=estimateDates]').length >= 1 && i < $('[name=estimateDates]').length;i++){
    	  //alert($('[name=estimateDates]').eq(i).val());
          if(i != $('[name=estimateDates]').length -1){
        	  if($('[name=estimateDates]').eq(i).val()!='') {
	              $('[name=estimateDates]').eq(i).datepicker("destroy");
                  $('[name=estimateDates]').eq(i).attr('readonly',true);
                  $('[name=estimateDates]').eq(i).attr('class','txt_fieldRO');
                  $('[name=okCnts]').eq(i).attr('readonly',true);
                  $('[name=ngCnts]').eq(i).attr('readonly',true);
                  $('[name=ngReasons]').eq(i).attr('readonly',true);
                  $('[name=okCnts]').eq(i).attr('class','txt_fieldRO');
                  $('[name=ngCnts]').eq(i).attr('class','txt_fieldRO');
                  $('[name=ngReasons]').eq(i).attr('class','txt_fieldRO');
        	  }
          }
      }  
    }
}

//출처. http://dvlp.tistory.com/218
//글자 바이트수로 계산하기
function charByteSize(msg) {

    if (msg == null || msg.length == 0) return 0;
    var i,size = 0;
    var charCode,chr = null;

    for(i=0;i<msg.length;i++)
    {
        chr = msg.charAt(i);
        charCode = chr.charCodeAt(0);
        if (charCode <= 0x00007F) size += 1; else 
        if (charCode <= 0x0007FF) size += 2; else 
        if (charCode <= 0x00FFFF) size += 3; 
        else size += 4;
    }
    return size;
}

function unbindEstimateDate() {
<%

	if(isOutputStatusLast) {
%>
    for(var i=0;$('[name=estimateDates]').length >= 1 && i < $('[name=estimateDates]').length;i++){
        $('[name=estimateDates]').eq(i).datepicker("destroy");
    }   
<%
	}else {
%>
    for(var i=0;$('[name=estimateDates]').length >= 1 && i < $('[name=estimateDates]').length;i++){
        if(i != $('[name=estimateDates]').length -1){
            $('[name=estimateDates]').eq(i).datepicker("destroy");
        }
    }   
<%
	}
%>
}

$(document).ready(function() {
	
	CalendarUtil.dateInputFormat('estimateDates', null, {maxDate : new Date()});
	unbindEstimateDate();
});


</script>
</head>
<body class="popup-background popup-space">
  <div class="contents-wrap">
    <div class="sub-title b-space5">
      <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07172") %><%--차수관리--%>
    </div>
    <div class="b-space5 float-r" style="text-align: right">
      <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> 
      <a href="javascript:createRowTable();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07173") %><%--차수추가--%></a></span><span class="pro-cell b-right"></span></span></span>
      
      <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> 
      <a href="javascript:saveTaskTrustResult();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a>
      </span><span class="pro-cell b-right"></span></span></span>
      
      <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> 
      <a href="javascript:deleteTaskTrustResult();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a>
      </span><span class="pro-cell b-right"></span></span></span>
      
      <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a href="javascript:self.close();"
            class="btn_blue"
          ><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
    </div>
    
<form name="TaskViewForm">
<!-- Hidden Value -->
<input type=hidden name=taskOid value='<%=oid%>'>
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=popup value='<%=popup%>'>
<input type=hidden name=isClose value=''>
    
            <table border="0" cellpadding="0" cellspacing="0" width="101%">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
    <div class="float-l" style="width:100%;height:400px;overflow:scroll;overflow-x: hidden; border:3; border-style:solid; border-color:#EBEBEB">
      <table border="0" cellspacing="0" cellpadding="0" id="outputTable">
        <colgroup>
<%--           <col width="30" /> --%>
          <col width="70" />
          <col width="70" />
          <col width="150" />
          <col width="70" />
          <col width="70" />
          <col width="70" />
          <col width="225" />
        </colgroup>
        <thead>
          <tr>
<!--             <td class="tdblueM" width="30"><input type="checkbox" name="allCheck" onclick="javascript:selectAll()"/></td> -->
            <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "07125") %><%--차수--%></td>
            <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "00747") %><%--결과--%></td>
            <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "07126") %><%--평가일--%></td>
            <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "07127") %><%--항목--%></td>
            <td class="tdblueM" width="70">OK<%--OK--%></td>
            <td class="tdblueM" width="70">NG<%--NG--%></td>
            <td class="tdblueM" width="225"><%=messageService.getString("e3ps.message.ket_message", "07128") %><%--NG사유및항목--%></td>
          </tr>
        </thead>
        <tbody>
<% 
String status= ""+taskTrustResultAllList.size();
if(taskTrustResultList!=null) {
    ArrayList arrList = new ArrayList();

    String readOnlyStr = "readonly";
    String disabledStr = "disabled";
    String disabledClass = "txt_fieldRO";
    String returnFalseStr = "onclick='return false'";
    String viewCheck = "N";

//     readOnlyStr = "";
//     disabledStr = "";
//     disabledClass = "txt_field";
//     returnFalseStr = "";
    for(int i=0;i<taskTrustResultList.size();i++) {
        //신뢰성 평가 결과 리스트를 가져온다
        TrustTaskResultDTO gDto = taskTrustResultList.get(i);
        boolean isOutputStatus = false;
        if(i==taskTrustResultList.size()-1) {
            
            String taskTrustRstOid = gDto.getTrustOid();
            for(int k=0;k<taskTrustResultAllList.size();k++) {
        	   TrustTaskResultDTO gDtoTagt = taskTrustResultAllList.get(k);
        	   String taskTrustRstOidTgt = gDtoTagt.getTrustOid();
        	   String outputObjStatus = StringUtil.checkNull(gDtoTagt.getObjStatus());
        	   if(taskTrustRstOid.equals(taskTrustRstOidTgt) ) {
//         	       status += " ,"+k+":"+taskTrustRstOid.substring(taskTrustRstOid.lastIndexOf(":")) + ":"+ taskTrustRstOidTgt.substring(taskTrustRstOidTgt.lastIndexOf(":"));//k+":"+outputObjStatus;
	        	   if( !StringUtil.isEmpty(outputObjStatus) && !"작업중".equals(outputObjStatus) && !"재작업".equals(outputObjStatus)) {
	        	       isOutputStatus = true;
	        	   }
        	   }
            }
            
            if(!isOutputStatus) {
	            readOnlyStr = "";
	            disabledStr = "";
	            returnFalseStr = "";
	            disabledClass = "txt_field";
	            viewCheck = "Y";
            }
        }
		
%>              
          <tr>
<!--              <td class="tdwhiteM" style="background-color:#f7f7f7"> -->
<!--                  <div style="width:30;height:27;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"> -->
                    <input type="hidden" name="LID" value=""/>
                    <input type='hidden' name='trustOidChks'/>
                    <input type='hidden' name='trustOidHidden' value='<%=gDto.getTrustOid() %>'/>
                    <input type='hidden' name='viewCheck' value='<%=viewCheck %>'/>
                    <input type='hidden' name='isOutputStatusComp' value="<%=(isOutputStatus)?"1":"0"%>"/>
<%--                     <input type='checkbox' <%=disabledStr %> name='trustOidCheckbox' <%=returnFalseStr %> value="<%=gDto.getTrustOid() %>"/> --%>
<!--                  </div> -->
<!--              </td> -->
            <input type="hidden" name="trustOids" value="<%=gDto.getTrustOid() %>">
            <td class="tdwhiteM" width="70"><%=gDto.getRev() %><%--차수--%></td>
            <td class="tdwhiteM" width="70"><%=gDto.getAssResult() %><%--결과--%></td>
            <td class="tdwhiteM" width="150"><%--평가일--%>
            <% if("readonly".equals(readOnlyStr)) { %>
	            <%=gDto.getEstimateDate() %>
	            <input type="hidden" name="estimateDates"  value="<%=gDto.getEstimateDate() %>">
            <% }else { %>
                <input type="text" name="estimateDates" <%=readOnlyStr %> class="<%=disabledClass %>" style="width: 80px" value="<%=gDto.getEstimateDate() %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('estimateDates');" style="cursor: hand;">
            <% } %>
            </td>
            <td class="tdwhiteM" width="70"><%=Integer.parseInt(gDto.getOkCnt()) + Integer.parseInt(gDto.getNgCnt()) %><%--항목--%></td>
            <td class="tdwhiteM" width="70">
            <input type="text" name="okCnts" <%=readOnlyStr %> class="<%=disabledClass %>" style="width: 40px" value="<%=gDto.getOkCnt() %>" onkeyup ="SetNum(this)"><%--OK--%></td>
            <td class="tdwhiteM" width="70">
            <input type="text" name="ngCnts" <%=readOnlyStr %> class="<%=disabledClass %>" style="width: 40px" value="<%=gDto.getNgCnt() %>" onkeyup ="SetNum(this)"><%--NG--%></td>
            </td>
            <td class="tdwhiteM" width="225">
	            <!--기존 disable처리 주석처리함 <textarea name='ngReasons' <%=readOnlyStr %> class='<%=disabledClass %>' style='height:42px;width:90%' rows='3' cols='22'><%=StringUtil.checkNull(gDto.getNgReason()).trim() %><%--NG사유및항목--%>-->
	            <textarea name='ngReasons'  style="width:90%;" class="fm_area" rows='3' cols='22'><%=StringUtil.checkNull(gDto.getNgReason()).trim() %></textarea><%--NG사유및항목--%>
            </td>

          </tr>
<%
    }
}
%>
        </tbody>
      </table>
    </div>
	</form>
  </div>
</body>
</html>
<script language="javascript">
<%
if(isClose!=null && "Y".equals(isClose)) {
%>
window.returnValue = true;
window.close();

<%
}
%>
</script>
