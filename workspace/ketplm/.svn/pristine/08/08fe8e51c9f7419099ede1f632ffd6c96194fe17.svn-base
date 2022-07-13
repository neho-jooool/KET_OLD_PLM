package ext.ket.part.base.service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import e3ps.common.iba.IBAUtil;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.edm.EPMLink;
import e3ps.edm.beans.EDMHelper;
import ext.ket.edm.stamping.util.ModelStrucUtil;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class RelatedEpmHandler {

    // 일반 제품
    public final static String EPM_P = "P";
    public final static String EPM_A = "A";
    public final static String EPM_C = "C";
    public final static String EPM_C_3D = "C_3D";
    public final static String EPM_E = "E";

    // 금형
    public final static String EPM_MS = "MS";
    public final static String EPM_M = "M";
    public final static String EPM_SS = "SS";
    public final static String EPM_S = "S";

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private ModelStrucUtil modelStrucUtil = new ModelStrucUtil();

    // ECO의 연계도면 전체 정보 가져오기
    public List<EPMDocument> getReferenceEPMDocsByWTPartList(List<WTPart> partList) throws Exception {

	List<EPMDocument> returnEpmDoc = new ArrayList<EPMDocument>();

	for (WTPart part : partList) {
	    returnEpmDoc.addAll(getReferenceEPMDocsByWTPart(part));
	}

	// 중복제거
	Map<String, String> epmMap = new HashMap<String, String>();
	for (int index = returnEpmDoc.size() - 1; index >= 0; index--) {
	    EPMDocument epmDoc = returnEpmDoc.get(index);
	    if (epmMap.containsKey(epmDoc.getNumber())) {
		returnEpmDoc.remove(index);
	    } else {
		epmMap.put(epmDoc.getNumber(), epmDoc.getNumber());
	    }
	}

	return returnEpmDoc;
    }

    // ///////////////////////////////////////////////
    // 도면 정보 from 부품
    // -. 부품 마스터의 모든 도면의 최신을 가져온다. - 참조도면은 제외됨
    // -. 도면이 Released면 OK, Inwork일 경우 0이 OK! <ECO 체크은 일단 빼고>, 0 아니면 No
    // ///////////////////////////////////////////////
    public List<EPMDocument> getReferenceEPMDocsByWTPart(WTPart part) throws Exception {

	List<EPMDocument> returnEpmDoc = new ArrayList<EPMDocument>();

	// -. 부품 마스터의 모든 도면의 최신을 가져온다.
	List<EPMDocument> relatedModel = new ArrayList<EPMDocument>();
	List<EPMDocument> relatedDrawing = new ArrayList<EPMDocument>();
	List<EPMDocument> appDrawing = new ArrayList<EPMDocument>();
	List<EPMDocument> cuDrawing = new ArrayList<EPMDocument>();
	List<EPMDocument> cu3DDrawing = new ArrayList<EPMDocument>();

	WTPartMaster partMast = (WTPartMaster) part.getMaster();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

	if (epmLinkList != null && epmLinkList.size() > 0) {

	    for (EPMLink link : epmLinkList) {

		EPMDocumentMaster epmMast = link.getEpmMaster();
		EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);
		// 도면이 Released면 OK, Inwork일 경우 0이 OK! <ECO 체크은 일단 빼고>, 0 아니면 No
		if (latestEpmDoc.getLifeCycleState() == State.toState("APPROVED")
		        || (latestEpmDoc.getLifeCycleState() == State.INWORK && latestEpmDoc.getVersionIdentifier().getValue().equals("0"))) {
		    if ("RELATED_MODEL".equals(link.getReferenceType())) {
			relatedModel.add(latestEpmDoc);
		    } else if ("RELATED_DRAWING".equals(link.getReferenceType())) {
			relatedDrawing.add(latestEpmDoc);
		    } else if ("APP_DRAWING".equals(link.getReferenceType())) {
			appDrawing.add(latestEpmDoc);
		    } else if ("CU_DRAWING".equals(link.getReferenceType())) {
			cuDrawing.add(latestEpmDoc);
		    } else if ("CU_3D_DRAWING".equals(link.getReferenceType())) {
			cu3DDrawing.add(latestEpmDoc);
		    }
		}
	    }
	}

	returnEpmDoc.addAll(relatedModel);
	returnEpmDoc.addAll(relatedDrawing);
	returnEpmDoc.addAll(appDrawing);
	returnEpmDoc.addAll(cuDrawing);
	returnEpmDoc.addAll(cu3DDrawing);

	return returnEpmDoc;
    }

    // ECO WTPart의 연관된 EPM 가져오기
    public Map<String, List<EPMDocument>> getRelatedEPMDocByEcoPart(WTPart wtPart, boolean isOnlyApproved) throws Exception {

	Map<String, List<EPMDocument>> retMap = new HashMap<String, List<EPMDocument>>();

	if (wtPart == null) {
	    return retMap;
	}

	if (PartUtil.isProductType(wtPart)) { // 일반제품

	    List<EPMDocument> appEpm = new ArrayList<EPMDocument>();
	    List<EPMDocument> customerEpm = new ArrayList<EPMDocument>();
	    List<EPMDocument> customer3DEpm = new ArrayList<EPMDocument>();
	    List<EPMDocument> ecadEpm = new ArrayList<EPMDocument>();
	    List<EPMDocument> prodEpm = new ArrayList<EPMDocument>();

	    WTPartMaster partMast = (WTPartMaster) wtPart.getMaster();
	    List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

	    if (epmLinkList != null && epmLinkList.size() > 0) {

		for (EPMLink link : epmLinkList) {

		    EPMDocumentMaster epmMast = link.getEpmMaster();
		    EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);
		    String referenceType = link.getReferenceType();
		    boolean ecad = link.isEcad();
		    // 도면이 Released면 OK, Inwork일 경우 0이 OK! <ECO 체크은 일단 빼고>, 0 아니면 No
		    if (!isOnlyApproved
			    || (latestEpmDoc.getLifeCycleState() == State.toState("APPROVED") || (latestEpmDoc.getLifeCycleState() == State.INWORK && latestEpmDoc
			            .getVersionIdentifier().getValue().equals("0")))) {
			if ("REF_DRAWING".equals(referenceType)) {
			    continue;
			}

			if ("APP_DRAWING".equals(link.getReferenceType())) {
			    appEpm.add(latestEpmDoc);
			} else if ("CU_DRAWING".equals(link.getReferenceType())) {
			    customerEpm.add(latestEpmDoc);
			} else if ("CU_3D_DRAWING".equals(link.getReferenceType())) {
			    customer3DEpm.add(latestEpmDoc);
			} else if (ecad) {
			    ecadEpm.add(latestEpmDoc);
			} else {

			    prodEpm.add(latestEpmDoc);

			    // 2D DRW를 찾아서 넣기
			    if ("RELATED_MODEL".equals(link.getReferenceType())
				    && "PROE".equals(epmMast.getAuthoringApplication().toString())) {
				EPMDocument epm2D = modelStrucUtil.getLatest2DBy3D(CommonUtil.getOIDString(latestEpmDoc),
				        latestEpmDoc.getNumber());
				if (epm2D != null) {
				    if (!isOnlyApproved
					    || (epm2D.getLifeCycleState() == State.toState("APPROVED") || (epm2D.getLifeCycleState() == State.INWORK && epm2D
					            .getVersionIdentifier().getValue().equals("0")))) {
					prodEpm.add(epm2D);
				    }
				}
			    }

			}
		    }
		}
	    }

	    retMap.put(EPM_P, prodEpm);
	    retMap.put(EPM_A, appEpm);
	    retMap.put(EPM_C, customerEpm);
	    retMap.put(EPM_E, ecadEpm);
	    retMap.put(EPM_C_3D, customer3DEpm);

	} else { // 금형 제품

	    List<EPMDocument> moldSetEpm = new ArrayList<EPMDocument>();
	    List<EPMDocument> moldEpm = new ArrayList<EPMDocument>();
	    List<EPMDocument> pressSetEpm = new ArrayList<EPMDocument>();
	    List<EPMDocument> pressEpm = new ArrayList<EPMDocument>();

	    WTPartMaster partMast = (WTPartMaster) wtPart.getMaster();
	    List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

	    if (epmLinkList != null && epmLinkList.size() > 0) {

		for (EPMLink link : epmLinkList) {

		    EPMDocumentMaster epmMast = link.getEpmMaster();
		    EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);
		    String referenceType = link.getReferenceType();
		    // boolean ecad = link.isEcad();
		    String cadCategory = IBAUtil.getAttrValue(latestEpmDoc, EDMHelper.IBA_CAD_CATEGORY);
		    // 도면이 Released면 OK, Inwork일 경우 0이 OK! <ECO 체크은 일단 빼고>, 0 아니면 No
		    if (!isOnlyApproved
			    || (latestEpmDoc.getLifeCycleState() == State.toState("APPROVED") || (latestEpmDoc.getLifeCycleState() == State.INWORK && latestEpmDoc
			            .getVersionIdentifier().getValue().equals("0")))) {
			if ("REF_DRAWING".equals(referenceType)) {
			    continue;
			}

			// 사출금형도면, 프레스금형도면, 사출금형SET도면, 프레스금형SET도면
			if ("사출금형SET도면".equals(cadCategory)) {
			    moldSetEpm.add(latestEpmDoc);
			} else if ("사출금형도면".equals(cadCategory)) {
			    moldEpm.add(latestEpmDoc);
			} else if ("프레스금형SET도면".equals(cadCategory)) {
			    pressSetEpm.add(latestEpmDoc);
			} else if ("프레스금형도면".equals(cadCategory)) {
			    pressEpm.add(latestEpmDoc);
			}
		    }
		}
	    }

	    retMap.put(EPM_MS, moldSetEpm);
	    retMap.put(EPM_M, moldEpm);
	    retMap.put(EPM_SS, pressSetEpm);
	    retMap.put(EPM_S, pressEpm);

	}

	return retMap;
    }

    // ECO WTPart의 연관된 EPM 가져오기
    public List<EPMDocumentMaster> getRelEPMDocByEcoPart(WTPart wtPart) throws Exception {

	List<EPMDocumentMaster> retList = new ArrayList<EPMDocumentMaster>();

	if (wtPart == null) {
	    return retList;
	}

	if (PartUtil.isProductType(wtPart)) { // 일반제품

	    WTPartMaster partMast = (WTPartMaster) wtPart.getMaster();
	    List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);

	    if (epmLinkList != null && epmLinkList.size() > 0) {

		for (EPMLink link : epmLinkList) {

		    EPMDocumentMaster epmMast = link.getEpmMaster();
		    String referenceType = link.getReferenceType();
		    boolean ecad = link.isEcad();
		    // 도면이 Released면 OK, Inwork일 경우 0이 OK! <ECO 체크은 일단 빼고>, 0 아니면 No
		    if ("REF_DRAWING".equals(referenceType)) {
			continue;
		    }

		    retList.add(epmMast);
		    // 2D DRW를 찾아서 넣기
		    if ("RELATED_MODEL".equals(link.getReferenceType()) && "PROE".equals(epmMast.getAuthoringApplication().toString())) {
			EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);
			EPMDocument epm2D = modelStrucUtil.getLatest2DBy3D(CommonUtil.getOIDString(latestEpmDoc), latestEpmDoc.getNumber());
			if (epm2D != null) {
			    retList.add((EPMDocumentMaster) epm2D.getMaster());
			}
		    }
		}
	    }

	} else { // 금형 제품

	    WTPartMaster partMast = (WTPartMaster) wtPart.getMaster();
	    List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast);
	    if (epmLinkList != null && epmLinkList.size() > 0) {
		for (EPMLink link : epmLinkList) {
		    EPMDocumentMaster epmMast = link.getEpmMaster();
		    String referenceType = link.getReferenceType();
		    if ("REF_DRAWING".equals(referenceType)) {
			continue;
		    }
		    retList.add(epmMast);
		}
	    }
	}

	return retList;
    }

    // ECO EPM의 연관된 WTPartMaster 가져오기
    public List<WTPartMaster> getRelPartByEcoPMDoc(EPMDocument epmDoc) throws Exception {

	List<WTPartMaster> retList = new ArrayList<WTPartMaster>();

	if (epmDoc == null) {
	    return retList;
	}

	EPMDocumentMaster epmDocMast = (EPMDocumentMaster) epmDoc.getMaster();
	List<EPMLink> epmLinkList = query.queryForListLinkByRoleA(EPMDocumentMaster.class, EPMLink.class, epmDocMast);

	if (epmLinkList != null && epmLinkList.size() > 0) {

	    for (EPMLink link : epmLinkList) {

		WTPartMaster partMast = link.getPartMaster();
		String referenceType = link.getReferenceType();

		if ("REF_DRAWING".equals(referenceType)) {
		    continue;
		}

		retList.add(partMast);
	    }
	}

	return retList;
    }

}