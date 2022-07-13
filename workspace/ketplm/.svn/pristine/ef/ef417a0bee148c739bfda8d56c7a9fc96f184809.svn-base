package ext.ket.edm.migration.stamping;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import wt.enterprise.RevisionControlled;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETStampDistLink;
import ext.ket.edm.entity.KETStamping;
import ext.ket.edm.stamping.service.StampingHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class MigStampingUploader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigStampingUploader manager = new MigStampingUploader();

    public MigStampingUploader() {

    }

    // windchill ext.ket.edm.migration.stamping.MigStampingUploader D1412002 Z:\Stamping\download\2014\12\01\D1412002\7\D1412002.xml
    // windchill ext.ket.edm.migration.stamping.MigStampingUploader D1412002 NONE
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
	
	    Kogger.debug(MigStampingUploader.class, "@start:" + toDayTime);
	    MigStampingUploader.manager.saveFromExcel(arg1, arg2);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(MigStampingUploader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigStampingUploader.class, e);
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
		
	    Kogger.debug(getClass(), "## arg1 :" + arg1);
	    Kogger.debug(getClass(), "## arg2 :" + arg2);
	    
	    String reqOid = null;
	    KETDrawingDistRequest drawingDistReq = null; 
	    if(arg1.indexOf("KETDrawingDistRequest:") != -1){ // ext.ket.edm.entity.KETDrawingDistRequest
		try{
		    drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);
		    if(drawingDistReq == null){
			Kogger.debug(getClass(), "## 배포요청서 변환 결과가 없습니다.");
			return;
		    }
		    
		    reqOid = arg1;
		    
		}catch(Exception e){
		    Kogger.error(getClass(), e);
		    Kogger.debug(getClass(), "## 해당 OID로 된 배포요청서가 없습니다.");
		    return;
		}
	    }else{ // D1412002
		
		List<KETDrawingDistRequest> list = find(arg1.toUpperCase());
		if(list == null || list.size() == 0 ){
		    Kogger.debug(getClass(), "## 해당 ID로 된 배포요청서가 없습니다.");
		    return;
		}else if( list.size() > 1 ){
		    Kogger.debug(getClass(), "## 해당 ID로 된 배포요청서가 2개 이상입니다.");
		    return;
		}else{
		    drawingDistReq = (KETDrawingDistRequest) list.get(0);
		    if(drawingDistReq == null){
			Kogger.debug(getClass(), "## 배포요청서 검색 결과가 없습니다.");
			return;
		    }
		    
		    reqOid = CommonUtil.getOIDString(drawingDistReq);
		}
	    }
	    
	    String xmlFileFullPath = null;
	    if( arg2 == null || arg2.equals("") || "NONE".equalsIgnoreCase(arg2)){
		// find filepath
		// 요청서로 filePath 찾기
		KETStamping stamping = getStamping(drawingDistReq);
		if(stamping == null){
		    Kogger.debug(getClass(), "## Stamping 정보가 없습니다.");
		    return;
		}
		
		xmlFileFullPath = stamping.getStampFilePath();
		if(xmlFileFullPath == null || xmlFileFullPath.equals("") ){
		    Kogger.debug(getClass(), "## Stamping에 정의된 파일 정보가 없습니다.");
		    return;
		}
		
	    }else{
		xmlFileFullPath = arg2;
	    }
            
	    StampingHelper.service.saveDrawingDeployRequest(reqOid, xmlFileFullPath);
	    
	} catch (Exception e) {
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
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
