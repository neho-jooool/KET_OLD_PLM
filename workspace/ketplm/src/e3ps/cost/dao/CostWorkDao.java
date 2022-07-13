package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.StringUtil;

public class CostWorkDao {

    private Connection conn;

    public CostWorkDao(Connection conn) {
	this.conn = conn;
    }

    public CostWorkDao() {
	super();
    }

    /**
     * 함수명 : getWorkSearch3List 설명 : 작업창 Search3 List 호출
     * 
     * @param String
     *            group_no, String pkcr_group, String rev_no, String data_type
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 19.
     */
    public ArrayList getWorkSearch3List(String group_no, String pkcr_group, String rev_no, String data_type, String mode_detail) throws Exception {
	ArrayList<Hashtable<String, String>> search3List = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> search3Hash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT B.group_no, A.m_co_chk, B.case_count_1, B.case_count_2, A.pk_cw, B.pk_cr_group, \n");
	sb.append("    B.make, B. part_no, B.part_name, B.net_1, B.net_2, B.net_3, A.usage, A.meterial, \n");
	sb.append("    A.t_size, A.w_size, A.p_size, A.plating, A.m_maker, m_mone, A.unitcost as unitcost, \n");
	sb.append("    A.grade_name, A.grade_color, A.order_con, A.h_weight, A.h_scrap, A.t_weight, A.recycle_state, \n");
	sb.append("    A.recycle, B.die_no, A.cavity, A.m_su, A.mold_cost, A.mold_c_type, A.make_type, A.pro_1, A.ton, A.spm, A.method_type, \n");
	sb.append("    A.pro_level, A.pro_level_txt, A.line_su, A.sul_cost, A.plating_type, A.type_2,  A.type_1, A.plating_cost, A.type_2, A.t_mone, \n");
	sb.append("    A.type_cost, A.t_order, A.pack_type, A.in_pack, A.out_pack, A.distri_cost, A.etc_cost, A.yazaki_ro, B.part_note, \n");
	sb.append("    B.car_type, A.p_su_chk, A.su_year_1, A.su_year_2, A.su_year_3, A.su_year_4, A.su_year_5, A.su_year_6, A.su_year_7, A.su_year_8, \n");
	sb.append("    A.p_su_year_1, A.p_su_year_2, A.p_su_year_3, A.p_su_year_4, A.p_su_year_5, A.p_su_year_6, A.p_su_year_7, A.p_su_year_8, \n");
	sb.append("    A.client_cost, A.ket_cost, A.target_cost, A.store, A.dest, A.royalty, A.information, A.USD_rate, A.EURO_rate, A.YEN_rate, A.CNY_rate, \n");
	sb.append("    B.pro_type, B.part_type, B.mix_group, A.lme_ton, A.u_ex_rate, A.y_ex_rate, A.cost_report_Add, replace(A.file_1,'\\','/') file_1 \n");
	sb.append(" FROM cost_work A, cost_productInfo B \n");
	if ("work_case".equals(mode_detail)) {
	    sb.append(" WHERE substr(A.group_no, 0, 4) = ? AND A.pk_cr_group = ? AND A.rev_no = ? AND A.case_type_admin = ? AND B.pk_pid = A.fk_pid and a.case_type_user = '-' ORDER BY A.group_no \n");
	} else {
	    sb.append(" WHERE substr(A.group_no, 0, 4) = ? AND A.pk_cr_group = ? AND A.rev_no = ? AND A.case_type_user = ? AND B.pk_pid = A.fk_pid and a.case_type_user != '-' ORDER BY A.group_no \n");
	}
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, group_no);
	    pstmt.setString(2, pkcr_group);
	    pstmt.setInt(3, Integer.parseInt(rev_no));
	    pstmt.setString(4, data_type);
	    System.out.println("group_no : " + group_no);
	    System.out.println("pkcr_group : " + pkcr_group);
	    System.out.println("rev_no : " + rev_no);
	    System.out.println("data_type : " + data_type);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		search3Hash = new Hashtable<String, String>();
		search3Hash.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		System.out.println("group_no : " + StringUtil.checkNull(rs.getString("group_no")));
		search3Hash.put("m_co_chk", StringUtil.checkNullZero(rs.getString("m_co_chk")));
		search3Hash.put("case_count_1", StringUtil.checkNull(rs.getString("case_count_1")));
		search3Hash.put("case_count_2", StringUtil.checkNull(rs.getString("case_count_2")));
		search3Hash.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		search3Hash.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		search3Hash.put("make", StringUtil.checkNull(rs.getString("make")));
		search3Hash.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		search3Hash.put("part_no", StringUtil.checkNull(rs.getString("part_no")));
		search3Hash.put("net_1", StringUtil.checkNull(rs.getString("net_1")));
		search3Hash.put("net_2", StringUtil.checkNull(rs.getString("net_2")));
		search3Hash.put("net_3", StringUtil.checkNull(rs.getString("net_3")));
		search3Hash.put("usage", StringUtil.checkNull(rs.getString("usage")));
		search3Hash.put("meterial", StringUtil.checkNull(rs.getString("meterial")));
		search3Hash.put("t_size", StringUtil.checkNull(rs.getString("t_size")));
		search3Hash.put("w_size", StringUtil.checkNull(rs.getString("w_size")));
		search3Hash.put("p_size", StringUtil.checkNull(rs.getString("p_size")));
		search3Hash.put("plating", StringUtil.checkNull(rs.getString("plating")));
		search3Hash.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		search3Hash.put("m_mone", StringUtil.checkNull(rs.getString("m_mone")));
		search3Hash.put("unitcost", StringUtil.checkNull(rs.getString("unitcost")));
		search3Hash.put("c_unitcostr", StringUtil.checkNull(rs.getString("unitcost")));
		search3Hash.put("grade_name", StringUtil.checkNull(rs.getString("grade_name")));
		search3Hash.put("grade_color", StringUtil.checkNull(rs.getString("grade_color")));
		search3Hash.put("order_con", StringUtil.checkNull(rs.getString("order_con")));
		search3Hash.put("h_weight", StringUtil.checkNull(rs.getString("h_weight")));
		search3Hash.put("h_scrap", StringUtil.checkNull(rs.getString("h_scrap")));
		search3Hash.put("t_weight", StringUtil.checkNull(rs.getString("t_weight")));
		search3Hash.put("recycle", StringUtil.checkNull(rs.getString("recycle")));
		search3Hash.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		search3Hash.put("cavity", StringUtil.checkNull(rs.getString("cavity")));
		search3Hash.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		search3Hash.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		search3Hash.put("ton", StringUtil.checkNull(rs.getString("ton")));
		search3Hash.put("spm", StringUtil.checkNull(rs.getString("spm")));
		search3Hash.put("pro_level_txt", StringUtil.checkNull(rs.getString("pro_level_txt")));
		search3Hash.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		search3Hash.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		search3Hash.put("plating_cost", StringUtil.checkNull(rs.getString("plating_cost")));
		search3Hash.put("type_cost", StringUtil.checkNull(rs.getString("type_cost")));
		search3Hash.put("in_pack", StringUtil.checkNull(rs.getString("in_pack")));
		search3Hash.put("out_pack", StringUtil.checkNull(rs.getString("out_pack")));
		search3Hash.put("distri_cost", StringUtil.checkNull(rs.getString("distri_cost")));
		search3Hash.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		search3Hash.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		search3Hash.put("part_note", StringUtil.checkNull(rs.getString("part_note")));
		search3Hash.put("car_type", StringUtil.checkNull(rs.getString("car_type")));
		search3Hash.put("p_su_chk", StringUtil.checkNullZero(rs.getString("p_su_chk")));
		search3Hash.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		search3Hash.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		search3Hash.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		search3Hash.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		search3Hash.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		search3Hash.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		search3Hash.put("su_year_7", StringUtil.checkNull(rs.getString("su_year_7")));
		search3Hash.put("su_year_8", StringUtil.checkNull(rs.getString("su_year_8")));
		search3Hash.put("p_su_year_1", StringUtil.checkNull(rs.getString("p_su_year_1")));
		search3Hash.put("p_su_year_2", StringUtil.checkNull(rs.getString("p_su_year_2")));
		search3Hash.put("p_su_year_3", StringUtil.checkNull(rs.getString("p_su_year_3")));
		search3Hash.put("p_su_year_4", StringUtil.checkNull(rs.getString("p_su_year_4")));
		search3Hash.put("p_su_year_5", StringUtil.checkNull(rs.getString("p_su_year_5")));
		search3Hash.put("p_su_year_6", StringUtil.checkNull(rs.getString("p_su_year_6")));
		search3Hash.put("p_su_year_7", StringUtil.checkNull(rs.getString("p_su_year_7")));
		search3Hash.put("p_su_year_8", StringUtil.checkNull(rs.getString("p_su_year_8")));
		search3Hash.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		search3Hash.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		search3Hash.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		search3Hash.put("store", StringUtil.checkNull(rs.getString("store")));
		search3Hash.put("dest", StringUtil.checkNull(rs.getString("dest")));
		search3Hash.put("royalty", StringUtil.checkNull(rs.getString("royalty")));
		search3Hash.put("information", StringUtil.checkNull(rs.getString("information")));
		search3Hash.put("USD_rate", StringUtil.checkNull(rs.getString("USD_rate")));
		search3Hash.put("EURO_rate", StringUtil.checkNull(rs.getString("EURO_rate")));
		search3Hash.put("YEN_rate", StringUtil.checkNull(rs.getString("YEN_rate")));
		search3Hash.put("CNY_rate", StringUtil.checkNull(rs.getString("CNY_rate")));
		search3Hash.put("lme_ton", StringUtil.checkNull(rs.getString("lme_ton")));
		search3Hash.put("u_ex_rate", StringUtil.checkNull(rs.getString("u_ex_rate")));
		search3Hash.put("y_ex_rate", StringUtil.checkNull(rs.getString("y_ex_rate")));
		search3Hash.put("cost_report_add", StringUtil.checkNull(rs.getString("cost_report_add")));
		search3Hash.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		search3Hash.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		search3Hash.put("mix_group", StringUtil.checkNull(rs.getString("mix_group")));
		search3Hash.put("recycle_state", StringUtil.checkNull(rs.getString("recycle_state")));
		search3Hash.put("mold_c_type", StringUtil.checkNull(rs.getString("mold_c_type")));
		search3Hash.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		search3Hash.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		search3Hash.put("method_type", StringUtil.checkNull(rs.getString("method_type")));
		search3Hash.put("pro_level", StringUtil.checkNull(rs.getString("pro_level")));
		search3Hash.put("plating_type", StringUtil.checkNull(rs.getString("plating_type")));
		search3Hash.put("type_2", StringUtil.checkNull(rs.getString("type_2")));
		search3Hash.put("type_1", StringUtil.checkNull(rs.getString("type_1")));
		search3Hash.put("t_mone", StringUtil.checkNull(rs.getString("t_mone")));
		search3Hash.put("t_order", StringUtil.checkNull(rs.getString("t_order")));
		search3Hash.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		search3Hash.put("file_1", StringUtil.checkNull(rs.getString("file_1")));
		search3List.add(search3Hash);
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
	return search3List;
    }

    /**
     * 함수명 : getWorkSearch4List 설명 : 작업창 Search4 List 호출
     * 
     * @param String
     *            group_no, String pkcr_group, String rev_no, String data_type
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 21.
     */
    public ArrayList getWorkSearch4List(String group_no, String pkcr_group, String rev_no, String data_type, String mode_detail) throws Exception {
	ArrayList<Hashtable<String, String>> search4List = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> search4Hash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT A.pk_cw, A.pk_cr_group, A.group_no, A.pro_type, A.part_name, A.usage, A.meterial_cost, A.loss, \n");
	sb.append("    A.scrap_cost, A.scrap_rate, A.m_total_cost, A.output, A.rate, A.eff_value, A.labor_cost, A.jun_cost, A.ma_depr, \n");
	sb.append("    A.tabalu, A.m_upkeep, A.etc_expense, A.pack_cost, A.type_cost, A.total_expense, A.overhead, A.gita_cost, A.common_cost, \n");
	sb.append("    A.yzk_depr, A.mold_type, A.start_depr, A.pro_depr, A.etc_depr, A.depr_cost, A.jae_cost, A.ge_cost, A.ad_cost, A.rnd_cost,  \n");
	sb.append("    A.qu_cost, A.tariff, A.royalty_cost, A.dis_cost, A.actual_cost, A.earn_rate, \n");
	sb.append("    A.pl_meterial_cost, 	A.pl_loss, A.pl_scrap_cost, A.pl_m_total_cost, A.pl_output, A.pl_rate, A.pl_labor_cost, A.pl_jun_cost, A.pl_ma_depr, \n");
	sb.append("    A.pl_tabalu, A.pl_m_upkeep, A.pl_total_expense, A.pl_overhead, A.pl_common_cost, A.pl_yzk_depr, A.pl_start_depr, A.pl_pro_depr, A.pl_etc_depr, \n");
	sb.append("    A.pl_jae_cost, A.pl_ge_cost, A.pl_rnd_cost, A.pl_tariff, A.pl_royalty_cost, A.pl_actual_cost \n");
	sb.append(" FROM cost_work A \n");

	if ("work_case".equals(mode_detail)) {
	    sb.append(" WHERE substr(A.group_no, 0, 4) = ? AND A.pk_cr_group = ? AND A.rev_no = ? AND A.case_type_admin = ? and a.case_type_user = '-' ORDER BY A.group_no \n");
	} else {
	    sb.append(" WHERE substr(A.group_no, 0, 4) = ? AND A.pk_cr_group = ? AND A.rev_no = ? AND A.case_type_user = ?  and a.case_type_user != '-'  ORDER BY A.group_no \n");
	}

	System.out.println("group_no : " + group_no);
	System.out.println("pkcr_group : " + pkcr_group);
	System.out.println("rev_no : " + rev_no);
	System.out.println("data_type : " + data_type);
	System.out.println("mode_detail : " + mode_detail);

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, group_no);
	    pstmt.setString(2, pkcr_group);
	    pstmt.setInt(3, Integer.parseInt(rev_no));
	    pstmt.setString(4, data_type);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		search4Hash = new Hashtable<String, String>();
		search4Hash.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		search4Hash.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		search4Hash.put("group_no", StringUtil.checkNull(rs.getString("group_no")));
		search4Hash.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		search4Hash.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		search4Hash.put("usage", StringUtil.checkNull(rs.getString("usage")));
		search4Hash.put("meterial_cost", StringUtil.checkNull(rs.getString("meterial_cost")));
		search4Hash.put("loss", StringUtil.checkNull(rs.getString("loss")));
		search4Hash.put("scrap_cost", StringUtil.checkNull(rs.getString("scrap_cost")));
		search4Hash.put("scrap_rate", StringUtil.checkNull(rs.getString("scrap_rate")));
		search4Hash.put("m_total_cost", StringUtil.checkNull(rs.getString("m_total_cost")));
		search4Hash.put("output", StringUtil.checkNull(rs.getString("output")));
		search4Hash.put("rate", StringUtil.checkNull(rs.getString("rate")));
		search4Hash.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		search4Hash.put("labor_cost", StringUtil.checkNull(rs.getString("labor_cost")));
		search4Hash.put("jun_cost", StringUtil.checkNull(rs.getString("jun_cost")));
		search4Hash.put("ma_depr", StringUtil.checkNull(rs.getString("ma_depr")));
		search4Hash.put("tabalu", StringUtil.checkNull(rs.getString("tabalu")));
		search4Hash.put("m_upkeep", StringUtil.checkNull(rs.getString("m_upkeep")));
		search4Hash.put("etc_expense", StringUtil.checkNull(rs.getString("etc_expense")));
		search4Hash.put("pack_cost", StringUtil.checkNull(rs.getString("pack_cost")));
		search4Hash.put("type_cost", StringUtil.checkNull(rs.getString("type_cost")));
		search4Hash.put("total_expense", StringUtil.checkNull(rs.getString("total_expense")));
		search4Hash.put("overhead", StringUtil.checkNull(rs.getString("overhead")));
		search4Hash.put("gita_cost", StringUtil.checkNull(rs.getString("gita_cost")));
		search4Hash.put("common_cost", StringUtil.checkNull(rs.getString("common_cost")));
		search4Hash.put("yzk_depr", StringUtil.checkNull(rs.getString("yzk_depr")));
		search4Hash.put("mold_type", StringUtil.checkNull(rs.getString("mold_type")));
		search4Hash.put("start_depr", StringUtil.checkNull(rs.getString("start_depr")));
		search4Hash.put("pro_depr", StringUtil.checkNull(rs.getString("pro_depr")));
		search4Hash.put("etc_depr", StringUtil.checkNull(rs.getString("etc_depr")));
		search4Hash.put("depr_cost", StringUtil.checkNull(rs.getString("depr_cost")));
		search4Hash.put("jae_cost", StringUtil.checkNull(rs.getString("jae_cost")));
		search4Hash.put("ge_cost", StringUtil.checkNull(rs.getString("ge_cost")));
		search4Hash.put("ad_cost", StringUtil.checkNull(rs.getString("ad_cost")));
		search4Hash.put("rnd_cost", StringUtil.checkNull(rs.getString("rnd_cost")));
		search4Hash.put("qu_cost", StringUtil.checkNull(rs.getString("qu_cost")));
		search4Hash.put("tariff", StringUtil.checkNull(rs.getString("tariff")));
		search4Hash.put("royalty_cost", StringUtil.checkNull(rs.getString("royalty_cost")));
		search4Hash.put("dis_cost", StringUtil.checkNull(rs.getString("dis_cost")));
		search4Hash.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		search4Hash.put("earn_rate", StringUtil.checkNull(rs.getString("earn_rate")));

		search4Hash.put("pl_meterial_cost", StringUtil.checkNull(rs.getString("pl_meterial_cost")));
		search4Hash.put("pl_loss", StringUtil.checkNull(rs.getString("pl_loss")));
		search4Hash.put("pl_scrap_cost", StringUtil.checkNull(rs.getString("pl_scrap_cost")));
		search4Hash.put("pl_m_total_cost", StringUtil.checkNull(rs.getString("pl_m_total_cost")));
		search4Hash.put("pl_output", StringUtil.checkNull(rs.getString("pl_output")));
		search4Hash.put("pl_rate", StringUtil.checkNull(rs.getString("pl_rate")));
		search4Hash.put("pl_labor_cost", StringUtil.checkNull(rs.getString("pl_labor_cost")));
		search4Hash.put("pl_jun_cost", StringUtil.checkNull(rs.getString("pl_jun_cost")));
		search4Hash.put("pl_ma_depr", StringUtil.checkNull(rs.getString("pl_ma_depr")));
		search4Hash.put("pl_tabalu", StringUtil.checkNull(rs.getString("pl_tabalu")));
		search4Hash.put("pl_m_upkeep", StringUtil.checkNull(rs.getString("pl_m_upkeep")));
		search4Hash.put("pl_total_expense", StringUtil.checkNull(rs.getString("pl_total_expense")));
		search4Hash.put("pl_overhead", StringUtil.checkNull(rs.getString("pl_overhead")));
		search4Hash.put("pl_common_cost", StringUtil.checkNull(rs.getString("pl_common_cost")));
		search4Hash.put("pl_yzk_depr", StringUtil.checkNull(rs.getString("pl_yzk_depr")));
		search4Hash.put("pl_start_depr", StringUtil.checkNull(rs.getString("pl_start_depr")));
		search4Hash.put("pl_pro_depr", StringUtil.checkNull(rs.getString("pl_pro_depr")));
		search4Hash.put("pl_etc_depr", StringUtil.checkNull(rs.getString("pl_etc_depr")));
		search4Hash.put("pl_jae_cost", StringUtil.checkNull(rs.getString("pl_jae_cost")));
		search4Hash.put("pl_ge_cost", StringUtil.checkNull(rs.getString("pl_ge_cost")));
		search4Hash.put("pl_rnd_cost", StringUtil.checkNull(rs.getString("pl_rnd_cost")));
		search4Hash.put("pl_tariff", StringUtil.checkNull(rs.getString("pl_tariff")));
		search4Hash.put("pl_royalty_cost", StringUtil.checkNull(rs.getString("pl_royalty_cost")));
		search4Hash.put("pl_actual_cost", StringUtil.checkNull(rs.getString("pl_actual_cost")));

		search4List.add(search4Hash);
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
	return search4List;
    }

    /**
     * 함수명 : getWorkSearch6List 설명 : 작업창 Search6 List 호출
     * 
     * @param String
     *            group_no, String pkcr_group, String rev_no, String data_type
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 21.
     */
    public ArrayList getWorkSearch6List(String group_no, String pkcr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> search6List = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> search6Hash = null;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT A.pk_cw, A.pk_cr_group, A.case_type_admin, A.part_name, A.actual_cost, A.earn_rate, A.case_infor, A.group_no");
	sb.append(" FROM cost_work A \n");
	sb.append(" WHERE length(A.group_no) = 4 AND A.pk_cr_group = ? AND A.rev_no = ? AND A.case_type_admin != 'main' and case_type_user='-' order by to_number(substr(A.case_type_admin,5)) \n");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pkcr_group);
	    pstmt.setInt(2, Integer.parseInt(rev_no));
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		search6Hash = new Hashtable<String, String>();
		search6Hash.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		search6Hash.put("pk_cr_group", StringUtil.checkNull(rs.getString("pk_cr_group")));
		search6Hash.put("case_type_admin", StringUtil.checkNull(rs.getString("case_type_admin")));
		search6Hash.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		search6Hash.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		search6Hash.put("earn_rate", StringUtil.checkNull(rs.getString("earn_rate")));
		search6Hash.put("case_infor", StringUtil.checkNull(rs.getString("case_infor")));
		search6Hash.put("group_no", StringUtil.checkNull(rs.getString("group_no")));

		search6List.add(search6Hash);
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
	return search6List;
    }

    /**
     * 함수명 : UpdateCostWork3 설명 : 작업창 저장3 호출
     * 
     * @param ArrayList
     *            SearchList
     * @return int
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 21.
     */
    public int UpdateCostWork3(ArrayList SearchList) throws Exception {

	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sbCostProductInfo = new StringBuffer();
	StringBuffer sbCostWork = new StringBuffer();
	int complet = 0;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    String m_co_chk = (String) SearchMap.get("m_co_chk");
	    String pk_cw = (String) SearchMap.get("pk_cw");
	    String pk_cr_group = (String) SearchMap.get("pk_cr_group");
	    String make = (String) SearchMap.get("make");
	    String group_no = (String) SearchMap.get("group_no");
	    String pro_type = (String) SearchMap.get("pro_type");
	    String part_name = (String) SearchMap.get("part_name");
	    String part_type = (String) SearchMap.get("part_type");
	    String mix_group = (String) SearchMap.get("mix_group");
	    String part_no = (String) SearchMap.get("part_no");
	    String net_1 = (String) SearchMap.get("net_1");
	    String net_2 = (String) SearchMap.get("net_2");
	    String net_3 = (String) SearchMap.get("net_3");
	    String usage = (String) SearchMap.get("usage");
	    String meterial = (String) SearchMap.get("meterial");
	    String t_size = (String) SearchMap.get("t_size");
	    String w_size = (String) SearchMap.get("w_size");
	    String p_size = (String) SearchMap.get("p_size");
	    String plating = (String) SearchMap.get("plating");
	    String m_maker = (String) SearchMap.get("m_maker");
	    String m_mone = (String) SearchMap.get("m_mone");
	    String unitcost = (String) SearchMap.get("unitcost");
	    String c_unitcost = (String) SearchMap.get("c_unitcost");
	    String grade_name = (String) SearchMap.get("grade_name");
	    String grade_color = (String) SearchMap.get("grade_color");
	    String order_con = (String) SearchMap.get("order_con");
	    String h_weight = (String) SearchMap.get("h_weight");
	    String h_scrap = (String) SearchMap.get("h_scrap");
	    String t_weight = (String) SearchMap.get("t_weight");
	    String recycle_state = (String) SearchMap.get("recycle_state");
	    String recycle = (String) SearchMap.get("recycle");
	    String die_no = (String) SearchMap.get("die_no");
	    String cavity = (String) SearchMap.get("cavity");
	    String m_su = (String) SearchMap.get("m_su");
	    String mold_cost = (String) SearchMap.get("mold_cost");
	    String mold_c_type = (String) SearchMap.get("mold_c_type");
	    String make_type = (String) SearchMap.get("make_type");
	    String pro_1 = (String) SearchMap.get("pro_1");
	    String ton = (String) SearchMap.get("ton");
	    String spm = (String) SearchMap.get("spm");
	    String method_type = (String) SearchMap.get("method_type");
	    String pro_level = (String) SearchMap.get("pro_level");
	    String pro_level_txt = (String) SearchMap.get("pro_level_txt");
	    String line_su = (String) SearchMap.get("line_su");
	    String sul_cost = (String) SearchMap.get("sul_cost");
	    String plating_type = (String) SearchMap.get("plating_type");
	    String type_2 = (String) SearchMap.get("type_2");
	    String plating_cost = (String) SearchMap.get("plating_cost");
	    String type_1 = (String) SearchMap.get("type_1");
	    String t_mone = (String) SearchMap.get("t_mone");
	    String type_cost = (String) SearchMap.get("type_cost");
	    String t_order = (String) SearchMap.get("t_order");
	    String pack_type = (String) SearchMap.get("pack_type");
	    String in_pack = (String) SearchMap.get("in_pack");
	    String out_pack = (String) SearchMap.get("out_pack");
	    String distri_cost = (String) SearchMap.get("distri_cost");
	    String etc_cost = (String) SearchMap.get("etc_cost");
	    String yazaki_ro = (String) SearchMap.get("yazaki_ro");
	    String part_note = (String) SearchMap.get("part_note");
	    String car_type = (String) SearchMap.get("car_type");
	    String p_su_chk = (String) SearchMap.get("p_su_chk");
	    String su_year_1 = (String) SearchMap.get("su_year_1");
	    String su_year_2 = (String) SearchMap.get("su_year_2");
	    String su_year_3 = (String) SearchMap.get("su_year_3");
	    String su_year_4 = (String) SearchMap.get("su_year_4");
	    String su_year_5 = (String) SearchMap.get("su_year_5");
	    String su_year_6 = (String) SearchMap.get("su_year_6");
	    String su_year_7 = (String) SearchMap.get("su_year_7");
	    String su_year_8 = (String) SearchMap.get("su_year_8");
	    String p_su_year_1 = (String) SearchMap.get("p_su_year_1");
	    String p_su_year_2 = (String) SearchMap.get("p_su_year_2");
	    String p_su_year_3 = (String) SearchMap.get("p_su_year_3");
	    String p_su_year_4 = (String) SearchMap.get("p_su_year_4");
	    String p_su_year_5 = (String) SearchMap.get("p_su_year_5");
	    String p_su_year_6 = (String) SearchMap.get("p_su_year_6");
	    String p_su_year_7 = (String) SearchMap.get("p_su_year_7");
	    String p_su_year_8 = (String) SearchMap.get("p_su_year_8");
	    String client_cost = (String) SearchMap.get("client_cost");
	    String ket_cost = (String) SearchMap.get("ket_cost");
	    String target_cost = (String) SearchMap.get("target_cost");
	    String store = (String) SearchMap.get("store");
	    String dest = (String) SearchMap.get("dest");
	    String royalty = (String) SearchMap.get("royalty");
	    String USD_rate = (String) SearchMap.get("USD_rate");
	    String EURO_rate = (String) SearchMap.get("EURO_rate");
	    String YEN_rate = (String) SearchMap.get("YEN_rate");
	    String CNY_rate = (String) SearchMap.get("CNY_rate");
	    String lme_ton = (String) SearchMap.get("lme_ton");
	    String u_ex_rate = (String) SearchMap.get("u_ex_rate");
	    String y_ex_rate = (String) SearchMap.get("y_ex_rate");
	    String information = (String) SearchMap.get("information");

	    sbCostProductInfo.append(" UPDATE cost_productInfo SET ");
	    sbCostProductInfo.append("group_no = ?, pro_type = ?, part_name = ?, part_type = ?, mix_group = ?, part_no = ?, net_1 = ?, net_2 = ?, ");
	    sbCostProductInfo.append(" net_3 = ?, die_no = ?, part_note = ?, car_type = ? ");
	    sbCostProductInfo.append(" WHERE pk_pid = (SELECT fk_pid FROM cost_work WHERE pk_cw = ? ) ");

	    sbCostWork.append(" UPDATE ( SELECT * FROM cost_work A, cost_productInfo B WHERE A.fk_pid = B.pk_pid AND A.pk_cw = ? ) SET ");
	    sbCostWork.append(" m_co_chk = ?, usage = ?, meterial = ?, t_size = ?, w_size = ?, p_size = ?, plating = ?, m_maker = ?, m_mone = ?, unitcost = ?, ");
	    sbCostWork.append(" grade_name = ?, grade_color = ?, order_con = ?, h_weight = ?, h_scrap = ?, t_weight = ?, recycle = ?, ");
	    sbCostWork.append(" cavity = ?, m_su = ?, mold_cost = ?, mold_c_type = ?, make_type = ?, pro_1 = ?, ton = ?, spm = ?, method_type = ?, pro_level = ?, ");
	    sbCostWork.append(" pro_level_txt = ?, line_su = ?, sul_cost = ?, plating_type = ?, type_2 = ?, plating_cost = ?, type_1 = ?, t_mone = ?, type_cost = ?, ");
	    sbCostWork.append(" t_order = ?, pack_type = ?, in_pack = ?, out_pack = ?, distri_cost = ?, etc_cost = ?, yazaki_ro = ?, ");
	    sbCostWork.append(" p_su_chk = ?, su_year_1 = ?, su_year_2 = ?, su_year_3 = ?, su_year_4 = ?, su_year_5 = ?, su_year_6 = ?, su_year_7 = ?, ");
	    sbCostWork.append(" su_year_8 = ?, p_su_year_1 = ?, p_su_year_2 = ?, p_su_year_3 = ?, p_su_year_4 = ?, p_su_year_5 = ?, p_su_year_6 = ?, ");
	    sbCostWork.append(" p_su_year_7 = ?, p_su_year_8 = ?, client_cost = ?, ket_cost = ?, target_cost = ?, store = ?, dest = ?, royalty = ?, USD_rate = ?, ");
	    sbCostWork.append(" EURO_rate = ?, YEN_rate = ?, CNY_rate = ?, lme_ton = ?, u_ex_rate = ?, y_ex_rate = ?, information = ? ");

	    try {
		pstmt = conn.prepareStatement(sbCostProductInfo.toString());
		pstmt.setString(1, group_no);
		pstmt.setString(2, pro_type);
		pstmt.setString(3, part_name);
		pstmt.setString(4, part_type);
		pstmt.setString(5, mix_group);
		pstmt.setString(6, part_no);
		pstmt.setString(7, net_1);
		pstmt.setString(8, net_2);
		pstmt.setString(9, net_3);
		pstmt.setString(10, die_no);
		pstmt.setString(11, part_note);
		pstmt.setString(12, car_type);
		pstmt.setString(13, pk_cw);
		complet = pstmt.executeUpdate();

		if (complet > 0) {
		    pstmt = conn.prepareStatement(sbCostWork.toString());
		    pstmt.setString(1, pk_cw);
		    pstmt.setString(2, m_co_chk);
		    pstmt.setString(3, usage);
		    pstmt.setString(4, meterial);
		    pstmt.setString(5, t_size);
		    pstmt.setString(6, w_size);
		    pstmt.setString(7, p_size);
		    pstmt.setString(8, plating);
		    pstmt.setString(9, m_maker);
		    pstmt.setString(10, m_mone);
		    pstmt.setString(11, unitcost);
		    pstmt.setString(12, grade_name);
		    pstmt.setString(13, grade_color);
		    pstmt.setString(14, order_con);
		    pstmt.setString(15, h_weight);
		    pstmt.setString(16, h_scrap);
		    pstmt.setString(17, t_weight);
		    pstmt.setString(18, recycle);
		    pstmt.setString(19, cavity);
		    pstmt.setString(20, m_su);
		    pstmt.setString(21, mold_cost);
		    pstmt.setString(22, mold_c_type);
		    pstmt.setString(23, make_type);
		    pstmt.setString(24, pro_1);
		    pstmt.setString(25, ton);
		    pstmt.setString(26, spm);
		    pstmt.setString(27, method_type);
		    pstmt.setString(28, pro_level);
		    pstmt.setString(29, pro_level_txt);
		    pstmt.setString(30, line_su);
		    pstmt.setString(31, sul_cost);
		    pstmt.setString(32, plating_type);
		    pstmt.setString(33, type_2);
		    pstmt.setString(34, plating_cost);
		    pstmt.setString(35, type_1);
		    pstmt.setString(36, t_mone);
		    pstmt.setString(37, type_cost);
		    pstmt.setString(38, t_order);
		    pstmt.setString(39, pack_type);
		    pstmt.setString(40, in_pack);
		    pstmt.setString(41, out_pack);
		    pstmt.setString(42, distri_cost);
		    pstmt.setString(43, etc_cost);
		    pstmt.setString(44, yazaki_ro);
		    pstmt.setString(45, p_su_chk);
		    pstmt.setString(46, su_year_1);
		    pstmt.setString(47, su_year_2);
		    pstmt.setString(48, su_year_3);
		    pstmt.setString(49, su_year_4);
		    pstmt.setString(50, su_year_5);
		    pstmt.setString(51, su_year_6);
		    pstmt.setString(52, su_year_7);
		    pstmt.setString(53, su_year_8);
		    pstmt.setString(54, p_su_year_1);
		    pstmt.setString(55, p_su_year_2);
		    pstmt.setString(56, p_su_year_3);
		    pstmt.setString(57, p_su_year_4);
		    pstmt.setString(58, p_su_year_5);
		    pstmt.setString(59, p_su_year_6);
		    pstmt.setString(60, p_su_year_7);
		    pstmt.setString(61, p_su_year_8);
		    pstmt.setString(62, client_cost);
		    pstmt.setString(63, ket_cost);
		    pstmt.setString(64, target_cost);
		    pstmt.setString(65, store);
		    pstmt.setString(66, dest);
		    pstmt.setString(67, royalty);
		    pstmt.setString(68, USD_rate);
		    pstmt.setString(69, EURO_rate);
		    pstmt.setString(70, YEN_rate);
		    pstmt.setString(71, CNY_rate);
		    pstmt.setString(72, lme_ton);
		    pstmt.setString(73, u_ex_rate);
		    pstmt.setString(74, y_ex_rate);
		    pstmt.setString(75, information);
		    complet = pstmt.executeUpdate();
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sbCostProductInfo.delete(0, sbCostProductInfo.length());
		sbCostWork.delete(0, sbCostWork.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    /**
     * 함수명 : UpdateCostWork 설명 : 작업창 저장4 호출
     * 
     * @param ArrayList
     *            SearchList
     * @return int
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 21.
     */
    public int UpdateCostWork4(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    String pk_cw = (String) SearchMap.get("pk_cw");
	    System.out.println("pk_cw== " + pk_cw);
	    String meterial_cost = (String) SearchMap.get("meterial_cost");
	    String loss = (String) SearchMap.get("loss");
	    String scrap_cost = (String) SearchMap.get("scrap_cost");
	    String scrap_rate = (String) SearchMap.get("scrap_rate");
	    String m_total_cost = (String) SearchMap.get("m_total_cost");
	    String output = (String) SearchMap.get("output");
	    String rate = (String) SearchMap.get("rate");
	    String eff_value = (String) SearchMap.get("eff_value");
	    String labor_cost = (String) SearchMap.get("labor_cost");
	    String jun_cost = (String) SearchMap.get("jun_cost");
	    String ma_depr = (String) SearchMap.get("ma_depr");
	    String tabalu = (String) SearchMap.get("tabalu");
	    String m_upkeep = (String) SearchMap.get("m_upkeep");
	    String etc_expense = (String) SearchMap.get("etc_expense");
	    String pack_cost = (String) SearchMap.get("pack_cost");
	    String out_cost = (String) SearchMap.get("out_cost");
	    String total_expense = (String) SearchMap.get("total_expense");
	    String overhead = (String) SearchMap.get("overhead");
	    String gita_cost = (String) SearchMap.get("gita_cost");
	    String common_cost = (String) SearchMap.get("common_cost");
	    String yzk_depr = (String) SearchMap.get("yzk_depr");
	    String mold_type = (String) SearchMap.get("mold_type");
	    System.out.println("mold_type== " + mold_type);
	    String start_depr = (String) SearchMap.get("start_depr");
	    String pro_depr = (String) SearchMap.get("pro_depr");
	    String etc_depr = (String) SearchMap.get("etc_depr");
	    String depr_cost = (String) SearchMap.get("depr_cost");
	    String jae_cost = (String) SearchMap.get("jae_cost");
	    String ge_cost = (String) SearchMap.get("ge_cost");
	    String ad_cost = (String) SearchMap.get("ad_cost");
	    String rnd_cost = (String) SearchMap.get("rnd_cost");
	    String qu_cost = (String) SearchMap.get("qu_cost");
	    String tariff = (String) SearchMap.get("tariff");
	    String royalty_cost = (String) SearchMap.get("royalty_cost");
	    String dis_cost = (String) SearchMap.get("dis_cost");
	    String actual_cost = (String) SearchMap.get("actual_cost");
	    String earn_rate = (String) SearchMap.get("earn_rate");
	    String pl_meterial_cost = (String) SearchMap.get("pl_meterial_cost");
	    String pl_loss = (String) SearchMap.get("pl_loss");
	    String pl_scrap_cost = (String) SearchMap.get("pl_scrap_cost");
	    String pl_m_total_cost = (String) SearchMap.get("pl_m_total_cost");
	    String pl_output = (String) SearchMap.get("pl_output");
	    String pl_rate = (String) SearchMap.get("pl_rate");
	    String pl_labor_cost = (String) SearchMap.get("pl_labor_cost");
	    String pl_jun_cost = (String) SearchMap.get("pl_jun_cost");
	    String pl_ma_depr = (String) SearchMap.get("pl_ma_depr");
	    String pl_tabalu = (String) SearchMap.get("pl_tabalu");
	    String pl_m_upkeep = (String) SearchMap.get("pl_m_upkeep");
	    String pl_total_expense = (String) SearchMap.get("pl_total_expense");
	    String pl_overhead = (String) SearchMap.get("pl_overhead");
	    String pl_common_cost = (String) SearchMap.get("pl_common_cost");
	    String pl_yzk_depr = (String) SearchMap.get("pl_yzk_depr");
	    String pl_start_depr = (String) SearchMap.get("pl_start_depr");
	    String pl_pro_depr = (String) SearchMap.get("pl_pro_depr");
	    String pl_etc_depr = (String) SearchMap.get("pl_etc_depr");
	    String pl_jae_cost = (String) SearchMap.get("pl_jae_cost");
	    String pl_ge_cost = (String) SearchMap.get("pl_ge_cost");
	    String pl_rnd_cost = (String) SearchMap.get("pl_rnd_cost");
	    String pl_tariff = (String) SearchMap.get("pl_tariff");
	    String pl_royalty_cost = (String) SearchMap.get("pl_royalty_cost");
	    String pl_actual_cost = (String) SearchMap.get("pl_actual_cost");

	    sb.append(" UPDATE cost_work SET ");
	    sb.append(" meterial_cost = ?, loss = ?, scrap_cost = ?, scrap_rate = ?, m_total_cost = ?, output = ?, rate = ?, eff_value = ?, ");
	    sb.append("labor_cost = ?, jun_cost = ?, ma_depr = ?, tabalu = ?, m_upkeep = ?, etc_expense = ?, pack_cost = ?, ");
	    sb.append("total_expense = ?, overhead = ?, gita_cost = ?, common_cost = ?, yzk_depr = ?, mold_type = ?, start_depr = ?, ");
	    sb.append("pro_depr = ?, etc_depr = ?, depr_cost = ?, jae_cost = ?, ge_cost = ?, ad_cost = ?, rnd_cost = ?, qu_cost = ?, tariff = ?,");
	    sb.append("royalty_cost = ?, dis_cost = ?, actual_cost = ?, earn_rate = ?, pl_meterial_cost = ?, pl_loss = ?, pl_scrap_cost = ?, ");
	    sb.append("pl_m_total_cost = ?, pl_output = ?, pl_rate = ?, pl_labor_cost = ?, pl_jun_cost = ?, pl_ma_depr = ?, pl_tabalu = ?, ");
	    sb.append("pl_m_upkeep = ?, pl_total_expense = ?, pl_overhead = ?, pl_common_cost = ?, pl_yzk_depr = ?, pl_start_depr = ?, ");
	    sb.append("pl_pro_depr = ?, pl_etc_depr = ?, pl_jae_cost = ?, pl_ge_cost = ?, pl_rnd_cost = ?, pl_tariff = ?, pl_royalty_cost = ?, pl_actual_cost = ? ");
	    sb.append(" WHERE pk_cw = ? ");

	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, meterial_cost);
		pstmt.setString(2, loss);
		pstmt.setString(3, scrap_cost);
		pstmt.setString(4, scrap_rate);
		pstmt.setString(5, m_total_cost);
		pstmt.setString(6, output);
		pstmt.setString(7, rate);
		pstmt.setString(8, eff_value);
		pstmt.setString(9, labor_cost);
		pstmt.setString(10, jun_cost);
		pstmt.setString(11, ma_depr);
		pstmt.setString(12, tabalu);
		pstmt.setString(13, m_upkeep);
		pstmt.setString(14, etc_expense);
		pstmt.setString(15, pack_cost);
		pstmt.setString(16, total_expense);
		pstmt.setString(17, overhead);
		pstmt.setString(18, gita_cost);
		pstmt.setString(19, common_cost);
		pstmt.setString(20, yzk_depr);
		pstmt.setString(21, mold_type);
		pstmt.setString(22, start_depr);
		pstmt.setString(23, pro_depr);
		pstmt.setString(24, etc_depr);
		pstmt.setString(25, depr_cost);
		pstmt.setString(26, jae_cost);
		pstmt.setString(27, ge_cost);
		pstmt.setString(28, ad_cost);
		pstmt.setString(29, rnd_cost);
		pstmt.setString(30, qu_cost);
		pstmt.setString(31, tariff);
		pstmt.setString(32, royalty_cost);
		pstmt.setString(33, dis_cost);
		pstmt.setString(34, actual_cost);
		pstmt.setString(35, earn_rate);
		pstmt.setString(36, pl_meterial_cost);
		pstmt.setString(37, pl_loss);
		pstmt.setString(38, pl_scrap_cost);
		pstmt.setString(39, pl_m_total_cost);
		pstmt.setString(40, pl_output);
		pstmt.setString(41, pl_rate);
		pstmt.setString(42, pl_labor_cost);
		pstmt.setString(43, pl_jun_cost);
		pstmt.setString(44, pl_ma_depr);
		pstmt.setString(45, pl_tabalu);
		pstmt.setString(46, pl_m_upkeep);
		pstmt.setString(47, pl_total_expense);
		pstmt.setString(48, pl_overhead);
		pstmt.setString(49, pl_common_cost);
		pstmt.setString(50, pl_yzk_depr);
		pstmt.setString(51, pl_start_depr);
		pstmt.setString(52, pl_pro_depr);
		pstmt.setString(53, pl_etc_depr);
		pstmt.setString(54, pl_jae_cost);
		pstmt.setString(55, pl_ge_cost);
		pstmt.setString(56, pl_rnd_cost);
		pstmt.setString(57, pl_tariff);
		pstmt.setString(58, pl_royalty_cost);
		pstmt.setString(59, pl_actual_cost);
		pstmt.setString(60, pk_cw);
		complet = pstmt.executeUpdate() + complet;
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    /**
     * 함수명 : getCaseRequestList 설명 : 개발원가 getCaseRequestList 호출
     * 
     * @param String
     *            pk_cr_group
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 25.
     */
    public ArrayList getCaseRequestList(String pk_cr_group, String rev_no) throws Exception {
	ArrayList<Hashtable<String, String>> caseRequestList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> caseRequestHash = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT case_count, user_case_count, team, w_name FROM cost_work A, cost_productInfo B ");
	sb.append("WHERE A.fk_pid = B.pk_pid AND B.pk_cr_group = ? AND length(B.group_no) = 4");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    // pstmt.setString(2, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		caseRequestHash = new Hashtable<String, String>();
		caseRequestHash.put("case_count", StringUtil.checkNull(rs.getString("case_count")));
		caseRequestHash.put("user_case_count", StringUtil.checkNull(rs.getString("user_case_count")));
		caseRequestHash.put("team", StringUtil.checkNull(rs.getString("team")));
		caseRequestHash.put("w_name", StringUtil.checkNull(rs.getString("w_name")));
		caseRequestList.add(caseRequestHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return caseRequestList;
    }

    /**
     * 함수명 : getCaseEarnRateList 설명 : 개발원가 getCaseEarnRateList 호출
     * 
     * @param String
     *            pk_cr_group
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 25.
     */
    public ArrayList getCaseEarnRateList(String pk_cr_group, String rev_no, String group_no) throws Exception {
	ArrayList<Hashtable<String, String>> caseEarnRateList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> caseEarnRateHash = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	/*
	 * sb.append(" SELECT \n"); sb.append("     pk_cw, A.part_name, A.part_type, A.pro_type, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, royalty, start_pro, \n");
	 * sb.append("     store, dest, client_cost, ket_cost, target_cost, assy_note, dis_cost, re_m_cost, usage, meterial, t_size, w_size, p_size, plating, m_maker, \n");
	 * sb.append("     m_mone, unitcost, order_con, h_weight, h_scrap, t_weight, recycle, die_no, cavity, make_type, pro_1, ton, spm, pro_level, pro_level_txt, \n");
	 * sb.append("     type_1, type_2, t_mone, type_cost, t_order, pack_type, in_pack, out_pack, yazaki_ro, m_su, mold_cost, to_cost, line_su, sul_cost, \n");
	 * sb.append("     etc_cost, part_note, distri_cost, meterial_cost, m_total_cost, labor_cost, common_cost, actual_cost, earn_rate, loss, scrap_cost, output, \n");
	 * sb.append("     rate, eff_value, rnd_cost, jun_cost, ma_depr, m_upkeep, total_expense, overhead, outsource, etc_expense, gita_cost, pack_cost, yzk_depr, tabalu, \n");
	 * sb.append("     start_depr, pro_depr, qu_cost, tariff, royalty_cost, jae_cost, ge_cost, cost_report_add, depr_cost, ad_cost, case_type_admin, case_type_user \n");
	 * sb.append(" FROM cost_work A, cost_productInfo B \n");
	 * sb.append(" WHERE A.fk_pid = B.pk_pid AND A.pk_cr_group = ? AND length(A.group_no) = 4 AND A.group_no = ? ORDER BY A.group_no DESC \n");
	 */

	sb.append(" SELECT \n");
	sb.append("     pk_cw, part_name, part_type, pro_type, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, royalty, start_pro, \n");
	sb.append("     store, dest, client_cost, ket_cost, target_cost, '' as assy_note, dis_cost, re_m_cost, usage, meterial, t_size, w_size, p_size, plating, m_maker, \n");
	sb.append("     m_mone, unitcost, order_con, h_weight, h_scrap, t_weight, recycle, '' as die_no, cavity, make_type, pro_1, ton, spm, pro_level, pro_level_txt, \n");
	sb.append("     type_1, type_2, t_mone, type_cost, t_order, pack_type, in_pack, out_pack, yazaki_ro, m_su, mold_cost, to_cost, line_su, sul_cost, \n");
	sb.append("     etc_cost, '' as part_note, distri_cost, meterial_cost, DECODE(PRO_TYPE,'Insert',ROUND(((T_WEIGHT*UNITCOST)+((T_WEIGHT*UNITCOST)*0.03))/1000,1),m_total_cost) AS m_total_cost, labor_cost, common_cost, actual_cost, earn_rate, loss, scrap_cost, output, \n");
	sb.append("     rate, eff_value, rnd_cost, jun_cost, ma_depr, m_upkeep, total_expense, overhead, outsource, etc_expense, gita_cost, pack_cost, yzk_depr, tabalu, \n");
	sb.append("     start_depr, pro_depr, qu_cost, tariff, royalty_cost, jae_cost, ge_cost, cost_report_add, depr_cost, ad_cost, case_type_admin, case_type_admin as case_type_user, 0 as case_order \n");
	sb.append(" FROM cost_work \n");
	sb.append(" WHERE pk_cr_group = ? AND group_no = ? and rev_no = ?  and case_type_admin = 'main' \n");
	sb.append(" UNION ALL \n");
	// 작업창 CASE
	sb.append(" SELECT \n");
	sb.append("     pk_cw, part_name, part_type, pro_type, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, royalty, start_pro, \n");
	sb.append("     store, dest, client_cost, ket_cost, target_cost, '' as assy_note, dis_cost, re_m_cost, usage, meterial, t_size, w_size, p_size, plating, m_maker, \n");
	sb.append("     m_mone, unitcost, order_con, h_weight, h_scrap, t_weight, recycle, '' as die_no, cavity, make_type, pro_1, ton, spm, pro_level, pro_level_txt, \n");
	sb.append("     type_1, type_2, t_mone, type_cost, t_order, pack_type, in_pack, out_pack, yazaki_ro, m_su, mold_cost, to_cost, line_su, sul_cost, \n");
	sb.append("     etc_cost, '' as part_note, distri_cost, meterial_cost, DECODE(PRO_TYPE,'Insert',ROUND(((T_WEIGHT*UNITCOST)+((T_WEIGHT*UNITCOST)*0.03))/1000,1),m_total_cost) AS m_total_cost, labor_cost, common_cost, actual_cost, earn_rate, loss, scrap_cost, output, \n");
	sb.append("     rate, eff_value, rnd_cost, jun_cost, ma_depr, m_upkeep, total_expense, overhead, outsource, etc_expense, gita_cost, pack_cost, yzk_depr, tabalu, \n");
	sb.append("     start_depr, pro_depr, qu_cost, tariff, royalty_cost, jae_cost, ge_cost, cost_report_add, depr_cost, ad_cost, case_type_admin, case_type_admin as case_type_user,TO_NUMBER(SUBSTR(case_type_admin,5)) as case_order \n");
	sb.append(" FROM cost_work \n");
	sb.append(" WHERE pk_cr_group = ? AND group_no = ? and rev_no = ?  and case_type_admin like '%case%' and case_type_user  = '-'   \n");
	sb.append(" UNION ALL \n");
	// 요청서 CASE
	sb.append(" SELECT \n");
	sb.append("     pk_cw, part_name, part_type, pro_type, su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, royalty, start_pro, \n");
	sb.append("     store, dest, client_cost, ket_cost, target_cost, '' as assy_note, dis_cost, re_m_cost, usage, meterial, t_size, w_size, p_size, plating, m_maker, \n");
	sb.append("     m_mone, unitcost, order_con, h_weight, h_scrap, t_weight, recycle, '' as die_no, cavity, make_type, pro_1, ton, spm, pro_level, pro_level_txt, \n");
	sb.append("     type_1, type_2, t_mone, type_cost, t_order, pack_type, in_pack, out_pack, yazaki_ro, m_su, mold_cost, to_cost, line_su, sul_cost, \n");
	sb.append("     etc_cost, '' as part_note, distri_cost, meterial_cost, DECODE(PRO_TYPE,'Insert',ROUND(((T_WEIGHT*UNITCOST)+((T_WEIGHT*UNITCOST)*0.03))/1000,1),m_total_cost) AS m_total_cost, labor_cost, common_cost, actual_cost, earn_rate, loss, scrap_cost, output, \n");
	sb.append("     rate, eff_value, rnd_cost, jun_cost, ma_depr, m_upkeep, total_expense, overhead, outsource, etc_expense, gita_cost, pack_cost, yzk_depr, tabalu, \n");
	sb.append("     start_depr, pro_depr, qu_cost, tariff, royalty_cost, jae_cost, ge_cost, cost_report_add, depr_cost, ad_cost, case_type_admin, case_type_admin as case_type_user,TO_NUMBER(SUBSTR(case_type_admin,5)) as case_order \n");
	sb.append(" FROM cost_work \n");
	sb.append(" WHERE pk_cr_group = ? AND group_no = ? and rev_no = ?  and case_type_admin like '%case%' and case_type_user  != '-'  order by case_order \n");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, pk_cr_group);
	    pstmt.setString(2, group_no);
	    pstmt.setString(3, rev_no);
	    pstmt.setString(4, pk_cr_group);
	    pstmt.setString(5, group_no);
	    pstmt.setString(6, rev_no);
	    pstmt.setString(7, pk_cr_group);
	    pstmt.setString(8, group_no);
	    pstmt.setString(9, rev_no);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		caseEarnRateHash = new Hashtable<String, String>();
		caseEarnRateHash.put("pk_cw", StringUtil.checkNull(rs.getString("pk_cw")));
		caseEarnRateHash.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
		caseEarnRateHash.put("part_type", StringUtil.checkNull(rs.getString("part_type")));
		caseEarnRateHash.put("pro_type", StringUtil.checkNull(rs.getString("pro_type")));
		caseEarnRateHash.put("su_year_1", StringUtil.checkNull(rs.getString("su_year_1")));
		caseEarnRateHash.put("su_year_2", StringUtil.checkNull(rs.getString("su_year_2")));
		caseEarnRateHash.put("su_year_3", StringUtil.checkNull(rs.getString("su_year_3")));
		caseEarnRateHash.put("su_year_4", StringUtil.checkNull(rs.getString("su_year_4")));
		caseEarnRateHash.put("su_year_5", StringUtil.checkNull(rs.getString("su_year_5")));
		caseEarnRateHash.put("su_year_6", StringUtil.checkNull(rs.getString("su_year_6")));
		caseEarnRateHash.put("royalty", StringUtil.checkNull(rs.getString("royalty")));
		caseEarnRateHash.put("start_pro", StringUtil.checkNull(rs.getString("start_pro")));
		caseEarnRateHash.put("store", StringUtil.checkNull(rs.getString("store")));
		caseEarnRateHash.put("dest", StringUtil.checkNull(rs.getString("dest")));
		caseEarnRateHash.put("client_cost", StringUtil.checkNull(rs.getString("client_cost")));
		caseEarnRateHash.put("ket_cost", StringUtil.checkNull(rs.getString("ket_cost")));
		caseEarnRateHash.put("target_cost", StringUtil.checkNull(rs.getString("target_cost")));
		caseEarnRateHash.put("assy_note", StringUtil.checkNull(rs.getString("assy_note")));
		caseEarnRateHash.put("dis_cost", StringUtil.checkNull(rs.getString("dis_cost")));
		caseEarnRateHash.put("re_m_cost", StringUtil.checkNull(rs.getString("re_m_cost")));
		caseEarnRateHash.put("usage", StringUtil.checkNull(rs.getString("usage")));
		caseEarnRateHash.put("meterial", StringUtil.checkNull(rs.getString("meterial")));
		caseEarnRateHash.put("t_size", StringUtil.checkNull(rs.getString("t_size")));
		caseEarnRateHash.put("w_size", StringUtil.checkNull(rs.getString("w_size")));
		caseEarnRateHash.put("p_size", StringUtil.checkNull(rs.getString("p_size")));
		caseEarnRateHash.put("plating", StringUtil.checkNull(rs.getString("plating")));
		caseEarnRateHash.put("m_maker", StringUtil.checkNull(rs.getString("m_maker")));
		caseEarnRateHash.put("m_mone", StringUtil.checkNull(rs.getString("m_mone")));
		caseEarnRateHash.put("unitcost", StringUtil.checkNull(rs.getString("unitcost")));
		caseEarnRateHash.put("order_con", StringUtil.checkNull(rs.getString("order_con")));
		caseEarnRateHash.put("h_weight", StringUtil.checkNull(rs.getString("h_weight")));
		caseEarnRateHash.put("h_scrap", StringUtil.checkNull(rs.getString("h_scrap")));
		caseEarnRateHash.put("t_weight", StringUtil.checkNull(rs.getString("t_weight")));
		caseEarnRateHash.put("recycle", StringUtil.checkNull(rs.getString("recycle")));
		caseEarnRateHash.put("die_no", StringUtil.checkNull(rs.getString("die_no")));
		caseEarnRateHash.put("cavity", StringUtil.checkNull(rs.getString("cavity")));
		caseEarnRateHash.put("make_type", StringUtil.checkNull(rs.getString("make_type")));
		caseEarnRateHash.put("pro_1", StringUtil.checkNull(rs.getString("pro_1")));
		caseEarnRateHash.put("ton", StringUtil.checkNull(rs.getString("ton")));
		caseEarnRateHash.put("spm", StringUtil.checkNull(rs.getString("spm")));
		caseEarnRateHash.put("pro_level", StringUtil.checkNull(rs.getString("pro_level")));
		caseEarnRateHash.put("pro_level_txt", StringUtil.checkNull(rs.getString("pro_level_txt")));
		caseEarnRateHash.put("type_1", StringUtil.checkNull(rs.getString("type_1")));
		caseEarnRateHash.put("type_2", StringUtil.checkNull(rs.getString("type_2")));
		caseEarnRateHash.put("t_mone", StringUtil.checkNull(rs.getString("t_mone")));
		caseEarnRateHash.put("type_cost", StringUtil.checkNull(rs.getString("type_cost")));
		caseEarnRateHash.put("t_order", StringUtil.checkNull(rs.getString("t_order")));
		caseEarnRateHash.put("pack_type", StringUtil.checkNull(rs.getString("pack_type")));
		caseEarnRateHash.put("in_pack", StringUtil.checkNull(rs.getString("in_pack")));
		caseEarnRateHash.put("out_pack", StringUtil.checkNull(rs.getString("out_pack")));
		caseEarnRateHash.put("yazaki_ro", StringUtil.checkNull(rs.getString("yazaki_ro")));
		caseEarnRateHash.put("m_su", StringUtil.checkNull(rs.getString("m_su")));
		caseEarnRateHash.put("mold_cost", StringUtil.checkNull(rs.getString("mold_cost")));
		caseEarnRateHash.put("to_cost", StringUtil.checkNull(rs.getString("to_cost")));
		caseEarnRateHash.put("line_su", StringUtil.checkNull(rs.getString("line_su")));
		caseEarnRateHash.put("sul_cost", StringUtil.checkNull(rs.getString("sul_cost")));
		caseEarnRateHash.put("etc_cost", StringUtil.checkNull(rs.getString("etc_cost")));
		caseEarnRateHash.put("part_note", StringUtil.checkNull(rs.getString("part_note")));
		caseEarnRateHash.put("distri_cost", StringUtil.checkNull(rs.getString("distri_cost")));
		caseEarnRateHash.put("meterial_cost", StringUtil.checkNull(rs.getString("meterial_cost")));
		caseEarnRateHash.put("m_total_cost", StringUtil.checkNull(rs.getString("m_total_cost")));
		caseEarnRateHash.put("labor_cost", StringUtil.checkNull(rs.getString("labor_cost")));
		caseEarnRateHash.put("common_cost", StringUtil.checkNull(rs.getString("common_cost")));
		caseEarnRateHash.put("actual_cost", StringUtil.checkNull(rs.getString("actual_cost")));
		caseEarnRateHash.put("earn_rate", StringUtil.checkNull(rs.getString("earn_rate")));
		caseEarnRateHash.put("loss", StringUtil.checkNull(rs.getString("loss")));
		caseEarnRateHash.put("scrap_cost", StringUtil.checkNull(rs.getString("scrap_cost")));
		caseEarnRateHash.put("output", StringUtil.checkNull(rs.getString("output")));
		caseEarnRateHash.put("rate", StringUtil.checkNull(rs.getString("rate")));
		caseEarnRateHash.put("eff_value", StringUtil.checkNull(rs.getString("eff_value")));
		caseEarnRateHash.put("rnd_cost", StringUtil.checkNull(rs.getString("rnd_cost")));
		caseEarnRateHash.put("jun_cost", StringUtil.checkNull(rs.getString("jun_cost")));
		caseEarnRateHash.put("ma_depr", StringUtil.checkNull(rs.getString("ma_depr")));
		caseEarnRateHash.put("m_upkeep", StringUtil.checkNull(rs.getString("m_upkeep")));
		caseEarnRateHash.put("total_expense", StringUtil.checkNull(rs.getString("total_expense")));
		caseEarnRateHash.put("overhead", StringUtil.checkNull(rs.getString("overhead")));
		caseEarnRateHash.put("outsource", StringUtil.checkNull(rs.getString("outsource")));
		caseEarnRateHash.put("etc_expense", StringUtil.checkNull(rs.getString("etc_expense")));
		caseEarnRateHash.put("gita_cost", StringUtil.checkNull(rs.getString("gita_cost")));
		caseEarnRateHash.put("pack_cost", StringUtil.checkNull(rs.getString("pack_cost")));
		caseEarnRateHash.put("yzk_depr", StringUtil.checkNull(rs.getString("yzk_depr")));
		caseEarnRateHash.put("tabalu", StringUtil.checkNull(rs.getString("tabalu")));
		caseEarnRateHash.put("start_depr", StringUtil.checkNull(rs.getString("start_depr")));
		caseEarnRateHash.put("pro_depr", StringUtil.checkNull(rs.getString("pro_depr")));
		caseEarnRateHash.put("qu_cost", StringUtil.checkNull(rs.getString("qu_cost")));
		caseEarnRateHash.put("tariff", StringUtil.checkNull(rs.getString("tariff")));
		caseEarnRateHash.put("royalty_cost", StringUtil.checkNull(rs.getString("royalty_cost")));
		caseEarnRateHash.put("jae_cost", StringUtil.checkNull(rs.getString("jae_cost")));
		caseEarnRateHash.put("ge_cost", StringUtil.checkNull(rs.getString("ge_cost")));
		caseEarnRateHash.put("cost_report_add", StringUtil.checkNull(rs.getString("cost_report_add")));
		caseEarnRateHash.put("depr_cost", StringUtil.checkNull(rs.getString("depr_cost")));
		caseEarnRateHash.put("ad_cost", StringUtil.checkNull(rs.getString("ad_cost")));
		caseEarnRateHash.put("case_type_admin", StringUtil.checkNull(rs.getString("case_type_admin")));
		caseEarnRateHash.put("case_type_user", StringUtil.checkNull(rs.getString("case_type_user")));

		caseEarnRateList.add(caseEarnRateHash);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (pstmt != null) {
		pstmt.close();
	    }
	    // if(conn!=null) {conn.close();}
	}
	return caseEarnRateList;
    }

    /**
     * 함수명 : getProductSeq 설명 : cost_Productinfo 의 pk sequence를 취득한다
     * 
     * @param String
     * @return
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2013. 03. 22.
     */
    public String getProductSeq() throws Exception {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT productinfo_pk_pid.nextval as ProductSeq FROM dual \n");
	String ProductSeq = "";
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ProductSeq = StringUtil.checkNull(rs.getString("ProductSeq"));
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
	return ProductSeq;
    }

    /**
     * 함수명 : insertCostWork 설명 : 개발원가 작업에서 작성한 데이터 등록
     * 
     * @param ArrayList
     *            SearchList
     * @return int
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 25.
     */
    public int insertCostWork(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sbProductInfo = new StringBuffer();
	StringBuffer sbCostWork = new StringBuffer();
	int complet = 0;

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);
	    String rev_no = (String) SearchMap.get("rev_no");//
	    String m_co_chk = (String) SearchMap.get("m_co_chk");
	    String case_count_1 = (String) SearchMap.get("case_count_1");//
	    String case_count_2 = (String) SearchMap.get("case_count_2");//
	    String pk_cw = (String) SearchMap.get("pk_cw");
	    String pk_cr_group = (String) SearchMap.get("pk_cr_group");//
	    String make = (String) SearchMap.get("make");//
	    String group_no = (String) SearchMap.get("group_no");//
	    String pro_type = (String) SearchMap.get("pro_type");//
	    String part_name = (String) SearchMap.get("part_name");//
	    String part_type = (String) SearchMap.get("part_type");//
	    String mix_group = (String) SearchMap.get("mix_group");//
	    String part_no = (String) SearchMap.get("part_no");//
	    String net_1 = (String) SearchMap.get("net_1");//
	    String net_2 = (String) SearchMap.get("net_2");//
	    String net_3 = (String) SearchMap.get("net_3");//
	    String usage = (String) SearchMap.get("usage");
	    String meterial = (String) SearchMap.get("meterial");
	    String t_size = (String) SearchMap.get("t_size");
	    String w_size = (String) SearchMap.get("w_size");
	    String p_size = (String) SearchMap.get("p_size");
	    String plating = (String) SearchMap.get("plating");
	    String m_maker = (String) SearchMap.get("m_maker");
	    String m_mone = (String) SearchMap.get("m_mone");
	    String unitcost = (String) SearchMap.get("unitcost");
	    String c_unitcost = (String) SearchMap.get("c_unitcost");
	    String grade_name = (String) SearchMap.get("grade_name");
	    String grade_color = (String) SearchMap.get("grade_color");
	    String order_con = (String) SearchMap.get("order_con");
	    String h_weight = (String) SearchMap.get("h_weight");
	    String h_scrap = (String) SearchMap.get("h_scrap");
	    String t_weight = (String) SearchMap.get("t_weight");
	    String recycle_state = (String) SearchMap.get("recycle_state");
	    String recycle = (String) SearchMap.get("recycle");
	    String die_no = (String) SearchMap.get("die_no");//
	    String cavity = (String) SearchMap.get("cavity");
	    String m_su = (String) SearchMap.get("m_su");
	    String mold_cost = (String) SearchMap.get("mold_cost");
	    String mold_c_type = (String) SearchMap.get("mold_c_type");
	    String make_type = (String) SearchMap.get("make_type");
	    String pro_1 = (String) SearchMap.get("pro_1");
	    String ton = (String) SearchMap.get("ton");
	    String spm = (String) SearchMap.get("spm");
	    String method_type = (String) SearchMap.get("method_type");
	    String pro_level = (String) SearchMap.get("pro_level");
	    String pro_level_txt = (String) SearchMap.get("pro_level_txt");
	    String line_su = (String) SearchMap.get("line_su");
	    String sul_cost = (String) SearchMap.get("sul_cost");
	    String plating_type = (String) SearchMap.get("plating_type");
	    String type_2 = (String) SearchMap.get("type_2");
	    String plating_cost = (String) SearchMap.get("plating_cost");
	    String type_1 = (String) SearchMap.get("type_1");
	    String t_mone = (String) SearchMap.get("t_mone");
	    String type_cost = (String) SearchMap.get("type_cost");
	    String t_order = (String) SearchMap.get("t_order");
	    String pack_type = (String) SearchMap.get("pack_type");
	    String in_pack = (String) SearchMap.get("in_pack");
	    String out_pack = (String) SearchMap.get("out_pack");
	    String distri_cost = (String) SearchMap.get("distri_cost");
	    String etc_cost = (String) SearchMap.get("etc_cost");
	    String yazaki_ro = (String) SearchMap.get("yazaki_ro");
	    String part_note = (String) SearchMap.get("part_note");//
	    String car_type = (String) SearchMap.get("car_type");//
	    String p_su_chk = (String) SearchMap.get("p_su_chk");
	    String su_year_1 = (String) SearchMap.get("su_year_1");
	    String su_year_2 = (String) SearchMap.get("su_year_2");
	    String su_year_3 = (String) SearchMap.get("su_year_3");
	    String su_year_4 = (String) SearchMap.get("su_year_4");
	    String su_year_5 = (String) SearchMap.get("su_year_5");
	    String su_year_6 = (String) SearchMap.get("su_year_6");
	    String su_year_7 = (String) SearchMap.get("su_year_7");
	    String su_year_8 = (String) SearchMap.get("su_year_8");
	    String p_su_year_1 = (String) SearchMap.get("p_su_year_1");
	    String p_su_year_2 = (String) SearchMap.get("p_su_year_2");
	    String p_su_year_3 = (String) SearchMap.get("p_su_year_3");
	    String p_su_year_4 = (String) SearchMap.get("p_su_year_4");
	    String p_su_year_5 = (String) SearchMap.get("p_su_year_5");
	    String p_su_year_6 = (String) SearchMap.get("p_su_year_6");
	    String p_su_year_7 = (String) SearchMap.get("p_su_year_7");
	    String p_su_year_8 = (String) SearchMap.get("p_su_year_8");
	    String client_cost = (String) SearchMap.get("client_cost");
	    String ket_cost = (String) SearchMap.get("ket_cost");
	    String target_cost = (String) SearchMap.get("target_cost");
	    String store = (String) SearchMap.get("store");
	    String dest = (String) SearchMap.get("dest");
	    String royalty = (String) SearchMap.get("royalty");
	    String USD_rate = (String) SearchMap.get("USD_rate");
	    String EURO_rate = (String) SearchMap.get("EURO_rate");
	    String YEN_rate = (String) SearchMap.get("YEN_rate");
	    String CNY_rate = (String) SearchMap.get("CNY_rate");
	    String lme_ton = (String) SearchMap.get("lme_ton");
	    String u_ex_rate = (String) SearchMap.get("u_ex_rate");
	    String y_ex_rate = (String) SearchMap.get("y_ex_rate");
	    String meterial_cost = (String) SearchMap.get("meterial_cost");
	    String loss = (String) SearchMap.get("loss");
	    String scrap_cost = (String) SearchMap.get("scrap_cost");
	    String scrap_rate = (String) SearchMap.get("scrap_rate");
	    String m_total_cost = (String) SearchMap.get("m_total_cost");
	    String output = (String) SearchMap.get("output");
	    String rate = (String) SearchMap.get("rate");
	    String eff_value = (String) SearchMap.get("eff_value");
	    String labor_cost = (String) SearchMap.get("labor_cost");
	    String jun_cost = (String) SearchMap.get("jun_cost");
	    String ma_depr = (String) SearchMap.get("ma_depr");
	    String tabalu = (String) SearchMap.get("tabalu");
	    String m_upkeep = (String) SearchMap.get("m_upkeep");
	    String etc_expense = (String) SearchMap.get("etc_expense");
	    String pack_cost = (String) SearchMap.get("pack_cost");
	    String out_cost = (String) SearchMap.get("out_cost");
	    String total_expense = (String) SearchMap.get("total_expense");
	    String overhead = (String) SearchMap.get("overhead");
	    String gita_cost = (String) SearchMap.get("gita_cost");
	    String common_cost = (String) SearchMap.get("common_cost");
	    String yzk_depr = (String) SearchMap.get("yzk_depr");
	    String mold_type = (String) SearchMap.get("mold_type");
	    String start_depr = (String) SearchMap.get("start_depr");
	    String pro_depr = (String) SearchMap.get("pro_depr");
	    String etc_depr = (String) SearchMap.get("etc_depr");
	    String depr_cost = (String) SearchMap.get("depr_cost");
	    String jae_cost = (String) SearchMap.get("jae_cost");
	    String ge_cost = (String) SearchMap.get("ge_cost");
	    String ad_cost = (String) SearchMap.get("ad_cost");
	    String rnd_cost = (String) SearchMap.get("rnd_cost");
	    String qu_cost = (String) SearchMap.get("qu_cost");
	    String tariff = (String) SearchMap.get("tariff");
	    String royalty_cost = (String) SearchMap.get("royalty_cost");
	    String dis_cost = (String) SearchMap.get("dis_cost");
	    String actual_cost = (String) SearchMap.get("actual_cost");
	    String earn_rate = (String) SearchMap.get("earn_rate");
	    String pl_meterial_cost = (String) SearchMap.get("pl_meterial_cost");
	    String pl_loss = (String) SearchMap.get("pl_loss");
	    String pl_scrap_cost = (String) SearchMap.get("pl_scrap_cost");
	    String pl_m_total_cost = (String) SearchMap.get("pl_m_total_cost");
	    String pl_output = (String) SearchMap.get("pl_output");
	    String pl_rate = (String) SearchMap.get("pl_rate");
	    String pl_labor_cost = (String) SearchMap.get("pl_labor_cost");
	    String pl_jun_cost = (String) SearchMap.get("pl_jun_cost");
	    String pl_ma_depr = (String) SearchMap.get("pl_ma_depr");
	    String pl_tabalu = (String) SearchMap.get("pl_tabalu");
	    String pl_m_upkeep = (String) SearchMap.get("pl_m_upkeep");
	    String pl_total_expense = (String) SearchMap.get("pl_total_expense");
	    String pl_overhead = (String) SearchMap.get("pl_overhead");
	    String pl_common_cost = (String) SearchMap.get("pl_common_cost");
	    String pl_yzk_depr = (String) SearchMap.get("pl_yzk_depr");
	    String pl_start_depr = (String) SearchMap.get("pl_start_depr");
	    String pl_pro_depr = (String) SearchMap.get("pl_pro_depr");
	    String pl_etc_depr = (String) SearchMap.get("pl_etc_depr");
	    String pl_jae_cost = (String) SearchMap.get("pl_jae_cost");
	    String pl_ge_cost = (String) SearchMap.get("pl_ge_cost");
	    String pl_rnd_cost = (String) SearchMap.get("pl_rnd_cost");
	    String pl_tariff = (String) SearchMap.get("pl_tariff");
	    String pl_royalty_cost = (String) SearchMap.get("pl_royalty_cost");
	    String pl_actual_cost = (String) SearchMap.get("pl_actual_cost");
	    String case_type_admin = (String) SearchMap.get("case_type_admin");
	    String case_infor = (String) SearchMap.get("case_infor");
	    String case_count = (String) SearchMap.get("case_count");

	    /*
	     * sbProductInfo.append(" INSERT INTO cost_productInfo (pk_pid, pk_cr_group, make, group_no, case_count_1, case_count_2, rev_no, ");
	     * sbProductInfo.append(" pro_type, part_name, part_type, mix_group, part_no, net_1, net_2, "); sbProductInfo.append("  net_3, car_type, part_note, die_no ) ");
	     * sbProductInfo.append("   VALUES (productinfo_pk_pid.nextval ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	     */

	    String ProductSeq = getProductSeq();// cost_Productinfo의 sequence

	    sbProductInfo.append(" INSERT INTO COST_PRODUCTINFO(                                                             ");
	    sbProductInfo.append("        PK_PID,       			PK_CR_GROUP,  	REV_PK,      		REV_NO,     		GROUP_NO,       			PARENT_ID,    					");
	    sbProductInfo.append("        CASE_COUNT_1, 	CASE_COUNT_2, 	TABLE_ROW,   	DATA_TYPE,  	CASE_TYPE_USER, 	PRO_TYPE,     					");
	    sbProductInfo.append("        MAKE,         			PART_TYPE,    		FAM_GROUP,   	SEL_GROUP,  	MIX_GROUP,      			PART_COUNT,   				");
	    sbProductInfo.append("        PJT_NAME,     		PJT_NO,       		PART_NO,     		PART_NAME,  	TEAM,           				F_NAME,       					");
	    sbProductInfo.append("        A_NAME,      	 		W_NAME,       		DEV_STEP,    		DR_STEP,    		SUB_STEP,       			REQUEST_TXT,  				");
	    sbProductInfo.append("        CAR_TYPE,     		APP_PART,     		CUSTOMER_F,  	CUSTOMER_L, MODI_SU,        				ASSY_NOTE,    				");
	    sbProductInfo.append("        PART_NOTE,    		ETC_NOTE,     		REPORT_PK,   		PPS_STATE,  	NET_1,          				NET_2,        						");
	    sbProductInfo.append("        NET_3,        			DIE_NO)                                                              																					");
	    sbProductInfo.append(" SELECT ?,       				        PK_CR_GROUP,  		REV_PK,      		REV_NO,     		GROUP_NO,       			PARENT_ID,    		");
	    sbProductInfo.append("        		CASE_COUNT_1, 		CASE_COUNT_2, 		TABLE_ROW,   	DATA_TYPE,  	CASE_TYPE_USER, 	PRO_TYPE,     		");
	    sbProductInfo.append("        		?,            					PART_TYPE,    			FAM_GROUP,   	SEL_GROUP,  	?,              					PART_COUNT,   	");
	    sbProductInfo.append("        		PJT_NAME,     			PJT_NO,       			?,         					PART_NAME,  	TEAM,           				F_NAME,       		");
	    sbProductInfo.append("        		A_NAME,       			W_NAME,       			DEV_STEP,    		DR_STEP,    		SUB_STEP,       			REQUEST_TXT,  	");
	    sbProductInfo.append("        		?,            					APP_PART,     			CUSTOMER_F,  	CUSTOMER_L, MODI_SU,        				ASSY_NOTE,    	");
	    sbProductInfo.append("        		?,            					ETC_NOTE,     			REPORT_PK,   		PPS_STATE,  	?,             	 					?,            				");
	    sbProductInfo.append("        		?,            					?                                                                    																				");
	    sbProductInfo.append("   FROM COST_PRODUCTINFO                                                                   																					");
	    sbProductInfo.append("WHERE PK_PID IN (SELECT FK_PID FROM COST_WORK WHERE PK_CW = ?)                     														");

	    sbCostWork.append(" INSERT INTO COST_WORK(												   ");
	    sbCostWork.append("  pk_cw,					group_no,	    case_count_1,	case_count_2,	rev_no,			pro_type,		part_name,	part_type          		");
	    sbCostWork.append(" ,m_co_chk,				su_year_1,		su_year_2,		su_year_3,		su_year_4,	su_year_5,	su_year_6          	");
	    sbCostWork.append(" ,su_year_7,				su_year_8,		client_cost,		ket_cost,			target_cost,	usage,			meterial           		");
	    sbCostWork.append(" ,t_size,						w_size,				p_size,				plating,				m_maker,		m_mone,		unitcost           		");
	    sbCostWork.append(" ,grade_name,			grade_color,		order_con,			h_weight,			h_scrap,		t_weight,		recycle       	   		");
	    sbCostWork.append(" ,cavity,						m_su,				mold_cost,		mold_c_type,		make_type,	pro_1,			ton                		");
	    sbCostWork.append(" ,spm,						method_type,		pro_level,			pro_level_txt,		line_su,			sul_cost,		plating_type  	   		");
	    sbCostWork.append(" ,type_2,					plating_cost,		type_1,				t_mone,				type_cost,		t_order,			pack_type          	");
	    sbCostWork.append(" ,in_pack,					out_pack,			distri_cost,		yzk_mone,		etc_cost,		yazaki_ro,		meterial_cost      	");
	    sbCostWork.append(" ,loss,						scrap_cost,		scrap_rate,		m_total_cost,		output,			rate,				eff_value     	   		");
	    sbCostWork.append(" ,labor_cost,				jun_cost,			ma_depr,			tabalu,				m_upkeep,	etc_expense,	pack_cost     	   	");
	    sbCostWork.append(" ,total_expense,			overhead,			gita_cost,			common_cost,	yzk_depr,		mold_type,	start_depr    	   		");
	    sbCostWork.append(" ,pro_depr,					etc_depr,			depr_cost,			jae_cost,			ge_cost,		ad_cost,		rnd_cost           		");
	    sbCostWork.append(" ,qu_cost,					tariff,					royalty_cost,		dis_cost,			actual_cost,	earn_rate,		case_type_user     ");
	    sbCostWork.append(" ,case_type_admin,	case_infor,			store,				dest,					royalty,			USD_rate,		EURO_rate          	");
	    sbCostWork.append(" ,YEN_rate,				CNY_rate,			lme_ton,			u_ex_rate,			y_ex_rate,		p_su_chk,		p_su_year_1        	");
	    sbCostWork.append(" ,p_su_year_2,			p_su_year_3,		p_su_year_4,		p_su_year_5,		p_su_year_6,	p_su_year_7,	p_su_year_8  	   	");
	    sbCostWork.append(" ,fk_pid,				fk_cost_request,    pk_cr_group																			  	 ");
	    sbCostWork.append(" ,pl_meterial_cost, pl_loss, pl_scrap_cost, pl_m_total_cost, pl_output, pl_rate, pl_labor_cost ,pl_jun_cost, pl_ma_depr, pl_tabalu	 ");
	    sbCostWork.append(" ,pl_m_upkeep, pl_total_expense, pl_overhead, pl_common_cost, pl_yzk_depr, pl_start_depr, pl_pro_depr, pl_etc_depr, pl_jae_cost       ");
	    sbCostWork.append(" ,pl_ge_cost, pl_rnd_cost, pl_tariff, pl_royalty_cost, pl_actual_cost )															  	 ");
	    sbCostWork.append(" VALUES(                                                                                                                ");
	    sbCostWork.append("  cost_work_pk_cw.nextval, ?, ?, ?, ?, ?, ?,?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, nvl(?,''), ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?                                                                                                   ");
	    sbCostWork
		    .append(" ,?,(select fk_cost_request from cost_work where pk_cw = ?),?                                                                                                                  ");
	    sbCostWork.append(" ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?      )             ");

	    try {
		pstmt = conn.prepareStatement(sbProductInfo.toString());
		pstmt.setString(1, ProductSeq);
		pstmt.setString(2, make);
		pstmt.setString(3, mix_group);
		pstmt.setString(4, part_no);
		pstmt.setString(5, car_type);
		pstmt.setString(6, part_note);
		pstmt.setString(7, net_1);
		pstmt.setString(8, net_2);
		pstmt.setString(9, net_3);
		pstmt.setString(10, die_no);
		pstmt.setString(11, pk_cw);

		complet = pstmt.executeUpdate();
		pstmt = null;

		pstmt = conn.prepareStatement(sbCostWork.toString());
		pstmt.setString(1, group_no);
		pstmt.setInt(2, Integer.parseInt(case_count_1));
		pstmt.setInt(3, Integer.parseInt(case_count_2));
		pstmt.setString(4, rev_no);
		pstmt.setString(5, pro_type);
		pstmt.setString(6, part_name);
		pstmt.setString(7, part_type);
		pstmt.setString(8, m_co_chk);
		pstmt.setString(9, su_year_1);
		pstmt.setString(10, su_year_2);
		pstmt.setString(11, su_year_3);
		pstmt.setString(12, su_year_4);
		pstmt.setString(13, su_year_5);
		pstmt.setString(14, su_year_6);
		pstmt.setString(15, su_year_7);
		pstmt.setString(16, su_year_8);
		pstmt.setString(17, client_cost);
		pstmt.setString(18, ket_cost);
		pstmt.setString(19, target_cost);
		pstmt.setString(20, usage);
		pstmt.setString(21, meterial);
		pstmt.setString(22, t_size);
		pstmt.setString(23, w_size);
		pstmt.setString(24, p_size);
		pstmt.setString(25, plating);
		pstmt.setString(26, m_maker);
		pstmt.setString(27, m_mone);
		pstmt.setString(28, unitcost);
		pstmt.setString(29, grade_name);
		pstmt.setString(30, grade_color);
		pstmt.setString(31, order_con);
		pstmt.setString(32, h_weight);
		pstmt.setString(33, h_scrap);
		pstmt.setString(34, t_weight);
		pstmt.setString(35, recycle);
		pstmt.setString(36, cavity);
		pstmt.setString(37, m_su);
		pstmt.setString(38, mold_cost);
		pstmt.setString(39, mold_c_type);
		pstmt.setString(40, make_type);
		pstmt.setString(41, pro_1);
		pstmt.setString(42, ton);
		pstmt.setString(43, spm);
		pstmt.setString(44, method_type);
		pstmt.setString(45, pro_level);
		pstmt.setString(46, pro_level_txt);
		pstmt.setString(47, line_su);
		pstmt.setString(48, sul_cost);
		pstmt.setString(49, plating_type);
		pstmt.setString(50, type_2);
		pstmt.setString(51, plating_cost);
		pstmt.setString(52, type_1);
		pstmt.setString(53, t_mone);
		pstmt.setString(54, type_cost);
		pstmt.setString(55, t_order);
		pstmt.setString(56, pack_type);
		pstmt.setString(57, in_pack);
		pstmt.setString(58, out_pack);
		pstmt.setString(59, distri_cost);
		pstmt.setString(60, "");
		pstmt.setString(61, etc_cost);
		pstmt.setString(62, yazaki_ro);
		pstmt.setString(63, meterial_cost);
		pstmt.setString(64, loss);
		pstmt.setString(65, scrap_cost);
		pstmt.setString(66, scrap_rate);
		pstmt.setString(67, m_total_cost);
		pstmt.setString(68, output);
		pstmt.setString(69, rate);
		pstmt.setString(70, eff_value);
		pstmt.setString(71, labor_cost);
		pstmt.setString(72, jun_cost);
		pstmt.setString(73, ma_depr);
		pstmt.setString(74, tabalu);
		pstmt.setString(75, m_upkeep);
		pstmt.setString(76, etc_expense);
		pstmt.setString(77, pack_cost);
		pstmt.setString(78, total_expense);
		pstmt.setString(79, overhead);
		pstmt.setString(80, gita_cost);
		pstmt.setString(81, common_cost);
		pstmt.setString(82, yzk_depr);
		pstmt.setString(83, mold_type);
		pstmt.setString(84, start_depr);
		pstmt.setString(85, pro_depr);
		pstmt.setString(86, etc_depr);
		pstmt.setString(87, depr_cost);
		pstmt.setString(88, jae_cost);
		pstmt.setString(89, ge_cost);
		pstmt.setString(90, ad_cost);
		pstmt.setString(91, rnd_cost);
		pstmt.setString(92, qu_cost);
		pstmt.setString(93, tariff);
		pstmt.setString(94, royalty_cost);
		pstmt.setString(95, dis_cost);
		pstmt.setString(96, actual_cost);
		pstmt.setString(97, earn_rate);
		pstmt.setString(98, "-");
		pstmt.setString(99, case_count);
		pstmt.setString(100, case_infor);
		pstmt.setString(101, store);
		pstmt.setString(102, dest);
		pstmt.setString(103, royalty);
		pstmt.setString(104, USD_rate);
		pstmt.setString(105, EURO_rate);
		pstmt.setString(106, YEN_rate);
		pstmt.setString(107, CNY_rate);
		pstmt.setString(108, lme_ton);
		pstmt.setString(109, u_ex_rate);
		pstmt.setString(110, y_ex_rate);
		pstmt.setString(111, p_su_chk);
		pstmt.setString(112, p_su_year_1);
		pstmt.setString(113, p_su_year_2);
		pstmt.setString(114, p_su_year_3);
		pstmt.setString(115, p_su_year_4);
		pstmt.setString(116, p_su_year_5);
		pstmt.setString(117, p_su_year_6);
		pstmt.setString(118, p_su_year_7);
		pstmt.setString(119, p_su_year_8);
		pstmt.setString(120, ProductSeq);
		pstmt.setString(121, pk_cw);
		pstmt.setString(122, pk_cr_group);

		pstmt.setString(123, pl_meterial_cost);
		pstmt.setString(124, pl_loss);
		pstmt.setString(125, pl_scrap_cost);
		pstmt.setString(126, pl_m_total_cost);
		pstmt.setString(127, pl_output);
		pstmt.setString(128, pl_rate);
		pstmt.setString(129, pl_labor_cost);
		pstmt.setString(130, pl_jun_cost);
		pstmt.setString(131, pl_ma_depr);
		pstmt.setString(132, pl_tabalu);
		pstmt.setString(133, pl_m_upkeep);
		pstmt.setString(134, pl_total_expense);
		pstmt.setString(135, pl_overhead);
		pstmt.setString(136, pl_common_cost);
		pstmt.setString(137, pl_yzk_depr);
		pstmt.setString(138, pl_start_depr);
		pstmt.setString(139, pl_pro_depr);
		pstmt.setString(140, pl_etc_depr);
		pstmt.setString(141, pl_jae_cost);
		pstmt.setString(142, pl_ge_cost);
		pstmt.setString(143, pl_rnd_cost);
		pstmt.setString(144, pl_tariff);
		pstmt.setString(145, pl_royalty_cost);
		pstmt.setString(146, pl_actual_cost);

		pstmt.executeUpdate();
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sbProductInfo.delete(0, sbProductInfo.length());
		sbCostWork.delete(0, sbCostWork.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    /**
     * 함수명 : DeleteCostWork 설명 : 개발원가 작업에서 작성한 데이터 삭제
     * 
     * @param int complet
     * @return int
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 06. 26.
     */
    public int DeleteCostWork(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sbProductInfo = new StringBuffer();
	StringBuffer sbCostWork = new StringBuffer();
	int complet = 0;
	String pk_cw = "";
	String pk_cr_group = "";
	String case_type_admin = "";

	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);

	    pk_cw = (String) SearchMap.get("pk_cw");
	    pk_cr_group = (String) SearchMap.get("pk_cr_group");
	    case_type_admin = (String) SearchMap.get("case_type_admin");

	    sbProductInfo.append(" DELETE cost_productInfo where pk_pid in (select fk_pid from cost_work where pk_cr_group = ? and case_type_admin = ?) ");

	    sbCostWork.append(" DELETE  FROM cost_work  where pk_cr_group = ? and case_type_admin = ?");

	    try {
		pstmt = conn.prepareStatement(sbProductInfo.toString());
		pstmt.setString(1, pk_cr_group);
		pstmt.setString(2, case_type_admin);

		complet = pstmt.executeUpdate() + complet;
		if (complet > 0) {
		    pstmt = conn.prepareStatement(sbCostWork.toString());
		    pstmt.setString(1, pk_cr_group);
		    pstmt.setString(2, case_type_admin);

		    complet = pstmt.executeUpdate() + complet;
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sbProductInfo.delete(0, sbProductInfo.length());
		sbCostWork.delete(0, sbCostWork.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
	return complet;
    }

    /**
     * 함수명 : UpdateCostWork5 설명 : 작업창 case 순서 변경 update
     * 
     * @param ArrayList
     *            SearchList
     * @return int
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2013. 09. 24.
     */
    public void UpdateCostWork5(ArrayList SearchList) throws Exception {
	Hashtable SearchMap = null;
	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();
	int complet = 0;

	String pk_cw = "";
	String case_type_admin = "";
	String case_infor = "";
	for (int i = 0; i < SearchList.size(); i++) {
	    SearchMap = (Hashtable) SearchList.get(i);

	    pk_cw = (String) SearchMap.get("pk_cw");
	    case_type_admin = (String) SearchMap.get("case_type_admin");
	    case_infor = (String) SearchMap.get("case_infor");
	    sb.append("update cost_work set case_type_admin = '" + case_type_admin + "', case_infor = '" + case_infor + "' where pk_cw = '" + pk_cw + "'");
	    try {
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.executeUpdate();
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		sb.delete(0, sb.length());
		if (pstmt != null) {
		    pstmt.close();
		}
		// if(conn!=null) {conn.close();}
	    }
	}
    }
}