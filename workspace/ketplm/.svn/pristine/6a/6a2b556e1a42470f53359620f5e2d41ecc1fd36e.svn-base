package ext.ket.arm.service;

import java.util.List;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.PageControl;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.entity.KETAnalysisDivisionLink;
import ext.ket.arm.entity.KETAnalysisInfoDTO;
import ext.ket.arm.entity.KETAnalysisMasterInfoLink;
import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.arm.entity.KETAnalysisRequestMaster;

public class StandardKETAnalysisInfoService extends StandardManager implements KETAnalysisInfoService {
    private static final long serialVersionUID = 1L;

    /**
     * Default factory for the class.
     * 
     * @return
     * @throws WTException
     * @메소드명 : newStandardSampleService
     * @작성자 : hooni
     * @작성일 : 2014. 10. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static StandardKETAnalysisInfoService newStandardKETAnalysisInfoService() throws WTException {
	StandardKETAnalysisInfoService instance = new StandardKETAnalysisInfoService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<KETAnalysisRequestInfo> find(KETAnalysisInfoDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PageControl findPaging(KETAnalysisInfoDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public KETAnalysisRequestInfo save(KETAnalysisInfoDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	WTContainerRef containerRef = WCUtil.getWTContainerRef();
	String folderPath = "/Default/자동차사업부/문서/해석의뢰문서/접수문서";

	KETAnalysisRequestMaster analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(paramObject.getmOid());

	//
	// MASTER 해석구분
	// 기존 삭제
	QueryResult qsd = PersistenceHelper.manager.navigate(analysis, KETAnalysisDivisionLink.DIVISION_ROLE,
	        KETAnalysisDivisionLink.class, false);
	if (qsd.hasMoreElements()) {
	    while (qsd.hasMoreElements()) {
		KETAnalysisDivisionLink plink = (KETAnalysisDivisionLink) qsd.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}

	// 등록
	String[] divArr = paramObject.getAnalysisDivision().split(",");
	NumberCode divNum = null;
	for (int i = 0; i < divArr.length; i++) {
	    divNum = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + divArr[i].toString());
	    if (divNum != null) {
		KETAnalysisDivisionLink dlink = KETAnalysisDivisionLink.newKETAnalysisDivisionLink(divNum, analysis);
		PersistenceHelper.manager.save(dlink);
	    }
	}

	// INFO 등록
	KETAnalysisRequestInfo analysisInfo = null;
	analysisInfo = KETAnalysisRequestInfo.newKETAnalysisRequestInfo();
	//
	// 결재 관련
	LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC_PMS", WCUtil.getWTContainerRef());
	LifeCycleHelper.setLifeCycle(analysisInfo, LCtemplate);

	analysisInfo.setWebEditor(paramObject.getWebEditor());
	analysisInfo.setWebEditorText(paramObject.getWebEditorText());
	analysisInfo.setDesignSpec(paramObject.getDesignSpec());
	analysisInfo.setRequestedTerm(paramObject.getRequestedTerm());
	analysisInfo.setEtc(paramObject.getEtc());

	// System.out.println("mOid ==================> " + paramObject.getmOid());
	// System.out.println("webEditor ==================> " + paramObject.getWebEditor());
	// System.out.println("webEditorText ==================> " + paramObject.getWebEditorText());
	// System.out.println("DesignSpec ==================> " + paramObject.getDesignSpec());
	// System.out.println("RequestedTerm ==================> " + paramObject.getRequestedTerm());
	// System.out.println("Etc ==================> " + paramObject.getEtc());

	SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	FolderHelper.assignLocation((FolderEntry) analysisInfo, folder);

	KETAnalysisRequestHelper.service.approvalMastermodify(paramObject.getmOid(), paramObject.getApprovalUser());

	analysisInfo = (KETAnalysisRequestInfo) PersistenceHelper.manager.save(analysisInfo);

	// 해석의뢰 링크 생성
	KETAnalysisMasterInfoLink miLink = KETAnalysisMasterInfoLink.newKETAnalysisMasterInfoLink(analysisInfo, analysis);
	PersistenceHelper.manager.save(miLink);

	return analysisInfo;
    }

    @Override
    public KETAnalysisRequestInfo modify(KETAnalysisInfoDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETAnalysisRequestMaster analysis = (KETAnalysisRequestMaster) CommonUtil.getObject(paramObject.getmOid());

	// MASTER 해석구분
	// 기존 삭제
	QueryResult qsd = PersistenceHelper.manager.navigate(analysis, KETAnalysisDivisionLink.DIVISION_ROLE,
	        KETAnalysisDivisionLink.class, false);
	if (qsd.hasMoreElements()) {
	    while (qsd.hasMoreElements()) {
		KETAnalysisDivisionLink plink = (KETAnalysisDivisionLink) qsd.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}

	// 등록
	String[] divArr = paramObject.getAnalysisDivision().split(",");
	NumberCode divNum = null;
	for (int i = 0; i < divArr.length; i++) {
	    divNum = (NumberCode) CommonUtil.getObject("e3ps.common.code.NumberCode:" + divArr[i].toString());
	    if (divNum != null) {
		KETAnalysisDivisionLink dlink = KETAnalysisDivisionLink.newKETAnalysisDivisionLink(divNum, analysis);
		PersistenceHelper.manager.save(dlink);
	    }
	}

	// INFO 등록
	KETAnalysisRequestInfo analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(getAnalysisInfoOid(paramObject.getmOid()));

	analysisInfo.setWebEditor(paramObject.getWebEditor());
	analysisInfo.setWebEditorText(paramObject.getWebEditorText());
	analysisInfo.setDesignSpec(paramObject.getDesignSpec());
	analysisInfo.setRequestedTerm(paramObject.getRequestedTerm());
	analysisInfo.setEtc(paramObject.getEtc());

	// System.out.println("mOid ==================> " + paramObject.getmOid());
	// System.out.println("webEditor ==================> " + paramObject.getWebEditor());
	// System.out.println("webEditorText ==================> " + paramObject.getWebEditorText());
	// System.out.println("DesignSpec ==================> " + paramObject.getDesignSpec());
	// System.out.println("RequestedTerm ==================> " + paramObject.getRequestedTerm());
	// System.out.println("Etc ==================> " + paramObject.getEtc());

	analysisInfo = (KETAnalysisRequestInfo) PersistenceHelper.manager.modify(analysisInfo);
	return analysisInfo;
    }

    @Override
    public KETAnalysisRequestInfo delete(KETAnalysisInfoDTO paramObject) throws Exception {
	// TODO Auto-generated method stub
	KETAnalysisRequestInfo analysisInfo = null;
	analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(paramObject.getAnalysisInfoOid());

	// Link 삭제
	QueryResult qsc = PersistenceHelper.manager.navigate(analysisInfo, KETAnalysisMasterInfoLink.INFO_ROLE,
	        KETAnalysisMasterInfoLink.class, false);
	if (qsc.hasMoreElements()) {
	    while (qsc.hasMoreElements()) {
		KETAnalysisMasterInfoLink plink = (KETAnalysisMasterInfoLink) qsc.nextElement();
		PersistenceHelper.manager.delete(plink);
	    }
	}

	PersistenceHelper.manager.delete(analysisInfo);
	return null;
    }

    /**
     * 해석의뢰 Master과 연결된 Info ida2a2 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisInfoOid
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisInfoOid(String analysisOid) throws Exception {
	String analysisInfoId = "";
	long analysisId = CommonUtil.getOIDLongValue(analysisOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx = query.appendClassList(KETAnalysisMasterInfoLink.class, false);

	query.appendSelectAttribute("roleAObjectRef.key.classname", idx, false);
	query.appendSelectAttribute("roleAObjectRef.key.id", idx, false);

	query.appendWhere(new SearchCondition(KETAnalysisMasterInfoLink.class, "roleBObjectRef.key.id", "=", analysisId), new int[] { idx });
	QueryResult rs = PersistenceHelper.manager.find(query);

	if (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    analysisInfoId = String.valueOf(objects[0] + ":" + objects[1]);
	}
	return analysisInfoId;
    }

    /**
     * 해석의뢰 INFO와 연결된 Master ida2a2 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisMasterOid
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 31.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisMasterOid(String analysisInfoOid) throws Exception {
	String analysisId = "";
	long analysisInfoId = CommonUtil.getOIDLongValue(analysisInfoOid);

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idx = query.appendClassList(KETAnalysisMasterInfoLink.class, false);

	query.appendSelectAttribute("roleBObjectRef.key.classname", idx, false);
	query.appendSelectAttribute("roleBObjectRef.key.id", idx, false);

	query.appendWhere(new SearchCondition(KETAnalysisMasterInfoLink.class, "roleAObjectRef.key.id", "=", analysisInfoId),
	        new int[] { idx });
	QueryResult rs = PersistenceHelper.manager.find(query);

	if (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    analysisId = String.valueOf(objects[0] + ":" + objects[1]);
	}
	return analysisId;
    }

    /**
     * 해석의뢰 Master의 제목(title) 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisInfoOid
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisInfoTitle(long analysisInfoOid) throws Exception {
	String analysisInfoTitle = "";

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);
	SearchCondition sc = null;

	int idxMaster = query.appendClassList(KETAnalysisRequestMaster.class, false);
	int idxLink = query.appendClassList(KETAnalysisMasterInfoLink.class, false);

	query.appendSelectAttribute("analysisTitle", idxMaster, false);

	ClassAttribute cm = null;
	ClassAttribute cl = null;

	cm = new ClassAttribute(KETAnalysisRequestMaster.class, "thePersistInfo.theObjectIdentifier.id");
	cl = new ClassAttribute(KETAnalysisMasterInfoLink.class, "roleBObjectRef.key.id");

	sc = new SearchCondition(cm, SearchCondition.EQUAL, cl);
	query.appendWhere(sc, new int[] { idxMaster, idxLink });
	query.appendAnd();
	query.appendWhere(new SearchCondition(KETAnalysisMasterInfoLink.class, "roleAObjectRef.key.id", "=", analysisInfoOid),
	        new int[] { idxLink });

	QueryResult rs = PersistenceHelper.manager.find(query);

	if (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    analysisInfoTitle = String.valueOf(objects[0]);
	}
	return analysisInfoTitle;
    }

    /**
     * 해석의뢰 결재상태 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisInfoOid
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisInfoState(String analysisOid) throws Exception {
	KETAnalysisRequestInfo analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(KETAnalysisInfoHelper.service
	        .getAnalysisInfoOid(analysisOid));
	return analysisInfo.getLifeCycleState().toString();
    }

    /**
     * 해석의뢰 결재상태 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisInfoOid
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisInfoEndDate(String analysisOid) throws Exception {
	String infoEndDate = "";
	KETAnalysisRequestInfo analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(KETAnalysisInfoHelper.service
	        .getAnalysisInfoOid(analysisOid));
	WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(analysisInfo)));
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	Object temp = new Object();
	Vector vec = null;
	if (master != null) {
	    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	    if (vec != null) {
		String activityName = "&nbsp;";
		for (int i = 0; i < vec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토")) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "").equals("승인")) {
			    if (history.getCompletedDate() != null) {
				infoEndDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
			    }
			}
		    }
		}
	    }
	}
	return infoEndDate;
    }

    /**
     * 해석의뢰 결재상태 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getAnalysisInfoOid
     * @작성자 : Hooni
     * @작성일 : 2014. 12. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getAnalysisInfoStateName(String analysisOid) throws Exception {
	String infoStateName = "";
	KETAnalysisRequestInfo analysisInfo = (KETAnalysisRequestInfo) CommonUtil.getObject(KETAnalysisInfoHelper.service
	        .getAnalysisInfoOid(analysisOid));
	if ("INWORK".equals(analysisInfo.getLifeCycleState().toString()) || "REWORK".equals(analysisInfo.getLifeCycleState().toString())) {
	    infoStateName = "의뢰접수중";
	} else if ("UNDERREVIEW".equals(analysisInfo.getLifeCycleState().toString())) {
	    infoStateName = "의뢰검토중";
	} else if ("APPROVED".equals(analysisInfo.getLifeCycleState().toString())) {
	    infoStateName = "의뢰완료";
	}
	return infoStateName;
    }

    /**
     * 해석 보고서 OID 호출
     * 
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getKETProjectDocumentOid
     * @작성자 : Hooni
     * @작성일 : 2016. 03. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public String getKETProjectDocumentOid(String analysisOid) throws Exception {
	String documentId = "";

	QuerySpec query = new QuerySpec();
	query.setDescendantQuery(false);

	int documentIdx = query.appendClassList(KETProjectDocument.class, false);

	query.appendSelectAttribute("thePersistInfo.theObjectIdentifier.classname", documentIdx, false);
	query.appendSelectAttribute("thePersistInfo.theObjectIdentifier.id", documentIdx, false);

	query.appendWhere(new SearchCondition(KETProjectDocument.class, "attribute2", "=", analysisOid), new int[] { documentIdx });
	QueryResult rs = PersistenceHelper.manager.find(query);

	int i = 0;
	while (rs.hasMoreElements()) {
	    Object[] objects = (Object[]) rs.nextElement();
	    String r_name = String.valueOf(objects[0] + ":" + objects[1]);
	    if (i != 0) {
		documentId += ",";
	    }
	    documentId += r_name;
	    i++;
	}
	return documentId;
    }

}