<html>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
	
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.obj.ObjectData,
                  e3ps.common.content.*,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.obj.ObjectUtil,
                  e3ps.common.code.NumberCode,
                  e3ps.ews.entity.KETEarlyWarning,
                  e3ps.ews.entity.KETEarlyWarningPlanLink,
                  e3ps.ews.entity.KETEarlyWarningResultLink,
                  e3ps.ews.entity.KETEarlyWarningTarget,
                  e3ps.ews.entity.EarlyWarningTargetLink,
                  e3ps.ews.entity.KETEarlyWarningEndReq,
                  e3ps.ews.entity.KETEndReqLink,
                  e3ps.ews.entity.KETEarlyWarningEndReqLink,
                  e3ps.ews.entity.KETEarlyWarningEnd,
                  e3ps.ews.dao.PartnerDao,           
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  wt.vc.VersionControlHelper,
                  wt.content.*,
                  wt.org.WTUser,
                  wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
									java.util.Vector"%>

<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/common.js"></script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<SCRIPT LANGUAGE="JavaScript">
<!--

  // 해제신청 상세화면으로 이동
	function goViewEndReq(endReqOid){		  	
  	var url='/plm/jsp/ews/ViewEarlyWarningEndReqPopup.jsp?endReqOid='+endReqOid;
		openWindow(url,"","830","600","status=1,scrollbars=no,resizable=no");
	}
	
	// 해제판정 상세화면으로 이동
	function goViewEnd(endOid){		  	
  	var url='/plm/jsp/ews/ViewEarlyWarningEndPopup.jsp?endOid='+endOid;
		openWindow(url,"","830","600","status=1,scrollbars=no,resizable=no");
	}
	
//-->
</SCRIPT>
</head>
<body>
<form method="post">
<table id="partnerTable" border="0" cellspacing="0" cellpadding="0" width="460">
	<%
	  /********************************       ketEarlyWarning object       ********************************/
	
		String oid = ParamUtil.getParameter(request, "oid");	
		KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);	
		
		/********************************       ketEarlyWarning object       ********************************/
		
		/********************************     ketEarlyWarningPlan object     ********************************/
	
		QueryResult isEndReq = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningEndReqLink.ROLE_BOBJECT_ROLE, KETEarlyWarningEndReqLink.class, false);
		KETEarlyWarningEndReqLink ketEarlyWarningEndReqLink = null;
	  Object endReqMaster = null; 
	  ObjectData endReqMasterData = null;
	  KETEarlyWarningEndReq ketEarlyWarningEndReq = null;
	  String endReqOid = null;
	  
	  QueryResult isEnd = null;
    KETEndReqLink ketEndReqLink = null;
    Object endMaster = null; 
    ObjectData endMasterData = null;
    KETEarlyWarningEnd ketEarlyWarningEnd = null;
    String endOid = null;
                  
	  String step = "해제신청";
	  String state = null;
	  int listCnt = 1;
	  
	  while(isEndReq.hasMoreElements()){
	 		ketEarlyWarningEndReqLink = (KETEarlyWarningEndReqLink)isEndReq.nextElement();
			endReqMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningEndReqLink.getRoleBObject());
			endReqMasterData = new ObjectData((WTDocument)endReqMaster);
			
			endReqOid = endReqMasterData.getOid();
		  ketEarlyWarningEndReq = (KETEarlyWarningEndReq)CommonUtil.getObject(endReqOid);
		  state = ketEarlyWarningEndReq.getLifeCycleState().getDisplay();
		  
		  isEnd = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningEndReq.getMaster(), KETEndReqLink.ROLE_BOBJECT_ROLE, KETEndReqLink.class, false);
 			if (isEnd != null && isEnd.size() != 0){
 			  step = "해제판정";
   			while(isEnd.hasMoreElements()){
   				ketEndReqLink = (KETEndReqLink)isEnd.nextElement();
     			endMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEndReqLink.getRoleBObject());
     			endMasterData = new ObjectData((WTDocument)endMaster);
     			endOid = endMasterData.getOid();
     			ketEarlyWarningEnd = (KETEarlyWarningEnd)CommonUtil.getObject(endOid);
     			state = ketEarlyWarningEnd.getLifeCycleState().getDisplay();
   			}
   		}
		  
		/********************************     ketEarlyWarningPlan object     ********************************/

	%>
	<tr>
    <td width="40" class="tdwhiteM">
    	<% if (step.equals("해제신청")) { %>
    		<a href="javascript:goViewEndReq('<%=endReqOid%>');"><%=listCnt++%></a>
    	<% }else {%>
    	  <a href="javascript:goViewEnd('<%=endOid%>');"><%=listCnt++%></a>
    	<% } %>
    </td>
    <td width="80" class="tdwhiteM"><%=ketEarlyWarningEndReq.getCreatorFullName()%></td>
    <td width="90" class="tdwhiteM"><%=DateUtil.getTimeFormat(ketEarlyWarningEndReq.getCreateTimestamp(),"yyyy-MM-dd")%></td>
    <td width="90" class="tdwhiteM"><%=DateUtil.getTimeFormat(ketEarlyWarningEndReq.getModifyTimestamp(),"yyyy-MM-dd")%></td>
    <td width="80" class="tdwhiteM"><%=step%></td>
    <td width="80" class="tdwhiteM0"><%=state%></td>
	</tr>
	<%
		}
	%>
</table>
</form>
</body>
</html>
