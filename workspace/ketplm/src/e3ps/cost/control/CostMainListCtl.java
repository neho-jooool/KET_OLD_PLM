package e3ps.cost.control;

import java.sql.Connection;
import java.util.ArrayList;

import e3ps.cost.dao.CostMainListDao;
import e3ps.cost.util.DBUtil;

public class CostMainListCtl {
    /**
     * 함수명 : searchMainList 설명 : 개발원가 MainList 조회
     * 
     * @param String
     *            select_name
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 황정태 작성일자 : 2012. 08. 13.
     */

    public ArrayList searchMainList(String select_name, String auth, String dept_no, String position, String group_m, String team,
	    String Ename, String select_team, String pjt_name, String pjt_no, String f_name, String a_name, String customer_F,
	    String car_type, String search_state) {
	// connection
	Connection conn = null;

	ArrayList searchMainList = new ArrayList();
	try {
	    // connection creation
	    conn = DBUtil.getConnection();
	    CostMainListDao MainDao = new CostMainListDao(conn);

	    // result
	    searchMainList = MainDao.getsearchMainList(select_name, auth, dept_no, position, group_m, team, Ename, select_team, pjt_name,
		    pjt_no, f_name, a_name, customer_F, car_type, search_state);
	} catch (Exception e) {
	    e.printStackTrace();
	    // }finally{
	    // DBUtil.close(conn);
	}

	return searchMainList;
    }

    public ArrayList searchDetailMainList(String select_name, String select_team, String pjt_name, String pjt_no, String f_name,
	    String a_name, String customer_F, String car_type) {
	// connection
	Connection conn = null;

	ArrayList searchMainList = new ArrayList();
	try {
	    // connection creation
	    conn = DBUtil.getConnection();
	    CostMainListDao MainDao = new CostMainListDao(conn);

	    // result
	    searchMainList = MainDao.getsearchDetailMainList(select_name, select_team, pjt_name, pjt_no, f_name, a_name, customer_F,
		    car_type);

	} catch (Exception e) {
	    e.printStackTrace();
	    // }finally{
	    // DBUtil.close(conn);
	}

	return searchMainList;
    }
}
