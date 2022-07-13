package e3ps.cost.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import e3ps.cost.util.StringUtil;

public class CostWorkPassDao {

    private Connection conn;

    public CostWorkPassDao(Connection conn){
        this.conn = conn;
    }

    public CostWorkPassDao(){
        super();
    }

    /**
     * 함수명 : getWorkSearchList
     * 설명 : 완료로 넘기기  cost_work Search List 호출
     * @param String pkcr_group, String rev_no
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 02.
     */
    public ArrayList getWorkSearchList(String pk_cr_group, String rev_no) throws Exception{
        ArrayList<Hashtable<String, String>> workList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> workHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT pk_cw, pk_cr_group, dev_step, pjt_name, pjt_no, team, f_name, a_name, w_name FROM cost_work A, cost_productInfo B \n");
        sb.append("     WHERE B.pk_pid = A.fk_pid AND B.pk_cr_group = ? AND B.group_no = 'T001' AND rev_no = ? ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, pk_cr_group);
            pstmt.setInt(2, Integer.parseInt(rev_no));
            rs = pstmt.executeQuery();
            while(rs.next()){
                workHash = new Hashtable<String, String>();
                workHash.put("pk_cw",StringUtil.checkNull(rs.getString("pk_cw"))); // 산출작업 DB PK 1안
                workHash.put("pk_cr_group",StringUtil.checkNull(rs.getString("pk_cr_group"))); // 요청서 그룹번호
                workHash.put("dev_step",StringUtil.checkNull(rs.getString("dev_step"))); // 개발단계
                workHash.put("pjt_name",StringUtil.checkNull(rs.getString("pjt_name"))); // 프로젝트 명
                workHash.put("pjt_no",StringUtil.checkNull(rs.getString("pjt_no"))); // 프로젝트 번호
                workHash.put("team",StringUtil.checkNull(rs.getString("team"))); // 개발담당팀
                workHash.put("f_name",StringUtil.checkNull(rs.getString("f_name"))); // 개발담당자
                workHash.put("a_name",StringUtil.checkNull(rs.getString("a_name"))); // 영업담당자
                workHash.put("w_name",StringUtil.checkNull(rs.getString("w_name"))); // 산출자
                workList.add(workHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return workList;
    }

    /**
     * 함수명 : getWorkSearchList
     * 설명 : 완료로 넘기기  cost_report Search List 호출
     * @param String pkcr_group, String rev_no
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 07. 02.
     */
    public ArrayList getReportSearchList(String pk_cr_group, String rev_no) throws Exception{
        ArrayList<Hashtable<String, String>> reportList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> reportHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT fk_cost_work_1, pk_cr_group, fk_cost_request, dev_step, pjt_name, pjt_no, team, C.f_name, a_name, w_name, file_1, C.f_day \n");
        sb.append("     FROM cost_report A, cost_productInfo B, WfInfo C \n");
        sb.append("     WHERE B.pk_pid = A.fk_pid AND A.fk_wid = C.pk_wid ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, pk_cr_group);
            pstmt.setInt(2, Integer.parseInt(rev_no));
            rs = pstmt.executeQuery();
            while(rs.next()){
                reportHash = new Hashtable<String, String>();
                reportHash.put("fk_cost_work_1",StringUtil.checkNull(rs.getString("fk_cost_work_1"))); // 산출작업DB PK 1안
                reportHash.put("pk_cr_group",StringUtil.checkNull(rs.getString("pk_cr_group"))); // 요청서 그룹번호
                reportHash.put("fk_cost_request",StringUtil.checkNull(rs.getString("fk_cost_request"))); // 요청서 리스트
                reportHash.put("dev_step",StringUtil.checkNull(rs.getString("dev_step"))); // 개발단계
                reportHash.put("pjt_name",StringUtil.checkNull(rs.getString("pjt_name"))); // 프로젝트 명
                reportHash.put("pjt_no",StringUtil.checkNull(rs.getString("pjt_no"))); // 프로젝트 번호
                reportHash.put("team",StringUtil.checkNull(rs.getString("team"))); // 개발담당팀
                reportHash.put("f_name",StringUtil.checkNull(rs.getString("f_name"))); // 개발담당자
                reportHash.put("a_name",StringUtil.checkNull(rs.getString("a_name"))); // 영업담당자
                reportHash.put("w_name",StringUtil.checkNull(rs.getString("w_name"))); // 산출자
                reportHash.put("file_1",StringUtil.checkNull(rs.getString("file_1"))); // 파일명
                reportHash.put("f_day",StringUtil.checkNull(rs.getString("f_day"))); // 개발담당결재일
                reportHash.put("step_no","6");
                reportHash.put("approval","approval");
                reportList.add(reportHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return reportList;
    }
}