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

    //ArrayList linkQrDwg = null;      // 관련도면
    //linkQrDwg = EDMPartHelper.getReferenceDocs(wt);
     List<EPMDocument> linkQrDwg = EDMPartHelper.getReferenceEPMDocsByWTPart(wt);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
//도면 상세조회 팝업
function viewDwgPop(dwgOid){
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + dwgOid + "&ketCustomFlag=Y";
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=10,left=100,width=800,height=800');
    newWinSPart.focus();
    newWinSPart.location.href = url;
    //openWindow(url,"","820","800",",toolbar=0,scrollbar=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,top=100,left=100");
}
</script>
</head>
	<body>
	
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
		    <tr>
				<td width="40" class="tdgrayM">No</td>
				<td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
				<td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
				<td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
				<td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%></td>
				<td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></td>
				<td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
				<td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
				<td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
				<td width="50" class="tdgrayM">파일</td>
		    </tr>
	<%      if( (linkQrDwg == null) || (linkQrDwg.size() == 0)) { %>
			<tr>
			    <td class="tdwhiteM" colspan="10"><%=messageService.getString("e3ps.message.ket_message", "00911") %><%--관련 도면이 없습니다--%></td>
			</tr>
	<%      } else { %>
	<%
				String dwgOid = "";
				int dwgCount = 1;
				for(int inx = 0; inx < linkQrDwg.size(); inx++) {
				    //Object[] objs = (Object[])linkQrDwg.get(inx);
				    //EPMDocument epm = (EPMDocument)objs[1];
				    EPMDocument epm = linkQrDwg.get(inx);
				    dwgOid = KETObjectUtil.getOidString(epm);
				    String CADAppType = e3ps.common.iba.IBAUtil.getAttrValue(epm, "CADAppType");
				    String CADCategory = e3ps.common.iba.IBAUtil.getAttrValue(epm, "CADCategory");
				    String manufVersion = StringUtil.checkNull(e3ps.common.iba.IBAUtil.getAttrValue(epm, EDMHelper.IBA_MANUFACTURING_VERSION)) ;
				    
				    String iconpath = ""; 
					//객체로 주첨부파일의 정보를 가져온다.
					if ( epm instanceof FormatContentHolder ) {
					    FormatContentHolder holder = (FormatContentHolder)epm;
					    ContentInfo info = ContentUtil.getPrimaryContent(holder);
					    if ( info != null ) {
					        iconpath = info.getIconURLStr();
					    }
					}
	%>
			<tr>
				<td class="tdwhiteM"><%=dwgCount++%></td>
				<td class="tdwhiteM"><a href="javascript:viewDwgPop('<%=dwgOid%>');"><%=epm.getNumber()%></a></td>
				<td class="tdwhiteM"><%=epm.getName()%></td>
				<td class="tdwhiteM"><%=manufVersion%></td>
				<td class="tdwhiteM"><%=CADCategory%></td>
				<td class="tdwhiteM"><%=CADAppType%></td>
				<td class="tdwhiteM"><%=epm.getCreatorFullName()%></td>
				<td class="tdwhiteM"><%=DateUtil.getDateString(epm.getCreateTimestamp(),"d")%></td>
				<td class="tdwhiteM"><%=State.toState(epm.getState().toString()).getDisplay(messageService.getLocale())%></td>
				<td class="tdwhiteM"><p><%=iconpath%></p></td>
			</tr>
	<%
			    }
			}
	%>
	    </table>
	
	</body>
</html>