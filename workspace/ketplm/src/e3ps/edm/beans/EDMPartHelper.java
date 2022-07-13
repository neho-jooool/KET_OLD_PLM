package e3ps.edm.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.org.WTPrincipal;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.Mastered;
import wt.vc.VersionControlHelper;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.edm.EPMLink;
import e3ps.edm.PartToEPMLink;
import e3ps.edm.util.EDMProperties;
import ext.ket.edm.stamping.util.ModelStrucUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class EDMPartHelper implements wt.method.RemoteAccess, java.io.Serializable {
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static ArrayList getReferenceDocs(WTPart part) {
	ArrayList result = new ArrayList();

	try {
	    String mt = EDMProperties.getInstance().getReferenceTypeForModel(null);
	    ArrayList docs = EDMHelper.getReferenceDocs(part, null, -1);

	    for (int i = 0; i < docs.size(); i++) {
		Object[] objs = (Object[]) docs.get(i);
		String referenceType = "";

		if (objs[0] instanceof EPMLink) {
		    EPMLink link = (EPMLink) objs[0];
		    referenceType = (link.getReferenceType() == null) ? "" : link.getReferenceType();
		} else if (objs[0] instanceof PartToEPMLink) {
		    PartToEPMLink link = (PartToEPMLink) objs[0];
		    referenceType = (link.getReferenceType() == null) ? "" : link.getReferenceType();
		}

		if (referenceType.equals(mt)) {
		    continue;
		}

		result.add(objs);
		// result.add((EPMDocument)objs[1]);
	    }
	} catch (Exception e) {
	    Kogger.error(EDMPartHelper.class, e);
	}

	return result;
    }

    public static void setReferencedBy(String partNumber, String number) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { String.class, String.class };
	    Object args[] = new Object[] { partNumber, number };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("setReferencedBy", EDMPartHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPartHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPartHelper.class, e);
		throw new WTException(e);
	    }

	    return;
	}

	try {
	    WTPart part = e3ps.edm.util.VersionHelper.getLatestRevisionPart(number, "Design");

	    if (part == null) {
		throw new WTException("'" + partNumber + "' 부품이 없습니다.");
	    }

	    setReferencedBy(part, number);
	} catch (Exception e) {
	    Kogger.error(EDMPartHelper.class, e);
	    throw new WTException(e);
	}
    }

    /*
     * param part : 금형부품 WTPart 객체 param number : 대표부품번호(Excel 데이터 기준.. 금형 부품이 존재하는 것만 관련 정보 생성..)
     */
    public static void setReferencedBy(WTPart part, String number, String cadno) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { WTPart.class, String.class, String.class };
	    Object args[] = new Object[] { part, number, cadno };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("setReferencedBy", EDMPartHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPartHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPartHelper.class, e);
		throw new WTException(e);
	    }

	    return;
	}

	// 금형 부품 WTPart 객체가 없을 경우 그냥 리턴.. 관련 정보 생성 안함
	if (part == null) {
	    return;
	}

	try {

	    Kogger.debug(EDMPartHelper.class, "입력 되어지는 대표 부품 번호 == " + number);
	    Kogger.debug(EDMPartHelper.class, "CADNO == " + cadno);
	    if (cadno == null)
		cadno = "";

	    if ("na".equals(cadno)) {
		// 대표 부품 있다 판단
		number = number.toUpperCase();
	    } else if (cadno == null || cadno.trim().length() == 0) {
		// 대표 부품 없다 판단..
		Kogger.debug(EDMPartHelper.class, "대표 부품 없을시 여기 실행..");
		if (number == null || number.trim().length() == 0) {
		    number = part.getNumber().toUpperCase();
		}
	    }

	    /*
	     * if( (number == null) || (number.trim().length() == 0) ) { //대표부품 번호가 없을시 금형 부품의 번호를 가져옴.. number = part.getNumber();
	     * Kogger.debug(EDMPartHelper.class, "대표부품 번호가 없을시 가져오는 Number == " + number); //Kogger.debug(EDMPartHelper.class,
	     * "IS Number null return part.getNumber() == " + part.getNumber()); //return; }
	     * 
	     * number = number.toUpperCase(); //2T421000-L002
	     */

	    Kogger.debug(EDMPartHelper.class, "최종 넘버..NUMBER == " + number);

	    boolean isPrimary = number.equalsIgnoreCase(part.getNumber());

	    ArrayList docs = getDocs(number);

	    // 번호에 해당하는 도면이 없는 경우...
	    if (docs.size() == 0) {
		return;
	    }

	    EDMProperties edmProperties = EDMProperties.getInstance();

	    EPMDocument epm = null;
	    EPMDocument model = null;

	    for (int i = 0; i < docs.size(); i++) {
		EPMDocument e = (EPMDocument) docs.get(i);

		/*
	         * //승인 상태 체크 if(!edmProperties.isReleasedState(e.getLifeCycleState().toString())) { continue; }
	         */

		if (!edmProperties.isAppTypeByPLM(e.getOwnerApplication().toString())) {
		    model = e; // 3D Mode 정보
		} else {
		    epm = e; // 2D Drawing 정보
		}
	    }

	    if ((epm == null) && (model == null)) {
		return;
	    }

	    if ((epm == null) && (model != null)) {
		epm = model;
		model = null;
	    }

	    // 관련 링크 생성...
	    String category = EDMHelper.getCategory(epm);

	    // '도면종류'가 없는 경우 return
	    if (category.length() == 0) {
		return;
	    }

	    // 승인된 도면이 아닌 경우 return
	    if (!edmProperties.isReleasedState(epm.getLifeCycleState().toString())) {
		return;
	    }

	    String referenceType = edmProperties.getReferenceType(category);
	    int required = (isPrimary == true) ? EDMHelper.REQUIRED_STANDARD : EDMHelper.REQUIRED_RELATED;

	    Kogger.debug(EDMPartHelper.class, "part == " + part);
	    Kogger.debug(EDMPartHelper.class, "epm == " + epm);
	    Kogger.debug(EDMPartHelper.class, "model == " + model);
	    Kogger.debug(EDMPartHelper.class, "referenceType == " + referenceType);
	    Kogger.debug(EDMPartHelper.class, "requirerd == " + required);
	    EDMServiceHelper.setEPMReference(part, epm, model, referenceType, required);

	    if (model != null) {
		EDMServiceHelper.syncEPMModelData(epm);

		String state = epm.getLifeCycleState().toString();

		if (!(state.equals(model.getLifeCycleState().toString()))) {
		    // LifeCycleHelper.service.setLifeCycleState(model, State.toState(state), false);
		    Kogger.debug(EDMPartHelper.class, "@@@@@@@@@@@@@@@@ getLifeCycleName@@" + model.getLifeCycleName());
		    Kogger.debug(EDMPartHelper.class, "@@@@@@@@@@@@@@@@ getLifeCycleState@@" + model.getLifeCycleState());
		    Kogger.debug(EDMPartHelper.class, "@@@@@@@@@@@@@@@@ getLifeCycleTemplate@@" + model.getLifeCycleTemplate());
		    Kogger.debug(EDMPartHelper.class, "@@@@@@@@@@@@@@@@ 2D Drawing Status@@" + state + "@@");
		    LifeCycleHelper.service.setLifeCycleState(model, State.toState(state));
		    Kogger.debug(EDMPartHelper.class, "@@@@@@@@@@@@@@@@ Changed model Status@@" + model.getLifeCycleState() + "@@");
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EDMPartHelper.class, e);
	    throw new WTException(e);
	}

	return;
    }

    /*
     * param part : 금형부품 WTPart 객체 param number : 대표부품번호(Excel 데이터 기준.. 금형 부품이 존재하는 것만 관련 정보 생성..)
     */
    public static void setReferencedBy(WTPart part, String number) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { WTPart.class, String.class };
	    Object args[] = new Object[] { part, number };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("setReferencedBy", EDMPartHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(EDMPartHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(EDMPartHelper.class, e);
		throw new WTException(e);
	    }

	    return;
	}

	// 금형 부품 WTPart 객체가 없을 경우 그냥 리턴.. 관련 정보 생성 안함
	if (part == null) {
	    return;
	}

	try {

	    Kogger.debug(EDMPartHelper.class, "입력 되어지는 대표 부품 번호 == " + number);

	    if ((number == null) || (number.trim().length() == 0)) {
		// 대표부품 번호가 없을시 금형 부품의 번호를 가져옴..
		number = part.getNumber();
		Kogger.debug(EDMPartHelper.class, "대표부품 번호가 없을시 가져오는 Number == " + number);
		// Kogger.debug(EDMPartHelper.class, "IS Number null return part.getNumber() == " + part.getNumber());
		// return;
	    }

	    number = number.toUpperCase(); // 2T421000-L002

	    Kogger.debug(EDMPartHelper.class, "NUMBER == " + number);

	    boolean isPrimary = number.equalsIgnoreCase(part.getNumber());

	    ArrayList docs = getDocs(number);
	    Kogger.debug(EDMPartHelper.class, "ArryList docs Size == " + docs.size());

	    // 번호에 해당하는 도면이 없는 경우...
	    if (docs.size() == 0) {
		return;
	    }

	    EDMProperties edmProperties = EDMProperties.getInstance();

	    EPMDocument epm = null;
	    EPMDocument model = null;

	    for (int i = 0; i < docs.size(); i++) {
		EPMDocument e = (EPMDocument) docs.get(i);

		/*
	         * //승인 상태 체크 if(!edmProperties.isReleasedState(e.getLifeCycleState().toString())) { continue; }
	         */

		if (!edmProperties.isAppTypeByPLM(e.getOwnerApplication().toString())) {
		    model = e; // 3D Mode 정보
		} else {
		    epm = e; // 2D Drawing 정보
		}
	    }

	    if ((epm == null) && (model == null)) {
		return;
	    }

	    if ((epm == null) && (model != null)) {
		epm = model;
		model = null;
	    }

	    // 관련 링크 생성...
	    String category = EDMHelper.getCategory(epm);

	    // '도면종류'가 없는 경우 return
	    if (category.length() == 0) {
		return;
	    }

	    // 승인된 도면이 아닌 경우 return
	    if (!edmProperties.isReleasedState(epm.getLifeCycleState().toString())) {
		return;
	    }

	    String referenceType = edmProperties.getReferenceType(category);
	    int required = (isPrimary == true) ? EDMHelper.REQUIRED_STANDARD : EDMHelper.REQUIRED_RELATED;

	    Kogger.debug(EDMPartHelper.class, "part == " + part);
	    Kogger.debug(EDMPartHelper.class, "epm == " + epm);
	    Kogger.debug(EDMPartHelper.class, "model == " + model);
	    Kogger.debug(EDMPartHelper.class, "referenceType == " + referenceType);
	    Kogger.debug(EDMPartHelper.class, "requirerd == " + required);
	    EDMServiceHelper.setEPMReference(part, epm, model, referenceType, required);

	    if (model != null) {
		EDMServiceHelper.syncEPMModelData(epm);

		String state = epm.getLifeCycleState().toString();

		if (!(state.equals(model.getLifeCycleState().toString()))) {
		    LifeCycleHelper.service.setLifeCycleState(model, State.toState(state), false);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EDMPartHelper.class, e);
	    throw new WTException(e);
	}

	return;
    }

    private static ArrayList getDocs(String number) throws WTException {
	ArrayList list = new ArrayList();

	try {
	    /*
	     * HashMap map = new HashMap(); map.put("className", EPMDocument.class.getName()); map.put("number", number+"*");
	     * 
	     * Object[] oo = null;
	     * 
	     * QueryResult result = EDMQueryHelper.find(map); while(result.hasMoreElements()) { oo = (Object[])result.nextElement();
	     * //String nr = (String)oo[1]; //String nm = (String)oo[2]; //String nv = (String)oo[3]; //String estate = (String)oo[4];
	     * //String noid = (String)oo[0];
	     * 
	     * list.add((new ReferenceFactory()).getReference((String)oo[0]).getObject()); }
	     */

	    Kogger.debug(EDMPartHelper.class, "도면을 찾기위해 입력된 NUMBER == " + number);
	    QuerySpec qs = new QuerySpec();

	    int idx = qs.appendClassList(EPMDocumentMaster.class, true);
	    Kogger.debug(EDMPartHelper.class, "Number Equal EPMDocumentMaster Check == " + number);
	    qs.appendWhere(new SearchCondition(EPMDocumentMaster.class, "number", SearchCondition.EQUAL, number + "_DWG"),
		    new int[] { idx });
	    qs.appendOr();
	    qs.appendWhere(new SearchCondition(EPMDocumentMaster.class, "number", SearchCondition.EQUAL, number + "_PLS"),
		    new int[] { idx });
	    qs.appendOr();
	    qs.appendWhere(new SearchCondition(EPMDocumentMaster.class, "number", SearchCondition.EQUAL, number + "_PRT"),
		    new int[] { idx });

	    Object[] oo = null;
	    QueryResult result = PersistenceHelper.manager.find(qs);

	    while (result.hasMoreElements()) {
		oo = (Object[]) result.nextElement();
		list.add(e3ps.edm.util.VersionHelper.getLatestRevision(EPMDocument.class, (Mastered) oo[0]));
	    }
	} catch (Exception e) {
	    Kogger.error(EDMPartHelper.class, e);
	    throw new WTException(e);
	}

	return list;
    }

    // ///////////////////////////////////////////////
    // 도면 정보 from 부품
    // -. 부품 마스터의 모든 도면의 최신을 가져온다.
    // -. 부품 리비전의 모든 도면을 가져온다. [도면 번호당 Max]
    // -. 부품 마스터에만 있는 도면을 추가한다.
    // ///////////////////////////////////////////////
    public static List<EPMDocument> getReferenceEPMDocsByWTPart(WTPart part) throws Exception {

	ModelStrucUtil modelStrucUtil = new ModelStrucUtil();

	// 해당부품과 형상관리로 연결된 관련 도면을 가져온다.
	List<EPMDocument> edmList = getInnerReferenceEPMDocsByWTPart(part, modelStrucUtil);

	String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
	// 제품일 경우 동일 No의 반제품 도면을 가져온다.
	if ("F".equals(partType)) {
	    // 상위 제품의 최신 리비전을 가져온다.
	    String prodPartNumber = "H" + part.getNumber();
	    Kogger.debug(EDMPartHelper.class, "#형상관리 prodPartNumber:" + prodPartNumber);
	    WTPart prodPart = PartBaseHelper.service.getLatestPart(prodPartNumber);
	    if (prodPart != null) {
		Kogger.debug(EDMPartHelper.class, "#형상관리 find prodPartNumber:" + prodPart.getNumber());
		List<EPMDocument> edmParentList = getInnerReferenceEPMDocsByWTPart(prodPart, modelStrucUtil);
		for (int k = edmParentList.size() - 1; k >= 0; k--) {
		    EPMDocument paretnEpmDoc = edmParentList.get(k);
		    for (EPMDocument epmDoc : edmList) {
			if (epmDoc.getNumber().equals(paretnEpmDoc.getNumber())) {
			    edmParentList.remove(paretnEpmDoc);
			    break;
			}
		    }
		}

		edmList.addAll(edmParentList);
	    }
	}

	return edmList;
    }

    public static List<EPMDocument> getInnerReferenceEPMDocsByWTPart(WTPart part, ModelStrucUtil modelStrucUtil) throws Exception {

	List<EPMDocument> returnEpmDoc = new ArrayList<EPMDocument>();

	// -. 부품 마스터의 모든 도면의 최신을 가져온다.
	List<EPMDocument> relatedModel = new ArrayList<EPMDocument>();
	List<EPMDocument> relatedDrawing = new ArrayList<EPMDocument>();
	List<EPMDocument> appDrawing = new ArrayList<EPMDocument>();
	List<EPMDocument> cuDrawing = new ArrayList<EPMDocument>();
	List<EPMDocument> refDrawing = new ArrayList<EPMDocument>();

	WTPartMaster partMast = (WTPartMaster) part.getMaster();
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

	if (epmLinkList != null && epmLinkList.size() > 0) {

	    for (EPMLink link : epmLinkList) {

		EPMDocumentMaster epmMast = link.getEpmMaster();
		EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);
		if ("RELATED_MODEL".equals(link.getReferenceType())) {
		    relatedModel.add(latestEpmDoc);
		    // 2D DRW를 찾아서 넣기
		    if ("RELATED_MODEL".equals(link.getReferenceType()) && "PROE".equals(epmMast.getAuthoringApplication().toString())) {
			EPMDocument epm2D = modelStrucUtil.getLatest2DBy3D(CommonUtil.getOIDString(latestEpmDoc), latestEpmDoc.getNumber());
			if (epm2D != null) {
			    relatedModel.add(epm2D);
			}
		    }
		} else if ("RELATED_DRAWING".equals(link.getReferenceType())) {
		    relatedDrawing.add(latestEpmDoc);
		} else if ("APP_DRAWING".equals(link.getReferenceType())) {
		    appDrawing.add(latestEpmDoc);
		} else if ("CU_DRAWING".equals(link.getReferenceType())) {
		    cuDrawing.add(latestEpmDoc);
		} else if ("REF_DRAWING".equals(link.getReferenceType())) {
		    refDrawing.add(latestEpmDoc);
		}
	    }
	}

	// -. 부품 리비전의 모든 도면을 가져온다.
	List<EPMDocument> relatedModel_V = new ArrayList<EPMDocument>();
	List<EPMDocument> relatedDrawing_V = new ArrayList<EPMDocument>();
	List<EPMDocument> appDrawing_V = new ArrayList<EPMDocument>();
	List<EPMDocument> cuDrawing_V = new ArrayList<EPMDocument>();
	List<EPMDocument> refDrawing_V = new ArrayList<EPMDocument>();

	QueryResult queryResult = getEPMDocAllBypartVer(part);
	Map<String, PartToEPMLink> verLinkMap = new HashMap<String, PartToEPMLink>();
	Map<String, EPMDocument> verEpmMap = new HashMap<String, EPMDocument>();
	while (queryResult.hasMoreElements()) {
	    Object[] objs = (Object[]) queryResult.nextElement();
	    PartToEPMLink link = (PartToEPMLink) objs[1];
	    EPMDocument verEpmDoc = link.getEpm();
	    EPMDocument latestEpmDoc = (EPMDocument) VersionControlHelper.getLatestIteration(verEpmDoc, false);
	    EPMDocumentMaster epmMast = (EPMDocumentMaster) verEpmDoc.getMaster();
	    if (verLinkMap.containsKey(epmMast.getNumber())) {
		EPMDocument mapEpmDoc = verEpmMap.get(epmMast.getNumber());
		if (mapEpmDoc.getVersionIdentifier().getSeries().greaterThan(latestEpmDoc.getVersionIdentifier().getSeries())) {

		} else {
		    verLinkMap.put(epmMast.getNumber(), link);
		    verEpmMap.put(epmMast.getNumber(), latestEpmDoc);
		}

	    } else {
		verLinkMap.put(epmMast.getNumber(), link);
		verEpmMap.put(epmMast.getNumber(), latestEpmDoc);
	    }
	}

	Iterator it = verLinkMap.keySet().iterator();
	while (it.hasNext()) {
	    String epmDocNumber = (String) it.next();
	    PartToEPMLink link = verLinkMap.get(epmDocNumber);
	    EPMDocument latestEpmDoc = verEpmMap.get(epmDocNumber);
	    if ("RELATED_MODEL".equals(link.getReferenceType())) {
		relatedModel_V.add(latestEpmDoc);
	    } else if ("RELATED_DRAWING".equals(link.getReferenceType())) {
		relatedDrawing_V.add(latestEpmDoc);
	    } else if ("APP_DRAWING".equals(link.getReferenceType())) {
		appDrawing_V.add(latestEpmDoc);
	    } else if ("CU_DRAWING".equals(link.getReferenceType())) {
		cuDrawing_V.add(latestEpmDoc);
	    } else if ("REF_DRAWING".equals(link.getReferenceType())) {
		refDrawing_V.add(latestEpmDoc);
	    }
	}

	// -. 부품 마스터에만 있는 도면을 추가한다.
	checkMastRev(relatedModel, relatedModel_V);
	checkMastRev(relatedDrawing, relatedDrawing_V);
	checkMastRev(appDrawing, appDrawing_V);
	checkMastRev(cuDrawing, cuDrawing_V);
	checkMastRev(refDrawing, refDrawing_V);

	// 최종으로 add
	addMastRev(returnEpmDoc, relatedModel, relatedModel_V);
	addMastRev(returnEpmDoc, relatedDrawing, relatedDrawing_V);
	addMastRev(returnEpmDoc, appDrawing, appDrawing_V);
	addMastRev(returnEpmDoc, cuDrawing, cuDrawing_V);
	addMastRev(returnEpmDoc, refDrawing, refDrawing_V);

	return returnEpmDoc;

    }

    private static void checkMastRev(List<EPMDocument> mast, List<EPMDocument> ver) {
	for (EPMDocument epmDoc_V : ver) {
	    String epmNo_V = epmDoc_V.getNumber();
	    for (EPMDocument epmDoc : mast) {
		String epmNo = epmDoc.getNumber();
		if (epmNo.equals(epmNo_V)) {
		    mast.remove(epmDoc);
		    break;
		}
	    }
	}
    }

    private static void addMastRev(List<EPMDocument> ret, List<EPMDocument> mast, List<EPMDocument> ver) {
	ret.addAll(ver);
	ret.addAll(mast);
    }

    private static QueryResult getEPMDocAllBypartVer(WTPart objectRoleB) throws Exception {

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	SessionHelper.manager.setAdministrator();

	QueryResult queryResult = null;

	try {

	    QuerySpec query = new QuerySpec();
	    query.setAdvancedQueryEnabled(true);

	    int indexA = query.appendClassList(EPMDocument.class, true);
	    int indexLink = query.appendClassList(PartToEPMLink.class, true);
	    int indexB = query.appendClassList(WTPart.class, true);

	    query.appendJoin(indexLink, "roleAObject", indexA);
	    query.appendJoin(indexLink, "roleBObject", indexB);

	    if (objectRoleB == null) {
		throw new Exception("Query Instance all null Exception");
	    }

	    // if (objectRoleA != null)
	    if (query.getConditionCount() > 0)
		query.appendAnd();

	    query.appendWhere(new SearchCondition(PartToEPMLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL,
		    VersionControlHelper.getBranchIdentifier(objectRoleB)), new int[] { indexLink });

	    Kogger.debug(EDMPartHelper.class, "## REQUEST Query:" + query.toString());

	    queryResult = PersistenceHelper.manager.find(query);

	} catch (Exception e) {
	    Kogger.debug(EDMPartHelper.class, ExceptionUtils.getStackTrace(e));
	    throw e;
	} finally {
	    SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	}

	return queryResult;
    }

}
