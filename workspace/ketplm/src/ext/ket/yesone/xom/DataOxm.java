package ext.ket.yesone.xom;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataOxm
{
    @XmlAttribute(name="dat_cd")
    private String dat_cd;
    
    @XmlAttribute(name="busnid")
    private String busnid;
    
    @XmlAttribute(name="trade_nm")
    private String trade_nm;
    
    @XmlAttribute(name="acc_no")
    private String acc_no;

    @XmlAttribute(name="donation_cd")
    private String donation_cd;

    @XmlAttribute(name="edu_tp")
    private String edu_tp;

    @XmlAttribute(name="first_tot_amt")
    private String first_tot_amt;

    @XmlAttribute(name="first_year_tot_amt")
    private String first_year_tot_amt;

    @XmlAttribute(name="inqr_end_mm")
    private String inqr_end_mm;

    @XmlAttribute(name="inqr_strt_mm")
    private String inqr_strt_mm;

    @XmlAttribute(name="second_tot_amt")
    private String second_tot_amt;

    @XmlAttribute(name="second_year_tot_amt")
    private String second_year_tot_amt;

    @XmlAttribute(name="secu_no")
    private String secu_no;

    @XmlAttribute(name="use_place_cd")
    private String use_place_cd;
    
    @XmlElement(name="amt")
    private ArrayList<AmtOxm> AmtOxmList = new ArrayList<AmtOxm>();
    
    private String amt;

    private String ann_tot_amt;

    private String com_cd;

    private String course_cd;

    private String ddct_bs_ass_amt;

    private String debt;

    private String end_dt;

    private String fixed_rate_debt;

    private String fund_nm;

    private String goods_nm;

    private String house_take_dt;
    
    private String insu_nm;

    private String insu_resid;

    private String insu1_nm;

    private String insu1_resid;

    private String insu2_nm_1;

    private String insu2_nm_2;

    private String insu2_nm_3;

    private String insu2_resid_1;

    private String insu2_resid_2;

    private String insu2_resid_3;

    private String lend_dt;

    private String lend_goods_nm;

    private String lend_kd;

    private String lend_loan_amt;

    private String mort_setup_dt;

    private String not_defer_debt;

    private String pay_method;

    private String pension_cd;

    private String reg_dt;

    private String repay_years;

    private String saving_gubn;

    private String start_dt;

    private String subject_nm;

    private String sum;

    private String tax_year_amt;

    private String this_year_rede_amt;
    
    public String getDat_cd() {
        return dat_cd;
    }
    public void setDat_cd(String dat_cd) {
        this.dat_cd = dat_cd;
    }
    public String getBusnid() {
        return busnid;
    }
    public void setBusnid(String busnid) {
        this.busnid = busnid;
    }
    public String getTrade_nm() {
        return trade_nm;
    }
    public void setTrade_nm(String trade_nm) {
        this.trade_nm = trade_nm;
    }
    public String getAcc_no() {
        return acc_no;
    }
    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }
    public String getDonation_cd() {
        return donation_cd;
    }
    public void setDonation_cd(String donation_cd) {
        this.donation_cd = donation_cd;
    }
    public String getEdu_tp() {
        return edu_tp;
    }
    public void setEdu_tp(String edu_tp) {
        this.edu_tp = edu_tp;
    }
    public String getFirst_tot_amt() {
        return first_tot_amt;
    }
    public void setFirst_tot_amt(String first_tot_amt) {
        this.first_tot_amt = first_tot_amt;
    }
    public String getFirst_year_tot_amt() {
        return first_year_tot_amt;
    }
    public void setFirst_year_tot_amt(String first_year_tot_amt) {
        this.first_year_tot_amt = first_year_tot_amt;
    }
    public String getInqr_end_mm() {
        return inqr_end_mm;
    }
    public void setInqr_end_mm(String inqr_end_mm) {
        this.inqr_end_mm = inqr_end_mm;
    }
    public String getInqr_strt_mm() {
        return inqr_strt_mm;
    }
    public void setInqr_strt_mm(String inqr_strt_mm) {
        this.inqr_strt_mm = inqr_strt_mm;
    }
    public String getSecond_tot_amt() {
        return second_tot_amt;
    }
    public void setSecond_tot_amt(String second_tot_amt) {
        this.second_tot_amt = second_tot_amt;
    }
    public String getSecond_year_tot_amt() {
        return second_year_tot_amt;
    }
    public void setSecond_year_tot_amt(String second_year_tot_amt) {
        this.second_year_tot_amt = second_year_tot_amt;
    }
    public String getSecu_no() {
        return secu_no;
    }
    public void setSecu_no(String secu_no) {
        this.secu_no = secu_no;
    }
    public String getUse_place_cd() {
        return use_place_cd;
    }
    public void setUse_place_cd(String use_place_cd) {
        this.use_place_cd = use_place_cd;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getAnn_tot_amt() {
        return ann_tot_amt;
    }
    public void setAnn_tot_amt(String ann_tot_amt) {
        this.ann_tot_amt = ann_tot_amt;
    }
    public String getCom_cd() {
        return com_cd;
    }
    public void setCom_cd(String com_cd) {
        this.com_cd = com_cd;
    }
    public String getCourse_cd() {
        return course_cd;
    }
    public void setCourse_cd(String course_cd) {
        this.course_cd = course_cd;
    }
    public String getDdct_bs_ass_amt() {
        return ddct_bs_ass_amt;
    }
    public void setDdct_bs_ass_amt(String ddct_bs_ass_amt) {
        this.ddct_bs_ass_amt = ddct_bs_ass_amt;
    }
    public String getDebt() {
        return debt;
    }
    public void setDebt(String debt) {
        this.debt = debt;
    }
    public String getEnd_dt() {
        return end_dt;
    }
    public void setEnd_dt(String end_dt) {
        this.end_dt = end_dt;
    }
    public String getFixed_rate_debt() {
        return fixed_rate_debt;
    }
    public void setFixed_rate_debt(String fixed_rate_debt) {
        this.fixed_rate_debt = fixed_rate_debt;
    }
    public String getFund_nm() {
        return fund_nm;
    }
    public void setFund_nm(String fund_nm) {
        this.fund_nm = fund_nm;
    }
    public String getGoods_nm() {
        return goods_nm;
    }
    public void setGoods_nm(String goods_nm) {
        this.goods_nm = goods_nm;
    }
    public String getHouse_take_dt() {
        return house_take_dt;
    }
    public void setHouse_take_dt(String house_take_dt) {
        this.house_take_dt = house_take_dt;
    }
    public String getInsu_nm() {
        return insu_nm;
    }
    public void setInsu_nm(String insu_nm) {
        this.insu_nm = insu_nm;
    }
    public String getInsu_resid() {
        return insu_resid;
    }
    public void setInsu_resid(String insu_resid) {
        this.insu_resid = insu_resid;
    }
    public String getInsu1_nm() {
        return insu1_nm;
    }
    public void setInsu1_nm(String insu1_nm) {
        this.insu1_nm = insu1_nm;
    }
    public String getInsu1_resid() {
        return insu1_resid;
    }
    public void setInsu1_resid(String insu1_resid) {
        this.insu1_resid = insu1_resid;
    }
    public String getInsu2_nm_1() {
        return insu2_nm_1;
    }
    public void setInsu2_nm_1(String insu2_nm_1) {
        this.insu2_nm_1 = insu2_nm_1;
    }
    public String getInsu2_nm_2() {
        return insu2_nm_2;
    }
    public void setInsu2_nm_2(String insu2_nm_2) {
        this.insu2_nm_2 = insu2_nm_2;
    }
    public String getInsu2_nm_3() {
        return insu2_nm_3;
    }
    public void setInsu2_nm_3(String insu2_nm_3) {
        this.insu2_nm_3 = insu2_nm_3;
    }
    public String getInsu2_resid_1() {
        return insu2_resid_1;
    }
    public void setInsu2_resid_1(String insu2_resid_1) {
        this.insu2_resid_1 = insu2_resid_1;
    }
    public String getInsu2_resid_2() {
        return insu2_resid_2;
    }
    public void setInsu2_resid_2(String insu2_resid_2) {
        this.insu2_resid_2 = insu2_resid_2;
    }
    public String getInsu2_resid_3() {
        return insu2_resid_3;
    }
    public void setInsu2_resid_3(String insu2_resid_3) {
        this.insu2_resid_3 = insu2_resid_3;
    }
    public String getLend_dt() {
        return lend_dt;
    }
    public void setLend_dt(String lend_dt) {
        this.lend_dt = lend_dt;
    }
    public String getLend_goods_nm() {
        return lend_goods_nm;
    }
    public void setLend_goods_nm(String lend_goods_nm) {
        this.lend_goods_nm = lend_goods_nm;
    }
    public String getLend_kd() {
        return lend_kd;
    }
    public void setLend_kd(String lend_kd) {
        this.lend_kd = lend_kd;
    }
    public String getLend_loan_amt() {
        return lend_loan_amt;
    }
    public void setLend_loan_amt(String lend_loan_amt) {
        this.lend_loan_amt = lend_loan_amt;
    }
    public String getMort_setup_dt() {
        return mort_setup_dt;
    }
    public void setMort_setup_dt(String mort_setup_dt) {
        this.mort_setup_dt = mort_setup_dt;
    }
    public String getNot_defer_debt() {
        return not_defer_debt;
    }
    public void setNot_defer_debt(String not_defer_debt) {
        this.not_defer_debt = not_defer_debt;
    }
    public String getPay_method() {
        return pay_method;
    }
    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }
    public String getPension_cd() {
        return pension_cd;
    }
    public void setPension_cd(String pension_cd) {
        this.pension_cd = pension_cd;
    }
    public String getReg_dt() {
        return reg_dt;
    }
    public void setReg_dt(String reg_dt) {
        this.reg_dt = reg_dt;
    }
    public String getRepay_years() {
        return repay_years;
    }
    public void setRepay_years(String repay_years) {
        this.repay_years = repay_years;
    }
    public String getSaving_gubn() {
        return saving_gubn;
    }
    public void setSaving_gubn(String saving_gubn) {
        this.saving_gubn = saving_gubn;
    }
    public String getStart_dt() {
        return start_dt;
    }
    public void setStart_dt(String start_dt) {
        this.start_dt = start_dt;
    }
    public String getSubject_nm() {
        return subject_nm;
    }
    public void setSubject_nm(String subject_nm) {
        this.subject_nm = subject_nm;
    }
    public String getSum() {
        return sum;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }
    public String getTax_year_amt() {
        return tax_year_amt;
    }
    public void setTax_year_amt(String tax_year_amt) {
        this.tax_year_amt = tax_year_amt;
    }
    public String getThis_year_rede_amt() {
        return this_year_rede_amt;
    }
    public void setThis_year_rede_amt(String this_year_rede_amt) {
        this.this_year_rede_amt = this_year_rede_amt;
    }
    public ArrayList<AmtOxm> getAmtOxmList() {
        return AmtOxmList;
    }
    public void setAmtOxmList(ArrayList<AmtOxm> amtOxmList) {
        AmtOxmList = amtOxmList;
    }
    
    
}
