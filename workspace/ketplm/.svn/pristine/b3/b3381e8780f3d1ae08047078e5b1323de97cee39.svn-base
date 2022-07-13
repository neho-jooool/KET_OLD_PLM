<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.fc.*"%>
<%@page import = "e3ps.common.util.*,
                  e3ps.project.beans.*,
                  e3ps.project.*,java.util.*"%>
<%@page import="java.text.Collator"%>

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


<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03063") %><%--프로젝트 구성원 정보--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<script language="JavaScript">
function viewPeople(v){
    var str="/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+v;
    newWin = window.open(str,"viewPeople", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=550,height=400,resizable=yes,mebar=no,left=40,top=65");
    newWin.focus();
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
<!-- Hidden Value -->
<input type=hidden name=oid value="<%=oid%>">
<!-- //Hidden Value -->
<!-- title제목 시작 //-->
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03062") %><%--프로젝트 구성원 목록--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03062") %><%--프로젝트 구성원 목록--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>


<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding-left:12">
<!------------------------------ 본문 시작 //------------------------------>
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
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueM">NO</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03110") %><%--프로젝트권한--%></td>
                    <td class="tdblueM">E-Mail</td>
                </tr>
<%
    for(int x = 0 ; x < vt.size(); x++){
        ProjectUserData data = (ProjectUserData)vt.get(x);
%>
                <tr>
                    <td class="tdwhiteM">&nbsp;<%=(x + 1)%></td>
                    <td class="tdwhiteM">&nbsp;<a href="JavaScript:viewPeople('<%=data.peopleOID%>')"><%=data.name%></a></td>
                    <td class="tdwhiteM">&nbsp;<%=data.departmentName%></td>
                    <td class="tdwhiteM">&nbsp;<%=data.duty%></td>
                    <td class="tdwhiteM">&nbsp;<%=data.projectDuty%></td>
                    <td class="tdwhiteM">&nbsp;<a href="mailto:<%=data.email%>"><%=data.email%></a></td>
                </tr>
<%  }  %>
<%  if ( vt.size() == 0 ) {  %>
                <tr>
                    <td class='tdwhiteM0' align='center' colspan='6'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
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
