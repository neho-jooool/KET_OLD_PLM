package ext.ket.yesone.entity;

public class KETHouseMtgDTO extends KETYesoneBaseDTO{

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

    private String lend_kd;

    private String house_take_dt;

    private String mort_setup_dt;

    private String start_dt;

    private String end_dt;

    private String repay_years;

    private String lend_goods_nm;

    private String debt;

    private String fixed_rate_debt;

    private String not_defer_debt;

    private String this_year_rede_amt;

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

    public void setLend_kd(String lend_kd) {

        this.lend_kd = lend_kd;

    }

    public String getLend_kd() {

        return lend_kd;

    }

    public void setHouse_take_dt(String house_take_dt) {

        this.house_take_dt = house_take_dt;

    }

    public String getHouse_take_dt() {

        return house_take_dt;

    }

    public void setMort_setup_dt(String mort_setup_dt) {

        this.mort_setup_dt = mort_setup_dt;

    }

    public String getMort_setup_dt() {

        return mort_setup_dt;

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

    public void setRepay_years(String repay_years) {

        this.repay_years = repay_years;

    }

    public String getRepay_years() {

        return repay_years;

    }

    public void setLend_goods_nm(String lend_goods_nm) {

        this.lend_goods_nm = lend_goods_nm;

    }

    public String getLend_goods_nm() {

        return lend_goods_nm;

    }

    public void setDebt(String debt) {

        this.debt = debt;

    }

    public String getDebt() {

        return debt;

    }

    public void setFixed_rate_debt(String fixed_rate_debt) {

        this.fixed_rate_debt = fixed_rate_debt;

    }

    public String getFixed_rate_debt() {

        return fixed_rate_debt;

    }

    public void setNot_defer_debt(String not_defer_debt) {

        this.not_defer_debt = not_defer_debt;

    }

    public String getNot_defer_debt() {

        return not_defer_debt;

    }

    public void setThis_year_rede_amt(String this_year_rede_amt) {

        this.this_year_rede_amt = this_year_rede_amt;

    }

    public String getThis_year_rede_amt() {

        return this_year_rede_amt;

    }

    public void setSum(String sum) {

        this.sum = sum;

    }

    public String getSum() {

        return sum;

    }

}