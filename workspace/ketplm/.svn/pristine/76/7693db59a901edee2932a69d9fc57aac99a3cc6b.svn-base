package e3ps.cost.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;

public class CostAccDao {

    private Connection conn;

    public CostAccDao(Connection conn){
        this.conn = conn;
    }

    public CostAccDao(){
        super();
    }

    /**
     * 함수명 : getAccRequestList
     * 설명 : 요청서 간이산출 해야 할 List 호출
     * @param String group_no, String pkcr_group, String rev_no, String k
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 2.
     */
    public ArrayList getAccRequestList(String pk_cr_group_1, String k, String rev_no) throws Exception{
        ArrayList<Hashtable<String, String>> accRequestList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accRequestHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT su_year_1, su_year_2, su_year_3, su_year_4, su_year_5, su_year_6, su_year_7, su_year_8, ket_cost, target_cost FROM cost_request A, cost_productInfo B \n");
        sb.append("     WHERE A.fk_pid = B.pk_pid AND B.pk_cr_group = ? AND group_no = ? AND data_type = 'main' AND rev_no = ? ORDER BY group_no ");
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
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accRequestList;
    }

    /**
     * 함수명 : getAccLmeList
     * 설명 : 산출식 LME 정보 출력
     * @param String lme_ton
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 3.
     */
    public ArrayList getAccLmeList(String lme_ton) throws Exception{
        ArrayList<Hashtable<String, String>> accLmeList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accLmeHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        if("6500".equals(lme_ton)){
            sb.append(" SELECT usd_rate, yen_rate, lme_cu, lme_zn, lme_sn, lme_ni FROM lme WHERE to_char(in_date, 'YYYY') = ? AND lme_type = 'best' ORDER BY pk_lme desc ");
        }else{
            sb.append(" SELECT usd_rate, yen_rate, lme_cu, lme_zn, lme_sn, lme_ni FROM lme WHERE to_char(in_date, 'YYYY') = ? AND lme_type = 'worst' ORDER BY pk_lme desc ");
        }
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, StringUtil.thisYear());
            rs = pstmt.executeQuery();
            while(rs.next()){
                accLmeHash = new Hashtable<String, String>();
                accLmeHash.put("usd_rate",StringUtil.checkNull(rs.getString("usd_rate")));
                accLmeHash.put("yen_rate",StringUtil.checkNull(rs.getString("yen_rate")));
                accLmeHash.put("lme_cu",StringUtil.checkNull(rs.getString("lme_cu")));
                accLmeHash.put("lme_zn",StringUtil.checkNull(rs.getString("lme_zn")));
                accLmeHash.put("lme_sn",StringUtil.checkNull(rs.getString("lme_sn")));
                accLmeHash.put("lme_ni",StringUtil.checkNull(rs.getString("lme_ni")));

                accLmeList.add(accLmeHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accLmeList;
    }

    /**
     * 함수명 : getAccReerList
     * 설명 : 산출식 환율 정보 출력
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 3.
     */
    public ArrayList getAccReerList() throws Exception{
        ArrayList<Hashtable<String, String>> accReerList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accReerHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT usd_rate, yen_rate, euro_rate, cny_rate FROM reer WHERE to_char(reer_date, 'YYYY') = to_char(sysdate,'YYYY') ORDER BY pk_re desc ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            System.out.println("YEAR==>"+StringUtil.thisYear());
            //pstmt.setString(1, StringUtil.thisYear());
            rs = pstmt.executeQuery();
            while(rs.next()){
                accReerHash = new Hashtable<String, String>();
                accReerHash.put("usd_rate",StringUtil.checkNull(rs.getString("usd_rate")));
                accReerHash.put("yen_rate",StringUtil.checkNull(rs.getString("yen_rate")));
                accReerHash.put("euro_rate",StringUtil.checkNull(rs.getString("euro_rate")));
                accReerHash.put("cny_rate",StringUtil.checkNull(rs.getString("cny_rate")));
                System.out.println("yen_rate==>"+StringUtil.checkNull(rs.getString("yen_rate")));
                accReerList.add(accReerHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accReerList;
    }

    /**
     * 함수명 : getAccProcessingList
     * 설명 : 산출식 Processing 정보 출력
     * @param String met_type
     * @return String
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 3.
     */
    public String getAccProcessing(String met_type, String im) throws Exception{
        String pro_cost = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        if("no".equals(im)){
            sb.append(" SELECT pro_cost FROM processing_cost WHERE met_type = ? AND pc_cost_type = '가공비' ");
        }else if("detin".equals(im)){
            sb.append(" SELECT pro_cost FROM processing_cost WHERE met_type = 'DeTin' ");
        }else{
            sb.append(" SELECT pro_cost FROM processing_cost WHERE met_type = ? AND pc_cost_type = '임가공비' ");
        }
        try{
            pstmt = conn.prepareStatement(sb.toString());
            if(!"detin".equals(im)){
                pstmt.setString(1, met_type);
            }
            rs = pstmt.executeQuery();
            while(rs.next()){
                pro_cost = rs.getString("pro_cost");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return pro_cost;
    }

    /**
     * 함수명 : getAccPlatingList
     * 설명 : 산출식 Plating_cost 정보 출력
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 3.
     */
    public ArrayList getAccPlatingList(String met_type) throws Exception{
        ArrayList<Hashtable<String, String>> accPlatingList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String>accPlatingHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT p_size_t_1, p_size_t_2, p_size_t_3, p_size_t_4 FROM plating_cost WHERE met_type = ? ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, met_type);
            rs = pstmt.executeQuery();
            while(rs.next()){
                accPlatingHash = new Hashtable<String, String>();
                accPlatingHash.put("p_size_t_1",StringUtil.checkNull(rs.getString("p_size_t_1")));
                accPlatingHash.put("p_size_t_2",StringUtil.checkNull(rs.getString("p_size_t_2")));
                accPlatingHash.put("p_size_t_3",StringUtil.checkNull(rs.getString("p_size_t_3")));
                accPlatingHash.put("p_size_t_4",StringUtil.checkNull(rs.getString("p_size_t_4")));

                accPlatingList.add(accPlatingHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accPlatingList;
    }

    /**
     * 함수명 : getAccCuttingList
     * 설명 : 산출식 cutting_cost 정보 출력
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 3.
     */
    public ArrayList getAccCuttingList(String met_type, int met_w) throws Exception{
        ArrayList<Hashtable<String, String>> accCuttingList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String>accCuttingHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT c_size_t_1, c_size_t_2, c_size_t_3, c_size_t_4, c_size_t_5, c_size_t_6, c_size_t_7, c_size_t_8, c_size_t_9 FROM cutting_cost WHERE met_type = ? AND met_w = ? ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, met_type);
            pstmt.setInt(2, met_w);
            rs = pstmt.executeQuery();
            while(rs.next()){
                accCuttingHash = new Hashtable<String, String>();
                accCuttingHash.put("c_size_t_1",StringUtil.checkNull(rs.getString("c_size_t_1")));
                accCuttingHash.put("c_size_t_2",StringUtil.checkNull(rs.getString("c_size_t_2")));
                accCuttingHash.put("c_size_t_3",StringUtil.checkNull(rs.getString("c_size_t_3")));
                accCuttingHash.put("c_size_t_4",StringUtil.checkNull(rs.getString("c_size_t_4")));
                accCuttingHash.put("c_size_t_5",StringUtil.checkNull(rs.getString("c_size_t_5")));
                accCuttingHash.put("c_size_t_6",StringUtil.checkNull(rs.getString("c_size_t_6")));
                accCuttingHash.put("c_size_t_7",StringUtil.checkNull(rs.getString("c_size_t_7")));
                accCuttingHash.put("c_size_t_8",StringUtil.checkNull(rs.getString("c_size_t_8")));
                accCuttingHash.put("c_size_t_9",StringUtil.checkNull(rs.getString("c_size_t_9")));

                accCuttingList.add(accCuttingHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accCuttingList;
    }

    /**
     * 함수명 : getAccProcessingList
     * 설명 : 산출식 Processing 정보 출력
     * @param String met_type
     * @return String
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 3.
     */
    public String getAccMoldMaker(String m_maker, String grade_name, String grade_color) throws Exception{
        String grade_cost = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        if("".equals(grade_color)){
            sb.append(" SELECT grade_cost FROM mold_maker WHERE maker_name = ? AND grade_name = ? AND rownum = 1 ORDER BY grade_color desc ");
        }else{
            sb.append(" SELECT grade_cost FROM mold_maker WHERE maker_name = ? AND grade_name = ? AND grade_color = ? ");
        }

        try{
            pstmt = conn.prepareStatement(sb.toString());
            if("".equals(grade_color)){
                pstmt.setString(1, m_maker);
                pstmt.setString(2, grade_name);
            }else{
                pstmt.setString(1, m_maker);
                pstmt.setString(2, grade_name);
                pstmt.setString(3, grade_color);
            }
            rs = pstmt.executeQuery();
            while(rs.next()){
                grade_cost = rs.getString("grade_cost");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return grade_cost;
    }

    /**
     * 함수명 : getAccExStandardList
     * 설명 : 산출식 expcost_standard 정보 출력
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 16.
     */
    public ArrayList getAccExStandardList(String pro_1, String pro_type, String ton_value, String menual) throws Exception{
        ArrayList<Hashtable<String, String>> accExStandardList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accExStandardHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        boolean parameter_ok =false;
        if(("1".equals(menual) && ("생산1".equals(pro_1) || "생산2".equals(pro_1))) || ("2".equals(menual) && ("중국".equals(pro_1) || "생산2".equals(pro_1)))){
            parameter_ok = true;
            sb.append(" SELECT st_eff_value, st_person, st_rate, st_jun_cost, st_ma_depr, st_tabalu, st_overhead, st_ge_cost, st_jae_cost, st_rnd_cost, st_royalty \n");
            sb.append("     FROM expcost_standard where st_team = ? AND st_pro_type = ? AND st_ton =? ");
        }else{
            sb.append(" SELECT st_eff_value, st_person, st_rate, st_jun_cost, st_ma_depr, st_tabalu, st_overhead, st_ge_cost, st_jae_cost, st_rnd_cost, st_royalty \n");
            sb.append("     FROM expcost_standard where st_team = ? AND st_pro_type = ? ");
        }

        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, pro_1);
            pstmt.setString(2, pro_type);
            /*if("생산1".equals(pro_1) || "생산2".equals(pro_1)){
                pstmt.setString(3, ton_value);
            }*/
            if(parameter_ok){
                pstmt.setString(3, ton_value);
            }

            rs = pstmt.executeQuery();
            while(rs.next()){
                accExStandardHash = new Hashtable<String, String>();
                accExStandardHash.put("st_eff_value",StringUtil.checkNull(rs.getString("st_eff_value")));
                accExStandardHash.put("st_person",StringUtil.checkNull(rs.getString("st_person")));
                accExStandardHash.put("st_rate",StringUtil.checkNull(rs.getString("st_rate")));
                accExStandardHash.put("st_jun_cost",StringUtil.checkNull(rs.getString("st_jun_cost")));
                accExStandardHash.put("st_ma_depr",StringUtil.checkNull(rs.getString("st_ma_depr")));
                accExStandardHash.put("st_tabalu",StringUtil.checkNull(rs.getString("st_tabalu")));
                accExStandardHash.put("st_overhead",StringUtil.checkNull(rs.getString("st_overhead")));
                accExStandardHash.put("st_ge_cost",StringUtil.checkNull(rs.getString("st_ge_cost")));
                accExStandardHash.put("st_jae_cost",StringUtil.checkNull(rs.getString("st_jae_cost")));
                accExStandardHash.put("st_rnd_cost",StringUtil.checkNull(rs.getString("st_rnd_cost")));
                accExStandardHash.put("st_royalty",StringUtil.checkNull(rs.getString("st_royalty")));

                accExStandardList.add(accExStandardHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return accExStandardList;
    }

    /**
     * 함수명 : getAccBandolierList
     * 설명 : 산출식 bandolier_DB 정보 출력
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 16.
     */
    public ArrayList getAccBandolierList() throws Exception{
        ArrayList<Hashtable<String, String>> accBandolierList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accBandolierHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT carrier_cost, carrier_grade FROM bandolier_DB ");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                accBandolierHash = new Hashtable<String, String>();
                accBandolierHash.put("carrier_cost",StringUtil.checkNull(rs.getString("carrier_cost")));
                accBandolierHash.put("carrier_grade",StringUtil.checkNull(rs.getString("carrier_grade")));
                accBandolierList.add(accBandolierHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accBandolierList;
    }

    /**
     * 함수명 : getAccExStandardList2
     * 설명 : 산출식 expcost_standard 정보 출력
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 08. 06.
     */
    public ArrayList getAccExStandardList2(String pro_1, String method_type) throws Exception{
        ArrayList<Hashtable<String, String>> accExStandardList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accExStandardHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        System.out.println("pro_1 : "+pro_1);
        System.out.println("method_type : "+method_type);
        sb.append(" SELECT st_eff_value, st_person, st_rate, st_jun_cost, st_ma_depr, st_tabalu, st_overhead, st_ge_cost, st_jae_cost, st_rnd_cost, st_royalty \n");
        sb.append("     FROM expcost_standard where st_team = ? AND st_met = ?");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, pro_1);
            pstmt.setString(2, method_type);

            rs = pstmt.executeQuery();
            while(rs.next()){
                accExStandardHash = new Hashtable<String, String>();
                accExStandardHash.put("st_eff_value",StringUtil.checkNull(rs.getString("st_eff_value")));
                accExStandardHash.put("st_person",StringUtil.checkNull(rs.getString("st_person")));
                accExStandardHash.put("st_rate",StringUtil.checkNull(rs.getString("st_rate")));
                accExStandardHash.put("st_jun_cost",StringUtil.checkNull(rs.getString("st_jun_cost")));
                accExStandardHash.put("st_ma_depr",StringUtil.checkNull(rs.getString("st_ma_depr")));
                accExStandardHash.put("st_tabalu",StringUtil.checkNull(rs.getString("st_tabalu")));
                accExStandardHash.put("st_overhead",StringUtil.checkNull(rs.getString("st_overhead")));
                accExStandardHash.put("st_ge_cost",StringUtil.checkNull(rs.getString("st_ge_cost")));
                accExStandardHash.put("st_jae_cost",StringUtil.checkNull(rs.getString("st_jae_cost")));
                accExStandardHash.put("st_rnd_cost",StringUtil.checkNull(rs.getString("st_rnd_cost")));
                accExStandardHash.put("st_royalty",StringUtil.checkNull(rs.getString("st_royalty")));

                accExStandardList.add(accExStandardHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accExStandardList;
    }

    /**
     * 함수명 : getAccExStandardList
     * 설명 : 산출식 expcost_standard 정보 출력
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 16.
     */
    public ArrayList getAccExStandardList3(String pro_1, String pro_type, String method_type) throws Exception{
        ArrayList<Hashtable<String, String>> accExStandardList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> accExStandardHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT st_eff_value, st_person, st_rate, st_jun_cost, st_ma_depr, st_tabalu, st_overhead, st_ge_cost, st_jae_cost, st_rnd_cost, st_royalty \n");
        sb.append("     FROM expcost_standard where st_team = ? AND st_pro_type = ? AND replace(st_met,' ','') = trim(?) ");
        if(pro_type.equals("assy")){
            pro_type = "ASSY";
        }
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, pro_1);
            pstmt.setString(2, pro_type);
            pstmt.setString(3, method_type);

            rs = pstmt.executeQuery();
            while(rs.next()){
                accExStandardHash = new Hashtable<String, String>();
                accExStandardHash.put("st_eff_value",StringUtil.checkNull(rs.getString("st_eff_value")));
                accExStandardHash.put("st_person",StringUtil.checkNull(rs.getString("st_person")));
                accExStandardHash.put("st_rate",StringUtil.checkNull(rs.getString("st_rate")));
                accExStandardHash.put("st_jun_cost",StringUtil.checkNull(rs.getString("st_jun_cost")));
                accExStandardHash.put("st_ma_depr",StringUtil.checkNull(rs.getString("st_ma_depr")));
                accExStandardHash.put("st_tabalu",StringUtil.checkNull(rs.getString("st_tabalu")));
                accExStandardHash.put("st_overhead",StringUtil.checkNull(rs.getString("st_overhead")));
                accExStandardHash.put("st_ge_cost",StringUtil.checkNull(rs.getString("st_ge_cost")));
                accExStandardHash.put("st_jae_cost",StringUtil.checkNull(rs.getString("st_jae_cost")));
                accExStandardHash.put("st_rnd_cost",StringUtil.checkNull(rs.getString("st_rnd_cost")));
                accExStandardHash.put("st_royalty",StringUtil.checkNull(rs.getString("st_royalty")));

                accExStandardList.add(accExStandardHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return accExStandardList;
    }
}