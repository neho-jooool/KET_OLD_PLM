package ext.ket.cost.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 2. 15. 오후 2:08:34
 * @Pakage ext.ket.cost.util
 * @class CostBomEditorColumnExcelUtil
 *********************************************************/
public class CostBomEditorColumnExcelUtil {

    private final static String FILEPATH;

    private final static String FILEPATHFORTYPE;

    static {
        Config conf = ConfigImpl.getInstance();
        FILEPATH = conf.getString("cost.columnList",
                "D:\\ptc\\Windchill_10.2\\Windchill\\codebase\\cost\\column.xlsx");
        FILEPATHFORTYPE = conf.getString("cost.columnListForType",
                "D:\\ptc\\Windchill_10.2\\Windchill\\codebase\\cost\\column.xlsx");
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 15. 오후 2:38:13
     * @method getColumnInfos
     * @return
     * @throws Exception List<BomEditorColumnInfo>
     * </pre>
     */
    public static List<BomEditorColumnInfo> getColumnInfos() throws Exception {

        return getListFromExcel(FILEPATH);

    }

    public static List<BomEditorColumnInfo> getColumnInfosforType() throws Exception {
        return getListFromExcel(FILEPATHFORTYPE);
    }

    /**
     * <pre>
     * @description  
     * @author khkim
     * @date 2018. 2. 15. 오후 2:38:04
     * @method getListFromExcel
     * @param path
     * @return
     * @throws Exception List<BomEditorColumnInfo>
     * </pre>
     */
    public static List<BomEditorColumnInfo> getListFromExcel(String path) throws Exception {
        FileInputStream fis = null;
        List<BomEditorColumnInfo> list = new ArrayList<BomEditorColumnInfo>();

        try {

            fis = new FileInputStream(path);

            XSSFWorkbook wb = new XSSFWorkbook(fis);

            XSSFSheet sheet = wb.getSheetAt(0);

            int rowNum = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowNum; i++) {

                String columnKey = "";
                String displayName = "";
                String description = "";

                XSSFRow row = sheet.getRow(i);

                XSSFCell cell = row.getCell(0);

                if (cell != null) {
                    columnKey = cell.getStringCellValue();
                }

                cell = row.getCell(1);
                if (cell != null) {
                    displayName = cell.getStringCellValue();
                }

                cell = row.getCell(2);
                if (cell != null) {
                    description = cell.getStringCellValue();
                }

                list.add(new BomEditorColumnInfo(columnKey, displayName, description));
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return list;
    }

}
