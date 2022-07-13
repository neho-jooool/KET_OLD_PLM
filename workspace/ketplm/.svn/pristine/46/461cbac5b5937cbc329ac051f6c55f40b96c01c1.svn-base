package e3ps.ecm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.ecm.dao.SearchEcoReportDao;
import ext.ket.shared.log.Kogger;

@SuppressWarnings("serial")
public class SearchEcoReportServlet extends CommonServlet {

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cmd = request.getParameter("cmd");
        if (cmd == null || cmd.equals(""))
            cmd = "search";

        if( cmd.equals("searchMonthlyEcoReport") ) {
            searchMonthlyEcoReport(request, response);     // 월간 현황조회
        }
        else if( cmd.equals("excelDownMonthlyEcoReport") ) {
            excelDownMonthlyEcoReport(request, response);  // 월간 현황조회Excel Download
        }
        else if ( cmd.equals("searchTypeEcoReport") ) {
            searchTypeEcoReport(request, response);        // 설계변경 유형별 현황 조회
        }
        else if ( cmd.equals("excelDownTypeEcoReport") ) {
            excelDownTypeEcoReport(request, response);     // 설계변경 유형별 현황 Excel Download
        }
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : 월간 현황조회
     * 수정일자 : 2013. 7. 24
     * 수정자   : 김종호
     */
    private void searchMonthlyEcoReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        StringBuffer strBuffer = new StringBuffer();
        int rowCount = 1;

        try {
            conn = PlmDBUtil.getConnection();
            SearchEcoReportDao dao = new SearchEcoReportDao(conn);

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
            KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

            paramMap.put("devYn",         KETParamMapUtil.toString(paramMap.getStringArray("devYn")));
            paramMap.put("divisionFlag",  KETParamMapUtil.toString(paramMap.getStringArray("divisionFlag")));
            paramMap.put("sancStateFlag", KETParamMapUtil.toString(paramMap.getStringArray("sancStateFlag")));

            // [Start] 결과내 검색 조건 처리 //
            HttpSession session = request.getSession();
            List<Map<String, Object>> conditionList = null;

            if ( !paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch") ) {

                conditionList = new ArrayList<Map<String, Object>>();
                session.setAttribute("ecoMonthlyReportSearchConditionList", conditionList);
            }
            else {

                if ( session.getAttribute("ecoMonthlyReportSearchConditionList") != null ) {

                    conditionList = (ArrayList<Map<String, Object>>)session.getAttribute("ecoMonthlyReportSearchConditionList");
                }
                else {

                    conditionList = new ArrayList<Map<String, Object>>();
                }
            }

            conditionList.add( paramMap );
            session.setAttribute("ecoMonthlyReportSearchConditionList", conditionList);
            // [End] 결과내 검색 조건 처리 //

            List<Map<String, Object>> list = dao.getSearchMonthlyEcoReportListSQL(paramMap, conditionList);
            for ( Map<String, Object> ecoMonthlyReport : list) {
                strBuffer.append( "<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;" );
                strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;");
                strBuffer.append( " DeptName=&quot;" + ecoMonthlyReport.get("orgName") + "&quot;");     // 부서명
                strBuffer.append( " OrgName=&quot;" + ecoMonthlyReport.get("creatorName") + "&quot;");    // 작성자
                strBuffer.append( " OldEcoCnt=&quot;" + ecoMonthlyReport.get("oldEcoCount") + "&quot;"); // 전년
                strBuffer.append( " NewEcoCnt=&quot;" + ecoMonthlyReport.get("newEcoCount") + "&quot;");    // 금년
                strBuffer.append( " M1=&quot;"  + ecoMonthlyReport.get("mm01") + "&quot;");
                strBuffer.append( " M2=&quot;"  + ecoMonthlyReport.get("mm02") + "&quot;");
                strBuffer.append( " M3=&quot;"  + ecoMonthlyReport.get("mm03") + "&quot;");
                strBuffer.append( " M4=&quot;"  + ecoMonthlyReport.get("mm04") + "&quot;");
                strBuffer.append( " M5=&quot;"  + ecoMonthlyReport.get("mm05") + "&quot;");
                strBuffer.append( " M6=&quot;"  + ecoMonthlyReport.get("mm06") + "&quot;");
                strBuffer.append( " M7=&quot;"  + ecoMonthlyReport.get("mm07") + "&quot;");
                strBuffer.append( " M8=&quot;"  + ecoMonthlyReport.get("mm08") + "&quot;");
                strBuffer.append( " M9=&quot;"  + ecoMonthlyReport.get("mm09") + "&quot;");
                strBuffer.append( " M10=&quot;" + ecoMonthlyReport.get("mm10") + "&quot;");
                strBuffer.append( " M11=&quot;" + ecoMonthlyReport.get("mm11") + "&quot;");
                strBuffer.append( " M12=&quot;" + ecoMonthlyReport.get("mm12") + "&quot;");
                strBuffer.append( "/>" );
            }
            request.setAttribute( "searchCondition", paramMap );        // 검색 화면에서 받은 검색조건
            request.setAttribute( "tgData", strBuffer.toString() );     // 검색 결과 데이터
            gotoResult(request, response, "/jsp/ecm/SearchMonthlyEcoReportForm.jsp");

        }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(conn);
        }
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : 월간 현황조회 Excel Download
     * 수정일자 : 2013. 7. 24
     * 수정자   : 김종호
     */
    private void excelDownMonthlyEcoReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        int rowCount = 0;

        try {
            conn = PlmDBUtil.getConnection();
            SearchEcoReportDao dao = new SearchEcoReportDao(conn);

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
            KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

            paramMap.put("devYn",         KETParamMapUtil.toString(paramMap.getStringArray("devYn")));
            paramMap.put("divisionFlag",  KETParamMapUtil.toString(paramMap.getStringArray("divisionFlag")));
            paramMap.put("sancStateFlag", KETParamMapUtil.toString(paramMap.getStringArray("sancStateFlag")));

            // [Start] 결과내 검색 조건 처리 //
            HttpSession session = request.getSession();
            List<Map<String, Object>> conditionList = null;

            if ( !paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch") ) {

                conditionList = new ArrayList<Map<String, Object>>();
                session.setAttribute("ecoMonthlyReportSearchConditionList", conditionList);
            }
            else {

                if ( session.getAttribute("ecoMonthlyReportSearchConditionList") != null ) {

                    conditionList = (ArrayList<Map<String, Object>>)session.getAttribute("ecoMonthlyReportSearchConditionList");
                }
                else {

                    conditionList = new ArrayList<Map<String, Object>>();
                }
            }

            conditionList.add( paramMap );
            session.setAttribute("ecoMonthlyReportSearchConditionList", conditionList);
            // [End] 결과내 검색 조건 처리 //

            // Excel File 위치 설정
            String userAgent = request.getHeader("User-Agent");
            boolean ie = userAgent.indexOf("MSIE") > -1;

            SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
            String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
            String sFilePath = sWtHome + "/temp/download";
            File downPath = new File(sFilePath);
            if ( !downPath.isDirectory() ) {
                downPath.mkdir();
            }
            String sFileName = "SearchMonthlyECOList_" +  ff.format(new Date()) + ".xls";

            // 파일명 한글 깨짐 방지
            if ( ie ) {
                sFileName = URLEncoder.encode(sFileName, "utf-8");
            } else {
                sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
            }

            // Excel File Object
            File excelFileObj = new File(sFilePath+ "/" + sFileName);

            try {

                WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
                WritableSheet s1 = workbook.createSheet("월간 ECO 현황 조회", 0);

                WritableCellFormat cellFormat = new WritableCellFormat();
                cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

                //문서제목
                Label lr = new jxl.write.Label(0, 0, "월간 ECO 현황 조회");
                s1.addCell(lr);

                int row = 2;

                lr = new Label(0, row, "번호", cellFormat);
                s1.addCell(lr);

                lr = new Label(1, row, "부서명", cellFormat);
                s1.addCell(lr);

                lr = new Label(2, row, "작성자", cellFormat);
                s1.addCell(lr);

                lr = new Label(3, row, "전년도합계", cellFormat);
                s1.addCell(lr);

                lr = new Label(4, row, "합계", cellFormat);
                s1.addCell(lr);

                lr = new Label(5, row, "1월", cellFormat);
                s1.addCell(lr);

                lr = new Label(6, row, "2월", cellFormat);
                s1.addCell(lr);

                lr = new Label(7, row, "3월", cellFormat);
                s1.addCell(lr);

                lr = new Label(8, row, "4월", cellFormat);
                s1.addCell(lr);

                lr = new Label(9, row, "5월", cellFormat);
                s1.addCell(lr);

                lr = new Label(10, row, "6월", cellFormat);
                s1.addCell(lr);

                lr = new Label(11, row, "7월", cellFormat);
                s1.addCell(lr);

                lr = new Label(12, row, "8월", cellFormat);
                s1.addCell(lr);

                lr = new Label(13, row, "9월", cellFormat);
                s1.addCell(lr);

                lr = new Label(14, row, "10월", cellFormat);
                s1.addCell(lr);

                lr = new Label(15, row, "11월", cellFormat);
                s1.addCell(lr);

                lr = new Label(16, row, "12월", cellFormat);
                s1.addCell(lr);

                // 3자리 콤마
                NumberFormat nf = NumberFormat.getInstance();

                // 결과 목록 조회
                List<Map<String, Object>> list = dao.getSearchMonthlyEcoReportListSQL(paramMap, conditionList);
                for ( Map<String, Object> ecoMonthlyReport : list) {

                    row++;
                    rowCount++;

                    //번호
                    lr = new Label(0, row, rowCount + "", cellFormat);
                    s1.addCell(lr);

                    //부서명
                    lr = new Label(1, row, StringUtil.checkNull( (String) ecoMonthlyReport.get("orgName") ), cellFormat);
                    s1.addCell(lr);

                    //작성자
                    lr = new Label(2, row, StringUtil.checkNull( (String) ecoMonthlyReport.get("creatorName") ), cellFormat);
                    s1.addCell(lr);

                    //전년도합계
                    lr = new Label(3, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("oldEcoCount"))) ), cellFormat);
                    s1.addCell(lr);

                    //합계
                    lr = new Label(4, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("newEcoCount"))) ), cellFormat);
                    s1.addCell(lr);

                    //1월
                    lr = new Label(5, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm01"))) ), cellFormat);
                    s1.addCell(lr);

                    //2월
                    lr = new Label(6, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm02"))) ), cellFormat);
                    s1.addCell(lr);

                    //3월
                    lr = new Label(7, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm03"))) ), cellFormat);
                    s1.addCell(lr);

                    //4월
                    lr = new Label(8, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm04"))) ), cellFormat);
                    s1.addCell(lr);

                    //5월
                    lr = new Label(9, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm05"))) ), cellFormat);
                    s1.addCell(lr);

                    //6월
                    lr = new Label(10, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm06"))) ), cellFormat);
                    s1.addCell(lr);

                    //7월
                    lr = new Label(11, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm07"))) ), cellFormat);
                    s1.addCell(lr);

                    //8월
                    lr = new Label(12, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm08"))) ), cellFormat);
                    s1.addCell(lr);

                    //9월
                    lr = new Label(13, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm09"))) ), cellFormat);
                    s1.addCell(lr);

                    //10월
                    lr = new Label(14, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm10"))) ), cellFormat);
                    s1.addCell(lr);

                    //11월
                    lr = new Label(15, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm11"))) ), cellFormat);
                    s1.addCell(lr);

                    //12월
                    lr = new Label(16, row, nf.format( Integer.parseInt( String.valueOf(ecoMonthlyReport.get("mm12"))) ), cellFormat);
                    s1.addCell(lr);
                }

                workbook.write();
                workbook.close();
            }
            catch (Exception e) {
               Kogger.error(getClass(), e);
               throw e;
            }

            try{
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Added by MJOH, 2011-04-18
                // 엑셀 다운로드 파일 DRM 암호화 적용
                String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
                excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, request.getRemoteAddr() );
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FileInputStream fin = new FileInputStream(excelFileObj);
                int ifilesize = (int)excelFileObj.length();
                byte b[] = new byte[ifilesize];

                response.setContentLength(ifilesize);
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
                response.setHeader("Content-Transfer-Encoding", "binary");

                ServletOutputStream oout = response.getOutputStream();
                fin.read(b);
                oout.write(b,0,ifilesize);
                oout.close();
                fin.close();
            }
            catch (Exception e) {
               Kogger.error(getClass(), e);
               throw e;
            }
            finally {
                if ( excelFileObj.isFile() ) {
                    excelFileObj.delete();
                }
            }
        }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            //커넥션 종료
            PlmDBUtil.close(conn);
        }
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : 설계변경 유형별 현황조회
     * 수정일자 : 2013. 7. 24
     * 수정자   : 김종호
     */
    private void searchTypeEcoReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;

        StringBuffer strBuffer = new StringBuffer();
        int rowCount = 1;

        try {
            conn = PlmDBUtil.getConnection();
            SearchEcoReportDao dao = new SearchEcoReportDao(conn);

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
            KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

            paramMap.put("devYn",            KETParamMapUtil.toString(paramMap.getStringArray("devYn")));
            paramMap.put("divisionFlag",     KETParamMapUtil.toString(paramMap.getStringArray("divisionFlag")));
            paramMap.put("changeReason",     KETParamMapUtil.toString(paramMap.getStringArray("changeReason")));
            paramMap.put("increaseProdType", KETParamMapUtil.toString(paramMap.getStringArray("increaseProdType")));
            paramMap.put("sancStateFlag",    KETParamMapUtil.toString(paramMap.getStringArray("sancStateFlag")));
            paramMap.put("dieType",          KETParamMapUtil.toString(paramMap.getStringArray("dieType")));
            paramMap.put("domestic_yn",      KETParamMapUtil.toString(paramMap.getStringArray("domestic_yn")));
            paramMap.put("car_maker",        KETParamMapUtil.toString(paramMap.getStringArray("car_maker")));
            paramMap.put("car_category",     KETParamMapUtil.toString(paramMap.getStringArray("car_category")));

            // [Start] 결과내 검색 조건 처리 //
            HttpSession session = request.getSession();
            List<Map<String, Object>> conditionList = null;

            if ( !paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch") ) {

                conditionList = new ArrayList<Map<String, Object>>();
                session.setAttribute("ecoReportSearchConditionList", conditionList);
            }
            else {

                if ( session.getAttribute("ecoReportSearchConditionList") != null ) {

                    conditionList = (ArrayList<Map<String, Object>>)session.getAttribute("ecoReportSearchConditionList");
                }
                else {

                    conditionList = new ArrayList<Map<String, Object>>();
                }
            }

            conditionList.add( paramMap );
            session.setAttribute("ecoReportSearchConditionList", conditionList);
            // [End] 결과내 검색 조건 처리 //

            List<Map<String, Object>> list = dao.getSearchTypeEcoReportListSQL(paramMap, conditionList);
            // ecoReasonTypeCls(조회결과보기) : C - 설계변경 사유별, I - 생산성 향상 유형별
            // prodMoldCls(ECO구분) : PT001 - 제품, PT002 - 금형
            if ( paramMap.getString("ecoReasonTypeCls").equals("C") ) {
                for ( Map<String, Object> typeEcoReport : list) {

                    strBuffer.append( "<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;" );
                    strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;");

                    if ( paramMap.getString("prodMoldCls").equals("PT002") ) {
                        if( typeEcoReport.get("partNo") != null && typeEcoReport.get("partNo").toString().substring(0, 2).equals("29") ) {
                            strBuffer.append( " ObjType=&quot;" + typeEcoReport.get("obj_type") + "&quot;");
                        }
                        else {
                            strBuffer.append( " ObjType=&quot;" + StringUtil.checkReplaceStr( (String) typeEcoReport.get("obj_type"),"-") + "&quot;");
                        }
                    }

                    strBuffer.append( " PartNo=&quot;" + typeEcoReport.get("partNo") + "&quot;"
                                    + " PartNoOnClick=&quot;javascript:viewPart2(&apos;" + typeEcoReport.get("oid") + "&apos;);&quot;"
                                    + " PartNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()+ "&apos;&gt;&quot;"
                                    + " PartNoeHtmlPostfix=&quot;&lt;/font&gt;&quot;" );
                    strBuffer.append( " PartName=&quot;" + TreeGridUtil.replaceContentForI(typeEcoReport.get("partName").toString()) + "&quot;");
                    strBuffer.append( " Oid=&quot;" + typeEcoReport.get("oid") + "&quot;");
                    strBuffer.append( " EcoCount=&quot;" + typeEcoReport.get("ecoCount") + "&quot;");
                    strBuffer.append( " DevCnt=&quot;" + typeEcoReport.get("dev_cnt") + "&quot;");
                    strBuffer.append( " ProdCnt=&quot;" + typeEcoReport.get("prod_cnt") + "&quot;");
                    strBuffer.append( " CarDivCnt=&quot;" + typeEcoReport.get("car_div_cnt") + "&quot;");
                    strBuffer.append( " ElecDivCnt=&quot;" + typeEcoReport.get("elec_div_cnt") + "&quot;");
                    strBuffer.append( " Reason01=&quot;" + typeEcoReport.get("REASON_1") + "&quot;");
                    strBuffer.append( " Reason02=&quot;" + typeEcoReport.get("REASON_2") + "&quot;");
                    strBuffer.append( " Reason03=&quot;" + typeEcoReport.get("REASON_3") + "&quot;");
                    strBuffer.append( " Reason04=&quot;" + typeEcoReport.get("REASON_4") + "&quot;");
                    strBuffer.append( " Reason05=&quot;" + typeEcoReport.get("REASON_5") + "&quot;");
                    strBuffer.append( " Reason06=&quot;" + typeEcoReport.get("REASON_6") + "&quot;");

                    if ( paramMap.getString("prodMoldCls").equals("PT002") ) {
                        strBuffer.append( " Reason07=&quot;" + typeEcoReport.get("REASON_7") + "&quot;");
                    }

                    strBuffer.append( "/>" );
                }

            }
            else if ( paramMap.getString("ecoReasonTypeCls").equals("I") ) {
                for ( Map<String, Object> typeEcoReport : list) {
                    strBuffer.append( "<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;" );
                    strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;");
                    strBuffer.append( " ObjType=&quot;" + typeEcoReport.get("obj_type") + "&quot;");
                    strBuffer.append( " PartNo=&quot;" + typeEcoReport.get("partNo") + "&quot;"
                                    + " PartNoOnClick=&quot;javascript:viewPart2(&apos;" + typeEcoReport.get("oid") + "&apos;);&quot;"
                                    + " PartNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()+ "&apos;&gt;&quot;"
                                    + " PartNoeHtmlPostfix=&quot;&lt;/font&gt;&quot;" );
                    strBuffer.append( " PartName=&quot;" + TreeGridUtil.replaceContentForI(typeEcoReport.get("partName").toString()) + "&quot;");
                    strBuffer.append( " Oid=&quot;" + typeEcoReport.get("oid") + "&quot;");
                    strBuffer.append( " EcoCount=&quot;" + typeEcoReport.get("ecoCount") + "&quot;");
                    strBuffer.append( " Incr01=&quot;" + typeEcoReport.get("INCR_01") + "&quot;");
                    strBuffer.append( " Incr02=&quot;" + typeEcoReport.get("INCR_02") + "&quot;");
                    strBuffer.append( " Incr03=&quot;" + typeEcoReport.get("INCR_03") + "&quot;");
                    strBuffer.append( " Incr04=&quot;" + typeEcoReport.get("INCR_04") + "&quot;");
                    strBuffer.append( " Incr05=&quot;" + typeEcoReport.get("INCR_05") + "&quot;");
                    strBuffer.append( " Incr06=&quot;" + typeEcoReport.get("INCR_06") + "&quot;");
                    strBuffer.append( " Incr07=&quot;" + typeEcoReport.get("INCR_07") + "&quot;");
                    strBuffer.append( " Incr08=&quot;" + typeEcoReport.get("INCR_08") + "&quot;");
                    strBuffer.append( " Incr09=&quot;" + typeEcoReport.get("INCR_09") + "&quot;");
                    strBuffer.append( " Incr10=&quot;" + typeEcoReport.get("INCR_10") + "&quot;");
                    strBuffer.append( " Incr11=&quot;" + typeEcoReport.get("INCR_11") + "&quot;");
                    strBuffer.append( " Incr12=&quot;" + typeEcoReport.get("INCR_12") + "&quot;");
                    strBuffer.append( "/>" );
                }
            }

            request.setAttribute( "searchCondition", paramMap );        // 검색 화면에서 받은 검색조건
            request.setAttribute( "tgData", strBuffer.toString() );     // 검색 결과 데이터
            gotoResult(request, response, "/jsp/ecm/SearchTypeEcoReportForm.jsp");
        }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(conn);
        }
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : 설계변경 유형별 현황조회 Excel Download
     * 수정일자 : 2013. 7. 24
     * 수정자   : 김종호
     */
    private void excelDownTypeEcoReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
        int rowCount = 0;

        try {
            conn = PlmDBUtil.getConnection();
            SearchEcoReportDao dao = new SearchEcoReportDao(conn);

            //Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
            KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

            paramMap.put("devYn",         KETParamMapUtil.toString(paramMap.getStringArray("devYn")));
            paramMap.put("divisionFlag",  KETParamMapUtil.toString(paramMap.getStringArray("divisionFlag")));
            paramMap.put("sancStateFlag", KETParamMapUtil.toString(paramMap.getStringArray("sancStateFlag")));

         // [Start] 결과내 검색 조건 처리 //
            HttpSession session = request.getSession();
            List<Map<String, Object>> conditionList = null;

            if ( !paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch") ) {

                conditionList = new ArrayList<Map<String, Object>>();
                session.setAttribute("ecoReportSearchConditionList", conditionList);
            }
            else {

                if ( session.getAttribute("ecoReportSearchConditionList") != null ) {

                    conditionList = (ArrayList<Map<String, Object>>)session.getAttribute("ecoReportSearchConditionList");
                }
                else {

                    conditionList = new ArrayList<Map<String, Object>>();
                }
            }

            conditionList.add( paramMap );
            session.setAttribute("ecoReportSearchConditionList", conditionList);
            // [End] 결과내 검색 조건 처리 //

            // Excel File 위치 설정
            String userAgent = request.getHeader("User-Agent");
            boolean ie = userAgent.indexOf("MSIE") > -1;

            SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
            String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
            String sFilePath = sWtHome + "/temp/download";
            File downPath = new File(sFilePath);
            if ( !downPath.isDirectory() ) {
                downPath.mkdir();
            }
            String sFileName = "TypeEcoListExcelForm_" +  ff.format(new Date()) + ".xls";

            // 파일명 한글 깨짐 방지
            if ( ie ) {
                sFileName = URLEncoder.encode(sFileName, "utf-8");
            } else {
                sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
            }

            // Excel File Object
            File excelFileObj = new File(sFilePath+ "/" + sFileName);

            try {

                WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
                WritableSheet s1 = workbook.createSheet("설계변경 유형별 ECO 현황 조회", 0);

                WritableCellFormat cellFormat = new WritableCellFormat();
                cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

                //문서제목
                Label lr = new jxl.write.Label(0, 0, "설계변경 유형별 ECO 현황 조회");
                s1.addCell(lr);

                int row = 2;

                //타이틀
                if ( "C".equals( paramMap.getString("ecoReasonTypeCls") ) ) {
                    if ( "PT001".equals( paramMap.getString("prodMoldCls") ) ) {

                        lr = new Label(0, row, "번호", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(1, row, "부품번호", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(2, row, "부품명", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(3, row, "건수", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(4, row, "자동차사업부", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(5, row, "전자사업부", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(6, row, "개발단계", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(7, row, "양산단계", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(8, row, "고객요청", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(9, row, "생산성향상", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(10, row, "품질문제", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(11, row, "도면양산이관", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(12, row, "BOM양산이관", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(13, row, "기타", cellFormat);
                        s1.addCell(lr);
                    }
                    else if( "PT002".equals( paramMap.getString("prodMoldCls") ) ) {

                        lr = new Label(0, row, "번호", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(1, row, "Type", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(2, row, "Die No", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(3, row, "부품명", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(4, row, "건수", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(5, row, "자동차사업부", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(6, row, "전자사업부", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(7, row, "개발단계", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(8, row, "양산단계", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(9, row, "고객요청", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(10, row, "품질문제", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(11, row, "제품설변", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(12, row, "금형보완", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(13, row, "생산성향상", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(14, row, "도면정리", cellFormat);
                        s1.addCell(lr);

                        lr = new Label(15, row, "기타", cellFormat);
                        s1.addCell(lr);
                    }
                }
                else if( "I".equals( paramMap.getString("ecoReasonTypeCls") ) ) {

                    lr = new Label(0, row, "번호", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(1, row, "Type", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(2, row, "Die No", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(3, row, "부품명", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(4, row, "건수", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(5, row, "자동차사업부", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(6, row, "전자사업부", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(7, row, "개발단계", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(8, row, "양산단계", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(9, row, "미성형", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(10, row, "제품치수", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(11, row, "BURR", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(12, row, "제품이송", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(13, row, "WELD", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(14, row, "자국발생", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(15, row, "GAS", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(16, row, "스크랩상승", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(17, row, "제품취출", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(18, row, "부품파손", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(19, row, "금형조립", cellFormat);
                    s1.addCell(lr);

                    lr = new Label(20, row, "기타", cellFormat);
                    s1.addCell(lr);
                }

                // 3자리 콤마
                NumberFormat nf = NumberFormat.getInstance();

                List<Map<String, Object>> list = dao.getSearchTypeEcoReportListSQL(paramMap, conditionList);
                // ecoReasonTypeCls(조회결과보기) : C - 설계변경 사유별, I - 생산성 향상 유형별
                // prodMoldCls(ECO구분) : PT001 - 제품, PT002 - 금형
                if ( paramMap.getString("ecoReasonTypeCls").equals("C") ) {
                    for ( Map<String, Object> typeEcoReport : list) {

                        row++;
                        rowCount++;

                        if ( "PT001".equals( paramMap.getString("prodMoldCls") ) ) {

                            //번호
                            lr = new Label(0, row, rowCount + "", cellFormat);
                            s1.addCell(lr);

                            //부품번호
                            lr = new Label(1, row, StringUtil.checkNull( (String) typeEcoReport.get("partNo") ), cellFormat);
                            s1.addCell(lr);

                            //부품명
                            lr = new Label(2, row, StringUtil.checkNull( (String) typeEcoReport.get("partName") ), cellFormat);
                            s1.addCell(lr);

                            //건수
                            lr = new Label(3, row, nf.format( Integer.parseInt((String) typeEcoReport.get("ecoCount")) ), cellFormat);
                            s1.addCell(lr);

                            //자동차사업부
                            lr = new Label(4, row, nf.format( Integer.parseInt((String) typeEcoReport.get("car_div_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //전자사업부
                            lr = new Label(5, row, nf.format( Integer.parseInt((String) typeEcoReport.get("elec_div_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //개발단계
                            lr = new Label(6, row, nf.format( Integer.parseInt((String) typeEcoReport.get("dev_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //양산단계
                            lr = new Label(7, row, nf.format( Integer.parseInt((String) typeEcoReport.get("prod_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //고객요청 건수
                            lr = new Label(8, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_1")) ), cellFormat);
                            s1.addCell(lr);

                            //생산성향상 건수
                            lr = new Label(9, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_2")) ), cellFormat);
                            s1.addCell(lr);

                            //품질문제 건수
                            lr = new Label(10, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_3")) ), cellFormat);
                            s1.addCell(lr);

                            //도면양산이관 건수
                            lr = new Label(11, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_4")) ), cellFormat);
                            s1.addCell(lr);

                            //BOM양산이관 건수
                            lr = new Label(12, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_5")) ), cellFormat);
                            s1.addCell(lr);

                            //기타 건수
                            lr = new Label(13, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_6")) ), cellFormat);
                            s1.addCell(lr);
                        }
                        else if ( "PT002".equals( paramMap.getString("prodMoldCls") ) ) {

                            //번호
                            lr = new Label(0, row, rowCount + "", cellFormat);
                            s1.addCell(lr);

                            //Type
                            lr = new Label(1, row, StringUtil.checkReplaceStr( (String) typeEcoReport.get("obj_type"),"-"), cellFormat);
                            s1.addCell(lr);

                            //Die No
                            lr = new Label(2, row, StringUtil.checkNull( (String) typeEcoReport.get("partNo") ), cellFormat);
                            s1.addCell(lr);

                            //부품명
                            lr = new Label(3, row, StringUtil.checkNull( (String) typeEcoReport.get("partName") ), cellFormat);
                            s1.addCell(lr);

                            //건수
                            lr = new Label(4, row, nf.format( Integer.parseInt((String) typeEcoReport.get("ecoCount")) ), cellFormat);
                            s1.addCell(lr);

                            //자동차사업부
                            lr = new Label(5, row, nf.format( Integer.parseInt((String) typeEcoReport.get("car_div_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //전자사업부
                            lr = new Label(6, row, nf.format( Integer.parseInt((String) typeEcoReport.get("elec_div_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //개발단계
                            lr = new Label(7, row, nf.format( Integer.parseInt((String) typeEcoReport.get("dev_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //양산단계
                            lr = new Label(8, row, nf.format( Integer.parseInt((String) typeEcoReport.get("prod_cnt")) ), cellFormat);
                            s1.addCell(lr);

                            //고객요청 건수
                            lr = new Label(9, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_1")) ), cellFormat);
                            s1.addCell(lr);

                            //품질문제 건수
                            lr = new Label(10, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_2")) ), cellFormat);
                            s1.addCell(lr);

                            //제품설변 건수
                            lr = new Label(11, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_3")) ), cellFormat);
                            s1.addCell(lr);

                            //금형보완 건수
                            lr = new Label(12, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_4")) ), cellFormat);
                            s1.addCell(lr);

                            //생산성향상 건수
                            lr = new Label(13, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_5")) ), cellFormat);
                            s1.addCell(lr);

                            //도면정리 건수
                            lr = new Label(14, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_6")) ), cellFormat);
                            s1.addCell(lr);

                            //기타 건수
                            lr = new Label(15, row, nf.format( Integer.parseInt((String) typeEcoReport.get("REASON_7")) ), cellFormat);
                            s1.addCell(lr);
                        }
                    }
                }
                else if ( paramMap.getString("ecoReasonTypeCls").equals("I") ) {
                    for ( Map<String, Object> typeEcoReport : list) {

                        row++;
                        rowCount++;

                        //번호
                        lr = new Label(0, row, rowCount + "", cellFormat);
                        s1.addCell(lr);

                        //Type
                        lr = new Label(1, row, StringUtil.checkReplaceStr( (String) typeEcoReport.get("obj_type"),"-"), cellFormat);
                        s1.addCell(lr);

                        //Die No
                        lr = new Label(2, row, StringUtil.checkNull( (String) typeEcoReport.get("partNo") ), cellFormat);
                        s1.addCell(lr);

                        //부품명
                        lr = new Label(3, row, StringUtil.checkNull( (String) typeEcoReport.get("partName") ), cellFormat);
                        s1.addCell(lr);

                        //건수
                        lr = new Label(4, row, nf.format( Integer.parseInt((String) typeEcoReport.get("ecoCount")) ), cellFormat);
                        s1.addCell(lr);

                        //자동차사업부
                        lr = new Label(5, row, nf.format( Integer.parseInt((String) typeEcoReport.get("car_div_cnt")) ), cellFormat);
                        s1.addCell(lr);

                        //전자사업부
                        lr = new Label(6, row, nf.format( Integer.parseInt((String) typeEcoReport.get("elec_div_cnt")) ), cellFormat);
                        s1.addCell(lr);

                        //개발단계
                        lr = new Label(7, row, nf.format( Integer.parseInt((String) typeEcoReport.get("dev_cnt")) ), cellFormat);
                        s1.addCell(lr);

                        //양산단계
                        lr = new Label(8, row, nf.format( Integer.parseInt((String) typeEcoReport.get("prod_cnt")) ), cellFormat);
                        s1.addCell(lr);

                        //미성형 건수
                        lr = new Label(9, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_01")) ), cellFormat);
                        s1.addCell(lr);

                        //제품치수 건수
                        lr = new Label(10, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_02")) ), cellFormat);
                        s1.addCell(lr);

                        //BURR 건수
                        lr = new Label(11, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_03")) ), cellFormat);
                        s1.addCell(lr);

                        //제품 이송 건수
                        lr = new Label(12, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_04")) ), cellFormat);
                        s1.addCell(lr);

                        //WELD 건수
                        lr = new Label(13, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_05")) ), cellFormat);
                        s1.addCell(lr);

                        //자국 발생 건수
                        lr = new Label(14, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_06")) ), cellFormat);
                        s1.addCell(lr);

                        //GAS 건수
                        lr = new Label(15, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_07")) ), cellFormat);
                        s1.addCell(lr);

                        //스크랩 상승 건수
                        lr = new Label(16, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_08")) ), cellFormat);
                        s1.addCell(lr);

                        //제품취출 건수
                        lr = new Label(17, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_09")) ), cellFormat);
                        s1.addCell(lr);

                        //부품 파손 건수
                        lr = new Label(18, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_10")) ), cellFormat);
                        s1.addCell(lr);

                        //금형조립 건수
                        lr = new Label(19, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_11")) ), cellFormat);
                        s1.addCell(lr);

                        //기타 건수
                        lr = new Label(20, row, nf.format( Integer.parseInt((String) typeEcoReport.get("INCR_12")) ), cellFormat);
                        s1.addCell(lr);
                    }
                }
                workbook.write();
                workbook.close();
            }
            catch (Exception e) {
               Kogger.error(getClass(), e);
               throw e;
            }

            try{
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Added by MJOH, 2011-04-18
                // 엑셀 다운로드 파일 DRM 암호화 적용
                String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
                excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, request.getRemoteAddr() );
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////

                FileInputStream fin = new FileInputStream(excelFileObj);
                int ifilesize = (int)excelFileObj.length();
                byte b[] = new byte[ifilesize];

                response.setContentLength(ifilesize);
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
                response.setHeader("Content-Transfer-Encoding", "binary");

                ServletOutputStream oout = response.getOutputStream();
                fin.read(b);
                oout.write(b,0,ifilesize);
                oout.close();
                fin.close();
            }
            catch (Exception e) {
               Kogger.error(getClass(), e);
               throw e;
            }
            finally {
                if ( excelFileObj.isFile() ) {
                    excelFileObj.delete();
                }
            }
        }
        catch (Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(conn);
        }
    }
}
