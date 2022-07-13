<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.StringUtil" %>
<%
    String url = StringUtil.checkNull(request.getParameter("url"));
    String obj[] = request.getParameterValues("obj");
    if(obj != null) {
        int size = obj.length;
        for(int i=0; i<size; i++) {
            if(i == 0) {
                url += "?" + obj[i].replace("^", "=");
            } else {
                url += "&" + obj[i].replace("^", "=");
            }
        }
    }
%>
<script language="javascript">
//alert("<%=url%>");
function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
    window.close();
}

</script>
<iframe name="popup" align="center" src="<%=url%>" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
