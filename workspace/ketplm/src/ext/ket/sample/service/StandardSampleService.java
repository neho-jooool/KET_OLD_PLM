package ext.ket.sample.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamHelper;
import wt.util.WTException;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfTemplateObject;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMaster;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.ProjectHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.sample.entity.KETSamplePartLink;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.sample.entity.Sample;
import ext.ket.sample.entity.SampleDTO;
import ext.ket.sample.entity.SampleRequestDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.SessionUtil;

public class StandardSampleService extends StandardManager implements SampleService {
    private static final long serialVersionUID = 1L;

    /**
     * Default factory for the class.
     * 
     * @return
     * @throws WTException
     * @메소드명 : newStandardSampleService
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static StandardSampleService newStandardSampleService() throws WTException {
	StandardSampleService instance = new StandardSampleService();
	instance.initialize();
	return instance;
    }

    /*
     * Sample 삭제
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#delete(java.lang.Object)
     */
    @Override
    public Sample delete(SampleDTO paramObject) throws Exception {
	Sample sample = (Sample) CommonUtil.getObject(paramObject.getOid());
	return (Sample) PersistenceHelper.manager.delete(sample);
    }

    /*
     * 전체 리스트
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#find(java.lang.Object)
     */
    public List<Sample> find(SampleDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<Sample> list = new ArrayList<Sample>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((Sample) objArr[0]);
	}
	return list;
    }

    /*
     * Paging 리스트
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#findPaging(java.lang.Object)
     */
    public PageControl findPaging(SampleDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		    pageSessionId);
	}
	return pageControl;
    }

    /**
     * Sample 검색 Query
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQuery(SampleDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;

	int idx = query.appendClassList(Sample.class, true);
	if (!StringUtil.isTrimToEmpty(paramObject.getName())) {
	    sc = new SearchCondition(Sample.class, Sample.NAME, SearchCondition.LIKE, "%" + paramObject.getName() + "%", false);
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getTitle())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(Sample.class, Sample.TITLE, SearchCondition.LIKE, "%" + paramObject.getTitle() + "%", false);
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    // '-'내림차순
	    if (paramObject.getSortName().startsWith("-")) {
		SearchUtil.setOrderBy(query, Sample.class, idx, paramObject.getSortName().substring(1), true);
	    } else {
		SearchUtil.setOrderBy(query, Sample.class, idx, paramObject.getSortName(), false);
	    }
	} else {
	    SearchUtil.setOrderBy(query, Sample.class, idx, Sample.NUM, true);
	}
	return query;
    }

    /*
     * 수정
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#modify(java.lang.Object)
     */
    @Override
    public Sample modify(SampleDTO paramObject) throws Exception {
	Sample modify = (Sample) CommonUtil.getObject(paramObject.getOid());
	modify.setName(paramObject.getName());
	modify.setTitle(paramObject.getTitle());
	modify.setNum(paramObject.getNum());
	modify.setWebEditor(paramObject.getWebEditor());
	modify.setWebEditorText(paramObject.getWebEditorText());
	modify.setName(SessionHelper.manager.getPrincipal().getName());
	modify = (Sample) PersistenceHelper.manager.modify(modify);
	// 첨부파일 업로드
	KETContentHelper.service.updateContent(modify, paramObject);
	return modify;
    }

    /*
     * 등록
     * 
     * @see ext.ket.shared.service.CommonServiceInterface#save(java.lang.Object)
     */
    @Override
    public Sample save(SampleDTO paramObject) throws Exception {
	Sample sample = Sample.newSample();
	sample.setNum(paramObject.getNum());
	sample.setName(SessionHelper.manager.getPrincipal().getName());
	sample.setTitle(paramObject.getTitle());
	sample.setWebEditor(paramObject.getWebEditor());
	sample.setWebEditorText(paramObject.getWebEditorText());
	sample = (Sample) PersistenceHelper.manager.save(sample);
	// 첨부파일 업로드
	KETContentHelper.service.updateContent(sample, paramObject);
	return sample;
    }

    @Override
    public SampleRequestDTO sampleRequestSave(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETSampleRequest ketSampleRequest = KETSampleRequest.newKETSampleRequest();

	ketSampleRequest.setRequestNo(getRequestNo());
	ketSampleRequest.setRequestTitle(paramObject.getRequestTitle());

	ketSampleRequest.setCarTypeCode(paramObject.getCarTypeCode());
	ketSampleRequest.setCarTypeName(paramObject.getCarTypeName());

	ketSampleRequest.setCustomerCode(paramObject.getCustomerCode());
	ketSampleRequest.setCustomerName(paramObject.getCustomerName());

	ketSampleRequest.setCustomerContractor(paramObject.getCustomerContractor());

	ketSampleRequest.setPurpose(paramObject.getPurpose());

	ketSampleRequest.setWebEditor(paramObject.getWebEditor());
	ketSampleRequest.setWebEditorText(paramObject.getWebEditorText());

	ketSampleRequest.setSampleRequestStateCode("INWORK");
	ketSampleRequest.setSampleRequestStateName("작업중");

	if (!(paramObject.getRequestDate().equals("") || paramObject.getRequestDate().equals(null))) {
	    ketSampleRequest.setRequestDate(DateUtil.getTimestampFormat(paramObject.getRequestDate(), "yyyy-MM-dd"));
	}

	WTContainerRef containerRef = WCUtil.getWTContainerRef();
	String lcName = "KET_COMMON_LC";
	LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketSampleRequest,
	        LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	String folderPath = "/Default/자동차사업부/초기유동/";

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) ketSampleRequest, folder);

	// 요청서 저장
	ketSampleRequest = (KETSampleRequest) TeamHelper.setTeamTemplate(ketSampleRequest, TeamHelper.service.getTeamTemplate("Default"));
	ketSampleRequest = (KETSampleRequest) PersistenceHelper.manager.save(ketSampleRequest);
	KETContentHelper.service.updateContent(ketSampleRequest, paramObject);

	String partOidArr[] = paramObject.getPartOidArr().split(",");
	String partPmUserOidArr[] = paramObject.getPartPmUserOidArr().split(",");
	String partRequestCountArr[] = paramObject.getPartRequestCountArr().split(",");
	String pjtoidArr[] = paramObject.getPjtoid().split(",");

	if (partOidArr.length == partPmUserOidArr.length && partPmUserOidArr.length == partRequestCountArr.length) {
	    for (int i = 0; i < partOidArr.length; i++) {
		KETSamplePart ketSamplePart = KETSamplePart.newKETSamplePart();

		if (pjtoidArr.length > i) {
		    if (StringUtil.checkString(pjtoidArr[i])) {
			ketSamplePart.setPjtoid(pjtoidArr[i]);
			E3PSProject project = (E3PSProject) CommonUtil.getObject(pjtoidArr[i]);
			ketSamplePart.setPjtno(project.getPjtNo());
		    }
		}

		WTPart wtPart = (WTPart) CommonUtil.getObject(partOidArr[i]);
		ketSamplePart.setPart(wtPart);

		WTUser wtUser = (WTUser) CommonUtil.getObject(partPmUserOidArr[i]);
		ketSamplePart.setUser(wtUser);

		if (!partRequestCountArr[i].equals("")) {
		    ketSamplePart.setRequestCount(Integer.parseInt(partRequestCountArr[i]));
		}

		lcName = "KET_ECA_LC";
		LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketSamplePart,
		        LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

		FolderHelper.assignLocation((FolderEntry) ketSamplePart, folder);
		// 부품 저장
		ketSamplePart = (KETSamplePart) TeamHelper.setTeamTemplate(ketSamplePart, TeamHelper.service.getTeamTemplate("Default"));
		ketSamplePart = (KETSamplePart) PersistenceHelper.manager.save(ketSamplePart);

		// 요청서 부품 링크 저장
		KETSamplePartLink ketSamplePartLink = KETSamplePartLink.newKETSamplePartLink(ketSampleRequest, ketSamplePart);
		ketSamplePartLink = (KETSamplePartLink) PersistenceHelper.manager.save(ketSamplePartLink);
	    }
	}

	paramObject.setOid(CommonUtil.getOIDString(ketSampleRequest));

	return paramObject;
    }

    public String getRequestNo() throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();

	    sql.append("SELECT    'S'").append("|| TO_CHAR (SYSDATE, 'YYMM')")
		    .append("|| TRIM(TO_CHAR(SEQ_SAMPLEREQUEST_NUM.NEXTVAL,'000'))").append("FROM DUAL");

	    // sql.append("SELECT FN_GET_ECM_NUMBERING(?) FROM DUAL \n");
	    pstmt = con.prepareStatement(sql.toString());
	    // pstmt.setString(1, "ECR");

	    rs = pstmt.executeQuery();
	    String ecrId = "";
	    while (rs.next()) {
		ecrId = rs.getString(1);
	    }

	    return ecrId;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
    }

    @Override
    public SampleRequestDTO sampleRequestView(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());
	paramObject = paramObject.SampleRequestDTO(paramObject, ketSampleRequest);
	return paramObject;
    }

    @Override
    public List<Map<String, Object>> samplePartReceptionListView(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());
	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	List<Map<String, Object>> samplePartMapList = new ArrayList<Map<String, Object>>();

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		HashMap<String, Object> samplePartMap = new HashMap<String, Object>();

		if (ketSampleRequest.getSampleRequestStateCode().equals("REQUEST")
		        || ketSampleRequest.getSampleRequestStateCode().equals("REREQUEST")) {

		    WTUser workItemWTUser = null;
		    WfProcess process = null;
		    boolean todoUserFlag = false;
		    QuerySpec todoSpec = new QuerySpec(WorkItem.class);
		    SearchUtil.appendEQUAL(todoSpec, WorkItem.class, "primaryBusinessObject.key.classname",
			    CommonUtil.getRefOID((LifeCycleManaged) ketSamplePart), 0);
		    QueryResult inTodoQr = PersistenceHelper.manager.find((StatementSpec) todoSpec);
		    if (inTodoQr.hasMoreElements()) {
			WorkItem item = (WorkItem) inTodoQr.nextElement();
			if (item.getStatus().equals(WfAssignmentState.POTENTIAL)) {
			    workItemWTUser = (WTUser) item.getOwnership().getOwner().getPrincipal();
			    if (workItemWTUser.equals((WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal())) {
				todoUserFlag = true;
			    }
			}
		    }

		    if (ketSamplePart.getUser().equals((WTUser) SessionHelper.manager.getPrincipal()) || todoUserFlag) {
			if (ketSamplePart.getSamplePartStateCode().equals("REQUEST")
			        || ketSamplePart.getSamplePartStateCode().equals("REREQUEST")) {
			    // 부품 리스트 상태가 요청인 것만 (재요청시때문에 사용함)
			    samplePartMap.put("samplePartOid", CommonUtil.getOIDString(ketSamplePart));
			    samplePartMap.put("partOid", CommonUtil.getOIDString(ketSamplePart.getPart()));
			    samplePartMap.put("partNo", ketSamplePart.getPart().getNumber());
			    samplePartMap.put("partName", ketSamplePart.getPart().getName());
			    samplePartMap.put("ver", ketSamplePart.getPart().getVersionInfo().getIdentifier().getValue());
			    samplePartMap.put("pmUserOid", CommonUtil.getOIDString(ketSamplePart.getUser()));
			    PeopleData peopleData = new PeopleData(ketSamplePart.getUser());
			    samplePartMap.put("pmUserName", peopleData.name);
			    samplePartMap.put("developeStageName", PartUtil.getSpDevLevelText(PartSpecGetter.getPartSpec(
				    ketSamplePart.getPart(), PartSpecEnum.SpPartDevLevel)));
			    String pjtno = ketSamplePart.getPjtno();
			    String pjtOid = ketSamplePart.getPjtoid();

			    // String pjtno = "";
			    // String pjtOid = "";
			    //
			    // WTPart wtPart = ketSamplePart.getPart();
			    //
			    // List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
			    // // 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
			    // // TKLEE 추가함.
			    // QueryResult partQr = PartUtil.getPartProjectLink(wtPart, null);
			    // KETPartProjectLink bProjectLink = null;
			    // ProjectMaster partPjtMast = null;
			    // while (partQr.hasMoreElements()) {
			    // Object[] objs = (Object[]) partQr.nextElement();
			    // bProjectLink = (KETPartProjectLink) objs[0];
			    // partPjtMast = bProjectLink.getProject();
			    // }
			    // if (partPjtMast != null) {
			    // E3PSProject partE3PSProject = ProjectHelper.getLastProject(partPjtMast);
			    // projectDataList.add(new E3PSProjectData(partE3PSProject));
			    // }
			    //
			    // // 프로젝트에서 등록한 부품을 통해서 프로젝트 정보를 가져온다.
			    // StringBuffer sql = new StringBuffer();
			    // sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n");
			    // sql.append("FROM PRODUCTPROJECT      PJT                                             \n");
			    // sql.append("WHERE 1=1                                                                \n");
			    // sql.append("AND PJT.LASTEST       = 1                                                \n");
			    // sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n");
			    // sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n");
			    // sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + wtPart.getNumber()
			    // + "' ) )   \n");
			    //
			    // List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
			    // DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
			    // returnObjList = oDaoManager.queryForList(sql.toString(), new RowSetStrategy<Map<String, Object>>() {
			    // @Override
			    // public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
			    // Map<String, Object> returnObj = new HashMap<String, Object>();
			    // returnObj.put("oid", rs.getString("oid"));
			    // return returnObj;
			    // }
			    // });
			    //
			    // for (int i = 0; i < returnObjList.size(); i++) {
			    // E3PSProject project = (E3PSProject) CommonUtil.getObject(StringUtil.checkNull((String) returnObjList.get(i)
			    // .get("oid")));
			    // if (partPjtMast != null) {
			    // if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
			    // continue;
			    // }
			    // }
			    // projectDataList.add(new E3PSProjectData(project));
			    // }
			    //
			    // Timestamp checkStamp = null;
			    // for (int i = 0; i < projectDataList.size(); i++) {
			    // E3PSProjectData projectData = (E3PSProjectData) projectDataList.get(i);
			    // if (checkStamp == null) {
			    // checkStamp = projectData.pjtCreateDate;
			    // E3PSProject project = projectData.e3psProject;
			    // pjtno = project.getMaster().getPjtNo();
			    // pjtOid = CommonUtil.getOIDString(project.getMaster());
			    // } else if (checkStamp.before(projectData.pjtCreateDate)) {
			    // checkStamp = projectData.pjtCreateDate;
			    // E3PSProject project = projectData.e3psProject;
			    // pjtno = project.getMaster().getPjtNo();
			    // pjtOid = CommonUtil.getOIDString(project.getMaster());
			    // }
			    // }

			    samplePartMap.put("projectNo", pjtno);
			    samplePartMap.put("projectOid", pjtOid);

			    samplePartMap.put("requestCount", ketSamplePart.getRequestCount());
			    samplePartMap.put("dispensationCount", ketSamplePart.getDispensationCount());
			    samplePartMap.put("receptionDate", DateUtil.getDateString(ketSamplePart.getReceptionDate(), "date"));
			    samplePartMap.put("dispensationExpectDate",
				    DateUtil.getDateString(ketSamplePart.getDispensationExpectDate(), "date"));
			    samplePartMap.put("dispensationDate", DateUtil.getDateString(ketSamplePart.getDispensationDate(), "date"));

			    samplePartMapList.add(samplePartMap);
			}
		    }

		} else {
		    samplePartMap.put("samplePartOid", CommonUtil.getOIDString(ketSamplePart));
		    samplePartMap.put("partOid", CommonUtil.getOIDString(ketSamplePart.getPart()));
		    samplePartMap.put("partNo", ketSamplePart.getPart().getNumber());
		    samplePartMap.put("partName", ketSamplePart.getPart().getName());
		    samplePartMap.put("ver", ketSamplePart.getPart().getVersionInfo().getIdentifier().getValue());
		    samplePartMap.put("pmUserOid", CommonUtil.getOIDString(ketSamplePart.getUser()));
		    PeopleData peopleData = new PeopleData(ketSamplePart.getUser());
		    samplePartMap.put("pmUserName", peopleData.name);
		    samplePartMap.put("developeStageName",
			    PartUtil.getSpDevLevelText(PartSpecGetter.getPartSpec(ketSamplePart.getPart(), PartSpecEnum.SpPartDevLevel)));

		    String pjtno = ketSamplePart.getPjtno();
		    String pjtOid = ketSamplePart.getPjtoid();

		    // String pjtno = "";
		    // String pjtOid = "";
		    //
		    // WTPart wtPart = ketSamplePart.getPart();
		    //
		    // List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
		    // // 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
		    // // TKLEE 추가함.
		    // QueryResult partQr = PartUtil.getPartProjectLink(wtPart, null);
		    // KETPartProjectLink bProjectLink = null;
		    // ProjectMaster partPjtMast = null;
		    // while (partQr.hasMoreElements()) {
		    // Object[] objs = (Object[]) partQr.nextElement();
		    // bProjectLink = (KETPartProjectLink) objs[0];
		    // partPjtMast = bProjectLink.getProject();
		    // }
		    // if (partPjtMast != null) {
		    // E3PSProject partE3PSProject = ProjectHelper.getLastProject(partPjtMast);
		    // projectDataList.add(new E3PSProjectData(partE3PSProject));
		    // }
		    //
		    // // 프로젝트에서 등록한 부품을 통해서 프로젝트 정보를 가져온다.
		    // StringBuffer sql = new StringBuffer();
		    // sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n");
		    // sql.append("FROM PRODUCTPROJECT      PJT                                             \n");
		    // sql.append("WHERE 1=1                                                                \n");
		    // sql.append("AND PJT.LASTEST       = 1                                                \n");
		    // sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n");
		    // sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n");
		    // sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + wtPart.getNumber() + "' ) )   \n");
		    //
		    // List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
		    // DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
		    // returnObjList = oDaoManager.queryForList(sql.toString(), new RowSetStrategy<Map<String, Object>>() {
		    // @Override
		    // public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		    // Map<String, Object> returnObj = new HashMap<String, Object>();
		    // returnObj.put("oid", rs.getString("oid"));
		    // return returnObj;
		    // }
		    // });
		    //
		    // for (int i = 0; i < returnObjList.size(); i++) {
		    // E3PSProject project = (E3PSProject) CommonUtil.getObject(StringUtil.checkNull((String) returnObjList.get(i).get(
		    // "oid")));
		    // if (partPjtMast != null) {
		    // if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
		    // continue;
		    // }
		    // }
		    // projectDataList.add(new E3PSProjectData(project));
		    // }
		    //
		    // Timestamp checkStamp = null;
		    // for (int i = 0; i < projectDataList.size(); i++) {
		    // E3PSProjectData projectData = (E3PSProjectData) projectDataList.get(i);
		    // if (checkStamp == null) {
		    // checkStamp = projectData.pjtCreateDate;
		    // E3PSProject project = projectData.e3psProject;
		    // pjtno = project.getMaster().getPjtNo();
		    // pjtOid = CommonUtil.getOIDString(project.getMaster());
		    // } else if (checkStamp.before(projectData.pjtCreateDate)) {
		    // checkStamp = projectData.pjtCreateDate;
		    // E3PSProject project = projectData.e3psProject;
		    // pjtno = project.getMaster().getPjtNo();
		    // pjtOid = CommonUtil.getOIDString(project.getMaster());
		    // }
		    // }

		    samplePartMap.put("projectNo", pjtno);
		    samplePartMap.put("projectOid", pjtOid);

		    samplePartMap.put("requestCount", ketSamplePart.getRequestCount());
		    samplePartMap.put("dispensationCount", ketSamplePart.getDispensationCount());
		    samplePartMap.put("receptionDate", DateUtil.getDateString(ketSamplePart.getReceptionDate(), "date"));
		    samplePartMap.put("dispensationExpectDate", DateUtil.getDateString(ketSamplePart.getDispensationExpectDate(), "date"));
		    samplePartMap.put("dispensationDate", DateUtil.getDateString(ketSamplePart.getDispensationDate(), "date"));

		    samplePartMapList.add(samplePartMap);

		}
	    }
	}

	return samplePartMapList;
    }

    @Override
    public List<Map<String, Object>> samplePartReceptionListOnlyView(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());
	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	List<Map<String, Object>> samplePartMapList = new ArrayList<Map<String, Object>>();

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		HashMap<String, Object> samplePartMap = new HashMap<String, Object>();

		samplePartMap.put("samplePartOid", CommonUtil.getOIDString(ketSamplePart));
		samplePartMap.put("partOid", CommonUtil.getOIDString(ketSamplePart.getPart()));
		samplePartMap.put("partNo", ketSamplePart.getPart().getNumber());
		samplePartMap.put("partName", ketSamplePart.getPart().getName());
		samplePartMap.put("ver", ketSamplePart.getPart().getVersionInfo().getIdentifier().getValue());
		samplePartMap.put("pmUserOid", CommonUtil.getOIDString(ketSamplePart.getUser()));
		PeopleData peopleData = new PeopleData(ketSamplePart.getUser());
		samplePartMap.put("pmUserName", peopleData.name);
		samplePartMap.put("developeStageName",
		        PartUtil.getSpDevLevelText(PartSpecGetter.getPartSpec(ketSamplePart.getPart(), PartSpecEnum.SpPartDevLevel)));

		String pjtno = ketSamplePart.getPjtno();
		String pjtOid = ketSamplePart.getPjtoid();

		// String pjtno = "";
		// String pjtOid = "";
		//
		// WTPart wtPart = ketSamplePart.getPart();
		//
		// List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
		// // 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
		// // TKLEE 추가함.
		// QueryResult partQr = PartUtil.getPartProjectLink(wtPart, null);
		// KETPartProjectLink bProjectLink = null;
		// ProjectMaster partPjtMast = null;
		// while (partQr.hasMoreElements()) {
		// Object[] objs = (Object[]) partQr.nextElement();
		// bProjectLink = (KETPartProjectLink) objs[0];
		// partPjtMast = bProjectLink.getProject();
		// }
		// if (partPjtMast != null) {
		// E3PSProject partE3PSProject = ProjectHelper.getLastProject(partPjtMast);
		// projectDataList.add(new E3PSProjectData(partE3PSProject));
		// }
		//
		// // 프로젝트에서 등록한 부품을 통해서 프로젝트 정보를 가져온다.
		// StringBuffer sql = new StringBuffer();
		// sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n");
		// sql.append("FROM PRODUCTPROJECT      PJT                                             \n");
		// sql.append("WHERE 1=1                                                                \n");
		// sql.append("AND PJT.LASTEST       = 1                                                \n");
		// sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n");
		// sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n");
		// sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + wtPart.getNumber() + "' ) )   \n");
		//
		// List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
		// DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
		// returnObjList = oDaoManager.queryForList(sql.toString(), new RowSetStrategy<Map<String, Object>>() {
		// @Override
		// public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		// Map<String, Object> returnObj = new HashMap<String, Object>();
		// returnObj.put("oid", rs.getString("oid"));
		// return returnObj;
		// }
		// });
		//
		// for (int i = 0; i < returnObjList.size(); i++) {
		// E3PSProject project = (E3PSProject) CommonUtil
		// .getObject(StringUtil.checkNull((String) returnObjList.get(i).get("oid")));
		// if (partPjtMast != null) {
		// if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
		// continue;
		// }
		// }
		// projectDataList.add(new E3PSProjectData(project));
		// }
		//
		// Timestamp checkStamp = null;
		// for (int i = 0; i < projectDataList.size(); i++) {
		// E3PSProjectData projectData = (E3PSProjectData) projectDataList.get(i);
		// if (checkStamp == null) {
		// checkStamp = projectData.pjtCreateDate;
		// E3PSProject project = projectData.e3psProject;
		// pjtno = project.getMaster().getPjtNo();
		// pjtOid = CommonUtil.getOIDString(project.getMaster());
		// } else if (checkStamp.before(projectData.pjtCreateDate)) {
		// checkStamp = projectData.pjtCreateDate;
		// E3PSProject project = projectData.e3psProject;
		// pjtno = project.getMaster().getPjtNo();
		// pjtOid = CommonUtil.getOIDString(project.getMaster());
		// }
		// }

		samplePartMap.put("projectNo", pjtno);
		samplePartMap.put("projectOid", pjtOid);

		samplePartMap.put("requestCount", ketSamplePart.getRequestCount());
		samplePartMap.put("dispensationCount", ketSamplePart.getDispensationCount());
		samplePartMap.put("receptionDate", DateUtil.getDateString(ketSamplePart.getReceptionDate(), "date"));
		samplePartMap.put("dispensationExpectDate", DateUtil.getDateString(ketSamplePart.getDispensationExpectDate(), "date"));
		samplePartMap.put("dispensationDate", DateUtil.getDateString(ketSamplePart.getDispensationDate(), "date"));

		samplePartMapList.add(samplePartMap);

	    }
	}

	return samplePartMapList;
    }

    @Override
    public List<Map<String, Object>> samplePartListView(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());
	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	List<Map<String, Object>> samplePartMapList = new ArrayList<Map<String, Object>>();

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		HashMap<String, Object> samplePartMap = new HashMap<String, Object>();

		samplePartMap.put("samplePartOid", CommonUtil.getOIDString(ketSamplePart));
		samplePartMap.put("partOid", CommonUtil.getOIDString(ketSamplePart.getPart()));
		samplePartMap.put("partNo", ketSamplePart.getPart().getNumber());
		samplePartMap.put("partName", ketSamplePart.getPart().getName());
		samplePartMap.put("ver", ketSamplePart.getPart().getVersionInfo().getIdentifier().getValue());
		samplePartMap.put("pmUserOid", CommonUtil.getOIDString(ketSamplePart.getUser()));
		PeopleData peopleData = new PeopleData(ketSamplePart.getUser());
		samplePartMap.put("pmUserName", peopleData.name);
		samplePartMap.put("developeStageName",
		        PartUtil.getSpDevLevelText(PartSpecGetter.getPartSpec(ketSamplePart.getPart(), PartSpecEnum.SpPartDevLevel)));

		String pjtno = ketSamplePart.getPjtno();
		String pjtOid = ketSamplePart.getPjtoid();

		List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
		// 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
		// TKLEE 추가함.
		QueryResult partQr = PartUtil.getPartProjectLink(ketSamplePart.getPart(), null);
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
		sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + ketSamplePart.getPart().getNumber()
		        + "' ) )   \n");

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
		    E3PSProject project = (E3PSProject) CommonUtil
			    .getObject(StringUtil.checkNull((String) returnObjList.get(i).get("oid")));
		    if (partPjtMast != null) {
			if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
			    continue;
			}
		    }
		    projectDataList.add(new E3PSProjectData(project));
		}

		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

		for (int i = 0; i < projectDataList.size(); i++) {
		    E3PSProjectData projectData = (E3PSProjectData) projectDataList.get(i);
		    Map<String, String> map = new HashMap<String, String>();

		    E3PSProject project = projectData.e3psProject;
		    String selectPjtno = project.getMaster().getPjtNo();
		    String selectPjtOid = CommonUtil.getOIDString(project);

		    map.put("selectPjtno", selectPjtno);
		    map.put("selectPjtOid", selectPjtOid);

		    mapList.add(map);
		}

		// String pjtno = "";
		// String pjtOid = "";
		//
		// WTPart wtPart = ketSamplePart.getPart();
		//
		// List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
		// // 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
		// // TKLEE 추가함.
		// QueryResult partQr = PartUtil.getPartProjectLink(wtPart, null);
		// KETPartProjectLink bProjectLink = null;
		// ProjectMaster partPjtMast = null;
		// while (partQr.hasMoreElements()) {
		// Object[] objs = (Object[]) partQr.nextElement();
		// bProjectLink = (KETPartProjectLink) objs[0];
		// partPjtMast = bProjectLink.getProject();
		// }
		// if (partPjtMast != null) {
		// E3PSProject partE3PSProject = ProjectHelper.getLastProject(partPjtMast);
		// projectDataList.add(new E3PSProjectData(partE3PSProject));
		// }
		//
		// // 프로젝트에서 등록한 부품을 통해서 프로젝트 정보를 가져온다.
		// StringBuffer sql = new StringBuffer();
		// sql.append("SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS OID                         \n");
		// sql.append("FROM PRODUCTPROJECT      PJT                                             \n");
		// sql.append("WHERE 1=1                                                                \n");
		// sql.append("AND PJT.LASTEST       = 1                                                \n");
		// sql.append("AND PJT.CHECKOUTSTATE <> 'c/o'                                           \n");
		// sql.append("AND ( PJT.PJTTYPE = '2' )                                                \n");
		// sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + wtPart.getNumber() + "' ) )   \n");
		//
		// List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
		// DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
		// returnObjList = oDaoManager.queryForList(sql.toString(), new RowSetStrategy<Map<String, Object>>() {
		// @Override
		// public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		// Map<String, Object> returnObj = new HashMap<String, Object>();
		// returnObj.put("oid", rs.getString("oid"));
		// return returnObj;
		// }
		// });
		//
		// for (int i = 0; i < returnObjList.size(); i++) {
		// E3PSProject project = (E3PSProject) CommonUtil
		// .getObject(StringUtil.checkNull((String) returnObjList.get(i).get("oid")));
		// if (partPjtMast != null) {
		// if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
		// continue;
		// }
		// }
		// projectDataList.add(new E3PSProjectData(project));
		// }
		//
		// Timestamp checkStamp = null;
		//
		// for (int i = 0; i < projectDataList.size(); i++) {
		// E3PSProjectData projectData = (E3PSProjectData) projectDataList.get(i);
		// if (checkStamp == null) {
		// checkStamp = projectData.pjtCreateDate;
		// E3PSProject project = projectData.e3psProject;
		// pjtno = project.getMaster().getPjtNo();
		// pjtOid = CommonUtil.getOIDString(project.getMaster());
		// } else if (checkStamp.before(projectData.pjtCreateDate)) {
		// checkStamp = projectData.pjtCreateDate;
		// E3PSProject project = projectData.e3psProject;
		// pjtno = project.getMaster().getPjtNo();
		// pjtOid = CommonUtil.getOIDString(project.getMaster());
		// }
		// }

		samplePartMap.put("projectNo", pjtno);
		samplePartMap.put("projectOid", pjtOid);

		samplePartMap.put("selectPjtList", mapList);

		samplePartMap.put("requestCount", ketSamplePart.getRequestCount());
		samplePartMap.put("dispensationCount", ketSamplePart.getDispensationCount());
		samplePartMap.put("receptionDate", DateUtil.getDateString(ketSamplePart.getReceptionDate(), "date"));
		samplePartMap.put("dispensationExpectDate", DateUtil.getDateString(ketSamplePart.getDispensationExpectDate(), "date"));
		samplePartMap.put("dispensationDate", DateUtil.getDateString(ketSamplePart.getDispensationDate(), "date"));

		samplePartMapList.add(samplePartMap);
	    }
	}

	return samplePartMapList;
    }

    @Override
    public PageControl sampleRequestFindPaging(SampleRequestDTO paramObject) throws Exception {
	QuerySpec query = sampleRequestGetQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1,
		    pageSessionId);
	}
	return pageControl;
    }

    private QuerySpec sampleRequestGetQuery(SampleRequestDTO paramObject) throws Exception {
	//
	// if (!StringUtil.isTrimToEmpty(paramObject.getRequstPartOid()) || !StringUtil.isTrimToEmpty(paramObject.getPmUserOid())
	// || !StringUtil.isTrimToEmpty(paramObject.getPmUserDeptOid())
	// || !StringUtil.isTrimToEmpty(paramObject.getDevelopeStageCode())) {
	// QuerySpec subQuery = new QuerySpec();
	// SearchCondition subSc = null;
	//
	// int partIdx = subQuery.appendClassList(KETSamplePart.class, true);
	// int samplePartLinkIdx = subQuery.appendClassList(KETSamplePartLink.class, true);
	//
	// ClassAttribute ca = new ClassAttribute(KETSamplePartLink.class, "roleAObjectRef.key.id");
	// subQuery.appendSelect(ca, new int[] { samplePartLinkIdx }, false);
	//
	// ClassAttribute partAttr = new ClassAttribute(KETSamplePart.class, "thePersistInfo.theObjectIdentifier.id");
	// ClassAttribute samplePartLinkAttr = new ClassAttribute(KETSamplePartLink.class, "roleBObjectRef.key.id");
	// subSc = new SearchCondition(partAttr, "=", samplePartLinkAttr);
	// subQuery.appendWhere(subSc, new int[] { partIdx, samplePartLinkIdx });
	//
	// String requstPartOid = paramObject.getRequstPartOid();
	// if (StringUtil.checkString(requstPartOid)) {
	// SearchUtil.appendLIKE(subQuery, KETSamplePart.class, "partReference.key.id", requstPartOid, partIdx);
	// }
	//
	// String pmUserOid = paramObject.getPmUserOid();
	// if (StringUtil.checkString(pmUserOid)) {
	// SearchUtil.appendLIKE(subQuery, KETSamplePart.class, "userReference.key.id", pmUserOid, partIdx);
	// }
	//
	// String pmUserDeptOid = paramObject.getPmUserDeptOid();
	// if (StringUtil.checkString(pmUserOid)) {
	// SearchUtil.appendLIKE(subQuery, KETSamplePart.class, "userReference.key.id", pmUserDeptOid, partIdx);
	// }
	//
	// String developeStageCode = paramObject.getDevelopeStageCode();
	// if (StringUtil.checkString(developeStageCode)) {
	// SearchUtil.appendLIKE(subQuery, KETSamplePart.class, "userReference.key.id", pmUserOid, partIdx);
	// }
	//
	// }

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;

	int idx = query.appendClassList(KETSampleRequest.class, true);
	if (!StringUtil.isTrimToEmpty(paramObject.getRequestTitle())) {
	    sc = new SearchCondition(KETSampleRequest.class, KETSampleRequest.REQUEST_TITLE, SearchCondition.LIKE, "%"
		    + paramObject.getRequestTitle() + "%", false);
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getRequestNo())) {
	    if (query.getConditionCount() > 0) {
		query.appendAnd();
	    }
	    sc = new SearchCondition(KETSampleRequest.class, KETSampleRequest.REQUEST_NO, SearchCondition.LIKE, "%"
		    + paramObject.getRequestNo() + "%", false);
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    // '-'내림차순
	    if (paramObject.getSortName().startsWith("-")) {
		SearchUtil.setOrderBy(query, KETSampleRequest.class, idx, paramObject.getSortName().substring(1), true);
	    } else {
		SearchUtil.setOrderBy(query, KETSampleRequest.class, idx, paramObject.getSortName(), false);
	    }
	} else {
	    SearchUtil.setOrderBy(query, KETSampleRequest.class, idx, KETSampleRequest.REQUEST_NO, true);
	}
	return query;
    }

    @Override
    public List<Map<String, String>> projectInfo(String partOid) throws Exception {
	String pjtno = "";
	String pjtOid = "";
	String pmUserName = "";
	String pmUserOid = "";

	WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);

	List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
	// 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
	// TKLEE 추가함.
	QueryResult partQr = PartUtil.getPartProjectLink(wtPart, null);
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
	sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + wtPart.getNumber() + "' ) )   \n");

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

	List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

	for (int i = 0; i < projectDataList.size(); i++) {
	    E3PSProjectData projectData = (E3PSProjectData) projectDataList.get(i);
	    Map<String, String> map = new HashMap<String, String>();

	    E3PSProject project = projectData.e3psProject;
	    pjtno = project.getMaster().getPjtNo();
	    pjtOid = CommonUtil.getOIDString(project);
	    pmUserName = projectData.pjtPmName;
	    WTUser pmUser = projectData.pjtPm;
	    pmUserOid = CommonUtil.getOIDString(pmUser);

	    map.put("pjtno", pjtno);
	    map.put("pjtOid", pjtOid);
	    map.put("pjtPmName", pmUserName);
	    map.put("pmUserOid", pmUserOid);

	    mapList.add(map);
	}

	return mapList;

    }

    @Override
    public SampleRequestDTO sampleRequestUpdate(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());

	ketSampleRequest.setRequestNo(getRequestNo());
	ketSampleRequest.setRequestTitle(paramObject.getRequestTitle());

	ketSampleRequest.setCarTypeCode(paramObject.getCarTypeCode());
	ketSampleRequest.setCarTypeName(paramObject.getCarTypeName());

	ketSampleRequest.setCustomerCode(paramObject.getCustomerCode());
	ketSampleRequest.setCustomerName(paramObject.getCustomerName());

	ketSampleRequest.setCustomerContractor(paramObject.getCustomerContractor());

	ketSampleRequest.setPurpose(paramObject.getPurpose());

	ketSampleRequest.setWebEditor(paramObject.getWebEditor());
	ketSampleRequest.setWebEditorText(paramObject.getWebEditorText());

	if (!(paramObject.getRequestDate().equals("") || paramObject.getRequestDate().equals(null))) {
	    ketSampleRequest.setRequestDate(DateUtil.getTimestampFormat(paramObject.getRequestDate(), "yyyy-MM-dd"));
	}

	WTContainerRef containerRef = WCUtil.getWTContainerRef();

	String folderPath = "/Default/자동차사업부/초기유동/";

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);

	// 요청서 저장
	ketSampleRequest = (KETSampleRequest) PersistenceHelper.manager.modify(ketSampleRequest);
	KETContentHelper.service.updateContent(ketSampleRequest, paramObject);

	// 부품 기존삭제
	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		PersistenceHelper.manager.delete(ketSamplePart);
	    }
	}
	// // 부품링크 기존삭제 자동삭제됨 위에 부품 삭제하면 링크 자동삭제됨
	// QuerySpec query = new QuerySpec();
	// int idx = query.appendClassList(KETSamplePartLink.class, true);
	// query.appendWhere(
	// new SearchCondition(KETSamplePartLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	// .getOIDLongValue(ketSampleRequest)), new int[] { idx });
	//
	// qr = PersistenceHelper.manager.find(query);
	//
	// while (qr.hasMoreElements()) {
	// KETSamplePartLink ketSamplePartLink = (KETSamplePartLink) qr.nextElement();
	// PersistenceHelper.manager.delete(ketSamplePartLink);
	// }
	String partOidArr[] = paramObject.getPartOidArr().split(",");
	String partPmUserOidArr[] = paramObject.getPartPmUserOidArr().split(",");
	String partRequestCountArr[] = paramObject.getPartRequestCountArr().split(",");
	String pjtoidArr[] = paramObject.getPjtoid().split(",");

	if (partOidArr.length == partPmUserOidArr.length && partPmUserOidArr.length == partRequestCountArr.length) {
	    for (int i = 0; i < partOidArr.length; i++) {
		KETSamplePart ketSamplePart = KETSamplePart.newKETSamplePart();

		WTPart wtPart = (WTPart) CommonUtil.getObject(partOidArr[i]);
		ketSamplePart.setPart(wtPart);

		if (pjtoidArr.length > i) {
		    if (StringUtil.checkString(pjtoidArr[i])) {
			ketSamplePart.setPjtoid(pjtoidArr[i]);
			E3PSProject project = (E3PSProject) CommonUtil.getObject(pjtoidArr[i]);
			ketSamplePart.setPjtno(project.getPjtNo());
		    }
		}

		WTUser wtUser = (WTUser) CommonUtil.getObject(partPmUserOidArr[i]);
		ketSamplePart.setUser(wtUser);

		if (!partRequestCountArr[i].equals("")) {
		    ketSamplePart.setRequestCount(Integer.parseInt(partRequestCountArr[i]));
		}

		String lcName = "KET_ECA_LC";
		LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketSamplePart,
		        LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

		FolderHelper.assignLocation((FolderEntry) ketSamplePart, folder);
		// 부품 저장
		ketSamplePart = (KETSamplePart) TeamHelper.setTeamTemplate(ketSamplePart, TeamHelper.service.getTeamTemplate("Default"));
		ketSamplePart = (KETSamplePart) PersistenceHelper.manager.save(ketSamplePart);

		// 요청서 부품 링크 저장
		KETSamplePartLink ketSamplePartLink = KETSamplePartLink.newKETSamplePartLink(ketSampleRequest, ketSamplePart);
		ketSamplePartLink = (KETSamplePartLink) PersistenceHelper.manager.save(ketSamplePartLink);
	    }
	}

	return paramObject;
    }

    @Override
    public SampleRequestDTO samplePartListUpdate(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String samplePartOidArr[] = paramObject.getSamplePartOidArr().split(",");
	String dispensationCountArr[] = paramObject.getDispensationCountArr().split(",");

	for (int i = 0; i < samplePartOidArr.length; i++) {
	    KETSamplePart ketSamplePart = (KETSamplePart) CommonUtil.getObject(samplePartOidArr[i]);

	    if (!dispensationCountArr.equals(null)) {
		if (dispensationCountArr.length > i) {
		    if (!StringUtil.checkNull(dispensationCountArr[i]).equals("")) {
			ketSamplePart.setDispensationCount(Integer.parseInt(dispensationCountArr[i]));
		    }
		}
	    }

	    ketSamplePart = (KETSamplePart) PersistenceHelper.manager.modify(ketSamplePart);

	}

	return paramObject;
    }

    @Override
    public SampleRequestDTO samplePartWorkComplete(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String samplePartOidArr[] = paramObject.getSamplePartOidArr().split(",");
	String dispensationCountArr[] = paramObject.getDispensationCountArr().split(",");

	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());

	boolean allDispansation = true;

	for (int i = 0; i < samplePartOidArr.length; i++) {
	    KETSamplePart ketSamplePart = (KETSamplePart) CommonUtil.getObject(samplePartOidArr[i]);

	    if (!dispensationCountArr.equals(null)) {
		if (dispensationCountArr.length > i) {
		    if (!StringUtil.checkNull(dispensationCountArr[i]).equals("")) {
			ketSamplePart.setDispensationCount(Integer.parseInt(dispensationCountArr[i]));
		    }
		}
	    }

	    ketSamplePart.setSamplePartStateCode("DISPENSATION");
	    ketSamplePart.setSamplePartStateName("불출");
	    ketSamplePart.setDispensationDate(DateUtil.getCurrentTimestamp());
	    ketSamplePart = (KETSamplePart) PersistenceHelper.manager.modify(ketSamplePart);

	    // todo 종료
	    WorkflowSearchHelper.manager.taskComplete(ketSamplePart);

	    if (i == samplePartOidArr.length - 1) {
		QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);
		if (qr.hasMoreElements()) {
		    while (qr.hasMoreElements()) {
			KETSamplePart checkKETSamplePart = (KETSamplePart) qr.nextElement();
			if (!checkKETSamplePart.getSamplePartStateCode().equals("DISPENSATION")) {
			    allDispansation = false;
			    break;
			}

		    }
		}
		if (allDispansation) {
		    // 부품이 전부 불출 되었는 확인후 불출되었다면 상태 불출바뀌고 투두생성
		    // 전체 불출완료
		    ketSampleRequest.setSampleRequestStateCode("DISPENSATION");
		    ketSampleRequest.setSampleRequestStateName("불출");

		    ketSampleRequest = (KETSampleRequest) PersistenceHelper.manager.modify(ketSampleRequest);

		    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    WfTemplateObject paramWfTemplateObject = null;

		    while (allTemplates.hasMoreElements()) {
			WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			if (wfTemplate.getName().equals("KET_TODO_WF")) {
			    paramWfTemplateObject = wfTemplate;
			}
		    }

		    Team team = (Team) ketSamplePart.getTeamId().getObject();
		    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			    ketSamplePart.getContainerReference());
		    ProcessData data = process.getContext();
		    WfEngineServerHelper.service.setPrimaryBusinessObject(process, ketSamplePart);
		    data.setValue("charge", (WTUser) ketSampleRequest.getCreator().getPrincipal());
		    WfEngineHelper.service.startProcess(process, data, 1);

		    List<WTUser> toUserList = new ArrayList<WTUser>();
		    toUserList.add((WTUser) ketSamplePart.getCreator().getPrincipal());
		    KETMailHelper.service.sendFormMail("08034", "NoticeMailLine2.html", ketSamplePart, ketSamplePart.getUser(), toUserList);
		} else {
		    // 불출완료
		    // 메일발송
		    List<WTUser> toUserList = new ArrayList<WTUser>();
		    toUserList.add((WTUser) ketSamplePart.getCreator().getPrincipal());
		    KETMailHelper.service.sendFormMail("08120", "NoticeMailLine2.html", ketSamplePart, ketSamplePart.getUser(), toUserList);
		}
	    } else {
		// 불출완료
		// 메일발송
		List<WTUser> toUserList = new ArrayList<WTUser>();
		toUserList.add((WTUser) ketSamplePart.getCreator().getPrincipal());
		KETMailHelper.service.sendFormMail("08120", "NoticeMailLine2.html", ketSamplePart, ketSamplePart.getUser(), toUserList);
	    }
	}

	return paramObject;
    }

    @Override
    public SampleRequestDTO samplePartReception(SampleRequestDTO paramObject) throws Exception {
	String samplePartOidArr[] = paramObject.getSamplePartOidArr().split(",");
	String dispensationExpectDateArr[] = paramObject.getDispensationExpectDateArr().split(",");

	for (int i = 0; i < samplePartOidArr.length; i++) {
	    KETSamplePart ketSamplePart = (KETSamplePart) CommonUtil.getObject(samplePartOidArr[i]);

	    ketSamplePart.setReceptionDate(DateUtil.getCurrentTimestamp());

	    if (!dispensationExpectDateArr.equals(null)) {
		if (dispensationExpectDateArr.length > i) {
		    if (!StringUtil.checkNull(dispensationExpectDateArr[i]).equals("")) {
			ketSamplePart.setDispensationExpectDate(DateUtil.getTimestampFormat(dispensationExpectDateArr[i], "yyyy-MM-dd"));
		    }
		}
	    }
	    ketSamplePart = (KETSamplePart) PersistenceHelper.manager.modify(ketSamplePart);

	}

	return paramObject;
    }

    @Override
    public SampleRequestDTO reRequest(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String[] checkPartArr = paramObject.getPartCheck();
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());

	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		WorkflowSearchHelper.manager.taskComplete(ketSamplePart);
	    }
	}

	ketSampleRequest.setSampleRequestStateCode("REREQUEST");
	ketSampleRequest.setSampleRequestStateName("요청");
	ketSampleRequest.setWebEditor(paramObject.getWebEditor());
	ketSampleRequest.setWebEditorText(paramObject.getWebEditorText());

	ketSampleRequest = (KETSampleRequest) PersistenceHelper.manager.modify(ketSampleRequest);

	Vector<WTUser> wtUserVector = new Vector<WTUser>();
	Vector<KETSamplePart> KETSampePartVector = new Vector<KETSamplePart>();

	for (int i = 0; i < checkPartArr.length; i++) {
	    KETSamplePart ketSamplePart = (KETSamplePart) CommonUtil.getObject(checkPartArr[i]);

	    ketSamplePart.setSamplePartStateCode("REQUEST");
	    ketSamplePart.setSamplePartStateName("요청");
	    ketSamplePart.setDispensationExpectDate(null);
	    ketSamplePart.setDispensationDate(null);
	    ketSamplePart.setDispensationCount(0);
	    ketSamplePart.setReceptionDate(null);

	    ketSamplePart = (KETSamplePart) PersistenceHelper.manager.modify(ketSamplePart);

	    boolean addFlag = true;

	    for (int j = 0; j < wtUserVector.size(); j++) {
		if (wtUserVector.get(j).equals(ketSamplePart.getUser())) {
		    addFlag = false;
		}
	    }
	    if (addFlag) {
		wtUserVector.add(ketSamplePart.getUser());
		KETSampePartVector.add(ketSamplePart);
	    }
	}

	for (int i = 0; i < KETSampePartVector.size(); i++) {
	    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
	    WfTemplateObject paramWfTemplateObject = null;

	    while (allTemplates.hasMoreElements()) {
		WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

		if (wfTemplate.getName().equals("KET_TODO_WF")) {
		    paramWfTemplateObject = wfTemplate;
		}
	    }

	    Team team = (Team) ((KETSamplePart) KETSampePartVector.get(i)).getTeamId().getObject();
	    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
		    ((KETSamplePart) KETSampePartVector.get(i)).getContainerReference());
	    ProcessData data = process.getContext();
	    WfEngineServerHelper.service.setPrimaryBusinessObject(process, ((KETSamplePart) KETSampePartVector.get(i)));
	    data.setValue("charge", ((KETSamplePart) KETSampePartVector.get(i)).getUser());
	    WfEngineHelper.service.startProcess(process, data, 1);

	}

	return paramObject;
    }

    @Override
    public SampleRequestDTO sampleComplete(SampleRequestDTO paramObject) throws Exception {
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());

	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		WorkflowSearchHelper.manager.taskComplete(ketSamplePart);
		ketSamplePart.setSamplePartStateCode("COMPLETE");
		ketSamplePart.setSamplePartStateName("완료");

		ketSamplePart = (KETSamplePart) PersistenceHelper.manager.modify(ketSamplePart);

	    }
	}

	ketSampleRequest.setSampleRequestStateCode("COMPLETE");
	ketSampleRequest.setSampleRequestStateName("완료");

	ketSampleRequest = (KETSampleRequest) PersistenceHelper.manager.modify(ketSampleRequest);

	return paramObject;
    }

    @Override
    public SampleRequestDTO sampleDelete(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETSampleRequest ketSampleRequest = (KETSampleRequest) CommonUtil.getObject(paramObject.getOid());
	// 부품 기존삭제
	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		PersistenceHelper.manager.delete(ketSamplePart);
	    }
	}

	// // 부품링크 기존삭제 자동삭제됨 위에 부품 삭제하면 링크 자동삭제됨
	// QuerySpec query = new QuerySpec();
	// int idx = query.appendClassList(KETSamplePartLink.class, true);
	// query.appendWhere(
	// new SearchCondition(KETSamplePartLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, CommonUtil
	// .getOIDLongValue(ketSampleRequest)), new int[] { idx });
	//
	// qr = PersistenceHelper.manager.find(query);
	//
	// while (qr.hasMoreElements()) {
	// KETSamplePartLink ketSamplePartLink = (KETSamplePartLink) qr.nextElement();
	// PersistenceHelper.manager.delete(ketSamplePartLink);
	// }
	PersistenceHelper.manager.delete(ketSampleRequest);

	return paramObject;
    }

    @Override
    public List<Map<String, Object>> reRequsetCreateSamplePartListView(SampleRequestDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	String checkPartArr[] = paramObject.getPartCheck();

	List<Map<String, Object>> samplePartMapList = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < checkPartArr.length; i++) {
	    KETSamplePart ketSamplePart = (KETSamplePart) CommonUtil.getObject(checkPartArr[i]);
	    HashMap<String, Object> samplePartMap = new HashMap<String, Object>();

	    samplePartMap.put("samplePartOid", CommonUtil.getOIDString(ketSamplePart));
	    samplePartMap.put("partOid", CommonUtil.getOIDString(ketSamplePart.getPart()));
	    samplePartMap.put("partNo", ketSamplePart.getPart().getNumber());
	    samplePartMap.put("partName", ketSamplePart.getPart().getName());
	    samplePartMap.put("ver", ketSamplePart.getPart().getVersionInfo().getIdentifier().getValue());
	    samplePartMap.put("pmUserOid", CommonUtil.getOIDString(ketSamplePart.getUser()));
	    PeopleData peopleData = new PeopleData(ketSamplePart.getUser());
	    samplePartMap.put("pmUserName", peopleData.name);
	    samplePartMap.put("developeStageName",
		    PartUtil.getSpDevLevelText(PartSpecGetter.getPartSpec(ketSamplePart.getPart(), PartSpecEnum.SpPartDevLevel)));

	    String pjtno = ketSamplePart.getPjtno();
	    String pjtOid = ketSamplePart.getPjtoid();

	    List<E3PSProjectData> projectDataList = new ArrayList<E3PSProjectData>();
	    // 부품 등록, 수정시 등록하는 프로젝트 정보(KETPartProjectLink)를 먼저 가져온다.
	    // TKLEE 추가함.
	    QueryResult partQr = PartUtil.getPartProjectLink(ketSamplePart.getPart(), null);
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
	    sql.append("AND ( PJT.IDA2A2 IN (SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM = '" + ketSamplePart.getPart().getNumber()
		    + "' ) )   \n");

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

	    for (int j = 0; j < returnObjList.size(); j++) {
		E3PSProject project = (E3PSProject) CommonUtil.getObject(StringUtil.checkNull((String) returnObjList.get(j).get("oid")));
		if (partPjtMast != null) {
		    if (partPjtMast.getPjtNo().equals(project.getPjtNo())) {
			continue;
		    }
		}
		projectDataList.add(new E3PSProjectData(project));
	    }

	    List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

	    for (int j = 0; j < projectDataList.size(); j++) {
		E3PSProjectData projectData = (E3PSProjectData) projectDataList.get(j);
		Map<String, String> map = new HashMap<String, String>();

		E3PSProject project = projectData.e3psProject;
		String selectPjtno = project.getMaster().getPjtNo();
		String selectPjtOid = CommonUtil.getOIDString(project);

		map.put("selectPjtno", selectPjtno);
		map.put("selectPjtOid", selectPjtOid);

		mapList.add(map);
	    }

	    samplePartMap.put("projectNo", pjtno);
	    samplePartMap.put("projectOid", pjtOid);

	    samplePartMap.put("selectPjtList", mapList);

	    samplePartMap.put("requestCount", ketSamplePart.getRequestCount());
	    samplePartMap.put("dispensationCount", ketSamplePart.getDispensationCount());
	    samplePartMap.put("receptionDate", DateUtil.getDateString(ketSamplePart.getReceptionDate(), "date"));
	    samplePartMap.put("dispensationExpectDate", DateUtil.getDateString(ketSamplePart.getDispensationExpectDate(), "date"));
	    samplePartMap.put("dispensationDate", DateUtil.getDateString(ketSamplePart.getDispensationDate(), "date"));

	    samplePartMapList.add(samplePartMap);

	}

	return samplePartMapList;
    }

    @Override
    public Map<String, String> projectPMUserInfo(String pjtoid) throws Exception {

	E3PSProject project = (E3PSProject) CommonUtil.getObject(pjtoid);

	E3PSProjectData projectData = new E3PSProjectData(project);
	Map<String, String> map = new HashMap<String, String>();

	String pmUserName = projectData.pjtPmName;
	WTUser pmUser = projectData.pjtPm;
	String pmUserOid = CommonUtil.getOIDString(pmUser);

	map.put("pjtPmName", pmUserName);
	map.put("pmUserOid", pmUserOid);

	return map;
    }
}