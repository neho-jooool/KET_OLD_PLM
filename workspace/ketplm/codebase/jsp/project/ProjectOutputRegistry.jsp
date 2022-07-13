<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.StringTokenizer,
                wt.fc.*,wt.folder.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>"/>
<%
    String oid = request.getParameter("oid");
    ProjectOutput output = (ProjectOutput)CommonUtil.getObject(oid);

    String temp_Location = output.getOutputLocation().trim();
    //Kogger.debug("temp_Location"+temp_Location);
    //StringTokenizer st = new StringTokenizer(temp_Location, "/");
    //int i = 0;
    //String[] temp = new String[st.countTokens()];
    //Kogger.debug("st.countTokens"+st.countTokens());
    //while(st.hasMoreTokens())
    //{
        //temp[i] = (String)st.nextToken();
        //Kogger.debug("temp["+i+"]:"+temp[i]);
        //i++;
    //}

    String projectOid = request.getParameter("projectOid");
    String redirectUrl = "/plm/jsp/project/ProjectOutputDocRegistryForm.jsp?oid="+oid;
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body onLoad="javascript:goPage('<%=redirectUrl%>')">
<form>

<!-- Hidden Value -->
<input type='hidden' name='projectOutputOid' value='<%=oid%>'>
<input type='hidden' name='preDocType' value='<%//=temp[0]%>'>
<input type='hidden' name='projectOutputName' value='<%=(String)output.getOutputName()%>'>
<input type='hidden' name='projectOutputDesc' value='<%=(String)output.getOutputDesc()%>'>
<input type='hidden' name='projectOutputLocation' value='<%=temp_Location%>'>
<input type='hidden' name='projectOid' value='<%=projectOid%>'>

</form>
</body>
</html>
<script language="JavaScript">
<!--
function goPage( url ) {
    document.forms[0].action=url;
    document.forms[0].method='post';
    document.forms[0].submit();
}
//-->
</script>
