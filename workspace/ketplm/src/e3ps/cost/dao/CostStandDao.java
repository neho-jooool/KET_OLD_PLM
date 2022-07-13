package e3ps.cost.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import e3ps.cost.util.StringUtil;

public class CostStandDao {

    private Connection conn;

    public CostStandDao(Connection conn){
        this.conn = conn;
    }

    public CostStandDao(){
        super();
    }

    /**
     * 함수명 : getReerList
     * 설명 :환율DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 6. 1.
     */

    //환율조회
    public ArrayList getReerList()throws Exception{

        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> SearchMap = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append("select PK_RE, TO_CHAR(REER_DATE,'YYYYMMDD') REER_DATE, USD_rate, YEN_rate, EURO_rate, CNY_rate ");
        sb.append(" from REER");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                SearchMap = new Hashtable<String, String>();
                SearchMap.put("PK_RE", StringUtil.checkNull(rs.getString("PK_RE")));
                SearchMap.put("REER_date", StringUtil.checkNull(rs.getString("REER_date")));
                SearchMap.put("USD_rate", StringUtil.checkNull(rs.getString("USD_rate")));
                SearchMap.put("YEN_rate", StringUtil.checkNull(rs.getString("YEN_rate")));
                SearchMap.put("EURO_rate", StringUtil.checkNull(rs.getString("EURO_rate")));
                SearchMap.put("CNY_rate", StringUtil.checkNull(rs.getString("CNY_rate")));

                SearchList.add(SearchMap);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    //환율등록
    public int ReerCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String REER_date = null;
        String USD_rate = null;
        String YEN_rate = null;
        String EURO_rate = null;
        String CNY_rate = null;
        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            REER_date   = (String)SearchMap.get("REER_date");
            USD_rate      = (String)SearchMap.get("USD_rate");
            YEN_rate = (String)SearchMap.get("YEN_rate");
            EURO_rate = (String)SearchMap.get("EURO_rate");
            CNY_rate = (String)SearchMap.get("CNY_rate");

            sb.append(" INSERT INTO REER (PK_RE, REER_date, USD_rate, YEN_rate,EURO_rate,CNY_rate )");
            sb.append("   VALUES ((SELECT MAX(PK_RE)+1 FROM REER), ?, ?, ?, ?, ?)");
            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, REER_date);
                pstmt.setString(2, USD_rate);
                pstmt.setString(3, YEN_rate);
                pstmt.setString(4, EURO_rate);
                pstmt.setString(5, CNY_rate);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    //환율수정
    public int ReerUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_RE = null;
        String REER_date = null;
        String USD_rate = null;
        String YEN_rate = null;
        String EURO_rate = null;
        String CNY_rate = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap 	= (Hashtable)SearchList.get(i);
            PK_RE   	= (String)SearchMap.get("PK_RE");
            REER_date   = (String)SearchMap.get("REER_date");
            USD_rate 	= (String)SearchMap.get("USD_rate");
            YEN_rate 	= (String)SearchMap.get("YEN_rate");
            EURO_rate 	= (String)SearchMap.get("EURO_rate");
            CNY_rate 	= (String)SearchMap.get("CNY_rate");

            sb.append(" UPDATE REER");
            sb.append("   SET REER_date = ?,   USD_rate = ?,      YEN_rate = ?, EURO_rate = ?, CNY_rate = ?");
               sb.append(" 	WHERE PK_RE = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, REER_date);
                pstmt.setString(2, USD_rate);
                pstmt.setString(3, YEN_rate);
                pstmt.setString(4, EURO_rate);
                pstmt.setString(5, CNY_rate);
                pstmt.setString(6, PK_RE);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    //환율삭제
    public int ReerDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_RE = null;
        String REER_date = null;
        String USD_rate = null;
        String YEN_rate = null;
        String EURO_rate = null;
        String CNY_rate = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_RE   = (String)SearchMap.get("PK_RE");

            sb.append(" DELETE REER");
            sb.append("  WHERE PK_RE = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PK_RE);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getCuttingList
     * 설명 :절단비DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 09.
     */

    /*절단비 조회*/
    public ArrayList getCuttingList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> SearchMap = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT PK_CUT,     MET_TYPE,   TO_CHAR(MET_W) MET_W,      C_SIZE_T_1, C_SIZE_T_2, C_SIZE_T_3,");
        sb.append("        C_SIZE_T_4, C_SIZE_T_5, C_SIZE_T_6,    C_SIZE_T_7, C_SIZE_T_8, C_SIZE_T_9");
        sb.append("   FROM CUTTING_COST");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                SearchMap = new Hashtable<String, String>();
                SearchMap.put("PK_CUT", StringUtil.checkNull(rs.getString("PK_CUT")));
                SearchMap.put("MET_TYPE", StringUtil.checkNull(rs.getString("MET_TYPE")));
                SearchMap.put("MET_W", StringUtil.checkNull(rs.getString("MET_W")));
                SearchMap.put("C_SIZE_T_1", StringUtil.checkNull(rs.getString("C_SIZE_T_1")));
                SearchMap.put("C_SIZE_T_2", StringUtil.checkNull(rs.getString("C_SIZE_T_2")));
                SearchMap.put("C_SIZE_T_3", StringUtil.checkNull(rs.getString("C_SIZE_T_3")));
                SearchMap.put("C_SIZE_T_4", StringUtil.checkNull(rs.getString("C_SIZE_T_4")));
                SearchMap.put("C_SIZE_T_5", StringUtil.checkNull(rs.getString("C_SIZE_T_5")));
                SearchMap.put("C_SIZE_T_6", StringUtil.checkNull(rs.getString("C_SIZE_T_6")));
                SearchMap.put("C_SIZE_T_7", StringUtil.checkNull(rs.getString("C_SIZE_T_7")));
                SearchMap.put("C_SIZE_T_8", StringUtil.checkNull(rs.getString("C_SIZE_T_8")));
                SearchMap.put("C_SIZE_T_9", StringUtil.checkNull(rs.getString("C_SIZE_T_9")));

                SearchList.add(SearchMap);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*절단비등록*/
    public int CuttingCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String MET_TYPE = null;
        String MET_W = null;
        String C_SIZE_T_1 = null;
        String C_SIZE_T_2 = null;
        String C_SIZE_T_3 = null;
        String C_SIZE_T_4 = null;
        String C_SIZE_T_5 = null;
        String C_SIZE_T_6 = null;
        String C_SIZE_T_7 = null;
        String C_SIZE_T_8 = null;
        String C_SIZE_T_9 = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            MET_TYPE   = (String)SearchMap.get("met_type");
            MET_W      = (String)SearchMap.get("met_w");
            C_SIZE_T_1 = (String)SearchMap.get("c_size_t_1");
            C_SIZE_T_2 = (String)SearchMap.get("c_size_t_2");
            C_SIZE_T_3 = (String)SearchMap.get("c_size_t_3");
            C_SIZE_T_4 = (String)SearchMap.get("c_size_t_4");
            C_SIZE_T_5 = (String)SearchMap.get("c_size_t_5");
            C_SIZE_T_6 = (String)SearchMap.get("c_size_t_6");
            C_SIZE_T_7 = (String)SearchMap.get("c_size_t_7");
            C_SIZE_T_8 = (String)SearchMap.get("c_size_t_8");
            C_SIZE_T_9 = (String)SearchMap.get("c_size_t_9");

            sb.append(" INSERT INTO CUTTING_COST (PK_CUT, MET_TYPE, MET_W, C_SIZE_T_1,C_SIZE_T_2,C_SIZE_T_3,C_SIZE_T_4,  ");
            sb.append("                           C_SIZE_T_5,C_SIZE_T_6,C_SIZE_T_7,C_SIZE_T_8,C_SIZE_T_9)");
            sb.append("   VALUES ((SELECT MAX(PK_CUT)+1 FROM CUTTING_COST),?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MET_TYPE);
                pstmt.setString(2, MET_W);
                pstmt.setString(3, C_SIZE_T_1);
                pstmt.setString(4, C_SIZE_T_2);
                pstmt.setString(5, C_SIZE_T_3);
                pstmt.setString(6, C_SIZE_T_4);
                pstmt.setString(7, C_SIZE_T_5);
                pstmt.setString(8, C_SIZE_T_6);
                pstmt.setString(9, C_SIZE_T_7);
                pstmt.setString(10, C_SIZE_T_8);
                pstmt.setString(11, C_SIZE_T_9);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*절단비수정*/
    public int CuttingUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_CUT = null;
        String MET_TYPE = null;
        String MET_W = null;
        String C_SIZE_T_1 = null;
        String C_SIZE_T_2 = null;
        String C_SIZE_T_3 = null;
        String C_SIZE_T_4 = null;
        String C_SIZE_T_5 = null;
        String C_SIZE_T_6 = null;
        String C_SIZE_T_7 = null;
        String C_SIZE_T_8 = null;
        String C_SIZE_T_9 = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_CUT   = (String)SearchMap.get("pk_cut");
            MET_TYPE   = (String)SearchMap.get("met_type");
            MET_W      = (String)SearchMap.get("met_w");
            C_SIZE_T_1 = (String)SearchMap.get("c_size_t_1");
            C_SIZE_T_2 = (String)SearchMap.get("c_size_t_2");
            C_SIZE_T_3 = (String)SearchMap.get("c_size_t_3");
            C_SIZE_T_4 = (String)SearchMap.get("c_size_t_4");
            C_SIZE_T_5 = (String)SearchMap.get("c_size_t_5");
            C_SIZE_T_6 = (String)SearchMap.get("c_size_t_6");
            C_SIZE_T_7 = (String)SearchMap.get("c_size_t_7");
            C_SIZE_T_8 = (String)SearchMap.get("c_size_t_8");
            C_SIZE_T_9 = (String)SearchMap.get("c_size_t_9");

            sb.append(" UPDATE CUTTING_COST");
            sb.append("    SET MET_TYPE = ?,   MET_W = ?,      C_SIZE_T_1 = ?, C_SIZE_T_2 = ?, C_SIZE_T_3 = ?, C_SIZE_T_4 = ?");
            sb.append("       ,C_SIZE_T_5 = ?, C_SIZE_T_6 = ?, C_SIZE_T_7 = ?, C_SIZE_T_8 = ?, C_SIZE_T_9 = ?");
            sb.append("  WHERE PK_CUT = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MET_TYPE);
                pstmt.setString(2, MET_W);
                pstmt.setString(3, C_SIZE_T_1);
                pstmt.setString(4, C_SIZE_T_2);
                pstmt.setString(5, C_SIZE_T_3);
                pstmt.setString(6, C_SIZE_T_4);
                pstmt.setString(7, C_SIZE_T_5);
                pstmt.setString(8, C_SIZE_T_6);
                pstmt.setString(9, C_SIZE_T_7);
                pstmt.setString(10, C_SIZE_T_8);
                pstmt.setString(11, C_SIZE_T_9);
                pstmt.setString(12, PK_CUT);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
            //	if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*절단비삭제*/
    public int CuttingDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_CUT = null;
        String MET_TYPE = null;
        String MET_W = null;
        String C_SIZE_T_1 = null;
        String C_SIZE_T_2 = null;
        String C_SIZE_T_3 = null;
        String C_SIZE_T_4 = null;
        String C_SIZE_T_5 = null;
        String C_SIZE_T_6 = null;
        String C_SIZE_T_7 = null;
        String C_SIZE_T_8 = null;
        String C_SIZE_T_9 = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_CUT   = (String)SearchMap.get("pk_cut");

            sb.append(" DELETE CUTTING_COST");
            sb.append("  WHERE PK_CUT = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PK_CUT);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
            //	if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getPlatingList
     * 설명 :도금비DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 09.
     */

    /*도금비조회*/
    public ArrayList getPlatingList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> plating = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT PK_PLC, MET_TYPE, P_SIZE_T_1, P_SIZE_T_2, P_SIZE_T_3, P_SIZE_T_4");
        sb.append("   FROM PLATING_COST");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                plating = new Hashtable<String, String>();
                plating.put("PK_PLC", StringUtil.checkNull(rs.getString("PK_PLC")));
                plating.put("MET_TYPE", StringUtil.checkNull(rs.getString("MET_TYPE")));
                plating.put("P_SIZE_T_1", StringUtil.checkNull(rs.getString("P_SIZE_T_1")));
                plating.put("P_SIZE_T_2", StringUtil.checkNull(rs.getString("P_SIZE_T_2")));
                plating.put("P_SIZE_T_3", StringUtil.checkNull(rs.getString("P_SIZE_T_3")));
                plating.put("P_SIZE_T_4", StringUtil.checkNull(rs.getString("P_SIZE_T_4")));

                SearchList.add(plating);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*도금비등록*/
    public int platingCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String MET_TYPE = null;
        String P_SIZE_T_1 = null;
        String P_SIZE_T_2 = null;
        String P_SIZE_T_3 = null;
        String P_SIZE_T_4 = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            MET_TYPE   = (String)SearchMap.get("met_type");
            P_SIZE_T_1 = (String)SearchMap.get("p_size_t_1");
            P_SIZE_T_2 = (String)SearchMap.get("p_size_t_2");
            P_SIZE_T_3 = (String)SearchMap.get("p_size_t_3");
            P_SIZE_T_4 = (String)SearchMap.get("p_size_t_4");

            sb.append(" INSERT INTO PLATING_COST (PK_PLC, MET_TYPE, P_SIZE_T_1,P_SIZE_T_2,P_SIZE_T_3,P_SIZE_T_4) ");
            sb.append("   VALUES ((SELECT MAX(PK_PLC)+1 FROM PLATING_COST),?, ?, ?, ?, ?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MET_TYPE);
                pstmt.setString(2, P_SIZE_T_1);
                pstmt.setString(3, P_SIZE_T_2);
                pstmt.setString(4, P_SIZE_T_3);
                pstmt.setString(5, P_SIZE_T_4);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*도금비수정*/
    public int PlatingUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_PLC = null;
        String MET_TYPE = null;
        String P_SIZE_T_1 = null;
        String P_SIZE_T_2 = null;
        String P_SIZE_T_3 = null;
        String P_SIZE_T_4 = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_PLC     = (String)SearchMap.get("PK_PLC");
            MET_TYPE   = (String)SearchMap.get("MET_TYPE");
            P_SIZE_T_1 = (String)SearchMap.get("P_SIZE_T_1");
            P_SIZE_T_2 = (String)SearchMap.get("P_SIZE_T_2");
            P_SIZE_T_3 = (String)SearchMap.get("P_SIZE_T_3");
            P_SIZE_T_4 = (String)SearchMap.get("P_SIZE_T_4");

            sb.append(" UPDATE PLATING_COST");
            sb.append("    SET MET_TYPE = ?,   P_SIZE_T_1 = ?, P_SIZE_T_2 = ?, P_SIZE_T_3 = ?, P_SIZE_T_4 = ?");
            sb.append("  WHERE PK_PLC = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MET_TYPE);
                pstmt.setString(2, P_SIZE_T_1);
                pstmt.setString(3, P_SIZE_T_2);
                pstmt.setString(4, P_SIZE_T_3);
                pstmt.setString(5, P_SIZE_T_4);
                pstmt.setString(6, PK_PLC);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*도금비삭제*/
    public int PlatingDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_PLC = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_PLC   = (String)SearchMap.get("PK_PLC");

            sb.append(" DELETE PLATING_COST");
            sb.append("  WHERE PK_PLC = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PK_PLC);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getProcessingList
     * 설명 :가공비DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 11.
     */

    /*가공비조회*/
    public ArrayList getProcessingList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> plating = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT PK_PC, PC_COST_TYPE, MET_TYPE, PRO_COST");
        sb.append("   FROM PROCESSING_COST");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                plating = new Hashtable<String, String>();
                plating.put("PK_PC", StringUtil.checkNull(rs.getString("PK_PC")));
                plating.put("PC_COST_TYPE", StringUtil.checkNull(rs.getString("PC_COST_TYPE")));
                plating.put("MET_TYPE", StringUtil.checkNull(rs.getString("MET_TYPE")));
                plating.put("PRO_COST", StringUtil.checkNull(rs.getString("PRO_COST")));

                SearchList.add(plating);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*가공비등록*/
    public int processingCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;

        String PC_COST_TYPE = null;
        String MET_TYPE = null;
        String PRO_COST = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PC_COST_TYPE = (String)SearchMap.get("PC_COST_TYPE");
            MET_TYPE = (String)SearchMap.get("MET_TYPE");
            PRO_COST = (String)SearchMap.get("PRO_COST");

            sb.append(" INSERT INTO PROCESSING_COST (PK_PC, PC_COST_TYPE, MET_TYPE,PRO_COST) ");
            sb.append("   VALUES ((SELECT MAX(PK_PC)+1 FROM PLATING_COST),?, ?, ?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PC_COST_TYPE);
                pstmt.setString(2, MET_TYPE);
                pstmt.setString(3, PRO_COST);

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*가공비수정*/
    public int ProcessingUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_PC = null;
        String PC_COST_TYPE = null;
        String MET_TYPE = null;
        String PRO_COST = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_PC     = (String)SearchMap.get("pk_pc");
            PC_COST_TYPE   = (String)SearchMap.get("pc_cost_type");
            MET_TYPE = (String)SearchMap.get("met_type");
            PRO_COST = (String)SearchMap.get("pro_cost");


            sb.append(" UPDATE PROCESSING_COST");
            sb.append("    SET PC_COST_TYPE = ?,   MET_TYPE = ?, PRO_COST = ?");
            sb.append("  WHERE PK_PC = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PC_COST_TYPE);
                pstmt.setString(2, MET_TYPE);
                pstmt.setString(3, PRO_COST);
                pstmt.setString(4, PK_PC);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*가공비삭제*/
    public int ProcessingDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_PC = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_PC   = (String)SearchMap.get("pk_pc");

            sb.append(" DELETE PROCESSING_COST");
            sb.append("  WHERE PK_PC = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PK_PC);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getPmakerList
     * 설명 :비철원재료정보DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 13.
     */

    /*비철원재료정보조회*/
    public ArrayList getPmakerList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> Pmaker = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT PK_PR, MK_CODE, MAKER_NAME, MET_TYPE,GRADE_NAME,S_GRAVITY");
        sb.append("   FROM PRESS_MAKER");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Pmaker = new Hashtable<String, String>();
                Pmaker.put("PK_PR", StringUtil.checkNull(rs.getString("PK_PR")));
                Pmaker.put("MK_CODE", StringUtil.checkNull(rs.getString("MK_CODE")));
                Pmaker.put("MAKER_NAME", StringUtil.checkNull(rs.getString("MAKER_NAME")));
                Pmaker.put("MET_TYPE", StringUtil.checkNull(rs.getString("MET_TYPE")));
                Pmaker.put("GRADE_NAME", StringUtil.checkNull(rs.getString("GRADE_NAME")));
                Pmaker.put("S_GRAVITY", StringUtil.checkNull(rs.getString("S_GRAVITY")));
                SearchList.add(Pmaker);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*비철원재료정보등록*/
    public int PmakerCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;

        String MK_CODE = null;
        String MAKER_NAME = null;
        String MET_TYPE = null;
        String GRADE_NAME = null;
        String S_GRAVITY = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            MK_CODE = (String)SearchMap.get("MK_CODE");
            MAKER_NAME = (String)SearchMap.get("MAKER_NAME");
            MET_TYPE = (String)SearchMap.get("MET_TYPE");
            GRADE_NAME = (String)SearchMap.get("GRADE_NAME");
            S_GRAVITY = (String)SearchMap.get("S_GRAVITY");

            sb.append(" INSERT INTO PRESS_MAKER (PK_PR, MK_CODE, MAKER_NAME, MET_TYPE,GRADE_NAME,S_GRAVITY) ");
            sb.append("   VALUES ((SELECT MAX(PK_PR)+1 FROM PRESS_MAKER),?, ?, ?, ?,?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MK_CODE);
                pstmt.setString(2, MAKER_NAME);
                pstmt.setString(3, MET_TYPE);
                pstmt.setString(4, GRADE_NAME);
                pstmt.setString(5, S_GRAVITY);

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*비철원재료정보수정*/
    public int PmakerUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_PR = null;
        String MK_CODE = null;
        String MAKER_NAME = null;
        String MET_TYPE = null;
        String GRADE_NAME = null;
        String S_GRAVITY = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_PR     = (String)SearchMap.get("PK_PR");
            MK_CODE   = (String)SearchMap.get("MK_CODE");
            MAKER_NAME = (String)SearchMap.get("MAKER_NAME");
            MET_TYPE = (String)SearchMap.get("MET_TYPE");
            GRADE_NAME = (String)SearchMap.get("GRADE_NAME");
            S_GRAVITY = (String)SearchMap.get("S_GRAVITY");


            sb.append(" UPDATE PRESS_MAKER");
            sb.append("    SET MK_CODE = ?,   MAKER_NAME = ?, MET_TYPE = ?, GRADE_NAME = ?, S_GRAVITY = ?");
            sb.append("  WHERE PK_PR = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MK_CODE);
                pstmt.setString(2, MAKER_NAME);
                pstmt.setString(3, MET_TYPE);
                pstmt.setString(4, GRADE_NAME);
                pstmt.setString(5, S_GRAVITY);
                pstmt.setString(6, PK_PR);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*비철원재료정보삭제*/
    public int PmakerDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_PR = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_PR   = (String)SearchMap.get("PK_PR");

            sb.append(" DELETE PRESS_MAKER");
            sb.append("  WHERE PK_PR = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PK_PR);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getMmakerList
     * 설명 :수지원재료정보DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 14.
     */

    /*수지원재료정보조회*/
    public ArrayList getMmakerList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> Mmaker = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT PK_MK, MK_CODE, MAKER_NAME, MATERIAL_NAME, GRADE_NAME, GRADE_COLOR, ");
        sb.append(" 	GRADE_COST, SU_STAN_DAY");
        sb.append("   FROM MOLD_MAKER");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Mmaker = new Hashtable<String, String>();
                Mmaker.put("PK_MK", StringUtil.checkNull(rs.getString("PK_MK")));
                Mmaker.put("MK_CODE", StringUtil.checkNull(rs.getString("MK_CODE")));
                Mmaker.put("MAKER_NAME", StringUtil.checkNull(rs.getString("MAKER_NAME")));
                Mmaker.put("MATERIAL_NAME", StringUtil.checkNull(rs.getString("MATERIAL_NAME")));
                Mmaker.put("GRADE_NAME", StringUtil.checkNull(rs.getString("GRADE_NAME")));
                Mmaker.put("GRADE_COLOR", StringUtil.checkNull(rs.getString("GRADE_COLOR")));
                Mmaker.put("GRADE_COST", StringUtil.checkNull(rs.getString("GRADE_COST")));
                Mmaker.put("SU_STAN_DAY", StringUtil.checkNull(rs.getString("SU_STAN_DAY")));
                SearchList.add(Mmaker);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*수지원재료정보등록*/
    public int MmakerCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;

        String PK_MK = null;
        String MK_CODE = null;
        String MAKER_NAME = null;
        String MATERIAL_NAME = null;
        String GRADE_NAME = null;
        String GRADE_COLOR = null;
        String GRADE_COST = null;
        String SU_STAN_DAY = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_MK = (String)SearchMap.get("PK_MK");
            MK_CODE = (String)SearchMap.get("MK_CODE");
            MAKER_NAME = (String)SearchMap.get("MAKER_NAME");
            MATERIAL_NAME = (String)SearchMap.get("MATERIAL_NAME");
            GRADE_NAME = (String)SearchMap.get("GRADE_NAME");
            GRADE_COLOR = (String)SearchMap.get("GRADE_COLOR");
            GRADE_COST = (String)SearchMap.get("GRADE_COST");
            SU_STAN_DAY = (String)SearchMap.get("SU_STAN_DAY");

            sb.append(" INSERT INTO MOLD_MAKER (PK_MK,MK_CODE, MAKER_NAME, MATERIAL_NAME, ");
            sb.append(" 						GRADE_NAME, GRADE_COLOR,GRADE_COST,SU_STAN_DAY) ");
            sb.append("   VALUES ((SELECT MAX(PK_MK)+1 FROM MOLD_MAKER),?, ?, ?, ?,?,?,?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MK_CODE);
                pstmt.setString(2, MAKER_NAME);
                pstmt.setString(3, MATERIAL_NAME);
                pstmt.setString(4, GRADE_NAME);
                pstmt.setString(5, GRADE_COLOR);
                pstmt.setString(6, GRADE_COST);
                pstmt.setString(7, SU_STAN_DAY);

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*수지원재료정보수정*/
    public int MmakerUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_MK = null;
        String MK_CODE = null;
        String MAKER_NAME = null;
        String MATERIAL_NAME = null;
        String GRADE_NAME = null;
        String GRADE_COLOR = null;
        String GRADE_COST = null;
        String SU_STAN_DAY = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_MK     = (String)SearchMap.get("PK_MK");
            MK_CODE   = (String)SearchMap.get("MK_CODE");
            MAKER_NAME = (String)SearchMap.get("MAKER_NAME");
            MATERIAL_NAME = (String)SearchMap.get("MATERIAL_NAME");
            GRADE_NAME = (String)SearchMap.get("GRADE_NAME");
            GRADE_COLOR = (String)SearchMap.get("GRADE_COLOR");
            GRADE_COST = (String)SearchMap.get("GRADE_COST");
            SU_STAN_DAY = (String)SearchMap.get("SU_STAN_DAY");

            sb.append(" UPDATE MOLD_MAKER");
            sb.append("    SET MK_CODE = ?,   MAKER_NAME = ?, MATERIAL_NAME = ?, GRADE_NAME = ?,");
            sb.append("    GRADE_COLOR = ?, GRADE_COST = ?, SU_STAN_DAY = ?");
            sb.append("  WHERE PK_MK = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, MK_CODE);
                pstmt.setString(2, MAKER_NAME);
                pstmt.setString(3, MATERIAL_NAME);
                pstmt.setString(4, GRADE_NAME);
                pstmt.setString(5, GRADE_COLOR);
                pstmt.setString(6, GRADE_COST);
                pstmt.setString(7, SU_STAN_DAY);
                pstmt.setString(8, PK_MK);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*수지원재료정보삭제*/
    public int MmakerDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String PK_MK = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            PK_MK   = (String)SearchMap.get("PK_MK");

            sb.append(" DELETE MOLD_MAKER");
            sb.append("  WHERE PK_MK = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, PK_MK);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getLMEList
     * 설명 :LME정보DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 15.
     */

    /*LME정보조회*/
    public ArrayList getLMEList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> LME = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT pk_LME, TO_CHAR(in_date,'YYYYMMDD') in_date, USD_rate, YEN_rate, Lme_cu, Lme_zn, ");
        sb.append(" 	Lme_sn, Lme_ni, Lme_type");
        sb.append("   FROM LME");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                LME = new Hashtable<String, String>();

                LME.put("pk_LME",	StringUtil.checkNull(rs.getString("pk_LME")));
                LME.put("in_date",	StringUtil.checkNull(rs.getString("in_date")));
                LME.put("USD_rate", StringUtil.checkNull(rs.getString("USD_rate")));
                LME.put("YEN_rate",	StringUtil.checkNull(rs.getString("YEN_rate")));
                LME.put("Lme_cu",	StringUtil.checkNull(rs.getString("Lme_cu")));
                LME.put("Lme_zn",	StringUtil.checkNull(rs.getString("Lme_zn")));
                LME.put("Lme_sn",	StringUtil.checkNull(rs.getString("Lme_sn")));
                LME.put("Lme_ni",	StringUtil.checkNull(rs.getString("Lme_ni")));
                LME.put("Lme_type",	StringUtil.checkNull(rs.getString("Lme_type")));
                SearchList.add(LME);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*LME정보등록*/
    public int LMECreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;

        String pk_LME   = null;
        String in_date  = null;
        String USD_rate = null;
        String YEN_rate = null;
        String Lme_cu   = null;
        String Lme_zn   = null;
        String Lme_sn   = null;
        String Lme_ni   = null;
        String Lme_type = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_LME  = (String)SearchMap.get("pk_LME");
            in_date = (String)SearchMap.get("in_date");
            USD_rate= (String)SearchMap.get("USD_rate");
            YEN_rate= (String)SearchMap.get("YEN_rate");
            Lme_cu  = (String)SearchMap.get("Lme_cu");
            Lme_zn  = (String)SearchMap.get("Lme_zn");
            Lme_sn  = (String)SearchMap.get("Lme_sn");
            Lme_ni  = (String)SearchMap.get("Lme_ni");
            Lme_type= (String)SearchMap.get("Lme_type");

            sb.append(" INSERT INTO LME (pk_LME,in_date, USD_rate, YEN_rate, ");
            sb.append(" 			Lme_cu, Lme_zn,Lme_sn,Lme_ni,Lme_type)");
            sb.append("   VALUES ((SELECT MAX(pk_LME)+1 FROM LME),?,?,?,?,?,?,?,?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, in_date);
                pstmt.setString(2, USD_rate);
                pstmt.setString(3, YEN_rate);
                pstmt.setString(4, Lme_cu);
                pstmt.setString(5, Lme_zn);
                pstmt.setString(6, Lme_sn);
                pstmt.setString(7, Lme_ni);
                pstmt.setString(8, Lme_type);

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*LME정보수정*/
    public int LMEUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String pk_LME   = null;
        String in_date  = null;
        String USD_rate = null;
        String YEN_rate = null;
        String Lme_cu   = null;
        String Lme_zn   = null;
        String Lme_sn   = null;
        String Lme_ni   = null;
        String Lme_type = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_LME  = (String)SearchMap.get("pk_LME");
            in_date = (String)SearchMap.get("in_date");
            USD_rate= (String)SearchMap.get("USD_rate");
            YEN_rate= (String)SearchMap.get("YEN_rate");
            Lme_cu  = (String)SearchMap.get("Lme_cu");
            Lme_zn  = (String)SearchMap.get("Lme_zn");
            Lme_sn  = (String)SearchMap.get("Lme_sn");
            Lme_ni  = (String)SearchMap.get("Lme_ni");
            Lme_type= (String)SearchMap.get("Lme_type");

            sb.append(" UPDATE LME");
            sb.append("    SET in_date = ?,   USD_rate = ?, YEN_rate = ?, Lme_cu = ?,");
            sb.append("    Lme_zn = ?, Lme_sn = ?, Lme_ni = ?, Lme_type = ?");
            sb.append("  WHERE pk_LME = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, in_date);
                pstmt.setString(2, USD_rate);
                pstmt.setString(3, YEN_rate);
                pstmt.setString(4, Lme_cu);
                pstmt.setString(5, Lme_zn);
                pstmt.setString(6, Lme_sn);
                pstmt.setString(7, Lme_ni);
                pstmt.setString(8, Lme_type);
                pstmt.setString(9, pk_LME);

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*LME정보삭제*/
    public int LMEDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String pk_LME = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_LME   = (String)SearchMap.get("pk_LME");

            sb.append(" DELETE LME");
            sb.append("  WHERE pk_LME = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, pk_LME);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getDisList
     * 설명 :물류흐름DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 15.
     */

    /*Dis정보조회*/
    public ArrayList getDisList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> Dis = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT pk_Dis, distri_type, store, dest, dest_1, distri_cost ");
        sb.append("   FROM distribution_cost");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Dis = new Hashtable<String, String>();
                Dis.put("pk_dis",	StringUtil.checkNull(rs.getString("pk_dis")));
                Dis.put("distri_type",	StringUtil.checkNull(rs.getString("distri_type")));
                Dis.put("store",	StringUtil.checkNull(rs.getString("store")));
                Dis.put("dest",		StringUtil.checkNull(rs.getString("dest")));
                Dis.put("dest_1",	StringUtil.checkNull(rs.getString("dest_1")));
                Dis.put("distri_cost",	StringUtil.checkNull(rs.getString("distri_cost")));

                SearchList.add(Dis);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*Dis정보등록*/
    public int DisCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;

        String pk_dis     = null;
        String distri_type= null;
        String store      = null;
        String dest       = null;
        String dest_1     = null;
        String distri_cost= null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_dis     = (String)SearchMap.get("pk_dis");
            distri_type= (String)SearchMap.get("distri_type");
            store      = (String)SearchMap.get("store");
            dest       = (String)SearchMap.get("dest");
            dest_1     = (String)SearchMap.get("dest_1");
            distri_cost= (String)SearchMap.get("distri_cost");


            sb.append(" INSERT INTO distribution_cost (pk_dis,distri_type, store, dest, ");
            sb.append(" 			dest_1, distri_cost) ");
            sb.append("   VALUES ((SELECT MAX(pk_dis)+1 FROM distribution_cost),?, ?, ?, ?,?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, distri_type );
                pstmt.setString(2, store);
                pstmt.setString(3, dest);
                pstmt.setString(4, dest_1  );
                pstmt.setString(5, distri_cost  );

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*Dis정보수정*/
    public int DisUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String pk_dis     = null;
        String distri_type= null;
        String store      = null;
        String dest       = null;
        String dest_1     = null;
        String distri_cost= null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_dis     = (String)SearchMap.get("pk_dis");
            distri_type= (String)SearchMap.get("distri_type");
            store      = (String)SearchMap.get("store");
            dest       = (String)SearchMap.get("dest");
            dest_1     = (String)SearchMap.get("dest_1");
            distri_cost= (String)SearchMap.get("distri_cost");

            sb.append(" UPDATE distribution_cost");
            sb.append("    SET distri_type = ?,   store = ?, dest = ?, dest_1 = ?,");
            sb.append("    distri_cost = ?");
            sb.append("  WHERE pk_dis = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());

                pstmt.setString(1, distri_type );
                pstmt.setString(2, store);
                pstmt.setString(3, dest);
                pstmt.setString(4, dest_1  );
                pstmt.setString(5, distri_cost  );
                pstmt.setString(6, pk_dis  );

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*Dis정보삭제*/
    public int DisDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String pk_dis = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_dis   = (String)SearchMap.get("pk_dis");

            sb.append(" DELETE distribution_cost");
            sb.append("  WHERE pk_dis = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, pk_dis);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /**
     * 함수명 : getExpcostList
     * 설명 :제조경비기준가DB
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 최윤정
     * 작성일자 : 2012. 06. 15.
     */

    /*Expcost정보조회*/
    public ArrayList getExpcostList() throws Exception{
        ArrayList<Hashtable<String, String>> SearchList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> Expcost = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT pk_Expcost, Expcosttri_type, store, dest, dest_1, Expcosttri_cost ");
        sb.append("   FROM expcost_standard");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            //pstmt.setString(1, gubun);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Expcost = new Hashtable<String, String>();
                Expcost.put("pk_st",		StringUtil.checkNull(rs.getString("pk_st")));
                Expcost.put("st_team",		StringUtil.checkNull(rs.getString("st_team")));
                Expcost.put("st_pro_type",	StringUtil.checkNull(rs.getString("st_pro_type")));
                Expcost.put("st_met",		StringUtil.checkNull(rs.getString("st_met")));
                Expcost.put("st_jun_cost",	StringUtil.checkNull(rs.getString("st_jun_cost")));
                Expcost.put("st_ma_depr",	StringUtil.checkNull(rs.getString("st_ma_depr")));
                Expcost.put("st_tabalu",	StringUtil.checkNull(rs.getString("st_tabalu")));
                Expcost.put("st_overhead",	StringUtil.checkNull(rs.getString("st_overhead")));
                Expcost.put("st_ge_cost",	StringUtil.checkNull(rs.getString("st_ge_cost")));
                Expcost.put("st_jae_cost",	StringUtil.checkNull(rs.getString("st_jae_cost")));
                Expcost.put("st_rnd_cost",	StringUtil.checkNull(rs.getString("st_rnd_cost")));
                Expcost.put("st_royalty",	StringUtil.checkNull(rs.getString("st_royalty")));
                Expcost.put("st_person",	StringUtil.checkNull(rs.getString("st_person")));
                Expcost.put("st_rate",		StringUtil.checkNull(rs.getString("st_rate")));
                Expcost.put("st_eff_value",	StringUtil.checkNull(rs.getString("st_eff_value")));

                SearchList.add(Expcost);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return SearchList;
    }

    /*Expcost정보등록*/
    public int ExpcostCreate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;

        String pk_st        = null;
        String st_team      = null;
        String st_pro_type  = null;
        String st_met       = null;
        String st_jun_cost  = null;
        String st_ma_depr   = null;
        String st_tabalu    = null;
        String st_overhead  = null;
        String st_ge_cost   = null;
        String st_jae_cost  = null;
        String st_rnd_cost  = null;
        String st_royalty   = null;
        String st_person    = null;
        String st_rate      = null;
        String st_eff_value = null;


        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_st       	= (String)SearchMap.get("pk_st");
            st_team     	= (String)SearchMap.get("st_team");
            st_pro_type 	= (String)SearchMap.get("st_pro_type");
            st_met      	= (String)SearchMap.get("st_met");
            st_jun_cost 	= (String)SearchMap.get("st_jun_cost");
            st_ma_depr  	= (String)SearchMap.get("st_ma_depr");
            st_tabalu   	= (String)SearchMap.get("st_tabalu");
            st_overhead 	= (String)SearchMap.get("st_overhead");
            st_ge_cost  	= (String)SearchMap.get("st_ge_cost");
            st_jae_cost 	= (String)SearchMap.get("st_jae_cost");
            st_rnd_cost 	= (String)SearchMap.get("st_rnd_cost");
            st_royalty  	= (String)SearchMap.get("st_royalty");
            st_person   	= (String)SearchMap.get("st_person");
            st_rate     	= (String)SearchMap.get("st_rate");
            st_eff_value	= (String)SearchMap.get("st_eff_value");



            sb.append(" INSERT INTO expcost_standard (pk_st,st_team, st_pro_type, st_met, st_jun_cost,");
            sb.append(" 			st_ma_depr, st_tabalu,st_overhead,st_ge_cost,");
            sb.append(" 			st_jae_cost, st_rnd_cost,st_royalty,st_person,st_rate,st_eff_value) ");
            sb.append("   VALUES ((SELECT MAX(pk_st)+1 FROM expcost_standard),?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, pk_st       );
                pstmt.setString(2, st_team     );
                pstmt.setString(3, st_pro_type );
                pstmt.setString(4, st_met      );
                pstmt.setString(5, st_jun_cost );
                pstmt.setString(6, st_ma_depr  );
                pstmt.setString(7, st_tabalu   );
                pstmt.setString(8, st_overhead );
                pstmt.setString(9, st_ge_cost  );
                pstmt.setString(10, st_jae_cost );
                pstmt.setString(11, st_rnd_cost );
                pstmt.setString(12, st_royalty  );
                pstmt.setString(13, st_person   );
                pstmt.setString(14, st_rate     );
                pstmt.setString(15, st_eff_value);


                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*Expcost정보수정*/
    public int ExpcostUpdate(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;

        String pk_st        = null;
        String st_team      = null;
        String st_pro_type  = null;
        String st_met       = null;
        String st_jun_cost  = null;
        String st_ma_depr   = null;
        String st_tabalu    = null;
        String st_overhead  = null;
        String st_ge_cost   = null;
        String st_jae_cost  = null;
        String st_rnd_cost  = null;
        String st_royalty   = null;
        String st_person    = null;
        String st_rate      = null;
        String st_eff_value = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_st       	= (String)SearchMap.get("pk_st");
            st_team     	= (String)SearchMap.get("st_team");
            st_pro_type 	= (String)SearchMap.get("st_pro_type");
            st_met      	= (String)SearchMap.get("st_met");
            st_jun_cost 	= (String)SearchMap.get("st_jun_cost");
            st_ma_depr  	= (String)SearchMap.get("st_ma_depr");
            st_tabalu   	= (String)SearchMap.get("st_tabalu");
            st_overhead 	= (String)SearchMap.get("st_overhead");
            st_ge_cost  	= (String)SearchMap.get("st_ge_cost");
            st_jae_cost 	= (String)SearchMap.get("st_jae_cost");
            st_rnd_cost 	= (String)SearchMap.get("st_rnd_cost");
            st_royalty  	= (String)SearchMap.get("st_royalty");
            st_person   	= (String)SearchMap.get("st_person");
            st_rate     	= (String)SearchMap.get("st_rate");
            st_eff_value	= (String)SearchMap.get("st_eff_value");

            sb.append(" UPDATE expcost_standard");
            sb.append("    SET st_team = ?, st_pro_type = ?, st_met = ?, st_jun_cost = ?,");
            sb.append("    st_ma_depr = ?,st_tabalu=?, st_overhead=?, st_ge_cost=?, st_jae_cost =?,");
            sb.append("    st_rnd_cost = ?,st_royalty=?, st_person=?, st_rate=?, st_eff_value =? )");
            sb.append("  WHERE pk_st = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, pk_st       );
                pstmt.setString(2, st_team     );
                pstmt.setString(3, st_pro_type );
                pstmt.setString(4, st_met      );
                pstmt.setString(5, st_jun_cost );
                pstmt.setString(6, st_ma_depr  );
                pstmt.setString(7, st_tabalu   );
                pstmt.setString(8, st_overhead );
                pstmt.setString(9, st_ge_cost  );
                pstmt.setString(10, st_jae_cost );
                pstmt.setString(11, st_rnd_cost );
                pstmt.setString(12, st_royalty  );
                pstmt.setString(13, st_person   );
                pstmt.setString(14, st_rate     );
                pstmt.setString(15, st_eff_value);

                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

    /*Expcost정보삭제*/
    public int ExpcostDelete(ArrayList SearchList) throws Exception{
        Hashtable SearchMap = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int  complet = 0;
        String pk_st = null;

        for(int i = 0; i < SearchList.size() ; i++)
        {
            SearchMap = (Hashtable)SearchList.get(i);
            pk_st   = (String)SearchMap.get("pk_st");

            sb.append(" DELETE expcost_standard");
            sb.append("  WHERE pk_st = ?");

            try{
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, pk_st);
                complet = pstmt.executeUpdate()+complet;

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                sb.delete( 0 , sb.length() );
                if(pstmt!=null) {pstmt.close();}
                //if(conn!=null) {conn.close();}
            }
        }
        return complet;
    }

}




