package ext.ket.part.migration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelHandle {

    public static final String EMPTY = "";

    public abstract void setSheet(int sheetNo);

    public abstract Sheet getSheet(int sheetNo);

    public abstract Sheet nextSheet();

    public abstract Sheet getCurrentSheet();

    public abstract int getSheetLastRowNum();

    public abstract void setRow(int rowNo);

    public abstract Row getRow(int rowNo);

    public abstract Row nextRow();

    public abstract Row getCurrentRow();

    public abstract void setCell(int cellNo);

    public abstract Cell getCell(int cellNo);

    public abstract Cell nextCell();

    public abstract Cell getCurrentCol();

    public abstract String getStrValue(int cellNo);

    public abstract String getStrValue();

    public abstract String getStrIntValue(int cellNo);

    public abstract String getStrIntValue();

    public abstract String getStrOrgValue(int cellNo);

    public abstract String getStrOrgValue();
    
    public abstract int getNumberOfSheets();
    
    public abstract CellStyle getCellStyle();
    
    public abstract Workbook getWorkBook();
}