package ext.ket.part.migration.claz;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import wt.enterprise.RevisionControlled;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETStampDistLink;
import ext.ket.edm.entity.KETStamping;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class MigNumberingMinNo implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigNumberingMinNo manager = new MigNumberingMinNo();

    public MigNumberingMinNo() {

    }

    // windchill ext.ket.part.migration.claz.MigNumberingMinNo Z:\90.개인폴더\이응진\부품사양관리\업로드포멧\업로드작업\제품분류_V1.1_MigrationData.xlsx
    // windchill ext.ket.part.migration.claz.MigNumberingMinNo D:\MigrationData.xlsx
    public static void main(String[] args) {

	try {

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");

	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(MigNumberingMinNo.class, "@start:" + toDayTime);
	    MigNumberingMinNo.manager.saveFromExcel(args);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(MigNumberingMinNo.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigNumberingMinNo.class, e);
	}
    }

    public void saveFromExcel(String[] args) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String[].class };
		Object aobj[] = { args };

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
	    executeMigration(args);
	}
    }
    
    public void executeMigration(String[] args) throws Exception {
	
	SessionContext sessioncontext = SessionContext.newContext();
	Transaction trx = null;
	 
	try {

	    SessionHelper.manager.setAdministrator();
		
	    trx = new Transaction();
	    trx.start();
	    
	    Kogger.debug(getClass(), "## args :" + args);
	    
	    String filePath = args[0];
	    
	    MigNumberingMinNoLoader loader = new MigNumberingMinNoLoader();
	    loader.load(filePath);
	    
	    List<Map> list = loader.getList();
	    
	    for( Map map : list ){
		
		String classCode = (String)map.get(MigNumberingMinNoLoader.CLASSCODE);
		Kogger.debug(getClass(), "### CLASSCODE:" + classCode);
		classCode = classCode.trim();
		
		String minNo = (String)map.get(MigNumberingMinNoLoader.MINNO);
		minNo = minNo.trim();
		
		KETPartClassification claz = PartClazHelper.service.getPartClassificationByClazCode(classCode);
		
		claz.setNumberingMinNo(minNo);
		
		PersistenceServerHelper.manager.update(claz);
	    }
	    
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
    }    
	
    // Stamping 정보 찾기
    private KETStamping getStamping(KETDrawingDistRequest drawingDistReq) throws Exception {

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	KETStamping stamping = query.queryForFirstAByRoleB(KETStamping.class, KETStampDistLink.class, KETDrawingDistRequest.class, drawingDistReq);

	Kogger.debug(getClass(), "stamping:" + stamping);

	return stamping;
    }
  
    // 요청서 찾기
    private List<KETDrawingDistRequest> find(String distId) throws Exception {

	QuerySpec query = getQuery(distId);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<KETDrawingDistRequest> list = new ArrayList<KETDrawingDistRequest>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((KETDrawingDistRequest) objArr[0]);
	}
	return list;
    }
    
    // 요청서 찾기 쿼리
    private QuerySpec getQuery(String distId) throws Exception {

	Kogger.debug(getClass(), "## KETDrawingDistRequest ID :" + distId);

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;

	int idx = query.appendClassList(KETDrawingDistRequest.class, true);
	query.appendWhere(new SearchCondition(KETDrawingDistRequest.class, RevisionControlled.LATEST_ITERATION, "TRUE"), idx);
	if (query.getConditionCount() > 0) {
	    query.appendAnd();
	}
	sc = new SearchCondition(KETDrawingDistRequest.class, KETDrawingDistRequest.NUMBER, SearchCondition.EQUAL, distId);
	query.appendWhere(sc, new int[] { idx });

	return query;
    }


}
