<%@page import="e3ps.dms.entity.KETStandardTemplate"%>
<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>

<%@page import="ext.ket.project.task.entity.*"%>
<%@page import="ext.ket.project.task.service.*"%>
<%@page import="ext.ket.project.gate.entity.*"%>
<%@page import="ext.ket.dqm.entity.*"%>
<%@page import="e3ps.common.code.*"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.*"%>
<%@page import="wt.content.*"%>
<%@page import="wt.util.*"%>

<%@page import="java.util.*"%>
<%@page import="java.net.*"%>

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
boolean trust_isTaskMaster =false;

String evTab = request.getParameter("evTab");
if(evTab==null || "".equals(evTab)) evTab = "1";
  try{
    
  String oid =  request.getParameter("oid");
  String oldOid =  request.getParameter("compareTaskOid");

  E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
  int taskStateByTrust = task.getTaskState();
  //out.println(project.getPjtType());
  E3PSProject project2 = (E3PSProject)task.getProject();
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

String DR_PASS_CHECK  = ""; //TaskViewCommon.jsp에서 변수로 사용(신뢰성은 의미없음 dr불합격판정용)
boolean isProtoGate1Check=false;//TaskViewCommon.jsp에서 변수로 사용(Proto Gate1 체크용)
boolean isProtoGate1Pass=false;//TaskViewCommon.jsp에서 변수로 사용(Proto Gate1 체크용)

boolean trustAuth = project2.getState().toString().equals("PROGRESS") && (trust_isPM || isAdmin|| (trust_isPmo && projectData2.teamType.equals("자동차 사업부")) || (trust_isPmo2 && projectData2.teamType.equals("전자 사업부")) || (trust_isPmo3 && projectData2.teamType.equals("KETS")) 
        || trust_isTaskMaster || trust_isTaskMember);
%>
<html>
<head>
<title></title>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script type="text/javascript">


function deleteIssue(v) {
  if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
    document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?deleteIssue="+v;
    document.forms[0].method = "post";
    document.forms[0].submit();
  }
}

function outputPageTrust(opid, isview) {

    var checkedList = document.getElementsByName('trustOidChks');

    var chkCnt = 0;
    for ( var i = 0; i < checkedList.length; i++) {
        if(checkedList[i].checked){
        	
            $('[name=selectTrustOid]').val($('[name=trustOidChks]')[i].value);
            chkCnt++;
        }
    }
    if(chkCnt>1) {
    	alert('<%=messageService.getString("e3ps.message.ket_message", "07130") %><%--신뢰성 평가결과 차수를 하나만 선택해주시기 바랍니다.--%>');
    	return;
    }
    if(chkCnt<1) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07131") %><%--신뢰성 평가결과 차수를 선택해주시기 바랍니다.--%>');
        return;
    }
//     alert('trustOid:'+ $('[name=selectTrustOid]').val());
//     return;
	
    var modalHeight = '400px';
    var modalWidth = '600px';

    var url = "/plm/jsp/project/ProjectOutputCreatePage.jsp?fromPage=TaskView&taskOid=<%=oid%>&oid=" + opid + "&selectTrustOid="+$('[name=selectTrustOid]').val();

    if(isview == 'true') {
      url = "/plm/jsp/project/ProjectOutputViewPage.jsp?fromPage=TaskView&taskOid=<%=oid%>&oid=" + opid;
      modalWidth = '670px';
      modalHeight = '570px';
    }
    //openOtherName(url,"manageTask","600","360","status=1,scrollbars=no,resizable=1");

    
    //attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth:"+modalWidth+"; dialogHeight:" +modalHeight+ "; center:yes");
    openOtherName(url,"manageTask","630","450","status=1,scrollbars=no,resizable=1");
    if(typeof attache == "undefined" || attache == null) {
      attache = false;
      return;
    }

    if(isview == 'true') {
      return;
    }


    //onProgressBar();

    var param = "command=searchOutput";
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
  }

var TrustDelAuth = false;
function deleteOutputKqisLink(opid){
	var msg = "";
	if(!TrustDelAuth){
		alert('Task가 완료된 경우 삭제 할 수 없습니다.');
		return;
	}else{
		var taskStateByTrust = $("#taskStateByTrust").val();
		if(taskStateByTrust == '5'){
			msg = "Task의 완료 근거가 삭제되므로 신중히 판단하시기 바랍니다.\r\n\r\n";
		}
	}

	if ( !confirm(msg+"선택한 KQIS 연계링크가 삭제됩니다.\nKQIS 원본 데이터에는 영향을 주지 않습니다.\n삭제하시겠습니까?") ) {
	      return;
	}
	var param = "command=deleteOutput";
    param += "&oid=<%=oid%>";
    param += "&outputOid=" + encodeURIComponent(opid);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
}

function deleteOutput(opid){
    if ( !confirm("<%=messageService.getString("e3ps.message.ket_message", "01918") %><%--산출물을 삭제 합니다.\n등록된 산출물의 경우 프로젝트에서는 삭제되지만,\n문서관리에서 남아 있습니다.\n삭제하시겠습니까?--%>") ) {
      return;
    }
    var param = "command=deleteOutput";
    param += "&oid=<%=oid%>";
    param += "&outputOid=" + encodeURIComponent(opid);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
}
var kqisFlag = 0;
function addKqisPop(){
	<%-- var taskStateByTrust = $("#taskStateByTrust").val();
	if(taskStateByTrust != '5' && checkStartEndDate()){
		alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %>실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다');
		document.forms[0].taskExecStartDate.focus();
	    return;
	} --%>
	
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=800,height=460";
    var oid = '<%=oid%>';
    var url = '/plm/ext/project/task/addKqisSearchForm.do?oid='+oid;
    getOpenWindow2(url,oid, '800', '460', opts);

}

function kqisLinkPop(url){
	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;
    var rest = "width=1050,height="+ (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='+ toppos;
    var pop = '';
    if(kqisFlag == 0){
        pop = window.open(url, '', (opts + rest));
        setTimeout(function(){
            window.open(url, '', (opts + rest));
            pop.close();
        }, 1000);
        //window.open('/plm/ext/project/purchase/kqisDocForm.do?url='+encodeURIComponent(url),'', (opts + rest));   
    }else{
        
        window.open(url, '', (opts + rest));
    }
    
    kqisFlag++;
}

function addTaskOutputLevelPop(){
	if(checkStartEndDate()){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
      document.forms[0].taskExecStartDate.focus();
      return;
    }

    var url = '/plm/ext/project/task/addTaskOutputLevelForm.do?oid=<%=oid%>';
    
    var attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth:730px; dialogHeight:500px; center:yes");
    if(attache==true) {
    	//goReloadPage();
    	document.location.href = '/plm/jsp/project/TaskTrustView.jsp?oid=<%=oid%>&popup=popup';
    }
//     window.open(url,'level',"status=1, menu=no, width=730, height=500");
}

function kqisTrustReload(){
	document.location.href = '/plm/jsp/project/TaskTrustView.jsp?oid=<%=oid%>&popup=popup';
}

function resizeOutputTable(){
    var margin = 30;
    var screenWidth = $('body').width();
    screenWidth = document.body.clientWidth;
    //alert(screenWidth);
    if(screenWidth==791) margin = 20;
    var resizeWidth = screenWidth - margin;
    $('#outputTable').parent().css('width',resizeWidth+'px');
}

function updateGateTarget(cb, outputOid) {
    
    //alert(outputOid);
    //alert('checked:'+cb.checked+',value:'+cb.value+',output:'+outputOid);
    var gCheckList = document.getElementsByName('gatechecks');
    
    var tgtStr = '';
    if(cb.checked) {
        tgtStr = '1';
    }else {
        tgtStr = '0';
    }

    $.ajax( {
        url : "/plm/ext/project/task/updateProjectOutputGateCheck.do?outputOid="+outputOid+"&gateStr="+tgtStr,   //String ecoOid, String outputOid 파라미터 필요함(ProjectTaskCompController)
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

</script>
</head>
<body style="padding-right:15px;padding-left:15px;" onresize="resizeOutputTable()" onload="resizeOutputTable()">
<form name="TaskViewForm">
<!-- Hidden Value -->
<input type=hidden name=taskOid value='<%=oid%>'>
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=cmd >
<input type=hidden name=issueOid>
<input type=hidden name=selectTrustOid>
<input type=hidden name=popup value='<%=popup%>'>
<input type=hidden name=ExeDate>

<!-- //Hidden Value -->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top">
    <!-------------------------------------- 상단버튼 시작 //-------------------------------------->
       <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
          <td valign="top" width="">
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
      
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="space20"></td>
              </tr>
            </table>
            <%
            List<TrustTaskResultDTO>  taskTrustResultList = ProjectTaskCompHelper.service.getTaskTrustResultList(oid);
            int listSize = (taskTrustResultList==null)?0:taskTrustResultList.size();
            %>
            <%if(listSize == 0){ %>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07124") %><%--신뢰성 평가결과--%></td>
                <td align="right">
                  <% if( project2.getState().toString().equals("PROGRESS") && (trust_isPM || isAdmin|| (trust_isPmo && projectData2.teamType.equals("자동차 사업부")) || (trust_isPmo2 && projectData2.teamType.equals("전자 사업부")) || (trust_isPmo3 && projectData2.teamType.equals("KETS")) 
                      || trust_isTaskMaster || trust_isTaskMember)) { %>

                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:addKqisPop();" class="btn_blue">의뢰서 등록</a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                <%
                  }
                %>
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
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="kqisTable" style="table-layout:fixed">
                <colgroup>
                    <col width="5%">
                    <col width="3.5%">
                    <col width="3.5%">
                    <col width="4%">
                    <col width="5%">        
                    <col width="4%">
                    <col width="4%">
                    <col width="5%">
                    <col width="5%">
                    <col width="5%">
                    <col width="5%">
                    <col width="5%">
                    <col width="2%">
                </colgroup>
                <thead>
                <tr>
                    <td class="tdblueM" rowSpan="2">관리번호</td>
                    <td class="tdblueM" rowSpan="2">진행단계</td>
                    <td class="tdblueM" rowSpan="2">시험결과</td>
                    <td class="tdblueM" rowSpan="2">이상발생<br>(완료/전체)</td>
                    <td class="tdblueM" rowSpan="2">품번</td>
                    <td class="tdblueM" rowSpan="2">의뢰자</td>
                    <td class="tdblueM" rowSpan="2">시험자</td>
                    <td class="tdblueM" rowSpan="2">의뢰일</td>
                    <td class="tdblueM" colSpan="2">시험시작일</td>
                    <td class="tdblueM" colSpan="2">시험종료일</td>
                    <td class="tdblueM" rowSpan="2">삭제</td>
                </tr>
                    <td class="tdblueM">시작예정</td>
                    <td class="tdblueM">실제시작</td>
                    <td class="tdblueM">종료예정</td>
                    <td class="tdblueM">실제종료</td>
                <tr>
                </tr>
                </thead>
                <tbody id="kqisList"></tbody>
            </table>
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="space20"></td>
              </tr>
            </table>
            <%} %>
            <div id="oldTrust">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07124") %><%--신뢰성 평가결과--%></td>
                <td align="right">
                  <% if( project2.getState().toString().equals("PROGRESS") && (trust_isPM || isAdmin|| (trust_isPmo && projectData2.teamType.equals("자동차 사업부")) || (trust_isPmo2 && projectData2.teamType.equals("전자 사업부")) || (trust_isPmo3 && projectData2.teamType.equals("KETS")) 
                      || trust_isTaskMaster || trust_isTaskMember)) { %>

                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:addTaskOutputLevelPop();" class="btn_blue">차수관리<%--차수관리--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td width="5"> 
                      </td>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:outputPageTrust('', 'false');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01738") %><%--산출물추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
				<%
                  }
				%>
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
<%
boolean isPmo = CommonUtil.isMember("자동차PMO");
boolean isPmo2 = CommonUtil.isMember("전자PMO");
boolean isPmo3 = CommonUtil.isMember("KETS_PMO");


TrustTaskResultDTO gDto2 =null;
if(taskTrustResultList!=null && taskTrustResultList.size()>0) {
    
	gDto2 = taskTrustResultList.get(0);
}

%>
            <table border="0" cellpadding="0" cellspacing="0" width="102%">
              <tr>
                <td>
        <input type="hidden" name="outputTrustCnt" value="1"/>
        <input type=hidden name='listSize' id='listSize' value='<%=listSize%>'>
        <input type=hidden name='taskStateByTrust' id='taskStateByTrust' value='<%=taskStateByTrust%>'>
        <input type=hidden name='isAdmin' id='isAdmin' value='<%=isAdmin%>'>
        <input type=hidden name='trustAuth' id='isAdmin' value='<%=trustAuth%>'>
            <!---------------------- 산출물 시작 --------------------->
            <div style="width:100%;height:height:200px;overflow:scroll;overflow-x:auto;overflow-y:auto; border:3; border-style:solid; border-color:#EBEBEB; scrollbar-face-color:#ccc;">
<!--             <div style="height:200px;overflow:scroll;overflow-x:auto;overflow-y:auto; border:3; border-style:solid; border-color:#EBEBEB; scrollbar-face-color:#ccc;"> -->
            <table border="0" cellspacing="0" cellpadding="0" width="1420px" id="outputTable" style="table-layout:fixed">
                <col width="30">
                <col width="40">
                <col width="40">
                <col width="80">
                <col width="40">
                <col width="40">
                <col width="40">
                <col width="225">
                <col width="100">
                <col width="40">
                <col width="30">
                <col width="80">
                <col width="80">
                <col width="40">
                <col width="60">
                <col width="100">
                <col width="30">
                <col width="120">
                <col width="50">
                <col width="50">
                <col width="40">
              <tr>
                <td class="tdblueM" width="30"><input type="checkbox" name="allCheck" onclick="selectAll(document.forms[0].allCheck, document.forms[0].trustOidChks)"/></td>
                <td class="tdblueM" width="40"><%=messageService.getString("e3ps.message.ket_message", "07125") %><%--차수--%></td>
                <td class="tdblueM" width="40"><%=messageService.getString("e3ps.message.ket_message", "00747") %><%--결과--%></td>
                <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "07126") %><%--평가일--%></td>
                <td class="tdblueM" width="40"><%=messageService.getString("e3ps.message.ket_message", "07127") %><%--항목--%></td>
                <td class="tdblueM" width="40">OK<%--OK--%></td>
                <td class="tdblueM" width="40">NG<%--NG--%></td>
                <td class="tdblueM" width="225" nowrap><%=messageService.getString("e3ps.message.ket_message", "07128") %><%--NG사유및항목--%></td>
                <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td class="tdblueM" width="40"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
                <td class="tdblueM" width="30"><%=messageService.getString("e3ps.message.ket_message", "03127") %><%--필수--%></td>
                <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02856") %><%--최종작성자--%></td>
                <td class="tdblueM" width="40"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                <td class="tdblueM" width="30"><%=messageService.getString("e3ps.message.ket_message", "02957") %><%--파일--%></td>
                <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
                <td class="tdblueM" width="50">Gate<%--Gate--%></td>
                <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "07129") %><%--Template--%></td>
<%
  if(isEdit2 || true) {
%>
                <td class="tdblueM0" width="40"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
<%
  }
%>
              </tr>
              
<%

  ProjectOutput output = null;
  ProjectOutputData outputData = null;
  PeopleData peopleData = null;
  WTUser outputUser = null;
  String deptName = "";
  String outputChargerName = "";
  String version = "";
  String lastVersion = "";
  String versionOid = "";
  String lastVersionOid = "";

  String state = "";
  String stateCode = "";
  String modifyDate = "";
  String peopleOID = "";
  
  String opDtObjType = "";
  String opDtIsPrimary = "";
  String opDtOid = "";
  boolean opDtIsRegistry = false;
  String opDtLocation = "";
  String opDtDocOid = "";
  String opDtDocName = "";
  String opDtSubjectType = "";
  String opDtAttatchUrl = "";
  RevisionControlled opDtDocument = null;
  RevisionControlled opDtLastDocument = null;
  String opDtName = "";

  boolean isOwner = false;
  boolean isState = false;
  Object[] opObj = null;
  String subjectCheck = "0";

  //QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);

//   while ( outputQr.hasMoreElements() ) {
    //opObj = (Object[])outputQr.nextElement();
    //output = (ProjectOutput)opObj[0];
if(taskTrustResultList!=null) {

  ArrayList arrList = new ArrayList();
  HashMap hMap = new HashMap();
  int rCnt = 1;
  String trOidTg = "";
  int dupliRow = 0;
  String dupliOid = "";
  boolean isDupli = false;
  for(int a=0;a<taskTrustResultList.size();a++) {
    //신뢰성 평가 결과 리스트를 가져온다
    TrustTaskResultDTO gDto = taskTrustResultList.get(a);
    if(trOidTg.equals(gDto.getTrustOid())) {
	
	    rCnt++;
	    isDupli = true;
    }else {
	   if(!StringUtil.isEmpty(dupliOid)) {
	       HashMap mImsi =  (HashMap) arrList.get(dupliRow);
	       mImsi.put("cnt", ""+rCnt);
	   }
// 	       out.println(dupliRow+":"+rCnt);
	    rCnt = 1;
	    dupliRow = a;
	    dupliOid = gDto.getTrustOid();
	    trOidTg = gDto.getTrustOid();
	    isDupli = false;
    }
    hMap = new HashMap();
    hMap.put("oid", trOidTg);
    if(isDupli) {
	     hMap.put("cnt", ""+0);
		 
	    //out.println(a+":"+trOidTg+ ":"+0);
    }
    else {
	    hMap.put("cnt", ""+rCnt);
// 	    out.println(a+":"+rCnt);
    }
    
    arrList.add(a, hMap);
    
    if(a==(taskTrustResultList.size()-1)) {
        HashMap mImsi2 =  (HashMap) arrList.get(dupliRow);
        mImsi2.put("cnt", ""+(rCnt+1));
        //out.println(mImsi2.get("cnt"));
//      out.println(a+":"+hMap.get("cnt")+":"+dupliRow +":"+taskTrustResultList.size()+":"+arrList.size());
     }
    
  }
  
  for(int i=0;i<taskTrustResultList.size();i++) {
    //신뢰성 평가 결과 리스트를 가져온다
    TrustTaskResultDTO gDto = taskTrustResultList.get(i);
    String outputOidStr = StringUtil.checkNull(gDto.getOutputOid());
    
    opDtObjType = "";
    opDtIsPrimary = "";
    opDtOid = "";
    opDtIsRegistry = false;
    opDtLocation = "";
    opDtDocOid = "";
    opDtDocName = "";
    opDtSubjectType = "";
    opDtAttatchUrl = "";
    
    String fileUrl = "";
    String icon = "";
    ContentInfo info = null;
    ContentItem item = null;

    String loginOid = "";
    String security = "";
    Boolean isSecu = false;
    String objType = "";
    boolean isEtc = "ETC".equals(objType);
    KETProjectDocument docu = null;
    EPMDocument epm = null;
    FormatContentHolder cholder = null;
    ContentInfo cinfo = null;
    subjectCheck = "0";
    KETDqmRaise dqmRaiseObj = null;
    KETDqmHeader dqmHeaderObj = null;

    boolean isMoldProject = false;
    if(project2 instanceof MoldProject) {
      isMoldProject = true;
    }
    
    String outputNameStr = "";

    //산출물 객체(output)를 업데이트
    if(!StringUtil.isEmpty(outputOidStr)) {
	//     output = (ProjectOutput)CommonUtil.getObject("e3ps.project.PorjectOutput:"+outputOidStr);
	    ReferenceFactory rf = new ReferenceFactory();
	    output = (ProjectOutput)rf.getReference(outputOidStr).getObject();
    }else {
	   output = null;
    }
    
    if(output!=null) {
	
	    outputUser = output.getOwner() == null? null:(WTUser)output.getOwner().getPrincipal();
	    outputData = new ProjectOutputData (output);
	    if("대상".equals(output.getSubjectType())) {
	        subjectCheck = "1";
	    }
	    
	    outputNameStr = StringUtil.checkNull(outputData.name);

	    //html오류방지를 위해 변수로 받아서 처리
	    opDtObjType = outputData.objType;
	    opDtIsPrimary = outputData.isPrimary;
	    opDtOid = outputData.oid;
	    opDtIsRegistry = outputData.isRegistry;
	    opDtLocation = outputData.location;
	    opDtDocument = outputData.document;
	    opDtLastDocument = outputData.LastDocument;
	    opDtName = StringUtil.checkNull(outputData.name);
// 	    if(opDtName.length()>10) {
// 		  opDtName = opDtName.substring(0, 10) + "..";
// 	    }

	    opDtSubjectType = outputData.subjectType;
	    opDtDocOid = output.getOutputDocOid();
        opDtDocName = output.getOutputDocName();
        KETStandardTemplate ketStTmpl = (KETStandardTemplate)CommonUtil.getObject(opDtDocOid);
        
        if (ketStTmpl instanceof FormatContentHolder) {
	        FormatContentHolder holder = (FormatContentHolder) ketStTmpl;
	        ContentInfo info2 = ContentUtil.getPrimaryContent(holder);
// 	        ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());
	        if(info2!=null && info2.getDownURL()!=null)  opDtAttatchUrl = info2.getDownURL().toString();
        }

        if("".equals(opDtAttatchUrl)) {
            if(ketStTmpl!=null) {
		        QueryResult rs = ContentHelper.service.getContentsByRole(ketStTmpl, ContentRoleType.SECONDARY);
		        while (rs.hasMoreElements()) {
		            ContentHolder holder = (ContentHolder)ketStTmpl;
	 		        ContentItem fileContent = (ContentItem) rs.nextElement();
	 		        ContentInfo info3 = ContentUtil.getContentInfo(holder, fileContent);
	 		        if(info3!=null && info3.getDownURL()!=null)  opDtAttatchUrl = info3.getDownURL().toString();
		        }
            }
        }

	    
	    if(output.getObjType() != null){
	      objType = output.getObjType();
	      isEtc = "ETC".equals(objType);
	    }
	    outputChargerName = "&nbsp;";
	    deptName = "&nbsp;";
	    version = "&nbsp;";
	    lastVersion = "&nbsp;";
	    state = "&nbsp;";
	    modifyDate = "&nbsp;";
	    peopleOID = "&nbsp;";
	    peopleData = null;
	
	
          WTUser duser = null;
	    if(!isEtc && opDtIsRegistry) {
          ObjectData data = null;
          WTUser sessionUser  = (WTUser) SessionHelper.manager.getPrincipal();
          // 산출물 열람 권한 추가 시작 #####################################
          if(CommonUtil.isMember("자동차PMO", sessionUser)) {
             isSecu = true;
          }else if(CommonUtil.isMember("자동차사업부_임원", sessionUser)) {
            isSecu = true;
          }else if(CommonUtil.isMember("전자사업부_임원", sessionUser)) {
            isSecu = true;
          }
          // 산출물 열람 권한 추가 종료 ########################################
           
	      if("ECO".equals(objType)) {
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
	              versionOid = CommonUtil.getOIDString(outputData.changeOrder);
	              lastVersion = prodChangeOrderObj.getVersionIdentifier().getValue();
	              lastVersionOid = CommonUtil.getOIDString(prodChangeOrderObj);
	              outputNameStr = prodChangeOrderObj.getEcoName();
	          }else if(moldChangeOrderObj!=null) {
	              versionOid = CommonUtil.getOIDString(outputData.changeOrder);
	              lastVersion = moldChangeOrderObj.getVersionIdentifier().getValue();
	              lastVersionOid = CommonUtil.getOIDString(moldChangeOrderObj);
	              outputNameStr = moldChangeOrderObj.getEcoName();
	          }
	          state = outputData.changeOrder.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
	          modifyDate = DateUtil.getDateString(outputData.changeOrder.getPersistInfo().getModifyStamp(), "D");
	          modifyDate = modifyDate.substring(2, 10);
	          
	          data = null;
	          outputChargerName = outputData.changeOrder.getCreatorFullName();
	          duser = (WTUser)outputData.changeOrder.getCreator().getPrincipal();
	          
	          loginOid = CommonUtil.getOIDString(sessionUser);
	          if(loginOid.equals(outputData.changeOrder.getCreator().toString())){
	            isSecu = true;
	          }
	          if(isSecu==false){
	            isSecu = WorkflowSearchHelper.manager.userInApproval(outputData.changeOrder,sessionUser.getName());
	          }
	          //out.println(isSecu);
	      }else if("TRY".equals(objType)) {
	          
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
	              versionOid = CommonUtil.getOIDString(outputData.tryCondition);
	              lastVersionOid = CommonUtil.getOIDString(moldTryConditionObj);
	              outputNameStr = moldTryConditionObj.getName();
	          }else if(pressTryConditionObj!=null) {
	              versionOid = CommonUtil.getOIDString(outputData.tryCondition);
	              lastVersionOid = CommonUtil.getOIDString(pressTryConditionObj);
	              outputNameStr = pressTryConditionObj.getName();
	          }
	          state = outputData.tryCondition.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
	          modifyDate = DateUtil.getDateString(outputData.tryCondition.getPersistInfo().getModifyStamp(), "D");
	          modifyDate = modifyDate.substring(2, 10);
	          
	          data = null;
	          outputChargerName = outputData.tryCondition.getCreatorFullName();
	          duser = (WTUser)outputData.tryCondition.getCreator().getPrincipal();
	          
	          loginOid = CommonUtil.getOIDString(sessionUser);
	          if(loginOid.equals(outputData.tryCondition.getCreator().toString())){
	            isSecu = true;
	          }
	          if(isSecu==false){
	            isSecu = WorkflowSearchHelper.manager.userInApproval(outputData.tryCondition,sessionUser.getName());
	          }
	          //out.println(isSecu);
	      }else if("QLP".equals(objType)) {
	              
		        
	            ext.ket.dqm.entity.KETDqmDTO dqmDto = ext.ket.project.task.service.ProjectTaskCompHelper.service.getOutputQLP(output);
	            String raiseOidStr =  dqmDto.getRaiseOid();
	            dqmRaiseObj = (raiseOidStr==null || "".equals(raiseOidStr))?null:(KETDqmRaise)CommonUtil.getObject(raiseOidStr);
	            dqmHeaderObj = (dqmDto==null || "".equals(dqmDto.getOid()))?null:(KETDqmHeader)CommonUtil.getObject(dqmDto.getOid());
	            
	            if(dqmDto!=null) {
	                versionOid = CommonUtil.getOIDString(dqmRaiseObj);
	                lastVersionOid = CommonUtil.getOIDString(dqmRaiseObj);
	                lastVersion = "";
	            }
	            outputNameStr = dqmHeaderObj.getProblem();
// 	            state = dqmRaiseObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
// 	            stateCode = dqmRaiseObj.getLifeCycleState().toString();
	            state = dqmHeaderObj.getDqmStateName();
                stateCode = dqmHeaderObj.getDqmStateCode();
                modifyDate = DateUtil.getDateString(dqmRaiseObj.getPersistInfo().getModifyStamp(), "D");
	            modifyDate = modifyDate.substring(2, 10);
	            
	            data = null;
	            outputChargerName = dqmRaiseObj.getCreatorFullName();
	            duser = (WTUser)dqmRaiseObj.getCreator().getPrincipal();
	            if(duser != null){
	                //peopleData = new PeopleData(outputUser);
	                peopleData = new PeopleData(duser);
	                deptName = peopleData.departmentName;
	                //out.println(peopleData.name);
	                peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
	            }
	              
	            loginOid = CommonUtil.getOIDString(sessionUser);
	            if(loginOid.equals(dqmRaiseObj.getCreator().toString())){
	              isSecu = true;
	            }
	            if(isSecu==false){
	              isSecu = WorkflowSearchHelper.manager.userInApproval(dqmRaiseObj, sessionUser.getName());
	            }
	         
	      }else {    //문서, 도면인 경우
			  if(outputData.LastDocument instanceof KETProjectDocument)
	          {
	              outputNameStr =  StringUtil.checkNull(((KETProjectDocument)outputData.LastDocument).getTitle() );
	          }else if(outputData.LastDocument instanceof EPMDocument) {
	              outputNameStr =  StringUtil.checkNull(((EPMDocument)outputData.LastDocument).getName() );
	          }
		      version = outputData.currentDocument.getVersionDisplayIdentifier().toString();
		      versionOid = CommonUtil.getOIDString(outputData.currentDocument);
		      lastVersion = opDtLastDocument.getVersionDisplayIdentifier().toString();
		      lastVersionOid = CommonUtil.getOIDString(opDtLastDocument);
		      state = outputData.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
		      modifyDate = DateUtil.getDateString(outputData.currentDocument.getPersistInfo().getModifyStamp(), "D");
		      modifyDate = modifyDate.substring(2, 10);
		      data = new ObjectData(opDtLastDocument); /* 임영근g 최신것으로 바꿔주길 요청 */
		      outputChargerName = outputData.currentDocument.getCreatorFullName();
		      //duser = (WTUser)outputData.currentDocument.getCreator().getPrincipal(); /* 최종작성자로 변경*/
		      duser = (WTUser)opDtLastDocument.getCreator().getPrincipal();
		      //out.println(duser);
		      if(duser != null){
		        //peopleData = new PeopleData(outputUser);
		        peopleData = new PeopleData(duser);
		        deptName = peopleData.departmentName;
		        //out.println(peopleData.name);
		        peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
		      }
		      
		      loginOid = CommonUtil.getOIDString(sessionUser);
		      if(loginOid.equals(opDtLastDocument.getIterationInfo().getCreator().toString())){
		        isSecu = true;
		      }
		      
		      if(isSecu==false){
		        isSecu = WorkflowSearchHelper.manager.userInApproval(opDtLastDocument,sessionUser.getName());
		      }
		
		      //out.println(opDtLastDocument);
		      if(opDtLastDocument instanceof KETProjectDocument)
		      {
			    security =  StringUtil.checkNull(((KETProjectDocument)opDtLastDocument).getSecurity());
		        if(!security.equals("S2")){
		          isSecu = true;
		        }
		      }
	      }
	
	      if(isSecu==false){
	        isSecu = ProjectUserHelper.manager.isProjectRoleUser((TemplateProject)outputData.project);
	      }
	       if(isSecu==false){
	         isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject)outputData.project);
	       }
	
	      if(CommonUtil.isAdmin())
	      {
	        isSecu = true;
	      }
	
	      if(project2.getPjtType() == 3)
	      {
	        isSecu = true;
	      }
	
	      if(data!=null) {
		  
		      fileUrl = data.getFileUrl();
		
		      icon = data.getIcon();
		
		      FormatContentHolder holder = (FormatContentHolder) ContentHelper.service.getContents((ContentHolder) opDtLastDocument);
		      item = holder.getPrimary();
		
		      if(holder != null){
		          info = ContentUtil.getContentInfo(holder , item);
		          if(item != null){
			          icon = info.getIconURLStr();
			          icon = icon.substring(icon.indexOf("<img"));
		          }
		      }
		
		      if(fileUrl == null && data.getSecondary() != null){
		        for(int ii = 0; ii < data.getSecondary().length; ii++){
		          Object oo[] = (Object[])data.getSecondary()[ii];
		            if(oo == null){
		              continue;
		            }else{
		               fileUrl = (String)oo[0];
		              break;
		            }
		        }
		
		      }
	      }
	      
	      FormatContentHolder holder = null;
	      if("ECO".equals(objType) || "TRY".equals(objType) || "QLP".equals(objType)) {

	      }else {
	          holder = (FormatContentHolder) ContentHelper.service.getContents((ContentHolder) outputData.LastDocument);
	          item = holder.getPrimary();
	          if(holder != null){
                info = ContentUtil.getContentInfo(holder , item);
                if(item != null){
                    icon = info.getIconURLStr();
                    icon = icon.substring(icon.indexOf("<img"));
                }
	          }
	      }
	      
          if(fileUrl == null){
              fileUrl = "";
              icon = "&nbsp;";
          }

	    }

	    //out.println(fileUrl);
	    isOwner = false;
	    if(outputUser != null && (wtuser2.getName()).equals(outputUser.getName())) {
	      isOwner = true;
	    }

    }else {
	peopleData = null;
 	   outputData = null;
    }

//     if(outputNameStr!=null && outputNameStr.length()>10) {
//       outputNameStr = outputNameStr.substring(0, 10) + "..";
//     }

%>
              <tr>
              


<% 
HashMap getMap = (HashMap)arrList.get(i);
String rowspanCntStr = StringUtil.checkNull((String)getMap.get("cnt"));
int rowspanCnt = Integer.parseInt(rowspanCntStr);
//   out.println("["+i+"]rowspanCntStr:"+rowspanCntStr);
	if(rowspanCnt>0) {
%>              

                 <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" style="background-color:#f7f7f7;text-align:center;text-valign:middle">
                        <input type="hidden" name="LID" value="<%=lastVersionOid%>"/>
                        <input type='checkbox' name='trustOidChks' value='<%=gDto.getTrustOid() %>'/>
                 </td>
                <input type="hidden" name="trustOids" value="<%=gDto.getTrustOid() %>">
                <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" style="text-align:center;width:30;"><%=gDto.getRev() %><%--차수--%></td>
                <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" style="text-align:center;width:30;"><%=gDto.getAssResult() %><%--결과--%></td>
                <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" style="text-align:center;width:60;"><%--평가일--%>
                    <%=gDto.getEstimateDate() %>
                </td>
                <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" style="text-align:center;width:30;"><%=Integer.parseInt(gDto.getOkCnt()) + Integer.parseInt(gDto.getNgCnt()) %><%--항목--%></td>
                <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" style="text-align:center;width:30;"><%=gDto.getOkCnt() %><%--OK--%></td>
                <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" style="text-align:center;width:30;"><%=gDto.getNgCnt() %><%--NG--%></td>
                </td>
                <td class="tdwhiteL" rowspan="<%=rowspanCntStr%>" width="225" style="text-align:left;text-valign:middle" nowrap>
                <div style="width: 225px;">
                <%=StringUtil.checkNull(gDto.getNgReason()).trim() %><%--NG사유및항목--%>
                </div>
                </td>
<%
	}
%>


<%
    if(outputData!=null && outputData.document!=null) {

        
        if(isMoldProject) {
           %>
                 <td class="tdwhiteL" title="<%=outputData.name%>" valign="middle" style="text-align:left;width:100;">
                    <div class="ellipsis" style="width:100%;">
                      <nobr>
                    <% 
                        if(opDtDocument!=null) {
                            if (lastVersionOid != null){
                    %>
                        <a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')"><%=opDtName%></a>
                    <%      }else { %>
                        <a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(opDtDocument)%>')"><%=opDtName%></a>
                    <%      
                            }
                        }else if(objType.equals("ETC")){  %>
                        <a href="#" onClick="javascript:openCompleteReson('<%=opDtOid%>');"><%=opDtName%> </a>
                    <%  }else { %>
                        <%=opDtName%>
                    <%      } 
                    %>
                      </nobr></div>
                 </td>
     <%
         }else{

             if(lastVersionOid != null) {
               if(opDtObjType == "문서") {
                   docu = (KETProjectDocument)CommonUtil.getObject(lastVersionOid);
                   cholder = (FormatContentHolder)docu;
                   cinfo = ContentUtil.getPrimaryContent(cholder);
               }else if(opDtObjType == "도면" && lastVersionOid.indexOf("KETProjectDocument")>=0) {
	        	   docu = (KETProjectDocument)CommonUtil.getObject(lastVersionOid);
                   cholder = (FormatContentHolder)docu;
                   cinfo = ContentUtil.getPrimaryContent(cholder);
               }else if(opDtObjType == "도면") {
                   epm = (EPMDocument)CommonUtil.getObject(lastVersionOid);
                   cholder = (FormatContentHolder)epm;
                   cinfo = ContentUtil.getPrimaryContent(cholder);
               }
       %>
            <% 
                if(opDtDocument!=null) {
                    if (lastVersionOid != null){
            %>
                  <td class="tdwhiteL" title="<%=outputNameStr%>"  style="text-align:left;text-valign:middle;width:100">
                      <nobr>
                        <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')"><%=outputNameStr%></a></div>
            <%      }else { %>
                  <td class="tdwhiteL" title="<%=outputNameStr%>"  style="text-align:left;text-valign:middle;width:100">
                      <nobr>
                        <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(opDtDocument)%>')"><%=outputNameStr%></a></div>
            <%      
                    }
                }else if(objType.equals("ETC")){  %>
                  <td class="tdwhiteL" title="<%=outputNameStr%>"  style="text-align:left;text-valign:middle;width:100">
                      <nobr>
                <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:openCompleteReson('<%=opDtOid%>');"><%=opDtName%> </a></div>
            <%  
                }else { %>
            
                  <td class="tdwhiteL" title="<%=outputNameStr%>"  style="text-align:left;text-valign:middle;width:100">
                      <nobr>
                        <div class="ellipsis" style="width:100%;"><%=outputNameStr%></div>
            <%  } 
            %>
                      </nobr>
                  </td>
      <%    }
         }
    }else if(outputData!=null){%>
                      
                <td class="tdwhiteL" title="<%=outputNameStr%>"  style="text-align:left;text-valign:middle;width:100">
                      <nobr>
                    <% 
                        if(opDtDocument!=null) {
                            if (lastVersionOid != null){
                    %>
                        <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')"><%=opDtName%></a></div>
                    <%      }else { %>
                        <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(opDtDocument)%>')"><%=opDtName%></a></div>
                    <%      
                            } %>
                        
                    <%      
                        }else if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETProdChangeOrder){  %>
                        <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:openECO('<%=outputData.changeOrder%>', 'PROD');"><%=outputNameStr%> </a></div>
		            <%      
		                }else if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETMoldChangeOrder){  %>
                        <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:openECO('<%=outputData.changeOrder%>', 'MOLD');"><%=outputNameStr%> </a></div>

		            <%      
		                }else if(objType.equals("TRY") && outputData.tryCondition!=null && outputData.tryCondition  instanceof KETTryPress) {  %>
		                <a href="#" onClick="javascript:openView('<%=outputData.tryCondition%>');"><div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div> </a>
		                
		            <%      
		                }else if(objType.equals("QLP")) {
		                    if(dqmHeaderObj==null) {  %>
		                <div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div>
		            <%      }else {%>
		                <a href="#" onClick="javascript:openDQM('<%=CommonUtil.getOIDString(dqmHeaderObj)%>');"><div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr %></nobr></div> </a>
		            <%      }
		                }else if(objType.equals("ETC")){
	                %>
                        <div class="ellipsis" style="width:100%;"><a href="#" onClick="javascript:openCompleteReson('<%=opDtOid%>');"><%=opDtName%> </a></div>
                          <%

                          String completeReson =  output.getComplete_reason();
                          if(completeReson == null){
                            completeReson = "";
                          }
                          completeReson = completeReson.trim();
                          
                          if("ECO_No.기입".equals(opDtName)){
		                      String isProduct = "";
		                      if(project2 instanceof ProductProject){
		                        isProduct = "P";
		                      }
		                      String ecoOid = EcmSearchHelper.manager.getEcoObjectOid(completeReson);
		                  %>
		                    <%if(ecoOid != null && ecoOid.length() > 0){ %>
		                      <%if("제품도".equals(taskData2.taskName)){ %>
		                      <a href="javascript:;" onClick="javascript:viewEcoPop('<%=ecoOid %>', 'P');"> [<%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%>]</a>
		                      <%}else{ %>
		                      <a href="javascript:;" onClick="javascript:viewEcoPop('<%=ecoOid %>', '');"> [<%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%>]</a>
		                      <%} %>
		                    <%} %>
		                  <%} %>
		                  
                    <%  }else { %>
                        
                        <div class="ellipsis" style="width:100%;"><nobr><%=opDtName%></nobr></div>
                    <%      } 
                    %>
                      </nobr>
                  </td>
    <%}else { %>
                <td class="tdwhiteL" title=""  style="text-align:left;text-valign:middle;width:100">
                  </td>

    <%} %>
                  <%--타입--%>
                  <td class="tdwhiteL" title="<%=opDtObjType%>" style="text-align:center;width:60;">
                  <% if("QLP".equals(opDtObjType)) opDtObjType = "품질"; %>
                        <a href="#" onClick="javascript:outputPage('<%=opDtOid%>', '<%=opDtIsRegistry%>');"><%=opDtObjType%></a>
                  </td>
                  <%--필수--%>
                  <td class="tdwhiteL" title="<%=opDtIsPrimary%>" style="text-align:center;width:30;">
                      <nobr><%=opDtIsPrimary%></nobr>
                  </td>
                  <%--문서분류--%>
                  <%if(opDtObjType.equals("문서")){ 
                      String outputLocationStr = outputData.location;
                      if(!StringUtil.isEmpty(outputLocationStr)) outputLocationStr = outputLocationStr.substring(outputLocationStr.lastIndexOf("/"));
                  %>
                  <td class="tdwhiteL" title="<%=(opDtLocation==null)?"":opDtLocation%>" style="text-align:center;width:60;">
                      <div class="ellipsis" style="width:100%;"><nobr><%=(opDtLocation==null)?"-":outputLocationStr%></nobr></div>
                  </td>
                  <%}else{ %>
                  <td class="tdwhiteL" title="" style="text-align:center;width:60;">
                  <div class="ellipsis" style="width:100%;">
                  <%if(opDtDocument != null && project2.getPjtType() == 3){%><a href="#" onClick="javascript:doViewEpm('<%=project2.getPjtNo()%>')">[<%=messageService.getString("e3ps.message.ket_message", "00926") %><%--관련도면--%>]</a><%}%>
                  &nbsp;</div>
                  </td>
                  <%} %>
                  <%--최종작성자--%>
                  <td class="tdwhiteL" title="<%=deptName%>" style="text-align:center;text-valign:middle;width:30">
<%
    if(peopleData == null) {
%>
                    &nbsp;
<%
    } else {
%>
                    <a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=peopleData.name%></a>
<%
    }
%>
                  </td>
                  <%--버전,상태,작성일, 파일--%>
<%
    if (outputData!=null && opDtIsRegistry ) {

		if(objType.equals("ECO") || objType.equals("QLP")){
%>
	                      <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:40">
            <%  if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETProdChangeOrder){  %>
                <a href="#" onClick="javascript:openECO('<%=lastVersionOid%>', 'PROD');"><%=lastVersion%> </a>
            <%  }else if(objType.equals("ECO") && outputData.changeOrder!=null  & outputData.changeOrder instanceof KETMoldChangeOrder){  %>
                <a href="#" onClick="javascript:openECO('<%=lastVersionOid%>', 'MOLD');"><%=lastVersion%> </a>
            <%  }  %>
	                      </td>
	                      <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:60"><%=state%></td>
	                      <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:60"><%=modifyDate%></td>
	                         <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:30"><a href="javascript:PLM_FILE_DOWNLOAD2('<%=fileUrl%>');"><%=icon%></a></td>
<% 

        }else if(objType.equals("TRY")){
             KETTryCondition tryCondition = (KETTryCondition)CommonUtil.getObject(lastVersionOid);    
            
%>
                  <td class="tdwhiteL" style="text-align:center">
                  <%   if(StringUtil.isEmpty(lastVersion)) { %>
                      -
                  <%   }else { %>
                      <a href="#" onClick="javascript:ViewDoc('<%=lastVersionOid%>')"><font color="blue" ><%=lastVersion%></font></a>
                  <%   } %>

                  </td>
                  <td class="tdwhiteL" style="text-align:center"><%=(StringUtil.isEmpty(state))?"-":state%></td>
                  <td class="tdwhiteL" style="text-align:center"><%=(StringUtil.isEmpty(modifyDate) || "&nbsp;".equals(modifyDate))?"-":modifyDate%></td>
                  <td class="tdwhiteL" style="text-align:center">
                  <%   if(StringUtil.isEmpty(icon)) { %>
                      -
                  <%   }else { %>
                      <a href="javascript:PLM_FILE_DOWNLOAD2('<%=fileUrl%>');"><%=icon%></a>
                  <%   } %>
                  </td>
<% 
	                
	    }else if(!isEtc){

	        Iterated pre = VersionControlHelper.service.predecessorOf(opDtLastDocument);
	        String preVer = null;
	
	        String preOid = null;
	
	        if(preOid == null || preOid.length() == 0){
	          preOid = versionOid;
	        }
	
	        if(preVer == null || preVer.length() == 0){
	          preVer = version;
	        }
	        if(pre instanceof RevisionControlled){
	          RevisionControlled preRc = (RevisionControlled)pre;
	          preVer = preRc.getVersionIdentifier().getValue();
	          preOid = preRc.getPersistInfo().getObjectIdentifier().getStringValue();
	        }
%>
              <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:40">
              <!-- 최신버전만 보여지도록 이전버전 주석처리 -->
              <!-- <a href="#" onClick="javascript:ViewDoc('<--%=preOid%>')"><font color="blue" ><--%=preVer%><--%//=version%></font></a>/ -->
              <a href="#" onClick="javascript:ViewDoc('<%=lastVersionOid%>')"><font color="blue" ><%=lastVersion%></font></a></td>
              <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:60"><%=state%></td>
              <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:60"><%=modifyDate%></td>
                 <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:30"><a href="javascript:PLM_FILE_DOWNLOAD2('<%=fileUrl%>');"><%=icon%></a></td>
<%
		}else{
%>
              <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:40">-</td>
              <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:60">-</td>
              <td class="tdwhiteL"  style="text-align:center;text-valign:middle;width:60"><%=modifyDate%></td>
              <td class="tdwhiteL" style="text-align:center;text-valign:middle;width:30">-</td>

<%
		}
    }
    else {



      int docCreateOrLinkType = buttonAuth.isOutputDocCreateOrLink(output);
      if( docCreateOrLinkType == TaskViewButtonAuth.CREATEORLINK || CommonUtil.isBizAdmin() ) {

      if(objType.equals("DOC")){
%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;">
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=opDtOid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputLinkOutput('<%=opDtOid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></span>
                    
                </td>
      <%}else if(objType.equals("ECO")){%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;">
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></span>
                </td>
      <%}else if(objType.equals("ETC")){%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;">
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=opDtOid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01172") %><%--내역입력--%></a></span>
                </td>

      <%}else if(isElectron2 && objType.equals("DWG") && !( pjtType2.equals("0")|| pjtType2.equals("1") ) ){%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;">
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=opDtOid%>','<%=objType%>', 'true');"> <%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></a></span>
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=opDtOid%>','<%=objType%>', 'false');"> <%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></a></span>
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputLinkOutput('<%=opDtOid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%></a></span>
                </td>

      <%}else if(StringUtil.isEmpty(objType)){ %>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;">&nbsp;
                </td>
      <%}else{ %>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;">
                      <%if("제품도출도".equals(taskData2.taskName)){
                      %>
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=opDtOid%>','<%=objType%>', 'false');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                      <%}else{ %>
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputOutputResult('<%=opDtOid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></span>
                    <%} %>
                    <span class="b-small2" style="vertical-align:middle"><a href="javascript:inputLinkOutput('<%=opDtOid%>','<%=objType%>');"> <%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></span>

                </td>
      <%} %>
<%
      }
      else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_CREATE){
%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01333") %><%--등록안됨--%></font></td>
<%
      }else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_TASK_MEMBER){
%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01210") %><%--담당자 지정안됨--%></font></td>
<%
      }else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_PROGRESS){
%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01463") %><%--미진행상태--%></font></td>
<%
      }else{
%>
                <td class="tdwhiteL" colspan="4" style="text-align:center;width:210;"><font color="red">-</font></td>
<%       }
    }
%>
                <%--진행률--%>
                <td class="tdwhiteL" width="80" style="text-align:center">
<%



    int outputCompletionAuthType = 0;
	if(output!=null) outputCompletionAuthType = buttonAuth.isOutputCompletion(output);



    if(outputCompletionAuthType == TaskViewButtonAuth.ACTIONCOMPLECTION) {
%>
                  <input type="hidden" name="progresskey" value="<%=opDtOid%>" />
                  <input name="outputCompletion" class="txt_field" style="width:30%;text-align:right;" size="3" maxlength="3" value="<%=output.getCompletion()%>" progresskey="<%=opDtOid%>" onkeyup ="SetNum(this)"/><b>%</b>&nbsp;
          
                  <span class="b-small2" style="vertical-align:middle"><a href="javascript:outputProgress('<%=opDtOid%>' , '<%=objType %>');"> <%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%></a></span>

<%
    }
    else if(outputCompletionAuthType == TaskViewButtonAuth.NOT_PROGRESS){
      out.println(output.getCompletion() + "%");
    }
    else if(outputCompletionAuthType == TaskViewButtonAuth.NOTACTIONCOMPLECTION){

      out.println(output.getCompletion() + "%");
    }

    else if(outputCompletionAuthType == TaskViewButtonAuth.NOTEXISTCOMPLETION){

      out.println("-");
    }
%>
                </td>
                <%--Gate--%>
                <td class="tdwhiteL" style="text-align:center" width="50">
<%
    if(output!=null) {
        //if( buttonAuth.isOuputDocAdd || isAdmin || isEdit2|| isPmo || isPmo2 || buttonAuth.isPM){
        if(isAdmin || isPmo || isPmo2 || isPmo3 || buttonAuth.isPM){
%>
                  <input type="checkbox" name="gatechecks" value="<%=subjectCheck%>" <%if("1".equals(subjectCheck)) out.println("checked");%> onClick="updateGateTarget(this, '<%=CommonUtil.getOIDString(output) %>')"/>
<%
        }else {
%>
                  <input type="checkbox" name="gatechecks" disabled="true" value="<%=subjectCheck%>" <%if("1".equals(subjectCheck)) out.println("checked");%> />
<%
        }
    }else {
%>
                <input type="checkbox" name="gatechecks" style="visibility: hidden;" value="<%=subjectCheck%>" <%if("1".equals(subjectCheck)) out.println("checked");%> />
<%
    }
%>
                </td>
                <%--템플릿--%>
                <td class="tdwhiteL" style="text-align:center" width="60">
<%
    if(output!=null) {
    	if(opDtAttatchUrl!=null && !"".equals(opDtAttatchUrl)) {
%>
                  <a href="javascript:PLM_FILE_DOWNLOAD2('<%=opDtAttatchUrl%>')">Down</a>
<%
    	}else {
%>
                   -
<%
    	}
    }
%>
                  
                </td>
                <%--삭제--%>
<%   if(StringUtil.isEmpty(objType)){ %>
                <td class="tdwhiteL">&nbsp;
                </td>
<%
	}else if( task!=null && buttonAuth!=null && buttonAuth.isOuputDocAdd || isAdmin || isEdit2|| isPmo || isPmo2 || isPmo3 || buttonAuth.isPM){ //buttonAuth.isDeleteOutput(output)) { 
 %> 
                <td class="tdwhiteL" style="text-align:center" width="50">
                  <a href="JavaScript:deleteOutput('<%=opDtOid%>')"><p><img src="/plm/portal/images/icon_delete.gif" width="13" height="12" border="0"></p></a>
                </td>
<%
     }else{%>
                <td class="tdwhiteL" style="text-align:center" width="50">
                 -
                </td>
    <%}%>

    
              </tr>
<%
  }
}
%>
            <!---------------------- 산출물 종료 --------------------->
            </table>
                </td>
              </tr>
            </table>
            </div>
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td class="space10"></td>
            </tr>
          </table>
            
		  <%if(!buttonAuth.isChild){ %>
		  <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
          </table>
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
      </table>
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

String oid = request.getParameter("oid");
%>

<script type="text/javascript">

// 산출물 진척률 입력
function outputProgress(objkey, objType) {

//   var oprogress = document.forms[0].outputCompletion;
  var oprogress = document.getElementsByName("outputCompletion");
  var oprogresskey = document.getElementsByName("progresskey");

  if(checkStartEndDate()){
    alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
    document.forms[0].taskExecStartDate.focus();
    return;
  }

  if(oprogress == null || oprogress == 'undefined') {
    return;
  }


  var oprogressvalue = 0;
  var len = oprogress.length;
  //alert('len:'+len +':'+oprogresskey.length+', objkey:'+objkey+', oprogress.progresskey:'+oprogress.progresskey +', oprogress.value:'+oprogress[0].value);
  if(len) {
    for(var i = 0; i < len; i++) {
        if(oprogresskey[i].value == objkey) {
            oprogressvalue = oprogress[i].value;
        break;
      }
    }
  }
  else {
      if(oprogresskey[0].value == objkey) {
          oprogressvalue = oprogress[0].value;

    }
  }
  if(oprogressvalue > 100) {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02732") %><%--진행율을 100미만으로 입력하시기 바랍니다--%>');
    return;
  }
  //if(check){

  if( oprogressvalue == 100 ){


      // [END] [PLM System 1차개선] [수정] FS 선행 Task 미완료 시 후행 Task 완료(산출물 진척률 100% 입력) 불가, 2013-08-23, BoLee

    if(objType == "ETC"){
      etcComplete(objkey);
      return;
    }else{
      alert("<%=messageService.getString("e3ps.message.ket_message", "01720") %><%--산출물 등록 및 결재 승인됨 상태가 되면 자동 입력됩니다--%>");
      return;
    }

  }
  //}

  //onProgressBar();

  var param = "command=inputOutputProgress";
  param += "&oid=<%=oid%>";
  param += "&outputOid=" + encodeURIComponent(objkey);
  param += "&outputCompletion=" + encodeURIComponent(oprogressvalue);
  //alert('param:'+param);
  var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
  
  postCallServer(url, param, setOutputTable, true);
  
  //location.reload();
  goReloadPage();
}

function checkStartEndDate(){
  var form = document.forms[0];

  if(form.taskExecStartDate.value == ''){
    return true;
  }
  return false;
}

 
$(document).ready(function(){
    CalendarUtil.dateInputFormat('estimateDates');
    if($("#listSize").val() == 0){
    	$("#oldTrust").hide(); 
    }
    setKqisList();
});



function setKqisList(){
	var isAdmin = $("#isAdmin").val();
    var taskStateByTrust = $("#taskStateByTrust").val();
    var trustAuth = $("#trustAuth").val();
    TrustDelAuth = isAdmin || (taskStateByTrust != '5' && trustAuth);
    
    var param = new Object();
    param.taskOid = "<%=oid%>";
    
    ajaxCallServer("/plm/ext/project/task/getKQISTrustListQuery", param, function(data){
        
        var kqisList = data.kqisList;
        
        if(kqisList.length == 0){
            
        }else{
            
            var checkCnt = 0;
            var listCnt = 0;
            
            for(var i = 0; i < kqisList.length; i++){
                addKqisList(kqisList[i]);
            }
            setTrustRate();
        }
    });
}

function nullConvert(data){
	if(data == null || data == 'null'){
		return "";
	}else{
		return data;
	}
}


function getTimeStamp() {

    var d = new Date();
    var s = leadingZeros(d.getFullYear(), 4) + leadingZeros(d.getMonth() + 1, 2) + leadingZeros(d.getDate(), 2);
    return s;
}

function leadingZeros(n, digits) {

    var zero = '';
    n = n.toString();

    if (n.length < digits) {
        for (i = 0; i < digits - n.length; i++)
            zero += '0';
    }
    return zero + n;
}

var today4Trust = getTimeStamp();

function betweenDay(firstDate, secondDate)
{
	firstDate = firstDate.replace(/-/gi, "");
	secondDate = secondDate.replace(/-/gi, "");
    var firstDateObj = new Date(firstDate.substring(0, 4), firstDate.substring(4, 6) - 1, firstDate.substring(6, 8));
    var secondDateObj = new Date(secondDate.substring(0, 4), secondDate.substring(4, 6) - 1, secondDate.substring(6, 8));
    var betweenTime = secondDateObj.getTime() - firstDateObj.getTime();
    return Math.floor(betweenTime / (1000 * 60 * 60 * 24));
}



function addKqisList(data){
	
    var row = "<tr>";
    var fontColor = "";
    var endPlanDay =  nullConvert(data.END_PLAN_DAY);//시험종료예정일
    var testEndDay = nullConvert(data.TEST_END_DAY);//시험실제종료일
    
    if(endPlanDay != '' && testEndDay == '' && betweenDay(today4Trust,endPlanDay) < 0){//실제종료일이 없는데 종료예정일이 현재일을 초과한 경우
    	fontColor = "#FF0000";
    }
    
    row += "<td class=tdwhiteL style='text-align: center'><a href=javascript:kqisLinkPop('"+data.URL +"');>" + data.ADMIN_NO + "</td>";
    row += "<td class=tdwhiteL style='text-align: center; color : " +fontColor + "'>" + data.TEST_STATE + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + data.TEST_RESULT + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + data.COMPLETED_CNT + "/" + data.TOT_CNT + "</td>";
    row += "<td class=tdwhiteL>" + data.PART_NO + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + nullConvert(data.REQUESTER) + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + nullConvert(data.TESTER) + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + nullConvert(data.REQ_DAY) + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + nullConvert(data.STR_PLAN_DAY) + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + nullConvert(data.TEST_STR_DAY) + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + endPlanDay + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'>" + testEndDay + "</td>";
    row += "<td class=tdwhiteL style='text-align: center'><a href=JavaScript:deleteOutputKqisLink('"+data.OID+"');><p><img src='/plm/portal/images/icon_delete.gif' width='13' height='12' border='0'></p></a></td>";    

    
    row += "</tr>";
    
    $("#kqisList").append(row);
}

function kqisTrustCheck(){
	
    var rtn = true;
	$("#kqisList tr").each(function(){
		   var state = $(this).find('td').eq(1).html();
		   if(state != '완료'){
			   rtn = false;
			   return false;
		   }
	});
	return rtn;
}

function setTrustRate(){
	var rowCount = document.getElementById("kqisList").rows.length;
	var completeCnt = 0;
	
	$("#kqisList tr").each(function(){
        var state = $(this).find('td').eq(1).html();
        if(state == '완료'){
        	completeCnt++;
        }
    });
	var rate = (completeCnt/rowCount) * 100;
	$("#trustRate").html(Math.floor(roundToTwo(rate)) + " / 100.0");
}

function roundToTwo(num) {
    return +(Math.round(num + "e+2")  + "e-2");
}

function viewTask(oid){
    openView(oid);
}

</script>

