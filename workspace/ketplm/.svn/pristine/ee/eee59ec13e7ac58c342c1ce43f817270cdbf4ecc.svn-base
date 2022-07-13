<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="wt.fc.*"%>
<%@page import="e3ps.project.*,
        e3ps.project.beans.*,
        e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
  String oid = request.getParameter("oid");
  TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);
  TemplateTaskData data = new TemplateTaskData(task);
  String name = StringUtil.checkNull(request.getParameter("name"));
  String description = StringUtil.checkNull(request.getParameter("description"));
  String command = StringUtil.checkNull(request.getParameter("command"));

  String optionType = StringUtil.checkNull(request.getParameter("optionType"));
  String mileStone = StringUtil.checkNull(request.getParameter("mileStone"));
  String taskType = StringUtil.checkNull(request.getParameter("taskType"));
  String drValue = StringUtil.checkNull(request.getParameter("drValue"));
  String taskTypeCategory = StringUtil.checkNull(request.getParameter("taskTypeCategory"));

  if ( command.equals("update") ) {
    if( !name.equals("") ){
      if(!name.equals(task.getTaskName())){
        task.setTaskName(name);
      }
    }

    task.setOptionType(Boolean.valueOf(optionType).booleanValue());
    task.setMileStone(Boolean.valueOf(mileStone).booleanValue());
    task.setTaskType(taskType);
    task.setTaskTypeCategory(taskTypeCategory);
    task.setDrValue(drValue);

    task.setTaskDesc(description);
    task = TaskHelper.manager.modifyTask(task);
  }
%>
<HTML>
<HEAD>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "00488") %><%--TASK 정보수정--%></TITLE>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript">
<!--
function update() {
  disabledAllBtn();
  showProcessing();
  document.forms[0].submit();
}

<%  if ( command.equals("update") ) {  %>
opener.document.location.reload();
self.close();
<%  }  %>

  function displayChange(){
    form = document.forms[0];
    var tasktypeValue = "";
    for(i=0; i<form.taskType.length; i++) {
      if(form.taskType[i].selected) tasktypeValue = form.taskType[i].value;
    }
   
    if(tasktypeValue == "Gate") {
        $("#taskTypeCategory").show();
    }else {
        $("#taskTypeCategory").hide();
    }
    
    if(tasktypeValue == "DR") {
      drvalueTd1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%>";
      drvalueTd2.innerHTML = "<INPUT id=i size=7 name=drValue value=\"<%=data.drvalue%>\" onkeyup =\"SetNum(this)\" style='IME-MODE: disabled'>";
    }else {
      drvalueTd1.innerHTML = "&nbsp;";
      drvalueTd2.innerHTML = "&nbsp;";
    }
  }
//-->
</script>
<style type="text/css">
<!--
body {
  background-repeat:repeat-x;
  background-color: #ffffff;
  margin-left:5px;
  margin-right:5px;
}
-->
</style>
</HEAD>
<body>
<%@include file="/jsp/common/processing.html"%>
<form name="projectUpdate" method="post">
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=command value='update'>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02547") %><%--제품 태스크 수정--%></td>
                <td width="10"></td>
              </tr>
            </table></td>
          <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02923") %><%--태스크 명--%></td>
                <td width="330" class="tdwhiteL0">&nbsp;<%=task.getTaskName()%></td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td width="330" class="tdwhiteL0">&nbsp;<%=data.duration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%></td>
                <td width="330" class="tdwhiteL0">
          &nbsp;
          <input type="radio" value="true" name="optionType" <%if(task.isOptionType()) {%>checked<%}%>/>Y
          <input type="radio" value="false" name="optionType" <%if(!task.isOptionType()) {%>checked<%}%>/>N
        </td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00324") %><%--Milestone여부--%></td>
                <td width="330" class="tdwhiteL0">
          &nbsp;
          <input type="radio" value="true" name="mileStone" <%if(task.isMileStone()) {%>checked<%}%>/>Y
          <input type="radio" value="false" name="mileStone" <%if(!task.isMileStone()) {%>checked<%}%>/>N
          </td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00502") %><%--Task종류--%></td>
                <td width="330" class="tdwhiteL0">
          &nbsp;
          <select name="taskType" onchange="javascript:displayChange();">
            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
            <%
            if(data.project instanceof e3ps.project.MoldTemplateProject) { %>
            <option value="Try" <%if("Try".equals(data.tasktype)) {%>selected<%}%>>Try</option>
                        <option value="디버깅" <%if("디버깅".equals(data.tasktype)) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01339") %><%--디버깅--%></option>
            <%
            } %>
                        <option value="일반" <%if("일반".equals(data.tasktype)) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "02345") %><%--일반--%></option>
            <%
            if(data.project instanceof e3ps.project.ProductTemplateProject || data.project instanceof e3ps.project.TemplateProject) { %>
            <option value="DR" <%if("DR".equals(data.tasktype)) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "00158") %><%--DR --%></option>
            <option value="Gate" <%if("Gate".equals(data.tasktype)) {%>selected<%}%>>Gate</option>
            <option value="신뢰성평가" <%if("신뢰성평가".equals(data.tasktype)) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "02029") %><%--신뢰성평가 --%></option>
            <%
            } %>
          </select>
          <select name="taskTypeCategory" id="taskTypeCategory" 
                        style="width:65;<% if(!"Gate".equals(task.getTaskType())) out.print("display:none;"); %>" class="fm_jmp">
                        <option value="A" <% if("A".equals(task.getTaskTypeCategory())) out.print("selected"); %>>A</option>
                        <option value="B" <% if("B".equals(task.getTaskTypeCategory())) out.print("selected"); %>>B</option>
                        <option value="0" <% if("0".equals(task.getTaskTypeCategory())) out.print("selected"); %>>0</option>
                        <option value="1" <% if("1".equals(task.getTaskTypeCategory())) out.print("selected"); %>>1</option>
                        <option value="2" <% if("2".equals(task.getTaskTypeCategory())) out.print("selected"); %>>2</option>
                        <option value="3" <% if("3".equals(task.getTaskTypeCategory())) out.print("selected"); %>>3</option>
                        <option value="4" <% if("4".equals(task.getTaskTypeCategory())) out.print("selected"); %>>4</option>
                        <option value="5" <% if("5".equals(task.getTaskTypeCategory())) out.print("selected"); %>>5</option>
                        <option value="6" <% if("6".equals(task.getTaskTypeCategory())) out.print("selected"); %>>6</option>
                        <option value="7" <% if("7".equals(task.getTaskTypeCategory())) out.print("selected"); %>>7</option>
                        <option value="8" <% if("8".equals(task.getTaskTypeCategory())) out.print("selected"); %>>8</option>
                        <option value="9" <% if("9".equals(task.getTaskTypeCategory())) out.print("selected"); %>>9</option>
                        <option value="10" <% if("10".equals(task.getTaskTypeCategory())) out.print("selected"); %>>10</option>
        </select>
        </td>
              </tr>
              <tr>
          <td width="130" class="tdblueL" id="drvalueTd1"><%if("DR".equals(data.tasktype)) {%><%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%><%}%></td>
          <td width="330" class="tdwhiteL0" id="drvalueTd2">
            &nbsp;<%if("DR".equals(data.tasktype)) {%><INPUT id=i size=7 name=drValue value="<%=data.drvalue%>" onkeyup ="SetNum(this)" style='IME-MODE: disabled'><%}%>
        </td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02925") %><%--태스크 설명--%></td>
                <td width="330" class="tdwhiteL0" style="height:100">&nbsp;<textarea name="description" rows="8" id=input style="width:99%"><%=task.getTaskDesc()==null?"":task.getTaskDesc()%></textarea></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space15"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10">&nbsp;</td>
        <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
      </tr>
    </table></td>
  </tr>
</table>

</form>
</BODY>
</HTML>
