<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="wt.fc.PersistenceHelper,
								wt.fc.QueryResult,
								wt.doc.WTDocument,
                wt.doc.WTDocumentMaster,
                e3ps.common.web.ParamUtil,
								e3ps.common.util.CommonUtil,
								e3ps.common.obj.ObjectUtil,
								e3ps.ews.dao.EarlyWarningEndDao,  
								e3ps.ews.entity.KETEarlyWarning,
								e3ps.ews.entity.KETEarlyWarningEndReq" %>
<%
  String oid = ParamUtil.getParameter(request, "oid");	
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);	
	
	EarlyWarningEndDao endDao = new EarlyWarningEndDao();
	String endReqOid = endDao.ViewEndReqId (CommonUtil.getOIDString((WTDocumentMaster)ketEarlyWarning.getMaster()));
	KETEarlyWarningEndReq ketEarlyWarningEndReq = null;
  String endReqState = null;

  String url = null;  
	
	if (endReqOid != null && !endReqOid.equals("")){
	  ketEarlyWarningEndReq = (KETEarlyWarningEndReq)CommonUtil.getObject(endReqOid);
		endReqState = ketEarlyWarningEndReq.getLifeCycleState().toString();
		
		if (endReqState.equals("INWORK")){
			url = "/plm/jsp/ews/ViewEarlyWarningEnd.jsp?todo=Y&endReqOid="+endReqOid;
		}else{
			url = "/plm/jsp/ews/CreateEarlyWarningEndReq.jsp?todo=Y&oid="+oid;
		}	  
	}else{
		url = "/plm/jsp/ews/CreateEarlyWarningEndReq.jsp?todo=Y&oid="+oid;
	}	
%>
<script type="text/javascript">
<!--

	// 화면이동
	function goEndReq(){
		window.location = '<%=url%>';
	}

//-->
</script>
<html>
	<body onload='javascript:goEndReq();'>
  </body>	
</html>
