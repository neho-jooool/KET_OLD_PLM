/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProductReportServlet.java
 * 작성자 : 김경희
 * 작성일자 : 2011. 5. 3.
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
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.dao.ProjectReportDao;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : ProductReportServlet
 * 설명 : project 진행현황 서블릿
 * 작성자 : 김경희
 * 작성일자 : 2011. 5. 3.
 */
public class ProjectReportServlet extends CommonServlet{

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String cmd = ParamUtil.getParameter(req, "cmd");

        if(cmd.equals("productSearch")){
            //project 진행현황 검색
            productSearch(req,res);
        }else if(cmd.equals("moldSearch")){
            //project 진행현황 검색
            moldSearch(req,res);
        }else if(cmd.equals("productExcel")){
            //project 진행현황 검색 Excel
            productExcel(req,res);
        }else if(cmd.equals("moldExcel")){
            //project 진행현황 검색 Excel
            moldExcel(req,res);
        }else if(cmd.equals("reportList1")){
            //project 진행현황 Report List1
            reportList1(req,res);
        }else if(cmd.equals("reportList1Excel")){
            //project 진행현황 Report List1 Excel
            reportList1Excel(req,res);
        }else if(cmd.equals("reportList2")){
            //project 진행현황 Report List2
            reportList2(req,res);
        }else if(cmd.equals("reportList2Excel")){
            //project 진행현황 Report List2 Excel
            reportList2Excel(req,res);
        }else if(cmd.equals("reportList3")){
            //project 진행현황 Report List3
            reportList3(req,res);
        }else if(cmd.equals("reportList3Excel")){
            //project 진행현황 Report List3 Excel
            reportList3Excel(req,res);
        }else if(cmd.equals("reportList4")){
            //금형제작 현황 Report List4
            reportList4(req,res);
        }else if(cmd.equals("reportList4Excel")){
            //금형제작 현황 Report List4 Excel
            reportList4Excel(req,res);
        }else if(cmd.equals("reportList5")){
            //금형제작 현황 Report List5
            reportList5(req,res);
        }else if(cmd.equals("reportList5Excel")){
            //금형제작 현황 Report List5 Excel
            reportList5Excel(req,res);
        }
    }

    /**
     * 함수명 : productSearch
     * 설명 : product project 진행현황 검색
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 3.
     */
    private void productSearch(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList productList = projectReportDao.ViewProductProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "productList", productList);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProductProjectReport.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 11.
     */
    private void moldSearch(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList moldList = projectReportDao.ViewMoldProjectList(hash);
            ArrayList addMoldList = projectReportDao.ViewAddMoldProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "moldList", moldList);
            req.setAttribute( "addMoldList", addMoldList);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/MoldProjectReport.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 13.
     */
    private void productExcel(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList productList = projectReportDao.ViewProductProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "productList", productList);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProductProjectReportExcel.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 13.
     */
    private void moldExcel(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList moldList = projectReportDao.ViewMoldProjectList(hash);
            ArrayList addMoldList = projectReportDao.ViewAddMoldProjectList(hash);

            //목록 결과 셋팅
            req.setAttribute( "moldList", moldList);
            req.setAttribute( "addMoldList", addMoldList);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/MoldProjectReportExcel.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 함수명 : reportList1
     * 설명 : project 진행현황 Report List1
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    private void reportList1(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList1(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);
            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListTable.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 함수명 : reportList1Excel
     * 설명 : project 진행현황 Report List1 Excel
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 18.
     */
    private void reportList1Excel(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList1(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListExcel.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    private void reportList2(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList2(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);
            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListTable.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 18.
     */
    private void reportList2Excel(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList2(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListExcel.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 함수명 : reportList3
     * 설명 : project 진행현황 Report List1
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    private void reportList3(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList3(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);
            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListTable.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 함수명 : reportList3Excel
     * 설명 : project 진행현황 Report List3 Excel
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 18.
     */
    private void reportList3Excel(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList3(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListExcel.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    private void reportList4(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList4(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);
            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListTable.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 18.
     */
    private void reportList4Excel(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList4(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListExcel.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 함수명 : reportList5
     * 설명 : project 진행현황 Report List1
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 17.
     */
    private void reportList5(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList5(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);
            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListTable.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
     * 함수명 : reportList5Excel
     * 설명 : project 진행현황 Report List5 Excel
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 김경희
     * 작성일자 : 2011. 5. 18.
     */
    private void reportList5Excel(HttpServletRequest req, HttpServletResponse res){

        Kogger.debug(getClass(), "----------------------서블릿시작");

        //커넥션
        Connection conn = null;

        try {

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            Hashtable hash = uploader.getFormParameters();

            // 커넥션 생성
            conn = PlmDBUtil.getConnection();
            ProjectReportDao projectReportDao = new ProjectReportDao(conn);
            //목록 결과
            ArrayList list = projectReportDao.ViewReportList5(hash);

            //목록 결과 셋팅
            req.setAttribute( "list", list);

            //검색 조건 셋팅
            req.setAttribute( "condition", hash);

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/project/ProjectReportListExcel.jsp");

            Kogger.debug(getClass(), "----------------------서블릿종료");

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
