<%@ page language="java" contentType="text/html;charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.dao.CostReportDao"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@ page import="e3ps.common.util.KETParamMapUtil"%>
<%
    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());
    String pk_cr_group		= request.getParameter("pk_cr_group");
    String table_row			= request.getParameter("table_row");
    String report_pk			= request.getParameter("report_pk");
    String rev_no			= request.getParameter("rev_no");
    String user_case_count	= request.getParameter("user_case_count");
    //String note_4 = paramMap.getString("note_4");

    //String note_4	= request.getParameter("note_4");
    String note_4 = java.net.URLDecoder.decode(request.getParameter("note_4"), "utf-8");
    Connection conn = null;
    conn = DBUtil.getConnection();
    CostReportDao dao = new CostReportDao(conn);
    dao.setNote4Update(report_pk,note_4);
%>
<HTML>
<HEAD>
<title>�ѱ����� ���� ���� �ý��� - �������� </title>
</HEAD>
<CENTER>
<br>
<br>
</CENTER>
<BODY onload="CALL();">
<script language= "javascript">
    function CALL(){
        alert("�����Ǿ����ϴ�.");
        this.close();
    }
</script>
</BODY>
</HTML>