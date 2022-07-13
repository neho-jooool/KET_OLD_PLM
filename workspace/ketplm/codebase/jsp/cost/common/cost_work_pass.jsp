<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%
    String pk_cr_group=request.getParameter("pk_cr_group");    // 요청서 그룹번호(PLM-검토PJT)
    String rev_no=request.getParameter("rev_no");    // REVNO
    String data_type=request.getParameter("data_type");    // CASE 구분
    String group_case_count=request.getParameter("group_case_count")!=null?request.getParameter("group_case_count"):"0";    // 요청당시추가CASE
    String table_row=request.getParameter("table_row")!=null?request.getParameter("table_row"):"0";    // 구성품 갯수
    String url_chk = request.getParameter("url_chk");
    String pjt_no = request.getParameter("pjt_no")!=null?request.getParameter("pjt_no"):"";

    Connection conn = null;
    conn = DBUtil.getConnection();
    CostComDao dao = new CostComDao(conn);
    dao.CostWorkPass(pk_cr_group, rev_no, url_chk, pjt_no, table_row, "");
%>

<script language= "javascript">
    alert("자료의 상태를 완료단계로 변경하였습니다.");
    //opener.parent.location.href = "/plm/jsp/cost/costWork/costWork.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&data_type=main&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>';
    this.close();
</script>