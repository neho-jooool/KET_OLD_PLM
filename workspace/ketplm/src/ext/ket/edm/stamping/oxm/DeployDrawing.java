package ext.ket.edm.stamping.oxm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "drawing")
public class DeployDrawing {

    private String cadType;
    private String transRequest;
    private String cadOid;
    private String drawingNumber;
    private String drawingVersion;
    @XmlJavaTypeAdapter(value = CDATAAdapter.class)
    private String drawingFileName;
    private String transNxSelectedSheet;
    private String transThreedCadNo;
    private String transThreedCadVersion;
    private String transThreedCadOid;
    @XmlJavaTypeAdapter(value = CDATAAdapter.class)
    private String transThreedCadFileName;
    private String stampType;
    private String stampAprovedDate;
    @XmlJavaTypeAdapter(value = CDATAAdapter.class)
    private String stampTeamName;

    private String stampRequest;
    private String stepRequest;
    private String igesRequest;
    private String pdfRequest;
    private String catRequest;
    @XmlElement(name = "imageRequest")
    private DeployDrawingSub imageRequest;

    public String getCadType() {
	return cadType;
    }

    public void setCadType(String cadType) {
	this.cadType = cadType;
    }

    public String getTransRequest() {
	return transRequest;
    }

    public void setTransRequest(String transRequest) {
	this.transRequest = transRequest;
    }

    public String getCadOid() {
	return cadOid;
    }

    public void setCadOid(String cadOid) {
	this.cadOid = cadOid;
    }

    public String getDrawingNumber() {
	return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
	this.drawingNumber = drawingNumber;
    }

    public String getDrawingVersion() {
	return drawingVersion;
    }

    public void setDrawingVersion(String drawingVersion) {
	this.drawingVersion = drawingVersion;
    }

    public String getDrawingFileName() {
	return drawingFileName;
    }

    public void setDrawingFileName(String drawingFileName) {
	this.drawingFileName = drawingFileName;
    }

    public String getTransNxSelectedSheet() {
	return transNxSelectedSheet;
    }

    public void setTransNxSelectedSheet(String transNxSelectedSheet) {
	this.transNxSelectedSheet = transNxSelectedSheet;
    }

    public String getTransThreedCadNo() {
	return transThreedCadNo;
    }

    public void setTransThreedCadNo(String transThreedCadNo) {
	this.transThreedCadNo = transThreedCadNo;
    }

    public String getTransThreedCadVersion() {
	return transThreedCadVersion;
    }

    public void setTransThreedCadVersion(String transThreedCadVersion) {
	this.transThreedCadVersion = transThreedCadVersion;
    }

    public String getTransThreedCadFileName() {
	return transThreedCadFileName;
    }

    public void setTransThreedCadFileName(String transThreedCadFileName) {
	this.transThreedCadFileName = transThreedCadFileName;
    }

    public String getStampType() {
	return stampType;
    }

    public void setStampType(String stampType) {
	this.stampType = stampType;
    }

    public String getStampAprovedDate() {
	return stampAprovedDate;
    }

    public void setStampAprovedDate(String stampAprovedDate) {
	this.stampAprovedDate = stampAprovedDate;
    }

    public String getStampTeamName() {
	return stampTeamName;
    }

    public void setStampTeamName(String stampTeamName) {
	this.stampTeamName = stampTeamName;
    }

    public String getTransThreedCadOid() {
	return transThreedCadOid;
    }

    public void setTransThreedCadOid(String transThreedCadOid) {
	this.transThreedCadOid = transThreedCadOid;
    }

    public String getStampRequest() {
	return stampRequest;
    }

    public void setStampRequest(String stampRequest) {
	this.stampRequest = stampRequest;
    }

    public String getStepRequest() {
	return stepRequest;
    }

    public void setStepRequest(String stepRequest) {
	this.stepRequest = stepRequest;
    }

    public String getIgesRequest() {
	return igesRequest;
    }

    public void setIgesRequest(String igesRequest) {
	this.igesRequest = igesRequest;
    }

    public String getPdfRequest() {
	return pdfRequest;
    }

    public void setPdfRequest(String pdfRequest) {
	this.pdfRequest = pdfRequest;
    }

    public DeployDrawingSub getImageRequest() {
	return imageRequest;
    }

    public void setImageRequest(DeployDrawingSub imageRequest) {
	this.imageRequest = imageRequest;
    }

    public String getCatRequest() {
	return catRequest;
    }

    public void setCatRequest(String catRequest) {
	this.catRequest = catRequest;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
