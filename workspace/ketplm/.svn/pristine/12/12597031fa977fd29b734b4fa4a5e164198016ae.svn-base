package ext.ket.part.classify.oxm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "B")
public class ClazXmlPageObject {

    @XmlElement(name = "I", type = ClazXmlRowObject.class)
    private List<ClazXmlRowObject> clazXmlRowObjectList = new ArrayList<ClazXmlRowObject>();

    public List<ClazXmlRowObject> getClazXmlRowObjectList() {
	return clazXmlRowObjectList;
    }

    public void addClazXmlRowObject(ClazXmlRowObject clazXmlRowObject) {
	this.clazXmlRowObjectList.add(clazXmlRowObject);
    }

    public void setClazXmlRowObjectList(List<ClazXmlRowObject> clazXmlRowObjectList) {
	this.clazXmlRowObjectList = clazXmlRowObjectList;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
