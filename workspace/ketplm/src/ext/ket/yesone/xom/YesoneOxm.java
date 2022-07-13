package ext.ket.yesone.xom;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="yesone")
@XmlAccessorType(XmlAccessType.FIELD)
public class YesoneOxm
{
    @XmlElement(name="doc", type=DocOxm.class)
    private DocOxm doc;
    
    @XmlElement(name="form")
    private ArrayList<FormOxm> FormOxmList = new ArrayList<FormOxm>();

    public DocOxm getDoc() {
        return doc;
    }

    public void setDoc(DocOxm doc) {
        this.doc = doc;
    }

    public ArrayList<FormOxm> getFormOxmList() {
        return FormOxmList;
    }

    public void setFormOxmList(ArrayList<FormOxm> formOxmList) {
        FormOxmList = formOxmList;
    }
}
