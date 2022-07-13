<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostReportCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    request.setCharacterEncoding("utf-8");
    String pk_cr_group = request.getParameter("pk_cr_group")!=null?request.getParameter("pk_cr_group"):"";
    String cost_report_add = request.getParameter("cost_report_add")!=null?request.getParameter("cost_report_add"):"";
    String rev_no = request.getParameter("rev_no")!=null?request.getParameter("rev_no"):"";
    String user_case_count = request.getParameter("user_case_count")!=null?request.getParameter("user_case_count"):"";
    String table_row = request.getParameter("table_row")!=null?request.getParameter("table_row"):"";
    String report_pk = request.getParameter("report_pk")!=null?request.getParameter("report_pk"):"";
    String page_no = request.getParameter("page_no")!=null?request.getParameter("page_no"):"";
    String note_1 = request.getParameter("note_1")!=null?request.getParameter("note_1"):"";
    String note_2 = request.getParameter("note_2")!=null?request.getParameter("note_2"):"";
    String note_3 = request.getParameter("note_3")!=null?request.getParameter("note_3"):"";
    String note_4 = request.getParameter("note_4")!=null?request.getParameter("note_4"):"";
    String note_5 = request.getParameter("note_5")!=null?request.getParameter("note_5"):"";
    String note_5_1 = request.getParameter("note_5_1")!=null?request.getParameter("note_5_1"):"";
    String chk_list = request.getParameter("chk_list")!=null?request.getParameter("chk_list"):"";
    String select_name = request.getParameter("select_name")!=null?request.getParameter("select_name"):"";
    String case_1_note = request.getParameter("case_1_note")!=null?request.getParameter("case_1_note"):"";
    String case_2_note = request.getParameter("case_2_note")!=null?request.getParameter("case_2_note"):"";
    String case_3_note = request.getParameter("case_3_note")!=null?request.getParameter("case_3_note"):"";
    String case_to_note_1 = request.getParameter("case_to_note_1")!=null?request.getParameter("case_to_note_1"):"";
    String case_to_note_2 = request.getParameter("case_to_note_2")!=null?request.getParameter("case_to_note_2"):"";
    String case_to_note_3 = request.getParameter("case_to_note_3")!=null?request.getParameter("case_to_note_3"):"";
    String actual_cost_sum_1 = request.getParameter("actual_cost_sum_1")!=null?request.getParameter("actual_cost_sum_1"):"";
    String earn_rate_sum_1 = request.getParameter("earn_rate_sum_1")!=null?request.getParameter("earn_rate_sum_1"):"";
    String actual_cost_sum_2 = request.getParameter("actual_cost_sum_2")!=null?request.getParameter("actual_cost_sum_2"):"";
    String earn_rate_sum_2 = request.getParameter("earn_rate_sum_2")!=null?request.getParameter("earn_rate_sum_2"):"";
    String actual_cost_sum_3 = request.getParameter("actual_cost_sum_3")!=null?request.getParameter("actual_cost_sum_3"):"";
    String earn_rate_sum_3 = request.getParameter("earn_rate_sum_3")!=null?request.getParameter("earn_rate_sum_3"):"";
    String tocost_year = request.getParameter("tocost_year")!=null?request.getParameter("tocost_year"):"";
    String w_name = request.getParameter("w_name")!=null?request.getParameter("w_name"):"";
    String m_depr_stan = request.getParameter("m_depr_stan")!=null?request.getParameter("m_depr_stan"):"";
    String p_depr_stan = request.getParameter("p_depr_stan")!=null?request.getParameter("p_depr_stan"):"";
    String L_depr_stan = request.getParameter("L_depr_stan")!=null?request.getParameter("L_depr_stan"):"";
    String pack_depr_stan = request.getParameter("pack_depr_stan")!=null?request.getParameter("pack_depr_stan"):"";
    String repack_depr_stan = request.getParameter("repack_depr_stan")!=null?request.getParameter("repack_depr_stan"):"";
    String su_stan_day = request.getParameter("su_stan_day")!=null?request.getParameter("su_stan_day"):"";
    String pjt_name = request.getParameter("pjt_name")!=null?request.getParameter("pjt_name"):"";
    String car_type = request.getParameter("car_type")!=null?request.getParameter("car_type"):"";
    String app_part = request.getParameter("app_part")!=null?request.getParameter("app_part"):"";
    String report_dest = request.getParameter("report_dest")!=null?request.getParameter("report_dest"):"";
    String request_txt = request.getParameter("request_txt")!=null?request.getParameter("request_txt"):"";
    String pack_type = request.getParameter("pack_type")!=null?request.getParameter("pack_type"):"";
    String eff_value = request.getParameter("eff_value")!=null?request.getParameter("eff_value"):"";
    String pro_1 = request.getParameter("pro_1")!=null?request.getParameter("pro_1"):"";
    String customer_F = request.getParameter("customer_F")!=null?request.getParameter("customer_F"):"";
    String pjt_no = request.getParameter("pjt_no")!=null?request.getParameter("pjt_no"):"";
    String select_cost = request.getParameter("select_cost")!=null?request.getParameter("select_cost"):"";
    String page_name = "";
    if("1".equals(page_no)){
        page_name="cost_report_1_edit.jsp";
    }else{
        page_name="cost_report_2_edit.jsp";
    }


    /*pk_cr_group = new String(pk_cr_group.getBytes("ISO-8859-1"), "utf-8");
    cost_report_add = new String(cost_report_add.getBytes("ISO-8859-1"), "utf-8");
    rev_no = new String(rev_no.getBytes("ISO-8859-1"), "utf-8");
    user_case_count = new String(user_case_count.getBytes("ISO-8859-1"), "utf-8");
    table_row = new String(table_row.getBytes("ISO-8859-1"), "utf-8");
    report_pk = new String(report_pk.getBytes("ISO-8859-1"), "utf-8");
    page_no = new String(page_no.getBytes("ISO-8859-1"), "utf-8");
    note_1 = new String(note_1.getBytes("ISO-8859-1"), "utf-8");
    note_2 = new String(note_2.getBytes("ISO-8859-1"), "utf-8");
    note_3 = new String(note_3.getBytes("ISO-8859-1"), "utf-8");
    note_4 = new String(note_4.getBytes("ISO-8859-1"), "utf-8");
    note_5 = new String(note_5.getBytes("ISO-8859-1"), "utf-8");
    note_5_1 = new String(note_5_1.getBytes("ISO-8859-1"), "utf-8");
    chk_list = new String(chk_list.getBytes("ISO-8859-1"), "utf-8");
    select_name = new String(select_name.getBytes("ISO-8859-1"), "utf-8");
    case_1_note = new String(case_1_note.getBytes("ISO-8859-1"), "utf-8");
    case_2_note = new String(case_2_note.getBytes("ISO-8859-1"), "utf-8");
    case_3_note = new String(case_3_note.getBytes("ISO-8859-1"), "utf-8");
    case_to_note_1 = new String(case_to_note_1.getBytes("ISO-8859-1"), "utf-8");
    case_to_note_2 = new String(case_to_note_2.getBytes("ISO-8859-1"), "utf-8");
    case_to_note_3 = new String(case_to_note_3.getBytes("ISO-8859-1"), "utf-8");
    actual_cost_sum_1 = new String(actual_cost_sum_1.getBytes("ISO-8859-1"), "utf-8");
    earn_rate_sum_1 = new String(earn_rate_sum_1.getBytes("ISO-8859-1"), "utf-8");
    actual_cost_sum_2 = new String(actual_cost_sum_2.getBytes("ISO-8859-1"), "utf-8");
    earn_rate_sum_2 = new String(earn_rate_sum_2.getBytes("ISO-8859-1"), "utf-8");
    actual_cost_sum_3 = new String(actual_cost_sum_3.getBytes("ISO-8859-1"), "utf-8");
    earn_rate_sum_3 = new String(earn_rate_sum_3.getBytes("ISO-8859-1"), "utf-8");
    tocost_year = new String(tocost_year.getBytes("ISO-8859-1"), "utf-8");
    w_name = new String(w_name.getBytes("ISO-8859-1"), "utf-8");
    m_depr_stan = new String(m_depr_stan.getBytes("ISO-8859-1"), "utf-8");
    p_depr_stan = new String(p_depr_stan.getBytes("ISO-8859-1"), "utf-8");
    L_depr_stan = new String(L_depr_stan.getBytes("ISO-8859-1"), "utf-8");
    pack_depr_stan = new String(pack_depr_stan.getBytes("ISO-8859-1"), "utf-8");
    repack_depr_stan = new String(repack_depr_stan.getBytes("ISO-8859-1"), "utf-8");
    su_stan_day = new String(su_stan_day.getBytes("ISO-8859-1"), "utf-8");
    pjt_name = new String(pjt_name.getBytes("ISO-8859-1"), "utf-8");
    car_type = new String(car_type.getBytes("ISO-8859-1"), "utf-8");
    app_part = new String(app_part.getBytes("ISO-8859-1"), "utf-8");
    report_dest = new String(report_dest.getBytes("ISO-8859-1"), "utf-8");
    request_txt = new String(request_txt.getBytes("ISO-8859-1"), "utf-8");
    pack_type = new String(pack_type.getBytes("ISO-8859-1"), "utf-8");
    eff_value = new String(eff_value.getBytes("ISO-8859-1"), "utf-8");
    pro_1 = new String(pro_1.getBytes("ISO-8859-1"), "utf-8");
    customer_F = new String(customer_F.getBytes("ISO-8859-1"), "utf-8");
    pjt_no = new String(pjt_no.getBytes("ISO-8859-1"), "utf-8");
    select_cost = new String(select_cost.getBytes("ISO-8859-1"), "utf-8");*/

    String[] start_pro = new String[20];
    String[] store = new String[20];
    String[] dest = new String[20];
    String[] USD_rate = new String[20];
    String[] YEN_rate = new String[20];
    String[] EURO_rate = new String[20];
    String[] CNY_rate = new String[20];
    String[] usage = new String[20];
    String[] pro_usage = new String[20];
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
    String[] group_no = new String[50];
    String[][] S_pk_cw = new String[20][20];
    String[] pk_cw = new String[20];
    String[] part_name = new String[20];
    String[] part_type = new String[20];
    String[] part_no = new String[20];
    String[] su_year_1 = new String[20];
    String[] su_year_2 = new String[20];
    String[] su_year_3 = new String[20];
    String[] su_year_4 = new String[20];
    String[] su_year_5 = new String[20];
    String[] su_year_6 = new String[20];
    String[] su_year_7 = new String[20];
    String[] su_year_8 = new String[20];
    String[] room_earn = new String[20];
    String[] total_sales_1 = new String[20];    // 매출액
    String[] total_sales_2 = new String[20];
    String[] total_sales_3 = new String[20];
    String[] total_sales_4 = new String[20];
    String[] total_sales_5 = new String[20];
    String[] total_sales_6 = new String[20];
    String[] total_sales_7 = new String[20];
    String[] total_sales_8 = new String[20];
    String[] profit_1 = new String[20];    // 영업이익
    String[] profit_2 = new String[20];
    String[] profit_3 = new String[20];
    String[] profit_4 = new String[20];
    String[] profit_5 = new String[20];
    String[] profit_6 = new String[20];
    String[] profit_7 = new String[20];
    String[] profit_8 = new String[20];
    String[] per_profit_1 = new String[20];    // 영업이익율
    String[] per_profit_2 = new String[20];
    String[] per_profit_3 = new String[20];
    String[] per_profit_4 = new String[20];
    String[] per_profit_5 = new String[20];
    String[] per_profit_6 = new String[20];
    String[] per_profit_7 = new String[20];
    String[] per_profit_8 = new String[20];
    String[] make_type = new String[20];
    String[] client_cost = new String[20];
    String[] ket_cost = new String[20];
    String[] target_cost = new String[20];
    String[] pro_type = new String[20];
    String[][] actual_cost = new String[20][20];    // 판매기준총원가
    String[][] earn_rate = new String[20][20];    // 수익률
    String[] FK_cost_request = new String[20];
    String[] avg_su = new String[20];    // 총판매기획수량
    String[] F_pk_crp = new String[20];
    String[] pk_pid = new String[20];

    String case_count_1 = "";
    String case_count_2 = "";
    String f_name = "";
    String a_name = "";
    String team = "";
    String rev_pk = "";
    String dev_step = "";
    String USD_rate_s = "";
    String YEN_rate_s = "";
    String EURO_rate_s = "";
    String CNY_rate_s = "";
    String lme_cu = "";
    String u_ex_rate = "";
    String FK_cost_request_s = "";


    CostReportCtl reportCtl = new CostReportCtl();
    ArrayList dbCostWorkList = null;
    Hashtable dbCostWorkItem = null;

    int A = 65;

    for(int i=0; i<Integer.parseInt(table_row);i++){
        for(int c=0; c<=2;c++){
            S_pk_cw[i][c] =request.getParameter("S_pk_cw"+Integer.toString(i)+Integer.toString(c))!=null?request.getParameter("S_pk_cw"+Integer.toString(i)+Integer.toString(c)):"";
        }
    }
    int table_row_count = Integer.parseInt(table_row);

    if(Integer.parseInt(page_no) == 1){
        int p = 0;
        mold_total[0] = request.getParameter("mold_total_0")!=null?request.getParameter("mold_total_0"):"";
        press_total[0] = request.getParameter("press_total_0")!=null?request.getParameter("press_total_0"):"";
        line_total[0] = request.getParameter("line_total_0")!=null?request.getParameter("line_total_0"):"";
        pack_total[0] = request.getParameter("pack_total_0")!=null?request.getParameter("pack_total_0"):"";
        repack_total[0] = request.getParameter("repack_total_0")!=null?request.getParameter("repack_total_0"):"";
        mold_total[1] = request.getParameter("mold_total_1")!=null?request.getParameter("mold_total_1"):"";
        press_total[1] = request.getParameter("press_total_1")!=null?request.getParameter("press_total_1"):"";
        line_total[1] = request.getParameter("line_total_1")!=null?request.getParameter("line_total_1"):"";
        pack_total[1] = request.getParameter("pack_total_1")!=null?request.getParameter("pack_total_1"):"";
        repack_total[1] = request.getParameter("repack_total_1")!=null?request.getParameter("repack_total_1"):"";
        mold_total[2] = request.getParameter("mold_total_2")!=null?request.getParameter("mold_total_2"):"";
        press_total[2] = request.getParameter("press_total_2")!=null?request.getParameter("press_total_2"):"";
        line_total[2] = request.getParameter("line_total_2")!=null?request.getParameter("line_total_2"):"";
        pack_total[2] = request.getParameter("pack_total_2")!=null?request.getParameter("pack_total_2"):"";
        repack_total[2] = request.getParameter("repack_total_2")!=null?request.getParameter("repack_total_2"):"";

        mold_count[0] = request.getParameter("mold_count")!=null?request.getParameter("mold_count"):"";
        press_count[0] = request.getParameter("press_count")!=null?request.getParameter("press_count"):"";
        line_count[0] = request.getParameter("line_count")!=null?request.getParameter("line_count"):"";
        pack_count[0] = request.getParameter("pack_count")!=null?request.getParameter("pack_count"):"";
        repack_count[0] = request.getParameter("repack_count")!=null?request.getParameter("repack_count"):"";

        for(int j=0;j<table_row_count;j++){
            for(int t=0;t<=2;t++){
                actual_cost[j][t] = request.getParameter("S_actual_cost_"+Integer.toString(j)+Integer.toString(t))!=null?request.getParameter("S_actual_cost_"+Integer.toString(j)+Integer.toString(t)):"";
                earn_rate[j][t] = request.getParameter("S_earn_rate_"+Integer.toString(j)+Integer.toString(t))!=null?request.getParameter("S_earn_rate_"+Integer.toString(j)+Integer.toString(t)):"";
            }
            repack_total[0] = request.getParameter("repack_total_0")!=null?request.getParameter("repack_total_0"):"";
            ket_cost[j] = request.getParameter("ket_cost_"+Integer.toString(j)+"0")!=null?request.getParameter("ket_cost_"+Integer.toString(j)+"0"):"";
            pro_usage[j] = request.getParameter("pro_usage"+Integer.toString(j))!=null?request.getParameter("pro_usage"+Integer.toString(j)):"";
            total_sales_1[j] = request.getParameter("total_sales_1_"+Integer.toString(j))!=null?request.getParameter("total_sales_1_"+Integer.toString(j)):"";
            total_sales_2[j] = request.getParameter("total_sales_2_"+Integer.toString(j))!=null?request.getParameter("total_sales_2_"+Integer.toString(j)):"";
            total_sales_3[j] = request.getParameter("total_sales_3_"+Integer.toString(j))!=null?request.getParameter("total_sales_3_"+Integer.toString(j)):"";
            total_sales_4[j] = request.getParameter("total_sales_4_"+Integer.toString(j))!=null?request.getParameter("total_sales_4_"+Integer.toString(j)):"";
            total_sales_5[j] = request.getParameter("total_sales_5_"+Integer.toString(j))!=null?request.getParameter("total_sales_5_"+Integer.toString(j)):"";
            total_sales_6[j] = request.getParameter("total_sales_6_"+Integer.toString(j))!=null?request.getParameter("total_sales_6_"+Integer.toString(j)):"";
            total_sales_7[j] = request.getParameter("total_sales_7_"+Integer.toString(j))!=null?request.getParameter("total_sales_7_"+Integer.toString(j)):"";
            total_sales_8[j] = request.getParameter("total_sales_8_"+Integer.toString(j))!=null?request.getParameter("total_sales_8_"+Integer.toString(j)):"";
            profit_1[j] = request.getParameter("profit_1_"+Integer.toString(j))!=null?request.getParameter("profit_1_"+Integer.toString(j)):"";
            profit_2[j] = request.getParameter("profit_2_"+Integer.toString(j))!=null?request.getParameter("profit_2_"+Integer.toString(j)):"";
            profit_3[j] = request.getParameter("profit_3_"+Integer.toString(j))!=null?request.getParameter("profit_3_"+Integer.toString(j)):"";
            profit_4[j] = request.getParameter("profit_4_"+Integer.toString(j))!=null?request.getParameter("profit_4_"+Integer.toString(j)):"";
            profit_5[j] = request.getParameter("profit_5_"+Integer.toString(j))!=null?request.getParameter("profit_5_"+Integer.toString(j)):"";
            profit_6[j] = request.getParameter("profit_6_"+Integer.toString(j))!=null?request.getParameter("profit_6_"+Integer.toString(j)):"";
            profit_7[j] = request.getParameter("profit_7_"+Integer.toString(j))!=null?request.getParameter("profit_7_"+Integer.toString(j)):"";
            profit_8[j] = request.getParameter("profit_8_"+Integer.toString(j))!=null?request.getParameter("profit_8_"+Integer.toString(j)):"";
            per_profit_1[j] = request.getParameter("per_profit_1_"+Integer.toString(j))!=null?request.getParameter("per_profit_1_"+Integer.toString(j)):"";
            per_profit_2[j] = request.getParameter("per_profit_2_"+Integer.toString(j))!=null?request.getParameter("per_profit_2_"+Integer.toString(j)):"";
            per_profit_3[j] = request.getParameter("per_profit_3_"+Integer.toString(j))!=null?request.getParameter("per_profit_3_"+Integer.toString(j)):"";
            per_profit_4[j] = request.getParameter("per_profit_4_"+Integer.toString(j))!=null?request.getParameter("per_profit_4_"+Integer.toString(j)):"";
            per_profit_5[j] = request.getParameter("per_profit_5_"+Integer.toString(j))!=null?request.getParameter("per_profit_5_"+Integer.toString(j)):"";
            per_profit_6[j] = request.getParameter("per_profit_6_"+Integer.toString(j))!=null?request.getParameter("per_profit_6_"+Integer.toString(j)):"";
            per_profit_7[j] = request.getParameter("per_profit_7_"+Integer.toString(j))!=null?request.getParameter("per_profit_7_"+Integer.toString(j)):"";
            per_profit_8[j] = request.getParameter("per_profit_8_"+Integer.toString(j))!=null?request.getParameter("per_profit_8_"+Integer.toString(j)):"";

            if(!"null".equals(S_pk_cw[j][p])  && !"".equals(S_pk_cw[j][p])){
                dbCostWorkList = reportCtl.dbCostWorkList(S_pk_cw[j][p]);
                for(int m=0; m < dbCostWorkList.size(); m++){
                    dbCostWorkItem = (Hashtable)dbCostWorkList.get(m);
                    case_count_1 = (String)dbCostWorkItem.get("case_count_1")!=null?(String)dbCostWorkItem.get("case_count_1"):"";
                    case_count_2 = (String)dbCostWorkItem.get("case_count_2")!=null?(String)dbCostWorkItem.get("case_count_2"):"";
                    f_name = (String)dbCostWorkItem.get("f_name")!=null?(String)dbCostWorkItem.get("f_name"):"";
                    a_name = (String)dbCostWorkItem.get("a_name")!=null?(String)dbCostWorkItem.get("a_name"):"";
                    team = (String)dbCostWorkItem.get("team")!=null?(String)dbCostWorkItem.get("team"):"";
                    group_no[j]= (String)dbCostWorkItem.get("group_no")!=null?(String)dbCostWorkItem.get("group_no"):"";
                    rev_pk= (String)dbCostWorkItem.get("rev_pk")!=null?(String)dbCostWorkItem.get("rev_pk"):"";
                    start_pro[j]= (String)dbCostWorkItem.get("start_pro")!=null?(String)dbCostWorkItem.get("start_pro"):"";
                    store[j]= (String)dbCostWorkItem.get("store")!=null?(String)dbCostWorkItem.get("store"):"";
                    dest[j]= (String)dbCostWorkItem.get("dest")!=null?(String)dbCostWorkItem.get("dest"):"";
                    FK_cost_request[j]= (String)dbCostWorkItem.get("FK_cost_request")!=null?(String)dbCostWorkItem.get("FK_cost_request"):"";
                    dev_step= (String)dbCostWorkItem.get("dev_step")!=null?(String)dbCostWorkItem.get("dev_step"):"";
                    USD_rate_s= (String)dbCostWorkItem.get("USD_rate")!=null?(String)dbCostWorkItem.get("USD_rate"):"";
                    YEN_rate_s= (String)dbCostWorkItem.get("YEN_rate")!=null?(String)dbCostWorkItem.get("YEN_rate"):"";
                    EURO_rate_s= (String)dbCostWorkItem.get("EURO_rate")!=null?(String)dbCostWorkItem.get("EURO_rate"):"";
                    CNY_rate_s= (String)dbCostWorkItem.get("CNY_rate")!=null?(String)dbCostWorkItem.get("CNY_rate"):"";
                    lme_cu= (String)dbCostWorkItem.get("lme_ton")!=null?(String)dbCostWorkItem.get("lme_ton"):"";
                    u_ex_rate= (String)dbCostWorkItem.get("u_ex_rate")!=null?(String)dbCostWorkItem.get("u_ex_rate"):"";
                    part_name[j]= (String)dbCostWorkItem.get("part_name")!=null?(String)dbCostWorkItem.get("part_name"):"";
                    part_type[j]= (String)dbCostWorkItem.get("part_type")!=null?(String)dbCostWorkItem.get("part_type"):"";
                    part_no[j]= (String)dbCostWorkItem.get("part_no")!=null?(String)dbCostWorkItem.get("part_no"):"";
                    su_year_1[j]= (String)dbCostWorkItem.get("su_year_1")!=null?(String)dbCostWorkItem.get("su_year_1"):"";
                    su_year_2[j]= (String)dbCostWorkItem.get("su_year_2")!=null?(String)dbCostWorkItem.get("su_year_2"):"";
                    su_year_3[j]= (String)dbCostWorkItem.get("su_year_3")!=null?(String)dbCostWorkItem.get("su_year_3"):"";
                    su_year_4[j]= (String)dbCostWorkItem.get("su_year_4")!=null?(String)dbCostWorkItem.get("su_year_4"):"";
                    su_year_5[j]= (String)dbCostWorkItem.get("su_year_5")!=null?(String)dbCostWorkItem.get("su_year_5"):"";
                    su_year_6[j]= (String)dbCostWorkItem.get("su_year_6")!=null?(String)dbCostWorkItem.get("su_year_6"):"";
                    su_year_7[j]= (String)dbCostWorkItem.get("su_year_7")!=null?(String)dbCostWorkItem.get("su_year_7"):"";
                    su_year_8[j]= (String)dbCostWorkItem.get("su_year_8")!=null?(String)dbCostWorkItem.get("su_year_8"):"";
                    client_cost[j]= (String)dbCostWorkItem.get("client_cost")!=null?(String)dbCostWorkItem.get("client_cost"):"";
                    target_cost[j]= (String)dbCostWorkItem.get("target_cost")!=null?(String)dbCostWorkItem.get("target_cost"):"";
                    usage[j]= (String)dbCostWorkItem.get("usage")!=null?(String)dbCostWorkItem.get("usage"):"";
                    make_type[j]= (String)dbCostWorkItem.get("make_type")!=null?(String)dbCostWorkItem.get("make_type"):"";
                    pro_type[j]= (String)dbCostWorkItem.get("pro_type")!=null?(String)dbCostWorkItem.get("pro_type"):"";
                    pk_pid[j]= (String)dbCostWorkItem.get("pk_pid")!=null?(String)dbCostWorkItem.get("pk_pid"):"";
                }
            }
        }
        if("ok".equals(cost_report_add)){
            int i = 1;
            String k = "0";
            for(int j=0; j < Integer.parseInt(table_row);j++){
                if(j<9){
                    k = "00"+Integer.toString(i);
                }else if(j < 99){
                    k = "0"+Integer.toString(i);
                }else if(j < 999){
                    k = Integer.toString(i);
                }

                if(group_no[j] != null){
                    ArrayList<Hashtable<String, String>> insertItemList = new ArrayList<Hashtable<String, String>>();
                    Hashtable<String, String> insertMap = null;
                    insertMap = new Hashtable<String, String>();
                    insertMap.put("FK_cost_work_1", StringUtil.checkNull(S_pk_cw[j][0]));    // 산출작업DB_PK(1안)
                    if(!StringUtil.checkNull(S_pk_cw[j][1]).equals("") && !StringUtil.checkNull(S_pk_cw[j][1]).equals("null")){
                    	insertMap.put("FK_cost_work_2", StringUtil.checkNull(S_pk_cw[j][1]));    // 산출작업DB_PK(2안)
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][2]).equals("") && !StringUtil.checkNull(S_pk_cw[j][2]).equals("null")){
                    	insertMap.put("FK_cost_work_3", StringUtil.checkNull(S_pk_cw[j][2]));    // 산출작업DB_PK(3안)
                    }
                    insertMap.put("group_no", StringUtil.checkNull(group_no[j]));
                    insertMap.put("pk_cr_group", StringUtil.checkNull(pk_cr_group));    // 요청서그룹번호
                    insertMap.put("FK_cost_request", StringUtil.checkNull(FK_cost_request_s));    // 요청서리스트
                    insertMap.put("dev_step", StringUtil.checkNull(dev_step));    // 개발단계
                    insertMap.put("pjt_name", StringUtil.checkNull(pjt_name));    // ProjectName
                    insertMap.put("pjt_no", StringUtil.checkNull(pjt_no));    // ProjectNo
                    insertMap.put("rev_pk", StringUtil.checkNull(rev_pk));
                    insertMap.put("rev_no", StringUtil.checkNull(rev_no));
                    insertMap.put("part_name", StringUtil.checkNull(part_name[j]));    // PartName
                    insertMap.put("team", StringUtil.checkNull(team));    // 개발담당팀
                    insertMap.put("f_name", StringUtil.checkNull(f_name));    // 개발담당자
                    insertMap.put("a_name", StringUtil.checkNull(a_name));    // 영업담당자
                    insertMap.put("w_name", StringUtil.checkNull(w_name));
                    insertMap.put("request_txt", StringUtil.checkNull(request_txt));    // 요청목적
                    insertMap.put("app_part", StringUtil.checkNull(app_part));    // 적용부위
                    //insertMap.put("room_earn", StringUtil.checkNull(room_earn[j]));    // 룸폭
                    insertMap.put("report_dest", StringUtil.checkNull(report_dest));    // 물류흐름
                    insertMap.put("car_type", StringUtil.checkNull(car_type));    // 적용차종
                    insertMap.put("customer_F", StringUtil.checkNull(customer_F));    // 1차고객사
                    insertMap.put("su_year_1", StringUtil.checkNull(su_year_1[j]));    // 기획수량-1년차
                    insertMap.put("su_year_2", StringUtil.checkNull(su_year_2[j]));    // 기획수량-2년차
                    insertMap.put("su_year_3", StringUtil.checkNull(su_year_3[j]));    // 기획수량-3년차
                    insertMap.put("su_year_4", StringUtil.checkNull(su_year_4[j]));    // 기획수량-4년차
                    insertMap.put("su_year_5", StringUtil.checkNull(su_year_5[j]));    // 기획수량-5년차
                    insertMap.put("su_year_6", StringUtil.checkNull(su_year_6[j]));    // 기획수량-6년차
                    insertMap.put("su_year_7", StringUtil.checkNull(su_year_7[j]));    // 기획수량-7년차
                    insertMap.put("su_year_8", StringUtil.checkNull(su_year_8[j]));    // 기획수량-8년차
                    insertMap.put("start_pro", StringUtil.checkNull(start_pro[j]));    // 생산처(물류흐름)
                    insertMap.put("store", StringUtil.checkNull(store[j]));    // 보관(물류흐름)
                    insertMap.put("dest", StringUtil.checkNull(dest[j]));    // 납입처(물류흐름)
                    insertMap.put("client_cost", StringUtil.checkNull(client_cost[j]));    // 고객사인정예상가
                    insertMap.put("pro_usage", StringUtil.checkNull(pro_usage[j]));    // 납입U/S
                    insertMap.put("ket_cost", StringUtil.checkNull(ket_cost[j]));    // 판매목표가
                    insertMap.put("target_cost", StringUtil.checkNull(target_cost[j]));    // 목표수익율
                    insertMap.put("USD_rate", StringUtil.checkNull(USD_rate_s));    // USD기준환율
                    insertMap.put("YEN_rate", StringUtil.checkNull(YEN_rate_s));    // YEN기준환율
                    insertMap.put("EURO_rate", StringUtil.checkNull(EURO_rate_s));    // EUR기준환율
                    insertMap.put("CNY_rate", StringUtil.checkNull(CNY_rate_s));    // CNY기준환율
                    insertMap.put("lme_cu", StringUtil.checkNull(lme_cu));
                    insertMap.put("u_ex_rate", StringUtil.checkNull(u_ex_rate));    // LME시세
                    insertMap.put("pack_type", StringUtil.checkNull(pack_type));    // 포장유형
                    insertMap.put("usage", StringUtil.checkNull(usage[j]));    // usage
                    insertMap.put("make_type", StringUtil.checkNull(make_type[j]));    // 생산처
                    insertMap.put("pro_1", StringUtil.checkNull(pro_1));    // 사내세부생산처
                    insertMap.put("eff_value", StringUtil.checkNull(eff_value));    // 효율
                    if(!StringUtil.checkNull(S_pk_cw[j][0]).equals("") && !StringUtil.checkNull(S_pk_cw[j][0]).equals("null")){
                    	insertMap.put("actual_cost_1", StringUtil.checkNull(actual_cost[j][0]));    // 총원가
                    	insertMap.put("earn_rate_1", StringUtil.checkNull(earn_rate[j][0]));    // 수익율
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][1]).equals("") && !StringUtil.checkNull(S_pk_cw[j][1]).equals("null")){
                    	insertMap.put("actual_cost_2", StringUtil.checkNull(actual_cost[j][1]));    // 총원가
                    	insertMap.put("earn_rate_2", StringUtil.checkNull(earn_rate[j][1]));    // 수익율
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][2]).equals("") && !StringUtil.checkNull(S_pk_cw[j][2]).equals("null")){
                    	insertMap.put("actual_cost_3", StringUtil.checkNull(actual_cost[j][2]));    // 총원가
                    	insertMap.put("earn_rate_3", StringUtil.checkNull(earn_rate[j][2]));    // 수익율
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][0]).equals("") && !StringUtil.checkNull(S_pk_cw[j][0]).equals("null")){
                    	insertMap.put("actual_cost_sum_1", StringUtil.checkNull(actual_cost_sum_1));
                    	insertMap.put("earn_rate_sum_1", StringUtil.checkNull(earn_rate_sum_1));
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][1]).equals("") && !StringUtil.checkNull(S_pk_cw[j][1]).equals("null")){
                    	insertMap.put("actual_cost_sum_2", StringUtil.checkNull(actual_cost_sum_2));
                    	insertMap.put("earn_rate_sum_2", StringUtil.checkNull(earn_rate_sum_2));
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][2]).equals("") && !StringUtil.checkNull(S_pk_cw[j][2]).equals("null")){
                    	insertMap.put("actual_cost_sum_3", StringUtil.checkNull(actual_cost_sum_3));
                    	insertMap.put("earn_rate_sum_3", StringUtil.checkNull(earn_rate_sum_3));
                    }
                    insertMap.put("mold_count", StringUtil.checkNull(mold_count[0]));    // Mold수량
                    insertMap.put("press_count", StringUtil.checkNull(press_count[0]));    // Press수량
                    insertMap.put("line_count", StringUtil.checkNull(line_count[0]));    // 수동조립설비
                    insertMap.put("pack_count", StringUtil.checkNull(pack_count[0]));    // 포장금형
                    insertMap.put("repack_count", StringUtil.checkNull(repack_count[0]));    // 회수용박스
                    insertMap.put("mold_total_1", StringUtil.checkNull(mold_total[0]));    // Mold비
                    insertMap.put("press_total_1", StringUtil.checkNull(press_total[0]));    // Press비
                    insertMap.put("line_total_1", StringUtil.checkNull(line_total[0]));    // 수동조립설비비
                    insertMap.put("pack_total_1", StringUtil.checkNull(pack_total[0]));    // 포장금형비
                    insertMap.put("repack_total_1", StringUtil.checkNull(repack_total[0]));    // 회수용박스비
                    insertMap.put("mold_total_2", StringUtil.checkNull(mold_total[1]));    // Mold비
                    insertMap.put("press_total_2", StringUtil.checkNull(press_total[1]));    // Press비
                    insertMap.put("line_total_2", StringUtil.checkNull(line_total[1]));    // 수동조립설비비
                    insertMap.put("pack_total_2", StringUtil.checkNull(pack_total[1]));    // 포장금형비
                    insertMap.put("repack_total_2", StringUtil.checkNull(repack_total[1]));    // 회수용박스비
                    insertMap.put("mold_total_3", StringUtil.checkNull(mold_total[2]));    // Mold비
                    insertMap.put("press_total_3", StringUtil.checkNull(press_total[2]));    // Press비
                    insertMap.put("line_total_3", StringUtil.checkNull(line_total[2]));    // 수동조립설비비
                    insertMap.put("pack_total_3", StringUtil.checkNull(pack_total[2]));    // 포장금형비
                    insertMap.put("repack_total_3", StringUtil.checkNull(repack_total[2]));    // 회수용박스비
                    insertMap.put("m_depr_stan", StringUtil.checkNull(m_depr_stan));    // Mold상각기준
                    insertMap.put("p_depr_stan", StringUtil.checkNull(p_depr_stan));    // Press상각기준
                    insertMap.put("L_depr_stan", StringUtil.checkNull(L_depr_stan));    // 수동조립설비상각기준
                    insertMap.put("pack_depr_stan", StringUtil.checkNull(pack_depr_stan));    // 포장금형상각기준
                    insertMap.put("repack_depr_stan", StringUtil.checkNull(repack_depr_stan));    // 회수용박스상각기준
                    if(!"".equals(total_sales_1[j])){
                        insertMap.put("total_sales_1", StringUtil.checkNull(total_sales_1[j]));    // 매출액-1년차
                        insertMap.put("total_sales_2", StringUtil.checkNull(total_sales_2[j]));    // 매출액-2년차
                        insertMap.put("total_sales_3", StringUtil.checkNull(total_sales_3[j]));    // 매출액-3년차
                        insertMap.put("total_sales_4", StringUtil.checkNull(total_sales_4[j]));    // 매출액-4년차
                        insertMap.put("total_sales_5", StringUtil.checkNull(total_sales_5[j]));    // 매출액-5년차
                        insertMap.put("total_sales_6", StringUtil.checkNull(total_sales_6[j]));    // 매출액-6년차
                        insertMap.put("total_sales_7", StringUtil.checkNull(total_sales_7[j]));    // 매출액-7년차
                        insertMap.put("total_sales_8", StringUtil.checkNull(total_sales_8[j]));    // 매출액-8년차
                        insertMap.put("profit_1", StringUtil.checkNull(profit_1[j]));    // 영업이익-1년차
                        insertMap.put("profit_2", StringUtil.checkNull(profit_2[j]));    // 영업이익-2년차
                        insertMap.put("profit_3", StringUtil.checkNull(profit_3[j]));    // 영업이익-3년차
                        insertMap.put("profit_4", StringUtil.checkNull(profit_4[j]));    // 영업이익-4년차
                        insertMap.put("profit_5", StringUtil.checkNull(profit_5[j]));    // 영업이익-5년차
                        insertMap.put("profit_6", StringUtil.checkNull(profit_6[j]));    // 영업이익-6년차
                        insertMap.put("profit_7", StringUtil.checkNull(profit_7[j]));    // 영업이익-7년차
                        insertMap.put("profit_8", StringUtil.checkNull(profit_8[j]));    // 영업이익-8년차
                        insertMap.put("per_profit_1", StringUtil.checkNull(per_profit_1[j]));    // 영업이익율-1년차
                        insertMap.put("per_profit_2", StringUtil.checkNull(per_profit_2[j]));    // 영업이익율-2년차
                        insertMap.put("per_profit_3", StringUtil.checkNull(per_profit_3[j]));    // 영업이익율-3년차
                        insertMap.put("per_profit_4", StringUtil.checkNull(per_profit_4[j]));    // 영업이익율-4년차
                        insertMap.put("per_profit_5", StringUtil.checkNull(per_profit_5[j]));    // 영업이익율-5년차
                        insertMap.put("per_profit_6", StringUtil.checkNull(per_profit_6[j]));    // 영업이익율-6년차
                        insertMap.put("per_profit_7", StringUtil.checkNull(per_profit_7[j]));    // 영업이익율-7년차
                        insertMap.put("per_profit_8", StringUtil.checkNull(per_profit_8[j]));    // 영업이익율-8년차
                    }else{
                        insertMap.put("total_sales_1", "");    // 매출액-1년차
                        insertMap.put("total_sales_2", "");    // 매출액-2년차
                        insertMap.put("total_sales_3", "");    // 매출액-3년차
                        insertMap.put("total_sales_4", "");    // 매출액-4년차
                        insertMap.put("total_sales_5", "");    // 매출액-5년차
                        insertMap.put("total_sales_6", "");    // 매출액-6년차
                        insertMap.put("total_sales_7", "");    // 매출액-7년차
                        insertMap.put("total_sales_8", "");    // 매출액-8년차
                        insertMap.put("profit_1", "");    // 영업이익-1년차
                        insertMap.put("profit_2", "");    // 영업이익-2년차
                        insertMap.put("profit_3", "");    // 영업이익-3년차
                        insertMap.put("profit_4", "");    // 영업이익-4년차
                        insertMap.put("profit_5", "");    // 영업이익-5년차
                        insertMap.put("profit_6", "");    // 영업이익-6년차
                        insertMap.put("profit_7", "");    // 영업이익-7년차
                        insertMap.put("profit_8", "");    // 영업이익-8년차
                        insertMap.put("per_profit_1", "");    // 영업이익율-1));
                        insertMap.put("per_profit_2", "");    // 영업이익율-2년차
                        insertMap.put("per_profit_3", "");    // 영업이익율-3년차
                        insertMap.put("per_profit_4", "");    // 영업이익율-4년차
                        insertMap.put("per_profit_5", "");    // 영업이익율-5년차
                        insertMap.put("per_profit_6", "");    // 영업이익율-6년차
                        insertMap.put("per_profit_7", "");    // 영업이익율-7년차
                        insertMap.put("per_profit_8", "");    // 영업이익율-8년차
                    }
                    insertMap.put("su_stan_day", StringUtil.checkNull(su_stan_day));    // 수지재료단가
                    insertMap.put("note_1", StringUtil.checkNull(note_1));    // 2.비고
                    insertMap.put("note_2", StringUtil.checkNull(note_2));    // 3.비고
                    insertMap.put("note_3", StringUtil.checkNull(note_3));    // 4.비고
                    insertMap.put("note_4", StringUtil.checkNull(note_4));    // 5.비고
                    insertMap.put("note_5", StringUtil.checkNull(note_5));    // 보고서2번page검토조건및의견란
                    insertMap.put("note_5_1", StringUtil.checkNull(note_5_1));    // 보고서2번page검토조건및의견란
                    insertMap.put("case_1_note", StringUtil.checkNull(case_1_note));
                    insertMap.put("case_2_note", StringUtil.checkNull(case_2_note));
                    insertMap.put("case_3_note", StringUtil.checkNull(case_3_note));
                    insertMap.put("case_to_note_1", StringUtil.checkNull(case_to_note_1));
                    insertMap.put("case_to_note_2", StringUtil.checkNull(case_to_note_2));
                    insertMap.put("case_to_note_3", StringUtil.checkNull(case_to_note_3));
                    insertMap.put("pack_type", StringUtil.checkNull(pack_type));
                    insertMap.put("tocost_year", StringUtil.checkNull(tocost_year));
                    insertMap.put("select_cost", StringUtil.checkNull(select_cost));
                    insertMap.put("crp_group", StringUtil.checkNull(report_pk));
                    insertMap.put("k", StringUtil.checkNull(k));
                    insertItemList.add(insertMap);
                    reportCtl.dbCostWorkUpdate(insertItemList);
                    insertItemList.clear();
                    i=i+1;
                } // group_no[j] if end
            } // table_row for end
            reportCtl.selectCostUpdate(pk_cr_group, rev_no);
        }else{
            for(int j=0; j < Integer.parseInt(table_row);j++){
                if(group_no[j] != null){
                    ArrayList<Hashtable<String, String>> insertItemList = new ArrayList<Hashtable<String, String>>();
                    Hashtable<String, String> insertMap = null;
                    insertMap = new Hashtable<String, String>();
                    insertMap.put("FK_cost_work_1", StringUtil.checkNull(S_pk_cw[j][0]));    // 산출작업DB_PK(1안)
                    if(!StringUtil.checkNull(S_pk_cw[j][1]).equals("") && !StringUtil.checkNull(S_pk_cw[j][1]).equals("null")){
                        insertMap.put("FK_cost_work_2", StringUtil.checkNull(S_pk_cw[j][1]));    // 산출작업DB_PK(3안)
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][2]).equals("") && !StringUtil.checkNull(S_pk_cw[j][2]).equals("null")){
                        insertMap.put("FK_cost_work_3", StringUtil.checkNull(S_pk_cw[j][2]));    // 산출작업DB_PK(3안)
                    }
                    insertMap.put("group_no", StringUtil.checkNull(group_no[j]));
                    insertMap.put("pk_cr_group", StringUtil.checkNull(pk_cr_group));    // 요청서그룹번호
                    insertMap.put("FK_cost_request", StringUtil.checkNull(FK_cost_request_s));    // 요청서리스트
                    insertMap.put("dev_step", StringUtil.checkNull(dev_step));    // 개발단계
                    insertMap.put("pjt_name", StringUtil.checkNull(pjt_name));    // ProjectName
                    insertMap.put("pjt_no", StringUtil.checkNull(pjt_no));    // ProjectNo
                    insertMap.put("rev_pk", StringUtil.checkNull(rev_pk));
                    insertMap.put("rev_no", StringUtil.checkNull(rev_no));
                    insertMap.put("part_name", StringUtil.checkNull(part_name[j]));    // PartName
                    insertMap.put("team", StringUtil.checkNull(team));    // 개발담당팀
                    insertMap.put("f_name", StringUtil.checkNull(f_name));    // 개발담당자
                    insertMap.put("a_name", StringUtil.checkNull(a_name));    // 영업담당자
                    insertMap.put("w_name", StringUtil.checkNull(w_name));
                    insertMap.put("request_txt", StringUtil.checkNull(request_txt));    // 요청목적
                    insertMap.put("app_part", StringUtil.checkNull(app_part));    // 적용부위
                    //insertMap.put("room_earn", StringUtil.checkNull(room_earn[j]));    // 룸폭
                    insertMap.put("report_dest", StringUtil.checkNull(report_dest));    // 물류흐름
                    insertMap.put("car_type", StringUtil.checkNull(car_type));    // 적용차종
                    insertMap.put("customer_F", StringUtil.checkNull(customer_F));    // 1차고객사
                    insertMap.put("su_year_1", StringUtil.checkNull(su_year_1[j]));    // 기획수량-1년차
                    insertMap.put("su_year_2", StringUtil.checkNull(su_year_2[j]));    // 기획수량-2년차
                    insertMap.put("su_year_3", StringUtil.checkNull(su_year_3[j]));    // 기획수량-3년차
                    insertMap.put("su_year_4", StringUtil.checkNull(su_year_4[j]));    // 기획수량-4년차
                    insertMap.put("su_year_5", StringUtil.checkNull(su_year_5[j]));    // 기획수량-5년차
                    insertMap.put("su_year_6", StringUtil.checkNull(su_year_6[j]));    // 기획수량-6년차
                    insertMap.put("su_year_7", StringUtil.checkNull(su_year_7[j]));    // 기획수량-7년차
                    insertMap.put("su_year_8", StringUtil.checkNull(su_year_8[j]));    // 기획수량-8년차
                    insertMap.put("start_pro", StringUtil.checkNull(start_pro[j]));    // 생산처(물류흐름)
                    insertMap.put("store", StringUtil.checkNull(store[j]));    // 보관(물류흐름)
                    insertMap.put("dest", StringUtil.checkNull(dest[j]));    // 납입처(물류흐름)
                    insertMap.put("client_cost", StringUtil.checkNull(client_cost[j]));    // 고객사인정예상가
                    insertMap.put("pro_usage", StringUtil.checkNull(pro_usage[j]));    // 납입U/S
                    insertMap.put("ket_cost", StringUtil.checkNull(ket_cost[j]));    // 판매목표가
                    insertMap.put("target_cost", StringUtil.checkNull(target_cost[j]));    // 목표수익율
                    insertMap.put("USD_rate", StringUtil.checkNull(USD_rate_s));    // USD기준환율
                    insertMap.put("YEN_rate", StringUtil.checkNull(YEN_rate_s));    // YEN기준환율
                    insertMap.put("EURO_rate", StringUtil.checkNull(EURO_rate_s));    // EUR기준환율
                    insertMap.put("CNY_rate", StringUtil.checkNull(CNY_rate_s));    // CNY기준환율
                    insertMap.put("lme_cu", StringUtil.checkNull(lme_cu));
                    insertMap.put("u_ex_rate", StringUtil.checkNull(u_ex_rate));    // LME시세
                    insertMap.put("pack_type", StringUtil.checkNull(pack_type));    // 포장유형
                    insertMap.put("usage", StringUtil.checkNull(usage[j]));    // usage
                    insertMap.put("make_type", StringUtil.checkNull(make_type[j]));    // 생산처
                    insertMap.put("pro_1", StringUtil.checkNull(pro_1));    // 사내세부생산처
                    insertMap.put("eff_value", StringUtil.checkNull(eff_value));    // 효율
                    if(!StringUtil.checkNull(S_pk_cw[j][0]).equals("") && !StringUtil.checkNull(S_pk_cw[j][0]).equals("null")){
                    	insertMap.put("actual_cost_1", StringUtil.checkNull(actual_cost[j][0]));    // 총원가
                    	insertMap.put("earn_rate_1", StringUtil.checkNull(earn_rate[j][0]));    // 수익율
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][1]).equals("") && !StringUtil.checkNull(S_pk_cw[j][1]).equals("null")){
                    	insertMap.put("actual_cost_2", StringUtil.checkNull(actual_cost[j][1]));    // 총원가
                    	insertMap.put("earn_rate_2", StringUtil.checkNull(earn_rate[j][1]));    // 수익율
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][2]).equals("") && !StringUtil.checkNull(S_pk_cw[j][2]).equals("null")){
                        insertMap.put("actual_cost_3", StringUtil.checkNull(actual_cost[j][2]));    // 총원가
                        insertMap.put("earn_rate_3", StringUtil.checkNull(earn_rate[j][2]));    // 수익율
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][0]).equals("") && !StringUtil.checkNull(S_pk_cw[j][0]).equals("null")){
                    	insertMap.put("actual_cost_sum_1", StringUtil.checkNull(actual_cost_sum_1));
                    	insertMap.put("earn_rate_sum_1", StringUtil.checkNull(earn_rate_sum_1));
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][1]).equals("") && !StringUtil.checkNull(S_pk_cw[j][1]).equals("null")){
                    	insertMap.put("actual_cost_sum_2", StringUtil.checkNull(actual_cost_sum_2));
                    	insertMap.put("earn_rate_sum_2", StringUtil.checkNull(earn_rate_sum_2));
                    }
                    if(!StringUtil.checkNull(S_pk_cw[j][2]).equals("") && !StringUtil.checkNull(S_pk_cw[j][2]).equals("null")){
                        insertMap.put("actual_cost_sum_3", StringUtil.checkNull(actual_cost_sum_3));
                        insertMap.put("earn_rate_sum_3", StringUtil.checkNull(earn_rate_sum_3));
                    }
                    insertMap.put("mold_count", StringUtil.checkNull(mold_count[0]));    // Mold수량
                    insertMap.put("press_count", StringUtil.checkNull(press_count[0]));    // Press수량
                    insertMap.put("line_count", StringUtil.checkNull(line_count[0]));    // 수동조립설비
                    insertMap.put("pack_count", StringUtil.checkNull(pack_count[0]));    // 포장금형
                    insertMap.put("repack_count", StringUtil.checkNull(repack_count[0]));    // 회수용박스
                    insertMap.put("mold_total_1", StringUtil.checkNull(mold_total[0]));    // Mold비
                    insertMap.put("press_total_1", StringUtil.checkNull(press_total[0]));    // Press비
                    insertMap.put("line_total_1", StringUtil.checkNull(line_total[0]));    // 수동조립설비비
                    insertMap.put("pack_total_1", StringUtil.checkNull(pack_total[0]));    // 포장금형비
                    insertMap.put("repack_total_1", StringUtil.checkNull(repack_total[0]));    // 회수용박스비
                    insertMap.put("mold_total_2", StringUtil.checkNull(mold_total[1]));    // Mold비
                    insertMap.put("press_total_2", StringUtil.checkNull(press_total[1]));    // Press비
                    insertMap.put("line_total_2", StringUtil.checkNull(line_total[1]));    // 수동조립설비비
                    insertMap.put("pack_total_2", StringUtil.checkNull(pack_total[1]));    // 포장금형비
                    insertMap.put("repack_total_2", StringUtil.checkNull(repack_total[1]));    // 회수용박스비
                    insertMap.put("mold_total_3", StringUtil.checkNull(mold_total[2]));    // Mold비
                    insertMap.put("press_total_3", StringUtil.checkNull(press_total[2]));    // Press비
                    insertMap.put("line_total_3", StringUtil.checkNull(line_total[2]));    // 수동조립설비비
                    insertMap.put("pack_total_3", StringUtil.checkNull(pack_total[2]));    // 포장금형비
                    insertMap.put("repack_total_3", StringUtil.checkNull(repack_total[2]));    // 회수용박스비
                    insertMap.put("m_depr_stan", StringUtil.checkNull(m_depr_stan));    // Mold상각기준
                    insertMap.put("p_depr_stan", StringUtil.checkNull(p_depr_stan));    // Press상각기준
                    insertMap.put("L_depr_stan", StringUtil.checkNull(L_depr_stan));    // 수동조립설비상각기준
                    insertMap.put("pack_depr_stan", StringUtil.checkNull(pack_depr_stan));    // 포장금형상각기준
                    insertMap.put("repack_depr_stan", StringUtil.checkNull(repack_depr_stan));    // 회수용박스상각기준
                    if(!"".equals(total_sales_1[j])){
                        insertMap.put("total_sales_1", StringUtil.checkNull(total_sales_1[j]));    // 매출액-1년차
                        insertMap.put("total_sales_2", StringUtil.checkNull(total_sales_2[j]));    // 매출액-2년차
                        insertMap.put("total_sales_3", StringUtil.checkNull(total_sales_3[j]));    // 매출액-3년차
                        insertMap.put("total_sales_4", StringUtil.checkNull(total_sales_4[j]));    // 매출액-4년차
                        insertMap.put("total_sales_5", StringUtil.checkNull(total_sales_5[j]));    // 매출액-5년차
                        insertMap.put("total_sales_6", StringUtil.checkNull(total_sales_6[j]));    // 매출액-6년차
                        insertMap.put("total_sales_7", StringUtil.checkNull(total_sales_7[j]));    // 매출액-7년차
                        insertMap.put("total_sales_8", StringUtil.checkNull(total_sales_8[j]));    // 매출액-8년차
                        insertMap.put("profit_1", StringUtil.checkNull(profit_1[j]));    // 영업이익-1년차
                        insertMap.put("profit_2", StringUtil.checkNull(profit_2[j]));    // 영업이익-2년차
                        insertMap.put("profit_3", StringUtil.checkNull(profit_3[j]));    // 영업이익-3년차
                        insertMap.put("profit_4", StringUtil.checkNull(profit_4[j]));    // 영업이익-4년차
                        insertMap.put("profit_5", StringUtil.checkNull(profit_5[j]));    // 영업이익-5년차
                        insertMap.put("profit_6", StringUtil.checkNull(profit_6[j]));    // 영업이익-6년차
                        insertMap.put("profit_7", StringUtil.checkNull(profit_7[j]));    // 영업이익-7년차
                        insertMap.put("profit_8", StringUtil.checkNull(profit_8[j]));    // 영업이익-8년차
                        insertMap.put("per_profit_1", StringUtil.checkNull(per_profit_1[j]));    // 영업이익율-1년차
                        insertMap.put("per_profit_2", StringUtil.checkNull(per_profit_2[j]));    // 영업이익율-2년차
                        insertMap.put("per_profit_3", StringUtil.checkNull(per_profit_3[j]));    // 영업이익율-3년차
                        insertMap.put("per_profit_4", StringUtil.checkNull(per_profit_4[j]));    // 영업이익율-4년차
                        insertMap.put("per_profit_5", StringUtil.checkNull(per_profit_5[j]));    // 영업이익율-5년차
                        insertMap.put("per_profit_6", StringUtil.checkNull(per_profit_6[j]));    // 영업이익율-6년차
                        insertMap.put("per_profit_7", StringUtil.checkNull(per_profit_7[j]));    // 영업이익율-7년차
                        insertMap.put("per_profit_8", StringUtil.checkNull(per_profit_8[j]));    // 영업이익율-8년차
                    }else{
                        insertMap.put("total_sales_1", "");    // 매출액-1년차
                        insertMap.put("total_sales_2", "");    // 매출액-2년차
                        insertMap.put("total_sales_3", "");    // 매출액-3년차
                        insertMap.put("total_sales_4", "");    // 매출액-4년차
                        insertMap.put("total_sales_5", "");    // 매출액-5년차
                        insertMap.put("total_sales_6", "");    // 매출액-6년차
                        insertMap.put("total_sales_7", "");    // 매출액-7년차
                        insertMap.put("total_sales_8", "");    // 매출액-8년차
                        insertMap.put("profit_1", "");    // 영업이익-1년차
                        insertMap.put("profit_2", "");    // 영업이익-2년차
                        insertMap.put("profit_3", "");    // 영업이익-3년차
                        insertMap.put("profit_4", "");    // 영업이익-4년차
                        insertMap.put("profit_5", "");    // 영업이익-5년차
                        insertMap.put("profit_6", "");    // 영업이익-6년차
                        insertMap.put("profit_7", "");    // 영업이익-7년차
                        insertMap.put("profit_8", "");    // 영업이익-8년차
                        insertMap.put("per_profit_1", "");    // 영업이익율-1));
                        insertMap.put("per_profit_2", "");    // 영업이익율-2년차
                        insertMap.put("per_profit_3", "");    // 영업이익율-3년차
                        insertMap.put("per_profit_4", "");    // 영업이익율-4년차
                        insertMap.put("per_profit_5", "");    // 영업이익율-5년차
                        insertMap.put("per_profit_6", "");    // 영업이익율-6년차
                        insertMap.put("per_profit_7", "");    // 영업이익율-7년차
                        insertMap.put("per_profit_8", "");    // 영업이익율-8년차
                    }
                    insertMap.put("su_stan_day", StringUtil.checkNull(su_stan_day));    // 수지재료단가
                    insertMap.put("note_1", StringUtil.checkNull(note_1));    // 2.비고
                    insertMap.put("note_2", StringUtil.checkNull(note_2));    // 3.비고
                    insertMap.put("note_3", StringUtil.checkNull(note_3));    // 4.비고
                    insertMap.put("note_4", StringUtil.checkNull(note_4));    // 5.비고
                    insertMap.put("note_5", StringUtil.checkNull(note_5));    // 보고서2번page검토조건및의견란
                    insertMap.put("note_5_1", StringUtil.checkNull(note_5_1));    // 보고서2번page검토조건및의견란
                    insertMap.put("case_1_note", StringUtil.checkNull(case_1_note));
                    insertMap.put("case_2_note", StringUtil.checkNull(case_2_note));
                    insertMap.put("case_3_note", StringUtil.checkNull(case_3_note));
                    insertMap.put("case_to_note_1", StringUtil.checkNull(case_to_note_1));
                    insertMap.put("case_to_note_2", StringUtil.checkNull(case_to_note_2));
                    insertMap.put("case_to_note_3", StringUtil.checkNull(case_to_note_3));
                    insertMap.put("pack_type", StringUtil.checkNull(pack_type));
                    insertMap.put("tocost_year", StringUtil.checkNull(tocost_year));
                    insertMap.put("select_cost", StringUtil.checkNull(select_cost));
                    insertMap.put("crp_group", StringUtil.checkNull(report_pk));
                    insertMap.put("pk_pid", StringUtil.checkNull(pk_pid[j]));
                    insertMap.put("j", Integer.toString(j));

                    insertItemList.add(insertMap);

                    ArrayList returnItemList = new ArrayList();
                    returnItemList = reportCtl.dbCostWorkInsert(insertItemList);
                    if(returnItemList.size() > 0 && (Integer)returnItemList.get(0) == 1){
                        F_pk_crp[j] = (String)returnItemList.get(1);
                        //if(Integer.parseInt(StringUtil.checkNullZero(F_pk_crp[0])) > 0){
                        //}else{
                        reportCtl.updateFPkCrp(F_pk_crp[0],F_pk_crp[j]) ;
                        //}
                    }
                    insertItemList.clear();
                } // group_no[j] if end
            } // table_row for end

            // 작업DB에 보고서 작성여부 저장
            reportCtl.selectCostUpdate1(pk_cr_group, rev_no, F_pk_crp[0]);
        }
    }else{
        reportCtl.reportNote5Update(report_pk, note_5, note_5_1);
    }

    if(!"".equals(StringUtil.checkNull(F_pk_crp[0]))){
        report_pk = F_pk_crp[0];
    }else{
        report_pk = report_pk;
    }
    if(chk_list.equals("")){
        if(Integer.parseInt(page_no) == 1){
            int p = Integer.parseInt(StringUtil.checkNullZero(select_cost)) - 1;
            for(int j=0;j<Integer.parseInt(table_row);j++){
                    reportCtl.costWorkSelectCostUpdate(S_pk_cw[j][p]);
            }
        }
    }
%>
<HTML>
<HEAD>
<title>한국단자 종합 개발 시스템 - 원가산출 </title>
</HEAD>
<CENTER>
<br>
<br>
</CENTER>
<BODY onload="CALL();">
<script language= "javascript">
    function CALL(){
        alert("수정되었습니다.");
        window.location.href = "/plm/jsp/cost/costreport/"+'<%=page_name%>'+"?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&cost_report_add=ok&chk_list="+'<%=chk_list%>'+"&report_pk="+'<%=report_pk%>'+"&page_no=3&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>';
    }
</script>
</BODY>
</HTML>