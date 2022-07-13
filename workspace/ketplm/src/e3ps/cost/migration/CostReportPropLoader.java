package e3ps.cost.migration;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.StringUtil;
import ext.ket.bom.matersum.util.BigDecimalUtil;
import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

final public class CostReportPropLoader extends AbstractExcelLoader {

    private ExcelHandle excel;
    private List<CostReportPropDTO> propList = new ArrayList<CostReportPropDTO>();

    public void printDTO(CostReportPropDTO dto) throws Exception {
	Kogger.debug(getClass(), dto.toString());
    }

    // 특정 Directory의 파일을 찾아오기
    private File[] findFileByPattern(String dirPath, String prefix, String suffix) throws Exception {

	final String _prefix = prefix.toLowerCase();
	final String _suffix = suffix.toLowerCase();
	File rootDir = new File(dirPath);

	File[] matchingFiles = rootDir.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return name.toLowerCase().startsWith(_prefix) && name.toLowerCase().endsWith(_suffix);
	    }
	});

	return matchingFiles;
    }

    public void load(String filePath, String fileType, int sheetNo) throws Exception {

	File[] fileArray = findFileByPattern(filePath, fileType, "");
	if(fileArray.length < 1){
	    System.out.println("************************ Error!! 해당 경로에 파일이 존재하지 않습니다. "+filePath+ " ************************");
	}
	for (File file : fileArray) {
	    loadInner(filePath + File.separator + file.getName(), sheetNo);
	}
    }

    public void loadInner(String filePath, int _sheetNo) throws Exception {

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = _sheetNo;
	excel.setSheet(sheetNo);
	int startRowNo = 1;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	System.out.println("******** Excel loading Start [ rowSize : "+rowSize+" ]");	
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);

	    /*if (
		StringUtils.isEmpty(excel.getStrValue(CostReportPropEnum.MAST_OID.getIndex())) || 
		StringUtils.isEmpty(excel.getStrValue(CostReportPropEnum.REV_OID.getIndex())) || 
		StringUtils.isEmpty(excel.getStrValue(CostReportPropEnum.ITER_OID.getIndex())) || 
	        StringUtils.isEmpty(excel.getStrValue(CostReportPropEnum.PART_NO.getIndex())) || 
		StringUtils.isEmpty(excel.getStrValue(CostReportPropEnum.PART_REV.getIndex()))
		    ) {
		continue;
	    }*/
	    CostReportPropDTO dto = null;
	    try{
		dto = makeProdValue();
	    }catch(Exception e){
		e.printStackTrace();
		System.out.println("오류 발생 row : " + i + " 번째 "+excel.getStrValue(CostReportPropEnum.ProjectNo.getIndex()));
	    }
	    //Kogger.debug(getClass(), dto.toString());
	    propList.add(dto);
	}
	System.out.println("******** Excel loading End");	
    }

    private CostReportPropDTO makeProdValue() throws Exception {

	CostReportPropDTO dto = new CostReportPropDTO();
	
	dto.setA_Name(excel.getStrValue(CostReportPropEnum.A_Name.getIndex()));
	dto.setRevision(excel.getStrValue(CostReportPropEnum.Revision.getIndex())); //리비전
	dto.setDrStep(excel.getStrValue(CostReportPropEnum.DrStep.getIndex()));   //DR단계
	dto.setDevStep(excel.getStrValue(CostReportPropEnum.DevStep.getIndex())); //개발단계
	dto.setProjectNo(excel.getStrValue(CostReportPropEnum.ProjectNo.getIndex())); 
	dto.setProductName(excel.getStrValue(CostReportPropEnum.ProductName.getIndex()));
	dto.setCartype(excel.getStrValue(CostReportPropEnum.Cartype.getIndex()));     //차종
	dto.setCustomer_f(excel.getStrValue(CostReportPropEnum.Customer_f.getIndex()));  //고객사
	dto.setApp_part(excel.getStrValue(CostReportPropEnum.App_part.getIndex()));    //적용부위
	dto.setA_Name(excel.getStrValue(CostReportPropEnum.A_Name.getIndex()));      //영업담당
	dto.setF_DeptName(excel.getStrValue(CostReportPropEnum.F_DeptName.getIndex()));  //개발부서
	dto.setF_Name(excel.getStrValue(CostReportPropEnum.F_Name.getIndex()));     //개발담당
	dto.setE_Name(excel.getStrValue(CostReportPropEnum.E_Name.getIndex()));     //원가담당
	dto.setReport_dest(excel.getStrValue(CostReportPropEnum.Report_dest.getIndex())); //물류흐름
	dto.setRequest_txt(excel.getStrValue(CostReportPropEnum.Request_txt.getIndex())); //검토목적
	dto.setMold_count(excel.getStrValue(CostReportPropEnum.Mold_count.getIndex()));  //Mold 수량
	dto.setPress_count(excel.getStrValue(CostReportPropEnum.Press_count.getIndex())); //Press 수량
	dto.setLine_count(excel.getStrValue(CostReportPropEnum.Line_count.getIndex()));  //조립설비 수량
	dto.setPack_count(excel.getStrValue(CostReportPropEnum.Pack_count.getIndex()));   //기타투자비 수량
	dto.setRepack_count(excel.getStrValue(CostReportPropEnum.Repack_count.getIndex())); //포장용 투자비 수량
	dto.setCase_to_note_1(excel.getStrValue(CostReportPropEnum.Case_to_note_1.getIndex())); //신규투자비 검토현황 1안
	dto.setMold_total_1(excel.getStrValue(CostReportPropEnum.Mold_total_1.getIndex()));   //Mold 1안(천원 단위)
	dto.setPress_total_1(excel.getStrValue(CostReportPropEnum.Press_total_1.getIndex()));  //Press 1안(천원 단위)
	dto.setLine_total_1(excel.getStrValue(CostReportPropEnum.Line_total_1.getIndex()));   //조립설비 1안(천원 단위
	dto.setPack_total_1(excel.getStrValue(CostReportPropEnum.Pack_total_1.getIndex()));   //기타투자비 1안
	dto.setRepack_total_1(excel.getStrValue(CostReportPropEnum.Repack_total_1.getIndex())); //포장용 투자비 1안(천원 단위)
	dto.setCase_to_note_2(excel.getStrValue(CostReportPropEnum.Case_to_note_2.getIndex())); //신규투자비 검토현황 2안
	dto.setMold_total_2(excel.getStrValue(CostReportPropEnum.Mold_total_2.getIndex()));   //Mold 2안(천원 단위)
	dto.setPress_total_2(excel.getStrValue(CostReportPropEnum.Press_total_2.getIndex())); //Press 2안(천원 단위)
	dto.setLine_total_2(excel.getStrValue(CostReportPropEnum.Line_total_2.getIndex()));   //조립설비 2안(천원 단위
	dto.setPack_total_2(excel.getStrValue(CostReportPropEnum.Pack_total_2.getIndex()));   //기타투자비 2안
	dto.setRepack_total_2(excel.getStrValue(CostReportPropEnum.Repack_total_2.getIndex())); //포장용 투자비 2안(천원 단위)
	dto.setCase_to_note_3(excel.getStrValue(CostReportPropEnum.Case_to_note_3.getIndex())); //신규투자비 검토현황 3안
	dto.setMold_total_3(excel.getStrValue(CostReportPropEnum.Mold_total_3.getIndex()));   //Mold 3안(천원 단위)
	dto.setPress_total_3(excel.getStrValue(CostReportPropEnum.Press_total_3.getIndex()));  //Press 3안(천원 단위)
	dto.setLine_total_3(excel.getStrValue(CostReportPropEnum.Line_total_3.getIndex()));   //조립설비 3안(천원 단위
	dto.setPack_total_3(excel.getStrValue(CostReportPropEnum.Pack_total_3.getIndex()));   //기타투자비 3안
	dto.setRepack_total_3(excel.getStrValue(CostReportPropEnum.Repack_total_3.getIndex())); //포장용 투자비 3안(천원 단위)
	dto.setTocost_year(excel.getStrValue(CostReportPropEnum.Tocost_year.getIndex()));    //투자비 회수기간
	dto.setSu_year_1(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_1.getIndex()),"1000",2));      //1년차 판매량(천개)
	dto.setTotal_sales_1(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_1.getIndex()),"1000000",0));  //1년차 매출액(백만원)
	dto.setProfit_1(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_1.getIndex()),"1000000",0),0));       //1년차 영업이익(백만원)
	dto.setPer_profit_1(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_1.getIndex()),"100",1));   //1년차 영업이익률
	
	
	dto.setSu_year_2(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_2.getIndex()),"1000",2));      //2년차 판매량(천개)
	dto.setTotal_sales_2(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_2.getIndex()),"1000000",0));  //2년차 매출액(백만원)
	dto.setProfit_2(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_2.getIndex()),"1000000",0),0));       //2년차 영업이익(백만원)
	dto.setPer_profit_2(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_2.getIndex()),"100",1));   //2년차 영업이익률
	
	dto.setSu_year_3(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_3.getIndex()),"1000",2));      //3년차 판매량(천개)
	dto.setTotal_sales_3(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_3.getIndex()),"1000000",0));  //3년차 매출액(백만원)
	dto.setProfit_3(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_3.getIndex()),"1000000",0),0));       //3년차 영업이익(백만원)
	dto.setPer_profit_3(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_3.getIndex()),"100",1));   //3년차 영업이익률
	
	dto.setSu_year_4(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_4.getIndex()),"1000",2));      //4년차 판매량(천개)
	dto.setTotal_sales_4(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_4.getIndex()),"1000000",0));  //4년차 매출액(백만원)
	dto.setProfit_4(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_4.getIndex()),"1000000",0),0));       //4년차 영업이익(백만원)
	dto.setPer_profit_4(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_4.getIndex()),"100",1));   //4년차 영업이익률
	
	dto.setSu_year_5(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_5.getIndex()),"1000",2));      //5년차 판매량(천개)
	dto.setTotal_sales_5(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_5.getIndex()),"1000000",0));  //5년차 매출액(백만원)
	dto.setProfit_5(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_5.getIndex()),"1000000",0),0));       //5년차 영업이익(백만원)
	dto.setPer_profit_5(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_5.getIndex()),"100",1));   //5년차 영업이익률
	
	dto.setSu_year_6(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_6.getIndex()),"1000",2));      //6년차 판매량(천개)
	dto.setTotal_sales_6(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_6.getIndex()),"1000000",0));  //6년차 매출액(백만원)
	dto.setProfit_6(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_6.getIndex()),"1000000",0),0));       //6년차 영업이익(백만원)
	dto.setPer_profit_6(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_6.getIndex()),"100",1));   //6년차 영업이익률
	
	dto.setSu_year_7(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_7.getIndex()),"1000",2));      //7년차 판매량(천개)
	dto.setTotal_sales_7(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_7.getIndex()),"1000000",0));  //7년차 매출액(백만원)
	dto.setProfit_7(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_7.getIndex()),"1000000",0),0));       //7년차 영업이익(백만원)
	dto.setPer_profit_7(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_7.getIndex()),"100",1));   //7년차 영업이익률
	
	dto.setSu_year_8(numberConvert(excel.getStrValue(CostReportPropEnum.Su_year_8.getIndex()),"1000",2));      //8년차 판매량(천개)
	dto.setTotal_sales_8(numberConvert(excel.getStrValue(CostReportPropEnum.Total_sales_8.getIndex()),"1000000",0));  //8년차 매출액(백만원)
	dto.setProfit_8(setScale(numberConvert(excel.getStrValue(CostReportPropEnum.Profit_8.getIndex()),"1000000",0),0));       //8년차 영업이익(백만원)
	dto.setPer_profit_8(numberConvert(excel.getStrValue(CostReportPropEnum.Per_profit_8.getIndex()),"100",1));   //8년차 영업이익률
	
	
	dto.setLme_cu(excel.getStrValue(CostReportPropEnum.Lme_cu.getIndex()));         //비철 LME 시세[＄/Ton]
	dto.setU_ex_rate(excel.getStrValue(CostReportPropEnum.U_ex_rate.getIndex()));      //비철 LME 시세[ ￦/$ ]
	dto.setPack_type(excel.getStrValue(CostReportPropEnum.Pack_type.getIndex()));       //포장유형
	dto.setPro_1(excel.getStrValue(CostReportPropEnum.Pro_1.getIndex()));        //생산처 구분
	dto.setNote_1(excel.getStrValue(CostReportPropEnum.Note_1.getIndex()));        //비고 1
	dto.setEff_value(excel.getStrValue(CostReportPropEnum.Eff_value.getIndex()));       //생산효율
	
	dto.setT001_part_name(excel.getStrValue(CostReportPropEnum.T001_part_name.getIndex()));   // T001_제품명 
	dto.setT001_pro_usage(excel.getStrValue(CostReportPropEnum.T001_pro_usage.getIndex()));   // T001_적용U/S
	dto.setT001_ket_cost(excel.getStrValue(CostReportPropEnum.T001_ket_cost.getIndex()));     // T001_판매 목표가
	dto.setT001_target_cost(excel.getStrValue(CostReportPropEnum.T001_target_cost.getIndex()));      // T001_목표 수익률
	dto.setT001_case_1_note(excel.getStrValue(CostReportPropEnum.T001_case_1_note.getIndex()));      // T001_1안 note
	dto.setT001_actual_cost_1(excel.getStrValue(CostReportPropEnum.T001_actual_cost_1.getIndex()));  // T001_1안 총원가
	dto.setT001_earn_rate_1(setScale(excel.getStrValue(CostReportPropEnum.T001_earn_rate_1.getIndex()),1));      // T001_1안 수익률
	dto.setT001_case_2_note(excel.getStrValue(CostReportPropEnum.T001_case_2_note.getIndex()));      // T001_2안 note
	dto.setT001_actual_cost_2(excel.getStrValue(CostReportPropEnum.T001_actual_cost_2.getIndex()));  // T001_2안 총원가
	dto.setT001_earn_rate_2(setScale(excel.getStrValue(CostReportPropEnum.T001_earn_rate_2.getIndex()),1));      // T001_2안 수익률
	dto.setT001_case_3_note(excel.getStrValue(CostReportPropEnum.T001_case_3_note.getIndex()));      // T001_3안 note
	dto.setT001_actual_cost_3(excel.getStrValue(CostReportPropEnum.T001_actual_cost_3.getIndex()));  // T001_3안 총원가
	dto.setT001_earn_rate_3(setScale(excel.getStrValue(CostReportPropEnum.T001_earn_rate_3.getIndex()),1));      // T001_3안 수익률
	dto.setNote_2(excel.getStrValue(CostReportPropEnum.note_2.getIndex()));                          //비고2
	    
	dto.setT002_part_name(excel.getStrValue(CostReportPropEnum.T002_part_name.getIndex()));         // T002_제품명 
	dto.setT002_pro_usage(excel.getStrValue(CostReportPropEnum.T002_pro_usage.getIndex()));         // T002_적용U/S
	dto.setT002_ket_cost(excel.getStrValue(CostReportPropEnum.T002_ket_cost.getIndex()));           // T002_판매 목표가
	dto.setT002_target_cost(excel.getStrValue(CostReportPropEnum.T002_target_cost.getIndex()));     // T002_목표 수익률
	//dto.setT002_case_1_note(excel.getStrValue(CostReportPropEnum.T002_case_1_note.getIndex()));      // T002_1안 note
	dto.setT002_actual_cost_1(excel.getStrValue(CostReportPropEnum.T002_actual_cost_1.getIndex()));  // T002_1안 총원가
	dto.setT002_earn_rate_1(setScale(excel.getStrValue(CostReportPropEnum.T002_earn_rate_1.getIndex()),1));      // T002_1안 수익률
	//dto.setT002_case_2_note(excel.getStrValue(CostReportPropEnum.T002_case_2_note.getIndex()));      // T002_2안 note
	dto.setT002_actual_cost_2(excel.getStrValue(CostReportPropEnum.T002_actual_cost_2.getIndex()));  // T002_2안 총원가
	dto.setT002_earn_rate_2(setScale(excel.getStrValue(CostReportPropEnum.T002_earn_rate_2.getIndex()),1));      // T002_2안 수익률
	//dto.setT002_case_3_note(excel.getStrValue(CostReportPropEnum.T002_case_3_note.getIndex()));      // T002_3안 note
	dto.setT002_actual_cost_3(excel.getStrValue(CostReportPropEnum.T002_actual_cost_3.getIndex()));  // T002_3안 총원가
	dto.setT002_earn_rate_3(setScale(excel.getStrValue(CostReportPropEnum.T002_earn_rate_3.getIndex()),1));      // T002_3안 수익률
	    
	dto.setT003_part_name(excel.getStrValue(CostReportPropEnum.T003_part_name.getIndex()));          // T003__제품명 
	dto.setT003_pro_usage(excel.getStrValue(CostReportPropEnum.T003_pro_usage.getIndex()));          // T003__적용U/S
	dto.setT003_ket_cost(excel.getStrValue(CostReportPropEnum.T003_ket_cost.getIndex()));            // T003__판매 목표가
	dto.setT003_target_cost(excel.getStrValue(CostReportPropEnum.T003_target_cost.getIndex()));      // T003__목표 수익률
	//dto.setT003_case_1_note(excel.getStrValue(CostReportPropEnum.T003_case_1_note.getIndex()));      // T003__1안 note
	dto.setT003_actual_cost_1(excel.getStrValue(CostReportPropEnum.T003_actual_cost_1.getIndex()));  // T003__1안 총원가
	dto.setT003_earn_rate_1(setScale(excel.getStrValue(CostReportPropEnum.T003_earn_rate_1.getIndex()),1));      // T003__1안 수익률
	//dto.setT003_case_2_note(excel.getStrValue(CostReportPropEnum.T003_case_2_note.getIndex()));      // T003__2안 note
	dto.setT003_actual_cost_2(excel.getStrValue(CostReportPropEnum.T003_actual_cost_2.getIndex()));  // T003__2안 총원가
	dto.setT003_earn_rate_2(setScale(excel.getStrValue(CostReportPropEnum.T003_earn_rate_2.getIndex()),1));      // T003__2안 수익률
	//dto.setT003_case_3_note(excel.getStrValue(CostReportPropEnum.T003_case_3_note.getIndex()));      // T003__3안 note
	dto.setT003_actual_cost_3(excel.getStrValue(CostReportPropEnum.T003_actual_cost_3.getIndex()));  // T003__3안 총원가
	dto.setT003_earn_rate_3(setScale(excel.getStrValue(CostReportPropEnum.T003_earn_rate_3.getIndex()),1));      // T003__3안 수익률
//	    MAST_OID(0),
//	    REV_OID(1),
//	    ITER_OID(2),
	
//	    PART_NO(3),
//	    PART_NAME(4),
//	    PART_REV(5),
//	    spPartType(11),
//	    PART_CLAZ(12),
//	    PART_DEV_LEVEL(13),
//	    spPartRevision(14);

	// .0으로 인식되는 것 제거
/*	if(dto.getPartNo() != null && dto.getPartNo().endsWith(".0")){
	    dto.setPartNo(dto.getPartNo().substring(0, dto.getPartNo().length()-2));
	}
	if(dto.getPartRev() != null && dto.getPartRev().endsWith(".0")){
	    dto.setPartRev(dto.getPartRev().substring(0, dto.getPartRev().length()-2));
	}
	if(dto.getPartKetRev() != null && dto.getPartKetRev().endsWith(".0")){
	    dto.setPartKetRev(dto.getPartKetRev().substring(0, dto.getPartKetRev().length()-2));
	}*/
	
	return dto;
    }
    
    public static void main(String[] args) throws Exception {

	CostReportPropLoader loader = new CostReportPropLoader();
	String filePath = "E:\\MigrationData\\part_tobeProp";
	String[] fileTypeArray = new String[] { "PART_TOBE_PROPS" };
	int maxSheet = 0;
	for (String fileType : fileTypeArray) {
	    for(int k=0; k<=maxSheet; k++){
		loader.load(filePath, fileType, 0);
		break;
	    }
	}
    }

    public List<CostReportPropDTO> getPropList() {
        return propList;
    }
    
    public String numberConvert(String up, String down,int scale){
	String val = "";
	if(!"".equals(StringUtil.checkNull(up)) && !"".equals(StringUtil.checkNull(down)) && Integer.parseInt(StringUtil.checkNullZero(down)) > 0 ){
	    BigDecimal up_   = new BigDecimal(up.toString());
            BigDecimal down_ = new BigDecimal(down.toString());
            BigDecimal temp = BigDecimalUtil.divideWithRoundUp(up_, down_, 5);
            //BigDecimal temp = up_.divide(down_, MathContext.DECIMAL32).setScale(scale,BigDecimal.ROUND_UNNECESSARY);
            val = temp.toString();
	}else{
	    val = up;
	}
	return val;
    }
    
    public String setScale(String up,int scale){
	String val = "";
	if(!"".equals(StringUtil.checkNull(up))){
	    BigDecimal up_   = new BigDecimal(up.toString());
	    val = up_.divide(BigDecimal.TEN.pow(0)).setScale(scale,RoundingMode.DOWN).toString();
	}else{
	    val = up;
	}
	return val;
    }

}