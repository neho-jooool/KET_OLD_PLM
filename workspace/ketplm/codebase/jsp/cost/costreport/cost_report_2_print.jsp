<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="java.net.URLDecoder,java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostReportCtl"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String pk_cr_group = request.getParameter("pk_cr_group")!=null?request.getParameter("pk_cr_group"):"";
    String rev_no = request.getParameter("rev_no")!=null?request.getParameter("rev_no"):"";
    String user_case_count = request.getParameter("user_case_count")!=null?request.getParameter("user_case_count"):"";
    String cost_report_add = request.getParameter("cost_report_add")!=null?request.getParameter("cost_report_add"):"";
    String pk_cw = request.getParameter("pk_cw")!=null?request.getParameter("pk_cw"):"";
    String chk_list = request.getParameter("chk_list")!=null?request.getParameter("chk_list"):"";
    String report_pk = request.getParameter("report_pk")!=null?request.getParameter("report_pk"):"";
    String page_no = request.getParameter("page_no")!=null?request.getParameter("page_no"):"";
    String approval = request.getParameter("approval")!=null?request.getParameter("approval"):"";
    String step_no  = request.getParameter("step_no")!=null?request.getParameter("step_no"):"";
    String select_case = request.getParameter("select_case")!=null?request.getParameter("select_case"):"";
    String select_name = request.getParameter("select_name")!=null?request.getParameter("select_name"):"";
    String visitor = request.getParameter("visitor")!=null?request.getParameter("visitor"):"";
    String note_1 = request.getParameter("note_1")!=null?request.getParameter("note_1"):"";
    String note_2 = request.getParameter("note_2")!=null?request.getParameter("note_2"):"";
    String note_3 = request.getParameter("note_3")!=null?request.getParameter("note_3"):"";
    String note_4 = request.getParameter("note_4")!=null?request.getParameter("note_4"):"";
    String note_5 = request.getParameter("note_5")!=null?request.getParameter("note_5"):"";
    String note_5_1 = request.getParameter("note_5_1")!=null?request.getParameter("note_5_1"):"";
    String case_1_note = request.getParameter("case_1_note")!=null?request.getParameter("case_1_note"):"";
    String case_2_note = request.getParameter("case_2_note")!=null?request.getParameter("case_2_note"):"";
    String case_3_note = request.getParameter("case_3_note")!=null?request.getParameter("case_3_note"):"";
    String DB_case_2 = request.getParameter("DB_case_2")!=null?request.getParameter("DB_case_2"):"";

    //1.�ǸŴ�����ǰ����
    String[][][] pk_cw_array = new String[70][80][50];
    String[][][] part_no = new String[70][80][50];
    String[][][] part_name = new String[70][80][50];
    String[][][] part_type = new String[70][80][50];
    String[][][] group_no = new String[70][80][50];
    String[][][] su_year_1 = new String[70][80][50];
    String[][][] su_year_2 = new String[70][80][50];
    String[][][] su_year_3 = new String[70][80][50];
    String[][][] su_year_4 = new String[70][80][50];
    String[][][] su_year_5 = new String[70][80][50];
    String[][][] su_year_6 = new String[70][80][50];
    String[][][] su_year_7 = new String[70][80][50];
    String[][][] su_year_8 = new String[70][80][50];
    String[][][] a_su_count = new String[70][80][50];
    String[][][] a_su_year_1 = new String[70][80][50];
    String[][][] a_su_year_2 = new String[70][80][50];
    String[][][] a_su_year_3 = new String[70][80][50];
    String[][][] a_su_year_4 = new String[70][80][50];
    String[][][] a_su_year_5 = new String[70][80][50];
    String[][][] a_su_year_6 = new String[70][80][50];
    String[][][] a_su_year_7 = new String[70][80][50];
    String[][][] a_su_year_8 = new String[70][80][50];
    String[][][] case_type_admin_t = new String[70][80][50];
    String[][][] ket_cost = new String[70][80][50];
    String[][][] target_cost = new String[70][80][50];
    String[][][] assy_note = new String[70][80][50];
    String[][][] t_actual_cost = new String[70][80][50];    //�Ǹű����ѿ���
    String[][][] t_earn_rate = new String[70][80][50];    //���ͷ�
    String[][][] h_FK_cost_request = new String[70][80][50];
    String[] case_count_1 = new String[70];
    String[][] case_count_2 = new String[70][80];

    //2.��ǰ���곻��
    String[][][] FK_cost_request = new String[70][80][50];
    String[][][] pro_type = new String[70][80][50];
    String[][][] make = new String[70][80][50];
    String[][][] usage = new String[70][80][50];
    String[][][] die_no = new String[70][80][50];
    String[][][] cavity = new String[70][80][50];
    String[][][] make_type = new String[70][80][50];
    String[][][] pro_1 = new String[70][80][50];
    String[][][] pro_level = new String[70][80][50];
    String[][][] pro_level_txt = new String[70][80][50];
    String[][][] type_cost = new String[70][80][50];
    String[][][] t_order = new String[70][80][50];
    String[][][] dis_cost = new String[70][80][50];
    String[][][] yazaki_ro = new String[70][80][50];
    String[][][] mold_cost = new String[70][80][50];
    String[][][] line_su = new String[70][80][50];
    String[][][] sul_cost = new String[70][80][50];
    String[][][] etc_cost = new String[70][80][50];
    String[][][] part_note = new String[70][80][50];
    String[][][] oem_info = new String[70][80][50];
    String[][][] to_cost = new String[70][80][50];
    String[][][] depr_su = new String[70][80][50];
    String[][][] meterial_cost = new String[70][80][50];    //������
    String[][][] m_total_cost = new String[70][80][50];    //����
    String[][][] labor_cost = new String[70][80][50];    //�빫��
    String[][][] common_cost = new String[70][80][50];    //�������
    String[][][] s_depr_cost = new String[70][80][50];    //�Ϲݰ�����
    String[][][] g_depr_cost = new String[70][80][50];    //��ȹ����������
    String[][][] ad_cost = new String[70][80][50];    //������
    String[][][] actual_cost = new String[70][80][50];    //�ѿ���
    String[][][] earn_rate = new String[70][80][50];    //���ͷ�
    String[][][] loss = new String[70][80][50];    //loss��
    String[][][] scrap_cost = new String[70][80][50];    //��ũ���Ǹź�
    String[][][] output = new String[70][80][50];    //�ð�����귮
    String[][][] rate = new String[70][80][50];    //����
    String[][][] eff_value = new String[70][80][50];    //ȿ��
    String[][][] rnd_cost = new String[70][80][50];    //R&D����
    String[][][] jun_cost = new String[70][80][50];    //���º�
    String[][][] ma_depr = new String[70][80][50];    //��谨��
    String[][][] tabalu = new String[70][80][50];    //Ÿ����
    String[][][] m_upkeep = new String[70][80][50];    //����������
    String[][][] total_expense = new String[70][80][50];    //�������
    String[][][] overhead = new String[70][80][50];    //�������
    String[][][] outsource = new String[70][80][50];    //���ִܰ�
    String[][][] etc_expense = new String[70][80][50];    //��Ÿ
    String[][][] gita_cost = new String[70][80][50];
    String[][][] pack_cost = new String[70][80][50];    //�����
    String[][][] yzk_depr = new String[70][80][50];    //yzk����
    String[][][] start_depr = new String[70][80][50];    //��������
    String[][][] pro_depr = new String[70][80][50];    //���񰨰�
    String[][][] qu_cost = new String[70][80][50];    //ǰ�����
    String[][][] tariff = new String[70][80][50];    //����
    String[][][] royalty_cost = new String[70][80][50];    //�ξ�Ƽ
    String[][][] s_avg_su = new String[70][80][50];    //�Ϲݰ������ؼ���
    String[][][] avg_su = new String[70][80][50];    //���Ǹű�ȹ����
    String[][][] process_cost = new String[70][80][50];    //������
    String[][][] jae_cost = new String[70][80][50];    //��������
    String[][][] ge_cost = new String[70][80][50];    //�Ϲݰ�����
    String[][][] s_rnd_cost = new String[70][80][50];    //�Ϲݰ�������R&D��
    String[][][] g_rnd_cost = new String[70][80][50];    //�Ǹű���R&D��
    String[][][] s_roy_cost = new String[70][80][50];    //�Ϲݰ������طο�Ƽ
    String[][][] s_actual_cost = new String[70][80][50];    //�Ϲݰ��������ѿ���
    String[][][] g_roy_cost = new String[70][80][50];    //�Ǹű��طο�Ƽ
    String[][][] g_actual_cost = new String[70][80][50];    //�Ǹű����ѿ���
    String[][][] s_earn_rate = new String[70][80][50];    //�Ϲݰ������ؼ�����
    String[][][] g_earn_rate = new String[70][80][50];    //�Ǹű��ؼ�����
    String[][][] sum_meterial = new String[70][80][50];    //��������
    String[][][] p_m_total_cost = new String[70][80][50];
    String[][][] p_labor_cost = new String[70][80][50];
    String[][][] p_common_cost = new String[70][80][50];
    String[][][] p_sum_meterial = new String[70][80][50];
    String[][][] p_ad_cost = new String[70][80][50];
    String[][][] p_g_depr_cost = new String[70][80][50];
    String[][][] p_actual_cost = new String[70][80][50];
    String[][][] sum_m_total_cost = new String[70][80][50];
    String[][][] sum_labor_cost = new String[70][80][50];
    String[][][] sum_common_cost = new String[70][80][50];
    String[][][] sum_sum_meterial = new String[70][80][50];
    String[][][] sum_ad_cost = new String[70][80][50];
    String[][][] sum_g_depr_cost = new String[70][80][50];
    String[][][] sum_actual_cost = new String[70][80][50];
    String[][][] in_actual_cost = new String[70][80][50];

    String pjt_name = "";
    String f_name = "";
    String f_day = "";
    String a_name = "";
    String w_name = "";
    String pjt_no = "";
    String car_type = "";
    String customer_F = "";
    String request_txt = "";
    String team = "";
    String table_row = "";

    String sum_meterial_no = "";

    // html bgcolor
    String bg_co_txt = "";
    String case_type_txt = "";

    CostReportCtl reportCtl = new CostReportCtl();
    ArrayList reportNoteList = null;
    Hashtable noteItem = null;
    ArrayList reportPkCrRevNoList = null;
    Hashtable revNoItem = null;
    ArrayList reportSuYearList = null;
    Hashtable suYearItem = null;
    ArrayList reportCaseTypeAdminList = null;
    Hashtable caseTypeItem = null;
    ArrayList reportPkcwGroupNoList = null;
    Hashtable pkcwGroupNoItem = null;
    ArrayList reportCostWorkList = null;
    Hashtable costWorkItem = null;


    if("3".equals(page_no) || "ok".equals(cost_report_add)){
        reportNoteList = reportCtl.reportNoteList(report_pk);
        for(int i=0; i < reportNoteList.size(); i++){
            noteItem = (Hashtable)reportNoteList.get(i);
            note_1 = (String)noteItem.get("note_1")!=null?(String)noteItem.get("note_1"):"";
            note_2 = (String)noteItem.get("note_2")!=null?(String)noteItem.get("note_2"):"";
            note_3 = (String)noteItem.get("note_3")!=null?(String)noteItem.get("note_3"):"";
            note_4 = (String)noteItem.get("note_4")!=null?(String)noteItem.get("note_4"):"";
            note_5 = (String)noteItem.get("note_5")!=null?(String)noteItem.get("note_5"):"";
            note_5_1 = (String)noteItem.get("note_5_1")!=null?(String)noteItem.get("note_5_1"):"";
            pjt_name = (String)noteItem.get("pjt_name")!=null?(String)noteItem.get("pjt_name"):"";
            f_name = (String)noteItem.get("f_name")!=null?(String)noteItem.get("f_name"):"";
            f_day = (String)noteItem.get("f_day")!=null?(String)noteItem.get("f_day"):"";
            a_name = (String)noteItem.get("a_name")!=null?(String)noteItem.get("a_name"):"";
            w_name = (String)noteItem.get("w_name")!=null?(String)noteItem.get("w_name"):"";
            pjt_no = (String)noteItem.get("pjt_no")!=null?(String)noteItem.get("pjt_no"):"";
            car_type = (String)noteItem.get("car_type")!=null?(String)noteItem.get("car_type"):"";
            customer_F = (String)noteItem.get("customer_F")!=null?(String)noteItem.get("customer_F"):"";
            request_txt = (String)noteItem.get("request_txt")!=null?(String)noteItem.get("request_txt"):"";
            team = (String)noteItem.get("team")!=null?(String)noteItem.get("team"):"";
        }
    }

    reportPkCrRevNoList = reportCtl.reportPkcrRevNoList(pk_cr_group, rev_no);
    for(int i=0; i < reportPkCrRevNoList.size(); i++){
        revNoItem = (Hashtable)reportPkCrRevNoList.get(i);
        table_row = (String)revNoItem.get("table_row")!=null?(String)revNoItem.get("table_row"):"";
        pjt_name = (String)revNoItem.get("pjt_name")!=null?(String)revNoItem.get("pjt_name"):"";
        f_day = (String)revNoItem.get("f_day")!=null?(String)revNoItem.get("f_day"):"";
        f_name = (String)revNoItem.get("f_name")!=null?(String)revNoItem.get("f_name"):"";
        f_day = (String)revNoItem.get("f_name")!=null?(String)revNoItem.get("f_day"):"";
        a_name = (String)revNoItem.get("a_name")!=null?(String)revNoItem.get("a_name"):"";
        w_name = (String)revNoItem.get("w_name")!=null?(String)revNoItem.get("w_name"):"";
        team = (String)revNoItem.get("team")!=null?(String)revNoItem.get("team"):"";
        if(!"3".equals(page_no) && !"ok".equals(cost_report_add)){
            pjt_no = (String)revNoItem.get("pjt_no")!=null?(String)revNoItem.get("pjt_no"):"";
            car_type = (String)revNoItem.get("car_type")!=null?(String)revNoItem.get("car_type"):"";
            customer_F = (String)revNoItem.get("customer_F")!=null?(String)revNoItem.get("customer_F"):"";
            request_txt = (String)revNoItem.get("request_txt")!=null?(String)revNoItem.get("request_txt"):"";
        }
    }
    String [] pk_cw_1_array  = null;
    if(!StringUtil.checkNull(chk_list).equals("")){
        pk_cw_1_array = chk_list.split(",");
    }

    int P_count = pk_cw_1_array.length; // request�� pk_cw ����
    int p = 0; //�迭count P
    int q = 0; //�迭 count Q
    int t = 0;
    for(int j=0; j < P_count; j++){
        a_su_count[j][p][q] = "0";
        if(pk_cw_1_array.length == 1){
            pk_cw_1_array[j] = chk_list;
        }

        reportSuYearList = reportCtl.reportSuYearList(pk_cw_1_array[j]);
        if(reportSuYearList.size() > 0){
            suYearItem = (Hashtable)reportSuYearList.get(0);
            pk_cw_array[j][p][q] = (String)suYearItem.get("pk_cw")!=null?(String)suYearItem.get("pk_cw"):"";
            group_no[j][p][q] = (String)suYearItem.get("group_no")!=null?(String)suYearItem.get("group_no"):"";
            case_count_1[j] = (String)suYearItem.get("case_count_1")!=null?(String)suYearItem.get("case_count_1"):"";
            case_type_admin_t[j][p][q] = (String)suYearItem.get("case_type_admin")!=null?(String)suYearItem.get("case_type_admin"):"";
            su_year_1[j][p][q] = (String)suYearItem.get("su_year_1")!=null?(String)suYearItem.get("su_year_1"):"";
            su_year_2[j][p][q] = (String)suYearItem.get("su_year_2")!=null?(String)suYearItem.get("su_year_2"):"";
            su_year_3[j][p][q] = (String)suYearItem.get("su_year_3")!=null?(String)suYearItem.get("su_year_3"):"";
            su_year_4[j][p][q] = (String)suYearItem.get("su_year_4")!=null?(String)suYearItem.get("su_year_4"):"";
            su_year_5[j][p][q] = (String)suYearItem.get("su_year_5")!=null?(String)suYearItem.get("su_year_5"):"";
            su_year_6[j][p][q] = (String)suYearItem.get("su_year_6")!=null?(String)suYearItem.get("su_year_6"):"";
            su_year_7[j][p][q] = (String)suYearItem.get("su_year_7")!=null?(String)suYearItem.get("su_year_7"):"";
            su_year_8[j][p][q] = (String)suYearItem.get("su_year_8")!=null?(String)suYearItem.get("su_year_8"):"";
        }

        if(!"0".equals(su_year_1[j][p][q]) || !"".equals(su_year_1[j][p][q])){
            a_su_year_1[j][p][q] = su_year_1[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_1[j][p][q] = "0";
        }
        if(!"0".equals(su_year_2[j][p][q]) || !"".equals(su_year_2[j][p][q])){
            a_su_year_2[j][p][q] = su_year_2[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_2[j][p][q] = "0";
        }
        if(!"0".equals(su_year_3[j][p][q]) || !"".equals(su_year_3[j][p][q])){
            a_su_year_3[j][p][q] = su_year_3[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_3[j][p][q] = "0";
        }
        if(!"0".equals(su_year_4[j][p][q]) || !"".equals(su_year_4[j][p][q])){
            a_su_year_4[j][p][q] = su_year_4[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_4[j][p][q] = "0";
        }
        if(!"0".equals(su_year_5[j][p][q]) || !"".equals(su_year_5[j][p][q])){
            a_su_year_5[j][p][q] = su_year_5[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_5[j][p][q] = "0";
        }
        if(!"0".equals(su_year_6[j][p][q]) || !"".equals(su_year_6[j][p][q])){
            a_su_year_6[j][p][q] = su_year_6[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_6[j][p][q] = "0";
        }
        if(!"0".equals(su_year_7[j][p][q]) || !"".equals(su_year_7[j][p][q])){
            a_su_year_7[j][p][q] = su_year_7[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_7[j][p][q] = "0";
        }
        if(!"0".equals(su_year_8[j][p][q]) || !"".equals(su_year_8[j][p][q])){
            a_su_year_8[j][p][q] = su_year_8[j][p][q];
            a_su_count[j][p][q] = Integer.toString(Integer.parseInt(a_su_count[j][p][q]) + 1);
        }else{
            a_su_year_8[j][p][q] = "0";
        }
        if(reportSuYearList.size() > 0){
            m_total_cost[j][p][q] = (String)suYearItem.get("m_total_cost")!=null?(String)suYearItem.get("m_total_cost"):"";    //����
            labor_cost[j][p][q] = (String)suYearItem.get("labor_cost")!=null?(String)suYearItem.get("labor_cost"):"";    //�빫��
            common_cost[j][p][q] = (String)suYearItem.get("common_cost")!=null?(String)suYearItem.get("common_cost"):"";    //�������
        }
        //avg_su[j][p][q] = Integer.toString(Integer.parseInt(a_su_year_1[j][p][q]) + Integer.parseInt(a_su_year_2[j][p][q]) + Integer.parseInt(a_su_year_3[j][p][q]) + Integer.parseInt(a_su_year_4[j][p][q]) + Integer.parseInt(a_su_year_5[j][p][q]) + Integer.parseInt(a_su_year_6[j][p][q]) + Integer.parseInt(a_su_year_7[j][p][q]) + Integer.parseInt(a_su_year_8[j][p][q]));
        avg_su[j][p][q] = Double.toString(StringUtil.nullDouble(a_su_year_1[j][p][q]) + StringUtil.nullDouble(a_su_year_2[j][p][q]) + StringUtil.nullDouble(a_su_year_3[j][p][q]) + StringUtil.nullDouble(a_su_year_4[j][p][q]) + StringUtil.nullDouble(a_su_year_5[j][p][q]) + StringUtil.nullDouble(a_su_year_6[j][p][q]) + StringUtil.nullDouble(a_su_year_7[j][p][q]) + StringUtil.nullDouble(a_su_year_8[j][p][q]));
        sum_meterial[j][p][q] = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(m_total_cost[j][p][q]))) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(labor_cost[j][p][q]))) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(common_cost[j][p][q]))));
    }
    q = 0; // �迭 count Q �ʱ�ȭ
    int s = 0; // �迭 count S
    int d = 1; // �迭 count D
    int b = 0; // �迭 count B
    String k = "";
    for(int j=0; j<P_count; j++){
        for(p=1;p<=Integer.parseInt(StringUtil.checkNullZero(case_count_1[j]));p++){
            if(p < 10){
                k = "0"+Integer.toString(p);
            }else{
                k = Integer.toString(p);
            }

            if("main".equals(case_type_admin_t[j][0][0])){
                s = j;
            }
            reportCaseTypeAdminList = reportCtl.reportCaseTypeAdminList(pk_cw_array[s][d][b], pk_cr_group, rev_no, group_no[j][0][0], case_type_admin_t[j][0][0], k);
            if(reportCaseTypeAdminList.size() > 0){
                caseTypeItem = (Hashtable)reportCaseTypeAdminList.get(0);
                pk_cw_array[j][p][q] = (String)caseTypeItem.get("pk_cw")!=null?(String)caseTypeItem.get("pk_cw"):"";
                group_no[j][p][q] = (String)caseTypeItem.get("group_no")!=null?(String)caseTypeItem.get("group_no"):"";
                case_count_2[j][p] = (String)caseTypeItem.get("case_count_2")!=null?(String)caseTypeItem.get("case_count_2"):"0";
            }
            s = j;
            d = p;
            b = q;
        }
    }

    for(int j=0;j<P_count;j++){
        for(p=1;p<=Integer.parseInt(StringUtil.checkNullZero(case_count_1[j]));p++){
            for(q=1;q<=Integer.parseInt(StringUtil.checkNullZero(case_count_2[j][p]));q++){
                if(q < 10){
                    k = "0"+Integer.toString(q);
                }else{
                    k = Integer.toString(q);
                }
                reportPkcwGroupNoList = reportCtl.reportPkcwGroupNoList(pk_cr_group, k, group_no[j][p][0], case_type_admin_t[j][0][0]);
                pkcwGroupNoItem = (Hashtable)reportPkcwGroupNoList.get(0);
                pk_cw_array[j][p][q] = (String)pkcwGroupNoItem.get("pk_cw")!=null?(String)pkcwGroupNoItem.get("pk_cw"):"";
                group_no[j][p][q] = (String)pkcwGroupNoItem.get("group_no")!=null?(String)pkcwGroupNoItem.get("group_no"):"";
            }
        }
    }

    t = 0;
    for(int j=0;j<P_count;j++){
        for(p=0;p<=Integer.parseInt(StringUtil.checkNullZero(case_count_1[j]));p++){
            for(q=0;q<=Integer.parseInt(StringUtil.checkNullZero(case_count_2[j][p]));q++){
                reportCostWorkList = reportCtl.reportCostWorkList(pk_cw_array[j][p][q]);
                    if(reportCostWorkList.size() > 0){
                        costWorkItem = (Hashtable)reportCostWorkList.get(0);
                        FK_cost_request[j][p][q] = (String)costWorkItem.get("FK_cost_request")!=null?(String)costWorkItem.get("FK_cost_request"):"";
                        case_type_admin_t[j][p][q] = (String)costWorkItem.get("case_type_admin")!=null?(String)costWorkItem.get("case_type_admin"):"";
                        part_name[j][p][q] = (String)costWorkItem.get("part_name")!=null?(String)costWorkItem.get("part_name"):"";
                        part_type[j][p][q] = (String)costWorkItem.get("part_type")!=null?(String)costWorkItem.get("part_type"):"";
                        pro_type[j][p][q] = (String)costWorkItem.get("pro_type")!=null?(String)costWorkItem.get("pro_type"):"";
                        su_year_1[j][p][q] = (String)costWorkItem.get("su_year_1")!=null?(String)costWorkItem.get("su_year_1"):"";
                        su_year_2[j][p][q] = (String)costWorkItem.get("su_year_2")!=null?(String)costWorkItem.get("su_year_2"):"";
                        su_year_3[j][p][q] = (String)costWorkItem.get("su_year_3")!=null?(String)costWorkItem.get("su_year_3"):"";
                        su_year_4[j][p][q] = (String)costWorkItem.get("su_year_4")!=null?(String)costWorkItem.get("su_year_4"):"";
                        su_year_5[j][p][q] = (String)costWorkItem.get("su_year_5")!=null?(String)costWorkItem.get("su_year_5"):"";
                        su_year_6[j][p][q] = (String)costWorkItem.get("su_year_6")!=null?(String)costWorkItem.get("su_year_6"):"";
                        su_year_7[j][p][q] = (String)costWorkItem.get("su_year_7")!=null?(String)costWorkItem.get("su_year_7"):"";
                        su_year_8[j][p][q] = (String)costWorkItem.get("su_year_8")!=null?(String)costWorkItem.get("su_year_8"):"";
                        ket_cost[j][p][q] = (String)costWorkItem.get("ket_cost")!=null?(String)costWorkItem.get("ket_cost"):"";
                        target_cost[j][p][q] = (String)costWorkItem.get("target_cost")!=null?(String)costWorkItem.get("target_cost"):"";
                        assy_note[j][p][q] = (String)costWorkItem.get("assy_note")!=null?(String)costWorkItem.get("assy_note"):"";
                        dis_cost[j][p][q] = (String)costWorkItem.get("dis_cost")!=null?(String)costWorkItem.get("dis_cost"):"";
                        usage[j][p][q] = (String)costWorkItem.get("usage")!=null?(String)costWorkItem.get("usage"):"";
                        die_no[j][p][q] = (String)costWorkItem.get("die_no")!=null?(String)costWorkItem.get("die_no"):"";
                        cavity[j][p][q] = (String)costWorkItem.get("cavity")!=null?(String)costWorkItem.get("cavity"):"";
                        make_type[j][p][q] = (String)costWorkItem.get("make_type")!=null?(String)costWorkItem.get("make_type"):"";
                        pro_1[j][p][q] = (String)costWorkItem.get("pro_1")!=null?(String)costWorkItem.get("pro_1"):"";
                        pro_level[j][p][q] = (String)costWorkItem.get("pro_level")!=null?(String)costWorkItem.get("pro_level"):"";
                        pro_level_txt[j][p][q] = (String)costWorkItem.get("pro_level_txt")!=null?(String)costWorkItem.get("pro_level_txt"):"";
                        type_cost[j][p][q] = (String)costWorkItem.get("type_cost")!=null?(String)costWorkItem.get("type_cost"):"";
                        t_order[j][p][q] = (String)costWorkItem.get("t_order")!=null?(String)costWorkItem.get("t_order"):"";
                        dis_cost[j][p][q] = (String)costWorkItem.get("dis_cost")!=null?(String)costWorkItem.get("dis_cost"):"";
                        yazaki_ro[j][p][q] = (String)costWorkItem.get("yazaki_ro")!=null?(String)costWorkItem.get("yazaki_ro"):"";
                        mold_cost[j][p][q] = (String)costWorkItem.get("mold_cost")!=null?(String)costWorkItem.get("mold_cost"):"";
                        to_cost[j][p][q] = (String)costWorkItem.get("to_cost")!=null?(String)costWorkItem.get("to_cost"):"";
                        line_su[j][p][q] = (String)costWorkItem.get("line_su")!=null?(String)costWorkItem.get("line_su"):"";
                        sul_cost[j][p][q] = (String)costWorkItem.get("sul_cost")!=null?(String)costWorkItem.get("sul_cost"):"";
                        etc_cost[j][p][q] = (String)costWorkItem.get("etc_cost")!=null?(String)costWorkItem.get("etc_cost"):"";
                        part_note[j][p][q] = (String)costWorkItem.get("part_note")!=null?(String)costWorkItem.get("part_note"):"";
                        meterial_cost[j][p][q] = (String)costWorkItem.get("meterial_cost")!=null?(String)costWorkItem.get("meterial_cost"):"";    //������
                        m_total_cost[j][p][q] = (String)costWorkItem.get("m_total_cost")!=null?(String)costWorkItem.get("m_total_cost"):"";    //����
                        labor_cost[j][p][q] = (String)costWorkItem.get("labor_cost")!=null?(String)costWorkItem.get("labor_cost"):"";    //�빫��
                        common_cost[j][p][q] = (String)costWorkItem.get("common_cost")!=null?(String)costWorkItem.get("common_cost"):"";    //�������
                        earn_rate[j][p][q] = (String)costWorkItem.get("earn_rate")!=null?(String)costWorkItem.get("earn_rate"):"";    //���ͷ�
                        loss[j][p][q] = (String)costWorkItem.get("loss")!=null?(String)costWorkItem.get("loss"):"";    //loss��
                        scrap_cost[j][p][q] = (String)costWorkItem.get("scrap_cost")!=null?(String)costWorkItem.get("scrap_cost"):"";    //��ũ���Ǹź�
                        output[j][p][q] = (String)costWorkItem.get("output")!=null?(String)costWorkItem.get("output"):"";    //�ð�����귮
                        rate[j][p][q] = (String)costWorkItem.get("rate")!=null?(String)costWorkItem.get("rate"):"";    //����
                        eff_value[j][p][q] = (String)costWorkItem.get("eff_value")!=null?(String)costWorkItem.get("eff_value"):"";    //ȿ��
                        rnd_cost[j][p][q] = (String)costWorkItem.get("rnd_cost")!=null?(String)costWorkItem.get("rnd_cost"):"";    //R&D����
                        jun_cost[j][p][q] = (String)costWorkItem.get("jun_cost")!=null?(String)costWorkItem.get("jun_cost"):"";    //���º�
                        ma_depr[j][p][q] = (String)costWorkItem.get("ma_depr")!=null?(String)costWorkItem.get("ma_depr"):"";    //��谨��
                        m_upkeep[j][p][q] = (String)costWorkItem.get("m_upkeep")!=null?(String)costWorkItem.get("m_upkeep"):"";    //����������
                        total_expense[j][p][q] = (String)costWorkItem.get("total_expense")!=null?(String)costWorkItem.get("total_expense"):"";    //�������
                        overhead[j][p][q] = (String)costWorkItem.get("overhead")!=null?(String)costWorkItem.get("overhead"):"";    //�������
                        outsource[j][p][q] = (String)costWorkItem.get("outsource")!=null?(String)costWorkItem.get("outsource"):"";    //���ִܰ�
                        etc_expense[j][p][q] = (String)costWorkItem.get("etc_expense")!=null?(String)costWorkItem.get("etc_expense"):"";    //��Ÿ
                        gita_cost[j][p][q] = (String)costWorkItem.get("gita_cost")!=null?(String)costWorkItem.get("gita_cost"):"";
                        pack_cost[j][p][q] = (String)costWorkItem.get("pack_cost")!=null?(String)costWorkItem.get("pack_cost"):"";    //�����
                        yzk_depr[j][p][q] = (String)costWorkItem.get("yzk_depr")!=null?(String)costWorkItem.get("yzk_depr"):"";    //YZK����
                        if("TML".equals(pro_type[j][p][q])){    //��ö
                            tabalu[j][p][q] = (String)costWorkItem.get("tabalu")!=null?(String)costWorkItem.get("tabalu"):"";    //Ÿ����
                            start_depr[j][p][q] = (String)costWorkItem.get("start_depr")!=null?(String)costWorkItem.get("start_depr"):"";    //��������
                            pro_depr[j][p][q] = "0";
                            if(!"".equals(to_cost[j][p][q])){
                                to_cost[j][p][q] = to_cost[j][p][q];
                            }else{
                                to_cost[j][p][q] = mold_cost[j][p][q];
                            }
                            ma_depr[j][p][q] = (String)costWorkItem.get("ma_depr")!=null?(String)costWorkItem.get("ma_depr"):"";    //��谨��
                        }else if("HSG".equals(pro_type[j][p][q])){    //����
                            tabalu[j][p][q] = "0";    //Ÿ����
                            start_depr[j][p][q] = "0";
                            pro_depr[j][p][q] = (String)costWorkItem.get("pro_depr")!=null?(String)costWorkItem.get("pro_depr"):"";    //���񰨰�
                            if(!"".equals(to_cost[j][p][q])){
                                to_cost[j][p][q] = to_cost[j][p][q];
                            }else{
                                to_cost[j][p][q] = sul_cost[j][p][q];
                            }
                            ma_depr[j][p][q] = (String)costWorkItem.get("ma_depr")!=null?(String)costWorkItem.get("ma_depr"):"";    //��谨��
                        }else if("assy".equals(pro_type[j][p][q])){    //����
                            //518
                            tabalu[j][p][q] = "0";    //Ÿ����
                            start_depr[j][p][q] = "0";
                            pro_depr[j][p][q] = (String)costWorkItem.get("pro_depr")!=null?(String)costWorkItem.get("pro_depr"):"";    //���񰨰�
                            if(!"".equals(to_cost[j][p][q])){
                                to_cost[j][p][q] = to_cost[j][p][q];
                            }else{
                                to_cost[j][p][q] = sul_cost[j][p][q];
                            }
                            ma_depr[j][p][q] = "";    //��谨��
                        }else if("sub_assy".equals(pro_type[j][p][q])){    //����

                            tabalu[j][p][q] = "0";    //Ÿ����
                            start_depr[j][p][q] = "0";
                            pro_depr[j][p][q] = (String)costWorkItem.get("pro_depr")!=null?(String)costWorkItem.get("pro_depr"):"";    //���񰨰�
                            if(!"".equals(to_cost[j][p][q])){
                                to_cost[j][p][q] = to_cost[j][p][q];
                            }else{
                                to_cost[j][p][q] = sul_cost[j][p][q];
                            }
                            ma_depr[j][p][q] = "";    //��谨��
                        }else if("Insert".equals(pro_type[j][p][q])){    //����

                            tabalu[j][p][q] = "0";    //Ÿ����
                            start_depr[j][p][q] = (String)costWorkItem.get("start_depr")!=null?(String)costWorkItem.get("start_depr"):"";    //��������
                            pro_depr[j][p][q] = (String)costWorkItem.get("pro_depr")!=null?(String)costWorkItem.get("pro_depr"):"";    //���񰨰�
                            if(!"".equals(to_cost[j][p][q])){
                                to_cost[j][p][q] = to_cost[j][p][q];
                            }else{
                                if(!"".equals(mold_cost[j][p][q])){
                                    to_cost[j][p][q] = mold_cost[j][p][q];
                                }
                                if(!"".equals(sul_cost[j][p][q])){
                                    to_cost[j][p][q] = sul_cost[j][p][q];
                                }
                            }
                            ma_depr[j][p][q] = "";    //��谨��
                        }
                        qu_cost[j][p][q] = (String)costWorkItem.get("qu_cost")!=null?(String)costWorkItem.get("qu_cost"):"";
                        tariff[j][p][q] = (String)costWorkItem.get("tariff")!=null?(String)costWorkItem.get("tariff"):"";
                        royalty_cost[j][p][q] = (String)costWorkItem.get("royalty_cost")!=null?(String)costWorkItem.get("royalty_cost"):"";
                        jae_cost[j][p][q] = (String)costWorkItem.get("jae_cost")!=null?(String)costWorkItem.get("jae_cost"):"";
                        ge_cost[j][p][q] = (String)costWorkItem.get("ge_cost")!=null?(String)costWorkItem.get("ge_cost"):"";
                        rnd_cost[j][p][q] = (String)costWorkItem.get("rnd_cost")!=null?(String)costWorkItem.get("rnd_cost"):"";
                        actual_cost[j][p][q] = (String)costWorkItem.get("actual_cost")!=null?(String)costWorkItem.get("actual_cost"):"";
                        g_depr_cost[j][p][q] = (String)costWorkItem.get("depr_cost")!=null?(String)costWorkItem.get("depr_cost"):"";
                        ad_cost[j][p][q] = (String)costWorkItem.get("ad_cost")!=null?(String)costWorkItem.get("ad_cost"):"";
                }
                //576
                if(!"0".equals(su_year_1[j][p][q]) || !"".equals(su_year_1[j][p][q])){
                    a_su_year_1[j][p][q] = su_year_1[j][p][q];
                }else{
                    a_su_year_1[j][p][q] = "0";
                }
                if(!"0".equals(su_year_2[j][p][q]) || !"".equals(su_year_2[j][p][q])){
                    a_su_year_2[j][p][q] = su_year_2[j][p][q];
                }else{
                    a_su_year_2[j][p][q] = "0";
                }
                if(!"0".equals(su_year_3[j][p][q]) || !"".equals(su_year_3[j][p][q])){
                    a_su_year_3[j][p][q] = su_year_3[j][p][q];
                }else{
                    a_su_year_3[j][p][q] = "0";
                }
                if(!"0".equals(su_year_4[j][p][q]) || !"".equals(su_year_4[j][p][q])){
                    a_su_year_4[j][p][q] = su_year_4[j][p][q];
                }else{
                    a_su_year_4[j][p][q] = "0";
                }
                if(!"0".equals(su_year_5[j][p][q]) || !"".equals(su_year_5[j][p][q])){
                    a_su_year_5[j][p][q] = su_year_5[j][p][q];
                }else{
                    a_su_year_5[j][p][q] = "0";
                }
                if(!"0".equals(su_year_6[j][p][q]) || !"".equals(su_year_6[j][p][q])){
                    a_su_year_6[j][p][q] = su_year_6[j][p][q];
                }else{
                    a_su_year_6[j][p][q] = "0";
                }
                if(!"0".equals(su_year_7[j][p][q]) || !"".equals(su_year_7[j][p][q])){
                    a_su_year_7[j][p][q] = su_year_7[j][p][q];
                }else{
                    a_su_year_7[j][p][q] = "0";
                }
                if(!"0".equals(su_year_8[j][p][q]) || !"".equals(su_year_8[j][p][q])){
                    a_su_year_8[j][p][q] = su_year_8[j][p][q];
                }else{
                    a_su_year_8[j][p][q] = "0";
                }
                if(!"0".equals(m_total_cost[j][p][q]) || !"".equals(m_total_cost[j][p][q])){
                    m_total_cost[j][p][q] = m_total_cost[j][p][q];
                }else{
                    m_total_cost[j][p][q] = "0";
                }
                if(!"0".equals(labor_cost[j][p][q]) || !"".equals(labor_cost[j][p][q])){
                    labor_cost[j][p][q] = labor_cost[j][p][q];
                }else{
                    labor_cost[j][p][q] = "0";
                }
                if(!"0".equals(common_cost[j][p][q]) || !"".equals(common_cost[j][p][q])){
                    common_cost[j][p][q] = common_cost[j][p][q];
                }else{
                    common_cost[j][p][q] = "0";
                }
                if(!"".equals(sum_labor_cost[j][0][0]) || !"0".equals(sum_labor_cost[j][0][0]) ){
                    if(!"0".equals(labor_cost[j][p][q]) || !"".equals(labor_cost[j][p][q])){
                        sum_labor_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_labor_cost[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(labor_cost[j][p][q]))));
                    }
                }else{
                    if(!"0".equals(labor_cost[j][p][q]) || !"".equals(labor_cost[j][p][q])){
                        sum_labor_cost[j][0][0] = String.format("%.4f", StringUtil.nullDouble(labor_cost[j][p][q]));
                    }
                }
                if(!"".equals(sum_common_cost[j][0][0]) || !"0".equals(sum_common_cost[j][0][0]) ){
                    if(!"0".equals(common_cost[j][p][q]) || !"".equals(common_cost[j][p][q])){
                        sum_common_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_common_cost[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(common_cost[j][p][q]))));
                    }
                }else{
                    if(!"0".equals(common_cost[j][p][q]) || !"".equals(common_cost[j][p][q])){
                        sum_common_cost[j][0][0] = String.format("%.4f", StringUtil.nullDouble(common_cost[j][p][q]));
                    }
                }
                if(!"".equals(sum_ad_cost[j][0][0]) || !"0".equals(sum_ad_cost[j][0][0]) ){
                    if(!"0".equals(ad_cost[j][p][q]) || !"".equals(ad_cost[j][p][q])){
                        sum_ad_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_ad_cost[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(ad_cost[j][p][q]))));
                    }
                }else{
                    if(!"0".equals(ad_cost[j][p][q]) || !"".equals(ad_cost[j][p][q])){
                        sum_ad_cost[j][0][0] = String.format("%.4f", StringUtil.nullDouble(ad_cost[j][p][q]));
                    }
                }

                if(!"Insert".equals(pro_type[j][p][q])){
                    if(!"".equals(g_depr_cost[j][p][q]) || !"0".equals(g_depr_cost[j][p][q])){
                        if(!"".equals(sum_g_depr_cost[j][0][0]) || !"0".equals(sum_g_depr_cost[j][0][0])){
                            sum_g_depr_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_g_depr_cost[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(g_depr_cost[j][p][q]))));
                        }else{
                            sum_g_depr_cost[j][0][0] = String.format("%.4f", StringUtil.nullDouble(g_depr_cost[j][p][q]));
                        }
                    }else{
                        g_depr_cost[j][p][q] = "0";
                    }
                }

                if(!"Insert".equals(pro_type[j][p][q]) && !"���ձ���".equals(part_type[j][p][q])){
                    in_actual_cost[j][0][0] = Double.toString(StringUtil.nullDouble(in_actual_cost[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][p][q]))));
                }

                if(!"assy".equals(pro_type[j][p][q]) && !"sub_assy".equals(pro_type[j][p][q])){
                    if(!"".equals(sum_m_total_cost[j][0][0]) || !"0".equals(sum_m_total_cost[j][0][0])){
                        if(!"".equals(m_total_cost[j][p][q]) || !"0".equals(m_total_cost[j][p][q])){
                            sum_m_total_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_m_total_cost[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(m_total_cost[j][p][q]))));
                        }
                    }else{
                        if(!"".equals(m_total_cost[j][p][q]) || !"0".equals(m_total_cost[j][p][q])){
                            sum_g_depr_cost[j][0][0] = String.format("%.4f", StringUtil.nullDouble(m_total_cost[j][p][q]));
                        }
                    }
                    sum_meterial[j][p][q] = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(m_total_cost[j][p][q]))) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(labor_cost[j][p][q]))) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(common_cost[j][p][q]))));

                    if(!"".equals(sum_sum_meterial[j][0][0]) || !"0".equals(sum_sum_meterial[j][0][0])){
                        sum_sum_meterial[j][0][0] = Double.toString(StringUtil.nullDouble(sum_sum_meterial[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_meterial[j][p][q]))));
                    }else{
                        sum_sum_meterial[j][0][0] = String.format("%.4f", StringUtil.nullDouble(sum_meterial[j][p][q]));
                    }
                }else{
                    sum_meterial_no = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(labor_cost[j][p][q]))) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(common_cost[j][p][q]))));
                    if(!"".equals(sum_sum_meterial[j][0][0]) || !"0".equals(sum_sum_meterial[j][0][0])){
                        if(!"".equals(sum_sum_meterial[j][0][0]) || !"0".equals(sum_sum_meterial[j][0][0])){
                            sum_sum_meterial[j][0][0] = Double.toString(StringUtil.nullDouble(sum_sum_meterial[j][0][0]) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_meterial_no))));
                        }else{
                            sum_sum_meterial[j][0][0] = String.format("%.4f", StringUtil.nullDouble(sum_meterial_no));
                        }
                    }
                }// assy, sub_assy if end
            } // caseCount2 For
        } // caseCount1 For
    } // j For

    //if(!"".equals(note_5)){
        //note_5 = note_5.replaceAll("<br>&nbsp;", "<br>");
    //}
    //if(!"".equals(note_5_1)){
        //note_5_1 = note_5_1.replaceAll("<br>&nbsp;", "<br>");
    //}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>���߿��� �������</title>
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

.style1 {font-size: 12px;text-align:center}
.style2 {font-size: 12px; font-weight: bold;color:#FF9900; text-align:center}
.style3 {font-size: 12px; font-weight: bold;color:#4F4F4F;}
.style4 {font-size: 12px; background-color:#D2D2D2; text-align: center; color:#FFFFFF}
.style5 {font-size: 12px; background-color:#D2D2D2; text-align: center; font-weight: bold; color:#333333}
.style6 {font-size: 11px; text-align:center;color:#3300FF}
.style7 {font-size: 10px}
.style8 {font-size: 12px;text-align:right}
.style9 {font-size: 12px;text-align:left}
.style20 {font-size: 24px; font-weight: bold;color:#4F4F4F;text-align:center;}
.style11 {font-size: 12px;text-align:center; font-weight: bold;color:#FF0033}
.style10 {font-size: 12px;font-weight: bold; text-align:center; color:#3E3E3E}
.style12 {font-size: 12px;font-weight: bold; text-align:center; color:#000000}
.style13 {font-size: 12px;font-weight: bold; text-align:center; color:#0000FF}
.style17 {font-size: 22px;font-weight:bold;text-align:center; color:#FF0000; font-family:"����"}
.style14 {font-size: 12px;text-align:right; font-weight: bold;color:#3E3E3E}
.line_name {font-size: 4px; color:#EFEFEF; text-align:center}
#bg_color {background:#E6E6E6}
#not_Data {background:#FFDFDF}
-->
</style>
<OBJECT ID="IEPrint" WIDTH="1" HEIGHT="1" CLASSID="CLSID:F290B058-CB26-460E-B3D4-8F36AEEDBE44" >
</OBJECT>
<SCRIPT LANGUAGE="JavaScript">
function print_page()
{
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
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td >
            <table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="845" height="5">&nbsp;</td>
                    <td width="115" height="5">&nbsp;</td>
                </tr>
                <tr>
                    <td width="860" height="80" class="style20">��ǰ�� ������� ���� </td>
                    <td width="100">
                        <table width="90" border="1" cellpadding="0" cellspacing="0" bordercolor="#FF0000" >
                            <tr>
                                <td width="119" height="70" class="style17">
                                    <table width="90" border="0" cellspacing="5" cellpadding="0">
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
                </tr>
                <tr>
                    <td height="340" colspan="2" valign="top">
                        <table width="955" height="21" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                            <tr>
                                <td width="79" class="style5">Project No. </td>
                                <td width="90" bgcolor="#FFFFFF" class="style9">&nbsp;<%=pjt_no%></td>
                                <td width="60" class="style5">�������</td>
                                <td width="380" bgcolor="#FFFFFF" class="style9">&nbsp;<%=request_txt%></td>
                                <td width="50" class="style5">����</td>
                                <td width="123" bgcolor="#FFFFFF" class="style9">&nbsp;<%=customer_F%></td>
                                <td width="38" class="style5">����</td>
                                <td width="126" bgcolor="#FFFFFF" class="style9">&nbsp;<%=car_type%></td>
                            </tr>
                        </table>
                        <table width="906" border="0" cellpadding="0" cellspacing="1">
                            <tr>
                                <td height="22" colspan="8" valign="bottom" bgcolor="#FFFFFF" class="style3">1. ������� ��Ȳ </td>
                            </tr>
                        </table>
                        <table width="955" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                            <tr>
                                <td colspan="3" class="style5">��ǰ Level </td>
                                <td width="215" height="35" class="style5">ǰ��</td>
                                <td width="30" class="style5">U/S</td>
                                <td width="50" class="style5">����</td>
                                <td width="50" class="style5">�빫��</td>
                                <td width="50" class="style5">���</td>
                                <td width="50" class="style5">����<br>����</td>
                                <td width="50" class="style5">������</td>
                                <td width="50" class="style5">������</td>
                                <td width="50" class="style5">�ѿ���</td>
                                <td width="70" class="style5">�ǸŸ�ǥ��</td>
                                <td width="50" class="style5">������</td>
                                <td width="79" class="style5">�����Ǹŷ�<br>[���/��]</td>
                                <td width="60" class="style5">���</td>
                            </tr>
<%
    t = 0;
    p = 0;
    q = 0;
    for(int j=0; j<P_count;j++){
        if(!"".equals(ket_cost[j][p][q]) || !"0".equals(ket_cost[j][p][q])){
            ket_cost[j][p][q] = ket_cost[j][p][q];
            if("main".equals(case_type_admin_t[j][p][q])){
                bg_co_txt = "#CBDAE9";
                if("ok".equals(DB_case_2)){
                    case_type_txt = "1��";
                }else{
                    case_type_txt = pro_1[j][p][q];
                }
            }else{
                k = Integer.toString(j - 1);
                bg_co_txt = "#FFFFCC";
                case_type_txt = "2��";
            }
        }else{
            if(!"0".equals(target_cost[j][p][q]) || !"".equals(target_cost[j][p][q])){
                ket_cost[j][p][q] = Double.toString(StringUtil.nullDouble(actual_cost[j][p][q]) / ((100 - StringUtil.nullDouble(target_cost[j][p][q])) / 100));
            }else{
                ket_cost[j][p][q] = Double.toString(StringUtil.nullDouble(actual_cost[j][p][q]) / 0.7);
            }
            ket_cost[j][p][q] = String.format("%.2f", StringUtil.nullDouble(ket_cost[j][p][q]));

            if("main".equals(case_type_admin_t[j][p][q])){
                earn_rate[j][p][q] = Double.toString(1 - (StringUtil.nullDouble(actual_cost[j][p][q]) / StringUtil.nullDouble(ket_cost[j][0][0])));
                bg_co_txt = "#CBDAE9";
                if("ok".equals(DB_case_2)){
                    case_type_txt = "1��";
                }else{
                    case_type_txt = pro_1[j][p][q];
                }
            }else{
                k = Integer.toString(j - 1);
                earn_rate[j][p][q] = Double.toString(1 - (StringUtil.nullDouble(actual_cost[j][p][q]) / StringUtil.nullDouble(ket_cost[j][0][0])));
                bg_co_txt = "#FFFFCC";
                case_type_txt = "2��";
            }
        }

%>
                            <tr>
                                <td width="28" height="18" bgcolor="<%=bg_co_txt%>" class="style1"><input name="p_low_add_<%=j%>" type="button" class="style7" value=" + "disabled ></td>
                                <td width="28" height="18" bgcolor="<%=bg_co_txt%>" class="style1">&nbsp;</td>
                                <td width="28" height="18" bgcolor="<%=bg_co_txt%>" class="style1">&nbsp;</td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style3">&nbsp;<%=part_name[j][p][q]%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=usage[j][p][q]%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=String.format("%.1f", StringUtil.nullDouble(sum_m_total_cost[j][0][0]))%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=String.format("%.1f", StringUtil.nullDouble(sum_labor_cost[j][0][0]))%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=String.format("%.1f", StringUtil.nullDouble(sum_common_cost[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=String.format("%.1f", StringUtil.nullDouble(sum_sum_meterial[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=String.format("%.1f", StringUtil.nullDouble(sum_ad_cost[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%
        if("Insert".equals(pro_type[j][p][q])){
            out.print(String.format("%.1f", (StringUtil.nullDouble(sum_g_depr_cost[j][0][0]) + StringUtil.nullDouble(g_depr_cost[j][0][0]))));
        }else{
            out.print(String.format("%.1f", StringUtil.nullDouble(sum_g_depr_cost[j][0][0])));
        }
//1015
%>
                                </td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style12"><%=String.format("%.2f", StringUtil.nullDouble(actual_cost[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=ket_cost[j][0][0]%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="<%if(Double.parseDouble(StringUtil.checkNullZero(earn_rate[j][p][q])) > 0){out.print("style13");}else{out.print("style11");}%>"><%
        if(!"".equals(earn_rate[j][p][q]) && !"0".equals(earn_rate[j][p][q])){
            out.print(String.format("%.1f", StringUtil.nullDouble(earn_rate[j][p][q])*100)+"%");
        }
%></td>
                                <td height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style14">
<%
        if(!"".equals(avg_su[j][p][q]) && !"0".equals(avg_su[j][p][q])){
            out.print(String.format("%.0f", (StringUtil.nullDouble(avg_su[j][p][q]) / StringUtil.nullDouble(a_su_count[j][p][q])))+"[õ��]");
        }
%></td>
                                <td width="60" height="18" valign="bottom" bgcolor="<%=bg_co_txt%>" class="style10"><%=case_type_txt%></td>
                            </tr>
<%
        if (!"0".equals(case_count_1[j])){
%>
                            <tr>
                                <td bgcolor="<%=bg_co_txt%>" class="style1">&nbsp;</td>
                                <td bgcolor="#F2F5F9" class="style1">&nbsp;</td>
                                <td bgcolor="#F2F5F9" class="style1">&nbsp;</td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style9">&nbsp;&nbsp;&nbsp;&nbsp;����</td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1">&nbsp;</td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%if("Insert".equals(pro_type[j][p][q]) || "���ձ���".equals(part_type[j][p][q])){out.print(String.format("%.2f", StringUtil.nullDouble((m_total_cost[j][p][q]))));}%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(labor_cost[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(common_cost[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%if("Insert".equals(pro_type[j][p][q]) || "���ձ���".equals(part_type[j][p][q])){out.print(String.format("%.1f", StringUtil.nullDouble((sum_meterial[j][p][q]))));}else{out.print(String.format("%.1f", (StringUtil.nullDouble(sum_meterial[j][p][q]) - StringUtil.nullDouble(m_total_cost[j][p][q]))));}%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(ad_cost[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(g_depr_cost[j][p][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#D8D8D8" class="style12"><%if("Insert".equals(pro_type[j][p][q]) || "���ձ���".equals(part_type[j][p][q])){out.print(String.format("%.2f", (StringUtil.nullDouble(sum_meterial[j][p][q]) + StringUtil.nullDouble(ad_cost[j][p][q]) + StringUtil.nullDouble(g_depr_cost[j][p][q]))));}else{out.print(String.format("%.2f", (StringUtil.nullDouble(sum_meterial[j][p][q]) - StringUtil.nullDouble(m_total_cost[j][p][q]) + StringUtil.nullDouble(ad_cost[j][p][q]) + StringUtil.nullDouble(g_depr_cost[j][p][q]))));}%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1">&nbsp;</td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style11">&nbsp;</td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1">&nbsp;</td>
                                <td width="60" height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%if("����".equals(make_type[j][p][q])){out.print("����");}else if("����".equals(pro_1[j][p][q]) || "����".equals(pro_1[j][p][q])){out.print("����");}else{out.print(pro_1[j][p][q]);}%></td>
                            </tr>
<%
        }
        if(!"".equals(case_count_1[j]) || !"0".equals(case_count_1[j])){
            for(int fk=1;fk<=Integer.parseInt(StringUtil.checkNullZero(case_count_1[j]));fk++){
                if("sub_assy".equals(pro_type[j][fk][q])){
                    sum_meterial[j][fk][q] = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(labor_cost[j][fk][q]))) + StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(common_cost[j][fk][q]))));
                }//üũ...
%>
                            <tr>
                                <td height="18" bgcolor="<%=bg_co_txt%>" class="style1">&nbsp;</td>
                                <td bgcolor="#F2F5F9" class="style1"><input name="p_low_add_<%=j%>2" type="button" class="style7" value=" + "disabled ></td>
                                <td bgcolor="#F2F5F9" class="style1">&nbsp;</td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style9">&nbsp;&nbsp;&nbsp;&nbsp;<%=part_name[j][fk][t]%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=usage[j][fk][q]%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%if(!"sub_assy".equals(pro_type[j][fk][q])){if(!"".equals(m_total_cost[j][fk][q])){out.print(String.format("%.1f", StringUtil.nullDouble(m_total_cost[j][fk][q])));}}%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%if(!"assy".equals(pro_type[j][fk][q]) && !"����".equals(make_type[j][fk][q])){if(!"".equals(m_total_cost[j][fk][q])){out.print(String.format("%.1f", StringUtil.nullDouble(labor_cost[j][fk][q])));}}%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(common_cost[j][fk][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(sum_meterial[j][fk][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(ad_cost[j][fk][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(g_depr_cost[j][fk][q]))%></td>
                                <td height="18" valign="bottom" bgcolor="#D8D8D8" class="style12"><%
                if("sub_assy".equals(pro_type[j][fk][q])){
                    out.print(String.format("%.2f", StringUtil.nullDouble(actual_cost[j][fk][q]) - StringUtil.nullDouble(m_total_cost[j][fk][q])));
                }else if("sub_Insert".equals(pro_type[j][fk][q])){
                    out.print(String.format("%.2f", StringUtil.nullDouble(sum_meterial[j][fk][q]) + StringUtil.nullDouble(ad_cost[j][fk][q]) + StringUtil.nullDouble(g_depr_cost[j][fk][q])));
                }else{
                    out.print(String.format("%.2f", StringUtil.nullDouble(actual_cost[j][fk][q])));
                }
%></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style11"></td>
                                <td height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"></td>
                                <td width="60" height="18" valign="bottom" bgcolor="#F2F5F9" class="style1"><%if("����".equals(make_type[j][fk][q])){out.print("����");}else if("����".equals(pro_1[j][fk][q]) || "����".equals(pro_1[j][fk][q])){out.print("����");}else{out.print(pro_1[j][fk][q]);}%></td>
                            </tr>
<%
                if(!"".equals(case_count_2[j][fk])){
                    for(int r=1; r<=Integer.parseInt(case_count_2[j][fk]);r++){
%>
                            <tr>
                                <td bgcolor="<%=bg_co_txt%>" class="style1">&nbsp;</td>
                                <td bgcolor="#F2F5F9" class="style1">&nbsp;</td>
                                <td bgcolor="#FCFDFE" class="style1"><input name="p_low_add_<%=j%>3" type="button" class="style7" value=" + "disabled ></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style9">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=part_name[j][fk][r]%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%=usage[j][fk][r]%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(m_total_cost[j][fk][r]))%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(labor_cost[j][fk][r]))%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(common_cost[j][fk][r]))%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(sum_meterial[j][fk][r]))%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(ad_cost[j][fk][r]))%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%=String.format("%.1f", StringUtil.nullDouble(g_depr_cost[j][fk][r]))%></td>
                                <td height="18" valign="bottom" bgcolor="#D8D8D8" class="style12"><%=String.format("%.1f", StringUtil.nullDouble(actual_cost[j][fk][r]))%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%//1122%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style11"><%%></td>
                                <td height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%%></td>
                                <td width="60" height="18" valign="bottom" bgcolor="#FCFDFE" class="style1"><%if("����".equals(make_type[j][fk][r])){out.print("����");}else if("����".equals(pro_1[j][fk][r]) || "����".equals(pro_1[j][fk][r])){out.print("����");}else{out.print(pro_1[j][fk][r]);}%></td>
                            </tr>
<%
                    } // r for end
                }  // case_count_2 if end
            } // case_count_1 for end
        } // case_count_1 if end
        p_m_total_cost[j][p][q]= Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_m_total_cost[j][0][0]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][0][0]))));
        p_labor_cost[j][p][q]  = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_labor_cost[j][0][0]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][0][0]))));
        p_common_cost[j][p][q]  = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_common_cost[j][0][0]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][0][0]))));
        p_sum_meterial[j][p][q]  = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_sum_meterial[j][0][0]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][0][0]))));
        p_ad_cost[j][p][q] = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_ad_cost[j][0][0]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][0][0]))));

        if(!"".equals(g_depr_cost[j][p][q]) || !"0".equals(g_depr_cost[j][p][q])){
            if("Insert".equals(pro_type[j][p][q])){
                p_g_depr_cost[j][p][q] = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_g_depr_cost[j][p][q]) + StringUtil.nullDouble(g_depr_cost[j][0][0]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][0][0]))));
            }else{
                p_g_depr_cost[j][p][q] = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_g_depr_cost[j][p][q]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][0][0]))));
            }
        }else{
            p_g_depr_cost[j][p][q] = "0";
        }
        p_actual_cost[j][p][q] = Double.toString(StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][p][q]))) / StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(actual_cost[j][p][q]))));
%>
                            <tr>
                                <td height="21" colspan="4" bgcolor="#FFFFFF" class="style6">�ѿ������ ������ </td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style6"><%=String.format("%.1f", StringUtil.nullDouble(p_m_total_cost[j][p][q])*100)%>%</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style6"><%=String.format("%.1f", StringUtil.nullDouble(p_labor_cost[j][p][q])*100)%>%</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style6"><%=String.format("%.1f", StringUtil.nullDouble(p_common_cost[j][p][q])*100)%>%</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style6"><%=String.format("%.1f", StringUtil.nullDouble(p_sum_meterial[j][p][q])*100)%>%</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style6"><%=String.format("%.1f", StringUtil.nullDouble(p_ad_cost[j][p][q])*100)%>%</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style6"><%=String.format("%.1f", StringUtil.nullDouble(p_g_depr_cost[j][p][q])*100)%>%</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style6"><%=String.format("%.1f", StringUtil.nullDouble(p_actual_cost[j][p][q])*100)%>%</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                <td height="21" valign="bottom" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                                <td width="60" height="21" valign="bottom" bgcolor="#FFFFFF" class="style1">&nbsp;</td>
                            </tr>
<%
    } // j for end
%>
                        </table>
                        <table width="906" border="0" cellpadding="0" cellspacing="1">
                            <tr>
                                <td height="22" valign="bottom" bgcolor="#FFFFFF" class="style3">2. �������� �� �����ǰ�		  </td>
                            </tr>
                        </table>
                        <table width="955" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                            <tr>
                                <td width="476" height="150" valign="top" bgcolor="#FFFFFF"><textarea name="note_5" cols="75" rows="13" class="style9" readonly><%=note_5%></textarea></td>
                                <td width="476" valign="top" bgcolor="#FFFFFF"><textarea name="note_5_1" cols="75" rows="13" class="style9" readonly><%=note_5_1%></textarea></td>
                            </tr>
                        </table>
                        <table width="906" border="0" cellpadding="0" cellspacing="1">
                            <tr>
                                <td height="1" bgcolor="#FFFFFF" class="style3"></td>
                            </tr>
                        </table>
                        <table width="955" height="21" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
                            <tr>
                                <td width="66" class="style5">�ۼ�����</td>
                                <td width="164" bgcolor="#FFFFFF" class="style9">&nbsp;<%=f_day%></td>
                                <td width="66" class="style5">�ۼ���</td>
                                <td width="177" bgcolor="#FFFFFF" class="style9">&nbsp;<%=w_name%></td>
                                <td width="66" class="style5">����ó</td>
                                <td width="204" bgcolor="#FFFFFF" class="style9">&nbsp;
                                <%
                                out.println(StringUtil.ChangeDeptNoCPString(team, f_name));
                                /*
                                if("1".equals(team)){
                                	out.print("Ŀ���Ϳ�������1��"+f_name);
                                }else if("11".equals(team)){
                                	out.print("Ŀ���Ϳ�������2��"+f_name);
                                }else if("12".equals(team)){
                                	out.print("Ŀ���Ϳ�������3��"+f_name);
                                }else if("13".equals(team)){
                                	out.print("Ŀ���Ϳ������߼�Ÿ"+f_name);
                                }else if("22".equals(team)){
                                	out.print("�����ⰳ��1��"+f_name);
                                }else if("23".equals(team)){
                                	out.print("�����ⰳ��2��"+f_name);
                                }else if("24".equals(team)){
                                	out.print("�����ⰳ��3��"+f_name);
                                }else if("3".equals(team)){
                                	out.print("��������3��"+f_name);
                                }else if("4".equals(team)){
                                	out.print("��������4��"+f_name);
                                }else if("6".equals(team)){
                                	out.print("��������6��"+f_name);
                                }else if("21".equals(team)){
                                	out.print("���۰���1��"+f_name);
                                }*/
                                %>
                                </td>
                                <td width="204" bgcolor="#FFFFFF" class="style9">&nbsp;<%="�ڵ��������� "+a_name%></td>
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
