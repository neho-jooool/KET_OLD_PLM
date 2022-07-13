package e3ps.ecm.entity;

public class KETMoldEcoEcrLinkVO extends GeneralVO {
	private static final long serialVersionUID = 1996208176149808272L;
	
	private String relEcrLinkOid;//oid
	private String relEcrOid;//ECRoid
	private String relEcrId;//ECR번호
	private String relEcrName; //ECR Name
	private String createDeptName;//작성부서
	private String creatorName;//작성자명
	private String approveDate;//승인일자
	private String secureBudgetYn;

	public String getRelEcrLinkOid() {
		return relEcrLinkOid;
	}
	public void setRelEcrLinkOid(String relEcrLinkOid) {
		this.relEcrLinkOid = relEcrLinkOid;
	}
	public String getRelEcrOid() {
		return relEcrOid;
	}
	public void setRelEcrOid(String relEcrOid) {
		this.relEcrOid = relEcrOid;
	}
	public String getRelEcrId() {
		return relEcrId;
	}
	public void setRelEcrId(String relEcrId) {
		this.relEcrId = relEcrId;
	}
	public String getCreateDeptName() {
		return createDeptName;
	}
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	public String getRelEcrName() {
		return relEcrName;
	}
	public void setRelEcrName(String relEcrName) {
		this.relEcrName = relEcrName;
	}
	public String getSecureBudgetYn() {
		return secureBudgetYn;
	}
	public void setSecureBudgetYn(String secureBudgetYn) {
		this.secureBudgetYn = secureBudgetYn;
	}

}
