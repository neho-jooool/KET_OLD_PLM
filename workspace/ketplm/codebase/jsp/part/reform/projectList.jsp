<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.ProjectMaster"%>
<%@page import="e3ps.project.ProductInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.*,wt.util.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil,
                 e3ps.common.dao.*"%>
<%@ page import="ext.ket.project.program.entity.*" %>                  
<%@ page import="e3ps.bom.dao.pool.*" %>                   
<%@ page import="e3ps.bom.framework.util.*" %>   
<%@ page import="e3ps.project.beans.*" %>
<%@ page import="ext.ket.part.util.*" %>
<%@ page import="ext.ket.part.entity.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("oid");      
    WTPart wtPart = (WTPart) CommonUtil.getObject(poid);
    /*
    List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
    
    QuerySpec qs = new QuerySpec();
    int idxpi = qs.appendClassList(ProductInfo.class, true);
    SearchCondition cs = new SearchCondition(ProductInfo.class, ProductInfo.P_NUM, "=", wtPart.getNumber());
    qs.appendWhere(cs, new int[] { idxpi });
    QueryResult qrpi = PersistenceHelper.manager.find(qs);
    while (qrpi.hasMoreElements()) {
        Object o[] = (Object[]) qrpi.nextElement();
        ProductInfo pi = (ProductInfo) o[0];
        
        projectDataList.add(new E3PSProjectData(pi.getProject()));
        
    }
    */
    /*
    Registry registry = null;
    DBConnectionManager res = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n"); 
    sql.append("FROM PRODUCTPROJECT      PJT                                             \n"); 
    sql.append("WHERE 1=1                                                                \n"); 
    sql.append("AND PJT.LASTEST       = 1                                                \n"); 
    sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n"); 
    sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n"); 
    sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = ? ) )   \n"); 

    
    try {
        registry = Registry.getRegistry("e3ps.bom.bom");
        res = DBConnectionManager.getInstance();
        conn = res.getConnection(registry.getString("plm"));
        pstmt = conn.prepareStatement(sql.toString());
        
        
        pstmt.setString(1, wtPart.getNumber());

        Kogger.debug(">>>>> sql : " + sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
	        E3PSProject project = (E3PSProject) CommonUtil.getObject(StringUtil.checkNull(rs.getString("oid")));
	        projectDataList.add(new E3PSProjectData(project));
        }
    } catch (Exception e) {
        throw e;
    } finally {
        sql.delete(0, sql.length());
        if (rs != null)
        rs.close();
        if (pstmt != null)
        pstmt.close();
        if (conn != null)
        conn.close();
    }
    */
    
    List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
    // 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
    // TKLEE 추가함.
    QueryResult partQr = PartUtil.getPartProjectLink(wtPart, null);
    KETPartProjectLink bProjectLink = null;
    ProjectMaster partPjtMast = null;
    while (partQr.hasMoreElements()) {
        Object[] objs = (Object[]) partQr.nextElement();
        bProjectLink = (KETPartProjectLink) objs[0];
        partPjtMast = bProjectLink.getProject();
    }
    if(partPjtMast != null){
	   E3PSProject partE3PSProject = ProjectHelper.getLastProject(partPjtMast);
	   projectDataList.add(new E3PSProjectData(partE3PSProject));
    }
    
    // 프로젝트에서 등록한 부품을 통해서 프로젝트 정보를 가져온다.
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n"); 
    sql.append("FROM PRODUCTPROJECT      PJT                                             \n"); 
    sql.append("WHERE 1=1                                                                \n"); 
    sql.append("AND PJT.LASTEST       = 1                                                \n"); 
    sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n"); 
    sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n"); 
    sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '"+wtPart.getNumber()+"' ) )   \n"); 
    
    List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
    DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
    returnObjList = oDaoManager.queryForList(sql.toString(), new RowSetStrategy<Map<String, Object>>() {
        @Override
        public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
            Map<String, Object> returnObj = new HashMap<String, Object>();
            returnObj.put("oid", rs.getString("oid"));
            return returnObj;
        }
    });
    
    for(int i = 0; i < returnObjList.size(); i++){
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(StringUtil.checkNull((String) returnObjList.get(i).get("oid")));
	    if(partPjtMast != null){
		  if(partPjtMast.getPjtNo().equals(project.getPjtNo())){
		      continue;
		  }
	    }
	    projectDataList.add(new E3PSProjectData(project));
    }
    
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

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
  var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
  openWindow(url, '',1050,800);
}

-->
</script>
</head>
    <body>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td width="60" class="tdgrayM">No</td>
                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                <td width="250" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                <td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%></td>
                <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03034") %><%--프로그램--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00415")%><%--프로젝트카드--%></td>
                <td width="89" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            </tr>
            <%      if( (projectDataList == null) || (projectDataList.size() == 0)) { %>
            <tr>
                <td class="tdwhiteM" colspan="12"><%=messageService.getString("e3ps.message.ket_message", "00921") %><%--관련 프로젝트가 없습니다.--%></td>
            </tr>
            <%      } else { %>
	        <%
	                    int listCount = projectDataList.size();
	                    for(int inx = 0; inx < projectDataList.size(); inx++) {
	                	  E3PSProjectData projectData = projectDataList.get(inx);
	        %>
                <tr>
                    <td class="tdwhiteM"><%=inx+1%></td>
                    <% if(projectData.ViewpjtNo == null){ %>
	                <td class="tdwhiteM">&nbsp;</td>
	                <% }else{%>
	                <td class="tdwhiteM"><a href="javascript:;" onclick="javascript:viewProjectPopup('<%=projectData.e3psPjtOID%>');"><%=projectData.ViewpjtNo%></a></td>
	                <% }%>
                    <td class="tdwhiteM"><%=projectData.pjtName%></td>
                    <td class="tdwhiteM"><%=projectData.pjtPmName%></td>
                    <td class="tdwhiteM">
	                    <%
	                         if (projectData.customereventVec != null && projectData.customereventVec.size() > 0) {
	                             
	                             for (int i = 0; i < projectData.customereventVec.size(); i++) {
	                                 NumberCode nc = (NumberCode) projectData.customereventVec.get(i);
	                                 String masterName = NumberCodeUtil.getNumberCodeValue("CUSTOMEREVENT", nc.getCode(), messageService.getLocale().toString());
	                                 if(i!=0){
	                                    out.print(",");   
	                                 }
	                                 out.print(masterName);
	                             }
	                         }
	                     %>
                    </td>
                    <td class="tdwhiteM">
                         <%
                              if (projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
                                  for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
                                      NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
                                      String masterName = NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode(), messageService.getLocale().toString());
                                      if(i!=0){
                                          out.print(",");   
                                      }
                                      out.print(masterName);
                                  }
                              }
                          %>
                    </td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(projectData.representModel)%></td>
                    <td class="tdwhiteM">
                          <%
                          QuerySpec sqs = new QuerySpec();
                          int idxSub = sqs.addClassList(KETProgramSchedule.class, false);
                          ClassAttribute masterOidAttr = new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id");
                          sqs.appendSelect(masterOidAttr, false);
                          ClassAttribute versionAttr = new ClassAttribute(KETProgramSchedule.class, "versionInfo.identifier.versionId");
                          SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);
                          sqs.appendSelect(max, false);
                          sqs.appendGroupBy(new ClassAttribute(KETProgramSchedule.class, "masterReference.key.id"), new int[] { idxSub }, false);

                          QuerySpec qs = new QuerySpec();
                          qs.setAdvancedQueryEnabled(true);
                          int idx0 = qs.addClassList(KETProgramSchedule.class, true);
                          int idx1 = qs.addClassList(KETProgramProjectLink.class, false);
                          qs.appendSelect(new ClassAttribute(KETProgramProjectLink.class, KETProgramProjectLink.IS_BASE), false);
                          qs.appendSelect(new ClassAttribute(KETProgramProjectLink.class, KETProgramProjectLink.IS_CHECKED_EVENT), false);

                          ColumnListExpression listExpression = new ColumnListExpression();
                          TableColumn masterOidAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx0), "IDA3MASTERREFERENCE");
                          TableColumn versionAttr1 = new TableColumn(qs.getFromClause().getAliasAt(idx0), "VERSIONIDA2VERSIONINFO");
                          listExpression.addColumn(masterOidAttr1);
                          listExpression.addColumn(versionAttr1);
                          SubSelectExpression selectExpression = new SubSelectExpression(sqs);

                          selectExpression.setAccessControlRequired(false);
                          SearchCondition sc = new SearchCondition(new ClassAttribute(KETProgramSchedule.class, WTAttributeNameIfc.VERSION_ID_NAME), SearchCondition.EQUAL, new ClassAttribute(
                                  KETProgramProjectLink.class, WTAttributeNameIfc.ROLEB_VERSION_ID));
                          qs.appendWhere(sc, new int[] { idx0, idx1 });
                          qs.appendAnd();
                          qs.appendWhere(new SearchCondition(KETProgramSchedule.class, KETProgramSchedule.LATEST_ITERATION, SearchCondition.IS_TRUE), new int[] { idx0 });
                          qs.appendAnd();
                          qs.appendWhere(new SearchCondition(listExpression, SearchCondition.IN, selectExpression), new int[] { idx0 });
                          qs.appendAnd();
                          SearchCondition sc2 = new SearchCondition(KETProgramProjectLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(projectData.e3psProject));
                          qs.appendWhere(sc2, new int[] { idx1 });
                          qs.appendOrderBy(new OrderBy(new ClassAttribute(KETProgramSchedule.class, KETProgramSchedule.NUMBER), false), new int[] { idx0 });

                          QueryResult qr = PersistenceHelper.manager.find(qs);
                          
                          String outString = "";
                          while (qr.hasMoreElements()) {
                              Object[] objArr = (Object[]) qr.nextElement();
                              KETProgramSchedule ketProgramSchedule = (KETProgramSchedule) objArr[0];
                              if(outString!=""){
                        	    outString += ", ";   
                              }
                              outString += "<a href=\"#\" onclick=\"javascript:getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid="+CommonUtil.getOIDString(ketProgramSchedule)+"','ProgramViewPopup',800,620);\">"+ketProgramSchedule.getNumber()+"</a>";
                              
                          }
                          out.print(outString);
                          %>
                    </td>
                    <td class="tdwhiteM">
                      <a href="#" onClick="javascript:SearchUtil.projectCard('<%=CommonUtil.getOIDString(projectData.e3psProject)%>','<%=projectData.ViewpjtNo%>');" class="btn_blue"><img src="/plm/portal/images/calendar.gif"></a>
                      <%-- 
	                    <table border="0" cellspacing="0" cellpadding="0">
	                        <tr>
	                            <td width="10"><img src="/plm/portal/images/btn_1_!.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1_!.gif">
                                    <a href="#" onClick="javascript:SearchUtil.projectCard('<%=CommonUtil.getOIDString(projectData.e3psProject)%>','<%=projectData.ViewpjtNo%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00415")%>프로젝트카드</a>
	                            </td>
	                            <td width="10"><img src="/plm/portal/images/btn_2_!.gif"></td>
	                        </tr>
	                    </table>
	                  --%>
                    </td>
                    <td class="tdwhiteM"><%=projectData.e3psProject.getLifeCycleState().getDisplay(messageService.getLocale())%></td>
                </tr>
	        <%
	                    }
	                }
	        %> 
        </table>
    </body>
</html>