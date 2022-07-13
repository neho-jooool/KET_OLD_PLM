package ext.ket.yesone.xom;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FormOxm
{
    @XmlAttribute(name="form_cd")
    private String form_cd;
    
    @XmlElement(name="man", type=ManOxm.class)
    private ArrayList<ManOxm> ManOxmList = new ArrayList<ManOxm>();

    public String getForm_cd() {
        return form_cd;
    }

    public void setForm_cd(String form_cd) {
        this.form_cd = form_cd;
    }

    public ArrayList<ManOxm> getManOxmList() {
        return ManOxmList;
    }

    public void setManOxmList(ArrayList<ManOxm> manOxmList) {
        ManOxmList = manOxmList;
    }
}
