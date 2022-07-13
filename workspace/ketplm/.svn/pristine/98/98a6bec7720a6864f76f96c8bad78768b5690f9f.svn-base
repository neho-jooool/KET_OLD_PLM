package ext.ket.part.classify.oxm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Grid")
public class ClazXmlGridObject {

    @XmlElement(name = "Body", type = ClazXmlBodyObject.class)
    private ClazXmlBodyObject clazXmlBodyObject;

    public ClazXmlBodyObject getClazXmlBodyObject() {
	return clazXmlBodyObject;
    }

    public void setClazXmlBodyObject(ClazXmlBodyObject clazXmlBodyObject) {
	this.clazXmlBodyObject = clazXmlBodyObject;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
