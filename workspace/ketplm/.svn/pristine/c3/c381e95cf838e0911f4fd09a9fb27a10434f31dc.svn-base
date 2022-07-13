<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.common.web.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
    Object obj = CommonUtil.getObject(oid);

    TemplateProject project = null;
    TemplateTask task = null;
    QueryResult list = null;
    if ( obj instanceof TemplateProject ) {
        project =(TemplateProject)obj;
        list = ProjectUserHelper.manager.getProjectUser(project);
    } else if ( obj instanceof TemplateTask ) {
        task = (TemplateTask)obj;
        //list = TaskUserHelper.manager.getTaskUser(task);
        project = task.getProject();
        list = ProjectUserHelper.manager.getProjectUser(project);
        //list = ProjectUserHelper.manager.getQueryForTeamUsers(project, "MEMBER");
    }

    String mode =  ParamUtil.getStrParameter(request.getParameter("mode"),"s");
    boolean isMultiSelect = false;
    if ( mode.equalsIgnoreCase("m") ) isMultiSelect = true;
    String function = ParamUtil.getStrParameter(request.getParameter("function"),"thiscolse");

    int listSize = 0;
    if ( list != null ) listSize = list.size();
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01207") %><%--담당자 선택--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="JavaScript">
function viewPeople(v){
    var str="/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+v;
    newWin = window.open(str,"viewPeople", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=550,height=400,resizable=yes,mebar=no,left=40,top=65");
    newWin.focus();
}

function selectAll() {
    var len = <%=listSize%>;
    if (len > 1) {
        for( var i = 0 ; i < len ; i++ ) {
            if ( document.selectTaskPeopleList.checkboxAll.checked == true ) document.selectTaskPeopleList.check[i].checked=true;
            else document.selectTaskPeopleList.check[i].checked=false;
        }
    } else if ( len == 1 ) {
        if ( document.selectTaskPeopleList.checkboxAll.checked == true ) document.selectTaskPeopleList.check.checked=true;
        else document.selectTaskPeopleList.check.checked=false;
    }
}

function clickThis(param) {
    if ( !param.checked ) return;

    var len = <%=listSize%>;
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
    var len = <%=listSize%>;
    if (len > 1) {
        for( i=0;i<len ;i++) {
            if( document.selectTaskPeopleList.check[i].checked == true) count++;
        }
    } else if ( len == 1 ) {
        if( document.selectTaskPeopleList.check.checked == true) count++;
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

function registryUserProjectOutput() {
    if ( fcheck() ) {
        var selectObj;
        var len = <%=listSize%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectTaskPeopleList.check[i].checked == true) selectObj = document.selectTaskPeopleList.check[i];
        } else if ( len == 1 ) {
            if( document.selectTaskPeopleList.check.checked == true) selectObj = document.selectTaskPeopleList.check;
        }

        opener.document.forms[0].registryUserName.value = selectObj.sname;
        opener.document.forms[0].registryUserOID.value = selectObj.value;
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="selectTaskPeopleList">
<input type=hidden name=oid value="<%=oid%>">
<input type=hidden name=mode value="<%=mode%>">
<input type=hidden name=function value="<%=function%>">
<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif>'<%=task==null?"":task.getTaskName()%>' <%=messageService.getString("e3ps.message.ket_message", "02756") %><%--참여 인원 목록--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button class="btnTras" onclick='javascript:<%=function%>();' value='<%=messageService.getString("e3ps.message.ket_message", "01823") %><%--선택항목 추가--%>' id=button>
            <input type=button class="btnTras" onclick='JavaScript:thiscolse()' value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' id=button>
        </td>
    </tr>
</table>
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center">
    <tr  bgcolor="#D8E0E7" align=center height=23>
        <td width="1%" bgcolor="#D8E0E7"><%if(isMultiSelect){%><input type="checkbox" name="checkboxAll" value="all"  onClick="javascript:selectAll();"><%}%>&nbsp;</td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "02723") %><%--직책--%></td>
        <td id=title><%=messageService.getString("e3ps.message.ket_message", "00412") %><%--Project권한--%></td>
        <td id=title>e-mail</td>
    </tr>
<%
    if(list != null) {
        while(list.hasMoreElements()){
            Object[] o = (Object[])list.nextElement();
            ProjectUserData data = null;
            if ( o[0] instanceof ProjectMemberLink ) data = new ProjectUserData((ProjectMemberLink)o[0]);
            else if ( o[0] instanceof TaskMemberLink ) data = new ProjectUserData((TaskMemberLink)o[0]);
%>
    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
        <td  width="20"><input type="checkbox" name="check" value="<%=data.wtuserOID%>" poid="<%=data.peopleOID%>" email="<%=data.email%>" sname="<%=data.name%>" memberType="<%=data.memberType%>" <%if(!isMultiSelect)out.print("onclick='javascript:clickThis(this)'");%>></td>
        <td  >&nbsp;<a href="JavaScript:viewPeople('<%=data.peopleOID%>')"><%=data.name%></a></td>
        <td  >&nbsp;<%=data.departmentName%>&nbsp;</td>
        <td  >&nbsp;<%=data.duty%>&nbsp;</td>
        <td  >&nbsp;<%=data.projectDuty%></td>
        <td  >&nbsp;<%=data.email%></td>
    </tr>
<%    } %>
<%  } %>
</table>
</form>
</body>
</html>
