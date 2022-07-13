package e3ps.edm.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.EPMDocumentType;
import wt.epm.structure.EPMReferenceLink;
import wt.epm.structure.EPMStructureHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.iba.value.litevalue.StringValueDefaultView;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.vc.config.ConfigHelper;
import wt.vc.config.ConfigSpec;
import wt.vc.config.LatestConfigSpec;
import e3ps.common.code.NumberCode;
import e3ps.edm.DrawingToModelReferenceLink;
import e3ps.edm.EDMUserData;
import e3ps.edm.EPMDocProjectLink;
import e3ps.edm.EPMDocTypeCodeLink;
import e3ps.edm.EPMLink;
import e3ps.edm.ModelReferenceLink;
import e3ps.edm.PartToEPMLink;
import e3ps.edm.dao.EPMPartMatcher;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.VersionHelper;
import e3ps.project.ProjectMaster;
import ext.ket.edm.cad2bom.service.internal.EPMNumberProdType;
import ext.ket.edm.cad2bom.service.internal.PartNumberProdType;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class EDMHelper {
    public static final int REQUIRED_STANDARD = 1;// 대표부품
    public static final int REQUIRED_RELATED = 0;// 관련부품
    public static final int REQUIRED_REFERENCE_MODEL = 2;// 참조모델(도면과 모델만 연관하는 경우)

    public static final String REFERENCE_TYPE_DEFAULT = "REFERENCE_DRAWING";// 참조
    public static final String REFERENCE_TYPE_RELATED = "RELATED_DRAWING";// 관련 제품/금형도면
    public static final String REFERENCE_TYPE_APP = "APP_DRAWING";// 관련 승인도면
    public static final String REFERENCE_TYPE_CU = "CU_DRAWING";// 관련 고객제출도면
    public static final String REFERENCE_TYPE_REF = "REF_DRAWING";// 제품참고도면
    public static final String REFERENCE_TYPE_MODEL = "RELATED_MODEL";// 관련 Model(3D)

    // IBA로 관리하는 속성( Value = IBA NAME)
    public static final String IBA_CAD_MANAGE_TYPE = "CADManageType";// 제품도면/금형도면 구분
    public static final String IBA_DEV_STAGE = "DevStage";// 개발단계
    public static final String IBA_CAD_CATEGORY = "CADCategory";// 도면유형
    public static final String IBA_CAD_APP_TYPE = "CADAppType";// CAD유형
    public static final String IBA_PART_REF_TYPE = "PartReferenceType";// 부품참조유형
    public static final String IBA_MANUFACTURING_VERSION = "ManufacturingVersion";// 양산버전
    public static final String IBA_DUMMY_FILE = "IsDummyFile";// Dummy File 여부(Y/N)
    /* 2013.02.07 shkim 보안등급 속성 추가 */
    public static final String IBA_SECURITY = "Security"; // 보안등급(대외비, 대내비)

    public static final String IBA_DUMMY_FILE_VALUE_YES = "Y";
    public static final String IBA_DUMMY_FILE_VALUE_NO = "N";

    static {
	try {
	    WTProperties localWTProperties = WTProperties.getLocalProperties();
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}
    }

    public static boolean isRunningData(EPMDocument doc) throws WTException {
	return EDMQueryHelper.isRunningData(doc);
    }

    /**
     * 도면의 참조 모델인지 여부
     * 
     * @param doc
     * @return
     */
    public static boolean isRefedModel(EPMDocument doc) {
	return isRefedModel((EPMDocumentMaster) doc.getMaster());
    }

    /**
     * 도면의 참조 모델여부
     * 
     * @param documentMaster
     * @return
     */
    public static boolean isRefedModel(EPMDocumentMaster documentMaster) {
	try {
	    QuerySpec qs = new QuerySpec();

	    int idx = qs.appendClassList(ModelReferenceLink.class, true);

	    qs.appendWhere(new SearchCondition(ModelReferenceLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
		    .getObjectIdentifier(documentMaster).getId()), new int[] { idx });

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    if (qr.hasMoreElements()) {
		return true;
	    }

	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return false;
    }

    public static ArrayList getReferenceSetByDie(String dieNo, String state) throws WTException {
	ArrayList result = new ArrayList();

	try {
	    QueryResult qr = EDMQueryHelper.getReferenceSetByDie(dieNo, state);

	    if (qr == null) {
		return result;
	    }

	    while (qr.hasMoreElements()) {
		Object obj[] = (Object[]) qr.nextElement();
		EPMDocument epm = (EPMDocument) obj[0];
		result.add(epm);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return result;
    }

    public static ArrayList getReferenceDocs(WTPart part, String referenceType, int required) throws WTException, WTPropertyVetoException {
	if (VersionHelper.isLatestRevision(part)) {
	    return getRefDocMaster(part, referenceType, required);
	} else {
	    return getRefDocsHistory(part, referenceType, required, true);
	}
    }

    public static ArrayList getRefDocMaster(WTPart part, String referenceType, int required) throws WTException, WTPropertyVetoException {
	ArrayList result = new ArrayList();

	Class clsRefLink = EPMLink.class;
	Class clsEPM = EPMDocumentMaster.class;

	QuerySpec qs = new QuerySpec();

	int idxRefLink = qs.appendClassList(clsRefLink, true);
	int idxEPM = qs.appendClassList(clsEPM, true);

	qs.appendWhere(new SearchCondition(clsRefLink, "roleBObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
	        .getObjectIdentifier(part.getMaster()).getId()), new int[] { idxRefLink });

	if ((referenceType != null) && (referenceType.trim().length() > 0)) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, EPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, referenceType),
		    new int[] { idxRefLink });
	}

	if (required > -1) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, EPMLink.REQUIRED, SearchCondition.EQUAL, required), new int[] { idxRefLink });
	}

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(clsRefLink, "roleAObjectRef.key.id", clsEPM, "thePersistInfo.theObjectIdentifier.id"),
	        new int[] { idxRefLink, idxEPM });

	ClassAttribute sortCa = null;
	OrderBy orderby = null;
	int sortIdx = 0;

	sortCa = new ClassAttribute(clsEPM, "number");
	sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	qs.appendSelect(sortCa, new int[] { idxEPM }, true);

	orderby = new OrderBy(sortCa, false, null);
	qs.appendOrderBy(orderby, new int[] { idxEPM });

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	Object data[] = null;
	Object oo[] = null;

	while (qr.hasMoreElements()) {
	    oo = (Object[]) qr.nextElement();

	    data = new Object[2];
	    data[0] = (EPMLink) oo[0];
	    data[1] = VersionHelper.getLatestRevision(EPMDocument.class, (EPMDocumentMaster) oo[1]);

	    result.add(data);
	}

	return result;
    }

    public static ArrayList getRefDocsHistory(WTPart part, String referenceType, int required, boolean isMaxRev) throws WTException,
	    WTPropertyVetoException {
	ArrayList result = new ArrayList();

	Class clsRefLink = PartToEPMLink.class;
	Class clsDoc = EPMDocument.class;

	QuerySpec qs = new QuerySpec();

	int idxRefLink = qs.appendClassList(clsRefLink, true);
	int idxDoc = qs.appendClassList(clsDoc, true);

	qs.appendWhere(
	        new SearchCondition(clsRefLink, "roleBObjectRef.key.branchId", SearchCondition.EQUAL, VersionControlHelper
	                .getBranchIdentifier(part)), new int[] { idxRefLink });

	if ((referenceType != null) && (referenceType.trim().length() > 0)) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, PartToEPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, referenceType),
		    new int[] { idxRefLink });
	}

	if (required > -1) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, PartToEPMLink.REQUIRED, SearchCondition.EQUAL, required),
		    new int[] { idxRefLink });
	}

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(clsRefLink, "roleAObjectRef.key.branchId", clsDoc, "iterationInfo.branchId"), new int[] {
	        idxRefLink, idxDoc });

	qs.appendAnd();
	qs.appendWhere(VersionControlHelper.getSearchCondition(clsDoc, true), new int[] { idxDoc });

	ClassAttribute sortCa = null;
	OrderBy orderby = null;
	int sortIdx = 0;

	sortCa = new ClassAttribute(clsDoc, "master>number");
	sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	qs.appendSelect(sortCa, new int[] { idxDoc }, true);

	orderby = new OrderBy(sortCa, false, null);
	qs.appendOrderBy(orderby, new int[] { idxDoc });

	sortCa = new ClassAttribute(clsDoc, "versionInfo.identifier.versionId");
	sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	qs.appendSelect(sortCa, new int[] { idxDoc }, true);

	orderby = new OrderBy(sortCa, false, null);
	qs.appendOrderBy(orderby, new int[] { idxDoc });

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	HashMap map0 = new HashMap();

	Object[] data = null;
	PartToEPMLink refLink = null;
	Object oo[] = null;

	while (qr.hasMoreElements()) {
	    oo = (Object[]) qr.nextElement();

	    data = new Object[2];
	    data[0] = oo[0];
	    data[1] = oo[1];

	    refLink = (PartToEPMLink) oo[0];

	    if (isMaxRev) {
		if (map0.containsKey(refLink.getEpm().getNumber())) {
		    Object[] data0 = (Object[]) map0.get(refLink.getEpm().getNumber());
		    EPMDocument doc0 = (EPMDocument) data0[1];

		    if (doc0.getVersionIdentifier().getSeries().lessThan(refLink.getEpm().getVersionIdentifier().getSeries())) {
			map0.put(refLink.getEpm().getNumber(), data);
		    }
		} else {
		    map0.put(refLink.getEpm().getNumber(), data);
		}
	    } else {
		result.add(data);
	    }
	}

	if (isMaxRev) {
	    Iterator itr = map0.values().iterator();

	    while (itr.hasNext()) {
		result.add(itr.next());
	    }

	    Collections.sort(result, new Comparator() {
		public int compare(Object a, Object b) {
		    Object[] ar = (Object[]) a;
		    Object[] br = (Object[]) b;

		    int rtn0 = ((EPMDocument) ar[1]).getNumber().compareToIgnoreCase(((EPMDocument) br[1]).getNumber());
		    if (rtn0 != 0) {
			return rtn0;
		    }

		    int rtn = 0;
		    try {
			if (((EPMDocument) ar[1]).getVersionIdentifier().getSeries()
			        .lessThan(((EPMDocument) br[1]).getVersionIdentifier().getSeries())) {
			    rtn = -1;
			} else {
			    rtn = 1;
			}
		    } catch (VersionControlException e) {
			Kogger.error(getClass(), e);
			rtn = rtn0;
		    }

		    return rtn;
		}
	    });
	}

	return result;
    }

    public static QueryResult getEPMLink(WTPart part, EPMDocument epm, String referenceType, int required) {
	WTPartMaster pm = null;
	EPMDocumentMaster em = null;

	if (part != null) {
	    pm = (WTPartMaster) part.getMaster();
	}

	if (epm != null) {
	    em = (EPMDocumentMaster) epm.getMaster();
	}

	return getEPMLink(pm, em, referenceType, required);
    }

    public static QueryResult getEPMLink(WTPartMaster part, EPMDocumentMaster epm, String referenceType, int required) {
	try {
	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(EPMLink.class, true);

	    if (epm != null) {

		if ("CU_DRAWING".equals(referenceType)) {
		    // 3D 2D 판단하기
		    String cadName = epm.getCADName(); //

		    boolean is3D = cadName.toUpperCase().endsWith(".PRT");
		    
		    String partNo = part.getNumber();
		    EPMDocumentMaster master = getDocPartRelatedCustomer(partNo,is3D);

		    if (master != null) {
			if (qs.getConditionCount() > 0) {
			    qs.appendAnd();
			}
			qs.appendWhere(new SearchCondition(EPMLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
			        .getObjectIdentifier(master).getId()), new int[] { idx });
		    }

		} else {
		    qs.appendWhere(new SearchCondition(EPMLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
			    .getObjectIdentifier(epm).getId()), new int[] { idx });
		}
	    }else{
		
		if ("CU_DRAWING".equals(referenceType)) {
		    
		    String partNo = part.getNumber();
		    EPMDocumentMaster master = getDocPartRelatedCustomer(partNo,false);

		    if (master == null) {
			return null;
		    }

		}
		
	    }

	    if ((referenceType != null) && (referenceType.trim().length() > 0)) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(EPMLink.class, EPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, referenceType),
		        new int[] { idx });
	    }

	    if (required > -1) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(EPMLink.class, EPMLink.REQUIRED, SearchCondition.EQUAL, required), new int[] { idx });
	    }

	    if (part != null) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}
		qs.appendWhere(new SearchCondition(EPMLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
		        .getObjectIdentifier(part).getId()), new int[] { idx });
	    }
	    return PersistenceHelper.manager.find((StatementSpec) qs);
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	    return null;
	}
    }

    public static ArrayList getReferencedByParts(EPMDocument epm, String referenceType, int required) throws WTException,
	    WTPropertyVetoException {

	ArrayList ret = new ArrayList();
	SessionContext sessioncontext = SessionContext.newContext();

	try {

	    // Set temporary Session - Administrator
	    SessionHelper.manager.setAdministrator();
	    ret = getReferencedByParts(epm, referenceType, required, true);

	} catch (Exception e) {

	    Kogger.error(EDMHelper.class, e);

	} finally {

	    // Restore User Session
	    SessionContext.setContext(sessioncontext);
	}

	return ret;

    }

    public static ArrayList getReferencedByParts(EPMDocument epm, String referenceType, int required, boolean ismax) throws WTException,
	    WTPropertyVetoException {
	ArrayList ret = new ArrayList();
	SessionContext sessioncontext = SessionContext.newContext();

	try {

	    // Set temporary Session - Administrator
	    SessionHelper.manager.setAdministrator();
	    if (VersionHelper.isLatestRevision(epm)) {// Kogger.debug(getClass(),
		                                      // ">>>>>getReferencedByParts >>>> VersionHelper.isLatestRevision(epm)if ::: "+VersionHelper.isLatestRevision(epm)
		                                      // );
		ret = getRefedByPartMasters(epm, referenceType, required);
	    } else {// Kogger.debug(getClass(),
		    // ">>>>>getReferencedByParts >>>> VersionHelper.isLatestRevision(epm)else ::: "+VersionHelper.isLatestRevision(epm) );
		ret = getRefedByPartHistory(epm, referenceType, required, ismax);
	    }

	} catch (Exception e) {

	    Kogger.error(EDMHelper.class, e);

	} finally {

	    // Restore User Session
	    SessionContext.setContext(sessioncontext);
	}

	return ret;
    }

    public static ArrayList getRefedByPartMasters(EPMDocument epm, String referenceType, int required) throws WTException,
	    WTPropertyVetoException {
	return getRefedByPartMasters((EPMDocumentMaster) epm.getMaster(), referenceType, required);
    }

    public static ArrayList getRefedByPartMasters(EPMDocumentMaster epm, String referenceType, int required) throws WTException,
	    WTPropertyVetoException {
	ArrayList result = new ArrayList();

	Class clsRefLink = EPMLink.class;
	Class clsPart = WTPartMaster.class;

	QuerySpec qs = new QuerySpec();

	int idxRefLink = qs.appendClassList(clsRefLink, true);
	int idxPart = qs.appendClassList(clsPart, true);

	// Kogger.debug(getClass(), "EDMHelper >>>>> getRefedByPartMasters >>> referenceType :::::::: " + referenceType.toString());
	// Kogger.debug(getClass(), "EDMHelper >>>>> getRefedByPartMasters >>> required :::::::: " + required);
	//
	// Kogger.debug(getClass(), "EDMHelper >>>>> getRefedByPartMasters >>> idxRefLink :::::::: " + idxRefLink);
	// Kogger.debug(getClass(), "EDMHelper >>>>> getRefedByPartMasters >>> idxPart :::::::: " + idxPart);
	// Kogger.debug(getClass(), "EDMHelper >>>>> getRefedByPartMasters >>> getObjectIdentifier :::::::: " +
	// PersistenceHelper.getObjectIdentifier(epm).getId() );

	qs.appendWhere(new SearchCondition(clsRefLink, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
	        .getObjectIdentifier(epm).getId()), new int[] { idxRefLink });

	if ((referenceType != null) && (referenceType.trim().length() > 0)) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, EPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, referenceType),
		    new int[] { idxRefLink });
	}

	if (required > -1) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, EPMLink.REQUIRED, SearchCondition.EQUAL, required), new int[] { idxRefLink });
	}

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(clsRefLink, "roleBObjectRef.key.id", clsPart, "thePersistInfo.theObjectIdentifier.id"),
	        new int[] { idxRefLink, idxPart });

	ClassAttribute sortCa = null;
	OrderBy orderby = null;
	int sortIdx = 0;

	sortCa = new ClassAttribute(clsPart, "number");
	sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	qs.appendSelect(sortCa, new int[] { idxPart }, true);

	orderby = new OrderBy(sortCa, false, null);
	qs.appendOrderBy(orderby, new int[] { idxPart });

	// Kogger.debug(getClass(), "============EDMHelper getRefedByPartMasters ============= \n" + qs +
	// "\n ========================================================");

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	Object data[] = null;
	Object oo[] = null;

	while (qr.hasMoreElements()) {
	    oo = (Object[]) qr.nextElement();

	    data = new Object[2];
	    data[0] = (EPMLink) oo[0];
	    data[1] = VersionHelper.getLatestRevision(WTPart.class, (WTPartMaster) oo[1]);

	    result.add(data);
	}

	return result;
    }

    public static ArrayList getRefedByPartHistory(EPMDocument epm, String referenceType, int required, boolean isMaxRev)
	    throws WTException, WTPropertyVetoException {
	ArrayList result = new ArrayList();

	Class clsRefLink = PartToEPMLink.class;
	Class clsPart = WTPart.class;

	QuerySpec qs = new QuerySpec();

	int idxRefLink = qs.appendClassList(clsRefLink, true);
	int idxPart = qs.appendClassList(clsPart, true);

	// Kogger.debug(getClass(), "EDMHelper >>>>> referenceType :::::::: " + referenceType.toString());
	// Kogger.debug(getClass(), "EDMHelper >>>>> required :::::::: " + required);
	// Kogger.debug(getClass(), "EDMHelper >>>>> isMaxRev :::::::: " + isMaxRev);
	//
	// Kogger.debug(getClass(), "EDMHelper >>>>> idxRefLink :::::::: " + idxRefLink);
	// Kogger.debug(getClass(), "EDMHelper >>>>> idxPart :::::::: " + idxPart);
	// Kogger.debug(getClass(), "EDMHelper >>>>> getBranchIdentifier :::::::: " + VersionControlHelper.getBranchIdentifier(epm) );

	qs.appendWhere(
	        new SearchCondition(clsRefLink, "roleAObjectRef.key.branchId", SearchCondition.EQUAL, VersionControlHelper
	                .getBranchIdentifier(epm)), new int[] { idxRefLink });

	if ((referenceType != null) && (referenceType.trim().length() > 0)) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, PartToEPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, referenceType),
		    new int[] { idxRefLink });
	}

	if (required > -1) {
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(clsRefLink, PartToEPMLink.REQUIRED, SearchCondition.EQUAL, required),
		    new int[] { idxRefLink });
	}

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(clsRefLink, "roleBObjectRef.key.branchId", clsPart, "iterationInfo.branchId"), new int[] {
	        idxRefLink, idxPart });

	qs.appendAnd();
	qs.appendWhere(VersionControlHelper.getSearchCondition(clsPart, true), new int[] { idxPart });

	ClassAttribute sortCa = null;
	OrderBy orderby = null;
	int sortIdx = 0;

	sortCa = new ClassAttribute(clsPart, "master>number");
	sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	qs.appendSelect(sortCa, new int[] { idxPart }, true);

	orderby = new OrderBy(sortCa, false, null);
	qs.appendOrderBy(orderby, new int[] { idxPart });

	sortCa = new ClassAttribute(clsPart, "versionInfo.identifier.versionId");
	sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
	qs.appendSelect(sortCa, new int[] { idxPart }, true);

	orderby = new OrderBy(sortCa, false, null);
	qs.appendOrderBy(orderby, new int[] { idxPart });

	// Kogger.debug(getClass(), "============EDMHelper getRefedByPartHistory ============= \n" + qs +
	// "\n ========================================================");

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
	HashMap map0 = new HashMap();
	PartToEPMLink refLink = null;

	Object[] data = null;
	Object oo[] = null;

	while (qr.hasMoreElements()) {
	    oo = (Object[]) qr.nextElement();

	    data = new Object[2];
	    data[0] = oo[0];
	    data[1] = oo[1];

	    refLink = (PartToEPMLink) oo[0];

	    if (isMaxRev) {
		if (map0.containsKey(refLink.getPart().getNumber())) {
		    Object[] data0 = (Object[]) map0.get(refLink.getPart().getNumber());
		    WTPart part0 = (WTPart) data0[1];

		    if (part0.getVersionIdentifier().getSeries().lessThan(refLink.getPart().getVersionIdentifier().getSeries())) {
			map0.put(refLink.getPart().getNumber(), data);
		    }
		} else {
		    map0.put(refLink.getPart().getNumber(), data);
		}
	    } else {
		result.add(data);
	    }
	}

	if (isMaxRev) {
	    Iterator itr = map0.values().iterator();

	    while (itr.hasNext()) {
		result.add(itr.next());
	    }

	    Collections.sort(result, new Comparator() {
		public int compare(Object a, Object b) {
		    Object[] ar = (Object[]) a;
		    Object[] br = (Object[]) b;

		    int rtn0 = ((WTPart) ar[1]).getNumber().compareToIgnoreCase(((WTPart) br[1]).getNumber());

		    if (rtn0 != 0) {
			return rtn0;
		    }

		    int rtn = 0;

		    try {
			if (((WTPart) ar[1]).getVersionIdentifier().getSeries()
			        .lessThan(((WTPart) br[1]).getVersionIdentifier().getSeries())) {
			    rtn = -1;
			} else {
			    rtn = 1;
			}
		    } catch (VersionControlException e) {
			Kogger.error(getClass(), e);
			rtn = rtn0;
		    }

		    return rtn;
		}
	    });
	}

	return result;
    }

    public static ArrayList getPartToEPMLink(WTPart part, EPMDocument epm, String referenceType, int required) {
	ArrayList r = new ArrayList();

	try {
	    Class cls_link = PartToEPMLink.class;

	    QuerySpec qs = new QuerySpec();
	    int idx_link = qs.appendClassList(cls_link, true);

	    SearchCondition sc = null;

	    if (part != null) {
		sc = new SearchCondition(cls_link, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		        wt.vc.VersionControlHelper.getBranchIdentifier(part));
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if (epm != null) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(cls_link, "roleAObjectRef.key.branchId", SearchCondition.EQUAL,
		        wt.vc.VersionControlHelper.getBranchIdentifier(epm));
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if ((referenceType != null) && (referenceType.trim().length() > 0)) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(cls_link, PartToEPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, referenceType);
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if (required > -1) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(cls_link, PartToEPMLink.REQUIRED, SearchCondition.EQUAL, required);
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    QueryResult result = PersistenceHelper.manager.find(qs);

	    Object oo[] = null;
	    while (result.hasMoreElements()) {
		oo = (Object[]) result.nextElement();
		r.add((PartToEPMLink) oo[0]);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return r;
    }

    public static ArrayList getPartToEPMLink(WTPart part, String referenceType, int required) {
	return getPartToEPMLink(part, null, referenceType, required);
    }

    public static ArrayList getPartToEPMLink(EPMDocument epm, String referenceType, int required) {
	return getPartToEPMLink(null, epm, referenceType, required);
    }

    public static QueryResult getRefedModel(EPMDocument epm, EPMDocument model, int required) {
	EPMDocumentMaster dm = null;
	EPMDocumentMaster mm = null;

	if (epm != null) {
	    dm = (EPMDocumentMaster) epm.getMaster();
	}

	if (model != null) {
	    mm = (EPMDocumentMaster) model.getMaster();
	}

	return getRefedModel(dm, mm, required);
    }

    public static QueryResult getRefedModel(EPMDocumentMaster epm, EPMDocumentMaster model, int required) {
	try {
	    return getRefedModel(epm, model, null, required);
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	    return null;
	}
    }

    public static QueryResult getRefedModel(EPMDocumentMaster epm, EPMDocumentMaster model, String relatedLinkOid, int required) {
	try {
	    Class linkClass = ModelReferenceLink.class;
	    QuerySpec qs = new QuerySpec();
	    int idx_link = qs.appendClassList(ModelReferenceLink.class, true);
	    SearchCondition sc = null;

	    if (epm != null) {
		sc = new SearchCondition(linkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(
		        epm).getId());
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if (model != null) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(linkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(
		        model).getId());
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if ((relatedLinkOid != null) && (relatedLinkOid.trim().length() > 0)) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(linkClass, ModelReferenceLink.RELATED_LINK_OID, SearchCondition.EQUAL, relatedLinkOid);
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if (required > -1) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(linkClass, ModelReferenceLink.REQUIRED, SearchCondition.EQUAL, required);
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    return PersistenceHelper.manager.find((StatementSpec) qs);
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	    return null;
	}
    }

    public static QueryResult getRefedModelHistory(EPMDocument epm, EPMDocument model, int required) {
	QueryResult qr = null;

	try {
	    Class linkClass = DrawingToModelReferenceLink.class;
	    QuerySpec qs = new QuerySpec();
	    int idx_link = qs.appendClassList(DrawingToModelReferenceLink.class, true);
	    SearchCondition sc = null;

	    if (epm != null) {
		sc = new SearchCondition(linkClass, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		        VersionControlHelper.getBranchIdentifier(epm));
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if (model != null) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(linkClass, "roleAObjectRef.key.branchId", SearchCondition.EQUAL,
		        VersionControlHelper.getBranchIdentifier(model));
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    if (required > -1) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(linkClass, DrawingToModelReferenceLink.REQUIRED, SearchCondition.EQUAL, required);
		qs.appendWhere(sc, new int[] { idx_link });
	    }

	    qr = PersistenceHelper.manager.find((StatementSpec) qs);
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return qr;
    }

    public static ArrayList getRefedModelHistory(EPMDocument epm, WTPart part, String referenceType, int required, boolean isMax) {
	ArrayList result = new ArrayList();

	try {
	    QuerySpec qs = getRefedModelHistorySpec(epm, part, referenceType, required);
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
	    Object[] data = null;
	    HashMap map0 = new HashMap();
	    Object[] oo = null;

	    while (qr.hasMoreElements()) {
		oo = (Object[]) qr.nextElement();

		EPMDocument e = (EPMDocument) oo[3];
		DrawingToModelReferenceLink dml = (DrawingToModelReferenceLink) oo[4];

		data = new Object[2];
		data[0] = dml;
		data[1] = e;

		if (isMax) {
		    if (map0.containsKey(e.getNumber())) {
			Object[] data0 = (Object[]) map0.get(e.getNumber());
			EPMDocument e0 = (EPMDocument) data0[1];

			if (e0.getVersionIdentifier().getSeries().lessThan(e.getVersionIdentifier().getSeries())) {
			    map0.put(e.getNumber(), data);
			}
		    } else {
			map0.put(e.getNumber(), data);
		    }
		} else {
		    result.add(data);
		}
	    }

	    if (isMax) {
		Iterator itr = map0.values().iterator();

		while (itr.hasNext()) {
		    result.add(itr.next());
		}
	    }

	    Collections.sort(result, new Comparator() {
		public int compare(Object a, Object b) {
		    Object[] ar = (Object[]) a;
		    Object[] br = (Object[]) b;

		    int rtn0 = ((EPMDocument) ar[1]).getNumber().compareToIgnoreCase(((EPMDocument) br[1]).getNumber());
		    if (rtn0 != 0) {
			return rtn0;
		    }

		    int rtn = 0;
		    try {
			if (((EPMDocument) ar[1]).getVersionIdentifier().getSeries()
			        .lessThan(((EPMDocument) br[1]).getVersionIdentifier().getSeries())) {
			    rtn = -1;
			} else {
			    rtn = 1;
			}
		    } catch (VersionControlException e) {
			Kogger.error(getClass(), e);
			rtn = rtn0;
		    }

		    return rtn;
		}
	    });
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return result;
    }

    /*
     * 해당 도면과 관련된 모델(개발검토도면)
     */
    public static ArrayList getReferencedByModels(EPMDocument epm, int required) throws WTException {
	QueryResult result = null;

	if (VersionHelper.isLatestRevision(epm)) {
	    result = getRefedModel((EPMDocumentMaster) epm.getMaster(), null, required);
	} else {
	    result = getRefedModelHistory(epm, null, required);
	}

	ArrayList arr = new ArrayList();

	if (result != null) {
	    Object[] data = null;

	    while (result.hasMoreElements()) {
		Object[] oo = (Object[]) result.nextElement();

		data = new Object[2];
		data[0] = oo[0];
		Object lk = oo[0];

		if (lk instanceof ModelReferenceLink) {
		    data[1] = VersionHelper.getLatestRevision(EPMDocument.class, ((ModelReferenceLink) lk).getModelMaster());
		} else if (lk instanceof DrawingToModelReferenceLink) {
		    data[1] = ((DrawingToModelReferenceLink) lk).getModel();
		}

		arr.add(data);
	    }
	}

	return arr;
    }

    /*
     * 해당 모델과 관련된 도면
     */
    public static ArrayList getReferenceEPMs(EPMDocument model, int required) throws WTException {
	QueryResult result = null;

	if (VersionHelper.isLatestRevision(model)) {
	    result = getRefedModel(null, (EPMDocumentMaster) model.getMaster(), required);
	} else {
	    result = getRefedModelHistory(null, model, required);
	}

	ArrayList arr = new ArrayList();

	if (result != null) {
	    Object[] data = null;

	    while (result.hasMoreElements()) {
		Object[] oo = (Object[]) result.nextElement();

		data = new Object[2];
		data[0] = oo[0];
		Object lk = oo[0];

		if (lk instanceof ModelReferenceLink) {
		    data[1] = VersionHelper.getLatestRevision(EPMDocument.class, ((ModelReferenceLink) lk).getDrawingMaster());
		} else if (lk instanceof DrawingToModelReferenceLink) {
		    data[1] = ((DrawingToModelReferenceLink) lk).getDrawing();
		}

		arr.add(data);
	    }
	}

	return arr;
    }

    public static boolean isReferenceEPMExist(EPMDocument model, int required) throws WTException {
	return isReferenceEPMExist(model, null, required);
    }

    public static boolean isReferenceEPMExist(EPMDocument model, EPMDocument epm, int required) throws WTException {
	if (model == null) {
	    throw new WTException("EPMDocument is Null...");
	}

	QueryResult qr = getRefedModel(epm, model, required);
	if ((qr != null) && (qr.size() > 0)) {
	    return true;
	}

	return false;
    }

    public static boolean isReferencedByModelExist(EPMDocument epm, int required) throws WTException {
	return isReferencedByModelExist(epm, null, required);
    }

    public static boolean isReferencedByModelExist(EPMDocument epm, EPMDocument model, int required) throws WTException {
	if (epm == null) {
	    throw new WTException("EPMDocument is Null...");
	}

	QueryResult qr = EDMHelper.getRefedModel(epm, model, required);

	if ((qr != null) && (qr.size() > 0)) {
	    return true;
	}

	return false;
    }

    public static boolean isReferencedByPartExist(EPMDocument epm, String referenceType, int required) throws WTException {
	return isReferencedByPartExist(epm, null, referenceType, required);
    }

    public static boolean isReferencedByPartExist(EPMDocument epm, WTPart part, String referenceType, int required) throws WTException {
	if (epm == null) {
	    throw new WTException("EPMDocument is Null...");
	}

	// EDMProperties edmProperties = EDMProperties.getInstance();
	// String referenceType = edmProperties.getReferenceType(category);

	QueryResult qr = getEPMLink(part, epm, referenceType, required);

	if ((qr != null) && (qr.size() > 0)) {
	    return true;
	}

	return false;
    }

    public static boolean isReferenceDocExist(WTPart part, String referenceType, int required) {
	return isReferenceDocExist(part, null, referenceType, required);
    }

    public static boolean isReferenceDocExist(WTPart part, EPMDocument epm, String referenceType, int required) {
	try {
	    if (part == null) {
		return false;
	    }

	    // EDMProperties edmProperties = EDMProperties.getInstance();
	    // String referenceType = edmProperties.getReferenceType(category);

	    QueryResult qr = getEPMLink(part, epm, referenceType, required);
	    if ((qr != null) && (qr.size() > 0)) {
		return true;
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return false;
    }

    /**
     * 제품도면 여부...
     * 
     * @param epm
     * @param valueViews
     * @return
     * @throws WTException
     */
    public static boolean isProductDrawing(EPMDocument epm) throws WTException {
	return isProductDrawing(epm, EDMAttributes.getAttributeValues(epm, Locale.KOREAN));
    }

    /**
     * 제품도면 여부...
     * 
     * @param epm
     * @param valueViews
     * @return
     * @throws WTException
     */
    public static boolean isProductDrawing(EPMDocument epm, HashMap viewMap) throws WTException {
	if (viewMap == null) {
	    viewMap = EDMAttributes.getAttributeValues(epm, Locale.KOREAN);
	}

	return "PRODUCT_DRAWING".equals(getCADManageType(epm, viewMap));
    }

    /**
     * 금형도면 여부...
     * 
     * @param epm
     * @param valueViews
     * @return
     * @throws WTException
     */
    public static boolean isMoldDrawing(EPMDocument epm) throws WTException {
	return isMoldDrawing(epm, EDMAttributes.getAttributeValues(epm, Locale.KOREAN));
    }

    /**
     * 금형도면 여부...
     * 
     * @param epm
     * @param valueViews
     * @return
     * @throws WTException
     */
    public static boolean isMoldDrawing(EPMDocument epm, HashMap viewMap) throws WTException {
	if (viewMap == null) {
	    viewMap = EDMAttributes.getAttributeValues(epm, Locale.KOREAN);
	}

	return "MOLD_DRAWING".equals(getCADManageType(epm, viewMap));
    }

    public static String getCADManageType(EPMDocument epm, HashMap viewMap) throws WTException {
	if (viewMap == null) {
	    viewMap = EDMAttributes.getAttributeValues(epm, Locale.KOREAN);
	}

	if (viewMap == null) {
	    return "";
	}

	String manageType = "";
	if (viewMap.get(EDMHelper.IBA_CAD_MANAGE_TYPE) != null) {
	    StringValueDefaultView sdv = (StringValueDefaultView) viewMap.get(EDMHelper.IBA_CAD_MANAGE_TYPE);
	    manageType = EDMEnumeratedTypeUtil.getCADManageType(sdv.getValue(), Locale.KOREAN);
	}

	return manageType;
    }

    public static ArrayList getAssociatedDocsByECAD2(EPMDocument epm, WTPart part) throws WTException {
	ArrayList result = new ArrayList();

	try {
	    QuerySpec qs = new QuerySpec();

	    int idx_pm = qs.appendClassList(EPMLink.class, false);
	    int idx_epm = qs.appendClassList(EPMDocument.class, true);

	    SearchCondition sc = null;

	    // sc = new SearchCondition(EPMLink.class,EPMLink.ECAD, SearchCondition.EQUAL,true);
	    // qs.appendWhere(sc, new int[]{idx_pm});

	    // qs.appendAnd();

	    sc = new SearchCondition(EPMLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(
		    part.getMaster()).getId());
	    qs.appendWhere(sc, new int[] { idx_pm });

	    qs.appendAnd();
	    sc = new SearchCondition(new ClassAttribute(EPMLink.class, "roleAObjectRef.key.id"), "=", new ClassAttribute(EPMDocument.class,
		    "masterReference.key.id"));

	    sc.setFromIndicies(new int[] { idx_pm, idx_epm }, 0);
	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { idx_pm, idx_epm });

	    // qs.appendAnd();
	    // sc = new SearchCondition(EPMDocument.class,"versionInfo.identifier.versionId",
	    // SearchCondition.EQUAL,epm.getVersionIdentifier().getSeries().getValue());
	    // qs.appendWhere(sc, new int[]{idx_epm});

	    qs.appendAnd();
	    qs.appendWhere(VersionControlHelper.getSearchCondition(EPMDocument.class, true), new int[] { idx_epm });
	    Kogger.debug(EDMHelper.class, "ECAD 쿼리 == " + qs);
	    Object[] oo = null;

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    while (qr.hasMoreElements()) {
		oo = (Object[]) qr.nextElement();
		result.add(oo[0]);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	    throw new WTException(e.getLocalizedMessage());
	}

	return result;
    }

    public static ArrayList<EPMDocument> getAssociatedDocsByECAD(EPMDocument epm, WTPart part) throws WTException {
	ArrayList<EPMDocument> tempList = new ArrayList<EPMDocument>();

	try {
	    QuerySpec qs = new QuerySpec();

	    // qs.setAdvancedQueryEnabled(true);
	    int idx_pm = qs.appendClassList(EPMLink.class, false);
	    int idx_epm = qs.appendClassList(EPMDocument.class, true);

	    SearchCondition sc = null;

	    // sc = new SearchCondition(EPMLink.class,EPMLink.ECAD, SearchCondition.EQUAL,true);
	    // qs.appendWhere(sc, new int[]{idx_pm});

	    // qs.appendAnd();

	    sc = new SearchCondition(EPMLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(
		    part.getMaster()).getId());
	    qs.appendWhere(sc, new int[] { idx_pm });

	    qs.appendAnd();
	    sc = new SearchCondition(new ClassAttribute(EPMLink.class, "roleAObjectRef.key.id"), "=", new ClassAttribute(EPMDocument.class,
		    "masterReference.key.id"));

	    sc.setFromIndicies(new int[] { idx_pm, idx_epm }, 0);
	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { idx_pm, idx_epm });

	    // 2차 고도화 YJLEE(TKLEE) 수정함. - SCH, PCB, AUTOCAD 별개로 등록, 수정, 개정함. - 최신으로만 서로 가져 옴.
	    // qs.appendAnd();
	    // sc = new SearchCondition(EPMDocument.class,"versionInfo.identifier.versionId",
	    // SearchCondition.EQUAL,epm.getVersionIdentifier().getSeries().getValue());
	    // qs.appendWhere(sc, new int[]{idx_epm});

	    qs.appendAnd();
	    qs.appendWhere(VersionControlHelper.getSearchCondition(EPMDocument.class, true), new int[] { idx_epm });
	    Kogger.debug(EDMHelper.class, "ECAD 쿼리 == " + qs);
	    Object[] oo = null;

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    while (qr.hasMoreElements()) {
		oo = (Object[]) qr.nextElement();
		tempList.add((EPMDocument) oo[0]);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	    throw new WTException(e.getLocalizedMessage());
	}

	// 2차 고도화 YJLEE(TKLEE) 수정함. - SCH, PCB, AUTOCAD 별개로 등록, 수정, 개정함. - 최신으로만 서로 가져 옴.
	Map uniqueMap = new HashMap();
	ArrayList<EPMDocument> resultList = new ArrayList<EPMDocument>();
	for (EPMDocument epmDoc : tempList) {

	    // 중복 SCH 방지
	    if (epm.getNumber().endsWith("_SCH") && epmDoc.getNumber().endsWith("_SCH")) {
		if (!epm.getNumber().equals(epmDoc.getNumber())) {
		    continue;
		}
	    }
	    // 동일 Prefix 체크
	    else if (epm.getNumber().endsWith("_SCH") && epmDoc.getNumber().endsWith("_PCB")) {
		if (!epm.getNumber().substring(0, 1).equals(epmDoc.getNumber().substring(0, 1))) {
		    continue;
		}
	    }

	    // 중복 PCB 방지
	    if (epm.getNumber().endsWith("_PCB") && epmDoc.getNumber().endsWith("_PCB")) {
		if (!epm.getNumber().equals(epmDoc.getNumber())) {
		    continue;
		}
	    }
	    // 동일 Prefix 체크
	    else if (epm.getNumber().endsWith("_PCB") && epmDoc.getNumber().endsWith("_SCH")) {
		if (!epm.getNumber().substring(0, 1).equals(epmDoc.getNumber().substring(0, 1))) {
		    continue;
		}
	    }

	    if (uniqueMap.containsKey(epmDoc.getNumber())) {
		continue;
	    } else {
		uniqueMap.put(epmDoc.getNumber(), null);
		EPMDocument latestEpm = null;
		try {
		    latestEpm = PartBaseHelper.service.getLatestEPM((EPMDocumentMaster) epmDoc.getMaster());
		} catch (Exception e) {
		    Kogger.error(EDMHelper.class, e);
		    throw new WTException(e);
		}
		resultList.add(latestEpm);
	    }
	}

	return resultList;
    }

    public static Object[] getAssociatedModelLinkObjs(EPMDocument epm, WTPart part, String referenceType, int required) throws WTException {
	if ((epm == null) || (part == null)) {
	    return null;
	}

	return getAssociatedModelLinkObjs((EPMDocumentMaster) epm.getMaster(), (WTPartMaster) part.getMaster(), referenceType, required);
    }

    public static Object[] getAssociatedModelLinkObjs(EPMDocumentMaster epm, WTPartMaster part, String referenceType, int required)
	    throws WTException {
	if ((epm == null) || (part == null)) {
	    return null;
	}

	try {
	    QuerySpec qs = getAssociatedModelSpec(epm, part, referenceType, required);

	    Object[] data = new Object[6];
	    Object[] oo = null;

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    if (qr.hasMoreElements()) {
		oo = (Object[]) qr.nextElement();
		// [0]:EDMLink - 도면-부품 링크
		// [1]:WTPartMaster - 부품마스터
		// [2]:EDMLink - 부품-모델 링크
		// [3]:EPMDocumentMaster - 모델 마스터
		// [4]:ModelReferenceLink - 모델-도면 링크

		data = new Object[6];
		data[0] = VersionHelper.getLatestRevision(EPMDocument.class, epm);
		data[1] = oo[0];
		data[2] = VersionHelper.getLatestRevision(WTPart.class, (WTPartMaster) oo[1]);
		data[3] = oo[2];
		data[4] = VersionHelper.getLatestRevision(EPMDocument.class, (EPMDocumentMaster) oo[3]);
		data[5] = oo[4];
		return data;
	    }
	} catch (Exception e) {
	    throw new WTException(e);
	}

	return null;
    }

    public static ArrayList getAssociatedModels(EPMDocument epm, WTPart part, String referenceType, int required) throws WTException {
	ArrayList result = new ArrayList();

	try {
	    boolean isLatestRevision = VersionHelper.isLatestRevision(epm);
	    QuerySpec qs = null;

	    if (isLatestRevision) {
		qs = getAssociatedModelSpec(epm, part, referenceType, required);
	    } else {
		qs = getRefedModelHistorySpec(epm, part, referenceType, required);
	    }

	    Object[] oo = null;
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    while (qr.hasMoreElements()) {
		oo = (Object[]) qr.nextElement();

		if (isLatestRevision) {
		    result.add(VersionHelper.getLatestRevision(EPMDocument.class, (EPMDocumentMaster) oo[3]));
		} else {
		    result.add(oo[3]);// EPMDocument
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return result;
    }

    public static EPMDocument getAssociatedModel(EPMDocument epm, WTPart part, String referenceType, int required) {
	if ((epm == null) || (part == null)) {
	    return null;
	}

	EPMDocument model = null;

	try {
	    ArrayList arrymodels = getAssociatedModels(epm, part, referenceType, required);

	    for (int i = 0; i < arrymodels.size(); i++) {
		model = (EPMDocument) arrymodels.get(i);
	    }
	} catch (WTException e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return model;
    }

    public static QuerySpec getAssociatedModelSpec(EPMDocument epm, WTPart part, String referenceType, int required) throws WTException {
	return getAssociatedModelSpec((EPMDocumentMaster) epm.getMaster(), (WTPartMaster) part.getMaster(), referenceType, required);
    }

    public static QuerySpec getAssociatedModelSpec(EPMDocumentMaster epm, WTPartMaster part, String referenceType, int required)
	    throws WTException {
	QuerySpec spec = null;

	try {
	    String modelReferenceType = EDMProperties.getInstance().getReferenceTypeForModel(null);
	    spec = new QuerySpec();

	    int idx_ep = spec.appendClassList(EPMLink.class, true);
	    int idx_partmaster = spec.appendClassList(WTPartMaster.class, true);
	    int idx_pm = spec.appendClassList(EPMLink.class, true);
	    int idx_epmmaster = spec.appendClassList(EPMDocumentMaster.class, true);
	    int idx_me = spec.appendClassList(ModelReferenceLink.class, true);

	    SearchCondition sc = null;

	    spec.appendWhere(new SearchCondition(EPMLink.class, "roleAObjectRef.key.id", "=", PersistenceHelper.getObjectIdentifier(epm)
		    .getId()), new int[] { idx_ep });
	    spec.appendAnd();

	    spec.appendWhere(new SearchCondition(EPMLink.class, EPMLink.REFERENCE_TYPE, "<>", modelReferenceType), new int[] { idx_ep });

	    if ((referenceType != null) && (referenceType.trim().length() > 0)) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(EPMLink.class, EPMLink.REFERENCE_TYPE, "=", referenceType.trim()),
		        new int[] { idx_ep });
	    }

	    if (required > -1) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(EPMLink.class, EPMLink.REQUIRED, "=", required), new int[] { idx_ep });
	    }

	    spec.appendAnd();
	    sc = new SearchCondition(new ClassAttribute(EPMLink.class, "roleBObjectRef.key.id"), "=", new ClassAttribute(
		    WTPartMaster.class, "thePersistInfo.theObjectIdentifier.id"));
	    sc.setFromIndicies(new int[] { idx_ep, idx_partmaster }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { idx_ep, idx_partmaster });

	    spec.appendAnd();
	    sc = new SearchCondition(WTPartMaster.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL, PersistenceHelper
		    .getObjectIdentifier(part).getId());
	    spec.appendWhere(sc, new int[] { idx_partmaster });

	    spec.appendAnd();
	    sc = new SearchCondition(new ClassAttribute(WTPartMaster.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(EPMLink.class, "roleBObjectRef.key.id"));
	    sc.setFromIndicies(new int[] { idx_partmaster, idx_pm }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { idx_partmaster, idx_pm });

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(EPMLink.class, EPMLink.REFERENCE_TYPE, "=", modelReferenceType), new int[] { idx_pm });

	    if (required > -1) {
		spec.appendAnd();
		sc = new SearchCondition(EPMLink.class, EPMLink.REQUIRED, SearchCondition.EQUAL, required);
		spec.appendWhere(sc, new int[] { idx_pm });
	    }

	    spec.appendAnd();
	    sc = new SearchCondition(new ClassAttribute(EPMLink.class, "roleAObjectRef.key.id"), "=", new ClassAttribute(
		    EPMDocumentMaster.class, "thePersistInfo.theObjectIdentifier.id"));
	    sc.setFromIndicies(new int[] { idx_pm, idx_epmmaster }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { idx_pm, idx_epmmaster });

	    spec.appendAnd();
	    sc = new SearchCondition(new ClassAttribute(EPMDocumentMaster.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(ModelReferenceLink.class, "roleAObjectRef.key.id"));
	    sc.setFromIndicies(new int[] { idx_epmmaster, idx_me }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { idx_epmmaster, idx_me });

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(ModelReferenceLink.class, "roleBObjectRef.key.id", "=", PersistenceHelper
		    .getObjectIdentifier(epm).getId()), new int[] { idx_me });

	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return spec;
    }

    public static QuerySpec getRefedModelHistorySpec(EPMDocument epm, WTPart part, String referenceType, int required) throws WTException {
	QuerySpec spec = null;

	try {
	    spec = new QuerySpec();

	    int idx_dp = spec.appendClassList(PartToEPMLink.class, true);
	    int idx_part = spec.appendClassList(WTPart.class, true);
	    int idx_pm = spec.appendClassList(PartToEPMLink.class, true);
	    int idx_model = spec.appendClassList(EPMDocument.class, true);
	    int idx_md = spec.appendClassList(DrawingToModelReferenceLink.class, true);

	    spec.appendWhere(new SearchCondition(PartToEPMLink.class, "roleAObjectRef.key.branchId", SearchCondition.EQUAL,
		    VersionControlHelper.getBranchIdentifier(epm)), new int[] { idx_dp });

	    if ((referenceType != null) && (referenceType.trim().length() > 0)) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REFERENCE_TYPE, SearchCondition.EQUAL,
		        referenceType), new int[] { idx_dp });
	    }

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REFERENCE_TYPE, SearchCondition.NOT_EQUAL,
		    EDMProperties.getInstance().getReferenceTypeForModel(null)), new int[] { idx_dp });

	    if (required > -1) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REQUIRED, SearchCondition.EQUAL, required),
		        new int[] { idx_dp });
	    }

	    spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(PartToEPMLink.class, "roleBObjectRef.key.branchId", WTPart.class, "iterationInfo.branchId"),
		    new int[] { idx_dp, idx_part });

	    spec.appendAnd();
	    spec.appendWhere(VersionControlHelper.getSearchCondition(WTPart.class, true), new int[] { idx_part });

	    if (part != null) {
		spec.appendAnd();
		spec.appendWhere(
		        new SearchCondition(WTPart.class, "iterationInfo.branchId", SearchCondition.EQUAL, VersionControlHelper
		                .getBranchIdentifier(part)), new int[] { idx_part });
	    }

	    spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(WTPart.class, "iterationInfo.branchId", PartToEPMLink.class, "roleBObjectRef.key.branchId"),
		    new int[] { idx_part, idx_pm });

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, EDMProperties
		    .getInstance().getReferenceTypeForModel(null)), new int[] { idx_pm });

	    if (required > -1) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REQUIRED, SearchCondition.EQUAL, required),
		        new int[] { idx_pm });
	    }

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(PartToEPMLink.class, "roleAObjectRef.key.branchId", EPMDocument.class,
		    "iterationInfo.branchId"), new int[] { idx_pm, idx_model });

	    spec.appendAnd();
	    spec.appendWhere(VersionControlHelper.getSearchCondition(EPMDocument.class, true), new int[] { idx_model });

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(EPMDocument.class, "iterationInfo.branchId", DrawingToModelReferenceLink.class,
		    "roleAObjectRef.key.branchId"), new int[] { idx_model, idx_md });

	    if (required > -1) {
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(DrawingToModelReferenceLink.class, DrawingToModelReferenceLink.REQUIRED,
		        SearchCondition.EQUAL, required), new int[] { idx_md });
	    }

	    spec.appendAnd();
	    spec.appendWhere(new SearchCondition(DrawingToModelReferenceLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		    VersionControlHelper.getBranchIdentifier(epm)), new int[] { idx_md });
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	    throw new WTException(e);
	}

	return spec;
    }

    public static Vector getRelatedDrawings(EPMDocument epm, ConfigSpec configSpec) throws WTException {
	Vector vector = new Vector();
	EPMDocumentType dt = epm.getDocType();
	// if ( (dt.equals(EPMDocumentType.toEPMDocumentType("CADASSEMBLY")) || dt.equals(EPMDocumentType.toEPMDocumentType("CADCOMPONENT"))
	// )) {
	// return vector;
	// }

	QueryResult refedResult = EPMStructureHelper.service.navigateReferencedBy((EPMDocumentMaster) epm.getMaster(), null, true);

	while (refedResult.hasMoreElements()) {
	    EPMDocument refedDrawing = (EPMDocument) refedResult.nextElement();
	    EPMDocument drawing = null;

	    if (configSpec != null) {
		QueryResult filteredResult = ConfigHelper.service.filteredIterationsOf(refedDrawing.getMaster(), configSpec);
		while (filteredResult.hasMoreElements()) {
		    drawing = (EPMDocument) filteredResult.nextElement();
		}
	    } else {
		drawing = refedDrawing;
	    }

	    if ((drawing != null) && (drawing.getDocType() == EPMDocumentType.toEPMDocumentType("CADDRAWING"))) {
		vector.add(drawing);
	    }
	}

	return vector;
    }

    public static Vector getReferenceDependency(EPMDocument doc, String role) {
	Vector references = new Vector();

	try {
	    QueryResult queryReferences = null;

	    if (role.equals("referencedBy")) {
		queryReferences = EPMStructureHelper.service.navigateReferencedBy((EPMDocumentMaster) doc.getMaster(), null, false);
	    } else {
		queryReferences = EPMStructureHelper.service.navigateReferences(doc, null, false);
	    }

	    Object dwg = null;
	    EPMReferenceLink referenceLink = null;

	    while (queryReferences.hasMoreElements()) {
		referenceLink = (EPMReferenceLink) queryReferences.nextElement();
		references.add(referenceLink);
		/*
	         * if(role.equals("referencedBy")) dwg = referenceLink.getReferencedBy(); else dwg = referenceLink.getReferences();
	         * references.add(dwg);
	         */
	    }
	} catch (Exception e) {
	    Kogger.debug(EDMHelper.class, "Error getting the Instance Type");
	    Kogger.error(EDMHelper.class, e);
	    return new Vector();
	}

	return references;
    }

    public static Vector getReferencedBy(EPMDocument doc, boolean flag) {
	Vector vec = new Vector();

	try {
	    EPMDocumentType dt = doc.getDocType();
	    // if (!( (dt.equals(EPMDocumentType.toEPMDocumentType("CADASSEMBLY")) ||
	    // dt.equals(EPMDocumentType.toEPMDocumentType("CADCOMPONENT"))) )) {
	    // return vec;
	    // }

	    HashMap epm2ds = new HashMap();
	    Vector refedByVec = getReferenceDependency(doc, "referencedBy");

	    for (int k = 0; k < refedByVec.size(); k++) {
		EPMReferenceLink referenceLink = (EPMReferenceLink) refedByVec.get(k);
		EPMDocument epm2d = (EPMDocument) referenceLink.getReferencedBy();

		if (!epm2d.getDocType().equals(EPMDocumentType.toEPMDocumentType("CADDRAWING"))) {
		    continue;
		}

		if (epm2ds.containsKey(epm2d.getNumber())) {
		    EPMReferenceLink preLink = (EPMReferenceLink) epm2ds.get(epm2d.getNumber());
		    EPMDocument pre = (EPMDocument) preLink.getReferencedBy();

		    if (epm2d.getVersionIdentifier().getSeries().equals(pre.getVersionIdentifier().getSeries())) {
			if (epm2d.getIterationIdentifier().getSeries().greaterThan(pre.getIterationIdentifier().getSeries())) {
			    epm2ds.put(epm2d.getNumber(), referenceLink);
			}
		    } else if (epm2d.getVersionIdentifier().getSeries().greaterThan(pre.getVersionIdentifier().getSeries())) {
			epm2ds.put(epm2d.getNumber(), referenceLink);
		    }
		} else {
		    epm2ds.put(epm2d.getNumber(), referenceLink);
		}
	    }

	    for (Iterator epm2dItr = epm2ds.values().iterator(); epm2dItr.hasNext();) {
		EPMReferenceLink referenceLink = (EPMReferenceLink) epm2dItr.next();

		if (flag) {
		    vec.add(referenceLink.getReferencedBy());
		} else {
		    vec.add(referenceLink);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return vec;
    }

    public static Vector getReferences(EPMDocument doc, boolean flag) {
	Vector vec = new Vector();

	try {
	    EPMDocumentType dt = doc.getDocType();
	    HashMap epm2ds = new HashMap();
	    Vector refedByVec = getReferenceDependency(doc, "references");

	    for (int k = 0; k < refedByVec.size(); k++) {
		EPMReferenceLink referenceLink = (EPMReferenceLink) refedByVec.get(k);
		EPMDocumentMaster epm2d = (EPMDocumentMaster) referenceLink.getReferences();

		if (flag) {
		    vec.add(VersionHelper.getLatestRevision(EPMDocument.class, epm2d));
		} else {
		    vec.add(referenceLink);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return vec;
    }

    public static Vector getEPMDocProjectLink(EPMDocument epm) throws WTException {
	return getEPMDocProjectLink(epm, null);
    }

    public static Vector getEPMDocProjectLink(EPMDocument epm, ProjectMaster pjtMaster) throws WTException {
	Vector result = new Vector();
	QuerySpec qs = new QuerySpec();
	int idx_link = qs.appendClassList(EPMDocProjectLink.class, true);

	if (epm != null) {
	    qs.appendWhere(new SearchCondition(EPMDocProjectLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		    VersionControlHelper.getBranchIdentifier(epm)), new int[] { idx_link });
	}

	if (pjtMaster != null) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(EPMDocProjectLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
		    .getObjectIdentifier(pjtMaster).getId()), new int[] { idx_link });
	}

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	Object oo[] = null;
	while (qr.hasMoreElements()) {
	    oo = (Object[]) qr.nextElement();
	    result.add(oo[0]);
	}

	return result;
    }

    public static Vector getEPMDocTypeCodeLink(EPMDocument epm) throws WTException {
	return getEPMDocTypeCodeLink(epm, null);
    }

    public static Vector getEPMDocTypeCodeLink(EPMDocument epm, NumberCode code) throws WTException {
	Vector result = new Vector();
	QuerySpec qs = new QuerySpec();
	int idx_link = qs.appendClassList(EPMDocTypeCodeLink.class, true);

	if (epm != null) {
	    qs.appendWhere(new SearchCondition(EPMDocTypeCodeLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		    VersionControlHelper.getBranchIdentifier(epm)), new int[] { idx_link });
	}

	if (code != null) {
	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(EPMDocTypeCodeLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, PersistenceHelper
		    .getObjectIdentifier(code).getId()), new int[] { idx_link });
	}

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	Object oo[] = null;
	while (qr.hasMoreElements()) {
	    oo = (Object[]) qr.nextElement();
	    result.add(oo[0]);
	}

	return result;
    }

    public static Object getProject(EPMDocument epm) throws WTException {
	try {
	    Vector links = getEPMDocProjectLink(epm);

	    if (links.size() > 0) {
		EPMDocProjectLink link = (EPMDocProjectLink) links.get(0);
		return link.getPjtMaster();
	    }
	    // QueryResult qr = PersistenceHelper.manager.navigate(epm, "pjtDoc", EPMDocProjectLink.class, false);
	    // while(qr.hasMoreElements()) {
	    // EPMDocProjectLink link = (EPMDocProjectLink)qr.nextElement();
	    // //ProjectMaster mst = link.getPjtMaster();
	    // return link.getPjtMaster();
	    // }
	} catch (WTException e) {
	    Kogger.error(EDMHelper.class, e);
	    new WTException(e);
	}

	return null;
    }

    public static Object getBizType(EPMDocument epm) throws WTException {
	try {
	    QueryResult qr = PersistenceHelper.manager.navigate(epm, "typeCode", EPMDocTypeCodeLink.class, false);

	    while (qr.hasMoreElements()) {
		EPMDocTypeCodeLink link = (EPMDocTypeCodeLink) qr.nextElement();
		// ProjectMaster mst = link.getPjtMaster();
		return link.getTypeCode();
	    }
	} catch (WTException e) {
	    Kogger.error(EDMHelper.class, e);
	    new WTException(e);
	}

	return null;
    }

    public static String getCategory(EPMDocument epm) throws WTException {
	HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);
	String category = "";

	if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
	    category = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN);
	}

	return category;
    }

    public static String getCADAppType(EPMDocument epm) throws WTException {
	HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);
	String cadAppType = "";

	if (ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE) != null) {
	    cadAppType = EDMEnumeratedTypeUtil.getCADAppType((String) ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE), Locale.KOREAN);
	}

	return cadAppType;
    }

    public static boolean checkEDMUserData(Object obj) {
	EDMUserData ud = null;

	try {
	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(EDMUserData.class, true);

	    qs.appendWhere(new SearchCondition(EDMUserData.class, "objData.key.id", SearchCondition.EQUAL, PersistenceHelper
		    .getObjectIdentifier((Persistable) obj).getId()), new int[] { idx });

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	    if (qr.hasMoreElements()) {
		return true;
	    }
	} catch (WTException e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return false;
    }

    public static EDMUserData getEDMUserData(Object obj) {
	EDMUserData ud = null;

	try {
	    long objDataId = 0;
	    long objBranchId = 0;
	    String objClassname = "";
	    // String objNumber = "";
	    String objVersion = "";

	    objDataId = PersistenceHelper.getObjectIdentifier((Persistable) obj).getId();

	    if (obj instanceof EPMDocument) {
		EPMDocument epm = (EPMDocument) obj;
		objDataId = PersistenceHelper.getObjectIdentifier(epm.getMaster()).getId();
		// objNumber = epm.getNumber();
	    }

	    if (obj instanceof Versioned) {
		Versioned v = (Versioned) obj;
		objVersion = v.getVersionIdentifier().getSeries().getValue();
		objClassname = v.getClass().getName();
		objBranchId = v.getBranchIdentifier();
	    }

	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(EDMUserData.class, true);

	    if (objDataId > 0) {
		qs.appendWhere(new SearchCondition(EDMUserData.class, "objData.key.id", SearchCondition.EQUAL, objDataId),
		        new int[] { idx });
	    }

	    // if( objNumber.trim().length() > 0 )
	    // {
	    // if( qs.getConditionCount() > 0 )
	    // {
	    // qs.appendAnd();
	    // }
	    //
	    // qs.appendWhere(new SearchCondition(EDMUserData.class,EDMUserData.OBJ_NUMBER, SearchCondition.EQUAL, objNumber.trim()), new
	    // int[]{idx});
	    // }

	    if (objVersion.trim().length() > 0) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		qs.appendWhere(new SearchCondition(EDMUserData.class, EDMUserData.OBJ_VERSION, SearchCondition.EQUAL, objVersion.trim()),
		        new int[] { idx });
	    }
	    /*
	     * if(objClassname.trim().length() > 0) { if(qs.getConditionCount() > 0) { qs.appendAnd(); }
	     * 
	     * qs.appendWhere(new SearchCondition( EDMUserData.class,EDMUserData.OBJ_CLASSNAME, SearchCondition.EQUAL, objClassname.trim()),
	     * new int[]{idx}); }
	     * 
	     * if(objBranchId > 0) { if(qs.getConditionCount() > 0) { qs.appendAnd(); }
	     * 
	     * qs.appendWhere(new SearchCondition( EDMUserData.class,EDMUserData.OBJ_BRANCH_ID, SearchCondition.EQUAL, objBranchId), new
	     * int[]{idx}); }
	     */

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
	    while (qr.hasMoreElements()) {
		Object r[] = (Object[]) qr.nextElement();
		ud = (EDMUserData) r[0];
	    }
	} catch (WTException e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return ud;
    }

    public static void addRelatedDrawings(EPMDocument model, ArrayList list) {
	try {
	    if (list == null) {
		list = new ArrayList();
	    }

	    if (!list.contains(model)) {
		list.add(model);
	    }

	    EPMDocument drawing = null;
	    Vector refedDrawings = getRelatedDrawings(model, new LatestConfigSpec());

	    for (int i = 0; i < refedDrawings.size(); i++) {
		drawing = (EPMDocument) refedDrawings.get(i);

		if (!list.contains(drawing)) {
		    list.add(drawing);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}
    }

    public static ArrayList getAssociateDocsBy(EPMDocument epm) {
	return getAssociateDocsBy(epm, false);
    }

    public static ArrayList getAssociateDocsBy(EPMDocument epm, boolean fromEcoRevise) {
	ArrayList refObjs = new ArrayList();

	try {
	    // 2010-10-28 TKLEE, 관련도면 개정은 않하는 걸로 일단 코딩함
	    // TODO TKLEE 확인 필요함.
	    if (fromEcoRevise) {
		return refObjs;
	    }

	    String category = getCategory(epm);
	    String referenceType = EDMProperties.getInstance().getReferenceType(category);

	    // 승인도/고객제출도 --> 연계도면을 관리하지 않음
	    if ("APPROVAL_DRAWING".equals(category) || "CUSTOMER_DRAWING".equals(category)) {
		return refObjs;
	    }

	    ArrayList modelsbyref = getReferencedByModels(epm, EDMHelper.REQUIRED_REFERENCE_MODEL); // 2 : 참조모델(도면과 모델만 연관하는 경우) --> 개발검토도면
	    // EPMDocument model = null;

	    for (int i = 0; i < modelsbyref.size(); i++) {
		EPMDocument model = null;
		model = (EPMDocument) ((Object[]) modelsbyref.get(i))[1];
		addRelatedDrawings(model, refObjs);
	    }

	    ArrayList reledParts = getReferencedByParts(epm, referenceType, EDMHelper.REQUIRED_STANDARD); // 1 : 대표부품
	    if ((reledParts != null) && (reledParts.size() > 0)) {
		for (int k = 0; k < reledParts.size(); k++) {
		    WTPart part = (WTPart) ((Object[]) reledParts.get(k))[1];
		    refObjs.add(part);
		    EPMDocument refedModel = getAssociatedModel(epm, part, referenceType, EDMHelper.REQUIRED_STANDARD); // 1 : 대표부품

		    if (refedModel != null) {
			addRelatedDrawings(refedModel, refObjs);
		    }
		}
	    }

	    reledParts = getReferencedByParts(epm, referenceType, EDMHelper.REQUIRED_RELATED); // 0 : 관련부품
	    if ((reledParts != null) && (reledParts.size() > 0)) {
		for (int k = 0; k < reledParts.size(); k++) {
		    WTPart part = (WTPart) ((Object[]) reledParts.get(k))[1];
		    refObjs.add(part);
		    EPMDocument refedModel = getAssociatedModel(epm, part, referenceType, EDMHelper.REQUIRED_RELATED);

		    if (refedModel != null) {
			addRelatedDrawings(refedModel, refObjs);
		    }
		}
	    }

	    Kogger.debug(EDMHelper.class, "=============> EDMHelper.java :: getAssociateDocsBy :: 관련도면 정보");
	    Kogger.debug(EDMHelper.class, "=============> EDMHelper.java :: getAssociateDocsBy :: 관련도면 갯수 :: " + refObjs.size());
	    for (int m = 0; m < refObjs.size(); m++) {
		Kogger.debug(EDMHelper.class, "=============> EDMHelper.java :: getAssociateDocsBy :: 관련도면 정보 " + (m + 1) + refObjs.get(m));
	    }
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	}

	return refObjs;
    }

    public static QueryResult getECAEpmDocLink(EPMDocument epm) throws WTException {
	return getECAEpmDocLink(epm, false);
    }

    public static QueryResult getECAEpmDocLink(EPMDocument epm, boolean bool) throws WTException {
	try {
	    long mstid = PersistenceHelper.getObjectIdentifier(epm.getMaster()).getId();
	    String version = epm.getVersionIdentifier().getSeries().getValue();

	    String columnAlias = "OID0";
	    Class moldcls = e3ps.ecm.entity.KETMoldECAEpmDocLink.class;
	    Class prodcls = e3ps.ecm.entity.KETProdECAEpmDocLink.class;

	    // 금형
	    QuerySpec qs0 = new QuerySpec();
	    int idx0 = qs0.appendClassList(moldcls, false);
	    int idx0_1 = qs0.appendClassList(EPMDocument.class, false);

	    ClassAttribute attr0 = new ClassAttribute(moldcls, "thePersistInfo.theObjectIdentifier.classname");
	    ClassAttribute attr1 = new ClassAttribute(moldcls, "thePersistInfo.theObjectIdentifier.id");
	    String alias0 = qs0.getFromClause().getAliasAt(idx0);

	    KeywordExpression ke0 = new KeywordExpression(alias0 + "." + attr0 + "||':'||" + alias0 + "." + attr1);
	    ke0.setColumnAlias(columnAlias);
	    qs0.appendSelect(ke0, new int[] { idx0 }, false);

	    qs0.appendWhere(new SearchCondition(EPMDocument.class, "masterReference.key.id", "=", mstid), new int[] { idx0_1 });

	    qs0.appendAnd();
	    qs0.appendOpenParen();// (
	    SearchCondition sc = new SearchCondition(new ClassAttribute(EPMDocument.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(moldcls, "roleAObjectRef.key.id"));
	    sc.setFromIndicies(new int[] { idx0_1, idx0 }, 0);
	    sc.setOuterJoin(0);
	    qs0.appendWhere(sc, new int[] { idx0_1, idx0 });
	    qs0.appendCloseParen();// )

	    if (bool) {
		qs0.appendAnd();
		qs0.appendWhere(new SearchCondition(moldcls, "afterVersion", "=", version), new int[] { idx0 });
	    } else {
		qs0.appendAnd();
		qs0.appendWhere(new SearchCondition(moldcls, "preVersion", "=", version), new int[] { idx0 });
	    }

	    // 제품
	    QuerySpec qs1 = new QuerySpec();
	    int idx1 = qs1.appendClassList(prodcls, false);
	    int idx1_1 = qs1.appendClassList(EPMDocument.class, false);

	    ClassAttribute attr0_1 = new ClassAttribute(prodcls, "thePersistInfo.theObjectIdentifier.classname");
	    ClassAttribute attr1_1 = new ClassAttribute(prodcls, "thePersistInfo.theObjectIdentifier.id");
	    String alias1 = qs1.getFromClause().getAliasAt(idx1);

	    KeywordExpression ke1 = new KeywordExpression(alias1 + "." + attr0_1 + "||':'||" + alias1 + "." + attr1_1);
	    ke1.setColumnAlias(columnAlias);
	    qs1.appendSelect(ke1, new int[] { idx1 }, false);

	    qs1.appendWhere(new SearchCondition(EPMDocument.class, "masterReference.key.id", "=", mstid), new int[] { idx1_1 });

	    qs1.appendAnd();
	    qs1.appendOpenParen();// (
	    sc = new SearchCondition(new ClassAttribute(EPMDocument.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(prodcls, "roleAObjectRef.key.id"));
	    sc.setFromIndicies(new int[] { idx1_1, idx1 }, 0);
	    sc.setOuterJoin(0);
	    qs1.appendWhere(sc, new int[] { idx1_1, idx1 });
	    qs1.appendCloseParen();// )

	    if (bool) {
		qs1.appendAnd();
		qs1.appendWhere(new SearchCondition(prodcls, "afterVersion", "=", version), new int[] { idx1 });
	    } else {
		qs1.appendAnd();
		qs1.appendWhere(new SearchCondition(prodcls, "preVersion", "=", version), new int[] { idx1 });
	    }

	    CompoundQuerySpec compound = new CompoundQuerySpec();
	    compound.setSetOperator(SetOperator.UNION_ALL);
	    compound.addComponent(qs0);
	    compound.addComponent(qs1);

	    return PersistenceHelper.manager.find(compound);
	} catch (Exception e) {
	    Kogger.error(EDMHelper.class, e);
	    throw new WTException(e);
	}
    }

    public static boolean isECAEpmDocLink(EPMDocument epm, boolean bool) throws WTException {
	QueryResult result = getECAEpmDocLink(epm, bool);

	if (result.hasMoreElements()) {
	    return true;
	}

	return false;
    }

    public static EPMDocumentMaster getDocPartRelatedCustomer(String partNo, boolean is3D) throws Exception {

	if (StringUtils.isEmpty(partNo)) {
	    return null;
	}

	EPMPartMatcher matcher = new EPMPartMatcher();

	PartNumberProdType epmType = PartNumberProdType.getMatchPart(partNo);

	EPMNumberProdType epmType1 = epmType.getEpmNumberType();
	String newCode1 = null;
	String epmCode = epmType.toString().replace("_", "");
	String prefix = "CU_";
	String suffix = "";
	if(is3D){
	    suffix = "_3D";
	}
	String itemWithSuffix = "";
	if (epmType1 != null) {

	    String partCode = epmType1.toString().replace("_", "");
	    newCode1 = partNo.substring(epmCode.length());
	    newCode1 = partCode + newCode1;

	    String item = prefix + newCode1;
	    itemWithSuffix = item + suffix;
	}

	System.out.println("getDocPartRelatedCustomer PartNo : " + itemWithSuffix);

	QuerySpec spec = new QuerySpec(EPMDocumentMaster.class);

	spec.appendWhere(new SearchCondition(EPMDocumentMaster.class, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, itemWithSuffix),
	        new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	EPMDocumentMaster master = null;
	if (rs.hasMoreElements()) {
	    master = (EPMDocumentMaster) rs.nextElement();
	}
	
	return master;
    }
}
