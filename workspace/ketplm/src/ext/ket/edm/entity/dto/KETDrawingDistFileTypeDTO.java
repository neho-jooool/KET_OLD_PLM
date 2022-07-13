package ext.ket.edm.entity.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class KETDrawingDistFileTypeDTO {

    private String distFileType;

    public String getDistFileType() {
	return distFileType;
    }

    public void setDistFileType(String distFileType) {
	this.distFileType = distFileType;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
