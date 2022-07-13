package ext.ket.edm.migration.stamping;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.DBProperties;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class MigStampingDownImgDwg implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigStampingDownImgDwg manager = new MigStampingDownImgDwg();

    public MigStampingDownImgDwg() {

    }

    public static void main(String[] args) {

	try {

	    String filePath = null;

	    if (args == null || args.length == 0)
		throw new Exception("@@ EpmDocument oid need !");
	    else
		filePath = args[0];

	    Kogger.debug(MigStampingDownImgDwg.class, "param1 epmOid = " + filePath);

	    Kogger.debug(MigStampingDownImgDwg.class, "@start");
	    MigStampingDownImgDwg.manager.saveFromExcel(filePath);
	    Kogger.debug(MigStampingDownImgDwg.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigStampingDownImgDwg.class, e);
	}
    }

    public void saveFromExcel(String filePath) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { filePath };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(filePath);
	}
    }

    public void executeMigration(String filePath) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();

	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    doTestDrawing(filePath);

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    // //////////////////////////////////////////////////////////
    // StampingDownLoadUtil
    // //////////////////////////////////////////////////////////

    public void doTestDrawing(String filePath) throws Exception {

	ReferenceFactory rf = new ReferenceFactory();

	// 전체 2d 가져오기
	Kogger.debug(getClass(), "**************** get 2d **************************");
	List<String> drwList = searchDrwAllList();
	// 3d 가져오기
	Kogger.debug(getClass(), "**************** get file **************************");
	for (String cad2Oid : drwList) {

	    EPMDocument epm2d = (EPMDocument) rf.getReference(cad2Oid).getObject();
	    String d2number = epm2d.getNumber();
	    Kogger.debug(getClass(), "2d :" + d2number);

	    // download file
	    downloadDrawing(epm2d, filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + d2number);

	}
	// 파일 다운로드

    }

    public void downloadDrawing(EPMDocument epm2d, String filePath) throws Exception {
	Kogger.debug(getClass(), "**************** down 2d **************************");
	execute2DFileInfo(epm2d, filePath);
    }

    private List<String> searchDrwAllList() throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    String query = getSearchDrwEPMDocQuery();

	    drwList = oDaoManager.queryForList(query, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {

		    String oid = rs.getString("OID");
		    return oid;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return drwList;
    }

    private String getSearchDrwEPMDocQuery() throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n");
	sb.append(" FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	sb.append(" WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2) \n");
	sb.append(" AND E.STATECHECKOUTINFO = 'c/i' \n");
	sb.append(" AND M.documentnumber in \n");
	sb.append(" ('CP110093' \n");
	sb.append(" ,'CP110118' \n");
	sb.append(" ,'EM450025-5' \n");
	sb.append(" ,'MG615573' \n");
	sb.append(" ,'MG615576' \n");
	sb.append(" ,'MG620403' \n");
	sb.append(" ,'MG625439' \n");
	sb.append(" ,'MG634063-5' \n");
	sb.append(" ,'MG653586-5' \n");
	sb.append(" ,'MG655531-5' \n");
	sb.append(" ,'MG670332' \n");
	sb.append(" ,'MG670336' \n");
	sb.append(" ,'MG675958' \n");
	sb.append(" ,'MG675959' \n");
	sb.append(" ,'MG675960' \n");
	sb.append(" ,'MG675961' \n");
	sb.append(" ,'ST731399-3' \n");
	sb.append(" ,'ST741354-3' \n");
	sb.append(" ,'ST791191-2' \n");
	sb.append(" ,'ST791192-2' \n");
	sb.append(" ,'ST791193-2' \n");
	sb.append(" ,'ST791194-2' \n");
	sb.append(" ,'ST791195-3' \n");
	sb.append(" ,'ST791196-3' \n");
	sb.append(" ,'ST791197-3') \n");

	return sb.toString();
    }

    // //////////////////////////////////////////////////////////
    // ModelStrucUtil
    // //////////////////////////////////////////////////////////

    // //////////////////////////////////////////////////////////
    // CadFileDownUtil
    // //////////////////////////////////////////////////////////

    public void execute2DFileInfo(EPMDocument epm2d, String filePath) throws Exception {
	boolean is2D = true;
	// getFileInfoWith3D2D(epm2d, !is2D, is2D, filePath);
	getFileInfoWith3D2DByRole(epm2d, !is2D, is2D, filePath);
	getFileInfoWith3D2DByRoleSecondary(epm2d, !is2D, is2D, filePath);
    }

//    /**
//     * 2D, 3D로 부터 파일 정보를 추출
//     * 
//     * @param epmDoc
//     * @param is3D
//     * @param is2D
//     * @param isOnlyDxf
//     * @throws Exception
//     */
//    private void getFileInfoWith3D2D(EPMDocument epmDoc, boolean is3D, boolean is2D, String filePath) throws Exception {
//
//	try {
//
//	    if (epmDoc != null) {
//		ContentHolder contentHolder = ContentHelper.service.getContents(epmDoc);
//		Vector contentList = ContentHelper.getContentListAll(contentHolder);
//		Kogger.debug(getClass(), "@@@ ContentHelper.service.getContents(epm)");
//		String fileName = null;
//
//		for (int i = 0; i < contentList.size(); i++) {
//
//		    ContentItem contentItem = (ContentItem) contentList.elementAt(i);
//		    Kogger.debug(getClass(), i + " contentItem = " + contentItem);
//
//		    ContentRoleType type = contentItem.getRole();
//		    if (ContentRoleType.PRIMARY != type)
//			continue;
//
//		    if (contentItem instanceof ApplicationData) {
//
//			fileName = ((ApplicationData) contentItem).getFileName();
//			if (fileName.equals("{$CAD_NAME}")) {
//			    fileName = epmDoc.getCADName();
//			}
//
//			Kogger.debug(getClass(), "fileName = " + fileName);
//
//			if (is3D) {
//			    if (fileName.endsWith(".asm") || fileName.endsWith(".prt")) {
//			    } else
//				continue;
//			} else if (is2D) {
//			    if (fileName.endsWith(".drw") || fileName.endsWith(".prt")) {
//			    } else
//				continue;
//			}
//
//			HashMap fileInfo = getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, filePath);
//
//		    } else {
//			Kogger.debug(getClass(), fileName);
//		    }
//		}
//	    }
//
//	} catch (PropertyVetoException pve) {
//	    throw pve;
//	}
//    }

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
    private HashMap getFileInfo(String fileName, EPMDocument epmDoc, ApplicationData appData, String filePath) throws Exception {

	HashMap fileInfo = new HashMap();

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

		fileInfo.put("fileName", fileName);
		fileInfo.put("roleType", temp);
		fileInfo.put("fileType", fileType);
		fileInfo.put("fileId", fileId);
		fileInfo.put("downURL", downURL);
		fileInfo.put("fileSize", fileSize);
		fileInfo.put("epmInfo", epmDoc.getNumber() + "." + epmDoc.getVersionIdentifier().getValue() + "." + epmDoc.getIterationIdentifier().getValue());

		if (filePath != null) {

		    InputStream inputstream = null;

		    try {
			inputstream = ContentServerHelper.service.findContentStream(appData);
		    } catch (Exception fvsie) {
			Kogger.error(getClass(), fvsie);
			throw fvsie;
		    }

		    File createDir = new File(filePath);
		    if (!createDir.exists())
			createDir.mkdir();

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

		    } catch (IOException iperr) {
			throw iperr;
		    }
		}
	    }

	} catch (Exception e) {
	    throw e;
	}

	return fileInfo;
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
		    // if (ContentRoleType.PRIMARY != type)
		    // continue;

		    if (contentItem instanceof ApplicationData) {

			fileName = ((ApplicationData) contentItem).getFileName();
			if (fileName.equals("{$CAD_NAME}")) {
			    fileName = epmDoc.getCADName();
			}

			Kogger.debug(getClass(), "fileName = " + fileName);

			if (is3D) {
			    if (fileName.endsWith(".asm") || fileName.endsWith(".prt")) {
			    } else
				continue;
			} else if (is2D) {
			    if (fileName.toLowerCase().endsWith(".dwg") || fileName.toLowerCase().endsWith(".drw") || fileName.toLowerCase().endsWith(".prt")) {
			    } else
				continue;
			}

			HashMap fileInfo = getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, filePath);

		    } else {
			Kogger.debug(getClass(), fileName);
		    }
		}
	    }

	} catch (PropertyVetoException pve) {
	    throw pve;
	}
    }
    
    private void getFileInfoWith3D2DByRoleSecondary(EPMDocument epmDoc, boolean is3D, boolean is2D, String filePath) throws Exception {
	
	try {
	    
	    if (epmDoc != null) {
		ContentHolder contentHolder = ContentHelper.service.getContents(epmDoc);
		QueryResult contentList = ContentHelper.service.getContentsByRole(contentHolder, ContentRoleType.SECONDARY);
		Kogger.debug(getClass(), "@@@ ContentHelper.service.getContentsByRole(epm, ContentRoleType.PRIMARY)");
		String fileName = null;
		
		while (contentList.hasMoreElements()) {
		    
		    ContentItem contentItem = (ContentItem) contentList.nextElement();
		    Kogger.debug(getClass(), " contentItem = " + contentItem);
		    
		    ContentRoleType type = contentItem.getRole();
		    // if (ContentRoleType.PRIMARY != type)
		    // continue;
		    
		    if (contentItem instanceof ApplicationData) {
			
			fileName = ((ApplicationData) contentItem).getFileName();
			if (fileName.equals("{$CAD_NAME}")) {
			    fileName = epmDoc.getCADName();
			}
			
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
			
			HashMap fileInfo = getFileInfo(fileName, epmDoc, (ApplicationData) contentItem, filePath);
			
		    } else {
			Kogger.debug(getClass(), fileName);
		    }
		}
	    }
	    
	} catch (PropertyVetoException pve) {
	    throw pve;
	}
    }
    
    
}
