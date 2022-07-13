package ext.ket.part.sap;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class ErpPartEnumLoader extends AbstractExcelLoader {

    private ExcelHandle excel;

    public void load(String filePath, boolean isProd) throws Exception {

	List<MigErpPartCreateDTO> list = new ArrayList<MigErpPartCreateDTO>();

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = isProd?0:2;
	excel.setSheet(sheetNo);
	int startRowNo = 8;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();

	for (int i = startRowNo; i <= rowSize; i++) {
	    
	    excel.setRow(i);

//	     int colSize = 20;
//	     for (int k = 0; k < colSize; k++) {
//	     excel.setCell(k);
//	     	Kogger.debug(getClass(), (k + 1) + ":" + excel.getStrValue());
//	     }

	    if (StringUtils.isEmpty(excel.getStrValue(14)) 
		    
	    ) {
		
		continue;
		
	    }

	    MigErpPartCreateDTO dto = makeValue();
	    
	    list.add(dto);
	}
	
	printErpPartCreate(list);
    }
    
    protected String getNextStr(String str){
	
	if(str == null) return str;
	
	String tempStr = str;
	tempStr = str.replaceAll("\r\n", ", ");
	tempStr = str.replaceAll("\n", ", ");
	
	return tempStr;
    }
    
    protected String getChgNextStr(String str){
	
	if(str == null) return str;
	
	String tempStr = str;
	tempStr = str.replaceAll("\r\n", " ");
	tempStr = str.replaceAll("\n", " ");
	
	return tempStr;
    }
    
    protected String getSpaceStr(String str){
	
	if(str == null) return str;
	
	String tempStr = str;
	
	return " " + tempStr;
    }
    
    private void printErpPartCreate(List<MigErpPartCreateDTO> list){
	
	for (MigErpPartCreateDTO dto : list) {

	    if(StringUtils.isEmpty(dto.getAttrCode()))
		continue;
	    		
	    Kogger.debug(getClass(), "");
	    Kogger.debug(getClass(), "//" + getChgNextStr(dto.getAttrName()) + getSpaceStr(dto.getClassName()) + getSpaceStr(getChgNextStr(dto.getAttrCode())) );
	    Kogger.debug(getClass(), "//" + ("→".equals(dto.getIfDirection())?"PLM → ERP":"") + ("Y".equals(getSpaceStr(dto.getInsert()))?"insert Yes ":"") + getSpaceStr(dto.getUpdate()) + getSpaceStr(getChgNextStr(dto.getChangeLogic())) );
	    Kogger.debug(getClass(), "//" + dto.getErpField() + " [" + dto.getErpTable() + "]" + getSpaceStr(dto.getErpEsse()) + getSpaceStr(dto.getErpDesc()) );
	    Kogger.debug(getClass(), "//" + dto.getType() + " [" + dto.getDomain() + "]" + getSpaceStr(getChgNextStr(dto.getBigo())) );
	    Kogger.debug(getClass(), "//" + "ex) " + dto.getExample());
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( ("ErpProdCode".equals(getCameStr(getChgNextStr(dto.getAttrCode())))?"ErpProdHalbCode":getCameStr(getChgNextStr(dto.getAttrCode()))))
	    .append("(")
	    .append(("erpProdCode".equals(getStringType(getChgNextStr(dto.getAttrCode())))?"erpProdHalbCode":getStringType(getChgNextStr(dto.getAttrCode()))))
	    .append(", ")
	    .append(getStringType(dto.getErpField()))
	    .append(", ")
	    .append(getStringType(dto.getErpDesc()))
	    .append(", ")
	    .append(getStringType(""))
	    .append(", ")
	    .append("O".equals(dto.getErpEsse())?"true":"false") // dbColumnName
	    .append(", ")
	    .append(getStringType(dto.getType()))
	    .append("),");
	    
	    Kogger.debug(getClass(), buffer.toString());
	}
    }

    private MigErpPartCreateDTO makeValue() throws Exception {

	MigErpPartCreateDTO dto = new MigErpPartCreateDTO();

	dto.setClassName(excel.getStrValue(3));
	dto.setAttrName(excel.getStrValue(4));
	dto.setAttrCode(excel.getStrValue(5));
	dto.setChangeLogic(excel.getStrValue(8));
	dto.setIfDirection(excel.getStrValue(9));
	dto.setInsert(excel.getStrValue(10));
	dto.setUpdate(excel.getStrValue(11));
	dto.setAlias(excel.getStrValue(12));
	dto.setErpTable(excel.getStrValue(13));
	dto.setErpField(excel.getStrValue(14));
	dto.setErpEsse(excel.getStrValue(15));
	dto.setErpDesc(excel.getStrValue(16));
	dto.setDomain(excel.getStrValue(17));
	dto.setType(excel.getStrValue(18));
	dto.setBigo(excel.getStrValue(20));
	dto.setExample(excel.getStrValue(22));
	
	if(dto.getExample() != null && dto.getExample().endsWith(".0")){
	    dto.setExample(dto.getExample().substring(0, dto.getExample().length()-2));
	}
	
//	dto.setAttrCode(excel.getStrValue(MigPartSpecEnum.ATTR_CODE.getIndex()));
//	dto.setSendErp(isSame(excel.getStrValue(MigPartSpecEnum.SEND_ERP.getIndex()), "O"));
//	dto.setNumberingRel(isSame(excel.getStrValue(MigPartSpecEnum.NUMBERING_REL.getIndex()), "O"));

	return dto;
    }

    public static void main(String[] args) throws Exception {

	ErpPartEnumLoader loader = new ErpPartEnumLoader();
	String filePath = "D:\\erpPart.xlsx";
	boolean isProd = false;
	loader.load(filePath, isProd);

    }

    public class MigErpPartCreateDTO {

	private String className;
	private String attrName;
	private String attrCode;
	private String changeLogic;
	private String ifDirection;
	private String insert;
	private String update;
	private String alias;
	private String erpTable;
	private String erpField;
	private String erpEsse;
	private String erpDesc;
	private String domain;
	private String type;
	private String bigo;
	private String example;
	public String getClassName() {
	    return className;
	}
	public void setClassName(String className) {
	    this.className = className;
	}
	public String getAttrName() {
	    return attrName;
	}
	public void setAttrName(String attrName) {
	    this.attrName = attrName;
	}
	public String getAttrCode() {
	    return attrCode;
	}
	public void setAttrCode(String attrCode) {
	    this.attrCode = attrCode;
	}
	public String getChangeLogic() {
	    return changeLogic;
	}
	public void setChangeLogic(String changeLogic) {
	    this.changeLogic = changeLogic;
	}
	public String getIfDirection() {
	    return ifDirection;
	}
	public void setIfDirection(String ifDirection) {
	    this.ifDirection = ifDirection;
	}
	public String getInsert() {
	    return insert;
	}
	public void setInsert(String insert) {
	    this.insert = insert;
	}
	public String getUpdate() {
	    return update;
	}
	public void setUpdate(String update) {
	    this.update = update;
	}
	public String getAlias() {
	    return alias;
	}
	public void setAlias(String alias) {
	    this.alias = alias;
	}
	public String getErpTable() {
	    return erpTable;
	}
	public void setErpTable(String erpTable) {
	    this.erpTable = erpTable;
	}
	public String getErpField() {
	    return erpField;
	}
	public void setErpField(String erpField) {
	    this.erpField = erpField;
	}
	public String getErpEsse() {
	    return erpEsse;
	}
	public void setErpEsse(String erpEsse) {
	    this.erpEsse = erpEsse;
	}
	public String getErpDesc() {
	    return erpDesc;
	}
	public void setErpDesc(String erpDesc) {
	    this.erpDesc = erpDesc;
	}
	public String getDomain() {
	    return domain;
	}
	public void setDomain(String domain) {
	    this.domain = domain;
	}
	public String getType() {
	    return type;
	}
	public void setType(String type) {
	    this.type = type;
	}
	public String getBigo() {
	    return bigo;
	}
	public void setBigo(String bigo) {
	    this.bigo = bigo;
	}
	public String getExample() {
	    return example;
	}
	public void setExample(String example) {
	    this.example = example;
	}
    }
}