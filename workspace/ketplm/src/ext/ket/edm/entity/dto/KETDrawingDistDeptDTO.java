package ext.ket.edm.entity.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class KETDrawingDistDeptDTO {

    private String deptCode;
    private String deptName;

    public String getDeptCode() {
	return deptCode;
    }

    public void setDeptCode(String deptCode) {
	this.deptCode = deptCode;
    }

    public String getDeptName() {
	return deptName;
    }

    public void setDeptName(String deptName) {
	this.deptName = deptName;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
