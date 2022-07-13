<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="wt.fc.*"%>
<%@page import ="e3ps.common.util.*"%>
<%@page import ="e3ps.project.*,e3ps.project.beans.*"%>
<%@page import ="e3ps.wbs.*,e3ps.wbs.service.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
  String oid = request.getParameter("oid");
  if(oid == null)
    oid = "";

  ReferenceFactory rf = new ReferenceFactory();
  TemplateTask templateTask = (TemplateTask)rf.getReference(oid).getObject();
  TemplateTaskData data = new TemplateTaskData(templateTask);

  boolean isWbs = false;
  QueryResult existResults = WBSItemHelper.getWbsForTaskTemplate(templateTask.getProject(), templateTask);
  if(existResults.hasMoreElements()) {
    isWbs = true;
  }
%>
<html>
<head>
<base target="_self">
<title>Project Template Register Page</title>
<%@include file="/jsp/common/top_include.jsp" %>
<script language="JavaScript">
function numCheck(source) {
  var sObj = document.getElementById(source);
  var sValue = sObj.value;
  if(sValue == '') {
    return;
  }

  if(isNaN(sValue)) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01984") %><%--숫자만 입력하십시오--%>');
    var newStr = '';
    for(var i=0; i < sValue.length;i++) {
      if(!isNaN(sValue.substring(i, i+1))) {
        newStr += sValue.substring(i, i+1);
      }
    }
    sObj.value = newStr;
    return;
  }
}

function onMessage(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
  } else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
  }

  parent.parent.tree.document.location.href = "/plm/jsp/project/template/ProjectTempTaskTree.jsp?oid=<%=oid%>";
  document.location.href = "/plm/jsp/project/template/ProjectTempTaskInfoEdit.jsp?oid=<%=oid%>";

}

function tmpRegister(tid) {
  form  = document.forms[0];

  if(form.name.value == '') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00483") %><%--Task 명을 입력하시기 바랍니다--%>");
    form.name.focus();
    return;
  }

  if(form.duration.value == '') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01113") %><%--기간을 입력해 주십시오--%>");
    form.duration.focus();
    return;
  }

  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>")) {
    return;
  }

  var param = "command=modifytask&oid=<%=data.taskOID%>";
  param += "&name=" + form.name.value;
  param += "&duration=" + form.duration.value;
  param += "&description=" + form.description.value;

  onProgressBar();

  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp?" + param;
  callServer(url, onMessage);

}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="ajaxProgress.html"%>
<form method="post">
<!-- Hidden Value -->
<input type="hidden" name="command" value="init">
<input type="hidden" name="oid" value="<%=data.taskOID%>">
<input type="hidden" name="projectOid" value="<%=data.projectOID%>">
<!-- //Hidden Value -->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding-left:0px;padding-top:10px;">
<!-------------------------------------- 상단버튼 시작 //-------------------------------------->
      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
        <tr>
          <td>
            <table border=0 cellpadding=0 cellspacing=0 >
              <tr>
                <td><img src=/plm/portal/images/title2_left.gif></td>
                <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "00486") %><%--Task 정보--%></td>
                <td><img src=/plm/portal/images/title2_right.gif></td>
              </tr>
            </table>
          </td>
          <td align="right">
          <%
            if(!isWbs)
            {
          %>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>' onClick="Javascript:tmpRegister();"></td>
                <td class=fixRight></td>
              </tr>
            </table>
          <%
            }
            else
            {
              out.println("&nbsp;");
            }
          %>
          </td>
        </tr>
      </table>
<!-------------------------------------- 상단버튼 끝 //-------------------------------------->
<!------------------------------ 본문 시작 //------------------------------>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <table class="tab_w01" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
        <col width="15%"><col width="85%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%><span class="style1"> *</span></td>
          <td class="tdwhiteL0">
            <input name="name" class="txt_field" style="width:100%;" value="<%=data.name%>" <%if(isWbs){%>disabled<%}%>>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
          <td class="tdwhiteL0">
            <input name="duration" class="txt_field" style="width:10%;text-align:right;" value="<%=data.duration%>" onkeyup="javascript:numCheck('duration');" <%if(isWbs){%>disabled<%}%>> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdwhiteL0">
            <textarea name="description" cols="110" rows="3" class="fm_area"onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)" <%if(isWbs){%>disabled<%}%>><%=data.description%></textarea>
          </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
<!------------------------------ 본문 끝 //------------------------------>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
