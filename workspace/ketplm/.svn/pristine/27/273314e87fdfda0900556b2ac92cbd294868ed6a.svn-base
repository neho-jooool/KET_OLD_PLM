<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%@ page import = "wt.vc.Versioned"%>
<%@ page import = "wt.vc.VersionControlHelper"%>
<%@ page import = "wt.fc.*"%>
<%@page import = "wt.session.SessionHelper"%>
<%@page import = "wt.org.WTUser"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import = "wt.org.*,wt.session.*"%>
<%@ page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 e3ps.project.*,
                 ext.ket.project.task.entity.*,
                 e3ps.groupware.company.*,
                 e3ps.project.beans.TaskViewButtonAuth,
                 e3ps.project.beans.TaskUserHelper,
                 e3ps.project.TaskMemberLink,
                 e3ps.groupware.company.PeopleData"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%
String oid = request.getParameter("oid");
E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
E3PSProject project = (E3PSProject)task.getProject();
ExtendScheduleData schData = (ExtendScheduleData)task.getTaskSchedule().getObject();
Timestamp planStartDateTime =  schData.getPlanStartDate();
String planStartDate = "";
if(planStartDateTime!=null) planStartDate = DateUtil.getTimeFormat(planStartDateTime,"yyyy-MM-dd");

Timestamp planEndDateTime =  schData.getPlanEndDate();
String planEndDate = "";
if(planEndDateTime!=null)  planEndDate = DateUtil.getTimeFormat(planEndDateTime,"yyyy-MM-dd");

java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
java.util.Date beginDate = formatter.parse(planStartDate);
java.util.Date endDate = formatter.parse(planEndDate);
long diffDays = (endDate.getTime() - beginDate.getTime()) /( 24 * 60 * 60 * 1000);

Timestamp execStartDateTime =  schData.getExecStartDate();
String execStartDate =  "";
if(execStartDateTime!=null) execStartDate = DateUtil.getTimeFormat(execStartDateTime,"yyyy-MM-dd");

Timestamp execEndDateTime =  schData.getExecEndDate();
String execEndDate =  "";
if(execEndDateTime!=null) execEndDate = DateUtil.getTimeFormat(execEndDateTime,"yyyy-MM-dd");

String command = request.getParameter("command");

WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();

//Task 책임자  설정
TaskViewButtonAuth buttonAuth = new TaskViewButtonAuth(task);
boolean isEdit = false;

if (buttonAuth != null) {
    QueryResult masterList = TaskUserHelper.manager.getMaster(task);
    while (masterList.hasMoreElements()) {
    Object o[] = (Object[]) masterList.nextElement();
    TaskMemberLink link = (TaskMemberLink) o[0];
    PeopleData data = new PeopleData(link.getMember().getMember());
        if (wtuser.getName().equals(data.id)) {
            isEdit = true;
        }
    }
}
if ((CommonUtil.isAdmin() || buttonAuth.isPM || CommonUtil.isBizAdmin())) {
    isEdit = true;
}

if(!project.getState().toString().equals("PROGRESS")){
    isEdit = false;
}
if ( "update".equals(command) ) {
    String planWorkTime = request.getParameter("planWorkTime");
    
    task.setPlanWorkTime(Float.parseFloat(planWorkTime));
    task = (E3PSTask)PersistenceHelper.manager.modify(task);
}

String taskTypeName = task.getTaskType();

%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">
function updatPlanWorkTime(){
	
	var planWorkTime = document.forms[0].planWorkTime.value;
	
	if(planWorkTime == '' || planWorkTime < 1 ){
		alert("0보다 큰 숫자를 입력하세요.");
		return;
	}
	
	if(confirm("최초 등록만 가능하며 수정은 일정변경시 PM만이 가능합니다.\n입력완료하시겠습니까?")){
		document.forms[0].command.value = "update";
		document.forms[0].submit();
	}
}

$(document).ready(function(){
    CommonUtil.singleSelect('delayType',100);
    var taskTypeName = '<%=taskTypeName%>';
    if(document.forms[0].command.value == 'update'){
    	opener.showProcessing();
    	
    	if(taskTypeName == 'Gate'){
    		opener.location.href='/plm/jsp/project/TaskGateView.jsp?oid='+opener.$('[name=oid]').val();
    	}else if(taskTypeName == '신뢰성평가'){
            opener.location.href='/plm/jsp/project/TaskTrustView.jsp?oid='+opener.$('[name=oid]').val();
        }else if(taskTypeName == 'DR'){
            opener.location.href='/plm/jsp/project/TaskAssessView.jsp?oid='+opener.$('[name=oid]').val();
        }else{
    		opener.location.href='/plm/jsp/project/TaskView.jsp?oid='+opener.$('[name=oid]').val();
    	}
        
        
        document.forms[0].command.value = "";
    }
    
    
    
    document.forms[0].command.value = "";
    $(document).on("keyup", "#planWorkTime", function() {$(this).val( $(this).val().replace(/[^\.0-9]/,"") );});
});
</script>
<table border="0" cellpadding="0" cellspacing="0" width="740px">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="740px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="740px" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07163")%><%--테스크 완료--%></td>
                      <td width="10"></td>
                    </tr>
                </table>
                </td>
                <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="715px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                              <!-- 목록  --> <a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
                          </td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      
      
      <form name="projectTaskForm" method="post">
      <input type="hidden" name="command" value="<%=command%>"/>
      <input type="hidden" name="oid" value="<%=oid%>"/>
      
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07164")%><%--테스크--%> : <%=task.getTaskName()%><%--테스크--%></td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <COL width="10%"><COL width="15%"><COL width="10%"><COL width="15%">
      
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07165")%><%--계획시작일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="planStartDate" class="txt_field" style="width: 100%" value=""> -->
                <%=planStartDate %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07166")%><%--계획종료일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="planEndDate" class="txt_field" style="width: 100%" value=""> -->
                <%=planEndDate %>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07167")%><%--실제시작일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="execStartDate" class="txt_field" style="width: 100%" value=""> -->
                <%=execStartDate %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07168")%><%--실제종료일--%></td>
            <td width="*" class="tdwhiteL">
<!--                 <input type="text" name="execEndDate" class="txt_field" style="width: 100%" value=""> -->
                <%=execEndDate %>
            </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07169")%><%--작업시간--%></td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
      
        <COL width="10%"><COL width="15%"><COL width="10%"><COL width="15%">
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07101")%><%--계획작업시간--%></td>
            
            <%if(task.getPlanWorkTime() == 0.0 && isEdit){%>
            <td class="tdwhiteL">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td><input type="text" name="planWorkTime" id="planWorkTime" class="txt_field" style="width: 30%" value="<%=task.getPlanWorkTime()%>"> hr</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                    onclick="JavaScript:updatPlanWorkTime();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            </td>
            <%}else{%>
            <td width="*" class="tdwhiteL">
                <%=task.getPlanWorkTime()%> hr
            </td>
            <%} %>
            
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07122")%><%--실제작업시간--%></td>
            <td width="*" class="tdwhiteL">
                <%=task.getExecWorkTime()%> hr
            </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00992")%><%--근거사유--%>
            </td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <COL width="10%"><COL width="40%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00992")%><%--근거사유--%>
          </td>
          <td width="*" class="tdwhiteL" cospan="3">
<!--               <input type="textarea" name="execWorkTime" class="txt_field" style="width: 100%"> -->
              <pre><%=StringUtil.checkNull(task.getCompReason()) %></pre>
          </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "07170")%><%--지연사유--%></td>
          </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="740px">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="740px">
        <COL width="10%"><COL width="40%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07171")%><%--지연사유유형--%></td>
          <td class="tdwhiteL" cospan="3">
<!--               <input type="text" name="delayType" class="txt_field" style="width: 100%"> -->
              <ket:select id="delayType" name="delayType" className="fm_jmp" style="width: 160px;" disabled="true" codeType="TASKDELAYTYPE" multiple="multiple" value="<%=task.getDelayType() %>"/>
              
<!--               <select name="planWorkTime"> -->
<!--                 <option value="">선택</option> -->
<!--                 <option value="유형1">유형1</option> -->
<!--               </select> -->
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07170")%><%--지연사유--%></td>
          <td width="*" class="tdwhiteL" cospan="3">
<!--               <input type="textarea" name="delayReason" class="txt_field" style="width: 100%"> -->
              <pre><%=StringUtil.checkNull(task.getDelayReason()) %></pre>
          </td>
        </tr>
      </table>
      </form>
    </td>
  </tr>
</table>
<script type="text/javascript">
<!--
<%
String isSuccess = request.getParameter("isSuccess");
if("Y".equals(isSuccess)) {
%>
//opener.reviseSuccess();
//opener.parent.document.location.reload();
opener.goTaskPage();
this.close();
<%
}
%>
//-->
</script>
