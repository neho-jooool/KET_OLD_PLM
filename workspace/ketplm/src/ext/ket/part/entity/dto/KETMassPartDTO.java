package ext.ket.part.entity.dto;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.part.entity.KETPartMassPlan;
import ext.ket.shared.dto.BaseDTO;

public class KETMassPartDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String oid;
    private String pjtNo;
    private String partNo;
    private String devSteps;
    private String masStartDate;
    private String masEndDate;
    private String partName;
    private String modifyName;
    private String modifyDate;

    private String pjtNos;
    private String processGb;
    private String pjtEndDate;
    private String ecoNo;
    private String ecoApproveDate;
    private String dr6EndDate;
    private String createDate;
    private String createName;
    private String pjtName;
    private String bigo;
    private String newPart;

    public KETMassPartDTO() {

    }

    public KETMassPartDTO(KETPartMassPlan massPart) throws Exception {
	this.oid = CommonUtil.getOIDString(massPart);
	this.pjtNo = massPart.getPjtNo();
	this.masStartDate = DateUtil.getDateString(massPart.getMasStartDate(), "d");
	this.partName = massPart.getPartName();
	this.partNo = massPart.getPartNo();
	this.pjtNos = massPart.getPjtNos();
	this.processGb = massPart.getProcessGb();
	this.pjtEndDate = DateUtil.getDateString(massPart.getPjtEndDate(), "d");
	this.ecoNo = massPart.getEcoNo();
	this.ecoApproveDate = DateUtil.getDateString(massPart.getEcoApproveDate(), "d");
	this.dr6EndDate = DateUtil.getDateString(massPart.getDr6EndDate(), "d");
	this.createName = massPart.getOwner().getFullName();
	this.modifyName = massPart.getModifyName();
	this.pjtName = massPart.getPjtName();
	this.bigo = massPart.getBigo();
    }

    public String getOid() {
	return oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public String getPjtNo() {
	return pjtNo;
    }

    public void setPjtNo(String pjtNo) {
	this.pjtNo = pjtNo;
    }

    public String getPartNo() {
	return partNo;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getDevSteps() {
	return devSteps;
    }

    public void setDevSteps(String devSteps) {
	this.devSteps = devSteps;
    }

    public String getMasStartDate() {
	return masStartDate;
    }

    public void setMasStartDate(String masStartDate) {
	this.masStartDate = masStartDate;
    }

    public String getMasEndDate() {
	return masEndDate;
    }

    public void setMasEndDate(String masEndDate) {
	this.masEndDate = masEndDate;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getModifyName() {
	return modifyName;
    }

    public void setModifyName(String modifyName) {
	this.modifyName = modifyName;
    }

    public String getModifyDate() {
	return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
	this.modifyDate = modifyDate;
    }

    public String getPjtNos() {
	return pjtNos;
    }

    public void setPjtNos(String pjtNos) {
	this.pjtNos = pjtNos;
    }

    public String getProcessGb() {
	return processGb;
    }

    public void setProcessGb(String processGb) {
	this.processGb = processGb;
    }

    public String getPjtEndDate() {
	return pjtEndDate;
    }

    public void setPjtEndDate(String pjtEndDate) {
	this.pjtEndDate = pjtEndDate;
    }

    public String getEcoNo() {
	return ecoNo;
    }

    public void setEcoNo(String ecoNo) {
	this.ecoNo = ecoNo;
    }

    public String getEcoApproveDate() {
	return ecoApproveDate;
    }

    public void setEcoApproveDate(String ecoApproveDate) {
	this.ecoApproveDate = ecoApproveDate;
    }

    public String getDr6EndDate() {
	return dr6EndDate;
    }

    public void setDr6EndDate(String dr6EndDate) {
	this.dr6EndDate = dr6EndDate;
    }

    public String getCreateDate() {
	return createDate;
    }

    public void setCreateDate(String createDate) {
	this.createDate = createDate;
    }

    public String getCreateName() {
	return createName;
    }

    public void setCreateName(String createName) {
	this.createName = createName;
    }

    public String getPjtName() {
	return pjtName;
    }

    public void setPjtName(String pjtName) {
	this.pjtName = pjtName;
    }

    public String getBigo() {
	return bigo;
    }

    public void setBigo(String bigo) {
	this.bigo = bigo;
    }

    public String getNewPart() {
	return newPart;
    }

    public void setNewPart(String newPart) {
	this.newPart = newPart;
    }

}
