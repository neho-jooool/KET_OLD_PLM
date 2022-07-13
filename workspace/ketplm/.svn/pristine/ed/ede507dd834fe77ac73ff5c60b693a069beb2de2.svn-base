/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EarlyWarningDao.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 10. 18.
 */
package e3ps.ews.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EarlyWarningDao
 * 설명 : 초기유동관리 조회
 * 작성자 : 김경희
 * 작성일자 : 2010. 10. 13.
 */
public class EarlyWarningDao {

    private Connection conn;

    public EarlyWarningDao(Connection conn){
        this.conn = conn;
    }

    public EarlyWarningDao(){
        super();
    }

    /**
     * 함수명 : ViewEarlyWarningList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2010. 10. 18.
     */
    public List<Map<String, Object>> ViewEarlyWarningList ( KETParamMapUtil hash, List<Map<String, Object>> conditionList ) throws Exception{
        LoggableStatement pstmt = null;

        StringBuffer sb = null;
        ResultSet rs = null;
        List<Map<String, Object>> earlyWarningList =  new ArrayList<Map<String, Object>>();
        Map<String, Object> earlyWarning = null;

        try {

            sb = new StringBuffer();
            sb.append(" SELECT * FROM (                                                                         \n" );
            sb.append(" SELECT EW.CLASSNAMEA2A2 || ':' || EW.IDA2A2                          AS OID             \n" );
            sb.append("       ,EW.IDA2A2                                                     AS IDA2A2          \n" );
            sb.append("       ,WTDM.WTDOCUMENTNUMBER                                         AS EWSNO           \n" );
            sb.append("       ,EW.PJTNO                                                      AS PJTNO           \n" );
            sb.append("       ,PJTMST.PJTNAME                                                AS PJTNAME         \n" );
            sb.append("       ,(SELECT WM_CONCAT(PM.PNO)                                                        \n" );
            sb.append("           FROM EARLYWARNINGTARGETLINK                                                   \n" );
            sb.append("               ,KETEARLYWARNINGTARGET                                                    \n" );
            sb.append("               ,( SELECT CLASSNAMEA2A2 || ':' || IDA2A2      POID                        \n" );
            sb.append("                       , PARTNO                              PNO                         \n" );
            sb.append("                    FROM PRODUCTPROJECT                                                  \n" );
            sb.append("                   UNION                                                                 \n" );
            sb.append("                  SELECT CLASSNAMEA2A2 || ':' || IDA2A2      POID                        \n" );
            sb.append("                       , PARTNO                              PNO                         \n" );
            sb.append("                    FROM MOLDITEMINFO )   PM                                             \n" );
            sb.append("                   WHERE EARLYWARNINGTARGETLINK.IDA3B5   = KETEARLYWARNINGTARGET.IDA2A2  \n" );
            sb.append("                     AND KETEARLYWARNINGTARGET.PRODUCTNO = PM.POID                       \n" );
            sb.append("                     AND IDA3A5 = EW.IDA2A2)                          AS PARTNO          \n" );
            sb.append("       ,EW.INOUT                                                      AS INOUT           \n" );
            sb.append("       ,CASE WHEN EW.INOUT = 'i' THEN                                                    \n" );
            sb.append("                FN_GET_NUMBERCODEVALUE_OID('PRODUCTIONDEPT', ltrim(EW.PROTEAMNO, 'e3ps.common.code.NumberCode:'), 'ko')           \n" );
            sb.append("            WHEN EW.INOUT = 'o' THEN                                                     \n" );
            sb.append("                KP.PARTNERNAME                                                           \n" );
            sb.append("            ELSE                                                                         \n" );
            sb.append("                ''                                                                       \n" );
            sb.append("        END                                                           AS PRODUCTION      \n" );
            sb.append("       ,FSTCHARGE.IDA3B4                                              AS FSTCHARGE       \n" );
            sb.append("       ,FSTCHARGE.NAME                                                AS FSTCHARGENAME   \n" );
            sb.append("       ,CREATOR.IDA3B4                                                AS CREATOR         \n" );
            sb.append("       ,CREATOR.NAME                                                  AS CREATORNAME     \n" );
            sb.append("       ,WTDM.CREATESTAMPA2                                            AS CREATESTAMPA2   \n" );
            sb.append("       ,TO_CHAR(WTDM.CREATESTAMPA2, 'YYYY-MM-DD')                     AS CREATEDATE      \n" );
            sb.append("       ,ES.STATESTATE                                                 AS STATESTATE      \n" );
            sb.append("       ,FN_GET_NUMBERCODEVALUE('EWSSTEP', ES.STATESTATE, 'ko')        AS STEP            \n" );
            sb.append("   FROM KETEARLYWARNING            EW                                                    \n" );
            sb.append("       ,WTDOCUMENTMASTER           WTDM                                                  \n" );
            sb.append("       ,PEOPLE                     FSTCHARGE                                             \n" );
            sb.append("       ,PEOPLE                     CREATOR                                               \n" );
            sb.append("       ,E3PSPROJECTMASTER          PJTMST                                                \n" );
            sb.append("       ,KETPARTNER                 KP                                                    \n" );
            sb.append("       ,KETEARLYWARNINGSTEPLINK    ESL                                                   \n" );
            sb.append("       ,(SELECT STATESTATE, IDA2A2, IDA3MASTERREFERENCE                                  \n" );
            sb.append("           FROM KETEARLYWARNINGSTEP                                                      \n" );
            sb.append("          WHERE IDA2A2 IN ( SELECT MAX(KEWS.IDA2A2)                                      \n" );
            sb.append("                              FROM KETEARLYWARNINGSTEP          KEWS                     \n" );
            sb.append("                          GROUP BY KEWS.IDA3MASTERREFERENCE ))  ES                       \n" );
            sb.append("       ,(SELECT CFTMEMBER  member, IDA2A2, IDA3MASTERREFERENCE                           \n" );
            sb.append("           FROM KETEARLYWARNINGPLAN                                                      \n" );
            sb.append("          WHERE IDA2A2 IN ( SELECT MAX(KEWP.IDA2A2)                                      \n" );
            sb.append("                              FROM KETEARLYWARNINGPLAN          KEWP                     \n" );
            sb.append("                          GROUP BY KEWP.IDA3MASTERREFERENCE ))  EP                       \n" );
            sb.append("       ,(SELECT IDA3A5, IDA3B5                                                           \n" );
            sb.append("           FROM KETEARLYWARNINGPLANLINK                                                  \n" );
            sb.append("          WHERE IDA3B5 IN ( SELECT MAX(IDA3B5)                                           \n" );
            sb.append("                              FROM KETEARLYWARNINGPLANLINK                               \n" );
            sb.append("                          GROUP BY IDA3A5 ))                    EPL                      \n" );
            sb.append("  WHERE 1=1                                                                              \n" );
            sb.append("    AND EW.IDA3MASTERREFERENCE = WTDM.IDA2A2                                             \n" );
            sb.append("    AND EW.PJTNO  = PJTMST.PJTNO(+)                                                      \n" );
            sb.append("    AND EW.IDA2A2 = (SELECT MAX(KEW.IDA2A2)                                              \n" );
            sb.append("                       FROM KETEARLYWARNING KEW                                          \n" );
            sb.append("                       WHERE 1=1                                                         \n" );
            sb.append("                         AND KEW.IDA3MASTERREFERENCE = EW.IDA3MASTERREFERENCE            \n" );
            sb.append("                    GROUP BY KEW.IDA3MASTERREFERENCE )                                   \n" );
            sb.append("    AND ltrim(EW.FSTCHARGE, 'wt.org.WTUser:') = FSTCHARGE.IDA3B4                         \n" );
            sb.append("    AND EW.IDA3D2ITERATIONINFO                = CREATOR.IDA3B4                           \n" );
            sb.append("    AND EW.PARTNERNO                          = KP.PARTNERNO(+)                          \n" );
            sb.append("    AND EW.IDA3MASTERREFERENCE    = EPL.IDA3A5(+)                                        \n" );
            sb.append("    AND EP.IDA3MASTERREFERENCE(+) = EPL.IDA3B5                                           \n" );
            sb.append("    AND ES.IDA3MASTERREFERENCE    = ESL.IDA3A5                                           \n" );
            sb.append("    AND EW.IDA3MASTERREFERENCE(+) = ESL.IDA3B5                                           \n" );
            sb.append("  ) EWS                                                                                  \n" );
            sb.append("  WHERE 1=1                                                                              \n" );

            for ( Map<String, Object> condistion : conditionList ) {
                KETParamMapUtil map = KETParamMapUtil.getMap(condistion);
                // 관리번호
                if ( !map.getString("ewsno").equals("") ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("EWSNO", map.getString("ewsno"), true)).append("        \n");
                }
                // 프로젝트 번호
                if ( !map.getString("pjtno").equals("") ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJTNO", map.getString("pjtno"), false)).append("        \n");
                }
                // 부품번호
                if ( !map.getString("partno").equals("") ) {
                    sb.append("   AND EWS.IDA2A2 IN (SELECT IDA3A5                                                                     \n");
                    sb.append("                        FROM EARLYWARNINGTARGETLINK                                                     \n");
                    sb.append("                            ,KETEARLYWARNINGTARGET                                                      \n");
                    sb.append("                            ,( SELECT CLASSNAMEA2A2 || ':' || IDA2A2      POID                          \n");
                    sb.append("                                    , PARTNO                              PNO                           \n");
                    sb.append("                                 FROM PRODUCTPROJECT                                                    \n");
                    sb.append("                                UNION                                                                   \n");
                    sb.append("                               SELECT CLASSNAMEA2A2 || ':' || IDA2A2      POID                          \n");
                    sb.append("                                    , PARTNO                              PNO                           \n");
                    sb.append("                                 FROM MOLDITEMINFO )   PM                                               \n");
                    sb.append("                                WHERE EARLYWARNINGTARGETLINK.IDA3B5   = KETEARLYWARNINGTARGET.IDA2A2    \n");
                    sb.append("                                  AND KETEARLYWARNINGTARGET.PRODUCTNO = PM.POID                         \n");
                    sb.append("                                  AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PM.PNO", map.getString("partno"), false)).append(" )       \n");
                }
                // 부품명
                if ( !map.getString("partname").equals("") ) {
                    sb.append("   AND EWS.IDA2A2 IN (SELECT IDA3A5                                                                      \n");
                    sb.append("                        FROM EARLYWARNINGTARGETLINK                                                      \n");
                    sb.append("                            ,KETEARLYWARNINGTARGET                                                       \n");
                    sb.append("                            ,( SELECT PRODUCTPROJECT.CLASSNAMEA2A2 || ':' || PRODUCTPROJECT.IDA2A2  POID \n");
                    sb.append("                                    , E3PSPROJECTMASTER.PJTNAME              PNAME                       \n");
                    sb.append("                                 FROM PRODUCTPROJECT                                                     \n");
                    sb.append("                                    , E3PSPROJECTMASTER                                                  \n");
                    sb.append("                                WHERE PRODUCTPROJECT.IDA3B8 = E3PSPROJECTMASTER.ida2a2                   \n");
                    sb.append("                                UNION                                                                    \n");
                    sb.append("                               SELECT CLASSNAMEA2A2 || ':' || IDA2A2         POID                        \n");
                    sb.append("                                    , PARTNAME                               PNAME                       \n");
                    sb.append("                                 FROM MOLDITEMINFO )   PM                                                \n");
                    sb.append("                                WHERE EARLYWARNINGTARGETLINK.IDA3B5   = KETEARLYWARNINGTARGET.IDA2A2     \n");
                    sb.append("                                  AND KETEARLYWARNINGTARGET.PRODUCTNO = PM.poid                          \n");
                    sb.append("                                  AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PM.PNAME", map.getString("partname"), true)).append(" )       \n");
                }
                // 생산처 구분
                if ( map.getStringArray("inout").length > 0 ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("INOUT", map.getStringArray("inout"), false)).append("        \n");
                }
                // 생산처
                if ( !map.getString("production").equals("") ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PRODUCTION", map.getString("production"), true)).append("        \n");
                }
                // 진행단계
                if ( map.getStringArray("step").length > 0 ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("STATESTATE", map.getString("step"), false)).append("        \n");
                }
                // 수행담당자
                if ( !map.getString("fstcharge").equals("") ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("FSTCHARGE", KETParamMapUtil.OidToString(map.getString("fstcharge")), false)).append("        \n");
                }
                // 작성자
                if ( !map.getString("creator").equals("") ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("CREATOR", KETParamMapUtil.OidToString(map.getString("creator")), false)).append("        \n");
                }
                // 작성일
                if ( map.getString("createdate").length() > 0 ) {
                    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("CREATEDATE", map.getString("createdate"), false)).append("        \n");
                }
            }

            Kogger.debug(getClass(),  "###############  테이블 정보  \n"+ sb.toString() );

            pstmt = new LoggableStatement( conn, sb.toString() );
            rs = pstmt.executeQuery();
            while (rs.next()){
                earlyWarning = new HashMap<String, Object>();
                earlyWarning.put("oid",        StringUtil.checkNull(rs.getString("OID"))); // 초기유동관리 oid
                earlyWarning.put("ewsno",      StringUtil.checkNull(rs.getString("EWSNO"))); // 초기유동관리 번호
                earlyWarning.put("pjtno",      StringUtil.checkNull(rs.getString("PJTNO"))); // 프로젝트 번호
                earlyWarning.put("pjtname",    StringUtil.checkNull(rs.getString("PJTNAME"))); // 프로젝트명
                earlyWarning.put("partno",     StringUtil.checkNull(rs.getString("PARTNO"))); // 부품 번호
                earlyWarning.put("inout",      StringUtil.checkNull(rs.getString("PRODUCTION"))); // 생산처
                earlyWarning.put("fstcharge",  StringUtil.checkNull(rs.getString("FSTCHARGENAME"))); // 수행담당자
                earlyWarning.put("creator",    StringUtil.checkNull(rs.getString("CREATORNAME")));  // 작성자
                earlyWarning.put("createdate", StringUtil.checkNull(rs.getString("CREATEDATE"))); // 작성일자
                earlyWarning.put("step",       StringUtil.checkNull(rs.getString("STEP"))); // 진행단계

                earlyWarningList.add(earlyWarning);
            }
        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }
        return earlyWarningList;
    }


    public ArrayList ViewEarlyWarningList ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> earlyWarningList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> earlyWarning = null;

        try {

            sb = new StringBuffer();
            sb.append( "SELECT ewsno, pjtno, partno, pjtname, inout, fstcharge, sndcharge,member,  creator, createdate, step, oid, partname, row_id                       \n" );
            sb.append( "  FROM (                                                                                                                                     \n" );
            sb.append( "SELECT ewsno, pjtno, partno, pjtname, inout, fstcharge, sndcharge, member, creator, createdate, step, oid, partname, ROWNUM row_id    \n" );
            sb.append( "  FROM (                                                                                                                                     \n" );
            sb.append(" SELECT WTDM.WTDOCUMENTNUMBER                                                     ewsno                          \n"); // 珥덇린�좊룞愿�━ NO
            sb.append("           , EW.PJTNO                                                                                    pjtno                           \n");// �꾨줈�앺듃 踰덊샇
            sb.append("           , PJ.PJTNAME                                                                                  pjtname                      \n");// �꾨줈�앺듃紐�
            sb.append("           , ET.PRODUCTNO                                                                            partno                         \n"); // 遺�뭹
            sb.append("           , decode(EW.INOUT , 'i' , NC.NAME , 'o' , KP.PARTNERNAME, ' ' )      inout                            \n"); // �앹궛泥�
            sb.append("           , PP2.NAME                                                                                    fstcharge                      \n"); // �섑뻾�대떦����
            sb.append("           , PP3.NAME                                                                                    sndcharge                     \n"); // �섑뻾�대떦��遺�
            sb.append("           , decode(instr(EP.member, '/', 1,1),0,PP4.NAME,null,PP4.NAME,PP4.NAME||'...')    member       \n"); // �쒕룞硫ㅻ쾭
            sb.append("           , PP.NAME                                                                                      creator                         \n"); // �묒꽦��
            sb.append("           , TO_CHAR(WTDM.CREATESTAMPA2, 'YYYY-MM-DD')                      createdate                    \n"); // �묒꽦�쇱옄
            sb.append("           , NC2.NAME                                                                                   step                             \n"); // 吏꾪뻾�④퀎
            sb.append("           , EW.CLASSNAMEA2A2 || ':' || EW.IDA2A2                                      oid                               \n"); // oid
            sb.append("           , ET2.PRODUCTNAME                                                                      partname                     \n"); // 遺�뭹紐�
            sb.append("   FROM KETEARLYWARNING            EW                                                                                         \n"); //珥덇린�좊룞愿�━ �뚯씠釉�
            sb.append("           , WTDOCUMENTMASTER        WTDM                                                                                    \n"); // WTDocument  留덉뒪���뚯씠釉�
            sb.append("           , PEOPLE                                PP                                                                                          \n"); // �ъ슜���뚯씠釉�
            sb.append("           , PEOPLE                                PP2                                                                                        \n"); // �ъ슜���뚯씠釉�2)
            sb.append("           , PEOPLE                                PP3                                                                                        \n"); // �ъ슜���뚯씠釉�3)
            sb.append("           , PEOPLE                                PP4                                                                                        \n"); // �ъ슜���뚯씠釉�4)
            sb.append("           , NUMBERCODE                    NC                                                                                         \n"); // 肄붾뱶 �뚯씠釉�
            sb.append("           , (SELECT CODE, NAME                                                                                                     \n"); // 肄붾뱶 �뚯씠釉�2)
            sb.append("                 FROM NUMBERCODE                                                                                                     \n");
            sb.append("               WHERE CODETYPE = 'EWSSTEP')     NC2                                                                         \n");
            sb.append("           , KETPARTNER                        KP                                                                                         \n"); // �묐젰���뚯씠釉�
            sb.append("           , E3PSPROJECTMASTER        PJ                                                                                         \n"); // �꾨줈�앺듃 �뚯씠釉�
            sb.append("           ,(SELECT IDA3A5 tewsno                                                                                                  \n"); //遺�뭹 紐⑺몴 �뚯씠釉�
            sb.append("                        , ltrim(sys_connect_by_path(PRODUCTNO,','),',') PRODUCTNO                             \n");
            sb.append("                FROM (SELECT IDA3A5                                                                                                \n");
            sb.append("                                     , PM.pno     PRODUCTNO                                                                         \n");
            sb.append("                                     , row_number() over  (partition by IDA3A5 order by PM.pno) rn               \n");
            sb.append("                                     , count(*) over (partition by IDA3A5) cnt                                                 \n");
            sb.append("                             FROM EARLYWARNINGTARGETLINK                                                                  \n");
            sb.append("                                     , KETEARLYWARNINGTARGET                                                                  \n");
            sb.append("                                     , ( SELECT CLASSNAMEA2A2 || ':' || IDA2A2      poid                               \n");
            sb.append("                                                    , PARTNO                                            pno                               \n");
            sb.append("                                            FROM PRODUCTPROJECT                                                                 \n");
            sb.append("                                           UNION                                                                                              \n");
            sb.append("                                          SELECT CLASSNAMEA2A2 || ':' || IDA2A2      poid                               \n");
            sb.append("                                                    , PARTNO                                            pno                                \n");
            sb.append("                                            FROM MOLDITEMINFO )   PM                                                            \n");
            sb.append("                           WHERE EARLYWARNINGTARGETLINK.IDA3B5 = KETEARLYWARNINGTARGET.IDA2A2      \n");
            sb.append("                                AND KETEARLYWARNINGTARGET.PRODUCTNO = PM.poid)                            \n");
            sb.append("              WHERE level = cnt                                                                                                         \n");
            sb.append("           start with rn = 1                                                                                                               \n");
            sb.append(" connect by prior IDA3A5 = IDA3A5 and prior rn=rn-1) ET                                                             \n");
            sb.append("           ,(SELECT IDA3A5 tewsno                                                                                                  \n"); //遺�뭹 紐⑺몴 �뚯씠釉�2)
            sb.append("                        , ltrim(sys_connect_by_path(PRODUCTNAME,','),',') PRODUCTNAME                     \n");
            sb.append("                FROM (SELECT IDA3A5                                                                                                \n");
            sb.append("                                     , PM.pname     PRODUCTNAME                                                                \n");
            sb.append("                                     , row_number() over  (partition by IDA3A5 order by PM.pname) rn          \n");
            sb.append("                                     , count(*) over (partition by IDA3A5) cnt                                                 \n");
            sb.append("                             FROM EARLYWARNINGTARGETLINK                                                                  \n");
            sb.append("                                     , KETEARLYWARNINGTARGET                                                                  \n");
            sb.append("                                     , ( SELECT PRODUCTPROJECT.CLASSNAMEA2A2 || ':' || PRODUCTPROJECT.IDA2A2    poid     \n");
            sb.append("                                                    , E3PSPROJECTMASTER.PJTNAME        pname                           \n");
            sb.append("                                            FROM PRODUCTPROJECT                                                                 \n");
            sb.append("                                                    , E3PSPROJECTMASTER                                                            \n");
            sb.append("                                          WHERE PRODUCTPROJECT.IDA3B8 = E3PSPROJECTMASTER.ida2a2     \n");
            sb.append("                                           UNION                                                                                              \n");
            sb.append("                                          SELECT CLASSNAMEA2A2 || ':' || IDA2A2      poid                               \n");
            sb.append("                                                    , PARTNAME                                         pname                          \n");
            sb.append("                                            FROM MOLDITEMINFO )   PM                                                            \n");
            sb.append("                           WHERE EARLYWARNINGTARGETLINK.IDA3B5 = KETEARLYWARNINGTARGET.IDA2A2      \n");
            sb.append("                                AND KETEARLYWARNINGTARGET.PRODUCTNO = PM.poid)                            \n");
            sb.append("              WHERE level = cnt                                                                                                         \n");
            sb.append("           start with rn = 1                                                                                                               \n");
            sb.append(" connect by prior IDA3A5 = IDA3A5 and prior rn=rn-1) ET2                                                           \n");
            sb.append("           ,(SELECT STATESTATE, IDA2A2, IDA3MASTERREFERENCE                                                   \n"); // 珥덇린�좊룞愿�━ �④퀎 �뚯씠釉�
            sb.append("                FROM KETEARLYWARNINGSTEP                                                                                    \n");
            sb.append("              WHERE IDA2A2 in ( SELECT max(KEWS.IDA2A2)                                                             \n");
            sb.append("                                              FROM KETEARLYWARNINGSTEP               KEWS                              \n");
            sb.append("                                        GROUP BY KEWS.IDA3MASTERREFERENCE ))        ES                            \n");
            sb.append("           , KETEARLYWARNINGSTEPLINK    ESL                                                                                 \n"); // 珥덇린�좊룞愿�━ �④퀎留곹겕 �뚯씠釉�
            sb.append("           ,(SELECT CFTMEMBER  member, IDA2A2, IDA3MASTERREFERENCE                                    \n"); //怨꾪쉷���뚯씠釉�
            sb.append("                FROM KETEARLYWARNINGPLAN                                                                                    \n");
            sb.append("              WHERE IDA2A2 in ( SELECT max(KEWP.IDA2A2)                                                             \n");
            sb.append("                                              FROM KETEARLYWARNINGPLAN               KEWP                              \n");
            sb.append("                                        GROUP BY KEWP.IDA3MASTERREFERENCE ))        EP                             \n");
            sb.append("           ,(SELECT IDA3A5, IDA3B5                                                                                                 \n"); //怨꾪쉷��留곹겕 �뚯씠釉�
            sb.append("                FROM KETEARLYWARNINGPLANLINK                                                                             \n");
            sb.append("              WHERE IDA3B5 in ( SELECT max(IDA3B5)                                                                      \n");
            sb.append("                                              FROM KETEARLYWARNINGPLANLINK                                               \n");
            sb.append("                                        GROUP BY IDA3A5 ))        EPL                                                              \n");
            sb.append(" WHERE EW.IDA3MASTERREFERENCE = WTDM.IDA2A2                                                                 \n");
            sb.append("     AND EW.IDA3D2ITERATIONINFO = PP.IDA3B4                                                                             \n");
            sb.append("     AND ltrim(EW.FSTCHARGE, 'wt.org.WTUser:') = PP2.IDA3B4                                                    \n");
            sb.append("     AND ltrim(EW.SNDCHARGE, 'wt.org.WTUser:') = PP3.IDA3B4(+)                                              \n");
            sb.append("     AND ltrim(decode(instr(EP.member, '/', 1,1),0,EP.member,substr(EP.member,1,instr(EP.member, '/', 1,1)-1)),'wt.org.WTUser:') = PP4.IDA3B4(+)       \n");
            sb.append("     AND ltrim(EW.PROTEAMNO, 'e3ps.common.code.NumberCode:') = NC.IDA2A2(+)                   \n");
            sb.append("     AND ES.STATESTATE = NC2.CODE(+)                                                                                     \n");
            sb.append("     AND EW.PARTNERNO = KP.PARTNERNO(+)                                                                              \n");
            sb.append("     AND EW.IDA2A2 in (      SELECT max(KEW.IDA2A2)                                                                 \n");
            sb.append("                                           FROM KETEARLYWARNING               KEW                                           \n");
            sb.append("                                    GROUP BY KEW.IDA3MASTERREFERENCE )                                                \n");
            sb.append("     AND EW.IDA2A2 = ET.tewsno                                                                                                  \n");
            sb.append("     AND EW.IDA2A2 = ET2.tewsno                                                                                                  \n");
            sb.append("     AND EW.PJTNO = PJ.PJTNO(+)                                                                                                  \n");
            sb.append("     AND EW.IDA3MASTERREFERENCE = EPL.IDA3A5(+)                                                                  \n");
            sb.append("     AND EP.IDA3MASTERREFERENCE(+) = EPL.IDA3B5                                                                      \n");
            sb.append("     AND ES.IDA3MASTERREFERENCE = ESL.IDA3A5                                                                       \n");
            sb.append("     AND EW.IDA3MASTERREFERENCE(+) = ESL.IDA3B5                                                                  \n");

            if (((String)hash.get("ewsno")).length() > 0){
                sb.append("     AND upper(WTDM.WTDOCUMENTNUMBER)  like   upper('" +  StringUtil.getLikeQueryString((String) hash.get("ewsno")) + "')           \n"); // 珥덇린�좊룞愿�━ 踰덊샇 寃�깋議곌굔

            }

            String strPjtno = (String)hash.get("pjtno");
            if ( strPjtno != null && strPjtno.trim().length() > 0 && !strPjtno.equalsIgnoreCase("null") ) {

                ArrayList<String> List = KETStringUtil.getToken(strPjtno, ",");
                if ( List.size() == 1 ) {
                    sb.append(" AND upper(EW.PJTNO)  like '" + List.get(0) + "'    \n");
                } else {
                    sb.append(" AND upper(EW.PJTNO) IN    upper((" + KETStringUtil.getMultiSearchCondition(List) + "))     \n");
                }
            }


            String strPartno = (String)hash.get("partno");
            if ( strPartno != null && strPartno.trim().length() > 0 && !strPartno.equalsIgnoreCase("null") ) {

                ArrayList<String> List = KETStringUtil.getToken(strPartno, ",");           // 遺�뭹踰덊샇 �ㅼ쨷寃�깋
                if ( List.size() == 1 ) {
                    sb.append(" AND upper(ET.PRODUCTNO)  like '" + List.get(0) + "'    \n");
                } else {
                    sb.append(" AND upper(ET.PRODUCTNO)  IN   upper((" + KETStringUtil.getMultiSearchCondition(List) + "))     \n");
                }
            }


            if (((String)hash.get("pjtname")).length() > 0){
                sb.append("     AND upper(PJ.PJTNAME)  like     upper('" +  StringUtil.getLikeQueryString((String) hash.get("pjtname")) + "')           \n");  // �꾨줈�앺듃紐�寃�깋議곌굔
            }

            if (((String)hash.get("partname")).length() > 0){
                sb.append("     AND upper(ET2.PRODUCTNAME)  like    upper('" +  StringUtil.getLikeQueryString((String) hash.get("partname")) + "')           \n");// 遺�뭹紐�寃�깋議곌굔
            }


            String lcStateInout = (String)hash.get("HdinOut");
            if ( lcStateInout != null && lcStateInout.trim().length() > 0 && !lcStateInout.equalsIgnoreCase("null") ) {

                ArrayList<String> stateList = KETStringUtil.getToken(lcStateInout, ",");           // �앹궛泥�援щ텇 寃�깋議곌굔
                if ( stateList.size() == 1 ) {
                    sb.append(" AND EW.INOUT = '" + stateList.get(0) + "'                             \n");
                } else {
                    sb.append(" AND EW.INOUT IN (" + KETStringUtil.getMultiSearchCondition(stateList) + ")                             \n");
                }
            }

            String lcStateStep = (String)hash.get("Hdstep");
            if ( lcStateStep != null && lcStateStep.trim().length() > 0 && !lcStateStep.equalsIgnoreCase("null") ) {

                ArrayList<String> stateList = KETStringUtil.getToken(lcStateStep, ",");           // 吏꾪뻾�④퀎 寃�깋議곌굔
                if ( stateList.size() == 1 ) {
                    sb.append(" AND NC2.CODE = '" + stateList.get(0) + "'                             \n");
                } else {
                    sb.append(" AND NC2.CODE  IN (" + KETStringUtil.getMultiSearchCondition(stateList) + ")                             \n");
                }
            }
            // End code


            if (((String)hash.get("production")).length() > 0){
                sb.append("     AND upper(decode(EW.INOUT , 'i' , NC.NAME , 'o' , KP.PARTNERNAME, ' ' ) ) like    upper('" +  StringUtil.getLikeQueryString((String) hash.get("production")) + "')           \n");// �앹궛泥�寃�깋議곌굔
            }
            if (((String)hash.get("fstcharge")).length() > 0){
                sb.append("     AND EW.FSTCHARGE  =    '" +  StringUtil.getLikeQueryString((String) hash.get("fstcharge")) + "'   \n"); // �섑뻾�대떦��寃�깋議곌굔
            }
            if (((String)hash.get("sndcharge")).length() > 0){
                sb.append("     AND EW.SNDCHARGE  =   '" +  StringUtil.getLikeQueryString((String) hash.get("sndcharge")) + "'  \n"); // �섑뻾�대떦��遺� 寃�깋議곌굔
            }
            if (((String)hash.get("member")).length() > 0){
                sb.append("     AND EP.member  like   '" + StringUtil.getLikeQueryString((String) hash.get("member"))  + "'  \n"); // �쒕룞硫ㅻ쾭 寃�깋議곌굔
            }


            if (((String)hash.get("creator")).length() > 0){
                sb.append("     AND EW.IDA3D2ITERATIONINFO  = ltrim(  '" + StringUtil.getLikeQueryString((String) hash.get("creator")) + "' , 'wt.org.WTUser:') \n"); // �묒꽦��寃�깋議곌굔
            }
            if (((String)hash.get("createdate")).length() > 0){
                sb.append("     AND TO_CHAR(WTDM.CREATESTAMPA2, 'YYYY-MM-DD')  =   '" + StringUtil.getLikeQueryString((String) hash.get("createdate")) + "'  \n"); // �묒꽦�쇱옄 寃�깋議곌굔
            }
            if (((String)hash.get("useroid")).length() > 0){
                sb.append("     AND (EW.FSTCHARGE  =  '" + hash.get("fstcharge") + "'  \n");
                sb.append("              OR EW.SNDCHARGE  =  '" + hash.get("sndcharge") + "' \n");
                sb.append("              OR EW.IDA3D2ITERATIONINFO  = ltrim( '" + hash.get("creator") + "' , 'wt.org.WTUser:')  \n");
                sb.append("              OR EP.member  like  '" + hash.get("member") + "' )   \n");
            }

            sb.append("   )  ");
            sb.append("   )  ");


            pstmt = new LoggableStatement( conn, sb.toString() );

            rs = pstmt.executeQuery();

            Kogger.debug(getClass(),  "###############  rs ===  "+ rs.toString() );
            while (rs.next()){
                earlyWarning = new Hashtable<String, String>();
                earlyWarning.put("ewsno", StringUtil.checkNull(rs.getString("ewsno"))); // 珥덇린�좊룞愿�━ 踰덊샇
                earlyWarning.put("pjtno", StringUtil.checkNull(rs.getString("pjtno"))); // �꾨줈�앺듃 踰덊샇
                earlyWarning.put("pjtname", StringUtil.checkNull(rs.getString("pjtname"))); // �꾨줈�앺듃紐�
                earlyWarning.put("partno", StringUtil.checkNull(rs.getString("partno"))); // 遺�뭹 踰덊샇
                earlyWarning.put("inout", StringUtil.checkNull(rs.getString("inout"))); // �앹궛泥�
                earlyWarning.put("fstcharge", StringUtil.checkNull(rs.getString("fstcharge"))); // �섑뻾�대떦��
                earlyWarning.put("sndcharge", StringUtil.checkNull(rs.getString("sndcharge"))); // �섑뻾�대떦��遺�
                earlyWarning.put("member", StringUtil.checkNull(rs.getString("member"))); // �쒕룞硫ㅻ쾭
                earlyWarning.put("creator", StringUtil.checkNull(rs.getString("creator")));  // �묒꽦��
                earlyWarning.put("createdate", StringUtil.checkNull(rs.getString("createdate"))); // �묒꽦�쇱옄
                earlyWarning.put("step", StringUtil.checkNull(rs.getString("step"))); // 吏꾪뻾�④퀎
                earlyWarning.put("oid", StringUtil.checkNull(rs.getString("oid"))); // 珥덇린�좊룞愿�━ oid
                earlyWarning.put("partname", StringUtil.checkNull(rs.getString("partname"))); // 遺�뭹紐�

                earlyWarningList.add(earlyWarning);
            }

            Kogger.debug(getClass(),  "###############  earlyWarning ===  "+earlyWarning );

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return earlyWarningList;
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
    public int ViewEarlyWarningListCnt ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        int listCnt = 0;

        try {

            Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "SELECT count(*)                                                                                                                         \n" );
            sb.append("   FROM KETEARLYWARNING            EW                                                                                         \n"); //초기유동관리 테이블
            sb.append("           , WTDOCUMENTMASTER        WTDM                                                                                    \n"); // WTDocument  마스터 테이블
            sb.append("           , PEOPLE                                PP                                                                                          \n"); // 사용자 테이블
            sb.append("           , PEOPLE                                PP2                                                                                        \n"); // 사용자 테이블(2)
            sb.append("           , PEOPLE                                PP3                                                                                        \n"); // 사용자 테이블(3)
            sb.append("           , PEOPLE                                PP4                                                                                        \n"); // 사용자 테이블(4)
            sb.append("           , NUMBERCODE                    NC                                                                                         \n"); // 코드 테이블
            sb.append("           , (SELECT CODE, NAME                                                                                                     \n"); // 코드 테이블(2)
            sb.append("                 FROM NUMBERCODE                                                                                                     \n");
            sb.append("               WHERE CODETYPE = 'EWSSTEP')     NC2                                                                         \n");
            sb.append("           , KETPARTNER                        KP                                                                                         \n"); // 협력사 테이블
            sb.append("           , E3PSPROJECTMASTER        PJ                                                                                         \n"); // 프로젝트 테이블
            sb.append("           ,(SELECT IDA3A5 tewsno                                                                                                  \n"); //부품 목표 테이블
            sb.append("                        , ltrim(sys_connect_by_path(PRODUCTNO,','),',') PRODUCTNO                             \n");
            sb.append("                FROM (SELECT IDA3A5                                                                                                \n");
            sb.append("                                     , PM.pno     PRODUCTNO                                                                         \n");
            sb.append("                                     , row_number() over  (partition by IDA3A5 order by PM.pno) rn               \n");
            sb.append("                                     , count(*) over (partition by IDA3A5) cnt                                                 \n");
            sb.append("                             FROM EARLYWARNINGTARGETLINK                                                                  \n");
            sb.append("                                     , KETEARLYWARNINGTARGET                                                                  \n");
            sb.append("                                     , ( SELECT CLASSNAMEA2A2 || ':' || IDA2A2      poid                               \n");
            sb.append("                                                    , PARTNO                                            pno                               \n");
            sb.append("                                            FROM PRODUCTPROJECT                                                                 \n");
            sb.append("                                           UNION                                                                                              \n");
            sb.append("                                          SELECT CLASSNAMEA2A2 || ':' || IDA2A2      poid                               \n");
            sb.append("                                                    , PARTNO                                            pno                                \n");
            sb.append("                                            FROM MOLDITEMINFO )   PM                                                            \n");
            sb.append("                           WHERE EARLYWARNINGTARGETLINK.IDA3B5 = KETEARLYWARNINGTARGET.IDA2A2      \n");
            sb.append("                                AND KETEARLYWARNINGTARGET.PRODUCTNO = PM.poid)                            \n");
            sb.append("              WHERE level = cnt                                                                                                         \n");
            sb.append("           start with rn = 1                                                                                                               \n");
            sb.append(" connect by prior IDA3A5 = IDA3A5 and prior rn=rn-1) ET                                                             \n");
            sb.append("           ,(SELECT IDA3A5 tewsno                                                                                                  \n"); //부품 목표 테이블(2)
            sb.append("                        , ltrim(sys_connect_by_path(PRODUCTNAME,','),',') PRODUCTNAME                     \n");
            sb.append("                FROM (SELECT IDA3A5                                                                                                \n");
            sb.append("                                     , PM.pname     PRODUCTNAME                                                                \n");
            sb.append("                                     , row_number() over  (partition by IDA3A5 order by PM.pname) rn          \n");
            sb.append("                                     , count(*) over (partition by IDA3A5) cnt                                                 \n");
            sb.append("                             FROM EARLYWARNINGTARGETLINK                                                                  \n");
            sb.append("                                     , KETEARLYWARNINGTARGET                                                                  \n");
            sb.append("                                     , ( SELECT PRODUCTPROJECT.CLASSNAMEA2A2 || ':' || PRODUCTPROJECT.IDA2A2    poid     \n");
            sb.append("                                                    , E3PSPROJECTMASTER.PJTNAME        pname                           \n");
            sb.append("                                            FROM PRODUCTPROJECT                                                                 \n");
            sb.append("                                                    , E3PSPROJECTMASTER                                                            \n");
            sb.append("                                          WHERE PRODUCTPROJECT.IDA3B8 = E3PSPROJECTMASTER.ida2a2     \n");
            sb.append("                                           UNION                                                                                              \n");
            sb.append("                                          SELECT CLASSNAMEA2A2 || ':' || IDA2A2      poid                               \n");
            sb.append("                                                    , PARTNAME                                         pname                          \n");
            sb.append("                                            FROM MOLDITEMINFO )   PM                                                            \n");
            sb.append("                           WHERE EARLYWARNINGTARGETLINK.IDA3B5 = KETEARLYWARNINGTARGET.IDA2A2      \n");
            sb.append("                                AND KETEARLYWARNINGTARGET.PRODUCTNO = PM.poid)                            \n");
            sb.append("              WHERE level = cnt                                                                                                         \n");
            sb.append("           start with rn = 1                                                                                                               \n");
            sb.append(" connect by prior IDA3A5 = IDA3A5 and prior rn=rn-1) ET2                                                           \n");
            sb.append("           ,(SELECT STATESTATE, IDA2A2, IDA3MASTERREFERENCE                                                   \n"); // 초기유동관리 단계 테이블
            sb.append("                FROM KETEARLYWARNINGSTEP                                                                                    \n");
            sb.append("              WHERE IDA2A2 in ( SELECT max(KEWS.IDA2A2)                                                             \n");
            sb.append("                                              FROM KETEARLYWARNINGSTEP               KEWS                              \n");
            sb.append("                                        GROUP BY KEWS.IDA3MASTERREFERENCE ))        ES                            \n");
            sb.append("           , KETEARLYWARNINGSTEPLINK    ESL                                                                                 \n"); // 초기유동관리 단계링크 테이블
            sb.append("           ,(SELECT CFTMEMBER  member, IDA2A2, IDA3MASTERREFERENCE                                    \n"); //계획서 테이블
            sb.append("                FROM KETEARLYWARNINGPLAN                                                                                    \n");
            sb.append("              WHERE IDA2A2 in ( SELECT max(KEWP.IDA2A2)                                                             \n");
            sb.append("                                              FROM KETEARLYWARNINGPLAN               KEWP                              \n");
            sb.append("                                        GROUP BY KEWP.IDA3MASTERREFERENCE ))        EP                             \n");
            sb.append("           ,(SELECT IDA3A5, IDA3B5                                                                                                 \n"); //계획서 링크 테이블
            sb.append("                FROM KETEARLYWARNINGPLANLINK                                                                             \n");
            sb.append("              WHERE IDA3B5 in ( SELECT max(IDA3B5)                                                                      \n");
            sb.append("                                              FROM KETEARLYWARNINGPLANLINK                                               \n");
            sb.append("                                        GROUP BY IDA3A5 ))        EPL                                                              \n");
            sb.append(" WHERE EW.IDA3MASTERREFERENCE = WTDM.IDA2A2                                                                 \n");
            sb.append("     AND EW.IDA3D2ITERATIONINFO = PP.IDA3B4                                                                             \n");
            sb.append("     AND ltrim(EW.FSTCHARGE, 'wt.org.WTUser:') = PP2.IDA3B4                                                    \n");
            sb.append("     AND ltrim(EW.SNDCHARGE, 'wt.org.WTUser:') = PP3.IDA3B4(+)                                              \n");
            sb.append("     AND ltrim(decode(instr(EP.member, '/', 1,1),0,EP.member,substr(EP.member,1,instr(EP.member, '/', 1,1)-1)),'wt.org.WTUser:') = PP4.IDA3B4(+)       \n");
            sb.append("     AND ltrim(EW.PROTEAMNO, 'e3ps.common.code.NumberCode:') = NC.IDA2A2(+)                   \n");
            sb.append("     AND ES.STATESTATE = NC2.CODE(+)                                                                                     \n");
            sb.append("     AND EW.PARTNERNO = KP.PARTNERNO(+)                                                                              \n");
            sb.append("     AND EW.IDA2A2 in (      SELECT max(KEW.IDA2A2)                                                                 \n");
            sb.append("                                           FROM KETEARLYWARNING               KEW                                           \n");
            sb.append("                                    GROUP BY KEW.IDA3MASTERREFERENCE )                                                \n");
            sb.append("     AND EW.IDA2A2 = ET.tewsno                                                                                                  \n");
            sb.append("     AND EW.IDA2A2 = ET2.tewsno                                                                                                  \n");
            sb.append("     AND EW.PJTNO = PJ.PJTNO(+)                                                                                                  \n");
            sb.append("     AND EW.IDA3MASTERREFERENCE = EPL.IDA3A5(+)                                                                  \n");
            sb.append("     AND EP.IDA3MASTERREFERENCE(+) = EPL.IDA3B5                                                                      \n");
            sb.append("     AND ES.IDA3MASTERREFERENCE = ESL.IDA3A5                                                                       \n");
            sb.append("     AND EW.IDA3MASTERREFERENCE(+) = ESL.IDA3B5                                                                  \n");

            if (((String)hash.get("ewsno")).length() > 0){
                sb.append("     AND upper(WTDM.WTDOCUMENTNUMBER) like ? "); // 초기유동관리 번호 검색조건
            }
            if (((String)hash.get("pjtno")).length() > 0){
                sb.append("     AND upper(EW.PJTNO)  like ? "); // 프로젝트 번호 검색조건
            }
            if (((String)hash.get("pjtname")).length() > 0){
                sb.append("     AND upper(PJ.PJTNAME)  like ? "); // 프로젝트명 검색조건
            }
            if (((String)hash.get("partno")).length() > 0){
                sb.append("     AND upper(ET.PRODUCTNO)  like ? "); // 부품 번호 검색조건
            }
            if (((String)hash.get("partname")).length() > 0){
                sb.append("     AND upper(ET2.PRODUCTNAME)  like ? "); // 부품명 검색조건
            }
            if (((String)hash.get("inout")).length() > 0){
                sb.append("     AND EW.INOUT  = ? "); // 생산처 구분 검색조건
            }
            if (((String)hash.get("production")).length() > 0){
                sb.append("     AND upper(decode(EW.INOUT , 'i' , NC.NAME , 'o' , KP.PARTNERNAME, ' ' ) ) like ? "); // 생산처 검색조건
            }
            if (((String)hash.get("fstcharge")).length() > 0){
                sb.append("     AND EW.FSTCHARGE  = ? "); // 수행담당자 검색조건
            }
            if (((String)hash.get("sndcharge")).length() > 0){
                sb.append("     AND EW.SNDCHARGE  = ? "); // 수행담당자(부) 검색조건
            }
            if (((String)hash.get("member")).length() > 0){
                sb.append("     AND EP.member  like ? "); // 활동멤버 검색조건
            }
            if (((String)hash.get("step")).length() > 0){
                sb.append("     AND NC2.CODE  = ? "); // 진행단계 검색조건
            }
            if (((String)hash.get("creator")).length() > 0){
                sb.append("     AND EW.IDA3D2ITERATIONINFO  = ltrim(?, 'wt.org.WTUser:') "); // 작성자 검색조건
            }
            if (((String)hash.get("createdate")).length() > 0){
                sb.append("     AND TO_CHAR(WTDM.CREATESTAMPA2, 'YYYY-MM-DD')  = ? "); // 작성일자 검색조건
            }
            if (((String)hash.get("useroid")).length() > 0){// 초기유동 이력
                sb.append("     AND (EW.FSTCHARGE  = ? ");
                sb.append("              OR EW.SNDCHARGE  = ? ");
                sb.append("              OR EW.IDA3D2ITERATIONINFO  = ltrim(?, 'wt.org.WTUser:') ");
                sb.append("              OR EP.member  like ?) ");
            }


            pstmt = new LoggableStatement( conn, sb.toString() );

            if (((String)hash.get("ewsno")).length() > 0){
                pstmt.setString( pstmtCnt++, StringUtil.getLikeQueryString(((String) hash.get("ewsno")).toUpperCase())); // 초기유동관리 번호 검색조건
            }
            if (((String)hash.get("pjtno")).length() > 0){
                pstmt.setString( pstmtCnt++,StringUtil.getLikeQueryString(((String) hash.get("pjtno")).toUpperCase())); // 프로젝트 번호 검색조건
            }
            if (((String)hash.get("pjtname")).length() > 0){
                pstmt.setString( pstmtCnt++,StringUtil.getLikeQueryString(((String) hash.get("pjtname")).toUpperCase())); // 프로젝트명 검색조건
            }
            if (((String)hash.get("partno")).length() > 0){
                pstmt.setString( pstmtCnt++,StringUtil.getLikeQueryString(((String) hash.get("partno")).toUpperCase())); // 부품 번호 검색조건
            }
            if (((String)hash.get("partname")).length() > 0){
                pstmt.setString( pstmtCnt++,StringUtil.getLikeQueryString(((String) hash.get("partname")).toUpperCase())); // 부품명 검색조건
            }
            if (((String)hash.get("inout")).length() > 0){
                pstmt.setString( pstmtCnt++, (String) hash.get("inout") ); // 생산처 구분 검색조건
            }
            if (((String)hash.get("production")).length() > 0){
                pstmt.setString( pstmtCnt++,StringUtil.getLikeQueryString(((String) hash.get("production")).toUpperCase())); // 생산처 검색조건
            }
            if (((String)hash.get("fstcharge")).length() > 0){
                pstmt.setString( pstmtCnt++, (String) hash.get("fstcharge") ); // 수행담당자 검색조건
            }
            if (((String)hash.get("sndcharge")).length() > 0){
                pstmt.setString( pstmtCnt++, (String) hash.get("sndcharge") ); // 수행담당자(부) 검색조건
            }
            if (((String)hash.get("member")).length() > 0){
                pstmt.setString( pstmtCnt++,StringUtil.getLikeQueryString((String) hash.get("member"))); // 활동멤버 검색조건
            }
            if (((String)hash.get("step")).length() > 0){
                pstmt.setString( pstmtCnt++, (String) hash.get("step") ); // 진행단계 검색조건
            }
            if (((String)hash.get("creator")).length() > 0){
                pstmt.setString( pstmtCnt++, (String) hash.get("creator") );  // 작성자 검색조건
            }
            if (((String)hash.get("createdate")).length() > 0){
                pstmt.setString( pstmtCnt++, (String) hash.get("createdate") ); // 작성일자 검색조건
            }
            if (((String)hash.get("useroid")).length() > 0){
                pstmt.setString( pstmtCnt++, (String) hash.get("useroid") ); // 초기유동 이력
                pstmt.setString( pstmtCnt++, (String) hash.get("useroid") ); // 초기유동 이력
                pstmt.setString( pstmtCnt++, (String) hash.get("useroid") ); // 초기유동 이력
                pstmt.setString( pstmtCnt++,StringUtil.getLikeQueryString((String) hash.get("useroid"))); // 초기유동 이력
            }

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
        }

        return listCnt;
    }

    /**
     * 함수명 : ViewPartList
     * 설명 :
     * @return ArrayList
     * @throws
     * 작성자 : 김경희
     * 작성일자 : 2011. 01. 18.
     */
    public ArrayList ViewPartList (String oid){
        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        Connection conn = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList list = null;

        try {
            conn = PlmDBUtil.getConnection();

            sb = new StringBuffer();
            sb.append(" SELECT ET.PRODUCTNO                                                                            partno                         \n"); // 부품
            sb.append("   FROM KETEARLYWARNING            EW                                                                                         \n"); //초기유동관리 테이블
            sb.append("           ,(SELECT IDA3A5  tewsno                                                                                                  \n");
            sb.append("                        , PRODUCTNO                                                                                                      \n");
            sb.append("                FROM EARLYWARNINGTARGETLINK                                                                               \n");
            sb.append("                        , KETEARLYWARNINGTARGET                                                                                \n");
            sb.append("              WHERE EARLYWARNINGTARGETLINK.IDA3B5 = KETEARLYWARNINGTARGET.IDA2A2)  ET      \n");
            sb.append("           ,(SELECT STATESTATE, IDA2A2, IDA3MASTERREFERENCE                                                   \n"); // 초기유동관리 단계 테이블
            sb.append("                FROM KETEARLYWARNINGSTEP                                                                                    \n");
            sb.append("              WHERE IDA2A2 in ( SELECT max(KEWS.IDA2A2)                                                             \n");
            sb.append("                                              FROM KETEARLYWARNINGSTEP               KEWS                              \n");
            sb.append("                                        GROUP BY KEWS.IDA3MASTERREFERENCE ))        ES                            \n");
            sb.append("           , KETEARLYWARNINGSTEPLINK    ESL                                                                                 \n"); // 초기유동관리 단계링크 테이블
            sb.append("WHERE EW.IDA2A2 in (      SELECT max(KEW.IDA2A2)                                                                 \n");
            sb.append("                                           FROM KETEARLYWARNING               KEW                                          \n");
            sb.append("                                    GROUP BY KEW.IDA3MASTERREFERENCE )                                               \n");
            sb.append("     AND EW.IDA2A2 = ET.tewsno                                                                                                  \n");
            sb.append("     AND ES.IDA3MASTERREFERENCE = ESL.IDA3A5                                                                       \n");
            sb.append("     AND EW.IDA3MASTERREFERENCE(+) = ESL.IDA3B5                                                                  \n");
            sb.append("     AND ES.STATESTATE not in ('EWRCOMPLETED')                                                                        \n");

            if (oid.length() > 0){
                sb.append("     AND EW.CLASSNAMEA2A2 || ':' || EW.IDA2A2  not in (?) ");
            }

            pstmt = new LoggableStatement( conn, sb.toString() );

            if (oid.length() > 0){
                pstmt.setString( pstmtCnt++, oid );
            }

            rs = pstmt.executeQuery();

            list = new ArrayList();

            while (rs.next()){
                list.add(rs.getString(1));
            }

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try {
                statementRsClose(rs, pstmt);
            } catch (Exception e) {
                Kogger.error(getClass(), e);
            }
            PlmDBUtil.close(conn);
        }

        return list;
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
