<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.project.*,
                  e3ps.common.util.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    String projectOid = oid;
    Object obj= CommonUtil.getObject(oid);
    String target = "Project";
    if ( obj instanceof E3PSTask ) {
        projectOid = CommonUtil.getOIDString(((E3PSTask)obj).getProject());
        target = "Task";
    }
%>
<HTML>
<HEAD>
<style>
<!--
body {
    margin:0;
    padding:0;
    border:0;

    overflow-x:auto;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}



-->
</style>
<script>
function pageReSize() {
    var treeDiv = document.getElementById("treeDiv");
    var imgDiv = document.getElementById("topImg");
    //alert(treeDiv.offsetHeight +'\t' + imgDiv.offsetHeight + '\t' + document.body.clientHeight);
    h1 = document.body.clientHeight-imgDiv.offsetHeight;
    //alert(h1);
    //treeDiv.style.height = h1;

    var treeTable_obj = document.getElementById("treeTable");
    treeTable_obj.height = h1;
    //alert(treeTable_obj.height);
}
</script>
</HEAD>
<!-- <BODY onload="javascript:pageReSize();"> -->
<body>
    <!-- button -->
    <div id='topImg' style="height=95px;">
        <table border="0" cellpadding="0" cellspacing="0" style="border:0;width:100%;height:100%;">
            <tr>
                <td style='border:0;margin:0;padding:0;'>
                    <iframe src="/plm/jsp/project/ProjectLinkFrm.jsp?oid=<%=projectOid%>" id="link" name="link" frameborder="0" framespacing="0" width="200" height="100%" leftmargin="0" topmargin="0" scrolling="no" noresize></iframe>
                </td>
            </tr>
        </table>
    </div>
    <!-- tree -->
    <div id='treeDiv'>
        <table border="0" cellpadding="0" cellspacing="0" style="border:0;width:100%;height:100%;">
            <tr>
                <td>
                    <iframe src="/plm/jsp/project/ProjectTree.jsp?oid=<%=projectOid%>&taskOid=<%=oid %>" id="tree" name="tree" frameborder="0" framespacing="0" width="200" height="100%" leftmargin="0" topmargin="0" scrolling="no" noresize></iframe>
                </td>
            </tr>
        </table>
    </div>
</BODY>
</HTML>

