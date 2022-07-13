package ext.ket.edm.migration.revision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class EpmRevReLoader extends AbstractExcelLoader {

    public static final String EPM_NO = "epmNo";
    public static final String DEV_LVL = "devLevel";
    public static final String MNG_TYPE = "manageType";
    
    private List<Map> list = new ArrayList<Map>();
    private ExcelHandle excel;

    public void print() {
	
	for (Map dto : list) {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( (String)dto.get(EPM_NO) )
	    .append(", ")
	    .append( (String)dto.get(DEV_LVL) )
	    .append(", ")
	    .append( (String)dto.get(MNG_TYPE) )
	    .append("");
	    
	    Kogger.debug(getClass(), buffer.toString());
	}
    }

    public void load(String filePath) throws Exception {

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = 0;
	excel.setSheet(sheetNo);
	int startRowNo = 1;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();
	
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);	    
    
	    Map dto = makeValue();
	    
	    if(
	       StringUtils.isEmpty((String)dto.get(EPM_NO)) ||
	       StringUtils.isEmpty((String)dto.get(DEV_LVL))
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
	String epmNo = excel.getStrValue(1);
	String manageType = excel.getStrValue(2);
	
	dto.put(EPM_NO, partNo);
	dto.put(DEV_LVL, epmNo);
	dto.put(MNG_TYPE, manageType);

	if(partNo!= null && partNo.endsWith(".0")){
	    dto.put(EPM_NO, partNo.substring(0, partNo.length()-2));
	}
	
	if(epmNo!= null && epmNo.endsWith(".0")){
	    dto.put(DEV_LVL, epmNo.substring(0, epmNo.length()-2));
	}
	
	if(manageType!= null && manageType.endsWith(".0")){
	    dto.put(MNG_TYPE, manageType.substring(0, manageType.length()-2));
	}

	return dto;
    }

    public List<Map> getList() {
        return list;
    }

    public static void main(String[] args) throws Exception {

	EpmRevReLoader loader = new EpmRevReLoader();
	String filePath = "d:\\epm_rev.xlsx";
	loader.load(filePath);
    }

}