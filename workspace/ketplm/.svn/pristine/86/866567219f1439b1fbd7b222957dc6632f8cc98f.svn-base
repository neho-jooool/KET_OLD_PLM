<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoDetailVO" %>
<%@page import="e3ps.common.web.PageControl" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />
<%
PageControl control = data.getPageControl();
%>
<script language="javascript">
parent.deleteAllList();
<%
if(control.getTotalCount() > 0) {
%>
    var arr = new Array();
    var idx = 0;
<%
int size = data.getResultRows();
KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
int rowCount = (control.getCurrentPage() - 1) * control.getInitPerPage();
for(int i=0; i<size; i++) {
    ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(i);
    rowCount++;
%>
    rArr = new Array();
    rArr[0] = '<%=ketSearchEcoDetailVO.getOid()%>';
    rArr[1] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPartNumber())%>';
    rArr[2] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPartName())%>';
    rArr[3] = '<%=rowCount%>';
    rArr[4] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPartVersion())%>';
    arr[idx++] = rArr;
<%
}
out.println("parent.addAllList(arr);");
} else {
out.println("parent.addNotFound();");
}
%>
</script>
<%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
