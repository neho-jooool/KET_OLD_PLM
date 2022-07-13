package ext.ket.part.migration.base;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

final public class PartPropLoader extends AbstractExcelLoader {

    private ExcelHandle excel;
    private List<PartPropDTO> propList = new ArrayList<PartPropDTO>();

    public void printDTO(PartPropDTO dto) throws Exception {
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
	
	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);

	    if (
		StringUtils.isEmpty(excel.getStrValue(PartPropEnum.MAST_OID.getIndex())) || 
		StringUtils.isEmpty(excel.getStrValue(PartPropEnum.REV_OID.getIndex())) || 
		StringUtils.isEmpty(excel.getStrValue(PartPropEnum.ITER_OID.getIndex())) || 
	        StringUtils.isEmpty(excel.getStrValue(PartPropEnum.PART_NO.getIndex())) || 
		StringUtils.isEmpty(excel.getStrValue(PartPropEnum.PART_REV.getIndex()))
		    ) {
		continue;
	    }

	    PartPropDTO dto = makeProdValue();
	    //Kogger.debug(getClass(), dto.toString());
	    propList.add(dto);
	}
    }

    private PartPropDTO makeProdValue() throws Exception {

	PartPropDTO dto = new PartPropDTO();

	dto.setMastOid(excel.getStrValue(PartPropEnum.MAST_OID.getIndex()));
	dto.setRevOid(excel.getStrValue(PartPropEnum.REV_OID.getIndex()));
	dto.setIterOid(excel.getStrValue(PartPropEnum.ITER_OID.getIndex()));
	
	dto.setPartNo(excel.getStrValue(PartPropEnum.PART_NO.getIndex()));
	dto.setPartName(excel.getStrValue(PartPropEnum.PART_NAME.getIndex()));
	dto.setPartRev(excel.getStrValue(PartPropEnum.PART_REV.getIndex()));
	dto.setPartType(excel.getStrValue(PartPropEnum.spPartType.getIndex()));
	dto.setPartClaz(excel.getStrValue(PartPropEnum.PART_CLAZ.getIndex()));
	dto.setPartDevLevel(excel.getStrValue(PartPropEnum.PART_DEV_LEVEL.getIndex()));
	dto.setPartKetRev(excel.getStrValue(PartPropEnum.spPartRevision.getIndex()));
	
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
	if(dto.getPartNo() != null && dto.getPartNo().endsWith(".0")){
	    dto.setPartNo(dto.getPartNo().substring(0, dto.getPartNo().length()-2));
	}
	if(dto.getPartRev() != null && dto.getPartRev().endsWith(".0")){
	    dto.setPartRev(dto.getPartRev().substring(0, dto.getPartRev().length()-2));
	}
	if(dto.getPartKetRev() != null && dto.getPartKetRev().endsWith(".0")){
	    dto.setPartKetRev(dto.getPartKetRev().substring(0, dto.getPartKetRev().length()-2));
	}
	
	return dto;
    }
    
    public static void main(String[] args) throws Exception {

	PartPropLoader loader = new PartPropLoader();
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

    public List<PartPropDTO> getPropList() {
        return propList;
    }

}