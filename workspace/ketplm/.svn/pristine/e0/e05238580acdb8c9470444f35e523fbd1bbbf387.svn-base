package ext.ket.part.migration.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class PartProjectRelLoader extends AbstractExcelLoader {

    public static final String PART_NO = "partNo";
    public static final String PROJECT_NO = "projectNo";
    
    private List<Map> list = new ArrayList<Map>();
    private ExcelHandle excel;

    public void print() {
	
	for (Map dto : list) {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( (String)dto.get(PART_NO) )
	    .append(", ")
	    .append( (String)dto.get(PROJECT_NO) )
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
	       StringUtils.isEmpty((String)dto.get(PROJECT_NO))
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
	String projectNo = excel.getStrValue(1);
	
	dto.put(PART_NO, partNo);
	dto.put(PROJECT_NO, projectNo);

	if(partNo!= null && partNo.endsWith(".0")){
	    dto.put(PART_NO, partNo.substring(0, partNo.length()-2));
	}
	
	if(projectNo!= null && projectNo.endsWith(".0")){
	    dto.put(PROJECT_NO, projectNo.substring(0, projectNo.length()-2));
	}
	
	return dto;
    }

    public List<Map> getList() {
        return list;
    }

    public static void main(String[] args) throws Exception {

	PartProjectRelLoader loader = new PartProjectRelLoader();
	String filePath = "d:\\partProject.xlsx";
	loader.load(filePath);
    }

}