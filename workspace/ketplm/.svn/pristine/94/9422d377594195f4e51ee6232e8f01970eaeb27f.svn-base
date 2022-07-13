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

public class MigNumberingMinNoLoader extends AbstractExcelLoader {

    public static final String CLASSCODE = "classCode";
    public static final String MINNO = "minNo";
    
    private List<Map> list = new ArrayList<Map>();
    private ExcelHandle excel;

    public void print() {
	
	for (Map dto : list) {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("")
	    .append( (String)dto.get(CLASSCODE) )
	    .append(", ")
	    .append( (String)dto.get(MINNO) )
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
	int rowSize = excel.getSheetLastRowNum();
	
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);	    
    
	    Map dto = makeValue();
	    
	    if(
	       StringUtils.isEmpty((String)dto.get(CLASSCODE))
	    || StringUtils.isEmpty((String)dto.get(MINNO))
	    ){
		continue;
	    }
	    
	    list.add(dto);
	}
	
	print();
    }

    private Map makeValue() {

	Map dto = new HashMap();

	String classCode = excel.getStrValue(25);
	String minNo = excel.getStrValue(27);
	dto.put(CLASSCODE, classCode);
	dto.put(MINNO, minNo);

	if(classCode!= null && classCode.endsWith(".0")){
	    dto.put(CLASSCODE, classCode.substring(0, classCode.length()-2));
	}
	
	if(minNo!= null && minNo.endsWith(".0")){
	    dto.put(MINNO, minNo.substring(0, minNo.length()-2));
	}
	
	return dto;
    }

    public List<Map> getList() {
        return list;
    }

    public static void main(String[] args) throws Exception {

	MigNumberingMinNoLoader loader = new MigNumberingMinNoLoader();
	String filePath = "Z:\\90.개인폴더\\이응진\\부품사양관리\\업로드포멧\\업로드작업\\\\제품분류_V1.1_MigrationData.xlsx";
	loader.load(filePath);
    }

}