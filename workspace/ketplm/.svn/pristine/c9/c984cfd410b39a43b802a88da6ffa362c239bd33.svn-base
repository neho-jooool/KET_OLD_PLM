package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;

/**
 * 클래스명 : MoldPlanDao
 * 설명 :
 * 작성자 : 홍길동
 * 작성일자 : 2010. 10. 14.
 */
public class MoldPlanDao {

    private Connection conn;
    private LoggableStatement pstmt;
    private ResultSet rs;

    public MoldPlanDao( Connection conn ) {
        this.conn = conn;
    }

    /**
     * 함수명 : searchMoldPlanList
     * 설명 :
     * @param searchCon
     * @return
     * @throws Exception
     * 작성자 : 오승연
     * 작성일자 : 2010. 10. 14.
     */
    public List<Map<String, Object>> searchMoldPlanList( List<Map<String, Object>> conditionList ) throws Exception {

        List<Map<String, Object>> planList  = new ArrayList<Map<String, Object>>();
        Map<String, Object> plan = new HashMap<String, Object>();

        StringBuffer sql = new StringBuffer();
        sql.append( "SELECT *                                                                               \n" );
        sql.append( "  FROM (                                                                               \n" );
        sql.append( "        SELECT  p.classnamea2a2 || ':' || p.ida2a2              oid                    \n" );
        sql.append( "              , p.plantype                                      plan_type              \n" );
        sql.append( "              , p.dieno                                         die_no                 \n" );
        sql.append( "              , p.partno                                        part_no                \n" );
        sql.append( "              , p.partname                                      part_name              \n" );
        sql.append( "              , ( SELECT pe.name                                                       \n" );
        sql.append( "                    FROM people pe                                                     \n" );
        sql.append( "                   WHERE pe.ida3b4 = p.ida3c4 )                 prod_owner             \n" );
        sql.append( "              , ( SELECT pe.name                                                       \n" );
        sql.append( "                    FROM people pe                                                     \n" );
        sql.append( "                   WHERE pe.ida3b4 = p.ida3d4 )                 mold_owner             \n" );
        sql.append( "              , p.currentprocess                                cur_proc               \n" );
        sql.append( "              , to_char(p.currentprocplandate, 'YYYY-MM-DD')    cur_proc_plan_date     \n" );
//        sql.append( "            , decode(p.schedulestatus, 'C', '완료', 'D', '지연', 'P', '진행', 'R', '등록중', '','등록중')         status                                   \n" );
        sql.append( "              , case when to_char(p.currentprocplandate,'YYYYMMDD') >= to_char(sysdate,'YYYYMMDD') AND (p.schedulestatus = 'D' OR p.schedulestatus = 'P')  \n" );
        sql.append( "                   then '진행'  \n" );
        sql.append( "                     when to_char(p.currentprocplandate,'YYYYMMDD') < to_char(sysdate,'YYYYMMDD') AND (p.schedulestatus = 'D' OR p.schedulestatus = 'P')   \n" );
        sql.append( "                   then '지연' \n" );
        sql.append( "                     else decode(p.schedulestatus, 'C', '완료', 'D', '지연', 'P', '진행', 'R', '등록중', '') end status                                    \n" );
        sql.append( "              , decode(p.result,'',p.measuretype,p.result )     measure_type           \n" );
        sql.append( "              , p.regbasis                                      reg_basis              \n" );
        sql.append( "              , to_char(p.basisdate, 'YYYY-MM-DD')              basis_date             \n" );
        sql.append( "              , p.modifydesc                                    modify_desc            \n" );
        sql.append( "              , FN_GET_VENDORNAME(p.vendorflag, p.vendorcode)   vendor_name            \n" );
        sql.append( "              , p.proddeptname                                  prod_dept_name         \n" );
        sql.append( "              , ( SELECT po.ecoid                                                      \n" );
        sql.append( "                    FROM ketprodchangeorder po                                         \n" );
        sql.append( "                   WHERE po.ida2a2 = p.ida3b4 )                 prod_eco_id            \n" );
        sql.append( "              , ( SELECT mo.ecoid                                                      \n" );
        sql.append( "                    FROM ketmoldchangeorder mo                                         \n" );
        sql.append( "                   WHERE mo.ida2a2 = p.ida3a4 )                 mold_eco_id            \n" );
        sql.append( "              , nvl(to_char(p.proddwgactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.proddwgplandate, 'YYYY-MM-DD') || ')' )             prod_dwg_date    \n" );
        sql.append( "              , nvl(to_char(p.moldchangeactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.moldchangeplandate, 'YYYY-MM-DD') || ')' )       mold_chg_date    \n" );
        sql.append( "              , nvl(to_char(p.workactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.workplandate, 'YYYY-MM-DD') || ')' )                   work_date        \n" );
        sql.append( "              , nvl(to_char(p.storeactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.storeplandate, 'YYYY-MM-DD') || ')' )                 store_date       \n" );
        sql.append( "              , nvl(to_char(p.assembleactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.assembleplandate, 'YYYY-MM-DD') || ')' )           assembly_date    \n" );
//        sql.append( "              , nvl(to_char(p.workactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.workplandate, 'YYYY-MM-DD') || ')' )                 work_date        \n" );
        sql.append( "              , nvl(to_char(p.tryactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.tryplandate, 'YYYY-MM-DD') || ')' )                     try_date         \n" );
        sql.append( "              , nvl(to_char(p.testActualDate, 'YYYY-MM-DD'),  '(' || to_char(p.testPlanDate, 'YYYY-MM-DD') || ')' )                   test_date        \n" );
        sql.append( "              , nvl(to_char(p.approveActualDate, 'YYYY-MM-DD'),  '(' || to_char(p.approvePlanDate, 'YYYY-MM-DD') || ')' )             approve_date     \n" );
        sql.append( "              , p.failaction                                    fail_action            \n" );
        sql.append( "              , p.result                                        result                 \n" );
        sql.append( "              , to_char(p.measureDate, 'YYYY-MM-DD')            measure_date           \n" );
        sql.append( "              , p.plandesc                                      plan_desc              \n" );
        sql.append( "              , to_char(p.createstampa2, 'YYYY-MM-DD')          createstampa2          \n" );
        sql.append( "              , ( SELECT pe.name                                                       \n" );
        sql.append( "                    FROM people pe                                                     \n" );
        sql.append( "                   WHERE pe.ida3b4 = p.ida3owner )              owner_name             \n" );
        sql.append( "              , DEPTNAME                                        owner_dept             \n" );
        sql.append( "              , ATTRIBUTE1                                                             \n" );
        sql.append( "              , ATTRIBUTE2                                                             \n" );
        sql.append( "           FROM ketmoldchangeplan p                                                    \n" );
        sql.append( "          WHERE 1 = 1                                                                  \n" );

        for ( Map<String, Object> condistion : conditionList ) {
            KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

            if( map.getString("fromPlanDate").length() > 0 || map.getString("toPlanDate").length() > 0  ) {
                sql.append( "            AND ( ( p.proddwgplandate >= nvl( to_date( '"    + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.proddwgplandate)             \n" );
                sql.append( "               AND  p.proddwgplandate <= nvl( to_date( '"    + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.proddwgplandate) )      \n" );
                sql.append( "             OR ( p.moldchangeplandate >= nvl( to_date( '"   + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.moldchangeplandate)          \n" );
                sql.append( "               AND  p.moldchangeplandate <= nvl( to_date( '" + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.moldchangeplandate) )   \n" );
                sql.append( "             OR ( p.storeplandate >= nvl( to_date( '"        + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.storeplandate)               \n" );
                sql.append( "               AND  p.storeplandate <= nvl( to_date( '"      + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.storeplandate) )        \n" );
                sql.append( "             OR ( p.workplandate >= nvl( to_date( '"         + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.workplandate)                \n" );
                sql.append( "               AND  p.workplandate <= nvl( to_date( '"       + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.workplandate) )         \n" );
                sql.append( "             OR ( p.assembleplandate >= nvl( to_date( '"     + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.assembleplandate)            \n" );
                sql.append( "               AND  p.assembleplandate <= nvl( to_date( '"   + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.assembleplandate) )     \n" );
                sql.append( "             OR ( p.tryplandate >= nvl( to_date( '"          + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.tryplandate)                 \n" );
                sql.append( "               AND  p.tryplandate <= nvl( to_date( '"        + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.tryplandate) )          \n" );
                sql.append( "             OR ( p.testplandate >= nvl( to_date( '"         + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.testplandate)                \n" );
                sql.append( "               AND  p.testplandate <= nvl( to_date( '"       + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.testplandate) )         \n" );
                sql.append( "             OR ( p.approveplandate >= nvl( to_date( '"      + map.getString("fromPlanDate") + "', 'YYYY-MM-DD'), p.approveplandate)             \n" );
                sql.append( "               AND  p.approveplandate <= nvl( to_date( '"    + map.getString("toPlanDate") + "', 'YYYY-MM-DD')+0.9999, p.approveplandate) )      \n" );
                sql.append( "                )  \n" );
            }

            if( map.getString("fromActualDate").length() > 0 || map.getString("toActualDate").length() > 0 ) {
                sql.append( "            AND ( ( p.proddwgactualdate >= nvl( to_date( '"      + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.proddwgactualdate)            \n" );
                sql.append( "               AND  p.proddwgactualdate <= nvl( to_date( '"      + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.proddwgactualdate) )     \n" );
                sql.append( "             OR ( p.moldchangeactualdate >= nvl( to_date( '"     + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.moldchangeactualdate)         \n" );
                sql.append( "               AND  p.moldchangeactualdate <= nvl( to_date( '"   + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.moldchangeactualdate) )  \n" );
                sql.append( "             OR ( p.storeactualdate >= nvl( to_date( '"          + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.storeactualdate)              \n" );
                sql.append( "               AND  p.storeactualdate <= nvl( to_date( '"        + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.storeactualdate) )       \n" );
                sql.append( "             OR ( p.workactualdate >= nvl( to_date( '"           + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.workactualdate)               \n" );
                sql.append( "               AND  p.workactualdate <= nvl( to_date( '"         + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.workactualdate) )        \n" );
                sql.append( "             OR ( p.assembleactualdate >= nvl( to_date( '"       + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.assembleactualdate)           \n" );
                sql.append( "               AND  p.assembleactualdate <= nvl( to_date( '"     + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.assembleactualdate) )    \n" );
                sql.append( "             OR ( p.tryactualdate >= nvl( to_date( '"            + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.tryactualdate)                \n" );
                sql.append( "               AND  p.tryactualdate <= nvl( to_date( '"          + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.tryactualdate) )         \n" );
                sql.append( "             OR ( p.testactualdate >= nvl( to_date( '"           + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.testactualdate)               \n" );
                sql.append( "               AND  p.testactualdate <= nvl( to_date( '"         + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.testactualdate) )        \n" );
                sql.append( "             OR ( p.approveactualdate >= nvl( to_date( '"        + map.getString("fromActualDate") + "', 'YYYY-MM-DD'), p.approveactualdate)            \n" );
                sql.append( "               AND  p.approveactualdate <= nvl( to_date( '"      + map.getString("toActualDate") + "', 'YYYY-MM-DD')+0.9999, p.approveactualdate) )     \n" );
                sql.append( "                )  \n" );
            }
            if( map.getString("dieNo").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.dieno", map.getString("dieNo"), true)).append("    \n");
            }
            if( map.getString("partNo").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.partno", map.getString("partNo"), true)).append("    \n");
            }
            if( map.getString("partName").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.partname", map.getString("partName"), true)).append("    \n");
            }
            if( map.getString("planType").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.plantype", map.getString("planType"), true)).append("    \n");
            }

            if( map.getString("prodOwnerOid").length() > 0 ) {
                ArrayList<String> arr = new ArrayList<String>();
                StringTokenizer token = new StringTokenizer(map.getString("prodOwnerOid"), ", ");
                while (token.hasMoreTokens()) {
                    arr.add(KETObjectUtil.getIda2a2(token.nextToken()));
                }
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.ida3c4", KETStringUtil.join(arr, ","), false)).append("    \n");
            }

            if( map.getString("moldOwnerOid").length() > 0 ) {
                ArrayList<String> arr = new ArrayList<String>();
                StringTokenizer token = new StringTokenizer(map.getString("moldOwnerOid"), ", ");
                while (token.hasMoreTokens()) {
                    arr.add(KETObjectUtil.getIda2a2(token.nextToken()));
                }
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.ida3d4", KETStringUtil.join(arr, ","), false)).append("    \n");
            }

            if( map.getString("prodDeptName").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.proddeptname", map.getString("prodDeptName"), true)).append("    \n");
            }

            if( map.getString("measureType").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.measuretype", map.getString("measureType"), true)).append("    \n");
            }

            if( map.getString("vendorCode").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.vendorcode", map.getString("vendorCode"), true)).append("    \n");
            }

            if( map.getString("currentProcess").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.currentprocess", map.getString("currentProcess"), true)).append("    \n");
            }

            if( map.getString("status").length() > 0 ) {
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.schedulestatus", map.getString("status"), true)).append("    \n");
            }

            if( map.getString("OwnerOid").length() > 0 ) {
                ArrayList<String> arr = new ArrayList<String>();
                StringTokenizer token = new StringTokenizer(map.getString("OwnerOid"), ", ");
                while (token.hasMoreTokens()) {
                    arr.add(KETObjectUtil.getIda2a2(token.nextToken()));
                }
                sql.append("            AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.ida3owner", KETStringUtil.join(arr, ","), false)).append("    \n");
            }
        }
        sql.append( "          ORDER BY CASE WHEN (p.schedulestatus = 'C') THEN 1 END DESC , createstampa2 DESC    \n" );
        sql.append("    ) t    \n");

        try {
            pstmt = new LoggableStatement( conn, sql.toString() );
            rs = pstmt.executeQuery();

            while( rs.next() ) {
                plan = new HashMap<String, Object>();
                plan.put( "oid",                 StringUtil.checkNull(rs.getString("oid")) );
                plan.put( "plan_type" ,          StringUtil.checkNull(rs.getString("plan_type")) );
                plan.put( "die_no" ,             StringUtil.checkNull(rs.getString("die_no")) );
                plan.put( "part_no" ,            StringUtil.checkNull(rs.getString("part_no")) );
                plan.put( "part_name" ,          StringUtil.checkNull(rs.getString("part_name")) );
                plan.put( "prod_owner" ,         StringUtil.checkNull(rs.getString("prod_owner")) );
                plan.put( "mold_owner" ,         StringUtil.checkNull(rs.getString("mold_owner")) );
                plan.put( "cur_proc" ,           StringUtil.checkNull(rs.getString("cur_proc")) );
                plan.put( "cur_proc_plan_date" , StringUtil.checkNull(rs.getString("cur_proc_plan_date")) );
                plan.put( "status" ,             StringUtil.checkNull(rs.getString("status")) );
                plan.put( "measure_type" ,       StringUtil.checkNull(rs.getString("measure_type")) );
                plan.put( "reg_basis" ,          StringUtil.checkNull(rs.getString("reg_basis")) );
                plan.put( "basis_date" ,         StringUtil.checkNull(rs.getString("basis_date")) );
                plan.put( "modify_desc" ,        StringUtil.checkNull(rs.getString("modify_desc")) );
                plan.put( "vendor_name" ,        StringUtil.checkNull(rs.getString("vendor_name")) );
                plan.put( "prod_dept_name" ,     StringUtil.checkNull(rs.getString("prod_dept_name")) );
                plan.put( "prod_eco_id" ,        StringUtil.checkNull(rs.getString("prod_eco_id")) );
                plan.put( "mold_eco_id" ,        StringUtil.checkNull(rs.getString("mold_eco_id")) );
                plan.put( "prod_dwg_date" ,      StringUtil.checkNull(rs.getString("prod_dwg_date")) );
                plan.put( "mold_chg_date" ,      StringUtil.checkNull(rs.getString("mold_chg_date")) );
                plan.put( "work_date" ,          StringUtil.checkNull(rs.getString("work_date")) );
                plan.put( "store_date" ,         StringUtil.checkNull(rs.getString("store_date")) );
                plan.put( "assembly_date" ,      StringUtil.checkNull(rs.getString("assembly_date")) );
                plan.put( "try_date" ,           StringUtil.checkNull(rs.getString("try_date")) );
                plan.put( "test_date" ,          StringUtil.checkNull(rs.getString("test_date")) );
                plan.put( "approve_date" ,       StringUtil.checkNull(rs.getString("approve_date")) );
                plan.put( "fail_action" ,        StringUtil.checkNull(rs.getString("fail_action")) );
                plan.put( "result" ,             StringUtil.checkNull(rs.getString("result")) );
                plan.put( "measure_date",        StringUtil.checkNull(rs.getString("measure_date")) );
                plan.put( "plan_desc",           StringUtil.checkNull(rs.getString("plan_desc")) );
                plan.put( "createstampa2",       StringUtil.checkNull(rs.getString("createstampa2")) );
                plan.put( "owner_name",          StringUtil.checkNull(rs.getString("owner_name")) );
                plan.put( "owner_dept",          StringUtil.checkNull(rs.getString("owner_dept")) );
                plan.put( "ATTRIBUTE1",          StringUtil.checkNull(rs.getString("ATTRIBUTE1")) );
                plan.put( "ATTRIBUTE2",          StringUtil.checkNull(rs.getString("ATTRIBUTE2")) );
                planList.add( plan );
            }
        }
        catch( Exception e ) {
            throw e;
        }
        finally {
            sql.delete( 0 , sql.length() );
            PlmDBUtil.close(rs);
            PlmDBUtil.close(pstmt);
        }
        return planList;
    }


    /**
     * 함수명 : getMoldPlanExcelList
     * 설명 :
     * @param searchCon
     * @return
     * @throws Exception
     * 작성자 : 오승연
     * 작성일자 : 2010. 10. 14.
     */
    public ArrayList<Hashtable<String, String>> getMoldPlanExcelList( Hashtable<String, String> searchCon ) throws Exception
    {
        ArrayList<Hashtable<String, String>> planList  = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> plan = null;
        int pstmtCnt = 1;

        StringBuffer sql = new StringBuffer();
//        sql.append( "SELECT t.*                                                                                                                                                                            \n" );
//        sql.append( "  FROM (                                                                                                                                                                            \n" );
        sql.append( "                SELECT ROWNUM row_id, s.*    FROM (                                                            \n" );
        sql.append( "                SELECT  p.classnamea2a2 || ':' || p.ida2a2                                            oid                                                                    \n" );
        sql.append( "                       , p.plantype                                                                       plan_type                                                            \n" );
        sql.append( "                         , p.dieno                                                                             die_no                                                                \n" );
        sql.append( "                         , p.partno                                                                            part_no                                                                \n" );
        sql.append( "                         , p.partname                                                                      part_name                                                            \n" );
        sql.append( "                         , ( SELECT pe.name                                                                                                                                        \n" );
        sql.append( "                               FROM people pe                                                                                                                                    \n" );
        sql.append( "                            WHERE pe.ida3b4 = p.ida3c4 )                                              prod_owner                                                        \n" );
        sql.append( "                         , ( SELECT pe.name                                                                                                                                        \n" );
        sql.append( "                               FROM people pe                                                                                                                                    \n" );
        sql.append( "                            WHERE pe.ida3b4 = p.ida3d4 )                                              mold_owner                                                        \n" );
        sql.append( "                         , p.currentprocess                                                                 cur_proc                                                                \n" );
        sql.append( "                         , to_char(p.currentprocplandate, 'YYYY-MM-DD')                        cur_proc_plan_date                                                \n" );
        //sql.append( "                         , decode(p.schedulestatus, 'C', '완료', 'D', '지연', 'P', '진행', 'R', '등록중', '')         status                                                                \n" );
        sql.append( "                         , case when to_char(p.currentprocplandate,'YYYYMMDD') >= to_char(sysdate,'YYYYMMDD') AND (p.schedulestatus = 'D' OR p.schedulestatus = 'P')  \n" );
        sql.append( "                                 then '진행'  \n" );
        sql.append( "                                when to_char(p.currentprocplandate,'YYYYMMDD') < to_char(sysdate,'YYYYMMDD') AND (p.schedulestatus = 'D' OR p.schedulestatus = 'P') \n" );
        sql.append( "                                 then '지연' \n" );
        sql.append( "                         else decode(p.schedulestatus, 'C', '완료', 'D', '지연', 'P', '진행', 'R', '등록중', '') end status                                     \n" );
        sql.append( "                         , p.measuretype                                                                   measure_type                                                        \n" );
        sql.append( "                         , p.regbasis                                                                        reg_basis                                                            \n" );
        sql.append( "                         , to_char(p.basisdate, 'YYYY-MM-DD')                                      basis_date                                                            \n" );
        sql.append( "                         , p.modifydesc                                                                      modify_desc                                                        \n" );
        sql.append( "                         , FN_GET_VENDORNAME(p.vendorflag, p.vendorcode)                vendor_name                                                        \n" );
        sql.append( "                         , p.proddeptname                                                                 prod_dept_name                                                    \n" );
        sql.append( "                         , ( SELECT po.ecoid                                                                                                                                        \n" );
        sql.append( "                              FROM ketprodchangeorder po                                                                                                                    \n" );
        sql.append( "                             WHERE po.ida2a2 = p.ida3b4 )                                          prod_eco_id                                                        \n" );
        sql.append( "                         , ( SELECT mo.ecoid                                                                                                                                        \n" );
        sql.append( "                              FROM ketmoldchangeorder mo                                                                                                                \n" );
        sql.append( "                            WHERE mo.ida2a2 = p.ida3a4 )                                          mold_eco_id                                                        \n" );
        sql.append( "                         , nvl(to_char(p.proddwgactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.proddwgplandate, 'YYYY-MM-DD') || ')' )                   prod_dwg_date    \n" );
        sql.append( "                         , nvl(to_char(p.moldchangeactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.moldchangeplandate, 'YYYY-MM-DD') || ')' )        mold_chg_date    \n" );
        sql.append( "                         , nvl(to_char(p.workactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.workplandate, 'YYYY-MM-DD') || ')' )                                work_date            \n" );
        sql.append( "                         , nvl(to_char(p.storeactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.storeplandate, 'YYYY-MM-DD') || ')' )                                store_date            \n" );
        sql.append( "                         , nvl(to_char(p.assembleactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.assembleplandate, 'YYYY-MM-DD') || ')' )                    assembly_date        \n" );
//        sql.append( "                         , nvl(to_char(p.workactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.workplandate, 'YYYY-MM-DD') || ')' )                                work_date            \n" );
        sql.append( "                         , nvl(to_char(p.tryactualdate, 'YYYY-MM-DD'),  '(' || to_char(p.tryplandate, 'YYYY-MM-DD') || ')' )                                    try_date                \n" );
        sql.append( "                         , nvl(to_char(p.testActualDate, 'YYYY-MM-DD'),  '(' || to_char(p.testPlanDate, 'YYYY-MM-DD') || ')' )                                test_date            \n" );
        sql.append( "                         , nvl(to_char(p.approveActualDate, 'YYYY-MM-DD'),  '(' || to_char(p.approvePlanDate, 'YYYY-MM-DD') || ')' )                    approve_date        \n" );
        sql.append( "                         , p.failAction                                                                         fail_action                                                            \n" );
        sql.append( "                         , p.result                                                                             rs                                                                        \n" );
        sql.append( "                         , to_char(p.measureDate, 'YYYY-MM-DD')                                  measure_date                                                        \n" );
        sql.append( "                         , p.plandesc                                                                        plan_desc                                                            \n" );
        sql.append( "                         , to_char(p.createstampa2, 'YYYY-MM-DD')                                                                    createstampa2                                                        \n" );
        sql.append( "                         , ( SELECT pe.name                                                                                                                                 \n" );
        sql.append( "                                FROM people pe                                                                                                                                \n" );
        sql.append( "                             WHERE pe.ida3b4 = p.ida3owner )   owner_name                                                                                                      \n" );
        sql.append( "                     , DEPTNAME                                      owner_dept                                                                                                                           \n" );
        sql.append( "                        , ATTRIBUTE1                                                                                                                                         \n" );
        sql.append( "                        , ATTRIBUTE2                                                                                                                                           \n" );
        sql.append( "                 FROM ketmoldchangeplan p                                                                                                                                \n" );
        sql.append( "               WHERE 1 = 1                                                                                                                                                        \n" );
        if( searchCon.get("fromPlanDate").length() > 0 || searchCon.get("toPlanDate").length() > 0  )
        {
        sql.append( "                  AND ( ( p.proddwgplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.proddwgplandate)                                                     \n" );
        sql.append( "                                 AND  p.proddwgplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.proddwgplandate) )                            \n" );
        sql.append( "                          OR ( p.moldchangeplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.moldchangeplandate)                                     \n" );
        sql.append( "                                  AND  p.moldchangeplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.moldchangeplandate) )                \n" );
        sql.append( "                          OR ( p.storeplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.storeplandate)                                                             \n" );
        sql.append( "                                 AND  p.storeplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.storeplandate) )                                        \n" );
        sql.append( "                          OR ( p.workplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.workplandate)                                                             \n" );
        sql.append( "                                 AND  p.workplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.workplandate) )                                        \n" );
        sql.append( "                          OR ( p.assembleplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.assembleplandate)                                                 \n" );
        sql.append( "                                 AND  p.assembleplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.assembleplandate) )                            \n" );
        sql.append( "                          OR ( p.tryplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.tryplandate)                                                                 \n" );
        sql.append( "                                 AND  p.tryplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.tryplandate) )                                            \n" );
        sql.append( "                          OR ( p.testplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.testplandate)                                                             \n" );
        sql.append( "                                 AND  p.testplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.testplandate) )                                        \n" );
        sql.append( "                          OR ( p.approveplandate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.approveplandate)                                                 \n" );
        sql.append( "                               AND  p.approveplandate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.approveplandate) )                            \n" );
        sql.append( "                          )                                                                                                                                                                \n" );
        }

        if( searchCon.get("fromActualDate").length() > 0 || searchCon.get("toActualDate").length() > 0 )
        {
        sql.append( "                  AND ( ( p.proddwgactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.proddwgactualdate)                                                 \n" );
        sql.append( "                                 AND  p.proddwgactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.proddwgactualdate) )                        \n" );
        sql.append( "                          OR ( p.moldchangeactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.moldchangeactualdate)                                 \n" );
        sql.append( "                                  AND  p.moldchangeactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.moldchangeactualdate) )            \n" );
        sql.append( "                          OR ( p.storeactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.storeactualdate)                                                     \n" );
        sql.append( "                                 AND  p.storeactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.storeactualdate) )                                \n" );
        sql.append( "                          OR ( p.workactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.workactualdate)                                                         \n" );
        sql.append( "                                 AND  p.workactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.workactualdate) )                                    \n" );
        sql.append( "                          OR ( p.assembleactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.assembleactualdate)                                         \n" );
        sql.append( "                                 AND  p.assembleactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.assembleactualdate) )                    \n" );
        sql.append( "                          OR ( p.tryactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.tryactualdate)                                                             \n" );
        sql.append( "                                 AND  p.tryactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.tryactualdate) )                                        \n" );
        sql.append( "                          OR ( p.testactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.testactualdate)                                                         \n" );
        sql.append( "                                 AND  p.testactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.testactualdate) )                                    \n" );
        sql.append( "                          OR ( p.approveactualdate >= nvl( to_date( ?, 'YYYY-MM-DD'), p.approveactualdate)                                             \n" );
        sql.append( "                               AND  p.approveactualdate <= nvl( to_date( ?, 'YYYY-MM-DD')+0.9999, p.approveactualdate) )                        \n" );
        sql.append( "                          )                                                                                                                                                                \n" );
        }
        if( searchCon.get("dieNo").length() > 0 )
        {
            sql.append( "                  AND p.dieno LIKE ?                                                                                                                                 \n" );
        }
        if( searchCon.get("partNo").length() > 0 )
        {
            sql.append( "                  AND p.partno LIKE ?                                                                                                                                \n" );
        }
        if( searchCon.get("partName").length() > 0 )
        {
            sql.append( "                AND p.partname LIKE ?                                                                                                                            \n" );
        }

        if( searchCon.get("planType").length() > 0 )
        {
            sql.append( "                  AND p.plantype = ?                                                                                                                                \n" );
        }

        if( searchCon.get("prodOwnerOid").length() > 0 )
        {
            sql.append( "                  AND p.ida3c4 = ?                                                                                                                                    \n" );
        }

        if( searchCon.get("moldOwnerOid").length() > 0 )
        {
            sql.append( "                  AND p.ida3d4 = ?                                                                                                                                    \n" );
        }

        if( searchCon.get("prodDeptName").length() > 0 )
        {
            sql.append( "                  AND p.proddeptname LIKE ?                                                                                                                    \n" );
        }

        if( searchCon.get("measureType").length() > 0 )
        {
            sql.append( "                  AND p.measuretype = ?                                                                                                                            \n" );
        }

        if( searchCon.get("vendorCode").length() > 0 )
        {
            sql.append( "                  AND p.vendorcode = ?                                                                                                                                    \n" );
        }

        if( searchCon.get("currentProcess").length() > 0 )
        {
            sql.append( "                  AND p.currentprocess = ?                                                                                                                        \n" );
        }

        if( searchCon.get("status").length() > 0 )
        {
            sql.append( "                  AND p.schedulestatus = ?                                                                                                                        \n" );
        }

        if( searchCon.get("OwnerOid").length() > 0 )
        {
            sql.append( "                  AND p.ida3owner = ?                                                                                                                                    \n" );
        }

        if( searchCon.get("sort").length() > 0 ){
            sql.append( "              ORDER BY CASE WHEN (p.schedulestatus = 'C') THEN 1 END DESC , "+searchCon.get("sort")+"     )s            \n" );
        }else{
            sql.append( "              ORDER BY CASE WHEN (p.schedulestatus = 'C') THEN 1 END DESC , createstampa2 DESC )s                    \n" );
        }

        try
        {
//            pstmt = conn.prepareStatement(sql.toString());
            pstmt = new LoggableStatement( conn, sql.toString() );

            if( searchCon.get("fromPlanDate").length() > 0 || searchCon.get("toPlanDate").length() > 0  )
            {
                for(int tmpCnt=0 ; tmpCnt < 8 ; tmpCnt++)
                {
                    pstmt.setString( pstmtCnt++, searchCon.get("fromPlanDate") );
                    pstmt.setString( pstmtCnt++, searchCon.get("toPlanDate") );
                }
            }

            if( searchCon.get("fromActualDate").length() > 0 || searchCon.get("toActualDate").length() > 0 )
            {
                for(int tmpCnt=0 ; tmpCnt < 8 ; tmpCnt++)
                {
                    pstmt.setString( pstmtCnt++, searchCon.get("fromActualDate") );
                    pstmt.setString( pstmtCnt++, searchCon.get("toActualDate") );
                }
            }

            if( searchCon.get("dieNo").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, StringUtil.getLikeQueryString( searchCon.get("dieNo") ) );
            }
            if( searchCon.get("partNo").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, StringUtil.getLikeQueryString( searchCon.get("partNo") ) );
            }
            if( searchCon.get("partName").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, StringUtil.getLikeQueryString(searchCon.get("partName")) );
            }

            if( searchCon.get("planType").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, searchCon.get("planType") );
            }

            if( searchCon.get("prodOwnerOid").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, KETObjectUtil.getIda2a2(searchCon.get("prodOwnerOid")) );
            }

            if( searchCon.get("moldOwnerOid").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, KETObjectUtil.getIda2a2(searchCon.get("moldOwnerOid")) );
            }

            if( searchCon.get("prodDeptName").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, StringUtil.getLikeQueryString(searchCon.get("prodDeptName")) );
            }

            if( searchCon.get("measureType").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, searchCon.get("measureType") );
            }

            if( searchCon.get("currentProcess").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, searchCon.get("currentProcess") );
            }

            if( searchCon.get("status").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, searchCon.get("status") );
            }

            if( searchCon.get("OwnerOid").length() > 0 )
            {
                pstmt.setString( pstmtCnt++, searchCon.get("OwnerOid") );
            }

            rs = pstmt.executeQuery();

            while( rs.next() )
            {
                plan = new Hashtable<String, String>();

                plan.put( "plan_type" , StringUtil.checkNull(rs.getString("plan_type")) );
                plan.put( "die_no" , StringUtil.checkNull(rs.getString("die_no")) );
                plan.put( "part_no" , StringUtil.checkNull(rs.getString("part_no")) );
                plan.put( "part_name" , StringUtil.checkNull(rs.getString("part_name")) );
                plan.put( "prod_owner" , StringUtil.checkNull(rs.getString("prod_owner")) );
                plan.put( "mold_owner" , StringUtil.checkNull(rs.getString("mold_owner")) );
                plan.put( "cur_proc" , StringUtil.checkNull(rs.getString("cur_proc")) );
                plan.put( "cur_proc_plan_date" , StringUtil.checkNull(rs.getString("cur_proc_plan_date")) );
                plan.put( "status" , StringUtil.checkNull(rs.getString("status")) );
                plan.put( "measure_type" , StringUtil.checkNull(rs.getString("measure_type")) );
                plan.put( "reg_basis" , StringUtil.checkNull(rs.getString("reg_basis")) );
                plan.put( "basis_date" , StringUtil.checkNull(rs.getString("basis_date")) );
                plan.put( "modify_desc" , StringUtil.checkNull(rs.getString("modify_desc")) );
                plan.put( "vendor_name" , StringUtil.checkNull(rs.getString("vendor_name")) );
                plan.put( "prod_dept_name" , StringUtil.checkNull(rs.getString("prod_dept_name")) );
                plan.put( "prod_eco_id" , StringUtil.checkNull(rs.getString("prod_eco_id")) );
                plan.put( "mold_eco_id" , StringUtil.checkNull(rs.getString("mold_eco_id")) );
                plan.put( "prod_dwg_date" , StringUtil.checkNull(rs.getString("prod_dwg_date")) );
                plan.put( "mold_chg_date" , StringUtil.checkNull(rs.getString("mold_chg_date")) );
                plan.put( "work_date" , StringUtil.checkNull(rs.getString("work_date")) );
                plan.put( "store_date" , StringUtil.checkNull(rs.getString("store_date")) );
                plan.put( "assembly_date" , StringUtil.checkNull(rs.getString("assembly_date")) );
                plan.put( "try_date" , StringUtil.checkNull(rs.getString("try_date")) );
                plan.put( "test_date" , StringUtil.checkNull(rs.getString("test_date")) );
                plan.put( "approve_date" , StringUtil.checkNull(rs.getString("approve_date")) );
                plan.put( "fail_action" , StringUtil.checkNull(rs.getString("fail_action")) );
                plan.put( "rs" , StringUtil.checkNull(rs.getString("rs")) );
                plan.put( "measure_date", StringUtil.checkNull(rs.getString("measure_date")) );
                plan.put( "plan_desc", StringUtil.checkNull(rs.getString("plan_desc")) );
                plan.put( "createstampa2", StringUtil.checkNull(rs.getString("createstampa2")) );
                plan.put( "owner_name", StringUtil.checkNull(rs.getString("owner_name")) );
                plan.put( "owner_dept", StringUtil.checkNull(rs.getString("owner_dept")) );
                plan.put( "ATTRIBUTE1", StringUtil.checkNull(rs.getString("ATTRIBUTE1")) );
                plan.put( "ATTRIBUTE2", StringUtil.checkNull(rs.getString("ATTRIBUTE2")) );

                planList.add( plan );
            }
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0 , sql.length() );
            statementRsClose();
        }

        return planList;
    }

    /**
     * 함수명 : getNewScheduleId
     * 설명 :  양산 개조금형 일정 Id 채번
     * @return
     * @throws Exception
     * 작성자 : 오승연
     * 작성일자 : 2010. 10. 26.
     */
    public String getNewScheduleId() throws Exception
    {
        String scheduleId = "";

        StringBuffer sql = new StringBuffer();
        sql.append( "SELECT fn_get_ecm_numbering('PLAN') FROM dual" );

        try
        {
//            pstmt = conn.prepareStatement( sql.toString() );
            pstmt = new LoggableStatement( conn, sql.toString() );

            rs = pstmt.executeQuery();

            if( rs.next() )
            {
                scheduleId = rs.getString(1);
            }
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0, sql.length() );
            statementRsClose();
        }

        return scheduleId;
    }

    public int countUserComment( String scheduleId, String userId ) throws Exception
    {
        int commentCnt = 0;
        StringBuffer sql = new StringBuffer();
        sql.append( "SELECT count(*)                            \n" );
        sql.append( " FROM ketmoldchangeplanline t        \n" );
        sql.append( "WHERE t.scheduleid = ?                    \n" );
        sql.append( "    AND t.userid = ?                        \n" );

        try
        {
//            pstmt = conn.prepareStatement( sql.toString() );
            pstmt = new LoggableStatement( conn, sql.toString() );

            pstmt.setString( 1, scheduleId );
            pstmt.setString( 2, userId );

            rs = pstmt.executeQuery();

            if( rs.next() )
            {
                commentCnt = rs.getInt(1);
            }
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0, sql.length() );
            statementRsClose();
        }

        return commentCnt;
    }

    public int countMaxCommentLines( String scheduleId ) throws Exception
    {
        int commentCnt = 0;
        StringBuffer sql = new StringBuffer();
        sql.append( "SELECT nvl(MAX(lineorder), 0)            \n" );
        sql.append( " FROM ketmoldchangeplanline t        \n" );
        sql.append( "WHERE t.scheduleid = ?                    \n" );

        try
        {
//            pstmt = conn.prepareStatement( sql.toString() );
            pstmt = new LoggableStatement( conn, sql.toString() );

            pstmt.setString( 1, scheduleId );

            rs = pstmt.executeQuery();

            if( rs.next() )
            {
                commentCnt = rs.getInt(1);
            }
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0, sql.length() );
            statementRsClose();
        }

        return commentCnt;
    }

    public boolean updateUserComment( String scheduleId, String userId, String comment, String userLineOrder ) throws Exception
    {
        boolean isSuccess = false;

        StringBuffer sql = new StringBuffer();
        sql.append( "UPDATE ketmoldchangeplanline \n" );
        sql.append( "      SET plancomment = ?            \n" );
        sql.append( "          , modifydate = sysdate        \n" );
        sql.append( "WHERE scheduleid = ?                \n" );
        sql.append( "    AND userid = ?                    \n" );
        sql.append( "    AND lineorder = ?                    \n" );

        try
        {
//            pstmt = conn.prepareStatement( sql.toString() );
            pstmt = new LoggableStatement( conn, sql.toString() );

            pstmt.setString( 1, comment );
            pstmt.setString( 2, scheduleId );
            pstmt.setString( 3, userId );
            pstmt.setString( 4, userLineOrder );

            pstmt.executeUpdate();

            isSuccess = true;
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0, sql.length() );
            statementRsClose();
        }

        return isSuccess;
    }

    public boolean insertUserComment( String scheduleId, String userId, String userName, String comment, int lineOrder ) throws Exception
    {
        boolean isSuccess = false;

        StringBuffer sql = new StringBuffer();
        sql.append( "INSERT INTO ketmoldchangeplanline     \n" );
        sql.append( "                  ( scheduleid                         \n" );
        sql.append( "                  ,  lineorder                           \n" );
        sql.append( "                  ,  userid                           \n" );
        sql.append( "                  ,  username                       \n" );
        sql.append( "                  ,  plancomment                   \n" );
        sql.append( "                  ,  createdate                       \n" );
        sql.append( "                  ,  modifydate )                   \n" );
        sql.append( "         VALUES                                    \n" );
        sql.append( "               ( ?                                    \n" );
        sql.append( "               , ?                                    \n" );
        sql.append( "               , ?                                    \n" );
        sql.append( "               , ?                                    \n" );
        sql.append( "               , ?                                    \n" );
        sql.append( "               , sysdate                            \n" );
        sql.append( "               , sysdate )                            \n" );

        try
        {
//            pstmt = conn.prepareStatement( sql.toString() );
            pstmt = new LoggableStatement( conn, sql.toString() );

            pstmt.setString( 1, scheduleId );
            pstmt.setInt( 2, lineOrder+1 );
            pstmt.setString( 3, userId );
            pstmt.setString( 4, userName );
            pstmt.setString( 5, comment );

            pstmt.executeUpdate();

            isSuccess = true;
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0, sql.length() );
            statementRsClose();
        }

        return isSuccess;
    }

    public boolean deleteUserComment( String scheduleId, String userId, String userLineOrder ) throws Exception
    {
        boolean isSuccess = false;

        StringBuffer sql = new StringBuffer();
        sql.append( "DELETE FROM ketmoldchangeplanline \n" );
        sql.append( "WHERE scheduleid = ?                        \n" );
        sql.append( "    AND userid = ?                            \n" );
        sql.append( "    AND lineorder = ?                            \n" );

        try
        {
//            pstmt = conn.prepareStatement( sql.toString() );
            pstmt = new LoggableStatement( conn, sql.toString() );

            pstmt.setString( 1, scheduleId );
            pstmt.setString( 2, userId );
            pstmt.setString( 3, userLineOrder );

            pstmt.executeUpdate();

            isSuccess = true;
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0, sql.length() );
            statementRsClose();
        }

        return isSuccess;

    }

    public ArrayList<Hashtable<String, String>> getUserComments( String scheduleId ) throws Exception
    {
        ArrayList<Hashtable<String, String>> commentList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> commentData = null;

        StringBuffer sql = new StringBuffer();
        sql.append( "SELECT t.scheduleid                schedule_id            \n" );
        sql.append( "         , t.lineorder                    line_order                \n" );
        sql.append( "         , t.userid                        user_id                    \n" );
        sql.append( "         , t.username                    user_name                \n" );
        sql.append( "         , t.plancomment            plan_comment        \n" );
        sql.append("          , to_char(t.modifydate, 'YYYY-MM-DD')   createdate             \n");
        sql.append( " FROM ketmoldchangeplanline t                            \n" );
        sql.append( "WHERE t.scheduleid = ?                                        \n" );
        sql.append( "ORDER BY t.lineorder desc                                    \n" );

        try
        {
//            pstmt = conn.prepareStatement( sql.toString() );
            pstmt = new LoggableStatement( conn, sql.toString() );

            pstmt.setString( 1, scheduleId );

            rs = pstmt.executeQuery();

            while( rs.next() )
            {
                commentData = new Hashtable<String, String>();
                commentData.put("schedule_id", StringUtil.checkNull(rs.getString("schedule_id")));
                commentData.put("line_order", StringUtil.checkNull(rs.getString("line_order")));
                commentData.put("user_id", StringUtil.checkNull(rs.getString("user_id")));
                commentData.put("user_name", StringUtil.checkNull(rs.getString("user_name")));
                commentData.put("plan_comment", StringUtil.checkNull(rs.getString("plan_comment")));
                commentData.put("createdate", StringUtil.checkNull(rs.getString("createdate")));
                commentList.add( commentData );
            }
        }
        catch( Exception e )
        {
            throw e;
        }
        finally
        {
            sql.delete( 0, sql.length() );
            statementRsClose();
        }

        return commentList;
    }


    /**
     * 함수명 : statementRsClose
     * 설명 :
     * @throws Exception
     * 작성자 : 오승연
     * 작성일자 : 2010. 10. 14.
     */
    public void statementRsClose() throws Exception
    {
        if( rs != null )
            rs.close();

        if( pstmt != null )
            pstmt.close();
    }

}
