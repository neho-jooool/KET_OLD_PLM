package ext.ket.sample.entity;

import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.dto.BaseDTO;

/**
 * 
 * 
 * @클래스명 : SampleRequestDTO
 * @작성자 : KKW
 * @작성일 : 2014. 9. 12.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
/**
 * 
 * @클래스명 : SampleRequestDTO
 * @작성자 : KKW
 * @작성일 : 2014. 9. 18.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
/**
 * 
 * @클래스명 : SampleRequestDTO
 * @작성자 : KKW
 * @작성일 : 2014. 9. 23.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class SampleRequestDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    public int rnum;
    private String samplePartOidArr;
    private String samplePartOid;
    private String approvDate;
    private String approvUserName;
    private String approvUserOid;
    private String viewType;
    private boolean receptionButtonFlag;

    private String checkPartOid;
    private String checkDevelCode;
    private String requestNo;
    private String requestPartName;
    private String developeStageName;
    private String developeStageCode;

    private String[] developeStageCodeArr; // 코드배열
    private String[] requstPartOidArr;
    private String sortType;
    private String pmUserName;
    private String pmUserOid;

    private String pmUserDeptName;
    private String pmUserDeptOid;

    private String dispensationDate;
    private String createUserName;
    private String createUserOid;
    private String createDate;

    private String createUserDeptName;
    private String createUserDeptOid;

    private String requstPartOid;
    private String requstPartName;

    private String requestTitle;
    private String customerName;
    private String customerCode;
    private String carTypeName;
    private String carTypeCode;
    private String customerContractor;
    private String webEditor;
    private String webEditorText;

    private String sampleRequestStateCode;
    private String sampleRequestStateName;

    private String partOidArr;
    private String partPmUserOidArr;
    private String partRequestCountArr;

    private String requestDate;
    private String purpose;

    private boolean createUserFlag;
    private boolean receptionUpdateFlag;

    private int partListSize;
    private String receptionDateArr;
    private String dispensationCountArr;
    private String dispensationExpectDateArr;
    private String dispensationDateArr;
    private String[] partCheck;
    private String partCheckToString;

    private String requestCount;
    private String partNumber;
    private String pjtoid;
    private String pjtno;

    public SampleRequestDTO() {
    }

    public SampleRequestDTO(KETSampleRequest ketSampleRequest) throws Exception {
	this.setOid(CommonUtil.getOIDString(ketSampleRequest));
	this.setRequestNo(ketSampleRequest.getRequestNo());
	this.setRequestTitle(ketSampleRequest.getRequestTitle());
	this.setCustomerName(ketSampleRequest.getCustomerName());
	this.setCustomerCode(ketSampleRequest.getCustomerCode());
	this.setCarTypeName(ketSampleRequest.getCarTypeName());
	this.setCarTypeCode(ketSampleRequest.getCarTypeCode());
	this.setCustomerContractor(ketSampleRequest.getCustomerContractor());
	// this.setWebEditor((String) ketSampleRequest.getWebEditor());
	// this.setWebEditorText((String) ketSampleRequest.getWebEditorText());

	this.setSampleRequestStateCode(ketSampleRequest.getSampleRequestStateCode());
	this.setSampleRequestStateName(ketSampleRequest.getSampleRequestStateName());

	this.setRequestDate(DateUtil.getDateString(ketSampleRequest.getRequestDate(), "date"));
	this.setPurpose(ketSampleRequest.getPurpose());

	PeopleData peopleData = new PeopleData(ketSampleRequest.getCreator().getPrincipal());
	this.createUserName = peopleData.name;

	QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

	if (qr.hasMoreElements()) {
	    while (qr.hasMoreElements()) {
		KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();

		this.requestPartName = ketSamplePart.getPart().getName() + "외 " + qr.size();
		this.developeStageName = PartUtil.getSpDevLevelText(PartSpecGetter.getPartSpec(ketSamplePart.getPart(),
		        PartSpecEnum.SpPartDevLevel)) + "외 " + qr.size();

		peopleData = new PeopleData(ketSamplePart.getUser());

		this.pmUserName = peopleData.name + "외 " + qr.size();
		this.dispensationDate = DateUtil.getDateString(ketSamplePart.getDispensationDate(), "date");

		break;
	    }
	}
    }

    public SampleRequestDTO SampleRequstList(SampleRequestDTO sampleRequestDTO, KETSampleRequest ketSampleRequest,
	    KETSamplePart ketSamplePart) throws Exception {
	sampleRequestDTO.setOid(CommonUtil.getOIDString(ketSampleRequest));
	sampleRequestDTO.setRequestNo(ketSampleRequest.getRequestNo());
	sampleRequestDTO.setPurpose(ketSampleRequest.getPurpose());
	sampleRequestDTO.setCustomerName(ketSampleRequest.getCustomerName());
	sampleRequestDTO.setCustomerContractor(ketSampleRequest.getCustomerContractor());
	sampleRequestDTO.setCarTypeName(ketSampleRequest.getCarTypeName());
	sampleRequestDTO.setDevelopeStageName(PartUtil.getSpDevLevelText(PartSpecGetter.getPartSpec(ketSamplePart.getPart(),
	        PartSpecEnum.SpPartDevLevel)));

	WTUser createUser = (WTUser) ketSampleRequest.getCreator().getPrincipal();
	PeopleData peopleData = new PeopleData(createUser);
	sampleRequestDTO.setCreateUserName(peopleData.name);

	sampleRequestDTO.setCreateDate(DateUtil.getDateString(ketSampleRequest.getCreateTimestamp(), "date"));

	peopleData = new PeopleData(ketSamplePart.getUser());
	sampleRequestDTO.setPmUserName(peopleData.name);

	sampleRequestDTO.setDispensationDate(DateUtil.getDateString(ketSamplePart.getDispensationDate(), "date"));
	sampleRequestDTO.setSampleRequestStateName(ketSampleRequest.getSampleRequestStateName());
	sampleRequestDTO.setDispensationCountArr(String.valueOf(ketSamplePart.getDispensationCount()));
	return sampleRequestDTO;
    }

    public SampleRequestDTO SampleRequestDTO(SampleRequestDTO sampleRequestDTO, KETSampleRequest ketSampleRequest) throws Exception {
	sampleRequestDTO.setOid(CommonUtil.getOIDString(ketSampleRequest));
	sampleRequestDTO.setRequestNo(ketSampleRequest.getRequestNo());
	sampleRequestDTO.setRequestTitle(ketSampleRequest.getRequestTitle());
	sampleRequestDTO.setCustomerName(ketSampleRequest.getCustomerName());
	sampleRequestDTO.setCustomerCode(ketSampleRequest.getCustomerCode());
	sampleRequestDTO.setCarTypeName(ketSampleRequest.getCarTypeName());
	sampleRequestDTO.setCarTypeCode(ketSampleRequest.getCarTypeCode());
	sampleRequestDTO.setCustomerContractor(ketSampleRequest.getCustomerContractor());
	sampleRequestDTO.setWebEditor((String) ketSampleRequest.getWebEditor());
	sampleRequestDTO.setWebEditorText((String) ketSampleRequest.getWebEditorText());

	sampleRequestDTO.setSampleRequestStateCode(ketSampleRequest.getSampleRequestStateCode());
	sampleRequestDTO.setSampleRequestStateName(ketSampleRequest.getSampleRequestStateName());

	WTUser create_User = (WTUser) ketSampleRequest.getCreator().getPrincipal();

	if (ketSampleRequest.getCreator().equals(SessionHelper.manager.getPrincipalReference())) {
	    sampleRequestDTO.setCreateUserFlag(true);
	} else {
	    sampleRequestDTO.setCreateUserFlag(false);
	}

	PeopleData peopleData = new PeopleData(create_User);

	sampleRequestDTO.setCreateUserName(peopleData.name);
	// sampleRequestDTO.setCreateUserOid(CommonUtil.getOIDString(create_User));
	sampleRequestDTO.setCreateUserDeptName(peopleData.departmentName);
	sampleRequestDTO.setCreateDate(DateUtil.getDateString(ketSampleRequest.getCreateTimestamp(), "date"));
	// sampleRequestDTO.setCreateUserOid(CommonUtil.getOIDString(create_User));

	sampleRequestDTO.setRequestDate(DateUtil.getDateString(ketSampleRequest.getRequestDate(), "date"));
	sampleRequestDTO.setPurpose(ketSampleRequest.getPurpose());

	WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(sampleRequestDTO.getOid()));
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
		    ;

		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토")) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인")) {
			    if (history.getCompletedDate() != null)
				sampleRequestDTO.setApprovDate(DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd"));
			    sampleRequestDTO.setApprovUserName(ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;"));

			}
		    }

		}
	    }
	}

	return sampleRequestDTO;
    }

    public void searchCondition() {
	if (!StringUtil.checkNull(this.pmUserName).equals("")) {
	    this.pmUserName = KETQueryUtil.getSqlQueryForMultiSearch("peo.NAME", this.pmUserName, true);
	}

	if (!StringUtil.checkNull(this.pmUserDeptName).equals("")) {
	    this.pmUserDeptName = KETQueryUtil.getSqlQueryForMultiSearch("dpart.NAME", this.pmUserDeptName, true);
	}

	if (!StringUtil.checkNull(this.createUserName).equals("")) {
	    this.createUserName = KETQueryUtil.getSqlQueryForMultiSearch("D.NAME", this.createUserName, true);
	}

	if (!StringUtil.checkNull(this.createUserDeptName).equals("")) {
	    this.createUserDeptName = KETQueryUtil.getSqlQueryForMultiSearch("E.NAME", this.createUserDeptName, true);
	}

	if (!StringUtil.checkNull(this.customerContractor).equals("")) {
	    this.customerContractor = KETQueryUtil.getSqlQueryForMultiSearch("A.CUSTOMERCONTRACTOR", this.customerContractor, true);
	}

	if (!StringUtil.checkNull(this.sampleRequestStateName).equals("")) {
	    this.sampleRequestStateName = KETQueryUtil.getSqlQueryForMultiSearch("A.SAMPLEREQUESTSTATENAME", this.sampleRequestStateName,
		    true);
	}

	if (!StringUtil.checkNull(this.carTypeName).equals("")) {
	    this.carTypeName = KETQueryUtil.getSqlQueryForMultiSearch("A.carTypeName", this.carTypeName, true);
	}

	if (!StringUtil.checkNull(this.customerName).equals("")) {
	    this.customerName = KETQueryUtil.getSqlQueryForMultiSearch("A.customerName", this.customerName, true);
	}

	if (!StringUtil.checkNull(this.requestTitle).equals("")) {
	    this.requestTitle = KETQueryUtil.getSqlQueryForMultiSearch("A.requestTitle", this.requestTitle, true);
	}

	if (!StringUtil.checkNull(this.requestNo).equals("")) {
	    this.requestNo = KETQueryUtil.getSqlQueryForMultiSearch("A.requestNo", this.requestNo, true);
	}

	if (!StringUtil.checkNull(this.requstPartName).equals("")) {
	    this.requstPartName = KETQueryUtil.getSqlQueryForMultiSearch("WTPM.WTPARTNUMBER", this.requstPartName, true);
	}

    }

    public String getPjtoid() {
	return pjtoid;
    }

    public void setPjtoid(String pjtoid) {
	this.pjtoid = pjtoid;
    }

    public String getPjtno() {
	return pjtno;
    }

    public void setPjtno(String pjtno) {
	this.pjtno = pjtno;
    }

    public String getPartCheckToString() {
	return partCheckToString;
    }

    public void setPartCheckToString(String partCheckToString) {
	this.partCheckToString = partCheckToString;
    }

    public String getRequestCount() {
	return requestCount;
    }

    public void setRequestCount(String requestCount) {
	this.requestCount = requestCount;
    }

    public String getPartNumber() {
	return partNumber;
    }

    public void setPartNumber(String partNumber) {
	this.partNumber = partNumber;
    }

    public String getSortType() {
	return sortType;
    }

    public void setSortType(String sortType) {
	this.sortType = sortType;
    }

    public int getRnum() {
	return rnum;
    }

    public void setRnum(int rnum) {
	this.rnum = rnum;
    }

    public String[] getPartCheck() {
	return partCheck;
    }

    public void setPartCheck(String[] partCheck) {
	this.partCheck = partCheck;
    }

    public boolean getReceptionButtonFlag() {
	return receptionButtonFlag;
    }

    public void setReceptionButtonFlag(boolean receptionButtonFlag) {
	this.receptionButtonFlag = receptionButtonFlag;
    }

    public String getViewType() {
	return viewType;
    }

    public void setViewType(String viewType) {
	this.viewType = viewType;
    }

    public String getSamplePartOidArr() {
	return samplePartOidArr;
    }

    public void setSamplePartOidArr(String samplePartOidArr) {
	this.samplePartOidArr = samplePartOidArr;
    }

    public String getSamplePartOid() {
	return samplePartOid;
    }

    public void setSamplePartOid(String samplePartOid) {
	this.samplePartOid = samplePartOid;
    }

    public String getReceptionDateArr() {
	return receptionDateArr;
    }

    public void setReceptionDateArr(String receptionDateArr) {
	this.receptionDateArr = receptionDateArr;
    }

    public String getDispensationCountArr() {
	return dispensationCountArr;
    }

    public void setDispensationCountArr(String dispensationCountArr) {
	this.dispensationCountArr = dispensationCountArr;
    }

    public String getDispensationExpectDateArr() {
	return dispensationExpectDateArr;
    }

    public void setDispensationExpectDateArr(String dispensationExpectDateArr) {
	this.dispensationExpectDateArr = dispensationExpectDateArr;
    }

    public String getDispensationDateArr() {
	return dispensationDateArr;
    }

    public void setDispensationDateArr(String dispensationDateArr) {
	this.dispensationDateArr = dispensationDateArr;
    }

    public int getPartListSize() {
	return partListSize;
    }

    public void setPartListSize(int partListSize) {
	this.partListSize = partListSize;
    }

    public boolean isReceptionUpdateFlag() {
	return receptionUpdateFlag;
    }

    public void setReceptionUpdateFlag(boolean receptionUpdateFlag) {
	this.receptionUpdateFlag = receptionUpdateFlag;
    }

    public boolean isCreateUserFlag() {
	return createUserFlag;
    }

    public void setCreateUserFlag(boolean createUserFlag) {
	this.createUserFlag = createUserFlag;
    }

    public String getApprovDate() {
	return approvDate;
    }

    public void setApprovDate(String approvDate) {
	this.approvDate = approvDate;
    }

    public String getApprovUserName() {
	return approvUserName;
    }

    public void setApprovUserName(String approvUserName) {
	this.approvUserName = approvUserName;
    }

    public String getApprovUserOid() {
	return approvUserOid;
    }

    public void setApprovUserOid(String approvUserOid) {
	this.approvUserOid = approvUserOid;
    }

    public String getCreateDate() {
	return createDate;
    }

    public void setCreateDate(String createDate) {
	this.createDate = createDate;
    }

    public String getCheckPartOid() {
	return checkPartOid;
    }

    public void setCheckPartOid(String checkPartOid) {
	this.checkPartOid = checkPartOid;
    }

    public String getCheckDevelCode() {
	return checkDevelCode;
    }

    public void setCheckDevelCode(String checkDevelCode) {
	this.checkDevelCode = checkDevelCode;
    }

    public String[] getDevelopeStageCodeArr() {
	return developeStageCodeArr;
    }

    public void setDevelopeStageCodeArr(String[] developeStageCodeArr) {
	this.developeStageCodeArr = developeStageCodeArr;
    }

    public String[] getRequstPartOidArr() {
	return requstPartOidArr;
    }

    public void setRequstPartOidArr(String[] requstPartOidArr) {
	this.requstPartOidArr = requstPartOidArr;
    }

    public String getDevelopeStageCode() {
	return developeStageCode;
    }

    public void setDevelopeStageCode(String developeStageCode) {
	this.developeStageCode = developeStageCode;
    }

    public String getPmUserOid() {
	return pmUserOid;
    }

    public void setPmUserOid(String pmUserOid) {
	this.pmUserOid = pmUserOid;
    }

    public String getPmUserDeptName() {
	return pmUserDeptName;
    }

    public void setPmUserDeptName(String pmUserDeptName) {
	this.pmUserDeptName = pmUserDeptName;
    }

    public String getPmUserDeptOid() {
	return pmUserDeptOid;
    }

    public void setPmUserDeptOid(String pmUserDeptOid) {
	this.pmUserDeptOid = pmUserDeptOid;
    }

    public String getCreateUserOid() {
	return createUserOid;
    }

    public void setCreateUserOid(String createUserOid) {
	this.createUserOid = createUserOid;
    }

    public String getCreateUserDeptOid() {
	return createUserDeptOid;
    }

    public void setCreateUserDeptOid(String createUserDeptOid) {
	this.createUserDeptOid = createUserDeptOid;
    }

    public String getRequstPartOid() {
	return requstPartOid;
    }

    public void setRequstPartOid(String requstPartOid) {
	this.requstPartOid = requstPartOid;
    }

    public String getRequstPartName() {
	return requstPartName;
    }

    public void setRequstPartName(String requstPartName) {
	this.requstPartName = requstPartName;
    }

    public String getRequestPartName() {
	return requestPartName;
    }

    public void setRequestPartName(String requestPartName) {
	this.requestPartName = requestPartName;
    }

    public String getDevelopeStageName() {
	return developeStageName;
    }

    public void setDevelopeStageName(String developeStageName) {
	this.developeStageName = developeStageName;
    }

    public String getPmUserName() {
	return pmUserName;
    }

    public void setPmUserName(String pmUserName) {
	this.pmUserName = pmUserName;
    }

    public String getDispensationDate() {
	return dispensationDate;
    }

    public void setDispensationDate(String dispensationDate) {
	this.dispensationDate = dispensationDate;
    }

    public String getCreateUserName() {
	return createUserName;
    }

    public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
    }

    public String getCreateUserDeptName() {
	return createUserDeptName;
    }

    public void setCreateUserDeptName(String createUserDeptName) {
	this.createUserDeptName = createUserDeptName;
    }

    public String getRequestNo() {
	return requestNo;
    }

    public void setRequestNo(String requestNo) {
	this.requestNo = requestNo;
    }

    public String getSampleRequestStateCode() {
	return sampleRequestStateCode;
    }

    public void setSampleRequestStateCode(String sampleRequestStateCode) {
	this.sampleRequestStateCode = sampleRequestStateCode;
    }

    public String getSampleRequestStateName() {
	return sampleRequestStateName;
    }

    public void setSampleRequestStateName(String sampleRequestStateName) {
	this.sampleRequestStateName = sampleRequestStateName;
    }

    public String getPartOidArr() {
	return partOidArr;
    }

    public void setPartOidArr(String partOidArr) {
	this.partOidArr = partOidArr;
    }

    public String getPartPmUserOidArr() {
	return partPmUserOidArr;
    }

    public void setPartPmUserOidArr(String partPmUserOidArr) {
	this.partPmUserOidArr = partPmUserOidArr;
    }

    public String getPartRequestCountArr() {
	return partRequestCountArr;
    }

    public void setPartRequestCountArr(String partRequestCountArr) {
	this.partRequestCountArr = partRequestCountArr;
    }

    public String getRequestTitle() {
	return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
	this.requestTitle = requestTitle;
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public String getCustomerCode() {
	return customerCode;
    }

    public void setCustomerCode(String customerCode) {
	this.customerCode = customerCode;
    }

    public String getCarTypeName() {
	return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
	this.carTypeName = carTypeName;
    }

    public String getCarTypeCode() {
	return carTypeCode;
    }

    public void setCarTypeCode(String carTypeCode) {
	this.carTypeCode = carTypeCode;
    }

    public String getCustomerContractor() {
	return customerContractor;
    }

    public void setCustomerContractor(String customerContractor) {
	this.customerContractor = customerContractor;
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

    public String getRequestDate() {
	return requestDate;
    }

    public void setRequestDate(String requestDate) {
	this.requestDate = requestDate;
    }

    public String getPurpose() {
	return purpose;
    }

    public void setPurpose(String purpose) {
	this.purpose = purpose;
    }

    public String getRequestTitleHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getRequestTitleHtmlPostfix() {
	return "</font>";
    }
}
