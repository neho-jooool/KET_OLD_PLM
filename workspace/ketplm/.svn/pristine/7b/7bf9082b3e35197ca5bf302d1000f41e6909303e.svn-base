<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="wt.fc.PersistenceHelper,
								wt.fc.QueryResult,
								wt.doc.WTDocument,
                wt.doc.WTDocumentMaster,
                e3ps.common.web.ParamUtil,
								e3ps.common.util.CommonUtil,
								e3ps.common.obj.ObjectData,
								e3ps.common.obj.ObjectUtil,
								e3ps.ews.entity.KETEarlyWarning,
								e3ps.ews.entity.KETEarlyWarningPlanLink,
								e3ps.ews.entity.KETEarlyWarningPlan" %>
<%
  String oid = ParamUtil.getParameter(request, "oid");	
	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);	
	
	QueryResult isPlan = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningPlanLink.ROLE_BOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
	KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
  Object planMaster = null; 
  ObjectData planMasterData = null;
  String planOid = null;
  KETEarlyWarningPlan ketEarlyWarningPlan = null;
  String url = null;
  
  if (isPlan != null && isPlan.size() != 0){	          
	  while(isPlan.hasMoreElements()){
	 		ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink)isPlan.nextElement();
			planMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningPlanLink.getRoleBObject());
			planMasterData = new ObjectData((WTDocument)planMaster);
		}
	}
	
	if(isPlan.size()>0){
		planOid = planMasterData.getOid();
	  url = "/plm/jsp/ews/ViewEarlyWarningPlan.jsp?todo=Y&planOid="+planOid;
	}else{
		url = "/plm/jsp/ews/CreateEarlyWarningPlan.jsp?todo=Y&oid="+oid;
	}	
%>
<script type="text/javascript">
<!--

	// 화면이동
	function goPlan(){
		window.location = '<%=url%>';
	}

//-->
</script>
<html>
	<body onload='javascript:goPlan();'>
  </body>	
</html>
