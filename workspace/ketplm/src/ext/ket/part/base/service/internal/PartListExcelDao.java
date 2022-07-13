package ext.ket.part.base.service.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import e3ps.common.message.KETMessageService;
import e3ps.common.util.KETStringUtil;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartListItemDTO;
import ext.ket.shared.log.Kogger;

public class PartListExcelDao {

    public String getStringValue(Cell cell) {
	String value = "";

	if (cell == null)
	    return value;

	switch (cell.getCellType()) {
	case Cell.CELL_TYPE_FORMULA:
	    value = cell.getCellFormula();
	    break;
	case Cell.CELL_TYPE_NUMERIC:
	    value = Integer.toString(new Double(cell.getNumericCellValue()).intValue());
	    break;
	case Cell.CELL_TYPE_STRING:
	    value = cell.getStringCellValue();
	    break;
	case Cell.CELL_TYPE_BLANK:
	    break;
	case Cell.CELL_TYPE_BOOLEAN:
	    value = Boolean.toString(cell.getBooleanCellValue());
	    break;
	case Cell.CELL_TYPE_ERROR:
	    value = "ERROR";
	    break;
	default:
	}

	return value.trim();
    }

    public void setCellMerge(HSSFSheet sheet, int sColNo, int eColNo, int sRowNo, int eRowno) {
	sheet.addMergedRegion(new CellRangeAddress(sColNo, // 시작 행번호
	        eColNo, // 마지막 행번호
	        sRowNo, // 시작 열번호
	        eRowno // 마지막 열번호
	));
    }

    public static void createCelltoExcel(HSSFSheet sheet, HSSFRow row, int cellNo, HSSFCellStyle cellStyle, String cellValue) {
	HSSFCell hcell3 = row.createCell((short) cellNo);

	// hcell3.setEncoding(HSSFCell.ENCODING_UTF_16);
	if (!cellValue.equals("")) {
	    // hcell3.setEncoding(HSSFCell.ENCODING_UTF_16);
	    hcell3.setCellValue(cellValue);
	}
	hcell3.setCellStyle(cellStyle);
	// hcell3.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	// sheet.autoSizeColumn(cellNo);
    }

    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, short fillForegroundColor, short fillPattern, short verticalAlignment,
	    short borderBottom, short borderLeft, short borderRight, short borderTop, short alignment) {
	HSSFCellStyle cellStyle = workbook.createCellStyle();

	cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
	cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	cellStyle.setBorderBottom(CellStyle.BORDER_DOUBLE);
	cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
	cellStyle.setBorderRight(CellStyle.BORDER_THIN);
	cellStyle.setBorderTop(CellStyle.BORDER_THIN);
	cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬 Font font = wb.createFont();

	// cellStyle.setWrapText(true);

	return cellStyle;
    }

    public static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
	HSSFCellStyle cellStyle1 = workbook.createCellStyle();
	cellStyle1.setFillForegroundColor(HSSFColor.YELLOW.index);
	cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	cellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
	cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
	cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
	cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
	cellStyle1.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬 Font font = wb.createFont();

	cellStyle1.setWrapText(true);

	return cellStyle1;
    }

    public static HSSFCellStyle getHeaderStyle2(HSSFWorkbook workbook) {
	HSSFCellStyle cellStyle1 = workbook.createCellStyle();
	cellStyle1.setFillForegroundColor(HSSFColor.YELLOW.index);
	cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	cellStyle1.setBorderBottom(CellStyle.BORDER_DOUBLE);
	cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
	cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
	cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
	cellStyle1.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬 Font font = wb.createFont();

	cellStyle1.setWrapText(true);

	return cellStyle1;
    }

    public static HSSFCellStyle getDataStyle(HSSFWorkbook workbook) {
	HSSFCellStyle cellStyle2 = workbook.createCellStyle();
	cellStyle2.setBorderBottom(CellStyle.BORDER_THIN); // 테두리 가늘게
	cellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
	cellStyle2.setBorderRight(CellStyle.BORDER_THIN);
	cellStyle2.setBorderTop(CellStyle.BORDER_THIN);
	cellStyle2.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬 Font font = wb.createFont();
	cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

	cellStyle2.setWrapText(true);

	return cellStyle2;
    }

    public static HSSFCellStyle getDataStyle2(HSSFWorkbook workbook) {
	HSSFCellStyle cellStyle2 = workbook.createCellStyle();
	cellStyle2.setBorderBottom(CellStyle.BORDER_THIN); // 테두리 가늘게
	cellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
	cellStyle2.setBorderRight(CellStyle.BORDER_THIN);
	cellStyle2.setBorderTop(CellStyle.BORDER_THIN);
	cellStyle2.setAlignment(CellStyle.ALIGN_LEFT); // 가운데 정렬 Font font = wb.createFont();
	cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

	cellStyle2.setWrapText(true);

	return cellStyle2;
    }

    // partListType (M: 기구 E: 기구 + 전자소자)
    public String createExcelPartList(String partOid, String partListType) throws Exception {

	String dUrl = "";
	if (partListType.equals("M")) {
	    dUrl = searchPartListM(partOid);
	} else {
	    dUrl = searchPartListE(partOid);
	}

	return dUrl;
    }

    public String searchPartListM(String partOid) throws Exception {
	PartExtDao dao = new PartExtDao();
	PartListDTO dto = dao.searchPartList(partOid);

	int maxLevel = dto.getMaxLevel();
	Kogger.debug(getClass(), "maxLevel==>" + maxLevel);

	if (maxLevel < 3)
	    maxLevel = 3;

	int lvlLength = maxLevel + 1;

	String downUrl = ""; // BOMList.xls
	String savePath = "";

	String pageName = "PartList";

	GregorianCalendar gc = new GregorianCalendar();
	long milis = gc.getTimeInMillis();

	String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	String folder = sWtHome + "/temp/download/";

	savePath = folder + pageName + "_" + Long.toString(milis) + ".xls";

	downUrl = folder + pageName + "_" + Long.toString(milis) + ".xls";

	// String folder = "D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/bom/tmp/";
	// savePath = folder + pageName + "_" + Long.toString(milis) + ".xls";

	// downUrl = "/plm/extcore/jsp/bom/tmp/" + pageName + "_" + Long.toString(milis) + ".xls";

	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet(pageName);
	workbook.setSheetName(0, pageName);

	HSSFPrintSetup hps = sheet.getPrintSetup();
	sheet.setAutobreaks(true);
	hps.setPaperSize((short) 8);
	hps.setLandscape(true);
	hps.setFitWidth((short) 1);
	// hps.setFitHeight((short) 0);

	HSSFFont fontDefault = workbook.createFont();
	fontDefault.setFontHeightInPoints((short) 10);
	fontDefault.setFontName("굴림체");
	fontDefault.setColor(IndexedColors.BLACK.getIndex());

	HSSFCellStyle headerStyle = getHeaderStyle(workbook);
	HSSFCellStyle headerStyle2 = getHeaderStyle2(workbook);
	HSSFCellStyle dataStyle = getDataStyle(workbook);
	HSSFCellStyle dataStyleLeft = getDataStyle2(workbook);

	HSSFRow row = sheet.createRow((short) 0);
	row.setHeight((short) 256);

	int irow0 = 0;
	int istart = 0;
	int iend = 0;

	// 프로젝트 No Header
	createCelltoExcel(sheet, row, irow0, headerStyle, "Project No");
	irow0++;
	for (int i = 1; i <= lvlLength; i++) {
	    createCelltoExcel(sheet, row, irow0, headerStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 0, 0, 0, irow0 - 1);

	// 프로젝트 Name Header
	createCelltoExcel(sheet, row, irow0, headerStyle, "Project Name");

	irow0++;
	istart = irow0;
	for (int hl = istart; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow0, headerStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 0, 0, istart - 1, irow0 - 1);

	// 제품구분 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02578"));// 제품구분
	irow0++;

	// 적용차종 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09081"));// 적용차종
	irow0++;

	// SOP Header
	createCelltoExcel(sheet, row, irow0, headerStyle, "SOP");
	irow0++;

	// 예상수량 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09081") + " \n ("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09444") + ")");// 예상수량 \n (천개/년)
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "1" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 1년차 01179
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "2" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 2년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "3" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 3년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "4" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 4년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "5" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 5년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "6" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 6년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03151"));// 합계
	irow0++;

	// 개발담당자 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09445"));// 개발담당자
	irow0++;

	// 개발담당부서 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09446"));// 개발 담당 부서
	irow0++;

	// =======================================================================================
	row = sheet.createRow((short) 1);

	irow0 = 0;
	istart = 0;
	iend = 0;

	// 프로젝트 No Header
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectNo(), ""));
	irow0++;
	for (int i = 1; i <= lvlLength; i++) {
	    createCelltoExcel(sheet, row, irow0, headerStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 1, 1, 0, irow0 - 1);

	// 프로젝트 No Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectName(), ""));
	irow0++;
	istart = irow0;
	for (int hl = istart; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow0, dataStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 1, 1, istart - 1, irow0 - 1);

	// 제품구분 Data getPartClazNameKr()
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getPartClazNameKr(), ""));
	irow0++;

	// 적용차종 Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectApplyCarType(), ""));
	irow0++;

	// SOP Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectSOP(), ""));
	irow0++;

	// 예상수량 data
	istart = irow0;
	createCelltoExcel(sheet, row, irow0, headerStyle, "");
	irow0++;
	setCellMerge(sheet, 0, 1, istart, istart);

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect1Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect2Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect3Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect4Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect5Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect6Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpectSumQty(), ""));
	irow0++;

	// 개발담당자 Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectDevOwner(), ""));
	irow0++;

	// 개발담당부서 Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectDevDept(), ""));
	irow0++;

	// 헤더1
	// ---------------------------------------------------------------------------------------
	row = sheet.createRow((short) 5);

	int irow5 = 0;
	istart = 0;
	iend = 0;
	// No
	createCelltoExcel(sheet, row, irow5, headerStyle, "No");
	irow5++;

	// Level

	createCelltoExcel(sheet, row, irow5, headerStyle, "Level");
	istart = irow5;
	irow5++;
	for (int hl = istart; hl < (istart + maxLevel); hl++) {
	    Kogger.debug(getClass(), "Level==>" + irow5);
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	Kogger.debug(getClass(), "istart==>" + istart);
	Kogger.debug(getClass(), "iend==>" + iend);
	setCellMerge(sheet, 5, 5, istart, iend);

	// 제 / 부품 내역
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09447"));// 제 / 부품 내역
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 단품 / Ass'Y품 구분
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "07266") + " /\n Ass'Y"
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09448"));// 단품 /\n Ass'Y품 구분
	irow5++;

	// 제품 SIZE
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02536") + " SIZE");// 제품 SIZE
	irow5++;

	// U/S
	createCelltoExcel(sheet, row, irow5, headerStyle, "U/S");
	irow5++;

	// 시작금형(설비) 내역
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09449"));// 시작금형(설비) 내역
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 양산금형(설비) 내역
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09450"));// 양산금형(설비) 내역
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 양산 생산조건
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09451"));// 양산 생산조건
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 1); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// Material
	createCelltoExcel(sheet, row, irow5, headerStyle, "Material");
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 2); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 제품중량[g/EA]
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02619") + "[g/EA]");// 제품중량[g/EA]
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 2); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 생산처
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01791"));// 생산처
	irow5++;

	// 납품처
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09452"));// 납품처
	irow5++;

	// 포장물류비(원)
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09453") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09454") + ")");// 포장물류비\n(원)
	irow5++;

	// 개발 구분(신규/양산)
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "00583") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09455") + ")");// 개발 구분\n(신규/양산)
	irow5++;

	// 임가공비/구매품 단가
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09456") + "\n/\n"
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09457"));// 임가공비\n/\n구매품 단가
	irow5++;

	// 비 고(특이사항, 신규 원재로 단가 등)
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01632") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03350") + ",\n"
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09458") + ")");// 비 고\n(특이사항,\n신규 원재료
	                                                                                                              // 단가 등)
	irow5++;
	// ---------------------------------------------------------------------------------------

	// 헤더2
	// ---------------------------------------------------------------------------------------
	row = sheet.createRow((short) 6);
	row.setHeight((short) (256 * 3 + 50));

	irow5 = 0;
	istart = 0;
	iend = 0;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	for (int hl = 0; hl < lvlLength; hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle2, Integer.toString(hl));
	    irow5++;
	}

	// 품명
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03021"));// 품명
	irow5++;

	// 품번
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03022"));// 품번
	irow5++;

	// 전산품번
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09459"));// 전산품번
	irow5++;

	// 협력사 품번
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09460"));// 협력사 품번
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	// Die No.
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Die No.");
	irow5++;

	// C/V
	createCelltoExcel(sheet, row, irow5, headerStyle2, "C/V");
	irow5++;

	// 금형제작구분(외주/사내)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09461") + " \n ("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09462") + ")");// 금형제작구분 \n (외주/사내)
	irow5++;

	// 투자비(천원)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09463") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09464") + ")");// 투자비\n(천원)
	irow5++;

	// Die No.
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Die No.");
	irow5++;

	// C/V
	createCelltoExcel(sheet, row, irow5, headerStyle2, "C/V");
	irow5++;

	// 금형제작구분(외주/사내)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09461") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09462") + ")");// 금형제작구분\n(외주/사내)
	irow5++;

	// 투자비(천원)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09463") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09464") + ")");// 투자비\n(천원)
	irow5++;

	// 설비Ton
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01874") + "Ton");// 설비Ton
	irow5++;

	// C/T(SPM)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "C/T\n(SPM)");
	irow5++;

	// Grade
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Grade");
	irow5++;

	// Finish(Color)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Finish\n(Color)");
	irow5++;

	// Maker
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Maker");
	irow5++;

	// 부품
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "04700"));// 부품
	irow5++;

	// Scrap
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Scrap");
	irow5++;

	// Total
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Total");
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	// ---------------------------------------------------------------------------------------

	// 데이터 PartListDTO dto
	// ---------------------------------------------------------------------------------------

	List<PartListItemDTO> itemDtoList = dto.getItemList();

	for (int i = 0; i < itemDtoList.size(); i++) {
	    PartListItemDTO item = (PartListItemDTO) itemDtoList.get(i);

	    row = sheet.createRow((short) (i + 7));
	    irow5 = 0;

	    createCelltoExcel(sheet, row, irow5, dataStyle, Integer.toString((i + 1)));
	    irow5++;
	    String lvl = item.getLvl();

	    for (int j = 0; j < lvlLength; j++) {
		if (lvl.equals(Integer.toString(j))) {

		    createCelltoExcel(sheet, row, irow5, dataStyle, lvl);
		} else {
		    createCelltoExcel(sheet, row, irow5, dataStyle, "");
		}
		irow5++;
	    }

	    // 품명
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartName(), ""));
	    irow5++;

	    // 품번 [ 도번
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartCombineNo(), ""));
	    irow5++;

	    // 전산품번 [ WTPartMaster
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartNo(), ""));
	    irow5++;

	    // 협력사 품번 [ 제외
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartSpManufPartNo(), ""));
	    irow5++;

	    // 단품 / Ass'y품 구분 [ Claz
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartClassificType(), ""));
	    irow5++;

	    // 제품 SIZE [ 사양
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpProdSize(), ""));
	    irow5++;

	    // U/S [ BOM
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartUS(), ""));
	    irow5++;

	    // 시작 금형 > Die No
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartStartDieNo(), ""));
	    irow5++;

	    // 시작 금형 > C/V cavity : spCavityStd
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartStartCV(), ""));
	    irow5++;

	    // 시작 금형 > 사급구분(외주/사내) : spMContractSAt
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartStartSpMContractSAt(), ""));
	    irow5++;

	    // 시작 금형 > 투자비(천원) : sap?
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getStartInvestMoney(), ""));
	    irow5++;

	    // 양산 금형 > Die No
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdDieNo(), ""));
	    irow5++;

	    // 양산 금형 > C/V cavity : spCavityStd
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdCV(), ""));
	    irow5++;

	    // 양산 금형 > 사급구분(외주/사내) : spMContractSAt
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdSpMContractSAt(), ""));
	    irow5++;

	    // 양산 금형 > 투자비(천원) : sap?
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getProdInvestMoney(), ""));
	    irow5++;

	    // 양산 생산조건 > 설비 Ton
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdConditionEquipTon(), ""));
	    irow5++;

	    // 양산 생산조건 > C/T(SPM) - 사양 SPM( C/T ) : spObjectiveCT
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdConditionCTSPM(), ""));
	    irow5++;

	    // Material > Grade
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartMaterialGrade(), ""));
	    irow5++;

	    // Material > Finish(Color)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartMaterialFinishColor(), ""));
	    irow5++;

	    // Material > Maker
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartMaterialMaker(), ""));
	    irow5++;

	    // 제품중량
	    // 부품 partProdWeightPartNet;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdWeightPartNet(), ""));
	    irow5++;

	    // Scrap partProdWeightScrap;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdWeightScrap(), ""));
	    irow5++;

	    // Total partProdWeightTotal;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdWeightTotal(), ""));
	    irow5++;

	    // 생산처(?)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProductionWhere(), ""));
	    irow5++;

	    // 납품처( 없어짐... 고객처가 맞을 듯 )
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartSupplyContract(), ""));
	    irow5++;

	    // 포장물류비(원) [ 삭제
	    createCelltoExcel(sheet, row, irow5, dataStyle, "");
	    irow5++;

	    // 개발 구분(신규/양산)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartDevLevel(), ""));
	    irow5++;

	    // 임가공비 / 구매품단가 [ 삭제
	    createCelltoExcel(sheet, row, irow5, dataStyle, "");
	    irow5++;

	    // 비고 ( 특이사항, 신규 원재료 단가 등) [ 삭제
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartSpManufPartNo(), ""));
	    irow5++;
	}
	// ---------------------------------------------------------------------------------------

	int nCnt = 0;
	sheet.setColumnWidth(nCnt, 1024);
	Kogger.debug(getClass(), "sheet.getColumnWidth(" + nCnt + ")==>" + sheet.getColumnWidth(nCnt));
	nCnt++;

	for (int k = 0; k < lvlLength; k++) {
	    sheet.setColumnWidth(nCnt, 694);
	    nCnt++;
	}

	sheet.setColumnWidth(nCnt, 5814);
	nCnt++;

	for (int k = 0; k < 3; k++) {
	    sheet.setColumnWidth(nCnt, 2998);
	    nCnt++;
	}

	sheet.setColumnWidth(nCnt, 5449);
	nCnt++;

	sheet.setColumnWidth(nCnt, 2560);
	nCnt++;

	sheet.setColumnWidth(nCnt, 2084);
	nCnt++;

	sheet.setColumnWidth(nCnt, 4388);
	nCnt++;

	for (int k = 0; k < 20; k++) {
	    sheet.setColumnWidth(nCnt, 3510);
	    nCnt++;
	}

	sheet.setColumnWidth(nCnt, 8813);

	FileOutputStream fileOutput = new FileOutputStream(savePath);
	workbook.write(fileOutput);

	fileOutput.close();

	return downUrl;

    }

    public String searchPartListE(String partOid) throws Exception {
	PartExtDao dao = new PartExtDao();
	PartListDTO dto = dao.searchPartList(partOid);

	int maxLevel = dto.getMaxLevel();

	if (maxLevel < 3)
	    maxLevel = 3;

	Kogger.debug(getClass(), "maxLevel==>" + maxLevel);
	int lvlLength = maxLevel + 1;

	String downUrl = ""; // BOMList.xls
	String savePath = "";

	String pageName = "PartList";

	GregorianCalendar gc = new GregorianCalendar();
	long milis = gc.getTimeInMillis();

	String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	String folder = sWtHome + "/temp/download/";

	savePath = folder + pageName + "_" + Long.toString(milis) + ".xls";

	downUrl = folder + pageName + "_" + Long.toString(milis) + ".xls";

	// String folder = "D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/bom/tmp/";
	// savePath = folder + pageName + "_" + Long.toString(milis) + ".xls";
	// downUrl = "/plm/extcore/jsp/bom/tmp/" + pageName + "_" + Long.toString(milis) + ".xls";

	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet(pageName);
	workbook.setSheetName(0, pageName);

	HSSFPrintSetup hps = sheet.getPrintSetup();
	sheet.setAutobreaks(true);
	hps.setPaperSize((short) 8);
	hps.setLandscape(true);
	hps.setFitWidth((short) 1);
	// hps.setFitHeight((short) 0);

	HSSFFont fontDefault = workbook.createFont();
	fontDefault.setFontHeightInPoints((short) 10);
	fontDefault.setFontName("굴림체");
	fontDefault.setColor(IndexedColors.BLACK.getIndex());

	HSSFCellStyle headerStyle = getHeaderStyle(workbook);
	HSSFCellStyle headerStyle2 = getHeaderStyle2(workbook);
	HSSFCellStyle dataStyle = getDataStyle(workbook);
	HSSFCellStyle dataStyleLeft = getDataStyle2(workbook);

	HSSFRow row = sheet.createRow((short) 0);

	int irow0 = 0;
	int istart = 0;
	int iend = 0;

	for (int k = 0; k < (56 + lvlLength); k++) {
	    sheet.autoSizeColumn(k);
	    sheet.setColumnWidth(k, (sheet.getColumnWidth(k)) + 512);
	}

	// 프로젝트 No Header
	createCelltoExcel(sheet, row, irow0, headerStyle, "Project No");
	irow0++;
	for (int i = 1; i <= lvlLength; i++) {
	    createCelltoExcel(sheet, row, irow0, headerStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 0, 0, 0, irow0 - 1);

	// 프로젝트 Name Header
	createCelltoExcel(sheet, row, irow0, headerStyle, "Project Name");
	irow0++;
	istart = irow0;
	for (int hl = istart; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow0, headerStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 0, 0, istart - 1, irow0 - 1);

	// 제품구분 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02578"));// 제품구분
	irow0++;

	// 적용차종 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09081"));// 적용차종
	irow0++;

	// SOP Header
	createCelltoExcel(sheet, row, irow0, headerStyle, "SOP");
	irow0++;

	// 예상수량 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09081") + " \n ("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09444") + ")");// 예상수량\n(천개/년)
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "1" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 1년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "2" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 2년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "3" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 3년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "4" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 4년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "5" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 5년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        "6" + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01179"));// 6년차
	irow0++;

	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03151"));// 합계
	irow0++;

	// 개발담당자 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09445"));// 개발담당자
	irow0++;

	// 개발담당부서 Header
	createCelltoExcel(sheet, row, irow0, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09446"));// 개발 담당 부서
	irow0++;

	// =======================================================================================
	row = sheet.createRow((short) 1);

	irow0 = 0;
	istart = 0;
	iend = 0;

	// 프로젝트 No Header
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectNo(), ""));
	irow0++;
	for (int i = 1; i <= lvlLength; i++) {
	    createCelltoExcel(sheet, row, irow0, headerStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 1, 1, 0, irow0 - 1);

	// 프로젝트 No Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectName(), ""));
	irow0++;
	istart = irow0;
	for (int hl = istart; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow0, dataStyle, "");
	    irow0++;
	}
	setCellMerge(sheet, 1, 1, istart - 1, irow0 - 1);

	// 제품구분 Data getPartClazNameKr()
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getPartClazNameKr(), ""));
	irow0++;

	// 적용차종 Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectApplyCarType(), ""));
	irow0++;

	// SOP Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectSOP(), ""));
	irow0++;

	// 예상수량 data
	istart = irow0;
	createCelltoExcel(sheet, row, irow0, headerStyle, "");
	irow0++;
	setCellMerge(sheet, 0, 1, istart, istart);

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect1Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect2Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect3Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect4Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect5Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpect6Qty(), ""));
	irow0++;

	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectExpectSumQty(), ""));
	irow0++;

	// 개발담당자 Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectDevHwOwner(), ""));
	irow0++;

	// 개발담당부서 Data
	createCelltoExcel(sheet, row, irow0, dataStyle, KETStringUtil.nvl(dto.getProjectDevHwDept(), ""));
	irow0++;

	// 헤더1
	// ---------------------------------------------------------------------------------------
	row = sheet.createRow((short) 5);

	int irow5 = 0;
	istart = 0;
	iend = 0;
	// No
	createCelltoExcel(sheet, row, irow5, headerStyle, "No");
	irow5++;

	// Level

	createCelltoExcel(sheet, row, irow5, headerStyle, "Level");
	istart = irow5;
	irow5++;
	for (int hl = istart; hl < (istart + maxLevel); hl++) {
	    Kogger.debug(getClass(), "Level==>" + irow5);
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	Kogger.debug(getClass(), "istart==>" + istart);
	Kogger.debug(getClass(), "iend==>" + iend);
	setCellMerge(sheet, 5, 5, istart, iend);

	// 제 / 부품 내역
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09447"));// 제 / 부품 내역
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// Reference No.
	createCelltoExcel(sheet, row, irow5, headerStyle, "Reference No.");
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 1); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 단품 / Ass'Y품 구분
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "07266") + " /\n Ass'Y"
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09448"));// 단품 /\nAss'Y품 구분
	irow5++;

	// 제품 SIZE
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02536") + " SIZE");// 제품 SIZE
	irow5++;

	// U/S
	createCelltoExcel(sheet, row, irow5, headerStyle, "U/S");
	irow5++;

	// 시작금형(설비) 내역
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09449"));// 시작금형(설비) 내역
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 양산금형(설비) 내역
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09450"));// 양산금형(설비) 내역
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 3); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 양산 생산조건
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09451"));// 양산 생산조건
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 1); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// Material
	createCelltoExcel(sheet, row, irow5, headerStyle, "Material");
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 2); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 제품중량[g/EA]
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02619") + "[g/EA]");// 제품중량[g/EA]
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 2); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// Specification
	createCelltoExcel(sheet, row, irow5, headerStyle, "Specification");
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 6); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// MSL
	createCelltoExcel(sheet, row, irow5, headerStyle, "MSL");
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 2); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// ESD
	createCelltoExcel(sheet, row, irow5, headerStyle, "ESD");
	irow5++;
	istart = irow5;
	for (int hl = irow5; hl < (istart + 1); hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle, "");
	    irow5++;
	}
	iend = irow5 - 1;
	setCellMerge(sheet, 5, 5, istart - 1, iend);

	// 생산처
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01791"));// 생산처
	irow5++;

	// 납품처
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09452"));// 납품처
	irow5++;

	// 포장물류비(원)
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09453") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09454") + ")");// 포장물류비\n(원)
	irow5++;

	// 개발 구분(신규/양산)
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "00583") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09455") + ")");// 개발 구분\n(신규/양산)
	irow5++;

	// 임가공비/구매품 단가
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09456") + "\n/\n"
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09457"));// 임가공비\n/\n구매품 단가
	irow5++;

	// 비 고(특이사항, 신규 원재로 단가 등)
	createCelltoExcel(sheet, row, irow5, headerStyle,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01632") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03350") + ",\n"
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09458") + ")");// 비 고\n(특이사항,\n신규 원재로
														      // 단가 등)
	irow5++;
	// ---------------------------------------------------------------------------------------

	// 헤더2
	// ---------------------------------------------------------------------------------------
	row = sheet.createRow((short) 6);

	irow5 = 0;
	istart = 0;
	iend = 0;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	for (int hl = 0; hl < lvlLength; hl++) {
	    createCelltoExcel(sheet, row, irow5, headerStyle2, Integer.toString(hl));
	    irow5++;
	}

	// 품명
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03021"));// 품명
	irow5++;

	// 품번
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03022"));// 품번
	irow5++;

	// 전산품번
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09459"));// 전산품번
	irow5++;

	// 협력사 품번
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09460"));// 협력사 품번
	irow5++;

	// TOP
	createCelltoExcel(sheet, row, irow5, headerStyle2, "TOP");
	irow5++;

	// BOTTOM
	createCelltoExcel(sheet, row, irow5, headerStyle2, "BOTTOM");
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	// Die No.
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Die No.");
	irow5++;

	// C/V
	createCelltoExcel(sheet, row, irow5, headerStyle2, "C/V");
	irow5++;

	// 금형제작구분(외주/사내)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09461") + " \n ("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09462") + ")");// 금형제작구분\n(외주/사내)
	irow5++;

	// 투자비(천원)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09463") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09464") + ")");// 투자비\n(천원)
	irow5++;

	// Die No.
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Die No.");
	irow5++;

	// C/V
	createCelltoExcel(sheet, row, irow5, headerStyle2, "C/V");
	irow5++;

	// 금형제작구분(외주/사내)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09461") + " \n ("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09462") + ")");// 금형제작구분\n(외주/사내)
	irow5++;

	// 투자비(천원)
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09463") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09464") + ")");// 투자비\n(천원)
	irow5++;

	// 설비Ton
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01874") + "Ton");// 설비Ton
	irow5++;

	// C/T(SPM)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "C/T\n(SPM)");
	irow5++;

	// Grade
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Grade");
	irow5++;

	// Finish(Color)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Finish\n(Color)");
	irow5++;

	// Maker
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Maker");
	irow5++;

	// 부품
	createCelltoExcel(sheet, row, irow5, headerStyle2,
	        KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09463") + "\n("
	                + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09464") + ")");// 투자비\n(천원)
	irow5++;

	// Scrap
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Scrap");
	irow5++;

	// Total
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Total");
	irow5++;

	// Value
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Value ");
	irow5++;

	// Volt
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Volt");
	irow5++;

	// Watt
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Watt");
	irow5++;

	// Tolerance
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Tolerance");
	irow5++;

	// Temp.(℃)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Temp.(℃)");
	irow5++;

	// Package(mm)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Package(mm)");
	irow5++;

	// Packing
	createCelltoExcel(sheet, row, irow5, headerStyle2, "Packing");
	irow5++;

	// 1
	createCelltoExcel(sheet, row, irow5, headerStyle2, "1");
	irow5++;

	// 2
	createCelltoExcel(sheet, row, irow5, headerStyle2, "2");
	irow5++;

	// 3
	createCelltoExcel(sheet, row, irow5, headerStyle2, "3");
	irow5++;

	// HBM(V)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "HBM(V)");
	irow5++;

	// SDM(V)
	createCelltoExcel(sheet, row, irow5, headerStyle2, "SDM(V)");
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	createCelltoExcel(sheet, row, irow5, headerStyle2, "");
	setCellMerge(sheet, 5, 6, irow5, irow5);
	irow5++;

	// ---------------------------------------------------------------------------------------

	// 데이터
	// ---------------------------------------------------------------------------------------
	List<PartListItemDTO> itemDtoList = dto.getItemList();

	for (int i = 0; i < itemDtoList.size(); i++) {
	    PartListItemDTO item = (PartListItemDTO) itemDtoList.get(i);

	    row = sheet.createRow((short) (i + 7));
	    irow5 = 0;

	    createCelltoExcel(sheet, row, irow5, dataStyle, Integer.toString((i + 1)));
	    irow5++;
	    String lvl = item.getLvl();

	    for (int j = 0; j < lvlLength; j++) {
		if (lvl.equals(Integer.toString(j))) {

		    createCelltoExcel(sheet, row, irow5, dataStyle, lvl);
		} else {
		    createCelltoExcel(sheet, row, irow5, dataStyle, "");
		}
		irow5++;
	    }

	    // 품명
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartName(), ""));
	    irow5++;

	    // 품번 [ 도번
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartCombineNo(), ""));
	    irow5++;

	    // 전산품번 [ WTPartMaster
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartNo(), ""));
	    irow5++;

	    // 협력사 품번 [ 제외
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartSpManufPartNo(), ""));
	    irow5++;

	    // BOM 정보(TOP)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getReferenceTop(), ""));
	    irow5++;

	    // BOM 정보(BOTTOM)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getReferenceBottom(), ""));
	    irow5++;

	    // 단품 / Ass'y품 구분 [ Claz
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartClassificType(), ""));
	    irow5++;

	    // 제품 SIZE [ 사양
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpProdSize(), ""));
	    irow5++;

	    // U/S [ BOM
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartUS(), ""));
	    irow5++;

	    // 시작 금형 > Die No
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartStartDieNo(), ""));
	    irow5++;

	    // 시작 금형 > C/V cavity : spCavityStd
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartStartCV(), ""));
	    irow5++;

	    // 시작 금형 > 사급구분(외주/사내) : spMContractSAt
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartStartSpMContractSAt(), ""));
	    irow5++;

	    // 시작 금형 > 투자비(천원) : sap?
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getStartInvestMoney(), ""));
	    irow5++;

	    // 양산 금형 > Die No
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdDieNo(), ""));
	    irow5++;

	    // 양산 금형 > C/V cavity : spCavityStd
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdCV(), ""));
	    irow5++;

	    // 양산 금형 > 사급구분(외주/사내) : spMContractSAt
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdSpMContractSAt(), ""));
	    irow5++;

	    // 양산 금형 > 투자비(천원) : sap?
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getProdInvestMoney(), ""));
	    irow5++;

	    // 양산 생산조건 > 설비 Ton
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdConditionEquipTon(), ""));
	    irow5++;

	    // 양산 생산조건 > C/T(SPM) - 사양 SPM( C/T ) : spObjectiveCT
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdConditionCTSPM(), ""));
	    irow5++;

	    // Material > Grade
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartMaterialGrade(), ""));
	    irow5++;

	    // Material > Finish(Color)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartMaterialFinishColor(), ""));
	    irow5++;

	    // Material > Maker
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartMaterialMaker(), ""));
	    irow5++;

	    // 제품중량
	    // 부품 partProdWeightPartNet;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdWeightPartNet(), ""));
	    irow5++;

	    // Scrap partProdWeightScrap;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdWeightScrap(), ""));
	    irow5++;

	    // Total partProdWeightTotal;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProdWeightTotal(), ""));
	    irow5++;

	    // Specification ==============
	    // Value : spValueValue? specValue;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpecValue(), ""));
	    irow5++;
	    // Volt : spRatedVoltage? specVolt;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpecVolt(), ""));
	    irow5++;
	    // Watt : spWatt specWatt;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpecWatt(), ""));
	    irow5++;
	    // Tolerance : spTolerance specTolerance;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpecTolerance(), ""));
	    irow5++;
	    // Temp.(℃) : spOperTemp specTemp;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpecTemp(), ""));
	    irow5++;
	    // Package(mm) : spPackageType specPackage;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpecPackage(), ""));
	    irow5++;
	    // Packing : spPackageSpec? specPacking;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSpecPacking(), ""));
	    irow5++;

	    // MSL ==============//
	    // msl1; // spMSL
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getMsl1(), ""));
	    irow5++;
	    // msl2;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getMsl2(), ""));
	    irow5++;
	    // msl3;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getMsl3(), ""));
	    irow5++;

	    // ESD ============== ESD spESD
	    // HBM(V) hbm;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getHbm(), ""));
	    irow5++;
	    // SDM(V) sdm;
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getSdm(), ""));
	    irow5++;

	    // 생산처(?)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartProductionWhere(), ""));
	    irow5++;

	    // 납품처( 없어짐... 고객처가 맞을 듯 )
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartSupplyContract(), ""));
	    irow5++;

	    // 포장물류비(원) [ 삭제
	    createCelltoExcel(sheet, row, irow5, dataStyle, "");
	    irow5++;

	    // 개발 구분(신규/양산)
	    createCelltoExcel(sheet, row, irow5, dataStyle, KETStringUtil.nvl(item.getPartDevLevel(), ""));
	    irow5++;

	    // 임가공비 / 구매품단가 [ 삭제
	    createCelltoExcel(sheet, row, irow5, dataStyle, "");
	    irow5++;

	    // 비고 ( 특이사항, 신규 원재료 단가 등) [ 삭제
	    createCelltoExcel(sheet, row, irow5, dataStyleLeft, KETStringUtil.nvl(item.getPartSpManufPartNo(), ""));
	    irow5++;
	}
	// ---------------------------------------------------------------------------------------

	int nCnt = 0;
	sheet.setColumnWidth(nCnt, 1024);
	Kogger.debug(getClass(), "sheet.getColumnWidth(" + nCnt + ")==>" + sheet.getColumnWidth(nCnt));
	nCnt++;

	for (int k = 0; k < lvlLength; k++) {
	    sheet.setColumnWidth(nCnt, 694);
	    nCnt++;
	}

	sheet.setColumnWidth(nCnt, 5814);
	nCnt++;

	for (int k = 0; k < 3; k++) {
	    sheet.setColumnWidth(nCnt, 2998);
	    nCnt++;
	}

	sheet.setColumnWidth(nCnt, 5449);
	nCnt++;

	sheet.setColumnWidth(nCnt, 2560);
	nCnt++;

	sheet.setColumnWidth(nCnt, 2084);
	nCnt++;

	sheet.setColumnWidth(nCnt, 4388);
	nCnt++;

	for (int k = 0; k < 34; k++) {
	    sheet.setColumnWidth(nCnt, 3510);
	    nCnt++;
	}

	sheet.setColumnWidth(nCnt, 8813);

	FileOutputStream fileOutput = new FileOutputStream(savePath);
	workbook.write(fileOutput);

	fileOutput.close();

	return downUrl;

    }

    public void excelTest() throws FileNotFoundException, IOException {
	File template = new File("D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/part/base/PartList_Template.xls");

	HSSFSheet sheet = null;
	HSSFRow row = null;
	HSSFCell cell = null;

	if (template.isFile()) {

	    POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(template));
	    HSSFWorkbook wb = new HSSFWorkbook(filein);

	    sheet = wb.getSheetAt(0);
	    row = sheet.getRow(8);

	    for (int j = 0; j < 34; j++) {

		// cell 을 가져온다.

		Kogger.debug(getClass(), "[" + j + "]==>" + sheet.getColumnWidth(j));
	    }

	}

    }

    public static void main(String[] args) throws Exception {

	// TODO Auto-generated method stub

	PartListExcelDao dao = new PartListExcelDao();
	// String durl = "";

	// String oid = "wt.part.WTPart:100002416583";
	// durl = dao.createExcelPartList(oid, "M");

	// Kogger.debug(getClass(), durl);

	dao.excelTest();

	// windchill ext.ket.part.base.service.internal.PartListExcelDao E
	// windchill ext.ket.part.base.service.internal.PartListExcelDao M
    }
}
