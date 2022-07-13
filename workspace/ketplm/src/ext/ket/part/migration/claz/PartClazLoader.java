package ext.ket.part.migration.claz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class PartClazLoader extends AbstractExcelLoader {

    private List<MigPartClazDTO> list = new ArrayList<MigPartClazDTO>();
    private List<MigPartClazPropLinkDTO> plist = new ArrayList<MigPartClazPropLinkDTO>();
    private Map<String, MigPartClazDTO> map = new HashMap<String, MigPartClazDTO>();
    private ExcelHandle excel;

    public void print(List<MigPartClazDTO> list) {

	for (MigPartClazDTO dto : list) {

	    // Kogger.debug(getClass(), dto);
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("").append(dto.getLevel()).append(", ").append(dto.getParentKeyCode()).append(", ").append(dto.getClassKrName())
		    .append(", ").append(dto.getProdGubun()).append(", ").append(dto.getEpmCode()).append(", ")
		    .append(dto.getErpProdGroupCode()).append(", ").append(dto.getErpProdGroupDesc()).append(", ")
		    .append(dto.getErpProdCode()).append(", ").append(dto.getErpProdDesc()).append(", ").append(dto.getClassPartType())
		    .append(", ").append(dto.getNumberingCode()).append(", ").append(dto.getNumberingCharics()).append(", ")
		    .append(dto.getSortNo()).append(", ").append(dto.getUseClaz()).append(", ").append(dto.getCatalogueCode()).append(", ")
		    .append(dto.getPartClassificType()).append(", ").append(dto.getPartNamingCode()).append(dto.getPartNamingCall())
		    .append(", ").append(dto.getErpClassCode()).append(", ").append(dto.getDivisionName()).append(dto.getClassNo())
		    .append("");

	    Kogger.debug(getClass(), buffer.toString());
	}
    }
    
    public void printP(List<MigPartClazPropLinkDTO> list) {
	
	for (MigPartClazPropLinkDTO dto : list) {
	    
	    // Kogger.debug(getClass(), dto);
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("").append(dto.getKeyCode()).append(", ").append(dto.getAttrCode()).append(", ").append(dto.getAttrName())
	    .append(", ").append(dto.getEsse()).append(", ").append(dto.getCheck()).append(", ")
	    .append(dto.getCol()).append(", ").append(dto.getRow()).append(", ")
	    .append("");
	    
	    Kogger.debug(getClass(), buffer.toString());
	}
    }

    public void load(String filePath) throws Exception {

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = 0;
	excel.setSheet(sheetNo);
	int startRowNo = 4;
	excel.setRow(startRowNo);
	int rowSize = 324; // excel.getSheetLastRowNum();

	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);

	    MigPartClazDTO dto = makeValue();

	    if (StringUtils.isEmpty(dto.getKeyCode()) || StringUtils.isEmpty(dto.getClassKrName())
	    // || "AS-IS".equals(dto.getCodeTypeEn()) || "AS-IS".equals(dto.getCodeTypeEn())
	    // || dto.getCodeTypeCode().indexOf("분석") != -1 || dto.getCodeTypeCode().indexOf("중복") != -1
	    ) {
		continue;
	    }

	    map.put(dto.getKeyCode(), dto);
	    list.add(dto);
	}
	
	print(list);
	// classification save
	for( MigPartClazDTO cdto : list){
	    // TKLEE CLAZ SAVE
	}
	
	// 속성 매핑
	sheetNo = 3;
	excel.setSheet(sheetNo);
	startRowNo = 3; // 분류체계 KEY 위치
	excel.setRow(startRowNo);
	
	final int term = 2; // 왼쪽에 비는 COLUMN 수
	final int clazCout = 321;
	int colSize = term+clazCout; // excel.getSheetLastRowNum();
	String[] keyCodeArry = new String[clazCout];

	for (int i = term+1; i <= colSize; i++) {
	    keyCodeArry[i-(term+1)] = excel.getStrIntValue(i);
	}
	
	startRowNo = 6;
	rowSize = 601; // 마지막 속성이 있는 줄 NO-1 excel.getSheetLastRowNum();
	for (int i = startRowNo; i <= rowSize; i = i+4) {
	    excel.setRow(i);
	    
	    //Kogger.debug(getClass(), "## attrCode:" + i + ":" + excel.getCurrentRow().getRowNum() + " : " + excel.getCurrentCol().getColumnIndex());
	    
	    String attrCode = excel.getStrValue(0);
	    String attrName = excel.getStrValue(1);
	    
	    for (int j = (term+1); j <= colSize; j++) {
		
		MigPartClazPropLinkDTO pdto =  new MigPartClazPropLinkDTO();
		pdto.setKeyCode(keyCodeArry[j-(term+1)]);
		pdto.setAttrCode(attrCode);
		//pdto.setClazOid(clazOid);
		pdto.setAttrName(attrName);
		excel.setRow(i);
		pdto.setEsse(excel.getStrValue(j));
		excel.setRow(i+1);
		pdto.setCheck(excel.getStrValue(j));
		excel.setRow(i+2);
		if(StringUtils.isNotEmpty(pdto.getEsse()) && StringUtils.isNotEmpty(pdto.getCheck())){
		    pdto.setCol("10");
		    pdto.setRow("100");
		}
		
//		pdto.setCol(excel.getStrIntValue(j));
//		excel.setRow(i+3);
//		pdto.setRow(excel.getStrIntValue(j));
//		excel.setRow(i);
		
		if( StringUtils.isEmpty(pdto.getEsse()) && StringUtils.isNotEmpty(pdto.getCheck()) ){
		    Kogger.debug(getClass(), "## attrCode:" + pdto.getKeyCode() + ":" + pdto.getAttrCode() );
		    throw new Exception("Esse Check Error1 !");
		}
		
		if( StringUtils.isNotEmpty(pdto.getEsse()) && StringUtils.isEmpty(pdto.getCheck()) ){
		    Kogger.debug(getClass(), "## attrCode:" + pdto.getKeyCode() + ":" + pdto.getAttrCode() );
		    throw new Exception("Esse Check Error2 !");
		}

		if (StringUtils.isEmpty(pdto.getKeyCode()) || StringUtils.isEmpty(pdto.getAttrCode())
			|| StringUtils.isEmpty(pdto.getEsse()) || StringUtils.isEmpty(pdto.getCheck())
			//|| StringUtils.isEmpty(pdto.getCol()) || StringUtils.isEmpty(pdto.getRow())
		) {
    			continue;
		}

		plist.add(pdto);
	    }
	}
	
	printP(plist);
	
	// relation save ext.ket.part.entity.KETPartClassification:897950017
	for (MigPartClazDTO cdto : list) {
	    // TKLEE CLAZ RELATION SAVE
	}

    }

    private MigPartClazDTO makeValue() {
	
	MigPartClazDTO dto = new MigPartClazDTO();
	
	dto.setKeyCode(excel.getStrIntValue(MigClazEnum.KEY_CODE.getIndex())); // no
	dto.setParentKeyCode(excel.getStrIntValue(MigClazEnum.PARENT.getIndex()));
	
	dto.setLev1(excel.getStrValue(MigClazEnum.LEV1.getIndex()));
	dto.setLev2(excel.getStrValue(MigClazEnum.LEV2.getIndex()));
	dto.setLev3(excel.getStrValue(MigClazEnum.LEV3.getIndex()));
//	dto.setLev4(excel.getStrValue(MigClazEnum.LEV4.getIndex()));
	
	if (!"-".equals(dto.getLev3()) && StringUtils.isNotEmpty(dto.getLev3())) {
	    
	    dto.setLevel(3);
	    dto.setClassKrName(excel.getStrValue(MigClazEnum.LEV3.getIndex()));
	    //level3Key = dto.getKeyCode();
	    //dto.setParentKeyCode(level2Key);
	    
	} else if (!"-".equals(dto.getLev2()) && StringUtils.isNotEmpty(dto.getLev2())) {
	    
	    dto.setLevel(2);
	    dto.setClassKrName(excel.getStrValue(MigClazEnum.LEV2.getIndex()));
	    //level2Key = dto.getKeyCode();
	    //dto.setParentKeyCode(level1Key);
	    
	} else if (!"-".equals(dto.getLev1()) && StringUtils.isNotEmpty(dto.getLev1())) {
	    
	    dto.setLevel(1);
	    dto.setClassKrName(excel.getStrValue(MigClazEnum.LEV1.getIndex()));
	    //level1Key = dto.getKeyCode();
	    //dto.setParentKeyCode("");
	} 
	
//	else if (!"-".equals(dto.getLev4()) && StringUtils.isNotEmpty(dto.getLev4())) {
//	    
//	    dto.setLevel(4);
//	    dto.setClassKrName(excel.getStrValue(MigClazEnum.LEV4.getIndex()));
//	    level4Key = dto.getKeyCode();
//	    dto.setParentKeyCode(level3Key);
//	    
//	}
	
	if(dto.getClassKrName() != null && dto.getClassKrName().indexOf("\r\n")!= -1){
	    dto.setClassKrName(dto.getClassKrName().replace("\r\n", ""));
	}
	
	// dto.setClassKrName(excel.getStrValue(MigClazEnum.LEV1.getIndex())); //LEV2 LEV3 LEV4
	dto.setClassEnName(excel.getStrValue(MigClazEnum.CLAZ_ENG.getIndex()));
	dto.setClassZhName(excel.getStrValue(MigClazEnum.CLAZ_CH.getIndex()));
	
	dto.setEpmCode(excel.getStrValue(MigClazEnum.EPM_CODE.getIndex()));
	dto.setErpProdGroupCode(excel.getStrValue(MigClazEnum.PRODG_CODE.getIndex())); // no
	if(dto.getErpProdGroupCode() != null && dto.getErpProdGroupCode().endsWith(".0")){
	    dto.setErpProdGroupCode(dto.getErpProdGroupCode().substring(0, dto.getErpProdGroupCode().length()-2));
	}
	
	if(dto.getErpProdGroupCode() != null && ( "_".equals(dto.getErpProdGroupCode()) || "-".equals(dto.getErpProdGroupCode()) )){
	    dto.setErpProdGroupCode("");
	}
	
	dto.setErpProdGroupDesc(excel.getStrValue(MigClazEnum.PRODG_DESC.getIndex()));
	dto.setErpProdCode(excel.getStrValue(MigClazEnum.ERP_FERT_CODE.getIndex())); // no
	if(dto.getErpProdCode() != null && dto.getErpProdCode().endsWith(".0")){
	    dto.setErpProdCode(dto.getErpProdCode().substring(0, dto.getErpProdCode().length()-2));
	}
	
	if(dto.getErpProdCode() != null && ( "_".equals(dto.getErpProdCode()) || "-".equals(dto.getErpProdCode()) )){
	    dto.setErpProdCode("");
	}
	
	dto.setErpProdDesc(excel.getStrValue(MigClazEnum.ERP_FERT_DESC.getIndex()));
	dto.setOpinion(excel.getStrValue(MigClazEnum.BIGO.getIndex()));
	dto.setClassPartType(excel.getStrValue(MigClazEnum.CLASS_PART_TYPE.getIndex())); // no
	if(dto.getClassPartType() != null && ( "_".equals(dto.getClassPartType()) || "-".equals(dto.getClassPartType()) )){
	    dto.setClassPartType("");
	}
	
	if(dto.getClassPartType() != null && dto.getClassPartType().endsWith(".0")){
	    dto.setClassPartType(dto.getClassPartType().substring(0, dto.getClassPartType().length()-2));
	}

//	dto.setErpHalbDesc(excel.getStrValue(MigClazEnum.ERP_HALB_DESC.getIndex()));
	dto.setNumberingCode(excel.getStrValue(MigClazEnum.NUMBERING_CODE.getIndex())); // no
	if(dto.getNumberingCode() != null && dto.getNumberingCode().endsWith(".0")){
	    dto.setNumberingCode(dto.getNumberingCode().substring(0, dto.getNumberingCode().length()-2));
	}
	if(dto.getNumberingCode() != null && ( "_".equals(dto.getNumberingCode()) || "-".equals(dto.getNumberingCode()) )){
	    dto.setNumberingCode("");
	}
	
	// 특성 type 추가
	dto.setNumberingCharics(excel.getStrValue(MigClazEnum.NUMBERING_CHARICS.getIndex())); // no
	if(dto.getNumberingCharics() != null && dto.getNumberingCharics().endsWith(".0")){
	    dto.setNumberingCharics(dto.getNumberingCharics().substring(0, dto.getNumberingCharics().length()-2));
	}
	if(dto.getNumberingCharics() != null && ( "_".equals(dto.getNumberingCharics()) || "-".equals(dto.getNumberingCharics()) )){
	    dto.setNumberingCharics("");
	}
	
	dto.setSortNo(excel.getStrIntValue(MigClazEnum.SORTING.getIndex())); // no
	if(dto.getSortNo() != null && dto.getSortNo().endsWith(".0")){
	    dto.setSortNo(dto.getSortNo().substring(0, dto.getSortNo().length()-2));
	}
	dto.setUseClaz(excel.getStrIntValue(MigClazEnum.USE_AT.getIndex()));
	dto.setCatalogueCode(excel.getStrValue(MigClazEnum.CATALOGUE_CODE.getIndex())); // no
	if(dto.getCatalogueCode() != null && dto.getCatalogueCode().endsWith(".0")){
	    dto.setCatalogueCode(dto.getCatalogueCode().substring(0, dto.getCatalogueCode().length()-2));
	}
	if(dto.getCatalogueCode() != null && ( dto.getCatalogueCode().endsWith("E7") || dto.getCatalogueCode().endsWith("e7"))){
	    dto.setCatalogueCode(dto.getCatalogueCode().substring(0, dto.getCatalogueCode().length()-2));
	}
	if(dto.getCatalogueCode() != null && ( dto.getCatalogueCode().indexOf(".") != -1)){
	    dto.setCatalogueCode(dto.getCatalogueCode().replace(".", ""));
	}
	if(dto.getCatalogueCode() != null && ( "_".equals(dto.getCatalogueCode()) || "-".equals(dto.getCatalogueCode()) )){
	    dto.setCatalogueCode("");
	}
	
	dto.setPartClassificType(excel.getStrValue(MigClazEnum.PART_CLAZ_TYPE.getIndex()));
	dto.setPartNamingCode(excel.getStrValue(MigClazEnum.PART_NAMING_CODE.getIndex())); // no
	if(dto.getPartNamingCode() != null && dto.getPartNamingCode().endsWith(".0")){
	    dto.setPartNamingCode(dto.getPartNamingCode().substring(0, dto.getPartNamingCode().length()-2));
	}
	
	if(dto.getPartNamingCode() != null && ( "_".equals(dto.getPartNamingCode()) || "-".equals(dto.getPartNamingCode()) )){
	    dto.setPartNamingCode("");
	}
	
	dto.setPartNamingCall(excel.getStrValue(MigClazEnum.PART_NAMING_CALL.getIndex()));
	dto.setErpClassCode(excel.getStrValue(MigClazEnum.ERP_CLAZ_NO.getIndex())); // no
	
	if(dto.getErpClassCode() != null && ( "_".equals(dto.getErpClassCode()) || "-".equals(dto.getErpClassCode()) )){
	    dto.setErpClassCode("");
	}
	
	dto.setDivisionName(excel.getStrValue(MigClazEnum.BIZ_NAME.getIndex()));
	dto.setClassNo(excel.getStrValue(MigClazEnum.CLAZ_NO.getIndex()));
	
	return dto;
    }

    public List<MigPartClazDTO> getList() {
	return list;
    }

    public Map<String, MigPartClazDTO> getMap() {
	return map;
    }

    public List<MigPartClazPropLinkDTO> getPList() {
	return plist;
    }
    
    public static void main(String[] args) throws Exception {

	PartClazLoader loader = new PartClazLoader();
//	String filePath = "D:\\MigrationData.xlsx";
	String filePath = "Z:\\90.개인폴더\\이응진\\부품사양관리\\업로드포멧\\업로드작업\\제품분류_V1.1_MigrationData.xlsx";
	loader.load(filePath);
    }

}