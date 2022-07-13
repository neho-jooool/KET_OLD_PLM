package ext.ket.yesone.xom;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ManOxm
{
    @XmlAttribute(name="resid")
    private String resid;
    
    @XmlAttribute(name="name")
    private String name;
    
    @XmlAttribute(name="ftyr_market_tot_amt")
    private String ftyr_market_tot_amt;

    @XmlAttribute(name="ftyr_tmoney_tot_amt")
    private String ftyr_tmoney_tot_amt;

    @XmlAttribute(name="ftyr_tot_amt")
    private String ftyr_tot_amt;

    @XmlAttribute(name="pre_market_tot_amt")
    private String pre_market_tot_amt;

    @XmlAttribute(name="pre_tmoney_tot_amt")
    private String pre_tmoney_tot_amt;

    @XmlAttribute(name="pre_tot_amt")
    private String pre_tot_amt;    
    
    @XmlElement(name="sum_data")
    private ArrayList<SumDataOxm> SumDataOxmList = new ArrayList<SumDataOxm>();
    
    @XmlElement(name="data")
    private ArrayList<DataOxm> DataOxmList = new ArrayList<DataOxm>();

    public String getResid() {
        return resid;
    }


    public void setResid(String resid) {
        this.resid = resid;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getFtyr_market_tot_amt() {
        return ftyr_market_tot_amt;
    }


    public void setFtyr_market_tot_amt(String ftyr_market_tot_amt) {
        this.ftyr_market_tot_amt = ftyr_market_tot_amt;
    }


    public String getFtyr_tmoney_tot_amt() {
        return ftyr_tmoney_tot_amt;
    }


    public void setFtyr_tmoney_tot_amt(String ftyr_tmoney_tot_amt) {
        this.ftyr_tmoney_tot_amt = ftyr_tmoney_tot_amt;
    }


    public String getFtyr_tot_amt() {
        return ftyr_tot_amt;
    }


    public void setFtyr_tot_amt(String ftyr_tot_amt) {
        this.ftyr_tot_amt = ftyr_tot_amt;
    }


    public String getPre_market_tot_amt() {
        return pre_market_tot_amt;
    }


    public void setPre_market_tot_amt(String pre_market_tot_amt) {
        this.pre_market_tot_amt = pre_market_tot_amt;
    }


    public String getPre_tmoney_tot_amt() {
        return pre_tmoney_tot_amt;
    }


    public void setPre_tmoney_tot_amt(String pre_tmoney_tot_amt) {
        this.pre_tmoney_tot_amt = pre_tmoney_tot_amt;
    }


    public String getPre_tot_amt() {
        return pre_tot_amt;
    }


    public void setPre_tot_amt(String pre_tot_amt) {
        this.pre_tot_amt = pre_tot_amt;
    }
    
    public ArrayList<SumDataOxm> getSumDataOxmList() {
        return SumDataOxmList;
    }


    public void setSumDataOxmList(ArrayList<SumDataOxm> sumDataOxmList) {
        SumDataOxmList = sumDataOxmList;
    }


    public ArrayList<DataOxm> getDataOxmList() {
        return DataOxmList;
    }


    public void setDataOxmList(ArrayList<DataOxm> dataOxmList) {
        DataOxmList = dataOxmList;
    }
    
    
}
