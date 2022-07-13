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
    String duty = request.getParameter("duty");

    QuerySpec spec = new QuerySpec();
    Class peopleClass = People.class;
    int peopleClassPos = spec.addClassList(peopleClass,true);
    spec.appendWhere(new SearchCondition(peopleClass,People.DUTY_CODE,SearchCondition.EQUAL,duty), new int[]{peopleClassPos});
    QueryResult result = PersistenceHelper.manager.find(spec);

    Vector dutyUser = new Vector();
    while ( result.hasMoreElements() ) {
        Object[] obj = (Object[])result.nextElement();
        People people = (People)obj[0];
        String peopleOID = CommonUtil.getOIDString(people);
        dutyUser.add(peopleOID);
    }

    Vector newdutyUser = new Vector();
    String[] reglist = request.getParameterValues("reglist");
    if ( reglist != null && reglist.length > 0 ) {
        for ( int i = 0 ; i < reglist.length ; i++ ) {
            String peopleOID = reglist[i];
            newdutyUser.add(peopleOID);
            People people = (People)CommonUtil.getObject(peopleOID);
            people.setDuty((String)CompanyState.dutyTable.get(duty));
            people.setDutyCode(duty);
            PersistenceHelper.manager.modify(people);
        }
    }

    for ( int j = 0 ; j < dutyUser.size() ; j++ ) {
        String peopleOID = (String)dutyUser.get(j);
        if ( !newdutyUser.contains(peopleOID) ) {
            People people = (People)CommonUtil.getObject(peopleOID);
            people.setDuty(null);
            people.setDutyCode(null);
            PersistenceHelper.manager.modify(people);
        }
    }
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02716") %><%--직위 설정중--%></title>
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
    opener.location.reload();
    self.close();
}
//-->
</script>
