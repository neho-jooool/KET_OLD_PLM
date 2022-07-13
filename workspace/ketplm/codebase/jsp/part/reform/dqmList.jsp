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
<%@ page import="ext.ket.dqm.entity.*" %>


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

    
    KETDqmDTO ketDqmDTO = new KETDqmDTO();
    ketDqmDTO.setRelatedPartOid(poid);
    
    
    QuerySpec query = new QuerySpec();
    SearchCondition sc = null;
    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
    int idxRaise = query.appendClassList(KETDqmRaise.class, true);
    int idxClose = query.appendClassList(KETDqmClose.class, true);
    int idxPart = query.appendClassList(WTPart.class, true);

    ClassAttribute caHeaderRaise = new ClassAttribute(KETDqmHeader.class, "raiseReference.key.id");
    ClassAttribute caRaiseHeader = new ClassAttribute(KETDqmRaise.class, "thePersistInfo.theObjectIdentifier.id");
   
    ClassAttribute caHeaderClose = new ClassAttribute(KETDqmHeader.class, "closeReference.key.id");
    ClassAttribute caCloseHeader = new ClassAttribute(KETDqmClose.class, "thePersistInfo.theObjectIdentifier.id");

    ClassAttribute caPart = new ClassAttribute(WTPart.class, "thePersistInfo.theObjectIdentifier.id");
    ClassAttribute caRaisePart = new ClassAttribute(KETDqmRaise.class, "partReference.key.id");
     
    
    sc = new SearchCondition(caHeaderRaise, SearchCondition.EQUAL, caRaiseHeader);

    query.appendWhere(sc, new int[] { idxHeaer, idxRaise });

    sc = new SearchCondition(caHeaderClose, SearchCondition.EQUAL, caCloseHeader);
    sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
    query.appendAnd();
    query.appendWhere(sc, new int[] { idxHeaer, idxClose });
    
    query.appendAnd();
    sc = new SearchCondition(caPart, SearchCondition.EQUAL, caRaisePart);
    query.appendWhere(sc, new int[] { idxPart, idxRaise  });

    query.appendAnd();
    sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, partNo);
    query.appendWhere(sc, new int[] { idxPart });
    
    List<KETDqmDTO> ketDqmDTOList = new ArrayList<KETDqmDTO>();
    
    QueryResult queryResult = PersistenceHelper.manager.find(query);
    
    while (queryResult.hasMoreElements()) {
        Object[] objArr = (Object[]) queryResult.nextElement();
        KETDqmDTO rsltKETDqmDTO = new KETDqmDTO();
        rsltKETDqmDTO = rsltKETDqmDTO.KETDqmDTOGrid((KETDqmHeader) objArr[0], rsltKETDqmDTO);
        rsltKETDqmDTO.setRelatedEcrNo(StringUtil.checkNull(rsltKETDqmDTO.getRelatedEcrNo()).replace(",", "<br>"));
        ketDqmDTOList.add(rsltKETDqmDTO);
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
function viewDqmPopup(oid){
	getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=view&oid='+oid,oid,1100,768);
}
</script>
</head>
    <body>
    
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td width="60" class="tdgrayM">No</td>
                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09002") %><%--문제내용--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09003") %><%--불량구분--%></td>
                <td width="250" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09004") %><%--불량유형--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "07137") %><%--발생처--%></td>
                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "07138") %><%--발생일--%></td>
                <!-- <td width="110" class="tdgrayM">담당자</td>  -->
                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "07139") %><%--발생구분--%></td>
                <td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00907") %><%--관련 ECO--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            </tr>
        
        <%      if( (ketDqmDTOList == null) || (ketDqmDTOList.size() == 0)) { %>
                <tr>
                    <td class="tdwhiteM" colspan="12">관련 개발품질문제가 없습니다.</td>
                </tr>
        <%      } else { %>
        <%
                    int listCount = ketDqmDTOList.size();
                    for(int inx = 0; inx < ketDqmDTOList.size(); inx++) {
                	    KETDqmDTO rsltDqmDTO = ketDqmDTOList.get(inx);
        %>
                <tr>
                    <td class="tdwhiteM"><%=inx+1%></td>
	                <td class="tdwhiteM" title="<%=rsltDqmDTO.getProblem()%>"><a href="javascript:viewDqmPopup('<%=rsltDqmDTO.getOid()%>');"><div style="width:116px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;"><%=rsltDqmDTO.getProblem()%></div><a></a></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getDefectDivName())%></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getDefectTypeName())%></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getOccurName())%></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getOccurDate())%></td>
	                <!-- <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getActionUserName())%></td>-->
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getCartypeName())%></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getOccurDivName())%></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getReviewDate())%></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getRelatedEcrNo())%></td>
	                <td class="tdwhiteM"><%=StringUtil.checkNull(rsltDqmDTO.getDqmStateName())%></td>
                </tr>
        <%
                    }
                }
        %>
        </table>
    
    </body>
</html>