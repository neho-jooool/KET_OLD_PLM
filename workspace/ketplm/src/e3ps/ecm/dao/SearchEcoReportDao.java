package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.beans.EcmUtil;
import ext.ket.shared.log.Kogger;

public class SearchEcoReportDao {

    private Connection conn;

    public SearchEcoReportDao(Connection conn){
        this.conn = conn;
    }

    public List<Map<String, Object>> getSearchTypeEcoReportListSQL(KETParamMapUtil paramMap, List<Map<String, Object>> conditionList) throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Map<String, Object> ecoTypeReport = null;
        List<Map<String, Object>> ecoTypeReportList = new ArrayList<Map<String, Object>>();

        StringBuffer sql = new StringBuffer();
        sql.append( "SELECT *                                                                         \n");
        sql.append( "  FROM (                                                                         \n");
        sql.append( "        SELECT ROWNUM row_id                                                     \n");
        sql.append( "              ,t.*                                                               \n");
        sql.append( "          FROM (                                                                 \n");
        sql.append( "                SELECT T4.WTPartNumber partNo                                    \n");
        sql.append( "                     , MIN(T4.name) partName                                     \n");
        sql.append( "                     , MAX(T3.classnameA2A2||':'|| T3.idA2A2) oid                \n");
        sql.append( "                     , SUM(DECODE(t1.devyn, 'D', 1, 0) ) dev_cnt                 \n");
        sql.append( "                     , SUM(DECODE(t1.devyn, 'P', 1, 0) ) prod_cnt                \n");
        sql.append( "                     , SUM(DECODE(t1.divisionflag, 'C', 1, 0) ) car_div_cnt      \n");
        sql.append( "                     , SUM(DECODE(t1.divisionflag, 'E', 1, 0) ) elec_div_cnt     \n");
        if ( paramMap.getString("ecoReasonTypeCls").equals("C") ) {
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.changeReason, 'REASON_1')), 1, 1)), 0) REASON_1 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.changeReason, 'REASON_2')), 1, 1)), 0) REASON_2 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.changeReason, 'REASON_3')), 1, 1)), 0) REASON_3 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.changeReason, 'REASON_4')), 1, 1)), 0) REASON_4 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.changeReason, 'REASON_5')), 1, 1)), 0) REASON_5 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.changeReason, 'REASON_6')), 1, 1)), 0) REASON_6 \n");
            if ( paramMap.getString("prodMoldCls").equals("PT002") ) {
                sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.changeReason, 'REASON_7')), 1, 1)), 0) REASON_7 \n");
            }
        }
        else {
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_01')), 1, 1)), 0) INCR_01 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_02')), 1, 1)), 0) INCR_02 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_03')), 1, 1)), 0) INCR_03 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_04')), 1, 1)), 0) INCR_04 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_05')), 1, 1)), 0) INCR_05 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_06')), 1, 1)), 0) INCR_06 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_07')), 1, 1)), 0) INCR_07 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_08')), 1, 1)), 0) INCR_08 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_09')), 1, 1)), 0) INCR_09 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_10')), 1, 1)), 0) INCR_10 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_11')), 1, 1)), 0) INCR_11 \n");
            sql.append( "                     , NVL(SUM(DECODE(SIGN(INSTR(T1.increaseProdType, 'INCR_12')), 1, 1)), 0) INCR_12 \n");
        }
        sql.append( "                     , COUNT(*) ecoCount                                                                  \n");
        if( paramMap.getString("prodMoldCls").equals("PT001") )
        {//제품ECO
            sql.append( "                  FROM KETProdChangeOrder T1                                                                                \n");
            sql.append( "                     , KETProdECOPartLink T2                                                                                \n");
        } else {//금형ECO
            sql.append( "                     , ( CASE WHEN SUBSTR(t4.wtpartnumber, 0, 2) = '29' THEN 'Press'                                         \n");
            sql.append( "                              WHEN SUBSTR(t4.wtpartnumber, 0, 1) = '1' OR SUBSTR(t4.wtpartnumber, 0, 1) = '3' THEN 'Press'   \n");
            sql.append( "                              WHEN SUBSTR(t4.wtpartnumber, 0, 1) = '2' OR SUBSTR(t4.wtpartnumber, 0, 1) = '4' THEN 'Mold'    \n");
            sql.append( "                          END )                                                         obj_type    \n" );
            sql.append( "                  FROM KETMoldChangeOrder T1                         \n");
            sql.append( "                     , KETMoldECOPartLink T2                         \n");
        }
        sql.append( "                     , WTPart T3                                         \n");
        sql.append( "                     , WTPartMaster T4                                   \n");
        sql.append( "                 WHERE 1 = 1                                             \n");
        sql.append( "                   AND T1.idA2A2 = T2.idA3B5                             \n");
        sql.append( "                   AND T2.IDA3A5 = T3.ida2a2                             \n");//wt.part.WTPart
        sql.append( "                   AND T3.idA3masterReference = T4.ida2a2                \n");//wt.part.WTPartMaster

        for ( Map<String, Object> condistion : conditionList ) {
            KETParamMapUtil parameter = KETParamMapUtil.getMap(condistion);

            //단계구분
            if( parameter.getString("devYn") != null && parameter.getString("devYn").length() > 0 ) {
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.devYn", parameter.getString("devYn"), false)).append("    \n");
            }
            //사업부구분
            if( parameter.getString("divisionFlag") != null && parameter.getString("divisionFlag").length() > 0 ) {
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.divisionFlag", parameter.getString("divisionFlag"), false)).append("    \n");
            }
            //부품번호
            if( parameter.getString("partNo") != null && parameter.getString("partNo").length() > 0 ) {
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T4.WTPartNumber", parameter.getString("partNo"), false)).append("    \n");
            }
            //설계변경 사유
            if( parameter.getString("changeReason") != null && parameter.getString("changeReason").length() > 0 ) {
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.changeReason", parameter.getString("changeReason"), false)).append("    \n");
            }
            //생산성향상유형
            if( parameter.getString("increaseProdType") != null && parameter.getString("increaseProdType").length() > 0 ) {
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.increaseProdType", parameter.getString("increaseProdType"), false)).append("    \n");
            }
            //상태
            if( parameter.getString("sancStateFlag") != null && parameter.getString("sancStateFlag").length() > 0 ) {
                if ( parameter.getString("sancStateFlag").equals("INWORK") ) {
                    sql.append("                   AND T1.stateState <> 'APPROVED' \n");
                }
                else if ( parameter.getString("sancStateFlag").equals("COMLETE") ) {
                    sql.append("                   AND T1.stateState = 'APPROVED' \n");
                }
            }
            //작성일자
            if( parameter.getString("createStartDate") != null && parameter.getString("createStartDate").length() > 0
                    && parameter.getString("createEndDate") != null && parameter.getString("createEndDate").length() > 0 ) {
                sql.append( "                   AND T1.createStampA2 BETWEEN TO_DATE('" + parameter.getString("createStartDate") + "', 'YYYYMMDD') AND TO_DATE('" + parameter.getString("createEndDate") + " 235959.86399', 'YYYYMMDD HH24MISS.SSSSS') \n");
            }

            // 고객사
            if( parameter.getString("domestic_yn") != null && parameter.getString("domestic_yn").length() > 0 ) {
                //생산성향상유형
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.domesticyn", parameter.getString("domestic_yn"), false)).append("    \n");
            }
            if( parameter.getString("car_maker") != null && parameter.getString("car_maker").length() > 0 ) {
                //생산성향상유형
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.carmaker", parameter.getString("car_maker"), false)).append("    \n");
            }
            // 차종
            if( parameter.getString("car_category") != null && parameter.getString("car_category").length() > 0 ) {
                //생산성향상유형
                sql.append("                   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.carcategory", parameter.getString("car_category"), false)).append("    \n");
            }
        }
        sql.append( "               GROUP BY T4.WTPartNumber   \n");
        sql.append( "               ORDER BY 1                 \n");
        sql.append( "               ) t                        \n");
        if( paramMap.getString("dieType") != null && paramMap.getString("dieType").length() > 0 ) {
            //금형 Type
            sql.append("               AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("t.obj_type", paramMap.getString("dieType"), false)).append("    \n");
        }
        sql.append( "     )tb       \n" );
        sql.append( " WHERE 1 = 1   \n" );

        try {
            pstmt = new LoggableStatement( conn, sql.toString() );
            rs = pstmt.executeQuery();

            while(rs.next()) {
                ecoTypeReport = new HashMap<String, Object>();
                ecoTypeReport.put("partNo",       rs.getString("partNo"));
                ecoTypeReport.put("partName",     rs.getString("partName"));
                ecoTypeReport.put("oid",          rs.getString("oid"));
                ecoTypeReport.put("ecoCount",     rs.getString("ecoCount"));
                ecoTypeReport.put("dev_cnt",      rs.getString("dev_cnt"));
                ecoTypeReport.put("prod_cnt",     rs.getString("prod_cnt"));
                ecoTypeReport.put("car_div_cnt",  rs.getString("car_div_cnt"));
                ecoTypeReport.put("elec_div_cnt", rs.getString("elec_div_cnt"));

                if ( paramMap.getString("ecoReasonTypeCls").equals("C") ) {
                    ecoTypeReport.put("REASON_1", rs.getString("REASON_1"));
                    ecoTypeReport.put("REASON_2", rs.getString("REASON_2"));
                    ecoTypeReport.put("REASON_3", rs.getString("REASON_3"));
                    ecoTypeReport.put("REASON_4", rs.getString("REASON_4"));
                    ecoTypeReport.put("REASON_5", rs.getString("REASON_5"));
                    ecoTypeReport.put("REASON_6", rs.getString("REASON_6"));

                    if ( paramMap.getString("prodMoldCls").equals("PT002") ) {
                        ecoTypeReport.put("REASON_7", rs.getString("REASON_7"));
                        ecoTypeReport.put("obj_type", rs.getString("obj_type"));
                    }
                }
                else {
                    ecoTypeReport.put("obj_type", rs.getString("obj_type"));
                    ecoTypeReport.put("INCR_01", rs.getString("INCR_01"));
                    ecoTypeReport.put("INCR_02", rs.getString("INCR_02"));
                    ecoTypeReport.put("INCR_03", rs.getString("INCR_03"));
                    ecoTypeReport.put("INCR_04", rs.getString("INCR_04"));
                    ecoTypeReport.put("INCR_05", rs.getString("INCR_05"));
                    ecoTypeReport.put("INCR_06", rs.getString("INCR_06"));
                    ecoTypeReport.put("INCR_07", rs.getString("INCR_07"));
                    ecoTypeReport.put("INCR_08", rs.getString("INCR_08"));
                    ecoTypeReport.put("INCR_09", rs.getString("INCR_09"));
                    ecoTypeReport.put("INCR_10", rs.getString("INCR_10"));
                    ecoTypeReport.put("INCR_11", rs.getString("INCR_11"));
                    ecoTypeReport.put("INCR_12", rs.getString("INCR_12"));
                }
                ecoTypeReportList.add(ecoTypeReport);
            }
        }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(rs);
            PlmDBUtil.close(pstmt);
        }

        EcmUtil.logging("\nSearchEcoReportDao.getSearchTypeEcoReportListSQL():\n" + sql.toString());
        return ecoTypeReportList;
    }

    public List<Map<String, Object>> getSearchMonthlyEcoReportListSQL( KETParamMapUtil paramMap, List<Map<String, Object>> conditionList ) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Map<String, Object> ecoMonthlyReport = null;
        List<Map<String, Object>> ecoMonthlyReportList = new ArrayList<Map<String, Object>>();

        StringBuffer sql = new StringBuffer();
        sql.append( "SELECT * \n" );
        sql.append( "  FROM ( \n" );
        sql.append( "        SELECT ROWNUM row_id \n" );
        sql.append( "             , t.*           \n" );
        sql.append( "          FROM (             \n" );
        sql.append("                  SELECT T5.name orgName \n");
        sql.append("                       , T4.name creatorName \n");
        sql.append("                       , T2.creatorOid \n");
        sql.append("                       , NVL(T1.ecoCount, 0) oldEcoCount \n");
        sql.append("                       , T2.ecoCount newEcoCount \n");
        sql.append("                       , T2.MM_01, T2.MM_02, T2.MM_03, T2.MM_04, T2.MM_05, T2.MM_06, T2.MM_07, T2.MM_08, T2.MM_09, T2.MM_10, T2.MM_11, T2.MM_12 \n");
        sql.append("                    FROM \n");
        int searchYear = StringUtil.getIntParameter((String)paramMap.get("searchYear")) - 1;
        sql.append(getSearchMonthlyEcoReportListDetailSQL(paramMap, conditionList, "T1", searchYear));
        sql.append("      ,");
        searchYear++;
        sql.append(getSearchMonthlyEcoReportListDetailSQL(paramMap, conditionList, "T2", searchYear));
        sql.append("                       , WTUser T3                         \n");
        sql.append("                       , people T4                         \n");
        sql.append("                       , department T5                     \n");
        sql.append("                   WHERE 1 = 1                             \n");
        sql.append("                     AND T2.creatorOid = T1.creatorOid (+) \n");
        sql.append("                     AND T3.idA2A2 = T2.creatorOid         \n");
        sql.append("                     AND T4.idA3B4 = T3.idA2A2             \n");
        sql.append("                     AND T5.idA2A2 = T4.idA3C4             \n");
        sql.append("                ORDER BY 1, 2                              \n");
        sql.append( "               ) t                                        \n" );
        sql.append( "       )tb                                                \n" );
        sql.append( " WHERE 1 = 1                                              \n" );

        try {
            pstmt = new LoggableStatement( conn, sql.toString() );
            rs = pstmt.executeQuery();
            while(rs.next()) {
                ecoMonthlyReport = new HashMap<String, Object>();
                ecoMonthlyReport.put("orgName",     StringUtil.checkNull(rs.getString("orgName")));  // 부서명
                ecoMonthlyReport.put("creatorName", StringUtil.checkNull(rs.getString("creatorName"))); // 작성자
                ecoMonthlyReport.put("creatorOid",  StringUtil.checkNull(rs.getString("creatorOid")));
                ecoMonthlyReport.put("oldEcoCount", rs.getLong("oldEcoCount")); // 전년
                ecoMonthlyReport.put("newEcoCount", rs.getLong("newEcoCount"));  // 금년
                ecoMonthlyReport.put("mm01",        rs.getLong("MM_01"));
                ecoMonthlyReport.put("mm02",        rs.getLong("MM_02"));
                ecoMonthlyReport.put("mm03",        rs.getLong("MM_03"));
                ecoMonthlyReport.put("mm04",        rs.getLong("MM_04"));
                ecoMonthlyReport.put("mm05",        rs.getLong("MM_05"));
                ecoMonthlyReport.put("mm06",        rs.getLong("MM_06"));
                ecoMonthlyReport.put("mm07",        rs.getLong("MM_07"));
                ecoMonthlyReport.put("mm08",        rs.getLong("MM_08"));
                ecoMonthlyReport.put("mm09",        rs.getLong("MM_09"));
                ecoMonthlyReport.put("mm10",        rs.getLong("MM_10"));
                ecoMonthlyReport.put("mm11",        rs.getLong("MM_11"));
                ecoMonthlyReport.put("mm12",        rs.getLong("MM_12"));
                ecoMonthlyReportList.add(ecoMonthlyReport);
            }
        }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(rs);
            PlmDBUtil.close(pstmt);
        }

        EcmUtil.logging("\nSearchEcoReportDao.getSearchMonthlyEcoReportListSQL():\n" + sql.toString());
        return ecoMonthlyReportList;
    }

    private String getSearchMonthlyEcoReportListDetailSQL( KETParamMapUtil paramMap, List<Map<String, Object>> conditionList, String tableAlias, int searchYear ) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("        ( SELECT T1.idA3B7 creatorOid \n");
        sql.append("               , COUNT(*) ecoCount           \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "01', 1)), 0) MM_01 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "02', 1)), 0) MM_02 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "03', 1)), 0) MM_03 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "04', 1)), 0) MM_04 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "05', 1)), 0) MM_05 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "06', 1)), 0) MM_06 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "07', 1)), 0) MM_07 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "08', 1)), 0) MM_08 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "09', 1)), 0) MM_09 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "00', 1)), 0) MM_10 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "11', 1)), 0) MM_11 \n");
        sql.append("               , NVL(SUM(DECODE(TO_CHAR(T1.createStampA2, 'YYYYMM'), '" + searchYear + "12', 1)), 0) MM_12 \n");
        // prodMoldCls
        if ( paramMap.get("prodMoldCls").equals("PT001") ) {//제품ECO
            sql.append("            FROM KETProdChangeOrder T1 \n");
        } else {//금형ECO
            sql.append("            FROM KETMoldChangeOrder T1 \n");
        }
        sql.append("           WHERE 1 = 1 \n");

        for ( Map<String, Object> condistion : conditionList ) {
            KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

            //개발양산구분
            String devYnstr = (String)map.get("devYn");
            if ( devYnstr != null && devYnstr.trim().length() > 0 && !devYnstr.equalsIgnoreCase("null") ) {
                sql.append("             AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.devYn", devYnstr, false)).append("    \n");
            }

            //사업부구분
            String busistr = (String)map.get("divisionFlag");
            if ( busistr != null && busistr.length() > 0 ) {
                sql.append("             AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.divisionFlag", busistr, false)).append("    \n");
            }

            //부서명
            String orgNamestr = (String)map.get("orgName");
            if ( orgNamestr != null && orgNamestr.length() > 0 ) {
                sql.append("             AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.deptname", orgNamestr, false)).append("    \n");
            }

            //작성자OID
            String creator = (String)map.get("creator");
            if ( creator != null && creator.length() > 0 ) {
                int i = 0;
                String[] arr = new String[creator.split(", ").length];
                StringTokenizer createOidstrToken = new StringTokenizer(creator, ", ");
                while (createOidstrToken.hasMoreTokens()) {
                    arr[i] = Long.toString(CommonUtil.getOIDLongValue(createOidstrToken.nextToken()));
                    i++;
                }
                sql.append("             AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.idA3B7", arr, false)).append("    \n");
            }

            //상태
            String sancStateFlagstr = (String)map.get("sancStateFlag");
            if ( sancStateFlagstr != null && sancStateFlagstr.length() > 0 ) {
                if ( sancStateFlagstr.equals("INWORK") ) {
                    sql.append("             AND T1.stateState <> 'APPROVED' \n");
                }
                else if ( sancStateFlagstr.equals("COMLETE") ) {
                    sql.append("             AND T1.stateState = 'APPROVED' \n");
                }
            }
        }

        sql.append("             AND T1.createStampA2 BETWEEN TO_DATE('" + searchYear + "0101" + "', 'YYYYMMDD') AND TO_DATE('" + searchYear + "1231" + " 235959.86399', 'YYYYMMDD HH24MISS.SSSSS') \n");
        sql.append("           GROUP BY T1.idA3B7) " + tableAlias + " \n");
        return sql.toString();
    }
}
