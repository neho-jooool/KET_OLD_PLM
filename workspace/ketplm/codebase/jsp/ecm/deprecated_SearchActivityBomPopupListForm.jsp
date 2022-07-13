<%@page import="e3ps.ecm.beans.EcmUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoVO" %>
<%@page import="e3ps.ecm.entity.KETSearchEcoDetailVO" %>
<%@page import="e3ps.common.web.PageControl" %>
<%@page import="java.util.ArrayList" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="data" class="e3ps.ecm.entity.KETSearchEcoVO" scope="request" />
<script language="javascript">
parent.deleteBomAllList();
<%
if(data.getTotalCount() > 0) {
%>
    var arr = new Array();
    var idx = 0;
<%
	int size = data.getResultRows();
	KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
	for(int i=0; i < size; i++) {
	    ketSearchEcoDetailVO =  data.getKetSearchEcoDetailVOList().get(i);
%>


	    rArr = new Array();
	    rArr[0] = '<%=ketSearchEcoDetailVO.getOid()%>';
	    rArr[1] = "<%=KETStringUtil.tagReplaceAll(StringUtil.checkNull(ketSearchEcoDetailVO.getPartNumber()))%>";
	    rArr[2] = "<%=KETStringUtil.tagReplaceAll(StringUtil.checkNull(ketSearchEcoDetailVO.getPartName()))%>";
	    rArr[3] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getSancStateFlag())%>';
	    rArr[4] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPartVersion())%>';
	    rArr[5] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getEpmDocCls())%>';
	    rArr[6] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getPartType())%>';
	
	    // Die No
	    rArr[7] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getDieNo())%>';
	    rArr[8] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getDieName())%>';
	    rArr[9] = '<%=StringUtil.checkNull(ketSearchEcoDetailVO.getDieCnt())%>';

<%
        ArrayList<wt.change2.WTChangeOrder2> relatedEcoList = EcmUtil.getECOexistEcaBomLink( ketSearchEcoDetailVO.getPartNumber(), ketSearchEcoDetailVO.getPartVersion() );
        int relatedEcoListSize = (relatedEcoList != null) ? relatedEcoList.size() : 0;
        if(relatedEcoListSize > 0) {
%>
            rArr2 = new Array();
<%          
            for(int j=0; j < relatedEcoListSize; j++) {
                String ecoId = "";
                wt.change2.WTChangeOrder2 wtChangeOrder2 = relatedEcoList.get(j);
                if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETProdChangeOrder) {
                    e3ps.ecm.entity.KETProdChangeOrder eco = (e3ps.ecm.entity.KETProdChangeOrder) wtChangeOrder2;
                    ecoId = eco.getEcoId();
                } else if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETMoldChangeOrder) {
                    e3ps.ecm.entity.KETMoldChangeOrder eco = (e3ps.ecm.entity.KETMoldChangeOrder) wtChangeOrder2;
                    ecoId = eco.getEcoId();
                }
%>
                rArr2[<%=j %>] = '<%=ecoId %>';
<%          
            }   // for(int j=0; j < relatedEcoListSize; j++) {
%>
            // 연관 ECO
            rArr[10] = rArr2;
<%
        }   // if(relatedEcoListSize > 0) {

%>
           
	    arr[idx++] = rArr;
	
<%
	}  // for(int i=0; i < size; i++) {
	out.println("parent.addBomAllList(arr);");
	
} else {
    out.println("parent.addBomNotFound();");
}
%>

try {
    parent.enabledAllBtn();
    parent.hideProcessing();
    
} catch(e) {}
</script>
