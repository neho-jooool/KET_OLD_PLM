package e3ps.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ExistsExpression;
import wt.query.NegatedExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.views.ViewManageable;
import e3ps.common.code.NumberCode;
import e3ps.common.iba.IBAUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.CompanyState;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldProject;
import e3ps.project.OutputDocumentLink;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMaster;
import e3ps.project.ProjectOutput;
import e3ps.project.TemplateProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.dms.controller.DocumentSearchController;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.shared.log.Kogger;

public class AuthUtil {

    /**
     * @param args
     */
    public static void main(String[] args) {
    }

    /**
     * 주 첨부파일 다운로드시 권한에 따른 다운로드 가능 or 불가 S2 대내비,S1 대외비
     * 
     * @param oid
     * @return
     */
    public static boolean isContentSecu(String oid) {
	boolean isSecu = false;
	Object obj = CommonUtil.getObject(oid);

	try {
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    if (obj instanceof KETProjectDocument) {
		KETProjectDocument pDoc = (KETProjectDocument) obj;
		/* Doc(KETProjectDocument) 연관프로젝트,산출물 프로젝트 멤버 유무, 결재자 기타 등등 */
		if (isAuthUserDOC(pDoc, wtuser))
		    return true;

	    } else if (obj instanceof EPMDocument) {
		/* 산출물 프로젝트 멤버 유무, 결재자 기타 등등 */
		EPMDocument epm = (EPMDocument) obj;
		if (isAuthUserCAD(epm))
		    return true;

	    } else if (obj instanceof KETTechnicalDocument) {
		isSecu = true;
	    }
	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}
	return isSecu;
    }

    public static boolean isContentSecu(String appDataIdString, String principalIdString) throws WTException, Exception {

	boolean isSecu = false;
	ContentHolder holder = null;
	WTUser wtuser = (WTUser) CommonUtil.getObject("wt.org.WTUser:" + principalIdString);

	if (CommonUtil.isMember("Administrators", wtuser)) {
	    return true;
	}

	QuerySpec query = new QuerySpec();
	int idx = query.addClassList(wt.content.HolderToContent.class, true);
	query.appendWhere(
	        new SearchCondition(wt.content.HolderToContent.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, Long
	                .parseLong(appDataIdString)), new int[] { idx });
	QueryResult qr = PersistenceHelper.manager.find(query);
	if (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    wt.content.HolderToContent htc = (wt.content.HolderToContent) obj[0];
	    holder = (ContentHolder) htc.getRoleAObject();
	}

	Kogger.debug(AuthUtil.class,
	        "ContentHolder : " + holder.toString() + " / " + "WTUser : " + wtuser.getName() + " / " + wtuser.getFullName());
	if (holder instanceof KETProjectDocument) {
	    KETProjectDocument pDoc = (KETProjectDocument) holder;
	    /* Doc(KETProjectDocument) 연관프로젝트,산출물 프로젝트 멤버 유무, 결재자 기타 등등 */
	    if (isAuthUserDOC(pDoc, wtuser))
		isSecu = true;

	} else if (holder instanceof EPMDocument) {
	    /* 산출물 프로젝트 멤버 유무, 결재자 기타 등등 */
	    EPMDocument epm = (EPMDocument) holder;
	    if (isAuthUserCAD(epm, wtuser))
		isSecu = true;

	} else if (holder instanceof KETTechnicalDocument) {
	    isSecu = true;
	} else if (holder instanceof KETInvestMaster) {
	    String appOid = "wt.content.ApplicationData:" + appDataIdString;
	    ApplicationData apData = (ApplicationData) CommonUtil.getObject(appOid);
	    if (!"1".equals(apData.getDescription())) {// descripton이 1이면 투자품의 파일
		isSecu = true;
	    } else {
		isSecu = isAuthUserInvest((KETInvestMaster) holder, wtuser);
	    }

	} else {
	    isSecu = true;
	}

	Kogger.debug(AuthUtil.class, "isSecu : " + isSecu);
	return isSecu;
    }

    public static boolean isAuthUserInvest(KETInvestMaster im, WTUser wtuser) throws Exception {
	boolean isSecu = false;

	PeopleData pd = new PeopleData(wtuser);

	if (CommonUtil.isMember("고객직접투자비등록관리", wtuser) || "영업원가관리팀".equals(pd.department.getName())) {
	    isSecu = true;
	} else if (im.getManager() != null) {

	    if (im.getManager().getName().equals(wtuser.getName())) {
		isSecu = true;
	    } else if (pd.department != null) {// manager의 팀장 체크
		WTUser ChiefUser = PeopleHelper.manager.getChiefUser(pd.department);
		if (ChiefUser != null && ChiefUser.getName().equals(wtuser.getName())) {
		    isSecu = true;
		}
	    }
	} else {
	    isSecu = WorkflowSearchHelper.manager.userInApproval(im, wtuser.getName());
	}

	return isSecu;
    }

    /**
     * Doc(KETProjectDocument)문서 연관프로젝트,산출물 프로젝트 멤버 유무,( 결재자,작성자,자동차PMO,자동차사업부_KETS_PMO,Admin)
     * 
     * @param pDoc
     * @return
     * @throws WTException
     */
    public static boolean isAuthUserDOC(KETProjectDocument pDoc, WTUser wtuser) throws Exception {

	if (WorkflowSearchHelper.manager.userInApproval(pDoc, wtuser.getName())) {// 결재라인에 포함되어 있으면 ok
	    return true;
	}

	Department checkDept = null;
	boolean isDept = true;

	PeopleData pd = new PeopleData(wtuser);
	Department userDept = pd.department;

	if (userDept != null && "영업원가관리팀".equals(userDept.getName())) {// 부서가 [ 영업원가관리팀 ] 이고 해당문서의 분류체계가 [ 프로젝트완료보고(제품) ] 인 경우 무조건 열람권한 부여
		                                                       // 2021.11.19 김한규 팀장 요청
	    KETDocumentCategory docCate = null;
	    QueryResult r = PersistenceHelper.manager.navigate(pDoc, "documentCategory", KETDocumentCategoryLink.class);
	    if (r.hasMoreElements()) {
		docCate = (KETDocumentCategory) r.nextElement();
	    }
	    if (docCate != null && "PD303148".equals(docCate.getCategoryCode())) {
		return true;
	    }
	}

	if (StringUtils.isNotEmpty(pDoc.getAttribute4())) {// 부서권한이 설정되어 있는지 확인
	    checkDept = DepartmentHelper.manager.getDepartment(pDoc.getAttribute4());
	}

	if (!CommonUtil.isMember("Administrators", wtuser) && checkDept != null) {

	    if (userDept == null) {
		isDept = false;
	    } else {

		if (!checkDept.getCode().equals(userDept.getCode())) {// 접속자의 부서와 문서에 설정된 부서권한이 일치하는지
		    ArrayList childDept = new ArrayList();
		    childDept = DepartmentHelper.manager.getAllChildList(checkDept, childDept);
		    isDept = false;
		    for (Object obj : childDept) {// 접속자의 부서가 문서에 설정된 하위 부서에 속하는지
			Department dp = (Department) obj;
			if (dp.getCode().equals(userDept.getCode())) {
			    isDept = true;
			}
		    }
		}
	    }
	}

	if (!isDept) {// 1. 문서에 설정된 부서권한에 속하지 않는 경우

	    return false;
	}

	if (!CommonUtil.isAdmin() && StringUtils.isNotEmpty(pDoc.getAttribute5())) {
	    Vector dutyCodeList = CompanyState.dutyCodeList;
	    HashMap<String, String> dutyOrder = new HashMap<String, String>();
	    String key = "";
	    for (int i = 0; i < dutyCodeList.size(); i++) {
		key = (String) dutyCodeList.get(i);
		dutyOrder.put(key, String.valueOf(dutyCodeList.size() - i));
	    }

	    String sessionOrder = StringUtils.defaultString(dutyOrder.get(pd.dutycode), "0");
	    String dataOrder = StringUtils.defaultString(dutyOrder.get(pDoc.getAttribute5()), "0");

	    if (Integer.parseInt(sessionOrder) < Integer.parseInt(dataOrder)) {
		return false;// 2. 접속자의 직급코드가 문서에 설정된 직급코드보다 낮은 경우
	    }
	}

	DocumentSearchController dsc = new DocumentSearchController();
	if (dsc.isDocCategoryS2(pDoc)) {
	    if ("Y".equals(dsc.isDocAuthCheckS2(pDoc, wtuser))) {
		return true; // 3. 분류체계에서 대내비설정된 개발산출문서의 경우 관리자/작성자/결재자 로 열람권한을 제한
	    } else {
		return false;
	    }
	}

	return isgAuthUserDOC(pDoc, wtuser);
    }

    private static boolean isgAuthUserDOC(KETProjectDocument pDoc, WTUser wtuser) {

	boolean isSecu = false;
	boolean isRankS = false;
	String security = StringUtil.checkNull(pDoc.getSecurity());
	Kogger.debug(AuthUtil.class, "security : " + security);
	try {

	    /* 연관프로젝트 */
	    QueryResult r4 = PersistenceHelper.manager.navigate(pDoc, "project", KETDocumentProjectLink.class);
	    while (r4.hasMoreElements()) {
		E3PSProject proj1 = (E3PSProject) r4.nextElement();
		if (proj1 instanceof MoldProject) {
		    continue;
		}
		proj1 = ProjectHelper.manager.getProject(proj1.getPjtNo());

		String rank = proj1.getRank() == null ? "" : proj1.getRank().getName();
		Kogger.debug(AuthUtil.class, "연관프로젝트 rank : " + rank);
		if (proj1 instanceof MoldProject && !"S2".equals(security)) {
		    if (ProjectUserHelper.manager.isProjectUser((TemplateProject) proj1, wtuser)) {
			return true;
		    }
		}

		if (proj1 instanceof ProductProject) {
		    String state = proj1.getState().toString();
		    if ("COMPLETED".equals(state)) {
			return true;
		    }
		}

		if (!"S".equals(rank)) {
		    if ("S2".equals(security)) {// 프로젝트가 S가 아닌데 문서가 대내비인 경우
			if (ProjectUserHelper.manager.isProjectUser((TemplateProject) proj1, wtuser))
			    return true;
		    } else {
			return true;
		    }
		} else {// S rnak 이면 대내비
		    isRankS = true;
		    if (ProjectUserHelper.manager.isProjectUser((TemplateProject) proj1, wtuser))
			isSecu = true;
		}
	    }

	    /* 산출물 프로젝트 */
	    ProjectOutput po = null;
	    QueryResult r33 = PersistenceHelper.manager.navigate(pDoc, "output", KETDocumentOutputLink.class);
	    if (r33.hasMoreElements()) {
		while (r33.hasMoreElements()) {
		    po = (ProjectOutput) r33.nextElement();
		    E3PSTask task = (E3PSTask) po.getTask();
		    E3PSProject project = (E3PSProject) task.getProject();
		    if (project instanceof MoldProject) {
			continue;
		    }
		    project = ProjectHelper.manager.getProject(project.getPjtNo());

		    String rank = project.getRank() == null ? "" : project.getRank().getName();
		    Kogger.debug(AuthUtil.class, "산출물 프로젝트 rank : " + rank);

		    if (project instanceof MoldProject && !"S2".equals(security)) {
			if (ProjectUserHelper.manager.isProjectUser((TemplateProject) project, wtuser)) {
			    return true;
			}
		    }

		    if (project instanceof ProductProject) {
			String state = project.getState().toString();
			if ("COMPLETED".equals(state)) {
			    return true;
			}
		    }

		    if (!"S".equals(rank)) {
			if ("S2".equals(security)) {
			    if (ProjectUserHelper.manager.isProjectUser((TemplateProject) project, wtuser))
				return true;
			} else {
			    return true;
			}
		    } else {// S rnak 이면 대내비
			isRankS = true;
			if (ProjectUserHelper.manager.isProjectUser((TemplateProject) project, wtuser))
			    isSecu = true;
		    }
		}
	    }
	    /* 프로젝트가 S인경우 대내비 결재자,작성자,자동차PMO,자동차사업부_KETS_PMO,Admin */
	    if (isSecu)
		return true;

	    if (isRankS) {
		if (isAuthUserDocu(pDoc, wtuser))
		    return true;

	    } else { // 산출물이 대내비인경우 S1 대외비,S2 대내비
		if ("S2".equals(security)) {
		    if (isAuthUserDocu(pDoc, wtuser))
			return true;
		} else {
		    return true;
		}
	    }
	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}
	return isSecu;
    }

    /**
     * 산출물 프로젝트 멤버 유무,( 결재자,작성자,자동차PMO,자동차사업부_KETS_PMO,Admin)
     * 
     * @param epm
     * @return
     * @throws WTException
     */
    public static boolean isAuthUserCAD(EPMDocument epm) throws WTException {

	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	return isAuthUserCAD(epm, wtuser);
    }

    public static boolean isAuthUserCAD(EPMDocument epm, WTUser wtuser) {

	boolean isRankS = false;
	boolean isSecu = false;
	try {
	    String security = StringUtil.checkNull(IBAUtil.getAttrValue(epm, "Security"));

	    /* 연관 프로젝트 */
	    ProjectMaster pjtMst = (ProjectMaster) EDMHelper.getProject(epm);
	    if (pjtMst != null) {
		E3PSProject project = ProjectHelper.getProject(pjtMst.getPjtNo());
		String rank = project.getRank() == null ? "" : project.getRank().getName();

		if (project instanceof MoldProject) {
		    return true;
		}

		if (!"S".equals(rank)) {
		    if ("S2".equals(security)) {
			if (ProjectUserHelper.manager.isProjectUser((TemplateProject) project, wtuser))
			    return true;
		    } else {
			return true;
		    }
		} else {// S rnak 이면 대내비
		    isRankS = true;
		    if (ProjectUserHelper.manager.isProjectUser((TemplateProject) project, wtuser))
			isSecu = true;
		}

	    }

	    /* 산출물 프로젝트 */
	    ProjectOutput po = null;
	    QueryResult r33 = PersistenceHelper.manager.navigate((EPMDocumentMaster) epm.getMaster(), "output", OutputDocumentLink.class);
	    if (r33.hasMoreElements()) {
		while (r33.hasMoreElements()) {
		    po = (ProjectOutput) r33.nextElement();
		    E3PSTask task = (E3PSTask) po.getTask();
		    E3PSProject project = (E3PSProject) task.getProject();

		    project = ProjectHelper.manager.getProject(project.getPjtNo());

		    String rank = project.getRank() == null ? "" : project.getRank().getName();

		    if (project instanceof MoldProject) {
			continue;
		    }

		    if (!"S".equals(rank)) {
			if ("S2".equals(security)) {
			    if (ProjectUserHelper.manager.isProjectUser((TemplateProject) project, wtuser))
				return true;
			} else {
			    return true;
			}
		    } else {// S rnak 이면 대내비
			isRankS = true;
			if (ProjectUserHelper.manager.isProjectUser((TemplateProject) project, wtuser))
			    isSecu = true;
		    }
		}
	    }

	    /* 프로젝트가 S인경우 대내비 결재자,작성자,자동차PMO,자동차사업부_KETS_PMO,Admin */
	    if (isRankS) {
		/* 결재자,작성자,자동차PMO,자동차사업부_KETS_PMO,Admin */
		if (isAuthUserEPM(epm, wtuser))
		    return true;
		/* 설계변경 담당자 */
		if (isECOAuth(epm, wtuser))
		    return true;
	    } else {
		if ("S2".equals(security)) {
		    if (isAuthUserEPM(epm, wtuser))
			return true;
		    /* 설계변경 담당자 */
		    if (isECOAuth(epm, wtuser))
			return true;
		} else {
		    return true;
		}
	    }
	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}

	return isSecu;

    }

    /**
     * 결재자,작성자,자동차PMO,자동차사업부_KETS_PMO,Admin
     * 
     * @param obj
     * @return
     */
    public static boolean isAuthUser(RevisionControlled obj) {
	try {
	    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
	    if (WorkflowSearchHelper.manager.userInApproval(obj, loginUser.getName()) || (loginUser.getName()).equals(obj.getCreatorName())
		    || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("자동차사업부_KETS_PMO") || CommonUtil.isMember("자동차사업부_임원")
		    || CommonUtil.isMember("FTA") || CommonUtil.isAdmin()) {
		return true;
	    }
	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}
	return false;
    }

    /**
     * 개발산출문서의 사업부별 유저 권한 자동차사업부 ( CA ) : admin, 작성자, 결재자, PMO, 임원 전자사업부 ( ER ) : admin, 작성자, 결재자, 임원
     * 
     * @param doc
     * @return
     * @throws WTException
     */
    public static boolean isAuthUserDocu(KETProjectDocument doc) throws WTException {

	return isAuthUserDocu(doc, (WTUser) SessionHelper.manager.getPrincipal());
    }

    private static boolean isAuthUserDocu(KETProjectDocument doc, WTUser wtuser) {

	try {
	    String divisionCode = doc.getDivisionCode();
	    // Kogger.debug(AuthUtil.class, ">>>>>>>>>>>>>>>>>>>> doc divisionCode == " + divisionCode);

	    if (divisionCode.equals("CA")) {
		if (WorkflowSearchHelper.manager.userInApproval(doc, wtuser.getName()) || (wtuser.getName()).equals(doc.getCreatorName())
		        || CommonUtil.isMember("자동차PMO", wtuser) || CommonUtil.isMember("자동차사업부_KETS_PMO", wtuser)
		        || CommonUtil.isMember("자동차사업부_임원", wtuser) || CommonUtil.isMember("FTA", wtuser)
		        || CommonUtil.isMember("Administrators", wtuser)) {
		    return true;
		}
	    } else if (divisionCode.equals("ER")) {
		if (WorkflowSearchHelper.manager.userInApproval(doc, wtuser.getName()) || (wtuser.getName()).equals(doc.getCreatorName())
		        || CommonUtil.isMember("전자사업부_임원", wtuser) || CommonUtil.isMember("Administrators", wtuser)) {
		    return true;
		}
	    }

	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}

	return false;
    }

    /**
     * 도면 등록시 사업부별 유저 권한 자동차사업부 ( C ): admin, 작성자, 결재자, PMO, 임원 전자사업부 ( E ) : admin, 작성자, 결재자, 임원
     * 
     * @param epm
     * @return
     * @throws WTException
     */
    public static boolean isAuthUserEPM(EPMDocument epm) throws WTException {

	return isAuthUserEPM(epm, (WTUser) SessionHelper.manager.getPrincipal());
    }

    public static boolean isAuthUserEPM(EPMDocument epm, WTUser wtuser) {

	try {
	    NumberCode bizCode = (NumberCode) EDMHelper.getBizType(epm);
	    String divisionCode = bizCode.getCode();
	    // Kogger.debug(AuthUtil.class, ">>>>>>>>>>>>>>>>>>>> epm divisionCode == " + divisionCode);
	    /*
	     * 자동차 사업부 : C 전자 사업부 : E
	     */

	    if (divisionCode.equals("C")) {
		if (WorkflowSearchHelper.manager.userInApproval(epm, wtuser.getName()) || (wtuser.getName()).equals(epm.getCreatorName())
		        || CommonUtil.isMember("자동차사업부_임원", wtuser) // || CommonUtil.isMember("FTA", wtuser)
		        || CommonUtil.isMember("Administrators", wtuser)) {
		    return true;
		}
	    } else if (divisionCode.equals("E")) {
		if (WorkflowSearchHelper.manager.userInApproval(epm, wtuser.getName()) || (wtuser.getName()).equals(epm.getCreatorName())
		        || CommonUtil.isMember("전자사업부_임원", wtuser) || CommonUtil.isMember("Administrators", wtuser)) {
		    return true;
		}
	    }
	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}

	return false;
    }

    /**
     * 설계변경 ( 결재라인, 작성자, 담당자 ) 최신버전이면 해당 ECO 하위버전이명 해당 ECO + 최신버전 ECO
     * 
     * @param epm
     * @return
     * @throws WTException
     */
    public static boolean isECOAuth(EPMDocument epm) throws WTException {

	return isECOAuth(epm, (WTUser) SessionHelper.manager.getPrincipal());
    }

    public static boolean isECOAuth(EPMDocument epm, WTUser wtuser) {

	try {

	    if (VersionControlHelper.isLatestIteration(epm)) {
		System.out.println("마지막 버전 체크");
		return isECOUser(epm, wtuser);
	    } else {// 최신이 아닌 경우
		/* 하위버전 */
		// isECOUser(epm, wtuser);
		/* 최신버전 */
		System.out.println("최신이 아닌 경우 체크");
		EPMDocument epmLast = getLastEPMDocument(epm.getNumber());
		if (epmLast != null) {
		    return isECOUser(epmLast, wtuser);
		} else {
		    Kogger.debug(AuthUtil.class, "############# 최신버전 ERROR :" + epm.getNumber());
		}

	    }

	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}

	return false;
    }

    /**
     * 결재라인, 작성자, 담당자
     * 
     * @param epm
     * @return
     * @throws WTException
     */
    public static boolean isECOUser(EPMDocument epm) throws WTException {

	return isECOUser(epm, (WTUser) SessionHelper.manager.getPrincipal());
    }

    public static boolean isECOUser(EPMDocument epm, WTUser wtuser) {

	/* ECO */
	try {
	    String oid = CommonUtil.getOIDString(epm);
	    // Object[] reledChanges = EcmSearchHelper.manager.getRelatedEcoList(oid);
	    wt.fc.Persistable[] reledChanges = ext.ket.edm.approval.KetEpmApprovalHelper.service.getRelatedEcoList(oid);

	    if ((reledChanges != null) && (reledChanges.length > 0)) {

		for (int i = 0; i < reledChanges.length; i++) {
		    WTChangeOrder2 wtEco = (WTChangeOrder2) reledChanges[i];
		    /* 결재라인, 작성자, 담당자 */
		    if (WorkflowSearchHelper.manager.userInApproval(wtEco, wtuser.getName())
			    || wtuser.getName().equals(wtEco.getOwnership().getOwner().getName())
			    || isECOWorkerUser(wtuser.getName(), wtEco)) {
			return true;
		    }
		}

	    }
	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}

	return false;
    }

    /**
     * ECO(KETMoldChangeOrder) 담당자
     * 
     * @return
     */
    public static boolean isECOWorkerUser(String loginUser, WTChangeOrder2 wteco) {

	try {

	    Class cls = null;
	    String fileName = "";
	    if (wteco instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) wteco;
		cls = KETMoldChangeActivity.class;
		fileName = "moldECOReference.key.id";
	    } else {
		KETProdChangeOrder eco = (KETProdChangeOrder) wteco;
		cls = KETProdChangeActivity.class;
		fileName = "prodECOReference.key.id";
	    }

	    QuerySpec qs = new QuerySpec(cls);
	    long ecoLong = CommonUtil.getOIDLongValue(wteco);
	    qs.appendWhere(new SearchCondition(cls, fileName, SearchCondition.EQUAL, ecoLong), new int[0]);

	    QueryResult rt = PersistenceHelper.manager.find(qs);
	    while (rt.hasMoreElements()) {
		if (wteco instanceof KETMoldChangeOrder) {
		    KETMoldChangeActivity ecoActive = (KETMoldChangeActivity) rt.nextElement();
		    String workOid = ecoActive.getWorkerId();
		    WTUser user = (WTUser) CommonUtil.getObject(workOid);
		    if (loginUser.equals(user.getName())) {
			return true;
		    } else {
			return false;
		    }

		} else {
		    KETProdChangeActivity ecoActive = (KETProdChangeActivity) rt.nextElement();
		    String workOid = ecoActive.getWorkerId();
		    WTUser user = (WTUser) CommonUtil.getObject(workOid);
		    if (loginUser.equals(user.getName())) {
			return true;
		    } else {
			return false;
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}

	return false;
    }

    /**
     * 최신 EPMDocument
     * 
     * @param epmNumber
     * @return
     */
    public static EPMDocument getLastEPMDocument(String epmNumber) {

	try {
	    QuerySpec spec = new QuerySpec();
	    Class cls_itr = EPMDocument.class;
	    Class cls_master = EPMDocumentMaster.class;

	    int idx_m = spec.appendClassList(cls_master, false);
	    int idx_cls = spec.appendClassList(cls_itr, false);

	    spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", cls_itr, "masterReference.key.id"),
		    new int[] { idx_m, idx_cls });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(wt.vc.VersionControlHelper.getSearchCondition(cls_itr, true), new int[] { idx_cls });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_itr));
	    spec.appendWhere(
		    new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_itr, paramBoolean, spec,
		            idx_cls))), null);

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(cls_master, EPMDocumentMaster.NUMBER, SearchCondition.EQUAL, epmNumber), new int[idx_m]);

	    QueryResult rt = PersistenceHelper.manager.find(spec);
	    while (rt.hasMoreElements()) {
		Object[] obj = (Object[]) rt.nextElement();
		EPMDocument epm = (EPMDocument) obj[0];
		return epm;
	    }

	} catch (Exception e) {
	    Kogger.error(AuthUtil.class, e);
	}

	return null;
    }
}
