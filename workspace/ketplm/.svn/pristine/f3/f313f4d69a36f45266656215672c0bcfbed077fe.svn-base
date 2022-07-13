package e3ps.cost.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import e3ps.cost.util.StringUtil;

public class CostEaseAccDao {

    private Connection conn;

    public CostEaseAccDao(Connection conn){
        this.conn = conn;
    }

    public CostEaseAccDao(){
        super();
    }

    /**
     * 함수명 : getAccRequestList
     * 설명 : 요청서 간이산출 해야 할 List 호출
     * @param String group_no, String pkcr_group, String rev_no, String data_type
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 19.
     */
    public ArrayList getAccRequestList(String pk_cr_group_1, String k, String rev_no) throws Exception{
        ArrayList<Hashtable<String, String>> accRequestList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accRequestHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, ket_cost, target_cost FROM cost_request A, cost_productInfo B \n");
        sb.append("     WHERE A.fk_pid = B.pk_pid AND B.pk_cr_group = ? AND group_no = ?, AND data_type = 'main' AND rev_no = ? ORDER BY group_no ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, pk_cr_group_1);
            pstmt.setString(2, "T"+k);
            pstmt.setInt(3, Integer.parseInt(rev_no));
            rs = pstmt.executeQuery();
            while(rs.next()){
                accRequestHash = new Hashtable<String, String>();
                accRequestHash.put("su_year_1",StringUtil.checkNull(rs.getString("su_year_1")));
                accRequestHash.put("su_year_2",StringUtil.checkNull(rs.getString("su_year_2")));
                accRequestHash.put("su_year_3",StringUtil.checkNull(rs.getString("su_year_3")));
                accRequestHash.put("su_year_4",StringUtil.checkNull(rs.getString("su_year_4")));
                accRequestHash.put("su_year_5",StringUtil.checkNull(rs.getString("su_year_5")));
                accRequestHash.put("su_year_6",StringUtil.checkNull(rs.getString("su_year_6")));
                accRequestHash.put("su_year_7",StringUtil.checkNull(rs.getString("su_year_7")));
                accRequestHash.put("su_year_8",StringUtil.checkNull(rs.getString("su_year_8")));
                accRequestHash.put("ket_cost",StringUtil.checkNull(rs.getString("ket_cost")));
                accRequestHash.put("target_cost",StringUtil.checkNull(rs.getString("target_cost")));

                accRequestList.add(accRequestHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return accRequestList;
    }
}