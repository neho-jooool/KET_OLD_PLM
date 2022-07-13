package ext.ket.part.base.controller.view;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public abstract class ExcelViewSupport extends AbstractExcelView {

    public HSSFSheet sheet = null;
    public HSSFRow row = null;
    public HSSFCell cell = null;

    public HSSFCellStyle label = null; // 제목 스타일
    public HSSFCellStyle header = null; // 헤더 스타일
    public HSSFCellStyle subHeader = null; // 헤더 스타일

    public HSSFCellStyle centerData = null; // 데이터셀 스타일(중앙정렬) : data
    public HSSFCellStyle leftData = null; // 데이터셀 스타일(왼쪽정렬) : data1
    public HSSFCellStyle rightData = null; // 데이터셀 스타일(오른쪽정렬) : data2
    public HSSFCellStyle outerLeftdata = null; // 데이터셀 스타일 (배경 - 왼쪽정렬) : data3

    private void initialize(HSSFWorkbook wb) throws Exception {

	label = wb.createCellStyle(); // 제목 스타일
	header = wb.createCellStyle(); // 헤더 스타일
	subHeader = wb.createCellStyle(); // 헤더 스타일

	centerData = wb.createCellStyle(); // 데이터셀 스타일(중앙정렬) : data
	leftData = wb.createCellStyle(); // 데이터셀 스타일(왼쪽정렬) : data1
	rightData = wb.createCellStyle(); // 데이터셀 스타일(오른쪽정렬) : data2
	outerLeftdata = wb.createCellStyle(); // 데이터셀 스타일 (배경 - 왼쪽정렬) : data3

//	// 데이터셀 폰트스타일
	HSSFFont dataFont = wb.createFont();
	dataFont.setFontHeightInPoints((short) 9);
	dataFont.setFontName("굴림체"); // 굴림체

	// 중앙정렬
	centerData.setBorderTop(HSSFCellStyle.BORDER_THIN);
	centerData.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	centerData.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	centerData.setBorderRight(HSSFCellStyle.BORDER_THIN);

	centerData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	centerData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	centerData.setFont(dataFont);

	// 좌측정렬
	leftData.setBorderTop(HSSFCellStyle.BORDER_THIN);
	leftData.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	leftData.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	leftData.setBorderRight(HSSFCellStyle.BORDER_THIN);

	leftData.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	leftData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	leftData.setFont(dataFont);
	leftData.setWrapText(true);

	// 우측정렬
	rightData.setBorderTop(HSSFCellStyle.BORDER_THIN);
	rightData.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	rightData.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	rightData.setBorderRight(HSSFCellStyle.BORDER_THIN);

	rightData.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	rightData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	rightData.setFont(dataFont);
    }

    public void setSheet(HSSFWorkbook wb, int startRow) throws Exception {

	initialize(wb);
	sheet = wb.getSheetAt(0);
	row = sheet.getRow((short) startRow);
	cell= getCell(sheet, startRow, 0);
    }

    public HSSFCell nextCell() throws Exception {
	int colIdx = cell.getColumnIndex();
	int rowIdx = cell.getRowIndex();
//	Kogger.debug(getClass(), "ColIdx:" + colIdx);
//	Kogger.debug(getClass(), "RowIdx:" + rowIdx);
	cell = getCell(sheet, rowIdx, colIdx + 1);
	return cell;
    }

    public HSSFCell nextRow() throws Exception {
	int rowIdx = cell.getRowIndex();
//	Kogger.debug(getClass(), "RowIdx:BEFORE:" + rowIdx);
	cell = getCell(sheet, rowIdx + 1, 0);
//	Kogger.debug(getClass(), "RowIdx:AFTER:" + cell.getRowIndex());
	return cell;
    }

    @Override
    protected void setText(HSSFCell cell, String text) {
	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	cell.setCellStyle(centerData);
	cell.setCellValue(text);
    }

    protected void setText(HSSFCell cell, String text, HSSFCellStyle style) {
	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	cell.setCellStyle(style);
	cell.setCellValue(text);
    }

    protected void setNumber(HSSFCell cell, String text) {
	cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	cell.setCellStyle(rightData);
	cell.setCellValue(text);
    }

    protected void setNumber(HSSFCell cell, Integer text) {
	cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	cell.setCellStyle(rightData);
	cell.setCellValue(text);
    }

    protected void setNumber(HSSFCell cell, Double text) {
	cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	cell.setCellStyle(rightData);
	cell.setCellValue(text);
    }

}