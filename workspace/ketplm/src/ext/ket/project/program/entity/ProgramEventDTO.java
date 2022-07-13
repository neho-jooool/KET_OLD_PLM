package ext.ket.project.program.entity;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.outputtype.OEMPlan;
import ext.ket.shared.dto.BaseDTO;

public class ProgramEventDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String carEventOid;
    private String carEventName;
    private String carEventDate;
    private String customerEventName;
    private String customerEventDate;
    private String lastUsingBuyerName;
    private String lastUsingBuyerOid;

    public ProgramEventDTO() {
    }

    public ProgramEventDTO(OEMPlan oemPlan) {
	
	this.lastUsingBuyerName = oemPlan.getModelPlan().getCarType().getMaker().getName();
	this.lastUsingBuyerOid = CommonUtil.getOIDString(oemPlan.getModelPlan().getCarType().getMaker());
	this.carEventOid = CommonUtil.getOIDString(oemPlan);
	this.carEventName = oemPlan.getOemType().getName();
	this.carEventDate = DateUtil.getTimeFormat(oemPlan.getOemEndDate(), "yyyy-MM-dd");
	this.customerEventName = this.carEventName;
	this.customerEventDate = this.carEventDate;
    }

    public ProgramEventDTO(KETProgramEventLink programEventLink) {
	KETProgramEvent programEvent = programEventLink.getEvent();
	this.setOid(CommonUtil.getOIDString(programEventLink));
	if (programEvent.getOemPlan() != null) {
	    this.carEventOid = CommonUtil.getOIDString(programEvent.getOemPlan());
	    this.carEventName = programEvent.getOemPlan().getOemType().getName();
	    this.carEventDate = DateUtil.getTimeFormat(programEvent.getOemPlan().getOemEndDate(), "yyyy-MM-dd");
	}
	this.customerEventName = programEvent.getCustomerEventName();
	this.customerEventDate = DateUtil.getTimeFormat(programEvent.getCustomerEventDate(), "yyyy-MM-dd");
    }

    public String getCustomerEventName() {
	return customerEventName;
    }

    public void setCustomerEventName(String customerEventName) {
	this.customerEventName = customerEventName;
    }

    public String getCustomerEventDate() {
	return customerEventDate;
    }

    public String getGridCustomerEventDate() {
	return "";
    }

    public String getGridCustomerEventDateHtmlPrefix() {
	return "<input type='text' name='customerEventDate' class='txt_field' value='" + customerEventDate + "' style='width:100px'>"
	        + "<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"customerEventDate\");' style='cursor: hand;'>";
    }

    public String getGridCustomerEventDateHtmlPostfix() {
	return "";
    }

    public void setCustomerEventDate(String customerEventDate) {
	this.customerEventDate = customerEventDate;
    }

    public String getCarEventName() {
	return carEventName;
    }

    public void setCarEventName(String carEventName) {
	this.carEventName = carEventName;
    }

    public String getCarEventDate() {
	return carEventDate;
    }

    public void setCarEventDate(String carEventDate) {
	this.carEventDate = carEventDate;
    }

    /**
     * @return the carEventOid
     */
    public String getCarEventOid() {
	return carEventOid;
    }

    /**
     * @param carEventOid
     *            the carEventOid to set
     */
    public void setCarEventOid(String carEventOid) {
	this.carEventOid = carEventOid;
    }

    public String getLastUsingBuyerName() {
        return lastUsingBuyerName;
    }

    public void setLastUsingBuyerName(String lastUsingBuyerName) {
        this.lastUsingBuyerName = lastUsingBuyerName;
    }

    public String getLastUsingBuyerOid() {
        return lastUsingBuyerOid;
    }

    public void setLastUsingBuyerOid(String lastUsingBuyerOid) {
        this.lastUsingBuyerOid = lastUsingBuyerOid;
    }
}
