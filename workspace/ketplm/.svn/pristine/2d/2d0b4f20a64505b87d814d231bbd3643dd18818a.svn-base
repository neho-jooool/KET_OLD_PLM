package ext.ket.edm.migration.mastrel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class EpmPartMastMultiRelLoader extends AbstractExcelLoader {

    public static final String PART_NO = "partNo";
    public static final String EPM_NO = "epmNo";
    public static final String REFER_TYPE = "referType";
    public static final String REQ = "req";
    public static final String ECAD = "ecad";
    
    private List<Map> list = new ArrayList<Map>();
    private ExcelHandle excel;

    public void print() {
	
	for (Map dto : list) {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( (String)dto.get(PART_NO) )
	    .append(", ")
	    .append( (String)dto.get(EPM_NO) )
	    .append(", ")
	    .append( (String)dto.get(REFER_TYPE) )
	    .append(", ")
	    .append( (String)dto.get(REQ) )
	    .append(", ")
	    .append( (String)dto.get(ECAD) )
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
	       StringUtils.isEmpty((String)dto.get(PART_NO)) ||
	       StringUtils.isEmpty((String)dto.get(EPM_NO)) ||
	       StringUtils.isEmpty((String)dto.get(REFER_TYPE)) ||
	       StringUtils.isEmpty((String)dto.get(REQ)) ||
	       StringUtils.isEmpty((String)dto.get(ECAD)) 
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
	String refer = excel.getStrValue(2);
	String req = excel.getStrValue(3);
	String ecad = excel.getStrValue(4);
	
	dto.put(PART_NO, partNo);
	dto.put(EPM_NO, epmNo);
	dto.put(REFER_TYPE, refer);
	dto.put(REQ, req);
	dto.put(ECAD, ecad);
	
	if(partNo!= null && partNo.endsWith(".0")){
	    dto.put(PART_NO, partNo.substring(0, partNo.length()-2));
	}
	
	if(epmNo!= null && epmNo.endsWith(".0")){
	    dto.put(EPM_NO, epmNo.substring(0, epmNo.length()-2));
	}
	
	if(req!= null && req.endsWith(".0")){
	    dto.put(REQ, req.substring(0, req.length()-2));
	}
	
	if(ecad!= null && ecad.endsWith(".0")){
	    dto.put(ECAD, ecad.substring(0, ecad.length()-2));
	}

	return dto;
    }

    public List<Map> getList() {
        return list;
    }

    public static void main(String[] args) throws Exception {

	EpmPartMastMultiRelLoader loader = new EpmPartMastMultiRelLoader();
	String filePath = "d:\\CP_EPM.xlsx";
	loader.load(filePath);
    }

}