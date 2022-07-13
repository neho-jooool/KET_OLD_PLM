package ext.ket.wfm.entity;

import e3ps.common.util.DateUtil;
import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : KETTodoDelegateHistoryDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 9. 4.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class KETTodoDelegateHistoryDTO extends BaseDTO {

    private static final long serialVersionUID = 8595447568822278401L;

    private String	    delegateDate;
    private String	    fromUserName;
    private String	    toUserName;
    private String	    reason;

    public KETTodoDelegateHistoryDTO(KETTodoDelegateHistory history) {

	setDelegateDate(DateUtil.getDateString(history.getPersistInfo().getCreateStamp(), "d"));
	setFromUserName(history.getFromUser().getName());
	setToUserName(history.getToUser().getName());
	setReason(history.getReason());
    }

    /**
     * @return the delegateDate
     */
    public String getDelegateDate() {
	return delegateDate;
    }

    /**
     * @param delegateDate
     *            the delegateDate to set
     */
    public void setDelegateDate(String delegateDate) {
	this.delegateDate = delegateDate;
    }

    /**
     * @return the fromUserName
     */
    public String getFromUserName() {
	return fromUserName;
    }

    /**
     * @param fromUserName
     *            the fromUserName to set
     */
    public void setFromUserName(String fromUserName) {
	this.fromUserName = fromUserName;
    }

    /**
     * @return the toUserName
     */
    public String getToUserName() {
	return toUserName;
    }

    /**
     * @param toUserName
     *            the toUserName to set
     */
    public void setToUserName(String toUserName) {
	this.toUserName = toUserName;
    }

    /**
     * @return the reason
     */
    public String getReason() {
	return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason) {
	this.reason = reason;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
	return serialVersionUID;
    }

}
