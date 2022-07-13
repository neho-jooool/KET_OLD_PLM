<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page
    import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 ext.ket.shared.query.*,
                 ext.ket.part.entity.*,
                 e3ps.project.ProjectMaster,
                 e3ps.project.E3PSProject,
                 e3ps.project.beans.ProjectHelper,
                 ext.ket.shared.code.service.*,
                 ext.ket.part.classify.service.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil"%>
<%@ page import="ext.ket.part.util.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("oid");      
    String ViewOid = "";
    WTPart wt = null;
    if (poid != null && !poid.equals("")) {
        wt = (WTPart) KETObjectUtil.getObject(poid);
        ViewOid = CommonUtil.getOIDLongValue2Str(poid);
    }

    // DIE와 연결된 반제품 리스트 가져오기 - KETHalbPartDieSetPartLink
    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    List<KETHalbPartDieSetPartLink> halbList = query.queryForListLinkByRoleB(KETHalbPartDieSetPartLink.class, WTPartMaster.class, (WTPartMaster) wt.getMaster());
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
<!--
var ViewOid = <%=ViewOid%>;
//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
  var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
  openWindow(url, '',1050,800);
}

function ViewPartForm(oid){
	var url = "/plm/jsp/bom/ViewPart.jsp?oid="+oid;
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "halb:" + oid;
    //var name = "registHalbPartFormPopup";
    var name = ViewOid;
    var defaultWidth = 1024;
    var defaultHeight = 768;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}
-->
</script>
</head>
    <body>
    
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td width="30" class="tdgrayM">No</td>
                <td width="80" class="tdgrayM">부품유형</td>
                <td width="120" class="tdgrayM">부품분류</td>
                <td width="90" class="tdgrayM">부품번호</td>
                <td width="*" class="tdgrayM">부품명</td>
                <td width="80" class="tdgrayM">Rev</td>
                <td width="100" class="tdgrayM">프로젝트번호</td>
                <td width="100" class="tdgrayM">ECO NO</td>
                <td width="110" class="tdgrayM">주도면 NO</td>
            </tr>
            <%
            if ((halbList == null) || (halbList.size() == 0)) {
	        %>
	        <tr>
	            <td class="tdwhiteM" colspan="09">관련 부품이 없습니다.</td>
	        </tr>
	        <%
	            } else {
	        %>
	        <%
	            int halbCount = 1;
	            for (KETHalbPartDieSetPartLink link : halbList) {
	                
	                WTPartMaster wtPartMast = link.getHalb();
	                WTPart wtPart = (WTPart)e3ps.common.obj.ObjectUtil.getLatestObject(wtPartMast);
	                String partOid = KETObjectUtil.getOidString(wtPart);
	                // 부품유형 
	                String spPartType = PartUtil.getPartType(wtPart);
	                // 부품분류 
	                String clazKrName = PartUtil.getPartClassificationKrName(wtPart);
	                // Rev 
	                String spPartRevision = PartUtil.getKetPartRevision(wtPart);
	                // 프로젝트번호
	                E3PSProject  project = PartUtil.getE3PSProject(wtPart);
	                String projectNo = project == null ? null : project.getPjtNo();

	                // ECO NO 
	                String ecoNo = PartUtil.getEcoNo(wtPart);
	                // 주도면 NO 
	                List<EPMDocument> primaryList = PartUtil.getReleateEpmDocumnetList(wtPart);
	               
	        %>
	        <tr>
	            <td class="tdwhiteM"><%=halbCount++%></td>
	            <td class="tdwhiteM"><%=spPartType%></td>
	            <td class="tdwhiteM"><%=clazKrName%></td>
	            <td class="tdwhiteM"><a href="javascript:ViewPartForm('<%=partOid %>');"><%=wtPartMast.getNumber()%></a></td>
	            <td class="tdwhiteM"><%=wtPartMast.getName()%></td>
	            <td class="tdwhiteM"><%=spPartRevision%></td>
	            <% if(projectNo == null){ %>
	            <td class="tdwhiteM">&nbsp;</td>
	            <% }else{%>
	            <td class="tdwhiteM"><a href="javascript:;" onclick="javascript:viewProjectPopup('<%=PersistenceHelper.getObjectIdentifier(project).getStringValue()%>');"><%=projectNo%></a></td>
	            <% }%>
	            <td class="tdwhiteM"><a href="javascript:;" onclick="javascript:openViewEco('<%=ecoNo%>');"><%=ecoNo%></a></td>
	            <% if(primaryList.size() == 0 ){ %>
	            <td class="tdwhiteM">&nbsp;</td>
	            <% }else{ %>
	            <td class="tdwhiteM">
	            <%
	                for(int j=0; j < primaryList.size(); j++){
	                    EPMDocument epmDoc = primaryList.get(j);
	            %>
	               <%=((j==0)?"":",<br>") %><a href="javascript:;" onclick="javascript:openView('<%=PersistenceHelper.getObjectIdentifier(epmDoc).getStringValue()%>');"><%=epmDoc.getNumber()%></a>
	            <%
	               }
	            %>
	            </td>
	            <% } %>
	        </tr>
	
	        <%
	            }
	        %>
	        <%
	            }
	        %>
        </table>
    
    </body>
</html>