/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : MoldChangePlanServlet.java
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 01.
 */
package e3ps.ecm.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.ecm.dao.MoldChangePlanDao;
import ext.ket.shared.log.Kogger;


/**
 * 클래스명 : MoldChangePlanServlet
 * 설명 : 금형부품 설계변경 검색 서블릿
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 01.
 */
public class MoldChangePlanServlet extends CommonServlet{

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = ParamUtil.getParameter(req, "cmd");
        Kogger.debug(getClass(), "=====> MoldChangePlanServlet: cmd=[" + cmd + "]");

        if ("openSearch".equals(cmd)) {
            openSearch(req, res);
        }
        else if ("search".equals(cmd)) {
            search(req, res);
        }
        else if ("excel".equals(cmd)) {
            searchExcel(req, res);
        }
        else if ("popup".equals(cmd)) {
            searchPopup(req, res);
        }

    }


    /**
     * 함수명 : search
     * 설명 : 금형부품 설계변경 검색
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 신대범
     * 작성일자 : 2010. 11. 01.
     */
    private void search(HttpServletRequest req, HttpServletResponse res){
        //Kogger.debug(getClass(), "----------------------서블릿시작");

        Connection conn = null;

        try {
            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

            conn = PlmDBUtil.getConnection();
            MoldChangePlanDao moldChangePlanDao = new MoldChangePlanDao(conn);

            // [Start] 결과 내 재검색
/*
            ArrayList moldChangeList = moldChangePlanDao.ViewMoldChangeList(paramMap); // 기존 검색 주석처리
*/
            boolean searchInResult = ( "searchInSearch".equals(paramMap.getString("searchInSearch")) );
            List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("MoldChangePlan", searchInResult, req);
            conditionList.add(paramMap);
            ArrayList moldChangeList = moldChangePlanDao.ViewMoldChangeListSIR(conditionList);
            // [End] 결과 내 재검색

            TreeGridStringBuffer strBuffer = new TreeGridStringBuffer();

            for (int i = 0; i < moldChangeList.size(); ++i) {
                Hashtable<String, String> moldChange = (Hashtable<String, String>) moldChangeList.get(i);

                strBuffer.append( "<I ");
                strBuffer.appendRepl( " NoColor=\"2\" CanDelete=\"0\"" );
                strBuffer.appendRepl( " RowNum=\"" + (i+1) + "\"" );
                strBuffer.appendRepl( " DieNo=\"" + moldChange.get("dieno") + "\"" );
                strBuffer.appendRepl( " ItemCode=\"" + moldChange.get("itemcode") + "\"" );
                strBuffer.appendRepl( " ItemName=\"").appendContent(moldChange.get("itemname")).appendRepl("\"" );
                strBuffer.appendRepl( " MoDesc=\"" + moldChange.get("modesc") + "\"" );
                strBuffer.appendRepl( " ChangeNo=\"" + moldChange.get("changeno") + "\"" );
                strBuffer.appendRepl( " ChangeDate=\"" + moldChange.get("changedate") + "\"" );
                strBuffer.append( "/>" );
            }

            req.setAttribute( "searchCondition", paramMap );    // 검색 화면에서 받은 검색조건
            req.setAttribute( "tgData", strBuffer.toString() );        // 검색 결과 데이터

            //검색화면으로 이동
            gotoResult(req, res, "/jsp/ecm/MoldChangePlan.jsp");

            //Kogger.debug(getClass(), "----------------------서블릿종료");

        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            PlmDBUtil.close(conn);
        }
    }
    private void openSearch(HttpServletRequest req, HttpServletResponse res) {
        try {
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
            req.setAttribute( "searchCondition", map ); // 검색 화면에서 받은 검색조건
            gotoResult(req, res, "/jsp/ecm/MoldChangePlan.jsp");
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    /**
     * 함수명 : search
     * 설명 : 금형부품 설계변경 검색
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 신대범
     * 작성일자 : 2010. 11. 01.
     */
    private void searchExcel(HttpServletRequest req, HttpServletResponse res){

        //Kogger.debug(getClass(), "----------------------서블릿시작");

        Connection conn = null;

        try {
            //Form 데이터 받기
            ServletContext context = getServletContext();
            FormUploader uploader = FormUploader.newFormUploader(req, res, context);
            Hashtable hash = uploader.getFormParameters();

            hash.put( "sort" , StringUtil.checkReplaceStr((String)hash.get("sort"), "1 DESC"));

            conn = PlmDBUtil.getConnection();
            MoldChangePlanDao moldChangePlanDao = new MoldChangePlanDao(conn);

            // [Start] 결과 내 재검색
/*
            ArrayList list = moldChangePlanDao.ExcelMoldChangeList(hash); // 기존 검색 주석처리
*/
            KETParamMapUtil paramMap = KETParamMapUtil.getMap(hash);
            boolean searchInResult = ( "searchInSearch".equals(paramMap.getString("searchInSearch")) );
            List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("MoldChangePlan", searchInResult, req);
            conditionList.add(paramMap);
            ArrayList list = moldChangePlanDao.ViewMoldChangeListSIR(conditionList); // 'sort' 차이 뿐..
            // [End] 결과 내 재검색

            req.setAttribute( "condition", hash );
            req.setAttribute( "list", list);

            gotoResult(req, res, "/jsp/ecm/MoldChangePlanExcel.jsp");

        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            PlmDBUtil.close(conn);
        }
    }

    /**
     * 함수명 : search
     * 설명 : 금형부품 설계변경 검색 팝업
     * @param HttpServletRequest req , HttpServletResponse res
     * 작성자 : 신대범
     * 작성일자 : 2010. 11. 01.
     */
    private void searchPopup(HttpServletRequest req, HttpServletResponse res){

        //Kogger.debug(getClass(), "----------------------서블릿시작");

        Connection conn = null;

        try {
            //Form 데이터 받기
            ServletContext context = getServletContext();
            FormUploader uploader = FormUploader.newFormUploader(req, res, context);
            Hashtable hash = uploader.getFormParameters();

            conn = PlmDBUtil.getConnection();
            MoldChangePlanDao moldChangePlanDao = new MoldChangePlanDao(conn);

            ArrayList list = moldChangePlanDao.PopupMoldChangeList(hash);

            req.setAttribute( "condition", hash );
            req.setAttribute( "list", list);

            gotoResult(req, res, "/jsp/bom/MoldChangHistoryPopup.jsp");

        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        }  catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            PlmDBUtil.close(conn);
        }
    }

}
