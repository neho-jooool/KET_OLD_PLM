package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import e3ps.cost.util.StringUtil;

public class CostInvestDao {

    private Connection conn;

    public CostInvestDao(Connection conn){
        this.conn = conn;
    }

    public CostInvestDao(){
        super();
    }

    /**
     * 함수명 : setInsertAuth
     * 설명 :  개발원가 투자비내역 등록
     * @param ArrayList invest_item
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 27.
     */
    public int setInsertInvest(ArrayList itemList) throws Exception{
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        Hashtable insertItem = (Hashtable)itemList.get(0);
        int complet = 0;
        sb.append(" INSERT INTO cost_invest (invest_no, report_pk, gubun, part_name, to_cost, to_su, to_type, to_note, item_name, item_cost, cost_sum, invest_order) \n");
        sb.append("     VALUES ((select nvl(max(invest_no),0)+1 from cost_invest), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, Integer.parseInt((String)insertItem.get("report_pk")));
            pstmt.setString(2, (String)insertItem.get("gubun"));
            pstmt.setString(3, (String)insertItem.get("part_name"));
            pstmt.setString(4, (String)insertItem.get("to_cost"));
            pstmt.setString(5, (String)insertItem.get("to_su"));
            pstmt.setString(6, (String)insertItem.get("to_type"));
            pstmt.setString(7, (String)insertItem.get("to_note"));
            pstmt.setString(8, (String)insertItem.get("pro_cost_name"));
            pstmt.setString(9, (String)insertItem.get("pro_cost"));
            pstmt.setString(10, (String)insertItem.get("cost_sum"));
            pstmt.setString(11, (String)insertItem.get("invest_order"));
            complet = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return complet;
    }


    /**
     * 함수명 : getCostInvest
     * 설명 :  개발원가 투자비내역 조회
     * @param String report_pk
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2013. 06. 27.
     */
    public ArrayList getCostInvest(String report_pk, String gubun) throws Exception{
        ArrayList<Hashtable<String, String>> investList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> investHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT report_pk, gubun, part_name, to_cost, to_su, to_type, to_note, item_name, item_cost, gubun_cnt, invest_no, cost_sum, invest_order ");
        sb.append(" FROM cost_invest ");
        sb.append(" WHERE gubun = ? AND report_pk = ? ORDER BY invest_no");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, gubun);
            pstmt.setString(2, report_pk);
            rs = pstmt.executeQuery();

            while(rs.next()){
                investHash = new Hashtable<String, String>();
                investHash.put("report_pk", StringUtil.checkNull(rs.getString("report_pk")));
                investHash.put("gubun", StringUtil.checkNull(rs.getString("gubun")));
                investHash.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
                investHash.put("to_cost", StringUtil.checkNull(rs.getString("to_cost")));
                investHash.put("to_su", StringUtil.checkNull(rs.getString("to_su")));
                investHash.put("to_type", StringUtil.checkNull(rs.getString("to_type")));
                investHash.put("to_note", StringUtil.checkNull(rs.getString("to_note")));
                investHash.put("item_name", StringUtil.checkNull(rs.getString("item_name")));
                investHash.put("item_cost", StringUtil.checkNull(rs.getString("item_cost")));
                investHash.put("gubun_cnt", StringUtil.checkNull(rs.getString("gubun_cnt")));
                investHash.put("invest_no", StringUtil.checkNull(rs.getString("invest_no")));
                investHash.put("cost_sum", StringUtil.checkNull(rs.getString("cost_sum")));
                investHash.put("invest_order", StringUtil.checkNull(rs.getString("invest_order")));
                investList.add(investHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return investList;
    }

    /**
     * 함수명 : getCostInvest
     * 설명 :  개발원가 투자비내역 조회
     * @param String report_pk
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2013. 06. 27.
     */
    public ArrayList getCostInvestFull(String report_pk) throws Exception{
        ArrayList<Hashtable<String, String>> investList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> investHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT A.report_pk, A.gubun, A.part_name, A.to_cost, A.to_su, A.to_type, A.to_note, A.item_name, A.item_cost, A.gubun_cnt, A.invest_no, A.cost_sum, A.invest_order, B.cnt ");
        sb.append(" FROM cost_invest A, (SELECT gubun, count(gubun) as cnt FROM cost_invest WHERE report_pk = ? GROUP BY gubun) B ");
        sb.append(" WHERE A.gubun = B.gubun AND A.report_pk = ? order by A.invest_no");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, report_pk);
            pstmt.setString(2, report_pk);
            rs = pstmt.executeQuery();

            while(rs.next()){
                investHash = new Hashtable<String, String>();
                investHash.put("report_pk", StringUtil.checkNull(rs.getString("report_pk")));
                investHash.put("gubun", StringUtil.checkNull(rs.getString("gubun")));
                investHash.put("part_name", StringUtil.checkNull(rs.getString("part_name")));
                investHash.put("to_cost", StringUtil.checkNull(rs.getString("to_cost")));
                investHash.put("to_su", StringUtil.checkNull(rs.getString("to_su")));
                investHash.put("to_type", StringUtil.checkNull(rs.getString("to_type")));
                investHash.put("to_note", StringUtil.checkNull(rs.getString("to_note")));
                investHash.put("item_name", StringUtil.checkNull(rs.getString("item_name")));
                investHash.put("item_cost", StringUtil.checkNull(rs.getString("item_cost")));
                investHash.put("gubun_cnt", StringUtil.checkNull(rs.getString("gubun_cnt")));
                investHash.put("invest_no", StringUtil.checkNull(rs.getString("invest_no")));
                investHash.put("cost_sum", StringUtil.checkNull(rs.getString("cost_sum")));
                investHash.put("invest_order", StringUtil.checkNull(rs.getString("invest_order")));
                investHash.put("cnt", StringUtil.checkNull(rs.getString("cnt")));
                investList.add(investHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return investList;
    }

    /**
     * 함수명 : setDelteInvest
     * 설명 :  개발원가 투자비내역 수정 시 삭제
     * @param String empno, String auth, String group
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2013. 07. 02.
     */
    public int setDeleteInvest(String report_pk) throws Exception{
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int complet = 0;
        sb.append(" DELETE FROM cost_invest WHERE report_pk = ? ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, report_pk);
            complet = pstmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return complet;
    }
}