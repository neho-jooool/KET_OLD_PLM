package e3ps.edm.beans;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import wt.epm.EPMDocument;
import wt.fc.ReferenceFactory;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.part.WTPart;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.edm.dao.EPMDocumentDao;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMProperties;
import ext.ket.shared.log.Kogger;

public class EDMExcelModel {

    private WritableWorkbook workbook = null;

    // {"명", "backgroundcolor","align","width"}
    private Object[] headers = new Object[] { new Object[] { "도면번호", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(30) },
	    new Object[] { "버전", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(7) },
	    new Object[] { "도면명", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(50) },
	    new Object[] { "부품번호", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "도면구분", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "도면유형", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "CAD종류", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "작성부서", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "작성자", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "작성일자", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "상신일자", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "승인일자", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "상태", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "Dummy", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(10) },
	    // Added by MJOH, 2011-03-16
	    new Object[] { "프로젝트번호", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(20) },
	    new Object[] { "프로젝트명", Colour.GREY_25_PERCENT, Alignment.CENTRE, new Integer(40) } };

    private Object[][] cntFomats = { new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.INTEGER, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.LEFT, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    // Added by MJOH, 2011-03-16
	    new Object[] { NumberFormats.DEFAULT, Alignment.CENTRE, Colour.WHITE },
	    new Object[] { NumberFormats.DEFAULT, Alignment.LEFT, Colour.WHITE } };

    private Object[] cols = null;

    private HashMap cnds = null;
    private HttpServletRequest req = null;

    public EDMExcelModel(HashMap cnds, HttpServletRequest req) {
	this.cnds = (cnds == null) ? new HashMap() : cnds;
	this.req = req;
    }

    public WritableWorkbook getWorkbook(HttpServletResponse response) throws Exception {
	return getWorkbook(response.getOutputStream());
    }

    public WritableWorkbook getWorkbook(OutputStream os) throws Exception {
	workbook = Workbook.createWorkbook(os);
	WritableSheet sheet = workbook.createSheet("도면목록", 0);

	int rownum = 0;

	cols = getHeaders();

	setColumnView(sheet, cols);

	setCntLabel(sheet, ++rownum, cols);
	printData(sheet, rownum);

	return workbook;
    }

    public WritableWorkbook getWorkbook(File file) throws Exception {
	workbook = Workbook.createWorkbook(file);
	WritableSheet sheet = workbook.createSheet("도면목록", 0);

	int rownum = 0;

	cols = getHeaders();

	setColumnView(sheet, cols);

	setCntLabel(sheet, ++rownum, cols);
	printData(sheet, rownum);

	return workbook;
    }

    /**
     * Data print
     * 
     * @param sheet
     * @throws Exception
     */
    private void printData(WritableSheet sheet, int rownum) throws Exception {

	EDMAttributes edmAttributes = EDMAttributes.getInstance();

	ReferenceFactory rf = new ReferenceFactory();
	WTUser creator = null;

	// [Start] 결과 내 재검색
	/*
	 * QueryResult result = EDMQueryHelper.find(cnds); // 기존 검색 주석처리
	 */
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(cnds);
	boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
	List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchEPMDocument", searchInResult, req);
	conditionList.add(paramMap);

	// [PLM System 1차개선]> 클래스명 : EDMExcelModel 설명 : 도면 Excel Export 수정자 : tklee 작성일자 : 2014. 6. 25.

	// QueryResult result = EDMQueryHelper.findSIR(conditionList);
	// // [End] 결과 내 재검색
	//
	// while (result.hasMoreElements()) {
	// Object oo[] = (Object[]) result.nextElement();
	// String nr = (String) oo[1];
	// String nm = (String) oo[2];
	// String nv = (String) oo[3];
	// String estate = (String) oo[4];
	// String noid = (String) oo[0];
	//
	// Timestamp createStamp = (Timestamp) oo[5];
	//
	// HashMap ibaValues = null;
	//
	// String partNumber = "";
	// String devStage = "";
	// String category = "";
	// String cadAppType = "";
	// String createDeptName = "";
	// String _dummy = "";
	// // Added by MJOH, 2011-03-16
	// String projectNo = "";
	// String projectName = "";
	// // 승인일자, 상신일자 관련 추가 2011-10-31
	// String activityName = "";
	// String completeDate = "";
	// String approvalDate = "";
	// String requestDate = "";
	// try {
	// EPMDocument epm = (EPMDocument) rf.getReference(noid).getObject();
	//
	// creator = (WTUser) rf.getReference((String) oo[7]).getObject();
	// ibaValues = edmAttributes.getIBAValues(epm, Locale.KOREAN);
	// if (ibaValues != null) {
	// devStage = (ibaValues.get(EDMHelper.IBA_DEV_STAGE) == null) ? "" : (String) ibaValues.get(EDMHelper.IBA_DEV_STAGE);
	// category = (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) == null) ? "" : (String) ibaValues
	// .get(EDMHelper.IBA_CAD_CATEGORY);
	// cadAppType = (ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE) == null) ? "" : (String) ibaValues
	// .get(EDMHelper.IBA_CAD_APP_TYPE);
	//
	// if (ibaValues.get(EDMHelper.IBA_DUMMY_FILE) != null) {
	// _dummy = (String) ibaValues.get(EDMHelper.IBA_DUMMY_FILE);
	// }
	// }
	//
	// //
	// if (category.length() > 0) {
	// partNumber = getPartNumber(epm, category);
	// }
	//
	// EDMUserData ud = EDMHelper.getEDMUserData(epm);
	// if ((ud != null) && (ud.getCreatorDeptName() != null)) {
	// createDeptName = ud.getCreatorDeptName();
	// }
	//
	// // Added by MJOH, 2011-03-16
	// ProjectMaster pjtMst = (ProjectMaster) EDMHelper.getProject(epm);
	// if (pjtMst != null) {
	// E3PSProject project = ProjectHelper.getProject(pjtMst.getPjtNo());
	// projectNo = project.getPjtNo();
	// projectName = project.getPjtName();
	// }
	// } catch (Exception e) {
	// Kogger.error(getClass(), e);
	// creator = null;
	// }
	//
	// Object obj = CommonUtil.getObject(noid);
	// // ReferenceFactory rf 2 = new ReferenceFactory();
	// KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
	// KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
	// KETWfmApprovalMaster master = null;
	// Object temp = new Object();
	// Vector vec = null;
	// WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(noid));
	// if (targetObj != null)
	// master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	// if (master != null) {
	//
	// vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	//
	// for (int k = 0; k < vec.size() - 1; k++) {
	// for (int j = k + 1; j < vec.size(); j++) {
	// history1 = (KETWfmApprovalHistory) vec.get(k);
	// history2 = (KETWfmApprovalHistory) vec.get(k);
	// if (history1.getSeqNum() < history2.getSeqNum()) {
	// temp = vec.get(k);
	// vec.set(k, vec.get(j));
	// vec.set(j, temp);
	// }
	// }
	// }
	// }
	//
	// if (vec != null) {
	// boolean isApprover = true;
	// activityName = "";
	// for (int x = 0; x < vec.size(); x++) {
	// KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x);
	// activityName = StringUtil.checkNull(history.getActivityName());
	// if (isApprover && activityName.equals("검토")) {
	// activityName = "승인";
	// isApprover = false;
	// }
	// if (history.getCompletedDate() != null)
	// completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
	// if (activityName.equals("승인"))
	// approvalDate = completeDate;
	// if (activityName.equals("요청"))
	// requestDate = completeDate;
	//
	// }
	// }

	EPMDocumentDao epmDocumentDao = new EPMDocumentDao();
	List<Map<String, Object>> edmList = epmDocumentDao.searchEPMDocumentForExcel(conditionList);
	for (Map<String, Object> edmObj : edmList) {

	    String nr = (String) edmObj.get("drawingNo");
	    String nm = (String) edmObj.get("drawingName");
	    String nv = (String) edmObj.get("ver");
	    String partNumber = (String) edmObj.get("projectNo");
	    String devStage = (String) edmObj.get("devStage");
	    String category = (String) edmObj.get("category");
	    String cadAppType = (String) edmObj.get("cadType");
	    String createDeptName = (String) edmObj.get("createDeptName");
	    String _dummy = (String) edmObj.get("dummy");
	    _dummy = "Y".equals(_dummy) ? _dummy : "";
	    java.sql.Timestamp createStamp = (java.sql.Timestamp) edmObj.get("createDate");
	    // Added by MJOH, 2011-03-16
	    String projectNo = (String) edmObj.get("projectNo");
	    String projectName = (String) edmObj.get("projectName");
	    String estate = (String) edmObj.get("status");
	    // 승인일자, 상신일자 관련 추가 2011-10-31
	    // 차후에 처리 tklee
	    String requestDate = (String) edmObj.get("requestDate");
	    String approvalDate = (String) edmObj.get("approvalDate");

	    int colnum = -1;
	    int rows = ++rownum;
	    // 도면번호
	    sheet.addCell(new Label(++colnum, rows, nr, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 버전
	    sheet.addCell(new Number(++colnum, rows, Double.parseDouble(nv), getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 도면명
	    sheet.addCell(new Label(++colnum, rows, nm, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 부품번호
	    sheet.addCell(new Label(++colnum, rows, partNumber, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 도면구분
	    sheet.addCell(new Label(++colnum, rows, devStage, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 도면종류
	    sheet.addCell(new Label(++colnum, rows, category, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // CAD종류
	    sheet.addCell(new Label(++colnum, rows, cadAppType, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 작성부서
	    sheet.addCell(new Label(++colnum, rows, createDeptName, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 작성자
	    sheet.addCell(new Label(++colnum, rows, (creator == null) ? "" : creator.getFullName(), getCellFormat(
		    (DisplayFormat) cntFomats[colnum][0], (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 작성일자
	    sheet.addCell(new Label(++colnum, rows, DateUtil.getDateString(createStamp, "d"), getCellFormat(
		    (DisplayFormat) cntFomats[colnum][0], (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 상신일자
	    sheet.addCell(new Label(++colnum, rows, requestDate, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 승인일자
	    sheet.addCell(new Label(++colnum, rows, approvalDate, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 상태
	    sheet.addCell(new Label(++colnum, rows, State.toState(estate).getDisplay(Locale.KOREAN), getCellFormat(
		    (DisplayFormat) cntFomats[colnum][0], (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // Dummy
	    sheet.addCell(new Label(++colnum, rows, ("Y".equals(_dummy)) ? _dummy : "", getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // Added by MJOH, 2011-03-16
	    // 프로젝트번호
	    sheet.addCell(new Label(++colnum, rows, projectNo, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	    // 프로젝트명
	    sheet.addCell(new Label(++colnum, rows, projectName, getCellFormat((DisplayFormat) cntFomats[colnum][0],
		    (Alignment) cntFomats[colnum][1], (Colour) cntFomats[colnum][2])));
	}
    }

    private String getPartNumber(EPMDocument epm, String category) {
	try {
	    ArrayList relateds = EDMHelper.getReferencedByParts(epm, EDMProperties.getInstance().getReferenceType(category),
		    EDMHelper.REQUIRED_STANDARD);
	    if ((relateds != null) && (relateds.size() > 0)) {
		return ((WTPart) ((Object[]) relateds.get(0))[1]).getNumber();
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return "";
    }

    private Object[] getHeaders() {
	int colnum = 0;
	Object[] cols = new Object[headers.length];
	for (int i = 0; i < headers.length; i++) {
	    Object[] header = (Object[]) headers[i];
	    cols[colnum++] = new Object[] { header[0], getCellFormat((Alignment) header[2], (Colour) header[1]), (Integer) header[3] };
	}
	return cols;
    }

    private void setColumnView(WritableSheet sheet, Object[] objs) {
	for (int i = 0; i < objs.length; i++) {
	    Object obj[] = (Object[]) objs[i];
	    sheet.setColumnView(i, ((Integer) obj[2]).intValue());
	}
    }

    private void setCntLabel(WritableSheet sheet, int rows, Object objs[]) throws RowsExceededException, WriteException {
	for (int i = 0; i < objs.length; i++) {
	    Object obj[] = (Object[]) objs[i];
	    sheet.addCell(new Label(i, rows, (String) obj[0], (WritableCellFormat) obj[1]));
	}
    }

    private WritableCellFormat getCellFormat(Alignment alignment, Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat();
	    format.setBorder(Border.ALL, BorderLineStyle.THIN);
	    if (color != null)
		format.setBackground(color);
	    if (alignment != null)
		format.setAlignment(alignment);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return format;
    }

    private WritableCellFormat getCellFormat(DisplayFormat df, Alignment alignment, Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat(df);
	    format.setBorder(Border.ALL, BorderLineStyle.THIN);
	    if (color != null)
		format.setBackground(color);
	    if (alignment != null)
		format.setAlignment(alignment);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return format;
    }
}
