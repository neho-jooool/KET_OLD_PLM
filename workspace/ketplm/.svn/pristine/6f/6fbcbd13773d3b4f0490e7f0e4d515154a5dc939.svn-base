/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProjectReportDao.java
 * 작성자 : 김경희
 * 작성일자 : 2011. 5. 3.
 */
package e3ps.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.query.LoggableStatement;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : ProjectReportDao
 * 설명 : 프로젝트 리포트 조회
 * 작성자 : 김경희
 * 작성일자 : 2011. 5. 3.
 */
public class ProjectReportDao {

    private Connection conn;

    public ProjectReportDao(Connection conn){
        this.conn = conn;
    }

    public ProjectReportDao(){
        super();
    }

    /**
     * 함수명 : ViewProductProjectList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 3.
     */
    public ArrayList ViewProductProjectList ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> productProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> productProject = null;
        String division = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select 'P' pjttype                                                                                                                                                       \n" );
            sb.append( "        , n.name                                                                                                                                                         \n" );
            sb.append( "        , pr.statestate statestate                                                                                                                               \n" );
            sb.append( "        , decode(pr.pjtstate,'4','지연','정상') pjtstate                                                                                                     \n" );
            sb.append( "        , TO_CHAR(ex.execenddate, 'YYYY') execenddate                                                                                            \n" );
            sb.append( "        , count(pr.statestate)                                                                                                                                   \n" );
            sb.append( "  from PRODUCTPROJECT pr                                                                                                                                     \n" );
            sb.append( "        , EXTENDSCHEDULEDATA ex                                                                                                                          \n" );
            sb.append( "        , NUMBERCODE n                                                                                                                                           \n" );
            sb.append( "        , ProjectViewDepartmentLink pv                                                                                                                       \n" );
            sb.append( "        , (select ida3b5                                                                                                                                             \n" );
            sb.append( "                   , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                                \n" );
            sb.append( "             from (select ida3b5                                                                                                                                    \n" );
            sb.append( "                              , ida3a5 customerno                                                                                                                \n" );
            sb.append( "                              , row_number() over  (partition by ida3b5 order by ida3a5) rn                                                  \n" );
            sb.append( "                              , count(*) over (partition by ida3b5) cnt                                                                                  \n" );
            sb.append( "                        from PROJECTSUBCONTRACTORLINK)                                                                                               \n" );
            sb.append( "            where level = cnt                                                                                                                                        \n" );
            sb.append( "       start with rn = 1                                                                                                                                                 \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                                    \n" );
            sb.append( " where pr.ida2a2 in (select max(ida2a2)                                                                                                                  \n" );
            sb.append( "                                 from PRODUCTPROJECT                                                                                                         \n" );
            sb.append( "                                group by ida3b8 )                                                                                                                    \n" );
            sb.append( "     and pr.ida3a8 = ex.ida2a2                                                                                                                                   \n" );
            if (((String)hash.get("division")).length() > 0){
                division = (String)hash.get("division") + "%";
                sb.append( " and pr.teamtype like '" + division + "'                                                                                                                     \n" );
            }
            sb.append( "     and pr.ida2a2 = pv.ida3a5(+)                                                                                                                                \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( " and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                             \n" );
            }
            sb.append( "     and pr.ida3d9 = n.ida2a2                                                                                                                                    \n" );
            sb.append( "     and pr.ida2a2 = ps.ida3b5(+)                                                                                                                             \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( " and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                                   \n" );
            }
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( " and ex.planstartdate < '" + year +"0101'                                                                                                            \n" );
                sb.append( " and (ex.execenddate between '" + (String)hash.get("year") +"0101' and sysdate                                               \n" );
                sb.append( "         or ex.execenddate is null)                                                                                                                          \n" );
            }
            sb.append( "     and pr.statestate not in ('WITHDRAWN')                                                                                                              \n" );//취소프로젝트 제외
            sb.append( "group by n.name, pr.statestate, decode(pr.pjtstate,'4','지연','정상'), TO_CHAR(ex.execenddate, 'YYYY')                       \n" );
            sb.append( "union                                                                                                                                                                        \n" );
            sb.append( "select 'R' pjttype                                                                                                                                                       \n" );
            sb.append( "        , n.name                                                                                                                                                         \n" );
            sb.append( "        , re.statestate statestate                                                                                                                               \n" );
            sb.append( "        , ''                                                                                                                                                                   \n" );
            sb.append( "        , TO_CHAR(ex.execenddate, 'YYYY') execenddate                                                                                             \n" );
            sb.append( "        , count(re.statestate)                                                                                                                                      \n" );
            sb.append( "  from REVIEWPROJECT re                                                                                                                                       \n" );
            sb.append( "        , EXTENDSCHEDULEDATA ex                                                                                                                            \n" );
            sb.append( "        , NUMBERCODE n                                                                                                                                            \n" );
            sb.append( "        , (select ida3b5                                                                                                                                               \n" );
            sb.append( "                   , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                                \n" );
            sb.append( "             from (select ida3b5                                                                                                                                    \n" );
            sb.append( "                              , ida3a5 customerno                                                                                                                 \n" );
            sb.append( "                              , row_number() over  (partition by ida3b5 order by ida3a5) rn                                                   \n" );
            sb.append( "                              , count(*) over (partition by ida3b5) cnt                                                                                    \n" );
            sb.append( "                      from PROJECTSUBCONTRACTORLINK)                                                                                               \n" );
            sb.append( "           where level = cnt                                                                                                                                         \n" );
            sb.append( "      start with rn = 1                                                                                                                                               \n" );
            sb.append( "      connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                                      \n" );
            sb.append( " where re.ida2a2 in (select max(ida2a2)                                                                                                                  \n" );
            sb.append( "                                 from REVIEWPROJECT                                                                                                            \n" );
            sb.append( "                           group by ida3b8 )                                                                                                                         \n" );
            sb.append( "    and re.ida3a8 = ex.ida2a2                                                                                                                                   \n" );
            if (((String)hash.get("division")).length() > 0){
                sb.append( "and re.attr1 like '" + division + "'                                                                                                                          \n" );
            }
            if (((String)hash.get("dept")).length() > 0){
                sb.append( "and re.ida3a10 = '" +  (String)hash.get("dept") + "'                                                                                             \n" );
            }
            sb.append( "    and re.ida3d9 = n.ida2a2                                                                                                                                     \n" );
            sb.append( "    and re.ida2a2 = ps.ida3b5(+)                                                                                                                              \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( " and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                                  \n" );
            }
            if (((String)hash.get("year")).length() > 0){
                sb.append( "     and ex.planstartdate < '" + year +"0101'                                                                                                       \n" );
                sb.append( "     and (ex.execenddate between '" + (String)hash.get("year") +"0101' and sysdate                                           \n" );
                sb.append( "             or ex.execenddate is null)                                                                                                                       \n" );
            }
            sb.append( "     and re.statestate not in ('WITHDRAWN')                                                                                                              \n" );//취소프로젝트 제외
            sb.append( "group by n.name, re.statestate, TO_CHAR(ex.execenddate, 'YYYY')                                                                          \n" );
            sb.append( "union                                                                                                                                                                       \n" );
            sb.append( "select 'S' pjttype, n.name,  t.statestate, t.pjtstate, TO_CHAR(execenddate, 'YYYY') execenddate, count(t.statestate)          \n" );
            if (((String)hash.get("year")).length() > 0){
                sb.append( "  from (select case when ex.planstartdate < '" + (String)hash.get("year") + "0101' then 'O'                                  \n" );
                sb.append( "                             else 'N'                                                                                                                                   \n" );
                sb.append( "                             end as statestate                                                                                                                    \n" );
            }else{
                sb.append( "  from (select ''    statestate                                                                                                                                \n" );
            }
            sb.append( "                   , '' pjtstate                                                                                                                                           \n" );
            sb.append( "                   , pr.ida3d9 ida3d9                                                                                                                                \n" );
            sb.append( "                   , ex.planstartdate planstartdate                                                                                                            \n" );
            sb.append( "                   , ex.execenddate execenddate                                                                                                             \n" );
            sb.append( "             from PRODUCTPROJECT pr                                                                                                                        \n" );
            sb.append( "                   , EXTENDSCHEDULEDATA ex                                                                                                                \n" );
            sb.append( "                   , ProjectViewDepartmentLink pv                                                                                                            \n" );
            sb.append( "                   , (select ida3b5                                                                                                                                    \n" );
            sb.append( "                              , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                                                 \n" );
            sb.append( "                        from (select ida3b5                                                                                                                        \n" );
            sb.append( "                                         , ida3a5 customerno                                                                                                     \n" );
            sb.append( "                                         , row_number() over  (partition by ida3b5 order by ida3a5) rn                                       \n" );
            sb.append( "                                         , count(*) over (partition by ida3b5) cnt                                                                        \n" );
            sb.append( "                                   from PROJECTSUBCONTRACTORLINK)                                                                                 \n" );
            sb.append( "                      where level = cnt                                                                                                                             \n" );
            sb.append( "                 start with rn = 1                                                                                                                                   \n" );
            sb.append( "                 connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                         \n" );
            sb.append( "           where pr.ida2a2 in (select max(ida2a2)                                                                                                      \n" );
            sb.append( "                                           from PRODUCTPROJECT                                                                                             \n" );
            sb.append( "                                     group by ida3b8 )                                                                                                             \n" );
            sb.append( "               and pr.ida3a8 = ex.ida2a2                                                                                                                      \n" );
            if (((String)hash.get("division")).length() > 0){
                sb.append( "           and pr.teamtype like '" + division + "'                                                                                                     \n" );
            }
            sb.append( "               and pr.ida2a2 = pv.ida3a5(+)                                                                                                                    \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( "           and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                  \n" );
            }
            sb.append( "               and pr.ida2a2 = ps.ida3b5(+)                                                                                                                 \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( "           and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                                                             \n" );
            }
            sb.append( "               and pr.statestate not in ('WITHDRAWN')                                                                                                 \n" );//취소프로젝트 제외
            sb.append( "           ) t                                                                                                                                                              \n" );
            sb.append( "         , NUMBERCODE n                                                                                                                                         \n" );
            sb.append( " where t.ida3d9 = n.ida2a2                                                                                                                                    \n" );
            if (((String)hash.get("year")).length() > 0){
                sb.append( "     and t.planstartdate < '" + year +"0101'                                                                                                        \n" );
                sb.append( "     and (t.execenddate between '" + (String)hash.get("year") +"0101' and sysdate                                            \n" );
                sb.append( "             or t.execenddate is null)                                                                                                                        \n" );
            }
            sb.append( "group by n.name, t.statestate, t.pjtstate, TO_CHAR(execenddate, 'YYYY')                                                               \n" );

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                productProject = new Hashtable<String, String>();
                productProject.put("pjtType", StringUtil.checkNull(rs.getString(1))); //개발, 검토 구분
                productProject.put("devType", StringUtil.checkNull(rs.getString(2))); // 개발유형
                productProject.put("state", StringUtil.checkNull(rs.getString(3))); // 진행상태
                productProject.put("pjtState", StringUtil.checkNull(rs.getString(4))); // 지연여부
                productProject.put("endDate", StringUtil.checkNull(rs.getString(5))); // 완료년도
                productProject.put("count", StringUtil.checkNull(rs.getString(6))); // 건수

                productProjectList.add(productProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return productProjectList;
    }

    /**
     * 함수명 : ViewMoldProjectList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 10.
     */
    public ArrayList ViewMoldProjectList ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldProject = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select mi.itemtype                                                                                                                                                   \n" );
            sb.append( "        , mp.statestate                                                                                                                                                  \n" );
            sb.append( "        , decode(mp.pjtstate,'4','지연','정상') pjtstate                                                                                                 \n" );
            sb.append( "        , mi.making                                                                                                                                                  \n" );
            sb.append( "        , n.name                                                                                                                                                         \n" );
            sb.append( "        , TO_CHAR(ex.execenddate, 'YYYY') execenddate                                                                                            \n" );
            sb.append( "        , count(mi.itemtype)                                                                                                                                         \n" );
            sb.append( "  from MOLDPROJECT mp                                                                                                                                        \n" );
            sb.append( "        , MOLDITEMINFO mi                                                                                                                                        \n" );
            sb.append( "        , NUMBERCODE n                                                                                                                                           \n" );
            sb.append( "        , PRODUCTPROJECT pr                                                                                                                                  \n" );
            sb.append( "        , EXTENDSCHEDULEDATA ex                                                                                                                              \n" );
            sb.append( "        , ProjectViewDepartmentLink pv                                                                                                                       \n" );
            sb.append( "        , (select ida3b5                                                                                                                                             \n" );
            sb.append( "                   , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                                \n" );
            sb.append( "             from (select ida3b5                                                                                                                                    \n" );
            sb.append( "                              , ida3a5 customerno                                                                                                                \n" );
            sb.append( "                              , row_number() over  (partition by ida3b5 order by ida3a5) rn                                                  \n" );
            sb.append( "                              , count(*) over (partition by ida3b5) cnt                                                                                  \n" );
            sb.append( "                        from PROJECTSUBCONTRACTORLINK)                                                                                               \n" );
            sb.append( "            where level = cnt                                                                                                                                        \n" );
            sb.append( "       start with rn = 1                                                                                                                                                 \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                                    \n" );
            sb.append( " where mp.ida2a2 in (select max(ida2a2)                                                                                                              \n" );
            sb.append( "                                   from MOLDPROJECT                                                                                                          \n" );
            sb.append( "                                group by ida3b8 )                                                                                                                    \n" );
            sb.append( "     and mp.ida3a10 = mi.ida2a2                                                                                                                              \n" );
            sb.append( "     and mi.ida3c3 = n.ida2a2                                                                                                                                    \n" );
            sb.append( "     and mi.ida3a3 = pr.ida2a2                                                                                                                               \n" );
            sb.append( "     and mp.ida3a8 = ex.ida2a2                                                                                                                                   \n" );
            sb.append( "     and pr.ida2a2 = ps.ida3b5(+)                                                                                                                            \n" );
            sb.append( "     and pr.ida2a2 = pv.ida3a5(+)                                                                                                                               \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( " and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                             \n" );
            }
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( " and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                                   \n" );
            }
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( " and ex.planstartdate < '" + year +"0101'                                                                                                            \n" );
                sb.append( " and (ex.execenddate between '" + (String)hash.get("year") +"0101' and sysdate                                               \n" );
                sb.append( "         or ex.execenddate is null)                                                                                                                          \n" );
            }
            sb.append( "     and mp.statestate not in ('WITHDRAWN')                                                                                                              \n" );//취소프로젝트 제외
            sb.append( "group by mi.itemtype, mp.statestate, mp.pjtstate, mi.making, n.name, TO_CHAR(ex.execenddate, 'YYYY')             \n" );

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                moldProject = new Hashtable<String, String>();
                moldProject.put("itemType", StringUtil.checkNull(rs.getString(1))); //아이템 구분
                moldProject.put("state", StringUtil.checkNull(rs.getString(2))); // 진행상태
                moldProject.put("pjtState", StringUtil.checkNull(rs.getString(3))); // 지연여부
                moldProject.put("making", StringUtil.checkNull(rs.getString(4))); // 사내,외 구분
                moldProject.put("moldType", StringUtil.checkNull(rs.getString(5))); // 금형유형
                moldProject.put("endDate", StringUtil.checkNull(rs.getString(6))); // 완료년도
                moldProject.put("count", StringUtil.checkNull(rs.getString(7))); // 건수

                moldProjectList.add(moldProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldProjectList;
    }

    /**
     * 함수명 : ViewAddMoldProjectList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 10.
     */
    public ArrayList ViewAddMoldProjectList ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldProject = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select itemtype, statestate, making, moldType, TO_CHAR(execenddate, 'YYYY') execenddate, count(itemtype)             \n" );
            sb.append( "  from (select mi.itemtype                                                                                                                                           \n" );
            sb.append( "                   , case when ex.planstartdate < '" + (String)hash.get("year") + "0101' then 'O'                                         \n" );
            sb.append( "                             else 'N'                                                                                                                                            \n" );
            sb.append( "                             end as statestate                                                                                                                           \n" );
            sb.append( "                   , mi.making                                                                                                                                           \n" );
            sb.append( "                   , n.name moldType                                                                                                                                 \n" );
            sb.append( "                   , ex.execenddate                                                                                                                                      \n" );
            sb.append( "             from MOLDPROJECT mp                                                                                                                                 \n" );
            sb.append( "                   , MOLDITEMINFO mi                                                                                                                                 \n" );
            sb.append( "                   , NUMBERCODE n                                                                                                                                     \n" );
            sb.append( "                   , PRODUCTPROJECT pr                                                                                                                             \n" );
            sb.append( "                   , EXTENDSCHEDULEDATA ex                                                                                                                       \n" );
            sb.append( "                   , ProjectViewDepartmentLink pv                                                                                                                \n" );
            sb.append( "                   , (select ida3b5                                                                                                                                          \n" );
            sb.append( "                              , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                        \n" );
            sb.append( "                        from (select ida3b5                                                                                                                              \n" );
            sb.append( "                                         , ida3a5 customerno                                                                                                             \n" );
            sb.append( "                                         , row_number() over  (partition by ida3b5 order by ida3a5) rn                                           \n" );
            sb.append( "                                         , count(*) over (partition by ida3b5) cnt                                                                           \n" );
            sb.append( "                                   from PROJECTSUBCONTRACTORLINK)                                                                                    \n" );
            sb.append( "                      where level = cnt                                                                                                                                  \n" );
            sb.append( "                 start with rn = 1                                                                                                                                       \n" );
            sb.append( "                 connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                              \n" );
            sb.append( "           where mp.ida2a2 in (select max(ida2a2)                                                                                                        \n" );
            sb.append( "                                             from MOLDPROJECT                                                                                                        \n" );
            sb.append( "                                       group by ida3b8 )                                                                                                                 \n" );
            sb.append( "              and mp.ida3a10 = mi.ida2a2                                                                                                                         \n" );
            sb.append( "              and mi.ida3c3 = n.ida2a2                                                                                                                              \n" );
            sb.append( "              and mi.ida3a3 = pr.ida2a2                                                                                                                             \n" );
            sb.append( "              and mp.ida3a8 = ex.ida2a2                                                                                                                           \n" );
            sb.append( "              and pr.ida2a2 = pv.ida3a5(+)                                                                                                                           \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( "          and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                        \n" );
            }
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "          and ex.planstartdate < '" + year +"0101'                                                                                                       \n" );
                sb.append( "          and (ex.execenddate between '" + (String)hash.get("year") +"0101' and sysdate                                          \n" );
                sb.append( "                  or ex.execenddate is null)                                                                                                                     \n" );
            }
            sb.append( "              and pr.ida2a2 = ps.ida3b5(+)                                                                                                                        \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( "          and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                              \n" );
            }
            sb.append( "              and mp.statestate not in ('WITHDRAWN')                                                                                                             \n" );//취소프로젝트 제외
            sb.append( "              )                                                                                                                                                                  \n" );
            sb.append( "group by itemtype, statestate, making, moldType, TO_CHAR(execenddate, 'YYYY')                                                        \n" );

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                moldProject = new Hashtable<String, String>();
                moldProject.put("itemType", StringUtil.checkNull(rs.getString(1))); //아이템 구분
                moldProject.put("state", StringUtil.checkNull(rs.getString(2))); // 진행상태
                moldProject.put("making", StringUtil.checkNull(rs.getString(3))); // 사내,외 구분
                moldProject.put("moldType", StringUtil.checkNull(rs.getString(4))); // 금형유형
                moldProject.put("endDate", StringUtil.checkNull(rs.getString(5))); // 완료년도
                moldProject.put("count", StringUtil.checkNull(rs.getString(6))); // 건수

                moldProjectList.add(moldProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldProjectList;
    }

    /**
     * 함수명 : ViewReportList1
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    public ArrayList ViewReportList1 ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> productProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> productProject = null;
        String division = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select devType, pjtno, pjtname, planstartdate, planenddate, execenddate, term, dept, budget, execution, statestate, pjtstate, oid, prodType, customername, stateimage, pjtstateimage, pmname, oemname, designType        \n" );
            sb.append( "  from (                                                                                                                                                                 \n" );
            sb.append( "select t.classnamea2a2||':'||t.ida2a2 oid                                                                                                                    \n" );
            sb.append( "        , substr(n.name, 1, 2) devType                                                                                                                           \n" );
            sb.append( "        , pm.pjtno                                                                                                                                                       \n" );
            sb.append( "        , pm.pjtname                                                                                                                                                 \n" );
            sb.append( "        , n2.name prodType                                                                                                                                       \n" );
            sb.append( "        , t.customername                                                                                                                                             \n" );
            sb.append( "        , t.planstartdate                                                                                                                                            \n" );
            sb.append( "        , trunc(t.execenddate) execenddate                                                                                                               \n" );
            sb.append( "        , trunc(decode(t.execenddate,null,sysdate,t.execenddate))-t.planstartdate term                                               \n" );
            sb.append( "        , dp.name dept                                                                                                                                               \n" );
            sb.append( "        , co.budget                                                                                                                                                      \n" );
            sb.append( "        , co.execution                                                                                                                                                   \n" );
            sb.append( "        , t.statestate                                                                                                                                                   \n" );
            sb.append( "        , t.pjtstate                                                                                                                                                         \n" );
            sb.append( "        , t.planenddate                                                                                                                                              \n" );
            sb.append( "        , decode(t.statestate,'PROGRESS',t.statestate,'COMPLETED',t.statestate,'X')    stateimage                                \n" );
            sb.append( "        , decode(t.pjtstate,'4',t.pjtstate,'3',t.pjtstate,'0')    pjtstateimage                                                                      \n" );
            sb.append( "        , t.pmname                                                                                                                                                   \n" );
            sb.append( "        , t.oemname                                                                                                                                                  \n" );
            sb.append( "        , n3.name as designType                                                                                                                                                  \n" );
            sb.append( "  from (select case when ex.planstartdate < '" + (String)hash.get("year") + "0101' then 'O'                                      \n" );
            sb.append( "                             else 'N'                                                                                                                                        \n" );
            sb.append( "                             end as newOld                                                                                                                           \n" );
            sb.append( "                    , pr.classnamea2a2                                                                                                                           \n" );
            sb.append( "                    , pr.ida2a2                                                                                                                                          \n" );
            sb.append( "                    , pr.ida3b8                                                                                                                                          \n" );
            sb.append( "                    , pr.partno                                                                                                                                          \n" );
            sb.append( "                    , pr.ida3d9                                                                                                                                          \n" );
            sb.append( "                    , pr.ida3a9                                                                                                                                          \n" );
            sb.append( "                    , ps2.customername                                                                                                                           \n" );
            sb.append( "                    , ex.planstartdate                                                                                                                               \n" );
            sb.append( "                    , ex.planenddate                                                                                                                                 \n" );
            sb.append( "                    , ex.execenddate execenddate                                                                                                             \n" );
            sb.append( "                    , pr.statestate                                                                                                                                      \n" );
            sb.append( "                    , pr.pjtstate                                                                                                                                        \n" );
            sb.append( "                    , pl.pmname                                                                                                                                      \n" );
            sb.append( "                    , oem.name as oemname                                                                                                                                        \n" );
            sb.append( "                    , pr.ida3g9                                                                                                                                      \n" );
            sb.append( "              from PRODUCTPROJECT pr                                                                                                                         \n" );
            sb.append( "                    , EXTENDSCHEDULEDATA ex                                                                                                                  \n" );
            sb.append( "                    , OEMPROJECTTYPE oem                                                                                                                 \n" );
            sb.append( "                    , (select ida3b5                                                                                                                                     \n" );
            sb.append( "                               , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                    \n" );
            sb.append( "                         from (select ida3b5                                                                                                                        \n" );
            sb.append( "                                          , ida3a5 customerno                                                                                                        \n" );
            sb.append( "                                          , row_number() over  (partition by ida3b5 order by ida3a5) rn                                      \n" );
            sb.append( "                                          , count(*) over (partition by ida3b5) cnt                                                                          \n" );
            sb.append( "                                    from PROJECTSUBCONTRACTORLINK)                                                                               \n" );
            sb.append( "                       where level = cnt                                                                                                                             \n" );
            sb.append( "                   start with rn = 1                                                                                                                                     \n" );
            sb.append( "                   connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                            \n" );
            sb.append( "                    , (select ida3b5                                                                                                                                 \n" );
            sb.append( "                               , ltrim(sys_connect_by_path(customername,','),',') customername                                           \n" );
            sb.append( "                         from (select p.ida3b5                                                                                                                       \n" );
            sb.append( "                                          , n.name customername                                                                                                  \n" );
            sb.append( "                                          , row_number() over  (partition by p.ida3b5 order by n.name) rn                                    \n" );
            sb.append( "                                          , count(*) over (partition by ida3b5) cnt                                                                          \n" );
            sb.append( "                                    from PROJECTSUBCONTRACTORLINK p                                                                                  \n" );
            sb.append( "                                          , NUMBERCODE n                                                                                                             \n" );
            sb.append( "                                  where p.ida3a5 = n.ida2a2)                                                                                                     \n" );
            sb.append( "                        where level = cnt                                                                                                                                \n" );
            sb.append( "                   start with rn = 1                                                                                                                                     \n" );
            sb.append( "                   connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps2                                                                       \n" );
            sb.append( "                    , (select a.ida3a5 pjtid                                                                                                                         \n" );
            sb.append( "                               , b.name pmname                                                                                                                   \n" );
            sb.append( "                         from PROJECTMEMBERLINK a                                                                                                            \n" );
            sb.append( "                               , PEOPLE b                                                                                                                                \n" );
            sb.append( "                       where a.ida3b5 = b.ida3b4                                                                                                             \n" );
            sb.append( "                           and a.ida2a2 in ( select max(ida2a2)                                                                                          \n" );
            sb.append( "                                                       from PROJECTMEMBERLINK                                                                                \n" );
            sb.append( "                                                     where pjtmembertype = '0'                                                                                   \n" );
            sb.append( "                                                     group by ida3a5) ) pl                                                                                           \n" );
            sb.append( "              where pr.ida2a2 in (select max(ida2a2)                                                                                                     \n" );
            sb.append( "                                              from PRODUCTPROJECT                                                                                            \n" );
            sb.append( "                                        group by ida3b8 )                                                                                                            \n" );
            sb.append( "                  and pr.ida3a8 = ex.ida2a2                                                                                                                  \n" );
            if (((String)hash.get("division")).length() > 0){
                division = (String)hash.get("division") + "%";
                sb.append( "              and pr.teamtype like '" + division + "'                                                                                                    \n" );
            }
            sb.append( "                  and pr.ida2a2 = ps.ida3b5(+)                                                                                                               \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( "              and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                  \n" );
            }
            sb.append( "                  and pr.ida2a2 = ps2.ida3b5(+)                                                                                                              \n" );
            sb.append( "                  and pr.ida2a2 = pl.pjtid(+)                                                                                                                    \n" );
            sb.append( "                  and pr.ida3d8 = oem.ida2a2(+)                                                                                                                    \n" );
            sb.append( "                  and pr.statestate not in ('WITHDRAWN')                                                                                                 \n" );//취소프로젝트 제외
            sb.append( "           ) t                                                                                                                                                                \n" );
            sb.append( "         , E3PSProjectMaster pm                                                                                                                                  \n" );
            sb.append( "         , NUMBERCODE n                                                                                                                                            \n" );
            sb.append( "         , NUMBERCODE n2                                                                                                                                         \n" );
            sb.append( "         , NUMBERCODE n3                                                                                                                                         \n" );
            sb.append( "         , ProjectViewDepartmentLink pv                                                                                                                      \n" );
            sb.append( "         , Department dp                                                                                                                                             \n" );
            sb.append( "         , ( select ida3a3 projectOid                                                                                                                             \n" );
            sb.append( "                                , sum(budget) budget                                                                                                              \n" );
            sb.append( "                                , sum(execution) execution                                                                                                      \n" );
            sb.append( "               from COSTINFO                                                                                                                                        \n" );
            sb.append( "                     , KETPROJECTCOST                                                                                                                             \n" );
            sb.append( "             where Lpad(orderinvest,12,'0') = orderno                                                                                                   \n" );
            sb.append( "             group by ida3a3                                                                                                                                         \n" );
            sb.append( "           ) co                                                                                                                                                             \n" );
            sb.append( " where t.ida3b8 = pm.ida2a2                                                                                                                                  \n" );
            sb.append( "     and t.ida3d9 = n.ida2a2                                                                                                                                     \n" );
            sb.append( "     and t.ida3a9 = n2.ida2a2(+)                                                                                                                              \n" );
            sb.append( "     and t.ida3g9 = n3.ida2a2(+)                                                                                                                              \n" );
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( " and t.planstartdate < '" + year +"0101'                                                                                                             \n" );
                sb.append( " and (t.execenddate between '" + (String)hash.get("year") +"0101' and sysdate                                                  \n" );
                sb.append( "         or t.execenddate is null)                                                                                                                              \n" );
            }
            sb.append( "     and t.ida2a2 = pv.ida3a5(+)                                                                                                                                 \n" );
            sb.append( "     and pv.ida3b5 = dp.ida2a2(+)                                                                                                                            \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( " and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                             \n" );
            }
            sb.append( "     and t.ida2a2 = co.projectOid(+)                                                                                                                         \n" );
            if (((String)hash.get("devType")).length() > 0){
                sb.append( " and n.code = '" + (String)hash.get("devType") + "'                                                                                              \n" );
            }
            if (((String)hash.get("statestate")).length() > 0){
                sb.append( " and t.newOld = '" + (String)hash.get("statestate") + "'                                                                                         \n" );
            }
            sb.append( "           )                                                                                                                                                                 \n" );
            if (((String)hash.get("sort")).length() > 0){
                if(((String)hash.get("sort")).equals("11 ASC ")){
                    sb.append( "order by 16 ASC, 17 ASC                                                                                                                                  \n" );
                }else if(((String)hash.get("sort")).equals("11 DESC ")){
                    sb.append( "order by 16 DESC, 17 DESC                                                                                                                                \n" );
                }else{
                    sb.append( "order by "+(String)hash.get("sort")+"                                                                                                                \n" );
                }
            }

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                productProject = new Hashtable<String, String>();

                productProject.put("devType", StringUtil.checkNull(rs.getString(1))); // 개발유형
                productProject.put("pjtno", StringUtil.checkNull(rs.getString(2))); // 프로젝트 no
                productProject.put("pjtname", StringUtil.checkNull(rs.getString(3))); // 프로젝트명
                productProject.put("planstartdate", StringUtil.checkNull(rs.getString(4))); // 시작일
                productProject.put("planenddate", StringUtil.checkNull(rs.getString(5))); // 완료일(계획)
                productProject.put("execenddate", StringUtil.checkNull(rs.getString(6))); // 완료일
                productProject.put("term", StringUtil.checkNull(rs.getString(7))); // 경과일
                productProject.put("dept", StringUtil.checkNull(rs.getString(8))); // 주관부서
                if ( rs.getString(9) == null){
                    productProject.put("budget", StringUtil.checkNull(rs.getString(9))); // 예산
                }else{
                    productProject.put("budget", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(9))/1000.0), "###,###", "###,###")); // 예산
                }
                if ( rs.getString(10) == null){
                    productProject.put("execution", StringUtil.checkNull(rs.getString(10))); // 실적
                }else{
                    productProject.put("execution", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(10))/1000.0), "###,###", "###,###")); // 실적
                }
                productProject.put("statestate", StringUtil.checkNull(rs.getString(11))); // 상태
                productProject.put("pjtstate", StringUtil.checkNull(rs.getString(12))); // 지연여부
                productProject.put("oid", StringUtil.checkNull(rs.getString(13))); //oid
                productProject.put("prodType", StringUtil.checkNull(rs.getString(14))); // 제품구분
                productProject.put("customername", StringUtil.checkNull(rs.getString(15))); // 고객
                productProject.put("pmname", StringUtil.checkNull(rs.getString(18))); // pm
                productProject.put("oemname", StringUtil.checkNull(rs.getString(19))); // 대표차종
                productProject.put("designType", StringUtil.checkNull(rs.getString(20))); // 설계구분

                productProjectList.add(productProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return productProjectList;
    }

    /**
     * 함수명 : ViewReportList2
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    public ArrayList ViewReportList2 ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> productProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> productProject = null;
        String division = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select devType, pjtno, pjtname, planstartdate, planenddate, execenddate, term, dept, budget, execution, statestate, pjtstate, oid, prodType, customername, stateimage, pjtstateimage, pmname, oemname, designType        \n" );
            sb.append( "  from (                                                                                                                                                                 \n" );
            sb.append( "select pr.classnamea2a2||':'||pr.ida2a2 oid                                                                                                              \n" );
            sb.append( "        , substr(n.name, 1, 2) devType                                                                                                                           \n" );
            sb.append( "        , pm.pjtno                                                                                                                                                       \n" );
            sb.append( "        , pm.pjtname                                                                                                                                                 \n" );
            sb.append( "        , n2.name prodType                                                                                                                                           \n" );
            sb.append( "        , ps2.customername                                                                                                                                       \n" );
            sb.append( "        , ex.planstartdate                                                                                                                                               \n" );
            sb.append( "        , trunc(ex.execenddate) execenddate                                                                                                              \n" );
            sb.append( "        , trunc(decode(ex.execenddate,null,sysdate,ex.execenddate))-ex.planstartdate term                                        \n" );
            sb.append( "        , dp.name dept                                                                                                                                                   \n" );
            sb.append( "        , co.budget                                                                                                                                                      \n" );
            sb.append( "        , co.execution                                                                                                                                                   \n" );
            sb.append( "        , pr.statestate                                                                                                                                                  \n" );
            sb.append( "        , pr.pjtstate                                                                                                                                                        \n" );
            sb.append( "        , ex.planenddate                                                                                                                                             \n" );
            sb.append( "        , decode(pr.statestate,'PROGRESS',pr.statestate,'COMPLETED',pr.statestate,'X')    stateimage                             \n" );
            sb.append( "        , decode(pr.pjtstate,'4',pr.pjtstate,'3',pr.pjtstate,'0')    pjtstateimage                                                                   \n" );
            sb.append( "        , pl.pmname                                                                                                                                                  \n" );
            sb.append( "        , oem.name as oemname                                                                                                                                                    \n" );
            sb.append( "        , n3.name as designType                                                                                                                                                  \n" );
            sb.append( "  from PRODUCTPROJECT pr                                                                                                                                     \n" );
            sb.append( "        , E3PSProjectMaster pm                                                                                                                                   \n" );
            sb.append( "        , EXTENDSCHEDULEDATA ex                                                                                                                              \n" );
            sb.append( "        , NUMBERCODE n                                                                                                                                               \n" );
            sb.append( "        , NUMBERCODE n2                                                                                                                                          \n" );
            sb.append( "        , NUMBERCODE n3                                                                                                                                          \n" );
            sb.append( "        , OEMPROJECTTYPE oem                                                                                                                                         \n" );
            sb.append( "        , (select ida3b5                                                                                                                                             \n" );
            sb.append( "                   , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                                \n" );
            sb.append( "             from (select ida3b5                                                                                                                                    \n" );
            sb.append( "                              , ida3a5 customerno                                                                                                                \n" );
            sb.append( "                              , row_number() over  (partition by ida3b5 order by ida3a5) rn                                                  \n" );
            sb.append( "                              , count(*) over (partition by ida3b5) cnt                                                                                  \n" );
            sb.append( "                        from PROJECTSUBCONTRACTORLINK)                                                                                               \n" );
            sb.append( "            where level = cnt                                                                                                                                        \n" );
            sb.append( "       start with rn = 1                                                                                                                                                 \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                                    \n" );
            sb.append( "        , (select ida3b5                                                                                                                                                 \n" );
            sb.append( "                   , ltrim(sys_connect_by_path(customername,','),',') customername                                                       \n" );
            sb.append( "             from (select p.ida3b5                                                                                                                                 \n" );
            sb.append( "                              , n.name customername                                                                                                          \n" );
            sb.append( "                              , row_number() over  (partition by p.ida3b5 order by n.name) rn                                                \n" );
            sb.append( "                              , count(*) over (partition by ida3b5) cnt                                                                                  \n" );
            sb.append( "                        from PROJECTSUBCONTRACTORLINK p                                                                                          \n" );
            sb.append( "                              , NUMBERCODE n                                                                                                                     \n" );
            sb.append( "                      where p.ida3a5 = n.ida2a2)                                                                                                             \n" );
            sb.append( "            where level = cnt                                                                                                                                        \n" );
            sb.append( "       start with rn = 1                                                                                                                                                 \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps2                                                                                   \n" );
            sb.append( "       , ProjectViewDepartmentLink pv                                                                                                                        \n" );
            sb.append( "       , Department dp                                                                                                                                               \n" );
            sb.append( "       , ( select ida3a3 projectOid                                                                                                                              \n" );
            sb.append( "                   , sum(budget) budget                                                                                                                          \n" );
            sb.append( "                   , sum(execution) execution                                                                                                                \n" );
            sb.append( "             from COSTINFO                                                                                                                                           \n" );
            sb.append( "                   , KETPROJECTCOST                                                                                                                              \n" );
            sb.append( "           where Lpad(orderinvest,12,'0') = orderno                                                                                                  \n" );
            sb.append( "           group by ida3a3                                                                                                                                           \n" );
            sb.append( "         ) co                                                                                                                                                                \n" );
            sb.append( "       , (select a.ida3a5 pjtid                                                                                                                                      \n" );
            sb.append( "                  , b.name pmname                                                                                                                                \n" );
            sb.append( "            from PROJECTMEMBERLINK a                                                                                                                     \n" );
            sb.append( "                  , PEOPLE b                                                                                                                                         \n" );
            sb.append( "          where a.ida3b5 = b.ida3b4                                                                                                                          \n" );
            sb.append( "              and a.ida2a2 in ( select max(ida2a2)                                                                                                       \n" );
            sb.append( "                                          from PROJECTMEMBERLINK                                                                                         \n" );
            sb.append( "                                        where pjtmembertype = '0'                                                                                            \n" );
            sb.append( "                                       group by ida3a5) ) pl                                                                                                     \n" );
            sb.append( " where pr.ida2a2 in (select max(ida2a2)                                                                                                                  \n" );
            sb.append( "                                 from PRODUCTPROJECT                                                                                                         \n" );
            sb.append( "                                group by ida3b8 )                                                                                                                    \n" );
            sb.append( "     and pr.ida3b8 = pm.ida2a2                                                                                                                               \n" );
            sb.append( "     and pr.ida3a8 = ex.ida2a2                                                                                                                               \n" );
            if (((String)hash.get("division")).length() > 0){
                division = (String)hash.get("division") + "%";
                sb.append( " and pr.teamtype like '" + division + "'                                                                                                                     \n" );
            }
            sb.append( "     and pr.ida3d9 = n.ida2a2                                                                                                                                    \n" );
            sb.append( "     and pr.ida3a9 = n2.ida2a2(+)                                                                                                                            \n" );
            sb.append( "     and pr.ida3d8 = oem.ida2a2(+)                                                                                                                               \n" );
            sb.append( "     and pr.ida3g9 = n3.ida2a2(+)                                                                                                                            \n" );
            sb.append( "     and pr.ida2a2 = ps.ida3b5(+)                                                                                                                             \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( " and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                                   \n" );
            }
            sb.append( "     and pr.ida2a2 = ps2.ida3b5(+)                                                                                                                          \n" );
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( " and ex.planstartdate < '" + year +"0101'                                                                                                            \n" );
                if (((String)hash.get("statestate")).length() > 0 && ((String)hash.get("statestate")).equals("'COMPLETED'") ){
                    sb.append( " and ex.execenddate between '" + (String)hash.get("year") +"0101' and '" + (String)hash.get("year") +"1231'      \n" );
                }else if (((String)hash.get("statestate")).length() > 0 && ((String)hash.get("statestate")).equals("'COMPLETED','PMOINWORK','DEVASSIGN','INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'") ){
                    sb.append( " and ((pr.statestate = 'COMPLETED' and ex.execenddate between '" + year +"0101' and sysdate)                                               \n" );
                    sb.append( "         or pr.statestate in ('PMOINWORK','DEVASSIGN','INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'))            \n" );
                }else{
                    sb.append( " and (ex.execenddate between '" + year +"0101' and sysdate                                               \n" );
                    sb.append( "         or ex.execenddate is null)                                                                                                                              \n" );
                }
            }
            sb.append( "     and pr.ida2a2 = pv.ida3a5(+)                                                                                                                                \n" );
            sb.append( "     and pv.ida3b5 = dp.ida2a2(+)                                                                                                                            \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( " and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                             \n" );
            }
            sb.append( "     and pr.ida2a2 = co.projectOid(+)                                                                                                                    \n" );
            sb.append( "     and pr.ida2a2 = pl.pjtid(+)                                                                                                                                 \n" );
            if (((String)hash.get("devType")).length() > 0){
                sb.append( " and n.code = '" + (String)hash.get("devType") + "'                                                                                              \n" );
            }
            if (((String)hash.get("deleyState")).length() > 0 && ((String)hash.get("deleyState")).equals("Y")){
                sb.append( " and pr.statestate in ('INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS')        \n" );
                sb.append( " and pr.pjtstate = '4'                                                                                                                                           \n" );
            }
            if (((String)hash.get("statestate")).length() > 0){
                sb.append( " and pr.statestate in (" + (String)hash.get("statestate") + ")                                                                               \n" );
            }
            sb.append( "     and pr.statestate not in ('WITHDRAWN')                                                                                                              \n" );//취소프로젝트 제외
            sb.append( "           )                                                                                                                                                                 \n" );
            if (((String)hash.get("sort")).length() > 0){
                if(((String)hash.get("sort")).equals("11 ASC ")){
                    sb.append( "order by 16 ASC, 17 ASC                                                                                                                                  \n" );
                }else if(((String)hash.get("sort")).equals("11 DESC ")){
                    sb.append( "order by 16 DESC, 17 DESC                                                                                                                                \n" );
                }else{
                    sb.append( "order by "+(String)hash.get("sort")+"                                                                                                                \n" );
                }
            }

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                productProject = new Hashtable<String, String>();

                productProject.put("devType", StringUtil.checkNull(rs.getString(1))); // 개발유형
                productProject.put("pjtno", StringUtil.checkNull(rs.getString(2))); // 프로젝트 no
                productProject.put("pjtname", StringUtil.checkNull(rs.getString(3))); // 프로젝트명
                productProject.put("planstartdate", StringUtil.checkNull(rs.getString(4))); // 시작일
                productProject.put("planenddate", StringUtil.checkNull(rs.getString(5))); // 완료일(계획)
                productProject.put("execenddate", StringUtil.checkNull(rs.getString(6))); // 완료일
                productProject.put("term", StringUtil.checkNull(rs.getString(7))); // 경과일
                productProject.put("dept", StringUtil.checkNull(rs.getString(8))); // 주관부서
                if ( rs.getString(9) == null){
                    productProject.put("budget", StringUtil.checkNull(rs.getString(9))); // 예산
                }else{
                    productProject.put("budget", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(9))/1000.0), "###,###", "###,###")); // 예산
                }
                if ( rs.getString(10) == null){
                    productProject.put("execution", StringUtil.checkNull(rs.getString(10))); // 실적
                }else{
                    productProject.put("execution", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(10))/1000.0), "###,###", "###,###")); // 실적
                }
                productProject.put("statestate", StringUtil.checkNull(rs.getString(11))); // 상태
                productProject.put("pjtstate", StringUtil.checkNull(rs.getString(12))); // 지연여부
                productProject.put("oid", StringUtil.checkNull(rs.getString(13))); //oid
                productProject.put("prodType", StringUtil.checkNull(rs.getString(14))); // 제품구분
                productProject.put("customername", StringUtil.checkNull(rs.getString(15))); // 고객
                productProject.put("pmname", StringUtil.checkNull(rs.getString(18))); // pm
                productProject.put("oemname", StringUtil.checkNull(rs.getString(19))); // 대표차종
                productProject.put("designType", StringUtil.checkNull(rs.getString(20))); // 설계구분

                productProjectList.add(productProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return productProjectList;
    }

    /**
     * 함수명 : ViewReportList3
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    public ArrayList ViewReportList3 ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> reviewProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> reviewProject = null;
        String division = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select devType, pjtno, pjtname, planstartdate, planenddate, execenddate, term, dept, budget, execution, statestate, pjtstate, oid, prodType, customername, stateimage, pjtstateimage, pmname, oemname, designType        \n" );
            sb.append( "  from (                                                                                                                                                                 \n" );
            sb.append( "select re.classnamea2a2||':'||re.ida2a2 oid                                                                                                              \n" );
            sb.append( "        , substr(n.name, 1, 2) devType                                                                                                                       \n" );
            sb.append( "        , pm.pjtno                                                                                                                                                       \n" );
            sb.append( "        , pm.pjtname                                                                                                                                                 \n" );
            sb.append( "        , n2.name prodType                                                                                                                                       \n" );
            sb.append( "        , ps2.customername                                                                                                                                       \n" );
            sb.append( "        , ex.planstartdate                                                                                                                                               \n" );
            sb.append( "        , trunc(ex.execenddate) execenddate                                                                                                              \n" );
            sb.append( "        , trunc(decode(ex.execenddate,null,sysdate,ex.execenddate))-ex.planstartdate term                                        \n" );
            sb.append( "        , dp.name dept                                                                                                                                               \n" );
            sb.append( "        , '' budget                                                                                                                                                      \n" );
            sb.append( "        , '' execution                                                                                                                                                   \n" );
            sb.append( "        , re.statestate                                                                                                                                              \n" );
            sb.append( "        , re.pjtstate                                                                                                                                                    \n" );
            sb.append( "        , ex.planenddate                                                                                                                                             \n" );
            sb.append( "        , decode(re.statestate,'PROGRESS',re.statestate,'COMPLETED',re.statestate,'X')    stateimage                             \n" );
            sb.append( "        , decode(re.pjtstate,'4',re.pjtstate,'3',re.pjtstate,'0')    pjtstateimage                                                                   \n" );
            sb.append( "        , pl.pmname                                                                                                                                                  \n" );
            sb.append( "        , oem.name as oemname                                                                                                                                                    \n" );
            sb.append( "        , n3.name as designType                                                                                                                                                  \n" );
            sb.append( "  from REVIEWPROJECT re                                                                                                                                      \n" );
            sb.append( "        , E3PSProjectMaster pm                                                                                                                                   \n" );
            sb.append( "        , EXTENDSCHEDULEDATA ex                                                                                                                          \n" );
            sb.append( "        , NUMBERCODE n                                                                                                                                           \n" );
            sb.append( "        , NUMBERCODE n2                                                                                                                                          \n" );
            sb.append( "        , NUMBERCODE n3                                                                                                                                          \n" );
            sb.append( "        , OEMPROJECTTYPE oem                                                                                                                                             \n" );
            sb.append( "        , (select ida3b5                                                                                                                                             \n" );
            sb.append( "                    , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                               \n" );
            sb.append( "              from (select ida3b5                                                                                                                                   \n" );
            sb.append( "                               , ida3a5 customerno                                                                                                               \n" );
            sb.append( "                               , row_number() over  (partition by ida3b5 order by ida3a5) rn                                                     \n" );
            sb.append( "                               , count(*) over (partition by ida3b5) cnt                                                                                 \n" );
            sb.append( "                         from PROJECTSUBCONTRACTORLINK)                                                                                          \n" );
            sb.append( "            where level = cnt                                                                                                                                        \n" );
            sb.append( "       start with rn = 1                                                                                                                                                 \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                                    \n" );
            sb.append( "         , (select ida3b5                                                                                                                                            \n" );
            sb.append( "                    , ltrim(sys_connect_by_path(customername,','),',') customername                                                     \n" );
            sb.append( "             from (select p.ida3b5                                                                                                                                 \n" );
            sb.append( "                               , n.name customername                                                                                                         \n" );
            sb.append( "                               , row_number() over  (partition by p.ida3b5 order by n.name) rn                                               \n" );
            sb.append( "                               , count(*) over (partition by ida3b5) cnt                                                                                 \n" );
            sb.append( "                         from PROJECTSUBCONTRACTORLINK p                                                                                         \n" );
            sb.append( "                               , NUMBERCODE n                                                                                                                    \n" );
            sb.append( "                       where p.ida3a5 = n.ida2a2)                                                                                                                \n" );
            sb.append( "           where level = cnt                                                                                                                                         \n" );
            sb.append( "       start with rn = 1                                                                                                                                                 \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps2                                                                                   \n" );
            sb.append( "       , Department dp                                                                                                                                               \n" );
            sb.append( "       , (select a.ida3a5 pjtid                                                                                                                                      \n" );
            sb.append( "                  , b.name pmname                                                                                                                                \n" );
            sb.append( "            from PROJECTMEMBERLINK a                                                                                                                     \n" );
            sb.append( "                  , PEOPLE b                                                                                                                                         \n" );
            sb.append( "          where a.ida3b5 = b.ida3b4                                                                                                                          \n" );
            sb.append( "              and a.ida2a2 in ( select max(ida2a2)                                                                                                       \n" );
            sb.append( "                                          from PROJECTMEMBERLINK                                                                                         \n" );
            sb.append( "                                        where pjtmembertype = '0'                                                                                            \n" );
            sb.append( "                                       group by ida3a5) ) pl                                                                                                     \n" );
            sb.append( " where re.ida2a2 in (select max(ida2a2)                                                                                                                  \n" );
            sb.append( "                                 from REVIEWPROJECT                                                                                                          \n" );
            sb.append( "                           group by ida3b8 )                                                                                                                         \n" );
            sb.append( "     and re.ida3b8 = pm.ida2a2                                                                                                                               \n" );
            sb.append( "     and re.ida3a8 = ex.ida2a2                                                                                                                               \n" );
            if (((String)hash.get("division")).length() > 0){
                division = (String)hash.get("division") + "%";
                sb.append( " and re.attr1 like '" + division + "'                                                                                                                        \n" );
            }
            sb.append( "     and re.ida3d9 = n.ida2a2                                                                                                                                    \n" );
            sb.append( "     and re.ida3a9 = n2.ida2a2(+)                                                                                                                            \n" );

            sb.append( "     and re.ida2a2 = ps.ida3b5(+)                                                                                                                            \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( " and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                                   \n" );
            }
            sb.append( "    and re.ida2a2 = ps2.ida3b5(+)                                                                                                                            \n" );
            sb.append( "    and re.ida2a2 = pl.pjtid(+)                                                                                                                                  \n" );
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( " and ex.planstartdate < '" + year +"0101'                                                                                                            \n" );
                if (((String)hash.get("statestate")).length() > 0 && ((String)hash.get("statestate")).equals("'COMPLETED'") ){
                    sb.append( " and ex.execenddate between '" + (String)hash.get("year") +"0101' and '" + (String)hash.get("year") +"1231'      \n" );
                }else if (((String)hash.get("statestate")).length() > 0 && ((String)hash.get("statestate")).equals("'COMPLETED','PMOINWORK','DEVASSIGN','INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'") ){
                    sb.append( " and ((re.statestate = 'COMPLETED' and ex.execenddate between '" + year +"0101' and sysdate)                                               \n" );
                    sb.append( "         or re.statestate in ('PMOINWORK','DEVASSIGN','INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'))            \n" );
                }else{
                    sb.append( " and (ex.execenddate between '" + (String)hash.get("year") +"0101' and '" + (String)hash.get("year") +"1231'           \n" );
                    sb.append( "         or ex.execenddate is null)                                                                                                                      \n" );
                }
            }
            sb.append( "     and re.ida3a10 = dp.ida2a2(+)                                                                                                                           \n" );
            sb.append( "     and re.ida3d8 = oem.ida2a2(+)                                                                                                                           \n" );
            sb.append( "     and re.ida3g9 = n3.ida2a2(+)                                                                                                                            \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( " and re.ida3a10 = '" +  (String)hash.get("dept") + "'                                                                                            \n" );
            }
            if (((String)hash.get("devType")).length() > 0){
                sb.append( " and n.code = '" + (String)hash.get("devType") + "'                                                                                              \n" );
            }
            if (((String)hash.get("statestate")).length() > 0){
                sb.append( " and re.statestate in (" + (String)hash.get("statestate") + ")                                                                                   \n" );
            }
            sb.append( "     and re.statestate not in ('WITHDRAWN')                                                                                                              \n" );//취소프로젝트 제외
            sb.append( "           )                                                                                                                                                                 \n" );
            if (((String)hash.get("sort")).length() > 0){
                if(((String)hash.get("sort")).equals("11 ASC ")){
                    sb.append( "order by 16 ASC, 17 ASC                                                                                                                                  \n" );
                }else if(((String)hash.get("sort")).equals("11 DESC ")){
                    sb.append( "order by 16 DESC, 17 DESC                                                                                                                                \n" );
                }else{
                    sb.append( "order by "+(String)hash.get("sort")+"                                                                                                                \n" );
                }
            }

            pstmt = new LoggableStatement( conn, sb.toString() );
            Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                reviewProject = new Hashtable<String, String>();

                reviewProject.put("devType", StringUtil.checkNull(rs.getString(1))); // 개발유형
                reviewProject.put("pjtno", StringUtil.checkNull(rs.getString(2))); // 프로젝트 no
                reviewProject.put("pjtname", StringUtil.checkNull(rs.getString(3))); // 프로젝트명
                reviewProject.put("planstartdate", StringUtil.checkNull(rs.getString(4))); // 시작일
                reviewProject.put("planenddate", StringUtil.checkNull(rs.getString(5))); // 완료일(계획)
                reviewProject.put("execenddate", StringUtil.checkNull(rs.getString(6))); // 완료일
                reviewProject.put("term", StringUtil.checkNull(rs.getString(7))); // 경과일
                reviewProject.put("dept", StringUtil.checkNull(rs.getString(8))); // 주관부서
                if ( rs.getString(9) == null){
                    reviewProject.put("budget", StringUtil.checkNull(rs.getString(9))); // 예산
                }else{
                    reviewProject.put("budget", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(9))/1000.0), "###,###", "###,###")); // 예산
                }
                if ( rs.getString(10) == null){
                    reviewProject.put("execution", StringUtil.checkNull(rs.getString(10))); // 실적
                }else{
                    reviewProject.put("execution", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(10))/1000.0), "###,###", "###,###")); // 실적
                }
                reviewProject.put("statestate", StringUtil.checkNull(rs.getString(11))); // 상태
                reviewProject.put("pjtstate", StringUtil.checkNull(rs.getString(12))); // 지연여부
                reviewProject.put("oid", StringUtil.checkNull(rs.getString(13))); //oid
                reviewProject.put("prodType", StringUtil.checkNull(rs.getString(14))); // 제품구분
                reviewProject.put("customername", StringUtil.checkNull(rs.getString(15))); // 고객
                reviewProject.put("pmname", StringUtil.checkNull(rs.getString(18))); // pm
                reviewProject.put("oemname", StringUtil.checkNull(rs.getString(19))); // 대표차종
                reviewProject.put("designType", StringUtil.checkNull(rs.getString(20))); // 설계구분

                reviewProjectList.add(reviewProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return reviewProjectList;
    }

    /**
     * 함수명 : ViewReportList4
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 18.
     */
    public ArrayList ViewReportList4 ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldProject = null;
        String division = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select devType, pjtno, pjtname, planstartdate, planenddate, execenddate, term, dept, budget, execution, statestate, pjtstate, oid, prodType, customername, stateimage, pjtstateimage, pmname, oemname, designType        \n" );
            sb.append( "  from (                                                                                                                                                                 \n" );
            sb.append( "select mp.classnamea2a2||':'||mp.ida2a2 oid                                                                                                      \n" );
            sb.append( "        , substr(n2.name, 1, 2) devType                                                                                                                  \n" );
            sb.append( "        , pm.pjtno                                                                                                                                                   \n" );
            sb.append( "        , pm.pjtname                                                                                                                                                 \n" );
            sb.append( "        , mi.itemtype prodType                                                                                                                               \n" );
            sb.append( "        , ps2.customername                                                                                                                                   \n" );
            sb.append( "        , ex.planstartdate                                                                                                                                       \n" );
            sb.append( "        , trunc(ex.execenddate) execenddate                                                                                                          \n" );
            sb.append( "        , trunc(decode(ex.execenddate,null,sysdate,ex.execenddate))-ex.planstartdate term                                    \n" );
            sb.append( "        , dp.deptname dept                                                                                                                                   \n" );
            sb.append( "        , co.budget                                                                                                                                                  \n" );
            sb.append( "        , co.execution                                                                                                                                           \n" );
            sb.append( "        , mp.statestate                                                                                                                                          \n" );
            sb.append( "        , mp.pjtstate                                                                                                                                                \n" );
            sb.append( "        , ex.planenddate                                                                                                                                             \n" );
            sb.append( "        , decode(mp.statestate,'PROGRESS',mp.statestate,'COMPLETED',mp.statestate,'X')    stateimage                 \n" );
            sb.append( "        , decode(mp.pjtstate,'4',mp.pjtstate,'3',mp.pjtstate,'0')    pjtstateimage                                                       \n" );
            sb.append( "        , pl.pmname                                                                                                                                              \n" );
            sb.append( "        , oem.name as oemname                                                                                                                                                \n" );
            sb.append( "        , n3.name as designType                                                                                                                                              \n" );
            sb.append( "  from MOLDPROJECT mp                                                                                                                                    \n" );
            sb.append( "        , E3PSProjectMaster pm                                                                                                                               \n" );
            sb.append( "        , MOLDITEMINFO mi                                                                                                                                    \n" );
            sb.append( "        , NUMBERCODE n                                                                                                                                       \n" );
            sb.append( "        , NUMBERCODE n2                                                                                                                                      \n" );
            sb.append( "        , NUMBERCODE n3                                                                                                                                      \n" );
            sb.append( "        , OEMPROJECTTYPE oem                                                                                                                                     \n" );
            sb.append( "        , PRODUCTPROJECT pr                                                                                                                              \n" );
            sb.append( "        , ProjectViewDepartmentLink pv                                                                                                                   \n" );
            sb.append( "        , EXTENDSCHEDULEDATA ex                                                                                                                      \n" );
            sb.append( "        , (select ida3b5                                                                                                                                         \n" );
            sb.append( "                    , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                           \n" );
            sb.append( "              from (select ida3b5                                                                                                                               \n" );
            sb.append( "                               , ida3a5 customerno                                                                                                           \n" );
            sb.append( "                               , row_number() over  (partition by ida3b5 order by ida3a5) rn                                             \n" );
            sb.append( "                               , count(*) over (partition by ida3b5) cnt                                                                             \n" );
            sb.append( "                         from PROJECTSUBCONTRACTORLINK)                                                                                          \n" );
            sb.append( "            where level = cnt                                                                                                                                    \n" );
            sb.append( "       start with rn = 1                                                                                                                                             \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                                    \n" );
            sb.append( "         , (select ida3b5                                                                                                                                        \n" );
            sb.append( "                    , ltrim(sys_connect_by_path(customername,','),',') customername                                                 \n" );
            sb.append( "             from (select p.ida3b5                                                                                                                            \n" );
            sb.append( "                               , n.name customername                                                                                                     \n" );
            sb.append( "                               , row_number() over  (partition by p.ida3b5 order by n.name) rn                                       \n" );
            sb.append( "                               , count(*) over (partition by ida3b5) cnt                                                                                 \n" );
            sb.append( "                         from PROJECTSUBCONTRACTORLINK p                                                                                         \n" );
            sb.append( "                               , NUMBERCODE n                                                                                                                    \n" );
            sb.append( "                       where p.ida3a5 = n.ida2a2)                                                                                                            \n" );
            sb.append( "           where level = cnt                                                                                                                                     \n" );
            sb.append( "       start with rn = 1                                                                                                                                         \n" );
            sb.append( "       connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps2                                                                               \n" );
            sb.append( "       , ( select dieno                                                                                                                                              \n" );
            sb.append( "                   , sum(budget) budget                                                                                                                      \n" );
            sb.append( "                   , sum(execution) execution                                                                                                            \n" );
            sb.append( "             from COSTINFO                                                                                                                                       \n" );
            sb.append( "                   , KETPROJECTCOST                                                                                                                          \n" );
            sb.append( "           where Lpad(orderinvest,12,'0') = orderno                                                                                                  \n" );
            sb.append( "           group by dieno                                                                                                                                        \n" );
            sb.append( "         ) co                                                                                                                                                        \n" );
            sb.append( "      , (select a.ida3a5 pjt                                                                                                                                     \n" );
            sb.append( "                 , a.ida3b5 pm                                                                                                                                       \n" );
            sb.append( "                 , c.name deptname                                                                                                                               \n" );
            sb.append( "           from PROJECTMEMBERLINK a                                                                                                                  \n" );
            sb.append( "                 , PEOPLE b                                                                                                                                          \n" );
            sb.append( "                 , DEPARTMENT c                                                                                                                                  \n" );
            sb.append( "         where a.pjtmembertype = '0'                                                                                                                     \n" );
            sb.append( "             and a.classnamekeyroleaobjectref = 'e3ps.project.MoldProject'                                                               \n" );
            sb.append( "             and a.ida3b5 = b.ida3b4                                                                                                                         \n" );
            sb.append( "             and b.ida3c4 = c.ida2a2) dp                                                                                                                 \n" );
            sb.append( "       , (select a.ida3a5 pjtid                                                                                                                                  \n" );
            sb.append( "                  , b.name pmname                                                                                                                                \n" );
            sb.append( "            from PROJECTMEMBERLINK a                                                                                                                     \n" );
            sb.append( "                  , PEOPLE b                                                                                                                                         \n" );
            sb.append( "          where a.ida3b5 = b.ida3b4                                                                                                                          \n" );
            sb.append( "              and a.ida2a2 in ( select max(ida2a2)                                                                                                       \n" );
            sb.append( "                                          from PROJECTMEMBERLINK                                                                                         \n" );
            sb.append( "                                        where pjtmembertype = '0'                                                                                            \n" );
            sb.append( "                                       group by ida3a5) ) pl                                                                                                     \n" );
            sb.append( " where mp.ida2a2 in (select max(ida2a2)                                                                                                              \n" );
            sb.append( "                                   from MOLDPROJECT                                                                                                      \n" );
            sb.append( "                             group by ida3b8 )                                                                                                                   \n" );
            sb.append( "     and mp.ida3b8 = pm.ida2a2                                                                                                                           \n" );
            sb.append( "     and mp.ida3a10 = mi.ida2a2                                                                                                                          \n" );
            sb.append( "     and mi.ida3c3 = n.ida2a2                                                                                                                            \n" );
            sb.append( "     and pr.ida3d9 = n2.ida2a2(+)                                                                                                                        \n" );
            sb.append( "     and mi.ida3a3 = pr.ida2a2                                                                                                                               \n" );
            sb.append( "     and mp.ida3a8 = ex.ida2a2                                                                                                                           \n" );
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( " and ex.planstartdate < '" + year +"0101'                                                                                                        \n" );
                if (((String)hash.get("statestate")).length() > 0 && ((String)hash.get("statestate")).equals("'COMPLETED'") ){
                    sb.append( " and ex.execenddate between '" + (String)hash.get("year") +"0101' and '" + (String)hash.get("year") +"1231'      \n" );
                }else if (((String)hash.get("statestate")).length() > 0 && ((String)hash.get("statestate")).equals("'COMPLETED','PMOINWORK','DEVASSIGN','INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'") ){
                    sb.append( " and ((mp.statestate = 'COMPLETED' and ex.execenddate between '" + year +"0101' and sysdate)                                               \n" );
                    sb.append( "         or mp.statestate in ('PMOINWORK','DEVASSIGN','INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS'))            \n" );
                }else{
                    sb.append( " and (ex.execenddate between '" + year +"0101' and sysdate                                       \n" );
                    sb.append( "         or ex.execenddate is null)                                                                                                                  \n" );
                }
            }
            sb.append( "     and pr.ida2a2 = pv.ida3a5(+)                                                                                                                            \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( " and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                         \n" );
            }
            sb.append( "     and pr.ida2a2 = ps.ida3b5(+)                                                                                                                        \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( " and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                               \n" );
            }
            sb.append( "    and pr.ida2a2 = ps2.ida3b5(+)                                                                                                                        \n" );
            sb.append( "    and pm.pjtno = co.dieno(+)                                                                                                                           \n" );
            sb.append( "    and mp.ida2a2 = dp.pjt(+)                                                                                                                                \n" );
            sb.append( "    and mp.ida2a2 = pl.pjtid(+)                                                                                                                              \n" );
            sb.append( "    and mp.ida3d8 = oem.ida2a2(+)                                                                                                                            \n" );
            sb.append( "    and mp.ida3g9 = n3.ida2a2(+)                                                                                                                             \n" );
            if (((String)hash.get("itemType")).length() > 0){
                sb.append( " and mi.itemtype = '" + (String)hash.get("itemType") + "'                                                                                \n" );
            }
            if (((String)hash.get("deleyState")).length() > 0 && ((String)hash.get("deleyState")).equals("Y")){
                sb.append( " and mp.statestate  in ('INWORK','UNDERREVIEW','APPROVED','REJECTED','REWORK','PLANCHANGE','PROGRESS')       \n" );
                sb.append( " and mp.pjtstate = '4'                                                                                                                                       \n" );
            }
            if (((String)hash.get("statestate")).length() > 0){
                sb.append( " and mp.statestate in (" + (String)hash.get("statestate") + ")                                                                           \n" );
            }
            if (((String)hash.get("making")).length() > 0){
                sb.append( " and mi.making = '" + (String)hash.get("making") + "'                                                                                        \n" );
            }
            sb.append( " and mi.making is not null                                                                                                                                   \n" );
            if (((String)hash.get("moldType1")).length() > 0 && ((String)hash.get("moldType2")).length() > 0){
                sb.append( " and n.code in ('" + (String)hash.get("moldType1") + "','" + (String)hash.get("moldType2") + "')                         \n" );
            }else if(((String)hash.get("moldType1")).length() > 0){
                sb.append( " and n.code = '" + (String)hash.get("moldType1") + "'                                                                                    \n" );
            }
            sb.append( "     and mp.statestate not in ('WITHDRAWN')                                                                                                          \n" );//취소프로젝트 제외
            sb.append( "           )                                                                                                                                                                 \n" );
            if (((String)hash.get("sort")).length() > 0){
                if(((String)hash.get("sort")).equals("11 ASC ")){
                    sb.append( "order by 16 ASC, 17 ASC                                                                                                                              \n" );
                }else if(((String)hash.get("sort")).equals("11 DESC ")){
                    sb.append( "order by 16 DESC, 17 DESC                                                                                                                        \n" );
                }else{
                    sb.append( "order by "+(String)hash.get("sort")+"                                                                                                        \n" );
                }
            }

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                moldProject = new Hashtable<String, String>();

                moldProject.put("devType", StringUtil.checkNull(rs.getString(1))); // 개발유형
                moldProject.put("pjtno", StringUtil.checkNull(rs.getString(2))); // 프로젝트 no
                moldProject.put("pjtname", StringUtil.checkNull(rs.getString(3))); // 프로젝트명
                moldProject.put("planstartdate", StringUtil.checkNull(rs.getString(4))); // 시작일
                moldProject.put("planenddate", StringUtil.checkNull(rs.getString(5))); // 완료일(계획)
                moldProject.put("execenddate", StringUtil.checkNull(rs.getString(6))); // 완료일
                moldProject.put("term", StringUtil.checkNull(rs.getString(7))); // 경과일
                moldProject.put("dept", StringUtil.checkNull(rs.getString(8))); // 주관부서
                if ( rs.getString(9) == null){
                    moldProject.put("budget", StringUtil.checkNull(rs.getString(9))); // 예산
                }else{
                    moldProject.put("budget", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(9))/1000.0), "###,###", "###,###")); // 예산
                }
                if ( rs.getString(10) == null){
                    moldProject.put("execution", StringUtil.checkNull(rs.getString(10))); // 실적
                }else{
                    moldProject.put("execution", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(10))/1000.0), "###,###", "###,###")); // 실적
                }
                moldProject.put("statestate", StringUtil.checkNull(rs.getString(11))); // 상태
                moldProject.put("pjtstate", StringUtil.checkNull(rs.getString(12))); // 지연여부
                moldProject.put("oid", StringUtil.checkNull(rs.getString(13))); //oid
                moldProject.put("prodType", StringUtil.checkNull(rs.getString(14))); // 제품구분
                moldProject.put("customername", StringUtil.checkNull(rs.getString(15))); // 고객
                moldProject.put("pmname", StringUtil.checkNull(rs.getString(18))); // pm
                moldProject.put("oemname", StringUtil.checkNull(rs.getString(19))); // 대표차종
                moldProject.put("designType", StringUtil.checkNull(rs.getString(20))); // 설계구분

                moldProjectList.add(moldProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldProjectList;
    }

    /**
     * 함수명 : ViewReportList5
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 18.
     */
    public ArrayList ViewReportList5 ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldProject = null;
        String division = null;
        int year = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "select devType, pjtno, pjtname, planstartdate, planenddate, execenddate, term, dept, budget, execution, statestate, pjtstate, oid, prodType, customername, stateimage, pjtstateimage, pmname, oemname, designType        \n" );
            sb.append( "  from (select mp.classnamea2a2||':'||mp.ida2a2 oid                                                                                              \n" );
            sb.append( "                   , substr(n2.name, 1, 2) devType                                                                                                           \n" );
            sb.append( "                   , pm.pjtno                                                                                                                                            \n" );
            sb.append( "                   , pm.pjtname                                                                                                                                      \n" );
            sb.append( "                   , mi.itemtype prodType                                                                                                                            \n" );
            sb.append( "                   , ps2.customername                                                                                                                            \n" );
            sb.append( "                   , ex.planstartdate                                                                                                                                \n" );
            sb.append( "                   , ex.planenddate                                                                                                                                  \n" );
            sb.append( "                   , trunc(ex.execenddate) execenddate                                                                                                   \n" );
            sb.append( "                   , trunc(decode(ex.execenddate,null,sysdate,ex.execenddate))-ex.planstartdate term                             \n" );
            sb.append( "                   , dp.deptname dept                                                                                                                                \n" );
            sb.append( "                   , co.budget                                                                                                                                           \n" );
            sb.append( "                   , co.execution                                                                                                                                    \n" );
            sb.append( "                   , mp.statestate                                                                                                                                   \n" );
            sb.append( "                   , mp.pjtstate                                                                                                                                         \n" );
            sb.append( "                   , case when ex.planstartdate < '" + (String)hash.get("year") + "0101' then 'O'                                     \n" );
            sb.append( "                             else 'N'                                                                                                                                        \n" );
            sb.append( "                             end as isOldNew                                                                                                                         \n" );
            sb.append( "                   , mi.making                                                                                                                                          \n" );
            sb.append( "                   , n.name moldType                                                                                                                               \n" );
            sb.append( "                   , n.code moldTypeCode                                                                                                                        \n" );
            sb.append( "                      , decode(mp.statestate,'PROGRESS',mp.statestate,'COMPLETED',mp.statestate,'X')    stateimage           \n" );
            sb.append( "                   , decode(mp.pjtstate,'4',mp.pjtstate,'3',mp.pjtstate,'0')    pjtstateimage                                                \n" );
            sb.append( "                   , pl.pmname                                                                                                                                           \n" );
            sb.append( "                   , oem.name as oemname                                                                                                                                         \n" );
            sb.append( "                   , n3.name as designType                                                                                                                                           \n" );
            sb.append( "             from MOLDPROJECT mp                                                                                                                             \n" );
            sb.append( "                   , E3PSProjectMaster pm                                                                                                                        \n" );
            sb.append( "                   , MOLDITEMINFO mi                                                                                                                             \n" );
            sb.append( "                   , NUMBERCODE n                                                                                                                                \n" );
            sb.append( "                   , NUMBERCODE n2                                                                                                                               \n" );
            sb.append( "                   , NUMBERCODE n3                                                                                                                               \n" );
            sb.append( "                   , OEMPROJECTTYPE oem                                                                                                                              \n" );
            sb.append( "                   , PRODUCTPROJECT pr                                                                                                                       \n" );
            sb.append( "                   , ProjectViewDepartmentLink pv                                                                                                            \n" );
            sb.append( "                   , EXTENDSCHEDULEDATA ex                                                                                                                   \n" );
            sb.append( "                   , (select ida3b5                                                                                                                                  \n" );
            sb.append( "                              , ltrim(sys_connect_by_path(customerno,','),',') customerno                                                     \n" );
            sb.append( "                        from (select ida3b5                                                                                                                         \n" );
            sb.append( "                                         , ida3a5 customerno                                                                                                         \n" );
            sb.append( "                                         , row_number() over  (partition by ida3b5 order by ida3a5) rn                                       \n" );
            sb.append( "                                         , count(*) over (partition by ida3b5) cnt                                                                       \n" );
            sb.append( "                                   from PROJECTSUBCONTRACTORLINK)                                                                                    \n" );
            sb.append( "                       where level = cnt                                                                                                                                 \n" );
            sb.append( "                  start with rn = 1                                                                                                                                      \n" );
            sb.append( "                  connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps                                                                         \n" );
            sb.append( "                    , (select ida3b5                                                                                                                                 \n" );
            sb.append( "                               , ltrim(sys_connect_by_path(customername,','),',') customername                                           \n" );
            sb.append( "                         from (select p.ida3b5                                                                                                                       \n" );
            sb.append( "                                          , n.name customername                                                                                                  \n" );
            sb.append( "                                          , row_number() over  (partition by p.ida3b5 order by n.name) rn                                \n" );
            sb.append( "                                          , count(*) over (partition by ida3b5) cnt                                                                      \n" );
            sb.append( "                                    from PROJECTSUBCONTRACTORLINK p                                                                              \n" );
            sb.append( "                                          , NUMBERCODE n                                                                                                         \n" );
            sb.append( "                                  where p.ida3a5 = n.ida2a2)                                                                                                     \n" );
            sb.append( "                        where level = cnt                                                                                                                            \n" );
            sb.append( "                   start with rn = 1                                                                                                                                     \n" );
            sb.append( "                   connect by prior ida3b5 = ida3b5 and prior rn=rn-1) ps2                                                                       \n" );
            sb.append( "                    , (select dieno                                                                                                                                  \n" );
            sb.append( "                               , sum(budget) budget                                                                                                              \n" );
            sb.append( "                               , sum(execution) execution                                                                                                        \n" );
            sb.append( "                         from COSTINFO                                                                                                                           \n" );
            sb.append( "                               , KETPROJECTCOST                                                                                                                  \n" );
            sb.append( "                       where Lpad(orderinvest,12,'0') = orderno                                                                                      \n" );
            sb.append( "                       group by dieno                                                                                                                                    \n" );
            sb.append( "                      ) co                                                                                                                                                   \n" );
            sb.append( "                   , (select a.ida3a5 pjt                                                                                                                            \n" );
            sb.append( "                              , a.ida3b5 pm                                                                                                                          \n" );
            sb.append( "                              , c.name deptname                                                                                                                  \n" );
            sb.append( "                        from PROJECTMEMBERLINK a                                                                                                             \n" );
            sb.append( "                              , PEOPLE b                                                                                                                                 \n" );
            sb.append( "                              , DEPARTMENT c                                                                                                                         \n" );
            sb.append( "                      where a.pjtmembertype = '0'                                                                                                            \n" );
            sb.append( "                          and a.classnamekeyroleaobjectref = 'e3ps.project.MoldProject'                                                      \n" );
            sb.append( "                          and a.ida3b5 = b.ida3b4                                                                                                                \n" );
            sb.append( "                          and b.ida3c4 = c.ida2a2) dp                                                                                                        \n" );
            sb.append( "                   , (select a.ida3a5 pjtid                                                                                                                          \n" );
            sb.append( "                              , b.name pmname                                                                                                                    \n" );
            sb.append( "                        from PROJECTMEMBERLINK a                                                                                                             \n" );
            sb.append( "                              , PEOPLE b                                                                                                                                 \n" );
            sb.append( "                      where a.ida3b5 = b.ida3b4                                                                                                                  \n" );
            sb.append( "                          and a.ida2a2 in ( select max(ida2a2)                                                                                               \n" );
            sb.append( "                                                      from PROJECTMEMBERLINK                                                                                 \n" );
            sb.append( "                                                    where pjtmembertype = '0'                                                                                    \n" );
            sb.append( "                                                  group by ida3a5) ) pl                                                                                              \n" );
            sb.append( "           where mp.ida2a2 in (select max(ida2a2)                                                                                                        \n" );
            sb.append( "                                            from MOLDPROJECT                                                                                                 \n" );
            sb.append( "                                      group by ida3b8 )                                                                                                              \n" );
            sb.append( "               and mp.ida3b8 = pm.ida2a2                                                                                                                     \n" );
            sb.append( "               and mp.ida3a10 = mi.ida2a2                                                                                                                    \n" );
            sb.append( "               and mi.ida3c3 = n.ida2a2                                                                                                                          \n" );
            sb.append( "               and pr.ida3d9 = n2.ida2a2(+)                                                                                                                  \n" );
            sb.append( "               and mi.ida3a3 = pr.ida2a2                                                                                                                         \n" );
            sb.append( "               and mp.ida3a8 = ex.ida2a2                                                                                                                     \n" );
            if (((String)hash.get("year")).length() > 0){
                year = Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "          and ex.planstartdate < '" + year +"0101'                                                                                                   \n" );
                sb.append( "          and (ex.execenddate between '" + (String)hash.get("year") +"0101' and sysdate                                      \n" );
                sb.append( "                  or ex.execenddate is null)                                                                                                                 \n" );
            }
            sb.append( "               and pr.ida2a2 = pv.ida3a5(+)                                                                                                                  \n" );
            if (((String)hash.get("dept")).length() > 0){
                sb.append( "           and pv.ida3b5 = '" +  (String)hash.get("dept") + "'                                                                                   \n" );
            }
            sb.append( "              and pr.ida2a2 = ps.ida3b5(+)                                                                                                                   \n" );
            if (((String)hash.get("customerNo")).length() > 0){
                sb.append( "          and ps.customerno like '%" + (String)hash.get("customerNo") + "%'                                                          \n" );
            }
            sb.append( "              and pr.ida2a2 = ps2.ida3b5(+)                                                                                                                  \n" );
            sb.append( "              and pm.pjtno = co.dieno(+)                                                                                                                     \n" );
            sb.append( "              and mp.ida2a2 = dp.pjt(+)                                                                                                                      \n" );
            sb.append( "              and mp.ida2a2 = pl.pjtid(+)                                                                                                                        \n" );
            sb.append( "              and mp.ida3d8 = oem.ida2a2(+)                                                                                                                              \n" );
            sb.append( "              and mp.ida3g9 = n3.ida2a2(+)                                                                                                                           \n" );
            sb.append( "              and mp.statestate not in ('WITHDRAWN')                                                                                                 \n" );//취소프로젝트 제외
            sb.append( "              )                                                                                                                                                              \n" );
            sb.append( " where 1=1                                                                                                                                                           \n" );
            if (((String)hash.get("statestate")).length() > 0){
                sb.append( "and isOldNew = '" + (String)hash.get("statestate") + "'                                                                                      \n" );
            }
            if (((String)hash.get("itemType")).length() > 0){
                sb.append( " and prodType = '" + (String)hash.get("itemType") + "'                                                                                       \n" );
            }
            if (((String)hash.get("making")).length() > 0){
                sb.append( " and making = '" + (String)hash.get("making") + "'                                                                                               \n" );
            }
            sb.append( " and making is not null                                                                                                                                              \n" );
            if (((String)hash.get("moldType1")).length() > 0 && ((String)hash.get("moldType2")).length() > 0){
                sb.append( " and moldTypeCode in ('" + (String)hash.get("moldType1") + "','" + (String)hash.get("moldType2") + "')                       \n" );
            }else if(((String)hash.get("moldType1")).length() > 0){
                sb.append( " and moldTypeCode = '" + (String)hash.get("moldType1") + "'                                                                                      \n" );
            }
            if (((String)hash.get("sort")).length() > 0){
                if(((String)hash.get("sort")).equals("11 ASC ")){
                    sb.append( "order by 16 ASC, 17 ASC                                                                                                                                  \n" );
                }else if(((String)hash.get("sort")).equals("11 DESC ")){
                    sb.append( "order by 16 DESC, 17 DESC                                                                                                                                \n" );
                }else{
                    sb.append( "order by "+(String)hash.get("sort")+"                                                                                                                \n" );
                }
            }

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                moldProject = new Hashtable<String, String>();

                moldProject.put("devType", StringUtil.checkNull(rs.getString(1))); // 개발유형
                moldProject.put("pjtno", StringUtil.checkNull(rs.getString(2))); // 프로젝트 no
                moldProject.put("pjtname", StringUtil.checkNull(rs.getString(3))); // 프로젝트명
                moldProject.put("planstartdate", StringUtil.checkNull(rs.getString(4))); // 시작일
                moldProject.put("planenddate", StringUtil.checkNull(rs.getString(5))); // 완료일(계획)
                moldProject.put("execenddate", StringUtil.checkNull(rs.getString(6))); // 완료일
                moldProject.put("term", StringUtil.checkNull(rs.getString(7))); // 경과일
                moldProject.put("dept", StringUtil.checkNull(rs.getString(8))); // 주관부서
                if ( rs.getString(9) == null){
                    moldProject.put("budget", StringUtil.checkNull(rs.getString(9))); // 예산
                }else{
                    moldProject.put("budget", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(9))/1000.0), "###,###", "###,###")); // 예산
                }
                if ( rs.getString(10) == null){
                    moldProject.put("execution", StringUtil.checkNull(rs.getString(10))); // 실적
                }else{
                    moldProject.put("execution", StringUtil.getDouble((Object)(Double.parseDouble(rs.getString(10))/1000.0), "###,###", "###,###")); // 실적
                }
                moldProject.put("statestate", StringUtil.checkNull(rs.getString(11))); // 상태
                moldProject.put("pjtstate", StringUtil.checkNull(rs.getString(12))); // 지연여부
                moldProject.put("oid", StringUtil.checkNull(rs.getString(13))); //oid
                moldProject.put("prodType", StringUtil.checkNull(rs.getString(14))); // 제품구분
                moldProject.put("customername", StringUtil.checkNull(rs.getString(15))); // 고객
                moldProject.put("pmname", StringUtil.checkNull(rs.getString(18))); // pm
                moldProject.put("oemname", StringUtil.checkNull(rs.getString(19))); // 대표차종
                moldProject.put("designType", StringUtil.checkNull(rs.getString(20))); // 설계구분

                moldProjectList.add(moldProject);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldProjectList;
    }

    /**
     * 함수명 : ViewEarlyWarningListCnt
     * 설명 :
     * @param hash
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2010. 10. 18.
     */
    public int isChief() throws Exception{
        LoggableStatement pstmt = null;
        Connection conn = null;
        int pstmtCnt = 0;
        StringBuffer sb = null;
        ResultSet rs = null;
        int listCnt = 0;
        WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal ();
        long userid = CommonUtil.getOIDLongValue(user);

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            conn = PlmDBUtil.getConnection();

            sb = new StringBuffer();
            sb.append("select count(*)                                                                                                                            \n");
            sb.append("  from PEOPLE t                                                                                                                           \n");
            sb.append("where t.chief is not null                                                                                                                 \n");
            sb.append("    and t.ida3b4 = '" + userid + "'                                                                                                  \n");

            pstmt = new LoggableStatement( conn, sb.toString() );
//          Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                listCnt = rs.getInt(1);
            }

            Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
            PlmDBUtil.close(conn);
        }

        return listCnt;
    }

    /**
     * 함수명 : statementRsClose
     * 설명 :
     * @throws Exception
     * 작성자 : 김경희
     * 작성일자 : 2010. 10. 18.
     */
    public void statementRsClose(ResultSet rs, LoggableStatement pstmt) throws Exception
    {
        if( rs != null )
            rs.close();

        if( pstmt != null )
            pstmt.close();
    }

}
