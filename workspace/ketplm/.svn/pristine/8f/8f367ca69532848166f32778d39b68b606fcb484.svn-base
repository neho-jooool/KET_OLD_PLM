<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostReportCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String Ename 	= StringUtil.checkNull((String)session.getAttribute("Ename"));
    String Dname	    = StringUtil.checkNull((String)session.getAttribute("Dname"));
    String emp_no	= StringUtil.checkNull((String)session.getAttribute("emp_no"));
    String dept_no	= StringUtil.checkNull((String)session.getAttribute("dept_no"));
    String K_pass	= StringUtil.checkNull((String)session.getAttribute("K_pass"));
    String position	= StringUtil.checkNull((String)session.getAttribute("position"));
    String login_id	= StringUtil.checkNull((String)session.getAttribute("login_id"));//권한 관련 id
    String cost_id	= StringUtil.checkNull((String)session.getAttribute("cost_id"));//user id
    String group_m	= StringUtil.checkNull((String)session.getAttribute("group_m"));
    String costAuth	= StringUtil.checkNull((String)session.getAttribute("costAuth"));

    if(group_m.equals("G")){
        position = "그룹장";
    }
    String visitor = request.getParameter("visitor")!=null?request.getParameter("visitor"):"";
    String rev_no = request.getParameter("rev_no")!=null?request.getParameter("rev_no"):"";
    String user_case_count = request.getParameter("user_case_count")!=null?request.getParameter("user_case_count"):"";
    String pk_cr_group = request.getParameter("pk_cr_group")!=null?request.getParameter("pk_cr_group"):"";
    String cost_report_add = request.getParameter("cost_report_add")!=null?request.getParameter("cost_report_add"):"";
    String table_row = request.getParameter("table_row")!=null?request.getParameter("table_row"):"";
    String report_pk = request.getParameter("report_pk")!=null?request.getParameter("report_pk"):"";
    String page_no = request.getParameter("page_no")!=null?request.getParameter("page_no"):"";
    String select_name = request.getParameter("select_name")!=null?request.getParameter("select_name"):"";
    String pk_cw = request.getParameter("pk_cw")!=null?request.getParameter("pk_cw"):"";
    String chk_list = request.getParameter("chk_list")!=null?request.getParameter("chk_list"):"";
    String note_1 = request.getParameter("note_1")!=null?request.getParameter("note_1"):"";
    String note_2 = request.getParameter("note_2")!=null?request.getParameter("note_2"):"";
    String note_3 = request.getParameter("note_3")!=null?request.getParameter("note_3"):"";
    String note_4 = request.getParameter("note_4")!=null?request.getParameter("note_4"):"";
    String note_5 = request.getParameter("note_5")!=null?request.getParameter("note_5"):"";
    String note_5_1 = request.getParameter("note_5_1")!=null?request.getParameter("note_5_1"):"";
    String case_1_note = request.getParameter("case_1_note")!=null?request.getParameter("case_1_note"):"";
    String case_2_note = request.getParameter("case_2_note")!=null?request.getParameter("case_2_note"):"";
    String case_3_note = request.getParameter("case_3_note")!=null?request.getParameter("case_3_note"):"";
    String note_6 = "";
    //String select_case = request.getParameter("select_case")!=null?request.getParameter("select_case"):"";
    //String us_change = request.getParameter("us_change")!=null?request.getParameter("us_change"):"";
    //String case_sel = request.getParameter("case_sel")!=null?request.getParameter("case_sel"):"";

    //if (visitor = "1" or visitor = "2") and Ename <>"" then
    //login_id = "pro_man"
    //session("id") = login_id
    //elseif (visitor = "3") and Ename <>"" then
    //login_id = "business"
    //session("id") = login_id
    //elseif (visitor = "1" or visitor = "2" or visitor = "3") and len(Ename) = 0 then
    //response.redirect "http://192.168.17.12/cost_acc/user_login.asp?page_url=cost_report_1&visitor="&visitor&"&pk_cr_group="&pk_cr_group&"&table_row="&table_row&"&report_pk="&report_pk&"&rev_no="&rev_no&"&user_case_count="&user_case_count
    //elseif visitor = "4"  then
    //login_id = "business"
    //session("id") = login_id
    //end if

    if(("1".equals(visitor) || "2".equals(visitor)) && !"".equals(Ename)){
        login_id = "pro_man";
        session.setAttribute("id", login_id);
    }else if("3".equals(visitor) && !"".equals(Ename)){
        login_id = "business";
        session.setAttribute("login_id", login_id);
    }else if(("1".equals(visitor) || "2".equals(visitor) || "3".equals(visitor)) && Ename.equals("")){
        response.sendRedirect("http://plm.ket.com/cost_main.jsp");
    }

    String[] start_pro = new String[70];
    String[] store = new String[70];
    String[] dest = new String[70];
    String[] to_cost = new String[70];
    String[] mold_count = new String[10];
    String[] press_count = new String[10];
    String[] line_count = new String[10];
    String[] pack_count = new String[10];
    String[] repack_count = new String[10];
    String[] mold_total = new String[5];
    String[] press_total = new String[5];
    String[] line_total = new String[5];
    String[] pack_total = new String[5];
    String[] repack_total = new String[5];
    String[] pro_usage = new String[20];
    String[][] group_no = new String[70][20];
    String[][] a_pk_cw = new String[70][20];
    String[][] t_pk_cw = new String[70][20];
    String[] pk_cw_array = new String[70];
    String[] part_name = new String[20];
    String[] part_type = new String[20];
    String[] part_no = new String[20];
    String[] su_year_1 = new String[70];
    String[] su_year_2 = new String[70];
    String[] su_year_3 = new String[70];
    String[] su_year_4 = new String[70];
    String[] su_year_5 = new String[70];
    String[] su_year_6 = new String[70];
    String[] su_year_7 = new String[70];
    String[] su_year_8 = new String[70];
    String[][] a_su_count = new String[70][20];
    String[][] a_su_year_1 = new String[70][20];
    String[][] a_su_year_2 = new String[70][20];
    String[][] a_su_year_3 = new String[70][20];
    String[][] a_su_year_4 = new String[70][20];
    String[][] a_su_year_5 = new String[70][20];
    String[][] a_su_year_6 = new String[70][20];
    String[][] a_su_year_7 = new String[70][20];
    String[][] a_su_year_8 = new String[70][20];
    String[][] t_su_year_1 = new String[70][40];
    String[][] t_su_year_2 = new String[70][40];
    String[][] t_su_year_3 = new String[70][40];
    String[][] t_su_year_4 = new String[70][40];
    String[][] t_su_year_5 = new String[70][40];
    String[][] t_su_year_6 = new String[70][40];
    String[][] t_su_year_7 = new String[70][40];
    String[][] t_su_year_8 = new String[70][40];
    String[] t_total_sales_1 = new String[70];    // 매출액
    String[] t_total_sales_2 = new String[70];
    String[] t_total_sales_3 = new String[70];
    String[] t_total_sales_4 = new String[70];
    String[] t_total_sales_5 = new String[70];
    String[] t_total_sales_6 = new String[70];
    String[] t_total_sales_7 = new String[70];
    String[] t_total_sales_8 = new String[70];
    String[] t_profit_1 = new String[70];    // 영업이익
    String[] t_profit_2 = new String[70];
    String[] t_profit_3 = new String[70];
    String[] t_profit_4 = new String[70];
    String[] t_profit_5 = new String[70];
    String[] t_profit_6 = new String[70];
    String[] t_profit_7 = new String[70];
    String[] t_profit_8 = new String[70];
    String[] t_per_profit_1 = new String[70];    // 영업이익율
    String[] t_per_profit_2 = new String[70];
    String[] t_per_profit_3 = new String[70];
    String[] t_per_profit_4 = new String[70];
    String[] t_per_profit_5 = new String[70];
    String[] t_per_profit_6 = new String[70];
    String[] t_per_profit_7 = new String[70];
    String[] t_per_profit_8 = new String[70];
    String[] make_type = new String[20];
    String[] client_cost = new String[20];
    String[] ket_cost = new String[20];
    String[] room_earn = new String[20];
    String[] target_cost = new String[20];
    String[] usage = new String[70];
    String[] pro_type = new String[50];
    String[][] actual_cost = new String[70][20];    // 판매기준총원가
    String[][] earn_rate = new String[70][20];    // 수익률
    String[][] t_actual_cost = new String[20][20];    // 판매기준총원가
    String[][] t_earn_rate = new String[20][20];    // 수익률
    String[][] FK_cost_request = new String[70][20];
    String[] avg_su = new String[70];    // 총판매기획수량
    String[][] mold_c_type = new String[70][20];

    String f_day = "";
    String Gr_day = "";
    String Leader_day = "";
    String Leader_name = "";
    String Gr_name = "";
    String JB_day = "";
    String JB_name = "";
    String p_leader_day = "";
    String p_leader_name = "";
    String r_owner_day = "";
    String r_pre_day = "";
    String approval = "";
    String w_name = "";
    String app_part = "";
    String case_to_note_1 = "";
    String case_to_note_2 = "";
    String case_to_note_3 = "";
    String step_no = "";
    String dev_step = "";
    String pjt_name = "";
    String pjt_no = "";
    String team = "";
    String f_name = "";
    String a_name = "";
    String request_txt = "";
    String car_type = "";
    String customer_F = "";
    String report_dest = "";
    String lme_cu = "";
    String u_ex_rate = "";
    String pack_type = "";
    String pro_1 = "";
    String eff_value = "";
    String actual_cost_sum_1 = "";
    String earn_rate_sum_1 = "";
    String actual_cost_sum_2 = "";
    String earn_rate_sum_2 = "";
    String actual_cost_sum_3 = "";
    String earn_rate_sum_3 = "";
    String m_depr_stan = "";
    String p_depr_stan = "";
    String L_depr_stan = "";
    String pack_depr_stan = "";
    String repack_depr_stan = "";
    String total_sales_1 = "";
    String total_sales_2 = "";
    String total_sales_3 = "";
    String total_sales_4 = "";
    String total_sales_5 = "";
    String total_sales_6 = "";
    String total_sales_7 = "";
    String total_sales_8 = "";
    String profit_1 = "";
    String profit_2 = "";
    String profit_3 = "";
    String profit_4 = "";
    String profit_5 = "";
    String profit_6 = "";
    String profit_7 = "";
    String profit_8 = "";
    String per_profit_1 = "";
    String per_profit_2 = "";
    String per_profit_3 = "";
    String per_profit_4 = "";
    String per_profit_5 = "";
    String per_profit_6 = "";
    String per_profit_7 = "";
    String per_profit_8 = "";
    String su_stan_day = "";
    String tocost_year = "";
    String file_1 = "";
    String FK_cost_request_s = "";
    String pass_state = "";
    String DB_case_2 = "";
    String client_cost_sum = "";
    String file_1_history = "";
    String sum_sales = "";
    String sum_profit = "";
    String sum_per_p = "";
    String AA, AB, AC = "";
    String ket_cost_value = "";
    Integer table_row_count = 0;
    String ket_cost_sum = "";
    String select_cost = "";

    chk_list = "";
    int j=0;
    int p = 0;
    CostReportCtl reportCtl = new CostReportCtl();
    ArrayList costReportList = null;
    Hashtable costReportItem = null;
    costReportList = reportCtl.costWorkReportViewList(report_pk);
    for(int i=0; i < costReportList.size(); i++){
        costReportItem = (Hashtable)costReportList.get(i);
        t_pk_cw[i][0] = (String)costReportItem.get("FK_cost_work_1")!=null?(String)costReportItem.get("FK_cost_work_1"):"";    // 산출작업DB_PK(1안)
        t_pk_cw[i][1] = (String)costReportItem.get("FK_cost_work_2")!=null?(String)costReportItem.get("FK_cost_work_2"):"";    // 산출작업DB_PK(2안)
        t_pk_cw[i][2] = (String)costReportItem.get("FK_cost_work_3")!=null?(String)costReportItem.get("FK_cost_work_3"):"";    // 산출작업DB_PK(3안)
        f_day = (String)costReportItem.get("f_day")!=null?(String)costReportItem.get("f_day"):"";
        Gr_day = (String)costReportItem.get("Gr_day")!=null?(String)costReportItem.get("Gr_day"):"";
        Leader_day = (String)costReportItem.get("Leader_day")!=null?(String)costReportItem.get("Leader_day"):"";
        Leader_name = (String)costReportItem.get("Leader_name")!=null?(String)costReportItem.get("Leader_name"):"";
        Gr_name = (String)costReportItem.get("Gr_name")!=null?(String)costReportItem.get("Gr_name"):"";
        JB_day = (String)costReportItem.get("JB_day")!=null?(String)costReportItem.get("JB_day"):"";
        JB_name = (String)costReportItem.get("JB_name")!=null?(String)costReportItem.get("JB_name"):"";
        p_leader_day = (String)costReportItem.get("p_leader_day")!=null?(String)costReportItem.get("p_leader_day"):"";
        p_leader_name = (String)costReportItem.get("p_leader_name")!=null?(String)costReportItem.get("p_leader_name"):"";
        r_owner_day = (String)costReportItem.get("r_owner_day")!=null?(String)costReportItem.get("r_owner_day"):"";
        r_pre_day = (String)costReportItem.get("r_pre_day")!=null?(String)costReportItem.get("r_pre_day"):"";
        approval = (String)costReportItem.get("approval")!=null?(String)costReportItem.get("approval"):"";
        w_name = (String)costReportItem.get("w_name")!=null?(String)costReportItem.get("w_name"):"";
        app_part = (String)costReportItem.get("app_part")!=null?(String)costReportItem.get("app_part"):"";
        case_1_note = (String)costReportItem.get("case_1_note")!=null?(String)costReportItem.get("case_1_note"):"";
        case_2_note = (String)costReportItem.get("case_2_note")!=null?(String)costReportItem.get("case_2_note"):"";
        case_3_note = (String)costReportItem.get("case_3_note")!=null?(String)costReportItem.get("case_3_note"):"";
        case_to_note_1 = (String)costReportItem.get("case_to_note_1")!=null?(String)costReportItem.get("case_to_note_1"):"";
        case_to_note_2 = (String)costReportItem.get("case_to_note_2")!=null?(String)costReportItem.get("case_to_note_2"):"";
        case_to_note_3 = (String)costReportItem.get("case_to_note_3")!=null?(String)costReportItem.get("case_to_note_3"):"";
        step_no = (String)costReportItem.get("step_no")!=null?(String)costReportItem.get("step_no"):"";
        pk_cr_group = (String)costReportItem.get("pk_cr_group")!=null?(String)costReportItem.get("pk_cr_group"):"";    // 요청서그룹번호
        FK_cost_request_s = (String)costReportItem.get("FK_cost_request")!=null?(String)costReportItem.get("FK_cost_request"):"";    // 요청서리스트
        dev_step = (String)costReportItem.get("dev_step")!=null?(String)costReportItem.get("dev_step"):"";    // 개발단계
        pjt_name = (String)costReportItem.get("pjt_name")!=null?(String)costReportItem.get("pjt_name"):"";    // ProjectName
        pjt_no = (String)costReportItem.get("pjt_no")!=null?(String)costReportItem.get("pjt_no"):"";    // ProjectNo
        part_name[i] = (String)costReportItem.get("part_name")!=null?(String)costReportItem.get("part_name"):"";    // PartName
        team = (String)costReportItem.get("team")!=null?(String)costReportItem.get("team"):"";    // 개발담당팀
        f_name = (String)costReportItem.get("f_name")!=null?(String)costReportItem.get("f_name"):"";    // 개발담당자
        a_name = (String)costReportItem.get("a_name")!=null?(String)costReportItem.get("a_name"):"";    // 영업담당자
        request_txt = (String)costReportItem.get("request_txt")!=null?(String)costReportItem.get("request_txt"):"";    // 요청목적
        car_type = (String)costReportItem.get("car_type")!=null?(String)costReportItem.get("car_type"):"";    // 적용차종
        customer_F = (String)costReportItem.get("customer_F")!=null?(String)costReportItem.get("customer_F"):"";    // 1차고객사
        su_year_1[i] = (String)costReportItem.get("su_year_1")!=null?(String)costReportItem.get("su_year_1"):"";    // 기획수량-1년차
        su_year_2[i] = (String)costReportItem.get("su_year_2")!=null?(String)costReportItem.get("su_year_2"):"";    // 기획수량-2년차
        su_year_3[i] = (String)costReportItem.get("su_year_3")!=null?(String)costReportItem.get("su_year_3"):"";    // 기획수량-3년차
        su_year_4[i] = (String)costReportItem.get("su_year_4")!=null?(String)costReportItem.get("su_year_4"):"";    // 기획수량-4년차
        su_year_5[i] = (String)costReportItem.get("su_year_5")!=null?(String)costReportItem.get("su_year_5"):"";    // 기획수량-5년차
        su_year_6[i] = (String)costReportItem.get("su_year_6")!=null?(String)costReportItem.get("su_year_6"):"";    // 기획수량-6년차
        su_year_7[i] = (String)costReportItem.get("su_year_7")!=null?(String)costReportItem.get("su_year_7"):"";
        su_year_8[i] = (String)costReportItem.get("su_year_8")!=null?(String)costReportItem.get("su_year_8"):"";
        report_dest = (String)costReportItem.get("report_dest")!=null?(String)costReportItem.get("report_dest"):"";
        client_cost[i] = (String)costReportItem.get("client_cost")!=null?(String)costReportItem.get("client_cost"):"";    // 고객사인정예상가
        room_earn[i] = (String)costReportItem.get("room_earn")!=null?(String)costReportItem.get("room_earn"):"";    // 룸폭
        pro_usage[i] = (String)costReportItem.get("pro_usage")!=null?(String)costReportItem.get("pro_usage"):"";    // 납입U/S
        ket_cost[i] = (String)costReportItem.get("ket_cost")!=null?(String)costReportItem.get("ket_cost"):"";    // 판매목표가
        target_cost[i] = (String)costReportItem.get("target_cost")!=null?(String)costReportItem.get("target_cost"):"";    // 목표수익율
        lme_cu = (String)costReportItem.get("lme_cu")!=null?(String)costReportItem.get("lme_cu"):"";
        u_ex_rate = (String)costReportItem.get("u_ex_rate")!=null?(String)costReportItem.get("u_ex_rate"):"";
        pack_type = (String)costReportItem.get("pack_type")!=null?(String)costReportItem.get("pack_type"):"";    // 포장유형
        usage[i] = (String)costReportItem.get("usage")!=null?(String)costReportItem.get("usage"):"";    // usage
        make_type[i] = (String)costReportItem.get("make_type")!=null?(String)costReportItem.get("make_type"):"";    // 생산처
        pro_1 = (String)costReportItem.get("pro_1")!=null?(String)costReportItem.get("pro_1"):"";    // 사내세부생산처
        eff_value = (String)costReportItem.get("eff_value")!=null?(String)costReportItem.get("eff_value"):"";    // 효율
        t_actual_cost[i][0] = (String)costReportItem.get("actual_cost_1")!=null?(String)costReportItem.get("actual_cost_1"):"";    // 총원가
        t_earn_rate[i][0] = (String)costReportItem.get("earn_rate_1")!=null?(String)costReportItem.get("earn_rate_1"):"";    // 수익율
        t_actual_cost[i][1] = (String)costReportItem.get("actual_cost_2")!=null?(String)costReportItem.get("actual_cost_2"):"";    // 총원가
        t_earn_rate[i][1] = (String)costReportItem.get("earn_rate_2")!=null?(String)costReportItem.get("earn_rate_2"):"";    // 수익율
        t_actual_cost[i][2] = (String)costReportItem.get("actual_cost_3")!=null?(String)costReportItem.get("actual_cost_3"):"";    // 총원가
        t_earn_rate[i][2] = (String)costReportItem.get("earn_rate_3")!=null?(String)costReportItem.get("earn_rate_3"):"";    // 수익율
        actual_cost_sum_1 = (String)costReportItem.get("actual_cost_sum_1")!=null?(String)costReportItem.get("actual_cost_sum_1"):"";
        earn_rate_sum_1 = (String)costReportItem.get("earn_rate_sum_1")!=null?(String)costReportItem.get("earn_rate_sum_1"):"";
        actual_cost_sum_2 = (String)costReportItem.get("actual_cost_sum_2")!=null?(String)costReportItem.get("actual_cost_sum_2"):"";
        earn_rate_sum_2 = (String)costReportItem.get("earn_rate_sum_2")!=null?(String)costReportItem.get("earn_rate_sum_2"):"";
        actual_cost_sum_3 = (String)costReportItem.get("actual_cost_sum_3")!=null?(String)costReportItem.get("actual_cost_sum_3"):"";
        earn_rate_sum_3 = (String)costReportItem.get("earn_rate_sum_3")!=null?(String)costReportItem.get("earn_rate_sum_3"):"";
        mold_count[0] = (String)costReportItem.get("mold_count")!=null?(String)costReportItem.get("mold_count"):"";    // Mold수량
        press_count[0] = (String)costReportItem.get("press_count")!=null?(String)costReportItem.get("press_count"):"";    // Press수량
        line_count[0] = (String)costReportItem.get("line_count")!=null?(String)costReportItem.get("line_count"):"";    // 수동조립설비
        pack_count[0] = (String)costReportItem.get("pack_count")!=null?(String)costReportItem.get("pack_count"):"";    // 포장금형
        repack_count[0] = (String)costReportItem.get("repack_count")!=null?(String)costReportItem.get("repack_count"):"";    // 회수용박스
        mold_total[0] = (String)costReportItem.get("mold_total_1")!=null?(String)costReportItem.get("mold_total_1"):"";    // Mold비
        press_total[0] = (String)costReportItem.get("press_total_1")!=null?(String)costReportItem.get("press_total_1"):"";    // Press비
        line_total[0] = (String)costReportItem.get("line_total_1")!=null?(String)costReportItem.get("line_total_1"):"";    // 수동조립설비비
        pack_total[0] = (String)costReportItem.get("pack_total_1")!=null?(String)costReportItem.get("pack_total_1"):"";    // 포장금형비
        mold_total[1] = (String)costReportItem.get("mold_total_2")!=null?(String)costReportItem.get("mold_total_2"):"";    // Mold비
        press_total[1] = (String)costReportItem.get("press_total_2")!=null?(String)costReportItem.get("press_total_2"):"";    // Press비
        line_total[1] = (String)costReportItem.get("line_total_2")!=null?(String)costReportItem.get("line_total_2"):"";    // 수동조립설비비
        pack_total[1] = (String)costReportItem.get("pack_total_2")!=null?(String)costReportItem.get("pack_total_2"):"";    // 포장금형비
        mold_total[2] = (String)costReportItem.get("mold_total_3")!=null?(String)costReportItem.get("mold_total_3"):"";    // Mold비
        press_total[2] = (String)costReportItem.get("press_total_3")!=null?(String)costReportItem.get("press_total_3"):"";    // Press비
        line_total[2] = (String)costReportItem.get("line_total_3")!=null?(String)costReportItem.get("line_total_3"):"";    // 수동조립설비비
        pack_total[2] = (String)costReportItem.get("pack_total_3")!=null?(String)costReportItem.get("pack_total_3"):"";    // 포장금형비
        repack_total[0] = (String)costReportItem.get("repack_total_1")!=null?(String)costReportItem.get("repack_total_1"):"";    // 회수용박스비
        repack_total[1] = (String)costReportItem.get("repack_total_2")!=null?(String)costReportItem.get("repack_total_2"):"";    // 회수용박스비
        repack_total[2] = (String)costReportItem.get("repack_total_3")!=null?(String)costReportItem.get("repack_total_3"):"";    // 회수용박스비
        m_depr_stan = (String)costReportItem.get("m_depr_stan")!=null?(String)costReportItem.get("m_depr_stan"):"";    // Mold상각기준
        p_depr_stan = (String)costReportItem.get("p_depr_stan")!=null?(String)costReportItem.get("p_depr_stan"):"";    // Press상각기준
        L_depr_stan = (String)costReportItem.get("L_depr_stan")!=null?(String)costReportItem.get("L_depr_stan"):"";    // 수동조립설비상각기준
        pack_depr_stan = (String)costReportItem.get("pack_depr_stan")!=null?(String)costReportItem.get("pack_depr_stan"):"";    // 포장금형상각기준
        repack_depr_stan = (String)costReportItem.get("repack_depr_stan")!=null?(String)costReportItem.get("repack_depr_stan"):"";    // 회수용박스상각기준
        total_sales_1 = (String)costReportItem.get("total_sales_1")!=null?(String)costReportItem.get("total_sales_1"):"";    // 매출액-1년차
        total_sales_2 = (String)costReportItem.get("total_sales_2")!=null?(String)costReportItem.get("total_sales_2"):"";    // 매출액-2년차
        total_sales_3 = (String)costReportItem.get("total_sales_3")!=null?(String)costReportItem.get("total_sales_3"):"";    // 매출액-3년차
        total_sales_4 = (String)costReportItem.get("total_sales_4")!=null?(String)costReportItem.get("total_sales_4"):"";    // 매출액-4년차
        total_sales_5 = (String)costReportItem.get("total_sales_5")!=null?(String)costReportItem.get("total_sales_5"):"";    // 매출액-5년차
        total_sales_6 = (String)costReportItem.get("total_sales_6")!=null?(String)costReportItem.get("total_sales_6"):"";    // 매출액-6년차
        total_sales_7 = (String)costReportItem.get("total_sales_7")!=null?(String)costReportItem.get("total_sales_7"):"";    // 매출액-7년차
        total_sales_8 = (String)costReportItem.get("total_sales_8")!=null?(String)costReportItem.get("total_sales_8"):"";    // 매출액-8년차
        profit_1 = (String)costReportItem.get("profit_1")!=null?(String)costReportItem.get("profit_1"):"";    // 영업이익-1년차
        profit_2 = (String)costReportItem.get("profit_2")!=null?(String)costReportItem.get("profit_2"):"";    // 영업이익-2년차
        profit_3 = (String)costReportItem.get("profit_3")!=null?(String)costReportItem.get("profit_3"):"";    // 영업이익-3년차
        profit_4 = (String)costReportItem.get("profit_4")!=null?(String)costReportItem.get("profit_4"):"";    // 영업이익-4년차
        profit_5 = (String)costReportItem.get("profit_5")!=null?(String)costReportItem.get("profit_5"):"";    // 영업이익-5년차
        profit_6 = (String)costReportItem.get("profit_6")!=null?(String)costReportItem.get("profit_6"):"";    // 영업이익-6년차
        profit_7 = (String)costReportItem.get("profit_7")!=null?(String)costReportItem.get("profit_7"):"";    // 영업이익-7년차
        profit_8 = (String)costReportItem.get("profit_8")!=null?(String)costReportItem.get("profit_8"):"";    // 영업이익-8년차
        per_profit_1 = (String)costReportItem.get("per_profit_1")!=null?(String)costReportItem.get("per_profit_1"):"";    // 영업이익율-1년차
        per_profit_2 = (String)costReportItem.get("per_profit_2")!=null?(String)costReportItem.get("per_profit_2"):"";    // 영업이익율-2년차
        per_profit_3 = (String)costReportItem.get("per_profit_3")!=null?(String)costReportItem.get("per_profit_3"):"";    // 영업이익율-3년차
        per_profit_4 = (String)costReportItem.get("per_profit_4")!=null?(String)costReportItem.get("per_profit_4"):"";    // 영업이익율-4년차
        per_profit_5 = (String)costReportItem.get("per_profit_5")!=null?(String)costReportItem.get("per_profit_5"):"";    // 영업이익율-5년차
        per_profit_6 = (String)costReportItem.get("per_profit_6")!=null?(String)costReportItem.get("per_profit_6"):"";    // 영업이익율-6년차
        per_profit_7 = (String)costReportItem.get("per_profit_7")!=null?(String)costReportItem.get("per_profit_7"):"";    // 영업이익율-7년차
        per_profit_8 = (String)costReportItem.get("per_profit_8")!=null?(String)costReportItem.get("per_profit_8"):"";    // 영업이익율-8년차
        su_stan_day = (String)costReportItem.get("su_stan_day")!=null?(String)costReportItem.get("su_stan_day"):"";    // 수지재료단가
        note_1 = (String)costReportItem.get("note_1")!=null?(String)costReportItem.get("note_1"):"";    // 2.비고
        note_2 = (String)costReportItem.get("note_2")!=null?(String)costReportItem.get("note_2"):"";    // 3.비고
        note_3 = (String)costReportItem.get("note_3")!=null?(String)costReportItem.get("note_3"):"";    // 4.비고
        note_4 = (String)costReportItem.get("note_4")!=null?(String)costReportItem.get("note_4"):"";    // 5.비고
        note_5 = (String)costReportItem.get("note_5")!=null?(String)costReportItem.get("note_5"):"";    // 보고서2번page검토조건및의견란
        note_5_1 = (String)costReportItem.get("note_5_1")!=null?(String)costReportItem.get("note_5_1"):"";    // 보고서2번page검토조건및의견란
        note_6 = (String)costReportItem.get("note_6")!=null?(String)costReportItem.get("note_6"):"";    // 사장님결재의견
        tocost_year = (String)costReportItem.get("tocost_year")!=null?(String)costReportItem.get("tocost_year"):"";
        file_1 = (String)costReportItem.get("file_1")!=null?(String)costReportItem.get("file_1"):"";
        select_cost = (String)costReportItem.get("select_cost")!=null?(String)costReportItem.get("select_cost"):"";

        if(!"".equals(note_1)){
            note_1 = note_1.replaceAll("\r\n", "<br>&nbsp;");
        }
        if(!"".equals(note_2)){
            note_2 = note_2.replaceAll("\r\n", "<br>&nbsp;");
        }
        if(!"".equals(note_3)){
            note_3 = note_3.replaceAll("\r\n", "<br>&nbsp;");
        }
        if(!"".equals(note_4)){
            note_4 = note_4.replaceAll("\r\n", "<br>&nbsp;");
        }

        if(!"".equals(t_pk_cw[i][0])){
            if(!"".equals(chk_list)){
                chk_list = chk_list + "," + t_pk_cw[i][0];
            }else{
                chk_list = chk_list;
            }
        }
        if(!"".equals(t_pk_cw[i][1])){
            if(!"".equals(chk_list)){
                chk_list = chk_list + "," + t_pk_cw[i][1];
            }else{
                chk_list = chk_list;
            }
            DB_case_2 = "ok";
        }
        if(!"".equals(t_pk_cw[i][2])){
            if(!"".equals(chk_list)){
                chk_list = chk_list + "," + t_pk_cw[i][2];
            }else{
                chk_list = chk_list;
            }
        }
        if(!"".equals(client_cost[i])){
            if(!"".equals(client_cost_sum)){
                client_cost_sum = Double.toString(StringUtil.nullDouble(client_cost_sum) + StringUtil.nullDouble(client_cost[i]));
            }else{
                client_cost_sum = client_cost[i];
            }
        }

        if(!"0".equals(su_year_1[i])){
            su_year_1[i] = Double.toString(StringUtil.nullDouble(su_year_1[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_1[i] = "0";
        }
        if(!"0".equals(su_year_2[i])){
            su_year_2[i] = Double.toString(StringUtil.nullDouble(su_year_2[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_2[i] = "0";
        }
        if(!"0".equals(su_year_3[i])){
            su_year_3[i] = Double.toString(StringUtil.nullDouble(su_year_3[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_3[i] = "0";
        }
        if(!"0".equals(su_year_4[i])){
            su_year_4[i] = Double.toString(StringUtil.nullDouble(su_year_4[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_4[i] = "0";
        }
        if(!"0".equals(su_year_5[i])){
            su_year_5[i] = Double.toString(StringUtil.nullDouble(su_year_5[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_5[i] = "0";
        }
        if(!"0".equals(su_year_6[i])){
            su_year_6[i] = Double.toString(StringUtil.nullDouble(su_year_6[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_6[i] = "0";
        }
        if(!"0".equals(su_year_7[i])){
            su_year_7[i] = Double.toString(StringUtil.nullDouble(su_year_7[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_7[i] = "0";
        }
        if(!"0".equals(su_year_8[i])){
            su_year_8[i] = Double.toString(StringUtil.nullDouble(su_year_8[i]) * StringUtil.nullDouble(usage[i]));
            a_su_count[i][p] = Double.toString(StringUtil.nullDouble(a_su_count[i][p]) + 1);
        }else{
            su_year_8[i] = "0";
        }
        avg_su[i] = Double.toString((StringUtil.nullDouble(su_year_1[i]) + StringUtil.nullDouble(su_year_2[i]) + StringUtil.nullDouble(su_year_3[i]) + StringUtil.nullDouble(su_year_4[i]) + StringUtil.nullDouble(su_year_5[i]) + StringUtil.nullDouble(su_year_6[i])) + StringUtil.nullDouble(su_year_7[i]) + StringUtil.nullDouble(su_year_8[i]));

        if(StringUtil.nullDouble(avg_su[i]) == 0){
            avg_su[i] = "";
        }
    }
    file_1_history = file_1;

    sum_sales = Double.toString(StringUtil.nullDouble(total_sales_1) + StringUtil.nullDouble(total_sales_2) + StringUtil.nullDouble(total_sales_3) + StringUtil.nullDouble(total_sales_4) + StringUtil.nullDouble(total_sales_5) + StringUtil.nullDouble(total_sales_6) + StringUtil.nullDouble(total_sales_7) + StringUtil.nullDouble(total_sales_8));
    sum_profit = Double.toString(StringUtil.nullDouble(profit_1) + StringUtil.nullDouble(profit_2) + StringUtil.nullDouble(profit_3) + StringUtil.nullDouble(profit_4) + StringUtil.nullDouble(profit_5) + StringUtil.nullDouble(profit_6) + StringUtil.nullDouble(profit_7) + StringUtil.nullDouble(profit_8));
    if(StringUtil.nullDouble(a_su_count[0][0]) > 0){
        //sum_per_p = Double.toString((StringUtil.nullDouble(per_profit_1) + StringUtil.nullDouble(per_profit_2) + StringUtil.nullDouble(per_profit_3) + StringUtil.nullDouble(per_profit_4) + StringUtil.nullDouble(per_profit_5) + StringUtil.nullDouble(per_profit_6) + StringUtil.nullDouble(per_profit_7) + StringUtil.nullDouble(per_profit_8)) / StringUtil.nullDouble(a_su_count[0][0]));
        sum_per_p = Double.toString(StringUtil.nullDouble(sum_profit)/StringUtil.nullDouble(sum_sales));
    }
    // 투자비합계 1안
    if(!"".equals(mold_total[0])){
        mold_total[0] = mold_total[0];
    }else{
        mold_total[0] = "0";
    }
    if(!"".equals(press_total[0])){
        press_total[0] = press_total[0];
    }else{
        press_total[0] = "0";
    }
    if(!"".equals(line_total[0])){
        line_total[0] = line_total[0];
    }else{
        line_total[0] = "0";
    }
    if(!"".equals(pack_total[0])){
        pack_total[0] = pack_total[0];
    }else{
        pack_total[0] = "0";
    }
    if(!"".equals(repack_total[0])){
        repack_total[0] = repack_total[0];
    }else{
        repack_total[0] = "0";
    }
    AA = Double.toString((StringUtil.nullDouble(mold_total[0]) * 1000) + (StringUtil.nullDouble(press_total[0]) * 1000) + (StringUtil.nullDouble(line_total[0]) * 1000) + (StringUtil.nullDouble(pack_total[0]) * 1000) + (StringUtil.nullDouble(repack_total[0]) * 1000));
    AA = Double.toString(StringUtil.nullDouble(AA) / 1000);
    // 투자비합계 2안
    if(!"".equals(mold_total[1])){
        mold_total[1] = mold_total[1];
    }else{
        mold_total[1] = "0";
    }
    if(!"".equals(press_total[1])){
        press_total[1] = press_total[1];
    }else{
        press_total[1] = "0";
    }
    if(!"".equals(line_total[1])){
        line_total[1] = line_total[1];
    }else{
        line_total[1] = "0";
    }
    if(!"".equals(pack_total[1])){
        pack_total[1] = pack_total[1];
    }else{
        pack_total[1] = "0";
    }
    if(!"".equals(repack_total[1])){
        repack_total[1] = repack_total[1];
    }else{
        repack_total[1] = "0";
    }
    AB = Double.toString((StringUtil.nullDouble(mold_total[1]) * 1000) + (StringUtil.nullDouble(press_total[1]) * 1000) + (StringUtil.nullDouble(line_total[1]) * 1000) + (StringUtil.nullDouble(pack_total[1]) * 1000) + (StringUtil.nullDouble(repack_total[1]) * 1000));
    AB = Double.toString(StringUtil.nullDouble(AB) / 1000);
    // 투자비합계 3안
    if(!"".equals(mold_total[2])){
        mold_total[2] = mold_total[2];
    }else{
        mold_total[2] = "0";
    }
    if(!"".equals(press_total[2])){
        press_total[2] = press_total[2];
    }else{
        press_total[2] = "0";
    }
    if(!"".equals(line_total[2])){
        line_total[2] = line_total[2];
    }else{
        line_total[2] = "0";
    }
    if(!"".equals(pack_total[2])){
        pack_total[2] = pack_total[2];
    }else{
        pack_total[2] = "0";
    }
    if(!"".equals(repack_total[2])){
        repack_total[2] = repack_total[2];
    }else{
        repack_total[2] = "0";
    }
    AC = Double.toString((StringUtil.nullDouble(mold_total[2]) * 1000) + (StringUtil.nullDouble(press_total[2]) * 1000) + (StringUtil.nullDouble(line_total[2]) * 1000) + (StringUtil.nullDouble(pack_total[2]) * 1000) + (StringUtil.nullDouble(repack_total[2]) * 1000));
    AC = Double.toString(StringUtil.nullDouble(AC) / 1000);

    pk_cw_array = chk_list.replaceFirst(",", "").split(",");
    int P_count = pk_cw_array.length;
    if(!"".equals(chk_list)){
        for(int m=0;m<P_count;m++){
            if(!"".equals(StringUtil.checkNull(reportCtl.ketCostList(pk_cw_array[m])))){
                ket_cost_value = "ok";
            }else{
                ket_cost_value = "no";
            }
        }
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>개발원가 결과보고</title>
<style type="text/css">
<!--
.style18 {font-size: 24px; font-weight: bold;color:#4F4F4F;text-align:center;}
.style24 {font-size: 18px;font-weight:bold;text-align:center; color:#FF0000; font-family:"돋움"}
-->
</style>
</head>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/report_work.js"></script>
<style type="text/css">
<!--
BODY
A:link    { text-decoration:none; color:#8A8A8A}
A:visited { text-decoration:underline; color:#5E5E5E}
A:active  { text-decoration:none; color:#5E5E5E}
A:hover   { text-decoration:underline; color:#8A8A8A}

A.txt:link    { text-decoration:none; color:#3F6FA3}
A.txt:visited { text-decoration:none; color:#3F6FA3}
A.txt:active  { text-decoration:none; color:#3F6FA3}
A.txt:hover   { text-decoration:none; color:#8A8A8A}

.redBorder {
border-top: 2px solid red;
border-left: 2px solid red;
border-right: 2px solid red;
}

.redBorder_left {
border-left: 2px solid red;
}

.redBorder_right {
border-right: 2px solid red;
}

.redBorder_left_bottom {
border-left: 2px solid red;
border-bottom: 2px solid red;
}

.redBorder_right_bottom {
border-right: 2px solid red;
border-bottom: 2px solid red;
}

.style1 {font-size: 12px;text-align:center}
.style2 {font-size: 12px; font-weight: bold;color:#FF9900; text-align:left}
.style3 {font-size: 12px; font-weight: bold;color:#4F4F4F;}
.style4 {font-size: 12px; background-color:#D2D2D2; text-align: center; color:#FFFFFF}
.style5 {font-size: 12px; background-color:#D2D2D2; text-align: center; font-weight: bold; color:#333333}
.style6 {font-size: 12px;font-weight: bold; text-align:right;color:#3F6FA3}
.style7 {font-size: 11px}
.style8 {font-size: 12px;text-align:right}
.style9 {font-size: 12px;text-align:left}
.style10 {font-size: 29px; font-weight: bold;color:#4F4F4F;text-align:center;}
.style11 {font-size: 12px;text-align:right; font-weight: bold;color:#FF0033}
.style12 {font-size: 11px;text-align:left}
.style13 {font-size: 12px;font-weight: bold; text-align:right; color:#0000FF}
.style14 {font-size: 12px;text-align:right; font-weight: bold;}
.style15 {font-size: 14px;font-weight:bold;text-align:center; color:#66A014}
.style16 {font-size: 12px;text-align:center; color:#8A8A8A}
.style17 {font-size: 12px;font-weight: bold; text-align:right}
.style18 {font-size: 22px;font-weight:bold;text-align:center; color:#FF0000; font-family:"돋움"}
.style19 {font-size: 14px; font-weight: bold;color:#4F4F4F;text-align:center;}
.style20 {font-size: 10px;text-align: center;}
.style21 {font-size: 12px; background-color:#FFFFFF; text-align: center; font-weight: bold; color:#333333}
.style22 {font-size: 14px;text-align:center}
.style23 {font-size: 12px;text-align:center; color:#0000FF}

.line_name {font-size: 4px; color:#EFEFEF; text-align:center}
#bg_color {background:#E6E6E6}
#not_Data {background:#FFDFDF}
#font_co {color:#CCCCCC}

-->
</style>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/dp_picker.css">
<link type="text/css" rel="stylesheet" href="/plm/jsp/cost/css/date_picker.css">
<OBJECT ID="IEPrint" WIDTH="1" HEIGHT="1" CLASSID="CLSID:F290B058-CB26-460E-B3D4-8F36AEEDBE44" ></OBJECT>
<script language="javascript">

function print_page(){
	var select_cost = "<%=select_cost%>";
    select_cost_gijun(select_cost);

    IEPrint.left = 15;
    IEPrint.right = 10;
    IEPrint.top = 5;
    IEPrint.bottom = 1;
    IEPrint.header = "";
    IEPrint.footer = "";
    IEPrint.printbg = true;
    IEPrint.landscape = true;
    IEPrint.paper = "A4";
    IEPrint.Preview();
}
</script>
<body onLoad="print_page(); close();">
<Form method="post" name="part_1" >
    <input name="approval" 		type="hidden" value="<%//=approval%>">
    <input name="step_no" 		type="hidden" value="<%=step_no%>">
    <input name="note_5" 		type="hidden" value="<%=note_5%>">
    <input name="note_5_1" 		type="hidden" value="<%=note_5_1%>">
    <input name="t_note_1" 		type="hidden" value="<%//=t_note_1%>">
    <input name="t_note_2" 		type="hidden" value="<%//=t_note_2%>">
    <input name="t_note_3" 		type="hidden" value="<%//=t_note_3%>">
    <input name="t_note_4" 		type="hidden" value="<%//=t_note_4%>">
    <input name="report_pk" 		type="hidden" value="<%=report_pk%>">
    <input name="DB_case_2" 	type="hidden" value="<%=DB_case_2%>">
    <input name="select_name" type="hidden" value="<%=select_name%>">
    <table width="957" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <table width="957" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="953" height="50" colspan="3" class="style8">
                            <table width="955" border="0" cellpadding="0" cellspacing="1" bgcolor="#999999">
                                <tr>
                                    <td bgcolor="#FFFFFF">
                                        <table width="957" border="0" align="center" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td height="5" colspan="4" class="style1">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td width="142" height="90">
                                                    <table width="142" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                        <tr>
                                                            <td height="18" colspan="2" class="style5">검 토 부 서</td>
                                                        </tr>
                                                        <tr>
                                                            <td width="77" height="18" bgcolor="#FFFFFF" class="style1">
                                                            <%
                                                            out.println(StringUtil.ChangeDeptNoCPString(team, ""));
                                                            /*
                                                            if("1".equals(team)){
																out.println("커넥터연구개발1팀");
															}else if("11".equals(team)){
																out.println("커넥터연구개발2팀");
															}else if("12".equals(team)){
																out.println("커넥터연구개발3팀");
															}else if("13".equals(team)){
																out.println("커넥터연구개발센타");
															}else if("22".equals(team)){
																out.println("전장모듈연구개발1팀");
															}else if("23".equals(team)){
																out.println("전장모듈연구개발2팀");
															}else if("24".equals(team)){
																out.println("전장모듈연구개발3팀");
															}else if("3".equals(team)){
																out.println("연구개발3팀");
															}else if("4".equals(team)){
																out.println("연구개발4팀");
															}else if("6".equals(team)){
																out.println("연구개발6팀");
															}else if("21".equals(team)){
																out.println("시작개발1팀");
															}*/
                                                            %>
                                                            </td>
                                                            <td width="62" height="18" bgcolor="#FFFFFF" class="style1"><%=f_name %></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">자동차영업팀</td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=a_name %></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="300" class="style10">
                                                    <table width="295" border="0" align="right" cellpadding="0" cellspacing="1">
                                                        <tr>
                                                            <td  width="293" class="style10">개발 원가 결과 보고</td>
                                                        </tr>
                                                        <tr>
                                                            <td height="7"></td>
                                                          </tr>
                                                        <tr>
                                                            <td class="style19"><%if("개발착수".equals(dev_step)){out.println("■ 개발착수/진행중");}else{out.println("■ "+dev_step);} %></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="72">
                                                    <table width="60" border="1" cellspacing="0" cellpadding="0" bordercolor="#FF0000">
                                                        <tr>
                                                            <td width="60" height="50" class="style24">
                                                                <table width="60" border="0" cellspacing="1" cellpadding="0">
                                                                    <tr>
                                                                        <td class="style24">사외비</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="style24">통제본</td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="443" >

                                                <%if(!step_no.equals("") && Integer.parseInt(step_no.substring(0,1)) > 4){ %>
                                                        <table width="325" height="70" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333" align="right">
                                                        <tr>
                                                            <td width="26" rowspan="2" class="style5">결<br>
                                                            <br> 재</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">담당</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">팀장</td>
                                                              <td width="58" bgcolor="#FFFFFF" class="style1">센타장</td>
                                                              <td width="58" bgcolor="#FFFFFF" class="style1">연구원장</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">사장</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">회장</td>
                                                        </tr>
                                                        <tr>
                                                            <td height="54" bgcolor="#FFFFFF" class="style1">
                                                                <%
                                                                if(step_no.equals("2")){
                                                                    out.println("");
                                                                }else{
                                                                    if(!StringUtil.checkNull(f_day).equals("")){
                                                                        out.println(w_name+"<br>"+f_day.substring(0,4)+"<br>"+f_day.substring(5,10));
                                                                    }
                                                                }
                                                                %>
                                                            </td>
                                                            <td height="54" bgcolor="#FFFFFF" class="style1">
                                                                <%if(step_no.equals("2") ||  step_no.equals("3")){
                                                                    out.println("");
                                                                }else{
                                                                    if(!StringUtil.checkNull(Gr_day).equals("")){
                                                                        out.println(Gr_name+"<br>"+Gr_day.substring(0,4)+"<br>"+Gr_day.substring(5,10));
                                                                    }
                                                                }
                                                                %>
                                                            </td>
                                                            <td bgcolor="#FFFFFF" class="style1">
                                                                <%if(step_no.equals("2") ||  step_no.equals("3")  ||  step_no.equals("4") ){
                                                                    out.println("");
                                                                }else{
                                                                    if(!StringUtil.checkNull(Leader_day).equals("")){
                                                                        out.println(Leader_name+"<br>"+Leader_day.substring(0,4)+"<br>"+Leader_day.substring(5,10));
                                                                    }
                                                                }
                                                                %>
                                                            </td>
                                                              <td width="58" bgcolor="#FFFFFF" class="style1" <% if(pass_state.equals("센타장전결") ||  pass_state.equals("본부장전결")){%>background="/plm/jsp/cost/acc_img/line.jpg" <% }%>>
                                                              <%
                                                                  if(step_no.equals("2") ||  step_no.equals("3")  ||  step_no.equals("4") || step_no.equals("5")){
                                                                    out.println("");
                                                                }else{
                                                                    if(!StringUtil.checkNull(p_leader_day).equals("")){
                                                                        out.println(p_leader_name+"<br>"+p_leader_day.substring(0,4)+"<br>"+p_leader_day.substring(5,10));
                                                                    }
                                                                }
                                                            %>
                                                            </td>
                                                              <td width="58" height="54" bgcolor="#FFFFFF" class="style1" <% if(pass_state.equals("센타장전결") ||  pass_state.equals("본부장전결")){%>background="/plm/jsp/cost/acc_img/line.jpg" <% }%>>
                                                              <%
                                                                  if(step_no.equals("2") ||  step_no.equals("3")  ||  step_no.equals("4") || step_no.equals("5")){
                                                                    out.println("");
                                                                }else{
                                                                    if(!StringUtil.checkNull(r_owner_day).equals("")){
                                                                        out.println("이원준<br>"+r_owner_day.substring(0,4)+"<br>"+r_owner_day.substring(5,10));
                                                                    }
                                                                }
                                                            %>
                                                            </td>
                                                              <td width="58" height="54" bgcolor="#FFFFFF" class="style1" <% if(r_pre_day.equals("") && (pass_state.equals("센타장전결") ||  pass_state.equals("본부장전결") || dev_step.equals("개발검토"))){ %>background="/plm/jsp/cost/acc_img/line.jpg" <%}%>>
                                                              <%
                                                                  if(!StringUtil.checkNull(r_pre_day).equals("")){
                                                                      out.println("이창원<br>"+r_pre_day.substring(0,4)+"<br>"+r_pre_day.substring(5,10));
                                                                  }else{
                                                                      out.println("");
                                                                  }
                                                            %>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <%}else{ %>

                                                            <table width="383" height="73" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333" align="right">
                                                            <tr>
                                                              <td width="26" rowspan="3" class="style5">결<br>
                                                                  <br>
                                                                재</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">담당</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">팀장</td>
                                                              <td width="58" bgcolor="#FFFFFF" class="style1">센타장</td>
                                                              <td width="58" bgcolor="#FFFFFF" class="style1">연구원장</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">사장</td>
                                                              <td width="58" height="18" bgcolor="#FFFFFF" class="style1">회장</td>
                                                            </tr>
                                                            <tr>
                                                              <td height="37" bgcolor="#FFFFFF" class="style1">
                                                              <%
                                                                  if(step_no.equals("2")){
                                                                      out.println("");
                                                                  }else{
                                                                      out.println(w_name);
                                                                  }
                                                              %>
                                                              </td>
                                                              <td height="37" bgcolor="#FFFFFF" class="style1">
                                                              <%
                                                                  if(step_no.equals("2") || step_no.equals("3")){
                                                                      out.println("");
                                                                  }else{
                                                                      out.println(Gr_name);
                                                                  }
                                                              %>
                                                              </td>
                                                              <td bgcolor="#FFFFFF" class="style1">
                                                              <%
                                                                  if(step_no.equals("2") || step_no.equals("3") || step_no.equals("4")){
                                                                      out.println("");
                                                                  }else{
                                                                      out.println(Leader_name);
                                                                  }
                                                              %>
                                                              </td>
                                                            <td width="58" bgcolor="#FFFFFF" class="style1" <% if(pass_state.equals("센타장전결") ||  pass_state.equals("본부장전결")){ %>background="/plm/jsp/cost/acc_img/line_1.jpg" <% }%>>&nbsp;</td>
                                                            <td width="58" height="37" bgcolor="#FFFFFF" class="style1" <% if(pass_state.equals("센타장전결") ||  pass_state.equals("본부장전결")){ %>background="/plm/jsp/cost/acc_img/line_1.jpg" <% }%>>&nbsp;</td>
                                                            <td width="58" height="37" bgcolor="#FFFFFF" class="style1" <% if(StringUtil.checkNull(r_pre_day).equals("") && (pass_state.equals("센타장전결") ||  pass_state.equals("본부장전결") || dev_step.equals("개발검토"))){ %>background="/plm/jsp/cost/acc_img/line_1.jpg" <% }%>>&nbsp;</td>
                                                        </tr>
                                                        <tr>
                                                          <td height="18" bgcolor="#FFFFFF" class="style20">
                                                          <%
                                                              if(step_no.equals("2")){
                                                                  out.println("");
                                                              }else{
                                                                  out.println(f_day.substring(0,4)+"<br>"+f_day.substring(5,10));
                                                              }
                                                          %>
                                                          </td>
                                                          <td height="18" bgcolor="#FFFFFF" class="style20">
                                                          <%
                                                              if(step_no.equals("2") || step_no.equals("3")){
                                                                  out.println("");
                                                              }else{
                                                                  out.println(Gr_day.substring(0,4)+"<br>"+Gr_day.substring(5,10));
                                                              }
                                                          %>
                                                          </td>
                                                          <td bgcolor="#FFFFFF" class="style20">
                                                          <%
                                                              if(step_no.equals("2") || step_no.equals("3") || step_no.equals("4")){
                                                                  out.println("");
                                                              }else{
                                                                  out.println(Leader_day.substring(0,4)+"<br>"+Leader_day.substring(5,10));
                                                              }
                                                          %>
                                                          </td>
                                                          <td width="58" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                          <td width="58" height="18" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                          <td width="58" height="18" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                        </tr>
                                                      </table>
                                                <%} %>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td height="197" colspan="4">
                                                    <table width="953" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
                                                        <tr>
                                                            <td height="18" colspan="7" valign="bottom" bgcolor="#FFFFFF" class="style3">1. 개발배경 및 투자비 현황</td>
                                                            <td width="128" height="18" valign="bottom" bgcolor="#FFFFFF" class="style6">작성일 :
                                                                    <%
                                                                    if(!StringUtil.checkNull(f_day).equals("")){
                                                                        out.println(f_day.substring(0,10));
                                                                    }
                                                                    %>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table width="955" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                        <tr>
                                                            <td width="83" height="21" rowspan="2" class="style5">고객사명</td>
                                                            <td width="273" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=customer_F%></FONT></strong></td>
                                                            <td width="72" rowspan="8" class="style5">신규 투자비<br />검 토 현 황<br /></td>
                                                            <td width="84" height="21" rowspan="2" class="style5">구분</td>
                                                            <td width="54" height="21" rowspan="2" class="style5">수량</td>
                                                            <td width="80" height="21" class="style5">1안</td>
                                                            <td width="80" height="21" class="style5">2안</td>
                                                            <td width="80" height="21" class="style5">3안</td>
                                                            <td width="79" height="21" rowspan="2" class="style5">상각기준</td>
                                                            <td width="59" rowspan="7" class="style5">투자비<br/>회수기간</td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" class="style5"><%=case_to_note_1%></td>
                                                            <td width="80" height="18" class="style5"><%=case_to_note_2%></td>
                                                            <td width="80" height="18" class="style5"><%=case_to_note_3%></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" class="style5">제품명</td>
                                                            <td width="273" height="18" bgcolor="#FFFFFF" class="style1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=pjt_name%></FONT></strong></td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">Mold</td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=mold_count[0]%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(mold_total[0], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(mold_total[1], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(mold_total[2], "천원", 0, 0)%></td>
                                                            <td width="79" height="18" bgcolor="#FFFFFF" class="style1"><%=m_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" class="style5">Project No. </td>
                                                            <td width="273" height="18" bgcolor="#FFFFFF" class="style1"><%=pjt_no%></td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">Press</td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=press_count[0]%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(press_total[0], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(press_total[1], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(press_total[2], "천원", 0, 0)%></td>
                                                            <td width="79" height="18" bgcolor="#FFFFFF" class="style1"><%=p_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" class="style5">적용차종</td>
                                                            <td width="273" height="18" bgcolor="#FFFFFF" class="style1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=car_type%></FONT></strong></td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">조립설비 </td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=line_count[0]%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(line_total[0], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(line_total[1], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(line_total[2], "천원", 0, 0)%></td>
                                                            <td width="79" height="18" bgcolor="#FFFFFF" class="style1"><%=L_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" class="style5">적용부위</td>
                                                            <td width="273" height="18" bgcolor="#FFFFFF" class="style1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=app_part%></FONT></strong></td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">기타 투자비 </td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=pack_count[0]%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(pack_total[0], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(pack_total[1], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(pack_total[2], "천원", 0, 0)%></td>
                                                            <td width="79" height="18" bgcolor="#FFFFFF" class="style1"><%=pack_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" class="style5">물류흐름</td>
                                                            <td width="273" height="18" bgcolor="#FFFFFF" class="style1"><%=report_dest%></td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">포장용 투자비</td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=repack_count[0]%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(repack_total[0], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(repack_total[1], "천원", 0, 0)%></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(repack_total[2], "천원", 0, 0)%></td>
                                                            <td width="79" height="18" bgcolor="#FFFFFF" class="style1"><%=repack_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" class="style5">검토목적</td>
                                                            <td width="273" height="18" bgcolor="#FFFFFF" class="style1"><%=request_txt%></td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">합 계</td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(AA, "천원", 0, 0)%></FONT></strong></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(AB, "천원", 0, 0)%></FONT></strong></td>
                                                            <td width="80" height="18" bgcolor="#FFFFFF" class="style1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(AC, "천원", 0, 0)%></FONT></strong></td>
                                                            <td width="79" height="18" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                            <td width="59" height="18" bgcolor="#FFFFFF" class="style21"><%=tocost_year%>  년</td>
                                                        </tr>
                                                    </table>
                                                    <table width="906" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
                                                        <tr>
                                                            <td height="18" colspan="13" valign="bottom" class="style3">2. 예상 손익 및 환율, 원재료 가격 기준 (1안기준)</td>
                                                        </tr>
                                                    </table>
                                                    <table width="955" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                        <tr>
                                                            <td width="110" height="18" class="style5">구분</td>
                                                            <td width="43" height="18" class="style5">1년차</td>
                                                            <td width="43" height="18" class="style5">2년차</td>
                                                            <td width="43" height="18" class="style5">3년차</td>
                                                            <td width="43" height="18" class="style5">4년차</td>
                                                            <td width="43" height="18" class="style5">5년차</td>
                                                            <td width="43" height="18" class="style5">6년차</td>
                                                            <td width="43" height="18" class="style5">7년차</td>
                                                            <td width="44" height="18" class="style5">8년차</td>
                                                            <td width="57" height="18" class="style5">합계</td>
                                                            <td width="96" height="18" class="style5">수지 재료단가 </td>
                                                            <td width="111" height="18" bgcolor="#FFFFFF" class="style1"><%=su_stan_day%></td>
                                                            <td width="222" height="18" class="style5">비고</td>
                                                        </tr>
                                                        <tr>
                                                            <td width="110" height="18" rowspan="2" class="style5">판매량(천개)</td>
                                                            <td width="43" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(su_year_1[0], "", 0, 0)%></FONT></strong></td>
                                                            <td width="43" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(su_year_2[0], "", 0, 0)%></FONT></strong></td>
                                                            <td width="43" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_3[0], "", 0, 0)%></td>
                                                            <td width="43" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_4[0], "", 0, 0)%></td>
                                                            <td width="43" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_5[0], "", 0, 0)%></td>
                                                            <td width="43" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_6[0], "", 0, 0)%></td>
                                                            <td width="43" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_7[0], "", 0, 0)%></td>
                                                            <td width="44" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_8[0], "", 0, 0)%></td>
                                                            <td width="57" height="18" rowspan="2" bgcolor="#FFFFFF" class="style8"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(avg_su[0], "", 0, 0)%></FONT></strong></td>
                                                            <td height="18" rowspan="2" class="style5">비철 LME시세 </td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style23"><%=lme_cu%><span class="style7"> [＄/Ton]</span></td>
                                                            <td rowspan="5" valign="top" bgcolor="#FFFFFF" class="style12">&nbsp;<%=note_1%></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%if(!"".equals(u_ex_rate)){out.println(u_ex_rate+"<span class='style7'>[￦/$]</span>");} %></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="110" height="18" class="style5">매출액(백만원)</td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(total_sales_1, "", 0, 0)%></FONT></strong></td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(total_sales_2, "", 0, 0)%></FONT></strong></td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_3, "", 0, 0)%></td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_4, "", 0, 0)%></td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_5, "", 0, 0)%></td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_6, "", 0, 0)%></td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_7, "", 0, 0)%></td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_8, "", 0, 0)%></td>
                                                            <td width="57" height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(sum_sales, "", 0, 0)%></td>
                                                            <td height="18" class="style5">포장유형</td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=pack_type%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="110" height="18" class="style5">영업이익(백만원)</td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_1.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_1, 0)));}%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_2.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_2, 0)));}%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_3.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_3, 0)));}%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_4)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_4.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_4, 0)));}%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_5)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_5.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_5, 0)));}%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_6)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_6.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_6, 0)));}%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_7)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_7.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_7, 0)));}%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_8)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_8.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_8, 0)));}%>
                                                            </td>
                                                            <td width="57" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(sum_profit)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(sum_profit, "", 0, 0)%>
                                                            </td>
                                                            <td height="18" class="style5">생산처 구분 </td>
                                                            <td height="18" bgcolor="#FFFFFF" class="style1"><%=pro_1%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="110" height="18" class="style5">영업이익율</td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_1, "%", 100, 1)%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_2, "%", 100, 1)%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_3, "%", 100, 1)%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_4)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_4, "%", 100, 1)%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_5)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_5, "%", 100, 1)%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_6)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_6, "%", 100, 1)%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_7)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_7, "%", 100, 1)%>
                                                            </td>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_8)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_8, "%", 100, 1)%>
                                                            </td>
                                                            <%if(report_pk.equals("1780")){ %>
                                                            <td width="43" height="18" bgcolor="#FFFFFF" class="style13">20.7 %</td>
                                                            <%}else{ %>
                                                            <td width="43" height="18" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(sum_per_p)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(sum_per_p, "%", 100, 1)%>
                                                            </td>
                                                            <%} %>

                                                            <td height="21" class="style5">생산 효율 </td>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(eff_value, "%", 100, 0)%></td>
                                                        </tr>
                                                    </table>
                                                    <table width="955" border="0" cellpadding="0" cellspacing="1" bgcolor = "#FFFFFF">
                                                            <tr>
                                                                <td height="18" colspan="12" valign="bottom" class="style3">3. 원가계산 결과 </td>
                                                                <td width="52" height="18" valign="bottom" class="style3">단위: 원</td>
                                                            </tr>
                                                        </table>
                                                        <table width="955" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="23" rowspan="<%=table_row + 4%>" class="style5">가<br/>격<br/>정<br/>보</td>
                                                                <td width="170" rowspan="2" class="style5">제품명</td>
                                                                <td width="30" rowspan="2" class="style5">적용<br>U/S</td>
                                                                <td width="65" rowspan="2" class="style5">판매<br/>목표가</td>
                                                                <td width="65" rowspan="2" class="style5">목표<br/>수익율</td>
                                                                <td height="35" colspan="2" valign="middle" class="style5"><span class="change">1안<br><%=case_1_note%></span></td>
                                                                <td height="35" colspan="2" valign="middle" class="style5"><span class="change_1">2안<br><%=case_2_note%></span></td>
                                                                <td height="35" colspan="2" valign="middle" class="style5"><span class="change_2">3안<br><%=case_3_note%></span></td>
                                                                <td width="217" rowspan="2" class="style5">비고</td>
                                                            </tr>
                                                            <tr>
                                                                <td width="72" height="18" class="style5"><span class="change_left">총원가</span></td>
                                                                <td width="45" height="18" class="style5"><span class="change_right">수익율</span></td>
                                                                <td width="72" height="18" class="style5"><span class="change_left_1">총원가</span></td>
                                                                <td width="45" height="18" class="style5"><span class="change_right_1">수익율</span></td>
                                                                <td width="72" height="18" class="style5"><span class="change_left_2">총원가</span></td>
                                                                <td width="45" height="18" class="style5"><span class="change_right_2">수익율</span></td>
                                                            </tr>
<%
    p = 0;
    table_row_count = Integer.parseInt(table_row);
    for(j=0;j<table_row_count;j++){
        if(!"".equals(t_earn_rate[j][0]) && !"0".equals(t_earn_rate[j][0])){
            t_earn_rate[j][0] = t_earn_rate[j][0];
        }else{
            t_earn_rate[j][0] = "0";
        }

        if("no".equals(ket_cost_value)){
            t_earn_rate[j][1] = "0";
            t_earn_rate[j][2] = "0";
        }
        if("".equals(t_earn_rate[j][1])){
            t_earn_rate[j][1] = "0";
        }
        if("".equals(t_earn_rate[j][2])){
            t_earn_rate[j][2] = "0";
        }
        if(!"".equals(ket_cost_sum)){
            ket_cost_sum = Double.toString(StringUtil.nullDouble(ket_cost_sum) + (StringUtil.nullDouble(ket_cost[j]) * StringUtil.nullDouble(pro_usage[j])));
        }else{
            ket_cost_sum = Double.toString(StringUtil.nullDouble(ket_cost[j]) * StringUtil.nullDouble(pro_usage[j]));
        }
%>
                                                            <tr>
                                                                <td height="18" bgcolor="#FFFFFF" class="style1"><%=part_name[j]%></td>
                                                                <td bgcolor="#FFFFFF" class="style1"><%=pro_usage[j]%></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(ket_cost[j], "원", 0, 1)%></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style8"><%=StringUtil.checkNull(target_cost[j])%></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style8"><span class="change_left"><%=StringUtil.DoubleFormat(t_actual_cost[j][0], "원", 0, 1)%></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][0])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right"><%=StringUtil.DoubleFormat(t_earn_rate[j][0], "%", 100, 1)%></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style8"><span class="change_left_1"><%=StringUtil.DoubleFormat(t_actual_cost[j][1], "원", 0, 1)%></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][1])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_1"><%=StringUtil.DoubleFormat(t_earn_rate[j][1], "%", 100, 1)%></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style8"><span class="change_left_2"><%=StringUtil.DoubleFormat(t_actual_cost[j][2], "원", 0, 1)%></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][2])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_2"><%=StringUtil.DoubleFormat(t_earn_rate[j][2], "%", 100, 1)%></span></td>

<%
        if(j == 0){
%>
                                                                <td rowspan="<%=table_row_count%>"  valign="top" bgcolor="#FFFFFF" class="style12"><%=note_2%></td>
<%
        }
%>
                                                            </tr>
<%
    }
%>
                                                            <tr>
                                                                <td height=18 class="style5">Total</td>
                                                                <td bgcolor="#FFFFFF" class="style14">&nbsp;</td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style14"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(ket_cost_sum, "원", 0, 1)%></FONT></strong></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style14">&nbsp;</td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style14"><span class="change_left_bottom"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(actual_cost_sum_1, "원", 0, 1)%></FONT></strong></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom"><strong><FONT style="BACKGROUND-COLOR: #FFFA84"><%=StringUtil.DoubleFormat(earn_rate_sum_1, "%", 0, 1)%></FONT></strong></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style14"><span class="change_left_bottom_1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(actual_cost_sum_2, "원", 0, 1)%></FONT></strong></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom_1"><strong><FONT style="BACKGROUND-COLOR: #FFFA84"><%=StringUtil.DoubleFormat(earn_rate_sum_2, "%", 0, 1)%></FONT></strong></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" class="style14"><span class="change_left_bottom_2"><strong><FONT style="BACKGROUND-COLOR: #FFFA84" color=#000000 ><%=StringUtil.DoubleFormat(actual_cost_sum_3, "원", 0, 1)%></FONT></strong></span></td>
                                                                <td height="18" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                 <span class="change_right_bottom_2"><strong><FONT style="BACKGROUND-COLOR: #FFFA84"><%=StringUtil.DoubleFormat(earn_rate_sum_3, "%", 0, 1)%></FONT></strong></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                            </tr>
                                                        </table>
                                                        <table width="955" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                                        <tr>
                                                            <td width="481" height="50" valign="top"><table width="470" border="0" cellpadding="0" cellspacing="1">
                                                            <tr>
                                                              <td height="18" colspan="13" valign="bottom" class="style3">4.주요 검토기준 및 ISSUE 사항 </td>
                                                            </tr>
                                                          </table>
                                                            <table width="470" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                              <tr>
                                                                <td height="120" valign="top" bgcolor="#FFFFFF" class="style12">&nbsp;<%=note_3%></td>
                                                              </tr>
                                                        </table></td>
                                                             <td width="474" height="50" valign="top"><table width="470" border="0" cellpadding="0" cellspacing="1">
                                                            <tr>
                                                              <td height="18" colspan="13" valign="bottom" class="style3">&nbsp;5. 경영층 지시사항 </td>
                                                            </tr>
                                                          </table>
                                                            <table width="470" border="0" align="right" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                              <tr>
                                                                <td height="120" valign="top" bgcolor="#FFFFFF" class="style12">&nbsp;<%=note_6%></td>
                                                              </tr>
                                                        </table></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</Form>
</body>
</html>