package ext.ket.part.migration;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class DefaultExcelHandler implements ExcelHandle {

    private Workbook wb = null;
    private Sheet sheet = null;
    private int sheetIdx = 0;
    private Row row = null;
    private int rowIdx = 0;
    private Cell cell = null;
    private int cellIdx = 0;

    // ///////////////////////////////////////
    // file 제어
    // ///////////////////////////////////////
    public DefaultExcelHandler(String filepath) throws Exception {
	File file = new File(filepath);
	if (!file.isFile())
	    throw new Exception("## file path error!!");

	wb = WorkbookFactory.create(new FileInputStream(file));
    }

    public DefaultExcelHandler(File file) throws Exception {

	if (file == null || !file.isFile())
	    throw new Exception("## file path error!!");

	wb = WorkbookFactory.create(new FileInputStream(file));
    }

    // ///////////////////////////////////////
    // sheet
    // ///////////////////////////////////////

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#setSheet(int)
     */
    @Override
    public void setSheet(int sheetNo) {
	sheetIdx = sheetNo;
	sheet = wb.getSheetAt(sheetNo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getSheet(int)
     */
    @Override
    public Sheet getSheet(int sheetNo) {
	setSheet(sheetNo);
	return sheet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#nextSheet()
     */
    @Override
    public Sheet nextSheet() {
	sheetIdx = sheetIdx++;
	setSheet(sheetIdx);
	return sheet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getCurrentSheet()
     */
    @Override
    public Sheet getCurrentSheet() {
	if (sheet == null)
	    setSheet(sheetIdx);
	return sheet;
    }

    // ///////////////////////////////////////
    // Row 제어
    // ///////////////////////////////////////

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getSheetLastRowNum()
     */
    @Override
    public int getSheetLastRowNum() {
	return getCurrentSheet().getLastRowNum();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#setRow(int)
     */
    @Override
    public void setRow(int rowNo) {
	rowIdx = rowNo;
	row = getCurrentSheet().getRow(rowNo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getRow(int)
     */
    @Override
    public Row getRow(int rowNo) {
	setRow(rowNo);
	return row;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#nextRow()
     */
    @Override
    public Row nextRow() {
	rowIdx = rowIdx++;
	setRow(rowIdx);
	return row;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getCurrentRow()
     */
    @Override
    public Row getCurrentRow() {
	if (row == null)
	    setRow(rowIdx);
	return row;
    }

    // ///////////////////////////////////////
    // cell 제어
    // ///////////////////////////////////////

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#setCell(int)
     */
    @Override
    public void setCell(int cellNo) {
	cellIdx = cellNo;
	cell = getCurrentRow().getCell(cellIdx);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getCell(int)
     */
    @Override
    public Cell getCell(int cellNo) {
	setCell(cellNo);
	return cell;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#nextCell()
     */
    @Override
    public Cell nextCell() {
	cellIdx = cellIdx++;
	setCell(cellIdx);
	return cell;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getCurrentCol()
     */
    @Override
    public Cell getCurrentCol() {
	if (cell == null)
	    setCell(cellIdx);
	return cell;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getStrValue(int)
     */
    @Override
    public String getStrValue(int cellNo) {
	setCell(cellNo);
	return getStrValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getStrValue()
     */
    @Override
    public String getStrValue() {
	return checkNull(getStrOrgValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getStrIntValue(int)
     */
    @Override
    public String getStrIntValue(int cellNo) {
	setCell(cellNo);
	return getStrIntValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getStrIntValue()
     */
    @Override
    public String getStrIntValue() {
	String value = null;
	try {

	    value = checkNumNull(String.valueOf(cell.getNumericCellValue()));

	} catch (Exception ne) {

	    try {
		value = checkNumNull(cell.getStringCellValue());
	    } catch (Exception nee) {
	    }
	}

	return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getStrOrgValue(int)
     */
    @Override
    public String getStrOrgValue(int cellNo) {
	setCell(cellNo);
	return getStrOrgValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.part.migration.ExcelHandle#getStrOrgValue()
     */
    @Override
    public String getStrOrgValue() {
	String value = null;
	try {
	    
	    
	    if (cell == null) {

	    } else {
		// 타입별로 내용 읽기
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_FORMULA:
		    value = cell.getCellFormula();
		    break;
		case XSSFCell.CELL_TYPE_NUMERIC:
		    value = String.valueOf(new Double(cell.getNumericCellValue()).intValue());// 실수형 데이터가 정수형으로 나오도록
		    // value=cell.getNumericCellValue()+"";
		    break;
		case XSSFCell.CELL_TYPE_STRING:
		    value = cell.getStringCellValue() + "";
		    break;
		case XSSFCell.CELL_TYPE_BLANK:
		    value = "";
		    break;
		case XSSFCell.CELL_TYPE_ERROR:
		    value = cell.getErrorCellValue() + "";
		    break;
		}
	    }


	    //value = cell.getStringCellValue();

	} catch (Exception ne) {

	    try {

		value = String.valueOf(cell.getNumericCellValue());

	    } catch (Exception nee) {
	    }
	}

	return value;
    }

    private String checkNull(String str) {
	if (str == null)
	    return EMPTY;
	else
	    return str.trim();
    }

    private String checkNumNull(String str) {
	if (str == null)
	    return EMPTY;
	else {
	    str = str.trim();
	    if (str.indexOf(".") != -1)
		return str.substring(0, str.lastIndexOf("."));
	    else
		return str;
	}
    }

    @Override
    public int getNumberOfSheets() {
	// TODO Auto-generated method stub
	return wb.getNumberOfSheets();
    }

    @Override
    public CellStyle getCellStyle() {
	// TODO Auto-generated method stub
	return wb.createCellStyle();
    }

    @Override
    public Workbook getWorkBook() {
	// TODO Auto-generated method stub
	return wb;
    }
}
