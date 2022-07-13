package ext.ket.part.replacePart.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wt.fc.PersistenceHelper;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.session.SessionHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.util.AuthHandler;
import e3ps.common.util.CommonUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartInfo;
import ext.ket.part.entity.RivalPartInfo;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.drm.DRMHelper;

public class replacePartUploadUtil {
    
    
    public static List<Map<String, Object>> partNoExtract(String sFilePath) throws Exception{
	
	Map<String, Object> dataMap = new HashMap<String, Object>();
        List<Map<String, Object>> partNoList = new ArrayList<Map<String, Object>>();
        
        File newFile = new File(sFilePath);
        
	if (DRMHelper.useDrm) {
	    newFile = DRMHelper.Decryptupload(newFile, newFile.getName());
	}

	String filePathInfo = "sFilePath = " + sFilePath;
	System.out.println(filePathInfo);

	String newFileInfo = "newFile = " + newFile;
	System.out.println(newFileInfo);

	System.out.println(newFile.getName());
	String fileName = newFile.getName();

	String ext = "";
	if (fileName.indexOf(".") >= 0) {
	    ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}

	if (ext.length() == 0) {
	    System.out.println("정확하지 않은 확장자의 파일");
	} else {
	    FileInputStream fis = new FileInputStream(newFile);
	    String ErrMsg = "";
	    if ("xlsx".equals(ext)) {
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);

	        XSSFSheet sheet = wb.getSheetAt(0);

	        int rowNum = sheet.getPhysicalNumberOfRows();
	        
	        boolean isError = true;
	        
	        DataFormatter formatter = new DataFormatter();
	        String partNo = "";
	        
	        for (int i = 1; i < rowNum; i++) {
	            XSSFRow row = sheet.getRow(i);
	            XSSFCell cell = row.getCell(0);
	            
	            if (cell != null){
	        	partNo = formatter.formatCellValue(cell);
	        	dataMap = new HashMap<String, Object>();
	        	dataMap.put("partNo", partNo);
	                partNoList.add(dataMap);
	            }	            
	        }
	        
	    } else {
		throw new Exception("xlsx 확장자의 파일만 사용가능합니다.");
	    }


	    fis.close();
	}
	
	return partNoList;
    }
    
    public static void load(String sFilePath) throws Exception {
	Transaction trx = new Transaction();
	
	try{
	    if (!RemoteMethodServer.ServerFlag) {
                Class c[] = new Class[] { String.class };
                Object o[] = new Object[] { sFilePath };

                RemoteMethodServer.getDefault().invoke("load", replacePartUploadUtil.class.getName(), null, c, o);
                return;
            }

            File newFile = new File(sFilePath);
            
            if (DRMHelper.useDrm) {
        	newFile = DRMHelper.Decryptupload(newFile, newFile.getName());
            }

            String filePathInfo = "sFilePath = " + sFilePath;
            System.out.println(filePathInfo);

            String newFileInfo = "newFile = " + newFile;
            System.out.println(newFileInfo);

            System.out.println(newFile.getName());
            String fileName = newFile.getName();

            String ext = "";
            if (fileName.indexOf(".") >= 0) {
                ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            }
            
            if (ext.length() == 0) {
        	System.out.println("정확하지 않은 확장자의 파일");
            } else {

                trx.start();

                System.out.println(ext);

                FileInputStream fis = new FileInputStream(newFile);
                String ErrMsg = "";
                if ("xlsx".equals(ext)) {
                    ErrMsg = xssfExcelLoad(fis);
                } else {
                    throw new Exception("xlsx 확장자의 파일만 업로드 가능합니다.");
                }
                
                if(StringUtils.isNotEmpty(ErrMsg)){
                    throw new Exception(ErrMsg);
                }
                fis.close();
                trx.commit();
                trx = null;
            }
	}catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (trx != null) {
                trx.rollback();
                trx = null;
            }
        }
    }
    
    @SuppressWarnings({ "static-access", "unused" })
    public static String xssfExcelLoad(FileInputStream fis) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook(fis);

        XSSFSheet sheet = wb.getSheetAt(0);

        int rowNum = sheet.getPhysicalNumberOfRows();
        
        boolean isError = true;
        String ErrMsg = "";
        
        DataFormatter formatter = new DataFormatter();
        String enter = "\r\n";
        for (int i = 1; i < rowNum; i++) {

            XSSFRow row = sheet.getRow(i);
            
            String companyCode = "";
            String companyName = "";
            String partNo = "";
            String partName = "";
            String waterProof = "";
            String mfType = "";
            String pole = "";
            String series = "";
            String seriesName = "";
            String matchTerminal = "";
            String bigo = "";
            
            String KETpartNo = "";
            String replaceGb = "";
            
            XSSFCell cell = row.getCell(0);
            
            if (cell != null)
        	partNo = formatter.formatCellValue(cell);
            cell = row.getCell(1);
            if (cell != null)
        	companyCode = formatter.formatCellValue(cell);
            cell = row.getCell(2);
            if (cell != null)
        	partName = formatter.formatCellValue(cell);
            cell = row.getCell(3);
            if (cell != null)
        	waterProof = formatter.formatCellValue(cell);
            cell = row.getCell(4);
            if (cell != null)
        	mfType = formatter.formatCellValue(cell);
            cell = row.getCell(5);
            if (cell != null)
        	pole = formatter.formatCellValue(cell);
            cell = row.getCell(6);
            if (cell != null)
        	series = formatter.formatCellValue(cell);
            cell = row.getCell(7);
            if (cell != null)
        	matchTerminal = formatter.formatCellValue(cell);
            cell = row.getCell(8);
            if (cell != null)
        	replaceGb = formatter.formatCellValue(cell);
            cell = row.getCell(9);
            if (cell != null)
        	KETpartNo = formatter.formatCellValue(cell);
            cell = row.getCell(10);
            if (cell != null)
        	bigo = formatter.formatCellValue(cell);
            
            
            KETpartNo = KETpartNo.replaceAll("\\p{Space}", ""); //공백제거
            partNo = partNo.replaceAll("\\p{Space}", ""); //공백제거
            waterProof = waterProof.replaceAll("\\p{Space}", ""); //공백제거
            mfType = mfType.replaceAll("\\p{Space}", ""); //공백제거
            pole = pole.replaceAll("\\p{Space}", ""); //공백제거
            series = series.replaceAll("\\p{Space}", ""); //공백제거
            replaceGb = replaceGb.replaceAll("\\p{Space}", ""); //공백제거
            NumberCode nc = null;
            
            if(StringUtils.isEmpty(partNo)){
        	isError = false;
        	ErrMsg += i+" 번째 경쟁사 부품번호가 누락되었습니다."+enter;
            }
            
            if(StringUtils.isEmpty(companyCode)){
        	isError = false;
        	ErrMsg += i+" 번째 ConnectorMaker가 누락되었습니다."+enter;
            }else{
        	//companyCode = CodeHelper.manager.getCodeCodeByValue("SALESCOMPETITOR", companyName);
        	companyName = CodeHelper.manager.getCodeValue("SALESCOMPETITOR", companyCode);
        	
        	if(StringUtils.isEmpty(companyName)){
        	    isError = false;
            	    ErrMsg += i+" 번째 ConnectorMaker는 존재하지 않는 경쟁사입니다." + companyCode+enter;
        	}
            }
            
            if(StringUtils.isEmpty(partName)){
        	isError = false;
        	ErrMsg += i+" 번째 경쟁사 PartName 이 누락되었습니다."+enter;
            }
            
            if(StringUtils.isEmpty(waterProof)){
        	isError = false;
        	ErrMsg += i+" 번째 경쟁사 방수여부가 누락되었습니다."+enter;
            }else{
        	if(!"방수".equals(waterProof) && !"비방수".equals(waterProof)){
        	    isError = false;
                    ErrMsg += i+" 번째 경쟁사 방수여부의 값은 방수/비방수 중 하나를 입력하셔야합니다."+enter;
        	}
            }
            
            if(StringUtils.isEmpty(mfType)){
        	isError = false;
        	ErrMsg += i+" 번째 Male/Female 항목이 누락되었습니다."+enter;
            }else{
        	if(!"M".equals(mfType) && !"F".equals(mfType)){
        	    isError = false;
                    ErrMsg += i+" 번째 경쟁사 Male/Femal 값은 M/F 중 하나를 입력하셔야합니다."+enter;
        	}
            }
            
            if(StringUtils.isEmpty(pole)){
        	isError = false;
        	ErrMsg += i+" 번째 경쟁사 극수 항목이 누락되었습니다."+enter;
            }else{
        	if(!StringUtils.isNumeric(pole)){
        	    isError = false;
            		ErrMsg += i+" 번째 경쟁사 극수 항목에 숫자가 아닌 값이 포함되어 있습니다."+enter;    
        	}
            }
            
            nc = CodeHelper.manager.getNumberCode("SPECSERIES", series);
            
            if(nc == null){
        	isError = false;
        	ErrMsg += i+" 번째 경쟁사 SERIES 항목이 누락되었거나 존재하지 않는 코드입니다. "+series+enter;
            }else{
        	seriesName = nc.getName();
            }
            
            if(StringUtils.isEmpty(replaceGb)){
        	isError = false;
        	ErrMsg += i+" 번째 Replace 항목이 누락되었습니다."+enter;
            }else{
        	if(!replaceGb.equals("△") && !replaceGb.equals("X") && !replaceGb.equals("O")){
        	    isError = false;
            	    ErrMsg += i+" 번째 Replace 항목은 특수문자 △ ,  X (알파벳), O(알파벳)만 입력가능합니다."+enter;
        	}
            }
            
            
            WTPart wtpart = PartBaseHelper.service.getLatestPart(KETpartNo);
            if(wtpart == null && StringUtils.isNotEmpty(KETpartNo)){
        	isError = false;
        	ErrMsg += i+" 번째 입력된 "+KETpartNo + " 는 PLM에 존재하지 않는 부품입니다."+enter;
            }
            
            if(!isError){
        	continue;
            }
            
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("partNo", partNo);
            
            RivalPartInfo part = ReplacePartUtil.manager.getRivalPart(reqMap);
            
            if(part == null){
        	part = RivalPartInfo.newRivalPartInfo();
            }
            
            part.setPartNo(partNo);
            part.setPartName(partName);
            part.setCompanyName(companyName);
            part.setCompanyCode(companyCode);
            part.setWaterProof(waterProof);
            part.setMfType(mfType);
            part.setPole(pole);
            part.setSeriesCode(series);
            part.setSeriesName(seriesName);
            part.setMatchTerminal(matchTerminal);
            part.setBigo(bigo);
            part.setOwner(SessionHelper.manager.getPrincipalReference());
            part = (RivalPartInfo)PersistenceHelper.manager.save(part);
            
            if(wtpart == null){
        	continue;
            }
            
            reqMap.put("partNo", KETpartNo);
            String rivalPartOid = CommonUtil.getOIDString(part);
            reqMap.put("rivalPartOid", rivalPartOid);
            
            KETPartInfo KETPart = ReplacePartUtil.manager.getKETPart(reqMap);
            
            if(KETPart == null){
        	KETPart = KETPart.newKETPartInfo();
            }
            
            String SpWaterProof = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpWaterProof);// 방수여부
            String SpNoOfPole = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpNoOfPole);// 극수
            String spMTerminal = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMTerminal);// 매칭터미널
            String spMConnector = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMConnector);// 매칭커넥터
            String spSeries = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpSeries);// 시리즈
            NumberCode num = CodeHelper.manager.getNumberCode("SEALED", SpWaterProof);
            String sealed = "";
            if(num != null){
        	sealed = num.getName();
            }
            String KetSeries = "";
            num = CodeHelper.manager.getNumberCode("SPECSERIES", spSeries);
            if(num != null){
        	KetSeries = num.getName();
            }
            
            KETPartClassification claz = PartClazHelper.service.getPartClassification(wtpart);
            
            if(StringUtils.isNotEmpty(spMTerminal)){

        	spMTerminal = spMTerminal.replaceAll(",", System.getProperty("line.separator"));
            }
            
            if(StringUtils.isNotEmpty(spMConnector)){

        	spMConnector = spMConnector.replaceAll(",", System.getProperty("line.separator"));
            }
            
            KETPart.setKetPartNo(KETpartNo);
            KETPart.setKetPartName(wtpart.getName());
            KETPart.setKetWaterProof(sealed);
            KETPart.setClassification(claz.getClassKrName());
            KETPart.setKetPole(SpNoOfPole);
            KETPart.setKetMatchConnector(spMConnector);
            KETPart.setKetMatchTerminal(spMTerminal);
            KETPart.setRivalPart(part);
            KETPart.setReplaceGb(replaceGb);
            KETPart.setKetSeries(KetSeries);
            KETPart.setOwner(SessionHelper.manager.getPrincipalReference());
            KETPart = (KETPartInfo)PersistenceHelper.manager.save(KETPart);

        }
        
        return ErrMsg;

    }

    public static void main(String args[]) {

        try {
            RemoteMethodServer server = RemoteMethodServer.getDefault();

            server.setAuthenticator(AuthHandler.getMethodAuthenticator("wcadmin"));

            String sFilePath = "";
            
            if (args != null && args.length > 0 && args[0].trim().length() > 0) {
                sFilePath = args[0];
            }
            load(sFilePath);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
