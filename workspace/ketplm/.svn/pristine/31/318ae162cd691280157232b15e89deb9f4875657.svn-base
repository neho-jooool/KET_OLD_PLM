package ext.ket.dashboard.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import ext.ket.shared.log.Kogger;

public class KETDashBoardExcelUtil {

    public KETDashBoardExcelUtil() {
	// TODO Auto-generated constructor stub
    }

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

    public String createExcelFile(ArrayList keyList, ArrayList dataList, String pageName, HttpServletResponse response) throws IOException {

	String downUrl = ""; // BOMList.xls
	String savePath = "";
	String fileName = "";

	GregorianCalendar gc = new GregorianCalendar();
	long milis = gc.getTimeInMillis();

	String folder = "D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/bom/tmp/";
	fileName = pageName + "_" + Long.toString(milis) + ".xls";
	savePath = folder + pageName + "_" + Long.toString(milis) + ".xls";

	response.setHeader("Content-Type", "application/vnd.ms-excel;charset=EUC-KR");
	response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

	downUrl = "/plm/extcore/jsp/bom/tmp/" + pageName + "_" + Long.toString(milis) + ".xls";

	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet(pageName);
	workbook.setSheetName(0, pageName);

	HSSFCellStyle cellStyle1 = workbook.createCellStyle();
	cellStyle1.setFillForegroundColor(HSSFColor.YELLOW.index);
	cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	cellStyle1.setBorderBottom(CellStyle.BORDER_DOUBLE);
	cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
	cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
	cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
	cellStyle1.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬 Font font = wb.createFont();

	HSSFFont fontDefault = workbook.createFont();
	fontDefault.setFontHeightInPoints((short) 10);
	fontDefault.setFontName("굴림체");
	fontDefault.setColor(IndexedColors.BLACK.getIndex());

	if (dataList != null) {
	    // ROWS DATA LOOP
	    for (int i = 0; i < dataList.size(); i++) {
		Hashtable data = (Hashtable) dataList.get(i);

		HSSFRow row = sheet.createRow((short) i);

		if (i == 0) // Header
		{
		    // COLUMN DATA LOOP
		    for (int j = 0; j < keyList.size(); j++) {
			String key = (String) keyList.get(j);
			String tmp = (String) data.get(key);
			tmp = tmp.trim();

			HSSFCell cell1 = row.createCell((short) j);

			cellStyle1.setFont(fontDefault);
			cell1.setCellStyle(cellStyle1);
			cell1.setCellValue(tmp);

		    }
		} else // Data
		{
		    // COLUMN DATA LOOP

		    for (int j = 0; j < keyList.size(); j++) {

			String key = (String) keyList.get(j);
			String tmp = (String) data.get(key);

			HSSFCellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2.setBorderBottom(CellStyle.BORDER_THIN); // 테두리 두껍게
			cellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle2.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle2.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle2.setAlignment(CellStyle.ALIGN_CENTER); // 가운데 정렬 Font font = wb.createFont();

			HSSFCell cell1 = row.createCell((short) j);

			if (!tmp.equals("") && tmp.indexOf("<font color='#FF0000'>") != -1) { // RED
			    String colorStr = "<font color='#FF0000'>";
			    tmp = tmp.substring(colorStr.length(), tmp.indexOf("</font>"));

			    Kogger.debug(getClass(), "RED==>" + tmp);

			    cellStyle2.setFont(getFont(workbook, "RED"));
			    cell1.setCellStyle(cellStyle2);

			} else if (!tmp.equals("") && tmp.indexOf("<font color='#0000FF'>") != -1) {// BLUE
			    String colorStr = "<font color='#0000FF'>";
			    tmp = tmp.substring(colorStr.length(), tmp.indexOf("</font>"));

			    Kogger.debug(getClass(), "BLUE==>" + tmp);

			    cellStyle2.setFont(getFont(workbook, "BLUE"));
			    cell1.setCellStyle(cellStyle2);

			} else if (!tmp.equals("") && tmp.indexOf("<font color='#00FF00'>") != -1) { // GREEN
			    String colorStr = "<font color='#00FF00'>";
			    tmp = tmp.substring(colorStr.length(), tmp.indexOf("</font>"));

			    Kogger.debug(getClass(), "GREEN==>" + tmp);

			    cellStyle2.setFont(getFont(workbook, "GREEN"));
			    cell1.setCellStyle(cellStyle2);

			} else if (tmp.equals("") || tmp.indexOf("<font color=") < 0) { // BLACK

			    cellStyle2.setFont(fontDefault);
			    cell1.setCellStyle(cellStyle2);
			}

			cell1.setCellValue(tmp);

		    }
		}

	    }
	}

	for (int j = 0; j < keyList.size(); j++) {
	    sheet.autoSizeColumn(j);
	}

	OutputStream fileOutput = null;
	fileOutput = response.getOutputStream();
	workbook.write(fileOutput);

	fileOutput.close();

	return downUrl;
    }

    public HSSFFont getFont(HSSFWorkbook workbook, String color) {
	HSSFFont font = workbook.createFont();

	font.setFontHeightInPoints((short) 10);
	font.setFontName("굴림체");

	if (color.equals("RED"))
	    font.setColor(IndexedColors.RED.getIndex());
	if (color.equals("BLUE"))
	    font.setColor(IndexedColors.BLUE.getIndex());
	if (color.equals("GREEN"))
	    font.setColor(IndexedColors.GREEN.getIndex());
	if (color.equals("BLACK"))
	    font.setColor(IndexedColors.BLACK.getIndex());

	return font;
    }

    public static void main(String[] args) throws IOException {

    }
}