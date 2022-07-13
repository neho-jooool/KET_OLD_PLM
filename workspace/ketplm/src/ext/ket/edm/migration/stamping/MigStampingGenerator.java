package ext.ket.edm.migration.stamping;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.WCUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.edm.entity.KETDrawingDistEpmLink;
import ext.ket.edm.entity.KETDrawingDistFileType;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.stamping.jms.StampingQueueSender;
import ext.ket.edm.util.DrawingDistFileTypeEnum;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class MigStampingGenerator implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigStampingGenerator manager = new MigStampingGenerator();

    public MigStampingGenerator() {

    }

    // windchill ext.ket.edm.migration.stamping.MigStampingGenerator ALL ALL
    // windchill ext.ket.edm.migration.stamping.MigStampingGenerator ACAD DWG
    // windchill ext.ket.edm.migration.stamping.MigStampingGenerator CREO PRT/ASM/DRW
    // windchill ext.ket.edm.migration.stamping.MigStampingGenerator NX ASM/PRT
    // windchill ext.ket.edm.migration.stamping.MigStampingGenerator KET STD
    public static void main(String[] args) {

	try {

	    String arg1 = null;
	    String arg2 = null;

	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		arg1 = args[0];
		arg2 = args[1];
	    }
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(MigStampingGenerator.class, "@start:" + toDayTime);
	    MigStampingGenerator.manager.saveFromExcel(arg1, arg2);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(MigStampingGenerator.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigStampingGenerator.class, e);
	}
    }

    public void saveFromExcel(String arg1, String arg2) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class };
		Object aobj[] = { arg1, arg2 };

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
	    executeMigration(arg1, arg2);
	}
    }
    
    public void executeMigration(String arg1, String arg2) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
	    
	    if("ALL".equals(arg1)){
		execute("KET", "STD"); 
		execute("ACAD", "DWG");
		execute("CREO", "DRW");
		execute("CREO", "PRT");
		execute("CREO", "ASM");
		execute("NX", "PRT");
		execute("NX", "ASM");
	    }else{
		execute(arg1, arg2);
	    }
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
    
    public void execute(String arg1, String arg2) throws Exception {
	
	    String numberPrefix = "T";
	    
	    if("CREO".equals(arg1)){
		
		// 1-1. creo drw transaction test - 120
		if ("DRW".equals(arg2)) {
		    
		    List<String> drawingList = searchCreoDrwAllList();
		    String flag = "DRW";
		    int idx = 100;
		    for (String epmOid : drawingList) {
			
//			SimpleDateFormat ff = new SimpleDateFormat("mmss");
//			String timeStr = ff.format(new Date());
			String number = numberPrefix + "CD" + idx;
			String distType = getDistType(idx);
			
			KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(number, flag, distType, epmOid);
			
			String reqId = drawingDistReq.getNumber();
			Kogger.debug(getClass(), "##################################################");
			Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
			Kogger.debug(getClass(), "##################################################");
			
			StampingQueueSender sender = StampingQueueSender.getInstance();
			sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
			
			Kogger.debug(getClass(), "### Queue : Sended");
			
			idx++;
		    }
		}
		
		// 1-1. creo asm test - 511
		if ("PRT".equals(arg2)) {
		    
		    List<String> drawingList = searchCreoPrtAllList();
		    String flag = "PRT";
		    int idx = 100;
		    for (String epmOid : drawingList) {
			
//			SimpleDateFormat ff = new SimpleDateFormat("mmss");
//			String timeStr = ff.format(new Date());
			String number = numberPrefix + "CP" + idx;
			String distType = getDistType(idx);
			
			KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(number, flag, distType, epmOid);
			
			String reqId = drawingDistReq.getNumber();
			Kogger.debug(getClass(), "##################################################");
			Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
			Kogger.debug(getClass(), "##################################################");
			
			StampingQueueSender sender = StampingQueueSender.getInstance();
			sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
			
			Kogger.debug(getClass(), "### Queue : Sended");
			
			idx++;
		    }
		}
		
		// 1-1. creo prt test - 458
		if ("ASM".equals(arg2)) {
		    
		    List<String> drawingList = searchCreoAsmAllList();
		    String flag = "ASM";
		    int idx = 100;
		    for (String epmOid : drawingList) {
			
//			SimpleDateFormat ff = new SimpleDateFormat("mmss");
//			String timeStr = ff.format(new Date());
			String number = numberPrefix + "CA" + idx;
			String distType = getDistType(idx);
			
			KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(number, flag, distType, epmOid);
			
			String reqId = drawingDistReq.getNumber();
			Kogger.debug(getClass(), "##################################################");
			Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
			Kogger.debug(getClass(), "##################################################");
			
			StampingQueueSender sender = StampingQueueSender.getInstance();
			sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
			
			Kogger.debug(getClass(), "### Queue : Sended");
			
			idx++;
		    }
		}
		
	    }else if("NX".equals(arg1)){
		
		// nx transaction test - randomn 하게 sampling
		if ("PRT".equals(arg2)) {
		    
		    List<String> drawingList = searchNxPrtAllList();
		    String flag = "PRT";
		    int idx = 100;
		    for (String epmOid : drawingList) {
			
//			SimpleDateFormat ff = new SimpleDateFormat("mmss");
//			String timeStr = ff.format(new Date());
			String number = numberPrefix + "NP" + idx;
			String distType = getDistType(idx);
			
			KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(number, flag, distType, epmOid);
			
			String reqId = drawingDistReq.getNumber();
			Kogger.debug(getClass(), "##################################################");
			Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
			Kogger.debug(getClass(), "##################################################");
			
			StampingQueueSender sender = StampingQueueSender.getInstance();
			sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
			
			Kogger.debug(getClass(), "### Queue : Sended");
			
			idx++;
		    }
		}
		
		// 1-1. dwg transaction randomn test
		if ("ASM".equals(arg2)) {
		    
		    List<String> drawingList = searchNxAsmAllList();
		    String flag = "ASM";
		    int idx = 100;
		    for (String epmOid : drawingList) {
			
//			SimpleDateFormat ff = new SimpleDateFormat("mmss");
//			String timeStr = ff.format(new Date());
			String number = numberPrefix + "NA" + idx;
			String distType = getDistType(idx);
			
			KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(number, flag, distType, epmOid);
			
			String reqId = drawingDistReq.getNumber();
			Kogger.debug(getClass(), "##################################################");
			Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
			Kogger.debug(getClass(), "##################################################");
			
			StampingQueueSender sender = StampingQueueSender.getInstance();
			sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
			
			Kogger.debug(getClass(), "### Queue : Sended");
			
			idx++;
		    }
		}
		
	    } else if ("ACAD".equals(arg1)) {
		
		// nx transaction test - randomn 하게 sampling
		if ("DWG".equals(arg2)) {
		    
		    List<String> drawingList = searchAcadDwgAllList();
		    String flag = "DWG";
		    int idx = 100;
		    for (String epmOid : drawingList) {
			
			// SimpleDateFormat ff = new SimpleDateFormat("mmss");
			// String timeStr = ff.format(new Date());
			String number = numberPrefix + "AG" + idx;
			String distType = getDistType(idx);
			
			KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(number, flag, distType, epmOid);
			
			String reqId = drawingDistReq.getNumber();
			Kogger.debug(getClass(), "##################################################");
			Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
			Kogger.debug(getClass(), "##################################################");
			
			StampingQueueSender sender = StampingQueueSender.getInstance();
			sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
			
			Kogger.debug(getClass(), "### Queue : Sended");
			
			idx++;
		    }
		}
	    } else if ("KET".equals(arg1)) {
		
		// nx transaction test - randomn 하게 sampling
		if ("STD".equals(arg2)) {
		    
		    List<String> drawingList = searchKetStdAllList();
		    String flag = "STD";
		    int idx = 100;
		    for (String epmOid : drawingList) {
			
			// SimpleDateFormat ff = new SimpleDateFormat("mmss");
			// String timeStr = ff.format(new Date());
			String number = numberPrefix + "KS" + idx;
			String distType = getDistType(idx);
			
			KETDrawingDistRequest drawingDistReq = createDrawingDistRequest(number, flag, distType, epmOid);
			
			String reqId = drawingDistReq.getNumber();
			Kogger.debug(getClass(), "##################################################");
			Kogger.debug(getClass(), "### 배포요청서 Queue 입력 " + reqId);
			Kogger.debug(getClass(), "##################################################");
			
			StampingQueueSender sender = StampingQueueSender.getInstance();
			sender.sendMessage(drawingDistReq.getNumber(), CommonUtil.getOIDString(drawingDistReq));
			
			Kogger.debug(getClass(), "### Queue : Sended");
			
			idx++;
		    }
		}
	    }
    }
    
    private String getDistType(int idx){
	
	String distType= null;
	if(idx%6 == 0){
	    distType= "APPROVED";
	}else if(idx%6 == 1){
	    distType= "APPROVED1";
	}else if(idx%6 == 2){
	    distType= "APPROVED2";
	}else if(idx%6 == 3){
	    distType= "REVIEW";
	}else if(idx%6 == 4){
	    distType= "FOURM1";
	}else if(idx%6 == 5){
	    distType= "FOURM2";
	}
	
	return distType;
    }
    
    private KETDrawingDistRequest createDrawingDistRequest(String number, String flag, String distType, String epmOid) throws Exception{
	
	SessionContext sessioncontext = SessionContext.newContext();
	Transaction trx = null;
	KETDrawingDistRequest drawingDistReq = null;
	 
	try {
	    
	    trx = new Transaction();
	    trx.start();
	    
	    drawingDistReq = KETDrawingDistRequest.newKETDrawingDistRequest();

	    drawingDistReq.setNumber(number);
	    drawingDistReq.setName("TEST:" + flag + ":" + number);
	    drawingDistReq.setTitle("TEST:" + flag + ":" + number);
	    // drawingDistReq.setDescription(paramObject.getDescription());

	    drawingDistReq.setDistType(distType);
	    drawingDistReq.setDistReason("TEST");
	    // drawingDistReq.setDownloadExpireDate(null);
	    // drawingDistReq.setDistSubcontractor(paramObject.getDistSubcontractor());
	    // drawingDistReq.setDistDeptName(paramObject.getDistDeptName());

	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	    String writeDeptKrName = (userDept == null) ? "" : userDept.getName();
	    String writeDeptCode = (userDept == null) ? "" : userDept.getCode();
	    drawingDistReq.setWriteDeptKrName(writeDeptKrName);
	    drawingDistReq.setWriteDeptEnName(userDept.getEnName());
	    drawingDistReq.setWriteDeptCode(writeDeptCode);

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String lcName = "KET_COMMON_LC";
	    LifeCycleHelper.setLifeCycle((LifeCycleManaged) drawingDistReq, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    String folderPath = "/Default/자동차사업부/초기유동/";

	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) drawingDistReq, folder);
	    drawingDistReq = (KETDrawingDistRequest) TeamHelper.setTeamTemplate(drawingDistReq, TeamHelper.service.getTeamTemplate("Default"));
	    drawingDistReq = (KETDrawingDistRequest) PersistenceHelper.manager.save(drawingDistReq);

	    // 배포 포멧 : PDF,DWG
	    String[] drawingDistFileTypeArrayItems = { "PDF", "DWG", "STEP" };
	    for (String fileTypeItem : drawingDistFileTypeArrayItems) {

		KETDrawingDistFileType fileTypes = KETDrawingDistFileType.newKETDrawingDistFileType();
		DrawingDistFileTypeEnum drawingDistFileType = DrawingDistFileTypeEnum.valueOf(fileTypeItem);
		fileTypes.setDistFileType(drawingDistFileType.toString());
		fileTypes.setDistReq(drawingDistReq);
		PersistenceHelper.manager.save(fileTypes);
	    }

	    // 배포 도면
	    EPMDocument distEpm = (EPMDocument) CommonUtil.getObject(epmOid);
	    KETDrawingDistEpmLink epmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
	    epmLink.setSheetType("All");
	    PersistenceHelper.manager.save(epmLink);
	    
	    trx.commit();
	    trx = null;
	
	} catch (Exception e) {
	    
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;
	    
	} finally {
	    
	    if(trx != null)
   		trx.rollback();
	    
	    SessionContext.setContext(sessioncontext);
	}
	
	return drawingDistReq;
    }
    

    // ket 표준
    private List<String> searchKetStdAllList() throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT OID FROM ( \n");
	    sb.append(" SELECT ROWNUM RN, E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n");
	    sb.append(" FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	    sb.append(" WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2) \n");
	    sb.append(" AND E.STATECHECKOUTINFO = 'c/i' \n");
	    sb.append(" AND M.DOCUMENTNUMBER LIKE 'TEST_KET_%_2D' \n");
	    sb.append(" ) \n");

	    String query = sb.toString();

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
    
    // acd 변환 전체 dwg list
    private List<String> searchAcadDwgAllList() throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT OID FROM ( \n");
	    sb.append(" SELECT ROWNUM RN, E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n");
	    sb.append(" FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	    sb.append(" WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2) \n");
	    sb.append(" AND E.STATECHECKOUTINFO = 'c/i' \n");
	    sb.append(" AND M.CADNAME LIKE '%.dwg' \n");
	    sb.append(" AND M.AUTHORINGAPPLICATION = 'ACAD'  \n");
	    sb.append(" ) WHERE MOD(RN, 122) = 0  \n");

	    String query = sb.toString();

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
    
    // nx 변환 전체 prt list
    private List<String> searchNxPrtAllList() throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();
	
	try {
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT OID FROM  \n");
	    sb.append(" (  \n");
	    sb.append(" SELECT ROWNUM RN, E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n"); 
	    sb.append(" FROM MODELREFERENCELINK L, EPMDocumentMaster A, EPMDocument E \n"); 
	    sb.append(" WHERE 1=1  \n");
	    sb.append(" AND L.IDA3A5 = A.IDA2A2 \n"); 
	    sb.append(" AND A.IDA2A2 = E.IDA3MASTERREFERENCE \n"); 
	    sb.append(" AND E.LATESTITERATIONINFO = 1  \n");
	    sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = A.IDA2A2) \n"); 
	    sb.append(" AND E.STATECHECKOUTINFO = 'c/i'  \n");
	    sb.append(" AND A.CADNAME LIKE '%.prt'   \n");
	    sb.append(" AND A.AUTHORINGAPPLICATION = 'UG'  \n");
	    sb.append(" AND A.DOCTYPE = 'CADCOMPONENT'   \n");
	    sb.append(" )   \n");
	    sb.append(" WHERE MOD(RN, 17) = 0 \n"); 
	    
	    String query = sb.toString();
	    
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
    
    // nx 변환 전체 asm list
    private List<String> searchNxAsmAllList() throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();
	
	try {
	    
	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT OID FROM  \n");
	    sb.append(" (   \n");
	    sb.append(" SELECT ROWNUM RN, E.CLASSNAMEA2A2||':'||E.IDA2A2 OID  \n");
	    sb.append(" FROM MODELREFERENCELINK L, EPMDocumentMaster A, EPMDocument E  \n");
	    sb.append(" WHERE 1=1  \n");
	    sb.append(" AND L.IDA3A5 = A.IDA2A2  \n");
	    sb.append(" AND A.IDA2A2 = E.IDA3MASTERREFERENCE  \n");
	    sb.append(" AND E.LATESTITERATIONINFO = 1  \n");
	    sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = A.IDA2A2)  \n");
	    sb.append(" AND E.STATECHECKOUTINFO = 'c/i'  \n");
	    sb.append(" AND A.CADNAME LIKE '%.prt' \n");
	    sb.append(" AND A.AUTHORINGAPPLICATION = 'UG'  \n");
	    sb.append(" AND A.DOCTYPE = 'CADASSEMBLY'   \n");
	    sb.append(" )  \n");
	    
	    String query = sb.toString();
	    
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
    
    // creo 변환 전체 drw list
    private List<String> searchCreoDrwAllList() throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n");
	    sb.append(" FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	    sb.append(" WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2) \n");
	    sb.append(" AND E.STATECHECKOUTINFO = 'c/i' \n");
	    sb.append(" AND M.CADNAME LIKE '%.drw' \n");
	    sb.append(" AND M.DOCTYPE IN ('CADDRAWING') \n");

	    String query = sb.toString();

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
    
    // creo 변환 전체 asm list
    private List<String> searchCreoAsmAllList() throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();
	
	try {
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT OID FROM ( \n");
	    sb.append(" SELECT ROWNUM RN, E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n");
	    sb.append(" FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	    sb.append(" WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2) \n");
	    sb.append(" AND E.STATECHECKOUTINFO = 'c/i' \n");
	    sb.append(" AND M.CADNAME LIKE '%.asm' \n");
	    sb.append(" AND M.AUTHORINGAPPLICATION = 'PROE'  \n");
	    sb.append(" ) WHERE MOD(RN, 3) = 0  \n");
	    
	    String query = sb.toString();
	    
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
    // creo 변환 전체 prt list
    private List<String> searchCreoPrtAllList() throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();
	
	try {
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT OID FROM ( \n");
	    sb.append(" SELECT ROWNUM RN, E.CLASSNAMEA2A2||':'||E.IDA2A2 OID \n");
	    sb.append(" FROM EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	    sb.append(" WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	    sb.append(" AND E.VERSIONIDA2VERSIONINFO IN ( SELECT MAX(VERSIONIDA2VERSIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = M.IDA2A2) \n");
	    sb.append(" AND E.STATECHECKOUTINFO = 'c/i' \n");
	    sb.append(" AND M.CADNAME LIKE '%.prt' \n");
	    sb.append(" AND M.AUTHORINGAPPLICATION = 'PROE'  \n");
	    sb.append(" ) WHERE MOD(RN, 10) = 0  \n");
		    
	    String query = sb.toString();
	    
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

}
