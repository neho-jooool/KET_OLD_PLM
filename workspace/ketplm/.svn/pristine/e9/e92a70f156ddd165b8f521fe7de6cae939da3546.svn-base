/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ErpBomMigInfoInterface
 * 작성자 : Shin.daebeum
 * 작성일자 : 2010. 12. 15.
 */

package e3ps.sap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import wt.pom.DatastoreException;
import wt.pom.PersistenceException;
import wt.util.WTException;
import wt.util.WTInvalidParameterException;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.content.uploader.FileUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

// ERP BOM Migration Class
public class ErpBomMigInfoInterface {
    DBConnectionManager res = null;
    Connection connection = null;
    PreparedStatement pstmt = null;
    Registry registry = Registry.getRegistry("e3ps.bom.bom");

    // public static String strDirPath = "D:\\ptc\\Windchill\\logs\\bom";
    // [PLM System 1차 고도화] Path 변경, 2014-06-12, TKLEE
    public static String strDirPath = "D:\\ptc\\Windchill_10.2\\Windchill\\logs\\bom";
    public static FileWriter fw = null;
    public static String currentDate = DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd"));
    public static String currentTime = DateUtil.getCurrentDateString(new SimpleDateFormat("HH:mm:ss"));

    public static Config conf = ConfigImpl.getInstance();
    public static boolean isERPCheck = conf.getBoolean("ERPCHECK");
    ArrayList<Hashtable<String, String>> BomAllstructure = new <Hashtable<String, String>>ArrayList();
    public ErpBomMigInfoInterface() {
    }
    
    //사용안함
    public String MigBomValidation(FileUploader uploader, String headCode){
	String errMessage = "";
	Vector files = uploader.getFiles();
	int cnt = 0;
	int excelcnt = 0;
	WBFile file_;
	File excel;
	FileInputStream fis;
	HSSFWorkbook workBook;
	HSSFSheet sheet;
	Iterator<Row> rowIterator;
	Row row;
	
	if(files.size() > 0){
	    for (int i = 0; i < files.size(); i++) {
		file_ = (WBFile) files.elementAt(i);
        	excel = file_.getFile();
        	try {
        	    fis = new FileInputStream(excel);
        	    workBook = new HSSFWorkbook(fis);
        	    sheet = workBook.getSheetAt(0);
        	    rowIterator = sheet.iterator();
        	    while (rowIterator.hasNext()) {
        		row = rowIterator.next();
        		if(excelcnt > 0){//엑셀 헤더 제외
        		    String partNo = getStringValue(row.getCell(Short.parseShort("0")));
        		    partNo = partNo.trim().toUpperCase();
        		    if(!PartBaseHelper.service.existWTPartNumber(partNo,"")){
        			errMessage = "엑셀의 "+ (excelcnt+1) +" 번째 부품이 존재하지 않습니다. ⇒ "+partNo;
        		    }
        		}
        		excelcnt++;
        	   }
                } catch (IOException e) {
                    Kogger.error(getClass(), e);
                    errMessage = "IOException 발생! 관리자에게 문의하세요";
                } catch (Exception e) {
                    Kogger.error(getClass(), e);
                    errMessage = "예상치 못한 문제 발생! 관리자에게 문의하세요";
        	}
             }
        }else{
            headCode = headCode.trim().toUpperCase();
	    try {
	        if(!PartBaseHelper.service.existWTPartNumber(headCode,"")){
	            errMessage = "부품이 존재하지 않습니다. ⇒ "+headCode;
	        }
            } catch (Exception e) {
        	Kogger.error(getClass(), e);
                errMessage = "예상치 못한 문제 발생! 관리자에게 문의하세요";
            }
        }
	return errMessage;
    }
    
    /* getMigResult2 (BOM 단건 업로드) 2015.06.23 황정태 작성 
     * ERP로부터 모든 하위 레벨의 BOM구조를 가져오고, 생성된 BOM구조(ArrayList)를 파라미터로 KETBomHelper.service.setKetPartUsageLink 를 호출, PLM 최신버전을 대상으로 BOM을 동기화하는 것이 목적이다
     */
    
    public Object[] getMigResult2(String chkItem, String headCode, String fromDate, String toDate, String strFileName) throws IOException {
	Kogger.debug(getClass(), "################### chkItem ::::::::::: " + chkItem);

	File dir = new File(strDirPath);
	if (!dir.exists()) { // 로그 저장 폴더가 존재하지 않는 경우 생성함
	    dir.mkdir();
	}

	Kogger.debug(getClass(), "@@@@@@@@@ strFilePath : " + strDirPath + "\\" + strFileName);
	File file = new File(strDirPath + "\\" + strFileName);
	if (!file.exists()) { // 로그 저장 파일이 존재하지 않는 경우 생성함
	    file.createNewFile();
	}

	// 로그 남길 파일 생성
	fw = new FileWriter(file, true);

	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.write(":::::::::::::: SAP BOM INTERFACE  \n");
	fw.write(":::::::::::::: START TIME :: " + currentDate + " " + currentTime + "\n");
	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.flush();
	ArrayList<Hashtable<String, String>> TotalStructureList = new <Hashtable<String, String>>ArrayList();
	Hashtable<String, String> hashtab2;
	Object[] result = new Object[2];
	String errMessage = "";
	String tempMessage = "";
	headCode = headCode.trim().toUpperCase();
	try {
	    if(!PartBaseHelper.service.existWTPartNumber(headCode,"")){
		tempMessage = "부품 "+headCode+" 가 PLM에 존재하지 않습니다.";
	        errMessage += tempMessage+"\r\n";
	        fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
	    }
        } catch (Exception e1) {
	    // TODO Auto-generated catch block
            tempMessage = "\r\n\r\n\r\n\r\n 부품 ["+headCode+"] ValidationCheck 오류 발생!\r\n\r\n\r\n"+this.getStackTrace(e1);
            errMessage = tempMessage +"\r\n";
            fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
            Kogger.error(getClass(), e1);
            result[0] = errMessage;
            result[1] = TotalStructureList;
            this.FileClose();
            return result;
        }
	
	if(!StringUtils.isEmpty(errMessage)){
	    result[0] = errMessage;
            result[1] = TotalStructureList;
            this.FileClose();
            return result;
	}
	
	if (chkItem.equals("1")) {
	    // if(headCode.equals("") || headCode ==null)
	    // {
	    String strBoxQuantity = "";
	    ArrayList<Hashtable<String, String>> vc = this.getHeadBomInfo(chkItem, headCode, fromDate, toDate);
	    
	    for (int q = 0; q < vc.size(); q++) {
		headCode = StringUtil.checkNull(vc.get(q).get("itemCode"));
		strBoxQuantity = StringUtil.checkNull(vc.get(q).get("boxQuantity"));
		if (!headCode.equals("")) {
		    fw.write(":::::::::::::: [부품번호] : " + q + " 번째 \n");
		    TotalStructureList = this.getGenBomInfo(chkItem, headCode, fromDate, toDate, strBoxQuantity,false);
		    
		    try {
			this.getSubStructure(TotalStructureList, chkItem, fromDate, toDate);
		    } catch (Exception e) {
			tempMessage = getClass()+" getSubStructure 호출 중 Exception 발생! 관리자에게 문의하세요.\r\n"+this.getStackTrace(e);
			errMessage += tempMessage +"\r\n";
			fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
			Kogger.error(getClass(), e);
			result[0] = errMessage;
			result[1] = TotalStructureList;
			this.FileClose();
			return result;
		    }
		}
	    }
	    
	    for(int i=0; i<BomAllstructure.size(); i++){
		hashtab2 = (Hashtable<String, String>)BomAllstructure.get(i);
		TotalStructureList.add(hashtab2);
	    }

	} else if (chkItem.equals("2")) {
	    
	    TotalStructureList = this.getMoldBomInfo(chkItem, headCode, fromDate, toDate);
            
	} else {
	    Kogger.debug(getClass(), "ItemType check value Must be input 1 or 2");
	}
	
	for (int i = 0; i < TotalStructureList.size(); i++) {
	    String parent = (String)((Hashtable<String, String>)TotalStructureList.get(i)).get("MATNR");
	    String child = (String)((Hashtable<String, String>)TotalStructureList.get(i)).get("IDNRK");
	    try {
		if(!PartBaseHelper.service.existWTPartNumber(parent,"")){
		    tempMessage = "부품 "+parent+" 가 PLM에 존재하지 않습니다.";
		    errMessage += tempMessage+"\r\n";
		    fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
		}
		if(!PartBaseHelper.service.existWTPartNumber(child,"")){
		    tempMessage = parent+" 의 구성부품 "+child+" 가 PLM에 존재하지 않습니다.";
		    errMessage += tempMessage +"\r\n";
		    fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
		}
            }catch (Exception e) {
        	tempMessage = "\r\n\r\n\r\n\r\n ["+parent+"] 의 구성부품 ["+child+"] ValidationCheck 오류 발생!\r\n\r\n\r\n"+this.getStackTrace(e);
        	errMessage = tempMessage +"\r\n";
        	fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
                Kogger.error(getClass(), e);
                
        	result[0] = errMessage;
        	result[1] = TotalStructureList;
        	this.FileClose();
        	return result;
        	
            }
	}
	
	if(((TotalStructureList!=null && TotalStructureList.size() < 1)) || !StringUtils.isEmpty(errMessage)){
	    if((TotalStructureList!=null && TotalStructureList.size() < 1)){
		errMessage += "구성부품이 존재하지 않습니다.\r\n";
	    }
	    this.FileClose();
	}
	
	result[0] = errMessage;
	result[1] = TotalStructureList;
	
	return result;
    }
    
    
    
    /* getMigResultFromExcel (BOM 구조 EXCEL 일괄 업로드) 2015.06.23 황정태 작성 
     * ERP로부터 모든 하위 레벨의 BOM구조를 가져오고, 생성된 BOM구조(ArrayList)를 파라미터로 KETBomHelper.service.setKetPartUsageLink 를 호출, PLM 최신버전을 대상으로 BOM을 동기화하는 것이 목적이다
     */
    public Object[] getMigResultFromExcel(String chkItem, String headCode, String fromDate, String toDate, String strFileName, FileUploader uploader) throws IOException {
	Kogger.debug(getClass(), "################### chkItem ::::::::::: " + chkItem);
	
	File dir = new File(strDirPath);
	if (!dir.exists()) { // 로그 저장 폴더가 존재하지 않는 경우 생성함
	    dir.mkdir();
	}

	Kogger.debug(getClass(), "@@@@@@@@@ strFilePath : " + strDirPath + "\\" + strFileName);
	File file = new File(strDirPath + "\\" + strFileName);
	if (!file.exists()) { // 로그 저장 파일이 존재하지 않는 경우 생성함
	    file.createNewFile();
	}

	// 로그 남길 파일 생성
	fw = new FileWriter(file, true);

	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.write(":::::::::::::: SAP BOM INTERFACE  \n");
	fw.write(":::::::::::::: START TIME :: " + currentDate + " " + currentTime + "\n");
	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.flush();
	
	
	ArrayList<Hashtable<String, String>> excel1LevelList = new <Hashtable<String, String>>ArrayList();
	ArrayList<Hashtable<String, String>> TotalStructureList = new <Hashtable<String, String>>ArrayList();
	
	ArrayList<Hashtable<String, String>> HeadCheckList = new <Hashtable<String, String>>ArrayList();
	Hashtable<String, String> Headtab = new <String, String>Hashtable();
	Hashtable<String, String> SubstructureTab;
	
	String errMessage = "";
	String tempMessage = "";    
	Vector files = uploader.getFiles();
	int cnt = 0;
	WBFile file_;
	File excel;
	FileInputStream fis;
	HSSFWorkbook workBook;
	HSSFSheet sheet;
	Iterator<Row> rowIterator;
	Row row = null;
	
	//boolean reSult = false;
	Object[] result = new Object[2];
	if (chkItem.equals("1")) {
	    // if(headCode.equals("") || headCode ==null)
	    // {
	    String strBoxQuantity = "";
	    
	    Kogger.debug(getClass(), "===== Excel Leading Start =====");
	    for (int i = 0; i < files.size(); i++) {
		file_ = (WBFile) files.elementAt(i);
		excel = file_.getFile();
		try {
		    fis = new FileInputStream(excel);
		    workBook = new HSSFWorkbook(fis);
		    sheet = workBook.getSheetAt(0);
		    rowIterator = sheet.iterator();
		    while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if(row.getRowNum() > 0){//엑셀 헤더 제외
			    String partNo = getStringValue(row.getCell(Short.parseShort("0")));
			    partNo = partNo.trim().toUpperCase();
			    Kogger.debug(getClass(), row.getRowNum()+" Excel Leading.. "+partNo);
			    if(!PartBaseHelper.service.existWTPartNumber(partNo,"")){
				tempMessage = "Excel "+(row.getRowNum()+1)+"번째 : 부품 "+partNo+" 가 PLM에 존재하지 않습니다.";
				errMessage += tempMessage +"\r\n";
				fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
			    }
			    if(StringUtils.isEmpty(errMessage)){
				HeadCheckList = this.getHeadBomInfo(chkItem, partNo, fromDate, toDate);//엑셀로 넘겨받은 품번 루프 돌면서 하위 자부품이 존재하는 모부품인지 판단하여 모부품일 경우만 Headtab에 쌓는다
				for (int q = 0; q < HeadCheckList.size(); q++) {
				    headCode = StringUtil.checkNull(HeadCheckList.get(q).get("itemCode"));
				    strBoxQuantity = StringUtil.checkNull(HeadCheckList.get(q).get("boxQuantity"));
				    if (!headCode.equals("")) {
					Headtab.put("itemCode"+cnt, headCode);
					Headtab.put("boxQuantity"+cnt, strBoxQuantity);
					cnt++;
				    }
			        }
			    }
			}
			
		    }
		} catch (IOException e) {
		    tempMessage = getClass()+" 제품 Excel File Leading 중 IOException 발생! 관리자에게 문의하세요.\r\n"+this.getStackTrace(e);
		    errMessage += tempMessage +"\r\n";
		    fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
		    Kogger.error(getClass(), e);
		    
		    result[0] = errMessage;
		    result[1] = TotalStructureList;
		    this.FileClose();
		    return result;
		    
	        } catch (Exception e) {
	            tempMessage = getClass()+" 제품 Excel File Leading 중 Exception 발생! 관리자에게 문의하세요.\r\n"+this.getStackTrace(e);
	            errMessage += tempMessage+"\r\n";
	            fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
	            Kogger.error(getClass(), e);
	            
	            result[0] = errMessage;
		    result[1] = TotalStructureList;
		    this.FileClose();
		    return result;
	        }
            }
	    Kogger.debug(getClass(), "===== Excel Leading End =====");
	    Kogger.debug(getClass(), "=============================");
	    Kogger.debug(getClass(), "=============================");
	    Kogger.debug(getClass(), "=============================");
	    Kogger.debug(getClass(), "===== Get BOM Structure Start =====");
	    
	    if(!StringUtils.isEmpty(errMessage)){
		cnt = 0;
	    }
	    for (int x = 0; x < cnt; x++) {
		fw.write(":::::::::::::: [부품번호] : " + x + " 번째 \n");
		try {
		    Kogger.debug(getClass(), row.getRowNum()+" Get BOM Structure.. "+Headtab.get("itemCode"+x));
		    excel1LevelList = getGenBomInfo(chkItem, Headtab.get("itemCode"+x), fromDate, toDate, Headtab.get("boxQuantity"+x),false); //하위 1LEVEL 자부품 리스트를 가져온다
		}catch(Exception e){
		    tempMessage = getClass()+" getGenBomInfo 호출 중 Exception 발생! 관리자에게 문의하세요.\r\n"+this.getStackTrace(e);
		    errMessage += tempMessage +"\r\n";
		    fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
	            Kogger.error(getClass(), e);
	            
	            result[0] = errMessage;
		    result[1] = TotalStructureList;
		    this.FileClose();
		    return result;
		}
		for (int y = 0; y < excel1LevelList.size(); y++) {//가져온 하위 1LEVEL 자부품리스트를 TotalStructureList에 담는다
		    SubstructureTab = (Hashtable<String, String>)excel1LevelList.get(y);
		    TotalStructureList.add(SubstructureTab);
		}
		
		try {
		    this.getSubStructure(excel1LevelList, chkItem, fromDate, toDate); //가져온 하위 1LEVEL 자부품리스트를 getSubStructure로 넘겨 최하위 레벨까지의 Structure를 구한다(결과적으로 전역변수인 BomAllstructure에 쌓인다)
		} catch (Exception e) {
		    tempMessage = getClass()+" getSubStructure 호출 중 Exception 발생! 관리자에게 문의하세요.\r\n"+this.getStackTrace(e);
		    errMessage += getClass()+ tempMessage +"\r\n";
		    fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
	            Kogger.error(getClass(), e);
	            
	            result[0] = errMessage;
		    result[1] = TotalStructureList;
		    this.FileClose();
		    return result;
		}
		
	    }
	    Kogger.debug(getClass(), "===== Get BOM Structure End =====");
	    
	    for(int i=0; i<BomAllstructure.size(); i++){//최초 1레벨 BOM리스트 제외 모든 하위 레벨의 BOM구조가 축적된 BomAllstructure로 부터 데이터를 가져온다
		SubstructureTab = (Hashtable<String, String>)BomAllstructure.get(i);
		TotalStructureList.add(SubstructureTab);
	    }
	    
	} else if (chkItem.equals("2")) {
	    excel1LevelList = null;
	    TotalStructureList = new <Hashtable<String, String>>ArrayList();
	    for (int i = 0; i < files.size(); i++) {
		file_ = (WBFile) files.elementAt(i);
		excel = file_.getFile();
		try {
		    fis = new FileInputStream(excel);
		    workBook = new HSSFWorkbook(fis);
		    sheet = workBook.getSheetAt(0);
		    rowIterator = sheet.iterator();
		    while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if(row.getRowNum() > 0){//엑셀 헤더 제외
			    String partNo = getStringValue(row.getCell(Short.parseShort("0")));
			    partNo = partNo.trim().toUpperCase();
			    
			    if(!PartBaseHelper.service.existWTPartNumber(partNo,"")){
				tempMessage = "Excel "+(row.getRowNum()+1)+"번째 : 부품 "+partNo+" 가 PLM에 존재하지 않습니다.";
				errMessage += tempMessage +"\r\n";
				fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
			    }
			    
			    if(StringUtils.isEmpty(errMessage)){
				excel1LevelList = this.getMoldBomInfo(chkItem, partNo, fromDate, toDate);
				for (int q = 0; q < excel1LevelList.size(); q++) {
				    TotalStructureList.add((Hashtable)excel1LevelList.get(q));   
				}
			    }
			}
		    }
		} catch (IOException e) {
		    tempMessage = getClass()+" 금형 Excel File Leading 중 Exception 발생! 관리자에게 문의하세요.\r\n"+this.getStackTrace(e);
		    errMessage += tempMessage + "\r\n";
		    fw.write(":::::::::::::: Error !!! :: " + errMessage +"\n");
		    Kogger.error(getClass(), e);
		    
	        } catch (Exception e) {
	            tempMessage = getClass()+" 금형 Excel File Leading 중 Exception 발생! 관리자에게 문의하세요.\r\n"+this.getStackTrace(e);
	            errMessage += tempMessage  +"\r\n";
	            fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
	            Kogger.error(getClass(), e);
	        }
           }
	} else {
	    Kogger.debug(getClass(), "ItemType check value Must be input 1 or 2");
	    errMessage += "ItemType check value Must be input 1 or 2";
	    //reSult = false;
	}
	
	if(StringUtils.isEmpty(errMessage)){//여기까지 에러가 없었다면 Validation check 시작한다. 에러가 있다면 어차피 실패이므로 굳이 체크안함(과부화피하기위함). 
	    for (int i = 0; i < TotalStructureList.size(); i++) {
		String parent = (String)((Hashtable<String, String>)TotalStructureList.get(i)).get("MATNR");
		String child = (String)((Hashtable<String, String>)TotalStructureList.get(i)).get("IDNRK");
		try {
		    if(!PartBaseHelper.service.existWTPartNumber(parent,"")){
			tempMessage = "부품 "+parent+" 가 PLM에 존재하지 않습니다.";
			errMessage += tempMessage+"\r\n";
			fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
	            }
		    if(!PartBaseHelper.service.existWTPartNumber(child,"")){
			tempMessage = parent+" 의 구성부품 "+child+" 가 PLM에 존재하지 않습니다.";
			errMessage += tempMessage+"\r\n";
			fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
		    }
	        }catch (Exception e) {
	            tempMessage = "\r\n\r\n\r\n\r\n ["+parent+"] 의 구성부품 ["+child+"] ValidationCheck 오류 발생!\r\n\r\n\r\n"+this.getStackTrace(e);
	            errMessage = tempMessage +"\r\n";
	            fw.write(":::::::::::::: Error !!! :: " + tempMessage +"\n");
	            Kogger.error(getClass(), e);
	            result[0] = errMessage;
	            result[1] = TotalStructureList;
	            this.FileClose();
	            return result;
	        }
	   }
	}
	
	if(((TotalStructureList!=null && TotalStructureList.size() < 1)) || !StringUtils.isEmpty(errMessage)){
	    if((TotalStructureList!=null && TotalStructureList.size() < 1)){
		errMessage += "구성부품이 존재하지 않습니다.\r\n";
	    }
	    this.FileClose();
	}
	
	result[0] = errMessage;
	result[1] = TotalStructureList;

	return result;
    }
    
    //하위 Level의 모든 bom구조를 create한다. 재귀호출을 통해 반복적으로 getGenBomInfo를 호출하여 전역변수인 BomAllstructure 모든 bom구조가 생성됨 2015.06.19 by 황정태
    private void getSubStructure(ArrayList<Hashtable<String, String>> list, String chkItem, String fromDate, String toDate) throws Exception{
	ArrayList<Hashtable<String, String>> HeadCheck;
	ArrayList<Hashtable<String, String>> SubStructure;
	
	for (int i = 0; i < list.size(); i++) {
	    String Sublevelpart = ((Hashtable<String, String>)list.get(i)).get("IDNRK"); //자부품
	    if(!"".equals(Sublevelpart) && Sublevelpart!= null){
		HeadCheck = this.getHeadBomInfo(chkItem, Sublevelpart , fromDate, toDate); //자부품을 가지고 있는 모부품이면 list로 리턴받음
		for (int z = 0; z < HeadCheck.size(); z++) {
		    String itemCode = ((Hashtable<String, String>)HeadCheck.get(z)).get("itemCode");
		    String boxQuantity = ((Hashtable<String, String>)HeadCheck.get(z)).get("boxQuantity");
		    if(!"".equals(itemCode)){
			//자부품을 가지고 있는 모품번이면 자부품리스트를 리턴받아 재귀 호출
			SubStructure = getGenBomInfo(chkItem, itemCode, fromDate, toDate, boxQuantity, true);
			if(SubStructure.size()>0){
			    getSubStructure(SubStructure, chkItem, fromDate, toDate);
			}
	            }
	        }
	    }
	}
    }
    
    
    public boolean setKetPartUsageLink(ArrayList<Hashtable<String, String>> list, Integer chk) throws IOException{
	
	boolean reSult = true;
	try {
	    if (!KETBomHelper.service.setKetPartUsageLink(list, chk, fw)) {
		reSult = false;
		Kogger.debug(getClass(), ":::::::::::::: [실패!!]");
		fw.write(":::::::::::::: [실패!!] \n");
	    } else {
		Kogger.debug(getClass(), ":::::::::::::: [성공!!]");
		fw.write(":::::::::::::: [성공!!] \n");
	    }
	} catch (Exception e) {
            fw.write(":::::::::::::: Error !!! KETBomHelper.service.setKetPartUsageLink 수행 중 오류발생 :: \n" + this.getStackTrace(e) +"\n");
            Kogger.error(getClass(), e);
	    reSult = false;
	} finally{
	    this.FileClose();
	}
	
	return reSult;
    }
    
    public boolean getMigResult(String chkItem, String headCode, String fromDate, String toDate, String strFileName) throws IOException {
	Kogger.debug(getClass(), "################### chkItem ::::::::::: " + chkItem);

	File dir = new File(strDirPath);
	if (!dir.exists()) { // 로그 저장 폴더가 존재하지 않는 경우 생성함
	    dir.mkdir();
	}

	Kogger.debug(getClass(), "@@@@@@@@@ strFilePath : " + strDirPath + "\\" + strFileName);
	File file = new File(strDirPath + "\\" + strFileName);
	if (!file.exists()) { // 로그 저장 파일이 존재하지 않는 경우 생성함
	    file.createNewFile();
	}

	// 로그 남길 파일 생성
	fw = new FileWriter(file, true);

	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.write(":::::::::::::: SAP BOM INTERFACE  \n");
	fw.write(":::::::::::::: START TIME :: " + currentDate + " " + currentTime + "\n");
	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.flush();
	ArrayList<Hashtable<String, String>> list = null;
	boolean reSult = false;
	if (chkItem.equals("1")) {
	    // if(headCode.equals("") || headCode ==null)
	    // {
	    String strBoxQuantity = "";
	    ArrayList<Hashtable<String, String>> vc = this.getHeadBomInfo(chkItem, headCode, fromDate, toDate);
	    for (int q = 0; q < vc.size(); q++) {
		headCode = StringUtil.checkNull(vc.get(q).get("itemCode"));
		strBoxQuantity = StringUtil.checkNull(vc.get(q).get("boxQuantity"));
		if (!headCode.equals("")) {
		    fw.write(":::::::::::::: [부품번호] : " + q + " 번째 \n");
		    list = this.getGenBomInfo(chkItem, headCode, fromDate, toDate, strBoxQuantity,false);

		    /*if (this.getGenBomInfo(chkItem, headCode, fromDate, toDate, strBoxQuantity)) {
			reSult = true;
		    }
		 /*   if(!this.getGenBomInfo(chkItem, headCode, fromDate, toDate, strBoxQuantity)){
			reSult = false;
		    }*/
		}
	    }

	    // }else{
	    // if( this.getGenBomInfo( chkItem, headCode, fromDate, toDate ) )
	    // {
	    // reSult = true;
	    // }
	    // }

	} else if (chkItem.equals("2")) {
	    
	    
	    list = this.getMoldBomInfo(chkItem, headCode, fromDate, toDate);
            
	} else {
	    Kogger.debug(getClass(), "ItemType check value Must be input 1 or 2");
	    reSult = false;
	}

	if(list.size()>0){
	    if(this.setKetPartUsageLink(list,Integer.parseInt(chkItem))){
		reSult = true;
	    }
	}else{
	    reSult = false;
	    this.FileClose();
	}
	
	return reSult;
    }

    // 제품 BOM 의 모자관계 정보를 가져온다.
    private ArrayList getGenBomInfo(String chkItem, String headCode, String fromDate, String toDate, String strBoxQuantity, boolean Substructure)
	    throws IOException {
	Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	Kogger.debug(getClass(), ">>>>> getBomInfo Start....");
	Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	boolean reSult = true;
	Hashtable<String, String> ht = null;
	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
	ArrayList<Hashtable<String, String>> tempSubStructurelist = new ArrayList<Hashtable<String, String>>(); 
	Client client = null;
	IRepository repository = null;

	try {
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_MIG_BOM");
	    Function function = tmpl.getFunction();

	    function.getImportParameterList().setValue(chkItem, "I_ZOPTB");
	    function.getImportParameterList().setValue(headCode, "I_MATNR");
	    function.getImportParameterList().setValue(fromDate, "I_ERDAT_FR");
	    function.getImportParameterList().setValue(toDate, "I_ERDAT_TO");

	    Table tables = function.getTableParameterList().getTable("T_BOMIN");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(getClass(), "Migration [ getbomgen ] >>> " + e.toString());
	    }

	    String startDate = "";
 	    for (int p = 0; p < tables.getNumRows(); p++) {
		tables.setRow(p);

		startDate = StringUtil.checkNull(tables.getString("DATUV"));
		if (startDate.equals("0000-00-00")) {
		    startDate = "";
		}
		ht = new Hashtable<String, String>();
		ht.put("MATNR", StringUtil.checkNull(tables.getString("MATNR"))); // 자재번호
		ht.put("STLNR", StringUtil.checkNull(tables.getString("STLNR"))); // BOM 번호 (SAP 내부 관리번호)
		ht.put("IDNRK", StringUtil.checkNull(tables.getString("IDNRK"))); // BOM 구성부품
		ht.put("ICT", StringUtil.checkNull(tables.getString("POSTP"))); // 품목범주 (BOM)
		ht.put("POSNR", StringUtil.checkNull(tables.getString("POSNR"))); // BOM 품목번호
		ht.put("SORTF", StringUtil.checkNull(tables.getString("SORTF"))); // 정렬문자열
		ht.put("MEINS", StringUtil.checkNull(tables.getString("MEINS"))); // 구성부품단위
		ht.put("MENGE", StringUtil.checkNull(tables.getString("MENGE"))); // 구성부품수량
		ht.put("BMENG", StringUtil.checkNull(strBoxQuantity)); // 기준수량(헤더인경우 존재함)
		ht.put("ERFMG", StringUtil.checkNull(tables.getString("ERFMG"))); // 단위수량 (포장재는 제외하고 구성부품수량을 모부품의 기준수량으로 나눈 값) :: 실제 에디터에
		                                                                  // 보여질 수량
		ht.put("POTX1", StringUtil.checkNull(tables.getString("POTX1"))); // BOM 품목 텍스트(라인 1)
		ht.put("POTX2", StringUtil.checkNull(tables.getString("POTX2"))); // BOM 품목 텍스트(라인 2)
		ht.put("LGORT", StringUtil.checkNull(tables.getString("LGORT"))); // 생산오더에 대한 출고위치
		ht.put("DATUV", startDate); // 효력시작일
		ht.put("LOEKZ", StringUtil.checkNull(tables.getString("LOEKZ"))); // BOM 삭제표시
		ht.put("CHILDV", "0.0"); // 자부품 버전 (0으로 셋팅)
		ht.put("NBOMV", "0.0"); // BOM 버전 (0으로 셋팅)
		
		list.add(ht);
		if(Substructure){
		    BomAllstructure.add(ht);
		}
	    }
	 // Kogger.debug(getClass(), ">>>>> 제품 Data : "+ ht );
	 		
	    fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	    fw.flush();

	    String r1 = (String) function.getExportParameterList().getValue("E_RETURN");
	    int c1 = function.getExportParameterList().getInt("E_CNT");
	    String m1 = (String) function.getExportParameterList().getValue("E_MSG");
	    Kogger.debug(getClass(), "------------------------------->>> gen flag : " + r1);
	    Kogger.debug(getClass(), "------------------------------->>> gen msg : " + m1);
	} catch (Exception e) {
	    reSult = false;
	    Kogger.error(getClass(), e);
	} finally {

	    client.disconnect();
	    repository = null;
	}

	return list;
    }

    // 금형 BOM 정보를 가져온다.
    private ArrayList getMoldBomInfo(String chkItem, String headCode, String fromDate, String toDate) throws IOException {

	Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	Kogger.debug(getClass(), ">>>>> getBomInfo Start....");
	Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
	Hashtable<String, String> ht = null;
	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
	Client client = null;
	IRepository repository = null;

	try {
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_MIG_BOM");
	    Function function = tmpl.getFunction();

	    function.getImportParameterList().setValue(chkItem, "I_ZOPTB");
	    function.getImportParameterList().setValue(headCode, "I_MATNR");
	    function.getImportParameterList().setValue(fromDate, "I_ERDAT_FR");
	    function.getImportParameterList().setValue(toDate, "I_ERDAT_TO");

	    Table tables = function.getTableParameterList().getTable("T_BOMIM");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(getClass(), "Interface[getbommold]>>> " + e.toString());
	    }
	    int itemSeq = 10;
	    String designDate = "";
	    String preDieNumber = "";
	    String dieNumber = "";
	    KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
	    for (int i = 0; i < tables.getNumRows(); i++) {
		tables.setRow(i);
		dieNumber = StringUtil.checkNull(tables.getString("DDCNO"));

		// 이전 DieNo가 비어있지 않은 경우에만 비교함 (최초 입력은 제외함)
		if (!preDieNumber.equals("")) {
		    // DieNo 가 같은 경우는 itemSeq 계속 증가
		    if (preDieNumber.equals(dieNumber)) {
			itemSeq += 10;
		    } else // DieNo 가 다른 경우는 itemSeq 초기화
		    {
			itemSeq = 10;
		    }
		}

		designDate = StringUtil.checkNull(tables.getString("DATUV"));
		if (designDate.equals("0000-00-00")) {
		    designDate = "";
		}
		ht = new Hashtable<String, String>();
		ht.put("MATNR", dieNumber); // 금형번호 (제품이랑 같이 쓰기 위해 MATNR으로 넣음)
		ht.put("IDNRK", StringUtil.checkNull(tables.getString("IDNRK"))); // 금형부품번호
		ht.put("EKGRP", StringUtil.checkNull(tables.getString("EKGRP"))); // 구매 그룹
		ht.put("WERKS", StringUtil.checkNull(tables.getString("WERKS"))); // 플랜드
		ht.put("FINSH", StringUtil.checkNull(tables.getString("FINSH"))); // BOM 단종
		ht.put("MAKTX", StringUtil.checkNull(tables.getString("MAKTX"))); // 금형부품설명
		// ht.put( "ODNRK", (i+1)+"0" ); // 금형부품번호
		ht.put("ODNRK", itemSeq + ""); // 금형부품번호
		ht.put("MENGE", StringUtil.checkNull(tables.getString("MENGE"))); // 수량
		ht.put("MEINS", StringUtil.checkNull(tables.getString("MEINS"))); // 기본단위
		ht.put("DATUV", designDate); // 유효일자
		ht.put("ATWRT", StringUtil.checkNull(tables.getString("ATWRT"))); // 재질
		ht.put("HARDN", StringUtil.checkNull(tables.getString("HARDN"))); // 경도(From)
		ht.put("HARDT", StringUtil.checkNull(tables.getString("HARDT"))); // 경도(To)
		ht.put("LOEKZ", StringUtil.checkNull(tables.getString("LOEKZ"))); // BOM 삭제표시
		ht.put("CHILDV", "0.0"); // 자부품 버전 (0으로 셋팅)
		ht.put("NBOMV", "0.0"); // BOM 버전 (0으로 셋팅)
		// Kogger.debug(getClass(), ">>>>> 금형 Data : "+ ht );

		// itemSeq 초기화를 위해 이전 dieNo를 저장함
		list.add(ht);
		preDieNumber = dieNumber;
	    }
	    String r2 = (String) function.getExportParameterList().getValue("E_RETURN");
	    int c2 = function.getExportParameterList().getInt("E_CNT");
	    String m2 = (String) function.getExportParameterList().getValue("E_MSG");
	    Kogger.debug(getClass(), "------------------------------->>> mold flag : " + r2);
	    Kogger.debug(getClass(), "------------------------------->>> mold msg : " + m2);

	    Kogger.debug(getClass(), ":::::::::::::: 금형 BOM INTERFACE [size] : " + c2 + " 개 ");
	    fw.write(":::::::::::::: 금형 BOM INTERFACE [size] : " + c2 + " 개 \n");
	    fw.flush();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    client.disconnect();
	    repository = null;
	}
	//Kogger.debug(getClass(), "################## >>>>>>>>>>>>>>>>>>>> reSult : " + reSult);
	return list;
    }

    // 제품 BOM 의 모부품 목록을 가져온다. (BOM 헤더정보만 가져옴)
    public ArrayList getHeadBomInfo(String chkItem, String headCode, String fromDate, String toDate) throws IOException {
	Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	Kogger.debug(getClass(), ">>>>> getBomInfo Start....");
	Kogger.debug(getClass(), ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

	boolean reSult = true;
	// Vector vc = new Vector();
	ArrayList<Hashtable<String, String>> arr = new ArrayList<Hashtable<String, String>>();
	Client client = null;
	IRepository repository = null;

	try {
	    client = RFCConnect.getConnection();
	    client.connect();

	    repository = JCO.createRepository("BFREPOSITORY", client);

	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_MIG_BOM");
	    Function function = tmpl.getFunction();

	    function.getImportParameterList().setValue(chkItem, "I_ZOPTB");
	    function.getImportParameterList().setValue(headCode, "I_MATNR");
	    function.getImportParameterList().setValue(fromDate, "I_ERDAT_FR");
	    function.getImportParameterList().setValue(toDate, "I_ERDAT_TO");

	    Table tables = function.getTableParameterList().getTable("T_BOMHN");

	    try {
		client.execute(function);
	    } catch (Exception e) {
		Kogger.debug(getClass(), "Interface[getbomheader]>>> " + e.toString());
	    }

	    Kogger.debug(getClass(), ":::::::::::::: [총 모부품 수] : " + tables.getNumRows() + " 개 ");
	    fw.write(":::::::::::::: [총 모부품 수] : " + tables.getNumRows() + " 개 \n");

	    for (int j = 0; j < tables.getNumRows(); j++) {
		tables.setRow(j);

		Hashtable<String, String> vc = new Hashtable<String, String>();
		vc.put("itemCode", StringUtil.checkNull(tables.getString("MATNR")));
		vc.put("boxQuantity", StringUtil.checkNull(tables.getString("BMENG")));

		arr.add(vc);

		Kogger.debug(getClass(), ":::::::::::::: [부품번호] : " + StringUtil.checkNull(tables.getString("MATNR")));
		fw.write(":::::::::::::: [부품번호] : " + StringUtil.checkNull(tables.getString("MATNR")) + "\n");
	    }
	    fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	    fw.flush();

	    String r0 = (String) function.getExportParameterList().getValue("E_RETURN");
	    int c0 = function.getExportParameterList().getInt("E_CNT");
	    String m0 = (String) function.getExportParameterList().getValue("E_MSG");
	    Kogger.debug(getClass(), ">>>>> head flag : " + r0);
	    Kogger.debug(getClass(), ">>>>> head msg : " + m0);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    client.disconnect();
	    repository = null;
	}
	return arr;
    }
    
    private void FileClose() throws IOException{
	
	currentDate = DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd"));
	currentTime = DateUtil.getCurrentDateString(new SimpleDateFormat("HH:mm:ss"));
	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.write(":::::::::::::: END TIME :: " + currentDate + " " + currentTime + "\n");
	fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	fw.flush();
	fw.close();
    }
    
    private String getStackTrace(Throwable t){
	if(t == null) return "";
	try{
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    t.printStackTrace(new PrintStream(bout));
	    bout.flush();
	    String error = new String(bout.toByteArray());
	    return error;
	}catch(Exception ex){
	    return "";
	}
    }
    
    private String getStringValue(Cell cell) {
 	String value = "";

 	if (cell == null)
 	    return value;

 	switch (cell.getCellType()) {
 	case Cell.CELL_TYPE_FORMULA:
 	    value = cell.getCellFormula();
 	    break;
 	case Cell.CELL_TYPE_NUMERIC:
 	    value = Integer.toString(new Double(cell.getNumericCellValue()).intValue());
 	    break;
 	case Cell.CELL_TYPE_STRING:
 	    value = cell.getStringCellValue();
 	    break;
 	case Cell.CELL_TYPE_BLANK:
 	    break;
 	case Cell.CELL_TYPE_BOOLEAN:
 	    value = Boolean.toString(cell.getBooleanCellValue());
 	    break;
 	case Cell.CELL_TYPE_ERROR:
 	    value = "ERROR";
 	    break;
 	default:
 	}

 	return value.trim();
     }
}
