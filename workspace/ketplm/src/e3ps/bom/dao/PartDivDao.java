package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.util.WTProperties;
import ext.ket.shared.log.Kogger;

public class PartDivDao {

    private static final long serialVersionUID = 1476082172878154226L;
    public static final PartDivDao manager = new PartDivDao();

    static String dataStore = "Oracle"; //"SQLServer" ....
    static {
        try {
            dataStore = WTProperties.getLocalProperties().getProperty("wt.db.dataStore");
        }
        catch ( Exception ex ) {
            dataStore = "Oracle";
        }
    }

    protected PartDivDao() {
    }
    
    /**
     * @return
     * @throws Exception
     */
    public ArrayList getPartDivDTree(final String exceptUnuse) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList list = null;

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            list = new ArrayList();

            StringBuffer sb = null;

            sb = new StringBuffer();
            sb.append(" SELECT NVL(S.RN, 0) DT_PID, T.*            ");
            sb.append("   FROM (                                   ");
            sb.append("          SELECT ROWNUM DT_NID,             ");
            sb.append("                 LEVEL LVL,                 ");
            sb.append("                 TA.CID,                    ");
            sb.append("                 TA.PCID,                   ");
            sb.append("                 TA.PART_DIV_CODE,          ");
            sb.append("                 NVL(TA.PART_DIV_DESC, TA.PART_DIV_CODE) DESCRIPTION, ");
            sb.append("                 NVL(TA.PART_DIV_DESC, TA.PART_DIV_CODE)||'('||TA.PART_DIV_CODE||')' DESCRIPTION_WITH_CODE, ");
            sb.append("                 SYS_CONNECT_BY_PATH(TA.PART_DIV_CODE, '/') REAL_PATH, ");
            sb.append("                 SYS_CONNECT_BY_PATH(NVL(TA.PART_DIV_DESC, TA.PART_DIV_CODE), '/') DISP_PATH, ");
            sb.append("                 SYS_CONNECT_BY_PATH(NVL(TA.PART_DIV_DESC, TA.PART_DIV_CODE)||'('||TA.PART_DIV_CODE||')', '/') DISP_PATH_WITH_CODE, ");
            sb.append("                 CONNECT_BY_ISLEAF IS_LEAF, ");
            sb.append("                 TA.USE_FG                  ");
            if("true".equals(exceptUnuse)) {
                sb.append("            FROM (SELECT * FROM PARTDIV TAA WHERE TAA.USE_FG = 'Y') TA ");
            } else {
                sb.append("            FROM PARTDIV TA             ");
            }
            sb.append("           START WITH TA.PCID IS NULL       ");
            sb.append("         CONNECT BY PRIOR TA.CID = TA.PCID  ");
            sb.append("           ORDER SIBLINGS BY UPPER(NVL(TA.PART_DIV_DESC, TA.PART_DIV_CODE)) ASC  ");
            sb.append("        ) T,                                ");
            sb.append("        (                                   ");
            sb.append("          SELECT ROWNUM RN,                 ");
            sb.append("                 SA.CID                     ");
            if("true".equals(exceptUnuse)) {
                sb.append("            FROM (SELECT * FROM PARTDIV SAA WHERE SAA.USE_FG = 'Y') SA ");
            } else {
                sb.append("            FROM PARTDIV SA             ");
            }
            sb.append("           START WITH SA.PCID IS NULL       ");
            sb.append("         CONNECT BY PRIOR SA.CID = SA.PCID  ");
            sb.append("           ORDER SIBLINGS BY UPPER(NVL(SA.PART_DIV_DESC, SA.PART_DIV_CODE)) ASC  ");
            sb.append("        ) S                                 ");
            sb.append("  WHERE T.PCID = S.CID(+)                   ");
            sb.append("  ORDER BY T.DT_NID                         ");

            st = con.prepareStatement(sb.toString());
            rs = st.executeQuery();

            while ( rs.next() ) {
                list.add(new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                       rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                                       rs.getString(11), rs.getString(12), rs.getString(13) });
            }

            return list;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( rs != null ) {
                rs.close();
            }
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @param isInsert
     * @return
     * @throws Exception
     */
    public int getDupPartDivCodeCnt(final Hashtable hash, final boolean isInsert) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;

        ResultSet rs = null;
        int rtnVal = 0;

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT COUNT(*) CNT      ");
            sb.append("   FROM PARTDIV           ");
            sb.append("  WHERE PART_DIV_CODE = ? ");
            sb.append("    AND PART_DIV_LVL  = ? ");
            if ( "".equals(hash.get("partPCid")) ) {
                sb.append("    AND PCID IS NULL  ");
            } else {
                sb.append("    AND PCID = ?      ");
            }
            if ( !isInsert ) {
                sb.append("    AND CID <> ?      ");
            }

            st = con.prepareStatement(sb.toString());
            st.setString(1, (String) hash.get("partDivCode"));
            st.setInt(2, Integer.parseInt((String) hash.get("partDivLvl")));
            if ( !"".equals(hash.get("partPCid")) ) {
                st.setString(3, (String) hash.get("partPCid"));
                if ( !isInsert ) {
                    st.setString(4, (String) hash.get("partCid"));
                }
            } else {
                if ( !isInsert ) {
                    st.setString(3, (String) hash.get("partCid"));
                }
            }

            rs = st.executeQuery();
            if ( rs.next() ) {
                rtnVal = rs.getInt(1);
            }

            return rtnVal;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( rs != null ) {
                rs.close();
            }
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @param isInsert
     * @return
     * @throws Exception
     */
    public int getDupPartDivDescCnt(final Hashtable hash, final boolean isInsert) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;

        ResultSet rs = null;
        int rtnVal = 0;

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT COUNT(*) CNT      ");
            sb.append("   FROM PARTDIV           ");
            sb.append("  WHERE PART_DIV_DESC = ? ");
            sb.append("    AND PART_DIV_LVL  = ? ");
            if ( "".equals(hash.get("partPCid")) ) {
                sb.append("    AND PCID IS NULL  ");
            } else {
                sb.append("    AND PCID = ?      ");
            }
            if ( !isInsert ) {
                sb.append("    AND CID <> ?      ");
            }

            st = con.prepareStatement(sb.toString());
            st.setString(1, (String) hash.get("partDivDesc"));
            st.setInt(2, Integer.parseInt((String) hash.get("partDivLvl")));
            if ( !"".equals(hash.get("partPCid")) ) {
                st.setString(3, (String) hash.get("partPCid"));
                if ( !isInsert ) {
                    st.setString(4, (String) hash.get("partCid"));
                }
            } else {
                if ( !isInsert ) {
                    st.setString(3, (String) hash.get("partCid"));
                }
            }

            rs = st.executeQuery();
            if ( rs.next() ) {
                rtnVal = rs.getInt(1);
            }

            return rtnVal;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( rs != null ) {
                rs.close();
            }
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @return
     * @throws Exception
     */
    public String setPartDiv(final Hashtable hash) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;
        PreparedStatement st1 = null;
        ResultSet rs = null;
        int iRslt = 0;
        String rtnVal = "";

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append(" INSERT INTO PARTDIV(CID, PART_DIV_CODE, PART_DIV_DESC, PART_DIV_LVL, PCID, ");
            sb.append("                       USE_FG, CREATOR, CREATE_DATE) ");
            sb.append(" VALUES ((SELECT LPAD(TO_NUMBER(NVL(MAX(CID), 0)) + 1, 5, '0') FROM PARTDIV), ?, ?, ?, DECODE(?, '', NULL, ?), ?, ?, SYSDATE) ");

            st = con.prepareStatement(sb.toString());

            st.setString(1, (String) hash.get("partDivCode"));
            st.setString(2, (String) hash.get("partDivDesc"));
            st.setInt(3, Integer.parseInt((String) hash.get("partDivLvl")));
            st.setString(4, (String) hash.get("partPCid"));
            st.setString(5, (String) hash.get("partPCid"));
            st.setString(6, (String) hash.get("partDivUse"));
            st.setString(7, (String) hash.get("user"));

            iRslt = st.executeUpdate();

            if ( iRslt > 0 ) {
                sb = new StringBuffer();
                sb.append(" SELECT CID               ");
                sb.append("   FROM PARTDIV           ");
                sb.append("  WHERE PART_DIV_CODE = ? ");
                sb.append("    AND PART_DIV_DESC = ? ");
                sb.append("    AND PART_DIV_LVL  = ? ");
                sb.append("    AND USE_FG =  ?       ");
                sb.append("    AND CREATOR = ?       ");
                if ( "".equals(hash.get("partPCid")) ) {
                    sb.append("    AND PCID IS NULL  ");
                } else {
                    sb.append("    AND PCID = ?      ");
                }
                sb.append("    AND MODIFIER IS NULL  ");

                st1 = con.prepareStatement(sb.toString());

                st1.setString(1, (String) hash.get("partDivCode"));
                st1.setString(2, (String) hash.get("partDivDesc"));
                st1.setInt(3, Integer.parseInt((String) hash.get("partDivLvl")));
                st1.setString(4, (String) hash.get("partDivUse"));
                st1.setString(5, (String) hash.get("user"));
                if ( !"".equals(hash.get("partPCid")) ) {
                    st1.setString(6, (String) hash.get("partPCid"));
                }
                rs = st1.executeQuery();

                if ( rs.next() ) {
                    rtnVal = rs.getString(1);
                }
            }

            return rtnVal;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( rs != null ) {
                rs.close();
            }
            if ( st != null ) {
                st.close();
            }
            if ( st1 != null ) {
                st1.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @return
     * @throws Exception
     */
    public int delPartDiv(final Hashtable hash) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;
        int iRslt = 0;

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append(" DELETE FROM PARTDIV ");
            sb.append("  WHERE CID = ?      ");

            st = con.prepareStatement(sb.toString());

            st.setString(1, (String) hash.get("partCid"));

            iRslt = st.executeUpdate();

            return iRslt;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @return
     * @throws Exception
     */
    public int updatePartDiv(final Hashtable hash) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;
        int iRslt = 0;

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append(" UPDATE PARTDIV               ");
            sb.append("    SET PART_DIV_CODE = ?,    ");
            sb.append("        PART_DIV_DESC = ?,    ");
            sb.append("        USE_FG = ?,           ");
            sb.append("        MODIFIER = ?,         ");
            sb.append("        MODIFY_DATE = SYSDATE ");
            sb.append("  WHERE CID = ?               ");

            st = con.prepareStatement(sb.toString());

            st.setString(1, (String) hash.get("partDivCode"));
            st.setString(2, (String) hash.get("partDivDesc"));
            st.setString(3, (String) hash.get("partDivUse"));
            st.setString(4, (String) hash.get("user"));
            st.setString(5, (String) hash.get("partCid"));

            iRslt = st.executeUpdate();

            return iRslt;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @return
     * @throws Exception
     */
    public String getPartHeadNum(final String divCode) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;

        ResultSet rs = null;
        String rtnVal = "";

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append("  SELECT REPLACE(SYS_CONNECT_BY_PATH(TA.PART_DIV_CODE, '/'), '/', '') PART_HEAD_NUM ");
            sb.append("    FROM PARTDIV TA                ");
            sb.append("   WHERE TA.CID = ?                ");
            sb.append("   START WITH TA.PCID IS NULL      ");
            sb.append(" CONNECT BY PRIOR TA.CID = TA.PCID ");

            st = con.prepareStatement(sb.toString());
            st.setString(1, divCode);

            rs = st.executeQuery();
            if ( rs.next() ) {
                rtnVal = rs.getString(1);
            }

            return rtnVal;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( rs != null ) {
                rs.close();
            }
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @return
     * @throws Exception
     */
    public String getPartFolderPath(final String divCode) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;

        ResultSet rs = null;
        String rtnVal = "";

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append("  SELECT SYS_CONNECT_BY_PATH(NVL(TA.PART_DIV_DESC, TA.PART_DIV_CODE), '/') PART_NUM_PATH ");
            sb.append("    FROM PARTDIV TA                ");
            sb.append("   WHERE TA.CID = ?                ");
            sb.append("   START WITH TA.PCID IS NULL      ");
            sb.append(" CONNECT BY PRIOR TA.CID = TA.PCID ");

            st = con.prepareStatement(sb.toString());
            st.setString(1, divCode);

            rs = st.executeQuery();
            if ( rs.next() ) {
                rtnVal = rs.getString(1);
            }

            return rtnVal;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( rs != null ) {
                rs.close();
            }
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }

    /**
     * @param hash
     * @return
     * @throws Exception
     */
    public String getCidByPartNum(final String partNum) throws Exception
    {
        MethodContext methodcontext = null;
        WTConnection wtconnection = null;
        PreparedStatement st = null;

        ResultSet rs = null;
        String rtnVal = "";

        try {
            methodcontext = MethodContext.getContext();
            wtconnection = (WTConnection) methodcontext.getConnection();
            Connection con = wtconnection.getConnection();

            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT T.CID                             ");
            sb.append("   FROM (                                 ");
            sb.append("         SELECT REPLACE(SYS_CONNECT_BY_PATH(TA.PART_DIV_CODE, '/'), '/', '') PART_HEAD_NUM  ");
            sb.append("              , SYS_CONNECT_BY_PATH(TA.PART_DIV_DESC, '/') PART_PATH                        ");
            sb.append("              , TA.CID                    ");
            sb.append("           FROM PARTDIV TA                ");
            sb.append("          START WITH TA.PCID IS NULL      ");
            sb.append("        CONNECT BY PRIOR TA.CID = TA.PCID ");
            sb.append("        ) T                               ");
            sb.append("  WHERE T.PART_HEAD_NUM = SUBSTR(?, 1, 5) ");

            st = con.prepareStatement(sb.toString());
            st.setString(1, partNum);

            rs = st.executeQuery();
            if ( rs.next() ) {
                rtnVal = rs.getString(1);
            }

            return rtnVal;
        }
        catch ( Exception e ) {
            Kogger.error(getClass(), e);
            throw new Exception(e);
        }
        finally {
            if ( rs != null ) {
                rs.close();
            }
            if ( st != null ) {
                st.close();
            }
            if ( DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive() ) {
                MethodContext.getContext().freeConnection();
            }
        }
    }
    
}
