package ext.ket.yesone.entity;

public class KETDepositPvtDTO extends KETYesoneBaseDTO{

    private static final long serialVersionUID = 1L;

//Start Man Attribute(ManOxm.java)

//End Man Attribute(ManOxm.java)

//Start Data Attribute(DataOxm.java)

    private String dat_cd;

    private String busnid;

    private String trade_nm;

    private String acc_no;

//End Data Attribute(DataOxm.java)

//Start Data Field(DataOxm.java)

    private String start_dt;

    private String end_dt;

    private String com_cd;

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

    public void setAcc_no(String acc_no) {

        this.acc_no = acc_no;

    }

    public String getAcc_no() {

        return acc_no;

    }

    public void setStart_dt(String start_dt) {

        this.start_dt = start_dt;

    }

    public String getStart_dt() {

        return start_dt;

    }

    public void setEnd_dt(String end_dt) {

        this.end_dt = end_dt;

    }

    public String getEnd_dt() {

        return end_dt;

    }

    public void setCom_cd(String com_cd) {

        this.com_cd = com_cd;

    }

    public String getCom_cd() {

        return com_cd;

    }

    public void setSum(String sum) {

        this.sum = sum;

    }

    public String getSum() {

        return sum;

    }

}