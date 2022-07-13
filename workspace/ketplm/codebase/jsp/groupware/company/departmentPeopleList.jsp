<%--
    /**
     * @(#)  companypeople.jsp
     * Copyright (c) e3ps. All rights reserverd
     *
     * @version 1.00
     * @since jdk 1.4.02
     * @author Cho Sung Ok
     */
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,e3ps.common.web.ParamUtil"%>
<%@page import="wt.fc.*,wt.query.*" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*" %>
<%@page import="e3ps.load.groupware.LoadDepartment"%>
<%@page import="e3ps.load.groupware.LoadPeople"%>
<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<jsp:setProperty name="control" property="href" value="/plm/servlet/e3ps/ManagePeopleServlet" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>

<%
    int year = Calendar.getInstance().get(Calendar.YEAR);

    String oid = ParamUtil.getStrParameter(request.getParameter("oid"));

    String sortType = request.getParameter("sortType");
    if(sortType == null){
        sortType = "false";
    }
    Department dept = null;
    String departmentStr = "";

    if ( oid.equals("root") || oid.trim().equals("") ){
        departmentStr = messageService.getString("e3ps.message.ket_message", "03247")/*회사명 : 한국단자*/;
    }
    else {
        dept = (Department)CommonUtil.getObject(oid);
        departmentStr = messageService.getString("e3ps.message.ket_message", "01555")/*부서명*/ + " : " + dept.getName();
    }

    boolean isAdmin = CommonUtil.isAdmin();

    String paramStr = "oid="+oid;
    control.setParam(paramStr);

    Kogger.debug("departmentPeopleList", null, null, "정렬 value : "+ sortType);

%>

<head>
<%@include file="/jsp/common/top_include.jsp" %>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language="javascript">
<!--
function view(id) {
    //document.companypeople.action = "/plm/jsp/groupware/company/peopleView.jsp?oid="+id;
    //document.companypeople.submit();
    var url = "/plm/jsp/groupware/company/peopleView.jsp?oid="+id;
    openWindow(url, 'peopleView', '850', '600');
}

function sorting(value) {
    //alert(document.companypeople.sortType.value);

    document.companypeople.sortKey.value=value;
    //document.companypeople.sortType.value="<%=sortType%>";
    document.companypeople.action = "/plm/servlet/e3ps/ManagePeopleServlet?sessionid=0";
    document.companypeople.submit();
}

function search() {
    if(checkField(document.forms[0].keyvalue, "검색어")) {
        //window.event.returnValue = false;
        return;
    }
    showProcessing();
    disabledAllBtn();
    //document.companypeople.action = "/plm/servlet/e3ps.groupware.company.servlet.ManagePeopleServlet?sessionid=0&page=0";
    document.companypeople.action = "/plm/servlet/e3ps/ManagePeopleServlet?sessionid=0&page=0";
    document.companypeople.submit();
}

function setDept(v){
    var url = "/plm/jsp/groupware/company/setDepartmentForm.jsp?oid="+v;
    openWindow(url,'Dept',"550","400");
}

function setDuty(){
    var url = "/plm/jsp/groupware/company/setDutyForm.jsp";
    openWindow(url,'Duty', "550","400");
}
function viewProjectHistory(userOid){
    var url = "/plm/jsp/groupware/company/userProjectList.jsp?userOid=" + userOid;
    openWindow(url, "P", 800, 600);
//  openOtherName(url,"viewEducationHistory","800","640","status=no,scrollbars=yes,resizable=no");
}

function EHRSync(){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03324") %><%--인사 정보를  동기화  하시겠습니까?--%>")) {
            return;
    }

    showProcessing();
    disabledAllBtn();
    document.companypeople.action = "/plm/servlet/e3ps/ManagePeopleServlet?cmd=erp";
    document.companypeople.submit();

    //document.companypeople.action = "/plm/jsp/groupware/company/EHRSync.jsp";
    //document.companypeople.submit();
}

function onKeyPress() {
    if (window.event) {
        if (window.event.keyCode == 13) search();
    } else return;
}
document.onkeypress = onKeyPress;

    function viewTodo(oid){
        var url = "/plm/jsp/project/ListMyTask.jsp?wtUser="+oid;
        openOtherName(url,"ViewTemplate","800","600","status=1,scrollbars=yes,resizable=1");
    }
//-->
</script>
</head>
<body topmargin="0" leftmargin="0" onload='document.forms[0].keyvalue.focus()'>
<%@include file="/jsp/common/processing.html"%>

<form method="post" name="companypeople">
<input type="hidden" name="cmd" >
<input type="hidden" name="sortKey">
<input type="hidden" name="sortType" value="<%=sortType%>">
<input type="hidden" name="oid" value="<%=oid%>">

<table width=95% cellpadding=0 cellspacing=0 border=0>
    <tr>
        <td><br></td>
    </tr>
</table>
<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=<%=imageUrl%>/title3_left.gif></td>
                    <td background=<%=imageUrl%>/title_back.gif nowrap><%=messageService.getString("e3ps.message.ket_message", "01542") %><%--부서 및 사원 관리--%> <b>[<%= departmentStr %>]</b></td>
                    <td><img src=<%=imageUrl%>/title3_right.gif></td>
                </tr>
                <!--tr>
                    <td><img src=<%=imageUrl%>/title3_left.gif></td>
                    <td background=<%=imageUrl%>/title_back.gif nowrap>
                        <a href="/plm/jsp/groupware/company/msSQLServerConnectionTest.jsp" target="_blank">SQL연결 테스트</a>
                    </td>
                    <td><img src=<%=imageUrl%>/title3_right.gif></td>
                </tr-->
            </table>
        </td>
        <%//if(CommonUtil.isAdmin()){%>
        <!--td>
            <a href="javascript:EHRSync();">
            <img src="/plm/portal/images/img_default/button/board_btn_ehr.gif" alt="EHR 동기화" width="98" height="20" border="0" align="right">
            </a-->
            <!--input type="button" value="EHR 동기화" Onclick="javascript:EHRSync()"-->
        <!--/td-->
        <%//} %>

        <input type="hidden" name="key" value="<%=People.NAME%>">
        <td align="right" height="20">
            <%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%>  :  <input type="text" name="keyvalue" size=15  class="txt_field" />
            <a href="javascript:search();"><img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0" align="right"></a>
            <!--input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onclick="javascript:search();"-->
            <%
                //if(false) {
                    if( !( oid.equals("root") || oid.trim().equals("") ) ) {
            %>
            &nbsp;
                <a href="javascript:setDept('<%=oid%>');">
                <img src="/plm/portal/images/img_default/button/board_btn_post.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01545") %><%--부서 설정--%>" width="76" height="20" border="0" align="right">
                </a>
                <!--input type=button onclick="javascript:setDept('<%//=oid%>')" value="<%=messageService.getString("e3ps.message.ket_message", "01561") %><%--부서설정--%>" id=button-->
            <%    } else { %>
                <a href="javascript:setDuty();">
                <img src="/plm/portal/images/img_default/button/board_btn_position.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "02719") %><%--직위설정--%>' width="76" height="20" border="0" align="right">
                </a>
                <!--input type=button onclick="javascript:setDuty()" value="직위설정" id=button-->
            <%    }
                //}
            %>
        </td>
        <!--td>
            <a href="javascript:search();"><img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="검색" width="52" height="20" border="0" align="right"></a>
        </td-->
    </tr>
</table>

<!---리스트------->
<table width="95%" border="0" cellpadding="2" cellspacing="1" bgcolor=AABCC7 align=center>
    <tr height=23 bgcolor="#D9E2E7" align=center>
        <td nowrap nowrap  class="tdblueL">NO</td>
        <%if(true){%><td nowrap class="tdblueL"><a href="javascript:sorting('<%=People.ID%>');" id=title><%=messageService.getString("e3ps.message.ket_message", "02074") %><%--아이디--%></A></td><%}%>
        <td nowrap class="tdblueL">
        <a href="javascript:sorting('<%=People.NAME%>');"  id=title><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></A>
        </td>
        <td nowrap class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
        <td nowrap class="tdblueL"><a href="javascript:sorting('<%=People.DUTY%>');"  id=title><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></A></td>
        <td nowrap class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01562") %><%--부서장--%></td>
        <td nowrap class="tdblueL"><a href="javascript:sorting('<%=People.EMAIL%>');"  id=title>e-mail</A></td>
        <%if(isAdmin){%>
        <td nowrap class="tdblueL"><a href="javascript:sorting('<%=People.IS_DISABLE%>');"  id=title><%=messageService.getString("e3ps.message.ket_message", "00151") %><%--Disable 여부--%></A></td>
        <%}%>
        <td nowrap class="tdblueL" width="80"><%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%></td>
        <!--td nowrap id=title>Project<br>수행이력</td-->
    </tr>
<%
    String rowCount = "8";
    if ( isAdmin ) rowCount = "9";
    PagingQueryResult result = control.getResult();
    int listCount = control.getTopListCount();
    int count = 0;
    while ( result.hasMoreElements() ) {
        Object[] obj = (Object[])result.nextElement();
        PeopleData data = new PeopleData(obj[0]);
        String wtuserOID = CommonUtil.getOIDString(data.wtuser);
%>
    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
        <td nowrap class="tdwhiteL">&nbsp;<%=listCount-count++%></td>
        <%if(true){%>
        <td class="tdwhiteL"><a href="javascript:view('<%= CommonUtil.getOIDString(data.people) %>')">&nbsp;<%= data.id %></a></td>
        <%}%>
        <td nowrap class="tdwhiteL">
        <%if(true){%>
        <a href="javascript:view('<%= CommonUtil.getOIDString(data.people) %>')">&nbsp;<%= data.name %></a>
        <%}else{ %>
        <%=data.name%>
        <%} %>
        </td>
        <td nowrap class="tdwhiteL">&nbsp;<%= StringUtil.checkNull(data.departmentName) %></td>
        <td nowrap class="tdwhiteL">&nbsp;<%= StringUtil.checkNull(data.duty) %></td>
        <td nowrap class="tdwhiteL">&nbsp;<% if(StringUtil.checkString(data.chief)) { %><%=StringUtil.checkNull(data.chief)%> <%=messageService.getString("e3ps.message.ket_message", "01562") %><%--부서장--%><% } %>
        <td nowrap class="tdwhiteL">&nbsp;<%= StringUtil.checkNull(data.email) %></td>
        <!--td  nowrap  class=licommon><a href="javascript:viewProjectHistory('<%=CommonUtil.getOIDLongValue(data.wtuser)%>');">수행이력</a>
        </td-->
        <%if(isAdmin){%>
        <td nowrap class=licommon_left>&nbsp;<%=data.isDiable%></td>
        <%} %>
        <td class="tdwhiteM">&nbsp;
            <a href="javascript:viewTodo('<%=wtuserOID %>');">
            <img src="/plm/portal/images/img_default/button/but2_list.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%>" width="21" height="20" border="0">
            </a>
        </td>

    </tr>
<%  }  %>
<%  if(control.getTotalCount() == 0) {  %>
    <tr bgcolor="#FFFFFF">
        <td colspan="<%=rowCount%>" height="25" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></font></td>
    </tr>
<%  }  %>
</table>
<!--여백테이블//-->
<table border=0 align=center cellpadding=0 cellspacing=0><tr><td height=4></td></tr></table>
<!--//-->
<table width=95% align=center cellpadding=0 cellspacing=0 border=0><tr><td>
<% control.setPostMethod(); %>
<%@include file="/jsp/common/page_include.jsp" %>
</td></tr></table>
</form>
</body>
</html>
