package ext.ket.part.classify.oxm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Body")
public class ClazXmlBodyObject {

    @XmlElement(name = "B", type = ClazXmlPageObject.class)
    private ClazXmlPageObject clazXmlPageObject;

    public ClazXmlPageObject getClazXmlPageObject() {
	return clazXmlPageObject;
    }

    public void setClazXmlPageObject(ClazXmlPageObject clazXmlPageObject) {
	this.clazXmlPageObject = clazXmlPageObject;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
