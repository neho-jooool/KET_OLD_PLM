package e3ps.cost.migration;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.aspectj.util.LangUtil.StringChecker;

import java.math.BigDecimal;

import e3ps.common.util.StringUtil;
import ext.ket.bom.matersum.util.BigDecimalUtil;

public class CostReportPropDTO {

    private String Revision; //리비전
    private String DrStep;   //DR단계
    private String DevStep;  //개발단계
    private String ProjectNo; 
    private String ProductName;
    private String Cartype;     //차종
    private String Customer_f;  //고객사
    private String App_part;    //적용부위
    private String A_Name;      //영업담당
    private String F_DeptName;  //개발부서
    private String F_Name;     //개발담당
    private String E_Name;     //원가담당
    private String Report_dest; //물류흐름
    private String Request_txt; //검토목적
    private String Mold_count;  //Mold 수량
    private String Press_count; //Press 수량
    private String Line_count;  //조립설비 수량
    private String Pack_count;   //기타투자비 수량
    private String Repack_count; //포장용 투자비 수량
    private String Case_to_note_1; //신규투자비 검토현황 1안
    private String Mold_total_1;   //Mold 1안(천원 단위)
    private String Press_total_1;  //Press 1안(천원 단위)
    private String Line_total_1;   //조립설비 1안(천원 단위
    private String Pack_total_1;   //기타투자비 1안
    private String Repack_total_1; //포장용 투자비 1안(천원 단위)
    private String Case_to_note_2; //신규투자비 검토현황 2안
    private String Mold_total_2;   //Mold 2안(천원 단위)
    private String Press_total_2; //Press 2안(천원 단위)
    private String Line_total_2;   //조립설비 2안(천원 단위
    private String Pack_total_2;   //기타투자비 2안
    private String Repack_total_2; //포장용 투자비 2안(천원 단위)
    private String Case_to_note_3; //신규투자비 검토현황 3안
    private String Mold_total_3;   //Mold 3안(천원 단위)
    private String Press_total_3;  //Press 3안(천원 단위)
    private String Line_total_3;   //조립설비 3안(천원 단위
    private String Pack_total_3;   //기타투자비 3안
    private String Repack_total_3; //포장용 투자비 3안(천원 단위)
    private String Tocost_year;    //투자비 회수기간
    private String Su_year_1;      //1년차 판매량(천개)
    private String Total_sales_1;  //1년차 매출액(백만원)
    private String Profit_1;       //1년차 영업이익(백만원)
    private String Per_profit_1;   //1년차 영업이익률
    private String Su_year_2;      //2년차 판매량(천개)
    private String Total_sales_2;  //2년차 매출액(백만원)
    private String Profit_2;       //2년차 영업이익(백만원)
    private String Per_profit_2;   //2년차 영업이익률
    private String Su_year_3;      //3년차 판매량(천개)
    private String Total_sales_3;  //3년차 매출액(백만원)
    private String Profit_3;       //3년차 영업이익(백만원)
    private String Per_profit_3;   //3년차 영업이익률
    private String Su_year_4;      //4년차 판매량(천개)
    private String Total_sales_4;  //4년차 매출액(백만원)
    private String Profit_4;       //4년차 영업이익(백만원)
    private String Per_profit_4;   //4년차 영업이익률
    private String Su_year_5;      //5년차 판매량(천개)
    private String Total_sales_5;  //5년차 매출액(백만원)
    private String Profit_5;       //5년차 영업이익(백만원)
    private String Per_profit_5;   //5년차 영업이익률
    private String Su_year_6;      //6년차 판매량(천개)
    private String Total_sales_6;  //6년차 매출액(백만원)
    private String Profit_6;       //6년차 영업이익(백만원)
    private String Per_profit_6;   //6년차 영업이익률
    private String Su_year_7;      //7년차 판매량(천개)
    private String Total_sales_7;  //7년차 매출액(백만원)
    private String Profit_7;       //7년차 영업이익(백만원)
    private String Per_profit_7;   //7년차 영업이익률
    private String Su_year_8;      //8년차 판매량(천개)
    private String Total_sales_8;  //8년차 매출액(백만원)
    private String Profit_8;       //8년차 영업이익(백만원)
    private String Per_profit_8;   //8년차 영업이익률
    private String Lme_cu;         //비철 LME 시세[＄/Ton]
    private String U_ex_rate;      //비철 LME 시세[ ￦/$ ]
    private String Pack_type;       //포장유형
    private String Pro_1;		//생산처 구분
    private String Note_1;        //비고 1
    private String Eff_value;       //생산효율
    
    private String T001_part_name;      // T001_제품명 
    private String T001_pro_usage;      // T001_적용U/S
    private String T001_ket_cost;       // T001_판매 목표가
    private String T001_target_cost;    // T001_목표 수익률
    private String T001_case_1_note;    // T001_1안 note
    private String T001_actual_cost_1;  // T001_1안 총원가
    private String T001_earn_rate_1;    // T001_1안 수익률
    private String T001_case_2_note;    // T001_2안 note
    private String T001_actual_cost_2;  // T001_2안 총원가
    private String T001_earn_rate_2;    // T001_2안 수익률
    private String T001_case_3_note;    // T001_3안 note
    private String T001_actual_cost_3;  // T001_3안 총원가
    private String T001_earn_rate_3;    // T001_3안 수익률
    private String note_2;              //비고2
    
    private String T002_part_name;      // T002_제품명 
    private String T002_pro_usage;      // T002_적용U/S
    private String T002_ket_cost;       // T002_판매 목표가
    private String T002_target_cost;    // T002_목표 수익률
    private String T002_case_1_note;    // T002_1안 note
    private String T002_actual_cost_1;  // T002_1안 총원가
    private String T002_earn_rate_1;    // T002_1안 수익률
    private String T002_case_2_note;    // T002_2안 note
    private String T002_actual_cost_2;  // T002_2안 총원가
    private String T002_earn_rate_2;    // T002_2안 수익률
    private String T002_case_3_note;    // T002_3안 note
    private String T002_actual_cost_3;  // T002_3안 총원가
    private String T002_earn_rate_3;    // T002_3안 수익률
    
    private String T003_part_name;      // T003__제품명 
    private String T003_pro_usage;      // T003__적용U/S
    private String T003_ket_cost;       // T003__판매 목표가
    private String T003_target_cost;    // T003__목표 수익률
    private String T003_case_1_note;    // T003__1안 note
    private String T003_actual_cost_1;  // T003__1안 총원가
    private String T003_earn_rate_1;    // T003__1안 수익률
    private String T003_case_2_note;    // T003__2안 note
    private String T003_actual_cost_2;  // T003__2안 총원가
    private String T003_earn_rate_2;    // T003__2안 수익률
    private String T003_case_3_note;    // T003__3안 note
    private String T003_actual_cost_3;  // T003__3안 총원가
    private String T003_earn_rate_3;    // T003__3안 수익률
    
    
    private String pk_crp = ""; //cost_report pk 보고서
    private String pk_wid = ""; //wf_info pk  결재
    
    
    public String getPk_crp() {
        return pk_crp;
    }



    public void setPk_crp(String pk_crp) {
        this.pk_crp = pk_crp;
    }


    public String getPk_wid() {
        return pk_wid;
    }



    public void setPk_wid(String pk_wid) {
        this.pk_wid = pk_wid;
    }



    public String getRevision() {
        return Revision;
    }



    public void setRevision(String revision) {
        Revision = revision;
    }



    public String getDrStep() {
        return DrStep;
    }



    public void setDrStep(String drStep) {
        DrStep = drStep;
    }



    public String getDevStep() {
        return DevStep;
    }



    public void setDevStep(String devStep) {
        DevStep = devStep;
    }



    public String getProjectNo() {
        return ProjectNo;
    }



    public void setProjectNo(String projectNo) {
        ProjectNo = projectNo;
    }



    public String getProductName() {
        return ProductName;
    }



    public void setProductName(String productName) {
        ProductName = productName;
    }



    public String getCartype() {
        return Cartype;
    }



    public void setCartype(String cartype) {
        Cartype = cartype;
    }



    public String getCustomer_f() {
        return Customer_f;
    }



    public void setCustomer_f(String customer_f) {
        Customer_f = customer_f;
    }



    public String getApp_part() {
        return App_part;
    }



    public void setApp_part(String app_part) {
        App_part = app_part;
    }



    public String getA_Name() {
        return A_Name;
    }



    public void setA_Name(String a_Name) {
        A_Name = a_Name;
    }



    public String getF_DeptName() {
        return F_DeptName;
    }



    public void setF_DeptName(String f_DeptName) {
        F_DeptName = f_DeptName;
    }



    public String getF_Name() {
        return F_Name;
    }



    public void setF_Name(String f_Name) {
        F_Name = f_Name;
    }



    public String getE_Name() {
        return E_Name;
    }



    public void setE_Name(String e_Name) {
        E_Name = e_Name;
    }



    public String getReport_dest() {
        return Report_dest;
    }



    public void setReport_dest(String report_dest) {
        Report_dest = report_dest;
    }



    public String getRequest_txt() {
        return Request_txt;
    }



    public void setRequest_txt(String request_txt) {
        Request_txt = request_txt;
    }



    public String getMold_count() {
        return Mold_count;
    }



    public void setMold_count(String mold_count) {
        Mold_count = mold_count;
    }



    public String getPress_count() {
        return Press_count;
    }



    public void setPress_count(String press_count) {
        Press_count = press_count;
    }



    public String getLine_count() {
        return Line_count;
    }



    public void setLine_count(String line_count) {
        Line_count = line_count;
    }



    public String getPack_count() {
        return Pack_count;
    }



    public void setPack_count(String pack_count) {
        Pack_count = pack_count;
    }



    public String getRepack_count() {
        return Repack_count;
    }



    public void setRepack_count(String repack_count) {
        Repack_count = repack_count;
    }



    public String getCase_to_note_1() {
        return Case_to_note_1;
    }



    public void setCase_to_note_1(String case_to_note_1) {
        Case_to_note_1 = case_to_note_1;
    }



    public String getMold_total_1() {
        return Mold_total_1;
    }



    public void setMold_total_1(String mold_total_1) {
        Mold_total_1 = mold_total_1;
    }



    public String getPress_total_1() {
        return Press_total_1;
    }



    public void setPress_total_1(String press_total_1) {
        Press_total_1 = press_total_1;
    }



    public String getLine_total_1() {
        return Line_total_1;
    }



    public void setLine_total_1(String line_total_1) {
        Line_total_1 = line_total_1;
    }



    public String getPack_total_1() {
        return Pack_total_1;
    }



    public void setPack_total_1(String pack_total_1) {
        Pack_total_1 = pack_total_1;
    }



    public String getRepack_total_1() {
        return Repack_total_1;
    }



    public void setRepack_total_1(String repack_total_1) {
        Repack_total_1 = repack_total_1;
    }



    public String getCase_to_note_2() {
        return Case_to_note_2;
    }



    public void setCase_to_note_2(String case_to_note_2) {
        Case_to_note_2 = case_to_note_2;
    }



    public String getMold_total_2() {
        return Mold_total_2;
    }



    public void setMold_total_2(String mold_total_2) {
        Mold_total_2 = mold_total_2;
    }



    public String getPress_total_2() {
        return Press_total_2;
    }



    public void setPress_total_2(String press_total_2) {
        Press_total_2 = press_total_2;
    }



    public String getLine_total_2() {
        return Line_total_2;
    }



    public void setLine_total_2(String line_total_2) {
        Line_total_2 = line_total_2;
    }



    public String getPack_total_2() {
        return Pack_total_2;
    }



    public void setPack_total_2(String pack_total_2) {
        Pack_total_2 = pack_total_2;
    }



    public String getRepack_total_2() {
        return Repack_total_2;
    }



    public void setRepack_total_2(String repack_total_2) {
        Repack_total_2 = repack_total_2;
    }



    public String getCase_to_note_3() {
        return Case_to_note_3;
    }



    public void setCase_to_note_3(String case_to_note_3) {
        Case_to_note_3 = case_to_note_3;
    }



    public String getMold_total_3() {
        return Mold_total_3;
    }



    public void setMold_total_3(String mold_total_3) {
        Mold_total_3 = mold_total_3;
    }



    public String getPress_total_3() {
        return Press_total_3;
    }



    public void setPress_total_3(String press_total_3) {
        Press_total_3 = press_total_3;
    }



    public String getLine_total_3() {
        return Line_total_3;
    }



    public void setLine_total_3(String line_total_3) {
        Line_total_3 = line_total_3;
    }



    public String getPack_total_3() {
        return Pack_total_3;
    }



    public void setPack_total_3(String pack_total_3) {
        Pack_total_3 = pack_total_3;
    }



    public String getRepack_total_3() {
        return Repack_total_3;
    }



    public void setRepack_total_3(String repack_total_3) {
        Repack_total_3 = repack_total_3;
    }



    public String getTocost_year() {
        return Tocost_year;
    }



    public void setTocost_year(String tocost_year) {
        Tocost_year = tocost_year;
    }



    public String getSu_year_1() {
        return Su_year_1;
    }



    public void setSu_year_1(String su_year_1) {
        Su_year_1 = su_year_1;
        
    }



    public String getTotal_sales_1() {
        return Total_sales_1;
    }



    public void setTotal_sales_1(String total_sales_1) {
        Total_sales_1 = total_sales_1;
    }



    public String getProfit_1() {
        return Profit_1;
    }



    public void setProfit_1(String profit_1) {
        Profit_1 = profit_1;
    }



    public String getPer_profit_1() {
        return Per_profit_1;
    }



    public void setPer_profit_1(String per_profit_1) {
        Per_profit_1 = per_profit_1;
    }



    public String getSu_year_2() {
        return Su_year_2;
    }



    public void setSu_year_2(String su_year_2) {
        Su_year_2 = su_year_2;
    }



    public String getTotal_sales_2() {
        return Total_sales_2;
    }



    public void setTotal_sales_2(String total_sales_2) {
        Total_sales_2 = total_sales_2;
    }



    public String getProfit_2() {
        return Profit_2;
    }



    public void setProfit_2(String profit_2) {
        Profit_2 = profit_2;
    }



    public String getPer_profit_2() {
        return Per_profit_2;
    }



    public void setPer_profit_2(String per_profit_2) {
        Per_profit_2 = per_profit_2;
    }



    public String getSu_year_3() {
        return Su_year_3;
    }



    public void setSu_year_3(String su_year_3) {
        Su_year_3 = su_year_3;
    }



    public String getTotal_sales_3() {
        return Total_sales_3;
    }



    public void setTotal_sales_3(String total_sales_3) {
        Total_sales_3 = total_sales_3;
    }



    public String getProfit_3() {
        return Profit_3;
    }



    public void setProfit_3(String profit_3) {
        Profit_3 = profit_3;
    }



    public String getPer_profit_3() {
        return Per_profit_3;
    }



    public void setPer_profit_3(String per_profit_3) {
        Per_profit_3 = per_profit_3;
    }



    public String getSu_year_4() {
        return Su_year_4;
    }



    public void setSu_year_4(String su_year_4) {
        Su_year_4 = su_year_4;
    }



    public String getTotal_sales_4() {
        return Total_sales_4;
    }



    public void setTotal_sales_4(String total_sales_4) {
        Total_sales_4 = total_sales_4;
    }



    public String getProfit_4() {
        return Profit_4;
    }



    public void setProfit_4(String profit_4) {
        Profit_4 = profit_4;
    }



    public String getPer_profit_4() {
        return Per_profit_4;
    }



    public void setPer_profit_4(String per_profit_4) {
        Per_profit_4 = per_profit_4;
    }



    public String getSu_year_5() {
        return Su_year_5;
    }



    public void setSu_year_5(String su_year_5) {
        Su_year_5 = su_year_5;
    }



    public String getTotal_sales_5() {
        return Total_sales_5;
    }



    public void setTotal_sales_5(String total_sales_5) {
        Total_sales_5 = total_sales_5;
    }



    public String getProfit_5() {
        return Profit_5;
    }



    public void setProfit_5(String profit_5) {
        Profit_5 = profit_5;
    }



    public String getPer_profit_5() {
        return Per_profit_5;
    }



    public void setPer_profit_5(String per_profit_5) {
        Per_profit_5 = per_profit_5;
    }



    public String getSu_year_6() {
        return Su_year_6;
    }



    public void setSu_year_6(String su_year_6) {
        Su_year_6 = su_year_6;
    }



    public String getTotal_sales_6() {
        return Total_sales_6;
    }



    public void setTotal_sales_6(String total_sales_6) {
        Total_sales_6 = total_sales_6;
    }



    public String getProfit_6() {
        return Profit_6;
    }



    public void setProfit_6(String profit_6) {
        Profit_6 = profit_6;
    }



    public String getPer_profit_6() {
        return Per_profit_6;
    }



    public void setPer_profit_6(String per_profit_6) {
        Per_profit_6 = per_profit_6;
    }



    public String getSu_year_7() {
        return Su_year_7;
    }



    public void setSu_year_7(String su_year_7) {
        Su_year_7 = su_year_7;
    }



    public String getTotal_sales_7() {
        return Total_sales_7;
    }



    public void setTotal_sales_7(String total_sales_7) {
        Total_sales_7 = total_sales_7;
    }



    public String getProfit_7() {
        return Profit_7;
    }



    public void setProfit_7(String profit_7) {
        Profit_7 = profit_7;
    }



    public String getPer_profit_7() {
        return Per_profit_7;
    }



    public void setPer_profit_7(String per_profit_7) {
        Per_profit_7 = per_profit_7;
    }



    public String getSu_year_8() {
        return Su_year_8;
    }



    public void setSu_year_8(String su_year_8) {
        Su_year_8 = su_year_8;
    }



    public String getTotal_sales_8() {
        return Total_sales_8;
    }



    public void setTotal_sales_8(String total_sales_8) {
        Total_sales_8 = total_sales_8;
    }



    public String getProfit_8() {
        return Profit_8;
    }



    public void setProfit_8(String profit_8) {
        Profit_8 = profit_8;
    }



    public String getPer_profit_8() {
        return Per_profit_8;
    }



    public void setPer_profit_8(String per_profit_8) {
        Per_profit_8 = per_profit_8;
    }



    public String getLme_cu() {
        return Lme_cu;
    }



    public void setLme_cu(String lme_cu) {
        Lme_cu = lme_cu;
    }



    public String getU_ex_rate() {
        return U_ex_rate;
    }



    public void setU_ex_rate(String u_ex_rate) {
        U_ex_rate = u_ex_rate;
    }



    public String getPack_type() {
        return Pack_type;
    }



    public void setPack_type(String pack_type) {
        Pack_type = pack_type;
    }



    public String getPro_1() {
        return Pro_1;
    }



    public void setPro_1(String pro_1) {
        Pro_1 = pro_1;
    }



    public String getNote_1() {
        return Note_1;
    }



    public void setNote_1(String note_1) {
        Note_1 = note_1;
    }



    public String getEff_value() {
        return Eff_value;
    }



    public void setEff_value(String eff_value) {
        Eff_value = eff_value;
    }


    public String getT001_part_name() {
        return T001_part_name;
    }



    public void setT001_part_name(String t001_part_name) {
        T001_part_name = t001_part_name;
    }



    public String getT001_pro_usage() {
        return T001_pro_usage;
    }



    public void setT001_pro_usage(String t001_pro_usage) {
        T001_pro_usage = t001_pro_usage;
    }



    public String getT001_ket_cost() {
        return T001_ket_cost;
    }



    public void setT001_ket_cost(String t001_ket_cost) {
        T001_ket_cost = t001_ket_cost;
    }



    public String getT001_target_cost() {
        return T001_target_cost;
    }



    public void setT001_target_cost(String t001_target_cost) {
        T001_target_cost = t001_target_cost;
    }



    public String getT001_case_1_note() {
        return T001_case_1_note;
    }



    public void setT001_case_1_note(String t001_case_1_note) {
        T001_case_1_note = t001_case_1_note;
    }



    public String getT001_actual_cost_1() {
        return T001_actual_cost_1;
    }



    public void setT001_actual_cost_1(String t001_actual_cost_1) {
        T001_actual_cost_1 = t001_actual_cost_1;
    }



    public String getT001_earn_rate_1() {
        return T001_earn_rate_1;
    }



    public void setT001_earn_rate_1(String t001_earn_rate_1) {
        T001_earn_rate_1 = t001_earn_rate_1;
    }



    public String getT001_case_2_note() {
        return T001_case_2_note;
    }



    public void setT001_case_2_note(String t001_case_2_note) {
        T001_case_2_note = t001_case_2_note;
    }



    public String getT001_actual_cost_2() {
        return T001_actual_cost_2;
    }



    public void setT001_actual_cost_2(String t001_actual_cost_2) {
        T001_actual_cost_2 = t001_actual_cost_2;
    }



    public String getT001_earn_rate_2() {
        return T001_earn_rate_2;
    }



    public void setT001_earn_rate_2(String t001_earn_rate_2) {
        T001_earn_rate_2 = t001_earn_rate_2;
    }



    public String getT001_case_3_note() {
        return T001_case_3_note;
    }



    public void setT001_case_3_note(String t001_case_3_note) {
        T001_case_3_note = t001_case_3_note;
    }



    public String getT001_actual_cost_3() {
        return T001_actual_cost_3;
    }



    public void setT001_actual_cost_3(String t001_actual_cost_3) {
        T001_actual_cost_3 = t001_actual_cost_3;
    }



    public String getT001_earn_rate_3() {
        return T001_earn_rate_3;
    }



    public void setT001_earn_rate_3(String t001_earn_rate_3) {
        T001_earn_rate_3 = t001_earn_rate_3;
    }



    public String getNote_2() {
        return note_2;
    }



    public void setNote_2(String note_2) {
        this.note_2 = note_2;
    }



    public String getT002_part_name() {
        return T002_part_name;
    }



    public void setT002_part_name(String t002_part_name) {
        T002_part_name = t002_part_name;
    }



    public String getT002_pro_usage() {
        return T002_pro_usage;
    }



    public void setT002_pro_usage(String t002_pro_usage) {
        T002_pro_usage = t002_pro_usage;
    }



    public String getT002_ket_cost() {
        return T002_ket_cost;
    }



    public void setT002_ket_cost(String t002_ket_cost) {
        T002_ket_cost = t002_ket_cost;
    }



    public String getT002_target_cost() {
        return T002_target_cost;
    }



    public void setT002_target_cost(String t002_target_cost) {
        T002_target_cost = t002_target_cost;
    }



    public String getT002_case_1_note() {
        return T002_case_1_note;
    }



    public void setT002_case_1_note(String t002_case_1_note) {
        T002_case_1_note = t002_case_1_note;
    }



    public String getT002_actual_cost_1() {
        return T002_actual_cost_1;
    }



    public void setT002_actual_cost_1(String t002_actual_cost_1) {
        T002_actual_cost_1 = t002_actual_cost_1;
    }



    public String getT002_earn_rate_1() {
        return T002_earn_rate_1;
    }



    public void setT002_earn_rate_1(String t002_earn_rate_1) {
        T002_earn_rate_1 = t002_earn_rate_1;
    }



    public String getT002_case_2_note() {
        return T002_case_2_note;
    }



    public void setT002_case_2_note(String t002_case_2_note) {
        T002_case_2_note = t002_case_2_note;
    }



    public String getT002_actual_cost_2() {
        return T002_actual_cost_2;
    }



    public void setT002_actual_cost_2(String t002_actual_cost_2) {
        T002_actual_cost_2 = t002_actual_cost_2;
    }



    public String getT002_earn_rate_2() {
        return T002_earn_rate_2;
    }



    public void setT002_earn_rate_2(String t002_earn_rate_2) {
        T002_earn_rate_2 = t002_earn_rate_2;
    }



    public String getT002_case_3_note() {
        return T002_case_3_note;
    }



    public void setT002_case_3_note(String t002_case_3_note) {
        T002_case_3_note = t002_case_3_note;
    }



    public String getT002_actual_cost_3() {
        return T002_actual_cost_3;
    }



    public void setT002_actual_cost_3(String t002_actual_cost_3) {
        T002_actual_cost_3 = t002_actual_cost_3;
    }



    public String getT002_earn_rate_3() {
        return T002_earn_rate_3;
    }



    public void setT002_earn_rate_3(String t002_earn_rate_3) {
        T002_earn_rate_3 = t002_earn_rate_3;
    }



    public String getT003_part_name() {
        return T003_part_name;
    }



    public void setT003_part_name(String t003_part_name) {
        T003_part_name = t003_part_name;
    }



    public String getT003_pro_usage() {
        return T003_pro_usage;
    }



    public void setT003_pro_usage(String t003_pro_usage) {
        T003_pro_usage = t003_pro_usage;
    }



    public String getT003_ket_cost() {
        return T003_ket_cost;
    }



    public void setT003_ket_cost(String t003_ket_cost) {
        T003_ket_cost = t003_ket_cost;
    }



    public String getT003_target_cost() {
        return T003_target_cost;
    }



    public void setT003_target_cost(String t003_target_cost) {
        T003_target_cost = t003_target_cost;
    }



    public String getT003_case_1_note() {
        return T003_case_1_note;
    }



    public void setT003_case_1_note(String t003_case_1_note) {
        T003_case_1_note = t003_case_1_note;
    }



    public String getT003_actual_cost_1() {
        return T003_actual_cost_1;
    }



    public void setT003_actual_cost_1(String t003_actual_cost_1) {
        T003_actual_cost_1 = t003_actual_cost_1;
    }



    public String getT003_earn_rate_1() {
        return T003_earn_rate_1;
    }



    public void setT003_earn_rate_1(String t003_earn_rate_1) {
        T003_earn_rate_1 = t003_earn_rate_1;
    }



    public String getT003_case_2_note() {
        return T003_case_2_note;
    }



    public void setT003_case_2_note(String t003_case_2_note) {
        T003_case_2_note = t003_case_2_note;
    }



    public String getT003_actual_cost_2() {
        return T003_actual_cost_2;
    }



    public void setT003_actual_cost_2(String t003_actual_cost_2) {
        T003_actual_cost_2 = t003_actual_cost_2;
    }



    public String getT003_earn_rate_2() {
        return T003_earn_rate_2;
    }



    public void setT003_earn_rate_2(String t003_earn_rate_2) {
        T003_earn_rate_2 = t003_earn_rate_2;
    }



    public String getT003_case_3_note() {
        return T003_case_3_note;
    }



    public void setT003_case_3_note(String t003_case_3_note) {
        T003_case_3_note = t003_case_3_note;
    }



    public String getT003_actual_cost_3() {
        return T003_actual_cost_3;
    }



    public void setT003_actual_cost_3(String t003_actual_cost_3) {
        T003_actual_cost_3 = t003_actual_cost_3;
    }



    public String getT003_earn_rate_3() {
        return T003_earn_rate_3;
    }



    public void setT003_earn_rate_3(String t003_earn_rate_3) {
        T003_earn_rate_3 = t003_earn_rate_3;
    }



    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
