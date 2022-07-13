package ext.ket.project.gate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.springframework.web.multipart.MultipartFile;

import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleServerHelper;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.pdmlink.PDMLinkProduct;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.project.E3PSProject;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import e3ps.project.TemplateProject;
import ext.ket.project.gate.entity.AssSheetGateChkSheetLink;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.AssessSheetDTO;
import ext.ket.project.gate.entity.GateAttribute;
import ext.ket.project.gate.entity.GateCheckSheet;
import ext.ket.project.gate.entity.GateCheckSheetDTO;
import ext.ket.project.gate.entity.ProjectAssSheetLink;
import ext.ket.project.gate.entity.TemplateAssessSheet;
import ext.ket.project.gate.entity.TemplateGateCheckSheet;
import ext.ket.project.gate.entity.TmplAssShtTmplGateChkLink;
import ext.ket.project.task.entity.GateTaskOutputDTO;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.SessionUtil;

public class StandardAssessSheetService extends StandardManager implements AssessSheetService {
    private static final long serialVersionUID = 1L;

    public static StandardAssessSheetService newStandardAssessSheetService() throws WTException {
	StandardAssessSheetService instance = new StandardAssessSheetService();
	instance.initialize();
	return instance;
    }

    /**
     * 프로젝트 평가시트 조회하는 쿼리스펙을 리턴하는 함수(미사용)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQuery(AssessSheetDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx = query.appendClassList(AssessSheet.class, true);
	if (!StringUtil.isTrimToEmpty(paramObject.getDevType())) {
	    sc = new SearchCondition(AssessSheet.class, AssessSheet.DEV_TYPE, SearchCondition.LIKE, "%" + paramObject.getDevType());
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getDevDiv())) {
	    sc = new SearchCondition(AssessSheet.class, AssessSheet.DEV_DIV, SearchCondition.LIKE, "%" + paramObject.getDevDiv());
	    query.appendWhere(sc, new int[] { idx });
	}
	if (!StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    if (paramObject.getSortName().startsWith("-")) {
		SearchUtil.setOrderBy(query, AssessSheet.class, idx, paramObject.getSortName().substring(1), true);
	    } else {
		SearchUtil.setOrderBy(query, AssessSheet.class, idx, paramObject.getSortName(), false);
	    }
	} else {
	    SearchUtil.setOrderBy(query, AssessSheet.class, idx, AssessSheet.DEV_DIV, true);
	}
	return query;
    }

    /**
     * 프로젝트 평가시트 조회하는 쿼리스펙을 리턴하는 함수(미사용)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getProjectGateResult(String pjtOid) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	StringBuffer sb = new StringBuffer(0);
	sb.append("with GATE_BASE AS (select		");
	sb.append("         t.ida2a2 oid		");
	sb.append("        , o.idA3b5		");
	sb.append("        , o.outputName    		");
	sb.append("        , wu.FULLNAME		");
	sb.append("        , assLink.outputCheck		");
	sb.append("        , assLink.outputComment		");
	sb.append("        , level lev		");
	sb.append("        , T.TASKTYPE		");
	sb.append("        , T.TASKNAME		");
	sb.append("        from E3PSTask t		");
	sb.append("        ,(SELECT * FROM ProductProject WHERE ida2a2 ='893393238') p		");
	sb.append("        , projectOutput o		");
	sb.append("        , TASKMEMBERLINK tml		");
	sb.append("        , PROJECTMEMBERLINK pml		");
	sb.append("        , WTUSER wu		");
	sb.append("        , KETAssessResultOutputLink assLink		");
	sb.append("        , KETGateAssessResult gateRst		");
	sb.append("        WHERE t.ida3b4 = p.ida2a2		");
	sb.append("        AND t.ida2a2 = o.idA3b5(+)		");
	sb.append("        AND t.ida2a2 = tml.ida3a5(+)		");
	sb.append("        --AND T.TASKTYPE ='Gate'		");
	sb.append("        AND tml.TASKMEMBERTYPE(+) = '1'		");
	sb.append("        AND tml.ida3b5 = pml.ida2a2(+)		");
	sb.append("        AND pml.ida3b5 = wu.ida2a2(+)		");
	sb.append("        AND o.ida2a2 = assLink.idA3B5(+)		");
	sb.append("        AND assLink.idA3A5 = gateRst.ida2a2(+)		");
	sb.append("        START WITH t.ida3parentreference = 0   		");
	sb.append("        CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference		");
	sb.append("        ORDER SIBLINGS BY t.taskseq		");
	sb.append("),		");
	sb.append("GATE_OUTPUT AS (		");
	sb.append("        SELECT TOT.oid , 		");
	sb.append("         SUM(DECODE(NVL(TOT.outputCheck,''),'1',1,0))		");
	sb.append("         ||'/'||SUM(DECODE(NVL(TOT.outputName,''),'',0,1)) OUTPUT_CHECK		");
	sb.append("        FROM (		");
	sb.append("        select 		");
	sb.append("         t.ida2a2 oid		");
	sb.append("        , T.TASKNAME		");
	sb.append("        , o.outputName    		");
	sb.append("        , assLink.outputCheck		");
	sb.append("        from E3PSTask t		");
	sb.append("        ,(SELECT * FROM ProductProject WHERE ida2a2 ='893393238') p		");
	sb.append("        , projectOutput o		");
	sb.append("        , KETAssessResultOutputLink assLink		");
	sb.append("        WHERE t.ida3b4 = p.ida2a2		");
	sb.append("        AND t.ida2a2 = o.idA3b5(+)		");
	sb.append("        AND o.ida2a2 = assLink.idA3B5(+)		");
	sb.append("        --AND T.TASKTYPE ='Gate'		");
	sb.append("        ) TOT		");
	sb.append("        GROUP BY TOT.oid		");
	sb.append("),		");
	sb.append("GATE_ASSESS AS (		");
	sb.append("        SELECT CHK_TOT.IDA2A2 OID,		");
	sb.append("            ROUND(SUM(CHK_TOT.CRI)/COUNT(CHK_TOT.CRI),2)*100 ASS_RATIO		");
	sb.append("        FROM		");
	sb.append("        (		");
	sb.append("        select 		");
	sb.append("        -- CHK.*--ida2a2 oid		");
	sb.append("        --,CHK.checkSheetName		");
	sb.append("         CHK.ORDERNO		");
	sb.append("        , CHK.CRITERIA		");
	sb.append("        , t.IDA2A2		");
	sb.append("        , DECODE(CHK.CRITERIA,0,'>=', 1,'=',2,'<=') CH		");
	sb.append("        , CHK.target1B4		");
	sb.append("        , CHK.result1B4		");
	sb.append("        , DECODE(CHK.CRITERIA,0, 		");
	sb.append("                (		");
	sb.append("                    CASE		");
	sb.append("                        WHEN (CHK.target1B4>=CHK.result1B4)		");
	sb.append("                        THEN 1		");
	sb.append("                        ELSE 0		");
	sb.append("                        END		");
	sb.append("                ),		");
	sb.append("            1,		");
	sb.append("                (		");
	sb.append("                    CASE		");
	sb.append("                        WHEN (CHK.target1B4=CHK.result1B4)		");
	sb.append("                        THEN 1		");
	sb.append("                        ELSE 0		");
	sb.append("                        END		");
	sb.append("                ),		");
	sb.append("            2,		");
	sb.append("                (		");
	sb.append("                    CASE		");
	sb.append("                        WHEN (CHK.target1B4<=CHK.result1B4)		");
	sb.append("                        THEN 1		");
	sb.append("                        ELSE 0		");
	sb.append("                        END		");
	sb.append("                )		");
	sb.append("            ) CRI		");
	sb.append("        from E3PSTask t		");
	sb.append("        , (SELECT * FROM ProductProject WHERE ida2a2 ='893393238') PRJ		");
	sb.append("        , ProjectAssSheetLink PLINK		");
	sb.append("        , KETASSESSSHEET ASS		");
	sb.append("        , KETGATECHECKSHEET CHK		");
	sb.append("        , AssSheetGateChkSheetLink ALINK		");
	sb.append("        WHERE t.ida3b4 = PRJ.ida2a2		");
	sb.append("        AND PLINK.idA3B5 = PRJ.IDA2A2		");
	sb.append("        AND PLINK.idA3A5 = ASS.IDA2A2		");
	sb.append("        AND ALINK.idA3B5 = ASS.IDA2A2		");
	sb.append("        AND ALINK.idA3A5 = CHK.IDA2A2		");
	sb.append("        ) CHK_TOT		");
	sb.append("        GROUP BY CHK_TOT.IDA2A2		");
	sb.append(")		");
	sb.append("select A.OID		");
	sb.append(", B.OUTPUT_CHECK		");
	sb.append(", C.ASS_RATIO		");
	sb.append("from GATE_BASE A		");
	sb.append(", GATE_OUTPUT B		");
	sb.append(", GATE_ASSESS C		");
	sb.append("where A.OID = b.OID		");
	sb.append("and A.OID = C.OID(+)		");
	return query;
    }

    /**
     * 프로젝트 평가시트 조회하는 함수(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : find
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<AssessSheet> find(AssessSheetDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<AssessSheet> assList = new ArrayList<AssessSheet>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assList.add((AssessSheet) objArr[0]);
	}
	return assList;
    }

    /**
     * 프로젝트 평가시트를 삭제하는 함수(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public AssessSheet delete(AssessSheetDTO paramObject) throws Exception {
	AssessSheet ass = (AssessSheet) CommonUtil.getObject(paramObject.getOid());
	return (AssessSheet) PersistenceHelper.manager.delete(ass);
    }

    /**
     * 프로젝트 평가시트 조회하는 함수. 서버 페이징 쿼리처리(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : findPaging
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PageControl findPaging(AssessSheetDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), query);
	} else {
	    pageControl = CommonSearchHelper.find(paramObject.getFormPage(), paramObject.getFormPage(), paramObject.getPage() + 1, pageSessionId);
	}
	return pageControl;
    }

    /**
     * 프로젝트 평가시트 조회하는 함수. 서버 페이징 쿼리처리(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : findRevisedProjectAssess
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<AssessSheet> findRevisedProjectAssess(AssessSheetDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;

	List<AssessSheet> listAssesSheet = new ArrayList<AssessSheet>();

	AssessSheet assSheet = this.getAssessSheetFromPjtOid(paramObject.getPjtOid());
	String assOid = "";
	if (assSheet != null) {
	    assOid = assSheet.getPersistInfo().getObjectIdentifier().toString();
	    AssessSheet sheet = (AssessSheet) CommonUtil.getObject(assOid);

	    if (sheet != null) {

		QueryResult result = VersionControlHelper.service.allVersionsOf(sheet.getMaster());
		if ((result == null) || (result.size() == 0)) {

		} else {
		    AssessSheet revision = null;
		    while (result.hasMoreElements()) {
			revision = (AssessSheet) result.nextElement();
			listAssesSheet.add(revision);
		    }
		}
	    }
	}

	return listAssesSheet;
    }

    /**
     * 프로젝트 평가시트 수정하는 함수(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : modify
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public AssessSheet modify(AssessSheetDTO paramObject) throws Exception {
	AssessSheet modify = (AssessSheet) CommonUtil.getObject(paramObject.getOid());

	// doc Check Out
	// AssessSheet assSheetCopy = checkoutDoc(modify);

	modify = setAssessAttribute(modify, paramObject);

	// doc save
	modify = (AssessSheet) PersistenceHelper.manager.modify(modify);

	// doc Check In
	// assSheetCopy = (AssessSheet) wt.vc.wip.WorkInProgressHelper.service.checkin(assSheetCopy, "");
	// assSheetCopy = (AssessSheet) PersistenceHelper.manager.refresh(assSheetCopy, true, true); // refresh

	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 프로젝트 평가시트 저장하는 함수(현재 미사용)
     * 
     * @param AssessSheet
     * @return
     * @throws Exception
     * @메소드명 : save
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public AssessSheet save(AssessSheetDTO paramObject) throws Exception {
	AssessSheet modify = AssessSheet.newAssessSheet();
	long todaytime = System.currentTimeMillis();
	Kogger.debug(getClass(), "assessSeq1:" + todaytime);
	SimpleDateFormat militime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	String assessSeq = "ASS" + militime.format(new Date(todaytime));
	Kogger.debug(getClass(), "assessSeq2:" + assessSeq);

	NumberCode divNumberCode = modify.getDivision();
	String folderDir = "";
	if (divNumberCode != null) {
	    if ("2".equals(divNumberCode.getCode())) {
		folderDir = "/전자사업부/프로젝트/GATE";
	    } else { // if("1".equals(divNumberCode.getCode())) {
		folderDir = "/자동차사업부/프로젝트/GATE";
	    }
	} else {
	    folderDir = "/자동차사업부/프로젝트/GATE";
	}
	// 폴더지정
	PDMLinkProduct wtProduct = null;
	WTContainerRef wtContainerRef = null;
	// assignLocation.
	QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	qs.appendSearchCondition(sc1);
	QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	if (results.hasMoreElements()) {
	    wtProduct = (PDMLinkProduct) results.nextElement();
	    wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	}
	if (wtProduct == null) {
	    wtProduct = WCUtil.getPDMLinkProduct();
	    wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	}

	Folder folder = FolderHelper.service.getFolder("/Default" + folderDir, wtContainerRef);
	FolderHelper.assignLocation((FolderEntry) modify, folder);
	modify = setAssessAttribute(modify, paramObject);

	modify = (AssessSheet) PersistenceHelper.manager.save(modify);

	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 체크아웃기능(현재 미사용)
     * 
     * @param AssessSheet
     * @return
     * @throws Exception
     * @메소드명 : checkoutDoc
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public AssessSheet checkoutDoc(AssessSheet obj) throws WTException {
	// ##begin checkoutDoc%4035514802E0.body preserve=yes
	wt.vc.wip.Workable workable = (wt.vc.wip.Workable) obj;
	wt.vc.wip.CheckoutLink checkOutLink = null;
	wt.vc.wip.Workable workingCopy = null;
	wt.vc.wip.Workable orgCopy = null;

	try {
	    if (!wt.vc.wip.WorkInProgressHelper.isWorkingCopy(workable)) {
		if (!wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable)) {
		    wt.folder.Folder folder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
		    // Folder folder = FolderHelper.service.getFolder("/Default");
		    Kogger.debug(getClass(), "Folder is " + folder);
		    Kogger.debug(getClass(), "++++++++++++++++++++++Document Check-out Started...");
		    Kogger.debug(getClass(), "checkOutLink Is: " + workable + " and Folder is : " + folder);
		    try {
			checkOutLink = (wt.vc.wip.CheckoutLink) wt.vc.wip.WorkInProgressHelper.service.checkout(workable, folder, "");
		    } catch (wt.util.WTPropertyVetoException wtpve) {
			Kogger.debug(getClass(), "++++++++++++++++++++++Document Check-out wtpve error :" + wtpve.getLocalizedMessage());
			Kogger.error(getClass(), wtpve);
		    }
		    // get Original copy
		    orgCopy = checkOutLink.getOriginalCopy();

		    Kogger.debug(getClass(), "orgCopy is " + orgCopy);
		    // get working copy
		    workingCopy = checkOutLink.getWorkingCopy();

		    Kogger.debug(getClass(), "workingCopy is " + workingCopy);

		} else if (wt.vc.wip.WorkInProgressHelper.isCheckedOut(workable)) {
		    // get Original copy
		    orgCopy = wt.vc.wip.WorkInProgressHelper.service.originalCopyOf(workable);
		    // get working copy
		    workingCopy = wt.vc.wip.WorkInProgressHelper.service.workingCopyOf(workable);
		    Kogger.debug(getClass(), "workingCopy is " + workingCopy);
		}

	    } else if (wt.vc.wip.WorkInProgressHelper.isWorkingCopy(workable)) {
		workingCopy = workable;
	    }
	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);

	    throw new WTException(wte.getMessage());
	}

	Kogger.debug(getClass(), "++++++++++++++++++++++Document Check-outworkingCopy:" + workingCopy.getPersistInfo().getObjectIdentifier().toString());

	return (AssessSheet) workingCopy;
    }

    /**
     * 화면 입력폼 정보(AssessSheetDTO)를 받아서 AssessSheet 객체의 코드 정보를 세팅하는 함수
     * 
     * @param AssessSheet
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : setAssessAttribute
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public AssessSheet setAssessAttribute(AssessSheet modify, AssessSheetDTO paramObject) throws Exception {
	// 사업부 정보 처리....
	NumberCode divisionNumber = null;
	ReferenceFactory rf = new ReferenceFactory();

	String divisionOid = (paramObject.getDivision() == null) ? "" : (String) paramObject.getDivision();
	if (divisionOid.trim().length() > 0) {
	    try {
		divisionNumber = (NumberCode) rf.getReference(divisionOid).getObject();
	    } catch (Exception e) {
		divisionNumber = null;
	    }
	}
	modify.setDivision(divisionNumber);

	// 개발유형
	NumberCode devTypeNumber = null;
	String devTypeOid = (paramObject.getDevType() == null) ? "" : (String) paramObject.getDevType();
	if (devTypeOid.trim().length() > 0) {
	    try {
		devTypeNumber = (NumberCode) rf.getReference(devTypeOid).getObject();
	    } catch (Exception e) {
		devTypeNumber = null;
	    }
	}
	modify.setDevType(devTypeNumber);

	// 개발구분
	NumberCode devDivNumber = null;
	String devDivOid = (paramObject.getDevDiv() == null) ? "" : (String) paramObject.getDevDiv();

	if (devDivOid.trim().length() > 0) {
	    try {
		devDivNumber = (NumberCode) rf.getReference(devDivOid).getObject();
	    } catch (Exception e) {
		devDivNumber = null;
	    }
	}
	modify.setDevDiv(devDivNumber);

	// 개발구분
	// NumberCode prodCateNumber = null;
	// String prodCateOid = (paramObject.getProdCategory() == null) ? "" : (String) paramObject.getProdCategory();
	//
	// if (prodCateOid.trim().length() > 0) {
	// try {
	// prodCateNumber = (NumberCode) rf.getReference(prodCateOid).getObject();
	// } catch (Exception e) {
	// prodCateNumber = null;
	// }
	// }
	// modify.setProdCategory(prodCateNumber);

	modify.setSheetName(paramObject.getSheetName());
	modify.setSheetDesc(paramObject.getSheetDesc());
	// modify.setState(paramObject.getStatus());
	// modify.setSheetDesc(paramObject.getSheetRev());
	String isActive = paramObject.getIsActive();
	if (StringUtil.isEmpty(isActive))
	    isActive = "0";

	modify.setActive(isActive);

	return modify;
    }

    /**
     * 프로젝트에 링크된 평가시트를 조회하는 함수
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : findLinkedAssessSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public AssessSheet findLinkedAssessSheet(String pjtOid) throws Exception {

	AssessSheet assSheet = null;

	QuerySpec query = getQueryAssessSheet(pjtOid);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assSheet = (AssessSheet) objArr[0];
	}

	return assSheet;
    }

    /**
     * 프로젝트에 링크된 평가시트를 찾아서 하위의 평가항목들을 조회하는 함수
     * 
     * @param pjtOid
     * @param GateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : findGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateCheckSheet> findGateCheckSheet(String pjtOid, GateCheckSheetDTO paramObject) throws Exception {

	AssessSheet assSheet = null;
	List<GateCheckSheet> checkList = new ArrayList<GateCheckSheet>();

	QuerySpec query = getQueryAssessSheet(pjtOid);
	QueryResult qr = null;
	/*
	 * qr = PersistenceHelper.manager.find((StatementSpec) query); while (qr.hasMoreElements()) { Object[] objArr = (Object[]) qr.nextElement(); assSheet = (AssessSheet) objArr[0]; }
	 */
	TemplateProject tProject = (TemplateProject) CommonUtil.getObject(pjtOid);
	if (tProject instanceof ReviewProject) {
	    qr = PersistenceHelper.manager.navigate((ReviewProject) tProject, ProjectAssSheetLink.ASSESS_ROLE, ProjectAssSheetLink.class);
	} else if (tProject instanceof ProductProject) {
	    qr = PersistenceHelper.manager.navigate((ProductProject) tProject, ProjectAssSheetLink.ASSESS_ROLE, ProjectAssSheetLink.class);
	} else if (tProject instanceof MoldProject) {
	    qr = PersistenceHelper.manager.navigate((MoldProject) tProject, ProjectAssSheetLink.ASSESS_ROLE, ProjectAssSheetLink.class);
	}
	while (qr.hasMoreElements()) {
	    // 링크확인
	    assSheet = (AssessSheet) qr.nextElement();
	}

	if (assSheet != null) {
	    query = getQueryGateCheckSheet(assSheet.getPersistInfo().getObjectIdentifier().getStringValue(), paramObject);
	    qr = PersistenceHelper.manager.find((StatementSpec) query);
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		checkList.add((GateCheckSheet) objArr[0]);
	    }
	}
	return checkList;
    }

    /**
     * versionNo에 해당하는 평가시트를 찾아서 하위의 평가항목들을 조회하는 함수
     * 
     * @param oid
     * @param versionNo
     * @param GateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : findViewVerGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateCheckSheet> findViewVerGateCheckSheet(String oid, String versionNo, GateCheckSheetDTO paramObject) throws Exception {

	AssessSheet assSheet = null;
	List<GateCheckSheet> checkList = new ArrayList<GateCheckSheet>();

	AssessSheet sheet = (AssessSheet) CommonUtil.getObject(oid);
	AssessSheet versionAsssheet = null;

	if (StringUtil.isEmpty(versionNo)) {
	    versionNo = "0";
	}
	if (sheet != null) {

	    QueryResult result = VersionControlHelper.service.allVersionsOf(sheet.getMaster());
	    if ((result == null) || (result.size() == 0)) {

	    } else {
		AssessSheet revision = null;
		while (result.hasMoreElements()) {
		    revision = (AssessSheet) result.nextElement();
		    String val = revision.getVersionIdentifier().getValue();
		    if (versionNo.equals(val)) {
			assSheet = revision;
		    }
		}
	    }
	}

	if (assSheet != null) {
	    QuerySpec query = getQueryGateCheckSheet(assSheet.getPersistInfo().getObjectIdentifier().getStringValue(), paramObject);
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		checkList.add((GateCheckSheet) objArr[0]);
	    }
	}
	return checkList;
    }

    /**
     * 프로젝트Oid정보를 가지고 연관 AssessSheet 정보가져오기
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getAssessSheetFromPjtOid
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public AssessSheet getAssessSheetFromPjtOid(String pjtOid) throws Exception {

	AssessSheet assSheet = null;

	QuerySpec query = getQueryAssessSheet(pjtOid);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assSheet = (AssessSheet) objArr[0];
	}

	return assSheet;
    }

    /**
     * 프로젝트에 링크된 평가시트를 조회하는 쿼리스펙을 리턴하는 함수
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getQueryAssessSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryAssessSheet(String pjtOid) throws Exception {

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_link = query.appendClassList(ProjectAssSheetLink.class, false);
	int idx_check = query.appendClassList(AssessSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(AssessSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(ProjectAssSheetLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	if (pjtOid != null && !StringUtil.isTrimToEmpty(pjtOid)) {
	    long pjtOidLong = 0;
	    pjtOid = pjtOid.substring(pjtOid.indexOf(":") + 1, pjtOid.length());
	    pjtOidLong = Long.parseLong(pjtOid);
	    sc = new SearchCondition(ProjectAssSheetLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * 평가시트 하위의 평가항목들을 조회하는 쿼리스펙을 리턴하는 함수
     * 
     * @param assOid
     * @param GateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : getQueryGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryGateCheckSheet(String assOid, GateCheckSheetDTO paramObject) throws Exception {
	AssessSheet sheet = (AssessSheet) CommonUtil.getObject(assOid);

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	// int idx_assess = query.appendClassList(AssessSheet.class, true);
	int idx_link = query.appendClassList(AssSheetGateChkSheetLink.class, false);
	int idx_check = query.appendClassList(GateCheckSheet.class, true);
	int idx_number = query.appendClassList(NumberCode.class, false);

	// ClassAttribute ca1 = null;
	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;
	// ClassAttribute ca4 = null;
	// ClassAttribute ca5 = null;
	// ClassAttribute ca6 = null;
	ClassAttribute ca7 = null;
	ClassAttribute ca8 = null;

	// ca1 = new ClassAttribute(AssessSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca2 = new ClassAttribute(GateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(AssSheetGateChkSheetLink.class, "roleAObjectRef.key.id");
	// ca4 = new ClassAttribute(AssSheetGateChkSheetLink.class, "roleBObjectRef.key.id");
	ca7 = new ClassAttribute(GateCheckSheet.class, "targetTypeReference.key.id");
	ca8 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	sc = new SearchCondition(ca7, SearchCondition.EQUAL, ca8);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idx_check, idx_number });

	if (!StringUtil.isTrimToEmpty(assOid)) {
	    long pjtOidLong = 0;
	    assOid = assOid.substring(assOid.indexOf(":") + 1, assOid.length());
	    pjtOidLong = Long.parseLong(assOid);
	    sc = new SearchCondition(AssSheetGateChkSheetLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	if (paramObject != null && !StringUtil.isTrimToEmpty(paramObject.getSortName())) {
	    if ("targetType".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number, "name", false);
	    } else if ("-targetType".equals(paramObject.getSortName())) {
		SearchUtil.setOrderBy(query, NumberCode.class, idx_number, "name", true);
	    } else {
		if (paramObject.getSortName().startsWith("-")) {
		    SearchUtil.setOrderBy(query, GateCheckSheet.class, idx_check, paramObject.getSortName().substring(1), true);
		} else {
		    SearchUtil.setOrderBy(query, GateCheckSheet.class, idx_check, paramObject.getSortName(), false);
		}
	    }
	} else {
	    SearchUtil.setOrderBy(query, GateCheckSheet.class, idx_check, GateCheckSheet.CHECK_SHEET_NAME, true);
	}
	return query;
    }

    /**
     * 프로젝트에 링크된 평가시트를 찾아서 하위의 평가항목들을 조회하는 함수
     * 
     * @param pjtOid
     * @param GateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : getProjectAssessHeadInfo
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public HashMap<String, String> getProjectAssessHeadInfo(String pjtOid, GateCheckSheetDTO paramObject) throws Exception {

	AssessSheet assSheet = null;
	List<String> rtnObj = new ArrayList();
	HashMap hMap = new HashMap();
	int qualityCnt = 0;
	int costCnt = 0;
	int deliveryCnt = 0;
	int etcCnt = 0;
	String pjtVersion = "";
	String createDate = "";

	QuerySpec query = getQueryAssessSheet(pjtOid);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assSheet = (AssessSheet) objArr[0];
	}

	if (assSheet != null) {
	    pjtVersion = assSheet.getVersionIdentifier().getValue(); // assSheet.getIterationIdentifier().getValue()
	    // pjtVersion = assSheet.getVersionIdentifier().getSeries().getValue();
	    createDate = DateUtil.getDateString(assSheet.getCreateTimestamp(), "d");
	    hMap.put("ASS_OID", assSheet.getPersistInfo().getObjectIdentifier().getStringValue());
	    hMap.put("PJT_VERSION", pjtVersion);
	    hMap.put("CREATE_DATE", createDate);
	    hMap.put("PJT_STATE", assSheet.getState().toString());
	    hMap.put("PJT_STATE_NAME", assSheet.getLifeCycleState().getDisplay(Locale.KOREA));

	}

	Vector resultVec = new Vector();
	if (assSheet != null) {
	    Vector assTagtVec = new Vector();
	    Vector targetTypeVec = NumberCodeHelper.manager.getNumberCodeForQuery("ASSESSTARGETTYPE");

	    query = getQueryGateCheckSheet(assSheet.getPersistInfo().getObjectIdentifier().getStringValue(), paramObject);
	    qr = PersistenceHelper.manager.find((StatementSpec) query);
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		GateCheckSheet tmpGateCheckSheet = (GateCheckSheet) objArr[0];
		NumberCode numTargetType = tmpGateCheckSheet.getTargetType();

		HashMap hMap1 = new HashMap();
		hMap1.put("code", numTargetType.getCode());
		hMap1.put("count", "" + 1);
		assTagtVec.addElement(hMap1);

		if (numTargetType != null && "T001".equals(numTargetType.getCode())) { // CODE:T001
		    qualityCnt++;
		} else if (numTargetType != null && "T002".equals(numTargetType.getCode())) {
		    costCnt++;
		} else if (numTargetType != null && "T003".equals(numTargetType.getCode())) {
		    deliveryCnt++;
		} else if (numTargetType != null && "T004".equals(numTargetType.getCode())) {
		    etcCnt++;
		}
	    }

	    if (targetTypeVec != null) {
		for (int i = 0; i < targetTypeVec.size(); i++) {
		    NumberCode code = (NumberCode) targetTypeVec.get(i);
		    int codeCnt = 0;
		    if (assTagtVec != null) {
			for (int j = 0; j < assTagtVec.size(); j++) {
			    HashMap hMap2 = (HashMap) assTagtVec.get(j);
			    String hMapCode = (String) hMap2.get("code");
			    if (code.getCode().equals(hMapCode)) {
				codeCnt++;
			    }
			}
		    }
		    HashMap hMap3 = new HashMap();
		    hMap3.put("code", code.getCode());
		    hMap3.put("name", code.getName());
		    hMap3.put("count", "" + codeCnt);
		    resultVec.addElement(hMap3);
		    // code.getPersistInfo().getObjectIdentifier().getStringValue()
		}
	    }
	}
	hMap.put("QUALITY_CNT", "" + qualityCnt);
	hMap.put("COST_CNT", "" + costCnt);
	hMap.put("DELIVERY_CNT", "" + deliveryCnt);
	hMap.put("ETC_CNT", "" + etcCnt);
	hMap.put("VEC_CNT", resultVec);

	return hMap;
    }

    /**
     * 표준 평가시트의 평가항목을 받아서 프로젝트에 평가항목으로 등록하는 함수
     * 
     * @param proj
     *            대상 프로젝트 객체
     * @param tmplAssess
     *            복사할 표준 평가시트 객체
     * @return
     * @throws Exception
     * @메소드명 : registTemplateAssessSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateCheckSheet> registTemplateAssessSheet(E3PSProject proj, TemplateAssessSheet tmplAssess) throws Exception {
	List<AssessSheet> resultList = new ArrayList<AssessSheet>();
	AssessSheet targetAssSht = null;

	// 1. E3PSProject 에 기존에 연결된 평가시트 링크정보가 있는지 조회
	QuerySpec query = getLinkedAssessSheet(proj);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	// 2. 기존에 연결된 평가시트 링크정보를 삭제한다
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    targetAssSht = (AssessSheet) objArr[1];
	    // 링크확인
	    ProjectAssSheetLink pjtAssLink = (ProjectAssSheetLink) objArr[0];

	    // 평가시트 삭제
	    // if (oldAssSht != null)
	    // PersistenceHelper.manager.delete(oldAssSht);

	    // 링크 삭제
	    if (pjtAssLink != null)
		PersistenceHelper.manager.delete(pjtAssLink);
	}

	if (targetAssSht == null) {
	    // 3. TemplateAssessSheet 를 AssessSheet 로 복사생성한다
	    AssessSheet assShetImsi1 = AssessSheet.newAssessSheet();
	    assShetImsi1.setDevDiv(tmplAssess.getDevDiv());
	    assShetImsi1.setDevDivReference(tmplAssess.getDevDivReference());
	    assShetImsi1.setDevType(tmplAssess.getDevType());
	    assShetImsi1.setDevTypeReference(tmplAssess.getDevTypeReference());
	    assShetImsi1.setDivision(tmplAssess.getDivision());
	    assShetImsi1.setDivisionReference(tmplAssess.getDivisionReference());
	    // assShetImsi1.setProdCategory(tmplAssess.getProdCategory());
	    // assShetImsi1.setProdCategoryReference(tmplAssess.getProdCategoryReference());
	    assShetImsi1.setPartOid(tmplAssess.getPartOid());
	    assShetImsi1.setActive(tmplAssess.getActive());
	    assShetImsi1.setSheetName(tmplAssess.getSheetName());
	    assShetImsi1.setSheetDesc(tmplAssess.getSheetDesc());
	    assShetImsi1.setPartOid(tmplAssess.getPartOid());

	    assShetImsi1.setName(tmplAssess.getSheetName());
	    assShetImsi1.setTitle(tmplAssess.getSheetName());

	    WTUser usr = (WTUser) SessionHelper.manager.getPrincipal();

	    // 폴더지정
	    NumberCode divNumberCode = assShetImsi1.getDivision();
	    String folderDir = "";
	    if (divNumberCode != null) {
		if ("2".equals(divNumberCode.getCode())) {
		    folderDir = "/전자사업부/프로젝트/GATE";
		} else { // if("1".equals(divNumberCode.getCode())) {
		    folderDir = "/자동차사업부/프로젝트/GATE";
		}
	    } else {
		folderDir = "/자동차사업부/프로젝트/GATE";
	    }
	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    // assignLocation.
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }
	    if (wtProduct == null) {
		wtProduct = WCUtil.getPDMLinkProduct();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }

	    Folder folder = FolderHelper.service.getFolder("/Default" + folderDir, wtContainerRef);
	    FolderHelper.assignLocation((FolderEntry) assShetImsi1, folder);

	    targetAssSht = (AssessSheet) PersistenceHelper.manager.save(assShetImsi1);

	    String lifecycle = "KET_COMMON_LC"; // KET_COMMON_LC, KET_MOLD_PMS_LC, LC_Project

	    // FolderHelper.assignLocation((FolderEntry) targetAssSht,
	    // FolderHelper.service.getFolder("/Default/프로젝트", WCUtil.getWTContainerRef()));
	    // LifeCycleHelper.setLifeCycle(targetAssSht, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,
	    // WCUtil.getWTContainerRef()));

	}

	/*
	 * Versioned versioned = (Versioned) assShetImsi1; Versioned newVer = null; String version = null; if ((version != null) && (version.trim().length() > 0)) { VersionIdentifier newVersionId =
	 * VersionUtil.getVersionIdentifier(versioned, version.trim()); IterationIdentifier newIterationId = VersionControlHelper.firstIterationId(versioned); newVer =
	 * VersionControlHelper.service.newVersion(versioned, newVersionId, newIterationId); } else { newVer = VersionControlHelper.service.newVersion(versioned); }
	 * 
	 * if ((tmplAssess.getRevNote() != null) && (tmplAssess.getRevNote().trim().length() > 0)) { VersionControlHelper.setNote(newVer, note); }
	 * 
	 * LifeCycleHelper.setLifeCycle((LifeCycleManaged) versioned, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef()));
	 */

	query = getQuerySubGateCheckSheet(targetAssSht.getPersistInfo().getObjectIdentifier().getStringValue());
	qr = PersistenceHelper.manager.find((StatementSpec) query);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    AssSheetGateChkSheetLink assGateLink = (AssSheetGateChkSheetLink) objArr[0];
	    GateCheckSheet tGateCheck = (GateCheckSheet) objArr[1];

	    // 기존에 링크된 Gate가 있으면 평가항목과의 링크삭제
	    PersistenceHelper.manager.delete(assGateLink);

	    // 기존에 링크된 Gate체크항목이 있으면 삭제
	    PersistenceHelper.manager.delete(tGateCheck);
	}

	// 프로젝트 하위에 있는 모든 Gate를 가져온다
	List<GateTaskOutputDTO> gateDtoList = ProjectTaskCompHelper.service.getProjectGateCheckSheetList(CommonUtil.getOIDString(proj));

	List<GateCheckSheet> arrAssSheet = new ArrayList<GateCheckSheet>();
	// 4. TemplateAssessSheet 하위에 있는 TemplateGateCheckSheet 를 조회한다
	query = getQueryTemplateGateCheckSheet(tmplAssess.getPersistInfo().getObjectIdentifier().getStringValue());
	qr = PersistenceHelper.manager.find((StatementSpec) query);
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    TemplateGateCheckSheet tGateCheck = (TemplateGateCheckSheet) objArr[1];

	    // 항목을 체크해서 Template평가항목의 선택된 유형(1,2,3,4,5,..)이 프로젝트 하위에 있는 모든 GateTask의 카테고리유형과 일치하면 복사대상이 되므로 계속 진행되고 그렇지 않으면 Skip(=continue)
	    if (!checkIsTarget(gateDtoList, tGateCheck))
		continue;

	    GateCheckSheet gateChkImsi = null;

	    // 5. TemplateGateCheckSheet 를 GateCheckSheet 로 복사생성하고 AssessSheet와 링크 해준다
	    gateChkImsi = copyTemplateToGateCheckSheet(tGateCheck);
	    // 평가항목 생성
	    gateChkImsi = (GateCheckSheet) PersistenceHelper.manager.save(gateChkImsi);

	    // 평가항목 링크생성
	    // 링크연결
	    AssSheetGateChkSheetLink assGateLink = AssSheetGateChkSheetLink.newAssSheetGateChkSheetLink(gateChkImsi, targetAssSht);
	    // 저장
	    assGateLink = (AssSheetGateChkSheetLink) PersistenceHelper.manager.save(assGateLink);

	}

	// 6. E3PSProject 와 AssessSheet를 링크 해준다
	// 링크연결
	ProjectAssSheetLink pjtAssLink = ProjectAssSheetLink.newProjectAssSheetLink(targetAssSht, proj);
	// 저장
	pjtAssLink = (ProjectAssSheetLink) PersistenceHelper.manager.save(pjtAssLink);

	// 프로젝트 하위에 달린 평가시트 항목을 조회한다
	query = getQueryAssessGateCheckSheet(targetAssSht);
	qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<GateCheckSheet> checkList = new ArrayList<GateCheckSheet>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    checkList.add((GateCheckSheet) objArr[0]);
	}
	
	// 결재 절차 없이 바로 승인처리 요청 (2018.10.04 연구지원팀 최희락 요청)
	LifeCycleServerHelper.service.setState((LifeCycleManaged) targetAssSht, State.toState("APPROVED"));
	
	return checkList;
    }

    public boolean checkIsTarget(List<GateTaskOutputDTO> gateDtoList, TemplateGateCheckSheet tGateCheck) {
	KETMessageService messageService = KETMessageService.getMessageService();
	GateAttribute gAttribute = tGateCheck.getAttr();
	boolean isTargetYn = false;
	try {

	    if (gateDtoList != null) {
		for (int k = 0; k < gateDtoList.size(); k++) {
		    GateTaskOutputDTO gTODto = gateDtoList.get(k);
		    String taskCategory = gTODto.getOutputTaskCategory();

		    if (gAttribute.isSelect1()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G1", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect2()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G2", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect3()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G3", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect4()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G4", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect5()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G5", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect6()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G6", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect7()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G7", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect8()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G8", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }

		    if (gAttribute.isSelect9()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G9", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }
		    
		    if (gAttribute.isSelect10()) {
			String cateName = NumberCodeUtil.getNumberCodeValue("GATELEVELNAME", "G10", messageService.getLocale().toString()).replaceAll("Gate", "");

			if (cateName.equals(taskCategory)) {
			    isTargetYn = true;
			}
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return isTargetYn;
    }

    /**
     * 표준 평가항목을 받아서 프로젝트 평가항목으로 복사하는 함수
     * 
     * @param TemplateGateCheckSheet
     * @return
     * @throws Exception
     * @메소드명 : copyGateCheckSheetToGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public GateCheckSheet copyGateCheckSheetToGateCheckSheet(GateCheckSheet tmplGate) throws Exception {
	GateCheckSheet gateCheckSheet = GateCheckSheet.newGateCheckSheet();
	if (tmplGate != null && tmplGate.getTargetType() != null && tmplGate.getTargetTypeReference() != null) {
	    gateCheckSheet.setTargetType(tmplGate.getTargetType());
	    gateCheckSheet.setTargetTypeReference(tmplGate.getTargetTypeReference());
	} else {
	    NumberCode defaultCode = NumberCodeHelper.manager.getNumberCode("ASSESSTARGETTYPE", "T004");
	    gateCheckSheet.setTargetType(defaultCode);
	    gateCheckSheet.setTargetTypeReference(ObjectReference.newObjectReference(defaultCode));
	}
	gateCheckSheet.setCheckSheetName(tmplGate.getCheckSheetName());
	gateCheckSheet.setAchieveBase(tmplGate.getAchieveBase());
	gateCheckSheet.setUnit(tmplGate.getUnit());
	gateCheckSheet.setCriteria(tmplGate.getCriteria());
	gateCheckSheet.setOrderNo(tmplGate.getOrderNo());

	GateAttribute tmplGateAttr = tmplGate.getAttr();
	GateAttribute newGateAttr = GateAttribute.newGateAttribute();
	newGateAttr.setGateName1(tmplGateAttr.getGateName1());
	newGateAttr.setGateName2(tmplGateAttr.getGateName2());
	newGateAttr.setGateName3(tmplGateAttr.getGateName3());
	newGateAttr.setGateName4(tmplGateAttr.getGateName4());
	newGateAttr.setGateName5(tmplGateAttr.getGateName5());
	newGateAttr.setGateName6(tmplGateAttr.getGateName6());
	newGateAttr.setGateName7(tmplGateAttr.getGateName7());
	newGateAttr.setGateName8(tmplGateAttr.getGateName8());
	newGateAttr.setGateName9(tmplGateAttr.getGateName9());
	newGateAttr.setGateName10(tmplGateAttr.getGateName10());
	newGateAttr.setGateName11(tmplGateAttr.getGateName11());
	newGateAttr.setGateName12(tmplGateAttr.getGateName12());
	newGateAttr.setGateName13(tmplGateAttr.getGateName13());
	newGateAttr.setGateName14(tmplGateAttr.getGateName14());
	newGateAttr.setGateName15(tmplGateAttr.getGateName15());
	newGateAttr.setGateName16(tmplGateAttr.getGateName16());
	newGateAttr.setGateName17(tmplGateAttr.getGateName17());
	newGateAttr.setGateName18(tmplGateAttr.getGateName18());
	newGateAttr.setGateName19(tmplGateAttr.getGateName19());
	newGateAttr.setGateName20(tmplGateAttr.getGateName20());
	newGateAttr.setResult1(tmplGateAttr.getResult1());
	newGateAttr.setResult2(tmplGateAttr.getResult2());
	newGateAttr.setResult3(tmplGateAttr.getResult3());
	newGateAttr.setResult4(tmplGateAttr.getResult4());
	newGateAttr.setResult5(tmplGateAttr.getResult5());
	newGateAttr.setResult6(tmplGateAttr.getResult6());
	newGateAttr.setResult7(tmplGateAttr.getResult7());
	newGateAttr.setResult8(tmplGateAttr.getResult8());
	newGateAttr.setResult9(tmplGateAttr.getResult9());
	newGateAttr.setResult10(tmplGateAttr.getResult10());
	newGateAttr.setResult11(tmplGateAttr.getResult11());
	newGateAttr.setResult12(tmplGateAttr.getResult12());
	newGateAttr.setResult13(tmplGateAttr.getResult13());
	newGateAttr.setResult14(tmplGateAttr.getResult14());
	newGateAttr.setResult15(tmplGateAttr.getResult15());
	newGateAttr.setResult16(tmplGateAttr.getResult16());
	newGateAttr.setResult17(tmplGateAttr.getResult17());
	newGateAttr.setResult18(tmplGateAttr.getResult18());
	newGateAttr.setResult19(tmplGateAttr.getResult19());
	newGateAttr.setResult20(tmplGateAttr.getResult20());
	newGateAttr.setTarget1(tmplGateAttr.getTarget1());
	newGateAttr.setTarget2(tmplGateAttr.getTarget2());
	newGateAttr.setTarget3(tmplGateAttr.getTarget3());
	newGateAttr.setTarget4(tmplGateAttr.getTarget4());
	newGateAttr.setTarget5(tmplGateAttr.getTarget5());
	newGateAttr.setTarget6(tmplGateAttr.getTarget6());
	newGateAttr.setTarget7(tmplGateAttr.getTarget7());
	newGateAttr.setTarget8(tmplGateAttr.getTarget8());
	newGateAttr.setTarget9(tmplGateAttr.getTarget9());
	newGateAttr.setTarget10(tmplGateAttr.getTarget10());
	newGateAttr.setTarget11(tmplGateAttr.getTarget11());
	newGateAttr.setTarget12(tmplGateAttr.getTarget12());
	newGateAttr.setTarget13(tmplGateAttr.getTarget13());
	newGateAttr.setTarget14(tmplGateAttr.getTarget14());
	newGateAttr.setTarget15(tmplGateAttr.getTarget15());
	newGateAttr.setTarget16(tmplGateAttr.getTarget16());
	newGateAttr.setTarget17(tmplGateAttr.getTarget17());
	newGateAttr.setTarget18(tmplGateAttr.getTarget18());
	newGateAttr.setTarget19(tmplGateAttr.getTarget19());
	newGateAttr.setTarget20(tmplGateAttr.getTarget20());
	newGateAttr.setSelect1(tmplGateAttr.isSelect1());
	newGateAttr.setSelect2(tmplGateAttr.isSelect2());
	newGateAttr.setSelect3(tmplGateAttr.isSelect3());
	newGateAttr.setSelect4(tmplGateAttr.isSelect4());
	newGateAttr.setSelect5(tmplGateAttr.isSelect5());
	newGateAttr.setSelect6(tmplGateAttr.isSelect6());
	newGateAttr.setSelect7(tmplGateAttr.isSelect7());
	newGateAttr.setSelect8(tmplGateAttr.isSelect8());
	newGateAttr.setSelect9(tmplGateAttr.isSelect9());
	newGateAttr.setSelect10(tmplGateAttr.isSelect10());
	newGateAttr.setSelect11(tmplGateAttr.isSelect11());
	newGateAttr.setSelect12(tmplGateAttr.isSelect12());
	newGateAttr.setSelect13(tmplGateAttr.isSelect13());
	newGateAttr.setSelect14(tmplGateAttr.isSelect14());
	newGateAttr.setSelect15(tmplGateAttr.isSelect15());
	newGateAttr.setSelect16(tmplGateAttr.isSelect16());
	newGateAttr.setSelect17(tmplGateAttr.isSelect17());
	newGateAttr.setSelect18(tmplGateAttr.isSelect18());
	newGateAttr.setSelect19(tmplGateAttr.isSelect19());
	newGateAttr.setSelect20(tmplGateAttr.isSelect20());

	gateCheckSheet.setAttr(newGateAttr);

	return gateCheckSheet;
    }

    /**
     * 표준 평가항목을 받아서 프로젝트 평가항목으로 복사하는 함수
     * 
     * @param TemplateGateCheckSheet
     * @return
     * @throws Exception
     * @메소드명 : copyTemplateToGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public GateCheckSheet copyTemplateToGateCheckSheet(TemplateGateCheckSheet tmplGate) throws Exception {
	GateCheckSheet gateCheckSheet = GateCheckSheet.newGateCheckSheet();
	gateCheckSheet.setTargetType(tmplGate.getTargetType());
	gateCheckSheet.setTargetTypeReference(tmplGate.getTargetTypeReference());
	gateCheckSheet.setCheckSheetName(tmplGate.getCheckSheetName());
	gateCheckSheet.setAchieveBase(tmplGate.getAchieveBase());
	gateCheckSheet.setUnit(tmplGate.getUnit());
	gateCheckSheet.setCriteria(tmplGate.getCriteria());
	gateCheckSheet.setOrderNo(tmplGate.getOrderNo());

	GateAttribute tmplGateAttr = tmplGate.getAttr();
	GateAttribute newGateAttr = GateAttribute.newGateAttribute();
	newGateAttr.setGateName1(tmplGateAttr.getGateName1());
	newGateAttr.setGateName2(tmplGateAttr.getGateName2());
	newGateAttr.setGateName3(tmplGateAttr.getGateName3());
	newGateAttr.setGateName4(tmplGateAttr.getGateName4());
	newGateAttr.setGateName5(tmplGateAttr.getGateName5());
	newGateAttr.setGateName6(tmplGateAttr.getGateName6());
	newGateAttr.setGateName7(tmplGateAttr.getGateName7());
	newGateAttr.setGateName8(tmplGateAttr.getGateName8());
	newGateAttr.setGateName9(tmplGateAttr.getGateName9());
	newGateAttr.setGateName10(tmplGateAttr.getGateName10());
	newGateAttr.setGateName11(tmplGateAttr.getGateName11());
	newGateAttr.setGateName12(tmplGateAttr.getGateName12());
	newGateAttr.setGateName13(tmplGateAttr.getGateName13());
	newGateAttr.setGateName14(tmplGateAttr.getGateName14());
	newGateAttr.setGateName15(tmplGateAttr.getGateName15());
	newGateAttr.setGateName16(tmplGateAttr.getGateName16());
	newGateAttr.setGateName17(tmplGateAttr.getGateName17());
	newGateAttr.setGateName18(tmplGateAttr.getGateName18());
	newGateAttr.setGateName19(tmplGateAttr.getGateName19());
	newGateAttr.setGateName20(tmplGateAttr.getGateName20());
	newGateAttr.setResult1(tmplGateAttr.getResult1());
	newGateAttr.setResult2(tmplGateAttr.getResult2());
	newGateAttr.setResult3(tmplGateAttr.getResult3());
	newGateAttr.setResult4(tmplGateAttr.getResult4());
	newGateAttr.setResult5(tmplGateAttr.getResult5());
	newGateAttr.setResult6(tmplGateAttr.getResult6());
	newGateAttr.setResult7(tmplGateAttr.getResult7());
	newGateAttr.setResult8(tmplGateAttr.getResult8());
	newGateAttr.setResult9(tmplGateAttr.getResult9());
	newGateAttr.setResult10(tmplGateAttr.getResult10());
	newGateAttr.setResult11(tmplGateAttr.getResult11());
	newGateAttr.setResult12(tmplGateAttr.getResult12());
	newGateAttr.setResult13(tmplGateAttr.getResult13());
	newGateAttr.setResult14(tmplGateAttr.getResult14());
	newGateAttr.setResult15(tmplGateAttr.getResult15());
	newGateAttr.setResult16(tmplGateAttr.getResult16());
	newGateAttr.setResult17(tmplGateAttr.getResult17());
	newGateAttr.setResult18(tmplGateAttr.getResult18());
	newGateAttr.setResult19(tmplGateAttr.getResult19());
	newGateAttr.setResult20(tmplGateAttr.getResult20());
	newGateAttr.setTarget1(tmplGateAttr.getTarget1());
	newGateAttr.setTarget2(tmplGateAttr.getTarget2());
	newGateAttr.setTarget3(tmplGateAttr.getTarget3());
	newGateAttr.setTarget4(tmplGateAttr.getTarget4());
	newGateAttr.setTarget5(tmplGateAttr.getTarget5());
	newGateAttr.setTarget6(tmplGateAttr.getTarget6());
	newGateAttr.setTarget7(tmplGateAttr.getTarget7());
	newGateAttr.setTarget8(tmplGateAttr.getTarget8());
	newGateAttr.setTarget9(tmplGateAttr.getTarget9());
	newGateAttr.setTarget10(tmplGateAttr.getTarget10());
	newGateAttr.setTarget11(tmplGateAttr.getTarget11());
	newGateAttr.setTarget12(tmplGateAttr.getTarget12());
	newGateAttr.setTarget13(tmplGateAttr.getTarget13());
	newGateAttr.setTarget14(tmplGateAttr.getTarget14());
	newGateAttr.setTarget15(tmplGateAttr.getTarget15());
	newGateAttr.setTarget16(tmplGateAttr.getTarget16());
	newGateAttr.setTarget17(tmplGateAttr.getTarget17());
	newGateAttr.setTarget18(tmplGateAttr.getTarget18());
	newGateAttr.setTarget19(tmplGateAttr.getTarget19());
	newGateAttr.setTarget20(tmplGateAttr.getTarget20());
	newGateAttr.setSelect1(tmplGateAttr.isSelect1());
	newGateAttr.setSelect2(tmplGateAttr.isSelect2());
	newGateAttr.setSelect3(tmplGateAttr.isSelect3());
	newGateAttr.setSelect4(tmplGateAttr.isSelect4());
	newGateAttr.setSelect5(tmplGateAttr.isSelect5());
	newGateAttr.setSelect6(tmplGateAttr.isSelect6());
	newGateAttr.setSelect7(tmplGateAttr.isSelect7());
	newGateAttr.setSelect8(tmplGateAttr.isSelect8());
	newGateAttr.setSelect9(tmplGateAttr.isSelect9());
	newGateAttr.setSelect10(tmplGateAttr.isSelect10());
	newGateAttr.setSelect11(tmplGateAttr.isSelect11());
	newGateAttr.setSelect12(tmplGateAttr.isSelect12());
	newGateAttr.setSelect13(tmplGateAttr.isSelect13());
	newGateAttr.setSelect14(tmplGateAttr.isSelect14());
	newGateAttr.setSelect15(tmplGateAttr.isSelect15());
	newGateAttr.setSelect16(tmplGateAttr.isSelect16());
	newGateAttr.setSelect17(tmplGateAttr.isSelect17());
	newGateAttr.setSelect18(tmplGateAttr.isSelect18());
	newGateAttr.setSelect19(tmplGateAttr.isSelect19());
	newGateAttr.setSelect20(tmplGateAttr.isSelect20());

	gateCheckSheet.setAttr(newGateAttr);

	return gateCheckSheet;
    }

    /**
     * E3PSProject 에 기존에 연결된 평가시트 링크정보가 있는지 조회
     * 
     * @param proj
     *            대상 프로젝트 객체
     * @param tmplAssess
     *            복사할 표준 평가시트 객체
     * @return
     * @throws Exception
     * @메소드명 : getLinkedAssessSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public QuerySpec getLinkedAssessSheet(E3PSProject proj) throws Exception {
	String rtnAssOid = null;
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	// int idx_ass = query.appendClassList(TemplateAssessSheet.class, false);
	int idx_link = query.appendClassList(ProjectAssSheetLink.class, true);
	int idx_check = query.appendClassList(AssessSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(AssessSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(ProjectAssSheetLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	String pjtOid = proj.getPersistInfo().getObjectIdentifier().getStringValue();
	if (!StringUtil.isTrimToEmpty(pjtOid)) {
	    long pjtOidLong = proj.getPersistInfo().getObjectIdentifier().getId();
	    sc = new SearchCondition(ProjectAssSheetLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * 평가시트 OID 하위에 있는 평가항목을 조회하는 QuerySpec 리턴
     * 
     * @param pjtOid
     *            평가시트 OID
     * @return
     * @throws Exception
     * @메소드명 : getQueryAssessGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryAssessGateCheckSheet(AssessSheet assSheet) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_link = query.appendClassList(AssSheetGateChkSheetLink.class, false);
	int idx_check = query.appendClassList(GateCheckSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(GateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(AssSheetGateChkSheetLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	if (assSheet != null) {
	    long pjtOidLong = 0;
	    pjtOidLong = assSheet.getPersistInfo().getObjectIdentifier().getId();
	    sc = new SearchCondition(AssSheetGateChkSheetLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * 평가시트 OID 하위에 있는 평가항목을 조회하는 QuerySpec 리턴
     * 
     * @param pjtOid
     *            평가시트 OID
     * @return
     * @throws Exception
     * @메소드명 : getQueryTemplateGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryTemplateGateCheckSheet(String tmpAssOid) throws Exception {
	TemplateAssessSheet sheet = (TemplateAssessSheet) CommonUtil.getObject(tmpAssOid);

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_link = query.appendClassList(TmplAssShtTmplGateChkLink.class, true);
	int idx_check = query.appendClassList(TemplateGateCheckSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(TemplateGateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(TmplAssShtTmplGateChkLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	if (!StringUtil.isTrimToEmpty(tmpAssOid)) {
	    long tmpAssOidLong = 0;
	    tmpAssOid = tmpAssOid.substring(tmpAssOid.indexOf(":") + 1, tmpAssOid.length());
	    tmpAssOidLong = Long.parseLong(tmpAssOid);
	    sc = new SearchCondition(TmplAssShtTmplGateChkLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, tmpAssOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * 평가시트 OID 하위에 있는 평가항목을 조회하는 QuerySpec 리턴
     * 
     * @param pjtOid
     *            평가시트 OID
     * @return
     * @throws Exception
     * @메소드명 : getQuerySubGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQuerySubGateCheckSheet(String assOid) throws Exception {
	AssessSheet sheet = (AssessSheet) CommonUtil.getObject(assOid);

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_link = query.appendClassList(AssSheetGateChkSheetLink.class, true);
	int idx_check = query.appendClassList(GateCheckSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(GateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(AssSheetGateChkSheetLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	if (!StringUtil.isTrimToEmpty(assOid)) {
	    long assOidLong = 0;
	    assOid = assOid.substring(assOid.indexOf(":") + 1, assOid.length());
	    assOidLong = Long.parseLong(assOid);
	    sc = new SearchCondition(AssSheetGateChkSheetLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, assOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * Hashtable로 AssessOID와 개정사유 정보를 받아서 개정버전을 생성후 리턴함
     * 
     * StandardKETDmsService.reviceProjectDoc 참조
     * 
     * @param Hashtable
     * @return
     * @throws Exception
     * @메소드명 : reviseAssessSheetVersion
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public AssessSheet reviseAssessSheetVersion(Hashtable hash, AssessSheetDTO dto) throws WTException {

	String assOid = null;
	assOid = (String) hash.get("assOid");
	AssessSheet latestAssSht = null;
	AssessSheet newAssSht = null;

	Transaction trx = new Transaction();
	trx.start();

	// ReferenceFactory rf = new ReferenceFactory();
	AssessSheet assSht = (AssessSheet) CommonUtil.getObject(dto.getOid());
	// Versioned vs = (Versioned) CommonUtil.getObject(assOid);
	// wtDoc = (AssessSheet)rf.getReference(assSht).getObject();
	try {
	    // 1. 최근 버전 AssessSheet를 가져온다
	    latestAssSht = (AssessSheet) ObjectUtil.getLatestVersion(assSht);

	    // 2. 기존에 연결된 평가시트와 프로젝트와의 링크를 삭제한다
	    E3PSProject proj = (E3PSProject) CommonUtil.getObject(dto.getPjtOid());
	    // QuerySpec query = getLinkedAssessSheet(proj);
	    // QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	    // while (qr.hasMoreElements()) {
	    // Object[] objArr = (Object[]) qr.nextElement();
	    // AssessSheet rtnAssSht = (AssessSheet) objArr[1];
	    // // 링크확인
	    // ProjectAssSheetLink pjtAssLink = (ProjectAssSheetLink) objArr[0];
	    //
	    // // 링크 삭제
	    // PersistenceHelper.manager.delete(pjtAssLink);
	    //
	    // // 평가시트 삭제
	    // // PersistenceHelper.manager.delete(rtnAssSht);
	    // }

	    // gateCheck 대상 객체, ASSESS_ROLE: 링크된 객체, 링크객체, false : 링크된객체(true:해당객체)
	    QuerySpec query = null;
	    QueryResult qr = PersistenceHelper.manager.navigate(proj, ProjectAssSheetLink.ASSESS_ROLE, ProjectAssSheetLink.class, false);

	    while (qr.hasMoreElements()) {
		// Object[] objArr = (Object[]) qr.nextElement();
		// GateCheckSheet tGateCheckSheet = (GateCheckSheet) objArr[1];
		// 링크확인
		ProjectAssSheetLink projAssLink = (ProjectAssSheetLink) qr.nextElement();
		// assGateLink.getCheck();

		// 링크 삭제(링크만)
		PersistenceHelper.manager.delete(projAssLink);
	    }

	    AssessSheet vs = (AssessSheet) CommonUtil.getObject(assOid); // 현재 링크된 버전을 대상으로 한다
	    if (vs != null) {
		// vs = (AssessSheet) ObjectUtil.getLatestVersion(vs); // 가장 최신의 버전을 대상으로 한다
	    }

	    String lifecycle = ((LifeCycleManaged) vs).getLifeCycleName();

	    // newVs = VersionUtil.revise(vs, lifecycle); //

	    // 3. 기존 버전(vs)을 복사하여 새로운 버전(newVs)의 AssessSheet(newAssSht)를 생성한다
	    newAssSht = (AssessSheet) VersionControlHelper.service.newVersion(vs);

	    NumberCode divNumberCode = vs.getDivision();
	    String folderDir = "";
	    if (divNumberCode != null) {
		if ("2".equals(divNumberCode.getCode())) {
		    folderDir = "/전자사업부/프로젝트/GATE";
		} else { // if("1".equals(divNumberCode.getCode())) {
		    folderDir = "/자동차사업부/프로젝트/GATE";
		}
	    } else {
		folderDir = "/자동차사업부/프로젝트/GATE";
	    }
	    // 3-1. 폴더지정
	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    // assignLocation.
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }
	    if (wtProduct == null) {
		wtProduct = WCUtil.getPDMLinkProduct();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }

	    // Folder folder = FolderHelper.service.getFolder((FolderEntry) vs);
	    Folder folder = FolderHelper.service.getFolder("/Default" + folderDir, wtContainerRef);
	    FolderHelper.assignLocation((FolderEntry) newAssSht, folder);
	    // newAssSht = (AssessSheet) newVs;

	    // 4. 새버전의 AssessSheet(newAssSht)에 개정사유를 등록한다
	    VersionControlHelper.setNote(newAssSht, dto.getReviseCause());

	    // 5. 상태를 등록한다
	    LifeCycleHelper.setLifeCycle((LifeCycleManaged) newAssSht, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef()));

	    // 6. 새버전의 AssessSheet(newAssSht)를 저장한다
	    newAssSht = (AssessSheet) PersistenceHelper.manager.save(newAssSht);

	    // newAssSht = (AssessSheet) newVs;
	    // newAssSht = (AssessSheet) PersistenceHelper.manager.save(newAssSht);

	    // VersionControlHelper.setNote(newAssSht, dto.getReviseCause());
	    // LifeCycleServerHelper.service.setState((LifeCycleManaged) newAssSht, State.toState("REWORK")); // new step

	    // newAssSht = (AssessSheet) PersistenceHelper.manager.save(newAssSht);// //a

	    // 7. 기존 버전의 AssessSheet(assSht) 하위에 있는 GateCheckSheet 를 조회한다
	    List<GateCheckSheet> arrAssSheet = new ArrayList<GateCheckSheet>();
	    query = getQuerySubGateCheckSheet(assSht.getPersistInfo().getObjectIdentifier().getStringValue());
	    qr = PersistenceHelper.manager.find((StatementSpec) query);
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		// GateCheckSheet tGateCheck = (GateCheckSheet) objArr[1];
		GateCheckSheet tGateCheck = (GateCheckSheet) objArr[1];
		GateCheckSheet gateChkImsi = GateCheckSheet.newGateCheckSheet();

		// 8. 기존버전의 AssessSheet하위에 있는 GateCheckSheet 를 복사생성한다
		gateChkImsi = copyGateCheckSheetToGateCheckSheet(tGateCheck);

		// 평가항목 생성
		gateChkImsi = (GateCheckSheet) PersistenceHelper.manager.save(gateChkImsi);

		// 9. 새버전의 AssessSheet(newAssSht)와 복사한 GateCheckSheet(gateChkImsi)를 링크 해준다
		AssSheetGateChkSheetLink assGateLink = AssSheetGateChkSheetLink.newAssSheetGateChkSheetLink(gateChkImsi, newAssSht);
		// 저장
		assGateLink = (AssSheetGateChkSheetLink) PersistenceHelper.manager.save(assGateLink);

	    }

	    // 10. 새버전의 평가시트와 프로젝트를 링크 해준다
	    ProjectAssSheetLink pjtAssLink = ProjectAssSheetLink.newProjectAssSheetLink(newAssSht, proj);
	    // 저장
	    pjtAssLink = (ProjectAssSheetLink) PersistenceHelper.manager.save(pjtAssLink);

	    // 프로젝트하위의 Gate 태스크중 승인완료인 상태를 제외하고 모두 체크결과와의 링크를 제거한다
	    /*
	     * List<GateTaskOutputDTO> gateStringList = ProjectTaskCompHelper.service.getProjectGateCheckSheetList(CommonUtil.getOIDString(proj));
	     * 
	     * if (gateStringList != null) { for (int i = 0; i < gateStringList.size(); i++) {
	     * 
	     * GateTaskOutputDTO gateTaskOutDto = gateStringList.get(i); String taskOid = gateTaskOutDto.getTaskOid(); E3PSTask taskG = (E3PSTask) CommonUtil.getObject("e3ps.project.E3PSTask:" +
	     * taskOid);
	     * 
	     * GateAssessResult assessResult = null; if (taskG != null) { qr = PersistenceHelper.manager.navigate(taskG, GateAssRsltTaskLink.ASSESS_ROLE, GateAssRsltTaskLink.class); while
	     * (qr.hasMoreElements()) { assessResult = (GateAssessResult) qr.nextElement(); if (assessResult != null) { String state =
	     * assessResult.getLifeCycleState().getLocalizedMessage(Locale.KOREAN); if ("승인완료".equals(state)) { continue; } else { AssessResultGateCheckLink assessResultLink = null; QueryResult qr2 =
	     * PersistenceHelper.manager.navigate(assessResult, AssessResultGateCheckLink.SHEET_CHECK, AssessResultGateCheckLink.class, false); while (qr2.hasMoreElements()) { assessResultLink =
	     * (AssessResultGateCheckLink) qr2.nextElement(); PersistenceHelper.manager.delete(assessResultLink);
	     * 
	     * } } } } }
	     * 
	     * } }
	     */

	    // 신규OID
	    // assOid = CommonUtil.getOIDString(newAssSht);

	    /*
	     * // revise newAssSht = (AssessSheet) wt.vc.VersionControlHelper.service.newVersion((wt.vc.Versioned) assSht); // vs = (Versioned) wt.vc.VersionControlHelper.service.newVersion(vs);
	     * 
	     * // 개정사유 VersionControlHelper.setNote((wt.vc.Versioned) newAssSht, dto.getReviseCause());
	     * 
	     * // save // Versioned newDs = (Versioned) PersistenceHelper.manager.save(vs); // save newAssSht = (AssessSheet) PersistenceHelper.manager.save(newAssSht);
	     * 
	     * newAssSht = (AssessSheet) PersistenceHelper.manager.refresh(newAssSht, true, true); // refresh
	     * 
	     * // AssessSheet assTarget = (AssessSheet) newDs; // Primary 첨부파일이 있을 경우 Delete AssessSheet dsContent = (AssessSheet) ContentHelper.service.getContents(newAssSht); ContentItem contentitem
	     * = ContentHelper.getPrimary(dsContent); if (contentitem != null // && attachFile!=null && attachFile.size()>0 // && roleType.getStringValue().indexOf("PRIMARY")!=-1 ) {
	     * Kogger.debug(getClass(), "primary start deletePrimaryFiles contentitem = " + contentitem.toString()); ContentServerHelper.service.deleteContent(dsContent, contentitem);
	     * ContentServerHelper.service.updateHolderFormat(dsContent); dsContent = (AssessSheet) PersistenceHelper.manager.refresh(dsContent, true, true);
	     * Kogger.debug(getClass(), "PPP deletePrimaryFiles FINISH "); }
	     */

	    // 기존 객체 컨텐트를 개정할 객체에 복사한후 첨부파일 업로드 하기 위해 아래의 copyContent함수를 써야한다
	    // 현재 copyContent함수를 쓰면 에러가 남(로컬에서 애러나는 문제로 여겨짐(서버에서 테스트요망)
	    // 아래의 함수를 쓰지 않으면 개정시 기존 첨부파일이 신규버전에 반영되지 않는다
	    // KETContentHelper.service.copyContent(vs, newAssSht);

	    // 11. 첨부파일 업로드 1
	    KETContentHelper.service.updateContent(newAssSht, dto);
	    // updateContent(newAssSht, dto);

	    trx.commit();

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    newAssSht = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    newAssSht = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    newAssSht = null;
	    Kogger.error(getClass(), e);
	}

	return newAssSht;
    }

    /**
     * 개정을 할 경우 기존 파일을 삭제하는 로직을 SKIP하기 위해 기존 updateContent 함수에서 수정후 반영 기존 함수 : KETContentHelper.service.updateContent(newAssSht, dto);
     * 
     * @param ContentHolder
     * @return
     * @throws Exception
     * @메소드명 : updateContent
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateContent(ContentHolder holder, BaseDTO dto) throws Exception {

	List<ContentItem> contentItems = new ArrayList<ContentItem>(); // not remove contentitem list
	List<UploadedFile> files = new ArrayList<UploadedFile>(); // uploaded new file list

	List<MultipartFile> secondaryFiles = dto.getSecondaryFiles();

	if (secondaryFiles != null && secondaryFiles.size() > 0) {
	    for (MultipartFile multipartFile : secondaryFiles) {
		UploadedFile uploadedFile = UploadedFile.newUploadedFile(multipartFile, false);
		files.add(uploadedFile);
	    }
	}

	KETContentHelper.service.updateContent(holder, files, contentItems);
    }

    /**
     * 프로젝트 Gate 평가결과 조회하는 함수
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : findProjectGateResult
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    // public List<GateResultDTO> findProjectGateResult(String Oid) throws Exception {
    // QuerySpec query = getProjectGateResult(Oid);
    // QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
    // List<GateResultDTO> assList = new ArrayList<AssessSheet>();
    // while (qr.hasMoreElements()) {
    // Object[] objArr = (Object[]) qr.nextElement();
    // assList.add((GateResultDTO) objArr[0]);
    // }
    // return assList;
    // }
}
