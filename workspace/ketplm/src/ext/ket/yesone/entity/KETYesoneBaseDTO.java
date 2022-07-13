package ext.ket.yesone.entity;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ext.ket.yesone.xom.AmtOxm;

public class KETYesoneBaseDTO{
    /* !! 주의 !! 
     * 멤버변수는 추가하지 말것. 만약 추가한다면 서식코드별로 쪼개진 db의 모든 table에 똑같이 추가해줘야한다. 
     * 
     * 멤버변수 추가시 반드시 마지막 부터 입력해야 하며
     * (주석의 custom 변수 start 아래로 입력할것)
     * 
     * YesoneDao.java의 import2DtoMappingData 메서드에 종속되어있으므로
     * 추가한 멤버변수가 있을시 반드시 import2DtoMappingData메서드를 수정해야함
     * 
     *   
     *   */
    private String form_cd; //서식코드;
    
    private String resid; //주민등록번호;
    
    private String name; //성명;
    
    private ArrayList<AmtOxm> MM_List;
    
    //custom 변수 start
    private String chasu; //PDF 업로드 차수
    
    private String emp_no; //사번
    
    private String year; //연말정산 년도
    
    private String site; //사업장 구분
    
    //custom 변수 end    
    
    public KETYesoneBaseDTO() {
    }

    public String getForm_cd() {
        return form_cd;
    }

    public void setForm_cd(String form_cd) {
        this.form_cd = form_cd;
    }

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
    
    
    public ArrayList<AmtOxm> getMM_List() {
        return MM_List;
    }

    public void setMM_List(ArrayList<AmtOxm> mM_List) {
        MM_List = mM_List;
    }

    public String getChasu() {
        return chasu;
    }

    public void setChasu(String chasu) {
        this.chasu = chasu;
    }
    
    public String getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
