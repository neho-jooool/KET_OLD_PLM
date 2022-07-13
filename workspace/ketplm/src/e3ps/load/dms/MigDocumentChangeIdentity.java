package e3ps.load.dms;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.doc.WTDocumentMasterIdentity;
import wt.fc.Identified;
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.load.migration.edm.log.LogToFile;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MigDocumentChangeIdentity implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    
    private static String logFile = "D://ptc//Windchill_10.2//Windchill//loadFiles//ket//migration//dms//projectDocumentLoader.log";
    
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigDocumentChangeIdentity manager = new MigDocumentChangeIdentity();

    public MigDocumentChangeIdentity() {

    }

    // windchill ext.ket.part.migration.base.MigEpmChangeIdentity D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\migration\edm\20170810.xlsx

    public static void main(String[] args) {

	try {
	    	    
	    String filePath = "";
	    String flag = "";
	    
	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		filePath = args[0];
		flag = args[1];
	    }
	    
	    Kogger.debug(MigDocumentChangeIdentity.class, "@start");
	    MigDocumentChangeIdentity.manager.saveFromExcel(filePath, flag);
	    Kogger.debug(MigDocumentChangeIdentity.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigDocumentChangeIdentity.class, e);
	}
    }

    public void saveFromExcel(String filePath, String flag) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {String.class, String.class};
		Object aobj[] = {filePath, flag};

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
	    executeMigration(filePath, flag);
	}
    }

    public void executeMigration(String filePath, String flag) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	
	Map<String, Object> partCheckMap = new HashMap<String, Object>();
	
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    
	    
	    File dataFile = new File(filePath);
	    
	    if(dataFile == null){
		throw new Exception("@@ 해당 경로에 파일이 존재하지 않습니다 ! [ "+filePath+" ]");
	    }
	    
	    if (DRMHelper.useDrm) {
		dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	    }
	    
	    ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(dataFile);
	    
	    int sheetNo = 0;
	    excel.setSheet(sheetNo);
	    int startRowNo = 1;
	    
	    int rowSize = excel.getSheetLastRowNum();
	    
	    for (int i = startRowNo; i <= rowSize; i++) {
		
		excel.setRow(i);
		String docNo = excel.getStrValue(0);
		String targetName = excel.getStrValue(1);
		
		KETProjectDocument doc = this.getLastestDoc(docNo);
		
		if(doc == null){
		    log(docNo+" 는 존재하지 않는 문서번호입니다.");
		    continue;
		}
		
		if("0".equals(flag)){//연계부품 삭제 후 재연결
		    checkValidation(partCheckMap, targetName, doc);
		    if(StringUtils.isNotEmpty((String)partCheckMap.get(doc.getNumber()))){
			continue;
		    }
		    this.relatedPartDelete(doc);
		    this.createRelatedPart(targetName, doc);
		}else if("1".equals(flag)){//문서명 변경
		    this.changeDocNo(doc, targetName);
		}
		
	    }
	    
	    trx.commit();
	    
	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
    
    
    public void changeDocNo(KETProjectDocument d, String newDocNo) throws Exception{
	
	String oldDocNo = d.getNumber();
	
	KETProjectDocument newDoc = getLastestDoc(newDocNo);
	
	if (newDoc != null) {
	    
	    log(oldDocNo + " 의 변경대상 번호가 이미 있습니다. [ "+newDocNo + " ]");
	    
	} else {
	    Identified localIdentified = (Identified) d.getMaster();
	    WTDocumentMasterIdentity identity = (WTDocumentMasterIdentity) localIdentified.getIdentificationObject();
	    identity.setNumber(newDocNo);
	    IdentityHelper.service.changeIdentity(localIdentified, identity);

	    d = (KETProjectDocument) PersistenceHelper.manager.refresh(d);

	    d.setDocumentNo(newDocNo);

	    PersistenceServerHelper.manager.update(d);
	}
	
    }
    
    public KETProjectDocument getLastestDoc(String docNo) throws Exception{
	
	ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO();
	
	NumberCode number = NumberCodeHelper.manager.getNumberCode("VERSION", "LATEST");
	String NumberCodeOid = CommonUtil.getOIDLongValue2Str(number);
	projectDocumentDTO.setVersion(NumberCodeOid);//최신 문서만 가져오도록 한다
	projectDocumentDTO.setDocumentNo(docNo);

	List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);
	
	KETProjectDocument d = null;
	
	for (KETProjectDocument doc : list) {
		d = doc;
	}
	
	return d;
    }
    
    public List<KETProjectDocument> getDocumentResult(ProjectDocumentDTO projectDocumentDTO) throws Exception {

	// KETProjectDocument spec
	List<KETProjectDocument> list = null;
	StatementSpec query = KETProjectDocumentHelper.service.getListProjectDocumentQuerySpec(projectDocumentDTO);

	if (!query.isAdvancedQueryEnabled())
	    query.setAdvancedQueryEnabled(true);

	QueryResult result = PersistenceServerHelper.manager.query(query);
	if (result != null) {
	    list = new ArrayList<KETProjectDocument>();
	    while (result.hasMoreElements()) {
		Object[] objArr = (Object[]) result.nextElement();
		list.add((KETProjectDocument) objArr[0]);
	    }
	}

	return list;
    }
    
    public void relatedPartDelete(KETProjectDocument doc) throws Exception {
	QueryResult r = PersistenceHelper.manager.navigate(doc, "part", KETDocumentPartLink.class, false);

	while (r.hasMoreElements()) {
	    // KETContentHelper.service.delete(doc);
	    KETDocumentPartLink link = (KETDocumentPartLink) r.nextElement();
	    PersistenceHelper.manager.delete(link);
	}
    }
    
    public void createRelatedPart(String partNo, KETProjectDocument d) throws Exception {
	
	KETDocumentPartLink DpLink = null;

	String partNos[] = partNo.split(",");

	for (String part : partNos) {
	    if(StringUtils.isEmpty(part)){
		continue;
	    }
	    WTPart wtpart = PartBaseHelper.service.getLatestPart(part);
	    
	    DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
	    DpLink = (KETDocumentPartLink) PersistenceHelper.manager.save(DpLink);
	}
    }
    
    public void checkValidation(Map<String, Object> map, String partNo, KETProjectDocument d) throws Exception{
	
	String partNos[] = partNo.split(",");
    
	for (String part : partNos) {
	    if(StringUtils.isEmpty(part)){
		continue;
	    }
	    WTPart wtpart = PartBaseHelper.service.getLatestPart(part);
	    
	    if(wtpart == null){
		log(d.getNumber() +" 의 연계대상 부품번호 ["+part + "] 는 존재하지 않는 wtpart입니다");
		map.put(d.getNumber(), d.getNumber());
	    }
	}
    }
    
    public static void log(String msg) {
	try {
	    LogToFile logger = new LogToFile(logFile, true);
	    logger.log(msg);
	} catch (IOException e) {
	    Kogger.error(ProjectDocumentDataLoader.class, e);
	}
    }
}
