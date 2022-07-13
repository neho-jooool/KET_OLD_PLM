package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
import e3ps.common.query.LoggableStatement;

public class CostAuthDao {

    private Connection conn;

    public CostAuthDao(Connection conn){
        this.conn = conn;
    }

    public CostAuthDao(){
        super();
    }

    /**
     * 함수명 : getEPUser
     * 설명 : EP 사용자 호출
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 03.
     */
    public ArrayList getEPUser() throws Exception{
        ArrayList<Hashtable<String, String>> epList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> epHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT A.Account as Account, A.Name as Name, master.dbo.xdbdec('normal',A.Email) as Email, A.GroupName as GroupName, ");
        sb.append(" CASE ");
        sb.append("   WHEN A.PositionCode = 'J10' THEN A.Position ");
        sb.append("   WHEN B.group_m = 'G' THEN A.Title+'(그룹장)' ");
        sb.append("   ELSE A.Title ");
        sb.append(" END Position, ");
        sb.append(" CASE B.auth");
        sb.append("   WHEN 'D' THEN '개별' ");
        sb.append("   WHEN 'L' THEN '팀별' ");
        sb.append("   WHEN 'S' THEN '전체' ");
        sb.append("   WHEN 'A' THEN '관리자' ");
        sb.append("   ELSE '' ");
        sb.append(" END auth ");
        sb.append(" FROM ghost_ket.CDUser A, ghost_ket.auth_cost B ");
        sb.append(" WHERE A.FlagAccountLock = 0 AND A.Account = B.empno ");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                epHash = new Hashtable<String, String>();
                epHash.put("account", StringUtil.checkNull(rs.getString("Account")));
                epHash.put("name", StringUtil.checkNull(rs.getString("Name")));
                epHash.put("email", StringUtil.checkNull(rs.getString("Email")));
                epHash.put("groupName", StringUtil.checkNull(rs.getString("GroupName")));
                epHash.put("position", StringUtil.checkNull(rs.getString("Position")));
                epHash.put("auth", StringUtil.checkNull(rs.getString("auth")));
                epList.add(epHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return epList;
    }

    /**
     * 함수명 : getSearchEPUser
     * 설명 :  EP 검색 계정 호출
     * @param String userName
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 07.
     */
    public ArrayList getSearchEPUser(String userName) throws Exception{
        ArrayList<Hashtable<String, String>> epList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> epHash = null;

        LoggableStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT A.Account as Account, A.Name as Name, master.dbo.xdbdec('normal',A.Email)  as Email, A.GroupName as GroupName,");
        sb.append(" CASE ");
        sb.append("   WHEN A.PositionCode = 'J10' THEN A.Position ");
        sb.append("   WHEN B.group_m = 'G' THEN A.Title+'(그룹장)' ");
        sb.append("   ELSE A.Title ");
        sb.append(" END Position,");
        sb.append(" CASE B.auth");
        sb.append("   WHEN 'D' THEN '개별' ");
        sb.append("   WHEN 'L' THEN '팀별' ");
        sb.append("   WHEN 'S' THEN '전체' ");
        sb.append("   WHEN 'A' THEN '관리자' ");
        sb.append("   ELSE '' ");
        sb.append(" END auth ");
        sb.append(" FROM ghost_ket.CDUser A, ghost_ket.auth_cost B ");
        sb.append(" WHERE A.FlagAccountLock = 0 AND A.Account = B.empno ");
        if(!userName.equals("")){
            sb.append(" AND A.Name = ? ");
        }

        try{
            pstmt = new LoggableStatement( conn, sb.toString() );

            if(!userName.equals("")){
                pstmt.setString(1, userName);
            }

            rs = pstmt.executeQuery();

            while(rs.next()){
                epHash = new Hashtable<String, String>();
                epHash.put("account", StringUtil.checkNull(rs.getString("Account")));
                epHash.put("name", StringUtil.checkNull(rs.getString("Name")));
                epHash.put("email", StringUtil.checkNull(rs.getString("Email")));
                epHash.put("groupName", StringUtil.checkNull(rs.getString("GroupName")));
                epHash.put("position", StringUtil.checkNull(rs.getString("Position")));
                epHash.put("auth", StringUtil.checkNull(rs.getString("auth")));
                epList.add(epHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return epList;
    }

    /**
     * 함수명 : getModifyAuthUser
     * 설명 :  개발원가 수정 사용자 조회
     * @param String empno
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 08.
     */
    public ArrayList getModifyAuthUser(String empno) throws Exception{
        ArrayList<Hashtable<String, String>> epList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> epHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT A.Account as Account, A.Name as Name, master.dbo.xdbdec('normal',A.Email)  as Email, A.GroupName as GroupName, ");
        sb.append(" CASE ");
        sb.append("   WHEN A.PositionCode = 'J10' THEN A.Position ");
        sb.append("   WHEN B.group_m = 'G' THEN A.Title+'(그룹장)' ");
        sb.append("   ELSE A.Title ");
        sb.append(" END Position, ");
        sb.append(" B.auth as auth, B.group_m as group_m ");
        sb.append(" FROM ghost_ket.CDUser A, ghost_ket.auth_cost B ");
        sb.append(" WHERE A.FlagAccountLock = 0 AND A.Account = B.empno AND A.account = ? ");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, empno);
            rs = pstmt.executeQuery();

            while(rs.next()){
                epHash = new Hashtable<String, String>();
                epHash.put("account", StringUtil.checkNull(rs.getString("Account")));
                epHash.put("name", StringUtil.checkNull(rs.getString("Name")));
                epHash.put("email", StringUtil.checkNull(rs.getString("Email")));
                epHash.put("groupName", StringUtil.checkNull(rs.getString("GroupName")));
                epHash.put("position", StringUtil.checkNull(rs.getString("Position")));
                epHash.put("auth", StringUtil.checkNull(rs.getString("auth")));
                epHash.put("groupMaster", StringUtil.checkNull(rs.getString("group_m")));
                epList.add(epHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return epList;
    }

    /**
     * 함수명 : setModifyAuth
     * 설명 :  개발원가 권한 수정
     * @param String empno, String auth, String group
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */
    public int setModifyAuth(String empno, String auth, String group) throws Exception{
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int complet = 0;
        sb.append(" UPDATE ghost_ket.auth_cost SET auth = ?, group_m = ? WHERE empno = ? ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, auth);
            pstmt.setString(2, group);
            pstmt.setString(3, empno);
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
     * 함수명 : getSearchEPuser
     * 설명 :  EP 사용자 조회
     * @param String empno
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */
    public ArrayList getSearchEPuser(String empno) throws Exception{
        ArrayList<Hashtable<String, String>> epList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> epHash = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT Account, Name FROM ghost_ket.CDUser WHERE account = ? AND FlagAccountLock = 0 ");

        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, empno);
            rs = pstmt.executeQuery();

            while(rs.next()){
                epHash = new Hashtable<String, String>();
                epHash.put("account", StringUtil.checkNull(rs.getString("Account")));
                epHash.put("name", StringUtil.checkNull(rs.getString("Name")));
                epList.add(epHash);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(rs!=null) {rs.close();}
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return epList;
    }

    /**
     * 함수명 : getSearchAuthUser
     * 설명 :  auth 사용자 조회
     * @param String empno
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */
    public int getSearchAuthUser(String empno) throws Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        int count = 0;
        sb.append(" SELECT count(*) as cnt FROM ghost_ket.auth_cost WHERE empno = ? ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, empno);
            rs = pstmt.executeQuery();

            while(rs.next()){
                count = rs.getInt("cnt");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sb.delete( 0 , sb.length() );
            if(pstmt!=null) {pstmt.close();}
            if(conn!=null) {conn.close();}
        }
        return count;
    }

    /**
     * 함수명 : setInsertAuth
     * 설명 :  개발원가 권한 수정
     * @param String empno, String auth, String group
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */
    public int setInsertAuth(String empno, String name, String auth, String group) throws Exception{
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int complet = 0;
        sb.append(" INSERT INTO ghost_ket.auth_cost (empno, name, auth, group_m) ");
        sb.append("     VALUES (?, (select name from ghost_ket.CDUser where account = ?), ?, ?) ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, empno);
            pstmt.setString(2, empno);
            pstmt.setString(3, auth);
            pstmt.setString(4, group);
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
     * 함수명 : setModifyAuth
     * 설명 :  개발원가 권한 수정
     * @param String empno, String auth, String group
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */
    public int setDeleteAuth(String empno) throws Exception{
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        int complet = 0;
        sb.append(" DELETE FROM ghost_ket.auth_cost WHERE empno = ? ");
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, empno);
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
