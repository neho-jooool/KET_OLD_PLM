package e3ps.cost.control;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.PlmDBUtil;
import e3ps.cost.util.DBUtil;
import e3ps.cost.dao.CostReportDao;

public class CostReportCtl {
    /**
     * 함수명 : workList3
     * 설명 : 개발원가 결과 보고의 원가계산 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 08. 24.
     */

    public ArrayList workList3(String t_chk_list){
        //connection
        Connection conn = null;

        ArrayList workList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            workList = reportDao.getWorkList3(t_chk_list);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return workList;
    }

    /**
     * 함수명 : suStanDay
     * 설명 : 보고서의 수지재료단가 호출
     * @param String met_type
     * @return String
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 08. 29.
     */

    public String suStanDay(String m_maker, String grade_name, String grade_color){
        //connection
        Connection conn = null;

        String suStanDay = "";
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            suStanDay = reportDao.getSuStanDay(m_maker, grade_name, grade_color);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return suStanDay;
    }

    /**
     * 함수명 : reportNoteList
     * 설명 : 보고서의 원가세부내역 note1~5등의 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 4.
     */

    public ArrayList reportNoteList(String report_pk){
        //connection
        Connection conn = null;

        ArrayList reportNoteList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportNoteList = reportDao.getReportNoteList(report_pk);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportNoteList;
    }

    /**
     * 함수명 : reportPkcrRevNoList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 4.
     */

    public ArrayList reportPkcrRevNoList(String pk_cr_group, String rev_no){
        //connection
        Connection conn = null;

        ArrayList reportPkcrRevNoList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportPkcrRevNoList = reportDao.getReportPkcrRevNoList(pk_cr_group, rev_no);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportPkcrRevNoList;
    }

    /**
     * 함수명 : reportPkcrRevNoList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 4.
     */

    public ArrayList reportSuYearList(String pk_cw){
        //connection
        Connection conn = null;

        ArrayList reportSuYearList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportSuYearList = reportDao.getReportSuYearList(pk_cw);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportSuYearList;
    }

    /**
     * 함수명 : reportCaseTypeAdminList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 5.
     */

    public ArrayList reportCaseTypeAdminList(String pk_cw, String pk_cr_group, String rev_no, String group_no, String case_type_admin_t, String k){
        //connection
        Connection conn = null;

        ArrayList reportCaseTypeAdminList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCaseTypeAdminList = reportDao.getReportCaseTypeAdminList(pk_cw, pk_cr_group, rev_no, group_no, case_type_admin_t, k);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCaseTypeAdminList;
    }

    /**
     * 함수명 : reportPkcwGroupNoList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 5.
     */

    public ArrayList reportPkcwGroupNoList(String pk_cr_group, String k, String group_no, String case_type_admin_t){
        //connection
        Connection conn = null;

        ArrayList reportPkcwGroupNoList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportPkcwGroupNoList = reportDao.getReportPkcwGroupNoList(pk_cr_group, k, group_no, case_type_admin_t);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportPkcwGroupNoList;
    }

    /**
     * 함수명 : reportCostWorkList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 5.
     */

    public ArrayList reportCostWorkList(String pk_cw){
        //connection
        Connection conn = null;

        ArrayList reportCostWorkList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostWorkList = reportDao.getReportCostWorkList(pk_cw);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostWorkList;
    }

    /**
     * 함수명 : reportCostWorkCaseCountList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 7.
     */

    public ArrayList reportCostWorkCaseCountList(String pk_cr_group, String rev_no){
        //connection
        Connection conn = null;

        ArrayList reportCostWorkCaseCountList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostWorkCaseCountList = reportDao.getReportCostWorkCaseCountList(pk_cr_group, rev_no);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostWorkCaseCountList;
    }

    /**
     * 함수명 : reportCostWorkMoreList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 10.
     */

    public ArrayList reportCostWorkMoreList(String pk_cw){
        //connection
        Connection conn = null;

        ArrayList reportCostWorkMoreList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostWorkMoreList = reportDao.getReportCostWorkMoreList(pk_cw);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostWorkMoreList;
    }

    /**
     * 함수명 : reportCostWorkAllList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 10.
     */

    public ArrayList reportCostWorkAllList(String pk_cr_group, String k, String rev_no){
        //connection
        Connection conn = null;

        ArrayList reportCostWorkAllList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostWorkAllList = reportDao.getReportCostWorkAllList(pk_cr_group, k, rev_no);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostWorkAllList;
    }

    /**
     * 함수명 : reportCostWorkAllList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 10.
     */

    public ArrayList reportCostReportAllList(String report_pk){
        //connection
        Connection conn = null;

        ArrayList reportCostReportAllList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostReportAllList = reportDao.getReportCostReportAllList(report_pk);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostReportAllList;
    }

    /**
     * 함수명 : reportFKCostWorkList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 10.
     */

    public ArrayList reportFKCostWorkList(String report_pk){
        //connection
        Connection conn = null;

        ArrayList reportFKCostWorkList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportFKCostWorkList = reportDao.getReportFKCostWorkList(report_pk);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
        	DBUtil.close(conn);
        }

        return reportFKCostWorkList;
    }

    /**
     * 함수명 : reportCostWorkGroupNoList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 11.
     */

    public ArrayList reportCostWorkGroupNoList(String pk_cw,String rev_no){
        //connection
        Connection conn = null;

        ArrayList reportCostWorkGroupNoList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostWorkGroupNoList = reportDao.getReportCostWorkGroupNoList(pk_cw,rev_no);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostWorkGroupNoList;
    }

    /**
     * 함수명 : dbCostWorkList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 11.
     */

    public ArrayList dbCostWorkList(String pk_cw){
        //connection
        Connection conn = null;

        ArrayList dbCostWorkList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            dbCostWorkList = reportDao.getDbCostWorkList(pk_cw);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return dbCostWorkList;
    }

    /**
     * 함수명 : dbCostWorkUpdate
     * 설명 : 보고서의 원가세부내역 데이터 UPDATE
     * @param ArrayList
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 17.
     */

    public int dbCostWorkUpdate(ArrayList insertItemList){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);
            // result
            complet = reportDao.setdbCostWorkUpdate(insertItemList);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : dbCostWorkUpdate
     * 설명 : 보고서의 원가세부내역 데이터 UPDATE
     * @param ArrayList
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 17.
     */

    public int selectCostUpdate(String pk_cr_group, String rev_no){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);
            // result
            complet = reportDao.setSelectCostUpdate(pk_cr_group, rev_no);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : dbCostWorkInsert
     * 설명 : 보고서의 원가세부내역 데이터 INSERT
     * @param ArrayList
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 17.
     */

    public ArrayList dbCostWorkInsert(ArrayList insertItemList){
        System.out.println("여기옵니?");
        //connection
        Connection conn = null;
        ArrayList complet = new ArrayList();
        //int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);
            // result
            complet = reportDao.setdbCostWorkInsert(insertItemList);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : dbCostWorkUpdate
     * 설명 : 보고서의 원가세부내역 데이터 UPDATE
     * @param ArrayList
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 17.
     */

    public int updateFPkCrp(String fPKCrp, String fPKCrp0){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);
            // result
            complet = reportDao.setFPkCrpUpdate(fPKCrp, fPKCrp0);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : selectCostUpdate1
     * 설명 : 보고서의 원가세부내역 데이터 UPDATE
     * @param ArrayList
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 19.
     */

    public int selectCostUpdate1(String pk_cr_group, String rev_no, String fPkCrp){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);
            // result
            complet = reportDao.setSelectCostUpdate1(pk_cr_group, rev_no, fPkCrp);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : reportNote5Update
     * 설명 : 보고서의 원가세부내역 데이터 UPDATE
     * @param ArrayList
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 19.
     */

    public int reportNote5Update(String report_pk, String note_5, String note_5_1){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);
            // result
            complet = reportDao.setReportNote5Update(report_pk, note_5, note_5_1);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : costWorkSelectCostUpdate
     * 설명 : 보고서의 원가세부내역 데이터 UPDATE
     * @param ArrayList
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 19.
     */

    public int costWorkSelectCostUpdate(String pk_cw){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);
            // result
            complet = reportDao.setCostWorkSelectCostUpdate(pk_cw);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : reportCostWorkGroupNoList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 11.
     */

    public ArrayList costWorkReportViewList(String report_pk){
        //connection
        Connection conn = null;

        ArrayList reportCostWorkGroupNoList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostWorkGroupNoList = reportDao.getCostWorkReportViewList(report_pk);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostWorkGroupNoList;
    }

    /**
     * 함수명 : ketCostList
     * 설명 : 보고서의 원가세부내역 데이터 결과 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 9. 11.
     */

    public String ketCostList(String pk_cw){
        //connection
        Connection conn = null;

        String complet = "";
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            complet = reportDao.getKetCostList(pk_cw);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : getreportCostWorkPk
     * 설명 : 보고서의 최초작성시 costwork의 pk찾기
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 황정태
     * 작성일자 : 2012. 11. 27.
     */
    public String getreportCostWorkPk(String pk_cr_group){
        //connection
        Connection conn = null;

        String pk_cw = "";
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            pk_cw = reportDao.getreportCostWorkPk(pk_cr_group);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return pk_cw;
    }

    /**
     * 함수명 : searchCostWork
     * 설명 : 고서 작성시 선택안에 따른 매출액,영업이익,영업이익율 계산값 가져오기
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 황정태
     * 작성일자 : 2013. 10. 18.
     */

    public ArrayList searchCostWork(String select_cost, String report_pk){
        //connection
        Connection conn = null;

        ArrayList reportCostWorkSelectList = new ArrayList();
        try{
            // connection creation
            conn = DBUtil.getConnection();
            CostReportDao reportDao = new CostReportDao(conn);

            // result
            reportCostWorkSelectList = reportDao.getSelectWork(select_cost,report_pk);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.close(conn);
        }

        return reportCostWorkSelectList;
    }

    public ArrayList CostReportTempSearch(Hashtable condition){
    	//커넥션
        Connection conn = null;
        ArrayList costTempList = new ArrayList();
        System.out.println("========= cmd222 ======> ");
        try {

            System.out.println("========= cmd333 ======> "+(String)condition.get("report_pk"));
            // 커넥션 생성
            conn = DBUtil.getConnection();
            CostReportDao codeDao = new CostReportDao(conn);
            //목록 결과
            costTempList = codeDao.ViewCostReportTemp(condition);
            System.out.println("========= cmd444 ======> ");

        } catch (Exception e) {
        	System.out.println("========= cmd5555 ======> ");
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
        return costTempList;
    }



}