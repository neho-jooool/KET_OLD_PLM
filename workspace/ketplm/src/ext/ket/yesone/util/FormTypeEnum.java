package ext.ket.yesone.util;

import java.util.ArrayList;

import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.yesone.entity.KETCashRctDTO;
import ext.ket.yesone.entity.KETCreditDTO;
import ext.ket.yesone.entity.KETDebitCardDTO;
import ext.ket.yesone.entity.KETDepositDTO;
import ext.ket.yesone.entity.KETDepositPvtDTO;
import ext.ket.yesone.entity.KETDonationDTO;
import ext.ket.yesone.entity.KETEduDTO;
import ext.ket.yesone.entity.KETHouseLandDTO;
import ext.ket.yesone.entity.KETHouseMtgDTO;
import ext.ket.yesone.entity.KETHousePrpDTO;
import ext.ket.yesone.entity.KETInsuranceDTO;
import ext.ket.yesone.entity.KETInvestSecuDTO;
import ext.ket.yesone.entity.KETJobTrainingDTO;
import ext.ket.yesone.entity.KETLargeLeaseDTO;
import ext.ket.yesone.entity.KETMedicalDTO;
import ext.ket.yesone.entity.KETRetirePsDTO;
import ext.ket.yesone.entity.KETSchoolExpenseDTO;
import ext.ket.yesone.entity.KETSmallCopDTO;
import ext.ket.yesone.entity.KETStockDTO;
import ext.ket.yesone.entity.KETUniformDTO;

public enum FormTypeEnum {

    /**
    // 서식코드
     A102Y => 보장성 보험, 장애인전용보장성보험
     B101Y => 의료비
     C101Y => 교육비(2016년 귀속) (X)
     C102Y => 교육비
     C202Y => 직업훈련비
     C301Y => 교복구입비
     C401Y => 교육비(학자금대출) 2017년 추가
     D101Y => 개인연금저축
     E102Y => 연금저축
     F102Y => 퇴직연금
     G104Y => 신용카드
     G205M => 현금영수증
     G304Y => 직불카드등
     J101Y => 주택임차차입금 원리금상환액   (X)
     J203Y => 장기주택저당차입금 이자상환액 (X)
     J301Y => 주택마련저축
     J401Y => 목돈 안드는 전세 이자상환액 (X)
     K101M => 소기업소상공인 공제부금 (X)
     L102Y => 기부금
     N101Y => 장기집합투자증권저축
     M101Y => 장기주식형저축 (X)
     O101M => 건강보험료     (X)
     P101M => 국민연금보험료 (X)
     */
	
	/*
	 * 국세청 서식코드 변경내역
	 * 2020년
	 * 
	 * 신규추가 : B201Y (실손의료보험금)
	 *
	 * 1.신용카드     : G107Y -> G108M
	 * 2.현금영수증  : G207M -> G208M 
	 * 3.직불카드     : G307Y -> G308M
	 * 4.제로페이     : G407Y -> G408M 
	 *
	 * 2019년 신규추가
	 *  
	 * 1.제로페이 : G407Y
	 * 
	 * 2018년
	 * 
	 * 1.신용카드		: G106Y -> G107Y
	 * 2.현금영수증 : G206Y -> G207M
	 * 3.직불카드   : G306Y -> G307Y
	 * 
	 * 2017년
	 * 
	 * 1.교육비(학자금 대출) 신규 추가 -> C401Y
	 * 2.교육비       : C101Y -> C102Y
	 * 2.신용카드		: G104Y -> G106Y
	 * 3.현금영수증	: G205M -> G206M
	 * 4.직불카드		: G304Y -> G306Y
	 * 
	 *  
	 * 
	 * 2016년
	 * 
	 * 1.신용카드                : G103Y -> G104Y
	 * 2.현금영수증              : G204M -> G205M 
	 * 3.직불카드                : G303Y -> G304Y
	 * 4.기부금                  : L101Y -> L102Y
	 * 
	 * 
	 */
    
    
    보험("A102Y", 		KETInsuranceDTO.class.getName() , false,   "form_cd, resid, chasu, emp_no, year, site, acc_no",   		false,	"HCLRN12"),
    의료("B101Y", 		KETMedicalDTO.class.getName(), false,      "form_cd, resid, chasu, emp_no, year, site, busnid",   		false,	"HCLRN14"),
    실손의료보험("B201Y", 		KETInsuranceDTO.class.getName() , false,   "form_cd, resid, chasu, emp_no, year, site, acc_no",   		false,	"HCLRN12"),
    교육("C102Y", 		KETEduDTO.class.getName(), false,          "form_cd, resid, chasu, emp_no, year, site, busnid",   		false,	"HCLRN12"),
    직업훈련("C202Y", 		KETJobTrainingDTO.class.getName(), false,  "form_cd, resid, chasu, emp_no, year, site, busnid, course_cd",	false, 	"HCLRN12"),
    교복구입("C301Y", 		KETUniformDTO.class.getName(), false,      "form_cd, resid, chasu, emp_no, year, site, busnid",			false, 	"HCLRN12"),
    학자금대출("C401Y", 		KETSchoolExpenseDTO.class.getName(), false, "form_cd, resid, chasu, emp_no, year, site, busnid",			false, 	"HCLRN12"),
    개인연금저축("D101Y", 	KETDepositPvtDTO.class.getName(), false,   "form_cd, resid, chasu, emp_no, year, site, busnid, acc_no",		true, 	"HCLRN20"),  
    연금저축("E102Y", 		KETDepositDTO.class.getName(), false,      "form_cd, resid, chasu, emp_no, year, site, busnid, acc_no",		true, 	"HCLRN20"),
    퇴직연금("F102Y", 		KETRetirePsDTO.class.getName(), false,     "form_cd, resid, chasu, emp_no, year, site, busnid, acc_no",		true, 	"HCLRN20"),
    신용카드("G108M", 		KETCreditDTO.class.getName(), false,       "form_cd, resid, chasu, emp_no, year, site, busnid, use_place_cd",	false, 	"HCLRN17"),
    현금영수증("G208M", 		KETCashRctDTO.class.getName(), true,       "form_cd, resid, chasu, emp_no, year, site, use_place_cd",		false, 	"HCLRN12"),
    직불카드등("G308M", 		KETDebitCardDTO.class.getName(), false,    "form_cd, resid, chasu, emp_no, year, site, busnid, use_place_cd",	false,	"HCLRN12"),
    제로페이("G408M", 		KETDebitCardDTO.class.getName(), false,    "form_cd, resid, chasu, emp_no, year, site, busnid, use_place_cd",	false,	"HCLRN12"),
    주택임차차입금("J101Y", 	KETHouseLandDTO.class.getName(), false,    "form_cd, resid, chasu, emp_no, year, site, busnid, acc_no",		false, 	""),  //사용안함
    장기주택저당차입금("J203Y", 	KETHouseMtgDTO.class.getName(), false,     "form_cd, resid, chasu, emp_no, year, site, busnid, acc_no",		false, 	""),//사용안함  
    주택마련저축("J301Y", 	KETHousePrpDTO.class.getName(), false,     "form_cd, resid, chasu, emp_no, year, site, busnid, acc_no",		true, 	"HCLRN20"),
    전세이자상환액("J401Y", 	KETLargeLeaseDTO.class.getName(), false,   "form_cd, resid, chasu, emp_no, year, site, busnid, acc_no",		false, 	""),  //사용안함
    소기업소상공인("K101M", 	KETSmallCopDTO.class.getName(), false,     "form_cd, resid, chasu, emp_no, year, site, acc_no",			false, 	""), //사용안함
    기부금("L102Y", 		KETDonationDTO.class.getName(), true,      "form_cd, resid, chasu, emp_no, year, site, busnid, donation_cd",	false,	"HCLRN16"),  
    장기집합투자증권저축("N101Y", 	KETInvestSecuDTO.class.getName() , false,  "form_cd, resid, chasu, emp_no, year, site, busnid, secu_no", 	true,	"HCLRN20"),
    장기주식형저축("M101Y",       KETStockDTO.class.getName(), false,        "form_cd, resid, chasu, emp_no, year, site, busnid",			false,	""), //국세청 서비스 종료
    건강보험료("O101M",       KETStockDTO.class.getName(), false,        "form_cd, resid, chasu, emp_no, year, site, busnid",			false,	""), //사용안함
    국민연금보험료("P101M",       KETStockDTO.class.getName(), false,        "form_cd, resid, chasu, emp_no, year, site, busnid",			false,	""), //사용안함
    국민연금보험료_2017("P102M",       KETStockDTO.class.getName(), false,        "form_cd, resid, chasu, emp_no, year, site, busnid",			false,	""); //사용안함
    
    private String formCode;
    private Object object;
    private boolean IsChild;
    private String dbkey;
    private boolean IsDeposit;
    private String LegacyTBInfo;
    
    private FormTypeEnum (String formCode, Object object, boolean IsChild, String dbkey, boolean IsDeposit, String LegacyTBInfo){
	this.formCode = formCode; //서식코드
	this.object = object;     //서식코드에 따른 자바 오브젝트
	this.IsChild = IsChild;   //xml구조에서 4레벨 <amt mm> attribute와 value를 가진 경우 설정(basedto에서 AmtOxm.java를 ArrayList 변수로 갖는다)
	this.dbkey = dbkey; //관련 테이블의 Primarykey 설정시 사용 
	this.IsDeposit = IsDeposit;//개인연금저축, 연금저축,퇴직연금, 장기집합투자증권저축,주택마련저축 구분 위함
	this.LegacyTBInfo = LegacyTBInfo; //레가시 연말정산 테이블명
    }
    
    public static FormTypeEnum[] toPartTypeEnum(){
	
	FormTypeEnum[] arry = FormTypeEnum.values();
	return arry;
    }
    
    public static FormTypeEnum getFormTypeEnum(String formCode){
	if(formCode == null) return null;
	
	FormTypeEnum ret = null;
	
	FormTypeEnum[] arry = FormTypeEnum.values();
	for( FormTypeEnum item : arry){
	    if(formCode.equalsIgnoreCase(item.getFormCode())){
		ret = item;
		break;
	    }
	}
	
	return ret;
    }
    
    public static FormTypeEnum IsChild(String formCode){
	if(formCode == null) return null;
	
	FormTypeEnum ret = null;
	
	FormTypeEnum[] arry = FormTypeEnum.values();
	for( FormTypeEnum item : arry){
	    if(formCode.equalsIgnoreCase(item.getFormCode())){
		ret = item;
		break;
	    }
	}
	
	return ret;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public Object getObject() {
	
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isIsChild() {
        return IsChild;
    }

    public void setIsChild(boolean isChild) {
        IsChild = isChild;
    }
    
    public String getDbkey() {
        return dbkey;
    }

    public void setDbkey(String dbkey) {
        this.dbkey = dbkey;
    }

    public boolean isIsDeposit() {
        return IsDeposit;
    }

    public void setIsDeposit(boolean isDeposit) {
        IsDeposit = isDeposit;
    }

    public String getLegacyTBInfo() {
        return LegacyTBInfo;
    }

    public void setLegacyTBInfo(String legacyTBInfo) {
        LegacyTBInfo = legacyTBInfo;
    }

    public static String getClassName(Object object){
	return object.getClass().getName();
    }
    
}
