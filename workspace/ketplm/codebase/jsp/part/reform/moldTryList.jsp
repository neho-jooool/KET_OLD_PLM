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
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("oid");      
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">

</script>
</head>
    <body>
    
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td width="60" class="tdgrayM">No</td>
                <td width="130" class="tdgrayM">Try No</td>
                <td width="250" class="tdgrayM">Sub ver.</td>
                <td width="100" class="tdgrayM">작성자</td>
                <td width="100" class="tdgrayM">Try 장소</td>
                <td width="110" class="tdgrayM">디버깅 사유</td>
                <td width="110" class="tdgrayM">작성일자</td>
                <td width="110" class="tdgrayM">수정일자</td>
                <td width="110" class="tdgrayM">상태</td>
            </tr>
            <tr>
                <td class="tdwhiteM" colspan="12">관련 금형 Try가 없습니다.</td>
            </tr>
        </table>
    
    </body>
</html>