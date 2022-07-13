<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*" %>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
  String mode = request.getParameter("mode");
  String oid = request.getParameter("oid");

  String invokeMethod = request.getParameter("invokeMethod");

  if(invokeMethod == null)
    invokeMethod = "";

  if(mode == null)
    mode = "one";

  TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);
  TemplateProject project = task.getProject();

  ArrayList list = new ArrayList();

  Persistable parent = ProjectTaskHelper.manager.getParent(task);
  if(parent instanceof TemplateTask){
    TemplateTask pTask = (TemplateTask)parent;

    ArrayList plist = TaskDependencyHelper.manager.getCanDependTaskList(pTask);
    for(int i=0; i< plist.size(); i++){
      TemplateTask canDependTask = (TemplateTask)plist.get(i);
      QueryResult qr = ProjectTaskHelper.manager.getChildList(canDependTask);
      while(qr.hasMoreElements()){
        TemplateTask cTask = (TemplateTask)qr.nextElement();
        list.add(cTask);
      }
    }
  }
  list.addAll(TaskDependencyHelper.manager.getCanDependTaskList(task));
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01830") %><%--선행Task 선택--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript>
<!--
function doSubmit() {
  form = document.forms[0];
  var wname = form.wbsName.value;
  var wcode = form.wbsCode.value;

  form.method = "post";
  form.action= "/plm/jsp/project/wbs/ProjectWbsListTable.jsp?isDisabled=false&wbsName=" + wname + "&wbsCode=" + wcode;
  form.target = "list";
  form.submit();
}

function checkAll() {
  form = document.forms[0];
  if(form.oid) {
    var chkLen = form.oid.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        form.oid[i].checked = form.chkAll.checked;
      }
    }else{
      form.oid.checked = form.chkAll.checked;
    }
  }
}

function isCheckedCheckBox() {
  form = document.forms[0];
  if(form.oid == null) {
    return false;
  }

  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.oid[i].checked == true) {
        return true;
      }
    }
  }
  else {
    if(form.oid.checked == true) {
      return true;
    }
  }

  return false;

}

function checkList() {
  form = document.forms[0];

  var arr = new Array();
  var subarr = new Array();//0:oid
  if(!isCheckedCheckBox()) {
    return arr;
  }

  len = form.oid.length;

  var idx = 0;
  if(len) {
    for(var i = 0; i < len; i++) {
      if(form.oid[i].checked == true) {
        subarr = new Array();
        subarr[0] = form.oid[i].value;//oid
        arr[idx++] = subarr;
      }
    }
  } else {
    if(form.oid.checked == true) {
      subarr = new Array();
      subarr[0] = form.oid.value;//oid
      arr[idx++] = subarr;
    }
  }

  return arr;
}


function onSelect() {
  form = document.forms[0];

  if(document.forms[0].oid == null) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>");
    return;
  }

  var arr = checkList();
  if(arr.length == 0) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01805") %><%--선택된 Task가 없습니다--%>");
    return;
  }

<%  if(invokeMethod.length() == 0) {  %>
  //modal dialog
  selectModalDialog(arr);
<%  } else {  %>
  //open window
  selectOpenWindow(arr);
<%  }  %>

}

function selectModalDialog(arrObj) {
  window.returnValue= arrObj;
  window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function selectOpenWindow(arrObj) {
  //...이하 원하는 스크립트를 만들어서 작업...
  if(opener) {
    if(opener.<%=invokeMethod%>) {
      opener.<%=invokeMethod%>(arrObj);
    }
  }

  if(parent.opener) {
    if(parent.opener.<%=invokeMethod%>) {
      parent.opener.<%=invokeMethod%>(arrObj);
    }
  }
  self.close();
}

<%  }  %>
//-->
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form method=post>
<table border="0" cellspacing="0" cellpadding="0" class="tab_w03">
  <tr>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="popBox" width="100%">
        <tr>
          <td class="boxTLeft"></td>
          <td class="boxTCenter"></td>
          <td class="boxTRight"></td>
        </tr>
        <tr>
          <td class="boxLeft"></td>
          <td>
            <!-------------------------------------- 상단버튼 시작 //-------------------------------------->
            <table border="0" cellspacing="0" cellpadding="0"  width="100%">
              <tr>
                <td class="titleP"><%=messageService.getString("e3ps.message.ket_message", "01830") %><%--선행Task 선택--%></td>
                <td align="right" width='70%'>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class=fixLeft></td>
                      <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" onClick="Javascript:onSelect();"></td>
                      <td class=fixRight></td>
                      <td>&nbsp;</td>
                      <td class=fixLeft></td>
                      <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="Javascript:self.close();"></td>
                      <td class=fixRight></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!-------------------------------------- 상단버튼 끝 //-------------------------------------->

            <!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellpadding="0" cellspacing="0"  width="100%">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0"  width="100%">
              <tr>
                <td class="tab_btm1"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0"  width="100%">
              <tr>
                <td class="tdblueM" width="50">
                  <%if("multi".equals(mode)){%>
                    <input name="chkAll" type="checkbox" class="Checkbox" onclick="javascript:checkAll();">
                  <%}else{%>
                    &nbsp;
                  <%}%>
                </td>
                <td class="tdblueL" width="200"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                                <td class="tdblueM0" width="80"><%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%></td>
              </tr>
<%
  if(list.size() == 0) {
%>
              <tr>
                <td class="tdwhiteM0" colspan='4'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
              </tr>
<%
  }
  else {
    TemplateTask canDependTask = null;
    TemplateTaskData data = null;
    for ( int i = 0 ; i < list.size() ; i++ ) {
      canDependTask = (TemplateTask)list.get(i);
      data = new TemplateTaskData(canDependTask);

%>
              <tr bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'" >
                <td class="tdwhiteM">
                  <input name="oid" type="checkbox" class="Checkbox" <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%>
                          value='<%=data.taskOID%>'/>
                </td>
                <td class="tdwhiteL"><%=data.name%></td>
                <td class="tdwhiteM"><%=data.duration%></td>
                <td class="tdwhiteM0">&nbsp;</td>
              </tr>
<%
    }
  }

%>
            </table>
            <!------------------------------ 본문 끝 //------------------------------>
          </td>
          <td class="boxRight"></td>
        </tr>
        <tr>
          <td class="boxBLeft"></td>
          <td valign="bottom" class="boxBCenter"></td>
          <td class="boxBRight"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
