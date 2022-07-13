<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.ProjectChangeStop"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.common.query.SearchUtil"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    String command = request.getParameter("command");

    String pjtType = "";
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);

    QuerySpec qs = null;
    QueryResult qr = null;
    try {
        long idLong = CommonUtil.getOIDLongValue(project.getMaster());
        qs = new QuerySpec();
        int idx = qs.addClassList(ProjectChangeStop.class, true);
        qs.appendWhere(new SearchCondition(ProjectChangeStop.class, "pcsMasterReference.key.id", "=", idLong), new int[] {idx});
        SearchUtil.setOrderBy(qs, ProjectChangeStop.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
        qr = PersistenceHelper.manager.find(qs);

    }
    catch(Exception e) {
	Kogger.error(e);
    }
%>


<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01492") %><%--변경 사유--%></title>
<base target="_self">

<link rel="stylesheet" href="/plm/portal/css/e3ps.css">

<script type="text/javascript" src="/plm/portal/js/script.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/select.js"></script>
<script type="text/javascript" src="/plm/portal/js/table.js"></script>
<script type="text/javascript" src="/plm/portal/js/viewObject.js"></script>
<script type="text/javascript"  src="/plm/portal/js/ajax.js"></script>
<script type="text/javascript" src="/plm/portal/js/checkbox2.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<%@include file="/jsp/project/template/ajaxProgress.html"%>

<style type="text/css">
body {
    margin-top:0px;
    margin-left:0px;
    margin-right: 0px;
    margin-bottom: 0px;
}

table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}

img {
    vertical-align: middle;
}

input {
    vertical-align:middle;line-height:22px;
}
</style>

<script type="text/javascript">
    function stateChangeStopView(oid, pcsOid){
        var url = "/plm/jsp/project/StopHistoryView.jsp?oid="+oid+"&pcsOid="+pcsOid;
//         openSameName(url,"790","730","status=no ,scrollbars=yes ,resizable=no");
        openOtherName(url,"historyView","900","800","status=no,scrollbars=yes,resizable=1");
    }
</script>


</head>
<body>
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->

<form id="frm" name="frm" method="post" enctype="multipart/form-data">
<input type="hidden" name="oid" value="<%=oid%>" />
<input type="hidden" name="command" />

<table style="width: 100%;">
<tr>
    <td valign="top">
        <table style="width: 100%;">
        <tr>
            <td>
    <!------------------------------ 본문 Start //------------------------------>
                <table style="width: 100%;">
                    <tr>
                        <td  class="tab_btm2"></td>
                </tr>
                </table>
                <table style="width: 100%;">
                    <tr>
                        <td class="tdblueM" style="width: 100px;"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                        <td class="tdblueM" style="width: 100px;"><%=messageService.getString("e3ps.message.ket_message", "01505") %><%--변경구분--%></td>
                        <td class="tdblueM" style="width: 210px;"><%=messageService.getString("e3ps.message.ket_message", "01682") %><%--사유--%></td>
                        <td class="tdblueM" style="width: 100px;"><%=messageService.getString("e3ps.message.ket_message", "02020") %><%--시행일--%></td>
                        <td class="tdblueM" style="width: 50px;"><%=messageService.getString("e3ps.message.ket_message", "02794") %><%--첨부--%></td>
                    </tr>
                    <%
                        if ( qr == null || qr.size() == 0 ) {
                    %>
                    <tr>
                        <td class='tdwhiteM0' align='center' colspan='5'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                    </tr>
                    <%
                        }
                        else {
                            Object[] obj  = null;
                                while ( qr.hasMoreElements() ) {
                                    obj = (Object[])qr.nextElement();
                                    ProjectChangeStop ps = (ProjectChangeStop)obj[0];
                                    String pcsOid = CommonUtil.getOIDString(ps);
                                    String changeType = "";
                                    String changeDetail = "";
                                    String changeDate = "";
                                    String fileImg = "&nbsp;";
    
                                    String webEditor = "";
                                    String webEditorText = "";
    
                                    if ( ps.getChangeType() != null ) { changeType = ps.getChangeType(); }
                                    if ( ps.getChangeDetil() != null ) { changeDetail = ps.getChangeDetil(); }
                                    if ( ps.getChangeDate() != null ) { changeDate = DateUtil.getDateString(ps.getChangeDate(), "D"); }
    
                                    if ( ps.getWebEditor() != null) { webEditor = (String)ps.getWebEditor(); }
                                    if ( ps.getWebEditorText() != null) { webEditorText = (String)ps.getWebEditorText(); }
    
                                    Vector contents = new Vector();
                                    contents = ContentUtil.getSecondaryContents(ps);
                                    if ( contents.size() > 0 ) {
                                        fileImg = "<img src='/plm/portal/images/icon_file.gif' border='0'>";
                                    }
    
                                    String stopChageType = "";
                                    if ( ps.getChangeStopType() != null ) {
                                        Map<String, Object> numCodeValue = new HashMap<String, Object>();
                                        String change = "";
                                        if ( changeType.equals("중지") || changeType.equals("중지검토") | changeType.equals("중지완료")) {
                                            change = "PROJECTSTOPTYPE";
                                        }
                                        else if ( changeType.equals("취소") ) {
                                            change = "PROJECTCANCELTYPE";
                                        }
    
                                        numCodeValue = NumberCodeUtil.getNumberCodeValueMap(change, ps.getChangeStopType().toString(), messageService.getLocale().toString());
    
                                        stopChageType = numCodeValue.get("value").toString();
                                    }
                    %>
                    <tr>
                        <td class="tdwhiteM" style="width: 100px;"><%=changeType%></td>
                        <td class="tdwhiteM" style="width: 100px;"><%=stopChageType%></td>
                        <td class="tdwhiteM" style="text-align:left; cursor:pointer;" onclick="javascript:stateChangeStopView('<%=oid%>','<%=pcsOid%>');">
                            <div class="changeDetail" style="width:240px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
                                 <%=webEditorText%>
                            </div>
                        </td>
                        <td class="tdwhiteM" style="width: 100px;"><%=changeDate%></td>
                        <td class="tdwhiteM" style="width: 50px;"><%=fileImg%>&nbsp;</td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </td>
        </tr>
        </table>
    </td>
</tr>
</table>
</form>
</body>
</html>
