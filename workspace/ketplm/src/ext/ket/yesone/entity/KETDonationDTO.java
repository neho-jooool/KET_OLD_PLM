package ext.ket.yesone.entity;

public class KETDonationDTO extends KETYesoneBaseDTO{

    private static final long serialVersionUID = 1L;

//Start Man Attribute(ManOxm.java)

//End Man Attribute(ManOxm.java)

//Start Data Attribute(DataOxm.java)

    private String dat_cd;

    private String busnid;

    private String trade_nm;

    private String donation_cd;

//End Data Attribute(DataOxm.java)

//Start Data Field(DataOxm.java)

    private String sum;

//Start Data Field(DataOxm.java)

    public void setDat_cd(String dat_cd) {

        this.dat_cd = dat_cd;

    }

    public String getDat_cd() {

        return dat_cd;

    }

    public void setBusnid(String busnid) {

        this.busnid = busnid;

    }

    public String getBusnid() {

        return busnid;

    }

    public void setTrade_nm(String trade_nm) {

        this.trade_nm = trade_nm;

    }

    public String getTrade_nm() {

        return trade_nm;

    }

    public void setDonation_cd(String donation_cd) {

        this.donation_cd = donation_cd;

    }

    public String getDonation_cd() {

        return donation_cd;

    }

    public void setSum(String sum) {

        this.sum = sum;

    }

    public String getSum() {

        return sum;

    }

}