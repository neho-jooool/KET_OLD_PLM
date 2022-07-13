package ext.ket.edm.entity.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ext.ket.shared.dto.BaseDTO;

public class KETDrawingDownHisDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String downloadFileName;
    private String downloadReason;
    private Date downloadDate;
    private String distSubcontractor;

    public String getDownloadFileName() {
	return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
	this.downloadFileName = downloadFileName;
    }

    public String getDownloadReason() {
	return downloadReason;
    }

    public void setDownloadReason(String downloadReason) {
	this.downloadReason = downloadReason;
    }

    public Date getDownloadDate() {
	return downloadDate;
    }

    public void setDownloadDate(Date downloadDate) {
	this.downloadDate = downloadDate;
    }

    public String getDistSubcontractor() {
	return distSubcontractor;
    }

    public void setDistSubcontractor(String distSubcontractor) {
	this.distSubcontractor = distSubcontractor;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
