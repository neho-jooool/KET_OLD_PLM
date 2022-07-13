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
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.epm.structure.EPMMemberLink;
import wt.epm.structure.EPMStructureHelper;
import wt.epm.workspaces.EPMAsStoredConfigSpec;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.DBProperties;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.config.ConfigSpec;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class MigStampingDownCreo implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigStampingDownCreo manager = new MigStampingDownCreo();

    public MigStampingDownCreo() {

    }

    public static void main(String[] args) {

	try {

	    String filePath = null;

	    if (args == null || args.length == 0)
		throw new Exception("@@ EpmDocument oid need !");
	    else
		filePath = args[0];

	    Kogger.debug(MigStampingDownCreo.class, "param1 epmOid = " + filePath);

	    Kogger.debug(MigStampingDownCreo.class, "@start");
	    MigStampingDownCreo.manager.saveFromExcel(filePath);
	    Kogger.debug(MigStampingDownCreo.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigStampingDownCreo.class, e);
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
	Kogger.debug(getClass(), "**************** get 3d **************************");
	for (String cad2Oid : drwList) {

	    EPMDocument epm2d = (EPMDocument) rf.getReference(cad2Oid).getObject();
	    EPMDocument epm3d = null;
	    String d2number = epm2d.getNumber();
	    Kogger.debug(getClass(), "2d :" + d2number);

	    String cad3Oid = null;
	    List<String> asStoredModelList = searchAsStoredModelList(cad2Oid);
	    if (asStoredModelList.size() == 1) {
		cad3Oid = asStoredModelList.get(0);
		epm3d = (EPMDocument) rf.getReference(cad3Oid).getObject();
		Kogger.debug(getClass(), "3d :" + cad3Oid);
	    } else {
		String prefix2 = d2number.substring(0, d2number.indexOf("_"));
		for (String tempCad3Oid : asStoredModelList) {
		    EPMDocument tempEpm3d = (EPMDocument) rf.getReference(tempCad3Oid).getObject();
		    String d3number = tempEpm3d.getNumber();
		    String prefix3 = d3number.substring(0, d3number.indexOf("_"));
		    if (prefix2.equals(prefix3)) {
			cad3Oid = tempCad3Oid;
			epm3d = tempEpm3d;
			Kogger.debug(getClass(), "3d :" + cad3Oid);
			break;
		    }
		}
	    }

	    // download file
	    downloadDrawing(epm2d, filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + d2number);
	    downloadModel(epm3d, cad3Oid, filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + d2number);

	}
	// 파일 다운로드

    }

    public void downloadDrawing(EPMDocument epm2d, String filePath) throws Exception {
	Kogger.debug(getClass(), "**************** down 2d **************************");
	// CadFileDownUtil cadFileDownUtil = new CadFileDownUtil();
	// cadFileDownUtil.execute2DFileInfo(epm2d, filePath);
	execute2DFileInfo(epm2d, filePath);
    }

    public void downloadModel(EPMDocument epm3d, String cad3Oid, String filePath) throws Exception {

	// ModelStrucUtil modelStrucUtil = new ModelStrucUtil();
	// List<EPMDocument> epmDocList = modelStrucUtil.getModel(cad3Oid);
	// Kogger.debug(getClass(), "**************** down 3d **************************");
	// execute3DFileInfo(epm3d, filePath);

	Kogger.debug(getClass(), "**************** get 3d model *********************");
	List<EPMDocument> epmDocList = getModel(cad3Oid);
	// CadFileDownUtil cadFileDownUtil = new CadFileDownUtil();

	for (EPMDocument epmDoc : epmDocList) {
	    // cadFileDownUtil.execute3DFileInfo(epmDoc, filePath);
	    Kogger.debug(getClass(), "**************** down 3d **************************");
	    Kogger.debug(getClass(), "3d :" + epmDoc.getNumber() + " " + epmDoc.getVersionIdentifier().getValue() + " " + epmDoc.getIterationIdentifier().getValue());
	    execute3DFileInfo(epmDoc, filePath);
	}
    }

    private List<String> searchAsStoredModelList(String cad2Oid) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> modelAsStoredList = new ArrayList<String>();

	try {
	    String tempStr = cad2Oid.substring(cad2Oid.lastIndexOf(":") + 1);
	    String query = getSearchAsStoredModelQuery(tempStr);

	    modelAsStoredList = oDaoManager.queryForList(query, new StampRowSetStrategy<String>() {
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

	return modelAsStoredList;
    }

    private String getSearchAsStoredModelQuery(String cad2Oid) throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT A.CLASSNAMEKEYROLEBOBJECTREF||':'||A.IDA3B5 OID \n");
	sb.append(" FROM EPMASSTOREDMEMBER A \n");
	sb.append(" WHERE IDA3A5 IN \n");
	sb.append(" ( \n");
	sb.append("     SELECT IDA3A5 FROM EPMASSTOREDMEMBER \n");
	sb.append("     WHERE 1=1 \n");
	sb.append("     AND IDA3B5 = '" + cad2Oid + "' \n");
	sb.append(" ) \n");
	sb.append(" AND IDA3B5 IN \n");
	sb.append(" ( \n");
	sb.append("     SELECT E.IDA2A2 \n");
	sb.append("     FROM EPMREFERENCELINK L, EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	sb.append("     WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	sb.append("     AND L.IDA3B5 = M.IDA2A2 \n");
	sb.append("     AND L.REFERENCETYPE = 'DRAWING' \n");
	sb.append("     AND IDA3A5 = '" + cad2Oid + "' \n");
	sb.append(" ) \n");

	return sb.toString();
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

    // SELECT M2.DOCUMENTNUMBER NO_2D, M2.NAME AS NAME_2D, E2.VERSIONIDA2VERSIONINFO VER_2D, E2.ITERATIONIDA2ITERATIONINFO ITER_2D
    // , M.DOCUMENTNUMBER AS NO_3D, M.NAME AS NAME_3D, E.VERSIONIDA2VERSIONINFO VER_3D, E.ITERATIONIDA2ITERATIONINFO ITER_3D
    // , E2.CLASSNAMEA2A2||':'||E2.IDA2A2 OID_2D, E.CLASSNAMEA2A2||':'||E.IDA2A2 OID_3D
    // FROM EPMREFERENCELINK L, EPMDOCUMENT E, EPMDOCUMENTMASTER M, EPMDOCUMENT E2, EPMDOCUMENTMASTER M2
    // WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE
    // AND L.IDA3B5 = M.IDA2A2
    // AND L.REFERENCETYPE = 'DRAWING'
    // AND IDA3A5 IN
    // (
    // SELECT E.IDA2A2
    // FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M
    // WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE
    // AND E.LATESTITERATIONINFO = 1
    // AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2)
    // AND E.STATECHECKOUTINFO = 'c/i'
    // AND ( M.CADNAME LIKE '%.drw' )
    // AND M.DOCTYPE IN ('CADDRAWING')
    // )
    // AND M2.IDA2A2 = E2.IDA3MASTERREFERENCE
    // AND L.IDA3A5 = E2.IDA2A2
    // AND EXISTS ( SELECT * FROM EPMASSTOREDMEMBER WHERE 1=1 AND IDA3A5 IN (SELECT IDA3A5 FROM EPMASSTOREDMEMBER WHERE IDA3B5 = E2.IDA2A2 )
    // AND IDA3A5 IN (SELECT IDA3A5 FROM EPMASSTOREDMEMBER WHERE IDA3B5 = E.IDA2A2 ))
    // ORDER BY M2.DOCUMENTNUMBER, M.DOCUMENTNUMBER

    private String getSearchDrwEPMDocQuery() throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n");
	sb.append(" FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	sb.append(" WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2) \n");
	sb.append(" AND E.STATECHECKOUTINFO = 'c/i' \n");
	sb.append(" AND M.CADNAME LIKE '%.drw' \n");
	sb.append(" AND M.DOCTYPE IN ('CADDRAWING') \n");

	return sb.toString();
    }

    // //////////////////////////////////////////////////////////
    // ModelStrucUtil
    // //////////////////////////////////////////////////////////

    public List<EPMDocument> getModel(String cad3Oid) throws Exception {

	ReferenceFactory rf = new ReferenceFactory();
	EPMDocument epm3d = (EPMDocument) rf.getReference(cad3Oid).getObject();

	return getModel(epm3d);
    }

    public List<EPMDocument> getModel(EPMDocument epm3d) throws Exception {

	List<EPMDocument> epmDocList = new ArrayList<EPMDocument>();
	epmDocList.add(epm3d);
	getModelStructure(epm3d, epmDocList);

	return epmDocList;
    }

    public void getModelStructure(EPMDocument epm3d, List<EPMDocument> epmDocList) throws Exception {

	ConfigSpec asStoredSpec = EPMAsStoredConfigSpec.newEPMAsStoredConfigSpec((EPMDocument) epm3d);

	Kogger.debug(getClass(), " Start As Stored ");
	QueryResult result = EPMStructureHelper.service.navigateUsesToIteration(epm3d, null, false, asStoredSpec);

	if (result.size() > 0) {

	    while (result.hasMoreElements()) {

		Persistable[] persist = (Persistable[]) result.nextElement();
		EPMMemberLink member = null;
		EPMDocument modelDoc = null;

		member = (EPMMemberLink) persist[0];
		modelDoc = (EPMDocument) persist[1];

		Kogger.debug(getClass(), "##### Member : exist ");

		if (!member.isRequired())
		    continue;

		epmDocList.add(modelDoc);

		Kogger.debug(getClass(), " | " + modelDoc.getNumber() + " | " + modelDoc.getVersionIdentifier().getValue() + "." + modelDoc.getIterationIdentifier().getValue() + " | "
		        + modelDoc.isLatestIteration() + " | " + modelDoc.isLatestIteration() + " | Fi");

		getModelStructure(modelDoc, epmDocList);
	    }
	}
    }

    // //////////////////////////////////////////////////////////
    // CadFileDownUtil
    // //////////////////////////////////////////////////////////

    public void execute3DFileInfo(EPMDocument epm3d, String filePath) throws Exception {
	boolean is3D = true;
	// getFileInfoWith3D2D(epm3d, is3D, !is3D, filePath);
	getFileInfoWith3D2DByRole(epm3d, is3D, !is3D, filePath);
    }

    public void execute2DFileInfo(EPMDocument epm2d, String filePath) throws Exception {
	boolean is2D = true;
	// getFileInfoWith3D2D(epm2d, !is2D, is2D, filePath);
	getFileInfoWith3D2DByRole(epm2d, !is2D, is2D, filePath);
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
			    if (fileName.endsWith(".drw") || fileName.endsWith(".prt")) {
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
			    if (fileName.endsWith(".drw") || fileName.endsWith(".prt")) {
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
