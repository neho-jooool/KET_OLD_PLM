package e3ps.cost.control;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.dao.CostAccDao;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;

public class CostAccVarialbe {
    // 1.판매단위 제품정보
    String[] list_pk_ = {};
    String[] assy_in = {};
    String[] a_su_year_1 = {};
    String[] a_su_year_2 = {};
    String[] a_su_year_3 = {};
    String[] a_su_year_4 = {};
    String[] a_su_year_5 = {};
    String[] a_su_year_6 = {};
    String[] a_su_year_7 = {};
    String[] a_su_year_8 = {};
    String[] p_su_year_1 = {};
    String[] p_su_year_2 = {};
    String[] p_su_year_3 = {};
    String[] p_su_year_4 = {};
    String[] p_su_year_5 = {};
    String[] p_su_year_6 = {};
    String[] p_su_year_7 = {};
    String[] p_su_year_8 = {};
    String[] royalty = {};
    String[] start_pro = {};
    String[] store = {};
    String[] dest = {};
    String[] ket_cost = {};
    String[] target_cost = {};
    String[] distri_cost = {};
    String[] case_count_1 = {};
    String[][][] pk_cr_ = {};
    String[][][] pk_cw = {};
    String[][][] pk_cr_group = {};
    String[] customer_F = {};
    String[] customer_L = {};
    String[] client_cost_ = {};
    String[] assy_note_ = {};
    String[][][] part_note_ = {};
    String[] car_type_ = {};

    // 2.제품 생산내역

    String[][][] group_no = {};
    String[][][] part_no_1 = {};
    String[][][] die_no = {};
    String[][][] part_name = {};
    String[][] case_count_2 = {};
    String[][][] scrap_rate = {};
    String[][][] etc_depr = {}; // 기타감가
    String[][][] mold_c_type = {};
    String[][][] grade_name = {};
    String[][][] grade_color = {};
    String[][][] mold_type = {};
    String[][][] mix_group_1 = {};
    String[][][] make = {};
    String[][][] re_m_cost = {};
    String[][][] part_type_1 = {};
    String[][][] pro_type = {};
    String[][][] usage = {};
    String[][][] meterial = {};
    String[][][] t_size = {};
    String[][][] w_size = {};
    String[][][] p_size = {};
    String[][][] plating = {};
    String[][][] m_maker = {};
    String[][][] m_mone = {};
    String[][][] unitcost = {};
    String[][][] c_unitcost = {};
    String[][][] unitcost_txt = {};
    String[][][] order_con = {};
    String[][][] h_weight = {};
    String[][][] h_scrap = {};
    String[][][] t_weight = {};
    String[][][] recycle_state = {};
    String[][][] recycle = {};
    String[][][] cavity = {};
    String[][][] make_type = {};
    String[][][] pro_1 = {};
    String[][][] ton = {};
    String[][][] spm = {};
    String[][][] method_type = {};
    String[][][] pro_level = {};
    String[][][] pro_level_txt = {};
    String[][][] type_1 = {};
    String[][][] type_2 = {};
    String[][][] t_mone = {};
    String[][][] type_cost = {};
    String[][][] t_order = {};
    String[][][] pack_type = {};
    String[][][] in_pack = {};
    String[][][] out_pack = {};
    String[][][] dis_cost = {};
    String[][][] yazaki_ro = {};
    String[][][] yzk_mone = {};
    String[][][] m_su = {};
    String[][][] mold_cost = {};
    String[][][] line_su = {};
    String[][][] sul_cost = {};
    String[][][] etc_cost = {};
    String[][][] plating_type = {};
    String[][][] plating_cost = {};
    // 간이버튼 Click

    String[][][] st_person = {}; // 표준작업인원
    String[][][] st_eff_value = {}; // 효율
    String[][][] eff_value = {}; // 효율
    String[][][] unitcost_si = {}; // 원재료단가
    String[][][] meterial_cost = {};
    String[][][] m_total_cost = {}; // 재료비
    String[][][] labor_cost = {}; // 노무비
    String[][][] common_cost = {}; // 제조경비
    String[][][] s_depr_cost = {}; // 일반감가비
    String[][][] g_depr_cost = {}; // 기획수량감가비
    String[][][] yzk_depr = {}; // yzk감가
    String[][][] ad_cost = {}; // 관리비
    String[][][] actual_cost = {}; // 총원가
    String[][][] earn_rate = {}; // 수익률
    String[][][] loss = {}; // loss비
    String[][][] scrap_cost = {}; // 스크랩판매비
    String[][][] output = {}; // 시간당 생산량
    String[][][] rate = {}; // 임율
    String[][][] qu_cost = {}; // 품질비용
    String[][][] gita_cost = {}; // 기타감가
    String[][][] tariff = {}; // 관세
    String[][][] jun_cost = {}; // 전력비
    String[][][] ma_depr = {}; // 기계감가
    String[][][] tabalu = {}; // 타발유
    String[][][] m_upkeep = {}; // 금형유지비
    String[][][] total_expense = {}; // 직접경비
    String[][][] overhead = {}; // 간접경비
    String[][][] pack_cost = {}; // 포장비
    String[][][] s_avg_su = {}; // 일반감가기준수량
    String[][][] avg_su = {}; // 총 판매기획수량
    String[][][] process_cost = {}; // 가공비
    String[][][] jae_cost = {}; // 재료관리비
    String[][][] ge_cost = {}; // 일반관리비
    String[][][] s_rnd_cost = {}; // 일반감가기준 R&D비
    String[][][] g_rnd_cost = {}; // 판매기준 R&D비
    String[][][] s_roy_cost = {}; // 일반감가기준로열티
    String[][][] s_actual_cost = {}; // 일반감가기준총원가
    String[][][] g_roy_cost = {}; // 판매기준 로열티
    String[][][] g_actual_cost = {}; // 판매기준 총원가
    String[][][] s_earn_rate = {}; // 일반감가기준 수익률
    String[][][] g_earn_rate = {}; // 판매기준 수익률

    // 조립
    String[] s_assy_m_total_cost = {}; // 단품원가총합(재료비산출에사용) -일반감가
    String[] g_assy_m_total_cost = {}; // 단품원가총합(재료비산출에사용) - 판매수량
    String[] s_assy_meterial_cost = {}; // 조립 재료비
    String[] g_assy_meterial_cost = {}; // 조립 재료비
    String[] s_assy_loss = {}; // loss비-일반
    String[] g_assy_loss = {}; // loss비-판매
    String[] assy_scrap_cost = {}; // 스크랩판매비
    String[] assy_labor_cost = {}; // 노무비
    String[] assy_common_cost = {}; // 제조경비
    String[] assy_s_depr_cost = {}; // 일반감가비
    String[] assy_g_depr_cost = {}; // 기획수량감가비
    String[] assy_ad_cost = {}; // 관리비
    String[] assy_output = {}; // 시간당 생산량
    String[] assy_rate = {}; // 임율
    String[] assy_rnd_cost = {}; // R&D비율
    String[] assy_jun_cost = {}; // 전력비
    String[] assy_ma_depr = {}; // 기계감가
    String[] assy_m_upkeep = {}; // 금형유지비
    String[] assy_total_expense = {}; // 직접경비
    String[] assy_overhead = {}; // 간접경비
    String[] assy_pack_cost = {}; // 포장비
    String[] assy_s_avg_su = {}; // 일반감가기준수량
    String[] assy_avg_su = {}; // 총 판매기획수량
    String[] assy_process_cost = {}; // 가공비
    String[] assy_jae_cost = {}; // 재료관리비
    String[] assy_ge_cost = {}; // 일반관리비
    String[] assy_s_rnd_cost = {}; // 일반감가기준 R&D비
    String[] assy_g_rnd_cost = {}; // 판매기준 R&D비
    String[] assy_s_roy_cost = {}; // 일반감가기준로열티
    String[] assy_s_actual_cost = {}; // 일반감가기준총원가
    String[] assy_g_roy_cost = {}; // 판매기준 로열티
    String[] assy_g_actual_cost = {}; // 판매기준 총원가
    String[] assy_s_earn_rate = {}; // 일반감가기준 수익률
    String[] assy_g_earn_rate = {}; // 판매기준 수익률

    // sub 조립
    String[][] s_sub_m_total_cost = {}; // 단품원가총합(재료비산출에사용) -일반감가
    String[][] g_sub_m_total_cost = {}; // 단품원가총합(재료비산출에사용) - 판매수량
    String[] s_sub_meterial_cost = {}; // 조립 재료비
    String[] g_sub_meterial_cost = {}; // 조립 재료비
    String[] s_sub_loss = {}; // loss비-일반
    String[] g_sub_loss = {}; // loss비-판매
    String[] sub_scrap_cost = {}; // 스크랩판매비
    String[] sub_labor_cost = {}; // 노무비
    String[] sub_common_cost = {}; // 제조경비
    String[] sub_s_depr_cost = {}; // 일반감가비
    String[] sub_g_depr_cost = {}; // 기획수량감가비
    String[] sub_ad_cost = {}; // 관리비
    String[] sub_output = {}; // 시간당 생산량
    String[] sub_rate = {}; // 임율
    String[] sub_rnd_cost = {}; // R&D비율
    String[] sub_jun_cost = {}; // 전력비
    String[] sub_ma_depr = {}; // 기계감가
    String[] sub_m_upkeep = {}; // 금형유지비
    String[] sub_total_expense = {}; // 직접경비
    String[] sub_overhead = {}; // 간접경비
    String[] sub_pack_cost = {}; // 포장비
    String[] sub_s_avg_su = {}; // 일반감가기준수량
    String[] sub_avg_su = {}; // 총 판매기획수량
    String[] sub_process_cost = {}; // 가공비
    String[] sub_jae_cost = {}; // 재료관리비
    String[] sub_ge_cost = {}; // 일반관리비
    String[] sub_s_rnd_cost = {}; // 일반감가기준 R&D비
    String[] sub_g_rnd_cost = {}; // 판매기준 R&D비
    String[] sub_s_roy_cost = {}; // 일반감가기준로열티
    String[] sub_s_actual_cost = {}; // 일반감가기준총원가
    String[] sub_g_roy_cost = {}; // 판매기준 로열티
    String[] sub_g_actual_cost = {}; // 판매기준 총원가
    String[] sub_s_earn_rate = {}; // 일반감가기준 수익률
    String[] sub_g_earn_rate = {}; // 판매기준 수익률

    // 최종SUM
    String[][][] sum_meterial = {}; // 제조원가
    String[][][] sum_m_total_cost = {};
    String[][][] sum_labor_cost = {};
    String[][][] sum_common_cost = {};
    String[][][] sum_sum_meterial = {};
    String[][][] sum_ad_cost = {};
    String[][][] sum_g_depr_cost = {};
    String[][][] sum_s_actual_cost = {};
    String[][][] sum_g_actual_cost = {};

    String[][][] pl_meterial_cost = {};
    String[][][] pl_loss = {};
    String[][][] pl_scrap_cost = {};
    String[][][] pl_m_total_cost = {};
    String[][][] pl_output = {};
    String[][][] pl_rate = {};
    String[][][] pl_labor_cost = {};
    String[][][] pl_jun_cost = {};
    String[][][] pl_ma_depr = {};
    String[][][] pl_tabalu = {};
    String[][][] pl_m_upkeep = {};
    String[][][] pl_total_expense = {};
    String[][][] pl_overhead = {};
    String[][][] pl_common_cost = {};
    String[][][] pl_yzk_depr = {};
    String[][][] pl_start_depr = {};
    String[][][] pl_pro_depr = {};
    String[][][] pl_etc_depr = {};
    String[][][] pl_jae_cost = {};
    String[][][] pl_ge_cost = {};
    String[][][] pl_rnd_cost = {};
    String[][][] pl_tariff = {};
    String[][][] pl_royalty_cost = {};
    String[][][] pl_actual_cost = {};

    // 배열위치
    int a = 0;
    int b = 0;
    int c = 0;

    // grid 4
    // 배열위치
    int a2 = 0;
    int b2 = 0;
    int c2 = 0;

    // 원재료 단가 산출 변수 추가 및 선언 START
    String lme_ton = "0";
    String u_ex_rate = "0";
    String y_ex_rate = "0";

    String lme_cu = "0";
    String lme_zn = "0";
    String lme_sn = "0";
    String lme_ni = "0";
    String usd_rate = "0";
    String yen_rate = "0";

    String table_row_count = "0";

    String W_Lme_cu = "0";
    String W_Lme_zn = "0";
    String W_Lme_sn = "0";
    String W_Lme_ni = "0";

    String Y_Lme_cu = "0";
    String Y_Lme_zn = "0";
    String Y_Lme_sn = "0";
    String Y_Lme_ni = "0";
    String Y_Premium = "0";

    String met_type = "0";
    // 원재료 단가 산출 변수 추가 및 선언 종료

    // 변수 추가 및 선언
    String p_su_chk = "";
    String c_lme_ton = "0";
    String c_u_ex_rate = "0";
    String c_y_ex_rate = "0";
    String r_USD_rate = "0";
    String r_EURO_rate = "0";
    String r_YEN_rate = "0";
    String r_CNY_rate = "0";

    /******* 원가산출식 변수 선언 *******/
    String type_cost_rate = "0";
    String grade_cost_rate = "0";
    String scrap_cost_im = "0";
    String Assy_content = "";
    String a_pro_type = "";
    String grade_cost_KRW = "0";
    String type_cost_KRW = "0";
    String rec_weight = "0";
    String sul_cost_depr = "0";
    String yzk_su = "0";
    String target_rate = "0";
    String etc_su = "0";
    String ton_value = "0";
    String st_eff_value_in = "0"; // 효율
    String st_person_in = "0"; // 표준인원
    String st_rate = "0"; // 임율
    String st_jun_cost = "0"; // 전력비
    String st_ma_depr = "0"; // 기계감가
    String st_tabalu = "0"; // 타발유
    String st_overhead = "0"; // 간접경비
    String st_ge_cost = "0"; // 일반관리비율
    String st_jae_cost = "0"; // 재료관리비율
    String st_rnd_cost = "0"; // R&D비율
    String st_royalty = "0"; // 로얄티비율
    String carrier_cost = "0";
    String carrier_grade = "0";
    String carrier_scrap = "0"; // 스크랩판매비
    String t_carrier_cost = "0"; // 탄창총재료비
    String wire_cost = "0"; // wire 원재료비
    String wire_cost_loss = "0"; // wire loss
    String m_wire_cost = "0"; // wire 총재료비
    String an_output = "0";
    String an_labor_cost = "0";
    String an_jun_cost = "0";
    String an_ma_depr = "0";
    String mold_su = "0";
    String mold_su_1 = "0";
    String mold_su_2 = "0";
    String mold_su_3 = "0";
    String mold_su_4 = "0";
    String mold_su_5 = "0";
    String mold_su_6 = "0";
    String mold_su_7 = "0";
    String mold_su_8 = "0";
    String sub_assy_content = "";
    String case1_k = "";
    String m_total = "";
    String TML_loss = "";

    String line_su_in = "0";
    String line_su_1 = "0";
    String line_su_2 = "0";
    String line_su_3 = "0";
    String line_su_4 = "0";
    String line_su_5 = "0";
    String line_su_6 = "0";
    String line_su_7 = "0";
    String line_su_8 = "0";

    String to_cost = "0";
    String table_row = "0";
    String group_case_count = "0";
    String case_data_value = "";
    /*************************************/

    BigDecimal temp_1 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_2 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_3 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_4 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_5 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_6 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_7 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_8 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_9 = new BigDecimal("0");// 소수점 계산용
    BigDecimal temp_10 = new BigDecimal("0");// 소수점 계산용

    /**
     * 함수명 : cost_acc 설명 : 원가산출 (5 : 5000 Line)
     * 
     * @param String
     *            page_name, GridData gdReq, GridData gdReq3, GridData gdReq4
     * @return
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 07. 23.
     */
    public void cost_acc(String page_name) throws Exception {

	Connection conn = null;
	CostAccDao costAccDao = null;

	/**************** 원재료 단가산출 START **************/
	// //System.out.println("common_cost 시작 1 : "+common_cost[0][0][0]);
	cost_rawMaterial(page_name);
	// //System.out.println("common_cost 시작 2 : "+common_cost[0][0][0]);
	/**************** 원재료 단가산출 END ****************/
	// Connection conn = DBUtil.getConnection();
	// CostAccDao costAccDao = new CostAccDao(conn);

	for (int j = 0; j < Integer.parseInt(table_row_count); j++) {
	    for (int case1 = 0; case1 <= Integer.parseInt(StringUtil.checkNullZero(case_count_1[j])); case1++) {

		s_sub_m_total_cost[j][case1] = ""; // 단품원가총합(재료비산출에사용) - 일반감가
		g_sub_m_total_cost[j][case1] = ""; // 단품원가총합(재료비산출에사용) - 판매수량
		// if(case_count_2[j][case1] == null){case_count_2[j][case1] = "0";}
		case_count_2[j][case1] = StringUtil.checkNullZero(case_count_2[j][case1]);

		for (int case2 = 0; case2 <= Integer.parseInt(case_count_2[j][case1]); case2++) {

		    System.out.println("babisss::pack_cost : " + j + "///" + pack_cost[j][case1][case2]);

		    // //System.out.println("g_assy_m_total_cost[j]의 common_cost !!!!!"+common_cost[j][case1][case2]);
		    if ("EUR".equals(t_mone[j][case1][case2])) {
			type_cost_rate = r_EURO_rate;
		    } else if ("CNY".equals(t_mone[j][case1][case2])) {
			type_cost_rate = r_CNY_rate;
		    } else if ("JPY".equals(t_mone[j][case1][case2])) {
			type_cost_rate = r_YEN_rate;
		    } else if ("USD".equals(t_mone[j][case1][case2])) {
			type_cost_rate = r_USD_rate;
		    } else if ("KRW".equals(t_mone[j][case1][case2])) {
			type_cost_rate = "1";
		    } // type_cost_rate if end

		    // 원재료비환산
		    if ("EUR".equals(m_mone[j][case1][case2])) {
			grade_cost_rate = r_EURO_rate;
		    } else if ("CNY".equals(m_mone[j][case1][case2])) {
			grade_cost_rate = r_CNY_rate;
		    } else if ("JPY".equals(m_mone[j][case1][case2])) {
			grade_cost_rate = r_YEN_rate;
		    } else if ("USD".equals(m_mone[j][case1][case2])) {
			grade_cost_rate = r_USD_rate;
		    } else if ("KRW".equals(m_mone[j][case1][case2])) {
			grade_cost_rate = "1";
		    } // 원재료비환산 end

		    // 원재료 단가
		    if (!"".equals(StringUtil.checkNull(unitcost[j][case1][case2])) && !"표준단가".equals(unitcost[j][case1][case2])) {
			unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000);
			if ("중국".equals(store[j]) && ("중국내업체".equals(dest[j]) || "유리(북경)".equals(dest[j]))) {
			    if ("내자".equals(order_con[j][case1][case2])) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1);
			    } else if ("FOB".equals(order_con)) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1.05);
			    } else if ("CIF".equals(order_con)) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1.03);
			    } else if ("L/C".equals(order_con)) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1);
			    } else {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1);
			    }
			} else {
			    if ("내자".equals(order_con[j][case1][case2])) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1);
			    } else if ("FOB".equals(order_con[j][case1][case2])) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1.13);
			    } else if ("CIF".equals(order_con)) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1.11);
			    } else if ("L/C".equals(order_con)) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1);
			    } else {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(grade_cost_rate) * 1);
			    }
			}
		    } // 원재료단가 if end

		    // 스크랩판매단가
		    // C2100 = 원화_Cu*0.95 + 원화_Zn*0.05)*0.55
		    // C2600 / C2680 = 원화_Cu*0.65 + 원화_Zn*0.35)*0.55
		    // C5210 / C5191 = 원화_Cu*0.92 + 원화_Sn*0.08)*0.45
		    // C194 = 원화_Cu*0.98*0.55
		    // W_Lme_cu

		    if ("재료도금".equals(plating_type[j][case1][case2]) && "Au".equals(type_2[j][case1][case2])) {
			scrap_cost_im = Double.toString(24878 / 1000);
			scrap_rate[j][case1][case2] = "";
		    } else {
			met_type = meterial[j][case1][case2];
			if ("C2100R".equals(met_type)) {
			    if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
				scrap_cost_im = Double
				        .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.95 + StringUtil.nullDouble(W_Lme_zn) * 0.05) / 1000)
				                * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			    } else {
				scrap_cost_im = Double
				        .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.95 + StringUtil.nullDouble(W_Lme_zn) * 0.05) / 1000) * 0.55);
				scrap_cost[j][case1][case2] = "55";
			    }
			} else if ("C2600R".equals(met_type) || "C2680R".equals(met_type)) { // 석황동 스크랩판가비율 55% 에서 60% 변경 10.6.30
			    if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
				scrap_cost_im = Double
				        .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.65 + StringUtil.nullDouble(W_Lme_zn) * 0.35) / 1000)
				                * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			    } else {
				scrap_cost_im = Double
				        .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.65 + StringUtil.nullDouble(W_Lme_zn) * 0.35) / 1000) * 0.60);
				scrap_cost[j][case1][case2] = "60";
			    }
			} else if ("C5210R".equals(met_type) || "C5191R".equals(met_type) || "PMC26R".equals(met_type)
			        || "PMC90R".equals(met_type)) { // 인청동 스크랩판가율 45%에서50%변경 10.6.30
			    if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
				scrap_cost_im = Double
				        .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.92 + StringUtil.nullDouble(W_Lme_sn) * 0.08) / 1000)
				                * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			    } else {
				scrap_cost_im = Double
				        .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.92 + StringUtil.nullDouble(W_Lme_sn) * 0.08) / 1000) * 0.50);
				scrap_cost[j][case1][case2] = "50";
			    }
			} else if ("C194".equals(met_type)) {
			    if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
				scrap_cost_im = Double.toString((StringUtil.nullDouble(W_Lme_cu) * 0.98 / 1000)
				        * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			    } else {
				scrap_cost_im = Double.toString((StringUtil.nullDouble(W_Lme_cu) * 0.98 / 1000) * 0.55);
				scrap_cost[j][case1][case2] = "55";
			    }
			} else {
			    if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
				scrap_cost_im = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			    } else {
				scrap_cost_im = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.45);
				scrap_cost[j][case1][case2] = "45";
			    }
			}
		    } // 스크랩판매단가 if end

		    if ("복합금형".equals(part_type_1[j][case1][case2])) {
			Assy_content = "OK";
			a_pro_type = "OK";
		    } else if ("Insert".equals(pro_type[j][case1][case2])) {
			a_pro_type = "OK";
		    }

		    if ("복합금형".equals(part_type_1[j][case1][case2]) && "동시생산".equals(mix_group_1[j][case1][case2])
			    && (!"assy".equals(pro_type[j][case1][case2]) || !"TML-조립".equals(pro_type[j][case1][case2]))) {
			if ("구매".equals(make_type[j][case1][case2]) || "부재료".equals(pro_type[j][case1][case2])) {
			    if ("무상".equals(pro_1[j][case1][case2])) {
				m_total_cost[j][case1][case2] = "0";
			    } else {
				if ("부재료".equals(pro_type[j][case1][case2])) {
				    if ("중국".equals(store[j]) && ("중국내업체".equals(dest[j]) || "유라(북경)".equals(dest[j]))) {
					if ("내자".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					} else if ("FOB".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.05);
					} else if ("CIF".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.03);
					} else if ("L / C".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					} else {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					}
				    } else {
					if ("내자".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					} else if ("FOB".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.13);
					} else if ("CIF".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.11);
					} else if ("L / C".equals(order_con[j][case1][case2])) {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					} else {
					    grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						    * StringUtil.nullDouble(grade_cost_rate)
						    * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					}
				    }
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(grade_cost_KRW)
					    * StringUtil.nullDouble(usage[j][case1][case2]));
				} else { // make_type이 구매이거나 부재료일때 else 괄호
				    if (!"Sec".equals(t_mone[j][case1][case2])) {
					if ("내자".equals(t_order[j][case1][case2])) {
					    type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						    * StringUtil.nullDouble(type_cost_rate) * 1);
					} else if ("FOB".equals(t_order[j][case1][case2])) {
					    type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						    * StringUtil.nullDouble(type_cost_rate) * 1.13);
					} else if ("CIF".equals(t_order[j][case1][case2])) {
					    type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						    * StringUtil.nullDouble(type_cost_rate) * 1.11);
					} else if ("L / C".equals(t_order[j][case1][case2])) {
					    type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						    * StringUtil.nullDouble(type_cost_rate) * 1);
					} else {
					    type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						    * StringUtil.nullDouble(type_cost_rate) * 1);
					}
				    } else {
					type_cost_KRW = type_cost[j][case1][case2];
				    }
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(type_cost_KRW)
					    * StringUtil.nullDouble(usage[j][case1][case2]));
				}
			    }// 무상if의 else종료괄호
			    g_actual_cost[j][case1][case2] = m_total_cost[j][case1][case2];
			    // //System.out.println("총원가 찍기 1: "+g_actual_cost[j][case1][case2]);
			} else if ("외주".equals(make_type[j][case1][case2])) {
			    if ("make_1".equals(make[j][case1][case2]) || "TML".equals(pro_type[j][case1][case2])) { // 비철
				if ("무상".equals(pro_1[j][case1][case2])) {
				    /*********************************/
				    /*************** 재료비 *************/
				    /*********************************/
				    if ("재료도금".equals(plating_type[j][case1][case2])) {
					unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					        + (StringUtil.nullDouble(plating_cost[j][case1][case2]) / 1000));
				    } else {
					unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
				    }

				    if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
					// 원재료비(재생반영)
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(unitcost_si[j][case1][case2])
					        * StringUtil.nullDouble(t_weight[j][case1][case2]));
					if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					}

					// 스크랩판매비(재생반영)
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
					        + StringUtil.nullDouble(scrap_cost[j][case1][case2]));
					if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(scrap_cost[j][case1][case2])
						    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
					}

					// loss 비
					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					        + StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.02);
					if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					}

					// 총재료비
					m_total_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					                    .nullDouble(scrap_cost[j][case1][case2]))
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    } else {
					// 원재료비
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(meterial_cost[j][case1][case2])
					        * StringUtil.nullDouble(t_weight[j][case1][case2]));
					if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					}

					// 스크랩판매비
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
					        * StringUtil.nullDouble(scrap_cost_im));
					if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(scrap_cost[j][case1][case2])
						    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
					}

					// loss비
					loss[j][case1][case2] = Double
					        .toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02);
					if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					}

					// 총재료비
					m_total_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					                    .nullDouble(scrap_cost[j][case1][case2]))
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    }
				} else {
				    m_total_cost[j][case1][case2] = "0";
				}
				if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));

				}
			    } else if ("HSG".equals(pro_type[j][case1][case2]) || "HSG-Box".equals(pro_type[j][case1][case2])) { // 수지 1188
				if ("무상".equals(pro_1[j][case1][case2])) {
				    /*** * *****************************/
				    /*************** 재료비 *************/
				    /*********************************/
				    scrap_cost[j][case1][case2] = "0";
				    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				    }

				    if (!"0".equals(recycle[j][case1][case2]) && !"".equals(StringUtil.checkNull(recycle[j][case1][case2]))) {
					rec_weight = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					        * (StringUtil.nullDouble(recycle[j][case1][case2]) / 100));

					if (StringUtil.nullDouble(rec_weight) < Double.parseDouble(StringUtil.Roundformat(
					        h_scrap[j][case1][case2], 2))) {
					    // 원재료비(재생반영)
					    meterial_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(t_weight[j][case1][case2]) - StringUtil.nullDouble(rec_weight))
						    * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
					} else {
					    // 원재료비(재생반영)
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(h_weight[j][case1][case2])
						    * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
					}
				    } else {
					// 원재료비
					meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					        * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
					recycle[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(meterial_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				    }

				    // loss비
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.03);
				    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				    }

				    // 총재료비
				    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					        .nullDouble(scrap_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));

				} else {
				    m_total_cost[j][case1][case2] = "0";
				}

				if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				}
			    }
			} else {
			    if ("make_1".equals(make[j][case1][case2]) || "TML".equals(pro_type[j][case1][case2])) { // 비철
				/*********************************/
				/*************** 재료비 *************/
				/*********************************/
				if ("재료도금".equals(plating_type[j][case1][case2])) {
				    unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					    + (StringUtil.nullDouble(plating_cost[j][case1][case2]) / 1000));
				} else {
				    unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
				}

				if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
				    // 원재료비(재생반영)
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					    * StringUtil.nullDouble(t_weight[j][case1][case2]));

				    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(meterial_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				    }

				    // 스크랩판매비(재생반영)
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
					    * StringUtil.nullDouble(scrap_cost[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				    }

				    // loss비
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					    * StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.02);
				    if (!"".equals(StringUtil.checkNull(loss[j][case1][case2]))) {
					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				    }

				    // 총재료비
				    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					        .nullDouble(scrap_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));
				} else {
				    // 원재료비
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					    * StringUtil.nullDouble(t_weight[j][case1][case2]));

				    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(meterial_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				    }

				    // 스크랩판매비 스크랩판매단가 : Kg당 4650원
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
					    * StringUtil.nullDouble(scrap_cost_im));
				    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				    }

				    // loss비
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2]) * 0.02);
				    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				    }

				    // 총재료비
				    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					        .nullDouble(scrap_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));
				}

				if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				}
			    } else if ("HSG".equals(pro_type[j][case1][case2]) || "HSG-Box".equals(pro_type[j][case1][case2])) { // 수지
				/*** * *****************************/
				/*************** 재료비 *************/
				/*********************************/
				if ("중국".equals(pro_1[j][case1][case2])) {
				    unitcost_si[j][case1][case2] = Double
					    .toString(StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 1.05);
				}

				scrap_cost[j][case1][case2] = "0";

				if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				}

				if (!"".equals(StringUtil.checkNull(recycle[j][case1][case2])) && !"0".equals(recycle[j][case1][case2])) {
				    rec_weight = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					    * (StringUtil.nullDouble(recycle[j][case1][case2]) / 100));

				    if (StringUtil.nullDouble(rec_weight) < Double.parseDouble(StringUtil.Roundformat(
					    h_scrap[j][case1][case2], 4))) {
					// 원재료비(재생반영)
					meterial_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(t_weight[j][case1][case2]) - StringUtil
					                .nullDouble(rec_weight)) * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				    } else {
					// 원재료비(재생반영)
					meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_weight[j][case1][case2])
					        * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				    }
				} else {
				    // 원재료비
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					    * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				    recycle[j][case1][case2] = "0";
				}

				if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				}

				// loss비
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.03);
				if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				}

				// 총재료비
				m_total_cost[j][case1][case2] = Double
				        .toString((StringUtil.nullDouble(m_total_cost[j][case1][case2])
				                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
				                    .nullDouble(scrap_cost[j][case1][case2]))
				                * StringUtil.nullDouble(usage[j][case1][case2]));
				if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				}
			    }
			}
		    } else { // 1336 복합금형 else
			if (StringUtil.nullDouble(usage[j][case1][case2]) > 0) {
			    if ("구매".equals(make_type[j][case1][case2]) || "부재료".equals(pro_type[j][case1][case2])) {
				if ("무상".equals(pro_1[j][case1][case2])) {
				    m_total_cost[j][case1][case2] = "0";
				} else {
				    if ("부재료".equals(pro_type[j][case1][case2])) {
					grade_cost_KRW = "0";

					if ("중국".equals(store[j]) && ("중국내업체".equals(dest[j]) || "유라(북경)".equals(dest[j]))) {
					    if ("내자".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					    } else if ("FOB".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.05);
					    } else if ("CIF".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.03);
					    } else if ("L / C".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					    } else {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					    }
					} else {
					    if ("내자".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					    } else if ("FOB".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.13);
					    } else if ("CIF".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1.11);
					    } else if ("L / C".equals(order_con[j][case1][case2])) {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					    } else {
						grade_cost_KRW = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
						        * StringUtil.nullDouble(grade_cost_rate)
						        * StringUtil.nullDouble(t_weight[j][case1][case2]) * 1);
					    }
					}

					m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(grade_cost_KRW)
					        * StringUtil.nullDouble(usage[j][case1][case2]));

				    } else {
					type_cost_KRW = "0";
					if (!"Sec".equals(t_mone[j][case1][case2])) {
					    if ("내자".equals(t_order[j][case1][case2])) {
						type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						        * StringUtil.nullDouble(type_cost_rate) * 1);
					    } else if ("FOB".equals(t_order[j][case1][case2])) {
						type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						        * StringUtil.nullDouble(type_cost_rate) * 1.13);
					    } else if ("CIF".equals(t_order[j][case1][case2])) {
						type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						        * StringUtil.nullDouble(type_cost_rate) * 1.11);
					    } else if ("L / C".equals(t_order[j][case1][case2])) {
						type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						        * StringUtil.nullDouble(type_cost_rate) * 1);
					    } else {
						type_cost_KRW = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
						        * StringUtil.nullDouble(type_cost_rate) * 1);
					    }
					} else {
					    type_cost_KRW = type_cost[j][case1][case2];
					}

					if ("make_4".equals(make[j][case1][case2])) { // 벤도리아핀일경우(구매)
					    // 스크랩판매비
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
						    * StringUtil.nullDouble(scrap_cost_im));
					    m_total_cost[j][case1][case2] = Double
						    .toString((StringUtil.nullDouble(type_cost_KRW) - StringUtil
						            .nullDouble(scrap_cost[j][case1][case2]))
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(type_cost_KRW)
						    * StringUtil.nullDouble(usage[j][case1][case2]));
					    // //System.out.println("060 WP 20F INNER SEAL 의 m_total_cost: "+m_total_cost[j][case1][case2]);
					    // //System.out.println("060 WP 20F INNER SEAL 의 type_cost_KRW: "+type_cost_KRW);
					    // //System.out.println("060 WP 20F INNER SEAL 의 m_total_cost: "+usage[j][case1][case2]);
					}
				    }
				}
				if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				}

				/*********************************/
				/*************** 노무비 *************/
				/*********************************/
				labor_cost[j][case1][case2] = "0";
				if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
				    labor_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
				}
				// //System.out.println("cost acc() 노무비 1 : "+labor_cost);
				/*********************************/
				/*************** 포장비 *************/
				/*********************************/
				if ("ok".equals(assy_in[j])) {
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = pack_cost[j][case1][case2];
				    } else {
					pack_cost[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
					dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
				    } else {
					dis_cost[j][case1][case2] = "0";
				    }
				} else {
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = StringUtil.Roundformat(pack_cost[j][case1][case2], 2);
				    } else {
					if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
					        && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("Reel".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((1072 / Integer
						        .parseInt(in_pack[j][case1][case2]))
						        + (1277 / Integer.parseInt(out_pack[j][case1][case2])));
					    } else if ("Tray".equals(pack_type[j][case1][case2])) { // 450에서 480으로 변경 2011.05.03
						pack_cost[j][case1][case2] = Double.toString((485 / Integer
						        .parseInt(in_pack[j][case1][case2]))
						        + (1056 / Integer.parseInt(out_pack[j][case1][case2])));
					    } else if ("비닐".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((206 / Integer
						        .parseInt(in_pack[j][case1][case2]))
						        + (764 / Integer.parseInt(out_pack[j][case1][case2])));
					    } else if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / Integer
						        .parseInt(out_pack[j][case1][case2]));
					    }
					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = StringUtil.Roundformat(pack_cost[j][case1][case2], 2);
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / Integer
						        .parseInt(out_pack[j][case1][case2]));
					    }

					    if ("".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = StringUtil.Roundformat(pack_cost[j][case1][case2], 2);
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else {
					    pack_cost[j][case1][case2] = "0";
					}
				    }
				    /*********************************/
				    /*************** 물류비 *************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
					dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
				    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
					    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {

					if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
					} else if ("생산4".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
					}
					dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
					        / StringUtil.nullDouble(out_pack[j][case1][case2]));
					dis_cost[j][case1][case2] = StringUtil.Roundformat(dis_cost[j][case1][case2], 2);
				    } else {
					dis_cost[j][case1][case2] = "0";
				    }
				}
				/*********************************/
				/*************** 경비 ***************/
				/*********************************/
				if (!"재료도금".equals(plating_type[j][case1][case2])
				        && !"".equals(StringUtil.checkNull(plating_cost[j][case1][case2]))) {
				    // 직접경비
				    total_expense[j][case1][case2] = plating_cost[j][case1][case2];
				    // 간접경비
				    overhead[j][case1][case2] = "0";
				} else if (!"ok".equals(assy_in[j])) {
				    if (!"empty".equals(pack_type[j][case1][case2]) && !"0".equals(pack_cost[j][case1][case2])) {
					// 직접경비
					total_expense[j][case1][case2] = pack_cost[j][case1][case2];
					// 간접경비
					overhead[j][case1][case2] = Double
					        .toString(StringUtil.nullDouble(total_expense[j][case1][case2]) * 0.2);
				    } else {
					// 직접경비
					total_expense[j][case1][case2] = "0";
					// 간접경비
					overhead[j][case1][case2] = "0";
				    }
				} else {
				    // 직접경비
				    total_expense[j][case1][case2] = pack_cost[j][case1][case2];
				    // 간접경비
				    overhead[j][case1][case2] = "0";
				}
				// 기타경비
				if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
				    gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
				} else {
				    gita_cost[j][case1][case2] = "0";
				}

				// 제조경비
				common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
				        + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil
				            .nullDouble(gita_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));

				if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
				    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
					    + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
				} else {
				    total_expense[j][case1][case2] = total_expense[j][case1][case2];
				}

				if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
				    common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
				}
				/*********************************/
				/*************** 관리비 *************/
				/*********************************/
				// 관세
				if (!"".equals(StringUtil.checkNull(pl_tariff[j][case1][case2]))) {
				    tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(pl_tariff[j][case1][case2]));
				} else {
				    tariff[j][case1][case2] = "0";
				}

				// 재료관리비
				jae_cost[j][0][0] = "0";
				jae_cost[j][case1][case2] = "0";

				if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
				    jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
				}
				// 일반관리비
				if ("ok".equals(assy_in[j])) {
				    ge_cost[j][case1][case2] = "0";
				} else {
				    if (!"empty".equals(pack_type[j][case1][case2]) && !"0".equals(pack_cost[j][case1][case2])) {
					ge_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(total_expense[j][case1][case2]) + StringUtil
					                .nullDouble(overhead[j][case1][case2])) * 0.2);
				    } else {
					ge_cost[j][case1][case2] = Double
					        .toString(StringUtil.nullDouble(type_cost[j][case1][case2]) * 0.05);
				    }
				}

				if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
				    ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
				}
				// 총관리비
				ad_cost[j][case1][case2] = Double
				        .toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
				                + StringUtil.nullDouble(ge_cost[j][case1][case2])
				                + StringUtil.nullDouble(dis_cost[j][case1][case2])
				                + StringUtil.nullDouble(tariff[j][case1][case2]));

				// 품질내용
				if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
				    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
					    + StringUtil.nullDouble(qu_cost[j][case1][case2]));
				}
				/*********************************/
				/*************** 감가비 *************/
				/*********************************/
				// 구매품 감가비 = 금형비, 설비비 / 총 기획수량(판매량 감가) 11.11.30

				if ("감가".equals(mold_c_type[j][case1][case2])) {
				    // 금형비가 있을경우
				    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					// 판매량감가기준(8년치총수량)
					avg_su[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(mold_cost[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(avg_su[j][case1][case2])
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					// //System.out.println("감가비 g_depr_cost[j][case1][case2] : "+g_depr_cost[j][case1][case2]);
					if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) {

					    sul_cost_depr = Double.toString((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1000)
						    / StringUtil.nullDouble(avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));
					    g_depr_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(g_depr_cost[j][case1][case2]) + StringUtil.nullDouble(sul_cost_depr));
					}
				    } else { // 금형비가 없을경우

					if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) { // 금형비는 없으나 실비비가 있는 경우
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					    g_depr_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(sul_cost[j][case1][case2]) * 1000)
						    / StringUtil.nullDouble(avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));
					}
				    }

				} else {
				    g_depr_cost[j][case1][case2] = "0";
				}

				if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
				    if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
					etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				    } else {
					etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				    }
				    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
				    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
				    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				}

				// YZK감가비 20101223 // 필드 yazaki_ro -> 포장용투자비로대체 / 산출식 미정
				// 포장용투자비로 항목 변경에 따른 감가비 추가 : 20110526
				if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
				    yzk_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
					    + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
					    + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
					    + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
					        .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				    yzk_depr[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000) / StringUtil
					            .nullDouble(yzk_su)) * StringUtil.nullDouble(usage[j][case1][case2]));
				    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				}

				if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
				    yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
					    + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				}

				/*********************************/
				/*************** 총원가 *************/
				/*********************************/

				g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(common_cost[j][case1][case2])
				        + StringUtil.nullDouble(ad_cost[j][case1][case2])
				        + StringUtil.nullDouble(labor_cost[j][case1][case2])
				        + StringUtil.nullDouble(g_depr_cost[j][case1][case2]));

				if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
				    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_actual_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				}

				// //System.out.println("총원가 찍기 2: "+g_actual_cost[j][case1][case2]);
				/*********************************/
				/*************** 수익률 *************/
				/*********************************/

				if (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) > 0) {
				    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNull(ket_cost[j]))) {
					g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
				    } else {
					if ("0".equals(target_cost[j])) {
					    g_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
					} else {
					    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
					    g_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(g_actual_cost[j][case1][case2]))
						            / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						                    .nullDouble(target_rate))));
					}
				    }
				} // 종료
				  // //System.out.println("@#@#조립산출 재료비 #@#@ ===> "+part_name[j][case1][case2]
				  // +" "+g_actual_cost[j][case1][case2]);
				  // //System.out.println("@#@#구매 수익률 #@#@ ===>"+ g_earn_rate[j][case1][case2] );
				  // 조립산출 재료비
				if (!"".equals(StringUtil.checkNull(g_assy_m_total_cost[j]))) {
				    g_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(g_assy_m_total_cost[j])
					    + StringUtil.nullDouble(g_actual_cost[j][case1][case2]));

				} else {
				    g_assy_m_total_cost[j] = g_actual_cost[j][case1][case2];
				}
				// sub 조립산출 재료비 1705
				if (case2 > 0) {
				    if (!"".equals(StringUtil.checkNull(s_sub_m_total_cost[j][case1]))) {
					s_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(s_sub_m_total_cost[j][case1])
					        + StringUtil.nullDouble(s_actual_cost[j][case1][case2]));
				    } else {
					s_sub_m_total_cost[j][case1] = s_actual_cost[j][case1][case2];
				    }
				    if (!"".equals(StringUtil.checkNull(g_sub_m_total_cost[j][case1]))) {
					g_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(g_sub_m_total_cost[j][case1])
					        + StringUtil.nullDouble(g_actual_cost[j][case1][case2]));
				    } else {
					g_sub_m_total_cost[j][case1] = g_actual_cost[j][case1][case2];
				    }
				}
			    } else if ("외주".equals(make_type[j][case1][case2]) && !"assy".equals(pro_type[j][case1][case2])
				    && !"sub_assy".equals(pro_type[j][case1][case2]) && !"Insert".equals(pro_type[j][case1][case2])
				    && !"sub_Insert".equals(pro_type[j][case1][case2])) {
				/*********************************/
				/*************** 노무비 *************/
				/*********************************/
				labor_cost[j][case1][case2] = "0"; // 노무비
				if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
				    labor_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
				}
				// //System.out.println("cost acc() 노무비 2 : "+labor_cost);
				if ("make_1".equals(make[j][case1][case2]) || "TML".equals(pro_type[j][case1][case2])) { // 비철
				    if ("무상".equals(pro_1[j][case1][case2])) {
					/*********************************/
					/*************** 재료비 *************/
					/*********************************/
					if ("재료도금".equals(plating_type[j][case1][case2])) {
					    unitcost_si[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(unitcost_si[j][case1][case2])
						    + (StringUtil.nullDouble(plating_cost[j][case1][case2]) / 1000));
					} else {
					    unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
					}

					if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
					    // 원재료비(재생반영)
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(unitcost_si[j][case1][case2])
						    * StringUtil.nullDouble(t_weight[j][case1][case2]));
					    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
						meterial_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(meterial_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					    }

					    // 스크랩판매비(재생반영)
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
						    * StringUtil.nullDouble(scrap_cost[j][case1][case2]));
					    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
						scrap_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(scrap_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
					    }

					    // loss비
					    loss[j][case1][case2] = Double
						    .toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02);
					    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
						loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					    }

					    // 총재료비
					    m_total_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
						        .nullDouble(scrap_cost[j][case1][case2]))
						    * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    // 원재료비
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(unitcost_si[j][case1][case2])
						    * StringUtil.nullDouble(t_weight[j][case1][case2]));
					    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
						meterial_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(meterial_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					    }

					    // 스크랩판매비
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
						    * StringUtil.nullDouble(scrap_cost_im));
					    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
						scrap_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(scrap_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
					    }

					    // loss비
					    loss[j][case1][case2] = Double
						    .toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02);
					    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
						loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					    }

					    // 총재료비
					    m_total_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
						        .nullDouble(scrap_cost[j][case1][case2]))
						    * StringUtil.nullDouble(usage[j][case1][case2]));

					}
				    } else {
					m_total_cost[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
					m_total_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(m_total_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));

				    }

				    /*********************************/
				    /*************** 제조경비 ***********/
				    /*********************************/
				    // 전력비
				    jun_cost[j][case1][case2] = "0";
				    if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
					jun_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
				    }

				    // 기계감가
				    ma_depr[j][case1][case2] = "0";
				    if (!"".equals(StringUtil.checkNull(pl_ma_depr[j][case1][case2]))) {
					ma_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_ma_depr[j][case1][case2]));
				    }

				    // 타발유
				    tabalu[j][case1][case2] = "0";
				    if (!"".equals(StringUtil.checkNull(pl_tabalu[j][case1][case2]))) {
					tabalu[j][case1][case2] = Double.toString(StringUtil.nullDouble(tabalu[j][case1][case2])
					        + StringUtil.nullDouble(pl_tabalu[j][case1][case2]));
				    }

				    // 포장비
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = StringUtil.Roundformat(pack_cost[j][case1][case2], 2);
				    } else {
					if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
					        && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("Reel".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((1072 / StringUtil
						        .nullDouble(in_pack[j][case1][case2]))
						        + (1277 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("Tray".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((485 / StringUtil
						        .nullDouble(in_pack[j][case1][case2]))
						        + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
						        .nullDouble(out_pack[j][case1][case2]));
					    }

					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = StringUtil.Roundformat(pack_cost[j][case1][case2], 2);
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
						        .nullDouble(out_pack[j][case1][case2]));
					    }
					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = StringUtil.Roundformat(pack_cost[j][case1][case2], 2);
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else {
					    pack_cost[j][case1][case2] = "0";
					}
				    }
				    // 금형유지비 (생산3팀도 금형유지비적용 10.01.22)
				    m_upkeep[j][case1][case2] = "0.3";
				    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
					m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
				    }

				    // 직접경비
				    if (!"재료도금".equals(plating_type[j][case1][case2])
					    && !"".equals(StringUtil.checkNull(plating_cost[j][case1][case2]))) {
					total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(tabalu[j][case1][case2])
					        + StringUtil.nullDouble(type_cost[j][case1][case2])
					        + StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pack_cost[j][case1][case2])
					        + StringUtil.nullDouble(plating_cost[j][case1][case2]));
				    } else {
					total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(tabalu[j][case1][case2])
					        + StringUtil.nullDouble(type_cost[j][case1][case2])
					        + StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pack_cost[j][case1][case2]));

				    }

				    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
					total_expense[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(total_expense[j][case1][case2])
					        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
				    } else {
					total_expense[j][case1][case2] = total_expense[j][case1][case2];
				    }

				    // 간접경비
				    // //System.out.println("total_expense>>>> "+total_expense[j][case1][case2]);
				    overhead[j][case1][case2] = Double
					    .toString(StringUtil.nullDouble(total_expense[j][case1][case2]) * 0.2);
				    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
					overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
					        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
				    }

				    // 기타경비
				    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
					gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
				    } else {
					gita_cost[j][case1][case2] = "0";
				    }

				    // 제조경비
				    common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
					    + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil
					        .nullDouble(gita_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));

				    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
					common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
				    }

				    // //System.out.println("common_cost[j][case1][case2] 구하기 ... >>> "+common_cost[j][case1][case2]);

				    /*********************************/
				    /*************** 감가비 *************/
				    /*********************************/
				    // 금형감가비
				    // 기준수량
				    if ("개조".equals(part_type_1[j][case1][case2])) {
					// 일반감가기준(50000000)
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					}
					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * StringUtil
					        .nullDouble(cavity[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    } else if ("공용".equals(part_type_1[j][case1][case2])) {
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "일반";
					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					    mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
					} else {
					    mold_cost[j][case1][case2] = "2500";
					}
				    } else {
					// 일반감가기준(50000000)
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					    // avg_su[j][case1][case2] =
					    // Double.toString(((StringUtil.nullDouble(a_su_year_1[j])+StringUtil.nullDouble(a_su_year_2[j])+StringUtil.nullDouble(a_su_year_3[j])+StringUtil.nullDouble(a_su_year_4[j])+StringUtil.nullDouble(a_su_year_5[j])+StringUtil.nullDouble(a_su_year_6[j])+StringUtil.nullDouble(a_su_year_7[j])+StringUtil.nullDouble(a_su_year_8[j])
					    // )*1000)* StringUtil.nullDouble(usage[j][case1][case2]));
					}
					// //System.out.println("제대로 찍히나 s_avg_su "+s_avg_su[j][case1][case2] );
					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer
					        .parseInt(cavity[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    }
				    // //System.out.println("제대로 찍히나 avg su "+s_avg_su[j][case1][case2] );
				    // //System.out.println("제대로 찍히나 avg 2 "+avg_su[j][case1][case2] );
				    if ("감가".equals(mold_c_type[j][case1][case2])) {
					// 일반감가기준 (50000000)
					s_depr_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(mold_cost[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(s_avg_su[j][case1][case2])
					                * StringUtil.nullDouble(usage[j][case1][case2]));

					// 판매량감가기준(6년치총수량)
					g_depr_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(mold_cost[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(avg_su[j][case1][case2])
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					// System.out.println("진짜 : "+mold_cost[j][case1][case2] + " " +avg_su[j][case1][case2] +
					// "  "+StringUtil.nullDouble(usage[j][case1][case2]));
				    } else {
					s_depr_cost[j][case1][case2] = "0";
					g_depr_cost[j][case1][case2] = "0";
				    }

				    // 기타투자비감가
				    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
					etc_su = "0";
					etc_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					etc_depr[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
				    } else {
					etc_depr[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
					if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
					    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
						    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
					} else {
					    etc_depr[j][case1][case2] = Double
						    .toString(StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
					}
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				    }

				    // YZK감가비 20101223 // 필드 yazaki_ro → 포장용투자비로대체 / 산출식 미정
				    // 포장용투자비로 항목 변경에 따른 감가비추가 : 20110526
				    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
					yzk_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					yzk_depr[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000) / StringUtil
					                .nullDouble(yzk_su)) * StringUtil.nullDouble(usage[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
					yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				    }

				    /*********************************/
				    /*************** 물류비 *************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
					dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
				    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
					    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
					if ("생산".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
					} else if ("생산4".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
					}
					dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
					        / StringUtil.nullDouble(out_pack[j][case1][case2]));
					dis_cost[j][case1][case2] = StringUtil.Roundformat(dis_cost[j][case1][case2], 2);
				    } else {
					dis_cost[j][case1][case2] = "0";
				    }
				    /*********************************/
				    /*************** 관리비 *************/
				    /*********************************/
				    // 가공비
				    process_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2]));
				    // 재료관리비
				    jae_cost[j][case1][case2] = Double
					    .toString(StringUtil.nullDouble(m_total_cost[j][case1][case2]) * 0.011);
				    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
					jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
				    }

				    // 일반관리비 - 12.2.17 비율 0.2에서 0.5로 변경
				    ge_cost[j][case1][case2] = Double.toString(Double.parseDouble(StringUtil.Roundformat(
					    process_cost[j][case1][case2], 4)) * 0.5);
				    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
					ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
				    }

				    // R&D비 - 12.2.17 비율 0.024에서 0.019로 변경
				    if (!"OK".equals(a_pro_type)) {
					s_rnd_cost[j][case1][case2] = Double.toString(((StringUtil
					        .nullDouble(m_total_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(s_depr_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])) + (StringUtil
					        .nullDouble(common_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
					        * StringUtil.nullDouble(usage[j][case1][case2]) * 0.019);
					g_rnd_cost[j][case1][case2] = Double.toString(((StringUtil
					        .nullDouble(m_total_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(g_depr_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])) + (StringUtil
					        .nullDouble(common_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
					        * StringUtil.nullDouble(usage[j][case1][case2]) * 0.019);
				    }

				    if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
					s_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
					g_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
				    }

				    // 총관리비
				    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					    + StringUtil.nullDouble(ge_cost[j][case1][case2])
					    + StringUtil.nullDouble(dis_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_rnd_cost[j][case1][case2]));
				    // System.out.println("가자 ad_cost : "+"jae_cost : "+jae_cost[j][case1][case2]+" ge_cost : "+ge_cost[j][case1][case2]+" dis_cost : "+dis_cost[j][case1][case2]+" g_rnd_cost : "+g_rnd_cost[j][case1][case2]);
				    // 품질비용
				    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
					ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
					        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
				    }
				    /*********************************/
				    /*************** 총원가 *************/
				    /*********************************/
				    s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2]));
				    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2]));
				    // System.out.println("가자 g_actual_cost : "+m_total_cost[j][case1][case2]+"g_actual_cost : "+m_total_cost[j][case1][case2]+"labor_cost : "+labor_cost[j][case1][case2]+"common_cost : "+common_cost[j][case1][case2]+"g_depr_cost : "+g_depr_cost[j][case1][case2]+"ad_cost : "+ad_cost[j][case1][case2]
				    // );
				    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
					s_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
					g_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				    }
				    // //System.out.println("총원가 찍기 3 : "+g_actual_cost[j][case1][case2]);
				    /*********************************/
				    /*************** 수익률 *************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
					s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
					g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
				    } else {
					if ("0".equals(target_cost[j])) {
					    s_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7));
					    g_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
					} else {
					    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
					    s_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(s_actual_cost[j][case1][case2]))
						            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
						                    .nullDouble(target_rate))));
					    g_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(g_actual_cost[j][case1][case2]))
						            / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						                    .nullDouble(target_rate))));
					}
				    }
				} else if ("HSG".equals(pro_type[j][case1][case2]) || "HSG-Box".equals(pro_type[j][case1][case2])) { // 수지
				    // 2213
				    if ("무상".equals(pro_1[j][case1][case2])) {
					/*********************************/
					/*************** 재료비 *************/
					/*********************************/
					scrap_cost[j][case1][case2] = "0";
					if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(scrap_cost[j][case1][case2])
						    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
					}
					if (!"0".equals(recycle[j][case1][case2])
					        && !"".equals(StringUtil.checkNull(recycle[j][case1][case2]))) {
					    rec_weight = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
						    * (StringUtil.nullDouble(recycle[j][case1][case2]) * 100));
					    if (StringUtil.nullDouble(rec_weight) < StringUtil.nullDouble(String.format("%.4f",
						    StringUtil.nullDouble(h_scrap[j][case1][case2])))) {
						// 원재료비(재생반영)
						meterial_cost[j][case1][case2] = Double.toString((StringUtil
						        .nullDouble(t_weight[j][case1][case2]) - StringUtil.nullDouble(rec_weight))
						        * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
					    } else {
						// 원재료비(재생반영)
						meterial_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(h_weight[j][case1][case2])
						        * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
					    }
					} else {
					    // 원재료비
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(t_weight[j][case1][case2])
						    * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
					    recycle[j][case1][case2] = "0";
					}
					// 2235
					if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					}

					// loss비
					loss[j][case1][case2] = Double
					        .toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.03);
					if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					}

					// 총재료비
					m_total_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					                    .nullDouble(scrap_cost[j][case1][case2]))
					                * StringUtil.nullDouble(usage[j][case1][case2]));

				    } else {
					m_total_cost[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
					m_total_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(m_total_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));

				    }

				    /*********************************/
				    /*************** 재조경비 ***********/
				    /*********************************/
				    // 포장비 임의값
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = String.format("%.2f",
					        StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    } else {
					if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
					        && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("비닐".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((206 / (StringUtil
						        .nullDouble(in_pack[j][case1][case2])))
						        + (764 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("Tray".equals(pack_type)) {
						pack_cost[j][case1][case2] = Double.toString((485 / (StringUtil
						        .nullDouble(in_pack[j][case1][case2])))
						        + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / (StringUtil
						        .nullDouble(out_pack[j][case1][case2])));
					    }

					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = String.format("%.2f",
						        StringUtil.nullDouble(pack_cost[j][case1][case2]));
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / (StringUtil
						        .nullDouble(out_pack[j][case1][case2])));
					    }
					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = String.format("%.2f",
						        StringUtil.nullDouble(pack_cost[j][case1][case2]));
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else {
					    pack_cost[j][case1][case2] = "0";
					}
				    }

				    // 금형유지비
				    if (StringUtil.nullDouble(cavity[j][case1][case2]) < 5) {
					m_upkeep[j][case1][case2] = "1.5";
				    } else if (StringUtil.nullDouble(cavity[j][case1][case2]) < 9) {
					m_upkeep[j][case1][case2] = "1";
				    } else if (StringUtil.nullDouble(cavity[j][case1][case2]) > 15) {
					m_upkeep[j][case1][case2] = "0.5";
				    } else {
					m_upkeep[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
					m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
				    }

				    // 직접경비
				    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
					    + StringUtil.nullDouble(pack_cost[j][case1][case2])
					    + StringUtil.nullDouble(m_upkeep[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
					total_expense[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(total_expense[j][case1][case2])
					        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
				    } else {
					total_expense[j][case1][case2] = total_expense[j][case1][case2];
				    }

				    // 간접경비
				    overhead[j][case1][case2] = Double
					    .toString(StringUtil.nullDouble(total_expense[j][case1][case2]) * 0.2);
				    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
					overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
					        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
				    }

				    // 기타경비
				    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
					gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
				    } else {
					gita_cost[j][case1][case2] = "0";
				    }
				    // 제조경비
				    common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
					    + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil
					        .nullDouble(gita_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
					common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
				    }
				    /*********************************/
				    /*************** 감가비 *************/
				    /*********************************/
				    // 2345
				    // 금형감가비
				    // 기준수량
				    if ("개조".equals(part_type_1[j][case1][case2])) {
					// 일반감가기준(1000000)
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					}

					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (1000000 * StringUtil
					        .nullDouble(cavity[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    } else if ("공용".equals(part_type_1[j][case1][case2])) {
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "일반";
					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					    mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
					} else {
					    mold_cost[j][case1][case2] = "3000";
					}
				    } else {
					// 일반감가기준(1000000)
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} // 2400

					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (1000000 * StringUtil
					        .nullDouble(cavity[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    }

				    if ("감가".equals(mold_c_type[j][case1][case2])) {
					// 일반감가기준(1000000)
					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					    s_depr_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(mold_cost[j][case1][case2]) * 1000)
						    / StringUtil.nullDouble(s_avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));
					}
					// 판매량감가기준(6년치총수량)
					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					    g_depr_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(mold_cost[j][case1][case2]) * 1000)
						    / StringUtil.nullDouble(avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));

					}
				    } else {
					s_depr_cost[j][case1][case2] = "0";
					g_depr_cost[j][case1][case2] = "0";
				    }
				    // 일반감가기준(1000000)
				    if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) {
					s_depr_cost[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
					                / StringUtil.nullDouble(s_avg_su[j][case1][case2])
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    }

				    // 판매량감가기준 (6년치총수량)
				    if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
					                / StringUtil.nullDouble(avg_su[j][case1][case2])
					                * StringUtil.nullDouble(usage[j][case1][case2]));

				    }

				    // 기타투자비감가
				    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
					etc_su = "0";
					etc_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					etc_depr[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));

				    } else {
					etc_depr[j][case1][case2] = "0";
				    }
				    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
					if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
					    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
						    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
					} else {
					    etc_depr[j][case1][case2] = Double
						    .toString(StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
					}
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				    }

				    // YZK감가비 20101223 // 필드 yazaki_ro → 포장용투자비로대체 / 산출식 미정
				    // 포장용투자비로 항목 변경에 따른 감가비추가 : 20110526
				    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
					yzk_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					yzk_depr[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000) / StringUtil
					                .nullDouble(yzk_su)) * StringUtil.nullDouble(usage[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				    } // 2500

				    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
					yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				    }

				    /*********************************/
				    /*************** 물류비 *************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
					dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
				    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
					    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
					if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
					} else if ("생산4".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
					}
					dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
					        / StringUtil.nullDouble(out_pack[j][case1][case2]));
					dis_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(dis_cost[j][case1][case2]));
				    } else {
					dis_cost[j][case1][case2] = "0";
				    }

				    /*********************************/
				    /*************** 관리비 *************/
				    /*********************************/
				    // 가공비
				    process_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2]));

				    // 재료관리비 11.12.13 0.01에서 0.011로변경
				    jae_cost[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(m_total_cost[j][case1][case2]) * 0.011));
				    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
					jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
				    }

				    // 일반관리비 11.12.13 0.02에서0.28로변경
				    ge_cost[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(process_cost[j][case1][case2]) * 0.28));
				    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
					ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
				    }

				    // R&D비
				    if (!"OK".equals(a_pro_type)) {
					s_rnd_cost[j][case1][case2] = Double.toString(((StringUtil
					        .nullDouble(m_total_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(s_depr_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])) + (StringUtil
					        .nullDouble(common_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
					        * StringUtil.nullDouble(usage[j][case1][case2]) * 0.038);
					g_rnd_cost[j][case1][case2] = Double.toString(((StringUtil
					        .nullDouble(m_total_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(g_depr_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])) + (StringUtil
					        .nullDouble(common_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
					        * StringUtil.nullDouble(usage[j][case1][case2]) * 0.038);
				    }

				    if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
					s_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
					g_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
				    }

				    // 총관리비
				    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					    + StringUtil.nullDouble(ge_cost[j][case1][case2])
					    + StringUtil.nullDouble(dis_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_rnd_cost[j][case1][case2]));

				    // 품질비용
				    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
					ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
					        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
				    }

				    /*********************************/
				    /*************** 총원가 *************/
				    /*********************************/
				    s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2]));
				    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
					s_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
					g_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				    }
				    // //System.out.println("총원가 찍기 4: "+g_actual_cost[j][case1][case2]);
				    /*********************************/
				    /*************** 수익률 *************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
					s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
					g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
				    } else {
					if ("0".equals(target_cost[j])) {
					    s_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7));
					    g_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
					} else {
					    s_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(s_actual_cost[j][case1][case2]))
						            / (1 - StringUtil.nullDouble(target_rate)));
					    g_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(g_actual_cost[j][case1][case2]))
						            / (1 - StringUtil.nullDouble(target_rate)));
					}
				    }
				} // 2657
				  // 조립산출 재료비
				if (!"".equals(StringUtil.checkNull(s_assy_m_total_cost[j]))) {
				    s_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(s_assy_m_total_cost[j])
					    + StringUtil.nullDouble(s_actual_cost[j][case1][case2]));
				} else {
				    s_assy_m_total_cost[j] = s_actual_cost[j][case1][case2];
				}
				// System.out.println("g_assy_m_total_cost 111 === group ==> "+group_no[j][case1][case2] +" : "+
				// g_assy_m_total_cost[j] );
				// System.out.println("g_actual_cost 111 === group ==> "+group_no[j][case1][case2] +" : "+
				// g_actual_cost[j][case1][case2]);
				if (!"".equals(StringUtil.checkNull(g_assy_m_total_cost[j]))) {
				    g_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(g_assy_m_total_cost[j])
					    + StringUtil.nullDouble(g_actual_cost[j][case1][case2]));
				} else {
				    g_assy_m_total_cost[j] = g_actual_cost[j][case1][case2];
				}
				// System.out.println("g_assy_m_total_cost === group ==> "+group_no[j][case1][case2] +" : "+
				// g_assy_m_total_cost[j] );
				// sub조립산출재료비
				if (case2 > 0) {
				    if (!"".equals(StringUtil.checkNull(s_sub_m_total_cost[j][case1]))) {
					s_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(s_sub_m_total_cost[j][case1])
					        + StringUtil.nullDouble(s_actual_cost[j][case1][case2]));
				    } else {
					s_sub_m_total_cost[j][case1] = s_actual_cost[j][case1][case2];
				    }
				    if (!"".equals(StringUtil.checkNull(g_sub_m_total_cost[j][case1]))) {
					g_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(g_sub_m_total_cost[j][case1])
					        + StringUtil.nullDouble(g_actual_cost[j][case1][case2]));
				    } else {
					g_sub_m_total_cost[j][case1] = g_actual_cost[j][case1][case2];
				    }
				}
			    } else if ((!"assy".equals(pro_type[j][case1][case2]) && !"sub_assy".equals(pro_type[j][case1][case2])
				    && !"Insert".equals(pro_type[j][case1][case2]) && !"sub_Insert".equals(pro_type[j][case1][case2]) && !"TML-조립"
				        .equals(pro_type[j][case1][case2])) && "사내".equals(make_type[j][case1][case2])) {
				// 일반단품계산

				// '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
				// TML, HS'G 의 전력비 산출기준 변경 (2011.12.01 )
				// → 설비Ton의 입력값기준으로 전력비의 값이 변경됨
				// '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
				// 설비Ton 변수명 = ton_(j,p,q)
				// 제품type 변수명 = pro_type_(j,p,q)
				// 생산처 변수명 = pro_1_(j,p,q)
				// '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
				// TML and 생산1,2 팀 일경우 [st_pro_type = "TML" and (st_team = "생산1" or st_team = "생산2")]
				// 설비Ton 30~40미만 = 5,017원
				// 설비Ton 40~60미만 = 5,459원
				// 설비Ton 60~ = 8,115원

				// TML and 생산3 팀 일경우 [st_pro_type = "TML" and st_team = "생산3"]
				// 설비Ton 무관 = 45,097원

				// TML and 중국 일경우 [st_pro_type = "TML" and st_team = "중국"]
				// 설비Ton 무관 = 1,319원

				// HSG,HSG-Box and 생산2 팀 일경우 [(st_pro_type = "HSG" or st_pro_type = "HSG-Box") and st_team = "생산2"]
				// 설비Ton 40~80미만 = 3,393원
				// 설비Ton 80~150미만 = 4,806원
				// 설비Ton 150~ = 5,089원

				// HSG,HSG-Box and 생산3 팀 일경우 [(st_pro_type = "HSG" or st_pro_type = "HSG-Box") and st_team = "생산3"]
				// 설비Ton 무관 = 10,157원

				// HSG,HSG-Box and 중국 일경우 [(st_pro_type = "HSG" or st_pro_type = "HSG-Box") and st_team = "중국"]
				// 설비Ton 0~150미만 = 2,050원
				// 설비Ton 150~ = 2,799원
				//
				// '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
				// 비철
				if (("make_1".equals(make[j][case1][case2]) && !"TML-조립".equals(pro_type[j][case1][case2]))
				        || "TML".equals(pro_type[j][case1][case2])) {
				    if (StringUtil.nullDouble(ton[j][case1][case2]) > 29
					    && StringUtil.nullDouble(ton[j][case1][case2]) < 40) {
					ton_value = "39";
				    } else if (StringUtil.nullDouble(ton[j][case1][case2]) < 60) {
					ton_value = "59";
				    } else {
					ton_value = "60";
				    }

				    ArrayList accExStandardList = null;

				    try {
					conn = DBUtil.getConnection();
					costAccDao = new CostAccDao(conn);
					accExStandardList = costAccDao.getAccExStandardList(pro_1[j][case1][case2],
					        pro_type[j][case1][case2], ton_value, "1");
				    } catch (Exception e) {
					e.printStackTrace();
				    } finally {
					DBUtil.close(conn);
				    }

				    // System.out.println("getAccExStandardList 의 pro_1 : " +pro_1[j][case1][case2]);
				    // //System.out.println("getAccExStandardList 의 pro_type : " + pro_type[j][case1][case2]);
				    // //System.out.println("getAccExStandardList 의 ton_value : " + ton_value);
				    Hashtable accExStandardMap = null;
				    accExStandardMap = (Hashtable) accExStandardList.get(0);
				    st_eff_value_in = (String) accExStandardMap.get("st_eff_value"); // 효율
				    st_person_in = (String) accExStandardMap.get("st_person"); // 표준인원
				    st_rate = (String) accExStandardMap.get("st_rate"); // 임율
				    st_jun_cost = (String) accExStandardMap.get("st_jun_cost"); // 전력비
				    st_ma_depr = (String) accExStandardMap.get("st_ma_depr"); // 기계감가
				    st_tabalu = (String) accExStandardMap.get("st_tabalu"); // 타발유
				    st_overhead = (String) accExStandardMap.get("st_overhead"); // 간접경비
				    st_ge_cost = (String) accExStandardMap.get("st_ge_cost"); // 일반관리비율
				    st_jae_cost = (String) accExStandardMap.get("st_jae_cost"); // 재료관리비율
				    st_rnd_cost = (String) accExStandardMap.get("st_rnd_cost"); // R&D비율
				    st_royalty = (String) accExStandardMap.get("st_royalty"); // 로얄티비율

				    st_eff_value[j][case1][case2] = st_eff_value_in;// 효율
				    st_person[j][case1][case2] = st_person_in;// 표준인원

				    if ("기타".equals(pro_level[j][case1][case2])) { // 작업인원
					st_person_in = pro_level_txt[j][case1][case2];
				    } else if (!"".equals(StringUtil.checkNull(st_person_in))) {
					// System.out.println("최초 st_person_in : "+st_person_in);
					temp_1 = new BigDecimal("1");
					temp_2 = new BigDecimal(StringUtil.checkNull(st_person_in));

					st_person_in = Double.toString(temp_1.divide(temp_2).doubleValue());

					// st_person_in = Integer.toString(1 / Integer.parseInt(st_person_in));
				    } else {
					st_person_in = "1";
				    }
				    // System.out.println("최종 st_person_in : "+st_person_in);
				    /*********************************/
				    /*************** 재료비 *************/
				    /*********************************/
				    if ("재료도금".equals(plating_type[j][case1][case2])) {
					unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					        + (StringUtil.nullDouble(plating_cost[j][case1][case2])) / 1000);
				    } else {
					unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
				    }

				    if ("make_4".equals(make[j][case1][case2])) {// 벤도리아핀일경우(사내제작)

					// 벤도리아핀DB에서 Carrier 원재료비, Carrier원재료명 출력
					ArrayList accBandolierList = null;
					try {
					    conn = DBUtil.getConnection();
					    costAccDao = new CostAccDao(conn);
					    accBandolierList = costAccDao.getAccBandolierList();
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    DBUtil.close(conn);
					}

					Hashtable accBandolierMap = null;
					accBandolierMap = (Hashtable) accBandolierList.get(0);
					carrier_cost = (String) accBandolierMap.get("carrier_cost");
					carrier_grade = (String) accBandolierMap.get("carrier_grade");

					// carrier 원재료단가 1.30원
					// 스크랩판매단가산출
					if ("C2100R".equals(carrier_grade)) {
					    scrap_cost_im = Double.toString(((StringUtil.nullDouble(W_Lme_cu) * 0.95 + StringUtil
						    .nullDouble(W_Lme_zn) * 0.05) / 1000) * 0.55);
					} else if ("C2600R".equals(carrier_grade) || "C2680R".equals(carrier_grade)) { // 석황동 스크랩판가비율 55% 에서
						                                                                       // 60%변경 2010.06.30
					    scrap_cost_im = Double.toString(((StringUtil.nullDouble(W_Lme_cu) * 0.65 + StringUtil
						    .nullDouble(W_Lme_zn) * 0.35) / 1000) * 0.60);
					} else if ("C5210R".equals(carrier_grade) || "C5191R".equals(carrier_grade)
					        || "PMC26R".equals(carrier_grade) || "PMC90R".equals(carrier_grade)) { // 인청동
						                                                                       // 스크랩판가비율
						                                                                       // 45%에서
						                                                                       // 50%변경
						                                                                       // 2010.06.30
					    scrap_cost_im = Double.toString(((StringUtil.nullDouble(W_Lme_cu) * 0.92 + StringUtil
						    .nullDouble(W_Lme_sn) * 0.08) / 1000) * 0.50);
					} else if ("C194".equals(carrier_grade)) {
					    scrap_cost_im = Double.toString((StringUtil.nullDouble(W_Lme_cu) * 0.98 / 1000) * 0.50);
					} else {
					    scrap_cost_im = Double.toString(StringUtil.nullDouble(carrier_cost) * 0.45);
					}

					carrier_scrap = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
					        * StringUtil.nullDouble(scrap_cost_im)); // 스크랩판매비
					t_carrier_cost = Double.toString(StringUtil.nullDouble(carrier_cost)
					        - StringUtil.nullDouble(carrier_scrap)); // 탄창총재료비
					wire_cost = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					        * StringUtil.nullDouble(h_weight[j][case1][case2])); // wire 원재료비
					wire_cost_loss = Double.toString(StringUtil.nullDouble(wire_cost) * 0.02); // wire loss
					m_wire_cost = Double.toString(StringUtil.nullDouble(wire_cost)
					        + StringUtil.nullDouble(wire_cost_loss)); // wire 총재료비

					meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_carrier_cost)
					        + StringUtil.nullDouble(m_wire_cost)); // 벤도리아핀 재료비

					if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					}

					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(carrier_cost) * 0.02); // loss비
					if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					}

					m_total_cost[j][case1][case2] = Double.toString((StringUtil
					        .nullDouble(meterial_cost[j][case1][case2]) + StringUtil.nullDouble(loss[j][case1][case2]))
					        * StringUtil.nullDouble(usage[j][case1][case2])); // 총재료비

				    } else {
					if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(unitcost_si[j][case1][case2])
						    * StringUtil.nullDouble(t_weight[j][case1][case2])); // 원재료비(재생반영)
					    if (!"".equals(StringUtil.checkNull(StringUtil.checkNull(pl_meterial_cost[j][case1][case2])))) {
						meterial_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(meterial_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					    }
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
						    * StringUtil.nullDouble(scrap_cost[j][case1][case2])); // 스크랩판매비(재생반영)
					    if (!"".equals(StringUtil.checkNull(StringUtil.checkNull(pl_scrap_cost[j][case1][case2])))) {
						scrap_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(scrap_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
					    }
					    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
						    * StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.02); // loss비
					    if (!"".equals(StringUtil.checkNull(StringUtil.checkNull(pl_loss[j][case1][case2])))) {
						loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					    }

					    m_total_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
						        .nullDouble(scrap_cost[j][case1][case2]))
						    * StringUtil.nullDouble(usage[j][case1][case2])); // 총재료비

					} else {
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(unitcost_si[j][case1][case2])
						    * StringUtil.nullDouble(t_weight[j][case1][case2])); // 원재료비
					    if (!"".equals(StringUtil.checkNull(StringUtil.checkNull(pl_meterial_cost[j][case1][case2])))) {
						meterial_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(meterial_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
					    }
					    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
						    * StringUtil.nullDouble(scrap_cost_im)); // 스크랩판매비 스크랩판매단가 :
						                                             // Kg당4650원
					    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
						scrap_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(scrap_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
					    }
					    loss[j][case1][case2] = Double
						    .toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02); // loss비
					    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
						loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
						        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
					    }

					    m_total_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(meterial_cost[j][case1][case2])
						    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
						        .nullDouble(scrap_cost[j][case1][case2]))
						    * StringUtil.nullDouble(usage[j][case1][case2])); // 총재료비

					}
				    }
				    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
					m_total_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(m_total_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));

				    }
				    /*********************************/
				    /*************** 노무비 *************/
				    /*********************************/
				    if (!"make_4".equals(make[j][case1][case2]) && !"생산1".equals(pro_1[j][case1][case2])
					    && !"생산3".equals(pro_1[j][case1][case2])) {
					st_eff_value_in = "1";
					st_rate = "0";
					st_person_in = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(eff_value[j][case1][case2]))) {
					st_eff_value_in = eff_value[j][case1][case2];
					st_eff_value[j][case1][case2] = eff_value[j][case1][case2];
				    }

				    output[j][case1][case2] = Double.toString(60 * StringUtil.nullDouble(spm[j][case1][case2])
					    * StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in)); // 시간당생산량
				    if ("변색방지".equals(plating_type[j][case1][case2])) { // 도금 후 처리란에 변색방지선택시 시간당 생산량에 (60*3500/p_size*0.8)
					                                                // 추가 11.12.02
					// an_output 은 변색방지에 들어가는 시간당 생산량
					an_output = Double.toString(60 * 3500 / StringUtil.nullDouble(p_size[j][case1][case2]) * 0.8);
				    }

				    if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
					output[j][case1][case2] = Double.toString(StringUtil.nullDouble(output[j][case1][case2])
					        + StringUtil.nullDouble(pl_output[j][case1][case2]));
				    }
				    rate[j][case1][case2] = Double.toString(StringUtil.nullDouble(st_rate)
					    * StringUtil.nullDouble(st_person_in)); // 임율
				    // System.out.println("cost acc()의 rate :           " + st_rate);
				    // System.out.println("cost acc()의 st_person_in : " + st_person_in);
				    // System.out.println("cost acc()의 pl_rate : " + pl_rate[j][case1][case2]);

				    if (!"".equals(StringUtil.checkNull(pl_rate[j][case1][case2]))) {
					rate[j][case1][case2] = Double.toString(StringUtil.nullDouble(rate[j][case1][case2])
					        + StringUtil.nullDouble(pl_rate[j][case1][case2]));
				    }

				    if ("검사".equals(type_1[j][case1][case2]) || "포장".equals(type_1[j][case1][case2])) {
					// 노무비
					labor_cost[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
					                .nullDouble(output[j][case1][case2])) + (StringUtil.nullDouble(st_rate) / (3600 / StringUtil
					                .nullDouble(type_cost[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in))))
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    } else {
					// 노무비
					labor_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
					                .nullDouble(output[j][case1][case2]))
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    }
				    // System.out.println("cost acc()의 rate : " + rate[j][case1][case2]);
				    // System.out.println("cost acc()의 output : " + output[j][case1][case2]);
				    // System.out.println("cost acc()의 usage : " + usage[j][case1][case2]);
				    // System.out.println("cost acc() 노무비 3 : "+labor_cost[j][case1][case2]);
				    if ("변색방지".equals(plating_type)) {// 도금 후 처리란에 변색방지선택시 시간당 생산량에 (4800*3*0.5/an_output) 추가 11.12.02
					// an_labor_cost는 변색방지에 들어가는 노무비
					an_labor_cost = Double.toString(4800 * 3 * 0.5 / StringUtil.nullDouble(an_output));
					labor_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					        + StringUtil.nullDouble(an_labor_cost));
				    }
				    // System.out.println("cost acc() 노무비 4 : "+labor_cost[j][case1][case2]);
				    if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
					labor_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
				    }
				    // System.out.println("cost acc() 노무비 5 : "+labor_cost[j][case1][case2]);
				    /*********************************/
				    /************* 제조경비 *************/
				    /*********************************/
				    // 전력비
				    // System.out.println(":: st_jun_cost : "+st_jun_cost);
				    // System.out.println(":: output : "+output[j][case1][case2]);
				    jun_cost[j][case1][case2] = String.format("%.4f",
					    StringUtil.nullDouble(st_jun_cost) / StringUtil.nullDouble(output[j][case1][case2]));
				    if ("변색방지".equals(plating_type)) {// 도금 후 처리란에 변색방지선택시 시간당 생산량에 (3230/an_output) 추가 11.12.02
					// an_jun_output은 변색방지에 들어가는 전력비
					an_jun_cost = Double.toString(3230 / StringUtil.nullDouble(an_output));
					jun_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(an_jun_cost));
				    }
				    if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
					jun_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
				    }

				    // 기계감가
				    ma_depr[j][case1][case2] = String.format("%.4f",
					    StringUtil.nullDouble(st_ma_depr) / StringUtil.nullDouble(output[j][case1][case2]));
				    if ("변색방지".equals(plating_type)) {// 도금 후처리란에 변색방지선택시 기계감가에 (7050/an_output) 추가 11.12.02
					// an_ma_depr = 변색방지에 들어가는 전력비
					an_ma_depr = Double.toString(7050 / StringUtil.nullDouble(an_output));
					ma_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(an_ma_depr));
				    }
				    if (!"".equals(StringUtil.checkNull(pl_ma_depr[j][case1][case2]))) {
					ma_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_ma_depr[j][case1][case2]));
				    }

				    // 타발유
				    tabalu[j][case1][case2] = String.format("%.4f",
					    StringUtil.nullDouble(st_tabalu) / StringUtil.nullDouble(output[j][case1][case2]));
				    if ("변색방지".equals(plating_type[j][case1][case2])) {// 도금 후처리란에 변색방지선택시 타발유값에 0.10(고정값) 추가 11.12.02
					tabalu[j][case1][case2] = Double.toString(StringUtil.nullDouble(tabalu[j][case1][case2]) + 0.1);
				    }
				    if (!"".equals(StringUtil.checkNull(pl_tabalu[j][case1][case2]))) {
					tabalu[j][case1][case2] = Double.toString(StringUtil.nullDouble(tabalu[j][case1][case2])
					        + StringUtil.nullDouble(pl_tabalu[j][case1][case2]));
				    }

				    // 금형유지비
				    if ("make_4".equals(make[j][case1][case2])) {
					m_upkeep[j][case1][case2] = "0";
				    } else {
					m_upkeep[j][case1][case2] = "0.3";
				    }

				    if ("변색방지".equals(plating_type[j][case1][case2])) {// 도금 후처리란에 변색방지선택시 금형유지비에 0.20(고정값) 추가 11.12.02
					m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2]) + 0.2);
				    }
				    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
					m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
				    }

				    // 포장비임의값
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = String.format("%.2f",
					        StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    } else {
					if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
					        && !"".equals(StringUtil.checkNull(output[j][case1][case2]))) {
					    if ("Reel".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((1072 / StringUtil
						        .nullDouble(in_pack[j][case1][case2]))
						        + (1277 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("Tray".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((485 / StringUtil
						        .nullDouble(in_pack[j][case1][case2]))
						        + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
						        .nullDouble(out_pack[j][case1][case2]));
					    }
					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = String.format("%.2f",
						        StringUtil.nullDouble(pack_cost[j][case1][case2]));
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
						        .nullDouble(out_pack[j][case1][case2]));
					    }
					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = String.format("%.2f",
						        StringUtil.nullDouble(pack_cost[j][case1][case2]));
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else {
					    pack_cost[j][case1][case2] = "0";
					}
				    }
				    // 직접경비
				    if (!"재료도금".equals(plating_type[j][case1][case2])
					    && !"".equals(StringUtil.checkNull(plating_cost[j][case1][case2]))) {
					total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(tabalu[j][case1][case2])
					        + StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pack_cost[j][case1][case2])
					        + StringUtil.nullDouble(plating_cost[j][case1][case2]));
				    } else {
					total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(tabalu[j][case1][case2])
					        + StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
					total_expense[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(total_expense[j][case1][case2])
					        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
				    } else {
					total_expense[j][case1][case2] = total_expense[j][case1][case2];
				    }

				    /*
		                     * //System.out.println("::jun_cost : "+jun_cost[j][case1][case2]);
		                     * //System.out.println("::ma_depr : "+ma_depr[j][case1][case2]);
		                     * //System.out.println("::tabalu : "+tabalu[j][case1][case2]);
		                     * //System.out.println("::m_upkeep : "+m_upkeep[j][case1][case2]);
		                     * //System.out.println("::pack_cost : "+pack_cost[j][case1][case2]);
		                     * //System.out.println("::pl_total_expense : "+pl_total_expense[j][case1][case2]);
		                     */

				    // 간접경비
				    overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
					    * StringUtil.nullDouble(st_overhead));

				    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
					overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
					        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
				    }
				    // 기타경비
				    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
					gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
				    } else {
					gita_cost[j][case1][case2] = "0";
				    }
				    // System.out.println("total_expense[j][case1][case2] : " + total_expense[j][case1][case2]);
				    // System.out.println("overhead[j][case1][case2] : " + overhead[j][case1][case2]);
				    // System.out.println("gita_cost[j][case1][case2] : " + gita_cost[j][case1][case2]);
				    // System.out.println("usage[j][case1][case2] : " + usage[j][case1][case2]);
				    // System.out.println("pl_common_cost[j][case1][case2] : " + pl_common_cost[j][case1][case2]);

				    // 제조경비 plating_cost -> 도금비 3065
				    common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
					    + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil
					        .nullDouble(gita_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
					common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
				    }

				    /*********************************/
				    /************* 금형벌수 *************/
				    /*********************************/
				    mold_su_1 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    mold_su_2 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    mold_su_3 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    mold_su_4 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
					mold_su_5 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    }
				    if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
					mold_su_6 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    }
				    if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
					mold_su_7 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    }
				    if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
					mold_su_8 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
				    }

				    if (StringUtil.nullDouble(mold_su_1) > StringUtil.nullDouble(mold_su_2)) {
					mold_su = mold_su_1;
				    } else {
					mold_su = mold_su_2;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_3;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_3;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_4)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_4;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_5)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_5;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_6)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_6;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_7)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_7;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_8)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_8;
				    }

				    if (StringUtil.nullDouble(mold_su) == 0 || StringUtil.nullDouble(mold_su) < 0) {
					m_su[j][case1][case2] = "1";
				    } else {
					// m_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(mold_su) + 1);
					// 아래 산출식에서 m_su를 int로 형변환하여 사용하므로 기존 double이 아닌 int로 변경 어차피 소수점 버림을 하고있으므로 무리가없음 2013.09.24 황정태
					m_su[j][case1][case2] = Integer.toString((int) (StringUtil.nullDouble(mold_su) + 1));
				    }

				    /*********************************/
				    /************** 감가비 **************/
				    /*********************************/
				    // 금형감가비
				    // 기준수량
				    if ("개조".equals(part_type_1[j][case1][case2])) {
					// 일반감가기준(50000000)
					// s_avg_su[j][case1][case2] = Double.toString(50000000 *
					// StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(m_su[j][case1][case2]));
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
					        * Integer.parseInt(m_su[j][case1][case2]));

					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1)) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					}

					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer
					        .parseInt(cavity[j][case1][case2]) * Integer.parseInt(m_su[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    } else if ("공용".equals(part_type_1[j][case1][case2])) {

					// System.out.println("cavity : "+cavity[j][case1][case2]);
					// System.out.println("m_su"+m_su[j][case1][case2]);

					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
					        * Integer.parseInt(m_su[j][case1][case2]));
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "일반";
					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					    mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
					} else {
					    mold_cost[j][case1][case2] = "2500";
					}
				    } else {
					// 일반감가기준(50000000)
					s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
					        * Integer.parseInt(m_su[j][case1][case2]));
					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					}
					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer
					        .parseInt(cavity[j][case1][case2]) * Integer.parseInt(m_su[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    }

				    if ("감가".equals(mold_c_type[j][case1][case2])) { // 금형비의 경우 지급조건이 감가일때만 계산
					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					    // 일반감가기준(50000000)
					    s_depr_cost[j][case1][case2] = Double
						    .toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
						            * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
						            / StringUtil.nullDouble(s_avg_su[j][case1][case2])
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					    // 판매량감가기준(6년치총수량)

					    // System.out.println("mold_cost[j][case1][case2]  : "+mold_cost[j][case1][case2] );
					    // System.out.println("m_su[j][case1][case2]  : "+m_su[j][case1][case2] );
					    // System.out.println("avg_su[j][case1][case2]  : "+avg_su[j][case1][case2] );
					    // System.out.println("usage[j][case1][case2]  : "+usage[j][case1][case2] );
					    g_depr_cost[j][case1][case2] = Double
						    .toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
						            * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
						            / StringUtil.nullDouble(avg_su[j][case1][case2])
						            * StringUtil.nullDouble(usage[j][case1][case2]));

					}
				    } else {
					s_depr_cost[j][case1][case2] = "0";
					g_depr_cost[j][case1][case2] = "0";
				    }

				    // 기타투자비감가
				    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
					etc_su = "0";
					etc_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					// System.out.println("a_su_year_1 ==>"+a_su_year_1[j]);
					etc_depr[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
				    } else {
					etc_depr[j][case1][case2] = "0";
				    }

				    // 벤도리아일경우(사내제작)
				    if ("make_4".equals(make[j][case1][case2])) {
					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + ((StringUtil.nullDouble("94500") / StringUtil.nullDouble("432000")) * StringUtil
					                .nullDouble(usage[j][case1][case2])));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + ((StringUtil.nullDouble("94500") / StringUtil.nullDouble("432000")) * StringUtil
					                .nullDouble(usage[j][case1][case2])));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
					if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
					    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
						    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
					} else {
					    etc_depr[j][case1][case2] = pl_etc_depr[j][case1][case2];
					}
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				    }

				    // YZK감가비 20101223 // 필드 yazaki_ro → 포장용투자비로대체/산출식 미정
				    // 포장용투자비로 항목 변경에 따른 감가비추가 : 20110526
				    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
					yzk_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					yzk_depr[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(yzk_su) * StringUtil.nullDouble(usage[j][case1][case2])));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
					yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				    }

				    /*********************************/
				    /************** 물류비 **************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
					dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
				    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
					    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
					if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
					} else if ("생산4".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
					}

					dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
					        / StringUtil.nullDouble(out_pack[j][case1][case2]));
					dis_cost[j][case1][case2] = String.format("%.3f", StringUtil.nullDouble(dis_cost[j][case1][case2]));
				    } else {
					dis_cost[j][case1][case2] = "0";
				    }
				    /*********************************/
				    /************** 관리비 **************/
				    /*********************************/
				    // 가공비 3326
				    process_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2]));
				    if (!"ok".equals(assy_in[j]) && "중국".equals(pro_1[j][case1][case2])) {
					tariff[j][case1][case2] = Double
					        .toString(StringUtil.nullDouble(process_cost[j][case1][case2]) * 0.08);
				    } else {
					tariff[j][case1][case2] = "0";
				    }

				    // 재료관리비
				    jae_cost[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(m_total_cost[j][case1][case2]) * StringUtil.nullDouble(st_jae_cost)));
				    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
					jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
				    }

				    // 일반관리비
				    ge_cost[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(process_cost[j][case1][case2]) * StringUtil.nullDouble(st_ge_cost)));
				    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
					ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
				    }
				    // System.out.println(">>>>m_total_cost !! >> "+m_total_cost[j][case1][case2]);
				    // System.out.println(">>>>usage !! >> "+usage[j][case1][case2]);
				    // System.out.println(">>>>process_cost !! >> "+process_cost[j][case1][case2]);
				    // System.out.println(">>>>jae_cost !! >> "+jae_cost[j][case1][case2]);
				    // System.out.println(">>>>st_rnd_cost !! >> "+st_rnd_cost);
				    // System.out.println(">>>>g_depr_cost !! >> "+g_depr_cost[j][case1][case2]);
				    // System.out.println(">>>>ge_cost !! >> "+ge_cost[j][case1][case2]);
				    // R&D비
				    if (!"OK".equals(a_pro_type)) {
					s_rnd_cost[j][case1][case2] = Double.toString(((StringUtil
					        .nullDouble(m_total_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(process_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])) + (StringUtil
					        .nullDouble(s_depr_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
					        * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));
					g_rnd_cost[j][case1][case2] = Double.toString(((StringUtil
					        .nullDouble(m_total_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(process_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					        + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])) + (StringUtil
					        .nullDouble(g_depr_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
					        * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));
				    }
				    if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
					s_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
					g_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
				    }

				    // 총관리비
				    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					    + StringUtil.nullDouble(ge_cost[j][case1][case2])
					    + StringUtil.nullDouble(dis_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
					    + StringUtil.nullDouble(tariff[j][case1][case2]));

				    // 품질비용
				    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
					ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
					        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
				    } else {
					qu_cost[j][case1][case2] = "0";
				    }

				    /*********************************/
				    /************** 총원가 **************/
				    /*********************************/
				    s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2])
					    + StringUtil.nullDouble(s_depr_cost[j][case1][case2]));
				    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_depr_cost[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
					s_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
					g_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				    }

				    /*
		                     * //System.out.println("m_total_cost : "+m_total_cost[j][case1][case2]);
		                     * //System.out.println("labor_cost : "+labor_cost[j][case1][case2]);
		                     * //System.out.println("common_cost : "+common_cost[j][case1][case2]);
		                     * //System.out.println("ad_cost : "+ad_cost[j][case1][case2]);
		                     * //System.out.println("g_depr_cost : "+g_depr_cost[j][case1][case2]);
		                     * //System.out.println("pl_actual_cost : "+pl_actual_cost[j][case1][case2]);
		                     * //System.out.println("g_actual_cost : "+g_actual_cost[j][case1][case2]);
		                     */

				    /*********************************/
				    /************** 로열티 **************/
				    /*********************************/
				    if (!"ok".equals(StringUtil.checkNull(assy_in[j]))) {
					if ("1".equals(royalty[j])) {
					    s_roy_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(m_total_cost[j][case1][case2])
						    + StringUtil.nullDouble(labor_cost[j][case1][case2])
						    + StringUtil.nullDouble(common_cost[j][case1][case2])
						    + StringUtil.nullDouble(jae_cost[j][case1][case2])
						    + StringUtil.nullDouble(ge_cost[j][case1][case2])
						    + StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
						    + StringUtil.nullDouble(tariff[j][case1][case2])
						    + StringUtil.nullDouble(s_depr_cost[j][case1][case2]) + StringUtil
						        .nullDouble(qu_cost[j][case1][case2]))
						    * StringUtil.nullDouble(st_royalty));
					    g_roy_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(m_total_cost[j][case1][case2])
						    + StringUtil.nullDouble(labor_cost[j][case1][case2])
						    + StringUtil.nullDouble(common_cost[j][case1][case2])
						    + StringUtil.nullDouble(jae_cost[j][case1][case2])
						    + StringUtil.nullDouble(ge_cost[j][case1][case2])
						    + StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
						    + StringUtil.nullDouble(tariff[j][case1][case2])
						    + StringUtil.nullDouble(g_depr_cost[j][case1][case2]) + StringUtil
						        .nullDouble(qu_cost[j][case1][case2]))
						    * StringUtil.nullDouble(st_royalty));

					    if (!"".equals(StringUtil.checkNull(pl_royalty_cost[j][case1][case2]))) {
						s_roy_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(s_roy_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
						g_roy_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(g_roy_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
					    } else {
						s_roy_cost[j][case1][case2] = s_roy_cost[j][case1][case2];
						g_roy_cost[j][case1][case2] = g_roy_cost[j][case1][case2];
					    }

					    s_actual_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2])
						    + StringUtil.nullDouble(s_roy_cost[j][case1][case2]));
					    g_actual_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2])
						    + StringUtil.nullDouble(g_roy_cost[j][case1][case2]));

					    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
						    + StringUtil.nullDouble(g_roy_cost[j][case1][case2]));

					}
				    }
				    // System.out.println("ad_cost >>>"+part_name[j][case1][case2]+">>>>  " +ad_cost[j][case1][case2] );

				    /*********************************/
				    /************** 수익률 **************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
					s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
					g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
				    } else {
					if ("0".equals(target_cost[j])) {
					    s_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7));
					    g_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
					} else {
					    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
					    s_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(s_actual_cost[j][case1][case2]))
						            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
						                    .nullDouble(target_rate))));
					    g_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(g_actual_cost[j][case1][case2]))
						            / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						                    .nullDouble(target_rate))));
					}
				    }
				} else if ("HSG".equals(pro_type[j][case1][case2]) || "HSG-Box".equals(pro_type[j][case1][case2])) { // 수지
				    if ("생산2".equals(pro_1[j][case1][case2])) {
					if (StringUtil.nullDouble(ton[j][case1][case2]) > 39
					        && StringUtil.nullDouble(ton[j][case1][case2]) < 80) {
					    ton_value = "39"; // 79
					} else if (StringUtil.nullDouble(ton[j][case1][case2]) < 150) {
					    ton_value = "79"; // 149
					} else {
					    ton_value = "80"; // 150
					}
				    } else if ("중국".equals(pro_1[j][case1][case2])) {
					if (StringUtil.nullDouble(ton[j][case1][case2]) < 150) {
					    ton_value = "149";
					} else {
					    ton_value = "150";
					}
				    }

				    // 3446
				    ArrayList accExStandardList = null;
				    try {
					conn = DBUtil.getConnection();
					costAccDao = new CostAccDao(conn);
					accExStandardList = costAccDao.getAccExStandardList(pro_1[j][case1][case2],
					        pro_type[j][case1][case2], ton_value, "2");
				    } catch (Exception e) {
					e.printStackTrace();
				    } finally {
					DBUtil.close(conn);
				    }
				    Hashtable accExStandardMap = null;
				    accExStandardMap = (Hashtable) accExStandardList.get(0);
				    st_eff_value_in = (String) accExStandardMap.get("st_eff_value"); // 효율
				    st_person_in = (String) accExStandardMap.get("st_person"); // 표준인원
				    st_rate = (String) accExStandardMap.get("st_rate"); // 임율
				    st_jun_cost = (String) accExStandardMap.get("st_jun_cost"); // 전력비
				    st_ma_depr = (String) accExStandardMap.get("st_ma_depr"); // 기계감가
				    st_tabalu = (String) accExStandardMap.get("st_tabalu"); // 타발유
				    st_overhead = (String) accExStandardMap.get("st_overhead"); // 간접경비
				    st_ge_cost = (String) accExStandardMap.get("st_ge_cost"); // 일반관리비율
				    st_jae_cost = (String) accExStandardMap.get("st_jae_cost"); // 재료관리비율
				    st_rnd_cost = (String) accExStandardMap.get("st_rnd_cost"); // R&D비율
				    st_royalty = (String) accExStandardMap.get("st_royalty"); // 로얄티비율

				    st_eff_value[j][case1][case2] = st_eff_value_in;// 효율
				    st_person[j][case1][case2] = st_person_in;// 표준인원

				    if ("기타".equals(pro_level[j][case1][case2])) { // 작업인원
					st_person_in = pro_level_txt[j][case1][case2];
				    } else if (!"".equals(StringUtil.checkNull(st_person_in))) {
					st_person_in = Double.toString(1 / Double.parseDouble(st_person_in));
				    } else {
					st_person_in = "1";
				    }
				    /*********************************/
				    /*************** 재료비 *************/
				    /*********************************/
				    if ("중국".equals(pro_1[j][case1][case2])) {
					unitcost_si[j][case1][case2] = Double
					        .toString(StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 1.05);
				    }
				    scrap_cost[j][case1][case2] = "0";
				    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				    }
				    if (!"".equals(StringUtil.checkNull(recycle[j][case1][case2])) && !"0".equals(recycle[j][case1][case2])) {
					rec_weight = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					        * (StringUtil.nullDouble(recycle[j][case1][case2]) / 100));
					if (StringUtil.nullDouble(rec_weight) < StringUtil.nullDouble(String.format("%.4f",
					        StringUtil.nullDouble(h_scrap[j][case1][case2])))) {
					    meterial_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(t_weight[j][case1][case2]) - StringUtil.nullDouble(rec_weight))
						    * StringUtil.nullDouble(unitcost_si[j][case1][case2])); // 원재료비(재생반영)
					} else {
					    meterial_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(h_weight[j][case1][case2])
						    * StringUtil.nullDouble(unitcost_si[j][case1][case2])); // 원재료비(재생반영)
					}
				    } else {
					meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					        * StringUtil.nullDouble(unitcost_si[j][case1][case2])); // 원재료비
					recycle[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(meterial_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				    }

				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.03); // loss비
				    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				    }

				    // 총재료비
				    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					        .nullDouble(scrap_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));

				    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
					m_total_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(m_total_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));

				    }

				    /*********************************/
				    /*************** 노무비 *************/
				    /*********************************/
				    if (!"생산2".equals(pro_1[j][case1][case2]) && !"생산3".equals(pro_1[j][case1][case2])
					    && !"중국".equals(pro_1[j][case1][case2])) {
					st_eff_value_in = "1";
					st_rate = "0";
					st_person_in = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(eff_value[j][case1][case2]))) {
					st_eff_value_in = eff_value[j][case1][case2];
					st_eff_value[j][case1][case2] = eff_value[j][case1][case2];
				    }

				    // 시간당생산량
				    output[j][case1][case2] = Double.toString(3600 / StringUtil.nullDouble(spm[j][case1][case2])
					    * StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in));
				    if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
					output[j][case1][case2] = Double.toString(StringUtil.nullDouble(output[j][case1][case2])
					        + StringUtil.nullDouble(pl_output[j][case1][case2]));
				    }

				    // 임율
				    rate[j][case1][case2] = Double.toString(StringUtil.nullDouble(st_rate)
					    * StringUtil.nullDouble(st_person_in));
				    if (!"".equals(StringUtil.checkNull(pl_rate[j][case1][case2]))) {
					rate[j][case1][case2] = Double.toString(StringUtil.nullDouble(rate[j][case1][case2])
					        + StringUtil.nullDouble(pl_rate[j][case1][case2]));
				    }

				    if ("검사".equals(type_1[j][case1][case2]) || "포장".equals(type_1[j][case1][case2])) {
					// 노무비
					labor_cost[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
					                .nullDouble(output[j][case1][case2])) + (StringUtil.nullDouble(st_rate) / (3600 / StringUtil
					                .nullDouble(type_cost[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in))))
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    } else {
					// 노무비
					labor_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
					                .nullDouble(output[j][case1][case2]))
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    }
				    // System.out.println("cost acc() 노무비 6 : "+labor_cost);
				    if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
					labor_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
				    }
				    // System.out.println("cost acc() 노무비 7 : "+labor_cost);
				    /*********************************/
				    /************* 제조경비 *************/
				    /*********************************/
				    // 전력비
				    jun_cost[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(st_jun_cost) / StringUtil.nullDouble(output[j][case1][case2])));
				    if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
					jun_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
				    }

				    // 기계감가
				    ma_depr[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(st_ma_depr) / StringUtil.nullDouble(output[j][case1][case2])));
				    if (!"".equals(StringUtil.checkNull(pl_ma_depr[j][case1][case2]))) {
					ma_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(ma_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_ma_depr[j][case1][case2]));
				    }

				    // 금형유지비
				    if (StringUtil.nullDouble(cavity[j][case1][case2]) < 5) {
					m_upkeep[j][case1][case2] = "1.5";
				    } else if (StringUtil.nullDouble(cavity[j][case1][case2]) < 9) {
					m_upkeep[j][case1][case2] = "1";
				    } else if (StringUtil.nullDouble(cavity[j][case1][case2]) > 15) {
					m_upkeep[j][case1][case2] = "0.5";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
					m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
					        + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
				    }

				    // 포장비임의값
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = String.format("%.2f",
					        (StringUtil.nullDouble(pack_cost[j][case1][case2])));
				    } else {
					if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
					        && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("비닐".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((206 / StringUtil
						        .nullDouble(in_pack[j][case1][case2]))
						        + (764 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("Tray".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString((485 / StringUtil
						        .nullDouble(in_pack[j][case1][case2]))
						        + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
					    } else if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
						        .nullDouble(out_pack[j][case1][case2]));
					    }

					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = String.format("%.2f",
						        (StringUtil.nullDouble(pack_cost[j][case1][case2])));
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
					    if ("골판지".equals(pack_type[j][case1][case2])) {
						pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
						        .nullDouble(out_pack[j][case1][case2]));
					    }
					    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
						pack_cost[j][case1][case2] = String.format("%.2f",
						        (StringUtil.nullDouble(pack_cost[j][case1][case2])));
					    } else {
						pack_cost[j][case1][case2] = "0";
					    }
					} else {
					    pack_cost[j][case1][case2] = "0";
					}
				    }

				    // 직접경비
				    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
					    + StringUtil.nullDouble(ma_depr[j][case1][case2])
					    + StringUtil.nullDouble(m_upkeep[j][case1][case2])
					    + StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
					total_expense[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(total_expense[j][case1][case2])
					        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
				    } else {
					total_expense[j][case1][case2] = total_expense[j][case1][case2];
				    }
				    // 간접경비
				    overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
					    * StringUtil.nullDouble(st_overhead));
				    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
					overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
					        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
				    }

				    // 기타경비
				    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
					gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
				    } else {
					gita_cost[j][case1][case2] = "0";
				    }

				    // 제조경비
				    common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
					    + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil
					        .nullDouble(gita_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
					common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
				    }

				    /*********************************/
				    /************* 금형벌수 *************/
				    /*********************************/
				    mold_su_1 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    mold_su_2 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    mold_su_3 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    mold_su_4 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
					mold_su_5 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    }
				    if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
					mold_su_6 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    }
				    if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
					mold_su_7 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    }
				    if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
					mold_su_8 = Double
					        .toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
					                .nullDouble(output[j][case1][case2]) * 22 * 25)) + 0.9) - 1);
				    }

				    if (StringUtil.nullDouble(mold_su_1) > StringUtil.nullDouble(mold_su_2)) {
					mold_su = mold_su_1;
				    } else {
					mold_su = mold_su_2;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_3;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_3;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_4)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_4;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_5)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_5;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_6)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_6;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_7)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_7;
				    }

				    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_8)) {
					mold_su = mold_su;
				    } else {
					mold_su = mold_su_8;
				    }

				    if (StringUtil.nullDouble(mold_su) == 0 || StringUtil.nullDouble(mold_su) < 0) {
					m_su[j][case1][case2] = "1";
				    } else {
					m_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(mold_su) + 1);
				    }

				    /*********************************/
				    /************** 감가비 **************/
				    /*********************************/
				    // 금형감가비
				    // 기준수량
				    if ("개조".equals(part_type_1[j][case1][case2])) {
					// 일반감가기준(50000000)
					// s_avg_su[j][case1][case2] = Double.toString(1000000 *
					// StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(m_su[j][case1][case2]));
					// s_avg_su[j][case1][case2] = Integer.toString(1000000 * Integer.parseInt(cavity[j][case1][case2])
					// * Integer.parseInt(m_su[j][case1][case2]));
					s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0",
					        cavity[j][case1][case2], m_su[j][case1][case2]));

					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					}

					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (StringUtil.DoubleMultiply("1000000.0",
					        cavity[j][case1][case2], m_su[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    } else if ("공용".equals(part_type_1[j][case1][case2])) {

					// s_avg_su[j][case1][case2] = Double.toString(1000000 *
					// StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(m_su[j][case1][case2]));
					// s_avg_su[j][case1][case2] = Integer.toString(1000000 * Integer.parseInt(cavity[j][case1][case2])
					// * Integer.parseInt(m_su[j][case1][case2]));
					s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0",
					        cavity[j][case1][case2], m_su[j][case1][case2]));
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "일반";
					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {

					    mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
					} else {

					    mold_cost[j][case1][case2] = "3000";
					}
				    } else {
					// 일반감가기준(1000000)
					// s_avg_su[j][case1][case2] = Double.toString(1000000 *
					// StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(m_su[j][case1][case2]));
					// s_avg_su[j][case1][case2] = Integer.toString(1000000 * Integer.parseInt(cavity[j][case1][case2])
					// * Integer.parseInt(m_su[j][case1][case2]));
					s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0",
					        cavity[j][case1][case2], m_su[j][case1][case2]));
					// 판매량감가기준(6년치총수량)
					if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(p_su_year_1[j])
						            + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
						            + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
						            + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
						                .nullDouble(p_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    avg_su[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(a_su_year_1[j])
						            + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
						            + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
						            + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
						                .nullDouble(a_su_year_8[j])) * 1000)
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					}
					if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (StringUtil.DoubleMultiply("1000000.0",
					        cavity[j][case1][case2], m_su[j][case1][case2]))) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "일반";
					} else {
					    if ("일반".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					    } else if ("판매".equals(mold_type[j][case1][case2])) {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    } else {
						avg_su[j][case1][case2] = avg_su[j][case1][case2];
						mold_type[j][case1][case2] = "판매";
					    }
					}
				    }

				    if ("감가".equals(mold_c_type[j][case1][case2])) { // 금형비의 경우 지급조건이 감가일때만 계산

					if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					    // 일반감가기준(1000000)
					    s_depr_cost[j][case1][case2] = Double
						    .toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
						            * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
						            / StringUtil.nullDouble(s_avg_su[j][case1][case2])
						            * StringUtil.nullDouble(usage[j][case1][case2]));
					    // 판매량감가기준(6년치총수량)
					    g_depr_cost[j][case1][case2] = Double
						    .toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
						            * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
						            / StringUtil.nullDouble(avg_su[j][case1][case2])
						            * StringUtil.nullDouble(usage[j][case1][case2]));

					}
				    } else {
					s_depr_cost[j][case1][case2] = "0";
					g_depr_cost[j][case1][case2] = "0";
				    }

				    // 일반감가기준(1000000) 3833
				    if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) {
					if (!"".equals(StringUtil.checkNull(s_depr_cost[j][case1][case2]))) {
					    s_depr_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(s_depr_cost[j][case1][case2])
						    + ((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
						    / StringUtil.nullDouble(s_avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));
					} else {
					    s_depr_cost[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
						    / StringUtil.nullDouble(s_avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));
					}
				    }

				    // 판매량감가기준(6년치총수량)
				    if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) {
					if (!"".equals(StringUtil.checkNull(s_depr_cost[j][case1][case2]))) {
					    g_depr_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(g_depr_cost[j][case1][case2])
						    + ((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
						    / StringUtil.nullDouble(avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));

					} else {
					    g_depr_cost[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
						    / StringUtil.nullDouble(avg_su[j][case1][case2])
						    * StringUtil.nullDouble(usage[j][case1][case2]));

					}
				    }

				    // 기타투자비감가
				    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
					etc_su = "0";
					etc_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					etc_depr[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
					                / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(etc_depr[j][case1][case2]));

				    } else {
					etc_depr[j][case1][case2] = "0";
				    }
				    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
					if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
					    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
						    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
					} else {
					    etc_depr[j][case1][case2] = Double
						    .toString(StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
					}
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				    }

				    // YZK감가비 20101223 // 필드 yazaki_ro → 포장용투자비로대체 / 산출식 미정
				    // 포장용투자비로 항목 변경에 따른 감가비추가 : 20110526
				    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
					yzk_su = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
					yzk_depr[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000) / StringUtil
					                .nullDouble(yzk_su)) * StringUtil.nullDouble(usage[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
					yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
					g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));

					s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				    }

				    /*********************************/
				    /************** 물류비 **************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
					dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
				    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
					    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
					if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
					} else if ("생산4".equals(start_pro[j])) {
					    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
					}
					dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
					        / StringUtil.nullDouble(out_pack[j][case1][case2]));
					dis_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(dis_cost[j][case1][case2]));
				    } else {
					dis_cost[j][case1][case2] = "0";
				    }

				    /*********************************/
				    /************** 관리비 **************/
				    /*********************************/
				    // 가공비
				    process_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2]));

				    // 관세
				    if ("ok".equals(assy_in[j]) && "중국".equals(pro_1[j][case1][case2])) {
					tariff[j][case1][case2] = Double
					        .toString(StringUtil.nullDouble(process_cost[j][case1][case2]) * 0.08);
				    } else {
					tariff[j][case1][case2] = "0";
				    }

				    if (!"".equals(StringUtil.checkNull(pl_tariff[j][case1][case2]))) {
					tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(tariff[j][case1][case2])
					        + StringUtil.nullDouble(pl_tariff[j][case1][case2]));
				    }

				    // 재료관리비
				    jae_cost[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(m_total_cost[j][case1][case2]) * StringUtil.nullDouble(st_jae_cost)));
				    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
					jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
				    }

				    // 일반관리비
				    ge_cost[j][case1][case2] = String.format("%.4f",
					    (StringUtil.nullDouble(process_cost[j][case1][case2]) * StringUtil.nullDouble(st_ge_cost)));
				    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
					ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
				    }

				    // R&D비
				    if (!"OK".equals(a_pro_type)) {
					s_rnd_cost[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					                + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                        .nullDouble(usage[j][case1][case2]))
					                + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                        .nullDouble(usage[j][case1][case2]))
					                + (StringUtil.nullDouble(s_depr_cost[j][case1][case2]) / StringUtil
					                        .nullDouble(usage[j][case1][case2])) + (StringUtil
					                .nullDouble(process_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])))
					                * StringUtil.nullDouble(usage[j][case1][case2])
					                * StringUtil.nullDouble(st_rnd_cost));
					g_rnd_cost[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2]))
					                + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
					                        .nullDouble(usage[j][case1][case2]))
					                + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
					                        .nullDouble(usage[j][case1][case2]))
					                + (StringUtil.nullDouble(g_depr_cost[j][case1][case2]) / StringUtil
					                        .nullDouble(usage[j][case1][case2])) + (StringUtil
					                .nullDouble(process_cost[j][case1][case2]) / StringUtil
					                .nullDouble(usage[j][case1][case2])))
					                * StringUtil.nullDouble(usage[j][case1][case2])
					                * StringUtil.nullDouble(st_rnd_cost));
				    }

				    if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
					s_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
					g_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
				    }
				    // System.out.println("총관리비 세부 jae_cost : "+jae_cost[j][case1][case2]);
				    // System.out.println("총관리비 세부 ge_cost : "+ge_cost[j][case1][case2]);
				    // System.out.println("총관리비 세부 dis_cost : "+dis_cost[j][case1][case2]);
				    // System.out.println("총관리비 세부 g_rnd_cost : "+g_rnd_cost[j][case1][case2]);
				    // System.out.println("총관리비 세부 tariff : "+tariff[j][case1][case2]);
				    // 총관리비
				    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
					    + StringUtil.nullDouble(ge_cost[j][case1][case2])
					    + StringUtil.nullDouble(dis_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
					    + StringUtil.nullDouble(tariff[j][case1][case2]));
				    // 품질비용
				    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
					ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
					        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
				    } else {
					qu_cost[j][case1][case2] = "0";
				    }

				    /*********************************/
				    /*************** 총원가 *************/
				    /*********************************/
				    s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2]));
				    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(labor_cost[j][case1][case2])
					    + StringUtil.nullDouble(common_cost[j][case1][case2])
					    + StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(ad_cost[j][case1][case2]));

				    // System.out.println("m_total_cost : "+j + " "+ part_name[j][case1][case2] + "의 m_total_cost 3 : "+
				    // m_total_cost[j][case1][case2]);
				    // System.out.println("labor_cost : "+j + " "+ part_name[j][case1][case2] + "의 labor_cost 3 : "+
				    // labor_cost[j][case1][case2]);
				    // System.out.println("common_cost : "+j + " "+ part_name[j][case1][case2] + "의 common_cost 3 : "+
				    // common_cost[j][case1][case2]);
				    // System.out.println("g_depr_cost : "+j + " "+ part_name[j][case1][case2] + "의 g_depr_cost 3 : "+
				    // g_depr_cost[j][case1][case2]);
				    // System.out.println("ad_cost : "+j + " "+ part_name[j][case1][case2] + "의 ad_cost 3 : "+
				    // ad_cost[j][case1][case2]);
				    // System.out.println("j번째g_actual_cost : "+j + " "+ part_name[j][case1][case2] +
				    // "의 g_actual_cost 3 : "+ g_actual_cost[j][case1][case2]);

				    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
					s_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
					g_actual_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				    }

				    /*********************************/
				    /*************** 로열티 *************/
				    /*********************************/
				    if (!"ok".equals(assy_in)) {
					if ("1".equals(royalty)) {
					    s_roy_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(m_total_cost[j][case1][case2])
						    + StringUtil.nullDouble(labor_cost[j][case1][case2])
						    + StringUtil.nullDouble(common_cost[j][case1][case2])
						    + StringUtil.nullDouble(jae_cost[j][case1][case2])
						    + StringUtil.nullDouble(ge_cost[j][case1][case2])
						    + StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
						    + StringUtil.nullDouble(tariff[j][case1][case2])
						    + StringUtil.nullDouble(s_depr_cost[j][case1][case2]) + StringUtil
						        .nullDouble(qu_cost[j][case1][case2]))
						    * StringUtil.nullDouble(st_royalty));
					    g_roy_cost[j][case1][case2] = Double.toString((StringUtil
						    .nullDouble(m_total_cost[j][case1][case2])
						    + StringUtil.nullDouble(labor_cost[j][case1][case2])
						    + StringUtil.nullDouble(common_cost[j][case1][case2])
						    + StringUtil.nullDouble(jae_cost[j][case1][case2])
						    + StringUtil.nullDouble(ge_cost[j][case1][case2])
						    + StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
						    + StringUtil.nullDouble(tariff[j][case1][case2])
						    + StringUtil.nullDouble(g_depr_cost[j][case1][case2]) + StringUtil
						        .nullDouble(qu_cost[j][case1][case2]))
						    * StringUtil.nullDouble(st_royalty));
					    if (!"".equals(StringUtil.checkNull(pl_royalty_cost[j][case1][case2]))) {
						s_roy_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(s_roy_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
						g_roy_cost[j][case1][case2] = Double.toString(StringUtil
						        .nullDouble(g_roy_cost[j][case1][case2])
						        + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
					    } else {
						s_roy_cost[j][case1][case2] = s_roy_cost[j][case1][case2];
						g_roy_cost[j][case1][case2] = g_roy_cost[j][case1][case2];
					    }
					    s_actual_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2])
						    + StringUtil.nullDouble(s_roy_cost[j][case1][case2]));
					    g_actual_cost[j][case1][case2] = Double.toString(StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2])
						    + StringUtil.nullDouble(g_roy_cost[j][case1][case2]));
					    // System.out.println("총원가 찍기 5: "+g_actual_cost[j][case1][case2]);
					    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
						    + StringUtil.nullDouble(g_roy_cost[j][case1][case2]));

					}
				    }
				    /*********************************/
				    /*************** 수익률 *************/
				    /*********************************/
				    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
					s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(String.format("%.2f",
					        StringUtil.nullDouble(ket_cost[j]))) - StringUtil
					        .nullDouble(s_actual_cost[j][case1][case2]))
					        / StringUtil.nullDouble(String.format("%.2f", StringUtil.nullDouble(ket_cost[j]))));
					g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(String.format("%.2f",
					        StringUtil.nullDouble(ket_cost[j]))) - StringUtil
					        .nullDouble(g_actual_cost[j][case1][case2]))
					        / StringUtil.nullDouble(String.format("%.2f", StringUtil.nullDouble(ket_cost[j]))));
				    } else {
					if ("0".equals(target_cost[j])) {
					    s_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(s_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7));
					    g_earn_rate[j][case1][case2] = Double.toString(((StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
						    .nullDouble(g_actual_cost[j][case1][case2]))
						    / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
					} else {
					    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
					    s_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(s_actual_cost[j][case1][case2]))
						            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
						                    .nullDouble(target_rate))));
					    g_earn_rate[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						            .nullDouble(target_rate))) - StringUtil
						            .nullDouble(g_actual_cost[j][case1][case2]))
						            / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
						                    .nullDouble(target_rate))));
					}
				    }

				    // System.out.println("** !!! g_earn_rate[j][case1][case2] : "+g_earn_rate[j][case1][case2]);
				}

				// 조립산출 재료비
				if (!"".equals(StringUtil.checkNull(s_assy_m_total_cost[j]))) {
				    s_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(s_assy_m_total_cost[j])
					    + StringUtil.nullDouble(s_actual_cost[j][case1][case2]));
				} else {
				    s_assy_m_total_cost[j] = s_actual_cost[j][case1][case2];
				}
				// System.out.println("g_assy_m_total_cost2 === group ==> "+group_no[j][case1][case2] +" : "+
				// g_actual_cost[j][case1][case2] );
				// System.out.println("g_assy_m_total_cost2 === group ==> "+group_no[j][case1][case2] +" : "+
				// g_assy_m_total_cost[j] );
				if (!"".equals(StringUtil.checkNull(g_assy_m_total_cost[j]))) {
				    g_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(g_assy_m_total_cost[j])
					    + StringUtil.nullDouble(g_actual_cost[j][case1][case2]));
				} else {

				    g_assy_m_total_cost[j] = g_actual_cost[j][case1][case2];
				}
				// System.out.println("g_assy_m_total_cost2 === group ==> "+group_no[j][case1][case2] +" : "+
				// g_assy_m_total_cost[j] );
				// sub조립산출 재료비
				if (case2 > 0) {
				    if (!"".equals(StringUtil.checkNull(s_sub_m_total_cost[j][case1]))) {
					s_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(s_sub_m_total_cost[j][case1])
					        + StringUtil.nullDouble(s_actual_cost[j][case1][case2]));
				    } else {
					s_sub_m_total_cost[j][case1] = s_actual_cost[j][case1][case2];
				    }
				    if (!"".equals(StringUtil.checkNull(g_sub_m_total_cost[j][case1]))) {
					g_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(g_sub_m_total_cost[j][case1])
					        + StringUtil.nullDouble(g_actual_cost[j][case1][case2]));
				    } else {
					g_sub_m_total_cost[j][case1] = g_actual_cost[j][case1][case2];
				    }
				}
			    } else if ("assy".equals(pro_type[j][case1][case2]) || "TML-조립".equals(pro_type[j][0][0])
				    || "Insert".equals(pro_type[j][case1][case2])) {
				Assy_content = "OK";
			    } else if ("sub_assy".equals(pro_type[j][case1][case2]) || "sub_Insert".equals(pro_type[j][case1][case2])
				    || "TML-조립".equals(pro_type[j][case1][case2])) {
				sub_assy_content = "OK";
				case1_k = Integer.toString(case1);
			    }
			} else { // Usage가 0 일때
				 // 재료비
			    m_total_cost[j][case1][case2] = "0";
			    // 노무비
			    labor_cost[j][case1][case2] = "0";
			    // 포장비
			    pack_cost[j][case1][case2] = "0";
			    // 물류비
			    dis_cost[j][case1][case2] = "0";
			    // 금형유지비
			    m_upkeep[j][case1][case2] = "0";
			    // 기계감가
			    ma_depr[j][case1][case2] = "0";
			    // 타발유
			    tabalu[j][case1][case2] = "0";
			    // 직접경비
			    total_expense[j][case1][case2] = "0";
			    // 간접경비
			    overhead[j][case1][case2] = "0";
			    // 제조경비
			    common_cost[j][case1][case2] = "0";
			    // 감가비
			    s_depr_cost[j][case1][case2] = "0";
			    g_depr_cost[j][case1][case2] = "0";
			    // 관세
			    tariff[j][case1][case2] = "0";
			    // 재료관리비
			    jae_cost[j][case1][case2] = "0";
			    // 일반관리비
			    ge_cost[j][case1][case2] = "0";
			    // R&D비
			    s_rnd_cost[j][case1][case2] = "0";
			    g_rnd_cost[j][case1][case2] = "0";
			    // 총관리비
			    ad_cost[j][case1][case2] = "0";
			    // 총원가
			    g_actual_cost[j][case1][case2] = "0";
			    // System.out.println("총원가 찍기 6: "+g_actual_cost[j][case1][case2]);
			    // 수익률
			    g_earn_rate[j][case1][case2] = "0";
			}
		    }// 복합금형 else end

		}// case2 for end

	    }// case1 for end

	}// int j table_row_count for end

	/********* cost sub assy **********/
	// System.out.println("g_actual_cost 시작 3 : "+g_actual_cost[0][0][0]);
	cost_sub_assy_content();
	// System.out.println("g_actual_cost 시작 4 : "+g_actual_cost[0][0][0]);
	/******************************/
	/*********** cost assy ************/
	cost_assy_content();
	// System.out.println("g_actual_cost 시작 5 : "+g_actual_cost[0][0][0]);
	/******************************/
    } // cost_acc end

    /**
     * 함수명 : cost_sub_assy_content 설명 : 원재료 단가 산출 sub assy
     * 
     * @param
     * @return
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 07. 27.
     */
    public void cost_sub_assy_content() throws Exception {

	Connection conn = null;
	CostAccDao costAccDao = null;

	if ("OK".equals(sub_assy_content)) {
	    // System.out.println("assy_content Ok");
	    int case2 = 0;

	    for (int j = 0; j < Integer.parseInt(table_row_count); j++) {
		// System.out.println("여기옵니깜 1....");
		for (int case1 = 1; case1 <= Integer.parseInt(StringUtil.checkNullZero(case_count_1[j])); case1++) {
		    // System.out.println("여기옵니깜 2....>> "+part_name[j][case1][case2]);
		    if ("TML-조립".equals(pro_type[j][case1][case2]) && "복합금형".equals(part_type_1[j][case1][case2])) {

			if ("외주".equals(make_type[j][case1][case2])) {

			    if ("무상".equals(pro_1[j][case1][case2])) {

				/*********************************/
				/************** 재료비 **************/
				/*********************************/
				if ("재료도금".equals(plating_type[j][case1][case2])) {
				    unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					    + (StringUtil.nullDouble(plating_cost[j][case1][case2]) / 1000));
				} else {
				    unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
				}// plating_type if end

				if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
				    // 원재료비(재생반영)
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					    * StringUtil.nullDouble(t_weight[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(meterial_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				    }

				    // 스크랩판매비(재생반영)
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
					    * StringUtil.nullDouble(scrap_cost[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				    }

				    // loss비
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					    * StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.02);
				    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				    }

				    // 총재료비
				    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(loss[j][case1][case2]) + StringUtil
					        .nullDouble(scrap_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));

				} else {
				    // 원재료비
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
					    * StringUtil.nullDouble(t_weight[j][case1][case2]));
				    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
					meterial_cost[j][case1][case2] = Double.toString(StringUtil
					        .nullDouble(meterial_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				    }

				    // 스크랩판매비
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
					    * StringUtil.nullDouble(scrap_cost_im));
				    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
					scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				    }

				    // loss비
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02);
				    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
					loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				    }

				    // 총재료비
				    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
					        .nullDouble(scrap_cost[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));

				}// pro_1 if end
			    } else {
				m_total_cost[j][case1][case2] = "0";
			    }// make_type if end

			    if ("선생산".equals(mix_group_1[j][case1][1])) {
				m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(g_actual_cost[j][case1][1]));

			    } else {
				m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(m_total_cost[j][case1][1]));

			    }

			    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));

			    } else {
				m_total_cost[j][case1][case2] = m_total_cost[j][case1][case2];

			    }

			    /*********************************/
			    /************ 제조경비 **************/
			    /*********************************/
			    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
				pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
			    } else {
				if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
				        && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				    if ("Reel".equals(pack_type[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double.toString((1072 / StringUtil
					        .nullDouble(in_pack[j][case1][case2]))
					        + (1277 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				    } else if ("Tray".equals(pack_type[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double.toString(485 / StringUtil.nullDouble(in_pack[j][case1][case2])
					        + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				    } else if ("골판지".equals(pack_cost[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
					        .nullDouble(out_pack[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = String.format("%,2f",
					        StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    } else {
					pack_cost[j][case1][case2] = "0";
				    }
				} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				    if ("골판지".equals(pack_type[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
					        .nullDouble(out_pack[j][case1][case2]));
				    }
				    if (!"".equals(StringUtil.checkNull(pack_type[j][case1][case2]))) {
					pack_cost[j][case1][case2] = String.format("%,2f",
					        StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    } else {
					pack_cost[j][case1][case2] = "0";
				    }
				} else {
				    pack_cost[j][case1][case2] = "0";
				}
			    }
			    // 금형유지비
			    m_upkeep[j][case1][case2] = "0.3";
			    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
				m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
				        + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
			    }

			    // 직접경비
			    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
				    + StringUtil.nullDouble(pack_cost[j][case1][case2]) + StringUtil.nullDouble(m_upkeep[j][case1][case2]));

			    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
				total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
				        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
			    } else {
				total_expense[j][case1][case2] = total_expense[j][case1][case2];
			    }

			    // 간접경비
			    overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2]) * 0.2);
			    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
				overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
				        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
			    }

			    // 기타경비
			    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
				gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
			    } else {
				gita_cost[j][case1][case2] = "0";
			    }

			    // 제조경비
			    common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
				    + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil.nullDouble(gita_cost[j][case1][case2]))
				    * StringUtil.nullDouble(usage[j][case1][case2]));
			    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
				common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
			    }
			    // System.out.println(">>> common_cost Value : "+part_name[j][case1][case2]+">>>"+common_cost[j][case1][case2]);
			    /*********************************/
			    /************ 감가비 ****************/
			    /*********************************/
			    // 금형감가비
			    // 기준수량
			    if ("개조".equals(part_type_1[j][case1][case2])) {
				// 일반감가기준(50000000)
				// s_avg_su[j][case1][case2] = Double.toString(50000000 * StringUtil.nullDouble(cavity[j][case1][case2]));
				s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));

				// 판매량감가기준(6년치총수량)
				if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
					    + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
					    + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
					    + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
					        .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				} else {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
					    + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
					    + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
					    + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
					        .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				}

				if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer.parseInt(cavity[j][case1][case2]))) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "일반";
				} else {
				    if ("일반".equals(mold_type[j][case1][case2])) {
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    } else if ("판매".equals(mold_type)) {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    } else {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "판매";
				    }
				}
			    } else if ("공용".equals(part_type_1[j][case1][case2])) {
				s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
				if (!"".equals(StringUtil.checkNull(mold_type[j][case1][case2]))) {
				    mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
				} else {
				    mold_cost[j][case1][case2] = "2500";
				}
			    } else {
				// 일반감가기준(50000000)
				s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));

				// 판매량감가기준(6년치총수량)
				if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
					    + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
					    + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
					    + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
					        .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				} else {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
					    + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
					    + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
					    + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
					        .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				}

				if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer.parseInt(cavity[j][case1][case2]))) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "일반";
				} else {
				    if ("일반".equals(mold_type[j][case1][case2])) {
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    } else if ("판매".equals(mold_type)) {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    } else {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "판매";
				    }
				}
			    }// part_type_1 if end

			    if ("감가".equals(mold_c_type[j][case1][case2])) {
				// 일반감가기준
				s_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(s_avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));
				// 판매량감가기준(6년치총수량)
				g_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));
				// System.out.println("감가 g_depr : "+g_depr_cost[j][case1][case2]);
			    } else {
				s_depr_cost[j][case1][case2] = "0";
				g_depr_cost[j][case1][case2] = "0";
			    }

			    // 기타투자감가
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
				etc_su = "0";
				etc_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
				        * StringUtil.nullDouble(usage[j][case1][case2]));
				etc_depr[j][case1][case2] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
			    } else {
				etc_depr[j][case1][case2] = "0";
			    }

			    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
				if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
					    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				} else {
				    etc_depr[j][case1][case2] = etc_depr[j][case1][case2];
				}
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			    } else {
				g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
				s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			    }

			    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
			    } else {
				g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
				s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			    }

			    // YZK감가비 20101223 // 필드 yazaki_ro → 포장용투자비로대체/산출식 미정
			    // 포장용투자비로 항목 변경에 따른 감가비추가 : 20110526
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
				yzk_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
				        * StringUtil.nullDouble(usage[j][case1][case2]));
				yzk_depr[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000) / StringUtil
				                .nullDouble(yzk_su)) * StringUtil.nullDouble(usage[j][case1][case2]));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
				yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
				        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
			    }

			    /*********************************/
			    /************ 관리비 ****************/
			    /*********************************/
			    // 재료관리비
			    jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2]) * 0.011);
			    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
				jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
			    } else {
				jae_cost[j][case1][case2] = jae_cost[j][case1][case2];
			    }

			    // 일반관리비
			    ge_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2]) + StringUtil
				    .nullDouble(overhead[j][case1][case2])) * 0.2);
			    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
				ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
			    } else {
				ge_cost[j][case1][case2] = ge_cost[j][case1][case2];
			    }

			    // R&D비
			    s_rnd_cost[j][case1][case2] = Double
				    .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
				            .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(s_depr_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2])) + (StringUtil
				            .nullDouble(common_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
				            * StringUtil.nullDouble(usage[j][case1][case2]) * 0.024);
			    g_rnd_cost[j][case1][case2] = Double
				    .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
				            .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(g_depr_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2])) + (StringUtil
				            .nullDouble(common_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
				            * StringUtil.nullDouble(usage[j][case1][case2]) * 0.024);

			    if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
				s_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
				g_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
			    } else {
				s_rnd_cost[j][case1][case2] = s_rnd_cost[j][case1][case2];
				g_rnd_cost[j][case1][case2] = g_rnd_cost[j][case1][case2];
			    }

			    // 총관리비
			    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
				    + StringUtil.nullDouble(ge_cost[j][case1][case2]) + StringUtil.nullDouble(g_rnd_cost[j][case1][case2]));

			    // 품질비용
			    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
				ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
				        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
			    } else {
				qu_cost[j][case1][case2] = "0";
			    }

			    /*********************************/
			    /************ 총원가 ****************/
			    /*********************************/
			    s_actual_cost[j][case1][case2] = Double
				    .toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				            + StringUtil.nullDouble(common_cost[j][case1][case2])
				            + StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				            + StringUtil.nullDouble(ad_cost[j][case1][case2]));
			    g_actual_cost[j][case1][case2] = Double
				    .toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				            + StringUtil.nullDouble(common_cost[j][case1][case2])
				            + StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				            + StringUtil.nullDouble(ad_cost[j][case1][case2]));

			    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
				s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_actual_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_actual_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			    } else {
				s_actual_cost[j][case1][case2] = s_actual_cost[j][case1][case2];
				g_actual_cost[j][case1][case2] = g_actual_cost[j][case1][case2];
			    }
			    // System.out.println("총원가 찍기 7: "+g_actual_cost[j][case1][case2]);
			    /*********************************/
			    /************ 수익율 ****************/
			    /*********************************/
			    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
				s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				        .nullDouble(s_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
				g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				        .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
			    } else {
				if ("0".equals(target_cost[j])) {
				    s_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
					            .nullDouble(s_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				    g_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
					            .nullDouble(g_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				} else {
				    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
				    s_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
					            .nullDouble(target_rate))) - StringUtil.nullDouble(s_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				    g_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
					            .nullDouble(target_rate))) - StringUtil.nullDouble(g_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				}
			    }
			} else {
			    // 4524
			    if (StringUtil.nullDouble(ton[j][case1][case2]) > 29 && StringUtil.nullDouble(ton[j][case1][case2]) < 40) {
				ton_value = "39";
			    } else if (StringUtil.nullDouble(ton[j][case1][case2]) < 60) {
				ton_value = "59";
			    } else {
				ton_value = "60";
			    }

			    ArrayList accExStandardList = null;
			    try {
				conn = DBUtil.getConnection();
				costAccDao = new CostAccDao(conn);
				accExStandardList = costAccDao.getAccExStandardList(pro_1[j][case1][case2], pro_type[j][case1][case2],
				        ton_value, "1");
			    } catch (Exception e) {
				e.printStackTrace();
			    } finally {
				DBUtil.close(conn);
			    }
			    Hashtable accExStandardMap = null;
			    accExStandardMap = (Hashtable) accExStandardList.get(0);
			    st_eff_value_in = (String) accExStandardMap.get("st_eff_value"); // 효율
			    st_person_in = (String) accExStandardMap.get("st_person"); // 표준인원
			    st_rate = (String) accExStandardMap.get("st_rate"); // 임율
			    st_jun_cost = (String) accExStandardMap.get("st_jun_cost"); // 전력비
			    st_ma_depr = (String) accExStandardMap.get("st_ma_depr"); // 기계감가
			    st_tabalu = (String) accExStandardMap.get("st_tabalu"); // 타발유
			    st_overhead = (String) accExStandardMap.get("st_overhead"); // 간접경비
			    st_ge_cost = (String) accExStandardMap.get("st_ge_cost"); // 일반관리비율
			    st_jae_cost = (String) accExStandardMap.get("st_jae_cost"); // 재료관리비율
			    st_rnd_cost = (String) accExStandardMap.get("st_rnd_cost"); // R&D비율
			    st_royalty = (String) accExStandardMap.get("st_royalty"); // 로얄티비율

			    st_eff_value[j][case1][case2] = st_eff_value_in;// 효율
			    st_person[j][case1][case2] = st_person_in;// 표준인원

			    if ("기타".equals(pro_level[j][case1][case2])) { // 작업인원
				st_person_in = pro_level_txt[j][case1][case2];
			    } else if (!"".equals(StringUtil.checkNull(st_person_in))) {
				// st_person_in = Integer.toString(1 / Integer.parseInt(st_person_in));
				temp_1 = new BigDecimal("1");
				temp_2 = new BigDecimal(StringUtil.checkNull(st_person_in));
				st_person_in = Double.toString(temp_1.divide(temp_2).doubleValue());
			    } else {
				st_person_in = "1";
			    }
			    /*********************************/
			    /*************** 재료비 *************/
			    /*********************************/
			    if ("재료도금".equals(plating_type[j][case1][case2])) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        + (StringUtil.nullDouble(plating_cost[j][case1][case2])) / 1000);
			    } else {
				unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
			    }
			    // 4590
			    if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
				meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(t_weight[j][case1][case2])); // 원재료비(재생반영)
				if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				}
				scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
				        * StringUtil.nullDouble(scrap_cost[j][case1][case2])); // 스크랩판매비(재생반영)
				if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				}
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
				        * StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.02); // loss비
				if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				}

				m_total_cost[j][case1][case2] = Double
				        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
				                    .nullDouble(scrap_cost[j][case1][case2]))
				                * StringUtil.nullDouble(usage[j][case1][case2])); // 총재료비

			    } else {
				meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(t_weight[j][case1][case2])); // 원재료비
				if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				}
				scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
				        * StringUtil.nullDouble(scrap_cost_im)); // 스크랩판매비 스크랩판매단가 : Kg당4650원
				if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				}
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02); // loss비
				if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				}

				m_total_cost[j][case1][case2] = Double
				        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
				                    .nullDouble(scrap_cost[j][case1][case2]))
				                * StringUtil.nullDouble(usage[j][case1][case2])); // 총재료비

			    }

			    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));

			    }

			    /*********************************/
			    /*************** 노무비 *************/
			    /*********************************/
			    if (!"make_4".equals(make[j][case1][case2]) && !"생산1".equals(pro_1[j][case1][case2])
				    && !"생산3".equals(pro_1[j][case1][case2])) {
				st_eff_value_in = "1";
				st_rate = "0";
				st_person_in = "0";
			    }

			    if (!"".equals(StringUtil.checkNull(eff_value[j][case1][case2]))) {
				st_eff_value_in = eff_value[j][case1][case2];
				st_eff_value[j][case1][case2] = eff_value[j][case1][case2];
			    }

			    output[j][case1][case2] = Double.toString(60 * StringUtil.nullDouble(spm[j][case1][case2])
				    * StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in)); // 시간당생산량
			    if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
				output[j][case1][case2] = Double.toString(StringUtil.nullDouble(output[j][case1][case2])
				        + StringUtil.nullDouble(pl_output[j][case1][case2]));
			    }

			    rate[j][case1][case2] = Double.toString(StringUtil.nullDouble(rate[j][case1][case2])
				    * StringUtil.nullDouble(st_person_in)); // 임율
			    if (!"".equals(StringUtil.checkNull(pl_rate[j][case1][case2]))) {
				rate[j][case1][case2] = Double.toString(StringUtil.nullDouble(rate[j][case1][case2])
				        + StringUtil.nullDouble(pl_rate[j][case1][case2]));
			    }
			    // System.out.println("===== COST_SUB_ASSY_CONTENT() =========");
			    if ("검사".equals(type_1[j][case1][case2]) || "포장".equals(type_1[j][case1][case2])) {
				// 노무비
				labor_cost[j][case1][case2] = Double.toString(((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
				        .nullDouble(output[j][case1][case2])) + (StringUtil.nullDouble(st_rate) / (3600 / StringUtil
				        .nullDouble(type_cost[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in))))
				        * StringUtil.nullDouble(usage[j][case1][case2]));
				// System.out.println("labor_cost 1번  : "+labor_cost[j][case1][case2]);
			    } else {
				// 노무비
				labor_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
				        .nullDouble(output[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));
				// System.out.println("labor_cost 2번  : "+labor_cost[j][case1][case2]);
			    }

			    if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
				labor_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
				// System.out.println("labor_cost 3번  : "+labor_cost[j][case1][case2]);
			    } else {
				labor_cost[j][case1][case2] = labor_cost[j][case1][case2];
				// System.out.println("labor_cost 4번  : "+labor_cost[j][case1][case2]);
			    }
			    // 4681
			    /*********************************/
			    /************* 제조경비 *************/
			    /*********************************/
			    // 전력비
			    jun_cost[j][case1][case2] = String.format("%.4f",
				    StringUtil.nullDouble(st_jun_cost) / StringUtil.nullDouble(output[j][case1][case2]));
			    if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
				jun_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
			    }

			    // 기계감가
			    ma_depr[j][case1][case2] = String.format("%.4f",
				    StringUtil.nullDouble(st_ma_depr) / StringUtil.nullDouble(output[j][case1][case2]));
			    if (!"".equals(StringUtil.checkNull(pl_ma_depr[j][case1][case2]))) {
				ma_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(ma_depr[j][case1][case2])
				        + StringUtil.nullDouble(pl_ma_depr[j][case1][case2]));
			    }

			    // 타발유
			    tabalu[j][case1][case2] = String.format("%.4f",
				    StringUtil.nullDouble(st_tabalu) / StringUtil.nullDouble(output[j][case1][case2]));
			    if (!"".equals(StringUtil.checkNull(pl_tabalu[j][case1][case2]))) {
				tabalu[j][case1][case2] = Double.toString(StringUtil.nullDouble(tabalu[j][case1][case2])
				        + StringUtil.nullDouble(pl_tabalu[j][case1][case2]));
			    }

			    // 금형유지비
			    m_upkeep[j][case1][case2] = "0.3";
			    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
				m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
				        + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
			    }

			    // 포장비임의값
			    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
				pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
			    } else {
				if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
				        && !"".equals(StringUtil.checkNull(output[j][case1][case2]))) {
				    if ("Reel".equals(pack_type[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double.toString((1072 / StringUtil
					        .nullDouble(in_pack[j][case1][case2]))
					        + (1277 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				    } else if ("Tray".equals(pack_type[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double
					        .toString((485 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					                + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				    } else if ("골판지".equals(pack_type[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
					        .nullDouble(out_pack[j][case1][case2]));
				    }
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = String.format("%.2f",
					        StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    } else {
					pack_cost[j][case1][case2] = "0";
				    }
				} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				    if ("골판지".equals(pack_type[j][case1][case2])) {
					pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil
					        .nullDouble(out_pack[j][case1][case2]));
				    }
				    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
					pack_cost[j][case1][case2] = String.format("%.2f",
					        StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    } else {
					pack_cost[j][case1][case2] = "0";
				    }
				} else {
				    pack_cost[j][case1][case2] = "0";
				}
			    }// 포장비임의값 if end

			    // 4752
			    // 직접경비
			    if (!"재료도금".equals(plating_type[j][case1][case2])
				    && !"".equals(StringUtil.checkNull(plating_cost[j][case1][case2]))) {
				total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
				        + StringUtil.nullDouble(ma_depr[j][case1][case2]) + StringUtil.nullDouble(tabalu[j][case1][case2])
				        + StringUtil.nullDouble(m_upkeep[j][case1][case2])
				        + StringUtil.nullDouble(pack_cost[j][case1][case2])
				        + StringUtil.nullDouble(plating_cost[j][case1][case2]));
			    } else {
				total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
				        + StringUtil.nullDouble(ma_depr[j][case1][case2]) + StringUtil.nullDouble(tabalu[j][case1][case2])
				        + StringUtil.nullDouble(m_upkeep[j][case1][case2])
				        + StringUtil.nullDouble(pack_cost[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
				total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
				        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
			    } else {
				total_expense[j][case1][case2] = total_expense[j][case1][case2];
			    }

			    // 간접경비
			    overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
				    + StringUtil.nullDouble(st_overhead));
			    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
				overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
				        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
			    }

			    // 기타경비
			    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
				gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
			    } else {
				gita_cost[j][case1][case2] = "0";
			    }

			    // 제조경비 plating_cost -> 도금비
			    common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
				    + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil.nullDouble(gita_cost[j][case1][case2]))
				    * StringUtil.nullDouble(usage[j][case1][case2]));
			    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
				common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
			    }

			    // 4788
			    /*********************************/
			    /************* 금형벌수 *************/
			    /*********************************/
			    mold_su_1 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    mold_su_2 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    mold_su_3 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    mold_su_4 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
				mold_su_5 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
				mold_su_6 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
				mold_su_7 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    }
			    if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
				mold_su_8 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			    }

			    if (StringUtil.nullDouble(mold_su_1) > StringUtil.nullDouble(mold_su_2)) {
				mold_su = mold_su_1;
			    } else {
				mold_su = mold_su_2;
			    }

			    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
				mold_su = mold_su;
			    } else {
				mold_su = mold_su_3;
			    }

			    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
				mold_su = mold_su;
			    } else {
				mold_su = mold_su_3;
			    }

			    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_4)) {
				mold_su = mold_su;
			    } else {
				mold_su = mold_su_4;
			    }

			    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_5)) {
				mold_su = mold_su;
			    } else {
				mold_su = mold_su_5;
			    }

			    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_6)) {
				mold_su = mold_su;
			    } else {
				mold_su = mold_su_6;
			    }

			    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_7)) {
				mold_su = mold_su;
			    } else {
				mold_su = mold_su_7;
			    }

			    if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_8)) {
				mold_su = mold_su;
			    } else {
				mold_su = mold_su_8;
			    }

			    if (StringUtil.nullDouble(mold_su) == 0 || StringUtil.nullDouble(mold_su) < 0) {
				m_su[j][case1][case2] = "1";
			    } else {
				m_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(mold_su) + 1);
			    }

			    // 4859
			    /*********************************/
			    /************** 감가비 **************/
			    /*********************************/
			    // 금형감가비
			    // 기준수량
			    if ("개조".equals(part_type_1[j][case1][case2])) {
				// 일반감가기준(50000000)
				s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
				        * Integer.parseInt(m_su[j][case1][case2]));

				// 판매량감가기준(6년치총수량)
				if ("1".equals(p_su_chk) && !"신규".equals(part_type_1)) {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
					    + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
					    + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
					    + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
					        .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				} else {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
					    + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
					    + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
					    + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
					        .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				}

				if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer.parseInt(cavity[j][case1][case2]) * Integer
				        .parseInt(m_su[j][case1][case2]))) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "일반";
				} else {
				    if ("일반".equals(mold_type[j][case1][case2])) {
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    } else if ("판매".equals(mold_type[j][case1][case2])) {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    } else {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "판매";
				    }
				}
			    } else if ("공용".equals(part_type_1[j][case1][case2])) {
				s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
				        * Integer.parseInt(m_su[j][case1][case2]));
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
				if ("".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				    mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
				} else {
				    mold_cost[j][case1][case2] = "2500";
				}
			    } else {
				// 일반감가기준(50000000)
				s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
				        * Integer.parseInt(m_su[j][case1][case2]));
				// 판매량감가기준(6년치총수량)
				if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
					    + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
					    + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
					    + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
					        .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				} else {
				    avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
					    + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
					    + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
					    + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
					        .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
				}
				if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer.parseInt(cavity[j][case1][case2]) * Integer
				        .parseInt(m_su[j][case1][case2]))) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "일반";
				} else {
				    if ("일반".equals(mold_type[j][case1][case2])) {
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    } else if ("판매".equals(mold_type[j][case1][case2])) {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    } else {
					avg_su[j][case1][case2] = avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "판매";
				    }
				}
			    }

			    // 4948
			    if ("감가".equals(mold_c_type[j][case1][case2])) { // 금형비의 경우 지급조건이 감가일때만 계산
				if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				    // 일반감가기준(50000000)
				    s_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
					    * StringUtil.nullDouble(m_su[j][case1][case2]) / 1000)
					    / StringUtil.nullDouble(s_avg_su[j][case1][case2])
					    * StringUtil.nullDouble(usage[j][case1][case2]));
				    // 판매량감가기준(6년치총수량)
				    g_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
					    * StringUtil.nullDouble(m_su[j][case1][case2]) / 1000)
					    / StringUtil.nullDouble(avg_su[j][case1][case2])
					    * StringUtil.nullDouble(usage[j][case1][case2]));
				}
			    } else {
				s_depr_cost[j][case1][case2] = "0";
				g_depr_cost[j][case1][case2] = "0";
			    }

			    // 기타투자비감가
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
				etc_su = "0";
				etc_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
				        * StringUtil.nullDouble(usage[j][case1][case2]));
				etc_depr[j][case1][case2] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));

				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(etc_depr[j][case1][case2]));
			    } else {
				etc_depr[j][case1][case2] = "0";
			    }

			    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
				if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
					    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				} else {
				    etc_depr[j][case1][case2] = pl_etc_depr[j][case1][case2];
				}
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			    } else {
				g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
				s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			    }

			    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
			    } else {
				g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
				s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			    }

			    // 5000
			    // YZK감가비 20101223 // 필드 yazaki_ro → 포장용투자비로대체/산출식 미정
			    // 포장용투자비로 항목 변경에 따른 감가비추가 : 20110526
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
				yzk_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
				        * StringUtil.nullDouble(usage[j][case1][case2]));
				yzk_depr[j][case1][case2] = Double.toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(yzk_su) * StringUtil.nullDouble(usage[j][case1][case2])));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
				yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
				        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
			    }

			    /*********************************/
			    /************** 물류비 **************/
			    /*********************************/
			    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
				dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
			    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
				    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
				if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
				    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
				} else if ("생산4".equals(start_pro[j])) {
				    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
				}

				dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
				        / StringUtil.nullDouble(out_pack[j][case1][case2]));
				dis_cost[j][case1][case2] = String.format("%.3f", StringUtil.nullDouble(dis_cost[j][case1][case2]));
			    } else {
				dis_cost[j][case1][case2] = "0";
			    }
			    /*********************************/
			    /************** 관리비 **************/
			    /*********************************/
			    // 가공비 5048
			    process_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
				    + StringUtil.nullDouble(common_cost[j][case1][case2]));
			    if (!"ok".equals(assy_in[j]) && "중국".equals(pro_1[j][case1][case2])) {
				tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(process_cost[j][case1][case2]) * 0.08);
			    } else {
				tariff[j][case1][case2] = "0";
			    }

			    if ("".equals(StringUtil.checkNull(mix_group_1[j][Integer.parseInt(case1_k)][1]))) {
				m_total = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(g_actual_cost[j][Integer.parseInt(case1_k)][1]));
			    } else {
				m_total = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(m_total_cost[j][Integer.parseInt(case1_k)][1]));
			    }
			    // 재료관리비
			    jae_cost[j][case1][case2] = String.format("%.4f",
				    (StringUtil.nullDouble(m_total_cost[j][case1][case2]) * StringUtil.nullDouble(st_jae_cost)));
			    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
				jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
			    }

			    // 일반관리비
			    ge_cost[j][case1][case2] = String.format("%.4f",
				    (StringUtil.nullDouble(process_cost[j][case1][case2]) * StringUtil.nullDouble(st_ge_cost)));
			    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
				ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
			    }

			    // R&D비(야자키감가비는 포함하지 말것)
			    s_rnd_cost[j][case1][case2] = Double
				    .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
				            .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(process_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2])) + (StringUtil
				            .nullDouble(s_depr_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
				            * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));
			    g_rnd_cost[j][case1][case2] = Double
				    .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
				            .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(process_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2]))
				            + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil
				                    .nullDouble(usage[j][case1][case2])) + (StringUtil
				            .nullDouble(g_depr_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
				            * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));

			    // 총관리비
			    TML_loss = Double.toString(StringUtil.nullDouble(g_actual_cost[j][Integer.parseInt(case1_k)][1]) * 0.01
				    * StringUtil.nullDouble(usage[j][case1][case2]));
			    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
				    + StringUtil.nullDouble(ge_cost[j][case1][case2]) + StringUtil.nullDouble(dis_cost[j][case1][case2])
				    + StringUtil.nullDouble(g_rnd_cost[j][case1][case2]) + StringUtil.nullDouble(tariff[j][case1][case2])
				    + StringUtil.nullDouble(TML_loss));

			    // 품질비용
			    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
				ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
				        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
			    } else {
				qu_cost[j][case1][case2] = "0";
			    }

			    /*********************************/
			    /************** 총원가 **************/
			    /*********************************/
			    // 5118
			    s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total)
				    + StringUtil.nullDouble(labor_cost[j][case1][case2])
				    + StringUtil.nullDouble(common_cost[j][case1][case2]) + StringUtil.nullDouble(ad_cost[j][case1][case2])
				    + StringUtil.nullDouble(s_depr_cost[j][case1][case2]));
			    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total)
				    + StringUtil.nullDouble(labor_cost[j][case1][case2])
				    + StringUtil.nullDouble(common_cost[j][case1][case2]) + StringUtil.nullDouble(ad_cost[j][case1][case2])
				    + StringUtil.nullDouble(g_depr_cost[j][case1][case2]));
			    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
				s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_actual_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_actual_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			    } else {
				s_actual_cost[j][case1][case2] = s_actual_cost[j][case1][case2];
				g_actual_cost[j][case1][case2] = g_actual_cost[j][case1][case2];
			    }
			    // System.out.println("총원가 찍기 9: "+g_actual_cost[j][case1][case2]);
			    /*********************************/
			    /************** 수익률 **************/
			    /*********************************/
			    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
				s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				        .nullDouble(s_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
				g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				        .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
			    } else {
				if ("0".equals(target_cost[j])) {
				    s_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
					            .nullDouble(s_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7));
				    g_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
					            .nullDouble(g_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
				} else {
				    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
				    s_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
					            .nullDouble(target_rate))) - StringUtil.nullDouble(s_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				    g_earn_rate[j][case1][case2] = Double
					    .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
					            .nullDouble(target_rate))) - StringUtil.nullDouble(g_actual_cost[j][case1][case2]))
					            / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				}
			    }
			}// make_type if end
			 // 5149
			 // 조립산출-재료비
			if (!"".equals(StringUtil.checkNull(s_assy_m_total_cost[j]))) {
			    s_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(s_assy_m_total_cost[j])
				    + StringUtil.nullDouble(s_actual_cost[j][case1][case2]));
			} else {
			    s_assy_m_total_cost[j] = s_actual_cost[j][case1][case2];
			}
			if (!"".equals(StringUtil.checkNull(g_assy_m_total_cost[j]))) {
			    g_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(g_assy_m_total_cost[j])
				    + StringUtil.nullDouble(g_actual_cost[j][case1][case2]));
			} else {
			    g_assy_m_total_cost[j] = g_actual_cost[j][case1][case2];
			}
			// System.out.println("g_assy_m_total_cost3 === group ==> "+group_no[j][case1][case2] +" : "+ g_assy_m_total_cost[j]
			// );
		    } else {
			// 5165
			if (pro_type[j][case1][case2] != null
			        && ("sub_assy".equals(StringUtil.checkNull(pro_type[j][case1][case2])) || "sub_Insert".equals(StringUtil
			                .checkNull(pro_type[j][case1][case2])))) {

			    ArrayList accExStandardList = null;
			    try {
				conn = DBUtil.getConnection();
				costAccDao = new CostAccDao(conn);
				accExStandardList = costAccDao.getAccExStandardList2(pro_1[j][case1][case2], method_type[j][case1][case2]);
			    } catch (Exception e) {
				e.printStackTrace();
			    } finally {
				DBUtil.close(conn);
			    }

			    // System.out.println("에러 3 "+"pro_1 : "+pro_1[j][case1][case2] +"method_type :"+ method_type[j][case1][case2]);
			    Hashtable accExStandardMap = null;
			    if (accExStandardList.size() > 0) {
				accExStandardMap = (Hashtable) accExStandardList.get(0);
				st_eff_value_in = (String) accExStandardMap.get("st_eff_value"); // 효율
				st_person_in = (String) accExStandardMap.get("st_person"); // 표준인원
				st_rate = (String) accExStandardMap.get("st_rate"); // 임율
				st_jun_cost = (String) accExStandardMap.get("st_jun_cost"); // 전력비
				st_ma_depr = (String) accExStandardMap.get("st_ma_depr"); // 기계감가
				st_tabalu = (String) accExStandardMap.get("st_tabalu"); // 타발유
				st_overhead = (String) accExStandardMap.get("st_overhead"); // 간접경비
				st_ge_cost = (String) accExStandardMap.get("st_ge_cost"); // 일반관리비율
				st_jae_cost = (String) accExStandardMap.get("st_jae_cost"); // 재료관리비율
				st_rnd_cost = (String) accExStandardMap.get("st_rnd_cost"); // R&D비율
				st_royalty = (String) accExStandardMap.get("st_royalty"); // 로얄티비율
			    }

			    st_eff_value[j][case1][case2] = st_eff_value_in;// 효율
			    st_person[j][case1][case2] = st_person_in;// 표준인원

			    if ("기타".equals(pro_level[j][case1][case2])) { // 작업인원
				st_person_in = pro_level_txt[j][case1][case2];
			    } else if (!"".equals(StringUtil.checkNull(st_person_in))) {
				// st_person_in = Integer.toString(1 / Integer.parseInt(st_person_in));
				temp_1 = new BigDecimal("1");
				temp_2 = new BigDecimal(StringUtil.checkNull(st_person_in));
				st_person_in = Double.toString(temp_1.divide(temp_2).doubleValue());
			    } else {
				st_person_in = "1";
			    }

			    if (part_name[j][case1][case2].equals("Housing")) {
				// System.out.println("::::::st_eff_value_in>>>>>>>>>> "+st_eff_value_in);
			    }

			    if (!"".equals(StringUtil.checkNull(eff_value[j][case1][case2]))) {
				st_eff_value_in = eff_value[j][case1][case2];
				st_eff_value[j][case1][case2] = eff_value[j][case1][case2];
			    } else {
				eff_value[j][case1][case2] = st_eff_value[j][case1][case2];
			    }

			    if ("".equals(StringUtil.checkNull(make_type[j][case1][case2]))
				    || "".equals(StringUtil.checkNull(type_1[j][case1][case2]))) {
				if (!"".equals(StringUtil.checkNull(type_cost[j][case1][case2]))) {
				    type_cost[j][case1][case2] = type_cost[j][case1][case2];
				} else {
				    type_cost[j][case1][case2] = String.format("%.2f",
					    (StringUtil.nullDouble(st_rate) * StringUtil.nullDouble(st_person_in) / (3600 / StringUtil
					            .nullDouble(spm[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in))));
				}
			    }

			    /*********************************/
			    /************** 재료비 **************/
			    /*********************************/

			    if ("sub_Insert".equals(pro_type[j][case1][case2])) {
				if ("중국".equals(pro_1[j][case1][case2])) {
				    unitcost_si[j][case1][case2] = Double
					    .toString(StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 1.05);
				}
				scrap_cost[j][case1][case2] = "0";
				if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				}

				if (!"".equals(StringUtil.checkNull(recycle[j][case1][case2])) && !"0".equals(recycle[j][case1][case2])) {
				    rec_weight = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					    * (StringUtil.nullDouble(recycle[j][case1][case2]) / 100));
				    if (StringUtil.nullDouble(rec_weight) < StringUtil.nullDouble(String.format("%.4f",
					    StringUtil.nullDouble(h_scrap[j][case1][case2])))) {
					// 원재료비(재생반영)
					meterial_cost[j][case1][case2] = Double
					        .toString((StringUtil.nullDouble(t_weight[j][case1][case2]) - StringUtil
					                .nullDouble(rec_weight)) * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				    } else {
					// 원재료비(재생반영)
					meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_weight[j][case1][case2])
					        * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				    }
				} else {
				    // 원재료비
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
					    * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				    recycle[j][case1][case2] = "0";
				}

				// 5246
				if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				}

				// loss비
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.03);
				if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				}

				// 총재료비
				m_total_cost[j][case1][case2] = Double
				        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
				                    .nullDouble(scrap_cost[j][case1][case2]))
				                * StringUtil.nullDouble(usage[j][case1][case2]));
				if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				}

				// 금형유지비
				if (StringUtil.nullDouble(cavity[j][case1][case2]) < 5) {
				    m_upkeep[j][case1][case2] = "1.5";
				} else if (StringUtil.nullDouble(cavity[j][case1][case2]) < 9) {
				    m_upkeep[j][case1][case2] = "1";
				} else if (StringUtil.nullDouble(cavity[j][case1][case2]) > 15) {
				    m_upkeep[j][case1][case2] = "0.5";
				}
				if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
				    m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
					    + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
				}
			    } else {
				m_total_cost[j][case1][case2] = "0";

				// 금형유지비
				if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
				    m_upkeep[j][case1][case2] = pl_m_upkeep[j][case1][case2];
				}
			    }
			    if (!"sub_Insert".equals(pro_type[j][case1][case2])) {
				if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				    s_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(s_sub_m_total_cost[j][case1])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				    g_sub_m_total_cost[j][case1] = Double.toString(StringUtil.nullDouble(g_sub_m_total_cost[j][case1])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				}
			    }
			    // loss비 일반
			    s_sub_loss[case1] = Double.toString(StringUtil.nullDouble(s_sub_m_total_cost[j][case1])
				    * StringUtil.nullDouble(usage[j][case1][case2]) * 0.01);
			    // loss비 판매
			    g_sub_loss[case1] = Double.toString(StringUtil.nullDouble(g_sub_m_total_cost[j][case1])
				    * StringUtil.nullDouble(usage[j][case1][case2]) * 0.01);

			    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				s_sub_loss[case1] = Double.toString(StringUtil.nullDouble(s_sub_loss[case1])
				        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				g_sub_loss[case1] = Double.toString(StringUtil.nullDouble(g_sub_loss[case1])
				        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
			    }

			    loss[j][case1][case2] = g_sub_loss[case1];

			    sub_scrap_cost[case1] = "0";
			    s_sub_meterial_cost[case1] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(s_sub_m_total_cost[j][case1]) - StringUtil.nullDouble(sub_scrap_cost[case1])
				    + StringUtil.nullDouble(s_sub_loss[case1]));
			    g_sub_meterial_cost[case1] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(g_sub_m_total_cost[j][case1]) - StringUtil.nullDouble(sub_scrap_cost[case1])
				    + StringUtil.nullDouble(g_sub_loss[case1]));

			    if (!"sub_Insert".equals(pro_type[j][case1][case2])) {
				if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				    s_sub_meterial_cost[case1] = Double.toString(StringUtil.nullDouble(s_sub_meterial_cost[case1])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				    g_sub_meterial_cost[case1] = Double.toString(StringUtil.nullDouble(g_sub_meterial_cost[case1])
					    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				} else {
				    s_sub_meterial_cost[case1] = s_sub_meterial_cost[case1];
				    g_sub_meterial_cost[case1] = g_sub_meterial_cost[case1];
				}
			    }
			    meterial_cost[j][case1][case2] = g_sub_meterial_cost[case1];

			    /*********************************/
			    /************** 노무비 **************/
			    /*********************************/
			    if (!"생산2".equals(pro_1[j][case1][case2]) && !"생산3".equals(pro_1[j][case1][case2])
				    && !"중국".equals(pro_1[j][case1][case2]) && !"Insert".equals(method_type[j][case1][case2])) {
				st_eff_value_in = "1";
				st_rate = "0";
				st_person_in = "0";
			    }

			    if ("외주".equals(make_type[j][case1][case2])
				    && (!"Insert".equals(method_type[j][case1][case2]) && !"자동".equals(method_type[j][case1][case2]))) {
				sub_output[case1] = "0"; // 시간당생산량
				if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
				    sub_output[case1] = Double.toString(StringUtil.nullDouble(sub_output[case1])
					    + StringUtil.nullDouble(pl_output[j][case1][case2]));
				}

				sub_rate[case1] = "0"; // 임율
				sub_labor_cost[case1] = "0"; // 노무비
			    } else if ("외주".equals(make_type[j][case1][case2]) && "Insert".equals(method_type[j][case1][case2])) {
				// 시간당생산량
				sub_output[case1] = Double.toString(3600 / StringUtil.nullDouble(spm[j][case1][case2])
				        * StringUtil.nullDouble(st_eff_value_in));
				if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
				    sub_output[case1] = Double.toString(StringUtil.nullDouble(sub_output[case1])
					    + StringUtil.nullDouble(pl_output[j][case1][case2]));
				}

				sub_rate[case1] = "0"; // 임율
				sub_labor_cost[case1] = "0"; // 노무비
			    } else {
				// 5348
				// 시간당생산량
				// System.out.println("spm : "+spm[j][case1][case2]+">>st_eff_value_in :  "+st_eff_value_in+
				// ">>pl_output : "+pl_output[j][case1][case2]+">>st_rate : "+st_rate+">>st_person_in : "+st_person_in+">>pl_labor_cost : "+pl_labor_cost[j][case1][case2]);
				sub_output[case1] = Double.toString(3600 / StringUtil.nullDouble(spm[j][case1][case2])
				        * StringUtil.nullDouble(st_eff_value_in));
				if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
				    sub_output[case1] = Double.toString(StringUtil.nullDouble(sub_output[case1])
					    + StringUtil.nullDouble(pl_output[j][case1][case2]));
				}
				// 임율
				sub_rate[case1] = Double.toString(StringUtil.nullDouble(st_rate) * StringUtil.nullDouble(st_person_in));
				// 노무비
				sub_labor_cost[case1] = Double.toString(StringUtil.nullDouble(sub_rate[case1])
				        / StringUtil.nullDouble(sub_output[case1]));
			    }
			    if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
				sub_labor_cost[case1] = Double.toString(StringUtil.nullDouble(sub_labor_cost[case1])
				        + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
			    }

			    output[j][case1][case2] = sub_output[case1];
			    rate[j][case1][case2] = sub_rate[case1];
			    labor_cost[j][case1][case2] = sub_labor_cost[case1];
			    // System.out.println("===labor_cost : "+labor_cost[j][case1][case2]);

			    /*********************************/
			    /************** 제조경비 ************/
			    /*********************************/
			    // 포장비 임의값
			    // 5372
			    if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][0]))) {
				sub_pack_cost[case1] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][0]));
			    } else {
				if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
				        && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				    if ("Reel".equals(pack_type[j][case1][case2])) {
					sub_pack_cost[j] = Double.toString((1072 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					        + (1277 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				    } else if ("비닐".equals(pack_type[j][case1][case2])) {
					sub_pack_cost[j] = Double.toString((206 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					        + (764 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				    } else if ("Tray".equals(pack_type[j][case1][case2])) {
					sub_pack_cost[j] = Double.toString((485 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					        + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				    } else if ("골판지".equals(pack_type[j][case1][case2])) {
					sub_pack_cost[j] = Double.toString(1400 / StringUtil.nullDouble(out_pack[j][case1][case2]));
				    }

				    if (!"".equals(StringUtil.checkNull(sub_pack_cost[case1]))) {
					sub_pack_cost[case1] = String.format("%.2f", StringUtil.nullDouble(sub_pack_cost[case1]));
				    } else {
					sub_pack_cost[case1] = "0";
				    }
				} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				    if ("골판지".equals(pack_type[j][case1][case2])) {
					sub_pack_cost[case1] = Double.toString(1400 / StringUtil.nullDouble(out_pack[j][case1][case2]));
				    }
				    if (!"".equals(StringUtil.checkNull(sub_pack_cost[case1]))) {
					sub_pack_cost[case1] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
				    } else {
					sub_pack_cost[case1] = "0";
				    }
				} else {
				    sub_pack_cost[case1] = "0";
				}
			    }

			    // 5407
			    pack_cost[j][case1][case2] = sub_pack_cost[case1];

			    if ("외주".equals(make_type[j][case1][case2])
				    && (!"Insert".equals(method_type[j][case1][case2]) && !"자동".equals(method_type[j][case1][case2]))) {
				// 전력비
				sub_jun_cost[case1] = "0";
				if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
				    sub_jun_cost[case1] = Double.toString(StringUtil.nullDouble(sub_jun_cost[case1])
					    + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
				}

				// 직접경비
				sub_total_expense[case1] = Double.toString(StringUtil.nullDouble(sub_jun_cost[case1])
				        + StringUtil.nullDouble(type_cost[j][case1][case2]) + StringUtil.nullDouble(sub_pack_cost[case1]));

			    } else if (("중국".equals(pro_1[j][case1][case2]) && "단순".equals(method_type[j][case1][case2]))
				    || ("생산2".equals(pro_1[j][case1][case2]) && "단순".equals(method_type[j][case1][case2]))) {
				// 전력비
				sub_jun_cost[case1] = "0";
				if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
				    sub_jun_cost[case1] = Double.toString(StringUtil.nullDouble(sub_jun_cost[case1])
					    + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
				}

				// 직접경비
				sub_total_expense[case1] = Double.toString(StringUtil.nullDouble(sub_jun_cost[case1])
				        + StringUtil.nullDouble(sub_pack_cost[case1]));
			    } else {
				// 전력비
				sub_jun_cost[case1] = String.format("%.4f",
				        (StringUtil.nullDouble(st_jun_cost) / StringUtil.nullDouble(sub_output[case1])));
				if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
				    sub_jun_cost[case1] = Double.toString(StringUtil.nullDouble(sub_jun_cost[case1])
					    + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
				}

				// 직접경비
				if (!"".equals(StringUtil.checkNull(type_cost[j][case1][case2]))
				        && (!"검사".equals(type_1[j][case1][case2]) && !"포장".equals(type_1[j][case1][case2]))) {
				    sub_total_expense[case1] = Double.toString(StringUtil.nullDouble(sub_jun_cost[case1])
					    + StringUtil.nullDouble(type_cost[j][case1][case2])
					    + StringUtil.nullDouble(sub_pack_cost[case1]));
				} else {
				    sub_total_expense[case1] = Double.toString(StringUtil.nullDouble(sub_jun_cost[case1])
					    + StringUtil.nullDouble(sub_pack_cost[case1]));
				}
			    }
			    // System.out.println("m_upkeep : " + m_upkeep[j][case1][case2]+ " "+part_name[j][case1][case2] );
			    // System.out.println("st_jun_cost : " + st_jun_cost+ " "+part_name[j][case1][case2] );
			    // System.out.println("sub_output : " + sub_output[case1]+ " "+part_name[j][case1][case2] );

			    // 5447
			    if ("sub_Insert".equals(pro_type[j][case1][case2])
				    || !"".equals(StringUtil.checkNull(m_upkeep[j][case1][case2]))) {
				sub_total_expense[case1] = Double.toString(StringUtil.nullDouble(sub_total_expense[case1])
				        + StringUtil.nullDouble(m_upkeep[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
				sub_total_expense[case1] = Double.toString(StringUtil.nullDouble(sub_total_expense[case1])
				        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
			    } else {
				sub_total_expense[case1] = sub_total_expense[case1];
			    }
			    jun_cost[j][case1][case2] = sub_jun_cost[case1];
			    total_expense[j][case1][case2] = sub_total_expense[case1];

			    // 간접경비
			    sub_overhead[case1] = Double.toString(StringUtil.nullDouble(sub_total_expense[case1])
				    * StringUtil.nullDouble(st_overhead));
			    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
				sub_overhead[case1] = Double.toString(StringUtil.nullDouble(sub_overhead[case1])
				        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
			    }
			    overhead[j][case1][case2] = sub_overhead[case1];

			    // 기타경비
			    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
				gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
			    } else {
				gita_cost[j][case1][case2] = "0";
			    }
			    // System.out.println(">>>sub_assy의 sub_total_expense : "+sub_total_expense[case1]);
			    // System.out.println(">>>sub_assy의 sub_overhead : "+sub_overhead[case1]);
			    // System.out.println(">>>sub_assy의 gita_cost : "+gita_cost[j][case1][case2]);
			    // System.out.println(">>>sub_assy의 usage : "+usage[j][case1][case2]);
			    // 제조경비
			    sub_common_cost[case1] = Double.toString((StringUtil.nullDouble(sub_total_expense[case1])
				    + StringUtil.nullDouble(sub_overhead[case1]) + StringUtil.nullDouble(gita_cost[j][case1][case2]))
				    * StringUtil.nullDouble(usage[j][case1][case2]));
			    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
				sub_common_cost[case1] = Double.toString(StringUtil.nullDouble(sub_common_cost[case1])
				        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
			    }
			    common_cost[j][case1][case2] = sub_common_cost[case1];
			    // System.out.println(">>>sub_assy의 제조경비 : "+part_name[j][case1][case2]+" ==> "+common_cost[j][case1][case2]);
			    // System.out.println(">>>sub_assy의 make_type : "+make_type[j][case1][case2]);
			    // System.out.println(">>>sub_assy의 method_type : "+method_type[j][case1][case2]);

			    // 5484
			    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				/*********************************/
				/************* 금형벌수 *************/
				/*********************************/
				mold_su_1 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				mold_su_2 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				mold_su_3 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				mold_su_4 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
				    mold_su_5 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}
				if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
				    mold_su_6 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}
				if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
				    mold_su_7 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}
				if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
				    mold_su_8 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}

				if (StringUtil.nullDouble(mold_su_1) > StringUtil.nullDouble(mold_su_2)) {
				    mold_su = mold_su_1;
				} else {
				    mold_su = mold_su_2;
				}

				if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
				    mold_su = mold_su;
				} else {
				    mold_su = mold_su_3;
				}

				if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
				    mold_su = mold_su;
				} else {
				    mold_su = mold_su_3;
				}

				if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_4)) {
				    mold_su = mold_su;
				} else {
				    mold_su = mold_su_4;
				}

				if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_5)) {
				    mold_su = mold_su;
				} else {
				    mold_su = mold_su_5;
				}

				if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_6)) {
				    mold_su = mold_su;
				} else {
				    mold_su = mold_su_6;
				}

				if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_7)) {
				    mold_su = mold_su;
				} else {
				    mold_su = mold_su_7;
				}

				if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_8)) {
				    mold_su = mold_su;
				} else {
				    mold_su = mold_su_8;
				}

				if (StringUtil.nullDouble(mold_su) == 0 || StringUtil.nullDouble(mold_su) < 0) {
				    m_su[j][case1][case2] = "1";
				} else {
				    m_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(mold_su) + 1);
				}

				// 5554
				/*********************************/
				/*************** 감가비 *************/
				/*********************************/
				// 금형감가비
				// 기준수량
				if ("개조".equals(part_type_1[j][case1][case2])) {
				    // 일반감가기준(1000000)
				    // s_avg_su[j][case1][case2] = Integer.toString(1000000 * Integer.parseInt(cavity[j][case1][case2]) *
				    // Integer.parseInt(m_su[j][case1][case2]));
				    s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0",
					    cavity[j][case1][case2], m_su[j][case1][case2]));
				    // 판매량감가기준(6년치총수량)
				    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					avg_su[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(p_su_year_1[j]) + StringUtil.nullDouble(p_su_year_2[j])
					                + StringUtil.nullDouble(p_su_year_3[j]) + StringUtil.nullDouble(p_su_year_4[j])
					                + StringUtil.nullDouble(p_su_year_5[j]) + StringUtil.nullDouble(p_su_year_6[j])
					                + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil.nullDouble(p_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    } else {
					avg_su[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    }

				    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (StringUtil.DoubleMultiply("1000000.0",
					    cavity[j][case1][case2], m_su[j][case1][case2]))) {
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "일반";
				    } else {
					if ("일반".equals(mold_type[j][case1][case2])) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					} else if ("판매".equals(mold_type[j][case1][case2])) {
					    avg_su[j][case1][case2] = avg_su[j][case1][case2];
					} else {
					    avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "판매";
					}
				    }
				} else if ("공용".equals(part_type_1[j][case1][case2])) {
				    s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0",
					    cavity[j][case1][case2], m_su[j][case1][case2]));
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "일반";
				    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
				    } else {
					mold_cost[j][case1][case2] = "3000";
				    }
				} else {
				    // 일반감가기준(1000000)
				    s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0",
					    cavity[j][case1][case2], m_su[j][case1][case2]));
				    // 판매량감가기준(6년치총수량)
				    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
					avg_su[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(p_su_year_1[j]) + StringUtil.nullDouble(p_su_year_2[j])
					                + StringUtil.nullDouble(p_su_year_3[j]) + StringUtil.nullDouble(p_su_year_4[j])
					                + StringUtil.nullDouble(p_su_year_5[j]) + StringUtil.nullDouble(p_su_year_6[j])
					                + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil.nullDouble(p_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    } else {
					avg_su[j][case1][case2] = Double
					        .toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
					                + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
					                + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
					                + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
					                * StringUtil.nullDouble(usage[j][case1][case2]));
				    }

				    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (StringUtil.DoubleMultiply("1000000.0",
					    cavity[j][case1][case2], m_su[j][case1][case2]))) {
					avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					mold_type[j][case1][case2] = "일반";
				    } else {
					if ("일반".equals(mold_type[j][case1][case2])) {
					    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
					} else if ("판매".equals(mold_type[j][case1][case2])) {
					    avg_su[j][case1][case2] = avg_su[j][case1][case2];
					} else {
					    avg_su[j][case1][case2] = avg_su[j][case1][case2];
					    mold_type[j][case1][case2] = "판매";
					}
				    }
				}

				// 5634
				if ("감가".equals(mold_c_type[j][case1][case2])) {
				    // 일반감가기준(1000000)
				    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					s_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
					        * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
					        / StringUtil.nullDouble(s_avg_su[j][case1][case2])
					        * StringUtil.nullDouble(usage[j][case1][case2]));
				    }
				    // 판매량감가기준(6년치총수량)
				    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
					g_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
					        * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
					        / StringUtil.nullDouble(avg_su[j][case1][case2])
					        * StringUtil.nullDouble(usage[j][case1][case2]));
				    }
				} else {
				    s_depr_cost[j][case1][case2] = "0";
				    g_depr_cost[j][case1][case2] = "0";
				}

				if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
				    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				}
			    }

			    // 5658
			    if (("수동".equals(method_type[j][case1][case2]) || "자동".equals(method_type[j][case1][case2]))
				    && !"외주".equals(make_type[j][case1][case2])) {
				/*********************************/
				/************* 금형벌수 *************/
				/*********************************/
				line_su_1 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				line_su_2 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				line_su_3 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				line_su_4 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
				        .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
				    line_su_5 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}
				if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
				    line_su_6 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}
				if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
				    line_su_7 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}
				if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
				    line_su_8 = Double
					    .toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
					            .nullDouble(sub_output[case1]) * 20 * 25)) + 0.9) - 1);
				}

				if (StringUtil.nullDouble(line_su_1) > StringUtil.nullDouble(line_su_2)) {
				    line_su_in = line_su_1;
				} else {
				    line_su_in = line_su_2;
				}

				if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_3)) {
				    line_su_in = line_su_in;
				} else {
				    line_su_in = line_su_3;
				}

				if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_3)) {
				    line_su_in = line_su_in;
				} else {
				    line_su_in = line_su_3;
				}

				if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_4)) {
				    line_su_in = line_su_in;
				} else {
				    line_su_in = line_su_4;
				}

				if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_5)) {
				    line_su_in = line_su_in;
				} else {
				    line_su_in = line_su_5;
				}

				if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_6)) {
				    line_su_in = line_su_in;
				} else {
				    line_su_in = line_su_6;
				}

				if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_7)) {
				    line_su_in = line_su_in;
				} else {
				    line_su_in = line_su_7;
				}

				if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_8)) {
				    line_su_in = line_su_in;
				} else {
				    line_su_in = line_su_8;
				}

				if (StringUtil.nullDouble(line_su_in) == 0 || StringUtil.nullDouble(line_su_in) < 0) {
				    line_su[j][case1][case2] = "1";
				} else {
				    line_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(line_su_in) + 1);
				}
			    }

			    /*********************************/
			    /************** 감가비 **************/
			    /*********************************/
			    // 금형감가비
			    // 판매량감가기준(6년치총수량)

			    sub_avg_su[case1] = Double.toString((StringUtil.nullDouble(a_su_year_1[j])
				    + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
				    + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
				    + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
				    .nullDouble(a_su_year_8[j])) * 1000);
			    // System.out.println("@@@@@@@@@@@@ 333 sul_cost[j][case1][case2] : "+sul_cost[j][case1][case2]);
			    if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))
				    && !"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
				if (("수동".equals(method_type[j][case1][case2]) || "자동".equals(method_type[j][case1][case2]))
				        && !"외주".equals(make_type[j][case1][case2])) {
				    to_cost = Double.toString((StringUtil.nullDouble(sul_cost[j][case1][case2])
					    * StringUtil.nullDouble(line_su[j][case1][case2]) * 1.05)
					    + StringUtil.nullDouble(etc_cost[j][case1][case2]));
				} else {
				    to_cost = Double.toString((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05)
					    + StringUtil.nullDouble(etc_cost[j][case1][case2]));
				}
				sub_g_depr_cost[case1] = Double.toString((StringUtil.nullDouble(to_cost) * 1000)
				        / StringUtil.nullDouble(sub_avg_su[case1]));
				sub_s_depr_cost[case1] = Double.toString((StringUtil.nullDouble(to_cost) * 1000)
				        / StringUtil.nullDouble(sub_avg_su[case1]));
			    } else if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) {
				if (("수동".equals(method_type[j][case1][case2]) || "자동".equals(method_type[j][case1][case2]))
				        && !"외주".equals(make_type[j][case1][case2])) {
				    sub_g_depr_cost[case1] = Double.toString(((StringUtil.nullDouble(sul_cost[j][case1][case2])
					    * StringUtil.nullDouble(line_su[j][case1][case2]) * 1.05) * 1000)
					    / StringUtil.nullDouble(sub_avg_su[case1]));
				    sub_s_depr_cost[case1] = Double.toString(((StringUtil.nullDouble(sul_cost[j][case1][case2])
					    * StringUtil.nullDouble(line_su[j][case1][case2]) * 1.05) * 1000)
					    / StringUtil.nullDouble(sub_avg_su[case1]));
				} else {
				    sub_g_depr_cost[case1] = Double
					    .toString(((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
					            / StringUtil.nullDouble(sub_avg_su[case1]));
				    sub_s_depr_cost[case1] = Double
					    .toString(((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
					            / StringUtil.nullDouble(sub_avg_su[case1]));
				}
			    } else if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
				sub_g_depr_cost[case1] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(sub_avg_su[case1]));
				sub_s_depr_cost[case1] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(sub_avg_su[case1]));
			    }
			    // 5766
			    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
				sub_g_depr_cost[case1] = Double.toString(StringUtil.nullDouble(sub_g_depr_cost[case1])
				        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
				sub_s_depr_cost[case1] = Double.toString(StringUtil.nullDouble(sub_s_depr_cost[case1])
				        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			    } else {
				sub_g_depr_cost[case1] = sub_g_depr_cost[case1];
				sub_s_depr_cost[case1] = sub_s_depr_cost[case1];
			    }

			    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
				sub_g_depr_cost[case1] = Double.toString(StringUtil.nullDouble(sub_g_depr_cost[case1])
				        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
				sub_s_depr_cost[case1] = Double.toString(StringUtil.nullDouble(sub_s_depr_cost[case1])
				        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
			    } else {
				sub_g_depr_cost[case1] = sub_g_depr_cost[case1];
				sub_s_depr_cost[case1] = sub_s_depr_cost[case1];
			    }

			    // 기타투자비감가
			    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
				etc_su = "0";
				etc_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000));
				etc_depr[j][case1][case2] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } else {
				etc_depr[j][case1][case2] = "0";
			    }

			    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
				if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
					    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				} else {
				    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				}
				sub_g_depr_cost[case1] = Double.toString(StringUtil.nullDouble(sub_g_depr_cost[case1])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
				sub_s_depr_cost[case1] = Double.toString(StringUtil.nullDouble(sub_s_depr_cost[case1])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    }
			    // System.out.println(">>"+sub_g_depr_cost[case1]+">>"+g_depr_cost[j][case1][case2]+">>etc_depr>>"+etc_depr[j][case1][case2]);
			    sub_g_depr_cost[case1] = Double.toString(StringUtil.nullDouble(sub_g_depr_cost[case1])
				    + StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(etc_depr[j][case1][case2]));

			    // YZK감가비 20101223 // 필드 yazaki_ro → 포장용투자비로대체/산출식 미정
			    // 포장용투자비로 항목 변경에 따른 감가비추가 : 20110526
			    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
				yzk_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
				        * StringUtil.nullDouble(usage[j][case1][case2]));
				yzk_depr[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000) / StringUtil
				                .nullDouble(yzk_su)) * StringUtil.nullDouble(usage[j][case1][case2]));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
				yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
				        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
				s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
			    }

			    /*********************************/
			    /************** 물류비 **************/
			    /*********************************/
			    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
				dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
			    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
				    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
				if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
				    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
				} else if ("생산4".equals(start_pro[j])) {
				    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
				}
				dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
				        / StringUtil.nullDouble(out_pack[j][case1][case2]));
				dis_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(dis_cost[j][case1][case2]));
			    } else {
				dis_cost[j][case1][case2] = "0";
			    }

			    // 5848
			    /*********************************/
			    /************** 관리비 **************/
			    /*********************************/
			    // 가공비
			    sub_process_cost[case1] = Double.toString(StringUtil.nullDouble(sub_labor_cost[case1])
				    + StringUtil.nullDouble(sub_common_cost[case1]));

			    // 관세
			    if ("중국".equals(pro_1[j][case1][case2])) {
				tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(sub_process_cost[case1]) * 0.08);
			    } else {
				tariff[j][case1][case2] = "0";
			    }

			    if (!"".equals(StringUtil.checkNull(pl_tariff[j][case1][case2]))) {
				tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(tariff[j][case1][case2])
				        + StringUtil.nullDouble(pl_tariff[j][case1][case2]));
			    }

			    // 재료관리비
			    sub_jae_cost[case1] = String.format("%.4f",
				    (StringUtil.nullDouble(g_sub_meterial_cost[case1]) * StringUtil.nullDouble(st_jae_cost)));
			    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
				sub_jae_cost[case1] = Double.toString(StringUtil.nullDouble(sub_jae_cost[case1])
				        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
			    }
			    jae_cost[j][case1][case2] = sub_jae_cost[case1];

			    // 일반관리비
			    sub_ge_cost[case1] = String.format("%.4f",
				    (StringUtil.nullDouble(sub_process_cost[case1]) * StringUtil.nullDouble(st_ge_cost)));
			    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
				sub_ge_cost[case1] = Double.toString(StringUtil.nullDouble(sub_ge_cost[case1])
				        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
			    }
			    ge_cost[j][case1][case2] = sub_ge_cost[case1];

			    // 총관리비
			    sub_ad_cost[case1] = Double.toString(StringUtil.nullDouble(sub_jae_cost[case1])
				    + StringUtil.nullDouble(sub_ge_cost[case1]) + StringUtil.nullDouble(tariff[j][case1][case2])
				    + StringUtil.nullDouble(dis_cost[j][case1][case2]) + StringUtil.nullDouble(g_sub_loss[case1]));

			    // 품질비용
			    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
				sub_ad_cost[case1] = Double.toString(StringUtil.nullDouble(sub_ad_cost[case1])
				        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
			    } else {
				qu_cost[j][case1][case2] = "0";
			    }
			    ad_cost[j][case1][case2] = sub_ad_cost[case1];

			    /*********************************/
			    /*************** 총원가 *************/
			    /*********************************/
			    sub_s_actual_cost[case1] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(sub_labor_cost[case1]) + StringUtil.nullDouble(sub_common_cost[case1])
				    + StringUtil.nullDouble(sub_s_depr_cost[case1]) + StringUtil.nullDouble(sub_ad_cost[case1]));
			    sub_g_actual_cost[case1] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(sub_labor_cost[case1]) + StringUtil.nullDouble(sub_common_cost[case1])
				    + StringUtil.nullDouble(sub_g_depr_cost[case1]) + StringUtil.nullDouble(sub_ad_cost[case1]));
			    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
				sub_s_actual_cost[case1] = Double.toString(StringUtil.nullDouble(sub_s_actual_cost[case1])
				        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
				sub_g_actual_cost[case1] = Double.toString(StringUtil.nullDouble(sub_g_actual_cost[case1])
				        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			    }
			    g_actual_cost[j][case1][case2] = sub_g_actual_cost[case1];
			    g_depr_cost[j][case1][case2] = sub_g_depr_cost[case1];
			    // System.out.println("총원가 찍기 10: "+g_actual_cost[j][case1][case2]+" "+m_total_cost[j][case1][case2]
			    // +" "+sub_labor_cost[case1] + " "+sub_common_cost[case1] +
			    // " "+sub_ad_cost[case1]+" "+sub_g_depr_cost[case1]);

			    /*********************************/
			    /*************** 수익률 *************/
			    /*********************************/
			    if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
				sub_s_earn_rate[case1] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				        .nullDouble(sub_s_actual_cost[case1])) / StringUtil.nullDouble(ket_cost[j]));
				sub_g_earn_rate[case1] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				        .nullDouble(sub_g_actual_cost[case1])) / StringUtil.nullDouble(ket_cost[j]));
			    } else {
				if ("0".equals(target_cost[j])) {
				    sub_s_earn_rate[case1] = Double
					    .toString(((StringUtil.nullDouble(sub_s_actual_cost[case1]) / 0.7) - StringUtil
					            .nullDouble(sub_s_actual_cost[case1]))
					            / (StringUtil.nullDouble(sub_s_actual_cost[case1]) / 0.7));
				    sub_g_earn_rate[case1] = Double
					    .toString(((StringUtil.nullDouble(sub_g_actual_cost[case1]) / 0.7) - StringUtil
					            .nullDouble(sub_g_actual_cost[case1]))
					            / (StringUtil.nullDouble(sub_g_actual_cost[case1]) / 0.7));
				} else {
				    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
				    sub_s_earn_rate[case1] = Double
					    .toString(((StringUtil.nullDouble(sub_s_actual_cost[case1]) / (1 - StringUtil
					            .nullDouble(target_rate))) - StringUtil.nullDouble(sub_s_actual_cost[case1]))
					            / (StringUtil.nullDouble(sub_s_actual_cost[case1]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				    sub_g_earn_rate[case1] = Double
					    .toString(((StringUtil.nullDouble(sub_g_actual_cost[case1]) / (1 - StringUtil
					            .nullDouble(target_rate))) - StringUtil.nullDouble(sub_g_actual_cost[case1]))
					            / (StringUtil.nullDouble(sub_g_actual_cost[case1]) / (1 - StringUtil
					                    .nullDouble(target_rate))));
				}
			    }
			    g_earn_rate[j][case1][case2] = sub_g_earn_rate[case1];

			    // 조립산출 재료비
			    if (!"".equals(StringUtil.checkNull(s_assy_m_total_cost[j]))) {
				s_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(s_assy_m_total_cost[j])
				        + StringUtil.nullDouble(sub_s_actual_cost[case1]));
			    } else {
				s_assy_m_total_cost[j] = sub_s_actual_cost[case1];
			    }
			    if (!"".equals(StringUtil.checkNull(g_assy_m_total_cost[j]))) {
				g_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(g_assy_m_total_cost[j])
				        + StringUtil.nullDouble(sub_g_actual_cost[case1]));
			    } else {
				g_assy_m_total_cost[j] = sub_g_actual_cost[case1];
			    }
			    // System.out.println("g_assy_m_total_cost4 === group ==> "+ part_name[j][case1][case2]
			    // +group_no[j][case1][case2] +" : "+
			    // g_assy_m_total_cost[j]+"  sub_g => "+StringUtil.nullDouble(sub_g_actual_cost[case1]) );
			    // 5957
			} // pro_type : sub_assy or sub_Insert if end
		    }// pro_type, part_type_1 if else end (복합금형)
		}// case1 for end
	    } // table_row_count for end
	} // sub_assy_content if end
    } // sub_assy_content function end

    /**
     * 함수명 : cost_assy_content 설명 : 원재료 단가 산출 assy
     * 
     * @param
     * @return
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 08. 09.
     */
    public void cost_assy_content() throws Exception {
	// 5967
	Connection conn = null;
	CostAccDao costAccDao = null;
	// System.out.println("Assy_content : "+Assy_content);
	// System.out.println("총원가 : "+g_actual_cost[0][0][0]);

	if ("OK".equals(Assy_content)) {

	    /*********************************/
	    /********** 조립원가산출 ************/
	    /*********************************/
	    int case1 = 0;
	    int case2 = 0;
	    scrap_cost_im = "0";
	    for (int j = 0; j < Integer.parseInt(table_row_count); j++) {
		// 스크랩판매단가
		// C2100 = 원화_Cu*0.95 + 원화_Zn*0.05)*0.55
		// C2600 / C2680 = 원화_Cu*0.65 + 원화_Zn*0.35)*0.55
		// C5210 / C5191 = 원화_Cu*0.92 + 원화_Sn*0.08)*0.45
		// C194 = 원화_Cu*0.98*0.55
		// W_Lme_cu

		if ("재료도금".equals(plating_type[j][case1][case2]) && "Au".equals(type_2[j][case1][case2])) {
		    scrap_cost_im = Double.toString(24878 / 1000);
		    scrap_rate[j][case1][case2] = "";
		} else {
		    met_type = meterial[j][case1][case2];
		    if ("C2100R".equals(met_type)) {
			if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
			    scrap_cost_im = Double
				    .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.95 + StringUtil.nullDouble(W_Lme_zn) * 0.05) / 1000)
				            * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			} else {
			    scrap_cost_im = Double
				    .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.95 + StringUtil.nullDouble(W_Lme_zn) * 0.05) / 1000) * 0.55);
			    scrap_rate[j][case1][case2] = "55";
			}
		    } else if ("C2600R".equals(met_type) || "C2680R".equals(met_type)) { // 석황동 스크랩판가비율 55% 에서 60% 변경 10.6.30
			if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
			    scrap_cost_im = Double
				    .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.65 + StringUtil.nullDouble(W_Lme_zn) * 0.35) / 1000)
				            * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			} else {
			    scrap_cost_im = Double
				    .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.65 + StringUtil.nullDouble(W_Lme_zn) * 0.35) / 1000) * 0.60);
			    scrap_rate[j][case1][case2] = "60";
			}
		    } else if ("C5210R".equals(met_type) || "C5191R".equals(met_type) || "PMC26R".equals(met_type)
			    || "PMC90R".equals(met_type)) { // 인청동 스크랩판가율 45%에서50%변경 10.6.30
			if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
			    scrap_cost_im = Double
				    .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.92 + StringUtil.nullDouble(W_Lme_sn) * 0.08) / 1000)
				            * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			} else {
			    scrap_cost_im = Double
				    .toString(((StringUtil.nullDouble(W_Lme_cu) * 0.92 + StringUtil.nullDouble(W_Lme_sn) * 0.08) / 1000) * 0.50);
			    scrap_rate[j][case1][case2] = "50";
			}
		    } else if ("C194".equals(met_type)) {
			if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
			    scrap_cost_im = Double.toString((StringUtil.nullDouble(W_Lme_cu) * 0.98 / 1000)
				    * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			} else {
			    scrap_cost_im = Double.toString((StringUtil.nullDouble(W_Lme_cu) * 0.98 / 1000) * 0.55);
			    scrap_rate[j][case1][case2] = "55";
			}
		    } else {
			if (!"".equals(StringUtil.checkNull(scrap_rate[j][case1][case2]))) {
			    scrap_cost_im = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				    * (StringUtil.nullDouble(scrap_rate[j][case1][case2]) / 100));
			} else {
			    scrap_cost_im = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.45);
			    scrap_rate[j][case1][case2] = "45";
			}
		    }
		} // 스크랩판매단가 if end
		if (("assy".equals(pro_type[j][case1][case2]) || "TML-조립".equals(pro_type[j][case1][case2]))
		        && "복합금형".equals(part_type_1[j][case1][case2])) {

		    if ("외주".equals(make_type[j][case1][case2])) {
			if ("무상".equals(pro_1[j][case1][case2])) {
			    /*********************************/
			    /*************** 재료비 *************/
			    /*********************************/
			    if ("재료도금".equals(plating_type[j][case1][case2])) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        + (StringUtil.nullDouble(plating_cost[j][case1][case2]) / 1000));
			    } else {
				unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
			    }
			    // 6045
			    if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
				// 원재료비(재생반영)
				meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				        * StringUtil.nullDouble(t_weight[j][case1][case2]));
				if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				}

				// 스크랩판매비(재생반영)
				scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
				        + StringUtil.nullDouble(scrap_cost[j][case1][case2]));
				if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				}

				// loss 비
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
				        + StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.02);
				if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				}

				// 총재료비
				m_total_cost[j][case1][case2] = Double
				        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
				                    .nullDouble(scrap_cost[j][case1][case2]))
				                * StringUtil.nullDouble(usage[j][case1][case2]));

			    } else {
				// 원재료비
				meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
				        * StringUtil.nullDouble(t_weight[j][case1][case2]));
				if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				}

				// 스크랩판매비
				scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
				        * StringUtil.nullDouble(scrap_cost_im));
				if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
				}

				// loss비
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02);
				if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
					    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
				}

				// 총재료비
				m_total_cost[j][case1][case2] = Double
				        .toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				                + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil
				                    .nullDouble(scrap_cost[j][case1][case2]))
				                * StringUtil.nullDouble(usage[j][case1][case2]));

			    } // re_m_cost if end

			} else {
			    m_total_cost[j][case1][case2] = "0";
			} // pro_1 : 무상 if end

			if ("선생산".equals(mix_group_1[j][case1][case2])) {
			    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(g_actual_cost[j][1][0]));

			} else {
			    m_total_cost[j][case1][case2] = m_total_cost[j][case1][case2];

			}

			if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
			    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(g_actual_cost[j][1][0]));

			} else {
			    m_total_cost[j][case1][case2] = m_total_cost[j][case1][case2];

			}

			/*********************************/
			/*************** 제조경비 ***********/
			/*********************************/
			// 포장비
			if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
			    pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
			} else {
			    if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
				    && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				if ("Reel".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString((1072 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					    + (1277 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				} else if ("Tray".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString((485 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					    + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				} else if ("골판지".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil.nullDouble(out_pack[j][case1][case2]));
				}

				if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
				    pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
				} else {
				    pack_cost[j][case1][case2] = "0";
				}
			    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				if ("골판지".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil.nullDouble(out_pack[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
				    pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
				} else {
				    pack_cost[j][case1][case2] = "0";
				}
			    } else {
				pack_cost[j][case1][case2] = "0";
			    }
			}
			// 금형유지비
			m_upkeep[j][case1][case2] = "0.3";
			if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
			    m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
				    + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
			}

			// 직접경비
			total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
			        + StringUtil.nullDouble(pack_cost[j][case1][case2]) + StringUtil.nullDouble(m_upkeep[j][case1][case2]));
			if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
			    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
				    + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
			} else {
			    total_expense[j][case1][case2] = total_expense[j][case1][case2];
			}

			// 간접경비
			overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2]) * 0.2);
			if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
			    overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
				    + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
			}

			// 기타경비
			if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
			    gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
			} else {
			    gita_cost[j][case1][case2] = "0";
			}

			// 제조경비
			common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
			        + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil.nullDouble(gita_cost[j][case1][case2]))
			        * StringUtil.nullDouble(usage[j][case1][case2]));
			if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
			    common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
			}

			/*********************************/
			/*************** 감가비 *************/
			/*********************************/
			// 금형감가비
			// 기준수량
			if ("개조".equals(part_type_1[j][case1][case2])) {
			    // 일반감가기준(50000000)

			    s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));

			    // 판매량감가기준(6년치총수량)
			    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
				        + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
				        + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
				        + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
				            .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } else {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
				        + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
				        + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
				        + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
				            .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    }
			    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer.parseInt(cavity[j][case1][case2]))) {
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
			    } else {
				if ("일반".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				} else if ("판매".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				} else {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "판매";
				}
			    }
			} else if ("공용".equals(part_type_1[j][case1][case2])) {
			    s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
			    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
			    mold_type[j][case1][case2] = "일반";
			    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
			    } else {
				mold_cost[j][case1][case2] = "2500";
			    }
			} else {
			    // 일반감가기준(50000000)
			    s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2]));
			    // 판매량감가기준(6년치총수량)
			    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
				        + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
				        + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
				        + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
				            .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } else {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
				        + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
				        + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
				        + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
				            .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    }
			    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer.parseInt(cavity[j][case1][case2]))) {
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
			    } else {
				if ("일반".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				} else if ("판매".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				} else {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "판매";
				}
			    }
			}

			if ("감가".equals(mold_c_type[j][case1][case2])) {
			    // 일반감가기준 (50000000)
			    s_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2]) * 1000)
				    / StringUtil.nullDouble(s_avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));

			    // 판매량감가기준(6년치총수량)
			    g_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2]) * 1000)
				    / StringUtil.nullDouble(avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));
			} else {
			    s_depr_cost[j][case1][case2] = "0";
			    g_depr_cost[j][case1][case2] = "0";
			}

			// 6274
			// 기타투자비감가
			if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
			    etc_su = "0";
			    etc_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				    + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				    + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				    + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
				    * StringUtil.nullDouble(usage[j][case1][case2]));
			    etc_depr[j][case1][case2] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
				    / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(etc_depr[j][case1][case2]));
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(etc_depr[j][case1][case2]));
			} else {
			    etc_depr[j][case1][case2] = "0";
			}

			if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
			    if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    } else {
				etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    }
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			}

			if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			} else {
			    g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
			    s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			}

			if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
			} else {
			    g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
			    s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			}

			/*********************************/
			/*************** 관리비 *************/
			/*********************************/
			// 재료관리비
			jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2]) * 0.011);
			if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
			    jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
			} else {
			    jae_cost[j][case1][case2] = jae_cost[j][case1][case2];
			}

			// 일반관리비
			ge_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2]) + StringUtil
			        .nullDouble(overhead[j][case1][case2])) * 0.2);
			if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
			    ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
			} else {
			    ge_cost[j][case1][case2] = ge_cost[j][case1][case2];
			}

			// R&D비
			s_rnd_cost[j][case1][case2] = Double
			        .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
			                .nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(s_depr_cost[j][case1][case2]) / StringUtil
			                        .nullDouble(usage[j][case1][case2])) + (StringUtil.nullDouble(common_cost[j][case1][case2]) / StringUtil
			                .nullDouble(usage[j][case1][case2])))
			                * StringUtil.nullDouble(usage[j][case1][case2]) * 0.019);
			g_rnd_cost[j][case1][case2] = Double
			        .toString(((StringUtil.nullDouble(m_total_cost[j][case1][case2]) / StringUtil
			                .nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(g_depr_cost[j][case1][case2]) / StringUtil
			                        .nullDouble(usage[j][case1][case2])) + (StringUtil.nullDouble(common_cost[j][case1][case2]) / StringUtil
			                .nullDouble(usage[j][case1][case2])))
			                * StringUtil.nullDouble(usage[j][case1][case2]) * 0.019);

			if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
			    s_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
			    g_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
			} else {
			    s_rnd_cost[j][case1][case2] = s_rnd_cost[j][case1][case2];
			    g_rnd_cost[j][case1][case2] = g_rnd_cost[j][case1][case2];
			}

			// 총관리비
			ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
			        + StringUtil.nullDouble(ge_cost[j][case1][case2]) + StringUtil.nullDouble(g_rnd_cost[j][case1][case2]));

			// 품질비용
			if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
			    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
				    + StringUtil.nullDouble(qu_cost[j][case1][case2]));
			} else {
			    qu_cost[j][case1][case2] = "0";
			}

			// 6356
			/*********************************/
			/*************** 총원가 *************/
			/*********************************/
			s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + StringUtil.nullDouble(common_cost[j][case1][case2]) + StringUtil.nullDouble(s_depr_cost[j][case1][case2])
			        + StringUtil.nullDouble(ad_cost[j][case1][case2]));
			g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + StringUtil.nullDouble(common_cost[j][case1][case2]) + StringUtil.nullDouble(g_depr_cost[j][case1][case2])
			        + StringUtil.nullDouble(ad_cost[j][case1][case2]));

			if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
			    s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_actual_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_actual_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			} else {
			    s_actual_cost[j][case1][case2] = s_actual_cost[j][case1][case2];
			    g_actual_cost[j][case1][case2] = g_actual_cost[j][case1][case2];
			}
			// System.out.println("총원가 찍기 11: "+g_actual_cost[j][case1][case2]);
			/*********************************/
			/*************** 수익률 *************/
			/*********************************/
			if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
			    s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				    .nullDouble(s_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
			    g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				    .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
			} else {
			    if ("0".equals(target_cost[j])) {
				s_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
				                .nullDouble(s_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7));
				g_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
				                .nullDouble(g_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
			    } else {
				target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
				s_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
				                .nullDouble(target_rate))) - StringUtil.nullDouble(s_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
				                        .nullDouble(target_rate))));
				g_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
				                .nullDouble(target_rate))) - StringUtil.nullDouble(g_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
				                        .nullDouble(target_rate))));
			    }
			}
		    } else { // make_type : 외주 if end

			// 6388
			if (StringUtil.nullDouble(ton[j][case1][case2]) > 29 && StringUtil.nullDouble(ton[j][case1][case2]) < 40) {
			    ton_value = "39";
			} else if (StringUtil.nullDouble(ton[j][case1][case2]) < 60) {
			    ton_value = "59";
			} else {
			    ton_value = "60";
			}

			ArrayList accExStandardList = null;

			try {
			    conn = DBUtil.getConnection();
			    costAccDao = new CostAccDao(conn);
			    accExStandardList = costAccDao.getAccExStandardList(pro_1[j][case1][case2], pro_type[j][case1][case2],
				    ton_value, "1");
			} catch (Exception e) {
			    e.printStackTrace();
			} finally {
			    DBUtil.close(conn);
			}

			Hashtable accExStandardMap = null;
			accExStandardMap = (Hashtable) accExStandardList.get(0);
			st_eff_value_in = (String) accExStandardMap.get("st_eff_value"); // 효율
			st_person_in = (String) accExStandardMap.get("st_person"); // 표준인원
			st_rate = (String) accExStandardMap.get("st_rate"); // 임율
			st_jun_cost = (String) accExStandardMap.get("st_jun_cost"); // 전력비
			st_ma_depr = (String) accExStandardMap.get("st_ma_depr"); // 기계감가
			st_tabalu = (String) accExStandardMap.get("st_tabalu"); // 타발유
			st_overhead = (String) accExStandardMap.get("st_overhead"); // 간접경비
			st_ge_cost = (String) accExStandardMap.get("st_ge_cost"); // 일반관리비율
			st_jae_cost = (String) accExStandardMap.get("st_jae_cost"); // 재료관리비율
			st_rnd_cost = (String) accExStandardMap.get("st_rnd_cost"); // R&D비율
			st_royalty = (String) accExStandardMap.get("st_royalty"); // 로얄티비율

			st_eff_value[j][case1][case2] = st_eff_value_in;// 효율
			st_person[j][case1][case2] = st_person_in;// 표준인원

			if ("기타".equals(pro_level[j][case1][case2])) { // 작업인원
			    st_person_in = pro_level_txt[j][case1][case2];
			} else if (!"".equals(StringUtil.checkNull(st_person_in))) {
			    // st_person_in = Integer.toString(1 / Integer.parseInt(st_person_in));
			    temp_1 = new BigDecimal("1");
			    temp_2 = new BigDecimal(StringUtil.checkNull(st_person_in));
			    st_person_in = Double.toString(temp_1.divide(temp_2).doubleValue());
			} else {
			    st_person_in = "1";
			}

			// 6440
			/*********************************/
			/*************** 재료비 *************/
			/*********************************/
			if ("재료도금".equals(plating_type[j][case1][case2])) {
			    unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				    + (StringUtil.nullDouble(plating_cost[j][case1][case2])) / 1000);
			} else {
			    unitcost_si[j][case1][case2] = unitcost_si[j][case1][case2];
			}
			// System.out.println(">>>>>>>TML-조립의 h_scrap : "+h_scrap[j][case1][case2]);
			// System.out.println(">>>>>>>TML-조립의 scrap_cost : "+scrap_cost[j][case1][case2]);
			// System.out.println(">>>>>>>TML-조립의 pl_scrap_cost : "+pl_scrap_cost[j][case1][case2]);
			// System.out.println(">>>>>>>TML-조립의 scrap_cost_im : "+scrap_cost_im);
			if (!"".equals(StringUtil.checkNull(re_m_cost[j][case1][case2]))) {
			    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				    * StringUtil.nullDouble(t_weight[j][case1][case2])); // 원재료비(재생반영)
			    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
			    }
			    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
				    * StringUtil.nullDouble(scrap_cost[j][case1][case2])); // 스크랩판매비(재생반영)
			    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
			    }
			    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
				    * StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 0.02); // loss비
			    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
				        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
			    }

			    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil.nullDouble(scrap_cost[j][case1][case2]))
				    * StringUtil.nullDouble(usage[j][case1][case2])); // 총재료비
			    // System.out.println(">>>>>>>TML-조립의 재료비 1: "+m_total_cost[j][case1][case2]);
			} else {
			    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2])
				    * StringUtil.nullDouble(t_weight[j][case1][case2])); // 원재료비
			    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				meterial_cost[j][case1][case2] = Double.toHexString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
			    }
			    scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_scrap[j][case1][case2])
				    * StringUtil.nullDouble(scrap_cost_im)); // 스크랩판매비 스크랩판매단가 : Kg당4650원
			    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
			    }
			    loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.02); // loss비
			    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
				        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
			    }

			    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil.nullDouble(scrap_cost[j][case1][case2]))
				    * StringUtil.nullDouble(usage[j][case1][case2])); // 총재료비
			    // System.out.println(">>>>>>>TML-조립의 meterial_cost 2: "+meterial_cost[j][case1][case2]);
			    // System.out.println(">>>>>>>TML-조립의 loss 2: "+loss[j][case1][case2]);
			    // System.out.println(">>>>>>>TML-조립의 scrap_cost 2: "+scrap_cost[j][case1][case2]);
			    // System.out.println(">>>>>>>TML-조립의 usage 2: "+usage[j][case1][case2]);
			    // System.out.println(">>>>>>>TML-조립의 재료비 2: "+m_total_cost[j][case1][case2]);
			}

			if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
			    m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
			    // System.out.println(">>>>>>>TML-조립의 재료비 3: "+m_total_cost[j][case1][case2]);
			} else {
			    m_total_cost[j][case1][case2] = m_total_cost[j][case1][case2];
			    // System.out.println(">>>>>>>TML-조립의 재료비 4: "+m_total_cost[j][case1][case2]);
			}
			// System.out.println(">>>>>>>TML-조립의 재료비 최종: "+m_total_cost[j][case1][case2]);
			/*********************************/
			/*************** 노무비 *************/
			/*********************************/
			if (!"생산1".equals(pro_1[j][case1][case2]) && !"생산3".equals(pro_1[j][case1][case2])) {
			    st_eff_value_in = "1";
			    st_rate = "0";
			    st_person_in = "0";
			}

			// 6514
			if (!"".equals(StringUtil.checkNull(eff_value[j][case1][case2]))) {
			    st_eff_value_in = eff_value[j][case1][case2];
			    st_eff_value[j][case1][case2] = eff_value[j][case1][case2];
			}

			output[j][case1][case2] = Double.toString(60 * StringUtil.nullDouble(spm[j][case1][case2])
			        * StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in)); // 시간당생산량
			if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
			    output[j][case1][case2] = Double.toString(StringUtil.nullDouble(output[j][case1][case2])
				    + StringUtil.nullDouble(pl_output[j][case1][case2]));
			}

			rate[j][case1][case2] = Double.toString(StringUtil.nullDouble(st_rate) * StringUtil.nullDouble(st_person_in)); // 임율
			if (!"".equals(StringUtil.checkNull(pl_rate[j][case1][case2]))) {
			    rate[j][case1][case2] = Double.toHexString(StringUtil.nullDouble(rate[j][case1][case2])
				    + StringUtil.nullDouble(pl_rate[j][case1][case2]));
			}
			// System.out.println("=======cost_sub_assy_content()=============");
			if ("검사".equals(type_1[j][case1][case2]) || "포장".equals(type_1[j][case1][case2])) {
			    // 노무비
			    labor_cost[j][case1][case2] = Double.toString(((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
				    .nullDouble(output[j][case1][case2])) + (StringUtil.nullDouble(st_rate) / (3600 / StringUtil
				    .nullDouble(type_cost[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in))))
				    * StringUtil.nullDouble(usage[j][case1][case2]));
			    // System.out.println("labor_cost 1번  : "+labor_cost[j][case1][case2]);
			} else {
			    // 노무비
			    labor_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(rate[j][case1][case2]) / StringUtil
				    .nullDouble(output[j][case1][case2])) * StringUtil.nullDouble(usage[j][case1][case2]));
			    // System.out.println("labor_cost 2번  : "+labor_cost[j][case1][case2]);
			}

			if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
			    labor_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
			    // System.out.println("labor_cost 3번  : "+labor_cost[j][case1][case2]);
			} else {
			    labor_cost[j][case1][case2] = labor_cost[j][case1][case2];
			    // System.out.println("labor_cost 4번  : "+labor_cost[j][case1][case2]);
			}
			assy_labor_cost[j] = labor_cost[j][case1][case2];

			/*********************************/
			/************* 제조경비 *************/
			/*********************************/
			// 전력비
			jun_cost[j][case1][case2] = String.format("%.4f",
			        StringUtil.nullDouble(st_jun_cost) / StringUtil.nullDouble(output[j][case1][case2]));
			if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
			    jun_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
			}

			// 기계감가
			ma_depr[j][case1][case2] = String.format("%.4f",
			        StringUtil.nullDouble(st_ma_depr) / StringUtil.nullDouble(output[j][case1][case2]));
			if (!"".equals(StringUtil.checkNull(pl_ma_depr[j][case1][case2]))) {
			    ma_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(ma_depr[j][case1][case2])
				    + StringUtil.nullDouble(pl_ma_depr[j][case1][case2]));
			}

			// 타발유
			tabalu[j][case1][case2] = String.format("%.4f",
			        StringUtil.nullDouble(st_tabalu) / StringUtil.nullDouble(output[j][case1][case2]));
			if (!"".equals(StringUtil.checkNull(pl_tabalu[j][case1][case2]))) {
			    tabalu[j][case1][case2] = Double.toString(StringUtil.nullDouble(tabalu[j][case1][case2])
				    + StringUtil.nullDouble(pl_tabalu[j][case1][case2]));
			}

			// 금형유지비
			m_upkeep[j][case1][case2] = "0.3";
			if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
			    m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
				    + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
			}

			// 6577
			// 포장비임의값
			if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
			    pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
			} else {
			    if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
				    && !"".equals(StringUtil.checkNull(output[j][case1][case2]))) {
				if ("Reel".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString((1072 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					    + (1277 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				} else if ("Tray".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString((485 / StringUtil.nullDouble(in_pack[j][case1][case2]))
					    + (1056 / StringUtil.nullDouble(out_pack[j][case1][case2])));
				} else if ("골판지".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil.nullDouble(out_pack[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
				    pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
				} else {
				    pack_cost[j][case1][case2] = "0";
				}
			    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
				if ("골판지".equals(pack_type[j][case1][case2])) {
				    pack_cost[j][case1][case2] = Double.toString(1400 / StringUtil.nullDouble(out_pack[j][case1][case2]));
				}
				if (!"".equals(StringUtil.checkNull(pack_cost[j][case1][case2]))) {
				    pack_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(pack_cost[j][case1][case2]));
				} else {
				    pack_cost[j][case1][case2] = "0";
				}
			    } else {
				pack_cost[j][case1][case2] = "0";
			    }
			}

			// 직접경비
			if (!"재료도금".equals(plating_type[j][case1][case2])
			        && !"".equals(StringUtil.checkNull(plating_cost[j][case1][case2]))) {
			    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
				    + StringUtil.nullDouble(ma_depr[j][case1][case2]) + StringUtil.nullDouble(tabalu[j][case1][case2])
				    + StringUtil.nullDouble(m_upkeep[j][case1][case2]) + StringUtil.nullDouble(pack_cost[j][case1][case2])
				    + StringUtil.nullDouble(plating_cost[j][case1][case2]));
			} else {
			    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(jun_cost[j][case1][case2])
				    + StringUtil.nullDouble(ma_depr[j][case1][case2]) + StringUtil.nullDouble(tabalu[j][case1][case2])
				    + StringUtil.nullDouble(m_upkeep[j][case1][case2]) + StringUtil.nullDouble(pack_cost[j][case1][case2]));
			}

			if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
			    total_expense[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
				    + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
			} else {
			    total_expense[j][case1][case2] = total_expense[j][case1][case2];
			}

			// 간접경비
			// System.out.println("TML 조립 st_overhead : " +st_overhead);
			// System.out.println("TML 조립 pl_overhead : " +pl_overhead[j][case1][case2]);
			overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(total_expense[j][case1][case2])
			        * StringUtil.nullDouble(st_overhead));
			if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
			    overhead[j][case1][case2] = Double.toString(StringUtil.nullDouble(overhead[j][case1][case2])
				    + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
			}

			// 기타경비
			if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
			    gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
			} else {
			    gita_cost[j][case1][case2] = "0";
			}

			// 제조경비 plating_cost -> 도금비
			// System.out.println("TML 조립 total_expense : " +total_expense[j][case1][case2]);
			// System.out.println("TML 조립 overhead : " +overhead[j][case1][case2]);
			// System.out.println("TML 조립 gita_cost : " +gita_cost[j][case1][case2]);
			// System.out.println("TML 조립 usage : " +usage[j][case1][case2]);
			common_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(total_expense[j][case1][case2])
			        + StringUtil.nullDouble(overhead[j][case1][case2]) + StringUtil.nullDouble(gita_cost[j][case1][case2]))
			        * StringUtil.nullDouble(usage[j][case1][case2]));
			if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
			    common_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(common_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
			} else {
			    common_cost[j][case1][case2] = common_cost[j][case1][case2];
			}
			assy_common_cost[j] = common_cost[j][case1][case2];

			/*********************************/
			/************* 금형벌수 *************/
			/*********************************/
			mold_su_1 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			mold_su_2 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			mold_su_3 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			mold_su_4 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
			    mold_su_5 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
			    mold_su_6 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
			    mold_su_7 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
			    mold_su_8 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(output[j][case1][case2]) * 20 * 25)) + 0.9) - 1);
			}

			if (StringUtil.nullDouble(mold_su_1) > StringUtil.nullDouble(mold_su_2)) {
			    mold_su = mold_su_1;
			} else {
			    mold_su = mold_su_2;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_3;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_3;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_4)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_4;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_5)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_5;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_6)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_6;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_7)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_7;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_8)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_8;
			}

			if (StringUtil.nullDouble(mold_su) == 0 || StringUtil.nullDouble(mold_su) < 0) {
			    m_su[j][case1][case2] = "1";
			} else {
			    m_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(mold_su) + 1);
			}

			// 6720
			/*********************************/
			/************** 감가비 **************/
			/*********************************/
			// 금형감가비
			// 기준수량

			if ("개조".equals(part_type_1[j][case1][case2])) {
			    // 일반감가기준(50000000)

			    s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
				    * Integer.parseInt(m_su[j][case1][case2]));

			    // 판매량감가기준(6년치총수량)
			    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1)) {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
				        + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
				        + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
				        + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
				            .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } else {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
				        + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
				        + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
				        + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
				            .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    }

			    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * Integer.parseInt(cavity[j][case1][case2]) * Integer
				    .parseInt(m_su[j][case1][case2]))) {
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
			    } else {
				if ("일반".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				} else if ("판매".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				} else {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "판매";
				}
			    }
			} else if ("공용".equals(part_type_1[j][case1][case2])) {
			    s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
				    * Integer.parseInt(m_su[j][case1][case2]));
			    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
			    mold_type[j][case1][case2] = "일반";
			    if ("".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
			    } else {
				mold_cost[j][case1][case2] = "2500";
			    }
			} else {
			    // 일반감가기준(50000000)
			    try {
				s_avg_su[j][case1][case2] = Integer.toString(50000000 * Integer.parseInt(cavity[j][case1][case2])
				        * Integer.parseInt(m_su[j][case1][case2]));
			    } catch (Exception e) {
				s_avg_su[j][case1][case2] = Double.toString(50000000 * StringUtil.nullDouble(cavity[j][case1][case2])
				        * StringUtil.nullDouble(m_su[j][case1][case2]));
			    }

			    // 판매량감가기준(6년치총수량)
			    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
				        + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
				        + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
				        + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
				            .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } else {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
				        + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
				        + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
				        + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
				            .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    }
			    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (50000000 * StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil
				    .nullDouble(m_su[j][case1][case2]))) {
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
			    } else {
				if ("일반".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				} else if ("판매".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				} else {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "판매";
				}
			    }
			}

			if ("감가".equals(mold_c_type[j][case1][case2])) { // 금형비의 경우 지급조건이 감가일때만 계산
			    // 일반감가기준(50000000)
			    s_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
				    * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
				    / StringUtil.nullDouble(s_avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));
			    // 판매량감가기준(6년치총수량)
			    g_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
				    * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
				    / StringUtil.nullDouble(avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));
			} else {
			    s_depr_cost[j][case1][case2] = "0";
			    g_depr_cost[j][case1][case2] = "0";
			}

			// 기타투자비감가
			if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
			    etc_su = "0";
			    etc_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
				    + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
				    + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
				    + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
				    * StringUtil.nullDouble(usage[j][case1][case2]));
			    etc_depr[j][case1][case2] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
				    / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));

			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(etc_depr[j][case1][case2]));
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(etc_depr[j][case1][case2]));

			} else {
			    etc_depr[j][case1][case2] = "0";
			}

			if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
			    if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
				etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
				        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    } else {
				etc_depr[j][case1][case2] = pl_etc_depr[j][case1][case2];
			    }
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			}

			if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			} else {
			    g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
			    s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			}

			if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
			} else {
			    g_depr_cost[j][case1][case2] = g_depr_cost[j][case1][case2];
			    s_depr_cost[j][case1][case2] = s_depr_cost[j][case1][case2];
			}

			assy_g_depr_cost[j] = g_depr_cost[j][case1][case2];

			/*********************************/
			/************** 물류비 **************/
			/*********************************/
			if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
			    dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
			} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
			        && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
			    if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
				distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
			    } else if ("생산4".equals(start_pro[j])) {
				distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
			    }

			    dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
				    / StringUtil.nullDouble(out_pack[j][case1][case2]));
			    dis_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(dis_cost[j][case1][case2]));
			} else {
			    dis_cost[j][case1][case2] = "0";
			}

			/*********************************/
			/************** 관리비 **************/
			/*********************************/
			// 가공비
			process_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(labor_cost[j][case1][case2])
			        + StringUtil.nullDouble(common_cost[j][case1][case2]));
			if (!"ok".equals(assy_in[j]) && "중국".equals(pro_1[j][case1][case2])) {
			    tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(process_cost[j][case1][case2]) * 0.08);
			} else {
			    tariff[j][case1][case2] = "0";
			}

			if (!"".equals(StringUtil.checkNull(pl_tariff[j][case1][case2]))) {
			    tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(tariff[j][case1][case2])
				    + StringUtil.nullDouble(pl_tariff[j][case1][case2]));
			} else {
			    tariff[j][case1][case2] = tariff[j][case1][case2];
			}

			if ("선생산".equals(mix_group_1[j][1][0])) {
			    m_total = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(g_actual_cost[j][1][0]));
			} else {
			    m_total = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				    + StringUtil.nullDouble(m_total_cost[j][1][0]));
			}

			// 재료관리비
			jae_cost[j][case1][case2] = String.format("%.4f",
			        (StringUtil.nullDouble(m_total) * StringUtil.nullDouble(st_jae_cost)));
			if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
			    jae_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
			} else {
			    jae_cost[j][case1][case2] = jae_cost[j][case1][case2];
			}

			// 일반관리비
			ge_cost[j][case1][case2] = String.format("%.4f",
			        (StringUtil.nullDouble(process_cost[j][case1][case2]) * StringUtil.nullDouble(st_ge_cost)));
			if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
			    ge_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ge_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
			} else {
			    ge_cost[j][case1][case2] = ge_cost[j][case1][case2];
			}

			// R&D비
			s_rnd_cost[j][case1][case2] = Double
			        .toString(((StringUtil.nullDouble(m_total) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(process_cost[j][case1][case2]) / StringUtil
			                        .nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])) + (StringUtil
			                .nullDouble(s_depr_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
			                * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));
			g_rnd_cost[j][case1][case2] = Double
			        .toString(((StringUtil.nullDouble(m_total) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(process_cost[j][case1][case2]) / StringUtil
			                        .nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(jae_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2]))
			                + (StringUtil.nullDouble(ge_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])) + (StringUtil
			                .nullDouble(g_depr_cost[j][case1][case2]) / StringUtil.nullDouble(usage[j][case1][case2])))
			                * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));

			// System.out.println("TML 조립 m_total : "+m_total);
			// System.out.println("TML 조립 usage : "+usage[j][case1][case2]);
			// System.out.println("TML 조립 process_cost : "+process_cost[j][case1][case2]);
			// System.out.println("TML 조립 jae_cost : "+jae_cost[j][case1][case2]);
			// System.out.println("TML 조립 ge_cost : "+ge_cost[j][case1][case2]);
			// System.out.println("TML 조립 g_depr_cost : "+g_depr_cost[j][case1][case2]);
			// System.out.println("TML 조립 st_rnd_cost : "+st_rnd_cost);

			if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
			    s_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
			    g_rnd_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
			} else {
			    s_rnd_cost[j][case1][case2] = s_rnd_cost[j][case1][case2];
			    g_rnd_cost[j][case1][case2] = g_rnd_cost[j][case1][case2];
			}
			// System.out.println("TML 조립 g_actual_cost : "+g_actual_cost[j][1][0]);
			// System.out.println("TML 조립 jae_cost : "+jae_cost[j][case1][case2]);
			// 총관리비
			// 복합금형의 경우 Insert관리비 산출식과 동일 / 선생산원가 * 0.01(loss비율) 10.07.22 변경
			TML_loss = Double.toString(StringUtil.nullDouble(g_actual_cost[j][1][0]) * 0.01
			        * StringUtil.nullDouble(usage[j][case1][case2]));
			ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(jae_cost[j][case1][case2])
			        + StringUtil.nullDouble(ge_cost[j][case1][case2]) + StringUtil.nullDouble(dis_cost[j][case1][case2])
			        + StringUtil.nullDouble(g_rnd_cost[j][case1][case2]) + StringUtil.nullDouble(tariff[j][case1][case2])
			        + StringUtil.nullDouble(TML_loss));
			// System.out.println("TML 조립 TML_loss : "+TML_loss);
			// System.out.println("TML 조립 qu_cost : "+qu_cost[j][case1][case2]);
			if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
			    ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
				    + StringUtil.nullDouble(qu_cost[j][case1][case2]));
			} else {
			    qu_cost[j][case1][case2] = "0";
			}
			// System.out.println("TML 조립 ad_cost : "+ad_cost[j][case1][case2]);

			/*********************************/
			/************** 총원가 **************/
			/*********************************/
			s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total)
			        + StringUtil.nullDouble(labor_cost[j][case1][case2]) + StringUtil.nullDouble(common_cost[j][case1][case2])
			        + StringUtil.nullDouble(ad_cost[j][case1][case2]) + StringUtil.nullDouble(s_depr_cost[j][case1][case2]));
			g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total)
			        + StringUtil.nullDouble(labor_cost[j][case1][case2]) + StringUtil.nullDouble(common_cost[j][case1][case2])
			        + StringUtil.nullDouble(ad_cost[j][case1][case2]) + StringUtil.nullDouble(g_depr_cost[j][case1][case2]));
			if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
			    s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_actual_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			    g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_actual_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			} else {
			    s_actual_cost[j][case1][case2] = s_actual_cost[j][case1][case2];
			    g_actual_cost[j][case1][case2] = g_actual_cost[j][case1][case2];
			}
			// System.out.println("총원가 찍기 12: "+g_actual_cost[j][case1][case2]);
			/*********************************/
			/************** 로열티 **************/
			/*********************************/
			if (!"".equals(StringUtil.checkNull(assy_in[j]))) {
			    if ("1".equals(royalty[j])) {
				s_roy_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(m_total)
				        + StringUtil.nullDouble(labor_cost[j][case1][case2])
				        + StringUtil.nullDouble(common_cost[j][case1][case2])
				        + StringUtil.nullDouble(jae_cost[j][case1][case2])
				        + StringUtil.nullDouble(ge_cost[j][case1][case2])
				        + StringUtil.nullDouble(s_rnd_cost[j][case1][case2])
				        + StringUtil.nullDouble(tariff[j][case1][case2])
				        + StringUtil.nullDouble(s_depr_cost[j][case1][case2]) + StringUtil
				            .nullDouble(qu_cost[j][case1][case2])) * StringUtil.nullDouble(st_royalty));
				g_roy_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(m_total)
				        + StringUtil.nullDouble(labor_cost[j][case1][case2])
				        + StringUtil.nullDouble(common_cost[j][case1][case2])
				        + StringUtil.nullDouble(jae_cost[j][case1][case2])
				        + StringUtil.nullDouble(ge_cost[j][case1][case2])
				        + StringUtil.nullDouble(g_rnd_cost[j][case1][case2])
				        + StringUtil.nullDouble(tariff[j][case1][case2])
				        + StringUtil.nullDouble(g_depr_cost[j][case1][case2]) + StringUtil
				            .nullDouble(qu_cost[j][case1][case2])) * StringUtil.nullDouble(st_royalty));

				if (!"".equals(StringUtil.checkNull(pl_royalty_cost[j][case1][case2]))) {
				    s_roy_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_roy_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
				    g_roy_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_roy_cost[j][case1][case2])
					    + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
				} else {
				    s_roy_cost[j][case1][case2] = s_roy_cost[j][case1][case2];
				    g_roy_cost[j][case1][case2] = g_roy_cost[j][case1][case2];
				}

				s_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_actual_cost[j][case1][case2])
				        + StringUtil.nullDouble(s_roy_cost[j][case1][case2]));
				g_actual_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_actual_cost[j][case1][case2])
				        + StringUtil.nullDouble(g_roy_cost[j][case1][case2]));

				ad_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(ad_cost[j][case1][case2])
				        + StringUtil.nullDouble(g_roy_cost[j][case1][case2]));
			    }
			}

			assy_g_actual_cost[j] = g_actual_cost[j][case1][case2];
			assy_ad_cost[j] = ad_cost[j][case1][case2];
			sum_s_actual_cost[j][0][0] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + StringUtil.nullDouble(assy_labor_cost[j]) + StringUtil.nullDouble(assy_common_cost[j])
			        + StringUtil.nullDouble(assy_ad_cost[j]) + StringUtil.nullDouble(assy_s_depr_cost[j]));
			sum_g_actual_cost[j][0][0] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + StringUtil.nullDouble(assy_labor_cost[j]) + StringUtil.nullDouble(assy_common_cost[j])
			        + StringUtil.nullDouble(assy_ad_cost[j]) + StringUtil.nullDouble(assy_g_depr_cost[j]));

			// 6997
			/*********************************/
			/************** 수익률 **************/
			/*********************************/
			if (!"".equals(StringUtil.checkNull(ket_cost[j])) && !"0".equals(StringUtil.checkNullZero(ket_cost[j]))) {
			    s_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				    .nullDouble(s_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
			    g_earn_rate[j][case1][case2] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
				    .nullDouble(g_actual_cost[j][case1][case2])) / StringUtil.nullDouble(ket_cost[j]));
			} else {
			    if ("0".equals(target_cost[j])) {
				s_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7) - StringUtil
				                .nullDouble(s_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / 0.7));
				g_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7) - StringUtil
				                .nullDouble(g_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / 0.7));
			    } else {
				target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
				s_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
				                .nullDouble(target_rate))) - StringUtil.nullDouble(s_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(s_actual_cost[j][case1][case2]) / (1 - StringUtil
				                        .nullDouble(target_rate))));
				g_earn_rate[j][case1][case2] = Double
				        .toString(((StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
				                .nullDouble(target_rate))) - StringUtil.nullDouble(g_actual_cost[j][case1][case2]))
				                / (StringUtil.nullDouble(g_actual_cost[j][case1][case2]) / (1 - StringUtil
				                        .nullDouble(target_rate))));
			    }
			}
			assy_g_earn_rate[j] = g_earn_rate[j][case1][case2];
		    } // make_type : 외주 if end
		} else {

		    if ("assy".equals(StringUtil.checkNull(pro_type[j][case1][case2]))
			    || "Insert".equals(StringUtil.checkNull(pro_type[j][case1][case2]))) {
			ArrayList accExStandardList = null;
			try {
			    conn = DBUtil.getConnection();
			    costAccDao = new CostAccDao(conn);
			    accExStandardList = costAccDao.getAccExStandardList3(pro_1[j][case1][case2], pro_type[j][case1][case2],
				    method_type[j][case1][case2]);
			} catch (Exception e) {
			    e.printStackTrace();
			} finally {
			    DBUtil.close(conn);
			}

			Hashtable accExStandardMap = null;
			try {
			    accExStandardMap = (Hashtable) accExStandardList.get(0);
			    st_eff_value_in = StringUtil.checkNull((String) accExStandardMap.get("st_eff_value")); // 효율
			    st_person_in = StringUtil.checkNull((String) accExStandardMap.get("st_person")); // 표준인원
			    st_rate = StringUtil.checkNull((String) accExStandardMap.get("st_rate")); // 임율
			    st_jun_cost = StringUtil.checkNull((String) accExStandardMap.get("st_jun_cost")); // 전력비
			    st_ma_depr = StringUtil.checkNull((String) accExStandardMap.get("st_ma_depr")); // 기계감가
			    st_tabalu = StringUtil.checkNull((String) accExStandardMap.get("st_tabalu")); // 타발유
			    st_overhead = StringUtil.checkNull((String) accExStandardMap.get("st_overhead")); // 간접경비
			    st_ge_cost = StringUtil.checkNull((String) accExStandardMap.get("st_ge_cost")); // 일반관리비율
			    st_jae_cost = StringUtil.checkNull((String) accExStandardMap.get("st_jae_cost")); // 재료관리비율
			    st_rnd_cost = StringUtil.checkNull((String) accExStandardMap.get("st_rnd_cost")); // R&D비율
			    st_royalty = StringUtil.checkNull((String) accExStandardMap.get("st_royalty")); // 로얄티비율
			} catch (Exception e) {
			    e.printStackTrace();
			}

			st_eff_value[j][case1][case2] = st_eff_value_in;// 효율
			st_person[j][case1][case2] = st_person_in;// 표준인원
			if ("기타".equals(StringUtil.checkNull(pro_level[j][case1][case2]))) { // 작업인원
			    st_person_in = pro_level_txt[j][case1][case2];
			} else if (!"0".equals(StringUtil.checkNullZero((st_person_in)))) {
			    // st_person_in = Integer.toString(1 / Integer.parseInt(st_person_in));
			    temp_1 = new BigDecimal("1");
			    temp_2 = new BigDecimal(StringUtil.checkNull(st_person_in));
			    // st_person_in = Double.toString(temp_1.divide(temp_2).doubleValue()) ;
			    st_person_in = Double.toString(temp_1.divide(temp_2, MathContext.DECIMAL32).doubleValue());
			} else {
			    st_person_in = "1";
			}

			// 7056
			if (!"".equals(StringUtil.checkNull(eff_value[j][case1][case2]))) {
			    st_eff_value_in = eff_value[j][case1][case2];
			    st_eff_value[j][case1][case2] = eff_value[j][case1][case2];
			} else {
			    eff_value[j][case1][case2] = st_eff_value_in;
			}

			// 외주단가 구하기
			if ("외주".equals(make_type[j][case1][case2]) || "임가공".equals(type_1[j][case1][case2])) {
			    if (!"".equals(StringUtil.checkNull(type_cost[j][case1][case2]))) {
				type_cost[j][case1][case2] = type_cost[j][case1][case2];
			    } else {
				if ("단순".equals(method_type[j][case1][case2]) || "JIG이용".equals(method_type[j][case1][case2])) {
				    if ("Tray".equals(pack_type[j][case1][case2])) {
					type_cost[j][case1][case2] = String.format(
					        "%.2f",
					        ((StringUtil.nullDouble(st_rate) * StringUtil.nullDouble(st_person_in))
					                / (3600 / StringUtil.nullDouble(spm[j][case1][case2]) * StringUtil
					                        .nullDouble(st_eff_value_in)) + (19.4 / StringUtil
					                .nullDouble(out_pack[j][case1][case2])) * 1.03)
					                + (StringUtil.nullDouble(st_rate) / (3600 / 1.5 * StringUtil
					                        .nullDouble(st_eff_value_in))));
				    } else {
					type_cost[j][case1][case2] = String.format(
					        "%.2f",
					        (StringUtil.nullDouble(st_rate) * StringUtil.nullDouble(st_person_in))
					                / (3600 / StringUtil.nullDouble(spm[j][case1][case2]) * StringUtil
					                        .nullDouble(st_eff_value_in))
					                + (19.2 / StringUtil.nullDouble(in_pack[j][case1][case2]) + 19.4 / StringUtil
					                        .nullDouble(out_pack[j][case1][case2])) * 1.03 + 100
					                / StringUtil.nullDouble(in_pack[j][case1][case2]));
				    }

				    // 구매팀 요청에 의해 2천개 이하에 대한 외주물류비 반영 2011.05.03
				    if (StringUtil.nullDouble(out_pack[j][case1][case2]) < 2001) {
					type_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(type_cost[j][case1][case2])
					        + 460 / StringUtil.nullDouble(out_pack[j][case1][case2]));
				    }
				} else {
				    type_cost[j][case1][case2] = String.format(
					    "%.2f",
					    (StringUtil.nullDouble(st_rate) * StringUtil.nullDouble(st_person_in))
					            / (3600 / StringUtil.nullDouble(spm[j][case1][case2]) * StringUtil
					                    .nullDouble(st_eff_value_in)));
				}
			    }
			}

			// 7087
			if ("Insert".equals(pro_type[j][case1][case2])) {
			    if ("중국".equals(pro_1[j][case1][case2])) {
				unitcost_si[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost_si[j][case1][case2]) * 1.05);
			    }

			    scrap_cost[j][case1][case2] = "0";
			    if (!"".equals(StringUtil.checkNull(pl_scrap_cost[j][case1][case2]))) {
				scrap_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(scrap_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_scrap_cost[j][case1][case2]));
			    }

			    if (!"".equals(StringUtil.checkNull(recycle[j][case1][case2])) && !"0".equals(recycle[j][case1][case2])) {
				rec_weight = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
				        * (StringUtil.nullDouble(recycle[j][case1][case2]) / 100));

				if (StringUtil.nullDouble(rec_weight) < StringUtil.nullDouble(String.format("%.4f",
				        StringUtil.nullDouble(h_scrap[j][case1][case2])))) {
				    // 원재료비(재생반영)
				    meterial_cost[j][case1][case2] = Double
					    .toString((StringUtil.nullDouble(t_weight[j][case1][case2]) - StringUtil.nullDouble(rec_weight))
					            * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				} else {
				    // 원재료비(재생반영)
				    meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(h_weight[j][case1][case2])
					    * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				}
			    } else {
				// 원재료비
				// System.out.println("조립(Insert) t_weight : "+t_weight[j][case1][case2]);
				meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(t_weight[j][case1][case2])
				        * StringUtil.nullDouble(unitcost_si[j][case1][case2]));
				recycle[j][case1][case2] = "0";
			    }

			    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				meterial_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
			    }

			    // loss비
			    temp_1 = new BigDecimal(meterial_cost[j][case1][case2]);
			    temp_2 = new BigDecimal("0.03");
			    loss[j][case1][case2] = Double.toString(temp_1.multiply(temp_2).doubleValue());
			    // loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(meterial_cost[j][case1][case2]) * 0.03);
			    if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
				loss[j][case1][case2] = Double.toString(StringUtil.nullDouble(loss[j][case1][case2])
				        + StringUtil.nullDouble(pl_loss[j][case1][case2]));
			    }

			    // 총재료비
			    m_total_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(meterial_cost[j][case1][case2])
				    + StringUtil.nullDouble(loss[j][case1][case2]) - StringUtil.nullDouble(scrap_cost[j][case1][case2]))
				    * StringUtil.nullDouble(usage[j][case1][case2]));

			    // System.out.println("====== 조립(Insert) meterial_cost====> "+meterial_cost[j][case1][case2] );
			    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				m_total_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
				        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
			    }

			    if (StringUtil.nullDouble(cavity[j][case1][case2]) < 5) {
				m_upkeep[j][case1][case2] = "1.5";
			    } else if (StringUtil.nullDouble(cavity[j][case1][case2]) < 9) {
				m_upkeep[j][case1][case2] = "1";
			    } else if (StringUtil.nullDouble(cavity[j][case1][case2]) > 15) {
				m_upkeep[j][case1][case2] = "0.5";
			    }

			    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
				m_upkeep[j][case1][case2] = Double.toString(StringUtil.nullDouble(m_upkeep[j][case1][case2])
				        + StringUtil.nullDouble(pl_m_upkeep[j][case1][case2]));
			    }
			} else {
			    m_total_cost[j][case1][case2] = "0";

			    // 금형유지비
			    if (!"".equals(StringUtil.checkNull(pl_m_upkeep[j][case1][case2]))) {
				m_upkeep[j][case1][case2] = pl_m_upkeep[j][case1][case2];
			    }
			}

			// 7155
			/*********************************/
			/*************** 재료비 *************/
			/*********************************/

			if (!"Insert".equals(pro_type[j][case1][case2])) {
			    if (!"".equals(StringUtil.checkNull(pl_meterial_cost[j][case1][case2]))) {
				s_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(s_assy_m_total_cost[j])
				        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
				g_assy_m_total_cost[j] = Double.toString(StringUtil.nullDouble(g_assy_m_total_cost[j])
				        + StringUtil.nullDouble(pl_meterial_cost[j][case1][case2]));
			    }
			}

			s_assy_loss[j] = Double.toString(StringUtil.nullDouble(s_assy_m_total_cost[j])
			        * StringUtil.nullDouble(usage[j][case1][case2]) * 0.01);
			g_assy_loss[j] = Double.toString(StringUtil.nullDouble(g_assy_m_total_cost[j])
			        * StringUtil.nullDouble(usage[j][case1][case2]) * 0.01);

			if (!"".equals(StringUtil.checkNull(pl_loss[j][case1][case2]))) {
			    s_assy_loss[j] = Double.toString(StringUtil.nullDouble(s_assy_loss[j])
				    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
			    g_assy_loss[j] = Double.toString(StringUtil.nullDouble(g_assy_loss[j])
				    + StringUtil.nullDouble(pl_loss[j][case1][case2]));
			}
			// 7173
			assy_scrap_cost[j] = "0";

			// System.out.println("g_assy_meterial_cost[j] 계산..."+g_assy_meterial_cost[j]);
			// System.out.println("m_total_cost[j] 계산..."+m_total_cost[j][case1][case2]);
			// System.out.println("g_assy_m_total_cost[j] 계산..."+g_assy_m_total_cost[j]);

			s_assy_meterial_cost[j] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + (StringUtil.nullDouble(s_assy_m_total_cost[j]) - StringUtil.nullDouble(assy_scrap_cost[j]) + StringUtil
			                .nullDouble(s_assy_loss[j])));
			g_assy_meterial_cost[j] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + (StringUtil.nullDouble(g_assy_m_total_cost[j]) - StringUtil.nullDouble(assy_scrap_cost[j]) + StringUtil
			                .nullDouble(g_assy_loss[j])));
			// System.out.println("g_assy_meterial_cost[j] 계산.222.."+g_assy_meterial_cost[j]);
			if (!"Insert".equals(pro_type[j][case1][case2])) {
			    if (!"".equals(StringUtil.checkNull(pl_m_total_cost[j][case1][case2]))) {
				s_assy_meterial_cost[j] = Double.toString(StringUtil.nullDouble(s_assy_meterial_cost[j])
				        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
				g_assy_meterial_cost[j] = Double.toString(StringUtil.nullDouble(g_assy_meterial_cost[j])
				        + StringUtil.nullDouble(pl_m_total_cost[j][case1][case2]));
			    } else {
				s_assy_meterial_cost[j] = s_assy_meterial_cost[j];
				g_assy_meterial_cost[j] = g_assy_meterial_cost[j];
			    }
			}

			// loss비-판매
			loss[j][case1][case2] = g_assy_loss[j];
			// 스크랩판매비
			scrap_cost[j][case1][case2] = assy_scrap_cost[j];

			/*********************************/
			/*************** 노무비 *************/
			/*********************************/
			if (!"생산2".equals(pro_1[j][case1][case2]) && !"생산3".equals(pro_1[j][case1][case2])
			        && !"중국".equals(pro_1[j][case1][case2]) && !"Insert".equals(method_type[j][case1][case2])) {
			    st_eff_value_in = "1";
			    st_rate = "0";
			    st_person_in = "0";
			}

			// 7200
			if ("외주".equals(make_type[j][case1][case2])
			        && (!"Insert".equals(method_type[j][case1][case2]) && !"자동".equals(method_type[j][case1][case2]))) {
			    // 시간당생산량
			    assy_output[j] = "0";
			    if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
				assy_output[j] = Double.toString(StringUtil.nullDouble(assy_output[j])
				        + StringUtil.nullDouble(pl_output[j][case1][case2]));
			    }

			    // 임율
			    assy_rate[j] = "0";
			    // 노무비
			    assy_labor_cost[j] = "0";
			} else if ("외주".equals(make_type[j][case1][case2]) && "Insert".equals(method_type[j][case1][case2])) {
			    if (!"".equals(StringUtil.checkNull(cavity[j][case1][case2]))) {
				// 시간당생산량
				assy_output[j] = Double.toString(3600 / StringUtil.nullDouble(spm[j][case1][case2])
				        * StringUtil.nullDouble(st_eff_value_in) * StringUtil.nullDouble(cavity[j][case1][case2]));
			    } else {
				cavity[j][case1][case2] = "1";
				// 시간당생산량
				assy_output[j] = Double.toString(3600 / StringUtil.nullDouble(spm[j][case1][case2])
				        * StringUtil.nullDouble(st_eff_value_in) * StringUtil.nullDouble(cavity[j][case1][case2]));
			    }

			    // 7216
			    if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
				assy_output[j] = Double.toString(StringUtil.nullDouble(assy_output[j])
				        + StringUtil.nullDouble(pl_output[j][case1][case2]));
			    }

			    // 임율
			    assy_rate[j] = "0";
			    if (!"".equals(StringUtil.checkNull(pl_rate[j][case1][case2]))) {
				assy_rate[j] = Double.toString(StringUtil.nullDouble(assy_rate[j])
				        + StringUtil.nullDouble(pl_rate[j][case1][case2]));
			    }

			    // 노무비
			    assy_labor_cost[j] = "0";
			}
			// 7227
			if (!"".equals(StringUtil.checkNull(cavity[j][case1][case2]))) {
			    // 시간당생산량
			    assy_output[j] = Double.toString(3600 / StringUtil.nullDouble(spm[j][case1][case2])
				    * StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in));
			} else {
			    cavity[j][case1][case2] = "1";
			    // 시간당생산량
			    assy_output[j] = Double.toString(3600 / StringUtil.nullDouble(spm[j][case1][case2])
				    * StringUtil.nullDouble(cavity[j][case1][case2]) * StringUtil.nullDouble(st_eff_value_in));
			}

			if (!"".equals(StringUtil.checkNull(pl_output[j][case1][case2]))) {
			    assy_output[j] = Double.toString(StringUtil.nullDouble(assy_output[j])
				    + StringUtil.nullDouble(pl_output[j][case1][case2]));
			}

			// 임율
			assy_rate[j] = Double.toString(StringUtil.nullDouble(st_rate) * StringUtil.nullDouble(st_person_in));

			if (!"".equals(StringUtil.checkNull(pl_rate[j][case1][case2]))) {
			    assy_rate[j] = Double.toString(StringUtil.nullDouble(assy_rate[j])
				    + StringUtil.nullDouble(pl_rate[j][case1][case2]));
			}

			// 노무비
			assy_labor_cost[j] = Double.toString(StringUtil.nullDouble(assy_rate[j]) / StringUtil.nullDouble(assy_output[j]));
			// System.out.println("여기 노무비는 찍을지 !!!???");

		    }
		    // System.out.println("type_1[j][case1][case2] : "+type_1[j][case1][case2]);
		    if ("검사".equals(StringUtil.checkNull(type_1[j][case1][case2]))
			    || "포장".equals(StringUtil.checkNull(type_1[j][case1][case2]))) {
			if (!"".equals(StringUtil.checkNull(type_cost[j][case1][case2]))) {
			    // 노무비
			    assy_labor_cost[j] = Double
				    .toString((StringUtil.nullDouble(assy_rate[j]) / StringUtil.nullDouble(assy_output[j]))
				            + (StringUtil.nullDouble(st_rate) / (3600 / StringUtil.nullDouble(type_cost[j][case1][case2]) * StringUtil
				                    .nullDouble(st_eff_value_in))) * StringUtil.nullDouble(usage[j][case1][case2]));
			}
		    }// type1 : 검사 || 포장
		    if (!"".equals(StringUtil.checkNull(pl_labor_cost[j][case1][case2]))) {
			assy_labor_cost[j] = Double.toString(StringUtil.nullDouble(assy_labor_cost[j])
			        + StringUtil.nullDouble(pl_labor_cost[j][case1][case2]));
		    } else {
			assy_labor_cost[j] = assy_labor_cost[j];
		    }

		    // 시간당생산량
		    output[j][case1][case2] = assy_output[j];
		    // 임율
		    rate[j][case1][case2] = assy_rate[j];
		    labor_cost[j][case1][case2] = assy_labor_cost[j];
		    // System.out.println("==2 labor_cost : "+labor_cost[j][case1][case2]);

		    /*********************************/
		    /************* 제조경비 *************/
		    /*********************************/

		    // 포장비임의값
		    if (!"".equals(StringUtil.checkNull(pack_cost[j][0][0]))) {
			assy_pack_cost[j] = String.format("%.2f", (StringUtil.nullDouble(pack_cost[j][0][0])));
		    } else {
			if (!"".equals(StringUtil.checkNull(in_pack[j][case1][case2]))
			        && !"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
			    if ("Reel".equals(pack_type[j][case1][case2])) {
				assy_pack_cost[j] = Double.toString((1072 / Double.parseDouble(in_pack[j][case1][case2]))
				        + (1277 / Double.parseDouble(out_pack[j][case1][case2])));
			    } else if ("Tray".equals(pack_type[j][case1][case2])) { // 450에서 480으로 변경 2011.05.03
				assy_pack_cost[j] = Double.toString((485 / Double.parseDouble(in_pack[j][case1][case2]))
				        + (1056 / Double.parseDouble(out_pack[j][case1][case2])));
			    } else if ("비닐".equals(pack_type[j][case1][case2])) {
				assy_pack_cost[j] = Double.toString((206 / Double.parseDouble(in_pack[j][case1][case2]))
				        + (764 / Double.parseDouble(out_pack[j][case1][case2])));
			    } else if ("골판지".equals(pack_type[j][case1][case2])) {
				assy_pack_cost[j] = Double.toString(1400 / Double.parseDouble(out_pack[j][case1][case2]));
			    }
			    if (!"".equals(StringUtil.checkNull(assy_pack_cost[j]))) {
				assy_pack_cost[j] = String.format("%.2f", StringUtil.nullDouble(assy_pack_cost[j]));
			    } else {
				assy_pack_cost[j] = "0";
			    }

			} else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))) {
			    if ("골판지".equals(pack_type[j][case1][case2])) {
				assy_pack_cost[j] = Double.toString(1400 / Integer.parseInt(out_pack[j][case1][case2]));
			    }

			    if ("".equals(StringUtil.checkNull(assy_pack_cost[j]))) {
				assy_pack_cost[j] = String.format("%.2f", StringUtil.nullDouble(assy_pack_cost[j]));
			    } else {
				assy_pack_cost[j] = "0";
			    }
			} else {
			    assy_pack_cost[j] = "0";
			}
		    }
		    // 포장비
		    pack_cost[j][case1][case2] = assy_pack_cost[j];

		    // 기계감가
		    if (!"".equals(StringUtil.checkNull(pl_ma_depr[j][case1][case2]))) {
			ma_depr[j][case1][case2] = pl_ma_depr[j][case1][case2];
		    } else {
			ma_depr[j][case1][case2] = "0";
		    }

		    if ("외주".equals(make_type[j][case1][case2])
			    && (!"Insert".equals(method_type[j][case1][case2]) && !"자동".equals(method_type[j][case1][case2]))) {
			// 전력비
			assy_jun_cost[j] = "0";

			// System.out.println("pl_jun_cost ===>"+pl_jun_cost[j][case1][case2]);

			if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
			    assy_jun_cost[j] = Double.toString(StringUtil.nullDouble(assy_jun_cost[j])
				    + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
			}
			// 직접경비
			assy_total_expense[j] = Double.toString(StringUtil.nullDouble(assy_jun_cost[j])
			        + StringUtil.nullDouble(type_cost[j][case1][case2]) + StringUtil.nullDouble(assy_pack_cost[j])
			        + StringUtil.nullDouble(ma_depr[j][case1][case2]));

		    } else if (("중국".equals(pro_1) && "단순".equals(method_type[j][case1][case2]))
			    || ("생산".equals(pro_1[j][case1][case2]) && "단순".equals(method_type[j][case1][case2]))) {
			// 전력비
			assy_jun_cost[j] = "0";
			if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
			    assy_jun_cost[j] = Double.toString(StringUtil.nullDouble(assy_jun_cost[j])
				    + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
			}
			// 직접경비
			assy_total_expense[j] = Double.toString(StringUtil.nullDouble(assy_jun_cost[j])
			        + StringUtil.nullDouble(assy_pack_cost[j]) + StringUtil.nullDouble(ma_depr[j][case1][case2]));
		    } else {
			// 전력비
			assy_jun_cost[j] = String
			        .format("%.4f", StringUtil.nullDouble(st_jun_cost) / StringUtil.nullDouble(assy_output[j]));
			if (!"".equals(StringUtil.checkNull(pl_jun_cost[j][case1][case2]))) {
			    assy_jun_cost[j] = Double.toString(StringUtil.nullDouble(assy_jun_cost[j])
				    + StringUtil.nullDouble(pl_jun_cost[j][case1][case2]));
			}
			// 직접경비
			if (!"".equals(StringUtil.checkNull(pro_type[j][case1][case2]))
			        && (!"검사".equals(type_1[j][case1][case2]) && !"포장".equals(type_1[j][case1][case2]))) {
			    assy_total_expense[j] = Double.toString(StringUtil.nullDouble(assy_jun_cost[j])
				    + StringUtil.nullDouble(assy_pack_cost[j]) + StringUtil.nullDouble(type_cost[j][case1][case2])
				    + StringUtil.nullDouble(ma_depr[j][case1][case2]));
			} else {
			    assy_total_expense[j] = Double.toString(StringUtil.nullDouble(assy_jun_cost[j])
				    + StringUtil.nullDouble(assy_pack_cost[j]) + StringUtil.nullDouble(ma_depr[j][case1][case2]));
			}
		    }

		    if ("Insert".equals(pro_type[j][case1][case2]) || !"".equals(StringUtil.checkNull(m_upkeep[j][case1][case2]))) {
			assy_total_expense[j] = Double.toString(StringUtil.nullDouble(assy_total_expense[j])
			        + StringUtil.nullDouble(m_upkeep[j][case1][case2]));
		    }

		    if (!"".equals(StringUtil.checkNull(pl_total_expense[j][case1][case2]))) {
			assy_total_expense[j] = Double.toString(StringUtil.nullDouble(assy_total_expense[j])
			        + StringUtil.nullDouble(pl_total_expense[j][case1][case2]));
		    } else {
			assy_total_expense[j] = assy_total_expense[j];
		    }

		    // 전력비
		    jun_cost[j][case1][case2] = assy_jun_cost[j];
		    // 직접경비
		    total_expense[j][case1][case2] = assy_total_expense[j];
		    // 간접경비
		    assy_overhead[j] = Double.toString(StringUtil.nullDouble(assy_total_expense[j]) * StringUtil.nullDouble(st_overhead));

		    if (!"".equals(StringUtil.checkNull(pl_overhead[j][case1][case2]))) {
			assy_overhead[j] = Double.toString(StringUtil.nullDouble(assy_overhead[j])
			        + StringUtil.nullDouble(pl_overhead[j][case1][case2]));
		    }
		    overhead[j][case1][case2] = assy_overhead[j];

		    // 기타경비
		    if (!"".equals(StringUtil.checkNull(gita_cost[j][case1][case2]))) {
			gita_cost[j][case1][case2] = gita_cost[j][case1][case2];
		    } else {
			gita_cost[j][case1][case2] = "0";
		    }

		    // 제조경비
		    assy_common_cost[j] = Double.toString((StringUtil.nullDouble(assy_total_expense[j])
			    + StringUtil.nullDouble(assy_overhead[j]) + StringUtil.nullDouble(gita_cost[j][case1][case2]))
			    * StringUtil.nullDouble(usage[j][case1][case2]));
		    // System.out.println("assy_total_expense[j]" + assy_total_expense[j]);
		    // System.out.println("assy_overhead[j]" + assy_overhead[j]);

		    if (!"".equals(StringUtil.checkNull(pl_common_cost[j][case1][case2]))) {
			assy_common_cost[j] = Double.toString(StringUtil.nullDouble(assy_common_cost[j])
			        + StringUtil.nullDouble(pl_common_cost[j][case1][case2]));
		    } else {
			assy_common_cost[j] = assy_common_cost[j];
		    }

		    common_cost[j][case1][case2] = assy_common_cost[j];

		    // System.out.println(group_no[j][case1][case2]+"의 제조경비 : "+common_cost[j][case1][case2]);
		    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {

			/*********************************/
			/************* 금형벌수 *************/
			/*********************************/
			mold_su_1 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			mold_su_2 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			mold_su_3 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			mold_su_4 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
			    mold_su_5 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
			    mold_su_6 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
			    mold_su_7 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
			    mold_su_8 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[j]) * 22 * 25)) + 0.9) - 1);
			}

			if (StringUtil.nullDouble(mold_su_1) > StringUtil.nullDouble(mold_su_2)) {
			    mold_su = mold_su_1;
			} else {
			    mold_su = mold_su_2;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_3;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_3)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_3;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_4)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_4;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_5)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_5;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_6)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_6;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_7)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_7;
			}

			if (StringUtil.nullDouble(mold_su) > StringUtil.nullDouble(mold_su_8)) {
			    mold_su = mold_su;
			} else {
			    mold_su = mold_su_8;
			}

			if (StringUtil.nullDouble(mold_su) == 0 || StringUtil.nullDouble(mold_su) < 0) {
			    m_su[j][case1][case2] = "1";
			} else {
			    m_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(mold_su) + 1);
			}

			// 7459
			/*********************************/
			/*************** 감가비 *************/
			/*********************************/
			// 금형감가비
			// 기준수량
			if ("개조".equals(part_type_1[j][case1][case2])) {
			    // 일반감가기준(1000000)
			    s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0", cavity[j][case1][case2],
				    m_su[j][case1][case2]));

			    // 판매량감가기준(6년치총수량)
			    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
				        + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
				        + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
				        + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
				            .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } else {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
				        + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
				        + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
				        + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
				            .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    }

			    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (StringUtil.DoubleMultiply("1000000.0",
				    cavity[j][case1][case2], m_su[j][case1][case2]))) {
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
			    } else {
				if ("일반".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				} else if ("판매".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				} else {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "판매";
				}
			    }
			} else if ("공용".equals(part_type_1[j][case1][case2])) {
			    s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0", cavity[j][case1][case2],
				    m_su[j][case1][case2]));
			    avg_su[j][case1][case2] = avg_su[j][case1][case2];
			    mold_type[j][case1][case2] = "일반";
			    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				mold_cost[j][case1][case2] = mold_cost[j][case1][case2];
			    } else {
				mold_cost[j][case1][case2] = "3000";
			    }
			} else {
			    // 일반감가기준(1000000)
			    s_avg_su[j][case1][case2] = Integer.toString(StringUtil.DoubleMultiply("1000000.0", cavity[j][case1][case2],
				    m_su[j][case1][case2]));
			    // 판매량감가기준(6년치총수량)
			    if ("1".equals(p_su_chk) && !"신규".equals(part_type_1[j][case1][case2])) {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(p_su_year_1[j])
				        + StringUtil.nullDouble(p_su_year_2[j]) + StringUtil.nullDouble(p_su_year_3[j])
				        + StringUtil.nullDouble(p_su_year_4[j]) + StringUtil.nullDouble(p_su_year_5[j])
				        + StringUtil.nullDouble(p_su_year_6[j]) + StringUtil.nullDouble(p_su_year_7[j]) + StringUtil
				            .nullDouble(p_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } else {
				avg_su[j][case1][case2] = Double.toString(((StringUtil.nullDouble(a_su_year_1[j])
				        + StringUtil.nullDouble(a_su_year_2[j]) + StringUtil.nullDouble(a_su_year_3[j])
				        + StringUtil.nullDouble(a_su_year_4[j]) + StringUtil.nullDouble(a_su_year_5[j])
				        + StringUtil.nullDouble(a_su_year_6[j]) + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil
				            .nullDouble(a_su_year_8[j])) * 1000) * StringUtil.nullDouble(usage[j][case1][case2]));
			    } // 2400

			    if (StringUtil.nullDouble(avg_su[j][case1][case2]) > (StringUtil.DoubleMultiply("1000000.0",
				    cavity[j][case1][case2], m_su[j][case1][case2]))) {
				avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				mold_type[j][case1][case2] = "일반";
			    } else {
				if ("일반".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = s_avg_su[j][case1][case2];
				} else if ("판매".equals(mold_type[j][case1][case2])) {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				} else {
				    avg_su[j][case1][case2] = avg_su[j][case1][case2];
				    mold_type[j][case1][case2] = "판매";
				}
			    }
			}
			// System.out.println("형변환 에러 전...");
			if ("감가".equals(mold_c_type[j][case1][case2])) {
			    // 일반감가기준(1000000)
			    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				s_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
				        * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(s_avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));
			    }
			    // 판매량감가기준(6년치총수량)
			    if (!"".equals(StringUtil.checkNull(mold_cost[j][case1][case2]))) {
				g_depr_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(mold_cost[j][case1][case2])
				        * StringUtil.nullDouble(m_su[j][case1][case2]) * 1000)
				        / StringUtil.nullDouble(avg_su[j][case1][case2]) * StringUtil.nullDouble(usage[j][case1][case2]));
			    }
			} else {
			    s_depr_cost[j][case1][case2] = "0";
			    g_depr_cost[j][case1][case2] = "0";
			}

			if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
			    g_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(g_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			    s_depr_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(s_depr_cost[j][case1][case2])
				    + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
			}
		    } // mold_cost !"" if end

		    // 7563
		    if (("수동".equals(method_type[j][case1][case2]) || "자동".equals(method_type[j][case1][case2]))
			    && !"외주".equals(make_type[j][case1][case2])) {
			/*********************************/
			/************* 금형벌수 *************/
			/*********************************/
			line_su_1 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_1[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			line_su_2 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_2[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			line_su_3 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_3[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			line_su_4 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_4[j]) * 1000) / 12) / (StringUtil
			        .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			if (!"".equals(StringUtil.checkNull(a_su_year_5[j])) && StringUtil.nullDouble(a_su_year_5[j]) > 0) {
			    line_su_5 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_5[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_6[j])) && StringUtil.nullDouble(a_su_year_6[j]) > 0) {
			    line_su_6 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_6[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_7[j])) && StringUtil.nullDouble(a_su_year_7[j]) > 0) {
			    line_su_7 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_7[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			}
			if (!"".equals(StringUtil.checkNull(a_su_year_8[j])) && StringUtil.nullDouble(a_su_year_8[j]) > 0) {
			    line_su_8 = Double.toString(Math.floor((((StringUtil.nullDouble(a_su_year_8[j]) * 1000) / 12) / (StringUtil
				    .nullDouble(assy_output[case1]) * 20 * 25)) + 0.9) - 1);
			}

			if (StringUtil.nullDouble(line_su_1) > StringUtil.nullDouble(line_su_2)) {
			    line_su_in = line_su_1;
			} else {
			    line_su_in = line_su_2;
			}

			if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_3)) {
			    line_su_in = line_su_in;
			} else {
			    line_su_in = line_su_3;
			}

			if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_3)) {
			    line_su_in = line_su_in;
			} else {
			    line_su_in = line_su_3;
			}

			if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_4)) {
			    line_su_in = line_su_in;
			} else {
			    line_su_in = line_su_4;
			}

			if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_5)) {
			    line_su_in = line_su_in;
			} else {
			    line_su_in = line_su_5;
			}

			if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_6)) {
			    line_su_in = line_su_in;
			} else {
			    line_su_in = line_su_6;
			}

			if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_7)) {
			    line_su_in = line_su_in;
			} else {
			    line_su_in = line_su_7;
			}

			if (StringUtil.nullDouble(line_su_in) > StringUtil.nullDouble(line_su_8)) {
			    line_su_in = line_su_in;
			} else {
			    line_su_in = line_su_8;
			}

			if (StringUtil.nullDouble(line_su_in) == 0 || StringUtil.nullDouble(line_su_in) < 0) {
			    line_su[j][case1][case2] = "1";
			} else {
			    line_su[j][case1][case2] = Double.toString(StringUtil.nullDouble(line_su_in) + 1);
			}
		    }

		    // 7633
		    /*********************************/
		    /************** 감가비 **************/
		    /*********************************/

		    // 금형감가비
		    // 판매량감가기준(6년치총수량)
		    assy_avg_su[j] = Double.toString((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
			    + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
			    + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
			    + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000);

		    if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))
			    && !"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
			if (("수동".equals(method_type[j][case1][case2]) || "자동".equals(method_type[j][case1][case2]))
			        && !"외주".equals(make_type[j][case1][case2])) {
			    to_cost = Double.toString((StringUtil.nullDouble(sul_cost[j][case1][case2])
				    * StringUtil.nullDouble(line_su[j][case1][case2]) * 1.05)
				    + StringUtil.nullDouble(etc_cost[j][case1][case2]));
			} else {
			    to_cost = Double.toString((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05)
				    + StringUtil.nullDouble(etc_cost[j][case1][case2]));
			}
			assy_g_depr_cost[j] = Double.toString((StringUtil.nullDouble(to_cost) * 1000)
			        / StringUtil.nullDouble(assy_avg_su[j]));

			assy_s_depr_cost[j] = Double.toString((StringUtil.nullDouble(to_cost) * 1000)
			        / StringUtil.nullDouble(assy_avg_su[j]));
		    } else if (!"".equals(StringUtil.checkNull(sul_cost[j][case1][case2]))) {
			if (("수동".equals(method_type[j][case1][case2]) || "자동".equals(method_type[j][case1][case2]))
			        && !"외주".equals(make_type[j][case1][case2])) {
			    assy_g_depr_cost[j] = Double.toString(((StringUtil.nullDouble(sul_cost[j][case1][case2])
				    * StringUtil.nullDouble(line_su[j][case1][case2]) * 1.05) * 1000)
				    / StringUtil.nullDouble(assy_avg_su[j]));
			    assy_s_depr_cost[j] = Double.toString(((StringUtil.nullDouble(sul_cost[j][case1][case2])
				    * StringUtil.nullDouble(line_su[j][case1][case2]) * 1.05) * 1000)
				    / StringUtil.nullDouble(assy_avg_su[j]));
			} else {
			    assy_g_depr_cost[j] = Double.toString(((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
				    / StringUtil.nullDouble(assy_avg_su[j]));
			    assy_s_depr_cost[j] = Double.toString(((StringUtil.nullDouble(sul_cost[j][case1][case2]) * 1.05) * 1000)
				    / StringUtil.nullDouble(assy_avg_su[j]));
			}
		    } else if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
			assy_g_depr_cost[j] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
			        / StringUtil.nullDouble(assy_avg_su[j]));

			assy_s_depr_cost[j] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
			        / StringUtil.nullDouble(assy_avg_su[j]));
		    }

		    if (!"".equals(StringUtil.checkNull(pl_start_depr[j][case1][case2]))) {
			assy_g_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j])
			        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));

			assy_s_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_depr_cost[j])
			        + StringUtil.nullDouble(pl_start_depr[j][case1][case2]));
		    } else {
			assy_g_depr_cost[j] = assy_g_depr_cost[j];

			assy_s_depr_cost[j] = assy_s_depr_cost[j];
		    }

		    if (!"".equals(StringUtil.checkNull(pl_pro_depr[j][case1][case2]))) {
			assy_g_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j])
			        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));

			assy_s_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_depr_cost[j])
			        + StringUtil.nullDouble(pl_pro_depr[j][case1][case2]));
		    } else {
			assy_g_depr_cost[j] = assy_g_depr_cost[j];

			assy_s_depr_cost[j] = assy_s_depr_cost[j];
		    }

		    // 7687
		    // 기타투자비감가
		    if (!"".equals(StringUtil.checkNull(etc_cost[j][case1][case2]))) {
			etc_su = "0";
			etc_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
			        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
			        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
			        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000));
			etc_depr[j][case1][case2] = Double.toString((StringUtil.nullDouble(etc_cost[j][case1][case2]) * 1000)
			        / StringUtil.nullDouble(etc_su) * StringUtil.nullDouble(usage[j][case1][case2]));
		    } else {
			etc_depr[j][case1][case2] = "0";
		    }
		    assy_g_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j])
			    + StringUtil.nullDouble(g_depr_cost[j][case1][case2]));

		    if (!"".equals(StringUtil.checkNull(pl_etc_depr[j][case1][case2]))) {
			if (!"".equals(StringUtil.checkNull(etc_depr[j][case1][case2]))) {
			    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(etc_depr[j][case1][case2])
				    + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			} else {
			    etc_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
			}
			assy_g_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j])
			        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));

			assy_s_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_depr_cost[j])
			        + StringUtil.nullDouble(pl_etc_depr[j][case1][case2]));
		    }

		    /*********************************/
		    /************** 물류비 **************/
		    /*********************************/
		    if (!"".equals(StringUtil.checkNull(dis_cost[j][case1][case2]))) {
			dis_cost[j][case1][case2] = dis_cost[j][case1][case2];
		    } else if (!"".equals(StringUtil.checkNull(out_pack[j][case1][case2]))
			    && !"".equals(StringUtil.checkNull(distri_cost[j]))) {
			if ("생산1".equals(start_pro[j]) || "생산2".equals(start_pro[j])) {
			    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 460);
			} else if ("생산4".equals(start_pro[j])) {
			    distri_cost[j] = Double.toString(StringUtil.nullDouble(distri_cost[j]) + 1000);
			}
			dis_cost[j][case1][case2] = Double.toString(StringUtil.nullDouble(distri_cost[j])
			        / StringUtil.nullDouble(out_pack[j][case1][case2]));
			dis_cost[j][case1][case2] = String.format("%.2f", StringUtil.nullDouble(dis_cost[j][case1][case2]));
		    } else {
			dis_cost[j][case1][case2] = "0";
		    }
		    // System.out.println(">>>>조립 물류비2 : "+dis_cost[j][case1][case2]);
		    /*********************************/
		    /************** 관리비 **************/
		    /*********************************/
		    // 가공비
		    assy_process_cost[j] = Double.toString(StringUtil.nullDouble(assy_labor_cost[j])
			    + StringUtil.nullDouble(assy_common_cost[j]));
		    // System.out.println("************ !! assy_labor_cost : "+assy_labor_cost[j]);

		    // 관세
		    if ("중국".equals(pro_1[j][case1][case2])) {
			tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(assy_process_cost[case1]) * 0.08);
		    } else {
			tariff[j][case1][case2] = "0";
		    }

		    if (!"".equals(StringUtil.checkNull(pl_tariff[j][case1][case2]))) {
			tariff[j][case1][case2] = Double.toString(StringUtil.nullDouble(tariff[j][case1][case2])
			        + StringUtil.nullDouble(pl_tariff[j][case1][case2]));
		    } else {
			tariff[j][case1][case2] = tariff[j][case1][case2];
		    }

		    // 재료관리비
		    assy_jae_cost[j] = String.format("%.4f",
			    (StringUtil.nullDouble(g_assy_meterial_cost[j]) * StringUtil.nullDouble(st_jae_cost)));
		    // System.out.println("========== 재료관리비 ================");
		    // System.out.println("g_assy_meterial_cost : "+g_assy_meterial_cost[j]);

		    if (!"".equals(StringUtil.checkNull(pl_jae_cost[j][case1][case2]))) {
			assy_jae_cost[j] = Double.toString(StringUtil.nullDouble(assy_jae_cost[j])
			        + StringUtil.nullDouble(pl_jae_cost[j][case1][case2]));
		    } else {
			assy_jae_cost[j] = assy_jae_cost[j];
		    }

		    jae_cost[j][case1][case2] = assy_jae_cost[j];

		    // 일반관리비
		    assy_ge_cost[j] = String.format("%.4f",
			    (StringUtil.nullDouble(assy_process_cost[j]) * StringUtil.nullDouble(st_ge_cost)));
		    // System.out.println("************ !! assy_process_cost : "+assy_process_cost[j]);

		    if (!"".equals(StringUtil.checkNull(pl_ge_cost[j][case1][case2]))) {
			assy_ge_cost[j] = Double.toString(StringUtil.nullDouble(assy_ge_cost[j])
			        + StringUtil.nullDouble(pl_ge_cost[j][case1][case2]));
		    } else {
			assy_ge_cost[j] = assy_ge_cost[j];
		    }

		    ge_cost[j][case1][case2] = assy_ge_cost[j];

		    // YZK감가비 20101223 // 필드 yazaki_ro -> 포장용투자비로대체 / 산출식 미정
		    if (!"".equals(StringUtil.checkNull(yazaki_ro[j][case1][case2]))) {
			yzk_su = Double.toString(((StringUtil.nullDouble(a_su_year_1[j]) + StringUtil.nullDouble(a_su_year_2[j])
			        + StringUtil.nullDouble(a_su_year_3[j]) + StringUtil.nullDouble(a_su_year_4[j])
			        + StringUtil.nullDouble(a_su_year_5[j]) + StringUtil.nullDouble(a_su_year_6[j])
			        + StringUtil.nullDouble(a_su_year_7[j]) + StringUtil.nullDouble(a_su_year_8[j])) * 1000)
			        * StringUtil.nullDouble(usage[j][case1][case2]));
			yzk_depr[j][case1][case2] = Double
			        .toString(((StringUtil.nullDouble(yazaki_ro[j][case1][case2]) * 1000) / StringUtil.nullDouble(yzk_su))
			                * StringUtil.nullDouble(usage[j][case1][case2]));
			assy_s_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_depr_cost[j])
			        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
			assy_g_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j])
			        + StringUtil.nullDouble(yzk_depr[j][case1][case2]));
		    }

		    if (!"".equals(StringUtil.checkNull(pl_yzk_depr[j][case1][case2]))) {
			yzk_depr[j][case1][case2] = Double.toString(StringUtil.nullDouble(yzk_depr[j][case1][case2])
			        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
			assy_g_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_depr_cost[j])
			        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
			assy_s_depr_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_depr_cost[j])
			        + StringUtil.nullDouble(pl_yzk_depr[j][case1][case2]));
		    }
		    // System.out.println("############ 조립 감가비 2 :"+g_depr_cost[j][case1][case2]);

		    // R&D비 YZK감가비→ 포장용 투자비로 대체후 R&D비용에 포함적용으로 변경 2011.05.
		    if ("OK".equals(a_pro_type)) {
			assy_s_rnd_cost[j] = Double.toString(((StringUtil.nullDouble(g_assy_meterial_cost[j]) / StringUtil
			        .nullDouble(usage[j][case1][case2]))
			        + (StringUtil.nullDouble(assy_process_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2]))
			        + (StringUtil.nullDouble(assy_jae_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2]))
			        + (StringUtil.nullDouble(assy_ge_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2])) + (StringUtil
			        .nullDouble(assy_s_depr_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2])))
			        * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));
			assy_g_rnd_cost[j] = Double.toString(((StringUtil.nullDouble(g_assy_meterial_cost[j]) / StringUtil
			        .nullDouble(usage[j][case1][case2]))
			        + (StringUtil.nullDouble(assy_process_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2]))
			        + (StringUtil.nullDouble(assy_jae_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2]))
			        + (StringUtil.nullDouble(assy_ge_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2])) + (StringUtil
			        .nullDouble(assy_g_depr_cost[j]) / StringUtil.nullDouble(usage[j][case1][case2])))
			        * StringUtil.nullDouble(usage[j][case1][case2]) * StringUtil.nullDouble(st_rnd_cost));
			// System.out.println(j+" 번째 2번 assy_g_rnd_cost  : " + assy_g_rnd_cost[j]);
		    }

		    if (!"".equals(StringUtil.checkNull(pl_rnd_cost[j][case1][case2]))) {
			assy_s_rnd_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_rnd_cost[j])
			        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
			assy_g_rnd_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_rnd_cost[j])
			        + StringUtil.nullDouble(pl_rnd_cost[j][case1][case2]));
		    }
		    g_rnd_cost[j][case1][case2] = assy_g_rnd_cost[j];

		    // 총관리비
		    assy_ad_cost[j] = Double.toString(StringUtil.nullDouble(assy_jae_cost[j]) + StringUtil.nullDouble(assy_ge_cost[j])
			    + StringUtil.nullDouble(dis_cost[j][case1][case2]) + StringUtil.nullDouble(g_assy_loss[j])
			    + StringUtil.nullDouble(tariff[j][case1][case2]) + StringUtil.nullDouble(assy_g_rnd_cost[j]));
		    // System.out.println("assy_jae_cost !!!!!!!!===>"+assy_jae_cost[j]);
		    // System.out.println("assy_ge_cost !!!!!!!!===>"+assy_ge_cost[j]);
		    // System.out.println("dis_cost !!!!!!!!===>"+dis_cost[j][case1][case2]);

		    // 품질비용
		    if (!"".equals(StringUtil.checkNull(qu_cost[j][case1][case2]))) {
			assy_ad_cost[j] = Double.toString(StringUtil.nullDouble(assy_ad_cost[j])
			        + StringUtil.nullDouble(qu_cost[j][case1][case2]));
		    } else {
			qu_cost[j][case1][case2] = "0";
		    }
		    // 7828
		    /*********************************/
		    /*************** 총원가 *************/
		    /*********************************/

		    assy_s_actual_cost[j] = Double.toString((StringUtil.nullDouble(s_assy_meterial_cost[j]) - StringUtil
			    .nullDouble(s_assy_loss[j]))
			    + StringUtil.nullDouble(assy_labor_cost[j])
			    + StringUtil.nullDouble(assy_common_cost[j])
			    + StringUtil.nullDouble(assy_ad_cost[j])
			    + StringUtil.nullDouble(assy_s_depr_cost[j]));
		    assy_g_actual_cost[j] = Double.toString((StringUtil.nullDouble(g_assy_meterial_cost[j]) - StringUtil
			    .nullDouble(g_assy_loss[j]))
			    + StringUtil.nullDouble(assy_labor_cost[j])
			    + StringUtil.nullDouble(assy_common_cost[j])
			    + StringUtil.nullDouble(assy_ad_cost[j])
			    + StringUtil.nullDouble(assy_g_depr_cost[j]));
		    if (!"".equals(StringUtil.checkNull(pl_actual_cost[j][case1][case2]))) {
			assy_s_actual_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_actual_cost[j])
			        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
			assy_g_actual_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_actual_cost[j])
			        + StringUtil.nullDouble(pl_actual_cost[j][case1][case2]));
		    } else {
			assy_s_actual_cost[j] = assy_s_actual_cost[j];
			assy_g_actual_cost[j] = assy_g_actual_cost[j];
		    }

		    /*********************************/
		    /*************** 로열티 *************/
		    /*********************************/

		    if ("ok".equals(assy_in[j])) {
			if ("1".equals(royalty[j])) {
			    assy_s_roy_cost[j] = Double
				    .toString(((StringUtil.nullDouble(s_assy_meterial_cost[j]) - StringUtil.nullDouble(s_assy_loss[j]))
				            + StringUtil.nullDouble(assy_labor_cost[j])
				            + StringUtil.nullDouble(assy_common_cost[j])
				            + (StringUtil.nullDouble(assy_jae_cost[j]) + StringUtil.nullDouble(assy_ge_cost[j])
				                    + StringUtil.nullDouble(s_assy_loss[j])
				                    + StringUtil.nullDouble(tariff[j][case1][case2])
				                    + StringUtil.nullDouble(assy_s_rnd_cost[j]) + StringUtil
				                        .nullDouble(qu_cost[j][case1][case2])) + StringUtil.nullDouble(assy_s_depr_cost[j]))
				            * StringUtil.nullDouble(st_royalty));
			    assy_g_roy_cost[j] = Double
				    .toString(((StringUtil.nullDouble(g_assy_meterial_cost[j]) - StringUtil.nullDouble(g_assy_loss[j]))
				            + StringUtil.nullDouble(assy_labor_cost[j])
				            + StringUtil.nullDouble(assy_common_cost[j])
				            + (StringUtil.nullDouble(assy_jae_cost[j]) + StringUtil.nullDouble(assy_ge_cost[j])
				                    + StringUtil.nullDouble(g_assy_loss[j])
				                    + StringUtil.nullDouble(tariff[j][case1][case2])
				                    + StringUtil.nullDouble(assy_g_rnd_cost[j]) + StringUtil
				                        .nullDouble(qu_cost[j][case1][case2])) + StringUtil.nullDouble(assy_g_depr_cost[j]))
				            * StringUtil.nullDouble(st_royalty));

			    // System.out.println("g_assy_meterial_cost==>"+g_assy_meterial_cost[j]);

			    if (!"".equals(StringUtil.checkNull(pl_royalty_cost[j][case1][case2]))) {
				assy_s_roy_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_roy_cost[j])
				        + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
				assy_g_roy_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_roy_cost[j])
				        + StringUtil.nullDouble(pl_royalty_cost[j][case1][case2]));
			    }
			    assy_s_actual_cost[j] = Double.toString(StringUtil.nullDouble(assy_s_actual_cost[j])
				    + StringUtil.nullDouble(assy_s_roy_cost[j]));
			    assy_g_actual_cost[j] = Double.toString(StringUtil.nullDouble(assy_g_actual_cost[j])
				    + StringUtil.nullDouble(assy_g_roy_cost[j]));
			    // System.out.println("assy_ad_cost===>"+assy_ad_cost[j]);
			    assy_ad_cost[j] = Double.toString(StringUtil.nullDouble(assy_ad_cost[j])
				    + StringUtil.nullDouble(assy_g_roy_cost[j]));

			}
		    }

		    ad_cost[j][0][0] = assy_ad_cost[j];
		    // System.out.println(part_name[j][case1][case2]+"  ad_cost .=-======>"+ad_cost[j][0][0] );

		    // 7868
		    if ("Insert".equals(pro_type[j][case1][case2])) {
			sum_s_actual_cost[j][0][0] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + StringUtil.nullDouble(assy_labor_cost[j]) + StringUtil.nullDouble(assy_common_cost[j])
			        + StringUtil.nullDouble(assy_ad_cost[j]) + StringUtil.nullDouble(assy_s_depr_cost[j]));
			sum_g_actual_cost[j][0][0] = Double.toString(StringUtil.nullDouble(m_total_cost[j][case1][case2])
			        + StringUtil.nullDouble(assy_labor_cost[j]) + StringUtil.nullDouble(assy_common_cost[j])
			        + StringUtil.nullDouble(assy_ad_cost[j]) + StringUtil.nullDouble(assy_g_depr_cost[j]));
		    } else {
			sum_s_actual_cost[j][0][0] = Double.toString(StringUtil.nullDouble(assy_labor_cost[j])
			        + StringUtil.nullDouble(assy_common_cost[j]) + StringUtil.nullDouble(assy_ad_cost[j])
			        + StringUtil.nullDouble(assy_s_depr_cost[j]));
			sum_g_actual_cost[j][0][0] = Double.toString(StringUtil.nullDouble(assy_labor_cost[j])
			        + StringUtil.nullDouble(assy_common_cost[j]) + StringUtil.nullDouble(assy_ad_cost[j])
			        + StringUtil.nullDouble(assy_g_depr_cost[j]));
		    }
		    g_roy_cost[j][case1][case2] = assy_g_roy_cost[j];

		    /*********************************/
		    /*************** 수익률 *************/
		    /*********************************/

		    if (!"0".equals(StringUtil.checkNullZero(ket_cost[j])) && !"".equals(StringUtil.checkNull(ket_cost[j]))) {
			assy_s_earn_rate[j] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
			        .nullDouble(assy_s_actual_cost[j])) / StringUtil.nullDouble(ket_cost[j]));
			assy_g_earn_rate[j] = Double.toString((StringUtil.nullDouble(ket_cost[j]) - StringUtil
			        .nullDouble(assy_g_actual_cost[j])) / StringUtil.nullDouble(ket_cost[j]));
		    } else {
			if ("0".equals(target_cost[j])) {
			    assy_s_earn_rate[j] = Double.toString(((StringUtil.nullDouble(assy_s_actual_cost[j]) / 0.7) - StringUtil
				    .nullDouble(assy_s_actual_cost[j])) / (StringUtil.nullDouble(assy_s_actual_cost[j]) / 0.7));
			    assy_g_earn_rate[j] = Double.toString(((StringUtil.nullDouble(assy_g_actual_cost[j]) / 0.7) - StringUtil
				    .nullDouble(assy_g_actual_cost[j])) / (StringUtil.nullDouble(assy_g_actual_cost[j]) / 0.7));
			} else {
			    target_rate = Double.toString(StringUtil.nullDouble(target_cost[j]) / 100);
			    assy_s_earn_rate[j] = Double.toString(((StringUtil.nullDouble(assy_s_actual_cost[j]) / (1 - StringUtil
				    .nullDouble(target_rate))) - StringUtil.nullDouble(assy_s_actual_cost[j]))
				    / (StringUtil.nullDouble(assy_s_actual_cost[j]) / (1 - StringUtil.nullDouble(target_rate))));
			    assy_g_earn_rate[j] = Double.toString(((StringUtil.nullDouble(assy_g_actual_cost[j]) / (1 - StringUtil
				    .nullDouble(target_rate))) - StringUtil.nullDouble(assy_g_actual_cost[j]))
				    / (StringUtil.nullDouble(assy_g_actual_cost[j]) / (1 - StringUtil.nullDouble(target_rate))));

			}
		    }
		} // pro_type : assy, TML-조립, part_type_1 : 복합금형 if end
	    } // table_row_count for end
	} // assy_content if end
    } // cost_assy_content function end

    /**
     * 함수명 : cost_rawMaterial 설명 : 원재료 단가 산출
     * 
     * @param String
     *            page_name, GridData gdReq, GridData gdReq3, GridData gdReq4
     * @return
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 07. 23.
     */
    public void cost_rawMaterial(String page_name) throws Exception {

	/******* 함수안에서 사용할 변수 선언 *******/
	String u_cost = "";
	String recycle_stateYN = "";
	String pro_cost = "";
	String pla_cost = "";
	String lme_change = "";
	String met_w = "0";
	String cut_cost = "";
	String detin = "";
	/*************************************/

	Connection conn = null;
	CostAccDao costAccDao = null;

	ArrayList accLmeList = null;

	try {
	    conn = DBUtil.getConnection();
	    costAccDao = new CostAccDao(conn);
	    accLmeList = costAccDao.getAccLmeList(lme_ton);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(conn);
	}

	Hashtable accLmeMap = null;
	accLmeMap = (Hashtable) accLmeList.get(0);
	lme_cu = (String) accLmeMap.get("lme_cu");
	lme_zn = (String) accLmeMap.get("lme_zn");
	lme_sn = (String) accLmeMap.get("lme_sn");
	lme_ni = (String) accLmeMap.get("lme_ni");
	usd_rate = (String) accLmeMap.get("usd_rate");
	yen_rate = (String) accLmeMap.get("yen_rate");

	if ("".equals(StringUtil.checkNull(r_USD_rate)) || "0".equals(StringUtil.checkNull(r_USD_rate))) {

	    ArrayList accReerList = null;
	    try {
		conn = DBUtil.getConnection();
		costAccDao = new CostAccDao(conn);
		accReerList = costAccDao.getAccReerList();
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		DBUtil.close(conn);
	    }

	    Hashtable accReerMap = null;
	    accReerMap = (Hashtable) accReerList.get(0);
	    r_USD_rate = (String) accReerMap.get("usd_rate");
	    r_EURO_rate = (String) accReerMap.get("euro_rate");
	    r_YEN_rate = (String) accReerMap.get("yen_rate");
	    r_CNY_rate = (String) accReerMap.get("cny_rate");
	}

	/*************************/
	/******** 원화 환산 *********/
	/*************************/
	if ("work".equals(page_name)) {
	    for (int case1 = 0; case1 <= Integer.parseInt(case_count_1[0]); case1++) {
		for (int case2 = 0; case2 <= Integer.parseInt(case_count_2[0][case1]); case2++) {
		    if ("make_1".equals(make[0][case1][case2]) || "TML".equals(pro_type[0][case1][case2])
			    || "TML-조립".equals(pro_type[0][case1][case2])) { // 비철
			if (!"".equals(StringUtil.checkNull(lme_ton)) && (!lme_cu.equals(lme_ton) || !c_lme_ton.equals(lme_ton))) {
			    lme_cu = lme_ton;
			    lme_change = "OK";
			} else {
			    lme_cu = lme_cu;
			}
			if (!"".equals(StringUtil.checkNull(u_ex_rate)) && (!u_ex_rate.equals(usd_rate) || !c_u_ex_rate.equals(u_ex_rate))) {
			    usd_rate = u_ex_rate;
			    lme_change = "OK";
			} else {
			    usd_rate = usd_rate;
			}
			if (!"".equals(StringUtil.checkNull(y_ex_rate)) && (!y_ex_rate.equals(yen_rate) || !c_y_ex_rate.equals(y_ex_rate))) {
			    yen_rate = y_ex_rate;
			    lme_change = "OK";
			} else {
			    yen_rate = yen_rate;
			}
		    }
		    table_row_count = "1";
		}
	    }
	} else {
	    table_row_count = table_row;
	}

	W_Lme_cu = Double.toString((StringUtil.nullDouble(lme_cu) + 104) * 1.019 * StringUtil.nullDouble(usd_rate) / 1000);
	W_Lme_zn = Double.toString((Integer.parseInt(lme_zn) + 120) * 1.04 * (Integer.parseInt(usd_rate) / 1.0099) / 1000);
	W_Lme_sn = Double.toString((Integer.parseInt(lme_sn) + 260) * 1.0075 * Integer.parseInt(usd_rate) / 1000);
	W_Lme_ni = Double.toString((Integer.parseInt(lme_ni) + 210) * 1.037 * Integer.parseInt(usd_rate) / 1000);

	/******** 엔화 계산 START *******/
	Y_Lme_cu = Double
	        .toString(StringUtil.nullDouble(lme_cu) * StringUtil.nullDouble(usd_rate) / StringUtil.nullDouble(yen_rate) / 1000);
	Y_Lme_zn = Double
	        .toString(StringUtil.nullDouble(lme_zn) * StringUtil.nullDouble(usd_rate) / StringUtil.nullDouble(yen_rate) / 1000);
	Y_Lme_sn = Double
	        .toString(StringUtil.nullDouble(lme_sn) * StringUtil.nullDouble(usd_rate) / StringUtil.nullDouble(yen_rate) / 1000);
	Y_Lme_ni = Double
	        .toString(StringUtil.nullDouble(lme_ni) * StringUtil.nullDouble(usd_rate) / StringUtil.nullDouble(yen_rate) / 1000);
	Y_Premium = Double.toString(StringUtil.nullDouble(lme_cu) * StringUtil.nullDouble(usd_rate) / StringUtil.nullDouble(yen_rate)
	        / 1000);
	/******** 엔화 계산 END *******/

	for (int j = 0; j < Integer.parseInt(table_row_count); j++) {
	    for (int case1 = 0; case1 <= Integer.parseInt(StringUtil.checkNullZero(case_count_1[j])); case1++) {
		if (case_count_2[j][case1] == null) {
		    case_count_2[j][case1] = "0";
		}
		for (int case2 = 0; case2 <= Integer.parseInt(case_count_2[j][case1]); case2++) {
		    if ("work".equals(page_name)) {
			if (!c_unitcost[j][case1][case2].equals(unitcost[j][case1][case2])) {
			    if (!"표준단가".equals(unitcost[j][case1][case2])) {
				u_cost = "OK";
			    } else {
				u_cost = "표준단가";
			    }
			} else {
			    if (!"표준단가".equals(unitcost[j][case1][case2])) {
				u_cost = "No";
			    } else {
				u_cost = "표준단가";
			    }
			}

			if ("1".equals(recycle_state[j][case1][case2])) {
			    recycle_stateYN = "OK";
			} else {
			    recycle_stateYN = "No";
			}

			if (!"표준단가".equals(u_cost) && (!"OK".equals(lme_change) || "OK".equals(u_cost)) && "No".equals(recycle_stateYN)) {
			    unitcost[j][case1][case2] = unitcost[j][case1][case2];
			} else {
			    if ("make_1".equals(make[j][case1][case2]) || "TML".equals(pro_type[j][case1][case2])
				    || "TML-조립".equals(pro_type[j][case1][case2])) { // 비철
				if ("SUS301".equals(meterial[j][case1][case2])) {
				    unitcost[j][case1][case2] = Integer.toString(6000);
				} else if ("SUS304".equals(meterial[j][case1][case2])) {
				    unitcost[j][case1][case2] = Integer.toString(7000);
				} else if ("SPCC".equals(meterial[j][case1][case2])) {
				    unitcost[j][case1][case2] = Integer.toString(1500);
				} else {
				    met_type = meterial[j][case1][case2]; // 구분

				    // 가공비

				    try {
					conn = DBUtil.getConnection();
					costAccDao = new CostAccDao(conn);
					pro_cost = costAccDao.getAccProcessing(met_type, "no"); // "no" 임가공비인지 아닌지
				    } catch (Exception e) {
					e.printStackTrace();
				    } finally {
					DBUtil.close(conn);
				    }

				    if ("Unplate".equals(plating[j][case1][case2])) { // 무도금

					ArrayList accPlatingList = null;
					try {
					    conn = DBUtil.getConnection();
					    costAccDao = new CostAccDao(conn);
					    accPlatingList = costAccDao.getAccPlatingList(met_type);
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    DBUtil.close(conn);
					}

					Hashtable accPlatingMap = null;
					accPlatingMap = (Hashtable) accPlatingList.get(0);
					if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.299) { // 0.3t이상
					    pla_cost = (String) accPlatingMap.get("p_size_t_4");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.249) { // 0.25t~0.299
					    pla_cost = (String) accPlatingMap.get("p_size_t_3");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.199) { // 0.20t~0.249
					    pla_cost = (String) accPlatingMap.get("p_size_t_2");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.149) { // 0.15t~0.199
					    pla_cost = (String) accPlatingMap.get("p_size_t_1");
					}
					// C1100 = ROUND(원화_Cu,-1)+가공비_C1100
					// C2680 = ROUND(원화_Cu*0.65+원화_Zn*0.35,-1)+가공비_C2680
					// C5210 = ROUND(원화_Cu*0.92+원화_Sn*0.08,-1)+가공비_C5210
					// PMC26 = ROUND(원화_Cu*0.98+원화_Ni*0.02,-1)+가공비_PMC26
					// PMC90 = ROUND(원화_Cu,-1)+가공비_PMC90
					// C2100 = ROUND(원화_Cu*0.95+원화_Zn*0.05,-1)+가공비_C2100
					// C5191 = ROUND(원화_Cu*0.94+원화_Sn*0.06,-1)+가공비_C5210
					// C194 = ROUND(원화_Cu*0.98,-1)+가공비_C194-100
					// C2600 = ROUND(원화_Cu*0.7+원화_Zn*0.3,-1)+가공비_C2600
					// MSP1 = (엔화_Cu+엔화_Premium+가공비_MSP1+40-180)*환율_엔*1.125
					// MAX251= ((엔화_Cu*0.97+엔화_Sn*0.01+엔화_Ni*0.02+엔화_Premium)+가공비_MAX251+40)*환율_엔*1.125
					// KLF5 = (엔화_Cu+엔화_Premium+263)*환율_엔*1.125
					// KFC = (엔화_Cu*0.98+엔화_Sn*0.02+엔화_Premium+가공비_KFC)*환율_엔*1.125
					if ("표준단가".equals(u_cost) || "OK".equals(lme_change)) {
					    if ("C1100R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						                + StringUtil.nullDouble(pro_cost));
					    } else if ("C2680R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.65 + StringUtil
						                .nullDouble(W_Lme_zn) * 0.35) + 0.99) + StringUtil.nullDouble(pro_cost));
					    } else if ("C5210R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.92 + StringUtil
						                .nullDouble(W_Lme_sn) * 0.08) + 0.99) + StringUtil.nullDouble(pro_cost));
					    } else if ("PMC26R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.98 + StringUtil
						                .nullDouble(W_Lme_ni) * 0.02) + 0.99)
						                + StringUtil.nullDouble(pro_cost)
						                - StringUtil.nullDouble(pla_cost));
					    } else if ("PMC90R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						                + StringUtil.nullDouble(pro_cost) - StringUtil.nullDouble(pla_cost));
					    } else if ("C2100R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.95 + StringUtil
						                .nullDouble(W_Lme_zn) * 0.05) + 0.99) + StringUtil.nullDouble(pro_cost));
					    } else if ("C5191R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.94 + StringUtil
						                .nullDouble(W_Lme_sn) * 0.06) + 0.99) + StringUtil.nullDouble(pro_cost));
					    } else if ("C194".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.98) + 0.99)
						                + StringUtil.nullDouble(pro_cost) - 100);
					    } else if ("C2600R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.7 + StringUtil
						                .nullDouble(W_Lme_zn) * 0.3) + 0.99) + StringUtil.nullDouble(pro_cost));
					    } else if ("MSP1".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						        + StringUtil.nullDouble(Y_Premium) + 40 - 180)
						        * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else if ("MAX251C".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(((StringUtil.nullDouble(Y_Lme_cu) * 0.97
						                + StringUtil.nullDouble(Y_Lme_sn) * 0.01 + StringUtil.nullDouble(Y_Lme_ni)
						                * 0.02 + StringUtil.nullDouble(Y_Premium))
						                + StringUtil.nullDouble(pro_cost) + 40)
						                * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else if ("KLF5".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						        + StringUtil.nullDouble(Y_Premium) + 263)
						        * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else if ("KFC".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString((StringUtil.nullDouble(Y_Lme_cu) * 0.98 + StringUtil.nullDouble(Y_Lme_sn)
						                * 0.02 + StringUtil.nullDouble(Y_Premium) + StringUtil.nullDouble(pro_cost))
						                * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else {
						unitcost = unitcost;
					    }
					}
				    } else { // 도금 일때
					     // 도금비

					ArrayList accPlatingList = null;
					try {
					    conn = DBUtil.getConnection();
					    costAccDao = new CostAccDao(conn);
					    accPlatingList = costAccDao.getAccPlatingList(met_type);
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    DBUtil.close(conn);
					}

					Hashtable accPlatingMap = null;
					accPlatingMap = (Hashtable) accPlatingList.get(0);
					if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.299) { // 0.3t이상
					    pla_cost = (String) accPlatingMap.get("p_size_t_4");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.249) { // 0.25t~0.299
					    pla_cost = (String) accPlatingMap.get("p_size_t_3");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.199) { // 0.20t~0.249
					    pla_cost = (String) accPlatingMap.get("p_size_t_2");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.149) { // 0.15t~0.199
					    pla_cost = (String) accPlatingMap.get("p_size_t_1");
					}
					// C1100 = ROUND(원화_Cu,-1)+가공비_C1100
					// C2680 = ROUND(원화_Cu*0.65+원화_Zn*0.35,-1)+가공비_C2680 + 도금비
					// C5210 = ROUND(원화_Cu*0.92+원화_Sn*0.08,-1)+가공비_C5210 + 도금비
					// PMC26 = ROUND(원화_Cu*0.98+원화_Ni*0.02,-1)+가공비_PMC26 + 도금비
					// PMC90 = ROUND(원화_Cu,-1)+가공비_PMC90 + 도금비
					// C2100 = ROUND(원화_Cu*0.95+원화_Zn*0.05,-1)+가공비_C2100 + 도금비
					// C5210 = ROUND(원화_Cu*0.94+원화_Sn*0.06,-1)+가공비_C5210 + 도금비
					// C194 = ROUND(원화_Cu*0.98,-1)+가공비_C194-100 + 도금비
					// C2600 = ROUND(원화_Cu*0.7+원화_Zn*0.3,-1)+가공비_C2600 + 도금비
					// MSP1 = (엔화_Cu+엔화_Premium+가공비_MSP1+40)*환율_엔*1.125
					// NB109 = ((엔화_Cu*0.98+엔화_Sn*0.01+엔화_Ni*0.01+엔화_Premium)+가공비_NB109)*환율_엔*1.125
					// TRKLF5= (엔화_Cu+엔화_Premium+가공비_KLF5+150)*환율_엔*1.125
					// TRKFC = (엔화_Cu*0.98+엔화_Sn*0.02+엔화_Premium+가공비_KFC+85)*환율_엔*1.125
					if ("표준단가".equals(u_cost) || "OK".equals(lme_change)) {
					    if ("C1100R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						                + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					    } else if ("C2680R".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu)
						        * 0.65 + StringUtil.nullDouble(W_Lme_zn) * 0.35 + 0.99)
						        + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					    } else if ("C5210R".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu)
						        * 0.92 + StringUtil.nullDouble(W_Lme_sn) * 0.08 + 0.99)
						        + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					    } else if ("PMC26R".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu)
						        * 0.98 + StringUtil.nullDouble(W_Lme_ni) * 0.02 + 0.99)
						        + StringUtil.nullDouble(pro_cost));
					    } else if ("PMC90R".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						                + StringUtil.nullDouble(pro_cost));
					    } else if ("C2100R".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu)
						        * 0.95 + StringUtil.nullDouble(W_Lme_zn) * 0.05 + 0.99)
						        + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					    } else if ("C5191R".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu)
						        * 0.94 + StringUtil.nullDouble(W_Lme_sn) * 0.06 + 0.99)
						        + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					    } else if ("C194".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.98 + 0.99)
						                + StringUtil.nullDouble(pro_cost) - 100 + StringUtil.nullDouble(pla_cost));
					    } else if ("C2600R".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu)
						        * 0.7 + StringUtil.nullDouble(W_Lme_zn) * 0.3 + 0.99)
						        + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					    } else if ("MSP1".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						        + StringUtil.nullDouble(Y_Premium) + StringUtil.nullDouble(pro_cost) + 40)
						        * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else if ("NB109".equals(met_type)) {
						unitcost[j][case1][case2] = Double
						        .toString(((StringUtil.nullDouble(Y_Lme_cu) * 0.98
						                + StringUtil.nullDouble(Y_Lme_sn) * 0.01 + StringUtil.nullDouble(Y_Premium)) + StringUtil
						                .nullDouble(pro_cost)) * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else if ("TRKLF5".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						        + StringUtil.nullDouble(Y_Premium) + StringUtil.nullDouble(pro_cost) + 150)
						        * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else if ("TRKFC".equals(met_type)) {
						unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu) * 0.98
						        + StringUtil.nullDouble(Y_Lme_sn) * 0.02 + StringUtil.nullDouble(Y_Premium)
						        + StringUtil.nullDouble(pro_cost) + 85)
						        * StringUtil.nullDouble(yen_rate) * 1.125);
					    } else {
						unitcost[j][case1][case2] = unitcost[j][case1][case2];
					    }
					}
				    } // 도금일때 else 종료

				    // 절단비
				    if (StringUtil.nullDouble(w_size[j][case1][case2]) < 200 && "풍산".equals(m_maker[j][case1][case2])) {
					if ("C1100R".equals(met_type) || "C2600R".equals(met_type) || "C2680R".equals(met_type)) {
					    if (StringUtil.nullDouble(w_size[j][case1][case2]) > 20) {
						met_w = "21";
					    } else if (StringUtil.nullDouble(w_size[j][case1][case2]) < 20) {
						met_w = "20";
					    }
					} else {
					    if (StringUtil.nullDouble(w_size[j][case1][case2]) > 169) {
						met_w = "170";
					    } else if (StringUtil.nullDouble(w_size[j][case1][case2]) > 50) {
						met_w = "51";
					    } else {
						met_w = "50";
					    }
					}

					ArrayList accCuttingList = null;

					try {
					    conn = DBUtil.getConnection();
					    costAccDao = new CostAccDao(conn);
					    accCuttingList = costAccDao.getAccCuttingList(met_type, Integer.parseInt(met_w));
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    DBUtil.close(conn);
					}

					Hashtable accCuttingMap = null;
					accCuttingMap = (Hashtable) accCuttingList.get(0);
					if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.499) { // 0.5t이상
					    cut_cost = (String) accCuttingMap.get("c_size_t_1");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.399) { // 0.4t~0.499t
					    cut_cost = (String) accCuttingMap.get("c_size_t_2");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.299) { // 0.3t~0.399t
					    cut_cost = (String) accCuttingMap.get("c_size_t_3");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.249) { // 0.25t~0.299t
					    cut_cost = (String) accCuttingMap.get("c_size_t_4");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.199) { // 0.20t~0.249t
					    cut_cost = (String) accCuttingMap.get("c_size_t_5");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.149) { // 0.15t~0.199t
					    cut_cost = (String) accCuttingMap.get("c_size_t_6");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.129) { // 0.13t~0.149t
					    cut_cost = (String) accCuttingMap.get("c_size_t_7");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.079) { // 0.08t~0.129t
					    cut_cost = (String) accCuttingMap.get("c_size_t_8");
					} else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.999) { // 0.08t~0.129t
					    cut_cost = (String) accCuttingMap.get("c_size_t_9");
					}
				    } else {
					cut_cost = "0";
				    } // 풍산 이 아닐때 종료

				    if ("표준단가".equals(u_cost) || "OK".equals(lme_change)) {
					unitcost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost[j][case1][case2])
					        + StringUtil.nullDouble(cut_cost));
				    }
				} // 비철구분 안의 if문의 else 종료

				if ("1".equals(recycle_state)) {

				    try {
					conn = DBUtil.getConnection();
					costAccDao = new CostAccDao(conn);
					re_m_cost[j][case1][case2] = costAccDao.getAccProcessing(met_type, "yes"); // "yes" 임가공비일때
				    } catch (Exception e) {
					e.printStackTrace();
				    } finally {
					DBUtil.close(conn);
				    }

				}

				try {
				    conn = DBUtil.getConnection();
				    costAccDao = new CostAccDao(conn);
				    detin = costAccDao.getAccProcessing(met_type, "detin");
				} catch (Exception e) {
				    e.printStackTrace();
				} finally {
				    DBUtil.close(conn);
				}

				if ("Unplate".equals(plating)) { // 무도금
				    scrap_cost[j][case1][case2] = Double
					    .toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
					            - (((StringUtil.nullDouble(re_m_cost[j][case1][case2]) + StringUtil
					                    .nullDouble(cut_cost)) / 1000) / 0.98));
				} else {
				    scrap_cost[j][case1][case2] = Double.toString((StringUtil.nullDouble(unitcost[j][case1][case2]) / 1000)
					    - (((StringUtil.nullDouble(re_m_cost[j][case1][case2]) + StringUtil.nullDouble(cut_cost)
					            + StringUtil.nullDouble(detin) + StringUtil.nullDouble(pla_cost)) / 1000) / 0.98));
				}
				// 아래의 else if 는 비철이 아닌 수지일때
			    } else if ("make_2".equals(make[j][case1][case2]) || "HSG".equals(pro_type[j][case1][case2])
				    || "Insert".equals(pro_type[j][case1][case2]) || "sub_Insert".equals(pro_type[j][case1][case2])) { // 수지

				try {
				    conn = DBUtil.getConnection();
				    costAccDao = new CostAccDao(conn);
				    unitcost[j][case1][case2] = costAccDao.getAccMoldMaker(m_maker[j][case1][case2],
					    grade_name[j][case1][case2], grade_color[j][case1][case2]);
				} catch (Exception e) {
				    e.printStackTrace();
				} finally {
				    DBUtil.close(conn);
				}

				if ("".equals(StringUtil.checkNull(unitcost[j][case1][case2]))) {
				    if ("표준단가".equals(unitcost[j][case1][case2])) {
					unitcost[j][case1][case2] = unitcost[j][case1][case2];
				    } else {
					unitcost[j][case1][case2] = "1";
				    }
				}
			    } // 수지일때 else if 종료
			}
		    } else { // 요청서일때
			if ((!"".equals(StringUtil.checkNull(unitcost[j][case1][case2])) && !"표준단가".equals(StringUtil
			        .checkNull(unitcost[j][case1][case2]))) || "구매".equals(make_type[j][case1][case2])) {
			    unitcost[j][case1][case2] = unitcost[j][case1][case2];
			} else {
			    if ("make_1".equals(make[j][case1][case2]) || "TML".equals(pro_type[j][case1][case2])
				    || "TML-조립".equals(pro_type[j][case1][case2])) { // 비철
				if ("SUS301".equals(meterial[j][case1][case2])) {
				    unitcost[j][case1][case2] = Integer.toString(6000);
				} else if ("SUS304".equals(meterial[j][case1][case2])) {
				    unitcost[j][case1][case2] = Integer.toString(7000);
				} else if ("SPCC".equals(meterial[j][case1][case2])) {
				    unitcost[j][case1][case2] = Integer.toString(1500);
				} else {

				    met_type = meterial[j][case1][case2]; // 구분
				    // 가공비
				    try {
					conn = DBUtil.getConnection();
					costAccDao = new CostAccDao(conn);
					pro_cost = costAccDao.getAccProcessing(met_type, "no"); // "no" 임가공비인지 아닌지
				    } catch (Exception e) {
					e.printStackTrace();
				    } finally {
					DBUtil.close(conn);
				    }

				    if ("Unplate".equals(plating[j][case1][case2])) {
					// C1100 = ROUND(원화_Cu,-1)+가공비_C1100
					// C2680 = ROUND(원화_Cu*0.65+원화_Zn*0.35,-1)+가공비_C2680
					// C5210 = ROUND(원화_Cu*0.92+원화_Sn*0.08,-1)+가공비_C5210
					// PMC26 = ROUND(원화_Cu*0.98+원화_Ni*0.02,-1)+가공비_PMC26
					// PMC90 = ROUND(원화_Cu,-1)+가공비_PMC90
					// C2100 = ROUND(원화_Cu*0.95+원화_Zn*0.05,-1)+가공비_C2100
					// C5191 = ROUND(원화_Cu*0.94+원화_Sn*0.06,-1)+가공비_C5210
					// C194 = ROUND(원화_Cu*0.98,-1)+가공비_C194-100
					// C2600 = ROUND(원화_Cu*0.7+원화_Zn*0.3,-1)+가공비_C2600
					// MSP1 = (엔화_Cu+엔화_Premium+가공비_MSP1+40-180)*환율_엔*1.125
					// MAX251= ((엔화_Cu*0.97+엔화_Sn*0.01+엔화_Ni*0.02+엔화_Premium)+가공비_MAX251+40)*환율_엔*1.125
					// KLF5 = (엔화_Cu+엔화_Premium+263)*환율_엔*1.125
					// KFC = (엔화_Cu*0.98+엔화_Sn*0.02+엔화_Premium+가공비_KFC)*환율_엔*1.125
					if ("C1100R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						    + StringUtil.nullDouble(pro_cost));
					} else if ("C2680R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.65 + StringUtil
						            .nullDouble(W_Lme_zn) * 0.35) + 0.99) + StringUtil.nullDouble(pro_cost));
					} else if ("C5210R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.92 + StringUtil
						            .nullDouble(W_Lme_sn) * 0.08) + 0.99) + StringUtil.nullDouble(pro_cost));
					} else if ("PMC26R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.98 + StringUtil
						            .nullDouble(W_Lme_ni) * 0.02) + 0.99)
						            + StringUtil.nullDouble(pro_cost)
						            - StringUtil.nullDouble(pla_cost));
					} else if ("PMC90R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						    + StringUtil.nullDouble(pro_cost) - StringUtil.nullDouble(pla_cost));
					} else if ("C2100R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.95 + StringUtil
						            .nullDouble(W_Lme_zn) * 0.05) + 0.99) + StringUtil.nullDouble(pro_cost));
					} else if ("C5191R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.94 + StringUtil
						            .nullDouble(W_Lme_sn) * 0.06) + 0.99) + StringUtil.nullDouble(pro_cost));
					} else if ("C194".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.98) + 0.99)
						            + StringUtil.nullDouble(pro_cost) - 100);
					} else if ("C2600R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor((StringUtil.nullDouble(W_Lme_cu) * 0.7 + StringUtil
						            .nullDouble(W_Lme_zn) * 0.3) + 0.99) + StringUtil.nullDouble(pro_cost));
					} else if ("MSP1".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						    + StringUtil.nullDouble(Y_Premium) + 40 - 180)
						    * StringUtil.nullDouble(yen_rate) * 1.125);
					} else if ("MAX251C".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(Y_Lme_cu) * 0.97 + StringUtil.nullDouble(Y_Lme_sn)
						            * 0.01 + StringUtil.nullDouble(Y_Lme_ni) * 0.02 + StringUtil
						                .nullDouble(Y_Premium)) + StringUtil.nullDouble(pro_cost) + 40)
						            * StringUtil.nullDouble(yen_rate) * 1.125);
					} else if ("KLF5".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						    + StringUtil.nullDouble(Y_Premium) + 263)
						    * StringUtil.nullDouble(yen_rate) * 1.125);
					} else if ("KFC".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString((StringUtil.nullDouble(Y_Lme_cu) * 0.98 + StringUtil.nullDouble(Y_Lme_sn)
						            * 0.02 + StringUtil.nullDouble(Y_Premium) + StringUtil.nullDouble(pro_cost))
						            * StringUtil.nullDouble(yen_rate) * 1.125);
					} else {
					    unitcost[j][case1][case2] = unitcost[j][case1][case2];
					}
				    } else { // 도금일때

					ArrayList accPlatingList = null;
					try {
					    conn = DBUtil.getConnection();
					    costAccDao = new CostAccDao(conn);
					    accPlatingList = costAccDao.getAccPlatingList(met_type);
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    DBUtil.close(conn);
					}

					Hashtable accPlatingMap = null;
					if (accPlatingList.size() > 0) {
					    accPlatingMap = (Hashtable) accPlatingList.get(0);
					    if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.299) { // 0.3t이상
						pla_cost = (String) accPlatingMap.get("p_size_t_4");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.249) { // 0.25t~0.299
						pla_cost = (String) accPlatingMap.get("p_size_t_3");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.199) { // 0.20t~0.249
						pla_cost = (String) accPlatingMap.get("p_size_t_2");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.149) { // 0.15t~0.199
						pla_cost = (String) accPlatingMap.get("p_size_t_1");
					    }
					}
					// C1100 = ROUND(원화_Cu,-1)+가공비_C1100
					// C2680 = ROUND(원화_Cu*0.65+원화_Zn*0.35,-1)+가공비_C2680 + 도금비
					// C5210 = ROUND(원화_Cu*0.92+원화_Sn*0.08,-1)+가공비_C5210 + 도금비
					// PMC26 = ROUND(원화_Cu*0.98+원화_Ni*0.02,-1)+가공비_PMC26 + 도금비
					// PMC90 = ROUND(원화_Cu,-1)+가공비_PMC90 + 도금비
					// C2100 = ROUND(원화_Cu*0.95+원화_Zn*0.05,-1)+가공비_C2100 + 도금비
					// C5210 = ROUND(원화_Cu*0.94+원화_Sn*0.06,-1)+가공비_C5210 + 도금비
					// C194 = ROUND(원화_Cu*0.98,-1)+가공비_C194-100 + 도금비
					// C2600 = ROUND(원화_Cu*0.7+원화_Zn*0.3,-1)+가공비_C2600 + 도금비
					// MSP1 = (엔화_Cu+엔화_Premium+가공비_MSP1+40)*환율_엔*1.125
					// NB109 = ((엔화_Cu*0.98+엔화_Sn*0.01+엔화_Ni*0.01+엔화_Premium)+가공비_NB109)*환율_엔*1.125
					// TRKLF5= (엔화_Cu+엔화_Premium+가공비_KLF5+150)*환율_엔*1.125
					// TRKFC = (엔화_Cu*0.98+엔화_Sn*0.02+엔화_Premium+가공비_KFC+85)*환율_엔*1.125
					if ("C1100R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						    + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					} else if ("C2680R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.65
						    + StringUtil.nullDouble(W_Lme_zn) * 0.35 + 0.99)
						    + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					} else if ("C5210R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.92
						    + StringUtil.nullDouble(W_Lme_sn) * 0.08 + 0.99)
						    + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					} else if ("PMC26R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.98
						    + StringUtil.nullDouble(W_Lme_ni) * 0.02 + 0.99)
						    + StringUtil.nullDouble(pro_cost));
					} else if ("PMC90R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) + 0.99)
						    + StringUtil.nullDouble(pro_cost));
					} else if ("C2100R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.95
						    + StringUtil.nullDouble(W_Lme_zn) * 0.05 + 0.99)
						    + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					} else if ("C5191R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.94
						    + StringUtil.nullDouble(W_Lme_sn) * 0.06 + 0.99)
						    + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					} else if ("C194".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.98 + 0.99)
						            + StringUtil.nullDouble(pro_cost) - 100 + StringUtil.nullDouble(pla_cost));
					} else if ("C2600R".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString(Math.floor(StringUtil.nullDouble(W_Lme_cu) * 0.7
						    + StringUtil.nullDouble(W_Lme_zn) * 0.3 + 0.99)
						    + StringUtil.nullDouble(pro_cost) + StringUtil.nullDouble(pla_cost));
					} else if ("MSP1".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						    + StringUtil.nullDouble(Y_Premium) + StringUtil.nullDouble(pro_cost) + 40)
						    * StringUtil.nullDouble(yen_rate) * 1.125);
					} else if ("NB109".equals(met_type)) {
					    unitcost[j][case1][case2] = Double
						    .toString(((StringUtil.nullDouble(Y_Lme_cu) * 0.98 + StringUtil.nullDouble(Y_Lme_sn)
						            * 0.01 + StringUtil.nullDouble(Y_Premium)) + StringUtil.nullDouble(pro_cost))
						            * StringUtil.nullDouble(yen_rate) * 1.125);
					} else if ("TRKLF5".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu)
						    + StringUtil.nullDouble(Y_Premium) + StringUtil.nullDouble(pro_cost) + 150)
						    * StringUtil.nullDouble(yen_rate) * 1.125);
					} else if ("TRKFC".equals(met_type)) {
					    unitcost[j][case1][case2] = Double.toString((StringUtil.nullDouble(Y_Lme_cu) * 0.98
						    + StringUtil.nullDouble(Y_Lme_sn) * 0.02 + StringUtil.nullDouble(Y_Premium)
						    + StringUtil.nullDouble(pro_cost) + 85)
						    * StringUtil.nullDouble(yen_rate) * 1.125);
					} else {
					    unitcost[j][case1][case2] = unitcost[j][case1][case2];
					}
				    }

				    // 절단비
				    if (StringUtil.nullDouble(w_size[j][case1][case2]) < 200 && "풍산".equals(m_maker[j][case1][case2])) {
					if ("C1100R".equals(met_type) || "C2600R".equals(met_type) || "C2680R".equals(met_type)) {
					    if (StringUtil.nullDouble(w_size[j][case1][case2]) > 20) {
						met_w = "21";
					    } else if (StringUtil.nullDouble(w_size[j][case1][case2]) < 20) {
						met_w = "20";
					    }
					} else {
					    if (StringUtil.nullDouble(w_size[j][case1][case2]) > 169) {
						met_w = "170";
					    } else if (StringUtil.nullDouble(w_size[j][case1][case2]) > 50) {
						met_w = "51";
					    } else {
						met_w = "50";
					    }
					}

					ArrayList accCuttingList = null;
					try {
					    conn = DBUtil.getConnection();
					    costAccDao = new CostAccDao(conn);
					    accCuttingList = costAccDao.getAccCuttingList(met_type, Integer.parseInt(met_w));
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    DBUtil.close(conn);
					}
					Hashtable accCuttingMap = null;
					if (accCuttingList.size() > 0) {
					    accCuttingMap = (Hashtable) accCuttingList.get(0);
					    if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.499) { // 0.5t이상
						cut_cost = (String) accCuttingMap.get("c_size_t_1");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.399) { // 0.4t~0.499t
						cut_cost = (String) accCuttingMap.get("c_size_t_2");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.299) { // 0.3t~0.399t
						cut_cost = (String) accCuttingMap.get("c_size_t_3");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.249) { // 0.25t~0.299t
						cut_cost = (String) accCuttingMap.get("c_size_t_4");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.199) { // 0.20t~0.249t
						cut_cost = (String) accCuttingMap.get("c_size_t_5");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.149) { // 0.15t~0.199t
						cut_cost = (String) accCuttingMap.get("c_size_t_6");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.129) { // 0.13t~0.149t
						cut_cost = (String) accCuttingMap.get("c_size_t_7");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.079) { // 0.08t~0.129t
						cut_cost = (String) accCuttingMap.get("c_size_t_8");
					    } else if (StringUtil.nullDouble(t_size[j][case1][case2]) > 0.999) { // 0.08t~0.129t
						cut_cost = (String) accCuttingMap.get("c_size_t_9");
					    }
					} else {
					    cut_cost = "0";
					} // 풍산 이 아닐때 종료

					unitcost[j][case1][case2] = Double.toString(StringUtil.nullDouble(unitcost[j][case1][case2])
					        + StringUtil.nullDouble(cut_cost));
				    }
				}
			    } else if ("make_2".equals(make[j][case1][case2]) || "HSG".equals(pro_type[j][case1][case2])
				    || "sub_Insert".equals(pro_type[j][case1][case2])) { // 수지

				try {
				    conn = DBUtil.getConnection();
				    costAccDao = new CostAccDao(conn);
				    unitcost[j][case1][case2] = costAccDao.getAccMoldMaker(m_maker[j][case1][case2],
					    grade_name[j][case1][case2], grade_color[j][case1][case2]);
				} catch (Exception e) {
				    e.printStackTrace();
				} finally {
				    DBUtil.close(conn);
				}
				if ("".equals(StringUtil.checkNull(unitcost[j][case1][case2]))) {
				    if ("표준단가".equals(unitcost[j][case1][case2])) {
					unitcost[j][case1][case2] = unitcost[j][case1][case2];
				    } else {
					unitcost[j][case1][case2] = "1";
				    }
				}
			    } // 수지일때 else if 종료
			}
		    } // 요청서일때 종료
		} // for 3
	    } // for 2
	} // for 1
    } // 원재료 단가 산출 end
}
