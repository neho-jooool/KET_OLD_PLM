package e3ps.load.dms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.dms.entity.KETProjectDocument;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;

public class MigDelProjectDocument implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static MigDelProjectDocument manager = new MigDelProjectDocument();

    public MigDelProjectDocument() {

    }
    
    static String delTargetDocNos = "KIS-H520429,"+
	    "KIS-H610335,"+
	    "KIS-H613801,"+
	    "KIS-H614450,"+
	    "KIS-H615171,"+
	    "KIS-H632606-40,"+
	    "KIS-H633556,"+
	    "KIS-H634319,"+
	    "KIS-H634604,"+
	    "KIS-H634647,"+
	    "KIS-H635650-4,"+
	    "KIS-H635726,"+
	    "KIS-H635797,"+
	    "KIS-H640605,"+
	    "KIS-H645877-5,"+
	    "KIS-H646024,"+
	    "KIS-H656963-3,"+
	    "KIS-H656963-3,"+
	    "KIS-H664098-41,"+
	    "KIS-H665658-4,"+
	    "KIS-H670328,"+
	    "KIS-H670340-2,"+
	    "KIS-H670863,"+
	    "KIS-H671192,"+
	    "KIS-H671202,"+
	    "KIS-H671878,"+
	    "KIS-H671915,"+
	    "KIS-H672588-4,"+
	    "KIS-H672991,"+
	    "KIS-H672994,"+
	    "KIS-H673010,"+
	    "KIS-H673013,"+
	    "KIS-H673073,"+
	    "KIS-H673386-2,"+
	    "KIS-H673752,"+
	    "KIS-H674150,"+
	    "KIS-H674480,"+
	    "KIS-H674637,"+
	    "KIS-H674649,"+
	    "KIS-H674649,"+
	    "KIS-H674661,"+
	    "KIS-H674661,"+
	    "KIS-H674662,"+
	    "KIS-H674662,"+
	    "KIS-H674689,"+
	    "KIS-H674692,"+
	    "KIS-H674772,"+
	    "KIS-H674792,"+
	    "KIS-H674865,"+
	    "KIS-H675054,"+
	    "KIS-H675063,"+
	    "KIS-H675124,"+
	    "KIS-H675271,"+
	    "KIS-H675479,"+
	    "KIS-H675547-4,"+
	    "KIS-H675591,"+
	    "KIS-H675640,"+
	    "KIS-H675644-4,"+
	    "KIS-H675680,"+
	    "KIS-H675681,"+
	    "KIS-H675689,"+
	    "KIS-H675691,"+
	    "KIS-H675720,"+
	    "KIS-H675771-2,"+
	    "KIS-H675773-2,"+
	    "KIS-H675816,"+
	    "KIS-H675819,"+
	    "KIS-H675822,"+
	    "KIS-H675899-4,"+
	    "KIS-H675903,"+
	    "KIS-H675907,"+
	    "KIS-H675910,"+
	    "KIS-H675933,"+
	    "KIS-H676112-4,"+
	    "KIS-H676167,"+
	    "KIS-H676170,"+
	    "KIS-H676213,"+
	    "KIS-H676264-3,"+
	    "KIS-H676264-40,"+
	    "KIS-H676264-3,"+
	    "KIS-H676304-5,"+
	    "KIS-H676383,"+
	    "KIS-H676387-2,"+
	    "KIS-H676425,"+
	    "KIS-H676425,"+
	    "KIS-H676447,"+
	    "KIS-H676479-1,"+
	    "KIS-H676631,"+
	    "KIS-H676687,"+
	    "KIS-H676688,"+
	    "KIS-H676704-41,"+
	    "KIS-H730502-3,"+
	    "KIS-H741360-3,"+
	    "KIS-H741361-3,"+
	    "KIS-H741363-3,"+
	    "KIS-H741364-3,"+
	    "KIS-H791224-2S,"+
	    "KIS-H791711-3,"+
	    "KIS-H792054-2,"+
	    "KIS-H792238-2,"+
	    "KIS-H792239-2,"+
	    "KIS-H792470,"+
	    "KIS-H792486-3,"+
	    "KIS-H793371-2,"+
	    "KIS-H793372-2,"+
	    "KIS-H793404-2,"+
	    "KIS-H793415-2,"+
	    "KIS-H793416-2,"+
	    "KIS-H793418-2,"+
	    "KIS-H793419-2,"+
	    "KIS-H793438-2,"+
	    "KIS-H793439-2,"+
	    "KIS-H793450-2,"+
	    "KIS-H793493-2,"+
	    "KIS-H793494-2,"+
	    "KIS-H793500-2,"+
	    "KIS-H793501-2,"+
	    "KIS-H793605-2,"+
	    "KIS-K200444A-G";

    // windchill e3ps.load.dms.MigProjectDocument TEST.xlsx
    public static void main(String[] args) {

	try {

	    String delCategoryCode = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
	    	delCategoryCode = args[0];
	    }

	    
	    Kogger.debug(MigDelProjectDocument.class, "@start");
	    MigDelProjectDocument.manager.saveFromExcel(delCategoryCode);
	    Kogger.debug(MigDelProjectDocument.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigDelProjectDocument.class, e);
	}
    }

    public void saveFromExcel(String delCategoryCode) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { delCategoryCode };

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
	    executeMigration(delCategoryCode);
	}
    }

    public void executeMigration(String delCategoryCode) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    
	    String delDocNo[] = delTargetDocNos.split(",");
	    
	    for(int i=0; i<delDocNo.length; i++){
		deleteDocs(delDocNo[i],delCategoryCode);
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
    

    public void deleteDocs(String docNo,String delCategoryCode) throws Exception {

	ProjectDocumentDTO projectDocumentDTO = new ProjectDocumentDTO();

	projectDocumentDTO.setProjectDocType(delCategoryCode);// categorycode
	
	projectDocumentDTO.setDocumentNo(docNo);
	NumberCode number = NumberCodeHelper.manager.getNumberCode("VERSION", "LATEST");
	String NumberCodeOid = CommonUtil.getOIDLongValue2Str(number);
	projectDocumentDTO.setVersion(NumberCodeOid);//최신 문서만 가져오도록 한다

	List<KETProjectDocument> list = getDocumentResult(projectDocumentDTO);
	
	//String currentYear = DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"YYYY");
	

	for (KETProjectDocument doc : list) {
	    System.out.println("docNo : "+docNo + " 삭제 시작....");
	    doc = (KETProjectDocument) KETContentHelper.service.delete(doc);
	    PersistenceHelper.manager.delete(doc);
	    System.out.println("docNo : "+docNo + " 삭제 완료....");
	}
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



}
