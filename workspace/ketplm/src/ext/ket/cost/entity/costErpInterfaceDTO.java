package ext.ket.cost.entity;

import org.apache.log4j.Logger;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import ext.ket.shared.dto.BaseDTO;

public class costErpInterfaceDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(costErpInterfaceDTO.class);

    private String oid;
    private String pjtNo;
    private String partNo;
    private String realPartNo;
    private String version;

    private String drStep;
    private String materialCost;
    private String laborCost;
    private String facReducePrice;
    private String directCost;
    private String inDirectCost;
    private String moldReducePrice;
    private String outUnitCost;
    private String etcCost;
    private String salesManageCost;
    private String scrapSalesCost;
    private String salesTargetCostTotal;
    private String productCostTotal;
    private String transferFlag;
    private String transferMsg;
    private String orgProductCostTotal;
    private String taskOid;
    private String transferDate;
    private String partOid;
    private String[] drSteps;
    private String[] transferFlags;
    private String lastest;
    private String partDetailLabel;
    private String inDirectLaborCost;
    private String gap;

    private String linkColor = PropertiesUtil.getSearchGridLinkColor();

    public costErpInterfaceDTO() {

    }

    @SuppressWarnings({ "static-access", "deprecation" })
    public costErpInterfaceDTO(CostInterfaceHistory obj) {
	this.oid = CommonUtil.getOIDString(obj);
	this.pjtNo = obj.getPjtNo();
	this.partNo = obj.getPartNo();
	this.realPartNo = obj.getRealPartNo();
	this.version = obj.getVersion();
	this.drStep = obj.getDrStep();
	this.materialCost = obj.getMaterialCost();
	this.laborCost = obj.getLaborCost();
	this.facReducePrice = obj.getFacReducePrice();
	this.directCost = obj.getDirectCost();
	this.moldReducePrice = obj.getMoldReducePrice();
	this.outUnitCost = obj.getOutUnitCost();
	this.etcCost = obj.getEtcCost();
	this.salesManageCost = obj.getSalesManageCost();
	this.productCostTotal = obj.getProductCostTotal();
	this.inDirectCost = obj.getInDirectCost();
	this.salesTargetCostTotal = obj.getSalesTargetCostTotal();
	this.orgProductCostTotal = obj.getOrgProductCostTotal();
	this.taskOid = CommonUtil.getOIDString(obj.getTask());
	this.inDirectLaborCost = obj.getInDirectLaborCost();
	this.scrapSalesCost = obj.getScrapSalesCost();
	if (obj.getTransferDate() != null) {
	    this.transferDate = DateUtil.getDateString(obj.getTransferDate(), "d");
	}

	this.partOid = CommonUtil.getOIDString(obj.getCostPart());

	if (obj.isLastest()) {
	    this.lastest = "최신";
	}

	switch (obj.getTransferFlag()) {
	case "Y":
	    this.transferFlag = "전송완료";
	    break;

	case "N":
	    this.transferFlag = "전송대기";
	    break;

	case "E":
	    this.transferFlag = "전송오류";
	    break;

	default:
	    break;
	}
	this.transferMsg = obj.getTransferMsg();

    }

    /**
     * @return the oid
     */
    public String getOid() {
	return oid;
    }

    /**
     * @param oid
     *            the oid to set
     */
    public void setOid(String oid) {
	this.oid = oid;
    }

    /**
     * @return the pjtNo
     */
    public String getPjtNo() {
	return pjtNo;
    }

    /**
     * @param pjtNo
     *            the pjtNo to set
     */
    public void setPjtNo(String pjtNo) {
	this.pjtNo = pjtNo;
    }

    public String getPartNo() {
	return partNo;
    }

    /**
     * @param partNo
     *            the partNo to set
     */
    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    /**
     * @return the linkColor
     */
    public String getLinkColor() {
	return linkColor;
    }

    /**
     * @param linkColor
     *            the linkColor to set
     */
    public void setLinkColor(String linkColor) {
	this.linkColor = linkColor;
    }

    public String getRealPartNo() {
	return realPartNo;
    }

    public void setRealPartNo(String realPartNo) {
	this.realPartNo = realPartNo;
    }

    /**
     * @return the version
     */
    public String getVersion() {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
	this.version = version;
    }

    public String getDrStep() {
	return drStep;
    }

    public void setDrStep(String drStep) {
	this.drStep = drStep;
    }

    public String getMaterialCost() {
	return materialCost;
    }

    public void setMaterialCost(String materialCost) {
	this.materialCost = materialCost;
    }

    public String getLaborCost() {
	return laborCost;
    }

    public void setLaborCost(String laborCost) {
	this.laborCost = laborCost;
    }

    public String getFacReducePrice() {
	return facReducePrice;
    }

    public void setFacReducePrice(String facReducePrice) {
	this.facReducePrice = facReducePrice;
    }

    public String getDirectCost() {
	return directCost;
    }

    public void setDirectCost(String directCost) {
	this.directCost = directCost;
    }

    public String getInDirectCost() {
	return inDirectCost;
    }

    public void setInDirectCost(String inDirectCost) {
	this.inDirectCost = inDirectCost;
    }

    public String getMoldReducePrice() {
	return moldReducePrice;
    }

    public void setMoldReducePrice(String moldReducePrice) {
	this.moldReducePrice = moldReducePrice;
    }

    public String getOutUnitCost() {
	return outUnitCost;
    }

    public void setOutUnitCost(String outUnitCost) {
	this.outUnitCost = outUnitCost;
    }

    public String getEtcCost() {
	return etcCost;
    }

    public void setEtcCost(String etcCost) {
	this.etcCost = etcCost;
    }

    public String getSalesManageCost() {
	return salesManageCost;
    }

    public void setSalesManageCost(String salesManageCost) {
	this.salesManageCost = salesManageCost;
    }

    public String getScrapSalesCost() {
	return scrapSalesCost;
    }

    public void setScrapSalesCost(String scrapSalesCost) {
	this.scrapSalesCost = scrapSalesCost;
    }

    public String getSalesTargetCostTotal() {
	return salesTargetCostTotal;
    }

    public void setSalesTargetCostTotal(String salesTargetCostTotal) {
	this.salesTargetCostTotal = salesTargetCostTotal;
    }

    public String getProductCostTotal() {
	return productCostTotal;
    }

    public void setProductCostTotal(String productCostTotal) {
	this.productCostTotal = productCostTotal;
    }

    public String getTransferFlag() {
	return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
	this.transferFlag = transferFlag;
    }

    public String getTransferMsg() {
	return transferMsg;
    }

    public void setTransferMsg(String transferMsg) {
	this.transferMsg = transferMsg;
    }

    public String getOrgProductCostTotal() {
	return orgProductCostTotal;
    }

    public void setOrgProductCostTotal(String orgProductCostTotal) {
	this.orgProductCostTotal = orgProductCostTotal;
    }

    public String getTaskOid() {
	return taskOid;
    }

    public void setTaskOid(String taskOid) {
	this.taskOid = taskOid;
    }

    public String getTransferDate() {
	return transferDate;
    }

    public void setTransferDate(String transferDate) {
	this.transferDate = transferDate;
    }

    public String getPartOid() {
	return partOid;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public String[] getDrSteps() {
	return drSteps;
    }

    public void setDrSteps(String[] drSteps) {
	this.drSteps = drSteps;
    }

    public String[] getTransferFlags() {
	return transferFlags;
    }

    public void setTransferFlags(String[] transferFlags) {
	this.transferFlags = transferFlags;
    }

    public String getTransferFlagHtmlPrefix() {

	String html = "";
	if (this.transferFlag.equals("전송오류")) {
	    html = "<font color='red' style='cursor:hand'>";
	}
	return html;
    }

    public String getTransferFlagHtmlPostfix() {

	String html = "";
	if (this.transferFlag.equals("전송오류")) {
	    html = "</font>";
	}
	return html;
    }

    public String getPartDetailLabel() {
	return partDetailLabel;
    }

    public void setPartDetailLabel(String partDetailLabel) {
	this.partDetailLabel = partDetailLabel;
    }

    public String getPartDetailLabelHtmlPrefix() {

	// String html = "<span class=b-small box-size onclick=cost.detailPartPopup('" + this.taskOid + "','" + this.partOid + "')>확인";
	String html = "<span class=b-small box-size onclick=cost.detailPartPopup('" + this.oid + "')>확인";
	return html;
    }

    public String getPartDetailLabelHtmlPostfix() {

	String html = "</span>";
	return html;
    }

    public String getLastest() {
	return lastest;
    }

    public void setLastest(String lastest) {
	this.lastest = lastest;
    }

    public String getInDirectLaborCost() {
	return inDirectLaborCost;
    }

    public void setInDirectLaborCost(String inDirectLaborCost) {
	this.inDirectLaborCost = inDirectLaborCost;
    }

    public String getGap() {
	return gap;
    }

    public void setGap(String gap) {
	this.gap = gap;
    }
}
