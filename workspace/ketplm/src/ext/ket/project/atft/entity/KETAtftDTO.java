package ext.ket.project.atft.entity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ext.ket.shared.dto.BaseDTO;

final public class KETAtftDTO extends BaseDTO implements Serializable{
private static final long serialVersionUID = 1L;
    
    private String classification;
    private String sheetdivision;
    private String desision;
    private String basis;
    private String aftfInfo;
    private String projectOid;
    private KETATFTSheetTemplate attribute;
    private List<Map<String, Object>> atftInfoList;
    private String p1_oid;
    private String p2_oid;

    public String getP1_oid() {
        return p1_oid;
    }

    public void setP1_oid(String p1_oid) {
        this.p1_oid = p1_oid;
    }

    public String getP2_oid() {
        return p2_oid;
    }

    public void setP2_oid(String p2_oid) {
        this.p2_oid = p2_oid;
    }

    public List<Map<String, Object>> getAtftInfoList() {
        return atftInfoList;
    }

    public void setAtftInfoList(List<Map<String, Object>> atftInfoList) {
        this.atftInfoList = atftInfoList;
    }

    public KETATFTSheetTemplate getAttribute() {
        return attribute;
    }

    public void setAttribute(KETATFTSheetTemplate attribute) {
        this.attribute = attribute;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSheetdivision() {
        return sheetdivision;
    }

    public void setSheetdivision(String sheetdivision) {
        this.sheetdivision = sheetdivision;
    }

    public String getDesision() {
        return desision;
    }

    public void setDesision(String desision) {
        this.desision = desision;
    }
    
    public String getBasis() {
        return basis;
    }



    public void setBasis(String basis) {
        this.basis = basis;
    }



    public String getAftfInfo() {
        return aftfInfo;
    }



    public void setAftfInfo(String aftfInfo) {
        this.aftfInfo = aftfInfo;
    }



    public String getProjectOid() {
        return projectOid;
    }



    public void setProjectOid(String projectOid) {
        this.projectOid = projectOid;
    }



    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}


