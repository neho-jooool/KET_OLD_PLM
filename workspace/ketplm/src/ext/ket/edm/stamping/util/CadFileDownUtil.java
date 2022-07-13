package ext.ket.edm.stamping.util;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pom.DBProperties;
import wt.util.WTException;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

public class CadFileDownUtil {

    private boolean isTest = false;
    
    public void execute3DFileInfo(EPMDocument epm3d, String filePath) throws Exception {
	boolean is3D = true;
	// getFileInfoWith3D2D(epm3d, is3D, !is3D, filePath);
	getFileInfoWith3D2DByRole(epm3d, is3D, !is3D, filePath);
	// 이미지만 들어 있는 Stamping
	//getFileInfoWith3D2DByRoleSecondary(epm3d, is3D, !is3D, filePath); 
    }

    public void execute2DFileInfo(EPMDocument epm2d, String filePath) throws Exception {
	boolean is2D = true;
	// getFileInfoWith3D2D(epm2d, !is2D, is2D, filePath);
	getFileInfoWith3D2DByRole(epm2d, !is2D, is2D, filePath);
	// 이미지만 들어 있는 Stamping
	//getFileInfoWith3D2DByRoleSecondary(epm2d, !is2D, is2D, filePath);
    }

    /**
     * 2D, 3D로 부터 파일 정보를 추출
     * 
     * @param epmDoc
     * @param is3D
     * @param is2D
     * @param isOnlyDxf
     * @throws Exception
     */
    private void getFileInfoWith3D2D(EPMDocument epmDoc, boolean is3D, boolean is2D, String filePath) throws Exception {

	try {

	    if (epmDoc != null) {
		ContentHolder contentHolder = ContentHelper.service.getContents(epmDoc);
		Vector contentList = ContentHelper.getContentListAll(contentHolder);
		Kogger.debug(getClass(), "@@@ ContentHelper.service.getContents(epm)");
		String fileName = null;

		for (int i = 0; i < contentList.size(); i++) {

		    ContentItem contentItem = (ContentItem) contentList.elementAt(i);
		    Kogger.debug(getClass(), i + " contentItem = " + contentItem);

		    ContentRoleType type = contentItem.getRole();
		    if (ContentRoleType.PRIMARY != type)
			continue;

		    if (contentItem instanceof ApplicationData) {
			//첨부파일명과 epmdocumentmaster의 cadname이 다를 경우 스탬핑을 할 수 없어서 아래 구문은 주석처리하고 무조건 epmdoc의 cadname을 가져오도록 한다 2015.08.18 by 황정태
			/*fileName = ((ApplicationData) contentItem).getFileName();
			if (fileName.equals("{$CAD_NAME}")) {
			    fileName = epmDoc.getCADName();
			}*/
			fileName = epmDoc.getCADName();

			Kogger.debug(getClass(), "fileName = " + fileName);

			if (is3D) {
			    if (fileName.toLowerCase().endsWith(".asm") || fileName.toLowerCase().endsWith(".prt")) {
			    } else
				continue;
			} else if (is2D) {
			    if (fileName.toLowerCase().endsWith(".drw") || fileName.toLowerCase().endsWith(".prt") || fileName.toLowerCase().endsWith(".dwg")) {
			    } else
				continue;
			}

			getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, filePath);

		    } else {
			Kogger.debug(getClass(), fileName);
		    }
		}
	    }

	} catch (PropertyVetoException pve) {
	    throw new WTException(pve);
	}
    }
    
    /**
     * RDB쿼리를 통해 FileVault의 폴더경로를 반환한다. 2015.07.24 by 황정태
     * 
     * @param appData
     * @return
     * @throws Exception
     */
    private String getVaultPath(ApplicationData appData) throws Exception {
	
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	
	String VaultPath = StringUtils.EMPTY;
	try {
	    
	    try {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT REPLACE(FM.PATH,'\','\\')||'\\'||NVL(TO_CHAR(FI.UNIQUESEQUENCENUMBER,'FM0XXXXXXXXXXXXX'),'BLOB') VAULTPATH   \n");
	        sb.append("   FROM EPMDOCUMENT DI, 												\n");
	        sb.append("	   EPMDOCUMENTMASTER DM, 											\n");
	        sb.append("	   HOLDERTOCONTENT HC, 											        \n");
	        sb.append("	   APPLICATIONDATA AD, 											        \n");
	        sb.append("	   FVITEM FI, 												        \n");
	        sb.append("	   FVFOLDER FF, 												\n");
	        sb.append("	   FVMOUNT FM, 												        \n");
	        sb.append("	   FVHOST FH 												        \n");
	        sb.append("  WHERE DI.IDA3MASTERREFERENCE = DM.IDA2A2 	        \n");
	        sb.append("    AND FM.IDA3A5 = FF.IDA2A2 			\n");
	        sb.append("    AND FM.IDA3B5 = FH.IDA2A2 			\n");
	        sb.append("    AND FI.IDA3A4 = FF.IDA2A2 			\n");
	        sb.append("    AND AD.IDA3A5 = FI.IDA2A2 			\n");
	        sb.append("    AND HC.IDA3B5 = AD.IDA2A2 			\n");
	        sb.append("    AND HC.IDA3A5 = DI.IDA2A2 			\n");
	        sb.append("    AND AD.IDA2A2 = ?				\n");
			
		String query = sb.toString();
		List<String> alBindSql = new ArrayList<String>();
		alBindSql.add( CommonUtil.getOIDLongValue2Str( appData ));
		    
		VaultPath = oDaoManager.queryForObject(query, alBindSql, new RowSetStrategy<String>() {
		    
		    @Override
		    public String mapRow(ResultSet rs) throws SQLException {
			String VAULTPATH = rs.getString("VAULTPATH");
			return VAULTPATH;
		    }
		});
		    
	    } catch (Exception e) {
		throw e;
	    } finally {
		    
	    }
	    
	} catch (Exception e) {
	    throw e;
	} finally {

	}
	return VaultPath;
    }
    
    /**
     * getVaultPath를 호출하여 FileVault경로를 반환받고 Cad서버에 해당파일을 복사한다 2015.07.24 by 황정태
     * 
     * @param appData
     * @param fileName
     * @param filePath
     * @return
     * @throws Exception
     */
    
    private void getPassiveFileInfoToFileVault(ApplicationData appData, String fileName, String filePath) throws Exception {
	
	try {
	    
	    String VaultPath = getVaultPath(appData);
	    FileInputStream fin = new FileInputStream(VaultPath);
        	
	    FileUtil.checkDir(filePath);
	    File outputFile = new File(filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + fileName);
	    FileOutputStream fo = new FileOutputStream(outputFile);
	    
	    try {
		
		int j = 0;
		byte abyte0[] = new byte[(int) DBProperties.LOB_CHUNK_SIZE];
        	
		while ((j = fin.read(abyte0, 0, abyte0.length)) >= 0) {
		    fo.write(abyte0, 0, j);
		}
		
		fo.flush();
		fo.close();
		fin.close();
	    } catch (IOException e) {
		Kogger.error(getClass(), e);
        	throw e;
            }
	}catch (Exception e) {
	    throw new WTException(e);
	}
	
    }

    /**
     * 파일 정보 추출
     * 
     * @param fileName
     * @param epmDoc
     * @param appData
     * @param derivedImg
     * @return
     * @throws Exception
     */
    private void getFileInfo(String fileName, EPMDocument epmDoc, ApplicationData appData, String filePath) throws Exception {

//	HashMap fileInfo = new HashMap();

	try {

	    if (epmDoc != null) {

		ContentRoleType crt = appData.getRole();
		String temp = crt.getStringValue();
		Kogger.debug(getClass(), "temp = " + temp);

		String fileType = (String) appData.getRole().toString();
		Kogger.debug(getClass(), "Display = " + fileType);

		String fileId = PersistenceHelper.getObjectIdentifier(appData).toString();
		Kogger.debug(getClass(), "File ID = " + fileId);

		String downURL = (ContentHelper.getDownloadURL((ContentHolder) epmDoc, appData)).toString();

		Kogger.debug(getClass(), "Content URL = " + downURL);

		String fileSize = String.valueOf(((ApplicationData) appData).getFileSizeKB());
		Kogger.debug(getClass(), "fileSize = " + fileSize);

//		fileInfo.put("fileName", fileName);
//		fileInfo.put("roleType", temp);
//		fileInfo.put("fileType", fileType);
//		fileInfo.put("fileId", fileId);
//		fileInfo.put("downURL", downURL);
//		fileInfo.put("fileSize", fileSize);
//		fileInfo.put("epmInfo", epmDoc.getNumber() + "." + epmDoc.getVersionIdentifier().getValue() + "." + epmDoc.getIterationIdentifier().getValue());
		boolean inputstreamSuccess = true;
		if (filePath != null) {

		    InputStream inputstream = null;

		    try {
			inputstream = ContentServerHelper.service.findContentStream(appData);
		    } catch (Exception fvsie) {
			Kogger.error(getClass(), fvsie);
			Kogger.debug(getClass(), " findContentStream Err! Call getPassiveFileInfoToFileVault() Start ... ");
			/*** [ Start ] getPassiveFileInfoToFileVault 호출 by 황정태 2015.07.24 ****
			 *** findContentStream 사용시, 종종 파일볼트 mount 중복 에러가 발생하여 cad서버로 파일을 복사하지 못하는 경우가 존재함. 
			 *** 때문에 차선책으로 getPassiveFileInfoToFileVault을 사용한다.(만약 사용하지 않으려면 아래의 throw fvsie 주석 풀어야함) */
			
			inputstreamSuccess = false;
			getPassiveFileInfoToFileVault(appData, fileName, filePath);//RDB쿼리를 통해 ApplicationData의 물리적 파일 경로를 직접 찾고, cad서버로 파일을 복사한다
			
			/*** [ End ] getPassiveFileInfoToFileVault 호출 종료 by 황정태 2015.07.24 ****/
			
			//throw fvsie;
		    }
		    if(!inputstreamSuccess){
			Kogger.debug(getClass(), " Call getPassiveFileInfoToFileVault() End ");
		    }
		    if(inputstreamSuccess){
			FileUtil.checkDir(filePath);
        		File outputFile = new File(filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + fileName);
        		FileOutputStream fo = new FileOutputStream(outputFile);
        
        		try {
        		    
        		    int j = 0;
        		    byte abyte0[] = new byte[(int) DBProperties.LOB_CHUNK_SIZE];
        		    while ((j = inputstream.read(abyte0, 0, abyte0.length)) >= 0) {
        			fo.write(abyte0, 0, j);
        		    }
        		    
        		    fo.flush();
        		    fo.close();
        		    inputstream.close();
        		} catch (IOException e) {
        		    Kogger.error(getClass(), e);
        		    throw e;
        		}
		    }
		}
	    }

	} catch (Exception e) {
	    throw new WTException(e);
	}

//	return fileInfo;
    }

    private void getFileInfoWith3D2DByRole(EPMDocument epmDoc, boolean is3D, boolean is2D, String filePath) throws Exception {

	try {

	    if (epmDoc != null) {
		ContentHolder contentHolder = ContentHelper.service.getContents(epmDoc);
		QueryResult contentList = ContentHelper.service.getContentsByRole(contentHolder, ContentRoleType.PRIMARY);
		Kogger.debug(getClass(), "@@@ ContentHelper.service.getContentsByRole(epm, ContentRoleType.PRIMARY)");
		String fileName = null;

		while (contentList.hasMoreElements()) {

		    ContentItem contentItem = (ContentItem) contentList.nextElement();
		    Kogger.debug(getClass(), " contentItem = " + contentItem);

		    ContentRoleType type = contentItem.getRole();
		    if (ContentRoleType.PRIMARY != type)
			continue;

		    if (contentItem instanceof ApplicationData) {

			//첨부파일명과 epmdocumentmaster의 cadname이 다를 경우 스탬핑을 할 수 없어서 아래 구문은 주석처리하고 무조건 epmdoc의 cadname을 가져오도록 한다 2015.08.18 by 황정태
			/*fileName = ((ApplicationData) contentItem).getFileName();
			if (fileName.equals("{$CAD_NAME}")) {
			    fileName = epmDoc.getCADName();
			}*/
			fileName = epmDoc.getCADName();

			Kogger.debug(getClass(), "fileName = " + fileName);

			if (is3D) {
			    if (fileName.toLowerCase().endsWith(".asm") || fileName.toLowerCase().endsWith(".prt")|| StringUtils.contains(fileName.toLowerCase(), ".prt.")) {
			    } else
				continue;
			} else if (is2D) {
			    if (fileName.toLowerCase().endsWith(".drw") || fileName.toLowerCase().endsWith(".prt") || fileName.toLowerCase().endsWith(".dwg")) {
			    } else
				continue;
			}
			
			if(!isTest){
			    getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, filePath);
			    
			    // 금형 nx쪽 prt 내려줬을 때 _prt를 뺀 prt를 찾는 문제 처리함.
			    if(is3D && fileName.toLowerCase().endsWith("_prt.prt")){
				fileName = fileName.substring(0, fileName.length()-8) + ".prt";
				getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, filePath);
			    }
			}

		    } else {
			Kogger.debug(getClass(), fileName);
		    }
		}
	    }

	} catch (PropertyVetoException pve) {
	    throw new WTException(pve);
	}
    }
    
    private void getFileInfoWith3D2DByRoleSecondary(EPMDocument epmDoc, boolean is3D, boolean is2D, String filePath) throws Exception {
	
	try {
	    
	    if (epmDoc != null) {
		ContentHolder contentHolder = ContentHelper.service.getContents(epmDoc);
		QueryResult contentList = ContentHelper.service.getContentsByRole(contentHolder, ContentRoleType.SECONDARY);
		Kogger.debug(getClass(), "@@@ ContentHelper.service.getFileInfoWith3D2DByRoleSecondary(epm, ContentRoleType.SECONDARY)");
		String fileName = null;
		
		while (contentList.hasMoreElements()) {
		    
		    ContentItem contentItem = (ContentItem) contentList.nextElement();
		    Kogger.debug(getClass(), " contentItem = " + contentItem);
		    
		    ContentRoleType type = contentItem.getRole();
		    
		    if (contentItem instanceof ApplicationData) {
			
			//첨부파일명과 epmdocumentmaster의 cadname이 다를 경우 스탬핑을 할 수 없어서 아래 구문은 주석처리하고 무조건 epmdoc의 cadname을 가져오도록 한다 2015.08.18 by 황정태
			/*fileName = ((ApplicationData) contentItem).getFileName();
			if (fileName.equals("{$CAD_NAME}")) {
			    fileName = epmDoc.getCADName();
			}*/
			fileName = epmDoc.getCADName();
			
			Kogger.debug(getClass(), "fileName = " + fileName);
			
			if (is3D) {
			    if (fileName.endsWith(".asm") || fileName.endsWith(".prt")) {
			    } else
				continue;
			} else if (is2D) {
			    if (fileName.toLowerCase().endsWith(".tif") || fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".gif")) {
			    } else
				continue;
			}
			
			getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, filePath);
			
		    } else {
			Kogger.debug(getClass(), fileName);
		    }
		}
	    }
	    
	} catch (PropertyVetoException pve) {
	    throw pve;
	}
    }
    
//    public static void main(String[] args){
//	String fileName = "asdf_prt.prt";
//	String ret = fileName.substring(0, fileName.length()-8) + ".prt";
//	Kogger.debug(getClass(), ret);
//    }

}
