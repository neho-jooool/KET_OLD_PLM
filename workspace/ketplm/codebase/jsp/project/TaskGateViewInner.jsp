<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page import = "wt.fc.*,
          wt.org.*,
          wt.vc.*,
          wt.vc.wip.*,
          java.util.*,
          e3ps.ecm.entity.*,
          e3ps.dms.entity.*,
          ext.ket.project.trycondition.entity.*,
          java.text.*"%>
<%@page import="e3ps.groupware.company.*,
          e3ps.project.*,
          e3ps.project.beans.*,
          e3ps.common.util.*" %>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.enterprise.RevisionControlled"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="ext.ket.shared.content.entity.*"%>
<%@page import="ext.ket.project.task.entity.*"%>
<%@page import="ext.ket.project.task.service.*"%>
<%@page import="ext.ket.project.gate.entity.*"%>
<%@page import="ext.ket.dqm.entity.*"%>
<%@page import="e3ps.common.code.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@page import="java.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />


<%
// 포함한 페이지에 정의됨
  String oid =  request.getParameter("oid");
  String oldOid =  request.getParameter("compareTaskOid");
  String taskCategoryType = "";
  E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
  taskCategoryType = task.getTaskTypeCategory();
    E3PSProject project = (E3PSProject)task.getProject();
//   //out.println(project.getPjtType());
    String projectOid = CommonUtil.getOIDString(project);
    WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
    String taskTypeName = task.getTaskType();
    
    boolean isProtoGate1Check = false;
    boolean isProtoGate1Pass = true;
    String ProtoGate1Result = "완료";
    String proto_resultTotalStr = "";
    String ProtoGateTaskOid = "";
    String protoProjectNo = "";
    
  try{
      
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
    
  //out.println(project.getPjtType());

  //팝업 여부
  String popup = request.getParameter("popup");

  /*
    ####################  권한 설정  ####################
  */


%>
<html>
<head>
<title></title>
<%@include file="/jsp/common/top_include.jsp" %>
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
<!--
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
a:link {
    color: #4398BC;
    text-decoration: none;
}

a:visited {
    color: #4398BC;
    text-decoration: none;
}

a:active {
    color: #1660c7;
    text-decoration: none;
}

a:hover {
    color: #1660c7;
    text-decoration: underline;
}
</style>

<script type="text/javascript">

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

function saveTaskOutputResult(resultState) {
    if(resultState!='INWORK') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "05136")%><%--문서의 상태가 작업중 재작업인 경우만 결재요청 가능합니다. --%>");
        return;
    }
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>") ) {
//      document.forms[0].action = "/plm/ext/project/task/saveTaskOutputResult.do";
//      document.forms[0].method = "post";
//      document.forms[0].submit();
        
        $.ajax( {
            url : "/plm/ext/project/task/saveTaskOutputResult.do",
            type : "POST",
            data : $('[name=gateForm]').serialize(),
            async : false,
            success: function(data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "01947")%><%--수정되었습니다. --%>');
                document.forms[0].action = "/plm/jsp/project/TaskGateView.jsp?oid=<%=oid%>&popup=popup";
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

function approvalAction(oid){
    if(oid=='' || oid == 'null') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07142")%><%--저장 후 결재요청바랍니다.--%>');
        return;
    } 
    alert(oid);
    /*
    if( document.forms[0].resultTotalStr.value=='R') {
        alert('결과 상태가 R인 경우 결재 요청할 수 없습니다.');
        return;
    }
    */

    var url="/plm/jsp/wfm/RequestApproval.jsp?pboOid="+oid;
    openOtherName(url,"approval","800","700","status=no,scrollbars=yes,resizable=no");
}

function viewDqmPopup(oid){
    getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=view&oid='+oid,oid,1100,768);
}

function openCompleteReson(oid){
  var url="/plm/jsp/project/EtcOutputReasonView.jsp?oid="+oid;
  openOtherName(url,"Reson","500","360","status=no,scrollbars=no,resizable=yes");
}
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
</script>
</head>
<body style="padding-right:15px;">
<%@include file="/jsp/project/template/ajaxProgress.html"%>
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
       <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
          <td valign="top">
            


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

    GateAssessResult gateAssResult = null;
    
    String recoveryFileCnt = "";
    try {
	
	    int gate_rev = ProjectTaskCompHelper.service.getMaxGateDegree(oid);
        //평가결과(모든 결과를 Hashtable에 담는다)
        gateResultTotalHash = ProjectTaskCompHelper.service.getGateAssessResultInfoList(oid, true,gate_rev);
        if(gateResultTotalHash!=null) {
            
             resultOutputVal = (String)gateResultTotalHash.get("resultOutputVal") ;
             resultAssVal = (String)gateResultTotalHash.get("resultAssVal") ; 
             resultDqmVal = (String)gateResultTotalHash.get("resultDqmVal") ;
             resultTotalVal = (String)gateResultTotalHash.get("resultTotalVal") ;
             resultTotalStr = (String)gateResultTotalHash.get("resultTotalStr") ;
             resultState = (String)gateResultTotalHash.get("resultState") ;
             resultStateName = (String)gateResultTotalHash.get("resultStateName") ;
             resultTemplateUrl = (String)gateResultTotalHash.get("resultTemplateUrl") ;
             gateResultOid =  "ext.ket.project.gate.entity.GateAssessResult:" + (String)gateResultTotalHash.get("gateResultOid") ;
        }
        
        gateAssResult = ProjectTaskCompHelper.service.getGateAssessResultObj(oid);
        if (gateAssResult != null) {
            ArrayList<ContentDTO> secondArrList =  ext.ket.shared.content.service.KETContentHelper.manager.getSecondaryContents(gateAssResult);
            if(secondArrList!=null) {
                recoveryFileCnt = ""+secondArrList.size();
            }
        }
    }catch(Exception e) {
	Kogger.error(e);
    }
        
    
    
    
%>
            <input type="hidden" name="resultTotalStr" value="<%=resultTotalStr%>"/>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07141")%><%--평가결과--%></td>
                <td align="right">
                  <table border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
                    <tr>
                      <td width="80" align="right"> <%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%> : </td>
                      <td width="120"> &nbsp;<%=resultStateName %>
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

        String latestInfoTitle = (latestInfo != null) ? latestInfo.getTitle() : "";
        latestInfoTitle = org.apache.commons.lang.StringEscapeUtils.escapeHtml(latestInfoTitle);
%>
                      <td width="80" align="right"> Template : </td>
					  <td width="100" title="<%=latestInfoTitle%>"><div class="ellipsis" style="width:100px;">
	                      <a href="JavaScript:linkStandardForm('<%=latestInfo == null ? "" : latestInfo.toString()%>')"><%=latestInfoTitle%></a>
	                      </div>
                      </td>
                      <td width="5"> 
                      </td>
<%
    if("INWORK".equals(resultState) || "REWORK".equals(resultState)) {
%>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:saveTaskOutputResult('<%=resultState %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td width="5"> 
                      </td>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:approvalAction('<%=gateResultOid %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
<%
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
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07133") %><%--최종평가--%></td>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00747") %><%--결과--%></td>
                <td width="130" class="tdblueM">Recovery Plan<%--Recovery Plan--%></td>
              </tr>

              <tr>
                <td width="130" class="tdwhiteM"><%=resultOutputVal %></td>
                <td width="130" class="tdwhiteM">&nbsp;<%=resultAssVal%></td>
                <td width="130" class="tdwhiteM">&nbsp;<%=resultDqmVal%></td>
                <td width="130" class="tdwhiteM">&nbsp;<%=resultTotalVal %></td>
                <td width="130" class="tdwhiteM">&nbsp;
                <% if("G".equals(resultTotalStr)) { %>
                    <img src="/plm/extcore/image/ico_green.png" />
                <% }else if("Y".equals(resultTotalStr)) { %>
                    <img src="/plm/extcore/image/ico_yellow.png" />
                <% }else if("R".equals(resultTotalStr)) { %>
                    <img src="/plm/extcore/image/ico_red.png" />
                <% } %>
                </td>
                <td width="130" class="tdwhiteM">&nbsp;
                <% if(!StringUtil.isEmpty((String)gateResultTotalHash.get("gateResultOid")) ) { %>
                    <a href="JavaScript:gateResultAttatch();"><%=recoveryFileCnt %></a>
                <% } %>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
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
    </ul>
    <!-- 첫번째 tab 화면 -->
    <div id="tabs-1" class="tabMain"  style="height: 200px; overflow: auto;">
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed">
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
//                             state = dqmRaiseObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
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
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:openView('<%=lastVersionOid%>')"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div></a>
            <%      }else { %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=outputNameStrFull %>"><a href="#" onClick="javascript:openView('<%=CommonUtil.getOIDString(opDtDocument)%>')"><div class="ellipsis" style="width:100%;"><%=outputNameStr%></div></a>
            <%      
                    }
                }else if(objType.equals("ETC") || objType.equals("COST") || objType.equals("SALES") ){  %>
                <td width="200" class="tdwhiteM" style="text-align:left" title="<%=opDtNameFull %>"><a href="#" onClick="javascript:openCompleteReson('<%=opDtOid%>');"><div class="ellipsis" style="width:100%;"><%=opDtName%></div> </a>
            <%      
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
                <td width="160" class="tdwhiteM" style="text-align:left">
                    <div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(gDto.getOutputComment()) %></div>
                </td>
                <td width="50" class="tdwhiteM" style="<%=outputBgColor %>">
                    <input type="hidden" name="tgOutputOids" value="<%=outputOidStr %>"/>
                    <font color="#000000"><b><%=StringUtil.checkNull(gDto.getOutputCheck())%></b></font>
                    
                </td>
              </tr>
<%
            }
        }
    }catch(Exception e){
	Kogger.error(e);
    }

%>
            </table>
    </div>
    <div id="tabs-2"  style="height: 200px; overflow: auto;">
<%
  
    List<GateCheckSheetDTO>  taskGateCheckSheetList = new ArrayList<GateCheckSheetDTO>();
    String taskName = eTask.getTaskName();
    String taskCategory = eTask.getTaskTypeCategory();
    try {
%>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed">
              <tr>
                <td width="20" rowspan="2" class="tdblueM">No</td>
                <td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07134") %><%--목표구분--%></td>
                <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%></td>
                <td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07135") %><%--달성기준--%></td>
<%--                 <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07136") %>기준</td> --%>
                <td width="120" colspan="2" class="tdblueM"><%=taskName%><%--GateName--%></td>
                <td width="40" rowspan="2" class="tdblueM">Check<%--Check--%></td>
                <td width="120" rowspan="2" class="tdblueM">담당부서</td>
              </tr>
              <tr>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
              </tr>
<%


        //이전 게이트에서 현재 게이트까지 테스크 산출물 리스트
// 		taskGateCheckSheetList = ProjectTaskCompHelper.service.getProjectTaskGateCheckLinkList(oid);
		taskGateCheckSheetList = (List<GateCheckSheetDTO>)gateResultTotalHash.get("resultGateCheckSheetList") ;
        if(taskGateCheckSheetList!=null) {

            for(int i=0;i<taskGateCheckSheetList.size();i++) {
                //프로젝트 평가항목 리스트에서 해당 Gate명에 해당하는 체크항목이 체크된 것만 가져온다
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
                
                String checkSheetStr = StringUtil.checkNull( gDto.getCheckSheetName() );
                if(checkSheetStr!=null && checkSheetStr.getBytes().length>50) {
                    //checkSheetStr = checkSheetStr.substring(0, 24) + "..";
                  }
                String baseSheetStr = StringUtil.checkNull( gDto.getAchieveBase() );
                if(baseSheetStr!=null && baseSheetStr.length()>5) {
                    //baseSheetStr = baseSheetStr.substring(0, 5) + "..";
                  }
%>
              <tr>
                <td width="30" class="tdwhiteM"><%=i+1%></td>
                <td width="80" class="tdwhiteM"><%=gDto.getTargetTypeName() %></td>
                <td width="120" class="tdwhiteM" style="text-align:left" title="<%=checkSheetStr %>"><div class="ellipsis" style="width:100%;"><%=checkSheetStr %><%--평가항목--%></div></td>
                <td width="100" class="tdwhiteM" title="<%=baseSheetStr %>"><div class="ellipsis" style="width:100%;"><%=baseSheetStr %><%--달성기준--%></div></td>
<%--                 <td width="80" class="tdwhiteM"><%=StringUtil.checkNull(tCriteria) %>기준</td> --%>
                <td width="80" class="tdwhiteM"><%=StringUtil.checkNull(gDto.getTaskTarget()) %><%--GateName--%></td>
                <td width="60" class="tdwhiteM">    
                    <input type="hidden" name="tgGateRsltCodes" value="" />
                    <input type="hidden" name="tgGateCheckOids" value="<%=gateCheckOidStr %>" />
                    <%=StringUtil.checkNull(gDto.getTaskResult()) %>
                </td>
                <td width="60" class="tdwhiteM" style="<%=checkBgColor %>">
                    <font color="#000000"><b><%=StringUtil.checkNull(gDto.getTaskCheck())%></b></font>

                </td>
                <td width="140" class="tdwhiteM" style="text-align:left" title="<%=StringUtil.checkNull(gDto.getManagerDeptName()) %>"><div class="ellipsis" style="width:100%;"><%=StringUtil.checkNull(gDto.getManagerDeptName())  %></div></td>
              </tr>
<%
            }
        }
    }catch(Exception e){
	Kogger.error(e);
    }
%>
            </table>
    </div>
    <div id="tabs-3" style="height: 200px; overflow: auto;">
<%
  
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


//List<KETDqmDTO>  taskQualityPrbmList = ProjectTaskCompHelper.service.getQualityProblemList(oid);
List<KETDqmDTO>  taskQualityPrbmList = (List<KETDqmDTO>)gateResultTotalHash.get("resultKETDqmList") ;
if(taskQualityPrbmList!=null) {

 for(int i=0;i<taskQualityPrbmList.size();i++) {
     //프로젝트 평가항목 리스트에서 해당 Gate명에 해당하는 체크항목이 체크된 것만 가져온다
     KETDqmDTO gDto = taskQualityPrbmList.get(i);
     
     
     String outputBgColor = "";
     if("G".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:green;";
     else if("Y".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:yellow;";
     else if("R".equals(StringUtil.checkNull(gDto.getOutputCheck()))) outputBgColor = "background-color:red;";
     
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
     if(gateAssResult!=null) {
         AssessResultDqmHeaderLink dqmHeaderLink = null;
         QueryResult qr = PersistenceHelper.manager.navigate(gateAssResult, AssessResultDqmHeaderLink.DQM_ROLE , AssessResultDqmHeaderLink.class, false);
         if (qr.hasMoreElements()) {
            dqmHeaderLink = (AssessResultDqmHeaderLink) qr.nextElement();
            reviewDateStr = dqmHeaderLink.getDqmCompDate();
            prbmStateStr = dqmHeaderLink.getDqmState();
         }
     }
     /*******************(끝) Gate객체 저장당시 연결된 산출물 중 AssessResultDqmHeaderLink객체에 산출물의 History정보가 있는 경우 가져오기****************/
%>
              <tr>
                <td width="30" class="tdwhiteM"><%=(i+1) %></td>
                <td width="70" class="tdwhiteM"><a href="#" style="color:#1660c7" onclick="javascript:viewDqmPopup('<%=gDto.getOid()%>');">
                <%= StringUtil.checkNull( gDto.getProblemNo() )%><%--품질문제No--%></a></td>
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
                <td id="tdComboDqm<%=i %>" width="60" class="tdwhiteM" style="<%=outputBgColor %>;text-align:center">
                    <input type="hidden" name="tgDqmHeaderOids" value="<%=gDto.getOid() %>"/>
                    <%= StringUtil.checkNull(gDto.getOutputCheck())%>
                </td>
              </tr>
<%
            }
        }
    }catch(Exception e){
	Kogger.error(e);
    }
%>
            </table>
    </div>
</div>


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
}

%>


<script type="text/javascript">

$(document).ready(function(){

     var tab = CommonUtil.tabs('tabs');
     
     var taskCategoryType = '<%=taskCategoryType%>';
     
     if(taskCategoryType != 'p-2' && taskCategoryType.indexOf('p') != -1){//p Gate는 평가항목만 평가대상임(p-2 만 빼고)
         tab.tabs({active: 1});
         tab.tabs({disabled: [0,2]});
         isCheckTarget = false;
     }
});
</script>