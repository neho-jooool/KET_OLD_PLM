<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.web.ParamUtil,
                e3ps.common.util.CommonUtil" %>
<%@page import="wt.fc.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = request.getParameter("oid");
    TemplateProject project =(TemplateProject)CommonUtil.getObject(oid);
    QueryResult list = ProjectUserHelper.manager.getProjectUser(project);
    String mode =  ParamUtil.getStrParameter(request.getParameter("mode"),"s");

    String taskOid = request.getParameter("taskOid");
    if(taskOid == null){
        taskOid = "";
    }

    String workItemOid = request.getParameter("workItemOid");
    //out.println(workItemOid);
    if(workItemOid == null){
        workItemOid = "";
    }

    boolean isMultiSelect = false;

    if ( mode.equalsIgnoreCase("m") ) isMultiSelect = true;

    Hashtable ht = new Hashtable();

    while(list.hasMoreElements()){
        Object[] o = (Object[])list.nextElement();
        ProjectUserData data = new ProjectUserData((ProjectMemberLink)o[0]);
        if(data.projectDuty.equals("구성원")){
            ht.put(data.peopleOID, data);
        }
    }
    list.reset();

    while(list.hasMoreElements()){
        Object[] o = (Object[])list.nextElement();
        ProjectUserData data = new ProjectUserData((ProjectMemberLink)o[0]);
        if(!data.projectDuty.equals("구성원")){
            ht.put(data.peopleOID,data);
        }
    }

    Enumeration e = ht.keys();
    Vector vt = new Vector();
    ComparatorUtil comparator = new ComparatorUtil();

    try{
        while(e.hasMoreElements()){
            Object oo = (Object)e.nextElement();
            String key = (String)oo;
            ProjectUserData dataSet = (ProjectUserData)ht.get(key);
            vt.add(dataSet);
        }
    }catch(Exception e1){
	Kogger.error(e1);
    }
    Collections.sort(vt, comparator);




%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Collections"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03100") %><%--프로젝트 참여자 목록--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
function viewPeople(oid){
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openOtherName(url,"process","550","400","status=no,scrollbars=no,resizable=no");
}

function selectAll() {
    var len = <%=vt.size()%>;
    if (len > 1) {
        for( var i = 0 ; i < len ; i++ ) {
            if ( document.selectProjectPeopleList.checkboxAll.checked == true ) document.selectProjectPeopleList.check[i].checked=true;
            else document.selectProjectPeopleList.check[i].checked=false;
        }
    } else if ( len == 1 ) {
        if ( document.selectProjectPeopleList.checkboxAll.checked == true ) document.selectProjectPeopleList.check.checked=true;
        else document.selectProjectPeopleList.check.checked=false;
    }
}

function clickThis(param) {
    if ( !param.checked ) return;

    var len = <%=vt.size()%>;
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
    var len = <%=vt.size()%>;

    if (len > 1) {
        for( i=0;i<len ;i++) {
            if( document.selectProjectPeopleList.check[i].checked == true) count++;
        }
    } else if ( len == 1 ) {
        if( document.selectProjectPeopleList.check.checked == true) count++;
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

function addWFInformTask() {
    if ( fcheck() ) {
        var paramStr = "";
        var len = <%=vt.size()%>;
        if (len > 1) {
            for( i=0;i<len ;i++)
                if( document.selectProjectPeopleList.check[i].checked == true) paramStr = paramStr + "addMaster=" + document.selectProjectPeopleList.check[i].value + "&taskOid=<%=taskOid%>&workItemOid=<%=workItemOid%>";
        } else if ( len == 1 ) {
            if( document.selectProjectPeopleList.check.checked == true) paramStr = "addMaster=" + document.selectProjectPeopleList.check.value  + "&taskOid=<%=taskOid%>&workItemOid=<%=workItemOid%>";
        }

        opener.document.forms[0].method="post";
        opener.document.forms[0].action = "/plm/jsp/groupware/workprocess/DefaultProjectLine.jsp?"+paramStr;
        opener.document.forms[0].addMaster.value = document.selectProjectPeopleList.check.value ;
        opener.document.forms[0].taskOid.value= "<%=taskOid%>";
        opener.document.forms[0].workItemOid.value= "<%=workItemOid%>";

        opener.document.forms[0].submit();
    }
<%  if ( mode.equals("s") ) out.print("thiscolse();");%>
}


</script>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>
<body>
<form name="selectProjectPeopleList">
<input type=hidden name=oid value="<%=oid.trim()%>">
<input type=hidden name=taskOid value="<%=taskOid.trim()%>">
<input type=hidden name=workItemOid value="<%=workItemOid.trim()%>">
<input type=hidden name=mode value="<%=mode.trim()%>">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%> '<%=project.getPjtName()%>' <%=messageService.getString("e3ps.message.ket_message", "02756") %><%--참여 인원 목록--%></td>
                <td align="right" style="padding:8px 0px 0px 0px"></td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table><table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:addWFInformTask();">
                        <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:self.close();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="tdblueM" width="20"><%if(isMultiSelect){%><input type="checkbox" name="checkboxAll" value="all"  onClick="javascript:selectAll();"><%}%>&nbsp;</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02723") %><%--직책--%></td>
                    <td class="tdblueM">Role</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03110") %><%--프로젝트권한--%></td>
                    <td class="tdblueM">e-mail</td>
                </tr>
        <%
        for(int x = 0 ; x < vt.size(); x++){
            ProjectUserData data = (ProjectUserData)vt.get(x);
        %>
                    <tr>
                        <td class="tdwhiteM" width="1%">
                            <input type="checkbox" name="check"
                            value="<%=data.wtuserOID%>"
                            poid="<%=data.peopleOID%>"
                            email="<%=data.email%>"
                            sname="<%=data.name%>"
                            memberType="<%=data.memberType%>"
                            <%if(!isMultiSelect)out.print("onclick='javascript:clickThis(this)'");%>>

                        </td>
                        <td class="tdwhiteM">&nbsp;<a href="JavaScript:viewPeople('<%=data.peopleOID%>')"><%=data.name%></a></td>
                        <td class="tdwhiteM">&nbsp;<%=data.departmentName%></td>
                        <td class="tdwhiteM">&nbsp;<%=data.duty%></td>
                        <td class="tdwhiteM">&nbsp;<%=data.role_ko%></td>
                        <td class="tdwhiteM">&nbsp;<%=data.projectDuty%></td>
                        <td class="tdwhiteM">&nbsp;<%=data.email%></td>
                    </tr>
        <%} if ( vt.size() == 0 ) {%>
                    <tr>
                        <td class='tdwhiteM0' align='center' colspan='7'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                    </tr>
        <%  }
        %>
            </table>
        </td>
    </tr>
</table>

</form>
</body>
</html>
