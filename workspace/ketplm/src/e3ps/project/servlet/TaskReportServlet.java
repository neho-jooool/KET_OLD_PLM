/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : TaskReportServlet.java
 * 작성자 : 엄태훈
 * 작성일자 : 2011. 6. 21.
 */
package e3ps.project.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.PlmDBUtil;
//import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
//import e3ps.ews.dao.EarlyWarningDao;
import e3ps.project.dao.TaskReportDao;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : ProductReportServlet
 * 설명 : task 진행현황 서블릿
 * 작성자 : 엄태훈
 * 작성일자 : 2011. 6. 21.
 */
public class TaskReportServlet extends CommonServlet{

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String cmd = ParamUtil.getParameter(req, "cmd");


        if(cmd.equals("productSearch")){
            //project 진행현황 검색
            productSearch(req,res);
        }else if(cmd.equals("moldSearch")){
            //금형 진행현황 검색
            moldSearch(req,res);
        }else if(cmd.equals("reportList2") || cmd.equals("reportList3") ){
            //project 진행현황 Report List1
            reportList2(req,res);
        }else if(cmd.equals("reportList4")){
            //금형제작 현황 Report List4
            reportList4(req,res);
        }else if(cmd.equals("productExcel")){
            //project 진행현황 검색 Excel
            productExcel(req,res);
        }else if(cmd.equals("moldExcel")){
            //금형제작 현황 검색 Excel
            moldExcel(req,res);
        }else if(cmd.equals("reportList2Excel") || cmd.equals("reportList3Excel") ){
            //금형제작 현황 검색 Excel
            reportList2Excel(req,res);
        }else if(cmd.equals("reportList4Excel")){
            //금형제작 현황 검색 Excel
            reportList4Excel(req,res);
        }
    }

    /**
     * 함수명 : productSearch
     * 설명 : product task 진행현황 검색
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 6. 22.
     */
    private void productSearch(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList productList = taskReportDao.ViewProductProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "productList", productList);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProductTaskReport.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : moldSearch
     * 설명 : mold project 진행현황 검색
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 07. 04.
     */
    private void moldSearch(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList moldList = taskReportDao.ViewMoldProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "moldList", moldList);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/MoldTaskReport.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : reportList2
     * 설명 : project 진행현황 Report List2
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 06. 21.
     */
    private void reportList2(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList list = taskReportDao.ViewReportList2(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);
            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/TaskReportListTable.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : reportList4
     * 설명 : project 진행현황 Report List1
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 07. 05.
     */
    private void reportList4(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList list = taskReportDao.ViewReportList4(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);
            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/TaskReportListTable.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : productExcel
     * 설명 : project 진행현황 검색(엑셀)
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 06. 30.
     */
    private void productExcel(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList productList = taskReportDao.ViewProductProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "productList", productList);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProductTaskReportExcel.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : moldExcel
     * 설명 : 금형제작현황 검색(엑셀)
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 07. 06.
     */
    private void moldExcel(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList moldList = taskReportDao.ViewMoldProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "moldList", moldList);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/MoldTaskReportExcel.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : reportList2Excel
     * 설명 : project 진행현황 Report List2 Excel
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 07. 07.
     */
    private void reportList2Excel(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList list = taskReportDao.ViewReportList2(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/TaskReportListExcel.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : reportList4Excel
     * 설명 : project 진행현황 Report List1 Excel
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 07. 07.
     */
    private void reportList4Excel(HttpServletRequest req, HttpServletResponse res){



        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            TaskReportDao taskReportDao = new TaskReportDao(conn);
            //목록 결과
            ArrayList list = taskReportDao.ViewReportList4(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/TaskMoldReportListExcel.jsp");



        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }


}
