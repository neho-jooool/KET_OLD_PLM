package ext.ket.part.base.service.internal.associate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTPrincipal;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.vc.Iterated;
import wt.vc.VersionControlHelper;
import wt.vc.VersionReference;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.edm.EPMLink;
import e3ps.edm.PartToEPMLink;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import ext.ket.edm.cad2bom.service.internal.EpmInfoUpadator;
import ext.ket.edm.stamping.util.ModelStrucUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryBStrategy;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartEpmRelation extends AbsAssociator implements AssociateEpmDoc {

	public static final int TWOD_REPRESENT_PART_TYPE = 0;
	public static final String TWOD_REFERENCE_TYPE = "RELATED_DRAWING";

	private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

	public void associateCreate(String partOid, String epmOid) throws Exception {

		if (StringUtils.isNotEmpty(partOid) && StringUtils.isNotEmpty(epmOid)) {

			WTPart part = (WTPart) CommonUtil.getObject(partOid);
			EPMDocument epmDocument = (EPMDocument) CommonUtil.getObject(epmOid);
			associateCreate(part, epmDocument);
		}
	}

	// CAD2BOM 생성시 호출 - 3D에 대해서만임.
	@Override
	public void associateCreate(WTPart part, EPMDocument epmDocument) throws Exception {
		String referType = "RELATED_MODEL";
		HashMap ibaValues = EDMAttributes.getIBAValues(epmDocument, Locale.KOREAN);

		String cateory = "";
		if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
			cateory = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY), Locale.KOREAN);
		}
		if ("CUSTOMER_DRAWING".equals(cateory)) {
			referType = "CU_DRAWING";
		}
		associateCreate(part, epmDocument, false, 1, referType, false);
		// 2D를 찾아서 붙여주어야 한다.

		// OOTB의 것을 활용 - View 화면에는 이미 추가됨, ECO 연관도면, 부품조회 형상관리 추가됨
		// OOTB를 사용할 경우 3D의 연결된 2D를 찾아와서 처리한다.
		if ("PROE".equals(epmDocument.getAuthoringApplication().toString())) {

			ModelStrucUtil modelStrucUtil = new ModelStrucUtil();
			String epm3dMastOid = CommonUtil.getOIDString(epmDocument);
			String epm3DNo = epmDocument.getNumber();
			EPMDocument epmDocument2d = modelStrucUtil.getLatest2DBy3D(epm3dMastOid, epm3DNo);

			if (epmDocument2d != null) {

				EPMDocumentMaster epmMaster = (EPMDocumentMaster) epmDocument2d.getMaster();
				// EPMLink를 생성해준다.
				// EpmInfoUpadator updator = new EpmInfoUpadator();
				// updator.updateEpmInfo((WTPartMaster) part.getMaster(), epmMaster, part, epmDocument2d);
				EPMDocument epm2d = PartBaseHelper.service.getLatestEPM(epmMaster);
				// 2D의 경우는 대표부품이 아닌 0을 입력한다.
				associateCreate(part, epm2d, false, PartEpmRelation.TWOD_REPRESENT_PART_TYPE, PartEpmRelation.TWOD_REFERENCE_TYPE, false);
			}
		}
	}

	// 금형 부품리스트 및 BOM Excel Upload 시점에 사용함.
	public void associateCreate(WTPart part, EPMDocument epmDocument, int required, String referType) throws Exception {
		associateCreate(part, epmDocument, false, required, referType, false);
	}

	public void associateCreate(WTPart part, EPMDocument epmDocument, boolean isEcad, int required, String referType, boolean userServerHelper) throws Exception {

		if (part == null || epmDocument == null)
			return;

		WTPartMaster partMast = (WTPartMaster) part.getMaster();
		EPMDocumentMaster epmMast = (EPMDocumentMaster) epmDocument.getMaster();

		// 신규일 경우
		createNewRelation(partMast, epmMast, part, epmDocument, isEcad, required, referType, userServerHelper, false);
	}

	// 부품 수정
	@Override
	public void associateUpdate(WTPart part, EPMDocument epmDocument) throws Exception {

	}

	// 부품 삭제 - 개정 or Mast
	@Override
	public void deAssociate(WTPart part) throws Exception {

		QueryResult queryResult = getEPMDocAllBypartVer(part);
		while (queryResult.hasMoreElements()) {
			Object[] objs = (Object[]) queryResult.nextElement();
			PartToEPMLink link = (PartToEPMLink) objs[1];
			PersistenceHelper.manager.delete(link);
		}

	}

	// 부품 개정
	// PartToEPMLink : 도면은 ServiceEventListenerAdapter 후처리 검토
	// 3D Relation Relation(CAD2BOM 관련) 처리 : 부품 개정시에 3D의 Relation만 걸어 줌
	@Override
	public void reviseAssociate(WTPart beforePart, WTPart afterPart) throws Exception {

		Map<String, EPMDocument> map = new HashMap<String, EPMDocument>();
		QueryResult queryResult = getEPMDocAllBypartVer(beforePart, 1, "RELATED_MODEL");
		if (queryResult != null && queryResult.size() > 0) {
			while (queryResult.hasMoreElements()) {

				Object[] objs = (Object[]) queryResult.nextElement();
				EPMDocument epmRevTempDoc = (EPMDocument) objs[0];
				map.put(epmRevTempDoc.getVersionIdentifier().getValue(), epmRevTempDoc);

			}

			Iterator it = map.keySet().iterator();
			int k = 0;
			while (it.hasNext()) {
				String verStr = (String) it.next();
				int ver = Integer.parseInt(verStr);

				if (ver > k)
					k = ver;
			}

			EPMDocument doc = map.get(k + "");
			if (doc != null) {
				createNewVerRelation((WTPartMaster) afterPart.getMaster(), (EPMDocumentMaster) doc.getMaster(), afterPart, doc, false, 1, "RELATED_MODEL", false, false);
			}
		}

	}

	// 부품 복사 생성
	// 부품 복사 생성시점에 로직 필요함. - 전체 copy - 다품 1도
	@Override
	public void associateCreateCopy(WTPart beforePart, WTPart afterPart) throws Exception {

		if (beforePart == null || afterPart == null)
			return;

		WTPartMaster oldPartMast = (WTPartMaster) beforePart.getMaster();
		WTPartMaster newPartMast = (WTPartMaster) afterPart.getMaster();

		List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, oldPartMast);
		for (EPMLink epmLink : epmLinkList) {
			EPMDocumentMaster epmTempMast = (EPMDocumentMaster) epmLink.getEpmMaster();
			EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmTempMast);
			saveNewMastRelation(newPartMast, epmTempMast, afterPart, latestEpmDoc, epmLink.isEcad(), epmLink.getRequired(), epmLink.getReferenceType(), false, false);
		}

		QueryResult queryResult = getEPMDocAllBypartVer(beforePart);
		while (queryResult.hasMoreElements()) {
			Object[] objs = (Object[]) queryResult.nextElement();
			PartToEPMLink link = (PartToEPMLink) objs[1];
			EPMDocument tempEpmDoc = link.getEpm();
			savePartToEPMLink(afterPart, tempEpmDoc, link.isEcad(), link.getRequired(), link.getReferenceType(), false);
		}

	}

	private void createNewRelation(WTPartMaster partMast, EPMDocumentMaster epmMast, WTPart part, EPMDocument epmDocument, boolean isEcad, int required, String referType,
			boolean userServerHelper, boolean epmInworkOnly) throws Exception {

		if (part == null || epmDocument == null)
			return;

		// EPMDocumentMaster.class,
		final String _referType = referType;
		final int _required = required;
		List<EPMLink> epmLinkList = query.queryForListLinkByRoleB(EPMLink.class, WTPartMaster.class, partMast, new QueryBStrategy() {

			@Override
			public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

				if (query.getConditionCount() > 0)
					query.appendAnd();

				query.appendWhere(new SearchCondition(classLink, EPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, _referType), new int[] { indexLink });

				if (query.getConditionCount() > 0)
					query.appendAnd();

				query.appendWhere(new SearchCondition(classLink, EPMLink.REQUIRED, SearchCondition.EQUAL, _required), new int[] { indexLink });

				// if (query.getConditionCount() > 0)
				// query.appendAnd();
				//
				// query.appendWhere(new SearchCondition(classLink, EPMLink.ECAD, SearchCondition.EQUAL, false), new int[] { indexLink });

			}
		});

		if (epmLinkList == null || epmLinkList.size() == 0) {
			// 신규일 경우
			saveNewMastRelation(partMast, epmMast, part, epmDocument, isEcad, required, referType, userServerHelper, true);
			createNewVerRelation(partMast, epmMast, part, epmDocument, isEcad, required, referType, userServerHelper, epmInworkOnly);
		} else {
			for (EPMLink epmLink : epmLinkList) {
				EPMDocumentMaster epmTempMast = (EPMDocumentMaster) epmLink.getEpmMaster();
				if (CommonUtil.getOIDString(epmMast).equals(CommonUtil.getOIDString(epmTempMast))) {
					return;
				}
			}

			// 신규일 경우
			saveNewMastRelation(partMast, epmMast, part, epmDocument, isEcad, required, referType, userServerHelper, true);
			createNewVerRelation(partMast, epmMast, part, epmDocument, isEcad, required, referType, userServerHelper, epmInworkOnly);
		}

	}

	// 신규 EPM Relation
	private void saveNewMastRelation(WTPartMaster partMast, EPMDocumentMaster epmMast, WTPart part, EPMDocument epmDocument, boolean isEcad, int required, String referType,
			boolean userServerHelper, boolean updateEpm) throws Exception {

		Kogger.debug(getClass(), "## EPMLink new Relation: WTPartMaster - EPMDocumentMaster ");
		Kogger.debug(getClass(), "## WTPartMaster:" + partMast.getNumber() + " EPMDocumentMaster:" + epmMast.getNumber());
		Kogger.debug(getClass(), "## WTPartMaster:" + CommonUtil.getOIDString(partMast) + " EPMDocumentMaster:" + CommonUtil.getOIDString(epmMast));
		EPMLink epmLink = EPMLink.newEPMLink(epmMast, partMast);
		epmLink.setEcad(isEcad);
		epmLink.setRequired(required);
		epmLink.setReferenceType(referType);

		if (userServerHelper)
			PersistenceServerHelper.manager.insert(epmLink);
		else
			PersistenceHelper.manager.save(epmLink);

		EpmInfoUpadator updator = new EpmInfoUpadator();

		if (updateEpm) // 복사 생성시에는 update 방지함
			updator.updateEpmInfo(partMast, epmMast, part, epmDocument);

	}

	// 신규 Rev Relation
	// epmInworkOnly 가 'true' 면 부품 개정후 도면이 개정될 때에는 '작업중[In Work]' 상태의 부품에게만 연계(Link)정보를 생성합니다.
	public void createNewVerRelation(WTPartMaster partMast, EPMDocumentMaster epmMast, WTPart part, EPMDocument epmDocument, boolean isEcad, int required, String referType,
			boolean userServerHelper, boolean epmInworkOnly) throws Exception {

		String epmRev = epmDocument.getVersionIdentifier().getValue();
		boolean exiestSameRev = false;

		QueryResult queryResult = getEPMDocAllBypartVer(part, required, referType);

		if (queryResult == null || queryResult.size() == 0) {

			// 신규일 경우
			savePartToEPMLink(part, epmDocument, isEcad, required, referType, userServerHelper);

		} else {

			while (queryResult.hasMoreElements()) {

				Object[] objs = (Object[]) queryResult.nextElement();
				EPMDocument epmRevTempDoc = (EPMDocument) objs[0];
				if (epmRev.equals(epmRevTempDoc.getVersionIdentifier().getValue())) {
					exiestSameRev = true; // 동일 리비전이 존재 경우에 생성하지 않는다.
					break;
				}

			}

			if (!exiestSameRev) {
				// 신규일 경우
				savePartToEPMLink(part, epmDocument, isEcad, required, referType, userServerHelper);
			}
		}

		// 부품 기준 상위 존재 체크
		if (epmDocument.getLifeCycleState() == State.toState("APPROVED")) {
			if (!epmInworkOnly || (epmInworkOnly && part.getLifeCycleState() == State.toState("INWORK"))) {
				EPMDocument latestEpmDoc = (EPMDocument) ObjectUtil.getLatestObject(epmMast);
				if (!epmRev.equals(latestEpmDoc.getVersionIdentifier().getValue())) { // 무한 루프 방지하도록 최신 버전 체크
					Kogger.debug(getClass(), "##### PartToEPMLink UP UP UP Version : WTPart - EPMDocument ");
					createNewVerRelation(partMast, epmMast, part, latestEpmDoc, isEcad, required, referType, userServerHelper, epmInworkOnly);
				}
			}
		}

		// 도면 기준 상위 존재 체크
		if (part.getLifeCycleState() == State.toState("APPROVED")) {
			WTPart latestWtPart = (WTPart) ObjectUtil.getLatestObject(partMast);
			if (!epmInworkOnly || (epmInworkOnly && latestWtPart.getLifeCycleState() == State.toState("INWORK"))) {
				if (!part.getVersionIdentifier().getValue().equals(latestWtPart.getVersionIdentifier().getValue())) { // 무한 루프 방지하도록 최신 버전 체크
					Kogger.debug(getClass(), "##### PartToEPMLink UP UP UP Version : EPMDocument - WTPart ");
					createNewVerRelation(partMast, epmMast, latestWtPart, epmDocument, isEcad, required, referType, userServerHelper, epmInworkOnly);
				}
			}
		}

	}

	public QueryResult getEPMDocAllBypartVer(WTPart objectRoleB) throws Exception {
		return getEPMDocAllBypartVer(objectRoleB, -1, null);
	}

	public QueryResult getEPMDocAllBypartVer(WTPart objectRoleB, int required, String referType) throws Exception {

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

			query.appendWhere(
					new SearchCondition(PartToEPMLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL, VersionControlHelper.getBranchIdentifier(objectRoleB)),
					new int[] { indexLink });

			if (required >= 0) {
				// if (objectRoleA != null)
				if (query.getConditionCount() > 0)
					query.appendAnd();

				query.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REQUIRED, SearchCondition.EQUAL, required), new int[] { indexLink });
			}

			if (StringUtils.isNotEmpty(referType)) {
				// if (objectRoleA != null)
				if (query.getConditionCount() > 0)
					query.appendAnd();

				query.appendWhere(new SearchCondition(PartToEPMLink.class, PartToEPMLink.REFERENCE_TYPE, SearchCondition.EQUAL, referType), new int[] { indexLink });
			}

			Kogger.debug(getClass(), "## REQUEST Query:" + query.toString());

			queryResult = PersistenceHelper.manager.find(query);

		} catch (Exception e) {
			Kogger.error(getClass(), ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			SessionHelper.manager.setPrincipal(orgPrincipal.getName());
		}

		return queryResult;
	}

	private void savePartToEPMLink(WTPart part, EPMDocument epmDoc, boolean isEcad, int required, String referType, boolean userServerHelper) throws Exception {
		// 신규일 경우
		Kogger.debug(getClass(), "## PartToEPMLink new Version Relation: WTPart - EPMDocument ");
		Kogger.debug(getClass(), "## WTPart:" + part.getNumber() + ":" + part.getVersionIdentifier().getValue() + " EPMDocument:" + epmDoc.getNumber() + ":"
				+ epmDoc.getVersionIdentifier().getValue());
		Kogger.debug(getClass(),
				"## WTPart:" + VersionReference.newVersionReference((Iterated) part).toString() + " EPMDocument:"
						+ VersionReference.newVersionReference((Iterated) epmDoc).toString());
		PartToEPMLink partToEPMLink = PartToEPMLink.newPartToEPMLink(epmDoc, part);
		partToEPMLink.setEcad(isEcad);
		partToEPMLink.setRequired(required);
		partToEPMLink.setReferenceType(referType);

		if (userServerHelper)
			PersistenceServerHelper.manager.insert(partToEPMLink);
		else
			PersistenceHelper.manager.save(partToEPMLink);
	}

}
