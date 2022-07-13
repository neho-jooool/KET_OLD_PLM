package ext.ket.part.migration;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ExcelHandleHs {

    public static final String EMPTY = "";

    public abstract void setSheet(int sheetNo);

    public abstract HSSFSheet getSheet(int sheetNo);

    public abstract HSSFSheet nextSheet();

    public abstract HSSFSheet getCurrentSheet();

    public abstract int getSheetLastRowNum();

    public abstract void setRow(int rowNo);

    public abstract HSSFRow getRow(int rowNo);

    public abstract HSSFRow nextRow();

    public abstract HSSFRow getCurrentRow();

    public abstract void setCell(int cellNo);

    public abstract HSSFCell getCell(int cellNo);

    public abstract HSSFCell nextCell();

    public abstract HSSFCell getCurrentCol();

    public abstract String getStrValue(int cellNo);

    public abstract String getStrValue();

    public abstract String getStrIntValue(int cellNo);

    public abstract String getStrIntValue();

    public abstract String getStrOrgValue(int cellNo);

    public abstract String getStrOrgValue();

}