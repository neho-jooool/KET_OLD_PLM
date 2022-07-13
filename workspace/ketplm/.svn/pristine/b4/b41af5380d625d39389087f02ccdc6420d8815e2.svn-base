<%@page import="e3ps.wfm.entity.KETWfmMultiApprovalRequest"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.*" %>
<%@page import = "java.net.URL" %>
<%@page import = "java.sql.Timestamp" %>
<%@page import = "e3ps.wfm.entity.KETWfmApprovalMaster" %>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.groupware.company.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "e3ps.project.E3PSProject" %>
<%@page import = "e3ps.project.Performance" %>
<%@page import = "e3ps.common.content.ContentUtil" %>
<%@page import = "e3ps.common.content.ContentInfo" %>
<%@page import = "e3ps.dms.beans.DMSUtil" %>
<%@page import = "e3ps.dms.entity.*" %>
<%@page import = "wt.workflow.work.WfAssignedActivity" %>
<%@page import = "wt.workflow.work.*" %>
<%@page import = "wt.workflow.engine.*" %>
<%@page import = "wt.workflow.WfException" %>
<%@page import = "wt.lifecycle.*" %>
<%@page import = "wt.content.*" %>
<%@page import = "wt.epm.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.vc.*" %>
<%@page import = "wt.session.*" %>
<%@page import = "wt.org.*" %>
<%@page import = "wt.query.*" %>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  String workOID = request.getParameter("oid");
  ReferenceFactory rf = new ReferenceFactory();
  WorkItem item = (WorkItem)rf.getReference(workOID).getObject();
  WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
  Persistable obj = item.getPrimaryBusinessObject().getObject();
  //Kogger.debug(">>>>>>>>>>>>>>>>>> "+ClassifyPBOUtil.extractPBOInfo(obj).get("type").toString() );
  //out.println(obj);


  boolean conAccept = false;
  boolean isDRR = false;
  if(obj instanceof KETProjectDocument){
    KETProjectDocument projDoc = (KETProjectDocument)obj;
    conAccept = DMSUtil.isDrCheckOfDoc(projDoc);
  }
  if(obj instanceof KETDevelopmentRequest){
    isDRR = true;
  }
  String approvalDate = "&nbsp;";
  String arrivalDate = DateUtil.getDateString(item.getPersistInfo().getCreateStamp(),"d");
  String comment = "&nbsp;";
  String status = ClassifyPBOUtil.extractPBOInfo(obj).get("state").toString();
  String title = ClassifyPBOUtil.extractPBOInfo(obj).get("title").toString();
  String pboType = ClassifyPBOUtil.extractPBOInfo(obj).get("type").toString();
  WTUser lastApprover = (WTUser)WorkflowSearchHelper.manager.getLastUser(obj);
  String masterOid = "";
  String step = "";
  People user = new People();

  boolean receiver = false;
  boolean confirmReject = false;
  boolean noWorkflow = false;

  if( obj instanceof KETDevelopmentRequest )
  {
    KETDevelopmentRequest devReq = (KETDevelopmentRequest)obj;
    step = devReq.getDevelopmentStep();
  }

  String taskName="&nbsp;";


  if( "R".equals(step) ){
    taskName = "개발검토의뢰";
  }else if ( "D".equals(step) ) {
    taskName = "개발의뢰"+activity.getName();
  }else{
    taskName = activity.getName();
  }


  if(activity.getName().equals("수신확인")) receiver = true;
  if(activity.getName().equals("반려확인")) confirmReject = true;
  if(activity.getName().equals("수행담당자"))  {
    taskName = item.getRole().getDisplay();
    noWorkflow = true;
  }
  if(lastApprover!=null){
    String ownerID = item.getOwnership().getOwner().getName();
    if(ownerID.equals(lastApprover.getName())){
      taskName = "승인";
    }
  }

  if(!noWorkflow){
    KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster(obj);
    masterOid = CommonUtil.getOIDString(appMaster);
    user = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
    approvalDate = DateUtil.getDateString(appMaster.getPersistInfo().getUpdateStamp(),"d");
    comment = (appMaster.getComments() != null && appMaster.getComments().trim().length() > 0) ? appMaster.getComments().trim() : "&nbsp;";
  }
  else {
    String uID = ClassifyPBOUtil.extractPBOInfo(obj).get("creatorID").toString();
    user = PeopleHelper.manager.getPeople(uID);
    approvalDate = arrivalDate;
  }
  String dept = "지정안됨";
  String duty = "지정안됨";
  if(user.getDepartment()!=null)dept = user.getDepartment().getName();
  if(user.getDuty()!=null)duty = user.getDuty();


  // 2013.1.14 shkim
  Vector docVec = null;
  String objOid = obj.toString();
  if(obj instanceof KETWfmMultiApprovalRequest) {
    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest)CommonUtil.getObject(objOid);
    //Vector epmVec = WorkflowSearchHelper.manager.getEPMList(multiReq);
    docVec = WorkflowSearchHelper.manager.getDocList(multiReq);
    //out.println(docVec.size());
  }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00776") %><%--결재수행--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=JavaScript src="viewPBO.js"></script>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">

function complete(condition){
  var ok;
  var comment;
  if(condition=='reject'){
    ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "01467") %><%--반려 하시겠습니까?--%>\n(<%=messageService.getString("e3ps.message.ket_message", "00784") %><%--결재의견은 필수 입력입니다--%>)');
    comment = document.taskPage.acomment.value;
  }else if(condition=='cancel'){
    ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "02892") %><%--취소 하시겠습니까?--%>');
  }else if(condition=='accept'||condition=='conAccept'){
    ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "01989") %><%--승인 하시겠습니까?--%>');
  }else if(condition=='update'){
    ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "01944") %><%--수정 후 결재 요청 하시겠습니까?--%>');
  }else if(condition=='taskComplete'){
    ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "01979") %><%--수행작업이 완료되었습니까?--%>');
  }
  else ok = true;
  if(ok){
    if(!comment && condition=='reject') {alert('결재의견을 입력하여 주십시요');return;}
    document.taskPage.method="post";
    if(condition=='update'){
      document.taskPage.action="/plm/servlet/e3ps/CommonWorkflowServlet?cmd=updateRestart&master=<%=masterOid%>&item=<%= workOID%>";
    }else {
      document.taskPage.action="/plm/servlet/e3ps/CommonWorkflowServlet?cmd=complete&item=<%= workOID%>&master=<%=masterOid%>&condition="+condition;
    }
    document.taskPage.submit();
  }
}
</script>
</head>
<body>
<form name="taskPage">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00776") %><%--결재수행--%></td>
                <td align="right">
                    <img src="../../portal/images/icon_navi.gif">Home
                    <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435") %><%--작업공간--%>
                    <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00760") %><%--결재대기함--%>
                    <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00776") %><%--결재수행--%>
                </td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <%if(!receiver && !confirmReject && !noWorkflow){ %>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:complete('accept')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01987") %><%--승인--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <% }%>

              <%if(conAccept && !receiver && !confirmReject && !noWorkflow ){ %>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:complete('conAccept')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02636") %><%--조건부승인--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <%} %>
              <%if(isDRR && !receiver && !confirmReject && !noWorkflow){%>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:complete('cancel')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00733") %><%--검토취소--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <%} %>

             <%if(!receiver && !confirmReject && !noWorkflow){ %>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:complete('reject')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01466") %><%--반려--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <%} %>

             <%if(receiver && !confirmReject && !noWorkflow){ %>
             <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:complete('confirm')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01931") %><%--수신확인--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <%} %>

             <%if(!receiver && confirmReject && !noWorkflow){ %>
             <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:complete('update')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02451") %><%--재작업--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <%} %>

              <%if(noWorkflow){ %>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:complete('taskComplete')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01978") %><%--수행완료--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <%} %>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %><%--목록--%></a></td>
                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02436") %><%--작업명--%></td>
          <td colspan="3" class="tdwhiteL0"><%=taskName %></td>
        </tr>
                <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00782") %><%--결재요청자--%></td>
          <td width="240" class="tdwhiteL"><%=dept%>&nbsp;/&nbsp;<%=duty %>&nbsp;/&nbsp;<%=user.getName() %></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td width="240" class="tdwhiteL0"><a href="javascript:viewHistory('<%=obj.toString() %>')"><%=status %></a></td>
        </tr>
                <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00781") %><%--결재요청일--%></td>
          <td width="240" class="tdwhiteL"><%=approvalDate %></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00768") %><%--결재도착일--%></td>
          <td width="240" class="tdwhiteL0"><%=arrivalDate %></td>
        </tr>
                <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00762") %><%--결재대상--%></td>
          <%if(docVec != null && docVec.size() != 0) { %>
            <td colspan="3" class="tdwhiteL0"><a href="javascript:openView2('<%=obj %>')"><font color="blue"><u><b><%=title %></b></u></font></a></td>
          <%}else{ %>
             <td colspan="3" class="tdwhiteL0"><a href="javascript:openView('<%=obj %>')"><font color="blue"><u><b><%=title %></b></u></font></a></td>
          <%} %>
        </tr>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02964") %><%--파일정보--%></td>
          <td colspan="3" class="tdwhiteL0">
          <%

          if(obj instanceof ContentHolder){
              ContentHolder holder = ContentHelper.service.getContents((ContentHolder)obj);
              Vector holderVec = ContentHelper.getContentListAll(holder);
              if((!holderVec.isEmpty())&&(holderVec!=null)){
                for(int i=0; i<holderVec.size(); i++){
                  ContentItem contItem = (ContentItem)holderVec.get(i);
                  if((contItem.getRole().equals(ContentRoleType.PRIMARY))||(contItem.getRole().equals(ContentRoleType.SECONDARY))){
                    ContentInfo contInfo = ContentUtil.getContentInfo(holder, contItem);
                    String url = contInfo.getDownURLStr();
                    String iconUrl = contInfo.getIconURLStr();
                    //Kogger.debug(iconUrl);
          %>
                    <%=url %>
          <%
                }
        %><br><%
              }
           }else{%>
          <br>
          <%}
          }else{%>
          <br>
          <%}%>
         </td>
         </tr>
         <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00780") %><%--결재요청의견--%></td>
            <td colspan="3" class="tdwhiteL0" style="height:70"><textarea name="textfield10" class="txt_fieldRO" id="textfield12" style="width:100%; height:96%" readOnly><%=comment %></textarea></td>
         </tr>
         <tr>
            <%if(!receiver && !confirmReject && !noWorkflow){ %>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00783") %><%--결재의견--%><br>(<%=messageService.getString("e3ps.message.ket_message", "00045", 600) %><%--{0}자 이내--%>)</td>
            <td colspan="3" class="tdwhiteL0" style="height:70"><textarea name="acomment" class="txt_field"  style="width:100%; height:96%"></textarea></td>
          <%} %>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
