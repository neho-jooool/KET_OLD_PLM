package ext.ket.arm.entity;

import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.dms.entity.KETProjectDocument;
import ext.ket.arm.service.KETAnalysisInfoHelper;
import ext.ket.shared.dto.BaseDTO;

/**
 * KETAnalysisRequest DTO 객체
 * 
 * @클래스명 : KETAnalysisRequestDTO
 * @작성자 : hooni
 * @작성일 : 2014. 10. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class KETAnalysisInfoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String mOid;
    private String analysisInfoOid;
    private String analysisTitle; // 해석의뢰 제목
    private String analysisRequestNo; // 해석의뢰 번호
    private String analysisDivision; // 해석구분
    private String webEditor; // 해석항목 webEditor
    private String webEditorText; // 해석항목 webEditorText
    private String designSpec; // 디자인스펙
    private String requestedTerm; // 요청사항
    private String etc; // 비고
    private String state; // 결재상태
    private String stateName; // 결재상태 명
    private String createInfoUserFlage; // 접수작성자여부확인
    private String startDate; // 시작일
    private boolean createUserFlag = false;
    private String approvalUser;// 해석 담당자Oid
    private String viewDocTag;

    public KETAnalysisInfoDTO() {
    }

    public KETAnalysisInfoDTO KETAnalysisInfoDTO(KETAnalysisInfoDTO analysisInfoDTO, KETAnalysisRequestInfo analysisInfo) throws Exception {
	super.setOid(CommonUtil.getOIDString(analysisInfo));
	analysisInfoDTO.setmOid(CommonUtil.getOIDString(analysisInfo));
	analysisInfoDTO.setAnalysisInfoOid(CommonUtil.getOIDString(analysisInfo));
	analysisInfoDTO.setWebEditor(analysisInfo.getWebEditor()); // 해석의뢰접수 WEBEDITOR
	analysisInfoDTO.setWebEditorText(analysisInfo.getWebEditorText()); // 해석의뢰접수 WEBEDITOR
	analysisInfoDTO.setDesignSpec(analysisInfo.getDesignSpec()); // 디자인스팩
	analysisInfoDTO.setRequestedTerm(analysisInfo.getRequestedTerm()); // 요청사항
	analysisInfoDTO.setEtc(analysisInfo.getEtc()); // 기타
	analysisInfoDTO.setState(analysisInfo.getLifeCycleState().toString()); // 결재상태

	if ("INWORK".equals(analysisInfo.getLifeCycleState().toString()) || "REWORK".equals(analysisInfo.getLifeCycleState().toString())) {
	    analysisInfoDTO.setStateName("의뢰접수중"); // 결재상태Name
	} else if ("UNDERREVIEW".equals(analysisInfo.getLifeCycleState().toString())) {
	    analysisInfoDTO.setStateName("의뢰검토중"); // 결재상태Name
	} else if ("APPROVED".equals(analysisInfo.getLifeCycleState().toString())) {
	    analysisInfoDTO.setStateName("의뢰완료"); // 결재상태Name

	}

	if (analysisInfo.getCreatorFullName().equals(SessionHelper.manager.getPrincipalReference().getFullName())) {
	    analysisInfoDTO.setCreateUserFlag(true);
	} else {
	    analysisInfoDTO.setCreateUserFlag(false);
	}

	String docOid[] = KETAnalysisInfoHelper.service.getKETProjectDocumentOid(
	        KETAnalysisInfoHelper.service.getAnalysisMasterOid(getmOid())).split(",");

	KETProjectDocument document = null;
	String documentTag = "";
	if (docOid.length > 1) {
	    for (int i = 0; docOid.length > i; i++) {
		document = (KETProjectDocument) CommonUtil.getObject(docOid[i].toString());
		documentTag += "<tr><td class='tdwhiteM'>" + document.getDocumentNo() + "</td><td class='tdwhiteM'>" + document.getTitle()
		        + "</td></tr>";
	    }
	}
	analysisInfoDTO.setViewDocTag(documentTag);

	return analysisInfoDTO;
    }

    public String getmOid() {
	return mOid;
    }

    public void setmOid(String mOid) {
	this.mOid = mOid;
    }

    public String getAnalysisInfoOid() {
	return analysisInfoOid;
    }

    public void setAnalysisInfoOid(String analysisInfoOid) {
	this.analysisInfoOid = analysisInfoOid;
    }

    public String getAnalysisTitle() {
	return analysisTitle;
    }

    public void setAnalysisTitle(String analysisTitle) {
	this.analysisTitle = analysisTitle;
    }

    public String getAnalysisRequestNo() {
	return analysisRequestNo;
    }

    public void setAnalysisRequestNo(String analysisRequestNo) {
	this.analysisRequestNo = analysisRequestNo;
    }

    public String getAnalysisDivision() {
	return analysisDivision;
    }

    public void setAnalysisDivision(String analysisDivision) {
	this.analysisDivision = analysisDivision;
    }

    public String getWebEditor() {
	return webEditor;
    }

    public void setWebEditor(String webEditor) {
	this.webEditor = webEditor;
    }

    public String getWebEditorText() {
	return webEditorText;
    }

    public void setWebEditorText(String webEditorText) {
	this.webEditorText = webEditorText;
    }

    public String getDesignSpec() {
	return designSpec;
    }

    public void setDesignSpec(String designSpec) {
	this.designSpec = designSpec;
    }

    public String getRequestedTerm() {
	return requestedTerm;
    }

    public void setRequestedTerm(String requestedTerm) {
	this.requestedTerm = requestedTerm;
    }

    public String getEtc() {
	return etc;
    }

    public void setEtc(String etc) {
	this.etc = etc;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getStateName() {
	return stateName;
    }

    public void setStateName(String stateName) {
	this.stateName = stateName;
    }

    public String getCreateInfoUserFlage() {
	return createInfoUserFlage;
    }

    public void setCreateInfoUserFlage(String createInfoUserFlage) {
	this.createInfoUserFlage = createInfoUserFlage;
    }

    public String getStartDate() {
	return startDate;
    }

    public void setStartDate(String startDate) {
	this.startDate = startDate;
    }

    public boolean isCreateUserFlag() {
	return createUserFlag;
    }

    public void setCreateUserFlag(boolean createUserFlag) {
	this.createUserFlag = createUserFlag;
    }

    public String getApprovalUser() {
	return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
	this.approvalUser = approvalUser;
    }

    public String getViewDocTag() {
	return viewDocTag;
    }

    public void setViewDocTag(String viewDocTag) {
	this.viewDocTag = viewDocTag;
    }

}
