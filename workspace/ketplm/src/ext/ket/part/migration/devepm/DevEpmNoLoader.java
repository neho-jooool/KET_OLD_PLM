package ext.ket.part.migration.devepm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class DevEpmNoLoader extends AbstractExcelLoader {

    public static final String PART_NO = "partNo";
    public static final String PART_NAME = "partName";
    public static final String REG_USER = "regUser";
    public static final String REG_DATE = "regDate";
    public static final String EPM_2D = "epm2D";
    public static final String EPM_3D = "epm3D";
    
    private List<Map> list = new ArrayList<Map>();
    private ExcelHandle excel;

    public void print() {
	
	for (Map dto : list) {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( (String)dto.get(PART_NO) )
	    .append(", ")
	    .append( (String)dto.get(PART_NAME) )
	    .append(", ")
	    .append( (String)dto.get(REG_USER) )
	    .append(", ")
	    .append( (String)dto.get(REG_DATE) )
	    .append(", ")
	    .append( (String)dto.get(EPM_2D) )
	    .append(", ")
	    .append( (String)dto.get(EPM_3D) )
	    .append("");
	    
	    Kogger.debug(getClass(), buffer.toString());
	}
    }

    public void load(String filePath) throws Exception {

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = 2;
	excel.setSheet(sheetNo);
	int startRowNo = 1;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);	    
    
	    Map dto = makeValue();
	    
	    if(
	       StringUtils.isEmpty((String)dto.get(PART_NO))
	    ){
		continue;
	    }

	    list.add(dto);
	}
	
	print();
    }

    private Map makeValue() {

	Map dto = new HashMap();

	String partNo = excel.getStrValue(0);
	String partName = excel.getStrValue(1);
	String regUser = excel.getStrValue(2);
	String regDate = excel.getStrValue(3);
	String epm2D = excel.getStrValue(4);
	String epm3D = excel.getStrValue(5);
	
	dto.put(PART_NO, partNo);
	dto.put(PART_NAME, partName);
	dto.put(REG_USER, regUser);
	dto.put(REG_DATE, regDate);
	dto.put(EPM_2D, epm2D);
	dto.put(EPM_3D, epm3D);

	if(partNo!= null && partNo.endsWith(".0")){
	    dto.put(PART_NO, partNo.substring(0, partNo.length()-2));
	}
	
	if(regDate!= null && regDate.endsWith(".0")){
	    dto.put(REG_DATE, regDate.substring(0, regDate.length()-2));
	}
	
	if(epm2D!= null && epm2D.endsWith(".0")){
	    dto.put(EPM_2D, epm2D.substring(0, epm2D.length()-2));
	}
	
	if(epm3D!= null && epm3D.endsWith(".0")){
	    dto.put(EPM_3D, epm3D.substring(0, epm3D.length()-2));
	}
	
	return dto;
    }

    public List<Map> getList() {
        return list;
    }

    public static void main(String[] args) throws Exception {

	DevEpmNoLoader loader = new DevEpmNoLoader();
	String filePath = "d:\\CP_EPM.xlsx";
	loader.load(filePath);
    }

}