<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETObjectUtil" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoDetailVO" %>
<%@page import="wt.fc.Persistable" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />

<script language="javascript">
parent.enabledAllBtn();
parent.hideProcessing();
parent.deleteAllList();
<%
if(data.getResultRows() > 0) {
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
    rArr[1] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoId())%>";<%//EcrId%>
    rArr[2] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getEcoName())%>";<%//EcrName%>
    rArr[3] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreatorName())%>";<%//creatorName%>
    rArr[4] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDate())%>";<%//createDate%>
    rArr[5] = "<%=EcmUtil.getLastApproveDate( (Persistable)KETObjectUtil.getObject(ketSearchEcoDetailVO.getOid()) )%>";<%//approveDate%>
    rArr[6] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getCreateDeptName())%>";<%//CreateDeptName%>
    rArr[7] = "<%=StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag())%>";<%//Status%>
    arr[idx++] = rArr;
<%
}
out.println("parent.addAllList(arr);");
} else {
out.println("parent.addNotFound();");
}
%>
</script>
