package ext.ket.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * Excel파일을 읽어 Data를 추출하는 추상 클래스
 * 
 * @author swpark
 * @date Jun 20, 2008
 */
public abstract class ExcelLoader {
    private FileInputStream fileInputStream = null;
    private FileOutputStream fileOutputStream = null;
    protected Workbook workbook;
    protected Sheet sheet;
    protected String excelFileName;

    /**
     * File명을 반드시 지정하기 구현하도록 한 추상 메소드
     * 
     * @author swpark
     * @date Jun 20, 2008
     * @param FileName
     */
    public abstract void setExcelFileName(String fileName);

    public abstract void setWorkBookData();

    /**
     * Excel 파일을 생성하기 위한 Template method
     * 
     * @메소드명 : exportExcelTemplate
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void exportExcelTemplate() {
	try {
	    this.loadExcelTemplate();
	    this.setSheet(0);
	    this.setWorkBookData();
	    // this.saveAs();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (fileOutputStream != null) {
		    fileOutputStream.close();
		}
	    } catch (IOException e) {
		Kogger.error(getClass(), e);
	    }
	}
    }

    /**
     * Excel File을 읽어들인다.
     * 
     * @author swpark
     * @date Jun 18, 2008
     * @param file
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void loadExcelTemplate() throws Exception {
	if (excelFileName.length() > 0 && excelFileName.endsWith("xls")) {
	    fileInputStream = new FileInputStream(excelFileName);
	    POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
	    workbook = new HSSFWorkbook(fs);
	} else if (excelFileName.length() > 0 && excelFileName.endsWith("xlsx")) {
	    workbook = new XSSFWorkbook(excelFileName);
	}
    }

    public void dispose() throws Exception {
	if (fileInputStream != null)
	    fileInputStream.close();
    }

    /**
     * WorkBook정보를 가져온다.
     * 
     * @author swpark
     * @date Jun 20, 2008
     * @return
     */
    public Workbook getWorkBook() {
	return workbook;
    }

    /**
     * Sheet를 선택한다.
     * 
     * @author swpark
     * @date Jun 18, 2008
     * @param sheetIndex
     */
    public void setSheet(int sheetIndex) {
	this.sheet = workbook.getSheetAt(sheetIndex);
    }

    /**
     * 선택되어진 Sheet를 가져온다.
     * 
     * @author swpark
     * @date Jun 20, 2008
     * @return
     */
    public Sheet getSheet(int sheetIndex) {
	return workbook.getSheetAt(sheetIndex);
    }

    /**
     * Excel Cell에 대한 값을 가져온다.
     * 
     * @author swpark
     * @date Jun 20, 2008
     * @param cell
     * @return
     */
    protected Object getValue(Cell cell) {
	Object value = "";

	switch (cell.getCellType()) {
	case HSSFCell.CELL_TYPE_BLANK:
	    break;
	case HSSFCell.CELL_TYPE_BOOLEAN:
	    value = new Boolean(cell.getBooleanCellValue());
	    break;
	case HSSFCell.CELL_TYPE_NUMERIC:
	    value = (HSSFDateUtil.isCellDateFormatted(cell)) ? (Object) cell.getDateCellValue() : new Double(cell.getNumericCellValue());
	    break;
	case HSSFCell.CELL_TYPE_STRING:
	    value = cell.getRichStringCellValue().getString().trim().replaceAll("\n", " ");
	    break;
	case HSSFCell.CELL_TYPE_ERROR:
	    value = "Error";
	    break;
	case HSSFCell.CELL_TYPE_FORMULA:
	    // value = cell.getCellFormula();
	    value = cell.getNumericCellValue();
	    break;
	default:
	    value = "";
	    break;
	}

	return value;
    }

    /**
     * 새로운 이름으로 Load된 Excel 파일을 다른이름으로
     * 
     * @메소드명 : saveExcelFile
     * @작성자 : sw775.park
     * @작성일 : Sep 1, 2009
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveAs(String newName) {
	try {
	    if (newName == null)
		throw new Exception("Not exists new excel file name");

	    String directory = newName.substring(0, newName.lastIndexOf("\\"));
	    File file = new File(directory);
	    if (!file.exists()) {
		file.mkdirs();
	    }

	    fileOutputStream = new FileOutputStream(newName);
	    this.workbook.write(fileOutputStream);
	    fileOutputStream.close();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		fileOutputStream.close();
	    } catch (IOException e) {
		Kogger.error(getClass(), e);
	    }
	}
    }

    /**
     * @메소드명 : replaceNull
     * @작성자 : sw775.park
     * @작성일 : Sep 8, 2009
     * @param str
     * @return
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String replaceNull(String str) {
	if (str == null)
	    return "";
	else
	    return str;
    }

    /**
     * @메소드명 : replaceNumber
     * @작성자 : sw775.park
     * @작성일 : Jan 6, 2010
     * @param str
     * @return
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public double replaceToNumber(String str) {
	if (str == null)
	    return 0;
	else
	    return new Double(str).doubleValue();
    }

    /**
     * 파일(Office 문서 및 pdf)을 POIFS에 추가 한다.
     * 
     * @메소드명 : createDocument
     * @작성자 : sw775.park
     * @작성일 : Nov 13, 2009
     * @param fs
     * @param fileFullPath
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void createDocument(POIFSFileSystem fs, String fileFullPath) {
	try {
	    File file = new File(fileFullPath);
	    if (!file.exists())
		throw new Exception(fileFullPath + " 파일이 존재하지 않습니다.");

	    fileInputStream = new FileInputStream(file);
	    fs.createDocument(fileInputStream, file.getName());
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 파일(Office 문서 및 pdf)을 POIFS에 추가 한다.
     * 
     * @메소드명 : createDocument
     * @작성자 : sw775.park
     * @작성일 : Nov 13, 2009
     * @param fs
     * @param fileFullPath
     * 
     */
    public void createDocument(String fileFullPath) {
	try {
	    File file = new File(fileFullPath);
	    if (!file.exists())
		throw new Exception(fileFullPath + " 파일이 존재하지 않습니다.");

	    fileInputStream = new FileInputStream(file);
	    POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
	    fs.createDocument(fileInputStream, file.getName());
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * Default CellStyle
     * 
     * @메소드명 : createDefaultCellStyle
     * @작성자 : sw775.park
     * @작성일 : Nov 3, 2009
     * @return
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private CellStyle createDefaultCellStyle() {
	// Font
	Font font = this.getWorkBook().createFont();
	font.setFontName("gulim");
	font.setFontHeightInPoints((short) 10);

	// Cell Style
	CellStyle cellStyle = this.getWorkBook().createCellStyle();
	cellStyle.setWrapText(true);
	cellStyle.setFont(font);

	return cellStyle;
    }

    /**
     * border style
     * 
     * @메소드명 : createBorderStyle
     * @작성자 : sw775.park
     * @작성일 : Nov 13, 2009
     * @param lineStyle
     * @param borderStyle
     * @return
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private CellStyle createBorderStyle(short top, short right, short bottom, short left) {
	CellStyle style = this.createDefaultCellStyle();

	style.setBorderTop(top);
	style.setBorderRight(right);
	style.setBorderBottom(bottom);
	style.setBorderLeft(left);
	style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setWrapText(true);
	return style;
    }

    /**
     * Cell Styles
     * 
     * @메소드명 : createStyles
     * @작성자 : sw775.park
     * @작성일 : Nov 13, 2009
     * @param lineStyle
     * @param borderLine
     * @param borderStyle
     * @return
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Map<String, CellStyle> createStyles(short top, short right, short bottom, short left) {
	CellStyle style;
	Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	DataFormat df = this.getWorkBook().createDataFormat();

	// left align
	style = createBorderStyle(top, right, bottom, left);
	style.setAlignment(CellStyle.ALIGN_LEFT);
	styles.put("string_left", style);
	// center align
	style = createBorderStyle(top, right, bottom, left);
	style.setAlignment(CellStyle.ALIGN_CENTER);
	styles.put("string_center", style);
	// right align
	style = createBorderStyle(top, right, bottom, left);
	style.setAlignment(CellStyle.ALIGN_RIGHT);
	styles.put("string_right", style);
	// numberic_right format
	style = createBorderStyle(top, right, bottom, left);
	style.setAlignment(CellStyle.ALIGN_RIGHT);
	style.setDataFormat(this.getWorkBook().createDataFormat().getFormat("0.0"));
	styles.put("numberic_right", style);
	// numberic_center format
	style = createBorderStyle(top, right, bottom, left);
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setDataFormat(this.getWorkBook().createDataFormat().getFormat("0.0"));
	styles.put("numberic_center", style);
	// date format
	style = createBorderStyle(top, right, bottom, left);
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setDataFormat(df.getFormat("yyyy-mm-dd"));
	styles.put("date", style);

	return styles;
    }

    public void setCellValue(int rowIndex, int cellIndex, String stringValue) throws Exception {
	this.sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(StringUtil.checkNull(stringValue));
    }
}
