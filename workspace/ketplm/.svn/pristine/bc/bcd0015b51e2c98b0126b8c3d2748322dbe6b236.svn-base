/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EarlyWarningDao.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 10. 18.
 */
package e3ps.dms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : DevRequestDao
 * 설명 : 개발검토조회
 * 작성자 : 김경종
 * 작성일자 : 2010. 11.
 */
public class DevRequestDao {

    private Connection conn;

    public DevRequestDao(Connection conn){
        this.conn = conn;
    }

    public String CustmerEvName ( String carType ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        String cName = "";

        try {

            sb = new StringBuffer();
            sb.append(" SELECT b.name                      			\n");
            sb.append(" from OEMProjectType a, NumberCode b         \n");
            sb.append(" where a.name = ?                       \n");
            sb.append(" and a.CLASSNAMEKEYA4 = b.CLASSNAMEA2A2      \n");
            sb.append(" and a.IDA3A4 = b.IDA2A2                     \n");

            pstmt = new LoggableStatement( conn, sb.toString() );

            pstmt.setString( 1, carType);

            rs = pstmt.executeQuery();
            while (rs.next()){
                cName = rs.getString("name");
            }

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return cName;
    }

    public List<Map<String, String>> ViewDRList ( Map hash ) throws Exception{

        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {

            Kogger.debug(getClass(), "----------------------ViewDRList 시작  -----------------  \n ");
            sb = new StringBuffer();
            sb.append(" select A.CLASSNAMEA2A2||':'||A.IDA2A2 oid, B.WTDOCUMENTNUMBER, A.DEVELOPMENTSTEP, A.PROJECTOID, \n");
            sb.append(" A.REPCARTYPE, \n");
            sb.append(" A.DEVPRODUCTNAME, \n");
            sb.append(" bu.buyername REQUESTBUYER,  \n");
            sb.append(" DECODE(SUBSTR(A.DEVREVIEWTYPECODE,1,4),'e3ps',  \n");
            sb.append("           (select name from NumberCode where CLASSNAMEA2A2||':'||IDA2A2 = A.DEVREVIEWTYPECODE), \n");
            sb.append("           A.DEVREVIEWTYPECODE) DEVREVIEWTYPECODE, \n");
            sb.append(" A.STATESTATE STATE, \n");
            sb.append(" I.NAME CREATOR, \n");
            sb.append(" to_char(A.CREATESTAMPA2,'yyyy-mm-dd') CREATESTAMPA2, \n");
            sb.append(" wm_concat(distinct D.PARTNAME) PARTNAME, \n");
            sb.append(" wm_concat(distinct F.CARTYPECODE) CARTYPECODE \n");
            sb.append(" from KETDevelopmentRequest A, \n");
            sb.append(" WTDocumentMaster B, \n");
            sb.append(" KETRequestPartLink C, \n");
            sb.append(" KETRequestPartList D, \n");
            sb.append(" KETPartCarLink E, \n");
            sb.append(" KETCarYearlyAmount F, \n");
            sb.append(" PHASELINK G, \n");
            sb.append(" PHASETEMPLATE H, \n");
            sb.append(" PEOPLE I, \n");

            sb.append(" (SELECT ida2a2 reqno \n");
            sb.append("             , ltrim(sys_connect_by_path(buyername,','),',') buyername       \n");
            sb.append("     FROM (select t.ida2a2 \n");
            sb.append("                        , n.name buyername \n");
            sb.append("                        , row_number() over  (partition by t.ida2a2 order by n.name) rn  \n");
            sb.append("                        , count(*) over (partition by t.ida2a2) cnt    \n");
            sb.append("                  from KETDEVELOPMENTREQUEST t \n");
            sb.append("                        , NUMBERCODE n \n");
            sb.append("                where t.requestbuyer like '%'||n.CLASSNAMEA2A2 || ':' || n.IDA2A2||'%')  \n");
            sb.append("    WHERE level = cnt \n");
            sb.append(" start with rn = 1   \n");
            sb.append(" connect by prior ida2a2 = ida2a2 and prior rn=rn-1  ) bu, \n");

            sb.append(" (SELECT ida2a2 reqno \n");
            sb.append("             , ltrim(sys_connect_by_path(lastbuyer,','),',') lastbuyer       \n");
            sb.append("     FROM (select t.ida2a2 \n");
            sb.append("                        , n.name lastbuyer \n");
            sb.append("                        , row_number() over  (partition by t.ida2a2 order by n.name) rn  \n");
            sb.append("                        , count(*) over (partition by t.ida2a2) cnt    \n");
            sb.append("                  from KETDEVELOPMENTREQUEST t \n");
            sb.append("                        , NUMBERCODE n \n");
            sb.append("                where t.lastusingbuyer like '%'||n.CLASSNAMEA2A2 || ':' || n.IDA2A2||'%')  \n");
            sb.append("    WHERE level = cnt \n");
            sb.append(" start with rn = 1   \n");
            sb.append(" connect by prior ida2a2 = ida2a2 and prior rn=rn-1  ) la \n");

            sb.append(" where A.CLASSNAMEKEYMASTERREFERENCE = B.CLASSNAMEA2A2 \n");
            sb.append(" AND A.IDA3MASTERREFERENCE = B.IDA2A2 \n");
            sb.append(" AND A.LATESTITERATIONINFO = 1 \n");
            sb.append(" AND A.CLASSNAMEA2A2 = C.CLASSNAMEKEYROLEAOBJECTREF \n");
            sb.append(" AND A.IDA2A2 = C.IDA3A5 \n");
            sb.append(" AND C.CLASSNAMEKEYROLEBOBJECTREF = D.CLASSNAMEA2A2 \n");
            sb.append(" AND C.IDA3B5 = D.IDA2A2 \n");
            sb.append(" AND D.CLASSNAMEA2A2 = E.CLASSNAMEKEYROLEAOBJECTREF \n");
            sb.append(" AND D.IDA2A2 = E.IDA3A5 \n");
            sb.append(" AND E.CLASSNAMEKEYROLEBOBJECTREF = F.CLASSNAMEA2A2 \n");
            sb.append(" AND E.IDA3B5 = F.IDA2A2 \n");
            sb.append(" AND A.IDA3A2STATE = G.IDA3A5 \n");
            sb.append(" AND G.IDA3B5 = H.IDA2A2 \n");
            sb.append(" AND H.PHASESTATE = A.STATESTATE \n");
            sb.append(" AND A.IDA3D2ITERATIONINFO = I.IDA3B4 \n");
            sb.append(" and A.IDA2A2 = bu.reqno \n");
            sb.append(" and A.IDA2A2 = la.reqno \n");


            // 자동차, 전자 사업부
            if (((String)hash.get("divCode")).length() > 0){
                sb.append(" AND A.DIVISIONCODE =   '" +  (String)hash.get("divCode") + "'  \n");
            }

            // 구분-개발의뢰,검토의뢰
            String developmentStep =   (String)hash.get("HdDevelopmentStep");
            if ( developmentStep != null && developmentStep.trim().length() > 0 && !developmentStep.equalsIgnoreCase("null") ) {
                sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.DEVELOPMENTSTEP", developmentStep, false)).append("		\n");
            }

            if (((String)hash.get("DevProductName")).length() > 0){
                sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.DEVPRODUCTNAME", (String)hash.get("DevProductName"), true)).append("        \n");
            }

            //[START][KET PLM 고도화 프로젝트] 의뢰처 Like 검색으로 변경, 2014. 6. 11. Jason, Han
            if (((String)hash.get("RequestBuyer")).length() > 0){
                sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("bu.buyername", "*"+(String)hash.get("RequestBuyer")+"*", true)).append("        \n");
            }
	    //[END][KET PLM 고도화 프로젝트] 의뢰처 Like 검색으로 변경, 2014. 6. 11. Jason, Han

            if (((String)hash.get("LastUsingBuyer")).length() > 0){
                sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("la.lastbuyer", (String)hash.get("LastUsingBuyer"), true)).append("        \n");
            }

            if (((String)hash.get("creator")).length() > 0){
                sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("I.ID", (String)hash.get("creator"), true)).append("        \n");
            }

            if (((String)hash.get("RepCarType")).length() > 0){
                sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.REPCARTYPE", (String)hash.get("RepCarType"), true)).append("        \n");
            }

            // 결재 상태
             String authorStatus =   (String)hash.get("HdAuthorStatus");
             if ( authorStatus != null && authorStatus.trim().length() > 0 && !authorStatus.equalsIgnoreCase("null") ) {
                 sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("H.PHASESTATE", authorStatus, false)).append("		\n");
             }


            // 검토유형
            String devReviewTypeCode =   (String)hash.get("HdDevReviewTypeCode");
             if ( devReviewTypeCode != null && devReviewTypeCode.trim().length() > 0 && !devReviewTypeCode.equalsIgnoreCase("null") ) {
                 sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.DevReviewTypeCode", devReviewTypeCode, false)).append("		\n");
             }

            // 제품군
            String productCategoryCode =   (String)hash.get("HdProductCategoryCode");
             if ( productCategoryCode != null && productCategoryCode.trim().length() > 0 && !productCategoryCode.equalsIgnoreCase("null") ) {
                 sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.ProductCategoryCode", productCategoryCode, false)).append("		\n");
             }


            if (((String)hash.get("predate")).length() > 0){
                sb.append(" AND A.CREATESTAMPA2 > TO_DATE( '"+  (String)hash.get("predate") +  "'  ||'000000','YYYY-MM-DDHH24MISS')                \n");
            }

            if (((String)hash.get("postdate")).length() > 0){
                sb.append(" AND A.CREATESTAMPA2 < TO_DATE('"+  (String)hash.get("postdate") +  "'||'235959','YYYY-MM-DDHH24MISS')                \n");
            }

            sb.append(" GROUP BY A.CLASSNAMEA2A2||':'||A.IDA2A2, B.WTDOCUMENTNUMBER, A.DEVELOPMENTSTEP, A.PROJECTOID, \n");
            sb.append(" A.REPCARTYPE, \n");
            sb.append(" A.DEVPRODUCTNAME, \n");
            sb.append(" bu.buyername, \n");
            sb.append(" A.DEVREVIEWTYPECODE, \n");
            sb.append(" A.STATESTATE, \n");
            sb.append(" I.NAME, \n");
            sb.append(" A.CREATESTAMPA2 \n");

            Kogger.debug(getClass(), sb.toString());

            pstmt = new LoggableStatement( conn, sb.toString() );
            rs = pstmt.executeQuery();
            while (rs.next()){
                Map<String, String> row = new HashMap<String, String>();

                row.put("oid", rs.getString("oid"));
                row.put("drNo",rs.getString("WTDOCUMENTNUMBER"));
                row.put("developmentStep",rs.getString("DEVELOPMENTSTEP"));
                row.put("projectNo",StringUtil.checkNull(rs.getString("PROJECTOID")));
                row.put("repCarType",rs.getString("REPCARTYPE"));
                row.put("devProductName",rs.getString("DEVPRODUCTNAME"));
                row.put("requestBuyer",rs.getString("REQUESTBUYER"));
                row.put("devReviewTypeCode",rs.getString("DEVREVIEWTYPECODE"));
                row.put("drState" ,rs.getString("STATE"));
                row.put("drCreator" ,rs.getString("CREATOR"));
                row.put("drCreateDate" ,rs.getString("CREATESTAMPA2"));
                row.put("partName" ,rs.getString("PARTNAME"));
                row.put("carTypeCode" ,rs.getString("CARTYPECODE"));

                list.add(row);
            }


        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return list;
    }

    public List<Map<String, String>> ViewPopDRList ( Map hash ) throws Exception{
        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        try {

            sb = new StringBuffer();
            sb.append(" select A.CLASSNAMEA2A2||':'||A.IDA2A2 oid, B.WTDOCUMENTNUMBER, \n");
            sb.append(" A.DEVELOPMENTSTEP, \n");
            sb.append(" A.DEVPRODUCTNAME, \n");
            sb.append(" H.NAME STATE, \n");
            sb.append("  DECODE(SUBSTR(A.PROJECTOID,14,4), \n");
            sb.append(" 	        'Prod',(SELECT L.PJTNO FROM ProductProject K, E3PSProjectMaster L  WHERE K.IDA2A2 = TO_NUMBER(SUBSTR(A.PROJECTOID,INSTR(A.PROJECTOID,':')+1)) AND K.IDA3B8 = L.IDA2A2),  \n");
            sb.append(" 	        'Revi',(SELECT L.PJTNO FROM ReviewProject K, E3PSProjectMaster L  WHERE K.IDA2A2 = TO_NUMBER(SUBSTR(A.PROJECTOID,INSTR(A.PROJECTOID,':')+1)) AND K.IDA3B8 = L.IDA2A2),  \n");
            sb.append(" 	        'Mold',(SELECT L.PJTNO FROM MoldProject K, E3PSProjectMaster L  WHERE K.IDA2A2 = TO_NUMBER(SUBSTR(A.PROJECTOID,INSTR(A.PROJECTOID,':')+1)) AND K.IDA3B8 = L.IDA2A2),  \n");
            sb.append(" 	        ' ') PJTNO  \n");
            sb.append(" from KETDevelopmentRequest A, \n");
            sb.append(" WTDocumentMaster B, \n");
            sb.append(" PHASELINK G, \n");
            sb.append(" PHASETEMPLATE H \n");
            sb.append(" where A.CLASSNAMEKEYMASTERREFERENCE = B.CLASSNAMEA2A2 \n");
            sb.append(" AND A.IDA3MASTERREFERENCE = B.IDA2A2 \n");
            sb.append(" AND A.LATESTITERATIONINFO = 1 \n");
            sb.append(" AND A.STATESTATE = 'APPROVED' \n");
            sb.append(" AND A.IDA3A2STATE = G.IDA3A5 \n");
            sb.append(" AND G.IDA3B5 = H.IDA2A2 \n");
            sb.append(" AND H.PHASESTATE = A.STATESTATE \n");

            //if (((String)hash.get("divCode")).length() > 0){
            //	sb.append(" AND A.DIVISIONCODE =   '"+  (String)hash.get("divCode") +  "'   \n");
            //}

            Kogger.debug(getClass(), " ############ DevelopmentStep   "+   (String)hash.get("DevelopmentStepStr")  );

            String s_developmentStep = (String)hash.get("DevelopmentStepStr");
            if(!s_developmentStep.equals("0")){
                sb.append(" AND A.DEVELOPMENTSTEP = '"+  (String)hash.get("DevelopmentStepStr") +  "'  \n");

            }
            if (((String)hash.get("DevProductName")).length() > 0){
                sb.append("AND upper(A.DEVPRODUCTNAME) LIKE upper( '"+  KETStringUtil.getLikeString((String)hash.get("DevProductName")) +  "' ) \n");

            }
            if (((String)hash.get("RequestNo")).length() > 0){
                sb.append(" AND B.WTDOCUMENTNUMBER LIKE      '"+  KETStringUtil.getLikeString((String)hash.get("RequestNo")) +  "'   \n");

            }

            if (((String)hash.get("projectNo")).length() > 0){
                sb.append(" AND ( EXISTS (SELECT L.PJTNO FROM MoldProject K, E3PSProjectMaster L  WHERE K.IDA2A2 = TO_NUMBER(SUBSTR(A.PROJECTOID,INSTR(A.PROJECTOID,':')+1)) AND K.IDA3B8 = L.IDA2A2 AND L.PJTNO LIKE  '"+  KETStringUtil.getLikeString((String)hash.get("projectNo")) +  "'   )  \n");
                sb.append(" 	    OR EXISTS (SELECT L.PJTNO FROM ReviewProject K, E3PSProjectMaster L  WHERE K.IDA2A2 = TO_NUMBER(SUBSTR(A.PROJECTOID,INSTR(A.PROJECTOID,':')+1)) AND K.IDA3B8 = L.IDA2A2 AND L.PJTNO LIKE '"+  KETStringUtil.getLikeString((String)hash.get("projectNo")) +  "' )  \n");
                sb.append(" 	    OR EXISTS (SELECT L.PJTNO FROM ProductProject K, E3PSProjectMaster L  WHERE K.IDA2A2 = TO_NUMBER(SUBSTR(A.PROJECTOID,INSTR(A.PROJECTOID,':')+1)) AND K.IDA3B8 = L.IDA2A2 AND L.PJTNO LIKE  '"+  KETStringUtil.getLikeString((String)hash.get("projectNo")) +  "' ))  \n");
            }

            sb.append(" ORDER BY B.WTDOCUMENTNUMBER DESC \n");

            Kogger.debug(getClass(), "----------------------sb.toString()   -----------------  \n "+ sb.toString() );
            pstmt = new LoggableStatement( conn, sb.toString() );
            rs = pstmt.executeQuery();

            while (rs.next()){
                Map<String, String> row = new HashMap<String, String>();

                row.put("drNo",rs.getString("WTDOCUMENTNUMBER"));
                row.put("developmentStep",rs.getString("DEVELOPMENTSTEP"));
                row.put("devProductName",rs.getString("DEVPRODUCTNAME"));
                row.put("drState" ,rs.getString("STATE"));
                row.put("oid", rs.getString("oid"));
                row.put("pjtNo" ,rs.getString("PJTNO"));
                list.add(row);
            }


        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return list;
    }

    public void statementRsClose(ResultSet rs, LoggableStatement pstmt) throws Exception
    {
        if( rs != null )
            rs.close();

        if( pstmt != null )
            pstmt.close();
    }
}
