package ext.ket.dqm.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.ecm.entity.KETEcoDqmLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.ProductProject;
import e3ps.project.beans.E3PSProjectData;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;

public class KETDqmDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String mode;
    private String ida2a2;
    private String raiseOid;
    private String pjtno;
    private String pjtname;
    private String pjtoid;
    private String pmUserName;
    private String closerName;
    private String customerName;
    private String customerCode;
    private String cartypeName;
    private String cartypeCode;
    private String problemTypeName;
    private String problemTypeCode;
    private String urgencyName;
    private String urgencyCode;
    private String importanceName; // 중요도 명칭
    private String importanceCode; // 중요도 코드
    private String partCategoryName;
    private String partCategoryCode;
    private String relatedPart;
    private String relatedPartOid;
    private String occurDivName;
    private String occurDivCode;
    private String occurStepName; // 발생단계
    private String occurStepCode;
    private String occurPlaceName;
    private String occurPlaceCode;
    private String occurCode;
    private String occurName;
    private String occurDate;
    private String actionDeptCode;
    private String actionDeptName;
    private String requestDate;
    private String webEditor;
    private String webEditorText;

    private String problemNo;
    private String problem;

    private String actionOid;

    private String causeWebEditor;
    private String causeWebEditorText;
    private String improveWebEditor;
    private String improveWebEditorText;

    private String closeOid;

    private String applyExpectDate;
    private String reviewCheckCode;
    private String reviewDate;
    private String reviewRsltCode;
    private String reviewer;
    private String causeCode;
    private String causeCodeName;

    private String designReflect;
    private String designReflectName;

    private String defectDivName;
    private String defectDivCode;
    private String defectTypeName;
    private String defectTypeCode;
    private String applyArea1;
    private String applyArea2;
    private String applyArea3;

    private String dqmStateName;
    private String dqmStateCode;

    private String series;
    private String seriesName;
    private String pmUserOid;
    private String closerOid;
    private String raiserUserOid;
    private String raiserUserName;

    private boolean createUserFlag;
    private boolean pmUserFlag;
    private boolean actionUserFlag;
    private boolean closeUserFlag;

    private String reviewWebEditor;
    private String reviewWebEditorText;

    private String actionUserName;
    private String actionUserDept;
    private String actionUserOid;

    private String drawingOutDate;
    private String moldModifyDate;
    private String toDate;
    private String trustTestDate;

    private String duplicateYn;
    private String duplicateReportName;
    private String duplicateReportCode;
    private String actionCreateStamp;

    private String relatedEcrOid;
    private String relatedEcrNo;

    private String raiseApprovName;
    private String raiseApprovDate;
    private String raiseApprovDept;

    private String raiseReviewName;
    private String raiseReviewDate;
    private String raiseReviewDept;

    private String raiseCreateStamp;
    private String raiseCreateUserName;
    private String raiseCreateUserDept;

    private String actionReviewName;
    private String actionReviewDate;
    private String actionReviewDept;

    private String actionApprovName;
    private String actionApprovDate;
    private String actionApprovDept;

    private String createStartDate;
    private String createEndDate;
    private String compStartDate;
    private String compEndDate;

    private String createStamp;
    private String problemReflectYn;
    private String designChangeYn;
    private String validationDate;

    private String validationCheck;

    // 테스크별 품질문제 체크항목 관리용 2014.09.16
    private String outputCheck;

    private String outputOid;

    // Gate에서 링크OID정보를 담기위한 변수추가 2014.11.29
    private String dqmLinkOid;

    private String issueCode; // 문제점구분 코드
    private String issueName; // 문제점구분 명칭

    private String occurPointCode; // 발생시점 코드
    private String occurPointName; // 발생시점 명칭

    private String mainSubject; // 진행사항
    private String mainSubjectHtml;

    private String execEndDate;// 실제완료일

    private String byProjectDqm;// 프로젝트에서 등록시 플래그로 활용

    private String dqmStateFlag = "";// list 화면에서 지연여부 판단

    private List<HashMap<String, String>> relatedEcrInfoList = new ArrayList<HashMap<String, String>>();

    public KETDqmDTO() {
    }

    public KETDqmDTO KETDqmDTOOidSetting(KETDqmHeader ketDqmHeader, KETDqmRaise ketDqmRaise, KETDqmAction ketDqmAction,
	    KETDqmClose ketDqmClose, KETDqmDTO ketDqmDTO) throws Exception {
	if (ketDqmHeader != null && !ketDqmHeader.equals(null)) {
	    ketDqmDTO.setOid(CommonUtil.getOIDString(ketDqmHeader));
	}
	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    ketDqmDTO.setRaiseOid(CommonUtil.getOIDString(ketDqmRaise));
	    if (ketDqmRaise.getCreator() != null && !ketDqmRaise.getCreator().equals(null)) {
		WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
		PeopleData peopleData = new PeopleData(createUser);
		ketDqmDTO.setRaiseCreateUserName(peopleData.name);
		if (ketDqmRaise.getCreator().equals(SessionHelper.manager.getPrincipalReference())) {
		    ketDqmDTO.setCreateUserFlag(true);
		} else {
		    ketDqmDTO.setCreateUserFlag(false);
		}
	    } else {
		ketDqmDTO.setCreateUserFlag(false);
	    }
	}
	if (ketDqmAction != null && !ketDqmAction.equals(null)) {
	    ketDqmDTO.setActionOid(CommonUtil.getOIDString(ketDqmAction));
	}
	if (ketDqmClose != null && !ketDqmClose.equals(null)) {
	    ketDqmDTO.setCloseOid(CommonUtil.getOIDString(ketDqmClose));
	}

	return ketDqmDTO;
    }

    public KETDqmDTO KETDqmDTOGrid(KETDqmHeader ketDqmHeader, KETDqmDTO ketDqmDTO) throws Exception {
	if (ketDqmHeader != null && !ketDqmHeader.equals(null)) {
	    // 문제점, 불량구분, 불량유형, 발생처, 발생일, 부품NO, 차종, 발생구분, 완료일, 상태
	    ketDqmDTO.setOid(CommonUtil.getOIDString(ketDqmHeader));
	    ketDqmDTO.problem = ketDqmHeader.getProblem();
	    ketDqmDTO.problemNo = ketDqmHeader.getProblemNo();
	    ketDqmDTO.dqmStateCode = ketDqmHeader.getDqmStateCode();
	    ketDqmDTO.dqmStateName = ketDqmHeader.getDqmStateName();

	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	    ketDqmDTO.setRaiseOid(CommonUtil.getOIDString(ketDqmRaise));
	    ketDqmDTO.createStamp = DateUtil.getDateString(ketDqmRaise.getCreateTimestamp(), "date");
	    if (ketDqmRaise.getCreator() != null && !ketDqmRaise.getCreator().equals(null)) {
		WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
		PeopleData peopleData = new PeopleData(createUser);
		ketDqmDTO.raiseCreateUserName = peopleData.name;
	    }
	    ketDqmDTO.customerCode = ketDqmRaise.getCustomerCode();
	    ketDqmDTO.cartypeCode = ketDqmRaise.getCartypeCode();
	    ketDqmDTO.problemTypeCode = ketDqmRaise.getProblemTypeCode();
	    ketDqmDTO.urgencyCode = ketDqmRaise.getUrgencyCode();
	    ketDqmDTO.partCategoryCode = ketDqmRaise.getPartCategoryCode();

	    ketDqmDTO.customerName = ketDqmRaise.getCustomerName();
	    ketDqmDTO.cartypeName = ketDqmRaise.getCartypeName();
	    ketDqmDTO.problemTypeName = ketDqmRaise.getProblemTypeName();
	    ketDqmDTO.urgencyName = ketDqmRaise.getUrgencyName();
	    ketDqmDTO.partCategoryName = ketDqmRaise.getPartCategoryName();
	    ketDqmDTO.occurDivCode = ketDqmRaise.getOccurDivCode();
	    ketDqmDTO.occurStepCode = ketDqmRaise.getOccurStepCode();
	    ketDqmDTO.occurCode = ketDqmRaise.getOccurCode();

	    ketDqmDTO.occurDivName = ketDqmRaise.getOccurDivName();
	    ketDqmDTO.occurPlaceName = ketDqmRaise.getOccurPlaceName();
	    ketDqmDTO.occurStepName = ketDqmRaise.getOccurStepName();
	    ketDqmDTO.occurName = ketDqmRaise.getOccurName();

	    ketDqmDTO.occurDate = DateUtil.getDateString(ketDqmRaise.getOccurDate(), "date");
	    ketDqmDTO.defectDivName = ketDqmRaise.getDefectDivName();
	    ketDqmDTO.defectDivCode = ketDqmRaise.getDefectDivCode();
	    ketDqmDTO.defectTypeName = ketDqmRaise.getDefectTypeName();
	    ketDqmDTO.defectTypeCode = ketDqmRaise.getDefectTypeCode();

	    WTPart wtPart = ketDqmRaise.getPart();
	    if (wtPart != null && !wtPart.equals(null)) {
		ketDqmDTO.relatedPart = wtPart.getNumber();
		ketDqmDTO.relatedPartOid = CommonUtil.getOIDString(wtPart);
		// ketDqmDTO.series = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpSeries);
	    }

	    // // 시리즈 가져오기 리스트에서 시리즈 표현 X
	    // ketDqmDTO.seriesName = "";
	    // ketDqmDTO.series = "";
	    //
	    // QueryResult qrSeries = PersistenceHelper.manager.navigate(ketDqmRaise, "dqmRaiseSeries", KETDqmRaiseSeriesLink.class, false);
	    // while (qrSeries.hasMoreElements()) {
	    // KETDqmRaiseSeriesLink ketDqmRaiseSeriesLink = (KETDqmRaiseSeriesLink) qrSeries.nextElement();
	    // KETDqmRaiseSeries ketDqmRaiseSeries = (KETDqmRaiseSeries) ketDqmRaiseSeriesLink.getDqmRaiseSeries();
	    // if (!ketDqmDTO.seriesName.equals("")) {
	    // ketDqmDTO.seriesName += ", ";
	    // }
	    // if (!ketDqmDTO.series.equals("")) {
	    // ketDqmDTO.series += ",";
	    // }
	    // ketDqmDTO.seriesName += ketDqmRaiseSeries.getName();
	    // ketDqmDTO.series += ketDqmRaiseSeries.getCode();
	    // }

	    KETDqmAction ketDqmAction = ketDqmHeader.getAction();
	    if (ketDqmAction != null && !ketDqmAction.equals(null)) {

		KETEcoDqmLink ketEcoDqmLink = null;
		KETProdChangeOrder eco = null;
		QueryResult qr = PersistenceHelper.manager.navigate(ketDqmAction, "eco", KETEcoDqmLink.class, false);
		while (qr.hasMoreElements()) {
		    ketEcoDqmLink = (KETEcoDqmLink) qr.nextElement();
		    eco = (KETProdChangeOrder) ketEcoDqmLink.getEco();
		    if (!StringUtil.checkNull(relatedEcrNo).equals(null) && !StringUtil.checkNull(relatedEcrNo).equals("")) {
			ketDqmDTO.relatedEcrNo += ",";
		    } else {
			ketDqmDTO.relatedEcrNo = "";
		    }
		    ketDqmDTO.relatedEcrNo += eco.getEcoId().replace("ECO-", "");
		}

		// 원인
		ketDqmDTO.causeCode = "";
		ketDqmDTO.causeCodeName = "";

		QueryResult qrCause = PersistenceHelper.manager
		        .navigate(ketDqmAction, "dqmActionCause", KETDqmActionCauseLink.class, false);
		while (qrCause.hasMoreElements()) {
		    KETDqmActionCauseLink ketDqmActionCauseLink = (KETDqmActionCauseLink) qrCause.nextElement();
		    KETDqmActionCause ketDqmActionCause = (KETDqmActionCause) ketDqmActionCauseLink.getDqmActionCause();
		    if (!ketDqmDTO.causeCode.equals("")) {
			ketDqmDTO.causeCode += ",";
		    }
		    if (!ketDqmDTO.causeCodeName.equals("")) {
			ketDqmDTO.causeCodeName += "/";
		    }
		    ketDqmDTO.causeCodeName += CodeHelper.manager.getCodeValue("PROBLEMTEAM", ketDqmActionCause.getCode());
		    ketDqmDTO.causeCode += ketDqmActionCause.getCode();
		}
	    }

	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	    if (ketDqmClose != null && !ketDqmClose.equals(null)) {
		ketDqmDTO.reviewDate = DateUtil.getDateString(ketDqmClose.getReviewDate(), "date");
	    }

	    ketDqmDTO.pjtno = ketDqmRaise.getPjtno();
	    ketDqmDTO.pjtname = ketDqmRaise.getPjtname();
	    ketDqmDTO.issueName = ketDqmRaise.getIssueName();
	    ketDqmDTO.occurStepName = ketDqmRaise.getOccurStepName();
	    ketDqmDTO.occurPointName = ketDqmRaise.getOccurPointName();

	    if (ketDqmRaise.getRequestDate() != null) {
		ketDqmDTO.requestDate = DateUtil.getDateString(ketDqmRaise.getRequestDate(), "date");// 완료목표일
	    }

	    ketDqmDTO.raiseCreateUserName = ketDqmRaise.getCreatorUserName();
	    ketDqmDTO.raiseCreateUserDept = ketDqmRaise.getCreatorDeptName();

	    ketDqmDTO.actionUserName = ketDqmRaise.getActionUserName();
	    ketDqmDTO.actionDeptName = ketDqmRaise.getActionUserDeptName();
	}
	return ketDqmDTO;
    }

    public KETDqmDTO KETDqmDTO(KETDqmHeader ketDqmHeader, KETDqmDTO ketDqmDTO) {
	if (ketDqmHeader != null && !ketDqmHeader.equals(null)) {
	    ketDqmDTO.setOid(CommonUtil.getOIDString(ketDqmHeader));
	    ketDqmDTO.problem = ketDqmHeader.getProblem();
	    ketDqmDTO.problemNo = ketDqmHeader.getProblemNo();
	    ketDqmDTO.dqmStateCode = ketDqmHeader.getDqmStateCode();
	    ketDqmDTO.dqmStateName = ketDqmHeader.getDqmStateName();
	}
	return ketDqmDTO;
    }

    public KETDqmDTO KETDqmDTO(KETDqmClose ketDqmClose, KETDqmRaise ketDqmRaise, KETDqmDTO ketDqmDTO) throws Exception {
	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    WTUser wtPmUser = ketDqmRaise.getPmUser();
	    if (wtPmUser != null && !wtPmUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtPmUser);
		ketDqmDTO.pmUserName = peopleData.name;
		ketDqmDTO.pmUserOid = CommonUtil.getOIDString(wtPmUser);
		if (wtPmUser.equals((WTUser) SessionHelper.manager.getPrincipal())) {
		    ketDqmDTO.pmUserFlag = true;
		} else {
		    ketDqmDTO.pmUserFlag = false;
		}
	    } else {
		ketDqmDTO.pmUserFlag = false;
	    }

	    WTUser wtCloseUser = ketDqmRaise.getCloseUser();
	    if (wtCloseUser != null && !wtCloseUser.equals(null)) {

		if (wtCloseUser.equals((WTUser) SessionHelper.manager.getPrincipal())) {
		    ketDqmDTO.closeUserFlag = true;
		} else {
		    ketDqmDTO.closeUserFlag = false;
		}
	    } else {
		ketDqmDTO.closeUserFlag = false;
	    }
	}

	if (ketDqmClose != null && !ketDqmClose.equals(null)) {
	    ketDqmDTO.setCloseOid(CommonUtil.getOIDString(ketDqmClose));
	    ketDqmDTO.reviewRsltCode = ketDqmClose.getReviewRsltCode();
	    ketDqmDTO.reviewDate = DateUtil.getDateString(ketDqmClose.getReviewDate(), "date");
	    ketDqmDTO.reviewWebEditor = (String) ketDqmClose.getWebEditor();
	    ketDqmDTO.reviewWebEditorText = (String) ketDqmClose.getWebEditorText();
	    ketDqmDTO.validationCheck = ketDqmClose.getValidationCheck();
	    // 종결에서 검토로 변경
	    // ketDqmDTO.problemReflectYn = (String) ketDqmClose.getProblemReflectYn();
	    // ketDqmDTO.validationDate = DateUtil.getDateString(ketDqmClose.getValidationDate(), "date");
	    ketDqmDTO.reviewCheckCode = ketDqmClose.getReviewCheckCode();
	    ketDqmDTO.applyExpectDate = DateUtil.getDateString(ketDqmClose.getApplyExpectDate(), "date");
	    if (ketDqmClose.getUser() != null && !ketDqmClose.getUser().equals(null)) {
		PeopleData peopleData = new PeopleData(ketDqmClose.getUser());
		ketDqmDTO.reviewer = peopleData.name;
	    }

	}
	return ketDqmDTO;
    }

    public KETDqmDTO KETDqmDTO(KETDqmRaise ketDqmRaise, KETDqmDTO ketDqmDTO) throws Exception {
	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    ketDqmDTO.setRaiseOid(CommonUtil.getOIDString(ketDqmRaise));
	    ketDqmDTO.customerCode = ketDqmRaise.getCustomerCode();
	    ketDqmDTO.cartypeCode = ketDqmRaise.getCartypeCode();
	    ketDqmDTO.problemTypeCode = ketDqmRaise.getProblemTypeCode();
	    ketDqmDTO.urgencyCode = ketDqmRaise.getUrgencyCode();
	    ketDqmDTO.partCategoryCode = ketDqmRaise.getPartCategoryCode();
	    ketDqmDTO.importanceCode = ketDqmRaise.getImportanceCode();
	    ketDqmDTO.issueCode = ketDqmRaise.getIssueCode();
	    ketDqmDTO.occurPointCode = ketDqmRaise.getOccurPointCode();

	    ketDqmDTO.customerName = ketDqmRaise.getCustomerName();
	    ketDqmDTO.cartypeName = ketDqmRaise.getCartypeName();
	    ketDqmDTO.problemTypeName = ketDqmRaise.getProblemTypeName();
	    ketDqmDTO.urgencyName = ketDqmRaise.getUrgencyName();
	    ketDqmDTO.importanceName = ketDqmRaise.getImportanceName();
	    ketDqmDTO.partCategoryName = ketDqmRaise.getPartCategoryName();

	    ketDqmDTO.issueName = ketDqmRaise.getIssueName();// 문제점구분
	    ketDqmDTO.occurPointName = ketDqmRaise.getOccurPointName();// 발생시점

	    // ketDqmDTO.relatedPart = ketDqmRaise.getRelatedPart();
	    ketDqmDTO.occurDivCode = ketDqmRaise.getOccurDivCode();
	    ketDqmDTO.occurStepCode = ketDqmRaise.getOccurStepCode();
	    ketDqmDTO.occurCode = ketDqmRaise.getOccurCode();

	    ketDqmDTO.occurDivName = ketDqmRaise.getOccurDivName();
	    ketDqmDTO.occurPlaceName = ketDqmRaise.getOccurPlaceName();
	    ketDqmDTO.occurStepName = ketDqmRaise.getOccurStepName();
	    ketDqmDTO.occurName = ketDqmRaise.getOccurName();

	    ketDqmDTO.occurDate = DateUtil.getDateString(ketDqmRaise.getOccurDate(), "date");
	    ketDqmDTO.actionDeptCode = ketDqmRaise.getActionDeptCode();

	    ketDqmDTO.actionDeptName = ketDqmRaise.getActionDeptName();

	    ketDqmDTO.requestDate = DateUtil.getDateString(ketDqmRaise.getRequestDate(), "date");
	    ketDqmDTO.webEditor = (String) ketDqmRaise.getWebEditor();
	    ketDqmDTO.webEditorText = (String) ketDqmRaise.getWebEditorText();

	    ketDqmDTO.raiseCreateStamp = DateUtil.getDateString(ketDqmRaise.getCreateTimestamp(), "date");

	    ketDqmDTO.defectDivName = ketDqmRaise.getDefectDivName();
	    ketDqmDTO.defectDivCode = ketDqmRaise.getDefectDivCode();
	    ketDqmDTO.defectTypeName = ketDqmRaise.getDefectTypeName();
	    ketDqmDTO.defectTypeCode = ketDqmRaise.getDefectTypeCode();
	    ketDqmDTO.applyArea1 = ketDqmRaise.getApplyArea1();
	    ketDqmDTO.applyArea2 = ketDqmRaise.getApplyArea2();
	    ketDqmDTO.applyArea3 = ketDqmRaise.getApplyArea3();

	    WTUser wtCloseUser = ketDqmRaise.getCloseUser();
	    if (wtCloseUser != null && !wtCloseUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtCloseUser);
		ketDqmDTO.closerName = peopleData.name;
		ketDqmDTO.closerOid = CommonUtil.getOIDString(wtCloseUser);
	    } else {
		ketDqmDTO.closerName = "";
		ketDqmDTO.closerOid = "";
	    }
	    WTUser wtActionUser = ketDqmRaise.getActionUser();

	    if (wtActionUser != null && !wtActionUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtActionUser);
		ketDqmDTO.actionUserName = peopleData.name;
		ketDqmDTO.actionUserOid = CommonUtil.getOIDString(wtActionUser);
	    } else {
		ketDqmDTO.actionUserName = "";
		ketDqmDTO.actionUserOid = "";
	    }

	    ProductProject productProject = ketDqmRaise.getProductProject();
	    if (productProject != null && !productProject.equals(null)) {
		E3PSProject project = (E3PSProject) CommonUtil.getObject(CommonUtil.getOIDString(productProject));
		E3PSProjectData projectData;
		try {
		    projectData = new E3PSProjectData(project);
		    ketDqmDTO.pjtno = projectData.pjtNo;
		    ketDqmDTO.pjtoid = CommonUtil.getOIDString(project);
		    ketDqmDTO.pjtname = projectData.pjtName;
		    // ketDqmDTO.pmUserName = projectData.pjtPmName;
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
	    }
	    WTPart wtPart = ketDqmRaise.getPart();
	    if (wtPart != null && !wtPart.equals(null)) {
		ketDqmDTO.relatedPart = wtPart.getNumber();
		ketDqmDTO.relatedPartOid = CommonUtil.getOIDString(wtPart);
		// TODO
		// ketDqmDTO.series = wtPart.getSeries();
	    }

	    // 시리즈 가져오기
	    ketDqmDTO.seriesName = "";
	    ketDqmDTO.series = "";

	    QueryResult qrSeries = PersistenceHelper.manager.navigate(ketDqmRaise, "dqmRaiseSeries", KETDqmRaiseSeriesLink.class, false);
	    while (qrSeries.hasMoreElements()) {
		KETDqmRaiseSeriesLink ketDqmRaiseSeriesLink = (KETDqmRaiseSeriesLink) qrSeries.nextElement();
		KETDqmRaiseSeries ketDqmRaiseSeries = (KETDqmRaiseSeries) ketDqmRaiseSeriesLink.getDqmRaiseSeries();
		if (!ketDqmDTO.seriesName.equals("")) {
		    ketDqmDTO.seriesName += ", ";
		}
		if (!ketDqmDTO.series.equals("")) {
		    ketDqmDTO.series += ",";
		}
		ketDqmDTO.seriesName += ketDqmRaiseSeries.getName();
		ketDqmDTO.series += ketDqmRaiseSeries.getCode();
	    }

	    WTUser wtPmUser = ketDqmRaise.getPmUser();
	    if (wtPmUser != null && !wtPmUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtPmUser);
		ketDqmDTO.pmUserName = peopleData.name;
		ketDqmDTO.pmUserOid = CommonUtil.getOIDString(wtPmUser);
		if (wtPmUser.equals((WTUser) SessionHelper.manager.getPrincipal())) {
		    ketDqmDTO.pmUserFlag = true;
		} else {
		    ketDqmDTO.pmUserFlag = false;
		}
	    } else {
		ketDqmDTO.pmUserFlag = false;
	    }

	    WTUser wtRaiseUser = ketDqmRaise.getUser();
	    if (wtRaiseUser != null && !wtRaiseUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtRaiseUser);
		ketDqmDTO.raiserUserName = peopleData.name;
		ketDqmDTO.raiserUserOid = CommonUtil.getOIDString(wtRaiseUser);
	    }

	    if (ketDqmRaise.getCreator() != null && !ketDqmRaise.getCreator().equals(null)) {
		WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
		PeopleData peopleData = new PeopleData(createUser);
		ketDqmDTO.raiseCreateUserName = peopleData.name;
		if (ketDqmRaise.getCreator().equals(SessionHelper.manager.getPrincipalReference())) {
		    ketDqmDTO.createUserFlag = true;
		} else {
		    ketDqmDTO.createUserFlag = false;
		}
	    } else {
		ketDqmDTO.createUserFlag = false;
	    }

	    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(ketDqmDTO.raiseOid));
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    Object temp = new Object();
	    Vector vec = null;
	    // out.println(master.getPbo().toString()); 결재객체oid확인
	    if (master != null) {

		vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

		if (vec != null) {
		    String activityName = "&nbsp;";

		    for (int i = 0; i < vec.size(); i++) {
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);

			activityName = StringUtil.checkNull(history.getActivityName());
			if (activityName.equals("검토") && history.isLast()) {
			    if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인")) {
				ketDqmDTO.raiseApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				if (history.getCompletedDate() != null)
				    ketDqmDTO.raiseApprovDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
			    }
			}

		    }
		}
	    }

	}

	return ketDqmDTO;
	/*
	 * try { this.iterationCode = pastProblemManagement.getIterationInfo().getCodeentifier().getSeries().getValue(); } catch
	 * (VersionControlException e) { // TODO Auto-generated catch block this.iterationCode = "0"; Kogger.error(getClass(), e); }
	 */
    }

    public KETDqmDTO KETDqmDTO(KETDqmAction ketDqmAction, KETDqmRaise ketDqmRaise, KETDqmDTO ketDqmDTO) throws Exception {
	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    WTUser wtPmUser = ketDqmRaise.getPmUser();
	    if (wtPmUser != null && !wtPmUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtPmUser);
		ketDqmDTO.pmUserName = peopleData.name;
		ketDqmDTO.pmUserOid = CommonUtil.getOIDString(wtPmUser);
		if (wtPmUser.equals((WTUser) SessionHelper.manager.getPrincipal())) {
		    ketDqmDTO.pmUserFlag = true;
		} else {
		    ketDqmDTO.pmUserFlag = false;
		}
	    } else {
		ketDqmDTO.pmUserFlag = false;
	    }
	}

	if (ketDqmAction != null && !ketDqmAction.equals(null)) {
	    ketDqmDTO.setActionOid(CommonUtil.getOIDString(ketDqmAction));
	    // ketDqmDTO.causeCode = ketDqmAction.getCauseCode();

	    // 원인
	    ketDqmDTO.causeCode = "";
	    ketDqmDTO.causeCodeName = "";

	    QueryResult qrCause = PersistenceHelper.manager.navigate(ketDqmAction, "dqmActionCause", KETDqmActionCauseLink.class, false);
	    while (qrCause.hasMoreElements()) {
		KETDqmActionCauseLink ketDqmActionCauseLink = (KETDqmActionCauseLink) qrCause.nextElement();
		KETDqmActionCause ketDqmActionCause = (KETDqmActionCause) ketDqmActionCauseLink.getDqmActionCause();
		if (!ketDqmDTO.causeCode.equals("")) {
		    ketDqmDTO.causeCode += ",";
		}
		if (!ketDqmDTO.causeCodeName.equals("")) {
		    ketDqmDTO.causeCodeName += "/";
		}
		ketDqmDTO.causeCodeName += CodeHelper.manager.getCodeValue("PROBLEMTEAM", ketDqmActionCause.getCode());
		ketDqmDTO.causeCode += ketDqmActionCause.getCode();
	    }

	    // 설계변경여부
	    ketDqmDTO.designReflect = "";
	    ketDqmDTO.designReflectName = "";

	    QueryResult qrDesignReflect = PersistenceHelper.manager.navigate(ketDqmAction, "dqmActionDesignReflect",
		    KETActionDesignReflectLink.class, false);
	    while (qrDesignReflect.hasMoreElements()) {
		KETActionDesignReflectLink ketActionDesignReflectLink = (KETActionDesignReflectLink) qrDesignReflect.nextElement();
		KETDqmActionDesignReflect ketDqmActionDesignReflect = (KETDqmActionDesignReflect) ketActionDesignReflectLink
		        .getDqmActionDesignReflect();
		if (!ketDqmDTO.designReflect.equals("")) {
		    ketDqmDTO.designReflect += ",";
		}
		if (!ketDqmDTO.designReflectName.equals("")) {
		    ketDqmDTO.designReflectName += "/";
		}
		ketDqmDTO.designReflectName += CodeHelper.manager.getCodeValue("DESIGNREFLECT", ketDqmActionDesignReflect.getCode());
		ketDqmDTO.designReflect += ketDqmActionDesignReflect.getCode();
	    }

	    ketDqmDTO.validationDate = DateUtil.getDateString(ketDqmAction.getValidationDate(), "date");
	    ketDqmDTO.problemReflectYn = ketDqmAction.getProblemReflectYn();
	    ketDqmDTO.designChangeYn = ketDqmAction.getDesignChangeYn();

	    ketDqmDTO.causeWebEditor = (String) ketDqmAction.getCauseWebEditor();
	    ketDqmDTO.causeWebEditorText = (String) ketDqmAction.getCauseWebEditorText();
	    ketDqmDTO.improveWebEditor = (String) ketDqmAction.getImproveWebEditor();
	    ketDqmDTO.improveWebEditorText = (String) ketDqmAction.getImproveWebEditorText();

	    ketDqmDTO.drawingOutDate = DateUtil.getDateString(ketDqmAction.getDrawingOutDate(), "date");
	    ketDqmDTO.moldModifyDate = DateUtil.getDateString(ketDqmAction.getMoldModifyDate(), "date");
	    ketDqmDTO.toDate = DateUtil.getDateString(ketDqmAction.getToDate(), "date");
	    ketDqmDTO.trustTestDate = DateUtil.getDateString(ketDqmAction.getTrustTestDate(), "date");

	    ketDqmDTO.duplicateYn = ketDqmAction.getDuplicateYn();
	    ketDqmDTO.duplicateReportName = ketDqmAction.getDuplicateReportName();
	    ketDqmDTO.duplicateReportCode = ketDqmAction.getDuplicateReportCode();
	    ketDqmDTO.actionCreateStamp = DateUtil.getDateString(ketDqmAction.getReviewDate(), "date");

	    ketDqmDTO.execEndDate = DateUtil.getDateString(ketDqmAction.getExecEndDate(), "date");

	    this.mainSubject = StringUtil.checkNull(ketDqmAction.getMainSubject()); // 요청사항
	    this.mainSubjectHtml = this.mainSubject.replaceAll("\r\n", "<br/>"); // 요청사항

	    if (ketDqmAction.getUser() != null && !ketDqmAction.getUser().equals(null)) {
		PeopleData peopleData = new PeopleData(ketDqmAction.getUser());
		ketDqmDTO.actionUserName = peopleData.name;
		ketDqmDTO.actionUserOid = CommonUtil.getOIDString(ketDqmAction.getUser());
		if (ketDqmAction.getUser().equals((WTUser) SessionHelper.manager.getPrincipal())) {
		    ketDqmDTO.actionUserFlag = true;
		} else {
		    ketDqmDTO.actionUserFlag = false;
		}
	    } else {
		ketDqmDTO.actionUserFlag = false;
	    }

	    KETEcoDqmLink ketEcoDqmLink = null;
	    KETProdChangeOrder eco = null;
	    QueryResult qr = PersistenceHelper.manager.navigate(ketDqmAction, "eco", KETEcoDqmLink.class, false);
	    while (qr.hasMoreElements()) {
		ketEcoDqmLink = (KETEcoDqmLink) qr.nextElement();
		eco = (KETProdChangeOrder) ketEcoDqmLink.getEco();
		/*
	         * if (!StringUtil.checkNull(relatedEcrNo).equals(null) && !StringUtil.checkNull(relatedEcrNo).equals("")) {
	         * ketDqmDTO.relatedEcrNo += ","; ketDqmDTO.relatedEcrOid += ","; } else { ketDqmDTO.relatedEcrNo = "";
	         * ketDqmDTO.relatedEcrOid = ""; } ketDqmDTO.relatedEcrNo += ecr.getEcrId(); ketDqmDTO.relatedEcrOid +=
	         * CommonUtil.getOIDString(ecr);
	         */
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("relatedEcrNo", eco.getEcoId().replace("ECO-", ""));
		map.put("relatedEcrOid", CommonUtil.getOIDString(eco));
		relatedEcrInfoList.add(map);
	    }

	    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(ketDqmDTO.actionOid));
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    // out.println(master.getPbo().toString()); 결재객체oid확인
	    if (master != null) {
		QuerySpec query = new QuerySpec();
		SearchCondition sc = null;

		int idxApprovalHistory = query.appendClassList(KETWfmApprovalHistory.class, true);

		sc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(master));
		query.appendWhere(sc, new int[] { idxApprovalHistory });

		SearchUtil
		        .setOrderBy(query, KETWfmApprovalHistory.class, idxApprovalHistory, "thePersistInfo.theObjectIdentifier.id", true);

		qr = PersistenceHelper.manager.find(query);
		Timestamp compareApprovDate = null;
		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];

		    String activityName = "&nbsp;";

		    activityName = StringUtil.checkNull(history.getActivityName());

		    if (activityName.equals("검토") && history.isLast()) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인")) {
			    if (history.getCompletedDate() != null) {
				if (compareApprovDate == null) {
				    compareApprovDate = history.getCompletedDate();
				    ketDqmDTO.actionApprovDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.actionApprovDept = peopleData.departmentName;
				    ketDqmDTO.actionApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				} else if (compareApprovDate.before(history.getCompletedDate())) {
				    ketDqmDTO.actionApprovDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.actionApprovDept = peopleData.departmentName;
				    ketDqmDTO.actionApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				    compareApprovDate = history.getCompletedDate();
				}
			    } else {
				ketDqmDTO.actionApprovDate = "";
				PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				ketDqmDTO.actionApprovDept = peopleData.departmentName;
				ketDqmDTO.actionApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
			    }
			}
		    }
		}
	    }

	}
	return ketDqmDTO;
    }

    public KETDqmDTO KETDqmDTOPrint(KETDqmHeader ketDqmHeader, KETDqmAction ketDqmAction, KETDqmRaise ketDqmRaise, KETDqmDTO ketDqmDTO)
	    throws Exception {
	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    ketDqmDTO.setOid(CommonUtil.getOIDString(ketDqmHeader));
	    ketDqmDTO.problem = ketDqmHeader.getProblem();
	    ketDqmDTO.problemNo = ketDqmHeader.getProblemNo();
	    ketDqmDTO.dqmStateCode = ketDqmHeader.getDqmStateCode();
	    ketDqmDTO.dqmStateName = ketDqmHeader.getDqmStateName();

	    ketDqmDTO.setRaiseOid(CommonUtil.getOIDString(ketDqmRaise));
	    ketDqmDTO.customerCode = ketDqmRaise.getCustomerCode();
	    ketDqmDTO.cartypeCode = ketDqmRaise.getCartypeCode();
	    ketDqmDTO.problemTypeCode = ketDqmRaise.getProblemTypeCode();
	    ketDqmDTO.urgencyCode = ketDqmRaise.getUrgencyCode();
	    ketDqmDTO.partCategoryCode = ketDqmRaise.getPartCategoryCode();

	    ketDqmDTO.customerName = ketDqmRaise.getCustomerName();
	    ketDqmDTO.cartypeName = ketDqmRaise.getCartypeName();
	    ketDqmDTO.problemTypeName = ketDqmRaise.getProblemTypeName();
	    ketDqmDTO.urgencyName = ketDqmRaise.getUrgencyName();
	    ketDqmDTO.partCategoryName = ketDqmRaise.getPartCategoryName();

	    // ketDqmDTO.relatedPart = ketDqmRaise.getRelatedPart();
	    ketDqmDTO.occurDivCode = ketDqmRaise.getOccurDivCode();
	    ketDqmDTO.occurStepCode = ketDqmRaise.getOccurStepCode();
	    ketDqmDTO.occurCode = ketDqmRaise.getOccurCode();

	    ketDqmDTO.occurDivName = ketDqmRaise.getOccurDivName();
	    ketDqmDTO.occurPlaceName = ketDqmRaise.getOccurPlaceName();
	    ketDqmDTO.occurStepName = ketDqmRaise.getOccurStepName();
	    ketDqmDTO.occurName = ketDqmRaise.getOccurName();

	    ketDqmDTO.occurDate = DateUtil.getDateString(ketDqmRaise.getOccurDate(), "date");
	    ketDqmDTO.actionDeptCode = ketDqmRaise.getActionDeptCode();

	    ketDqmDTO.actionDeptName = ketDqmRaise.getActionDeptName();

	    ketDqmDTO.requestDate = DateUtil.getDateString(ketDqmRaise.getRequestDate(), "date");
	    ketDqmDTO.webEditor = (String) ketDqmRaise.getWebEditor();
	    ketDqmDTO.webEditorText = (String) ketDqmRaise.getWebEditorText();

	    ketDqmDTO.raiseCreateStamp = DateUtil.getDateString(ketDqmRaise.getCreateTimestamp(), "date");

	    ketDqmDTO.defectDivName = ketDqmRaise.getDefectDivName();
	    ketDqmDTO.defectDivCode = ketDqmRaise.getDefectDivCode();
	    ketDqmDTO.defectTypeName = ketDqmRaise.getDefectTypeName();
	    ketDqmDTO.defectTypeCode = ketDqmRaise.getDefectTypeCode();
	    ketDqmDTO.applyArea1 = ketDqmRaise.getApplyArea1();
	    ketDqmDTO.applyArea2 = ketDqmRaise.getApplyArea2();
	    ketDqmDTO.applyArea3 = ketDqmRaise.getApplyArea3();
	    ProductProject productProject = ketDqmRaise.getProductProject();
	    if (productProject != null && !productProject.equals(null)) {
		E3PSProject project = (E3PSProject) CommonUtil.getObject(CommonUtil.getOIDString(productProject));
		E3PSProjectData projectData;
		try {
		    projectData = new E3PSProjectData(project);
		    ketDqmDTO.pjtno = projectData.pjtNo;
		    ketDqmDTO.pjtoid = CommonUtil.getOIDString(project);
		    ketDqmDTO.pjtname = projectData.pjtName;
		    // ketDqmDTO.pmUserName = projectData.pjtPmName;
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
	    }
	    WTPart wtPart = ketDqmRaise.getPart();
	    if (wtPart != null && !wtPart.equals(null)) {
		ketDqmDTO.relatedPart = wtPart.getNumber();
		ketDqmDTO.relatedPartOid = CommonUtil.getOIDString(wtPart);
		// TODO
		// ketDqmDTO.series = wtPart.getSeries();
	    }

	    // 시리즈 가져오기
	    ketDqmDTO.seriesName = "";
	    ketDqmDTO.series = "";

	    QueryResult qrSeries = PersistenceHelper.manager.navigate(ketDqmRaise, "dqmRaiseSeries", KETDqmRaiseSeriesLink.class, false);
	    while (qrSeries.hasMoreElements()) {
		KETDqmRaiseSeriesLink ketDqmRaiseSeriesLink = (KETDqmRaiseSeriesLink) qrSeries.nextElement();
		KETDqmRaiseSeries ketDqmRaiseSeries = (KETDqmRaiseSeries) ketDqmRaiseSeriesLink.getDqmRaiseSeries();
		if (!ketDqmDTO.seriesName.equals("")) {
		    ketDqmDTO.seriesName += ", ";
		}
		if (!ketDqmDTO.series.equals("")) {
		    ketDqmDTO.series += ",";
		}
		ketDqmDTO.seriesName += ketDqmRaiseSeries.getName();
		ketDqmDTO.series += ketDqmRaiseSeries.getCode();
	    }

	    WTUser wtPmUser = ketDqmRaise.getPmUser();
	    if (wtPmUser != null && !wtPmUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtPmUser);
		ketDqmDTO.pmUserName = peopleData.name;
		ketDqmDTO.pmUserOid = CommonUtil.getOIDString(wtPmUser);
		if (wtPmUser.equals((WTUser) SessionHelper.manager.getPrincipal())) {
		    ketDqmDTO.pmUserFlag = true;
		} else {
		    ketDqmDTO.pmUserFlag = false;
		}
	    } else {
		ketDqmDTO.pmUserFlag = false;
	    }

	    WTUser wtRaiseUser = ketDqmRaise.getUser();
	    if (wtRaiseUser != null && !wtRaiseUser.equals(null)) {
		PeopleData peopleData = new PeopleData(wtRaiseUser);
		ketDqmDTO.raiserUserName = peopleData.name;
		ketDqmDTO.raiserUserOid = CommonUtil.getOIDString(wtRaiseUser);
	    }

	    if (ketDqmRaise.getCreator() != null && !ketDqmRaise.getCreator().equals(null)) {
		WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
		PeopleData peopleData = new PeopleData(createUser);
		ketDqmDTO.raiseCreateUserName = peopleData.name;
		ketDqmDTO.raiseCreateUserDept = peopleData.departmentName;
		if (ketDqmRaise.getCreator().equals(SessionHelper.manager.getPrincipalReference())) {
		    ketDqmDTO.createUserFlag = true;
		} else {
		    ketDqmDTO.createUserFlag = false;
		}
	    } else {
		ketDqmDTO.createUserFlag = false;
	    }

	    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(ketDqmDTO.raiseOid));
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    Object temp = new Object();
	    Vector vec = null;
	    // out.println(master.getPbo().toString()); 결재객체oid확인
	    if (master != null) {

		vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

		if (vec != null) {
		    String activityName = "&nbsp;";

		    for (int i = 0; i < vec.size(); i++) {
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);

			activityName = StringUtil.checkNull(history.getActivityName());
			if (activityName.equals("검토")) {

			    if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인") && history.isLast()) {
				if (history.getCompletedDate() != null) {
				    ketDqmDTO.raiseApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.raiseApprovDept = peopleData.departmentName;
				    ketDqmDTO.raiseApprovDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				}
			    }

			    if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인") && !history.isLast()) {
				if (history.getCompletedDate() != null) {
				    ketDqmDTO.raiseReviewName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.raiseReviewDept = peopleData.departmentName;
				    ketDqmDTO.raiseReviewDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				}
			    }
			}

		    }
		}
	    }

	}

	if (ketDqmAction != null && !ketDqmAction.equals(null)) {
	    ketDqmDTO.setActionOid(CommonUtil.getOIDString(ketDqmAction));
	    // ketDqmDTO.causeCode = ketDqmAction.getCauseCode();

	    // 원인
	    ketDqmDTO.causeCode = "";
	    ketDqmDTO.causeCodeName = "";

	    QueryResult qrCause = PersistenceHelper.manager.navigate(ketDqmAction, "dqmActionCause", KETDqmActionCauseLink.class, false);
	    while (qrCause.hasMoreElements()) {
		KETDqmActionCauseLink ketDqmActionCauseLink = (KETDqmActionCauseLink) qrCause.nextElement();
		KETDqmActionCause ketDqmActionCause = (KETDqmActionCause) ketDqmActionCauseLink.getDqmActionCause();
		if (!ketDqmDTO.causeCode.equals("")) {
		    ketDqmDTO.causeCode += ",";
		}
		if (!ketDqmDTO.causeCodeName.equals("")) {
		    ketDqmDTO.causeCodeName += "/";
		}
		ketDqmDTO.causeCodeName += CodeHelper.manager.getCodeValue("PROBLEMTEAM", ketDqmActionCause.getCode());
		ketDqmDTO.causeCode += ketDqmActionCause.getCode();
	    }

	    // 설계변경여부
	    ketDqmDTO.designReflect = "";
	    ketDqmDTO.designReflectName = "";

	    QueryResult qrDesignReflect = PersistenceHelper.manager.navigate(ketDqmAction, "dqmActionDesignReflect",
		    KETActionDesignReflectLink.class, false);
	    while (qrDesignReflect.hasMoreElements()) {
		KETActionDesignReflectLink ketActionDesignReflectLink = (KETActionDesignReflectLink) qrDesignReflect.nextElement();
		KETDqmActionDesignReflect ketDqmActionDesignReflect = (KETDqmActionDesignReflect) ketActionDesignReflectLink
		        .getDqmActionDesignReflect();
		if (!ketDqmDTO.designReflect.equals("")) {
		    ketDqmDTO.designReflect += ",";
		}
		if (!ketDqmDTO.designReflectName.equals("")) {
		    ketDqmDTO.designReflectName += "/";
		}
		ketDqmDTO.designReflectName += CodeHelper.manager.getCodeValue("DESIGNREFLECT", ketDqmActionDesignReflect.getCode());
		ketDqmDTO.designReflect += ketDqmActionDesignReflect.getCode();
	    }

	    ketDqmDTO.validationDate = DateUtil.getDateString(ketDqmAction.getValidationDate(), "date");
	    ketDqmDTO.problemReflectYn = ketDqmAction.getProblemReflectYn();

	    ketDqmDTO.causeWebEditor = (String) ketDqmAction.getCauseWebEditor();
	    ketDqmDTO.causeWebEditorText = (String) ketDqmAction.getCauseWebEditorText();
	    ketDqmDTO.improveWebEditor = (String) ketDqmAction.getImproveWebEditor();
	    ketDqmDTO.improveWebEditorText = (String) ketDqmAction.getImproveWebEditorText();

	    ketDqmDTO.drawingOutDate = DateUtil.getDateString(ketDqmAction.getDrawingOutDate(), "date");
	    ketDqmDTO.moldModifyDate = DateUtil.getDateString(ketDqmAction.getMoldModifyDate(), "date");
	    ketDqmDTO.toDate = DateUtil.getDateString(ketDqmAction.getToDate(), "date");
	    ketDqmDTO.trustTestDate = DateUtil.getDateString(ketDqmAction.getTrustTestDate(), "date");

	    ketDqmDTO.duplicateYn = ketDqmAction.getDuplicateYn();
	    ketDqmDTO.duplicateReportName = ketDqmAction.getDuplicateReportName();
	    ketDqmDTO.duplicateReportCode = ketDqmAction.getDuplicateReportCode();
	    ketDqmDTO.actionCreateStamp = DateUtil.getDateString(ketDqmAction.getReviewDate(), "date");

	    if (ketDqmAction.getUser() != null && !ketDqmAction.getUser().equals(null)) {
		PeopleData peopleData = new PeopleData(ketDqmAction.getUser());
		ketDqmDTO.actionUserName = peopleData.name;
		ketDqmDTO.actionUserDept = peopleData.departmentName;
		ketDqmDTO.actionUserOid = CommonUtil.getOIDString(ketDqmAction.getUser());
		if (ketDqmAction.getUser().equals((WTUser) SessionHelper.manager.getPrincipal())) {
		    ketDqmDTO.actionUserFlag = true;
		} else {
		    ketDqmDTO.actionUserFlag = false;
		}
	    } else {
		ketDqmDTO.actionUserFlag = false;
	    }

	    KETEcoDqmLink ketEcoDqmLink = null;
	    KETProdChangeOrder eco = null;
	    QueryResult qr = PersistenceHelper.manager.navigate(ketDqmAction, "eco", KETEcoDqmLink.class, false);
	    while (qr.hasMoreElements()) {
		ketEcoDqmLink = (KETEcoDqmLink) qr.nextElement();
		eco = (KETProdChangeOrder) ketEcoDqmLink.getEco();
		/*
	         * if (!StringUtil.checkNull(relatedEcrNo).equals(null) && !StringUtil.checkNull(relatedEcrNo).equals("")) {
	         * ketDqmDTO.relatedEcrNo += ","; ketDqmDTO.relatedEcrOid += ","; } else { ketDqmDTO.relatedEcrNo = "";
	         * ketDqmDTO.relatedEcrOid = ""; } ketDqmDTO.relatedEcrNo += ecr.getEcrId(); ketDqmDTO.relatedEcrOid +=
	         * CommonUtil.getOIDString(ecr);
	         */
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("relatedEcrNo", eco.getEcoId().replace("ECO-", ""));
		map.put("relatedEcrOid", CommonUtil.getOIDString(eco));
		relatedEcrInfoList.add(map);
	    }

	    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(ketDqmDTO.actionOid));
	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	    // out.println(master.getPbo().toString()); 결재객체oid확인
	    if (master != null) {
		QuerySpec query = new QuerySpec();
		SearchCondition sc = null;

		int idxApprovalHistory = query.appendClassList(KETWfmApprovalHistory.class, true);

		sc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(master));
		query.appendWhere(sc, new int[] { idxApprovalHistory });

		SearchUtil
		        .setOrderBy(query, KETWfmApprovalHistory.class, idxApprovalHistory, "thePersistInfo.theObjectIdentifier.id", true);

		qr = PersistenceHelper.manager.find(query);

		Timestamp compareApprovDate = null;
		Timestamp compareReviewDate = null;
		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];
		    String activityName = "&nbsp;";

		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토")) {

			if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인") && history.isLast()) {

			    if (history.getCompletedDate() != null) {
				if (compareApprovDate == null) {
				    compareApprovDate = history.getCompletedDate();
				    ketDqmDTO.actionApprovDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.actionApprovDept = peopleData.departmentName;
				    ketDqmDTO.actionApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				} else if (compareApprovDate.before(history.getCompletedDate())) {
				    ketDqmDTO.actionApprovDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.actionApprovDept = peopleData.departmentName;
				    ketDqmDTO.actionApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				    compareApprovDate = history.getCompletedDate();
				}
			    } else {
				ketDqmDTO.actionApprovDate = "";
				PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				ketDqmDTO.actionApprovDept = peopleData.departmentName;
				ketDqmDTO.actionApprovName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
			    }

			}

			if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인") && !history.isLast()) {

			    if (history.getCompletedDate() != null) {
				if (compareReviewDate == null) {
				    compareReviewDate = history.getCompletedDate();
				    ketDqmDTO.actionReviewDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.actionReviewDept = peopleData.departmentName;
				    ketDqmDTO.actionReviewName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				} else if (compareReviewDate.before(history.getCompletedDate())) {
				    compareReviewDate = history.getCompletedDate();
				    ketDqmDTO.actionReviewDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
				    PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				    ketDqmDTO.actionReviewDept = peopleData.departmentName;
				    ketDqmDTO.actionReviewName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
				}
			    } else {
				ketDqmDTO.actionReviewDate = "";
				PeopleData peopleData = new PeopleData((WTUser) history.getOwner().getPrincipal());
				ketDqmDTO.actionReviewDept = peopleData.departmentName;
				ketDqmDTO.actionReviewName = ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;");
			    }

			}
		    }
		}
	    }

	}
	return ketDqmDTO;
    }

    public KETDqmDTO(KETDqmRaise ketDqmRaise) {
	this.setOid(CommonUtil.getOIDString(ketDqmRaise));
	this.pjtno = ketDqmRaise.getPjtno();
	this.pjtname = ketDqmRaise.getPjtname();
	this.customerCode = ketDqmRaise.getCustomerCode();
	this.cartypeCode = ketDqmRaise.getCartypeCode();
	this.problemTypeCode = ketDqmRaise.getProblemTypeCode();
	this.urgencyCode = ketDqmRaise.getUrgencyCode();
	this.partCategoryCode = ketDqmRaise.getPartCategoryCode();

	this.customerName = ketDqmRaise.getCustomerName();
	this.cartypeName = ketDqmRaise.getCartypeName();
	this.problemTypeName = ketDqmRaise.getProblemTypeName();
	this.urgencyName = ketDqmRaise.getUrgencyName();
	this.partCategoryName = ketDqmRaise.getPartCategoryName();

	// this.relatedPart = ketDqmRaise.getRelatedPart();
	this.occurDivCode = ketDqmRaise.getOccurDivCode();
	this.occurPlaceCode = ketDqmRaise.getOccurPlaceCode();
	this.occurStepCode = ketDqmRaise.getOccurStepCode();
	this.occurCode = ketDqmRaise.getOccurCode();

	this.occurDivName = ketDqmRaise.getOccurDivName();
	this.occurPlaceName = ketDqmRaise.getOccurPlaceName();
	this.occurStepName = ketDqmRaise.getOccurStepName();
	this.occurName = ketDqmRaise.getOccurName();

	this.occurDate = DateUtil.getDateString(ketDqmRaise.getOccurDate(), "date");
	this.actionDeptCode = ketDqmRaise.getActionDeptCode();

	this.actionDeptName = ketDqmRaise.getActionDeptName();

	this.requestDate = DateUtil.getDateString(ketDqmRaise.getRequestDate(), "date");
	this.webEditor = (String) ketDqmRaise.getWebEditor();
	this.webEditorText = (String) ketDqmRaise.getWebEditorText();

	this.defectDivName = ketDqmRaise.getDefectDivName();
	this.defectDivCode = ketDqmRaise.getDefectDivCode();
	this.defectTypeName = ketDqmRaise.getDefectTypeName();
	this.defectTypeCode = ketDqmRaise.getDefectTypeCode();
	this.applyArea1 = ketDqmRaise.getApplyArea1();
	this.applyArea2 = ketDqmRaise.getApplyArea2();
	this.applyArea3 = ketDqmRaise.getApplyArea3();
	/*
	 * try { this.iterationCode = pastProblemManagement.getIterationInfo().getCodeentifier().getSeries().getValue(); } catch
	 * (VersionControlException e) { // Auto-generated catch block this.iterationCode = "0"; Kogger.error(getClass(), e); }
	 */
    }

    public String getActionUserDept() {
	return actionUserDept;
    }

    public void setActionUserDept(String actionUserDept) {
	this.actionUserDept = actionUserDept;
    }

    public String getRaiseApprovDept() {
	return raiseApprovDept;
    }

    public void setRaiseApprovDept(String raiseApprovDept) {
	this.raiseApprovDept = raiseApprovDept;
    }

    public String getRaiseReviewName() {
	return raiseReviewName;
    }

    public void setRaiseReviewName(String raiseReviewName) {
	this.raiseReviewName = raiseReviewName;
    }

    public String getRaiseReviewDate() {
	return raiseReviewDate;
    }

    public void setRaiseReviewDate(String raiseReviewDate) {
	this.raiseReviewDate = raiseReviewDate;
    }

    public String getRaiseReviewDept() {
	return raiseReviewDept;
    }

    public void setRaiseReviewDept(String raiseReviewDept) {
	this.raiseReviewDept = raiseReviewDept;
    }

    public String getRaiseCreateUserDept() {
	return raiseCreateUserDept;
    }

    public void setRaiseCreateUserDept(String raiseCreateUserDept) {
	this.raiseCreateUserDept = raiseCreateUserDept;
    }

    public String getActionReviewName() {
	return actionReviewName;
    }

    public void setActionReviewName(String actionReviewName) {
	this.actionReviewName = actionReviewName;
    }

    public String getActionReviewDate() {
	return actionReviewDate;
    }

    public void setActionReviewDate(String actionReviewDate) {
	this.actionReviewDate = actionReviewDate;
    }

    public String getActionReviewDept() {
	return actionReviewDept;
    }

    public void setActionReviewDept(String actionReviewDept) {
	this.actionReviewDept = actionReviewDept;
    }

    public String getActionApprovDept() {
	return actionApprovDept;
    }

    public void setActionApprovDept(String actionApprovDept) {
	this.actionApprovDept = actionApprovDept;
    }

    public String getOutputOid() {
	return outputOid;
    }

    public void setOutputOid(String outputOid) {
	this.outputOid = outputOid;
    }

    public String getValidationCheck() {
	return validationCheck;
    }

    public void setValidationCheck(String validationCheck) {
	this.validationCheck = validationCheck;
    }

    public String getDesignReflect() {
	return designReflect;
    }

    public void setDesignReflect(String designReflect) {
	this.designReflect = designReflect;
    }

    public String getDesignReflectName() {
	return designReflectName;
    }

    public void setDesignReflectName(String designReflectName) {
	this.designReflectName = designReflectName;
    }

    public String getCauseCodeName() {
	return causeCodeName;
    }

    public void setCauseCodeName(String causeCodeName) {
	this.causeCodeName = causeCodeName;
    }

    public String getSeriesName() {
	return seriesName;
    }

    public void setSeriesName(String seriesName) {
	this.seriesName = seriesName;
    }

    public String getProblemReflectYn() {
	return problemReflectYn;
    }

    public void setProblemReflectYn(String problemReflectYn) {
	this.problemReflectYn = problemReflectYn;
    }

    public String getValidationDate() {
	return validationDate;
    }

    public void setValidationDate(String validationDate) {
	this.validationDate = validationDate;
    }

    public String getMode() {
	return mode;
    }

    public void setMode(String mode) {
	this.mode = mode;
    }

    public String getCreateStamp() {
	return createStamp;
    }

    public void setCreateStamp(String createStamp) {
	this.createStamp = createStamp;
    }

    public String getCreateStartDate() {
	return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
	this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
	return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
	this.createEndDate = createEndDate;
    }

    public String getCompStartDate() {
	return compStartDate;
    }

    public void setCompStartDate(String compStartDate) {
	this.compStartDate = compStartDate;
    }

    public String getCompEndDate() {
	return compEndDate;
    }

    public void setCompEndDate(String compEndDate) {
	this.compEndDate = compEndDate;
    }

    public String getActionApprovName() {
	return actionApprovName;
    }

    public void setActionApprovName(String actionApprovName) {
	this.actionApprovName = actionApprovName;
    }

    public String getActionApprovDate() {
	return actionApprovDate;
    }

    public void setActionApprovDate(String actionApprovDate) {
	this.actionApprovDate = actionApprovDate;
    }

    public String getRaiseCreateUserName() {
	return raiseCreateUserName;
    }

    public void setRaiseCreateUserName(String raiseCreateUserName) {
	this.raiseCreateUserName = raiseCreateUserName;
    }

    public String getActionCreateStamp() {
	return actionCreateStamp;
    }

    public void setActionCreateStamp(String actionCreateStamp) {
	this.actionCreateStamp = actionCreateStamp;
    }

    public String getRaiseCreateStamp() {
	return raiseCreateStamp;
    }

    public void setRaiseCreateStamp(String raiseCreateStamp) {
	this.raiseCreateStamp = raiseCreateStamp;
    }

    public String getRaiseApprovName() {
	return raiseApprovName;
    }

    public void setRaiseApprovName(String raiseApprovName) {
	this.raiseApprovName = raiseApprovName;
    }

    public String getRaiseApprovDate() {
	return raiseApprovDate;
    }

    public void setRaiseApprovDate(String raiseApprovDate) {
	this.raiseApprovDate = raiseApprovDate;
    }

    public List<HashMap<String, String>> getRelatedEcrInfoList() {
	return relatedEcrInfoList;
    }

    public void setRelatedEcrInfoList(List<HashMap<String, String>> relatedEcrInfoList) {
	this.relatedEcrInfoList = relatedEcrInfoList;
    }

    public String getRelatedEcrOid() {
	return relatedEcrOid;
    }

    public void setRelatedEcrOid(String relatedEcrOid) {
	this.relatedEcrOid = relatedEcrOid;
    }

    public String getRelatedEcrNo() {
	return relatedEcrNo;
    }

    public void setRelatedEcrNo(String relatedEcrNo) {
	this.relatedEcrNo = relatedEcrNo;
    }

    public String getDuplicateYn() {
	return duplicateYn;
    }

    public void setDuplicateYn(String duplicateYn) {
	this.duplicateYn = duplicateYn;
    }

    public String getDuplicateReportName() {
	return duplicateReportName;
    }

    public void setDuplicateReportName(String duplicateReportName) {
	this.duplicateReportName = duplicateReportName;
    }

    public String getDuplicateReportCode() {
	return duplicateReportCode;
    }

    public void setDuplicateReportCode(String duplicateReportCode) {
	this.duplicateReportCode = duplicateReportCode;
    }

    public String getDrawingOutDate() {
	return drawingOutDate;
    }

    public void setDrawingOutDate(String drawingOutDate) {
	this.drawingOutDate = drawingOutDate;
    }

    public String getMoldModifyDate() {
	return moldModifyDate;
    }

    public void setMoldModifyDate(String moldModifyDate) {
	this.moldModifyDate = moldModifyDate;
    }

    public String getToDate() {
	return toDate;
    }

    public void setToDate(String toDate) {
	this.toDate = toDate;
    }

    public String getTrustTestDate() {
	return trustTestDate;
    }

    public void setTrustTestDate(String trustTestDate) {
	this.trustTestDate = trustTestDate;
    }

    public String getActionUserName() {
	return actionUserName;
    }

    public void setActionUserName(String actionUserName) {
	this.actionUserName = actionUserName;
    }

    public String getActionUserOid() {
	return actionUserOid;
    }

    public void setActionUserOid(String actionUserOid) {
	this.actionUserOid = actionUserOid;
    }

    public String getReviewWebEditor() {
	return reviewWebEditor;
    }

    public void setReviewWebEditor(String reviewWebEditor) {
	this.reviewWebEditor = reviewWebEditor;
    }

    public String getReviewWebEditorText() {
	return reviewWebEditorText;
    }

    public void setReviewWebEditorText(String reviewWebEditorText) {
	this.reviewWebEditorText = reviewWebEditorText;
    }

    public boolean isCreateUserFlag() {
	return createUserFlag;
    }

    public void setCreateUserFlag(boolean createUserFlag) {
	this.createUserFlag = createUserFlag;
    }

    public boolean isActionUserFlag() {
	return actionUserFlag;
    }

    public void setActionUserFlag(boolean actionUserFlag) {
	this.actionUserFlag = actionUserFlag;
    }

    public boolean isPmUserFlag() {
	return pmUserFlag;
    }

    public void setPmUserFlag(boolean pmUserFlag) {
	this.pmUserFlag = pmUserFlag;
    }

    public String getRaiserUserName() {
	return raiserUserName;
    }

    public void setRaiserUserName(String raiserUserName) {
	this.raiserUserName = raiserUserName;
    }

    public String getRaiserUserOid() {
	return raiserUserOid;
    }

    public void setRaiserUserOid(String raiserUserOid) {
	this.raiserUserOid = raiserUserOid;
    }

    public String getPmUserOid() {
	return pmUserOid;
    }

    public void setPmUserOid(String pmUserOid) {
	this.pmUserOid = pmUserOid;
    }

    public String getSeries() {
	return series;
    }

    public void setSeries(String series) {
	this.series = series;
    }

    public String getRelatedPartOid() {
	return relatedPartOid;
    }

    public void setRelatedPartOid(String relatedPartOid) {
	this.relatedPartOid = relatedPartOid;
    }

    public String getPmUserName() {
	return pmUserName;
    }

    public void setPmUserName(String pmUserName) {
	this.pmUserName = pmUserName;
    }

    public String getPjtoid() {
	return pjtoid;
    }

    public void setPjtoid(String pjtoid) {
	this.pjtoid = pjtoid;
    }

    public String getDqmStateNameHtmlPrefix() {
	if ("지연".equals(this.dqmStateFlag)) {
	    return "<font color=red>";
	} else {
	    return "";
	}
    }

    public String getDqmStateNameHtmlPostfix() {
	if ("지연".equals(this.dqmStateFlag)) {
	    return "</font>";
	} else {
	    return "";
	}
    }

    public String getDqmStateName() {
	return dqmStateName;
    }

    public void setDqmStateName(String dqmStateName) {
	this.dqmStateName = dqmStateName;
    }

    public String getDqmStateCode() {
	return dqmStateCode;
    }

    public void setDqmStateCode(String dqmStateCode) {
	this.dqmStateCode = dqmStateCode;
    }

    public String getDefectDivCode() {
	return defectDivCode;
    }

    public void setDefectDivCode(String defectDivCode) {
	this.defectDivCode = defectDivCode;
    }

    public String getDefectTypeCode() {
	return defectTypeCode;
    }

    public void setDefectTypeCode(String defectTypeCode) {
	this.defectTypeCode = defectTypeCode;
    }

    public String getDefectDivName() {
	return defectDivName;
    }

    public void setDefectDivName(String defectDivName) {
	this.defectDivName = defectDivName;
    }

    public String getDefectTypeName() {
	return defectTypeName;
    }

    public void setDefectTypeName(String defectTypeName) {
	this.defectTypeName = defectTypeName;
    }

    public String getApplyArea1() {
	return applyArea1;
    }

    public void setApplyArea1(String applyArea1) {
	this.applyArea1 = applyArea1;
    }

    public String getApplyArea2() {
	return applyArea2;
    }

    public void setApplyArea2(String applyArea2) {
	this.applyArea2 = applyArea2;
    }

    public String getApplyArea3() {
	return applyArea3;
    }

    public void setApplyArea3(String applyArea3) {
	this.applyArea3 = applyArea3;
    }

    public String getOccurPlaceName() {
	return occurPlaceName;
    }

    public void setOccurPlaceName(String occurPlaceName) {
	this.occurPlaceName = occurPlaceName;
    }

    public String getOccurPlaceCode() {
	return occurPlaceCode;
    }

    public void setOccurPlaceCode(String occurPlaceCode) {
	this.occurPlaceCode = occurPlaceCode;
    }

    public String getCauseCode() {
	return causeCode;
    }

    public void setCauseCode(String causeCode) {
	this.causeCode = causeCode;
    }

    public String getReviewer() {
	return reviewer;
    }

    public void setReviewer(String reviewer) {
	this.reviewer = reviewer;
    }

    public String getReviewRsltCode() {
	return reviewRsltCode;
    }

    public void setReviewRsltCode(String reviewRsltCode) {
	this.reviewRsltCode = reviewRsltCode;
    }

    public String getApplyExpectDate() {
	return applyExpectDate;
    }

    public void setApplyExpectDate(String applyExpectDate) {
	this.applyExpectDate = applyExpectDate;
    }

    public String getReviewCheckCode() {
	return reviewCheckCode;
    }

    public void setReviewCheckCode(String reviewCheckCode) {
	this.reviewCheckCode = reviewCheckCode;
    }

    public String getReviewDate() {
	return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
	this.reviewDate = reviewDate;
    }

    public String getCloseOid() {
	return closeOid;
    }

    public void setCloseOid(String closeOid) {
	this.closeOid = closeOid;
    }

    public String getCauseWebEditor() {
	return causeWebEditor;
    }

    public void setCauseWebEditor(String causeWebEditor) {
	this.causeWebEditor = causeWebEditor;
    }

    public String getCauseWebEditorText() {
	return causeWebEditorText;
    }

    public void setCauseWebEditorText(String causeWebEditorText) {
	this.causeWebEditorText = causeWebEditorText;
    }

    public String getImproveWebEditor() {
	return improveWebEditor;
    }

    public void setImproveWebEditor(String improveWebEditor) {
	this.improveWebEditor = improveWebEditor;
    }

    public String getImproveWebEditorText() {
	return improveWebEditorText;
    }

    public void setImproveWebEditorText(String improveWebEditorText) {
	this.improveWebEditorText = improveWebEditorText;
    }

    public String getActionOid() {
	return actionOid;
    }

    public void setActionOid(String actionOid) {
	this.actionOid = actionOid;
    }

    public String getRaiseOid() {
	return raiseOid;
    }

    public void setRaiseOid(String raiseOid) {
	this.raiseOid = raiseOid;
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public String getCartypeName() {
	return cartypeName;
    }

    public void setCartypeName(String cartypeName) {
	this.cartypeName = cartypeName;
    }

    public String getProblemTypeName() {
	return problemTypeName;
    }

    public void setProblemTypeName(String problemTypeName) {
	this.problemTypeName = problemTypeName;
    }

    public String getUrgencyName() {
	return urgencyName;
    }

    public void setUrgencyName(String urgencyName) {
	this.urgencyName = urgencyName;
    }

    public String getPartCategoryName() {
	return partCategoryName;
    }

    public void setPartCategoryName(String partCategoryName) {
	this.partCategoryName = partCategoryName;
    }

    public String getOccurDivName() {
	return occurDivName;
    }

    public void setOccurDivName(String occurDivName) {
	this.occurDivName = occurDivName;
    }

    public String getOccurStepName() {
	return occurStepName;
    }

    public void setOccurStepName(String occurStepName) {
	this.occurStepName = occurStepName;
    }

    public String getOccurName() {
	return occurName;
    }

    public void setOccurName(String occurName) {
	this.occurName = occurName;
    }

    public String getActionDeptName() {
	return actionDeptName;
    }

    public void setActionDeptName(String actionDeptName) {
	this.actionDeptName = actionDeptName;
    }

    public String getProblemNo() {
	return problemNo;
    }

    public void setProblemNo(String problemNo) {
	this.problemNo = problemNo;
    }

    public String getProblem() {
	return problem;
    }

    public void setProblem(String problem) {
	this.problem = problem;
    }

    public String getCodea2a2() {
	return ida2a2;
    }

    public void setCodea2a2(String ida2a2) {
	this.ida2a2 = ida2a2;
    }

    public String getPjtno() {
	return pjtno;
    }

    public void setPjtno(String pjtno) {
	this.pjtno = pjtno;
    }

    public String getPjtname() {
	return pjtname;
    }

    public void setPjtname(String pjtname) {
	this.pjtname = pjtname;
    }

    public String getCustomerCode() {
	return customerCode;
    }

    public void setCustomerCode(String customerCode) {
	this.customerCode = customerCode;
    }

    public String getCartypeCode() {
	return cartypeCode;
    }

    public void setCartypeCode(String cartypeCode) {
	this.cartypeCode = cartypeCode;
    }

    public String getProblemTypeCode() {
	return problemTypeCode;
    }

    public void setProblemTypeCode(String problemTypeCode) {
	this.problemTypeCode = problemTypeCode;
    }

    public String getUrgencyCode() {
	return urgencyCode;
    }

    public void setUrgencyCode(String urgencyCode) {
	this.urgencyCode = urgencyCode;
    }

    public String getPartCategoryCode() {
	return partCategoryCode;
    }

    public void setPartCategoryCode(String partCategoryCode) {
	this.partCategoryCode = partCategoryCode;
    }

    public String getRelatedPart() {
	return relatedPart;
    }

    public void setRelatedPart(String relatedPart) {
	this.relatedPart = relatedPart;
    }

    public String getOccurDivCode() {
	return occurDivCode;
    }

    public void setOccurDivCode(String occurDivCode) {
	this.occurDivCode = occurDivCode;
    }

    public String getOccurStepCode() {
	return occurStepCode;
    }

    public void setOccurStepCode(String occurStepCode) {
	this.occurStepCode = occurStepCode;
    }

    public String getOccurCode() {
	return occurCode;
    }

    public void setOccurCode(String occurCode) {
	this.occurCode = occurCode;
    }

    public String getOccurDate() {
	return occurDate;
    }

    public void setOccurDate(String occurDate) {
	this.occurDate = occurDate;
    }

    public String getActionDeptCode() {
	return actionDeptCode;
    }

    public void setActionDeptCode(String actionDeptCode) {
	this.actionDeptCode = actionDeptCode;
    }

    public String getRequestDate() {
	return requestDate;
    }

    public void setRequestDate(String requestDate) {
	this.requestDate = requestDate;
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

    public String getProblemHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getProblemHtmlPostfix() {
	return "</font>";
    }

    public String getOutputCheck() {
	return outputCheck;
    }

    public void setOutputCheck(String outputCheck) {
	this.outputCheck = outputCheck;
    }

    public String getDqmLinkOid() {
	return dqmLinkOid;
    }

    public void setDqmLinkOid(String dqmLinkOid) {
	this.dqmLinkOid = dqmLinkOid;
    }

    public String getDesignChangeYn() {
	return designChangeYn;
    }

    public void setDesignChangeYn(String designChangeYn) {
	this.designChangeYn = designChangeYn;
    }

    public String getCloserName() {
	return closerName;
    }

    public void setCloserName(String closerName) {
	this.closerName = closerName;
    }

    public String getCloserOid() {
	return closerOid;
    }

    public void setCloserOid(String closerOid) {
	this.closerOid = closerOid;
    }

    public boolean isCloseUserFlag() {
	return closeUserFlag;
    }

    public void setCloseUserFlag(boolean closeUserFlag) {
	this.closeUserFlag = closeUserFlag;
    }

    public String getImportanceName() {
	return importanceName;
    }

    public void setImportanceName(String importanceName) {
	this.importanceName = importanceName;
    }

    public String getImportanceCode() {
	return importanceCode;
    }

    public void setImportanceCode(String importanceCode) {
	this.importanceCode = importanceCode;
    }

    public String getIssueCode() {
	return issueCode;
    }

    public void setIssueCode(String issueCode) {
	this.issueCode = issueCode;
    }

    public String getIssueName() {
	return issueName;
    }

    public void setIssueName(String issueName) {
	this.issueName = issueName;
    }

    public String getOccurPointCode() {
	return occurPointCode;
    }

    public void setOccurPointCode(String occurPointCode) {
	this.occurPointCode = occurPointCode;
    }

    public String getOccurPointName() {
	return occurPointName;
    }

    public void setOccurPointName(String occurPointName) {
	this.occurPointName = occurPointName;
    }

    public String getMainSubject() {
	return mainSubject;
    }

    public void setMainSubject(String mainSubject) {
	this.mainSubject = mainSubject;
    }

    public String getExecEndDate() {
	return execEndDate;
    }

    public void setExecEndDate(String execEndDate) {
	this.execEndDate = execEndDate;
    }

    public String getMainSubjectHtml() {
	return mainSubjectHtml;
    }

    public void setMainSubjectHtml(String mainSubjectHtml) {
	this.mainSubjectHtml = mainSubjectHtml;
    }

    public String getByProjectDqm() {
	return byProjectDqm;
    }

    public void setByProjectDqm(String byProjectDqm) {
	this.byProjectDqm = byProjectDqm;
    }

    public String getDqmStateFlag() {
	return dqmStateFlag;
    }

    public void setDqmStateFlag(String dqmStateFlag) {
	this.dqmStateFlag = dqmStateFlag;
    }

}
