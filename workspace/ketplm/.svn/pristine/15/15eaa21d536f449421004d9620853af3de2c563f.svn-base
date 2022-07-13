<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.dao.CostReportDao"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.sql.Connection"%>
<%
    String pk_cr_group				=	StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String report_pk					=	StringUtil.checkNull(request.getParameter("report_pk"));
    String table_row					=	StringUtil.checkNull(request.getParameter("table_row"));
    String pjt_no						=	StringUtil.checkNull(request.getParameter("pjt_no"));
    String rev_no 						= 	StringUtil.checkNull(request.getParameter("rev_no"));
    String user_case_count = "";
    Connection conn = null;
    conn = DBUtil.getConnection();
    CostReportDao dao = new CostReportDao(conn);
    try{
        user_case_count = dao.getCase_type_user(pk_cr_group,rev_no);
    }catch(Exception e){
        e.printStackTrace();
        e.getMessage();
    }

    String link_url = "http://plm.ket.com/plm/jsp/cost/costreport/cost_report_1.jsp?cost_report_add=ok&visitor=4&pk_cr_group="+pk_cr_group+"&table_row="+table_row+"&report_pk="+report_pk+"&rev_no="+rev_no+"&user_case_count="+user_case_count;
    int C_count = 0;
    String[] pjt_no_ = {};

    pjt_no_ = pjt_no.split(",");

    C_count = pjt_no_.length;


    try{

        for(int i=0; i<C_count; i++){
            dao.urlCostIUpdate(link_url, pjt_no_[i]);
        }

    }catch(Exception e){
        e.printStackTrace();
        e.getMessage();
    }finally{
        if(conn!=null) {conn.close();}
    }

%>
<script language="javascript">
    alert("URL을 등록하였습니다.");
    this.close();
</script>
