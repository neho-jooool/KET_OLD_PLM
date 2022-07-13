package ext.ket.part.base.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.VersionControlHelper;
import e3ps.bom.dao.PartDao;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.edm.beans.EDMPartHelper;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMaster;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.ProjectHelper;
import e3ps.qms.QmsPartInterfaceUtil;
import ext.ket.dqm.entity.KETDqmClose;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETHalbPartDieSetPartLink;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.project.trycondition.entity.TryConditionDTO;
import ext.ket.project.trycondition.service.TryConditionHelper;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.sample.entity.KETSamplePartLink;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.sample.entity.SampleRequestDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartDeleteUtil {

    public static List<String> deleteWTPartMastByAdmin(WTPart part) throws Exception {
	boolean deleteCheck = true;
	String partRev = part.getVersionIdentifier().getValue();
	String partNo = part.getNumber();
	String msg = "";
	List<String> msgList = new ArrayList<String>();

	// 1. 버전 체크
	if (!"0".equals(partRev)) {
	    msg = partNo + " 의 버전 : " + partRev + "(최초 등록이 아닌 부품은 삭제불가)";
	    msgList.add(msg);
	    deleteCheck = false;
	}

	// 2. ERP 존재여부 체크
	if (PartBaseHelper.service.existErpPartNumber(partNo)) {
	    msg = partNo + " 는 ERP에 자재마스터가 존재함";
	    msgList.add(msg);
	    deleteCheck = false;
	}

	// 3.도면 체크
	List<EPMDocument> linkQrDwg = EDMPartHelper.getReferenceEPMDocsByWTPart(part);
	if ((linkQrDwg == null) || (linkQrDwg.size() == 0)) {

	} else {
	    msg = partNo + " 의 " + "연관도면 건수 : " + linkQrDwg.size() + " 건";
	    msgList.add(msg);
	    deleteCheck = false;
	}
	// 4.문서 체크
	QueryResult linkQrDoc = PersistenceHelper.manager.navigate(part, KETDocumentPartLink.DOCUMENT_ROLE, KETDocumentPartLink.class);

	if ((linkQrDoc == null) || (linkQrDoc.size() == 0)) {

	} else {
	    msg = partNo + " 의 " + "연관문서 건수 : " + linkQrDoc.size() + " 건";
	    msgList.add(msg);
	    deleteCheck = false;
	}

	String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

	if ("M".equals(partType)) {// 금형부품은 스킵

	} else {

	    // 5.프로젝트 체크
	    List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
	    // 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
	    // TKLEE 추가함.
	    QueryResult partQr = PartUtil.getPartProjectLink(part, null);
	    KETPartProjectLink bProjectLink = null;
	    ProjectMaster partPjtMast = null;
	    while (partQr.hasMoreElements()) {
		Object[] objs = (Object[]) partQr.nextElement();
		bProjectLink = (KETPartProjectLink) objs[0];
		partPjtMast = bProjectLink.getProject();
	    }
	    if (partPjtMast != null) {
		E3PSProject partE3PSProject = ProjectHelper.getLastProject(partPjtMast);
		projectDataList.add(new E3PSProjectData(partE3PSProject));
	    }

	    // 프로젝트에서 등록한 부품을 통해서 프로젝트 정보를 가져온다.
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n");
	    sql.append("FROM PRODUCTPROJECT      PJT                                             \n");
	    sql.append("WHERE 1=1                                                                \n");
	    sql.append("AND PJT.LASTEST       = 1                                                \n");
	    sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n");
	    sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n");
	    sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + part.getNumber() + "' ) )   \n");

	    List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
	    DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	    returnObjList = oDaoManager.queryForList(sql.toString(), new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		    Map<String, Object> returnObj = new HashMap<String, Object>();
		    returnObj.put("oid", rs.getString("oid"));
		    return returnObj;
		}
	    });

	    for (int i = 0; i < returnObjList.size(); i++) {
		E3PSProject project = (E3PSProject) CommonUtil.getObject(StringUtil.checkNull((String) returnObjList.get(i).get("oid")));
		if (partPjtMast != null) {
		    if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
			continue;
		    }
		}
		projectDataList.add(new E3PSProjectData(project));
	    }

	    if ((projectDataList == null) || (projectDataList.size() == 0)) {

	    } else {
		msg = partNo + " 의 " + "연관프로젝트 건수 : " + projectDataList.size() + " 건";
		msgList.add(msg);
		deleteCheck = false;
	    }
	}

	// 6. ECO 체크
	ArrayList linkQrEco = PartDao.searchRelatedEcoEco(partNo, partRev);

	if ((linkQrEco == null) || (linkQrEco.size() == 0)) {

	} else {
	    msg = partNo + " 의 " + "연관 ECO 건수 : " + linkQrEco.size() + " 건";
	    msgList.add(msg);
	    deleteCheck = false;
	}

	// 7. 연관 부품 체크

	List<KETHalbPartDieSetPartLink> partList = null;
	if ("D".equals(partType) || "M".equals(partType)) {
	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	    partList = query.queryForListLinkByRoleB(KETHalbPartDieSetPartLink.class, WTPartMaster.class, (WTPartMaster) part.getMaster());
	} else {
	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	    partList = query.queryForListLinkByRoleA(WTPartMaster.class, KETHalbPartDieSetPartLink.class, (WTPartMaster) part.getMaster());
	}

	if ((partList == null) || (partList.size() == 0)) {

	} else {
	    msg = partNo + " 의 " + "연관 부품 건수 : " + partList.size() + " 건";
	    msgList.add(msg);
	    deleteCheck = false;
	}

	// 8. 개발품질문제 체크

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
	int idxRaise = query.appendClassList(KETDqmRaise.class, true);
	int idxClose = query.appendClassList(KETDqmClose.class, true);
	int idxPart = query.appendClassList(WTPart.class, true);

	ClassAttribute caHeaderRaise = new ClassAttribute(KETDqmHeader.class, "raiseReference.key.id");
	ClassAttribute caRaiseHeader = new ClassAttribute(KETDqmRaise.class, "thePersistInfo.theObjectIdentifier.id");

	ClassAttribute caHeaderClose = new ClassAttribute(KETDqmHeader.class, "closeReference.key.id");
	ClassAttribute caCloseHeader = new ClassAttribute(KETDqmClose.class, "thePersistInfo.theObjectIdentifier.id");

	ClassAttribute caPart = new ClassAttribute(WTPart.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute caRaisePart = new ClassAttribute(KETDqmRaise.class, "partReference.key.id");

	sc = new SearchCondition(caHeaderRaise, SearchCondition.EQUAL, caRaiseHeader);

	query.appendWhere(sc, new int[] { idxHeaer, idxRaise });

	sc = new SearchCondition(caHeaderClose, SearchCondition.EQUAL, caCloseHeader);
	sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idxHeaer, idxClose });

	query.appendAnd();
	sc = new SearchCondition(caPart, SearchCondition.EQUAL, caRaisePart);
	query.appendWhere(sc, new int[] { idxPart, idxRaise });

	query.appendAnd();
	sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, partNo);
	query.appendWhere(sc, new int[] { idxPart });

	List<KETDqmDTO> ketDqmDTOList = new ArrayList<KETDqmDTO>();

	QueryResult queryResult = PersistenceHelper.manager.find(query);

	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    KETDqmDTO rsltKETDqmDTO = new KETDqmDTO();
	    rsltKETDqmDTO = rsltKETDqmDTO.KETDqmDTOGrid((KETDqmHeader) objArr[0], rsltKETDqmDTO);
	    rsltKETDqmDTO.setRelatedEcrNo(StringUtil.checkNull(rsltKETDqmDTO.getRelatedEcrNo()).replace(",", "<br>"));
	    ketDqmDTOList.add(rsltKETDqmDTO);
	}

	if ((ketDqmDTOList == null) || (ketDqmDTOList.size() == 0)) {

	} else {
	    msg = partNo + " 의 " + "연관 개발품질문제 건수 : " + ketDqmDTOList.size() + " 건";
	    msgList.add(msg);
	    deleteCheck = false;
	}

	// 9. 샘플이력 체크

	query = new QuerySpec();

	int smpReqIdx = query.appendClassList(KETSampleRequest.class, true);
	int smpPartIdx = query.appendClassList(KETSamplePart.class, true);
	int smpPartLinkIdx = query.appendClassList(KETSamplePartLink.class, false);
	idxPart = query.appendClassList(WTPart.class, false);

	ClassAttribute caSmpReq = new ClassAttribute(KETSampleRequest.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute caSmpPart = new ClassAttribute(KETSamplePart.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute caSmpReqLink = new ClassAttribute(KETSamplePartLink.class, "roleAObjectRef.key.id");
	ClassAttribute caSmpPartLink = new ClassAttribute(KETSamplePartLink.class, "roleBObjectRef.key.id");

	caPart = new ClassAttribute(WTPart.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute caSmp = new ClassAttribute(KETSamplePart.class, "partReference.key.id");

	sc = null;

	sc = new SearchCondition(caSmpReq, SearchCondition.EQUAL, caSmpReqLink);
	sc.setFromIndicies(new int[] { smpReqIdx, smpPartLinkIdx }, 0);
	sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	query.appendWhere(sc, new int[] { smpReqIdx, smpPartLinkIdx });

	query.appendAnd();
	sc = new SearchCondition(caSmpPart, SearchCondition.EQUAL, caSmpPartLink);
	sc.setFromIndicies(new int[] { smpPartIdx, smpPartLinkIdx }, 0);
	sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
	query.appendWhere(sc, new int[] { smpPartIdx, smpPartLinkIdx });

	query.appendAnd();
	sc = new SearchCondition(caPart, SearchCondition.EQUAL, caSmp);
	query.appendWhere(sc, new int[] { idxPart, smpPartIdx });

	query.appendAnd();
	sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, partNo);
	query.appendWhere(sc, new int[] { idxPart });

	SearchUtil.setOrderBy(query, KETSampleRequest.class, smpReqIdx, KETSampleRequest.REQUEST_NO, true);

	List<SampleRequestDTO> sampleRequestDTOList = new ArrayList<SampleRequestDTO>();

	QueryResult result = PersistenceHelper.manager.find(query);
	if (result != null && result.size() > 0) {
	    if (result.hasMoreElements()) {
		while (result.hasMoreElements()) {
		    Object[] objArr = (Object[]) result.nextElement();
		    SampleRequestDTO sampleRequestDTO = new SampleRequestDTO();
		    sampleRequestDTO = sampleRequestDTO.SampleRequstList(sampleRequestDTO, (KETSampleRequest) objArr[0],
			    (KETSamplePart) objArr[1]);
		    sampleRequestDTOList.add(sampleRequestDTO);
		}
	    }
	}

	if ((sampleRequestDTOList == null) || (sampleRequestDTOList.size() == 0)) {

	} else {
	    msg = partNo + " 의 " + "연관 샘플이력 건수 : " + sampleRequestDTOList.size() + " 건";
	    msgList.add(msg);
	    deleteCheck = false;
	}

	// 10. Try 리포트 체크
	if ("D".equals(partType) || "M".equals(partType)) {
	    TryConditionDTO paramObject = new TryConditionDTO();
	    paramObject.setDieNo(partNo);
	    List<KETTryCondition> tryConditionList = TryConditionHelper.service.find(paramObject);

	    if ((tryConditionList == null) || (tryConditionList.size() == 0)) {

	    } else {
		msg = partNo + " 의 " + "연관 금형Try 건수 : " + tryConditionList.size() + " 건";
		msgList.add(msg);
		deleteCheck = false;
	    }
	}

	if (deleteCheck) {
	    PartDeleteUtil.deleteWTPartMast(part);
	}

	return msgList;

    }

    // 부품 생성 자체 삭제
    public static void deleteWTPartMast(WTPart part) throws Exception {

	try {

	    List<WTPart> partList = new ArrayList<WTPart>();

	    QueryResult qr = getPartVersionFromFirstByRev(part);
	    if (qr != null && qr.size() > 0) {
		PartAssociationUtil associateUtil = new PartAssociationUtil();
		int idx = 0;
		while (qr.hasMoreElements()) {
		    WTPart temp = (WTPart) qr.nextElement();

		    if (idx == 0) {
			Kogger.debug(PartDeleteUtil.class, "@ first iteration : " + temp.getIterationIdentifier().getValue());
		    }

		    if (!qr.hasMoreElements()) {
			Kogger.debug(PartDeleteUtil.class, "@ last iteration : " + temp.getIterationIdentifier().getValue());
		    }

		    // 마스터는 별도 삭제 로직
		    associateUtil.deAssociateWTPartMast(temp);
		    partList.add(temp);

		    idx++;
		}
	    }

	    if (partList.size() > 0) {
		// TKLEE 삭제 로그 필요함
		WTPart _delPart = partList.get(partList.size() - 1);

		Kogger.debug(PartDeleteUtil.class, _delPart.toString());
		// 하나만 삭제하면 모든 리비전이 모두 삭제된다. // windchill 특징

		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "#################### 부품 삭제  시작 ########################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");

		Kogger.debug(PartDeleteUtil.class, "PART Oid:" + CommonUtil.getOIDString(_delPart));
		Kogger.debug(PartDeleteUtil.class, "PART NO:" + _delPart.getNumber());
		Kogger.debug(PartDeleteUtil.class, "PART Name:" + _delPart.getName());
		Kogger.debug(PartDeleteUtil.class, "PART Ver:" + _delPart.getVersionIdentifier().getValue());

		WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
		String userId = user.getName();
		String userName = user.getFullName();
		Department userDept = DepartmentHelper.manager.getDepartment(user);
		String deptCode = (userDept == null) ? "" : userDept.getName();
		String deptName = (userDept == null) ? "" : userDept.getCode();
		String valueOld = "부품삭제 전";
		String valueNew = "부품삭제 후";

		PartChangeLogHandler.changeLog(part, "WTPart", "WTPart Revision Self", valueOld, valueNew, userId, userName, deptCode,
		        deptName);

		WTProperties properties = WTProperties.getLocalProperties();
		String hostName = properties.getProperty("java.rmi.server.hostname");

		if (hostName.equals("plmapwas")) {
		    // qms 자재마스터 삭제
		    QmsPartInterfaceUtil.manager.qmsPartDelete(_delPart.getNumber());
		}

		PersistenceHelper.manager.delete(_delPart);

		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "#################### 부품 삭제 끝    ########################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
	    }

	} catch (Exception e) {

	    Kogger.error(PartDeleteUtil.class, e);
	    throw e;
	}
    }

    // 리비전 취소 - 삭제
    public static void deleteWTPartRev(WTPart part) throws Exception {

	try {

	    List<WTPart> partList = new ArrayList<WTPart>();

	    QueryResult qr = getPartVersionFromFirstByRev(part);
	    if (qr != null && qr.size() > 0) {
		PartAssociationUtil associateUtil = new PartAssociationUtil();
		int idx = 0;
		while (qr.hasMoreElements()) {
		    WTPart temp = (WTPart) qr.nextElement();

		    if (idx == 0) {
			Kogger.debug(PartDeleteUtil.class, "@ first iteration : " + temp.getIterationIdentifier().getValue());
		    }

		    if (!qr.hasMoreElements()) {
			Kogger.debug(PartDeleteUtil.class, "@ last iteration : " + temp.getIterationIdentifier().getValue());
		    }

		    // 리비전은 별도의 삭제로직
		    associateUtil.deAssociateWTPartRev(temp);
		    partList.add(temp);

		    idx++;
		}
	    }

	    if (partList.size() > 0) {

		// TKLEE 삭제 로그 필요함
		WTPart _delPart = partList.get(partList.size() - 1);

		Kogger.debug(PartDeleteUtil.class, _delPart.toString());
		// 하나만 삭제하면 모든 리비전이 모두 삭제된다. // windchill 특징

		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "#################### 부품 삭제  시작 ########################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");

		Kogger.debug(PartDeleteUtil.class, "PART Oid:" + CommonUtil.getOIDString(_delPart));
		Kogger.debug(PartDeleteUtil.class, "PART NO:" + _delPart.getNumber());
		Kogger.debug(PartDeleteUtil.class, "PART Name:" + _delPart.getName());
		Kogger.debug(PartDeleteUtil.class, "PART Ver:" + _delPart.getVersionIdentifier().getValue());

		PersistenceHelper.manager.delete(_delPart);

		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "#################### 부품 삭제 끝    ########################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");
		Kogger.debug(PartDeleteUtil.class, "###########################################################");

	    }

	} catch (Exception e) {

	    Kogger.error(PartDeleteUtil.class, e);
	    throw e;
	}
    }

    // 최초 iteration부터 차례로 가져오기
    public static QueryResult getPartVersionFromFirstByRev(WTPart wtPart) throws WTException {

	QuerySpec qs = new QuerySpec(WTPart.class);

	qs.appendWhere(
	        new SearchCondition(WTPart.class, "iterationInfo.branchId", SearchCondition.EQUAL, VersionControlHelper
	                .getBranchIdentifier(wtPart)), new int[] { 0 });

	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(WTPart.class,
	        "iterationInfo.identifier.iterationId"));
	OrderBy orderby = new OrderBy(sqlfunction, false); // false : 처음 부터 나옴

	qs.appendOrderBy(orderby, new int[] { 0 });

	Kogger.debug(PartDeleteUtil.class, "@ query : " + qs);

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);

	return qr;
    }

}
