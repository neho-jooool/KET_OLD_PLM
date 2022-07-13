package ext.ket.part.migration.lov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class PartSpecLovLoader extends AbstractExcelLoader {

    private List<MigPartLovDTO> list = new ArrayList<MigPartLovDTO>();
    private Map<String, MigPartLovDTO> map = new HashMap<String, MigPartLovDTO>();
    private ExcelHandle excel;

    public void print(List<MigPartLovDTO> list) {
	
	for (MigPartLovDTO dto : list) {
	    
	    //Kogger.debug(getClass(), dto);
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( dto.getGubun() )
	    .append(", ")
	    .append(dto.getCodeTypeKr())
	    .append(", ")
	    .append(dto.getCodeTypeEn())
	    .append(", ")
	    .append(dto.getCodeTypeCode())
	    .append(", ")
	    .append(dto.getCodeCode())
	    .append(", ")
	    .append(dto.getCodeName())
	    .append(", ")
	    .append(dto.getCodeParentCode())
	    .append(", ")
	    .append(dto.getCodeShortName())
	    .append(", ")
	    .append(dto.getCodeDesc())
	    .append(", ")
	    .append(dto.getCodeNameKr())
	    .append(", ")
	    .append(dto.getCodeNameEn())
	    .append(", ")
	    .append(dto.getCodeNameCn())
	    .append(", ")
	    .append(dto.getCodeSorting())
	    .append("");
	    
	    Kogger.debug(getClass(), buffer.toString());
	}
    }

    public void load(String filePath) throws Exception {

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = 2;
	excel.setSheet(sheetNo);
	int startRowNo = 4;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);	    
    
	    MigPartLovDTO dto = makeValue();
	    
	    if(
	       StringUtils.isEmpty(dto.getCodeTypeKr())
	    || StringUtils.isEmpty(dto.getCodeTypeCode())
	    || "AS-IS".equals(dto.getCodeTypeEn())
	    || "AS-IS".equals(dto.getCodeTypeEn())
	    || dto.getCodeTypeCode().indexOf("분석") != -1
	    || dto.getCodeTypeCode().indexOf("중복") != -1
	    ){
		continue;
	    }
	    
	    map.put(dto.getCodeTypeCode(), dto);
	    list.add(dto);
	}
	
	print(list);
    }

    private MigPartLovDTO makeValue() {

	MigPartLovDTO dto = new MigPartLovDTO();

	dto.setGubun(excel.getStrValue(MigPartLovEnum.GUBUN.getIndex()));
	dto.setCodeTypeKr(excel.getStrValue(MigPartLovEnum.CODETYPE_KR.getIndex()));
	dto.setCodeTypeEn(excel.getStrValue(MigPartLovEnum.CODETYPE_EN.getIndex()));
	dto.setCodeTypeCode(excel.getStrValue(MigPartLovEnum.CODETYPE_CODE.getIndex()));
	if(dto.getCodeTypeCode() != null && dto.getCodeTypeCode().endsWith(".0")){
	    dto.setCodeTypeCode(dto.getCodeTypeCode().substring(0, dto.getCodeTypeCode().length()-2));
	}
	
	dto.setCodeCode(excel.getStrValue(MigPartLovEnum.CODE_CODE.getIndex()));
	if(dto.getCodeCode() != null && dto.getCodeCode().endsWith(".0")){
	    dto.setCodeCode(dto.getCodeCode().substring(0, dto.getCodeCode().length()-2));
	}
	dto.setCodeName(excel.getStrValue(MigPartLovEnum.CODE_NAME.getIndex()));
	if(dto.getCodeName() != null && dto.getCodeName().endsWith(".0")){
	    dto.setCodeName(dto.getCodeName().substring(0, dto.getCodeName().length()-2));
	}
	dto.setCodeParentCode(excel.getStrValue(MigPartLovEnum.CODE_PARENT_CODE.getIndex()));
	if(dto.getCodeParentCode() != null && dto.getCodeParentCode().endsWith(".0")){
	    dto.setCodeParentCode(dto.getCodeParentCode().substring(0, dto.getCodeParentCode().length()-2));
	}
	dto.setCodeShortName(excel.getStrValue(MigPartLovEnum.CODE_SHORT_NAME.getIndex()));
	if(dto.getCodeShortName() != null && dto.getCodeShortName().endsWith(".0")){
	    dto.setCodeShortName(dto.getCodeShortName().substring(0, dto.getCodeShortName().length()-2));
	}
	dto.setCodeDesc(excel.getStrValue(MigPartLovEnum.CODE_DESC.getIndex()));
	if(dto.getCodeDesc() != null && dto.getCodeDesc().endsWith(".0")){
	    dto.setCodeDesc(dto.getCodeDesc().substring(0, dto.getCodeDesc().length()-2));
	}
	dto.setCodeNameKr(excel.getStrValue(MigPartLovEnum.CODE_NAME_KR.getIndex()));
	if(dto.getCodeNameKr() != null && dto.getCodeNameKr().endsWith(".0")){
	    dto.setCodeNameKr(dto.getCodeNameKr().substring(0, dto.getCodeNameKr().length()-2));
	}
	dto.setCodeNameEn(excel.getStrValue(MigPartLovEnum.CODE_NAME_EN.getIndex()));
	dto.setCodeNameCn(excel.getStrValue(MigPartLovEnum.CODE_NAME_CN.getIndex()));
	dto.setCodeSorting(excel.getStrIntValue(MigPartLovEnum.CODE_SORTING.getIndex()));
	
	return dto;
    }

    public List<MigPartLovDTO> getList() {
        return list;
    }

    public Map<String, MigPartLovDTO> getMap() {
        return map;
    }

    public static void main(String[] args) throws Exception {

	PartSpecLovLoader loader = new PartSpecLovLoader();
//	String filePath = "D:\\MigrationData.xlsx";
	String filePath = "Z:\\90.개인폴더\\이응진\\부품사양관리\\업로드포멧\\업로드작업\\제품분류_V1.1_MigrationData.xlsx";
	loader.load(filePath);
    }

}