package ext.ket.project.program.entity;

import java.util.List;

import wt.enterprise.RevisionControlled;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.shared.dto.BaseDTO;

public class ProgramNotifyDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String version;
    private String notifyer;
    private String notifyDate;
    private String notifyReason;
    private List<String> versions;
    private List<String> versionOids;

    public ProgramNotifyDTO() {
    }

    public ProgramNotifyDTO(RevisionControlled obj) {
	this.setOid(CommonUtil.getOIDString(obj));
	this.version = obj.getVersionIdentifier().getValue();
	this.notifyer = obj.getCreatorFullName();
	this.notifyDate = DateUtil.getTimeFormat(obj.getCreateTimestamp(), "yyyy-MM-dd");
	this.notifyReason = obj.getVersionIdentifier().getValue().equals("0") ? "최초등록" : obj.getIterationNote(); // TODO Note를 어케 가져오나?
    }

    public String getVersion() {
	return version;
    }

    public void setVersion(String version) {
	this.version = version;
    }

    public String getNotifyer() {
	return notifyer;
    }

    public void setNotifyer(String notifyer) {
	this.notifyer = notifyer;
    }

    public String getNotifyDate() {
	return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
	this.notifyDate = notifyDate;
    }

    public String getNotifyReason() {
	return notifyReason;
    }

    public void setNotifyReason(String notifyReason) {
	this.notifyReason = notifyReason;
    }

    public String getNotifyDateHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getNotifyDateHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    /**
     * @return the versionOids
     */
    public List<String> getVersionOids() {
	return versionOids;
    }

    /**
     * @param versionOids
     *            the versionOids to set
     */
    public void setVersionOids(List<String> versionOids) {
	this.versionOids = versionOids;
    }

    /**
     * @return the versions
     */
    public List<String> getVersions() {
	return versions;
    }

    /**
     * @param versions
     *            the versions to set
     */
    public void setVersions(List<String> versions) {
	this.versions = versions;
    }
}
