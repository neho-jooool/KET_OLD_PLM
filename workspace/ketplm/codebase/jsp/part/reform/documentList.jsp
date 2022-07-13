<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.*"%>
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
                 e3ps.bom.common.iba.IBAUtil"%>
<%@ page import="ext.ket.part.util.*" %>
<%@ page import="ext.ket.shared.content.service.*" %>
<%@ page import="ext.ket.shared.content.entity.*" %>
<%@page import="wt.content.ContentItem"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("oid");      // oid
    String type = "";
    String partNo = "";

    WTPart wt = null;

    if( poid != null && !poid.equals("") ) {
        wt = (WTPart) KETObjectUtil.getObject(poid);
        if (wt != null) {
            type = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartType);
            partNo = wt.getNumber();
        }
    }
    
    QuerySpec qs = new QuerySpec();
    qs.addClassList(KETProjectDocument.class, true);
    qs.addClassList(KETDocumentPartLink.class, false);
    
    /* 
    SearchUtil.setOrderBy(qs, KETProjectDocument.class, 0, KETProjectDocument.DOCUMENT_NO, false);
    SearchUtil.setOrderBy(qs, KETProjectDocument.class, 0, "versionInfo.identifier.versionSortId", true);
    QueryResult linkQrDoc = PersistenceHelper.manager.navigate(wt, KETDocumentPartLink.DOCUMENT_ROLE, qs, true);        //관련문서
     */

	SearchCondition sc = new SearchCondition(new ClassAttribute(KETDocumentPartLink.class, "roleAObjectRef.key.id"), "=", new ClassAttribute(KETProjectDocument.class,
			"thePersistInfo.theObjectIdentifier.id"));
	sc.setFromIndicies(new int[] { 1, 0 }, 0);
	sc.setOuterJoin(0);
	qs.appendWhere(sc, new int[] { 1, 0 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(KETDocumentPartLink.class, KETDocumentPartLink.PART_NO, SearchCondition.EQUAL, partNo), new int[] { 1 });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(KETProjectDocument.class, "state.state", SearchCondition.EQUAL, "APPROVED"), new int[] { 0 });
	SearchUtil.setOrderBy(qs, KETProjectDocument.class, 0, KETProjectDocument.DOCUMENT_NO, false);
	SearchUtil.setOrderBy(qs, KETProjectDocument.class, 0, "versionInfo.identifier.versionSortId", true);
	
	QueryResult linkQrDoc = PersistenceHelper.manager.find(qs);
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/multicombo.jsp"%>
<script language='javascript'>
//문서 상세조회 팝업
function viewDocPop(docOid){
    var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid=" + docOid;
    openWindow(url,"","830","670","status=yes,scrollbars=no,resizable=no");
}
</script>
</head>
    <body>
    
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td width="40" class="tdgrayM" >No</td>
				<td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
				<td width="*" class="tdgrayM" ><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
				<td width="130" class="tdgrayM" ><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
				<td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
				<td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
				<td width="90" class="tdgrayM" ><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
				<td width="90" class="tdgrayM" ><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
				<td width="80" class="tdgrayM" ><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
				<td width="50" class="tdgrayM" ><%=messageService.getString("e3ps.message.ket_message", "02957") %><%--파일--%></td>
			</tr>
	<%      if( (linkQrDoc == null) || (linkQrDoc.size() == 0)) { %>
			<tr>
			    <td class="tdwhiteM" colspan="10"><%=messageService.getString("e3ps.message.ket_message", "00913") %><%--관련 문서가 없습니다--%>
			    </td>
			</tr>
	<%      } else { %>
				<%
				int docCount = 1;
				
				List<String> checkDoc = new ArrayList<String>();
				 
				while( linkQrDoc.hasMoreElements() ){
					
				    //pDoc =   (KETProjectDocument)linkQrDoc.nextElement();
				    
					Object[] o = (Object[]) linkQrDoc.nextElement();
					KETProjectDocument pDoc = (KETProjectDocument) o[0];
				    
				    String docNo = pDoc.getDocumentNo();
				    
				    //중복체크로 구버전 문서 스킵
				    if(checkDoc.contains(docNo)) continue;
				    checkDoc.add(docNo);
				    
				    String docOid = KETObjectUtil.getOidString(pDoc);
				    String iconpath = "";
				    
				    QueryResult linkDocCategory = null;  
				    linkDocCategory = PersistenceHelper.manager.navigate( pDoc, "documentCategory", KETDocumentCategoryLink.class );
				    KETDocumentCategory docCategory = null;
				    while( linkDocCategory.hasMoreElements() ){
					   docCategory =   (KETDocumentCategory)linkDocCategory.nextElement();
				    }
				    
				    //문서객체로 주첨부파일의 정보를 가져온다.
		            if ( pDoc instanceof FormatContentHolder ) {
		                FormatContentHolder holder = (FormatContentHolder)pDoc;
		                ContentInfo info = ContentUtil.getPrimaryContent(holder);
		                if ( info != null ) {
		                    iconpath = info.getIconURLStr();
		                }
		            }
				    
				%>
			<tr>
				<td class="tdwhiteM"><%=docCount++%></td>
				<td class="tdwhiteM"><a href="javascript:viewDocPop('<%=docOid%>');"><%=docNo%></a></td>
				<td class="tdwhiteM"><%=pDoc.getTitle()%></td>
				<td class="tdwhiteM"><ket:codeValueByCode codeType="DOCUMENTCATEGORY" code="<%=docCategory.getCategoryCode()%>"/></td>
				<td class="tdwhiteM"><%=KETObjectUtil.getVersion(pDoc)%></td>
				<td class="tdwhiteM"><%=pDoc.getCreatorFullName()%></td>
				<td class="tdwhiteM"><%=DateUtil.getDateString(pDoc.getCreateTimestamp(),"d")%></td>
				<td class="tdwhiteM"><%=DateUtil.getDateString(pDoc.getModifyTimestamp(),"d")%></td>
				<td class="tdwhiteM"><%=State.toState(pDoc.getState().toString()).getDisplay(messageService.getLocale())%></td>
				<td class="tdwhiteM"><p><%=iconpath%></p></td>
			</tr>
			<%
			    }
			}
			%>
		</table>
    
    </body>
</html>