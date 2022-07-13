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
<%@ page import="ext.ket.sample.entity.*" %>                 
<%@ page import="ext.ket.part.util.*" %>

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
    
    String partNo = "";

    WTPart wt = null;

    if( poid != null && !poid.equals("") ) {
        wt = (WTPart) KETObjectUtil.getObject(poid);
        if (wt != null) {
            partNo = wt.getNumber();
        }
    }
    
    QuerySpec query = new QuerySpec();
    
    int smpReqIdx = query.appendClassList(KETSampleRequest.class, true);
    int smpPartIdx = query.appendClassList(KETSamplePart.class, true);
    int smpPartLinkIdx = query.appendClassList(KETSamplePartLink.class, false);
    int idxPart = query.appendClassList(WTPart.class, false);
    
    ClassAttribute caSmpReq = new ClassAttribute(KETSampleRequest.class, "thePersistInfo.theObjectIdentifier.id");
    ClassAttribute caSmpPart = new ClassAttribute(KETSamplePart.class, "thePersistInfo.theObjectIdentifier.id");
    ClassAttribute caSmpReqLink = new ClassAttribute(KETSamplePartLink.class, "roleAObjectRef.key.id");
    ClassAttribute caSmpPartLink = new ClassAttribute(KETSamplePartLink.class, "roleBObjectRef.key.id");
    
    ClassAttribute caPart = new ClassAttribute(WTPart.class, "thePersistInfo.theObjectIdentifier.id");
    ClassAttribute caSmp = new ClassAttribute(KETSamplePart.class, "partReference.key.id");
    
    SearchCondition sc = null;
    
    sc = new SearchCondition(caSmpReq, SearchCondition.EQUAL, caSmpReqLink);
    sc.setFromIndicies(new int[] { smpReqIdx, smpPartLinkIdx }, 0);
    sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
    query.appendWhere(sc, new int[] { smpReqIdx, smpPartLinkIdx });
    
    query.appendAnd();
    sc = new SearchCondition(caSmpPart, SearchCondition.EQUAL, caSmpPartLink);
    sc.setFromIndicies(new int[] { smpPartIdx, smpPartLinkIdx }, 0);
    sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
    query.appendWhere(sc, new int[] { smpPartIdx, smpPartLinkIdx });
    
    query.appendAnd();
    sc = new SearchCondition(caPart, SearchCondition.EQUAL, caSmp);
    query.appendWhere(sc, new int[] { idxPart, smpPartIdx  });
    
    query.appendAnd();
    sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, partNo);
    query.appendWhere(sc, new int[] { idxPart });
    
    SearchUtil.setOrderBy(query, KETSampleRequest.class, smpReqIdx, KETSampleRequest.REQUEST_NO, true);
    
    List<SampleRequestDTO> sampleRequestDTOList = new ArrayList<SampleRequestDTO>();
    
    QueryResult result = PersistenceHelper.manager.find(query);
    if (result != null && result.size() > 0) {
        if (result.hasMoreElements()) {
            while (result.hasMoreElements()) {
        	   Object[] objArr = (Object[]) result.nextElement();
        	   SampleRequestDTO sampleRequestDTO = new SampleRequestDTO();
        	   sampleRequestDTO = sampleRequestDTO.SampleRequstList(sampleRequestDTO,(KETSampleRequest) objArr[0],(KETSamplePart) objArr[1]);
        	   sampleRequestDTOList.add(sampleRequestDTO);
            }
        }
    }
    
    Kogger.debug("query >> " + query);
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
function viewSmpReqPopup(oid){
	getOpenWindow2('/plm/ext/sample/sampleRequstMainForm.do?oid='+oid,'sampleRequstMainForm',1024,670);
}
</script>
</head>
    <body>

      <% if( (sampleRequestDTOList == null) || (sampleRequestDTOList.size() == 0)) { %>
    
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td width="60" class="tdgrayM">No</td>
                <td width="130" class="tdgrayM">요청서 No</td>
                <td width="250" class="tdgrayM">용도</td>
                <td width="100" class="tdgrayM">고객사</td>
                <td width="100" class="tdgrayM">고객담당자</td>
                <td width="110" class="tdgrayM">적용차종</td>
                <td width="110" class="tdgrayM">개발단계</td>
                <td width="110" class="tdgrayM">요청자</td>
                <td width="110" class="tdgrayM">요청일</td>
                <td width="160" class="tdgrayM">담당자</td>
                <td width="100" class="tdgrayM">불출일</td>
                <td width="100" class="tdgrayM">불출수량</td>
                <td width="100" class="tdgrayM">상태</td>
            </tr>            
            <tr>
                <td class="tdwhiteM" colspan="13">관련 Sample 이력이 없습니다.</td>
            </tr>
      <% } else { %>
    
        <table border="0" cellspacing="0" cellpadding="0" width="1024">
            <tr>
                <td width="60" class="tdgrayM">No</td>
                <td width="130" class="tdgrayM">요청서 No</td>
                <td width="250" class="tdgrayM">용도</td>
                <td width="100" class="tdgrayM">고객사</td>
                <td width="100" class="tdgrayM">고객담당자</td>
                <td width="110" class="tdgrayM">적용차종</td>
                <td width="110" class="tdgrayM">개발단계</td>
                <td width="110" class="tdgrayM">요청자</td>
                <td width="110" class="tdgrayM">요청일</td>
                <td width="160" class="tdgrayM">담당자</td>
                <td width="100" class="tdgrayM">불출일</td>
                <td width="100" class="tdgrayM">불출수량</td>
                <td width="100" class="tdgrayM">상태</td>
            </tr>            
	  <%
	          int listCount = sampleRequestDTOList.size();
	          for(int inx = 0; inx < sampleRequestDTOList.size(); inx++) {
	              SampleRequestDTO sampleRequestDTO = sampleRequestDTOList.get(inx);
      %>
                <tr>
                    <td class="tdwhiteM"><%=inx+1%></td>
                    <td class="tdwhiteM"><a href="javascript:viewSmpReqPopup('<%=sampleRequestDTO.getOid()%>');"><%=sampleRequestDTO.getRequestNo()%><a></a></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getPurpose())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getCustomerName())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getCustomerContractor())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getCarTypeName())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getDevelopeStageName())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getCreateUserName())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getCreateDate())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getPmUserName())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getDispensationDate())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getDispensationCountArr())%></td>
                    <td class="tdwhiteM"><%=StringUtil.checkNull(sampleRequestDTO.getSampleRequestStateName())%></td>
                </tr>
	  <%
	          }
	      }
	  %>
        </table>
    
    </body>
</html>