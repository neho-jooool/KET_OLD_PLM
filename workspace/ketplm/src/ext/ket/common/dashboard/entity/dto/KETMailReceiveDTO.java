package ext.ket.common.dashboard.entity.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

final public class KETMailReceiveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String targetPeopleName;
    private String targetPeopleOid;
    private String receiveOid;
    private String duty;
    private String dept;
    private String targetDeptName;
    private String targetDeptOid;
    private String targetDeptOidAttr[];
    private String targetPeopleOidAttr[];
    private String receiveOidAttr[];

    public String getTargetPeopleName() {
	return targetPeopleName;
    }

    public void setTargetPeopleName(String targetPeopleName) {
	this.targetPeopleName = targetPeopleName;
    }

    public String getTargetPeopleOid() {
	return targetPeopleOid;
    }

    public void setTargetPeopleOid(String targetPeopleOid) {
	this.targetPeopleOid = targetPeopleOid;
    }

    public String getReceiveOid() {
	return receiveOid;
    }

    public void setReceiveOid(String receiveOid) {
	this.receiveOid = receiveOid;
    }

    public String getDuty() {
	return duty;
    }

    public void setDuty(String duty) {
	this.duty = duty;
    }

    public String getDept() {
	return dept;
    }

    public void setDept(String dept) {
	this.dept = dept;
    }

    public String getTargetDeptName() {
	return targetDeptName;
    }

    public void setTargetDeptName(String targetDeptName) {
	this.targetDeptName = targetDeptName;
    }

    public String getTargetDeptOid() {
	return targetDeptOid;
    }

    public void setTargetDeptOid(String targetDeptOid) {
	this.targetDeptOid = targetDeptOid;
    }

    public String[] getTargetDeptOidAttr() {
	return targetDeptOidAttr;
    }

    public void setTargetDeptOidAttr(String[] targetDeptOidAttr) {
	this.targetDeptOidAttr = targetDeptOidAttr;
    }

    public String[] getTargetPeopleOidAttr() {
	return targetPeopleOidAttr;
    }

    public void setTargetPeopleOidAttr(String[] targetPeopleOidAttr) {
	this.targetPeopleOidAttr = targetPeopleOidAttr;
    }

    public String[] getReceiveOidAttr() {
	return receiveOidAttr;
    }

    public void setReceiveOidAttr(String[] receiveOidAttr) {
	this.receiveOidAttr = receiveOidAttr;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}