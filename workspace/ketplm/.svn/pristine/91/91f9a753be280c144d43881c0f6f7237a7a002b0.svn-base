/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : MoldChangePlanDao.java
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 01.
 */
package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : MoldChangePlanDao
 * 설명 : 초기유동관리 조회
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 01.
 */
public class MoldChangePlanDao {

    private Connection conn;

    public MoldChangePlanDao(Connection conn){
        this.conn = conn;
    }

    /**
     * 함수명 : ViewMoldChangeList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 신대범
     * 작성일자 : 2010. 11. 01.
     */
    public ArrayList ViewMoldChangeList ( Map _map ) throws Exception{
        KETParamMapUtil map = KETParamMapUtil.getMap(_map);

        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldChangeList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldChange = null;

        try {
            sb = new StringBuffer();

            sb.append( " select     \n" );
            sb.append( "   itemcode \n" );
            sb.append( "   , itemnm  as itemname      \n" );
            sb.append( "   , mocnt     as changeno  \n" );
            sb.append( "   , decode(mocnt,0,'',decode(revisiondate,null,'','','','0','',to_char(to_date(revisiondate,'yyyymmdd'),'yyyy-mm-dd'))) as changedate            \n" );
            sb.append( "   , revisiondate                \n" );
            sb.append( "   , modesc                      \n" );
            sb.append( "   , dieno   \n" );
            sb.append( " from        \n" );
            sb.append( " (    select     t.dieno            \n" );
            sb.append( "           , t.itemcode        \n" );
            sb.append( "           , max(w.name) as itemnm            \n" );
            sb.append( "           , sum(t.mocnt) as mocnt            \n" );
            sb.append( "           , max(t.revisiondate) as revisiondate\n" );
            sb.append( "           , t.modesc            \n" );
            sb.append( "    from   keterpecohistory t, wtpartmaster w\n" );
            sb.append( "   where  t.itemcode = w.wtpartnumber(+)    \n" );
            sb.append( "    group by  t.dieno, t.itemcode, t.modesc    \n" );
//            sb.append(getQueryStr());
            sb.append( " ) t        \n" );
            sb.append( " where 1=1            \n" );

            String partno = map.getString("partno");
            if (StringUtil.checkString(partno)) {
                sb.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("dieno", partno, false)).append("        \n");
            }
            String predate = map.getString("predate");
            boolean predateExist = StringUtil.checkString(predate);
            String postdate = map.getString("postdate");
            boolean postdateExist = StringUtil.checkString(postdate);
            if (predateExist) {
                sb.append( "   and revisiondate >= ?     \n" );
            }
            if (postdateExist) {
                sb.append( "   and revisiondate <= ?     \n" );
            }
            pstmt = new LoggableStatement( conn, sb.toString() );

            if (predateExist) {
                pstmt.setString( pstmtCnt++, predate.replace("-", "") );
            }
            if (postdateExist) {
                pstmt.setString( pstmtCnt++, postdate.replace("-", "") );
            }

Kogger.debug(getClass(), ">>>>> MoldChangePlanDao.ViewMoldChangeList: SQL=\n"+pstmt.getQueryString());

            rs = pstmt.executeQuery();

            String itemCd = "";

            Hashtable ht = null;
            while (rs.next())
            {
                int s_eco_cnt = 0;
                int p_eco_cnt = 0;
                int total_eco_cnt = 0;
                int s_chg_dt = 0;
                int p_chg_dt = 0;

                itemCd = StringUtil.checkNull(rs.getString("itemcode"));
                moldChange = new Hashtable<String, String>();
                moldChange.put("row_id", StringUtil.checkNull(rs.getString("row_id")));
                moldChange.put("itemcode", itemCd);
                moldChange.put("itemname", StringUtil.checkNull(rs.getString("itemname")));
                moldChange.put("modesc", StringUtil.checkNull(rs.getString("modesc")));
                moldChange.put("dieno", StringUtil.checkNull(rs.getString("dieno")));

                if ((map.getString("predate")).length() == 0 && (map.getString("postdate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, "", "");
                }
                else if ((map.getString("predate")).length() > 0 && (map.getString("postdate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, map.getString("predate") , "");
                }
                else if ((map.getString("postdate")).length() > 0 && (map.getString("predate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, "", map.getString("postdate"));
                }
                else if ((map.getString("postdate")).length() > 0 && (map.getString("predate")).length() > 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, map.getString("predate"), map.getString("postdate"));
                }

                try{s_eco_cnt = Integer.parseInt((String)rs.getString("changeno"));}catch(Exception e){s_eco_cnt =0; }
                //Kogger.debug(getClass(), "@@@@@@@@@@@ s_eco_cnt : "+s_eco_cnt);
                try{p_eco_cnt = Integer.parseInt( (String)ht.get("eco_count") );}catch(Exception e){p_eco_cnt =0; }
                //Kogger.debug(getClass(), "@@@@@@@@@@@ p_eco_cnt : "+p_eco_cnt);
                total_eco_cnt = s_eco_cnt+p_eco_cnt;

                try{s_chg_dt = Integer.parseInt( ((String)rs.getString("revisiondate")).replace("-",""));}catch(Exception e){s_chg_dt =0;}
                //Kogger.debug(getClass(), "@@@@@@@@@@@ s_chg_dt : "+s_chg_dt);
                try{p_chg_dt = Integer.parseInt( ((String)ht.get("last_update_date")).replace("-", "") );}catch(Exception e){p_chg_dt =0; }
                //Kogger.debug(getClass(), "@@@@@@@@@@@ p_chg_dt : "+p_chg_dt);

                moldChange.put("changeno", StringUtil.checkNull( String.valueOf(total_eco_cnt) ));
                if(s_chg_dt == p_chg_dt || s_chg_dt > p_chg_dt)
                {
                moldChange.put("changedate", StringUtil.checkNull(rs.getString("changedate")));
                }else{
                    moldChange.put("changedate", StringUtil.checkNull((String)ht.get("last_update_date")));
                }

                moldChangeList.add(moldChange);
            }
        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldChangeList;
    }
    /*
     * 결과내 재검색용(Search In Result)
     */
    public ArrayList ViewMoldChangeListSIR( List<Map> mapList ) throws Exception{
        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldChangeList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldChange = null;

        try {
            sb = new StringBuffer();

            sb.append( " select     \n" );
            sb.append( "   itemcode \n" );
            sb.append( "   , itemnm  as itemname      \n" );
            sb.append( "   , mocnt     as changeno  \n" );
            sb.append( "   , decode(mocnt,0,'',decode(revisiondate,null,'','','','0','',to_char(to_date(revisiondate,'yyyymmdd'),'yyyy-mm-dd'))) as changedate            \n" );
            sb.append( "   , revisiondate                \n" );
            sb.append( "   , modesc                      \n" );
            sb.append( "   , dieno   \n" );
            sb.append( " from        \n" );
            sb.append( " (    select     t.dieno            \n" );
            sb.append( "           , t.itemcode        \n" );
            sb.append( "           , max(w.name) as itemnm            \n" );
            sb.append( "           , sum(t.mocnt) as mocnt            \n" );
            sb.append( "           , max(t.revisiondate) as revisiondate\n" );
            sb.append( "           , t.modesc            \n" );
            sb.append( "    from   keterpecohistory t, wtpartmaster w\n" );
            sb.append( "   where  t.itemcode = w.wtpartnumber(+)    \n" );
            sb.append( "    group by  t.dieno, t.itemcode, t.modesc    \n" );
//            sb.append(getQueryStr());
            sb.append( " ) t        \n" );
            sb.append( " where 1=1            \n" );

            // 결과내 재검색 loop
            for (int i = 0; i < mapList.size(); ++i) {
                KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
                String partno = paramMap.getString("partno");
                if (StringUtil.checkString(partno)) {
                    sb.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("dieno", partno, false)).append("        \n");
                }
            }

            // 결과내 재검색 loop
            String maxPredate = "", minPostdate = ""; // 변경횟수 계산 위한 기간 범위
            for (int i = 0; i < mapList.size(); ++i) {
                KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
                String predate = paramMap.getString("predate");
                if (StringUtil.checkString(predate)) {
                    sb.append( "   and revisiondate >= '").append(predate.replace("-", "")).append("'     \n" );
                    if ("".equals(maxPredate) || predate.compareTo(maxPredate) > 0) {
                        maxPredate = predate;
                    }
                }
                String postdate = paramMap.getString("postdate");
                if (StringUtil.checkString(postdate)) {
                    sb.append( "   and revisiondate <= '").append(postdate.replace("-", "")).append("'     \n" );
                }
                if ("".equals(minPostdate) || postdate.compareTo(minPostdate) < 0) {
                    minPostdate = postdate;
                }
            }
            Kogger.debug(getClass(), "maxPredate=[" + maxPredate + "], minPostdate=[" + minPostdate + "]");

            pstmt = new LoggableStatement( conn, sb.toString() );

Kogger.debug(getClass(), ">>>>> MoldChangePlanDao.ViewMoldChangeListSIR: SQL=\n"+pstmt.getQueryString());

            rs = pstmt.executeQuery();

            String itemCd = "";

            Hashtable ht = null;
            while (rs.next())
            {
                int s_eco_cnt = 0;
                int p_eco_cnt = 0;
                int total_eco_cnt = 0;
                int s_chg_dt = 0;
                int p_chg_dt = 0;

                itemCd = StringUtil.checkNull(rs.getString("itemcode"));
                moldChange = new Hashtable<String, String>();
                moldChange.put("row_id", StringUtil.checkNull(rs.getString("row_id")));
                moldChange.put("itemcode", itemCd);
                moldChange.put("itemname", StringUtil.checkNull(rs.getString("itemname")));
                moldChange.put("modesc", StringUtil.checkNull(rs.getString("modesc")));
                moldChange.put("dieno", StringUtil.checkNull(rs.getString("dieno")));

/*
                if ((map.getString("predate")).length() == 0 && (map.getString("postdate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, "", "");
                }
                else if ((map.getString("predate")).length() > 0 && (map.getString("postdate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, map.getString("predate") , "");
                }
                else if ((map.getString("postdate")).length() > 0 && (map.getString("predate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, "", map.getString("postdate"));
                }
                else if ((map.getString("postdate")).length() > 0 && (map.getString("predate")).length() > 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, map.getString("predate"), map.getString("postdate"));
                }
*/
                ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, maxPredate, minPostdate);

                try{s_eco_cnt = Integer.parseInt((String)rs.getString("changeno"));}catch(Exception e){s_eco_cnt =0; }
                //Kogger.debug(getClass(), "@@@@@@@@@@@ s_eco_cnt : "+s_eco_cnt);
                try{p_eco_cnt = Integer.parseInt( (String)ht.get("eco_count") );}catch(Exception e){p_eco_cnt =0; }
                //Kogger.debug(getClass(), "@@@@@@@@@@@ p_eco_cnt : "+p_eco_cnt);
                total_eco_cnt = s_eco_cnt+p_eco_cnt;

                try{s_chg_dt = Integer.parseInt( ((String)rs.getString("revisiondate")).replace("-",""));}catch(Exception e){s_chg_dt =0;}
                //Kogger.debug(getClass(), "@@@@@@@@@@@ s_chg_dt : "+s_chg_dt);
                try{p_chg_dt = Integer.parseInt( ((String)ht.get("last_update_date")).replace("-", "") );}catch(Exception e){p_chg_dt =0; }
                //Kogger.debug(getClass(), "@@@@@@@@@@@ p_chg_dt : "+p_chg_dt);

                moldChange.put("changeno", StringUtil.checkNull( String.valueOf(total_eco_cnt) ));
                if(s_chg_dt == p_chg_dt || s_chg_dt > p_chg_dt)
                {
                moldChange.put("changedate", StringUtil.checkNull(rs.getString("changedate")));
                }else{
                    moldChange.put("changedate", StringUtil.checkNull((String)ht.get("last_update_date")));
                }

                moldChangeList.add(moldChange);
            }
        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldChangeList;
    }

    /**
     * 함수명 : ViewMoldChangeListCnt
     * 설명 :
     * @param hash
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 신대범
     * 작성일자 : 2010. 12. 10.
     */
    public int ViewMoldChangeListCnt ( Map _map ) throws Exception{
        KETParamMapUtil map = KETParamMapUtil.getMap(_map);

        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        int listCnt = 0;

        try {

            String parent_item = "";
            String compair = " = ";
            if ((map.getString("partno")).length() > 0){
                parent_item = map.getString("partno");
                if(parent_item.indexOf("*")>-1)
                {
                    parent_item = parent_item.replace("*", "%");
                    compair = " like ";
                }else{
                    compair = " = ";
                }
            }

            sb = new StringBuffer();
            sb.append( "   select count(*)       \n" );
            sb.append( "        from           \n" );
            sb.append( "       (                   \n" );
            sb.append( getQueryStr() );
            sb.append( "       )                  \n" );
            sb.append( "       where 1=1       \n" );
            if ((map.getString("partno")).length() > 0){
                sb.append( " and dieno "+compair+" ?         \n" );
            }
            if ((map.getString("predate")).length() > 0){
                sb.append( " and revisiondate >= ?             \n" );
            }
            if ((map.getString("postdate")).length() > 0){
                sb.append( " and revisiondate <= ?             \n" );
            }
            pstmt = new LoggableStatement( conn, sb.toString() );

            if ((map.getString("partno")).length() > 0){
                pstmt.setString( pstmtCnt++, parent_item );
            }
            if ((map.getString("predate")).length() > 0){
                pstmt.setString( pstmtCnt++, (map.getString("predate")).replace("-", "") );
            }
            if ((map.getString("postdate")).length() > 0){
                pstmt.setString( pstmtCnt++, (map.getString("postdate")).replace("-", "") );
            }

            rs = pstmt.executeQuery();

            while (rs.next()){
                listCnt = rs.getInt(1);
            }

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return listCnt;
    }

    /**
     * 함수명 : ExcelMoldChangeList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 신대범
     * 작성일자 : 2010. 12. 10.
     */
    public ArrayList ExcelMoldChangeList ( Map _map ) throws Exception{
        KETParamMapUtil map = KETParamMapUtil.getMap(_map);

        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldChangeList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldChange = null;

        try {
            String parent_item = "";
            String compair = " = ";
            if ((map.getString("partno")).length() > 0){
                parent_item = map.getString("partno");
                if(parent_item.indexOf("*")>-1)
                {
                    parent_item = parent_item.replace("*", "%");
                    compair = " like ";
                }else{
                    compair = " = ";
                }
            }

            sb = new StringBuffer();
            sb.append( "SELECT * FROM (            \n" );
            sb.append( "SELECT rownum row_id, r.* FROM (            \n" );
            sb.append( "      select \n" );
            sb.append( "       itemcode                 \n" );
            sb.append( "      , itemnm  as itemname                      \n" );
            sb.append( "      , mocnt     as changeno                  \n" );
            sb.append( "      , decode(mocnt,0,'',decode(revisiondate,null,'','','','0','',to_char(to_date(revisiondate,'yyyymmdd'),'yyyy-mm-dd'))) as changedate            \n" );
            sb.append( "      , revisiondate            \n" );
            sb.append( "      , modesc                \n" );
            sb.append( "      , dieno                   \n" );
            sb.append( "      from    \n" );
            sb.append( "      (    select     t.dieno        \n" );
            sb.append( "                , t.itemcode    \n" );
            sb.append( "                , max(w.name) as itemnm        \n" );
            sb.append( "                , sum(t.mocnt) as mocnt        \n" );
            sb.append( "                , max(t.revisiondate) as revisiondate                \n" );
            sb.append( "                , t.modesc        \n" );
            sb.append( "         from   keterpecohistory t, wtpartmaster w                \n" );
            sb.append( "        where  t.itemcode = w.wtpartnumber(+)\n" );
            sb.append( "         group by  t.dieno, t.itemcode, t.modesc\n" );
//            sb.append(getQueryStr());
            sb.append( "      ) t    where 1=1        \n" );

            if ((map.getString("partno")).length() > 0){
                sb.append( " and dieno "+compair+" ?                 \n" );
            }
            if ((map.getString("predate")).length() > 0){
                sb.append( " and revisiondate >= ? \n" );
            }
            if ((map.getString("postdate")).length() > 0){
                sb.append( " and revisiondate <= ? \n" );
            }
            sb.append("ORDER BY "+map.getString("sort")+" ) r )            \n" );
            sb.append( " order by 1 asc                \n" );
            pstmt = new LoggableStatement( conn, sb.toString() );

            if ((map.getString("partno")).length() > 0){
                pstmt.setString( pstmtCnt++, parent_item );
            }
            if ((map.getString("predate")).length() > 0){
                pstmt.setString( pstmtCnt++, (map.getString("predate")).replace("-", "") );
            }
            if ((map.getString("postdate")).length() > 0){
                pstmt.setString( pstmtCnt++, (map.getString("postdate")).replace("-", "") );
            }
            rs = pstmt.executeQuery();

            String itemCd = "";

            Hashtable ht = null;
            while (rs.next()){
                int s_eco_cnt = 0;
                int p_eco_cnt = 0;
                int total_eco_cnt = 0;
                int s_chg_dt = 0;
                int p_chg_dt = 0;

                itemCd = StringUtil.checkNull(rs.getString("itemcode"));
                moldChange = new Hashtable<String, String>();
                moldChange.put("row_id", StringUtil.checkNull(rs.getString("row_id")));
                moldChange.put("itemcode", itemCd);
                moldChange.put("itemname", StringUtil.checkNull(rs.getString("itemname")));
                moldChange.put("modesc", StringUtil.checkNull(rs.getString("modesc")));
                moldChange.put("dieno", StringUtil.checkNull(rs.getString("dieno")));

                if ((map.getString("predate")).length() == 0 && (map.getString("postdate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, "", "");
                }
                else if ((map.getString("predate")).length() > 0 && (map.getString("postdate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, map.getString("predate") , "");
                }
                else if ((map.getString("postdate")).length() > 0 && (map.getString("predate")).length() == 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, "", map.getString("postdate"));
                }
                else if ((map.getString("postdate")).length() > 0 && (map.getString("predate")).length() > 0 ){
                    ht = EcmSearchHelper.manager.getPartChangeInfo(itemCd, map.getString("predate"), map.getString("postdate"));
                }

                try{s_eco_cnt = Integer.parseInt((String)rs.getString("changeno"));}catch(Exception e){s_eco_cnt =0; }
                try{p_eco_cnt = Integer.parseInt( (String)ht.get("eco_count") );}catch(Exception e){p_eco_cnt =0; }
                total_eco_cnt = s_eco_cnt+p_eco_cnt;

                try{s_chg_dt = Integer.parseInt( ((String)rs.getString("revisiondate")).replace("-",""));}catch(Exception e){s_chg_dt =0;}
                try{p_chg_dt = Integer.parseInt( ((String)ht.get("last_update_date")).replace("-", "") );}catch(Exception e){p_chg_dt =0; }

                moldChange.put("changeno", StringUtil.checkNull( String.valueOf(total_eco_cnt) ));
                if(s_chg_dt == p_chg_dt || s_chg_dt > p_chg_dt)
                {
                moldChange.put("changedate", StringUtil.checkNull(rs.getString("changedate")));
                }else{
                    moldChange.put("changedate", StringUtil.checkNull((String)ht.get("last_update_date")));
                }

                moldChangeList.add(moldChange);
            }

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldChangeList;
    }

    /**
     * 함수명 : ExcelMoldChangeList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 신대범
     * 작성일자 : 2010. 12. 10.
     */
    public ArrayList PopupMoldChangeList ( Map _map ) throws Exception{
        KETParamMapUtil map = KETParamMapUtil.getMap(_map);

        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldChangeList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldChange = null;

        try {
            sb = new StringBuffer();
            sb.append( " select rownum row_id, t.*                          \n" );
            sb.append( " from          \n" );
            sb.append( " ( select  itemcode, mocnt         \n" );
            sb.append( "           , to_char(to_date(revisiondate,'yyyymmdd'),'yyyy-mm-dd') revisiondate    \n" );
            sb.append( "           , empno, mouser, modesc                 \n" );
            sb.append( " from keterpecohistory            \n" );
            sb.append( " where itemcode = ?            \n" );
            sb.append( " order by 3 asc) t                \n" );
            pstmt = new LoggableStatement( conn, sb.toString() );

            if ((map.getString("itemcode")).length() > 0){
                pstmt.setString( pstmtCnt++, map.getString("itemcode") );
            }
            rs = pstmt.executeQuery();

            while (rs.next()){

                moldChange = new Hashtable<String, String>();
                moldChange.put("row_id", StringUtil.checkNull(rs.getString("row_id")));
                moldChange.put("itemcode", StringUtil.checkNull(rs.getString("itemcode")));
                moldChange.put("mocnt", StringUtil.checkNull(rs.getString("mocnt")));
                moldChange.put("revisiondate", StringUtil.checkNull(rs.getString("revisiondate")));
                moldChange.put("empno", StringUtil.checkNull(rs.getString("empno")));
                moldChange.put("mouser", StringUtil.checkNull(rs.getString("mouser")));
                moldChange.put("modesc", StringUtil.checkNull(rs.getString("modesc")));
                moldChangeList.add(moldChange);
            }

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }
        return moldChangeList;
    }

    /**
     * 함수명 : statementRsClose
     * 설명 :
     * @throws Exception
     * 작성자 : 신대범
     * 작성일자 : 2010. 10. 18.
     */
    public void statementRsClose(ResultSet rs, LoggableStatement pstmt) throws Exception
    {
        if( rs != null )
            rs.close();

        if( pstmt != null )
            pstmt.close();
    }

    /**
     * 함수명 : getQueryStr
     * 설명 :
     * @param
     * @return String
     * @throws
     * @throws
     * 작성자 : 신대범
     * 작성일자 : 2010. 12. 10.
     */
    public String getQueryStr()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "        select             \n" );
        sb.append( "          dieno           \n" );
        sb.append( "        , itemcode     \n" );
        sb.append( "        , max(itemnm) as itemnm        \n" );
        sb.append( "        , sum(mocnt) as mocnt           \n" );
        sb.append( "        , max(revisiondate) as revisiondate                \n" );
        sb.append( "        , modesc        \n" );
        sb.append( "        from             \n" );
        sb.append( "        (                \n" );
        sb.append( "            select         \n" );
        sb.append( "                 t.dieno, t.itemcode, w.name as itemnm, t.mocnt, t.revisiondate, t.modesc    \n" );
        sb.append( "             from         \n" );
        sb.append( "            (            \n" );
        sb.append( "              select  dieno, itemcode    \n" );
        sb.append( "                      , sum(to_number(mocnt)) as mocnt \n" );
        sb.append( "                      , max(to_number(revisiondate)) as revisiondate         \n" );
        sb.append( "                      , modesc             \n" );
        sb.append( "               from  keterpecohistory    \n" );
        sb.append( "              group by dieno ,itemcode, modesc                               \n" );
        sb.append( "            ) t, wtpartmaster w            \n" );
        sb.append( "            where t.itemcode = w.wtpartnumber(+)        \n" );
        sb.append( "        ) group by  dieno, itemcode, modesc            \n" );

        return sb.toString();
    }
}
