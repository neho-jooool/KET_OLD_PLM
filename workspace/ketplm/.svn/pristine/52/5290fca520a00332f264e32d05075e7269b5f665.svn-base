package ext.ket.cost.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.StringUtil;

/*********************************************************
 * @description 
 * @author dhkim
 * @date 2018. 4. 5. 오전 10:50:08
 * @Pakage ext.ket.cost.util
 * @class TrackingAttrExcelUtil
 *********************************************************/
public class TrackingAttrExcelUtil {

    private final static String FILEPATH;

    static {
        Config conf = ConfigImpl.getInstance();
        FILEPATH = conf.getString("cost.trackingAttrList",
                "D:\\ptc\\Windchill_10.2\\Windchill\\codebase\\cost\\trackingColumn.xlsx");
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 4. 5. 오전 10:50:12
     * @method getTrackingAttrList
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    public static List<Map<String, Object>> getTrackingAttrList() throws Exception {
        return getListFromExcel(FILEPATH);
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 4. 5. 오전 10:50:19
     * @method getListFromExcel
     * @param path
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    public static List<Map<String, Object>> getListFromExcel(String path) throws Exception {
        FileInputStream fis = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try {
            fis = new FileInputStream(path);

            XSSFWorkbook wb = new XSSFWorkbook(fis);

            XSSFSheet sheet = wb.getSheetAt(0);

            int rowNum = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowNum; i++) {

                XSSFRow row = sheet.getRow(i);
                Map<String, Object> data = new HashMap<String, Object>();

                XSSFCell cell = row.getCell(1);
                if (cell != null) {
                    data.put("displayName", cell.getStringCellValue());
                }

                cell = row.getCell(2);
                if (cell != null) {
                    data.put("attrKey", cell.getStringCellValue());
                }
                
                cell = row.getCell(3);
                if (cell != null) {
                    data.put("isTarget", StringUtil.checkString(cell.getStringCellValue()));
                }
                
                cell = row.getCell(4);
                if (cell != null) {
                    data.put("attrType", cell.getStringCellValue());
                }

                list.add(data);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return list;
    }
    
    public static void main(String[] args) throws Exception{
        getTrackingAttrList();
    }

}
