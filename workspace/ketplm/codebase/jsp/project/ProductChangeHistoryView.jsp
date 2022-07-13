<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
    String oid = "";

    if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
        oid = request.getParameter("oid");
    }

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    ProjectMaster projectMaster = null;
    if(project != null){
        projectMaster = project.getMaster();
    }

    QueryResult qr = null;
    QuerySpec qs = null;
    try{
        qs = new QuerySpec(ProductHistoryChage.class);
        long mtLong = CommonUtil.getOIDLongValue(projectMaster);
        qs.appendWhere(new SearchCondition(ProductHistoryChage.class, "masterChangeReference.key.id", "=", mtLong), new int[] {0});
        qr = PersistenceHelper.manager.find(qs);
    }catch(Exception e){
	Kogger.error(e);
    }
%>
<%@page import="e3ps.project.ProjectMaster"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.ProductHistoryChage"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<TITLE><%=messageService.getString("e3ps.message.ket_message", "02549") %><%--제품 프로젝트 등록--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
</head>
<body>
    <form>
        <input type="hidden" name="oid" value="<%=oid%>"></input>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02597")%><%--제품변경사유이력--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <td><table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table></td>
                                            </table></td>
                                </tr>
                            </table>
                        </tr>
                        <tr>
                            <td valign="top">
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tdblueM" width="100px"><%=messageService.getString("e3ps.message.ket_message", "01520")%><%--변경일--%></td>
                                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01498")%><%--변경 사유--%></td>
                                    </tr>
                                    <%
                                        if (qr != null) {
                                    		while (qr.hasMoreElements()) {
                                    		    ProductHistoryChage pc = (ProductHistoryChage) qr.nextElement();
                                    		    String pcDate = "";
                                    		    String pcDesc = "";
                                    		    if (pc.getChageDate() != null) {
                                    			pcDate = DateUtil.getDateString(pc.getChageDate(), "d");
                                    		    }
                                    		    if (pc.getChageDetil() != null) {
                                    			pcDesc = pc.getChageDetil();
                                    		    }
                                    %>
                                    <tr>
                                        <td class='tdwhiteL text-center'><%=pcDate%></td>
                                        <td class='tdwhiteL'><textarea name="reason" style="width: 100%" rows="2" class="fm_area"
                                                readOnly><%=pcDesc%></textarea></td>
                                    </tr>
                                    <%} %>
                                    <%if(qr.size() == 0 ){ %>
                                    <tr>
                                        <td class='tdwhiteM0' align='center' colspan='2'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>
                                        </td>
                                    </tr>
                                    <%} %>
                                    <%} %>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>
