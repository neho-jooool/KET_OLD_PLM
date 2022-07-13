<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="
                java.util.*,e3ps.common.query.SearchUtil,
                wt.fc.*,wt.org.*,wt.query.*,
                e3ps.common.util.*,e3ps.groupware.company.*"
%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>"/>
<%
    String oid = request.getParameter("oid");
    Department dept = (Department)CommonUtil.getObject(oid);

    QuerySpec spec = new QuerySpec();
    Class peopleClass = People.class;
    int peopleClassPos = spec.addClassList(peopleClass,true);
    SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, People.NAME, "sort", false);

    Vector allUser = new Vector();
    QueryResult result = PersistenceHelper.manager.find(spec);
    while ( result.hasMoreElements() ) {
        Object[] obj = (Object[])result.nextElement();
        allUser.add(obj[0]);
    }

    spec = new QuerySpec();
    peopleClass = People.class;
    peopleClassPos = spec.addClassList(peopleClass,true);
    spec.appendWhere(new SearchCondition(peopleClass,"departmentReference.key.id",SearchCondition.EQUAL,CommonUtil.getOIDLongValue(oid)),peopleClassPos);
    SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, People.DUTY_CODE, "sort", false);
    result = PersistenceHelper.manager.find(spec);
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01545") %><%--부서 설정--%></title>
<%@include file="/jsp/common/top_include.jsp" %>
</head>
<body leftmargin="0" topmargin="0">
<form method="post">
<input type="hidden" name="oid" value="<%=oid%>">
<table width=95% height=40 align=center border=0>
    <tr>
        <td>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=<%=imageUrl%>/title2_left.gif></td>
                    <td background=<%=imageUrl%>/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01539") %><%--부서 등록 인원--%> <B>[ <%=dept.getName()%> ]</B></td>
                    <td><img src=<%=imageUrl%>/title2_right.gif></td>
                </tr>
            </table>
        </td>
        <td align="right">
            <input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%>' onclick="javascript:setDept()">&nbsp;<input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%>' onclick="javascript:closeWindow()">
        </td>
    </tr>
</table>
<!---리스트------->
<table width="95%" border="0" cellpadding="2" cellspacing="1" bgcolor=AABCC7 align=center>
    <tr height=23 bgcolor="#D9E2E7" align=center>
        <td width="45%" id=textblue><%=messageService.getString("e3ps.message.ket_message", "01539") %><%--부서 등록 인원--%></td>
        <td width="10%" id=textblue></td>
        <td width="45%" id=textblue><%=messageService.getString("e3ps.message.ket_message", "01540") %><%--부서 미등록 인원--%></td>
    </tr>
    <tr height=23 bgcolor="#FFFFFF" align="center">
        <td width="45%" id=textblue>
            <select name="reglist" size="20" id=i style="width:100%;" multiple>
<%
    Vector deptUser = new Vector();
    while ( result.hasMoreElements() ) {
        Object[] obj = (Object[])result.nextElement();
        People people = (People)obj[0];
        String peopleOID = CommonUtil.getOIDString(people);
        deptUser.add(peopleOID);
%>
                <option value="<%=peopleOID%>"><%=people.getName()%>:<%=people.getId()%> ( <%=(people.getDuty()==null)? messageService.getString("e3ps.message.ket_message", "03357")/*지정안됨*/ :people.getDuty()%> )</option>
<%  }  %>
            </select>
        </td>
        <td width="10%" id=textblue valign="middle">
            <a href="javascript:leftMove();"><img src="<%=iconUrl%>/btn_preview.gif" border="0"></A>
            <br><br>
            <a href="javascript:rightMove();"><img src="<%=iconUrl%>/btn_next.gif" border="0"></A>
        </td>
        <td width="45%" id=textblue>
            <select name="noreglist" size="20" id=i style="width:100%;" multiple>
<%
    for ( int i = 0 ; i < allUser.size() ; i++ ) {
        People people = (People)allUser.get(i);
        String peopleOID = CommonUtil.getOIDString(people);
        if ( !deptUser.contains(peopleOID) ) {
%>
                <option value="<%=peopleOID%>"><%=people.getName()%>:<%=people.getId()%>  ( <%=(people.getDuty()==null)?messageService.getString("e3ps.message.ket_message", "03357")/*지정안됨*/:people.getDuty()%> )</option>
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
}

function rightMove() {
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
}

var processState = true;
function setDept() {
    for(var i = 0; i < document.forms[0].reglist.length; i++){
        document.forms[0].reglist.options[i].selected = true;
    }

    if ( processState ) {
        var url = '/plm/jsp/common/processing.html?img=modify_process.gif';
        openOtherName(url,"window","310","180","status=no,scrollbars=no,resizable=no");
        document.forms[0].target = "window";;
        document.forms[0].action = "setDepartment.jsp";
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}
//-->
</script>
