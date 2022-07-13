package ext.ket.part.migration.spec;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class PartSpecLoader extends AbstractExcelLoader {

    private MigPartSpecEnum[] enumList = MigPartSpecEnum.values();
    private ExcelHandle excel;

    public void printEnum(List<MigPartSpecDTO> list) throws Exception {
	
	for (MigPartSpecDTO dto : list) {
	    
	    Kogger.debug(getClass(), "//" + dto.getAttrName() + " - " + getNextStr(dto.getEnumASIS()) );
	    if(StringUtils.isNotEmpty(dto.getDescription()))
		Kogger.debug(getClass(), "//" + getNextStr(dto.getDescription()) );
	    if(StringUtils.isNotEmpty(dto.getComment()))
		Kogger.debug(getClass(), "//" + getNextStr(dto.getComment()) );
	    
	    //Kogger.debug(getClass(), dto);
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( getCameStr(dto.getAttrCode()) )
	    .append("(")
	    .append(getStringType(dto.getAttrCode()))
	    .append(", ")
	    .append(getStringType(getNameDT(dto.getAttrName())))
	    .append(", ")
//	    .append(getStringType(dto.getAttrCode()+"TYPEINFOWTPART") ) // dto.getColumnName()
//	    .append(getStringType(dto.getColumnName()+"TYPEINFOWTPART") ) // dbColumnName
	    .append(getStringType(dto.getColumnName().replace("TypeInfoWTPart", "TYPEINFOWTPART")) ) // dbColumnName
	    .append(", ")
	    .append(getStringType(StringUtils.isEmpty(dto.getColumnName())?"":"typeInfoWTPart." + dto.getColumnName().replace("TypeInfoWTPart", "").toLowerCase())) // typeInfoWTPart.ptc_str_1
	    .append(", ")
	    .append(getStringType(dto.getAttrInputType()))
	    .append(", ")
	    .append(getStringType(dto.getNumberCodeTypeCode()))
	    .append(", ")
	    .append(getBooleanType(dto.isMultiSelect())) // multi Select
	    .append(", ")
	    .append(getBooleanType(dto.isNumberType())) // Number만 입력 가능
	    .append(", ")
	    .append(getBooleanType(dto.isNumberingRel())) // 채번 사용
	    .append(", ")
	    .append(getStringType(null)) // 기본 단위
	    .append(", ")
	    .append(getBooleanType(dto.isSendErp())) // Erp Send Receive
	    .append(", ")
	    .append(getStringType(null))
	    .append("),");
	    
	    Kogger.debug(getClass(), buffer.toString());
	}
    }
    
    public void printJavaDtoPropDefine(List<MigPartSpecDTO> list) throws Exception {
	// 주석
	// private String xxxx;
	// PartSpecSetter.setxxxx(wTPartTypeInfo, dto.getxxxx());
	
	for (MigPartSpecDTO dto : list) {
	    
	    if("spPartType".equals(dto.getAttrCode()) || "spPartDevLevel".equals(dto.getAttrCode()) )
		continue;
	    
	    Kogger.debug(getClass(), "// " + dto.getAttrName());
	    Kogger.debug(getClass(), "private String " + dto.getAttrCode() + ";");
	}
    }
    
    public void printJavaComUtilSetter(List<MigPartSpecDTO> list) throws Exception {
    
//	switch(partSpecEnum){
//	  case SpPartNameHis :
//	      setSpPartNameHis(wtPart, value);
//	      break;
//	}
	/*
	Kogger.debug(getClass(), "public static void setPartSpec(WTPart wtPart, PartSpecEnum partSpecEnum, String value) throws WTPropertyVetoException{");
	Kogger.debug(getClass(), "	switch(partSpecEnum){");
	Kogger.debug(getClass(), "    ");
	for (MigPartSpecDTO dto : list) {
	    
	    Kogger.debug(getClass(), "	  case " + getCameStr(dto.getAttrCode()) + " :");
	    Kogger.debug(getClass(), "	      set" + getCameStr(dto.getAttrCode()) + "(wtPart, value);");
	    Kogger.debug(getClass(), "	      break;");
	}
	
	Kogger.debug(getClass(), "	}");
	Kogger.debug(getClass(), "}");
	Kogger.debug(getClass(), "");
	Kogger.debug(getClass(), "public static void setPartSpecWithType(WTPartTypeInfo wtPartTypeInfo, PartSpecEnum partSpecEnum, String value) throws WTPropertyVetoException{");
	Kogger.debug(getClass(), "	switch(partSpecEnum){");
	Kogger.debug(getClass(), "    ");
	for (MigPartSpecDTO dto : list) {
	    
	    Kogger.debug(getClass(), "	  case " + getCameStr(dto.getAttrCode()) + " :");
	    Kogger.debug(getClass(), "	      set" + getCameStr(dto.getAttrCode()) + "(wtPartTypeInfo, value);");
	    Kogger.debug(getClass(), "	      break;");
	}
	
	Kogger.debug(getClass(), "	}");
	Kogger.debug(getClass(), "}");
        */
	// SpecUtil
//	    public static void setSpPartNameHis(WTPart wtPart, String spPartNameHis) throws WTPropertyVetoException{
//		WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface)wtPart;
//	        WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();
//	        wTPartTypeInfo.setPtc_str_1(spPartNameHis);
//	    }
//	    
//	    public static void setSpPartNameHis(WTPartTypeInfo wTPartTypeInfo, String spPartNameHis) throws WTPropertyVetoException{
//		wTPartTypeInfo.setPtc_str_1(spPartNameHis);
//	    }
	/*
	for (MigPartSpecDTO dto : list) {
	    
	    Kogger.debug(getClass(), "    ");
	    Kogger.debug(getClass(), "    private static void set" + getCameStr(dto.getAttrCode()) + "(WTPart wtPart, String "+ dto.getAttrCode() + ") throws WTPropertyVetoException{");
	    Kogger.debug(getClass(), "	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface)wtPart;");
	    Kogger.debug(getClass(), "	WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();");
	    Kogger.debug(getClass(), "	wTPartTypeInfo.set" + getCameStr(dto.getColumnName().toLowerCase()) + "("+ dto.getAttrCode() + ");");
	    Kogger.debug(getClass(), "	log.debug(\"" + dto.getAttrName() + ":" + getCameStr(dto.getAttrCode()) +  ":" + getCameStr(dto.getColumnName().toLowerCase()) + ":\"+" + dto.getAttrCode() + ");");
	    Kogger.debug(getClass(), "    }");
	    Kogger.debug(getClass(), "    ");
	    Kogger.debug(getClass(), "    private static void set" + getCameStr(dto.getAttrCode()) + "(WTPartTypeInfo wTPartTypeInfo, String "+ dto.getAttrCode() + ") throws WTPropertyVetoException{");
	    Kogger.debug(getClass(), "	wTPartTypeInfo.set" + getCameStr(dto.getColumnName().toLowerCase()) + "("+ dto.getAttrCode() + ");");
	    Kogger.debug(getClass(), "	log.debug(\"" + dto.getAttrName() + ":" + getCameStr(dto.getAttrCode()) +  ":" + getCameStr(dto.getColumnName().toLowerCase()) + ":\"+" + dto.getAttrCode() + ");");
	    Kogger.debug(getClass(), "    }");
	    Kogger.debug(getClass(), "    ");
	}
	*/
    }
    
    public void printJavaUtilComGetter(List<MigPartSpecDTO> list) throws Exception {
	
//	switch(partSpecEnum){
//	  case SpPartNameHis :
//	      setSpPartNameHis(wtPart, value);
//	      break;
//	}
	/*
	Kogger.debug(getClass(), "public static String getPartSpec(WTPart wtPart, PartSpecEnum partSpecEnum){");
	Kogger.debug(getClass(), "	String value = null;");
	Kogger.debug(getClass(), "	switch(partSpecEnum){");
	Kogger.debug(getClass(), "    ");
	for (MigPartSpecDTO dto : list) {
	    
	    Kogger.debug(getClass(), "	  case " + getCameStr(dto.getAttrCode()) + " :");
	    Kogger.debug(getClass(), "	      value = get" + getCameStr(dto.getAttrCode()) + "(wtPart);");
	    Kogger.debug(getClass(), "	      break;");
	}
	
	Kogger.debug(getClass(), "	}");
	Kogger.debug(getClass(), "    return StringUtil.checkNull(value);");
	Kogger.debug(getClass(), "}");
	Kogger.debug(getClass(), "");
	Kogger.debug(getClass(), "public static String getPartSpecWithType(WTPartTypeInfo wtPartTypeInfo, PartSpecEnum partSpecEnum){");
	Kogger.debug(getClass(), "	String value = null;");
	Kogger.debug(getClass(), "	switch(partSpecEnum){");
	Kogger.debug(getClass(), "    ");
	for (MigPartSpecDTO dto : list) {
	    
	    Kogger.debug(getClass(), "	  case " + getCameStr(dto.getAttrCode()) + " :");
	    Kogger.debug(getClass(), "	      value = get" + getCameStr(dto.getAttrCode()) + "(wtPartTypeInfo);");
	    Kogger.debug(getClass(), "	      break;");
	}
	
	Kogger.debug(getClass(), "	}");
	Kogger.debug(getClass(), "    return StringUtil.checkNull(value);");
	Kogger.debug(getClass(), "}");
        */
	// SpecUtil
//	    public static void setSpPartNameHis(WTPart wtPart, String spPartNameHis) throws WTPropertyVetoException{
//		WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface)wtPart;
//	        WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();
//	        wTPartTypeInfo.setPtc_str_1(spPartNameHis);
//	    }
//	    
//	    public static void setSpPartNameHis(WTPartTypeInfo wTPartTypeInfo, String spPartNameHis) throws WTPropertyVetoException{
//		wTPartTypeInfo.setPtc_str_1(spPartNameHis);
//	    }
	/*
	for (MigPartSpecDTO dto : list) {
	    
	    Kogger.debug(getClass(), "    ");
	    Kogger.debug(getClass(), "    private static String get" + getCameStr(dto.getAttrCode()) + "(WTPart wtPart){");
	    Kogger.debug(getClass(), "	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface)wtPart;");
	    Kogger.debug(getClass(), "	WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();");
	    Kogger.debug(getClass(), "	log.debug(\"" + dto.getAttrName() + ":" + getCameStr(dto.getAttrCode()) +  ":" + getCameStr(dto.getColumnName().toLowerCase()) + ":\"+" + "wTPartTypeInfo.get" + getCameStr(dto.getColumnName().toLowerCase()) + "()" + ");");
	    Kogger.debug(getClass(), "	return wTPartTypeInfo.get" + getCameStr(dto.getColumnName().toLowerCase()) + "();");
	    Kogger.debug(getClass(), "    }");
	    Kogger.debug(getClass(), "    ");
	    Kogger.debug(getClass(), "    private static String get" + getCameStr(dto.getAttrCode()) + "(WTPartTypeInfo wTPartTypeInfo){");
	    Kogger.debug(getClass(), "	log.debug(\"" + dto.getAttrName() + ":" + getCameStr(dto.getAttrCode()) +  ":" + getCameStr(dto.getColumnName().toLowerCase()) + ":\"+" + "wTPartTypeInfo.get" + getCameStr(dto.getColumnName().toLowerCase()) + "()" + ");");
	    Kogger.debug(getClass(), "	return wTPartTypeInfo.get" + getCameStr(dto.getColumnName().toLowerCase()) + "();");
	    Kogger.debug(getClass(), "    }");
	    Kogger.debug(getClass(), "    ");
	}
	*/
    }
    
    public void printPartBaseBuilderDtoSet(List<MigPartSpecDTO> list) throws Exception {
//	PartSpecSetter.setPartSpecWithType(wtPartTypeInfo, PartSpecEnum.SpConstrucMeth, dto.getSpConstrucMeth());
	for (MigPartSpecDTO dto : list) {
	    Kogger.debug(getClass(), "// " + dto.getAttrName());
	    Kogger.debug(getClass(), "PartSpecSetter.setPartSpecWithType(wtPartTypeInfo, PartSpecEnum." + getCameStr(dto.getAttrCode()) + ", dto.get" + getCameStr(dto.getAttrCode()) + "());");
	}
    }
    
    public void printPartBaseBuilderDtoGet(List<MigPartSpecDTO> list) throws Exception {
//	dto.setSpPartType(PartSpecGetter.getPartSpecWithType(wtPartTypeInfo, PartSpecEnum.SpPartType));
	for (MigPartSpecDTO dto : list) {
	    Kogger.debug(getClass(), "// " + dto.getAttrName());
	    Kogger.debug(getClass(), "dto.set" + getCameStr(dto.getAttrCode()) + "(PartSpecGetter.getPartSpecWithType(wtPartTypeInfo, PartSpecEnum." + getCameStr(dto.getAttrCode()) + "));");
	}
    }
    
    public void printPartUtilPartSpecEnumArray(List<MigPartSpecDTO> list) throws Exception {
	int idx = 0;
	for (MigPartSpecDTO dto : list) {
	    if(idx % 5 == 4)
		Kogger.debug(getClass(), "PartSpecEnum." + getCameStr(dto.getAttrCode()) + ", ");
	    else
		System.out.print("PartSpecEnum." + getCameStr(dto.getAttrCode()) + ", ");
	    
	    idx++;
	}
    }
    
    public void printPartListJavascript() throws Exception {
	//{Name : 'modifyDate', Align : 'Center', CanSort : '1', CanEdit : '0'}
	// primaryEpmNo : '주도면 NO',//
	int idx = 0;
	PartSpecEnum[] list = PartUtil.getPartSpecForSearch();
	for (PartSpecEnum dto : list) {
	    
//	    if(     !"spSeriesBulb".equals(dto.getAttrCode()) &&
//		    !"spTBulbConn".equals(dto.getAttrCode()) &&
//		    !"spT2Charger".equals(dto.getAttrCode()) &&
//		    !"spT1HiVoltFuz".equals(dto.getAttrCode()) &&
//		    !"spMCover".equals(dto.getAttrCode()) &&
//		    !"spStoreTemp".equals(dto.getAttrCode()) &&
//		    !"spPropEtc".equals(dto.getAttrCode())
//	    ){
//		continue;
//	    }
	    
	    Kogger.debug(getClass(), "{Name : '" + dto.getAttrCode() + "', Align : '" + ("SELECT".equals(dto.getAttrInputType())?"Center":"Left") + "', CanSort : '1', CanEdit : '0'},");
	}
	
	for (PartSpecEnum dto : list) {
	    
//	    if(     !"spSeriesBulb".equals(dto.getAttrCode()) &&
//		    !"spTBulbConn".equals(dto.getAttrCode()) &&
//		    !"spT2Charger".equals(dto.getAttrCode()) &&
//		    !"spT1HiVoltFuz".equals(dto.getAttrCode()) &&
//		    !"spMCover".equals(dto.getAttrCode()) &&
//		    !"spStoreTemp".equals(dto.getAttrCode()) &&
//		    !"spPropEtc".equals(dto.getAttrCode())
//	    ){
//		continue;
//	    }
	    
	    if(dto.getAttrName().indexOf("'") != -1){
		Kogger.debug(getClass(),  dto.getAttrCode() + " : \"" + dto.getAttrName() + "\", // " + dto.getAttrName());
	    }else{
		Kogger.debug(getClass(),  dto.getAttrCode() + " : '" + dto.getAttrName() + "', // " + dto.getAttrName());
	    }
	}
    }
    

    public void printColumn(List<MigPartSpecDTO> list) throws Exception {
	
//    ALTER TABLE WTPart ADD
//    ( 
//      BRDNM_ENG VARCHAR2(100)
//    ) 
    	Kogger.debug(getClass(), "ALTER TABLE WTPart ADD");
    	Kogger.debug(getClass(), "(");
    	String endFlag = "";
    	
    	 StringBuffer buffer = new StringBuffer();
    	 int idx = 0;
	for (MigPartSpecDTO dto : list) {
	    //Kogger.debug(getClass(), dto);
	    buffer.append(" ")
	    .append(endFlag)
	    .append(" ")
//	    .append( dto.getAttrCode()+"TYPEINFOWTPART")
	    .append( dto.getColumnName().replace("TypeInfoWTPart", "TYPEINFOWTPART"))
	    .append(" VARCHAR2(1500) ")
	    .append(dto.getAttrCode().length()<=16?"":" =>" + dto.getAttrCode().length());
	    
	    if(idx%3 == 0){
		buffer.append("\n");
	    }
	    
	    endFlag = ",";
	    
	    idx++;
	}
	Kogger.debug(getClass(), buffer.toString());
	
	Kogger.debug(getClass(), ")");
    }
    
    public void printAlterColumn(List<MigPartSpecDTO> list) throws Exception {
	
	// ALTER TABLE table_name RENAME COLUMN old_name to new_name;
	
	StringBuffer buffer = new StringBuffer();
	for (MigPartSpecDTO dto : list) {
	    Kogger.debug(getClass(), "ALTER TABLE WTPart RENAME COLUMN " + dto.getAttrCode()+"TYPEINFOWTPART" + " to " + dto.getColumnName()+"TYPEINFOWTPART;" );
	}
    }

    public void printGenJava(List<MigPartSpecDTO> list) throws Exception {
	
	//@GeneratedProperty(name="spPartNameHis", type=String.class,
	// constraints=@PropertyConstraints(upperLimit=1500)),
	
	for (MigPartSpecDTO dto : list) {
//	    Kogger.debug(getClass(), "@GeneratedProperty(name=\"" + dto.getAttrCode() + "\", type=String.class, constraints = @PropertyConstraints(upperLimit = 1500)),");
	    Kogger.debug(getClass(), "@GeneratedProperty(name=\"" + dto.getColumnName().toLowerCase() + "\", type=String.class, constraints = @PropertyConstraints(upperLimit = 1500)),");
	}
    }
    
    public void load(String filePath) throws Exception {

	List<MigPartSpecDTO> list = new ArrayList<MigPartSpecDTO>();

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = 1;
	excel.setSheet(sheetNo);
	int startRowNo = 29;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);
	    
//	    int colSize = 20;
//	    for (int k = 0; k < colSize; k++) {
//		excel.setCell(k);
//		Kogger.debug(getClass(), (k + 1) + ":" + excel.getStrValue());
//	    }
	    
	    if(StringUtils.isEmpty(excel.getStrValue(MigPartSpecEnum.ATTR_CODE.getIndex()))
	    || StringUtils.isEmpty(excel.getStrValue(MigPartSpecEnum.ATTR_NAME.getIndex()))
		    ){
		    continue;
	    }
	    
	    MigPartSpecDTO dto = makeValue();
	    
	    if("spPartGroup".equals(dto.getAttrCode())){
		continue;
	    }
	    
//	    if(     !"spSeriesBulb".equals(dto.getAttrCode()) &&
//		    !"spTBulbConn".equals(dto.getAttrCode()) &&
//		    !"spT2Charger".equals(dto.getAttrCode()) &&
//		    !"spT1HiVoltFuz".equals(dto.getAttrCode()) &&
//		    !"spMCover".equals(dto.getAttrCode()) &&
//		    !"spStoreTemp".equals(dto.getAttrCode()) &&
//		    !"spPropEtc".equals(dto.getAttrCode())
//	    ){
//		continue;
//	    }
	    
	    list.add(dto);
	}
	
	//////////////////////////////////////////
	//
	// 1. PartSpecEnum
	// 2. PartBaseDTO : private String xxxx;
	// 3. PartSpecSetter, PartSpecGetter
	//
	//////////////////////////////////////////
	
	// 1. PartSpecEnum 안에 내용
//	printEnum(list);
	
	// 2. PartBaseDTO, PartSearchMainDTO 의 property 정의 : private String xxxx;
//	printJavaDtoPropDefine(list);
	
	// 3. partList.js에 사용 -
//	printPartListJavascript();
	
	// 4. WTPartTypeInfo.java Generation된 자바 수정 @GeneratedProperty(name=
//	printGenJava(list);
	
	// 5. wtpart 테이블에 칼럼 추가 내용 : ALTER TABLE WTPart ADD ~
//	printColumn(list);
	
	// 7. OOTB에 저장할 때에 내용 - ootbInput.txt
	/**
	 SELECT ATTRCODE, ' ' sss, COLUMNNAME
	 FROM KETPartAttribute
	 ORDER BY COLUMNNAME
	 */
	
	// wtpart Rename : "ALTER TABLE WTPart RENAME COLUMN
//	printAlterColumn(list);
	
	// PartBaseBuilder : setter set
//	printPartBaseBuilderDtoSet(list);
	
	// PartBaseBuilder : dto에 set : dto.setSpPartType(PartSpecGetter.getPartSpecWithType(wtPartTypeInfo, PartSpecEnum.SpPartType));
//	printPartBaseBuilderDtoGet(list);
	
	// PartSpecSetter : java util setter 
//	printJavaComUtilSetter(list);
	// PartSpecGetter : java util getter 
//	printJavaUtilComGetter(list);
	
	// PartUtil : PartSpecEnum[]
//	printPartUtilPartSpecEnumArray(list);
	
    }

    private MigPartSpecDTO makeValue() throws Exception {

	MigPartSpecDTO dto = new MigPartSpecDTO();

	dto.setAttrCode(excel.getStrValue(MigPartSpecEnum.ATTR_CODE.getIndex()));
	dto.setAttrName(excel.getStrValue(MigPartSpecEnum.ATTR_NAME.getIndex()));
	dto.setSendErp(isSame(excel.getStrValue(MigPartSpecEnum.SEND_ERP.getIndex()), "O"));
	dto.setColumnName(excel.getStrValue(MigPartSpecEnum.COLUMN_NAME.getIndex()));
	dto.setAttrInputType(excel.getStrValue(MigPartSpecEnum.ATTR_INPUT_TYPE.getIndex()));
	dto.setMultiSelect(isSame(excel.getStrValue(MigPartSpecEnum.MULTI_SELECT.getIndex()), "O"));
	dto.setNumberCodeTypeCode(excel.getStrValue(MigPartSpecEnum.NUMBER_CODETYPE_CODE.getIndex()));
	dto.setNumberCodeTypeName(excel.getStrValue(MigPartSpecEnum.NUMBER_CODETYPE_NAME.getIndex()));
	dto.setNumberType(isSame(excel.getStrValue(MigPartSpecEnum.NUMBER_TYPE.getIndex()), "O"));
	dto.setEnumASIS(excel.getStrValue(MigPartSpecEnum.ENUM_ASIS.getIndex()));
	dto.setNumberingRel(isSame(excel.getStrValue(MigPartSpecEnum.NUMBERING_REL.getIndex()), "O"));
	dto.setNotNull(isSame(excel.getStrValue(MigPartSpecEnum.NOT_NULL.getIndex()), "O"));
	dto.setDescription(excel.getStrValue(MigPartSpecEnum.DESCRIPTION.getIndex()));
	dto.setComment(excel.getStrValue(MigPartSpecEnum.COMMENT.getIndex()));

	return dto;
    }

    public static void main(String[] args) throws Exception {

	PartSpecLoader loader = new PartSpecLoader();
//	String filePath = "D:\\MigrationData.xlsx";
	String filePath = "Z:\\90.개인폴더\\이응진\\부품사양관리\\업로드포멧\\업로드작업\\\\제품분류_V1.1_MigrationData.xlsx";
	loader.load(filePath);

    }
    
}