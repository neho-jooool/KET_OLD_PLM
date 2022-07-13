/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EcmSearchHelper.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 12. 9.
 */
package e3ps.ecm.beans;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.CompoundQuerySpec;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.vc.Mastered;
import wt.vc.Versioned;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.VersionUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.dao.EcmComDao;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETMoldECADocLink;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETMoldECOPartLink;
import e3ps.ecm.entity.KETMoldECRPartLink;
import e3ps.ecm.entity.KETMoldProdEcoLink;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.ecm.entity.KETProdECADocLink;
import e3ps.ecm.entity.KETProdECAEpmDocLink;
import e3ps.ecm.entity.KETProdECOPartLink;
import e3ps.ecm.entity.KETProdECRPartLink;
import e3ps.edm.beans.EDMChangeHelper;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.VersionHelper;
import e3ps.project.MoldItemInfo;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EcmSearchHelper 설명 : 타모듈을 위한 공통 Class 작성자 : 오승연 작성일자 : 2010. 12. 9.
 */
public class EcmSearchHelper {

    public static EcmSearchHelper manager = new EcmSearchHelper();

    /**
     * 함수명 : getPartChangeInfo 설명 : Part No 에 대한 ECO 횟수와 최종 변경일자를 가져오는 함수
     * 
     * @param partNo
     * @return 작성자 : 오승연 작성일자 : 2010. 12. 9.
     */
    public Hashtable<String, String> getPartChangeInfo(String partNo, String startDate, String endDate) {
	Hashtable<String, String> changeInfo = null;
	Connection conn = null;

	try {
	    conn = PlmDBUtil.getConnection();

	    EcmComDao comDao = new EcmComDao(conn);
	    changeInfo = comDao.getPartChangeInfo(partNo, startDate, endDate);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}

	return changeInfo;

    }

    /**
     * 함수명 : getEcoObject 설명 : ecoId에 해당하는 ECO 객체를 가져오는 함수
     * 
     * @param ecoId
     * @return
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 12. 16.
     */
    public Persistable getEcoObject(String ecoId) throws Exception {
	Persistable ecoObj = null;

	try {
	    QuerySpec spec = new QuerySpec(KETProdChangeOrder.class);
	    SearchUtil.appendEQUAL(spec, KETProdChangeOrder.class, KETProdChangeOrder.ECO_ID, ecoId, 0);

	    QueryResult result = PersistenceHelper.manager.find(spec);

	    if (result != null && result.hasMoreElements()) {
		ecoObj = (Persistable) result.nextElement();
	    } else {
		spec = new QuerySpec(KETMoldChangeOrder.class);
		SearchUtil.appendEQUAL(spec, KETMoldChangeOrder.class, KETMoldChangeOrder.ECO_ID, ecoId, 0);

		result = PersistenceHelper.manager.find(spec);

		if (result != null && result.hasMoreElements()) {
		    ecoObj = (Persistable) result.nextElement();
		}
	    }
	} catch (WTException e) {
	    throw new Exception(e);
	}

	return ecoObj;
    }

    public void batchPartRevise(String[] oids) throws WTException {
	Transaction trx = new Transaction();

	try {
	    if ((oids == null) || (oids.length == 0)) {
		return;
	    }

	    trx.start();

	    ReferenceFactory rf = new ReferenceFactory();

	    Versioned versioned = null;
	    for (int i = 0; i < oids.length; i++) {
		versioned = (Versioned) rf.getReference(oids[i]).getObject();
		if (versioned instanceof EPMDocument) {
		    doPartRevise((WTPart) versioned, null, null, null, null);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();

	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
    }

    /**
     * 함수명 : getEcoObjectOid 설명 : ecoId에 해당하는 ECO Oid를 가져오는 함수
     * 
     * @param ecoId
     * @return
     * @throws Exception
     *             작성자 : 오승연 작성일자 : 2010. 12. 16.
     */
    public String getEcoObjectOid(String ecoId) throws Exception {
	String oid = "";
	Persistable ecoObj = null;

	try {
	    QuerySpec spec = new QuerySpec(KETProdChangeOrder.class);
	    SearchUtil.appendEQUAL(spec, KETProdChangeOrder.class, KETProdChangeOrder.ECO_ID, ecoId, 0);

	    QueryResult result = PersistenceHelper.manager.find(spec);

	    if (result != null && result.hasMoreElements()) {
		ecoObj = (Persistable) result.nextElement();
	    } else {
		spec = new QuerySpec(KETMoldChangeOrder.class);
		SearchUtil.appendEQUAL(spec, KETMoldChangeOrder.class, KETMoldChangeOrder.ECO_ID, ecoId, 0);

		result = PersistenceHelper.manager.find(spec);

		if (result != null && result.hasMoreElements()) {
		    ecoObj = (Persistable) result.nextElement();
		}
	    }

	    oid = KETObjectUtil.getOidString(ecoObj);
	} catch (WTException e) {
	    throw new Exception(e);
	}

	return oid;
    }

    public WTPart doPartRevise(WTPart part, String location, String lifecycle, String state, String version) throws WTException {

	WTPart newPart = null;
	try {
	    if (!VersionHelper.isLatestRevision(part)) {
		throw new WTException("최신버전이 아닙니다.");
	    }

	    EDMProperties edmProperties = EDMProperties.getInstance();

	    if ((location == null) || (location.trim().length() == 0)) {
		location = FolderHelper.service.getFolder((FolderEntry) part).getLocation();
	    }
	    if ((lifecycle == null) || (lifecycle.trim().length() == 0)) {
		lifecycle = edmProperties.getEPMDefaultLC();
	    }
	    if ((state == null) || (state.trim().length() == 0)) {
		state = "";
	    }
	    if ((version == null) || (version.trim().length() == 0)) {
		version = "";
	    }

	    newPart = (WTPart) VersionUtil.doRevise(part, version, lifecycle, location, state, null, null);

	} catch (WTException e) {
	    throw new WTException(e);
	} catch (Exception e) {
	    throw new WTException(e);
	}
	return (WTPart) PersistenceHelper.manager.refresh(newPart);
    }

    /**
     * 함수명 : getRelatedEcoList 설명 : 도면이 관련된 ECO 정보를 가져오는 함수
     * 
     * @param epmDocOid
     * @return 작성자 : 오승연 작성일자 : 2010. 12. 21.
     */
    public Persistable[] getRelatedEcoList(String epmDocOid) {
	Persistable[] ecoObjList = null;
	ArrayList<Persistable> tempArray = new ArrayList<Persistable>();
	QueryResult qr = null;
	QueryResult subQr = null;

	KETProdChangeActivity prodECA = null;
	KETMoldChangeActivity moldECA = null;
	Persistable eco = null;

	EPMDocument preEpmDoc = null;

	KETProdECAEpmDocLink prodEpmDocLink = null;
	KETMoldECAEpmDocLink moldEpmDocLink = null;

	try {
	    EPMDocument afterEpmDoc = (EPMDocument) KETObjectUtil.getObject(epmDocOid);
	    Mastered master = afterEpmDoc.getMaster();
	    String version = VersionUtil.getMajorVersion(afterEpmDoc);

	    QuerySpec spec = new QuerySpec(KETProdECAEpmDocLink.class);
	    SearchUtil.appendEQUAL(spec, KETProdECAEpmDocLink.class, KETProdECAEpmDocLink.AFTER_VERSION, version, 0);
	    QueryResult result = PersistenceHelper.manager.find(spec);

	    while (result.hasMoreElements()) {
		prodEpmDocLink = (KETProdECAEpmDocLink) result.nextElement();

		if (prodEpmDocLink.getEpmDoc().getMaster().equals(master)) {
		    preEpmDoc = prodEpmDocLink.getEpmDoc();
		}
	    }

	    if (preEpmDoc == null) {
		spec = new QuerySpec(KETMoldECAEpmDocLink.class);
		SearchUtil.appendEQUAL(spec, KETMoldECAEpmDocLink.class, KETMoldECAEpmDocLink.AFTER_VERSION, version, 0);
		result = PersistenceHelper.manager.find(spec);

		while (result.hasMoreElements()) {
		    moldEpmDocLink = (KETMoldECAEpmDocLink) result.nextElement();

		    if (moldEpmDocLink.getEpmDoc().getMaster().equals(master)) {
			preEpmDoc = moldEpmDocLink.getEpmDoc();
		    }
		}
	    }

	    if (preEpmDoc != null) {
		// 제품 ECO
		qr = PersistenceHelper.manager.navigate(preEpmDoc, "prodECA", KETProdECAEpmDocLink.class);

		while (qr.hasMoreElements()) {
		    prodECA = (KETProdChangeActivity) qr.nextElement();
		    subQr = PersistenceHelper.manager.navigate(prodECA, "prodECO", KETProdChangeActivityLink.class);

		    while (subQr.hasMoreElements()) {
			eco = (Persistable) subQr.nextElement();
			tempArray.add(eco);
		    }
		}

		// 금형 ECO
		qr = PersistenceHelper.manager.navigate(preEpmDoc, "moldECA", KETMoldECAEpmDocLink.class);

		while (qr.hasMoreElements()) {
		    moldECA = (KETMoldChangeActivity) qr.nextElement();
		    subQr = PersistenceHelper.manager.navigate(moldECA, "moldECO", KETMoldChangeActivityLink.class);

		    while (subQr.hasMoreElements()) {
			eco = (Persistable) subQr.nextElement();
			tempArray.add(eco);
		    }
		}
	    }

	    if (tempArray.size() > 0) {
		ecoObjList = new Persistable[tempArray.size()];
		tempArray.toArray(ecoObjList);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return ecoObjList;
    }

    /**
     * 함수명 : isLastActivity 설명 : ECA 객체가 ECO에 포함된 객체 중 마지막으로 완료할 객체인지 여부를 체크하는 함수
     * 
     * @param pbo
     *            ( ECA 객체 )
     * @return boolean : 마지막 객체 여부 작성자 : 오승연 작성일자 : 2010. 12. 21.
     */
    public boolean isLastActivity(Persistable pbo) {
	boolean isLastActivity = true;

	QueryResult qr = null;
	QueryResult subQr = null;
	KETProdChangeOrder prodEco = null;
	KETProdChangeActivity prodECA = null;
	KETMoldChangeOrder moldEco = null;
	KETMoldChangeActivity moldECA = null;

	try {
	    if (pbo instanceof KETProdChangeActivity) {
		qr = PersistenceHelper.manager.navigate(pbo, "prodECO", KETProdChangeActivityLink.class);

		if (qr.hasMoreElements()) {
		    prodEco = (KETProdChangeOrder) qr.nextElement();

		    subQr = PersistenceHelper.manager.navigate(prodEco, "prodECA", KETProdChangeActivityLink.class);

		    while (subQr.hasMoreElements()) {
			prodECA = (KETProdChangeActivity) subQr.nextElement();

			if (!(prodECA.getLifeCycleState().getDisplay().equals("작업완료"))
			        && (!prodECA.getActivityType().equals("3") && !prodECA.getActivityType().equals("4"))) {
			    isLastActivity = false;
			    break;
			}
		    }
		}
	    } else if (pbo instanceof KETMoldChangeActivity) {
		qr = PersistenceHelper.manager.navigate(pbo, "moldECO", KETMoldChangeActivityLink.class);

		if (qr.hasMoreElements()) {
		    moldEco = (KETMoldChangeOrder) qr.nextElement();

		    subQr = PersistenceHelper.manager.navigate(moldEco, "moldECA", KETMoldChangeActivityLink.class);

		    while (subQr.hasMoreElements()) {
			moldECA = (KETMoldChangeActivity) subQr.nextElement();

			// if (!(moldECA.getLifeCycleState().getDisplay().equals("작업완료"))) {
			if (!(moldECA.getLifeCycleState().getDisplay().equals("작업완료")) && (!moldECA.getActivityType().equals("4"))) {
			    isLastActivity = false;
			    break;
			}
		    }
		}
	    }
	} catch (Exception e) {
	    isLastActivity = false;
	    Kogger.error(getClass(), e);
	}

	return isLastActivity;
    }

    /**
     * 함수명 : getLatestObject 설명 : 하위 Revsion Oid로 최신 Revision Object를 가져오는 함수
     * 
     * @param objOid
     * @return 작성자 : 오승연 작성일자 : 2010. 12. 23.
     */
    public RevisionControlled getLatestObject(String objOid) {
	RevisionControlled lastestObj = null;
	try {
	    RevisionControlled revObj = (RevisionControlled) KETObjectUtil.getObject(objOid);
	    Master objMaster = (Master) revObj.getMaster();

	    lastestObj = VersionUtil.getLatestObject(objMaster);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return lastestObj;
    }

    public Persistable[] getRelatedObjList(Persistable pbo) {
	Persistable[] pboList = null;
	QueryResult qr = null;
	QueryResult subQr = null;
	ArrayList<Persistable> pboArry = new ArrayList<Persistable>();
	try {
	    if (pbo instanceof KETProdChangeOrder) {
		KETProdChangeActivity prodECA = null;
		EPMDocument epmDoc = null;
		KETBomEcoHeader ecoHeader = null;
		WTPart part = null;

		qr = PersistenceHelper.manager.navigate(pbo, "prodECA", KETProdChangeActivityLink.class);

		while (qr.hasMoreElements()) {
		    prodECA = (KETProdChangeActivity) qr.nextElement();

		    if (prodECA.getActivityType().equals("1")) {
			subQr = PersistenceHelper.manager.navigate(prodECA, "epmDoc", KETProdECAEpmDocLink.class);

			while (subQr.hasMoreElements()) {
			    epmDoc = (EPMDocument) subQr.nextElement();
			    pboArry.add(VersionUtil.getLatestObject((Master) epmDoc.getMaster()));
			}
		    } else if (prodECA.getActivityType().equals("2")) {
			subQr = PersistenceHelper.manager.navigate(prodECA, "bom", KETProdECABomLink.class);

			while (subQr.hasMoreElements()) {
			    ecoHeader = (KETBomEcoHeader) subQr.nextElement();
			    part = KETPartHelper.service.getPart(ecoHeader.getEcoItemCode());
			    pboArry.add(part);
			}

		    }
		}
	    } else if (pbo instanceof KETMoldChangeOrder) {
		KETMoldChangeActivity moldECA = null;
		EPMDocument epmDoc = null;
		KETBomEcoHeader ecoHeader = null;
		WTPart part = null;

		qr = PersistenceHelper.manager.navigate(pbo, "moldECA", KETMoldChangeActivityLink.class);

		while (qr.hasMoreElements()) {
		    moldECA = (KETMoldChangeActivity) qr.nextElement();

		    if (moldECA.getActivityType().equals("1")) {
			subQr = PersistenceHelper.manager.navigate(moldECA, "epmDoc", KETMoldECAEpmDocLink.class);

			while (subQr.hasMoreElements()) {
			    epmDoc = (EPMDocument) subQr.nextElement();
			    pboArry.add(VersionUtil.getLatestObject((Master) epmDoc.getMaster()));
			}
		    } else if (moldECA.getActivityType().equals("2")) {
			subQr = PersistenceHelper.manager.navigate(moldECA, "bom", KETMoldECABomLink.class);

			while (subQr.hasMoreElements()) {
			    ecoHeader = (KETBomEcoHeader) subQr.nextElement();

			    part = KETPartHelper.service.getPart(ecoHeader.getEcoItemCode());
			    pboArry.add(part);
			}

		    }
		}
	    }

	    pboList = new Persistable[pboArry.size()];
	    pboArry.toArray(pboList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return pboList;
    }

    public EPMDocument[] getRelatedECAD(String epmDocOid) {
	EPMDocument[] relatedECADList = null;

	try {
	    EPMDocument epmDoc = (EPMDocument) KETObjectUtil.getObject(epmDocOid);

	    ArrayList relatedList = EDMChangeHelper.getAssociatedDocsByECAD(epmDoc);

	    if (relatedList != null && relatedList.size() > 0) {
		relatedECADList = new EPMDocument[relatedList.size()];

		for (int cadCnt = 0; cadCnt < relatedList.size(); cadCnt++) {
		    relatedECADList[cadCnt] = (EPMDocument) relatedList.get(cadCnt);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return relatedECADList;
    }

    public KETProdECAEpmDocLink getProdEcaEpmDocLink(EPMDocument epmDoc) {
	KETProdECAEpmDocLink epmDocLink = null;

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(epmDoc, "prodECA", KETProdECAEpmDocLink.class, false);

	    while (qr.hasMoreElements()) {
		epmDocLink = (KETProdECAEpmDocLink) qr.nextElement();
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return epmDocLink;
    }

    public KETBomEcoHeader getProdEcoBomHeader(String ecoId, String partNo) {
	KETBomEcoHeader bomHeader = null;

	try {
	    QuerySpec spec = new QuerySpec(KETBomEcoHeader.class);
	    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER, ecoId, 0);
	    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, partNo, 0);

	    QueryResult result = PersistenceHelper.manager.find(spec);

	    if (result != null && result.hasMoreElements()) {
		bomHeader = (KETBomEcoHeader) result.nextElement();
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return bomHeader;
    }

    public KETProdECADocLink getProdEcaDocLink(KETProdChangeOrder prodEco, KETProjectDocument document) {
	KETProdECADocLink docLink = null;
	KETProdChangeActivity prodECA = null;
	KETProdChangeOrder existEco = null;

	try {
	    QueryResult subQr = null;
	    QueryResult qr = PersistenceHelper.manager.navigate(document, "prodECA", KETProdECADocLink.class, false);

	    while (qr.hasMoreElements()) {
		docLink = (KETProdECADocLink) qr.nextElement();
		prodECA = docLink.getProdECA();

		subQr = PersistenceHelper.manager.navigate(prodECA, "prodECO", KETProdChangeActivityLink.class);
		while (subQr.hasMoreElements()) {
		    existEco = (KETProdChangeOrder) subQr.nextElement();

		    if (!existEco.equals(prodEco)) {
			docLink = null;
		    }
		}
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return docLink;
    }

    public KETProdECABomLink getProdEcaBomLink(KETBomEcoHeader bomHeader) {
	KETProdECABomLink bomLink = null;

	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(bomHeader, "prodECA", KETProdECABomLink.class, false);

	    while (qr.hasMoreElements()) {
		bomLink = (KETProdECABomLink) qr.nextElement();
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return bomLink;
    }

    public KETProdECABomLink[] getRelatedMassBomLink(String prodECAOid, String parentPartList) {
	KETProdECABomLink[] massBomLinkList = null;
	ArrayList<KETProdECABomLink> tempArr = new ArrayList<KETProdECABomLink>();
	KETProdECABomLink tempBomLink = null;

	try {
	    QuerySpec spec = new QuerySpec(KETProdECABomLink.class);
	    SearchUtil.appendEQUAL(spec, KETProdECABomLink.class, KETProdECABomLink.RELATED_PARENT_PART_LIST, parentPartList, 0);
	    SearchUtil.appendEQUAL(spec, KETProdECABomLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(prodECAOid), 0);

	    QueryResult result = PersistenceHelper.manager.find(spec);

	    if (result != null && result.hasMoreElements()) {
		tempBomLink = (KETProdECABomLink) result.nextElement();
		tempArr.add(tempBomLink);
	    }

	    massBomLinkList = new KETProdECABomLink[tempArr.size()];
	    tempArr.toArray(massBomLinkList);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return massBomLinkList;
    }

    /**
     * 함수명 : isSecureBudget 설명 : ECO의 예산확보가 되었는지 확인하는 함수
     * 
     * @param ecoOid
     * @return 작성자 : 오승연 작성일자 : 2011. 2. 21.
     */
    public boolean isSecureBudget(String ecoOid) {
	boolean isSecured = true;
	KETProdChangeOrder prodEco = null;
	KETProdECOPartLink prodPartLink = null;
	KETMoldECOPartLink moldPartLink = null;
	KETMoldChangeOrder moldEco = null;

	String rtnBudgetFlag = "";
	QueryResult qr = null;

	try {
	    if (ecoOid.startsWith("e3ps.ecm.entity.KETProdChangeOrder")) {
		prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(ecoOid);
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETMoldChangeOrder")) {
		moldEco = (KETMoldChangeOrder) KETObjectUtil.getObject(ecoOid);
	    } else {
		isSecured = false;
	    }

	    if (prodEco != null) {
		/*
	         * qr = PersistenceHelper.manager.navigate(prodEco, "part", KETProdECOPartLink.class, false); while (qr.hasMoreElements()) {
	         * prodPartLink = (KETProdECOPartLink) qr.nextElement();
	         * 
	         * if (prodPartLink.getSecureBudgetYn().equals("N")) { isSecured = false; break; } }
	         */

		qr = PersistenceHelper.manager.navigate(prodEco, "prodECA", KETProdChangeActivityLink.class, false);
		while (qr.hasMoreElements()) {
		    KETProdChangeActivityLink ecaLink = (KETProdChangeActivityLink) qr.nextElement();
		    KETProdChangeActivity eca = ecaLink.getProdECA();
		    String activityType = StringUtils.stripToEmpty(eca.getActivityType());
		    if (activityType.equals("2")) {

			QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
			while (qr2.hasMoreElements()) {
			    KETProdECABomLink bomLink = (KETProdECABomLink) qr2.nextElement();
			    if (bomLink.getSecureBudgetYn() != null && bomLink.getSecureBudgetYn().equals("N")) {
				isSecured = false;
				break;
			    }
			}

		    }
		}

		// 양산일 경우 비용확보는 무조건 '성공'이다. --> 부품 단위로 개발단계를 체크 하는 것으로 변경했기 때문에 아래 로직은 주석처리한다 2020.12.08
		// if (prodEco.getDevYn().equalsIgnoreCase("PRODUCTION") || prodEco.getDevYn().equalsIgnoreCase("P")) {
		// isSecured = true;
		// }

		// 초도일 경우 비용확보는 무조건 '성공'이다.
		String changeReason = StringUtils.stripToEmpty(prodEco.getChangeReason());
		boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);
		if (isTheFirst) {
		    isSecured = true;
		}
	    } else if (moldEco != null) {
		rtnBudgetFlag = getSecureBudgetProdEcoInMoldEco(moldEco);

		if (!rtnBudgetFlag.equalsIgnoreCase("Y")) {
		    /*
	             * qr = PersistenceHelper.manager.navigate(moldEco, "part", KETMoldECOPartLink.class, false); while
	             * (qr.hasMoreElements()) { moldPartLink = (KETMoldECOPartLink) qr.nextElement();
	             * 
	             * if (moldPartLink.getSecureBudgetYn().equals("N")) { isSecured = false; break; } }
	             */

		    qr = PersistenceHelper.manager.navigate(moldEco, "moldECA", KETMoldChangeActivityLink.class, false);
		    while (qr.hasMoreElements()) {
			KETMoldChangeActivityLink ecaLink = (KETMoldChangeActivityLink) qr.nextElement();
			KETMoldChangeActivity eca = ecaLink.getMoldECA();
			String activityType = StringUtils.stripToEmpty(eca.getActivityType());
			if (activityType.equals("2")) {

			    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
			    while (qr2.hasMoreElements()) {
				KETMoldECABomLink bomLink = (KETMoldECABomLink) qr2.nextElement();
				if (bomLink.getSecureBudgetYn() != null && bomLink.getSecureBudgetYn().equals("N")) {
				    isSecured = false;
				    break;
				}
			    }

			}
		    }

		} else {
		    isSecured = true;
		}

		// 양산일 경우 비용확보는 무조건 '성공'이다.
		if (moldEco.getDevYn().equalsIgnoreCase("PRODUCTION") || moldEco.getDevYn().equalsIgnoreCase("P")) {
		    isSecured = true;
		}

		// 초도일 경우 비용확보는 무조건 '성공'이다.
		String changeReason = StringUtils.stripToEmpty(moldEco.getChangeReason());
		boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);
		if (isTheFirst) {
		    isSecured = true;
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return isSecured;
    }

    /**
     * 
     * @param prodEco
     * @return
     * @메소드명 : getCanRequestApproveFlag
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     * @deprecated
     * 
     */
    public Hashtable<String, String> getCanRequestApproveFlag(KETProdChangeOrder prodEco) {
	Hashtable<String, String> rtnHash = new Hashtable<String, String>();

	// 초도일 경우 제외한다.
	String changeReason = StringUtils.stripToEmpty(prodEco.getChangeReason());
	boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);
	// 초도일 경우
	if (isTheFirst) {
	    rtnHash.put("flag", "TRUE");
	    rtnHash.put("msg", "결재요청 가능합니다.");
	    return rtnHash;

	}

	QueryResult qr = null;
	QueryResult epmDocQr = null;
	QueryResult bomQr = null;

	boolean isExistEpmDoc = false;
	boolean isExistBom = false;
	boolean isNotCompleteEpmDoc = false;
	boolean isNotCompleteBom = false;

	try {
	    KETProdChangeActivity prodECA = null;
	    KETProdECAEpmDocLink epmDocLink = null;
	    KETProdECABomLink bomLink = null;

	    qr = PersistenceHelper.manager.navigate(prodEco, "prodECA", KETProdChangeActivityLink.class);
	    while (qr.hasMoreElements()) {
		prodECA = (KETProdChangeActivity) qr.nextElement();

		if (prodECA.getActivityType().equals("1")) {
		    isExistEpmDoc = true;

		    epmDocQr = PersistenceHelper.manager.navigate(prodECA, "epmDoc", KETProdECAEpmDocLink.class, false);
		    while (epmDocQr.hasMoreElements()) {
			epmDocLink = (KETProdECAEpmDocLink) epmDocQr.nextElement();
			if (StringUtil.checkReplaceStr(epmDocLink.getAfterVersion(), "0").equals("0")) {
			    isNotCompleteEpmDoc = true;
			    break;
			}
		    }
		} else if (prodECA.getActivityType().equals("2")) {
		    isExistBom = true;

		    bomQr = PersistenceHelper.manager.navigate(prodECA, "bom", KETProdECABomLink.class, false);
		    while (bomQr.hasMoreElements()) {
			bomLink = (KETProdECABomLink) bomQr.nextElement();
			if (StringUtil.checkReplaceStr(bomLink.getAfterVersion(), "0").equals("0")) {
			    isNotCompleteBom = true;
			    break;
			}

		    }
		}
	    }

	    if (!isExistEpmDoc && !isExistBom) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "도면 또는 BOM 활동이 없습니다.");
	    } else if (isNotCompleteEpmDoc) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "완료되지 않은 도면 활동이 있습니다.");
	    } else if (isNotCompleteBom) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "완료되지 않은 BOM 활동이 있습니다.");
	    } else {
		rtnHash.put("flag", "TRUE");
		rtnHash.put("msg", "결재요청 가능합니다.");
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return rtnHash;
    }

    /**
     * 
     * @param moldEco
     * @return
     * @메소드명 : getCanReqApproveFlagForMoldEco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     * @deprecated
     * 
     */
    public Hashtable<String, String> getCanReqApproveFlagForMoldEco(KETMoldChangeOrder moldEco) {
	Hashtable<String, String> rtnHash = new Hashtable<String, String>();

	// 초도일 경우 제외한다.
	String changeReason = StringUtils.stripToEmpty(moldEco.getChangeReason());
	boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);
	// 초도일 경우
	if (isTheFirst) {
	    rtnHash.put("flag", "TRUE");
	    rtnHash.put("msg", "결재요청 가능합니다.");
	    return rtnHash;

	}

	QueryResult qr = null;
	QueryResult epmDocQr = null;
	QueryResult bomQr = null;
	QueryResult docQr = null;

	boolean isExistEpmDoc = false;
	boolean isExistBom = false;
	boolean isExistDoc = false;
	boolean isNotCompleteEpmDoc = false;
	boolean isNotCompleteBom = false;
	boolean isNotCompleteDoc = false;

	try {
	    KETMoldChangeActivity moldECA = null;
	    KETMoldECAEpmDocLink epmDocLink = null;
	    KETMoldECABomLink bomLink = null;
	    KETMoldECADocLink docLink = null;

	    qr = PersistenceHelper.manager.navigate(moldEco, "moldECA", KETMoldChangeActivityLink.class);
	    while (qr.hasMoreElements()) {
		moldECA = (KETMoldChangeActivity) qr.nextElement();

		if (moldECA.getActivityType().equals("1")) {
		    isExistEpmDoc = true;

		    epmDocQr = PersistenceHelper.manager.navigate(moldECA, "epmDoc", KETMoldECAEpmDocLink.class, false);
		    while (epmDocQr.hasMoreElements()) {
			epmDocLink = (KETMoldECAEpmDocLink) epmDocQr.nextElement();
			if (StringUtil.checkReplaceStr(epmDocLink.getAfterVersion(), "0").equals("0")) {
			    isNotCompleteEpmDoc = true;
			    break;
			}
		    }
		} else if (moldECA.getActivityType().equals("2")) {
		    isExistBom = true;

		    bomQr = PersistenceHelper.manager.navigate(moldECA, "bom", KETMoldECABomLink.class, false);
		    while (bomQr.hasMoreElements()) {
			bomLink = (KETMoldECABomLink) bomQr.nextElement();
			if (StringUtil.checkReplaceStr(bomLink.getAfterVersion(), "0").equals("0")) {
			    isNotCompleteBom = true;
			    break;
			}
		    }
		} else if (moldECA.getActivityType().equals("3")) {
		    isExistDoc = true;

		    docQr = PersistenceHelper.manager.navigate(moldECA, "doc", KETMoldECADocLink.class, false);
		    while (docQr.hasMoreElements()) {
			docLink = (KETMoldECADocLink) docQr.nextElement();
			if (StringUtil.checkReplaceStr(docLink.getAfterVersion(), "0").equals("0")) {
			    isNotCompleteDoc = true;
			    break;
			}
		    }
		}
	    }

	    if (!isExistEpmDoc && !isExistBom && !isExistDoc) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "변경활동이 없습니다.");
	    } else if (isNotCompleteEpmDoc || isNotCompleteBom || isNotCompleteDoc) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "완료되지 않은 변경활동이 있습니다.");
	    } else {
		rtnHash.put("flag", "TRUE");
		rtnHash.put("msg", "결재요청 가능합니다.");
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return rtnHash;
    }

    public String isManufacturingDrawing(KETProdChangeOrder prodEco) {
	String isManufacturingDrawing = "N";
	String chgReason = prodEco.getChangeReason();

	if (chgReason != null && chgReason.indexOf("REASON_4") > -1) {
	    isManufacturingDrawing = "Y";
	}

	return isManufacturingDrawing;
    }

    public String getSecureBudgetProdEcoInMoldEco(KETMoldChangeOrder moldEco) throws WTException {
	String rtnBudgetFlag = "N/A";

	KETProdChangeOrder prodEco = null;
	QueryResult ecoQr = null;

	ecoQr = PersistenceHelper.manager.navigate(moldEco, "prodECO", KETMoldProdEcoLink.class);

	while (ecoQr.hasMoreElements()) {
	    prodEco = (KETProdChangeOrder) ecoQr.nextElement();
	    // if( isSecureBudget( KETObjectUtil.getOidString(prodEco)) )
	    // {
	    rtnBudgetFlag = "Y";
	    // }
	    // else
	    // {
	    // rtnBudgetFlag = "N";
	    // }
	}

	return rtnBudgetFlag;
    }

    public String getRelatedECOLinkStr(String pboOid) {
	String ecoId = "";
	QueryResult qr = null;

	try {
	    KETProdChangeOrder prodEco = null;
	    KETMoldChangeOrder moldEco = null;

	    if (pboOid.startsWith("e3ps.ecm.entity.KETProdChangeRequest")) {
		qr = PersistenceHelper.manager.navigate((KETProdChangeRequest) KETObjectUtil.getObject(pboOid), "prodECO",
		        KETProdChangeLink.class);

		while (qr.hasMoreElements()) {
		    prodEco = (KETProdChangeOrder) qr.nextElement();
		    ecoId += prodEco.getEcoId();
		    ecoId += ",";
		}
	    } else if (pboOid.startsWith("e3ps.ecm.entity.KETMoldChangeRequest")) {
		qr = PersistenceHelper.manager.navigate((KETMoldChangeRequest) KETObjectUtil.getObject(pboOid), "moldECO",
		        KETMoldChangeLink.class);

		while (qr.hasMoreElements()) {
		    moldEco = (KETMoldChangeOrder) qr.nextElement();
		    ecoId += moldEco.getEcoId();
		    ecoId += ",";
		}
	    } else if (pboOid.startsWith("e3ps.ecm.entity.KETProdChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETProdChangeOrder) KETObjectUtil.getObject(pboOid), "moldECO",
		        KETMoldProdEcoLink.class);

		while (qr.hasMoreElements()) {
		    moldEco = (KETMoldChangeOrder) qr.nextElement();
		    ecoId += moldEco.getEcoId();
		    ecoId += ",";
		}
	    } else if (pboOid.startsWith("e3ps.ecm.entity.KETMoldChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETMoldChangeOrder) KETObjectUtil.getObject(pboOid), "prodECO",
		        KETMoldProdEcoLink.class);

		while (qr.hasMoreElements()) {
		    prodEco = (KETProdChangeOrder) qr.nextElement();
		    ecoId += prodEco.getEcoId();
		    ecoId += ",";
		}
	    }

	    if (ecoId.length() > 0) {
		ecoId = ecoId.substring(0, ecoId.lastIndexOf(","));
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return ecoId;
    }

    public String getRelatedECRLinkStr(String ecoOid) {
	String ecrId = "";
	QueryResult qr = null;

	try {
	    KETProdChangeRequest prodEcr = null;
	    KETMoldChangeRequest moldEcr = null;

	    if (ecoOid.startsWith("e3ps.ecm.entity.KETProdChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETProdChangeOrder) KETObjectUtil.getObject(ecoOid), "prodECR",
		        KETProdChangeLink.class);

		while (qr.hasMoreElements()) {
		    prodEcr = (KETProdChangeRequest) qr.nextElement();
		    ecrId += prodEcr.getEcrId();
		    ecrId += ",";
		}
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETMoldChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETMoldChangeOrder) KETObjectUtil.getObject(ecoOid), "moldECR",
		        KETMoldChangeLink.class);

		while (qr.hasMoreElements()) {
		    moldEcr = (KETMoldChangeRequest) qr.nextElement();
		    ecrId += moldEcr.getEcrId();
		    ecrId += ",";
		}
	    }

	    if (ecrId.length() > 0) {
		ecrId = ecrId.substring(0, ecrId.lastIndexOf(","));
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return ecrId;
    }

    public String getPartNoStr(String ecoOid) {
	String partNoStr = "";

	QueryResult qr = null;
	WTPart part = null;

	try {
	    if (ecoOid.startsWith("e3ps.ecm.entity.KETProdChangeRequest")) {
		qr = PersistenceHelper.manager.navigate((KETProdChangeRequest) KETObjectUtil.getObject(ecoOid), "part",
		        KETProdECRPartLink.class);
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETProdChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETProdChangeOrder) KETObjectUtil.getObject(ecoOid), "part",
		        KETProdECOPartLink.class);
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETMoldChangeRequest")) {
		qr = PersistenceHelper.manager.navigate((KETMoldChangeRequest) KETObjectUtil.getObject(ecoOid), "part",
		        KETMoldECRPartLink.class);
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETMoldChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETMoldChangeOrder) KETObjectUtil.getObject(ecoOid), "part",
		        KETMoldECOPartLink.class);
	    }

	    while (qr.hasMoreElements()) {
		part = (WTPart) qr.nextElement();
		partNoStr += part.getNumber();
		partNoStr += ",";
	    }

	    if (partNoStr.length() > 0) {
		partNoStr = partNoStr.substring(0, partNoStr.lastIndexOf(","));
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return partNoStr;
    }

    public String getPartNameStr(String ecoOid) {
	String partNameStr = "";

	QueryResult qr = null;
	WTPart part = null;

	try {
	    if (ecoOid.startsWith("e3ps.ecm.entity.KETProdChangeRequest")) {
		qr = PersistenceHelper.manager.navigate((KETProdChangeRequest) KETObjectUtil.getObject(ecoOid), "part",
		        KETProdECRPartLink.class);
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETProdChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETProdChangeOrder) KETObjectUtil.getObject(ecoOid), "part",
		        KETProdECOPartLink.class);
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETMoldChangeRequest")) {
		qr = PersistenceHelper.manager.navigate((KETMoldChangeRequest) KETObjectUtil.getObject(ecoOid), "part",
		        KETMoldECRPartLink.class);
	    } else if (ecoOid.startsWith("e3ps.ecm.entity.KETMoldChangeOrder")) {
		qr = PersistenceHelper.manager.navigate((KETMoldChangeOrder) KETObjectUtil.getObject(ecoOid), "part",
		        KETMoldECOPartLink.class);
	    }

	    while (qr.hasMoreElements()) {
		part = (WTPart) qr.nextElement();
		partNameStr += part.getName();
		partNameStr += ",";
	    }

	    if (partNameStr.length() > 0) {
		partNameStr = partNameStr.substring(0, partNameStr.lastIndexOf(","));
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return partNameStr;
    }

    public Hashtable<String, String> getCanCompleteFlagInProdECO(KETProdChangeOrder prodEco, String activityType) {
	Hashtable<String, String> rtnHash = new Hashtable<String, String>();

	QueryResult qr = null;
	QueryResult epmDocQr = null;
	QueryResult bomQr = null;
	QueryResult docQr = null;

	boolean isExistEpmDoc = false;
	boolean isExistBom = false;
	boolean isExistDoc = false;
	boolean isNotCompleteEpmDoc = false;
	boolean isNotCompleteBom = false;
	boolean isNotCompletDoc = false;
	KETBomEcoHeader bomHeader = null;

	Connection conn = null;

	try {
	    KETProdChangeActivity prodECA = null;
	    KETProdECAEpmDocLink epmDocLink = null;
	    KETProdECABomLink bomLink = null;
	    KETProdECADocLink docLink = null;

	    conn = PlmDBUtil.getConnection();
	    EcmComDao comDao = new EcmComDao(conn);

	    qr = PersistenceHelper.manager.navigate(prodEco, "prodECA", KETProdChangeActivityLink.class);
	    while (qr.hasMoreElements()) {
		prodECA = (KETProdChangeActivity) qr.nextElement();

		if (prodECA.getActivityType().equals("1") && activityType.equals(prodECA.getActivityType())
		        && prodECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
		    isExistEpmDoc = true;

		    epmDocQr = PersistenceHelper.manager.navigate(prodECA, "epmDoc", KETProdECAEpmDocLink.class, false);
		    while (epmDocQr.hasMoreElements()) {
			epmDocLink = (KETProdECAEpmDocLink) epmDocQr.nextElement();
			if (StringUtil.checkReplaceStr(epmDocLink.getAfterVersion(), "0").equals("0")) {
			    Kogger.debug(getClass(), "getCanCompleteFlagInProdECO(KETProdChangeOrder prodEco, String activityType)",
				    prodEco.getEcoId(), KETObjectUtil.getIda2a2(epmDocLink),
				    "완료되지않은 도면=====>" + KETObjectUtil.getIda2a2(epmDocLink));
			    isNotCompleteEpmDoc = true;
			    break;
			}
		    }
		} else if (prodECA.getActivityType().equals("2") && activityType.equals(prodECA.getActivityType())
		        && prodECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
		    int notCompleteCnt = 0;
		    isExistBom = true;

		    bomQr = PersistenceHelper.manager.navigate(prodECA, "bom", KETProdECABomLink.class, false);
		    while (bomQr.hasMoreElements()) {
			bomLink = (KETProdECABomLink) bomQr.nextElement();
			if (StringUtil.checkReplaceStr(bomLink.getAfterVersion(), "0").equals("0")) {
			    Kogger.debug(EcmSearchHelper.class,
				    "getCanCompleteFlagInProdECO(KETProdChangeOrder prodEco, String activityType)", prodEco.getEcoId(),
				    String.valueOf(CommonUtil.getOIDLongValue(bomLink)), "=============BOM이 개정이 안됬습니다...");
			    isNotCompleteBom = true;
			    break;
			} else {
			    bomHeader = bomLink.getBom();
			    notCompleteCnt = comDao.getNotCompleteBomChangeCnt(bomHeader.getEcoHeaderNumber(), bomHeader.getEcoItemCode(),
				    KETObjectUtil.getLoginUserId());
			    Kogger.debug(getClass(), "##### notCompleteCnt : " + notCompleteCnt);
			    Kogger.debug(EcmSearchHelper.class,
				    "getCanCompleteFlagInProdECO(KETProdChangeOrder prodEco, String activityType)",
				    bomHeader.getEcoItemCode(), String.valueOf(CommonUtil.getOIDLongValue(bomHeader)),
				    "##### notCompleteCnt : " + notCompleteCnt);
			    if (notCompleteCnt > 0) {
				Kogger.debug(EcmSearchHelper.class,
				        "getCanCompleteFlagInProdECO(KETProdChangeOrder prodEco, String activityType)",
				        bomHeader.getEcoItemCode(), String.valueOf(CommonUtil.getOIDLongValue(bomHeader)),
				        "=============BOM이 작업완료가 안됬습니다...");
				isNotCompleteBom = true;
				break;
			    }
			}
		    }
		} else if ((prodECA.getActivityType().equals("3") || prodECA.getActivityType().equals("4"))
		        && activityType.equals(prodECA.getActivityType())
		        && prodECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
		    isExistDoc = true;

		    docQr = PersistenceHelper.manager.navigate(prodECA, "doc", KETProdECADocLink.class, false);
		    while (docQr.hasMoreElements()) {
			docLink = (KETProdECADocLink) docQr.nextElement();
			if (StringUtil.checkReplaceStr(docLink.getAfterVersion(), "0").equals("0")) {
			    isNotCompletDoc = true;
			    break;
			}
		    }
		}
	    }

	    if (activityType.equals("1") && !isExistEpmDoc) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "도면활동이 없습니다.");
	    } else if (activityType.equals("2") && !isExistBom) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "BOM활동이 없습니다.");
	    } else if (activityType.equals("1") && isNotCompleteEpmDoc) {
		Kogger.debug(getClass(), "getCanCompleteFlagInProdECO(KETProdChangeOrder prodEco, String activityType)",
		        prodEco.getEcoId(), null, "####!!!여기로오나!!!!######");
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "완료되지 않은 도면활동이 있습니다.");
	    } else if (activityType.equals("2") && isNotCompleteBom) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "완료되지 않은 BOM활동이 있습니다.");
	    } else if ((activityType.equals("3") || activityType.equals("4")) && isNotCompletDoc) {
		rtnHash.put("flag", "FALSE");
		rtnHash.put("msg", "완료되지 않은 문서(후속)활동이 있습니다.");
	    } else {
		rtnHash.put("flag", "TRUE");
		rtnHash.put("msg", "작업완료 가능합니다.");
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}

	return rtnHash;
    }

    /**
     * 
     * @param moldEco
     * @param activityType
     * @return
     * @throws WTException
     * @메소드명 : getCanCompleteInMoldECO
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     * @deprecated
     * 
     */
    public Hashtable<String, String> getCanCompleteInMoldECO(KETMoldChangeOrder moldEco, String activityType) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	Hashtable<String, String> rtnHash = new Hashtable<String, String>();

	QueryResult qr = null;
	QueryResult epmDocQr = null;
	QueryResult bomQr = null;
	QueryResult docQr = null;

	boolean isExistEpmDoc = false;
	boolean isExistBom = false;
	boolean isExistDoc = false;
	boolean isNotCompleteEpmDoc = false;
	boolean isNotCompleteBom = false;
	boolean isNotCompleteDoc = false;
	boolean isNotApprovedDoc = false;
	boolean isNotExistedDoc = false;
	Connection conn = null;

	try {
	    KETMoldChangeActivity moldECA = null;
	    KETMoldECAEpmDocLink epmDocLink = null;
	    KETMoldECABomLink bomLink = null;
	    KETMoldECADocLink docLink = null;
	    KETBomEcoHeader bomHeader = null;

	    conn = PlmDBUtil.getConnection();
	    EcmComDao comDao = new EcmComDao(conn);

	    // KETProdChangeOrder 객체화
	    String status = moldEco.getLifeCycleState().toString();

	    // 초도인지 아닌지
	    String changeReason = StringUtils.stripToEmpty(moldEco.getChangeReason());
	    boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

	    WTPrincipal session = SessionHelper.manager.getPrincipal();

	    if (isTheFirst) {

		rtnHash.put("flag", "TRUE");
		rtnHash.put("msg", "결재요청 가능합니다.");

	    } else {

		qr = PersistenceHelper.manager.navigate(moldEco, "moldECA", KETMoldChangeActivityLink.class);

		int moldEcaListSize = (qr != null) ? qr.size() : 0;
		if (moldEcaListSize == 0) {

		    // <entry key="04990">작업완료 대상 활동이 없을 경우 진행하실 수 없습니다.</entry>
		    throw new WTException(messageService.getString("e3ps.message.ket_message", "04990"));

		} else {

		    while (qr.hasMoreElements()) {
			moldECA = (KETMoldChangeActivity) qr.nextElement();

			if (!isLastActivity(moldECA)) {
			    if (moldECA.getActivityType().equals("1") && activityType.equals(moldECA.getActivityType())
				    && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
				isExistEpmDoc = true;

				epmDocQr = PersistenceHelper.manager.navigate(moldECA, "epmDoc", KETMoldECAEpmDocLink.class, false);
				while (epmDocQr.hasMoreElements()) {
				    epmDocLink = (KETMoldECAEpmDocLink) epmDocQr.nextElement();
				    if (StringUtil.checkReplaceStr(epmDocLink.getAfterVersion(), "0").equals("0")) {
					isNotCompleteEpmDoc = true;
					break;
				    }
				}
			    } else if (moldECA.getActivityType().equals("2") && activityType.equals(moldECA.getActivityType())
				    && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
				int notCompleteCnt = 0;
				isExistBom = true;

				bomQr = PersistenceHelper.manager.navigate(moldECA, "bom", KETMoldECABomLink.class, false);
				while (bomQr.hasMoreElements()) {
				    bomLink = (KETMoldECABomLink) bomQr.nextElement();
				    if (StringUtil.checkReplaceStr(bomLink.getAfterVersion(), "0").equals("0")) {
					isNotCompleteBom = true;
					break;
				    }

				}

			    } else if (moldECA.getActivityType().equals("3") && activityType.equals(moldECA.getActivityType())
				    && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
				isExistDoc = true;

				docQr = PersistenceHelper.manager.navigate(moldECA, "doc", KETMoldECADocLink.class, false);
				while (docQr.hasMoreElements()) {
				    docLink = (KETMoldECADocLink) docQr.nextElement();
				    if (StringUtil.checkReplaceStr(docLink.getAfterVersion(), "0").equals("0")) {
					isNotCompleteDoc = true;
					break;
				    }

				    // 결재 승인 유무
				    KETProjectDocument ketProjectDocument = docLink.getDoc();
				    String documentOid = EcmUtil.getDocumentOid(ketProjectDocument.getNumber(), docLink.getAfterVersion());
				    if (documentOid != null && !documentOid.equals("")) {
					KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument) CommonUtil
					        .getObject(documentOid);
					// KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument)
					// ObjectUtil.getLatestVersion(ketProjectDocument);

					State ketProjectDocumentLatestVersionState = ketProjectDocumentLatestVersion.getState().getState();
					// if (ketProjectDocumentLatestVersionState.equals(State.toState("INWORK")) ||
					// ketProjectDocumentLatestVersionState.equals(State.toState("UNDERREVIEW"))) {
					if (!ketProjectDocumentLatestVersionState.equals(State.toState("APPROVED"))) {
					    // <entry key="05000">결재승인되지 않은 문서가 있습니다.</entry>
					    isNotApprovedDoc = true;
					    break;
					}
				    } else {
					isNotExistedDoc = true;
					break;
				    }

				}
			    }
			} else {
			    if (moldECA.getActivityType().equals("1") && activityType.equals(moldECA.getActivityType())
				    && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
				isExistEpmDoc = true;

				epmDocQr = PersistenceHelper.manager.navigate(moldECA, "epmDoc", KETMoldECAEpmDocLink.class, false);

				if (epmDocQr.size() > 0) {
				    while (epmDocQr.hasMoreElements()) {
					epmDocLink = (KETMoldECAEpmDocLink) epmDocQr.nextElement();
					if (StringUtil.checkReplaceStr(epmDocLink.getAfterVersion(), "0").equals("0")) {
					    isNotCompleteEpmDoc = true;
					    break;
					}
				    }
				} else {
				    isNotCompleteEpmDoc = true;
				}

			    } else if (moldECA.getActivityType().equals("2") && activityType.equals(moldECA.getActivityType())
				    && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
				int notCompleteCnt = 0;
				isExistBom = true;

				bomQr = PersistenceHelper.manager.navigate(moldECA, "bom", KETMoldECABomLink.class, false);

				if (bomQr.size() > 0) {
				    while (bomQr.hasMoreElements()) {
					bomLink = (KETMoldECABomLink) bomQr.nextElement();
					if (StringUtil.checkReplaceStr(bomLink.getAfterVersion(), "0").equals("0")) {
					    isNotCompleteBom = true;
					    break;
					}

				    }
				} else {
				    isNotCompleteBom = true;
				}

			    } else if (moldECA.getActivityType().equals("3") && activityType.equals(moldECA.getActivityType())
				    && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
				isExistDoc = true;

				docQr = PersistenceHelper.manager.navigate(moldECA, "doc", KETMoldECADocLink.class, false);
				while (docQr.hasMoreElements()) {
				    docLink = (KETMoldECADocLink) docQr.nextElement();
				    if (StringUtil.checkReplaceStr(docLink.getAfterVersion(), "0").equals("0")) {
					isNotCompleteDoc = true;
					break;
				    }

				    // 결재 승인 유무
				    KETProjectDocument ketProjectDocument = docLink.getDoc();
				    String documentOid = EcmUtil.getDocumentOid(ketProjectDocument.getNumber(), docLink.getAfterVersion());
				    if (documentOid != null && !documentOid.equals("")) {
					KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument) CommonUtil
					        .getObject(documentOid);
					// KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument)
					// ObjectUtil.getLatestVersion(ketProjectDocument);

					State ketProjectDocumentLatestVersionState = ketProjectDocumentLatestVersion.getState().getState();
					// if (ketProjectDocumentLatestVersionState.equals(State.toState("INWORK")) ||
					// ketProjectDocumentLatestVersionState.equals(State.toState("UNDERREVIEW"))) {
					if (!ketProjectDocumentLatestVersionState.equals(State.toState("APPROVED"))) {
					    // <entry key="05000">결재승인되지 않은 문서가 있습니다.</entry>
					    isNotApprovedDoc = true;
					    break;
					}
				    } else {
					isNotExistedDoc = true;
					break;
				    }

				}
			    }
			}
		    }

		    if (activityType.equals("1") && isNotCompleteEpmDoc) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "개정되지 않은 도면활동이 있습니다.");
		    } else if (activityType.equals("2") && isNotCompleteBom) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "개정되지 않은 BOM활동이 있습니다.");
		    } else if (activityType.equals("3") && isNotCompleteDoc) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "개정되지 않은 표준품활동이 있습니다.");
		    } else if (activityType.equals("3") && isNotApprovedDoc) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "결재승인되지 않은 표준품활동이 있습니다.");

		    } else if (activityType.equals("3") && isNotExistedDoc) {
			rtnHash.put("flag", "FALSE");

			// <entry key="04001">개정한 문서가 삭제되었습니다.\n관리자에게 문의하여 주십시오.</entry>
			// throw new WTException(messageService.getString("e3ps.message.ket_message", "04001"));
			rtnHash.put("msg", "개정한 표준품활동이 삭제되었습니다.\n관리자에게 문의하여 주십시오.");

		    } else {
			rtnHash.put("flag", "TRUE");
			rtnHash.put("msg", "결재요청 가능합니다.");
		    }

		}

	    }

	} catch (Exception e) {
	    throw new WTException(e);

	} finally {
	    PlmDBUtil.close(conn);
	}

	return rtnHash;
    }

    public Hashtable<String, String> completeInMoldECO(KETMoldChangeOrder moldEco, String activityType) throws WTException {
	KETParamMapUtil paramMap = new KETParamMapUtil();
	paramMap.put("activityTypes", new String[] { activityType });

	return this.completeInMoldECO(moldEco, paramMap);
    }

    public Hashtable<String, String> completeInMoldECO(KETMoldChangeOrder moldEco, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	Hashtable<String, String> rtnHash = new Hashtable<String, String>();

	QueryResult qr = null;
	QueryResult epmDocQr = null;
	QueryResult bomQr = null;
	QueryResult docQr = null;

	boolean isExistEpmDoc = false;
	boolean isExistBom = false;
	boolean isExistDoc = false;
	boolean isNotCompleteEpmDoc = false;
	boolean isNotCompleteBom = false;
	boolean isNotCompleteDoc = false;
	boolean isNotApprovedDoc = false;
	boolean isNotExistedDoc = false;
	boolean isNotSavedBomEditor = false;

	Connection conn = null;
	try {

	    // 1. 유효성 체크를 하면서 로그인한 사용자가 담당자로 있는 ECA를 담는다.
	    ArrayList<KETMoldChangeActivity> moldEcaList = null;

	    conn = PlmDBUtil.getConnection();
	    EcmComDao comDao = new EcmComDao(conn);

	    // KETProdChangeOrder 객체화
	    String status = moldEco.getLifeCycleState().toString();

	    // 초도인지 아닌지
	    String changeReason = StringUtils.stripToEmpty(moldEco.getChangeReason());
	    boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

	    WTPrincipal session = SessionHelper.manager.getPrincipal();

	    // 2. 유효성 검증
	    String isActivityReg = (String) paramMap.get("isActivityReg");

	    String[] activityTypes = (String[]) paramMap.get("activityTypes");
	    int activityTypesLength = (activityTypes != null) ? activityTypes.length : 0;
	    for (int i = 0; i < activityTypesLength; i++) {
		String activityType = activityTypes[i];

		KETMoldChangeActivity moldECA = null;
		KETMoldECAEpmDocLink epmDocLink = null;
		KETMoldECABomLink bomLink = null;
		KETMoldECADocLink docLink = null;
		KETBomEcoHeader bomHeader = null;

		// 2. 자신의 ECA의 유효성 체크를 먼저 한다.
		qr = PersistenceHelper.manager.navigate(moldEco, "moldECA", KETMoldChangeActivityLink.class);
		while (qr.hasMoreElements()) {
		    moldECA = (KETMoldChangeActivity) qr.nextElement();

		    if (!isLastActivity(moldECA)) {
			if (moldECA.getActivityType().equals("1") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
			    isExistEpmDoc = true;

			    // 초도일 경우 제외한다.
			    if (isTheFirst) {
				// Do nothing..!!

			    } else {

				epmDocQr = PersistenceHelper.manager.navigate(moldECA, "epmDoc", KETMoldECAEpmDocLink.class, false);
				while (epmDocQr.hasMoreElements()) {
				    epmDocLink = (KETMoldECAEpmDocLink) epmDocQr.nextElement();

				    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
				    String epmDocRevision = "";
				    EPMDocument epmDoc = epmDocLink.getEpmDoc();
				    epmDocRevision = (epmDoc != null) ? epmDoc.getVersionIdentifier().getValue() : "";
				    if (epmDoc.getLifeCycleState().equals(State.toState("INWORK"))
					    && (epmDocRevision.equals("") || epmDocRevision.equals("0"))) {
					// 통과
				    } else {
					if (StringUtil.checkReplaceStr(epmDocLink.getAfterVersion(), "0").equals("0")) {
					    isNotCompleteEpmDoc = true;
					    break;
					}

				    }

				}

			    }

			    if (!isNotCompleteEpmDoc) {
				if (moldEcaList == null)
				    moldEcaList = new ArrayList<KETMoldChangeActivity>();
				if (!moldEcaList.contains(moldECA))
				    moldEcaList.add(moldECA);
			    }

			} else if (moldECA.getActivityType().equals("2") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
			    int notCompleteCnt = 0;
			    isExistBom = true;

			    // 초도일 경우 제외한다.
			    if (isTheFirst) {
				// Do nothing..!!

			    } else {

				bomQr = PersistenceHelper.manager.navigate(moldECA, "bom", KETMoldECABomLink.class, false);
				while (bomQr.hasMoreElements()) {
				    bomLink = (KETMoldECABomLink) bomQr.nextElement();

				    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
				    String partRevision = "";
				    String beforePartOid = bomLink.getBeforePartOid();
				    WTPart beforeWTPart = (WTPart) CommonUtil.getObject(beforePartOid);
				    partRevision = (beforeWTPart != null) ? beforeWTPart.getVersionIdentifier().getValue() : "";
				    if (beforeWTPart.getLifeCycleState().equals(State.toState("INWORK"))
					    && (partRevision.equals("") || partRevision.equals("0"))) {
					// 통과
				    } else {
					if (StringUtil.checkReplaceStr(bomLink.getAfterVersion(), "0").equals("0")) {
					    isNotCompleteBom = true;
					    break;

					} else {

					    // BOM Editor에서 저장유무 Validation 처리 ----> 금형 BOM은 불필요한로직이라 주석처리함
					    // String changeYn = StringUtils.stripToEmpty(bomLink.getChangeYn());
					    // if (changeYn == null || changeYn.equals("") || !changeYn.equalsIgnoreCase("Y")) {
					    // isNotSavedBomEditor = true;
					    // break;
					    // }

					}

				    }

				}

			    }

			    if (!isNotCompleteBom) {
				if (moldEcaList == null)
				    moldEcaList = new ArrayList<KETMoldChangeActivity>();
				if (!moldEcaList.contains(moldECA))
				    moldEcaList.add(moldECA);
			    }

			} else if (moldECA.getActivityType().equals("3") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
			    isExistDoc = true;

			    docQr = PersistenceHelper.manager.navigate(moldECA, "doc", KETMoldECADocLink.class, false);
			    while (docQr.hasMoreElements()) {
				docLink = (KETMoldECADocLink) docQr.nextElement();
				if (StringUtil.checkReplaceStr(docLink.getAfterVersion(), "0").equals("0")) {
				    isNotCompleteDoc = true;
				    break;
				}

				// 결재 승인 유무
				KETProjectDocument ketProjectDocument = docLink.getDoc();
				String documentOid = EcmUtil.getDocumentOid(ketProjectDocument.getNumber(), docLink.getAfterVersion());
				if (documentOid != null && !documentOid.equals("")) {
				    KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument) CommonUtil
					    .getObject(documentOid);
				    // KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument)
				    // ObjectUtil.getLatestVersion(ketProjectDocument);

				    State ketProjectDocumentLatestVersionState = ketProjectDocumentLatestVersion.getState().getState();
				    // if (ketProjectDocumentLatestVersionState.equals(State.toState("INWORK")) ||
				    // ketProjectDocumentLatestVersionState.equals(State.toState("UNDERREVIEW"))) {
				    if (!ketProjectDocumentLatestVersionState.equals(State.toState("APPROVED"))) {
					// <entry key="05000">결재승인되지 않은 문서가 있습니다.</entry>
					isNotApprovedDoc = true;
					break;
				    }
				} else {
				    isNotExistedDoc = true;
				    break;
				}

			    }

			    if (!isNotCompleteDoc && !isNotApprovedDoc && !isNotExistedDoc) {
				if (moldEcaList == null)
				    moldEcaList = new ArrayList<KETMoldChangeActivity>();
				if (!moldEcaList.contains(moldECA))
				    moldEcaList.add(moldECA);
			    }

			} else if (moldECA.getActivityType().equals("4") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {

			    if (moldEcaList == null)
				moldEcaList = new ArrayList<KETMoldChangeActivity>();
			    if (!moldEcaList.contains(moldECA))
				moldEcaList.add(moldECA);

			}

		    } else {
			if (moldECA.getActivityType().equals("1") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
			    isExistEpmDoc = true;

			    // 초도일 경우 제외한다.
			    if (isTheFirst) {
				// Do nothing..!!

			    } else {

				epmDocQr = PersistenceHelper.manager.navigate(moldECA, "epmDoc", KETMoldECAEpmDocLink.class, false);
				while (epmDocQr.hasMoreElements()) {
				    epmDocLink = (KETMoldECAEpmDocLink) epmDocQr.nextElement();

				    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
				    String epmDocRevision = "";
				    EPMDocument epmDoc = epmDocLink.getEpmDoc();
				    epmDocRevision = (epmDoc != null) ? epmDoc.getVersionIdentifier().getValue() : "";
				    if (epmDoc.getLifeCycleState().equals(State.toState("INWORK"))
					    && (epmDocRevision.equals("") || epmDocRevision.equals("0"))) {
					// 통과
				    } else {
					if (StringUtil.checkReplaceStr(epmDocLink.getAfterVersion(), "0").equals("0")) {
					    isNotCompleteEpmDoc = true;
					    break;
					}
				    }

				}

			    }

			    if (!isNotCompleteEpmDoc) {
				if (moldEcaList == null)
				    moldEcaList = new ArrayList<KETMoldChangeActivity>();
				if (!moldEcaList.contains(moldECA))
				    moldEcaList.add(moldECA);
			    }

			} else if (moldECA.getActivityType().equals("2") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
			    int notCompleteCnt = 0;
			    isExistBom = true;

			    // 초도일 경우 제외한다.
			    if (isTheFirst) {
				// Do nothing..!!

			    } else {

				bomQr = PersistenceHelper.manager.navigate(moldECA, "bom", KETMoldECABomLink.class, false);
				while (bomQr.hasMoreElements()) {
				    bomLink = (KETMoldECABomLink) bomQr.nextElement();

				    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
				    String partRevision = "";
				    String beforePartOid = bomLink.getBeforePartOid();
				    WTPart beforeWTPart = (WTPart) CommonUtil.getObject(beforePartOid);
				    partRevision = (beforeWTPart != null) ? beforeWTPart.getVersionIdentifier().getValue() : "";
				    if (beforeWTPart.getLifeCycleState().equals(State.toState("INWORK"))
					    && (partRevision.equals("") || partRevision.equals("0"))) {
					// 통과
				    } else {
					if (StringUtil.checkReplaceStr(bomLink.getAfterVersion(), "0").equals("0")) {
					    isNotCompleteBom = true;
					    break;

					} else {

					    // BOM Editor에서 저장유무 Validation 처리
					    String changeYn = StringUtils.stripToEmpty(bomLink.getChangeYn());
					    if (changeYn == null || changeYn.equals("") || !changeYn.equalsIgnoreCase("Y")) {
						/*
			                         * isNotSavedBomEditor = true; break;
			                         */
					    }

					}

				    }

				}

			    }

			    if (!isNotCompleteBom) {
				if (moldEcaList == null)
				    moldEcaList = new ArrayList<KETMoldChangeActivity>();
				if (!moldEcaList.contains(moldECA))
				    moldEcaList.add(moldECA);
			    }

			} else if (moldECA.getActivityType().equals("3") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {
			    isExistDoc = true;

			    docQr = PersistenceHelper.manager.navigate(moldECA, "doc", KETMoldECADocLink.class, false);
			    while (docQr.hasMoreElements()) {
				docLink = (KETMoldECADocLink) docQr.nextElement();
				if (StringUtil.checkReplaceStr(docLink.getAfterVersion(), "0").equals("0")) {
				    isNotCompleteDoc = true;
				    break;
				}

				// 결재 승인 유무
				KETProjectDocument ketProjectDocument = docLink.getDoc();
				String documentOid = EcmUtil.getDocumentOid(ketProjectDocument.getNumber(), docLink.getAfterVersion());
				if (documentOid != null && !documentOid.equals("")) {
				    KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument) CommonUtil
					    .getObject(documentOid);
				    // KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument)
				    // ObjectUtil.getLatestVersion(ketProjectDocument);

				    State ketProjectDocumentLatestVersionState = ketProjectDocumentLatestVersion.getState().getState();
				    // if (ketProjectDocumentLatestVersionState.equals(State.toState("INWORK")) ||
				    // ketProjectDocumentLatestVersionState.equals(State.toState("UNDERREVIEW"))) {
				    if (!ketProjectDocumentLatestVersionState.equals(State.toState("APPROVED"))) {
					// <entry key="05000">결재승인되지 않은 문서가 있습니다.</entry>
					isNotApprovedDoc = true;
					break;
				    }
				} else {
				    isNotExistedDoc = true;
				    break;
				}

			    }

			    if (!isNotCompleteDoc && !isNotApprovedDoc && !isNotExistedDoc) {
				if (moldEcaList == null)
				    moldEcaList = new ArrayList<KETMoldChangeActivity>();
				if (!moldEcaList.contains(moldECA))
				    moldEcaList.add(moldECA);
			    }

			} else if (moldECA.getActivityType().equals("4") && activityType.equals(moldECA.getActivityType())
			        && moldECA.getWorkerId().equals(KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()))) {

			    if (moldEcaList == null)
				moldEcaList = new ArrayList<KETMoldChangeActivity>();
			    if (!moldEcaList.contains(moldECA))
				moldEcaList.add(moldECA);

			}

		    }

		    // 3. 자신의 ECA의 Validation를 체크먼저 한다.
		    if (activityType.equals("1") && isNotCompleteEpmDoc) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "개정되지 않은 도면활동이 있습니다.");
		    } else if (activityType.equals("2") && isNotCompleteBom) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "개정되지 않은 BOM활동이 있습니다.");
		    }
		    // else if (activityType.equals("2") && isNotSavedBomEditor) {
		    // rtnHash.put("flag", "FALSE");
		    // rtnHash.put("msg", "BOM Editor에서 저장되지 않은 BOM활동이 있습니다.");
		    // }
		    else if (activityType.equals("3") && isNotCompleteDoc) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "개정되지 않은 표준품활동이 있습니다.");
		    } else if (activityType.equals("3") && isNotApprovedDoc) {
			rtnHash.put("flag", "FALSE");
			rtnHash.put("msg", "결재승인되지 않은 표준품활동이 있습니다.");
		    } else if (activityType.equals("3") && isNotExistedDoc) {
			rtnHash.put("flag", "FALSE");

			// <entry key="04001">개정한 문서가 삭제되었습니다.\n관리자에게 문의하여 주십시오.</entry>
			// throw new WTException(messageService.getString("e3ps.message.ket_message", "04001"));
			rtnHash.put("msg", "개정한 표준품활동이 삭제되었습니다.\n관리자에게 문의하여 주십시오.");

		    }

		}

	    }

	    String returnFlag = rtnHash.get("flag");
	    if (returnFlag == null || !returnFlag.equalsIgnoreCase("FALSE")) {

		// 3. 마무리
		int moldEcaListSize = (moldEcaList != null) ? moldEcaList.size() : 0;
		if (moldEcaListSize == 0) {
		    rtnHash.put("flag", "FALSE");
		    rtnHash.put("msg", "작업완료 대상 활동이 없을 경우 진행하실 수 없습니다.");
		} else {
		    // 3. 가져온 KETProdChangeActivity 의 워크플로우를 완료시킨다.
		    for (int j = 0; j < moldEcaListSize; j++) {
			KETMoldChangeActivity eca = (KETMoldChangeActivity) moldEcaList.get(j);
			if (PersistenceHelper.isPersistent(eca)) {

			    bomQr = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
			    while (bomQr.hasMoreElements()) {
				KETMoldECABomLink bomLink = (KETMoldECABomLink) bomQr.nextElement();

				// 초도일 경우 개정된 리비전이 없을 경우 이전(초기) 리비전을 set 한다.
				/*
		                 * if (isTheFirst) { String afterVersion = StringUtils.stripToEmpty(bomLink.getAfterVersion()); String
		                 * preVersion = StringUtils.stripToEmpty(bomLink.getPreVersion()); if (afterVersion.equals("") &&
		                 * !preVersion.equals("")) { bomLink.setAfterVersion(preVersion);
		                 * 
		                 * PersistenceHelper.manager.save(bomLink); bomLink = (KETMoldECABomLink)
		                 * PersistenceHelper.manager.refresh(bomLink);
		                 * 
		                 * } }
		                 */

				KETBomEcoHeader bomHeader = bomLink.getBom();
				/*
		                 * notCompleteCnt = comDao.getNotCompleteBomChangeCnt(bomHeader.getEcoHeaderNumber(),
		                 * bomHeader.getEcoItemCode(), KETObjectUtil.getLoginUserId()); Kogger.debug(getClass(),
		                 * "===>Not Complete Count : " + notCompleteCnt); if (notCompleteCnt > 0) { isNotCompleteBom = true; break;
		                 * }
		                 */

				// 1. BOMHeader 의 Attribute3에 'Y'를 넣어준다.
				// Todo에서 '작업완료'했음을 나타내게 한다.
				bomHeader.setAttribute3("Y");
				bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(bomHeader);

				// 2. AS-IS를 위하여 아래 코드를 실행한다.
				/*
		                 * count 가 1이상일 경우 작업완료 할 수 없도록 하고 있었다.
		                 * 
		                 * SELECT COUNT(*) FROM ketbomecocoworker w WHERE w.ecoheadernumber = 'ECO-1409-054' AND w.ecoitemcode =
		                 * '610008R' AND w.endworkingflag <> '4'
		                 */
				String ecoHeaderNo = bomHeader.getEcoHeaderNumber();
				String ecoItemCode = bomHeader.getEcoItemCode();
				String coworkerId = session.getName();
				String flag = "endWorking";

				BOMSearchUtilDao bomSearchUtilDao = new BOMSearchUtilDao();
				bomSearchUtilDao.updateEndWorkingFlag(ecoHeaderNo, ecoItemCode, coworkerId, flag);

			    }

			    // Todo의 '작업완료'에서 왔을 경우
			    if (isActivityReg == null || isActivityReg.equals("")) {

				// 가져온 KETProdChangeActivity 의 워크플로우를 완료시킨다.
				WorkflowSearchHelper.manager.taskComplete(eca);

			    } else {

				Timestamp completeDate = DateUtil.getCurrentTimestamp();
				eca.setCompleteDate(completeDate);
				// Added by MJOH, 2011-03-30
				// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
				eca.setActivityStatus("WORKCOMPLETED");
				PersistenceHelper.manager.save(eca);
				eca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(eca);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("WORKCOMPLETED"));

			    }

			    // HEENEETODO : 여러 후처리들이 남아있다.
			    /*
		             * 모든 ECN의 후속작업 작업완료 시, ECN 자동 완료 및 ECN 완료 여부 자동 메일 공지 메일 수신자 : 모든 ECN 담당자
		             */

			}

		    }

		    rtnHash.put("flag", "TRUE");
		    rtnHash.put("msg", "결재요청 가능합니다.");

		}

	    }

	} catch (Exception e) {
	    throw new WTException(e);

	} finally {
	    PlmDBUtil.close(conn);
	}

	return rtnHash;
    }

    public String getRelatedPartNo(String dieNo) {
	String partNo = "";

	try {
	    QuerySpec spec = new QuerySpec(MoldItemInfo.class);

	    ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
	    SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
	    dieNo = dieNo.toUpperCase();

	    ColumnExpression ce = ConstantExpression.newExpression(dieNo);
	    spec.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(spec);

	    MoldItemInfo miInfo = null;

	    while (rs.hasMoreElements()) {
		miInfo = (MoldItemInfo) rs.nextElement();

		partNo = miInfo.getPartNo();
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return partNo;
    }

    public String getRelatedPartName(String partNo) {
	String partName = "";
	WTPart part = null;
	try {
	    part = KETPartHelper.service.getPart(partNo);

	    if (part != null) {
		partName = part.getName();
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return partName;
    }

    public String getPartnersName(String partnerCode) {
	String partnerName = "";
	Connection conn = null;

	try {
	    conn = PlmDBUtil.getConnection();

	    EcmComDao comDao = new EcmComDao(conn);
	    partnerName = comDao.getPartnerName(partnerCode);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}

	return partnerName;
    }

    public int getEcoListByUserCount() throws Exception {
	QuerySpec spec = new QuerySpec();
	int eco_idx = spec.appendClassList(KETProdChangeOrder.class, false);

	spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, eco_idx, false);

	long oidValue_t = CommonUtil.getOIDLongValue((WTUser) SessionHelper.manager.getPrincipal());

	spec.appendWhere(new SearchCondition(KETProdChangeOrder.class, "creator.key.id", SearchCondition.EQUAL, oidValue_t),
	        new int[] { eco_idx });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(new ClassAttribute(KETProdChangeOrder.class, "state.state"), SearchCondition.NOT_IN,
	        new ArrayExpression(new String[] { "APPROVED", "REJECTED" })), new int[] { eco_idx });

	spec.setAdvancedQueryEnabled(true);

	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);

	QuerySpec mold_spec = new QuerySpec();

	int eco_mold_idx = mold_spec.appendClassList(KETMoldChangeOrder.class, false);

	mold_spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, eco_mold_idx, false);

	mold_spec.appendWhere(new SearchCondition(KETMoldChangeOrder.class, "creator.key.id", SearchCondition.EQUAL, oidValue_t),
	        new int[] { eco_mold_idx });
	mold_spec.appendAnd();
	mold_spec.appendWhere(new SearchCondition(new ClassAttribute(KETMoldChangeOrder.class, "state.state"), SearchCondition.NOT_IN,
	        new ArrayExpression(new String[] { "APPROVED", "REJECTED" })), new int[] { eco_mold_idx });
	mold_spec.setAdvancedQueryEnabled(true);

	if (!mold_spec.isAdvancedQueryEnabled())
	    mold_spec.setAdvancedQueryEnabled(true);

	QuerySpec prod_ecr_spec = new QuerySpec();

	int prod_ecr_idx = prod_ecr_spec.appendClassList(KETProdChangeRequest.class, false);

	prod_ecr_spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, prod_ecr_idx, false);

	prod_ecr_spec.appendWhere(new SearchCondition(KETProdChangeRequest.class, "creator.key.id", SearchCondition.EQUAL, oidValue_t),
	        new int[] { prod_ecr_idx });
	prod_ecr_spec.appendAnd();
	prod_ecr_spec.appendWhere(new SearchCondition(new ClassAttribute(KETProdChangeRequest.class, "state.state"),
	        SearchCondition.NOT_IN, new ArrayExpression(new String[] { "APPROVED", "REJECTED" })), new int[] { prod_ecr_idx });
	prod_ecr_spec.setAdvancedQueryEnabled(true);

	if (!prod_ecr_spec.isAdvancedQueryEnabled())
	    prod_ecr_spec.setAdvancedQueryEnabled(true);

	QuerySpec mold_ecr_spec = new QuerySpec();

	int mold_ecr_idx = mold_ecr_spec.appendClassList(KETMoldChangeRequest.class, false);

	mold_ecr_spec.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, mold_ecr_idx, false);

	mold_ecr_spec.appendWhere(new SearchCondition(KETMoldChangeRequest.class, "creator.key.id", SearchCondition.EQUAL, oidValue_t),
	        new int[] { mold_ecr_idx });
	mold_ecr_spec.appendAnd();
	mold_ecr_spec.appendWhere(new SearchCondition(new ClassAttribute(KETMoldChangeRequest.class, "state.state"),
	        SearchCondition.NOT_IN, new ArrayExpression(new String[] { "APPROVED", "REJECTED" })), new int[] { mold_ecr_idx });
	mold_ecr_spec.setAdvancedQueryEnabled(true);

	if (!mold_ecr_spec.isAdvancedQueryEnabled())
	    mold_ecr_spec.setAdvancedQueryEnabled(true);

	CompoundQuerySpec compound_spec = new CompoundQuerySpec();

	compound_spec.setSetOperator(SetOperator.UNION);
	compound_spec.addComponent(spec);
	compound_spec.addComponent(mold_spec);
	compound_spec.addComponent(prod_ecr_spec);
	compound_spec.addComponent(mold_ecr_spec);

	// List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	QueryResult compound_result = PersistenceServerHelper.manager.query(compound_spec);

	return compound_result.size();
    }

}
