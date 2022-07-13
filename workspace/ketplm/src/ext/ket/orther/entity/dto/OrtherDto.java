package ext.ket.orther.entity.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class OrtherDto {
    private static final long serialVersionUID = 1L;
    
    private String divi_year;		//년도
    private String site;		//site
    private String emp_no;		//사번
    private String paym_sum;		//출자누계액
    private String kor_name;		//성명
    private String year_share_rate;	//연배당율
    private String divi_amt;		//연배당금
    private String mm_paym_amt;		//현출자금
    private String divi_yn;		//배당금 적치/회수 여부
    private String prin_yn;		//원금 회수 여부
    private String afr_mm_paym_amt;	//변경출자금
    private String saemal_acct_no; 	//계좌번호
    private String check_divi_yn;
    private String check_prin_yn;
    private String prin_amt;
    
    
    
    public String getPrin_amt() {
        return prin_amt;
    }



    public void setPrin_amt(String prin_amt) {
        this.prin_amt = prin_amt;
    }



    public String getCheck_divi_yn() {
        return check_divi_yn;
    }



    public void setCheck_divi_yn(String check_divi_yn) {
        this.check_divi_yn = check_divi_yn;
    }



    public String getCheck_prin_yn() {
        return check_prin_yn;
    }



    public void setCheck_prin_yn(String check_prin_yn) {
        this.check_prin_yn = check_prin_yn;
    }



    public String getDivi_year() {
        return divi_year;
    }



    public void setDivi_year(String divi_year) {
        this.divi_year = divi_year;
    }



    public String getSite() {
        return site;
    }



    public void setSite(String site) {
        this.site = site;
    }



    public String getEmp_no() {
        return emp_no;
    }



    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }



    public String getPaym_sum() {
        return paym_sum;
    }



    public void setPaym_sum(String paym_sum) {
        this.paym_sum = paym_sum;
    }



    public String getKor_name() {
        return kor_name;
    }



    public void setKor_name(String kor_name) {
        this.kor_name = kor_name;
    }



    public String getYear_share_rate() {
        return year_share_rate;
    }



    public void setYear_share_rate(String year_share_rate) {
        this.year_share_rate = year_share_rate;
    }



    public String getDivi_amt() {
        return divi_amt;
    }



    public void setDivi_amt(String divi_amt) {
        this.divi_amt = divi_amt;
    }



    public String getMm_paym_amt() {
        return mm_paym_amt;
    }



    public void setMm_paym_amt(String mm_paym_amt) {
        this.mm_paym_amt = mm_paym_amt;
    }



    public String getDivi_yn() {
        return divi_yn;
    }



    public void setDivi_yn(String divi_yn) {
        this.divi_yn = divi_yn;
    }



    public String getPrin_yn() {
        return prin_yn;
    }



    public void setPrin_yn(String prin_yn) {
        this.prin_yn = prin_yn;
    }



    public String getAfr_mm_paym_amt() {
        return afr_mm_paym_amt;
    }



    public void setAfr_mm_paym_amt(String afr_mm_paym_amt) {
        this.afr_mm_paym_amt = afr_mm_paym_amt;
    }

    
    public String getSaemal_acct_no() {
        return saemal_acct_no;
    }


    public void setSaemal_acct_no(String saemal_acct_no) {
        this.saemal_acct_no = saemal_acct_no;
    }



    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
