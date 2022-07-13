package e3ps.cost.control;

import java.sql.Connection;
import java.util.ArrayList;

import e3ps.cost.dao.CostWorkDao;
import e3ps.cost.util.DBUtil;

public class NewCaseRequestCtl {
    /**
     * 함수명 : caseRequestList
     * 설명 : 개발원가 caseRequestList 호출
     * @param String pk_cr_group
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 25.
     */
    public ArrayList caseRequestList(String pk_cr_group, String rev_no){
        //connection
        Connection conn = null;

        ArrayList caseRequestList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);

            // result
            caseRequestList = workDao.getCaseRequestList(pk_cr_group , rev_no);

        }catch(Exception e){
            e.printStackTrace();
		}finally{
			DBUtil.close(conn);
        }

        return caseRequestList;
    }

    /**
     * 함수명 : caseEarnRateList
     * 설명 : 개발원가 caseEarnRateList 호출
     * @param String pk_cr_group
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 25.
     */
    public ArrayList caseEarnRateList(String pk_cr_group, String rev_no, String group_no){
        //connection
        Connection conn = null;

        ArrayList caseRequestList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostWorkDao workDao = new CostWorkDao(conn);

            // result
            caseRequestList = workDao.getCaseEarnRateList(pk_cr_group, rev_no, group_no);

        }catch(Exception e){
            e.printStackTrace();
		}finally{
			DBUtil.close(conn);
        }

        return caseRequestList;
    }
}