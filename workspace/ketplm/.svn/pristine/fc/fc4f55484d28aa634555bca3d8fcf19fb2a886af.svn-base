package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import e3ps.cost.migration.CostReportPropDTO;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;

public class CostReportDao {

    private Connection conn;

    public CostReportDao(Connection conn) {
	this.conn = conn;
    }

    public CostReportDao() {
	super();
    }

    /**
     * 함수명 : getWorkList 설명 :산출작업DB-cost_work
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 최윤정 작성일자 : 2012. 7. 25.
     */

    // 원가DB 조회-신규
    public ArrayList getWorkList(String t_chk_list) throws Exception {

	String[] chk_list = t_chk_list.split(",");
	String qMark = "";
	for (int i = 1; i < chk_list.length; i++) {

	    if (chk_list.length > 1 && chk_list.length - 1 != i) {
		qMark = qMark + "?, ";
	    } else {
		qMark = qMark + "?";
	    }
	}

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT pk_cr_group, table_row,depr_cost,mold_cost, \n");
	sb.append("case_count_1, case_count_2, pro_type, part_type, pjt_name, pjt_no, part_no, part_name, team, f_name, a_name, w_name, dev_step, \n");
	sb.append("request_txt, car_type, app_part, customer_F, pk_cw, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, \n");
	sb.append("start_pro, store, dest, client_cost, ket_cost, target_cost, usage, pro_1, eff_value, actual_cost, lme_ton, u_ex_rate, earn_rate, \n");
	sb.append("group_no, m_su, m_maker, grade_name, grade_color, etc_cost, yazaki_ro, sul_cost, line_su, m_co_chk, \n");
	sb.append("ROW_NUMBER() OVER (partition by group_no order by pk_cw ) rn \n");
	// 결재관련
	// f_day,
	// Leader_day,
	// Leader_name,
	// step_no,
	// approval,
	sb.append(" FROM cost_productInfo A, cost_work B ");
	sb.append(" WHERE B.fk_pid = A.pk_pid AND pk_cw in (" + qMark + ") ORDER BY part_name");
	// System.out.println(sb.toString() +" == "+ t_chk_list);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    for (int i = 1; i < chk_list.length; i++) {
		pstmt.setString(i, chk_list[i]);
	    }
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("table_row", StringUtil.checkNull(rs.getString("table_row")));
		SearchMap.put("depr_cost", StringUtil.checkNull(rs.getString("depr_cost")));
		SearchMap.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		SearchMap.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		SearchMap.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("start_pro", StringUtil.checkNull(rs.getString("start_pro")));
		SearchMap.put("store", StringUtil.checkNull(rs.getString("store")));
		SearchMap.put("dest", StringUtil.checkNull(rs.getString("dest")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchMap.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		SearchMap.put("lme_ton", StringUtil.checkNull(rs.getString("lme_ton")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("earn_rate", StringUtil.checkNull(rs.getString("earn_rate")));

		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		SearchMap.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchMap.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		SearchMap.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		SearchMap.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		SearchMap.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		SearchMap.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		SearchMap.put("m_co_chk", StringUtil.checkNull(rs.getString("m_co_chk")));
		SearchMap.put("rn", StringUtil.checkNull(rs.getString("rn")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getWorkList3 설명 :보고서의 산출결과 불러올때의 원가 계산 결과 보고 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 8. 27.
     */
    public ArrayList getWorkList3(String t_chk_list) throws Exception {

	String[] chk_list = t_chk_list.split(",");
	String qMark = "";
	for (int i = 1; i < chk_list.length; i++) {

	    if (chk_list.length > 1 && chk_list.length - 1 != i) {
		qMark = qMark + "?, ";
	    } else {
		qMark = qMark + "?";
	    }
	}

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT group_no, part_name, \n");
	sb.append("       substr(Max(sys_connect_by_path(usage,',')),2) as usage, \n");
	sb.append("       substr(Max(sys_connect_by_path(client_cost,',')),2) as client_cost, \n");
	sb.append("       substr(Max(sys_connect_by_path(ket_cost,',')),2) as ket_cost, \n");
	sb.append("       substr(Max(sys_connect_by_path(target_cost,',')),2) as target_cost, \n");
	sb.append("       substr(Max(sys_connect_by_path(actual_cost,',')),2) as actual_cost, \n");
	sb.append("       substr(Max(sys_connect_by_path(earn_rate,',')),2) as earn_rate, \n");

	sb.append("       substr(Max(sys_connect_by_path(m_co_chk,',')),2) as m_co_chk,  \n");
	sb.append("       substr(Max(sys_connect_by_path(pro_type,',')),2) as pro_type,  \n");
	sb.append("       substr(Max(sys_connect_by_path(m_su,',')),2) as m_su,  \n");
	sb.append("       substr(Max(sys_connect_by_path(mold_cost,',')),2) as mold_cost, \n");
	sb.append("       substr(Max(sys_connect_by_path(m_maker,',')),2) as m_maker, \n");
	sb.append("       substr(Max(sys_connect_by_path(grade_name,',')),2) as grade_name, \n");
	sb.append("       substr(Max(sys_connect_by_path(grade_color,',')),2) as grade_color,  \n");
	sb.append("       substr(Max(sys_connect_by_path(etc_cost,',')),2) as etc_cost,  \n");
	sb.append("       substr(Max(sys_connect_by_path(yazaki_ro,',')),2) as yazaki_ro,  \n");
	sb.append("       substr(Max(sys_connect_by_path(sul_cost,',')),2) as sul_cost,  \n");
	sb.append("       substr(Max(sys_connect_by_path(line_su,',')),2) as line_su \n");

	sb.append("    FROM (SELECT group_no, part_name, usage, client_cost, ket_cost, target_cost, actual_cost, earn_rate, \n");
	sb.append("                               pro_type, m_su, mold_cost, m_maker, grade_name, grade_color, etc_cost, yazaki_ro, sul_cost, line_su, m_co_chk, \n");
	sb.append("                  ROW_NUMBER() OVER (partition by group_no order by pk_cw ) rn \n");
	sb.append("              FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid AND pk_cw in (" + qMark + ")) \n");
	sb.append("  start with rn = 1 connect by prior group_no = group_no and prior rn = rn-1 \n");
	sb.append("    GROUP BY group_no, part_name \n");

	// System.out.println(sb.toString() +" == "+ t_chk_list);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    for (int i = 1; i < chk_list.length; i++) {
		pstmt.setString(i, chk_list[i]);
	    }
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		SearchMap.put("earn_rate", StringUtil.checkNull(rs.getString("earn_rate")));

		SearchMap.put("m_co_chk", StringUtil.checkNull(rs.getString("m_co_chk")));
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		SearchMap.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		SearchMap.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchMap.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		SearchMap.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		SearchMap.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		SearchMap.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		SearchMap.put("line_su", StringUtil.checkNull(rs.getString("line_su")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportList 설명 :보고서DB-cost_report
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 최윤정 작성일자 : 2012. 7. 3.
     */

    // 보고서DB 조회
    public ArrayList getReportList() throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT table_row,pass_type, w_name, app_part, case_1_note, case_2_note,case_3_note, ");
	sb.append(" case_to_note_1, case_to_note_2, case_to_note_3, ");
	sb.append(" pk_cr_group, FK_cost_request, dev_step,pjt_name, ");
	sb.append(" pjt_no, part_name, target_cost, team, f_name, a_name, ");
	sb.append(" request_txt, car_type, customer_F, report_dest,  ");
	sb.append(" lme_cu, u_ex_rate, pack_type, usage, make_type, pro_1, ");
	sb.append(" eff_value, mold_count, press_count, line_count,  ");
	sb.append(" pack_count, repack_count, mold_total_1, press_total_1,  ");
	sb.append(" line_total_1, pack_total_1, mold_total_2, press_total_2,  ");
	sb.append(" line_total_2, pack_total_2, mold_total_3, press_total_3,   ");
	sb.append(" line_total_3, pack_total_3, repack_total_1, repack_total_2, ");
	sb.append(" repack_total_3, m_depr_stan, p_depr_stan, L_depr_stan,  ");
	sb.append(" pack_depr_stan, repack_depr_stan, su_stan_day, note_1, ");
	sb.append(" note_2, note_3, note_4, note_5, note_5_1, ");
	sb.append(" tocost_year, select_cost,  ");
	sb.append(" su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, ");
	sb.append(" su_year_7, su_year_8, client_cost, pro_usage, ket_cost, ");
	sb.append(" target_cost, actual_cost_1, earn_rate_1, actual_cost_2, earn_rate_2, ");
	sb.append(" actual_cost_3, earn_rate_3, actual_cost_sum_1, earn_rate_sum_1,  ");
	sb.append(" actual_cost_sum_2, earn_rate_sum_2, actual_cost_sum_3, earn_rate_sum_3,");
	sb.append(" total_sales_1, total_sales_2, total_sales_3, total_sales_4, ");
	sb.append(" total_sales_5, total_sales_6, total_sales_7, total_sales_8, ");
	sb.append(" profit_1, profit_2, profit_3, profit_4, profit_5, profit_6, ");
	sb.append(" profit_7, profit_8, per_profit_1, per_profit_2, per_profit_3, ");
	sb.append(" per_profit_4, per_profit_5, per_profit_6, per_profit_7, per_profit_8 ");
	sb.append(" FROM cost_report A, cost_productInfo B ");
	sb.append("WHERE A.fk_pid = B.pk_pid AND crp_group = '914'");
	// sb.append("WHERE A.fk_pid = B.pk_pid AND crp_group = '1476'");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, pkcr_group);
	    // pstmt.setInt(2, Integer.parseInt(rev_no));
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("table_row", StringUtil.checkNull(rs.getString("table_row")));
		SearchMap.put("pass_type", StringUtil.checkNull(rs.getString("pass_type")));
		// SearchMap.put("f_day",StringUtil.checkNull(rs.getString("f_day")));
		// SearchMap.put("Leader_day",StringUtil.checkNull(rs.getString("Leader_day")));
		// SearchMap.put("Leader_name",StringUtil.checkNull(rs.getString("Leader_name")));
		// SearchMap.put("JB_day",StringUtil.checkNull(rs.getString("JB_day")));
		// SearchMap.put("JB_name",StringUtil.checkNull(rs.getString("JB_name")));
		// SearchMap.put("p_leader_day",StringUtil.checkNull(rs.getString("p_leader_day")));
		// SearchMap.put("r_owner_day",StringUtil.checkNull(rs.getString("r_owner_day")));
		// SearchMap.put("r_pre_day",StringUtil.checkNull(rs.getString("r_pre_day")));
		// SearchMap.put("approval",StringUtil.checkNull(rs.getString("approval")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		SearchMap.put("case_1_note", StringUtil.checkNull(rs.getString("case_1_note")));
		SearchMap.put("case_2_note", StringUtil.checkNull(rs.getString("case_2_note")));
		SearchMap.put("case_3_note", StringUtil.checkNull(rs.getString("case_3_note")));
		SearchMap.put("case_to_note_1", StringUtil.checkNull(rs.getString("case_to_note_1")));
		SearchMap.put("case_to_note_2", StringUtil.checkNull(rs.getString("case_to_note_2")));
		SearchMap.put("case_to_note_3", StringUtil.checkNull(rs.getString("case_to_note_3")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("report_dest", StringUtil.checkNull(rs.getString("report_dest")));
		SearchMap.put("lme_cu", StringUtil.checkNull(rs.getString("lme_cu")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchMap.put("mold_count", StringUtil.checkNull(rs.getString("mold_count")));
		SearchMap.put("press_count", StringUtil.checkNull(rs.getString("press_count")));
		SearchMap.put("line_count", StringUtil.checkNull(rs.getString("line_count")));
		SearchMap.put("pack_count", StringUtil.checkNull(rs.getString("pack_count")));
		SearchMap.put("repack_count", StringUtil.checkNull(rs.getString("repack_count")));
		SearchMap.put("mold_total_1", StringUtil.checkNull(rs.getString("mold_total_1")));
		SearchMap.put("press_total_1", StringUtil.checkNull(rs.getString("press_total_1")));
		SearchMap.put("line_total_1", StringUtil.checkNull(rs.getString("line_total_1")));
		SearchMap.put("pack_total_1", StringUtil.checkNull(rs.getString("pack_total_1")));
		SearchMap.put("mold_total_2", StringUtil.checkNull(rs.getString("mold_total_2")));
		SearchMap.put("press_total_2", StringUtil.checkNull(rs.getString("press_total_2")));
		SearchMap.put("line_total_2", StringUtil.checkNull(rs.getString("line_total_2")));
		SearchMap.put("pack_total_2", StringUtil.checkNull(rs.getString("pack_total_2")));
		SearchMap.put("mold_total_3", StringUtil.checkNull(rs.getString("mold_total_3")));
		SearchMap.put("press_total_3", StringUtil.checkNull(rs.getString("press_total_3")));
		SearchMap.put("line_total_3", StringUtil.checkNull(rs.getString("line_total_3")));
		SearchMap.put("pack_total_3", StringUtil.checkNull(rs.getString("pack_total_3")));
		SearchMap.put("repack_total_1", StringUtil.checkNull(rs.getString("repack_total_1")));
		SearchMap.put("repack_total_2", StringUtil.checkNull(rs.getString("repack_total_2")));
		SearchMap.put("repack_total_3", StringUtil.checkNull(rs.getString("repack_total_3")));
		SearchMap.put("m_depr_stan", StringUtil.checkNull(rs.getString("m_depr_stan")));
		SearchMap.put("p_depr_stan", StringUtil.checkNull(rs.getString("p_depr_stan")));
		SearchMap.put("L_depr_stan", StringUtil.checkNull(rs.getString("L_depr_stan")));
		SearchMap.put("pack_depr_stan", StringUtil.checkNull(rs.getString("pack_depr_stan")));
		SearchMap.put("repack_depr_stan", StringUtil.checkNull(rs.getString("repack_depr_stan")));
		SearchMap.put("su_stan_day", StringUtil.checkNull(rs.getString("su_stan_day")));
		SearchMap.put("note_1", StringUtil.checkNull(rs.getString("note_1")));
		SearchMap.put("note_2", StringUtil.checkNull(rs.getString("note_2")));
		SearchMap.put("note_3", StringUtil.checkNull(rs.getString("note_3")));
		SearchMap.put("note_4", StringUtil.checkNull(rs.getString("note_4")));
		SearchMap.put("note_5", StringUtil.checkNull(rs.getString("note_5")));
		SearchMap.put("note_5_1", StringUtil.checkNull(rs.getString("note_5_1")));
		SearchMap.put("tocost_year", StringUtil.checkNull(rs.getString("tocost_year")));
		SearchMap.put("select_cost", StringUtil.checkNull(rs.getString("select_cost")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("pro_usage", StringUtil.checkNull(rs.getString("pro_usage")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("actual_cost_1", StringUtil.checkNull(rs.getString("actual_cost_1")));
		SearchMap.put("earn_rate_1", StringUtil.checkNull(rs.getString("earn_rate_1")));
		SearchMap.put("actual_cost_2", StringUtil.checkNull(rs.getString("actual_cost_2")));
		SearchMap.put("earn_rate_2", StringUtil.checkNull(rs.getString("earn_rate_2")));
		SearchMap.put("actual_cost_3", StringUtil.checkNull(rs.getString("actual_cost_3")));
		SearchMap.put("earn_rate_3", StringUtil.checkNull(rs.getString("earn_rate_3")));
		SearchMap.put("actual_cost_sum_1", StringUtil.checkNull(rs.getString("actual_cost_sum_1")));
		SearchMap.put("earn_rate_sum_1", StringUtil.checkNull(rs.getString("earn_rate_sum_1")));
		SearchMap.put("actual_cost_sum_2", StringUtil.checkNull(rs.getString("actual_cost_sum_2")));
		SearchMap.put("earn_rate_sum_2", StringUtil.checkNull(rs.getString("earn_rate_sum_2")));
		SearchMap.put("actual_cost_sum_3", StringUtil.checkNull(rs.getString("actual_cost_sum_3")));
		SearchMap.put("earn_rate_sum_3", StringUtil.checkNull(rs.getString("earn_rate_sum_3")));
		SearchMap.put("total_sales_1", StringUtil.checkNull(rs.getString("total_sales_1")));
		SearchMap.put("total_sales_2", StringUtil.checkNull(rs.getString("total_sales_2")));
		SearchMap.put("total_sales_3", StringUtil.checkNull(rs.getString("total_sales_3")));
		SearchMap.put("total_sales_4", StringUtil.checkNull(rs.getString("total_sales_4")));
		SearchMap.put("total_sales_5", StringUtil.checkNull(rs.getString("total_sales_5")));
		SearchMap.put("total_sales_6", StringUtil.checkNull(rs.getString("total_sales_6")));
		SearchMap.put("total_sales_7", StringUtil.checkNull(rs.getString("total_sales_7")));
		SearchMap.put("total_sales_8", StringUtil.checkNull(rs.getString("total_sales_8")));
		SearchMap.put("profit_1", StringUtil.checkNull(rs.getString("profit_1")));
		SearchMap.put("profit_2", StringUtil.checkNull(rs.getString("profit_2")));
		SearchMap.put("profit_3", StringUtil.checkNull(rs.getString("profit_3")));
		SearchMap.put("profit_4", StringUtil.checkNull(rs.getString("profit_4")));
		SearchMap.put("profit_5", StringUtil.checkNull(rs.getString("profit_5")));
		SearchMap.put("profit_6", StringUtil.checkNull(rs.getString("profit_6")));
		SearchMap.put("profit_7", StringUtil.checkNull(rs.getString("profit_7")));
		SearchMap.put("profit_8", StringUtil.checkNull(rs.getString("profit_8")));
		SearchMap.put("per_profit_1", StringUtil.checkNull(rs.getString("per_profit_1")));
		SearchMap.put("per_profit_2", StringUtil.checkNull(rs.getString("per_profit_2")));
		SearchMap.put("per_profit_3", StringUtil.checkNull(rs.getString("per_profit_3")));
		SearchMap.put("per_profit_4", StringUtil.checkNull(rs.getString("per_profit_4")));
		SearchMap.put("per_profit_5", StringUtil.checkNull(rs.getString("per_profit_5")));
		SearchMap.put("per_profit_6", StringUtil.checkNull(rs.getString("per_profit_6")));
		SearchMap.put("per_profit_7", StringUtil.checkNull(rs.getString("per_profit_7")));
		SearchMap.put("per_profit_8", StringUtil.checkNull(rs.getString("per_profit_8")));
		SearchList.add(SearchMap);

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getUsChange 설명 :보고서DB-cost_report/적용usage 변경
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 최윤정 작성일자 : 2012. 7. 17.
     */

    // 보고서DB 조회
    public ArrayList getUsChange(String[] pro_usage) throws Exception {

	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int p = 0; // 원가계산결과 적용u/s 배열인수
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT	table_row,pass_type, w_name, app_part, case_1_note, case_2_note,case_3_note, ");
	sb.append(" 		case_to_note_1, case_to_note_2, case_to_note_3, ");
	sb.append(" 		pk_cr_group, FK_cost_request, dev_step,pjt_name, ");
	sb.append(" 		pjt_no, part_name, target_cost, team, f_name, a_name, ");
	sb.append(" 		request_txt, car_type, customer_F, report_dest,  ");
	sb.append(" 		lme_cu, u_ex_rate, pack_type, usage, make_type, pro_1, ");
	sb.append(" 		eff_value, mold_count, press_count, line_count,  ");
	sb.append(" 		pack_count, repack_count, mold_total_1, press_total_1,  ");
	sb.append(" 		line_total_1, pack_total_1, mold_total_2, press_total_2,  ");
	sb.append(" 		line_total_2, pack_total_2, mold_total_3, press_total_3,   ");
	sb.append(" 		line_total_3, pack_total_3, repack_total_1, repack_total_2,  ");
	sb.append(" 		repack_total_3, m_depr_stan, p_depr_stan, L_depr_stan,  ");
	sb.append(" 		pack_depr_stan, repack_depr_stan, su_stan_day, note_1, ");
	sb.append(" 		note_2, note_3, note_4, note_5, note_5_1, ");
	sb.append(" 		tocost_year, select_cost,  ");
	sb.append(" 		su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, ");
	sb.append(" 		su_year_7, su_year_8, client_cost, pro_usage, ket_cost, ");
	sb.append(" 		target_cost, actual_cost_1, earn_rate_1, actual_cost_2, earn_rate_2, ");
	sb.append(" 		actual_cost_3, earn_rate_3, actual_cost_sum_1, earn_rate_sum_1,  ");
	sb.append(" 		actual_cost_sum_2, earn_rate_sum_2, actual_cost_sum_3, earn_rate_sum_3,");
	sb.append(" 		total_sales_1, total_sales_2, total_sales_3, total_sales_4, ");
	sb.append(" 		total_sales_5, total_sales_6, total_sales_7, total_sales_8, ");
	sb.append(" 		profit_1, profit_2, profit_3, profit_4, profit_5, profit_6, ");
	sb.append(" 		profit_7, profit_8, per_profit_1, per_profit_2, per_profit_3, ");
	sb.append(" 		per_profit_4, per_profit_5, per_profit_6, per_profit_7, per_profit_8 ");
	sb.append(" FROM cost_report A, cost_productInfo B ");
	sb.append("	WHERE A.fk_pid = B.pk_pid AND crp_group = '914'");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, pkcr_group);
	    // pstmt.setInt(2, Integer.parseInt(rev_no));
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("table_row", StringUtil.checkNull(rs.getString("table_row")));
		SearchMap.put("pass_type", StringUtil.checkNull(rs.getString("pass_type")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		SearchMap.put("case_1_note", StringUtil.checkNull(rs.getString("case_1_note")));
		SearchMap.put("case_2_note", StringUtil.checkNull(rs.getString("case_2_note")));
		SearchMap.put("case_3_note", StringUtil.checkNull(rs.getString("case_3_note")));
		SearchMap.put("case_to_note_1", StringUtil.checkNull(rs.getString("case_to_note_1")));
		SearchMap.put("case_to_note_2", StringUtil.checkNull(rs.getString("case_to_note_2")));
		SearchMap.put("case_to_note_3", StringUtil.checkNull(rs.getString("case_to_note_3")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("report_dest", StringUtil.checkNull(rs.getString("report_dest")));
		SearchMap.put("lme_cu", StringUtil.checkNull(rs.getString("lme_cu")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchMap.put("mold_count", StringUtil.checkNull(rs.getString("mold_count")));
		SearchMap.put("press_count", StringUtil.checkNull(rs.getString("press_count")));
		SearchMap.put("line_count", StringUtil.checkNull(rs.getString("line_count")));
		SearchMap.put("pack_count", StringUtil.checkNull(rs.getString("pack_count")));
		SearchMap.put("repack_count", StringUtil.checkNull(rs.getString("repack_count")));
		SearchMap.put("mold_total_1", StringUtil.checkNull(rs.getString("mold_total_1")));
		SearchMap.put("press_total_1", StringUtil.checkNull(rs.getString("press_total_1")));
		SearchMap.put("line_total_1", StringUtil.checkNull(rs.getString("line_total_1")));
		SearchMap.put("pack_total_1", StringUtil.checkNull(rs.getString("pack_total_1")));
		SearchMap.put("mold_total_2", StringUtil.checkNull(rs.getString("mold_total_2")));
		SearchMap.put("press_total_2", StringUtil.checkNull(rs.getString("press_total_2")));
		SearchMap.put("line_total_2", StringUtil.checkNull(rs.getString("line_total_2")));
		SearchMap.put("pack_total_2", StringUtil.checkNull(rs.getString("pack_total_2")));
		SearchMap.put("mold_total_3", StringUtil.checkNull(rs.getString("mold_total_3")));
		SearchMap.put("press_total_3", StringUtil.checkNull(rs.getString("press_total_3")));
		SearchMap.put("line_total_3", StringUtil.checkNull(rs.getString("line_total_3")));
		SearchMap.put("pack_total_3", StringUtil.checkNull(rs.getString("pack_total_3")));
		SearchMap.put("repack_total_1", StringUtil.checkNull(rs.getString("repack_total_1")));
		SearchMap.put("repack_total_2", StringUtil.checkNull(rs.getString("repack_total_2")));
		SearchMap.put("repack_total_3", StringUtil.checkNull(rs.getString("repack_total_3")));
		SearchMap.put("m_depr_stan", StringUtil.checkNull(rs.getString("m_depr_stan")));
		SearchMap.put("p_depr_stan", StringUtil.checkNull(rs.getString("p_depr_stan")));
		SearchMap.put("L_depr_stan", StringUtil.checkNull(rs.getString("L_depr_stan")));
		SearchMap.put("pack_depr_stan", StringUtil.checkNull(rs.getString("pack_depr_stan")));
		SearchMap.put("repack_depr_stan", StringUtil.checkNull(rs.getString("repack_depr_stan")));
		SearchMap.put("su_stan_day", StringUtil.checkNull(rs.getString("su_stan_day")));
		SearchMap.put("note_1", StringUtil.checkNull(rs.getString("note_1")));
		SearchMap.put("note_2", StringUtil.checkNull(rs.getString("note_2")));
		SearchMap.put("note_3", StringUtil.checkNull(rs.getString("note_3")));
		SearchMap.put("note_4", StringUtil.checkNull(rs.getString("note_4")));
		SearchMap.put("note_5", StringUtil.checkNull(rs.getString("note_5")));
		SearchMap.put("note_5_1", StringUtil.checkNull(rs.getString("note_5_1")));
		SearchMap.put("tocost_year", StringUtil.checkNull(rs.getString("tocost_year")));
		SearchMap.put("select_cost", StringUtil.checkNull(rs.getString("select_cost")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));

		if (pro_usage == null) {
		    SearchMap.put("pro_usage", StringUtil.checkNull(rs.getString("pro_usage")));
		} else {
		    SearchMap.put("pro_usage", pro_usage[p]);
		    p++;
		}

		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("actual_cost_1", StringUtil.checkNull(rs.getString("actual_cost_1")));
		SearchMap.put("earn_rate_1", StringUtil.checkNull(rs.getString("earn_rate_1")));
		SearchMap.put("actual_cost_2", StringUtil.checkNull(rs.getString("actual_cost_2")));
		SearchMap.put("earn_rate_2", StringUtil.checkNull(rs.getString("earn_rate_2")));
		SearchMap.put("actual_cost_3", StringUtil.checkNull(rs.getString("actual_cost_3")));
		SearchMap.put("earn_rate_3", StringUtil.checkNull(rs.getString("earn_rate_3")));
		SearchMap.put("actual_cost_sum_1", StringUtil.checkNull(rs.getString("actual_cost_sum_1")));
		SearchMap.put("earn_rate_sum_1", StringUtil.checkNull(rs.getString("earn_rate_sum_1")));
		SearchMap.put("actual_cost_sum_2", StringUtil.checkNull(rs.getString("actual_cost_sum_2")));
		SearchMap.put("earn_rate_sum_2", StringUtil.checkNull(rs.getString("earn_rate_sum_2")));
		SearchMap.put("actual_cost_sum_3", StringUtil.checkNull(rs.getString("actual_cost_sum_3")));
		SearchMap.put("earn_rate_sum_3", StringUtil.checkNull(rs.getString("earn_rate_sum_3")));
		SearchMap.put("total_sales_1", StringUtil.checkNull(rs.getString("total_sales_1")));
		SearchMap.put("total_sales_2", StringUtil.checkNull(rs.getString("total_sales_2")));
		SearchMap.put("total_sales_3", StringUtil.checkNull(rs.getString("total_sales_3")));
		SearchMap.put("total_sales_4", StringUtil.checkNull(rs.getString("total_sales_4")));
		SearchMap.put("total_sales_5", StringUtil.checkNull(rs.getString("total_sales_5")));
		SearchMap.put("total_sales_6", StringUtil.checkNull(rs.getString("total_sales_6")));
		SearchMap.put("total_sales_7", StringUtil.checkNull(rs.getString("total_sales_7")));
		SearchMap.put("total_sales_8", StringUtil.checkNull(rs.getString("total_sales_8")));
		SearchMap.put("profit_1", StringUtil.checkNull(rs.getString("profit_1")));
		SearchMap.put("profit_2", StringUtil.checkNull(rs.getString("profit_2")));
		SearchMap.put("profit_3", StringUtil.checkNull(rs.getString("profit_3")));
		SearchMap.put("profit_4", StringUtil.checkNull(rs.getString("profit_4")));
		SearchMap.put("profit_5", StringUtil.checkNull(rs.getString("profit_5")));
		SearchMap.put("profit_6", StringUtil.checkNull(rs.getString("profit_6")));
		SearchMap.put("profit_7", StringUtil.checkNull(rs.getString("profit_7")));
		SearchMap.put("profit_8", StringUtil.checkNull(rs.getString("profit_8")));
		SearchMap.put("per_profit_1", StringUtil.checkNull(rs.getString("per_profit_1")));
		SearchMap.put("per_profit_2", StringUtil.checkNull(rs.getString("per_profit_2")));
		SearchMap.put("per_profit_3", StringUtil.checkNull(rs.getString("per_profit_3")));
		SearchMap.put("per_profit_4", StringUtil.checkNull(rs.getString("per_profit_4")));
		SearchMap.put("per_profit_5", StringUtil.checkNull(rs.getString("per_profit_5")));
		SearchMap.put("per_profit_6", StringUtil.checkNull(rs.getString("per_profit_6")));
		SearchMap.put("per_profit_7", StringUtil.checkNull(rs.getString("per_profit_7")));
		SearchMap.put("per_profit_8", StringUtil.checkNull(rs.getString("per_profit_8")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getSuStanDay 설명 : 보고서의 수지재료단가 호출
     * 
     * @param String
     *            met_type
     * @return String
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 08. 29.
     */
    public String getSuStanDay(String m_maker, String grade_name, String grade_color) throws Exception {
	String su_stan_day = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT su_stan_day FROM mold_maker WHERE maker_name = ? AND grade_name = ? AND grade_color = ? ");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, m_maker);
	    pstmt.setString(2, grade_name);
	    pstmt.setString(3, grade_color);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		su_stan_day = rs.getString("su_stan_day");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return su_stan_day;
    }

    /**
     * 함수명 : getReportNoteList 설명 :보고서의 원가세부내역 note1~5등의 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 4.
     */
    public ArrayList getReportNoteList(String report_pk) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT pjt_name, a.f_name, a_name, w_name, pjt_no, car_type, customer_F, request_txt, team, \n");
	sb.append("              note_1, note_2, note_3, note_4, note_5, note_5_1,to_char(c.f_day_2,'YYYY-MM-DD') as f_day \n");
	sb.append("       FROM cost_productInfo A, cost_report B, wfinfo C  WHERE B.fk_pid = A.pk_pid AND B.FK_WID = C.PK_WID(+)  AND crp_group = ? \n");

	// System.out.println(sb.toString() +" == "+ report_pk);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("note_1", StringUtil.checkNull(rs.getString("note_1")));
		SearchMap.put("note_2", StringUtil.checkNull(rs.getString("note_2")));
		SearchMap.put("note_3", StringUtil.checkNull(rs.getString("note_3")));
		SearchMap.put("note_4", StringUtil.checkNull(rs.getString("note_4")));
		SearchMap.put("note_5", StringUtil.checkNull(rs.getString("note_5")));
		SearchMap.put("note_5_1", StringUtil.checkNull(rs.getString("note_5_1")));
		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportNoteList 설명 :보고서의 원가세부내역 note1~5등의 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 4.
     */
    public ArrayList getReportPkcrRevNoList(String pk_cr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	/*
	 * sb.append(
	 * "SELECT table_row, pjt_name, a.f_name, a_name, w_name, f_day_2 as f_day, team, pjt_no, car_type, customer_F, request_txt \n");
	 * sb.append(
	 * "       FROM cost_productInfo A, cost_work B, wfinfo C  WHERE B.fk_pid = A.pk_pid AND B.FK_WID = C.PK_WID(+)  AND pk_cr_group = ? AND rev_no = ? \n"
	 * );
	 */

	sb.append("  select table_row, pjt_name, c.f_name, a_name, w_name, to_char(f_day_2,'YYYY-MM-DD') as f_day, team, pjt_no, car_type, customer_F, request_txt \n");
	sb.append("    from cost_productinfo a, cost_request b,wfinfo c \n");
	sb.append("  where pk_cr_group  = ? \n");
	sb.append("     and a.pk_pid = b.fk_pid \n");
	sb.append("     and b.fk_wid  = c.pk_wid \n");
	sb.append("     and rev_no = ? \n");

	// System.out.println(sb.toString() +" == "+ pk_cr_group + " == " + rev_no);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("table_row", StringUtil.checkNull(rs.getString("table_row")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportSuYearList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 4.
     */
    public ArrayList getReportSuYearList(String pk_cw) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	/*
	 * sb.append(
	 * "SELECT pk_cw, group_no, case_count_1, case_type_admin, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, m_total_cost as m_total_cost, labor_cost, common_cost \n"
	 * ); sb.append("       FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid(+) AND pk_cw = ? \n");
	 */

	sb.append("SELECT pk_cw, group_no, case_count_1, case_type_admin, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, DECODE(PRO_TYPE,'Insert',ROUND(((T_WEIGHT*UNITCOST)+((T_WEIGHT*UNITCOST)*0.03))/1000,1),m_total_cost) AS m_total_cost, labor_cost, common_cost \n");
	sb.append("       FROM cost_work B  WHERE pk_cw = ? \n");
	System.out.println("pk_cw :~~ " + pk_cw);

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setInt(1, Integer.parseInt(pk_cw));

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cw", StringUtil.checkNull(Integer.toString(rs.getInt("pk_cw"))));
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("case_count_1", StringUtil.checkNull(Integer.toString(rs.getInt("case_count_1"))));
		SearchMap.put("case_type_admin", StringUtil.checkNull(rs.getString("case_type_admin")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("m_total_cost", StringUtil.checkNull(rs.getString("m_total_cost")));
		SearchMap.put("labor_cost", StringUtil.checkNull(rs.getString("labor_cost")));
		SearchMap.put("common_cost", StringUtil.checkNull(rs.getString("common_cost")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCaseTypeAdminList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 5.
     */
    public ArrayList getReportCaseTypeAdminList(String pk_cw, String pk_cr_group, String rev_no, String group_no, String case_type_admin_t,
	    String k) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	System.out.println("getReportCaseTypeAdminList 의 pk_cw == " + pk_cw);
	System.out.println("getReportCaseTypeAdminList 의 pk_cr_group == " + pk_cr_group);
	System.out.println("getReportCaseTypeAdminList 의 rev_no == " + rev_no);
	System.out.println("getReportCaseTypeAdminList 의 group_no == " + group_no);
	System.out.println("getReportCaseTypeAdminList 의 case_type_admin_t == " + case_type_admin_t);
	System.out.println("getReportCaseTypeAdminList 의 k == " + k);

	if (pk_cw == null) {
	    pk_cw = "";
	}
	/*
	 * if(!"".equals(pk_cw)){ sb.append("SELECT pk_cw, group_no, case_count_2 \n"); sb.append(
	 * "       FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid AND pk_cw > ? AND pk_cr_group = ? AND group_no = ? AND case_type_admin = ? ORDER BY pk_cw \n"
	 * ); }else{ sb.append("SELECT pk_cw, group_no, case_count_2 \n"); sb.append(
	 * "       FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid AND pk_cr_group = ? AND rev_no = ? AND group_no = ? AND case_type_admin = ? ORDER BY pk_cw \n"
	 * ); }
	 */

	if (!"".equals(pk_cw)) {
	    sb.append("SELECT pk_cw, group_no, case_count_2 \n");
	    sb.append("       FROM cost_work A  WHERE  pk_cw > ? AND pk_cr_group = ? AND group_no = ? AND case_type_admin = ? ORDER BY pk_cw \n");
	} else {
	    sb.append("SELECT pk_cw, group_no, case_count_2 \n");
	    sb.append("       FROM cost_work  WHERE  pk_cr_group = ? AND rev_no = ? AND group_no = ? AND case_type_admin = ? ORDER BY pk_cw \n");
	}

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    if (!"".equals(pk_cw)) {
		pstmt.setString(1, pk_cw);
		pstmt.setString(2, pk_cr_group);
		pstmt.setString(3, group_no + "-" + k);
		pstmt.setString(4, case_type_admin_t);
	    } else {
		pstmt.setString(1, pk_cr_group);
		pstmt.setString(2, rev_no);
		pstmt.setString(3, group_no + "-" + k);
		pstmt.setString(4, case_type_admin_t);
	    }
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCaseTypeAdminList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 5.
     */
    public ArrayList getReportPkcwGroupNoList(String pk_cr_group, String k, String group_no, String case_type_admin_t) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT pk_cw, group_no \n");
	sb.append("       FROM cost_work  WHERE pk_cr_group = ? AND group_no = ? AND case_type_admin = ? ORDER BY pk_cw \n");

	// System.out.println(sb.toString() +" == "+ t_chk_list);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, group_no + "-" + k);
	    pstmt.setString(3, case_type_admin_t);
	    System.out.println("파라미터 1 => " + pk_cr_group);
	    System.out.println("파라미터 2 => " + group_no + "-" + k);
	    System.out.println("파라미터 3 => " + case_type_admin_t);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
	    }
	    SearchList.add(SearchMap);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 5.
     */
    public ArrayList getReportCostWorkList(String pk_cw) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT                        		   \n");
	sb.append(" FK_cost_request,			   	   \n");
	sb.append(" case_type_admin,			   	   \n");
	sb.append(" part_name,					   \n");
	sb.append(" part_type,					   \n");
	sb.append(" pro_type,					   \n");
	sb.append(" su_year_1,					   \n");
	sb.append(" su_year_2,					   \n");
	sb.append(" su_year_3,					   \n");
	sb.append(" su_year_4,					   \n");
	sb.append(" su_year_5,					   \n");
	sb.append(" su_year_6,					   \n");
	sb.append(" su_year_7,					   \n");
	sb.append(" su_year_8,					   \n");
	sb.append(" ket_cost,					   \n");
	sb.append(" target_cost,				   \n");
	sb.append(" '' as assy_note,			   	   \n");
	sb.append(" dis_cost, usage,			   	   \n");
	sb.append(" '' as die_no,				   \n");
	sb.append(" cavity,					   \n");
	sb.append(" make_type,					   \n");
	sb.append(" pro_1,					   \n");
	sb.append(" pro_level,					   \n");
	sb.append(" pro_level_txt,				   \n");
	sb.append(" type_cost,					   \n");
	sb.append(" t_order,					   \n");
	sb.append(" dis_cost,					   \n");
	sb.append(" yazaki_ro,					   \n");
	sb.append(" mold_cost,					   \n");
	sb.append(" to_cost,					   \n");
	sb.append(" line_su,					   \n");
	sb.append(" sul_cost,					   \n");
	sb.append(" etc_cost,					   \n");
	sb.append(" '' as part_note,			           \n");
	sb.append(" meterial_cost,			           \n");
	sb.append(" DECODE(PRO_TYPE,'Insert',     		   \n");
	sb.append(" ROUND(((T_WEIGHT*UNITCOST)+((T_WEIGHT*UNITCOST)*0.03))/1000,1),m_total_cost) AS m_total_cost,  \n");
	sb.append(" labor_cost,				   	   \n");
	sb.append(" common_cost,				   \n");
	sb.append(" earn_rate,					   \n");
	sb.append(" loss,					   \n");
	sb.append(" scrap_cost,				   	   \n");
	sb.append(" output,					   \n");
	sb.append(" rate,					   \n");
	sb.append(" eff_value,					   \n");
	sb.append(" rnd_cost,					   \n");
	sb.append(" jun_cost,					   \n");
	sb.append(" ma_depr,					   \n");
	sb.append(" m_upkeep,					   \n");
	sb.append(" total_expense,				   \n");
	sb.append(" overhead,					   \n");
	sb.append(" outsource,					   \n");
	sb.append(" etc_expense,				   \n");
	sb.append(" gita_cost,					   \n");
	sb.append(" pack_cost,					   \n");
	sb.append(" yzk_depr,				           \n");
	sb.append(" tabalu,					   \n");
	sb.append(" start_depr,				   	   \n");
	sb.append(" ma_depr,					   \n");
	sb.append(" pro_depr,					   \n");
	sb.append(" start_depr,				           \n");
	sb.append(" qu_cost,					   \n");
	sb.append(" tariff,					   \n");
	sb.append(" royalty_cost,				   \n");
	sb.append(" jae_cost,					   \n");
	sb.append(" ge_cost,					   \n");
	sb.append(" rnd_cost,					   \n");
	sb.append(" actual_cost,				   \n");
	sb.append(" depr_cost,					   \n");
	sb.append(" ad_cost					   \n");
	sb.append(" FROM cost_work where pk_cw = ?		   \n");

	// sb.append("       FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid(+) AND pk_cw = ? \n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cw);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("case_type_admin", StringUtil.checkNull(rs.getString("case_type_admin")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("assy_note", StringUtil.checkNull(rs.getString("assy_note")));
		SearchMap.put("dis_cost", StringUtil.checkNull(rs.getString("dis_cost")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		SearchMap.put("cavity", StringUtil.checkNull(rs.getString("cavity")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("pro_level", StringUtil.checkNull(rs.getString("pro_level")));
		SearchMap.put("pro_level_txt", StringUtil.checkNull(rs.getString("pro_level_txt")));
		SearchMap.put("type_cost", StringUtil.checkNull(rs.getString("type_cost")));
		SearchMap.put("t_order", StringUtil.checkNull(rs.getString("t_order")));
		SearchMap.put("dis_cost", StringUtil.checkNull(rs.getString("dis_cost")));
		SearchMap.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		SearchMap.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		SearchMap.put("to_cost", StringUtil.checkNull(rs.getString("to_cost")));
		SearchMap.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		SearchMap.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		SearchMap.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		SearchMap.put("part_note", StringUtil.checkNull(rs.getString("part_note")));
		SearchMap.put("meterial_cost", StringUtil.checkNull(rs.getString("meterial_cost")));
		SearchMap.put("m_total_cost", StringUtil.checkNull(rs.getString("m_total_cost")));
		SearchMap.put("labor_cost", StringUtil.checkNull(rs.getString("labor_cost")));
		SearchMap.put("common_cost", StringUtil.checkNull(rs.getString("common_cost")));
		SearchMap.put("earn_rate", StringUtil.checkNull(rs.getString("earn_rate")));
		SearchMap.put("loss", StringUtil.checkNull(rs.getString("loss")));
		SearchMap.put("scrap_cost", StringUtil.checkNull(rs.getString("scrap_cost")));
		SearchMap.put("output", StringUtil.checkNull(rs.getString("output")));
		SearchMap.put("rate", StringUtil.checkNull(rs.getString("rate")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchMap.put("rnd_cost", StringUtil.checkNull(rs.getString("rnd_cost")));
		SearchMap.put("jun_cost", StringUtil.checkNull(rs.getString("jun_cost")));
		SearchMap.put("ma_depr", StringUtil.checkNull(rs.getString("ma_depr")));
		SearchMap.put("m_upkeep", StringUtil.checkNull(rs.getString("m_upkeep")));
		SearchMap.put("total_expense", StringUtil.checkNull(rs.getString("total_expense")));
		SearchMap.put("overhead", StringUtil.checkNull(rs.getString("overhead")));
		SearchMap.put("outsource", StringUtil.checkNull(rs.getString("outsource")));
		SearchMap.put("etc_expense", StringUtil.checkNull(rs.getString("etc_expense")));
		SearchMap.put("gita_cost", StringUtil.checkNull(rs.getString("gita_cost")));
		SearchMap.put("pack_cost", StringUtil.checkNull(rs.getString("pack_cost")));
		SearchMap.put("yzk_depr", StringUtil.checkNull(rs.getString("yzk_depr")));
		SearchMap.put("tabalu", StringUtil.checkNull(rs.getString("tabalu")));
		SearchMap.put("start_depr", StringUtil.checkNull(rs.getString("start_depr")));
		SearchMap.put("ma_depr", StringUtil.checkNull(rs.getString("ma_depr")));
		SearchMap.put("pro_depr", StringUtil.checkNull(rs.getString("pro_depr")));
		SearchMap.put("start_depr", StringUtil.checkNull(rs.getString("start_depr")));
		SearchMap.put("qu_cost", StringUtil.checkNull(rs.getString("qu_cost")));
		SearchMap.put("tariff", StringUtil.checkNull(rs.getString("tariff")));
		SearchMap.put("royalty_cost", StringUtil.checkNull(rs.getString("royalty_cost")));
		SearchMap.put("jae_cost", StringUtil.checkNull(rs.getString("jae_cost")));
		SearchMap.put("ge_cost", StringUtil.checkNull(rs.getString("ge_cost")));
		SearchMap.put("rnd_cost", StringUtil.checkNull(rs.getString("rnd_cost")));
		SearchMap.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		SearchMap.put("depr_cost", StringUtil.checkNull(rs.getString("depr_cost")));
		SearchMap.put("ad_cost", StringUtil.checkNull(rs.getString("ad_cost")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkCaseCountList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 5.
     */
    public ArrayList getReportCostWorkCaseCountList(String pk_cr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	/*
	 * sb.append("SELECT case_count_1, case_count_2 FROM \n"); sb.append("    (SELECT ROWNUM rn, case_count_1, case_count_2 \n");
	 * sb.append(
	 * "           FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid AND pk_cr_group = ? AND case_type_admin = 'main' AND length(group_no) = '4' AND rev_no = ? ) WHERE rn = 1 \n"
	 * );
	 */

	sb.append("SELECT case_count_1, case_count_2  \n");
	sb.append("   FROM cost_work where pk_cr_group = ? AND case_type_admin = 'main' AND length(group_no) = '4' AND rev_no = ?  and rownum = 1 \n");
	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		SearchMap.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkMoreList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 10.
     */
    public ArrayList getReportCostWorkMoreList(String pk_cw) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	/*
	 * sb.append(
	 * "SELECT m_co_chk, pro_type, depr_cost, m_su, mold_cost, m_maker, grade_name, grade_color, etc_cost, yazaki_ro, sul_cost, line_su \n"
	 * ); sb.append("    FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid AND pk_cw = ? \n");
	 */
	sb.append("SELECT m_co_chk, pro_type, depr_cost, m_su, mold_cost, m_maker, grade_name, grade_color, etc_cost, yazaki_ro, sul_cost, line_su \n");
	sb.append("    FROM cost_work  WHERE  pk_cw = ? \n");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cw);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("m_co_chk", StringUtil.checkNull(rs.getString("m_co_chk")));
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("depr_cost", StringUtil.checkNull(rs.getString("depr_cost")));
		SearchMap.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		SearchMap.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		SearchMap.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		SearchMap.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		SearchMap.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		SearchMap.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		SearchMap.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		SearchMap.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		SearchMap.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkMoreList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 10.
     */
    public ArrayList getReportCostWorkAllList(String pk_cr_group, String k, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	/*
	 * sb.append(
	 * "SELECT case_count_1, case_count_2, pjt_name, a.f_name, w_name, a_name, pjt_no, car_type, customer_F, team, step_no, app_part, target_cost, start_pro, \n"
	 * ); sb.append(
	 * "               store, dest, actual_cost, request_txt, f_day_2 as f_day, Leader_day_2 as Leader_day, Leader_name_2 as Leader_name, approval, dev_step, lme_ton, u_ex_rate, pk_cw, part_name, part_type, part_no, \n"
	 * ); sb.append(
	 * "               dev_step, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, client_cost, ket_cost, target_cost, usage, pro_type, pro_1, eff_value \n"
	 * ); sb.append(
	 * "    FROM cost_productInfo A, cost_work B, wfinfo C  WHERE B.fk_pid = A.pk_pid AND B.FK_WID = C.PK_WID(+) AND pk_cr_group = ? AND case_type_admin = 'main' AND group_no = ? AND rev_no = ? ORDER BY pk_cw \n"
	 * );
	 */

	sb.append(" select case_count_1, case_count_2, pjt_name, f_name, w_name, a_name, pjt_no, car_type, customer_F, team, step_no, app_part, target_cost, start_pro, 			\n");
	sb.append("	       store, dest, actual_cost, request_txt, f_day, Leader_day, Leader_name, approval, dev_step, lme_ton, u_ex_rate, pk_cw, part_name, part_type, part_no, 		\n");
	sb.append("	       dev_step, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, client_cost, ket_cost, target_cost, usage, pro_type, pro_1, eff_value		\n");
	sb.append("   from cost_work a, 																																											\n");
	sb.append("	      (select a.pjt_name,  a.f_name, w_name, a_name, pjt_no, car_type, customer_F, team,step_no,app_part,										\n");
	sb.append("	                 request_txt, f_day_2 as f_day, Leader_day_2 as Leader_day, Leader_name_2 as Leader_name,											\n");
	sb.append("	                 approval,    dev_step, part_no																																			\n");
	sb.append("	         from cost_productinfo a, cost_request b,wfinfo c																														\n");
	sb.append("	        where pk_cr_group = ?																																					\n");
	sb.append("	          and rev_no = ?																																					\n");
	sb.append("	          and a.pk_pid = b.fk_pid																																							\n");
	sb.append("	          and b.fk_wid = c.pk_wid																																						\n");
	sb.append("	          and rownum = 1) b																																								\n");
	sb.append("where  pk_cr_group  =   ? 																																							\n");
	sb.append("   and  group_no       =	 ?																																					    \n");
	sb.append("   and  rev_no 	        =   ?																																						\n");
	sb.append("   and  case_type_admin = 'main' 																																	    \n");

	// System.out.println(sb.toString() +" == "+ pk_cr_group +"=="+k+"=="+rev_no);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    pstmt.setString(3, pk_cr_group);
	    pstmt.setString(4, "T" + k);
	    pstmt.setString(5, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		SearchMap.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("step_no", StringUtil.checkNull(rs.getString("step_no")));
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("start_pro", StringUtil.checkNull(rs.getString("start_pro")));
		SearchMap.put("store", StringUtil.checkNull(rs.getString("store")));
		SearchMap.put("dest", StringUtil.checkNull(rs.getString("dest")));
		SearchMap.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		SearchMap.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		SearchMap.put("Leader_name", StringUtil.checkNull(rs.getString("Leader_name")));
		SearchMap.put("approval", StringUtil.checkNull(rs.getString("approval")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("lme_ton", StringUtil.checkNull(rs.getString("lme_ton")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkMoreList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 10.
     */
    public ArrayList getReportCostReportAllList(String report_pk) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append("SELECT pass_type,f_day_2 as f_day,Leader_day_2 as Leader_day,Leader_name_2 as Leader_name,JB_day,JB_name,p_leader_day,p_leader_name,r_owner_day,r_pre_day,Gr_day_2 as Gr_day,Gr_name_2 as Gr_name, approval,w_name,app_part, \n");
	sb.append("              case_1_note,case_2_note,case_3_note,case_to_note_1,case_to_note_2,case_to_note_3,step_no,pk_cr_group,FK_cost_request,dev_step,pjt_name, \n");
	sb.append("              pjt_no,part_name,target_cost,team,a.f_name,a_name,request_txt,car_type,customer_F,report_dest,lme_cu,u_ex_rate,pack_type,usage,make_type,pro_1,eff_value, \n");
	sb.append("             mold_count,press_count,line_count,pack_count,repack_count,mold_total_1,press_total_1,line_total_1,pack_total_1,mold_total_2,press_total_2,line_total_2,pack_total_2, \n");
	sb.append("             mold_total_3,press_total_3,line_total_3,pack_total_3,repack_total_1,repack_total_2,repack_total_3,m_depr_stan,p_depr_stan,L_depr_stan,pack_depr_stan,repack_depr_stan, \n");
	sb.append("             su_stan_day,note_1,note_2,note_3,note_4,note_5,note_5_1,tocost_year,select_cost,  \n");
	sb.append("             case_4_note, case_to_note_4, mold_total_4, press_total_4, line_total_4, pack_total_4, repack_total_4,b.file_2,b.file_3,b.file_4,b.file_5,b.file_6, file_2_name, file_3_name, file_4_name, file_5_name, file_6_name \n");
	sb.append("    FROM cost_productInfo A, cost_report B, wfinfo C  WHERE B.fk_pid = A.pk_pid AND B.FK_WID = C.PK_WID(+) AND crp_group = ? ORDER BY A.group_no \n");

	// System.out.println(sb.toString() +" == "+ report_pk);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pass_type", StringUtil.checkNull(rs.getString("pass_type")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		SearchMap.put("case_1_note", StringUtil.checkNull(rs.getString("case_1_note")));
		SearchMap.put("case_2_note", StringUtil.checkNull(rs.getString("case_2_note")));
		SearchMap.put("case_3_note", StringUtil.checkNull(rs.getString("case_3_note")));
		SearchMap.put("case_to_note_1", StringUtil.checkNull(rs.getString("case_to_note_1")));
		SearchMap.put("case_to_note_2", StringUtil.checkNull(rs.getString("case_to_note_2")));
		SearchMap.put("case_to_note_3", StringUtil.checkNull(rs.getString("case_to_note_3")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("report_dest", StringUtil.checkNull(rs.getString("report_dest")));
		SearchMap.put("lme_cu", StringUtil.checkNull(rs.getString("lme_cu")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchMap.put("mold_count", StringUtil.checkNull(rs.getString("mold_count")));
		SearchMap.put("press_count", StringUtil.checkNull(rs.getString("press_count")));
		SearchMap.put("line_count", StringUtil.checkNull(rs.getString("line_count")));
		SearchMap.put("pack_count", StringUtil.checkNull(rs.getString("pack_count")));
		SearchMap.put("repack_count", StringUtil.checkNull(rs.getString("repack_count")));
		SearchMap.put("mold_total_1", StringUtil.checkNull(rs.getString("mold_total_1")));
		SearchMap.put("press_total_1", StringUtil.checkNull(rs.getString("press_total_1")));
		SearchMap.put("line_total_1", StringUtil.checkNull(rs.getString("line_total_1")));
		SearchMap.put("pack_total_1", StringUtil.checkNull(rs.getString("pack_total_1")));
		SearchMap.put("mold_total_2", StringUtil.checkNull(rs.getString("mold_total_2")));
		SearchMap.put("press_total_2", StringUtil.checkNull(rs.getString("press_total_2")));
		SearchMap.put("line_total_2", StringUtil.checkNull(rs.getString("line_total_2")));
		SearchMap.put("pack_total_2", StringUtil.checkNull(rs.getString("pack_total_2")));
		SearchMap.put("mold_total_3", StringUtil.checkNull(rs.getString("mold_total_3")));
		SearchMap.put("press_total_3", StringUtil.checkNull(rs.getString("press_total_3")));
		SearchMap.put("line_total_3", StringUtil.checkNull(rs.getString("line_total_3")));
		SearchMap.put("pack_total_3", StringUtil.checkNull(rs.getString("pack_total_3")));
		SearchMap.put("repack_total_1", StringUtil.checkNull(rs.getString("repack_total_1")));
		SearchMap.put("repack_total_2", StringUtil.checkNull(rs.getString("repack_total_2")));
		SearchMap.put("repack_total_3", StringUtil.checkNull(rs.getString("repack_total_3")));
		SearchMap.put("m_depr_stan", StringUtil.checkNull(rs.getString("m_depr_stan")));
		SearchMap.put("p_depr_stan", StringUtil.checkNull(rs.getString("p_depr_stan")));
		SearchMap.put("L_depr_stan", StringUtil.checkNull(rs.getString("L_depr_stan")));
		SearchMap.put("pack_depr_stan", StringUtil.checkNull(rs.getString("pack_depr_stan")));
		SearchMap.put("repack_depr_stan", StringUtil.checkNull(rs.getString("repack_depr_stan")));
		SearchMap.put("su_stan_day", StringUtil.checkNull(rs.getString("su_stan_day")));
		SearchMap.put("note_1", StringUtil.checkNull(rs.getString("note_1")));
		SearchMap.put("note_2", StringUtil.checkNull(rs.getString("note_2")));
		SearchMap.put("note_3", StringUtil.checkNull(rs.getString("note_3")));
		SearchMap.put("note_4", StringUtil.checkNull(rs.getString("note_4")));
		SearchMap.put("note_5", StringUtil.checkNull(rs.getString("note_5")));
		SearchMap.put("note_5_1", StringUtil.checkNull(rs.getString("note_5_1")));
		SearchMap.put("tocost_year", StringUtil.checkNull(rs.getString("tocost_year")));
		SearchMap.put("select_cost", StringUtil.checkNull(rs.getString("select_cost")));

		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		SearchMap.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		SearchMap.put("Leader_name", StringUtil.checkNull(rs.getString("Leader_name")));
		SearchMap.put("JB_day", StringUtil.checkNull(rs.getString("JB_day")));
		SearchMap.put("JB_name", StringUtil.checkNull(rs.getString("JB_name")));
		SearchMap.put("p_leader_day", StringUtil.checkNull(rs.getString("p_leader_day")));
		SearchMap.put("p_leader_name", StringUtil.checkNull(rs.getString("p_leader_name")));
		SearchMap.put("r_owner_day", StringUtil.checkNull(rs.getString("r_owner_day")));
		SearchMap.put("r_pre_day", StringUtil.checkNull(rs.getString("r_pre_day")));
		SearchMap.put("Gr_day", StringUtil.checkNull(rs.getString("Gr_day")));
		SearchMap.put("Gr_name", StringUtil.checkNull(rs.getString("Gr_name")));
		SearchMap.put("approval", StringUtil.checkNull(rs.getString("approval")));
		SearchMap.put("step_no", StringUtil.checkNull(rs.getString("step_no")));
		// 4안
		SearchMap.put("case_4_note", StringUtil.checkNull(rs.getString("case_4_note")));
		SearchMap.put("case_to_note_4", StringUtil.checkNull(rs.getString("case_to_note_4")));
		SearchMap.put("mold_total_4", StringUtil.checkNull(rs.getString("mold_total_4")));
		SearchMap.put("press_total_4", StringUtil.checkNull(rs.getString("press_total_4")));
		SearchMap.put("line_total_4", StringUtil.checkNull(rs.getString("line_total_4")));
		SearchMap.put("pack_total_4", StringUtil.checkNull(rs.getString("pack_total_4")));
		SearchMap.put("repack_total_4", StringUtil.checkNull(rs.getString("repack_total_4")));

		SearchMap.put("file_2", StringUtil.checkNull(rs.getString("file_2")));
		SearchMap.put("file_3", StringUtil.checkNull(rs.getString("file_3")));
		SearchMap.put("file_4", StringUtil.checkNull(rs.getString("file_4")));
		SearchMap.put("file_5", StringUtil.checkNull(rs.getString("file_5")));
		SearchMap.put("file_6", StringUtil.checkNull(rs.getString("file_6")));

		SearchMap.put("file_2_name", StringUtil.checkNull(rs.getString("file_2_name")));
		SearchMap.put("file_3_name", StringUtil.checkNull(rs.getString("file_3_name")));
		SearchMap.put("file_4_name", StringUtil.checkNull(rs.getString("file_4_name")));
		SearchMap.put("file_5_name", StringUtil.checkNull(rs.getString("file_5_name")));
		SearchMap.put("file_6_name", StringUtil.checkNull(rs.getString("file_6_name")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkMoreList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 10.
     */
    public ArrayList getReportFKCostWorkList(String report_pk) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append("SELECT pass_type, FK_cost_work_1, FK_cost_work_2, FK_cost_work_3, \n");
	sb.append("  f_day_2 as f_day, Leader_day_2 as Leader_day, Leader_name_2 as Leader_name, JB_day, JB_name, r_owner_day, r_pre_day, p_leader_day,p_leader_name, Gr_day_2 as Gr_day,Gr_name_2 as Gr_name,approval,  w_name, app_part, case_1_note, case_2_note, case_3_note, case_to_note_1, case_to_note_2, case_to_note_3, \n");
	sb.append(" step_no,   pk_cr_group, FK_cost_request, dev_step, pjt_name, pjt_no, part_name, team, a.f_name, a_name, request_txt, car_type, customer_F, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, \n");
	sb.append("report_dest, client_cost, room_earn, pro_usage, ket_cost, target_cost, lme_cu, u_ex_rate, pack_type, usage, make_type, pro_1, eff_value, actual_cost_1, earn_rate_1, actual_cost_2, earn_rate_2, actual_cost_3, earn_rate_3, \n");
	sb.append("actual_cost_sum_1, earn_rate_sum_1, actual_cost_sum_2, earn_rate_sum_2, actual_cost_sum_3, earn_rate_sum_3, mold_count, press_count, line_count, pack_count, repack_count, mold_total_1, press_total_1, line_total_1, \n");
	sb.append("pack_total_1, mold_total_2, press_total_2, line_total_2, pack_total_2, mold_total_3, press_total_3, line_total_3, pack_total_3, repack_total_1, repack_total_2, repack_total_3, m_depr_stan, p_depr_stan, L_depr_stan, pack_depr_stan, \n");
	sb.append("repack_depr_stan, total_sales_1, total_sales_2, total_sales_3, total_sales_4, total_sales_5, total_sales_6, total_sales_7, total_sales_8, profit_1, profit_2, profit_3, profit_4, profit_5, profit_6, profit_7, profit_8, \n");
	sb.append("per_profit_1, per_profit_2, per_profit_3, per_profit_4, per_profit_5, per_profit_6, per_profit_7, per_profit_8, su_stan_day, note_1, note_2, note_3, note_4, note_5, note_5_1, tocost_year, select_cost,  \n");
	sb.append("FK_cost_work_4, actual_cost_4, earn_rate_4, actual_cost_sum_4, earn_rate_sum_4, mold_total_4, press_total_4, line_total_4, pack_total_4, case_4_note, case_to_note_4,file_1,file_2,file_3,file_4,file_5,file_6, \n");
	sb.append("file_2_name,file_3_name,file_4_name,file_5_name,file_6_name \n");
	sb.append("    FROM cost_productInfo A, cost_report B, wfinfo C  WHERE B.fk_pid = A.pk_pid AND B.FK_WID = C.PK_WID(+) AND crp_group = ? ORDER BY A.group_no \n");

	// System.out.println(sb.toString() +" == "+ report_pk);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("pass_type", StringUtil.checkNull(rs.getString("pass_type")));
		SearchMap.put("FK_cost_work_1", StringUtil.checkNull(rs.getString("FK_cost_work_1")));
		SearchMap.put("FK_cost_work_2", StringUtil.checkNull(rs.getString("FK_cost_work_2")));
		SearchMap.put("FK_cost_work_3", StringUtil.checkNull(rs.getString("FK_cost_work_3")));
		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		SearchMap.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		SearchMap.put("Leader_name", StringUtil.checkNull(rs.getString("Leader_name")));
		SearchMap.put("JB_day", StringUtil.checkNull(rs.getString("JB_day")));
		SearchMap.put("JB_name", StringUtil.checkNull(rs.getString("JB_name")));
		SearchMap.put("r_owner_day", StringUtil.checkNull(rs.getString("r_owner_day")));
		SearchMap.put("r_pre_day", StringUtil.checkNull(rs.getString("r_pre_day")));
		SearchMap.put("p_leader_day", StringUtil.checkNull(rs.getString("p_leader_day")));
		SearchMap.put("p_leader_name", StringUtil.checkNull(rs.getString("p_leader_name")));
		SearchMap.put("Gr_day", StringUtil.checkNull(rs.getString("Gr_day")));
		SearchMap.put("Gr_name", StringUtil.checkNull(rs.getString("Gr_name")));
		SearchMap.put("approval", StringUtil.checkNull(rs.getString("approval")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		SearchMap.put("case_1_note", StringUtil.checkNull(rs.getString("case_1_note")));
		SearchMap.put("case_2_note", StringUtil.checkNull(rs.getString("case_2_note")));
		SearchMap.put("case_3_note", StringUtil.checkNull(rs.getString("case_3_note")));
		SearchMap.put("case_to_note_1", StringUtil.checkNull(rs.getString("case_to_note_1")));
		SearchMap.put("case_to_note_2", StringUtil.checkNull(rs.getString("case_to_note_2")));
		SearchMap.put("case_to_note_3", StringUtil.checkNull(rs.getString("case_to_note_3")));
		SearchMap.put("step_no", StringUtil.checkNull(rs.getString("step_no")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("report_dest", StringUtil.checkNull(rs.getString("report_dest")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("room_earn", StringUtil.checkNull(rs.getString("room_earn")));
		SearchMap.put("pro_usage", StringUtil.checkNull(rs.getString("pro_usage")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("lme_cu", StringUtil.checkNull(rs.getString("lme_cu")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchMap.put("actual_cost_1", StringUtil.checkNull(rs.getString("actual_cost_1")));
		SearchMap.put("earn_rate_1", StringUtil.checkNull(rs.getString("earn_rate_1")));
		SearchMap.put("actual_cost_2", StringUtil.checkNull(rs.getString("actual_cost_2")));
		SearchMap.put("earn_rate_2", StringUtil.checkNull(rs.getString("earn_rate_2")));
		SearchMap.put("actual_cost_3", StringUtil.checkNull(rs.getString("actual_cost_3")));
		SearchMap.put("earn_rate_3", StringUtil.checkNull(rs.getString("earn_rate_3")));
		SearchMap.put("actual_cost_sum_1", StringUtil.checkNull(rs.getString("actual_cost_sum_1")));
		SearchMap.put("earn_rate_sum_1", StringUtil.checkNull(rs.getString("earn_rate_sum_1")));
		SearchMap.put("actual_cost_sum_2", StringUtil.checkNull(rs.getString("actual_cost_sum_2")));
		SearchMap.put("earn_rate_sum_2", StringUtil.checkNull(rs.getString("earn_rate_sum_2")));
		SearchMap.put("actual_cost_sum_3", StringUtil.checkNull(rs.getString("actual_cost_sum_3")));
		SearchMap.put("earn_rate_sum_3", StringUtil.checkNull(rs.getString("earn_rate_sum_3")));
		SearchMap.put("mold_count", StringUtil.checkNull(rs.getString("mold_count")));
		SearchMap.put("press_count", StringUtil.checkNull(rs.getString("press_count")));
		SearchMap.put("line_count", StringUtil.checkNull(rs.getString("line_count")));
		SearchMap.put("pack_count", StringUtil.checkNull(rs.getString("pack_count")));
		SearchMap.put("repack_count", StringUtil.checkNull(rs.getString("repack_count")));
		SearchMap.put("mold_total_1", StringUtil.checkNull(rs.getString("mold_total_1")));
		SearchMap.put("press_total_1", StringUtil.checkNull(rs.getString("press_total_1")));
		SearchMap.put("line_total_1", StringUtil.checkNull(rs.getString("line_total_1")));
		SearchMap.put("pack_total_1", StringUtil.checkNull(rs.getString("pack_total_1")));
		SearchMap.put("mold_total_2", StringUtil.checkNull(rs.getString("mold_total_2")));
		SearchMap.put("press_total_2", StringUtil.checkNull(rs.getString("press_total_2")));
		SearchMap.put("line_total_2", StringUtil.checkNull(rs.getString("line_total_2")));
		SearchMap.put("pack_total_2", StringUtil.checkNull(rs.getString("pack_total_2")));
		SearchMap.put("mold_total_3", StringUtil.checkNull(rs.getString("mold_total_3")));
		SearchMap.put("press_total_3", StringUtil.checkNull(rs.getString("press_total_3")));
		SearchMap.put("line_total_3", StringUtil.checkNull(rs.getString("line_total_3")));
		SearchMap.put("pack_total_3", StringUtil.checkNull(rs.getString("pack_total_3")));
		SearchMap.put("repack_total_1", StringUtil.checkNull(rs.getString("repack_total_1")));
		SearchMap.put("repack_total_2", StringUtil.checkNull(rs.getString("repack_total_2")));
		SearchMap.put("repack_total_3", StringUtil.checkNull(rs.getString("repack_total_3")));
		SearchMap.put("m_depr_stan", StringUtil.checkNull(rs.getString("m_depr_stan")));
		SearchMap.put("p_depr_stan", StringUtil.checkNull(rs.getString("p_depr_stan")));
		SearchMap.put("L_depr_stan", StringUtil.checkNull(rs.getString("L_depr_stan")));
		SearchMap.put("pack_depr_stan", StringUtil.checkNull(rs.getString("pack_depr_stan")));
		SearchMap.put("repack_depr_stan", StringUtil.checkNull(rs.getString("repack_depr_stan")));
		SearchMap.put("total_sales_1", StringUtil.checkNull(rs.getString("total_sales_1")));
		SearchMap.put("total_sales_2", StringUtil.checkNull(rs.getString("total_sales_2")));
		SearchMap.put("total_sales_3", StringUtil.checkNull(rs.getString("total_sales_3")));
		SearchMap.put("total_sales_4", StringUtil.checkNull(rs.getString("total_sales_4")));
		SearchMap.put("total_sales_5", StringUtil.checkNull(rs.getString("total_sales_5")));
		SearchMap.put("total_sales_6", StringUtil.checkNull(rs.getString("total_sales_6")));
		SearchMap.put("total_sales_7", StringUtil.checkNull(rs.getString("total_sales_7")));
		SearchMap.put("total_sales_8", StringUtil.checkNull(rs.getString("total_sales_8")));
		SearchMap.put("profit_1", StringUtil.checkNull(rs.getString("profit_1")));
		SearchMap.put("profit_2", StringUtil.checkNull(rs.getString("profit_2")));
		SearchMap.put("profit_3", StringUtil.checkNull(rs.getString("profit_3")));
		SearchMap.put("profit_4", StringUtil.checkNull(rs.getString("profit_4")));
		SearchMap.put("profit_5", StringUtil.checkNull(rs.getString("profit_5")));
		SearchMap.put("profit_6", StringUtil.checkNull(rs.getString("profit_6")));
		SearchMap.put("profit_7", StringUtil.checkNull(rs.getString("profit_7")));
		SearchMap.put("profit_8", StringUtil.checkNull(rs.getString("profit_8")));
		SearchMap.put("per_profit_1", StringUtil.checkNull(rs.getString("per_profit_1")));
		SearchMap.put("per_profit_2", StringUtil.checkNull(rs.getString("per_profit_2")));
		SearchMap.put("per_profit_3", StringUtil.checkNull(rs.getString("per_profit_3")));
		SearchMap.put("per_profit_4", StringUtil.checkNull(rs.getString("per_profit_4")));
		SearchMap.put("per_profit_5", StringUtil.checkNull(rs.getString("per_profit_5")));
		SearchMap.put("per_profit_6", StringUtil.checkNull(rs.getString("per_profit_6")));
		SearchMap.put("per_profit_7", StringUtil.checkNull(rs.getString("per_profit_7")));
		SearchMap.put("per_profit_8", StringUtil.checkNull(rs.getString("per_profit_8")));
		SearchMap.put("su_stan_day", StringUtil.checkNull(rs.getString("su_stan_day")));
		SearchMap.put("note_1", StringUtil.checkNull(rs.getString("note_1")));
		SearchMap.put("note_2", StringUtil.checkNull(rs.getString("note_2")));
		SearchMap.put("note_3", StringUtil.checkNull(rs.getString("note_3")));
		SearchMap.put("note_4", StringUtil.checkNull(rs.getString("note_4")));
		SearchMap.put("note_5", StringUtil.checkNull(rs.getString("note_5")));
		SearchMap.put("note_5_1", StringUtil.checkNull(rs.getString("note_5_1")));
		SearchMap.put("tocost_year", StringUtil.checkNull(rs.getString("tocost_year")));
		SearchMap.put("select_cost", StringUtil.checkNull(rs.getString("select_cost")));
		SearchMap.put("file_1", StringUtil.checkNull(rs.getString("file_1")));
		// 4안
		SearchMap.put("FK_cost_work_4", StringUtil.checkNull(rs.getString("FK_cost_work_4")));
		SearchMap.put("case_4_note", StringUtil.checkNull(rs.getString("case_4_note")));
		SearchMap.put("case_to_note_4", StringUtil.checkNull(rs.getString("case_to_note_4")));
		SearchMap.put("actual_cost_4", StringUtil.checkNull(rs.getString("actual_cost_4")));
		SearchMap.put("earn_rate_4", StringUtil.checkNull(rs.getString("earn_rate_4")));
		SearchMap.put("actual_cost_sum_4", StringUtil.checkNull(rs.getString("actual_cost_sum_4")));
		SearchMap.put("earn_rate_sum_4", StringUtil.checkNull(rs.getString("earn_rate_sum_4")));
		SearchMap.put("mold_total_4", StringUtil.checkNull(rs.getString("mold_total_4")));
		SearchMap.put("press_total_4", StringUtil.checkNull(rs.getString("press_total_4")));
		SearchMap.put("line_total_4", StringUtil.checkNull(rs.getString("line_total_4")));
		SearchMap.put("pack_total_4", StringUtil.checkNull(rs.getString("pack_total_4")));

		SearchMap.put("file_2", StringUtil.checkNull(rs.getString("file_2")));
		SearchMap.put("file_3", StringUtil.checkNull(rs.getString("file_3")));
		SearchMap.put("file_4", StringUtil.checkNull(rs.getString("file_4")));
		SearchMap.put("file_5", StringUtil.checkNull(rs.getString("file_5")));
		SearchMap.put("file_6", StringUtil.checkNull(rs.getString("file_6")));

		SearchMap.put("file_2_name", StringUtil.checkNull(rs.getString("file_2_name")));
		SearchMap.put("file_3_name", StringUtil.checkNull(rs.getString("file_3_name")));
		SearchMap.put("file_4_name", StringUtil.checkNull(rs.getString("file_4_name")));
		SearchMap.put("file_5_name", StringUtil.checkNull(rs.getString("file_5_name")));
		SearchMap.put("file_6_name", StringUtil.checkNull(rs.getString("file_6_name")));
		SearchList.add(SearchMap);
	    }
	} catch (SQLException se) {
	    se.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null)
		rs.close();

	    if (pstmt != null)
		pstmt.close();
	}

	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkEarnList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 11.
     */
    public ArrayList getReportCostWorkGroupNoList(String pk_cw, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	/*
	 * sb.append(
	 * "SELECT group_no, FK_cost_request, actual_cost, earn_rate, usage,  f_day_2 as f_day, Leader_day_2 as Leader_day, case_type_admin, Leader_name_2 as Leader_name, approval, step_no,   w_name, ket_cost, \n"
	 * );
	 * sb.append("              su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, mold_c_type \n"
	 * ); sb.append(
	 * "       FROM cost_productInfo A, cost_work B, wfinfo C  WHERE B.fk_pid = A.pk_pid and B.FK_WID = C.PK_WID(+) AND pk_cw = ? \n");
	 */

	sb.append(" select			\n");
	sb.append(" group_no,			\n");
	sb.append(" FK_cost_request,  		\n");
	sb.append(" actual_cost,		\n");
	sb.append(" earn_rate,			\n");
	sb.append(" usage,			\n");
	sb.append(" f_day,			\n");
	sb.append(" Leader_day,			\n");
	sb.append(" case_type_admin,  		\n");
	sb.append(" Leader_name,		\n");
	sb.append(" approval,			\n");
	sb.append(" step_no,			\n");
	sb.append(" w_name,			\n");
	sb.append(" ket_cost,			\n");
	sb.append(" su_year_1,			\n");
	sb.append(" su_year_2,			\n");
	sb.append(" su_year_3,			\n");
	sb.append(" su_year_4,			\n");
	sb.append(" su_year_5,			\n");
	sb.append(" su_year_6,			\n");
	sb.append(" su_year_7,			\n");
	sb.append(" su_year_8,			\n");
	sb.append(" mold_c_type 		\n");
	sb.append(" from cost_work,   		\n");
	sb.append(" (select f_day_2 as f_day, Leader_day_2 as Leader_day,  Leader_name_2 as Leader_name, approval, step_no, w_name from cost_productinfo a, cost_request b,wfinfo c where pk_cr_group = (select pk_cr_group from cost_work where pk_cw = ?)	\n");
	sb.append(" and a.pk_pid = b.fk_pid	\n");
	sb.append(" and b.fk_wid = c.pk_wid	\n");
	sb.append(" and rev_no = ?		\n");
	sb.append(" and rownum = 1) b		\n");
	sb.append(" where pk_cw = ?		\n");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cw);
	    pstmt.setString(2, rev_no);
	    pstmt.setString(3, pk_cw);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		SearchMap.put("earn_rate", StringUtil.checkNull(rs.getString("earn_rate")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		SearchMap.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		SearchMap.put("case_type_admin", StringUtil.checkNull(rs.getString("case_type_admin")));
		SearchMap.put("Leader_name", StringUtil.checkNull(rs.getString("Leader_name")));
		SearchMap.put("approval", StringUtil.checkNull(rs.getString("approval")));
		SearchMap.put("step_no", StringUtil.checkNull(rs.getString("step_no")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("mold_c_type", StringUtil.checkNull(rs.getString("mold_c_type")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : getReportCostWorkEarnList 설명 :보고서의 원가세부내역 데이터 불러오기
     * 
     * @param String
     *            pk_cw
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 11.
     */
    public ArrayList getDbCostWorkList(String pk_cw) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT b.case_count_1, b.case_count_2, f_name, a_name, team, b.group_no, rev_pk, start_pro, store, dest, FK_cost_request, dev_step, USD_rate, YEN_RATE, EURO_rate,  \n");
	sb.append("              CNY_rate, lme_ton, u_ex_rate, b.part_name, b.part_type, part_no, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8,   \n");
	sb.append("              client_cost, target_cost, usage, make_type, b.pro_type, a.pk_pid \n");
	sb.append("       FROM cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid AND pk_cw = ? \n");

	System.out.println(sb.toString() + " == " + pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cw);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		SearchMap.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		SearchMap.put("rev_pk", StringUtil.checkNull(rs.getString("rev_pk")));
		SearchMap.put("start_pro", StringUtil.checkNull(rs.getString("start_pro")));
		SearchMap.put("store", StringUtil.checkNull(rs.getString("store")));
		SearchMap.put("dest", StringUtil.checkNull(rs.getString("dest")));
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("USD_rate", StringUtil.checkNull(rs.getString("USD_rate")));
		SearchMap.put("YEN_rate", StringUtil.checkNull(rs.getString("YEN_rate")));
		SearchMap.put("EURO_rate", StringUtil.checkNull(rs.getString("EURO_rate")));
		SearchMap.put("CNY_rate", StringUtil.checkNull(rs.getString("CNY_rate")));
		SearchMap.put("lme_ton", StringUtil.checkNull(rs.getString("lme_ton")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		SearchMap.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		SearchMap.put("pk_pid", StringUtil.checkNull(rs.getString("pk_pid")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : setdbCostWorkInfoUpdate 설명 :보고서의 원가세부내역 제품정보 UPDATE
     * 
     * @param ArrayList
     *            insertItemList
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 11.
     */
    public int setdbCostWorkUpdate(ArrayList insertItemList) throws Exception {
	int complet = 0;
	Hashtable insertItem = (Hashtable) insertItemList.get(0);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sbInfo = new StringBuffer();
	StringBuffer sbReport = new StringBuffer();

	sbInfo.append(" UPDATE cost_productinfo \n");
	sbInfo.append(" SET                     \n");
	sbInfo.append(" group_no = ?,           \n");
	sbInfo.append(" pk_cr_group = ?,        \n");
	sbInfo.append(" dev_step = ?,		\n");
	sbInfo.append(" pjt_name = ?,           \n");
	sbInfo.append(" pjt_no = ?,             \n");
	sbInfo.append(" rev_pk = ?,             \n");
	sbInfo.append(" rev_no = ?,             \n");
	sbInfo.append(" part_name = ?,          \n");
	sbInfo.append(" team = ?,               \n");
	sbInfo.append(" f_name = ?,             \n");
	sbInfo.append(" a_name = ?,             \n");
	sbInfo.append(" w_name = ?,             \n");
	sbInfo.append(" request_txt = ?,        \n");
	sbInfo.append(" app_part = ?,           \n");
	sbInfo.append(" car_type = ?,           \n");
	sbInfo.append(" customer_F = ?          \n");
	sbInfo.append(" WHERE pk_pid = (SELECT fk_pid FROM cost_report WHERE crp_group = ? AND group_no = ?) \n");

	sbReport.append(" UPDATE cost_report        \n");
	sbReport.append(" SET			    \n");
	sbReport.append(" group_no = ?,             \n");
	sbReport.append(" FK_cost_work_1 = ?,       \n");
	sbReport.append(" FK_cost_work_2 = ?,       \n");
	sbReport.append(" FK_cost_work_3 = ?,       \n");
	sbReport.append(" FK_cost_request = ?,      \n");
	sbReport.append(" report_dest = ?,          \n");
	sbReport.append(" su_year_1 = ?,            \n");
	sbReport.append(" su_year_2 = ?,	    \n");
	sbReport.append(" su_year_3 = ?,	    \n");
	sbReport.append(" su_year_4 = ?,	    \n");
	sbReport.append(" su_year_5 = ?,	    \n");
	sbReport.append(" su_year_6 = ?,	    \n");
	sbReport.append(" su_year_7 = ?,	    \n");
	sbReport.append(" su_year_8 = ?,	    \n");
	sbReport.append(" start_pro = ?,	    \n");
	sbReport.append(" store = ?,	            \n");
	sbReport.append(" dest = ?,		    \n");
	sbReport.append(" client_cost = ?,	    \n");
	sbReport.append(" pro_usage = ?,	    \n");
	sbReport.append(" ket_cost = ?,	            \n");
	sbReport.append(" target_cost = ?,	    \n");
	sbReport.append(" lme_cu = ?,	            \n");
	sbReport.append(" u_ex_rate = ?,	    \n");
	sbReport.append(" pack_type = ?,	    \n");
	sbReport.append(" usage = ?,		    \n");
	sbReport.append(" make_type = ?,	    \n");
	sbReport.append("pro_1 = ?,		    \n");
	sbReport.append(" eff_value = ?,	    \n");
	sbReport.append(" actual_cost_1 = ?,	    \n");
	sbReport.append(" earn_rate_1 = ?,	    \n");
	sbReport.append(" actual_cost_2 = ?,	    \n");
	sbReport.append(" earn_rate_2 = ?,	    \n");
	sbReport.append(" actual_cost_3 = ?,	    \n");
	sbReport.append(" earn_rate_3 = ?,	    \n");
	sbReport.append(" actual_cost_sum_1 = ?,    \n");
	sbReport.append(" earn_rate_sum_1 = replace(replace(?,',',''),'%'),	\n");
	sbReport.append(" actual_cost_sum_2 = ?,				\n");
	sbReport.append(" earn_rate_sum_2 =  replace(replace(?,',',''),'%'),    \n");
	sbReport.append(" actual_cost_sum_3 = ?,				\n");
	sbReport.append(" earn_rate_sum_3 =  replace(replace(?,',',''),'%'),    \n");
	sbReport.append(" mold_count = ?,					\n");
	sbReport.append(" press_count = ?,					\n");
	sbReport.append(" line_count = ?,					\n");
	sbReport.append(" pack_count = ?,					\n");
	sbReport.append(" repack_count = ?,					\n");
	sbReport.append(" mold_total_1 = ?,					\n");
	sbReport.append(" press_total_1 = ?,					\n");
	sbReport.append(" line_total_1 = ?,					\n");
	sbReport.append(" pack_total_1 = ?,					\n");
	sbReport.append(" repack_total_1 = ?,					\n");
	sbReport.append(" mold_total_2 = ?,					\n");
	sbReport.append(" press_total_2 = ?,					\n");
	sbReport.append(" line_total_2 = ?,					\n");
	sbReport.append(" pack_total_2 = ?,					\n");
	sbReport.append(" repack_total_2 = ?,					\n");
	sbReport.append(" mold_total_3 = ?,					\n");
	sbReport.append(" press_total_3 = ?,					\n");
	sbReport.append(" line_total_3 = ?,					\n");
	sbReport.append(" pack_total_3 = ?,					\n");
	sbReport.append(" repack_total_3 = ?,					\n");
	sbReport.append(" m_depr_stan = ?,					\n");
	sbReport.append(" p_depr_stan = ?,					\n");
	sbReport.append(" L_depr_stan = ?,					\n");
	sbReport.append(" pack_depr_stan = ?,					\n");
	sbReport.append(" repack_depr_stan = ?,				        \n");
	sbReport.append(" total_sales_1 = ?,					\n");
	sbReport.append(" total_sales_2 = ?,					\n");
	sbReport.append(" total_sales_3 = ?,					\n");
	sbReport.append(" total_sales_4 = ?,					\n");
	sbReport.append(" total_sales_5 = ?,					\n");
	sbReport.append(" total_sales_6 = ?,					\n");
	sbReport.append(" total_sales_7 = ?,					\n");
	sbReport.append(" total_sales_8 = ?,					\n");
	sbReport.append(" profit_1 = ?,					        \n");
	sbReport.append(" profit_2 = ?,					        \n");
	sbReport.append(" profit_3 = ?,					        \n");
	sbReport.append(" profit_4 = ?,					        \n");
	sbReport.append(" profit_5 = ?,					        \n");
	sbReport.append(" profit_6 = ?,					        \n");
	sbReport.append(" profit_7 = ?,					        \n");
	sbReport.append(" profit_8 = ?,					        \n");
	sbReport.append(" per_profit_1 = ?,					\n");
	sbReport.append(" per_profit_2 = ?,					\n");
	sbReport.append(" per_profit_3 = ?,					\n");
	sbReport.append(" per_profit_4 = ?,					\n");
	sbReport.append(" per_profit_5 = ?,					\n");
	sbReport.append(" per_profit_6 = ?,					\n");
	sbReport.append(" per_profit_7 = ?,					\n");
	sbReport.append(" per_profit_8 = ?,					\n");
	sbReport.append(" su_stan_day = ?,					\n");
	sbReport.append(" note_1 = ?,						\n");
	sbReport.append(" note_2 = ?,						\n");
	sbReport.append(" note_3 = ?,						\n");
	sbReport.append(" note_4 = ?,						\n");
	sbReport.append(" note_5 = ?,						\n");
	sbReport.append(" note_5_1 = ?,						\n");
	sbReport.append(" case_1_note = ?,					\n");
	sbReport.append(" case_2_note = ?,					\n");
	sbReport.append(" case_3_note = ?,					\n");
	sbReport.append(" case_to_note_1 = ?,					\n");
	sbReport.append(" case_to_note_2 = ?,					\n");
	sbReport.append(" case_to_note_3 = ?,					\n");
	sbReport.append(" tocost_year = ?,					\n");
	sbReport.append(" select_cost = ?					\n");
	sbReport.append(" WHERE crp_group = ? AND group_no = ?			\n");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sbInfo.toString());
	    pstmt.setString(1, (String) insertItem.get("group_no"));
	    pstmt.setString(2, (String) insertItem.get("pk_cr_group"));
	    pstmt.setString(3, (String) insertItem.get("dev_step"));
	    pstmt.setString(4, (String) insertItem.get("pjt_name"));
	    pstmt.setString(5, (String) insertItem.get("pjt_no"));
	    pstmt.setString(6, (String) insertItem.get("rev_pk"));
	    pstmt.setString(7, (String) insertItem.get("rev_no"));
	    pstmt.setString(8, (String) insertItem.get("part_name"));
	    pstmt.setString(9, (String) insertItem.get("team"));
	    pstmt.setString(10, (String) insertItem.get("f_name"));
	    pstmt.setString(11, (String) insertItem.get("a_name"));
	    pstmt.setString(12, (String) insertItem.get("w_name"));
	    pstmt.setString(13, (String) insertItem.get("request_txt"));
	    pstmt.setString(14, (String) insertItem.get("app_part"));
	    pstmt.setString(15, (String) insertItem.get("car_type"));
	    pstmt.setString(16, (String) insertItem.get("customer_F"));
	    pstmt.setString(17, (String) insertItem.get("crp_group"));
	    pstmt.setString(18, "T" + (String) insertItem.get("k"));

	    /*
	     * System.out.println("group_no==>"+(String)insertItem.get("group_no"));
	     * System.out.println("pk_cr_group==>"+(String)insertItem.get("pk_cr_group"));
	     * System.out.println("dev_step==>"+(String)insertItem.get("dev_step"));
	     * System.out.println("pjt_name==>"+(String)insertItem.get("pjt_name"));
	     * System.out.println("pjt_no==>"+(String)insertItem.get("pjt_no"));
	     * System.out.println("rev_pk==>"+(String)insertItem.get("rev_pk"));
	     * System.out.println("rev_no==>"+(String)insertItem.get("rev_no"));
	     * System.out.println("part_name==>"+(String)insertItem.get("part_name"));
	     * System.out.println("team==>"+(String)insertItem.get("team"));
	     * System.out.println("a_name==>"+(String)insertItem.get("a_name"));
	     * System.out.println("w_name==>"+(String)insertItem.get("w_name"));
	     * System.out.println("request_txt==>"+(String)insertItem.get("request_txt"));
	     * System.out.println("app_part==>"+(String)insertItem.get("app_part"));
	     * System.out.println("car_type==>"+(String)insertItem.get("car_type"));
	     * System.out.println("customer_F==>"+(String)insertItem.get("customer_F"));
	     * System.out.println("crp_group==>"+(String)insertItem.get("crp_group"));
	     * System.out.println("group====> "+"T"+(String)insertItem.get("k"));
	     * System.out.println("case_to_note_1====> "+(String)insertItem.get("case_to_note_1"));
	     */
	    complet = pstmt.executeUpdate();

	    if (complet > 0) {

		pstmt = conn.prepareStatement(sbReport.toString());

		pstmt.setString(1, "T" + (String) insertItem.get("k"));
		pstmt.setString(2, (String) insertItem.get("FK_cost_work_1"));
		pstmt.setString(3, (String) insertItem.get("FK_cost_work_2"));
		pstmt.setString(4, (String) insertItem.get("FK_cost_work_3"));
		pstmt.setString(5, (String) insertItem.get("FK_cost_request"));
		pstmt.setString(6, (String) insertItem.get("report_dest"));
		pstmt.setString(7, (String) insertItem.get("su_year_1"));
		pstmt.setString(8, (String) insertItem.get("su_year_2"));
		pstmt.setString(9, (String) insertItem.get("su_year_3"));
		pstmt.setString(10, (String) insertItem.get("su_year_4"));
		pstmt.setString(11, (String) insertItem.get("su_year_5"));
		pstmt.setString(12, (String) insertItem.get("su_year_6"));
		pstmt.setString(13, (String) insertItem.get("su_year_7"));
		pstmt.setString(14, (String) insertItem.get("su_year_8"));
		pstmt.setString(15, (String) insertItem.get("start_pro"));
		pstmt.setString(16, (String) insertItem.get("store"));
		pstmt.setString(17, (String) insertItem.get("dest"));
		pstmt.setString(18, (String) insertItem.get("client_cost"));
		pstmt.setString(19, (String) insertItem.get("pro_usage"));
		pstmt.setString(20, (String) insertItem.get("ket_cost"));
		pstmt.setString(21, (String) insertItem.get("target_cost"));
		pstmt.setString(22, (String) insertItem.get("lme_cu"));
		pstmt.setString(23, (String) insertItem.get("u_ex_rate"));
		pstmt.setString(24, (String) insertItem.get("pack_type"));
		pstmt.setString(25, (String) insertItem.get("usage"));
		pstmt.setString(26, (String) insertItem.get("make_type"));
		pstmt.setString(27, (String) insertItem.get("pro_1"));
		pstmt.setDouble(28, Double.parseDouble((String) insertItem.get("eff_value")));
		pstmt.setString(29, (String) insertItem.get("actual_cost_1"));
		pstmt.setString(30, (String) insertItem.get("earn_rate_1"));
		pstmt.setString(31, (String) insertItem.get("actual_cost_2"));
		pstmt.setString(32, (String) insertItem.get("earn_rate_2"));
		pstmt.setString(33, (String) insertItem.get("actual_cost_3"));
		pstmt.setString(34, (String) insertItem.get("earn_rate_3"));
		pstmt.setString(35, (String) insertItem.get("actual_cost_sum_1"));
		pstmt.setString(36, (String) insertItem.get("earn_rate_sum_1"));
		pstmt.setString(37, (String) insertItem.get("actual_cost_sum_2"));
		pstmt.setString(38, (String) insertItem.get("earn_rate_sum_2"));
		pstmt.setString(39, (String) insertItem.get("actual_cost_sum_3"));
		pstmt.setString(40, (String) insertItem.get("earn_rate_sum_3"));
		pstmt.setString(41, (String) insertItem.get("mold_count"));
		pstmt.setString(42, (String) insertItem.get("press_count"));
		pstmt.setString(43, (String) insertItem.get("line_count"));
		pstmt.setString(44, (String) insertItem.get("pack_count"));
		pstmt.setString(45, (String) insertItem.get("repack_count"));
		pstmt.setString(46, (String) insertItem.get("mold_total_1"));
		pstmt.setString(47, (String) insertItem.get("press_total_1"));
		pstmt.setString(48, (String) insertItem.get("line_total_1"));
		pstmt.setString(49, (String) insertItem.get("pack_total_1"));
		pstmt.setString(50, (String) insertItem.get("repack_total_1"));
		pstmt.setString(51, (String) insertItem.get("mold_total_2"));
		pstmt.setString(52, (String) insertItem.get("press_total_2"));
		pstmt.setString(53, (String) insertItem.get("line_total_2"));
		pstmt.setString(54, (String) insertItem.get("pack_total_2"));
		pstmt.setString(55, (String) insertItem.get("repack_total_2"));
		pstmt.setString(56, (String) insertItem.get("mold_total_3"));
		pstmt.setString(57, (String) insertItem.get("press_total_3"));
		pstmt.setString(58, (String) insertItem.get("line_total_3"));
		pstmt.setString(59, (String) insertItem.get("pack_total_3"));
		pstmt.setString(60, (String) insertItem.get("repack_total_3"));
		pstmt.setString(61, (String) insertItem.get("m_depr_stan"));
		pstmt.setString(62, (String) insertItem.get("p_depr_stan"));
		pstmt.setString(63, (String) insertItem.get("L_depr_stan"));
		pstmt.setString(64, (String) insertItem.get("pack_depr_stan"));
		pstmt.setString(65, (String) insertItem.get("repack_depr_stan"));

		System.out.println("team=>" + ((String) insertItem.get("team")));
		System.out.println("f_name=>" + ((String) insertItem.get("f_name")));
		/*
	         * pstmt.setString(66, (String)insertItem.get("total_sales_1")); pstmt.setString(67,
	         * (String)insertItem.get("total_sales_2")); pstmt.setString(68, (String)insertItem.get("total_sales_3"));
	         * pstmt.setString(69, (String)insertItem.get("total_sales_4")); pstmt.setString(70,
	         * (String)insertItem.get("total_sales_5")); pstmt.setString(71, (String)insertItem.get("total_sales_6"));
	         * pstmt.setString(72, (String)insertItem.get("total_sales_7")); pstmt.setString(73,
	         * (String)insertItem.get("total_sales_8")); pstmt.setString(74, (String)insertItem.get("profit_1")); pstmt.setString(75,
	         * (String)insertItem.get("profit_2")); pstmt.setString(76, (String)insertItem.get("profit_3")); pstmt.setString(77,
	         * (String)insertItem.get("profit_4")); pstmt.setString(78, (String)insertItem.get("profit_5")); pstmt.setString(79,
	         * (String)insertItem.get("profit_6")); pstmt.setString(80, (String)insertItem.get("profit_7")); pstmt.setString(81,
	         * (String)insertItem.get("profit_8")); pstmt.setString(82, (String)insertItem.get("per_profit_1")); pstmt.setString(83,
	         * (String)insertItem.get("per_profit_2")); pstmt.setString(84, (String)insertItem.get("per_profit_3")); pstmt.setString(85,
	         * (String)insertItem.get("per_profit_4")); pstmt.setString(86, (String)insertItem.get("per_profit_5")); pstmt.setString(87,
	         * (String)insertItem.get("per_profit_6")); pstmt.setString(88, (String)insertItem.get("per_profit_7")); pstmt.setString(89,
	         * (String)insertItem.get("per_profit_8"));
	         */
		/*
	         * System.out.println("total_sales_1=>"+((String)insertItem.get("total_sales_1")));
	         * System.out.println("total_sales_2=>"+((String)insertItem.get("total_sales_2")));
	         * System.out.println("total_sales_3=>"+((String)insertItem.get("total_sales_3")));
	         * System.out.println("total_sales_4=>"+((String)insertItem.get("total_sales_4")));
	         * System.out.println("total_sales_5=>"+((String)insertItem.get("total_sales_5")));
	         * System.out.println("total_sales_6=>"+((String)insertItem.get("total_sales_6")));
	         * System.out.println("total_sales_7=>"+((String)insertItem.get("total_sales_7")));
	         * System.out.println("total_sales_8=>"+((String)insertItem.get("total_sales_8")));
	         * System.out.println("profit_1=>"+((String)insertItem.get("profit_1")));
	         * System.out.println("profit_2=>"+((String)insertItem.get("profit_2")));
	         * System.out.println("profit_3=>"+((String)insertItem.get("profit_3")));
	         * System.out.println("profit_4=>"+((String)insertItem.get("profit_4")));
	         * System.out.println("profit_5=>"+((String)insertItem.get("profit_5")));
	         * System.out.println("profit_6=>"+((String)insertItem.get("profit_6")));
	         * System.out.println("profit_7=>"+((String)insertItem.get("profit_7")));
	         * System.out.println("profit_8=>"+((String)insertItem.get("profit_8")));
	         * System.out.println("per_profit_1=>"+((String)insertItem.get("per_profit_1")));
	         * System.out.println("per_profit_2=>"+((String)insertItem.get("per_profit_2")));
	         * System.out.println("per_profit_3=>"+((String)insertItem.get("per_profit_3")));
	         * System.out.println("per_profit_4=>"+((String)insertItem.get("per_profit_4")));
	         * System.out.println("per_profit_5=>"+((String)insertItem.get("per_profit_5")));
	         * System.out.println("per_profit_6=>"+((String)insertItem.get("per_profit_6")));
	         * System.out.println("per_profit_7=>"+((String)insertItem.get("per_profit_7")));
	         * System.out.println("per_profit_8=>"+((String)insertItem.get("per_profit_8")));
	         */

		pstmt.setDouble(66, StringUtil.nullDouble((String) insertItem.get("total_sales_1")));

		pstmt.setDouble(67, StringUtil.nullDouble((String) insertItem.get("total_sales_2")));

		pstmt.setDouble(68, StringUtil.nullDouble((String) insertItem.get("total_sales_3")));

		pstmt.setDouble(69, StringUtil.nullDouble((String) insertItem.get("total_sales_4")));

		pstmt.setDouble(70, StringUtil.nullDouble((String) insertItem.get("total_sales_5")));

		pstmt.setDouble(71, StringUtil.nullDouble((String) insertItem.get("total_sales_6")));

		pstmt.setDouble(72, StringUtil.nullDouble((String) insertItem.get("total_sales_7")));

		pstmt.setDouble(73, StringUtil.nullDouble((String) insertItem.get("total_sales_8")));

		pstmt.setDouble(74, StringUtil.nullDouble((String) insertItem.get("profit_1")));

		pstmt.setDouble(75, StringUtil.nullDouble((String) insertItem.get("profit_2")));

		pstmt.setDouble(76, StringUtil.nullDouble((String) insertItem.get("profit_3")));

		pstmt.setDouble(77, StringUtil.nullDouble((String) insertItem.get("profit_4")));

		pstmt.setDouble(78, StringUtil.nullDouble((String) insertItem.get("profit_5")));

		pstmt.setDouble(79, StringUtil.nullDouble((String) insertItem.get("profit_6")));

		pstmt.setDouble(80, StringUtil.nullDouble((String) insertItem.get("profit_7")));

		pstmt.setDouble(81, StringUtil.nullDouble((String) insertItem.get("profit_8")));

		pstmt.setDouble(82, StringUtil.nullDouble((String) insertItem.get("per_profit_1")));

		pstmt.setDouble(83, StringUtil.nullDouble((String) insertItem.get("per_profit_2")));

		pstmt.setDouble(84, StringUtil.nullDouble((String) insertItem.get("per_profit_3")));

		pstmt.setDouble(85, StringUtil.nullDouble((String) insertItem.get("per_profit_4")));

		pstmt.setDouble(86, StringUtil.nullDouble((String) insertItem.get("per_profit_5")));

		pstmt.setDouble(87, StringUtil.nullDouble((String) insertItem.get("per_profit_6")));

		pstmt.setDouble(88, StringUtil.nullDouble((String) insertItem.get("per_profit_7")));

		pstmt.setDouble(89, StringUtil.nullDouble((String) insertItem.get("per_profit_8")));

		pstmt.setString(90, (String) insertItem.get("su_stan_day"));
		pstmt.setString(91, (String) insertItem.get("note_1"));
		pstmt.setString(92, (String) insertItem.get("note_2"));
		pstmt.setString(93, (String) insertItem.get("note_3"));
		pstmt.setString(94, (String) insertItem.get("note_4"));
		pstmt.setString(95, (String) insertItem.get("note_5"));
		pstmt.setString(96, (String) insertItem.get("note_5_1"));
		pstmt.setString(97, (String) insertItem.get("case_1_note"));
		pstmt.setString(98, (String) insertItem.get("case_2_note"));
		pstmt.setString(99, (String) insertItem.get("case_3_note"));
		pstmt.setString(100, (String) insertItem.get("case_to_note_1"));
		pstmt.setString(101, (String) insertItem.get("case_to_note_2"));
		pstmt.setString(102, (String) insertItem.get("case_to_note_3"));
		pstmt.setString(103, (String) insertItem.get("tocost_year"));
		pstmt.setString(104, (String) insertItem.get("select_cost"));
		pstmt.setString(105, (String) insertItem.get("crp_group"));
		pstmt.setString(106, "T" + (String) insertItem.get("k"));

		complet = pstmt.executeUpdate();

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sbInfo.delete(0, sbInfo.length());
	    sbReport.delete(0, sbInfo.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return complet;
    }

    /**
     * 함수명 : setdbCostWorkInfoUpdate 설명 :보고서의 원가세부내역 제품정보 UPDATE
     * 
     * @param String
     *            pk_cr_group, String rev_no
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 17.
     */
    public int setSelectCostUpdate(String pk_cr_group, String rev_no) throws Exception {
	int complet = 0;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("UPDATE cost_work  SET select_cost = ''  WHERE pk_cr_group = ? AND rev_no = ?  ");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    complet = pstmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return complet;
    }

    /**
     * 함수명 : getPkPid 설명 :productInfo의 pk_pid 구하기
     * 
     * @param
     * @return String complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 17.
     */
    public String getPkPid() throws Exception {
	String complet = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	StringBuffer sbReport = new StringBuffer();

	sb.append("SELECT productinfo_pk_pid.nextVal AS pk_pid FROM dual ");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		complet = rs.getString("pk_pid");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return complet;
    }

    /**
     * 함수명 : getPkPid 설명 :productInfo의 pk_pid 구하기
     * 
     * @param
     * @return String complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 17.
     */
    public String getPkCrt() throws Exception {
	String complet = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT cost_report_pk_crp.nextVal AS pk_crt FROM dual ");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		complet = rs.getString("pk_crt");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return complet;
    }

    /**
     * 함수명 : setdbCostWorkInsert 설명 :보고서의 원가세부내역 제품정보 INSERT
     * 
     * @param ArrayList
     *            insertItemList
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 17.
     */
    public ArrayList setdbCostWorkInsert(ArrayList insertItemList) throws Exception {
	int complet = 1;

	ArrayList returnItemList = new ArrayList();

	Hashtable insertItem = (Hashtable) insertItemList.get(0);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sbInfo = new StringBuffer();
	StringBuffer sbReport = new StringBuffer();
	String pk_pid = getPkPid();
	String pk_crp = getPkCrt();

	// System.out.println("pk_pid==>"+pk_pid);
	// sbInfo.append("INSERT INTO cost_productinfo (pk_pid, group_no, pk_cr_group, dev_step, pjt_name, pjt_no, rev_pk, rev_no, part_name, team, f_name, a_name, w_name, request_txt, app_part, car_type, customer_F) \n");
	// sbInfo.append("       VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \n");

	sbReport.append("INSERT INTO cost_report (\n");
	sbReport.append("pk_crp,						fk_pid,  				    group_no,			FK_cost_work_1,	FK_cost_work_2,		FK_cost_work_3,		FK_cost_request,		report_dest,			su_year_1,				su_year_2,  			\n");
	sbReport.append("su_year_3, 					su_year_4, 			su_year_5,		su_year_6,			su_year_7,				su_year_8,				start_pro,					store,					dest,							client_cost,  			\n");
	sbReport.append("pro_usage,     				ket_cost,				target_cost,		lme_cu,					u_ex_rate,					pack_type,				usage,						make_type,			pro_1,						eff_value,     			\n");
	sbReport.append("actual_cost_1, 			earn_rate_1, 			actual_cost_2, 	earn_rate_2, 			actual_cost_3, 			earn_rate_3, 				actual_cost_sum_1, 	earn_rate_sum_1, 	actual_cost_sum_2, 	earn_rate_sum_2,  \n");
	sbReport.append("actual_cost_sum_3,	earn_rate_sum_3,	mold_count,		press_count,			line_count,					pack_count,				repack_count,			mold_total_1,			press_total_1,			line_total_1,           \n");
	sbReport.append("pack_total_1,				repack_total_1,		mold_total_2,		press_total_2,		line_total_2,				pack_total_2,				repack_total_2,			mold_total_3,			press_total_3,			line_total_3, 			\n");
	sbReport.append("pack_total_3,				repack_total_3,		m_depr_stan,		p_depr_stan,			L_depr_stan,				pack_depr_stan,		repack_depr_stan,		total_sales_1,		total_sales_2,			total_sales_3, 		 \n");
	sbReport.append("total_sales_4, 			total_sales_5, 		total_sales_6, 	total_sales_7, 		total_sales_8, 			profit_1, 					profit_2, 					profit_3, 				profit_4, 					profit_5, 	 			\n");
	sbReport.append("profit_6, 						profit_7, 				profit_8, 			per_profit_1, 			per_profit_2, 				per_profit_3, 				per_profit_4, 				per_profit_5, 			per_profit_6, 				per_profit_7, 			\n");
	sbReport.append("per_profit_8, 				su_stan_day, 		note_1, 				note_2, 					note_3, 						note_4, 						note_5, 						note_5_1,             	\n");
	// sbReport.append("case_1_note, case_2_note, case_3_note, case_to_note_1, case_to_note_2, case_to_note_3, pack_type, tocost_year, select_cost) \n");
	// sbReport.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
	if ("0".equals((String) insertItem.get("j"))) {
	    sbReport.append("case_1_note,		case_2_note,		case_3_note,		case_to_note_1,		case_to_note_2,		case_to_note_3,		tocost_year,		select_cost,		crp_group,  fk_wid	) \n");
	    sbReport.append("VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	    sbReport.append("				?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, replace(replace(?,',',''),'%',''), ?, replace(replace(?,',',''),'%',''),");
	    sbReport.append("				?, replace(replace(?,',',''),'%',''), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	    sbReport.append("				?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,		");
	    sbReport.append("             ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,		");
	    sbReport.append("		        ?, ?, ?, ?, ?, ?, ?, (select pk_wid 									                                    ");
	    sbReport.append("											 from wfinfo                                                                            ");
	    sbReport.append("										   where fk_cost in (select fk_cost_request from cost_work		    ");
	    sbReport.append("	                						 						 where fk_pid in (select pk_pid from cost_productinfo	");
	    sbReport.append("	                                  		 												 where pk_cr_group = ? and group_no = ? and rev_no = ?) AND CASE_TYPE_ADMIN = 'main' )) ) \n");
	} else {
	    sbReport.append("case_1_note,		case_2_note,		case_3_note,		case_to_note_1,		case_to_note_2,		case_to_note_3,		tocost_year, 		select_cost, fk_wid	) \n");
	    sbReport.append("VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	    sbReport.append("				?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, replace(replace(?,',',''),'%',''), ?, replace(replace(?,',',''),'%',''),");
	    sbReport.append("				?, replace(replace(?,',',''),'%',''), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	    sbReport.append("				?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	    sbReport.append("             ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
	    sbReport.append("		        ?, ?, ?, ?, ?, ?,(select pk_wid 									                                    ");
	    sbReport.append("											 from wfinfo                                                                            ");
	    sbReport.append("										   where fk_cost in (select fk_cost_request from cost_work		    ");
	    sbReport.append("	                						 						 where fk_pid in (select pk_pid from cost_productinfo	");
	    sbReport.append("	                                  		 												 where pk_cr_group = ? and group_no = ? and rev_no = ?) AND CASE_TYPE_ADMIN = 'main')) ) \n");
	}

	sbInfo.append("UPDATE cost_productinfo SET dev_step = ?, pjt_name = ?, pjt_no = ?, rev_pk = ?, part_name = ?, \n");
	sbInfo.append("                                              team = ?, f_name = ?, a_name = ?, w_name = ?, request_txt = ?, app_part = ?, car_type = ?, customer_F = ? \n");
	sbInfo.append("    where pk_cr_group = ? and rev_no = ? and group_no = ? \n");

	try {
	    pstmt = conn.prepareStatement(sbInfo.toString());

	    pstmt.setString(1, (String) insertItem.get("dev_step"));
	    pstmt.setString(2, (String) insertItem.get("pjt_name"));
	    pstmt.setString(3, (String) insertItem.get("pjt_no"));
	    pstmt.setString(4, (String) insertItem.get("rev_pk"));
	    pstmt.setString(5, (String) insertItem.get("part_name"));
	    pstmt.setString(6, (String) insertItem.get("team"));
	    pstmt.setString(7, (String) insertItem.get("f_name"));
	    pstmt.setString(8, (String) insertItem.get("a_name"));
	    pstmt.setString(9, (String) insertItem.get("w_name"));
	    pstmt.setString(10, (String) insertItem.get("request_txt"));
	    pstmt.setString(11, (String) insertItem.get("app_part"));
	    pstmt.setString(12, (String) insertItem.get("car_type"));
	    pstmt.setString(13, (String) insertItem.get("customer_F"));
	    pstmt.setString(14, (String) insertItem.get("pk_cr_group"));
	    pstmt.setString(15, (String) insertItem.get("rev_no"));
	    pstmt.setString(16, (String) insertItem.get("group_no"));
	    complet = pstmt.executeUpdate();
	    pstmt = null;
	    // if(complet > 0){
	    pstmt = conn.prepareStatement(sbReport.toString());
	    pstmt.setString(1, pk_crp);
	    // System.out.println("pk_cr_group==>"+(String)insertItem.get("pk_cr_group"));
	    // System.out.println("group_no==>"+(String)insertItem.get("group_no"));
	    pstmt.setString(2, (String) insertItem.get("pk_pid"));
	    pstmt.setString(3, (String) insertItem.get("group_no"));
	    pstmt.setString(4, (String) insertItem.get("FK_cost_work_1"));
	    System.out.println("FK_cost_work_2==>" + (String) insertItem.get("FK_cost_work_2"));
	    pstmt.setString(5, (String) insertItem.get("FK_cost_work_2"));
	    pstmt.setString(6, (String) insertItem.get("FK_cost_work_3"));
	    pstmt.setString(7, (String) insertItem.get("FK_cost_request"));
	    pstmt.setString(8, (String) insertItem.get("report_dest"));
	    pstmt.setString(9, (String) insertItem.get("su_year_1"));
	    pstmt.setString(10, (String) insertItem.get("su_year_2"));
	    pstmt.setString(11, (String) insertItem.get("su_year_3"));
	    pstmt.setString(12, (String) insertItem.get("su_year_4"));
	    pstmt.setString(13, (String) insertItem.get("su_year_5"));
	    pstmt.setString(14, (String) insertItem.get("su_year_6"));
	    pstmt.setString(15, (String) insertItem.get("su_year_7"));
	    pstmt.setString(16, (String) insertItem.get("su_year_8"));
	    pstmt.setString(17, (String) insertItem.get("start_pro"));
	    pstmt.setString(18, (String) insertItem.get("store"));
	    pstmt.setString(19, (String) insertItem.get("dest"));
	    pstmt.setString(20, (String) insertItem.get("client_cost"));
	    pstmt.setString(21, (String) insertItem.get("pro_usage"));
	    pstmt.setString(22, (String) insertItem.get("ket_cost"));
	    pstmt.setString(23, (String) insertItem.get("target_cost"));
	    pstmt.setString(24, (String) insertItem.get("lme_cu"));
	    pstmt.setString(25, (String) insertItem.get("u_ex_rate"));
	    pstmt.setString(26, (String) insertItem.get("pack_type"));
	    pstmt.setString(27, (String) insertItem.get("usage"));
	    pstmt.setString(28, (String) insertItem.get("make_type"));
	    pstmt.setString(29, (String) insertItem.get("pro_1"));
	    pstmt.setDouble(30, StringUtil.nullDouble((String) insertItem.get("eff_value")));
	    pstmt.setString(31, (String) insertItem.get("actual_cost_1"));
	    pstmt.setString(32, (String) insertItem.get("earn_rate_1"));
	    pstmt.setString(33, (String) insertItem.get("actual_cost_2"));
	    pstmt.setString(34, (String) insertItem.get("earn_rate_2"));
	    pstmt.setString(35, (String) insertItem.get("actual_cost_3"));
	    pstmt.setString(36, (String) insertItem.get("earn_rate_3"));
	    pstmt.setString(37, (String) insertItem.get("actual_cost_sum_1"));
	    pstmt.setString(38, (String) insertItem.get("earn_rate_sum_1"));
	    pstmt.setString(39, (String) insertItem.get("actual_cost_sum_2"));
	    pstmt.setString(40, (String) insertItem.get("earn_rate_sum_2"));
	    pstmt.setString(41, (String) insertItem.get("actual_cost_sum_3"));
	    pstmt.setString(42, (String) insertItem.get("earn_rate_sum_3"));
	    pstmt.setString(43, (String) insertItem.get("mold_count"));
	    pstmt.setString(44, (String) insertItem.get("press_count"));
	    pstmt.setString(45, (String) insertItem.get("line_count"));
	    pstmt.setString(46, (String) insertItem.get("pack_count"));
	    pstmt.setString(47, (String) insertItem.get("repack_count"));
	    pstmt.setString(48, (String) insertItem.get("mold_total_1"));
	    pstmt.setString(49, (String) insertItem.get("press_total_1"));
	    pstmt.setString(50, (String) insertItem.get("line_total_1"));
	    pstmt.setString(51, (String) insertItem.get("pack_total_1"));
	    pstmt.setString(52, (String) insertItem.get("repack_total_1"));
	    pstmt.setString(53, (String) insertItem.get("mold_total_2"));
	    pstmt.setString(54, (String) insertItem.get("press_total_2"));
	    pstmt.setString(55, (String) insertItem.get("line_total_2"));
	    pstmt.setString(56, (String) insertItem.get("pack_total_2"));
	    pstmt.setString(57, (String) insertItem.get("repack_total_2"));
	    pstmt.setString(58, (String) insertItem.get("mold_total_3"));
	    pstmt.setString(59, (String) insertItem.get("press_total_3"));
	    pstmt.setString(60, (String) insertItem.get("line_total_3"));
	    pstmt.setString(61, (String) insertItem.get("pack_total_3"));
	    pstmt.setString(62, (String) insertItem.get("repack_total_3"));
	    pstmt.setString(63, (String) insertItem.get("m_depr_stan"));
	    pstmt.setString(64, (String) insertItem.get("p_depr_stan"));
	    pstmt.setString(65, (String) insertItem.get("L_depr_stan"));
	    pstmt.setString(66, (String) insertItem.get("pack_depr_stan"));
	    pstmt.setString(67, (String) insertItem.get("repack_depr_stan"));

	    pstmt.setDouble(68, StringUtil.nullDouble((String) insertItem.get("total_sales_1")));
	    pstmt.setDouble(69, StringUtil.nullDouble((String) insertItem.get("total_sales_2")));
	    pstmt.setDouble(70, StringUtil.nullDouble((String) insertItem.get("total_sales_3")));
	    pstmt.setDouble(71, StringUtil.nullDouble((String) insertItem.get("total_sales_4")));
	    pstmt.setDouble(72, StringUtil.nullDouble((String) insertItem.get("total_sales_5")));
	    pstmt.setDouble(73, StringUtil.nullDouble((String) insertItem.get("total_sales_6")));
	    pstmt.setDouble(74, StringUtil.nullDouble((String) insertItem.get("total_sales_7")));
	    pstmt.setDouble(75, StringUtil.nullDouble((String) insertItem.get("total_sales_8")));

	    /*
	     * pstmt.setInt(76 , Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_1")))); pstmt.setInt(77 ,
	     * Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_2")))); pstmt.setInt(78 ,
	     * Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_3")))); pstmt.setInt(79 ,
	     * Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_4")))); pstmt.setInt(80 ,
	     * Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_5")))); pstmt.setInt(81 ,
	     * Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_6")))); pstmt.setInt(82 ,
	     * Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_7")))); pstmt.setInt(83 ,
	     * Integer.parseInt(StringUtil.checkNullZero((String)insertItem.get("profit_8"))));
	     */

	    pstmt.setDouble(76, StringUtil.nullDouble((String) insertItem.get("profit_1")));
	    pstmt.setDouble(77, StringUtil.nullDouble((String) insertItem.get("profit_2")));
	    pstmt.setDouble(78, StringUtil.nullDouble((String) insertItem.get("profit_3")));
	    pstmt.setDouble(79, StringUtil.nullDouble((String) insertItem.get("profit_4")));
	    pstmt.setDouble(80, StringUtil.nullDouble((String) insertItem.get("profit_5")));
	    pstmt.setDouble(81, StringUtil.nullDouble((String) insertItem.get("profit_6")));
	    pstmt.setDouble(82, StringUtil.nullDouble((String) insertItem.get("profit_7")));
	    pstmt.setDouble(83, StringUtil.nullDouble((String) insertItem.get("profit_8")));

	    pstmt.setDouble(84, StringUtil.nullDouble((String) insertItem.get("per_profit_1")));
	    pstmt.setDouble(85, StringUtil.nullDouble((String) insertItem.get("per_profit_2")));
	    pstmt.setDouble(86, StringUtil.nullDouble((String) insertItem.get("per_profit_3")));
	    pstmt.setDouble(87, StringUtil.nullDouble((String) insertItem.get("per_profit_4")));
	    pstmt.setDouble(88, StringUtil.nullDouble((String) insertItem.get("per_profit_5")));
	    pstmt.setDouble(89, StringUtil.nullDouble((String) insertItem.get("per_profit_6")));
	    pstmt.setDouble(90, StringUtil.nullDouble((String) insertItem.get("per_profit_7")));
	    pstmt.setDouble(91, StringUtil.nullDouble((String) insertItem.get("per_profit_8")));

	    pstmt.setString(92, (String) insertItem.get("su_stan_day"));
	    pstmt.setString(93, (String) insertItem.get("note_1"));
	    pstmt.setString(94, (String) insertItem.get("note_2"));
	    pstmt.setString(95, (String) insertItem.get("note_3"));
	    pstmt.setString(96, (String) insertItem.get("note_4"));
	    pstmt.setString(97, (String) insertItem.get("note_5"));
	    pstmt.setString(98, (String) insertItem.get("note_5_1"));
	    pstmt.setString(99, (String) insertItem.get("case_1_note"));
	    pstmt.setString(100, (String) insertItem.get("case_2_note"));
	    pstmt.setString(101, (String) insertItem.get("case_3_note"));
	    pstmt.setString(102, (String) insertItem.get("case_to_note_1"));
	    pstmt.setString(103, (String) insertItem.get("case_to_note_2"));
	    pstmt.setString(104, (String) insertItem.get("case_to_note_3"));
	    pstmt.setString(105, (String) insertItem.get("tocost_year"));
	    pstmt.setString(106, (String) insertItem.get("select_cost"));

	    if ("0".equals((String) insertItem.get("j"))) {
		pstmt.setString(107, pk_crp);
		pstmt.setString(108, (String) insertItem.get("pk_cr_group"));
		pstmt.setString(109, (String) insertItem.get("group_no"));
		pstmt.setString(110, (String) insertItem.get("rev_no"));
	    } else {
		pstmt.setString(107, (String) insertItem.get("pk_cr_group"));
		pstmt.setString(108, (String) insertItem.get("group_no"));
		pstmt.setString(109, (String) insertItem.get("rev_no"));
	    }
	    System.out.println(sbReport.toString());
	    System.out.println(pk_crp + "," + (String) insertItem.get("pk_cr_group") + "," + (String) insertItem.get("group_no") + ","
		    + (String) insertItem.get("rev_no") + "," + (String) insertItem.get("group_no") + ","
		    + (String) insertItem.get("FK_cost_work_1") + "," + (String) insertItem.get("FK_cost_work_2") + ","
		    + (String) insertItem.get("FK_cost_work_3") + "," + (String) insertItem.get("FK_cost_request") + ","
		    + (String) insertItem.get("report_dest") + "," + (String) insertItem.get("su_year_1") + ","
		    + (String) insertItem.get("su_year_2") + "," + (String) insertItem.get("su_year_3") + ","
		    + (String) insertItem.get("su_year_4") + "," + (String) insertItem.get("su_year_5") + ","
		    + (String) insertItem.get("su_year_6") + "," + (String) insertItem.get("su_year_7") + ","
		    + (String) insertItem.get("su_year_8") + "," + (String) insertItem.get("start_pro") + ","
		    + (String) insertItem.get("store") + "," + (String) insertItem.get("dest") + ","
		    + (String) insertItem.get("client_cost") + "," + (String) insertItem.get("pro_usage") + ","
		    + (String) insertItem.get("ket_cost") + "," + (String) insertItem.get("target_cost") + ","
		    + (String) insertItem.get("lme_cu") + "," + (String) insertItem.get("u_ex_rate") + ","
		    + (String) insertItem.get("pack_type") + "," + (String) insertItem.get("usage") + ","
		    + (String) insertItem.get("make_type") + "," + (String) insertItem.get("pro_1") + ","
		    + (String) insertItem.get("eff_value") + "," + (String) insertItem.get("actual_cost_1") + ","
		    + (String) insertItem.get("earn_rate_1") + "," + (String) insertItem.get("actual_cost_2") + ","
		    + (String) insertItem.get("earn_rate_2") + "," + (String) insertItem.get("actual_cost_3") + ","
		    + (String) insertItem.get("earn_rate_3") + "," + (String) insertItem.get("actual_cost_sum_1") + ","
		    + (String) insertItem.get("earn_rate_sum_1") + "," + (String) insertItem.get("actual_cost_sum_2") + ","
		    + (String) insertItem.get("earn_rate_sum_2") + "," + (String) insertItem.get("actual_cost_sum_3") + ","
		    + (String) insertItem.get("earn_rate_sum_3") + "," + (String) insertItem.get("mold_count") + ","
		    + (String) insertItem.get("press_count") + "," + (String) insertItem.get("line_count") + ","
		    + (String) insertItem.get("pack_count") + "," + (String) insertItem.get("repack_count") + ","
		    + (String) insertItem.get("mold_total_1") + "," + (String) insertItem.get("press_total_1") + ","
		    + (String) insertItem.get("line_total_1") + "," + (String) insertItem.get("pack_total_1") + ","
		    + (String) insertItem.get("repack_total_1") + "," + (String) insertItem.get("mold_total_2") + ","
		    + (String) insertItem.get("press_total_2") + "," + (String) insertItem.get("line_total_2") + ","
		    + (String) insertItem.get("pack_total_2") + "," + (String) insertItem.get("repack_total_2") + ","
		    + (String) insertItem.get("mold_total_3") + "," + (String) insertItem.get("press_total_3") + ","
		    + (String) insertItem.get("line_total_3") + "," + (String) insertItem.get("pack_total_3") + ","
		    + (String) insertItem.get("repack_total_3") + "," + (String) insertItem.get("m_depr_stan") + ","
		    + (String) insertItem.get("p_depr_stan") + "," + (String) insertItem.get("L_depr_stan") + ","
		    + (String) insertItem.get("pack_depr_stan") + "," + (String) insertItem.get("repack_depr_stan") + ","
		    + (String) insertItem.get("total_sales_1") + "," + (String) insertItem.get("total_sales_2") + ","
		    + (String) insertItem.get("total_sales_3") + "," + (String) insertItem.get("total_sales_4") + ","
		    + (String) insertItem.get("total_sales_5") + "," + (String) insertItem.get("total_sales_6") + ","
		    + (String) insertItem.get("total_sales_7") + "," + (String) insertItem.get("total_sales_8") + ","
		    + (String) insertItem.get("profit_1") + "," + (String) insertItem.get("profit_2") + ","
		    + (String) insertItem.get("profit_3") + "," + (String) insertItem.get("profit_4") + ","
		    + (String) insertItem.get("profit_5") + "," + (String) insertItem.get("profit_6") + ","
		    + (String) insertItem.get("profit_7") + "," + (String) insertItem.get("profit_8") + ","
		    + (String) insertItem.get("per_profit_1") + "," + (String) insertItem.get("per_profit_2") + ","
		    + (String) insertItem.get("per_profit_3") + "," + (String) insertItem.get("per_profit_4") + ","
		    + (String) insertItem.get("per_profit_5") + "," + (String) insertItem.get("per_profit_6") + ","
		    + (String) insertItem.get("per_profit_7") + "," + (String) insertItem.get("per_profit_8") + ","
		    + (String) insertItem.get("su_stan_day") + "," + (String) insertItem.get("note_1") + ","
		    + (String) insertItem.get("note_2") + "," + (String) insertItem.get("note_3") + "," + (String) insertItem.get("note_4")
		    + "," + (String) insertItem.get("note_5") + "," + (String) insertItem.get("note_5_1") + ","
		    + (String) insertItem.get("case_1_note") + "," + (String) insertItem.get("case_2_note") + ","
		    + (String) insertItem.get("case_3_note") + "," + (String) insertItem.get("case_to_note_1") + ","
		    + (String) insertItem.get("case_to_note_2") + "," + (String) insertItem.get("case_to_note_3") + ","
		    + (String) insertItem.get("tocost_year") + "," + (String) insertItem.get("select_cost"));
	    complet = pstmt.executeUpdate();
	    // }
	    if (complet > 0) {
		returnItemList.add(0, complet);
		returnItemList.add(1, pk_crp);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sbInfo.delete(0, sbInfo.length());
	    sbReport.delete(0, sbInfo.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return returnItemList;
    }

    /**
     * 함수명 : setFPkCrpUpdate 설명 :보고서의 원가세부내역 제품정보 UPDATE
     * 
     * @param String
     *            fPkCrp, String fPkCrp0
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 17.
     */
    public int setFPkCrpUpdate(String crp_group, String pk_crp) throws Exception {
	int complet = 0;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("UPDATE cost_report SET crp_group = ? WHERE pk_crp = ? \n");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, crp_group);
	    pstmt.setString(2, pk_crp);
	    complet = pstmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return complet;
    }

    /**
     * 함수명 : setSelectCostUpdate1 설명 :보고서의 원가세부내역 제품정보 UPDATE
     * 
     * @param String
     *            pk_cr_group, String rev_no, String fPkCrp
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 19.
     */
    public int setSelectCostUpdate1(String pk_cr_group, String rev_no, String fPkCrp) throws Exception {
	int complet = 0;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sbCostWork = new StringBuffer();
	StringBuffer sbInfo = new StringBuffer();

	sbCostWork
	        .append(" UPDATE (SELECT cost_report_add, select_cost from cost_productInfo A, cost_work B  WHERE B.fk_pid = A.pk_pid AND B.pk_cr_group = ? AND B.rev_no = ?) \n");
	sbCostWork.append(" SET cost_report_add = 'ok', \n");
	sbCostWork.append(" select_cost = '' \n");

	sbInfo.append("UPDATE cost_productInfo SET report_pk = ? WHERE pk_cr_group = ? AND rev_no = ? \n");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sbCostWork.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    complet = pstmt.executeUpdate();
	    if (complet > 0) {
		pstmt = conn.prepareStatement(sbInfo.toString());
		pstmt.setString(1, fPkCrp);
		pstmt.setString(2, pk_cr_group);
		pstmt.setString(3, rev_no);
		complet = pstmt.executeUpdate();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sbCostWork.delete(0, sbCostWork.length());
	    sbInfo.delete(0, sbInfo.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return complet;
    }

    /**
     * 함수명 : setSelectCostUpdate1 설명 :보고서의 원가세부내역 제품정보 UPDATE
     * 
     * @param String
     *            report_pk, String note_5, String note_5_1
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 19.
     */
    public int setReportNote5Update(String report_pk, String note_5, String note_5_1) throws Exception {
	int complet = 0;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("UPDATE cost_report SET note_5 = ?, note_5_1 = ? WHERE crp_group = ? \n");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, note_5);
	    pstmt.setString(2, note_5_1);
	    pstmt.setString(3, report_pk);
	    complet = pstmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return complet;
    }

    /**
     * 함수명 : setSelectCostUpdate1 설명 :보고서의 원가세부내역 제품정보 UPDATE
     * 
     * @param String
     *            pk_cw
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 19.
     */
    public int setCostWorkSelectCostUpdate(String pk_cw) throws Exception {
	int complet = 0;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("UPDATE cost_work SET select_cost = 'ok' WHERE pk_cw = ? \n");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cw);
	    complet = pstmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return complet;
    }

    /**
     * 함수명 : getCostWorkReportViewList 설명 :보고서의 원가세부내역 제품정보 SELECT
     * 
     * @param String
     *            report_pk
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 19.
     */
    public ArrayList getCostWorkReportViewList(String report_pk) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT ");
	sb.append("FK_cost_work_1,");
	sb.append(" FK_cost_work_2,");
	sb.append(" FK_cost_work_3, ");
	sb.append(" pass_type,");
	sb.append(" f_day_2 as f_day,");
	sb.append(" Gr_day_2 as Gr_day, ");
	sb.append(" Leader_day_2 as Leader_day, ");
	sb.append(" Leader_name_2 as Leader_name, ");
	sb.append(" Gr_name_2 as Gr_name, ");
	sb.append(" JB_day,");
	sb.append(" JB_name,");
	sb.append("  p_leader_day,");
	sb.append(" c.p_leader_name,");
	sb.append(" r_owner_day, 		");
	sb.append(" r_pre_day,");
	sb.append(" approval,");
	sb.append(" w_name,");
	sb.append(" app_part,");
	sb.append(" case_1_note,");
	sb.append(" case_2_note,");
	sb.append(" case_3_note,");
	sb.append(" case_to_note_1,");
	sb.append(" case_to_note_2,");
	sb.append(" case_to_note_3,");
	sb.append(" step_no,");
	sb.append(" pk_cr_group,");
	sb.append(" FK_cost_request,");
	sb.append(" dev_step,  ");
	sb.append(" pjt_name, ");
	sb.append(" pjt_no, ");
	sb.append(" decode(b.input_gb,'1',a.part_name,(select part_name from cost_work where PK_CW = b.fk_cost_work_1)) as part_name, ");
	sb.append(" team, ");
	sb.append(" A.f_name,");
	sb.append(" a_name, ");
	sb.append(" request_txt,  ");
	sb.append(" car_type, ");
	sb.append(" customer_F, ");
	sb.append(" su_year_1, ");
	sb.append(" su_year_2, ");
	sb.append(" su_year_3, ");
	sb.append(" su_year_4,    ");
	sb.append(" su_year_5,");
	sb.append(" su_year_6,");
	sb.append(" su_year_7, ");
	sb.append(" su_year_8,");
	sb.append(" report_dest,");
	sb.append(" client_cost,");
	sb.append(" room_earn, ");
	sb.append(" pro_usage,");
	sb.append(" ket_cost, ");
	sb.append(" target_cost,");
	sb.append(" lme_cu, ");
	sb.append(" u_ex_rate, ");
	sb.append(" pack_type, ");
	sb.append(" usage, ");
	sb.append(" make_type,");
	sb.append(" pro_1, ");
	sb.append(" eff_value,");
	sb.append(" actual_cost_1, ");
	sb.append(" earn_rate_1,  ");
	sb.append(" actual_cost_2, ");
	sb.append(" earn_rate_2, ");
	sb.append(" actual_cost_3, ");
	sb.append(" earn_rate_3, ");
	sb.append(" actual_cost_sum_1,  ");
	sb.append(" earn_rate_sum_1, ");
	sb.append(" actual_cost_sum_2, ");
	sb.append(" earn_rate_sum_2,       ");
	sb.append(" actual_cost_sum_3, ");
	sb.append(" earn_rate_sum_3, ");
	sb.append(" mold_count, ");
	sb.append(" press_count, ");
	sb.append(" line_count,  ");
	sb.append(" pack_count,  ");
	sb.append(" repack_count, ");
	sb.append(" mold_total_1,  ");
	sb.append(" press_total_1,  ");
	sb.append(" line_total_1,  ");
	sb.append(" pack_total_1, ");
	sb.append(" mold_total_2, ");
	sb.append(" press_total_2,    ");
	sb.append(" line_total_2, ");
	sb.append(" pack_total_2, ");
	sb.append(" mold_total_3, ");
	sb.append(" press_total_3,");
	sb.append(" line_total_3, ");
	sb.append(" pack_total_3, ");
	sb.append(" repack_total_1,  ");
	sb.append(" repack_total_2, ");
	sb.append(" repack_total_3, ");
	sb.append(" m_depr_stan, ");
	sb.append(" p_depr_stan, ");
	sb.append(" L_depr_stan,  ");
	sb.append(" pack_depr_stan,   ");
	sb.append(" repack_depr_stan,  ");
	sb.append(" total_sales_1,  ");
	sb.append(" total_sales_2,  ");
	sb.append(" total_sales_3,   ");
	sb.append(" total_sales_4,  ");
	sb.append(" total_sales_5,  ");
	sb.append(" total_sales_6,  ");
	sb.append(" total_sales_7,  ");
	sb.append(" total_sales_8,  ");
	sb.append(" nvl(profit_1,0) as profit_1, ");
	sb.append(" nvl(profit_2,0) as profit_2, ");
	sb.append(" nvl(profit_3,0) as profit_3, ");
	sb.append(" nvl(profit_4,0) as profit_4, ");
	sb.append(" nvl(profit_5,0) as profit_5, ");
	sb.append(" nvl(profit_6,0) as profit_6,    ");
	sb.append(" nvl(profit_7,0) as profit_7, ");
	sb.append(" nvl(profit_8,0) as profit_8, ");
	sb.append(" per_profit_1, ");
	sb.append(" per_profit_2, ");
	sb.append(" per_profit_3, ");
	sb.append(" per_profit_4, ");
	sb.append(" per_profit_5,  ");
	sb.append(" per_profit_6,  ");
	sb.append(" per_profit_7, ");
	sb.append(" per_profit_8, ");
	sb.append(" su_stan_day, ");
	sb.append(" note_1,  ");
	sb.append(" note_2,   ");
	sb.append(" note_3,   ");
	sb.append(" note_4||note_6 as note_4,  ");
	sb.append(" note_5,  ");
	sb.append(" note_5_1, ");
	sb.append(" tocost_year,  ");
	sb.append(" FK_cost_work_4, ");
	sb.append(" case_4_note, ");
	sb.append(" case_to_note_4,  ");
	sb.append(" actual_cost_4,  ");
	sb.append(" earn_rate_4, ");
	sb.append(" actual_cost_sum_4,  ");
	sb.append(" earn_rate_sum_4,  ");
	sb.append(" mold_total_4,  ");
	sb.append(" press_total_4, ");
	sb.append(" line_total_4, ");
	sb.append(" pack_total_4, ");
	sb.append(" repack_total_4, ");
	sb.append(" file_1, ");
	sb.append(" file_2, ");
	sb.append(" file_3, ");
	sb.append(" file_4, ");
	sb.append(" file_5, ");
	sb.append(" file_6, ");
	sb.append(" file_2_name, ");
	sb.append(" file_3_name, ");
	sb.append(" file_4_name,  ");
	sb.append(" file_5_name,  ");
	sb.append(" file_6_name, ");
	sb.append(" note_6,          ");
	sb.append(" (select count(*) from cost_invest where report_pk = ?) as invest_cnt, ");
	sb.append(" b.select_cost, b.input_gb    ");
	sb.append(" FROM cost_productInfo A, cost_report B, wfinfo C  WHERE B.fk_pid = A.pk_pid AND B.FK_WID = C.PK_WID AND crp_group = ? ORDER BY A.group_no ");

	System.out.println(sb.toString() + " == " + report_pk);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    pstmt.setString(2, report_pk);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("FK_cost_work_1", StringUtil.checkNull(rs.getString("FK_cost_work_1")));
		SearchMap.put("FK_cost_work_2", StringUtil.checkNull(rs.getString("FK_cost_work_2")));
		SearchMap.put("FK_cost_work_3", StringUtil.checkNull(rs.getString("FK_cost_work_3")));
		SearchMap.put("pass_type", StringUtil.checkNull(rs.getString("pass_type")));
		SearchMap.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		SearchMap.put("Gr_day", StringUtil.checkNull(rs.getString("Gr_day")));
		SearchMap.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		SearchMap.put("Leader_name", StringUtil.checkNull(rs.getString("Leader_name")));
		SearchMap.put("Gr_name", StringUtil.checkNull(rs.getString("Gr_name")));
		SearchMap.put("JB_day", StringUtil.checkNull(rs.getString("JB_day")));
		SearchMap.put("JB_name", StringUtil.checkNull(rs.getString("JB_name")));
		SearchMap.put("p_leader_name", StringUtil.checkNull(rs.getString("p_leader_name")));
		SearchMap.put("p_leader_day", StringUtil.checkNull(rs.getString("p_leader_day")));
		SearchMap.put("r_owner_day", StringUtil.checkNull(rs.getString("r_owner_day")));
		SearchMap.put("r_pre_day", StringUtil.checkNull(rs.getString("r_pre_day")));
		SearchMap.put("approval", StringUtil.checkNull(rs.getString("approval")));
		SearchMap.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		SearchMap.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		SearchMap.put("case_1_note", StringUtil.checkNull(rs.getString("case_1_note")));
		SearchMap.put("case_2_note", StringUtil.checkNull(rs.getString("case_2_note")));
		SearchMap.put("case_3_note", StringUtil.checkNull(rs.getString("case_3_note")));
		SearchMap.put("case_to_note_1", StringUtil.checkNull(rs.getString("case_to_note_1")));
		SearchMap.put("case_to_note_2", StringUtil.checkNull(rs.getString("case_to_note_2")));
		SearchMap.put("case_to_note_3", StringUtil.checkNull(rs.getString("case_to_note_3")));
		SearchMap.put("step_no", StringUtil.checkNull(rs.getString("step_no")));
		SearchMap.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		SearchMap.put("FK_cost_request", StringUtil.checkNull(rs.getString("FK_cost_request")));
		SearchMap.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		SearchMap.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		SearchMap.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		SearchMap.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		SearchMap.put("team", StringUtil.checkNull(rs.getString("team")));
		SearchMap.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		SearchMap.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		SearchMap.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		SearchMap.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		SearchMap.put("customer_F", StringUtil.checkNull(rs.getString("customer_F")));
		SearchMap.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		SearchMap.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		SearchMap.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		SearchMap.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		SearchMap.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		SearchMap.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		SearchMap.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		SearchMap.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		SearchMap.put("report_dest", StringUtil.checkNull(rs.getString("report_dest")));
		SearchMap.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		SearchMap.put("room_earn", StringUtil.checkNull(rs.getString("room_earn")));
		SearchMap.put("pro_usage", StringUtil.checkNull(rs.getString("pro_usage")));
		SearchMap.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		SearchMap.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		SearchMap.put("lme_cu", StringUtil.checkNull(rs.getString("lme_cu")));
		SearchMap.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		SearchMap.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		SearchMap.put("usage", StringUtil.checkNull(rs.getString("usage")));
		SearchMap.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		SearchMap.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		SearchMap.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		SearchMap.put("actual_cost_1", StringUtil.checkNull(rs.getString("actual_cost_1")));
		SearchMap.put("earn_rate_1", StringUtil.checkNull(rs.getString("earn_rate_1")));
		SearchMap.put("actual_cost_2", StringUtil.checkNull(rs.getString("actual_cost_2")));
		SearchMap.put("earn_rate_2", StringUtil.checkNull(rs.getString("earn_rate_2")));
		SearchMap.put("actual_cost_3", StringUtil.checkNull(rs.getString("actual_cost_3")));
		SearchMap.put("earn_rate_3", StringUtil.checkNull(rs.getString("earn_rate_3")));
		SearchMap.put("actual_cost_sum_1", StringUtil.checkNull(rs.getString("actual_cost_sum_1")));
		SearchMap.put("earn_rate_sum_1", StringUtil.checkNull(rs.getString("earn_rate_sum_1")));
		SearchMap.put("actual_cost_sum_2", StringUtil.checkNull(rs.getString("actual_cost_sum_2")));
		SearchMap.put("earn_rate_sum_2", StringUtil.checkNull(rs.getString("earn_rate_sum_2")));
		SearchMap.put("actual_cost_sum_3", StringUtil.checkNull(rs.getString("actual_cost_sum_3")));
		SearchMap.put("earn_rate_sum_3", StringUtil.checkNull(rs.getString("earn_rate_sum_3")));
		SearchMap.put("mold_count", StringUtil.checkNull(rs.getString("mold_count")));
		SearchMap.put("press_count", StringUtil.checkNull(rs.getString("press_count")));
		SearchMap.put("line_count", StringUtil.checkNull(rs.getString("line_count")));
		SearchMap.put("pack_count", StringUtil.checkNull(rs.getString("pack_count")));
		SearchMap.put("repack_count", StringUtil.checkNull(rs.getString("repack_count")));
		SearchMap.put("mold_total_1", StringUtil.checkNull(rs.getString("mold_total_1")));
		SearchMap.put("press_total_1", StringUtil.checkNull(rs.getString("press_total_1")));
		SearchMap.put("line_total_1", StringUtil.checkNull(rs.getString("line_total_1")));
		SearchMap.put("pack_total_1", StringUtil.checkNull(rs.getString("pack_total_1")));
		SearchMap.put("mold_total_2", StringUtil.checkNull(rs.getString("mold_total_2")));
		SearchMap.put("press_total_2", StringUtil.checkNull(rs.getString("press_total_2")));
		SearchMap.put("line_total_2", StringUtil.checkNull(rs.getString("line_total_2")));
		SearchMap.put("pack_total_2", StringUtil.checkNull(rs.getString("pack_total_2")));
		SearchMap.put("mold_total_3", StringUtil.checkNull(rs.getString("mold_total_3")));
		SearchMap.put("press_total_3", StringUtil.checkNull(rs.getString("press_total_3")));
		SearchMap.put("line_total_3", StringUtil.checkNull(rs.getString("line_total_3")));
		SearchMap.put("pack_total_3", StringUtil.checkNull(rs.getString("pack_total_3")));
		SearchMap.put("repack_total_1", StringUtil.checkNull(rs.getString("repack_total_1")));
		SearchMap.put("repack_total_2", StringUtil.checkNull(rs.getString("repack_total_2")));
		SearchMap.put("repack_total_3", StringUtil.checkNull(rs.getString("repack_total_3")));
		SearchMap.put("m_depr_stan", StringUtil.checkNull(rs.getString("m_depr_stan")));
		SearchMap.put("p_depr_stan", StringUtil.checkNull(rs.getString("p_depr_stan")));
		SearchMap.put("L_depr_stan", StringUtil.checkNull(rs.getString("L_depr_stan")));
		SearchMap.put("pack_depr_stan", StringUtil.checkNull(rs.getString("pack_depr_stan")));
		SearchMap.put("repack_depr_stan", StringUtil.checkNull(rs.getString("repack_depr_stan")));
		SearchMap.put("total_sales_1", StringUtil.checkNull(rs.getString("total_sales_1")));
		SearchMap.put("total_sales_2", StringUtil.checkNull(rs.getString("total_sales_2")));
		SearchMap.put("total_sales_3", StringUtil.checkNull(rs.getString("total_sales_3")));
		SearchMap.put("total_sales_4", StringUtil.checkNull(rs.getString("total_sales_4")));
		SearchMap.put("total_sales_5", StringUtil.checkNull(rs.getString("total_sales_5")));
		SearchMap.put("total_sales_6", StringUtil.checkNull(rs.getString("total_sales_6")));
		SearchMap.put("total_sales_7", StringUtil.checkNull(rs.getString("total_sales_7")));
		SearchMap.put("total_sales_8", StringUtil.checkNull(rs.getString("total_sales_8")));
		SearchMap.put("profit_1", StringUtil.checkNull(rs.getString("profit_1")));
		SearchMap.put("profit_2", StringUtil.checkNull(rs.getString("profit_2")));
		SearchMap.put("profit_3", StringUtil.checkNull(rs.getString("profit_3")));
		SearchMap.put("profit_4", StringUtil.checkNull(rs.getString("profit_4")));
		SearchMap.put("profit_5", StringUtil.checkNull(rs.getString("profit_5")));
		SearchMap.put("profit_6", StringUtil.checkNull(rs.getString("profit_6")));
		SearchMap.put("profit_7", StringUtil.checkNull(rs.getString("profit_7")));
		SearchMap.put("profit_8", StringUtil.checkNull(rs.getString("profit_8")));
		SearchMap.put("per_profit_1", StringUtil.checkNull(rs.getString("per_profit_1")));
		SearchMap.put("per_profit_2", StringUtil.checkNull(rs.getString("per_profit_2")));
		SearchMap.put("per_profit_3", StringUtil.checkNull(rs.getString("per_profit_3")));
		SearchMap.put("per_profit_4", StringUtil.checkNull(rs.getString("per_profit_4")));
		SearchMap.put("per_profit_5", StringUtil.checkNull(rs.getString("per_profit_5")));
		SearchMap.put("per_profit_6", StringUtil.checkNull(rs.getString("per_profit_6")));
		SearchMap.put("per_profit_7", StringUtil.checkNull(rs.getString("per_profit_7")));
		SearchMap.put("per_profit_8", StringUtil.checkNull(rs.getString("per_profit_8")));
		SearchMap.put("su_stan_day", StringUtil.checkNull(rs.getString("su_stan_day")));
		SearchMap.put("note_1", StringUtil.checkNull(rs.getString("note_1")));
		SearchMap.put("note_2", StringUtil.checkNull(rs.getString("note_2")));
		SearchMap.put("note_3", StringUtil.checkNull(rs.getString("note_3")));
		SearchMap.put("note_4", StringUtil.checkNull(rs.getString("note_4")));
		SearchMap.put("note_5", StringUtil.checkNull(rs.getString("note_5")));
		SearchMap.put("note_5_1", StringUtil.checkNull(rs.getString("note_5_1")));
		SearchMap.put("tocost_year", StringUtil.checkNull(rs.getString("tocost_year")));
		// 4안
		SearchMap.put("FK_cost_work_4", StringUtil.checkNull(rs.getString("FK_cost_work_4")));
		SearchMap.put("case_4_note", StringUtil.checkNull(rs.getString("case_4_note")));
		SearchMap.put("case_to_note_4", StringUtil.checkNull(rs.getString("case_to_note_4")));
		SearchMap.put("actual_cost_4", StringUtil.checkNull(rs.getString("actual_cost_4")));
		SearchMap.put("earn_rate_4", StringUtil.checkNull(rs.getString("earn_rate_4")));
		SearchMap.put("actual_cost_sum_4", StringUtil.checkNull(rs.getString("actual_cost_sum_4")));
		SearchMap.put("earn_rate_sum_4", StringUtil.checkNull(rs.getString("earn_rate_sum_4")));
		SearchMap.put("mold_total_4", StringUtil.checkNull(rs.getString("mold_total_4")));
		SearchMap.put("press_total_4", StringUtil.checkNull(rs.getString("press_total_4")));
		SearchMap.put("line_total_4", StringUtil.checkNull(rs.getString("line_total_4")));
		SearchMap.put("pack_total_4", StringUtil.checkNull(rs.getString("pack_total_4")));
		SearchMap.put("repack_total_4", StringUtil.checkNull(rs.getString("repack_total_4")));

		SearchMap.put("file_1", StringUtil.checkNull(rs.getString("file_1")));
		SearchMap.put("file_2", StringUtil.checkNull(rs.getString("file_2")));
		SearchMap.put("file_3", StringUtil.checkNull(rs.getString("file_3")));
		SearchMap.put("file_4", StringUtil.checkNull(rs.getString("file_4")));
		SearchMap.put("file_5", StringUtil.checkNull(rs.getString("file_5")));
		SearchMap.put("file_6", StringUtil.checkNull(rs.getString("file_6")));
		SearchMap.put("file_2_name", StringUtil.checkNull(rs.getString("file_2_name")));
		SearchMap.put("file_3_name", StringUtil.checkNull(rs.getString("file_3_name")));
		SearchMap.put("file_4_name", StringUtil.checkNull(rs.getString("file_4_name")));
		SearchMap.put("file_5_name", StringUtil.checkNull(rs.getString("file_5_name")));
		SearchMap.put("file_6_name", StringUtil.checkNull(rs.getString("file_6_name")));
		SearchMap.put("invest_cnt", StringUtil.checkNull(rs.getString("invest_cnt")));
		SearchMap.put("note_6", StringUtil.checkNull(rs.getString("note_6")));
		SearchMap.put("select_cost", StringUtil.checkNull(rs.getString("select_cost")));
		SearchMap.put("input_gb", StringUtil.checkNull(rs.getString("input_gb")));

		SearchList.add(SearchMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : getPkPid 설명 :productInfo의 pk_pid 구하기
     * 
     * @param
     * @return String complet
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 17.
     */
    public String getKetCostList(String pk_cw) throws Exception {
	String complet = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT ket_cost FROM cost_work WHERE pk_cw = ?");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cw);

	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		complet = rs.getString("ket_cost");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return complet;
    }

    /**
     * 함수명 : getreportCostWorkPk 설명 :보고서의 최초작성시 costwork의 pk찾기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 5.
     */
    public String getreportCostWorkPk(String pk_cr_group) throws Exception {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	String pk_cw = "";
	sb.append("select pk_cw from cost_work											\n");
	sb.append(" where pk_cr_group =? 											\n");

	/*
	 * sb.append("where fk_pid in (															\n"); sb.append("                    		select pk_pid from cost_productinfo		\n");
	 * sb.append("                     		 where pk_cr_group =? )						\n");
	 */

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		pk_cw += StringUtil.checkNull(rs.getString("pk_cw")) + ",";
	    }
	    // System.out.println("pk_cw==>"+pk_cw.substring(0, pk_cw.length()-1));
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return pk_cw;
    }

    /**
     * 함수명 : getCase_type_user 설명 ::PLM interface테이블 UPDATE 시 case_type_user 찾기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 9. 5.
     */
    public String getCase_type_user(String pk_cr_group, String rev_no) throws Exception {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	String case_type_user = "";
	sb.append("select case_type_user  from cost_work											\n");
	sb.append(" where pk_cr_group =? 	and rev_no = ?										\n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		case_type_user = rs.getString("case_type_user");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return case_type_user;
    }

    /**
     * 함수명 : urlCostIUpdate 설명 :PLM interface테이블 URL 업데이트
     * 
     * @param String
     *            pk_cr_group, String rev_no
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2013. 4. 15.
     */
    public void urlCostIUpdate(String link_url, String pjt_no) throws Exception {
	int complet = 0;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("UPDATE COST_I  SET LINKURL = ? WHERE PROJECTCODE = ?  ");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, link_url);
	    pstmt.setString(2, pjt_no);
	    complet = pstmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	// return complet;
    }

    /**
     * 함수명 : getReportApprListr 설명 ::전자결재 목록 조회
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2013. 6. 19.
     */
    public ArrayList getReportApprList(String gubun, String position, String ProductName, String ProjectNo, String kyul_date1,
	    String kyul_date2, String kyul_line) throws Exception {
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	String DEV_STEP = "";
	String PK_CR_GROUP = "";
	String PART_NAME = "";
	String GR_NAME_2 = "";
	String LEADER_DAY_2 = "";
	String LEADER_NAME_2 = "";
	String JB_NAME = "";
	String JB_DAY = "";
	String P_LEADER_DAY = "";
	String P_LEADER_NAME = "";
	String R_OWNER_DAY = "";
	String R_OWNER_NAME = "";

	sb.append("SELECT  to_char(a.request_day,'YYYY-MM-DD') AS REQUEST_DAY,DEV_STEP, PK_CR_GROUP, B.PJT_NAME AS PART_NAME, GR_DAY_2, GR_NAME_2,   ");
	sb.append("TO_CHAR(LEADER_DAY_2,'YYYY-MM-DD') AS LEADER_DAY_2, LEADER_NAME_2, JB_NAME, TO_CHAR(JB_DAY,'YYYY-MM-DD') AS JB_DAY,  TO_CHAR(P_LEADER_DAY,'YYYY-MM-DD') AS P_LEADER_DAY,  P_LEADER_NAME, TO_CHAR(R_OWNER_DAY,'YYYY-MM-DD') AS R_OWNER_DAY, R_OWNER_NAME,  ");
	sb.append("CASE_TYPE_USER,TABLE_ROW,PK_CRP,REV_NO,REV_PK                                    ");
	sb.append("FROM COST_REQUEST A, COST_PRODUCTINFO B, WFINFO C, COST_REPORT D            ");
	sb.append("WHERE A.FK_PID      = B.PK_PID                                                          ");
	sb.append("AND A.PK_CR       = C.FK_COST                                                        ");
	sb.append("AND B.REPORT_PK = D.PK_CRP                                                                  ");
	sb.append("and B.group_no = 'T001'  ");
	sb.append("AND B.DATA_TYPE = 'main'  ");
	if (!StringUtil.checkNull(ProductName).equals("")) {
	    sb.append("    AND PART_NAME = '" + ProductName + "'");
	}
	if (!StringUtil.checkNull(ProjectNo).equals("")) {
	    sb.append("    AND PK_CR_GROUP = '" + ProjectNo + "'");
	}
	if (StringUtil.checkNull(gubun).equals("kyul_daesang")) {
	    if (StringUtil.checkNull(position).equals("연구원장")) {
		sb.append("    AND STEP_NO = '5.1'");
		sb.append("    AND TO_CHAR(GR_DAY_2,'YYYYMMDD') > '20130702' ");
		if (!StringUtil.checkNull(kyul_line).equals("")) {
		    sb.append("    AND GR_NAME_2 =  '" + kyul_line + "'");
		}
		if (!StringUtil.checkNull(kyul_date1).equals("")) {
		    sb.append("    AND TO_DATE(GR_DAY_2) between  to_date('" + kyul_date1 + "') and to_date('" + kyul_date2 + "')");
		}
	    }
	    /*
	     * if(StringUtil.checkNull(position).equals("부문장")){ sb.append("    AND STEP_NO = '5.1'" );
	     * sb.append("    AND TO_CHAR(JB_DAY,'YYYYMMDD') > '20130702' " ); if(!StringUtil.checkNull(kyul_line).equals("")){
	     * sb.append("    AND JB_NAME =  '"+kyul_line+"'" ); } if(!StringUtil.checkNull(kyul_date1).equals("")){
	     * sb.append("    AND TO_DATE(JB_DAY) between  to_date('"+kyul_date1+"') and to_date('"+kyul_date2+"')" ); } }
	     */
	    if (StringUtil.checkNull(position).equals("사장")) {
		sb.append("    AND STEP_NO = '5.2'");
		sb.append("    AND TO_CHAR(P_LEADER_DAY,'YYYYMMDD') > '20130702' ");
		if (!StringUtil.checkNull(kyul_line).equals("")) {
		    sb.append("    AND P_LEADER_NAME =  '" + kyul_line + "'");
		}
		if (!StringUtil.checkNull(kyul_date1).equals("")) {
		    sb.append("    AND TO_DATE(P_LEADER_DAY) between  to_date('" + kyul_date1 + "') and to_date('" + kyul_date2 + "')");
		}
	    }
	} else if (StringUtil.checkNull(gubun).equals("kyul_ing")) {
	    sb.append("    AND TO_CHAR(P_LEADER_DAY,'YYYYMMDD') > '20130702' ");
	    /*
	     * if(StringUtil.checkNull(position).equals("연구원장")){ sb.append("    AND STEP_NO IN ('5.1','5.2') " );
	     * if(!StringUtil.checkNull(kyul_line).equals("")){ if(kyul_line.equals("김동식")){ sb.append("    AND JB_NAME =  '"+kyul_line+"'"
	     * ); if(!StringUtil.checkNull(kyul_date1).equals("")){
	     * sb.append("    AND TO_DATE(JB_dAY) between  to_date('"+kyul_date1+"') and to_date('"+kyul_date2+"')" ); } }
	     * if(kyul_line.equals("이성범")){ sb.append("    AND P_LEADER_NAME =  '"+kyul_line+"'" );
	     * if(!StringUtil.checkNull(kyul_date1).equals("")){
	     * sb.append("    AND TO_DATE(P_LEADER_DAY) between  to_date('"+kyul_date1+"') and to_date('"+kyul_date2+"')" ); } } }else
	     * if(!StringUtil.checkNull(kyul_date1).equals("")){
	     * sb.append("    AND ((TO_DATE(JB_dAY) between  to_date('"+kyul_date1+"') and to_date('"+kyul_date2+"')) or" );
	     * sb.append("    (TO_DATE(P_LEADER_DAY) between  to_date('"+kyul_date1+"') and to_date('"+kyul_date2+"')))" ); } }
	     */
	    if (StringUtil.checkNull(position).equals("연구원장")) {
		sb.append("    AND STEP_NO = '5.2'");
		if (!StringUtil.checkNull(kyul_line).equals("")) {
		    sb.append("    AND P_LEADER_NAME =  '" + kyul_line + "'");
		}
		if (!StringUtil.checkNull(kyul_date1).equals("")) {
		    sb.append("    AND TO_DATE(P_LEADER_DAY) between  to_date('" + kyul_date1 + "') and to_date('" + kyul_date2 + "')");
		}
	    }
	    if (StringUtil.checkNull(position).equals("사장")) {
		sb.append("    AND STEP_NO = '5.2'");
		if (!StringUtil.checkNull(kyul_line).equals("")) {
		    sb.append("    AND P_LEADER_NAME =  '" + kyul_line + "'");
		}
		if (!StringUtil.checkNull(kyul_date1).equals("")) {
		    sb.append("    AND TO_DATE(P_LEADER_DAY) between  to_date('" + kyul_date1 + "') and to_date('" + kyul_date2 + "')");
		}
	    }
	} else if (StringUtil.checkNull(gubun).equals("kyul_finish")) {
	    sb.append("    AND STEP_NO = '6'");
	    if (!StringUtil.checkNull(kyul_date1).equals("")) {
		sb.append("    AND TO_DATE(R_OWNER_DAY) between  to_date('" + kyul_date1 + "') and to_date('" + kyul_date2 + "')");
	    }
	    sb.append("    AND TO_CHAR(R_OWNER_DAY,'YYYYMMDD') > '20130702' ");
	}
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    // pstmt.setString(1, pk_cr_group);
	    // pstmt.setString(2, gubun);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		SearchMap = new Hashtable<String, String>();
		SearchMap.put("ProjectNo", StringUtil.checkNull(rs.getString("PK_CR_GROUP")));
		SearchMap.put("ProductName", StringUtil.checkNull(rs.getString("PART_NAME")));
		SearchMap.put("DEV_STEP", StringUtil.checkNull(rs.getString("DEV_STEP")));
		SearchMap.put("REQUEST_DAY", StringUtil.checkNull(rs.getString("REQUEST_DAY")));
		SearchMap.put("CASE_TYPE_USER", StringUtil.checkNull(rs.getString("CASE_TYPE_USER")));
		SearchMap.put("TABLE_ROW", StringUtil.checkNull(rs.getString("TABLE_ROW")));
		SearchMap.put("PK_CRP", StringUtil.checkNull(rs.getString("PK_CRP")));
		SearchMap.put("REV_NO", StringUtil.checkNull(rs.getString("REV_NO")));
		SearchMap.put("REV_PK", StringUtil.checkNull(rs.getString("REV_PK")));
		if (StringUtil.checkNull(gubun).equals("kyul_daesang")) {
		    if (StringUtil.checkNull(position).equals("연구원장")) {
			SearchMap.put("KYUL_NAME", StringUtil.checkNull(rs.getString("LEADER_NAME_2")));
			SearchMap.put("KYUL_DAY", StringUtil.checkNull(rs.getString("LEADER_DAY_2")));
			System.out.println("?8888'");
		    }
		    if (StringUtil.checkNull(position).equals("부문장")) {
			SearchMap.put("KYUL_NAME", StringUtil.checkNull(rs.getString("JB_NAME")));
			SearchMap.put("KYUL_DAY", StringUtil.checkNull(rs.getString("JB_DAY")));
		    }
		    if (StringUtil.checkNull(position).equals("사장")) {
			SearchMap.put("KYUL_NAME", StringUtil.checkNull(rs.getString("P_LEADER_NAME")));
			SearchMap.put("KYUL_DAY", StringUtil.checkNull(rs.getString("P_LEADER_DAY")));
		    }
		} else if (StringUtil.checkNull(gubun).equals("kyul_ing")) {
		    SearchMap.put("KYUL_NAME", StringUtil.checkNull(rs.getString("P_LEADER_NAME")));
		    SearchMap.put("KYUL_DAY", StringUtil.checkNull(rs.getString("P_LEADER_DAY")));
		} else {
		    SearchMap.put("KYUL_NAME", StringUtil.checkNull(rs.getString("R_OWNER_NAME")));
		    SearchMap.put("KYUL_DAY", StringUtil.checkNull(rs.getString("R_OWNER_DAY")));
		}
		SearchList.add(SearchMap);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return SearchList;
    }

    /**
     * 함수명 : setNote4Update 설명 :보고서 경영층지시사항 update
     * 
     * @param String
     *            report_pk, String note_4
     * @return int complet
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2013. 7. 15.
     */
    public int setNote4Update(String report_pk, String note_4) throws Exception {
	int complet = 0;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("UPDATE cost_report  SET  note_6 = ? WHERE crp_group = ?  ");

	// System.out.println(sb.toString() +" == "+ pk_cw);
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, "[경영층 지시사항] " + note_4);
	    pstmt.setString(2, report_pk);
	    complet = pstmt.executeUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}

	return complet;
    }

    /**
     * 함수명 : getSelectWork 설명 : 보고서 작성시 선택안에 따른 매출액,영업이익,영업이익율 계산값 가져오기
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2013. 10. 18.
     */
    public ArrayList getSelectWork(String select_cost, String report_pk) throws Exception {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> SearchMap = null;
	System.out.println("report_pk : " + report_pk);
	System.out.println("select_cost : " + select_cost);
	sb.append(" SELECT ROUND(SUM(DECODE(TOTAL_SALES_1,0,'',TOTAL_SALES_1)),2) AS TOTAL_SALES_1,                                                                                                                                                                                                                                           \n");
	sb.append("        ROUND(SUM(PROFIT_1),2) AS PROFIT_1,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_1)/NULLIF(SUM(TOTAL_SALES_1),0),4) AS PER_PROFIT_1,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(DECODE(TOTAL_SALES_2,0,'',TOTAL_SALES_2)),2)  AS TOTAL_SALES_2,                                                                                                                                                                                                                                          \n");
	sb.append("        ROUND(SUM(PROFIT_2),2) AS PROFIT_2,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_2)/NULLIF(SUM(TOTAL_SALES_2),0),4) AS PER_PROFIT_2,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(DECODE(TOTAL_SALES_3,0,'',TOTAL_SALES_3)),2) AS TOTAL_SALES_3,                                                                                                                                                                                                                                           \n");
	sb.append("        ROUND(SUM(PROFIT_3),2) AS PROFIT_3,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_3)/NULLIF(SUM(TOTAL_SALES_3),0),4) AS PER_PROFIT_3,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(DECODE(TOTAL_SALES_4,0,'',TOTAL_SALES_4)),2) AS TOTAL_SALES_4,                                                                                                                                                                                                                                           \n");
	sb.append("        ROUND(SUM(PROFIT_4),2) AS PROFIT_4,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_4)/NULLIF(SUM(TOTAL_SALES_4),0),4) AS PER_PROFIT_4,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(DECODE(TOTAL_SALES_5,0,'',TOTAL_SALES_5)),2) AS TOTAL_SALES_5,                                                                                                                                                                                                                                           \n");
	sb.append("        ROUND(SUM(PROFIT_5),2)AS PROFIT_5,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_5)/NULLIF(SUM(TOTAL_SALES_5),0),4) AS PER_PROFIT_5,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(DECODE(TOTAL_SALES_6,0,'',TOTAL_SALES_6)),2) AS TOTAL_SALES_6,                                                                                                                                                                                                                                           \n");
	sb.append("        ROUND(SUM(PROFIT_6),2) AS PROFIT_6,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_6)/NULLIF(SUM(TOTAL_SALES_6),0),4) AS PER_PROFIT_6,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(DECODE(TOTAL_SALES_7,0,'',TOTAL_SALES_7)),2) AS TOTAL_SALES_7,                                                                                                                                                                                                                                           \n");
	sb.append("        ROUND(SUM(PROFIT_7),2) AS PROFIT_7,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_7)/NULLIF(SUM(TOTAL_SALES_7),0),4) AS PER_PROFIT_7,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(DECODE(TOTAL_SALES_8,0,'',TOTAL_SALES_8)),2) AS TOTAL_SALES_8,                                                                                                                                                                                                                                           \n");
	sb.append("        ROUND(SUM(PROFIT_8),2) AS PROFIT_8,                                                                                                                                                                                                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_8)/NULLIF(SUM(TOTAL_SALES_8),0),4) AS PER_PROFIT_8,                                                                                                                                                                                                                                               \n");
	sb.append("        ROUND(SUM(ROUND(TOTAL_SALES_1,2)+ROUND(TOTAL_SALES_2,2)+ROUND(TOTAL_SALES_3,2)+ROUND(TOTAL_SALES_4,2)+ROUND(TOTAL_SALES_5,2)+ROUND(TOTAL_SALES_6,2)+ROUND(TOTAL_SALES_7,2)+ROUND(TOTAL_SALES_8,2)),0) AS SUM_SALES,                                                                                                \n");
	sb.append("        ROUND(SUM(PROFIT_1),2) +ROUND(SUM(PROFIT_2),2) +ROUND(SUM(PROFIT_3),2) +ROUND(SUM(PROFIT_4),2) +ROUND(SUM(PROFIT_5),2) +ROUND(SUM(PROFIT_6),2) +ROUND(SUM(PROFIT_7),2) +ROUND(SUM(PROFIT_8),2)  AS SUM_PROFIT,                                                                                                     \n");
	sb.append("        ROUND(((ROUND(SUM(PROFIT_1),2)+ROUND(SUM(PROFIT_2),2)+ROUND(SUM(PROFIT_3),2)+ROUND(SUM(PROFIT_4),2)+ROUND(SUM(PROFIT_5),2)+ROUND(SUM(PROFIT_6),2)+ROUND(SUM(PROFIT_7),2)+ROUND(SUM(PROFIT_8),2))/NULLIF((ROUND(SUM(TOTAL_SALES_1),2)+ROUND(SUM(TOTAL_SALES_2),2)+ROUND(SUM(TOTAL_SALES_3),2)+ROUND(SUM(TOTAL_SALES_4),2)+ROUND(SUM(TOTAL_SALES_5),2)+ROUND(SUM(TOTAL_SALES_6),2)+ROUND(SUM(TOTAL_SALES_7),2)+ROUND(SUM(TOTAL_SALES_8),2)),0)*100),1) AS SUM_PER_P \n");
	sb.append("   FROM (                                                                                                                                                                                                                                                                                                                  \n");
	sb.append("        SELECT (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_1,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_1,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_2,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_2,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_3,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_3,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_4,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_4,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_5,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_5,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_6,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_6,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_7,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_7,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)*(NVL(SU_YEAR_8,0)*1000)*NVL(USAGE,0))/1000000 AS TOTAL_SALES_8,                                                                                                                                                                                                                                                 \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_1,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_1,                                                                                                                                                                                                                                     \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_2,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_2,                                                                                                                                                                                                                                     \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_3,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_3,                                                                                                                                                                                                                                     \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_4,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_4,                                                                                                                                                                                                                                     \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_5,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_5,                                                                                                                                                                                                                                     \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_6,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_6,                                                                                                                                                                                                                                     \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_7,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_7,                                                                                                                                                                                                                                     \n");
	sb.append("               (NVL(FN_GET_KET_COST(KET_COST,TARGET_COST,ACTUAL_COST),0)-NVL(ACTUAL_COST,0)) * (NVL(SU_YEAR_8,0) *1000) * NVL(USAGE,0)/1000000 AS PROFIT_8                                                                                                                                                                                                                                      \n");
	sb.append(" FROM COST_WORK                \n");
	if (select_cost.equals("1")) {
	    sb.append("         WHERE PK_CW IN (SELECT FK_COST_WORK_1 FROM COST_REPORT WHERE CRP_GROUP = ?))			\n");
	} else if (select_cost.equals("2")) {
	    sb.append("         WHERE PK_CW IN (SELECT FK_COST_WORK_2 FROM COST_REPORT WHERE CRP_GROUP = ?))			\n");
	} else if (select_cost.equals("3")) {
	    sb.append("         WHERE PK_CW IN (SELECT FK_COST_WORK_3 FROM COST_REPORT WHERE CRP_GROUP = ?))			\n");
	}

	if (!"".equals(select_cost)) {
	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, report_pk);
		rs = pstmt.executeQuery();

		while (rs.next()) {

		    SearchMap = new Hashtable<String, String>();
		    SearchMap.put("total_sales_1",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_1"))))));
		    SearchMap.put("profit_1", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_1"))))));
		    SearchMap.put("per_profit_1",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_1"))))));
		    SearchMap.put("total_sales_2",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_2"))))));
		    SearchMap.put("profit_2", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_2"))))));
		    SearchMap.put("per_profit_2",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_2"))))));
		    SearchMap.put("total_sales_3",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_3"))))));
		    SearchMap.put("profit_3", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_3"))))));
		    SearchMap.put("per_profit_3",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_3"))))));
		    SearchMap.put("total_sales_4",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_4"))))));
		    SearchMap.put("profit_4", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_4"))))));
		    SearchMap.put("per_profit_4",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_4"))))));
		    SearchMap.put("total_sales_5",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_5"))))));
		    SearchMap.put("profit_5", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_5"))))));
		    SearchMap.put("per_profit_5",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_5"))))));
		    SearchMap.put("total_sales_6",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_6"))))));
		    SearchMap.put("profit_6", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_6"))))));
		    SearchMap.put("per_profit_6",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_6"))))));
		    SearchMap.put("total_sales_7",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_7"))))));
		    SearchMap.put("profit_7", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_7"))))));
		    SearchMap.put("per_profit_7",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_7"))))));
		    SearchMap.put("total_sales_8",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("total_sales_8"))))));
		    SearchMap.put("profit_8", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("profit_8"))))));
		    SearchMap.put("per_profit_8",
			    Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("per_profit_8"))))));
		    SearchMap.put("sum_sales", StringUtil.checkNull(rs.getString("sum_sales")));
		    SearchMap
			    .put("sum_profit", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("sum_profit"))))));
		    SearchMap.put("sum_per_p", Double.toString((Double.parseDouble(StringUtil.checkNullZero(rs.getString("sum_per_p"))))));
		}
		SearchList.add(SearchMap);

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (rs != null) {
		    rs.close();
		}
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return SearchList;
    }

    /**
     * 함수명 : GetMaxRev 설명 :productInfo의 pk_pid 구하기
     * 
     * @param
     * @return String rev_no
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2014. 9. 30.
     */
    public String GetMaxRev(String pjt_no) throws Exception {
	String rev_no = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select decode(count(*),0,0,nvl(max(rev_no),0)+1) as rev_no from cost_productinfo where pk_cr_group = ? ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pjt_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		rev_no = rs.getString("rev_no");
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	}
	return rev_no;
    }

    /**
     * 함수명 : GetMaxRev 설명 :wfino pk 구하기
     * 
     * @param
     * @return String pk_wid
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2014. 9. 30.
     */
    public String GetwfPk() throws Exception {
	String pk_wid = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append("select wfinfo_pk_wid.nextval as pk_wid from dual ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		pk_wid = rs.getString("pk_wid");
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	}
	return pk_wid;
    }

    /**
     * 함수명 : InsertRerpotTemp 설명 : 보고서 수기 작성
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2014. 09. 29.
     */
    public String InsertRerpotTemp(ArrayList createDataList) throws Exception {

	Hashtable CreateMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;

	String report_pk = ""; // 보고서 pk
	String pk_pid = ""; // cost_productinfo pk
	String pk_cr = ""; // cost_request pk
	String pk_wid = ""; // wfinfo pk
	String pjt_no = "";
	String part_name = "";
	String group_no = "";
	String f_name = "";
	String rev_no = "";

	String team = "";
	String a_name = "";
	String customer_f = "";
	String pjt_name = "";
	String car_type = "";
	String app_part = "";
	String request_txt = "";

	String report_dest = "";

	String case_to_note_1 = "";
	String case_to_note_2 = "";
	String case_to_note_3 = "";

	String mold_count = "";
	String press_count = "";
	String line_count = "";
	String pack_count = "";
	String repack_count = "";

	String mold_total_1 = "";
	String mold_total_2 = "";
	String mold_total_3 = "";

	String press_total_1 = "";
	String press_total_2 = "";
	String press_total_3 = "";

	String line_total_1 = "";
	String line_total_2 = "";
	String line_total_3 = "";

	String pack_total_1 = "";
	String pack_total_2 = "";
	String pack_total_3 = "";

	String repack_total_1 = "";
	String repack_total_2 = "";
	String repack_total_3 = "";

	String m_depr_stan = "";
	String p_depr_stan = "";
	String l_depr_stan = "";
	String pack_depr_stan = "";
	String repack_depr_stan = "";

	String tocost_year = "";

	String su_year_1 = "";
	String su_year_2 = "";
	String su_year_3 = "";
	String su_year_4 = "";
	String su_year_5 = "";
	String su_year_6 = "";
	String su_year_7 = "";
	String su_year_8 = "";
	String su_stan_day = "";

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
	String pack_type = "";
	String pro_1 = "";
	String eff_value = "";
	String pro_usage = "";
	String ket_cost = "";
	String target_cost = "";
	String actual_cost_1 = "";
	String earn_rate_1 = "";
	String actual_cost_2 = "";
	String earn_rate_2 = "";
	String actual_cost_3 = "";
	String earn_rate_3 = "";
	String actual_cost_sum_1 = "";
	String actual_cost_sum_2 = "";
	String actual_cost_sum_3 = "";
	String earn_rate_sum_1 = "";
	String earn_rate_sum_2 = "";
	String earn_rate_sum_3 = "";
	String note_1 = "";
	String note_2 = "";
	String note_3 = "";
	String note_4 = "";
	String lme_cu = "";
	String u_ex_rate = "";
	String select_cost = "";
	String w_name = "";
	String dev_step = "";
	String table_row = "";
	String dr_step = "";
	String cmd = "";
	String division = "";
	String case_1_note = "";
	String case_2_note = "";
	String case_3_note = "";

	try {
	    for (int i = 0; i < createDataList.size(); i++) {
		CreateMap = (Hashtable) createDataList.get(i);
		pjt_no = (String) CreateMap.get("pjt_no");
		part_name = (String) CreateMap.get("part_name");
		group_no = (String) CreateMap.get("group_no");
		f_name = (String) CreateMap.get("f_name");
		report_pk = (String) CreateMap.get("pk_crp");
		rev_no = (String) CreateMap.get("rev_no");
		pk_wid = (String) CreateMap.get("pk_wid");

		team = (String) CreateMap.get("team");
		a_name = (String) CreateMap.get("a_name");
		customer_f = (String) CreateMap.get("customer_f");
		pjt_name = (String) CreateMap.get("pjt_name");
		car_type = (String) CreateMap.get("car_type");
		app_part = (String) CreateMap.get("app_part");
		request_txt = (String) CreateMap.get("request_txt");
		report_dest = (String) CreateMap.get("report_dest");

		case_to_note_1 = (String) CreateMap.get("case_to_note_1");
		case_to_note_2 = (String) CreateMap.get("case_to_note_2");
		case_to_note_3 = (String) CreateMap.get("case_to_note_3");

		mold_count = (String) CreateMap.get("mold_count");
		press_count = (String) CreateMap.get("press_count");
		line_count = (String) CreateMap.get("line_count");
		pack_count = (String) CreateMap.get("pack_count");
		repack_count = (String) CreateMap.get("repack_count");

		mold_total_1 = (String) CreateMap.get("mold_total_1");
		mold_total_2 = (String) CreateMap.get("mold_total_2");
		mold_total_3 = (String) CreateMap.get("mold_total_3");

		press_total_1 = (String) CreateMap.get("press_total_1");
		press_total_2 = (String) CreateMap.get("press_total_2");
		press_total_3 = (String) CreateMap.get("press_total_3");

		line_total_1 = (String) CreateMap.get("line_total_1");
		line_total_2 = (String) CreateMap.get("line_total_2");
		line_total_3 = (String) CreateMap.get("line_total_3");

		pack_total_1 = (String) CreateMap.get("pack_total_1");
		pack_total_2 = (String) CreateMap.get("pack_total_2");
		pack_total_3 = (String) CreateMap.get("pack_total_3");

		repack_total_1 = (String) CreateMap.get("repack_total_1");
		repack_total_2 = (String) CreateMap.get("repack_total_2");
		repack_total_3 = (String) CreateMap.get("repack_total_3");

		m_depr_stan = (String) CreateMap.get("m_depr_stan");
		p_depr_stan = (String) CreateMap.get("p_depr_stan");
		l_depr_stan = (String) CreateMap.get("l_depr_stan");
		pack_depr_stan = (String) CreateMap.get("pack_depr_stan");
		repack_depr_stan = (String) CreateMap.get("repack_depr_stan");

		tocost_year = (String) CreateMap.get("tocost_year");

		su_year_1 = (String) CreateMap.get("su_year_1");
		su_year_2 = (String) CreateMap.get("su_year_2");
		su_year_3 = (String) CreateMap.get("su_year_3");
		su_year_4 = (String) CreateMap.get("su_year_4");
		su_year_5 = (String) CreateMap.get("su_year_5");
		su_year_6 = (String) CreateMap.get("su_year_6");
		su_year_7 = (String) CreateMap.get("su_year_7");
		su_year_8 = (String) CreateMap.get("su_year_8");
		su_stan_day = (String) CreateMap.get("su_stan_day");

		total_sales_1 = (String) CreateMap.get("total_sales_1");
		total_sales_2 = (String) CreateMap.get("total_sales_2");
		total_sales_3 = (String) CreateMap.get("total_sales_3");
		total_sales_4 = (String) CreateMap.get("total_sales_4");
		total_sales_5 = (String) CreateMap.get("total_sales_5");
		total_sales_6 = (String) CreateMap.get("total_sales_6");
		total_sales_7 = (String) CreateMap.get("total_sales_7");
		total_sales_8 = (String) CreateMap.get("total_sales_8");

		profit_1 = (String) CreateMap.get("profit_1");
		profit_2 = (String) CreateMap.get("profit_2");
		profit_3 = (String) CreateMap.get("profit_3");
		profit_4 = (String) CreateMap.get("profit_4");
		profit_5 = (String) CreateMap.get("profit_5");
		profit_6 = (String) CreateMap.get("profit_6");
		profit_7 = (String) CreateMap.get("profit_7");
		profit_8 = (String) CreateMap.get("profit_8");

		per_profit_1 = (String) CreateMap.get("per_profit_1");
		per_profit_2 = (String) CreateMap.get("per_profit_2");
		per_profit_3 = (String) CreateMap.get("per_profit_3");
		per_profit_4 = (String) CreateMap.get("per_profit_4");
		per_profit_5 = (String) CreateMap.get("per_profit_5");
		per_profit_6 = (String) CreateMap.get("per_profit_6");
		per_profit_7 = (String) CreateMap.get("per_profit_7");
		per_profit_8 = (String) CreateMap.get("per_profit_8");
		pack_type = (String) CreateMap.get("pack_type");
		pro_1 = (String) CreateMap.get("pro_1");
		eff_value = (String) CreateMap.get("eff_value");
		pro_usage = (String) CreateMap.get("pro_usage");
		ket_cost = (String) CreateMap.get("ket_cost");
		target_cost = (String) CreateMap.get("target_cost");
		actual_cost_1 = (String) CreateMap.get("actual_cost_1");
		earn_rate_1 = (String) CreateMap.get("earn_rate_1");
		actual_cost_2 = (String) CreateMap.get("actual_cost_2");
		earn_rate_2 = (String) CreateMap.get("earn_rate_2");
		actual_cost_3 = (String) CreateMap.get("actual_cost_3");
		earn_rate_3 = (String) CreateMap.get("earn_rate_3");
		actual_cost_sum_1 = (String) CreateMap.get("actual_cost_sum_1");
		actual_cost_sum_2 = (String) CreateMap.get("actual_cost_sum_2");
		actual_cost_sum_3 = (String) CreateMap.get("actual_cost_sum_3");
		earn_rate_sum_1 = (String) CreateMap.get("earn_rate_sum_1");
		earn_rate_sum_2 = (String) CreateMap.get("earn_rate_sum_2");
		earn_rate_sum_3 = (String) CreateMap.get("earn_rate_sum_3");
		note_1 = (String) CreateMap.get("note_1");
		note_2 = (String) CreateMap.get("note_2");
		note_3 = (String) CreateMap.get("note_3");
		note_4 = (String) CreateMap.get("note_4");
		lme_cu = (String) CreateMap.get("lme_cu");
		u_ex_rate = (String) CreateMap.get("u_ex_rate");
		select_cost = (String) CreateMap.get("select_cost");
		w_name = (String) CreateMap.get("w_name");
		dev_step = (String) CreateMap.get("dev_step");
		table_row = (String) CreateMap.get("table_row");
		dr_step = (String) CreateMap.get("dr_step");
		division = (String) CreateMap.get("division");
		case_1_note = (String) CreateMap.get("case_1_note");
		case_2_note = (String) CreateMap.get("case_2_note");
		case_3_note = (String) CreateMap.get("case_3_note");

		sb.append(" SELECT productinfo_pk_pid.nextval as pk_pid, request_pk_cr.nextval as pk_cr");
		sb.append("   FROM DUAL");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    rs = pstmt.executeQuery();

		    while (rs.next()) {
			pk_pid = rs.getString("pk_pid");
			pk_cr = rs.getString("pk_cr");
		    }
		} catch (Exception e) {
		    throw e;
		} finally {
		    sb.delete(0, sb.length());
		    DBUtil.close(rs);
		    pstmt = null;
		}

		sb.append(" insert into COST_PRODUCTINFO (pk_pid,      pk_cr_group, rev_no,      group_no, case_count_1, case_count_2, table_row, ");
		sb.append("                               pjt_no,      pjt_name,    team,        f_name,   a_name,       dev_step,     dr_step,   ");
		sb.append("                               sub_step,    request_txt, pro_type,    make,     part_name,    part_type,    mix_group, ");
		sb.append("                               part_no,     net_1,       net_2,       net_3,    die_no,       part_note,    data_type, report_pk, customer_f, car_type, app_part, w_name");
		sb.append(" ) values (                                                                                ");
		sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		sb.append("           ?,?,?,?,?,?,?,?,?,?,?,?  )                                                                          ");
		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, pk_pid);
		    pstmt.setString(2, pjt_no);
		    pstmt.setString(3, rev_no);
		    pstmt.setString(4, group_no);
		    pstmt.setString(5, "");
		    pstmt.setString(6, "");
		    pstmt.setString(7, table_row);
		    pstmt.setString(8, pjt_no);
		    pstmt.setString(9, pjt_name);
		    pstmt.setString(10, team);
		    pstmt.setString(11, f_name);
		    pstmt.setString(12, a_name);
		    pstmt.setString(13, dev_step);
		    pstmt.setString(14, dr_step);
		    pstmt.setString(15, "");
		    pstmt.setString(16, request_txt);
		    pstmt.setString(17, "");
		    pstmt.setString(18, "");
		    pstmt.setString(19, part_name);
		    pstmt.setString(20, "");
		    pstmt.setString(21, "");
		    pstmt.setString(22, "");
		    pstmt.setString(23, "");
		    pstmt.setString(24, "");
		    pstmt.setString(25, "");
		    pstmt.setString(26, "");
		    pstmt.setString(27, "");
		    pstmt.setString(28, "main");
		    pstmt.setString(29, report_pk);
		    pstmt.setString(30, customer_f);
		    pstmt.setString(31, car_type);
		    pstmt.setString(32, app_part);
		    pstmt.setString(33, w_name);

		    pstmt.executeUpdate();

		} catch (Exception e) {
		    throw e;
		} finally {
		    sb.delete(0, sb.length());
		    pstmt = null;
		}

		sb.append(" insert into cost_request (usage,         meterial,   t_size,      w_size,       p_size,      plating,      m_maker,     m_mone,      unitcost,  ");
		sb.append("                           unitcost_txt,  grade_name, grade_color, order_con,    h_weight,    h_scrap,      t_weight,    recycle,     cavity,    ");
		sb.append("                           m_su,          mold_cost,  mold_c_type, make_type,    pro_1,       ton,          spm,         method_type, pro_level, ");
		sb.append("                           pro_level_txt, line_su,    sul_cost,    plating_type, type_2,      plating_cost, type_1,      t_mone,      type_cost, ");
		sb.append("                           t_order,       pack_type,  in_pack,     out_pack,     store,       dest,         dis_cost,    distri_cost, royalty,   ");
		sb.append("                           yazaki_ro,     etc_cost,   fk_pid,      pk_cr,        request_day, fk_wid     ");
		sb.append("        ) values (                                                                                                                               ");
		sb.append(" 	   ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                        		");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		sb.append("        ?, ?, ?, ? ,sysdate, ?                                  )																			");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, "");
		    pstmt.setString(2, "");
		    pstmt.setString(3, "");
		    pstmt.setString(4, "");
		    pstmt.setString(5, "");
		    pstmt.setString(6, "");
		    pstmt.setString(7, "");
		    pstmt.setString(8, "");
		    pstmt.setString(9, "");
		    pstmt.setString(10, "");
		    pstmt.setString(11, "");
		    pstmt.setString(12, "");
		    pstmt.setString(13, "");
		    pstmt.setString(14, "");
		    pstmt.setString(15, "");
		    pstmt.setString(16, "");
		    pstmt.setString(17, "");
		    pstmt.setString(18, "");
		    pstmt.setString(19, "");
		    pstmt.setString(20, "");
		    pstmt.setString(21, "");
		    pstmt.setString(22, "");
		    pstmt.setString(23, "");
		    pstmt.setString(24, "");
		    pstmt.setString(25, "");
		    pstmt.setString(26, "");
		    pstmt.setString(27, "");
		    pstmt.setString(28, "");
		    pstmt.setString(29, "");
		    pstmt.setString(30, "");
		    pstmt.setString(31, "");
		    pstmt.setString(32, "");
		    pstmt.setString(33, "");
		    pstmt.setString(34, "");
		    pstmt.setString(35, "");
		    pstmt.setString(36, "");
		    pstmt.setString(37, "");
		    pstmt.setString(38, "");
		    pstmt.setString(39, "");
		    pstmt.setString(40, "");
		    pstmt.setString(41, "");
		    pstmt.setString(42, "");
		    pstmt.setString(43, "");
		    pstmt.setString(44, "");
		    pstmt.setString(45, "");
		    pstmt.setString(46, "");
		    pstmt.setString(47, "");
		    pstmt.setString(48, pk_pid);
		    pstmt.setString(49, pk_cr);
		    pstmt.setString(50, pk_wid);

		    pstmt.executeUpdate();

		} catch (Exception e) {
		    throw e;
		} finally {
		    sb.delete(0, sb.length());
		    pstmt = null;
		}

		if ("T001".equals(group_no)) {
		    sb.append("insert into wfinfo (  						  			               ");
		    sb.append("       pk_wid, fk_cost, cost_flag, f_name, step_no, approval,     f_day  ) ");
		    /*
	             * if("report_create_temp".equals(cmd)){
	             * sb.append("values(?,      ?,       'R',       ?,      '2',     'finish',     sysdate) "); }else{
	             * sb.append("values(?,      ?,       'R',       ?,      '6',     'complete',     sysdate) "); }
	             */
		    sb.append("values(?,      ?,       'R',       ?,      '2',     'finish',     sysdate) ");
		    try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, pk_wid);
			pstmt.setString(2, pk_cr);
			pstmt.setString(3, f_name);

			pstmt.executeUpdate();
		    } catch (Exception e) {
			throw e;
		    } finally {
			sb.delete(0, sb.length());
			pstmt = null;
		    }
		}

		sb.append("INSERT INTO COST_REPORT (								                        ");
		sb.append("		  PK_CRP, FK_PID,  FK_WID,  CRP_GROUP,  GROUP_NO,  report_dest,		       ");
		sb.append("	case_to_note_1,  case_to_note_2,   case_to_note_3,     mold_count,      press_count,                ");
		sb.append("	line_count,          pack_count,         repack_count,        mold_total_1,    mold_total_2,        ");
		sb.append("	mold_total_3,       press_total_1,      press_total_2,        press_total_3,   line_total_1,        ");
		sb.append("	line_total_2,         line_total_3,        pack_total_1,         pack_total_2,    pack_total_3,     ");
		sb.append("	repack_total_1,     repack_total_2,   repack_total_3,       m_depr_stan,    p_depr_stan,            ");
		sb.append("	l_depr_stan,         pack_depr_stan,  repack_depr_stan,  tocost_year,     su_year_1,                ");
		sb.append("	su_year_2,           su_year_3,         su_year_4,           su_year_5,        su_year_6,           ");
		sb.append("	su_year_7,           su_year_8,         su_stan_day,        total_sales_1,    total_sales_2,                  ");
		sb.append("	total_sales_3,       total_sales_4,     total_sales_5,       total_sales_6,    total_sales_7,                 ");
		sb.append("	total_sales_8,       profit_1,             profit_2,              profit_3,            profit_4,              ");
		sb.append("	profit_5,              profit_6,             profit_7,              profit_8,             per_profit_1,       ");
		sb.append("	per_profit_2,        per_profit_3,       per_profit_4,         per_profit_5,       per_profit_6,              ");
		sb.append("	per_profit_7,        per_profit_8,       pack_type,            pro_1,               eff_value,                ");
		sb.append("	pro_usage,           ket_cost,           target_cost,          actual_cost_1,    earn_rate_1,                 ");
		sb.append("	actual_cost_2,      earn_rate_2,       actual_cost_3,       earn_rate_3,      note_1,                         ");
		sb.append("	note_2,                note_3,              note_4 ,                                                           ");
		sb.append("	actual_cost_sum_1,  actual_cost_sum_2,  actual_cost_sum_3,  earn_rate_sum_1,  earn_rate_sum_2,  earn_rate_sum_3,    ");
		sb.append("	lme_cu, u_ex_rate,  select_cost,        input_gb,           usage, division,  case_1_note,      case_2_note,     case_3_note  )  ");
		sb.append("	VALUES (                                                                        ");
		if ("T001".equals(group_no)) {
		    sb.append("	  ?,      ?,       ?,       ?,          ?,         ?,                                   ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ? / 100, ");
		    sb.append("? / 100, ? / 100, ? / 100, ? / 100, ? / 100, ");
		    sb.append("? / 100, ? / 100, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ? / 100, ");
		    sb.append("?, ? / 100, ?, ? / 100, ?,");
		    sb.append("?, ?, ?,");
		    sb.append("?, ?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?");
		} else {
		    sb.append("   cost_report_pk_crp.nextVal,  ?,  ?,  ?,  ?, ?,                           ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ? / 100, ");
		    sb.append("? / 100, ? / 100, ? / 100, ? / 100, ? / 100, ");
		    sb.append("? / 100, ? / 100, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ? / 100, ");
		    sb.append("?, ? / 100, ?, ? / 100, ?,");
		    sb.append("?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ?, ");
		    sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?");
		}
		sb.append("         )                                                                               ");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    if ("T001".equals(group_no)) {
			pstmt.setString(1, report_pk);
			pstmt.setString(2, pk_pid);
			pstmt.setString(3, pk_wid);
			pstmt.setString(4, report_pk);
			pstmt.setString(5, group_no);
			pstmt.setString(6, report_dest);

			pstmt.setString(7, case_to_note_1);
			pstmt.setString(8, case_to_note_2);
			pstmt.setString(9, case_to_note_3);
			pstmt.setString(10, mold_count);
			pstmt.setString(11, press_count);
			pstmt.setString(12, line_count);
			pstmt.setString(13, pack_count);
			pstmt.setString(14, repack_count);
			pstmt.setString(15, mold_total_1);
			pstmt.setString(16, mold_total_2);
			pstmt.setString(17, mold_total_3);
			pstmt.setString(18, press_total_1);
			pstmt.setString(19, press_total_2);
			pstmt.setString(20, press_total_3);
			pstmt.setString(21, line_total_1);
			pstmt.setString(22, line_total_2);
			pstmt.setString(23, line_total_3);
			pstmt.setString(24, pack_total_1);
			pstmt.setString(25, pack_total_2);
			pstmt.setString(26, pack_total_3);
			pstmt.setString(27, repack_total_1);
			pstmt.setString(28, repack_total_2);
			pstmt.setString(29, repack_total_3);
			pstmt.setString(30, m_depr_stan);
			pstmt.setString(31, p_depr_stan);
			pstmt.setString(32, l_depr_stan);
			pstmt.setString(33, pack_depr_stan);
			pstmt.setString(34, repack_depr_stan);
			pstmt.setString(35, tocost_year);
			pstmt.setString(36, su_year_1);
			pstmt.setString(37, su_year_2);
			pstmt.setString(38, su_year_3);
			pstmt.setString(39, su_year_4);
			pstmt.setString(40, su_year_5);
			pstmt.setString(41, su_year_6);
			pstmt.setString(42, su_year_7);
			pstmt.setString(43, su_year_8);
			pstmt.setString(44, su_stan_day);
			pstmt.setString(45, total_sales_1);
			pstmt.setString(46, total_sales_2);
			pstmt.setString(47, total_sales_3);
			pstmt.setString(48, total_sales_4);
			pstmt.setString(49, total_sales_5);
			pstmt.setString(50, total_sales_6);
			pstmt.setString(51, total_sales_7);
			pstmt.setString(52, total_sales_8);
			pstmt.setString(53, profit_1);
			pstmt.setString(54, profit_2);
			pstmt.setString(55, profit_3);
			pstmt.setString(56, profit_4);
			pstmt.setString(57, profit_5);
			pstmt.setString(58, profit_6);
			pstmt.setString(59, profit_7);
			pstmt.setString(60, profit_8);
			pstmt.setString(61, per_profit_1);
			pstmt.setString(62, per_profit_2);
			pstmt.setString(63, per_profit_3);
			pstmt.setString(64, per_profit_4);
			pstmt.setString(65, per_profit_5);
			pstmt.setString(66, per_profit_6);
			pstmt.setString(67, per_profit_7);
			pstmt.setString(68, per_profit_8);
			pstmt.setString(69, pack_type);
			pstmt.setString(70, pro_1);
			pstmt.setString(71, eff_value);
			pstmt.setString(72, pro_usage);
			pstmt.setString(73, ket_cost);
			pstmt.setString(74, target_cost);
			pstmt.setString(75, actual_cost_1);
			pstmt.setString(76, earn_rate_1);
			pstmt.setString(77, actual_cost_2);
			pstmt.setString(78, earn_rate_2);
			pstmt.setString(79, actual_cost_3);
			pstmt.setString(80, earn_rate_3);
			pstmt.setString(81, note_1);
			pstmt.setString(82, note_2);
			pstmt.setString(83, note_3);
			pstmt.setString(84, note_4);
			pstmt.setString(85, actual_cost_sum_1);
			pstmt.setString(86, actual_cost_sum_2);
			pstmt.setString(87, actual_cost_sum_3);
			pstmt.setString(88, earn_rate_sum_1);
			pstmt.setString(89, earn_rate_sum_2);
			pstmt.setString(90, earn_rate_sum_3);
			pstmt.setString(91, lme_cu);
			pstmt.setString(92, u_ex_rate);
			pstmt.setString(93, select_cost);
			pstmt.setString(94, "1");
			pstmt.setString(95, pro_usage);
			pstmt.setString(96, division);
			pstmt.setString(97, case_1_note);
			pstmt.setString(98, case_2_note);
			pstmt.setString(99, case_3_note);

		    } else {
			pstmt.setString(1, pk_pid);
			pstmt.setString(2, pk_wid);
			pstmt.setString(3, report_pk);
			pstmt.setString(4, group_no);
			pstmt.setString(5, report_dest);

			pstmt.setString(6, case_to_note_1);
			pstmt.setString(7, case_to_note_2);
			pstmt.setString(8, case_to_note_3);
			pstmt.setString(9, mold_count);
			pstmt.setString(10, press_count);
			pstmt.setString(11, line_count);
			pstmt.setString(12, pack_count);
			pstmt.setString(13, repack_count);
			pstmt.setString(14, mold_total_1);
			pstmt.setString(15, mold_total_2);
			pstmt.setString(16, mold_total_3);
			pstmt.setString(17, press_total_1);
			pstmt.setString(18, press_total_2);
			pstmt.setString(19, press_total_3);
			pstmt.setString(20, line_total_1);
			pstmt.setString(21, line_total_2);
			pstmt.setString(22, line_total_3);
			pstmt.setString(23, pack_total_1);
			pstmt.setString(24, pack_total_2);
			pstmt.setString(25, pack_total_3);
			pstmt.setString(26, repack_total_1);
			pstmt.setString(27, repack_total_2);
			pstmt.setString(28, repack_total_3);
			pstmt.setString(29, m_depr_stan);
			pstmt.setString(30, p_depr_stan);
			pstmt.setString(31, l_depr_stan);
			pstmt.setString(32, pack_depr_stan);
			pstmt.setString(33, repack_depr_stan);
			pstmt.setString(34, tocost_year);
			pstmt.setString(35, su_year_1);
			pstmt.setString(36, su_year_2);
			pstmt.setString(37, su_year_3);
			pstmt.setString(38, su_year_4);
			pstmt.setString(39, su_year_5);
			pstmt.setString(40, su_year_6);
			pstmt.setString(41, su_year_7);
			pstmt.setString(42, su_year_8);
			pstmt.setString(43, su_stan_day);
			pstmt.setString(44, total_sales_1);
			pstmt.setString(45, total_sales_2);
			pstmt.setString(46, total_sales_3);
			pstmt.setString(47, total_sales_4);
			pstmt.setString(48, total_sales_5);
			pstmt.setString(49, total_sales_6);
			pstmt.setString(50, total_sales_7);
			pstmt.setString(51, total_sales_8);
			pstmt.setString(52, profit_1);
			pstmt.setString(53, profit_2);
			pstmt.setString(54, profit_3);
			pstmt.setString(55, profit_4);
			pstmt.setString(56, profit_5);
			pstmt.setString(57, profit_6);
			pstmt.setString(58, profit_7);
			pstmt.setString(59, profit_8);
			pstmt.setString(60, per_profit_1);
			pstmt.setString(61, per_profit_2);
			pstmt.setString(62, per_profit_3);
			pstmt.setString(63, per_profit_4);
			pstmt.setString(64, per_profit_5);
			pstmt.setString(65, per_profit_6);
			pstmt.setString(66, per_profit_7);
			pstmt.setString(67, per_profit_8);
			pstmt.setString(68, pack_type);
			pstmt.setString(69, pro_1);
			pstmt.setString(70, eff_value);
			pstmt.setString(71, pro_usage);
			pstmt.setString(72, ket_cost);
			pstmt.setString(73, target_cost);
			pstmt.setString(74, actual_cost_1);
			pstmt.setString(75, earn_rate_1);
			pstmt.setString(76, actual_cost_2);
			pstmt.setString(77, earn_rate_2);
			pstmt.setString(78, actual_cost_3);
			pstmt.setString(79, earn_rate_3);
			pstmt.setString(80, note_1);
			pstmt.setString(81, note_2);
			pstmt.setString(82, note_3);
			pstmt.setString(83, note_4);
			pstmt.setString(84, actual_cost_sum_1);
			pstmt.setString(85, actual_cost_sum_2);
			pstmt.setString(86, actual_cost_sum_3);
			pstmt.setString(87, earn_rate_sum_1);
			pstmt.setString(88, earn_rate_sum_2);
			pstmt.setString(89, earn_rate_sum_3);
			pstmt.setString(90, lme_cu);
			pstmt.setString(91, u_ex_rate);
			pstmt.setString(92, select_cost);
			pstmt.setString(93, "1");
			pstmt.setString(94, pro_usage);
			pstmt.setString(95, division);
			pstmt.setString(96, case_1_note);
			pstmt.setString(97, case_2_note);
			pstmt.setString(98, case_3_note);
		    }

		    pstmt.executeUpdate();
		} catch (Exception e) {
		    throw e;
		} finally {
		    sb.delete(0, sb.length());
		    pstmt = null;
		}
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(pstmt);
	}
	return report_pk;
    }

    /**
     * 함수명 : ModifyRerpotTemp 설명 : 보고서 수기 작성 modify
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 김요한 작성일자 : 2017. 02. 13.
     */
    public String ModifyRerpotTemp(ArrayList createDataList) throws Exception {

	Hashtable CreateMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;

	String report_pk = ""; // 보고서 pk
	String part_name = "";
	String f_name = "";
	String team = "";
	String a_name = "";
	String customer_f = "";
	String pjt_name = "";
	String car_type = "";
	String app_part = "";
	String request_txt = "";

	String report_dest = "";

	String case_to_note_1 = "";
	String case_to_note_2 = "";
	String case_to_note_3 = "";

	String mold_count = "";
	String press_count = "";
	String line_count = "";
	String pack_count = "";
	String repack_count = "";

	String mold_total_1 = "";
	String mold_total_2 = "";
	String mold_total_3 = "";

	String press_total_1 = "";
	String press_total_2 = "";
	String press_total_3 = "";

	String line_total_1 = "";
	String line_total_2 = "";
	String line_total_3 = "";

	String pack_total_1 = "";
	String pack_total_2 = "";
	String pack_total_3 = "";

	String repack_total_1 = "";
	String repack_total_2 = "";
	String repack_total_3 = "";

	String m_depr_stan = "";
	String p_depr_stan = "";
	String l_depr_stan = "";
	String pack_depr_stan = "";
	String repack_depr_stan = "";

	String tocost_year = "";

	String su_year_1 = "";
	String su_year_2 = "";
	String su_year_3 = "";
	String su_year_4 = "";
	String su_year_5 = "";
	String su_year_6 = "";
	String su_year_7 = "";
	String su_year_8 = "";
	String su_stan_day = "";

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
	String pack_type = "";
	String pro_1 = "";
	String eff_value = "";
	String pro_usage = "";
	String ket_cost = "";
	String target_cost = "";
	String actual_cost_1 = "";
	String earn_rate_1 = "";
	String actual_cost_2 = "";
	String earn_rate_2 = "";
	String actual_cost_3 = "";
	String earn_rate_3 = "";
	String actual_cost_sum_1 = "";
	String actual_cost_sum_2 = "";
	String actual_cost_sum_3 = "";
	String earn_rate_sum_1 = "";
	String earn_rate_sum_2 = "";
	String earn_rate_sum_3 = "";
	String note_1 = "";
	String note_2 = "";
	String note_3 = "";
	String note_4 = "";
	String lme_cu = "";
	String u_ex_rate = "";
	String select_cost = "";
	String pk_pid = "";
	String dev_step = "";
	String dr_step = "";
	String case_1_note = "";
	String case_2_note = "";
	String case_3_note = "";

	try {
	    for (int i = 0; i < createDataList.size(); i++) {
		CreateMap = (Hashtable) createDataList.get(i);
		f_name = (String) CreateMap.get("f_name");
		report_pk = (String) CreateMap.get("pk_crp");
		System.out.println("report_pk ::::: " + report_pk);
		team = (String) CreateMap.get("team");
		a_name = (String) CreateMap.get("a_name");
		customer_f = (String) CreateMap.get("customer_f");
		pjt_name = (String) CreateMap.get("pjt_name");
		car_type = (String) CreateMap.get("car_type");
		app_part = (String) CreateMap.get("app_part");
		request_txt = (String) CreateMap.get("request_txt");
		report_dest = (String) CreateMap.get("report_dest");

		case_to_note_1 = (String) CreateMap.get("case_to_note_1");
		case_to_note_2 = (String) CreateMap.get("case_to_note_2");
		case_to_note_3 = (String) CreateMap.get("case_to_note_3");

		mold_count = (String) CreateMap.get("mold_count");
		press_count = (String) CreateMap.get("press_count");
		line_count = (String) CreateMap.get("line_count");
		pack_count = (String) CreateMap.get("pack_count");
		repack_count = (String) CreateMap.get("repack_count");

		mold_total_1 = (String) CreateMap.get("mold_total_1");
		mold_total_2 = (String) CreateMap.get("mold_total_2");
		mold_total_3 = (String) CreateMap.get("mold_total_3");

		press_total_1 = (String) CreateMap.get("press_total_1");
		press_total_2 = (String) CreateMap.get("press_total_2");
		press_total_3 = (String) CreateMap.get("press_total_3");

		line_total_1 = (String) CreateMap.get("line_total_1");
		line_total_2 = (String) CreateMap.get("line_total_2");
		line_total_3 = (String) CreateMap.get("line_total_3");

		pack_total_1 = (String) CreateMap.get("pack_total_1");
		pack_total_2 = (String) CreateMap.get("pack_total_2");
		pack_total_3 = (String) CreateMap.get("pack_total_3");

		repack_total_1 = (String) CreateMap.get("repack_total_1");
		repack_total_2 = (String) CreateMap.get("repack_total_2");
		repack_total_3 = (String) CreateMap.get("repack_total_3");

		m_depr_stan = (String) CreateMap.get("m_depr_stan");
		p_depr_stan = (String) CreateMap.get("p_depr_stan");
		l_depr_stan = (String) CreateMap.get("l_depr_stan");
		pack_depr_stan = (String) CreateMap.get("pack_depr_stan");
		repack_depr_stan = (String) CreateMap.get("repack_depr_stan");

		tocost_year = (String) CreateMap.get("tocost_year");

		su_year_1 = (String) CreateMap.get("su_year_1");
		su_year_2 = (String) CreateMap.get("su_year_2");
		su_year_3 = (String) CreateMap.get("su_year_3");
		su_year_4 = (String) CreateMap.get("su_year_4");
		su_year_5 = (String) CreateMap.get("su_year_5");
		su_year_6 = (String) CreateMap.get("su_year_6");
		su_year_7 = (String) CreateMap.get("su_year_7");
		su_year_8 = (String) CreateMap.get("su_year_8");
		su_stan_day = (String) CreateMap.get("su_stan_day");

		total_sales_1 = (String) CreateMap.get("total_sales_1");
		System.out.println("total_sales_1 ::::: " + total_sales_1);
		total_sales_2 = (String) CreateMap.get("total_sales_2");
		total_sales_3 = (String) CreateMap.get("total_sales_3");
		total_sales_4 = (String) CreateMap.get("total_sales_4");
		total_sales_5 = (String) CreateMap.get("total_sales_5");
		total_sales_6 = (String) CreateMap.get("total_sales_6");
		total_sales_7 = (String) CreateMap.get("total_sales_7");
		total_sales_8 = (String) CreateMap.get("total_sales_8");

		profit_1 = (String) CreateMap.get("profit_1");
		profit_2 = (String) CreateMap.get("profit_2");
		profit_3 = (String) CreateMap.get("profit_3");
		profit_4 = (String) CreateMap.get("profit_4");
		profit_5 = (String) CreateMap.get("profit_5");
		profit_6 = (String) CreateMap.get("profit_6");
		profit_7 = (String) CreateMap.get("profit_7");
		profit_8 = (String) CreateMap.get("profit_8");

		per_profit_1 = (String) CreateMap.get("per_profit_1");
		per_profit_2 = (String) CreateMap.get("per_profit_2");
		per_profit_3 = (String) CreateMap.get("per_profit_3");
		per_profit_4 = (String) CreateMap.get("per_profit_4");
		per_profit_5 = (String) CreateMap.get("per_profit_5");
		per_profit_6 = (String) CreateMap.get("per_profit_6");
		per_profit_7 = (String) CreateMap.get("per_profit_7");
		per_profit_8 = (String) CreateMap.get("per_profit_8");
		pack_type = (String) CreateMap.get("pack_type");
		pro_1 = (String) CreateMap.get("pro_1");
		eff_value = (String) CreateMap.get("eff_value");
		pro_usage = (String) CreateMap.get("pro_usage");
		ket_cost = (String) CreateMap.get("ket_cost");
		target_cost = (String) CreateMap.get("target_cost");
		actual_cost_1 = (String) CreateMap.get("actual_cost_1");
		earn_rate_1 = (String) CreateMap.get("earn_rate_1");
		actual_cost_2 = (String) CreateMap.get("actual_cost_2");
		earn_rate_2 = (String) CreateMap.get("earn_rate_2");
		actual_cost_3 = (String) CreateMap.get("actual_cost_3");
		earn_rate_3 = (String) CreateMap.get("earn_rate_3");
		actual_cost_sum_1 = (String) CreateMap.get("actual_cost_sum_1");
		actual_cost_sum_2 = (String) CreateMap.get("actual_cost_sum_2");
		actual_cost_sum_3 = (String) CreateMap.get("actual_cost_sum_3");
		earn_rate_sum_1 = (String) CreateMap.get("earn_rate_sum_1");
		earn_rate_sum_2 = (String) CreateMap.get("earn_rate_sum_2");
		earn_rate_sum_3 = (String) CreateMap.get("earn_rate_sum_3");
		note_1 = (String) CreateMap.get("note_1");
		note_2 = (String) CreateMap.get("note_2");
		note_3 = (String) CreateMap.get("note_3");
		note_4 = (String) CreateMap.get("note_4");
		lme_cu = (String) CreateMap.get("lme_cu");
		u_ex_rate = (String) CreateMap.get("u_ex_rate");
		select_cost = (String) CreateMap.get("select_cost");
		part_name = (String) CreateMap.get("part_name");
		pk_pid = (String) CreateMap.get("pk_pid");
		dev_step = (String) CreateMap.get("dev_step");
		dr_step = (String) CreateMap.get("dr_step");
		case_1_note = (String) CreateMap.get("case_1_note");
		case_2_note = (String) CreateMap.get("case_2_note");
		case_3_note = (String) CreateMap.get("case_3_note");

		sb.append(" UPDATE COST_PRODUCTINFO   ");
		sb.append("  SET pjt_name = ?	      ");
		sb.append("  ,team = ?		      ");
		sb.append("  ,f_name = ?	      ");
		sb.append("  ,a_name = ?	      ");
		sb.append("  ,request_txt = ?	      ");
		sb.append("  ,part_name = ?	      ");
		sb.append("  ,customer_f = ?	      ");
		sb.append("  ,car_type = ?	      ");
		sb.append("  ,app_part = ?	      ");
		sb.append("  ,dev_step = ?	      ");
		sb.append("  ,dr_step = ?	      ");
		sb.append(" WHERE pk_pid = ?	      ");

		try {
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setString(1, pjt_name);
		    pstmt.setString(2, team);
		    pstmt.setString(3, f_name);
		    pstmt.setString(4, a_name);
		    pstmt.setString(5, request_txt);
		    pstmt.setString(6, part_name);
		    pstmt.setString(7, customer_f);
		    pstmt.setString(8, car_type);
		    pstmt.setString(9, app_part);
		    pstmt.setString(10, dev_step);
		    pstmt.setString(11, dr_step);
		    pstmt.setString(12, pk_pid);

		    pstmt.executeUpdate();

		} catch (Exception e) {
		    throw e;
		} finally {
		    sb.delete(0, sb.length());
		    pstmt = null;
		}

		sb.append(" UPDATE COST_REPORT           	  ");
		sb.append("   SET report_dest = ?        	  ");
		sb.append(" 	,case_to_note_1 = ?		  ");
		sb.append(" 	,case_to_note_2 = ?		  ");
		sb.append(" 	,case_to_note_3 = ?		  ");
		sb.append(" 	,mold_count = ?		 	  ");
		sb.append(" 	,press_count = ?		  ");
		sb.append(" 	,line_count = ?		  	  ");
		sb.append(" 	,pack_count = ?		 	  ");
		sb.append(" 	,repack_count = ?		  ");
		sb.append(" 	,mold_total_1 = ?		  ");
		sb.append(" 	,mold_total_2 = ?		  ");
		sb.append(" 	,mold_total_3 = ?		  ");
		sb.append(" 	,press_total_1 = ?		  ");
		sb.append(" 	,press_total_2 = ?		  ");
		sb.append(" 	,press_total_3 = ?		  ");
		sb.append(" 	,line_total_1 = ?		  ");
		sb.append(" 	,line_total_2 = ?		  ");
		sb.append(" 	,line_total_3 = ?		  ");
		sb.append(" 	,pack_total_1 = ?		  ");
		sb.append(" 	,pack_total_2 = ?		  ");
		sb.append(" 	,pack_total_3 = ?		  ");
		sb.append(" 	,repack_total_1= ?		  ");
		sb.append(" 	,repack_total_2 = ?		  ");
		sb.append(" 	,repack_total_3 = ?		  ");
		sb.append(" 	,m_depr_stan = ?		  ");
		sb.append(" 	,p_depr_stan = ?		  ");
		sb.append(" 	,l_depr_stan = ?		  ");
		sb.append(" 	,pack_depr_stan = ?		  ");
		sb.append(" 	,repack_depr_stan = ?             ");
		sb.append(" 	,tocost_year = ?             	  ");
		sb.append(" 	,su_year_1 = ?               	  ");
		sb.append(" 	,su_year_2 = ?		 	  ");
		sb.append(" 	,su_year_3 = ?		 	  ");
		sb.append(" 	,su_year_4 = ?		 	  ");
		sb.append(" 	,su_year_5 = ?		 	  ");
		sb.append(" 	,su_year_6 = ?		 	  ");
		sb.append(" 	,su_year_7 = ?		 	  ");
		sb.append(" 	,su_year_8 = ?		 	  ");
		sb.append(" 	,su_stan_day = ?		  ");
		sb.append(" 	,total_sales_1 = ?		  ");
		sb.append(" 	,total_sales_2 = ?		  ");
		sb.append(" 	,total_sales_3 = ?		  ");
		sb.append(" 	,total_sales_4 = ?		  ");
		sb.append(" 	,total_sales_5 = ?		  ");
		sb.append(" 	,total_sales_6 = ?		  ");
		sb.append(" 	,total_sales_7 = ?		  ");
		sb.append(" 	,total_sales_8 = ?		  ");
		sb.append(" 	,profit_1 = ?		 	  ");
		sb.append(" 	,profit_2 = ?		 	  ");
		sb.append(" 	,profit_3 = ?		 	  ");
		sb.append(" 	,profit_4 = ?		 	  ");
		sb.append(" 	,profit_5 = ?		 	  ");
		sb.append(" 	,profit_6 = ?		 	  ");
		sb.append(" 	,profit_7 = ?		 	  ");
		sb.append(" 	,profit_8 = ?		 	  ");
		sb.append(" 	,per_profit_1 = ? / 100      	  ");
		sb.append(" 	,per_profit_2 = ? / 100	 	  ");
		sb.append(" 	,per_profit_3 = ? / 100	 	  ");
		sb.append(" 	,per_profit_4 = ? / 100	 	  ");
		sb.append(" 	,per_profit_5 = ? / 100	 	  ");
		sb.append(" 	,per_profit_6 = ? / 100	 	  ");
		sb.append(" 	,per_profit_7 = ? / 100	 	  ");
		sb.append(" 	,per_profit_8 = ? / 100	 	  ");
		sb.append(" 	,pack_type = ?		 	  ");
		sb.append(" 	,pro_1 = ?			  ");
		sb.append(" 	,eff_value = ?		 	  ");
		sb.append(" 	,pro_usage = ?		 	  ");
		sb.append(" 	,ket_cost = ?		 	  ");
		sb.append(" 	,target_cost = ?		  ");
		sb.append(" 	,actual_cost_1 = ?		  ");
		sb.append(" 	,earn_rate_1 = ?/ 100	 	  ");
		sb.append(" 	,actual_cost_2 = ?		  ");
		sb.append(" 	,earn_rate_2 = ?/ 100	 	  ");
		sb.append(" 	,actual_cost_3 = ?		  ");
		sb.append(" 	,earn_rate_3 = ?/100	 	  ");
		sb.append(" 	,note_1 = ?			  ");
		sb.append(" 	,note_2 = ?			  ");
		sb.append(" 	,note_3 = ?			  ");
		sb.append(" 	,note_4 = ?			  ");
		sb.append(" 	,actual_cost_sum_1 = ?	  	  ");
		sb.append(" 	,actual_cost_sum_2 = ?	 	  ");
		sb.append(" 	,actual_cost_sum_3 = ?	 	  ");
		sb.append(" 	,earn_rate_sum_1 = ?	 	  ");
		sb.append(" 	,earn_rate_sum_2 = ?	 	  ");
		sb.append(" 	,earn_rate_sum_3 = ?	 	  ");
		sb.append(" 	,lme_cu = ?			  ");
		sb.append(" 	,u_ex_rate = ?		 	  ");
		sb.append(" 	,select_cost = ?		  ");
		sb.append(" 	,usage = ?			  ");
		sb.append(" 	,case_1_note = ?		  ");
		sb.append(" 	,case_2_note = ?                  ");
		sb.append(" 	,case_3_note = ?	          ");
		sb.append("   	 WHERE pk_crp = ?	 	  ");

		try {
		    pstmt = conn.prepareStatement(sb.toString());

		    pstmt.setString(1, report_dest);
		    pstmt.setString(2, case_to_note_1);
		    pstmt.setString(3, case_to_note_2);
		    pstmt.setString(4, case_to_note_3);
		    pstmt.setString(5, mold_count);
		    pstmt.setString(6, press_count);
		    pstmt.setString(7, line_count);
		    pstmt.setString(8, pack_count);
		    pstmt.setString(9, repack_count);
		    pstmt.setString(10, mold_total_1);
		    pstmt.setString(11, mold_total_2);
		    pstmt.setString(12, mold_total_3);
		    pstmt.setString(13, press_total_1);
		    pstmt.setString(14, press_total_2);
		    pstmt.setString(15, press_total_3);
		    pstmt.setString(16, line_total_1);
		    pstmt.setString(17, line_total_2);
		    pstmt.setString(18, line_total_3);
		    pstmt.setString(19, pack_total_1);
		    pstmt.setString(20, pack_total_2);
		    pstmt.setString(21, pack_total_3);
		    pstmt.setString(22, repack_total_1);
		    pstmt.setString(23, repack_total_2);
		    pstmt.setString(24, repack_total_3);
		    pstmt.setString(25, m_depr_stan);
		    pstmt.setString(26, p_depr_stan);
		    pstmt.setString(27, l_depr_stan);
		    pstmt.setString(28, pack_depr_stan);
		    pstmt.setString(29, repack_depr_stan);
		    pstmt.setString(30, tocost_year);
		    pstmt.setString(31, su_year_1);
		    pstmt.setString(32, su_year_2);
		    pstmt.setString(33, su_year_3);
		    pstmt.setString(34, su_year_4);
		    pstmt.setString(35, su_year_5);
		    pstmt.setString(36, su_year_6);
		    pstmt.setString(37, su_year_7);
		    pstmt.setString(38, su_year_8);
		    pstmt.setString(39, su_stan_day);
		    pstmt.setString(40, total_sales_1);
		    pstmt.setString(41, total_sales_2);
		    pstmt.setString(42, total_sales_3);
		    pstmt.setString(43, total_sales_4);
		    pstmt.setString(44, total_sales_5);
		    pstmt.setString(45, total_sales_6);
		    pstmt.setString(46, total_sales_7);
		    pstmt.setString(47, total_sales_8);
		    pstmt.setString(48, profit_1);
		    pstmt.setString(49, profit_2);
		    pstmt.setString(50, profit_3);
		    pstmt.setString(51, profit_4);
		    pstmt.setString(52, profit_5);
		    pstmt.setString(53, profit_6);
		    pstmt.setString(54, profit_7);
		    pstmt.setString(55, profit_8);
		    pstmt.setString(56, per_profit_1);
		    pstmt.setString(57, per_profit_2);
		    pstmt.setString(58, per_profit_3);
		    pstmt.setString(59, per_profit_4);
		    pstmt.setString(60, per_profit_5);
		    pstmt.setString(61, per_profit_6);
		    pstmt.setString(62, per_profit_7);
		    pstmt.setString(63, per_profit_8);
		    pstmt.setString(64, pack_type);
		    pstmt.setString(65, pro_1);
		    pstmt.setString(66, eff_value);
		    pstmt.setString(67, pro_usage);
		    pstmt.setString(68, ket_cost);
		    pstmt.setString(69, target_cost);
		    pstmt.setString(70, actual_cost_1);
		    pstmt.setString(71, earn_rate_1);
		    pstmt.setString(72, actual_cost_2);
		    pstmt.setString(73, earn_rate_2);
		    pstmt.setString(74, actual_cost_3);
		    pstmt.setString(75, earn_rate_3);
		    pstmt.setString(76, note_1);
		    pstmt.setString(77, note_2);
		    pstmt.setString(78, note_3);
		    pstmt.setString(79, note_4);
		    pstmt.setString(80, actual_cost_sum_1);
		    pstmt.setString(81, actual_cost_sum_2);
		    pstmt.setString(82, actual_cost_sum_3);
		    pstmt.setString(83, earn_rate_sum_1);
		    pstmt.setString(84, earn_rate_sum_2);
		    pstmt.setString(85, earn_rate_sum_3);
		    pstmt.setString(86, lme_cu);
		    pstmt.setString(87, u_ex_rate);
		    pstmt.setString(88, select_cost);
		    pstmt.setString(89, pro_usage);
		    pstmt.setString(90, case_1_note);
		    pstmt.setString(91, case_2_note);
		    pstmt.setString(92, case_3_note);
		    pstmt.setString(93, report_pk);

		    pstmt.executeUpdate();
		} catch (Exception e) {
		    throw e;
		} finally {
		    sb.delete(0, sb.length());
		    pstmt = null;
		}
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(pstmt);
	}
	return report_pk;
    }

    /**
     * 함수명 : UpdateReportSum 설명 :보고서 총원가, 수익률 sum update
     * 
     * @param String
     *            rev_no //cost_report pk 보고서
     * @return void
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2014. 10. 07.
     */
    public void UpdateReportSum(String report_pk) throws Exception {
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" UPDATE COST_REPORT																																					            ");
	sb.append("   SET actual_cost_sum_1 = (select nvl(round(sum(actual_cost_1 * pro_usage),1),'') from cost_report where crp_group = ?)													        ");
	sb.append("      ,actual_cost_sum_2 = (select nvl(round(sum(actual_cost_2 * pro_usage),1),'') from cost_report where crp_group = ?)													        ");
	sb.append("      ,actual_cost_sum_3 = (select nvl(round(sum(actual_cost_3 * pro_usage),1),'') from cost_report where crp_group = ?)													        ");
	sb.append("      ,earn_rate_sum_1   = (select decode(sum(nvl(actual_cost_1,0)),0,'',nvl((round((1-nvl(sum(actual_cost_1 * pro_usage),0) / nullif(nvl(sum(ket_cost * pro_usage),0),0) ) * 100,1)),'')) from cost_report where crp_group = ?)	");
	sb.append("      ,earn_rate_sum_2   = (select decode(sum(nvl(actual_cost_2,0)),0,'',nvl((round((1-nvl(sum(actual_cost_2 * pro_usage),0) / nullif(nvl(sum(ket_cost * pro_usage),0),0) ) * 100,1)),'')) from cost_report where crp_group = ?)	");
	sb.append("      ,earn_rate_sum_3   = (select decode(sum(nvl(actual_cost_3,0)),0,'',nvl((round((1-nvl(sum(actual_cost_3 * pro_usage),0) / nullif(nvl(sum(ket_cost * pro_usage),0),0) ) * 100,1)),'')) from cost_report where crp_group = ?)	");
	sb.append(" WHERE CRP_GROUP = ?																																				                ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    pstmt.setString(2, report_pk);
	    pstmt.setString(3, report_pk);
	    pstmt.setString(4, report_pk);
	    pstmt.setString(5, report_pk);
	    pstmt.setString(6, report_pk);
	    pstmt.setString(7, report_pk);
	    pstmt.executeUpdate();
	} catch (Exception e) {
	    throw e;
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(pstmt);
	}
    }

    /**
     * 함수명 : ViewCostReportTemp 설명 :
     * 
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2014. 09. 30.
     */
    public ArrayList ViewCostReportTemp(Hashtable hash) throws Exception {
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	ArrayList<Hashtable<String, String>> costTempList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> costHash = null;

	String report_pk = (String) hash.get("report_pk");
	System.out.println("report_pk ======>" + report_pk);
	try {

	    sb = new StringBuffer();

	    sb.append(" select --개발배경 및 투자비 현황  start \n");
	    sb.append(" a.team   --개발담당팀			\n");
	    sb.append(",a.f_name --개발담당자				\n");
	    sb.append(",a.a_name --영업담당자				\n");
	    sb.append(",a.customer_f --고객사				\n");
	    sb.append(",a.pjt_name    -- 제품명				\n");
	    sb.append(",a.pjt_no     --프로젝트 번호			\n");
	    sb.append(",a.car_type   --적용차종				\n");
	    sb.append(",a.app_part   --적용부위				\n");
	    sb.append(",a.request_txt --검토목적				\n");
	    sb.append(",b.report_dest --물류흐름				\n");
	    sb.append(",b.case_to_note_1 --신규투자비1안메모	\n");
	    sb.append(",b.case_to_note_2 --신규투자비2안메모	\n");
	    sb.append(",b.case_to_note_3 --신규투자비3안메모	\n");
	    sb.append(",mold_count      --mold수량			\n");
	    sb.append(",press_count    --press수량		\n");
	    sb.append(",line_count     --조립설비 수량		\n");
	    sb.append(",pack_count     --기타 투자비 수량	\n");
	    sb.append(",repack_count   --포장용 투자비		\n");
	    sb.append(",trim(to_char(b.mold_total_1,'99,999,999')) as mold_total_1   --mold 비 1안 (천원단위)	\n");
	    sb.append(",trim(to_char(b.mold_total_2,'99,999,999')) as mold_total_2   --mold 비 2안 (천원단위)	\n");
	    sb.append(",trim(to_char(b.mold_total_3,'99,999,999')) as mold_total_3   --mold 비 3안 (천원단위)	\n");
	    sb.append(",trim(to_char(b.press_total_1,'99,999,999')) as press_total_1  -- press 비 1안(천원단위)	\n");
	    sb.append(",trim(to_char(b.press_total_2,'99,999,999')) as press_total_2  -- press 비 2안(천원단위)	\n");
	    sb.append(",trim(to_char(b.press_total_3,'99,999,999')) as press_total_3  -- press 비 3안(천원단위)	\n");
	    sb.append(",trim(to_char(b.line_total_1,'99,999,999')) as line_total_1   -- 조립설비비1안 (천원단위)	\n");
	    sb.append(",trim(to_char(b.line_total_2,'99,999,999')) as line_total_2   -- 조립설비비2안 (천원단위)	\n");
	    sb.append(",trim(to_char(b.line_total_3,'99,999,999')) as line_total_3   -- 조립설비비3안 (천원단위)	\n");
	    sb.append(",trim(to_char(b.pack_total_1,'99,999,999')) as pack_total_1   -- 기타 투자비(천원단위)		\n");
	    sb.append(",trim(to_char(b.pack_total_2,'99,999,999')) as pack_total_2   -- 기타 투자비(천원단위)		\n");
	    sb.append(",trim(to_char(b.pack_total_3,'99,999,999')) as pack_total_3   -- 기타 투자비(천원단위)		\n");
	    sb.append(",trim(to_char(b.repack_total_1,'99,999,999')) as repack_total_1 -- 포장용 투자비(천원단위)		\n");
	    sb.append(",trim(to_char(b.repack_total_2,'99,999,999')) as repack_total_2 -- 포장용 투자비(천원단위)		\n");
	    sb.append(",trim(to_char(b.repack_total_3,'99,999,999')) as repack_total_3 -- 포장용 투자비(천원단위)		\n");
	    sb.append(",trim(to_char(nvl(b.mold_total_1,0) + nvl(b.press_total_1,0) + nvl(b.line_total_1,0) + nvl(b.pack_total_1,0) + nvl(repack_total_1,0),'99,999,999'))  as total_1 \n");
	    sb.append(",trim(to_char(nvl(b.mold_total_2,0) + nvl(b.press_total_2,0) + nvl(b.line_total_2,0) + nvl(b.pack_total_2,0) + nvl(repack_total_2,0),'99,999,999'))  as total_2 \n");
	    sb.append(",trim(to_char(nvl(b.mold_total_3,0) + nvl(b.press_total_3,0) + nvl(b.line_total_3,0) + nvl(b.pack_total_3,0) + nvl(repack_total_3,0),'99,999,999'))  as total_3 \n");
	    sb.append(",b.m_depr_stan      -- 'mold상각기준'		\n");
	    sb.append(",b.p_depr_stan      -- 'press상각기준'		\n");
	    sb.append(",b.l_depr_stan      -- '조립설비상각기준'		\n");
	    sb.append(",b.pack_depr_stan   -- '기타투자비 상각기준'	\n");
	    sb.append(",b.repack_depr_stan -- '포장용 투자비 상각기준'	\n");
	    sb.append("--개발배경 및 투자비 현황 end										\n");
	    sb.append("--------------------------------------------------			\n");
	    sb.append("--예상 손익 및 환율, 원재료 가격 기준 start							\n");
	    sb.append(",trim(to_char(b.su_year_1,'99,999,999')) as su_year_1    -- 기획수량-1년차								\n");
	    sb.append(",trim(to_char(b.su_year_2,'99,999,999')) as su_year_2    -- 기획수량-2년차								\n");
	    sb.append(",trim(to_char(b.su_year_3,'99,999,999')) as su_year_3    -- 기획수량-3년차								\n");
	    sb.append(",trim(to_char(b.su_year_4,'99,999,999')) as su_year_4    -- 기획수량-4년차								\n");
	    sb.append(",trim(to_char(b.su_year_5,'99,999,999')) as su_year_5    -- 기획수량-5년차								\n");
	    sb.append(",trim(to_char(b.su_year_6,'99,999,999')) as su_year_6    -- 기획수량-6년차								\n");
	    sb.append(",trim(to_char(b.su_year_7,'99,999,999')) as su_year_7    -- 기획수량-7년차								\n");
	    sb.append(",trim(to_char(b.su_year_8,'99,999,999')) as su_year_8    -- 기획수량-8년차								\n");
	    sb.append(",trim(to_char(nvl(b.su_year_1,0) + nvl(b.su_year_2,0) + nvl(b.su_year_3,0) + nvl(b.su_year_4,0) + nvl(b.su_year_5,0) + nvl(b.su_year_6,0) + nvl(b.su_year_7,0) + nvl(b.su_year_8,0),'99,999,999')) as avg_su -- 기획수량 합계 \n");
	    sb.append(",b.su_stan_day  --수지 재료단가	\n");
	    sb.append(",b.lme_cu       --비철시세		\n");
	    sb.append(",b.u_ex_rate    --달러기준가		\n");
	    sb.append(",trim(to_char(b.total_sales_1,'99,999,999')) as total_sales_1  --매출액1년차	\n");
	    sb.append(",trim(to_char(b.total_sales_2,'99,999,999')) as total_sales_2  --매출액2년차	\n");
	    sb.append(",trim(to_char(b.total_sales_3,'99,999,999')) as total_sales_3  --매출액3년차	\n");
	    sb.append(",trim(to_char(b.total_sales_4,'99,999,999')) as total_sales_4  --매출액4년차	\n");
	    sb.append(",trim(to_char(b.total_sales_5,'99,999,999')) as total_sales_5  --매출액5년차	\n");
	    sb.append(",trim(to_char(b.total_sales_6,'99,999,999')) as total_sales_6  --매출액6년차	\n");
	    sb.append(",trim(to_char(b.total_sales_7,'99,999,999')) as total_sales_7  --매출액7년차	\n");
	    sb.append(",trim(to_char(b.total_sales_8,'99,999,999')) as total_sales_8  --매출액8년차	\n");
	    sb.append(",trim(to_char((nvl(total_sales_1,0)+nvl(total_sales_2,0)+nvl(total_sales_3,0)+nvl(total_sales_4,0)+nvl(total_sales_5,0)+nvl(total_sales_6,0)+nvl(total_sales_7,0)+nvl(total_sales_8,0)),'99,999,999')) as sum_sales \n");
	    sb.append(",ROUND(b.profit_1) AS profit_1 --영업이익-1년차		\n");
	    sb.append(",ROUND(b.profit_2) AS profit_2 --영업이익-2년차		\n");
	    sb.append(",ROUND(b.profit_3) AS profit_3 --영업이익-3년차		\n");
	    sb.append(",ROUND(b.profit_4) AS profit_4 --영업이익-4년차		\n");
	    sb.append(",ROUND(b.profit_5) AS profit_5 --영업이익-5년차		\n");
	    sb.append(",ROUND(b.profit_6) AS profit_6 --영업이익-6년차		\n");
	    sb.append(",ROUND(b.profit_7) AS profit_7 --영업이익-7년차		\n");
	    sb.append(",ROUND(b.profit_8) AS profit_8 --영업이익-8년차		\n");
	    sb.append(",(nvl(profit_1,0)+nvl(profit_2,0)+nvl(profit_3,0)+nvl(profit_4,0)+nvl(profit_5,0)+nvl(profit_6,0)+nvl(profit_7,0)+nvl(profit_8,0)) as sum_profit	\n");
	    sb.append(",round(((nvl(profit_1,0)+nvl(profit_2,0)+nvl(profit_3,0)+nvl(profit_4,0)+nvl(profit_5,0)+nvl(profit_6,0)+nvl(profit_7,0)+nvl(profit_8,0))/			\n");
	    sb.append("nullif((nvl(total_sales_1,0)+nvl(total_sales_2,0)+nvl(total_sales_3,0)+nvl(total_sales_4,0)+nvl(total_sales_5,0)+nvl(total_sales_6,0)+nvl(total_sales_7,0)+nvl(total_sales_8,0)),0) )*100,1) as sum_per_profit	\n");
	    sb.append(",per_profit_1 * 100 as per_profit_1  --영업이익률 1년차	\n");
	    sb.append(",per_profit_2 * 100 as per_profit_2--영업이익률 2년차	\n");
	    sb.append(",per_profit_3 * 100 as per_profit_3--영업이익률 3년차	\n");
	    sb.append(",per_profit_4 * 100 as per_profit_4--영업이익률 4년차	\n");
	    sb.append(",per_profit_5 * 100 as per_profit_5--영업이익률 5년차	\n");
	    sb.append(",per_profit_6 * 100 as per_profit_6--영업이익률 6년차	\n");
	    sb.append(",per_profit_7 * 100 as per_profit_7--영업이익률 7년차	\n");
	    sb.append(",per_profit_8 * 100  as per_profit_8--영업이익률 8년차	\n");
	    sb.append(",b.pack_type --포장유형													\n");
	    sb.append(",b.pro_1     --생산처 구분													\n");
	    sb.append(",eff_value * 100  AS eff_value -- 생산 효율							\n");
	    sb.append("-------------------------------------------------						\n");
	    sb.append("--예상 손익 및 환율, 원재료 가격 기준 end-------\n");
	    sb.append("-----------------------------------------------\n");
	    sb.append("-------------------------------------------------\n");
	    sb.append("-- 원가계산 결과start ---------------------------\n");
	    sb.append(",a.part_name  --제품명								\n");
	    sb.append(",b.pro_usage  --적용 u/s										\n");
	    sb.append(",trim(to_char(round(b.ket_cost,1),'99,999.9'))  as ket_cost --판매 목표가					\n");
	    sb.append(",b.target_cost  --목표 수익률									\n");
	    sb.append(",trim(to_char(round(actual_cost_1,1),'99,999.9'))  as actual_cost_1 -- 1안 총원가		\n");
	    sb.append(",earn_rate_1 as earn_rate_1 -- 1안수익율			\n");
	    sb.append(",trim(to_char(round(actual_cost_sum_1,1),'99,999.9'))  as actual_cost_sum_1 -- 1안 총원가 합계		\n");
	    sb.append(",earn_rate_sum_1   -- 1안수익율 합계									\n");
	    sb.append(",trim(to_char(round(actual_cost_2,1),'99,999.9')) as actual_cost_2 -- 2안 총원가				\n");
	    sb.append(",earn_rate_2 as earn_rate_2 -- 2안수익율					\n");
	    sb.append(",trim(to_char(round(actual_cost_sum_2,1),'99,999.9')) as actual_cost_sum_2 -- 2안 총원가 합계		\n");
	    sb.append(",earn_rate_sum_2   -- 2안수익율 합계									\n");
	    sb.append(",trim(to_char(round(actual_cost_3,1),'99,999.9'))  as actual_cost_3 -- 3안 총원가				\n");
	    sb.append(",earn_rate_3 as earn_rate_3 -- 3안수익율					\n");
	    sb.append(",trim(to_char(round(actual_cost_sum_3,1),'99,999.9'))  as actual_cost_sum_3-- 3안 총원가 합계		\n");
	    sb.append(",earn_rate_sum_3   -- 3안수익율 합계												\n");
	    sb.append(",trim(to_char((select nvl(round(sum(ket_cost * pro_usage),1),0) from cost_report b			\n");
	    sb.append("   where crp_group = ?),'99,999.9')) as sum_ket_cost -- 판매목표가 합계						\n");
	    sb.append(",case_1_note -- 1안메모						\n");
	    sb.append(",case_2_note -- 2안메모						\n");
	    sb.append(",case_3_note -- 3안메모						\n");
	    sb.append(",select_cost -- 선택 안 						\n");
	    sb.append(",tocost_year -- 투자비 회수기간 						\n");
	    sb.append(",note_1  						\n");
	    sb.append(",note_2   						\n");
	    sb.append(",note_3  						\n");
	    sb.append(",note_4  						\n");
	    sb.append(",b.file_1  						\n");
	    sb.append(",b.file_2  						\n");
	    sb.append(",b.file_3  						\n");
	    sb.append(",b.file_4  						\n");
	    sb.append(",b.file_5  						\n");
	    sb.append(",b.file_6  						\n");
	    sb.append(",b.file_2_name  						\n");
	    sb.append(",b.file_3_name  						\n");
	    sb.append(",b.file_4_name  						\n");
	    sb.append(",b.file_5_name  						\n");
	    sb.append(",b.file_6_name  						\n");
	    sb.append(",a.pk_pid  						\n"); // cost_productinfo의 pk
	    sb.append(",b.pk_crp  						\n"); // cost_report의 pk
	    sb.append(",d.pk_cr  						\n"); // cost_Request의 pk
	    sb.append(",c.step_no  					\n");
	    sb.append(",a.dev_step  					\n");
	    sb.append(",c.f_day_2 as f_day				\n");
	    sb.append(",c.Gr_day_2 as Gr_day				\n");
	    sb.append(",c.Gr_name_2 as Gr_name				\n");
	    sb.append(",c.Leader_day_2 as Leader_day		\n");
	    sb.append(",c.p_leader_day						\n");
	    sb.append(",c.p_leader_name					\n");
	    sb.append(",c.r_owner_day						\n");
	    sb.append(",c.r_pre_day						\n");
	    sb.append(",a.w_name						\n");
	    sb.append(",a.dr_step						\n");
	    sb.append("from cost_productinfo a, cost_report b, wfinfo c, cost_Request d				\n");
	    sb.append("where a.pk_pid = b.fk_pid 														\n");
	    sb.append("and b.fk_wid = c.pk_wid 														\n");
	    sb.append("and a.pk_pid = d.fk_pid 														\n");
	    sb.append("and crp_group = ? 															\n");
	    sb.append("and input_gb  = '1' 															\n");
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, report_pk);
	    pstmt.setString(2, report_pk);
	    System.out.println("query ==" + sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		costHash = new Hashtable<String, String>();
		costHash.put("team", StringUtil.checkNull(rs.getString("team")));
		costHash.put("f_name", StringUtil.checkNull(rs.getString("f_name")));
		costHash.put("a_name", StringUtil.checkNull(rs.getString("a_name")));
		costHash.put("customer_f", StringUtil.checkNull(rs.getString("customer_f")));
		costHash.put("pjt_name", StringUtil.checkNull(rs.getString("pjt_name")));
		costHash.put("pjt_no", StringUtil.checkNull(rs.getString("pjt_no")));
		costHash.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		costHash.put("app_part", StringUtil.checkNull(rs.getString("app_part")));
		costHash.put("request_txt", StringUtil.checkNull(rs.getString("request_txt")));
		costHash.put("report_dest", StringUtil.checkNull(rs.getString("report_dest")));
		costHash.put("case_to_note_1", StringUtil.checkNull(rs.getString("case_to_note_1")));
		costHash.put("case_to_note_2", StringUtil.checkNull(rs.getString("case_to_note_2")));
		costHash.put("case_to_note_3", StringUtil.checkNull(rs.getString("case_to_note_3")));
		costHash.put("mold_count", StringUtil.checkNull(rs.getString("mold_count")));
		costHash.put("press_count", StringUtil.checkNull(rs.getString("press_count")));
		costHash.put("line_count", StringUtil.checkNull(rs.getString("line_count")));
		costHash.put("pack_count", StringUtil.checkNull(rs.getString("pack_count")));
		costHash.put("repack_count", StringUtil.checkNull(rs.getString("repack_count")));
		costHash.put("mold_total_1", StringUtil.checkNull(rs.getString("mold_total_1")));
		costHash.put("mold_total_2", StringUtil.checkNull(rs.getString("mold_total_2")));
		costHash.put("mold_total_3", StringUtil.checkNull(rs.getString("mold_total_3")));
		costHash.put("press_total_1", StringUtil.checkNull(rs.getString("press_total_1")));
		costHash.put("press_total_2", StringUtil.checkNull(rs.getString("press_total_2")));
		costHash.put("press_total_3", StringUtil.checkNull(rs.getString("press_total_3")));
		costHash.put("line_total_1", StringUtil.checkNull(rs.getString("line_total_1")));
		costHash.put("line_total_2", StringUtil.checkNull(rs.getString("line_total_2")));
		costHash.put("line_total_3", StringUtil.checkNull(rs.getString("line_total_3")));
		costHash.put("pack_total_1", StringUtil.checkNull(rs.getString("pack_total_1")));
		costHash.put("pack_total_2", StringUtil.checkNull(rs.getString("pack_total_2")));
		costHash.put("pack_total_3", StringUtil.checkNull(rs.getString("pack_total_3")));
		costHash.put("repack_total_1", StringUtil.checkNull(rs.getString("repack_total_1")));
		costHash.put("repack_total_2", StringUtil.checkNull(rs.getString("repack_total_2")));
		costHash.put("repack_total_3", StringUtil.checkNull(rs.getString("repack_total_3")));
		costHash.put("total_1", StringUtil.checkNull(rs.getString("total_1")));
		costHash.put("total_2", StringUtil.checkNull(rs.getString("total_2")));
		costHash.put("total_3", StringUtil.checkNull(rs.getString("total_3")));
		costHash.put("m_depr_stan", StringUtil.checkNull(rs.getString("m_depr_stan")));
		costHash.put("p_depr_stan", StringUtil.checkNull(rs.getString("p_depr_stan")));
		costHash.put("l_depr_stan", StringUtil.checkNull(rs.getString("l_depr_stan")));
		costHash.put("pack_depr_stan", StringUtil.checkNull(rs.getString("pack_depr_stan")));
		costHash.put("repack_depr_stan", StringUtil.checkNull(rs.getString("repack_depr_stan")));
		costHash.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		costHash.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		costHash.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		costHash.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		costHash.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		costHash.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		costHash.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		costHash.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		costHash.put("avg_su", StringUtil.checkNull(rs.getString("avg_su")));
		costHash.put("su_stan_day", StringUtil.checkNull(rs.getString("su_stan_day")));
		costHash.put("lme_cu", StringUtil.checkNull(rs.getString("lme_cu")));
		costHash.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		costHash.put("total_sales_1", StringUtil.checkNull(rs.getString("total_sales_1")));
		costHash.put("total_sales_2", StringUtil.checkNull(rs.getString("total_sales_2")));
		costHash.put("total_sales_3", StringUtil.checkNull(rs.getString("total_sales_3")));
		costHash.put("total_sales_4", StringUtil.checkNull(rs.getString("total_sales_4")));
		costHash.put("total_sales_5", StringUtil.checkNull(rs.getString("total_sales_5")));
		costHash.put("total_sales_6", StringUtil.checkNull(rs.getString("total_sales_6")));
		costHash.put("total_sales_7", StringUtil.checkNull(rs.getString("total_sales_7")));
		costHash.put("total_sales_8", StringUtil.checkNull(rs.getString("total_sales_8")));
		costHash.put("sum_sales", StringUtil.checkNull(rs.getString("sum_sales")));
		costHash.put("profit_1", StringUtil.checkNull(rs.getString("profit_1")));
		costHash.put("profit_2", StringUtil.checkNull(rs.getString("profit_2")));
		costHash.put("profit_3", StringUtil.checkNull(rs.getString("profit_3")));
		costHash.put("profit_4", StringUtil.checkNull(rs.getString("profit_4")));
		costHash.put("profit_5", StringUtil.checkNull(rs.getString("profit_5")));
		costHash.put("profit_6", StringUtil.checkNull(rs.getString("profit_6")));
		costHash.put("profit_7", StringUtil.checkNull(rs.getString("profit_7")));
		costHash.put("profit_8", StringUtil.checkNull(rs.getString("profit_8")));
		costHash.put("sum_profit", StringUtil.checkNull(rs.getString("sum_profit")));
		costHash.put("sum_per_profit", StringUtil.checkNull(rs.getString("sum_per_profit")));
		costHash.put("per_profit_1", StringUtil.checkNull(rs.getString("per_profit_1")));
		costHash.put("per_profit_2", StringUtil.checkNull(rs.getString("per_profit_2")));
		costHash.put("per_profit_3", StringUtil.checkNull(rs.getString("per_profit_3")));
		costHash.put("per_profit_4", StringUtil.checkNull(rs.getString("per_profit_4")));
		costHash.put("per_profit_5", StringUtil.checkNull(rs.getString("per_profit_5")));
		costHash.put("per_profit_6", StringUtil.checkNull(rs.getString("per_profit_6")));
		costHash.put("per_profit_7", StringUtil.checkNull(rs.getString("per_profit_7")));
		costHash.put("per_profit_8", StringUtil.checkNull(rs.getString("per_profit_8")));
		costHash.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		costHash.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		costHash.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		costHash.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		costHash.put("pro_usage", StringUtil.checkNull(rs.getString("pro_usage")));
		costHash.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		costHash.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		costHash.put("actual_cost_1", StringUtil.checkNull(rs.getString("actual_cost_1")));
		costHash.put("earn_rate_1", StringUtil.checkNull(rs.getString("earn_rate_1")));
		costHash.put("actual_cost_sum_1", StringUtil.checkNull(rs.getString("actual_cost_sum_1")));
		costHash.put("earn_rate_sum_1", StringUtil.checkNull(rs.getString("earn_rate_sum_1")));
		costHash.put("actual_cost_2", StringUtil.checkNull(rs.getString("actual_cost_2")));
		costHash.put("earn_rate_2", StringUtil.checkNull(rs.getString("earn_rate_2")));
		costHash.put("actual_cost_sum_2", StringUtil.checkNull(rs.getString("actual_cost_sum_2")));
		costHash.put("earn_rate_sum_2", StringUtil.checkNull(rs.getString("earn_rate_sum_2")));
		costHash.put("actual_cost_3", StringUtil.checkNull(rs.getString("actual_cost_3")));
		costHash.put("earn_rate_3", StringUtil.checkNull(rs.getString("earn_rate_3")));
		costHash.put("actual_cost_sum_3", StringUtil.checkNull(rs.getString("actual_cost_sum_3")));
		costHash.put("earn_rate_sum_3", StringUtil.checkNull(rs.getString("earn_rate_sum_3")));
		costHash.put("sum_ket_cost", StringUtil.checkNull(rs.getString("sum_ket_cost")));
		costHash.put("case_1_note", StringUtil.checkNull(rs.getString("case_1_note")));
		costHash.put("case_2_note", StringUtil.checkNull(rs.getString("case_2_note")));
		costHash.put("case_3_note", StringUtil.checkNull(rs.getString("case_3_note")));
		costHash.put("select_cost", StringUtil.checkNull(rs.getString("select_cost")));
		costHash.put("tocost_year", StringUtil.checkNull(rs.getString("tocost_year")));
		costHash.put("note_1", StringUtil.checkNull(rs.getString("note_1")));
		costHash.put("note_2", StringUtil.checkNull(rs.getString("note_2")));
		costHash.put("note_3", StringUtil.checkNull(rs.getString("note_3")));
		costHash.put("note_4", StringUtil.checkNull(rs.getString("note_4")));
		costHash.put("pk_pid", StringUtil.checkNull(rs.getString("pk_pid")));
		costHash.put("pk_crp", StringUtil.checkNull(rs.getString("pk_crp")));
		costHash.put("pk_cr", StringUtil.checkNull(rs.getString("pk_cr")));
		costHash.put("file_1", StringUtil.checkNull(rs.getString("file_1")));
		costHash.put("file_2", StringUtil.checkNull(rs.getString("file_2")));
		costHash.put("file_3", StringUtil.checkNull(rs.getString("file_3")));
		costHash.put("file_4", StringUtil.checkNull(rs.getString("file_4")));
		costHash.put("file_5", StringUtil.checkNull(rs.getString("file_5")));
		costHash.put("file_6", StringUtil.checkNull(rs.getString("file_6")));
		costHash.put("file_2_name", StringUtil.checkNull(rs.getString("file_2_name")));
		costHash.put("file_3_name", StringUtil.checkNull(rs.getString("file_3_name")));
		costHash.put("file_4_name", StringUtil.checkNull(rs.getString("file_4_name")));
		costHash.put("file_5_name", StringUtil.checkNull(rs.getString("file_5_name")));
		costHash.put("file_6_name", StringUtil.checkNull(rs.getString("file_6_name")));
		costHash.put("step_no", StringUtil.checkNull(rs.getString("step_no")));
		costHash.put("dev_step", StringUtil.checkNull(rs.getString("dev_step")));
		costHash.put("f_day", StringUtil.checkNull(rs.getString("f_day")));
		costHash.put("Gr_day", StringUtil.checkNull(rs.getString("Gr_day")));
		costHash.put("Gr_name", StringUtil.checkNull(rs.getString("Gr_name")));
		costHash.put("Leader_day", StringUtil.checkNull(rs.getString("Leader_day")));
		costHash.put("p_leader_day", StringUtil.checkNull(rs.getString("p_leader_day")));
		costHash.put("p_leader_name", StringUtil.checkNull(rs.getString("p_leader_name")));
		costHash.put("r_owner_day", StringUtil.checkNull(rs.getString("r_owner_day")));
		costHash.put("r_pre_day", StringUtil.checkNull(rs.getString("r_pre_day")));
		costHash.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		costHash.put("dr_step", StringUtil.checkNull(rs.getString("dr_step")));

		costTempList.add(costHash);
	    }
	    // System.out.println("----------------------쿼리실행종료");

	} catch (SQLException se) {
	    se.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    pstmt.close();
	    rs.close();
	}

	return costTempList;
    }

    /**
     * 함수명 : InsertRerpotMig 설명 : 보고서 Mig
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2015. 05. 11.
     */
    public String InsertRerpotMig(List<CostReportPropDTO> propList) throws Exception {

	Hashtable CreateMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;

	String report_pk = ""; // 보고서 pk
	String pk_pid = ""; // cost_productinfo pk
	String pk_cr = ""; // cost_request pk
	String pk_wid = ""; // wfinfo pk
	String pjt_no = "";
	String part_name = "";
	String group_no = "";
	String f_name = "";
	String rev_no = "";

	String team = "";
	String a_name = "";
	String customer_f = "";
	String pjt_name = "";
	String car_type = "";
	String app_part = "";
	String request_txt = "";

	String report_dest = "";

	String case_to_note_1 = "";
	String case_to_note_2 = "";
	String case_to_note_3 = "";

	String mold_count = "";
	String press_count = "";
	String line_count = "";
	String pack_count = "";
	String repack_count = "";

	String mold_total_1 = "";
	String mold_total_2 = "";
	String mold_total_3 = "";

	String press_total_1 = "";
	String press_total_2 = "";
	String press_total_3 = "";

	String line_total_1 = "";
	String line_total_2 = "";
	String line_total_3 = "";

	String pack_total_1 = "";
	String pack_total_2 = "";
	String pack_total_3 = "";

	String repack_total_1 = "";
	String repack_total_2 = "";
	String repack_total_3 = "";

	String m_depr_stan = "";
	String p_depr_stan = "";
	String l_depr_stan = "";
	String pack_depr_stan = "";
	String repack_depr_stan = "";

	String tocost_year = "";

	String su_year_1 = "";
	String su_year_2 = "";
	String su_year_3 = "";
	String su_year_4 = "";
	String su_year_5 = "";
	String su_year_6 = "";
	String su_year_7 = "";
	String su_year_8 = "";
	String su_stan_day = "";

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
	String pack_type = "";
	String pro_1 = "";
	String eff_value = "";
	String pro_usage = "";
	String ket_cost = "";
	String target_cost = "";
	String actual_cost_1 = "";
	String earn_rate_1 = "";
	String actual_cost_2 = "";
	String earn_rate_2 = "";
	String actual_cost_3 = "";
	String earn_rate_3 = "";
	String actual_cost_sum_1 = "";
	String actual_cost_sum_2 = "";
	String actual_cost_sum_3 = "";
	String earn_rate_sum_1 = "";
	String earn_rate_sum_2 = "";
	String earn_rate_sum_3 = "";
	String note_1 = "";
	String note_2 = "";
	String note_3 = "";
	String note_4 = "";
	String lme_cu = "";
	String u_ex_rate = "";
	String select_cost = "";
	String w_name = "";
	String dev_step = "";
	String table_row = "";
	String dr_step = "";
	String cmd = "";
	String division = "";
	String case_1_note = "";
	String case_2_note = "";
	String case_3_note = "";
	int temp_row = 0;
	try {
	    for (CostReportPropDTO dto : propList) {
		temp_row = 0;
		System.out.println("Miglation Insert 진행 중 .." + dto.getProjectNo());
		if (!"".equals(dto.getT002_part_name())) {
		    temp_row = temp_row + 1;
		}
		if (!"".equals(dto.getT003_part_name())) {
		    temp_row = temp_row + 1;
		}

		table_row = Integer.toString(temp_row);

		pjt_no = dto.getProjectNo();
		part_name = dto.getProductName();

		f_name = dto.getF_Name();
		report_pk = getPkCrt(); // cost_report pk 보고서
		rev_no = dto.getRevision();
		pk_wid = GetwfPk(); // wf_info pk 결재

		if ("커넥터설계팀".equals(dto.getF_DeptName())) {
		    team = "1";
		} else if ("커넥터개발팀".equals(dto.getF_DeptName())) {
		    team = "11";
		} else if ("커넥터양산개선팀".equals(dto.getF_DeptName())) {
		    team = "12";
		} else if ("전장모듈연구개발1팀".equals(dto.getF_DeptName())) {
		    team = "22";
		} else if ("전장모듈연구개발2팀".equals(dto.getF_DeptName())) {
		    team = "23";
		} else if ("전장모듈연구개발3팀".equals(dto.getF_DeptName())) {
		    team = "24";
		} else if ("연구개발3팀".equals(dto.getF_DeptName())) {
		    team = "3";
		} else if ("연구개발4팀".equals(dto.getF_DeptName())) {
		    team = "4";
		} else if ("연구개발5팀".equals(dto.getF_DeptName())) {
		    team = "5";
		} else if ("연구개발6팀".equals(dto.getF_DeptName())) {
		    team = "6";
		} else if ("시작개발팀".equals(dto.getF_DeptName())) {
		    team = "21";
		} else if ("전장부품개발팀".equals(dto.getF_DeptName())) {
		    team = "7";
		}
		a_name = dto.getA_Name();
		customer_f = dto.getCustomer_f();
		pjt_name = dto.getProductName();
		car_type = dto.getCartype();
		app_part = dto.getApp_part();
		request_txt = dto.getRequest_txt();
		report_dest = dto.getReport_dest();

		case_to_note_1 = dto.getCase_to_note_1();
		case_to_note_2 = dto.getCase_to_note_2();
		case_to_note_3 = dto.getCase_to_note_3();

		mold_count = dto.getMold_count();
		press_count = dto.getPress_count();
		line_count = dto.getLine_count();
		pack_count = dto.getPack_count();
		repack_count = dto.getRepack_count();

		mold_total_1 = dto.getMold_total_1();
		mold_total_2 = dto.getMold_total_2();
		mold_total_3 = dto.getMold_total_3();

		press_total_1 = dto.getPress_total_1();
		press_total_2 = dto.getPress_total_2();
		press_total_3 = dto.getPress_total_3();

		line_total_1 = dto.getLine_total_1();
		line_total_2 = dto.getLine_total_2();
		line_total_3 = dto.getLine_total_3();

		pack_total_1 = dto.getPack_total_1();
		pack_total_2 = dto.getPack_total_2();
		pack_total_3 = dto.getPack_total_3();

		repack_total_1 = dto.getRepack_total_1();
		repack_total_2 = dto.getRepack_total_2();
		repack_total_3 = dto.getRepack_total_3();

		m_depr_stan = "";
		p_depr_stan = "";
		l_depr_stan = "";
		pack_depr_stan = "";
		repack_depr_stan = "";
		tocost_year = dto.getTocost_year();

		su_year_1 = dto.getSu_year_1();
		su_year_2 = dto.getSu_year_2();
		su_year_3 = dto.getSu_year_3();
		su_year_4 = dto.getSu_year_4();
		su_year_5 = dto.getSu_year_5();
		su_year_6 = dto.getSu_year_6();
		su_year_7 = dto.getSu_year_7();
		su_year_8 = dto.getSu_year_8();
		su_stan_day = "";

		total_sales_1 = dto.getTotal_sales_1();
		total_sales_2 = dto.getTotal_sales_2();
		total_sales_3 = dto.getTotal_sales_3();
		total_sales_4 = dto.getTotal_sales_4();
		total_sales_5 = dto.getTotal_sales_5();
		total_sales_6 = dto.getTotal_sales_6();
		total_sales_7 = dto.getTotal_sales_7();
		total_sales_8 = dto.getTotal_sales_8();

		profit_1 = dto.getProfit_1();
		profit_2 = dto.getProfit_2();
		profit_3 = dto.getProfit_3();
		profit_4 = dto.getProfit_4();
		profit_5 = dto.getProfit_5();
		profit_6 = dto.getProfit_6();
		profit_7 = dto.getProfit_7();
		profit_8 = dto.getProfit_8();

		per_profit_1 = dto.getPer_profit_1();
		per_profit_2 = dto.getPer_profit_2();
		per_profit_3 = dto.getPer_profit_3();
		per_profit_4 = dto.getPer_profit_4();
		per_profit_5 = dto.getPer_profit_5();
		per_profit_6 = dto.getPer_profit_6();
		per_profit_7 = dto.getPer_profit_7();
		per_profit_8 = dto.getPer_profit_8();
		pack_type = dto.getPack_type();
		pro_1 = dto.getPro_1();
		eff_value = dto.getEff_value();
		actual_cost_sum_1 = "";
		actual_cost_sum_2 = "";
		actual_cost_sum_3 = "";
		earn_rate_sum_1 = "";
		earn_rate_sum_2 = "";
		earn_rate_sum_3 = "";
		note_1 = dto.getNote_1();
		note_2 = dto.getNote_2();
		note_3 = "";
		note_4 = "";
		lme_cu = dto.getLme_cu();
		u_ex_rate = dto.getU_ex_rate();
		select_cost = "1";
		w_name = dto.getE_Name();
		dev_step = dto.getDevStep();
		if ("개발착수/진행중".equals(dev_step)) {
		    dev_step = "개발착수";
		}
		dr_step = dto.getDrStep();
		division = "1";// 사업부 구분(1: 자동차 2: 전자)

		for (int i = 0; i <= temp_row; i++) {
		    if (i == 0) {
			group_no = "T001";
			pro_usage = dto.getT001_pro_usage();
			ket_cost = dto.getT001_ket_cost();
			target_cost = dto.getT001_target_cost();
			actual_cost_1 = dto.getT001_actual_cost_1();
			earn_rate_1 = dto.getT001_earn_rate_1();
			actual_cost_2 = dto.getT001_actual_cost_2();
			earn_rate_2 = dto.getT001_earn_rate_2();
			actual_cost_3 = dto.getT001_actual_cost_3();
			earn_rate_3 = dto.getT001_earn_rate_3();
			case_1_note = dto.getT001_case_1_note();
			case_2_note = dto.getT001_case_2_note();
			case_3_note = dto.getT001_case_3_note();
		    } else if (i == 1) {
			group_no = "T002";
			pro_usage = dto.getT002_pro_usage();
			ket_cost = dto.getT002_ket_cost();
			target_cost = dto.getT002_target_cost();
			actual_cost_1 = dto.getT002_actual_cost_1();
			earn_rate_1 = dto.getT002_earn_rate_1();
			actual_cost_2 = dto.getT002_actual_cost_2();
			earn_rate_2 = dto.getT002_earn_rate_2();
			actual_cost_3 = dto.getT002_actual_cost_3();
			earn_rate_3 = dto.getT002_earn_rate_3();
			case_1_note = dto.getT002_case_1_note();
			case_2_note = dto.getT002_case_2_note();
			case_3_note = dto.getT002_case_3_note();
		    } else if (i == 2) {
			group_no = "T003";
			pro_usage = dto.getT003_pro_usage();
			ket_cost = dto.getT003_ket_cost();
			target_cost = dto.getT003_target_cost();
			actual_cost_1 = dto.getT003_actual_cost_1();
			earn_rate_1 = dto.getT003_earn_rate_1();
			actual_cost_2 = dto.getT003_actual_cost_2();
			earn_rate_2 = dto.getT003_earn_rate_2();
			actual_cost_3 = dto.getT003_actual_cost_3();
			earn_rate_3 = dto.getT003_earn_rate_3();
			case_1_note = dto.getT003_case_1_note();
			case_2_note = dto.getT003_case_2_note();
			case_3_note = dto.getT003_case_3_note();
		    }

		    sb.append(" SELECT productinfo_pk_pid.nextval as pk_pid, request_pk_cr.nextval as pk_cr");
		    sb.append("   FROM DUAL");

		    try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
			    pk_pid = rs.getString("pk_pid");
			    pk_cr = rs.getString("pk_cr");
			}
		    } catch (Exception e) {
			throw e;
		    } finally {
			sb.delete(0, sb.length());
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		    }

		    sb.append(" insert into COST_PRODUCTINFO (pk_pid,      pk_cr_group, rev_no,      group_no, case_count_1, case_count_2, table_row, ");
		    sb.append("                               pjt_no,      pjt_name,    team,        f_name,   a_name,       dev_step,     dr_step,   ");
		    sb.append("                               sub_step,    request_txt, pro_type,    make,     part_name,    part_type,    mix_group, ");
		    sb.append("                               part_no,     net_1,       net_2,       net_3,    die_no,       part_note,    data_type, report_pk, customer_f, car_type, app_part, w_name");
		    sb.append(" ) values (                                                                                ");
		    sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		    sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		    sb.append("           ?,?,?,?,?,?,?,                                                                            ");
		    sb.append("           ?,?,?,?,?,?,?,?,?,?,?,?  )                                                                          ");
		    try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, pk_pid);
			pstmt.setString(2, pjt_no);
			pstmt.setString(3, rev_no);
			pstmt.setString(4, group_no);
			pstmt.setString(5, "");
			pstmt.setString(6, "");
			pstmt.setString(7, table_row);
			pstmt.setString(8, pjt_no);
			pstmt.setString(9, pjt_name);
			pstmt.setString(10, team);
			pstmt.setString(11, f_name);
			pstmt.setString(12, a_name);
			pstmt.setString(13, dev_step);
			pstmt.setString(14, dr_step);
			pstmt.setString(15, "");
			pstmt.setString(16, request_txt);
			pstmt.setString(17, "");
			pstmt.setString(18, "");
			pstmt.setString(19, part_name);
			pstmt.setString(20, "");
			pstmt.setString(21, "");
			pstmt.setString(22, "");
			pstmt.setString(23, "");
			pstmt.setString(24, "");
			pstmt.setString(25, "");
			pstmt.setString(26, "");
			pstmt.setString(27, "");
			pstmt.setString(28, "main");
			pstmt.setString(29, report_pk);
			pstmt.setString(30, customer_f);
			pstmt.setString(31, car_type);
			pstmt.setString(32, app_part);
			pstmt.setString(33, w_name);

			pstmt.executeUpdate();

		    } catch (Exception e) {
			throw e;
		    } finally {
			sb.delete(0, sb.length());
			DBUtil.close(pstmt);
		    }

		    sb.append(" insert into cost_request (usage,         meterial,   t_size,      w_size,       p_size,      plating,      m_maker,     m_mone,      unitcost,  ");
		    sb.append("                           unitcost_txt,  grade_name, grade_color, order_con,    h_weight,    h_scrap,      t_weight,    recycle,     cavity,    ");
		    sb.append("                           m_su,          mold_cost,  mold_c_type, make_type,    pro_1,       ton,          spm,         method_type, pro_level, ");
		    sb.append("                           pro_level_txt, line_su,    sul_cost,    plating_type, type_2,      plating_cost, type_1,      t_mone,      type_cost, ");
		    sb.append("                           t_order,       pack_type,  in_pack,     out_pack,     store,       dest,         dis_cost,    distri_cost, royalty,   ");
		    sb.append("                           yazaki_ro,     etc_cost,   fk_pid,      pk_cr,        request_day, fk_wid     ");
		    sb.append("        ) values (                                                                                                                               ");
		    sb.append(" 	   ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                        		");
		    sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		    sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		    sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		    sb.append("        ?, ?, ?, ?, ?, ?, ?, ?, ?,                                                                                                    			");
		    sb.append("        ?, ?, ?, ? ,sysdate, ?                                  )																			");

		    try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, "");
			pstmt.setString(2, "");
			pstmt.setString(3, "");
			pstmt.setString(4, "");
			pstmt.setString(5, "");
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setString(8, "");
			pstmt.setString(9, "");
			pstmt.setString(10, "");
			pstmt.setString(11, "");
			pstmt.setString(12, "");
			pstmt.setString(13, "");
			pstmt.setString(14, "");
			pstmt.setString(15, "");
			pstmt.setString(16, "");
			pstmt.setString(17, "");
			pstmt.setString(18, "");
			pstmt.setString(19, "");
			pstmt.setString(20, "");
			pstmt.setString(21, "");
			pstmt.setString(22, "");
			pstmt.setString(23, "");
			pstmt.setString(24, "");
			pstmt.setString(25, "");
			pstmt.setString(26, "");
			pstmt.setString(27, "");
			pstmt.setString(28, "");
			pstmt.setString(29, "");
			pstmt.setString(30, "");
			pstmt.setString(31, "");
			pstmt.setString(32, "");
			pstmt.setString(33, "");
			pstmt.setString(34, "");
			pstmt.setString(35, "");
			pstmt.setString(36, "");
			pstmt.setString(37, "");
			pstmt.setString(38, "");
			pstmt.setString(39, "");
			pstmt.setString(40, "");
			pstmt.setString(41, "");
			pstmt.setString(42, "");
			pstmt.setString(43, "");
			pstmt.setString(44, "");
			pstmt.setString(45, "");
			pstmt.setString(46, "");
			pstmt.setString(47, "");
			pstmt.setString(48, pk_pid);
			pstmt.setString(49, pk_cr);
			pstmt.setString(50, pk_wid);

			pstmt.executeUpdate();

		    } catch (Exception e) {
			throw e;
		    } finally {
			sb.delete(0, sb.length());
			DBUtil.close(pstmt);
		    }

		    if ("T001".equals(group_no)) {
			sb.append("insert into wfinfo (  						  			               ");
			sb.append("       pk_wid, fk_cost, cost_flag, f_name, step_no, approval,     f_day  ) ");
			sb.append("values(?,      ?,       'R',       ?,      '6',     'complete',     sysdate) ");

			try {
			    pstmt = conn.prepareStatement(sb.toString());
			    pstmt.setString(1, pk_wid);
			    pstmt.setString(2, pk_cr);
			    pstmt.setString(3, f_name);

			    pstmt.executeUpdate();
			} catch (Exception e) {
			    throw e;
			} finally {
			    sb.delete(0, sb.length());
			    DBUtil.close(pstmt);
			}
		    }

		    sb.append("INSERT INTO COST_REPORT (								                        ");
		    sb.append("		  PK_CRP, FK_PID,  FK_WID,  CRP_GROUP,  GROUP_NO,  report_dest,		       ");
		    sb.append("	case_to_note_1,  case_to_note_2,   case_to_note_3,     mold_count,      press_count,                ");
		    sb.append("	line_count,          pack_count,         repack_count,        mold_total_1,    mold_total_2,        ");
		    sb.append("	mold_total_3,       press_total_1,      press_total_2,        press_total_3,   line_total_1,        ");
		    sb.append("	line_total_2,         line_total_3,        pack_total_1,         pack_total_2,    pack_total_3,     ");
		    sb.append("	repack_total_1,     repack_total_2,   repack_total_3,       m_depr_stan,    p_depr_stan,            ");
		    sb.append("	l_depr_stan,         pack_depr_stan,  repack_depr_stan,  tocost_year,     su_year_1,                ");
		    sb.append("	su_year_2,           su_year_3,         su_year_4,           su_year_5,        su_year_6,           ");
		    sb.append("	su_year_7,           su_year_8,         su_stan_day,        total_sales_1,    total_sales_2,                  ");
		    sb.append("	total_sales_3,       total_sales_4,     total_sales_5,       total_sales_6,    total_sales_7,                 ");
		    sb.append("	total_sales_8,       profit_1,             profit_2,              profit_3,            profit_4,              ");
		    sb.append("	profit_5,              profit_6,             profit_7,              profit_8,             per_profit_1,       ");
		    sb.append("	per_profit_2,        per_profit_3,       per_profit_4,         per_profit_5,       per_profit_6,              ");
		    sb.append("	per_profit_7,        per_profit_8,       pack_type,            pro_1,               eff_value,                ");
		    sb.append("	pro_usage,           ket_cost,           target_cost,          actual_cost_1,    earn_rate_1,                 ");
		    sb.append("	actual_cost_2,      earn_rate_2,       actual_cost_3,       earn_rate_3,      note_1,                         ");
		    sb.append("	note_2,                note_3,              note_4 ,                                                           ");
		    sb.append("	actual_cost_sum_1,  actual_cost_sum_2,  actual_cost_sum_3,  earn_rate_sum_1,  earn_rate_sum_2,  earn_rate_sum_3,    ");
		    sb.append("	lme_cu, u_ex_rate,  select_cost,        input_gb,           usage, division,  case_1_note,      case_2_note,     case_3_note  )  ");
		    sb.append("	VALUES (                                                                        ");
		    if ("T001".equals(group_no)) {
			sb.append("	  ?,      ?,       ?,       ?,          ?,         ?,                                   ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("? , ? , ? , ? , ? , ");
			sb.append("? , ? , ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ? , ");
			sb.append("?, ? , ?, ? , ?,");
			sb.append("?, ?, ?,");
			sb.append("?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?");
		    } else {
			sb.append("   cost_report_pk_crp.nextVal,  ?,  ?,  ?,  ?, ?,                           ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ? , ");
			sb.append("? , ? , ? , ? , ? , ");
			sb.append("? , ? , ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ? , ");
			sb.append("?, ? , ?, ? , ?,");
			sb.append("?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?");
		    }
		    sb.append("         )                                                                               ");

		    try {
			pstmt = conn.prepareStatement(sb.toString());
			if ("T001".equals(group_no)) {
			    pstmt.setString(1, report_pk);
			    pstmt.setString(2, pk_pid);
			    pstmt.setString(3, pk_wid);
			    pstmt.setString(4, report_pk);
			    pstmt.setString(5, group_no);
			    pstmt.setString(6, report_dest);

			    pstmt.setString(7, case_to_note_1);
			    pstmt.setString(8, case_to_note_2);
			    pstmt.setString(9, case_to_note_3);
			    pstmt.setString(10, mold_count);
			    pstmt.setString(11, press_count);
			    pstmt.setString(12, line_count);
			    pstmt.setString(13, pack_count);
			    pstmt.setString(14, repack_count);
			    pstmt.setString(15, mold_total_1);
			    pstmt.setString(16, mold_total_2);
			    pstmt.setString(17, mold_total_3);
			    pstmt.setString(18, press_total_1);
			    pstmt.setString(19, press_total_2);
			    pstmt.setString(20, press_total_3);
			    pstmt.setString(21, line_total_1);
			    pstmt.setString(22, line_total_2);
			    pstmt.setString(23, line_total_3);
			    pstmt.setString(24, pack_total_1);
			    pstmt.setString(25, pack_total_2);
			    pstmt.setString(26, pack_total_3);
			    pstmt.setString(27, repack_total_1);
			    pstmt.setString(28, repack_total_2);
			    pstmt.setString(29, repack_total_3);
			    pstmt.setString(30, m_depr_stan);
			    pstmt.setString(31, p_depr_stan);
			    pstmt.setString(32, l_depr_stan);
			    pstmt.setString(33, pack_depr_stan);
			    pstmt.setString(34, repack_depr_stan);
			    pstmt.setString(35, tocost_year);
			    pstmt.setString(36, su_year_1);
			    pstmt.setString(37, su_year_2);
			    pstmt.setString(38, su_year_3);
			    pstmt.setString(39, su_year_4);
			    pstmt.setString(40, su_year_5);
			    pstmt.setString(41, su_year_6);
			    pstmt.setString(42, su_year_7);
			    pstmt.setString(43, su_year_8);
			    pstmt.setString(44, su_stan_day);
			    pstmt.setString(45, total_sales_1);
			    pstmt.setString(46, total_sales_2);
			    pstmt.setString(47, total_sales_3);
			    pstmt.setString(48, total_sales_4);
			    pstmt.setString(49, total_sales_5);
			    pstmt.setString(50, total_sales_6);
			    pstmt.setString(51, total_sales_7);
			    pstmt.setString(52, total_sales_8);
			    pstmt.setString(53, profit_1);
			    pstmt.setString(54, profit_2);
			    pstmt.setString(55, profit_3);
			    pstmt.setString(56, profit_4);
			    pstmt.setString(57, profit_5);
			    pstmt.setString(58, profit_6);
			    pstmt.setString(59, profit_7);
			    pstmt.setString(60, profit_8);
			    pstmt.setString(61, per_profit_1);
			    pstmt.setString(62, per_profit_2);
			    pstmt.setString(63, per_profit_3);
			    pstmt.setString(64, per_profit_4);
			    pstmt.setString(65, per_profit_5);
			    pstmt.setString(66, per_profit_6);
			    pstmt.setString(67, per_profit_7);
			    pstmt.setString(68, per_profit_8);
			    pstmt.setString(69, pack_type);
			    pstmt.setString(70, pro_1);
			    pstmt.setString(71, eff_value);
			    pstmt.setString(72, pro_usage);
			    pstmt.setString(73, ket_cost);
			    pstmt.setString(74, target_cost);
			    pstmt.setString(75, actual_cost_1);
			    pstmt.setString(76, earn_rate_1);
			    pstmt.setString(77, actual_cost_2);
			    pstmt.setString(78, earn_rate_2);
			    pstmt.setString(79, actual_cost_3);
			    pstmt.setString(80, earn_rate_3);
			    pstmt.setString(81, note_1);
			    pstmt.setString(82, note_2);
			    pstmt.setString(83, note_3);
			    pstmt.setString(84, note_4);
			    pstmt.setString(85, actual_cost_sum_1);
			    pstmt.setString(86, actual_cost_sum_2);
			    pstmt.setString(87, actual_cost_sum_3);
			    pstmt.setString(88, earn_rate_sum_1);
			    pstmt.setString(89, earn_rate_sum_2);
			    pstmt.setString(90, earn_rate_sum_3);
			    pstmt.setString(91, lme_cu);
			    pstmt.setString(92, u_ex_rate);
			    pstmt.setString(93, select_cost);
			    pstmt.setString(94, "1");
			    pstmt.setString(95, pro_usage);
			    pstmt.setString(96, division);
			    pstmt.setString(97, case_1_note);
			    pstmt.setString(98, case_2_note);
			    pstmt.setString(99, case_3_note);

			} else {
			    pstmt.setString(1, pk_pid);
			    pstmt.setString(2, pk_wid);
			    pstmt.setString(3, report_pk);
			    pstmt.setString(4, group_no);
			    pstmt.setString(5, report_dest);
			    pstmt.setString(6, case_to_note_1);
			    pstmt.setString(7, case_to_note_2);
			    pstmt.setString(8, case_to_note_3);
			    pstmt.setString(9, mold_count);
			    pstmt.setString(10, press_count);
			    pstmt.setString(11, line_count);
			    pstmt.setString(12, pack_count);
			    pstmt.setString(13, repack_count);
			    pstmt.setString(14, mold_total_1);
			    pstmt.setString(15, mold_total_2);
			    pstmt.setString(16, mold_total_3);
			    pstmt.setString(17, press_total_1);
			    pstmt.setString(18, press_total_2);
			    pstmt.setString(19, press_total_3);
			    pstmt.setString(20, line_total_1);
			    pstmt.setString(21, line_total_2);
			    pstmt.setString(22, line_total_3);
			    pstmt.setString(23, pack_total_1);
			    pstmt.setString(24, pack_total_2);
			    pstmt.setString(25, pack_total_3);
			    pstmt.setString(26, repack_total_1);
			    pstmt.setString(27, repack_total_2);
			    pstmt.setString(28, repack_total_3);
			    pstmt.setString(29, m_depr_stan);
			    pstmt.setString(30, p_depr_stan);
			    pstmt.setString(31, l_depr_stan);
			    pstmt.setString(32, pack_depr_stan);
			    pstmt.setString(33, repack_depr_stan);
			    pstmt.setString(34, tocost_year);
			    pstmt.setString(35, su_year_1);
			    pstmt.setString(36, su_year_2);
			    pstmt.setString(37, su_year_3);
			    pstmt.setString(38, su_year_4);
			    pstmt.setString(39, su_year_5);
			    pstmt.setString(40, su_year_6);
			    pstmt.setString(41, su_year_7);
			    pstmt.setString(42, su_year_8);
			    pstmt.setString(43, su_stan_day);
			    pstmt.setString(44, total_sales_1);
			    pstmt.setString(45, total_sales_2);
			    pstmt.setString(46, total_sales_3);
			    pstmt.setString(47, total_sales_4);
			    pstmt.setString(48, total_sales_5);
			    pstmt.setString(49, total_sales_6);
			    pstmt.setString(50, total_sales_7);
			    pstmt.setString(51, total_sales_8);
			    pstmt.setString(52, profit_1);
			    pstmt.setString(53, profit_2);
			    pstmt.setString(54, profit_3);
			    pstmt.setString(55, profit_4);
			    pstmt.setString(56, profit_5);
			    pstmt.setString(57, profit_6);
			    pstmt.setString(58, profit_7);
			    pstmt.setString(59, profit_8);
			    pstmt.setString(60, per_profit_1);
			    pstmt.setString(61, per_profit_2);
			    pstmt.setString(62, per_profit_3);
			    pstmt.setString(63, per_profit_4);
			    pstmt.setString(64, per_profit_5);
			    pstmt.setString(65, per_profit_6);
			    pstmt.setString(66, per_profit_7);
			    pstmt.setString(67, per_profit_8);
			    pstmt.setString(68, pack_type);
			    pstmt.setString(69, pro_1);
			    pstmt.setString(70, eff_value);
			    pstmt.setString(71, pro_usage);
			    pstmt.setString(72, ket_cost);
			    pstmt.setString(73, target_cost);
			    pstmt.setString(74, actual_cost_1);
			    pstmt.setString(75, earn_rate_1);
			    pstmt.setString(76, actual_cost_2);
			    pstmt.setString(77, earn_rate_2);
			    pstmt.setString(78, actual_cost_3);
			    pstmt.setString(79, earn_rate_3);
			    pstmt.setString(80, note_1);
			    pstmt.setString(81, note_2);
			    pstmt.setString(82, note_3);
			    pstmt.setString(83, note_4);
			    pstmt.setString(84, actual_cost_sum_1);
			    pstmt.setString(85, actual_cost_sum_2);
			    pstmt.setString(86, actual_cost_sum_3);
			    pstmt.setString(87, earn_rate_sum_1);
			    pstmt.setString(88, earn_rate_sum_2);
			    pstmt.setString(89, earn_rate_sum_3);
			    pstmt.setString(90, lme_cu);
			    pstmt.setString(91, u_ex_rate);
			    pstmt.setString(92, select_cost);
			    pstmt.setString(93, "1");
			    pstmt.setString(94, pro_usage);
			    pstmt.setString(95, division);
			    pstmt.setString(96, case_1_note);
			    pstmt.setString(97, case_2_note);
			    pstmt.setString(98, case_3_note);
			}

			pstmt.executeUpdate();
		    } catch (Exception e) {
			throw e;
		    } finally {
			sb.delete(0, sb.length());
			DBUtil.close(pstmt);
		    }
		}
		UpdateReportSum(report_pk); // 총원가, 수익율 sum 하여 update
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sb.delete(0, sb.length());
	    DBUtil.close(pstmt);
	}
	return report_pk;
    }

}