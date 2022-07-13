package ext.ket.wfm.entity;

import wt.org.WTUser;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.util.WFMProperties;

/**
 * @클래스명 : KETWfmApprovalHistoryDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 8. 28.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class KETWfmApprovalHistoryDTO {

	private String deptName;
	private String ownerName;
	private String completeDate;
	private String activityName;
	private String activateStyle;
	private String classStyle;
	public static final String REQUEST = WFMProperties.getInstance().getString("wfm.request");
	public static final String REWORK = WFMProperties.getInstance().getString("wfm.rework");
	public static final String REVIEW = WFMProperties.getInstance().getString("wfm.review");
	public static final String DISCUSS = WFMProperties.getInstance().getString("wfm.discuss");
	public static final String RECEIVER = WFMProperties.getInstance().getString("wfm.receiver");
	public static final String REFERENCE = WFMProperties.getInstance().getString("wfm.reference");
	public static final String ACCEPT = WFMProperties.getInstance().getString("wfm.accept");
	public static final String CONACCEPT = WFMProperties.getInstance().getString("wfm.conaccept");
	public static final String REQRECIPIENT = WFMProperties.getInstance().getString("wfm.reqrecipient");
	public static final String DELEGATE = WFMProperties.getInstance().getString("wfm.delegate.receipt");
	public static final String DISTRIBUTE = WFMProperties.getInstance().getString("wfm.request.distribute");
	public static final String CONFIRM_REJECT = WFMProperties.getInstance().getString("wfm.confirm.reject");
	public static final int INPROGRESS = 1;
	public static final int COMPLETED = 2;
	public static final int RECEIPT = 3;
	private KETWfmApprovalHistory history;
	private String comment;
	private boolean completed = false;
	private String decision;
	private WTUser owner;
	private boolean last = false;
	private boolean riskCheck = false;

	public KETWfmApprovalHistoryDTO(KETWfmApprovalHistory history) throws Exception {

		WTUser user = (WTUser) history.getOwner().getPrincipal();
		PeopleData pdata = new PeopleData(user);
		setDeptName(pdata.departmentName);
		setOwnerName(pdata.name);
		setCompleteDate((history.getCompletedDate() == null) ? "&nbsp;" : DateUtil.getDateString(history.getCompletedDate(), "d"));
		String activityName = history.getActivityName();
		if (REVIEW.equals(activityName) && history.isLast())
			activityName = ACCEPT;
		if (history.isConditionalAccept())
			activityName = CONACCEPT;
		if (StringUtil.checkString(history.getDecision()) && !ACCEPT.equals(history.getDecision()))
			activityName = history.getDecision();
		setActivityName(activityName);
		String activateStyle = "";
		if (CommonUtil.getUserIDFromSession().equals(user.getName()) && history.getCompletedDate() == null)
			activateStyle = "class=\"activate\"";
		setActivateStyle(activateStyle);
		String classStyle = "";
		if (REQUEST.equals(activityName))
			classStyle = "class=\"color01\"";
		else if (REVIEW.equals(activityName) || DISCUSS.equals(activityName))
			classStyle = "class=\"color02\"";
		else if (ACCEPT.equals(activityName) || CONACCEPT.equals(activityName))
			classStyle = "class=\"color04\"";
		setClassStyle(classStyle);
		setHistory(history);

		String comments = StringUtil.checkNull(history.getComments());
		comments = comments.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");

		setComment(comments);
		setCompleted(history.getCompletedDate() != null);
		setDecision(StringUtil.checkNull(history.getDecision()));
		setOwner((WTUser) history.getOwner().getObject());
		setLast(history.isLast());
		setRiskCheck(history.isRiskCheck());
	}

	public boolean isRiskCheck() {
		return riskCheck;
	}

	public void setRiskCheck(boolean riskCheck) {
		this.riskCheck = riskCheck;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the ownerName
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName
	 *            the ownerName to set
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * @return the completeDate
	 */
	public String getCompleteDate() {
		return completeDate;
	}

	/**
	 * @param completeDate
	 *            the completeDate to set
	 */
	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName
	 *            the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return the classStyle
	 */
	public String getClassStyle() {
		return classStyle;
	}

	/**
	 * @param classStyle
	 *            the classStyle to set
	 */
	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	/**
	 * @return the activateStyle
	 */
	public String getActivateStyle() {
		return activateStyle;
	}

	/**
	 * @param activateStyle
	 *            the activateStyle to set
	 */
	public void setActivateStyle(String activateStyle) {
		this.activateStyle = activateStyle;
	}

	/**
	 * @return the history
	 */
	public KETWfmApprovalHistory getHistory() {
		return history;
	}

	/**
	 * @param history
	 *            the history to set
	 */
	public void setHistory(KETWfmApprovalHistory history) {
		this.history = history;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed
	 *            the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the decision
	 */
	public String getDecision() {
		return decision;
	}

	/**
	 * @param decision
	 *            the decision to set
	 */
	public void setDecision(String decision) {
		this.decision = decision;
	}

	/**
	 * @return the owner
	 */
	public WTUser getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(WTUser owner) {
		this.owner = owner;
	}

	/**
	 * @return the last
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(boolean last) {
		this.last = last;
	}

}
