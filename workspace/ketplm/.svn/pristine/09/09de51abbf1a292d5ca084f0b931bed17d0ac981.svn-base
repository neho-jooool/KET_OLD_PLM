package e3ps.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class CarDao {

    private Connection conn;

    public CarDao(Connection conn){
        this.conn = conn;
    }

	public List<Map<String, Object>> searchCarList( List<Map<String, Object>> conditionList ) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Map<String, Object> carSchedule = null;
        List<Map<String, Object>> carScheduleList = new ArrayList<Map<String, Object>>();

        StringBuffer sql = new StringBuffer();
        sql.append( " SELECT *                                        \n");
        sql.append( "   FROM (                                        \n");
        sql.append( "         SELECT NC_OEM.NAME     CUSTOMER         \n");
        sql.append( "               ,OEM.NAME        CARTYPE          \n");
        sql.append( "               ,NC_MP.NAME      FORMTYPE         \n");
        sql.append( "               ,NC_MP.CODE      FORMTYPECODE         \n");
        sql.append( "               ,MP.MODELNAME    MODELNAME        \n");
        sql.append( "               ,MP.TOTAL        TOTAL            \n");
        sql.append( "               ,FN_GET_OEMPLAN_OEMENDDATE(MP.IDA2A2, '1') OP1            \n");
        sql.append( "               ,FN_GET_OEMPLAN_OEMENDDATE(MP.IDA2A2, '2') OP2            \n");
        sql.append( "               ,FN_GET_OEMPLAN_OEMENDDATE(MP.IDA2A2, '3') OP3            \n");
        sql.append( "               ,FN_GET_OEMPLAN_OEMENDDATE(MP.IDA2A2, '4') OP4            \n");
        sql.append( "               ,FN_GET_OEMPLAN_OEMENDDATE(MP.IDA2A2, '5') OP5            \n");
        sql.append( "               ,FN_GET_OEMPLAN_OEMENDDATE_SOP(MP.IDA2A2)  OP6            \n");
        sql.append( "               ,(SELECT TO_CHAR(OP.OEMENDDATE,'YYYY') FROM OEMPLAN OP,OEMPROJECTTYPE OEM WHERE OP.IDA3A3 = OEM.IDA2A2(+) AND OEM.NAME = 'SOP' AND IDA3B3 = MP.IDA2A2) AS OP6_YYYY            \n");
        sql.append( "               ,(SELECT TO_CHAR(OP.OEMENDDATE,'YYYYMMDD') FROM OEMPLAN OP,OEMPROJECTTYPE OEM WHERE OP.IDA3A3 = OEM.IDA2A2(+) AND OEM.NAME = 'SOP' AND IDA3B3 = MP.IDA2A2) AS OP6_YYYYMMDD            \n");
        sql.append( "               ,MP.CLASSNAMEA2A2||':'||MP.IDA2A2          OID            \n");
        sql.append( "               ,MP.CREATESTAMPA2                          CREATEDATE     \n");
        sql.append( "               ,NC_OEM_ABBR.ABBR                          ABBR     \n");
        sql.append( "           FROM ModelPlan      MP             \n");
        sql.append( "               ,OEMProjectType OEM            \n");
        sql.append( "               ,NUMBERCODE     NC_OEM         \n");
        sql.append( "               ,NUMBERCODE     NC_MP          \n");
        sql.append( "               ,NUMBERCODEVALUE  NC_OEM_ABBR  \n");
        sql.append( "          WHERE MP.IDA3B3  = OEM.IDA2A2(+)    \n");
        sql.append( "            AND OEM.IDA3A4 = NC_OEM.IDA2A2(+) \n");
        sql.append( "            AND MP.IDA3A3  = NC_MP.IDA2A2(+)  \n");
        sql.append( "            AND NC_OEM.CODETYPE  =  NC_OEM_ABBR.CODETYPE(+)  \n");
        sql.append( "            AND NC_OEM.CODE      =  NC_OEM_ABBR.CODE(+)  \n");
        sql.append( "            AND NC_OEM_ABBR.LANG = 'ko'  \n");
        sql.append( "        ) CAR                                 \n");
        sql.append( "  WHERE 1 = 1                                 \n");

        for ( Map<String, Object> condistion : conditionList ) {
            KETParamMapUtil map = KETParamMapUtil.getMap(condistion);
            
            
            if ( map.getString("customerEvent1Txt") != "" ) {
                sql.append("     AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("CUSTOMER", map.getString("customerEvent1Txt"), true)).append("    \n");
            }
            
            if ( map.getString("sop") != "" ) {
        	sql.append("     AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("OP6_YYYY", map.getString("sop"), true)).append("    \n");
            }
            
            if ( map.getString("todaySop") != "" ) {
        	sql.append("     AND OP6_YYYYMMDD > TO_CHAR(SYSDATE,'YYYYMMDD')    \n");
            }

            if ( map.getString("criticalCustomer") != "" ) {
                sql.append("     AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("ABBR", map.getString("criticalCustomer"), true)).append("    \n");
            }
            
            if ( map.getString("formType") != "" ) {
                sql.append("     AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("FORMTYPECODE", map.getString("formType"), true)).append("    \n");
            }
            
            
            if ( map.getString("customer") != "" ) {
                sql.append("     AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("CUSTOMER", map.getString("customer"), true)).append("    \n");
            }

            if ( map.getString("cartype") != "" ) {
                sql.append("     AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("CARTYPE", map.getString("cartype"), true)).append("    \n");
            }

            if ( map.getString("customerViewTypeVal") != null && map.getString("customerViewTypeVal") != "" ) {

                String orCheck = "0";
                if ( map.getString("customerViewTypeVal").indexOf("1") > -1 ) {
                    if ( map.getString("predate") != "" ) {
                        String predate = map.getString("predate");
                        predate = predate.substring(0, 4)+predate.substring(5, 7)+predate.substring(8, 10);
                        sql.append("     AND OP1 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");

                        orCheck = "1";
                    }

                    if ( map.getString("postdate") != "" ) {
                        String postdate = map.getString("postdate");
                        postdate = postdate.substring(0, 4)+postdate.substring(5, 7)+postdate.substring(8, 10);
                        sql.append("     AND OP1 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");

                        orCheck = "2";
                    }
                }

                if ( map.getString("customerViewTypeVal").indexOf("2") > -1 ) {
                    if ( map.getString("predate") != "" ) {
                        String predate = map.getString("predate");
                        predate = predate.substring(0, 4)+predate.substring(5, 7)+predate.substring(8, 10);
                        if ( orCheck == "1" || orCheck == "2" ) {
                            sql.append("      OR OP2 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("     AND OP2 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "1";
                    }

                    if ( map.getString("postdate") != "" ) {
                        String postdate = map.getString("postdate");
                        postdate = postdate.substring(0, 4)+postdate.substring(5, 7)+postdate.substring(8, 10);
                        if ( orCheck == "1" ) {
                            sql.append("     AND OP2 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("      OR OP2 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "2";
                    }
                }

                if ( map.getString("customerViewTypeVal").indexOf("3") > -1 ) {
                    if ( map.getString("predate") != "" ) {
                        String predate = map.getString("predate");
                        predate = predate.substring(0, 4)+predate.substring(5, 7)+predate.substring(8, 10);
                        if ( orCheck == "1" || orCheck == "2" ) {
                            sql.append("      OR OP3 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("     AND OP3 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "1";
                    }

                    if ( map.getString("postdate") != "" ) {
                        String postdate = map.getString("postdate");
                        postdate = postdate.substring(0, 4)+postdate.substring(5, 7)+postdate.substring(8, 10);
                        if ( orCheck == "1" ) {
                            sql.append("     AND OP3 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("      OR OP3 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "2";
                    }
                }

                if ( map.getString("customerViewTypeVal").indexOf("4") > -1 ) {
                    if ( map.getString("predate") != "" ) {
                        String predate = map.getString("predate");
                        predate = predate.substring(0, 4)+predate.substring(5, 7)+predate.substring(8, 10);
                        if ( orCheck == "1" || orCheck == "2" ) {
                            sql.append("      OR OP4 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("     AND OP4 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "1";
                    }

                    if ( map.getString("postdate") != "" ) {
                        String postdate = map.getString("postdate");
                        postdate = postdate.substring(0, 4)+postdate.substring(5, 7)+postdate.substring(8, 10);
                        if ( orCheck == "1" ) {
                            sql.append("     AND OP4 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("      OR OP4 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "2";
                    }
                }

                if ( map.getString("customerViewTypeVal").indexOf("5") > -1 ) {
                    if ( map.getString("predate") != "" ) {
                        String predate = map.getString("predate");
                        predate = predate.substring(0, 4)+predate.substring(5, 7)+predate.substring(8, 10);
                        if ( orCheck == "1" || orCheck == "2" ) {
                            sql.append("      OR OP5 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("     AND OP5 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "1";
                    }

                    if ( map.getString("postdate") != "" ) {
                        String postdate = map.getString("postdate");
                        postdate = postdate.substring(0, 4)+postdate.substring(5, 7)+postdate.substring(8, 10);
                        if ( orCheck == "1" ) {
                            sql.append("     AND OP5 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("      OR OP5 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "2";
                    }
                }

                if ( map.getString("customerViewTypeVal").indexOf("6") > -1 ) {
                    if ( map.getString("predate") != "" ) {
                        String predate = map.getString("predate");
                        predate = predate.substring(0, 4)+predate.substring(5, 7)+predate.substring(8, 10);
                        if ( orCheck == "1" || orCheck == "2" ) {
                            sql.append("      OR OP6 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("     AND OP6 > TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "1";
                    }

                    if ( map.getString("postdate") != "" ) {
                        String postdate = map.getString("postdate");
                        postdate = postdate.substring(0, 4)+postdate.substring(5, 7)+postdate.substring(8, 10);
                        if ( orCheck == "1" ) {
                            sql.append("     AND OP6 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }
                        else {
                            sql.append("      OR OP6 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
                        }

                        orCheck = "2";
                    }
                }
            }
        }
        sql.append( "  ORDER BY CREATEDATE \n");

        try {
            pstmt = new LoggableStatement( conn, sql.toString() );
            rs = pstmt.executeQuery();
            while(rs.next()) {
                carSchedule = new HashMap<String, Object>();
                carSchedule.put("customer",  StringUtil.checkNull(rs.getString("CUSTOMER")));
                carSchedule.put("carType",   StringUtil.checkNull(rs.getString("CARTYPE")));
                carSchedule.put("formType",  StringUtil.checkNull(rs.getString("FORMTYPE")));
                carSchedule.put("modelName", StringUtil.checkNull(rs.getString("MODELNAME")));
                carSchedule.put("total",     StringUtil.checkNull(rs.getString("TOTAL")));
                carSchedule.put("op1",       StringUtil.checkNull(rs.getString("OP1")));
                carSchedule.put("op2",       StringUtil.checkNull(rs.getString("OP2")));
                carSchedule.put("op3",       StringUtil.checkNull(rs.getString("OP3")));
                carSchedule.put("op4",       StringUtil.checkNull(rs.getString("OP4")));
                carSchedule.put("op5",       StringUtil.checkNull(rs.getString("OP5")));
                carSchedule.put("op6",       StringUtil.checkNull(rs.getString("OP6")));
                carSchedule.put("oid",       rs.getString("OID"));
                carScheduleList.add(carSchedule);
            }
	    }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(rs);
            PlmDBUtil.close(pstmt);
        }
        return carScheduleList;
    }
}
