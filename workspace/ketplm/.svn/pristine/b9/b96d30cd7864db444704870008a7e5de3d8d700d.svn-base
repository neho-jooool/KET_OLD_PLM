package ext.ket.edm.approval.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import wt.change2.WTChangeOrder2;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import e3ps.edm.EPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.beans.EDMPBOHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.edm.util.EDMProperties;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.entity.KETWfmMultiReqEpmLink;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.edm.approval.KetEpmApprovalHelper;
import ext.ket.shared.log.Kogger;

public class EpmApprovalHandler {

    private String type = null;
    private Persistable pbo = null;
//    private EpmApprovalDao epmApprovalDao = new EpmApprovalDao();

    public void getApprovalPBO(EPMDocument epmDoc, boolean withOthers) throws Exception {

	KETWfmApprovalMaster master = getPBOBySelf(epmDoc);
	if (master != null) {
	    type = "EPMDocument";
	    // pbo는 미리 세팅함
	    return;
	}

	KETWfmMultiApprovalRequest multiReq = getPBOByMulti(epmDoc);
	if (multiReq != null) {
	    type = "KETWfmMultiApprovalRequest";
	    pbo = multiReq;
	    return;
	}

	WTChangeOrder2 ecoObj = getPBOByEco(epmDoc);
	if (ecoObj != null) {
	    type = "WTChangeOrder2";
	    pbo = ecoObj;
	    return;
	}

	if (withOthers) {
	    getPBOByOthers(epmDoc);
	}

    }

    // 자체 결재
    private KETWfmApprovalMaster getPBOBySelf(EPMDocument epmDoc) throws Exception {

	Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: EPMDocument");

	String pboOid = null;
	EPMDocument epm = (EPMDocument) epmDoc;
	HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);
	String category = "";
	EPMDocument ecadPCB = null;
	if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
	    category = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN);
	}
	if ("ECAD_DRAWING".equals(category)) {
	    EDMProperties edmProperties = EDMProperties.getInstance();
	    ArrayList relateds = null;
	    WTPart part = null;
	    try {
		relateds = EDMHelper.getReferencedByParts(epm, edmProperties.getReferenceType(category), EDMHelper.REQUIRED_STANDARD);
		if ((relateds != null) && (relateds.size() > 0)) {
		    part = (WTPart) ((Object[]) relateds.get(0))[1];
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	    if ((epm != null) && (part != null)) {
		//ArrayList ecads = EDMHelper.getAssociatedDocsByECAD(epm, part);
		// 마이그레이션 때문에 수정함.
		// 차후 open하고 나서는 날짜별로 분기할 필요 있음.
		ArrayList ecads = getAssociatedDocsByECAD(epm, part);
		for (int a = 0; a < ecads.size(); a++) {
		    EPMDocument ecad = (EPMDocument) ecads.get(a);
		    if ("PADS".equals(ecad.getAuthoringApplication().toString())) {
			ecadPCB = ecad;
		    }
		}
	    }

	    if (ecadPCB != null) {
		KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(epmDoc);
		if(master != null){
		    pbo = ecadPCB;
		}
		return master;
	    }
	}

	KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(epmDoc);
	if(master != null){
	    pbo = epmDoc;
	}
	return master;
    }

    // 멀티 결재
    private KETWfmMultiApprovalRequest getPBOByMulti(EPMDocument epmDoc) throws Exception {

	Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: EPMDocument");

	KETWfmMultiApprovalRequest multiReq = null;

	QueryResult multiReqQR = PersistenceHelper.manager.navigate(epmDoc, "request", KETWfmMultiReqEpmLink.class);

	if (multiReqQR.size() > 0) {

	    Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: Multiple Approval");
	    multiReq = (KETWfmMultiApprovalRequest) multiReqQR.nextElement();
	}

	return multiReq;
    }

    // ECO 결재
    private WTChangeOrder2 getPBOByEco(EPMDocument epmDoc) throws Exception {

	Kogger.debug(getClass(), "======> StandardKETCommonService.java :: getPBO() :: EPMDocument");

	WTChangeOrder2 ecoObj = KetEpmApprovalHelper.service.getEcoPbo(epmDoc);
	// WTChangeOrder2 ecoObj = (WTChangeOrder2) KETCommonHelper.service.getRelatedEco(epmDoc); // KETCommonService

	return ecoObj;
    }

    // 연관도면일 경우 처리
    private void getPBOByOthers(EPMDocument epmDoc) throws Exception {

	EPMDocument resultEPM = EDMPBOHelper.getPrimaryBusinessObject(epmDoc);

	if (resultEPM != null) {

	    type = "WTChangeOrder2";
	    getApprovalPBO(resultEPM, false);

	}
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Persistable getPbo() {
	return pbo;
    }

    public void setPbo(Persistable pbo) {
	this.pbo = pbo;
    }
    
    public ArrayList getAssociatedDocsByECAD(EPMDocument epm, WTPart part) throws WTException {
	
	ArrayList result = new ArrayList();

	try {
	    QuerySpec qs = new QuerySpec();

	    int idx_pm = qs.appendClassList(EPMLink.class, false);
	    int idx_epm = qs.appendClassList(EPMDocument.class, true);

	    SearchCondition sc = null;

	    // sc = new SearchCondition(EPMLink.class,EPMLink.ECAD, SearchCondition.EQUAL,true);
	    // qs.appendWhere(sc, new int[]{idx_pm});

	    // qs.appendAnd();

	    sc = new SearchCondition(EPMLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(part.getMaster()).getId());
	    qs.appendWhere(sc, new int[] { idx_pm });

	    qs.appendAnd();
	    sc = new SearchCondition(new ClassAttribute(EPMLink.class, "roleAObjectRef.key.id"), "=", new ClassAttribute(EPMDocument.class, "masterReference.key.id"));

	    sc.setFromIndicies(new int[] { idx_pm, idx_epm }, 0);
	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { idx_pm, idx_epm });

	    // TODO TKLEE 마이그레이션 후에 변경 필요 
	    qs.appendAnd();
	    sc = new SearchCondition(EPMDocument.class, "versionInfo.identifier.versionId", SearchCondition.EQUAL, epm.getVersionIdentifier().getSeries().getValue());
	    qs.appendWhere(sc, new int[] { idx_epm });

	    qs.appendAnd();
	    qs.appendWhere(VersionControlHelper.getSearchCondition(EPMDocument.class, true), new int[] { idx_epm });
	    Kogger.debug(getClass(), "ECAD 쿼리 == " + qs);
	    Object[] oo = null;

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    while (qr.hasMoreElements()) {
		oo = (Object[]) qr.nextElement();
		result.add(oo[0]);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e.getLocalizedMessage());
	}

	return result;
    }

}
