package e3ps.cost.control;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;
import e3ps.cost.dao.CostAccDao;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;

public class CostAccCtl extends CostAccVarialbe {

    /* doQry 조회 */
    public ArrayList doQry(String page_name, GridData gdReq, GridData gdReq3, GridData gdReq4) throws Exception {
	Connection conn = null;
	// 전송할 데이터를 담을 빈 GridData를 생성합니다.
	GridData gdRes = null;
	GridData gdRes3 = null;
	GridData gdRes4 = null;

	String pk_cr_group_1 = gdReq.getParam("pk_cr_group");
	String rev_no = gdReq.getParam("rev_no");
	table_row = gdReq.getParam("table_row"); // 판매단위 제품정보 rowCount

	int rowCount = 0;
	int rowCount4 = 0;
	String k = "0";

	// 원가산출 후 처리
	String test_value = "[";
	String flag = "1";

	String in_start_depr = "";
	String g_sel1 = "";
	String g_sel2 = "";
	String g_sel3 = "";
	String start_depr = "";
	String depr_cost = "";
	String pro_depr = "";
	String distri_cost_ = "";
	try {
	    // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
	    // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
	    gdRes = OperateGridData.cloneResponseGridData(gdReq);
	    gdRes3 = OperateGridData.cloneResponseGridData(gdReq3);
	    if ("work".equals(page_name)) {
		gdRes4 = OperateGridData.cloneResponseGridData(gdReq4);
	    }
	    // 조회조건으로 사용할 Param 값들을 가져옵니다.
	    rowCount = gdReq3.getHeader("SELECTED").getRowCount();

	    // 배열크기
	    int aa = rowCount;
	    int bb = 90;
	    int cc = 90;
	    /*
	     * for(int i = 0; i<rowCount; i ++){ if(gdReq3.getHeader("group_no").getValue(i).length() < 5){ // T001 aa++; }else
	     * if(gdReq3.getHeader("group_no").getValue(i).length() < 8){ bb++; }else{ cc++; } }
	     */

	    // 배열크기선언
	    a_su_year_1 = new String[aa];
	    a_su_year_2 = new String[aa];
	    a_su_year_3 = new String[aa];
	    a_su_year_4 = new String[aa];
	    a_su_year_5 = new String[aa];
	    a_su_year_6 = new String[aa];
	    a_su_year_7 = new String[aa];
	    a_su_year_8 = new String[aa];
	    ket_cost = new String[aa];
	    target_cost = new String[aa];

	    assy_in = new String[aa];
	    case_count_1 = new String[aa];
	    case_count_2 = new String[aa][bb];
	    unitcost_txt = new String[aa][bb][cc];
	    c_unitcost = new String[aa][bb][cc];
	    store = new String[aa];
	    dest = new String[aa];
	    dis_cost = new String[aa][bb][cc];
	    distri_cost = new String[aa];
	    royalty = new String[aa];
	    a_su_year_1 = new String[aa];
	    a_su_year_2 = new String[aa];
	    a_su_year_3 = new String[aa];
	    a_su_year_4 = new String[aa];
	    a_su_year_5 = new String[aa];
	    a_su_year_6 = new String[aa];
	    a_su_year_7 = new String[aa];
	    a_su_year_8 = new String[aa];
	    p_su_year_1 = new String[aa];
	    p_su_year_2 = new String[aa];
	    p_su_year_3 = new String[aa];
	    p_su_year_4 = new String[aa];
	    p_su_year_5 = new String[aa];
	    p_su_year_6 = new String[aa];
	    p_su_year_7 = new String[aa];
	    p_su_year_8 = new String[aa];
	    ket_cost = new String[aa];
	    target_cost = new String[aa];
	    store = new String[aa];
	    dest = new String[aa];
	    royalty = new String[aa];
	    make = new String[aa][bb][cc];
	    group_no = new String[aa][bb][cc];
	    pro_type = new String[aa][bb][cc];
	    part_name = new String[aa][bb][cc];
	    part_type_1 = new String[aa][bb][cc];
	    mix_group_1 = new String[aa][bb][cc];
	    usage = new String[aa][bb][cc];
	    meterial = new String[aa][bb][cc];
	    t_size = new String[aa][bb][cc];
	    w_size = new String[aa][bb][cc];
	    p_size = new String[aa][bb][cc];
	    plating = new String[aa][bb][cc];
	    m_maker = new String[aa][bb][cc];
	    m_mone = new String[aa][bb][cc];
	    unitcost = new String[aa][bb][cc];
	    grade_name = new String[aa][bb][cc];
	    grade_color = new String[aa][bb][cc];
	    order_con = new String[aa][bb][cc];
	    h_weight = new String[aa][bb][cc];
	    h_scrap = new String[aa][bb][cc];
	    t_weight = new String[aa][bb][cc];
	    recycle_state = new String[aa][bb][cc];
	    recycle = new String[aa][bb][cc];
	    cavity = new String[aa][bb][cc];
	    m_su = new String[aa][bb][cc];
	    mold_cost = new String[aa][bb][cc];
	    mold_c_type = new String[aa][bb][cc];
	    make_type = new String[aa][bb][cc];
	    pro_1 = new String[aa][bb][cc];
	    start_pro = new String[aa];
	    ton = new String[aa][bb][cc];
	    spm = new String[aa][bb][cc];
	    method_type = new String[aa][bb][cc];
	    pro_level = new String[aa][bb][cc];
	    pro_level_txt = new String[aa][bb][cc];
	    line_su = new String[aa][bb][cc];
	    sul_cost = new String[aa][bb][cc];
	    plating_type = new String[aa][bb][cc];
	    type_2 = new String[aa][bb][cc];
	    plating_cost = new String[aa][bb][cc];
	    type_1 = new String[aa][bb][cc];
	    t_mone = new String[aa][bb][cc];
	    type_cost = new String[aa][bb][cc];
	    t_order = new String[aa][bb][cc];
	    pack_type = new String[aa][bb][cc];
	    in_pack = new String[aa][bb][cc];
	    out_pack = new String[aa][bb][cc];
	    yazaki_ro = new String[aa][bb][cc];
	    etc_cost = new String[aa][bb][cc];

	    pk_cw = new String[aa][bb][cc];
	    pk_cr_group = new String[aa][bb][cc];
	    scrap_rate = new String[aa][bb][cc];
	    eff_value = new String[aa][bb][cc];
	    pack_cost = new String[aa][bb][cc];
	    gita_cost = new String[aa][bb][cc];
	    qu_cost = new String[aa][bb][cc];
	    dis_cost = new String[aa][bb][cc];
	    mold_type = new String[aa][bb][cc];

	    pl_meterial_cost = new String[aa][bb][cc];
	    pl_loss = new String[aa][bb][cc];
	    pl_scrap_cost = new String[aa][bb][cc];
	    pl_m_total_cost = new String[aa][bb][cc];
	    pl_output = new String[aa][bb][cc];
	    pl_rate = new String[aa][bb][cc];
	    pl_labor_cost = new String[aa][bb][cc];
	    pl_jun_cost = new String[aa][bb][cc];
	    pl_ma_depr = new String[aa][bb][cc];
	    pl_tabalu = new String[aa][bb][cc];
	    pl_m_upkeep = new String[aa][bb][cc];
	    pl_total_expense = new String[aa][bb][cc];
	    pl_overhead = new String[aa][bb][cc];
	    pl_common_cost = new String[aa][bb][cc];
	    pl_yzk_depr = new String[aa][bb][cc];
	    pl_start_depr = new String[aa][bb][cc];
	    pl_pro_depr = new String[aa][bb][cc];
	    pl_etc_depr = new String[aa][bb][cc];
	    pl_jae_cost = new String[aa][bb][cc];
	    pl_ge_cost = new String[aa][bb][cc];
	    pl_rnd_cost = new String[aa][bb][cc];
	    pl_tariff = new String[aa][bb][cc];
	    pl_royalty_cost = new String[aa][bb][cc];
	    pl_actual_cost = new String[aa][bb][cc];

	    st_person = new String[aa][bb][cc]; // 표준작업인원
	    st_eff_value = new String[aa][bb][cc]; // 효율
	    eff_value = new String[aa][bb][cc]; // 효율
	    unitcost_si = new String[aa][bb][cc]; // 원재료단가
	    meterial_cost = new String[aa][bb][cc]; // 원재료비
	    m_total_cost = new String[aa][bb][cc]; // 재료비
	    labor_cost = new String[aa][bb][cc]; // 노무비
	    common_cost = new String[aa][bb][cc]; // 제조경비
	    s_depr_cost = new String[aa][bb][cc]; // 일반감가비
	    g_depr_cost = new String[aa][bb][cc]; // 기획수량감가비
	    yzk_depr = new String[aa][bb][cc]; // yzk감가
	    ad_cost = new String[aa][bb][cc]; // 관리비
	    actual_cost = new String[aa][bb][cc]; // 총원가
	    earn_rate = new String[aa][bb][cc]; // 수익률
	    loss = new String[aa][bb][cc]; // loss비
	    scrap_cost = new String[aa][bb][cc]; // 스크랩판매비
	    output = new String[aa][bb][cc]; // 시간당 생산량
	    rate = new String[aa][bb][cc]; // 임율
	    qu_cost = new String[aa][bb][cc]; // 품질비용
	    gita_cost = new String[aa][bb][cc]; // 기타감가
	    tariff = new String[aa][bb][cc]; // 관세
	    jun_cost = new String[aa][bb][cc]; // 전력비
	    ma_depr = new String[aa][bb][cc]; // 기계감가
	    tabalu = new String[aa][bb][cc]; // 타발유
	    m_upkeep = new String[aa][bb][cc]; // 금형유지비
	    total_expense = new String[aa][bb][cc]; // 직접경비
	    overhead = new String[aa][bb][cc]; // 간접경비
	    pack_cost = new String[aa][bb][cc]; // 포장비
	    s_avg_su = new String[aa][bb][cc]; // 일반감가기준수량
	    avg_su = new String[aa][bb][cc]; // 총 판매기획수량
	    process_cost = new String[aa][bb][cc]; // 가공비
	    jae_cost = new String[aa][bb][cc]; // 재료관리비
	    ge_cost = new String[aa][bb][cc]; // 일반관리비
	    s_rnd_cost = new String[aa][bb][cc]; // 일반감가기준 R&D비
	    g_rnd_cost = new String[aa][bb][cc]; // 판매기준 R&D비
	    s_roy_cost = new String[aa][bb][cc]; // 일반감가기준 로열티
	    s_actual_cost = new String[aa][bb][cc]; // 일반감가기준 총원가
	    g_roy_cost = new String[aa][bb][cc]; // 판매기준 로열티
	    g_actual_cost = new String[aa][bb][cc]; // 판매기준 총원가
	    s_earn_rate = new String[aa][bb][cc]; // 일반감가기준 수익률
	    g_earn_rate = new String[aa][bb][cc]; // 판매기준 수익률

	    // 조립
	    s_assy_m_total_cost = new String[aa]; // 단품원가총합(재료비산출에사용) -일반감가
	    g_assy_m_total_cost = new String[aa]; // 단품원가총합(재료비산출에사용) - 판매수량
	    s_assy_meterial_cost = new String[aa]; // 조립 재료비
	    g_assy_meterial_cost = new String[aa]; // 조립 재료비
	    s_assy_loss = new String[aa]; // loss비-일반
	    g_assy_loss = new String[aa]; // loss비-판매
	    assy_scrap_cost = new String[aa]; // 스크랩판매비
	    assy_labor_cost = new String[aa]; // 노무비
	    assy_common_cost = new String[aa]; // 제조경비
	    assy_s_depr_cost = new String[aa]; // 일반감가비
	    assy_g_depr_cost = new String[aa]; // 기획수량감가비
	    assy_ad_cost = new String[aa]; // 관리비
	    assy_output = new String[aa]; // 시간당 생산량
	    assy_rate = new String[aa]; // 임율
	    assy_rnd_cost = new String[aa]; // R&D비율
	    assy_jun_cost = new String[aa]; // 전력비
	    assy_ma_depr = new String[aa]; // 기계감가
	    // assy_m_upkeep_ = new String[aa]; //금형유지비
	    assy_total_expense = new String[aa]; // 직접경비
	    assy_overhead = new String[aa]; // 간접경비
	    assy_pack_cost = new String[aa]; // 포장비
	    assy_s_avg_su = new String[aa]; // 일반감가기준수량
	    assy_avg_su = new String[aa]; // 총 판매기획수량
	    assy_process_cost = new String[aa]; // 가공비
	    assy_jae_cost = new String[aa]; // 재료관리비
	    assy_ge_cost = new String[aa]; // 일반관리비
	    assy_s_rnd_cost = new String[aa]; // 일반감가기준 R&D비
	    assy_g_rnd_cost = new String[aa]; // 판매기준 R&D비
	    assy_s_roy_cost = new String[aa]; // 일반감가기준 로열티
	    assy_s_actual_cost = new String[aa]; // 일반감가기준 총원가
	    assy_g_roy_cost = new String[aa]; // 판매기준 로열티
	    assy_g_actual_cost = new String[aa]; // 판매기준 총원가
	    assy_s_earn_rate = new String[aa]; // 일반감가기준 수익률
	    assy_g_earn_rate = new String[aa]; // 판매기준 수익률

	    // sub 조립
	    s_sub_m_total_cost = new String[aa][bb]; // 단품원가총합(재료비산출에사용) -일반감가
	    g_sub_m_total_cost = new String[aa][bb]; // 단품원가총합(재료비산출에사용) - 판매수량
	    s_sub_meterial_cost = new String[bb]; // 조립 재료비
	    g_sub_meterial_cost = new String[bb]; // 조립 재료비
	    s_sub_loss = new String[bb]; // loss비-일반
	    g_sub_loss = new String[bb]; // loss비-판매
	    sub_scrap_cost = new String[bb]; // 스크랩판매비
	    sub_labor_cost = new String[bb]; // 노무비
	    sub_common_cost = new String[bb]; // 제조경비
	    sub_s_depr_cost = new String[bb]; // 일반감가비
	    sub_g_depr_cost = new String[bb]; // 기획수량감가비
	    sub_ad_cost = new String[bb]; // 관리비
	    sub_output = new String[bb]; // 시간당 생산량
	    sub_rate = new String[bb]; // 임율
	    sub_rnd_cost = new String[bb]; // R&D비율
	    sub_jun_cost = new String[bb]; // 전력비
	    sub_ma_depr = new String[bb]; // 기계감가
	    // sub_m_upkeep_ = new String[bb]; //금형유지비
	    sub_total_expense = new String[bb]; // 직접경비
	    sub_overhead = new String[bb]; // 간접경비
	    sub_pack_cost = new String[bb]; // 포장비
	    sub_s_avg_su = new String[bb]; // 일반감가기준수량
	    sub_avg_su = new String[bb]; // 총 판매기획수량
	    sub_process_cost = new String[bb]; // 가공비
	    sub_jae_cost = new String[bb]; // 재료관리비
	    sub_ge_cost = new String[bb]; // 일반관리비
	    sub_s_rnd_cost = new String[bb]; // 일반감가기준 R&D비
	    sub_g_rnd_cost = new String[bb]; // 판매기준 R&D비
	    sub_s_roy_cost = new String[bb]; // 일반감가기준 로열티
	    sub_s_actual_cost = new String[bb]; // 일반감가기준 총원가
	    sub_g_roy_cost = new String[bb]; // 판매기준 로열티
	    sub_g_actual_cost = new String[bb]; // 판매기준 총원가
	    sub_s_earn_rate = new String[bb]; // 일반감가기준 수익률
	    sub_g_earn_rate = new String[bb]; // 판매기준 수익률

	    // 최종 SUM
	    sum_meterial = new String[aa][bb][cc]; // 제조원가
	    sum_m_total_cost = new String[aa][bb][cc];
	    sum_labor_cost = new String[aa][bb][cc];
	    sum_common_cost = new String[aa][bb][cc];
	    sum_sum_meterial = new String[aa][bb][cc];
	    sum_ad_cost = new String[aa][bb][cc];
	    sum_g_depr_cost = new String[aa][bb][cc];
	    sum_s_actual_cost = new String[aa][bb][cc];
	    sum_g_actual_cost = new String[aa][bb][cc];

	    pl_meterial_cost = new String[aa][bb][cc];
	    pl_loss = new String[aa][bb][cc];
	    pl_scrap_cost = new String[aa][bb][cc];
	    pl_m_total_cost = new String[aa][bb][cc];
	    pl_output = new String[aa][bb][cc];
	    pl_rate = new String[aa][bb][cc];
	    pl_labor_cost = new String[aa][bb][cc];
	    pl_jun_cost = new String[aa][bb][cc];
	    pl_ma_depr = new String[aa][bb][cc];
	    pl_tabalu = new String[aa][bb][cc];
	    pl_m_upkeep = new String[aa][bb][cc];
	    pl_total_expense = new String[aa][bb][cc];
	    pl_overhead = new String[aa][bb][cc];
	    pl_common_cost = new String[aa][bb][cc];
	    pl_yzk_depr = new String[aa][bb][cc];
	    pl_start_depr = new String[aa][bb][cc];
	    pl_pro_depr = new String[aa][bb][cc];
	    pl_etc_depr = new String[aa][bb][cc];
	    pl_jae_cost = new String[aa][bb][cc];
	    pl_ge_cost = new String[aa][bb][cc];
	    pl_rnd_cost = new String[aa][bb][cc];
	    pl_tariff = new String[aa][bb][cc];
	    pl_royalty_cost = new String[aa][bb][cc];
	    pl_actual_cost = new String[aa][bb][cc];

	    re_m_cost = new String[aa][bb][cc];
	    etc_depr = new String[aa][bb][cc];

	    lme_ton = gdReq.getParam("lme_ton") != null ? gdReq.getParam("lme_ton") : "";
	    u_ex_rate = gdReq.getParam("u_ex_rate") != null ? gdReq.getParam("u_ex_rate") : "";
	    y_ex_rate = gdReq.getParam("y_ex_rate") != null ? gdReq.getParam("y_ex_rate") : "";

	    BigDecimal culc_1 = new BigDecimal("0");// 소수점 계산용
	    BigDecimal culc_2 = new BigDecimal("0");// 소수점 계산용
	    BigDecimal culc_3 = new BigDecimal("0");// 소수점 계산용

	    if (!"work".equals(page_name)) {
		CostAccDao costAccDao = null;

		// System.out.println("page_name : "+page_name);
		// System.out.println("table_row : "+table_row);
		for (int j = 0; j <= Integer.parseInt(table_row) - 1; j++) {
		    if (j < 9) {
			k = "00" + Integer.toString(j + 1);
		    } else if (j < 99) {
			k = "0" + Integer.toString(j + 1);
		    } else if (j < 999) {
			k = Integer.toString(j + 1);
		    }
		    conn = DBUtil.getConnection();
		    costAccDao = new CostAccDao(conn);
		    // result
		    ArrayList accRequestList = null;
		    try {
			accRequestList = costAccDao.getAccRequestList(pk_cr_group_1, k, rev_no);
		    } catch (Exception e) {
			e.printStackTrace();
		    } finally {
			DBUtil.close(conn);
		    }
		    Hashtable accRequestMap = null;
		    // System.out.println("pk_cr_group_1 : "+pk_cr_group_1);
		    // System.out.println("kkkkk : "+k);
		    // System.out.println("rev_no : "+rev_no);

		    if (accRequestList.size() > 0) {
			// GridData에 데이터를 셋팅합니다.
			for (int i = 0; i < accRequestList.size(); i++) {
			    // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
			    // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
			    // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.
			    accRequestMap = (Hashtable) accRequestList.get(i);
			    a_su_year_1[j] = (String) accRequestMap.get("su_year_1");
			    // System.out.println("a_su_year_1[j] : ===> "+a_su_year_1[j]);
			    a_su_year_2[j] = (String) accRequestMap.get("su_year_2");
			    a_su_year_3[j] = (String) accRequestMap.get("su_year_3");
			    a_su_year_4[j] = (String) accRequestMap.get("su_year_4");
			    a_su_year_5[j] = (String) accRequestMap.get("su_year_5");
			    a_su_year_6[j] = (String) accRequestMap.get("su_year_6");
			    a_su_year_7[j] = (String) accRequestMap.get("su_year_7");
			    a_su_year_8[j] = (String) accRequestMap.get("su_year_8");
			    ket_cost[j] = (String) accRequestMap.get("ket_cost");
			    target_cost[j] = (String) accRequestMap.get("target_cost");

			    if (!"".equals(StringUtil.checkNull(a_su_year_1[j]))) {
				// a_su_year_1[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_1[j]));
				a_su_year_1[j] = StringUtil.Roundformat(a_su_year_1[j], 0);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_2[j]))) {
				// a_su_year_2[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_2[j]));
				a_su_year_2[j] = StringUtil.Roundformat(a_su_year_2[j], 0);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_3[j]))) {
				// a_su_year_3[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_3[j]));
				a_su_year_3[j] = StringUtil.Roundformat(a_su_year_3[j], 0);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_4[j]))) {
				// a_su_year_4[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_4[j]));
				a_su_year_4[j] = StringUtil.Roundformat(a_su_year_4[j], 0);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_5[j]))) {
				// a_su_year_5[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_5[j]));
				a_su_year_5[j] = StringUtil.Roundformat(a_su_year_5[j], 0);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_6[j]))) {
				// a_su_year_6[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_6[j]));
				a_su_year_6[j] = StringUtil.Roundformat(a_su_year_6[j], 0);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_7[j]))) {
				// a_su_year_7[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_7[j]));
				a_su_year_7[j] = StringUtil.Roundformat(a_su_year_7[j], 0);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_8[j]))) {
				// a_su_year_8[j] = String.format("%.2f", StringUtil.nullDouble(a_su_year_8[j]));
				a_su_year_8[j] = StringUtil.Roundformat(a_su_year_8[j], 0);
			    }
			}
		    }
		} // table_row for end
	    }

	    for (int i = 0; i < rowCount; i++) {
		if (gdReq3.getHeader("group_no").getValue(i).length() < 5) { // T001
		    if ("work".equals(page_name)) {
			a = 0;
			b = 0;
			c = 0;
		    } else {
			a = Integer.parseInt(gdReq3.getHeader("group_no").getValue(i).replaceAll("T", "")) - 1;
			b = 0;
			c = 0;
		    }
		} else if (gdReq3.getHeader("group_no").getValue(i).length() < 8) {
		    assy_in[a] = "ok";
		    b = Integer.parseInt(gdReq3.getHeader("group_no").getValue(i).substring(5));
		    c = 0;
		} else {
		    c = Integer.parseInt(gdReq3.getHeader("group_no").getValue(i).substring(8));
		}
		distri_cost[a] = "";
		if (!"work".equals(page_name)) {
		    unitcost_txt[a][b][c] = gdReq3.getHeader("unitcost_txt").getValue(i);
		    store[a] = gdReq3.getHeader("store").getValue(i);
		    dest[a] = gdReq3.getHeader("dest").getValue(i);
		    dis_cost[a][b][c] = gdReq3.getHeader("dis_cost").getValue(i);
		    // distri_cost[a] = gdReq3.getHeader("distri_cost").getValue(i);
		    distri_cost_ = gdReq3.getHeader("distri_cost").getValue(i);
		    royalty[a] = gdReq3.getHeader("royalty").getComboHiddenValues()[gdReq3.getHeader("royalty").getSelectedIndex(i)];
		    pack_cost[a][b][c] = gdReq3.getHeader("pack_cost").getValue(i);
		} else {
		    c_unitcost[a][b][c] = gdReq3.getHeader("c_unitcost").getValue(i);
		    try {
			recycle_state[a][b][c] = gdReq3.getHeader("recycle_state").getComboHiddenValues()[gdReq3.getHeader("recycle_state")
			        .getSelectedIndex(i)];
		    } catch (Exception e) {
			recycle_state[a][b][c] = "";
		    }
		    dis_cost[a][b][c] = "0";
		    // distri_cost[a] = "0";
		    distri_cost_ = "";

		    a_su_year_1[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_1").getValue(i), 0);
		    a_su_year_2[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_2").getValue(i), 0);
		    a_su_year_3[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_3").getValue(i), 0);
		    a_su_year_4[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_4").getValue(i), 0);
		    a_su_year_5[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_5").getValue(i), 0);
		    a_su_year_6[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_6").getValue(i), 0);
		    a_su_year_7[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_7").getValue(i), 0);
		    a_su_year_8[0] = StringUtil.Roundformat(gdReq3.getHeader("su_year_8").getValue(i), 0);

		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_1").getValue(i)))) {
			p_su_year_1[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_1").getValue(i), 0);
		    }
		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_2").getValue(i)))) {
			p_su_year_2[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_2").getValue(i), 0);
		    }
		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_3").getValue(i)))) {
			p_su_year_3[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_3").getValue(i), 0);
		    }
		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_4").getValue(i)))) {
			p_su_year_4[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_4").getValue(i), 0);
		    }
		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_5").getValue(i)))) {
			p_su_year_5[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_5").getValue(i), 0);
		    }
		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_6").getValue(i)))) {
			p_su_year_6[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_6").getValue(i), 0);
		    }
		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_7").getValue(i)))) {
			p_su_year_7[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_7").getValue(i), 0);
		    }
		    if (!"".equals(StringUtil.checkNull(gdReq3.getHeader("p_su_year_8").getValue(i)))) {
			p_su_year_8[0] = StringUtil.Roundformat(gdReq3.getHeader("p_su_year_8").getValue(i), 0);
		    }

		    ket_cost[0] = gdReq3.getHeader("ket_cost").getValue(i);
		    target_cost[0] = gdReq3.getHeader("target_cost").getValue(i);

		    p_su_chk = gdReq3.getHeader("p_su_chk").getValue(i);
		    store[a] = gdReq3.getHeader("store").getValue(i);
		    dest[a] = gdReq3.getHeader("dest").getValue(i);
		    royalty[a] = gdReq3.getHeader("royalty").getValue(i);
		    c_lme_ton = gdReq3.getHeader("lme_ton").getValue(i);
		    c_u_ex_rate = gdReq3.getHeader("u_ex_rate").getValue(i);
		    c_y_ex_rate = gdReq3.getHeader("y_ex_rate").getValue(i);
		    r_USD_rate = gdReq3.getHeader("USD_rate").getValue(i);
		    r_EURO_rate = gdReq3.getHeader("EURO_rate").getValue(i);
		    r_YEN_rate = gdReq3.getHeader("YEN_rate").getValue(i);
		    r_CNY_rate = gdReq3.getHeader("CNY_rate").getValue(i);
		}

		case_count_1[a] = gdReq3.getHeader("case_count_1").getValue(i);
		case_count_2[a][b] = gdReq3.getHeader("case_count_2").getValue(i);
		make[a][b][c] = gdReq3.getHeader("make").getValue(i);
		group_no[a][b][c] = gdReq3.getHeader("group_no").getValue(i);
		pro_type[a][b][c] = gdReq3.getHeader("pro_type").getComboHiddenValues()[gdReq3.getHeader("pro_type").getSelectedIndex(i)];
		part_name[a][b][c] = gdReq3.getHeader("part_name").getValue(i);
		part_type_1[a][b][c] = gdReq3.getHeader("part_type").getComboHiddenValues()[gdReq3.getHeader("part_type").getSelectedIndex(
		        i)];
		mix_group_1[a][b][c] = gdReq3.getHeader("mix_group").getComboHiddenValues()[gdReq3.getHeader("mix_group").getSelectedIndex(
		        i)];
		usage[a][b][c] = gdReq3.getHeader("usage").getValue(i);
		meterial[a][b][c] = gdReq3.getHeader("meterial").getValue(i);
		t_size[a][b][c] = gdReq3.getHeader("t_size").getValue(i);
		w_size[a][b][c] = gdReq3.getHeader("w_size").getValue(i);
		p_size[a][b][c] = gdReq3.getHeader("p_size").getValue(i);
		plating[a][b][c] = gdReq3.getHeader("plating").getValue(i);
		m_maker[a][b][c] = gdReq3.getHeader("m_maker").getValue(i);
		m_mone[a][b][c] = gdReq3.getHeader("m_mone").getValue(i);
		unitcost[a][b][c] = gdReq3.getHeader("unitcost").getValue(i);
		grade_name[a][b][c] = gdReq3.getHeader("grade_name").getValue(i);
		grade_color[a][b][c] = gdReq3.getHeader("grade_color").getValue(i);
		order_con[a][b][c] = gdReq3.getHeader("order_con").getValue(i);
		h_weight[a][b][c] = gdReq3.getHeader("h_weight").getValue(i);
		h_scrap[a][b][c] = gdReq3.getHeader("h_scrap").getValue(i);
		t_weight[a][b][c] = gdReq3.getHeader("t_weight").getValue(i);
		recycle[a][b][c] = gdReq3.getHeader("recycle").getValue(i);
		cavity[a][b][c] = gdReq3.getHeader("cavity").getValue(i);
		m_su[a][b][c] = gdReq3.getHeader("m_su").getValue(i);
		mold_cost[a][b][c] = gdReq3.getHeader("mold_cost").getValue(i);
		try {
		    mold_c_type[a][b][c] = gdReq3.getHeader("mold_c_type").getComboHiddenValues()[gdReq3.getHeader("mold_c_type")
			    .getSelectedIndex(i)];
		} catch (Exception e) {
		    mold_c_type[a][b][c] = "";
		}
		try {
		    make_type[a][b][c] = gdReq3.getHeader("make_type").getComboHiddenValues()[gdReq3.getHeader("make_type")
			    .getSelectedIndex(i)];
		} catch (Exception e) {
		    make_type[a][b][c] = "";
		}
		try {
		    pro_1[a][b][c] = gdReq3.getHeader("pro_1").getComboHiddenValues()[gdReq3.getHeader("pro_1").getSelectedIndex(i)];
		} catch (Exception e) {
		    pro_1[a][b][c] = "";
		}
		try {
		    start_pro[a] = gdReq3.getHeader("pro_1").getComboHiddenValues()[gdReq3.getHeader("pro_1").getSelectedIndex(i)];
		} catch (Exception e) {
		    start_pro[a] = "";
		}
		ton[a][b][c] = gdReq3.getHeader("ton").getValue(i);
		spm[a][b][c] = gdReq3.getHeader("spm").getValue(i);
		try {
		    method_type[a][b][c] = gdReq3.getHeader("method_type").getComboHiddenValues()[gdReq3.getHeader("method_type")
			    .getSelectedIndex(i)];
		} catch (Exception e) {
		    method_type[a][b][c] = "";
		}
		try {
		    pro_level[a][b][c] = gdReq3.getHeader("pro_level").getComboHiddenValues()[gdReq3.getHeader("pro_level")
			    .getSelectedIndex(i)];
		} catch (Exception e) {
		    pro_level[a][b][c] = "";
		}
		pro_level_txt[a][b][c] = gdReq3.getHeader("pro_level_txt").getValue(i);
		line_su[a][b][c] = gdReq3.getHeader("line_su").getValue(i);
		sul_cost[a][b][c] = gdReq3.getHeader("sul_cost").getValue(i);
		// System.out.println("디에이오 sul_cost : "+gdReq3.getHeader("sul_cost").getValue(i));
		try {
		    plating_type[a][b][c] = gdReq3.getHeader("plating_type").getComboHiddenValues()[gdReq3.getHeader("plating_type")
			    .getSelectedIndex(i)];
		} catch (Exception e) {
		    plating_type[a][b][c] = "";
		}
		try {
		    type_2[a][b][c] = gdReq3.getHeader("type_2").getComboHiddenValues()[gdReq3.getHeader("type_2").getSelectedIndex(i)];
		} catch (Exception e) {
		    type_2[a][b][c] = "";
		}
		plating_cost[a][b][c] = gdReq3.getHeader("plating_cost").getValue(i);
		try {
		    type_1[a][b][c] = gdReq3.getHeader("type_1").getComboHiddenValues()[gdReq3.getHeader("type_1").getSelectedIndex(i)];
		} catch (Exception e) {
		    type_1[a][b][c] = "";
		}
		try {
		    t_mone[a][b][c] = gdReq3.getHeader("t_mone").getComboHiddenValues()[gdReq3.getHeader("t_mone").getSelectedIndex(i)];
		} catch (Exception e) {
		    t_mone[a][b][c] = "";
		}
		type_cost[a][b][c] = gdReq3.getHeader("type_cost").getValue(i);
		try {
		    t_order[a][b][c] = gdReq3.getHeader("t_order").getComboHiddenValues()[gdReq3.getHeader("t_order").getSelectedIndex(i)];
		} catch (Exception e) {
		    t_order[a][b][c] = "";
		}
		try {
		    pack_type[a][b][c] = gdReq3.getHeader("pack_type").getComboHiddenValues()[gdReq3.getHeader("pack_type")
			    .getSelectedIndex(i)];
		} catch (Exception e) {
		    pack_type[a][b][c] = "";
		}
		in_pack[a][b][c] = gdReq3.getHeader("in_pack").getValue(i);
		out_pack[a][b][c] = gdReq3.getHeader("out_pack").getValue(i);
		yazaki_ro[a][b][c] = gdReq3.getHeader("yazaki_ro").getValue(i);
		etc_cost[a][b][c] = gdReq3.getHeader("etc_cost").getValue(i);
	    }

	    if ("work".equals(page_name)) {
		rowCount4 = Integer.parseInt(gdReq3.getParam("table_row2"));
		for (int i = 0; i < rowCount4; i++) {
		    if (gdReq4.getHeader("group_no").getValue(i).length() < 5) { // T001
			/*
		         * if(!StringUtil.checkNull(gdReq4.getHeader("group_no").getValue(i)).equals("")){ a2 =
		         * Integer.parseInt(gdReq4.getHeader("group_no").getValue(i).replaceAll("T", "")) - 1; }else{ a2 = 0; }
		         */
			a2 = 0;
			c2 = 0;
		    } else if (gdReq4.getHeader("group_no").getValue(i).length() < 8) { //
			b2 = Integer.parseInt(gdReq4.getHeader("group_no").getValue(i).substring(5));
			c2 = 0;
		    } else {
			c2 = Integer.parseInt(gdReq4.getHeader("group_no").getValue(i).substring(8));
		    }

		    pk_cw[a2][b2][c2] = gdReq4.getHeader("pk_cw").getValue(i);
		    pk_cr_group[a2][b2][c2] = gdReq4.getHeader("pk_cr_group").getValue(i);
		    scrap_rate[a2][b2][c2] = gdReq4.getHeader("scrap_rate").getValue(i);
		    eff_value[a2][b2][c2] = gdReq4.getHeader("eff_value").getValue(i);
		    pack_cost[a2][b2][c2] = gdReq4.getHeader("pack_cost").getValue(i);
		    gita_cost[a2][b2][c2] = gdReq4.getHeader("gita_cost").getValue(i);
		    qu_cost[a2][b2][c2] = gdReq4.getHeader("qu_cost").getValue(i);
		    dis_cost[a2][b2][c2] = gdReq4.getHeader("dis_cost").getValue(i);
		    try {
			mold_type[a2][b2][c2] = StringUtil.checkNull(gdReq4.getHeader("mold_type").getComboHiddenValues()[gdReq4.getHeader(
			        "mold_type").getSelectedIndex(i)]);
		    } catch (Exception e) {
			mold_type[a2][b2][c2] = "";
		    }
		    i = i + 1;
		    pl_meterial_cost[a2][b2][c2] = gdReq4.getHeader("meterial_cost").getValue(i);
		    pl_loss[a2][b2][c2] = gdReq4.getHeader("loss").getValue(i);
		    pl_scrap_cost[a2][b2][c2] = gdReq4.getHeader("scrap_cost").getValue(i);
		    pl_m_total_cost[a2][b2][c2] = gdReq4.getHeader("m_total_cost").getValue(i);
		    pl_output[a2][b2][c2] = gdReq4.getHeader("output").getValue(i);
		    pl_rate[a2][b2][c2] = gdReq4.getHeader("rate").getValue(i);
		    pl_labor_cost[a2][b2][c2] = gdReq4.getHeader("labor_cost").getValue(i);
		    pl_jun_cost[a2][b2][c2] = gdReq4.getHeader("jun_cost").getValue(i);
		    pl_ma_depr[a2][b2][c2] = gdReq4.getHeader("ma_depr").getValue(i);
		    pl_tabalu[a2][b2][c2] = gdReq4.getHeader("tabalu").getValue(i);
		    pl_m_upkeep[a2][b2][c2] = gdReq4.getHeader("m_upkeep").getValue(i);
		    pl_total_expense[a2][b2][c2] = gdReq4.getHeader("total_expense").getValue(i);
		    pl_overhead[a2][b2][c2] = gdReq4.getHeader("overhead").getValue(i);
		    pl_common_cost[a2][b2][c2] = gdReq4.getHeader("common_cost").getValue(i);
		    pl_yzk_depr[a2][b2][c2] = gdReq4.getHeader("yzk_depr").getValue(i);
		    pl_start_depr[a2][b2][c2] = gdReq4.getHeader("start_depr").getValue(i);
		    pl_pro_depr[a2][b2][c2] = gdReq4.getHeader("pro_depr").getValue(i);
		    pl_etc_depr[a2][b2][c2] = gdReq4.getHeader("etc_depr").getValue(i);
		    pl_jae_cost[a2][b2][c2] = gdReq4.getHeader("jae_cost").getValue(i);
		    pl_ge_cost[a2][b2][c2] = gdReq4.getHeader("ge_cost").getValue(i);
		    pl_rnd_cost[a2][b2][c2] = gdReq4.getHeader("rnd_cost").getValue(i);
		    pl_tariff[a2][b2][c2] = gdReq4.getHeader("tariff").getValue(i);
		    pl_royalty_cost[a2][b2][c2] = gdReq4.getHeader("royalty_cost").getValue(i);
		    pl_actual_cost[a2][b2][c2] = gdReq4.getHeader("actual_cost").getValue(i);
		}
	    } else {
		for (int i = 0; i < rowCount; i++) {
		    if (gdReq3.getHeader("group_no").getValue(i).length() < 5) { // T001
			if ("work".equals(page_name)) {
			    a = 0;
			    b = 0;
			    c = 0;
			} else {
			    a = Integer.parseInt(gdReq3.getHeader("group_no").getValue(i).replaceAll("T", "")) - 1;
			    b = 0;
			    c = 0;
			}
		    } else if (gdReq3.getHeader("group_no").getValue(i).length() < 8) {
			assy_in[a] = "ok";
			b = Integer.parseInt(gdReq3.getHeader("group_no").getValue(i).substring(5));
			c = 0;
		    } else {
			c = Integer.parseInt(gdReq3.getHeader("group_no").getValue(i).substring(8));
		    }

		    // pk_cw[a][b][c]="";
		    // pk_cr_group[a][b][c]="";
		    // scrap_rate[a][b][c]="";
		    // eff_value[a][b][c]="";
		    // pack_cost[a][b][c]="";
		    // gita_cost[a][b][c]="";
		    // qu_cost[a][b][c]="";
		    // dis_cost[a][b][c]="";
		    // mold_type[a][b][c]="";

		    pl_meterial_cost[a][b][c] = "";
		    pl_loss[a][b][c] = "";
		    pl_scrap_cost[a][b][c] = "";
		    pl_m_total_cost[a][b][c] = "";
		    pl_output[a][b][c] = "";
		    pl_rate[a][b][c] = "";
		    pl_labor_cost[a][b][c] = "";
		    pl_jun_cost[a][b][c] = "";
		    pl_ma_depr[a][b][c] = "";
		    pl_tabalu[a][b][c] = "";
		    pl_m_upkeep[a][b][c] = "";
		    pl_total_expense[a][b][c] = "";
		    pl_overhead[a][b][c] = "";
		    pl_common_cost[a][b][c] = "";
		    pl_yzk_depr[a][b][c] = "";
		    pl_start_depr[a][b][c] = "";
		    pl_pro_depr[a][b][c] = "";
		    pl_etc_depr[a][b][c] = "";
		    pl_jae_cost[a][b][c] = "";
		    pl_ge_cost[a][b][c] = "";
		    pl_rnd_cost[a][b][c] = "";
		    pl_tariff[a][b][c] = "";
		    pl_royalty_cost[a][b][c] = "";
		    pl_actual_cost[a][b][c] = "";
		}
	    }

	    for (int i = 0; i < gdReq3.getHeader("SELECTED").getRowCount(); i++) {
		if (gdReq3.getHeader("group_no").getValue(i).length() < 5) { // T001
		    aa++;
		} else if (gdReq3.getHeader("group_no").getValue(i).length() < 8) {
		    bb++;
		} else {
		    cc++;
		}
	    }
	    // 배열선언
	    s_sub_m_total_cost = new String[aa][bb];
	    g_sub_m_total_cost = new String[aa][bb];

	    /**************** 원가산출 START **************/
	    cost_acc(page_name);
	    /**************** 원가산출 END ****************/

	    int case1 = 0;
	    int case_count1 = 0;
	    int case_count2 = 0;
	    int i = 0;
	    int tb_count = 0;
	    tb_count = Integer.parseInt(StringUtil.checkNullZero(table_row_count));
	    if (tb_count < 1) {
		tb_count = 1;
	    }
	    for (int j = 0; j <= tb_count - 1; j++) {
		for (case1 = 0; case1 <= Integer.parseInt(StringUtil.checkNullZero(case_count_1[j])); case1++) {
		    for (int case2 = 0; case2 <= Integer.parseInt(StringUtil.checkNullZero(case_count_2[j][case1])); case2++) {
			if ((j == 0 && case1 == 0 && case2 == 0) || ("0".equals(case_count_1[j]))) {

			} else {
			    test_value = test_value + ",";
			    flag = "2";
			}
			if (!"".equals(StringUtil.checkNull(m_total_cost[j][case1][case2]))) {
			    m_total_cost[j][case1][case2] = m_total_cost[j][case1][case2];
			} else {
			    m_total_cost[j][case1][case2] = "0";
			}
			if (!"".equals(StringUtil.checkNull(labor_cost[j][case1][case2]))) {
			    labor_cost[j][case1][case2] = labor_cost[j][case1][case2];
			} else {
			    labor_cost[j][case1][case2] = "0";
			}

			if (!"".equals(StringUtil.checkNull(common_cost[j][case1][case2]))) {
			    common_cost[j][case1][case2] = common_cost[j][case1][case2];
			} else {
			    common_cost[j][case1][case2] = "0";
			}

			if (!"".equals(StringUtil.checkNull(sum_labor_cost[j][0][0]))) {
			    if (!"0".equals(labor_cost[j][case1][case2])) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(sum_labor_cost[j][0][0], 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(labor_cost[j][case1][case2], 4));

				sum_labor_cost[j][0][0] = Double.toString(culc_1.add(culc_2).doubleValue());
				// sum_labor_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_labor_cost[j][0][0]) +
				// StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(labor_cost[j][case1][case2]))));
			    }
			} else {
			    if (!"0".equals(labor_cost[j][case1][case2])) {
				sum_labor_cost[j][0][0] = StringUtil.Roundformat(labor_cost[j][case1][case2], 4);
			    }
			}

			if (!"".equals(StringUtil.checkNull(sum_common_cost[j][0][0]))) {
			    if (!"0".equals(common_cost[j][case1][case2])) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(sum_common_cost[j][0][0], 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(common_cost[j][case1][case2], 4));

				sum_common_cost[j][0][0] = Double.toString(culc_1.add(culc_2).doubleValue());

				// sum_common_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_common_cost[j][0][0]) +
				// StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(common_cost[j][case1][case2]))));
			    }
			} else {
			    if (!"0".equals(common_cost[j][case1][case2])) {
				sum_common_cost[j][0][0] = StringUtil.Roundformat(common_cost[j][case1][case2], 4);
			    }
			}

			if (!"".equals(StringUtil.checkNull(mix_group_1[j][case1][case2]))) {
			    mix_group_1[j][case1][case2] = mix_group_1[j][case1][case2];
			} else {
			    mix_group_1[j][case1][case2] = "empty";
			}
			// //System.out.println("ad_cost : "+ad_cost[j][case1][case2]);
			// //System.out.println("sum_ad_cost : "+sum_ad_cost[j][0][0]);
			if (!"".equals(StringUtil.checkNull(sum_ad_cost[j][0][0]))) {
			    if (!"".equals(StringUtil.checkNull(ad_cost[j][case1][case2]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(sum_ad_cost[j][0][0], 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(ad_cost[j][case1][case2], 0));

				sum_ad_cost[j][0][0] = Double.toString(culc_1.add(culc_2).doubleValue());
				// sum_ad_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_ad_cost[j][0][0]) +
				// StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(ad_cost[j][case1][case2]))));
			    } else {
				ad_cost[j][case1][case2] = "0";
			    }
			} else {
			    if (!"".equals(StringUtil.checkNull(ad_cost[j][case1][case2]))) {
				sum_ad_cost[j][0][0] = StringUtil.Roundformat(ad_cost[j][case1][case2], 4);
			    }
			}

			// cost_acc_test 1000 line
			if (!"Insert".equals(pro_type[j][case1][case2])) {
			    if (!"".equals(StringUtil.checkNull(g_depr_cost[j][case1][case2]))) {
				if (!"".equals(StringUtil.checkNull(sum_g_depr_cost[j][0][0]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(sum_g_depr_cost[j][0][0], 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(g_depr_cost[j][case1][case2], 4));

				    sum_g_depr_cost[j][0][0] = Double.toString(culc_1.add(culc_2).doubleValue());
				    // sum_g_depr_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_g_depr_cost[j][0][0]) +
				    // StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(g_depr_cost[j][case1][case2]))));
				} else {
				    sum_g_depr_cost[j][0][0] = StringUtil.Roundformat(g_depr_cost[j][case1][case2], 4);
				}
			    } else {
				if ("assy".equals(pro_type[j][case1][case2])) {
				    // //System.out.println("assy 일때 조립감가비 : "+assy_g_depr_cost[j]);
				    g_depr_cost[j][case1][case2] = assy_g_depr_cost[j];
				} else {
				    g_depr_cost[j][case1][case2] = "0";
				}
			    }
			} else {
			    in_start_depr = g_depr_cost[j][case1][case2];
			    g_depr_cost[j][case1][case2] = assy_g_depr_cost[j];
			}
			// //System.out.println("assy 일때 조립감가비2 : "+g_depr_cost[j][case1][case2]);
			// cost_acc_test 1020
			if (!"assy".equals(pro_type[j][case1][case2]) && !"sub_assy".equals(pro_type[j][case1][case2])) {
			    if (!"".equals(StringUtil.checkNull(sum_m_total_cost[j][0][0]))) {
				if (!"0".equals(m_total_cost[j][case1][case2])) {
				    // sum_m_total_cost[j][0][0] = String.format("%.4f", StringUtil.nullDouble(sum_m_total_cost[j][0][0]) +
				    // StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(m_total_cost[j][case1][case2]))));
				    culc_1 = new BigDecimal(StringUtil.Roundformat(sum_m_total_cost[j][0][0], 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(m_total_cost[j][case1][case2], 4));

				    sum_m_total_cost[j][0][0] = Double.toString(culc_1.add(culc_2).doubleValue());
				    // sum_m_total_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_m_total_cost[j][0][0]) +
				    // StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(m_total_cost[j][case1][case2]))));
				}
			    } else {
				if (!"0".equals(m_total_cost[j][case1][case2])) {
				    sum_m_total_cost[j][0][0] = StringUtil.Roundformat(m_total_cost[j][case1][case2], 4);
				}
			    }

			    culc_1 = new BigDecimal(StringUtil.Roundformat(m_total_cost[j][case1][case2], 4));
			    culc_2 = new BigDecimal(StringUtil.Roundformat(labor_cost[j][case1][case2], 4));
			    culc_3 = new BigDecimal(StringUtil.Roundformat(common_cost[j][case1][case2], 4));

			    sum_meterial[j][case1][case2] = Double.toString(culc_1.add(culc_2).add(culc_3).doubleValue());

			    // sum_meterial[j][case1][case2] = Double.toString(StringUtil.nullDouble(String.format("%.4f",
			    // StringUtil.nullDouble(m_total_cost[j][case1][case2]))) + StringUtil.nullDouble(String.format("%.4f",
			    // StringUtil.nullDouble(labor_cost[j][case1][case2]))) + StringUtil.nullDouble(String.format("%.4f",
			    // StringUtil.nullDouble(common_cost[j][case1][case2]))));

			    // 1035
			    if (!"".equals(StringUtil.checkNull(sum_sum_meterial[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(sum_sum_meterial[j][case1][case2], 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(sum_meterial[j][case1][case2], 4));

				sum_sum_meterial[j][0][0] = Double.toString(culc_1.add(culc_2).doubleValue());

				// sum_sum_meterial[j][0][0] = Double.toString(StringUtil.nullDouble(sum_sum_meterial[j][0][0]) +
				// StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_meterial[j][case1][case2]))));
			    } else {
				sum_sum_meterial[j][0][0] = StringUtil.Roundformat(sum_meterial[j][case1][case2], 4);
			    }
			} else {
			    culc_1 = new BigDecimal(StringUtil.Roundformat(m_total_cost[j][case1][case2], 0));
			    culc_2 = new BigDecimal(StringUtil.Roundformat(labor_cost[j][case1][case2], 4));
			    culc_3 = new BigDecimal(StringUtil.Roundformat(common_cost[j][case1][case2], 4));

			    sum_meterial[j][case1][case2] = Double.toString(culc_1.add(culc_2).add(culc_3).doubleValue());
			    // sum_meterial[j][case1][case2] = Double.toString(StringUtil.nullDouble(String.format("%.4f",
			    // StringUtil.nullDouble(m_total_cost[j][case1][case2]))) + StringUtil.nullDouble(String.format("%.4f",
			    // StringUtil.nullDouble(labor_cost[j][case1][case2]))) + StringUtil.nullDouble(String.format("%.4f",
			    // StringUtil.nullDouble(common_cost[j][case1][case2]))));
			    if (!"".equals(StringUtil.checkNull(sum_sum_meterial[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(sum_sum_meterial[j][0][0], 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(sum_meterial[j][case1][case2], 4));

				sum_sum_meterial[j][0][0] = Double.toString(culc_1.add(culc_2).doubleValue());

				// sum_sum_meterial[j][0][0] = Double.toString(StringUtil.nullDouble(sum_sum_meterial[j][0][0]) +
				// StringUtil.nullDouble(String.format("%.4f", StringUtil.nullDouble(sum_meterial[j][case1][case2]))));
			    } else {
				sum_sum_meterial[j][0][0] = StringUtil.Roundformat(sum_meterial[j][case1][case2], 4);
			    }
			}

			// 총원가
			if (StringUtil.nullDouble(sum_g_actual_cost[j][0][0]) > 0) {
			    g_actual_cost[j][0][0] = sum_g_actual_cost[j][0][0];
			}

			// 수익율
			if ("assy".equals(pro_type[j][case1][case2]) || "Insert".equals(pro_type[j][case1][case2])) {
			    g_earn_rate[j][0][0] = assy_g_earn_rate[j];
			}
			// //System.out.println(j+" 번째 g_earn_rate ==> "+g_earn_rate[j][0][0] );
			// //System.out.println(j+" 번째 g_actual_cost ==> "+assy_g_earn_rate[j] );

			if ("work".equals(page_name)) {
			    // //System.out.println("@@@@@@@@@@@@@@costaccctl====1" + j);
			    if (!StringUtil.checkNull(group_no[j][case1][case2]).equals("")) {
				if (group_no[j][case1][case2].length() < 5) {
				    g_sel1 = "/plm/jsp/cost/images/common/add_red.jpg";
				} else if (group_no[j][case1][case2].length() < 8) {
				    g_sel2 = "/plm/jsp/cost/images/common/add.jpg";
				} else {
				    g_sel3 = "/plm/jsp/cost/images/common/add.jpg";
				}
			    }

			    gdRes4.getHeader("CRUD").addValue("", "");
			    gdRes4.getHeader("pk_cw").addValue(pk_cw[j][case1][case2], "");
			    gdRes4.getHeader("pk_cr_group").addValue(pk_cr_group[j][case1][case2], "");
			    gdRes4.getHeader("group_no").addValue(group_no[j][case1][case2], "");
			    // System.out.println("그그그그그그그그룹~~~~"+group_no[j][case1][case2]);
			    gdRes4.getHeader("pro_type").addValue(pro_type[j][case1][case2], "");
			    gdRes4.getHeader("g_sel1").addValue("", "", g_sel1);
			    gdRes4.getHeader("g_sel2").addValue("", "", g_sel2);
			    gdRes4.getHeader("g_sel3").addValue("", "", g_sel3);
			    gdRes4.getHeader("part_name").addValue(part_name[j][case1][case2], "");
			    gdRes4.getHeader("usage").addValue(usage[j][case1][case2], "");
			    gdRes4.getHeader("meterial_cost").addValue(StringUtil.Roundformat(meterial_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("loss").addValue(StringUtil.Roundformat(loss[j][case1][case2], 4), "");
			    gdRes4.getHeader("scrap_cost").addValue(StringUtil.Roundformat(scrap_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("scrap_rate").addValue(StringUtil.Roundformat(scrap_rate[j][case1][case2], 4), "");
			    gdRes4.getHeader("m_total_cost").addValue(StringUtil.Roundformat(m_total_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("output").addValue(StringUtil.Roundformat(output[j][case1][case2], 4), "");
			    gdRes4.getHeader("rate").addValue(StringUtil.checkNull(rate[j][case1][case2]), "");
			    gdRes4.getHeader("eff_value").addValue(eff_value[j][case1][case2], "");
			    gdRes4.getHeader("labor_cost").addValue(StringUtil.Roundformat(labor_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("jun_cost").addValue(StringUtil.Roundformat(jun_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("ma_depr").addValue(StringUtil.Roundformat(ma_depr[j][case1][case2], 4), "");
			    gdRes4.getHeader("m_upkeep").addValue(StringUtil.Roundformat(m_upkeep[j][case1][case2], 4), "");

			    if (!"재료도금".equals(plating_type[j][case1][case2])) {
				gdRes4.getHeader("etc_expense").addValue(plating_cost[j][case1][case2], "");
			    } else {
				gdRes4.getHeader("etc_expense").addValue("", "");
			    }
			    gdRes4.getHeader("pack_cost").addValue(StringUtil.Roundformat(pack_cost[j][case1][case2], 4), "");
			    if (!"검사".equals(type_1[j][case1][case2]) && !"포장".equals(type_1[j][case1][case2])
				    && !"구매".equals(type_1[j][case1][case2])) {
				gdRes4.getHeader("out_cost").addValue(type_cost[j][case1][case2], "");
			    } else {
				gdRes4.getHeader("out_cost").addValue("", "");
			    }
			    gdRes4.getHeader("total_expense").addValue(StringUtil.Roundformat(total_expense[j][case1][case2], 4), "");
			    gdRes4.getHeader("overhead").addValue(StringUtil.Roundformat(overhead[j][case1][case2], 4), "");
			    gdRes4.getHeader("gita_cost").addValue(StringUtil.Roundformat(gita_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("common_cost").addValue(StringUtil.Roundformat(common_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("yzk_depr").addValue(StringUtil.Roundformat(yzk_depr[j][case1][case2], 4), "");

			    /*
		             * if(!mold_type[j][case1][case2].equals("")){
		             * gdRes4.getHeader("mold_type").addSelectedHiddenValue(StringUtil.checkNull(), ""); }
		             */
			    gdRes4.getHeader("mold_type").addSelectedHiddenValue(mold_type[j][case1][case2]);
			    // 비철

			    if ("TML".equals(pro_type[j][case1][case2]) || "TML-조립".equals(pro_type[j][case1][case2])) {
				start_depr = g_depr_cost[j][case1][case2];
				depr_cost = g_depr_cost[j][case1][case2];
				if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][case1][case2], 0));

				    start_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				    // start_depr = Double.toString(StringUtil.nullDouble(start_depr) -
				    // StringUtil.nullDouble(etc_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(yzk_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][case1][case2], 0));

				    start_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // start_depr = Double.toString(StringUtil.nullDouble(start_depr) -
				    // StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				}
				gdRes4.getHeader("tabalu").addValue(StringUtil.Roundformat(tabalu[j][case1][case2], 4), "");
				// 금형감가
				gdRes4.getHeader("start_depr").addValue(StringUtil.Roundformat(start_depr, 4), "");
				gdRes4.getHeader("pro_depr").addValue("0", "");
				gdRes4.getHeader("depr_cost").addValue(StringUtil.Roundformat(depr_cost, 4), "");
			    } else if ("HSG".equals(pro_type[j][case1][case2]) || "HSG-Box".equals(pro_type[j][case1][case2])) {
				start_depr = g_depr_cost[j][case1][case2];
				depr_cost = g_depr_cost[j][case1][case2];

				if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][case1][case2], 0));
				    start_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // start_depr = Double.toString(StringUtil.nullDouble(start_depr) -
				    // StringUtil.nullDouble(etc_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(yzk_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][case1][case2], 0));
				    start_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // start_depr = Double.toString(StringUtil.nullDouble(start_depr) -
				    // StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				}
				gdRes4.getHeader("tabalu").addValue("0", "");
				// 금형감가
				gdRes4.getHeader("start_depr").addValue(StringUtil.Roundformat(start_depr, 4), "");
				gdRes4.getHeader("pro_depr").addValue("0", "");
				gdRes4.getHeader("depr_cost").addValue(StringUtil.Roundformat(depr_cost, 4), "");
			    } else if ("assy".equals(pro_type[j][case1][case2])) {
				// 설비감가
				pro_depr = assy_g_depr_cost[j];
				depr_cost = assy_g_depr_cost[j];
				if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(etc_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(yzk_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				}
				gdRes4.getHeader("tabalu").addValue("0", "");
				// 금형감가
				gdRes4.getHeader("start_depr").addValue("0", "");
				gdRes4.getHeader("pro_depr").addValue(StringUtil.Roundformat(pro_depr, 4), "");
				gdRes4.getHeader("depr_cost").addValue(StringUtil.Roundformat(depr_cost, 4), "");
			    } else if ("Insert".equals(pro_type[j][case1][case2])) {
				start_depr = in_start_depr;
				depr_cost = assy_g_depr_cost[j];
				// 설비감가
				culc_1 = new BigDecimal(StringUtil.Roundformat(assy_g_depr_cost[j], 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j]) -
				// StringUtil.nullDouble(start_depr));
				if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(etc_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(yzk_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				}
				gdRes4.getHeader("tabalu").addValue("0", "");
				// 금형감가
				gdRes4.getHeader("start_depr").addValue(StringUtil.Roundformat(start_depr, 4), "");
				gdRes4.getHeader("pro_depr").addValue(StringUtil.Roundformat(pro_depr, 4), "");
				gdRes4.getHeader("depr_cost").addValue(StringUtil.Roundformat(depr_cost, 4), "");
			    } else if ("sub_Insert".equals(pro_type[j][case1][case2])) {
				start_depr = g_depr_cost[j][case1][case2];
				depr_cost = sub_g_depr_cost[case1];
				// 설비감가
				culc_1 = new BigDecimal(StringUtil.Roundformat(sub_g_depr_cost[case1], 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(g_depr_cost[j][case1][case2], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(sub_g_depr_cost[case1]) -
				// StringUtil.nullDouble(g_depr_cost[j][case1][case2]));
				if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(etc_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(yzk_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				}
				gdRes4.getHeader("tabalu").addValue("0", "");
				// 금형감가
				gdRes4.getHeader("start_depr").addValue(StringUtil.Roundformat(start_depr, 4), "");
				gdRes4.getHeader("pro_depr").addValue(StringUtil.Roundformat(pro_depr, 4), "");
				gdRes4.getHeader("depr_cost").addValue(StringUtil.Roundformat(depr_cost, 4), "");
			    } else if ("sub_assy".equals(pro_type[j][case1][case2])) {
				// 설비감가
				pro_depr = sub_g_depr_cost[case1];
				depr_cost = sub_g_depr_cost[case1];

				if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(etc_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))
				        || !"".equals(StringUtil.checkNull(yzk_depr[j][case1][case2]))) {
				    culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				    culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][case1][case2], 0));
				    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				    // pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) -
				    // StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				}
				gdRes4.getHeader("tabalu").addValue("0", "");
				// 금형감가
				gdRes4.getHeader("start_depr").addValue("0", "");
				gdRes4.getHeader("pro_depr").addValue(StringUtil.Roundformat(pro_depr, 4), "");
				gdRes4.getHeader("depr_cost").addValue(StringUtil.Roundformat(depr_cost, 4), "");
			    } else {
				if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
				    depr_cost = g_depr_cost[j][case1][case2];
				    // 금형감가
				    gdRes4.getHeader("start_depr").addValue("0", "");
				    gdRes4.getHeader("pro_depr").addValue("0", "");
				} else if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
				    depr_cost = g_depr_cost[j][case1][case2];
				    // 금형감가
				    gdRes4.getHeader("start_depr").addValue(StringUtil.Roundformat(depr_cost, 4), "");
				    gdRes4.getHeader("pro_depr").addValue("0", "");
				} else if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
				    depr_cost = g_depr_cost[j][case1][case2];
				    // 금형감가
				    gdRes4.getHeader("start_depr").addValue("0", "");
				    gdRes4.getHeader("pro_depr").addValue(StringUtil.Roundformat(depr_cost, 4), "");
				} else if ("구매".equals(make_type[j][case1][case2])
				        && !"".equals(StringUtil.checkNull(g_depr_cost[j][case1][case2]))) {
				    depr_cost = g_depr_cost[j][case1][case2];
				    // 금형감가
				    gdRes4.getHeader("start_depr").addValue(StringUtil.Roundformat(depr_cost, 4), "");
				    gdRes4.getHeader("pro_depr").addValue("0", "");
				} else {
				    depr_cost = "0";
				    // 금형감가
				    gdRes4.getHeader("start_depr").addValue("0", "");
				    gdRes4.getHeader("pro_depr").addValue("0", "");
				}
				gdRes4.getHeader("tabalu").addValue("0", "");
				gdRes4.getHeader("depr_cost").addValue(StringUtil.Roundformat(depr_cost, 4), "");
			    }

			    gdRes4.getHeader("etc_depr").addValue(StringUtil.Roundformat(etc_depr[j][case1][case2], 4), "");
			    gdRes4.getHeader("jae_cost").addValue(StringUtil.Roundformat(jae_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("ge_cost").addValue(StringUtil.Roundformat(ge_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("ad_cost").addValue(StringUtil.Roundformat(ad_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("rnd_cost").addValue(StringUtil.Roundformat(g_rnd_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("qu_cost").addValue(qu_cost[j][case1][case2], "");
			    gdRes4.getHeader("tariff").addValue(tariff[j][case1][case2], "");
			    gdRes4.getHeader("royalty_cost").addValue(StringUtil.Roundformat(g_roy_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("dis_cost").addValue(dis_cost[j][case1][case2], "");
			    gdRes4.getHeader("actual_cost").addValue(StringUtil.Roundformat(g_actual_cost[j][case1][case2], 4), "");
			    gdRes4.getHeader("earn_rate").addValue(StringUtil.Roundformat(g_earn_rate[j][case1][case2], 4), "");
			    // System.out.println("@@@@@@@@@@@@@@@################g_earn_rate ??? "+g_earn_rate[j][case1][case2]);
			    // System.out.println("@@@@@@@@@@@@@@@################g_earn_rate ??? "+j+" "+case1+" "+case2);
			    i = i + 1;

			    // 1261
			    gdRes4.getHeader("CRUD").addValue("", "");
			    gdRes4.getHeader("pk_cw").addValue(pk_cw[j][case1][case2], "");
			    gdRes4.getHeader("pk_cr_group").addValue("", "");
			    gdRes4.getHeader("group_no").addValue("", "");
			    gdRes4.getHeader("pro_type").addValue("", "");
			    gdRes4.getHeader("g_sel1").addValue("", "", "");
			    gdRes4.getHeader("g_sel2").addValue("", "", "");
			    gdRes4.getHeader("g_sel3").addValue("", "", "");
			    gdRes4.getHeader("part_name").addValue("산출결과값 변경", "");
			    gdRes4.getHeader("usage").addValue("", "");
			    gdRes4.getHeader("meterial_cost").addValue(pl_meterial_cost[j][case1][case2], "");
			    gdRes4.getHeader("loss").addValue(pl_loss[j][case1][case2], "");
			    gdRes4.getHeader("scrap_cost").addValue(pl_scrap_cost[j][case1][case2], "");
			    gdRes4.getHeader("scrap_rate").addValue("", "");
			    gdRes4.getHeader("m_total_cost").addValue(pl_m_total_cost[j][case1][case2], "");
			    gdRes4.getHeader("output").addValue(pl_output[j][case1][case2], "");
			    gdRes4.getHeader("rate").addValue(pl_rate[j][case1][case2], "");
			    gdRes4.getHeader("eff_value").addValue("", "");
			    gdRes4.getHeader("labor_cost").addValue(pl_labor_cost[j][case1][case2], "");
			    gdRes4.getHeader("jun_cost").addValue(pl_jun_cost[j][case1][case2], "");
			    gdRes4.getHeader("ma_depr").addValue(pl_ma_depr[j][case1][case2], "");
			    gdRes4.getHeader("tabalu").addValue(pl_tabalu[j][case1][case2], "");
			    gdRes4.getHeader("m_upkeep").addValue(pl_m_upkeep[j][case1][case2], "");
			    gdRes4.getHeader("etc_expense").addValue("", "");
			    gdRes4.getHeader("pack_cost").addValue("", "");
			    gdRes4.getHeader("out_cost").addValue("", "");
			    gdRes4.getHeader("total_expense").addValue(pl_total_expense[j][case1][case2], "");
			    gdRes4.getHeader("overhead").addValue(pl_overhead[j][case1][case2], "");
			    gdRes4.getHeader("gita_cost").addValue("", "");
			    gdRes4.getHeader("common_cost").addValue(pl_common_cost[j][case1][case2], "");
			    gdRes4.getHeader("yzk_depr").addValue(pl_yzk_depr[j][case1][case2], "");
			    gdRes4.getHeader("mold_type").addSelectedHiddenValue("empty");
			    gdRes4.getHeader("start_depr").addValue(pl_start_depr[j][case1][case2], "");
			    gdRes4.getHeader("pro_depr").addValue(pl_pro_depr[j][case1][case2], "");
			    gdRes4.getHeader("etc_depr").addValue(pl_etc_depr[j][case1][case2], "");
			    gdRes4.getHeader("depr_cost").addValue("", "");
			    gdRes4.getHeader("jae_cost").addValue(pl_jae_cost[j][case1][case2], "");
			    gdRes4.getHeader("ge_cost").addValue(pl_ge_cost[j][case1][case2], "");
			    gdRes4.getHeader("ad_cost").addValue("", "");
			    gdRes4.getHeader("rnd_cost").addValue(pl_rnd_cost[j][case1][case2], "");
			    gdRes4.getHeader("qu_cost").addValue("", "");
			    gdRes4.getHeader("tariff").addValue(pl_tariff[j][case1][case2], "");
			    gdRes4.getHeader("royalty_cost").addValue(pl_royalty_cost[j][case1][case2], "");
			    gdRes4.getHeader("dis_cost").addValue("", "");
			    gdRes4.getHeader("actual_cost").addValue(pl_actual_cost[j][case1][case2], "");
			    gdRes4.getHeader("earn_rate").addValue("", "");
			    // System.out.println("costaccctl====9");

			    i = i + 1;
			} // page_name : work if end

			if (!"Insert".equals(pro_type[j][case1][case2]) || !"복합금형".equals(part_type_1[j][0][0])) {
			    m_total_cost[j][0][0] = m_total_cost[j][0][0];
			} else {
			    if ("ok".equals(assy_in[j])) {
				m_total_cost[j][0][0] = "0";
			    }
			}

			// [{"이름":값,"이름":값},{"이름":값,"이름":값}]

			if (!"0".equals(case_count_1[j])) {
			    // System.out.println("총원가 찍기 : "+g_actual_cost[j][case1][case2]);
			    // System.out.println("수익률 1 >>> "+g_earn_rate[j][case1][case2]);
			    test_value = test_value + "{'part_name':'" + part_name[j][case1][case2] + "','group_no':'"
				    + group_no[j][case1][case2] + "','m_total_cost':"
				    + StringUtil.Roundformat(m_total_cost[j][case1][case2], 2) + ",'labor_cost':"
				    + StringUtil.Roundformat(labor_cost[j][case1][case2], 2) + ",'common_cost':"
				    + StringUtil.Roundformat(common_cost[j][case1][case2], 2) + ",'ad_cost':"
				    + StringUtil.Roundformat(ad_cost[j][case1][case2], 2) + ",'g_depr_cost':"
				    + StringUtil.Roundformat(g_depr_cost[j][case1][case2], 2) + ",'g_actual_cost':"
				    + StringUtil.Roundformat(g_actual_cost[j][case1][case2], 2) + ",'g_earn_rate':"
				    + StringUtil.Roundformat(Double.toString(StringUtil.nullDouble(g_earn_rate[j][case1][case2]) * 100), 2)
				    + "} ";
			    flag = "2";
			}
		    } // case2 for end
		} // case1 for end

		// 1331
		if ("복합금형".equals(part_type_1[j][0][0])) {
		    sum_g_depr_cost[j][0][0] = sum_g_depr_cost[j][0][0];
		    g_assy_m_total_cost[j] = meterial_cost[j][0][0];
		    g_assy_meterial_cost[j] = m_total_cost[j][0][0];
		} else {
		    sum_g_depr_cost[j][0][0] = Double.toString(StringUtil.nullDouble(sum_g_depr_cost[j][0][0])
			    + StringUtil.nullDouble(assy_g_depr_cost[j]));
		}
		// System.out.println("case_count_1[j]) 입니다!!!! ~~~~~~~~~~~~~~~~~~"+case_count_1[j]);
		if (!"0".equals(case_count_1[j])) {

		    test_value = test_value + ",{'part_name':'" + part_name[j][0][0] + "','group_no':'" + group_no[j][0][0]
			    + "sum','m_total_cost':" + StringUtil.Roundformat(sum_m_total_cost[j][0][0], 2) + ",'labor_cost':"
			    + StringUtil.Roundformat(sum_labor_cost[j][0][0], 2) + ",'common_cost':"
			    + StringUtil.Roundformat(sum_common_cost[j][0][0], 2) + ",'ad_cost':"
			    + StringUtil.Roundformat(sum_ad_cost[j][0][0], 2) + ",'g_depr_cost':"
			    + StringUtil.Roundformat(sum_g_depr_cost[j][0][0], 2) + ",'g_actual_cost':"
			    + StringUtil.Roundformat(assy_g_actual_cost[j], 2) + ",'g_earn_rate':"
			    + StringUtil.Roundformat(Double.toString(StringUtil.nullDouble(assy_g_earn_rate[j]) * 100), 2) + "} ";

		    if ("work".equals(page_name)) {
			gdRes4.getHeader("CRUD").setValue("", "", 0);
			gdRes4.getHeader("pk_cw").setValue(pk_cw[j][0][0], "", 0);
			gdRes4.getHeader("pk_cr_group").setValue(pk_cr_group[j][0][0], "", 0);
			gdRes4.getHeader("group_no").setValue(group_no[j][0][0], "", 0);
			gdRes4.getHeader("pro_type").setValue(pro_type[j][0][0], "", 0);
			gdRes4.getHeader("g_sel1").setValue("", "", "/plm/jsp/cost/images/common/add_red.jpg", 0);
			gdRes4.getHeader("g_sel2").setValue("", "", "", 0);
			gdRes4.getHeader("g_sel3").setValue("", "", "", 0);
			gdRes4.getHeader("part_name").setValue(part_name[j][0][0], "", 0);
			gdRes4.getHeader("usage").setValue(usage[j][0][0], "", 0);
			gdRes4.getHeader("meterial_cost").setValue(StringUtil.Roundformat(g_assy_m_total_cost[j], 4), "", 0);
			gdRes4.getHeader("loss").setValue(StringUtil.Roundformat(loss[j][0][0], 4), "", 0);
			gdRes4.getHeader("scrap_cost").setValue(StringUtil.Roundformat(scrap_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("scrap_rate").setValue(scrap_rate[j][0][0], "", 0);
			gdRes4.getHeader("m_total_cost").setValue(StringUtil.Roundformat(g_assy_meterial_cost[j], 4), "", 0);
			gdRes4.getHeader("output").setValue(StringUtil.Roundformat(output[j][0][0], 4), "", 0);
			gdRes4.getHeader("rate").setValue(StringUtil.checkNull(rate[j][0][0]), "", 0);
			gdRes4.getHeader("eff_value").setValue(eff_value[j][0][0], "", 0);
			gdRes4.getHeader("labor_cost").setValue(StringUtil.Roundformat(labor_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("jun_cost").setValue(StringUtil.Roundformat(jun_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("ma_depr").setValue(StringUtil.Roundformat(ma_depr[j][0][0], 4), "", 0);
			gdRes4.getHeader("m_upkeep").setValue(StringUtil.Roundformat(m_upkeep[j][0][0], 4), "", 0);
			if (!"재료도금".equals(plating_type[j][0][0])) {
			    gdRes4.getHeader("etc_expense").setValue(plating_cost[j][0][0], "", 0);
			} else {
			    gdRes4.getHeader("etc_expense").setValue("", "", 0);
			}
			gdRes4.getHeader("pack_cost").setValue(StringUtil.Roundformat(pack_cost[j][0][0], 4), "", 0);
			if (!"검사".equals(type_1[j][0][0]) && !"포장".equals(type_1[j][0][0]) && !"구매".equals(type_1[j][0][0])) {
			    gdRes4.getHeader("out_cost").setValue(type_cost[j][0][0], "", 0);
			} else {
			    gdRes4.getHeader("out_cost").setValue("", "", 0);
			}
			gdRes4.getHeader("total_expense").setValue(StringUtil.Roundformat(total_expense[j][0][0], 4), "", 0);
			gdRes4.getHeader("overhead").setValue(StringUtil.Roundformat(overhead[j][0][0], 4), "", 0);
			gdRes4.getHeader("gita_cost").setValue(StringUtil.Roundformat(gita_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("common_cost").setValue(StringUtil.Roundformat(common_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("yzk_depr").setValue(StringUtil.Roundformat(yzk_depr[j][0][0], 4), "", 0);
			// gdRes4.getHeader("mold_type").setSelectedHiddenValue(mold_type[j][0][0], "", 0);
			String mold_hidden = mold_type[j][0][0];
			String[] arrComboValue = { mold_hidden };
			// System.out.println("몰드 몰드 mold_hidden ~~~~~~~~~~~~~"+mold_hidden);
			if (!StringUtil.checkNull(mold_hidden).equals("")) {
			    try {
				gdRes4.getHeader("mold_type").setSelectedHiddenValue(mold_type[j][0][0], "", 0);
				// gdRes4.getHeader("mold_type").setComboValues(arrComboValue, arrComboValue);
				// gdRes4.getHeader("mold_type").getSelectedIndex(0);
			    } catch (Exception e) {
				// System.out.println("에러천지입니다!!!!");
			    }
			}
			// 비철
			if ("TML".equals(pro_type[j][0][0]) || "TML-조립".equals(pro_type[j][0][0])) {
			    start_depr = g_depr_cost[j][0][0];
			    depr_cost = g_depr_cost[j][0][0];
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][0][0])) || !"".equals(StringUtil.checkNull(etc_depr[j][0][0]))) {
				start_depr = Double.toString(StringUtil.nullDouble(start_depr) - StringUtil.nullDouble(etc_depr[j][0][0]));
			    }
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][0][0])) || !"".equals(StringUtil.checkNull(yzk_depr[j][0][0]))) {
				start_depr = Double.toString(StringUtil.nullDouble(start_depr) - StringUtil.nullDouble(yzk_depr[j][0][0]));
			    }
			    gdRes4.getHeader("tabalu").setValue(StringUtil.Roundformat(tabalu[j][0][0], 4), "", 0);
			    // 금형감가
			    gdRes4.getHeader("start_depr").setValue(StringUtil.Roundformat(start_depr, 4), "", 0);
			    gdRes4.getHeader("pro_depr").setValue("0", "", 0);
			    gdRes4.getHeader("depr_cost").setValue(StringUtil.Roundformat(depr_cost, 4), "", 0);
			} else if ("HSG".equals(pro_type[j][0][0]) || "HSG-Box".equals(pro_type[j][0][0])) {
			    start_depr = g_depr_cost[j][0][0];
			    depr_cost = g_depr_cost[j][0][0];
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][0][0])) || !"".equals(StringUtil.checkNull(etc_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][0][0], 0));
				start_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// start_depr = Double.toString(StringUtil.nullDouble(start_depr) -
				// StringUtil.nullDouble(etc_depr[j][0][0]));
			    }
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][0][0])) || !"".equals(StringUtil.checkNull(yzk_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][0][0], 0));
				start_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// start_depr = Double.toString(StringUtil.nullDouble(start_depr) -
				// StringUtil.nullDouble(yzk_depr[j][0][0]));
			    }
			    gdRes4.getHeader("tabalu").setValue("0", "", 0);
			    // 금형감가
			    gdRes4.getHeader("start_depr").setValue(StringUtil.Roundformat(start_depr, 4), "", 0);
			    gdRes4.getHeader("pro_depr").setValue("0", "", 0);
			    gdRes4.getHeader("depr_cost").setValue(StringUtil.Roundformat(depr_cost, 4), "", 0);
			} else if ("assy".equals(pro_type[j][0][0])) {
			    // 설비감가
			    pro_depr = assy_g_depr_cost[j];
			    depr_cost = assy_g_depr_cost[j];
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][0][0])) || !"".equals(StringUtil.checkNull(etc_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(etc_depr[j][0][0]));
			    }
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][0][0])) || !"".equals(StringUtil.checkNull(yzk_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(yzk_depr[j][0][0]));
			    }
			    gdRes4.getHeader("tabalu").setValue("0", "", 0);
			    // 금형감가
			    gdRes4.getHeader("start_depr").setValue("0", "", 0);
			    gdRes4.getHeader("pro_depr").setValue(StringUtil.Roundformat(pro_depr, 4), "", 0);
			    gdRes4.getHeader("depr_cost").setValue(StringUtil.Roundformat(depr_cost, 4), "", 0);
			} else if ("Insert".equals(pro_type[j][0][0])) {
			    start_depr = in_start_depr;
			    depr_cost = assy_g_depr_cost[j];
			    // 설비감가
			    culc_1 = new BigDecimal(StringUtil.Roundformat(assy_g_depr_cost[j], 0));
			    culc_2 = new BigDecimal(StringUtil.Roundformat(start_depr, 0));
			    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

			    // pro_depr = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j]) - StringUtil.nullDouble(start_depr));
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][0][0])) || !"".equals(StringUtil.checkNull(etc_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(etc_depr[j][0][0]));
			    }
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][0][0])) || !"".equals(StringUtil.checkNull(yzk_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(yzk_depr[j][0][0]));
			    }
			    gdRes4.getHeader("tabalu").setValue("0", "", 0);
			    // 금형감가
			    gdRes4.getHeader("start_depr").setValue(StringUtil.Roundformat(start_depr, 4), "", 0);
			    gdRes4.getHeader("pro_depr").setValue(StringUtil.Roundformat(pro_depr, 4), "", 0);
			    gdRes4.getHeader("depr_cost").setValue(StringUtil.Roundformat(depr_cost, 4), "", 0);
			} else if ("sub_Insert".equals(pro_type[j][0][0])) {
			    start_depr = g_depr_cost[j][0][0];
			    depr_cost = sub_g_depr_cost[case1];
			    // 설비감가
			    culc_1 = new BigDecimal(StringUtil.Roundformat(sub_g_depr_cost[case1], 0));
			    culc_2 = new BigDecimal(StringUtil.Roundformat(g_depr_cost[j][0][0], 0));
			    pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

			    // pro_depr = Double.toString(StringUtil.nullDouble(sub_g_depr_cost[case1]) -
			    // StringUtil.nullDouble(g_depr_cost[j][0][0]));
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][0][0])) || !"".equals(StringUtil.checkNull(etc_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(etc_depr[j][0][0]));
			    }
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][0][0])) || !"".equals(StringUtil.checkNull(yzk_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());
				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(yzk_depr[j][0][0]));
			    }
			    gdRes4.getHeader("tabalu").setValue("0", "", 0);
			    // 금형감가
			    gdRes4.getHeader("start_depr").setValue(StringUtil.Roundformat(start_depr, 4), "", 0);
			    gdRes4.getHeader("pro_depr").setValue(StringUtil.Roundformat(pro_depr, 4), "", 0);
			    gdRes4.getHeader("depr_cost").setValue(StringUtil.Roundformat(depr_cost, 4), "", 0);
			} else if ("sub_assy".equals(pro_type[j][0][0])) {
			    // 설비감가
			    pro_depr = sub_g_depr_cost[case1];
			    depr_cost = sub_g_depr_cost[case1];

			    if (!"".equals(StringUtil.checkNull(etc_cost[j][0][0])) || !"".equals(StringUtil.checkNull(etc_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(etc_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(etc_depr[j][0][0]));
			    }
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][0][0])) || !"".equals(StringUtil.checkNull(yzk_depr[j][0][0]))) {
				culc_1 = new BigDecimal(StringUtil.Roundformat(pro_depr, 0));
				culc_2 = new BigDecimal(StringUtil.Roundformat(yzk_depr[j][0][0], 0));
				pro_depr = Double.toString(culc_1.subtract(culc_2).doubleValue());

				// pro_depr = Double.toString(StringUtil.nullDouble(pro_depr) - StringUtil.nullDouble(yzk_depr[j][0][0]));
			    }
			    gdRes4.getHeader("tabalu").setValue("0", "", 0);
			    // 금형감가
			    gdRes4.getHeader("start_depr").setValue("0", "", 0);
			    gdRes4.getHeader("pro_depr").setValue(StringUtil.Roundformat(pro_depr, 4), "", 0);
			    gdRes4.getHeader("depr_cost").setValue(StringUtil.Roundformat(depr_cost, 4), "", 0);
			} else {
			    depr_cost = "0";
			    gdRes4.getHeader("tabalu").setValue("0", "", 0);
			    // 금형감가
			    gdRes4.getHeader("start_depr").setValue("0", "", 0);
			    gdRes4.getHeader("pro_depr").setValue("0", "", 0);
			    gdRes4.getHeader("depr_cost").setValue("0", "", 0);
			}
			gdRes4.getHeader("etc_depr").setValue(StringUtil.Roundformat(etc_depr[j][0][0], 4), "", 0);
			gdRes4.getHeader("jae_cost").setValue(StringUtil.Roundformat(jae_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("ge_cost").setValue(StringUtil.Roundformat(ge_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("rnd_cost").setValue(StringUtil.Roundformat(g_rnd_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("qu_cost").setValue(qu_cost[j][0][0], "", 0);
			gdRes4.getHeader("tariff").setValue(tariff[j][0][0], "", 0);
			gdRes4.getHeader("royalty_cost").setValue(StringUtil.Roundformat(g_roy_cost[j][0][0], 4), "", 0);
			gdRes4.getHeader("dis_cost").setValue(dis_cost[j][0][0], "", 0);
			gdRes4.getHeader("actual_cost").setValue(StringUtil.Roundformat(assy_g_actual_cost[j], 4), "", 0);
		    } // page_name : work if end
		    flag = "2";
		} else {
		    if ("1".equals(flag)) {
			// System.out.println("총원가 찍기 3: "+g_actual_cost[j][0][0]);
			// System.out.println("수익률 3 >>> "+g_earn_rate[j][0][0]);
			test_value = test_value + "{'part_name':'" + part_name[j][0][0] + "','group_no':'" + group_no[j][0][0]
			        + "sum','m_total_cost':" + StringUtil.Roundformat(m_total_cost[j][0][0], 2) + ",'labor_cost':"
			        + StringUtil.Roundformat(labor_cost[j][0][0], 2) + ",'common_cost':"
			        + StringUtil.Roundformat(common_cost[j][0][0], 2) + ",'ad_cost':"
			        + StringUtil.Roundformat(ad_cost[j][0][0], 2) + ",'g_depr_cost':"
			        + StringUtil.Roundformat(g_depr_cost[j][0][0], 2) + ",'g_actual_cost':"
			        + StringUtil.Roundformat(g_actual_cost[j][0][0], 2) + ",'g_earn_rate':"
			        + StringUtil.Roundformat(Double.toString(StringUtil.nullDouble(g_earn_rate[j][0][0]) * 100), 2) + "} ";
			flag = "2";

		    } else {
			// System.out.println("총원가 찍기 4: "+g_actual_cost[j][0][0]);
			// System.out.println("수익률 4 >>> "+g_earn_rate[j][0][0]);
			test_value = test_value + ",{'part_name':'" + part_name[j][0][0] + "','group_no':'" + group_no[j][0][0]
			        + "sum','m_total_cost':" + StringUtil.Roundformat(m_total_cost[j][0][0], 2) + ",'labor_cost':"
			        + StringUtil.Roundformat(labor_cost[j][0][0], 2) + ",'common_cost':"
			        + StringUtil.Roundformat(common_cost[j][0][0], 2) + ",'ad_cost':"
			        + StringUtil.Roundformat(ad_cost[j][0][0], 2) + ",'g_depr_cost':"
			        + StringUtil.Roundformat(g_depr_cost[j][0][0], 2) + ",'g_actual_cost':"
			        + StringUtil.Roundformat(g_actual_cost[j][0][0], 2) + ",'g_earn_rate':"
			        + StringUtil.Roundformat(Double.toString(StringUtil.nullDouble(g_earn_rate[j][0][0]) * 100), 2) + "} ";

		    }
		} // case_count1 : !0 if end
	    }// table_row_count for end

	    test_value = test_value + "]";

	    // System.out.println("test_value==>"+test_value);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	}
	// 메세지와 상태값을 셋팅합니다.
	gdRes.addParam("mode", "cost_acc");
	gdRes.addParam("cost_acc", test_value);
	gdRes.setMessage("성공적으로 작업하였습니다.");
	gdRes.setStatus("true");
	ArrayList<Hashtable<String, GridData>> accList = new ArrayList<Hashtable<String, GridData>>();
	Hashtable<String, GridData> accHash = null;
	accHash = new Hashtable<String, GridData>();
	if ("work".equals(page_name)) {
	    gdRes4.addParam("mode", "cost_acc");
	    accHash.put("gdRes4", gdRes4);
	    accList.add(accHash);
	}
	accHash.put("gdRes", gdRes);
	accList.add(accHash);
	return accList;
    }

}
