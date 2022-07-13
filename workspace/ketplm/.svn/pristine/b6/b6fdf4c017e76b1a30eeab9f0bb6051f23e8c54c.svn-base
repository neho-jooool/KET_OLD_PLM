<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoDetailVO" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />

<script language="javascript">
parent.enabledAllBtn();
parent.hideProcessing();
parent.deleteAllList();
<%
if(data.getTotalCount() > 0) {
%>
    var arr = new Array();
    var idx = 0;
<%
int size = data.getResultRows();
KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
int rowCount = 0;
for(int i=0; i<size; i++) {
    ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(i);
    rowCount++;
%>
    rArr = new Array();
    rArr[0] = "<%=ketSearchEcoDetailVO.getOid()%>";<%//oid%>
    rArr[1] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId())%>";<%//scheduleId%>
    rArr[2] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getDieNo())%>";<%//dieNo%>
    rArr[3] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPartNumber())%>";<%//partNo%>
    rArr[4] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPartName())%>";<%//partName%>
    rArr[5] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDate())%>";<%//createDate%>
    rArr[6] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName())%>";<%//creatorName%>
    rArr[7] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPlanDate())%>";<%//activityDate%>
    rArr[8] = "<%=rowCount%>";
    arr[idx++] = rArr;
<%
}
out.println("parent.addAllList(arr);");
} else {
out.println("parent.addNotFound();");
}
%>
</script>
