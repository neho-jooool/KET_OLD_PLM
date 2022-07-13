package e3ps.cost.control;

import java.sql.Connection;
import java.util.ArrayList;

import e3ps.cost.util.DBUtil;
import e3ps.cost.dao.CostInvestDao;

public class CostInvestCtl {
    /**
     * 함수명 : costInsertItem
     * 설명 : 투자비 내역 등록
     * @param ArrayList
     * @return integer
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2013. 06. 20.
     */

    public int costInsertItem(ArrayList insertItemList){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();

            CostInvestDao investDao = new CostInvestDao(conn);
            // result
            complet = investDao.setInsertInvest(insertItemList);
            //complet = investDao.max();
            //System.out.println(complet);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : costInvestList
     * 설명 : 투자비 내역 조회
     * @param String report_pk
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2013. 06. 28.
     */

    public ArrayList costInvestList(String report_pk, String gubun){
        //connection
        Connection conn = null;

        ArrayList investList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostInvestDao investDao = new CostInvestDao(conn);

            // result
            investList = investDao.getCostInvest(report_pk, gubun);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return investList;
    }

    /**
     * 함수명 : costFullList
     * 설명 : 투자비 내역 조회
     * @param String report_pk
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2013. 06. 28.
     */

    public ArrayList costFullList(String report_pk){
        //connection
        Connection conn = null;

        ArrayList investList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostInvestDao investDao = new CostInvestDao(conn);

            // result
            investList = investDao.getCostInvestFull(report_pk);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return investList;
    }

    /**
     * 함수명 : deleteInvest
     * 설명 : 투자비 내역 수정 시 삭제
     * @param ArrayList
     * @return integer
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2013. 06. 20.
     */

    public int deleteInvest(String report_pk){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostInvestDao investDao = new CostInvestDao(conn);
            // result
            complet = investDao.setDeleteInvest(report_pk);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return complet;
    }
}