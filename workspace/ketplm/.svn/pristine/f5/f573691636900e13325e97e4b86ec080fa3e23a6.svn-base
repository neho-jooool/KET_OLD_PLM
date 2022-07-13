package ext.ket.cost.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import e3ps.common.util.AuthHandler;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import ext.ket.common.files.util.DownloadView;
import ext.ket.cost.entity.CostAttribute;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.service.CostHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.drm.DRMHelper;
import wt.fc.PersistenceHelper;
import wt.lifecycle.State;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.pom.Transaction;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 12. 11. 오후 12:04:19
 * @Pakage ext.ket.cost.util
 * @class CostExcelLoadUtil
 *********************************************************/
public class CostExcelLoadUtil implements RemoteAccess {

    // windchill ext.ket.cost.util.CostExcelLoadUtil %WT_HOME%\loadFiles\ket\code\COST_ATTRIBUTE_DEFINITION.xlsx
    public static final CostExcelLoadUtil manager = new CostExcelLoadUtil();
    private static final Logger LOGGER = Logger.getLogger(CostExcelLoadUtil.class);
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 5. 18. 오후 1:34:50
     * @method createRealPartNoFormExcel
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception 
     * </pre>
     */
    public static Map<String, Object> createRealPartNoFormExcel(Map<String, Object> reqMap) throws Exception {
        
        Map<String, Object> resMap = new HashMap<String, Object>();
        
        String taskOid = (String) reqMap.get("taskOid");
        
        E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
        E3PSProject project = (E3PSProject) task.getProject();
        
        List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(reqMap);
        
        List<String> partNoList = new ArrayList<String>();
        Map<String, String> realPartNoMap = new HashMap<String, String>();
        
        for(CostPart cp : cpList) {
            String partNo = cp.getPartNo();
            
            if(!partNoList.contains(partNo)) {
                partNoList.add(partNo);
                realPartNoMap.put(partNo, cp.getRealPartNo());
            }
        }
        
        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet = wb.createSheet("COSTRPNO"); 
        sheet.setDefaultColumnWidth(20);

        Font font = wb.createFont();
        font.setBoldweight((short) 600);
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setLocked(true);
        
        int cnt = 0;
        XSSFRow row = sheet.createRow(cnt++);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("임시품번 (PARTNO)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("품번 (REALPARTNO)");
        
        style = wb.createCellStyle();
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        
        for(String partNo : partNoList) {
            
            row = sheet.createRow(cnt++);
            cell = row.createCell(0);
            style.setLocked(true);
            cell.setCellStyle(style);
            cell.setCellValue(partNo);
            
            cell = row.createCell(1);
            style.setLocked(false);
            cell.setCellStyle(style);
            cell.setCellValue(realPartNoMap.get(partNo));
            
        }
        
        String fileName = project.getMaster().getPjtNo() +"_PART.xlsx";
        FileOutputStream fos = new FileOutputStream(DownloadView.TEMPPATH + File.separator + fileName); 
        wb.write(fos);
        fos.close();
        
        String downloadLink = "/plm/ext/download?path=/TEMP/" + fileName;
        resMap.put("downloadLink", downloadLink);
        
        return resMap;
    }
    
    @SuppressWarnings("resource")
    public void saveRealPartNo(Map<String, Object> reqMap, File uploadFile) throws Exception {
        
        Transaction trx = new Transaction();
        
        try {

            trx.start();

            if (DRMHelper.useDrm) {
                uploadFile = DRMHelper.Decryptupload(uploadFile, uploadFile.getName());
            }

            String fileName = uploadFile.getName();

            String ext = "";
            if (fileName.indexOf(".") >= 0) {
                ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            }

            if (ext.length() == 0) {
                throw new Exception("정확하지 않은 확장자의 파일입니다.");
            } else {

                FileInputStream fis = new FileInputStream(uploadFile);

                if ("xlsx".equals(ext)) {
                    
                    List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(reqMap);
                    
                    XSSFWorkbook wb = new XSSFWorkbook(fis);

                    XSSFSheet sheet = wb.getSheetAt(0);

                    int rowNum = sheet.getPhysicalNumberOfRows();
                    
                    DataFormatter formatter = new DataFormatter();

                    for (int i = 1; i < rowNum; i++) {
                        
                        String partNo = "";
                        String realPartNo = "";
                        
                        XSSFRow row = sheet.getRow(i);
                        
                        XSSFCell cell = row.getCell(0);
                        if (cell != null) partNo = formatter.formatCellValue(cell);
                        
                        cell = row.getCell(1);
                        if (cell != null) realPartNo = formatter.formatCellValue(cell);
                        realPartNo = realPartNo.replaceAll("\\p{Space}", ""); //공백제거
                        WTPart part = PartBaseHelper.service.getLatestPart(realPartNo);
                        if(part == null){
                            throw new Exception(realPartNo + " 는 존재하지 않는 자재입니다.");
                        }else{
                            if (part.getVersionIdentifier().getValue().equals("0") && part.getLifeCycleState() != State.toState("APPROVED")) {
                        	throw new Exception(realPartNo + " 는 초도배포(ECO) 승인이 진행되지 않았습니다.");
                	    }
                        }
                        
                        for(CostPart cp : cpList) {
                            
                            if(partNo.equals(cp.getPartNo())) {
                                LOGGER.info(partNo + " #### " + realPartNo + " #### " + CommonUtil.getOIDString(cp));
                                cp.setRealPartNo(realPartNo);
                                PersistenceHelper.manager.save(cp);
                            }
                        }
                    }
                    
                } else {
                    throw new Exception("xlsx 확장자의 파일만 업로드 가능합니다.");
                }
            }

            trx.commit();
            trx = null;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getLocalizedMessage());
        } finally {
            if (trx != null) {
                trx.rollback();
                trx = null;
            }
        }
    }
    
    
    
    
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 11. 오후 12:04:21
     * @method load
     * @param sFilePath
     * </pre>
     */
    @SuppressWarnings("rawtypes")
    public static void load(String sFilePath) throws Exception {
        Transaction trx = new Transaction();
        try {

            if (!RemoteMethodServer.ServerFlag) {
                Class c[] = new Class[] { String.class };
                Object o[] = new Object[] { sFilePath };

                RemoteMethodServer.getDefault().invoke("load", CostExcelLoadUtil.class.getName(), null, c, o);
                return;
            }

            File newFile = new File(sFilePath);

            // newfile = DRMHelper.service.upload(newfile, newfile.getName());

            String filePathInfo = "sFilePath = " + sFilePath;
            LOGGER.info(filePathInfo);

            String newFileInfo = "newFile = " + newFile;
            LOGGER.info(newFileInfo);

            LOGGER.info(newFile.getName());
            String fileName = newFile.getName();

            String ext = "";
            if (fileName.indexOf(".") >= 0) {
                ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            }

            if (ext.length() == 0) {
                LOGGER.info("정확하지 않은 확장자의 파일");
            } else {

                trx.start();

                LOGGER.info(ext);

                FileInputStream fis = new FileInputStream(newFile);

                if ("xls".equals(ext)) {
                    hssfExcelLoad(fis);
                } else if ("xlsx".equals(ext)) {
                    xssfExcelLoad(fis);
                } else {
                    LOGGER.info("엑셀 파일이 아님");
                }

                fis.close();
                trx.commit();
                trx = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (trx != null) {
                trx.rollback();
                trx = null;
            }
        }
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 11. 오후 3:09:37
     * @method hssfExcelLoad
     * @param fis
     * @throws Exception
     * </pre>
     */
    public static void hssfExcelLoad(FileInputStream fis) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook(fis);

        HSSFSheet sheet = wb.getSheetAt(0);

        int rowNum = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < rowNum; i++) {

            HSSFRow row = sheet.getRow(i);

            String attrName = "";
            String attrCode = "";
            String attrType1 = "";
            String attrType2 = "";
            String attrType3 = "";
            String attrType4 = "";
            String attrType5 = "";
            String attrType6 = "";
            String attrType7 = "";

            HSSFCell cell = row.getCell(0);
            if (cell != null)
                attrName = cell.getStringCellValue();
            cell = row.getCell(1);
            if (cell != null)
                attrCode = cell.getStringCellValue();
            cell = row.getCell(2);
            if (cell != null)
                attrType1 = cell.getStringCellValue();
            cell = row.getCell(3);
            if (cell != null)
                attrType2 = cell.getStringCellValue();
            cell = row.getCell(4);
            if (cell != null)
                attrType3 = cell.getStringCellValue();
            cell = row.getCell(5);
            if (cell != null)
                attrType4 = cell.getStringCellValue();
            cell = row.getCell(6);
            if (cell != null)
                attrType5 = cell.getStringCellValue();
            cell = row.getCell(7);
            if (cell != null)
                attrType6 = cell.getStringCellValue();
            cell = row.getCell(8);
            if (cell != null)
                attrType7 = cell.getStringCellValue();

            CostAttribute costAttr = null;

            costAttr = CostUtil.manager.getCostAttributeForName(attrName);

            if (costAttr == null) {
                LOGGER.info("create attrCode = " + attrCode);
                costAttr = CostAttribute.newCostAttribute();
            } else {
                LOGGER.info("modify attrCode = " + attrCode);
            }

            costAttr.setCode(attrCode);
            costAttr.setName(attrName);

            List<String> attrTypeList = new ArrayList<String>();
            if ("○".equals(attrType1))
                attrTypeList.add(CostUtil.MATERIAL_COST);
            if ("○".equals(attrType2))
                attrTypeList.add(CostUtil.PRODUCTION);
            if ("○".equals(attrType3))
                attrTypeList.add(CostUtil.LABOR_COST);
            if ("○".equals(attrType4))
                attrTypeList.add(CostUtil.EXPENSE);
            if ("○".equals(attrType5))
                attrTypeList.add(CostUtil.MAINTENENCE_COST);
            if ("○".equals(attrType6))
                attrTypeList.add(CostUtil.PRODUCTTYPE);
            if ("○".equals(attrType7))
                attrTypeList.add(CostUtil.FORMULATYPE);

            LOGGER.info(attrTypeList.toString());

            String attrType = attrTypeList.toString();
            attrType = attrType.substring(1, attrType.length() - 1).replaceAll("\\s", "");
            costAttr.setAttrType(attrType);

            PersistenceHelper.manager.save(costAttr);
        }
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 11. 오후 3:09:45
     * @method xssfExcelLoad
     * @param fis
     * @throws Exception
     * </pre>
     */
    public static void xssfExcelLoad(FileInputStream fis) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook(fis);

        XSSFSheet sheet = wb.getSheetAt(0);

        int rowNum = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < rowNum; i++) {

            XSSFRow row = sheet.getRow(i);

            String attrName = "";
            String attrCode = "ATTRCODE" + i;
            String attrType1 = "";
            String attrType2 = "";
            String attrType3 = "";
            String attrType4 = "";
            String attrType5 = "";
            String attrType6 = "";
            String attrType7 = "";

            XSSFCell cell = row.getCell(0);
            if (cell != null)
                attrName = cell.getStringCellValue();
            cell = row.getCell(1);
            if (cell != null)
                attrCode = cell.getStringCellValue();
            cell = row.getCell(2);
            if (cell != null)
                attrType1 = cell.getStringCellValue();
            cell = row.getCell(3);
            if (cell != null)
                attrType2 = cell.getStringCellValue();
            cell = row.getCell(4);
            if (cell != null)
                attrType3 = cell.getStringCellValue();
            cell = row.getCell(5);
            if (cell != null)
                attrType4 = cell.getStringCellValue();
            cell = row.getCell(6);
            if (cell != null)
                attrType5 = cell.getStringCellValue();
            cell = row.getCell(7);
            if (cell != null)
                attrType6 = cell.getStringCellValue();
            cell = row.getCell(8);
            if (cell != null)
                attrType7 = cell.getStringCellValue();

            CostAttribute costAttr = null;
            costAttr = CostUtil.manager.getCostAttributeForName(attrName);

            if (costAttr == null) {
                LOGGER.info("create attrCode = " + attrCode);
                costAttr = CostAttribute.newCostAttribute();
            } else {
                LOGGER.info("modify attrCode = " + attrCode);
            }

            costAttr.setCode(attrCode);
            costAttr.setName(attrName);

            List<String> attrTypeList = new ArrayList<String>();
            if ("○".equals(attrType1))
                attrTypeList.add(CostUtil.MATERIAL_COST);
            if ("○".equals(attrType2))
                attrTypeList.add(CostUtil.PRODUCTION);
            if ("○".equals(attrType3))
                attrTypeList.add(CostUtil.LABOR_COST);
            if ("○".equals(attrType4))
                attrTypeList.add(CostUtil.EXPENSE);
            if ("○".equals(attrType5))
                attrTypeList.add(CostUtil.MAINTENENCE_COST);
            if ("○".equals(attrType6))
                attrTypeList.add(CostUtil.PRODUCTTYPE);
            if ("○".equals(attrType7))
                attrTypeList.add(CostUtil.FORMULATYPE);

            LOGGER.info(attrTypeList.toString());
            String attrType = attrTypeList.toString();
            attrType = attrType.substring(1, attrType.length() - 1).replaceAll("\\s", "");
            LOGGER.info(attrType);
            costAttr.setAttrType(attrType);

            PersistenceHelper.manager.save(costAttr);
        }

    }

    /**
     * <pre>
     * @description 메소드 호출
     * @author dhkim
     * @date 2017. 12. 11. 오후 12:04:40
     * @method main
     * @param args
     * </pre>
     */
    public static void main(String args[]) {

        try {
            RemoteMethodServer server = RemoteMethodServer.getDefault();

            server.setAuthenticator(AuthHandler.getMethodAuthenticator("wcadmin"));

            LOGGER.setLevel(Level.INFO);

            String sFilePath = "";
            LOGGER.info("############CostAttrExcelLoad START####################");
            if (args != null && args.length > 0 && args[0].trim().length() > 0) {
                sFilePath = args[0];
            }
            load(sFilePath);
            LOGGER.info("############CostAttrExcelLoad COMPLETE####################");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
