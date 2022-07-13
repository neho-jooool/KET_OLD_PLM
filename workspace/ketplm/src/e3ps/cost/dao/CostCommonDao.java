package e3ps.cost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import e3ps.cost.util.StringUtil;

public class CostCommonDao {
    private Connection conn;

    public CostCommonDao(Connection conn) {
	this.conn = conn;
    }

    public CostCommonDao() {
	super();
    }

    /**
     * 함수명 : getDeptName 설명 : 부서명 조회
     * 
     * @param
     * @return ArrayList
     * @throws Exception
     * @throws - 작성자 : 엄태훈 작성일자 : 2015. 03. 26.
     */
    public String getDeptName(String code) throws Exception {
	String deptName = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT name FROM department WHERE code = ? ");
	try {
	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.setString(1, code);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		deptName = StringUtil.checkNull(rs.getString("name"));
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
	}
	return deptName;
    }
}
