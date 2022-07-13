<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="
                java.util.*,e3ps.common.query.SearchUtil,e3ps.common.web.ParamUtil,
                wt.fc.*,wt.org.*,wt.query.*,
                e3ps.common.util.*,e3ps.groupware.company.*"
%>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String userName = request.getParameter("userName");
    if(userName == null){
        userName = "";
    }
    QuerySpec spec = new QuerySpec();
    Class peopleClass = People.class;
    int peopleClassPos = spec.addClassList(peopleClass,true);

    if(userName.length() > 0 ){
        spec.appendWhere(new SearchCondition(People.class, "name", SearchCondition.LIKE, "%"+userName+"%" ), new int[] {peopleClassPos});

    }

    SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, People.NAME, "sort", false);



    Kogger.debug("setDutyForm", null, null, spec);
    Vector allUser = new Vector();
    QueryResult result = PersistenceHelper.manager.find(spec);
    while ( result.hasMoreElements() ) {
        Object[] obj = (Object[])result.nextElement();
        allUser.add(obj[0]);
    }

    Vector dutyUser = new Vector();
    String duty = ParamUtil.getStrParameter(request.getParameter("duty"));
    if ( !duty.equals("") ) {
        spec = new QuerySpec();
        peopleClass = People.class;
        peopleClassPos = spec.addClassList(peopleClass,true);
        spec.appendWhere(new SearchCondition(peopleClass,People.DUTY_CODE,"=",duty),new int[]{peopleClassPos});
        SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, People.DUTY_CODE, "sort", false);
        result = PersistenceHelper.manager.find(spec);

        while ( result.hasMoreElements() ) {
            Object[] obj = (Object[])result.nextElement();
            People people = (People)obj[0];
            String peopleOID = CommonUtil.getOIDString(people);
            dutyUser.add(peopleOID);
        }
    }
    result.reset();
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02719") %><%--직위설정--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
<script>
    function SearchObj() {
        document.forms[0].action = "/plm/jsp/groupware/company/setDutyForm.jsp";
        document.forms[0].method = "post";
        document.forms[0].submit();

    }
</script>
</head>
<body leftmargin="0" topmargin="0">
<%@include file="/jsp/common/processing.html"%>
<form method="post" autocomplete="off">
<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/icon/title2_left.gif></td>
                    <td background=/plm/portal/icon/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "02719") %><%--직위설정--%></td>
                    <td><img src=/plm/portal/icon/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%>' onclick="javascript:setDept()">&nbsp;<input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onclick="javascript:closeWindow()">
        </td>
    </tr>
</table>
<!---리스트------->
<table width="95%" border="0" cellpadding="2" cellspacing="1" bgcolor=AABCC7 align=center>
    <tr height=23 bgcolor="#D9E2E7" align=center>
        <td width="45%" id=title><%=messageService.getString("e3ps.message.ket_message", "02717") %><%--직위별 등록 인원--%></td>
        <td width="10%" id=textblue></td>
        <td width="45%" id=title><%=messageService.getString("e3ps.message.ket_message", "02718") %><%--직위별 미등록 인원--%></td>
    </tr>
    <tr height=23 bgcolor="#FFFFFF" align="center">
        <td width="45%" id=textblue>
            <select name="duty" id=i style="width:50%;" onchange="javascript:changeDuty();">
                <%if(duty.equals("")){%><option value="-1"><%=messageService.getString("e3ps.message.ket_message", "02713") %><%--직위선택--%></option><%}%>
 <%
     Vector dutyNameList = CompanyState.dutyNameList;
     Vector dutyCodeList = CompanyState.dutyCodeList;
     for ( int i = 0; i < dutyCodeList.size() ; i++ ) {
 %>
                <option value="<%=dutyCodeList.get(i)%>" <%if(duty.equals((String)dutyCodeList.get(i)))out.print("selected");%>><%=dutyNameList.get(i)%></option>
 <% }  %>
            </select>
        </td>
        <td width="10%" id=textblue valign="middle" rowspan=2>
            <a href="javascript:leftMove();"><img src="/plm/portal/icon/btn_preview.gif" border="0"></A>
            <br><br>
            <a href="javascript:rightMove();"><img src="/plm/portal/icon/btn_next.gif" border="0"></A>
        </td>
        <td width="45%" id=textblue rowspan=2>
            <%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%> : <input type=text name="userName" value="<%=userName%>" style="width:50%;">&nbsp;<input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>' onclick="javascript:SearchObj();">
            <select name="noreglist" size="20" id=i style="width:100%;" multiple>
<%
    for ( int i = 0 ; i < allUser.size() ; i++ ) {
        People people = (People)allUser.get(i);
        String peopleOID = CommonUtil.getOIDString(people);
        //if ( !dutyUser.contains(peopleOID) ) {
            //if(people.getDuty()== null){
%>
                <option value="<%=peopleOID%>"><%=people.getName()%>:<%=people.getId()%> ( <%=(people.getDuty()==null)?messageService.getString("e3ps.message.ket_message", "03357")/*지정안됨*/:people.getDuty()%> )</option>
<%      //}
        //}
    }
%>
            </select>
        </td>
    </tr>
    <tr height=23 bgcolor="#FFFFFF" align="center">
        <td width="45%" id=textblue>
            <select name="reglist" size="18" id=i style="width:100%;" multiple>
<%
    if ( !duty.equals("") ) {
        while ( result.hasMoreElements() ) {
            Object[] obj = (Object[])result.nextElement();
            People people = (People)obj[0];
            String peopleOID = CommonUtil.getOIDString(people);

%>
                <option value="<%=peopleOID%>"><%=people.getName()%>:<%=people.getId()%> ( <%=(people.getDuty()==null)?messageService.getString("e3ps.message.ket_message", "03357")/*지정안됨*/:people.getDuty()%> )</option>
<%
        }
    }
%>
            </select>
        </td>
    </tr>
</table>
</form>
</body>
</html>
<script language="javascript">
<!--
function closeWindow() {
    self.close();
}

function leftMove() {
<%if ( !duty.equals("") ) {%>
    var idx = document.forms[0].reglist.length;

    for(var i = 0; i < document.forms[0].noreglist.length; i++){
        if(document.forms[0].noreglist.options[i].selected == true){
            document.forms[0].reglist.length += 1;
            document.forms[0].reglist.options[idx].text = document.forms[0].noreglist.options[i].text;
            document.forms[0].reglist.options[idx].value = document.forms[0].noreglist.options[i].value;
            document.forms[0].noreglist.options[i] = null;
            i -= 1;
            idx += 1;
        }
    }
<%}%>
}

function rightMove() {
<%if ( !duty.equals("") ) {%>
    var idx = document.forms[0].noreglist.length;

    for(var i = 0; i < document.forms[0].reglist.length; i++){
        if(document.forms[0].reglist.options[i].selected == true){
            document.forms[0].noreglist.length += 1;
            document.forms[0].noreglist.options[idx].text = document.forms[0].reglist.options[i].text;
            document.forms[0].noreglist.options[idx].value = document.forms[0].reglist.options[i].value;
            document.forms[0].reglist.options[i] = null;
            i -= 1;
            idx += 1;
        }
    }
<%}%>
}

function changeDuty() {
    showProcessing();
    document.location.href = "setDutyForm.jsp?duty="+document.forms[0].duty.value;
}

var processState = true;
function setDept() {
    for(var i = 0; i < document.forms[0].reglist.length; i++){
        document.forms[0].reglist.options[i].selected = true;
    }

    if ( processState ) {
        var url = "/plm/jsp/common/processing.html";
        openOtherName(url,"window","310","180","status=no, scrollbars=no,resizable=no");
        document.forms[0].target = "window";
        document.forms[0].action = "setDuty.jsp";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}
//-->
</script>
