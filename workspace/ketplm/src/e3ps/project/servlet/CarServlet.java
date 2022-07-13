package e3ps.project.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.dao.CarDao;
import ext.ket.shared.log.Kogger;

@SuppressWarnings("serial")
public class CarServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String command = ParamUtil.getStrParameter(req.getParameter("command"));

	// 메뉴에서 클릭시 파라미터 값 전달 되지 않아 처리.
	if (command.equals("")) {
	    command = "search";
	}

	if (command.equalsIgnoreCase("search")) {
	    search(req, res);
	} else if (command.equalsIgnoreCase("excelDown")) {
	    excelDown(req, res);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Car 검색 서블릿 적용 수정일자 : 2013. 6. 14 수정자 : 김종호
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {

	Connection conn = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	try {
	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    CarDao dao = new CarDao(conn);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    map.put("customerViewTypeVal", KETParamMapUtil.toString(map.getStringArray("customerViewType")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!map.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("carScheduleSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("carScheduleSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("carScheduleSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(map);
	    session.setAttribute("carScheduleSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 목록 결과
	    List<Map<String, Object>> list = dao.searchCarList(conditionList);
	    for (Map<String, Object> carSchedule : list) {
		strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\" ");
		strBuffer.append(" RowNum=\"" + rowCount++ + "\"");
		strBuffer.append(" MpCustomer=\"" + carSchedule.get("customer") + "\"");
		strBuffer.append(" MpCarType=\"" + carSchedule.get("carType") + "\"" + " MpCarTypeType=\"Text\"" + " MpCarTypeOnClick=\"javascript:gotoView(&apos;" + carSchedule.get("oid")
		        + "&apos;);\"" + " MpCarTypeHtmlPrefix=\"&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;\"" + " MpCarTypeHtmlPostfix=\"&lt;/font&gt;\"");
		strBuffer.append(" MpFormType=\"" + carSchedule.get("formType") + "\"");
		strBuffer.append(" MpModelName=\"" + carSchedule.get("modelName") + "\"");
		strBuffer.append(" MpTotal=\"" + Integer.parseInt(carSchedule.get("total").toString()) + "\"");
		strBuffer.append(" Op1Date=\"" + carSchedule.get("op1") + "\"");
		strBuffer.append(" Op2Date=\"" + carSchedule.get("op2") + "\"");
		strBuffer.append(" Op3Date=\"" + carSchedule.get("op3") + "\"");
		strBuffer.append(" Op4Date=\"" + carSchedule.get("op4") + "\"");
		strBuffer.append(" Op5Date=\"" + carSchedule.get("op5") + "\"");
		strBuffer.append(" Op6Date=\"" + carSchedule.get("op6") + "\"");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    req.setAttribute("rootCount", String.valueOf(list.size()));
	    req.setAttribute("pagingLength", String.valueOf("1"));
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    private void excelDown(HttpServletRequest req, HttpServletResponse res) {

	Connection conn = null;

	int rowCount = 0;

	try {
	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    CarDao dao = new CarDao(conn);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader.getFormParameters());

	    map.put("customerViewTypeVal", KETParamMapUtil.toString(map.getStringArray("customerViewType")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!map.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("carScheduleSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("carScheduleSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("carScheduleSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(map);
	    session.setAttribute("carScheduleSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // Excel File 위치 설정
	    String userAgent = req.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";
	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }
	    String sFileName = "ProgramList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    try {

		WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
		WritableSheet sheet = workbook.createSheet("자동차일정 목록", 1);

		WritableCellFormat titleformat = new WritableCellFormat(); // Title Cell의 스타일을 지정
		titleformat.setBorder(Border.ALL, BorderLineStyle.THIN);
		titleformat.setBackground(Colour.LIGHT_GREEN);
		titleformat.setAlignment(Alignment.CENTRE);
		titleformat.setVerticalAlignment(VerticalAlignment.CENTRE);

		WritableCellFormat cellFormatC = new WritableCellFormat(); // Cell의 스타일을 지정
		cellFormatC.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatC.setAlignment(Alignment.CENTRE);

		WritableCellFormat cellFormatR = new WritableCellFormat(); // Cell의 스타일을 지정
		cellFormatR.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatR.setAlignment(Alignment.RIGHT);

		int row = 0;

		Label lr = new Label(0, 0, "자동차 일정");
		sheet.addCell(lr);

		lr = new Label(0, row, "번호", titleformat);
		sheet.addCell(lr);
		lr = new Label(1, row, "고객", titleformat);
		sheet.addCell(lr);
		lr = new Label(2, row, "차종", titleformat);
		sheet.addCell(lr);
		lr = new Label(3, row, "형태", titleformat);
		sheet.addCell(lr);
		lr = new Label(4, row, "차종명", titleformat);
		sheet.addCell(lr);
		lr = new Label(5, row, "생산대수", titleformat);
		sheet.addCell(lr);
		lr = new Label(6, row, "EVENT", titleformat);
		sheet.addCell(lr);
		row++;
		lr = new Label(6, row, "Model 고정", titleformat);
		sheet.addCell(lr);
		lr = new Label(7, row, "PROTO", titleformat);
		sheet.addCell(lr);
		lr = new Label(8, row, "PILOT1", titleformat);
		sheet.addCell(lr);
		lr = new Label(9, row, "PILOT2", titleformat);
		sheet.addCell(lr);
		lr = new Label(10, row, "M", titleformat);
		sheet.addCell(lr);
		lr = new Label(11, row, "SOP", titleformat);
		sheet.addCell(lr);

		// Cell merge
		sheet.mergeCells(0, 0, 0, 1);
		sheet.mergeCells(1, 0, 1, 1);
		sheet.mergeCells(2, 0, 2, 1);
		sheet.mergeCells(3, 0, 3, 1);
		sheet.mergeCells(4, 0, 4, 1);
		sheet.mergeCells(5, 0, 5, 1);
		sheet.mergeCells(6, 0, 11, 0);

		// Cell Width
		sheet.setColumnView(0, 12);
		sheet.setColumnView(1, 15);
		sheet.setColumnView(2, 15);
		sheet.setColumnView(3, 15);
		sheet.setColumnView(4, 30);
		sheet.setColumnView(5, 15);
		sheet.setColumnView(6, 15);
		sheet.setColumnView(7, 15);
		sheet.setColumnView(8, 15);
		sheet.setColumnView(9, 15);
		sheet.setColumnView(10, 15);
		sheet.setColumnView(11, 15);

		// 3자리 콤마
		NumberFormat nf = NumberFormat.getInstance();
		int columnIndex = 0;
		// 목록 결과
		List<Map<String, Object>> list = dao.searchCarList(conditionList);
		for (Map<String, Object> carSchedule : list) {

		    row++;
		    rowCount++;
		    columnIndex = 0;

		    sheet.addCell(new Label(columnIndex++, row, Integer.toString(rowCount), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, (String) carSchedule.get("customer"), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, (String) carSchedule.get("carType"), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, (String) carSchedule.get("formType"), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, (String) carSchedule.get("modelName"), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, nf.format(Integer.parseInt(String.valueOf(carSchedule.get("total")))), cellFormatR));
		    sheet.addCell(new Label(columnIndex++, row, df((String) carSchedule.get("op1")), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, df((String) carSchedule.get("op2")), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, df((String) carSchedule.get("op3")), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, df((String) carSchedule.get("op4")), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, df((String) carSchedule.get("op5")), cellFormatC));
		    sheet.addCell(new Label(columnIndex++, row, df((String) carSchedule.get("op6")), cellFormatC));
		}

		workbook.write();
		workbook.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		// String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
		// excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////

		FileInputStream fin = new FileInputStream(excelFileObj);
		int ifilesize = (int) excelFileObj.length();
		byte b[] = new byte[ifilesize];

		res.setContentLength(ifilesize);
		res.setContentType("application/vnd.ms-excel;charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = res.getOutputStream();
		fin.read(b);
		oout.write(b, 0, ifilesize);
		oout.close();
		fin.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (excelFileObj.isFile()) {
		    excelFileObj.delete();
		}
	    }
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    private static String df(String inputString) {
	SimpleDateFormat fromUser = new SimpleDateFormat("yy/MM/dd");
	SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

	if (inputString == null || inputString.equals(""))
	    return inputString;
	try {
	    return myFormat.format(fromUser.parse(inputString));
	} catch (ParseException e) {
	    Kogger.error(CarServlet.class, e);
	}
	return inputString;
    }
}
