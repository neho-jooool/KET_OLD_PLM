package ext.ket.edm.approval;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import e3ps.common.util.CommonUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import ext.ket.edm.approval.internal.EpmApprovalDao;
import ext.ket.edm.approval.internal.EpmApprovalHandler;
import ext.ket.edm.approval.internal.EpmApprovalHistoryHandler;
import ext.ket.edm.entity.KETEpmApprovalHis;
import ext.ket.edm.entity.KETEpmApprovalHisLink;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardKetEpmApprovalService extends StandardManager implements KetEpmApprovalService {

    private static final long serialVersionUID = 1L;
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private EpmApprovalDao epmApprovalDao = new EpmApprovalDao();
    private EpmApprovalHandler epmApprovalHandler = new EpmApprovalHandler();

    public static StandardKetEpmApprovalService newStandardKetEpmApprovalService() throws WTException {
	StandardKetEpmApprovalService instance = new StandardKetEpmApprovalService();
	instance.initialize();
	return instance;
    }

    // 관련 ECO Oid를 가져온다.
    @Override
    public String getEcoPboOid(EPMDocument epmDoc) throws Exception {
	return epmApprovalDao.getEcoPboOid(epmDoc);
    }

    // ECO PBO 객체를 가져온다.
    @Override
    public WTChangeOrder2 getEcoPbo(EPMDocument epmDoc) throws Exception {
	String ecoPboOid = getEcoPboOid(epmDoc);
	if (ecoPboOid == null) {
	    return null;
	} else {
	    return (WTChangeOrder2) CommonUtil.getObject(ecoPboOid); // null 도 갈 수 있음
	}
    }

    // PBO 객체를 가져온다.
    @Override
    public Persistable getPbo(EPMDocument epmDoc) throws Exception {

	if (epmDoc == null || epmDoc.getLifeCycleState() != State.toState("APPROVED")) {
	    return null;
	}

	epmApprovalHandler.getApprovalPBO(epmDoc, true);
	Persistable pbo = epmApprovalHandler.getPbo();

	return pbo;
    }
    
    // PBO 객체를 가져온다.
    @Override
    public Persistable getSavedPbo(EPMDocument epmDoc) throws Exception{
	
	QueryResult qr = KetEpmApprovalHelper.service.getEpmApprovalLink(epmDoc, null);

	if (qr != null && qr.size() > 0) {

	    Object[] objs = (Object[]) qr.nextElement();

	    KETEpmApprovalHisLink historyLink = (KETEpmApprovalHisLink) objs[0];

	    KETEpmApprovalHis epmApprovalHis = historyLink.getApprovalHis();
	    String pboOid = epmApprovalHis.getPboOid();
	    if(StringUtils.isNotEmpty(pboOid)){
		Persistable pers = (Persistable)CommonUtil.getObject(pboOid);
		return pers;
	    }
	} 
	
	return null;
    }
    
    // PBO 객체를 가져온다.
    @Override
    public KETEpmApprovalHis getApprovalHis(EPMDocument epmDoc) throws Exception{
	
	QueryResult qr = KetEpmApprovalHelper.service.getEpmApprovalLink(epmDoc, null);

	if (qr != null && qr.size() > 0) {

	    Object[] objs = (Object[]) qr.nextElement();

	    KETEpmApprovalHisLink historyLink = (KETEpmApprovalHisLink) objs[0];

	    KETEpmApprovalHis epmApprovalHis = historyLink.getApprovalHis();
	    return epmApprovalHis;
	} 
	
	return null;
    }
    
    // 관련된 결재상태에 무관하게 전체 ECO 리스트를 가져온다.
    @Override
    public Persistable[] getRelatedEcoList(String epmDocOid){
	
	Persistable[] persDefault = new  Persistable[]{};
	
	if(StringUtils.isEmpty(epmDocOid)){
	    return persDefault;
	}
	
	try {
	    
	    EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(epmDocOid);
	    List<String> list = epmApprovalDao.getRelatedEcoList(epmDoc);
	    
	    if(list == null || list.size() == 0){
		return persDefault;
	    }
	    
	    Persistable[] pers = new Persistable[list.size()];
	    for(int k=0; k<list.size(); k++){
		pers[k] = (Persistable)CommonUtil.getObject(list.get(k));
	    }
	    
	    return pers;
	    
        } catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return persDefault;
        }
    }

    // 도면과 결재 테이블을 가져오기
    @Override
    public QueryResult getEpmApprovalLink(EPMDocument epmDoc, KETEpmApprovalHis approvalHis) throws WTException {

	QuerySpec qs = new QuerySpec();
	int idx_link = qs.appendClassList(KETEpmApprovalHisLink.class, true);

	if (epmDoc != null) {
	    qs.appendWhere(new SearchCondition(KETEpmApprovalHisLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL, VersionControlHelper.getBranchIdentifier(epmDoc)),
		    new int[] { idx_link });
	}

	if (approvalHis != null) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(KETEpmApprovalHisLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(approvalHis).getId()),
		    new int[] { idx_link });
	}

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	return qr;
    }

    // ECO 결재완료 시점에 결재정보를 넣어준다.
    @Override
    public void updateEpmApprovalInfoWhenEcoApproved(KETProdChangeOrder changeOrder2) throws Exception {
	// 해당하는 ECO에 연결된 도면을 모두 가져온다.
	List<String> epmOidList = epmApprovalDao.getEpmOidByEco(changeOrder2);
	for(String epmVerOid : epmOidList){
	    EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(epmVerOid);
	    epmDoc = (EPMDocument) VersionControlHelper.getLatestIteration(epmDoc, false);
	    Kogger.debug(getClass(), "epmDoc Approval 처리 :" + epmDoc.getNumber() + "<=>" + epmDoc.getVersionIdentifier().getValue());
	    updateEpmApprovalHistory(epmDoc, changeOrder2, null);
	}
    }

    @Override
    public void updateEpmApprovalInfoWhenEcoApproved(KETMoldChangeOrder changeOrder2) throws Exception {
	// 해당하는 ECO에 연결된 도면을 모두 가져온다.
	List<String> epmOidList = epmApprovalDao.getEpmOidByEco(changeOrder2);
	for(String epmVerOid : epmOidList){
	    EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(epmVerOid);
	    epmDoc = (EPMDocument) VersionControlHelper.getLatestIteration(epmDoc, false);
	    Kogger.debug(getClass(), "epmDoc Approval 처리 :" + epmDoc.getNumber() + "<=>" + epmDoc.getVersionIdentifier().getValue());
	    updateEpmApprovalHistory(epmDoc, null, changeOrder2);
	}
    }
    
    @Override
    public void updateEpmApprovalInfoWhenEcoApproved(EPMDocument epmDoc) throws Exception{
	  
	HashMap ibaValues = EDMAttributes.getIBAValues(epmDoc,Locale.KOREAN);
	String category = "";
	if(ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
	    category = EDMEnumeratedTypeUtil.getCADCategory((String)ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),Locale.KOREAN);
	}
	
	if("DEV_REVIEW_DRAWING".equals(category) || "PRODUCT_REFERENCE_DRAWING".equals(category)){
	    updateEpmApprovalHistory(epmDoc, null, null);
	}
    }

    private void updateEpmApprovalHistory(EPMDocument epmDoc, KETProdChangeOrder prodChangeOrder, KETMoldChangeOrder moldChangeOrder) throws Exception {
	
	// 1. PBO를 찾는다. : pbo object type, pbo object OID, ECO NO
	// WTObject pbo = KETCommonHelper.service.getPBO( epmDoc );
	
	Persistable pbo = null;
	String pboOid = null;
	String ecoNo = "";
	String approvalType = null;
	
	if(prodChangeOrder != null){
	    
	    approvalType = "WTChangeOrder2";
	    pbo = prodChangeOrder;
	    pboOid = CommonUtil.getOIDString(prodChangeOrder);
	    ecoNo = prodChangeOrder.getEcoId();
	    
	}else if(moldChangeOrder != null){
	    
	    approvalType = "WTChangeOrder2";
	    pbo = moldChangeOrder;
	    pboOid = CommonUtil.getOIDString(moldChangeOrder);
	    ecoNo = moldChangeOrder.getEcoId();
	    
	}else{
	
	    EpmApprovalHandler handler = new EpmApprovalHandler();
	    handler.getApprovalPBO(epmDoc, true);

	    approvalType = handler.getType();
	    pbo = handler.getPbo();

	    if (pbo == null) {
		Kogger.debug(getClass(), "EPM_Approval<=>" + epmDoc.getNumber() + "<=>" + epmDoc.getVersionIdentifier().getValue() + "<=>" + epmDoc.getIterationIdentifier().getValue() + "<=>"
		        + "HAS_NOT_PBO<=>" + "결재 정보 부재");
		return;
	    }

	    pboOid = CommonUtil.getOIDString(pbo);
	    
	    if (pbo instanceof KETProdChangeOrder) {
		ecoNo = ((KETProdChangeOrder) pbo).getEcoId();
	    } else if (pbo instanceof KETMoldChangeOrder) {
		ecoNo = ((KETMoldChangeOrder) pbo).getEcoId();
	    }
	}

	// 2. 결재정보를 가져온다. : 결재 승인자ID, 승인자명, 대리자, 요청일, 완료일, 결재Master Oid
	EpmApprovalHistoryHandler history = new EpmApprovalHistoryHandler();
	history.getApprovalHistory(pbo);

	if (history.getApprovalDate() == null) {
	    Kogger.debug(getClass(), "EPM_Approval<=>" + epmDoc.getNumber() + "<=>"+ epmDoc.getVersionIdentifier().getValue() + "<=>"
		    + epmDoc.getIterationIdentifier().getValue() + "<=>" + "HAS_NOT_COMPELETE_DATE<=>"  + "결재 완료일 부재");
	} else if (history.getApproverName() == null) {
	    Kogger.debug(getClass(), "EPM_Approval<=>" + epmDoc.getNumber() + "<=>"+ epmDoc.getVersionIdentifier().getValue() + "<=>"
		    + epmDoc.getIterationIdentifier().getValue() + "<=>" + "HAS_NOT_COMPELETE_USER<=>"  + "결재 승인자 부재");
	}

	// 3. KETEpmApprovalHis 에 있으면 업데이트 없으면, INSERT -- Unique Key 생성한다.
	QueryResult qr = KetEpmApprovalHelper.service.getEpmApprovalLink(epmDoc, null);

	if (qr != null && qr.size() > 0) {

	    Object[] objs = (Object[]) qr.nextElement();

	    KETEpmApprovalHisLink historyLink = (KETEpmApprovalHisLink) objs[0];

	    KETEpmApprovalHis epmApprovalHis = historyLink.getApprovalHis();

	    epmApprovalHis.setApprovalType(approvalType);
	    epmApprovalHis.setPboOid(pboOid);
	    epmApprovalHis.setEcoNo(ecoNo);

	    epmApprovalHis.setRequestDate(history.getRequestDate());
	    epmApprovalHis.setApprovalDate(history.getApprovalDate());
	    epmApprovalHis.setApproverId(history.getApproverId());
	    epmApprovalHis.setApproverName(history.getApproverName());
	    epmApprovalHis.setApproverDelegate(history.getApproverDelegate());
	    epmApprovalHis.setApprovalMastOid(history.getApprovalMastOid());

	    PersistenceServerHelper.manager.update(epmApprovalHis);

	} else {

	    KETEpmApprovalHis epmApprovalHis = KETEpmApprovalHis.newKETEpmApprovalHis();

	    epmApprovalHis.setApprovalType(approvalType);
	    epmApprovalHis.setPboOid(pboOid);
	    epmApprovalHis.setEcoNo(ecoNo);

	    epmApprovalHis.setRequestDate(history.getRequestDate());
	    epmApprovalHis.setApprovalDate(history.getApprovalDate());
	    epmApprovalHis.setApproverId(history.getApproverId());
	    epmApprovalHis.setApproverName(history.getApproverName());
	    epmApprovalHis.setApproverDelegate(history.getApproverDelegate());
	    epmApprovalHis.setApprovalMastOid(history.getApprovalMastOid());

	    PersistenceHelper.manager.save(epmApprovalHis);

	    KETEpmApprovalHisLink historyLink = KETEpmApprovalHisLink.newKETEpmApprovalHisLink(epmApprovalHis, epmDoc);

	    PersistenceHelper.manager.save(historyLink);
	}
    }

}