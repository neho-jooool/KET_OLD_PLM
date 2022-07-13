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
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import ext.ket.edm.entity.KETDrawingDistDocLink;
import ext.ket.edm.entity.KETDrawingDistEpmLink;
import ext.ket.edm.entity.KETDrawingDistFileType;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETStampDistLink;
import ext.ket.edm.entity.KETStampDocItem;
import ext.ket.edm.entity.KETStampDocLink;
import ext.ket.edm.entity.KETStampEpmLink;
import ext.ket.edm.entity.KETStamping;
import ext.ket.edm.entity.KETStampingItem;
import ext.ket.edm.entity.KETStampingItemLink;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class MigStampingDeleter implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigStampingDeleter manager = new MigStampingDeleter();

    public MigStampingDeleter() {

    }

    // windchill ext.ket.edm.migration.stamping.MigStampingDeleter ALL
    // windchill ext.ket.edm.migration.stamping.MigStampingDeleter T1412002 T1412003
    public static void main(String[] args) {

	try {

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");

	    String toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error(MigStampingDeleter.class, "Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigStampingDeleter.class, "@start:" + toDayTime);
	    MigStampingDeleter.manager.saveFromExcel(args);

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error(MigStampingDeleter.class, "Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigStampingDeleter.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigStampingDeleter.class, e);
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

	    if (args.length == 1 && "ALL".equals(args[0])) {

		List<KETDrawingDistRequest> list = findAllTest("T");
		for (KETDrawingDistRequest reqObj : list) {
		    deleteTest(reqObj.getNumber());
		}

	    } else if (args.length == 2 && "ALL".equals(args[0])) {

		List<KETDrawingDistRequest> list = findAllTest(args[1]);
		for (KETDrawingDistRequest reqObj : list) {
		    deleteTest(reqObj.getNumber());
		}

	    } else {

		for (String reqId : args) {
		    deleteTest(reqId);
		}
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {

	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;

	} finally {

	    if (trx != null)
		trx.rollback();

	    SessionContext.setContext(sessioncontext);
	}
    }

    private void deleteTest(String reqId) throws Exception {
	
	Kogger.debug(getClass(), "# Delete Dist Id :" + reqId);
	
	KETDrawingDistRequest drawingDistReq = null;
	List<KETDrawingDistRequest> list = find(reqId.toUpperCase());
	if (list == null || list.size() == 0) {
	    Kogger.debug(getClass(), "## 해당 ID로 된 배포요청서가 없습니다.");
	    return;
	} else if (list.size() > 1) {
	    Kogger.debug(getClass(), "## 해당 ID로 된 배포요청서가 2개 이상입니다.");
	    return;
	} else {
	    drawingDistReq = (KETDrawingDistRequest) list.get(0);
	    if (drawingDistReq == null) {
		Kogger.debug(getClass(), "## 배포요청서 검색 결과가 없습니다.");
		return;
	    }

	    KETStamping stamping = getStamping(drawingDistReq);
	    deleteDrawingDistRequest(drawingDistReq, stamping);
	}
    }

    // get stamping Object
    private void deleteDrawingDistRequest(KETDrawingDistRequest drawingDistReq, KETStamping stamping) throws Exception {

	if (drawingDistReq == null)
	    throw new Exception("# KETDrawingDistRequest is null !! : StampingUploader  deleteStampingItem() ");

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

	// StampingItemLink delete
	List<KETStampingItemLink> stampingLinkList = query.queryForListLinkByRoleB(KETStampingItemLink.class, KETStamping.class, stamping);
	for (KETStampingItemLink link : stampingLinkList) {
	    PersistenceHelper.manager.delete(link);
	}

	// StampingItem delete
	List<KETDrawingDistEpmLink> disEpmLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class, KETDrawingDistRequest.class, drawingDistReq);
	for (KETDrawingDistEpmLink distEpmlink : disEpmLinkList) {
	    List<KETStampEpmLink> stampingItemLinkList = query.queryForListLinkByRoleB(KETStampEpmLink.class, KETDrawingDistEpmLink.class, distEpmlink);
	    for (KETStampEpmLink itemLink : stampingItemLinkList) {
		KETStampingItem item = itemLink.getStampItem();
		PersistenceHelper.manager.delete(item);
		PersistenceHelper.manager.delete(itemLink);
	    }
	}

	// 문서 StampingItem delete
	List<KETDrawingDistDocLink> relatedDocList = query.queryForListLinkByRoleB(KETDrawingDistDocLink.class, KETDrawingDistRequest.class, drawingDistReq);
	for (KETDrawingDistDocLink relatedDoclink : relatedDocList) {
	    List<KETStampDocLink> stampingItemLinkList = query.queryForListLinkByRoleB(KETStampDocLink.class, KETDrawingDistDocLink.class, relatedDoclink);
	    for (KETStampDocLink itemLink : stampingItemLinkList) {
		KETStampDocItem item = itemLink.getStampDocItem();
		PersistenceHelper.manager.delete(item);
		PersistenceHelper.manager.delete(itemLink);
	    }
	}

	// 배포포멧 기존 삭제
	// 배포 도면 기존 삭제
	deleteDistDeploy(drawingDistReq);

	// Stamping 삭제
	PersistenceHelper.manager.delete(stamping);

	// 요청서 삭제
	PersistenceHelper.manager.delete(drawingDistReq);
    }

    private void deleteDistDeploy(KETDrawingDistRequest drawingDistReq) throws Exception {

	// 배포포멧 기존 삭제
	QuerySpec query = new QuerySpec();
	int idx = query.appendClassList(KETDrawingDistFileType.class, true);
	query.appendWhere(new SearchCondition(KETDrawingDistFileType.class, "distReqReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(drawingDistReq)), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETDrawingDistFileType ketDrawingDistFileType = (KETDrawingDistFileType) obj[0];
	    PersistenceHelper.manager.delete(ketDrawingDistFileType);
	}
	// 배포포멧 기존삭제 끝

	// 배포 도면 기존 삭제
	query = new QuerySpec();
	idx = query.appendClassList(KETDrawingDistEpmLink.class, true);
	query.appendWhere(new SearchCondition(KETDrawingDistEpmLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(drawingDistReq)), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    KETDrawingDistEpmLink ketDrawingDistEpmLink = (KETDrawingDistEpmLink) obj[0];
	    PersistenceHelper.manager.delete(ketDrawingDistEpmLink);
	}
	// 배포 도면 기존삭제 끝
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

    // 요청서 찾기
    private List<KETDrawingDistRequest> findAllTest(String prefix) throws Exception {

	QuerySpec query = getQueryForTest(prefix);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<KETDrawingDistRequest> list = new ArrayList<KETDrawingDistRequest>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((KETDrawingDistRequest) objArr[0]);
	}
	return list;
    }

    // 요청서 찾기 쿼리
    private QuerySpec getQueryForTest(String prefix) throws Exception {

	Kogger.debug(getClass(), "## test ID : T");

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;

	int idx = query.appendClassList(KETDrawingDistRequest.class, true);
	query.appendWhere(new SearchCondition(KETDrawingDistRequest.class, RevisionControlled.LATEST_ITERATION, "TRUE"), idx);
	if (query.getConditionCount() > 0) {
	    query.appendAnd();
	}
	sc = new SearchCondition(KETDrawingDistRequest.class, KETDrawingDistRequest.NUMBER, SearchCondition.LIKE, prefix + "%", false);
	query.appendWhere(sc, new int[] { idx });

	return query;
    }

}
