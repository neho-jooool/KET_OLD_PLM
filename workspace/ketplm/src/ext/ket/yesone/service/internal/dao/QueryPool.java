package ext.ket.yesone.service.internal.dao;

import ext.ket.yesone.entity.KETYesoneBaseDTO;
import ext.ket.yesone.util.FormTypeEnum;

public class QueryPool {

	private static QueryPool instance;

	public static QueryPool getInstance() throws Exception {
		if (instance == null) {
			synchronized (QueryPool.class) {
				if (instance == null) {
					instance = new QueryPool();
				}
			}
		}
		return instance;
	}

	private QueryPool() {

	}

	public String getCommonSelectQry(KETYesoneBaseDTO baseDto) throws Exception {

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT EMP_NO     	   AS EMP_NO,        \n");
		sb.append("       REL_CODE   	   AS REL_CODE,      \n");
		sb.append("       NAT_FOR_DED      AS NAT_FOR_DED,   \n");
		sb.append("       BAS_DED          AS BAS_DED,      \n");
		sb.append("       HANDI_PRSN_DED   AS HANDI_PRSN_DED,  \n");
		sb.append("       SON_REAL_DED     AS SON_REAL_DED,  \n");
		sb.append("       SITE             AS SITE,  \n");
		sb.append("       substr(FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_enc@legacy('comp7',FAM_RES_NO),7,7) AS FAM_RES_NO, \n");
		sb.append("       substr(FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',FAM_RES_NO),7,7) AS MY_RES_NO, \n");
		sb.append("       GET_SEARCH_MAN_AGE@legacy(substr(FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',FAM_RES_NO),7,7)) as MAN_AGE, \n");
		sb.append("       (SELECT MAX(to_number(chasu)) AS chasu FROM KETYesoneBaseDTO@LEGACY WHERE EMP_NO='" + baseDto.getEmp_no() + "' AND YEAR='" + baseDto.getYear()
				+ "') as CHASU, \n");
		sb.append("       KOR_NAME AS NAME\n");
		sb.append("  FROM HCLRN17@legacy  \n");
		sb.append(" WHERE YEAR = " + baseDto.getYear() + " \n");
		sb.append("   AND EMP_NO = " + baseDto.getEmp_no() + "\n ");

		return sb.toString();
	}

	public String getBaseChasuSelectQry(KETYesoneBaseDTO baseDto) throws Exception {

		String emp_no = baseDto.getEmp_no();
		String year = baseDto.getYear();
		String site = baseDto.getSite();

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT COUNT(*)+1 AS CHASU FROM KETYesoneBaseDTO@LEGACY WHERE EMP_NO='" + emp_no + "' AND YEAR='" + year + "' AND SITE='" + site + "'");

		return sb.toString();
	}

	public String getSelectQueryI(KETYesoneBaseDTO baseDto, String form_cd, String maxChasu) throws Exception {

		String year = baseDto.getYear();
		String emp_no = baseDto.getEmp_no();

		StringBuffer sb = new StringBuffer();

		FormTypeEnum Ftype = FormTypeEnum.getFormTypeEnum(form_cd);

		if (Ftype == null) {
			throw new Exception("서식코드가 존재하지 않습니다!");
		}

		if (Ftype == FormTypeEnum.기부금) {

			sb.append("        SELECT YEAR AS YEAR,                    \n");
			sb.append("               YEAR AS GIVE_YYMM,       \n");
			sb.append("               EMP_NO AS EMP_NO,                  \n");
			sb.append("               ROWNUM AS SEQ,                  \n");
			sb.append("               BUSNID AS GI_BIZ_NO,     \n");
			sb.append("               substr(RESID,1,6) || substr(xdbutl_acct.xdb_enc@legacy('comp7',RESID),7,7) AS GI_RES_NO,      \n");
			sb.append("               TRADE_NM AS GI_BIZ_NAME, \n");
			sb.append("               DONATION_CD AS GI_CODE,  \n");
			sb.append("               '' AS CONT,              \n");
			sb.append("               SUM AS AMT,              \n");
			sb.append("               SYSDATE AS IPUT_DATE,   \n");
			sb.append("               EMP_NO AS IPUT_EMP,     \n");
			sb.append("               SYSDATE AS MOD_DATE,     \n");
			sb.append("               '' AS GI_REC_SEQ,        \n");
			sb.append("               '' AS REL_CODE,          \n");
			sb.append("(SELECT NAT_FOR_DED FROM HCLRN17@legacy WHERE YEAR = donation.YEAR AND EMP_NO = donation.EMP_NO \n");
			sb.append("    AND substr(FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',FAM_RES_NO),7,7) = RESID) AS NAT_FOR_DED, \n");
			sb.append("(SELECT NAME FROM KETYesoneBaseDTO@legacy where year = donation.year and emp_no = donation.emp_no and chasu = donation.chasu) AS KOR_NAME, \n");
			sb.append("(SELECT REL_CODE FROM HCLRN17@legacy WHERE YEAR = donation.YEAR AND EMP_NO = donation.EMP_NO \n");
			sb.append("    AND substr(FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',FAM_RES_NO),7,7) = RESID) AS GI_RELAT, \n");
			sb.append("NAME AS GI_KOR_NAME,\n");
			sb.append("'1' AS GUBN         \n");
			sb.append("FROM KETDonationDTO@legacy donation WHERE EMP_NO = '" + emp_no + "' AND year = '" + year + "' AND CHASU = '" + maxChasu + "'\n");
			sb.append("AND RESID IN ");
			sb.append(" ( SELECT SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) \n");
			sb.append("     FROM HCLRN17@LEGACY              \n");
			sb.append("    WHERE YEAR = '" + year + "'       \n");
			sb.append("      AND EMP_NO = '" + emp_no + "')     \n");

			sb.append("order by RESID \n");

		} else if (Ftype == FormTypeEnum.의료) {
			sb.append(" SELECT YEAR   AS YEAR, \n");
			sb.append("        EMP_NO AS EMP_NO, \n");
			sb.append("        SEQ    AS SEQ, \n");
			sb.append("        HO_BIZ_NO AS HO_BIZ_NO, \n");
			sb.append("        HO_RES_NO AS HO_RES_NO, \n");
			sb.append("        HO_BIZ_NAME AS HO_BIZ_NAME, \n");
			sb.append("        CONT AS CONT, \n");
			sb.append("        AMT AS AMT, \n");
			sb.append("        REL_GU AS REL_GU, \n");
			sb.append("        CASE WHEN GUBN = '0' THEN '1' \n");
			sb.append("        WHEN HANDI = '1' THEN '2' \n");
			sb.append("        WHEN MAN_AGE >= 65 THEN '1' \n");
			sb.append("        ELSE '3' END GUBN, \n");
			sb.append("        IPUT_DATE AS IPUT_DATE, \n");
			sb.append("        IPUT_EMP AS IPUT_EMP, \n");
			sb.append("        MOD_DATE AS MOD_DATE, \n");
			sb.append("        MED_GUBN AS MED_GUBN, \n");
			sb.append("        NAT_FOR_DED AS NAT_FOR_DED \n");
			sb.append("  FROM ( \n");
			sb.append(" SELECT YEAR   AS YEAR, \n");
			sb.append("        EMP_NO AS EMP_NO, \n");
			sb.append("        ROWNUM AS SEQ, \n");
			sb.append("        BUSNID AS HO_BIZ_NO, \n");
			sb.append("        SUBSTR(RESID,1,6) || SUBSTR(XDBUTL_ACCT.XDB_ENC@LEGACY('comp7',RESID),7,7)  AS HO_RES_NO, \n");
			sb.append("        TRADE_NM AS HO_BIZ_NAME, \n");
			sb.append("        ''       AS CONT, \n");
			sb.append("        SUM      AS AMT, \n");
			sb.append("        ''      AS REL_GU, \n");
			sb.append("        (SELECT REL_CODE FROM HCLRN17@LEGACY WHERE YEAR = MEDICAL.YEAR AND EMP_NO = MEDICAL.EMP_NO \n");
			sb.append("            AND SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) = RESID) AS GUBN, \n");
			sb.append("        SYSDATE AS IPUT_DATE, \n");
			sb.append("        EMP_NO  AS IPUT_EMP, \n");
			sb.append("        SYSDATE AS MOD_DATE, \n");
			sb.append("        '1' AS MED_GUBN, \n");
			sb.append("        (SELECT NAT_FOR_DED FROM HCLRN17@LEGACY WHERE YEAR = MEDICAL.YEAR AND EMP_NO = MEDICAL.EMP_NO \n");
			sb.append("            AND SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) = RESID) AS NAT_FOR_DED,\n");
			sb.append("        GET_SEARCH_MAN_AGE@LEGACY(RESID) AS MAN_AGE,\n");
			sb.append("        (SELECT HANDI_PRSN_DED FROM HCLRN17@LEGACY WHERE YEAR = MEDICAL.YEAR AND EMP_NO = MEDICAL.EMP_NO \n");
			sb.append("            AND SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) = RESID) AS HANDI \n");
			sb.append("  FROM KETMEDICALDTO@LEGACY MEDICAL \n");
			sb.append(" WHERE EMP_NO = '" + emp_no + "' AND year = '" + year + "' AND CHASU = '" + maxChasu + "'\n");
			sb.append("   AND RESID IN ");
			sb.append(" ( SELECT SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) \n");
			sb.append("     FROM HCLRN17@LEGACY              \n");
			sb.append("    WHERE YEAR = '" + year + "'       \n");
			sb.append("      AND EMP_NO = '" + emp_no + "')     \n");
			sb.append(" ORDER BY RESID ) \n");
		} else {

			if (Ftype.isIsDeposit()) { // 개인연금저축, 연금저축,퇴직연금, 장기집합투자증권저축,주택마련저축
				sb.append(" select year as year, \n");
				sb.append("        emp_no as emp_no, \n");
				sb.append("        '21' as dedu_gubn, \n");
				sb.append("        com_cd as bank_code, \n");
				sb.append("        xdbutl_acct.xdb_enc@legacy('normal',ACC_NO) AS ACC_NO, \n");
				sb.append("        '0' as save_seq, \n");
				sb.append("        ''  as bank_name, \n");
				sb.append("         sum as pay_amt, \n");
				sb.append("         case when sum*0.4 >= 720000 then '720000' \n");
				sb.append("         else to_char(sum*0.4) \n");
				sb.append("         end dedu_amt, \n");
				sb.append("         ''      as kor_name, \n");
				sb.append("         sysdate as iput_date, \n");
				sb.append("         emp_no as iput_emp, \n");
				sb.append("         sysdate as mod_date \n");
				sb.append("   from KETDepositPvtDTO@legacy  \n"); // 개인연금저축 21
				sb.append("  WHERE EMP_NO = '" + emp_no + "' AND year = '" + year + "' AND CHASU = '" + maxChasu + "'\n");
				sb.append("    AND RESID IN ");
				sb.append(" ( SELECT SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) \n");
				sb.append("     FROM HCLRN17@LEGACY              \n");
				sb.append("    WHERE YEAR = '" + year + "'       \n");
				sb.append("      AND EMP_NO = '" + emp_no + "')  \n");
				sb.append("  union all \n");
				sb.append(" select year as year, \n");
				sb.append("        emp_no as emp_no, \n");
				sb.append("        '22' as dedu_gubn, \n");
				sb.append("        com_cd as bank_code, \n");
				sb.append("        xdbutl_acct.xdb_enc@legacy('normal',ACC_NO) AS ACC_NO, \n");
				sb.append("        '0' as save_seq, \n");
				sb.append("        ''  as bank_name, \n");
				sb.append("         ann_tot_amt as pay_amt, \n");
				sb.append("         case when ann_tot_amt > 4000000 then '4000000' \n");
				sb.append("         else ann_tot_amt \n");
				sb.append("         end dedu_amt, \n");
				sb.append("         ''      as kor_name, \n");
				sb.append("         sysdate as iput_date, \n");
				sb.append("         emp_no as iput_emp, \n");
				sb.append("         sysdate as mod_date \n");
				sb.append("  from KETDepositDTO@legacy \n"); // 연금저축22
				sb.append("  WHERE EMP_NO = '" + emp_no + "' AND year = '" + year + "' AND CHASU = '" + maxChasu + "'\n");
				sb.append("    AND RESID IN ");
				sb.append(" ( SELECT SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) \n");
				sb.append("     FROM HCLRN17@LEGACY              \n");
				sb.append("    WHERE YEAR = '" + year + "'       \n");
				sb.append("      AND EMP_NO = '" + emp_no + "')  \n");
				sb.append("    union all \n");
				sb.append("select year as year, \n");
				sb.append("        emp_no as emp_no, \n");
				sb.append("        decode(saving_gubn,'1','31','2','32','4','34') as dedu_gubn, \n");
				sb.append("        com_cd as bank_code, \n");
				sb.append("        xdbutl_acct.xdb_enc@legacy('normal',ACC_NO) AS ACC_NO, \n");
				sb.append("        '0' as save_seq, \n");
				sb.append("        ''  as bank_name, \n");
				sb.append("         case when saving_gubn = '1' and sum >= 1200000 then '1200000'  \n");
				sb.append("         when saving_gubn = '2' and sum >= 1200000 and decode(saving_gubn,1,sum,0)+decode(saving_gubn,2,sum,0) > 2400000 then '1200000' \n");
				sb.append("         when saving_gubn = '4' and sum >= 1800000 then '1800000'  \n");
				sb.append("         else sum end pay_amt,  \n");
				sb.append("         case when saving_gubn = '1' and sum >= 1200000 then to_char(1200000 * 0.4)  \n");
				sb.append("         when saving_gubn = '2' and sum >= 1200000 then to_char(1200000 * 0.4)  \n");
				sb.append("         when saving_gubn = '4' and sum >= 1800000 then to_char(1800000 * 0.4)  \n");
				sb.append("         else to_char(sum*0.4) end dedu_amt,  \n");
				sb.append("         ''      as kor_name, \n");
				sb.append("         sysdate as iput_date, \n");
				sb.append("         emp_no as iput_emp, \n");
				sb.append("         sysdate as mod_date  \n");
				sb.append("  from KETHousePrpDTO@legacy \n"); // 청약저축 saving_gubn 1:청약저축(31), 2:주택청약종합저축(32), 4:근로자주택마련저축(34)
				sb.append(" WHERE EMP_NO = '" + emp_no + "' AND year = '" + year + "' AND CHASU = '" + maxChasu + "'\n");
				// sb.append("   and sum <decode(saving_gubn,1,1200000,2,1200000,4,1800000)  \n");
				sb.append("   AND RESID IN ");
				sb.append(" ( SELECT SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) \n");
				sb.append("     FROM HCLRN17@LEGACY              \n");
				sb.append("    WHERE YEAR = '" + year + "'       \n");
				sb.append("      AND EMP_NO = '" + emp_no + "')  \n");
				sb.append(" union all \n");
				sb.append(" select  year as year, \n");
				sb.append("        emp_no as emp_no, \n");
				sb.append("        pension_cd as dedu_gubn, \n");
				sb.append("        com_cd as bank_code, \n");
				sb.append("        xdbutl_acct.xdb_enc@legacy('normal',ACC_NO) AS ACC_NO, \n");
				sb.append("        '0' as save_seq, \n");
				sb.append("        ''  as bank_name, \n");
				sb.append("         ann_tot_amt as pay_amt, \n");
				sb.append("         '' as dedu_amt, \n");
				sb.append("         ''      as kor_name, \n");
				sb.append("         sysdate as iput_date, \n");
				sb.append("         emp_no as iput_emp, \n");
				sb.append("         sysdate as mod_date from KETRetirePsDTO@legacy \n"); // 퇴직연금(11 근로자 퇴직급여보장, 12 과학기술인적공제)
				sb.append(" WHERE EMP_NO = '" + emp_no + "' AND year = '" + year + "' AND CHASU = '" + maxChasu + "'\n");
				sb.append("    AND RESID IN ");
				sb.append(" ( SELECT SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) \n");
				sb.append("     FROM HCLRN17@LEGACY              \n");
				sb.append("    WHERE YEAR = '" + year + "'       \n");
				sb.append("      AND EMP_NO = '" + emp_no + "')  \n");
				sb.append(" union all  \n");
				sb.append(" select year, \n");
				sb.append("        emp_no, \n");
				sb.append("        '51' as dedu_gubn, \n");
				sb.append("        com_cd as bank_code, \n");
				sb.append("        xdbutl_acct.xdb_enc@legacy('normal',SECU_NO) AS ACC_NO, \n");
				sb.append("        '0' as save_seq, \n");
				sb.append("        ''  as bank_name, \n");
				sb.append("         sum as pay_amt, \n");
				sb.append("         case when sum*0.4 >= 2400000 then '2400000' \n");
				sb.append("         else to_char(sum*0.4) \n");
				sb.append("         end dedu_amt, \n");
				sb.append("         ''      as kor_name, \n");
				sb.append("         sysdate as iput_date, \n");
				sb.append("         emp_no as iput_emp, \n");
				sb.append("         sysdate as mod_date  \n");
				sb.append("   from KETInvestSecuDTO@legacy  \n");// --장기집합투자증권저축 51
				sb.append(" WHERE EMP_NO = '" + emp_no + "' AND year = '" + year + "' AND CHASU = '" + maxChasu + "'\n");
				sb.append("    AND RESID IN ");
				sb.append(" ( SELECT SUBSTR(FAM_RES_NO,1,6) || SUBSTR(XDBUTL_ACCT.XDB_DEC@LEGACY('comp7',FAM_RES_NO),7,7) \n");
				sb.append("     FROM HCLRN17@LEGACY              \n");
				sb.append("    WHERE YEAR = '" + year + "'       \n");
				sb.append("      AND EMP_NO = '" + emp_no + "')  \n");
			}
		}

		return sb.toString();
	}

	public String getUpdateQuery(KETYesoneBaseDTO baseDto, String form_cd, String maxChasu) {// 신용카드, 직불카드, 현금영수증, 교복, 직업훈련, 교육,보험

		String year = baseDto.getYear();
		String emp_no = baseDto.getEmp_no();

		StringBuffer sb = new StringBuffer();

		sb.append(" UPDATE HCLRN17@LEGACY A \n");
		sb.append("    SET (\n");
		sb.append(" NAT_CARD_AMT,         \n ");
		sb.append(" NAT_DEB_CARD_AMT,   \n ");
		sb.append(" NAT_CASH_AMT,    \n ");
		sb.append(" NAT_TRAD_AMT,  \n ");
		sb.append(" public_transport_amt,  \n ");
		sb.append(" nat_educ_amt,  \n ");
		sb.append(" nat_insr_bil_amt  \n ");
		// sb.append(" pre_pre_card_amt,           \n ");
		// sb.append(" pre_pre_trad_amt,     \n ");
		// sb.append(" pre_pre_transport_amt,   \n ");
		// sb.append(" pre_pre_deb_card_amt,   \n ");
		// sb.append(" pre_pre_cash_amt,    \n ");
		// sb.append(" pre_card_amt,              \n ");
		// sb.append(" pre_trad_amt,      \n ");
		// sb.append(" pre_transport_amt, \n ");
		// sb.append(" pre_deb_card_amt,    \n ");
		// sb.append(" pre_cash_amt,   \n ");
		// sb.append(" fir_card_amt,  \n ");
		// sb.append(" fir_deb_card_amt,    \n ");
		// sb.append(" fir_cash_amt,  \n ");
		// sb.append(" sec_card_amt,  \n ");
		// sb.append(" sec_deb_card_amt,    \n ");
		// sb.append(" sec_cash_amt,    \n ");
		// sb.append(" fir_transport_amt,    \n ");
		// sb.append(" fir_trad_amt,   \n ");
		// sb.append(" sec_transport_amt,   \n ");
		// sb.append(" sec_trad_amt    \n ");
		sb.append(") =       \n");
		sb.append("(select NVL(NAT_CARD_AMT,0) AS NAT_CARD_AMT,  \n"); // --신용카드 일반
		sb.append("        NVL(NAT_DEB_CARD_AMT,0) AS NAT_DEB_CARD_AMT,  \n"); // --직불카드 일반
		sb.append("        NVL(NAT_CASH_AMT,0) AS NAT_CASH_AMT,  \n"); // --현금영수증 일반
		sb.append("        NVL(NAT_TRAD_AMT_CREDIT,0)+NVL(NAT_TRAD_AMT_DEBIT,0)+NVL(NAT_TRAD_AMT_cash,0) AS NAT_TRAD_AMT, --전통시장 \n");
		sb.append("        NVL(public_transport_amt_CREDIT,0)+NVL(public_transport_amt_DEBIT,0)+NVL(public_transport_amt_cash,0) AS public_transport_amt, \n"); // --대중교통
		sb.append("        nvl(utiform_amt,0)+nvl(job_amt,0)+nvl(edu_amt,0)+nvl(school_amt,0) as nat_educ_amt, --교육비 \n");
		sb.append("        nvl(nat_insr_bil_amt,0) as nat_insr_bil_amt \n"); // --보험
		// sb.append("        nvl(pre_pre_card_amt,0) as pre_pre_card_amt,\n"); // 전전년도 신용카드 일반
		// sb.append("        nvl(pre_pre_trad_amt_credit,0) + nvl(pre_pre_trad_amt_debit,0) + nvl(pre_pre_trad_amt_cash,0) as pre_pre_trad_amt,   \n"); // --전전년도
		// // 전통시장
		// sb.append("        nvl(pre_pre_transport_amt_credit,0) + nvl(pre_pre_transport_amt_debit,0) + nvl(pre_pre_transport_amt_cash,0) as pre_pre_transport_amt, \n"); // --
		// // 전전년도
		// // 대중교통
		// sb.append("        nvl(pre_pre_deb_card_amt,0) as pre_pre_deb_card_amt,  \n"); // --전전년도 직불카드 일반
		// sb.append("        nvl(pre_pre_cash_amt,0) as pre_pre_cash_amt,  \n"); // -- 전전년도 현금 일반
		// sb.append("        nvl(pre_card_amt,0) as pre_card_amt,            \n"); // -- 직전년도 신용카드 일반
		// sb.append("        nvl(pre_trad_amt_credit,0) + nvl(pre_trad_amt_debit,0) + nvl(pre_trad_amt_cash,0) as pre_trad_amt,    \n"); // --직전년도
		// // 전통시장
		// sb.append("        nvl(pre_transport_amt_credit,0) + nvl(pre_transport_amt_debit,0) + nvl(pre_transport_amt_cash,0) as pre_transport_amt, \n"); // --
		// // 직전년도
		// // 대중교통
		// sb.append("        nvl(pre_deb_card_amt,0) as pre_deb_card_amt,  \n"); // --직전년도 직불카드 일반
		// sb.append("        nvl(pre_cash_amt,0) as pre_cash_amt,  \n"); // -- 직전년도 현금 일반
		// sb.append("        nvl(fir_card_amt,0) as fir_card_amt,   \n"); // -- 신용카드 상반기
		// sb.append("        nvl(fir_deb_card_amt,0) as fir_deb_card_amt,  \n"); // --직불카드 상반기
		// sb.append("        nvl(fir_cash_amt,0) as fir_cash_amt, \n"); // --현금상반기
		// sb.append("        nvl(sec_card_amt,0) as sec_card_amt, \n"); // -- 신용카드 하반기
		// sb.append("        nvl(sec_deb_card_amt,0) as sec_deb_card_amt,  \n"); // --직불카드 하반기
		// sb.append("        nvl(sec_cash_amt,0) as sec_cash_amt,  \n"); // --현금 하반기
		// sb.append("        nvl(fir_transport_amt_credit,0) + nvl(fir_transport_amt_debit,0) + nvl(fir_transport_amt_cash,0) as fir_transport_amt,  \n"); // --대중교통
		// // 상반기
		// sb.append("        nvl(fir_trad_amt_credit,0) + nvl(fir_trad_amt_debit,0) + nvl(fir_trad_amt_cash,0) as fir_trad_amt,  \n"); // --전통시장
		// // 상반기
		// sb.append("        nvl(sec_transport_amt_credit,0) + nvl(sec_transport_amt_debit,0) + nvl(sec_transport_amt_cash,0) as sec_transport_amt,  \n"); // --대중교통
		// // 상반기
		// sb.append("        nvl(sec_trad_amt_credit,0) + nvl(sec_trad_amt_debit,0) + nvl(sec_trad_amt_cash,0) as sec_trad_amt  \n"); // --전통시장
		// // 상반기
		sb.append("  from (       \n");
		sb.append(" select base.FAM_RES_NO, \n");
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETCreditDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '1' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as nat_card_amt,          \n"); // --신용카드 일반
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETCreditDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '2' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as nat_trad_amt_credit,            \n"); // --신용카드 전통시장
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETCreditDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '3' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as public_transport_amt_credit,      \n"); // --신용카드 대중교통
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETDebitCardDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '1' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as nat_deb_card_amt,            \n"); // --직불카드 일반
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETDebitCardDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '2' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as nat_trad_amt_debit,           \n"); // --직불카드 전통시장
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETDebitCardDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '3' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as public_transport_amt_debit,      \n"); // --직불카드 대중교통
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETCashRctDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '1' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as nat_cash_amt,           \n"); // --현금영수증 일반
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETCashRctDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '2' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as nat_trad_amt_cash,            \n"); // --현금영수증 전통시장
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETCashRctDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and use_place_cd = '3' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as public_transport_amt_cash,     \n"); // --현금영수증 대중교통

		sb.append("        (select case when sum(sum) > 500000 then 500000 else sum(sum) end  utiform_amt \n");
		sb.append("           from KETUniformDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as utiform_amt,      \n"); // --교복
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETJobTrainingDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as job_amt,     \n"); // --직업훈련비
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETEduDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as edu_amt,      \n"); // --교육비
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETSchoolExpenseDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as school_amt,      \n"); // --학자금대출
		sb.append("        (select sum(sum)  \n");
		sb.append("           from KETInsuranceDTO@legacy \n");
		sb.append("          where year = '" + year + "'  \n");
		sb.append("            and emp_no = '" + emp_no + "' \n");
		sb.append("            and chasu = '" + maxChasu + "' \n");
		sb.append("            and dat_cd = 'G0001' \n"); // --G0001:보장성, G0002:장애인보장성
		sb.append("            and year = base.year \n");
		sb.append("            and emp_no = base.emp_no \n");
		sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)  \n");
		sb.append("            = substr(insu1_resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',insu1_resid),7,7) \n");
		sb.append("            and base.bas_ded = '1') as nat_insr_bil_amt     \n"); // --보험(기본공제대상자만)

		/*
		 * sb.append("        (select sum(ftyr_tot_amt)            \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
		 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_tot_amt is not null and rownum = 1  \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_card_amt,           \n"); // -- // 전전년도 // 신용카드 // 일반
		 * sb.append("        (select sum(ftyr_market_tot_amt)   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
		 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_market_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_trad_amt_credit,             \n"); // --전전년도 // 신용카드 //
		 * 전통시장 sb.append("        (select sum(ftyr_tmoney_tot_amt)   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
		 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_tmoney_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_transport_amt_credit,      \n"); // -- // 전전년도 // 신용카드 //
		 * 대중교통
		 */

		/*
		 * sb.append("        (select sum(ftyr_tot_amt)   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year + "'   \n");
		 * sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_tot_amt is not null and rownum = 1  \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_deb_card_amt,            \n"); // --전전년도 // 직불카드 // 일반
		 * sb.append("        (select sum(ftyr_market_tot_amt)   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
		 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_market_tot_amt is not null and rownum = 1    \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_trad_amt_debit,   \n"); // --전전년도 // 직불카드 // 전통시장
		 * sb.append("        (select sum(ftyr_tmoney_tot_amt)   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
		 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_tmoney_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_transport_amt_debit,      \n"); // --전전년도 // 직불카드 // 대중교통
		 * sb.append("        (select sum(ftyr_tot_amt)   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year + "'   \n");
		 * sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_tot_amt is not null and rownum = 1  \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_cash_amt,         \n"); // --전전년도 // 현금 // 일반
		 * sb.append("        (select sum(ftyr_market_tot_amt)   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
		 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_market_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_trad_amt_cash,              \n"); // -- // 전전년도 // 현금 //
		 * 전통시장 sb.append("        (select sum(ftyr_tmoney_tot_amt)   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
		 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
		 * sb.append("            and ftyr_tmoney_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
		 * sb.append("            and emp_no = base.emp_no  \n");
		 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
		 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_pre_transport_amt_cash,      \n"); // --전전년도 // 현금 // 대중교통
		 *//*
			 * sb.append("        (select sum(pre_tot_amt)   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year + "'   \n");
			 * sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_tot_amt is not null and rownum = 1  \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_card_amt,          \n"); // --직전년도 // 신용카드 // 일반
			 * sb.append("        (select sum(pre_market_tot_amt)   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_market_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_trad_amt_credit,          \n"); // --직전년도 // 신용카드 // 전통시장
			 * sb.append("        (select sum(pre_tmoney_tot_amt)   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_tmoney_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_transport_amt_credit,      \n"); // --직전년도 // 신용카드 //
			 * 대중교통 sb.append("        (select sum(pre_tot_amt)   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_deb_card_amt,             \n"); // --직전년도 // 직불카드 // 일반
			 * sb.append("        (select sum(pre_market_tot_amt)   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_market_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_trad_amt_debit,  \n"); // --직전년도 // 직불카드 // 전통시장
			 * sb.append("        (select sum(pre_tmoney_tot_amt)   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_tmoney_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_transport_amt_debit,        \n");
			 * sb.append("        (select sum(pre_tot_amt)   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year + "'   \n");
			 * sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_tot_amt is not null and rownum = 1  \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_cash_amt,       \n"); // --직전년도 // 현금일반
			 * sb.append("        (select sum(pre_tmoney_tot_amt)   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_tmoney_tot_amt is not null and rownum = 1   \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_transport_amt_cash,             \n"); // --직전년도 // 현금 //
			 * 대중교통 sb.append("        (select sum(pre_market_tot_amt)   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and pre_market_tot_amt is not null and rownum = 1 \n"); sb.append("            and year = base.year  \n");
			 * sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as pre_trad_amt_cash,  \n"); // --직전년도 // 현금 // 전통시장
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '1'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_card_amt,  \n"); // --신용카드 // 상반기
			 * sb.append("        (select sum(second_year_tot_amt )   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '1' \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_card_amt,   \n"); // --신용카드 // 하반기
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '1'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_deb_card_amt,   \n"); // --직불카드 // 상반기
			 * sb.append("        (select sum(second_year_tot_amt )   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '1'   \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_deb_card_amt,  \n"); // --직불카드 // 하반기
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '1'   \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_cash_amt,     \n"); // --현금 // 상반기
			 * sb.append("        (select sum(second_year_tot_amt  )   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '1'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_cash_amt,   \n"); // --현금 // 하반기
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '3'   \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_transport_amt_credit,  \n"); // --신용카드 // 상반기 // 대중교통
			 * sb.append("        (select sum(second_year_tot_amt )   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '3'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_transport_amt_credit,    \n"); // --신용카드 // 하반기 // 대중교통
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '3'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_transport_amt_debit,   \n"); // --직불카드 // 상반기 // 대중교통
			 * sb.append("        (select sum(second_year_tot_amt )   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '3' \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_transport_amt_debit,  \n"); // --직불카드 // 하반기 // 대중교통
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '3'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_transport_amt_cash,   \n"); // --현금 // 상반기 // 대중교통
			 * sb.append("        (select sum(second_year_tot_amt  )   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '3'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_transport_amt_cash, \n"); // --현금 // 하반기 // 대중교통
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '2'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_trad_amt_credit,  \n"); // --신용카드 // 상반기 // 전통시장
			 * sb.append("        (select sum(second_year_tot_amt )   \n"); sb.append("           from KETCreditDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '2'   \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_trad_amt_credit,   \n"); // --신용카드 // 하반기 // 전통시장
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '2'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_trad_amt_debit,   \n"); // --직불카드 // 상반기 // 전통시장
			 * sb.append("        (select sum(second_year_tot_amt )   \n"); sb.append("           from KETDebitCardDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '2'   \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_trad_amt_debit,  \n"); // --직불카드 // 하반기 // 전통시장
			 * sb.append("        (select sum(first_year_tot_amt )   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '2'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as fir_trad_amt_cash,    \n"); // --현금 // 상반기 // 전통시장
			 * sb.append("        (select sum(second_year_tot_amt  )   \n"); sb.append("           from KETCashRctDTO@legacy  \n"); sb.append("          where year = '" + year +
			 * "'   \n"); sb.append("            and emp_no = '" + emp_no + "'  \n"); sb.append("            and chasu = '" + maxChasu + "'  \n");
			 * sb.append("            and use_place_cd = '2'  \n"); sb.append("            and year = base.year  \n"); sb.append("            and emp_no = base.emp_no  \n");
			 * sb.append("            and substr(base.FAM_RES_NO,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',base.FAM_RES_NO),7,7)   \n");
			 * sb.append("            = substr(resid,1,6) || substr(xdbutl_acct.xdb_dec@legacy('comp7',resid),7,7)) as sec_trad_amt_cash \n"); // --현금 // 하반기 // 전통시장
			 */
		sb.append("   from HCLRN17@legacy base where year = '" + year + "' and emp_no = '" + emp_no + "') WHERE FAM_RES_NO = A.FAM_RES_NO) \n");
		sb.append("   where year = '" + year + "' and emp_no = '" + emp_no + "' \n");

		return (sb.toString());

	}
}
