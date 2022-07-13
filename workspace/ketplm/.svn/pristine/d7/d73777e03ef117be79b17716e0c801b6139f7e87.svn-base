<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="
                java.util.*,
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
    spec.appendWhere(new SearchCondition(peopleClass,"departmentReference.key.id",SearchCondition.EQUAL,CommonUtil.getOIDLongValue(oid)),peopleClassPos);
    e3ps.common.query.SearchUtil.setOrderBy(spec, peopleClass, peopleClassPos, People.DUTY_CODE, "sort", false);
    QueryResult result = PersistenceHelper.manager.find(spec);

    Vector deptUser = new Vector();
    while ( result.hasMoreElements() ) {
        Object[] obj = (Object[])result.nextElement();
        People people = (People)obj[0];
        String peopleOID = CommonUtil.getOIDString(people);
        deptUser.add(peopleOID);
    }

    Vector newDeptUser = new Vector();
    String[] reglist = request.getParameterValues("reglist");
    if ( reglist != null && reglist.length > 0 ) {
        for ( int i = 0 ; i < reglist.length ; i++ ) {
            String peopleOID = reglist[i];
            newDeptUser.add(peopleOID);
            People people = (People)CommonUtil.getObject(peopleOID);
            people.setDepartment(dept);
            PersistenceHelper.manager.modify(people);
        }
    }

    for ( int j = 0 ; j < deptUser.size() ; j++ ) {
        String peopleOID = (String)deptUser.get(j);
        if ( !newDeptUser.contains(peopleOID) ) {
            People people = (People)CommonUtil.getObject(peopleOID);
            people.setDepartment(null);
            PersistenceHelper.manager.modify(people);
        }
    }
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01546") %><%--부서 설정중--%></title>
</head>
<body onLoad="javascript:goPage()">
<form method="post">
</form>
</body>
</html>
<script language="JavaScript">
<!--
function goPage() {
    opener.opener.location.reload();
    opener.close();
    self.close();
}
//-->
</script>
