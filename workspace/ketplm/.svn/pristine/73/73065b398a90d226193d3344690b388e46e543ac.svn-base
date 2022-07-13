package e3ps.cost.control;

import java.sql.Connection;
import java.sql.SQLException;

import e3ps.cost.dao.CostCommonDao;
import e3ps.cost.util.DBUtil;

public class CostCommonCtl {
    /**
     * 함수명 : deptName 설명 : 부서명 조회
     * 
     * @param ArrayList
     * @return integer
     * @throws SQLException
     * @throws Exception
     * @throws 작성자
     * 
     *             : 엄태훈 작성일자 : 2015. 03. 26.
     */

    public String deptName(String code) {
	// connection
	Connection conn = null;

	String deptName = "";
	try {
	    // connection creation
	    conn = DBUtil.getConnection();

	    CostCommonDao commonDao = new CostCommonDao(conn);
	    // result
	    deptName = commonDao.getDeptName(code);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(conn);
	}
	return deptName;
    }
}
