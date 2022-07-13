package ext.ket.yesone.entity;

public class KETHouseLandDTO extends KETYesoneBaseDTO{

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

    private String goods_nm;

    private String lend_dt;

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

    public void setGoods_nm(String goods_nm) {

        this.goods_nm = goods_nm;

    }

    public String getGoods_nm() {

        return goods_nm;

    }

    public void setLend_dt(String lend_dt) {

        this.lend_dt = lend_dt;

    }

    public String getLend_dt() {

        return lend_dt;

    }

    public void setSum(String sum) {

        this.sum = sum;

    }

    public String getSum() {

        return sum;

    }

}