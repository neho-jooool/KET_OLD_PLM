<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.*,e3ps.common.web.ParamUtil" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    String mode = "s";
    if(request.getParameter("mode")!=null && request.getParameter("mode").length()!=0)
        mode = request.getParameter("mode");
    String function = request.getParameter("function");
    String other = request.getParameter("other");

    String treeURL = "/plm/jsp/groupware/company/selectDeptTree.jsp?mode="+mode;
    String mainURL = "/plm/jsp/groupware/company/selectPeopleList.jsp?mode="+mode;
    if ( !function.trim().equals("") ) {
        treeURL = treeURL + "&function="+function;
        mainURL = mainURL + "&function="+function;
    }
    if(other!=null&&other.trim().length()!=0)
    {
        treeURL = treeURL + "&other="+other;
        mainURL = mainURL + "&other="+other;
    }

    // Modify skyprda 2005/02/03
    String target = request.getParameter("target");
    if ( target != null && target.length() > 0) {
        treeURL = treeURL + "&target="+target;
        mainURL = mainURL + "&target="+target;
    }
    // end - skyprda
%>
<head>
<title>PLM Portal</title>
</head>
<frameset rows="1*" cols="180,*" border="0" frameborder="1" framespacing="0">
<frame name="tree" scrolling="auto" marginwidth="0" marginheight="0" src="<%=treeURL%>">
<frame name="main" scrolling="auto" marginwidth="0" marginheight="0" src="<%=mainURL%>">
</frameset>
</html>
