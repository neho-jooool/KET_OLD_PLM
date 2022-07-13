package ext.ket.part.migration;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class HssfExcelHandler implements ExcelHandleHs {

    private POIFSFileSystem fs = null;
    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;
    private int sheetIdx = 0;
    private HSSFRow row = null;
    private int rowIdx = 0;
    private HSSFCell cell = null;
    private int cellIdx = 0;

    public static final String EMPTY = "";

    // ///////////////////////////////////////
    // file 제어
    // ///////////////////////////////////////
    
    public HssfExcelHandler(String filepath) throws Exception {
	File file = new File(filepath);
	if (!file.isFile())
	    throw new Exception("## file path error!!");

	fs = new POIFSFileSystem(new FileInputStream(file));
	wb = new HSSFWorkbook(fs);
    }

    public HssfExcelHandler(File file) throws Exception {

	if (file == null || !file.isFile())
	    throw new Exception("## file path error!!");
	fs = new POIFSFileSystem(new FileInputStream(file));
	wb = new HSSFWorkbook(fs);
    }

    // ///////////////////////////////////////
    // sheet
    // ///////////////////////////////////////

    public void setSheet(int sheetNo) {
	sheetIdx = sheetNo;
	sheet = wb.getSheetAt(sheetNo);
    }

    public HSSFSheet getSheet(int sheetNo) {
	setSheet(sheetNo);
	return sheet;
    }

    public HSSFSheet nextSheet() {
	sheetIdx = sheetIdx++;
	setSheet(sheetIdx);
	return sheet;
    }

    public HSSFSheet getCurrentSheet() {
	if (sheet == null)
	    setSheet(sheetIdx);
	return sheet;
    }

    // ///////////////////////////////////////
    // Row 제어
    // ///////////////////////////////////////

    public int getSheetLastRowNum() {
	return getCurrentSheet().getLastRowNum();
    }

    public void setRow(int rowNo) {
	rowIdx = rowNo;
	row = getCurrentSheet().getRow(rowNo);
    }

    public HSSFRow getRow(int rowNo) {
	setRow(rowNo);
	return row;
    }

    public HSSFRow nextRow() {
	rowIdx = rowIdx++;
	setRow(rowIdx);
	return row;
    }

    public HSSFRow getCurrentRow() {
	if (row == null)
	    setRow(rowIdx);
	return row;
    }

    // ///////////////////////////////////////
    // cell 제어
    // ///////////////////////////////////////

    public void setCell(int cellNo) {
	cellIdx = cellNo;
	cell = getCurrentRow().getCell(cellIdx);
    }

    public HSSFCell getCell(int cellNo) {
	setCell(cellNo);
	return cell;
    }

    public HSSFCell nextCell() {
	cellIdx = cellIdx++;
	setCell(cellIdx);
	return cell;
    }

    public HSSFCell getCurrentCol() {
	if (cell == null)
	    setCell(cellIdx);
	return cell;
    }

    public String getStrValue(int cellNo) {
	setCell(cellNo);
	return getStrValue();
    }

    public String getStrValue() {
	return checkNull(getStrOrgValue());
    }

    public String getStrIntValue(int cellNo) {
	setCell(cellNo);
	return getStrIntValue();
    }

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

    public String getStrOrgValue(int cellNo) {
	setCell(cellNo);
	return getStrOrgValue();
    }

    public String getStrOrgValue() {
	String value = null;
	try {

	    value = cell.getStringCellValue();

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

}
