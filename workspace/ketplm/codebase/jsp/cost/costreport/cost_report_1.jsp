<%@ page language="java" contentType="text/html;charset=euc-kr" pageEncoding="euc-kr" %>
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
    String login_id	= StringUtil.checkNull((String)session.getAttribute("login_id"));//���� ���� id
    String cost_id	= StringUtil.checkNull((String)session.getAttribute("cost_id"));//user id
    String group_m	= StringUtil.checkNull((String)session.getAttribute("group_m"));
    String costAuth	= StringUtil.checkNull((String)session.getAttribute("costAuth"));

    if(group_m.equals("G")){
        position = "�׷���";
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
    //String select_case = request.getParameter("select_case")!=null?request.getParameter("select_case"):"";
    //String us_change = request.getParameter("us_change")!=null?request.getParameter("us_change"):"";
    //String case_sel = request.getParameter("case_sel")!=null?request.getParameter("case_sel"):"";
    if(dept_no.equals("11651")){
        login_id = "admin";
    }
    if(("1".equals(visitor) || "2".equals(visitor)) && !"".equals(Ename)){
        login_id = "pro_man";
        session.setAttribute("id", login_id);
    }else if("3".equals(visitor) && !"".equals(Ename)){
        login_id = "business";
        session.setAttribute("login_id", login_id);
    }else if(("1".equals(visitor) || "2".equals(visitor) || "3".equals(visitor)) && Ename.equals("")){
        response.sendRedirect("http://plm.ket.com/cost_main.jsp");
    }else if("4".equals(visitor)){
        login_id = "business";
        session.setAttribute("id", login_id);
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
    String[] t_total_sales_1 = new String[70];    // �����
    String[] t_total_sales_2 = new String[70];
    String[] t_total_sales_3 = new String[70];
    String[] t_total_sales_4 = new String[70];
    String[] t_total_sales_5 = new String[70];
    String[] t_total_sales_6 = new String[70];
    String[] t_total_sales_7 = new String[70];
    String[] t_total_sales_8 = new String[70];
    String[] t_profit_1 = new String[70];    // ��������
    String[] t_profit_2 = new String[70];
    String[] t_profit_3 = new String[70];
    String[] t_profit_4 = new String[70];
    String[] t_profit_5 = new String[70];
    String[] t_profit_6 = new String[70];
    String[] t_profit_7 = new String[70];
    String[] t_profit_8 = new String[70];
    String[] t_per_profit_1 = new String[70];    // ����������
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
    String[][] actual_cost = new String[70][20];    // �Ǹű����ѿ���
    String[][] earn_rate = new String[70][20];    // ���ͷ�
    String[][] t_actual_cost = new String[20][20];    // �Ǹű����ѿ���
    String[][] t_earn_rate = new String[20][20];    // ���ͷ�
    String[][] FK_cost_request = new String[70][20];
    String[] avg_su = new String[70];    // ���Ǹű�ȹ����
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
    String file_2 = "";
    String file_3 = "";
    String file_4 = "";
    String file_5 = "";
    String file_6 = "";

    String file_2_name = "";
    String file_3_name  = "";
    String file_4_name  = "";
    String file_5_name  = "";
    String file_6_name  = "";
    String invest_cnt   = "0";
    String select_cost   = "";
    String input_gb   = "";
    String line_name = "";

    chk_list = "";
    int j=0;
    int p = 0;
    CostReportCtl reportCtl = new CostReportCtl();
    ArrayList costReportList = null;
    Hashtable costReportItem = null;
    costReportList = reportCtl.costWorkReportViewList(report_pk);
    for(int i=0; i < costReportList.size(); i++){
        costReportItem = (Hashtable)costReportList.get(i);
        t_pk_cw[i][0] = (String)costReportItem.get("FK_cost_work_1")!=null?(String)costReportItem.get("FK_cost_work_1"):"";    // �����۾�DB_PK(1��)
        t_pk_cw[i][1] = (String)costReportItem.get("FK_cost_work_2")!=null?(String)costReportItem.get("FK_cost_work_2"):"";    // �����۾�DB_PK(2��)
        t_pk_cw[i][2] = (String)costReportItem.get("FK_cost_work_3")!=null?(String)costReportItem.get("FK_cost_work_3"):"";    // �����۾�DB_PK(3��)
        pass_state = (String)costReportItem.get("pass_type")!=null?(String)costReportItem.get("pass_type"):"";
        f_day = (String)costReportItem.get("f_day")!=null?(String)costReportItem.get("f_day"):"";
        Gr_day = (String)costReportItem.get("Gr_day")!=null?(String)costReportItem.get("Gr_day"):"";
        Leader_day = (String)costReportItem.get("Leader_day")!=null?(String)costReportItem.get("Leader_day"):"";
        Leader_name = (String)costReportItem.get("Leader_name")!=null?(String)costReportItem.get("Leader_name"):"";
        Gr_name = (String)costReportItem.get("Gr_name")!=null?(String)costReportItem.get("Gr_name"):"";
        JB_day = (String)costReportItem.get("JB_day")!=null?(String)costReportItem.get("JB_day"):"";
        JB_name = (String)costReportItem.get("JB_name")!=null?(String)costReportItem.get("JB_name"):"";
        p_leader_day = (String)costReportItem.get("p_leader_day")!=null?(String)costReportItem.get("p_leader_day"):"";
        p_leader_name = (String)costReportItem.get("p_leader_name")!=null?(String)costReportItem.get("p_leader_name"):"";
        /* if("".equals(p_leader_name)){
        	line_name = "��������";
        }else{
        	if("�赿��".equals(p_leader_name)){
        		line_name = "��������";
        	}else{
        		line_name = "��������";
        	}
        } */
        line_name = "��������";
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
        pk_cr_group = (String)costReportItem.get("pk_cr_group")!=null?(String)costReportItem.get("pk_cr_group"):"";    // ��û���׷��ȣ
        FK_cost_request_s = (String)costReportItem.get("FK_cost_request")!=null?(String)costReportItem.get("FK_cost_request"):"";    // ��û������Ʈ
        dev_step = (String)costReportItem.get("dev_step")!=null?(String)costReportItem.get("dev_step"):"";    // ���ߴܰ�
        pjt_name = (String)costReportItem.get("pjt_name")!=null?(String)costReportItem.get("pjt_name"):"";    // ProjectName
        pjt_no = (String)costReportItem.get("pjt_no")!=null?(String)costReportItem.get("pjt_no"):"";    // ProjectNo
        part_name[i] = (String)costReportItem.get("part_name")!=null?(String)costReportItem.get("part_name"):"";    // PartName
        team = (String)costReportItem.get("team")!=null?(String)costReportItem.get("team"):"";    // ���ߴ����
        f_name = (String)costReportItem.get("f_name")!=null?(String)costReportItem.get("f_name"):"";    // ���ߴ����
        a_name = (String)costReportItem.get("a_name")!=null?(String)costReportItem.get("a_name"):"";    // ���������
        request_txt = (String)costReportItem.get("request_txt")!=null?(String)costReportItem.get("request_txt"):"";    // ��û����
        car_type = (String)costReportItem.get("car_type")!=null?(String)costReportItem.get("car_type"):"";    // ��������
        customer_F = (String)costReportItem.get("customer_F")!=null?(String)costReportItem.get("customer_F"):"";    // 1������
        su_year_1[i] = (String)costReportItem.get("su_year_1")!=null?(String)costReportItem.get("su_year_1"):"";    // ��ȹ����-1����
        su_year_2[i] = (String)costReportItem.get("su_year_2")!=null?(String)costReportItem.get("su_year_2"):"";    // ��ȹ����-2����
        su_year_3[i] = (String)costReportItem.get("su_year_3")!=null?(String)costReportItem.get("su_year_3"):"";    // ��ȹ����-3����
        su_year_4[i] = (String)costReportItem.get("su_year_4")!=null?(String)costReportItem.get("su_year_4"):"";    // ��ȹ����-4����
        su_year_5[i] = (String)costReportItem.get("su_year_5")!=null?(String)costReportItem.get("su_year_5"):"";    // ��ȹ����-5����
        su_year_6[i] = (String)costReportItem.get("su_year_6")!=null?(String)costReportItem.get("su_year_6"):"";    // ��ȹ����-6����
        su_year_7[i] = (String)costReportItem.get("su_year_7")!=null?(String)costReportItem.get("su_year_7"):"";
        su_year_8[i] = (String)costReportItem.get("su_year_8")!=null?(String)costReportItem.get("su_year_8"):"";
        report_dest = (String)costReportItem.get("report_dest")!=null?(String)costReportItem.get("report_dest"):"";
        client_cost[i] = (String)costReportItem.get("client_cost")!=null?(String)costReportItem.get("client_cost"):"";    // ������������
        room_earn[i] = (String)costReportItem.get("room_earn")!=null?(String)costReportItem.get("room_earn"):"";    // ����
        pro_usage[i] = (String)costReportItem.get("pro_usage")!=null?(String)costReportItem.get("pro_usage"):"";    // ����U/S
        ket_cost[i] = (String)costReportItem.get("ket_cost")!=null?(String)costReportItem.get("ket_cost"):"";    // �ǸŸ�ǥ��
        target_cost[i] = (String)costReportItem.get("target_cost")!=null?(String)costReportItem.get("target_cost"):"";    // ��ǥ������
        lme_cu = (String)costReportItem.get("lme_cu")!=null?(String)costReportItem.get("lme_cu"):"";
        u_ex_rate = (String)costReportItem.get("u_ex_rate")!=null?(String)costReportItem.get("u_ex_rate"):"";
        pack_type = (String)costReportItem.get("pack_type")!=null?(String)costReportItem.get("pack_type"):"";    // ��������
        usage[i] = (String)costReportItem.get("usage")!=null?(String)costReportItem.get("usage"):"";    // usage
        make_type[i] = (String)costReportItem.get("make_type")!=null?(String)costReportItem.get("make_type"):"";    // ����ó
        pro_1 = (String)costReportItem.get("pro_1")!=null?(String)costReportItem.get("pro_1"):"";    // �系���λ���ó
        eff_value = (String)costReportItem.get("eff_value")!=null?(String)costReportItem.get("eff_value"):"";    // ȿ��
        t_actual_cost[i][0] = (String)costReportItem.get("actual_cost_1")!=null?(String)costReportItem.get("actual_cost_1"):"";    // �ѿ���
        t_earn_rate[i][0] = (String)costReportItem.get("earn_rate_1")!=null?(String)costReportItem.get("earn_rate_1"):"";    // ������
        t_actual_cost[i][1] = (String)costReportItem.get("actual_cost_2")!=null?(String)costReportItem.get("actual_cost_2"):"";    // �ѿ���
        t_earn_rate[i][1] = (String)costReportItem.get("earn_rate_2")!=null?(String)costReportItem.get("earn_rate_2"):"";    // ������
        t_actual_cost[i][2] = (String)costReportItem.get("actual_cost_3")!=null?(String)costReportItem.get("actual_cost_3"):"";    // �ѿ���
        t_earn_rate[i][2] = (String)costReportItem.get("earn_rate_3")!=null?(String)costReportItem.get("earn_rate_3"):"";    // ������
        actual_cost_sum_1 = (String)costReportItem.get("actual_cost_sum_1")!=null?(String)costReportItem.get("actual_cost_sum_1"):"";
        earn_rate_sum_1 = (String)costReportItem.get("earn_rate_sum_1")!=null?(String)costReportItem.get("earn_rate_sum_1"):"";
        actual_cost_sum_2 = (String)costReportItem.get("actual_cost_sum_2")!=null?(String)costReportItem.get("actual_cost_sum_2"):"";
        earn_rate_sum_2 = (String)costReportItem.get("earn_rate_sum_2")!=null?(String)costReportItem.get("earn_rate_sum_2"):"";
        actual_cost_sum_3 = (String)costReportItem.get("actual_cost_sum_3")!=null?(String)costReportItem.get("actual_cost_sum_3"):"";
        earn_rate_sum_3 = (String)costReportItem.get("earn_rate_sum_3")!=null?(String)costReportItem.get("earn_rate_sum_3"):"";
        mold_count[0] = (String)costReportItem.get("mold_count")!=null?(String)costReportItem.get("mold_count"):"";    // Mold����
        press_count[0] = (String)costReportItem.get("press_count")!=null?(String)costReportItem.get("press_count"):"";    // Press����
        line_count[0] = (String)costReportItem.get("line_count")!=null?(String)costReportItem.get("line_count"):"";    // ������������
        pack_count[0] = (String)costReportItem.get("pack_count")!=null?(String)costReportItem.get("pack_count"):"";    // �������
        repack_count[0] = (String)costReportItem.get("repack_count")!=null?(String)costReportItem.get("repack_count"):"";    // ȸ����ڽ�
        mold_total[0] = (String)costReportItem.get("mold_total_1")!=null?(String)costReportItem.get("mold_total_1"):"";    // Mold��
        press_total[0] = (String)costReportItem.get("press_total_1")!=null?(String)costReportItem.get("press_total_1"):"";    // Press��
        line_total[0] = (String)costReportItem.get("line_total_1")!=null?(String)costReportItem.get("line_total_1"):"";    // �������������
        pack_total[0] = (String)costReportItem.get("pack_total_1")!=null?(String)costReportItem.get("pack_total_1"):"";    // ���������
        mold_total[1] = (String)costReportItem.get("mold_total_2")!=null?(String)costReportItem.get("mold_total_2"):"";    // Mold��
        press_total[1] = (String)costReportItem.get("press_total_2")!=null?(String)costReportItem.get("press_total_2"):"";    // Press��
        line_total[1] = (String)costReportItem.get("line_total_2")!=null?(String)costReportItem.get("line_total_2"):"";    // �������������
        pack_total[1] = (String)costReportItem.get("pack_total_2")!=null?(String)costReportItem.get("pack_total_2"):"";    // ���������
        mold_total[2] = (String)costReportItem.get("mold_total_3")!=null?(String)costReportItem.get("mold_total_3"):"";    // Mold��
        press_total[2] = (String)costReportItem.get("press_total_3")!=null?(String)costReportItem.get("press_total_3"):"";    // Press��
        line_total[2] = (String)costReportItem.get("line_total_3")!=null?(String)costReportItem.get("line_total_3"):"";    // �������������
        pack_total[2] = (String)costReportItem.get("pack_total_3")!=null?(String)costReportItem.get("pack_total_3"):"";    // ���������
        repack_total[0] = (String)costReportItem.get("repack_total_1")!=null?(String)costReportItem.get("repack_total_1"):"";    // ȸ����ڽ���
        repack_total[1] = (String)costReportItem.get("repack_total_2")!=null?(String)costReportItem.get("repack_total_2"):"";    // ȸ����ڽ���
        repack_total[2] = (String)costReportItem.get("repack_total_3")!=null?(String)costReportItem.get("repack_total_3"):"";    // ȸ����ڽ���
        m_depr_stan = (String)costReportItem.get("m_depr_stan")!=null?(String)costReportItem.get("m_depr_stan"):"";    // Mold�󰢱���
        p_depr_stan = (String)costReportItem.get("p_depr_stan")!=null?(String)costReportItem.get("p_depr_stan"):"";    // Press�󰢱���
        L_depr_stan = (String)costReportItem.get("L_depr_stan")!=null?(String)costReportItem.get("L_depr_stan"):"";    // ������������󰢱���
        pack_depr_stan = (String)costReportItem.get("pack_depr_stan")!=null?(String)costReportItem.get("pack_depr_stan"):"";    // ��������󰢱���
        repack_depr_stan = (String)costReportItem.get("repack_depr_stan")!=null?(String)costReportItem.get("repack_depr_stan"):"";    // ȸ����ڽ��󰢱���
        total_sales_1 = (String)costReportItem.get("total_sales_1")!=null?(String)costReportItem.get("total_sales_1"):"";    // �����-1����
        total_sales_2 = (String)costReportItem.get("total_sales_2")!=null?(String)costReportItem.get("total_sales_2"):"";    // �����-2����
        total_sales_3 = (String)costReportItem.get("total_sales_3")!=null?(String)costReportItem.get("total_sales_3"):"";    // �����-3����
        total_sales_4 = (String)costReportItem.get("total_sales_4")!=null?(String)costReportItem.get("total_sales_4"):"";    // �����-4����
        total_sales_5 = (String)costReportItem.get("total_sales_5")!=null?(String)costReportItem.get("total_sales_5"):"";    // �����-5����
        total_sales_6 = (String)costReportItem.get("total_sales_6")!=null?(String)costReportItem.get("total_sales_6"):"";    // �����-6����
        total_sales_7 = (String)costReportItem.get("total_sales_7")!=null?(String)costReportItem.get("total_sales_7"):"";    // �����-7����
        total_sales_8 = (String)costReportItem.get("total_sales_8")!=null?(String)costReportItem.get("total_sales_8"):"";    // �����-8����
        profit_1 = (String)costReportItem.get("profit_1")!=null?(String)costReportItem.get("profit_1"):"";    // ��������-1����
        profit_2 = (String)costReportItem.get("profit_2")!=null?(String)costReportItem.get("profit_2"):"";    // ��������-2����
        profit_3 = (String)costReportItem.get("profit_3")!=null?(String)costReportItem.get("profit_3"):"";    // ��������-3����
        profit_4 = (String)costReportItem.get("profit_4")!=null?(String)costReportItem.get("profit_4"):"";    // ��������-4����
        profit_5 = (String)costReportItem.get("profit_5")!=null?(String)costReportItem.get("profit_5"):"";    // ��������-5����
        profit_6 = (String)costReportItem.get("profit_6")!=null?(String)costReportItem.get("profit_6"):"";    // ��������-6����
        profit_7 = (String)costReportItem.get("profit_7")!=null?(String)costReportItem.get("profit_7"):"";    // ��������-7����
        profit_8 = (String)costReportItem.get("profit_8")!=null?(String)costReportItem.get("profit_8"):"";    // ��������-8����
        per_profit_1 = (String)costReportItem.get("per_profit_1")!=null?(String)costReportItem.get("per_profit_1"):"";    // ����������-1����
        per_profit_2 = (String)costReportItem.get("per_profit_2")!=null?(String)costReportItem.get("per_profit_2"):"";    // ����������-2����
        per_profit_3 = (String)costReportItem.get("per_profit_3")!=null?(String)costReportItem.get("per_profit_3"):"";    // ����������-3����
        per_profit_4 = (String)costReportItem.get("per_profit_4")!=null?(String)costReportItem.get("per_profit_4"):"";    // ����������-4����
        per_profit_5 = (String)costReportItem.get("per_profit_5")!=null?(String)costReportItem.get("per_profit_5"):"";    // ����������-5����
        per_profit_6 = (String)costReportItem.get("per_profit_6")!=null?(String)costReportItem.get("per_profit_6"):"";    // ����������-6����
        per_profit_7 = (String)costReportItem.get("per_profit_7")!=null?(String)costReportItem.get("per_profit_7"):"";    // ����������-7����
        per_profit_8 = (String)costReportItem.get("per_profit_8")!=null?(String)costReportItem.get("per_profit_8"):"";    // ����������-8����
        su_stan_day = (String)costReportItem.get("su_stan_day")!=null?(String)costReportItem.get("su_stan_day"):"";    // �������ܰ�
        note_1 = (String)costReportItem.get("note_1")!=null?(String)costReportItem.get("note_1"):"";    // 2.���
        note_2 = (String)costReportItem.get("note_2")!=null?(String)costReportItem.get("note_2"):"";    // 3.���
        note_3 = (String)costReportItem.get("note_3")!=null?(String)costReportItem.get("note_3"):"";    // 4.���
        note_4 = (String)costReportItem.get("note_4")!=null?(String)costReportItem.get("note_4"):"";    // 5.���
        note_5 = (String)costReportItem.get("note_5")!=null?(String)costReportItem.get("note_5"):"";    // ����2��page�������ǹ��ǰ߶�
        note_5_1 = (String)costReportItem.get("note_5_1")!=null?(String)costReportItem.get("note_5_1"):"";    // ����2��page�������ǹ��ǰ߶�
        tocost_year = (String)costReportItem.get("tocost_year")!=null?(String)costReportItem.get("tocost_year"):"";
        file_1 = (String)costReportItem.get("file_1")!=null?(String)costReportItem.get("file_1"):"";
        file_2 = (String)costReportItem.get("file_2")!=null?(String)costReportItem.get("file_2"):"";
        file_3 = (String)costReportItem.get("file_3")!=null?(String)costReportItem.get("file_3"):"";
        file_4 = (String)costReportItem.get("file_4")!=null?(String)costReportItem.get("file_4"):"";
        file_5 = (String)costReportItem.get("file_5")!=null?(String)costReportItem.get("file_5"):"";
        file_6 = (String)costReportItem.get("file_6")!=null?(String)costReportItem.get("file_6"):"";
        file_2_name = (String)costReportItem.get("file_2_name")!=null?(String)costReportItem.get("file_2_name"):"";
        file_3_name = (String)costReportItem.get("file_3_name")!=null?(String)costReportItem.get("file_3_name"):"";
        file_4_name = (String)costReportItem.get("file_4_name")!=null?(String)costReportItem.get("file_4_name"):"";
        file_5_name = (String)costReportItem.get("file_5_name")!=null?(String)costReportItem.get("file_5_name"):"";
        file_6_name = (String)costReportItem.get("file_6_name")!=null?(String)costReportItem.get("file_6_name"):"";
        invest_cnt = (String)costReportItem.get("invest_cnt")!=null?(String)costReportItem.get("invest_cnt"):"";
        select_cost = (String)costReportItem.get("select_cost")!=null?(String)costReportItem.get("select_cost"):"";
        input_gb = (String)costReportItem.get("input_gb")!=null?(String)costReportItem.get("input_gb"):"";
        if(!dept_no.equals("11651") && !costAuth.equals("A") && !cost_id.equals("kimseok") && !cost_id.equals("yswgold") && !cost_id.equals("ketcolwj")   ){
            file_2 = "-";
            file_3 = "-";
            file_4 = "-";
            file_5 = "-";
            file_6 = "-";
            file_2_name = "";
            file_3_name = "";
            file_4_name = "";
            file_5_name = "";
            file_6_name = "";
        }
        if(StringUtil.checkNull(file_2).equals("")){
            file_2 = "-";
        }
        if(StringUtil.checkNull(file_3).equals("")){
            file_3 = "-";
        }
        if(StringUtil.checkNull(file_4).equals("")){
            file_4 = "-";
        }
        if(StringUtil.checkNull(file_5).equals("")){
            file_5 = "-";
        }
        if(StringUtil.checkNull(file_6).equals("")){
            file_6 = "-";
        }

        if(!"".equals(t_pk_cw[i][0])){
            if(!"".equals(chk_list)){
                chk_list = chk_list + "," + t_pk_cw[i][0];
            }else{
                chk_list = t_pk_cw[i][0];
            }
        }
        if(!"".equals(t_pk_cw[i][1])){
            if(!"".equals(chk_list)){
                chk_list = chk_list + "," + t_pk_cw[i][1];
            }else{
                chk_list =  t_pk_cw[i][1];
            }
            DB_case_2 = "ok";
        }
        if(!"".equals(t_pk_cw[i][2])){
            if(!"".equals(chk_list)){
                chk_list = chk_list + "," + t_pk_cw[i][2];
            }else{
                chk_list =  t_pk_cw[i][2];
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
        //���������� = (��������(�鸸��)�հ� / �����(�鸸��)�հ�) * 100    2013.09/04 ���μ� ���� ���� ���� ��û by Ȳ����
        sum_per_p = Double.toString(StringUtil.nullDouble(sum_profit)/StringUtil.nullDouble(sum_sales));
    }
    // ���ں��հ� 1��
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
    // ���ں��հ� 2��
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
    // ���ں��հ� 3��
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
    if(!StringUtil.checkNull(chk_list).equals("")){
        if(",".equals(StringUtil.checkNull(chk_list).substring(0,1))){
            chk_list = chk_list.substring(1);
        }
    }
    pk_cw_array = chk_list.split(",");
    int P_count = pk_cw_array.length;
    if(!"".equals(chk_list)){
        for(int m=0;m<P_count;m++){
            if(!pk_cw_array[m].equals("null")){
                if(!"".equals(StringUtil.checkNull(reportCtl.ketCostList(pk_cw_array[m])))){
                    ket_cost_value = "ok";
                }else{
                    ket_cost_value = "no";
                }
            }
        }
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>���߿��� �������</title>
<style type="text/css">
<!--
body {
    scrollbar-face-color: #CBDADF;
    scrollbar-highlight-color: #FFFFFF;
    scrollbar-3dlight-color: #E6E6E6;
    scrollbar-shadow-color: #E6E6E6;
    scrollbar-darkshadow-color: #F8F8F8;
    scrollbar-track-color: #F8F8F8;
    scrollbar-arrow-color: #E7EEF7;
    margin-top: 0px;
    margin-left: 0px;
}
-->
</style></head>

<link type="text/css" rel="stylesheet" href="/plm/jsp/cost/css/date_picker.css">
<script language="javascript" src="/plm/jsp/cost/js/date_picker.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/report_work.js"></script>
<script language="javascript">
 DP_InitPicker();
</script>
<script language="javascript">

function on_loads(){
    var file_2 = "<%=file_2%>";
    var file_3 = "<%=file_3%>";
    var file_4 = "<%=file_4%>";
    var file_5 = "<%=file_5%>";
    var file_6 = "<%=file_6%>";

    var src_2 = "";
    var src_3 = "";
    var src_4 = "";
    var src_5 = "";
    var src_6 = "";

    src_2 = ext_check(file_2);
    src_3 = ext_check(file_3);
    src_4 = ext_check(file_4);
    src_5 = ext_check(file_5);
    src_6 = ext_check(file_6);

    if(file_2 == "-"){
        document.getElementById("file_2_img").style.display = "none";
    }else{
        document.getElementById("file_2_img").src =src_2;
    }

    if(file_3 == "-"){
        document.getElementById("file_3_img").style.display = "none";
    }else{
        document.getElementById("file_3_img").src =src_3 ;
    }

    if(file_4 == "-"){
        document.getElementById("file_4_img").style.display = "none";
    }else{
        document.getElementById("file_4_img").src =src_4;
    }

    if(file_5 == "-"){
        document.getElementById("file_5_img").style.display = "none";
    }else{
        document.getElementById("file_5_img").src =src_5 ;
    }


    if(file_6 == "-"){
        document.getElementById("file_6_img").style.display = "none";
    }else{
        document.getElementById("file_6_img").src =src_6;
    }
    var select_cost = "<%=select_cost%>";
    select_cost_gijun(select_cost);
}


function ext_check(file){
    var src = "";
    if(file.indexOf(".ppt") > -1 || file.indexOf(".PPT") > -1) {
        src = "/plm/jsp/cost/images/common/powerpoint.png";
    }

    if(file.indexOf(".xls") > -1 || file.indexOf(".XLS") > -1 || file.indexOf(".xlsx") > -1 || file.indexOf(".XLSX") > -1) {
        src = "/plm/jsp/cost/images/common/excel.png";
    }

    return src;
}

function show_file(file_path,file_type){

    var src =  "";

    src = ext_check(file_path);
    if(src != ""){
        if(file_type == "file_2"){
            document.getElementById("file_2_img").src =src;
            document.getElementById("file_2_img").style.display = "block";
            document.getElementById("file_2").value = file_path;
        }
        if(file_type == "file_3"){
            document.getElementById("file_3_img").style.display = "block";
            document.getElementById("file_3_img").src =src;
            document.getElementById("file_3").value = file_path;
        }
        if(file_type == "file_4"){
            document.getElementById("file_4_img").style.display = "block";
            document.getElementById("file_4_img").src =src;
            document.getElementById("file_4").value = file_path;
        }
        if(file_type == "file_5"){
            document.getElementById("file_5_img").style.display = "block";
            document.getElementById("file_5_img").src =src;
            document.getElementById("file_5").value = file_path;
        }
        if(file_type == "file_6"){
            document.getElementById("file_6_img").style.display = "block";
            document.getElementById("file_6_img").src =src;
            document.getElementById("file_6").value = file_path;
        }
    }else{
        if(file_type == "file_2"){
            document.getElementById("file_2_img").style.display = "none";
        }
        if(file_type == "file_3"){
            document.getElementById("file_3_img").style.display = "none";
        }
        if(file_type == "file_4"){
            document.getElementById("file_4_img").style.display = "none";
        }
        if(file_type == "file_5"){
            document.getElementById("file_5_img").style.display = "none";
        }
        if(file_type == "file_6"){
            document.getElementById("file_6_img").style.display = "none";
        }
    }
}

function file_open(file_name)
{

    var file_name = document.getElementById(file_name).value;

    file_name = file_name.replace("Upload\\cost_file\\����\\","");
    file_name = file_name.replace("D:Uploadcost_file����","");

    file_name = escape(encodeURIComponent(file_name));
    var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=work";
    window.open(url);
}
 function print_call()
 {
      window.open("/plm/jsp/cost/costreport/cost_report_1_print.jsp?visitor=<%=visitor%>&pk_cr_group=<%=pk_cr_group%>&cost_report_add=<%=cost_report_add%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>", "pop_300", "width=960,height=700,scrollbars=yes,resizable=no");
 }
 function excel_call()
 {
      window.open("./cost_report_1_excel.asp?visitor=<%=visitor%>&pk_cr_group=<%=pk_cr_group%>&cost_report_add=<%=cost_report_add%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>", "pop_300", "width=960,height=700,scrollbars=yes,resizable=no");
 }

 function back_call()
 {
     <%if("3".equals(visitor) || "4".equals(visitor) || position.equals("������") || position.equals("��������") || position.equals("����")){%>
         this.close();
     <%}else{%>
         window.location.href ="/plm/jsp/cost/index.html?select_name="+'<%=select_name%>';
      <%}%>
 }

   /**********************************************
  ����2
 **********************************************/
  function report_call()
 {
    document.part_1.action = "/plm/jsp/cost/costreport/cost_report_2.jsp?visitor=<%=visitor%>&pk_cr_group=<%=pk_cr_group%>&cost_report_add=<%=cost_report_add%>&chk_list=<%=chk_list%>&report_pk=<%=report_pk%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>";
    document.part_1.submit();
 }

  /**********************************************
  ��û�� ����
 **********************************************/
  function request_call()
 {
    window.open("/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=main&group_case_count=<%=user_case_count%>", "pop_999", "width=1024,height=800,scrollbars=yes,resizable=yes");
}
  /**********************************************
    �����û-������
    **********************************************/
    function pass_call_1(){
        window.open("/plm/jsp/cost/costreport/work_pass.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>", "window_2", "width=262,height=210,scrollbars=no");
    }
    /**********************************************
     ����-����
    **********************************************/
    function pass_call_2(){
        window.open("/plm/jsp/cost/costreport/work_login.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&pass_type=ok_1&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>", "window_2", "width=262,height=210,scrollbars=no");
    }
    /**********************************************
    ����-��Ÿ��
    **********************************************/
    function pass_call_3(){
        window.open("/plm/jsp/cost/costreport/work_login.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&step_no=<%=step_no%>&team=<%=team%>&report_pk=<%=report_pk%>&pass_type=ok&rev_no=<%=rev_no%>&user_case_count=<%=user_case_count%>", "window_2", "width=262,height=210,scrollbars=no");
    }

    /**********************************************
    ���� - ������
    **********************************************/
    function pass_call_5(pass_type){
        /*var JB_day			= document.part_1.JB_day.value;
        var p_leader_day	= document.part_1.p_leader_day.value;
        var r_owner_day	= document.part_1.r_owner_day.value;
        var r_pre_day		= document.part_1.r_pre_day.value;*/
        var note_4				= document.part_1.note_4.value;
        var pass_type = pass_type;
        if(pass_type == "2"){
            step_no = "5.1";
        }else if(pass_type == "3"){
            step_no = "5.2";
        }else if(pass_type == "4"){
            step_no = "6";
        }
        window.open("/plm/jsp/cost/costreport/report_approval.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&note_4="+note_4+"&step_no="+step_no+"&pass_type="+pass_type ,"window_2", "width=262,height=210,scrollbars=no");
    }

    /**********************************************
    ��������
    **********************************************/
    function pass_call_4(pass_type){
        /*var JB_day            = document.part_1.JB_day.value;
        var p_leader_day    = document.part_1.p_leader_day.value;
        var r_owner_day = document.part_1.r_owner_day.value;*/
        var r_pre_day       = document.part_1.r_pre_day.value;
        var note_4          = document.part_1.note_4.value;
        if(r_pre_day == null || r_pre_day == ""){
            alert("��¥�� �Է��ϼ���.");
        }else{
            document.part_1.action = "/plm/jsp/cost/costreport/work_finish.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&step_no="+'<%=step_no%>'+"&team="+'<%=team%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>'+"&pass_type="+pass_type+"&r_pre_day="+r_pre_day+"&note_4="+note_4;

            document.part_1.submit();
        }
    }

 /**********************************************
PLM URL Uadate
**********************************************/
  function Urlcall()
 {
       window.open("/plm/jsp/cost/common/url_data.jsp?call_page=url_update&pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>&pjt_no=<%=pjt_no%>&rev_no=<%=rev_no%>", "window_2", "width=262,height=150,scrollbars=no");
 }
  /**********************************************
 * ��ϵ� ���Ϻ���
 **********************************************/
function file_call1()
{

    var file_name = '<%=file_1%>';
    file_name = file_name.replace("Uploadcost_file����","");
    file_name = escape(encodeURIComponent(file_name));
    var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=work";
    window.open(url);
}

function filecheck_file(file){
    var report_pk ='<%=report_pk%>';
    popUpOpen("/plm/jsp/cost/common/file_add.jsp?file_type="+file+"&report_pk="+report_pk+"&page_name=report", "file_pop", 470, 120);
}


/* �Ϲ��˾��� �߾ӿ� ��ġ���� �Ҷ�  */
function popUpOpen(url, title, width, height)
{
    if (title == '') title = 'Popup_Open';
        if (width == '') width = 540;
        if (height == '') height = 500;
        var left = "";
        var top = "";

    //ȭ�� ����� ��ġ
            var dim = new Array(2);

    dim = CenterWindow(height,width);
    top = dim[0];
    left = dim[1];

    var toolbar = 'no';
    var menubar = 'no';
    var status = 'no';
    var scrollbars = 'no';
    var resizable = 'no';
    var code_search = window.open(url, title, 'left='+left+', top='+top+',width='+width+',height='+height+', toolbar='+toolbar+', menubar='+menubar+', status='+status+', scrollbars='+scrollbars+', resizable='+resizable);
    code_search.focus();
    return code_search;
}

function CenterWindow(height,width)
{
    var outx = screen.height;
    var outy = screen.width;
    var x = (outx - height)/2;
    var y = (outy - width)/2;
    dim = new Array(2);
    dim[0] = x;
    dim[1] = y;

    return  dim;
}
/**
* �ؽ�Ʈ ���� �κ��� ���� ����
*/
String.prototype.rightSpaceQuantity = function()
{
var count = 0;

for( i = this.length - 1; i >= 0; i-- )
{
if( this.charAt(i) == '\n' )
count++;
else
break;
}

return count;
}



function DB_call(){

    var report_pk 	= document.part_1.report_pk.value;
    var note_4		=  document.part_1.note_4.value;
    var note_4_hidden = document.part_1.note_4_hidden.value;
    var start = note_4_hidden.length;
    var end   = note_4.length;
    note_4 = note_4.substr(start-(1+note_4.rightSpaceQuantity()),end);

    note_4 = escape(encodeURIComponent(note_4));


    popUpOpen("/plm/jsp/cost/costreport/updateNote4.jsp?note_4="+note_4+"&report_pk="+report_pk+"&pk_cr_group="+'<%=pk_cr_group%>'+"&table_row="+'<%=table_row%>'+"&report_pk="+'<%=report_pk%>'+"&rev_no="+'<%=rev_no%>'+"&user_case_count="+'<%=user_case_count%>', "file_pop", 470, 120);
}

function invest_call(){
    var report_pk = '<%=report_pk%>';
    window.open("/plm/jsp/cost/costreport/cost_invest_view.jsp?report_pk="
            + report_pk, "pop_999",
            "width=1024,height=800,scrollbars=yes,resizable=yes");
}



</script>
<style type="text/css">
<!--
BODY
A:link { text-decoration:none; color:#8A8A8A}
A:visited { text-decoration:underline; color:#5E5E5E}
A:active { text-decoration:none; color:#5E5E5E}
A:hover { text-decoration:underline; color:#8A8A8A}

A.txt:link { text-decoration:none; color:#3F6FA3}
A.txt:visited { text-decoration:none; color:#3F6FA3}
A.txt:active { text-decoration:none; color:#3F6FA3}
A.txt:hover { text-decoration:none; color:#8A8A8A}

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
.style10 {font-size: 24px; font-weight: bold;color:#4F4F4F;text-align:center;}
.style11 {font-size: 12px;text-align:right; font-weight: bold;color:#FF0033}
.style12 {font-size: 12px;font-weight: bold; text-align:right}
.style13 {font-size: 12px;font-weight: bold; text-align:right; color:#0000FF}
.style14 {font-size: 12px; background-color:#FFFFFF; text-align: center; font-weight: bold; color:#333333}
.style15 {font-size: 14px;font-weight:bold;text-align:center; color:#66A014}
.style16 {font-size: 12px;text-align:center; color:#8A8A8A}
.style17 {font-size: 18px;font-weight:bold;text-align:center; color:#FF0000; font-family:"����"}
.style18 {font-size: 14px; font-weight: bold;color:#4F4F4F;text-align:center;}
.style19 {font-size: 11px; background-color:#ffff80; text-align:center; font-weight: bold; color:#333333}
.line_name {font-size: 4px; color:#EFEFEF; text-align:center}
#bg_color {background:#E6E6E6}
#not_Data {background:#FFDFDF}
#font_co {color:#CCCCCC}


-->
</style>

<body onload = "javascript:on_loads();">
<Form method="post" name="part_1" >
    <input name="approval" 		type="hidden" value="<%//=approval%>">
    <input name="step_no" 		type="hidden" value="<%=step_no%>">
    <input name="note_5" 		type="hidden" value="<%=note_5%>">
    <input name="note_5_1" 		type="hidden" value="<%=note_5_1%>">
    <input name="note_4_hidden" 		type="hidden" value="<%=note_4%>">
    <input name="t_note_1" 		type="hidden" value="<%//=t_note_1%>">
    <input name="t_note_2" 		type="hidden" value="<%//=t_note_2%>">
    <input name="t_note_3" 		type="hidden" value="<%//=t_note_3%>">
    <input name="t_note_4" 		type="hidden" value="<%//=t_note_4%>">
    <input name="report_pk" 		type="hidden" value="<%=report_pk%>">
    <input name="DB_case_2" 	type="hidden" value="<%=DB_case_2%>">
    <input name="select_name" type="hidden" value="<%=select_name%>">
    <input name="file_2" type="hidden" value="<%=file_2 %>">
    <input name="file_3" type="hidden" value="<%=file_3 %>">
    <input name="file_4" type="hidden" value="<%=file_4 %>">
    <input name="file_5" type="hidden" value="<%=file_5 %>">
    <input name="file_6" type="hidden" value="<%=file_6 %>">

    <table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <table width="1022" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="left" valign="bottom"><img src="/plm/jsp/cost/acc_img/tap_report_1_big.gif" border="0"><%if(!"1".equals(input_gb)){//���������� ���� �����Է��� ��� �����ټ����� 2014-10-09 Ȳ����%><img src="/plm/jsp/cost/acc_img/tap_report_2_small.gif" width="102" height="16" border="0" onClick="report_call();" style="cursor:pointer;"/><%} %></td>
                        <td align="right" valign="middle"><img src="/plm/jsp/cost/acc_img/btn_back.gif" border="0" onClick="back_call();" style="cursor:pointer;"/></td>
                    </tr>
                    <tr>
                        <td bgcolor="#00455d" height="3"></td>
                        <td bgcolor="#00455d" height="3"></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table width="957" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td height="50" colspan="3" class="style8">
                            <table width="1022" border="0" cellpadding="0" cellspacing="1" bgcolor="#999999">
                                <tr>
                                    <td width="1022" bgcolor="#FFFFFF">
                                        <table width="1020" border="0" align="center" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td height="5" colspan="4" class="style1">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td width="173" height="99">
                                                    <table width="153" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                        <tr>
                                                            <td height="21" colspan="2" class="style5">�� �� �� ��</td>
                                                        </tr>
                                                        <tr>
                                                            <td width="74" height="21" bgcolor="#FFFFFF" class="style1">
                                                            	<%	
                                                            	out.println(StringUtil.ChangeDeptNoCPString(team, ""));
                                                            	/*
                                                            	if("1".equals(team)){
																		out.println("Ŀ���Ϳ�������1��");
																	}else if("11".equals(team)){
																		out.println("Ŀ���Ϳ�������2��");
																	}else if("12".equals(team)){
																		out.println("Ŀ���Ϳ�������3��");
																	}else if("13".equals(team)){
																		out.println("Ŀ���Ϳ������߼�Ÿ");
																	}else if("22".equals(team)){
																		out.println("�����⿬������1��");
																	}else if("23".equals(team)){
																		out.println("�����⿬������2��");
																	}else if("24".equals(team)){
																		out.println("�����⿬������3��");
																	}else if("3".equals(team)){
																		out.println("��������3��");
																	}else if("4".equals(team)){
																		out.println("��������4��");
																	}else if("6".equals(team)){
																		out.println("��������6��");
																	}else if("21".equals(team)){
																		out.println("���۰���1��");
																	}*/
																%>
                                                            </td>
                                                            <td width="76" height="21" bgcolor="#FFFFFF" class="style1"><%=f_name %></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1">�ڵ���������</td>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1"><%=a_name %></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="279" class="style10">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td  width="279" class="style10">���� ���� ��� ����</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="style10"><font size="3"><%if("��������".equals(dev_step)){out.println("�� ��������/������");}else{out.println("�� "+dev_step);} %></font></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="122">
                                                    <table width="106" border="1" cellspacing="0" cellpadding="0" bordercolor="#FF0000">
                                                        <tr>
                                                            <td height="50" class="style17">
                                                                <table width="60" border="0" cellspacing="1" cellpadding="0">
                                                                    <tr>
                                                                        <td class="style17">��ܺ�</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="style17">������</td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="446" valign="baseline" class="style8">
                                                    <table width="333" height="58" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                        <tr>
                                                            <td width="25" rowspan="2" class="style5">��<br>��</td>
                                                            <td width="55" height="18" bgcolor="#FFFFFF" class="style1">���</td>
                                                            <td width="55" height="18" bgcolor="#FFFFFF" class="style1">����</td>
                                                            <td width="57" height="19" bgcolor="#FFFFFF" class="style1">��Ÿ��</td>
                                                            <td width="59" bgcolor="#FFFFFF" class="style1"><%=line_name %></td>
                                                            <td width="59" height="18" bgcolor="#FFFFFF" class="style1">����</td>
                                                            <td width="55" height="18" bgcolor="#FFFFFF" class="style1">ȸ��</td>
                                                        </tr>
                                                        <tr>
                                                                <td height="53" bgcolor="#FFFFFF" class="style1">
                                                                    <%if(step_no.equals("2")){ %>
                                                                        <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" onClick="pass_call_1();" style="cursor:pointer;"/>
                                                                    <%}else{
                                                                            if(!StringUtil.checkNull(f_day).equals("")){
                                                                                out.println(w_name+"<br>"+f_day.substring(0,4)+"<br>"+f_day.substring(5,10));
                                                                            }
                                                                         }
                                                                    %>
                                                                </td>
                                                                <td  bgcolor="#FFFFFF" class="style1">
                                                                    <%if(step_no.equals("3") && (position.equals("����") || login_id.equals("admin"))){%>
                                                                    <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" onClick="pass_call_2();" style="cursor:pointer;"/>
                                                                    <%}else if(step_no.equals("2")){out.println("");}else{if(!StringUtil.checkNull(Gr_day).equals("")){out.println(Gr_name+"<br>"+Gr_day.substring(0,4)+"<br>"+Gr_day.substring(5,10));}} %>
                                                                </td>

                                                                <td bgcolor="#FFFFFF" class="style1">
                                                                <%if(step_no.equals("4") && (position.equals("������") || login_id.equals("admin"))){%>
                                                                    <img src="/plm/jsp/cost/acc_img/btn_pass_call_3.gif" border="0" onClick="pass_call_3();" style="cursor:pointer;"/>
                                                                <%}else if(step_no.equals("2") || step_no.equals("3")){out.println("");}else{if(!StringUtil.checkNull(Leader_day).equals("")){out.println(Leader_name+"<br>"+Leader_day.substring(0,4)+"<br>"+Leader_day.substring(5,10));}} %>
                                                                </td>


                                                                <!-- ��������  ���� -->
                                                                <td bgcolor="#FFFFFF" class="style1">
                                                                    <%if(step_no.equals("5.1") && (position.equals("��������") || login_id.equals("admin"))){%>
                                                                            <!-- <input name="p_leader_day" type="text" size="5" value="<%=p_leader_day%>" class="DateInput" autocomplete="off" onBlur="DP_PickerInput_blur()">-->
                                                                            <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" onClick="pass_call_5(3);"style="cursor:pointer;"/>
                                                                    <%}else if(step_no.equals("2") || step_no.equals("3") || step_no.equals("4") || step_no.equals("5")){
                                                                            out.println("");
                                                                         }else if(!p_leader_day.equals("")){
                                                                            out.println(p_leader_name+"<br>"+p_leader_day.substring(0,4)+"<br>"+p_leader_day.substring(5,10));
                                                                         }
                                                                    %>
                                                                </td>

                                                                <!-- ����� ���� -->
                                                                <td  bgcolor="#FFFFFF" class="style1">
                                                                <%if(step_no.equals("5.2") && (position.equals("����") || login_id.equals("admin"))){%>
                                                                        <!-- <input name="r_owner_day" type="text" size="5" value="<%=r_owner_day%>" class="DateInput" autocomplete="off" onBlur="DP_PickerInput_blur()">-->
                                                                        <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" onClick="pass_call_5(4);"style="cursor:pointer;"/>
                                                                <%}else if( step_no.equals("2") || step_no.equals("3") || step_no.equals("4") || step_no.equals("5") || step_no.equals("5.1")){
                                                                         out.println("");
                                                                     }else if(!r_owner_day.equals("")){
                                                                        out.println("�̿���<br>"+r_owner_day.substring(0,4)+"<br>"+r_owner_day.substring(5,10));
                                                                       }
                                                                  %>
                                                                  </td>

                                                                  <td <% if(r_pre_day.equals("") && step_no.equals("6") && dev_step.equals("���߰���") ){ %>background="/plm/jsp/cost/acc_img/line.jpg" <% }%> bgcolor="#FFFFFF" class="style1">
                                                                    <%if(!r_pre_day.equals("")){
                                                                            out.println("��â��<br>"+r_pre_day.substring(0,4)+"<br>"+r_pre_day.substring(5,10 ));
                                                                        }else if(step_no.equals("2") || step_no.equals("3") || step_no.equals("4") || step_no.equals("5") || step_no.equals("5.1") || step_no.equals("5.2") ){
                                                                            out.println("");
                                                                        }else if(r_pre_day.equals("") && step_no.equals("6") && !dev_step.equals("���߰���") && (login_id.equals("admin") || login_id.equals("pro_man"))){
                                                                    %>
                                                                     <input name="r_pre_day" type="text" size="5" value="<%=r_pre_day%>" class="DateInput" autocomplete="off" onBlur="DP_PickerInput_blur()">
                                                                     <br>
                                                                     <img src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="pass_call_4('5');" style="cursor:pointer;"/>

                                                                     <%} %>
                                                                </td>

                                                            </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td height="197" colspan="4">
                                                    <table width="1020" border="0" cellpadding="0" cellspacing="1">
                                                        <tr>
                                                            <td height="22" colspan="8" valign="bottom" bgcolor="#FFFFFF" class="style3">1. ���߹�� �� ���ں� ��Ȳ
                                                            <%if(!file_1.equals("-") && !file_1.equals("")){%>&nbsp;&nbsp;<font color="#0000ff" size="+3">��÷�ε� ��������(<img src="/plm/jsp/cost/acc_img/file.png" width="16" height="16" border="0" onClick="file_call1();"  style="cursor:pointer;"/>)</font><%}%>

                                                            </td>
                                                            <td width="128" height="18" valign="bottom" bgcolor="#FFFFFF" class="style6">�ۼ��� :
                                                            <%if(!StringUtil.checkNull(f_day).equals("")){
                                                                        out.println(f_day.substring(0,10));
                                                                    }
                                                            %>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <table width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                        <tr>
                                                            <td width="108" height="21" rowspan="2" class="style5">�����</td>
                                                            <td width="293" height="21" rowspan="2" bgcolor="#FFFFFF" class="style1"><%=customer_F %></td>
                                                            <td width="90" rowspan="9" class="style5">�ű� ���ں�<br />
                                                          �� �� �� Ȳ<br /></td>
                                                            <td width="87" height="21" rowspan="2" class="style5">����</td>
                                                            <td width="53" height="21" rowspan="2" class="style5">����</td>
                                                            <td width="83" height="21" class="style5">1��</td>
                                                            <td width="83" height="21" class="style5">2��</td>
                                                            <td width="83" height="21" class="style5">3��</td>
                                                            <!--  <td width="59" height="21" class="style5">4��</td>
                                                            <td width="59" height="21" class="style5">5��</td>-->
                                                            <td width="62" height="21" rowspan="2" class="style5">�󰢱���</td>
                                                            <td width="67" rowspan="7" class="style5">���ں�<br/>
                                                          ȸ���Ⱓ</td>
                                                        </tr>
                                                        <tr>
                                                            <td width="83" height="18" class="style5"><%=case_to_note_1%></td>
                                                            <td width="83" height="18" class="style5"><%=case_to_note_2%></td>
                                                            <td width="83" height="18" class="style5"><%=case_to_note_3%></td>
                                                            <!--<td width="59" height="18" class="style5"><%//=case_to_note_4%></td>
                                                            <td width="59" height="18" class="style5"><%//=case_to_note_5%></td>-->
                                                        </tr>
                                                        <tr>
                                                            <td width="108" height="21" class="style5">��ǰ��</td>
                                                            <td width="293" height="21" bgcolor="#FFFFFF" class="style1"><%=pjt_name%></td>
                                                            <td width="87" height="21" bgcolor="#FFFFFF" class="style1">Mold</td>
                                                            <td width="53" height="21" bgcolor="#FFFFFF" class="style1"><%=mold_count[0]%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(mold_total[0], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(mold_total[1], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(mold_total[2], "õ��", 0, 0)%></td>
                                                            <!--  <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=mold_total[3]%></td>
                                                            <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=mold_total[4]%></td>-->
                                                            <td width="62" height="21" bgcolor="#FFFFFF" class="style1"><%=m_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="108" height="21" class="style5">Project No. </td>
                                                            <td width="293" height="21" bgcolor="#FFFFFF" class="style1"><%=pjt_no%></td>
                                                            <td width="87" height="21" bgcolor="#FFFFFF" class="style1">Press</td>
                                                            <td width="53" height="21" bgcolor="#FFFFFF" class="style1"><%=press_count[0]%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(press_total[0], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(press_total[1], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(press_total[2], "õ��", 0, 0)%></td>
                                                            <!--<td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=press_total[3]%></td>
                                                            <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=press_total[4]%></td>-->
                                                            <td width="62" height="21" bgcolor="#FFFFFF" class="style1"><%=p_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="108" height="20" class="style5">��������</td>
                                                            <td width="293" height="20" bgcolor="#FFFFFF" class="style1"><%=car_type%></td>
                                                            <td width="87" height="20" bgcolor="#FFFFFF" class="style1">�������� </td>
                                                            <td width="53" height="20" bgcolor="#FFFFFF" class="style1"><%=line_count[0]%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(line_total[0], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(line_total[1], "õ��", 0, 0)%></td>
                                                            <td width="83" height="20" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(line_total[2], "õ��", 0, 0)%></td>
                                                            <!-- <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=line_total[3]%></td>
                                                            <td width="59" height="20" bgcolor="#FFFFFF" class="style1"><%//=line_total[4]%></td> -->
                                                            <td width="62" height="20" bgcolor="#FFFFFF" class="style1"><%=L_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="108" height="21" class="style5">�������</td>
                                                            <td width="293" height="21" bgcolor="#FFFFFF" class="style1"><%=app_part%></td>
                                                            <td width="87" height="21" bgcolor="#FFFFFF" class="style1">��Ÿ ���ں� </td>
                                                            <td width="53" height="21" bgcolor="#FFFFFF" class="style1"><%=pack_count[0]%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(pack_total[0], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(pack_total[1], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(pack_total[2], "õ��", 0, 0)%></td>
                                                            <!-- <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=pack_total[3]%></td>
                                                            <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=pack_total[4]%></td> -->
                                                            <td width="62" height="21" bgcolor="#FFFFFF" class="style1"><%=pack_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="108" height="21" class="style5">�����帧</td>
                                                            <td width="293" height="21" bgcolor="#FFFFFF" class="style1"><%=report_dest%></td>
                                                            <td width="87" height="21" bgcolor="#FFFFFF" class="style1">����� ���ں�</td>
                                                            <td width="53" height="21" bgcolor="#FFFFFF" class="style1"><%=repack_count[0]%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(repack_total[0], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(repack_total[1], "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(repack_total[2], "õ��", 0, 0)%></td>
                                                            <!-- <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=repack_total[3]%></td>
                                                            <td width="59" height="21" bgcolor="#FFFFFF" class="style1"><%//=repack_total[4]%></td> -->
                                                            <td width="62" height="21" bgcolor="#FFFFFF" class="style1"><%=repack_depr_stan%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="108" rowspan="2" class="style5">�������</td>
                                                            <td width="293" rowspan="2" bgcolor="#FFFFFF" class="style1"><%=request_txt%></td>
                                                            <td width="87" height="21" bgcolor="#FFFFFF" class="style1">�� ��</td>
                                                            <td width="53" height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style12"><%=StringUtil.DoubleFormat(AA, "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style12"><%=StringUtil.DoubleFormat(AB, "õ��", 0, 0)%></td>
                                                            <td width="83" height="21" bgcolor="#FFFFFF" class="style12"><%=StringUtil.DoubleFormat(AC, "õ��", 0, 0)%></td>
                                                            <td width="62" height="21" bgcolor="#FFFFFF" class="style12"></td>
                                                            <!-- <td width="59" height="21" bgcolor="#FFFFFF" class="style12"></td>
                                                            <td width="59" height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td> -->
                                                            <td width="67" height="21" bgcolor="#FFFFFF" class="style14"><%=tocost_year%>  ��</td>
                                                        </tr>
                                                    </table>
                                                    <table width="1020" border="0" cellpadding="0" cellspacing="1">
                                                        <tr>
                                                            <td height="21" colspan="13" valign="bottom" class="style3">2. ���� ���� �� ȯ��, ����� ���� ���� (<%=select_cost %>�ȱ���)</td>
                                                        </tr>
                                                    </table>
                                                    <table width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                        <tr>
                                                            <td width="121" height="21" class="style5">����</td>
                                                            <td width="45" height="21" class="style5">1����</td>
                                                            <td width="45" height="21" class="style5">2����</td>
                                                            <td width="45" height="21" class="style5">3����</td>
                                                            <td width="45" height="21" class="style5">4����</td>
                                                            <td width="45" height="21" class="style5">5����</td>
                                                            <td width="45" height="21" class="style5">6����</td>
                                                            <td width="45" class="style5">7����</td>
                                                            <td width="45" class="style5">8����</td>
                                                            <td width="45" height="21" class="style5">�հ�</td>
                                                            <td width="112" height="21" class="style5">���� ���ܰ� </td>
                                                            <td width="108" height="21" bgcolor="#FFFFFF" class="style1"><%=su_stan_day%></td>
                                                            <td width="260" height="21" class="style5">���</td>
                                                        </tr>
                                                        <tr>
                                                            <td width="121" height="21" rowspan="2" class="style5">�Ǹŷ�(õ��)</td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_1[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_2[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_3[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_4[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_5[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_6[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_7[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(su_year_8[0], "", 0, 0)%></td>
                                                            <td width="45" height="21" rowspan="2" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(avg_su[0], "", 0, 0)%></td>
                                                            <td height="21" rowspan="2" class="style5">��ö LME�ü� </td>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1"><%=lme_cu%><span class="style7"> [��/Ton]</span></td>
                                                            <td rowspan="5" bgcolor="#FFFFFF" class="style1"><textarea name="note_1" cols="40" rows="9" class="style9" readonly><%=note_1%></textarea></td>
                                                        </tr>
                                                        <tr>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1"><%if(!"".equals(u_ex_rate)){out.println(u_ex_rate+"<span class='style7'>[��/$]</span>");} %></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="121" height="21" class="style5">�����(�鸸��)</td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_1, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_2, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_3, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_4, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_5, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_6, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_7, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(total_sales_8, "", 0, 0)%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style8"><%=StringUtil.DoubleFormat(sum_sales, "", 0, 0)%></td>
                                                            <td height="21" class="style5">��������</td>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1"><%=pack_type%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="121" height="21" class="style5">��������(�鸸��)</td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_1.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_1, 0)));}%></td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_2.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_2, 0)));}%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_3.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_3, 0)));}%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_4)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_4.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_4, 0)));}%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_5)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_5.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_5, 0)));}%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_6)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_6.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_6, 0)));}%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_7)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_7.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_7, 0)));}%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(profit_8)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%if(!profit_8.equals("0")){out.println(Double.parseDouble(StringUtil.Roundformat(profit_8, 0)));}%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(sum_profit)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(sum_profit, "", 0, 0)%>
                                                            </td>
                                                            <td height="21" class="style5">����ó ���� </td>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1"><%=pro_1%></td>
                                                        </tr>
                                                        <tr>
                                                            <td width="121" height="21" class="style5">����������</td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_1, "%", 100, 1)%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_2, "%", 100, 1)%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_3, "%", 100, 1)%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_4)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_4, "%", 100, 1)%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_5)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_5, "%", 100, 1)%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_6)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_6, "%", 100, 1)%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_7)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_7, "%", 100, 1)%>
                                                            </td>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(per_profit_8)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(per_profit_8, "%", 100, 1)%>
                                                            </td>
                                                            <%if(report_pk.equals("1780")){ %>
                                                            <td width="45" height="21" bgcolor="#FFFFFF" class="style13">20.7 %</td>
                                                            <%}else{ %>
                                                            <td width="45" height="21" bgcolor="#FFFFFF"
                                                            <%if(Double.parseDouble(StringUtil.checkNullZero(sum_per_p)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                            <%=StringUtil.DoubleFormat(sum_per_p, "%", 100, 1)%>
                                                            </td>
                                                            <%} %>

                                                            <td height="21" class="style5">���� ȿ�� </td>
                                                            <td height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(eff_value, "%", 100, 0)%></td>
                                                        </tr>
                                                    </table>
                                                    <table width="1020" border="0" cellpadding="0" cellspacing="1">
                                                            <tr>
                                                                <td height="21" colspan="12" valign="bottom" class="style3">3. ������� ��� </td>
                                                                <td width="52" height="21" valign="bottom" class="style3">����: ��</td>
                                                            </tr>
                                                        </table>
                                                        <table width="1020" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                            <tr>
                                                                <td width="19" rowspan="<%=table_row + 4%>" class="style5">��<br/>��<br/>��<br/>��</td>
                                                                <td width="291" rowspan="2" class="style5">��ǰ��</td>
                                                                <td width="35" rowspan="2" class="style5">����<br>U/S</td>
                                                                <td width="68" rowspan="2" class="style5">�Ǹ�<br/>��ǥ��</td>
                                                                <td width="40" rowspan="2" class="style5">��ǥ<br/>������</td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change">1��<br><input name="case_1_note" class="style1" size="16" value="<%=case_1_note%>" readonly></span></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change_1">2��<br><input name="case_2_note" class="style1" size="16" value="<%=case_2_note%>" readonly></span></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5"><span class="change_2">3��<br><input name="case_3_note" class="style1" size="16" value="<%=case_3_note%>" readonly></span></td>
                                                                <!--  <td height="40" colspan="2" valign="bottom" class="style5">4��<br><input name="case_4_note" class="style1" size="16" value="<%//=case_4_note%>" readonly></td>
                                                                <td height="40" colspan="2" valign="bottom" class="style5">5��<br><input name="case_5_note" class="style1" size="16" value="<%//=case_5_note%>" readonly></td>-->
                                                                <td width="209" rowspan="2" class="style5">���</td>
                                                            </tr>
                                                            <tr>
                                                                <td width="65" height="21" class="style5"><span class="change_left">�ѿ���</span></td>
                                                                <td width="50" height="21" class="style5"><span class="change_right">������</span></td>
                                                                <td width="65" height="21" class="style5"><span class="change_left_1">�ѿ���</span></td>
                                                                <td width="50" height="21" class="style5"><span class="change_right_1">������</span></td>
                                                                <td width="65" height="21" class="style5"><span class="change_left_2">�ѿ���</span></td>
                                                                <td width="50" height="21" class="style5"><span class="change_right_2">������</span></td>
                                                                <!--  <td width="46" height="21" class="style5">�ѿ���</td>
                                                                <td width="40" height="21" class="style5">������</td>
                                                                <td width="46" height="21" class="style5">�ѿ���</td>
                                                                <td width="40" height="21" class="style5">������</td>-->
                                                            </tr>
<%
    p = 0;
    table_row_count = Integer.parseInt(StringUtil.checkNullZero(table_row));
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
                                                                <td width="291" height="21" bgcolor="#FFFFFF" class="style1"><%=part_name[j]%></td>
                                                                <td width="35" bgcolor="#FFFFFF" class="style1"><%=pro_usage[j]%></td>
                                                                <td width="68" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(ket_cost[j], "��", 0, 1)%></td>
                                                                <td width="40" height="21" bgcolor="#FFFFFF" class="style1"><%=target_cost[j]%></td>
                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left"><%=StringUtil.DoubleFormat(t_actual_cost[j][0], "��", 0, 1)%></span></td>


                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][0])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right"><%=StringUtil.DoubleFormat(t_earn_rate[j][0], "%", 100, 1)%></span></td>

                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_1"><%=StringUtil.DoubleFormat(t_actual_cost[j][1], "��", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][1])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_1"><%=StringUtil.DoubleFormat(t_earn_rate[j][1], "%", 100, 1)%></span></td>

                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_2"><%=StringUtil.DoubleFormat(t_actual_cost[j][2], "��", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(t_earn_rate[j][2])) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_2"><%=StringUtil.DoubleFormat(t_earn_rate[j][2], "%", 100, 1)%></span></td>

                                                                <!--  <td width="46" height="21" bgcolor="#FFFFFF" class="style1"><%//=actual_cost_4%></td>
                                                                <td width="40" height="21" bgcolor="#FFFFFF" class="style13"><%//=earn_rate_4%></td>
                                                                <td width="46" height="21" bgcolor="#FFFFFF" class="style1"><%//=actual_cost_5%></td>
                                                                <td width="40" height="21" bgcolor="#FFFFFF" class="style13"><%//=earn_rate_5%></td>-->
<%
        if(j == 0){
%>
                                                                <td rowspan="<%=table_row_count%>" bgcolor="#FFFFFF" class="style1"><textarea name="note_2" cols="32" rows="<%=table_row_count+1%>" class="style9" readonly><%=note_2%></textarea></td>
<%
        }
%>
                                                            </tr>
<%
    }
%>
                                                            <tr>
                                                                <td width="291" height="21" class="style5">Total</td>
                                                                <td width="35" bgcolor="#FFFFFF" class="style1"></td>
                                                                <td width="68" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(ket_cost_sum, "��", 0, 1)%></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom"><%=StringUtil.DoubleFormat(actual_cost_sum_1, "��", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_1)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom"><%=StringUtil.DoubleFormat(earn_rate_sum_1, "%", 0, 1)%></span></td>
                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom_1"><%=StringUtil.DoubleFormat(actual_cost_sum_2, "��", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_2)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom_1"><%=StringUtil.DoubleFormat(earn_rate_sum_2, "%", 0, 1)%></span></td>
                                                                <td width="65" height="21" bgcolor="#FFFFFF" class="style1"><span class="change_left_bottom_2"><%=StringUtil.DoubleFormat(actual_cost_sum_3, "��", 0, 1)%></span></td>
                                                                <td height="21" bgcolor="#FFFFFF" <%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate_sum_3)) > 0){  %>class="style13"><%}else{%>class="style11"><%} %>
                                                                <span class="change_right_bottom_2"><%=StringUtil.DoubleFormat(earn_rate_sum_3, "%", 0, 1)%></span></td>
                                                                <!-- <td width="46" height="21" bgcolor="#FFFFFF" class="style1"><%=StringUtil.DoubleFormat(actual_cost_sum_3, "��", 0, 1)%></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style13"></td>
                                                                <td width="46" height="21" bgcolor="#FFFFFF" class="style1"></td>
                                                                <td height="21" bgcolor="#FFFFFF" class="style13"></td>-->
                                                                <td height="21" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                                            </tr>
                                                        </table>
                                                        <table width="1020" border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="481" height="50">
                                                                <table width="470" border="0" cellpadding="0" cellspacing="1">
                                                                    <tr>
                                                                        <td height="21" colspan="13" valign="bottom" class="style3">4.�ֿ� ������� �� ISSUE ���� </td>
                                                                    </tr>
                                                                </table>
                                                                <table width="429" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                                    <tr>
                                                                        <td width="427" bgcolor="#FFFFFF"><textarea name="note_3" cols="82" rows="7" class="style9" readonly><%=note_3%></textarea></td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                            <td width="474" height="50">
                                                                <table width="470" border="0" cellpadding="0" cellspacing="1">
                                                                    <tr>
                                                                        <td height="21" colspan="13" valign="bottom" class="style3">&nbsp;5. �濵�� ���û��� / �������ǰ� </td>
                                                                    </tr>
                                                                </table>
                                                                <table width="429" border="0" align="right" cellpadding="0" cellspacing="1" bgcolor="#333333">
                                                                    <tr>
                                                                        <td colspan="5"  width="427" bgcolor="#FFFFFF"><textarea name="note_4" cols="80" rows="3" class="style9" ><%=note_4%></textarea></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td width="86" height="20" class="style19"><%=file_2_name %></td>
                                                                        <td width="86" height="20" class="style19"><%=file_3_name %></td>
                                                                        <td width="86" height="20" class="style19"><%=file_4_name %></td>
                                                                        <td width="86" height="20" class="style19"><%=file_5_name %></td>
                                                                        <td width="86" height="20" class="style19"><%=file_6_name %></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                                <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td align="center"><img id= "file_2_img" border="0" onClick="file_open('file_2');" style="cursor:pointer;"></td>
                                                                                </tr>
                                                                                </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td align="center"><img id= "file_3_img" border="0" onClick="file_open('file_3');" style="cursor:pointer;"></td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td align="center"><img id= "file_4_img" border="0" onClick="file_open('file_4');" style="cursor:pointer;"></td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td align="center"><img id= "file_5_img" border="0" onClick="file_open('file_5');" style="cursor:pointer;"></td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>

                                                                        <td align="center" width="86" height="34" bgcolor="#FFFFFF">
                                                                            <table width="86" border="0" cellpadding="0" cellspacing="1">
                                                                                <tr>
                                                                                <td align="center"><img id= "file_6_img" border="0" onClick="file_open('file_6');" style="cursor:pointer;" align="center"></td>
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
                            <table height="25"border="0" cellpadding="0" cellspacing="1">
                                <tr>
                                    <td width="1024" align="left" valign="bottom" ><img src="/plm/jsp/cost/acc_img/btn_url.jpg" border="0" onClick="Urlcall();" style="cursor:pointer;"/></td>
                                    <%if(!StringUtil.checkNullZero(invest_cnt).equals("0")){ %>
                                    <td valign="bottom"><img src="/plm/jsp/cost/acc_img/invest_btn.gif" border="0" onClick="invest_call();"style="cursor:pointer;"/></td>
                                    <%} %>
                                    <td valign="bottom" ><img src="/plm/jsp/cost/acc_img/btn_yo.gif" border="0" onClick="request_call();" style="cursor:pointer;"/></td>
                                    <td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_print.gif" border="0" onClick="print_call();"style="cursor:pointer;"/></td>
                                    <%if(login_id.equals("admin") ){ %>
                                    <td valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_Save.gif" border="0" onClick="DB_call();" style="cursor:pointer;"></td>
                                    <%} %>
                                </tr>
                            </table>
                            <div align="right"><font color="#FF0000" size="2">( ó�� ����Ͻô� ���� <a href="http://192.168.17.12/download/PrintInstaller.exe"><strong><font size="3">����</font></strong></a>�� ������ ����Ʈ ��� ��ġ�� �Ͻñ� �ٶ��ϴ�.)</font></div>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</Form>
</body>
</html>