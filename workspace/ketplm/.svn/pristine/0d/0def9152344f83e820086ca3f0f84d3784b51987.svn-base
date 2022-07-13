package e3ps.cost.control;

import java.sql.Connection;
import java.util.ArrayList;

import e3ps.cost.util.DBUtil;
import e3ps.cost.dao.CostAuthDao;

public class CostWorkPassCtl {
    /**
     * 함수명 : epUserList
     * 설명 : EP 계정 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 03.
     */

    public ArrayList epUserList(){
        //connection
        Connection conn = null;

        ArrayList epList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            epList = authDao.getEPUser();

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return epList;
    }
}