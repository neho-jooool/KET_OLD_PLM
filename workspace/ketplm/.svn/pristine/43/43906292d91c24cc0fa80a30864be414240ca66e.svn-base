package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.cost.util.StringUtil;

public class CostLoginDao {

    private Connection conn;

    public CostLoginDao(Connection conn) {
	this.conn = conn;
    }

    public CostLoginDao() {
	super();
    }

    /**
     * 함수명 : getLoginBool 설명 : EP 계정 확인
     * 
     * @param String
     *            userID, String password
     * @return boolean
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 05. 24.
     */
    public boolean getLoginBool(String userID, String password) throws Exception {
	boolean isLogin = false;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT A.name as name, B.auth as auth FROM ghost_ket.CDUser A, ghost_ket.auth_cost B ");
	sb.append(" WHERE A.AccountAlias = ? AND A.Account = B.empno ");
	/*
	 * if (!"neho".equals(userID)) { sb.append("   AND master.dbo.xdbdec('normal',A.CustomField5) = ?"); }
	 */

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, userID);
	    /*
	     * if (!"neho".equals(userID)) { pstmt.setString(2, password); }
	     */
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		isLogin = true;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return isLogin;
    }

    /**
     * 함수명 : getCostAuth 설명 : 계발원가 권한
     * 
     * @param String
     *            userID
     * @return String
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 05. 25.
     */
    public String getCostAuth(String userID) throws Exception {
	String auth = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT A.auth FROM auth_cost A, ghost_ket.CDUser B WHERE A.empno = B.Account AND B.AccountAlias = ? ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, userID);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		auth = rs.getString("auth");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return auth;
    }

    /**
     * 함수명 : getUserName 설명 : 계발원가 사용자 이름
     * 
     * @param String
     *            userID (사번)
     * @return String
     * @throws Exception
     * @throws 작성자
     *             : 엄태훈 작성일자 : 2012. 05. 30.
     */
    public String getUserName(String userID) throws Exception {
	String name = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT B.name FROM auth_cost A, ghost_ket.CDUser B WHERE A.empno = B.Account AND B.AccountAlias = ? ");

	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, userID);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		name = rs.getString("name");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return name;
    }

    /**
     * 함수명 : getUserInfo 설명 : 개발원가 사용자 세션 정보
     * 
     * @param String
     *            userID (사번)
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 08. 13.
     */
    public ArrayList getUserInfo(String userID) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	ArrayList UserInfoList = new ArrayList();
	Hashtable<String, String> UserInfoMap = null;
	UserInfoMap = new Hashtable<String, String>();
	System.out.println("COST_ID : [" + userID + "]");
	sb.append(" SELECT a.Account as emp_no, a.Name as Ename, a.GroupCode as dept_no,master.dbo.xdbdec('normal',a.CustomField5) as K_pass,B.Name as Dname,a.position, ");
	sb.append(" (select group_m from ghost_ket.auth_cost where empno = a.Account) as group_m                                             ");
	sb.append("   FROM ghost_ket.CDUser a,ghost_ket.CDGroup b                                                                            ");
	sb.append("  WHERE A.GroupCode    = B.GroupCode                                                                                         ");
	sb.append("    AND A.AccountAlias  = REPLACE(?,' ','')                                                                                              ");
	sb.append("    AND A.CustomField1 = 'Y'                                                                                           ");
	String emp_no = "";
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, userID);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		emp_no = StringUtil.checkNull(rs.getString("emp_no"));
		UserInfoMap.put("emp_no", emp_no);
		UserInfoMap.put("Ename", StringUtil.checkNull(rs.getString("Ename")));
		UserInfoMap.put("dept_no", StringUtil.checkNull(rs.getString("dept_no")));
		UserInfoMap.put("K_pass", StringUtil.checkNull(rs.getString("K_pass")));
		UserInfoMap.put("Dname", StringUtil.checkNull(rs.getString("Dname")));
		if (emp_no.equals("20080083")) {
		    UserInfoMap.put("position", "연구원장");
		} else {
		    UserInfoMap.put("position", StringUtil.checkNull(rs.getString("position")));
		}
		// UserInfoMap.put("position", StringUtil.checkNull(rs.getString("position")));
		UserInfoMap.put("group_m", StringUtil.checkNull(rs.getString("group_m")));
		UserInfoList.add(UserInfoMap);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    sb.delete(0, sb.length());
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (conn != null) {
		conn.close();
	    }
	}
	return UserInfoList;
    }
}
