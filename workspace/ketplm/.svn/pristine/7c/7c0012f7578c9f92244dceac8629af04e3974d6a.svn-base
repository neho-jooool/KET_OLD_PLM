<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="wt.fc.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.common.web.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
    TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);
    TemplateProject project = task.getProject();

    ArrayList list = ProjectTaskHelper.getDependencyList(task);



/*
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
    */

    String mode =  ParamUtil.getStrParameter(request.getParameter("mode"),"s");
    boolean isMultiSelect = false;
    if ( mode.equalsIgnoreCase("m") ) isMultiSelect = true;
    String function = ParamUtil.getStrParameter(request.getParameter("function"),"thiscolse");

%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03055") %><%--프로젝트 Task 목록--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
function selectAll() {
    var len = <%=list.size()%>;
    if (len > 1) {
        for( var i = 0 ; i < len ; i++ ) {
            if ( document.selectProjectTaskList.checkboxAll.checked == true ) document.selectProjectTaskList.check[i].checked=true;
            else document.selectProjectTaskList.check[i].checked=false;
        }
    } else if ( len == 1 ) {
        if ( document.selectProjectTaskList.checkboxAll.checked == true ) document.selectProjectTaskList.check.checked=true;
        else document.selectProjectTaskList.check.checked=false;
    }
}

function clickThis(param) {
    if ( !param.checked ) return;

    var len = <%=list.size()%>;
    var checkStr = param.value;

    var objArr = document.forms[0];
    if (len > 1) {
        for ( var i = 0 ; i < objArr.length ; i++ ) {
            if ( objArr[i].type == "checkbox" ) {
                if ( checkStr != objArr[i].value ) {
                    objArr[i].checked = false;
                }
            }
        }
    }
}

function fcheck() {
    var count = 0;
    var len = <%=list.size()%>;
    if (len > 1) {
        for( i=0;i<len ;i++) {
            if( document.selectProjectTaskList.check[i].checked == true) {
                count++;
            }
        }
    } else if ( len == 1 ) {
        if( document.selectProjectTaskList.check.checked == true) count++;
    }

    if(count==0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01813") %><%--선택된 항목이 없습니다--%>");
        return false;
    }
    return true;
}

function thiscolse(){
    self.close();
}

function addTaskUseTask() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=list.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectTaskList.check[i].checked == true) paramStr = paramStr + "addTask=" + document.selectProjectTaskList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectTaskList.check.checked == true) paramStr = "addTask=" + document.selectProjectTaskList.check.value;
        }
        opener.document.forms[0].method="post";
        opener.document.forms[0].action = "/plm/jsp/project/TaskView.jsp?"+paramStr;
        opener.document.forms[0].submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}

function addTaskUseTemplateTask() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=list.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectTaskList.check[i].checked == true) paramStr = paramStr + "addTask=" + document.selectProjectTaskList.check[i].value + "&";
        } else if ( len == 1 ) {
            if( document.selectProjectTaskList.check.checked == true) paramStr = "addTask=" + document.selectProjectTaskList.check.value;
        }
        opener.document.forms[0].method="post";
        opener.document.forms[0].action = "/plm/jsp/project/template/TemplateTaskView.jsp?"+paramStr;
        opener.document.forms[0].submit();
    }
<%//  if ( mode.equals("s") ) out.print("thiscolse();");%>
    thiscolse();
}
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form name="selectProjectTaskList">
<input type=hidden name=oid value="<%=oid%>">
<input type=hidden name=mode value="<%=mode%>">
<input type=hidden name=function value="<%=function%>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
          <table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
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
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:<%=function%>();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
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
              <%if(isMultiSelect){ %>
                  <td width="40" class="tdblueM"><input type="checkbox" name="checkboxAll" value="" onclick="JavaScript:selectAll()"></td>
              <%}else{%>
                <td width="40" class="tdblueM">&nbsp;</td>
              <%}%>
                <td width="220" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02923") %><%--태스크 명--%></td>
                <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td width="80" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%></td>
              </tr>
              <%
    for ( int i = 0 ; i < list.size() ; i++ ) {
        TemplateTask canDependTask = (TemplateTask)list.get(i);
        TemplateTaskData data = new TemplateTaskData(canDependTask);
%>
                <tr onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                    <td class="tdwhiteM"><input type="checkbox" name="check" value="<%=data.taskOID%>" <%if(!isMultiSelect)out.print("onclick='javascript:clickThis(this)'");%>>          </td>
                    <td class="tdwhiteL">&nbsp;<%=data.name%></td>
                    <td class="tdwhiteM"><%=data.duration%><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                    <td class="tdwhiteL0">&nbsp;<%//=data.getManagerStr()%></td>
                </tr>
<%  }  %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            </td>
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
</body>
</html>
