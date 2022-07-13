package e3ps.ecm.entity;

public class KETMoldECALinkVO extends GeneralVO {
    private static final long serialVersionUID = -7905174456768766511L;

    private String            activityType;                            //활동계획구분(1:도면, 2:BOM, 3:문서)
    private String            activityTypeName;                        //활동계획구분명(1:도면, 2:BOM, 3:문서)
    private String            relEcaOid;                               //활동계획oid
    private String            activityStatus;                          //활동계획진행상태
    private String            activityStatusName;                      //활동계획진행상태명
    private String            workerId;                                //활동계획담당자oid
    private String            workerName;                              //활동계획담당자명
    private String            completeDate;                            //활동계획완료일자

    private String            relEcaObjectLinkOid;                     //관련활동객체링크oid
    private String            relEcaObjectOid;                         //관련활동객체oid
    private String            relEcaObjectNo;                          //관련활동객체번호
    private String            relEcaObjectName;                        //관련활동객체명
    private String            relEcaObjectPreRev;                      //관련활동객체이전버전
    private String            relEcaObjectAfterRev;                    //관련활동객체이후버전
    private String            relEcaObjectPlanDate;                    //관련활동객체변경예정일
    private String            relEcaObjectActivityComment;             //관련활동객체변경내용
    private String            changeType;                              //관련활동객체변경유형
    private String            moldChangePlanOid;                       //금형변경일정OID
    private String            dieNo;                                   //금형변경일정dieno
    private String            scheduleId;                              //금형변경일정id
    private String            oldMoldChangePlanOid;                    ////금형변경일정 old oid
    private String            newMoldChangePlanOid;                    //금형변경일정 new oid
    private String            moldEcoPlanLinkOid;                      //금형변경일정링크 oid
    private String            relEcaEpmChangeYn;                       //도면변경여부

    private String            relEcaEpmDocType;                        //도면구분
    private String            cadCategory;                             // 도면유형

    private String            targetPart;                              //표준품대상부품번호

    private String            beforePartOid;                           //Before부품Oid

    private String            afterPartOid;                            //After부품Oid
    private String            ecoHeaderOid;                            //BOM Eco Header Oid
    private String            beforeQty;
    private String            afterQty;

    private String            bomChgFlag;
    private String            beforeMaterial;                          //재질
    private String            afterMaterial;

    private String            beforeHardness;                          //경도
    private String            afterHardness;
    private String            epmDocReviseYn;
    private String            epmDocCancelRevYn;

    private String            relEcaAfterEpmOid;
    // 비용확보
    private String            related_die_no;
    private String            expect_cost;


    private String            secure_budget_yn;
    // ECN
    private String            customCode;
    private String            customName;

    private String            completeRequestDate;
    private String            receiveConfirmedDate;
    private String            activityTypeDesc;
    private String            activityBackDesc;

    public String getActivityBackDesc() {
	return activityBackDesc;
    }

    public String getActivityStatus() {
	return activityStatus;
    }


    public String getActivityStatusName() {
	return activityStatusName;
    }

    public String getActivityType() {
	return activityType;
    }

    public String getActivityTypeDesc() {
	return activityTypeDesc;
    }

    public String getActivityTypeName() {
	return activityTypeName;
    }

    public String getAfterHardness() {
	return afterHardness;
    }

    public String getAfterMaterial() {
	return afterMaterial;
    }

    public String getAfterPartOid() {
	return afterPartOid;
    }

    public String getAfterQty() {
	return afterQty;
    }

    public String getBeforeHardness() {
	return beforeHardness;
    }

    public String getBeforeMaterial() {
	return beforeMaterial;
    }

    public String getBeforePartOid() {
	return beforePartOid;
    }

    public String getBeforeQty() {
	return beforeQty;
    }

    public String getBomChgFlag() {
	return bomChgFlag;
    }

    public String getCadCategory() {
	return cadCategory;
    }

    public String getChangeType() {
	return changeType;
    }

    public String getCompleteDate() {
	return completeDate;
    }

    public String getCompleteRequestDate() {
	return completeRequestDate;
    }

    public String getCustomCode() {
	return customCode;
    }

    public String getCustomName() {
	return customName;
    }

    public String getDieNo() {
	return dieNo;
    }

    public String getEcoHeaderOid() {
	return ecoHeaderOid;
    }

    public String getEpmDocCancelRevYn() {
	return epmDocCancelRevYn;
    }

    public String getEpmDocReviseYn() {
	return epmDocReviseYn;
    }

    public String getExpect_cost() {
	return expect_cost;
    }

    public String getMoldChangePlanOid() {
	return moldChangePlanOid;
    }

    public String getMoldEcoPlanLinkOid() {
	return moldEcoPlanLinkOid;
    }

    public String getNewMoldChangePlanOid() {
	return newMoldChangePlanOid;
    }

    public String getOldMoldChangePlanOid() {
	return oldMoldChangePlanOid;
    }

    public String getReceiveConfirmedDate() {
	return receiveConfirmedDate;
    }

    public String getRelated_die_no() {
	return related_die_no;
    }

    public String getRelEcaAfterEpmOid() {
	return relEcaAfterEpmOid;
    }

    public String getRelEcaEpmChangeYn() {
	return relEcaEpmChangeYn;
    }

    public String getRelEcaEpmDocType() {
	return relEcaEpmDocType;
    }

    public String getRelEcaObjectActivityComment() {
	return relEcaObjectActivityComment;
    }

    public String getRelEcaObjectAfterRev() {
	return relEcaObjectAfterRev;
    }

    public String getRelEcaObjectLinkOid() {
	return relEcaObjectLinkOid;
    }

    public String getRelEcaObjectName() {
	return relEcaObjectName;
    }

    public String getRelEcaObjectNo() {
	return relEcaObjectNo;
    }

    public String getRelEcaObjectOid() {
	return relEcaObjectOid;
    }

    public String getRelEcaObjectPlanDate() {
	return relEcaObjectPlanDate;
    }

    public String getRelEcaObjectPreRev() {
	return relEcaObjectPreRev;
    }

    public String getRelEcaOid() {
	return relEcaOid;
    }

    public String getScheduleId() {
	return scheduleId;
    }

    public String getSecure_budget_yn() {
	return secure_budget_yn;
    }

    public String getTargetPart() {
	return targetPart;
    }

    public String getWorkerId() {
	return workerId;
    }

    public String getWorkerName() {
	return workerName;
    }

    public void setActivityBackDesc(String activityBackDesc) {
	this.activityBackDesc = activityBackDesc;
    }

    public void setActivityStatus(String activityStatus) {
	this.activityStatus = activityStatus;
    }

    public void setActivityStatusName(String activityStatusName) {
	this.activityStatusName = activityStatusName;
    }

    public void setActivityType(String activityType) {
	this.activityType = activityType;
    }

    public void setActivityTypeDesc(String activityTypeDesc) {
	this.activityTypeDesc = activityTypeDesc;
    }

    public void setActivityTypeName(String activityTypeName) {
	this.activityTypeName = activityTypeName;
    }

    public void setAfterHardness(String afterHardness) {
	this.afterHardness = afterHardness;
    }

    public void setAfterMaterial(String afterMaterial) {
	this.afterMaterial = afterMaterial;
    }

    public void setAfterPartOid(String afterPartOid) {
	this.afterPartOid = afterPartOid;
    }

    public void setAfterQty(String afterQty) {
	this.afterQty = afterQty;
    }

    public void setBeforeHardness(String beforeHardness) {
	this.beforeHardness = beforeHardness;
    }

    public void setBeforeMaterial(String beforeMaterial) {
	this.beforeMaterial = beforeMaterial;
    }

    public void setBeforePartOid(String beforePartOid) {
	this.beforePartOid = beforePartOid;
    }

    public void setBeforeQty(String beforeQty) {
	this.beforeQty = beforeQty;
    }

    public void setBomChgFlag(String bomChgFlag) {
	this.bomChgFlag = bomChgFlag;
    }

    public void setCadCategory(String cadCategory) {
	this.cadCategory = cadCategory;
    }

    public void setChangeType(String changeType) {
	this.changeType = changeType;
    }

    public void setCompleteDate(String completeDate) {
	this.completeDate = completeDate;
    }

    public void setCompleteRequestDate(String completeRequestDate) {
	this.completeRequestDate = completeRequestDate;
    }

    public void setCustomCode(String customCode) {
	this.customCode = customCode;
    }

    public void setCustomName(String customName) {
	this.customName = customName;
    }

    public void setDieNo(String dieNo) {
	this.dieNo = dieNo;
    }

    public void setEcoHeaderOid(String ecoHeaderOid) {
	this.ecoHeaderOid = ecoHeaderOid;
    }

    public void setEpmDocCancelRevYn(String epmDocCancelRevYn) {
	this.epmDocCancelRevYn = epmDocCancelRevYn;
    }

    public void setEpmDocReviseYn(String epmDocReviseYn) {
	this.epmDocReviseYn = epmDocReviseYn;
    }

    public void setExpect_cost(String expect_cost) {
	this.expect_cost = expect_cost;
    }

    public void setMoldChangePlanOid(String moldChangePlanOid) {
	this.moldChangePlanOid = moldChangePlanOid;
    }

    public void setMoldEcoPlanLinkOid(String moldEcoPlanLinkOid) {
	this.moldEcoPlanLinkOid = moldEcoPlanLinkOid;
    }

    public void setNewMoldChangePlanOid(String newMoldChangePlanOid) {
	this.newMoldChangePlanOid = newMoldChangePlanOid;
    }

    public void setOldMoldChangePlanOid(String oldMoldChangePlanOid) {
	this.oldMoldChangePlanOid = oldMoldChangePlanOid;
    }

    public void setReceiveConfirmedDate(String receiveConfirmedDate) {
	this.receiveConfirmedDate = receiveConfirmedDate;
    }

    public void setRelated_die_no(String related_die_no) {
	this.related_die_no = related_die_no;
    }

    public void setRelEcaAfterEpmOid(String relEcaAfterEpmOid) {
	this.relEcaAfterEpmOid = relEcaAfterEpmOid;
    }

    public void setRelEcaEpmChangeYn(String relEcaEpmChangeYn) {
	this.relEcaEpmChangeYn = relEcaEpmChangeYn;
    }

    public void setRelEcaEpmDocType(String relEcaEpmDocType) {
	this.relEcaEpmDocType = relEcaEpmDocType;
    }

    public void setRelEcaObjectActivityComment(String relEcaObjectActivityComment) {
	this.relEcaObjectActivityComment = relEcaObjectActivityComment;
    }

    public void setRelEcaObjectAfterRev(String relEcaObjectAfterRev) {
	this.relEcaObjectAfterRev = relEcaObjectAfterRev;
    }

    public void setRelEcaObjectLinkOid(String relEcaObjectLinkOid) {
	this.relEcaObjectLinkOid = relEcaObjectLinkOid;
    }

    public void setRelEcaObjectName(String relEcaObjectName) {
	this.relEcaObjectName = relEcaObjectName;
    }

    public void setRelEcaObjectNo(String relEcaObjectNo) {
	this.relEcaObjectNo = relEcaObjectNo;
    }

    public void setRelEcaObjectOid(String relEcaObjectOid) {
	this.relEcaObjectOid = relEcaObjectOid;
    }

    public void setRelEcaObjectPlanDate(String relEcaObjectPlanDate) {
	this.relEcaObjectPlanDate = relEcaObjectPlanDate;
    }

    public void setRelEcaObjectPreRev(String relEcaObjectPreRev) {
	this.relEcaObjectPreRev = relEcaObjectPreRev;
    }

    public void setRelEcaOid(String relEcaOid) {
	this.relEcaOid = relEcaOid;
    }

    public void setScheduleId(String scheduleId) {
	this.scheduleId = scheduleId;
    }

    public void setSecure_budget_yn(String secure_budget_yn) {
	this.secure_budget_yn = secure_budget_yn;
    }

    public void setTargetPart(String targetPart) {
	this.targetPart = targetPart;
    }

    public void setWorkerId(String workerId) {
	this.workerId = workerId;
    }

    public void setWorkerName(String workerName) {
	this.workerName = workerName;
    }
}
