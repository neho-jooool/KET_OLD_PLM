package ext.ket.edm.stamping.oxm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "requestDrawingList")
public class RequestDrawingList {

    @XmlElement(name = "drawing", type = DeployDrawing.class)
    private List<DeployDrawing> drawingList = new ArrayList<DeployDrawing>();

    public List<DeployDrawing> getDrawingList() {
	return drawingList;
    }

    public void setDrawingList(List<DeployDrawing> drawingList) {
	this.drawingList = drawingList;
    }

    public void addDeployDrawing(DeployDrawing deployDrawing) {
	this.drawingList.add(deployDrawing);
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
