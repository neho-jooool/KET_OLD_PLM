package ext.ket.part.migration.erp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class ErpPartLoader extends AbstractExcelLoader {

    private ExcelHandle excel;

    private List<ErpProdPartDTO> prodList = new ArrayList<ErpProdPartDTO>();
    private List<ErpDiePartDTO> dieList = new ArrayList<ErpDiePartDTO>();
    private List<ErpMoldPartDTO> moldList = new ArrayList<ErpMoldPartDTO>();

    public void printDTO(ErpProdPartDTO dto) throws Exception {
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

    public void load(String filePath, String fileType) throws Exception {

	File[] fileArray = findFileByPattern(filePath, fileType, "");
	for (File file : fileArray) {
	    loadInner(filePath + File.separator + file.getName(), fileType, file.getName());
	}
    }

    public void loadInner(String filePath, String type, String fileName) throws Exception {

	excel = ExcelFactory.getInstance().getExcelManager(filePath);
	int sheetNo = 0;
	excel.setSheet(sheetNo);
	int startRowNo = 1;
	excel.setRow(startRowNo);
	int rowSize = excel.getSheetLastRowNum();

	for (int i = startRowNo; i <= rowSize; i++) {
	    excel.setRow(i);

	    if (type != null && type.startsWith("DIEM")) {
		
		if (StringUtils.isEmpty(excel.getStrValue(MigErpMoldPartEnum.PART_NO.getIndex())) 
			) {
			continue;
		}
		
		ErpMoldPartDTO dto = makeMoldValue(fileName);
		Kogger.debug(getClass(), dto.toString());
		moldList.add(dto);
	    } else if (type != null && type.startsWith("CAST")) {
		
		if (StringUtils.isEmpty(excel.getStrValue(MigErpDiePartEnum.PART_NO.getIndex())) 
			) {
			continue;
		}
		
		ErpDiePartDTO dto = makeDieValue();
		Kogger.debug(getClass(), dto.toString());
		dieList.add(dto);
		
	    } else {
		
		if (StringUtils.isEmpty(excel.getStrValue(MigErpProdPartEnum.PART_NO.getIndex())) || StringUtils.isEmpty(excel.getStrValue(MigErpProdPartEnum.PART_NAME.getIndex()))
			    || StringUtils.isEmpty(excel.getStrValue(MigErpProdPartEnum.spPartType.getIndex()))) {
			continue;
		}
		
		ErpProdPartDTO dto = makeProdValue();
		Kogger.debug(getClass(), dto.toString());
		prodList.add(dto);
	    }
	}
    }

    private ErpProdPartDTO makeProdValue() throws Exception {

	ErpProdPartDTO dto = new ErpProdPartDTO();

	dto.setPartNo(excel.getStrValue(MigErpProdPartEnum.PART_NO.getIndex()));
	dto.setPartName(excel.getStrValue(MigErpProdPartEnum.PART_NAME.getIndex()));
	dto.setPartType(excel.getStrValue(MigErpProdPartEnum.spPartType.getIndex()));
	dto.setPartUnit(excel.getStrValue(MigErpProdPartEnum.PART_UNIT.getIndex()));
	dto.setTotalW(excel.getStrValue(MigErpProdPartEnum.spTotalWeight.getIndex()));
	dto.setNetW(excel.getStrValue(MigErpProdPartEnum.spNetWeight.getIndex()));
	dto.setScrabW(excel.getStrValue(MigErpProdPartEnum.spScrabWeight.getIndex()));
	dto.setProdSize(excel.getStrValue(MigErpProdPartEnum.spProdSize.getIndex()));
	dto.setScrabCode(excel.getStrValue(MigErpProdPartEnum.spScrabCode.getIndex()));
	dto.setIsDeleted(excel.getStrValue(MigErpProdPartEnum.spIsDeleted.getIndex()));
	dto.setSeries(excel.getStrValue(MigErpProdPartEnum.spSeries.getIndex()));
	dto.setWaterProof(excel.getStrValue(MigErpProdPartEnum.spWaterProof.getIndex()));

	// 비철-7, K2 / 수지 6, K1 )
	
	// 원재료 Maker, 원재료 Type, 재질(수지) | 재질(비철) 처리는 별도로 원재로 업로드로 처리함
	
//	if (dto.getPartNo().startsWith("6") || dto.getPartNo().startsWith("K1") || dto.getPartNo().startsWith("H6") || dto.getPartNo().startsWith("HK1")) {
//	    dto.setMaterFe(excel.getStrValue(MigErpProdPartEnum.spMater.getIndex()));
//	} else if (dto.getPartNo().startsWith("7") || dto.getPartNo().startsWith("K2") || dto.getPartNo().startsWith("H7") || dto.getPartNo().startsWith("HK2")) {
//	    dto.setMaterNotFe(excel.getStrValue(MigErpProdPartEnum.spMater.getIndex()));
//	}

	dto.setColor(excel.getStrValue(MigErpProdPartEnum.spColor.getIndex()));
	dto.setPlating(excel.getStrValue(MigErpProdPartEnum.spPlating.getIndex()));
	dto.setRepresentM(excel.getStrValue(MigErpProdPartEnum.spRepresentM.getIndex()));
	dto.setIsYazaki(excel.getStrValue(MigErpProdPartEnum.spIsYazaki.getIndex()));
	dto.setYazakiNo(excel.getStrValue(MigErpProdPartEnum.spYazakiNo.getIndex()));

	return dto;
    }

    private ErpDiePartDTO makeDieValue() throws Exception {

	ErpDiePartDTO dto = new ErpDiePartDTO();

	dto.setPartNo(excel.getStrValue(MigErpDiePartEnum.PART_NO.getIndex()));
	dto.setSpMContractSAt(excel.getStrValue(MigErpDiePartEnum.spMContractSAt.getIndex()));
	dto.setSpMMoldAt(excel.getStrValue(MigErpDiePartEnum.spMMoldAt.getIndex()));
	dto.setSpMMakingAt(excel.getStrValue(MigErpDiePartEnum.spMMakingAt.getIndex()));
	dto.setSpMProdAt(excel.getStrValue(MigErpDiePartEnum.spMProdAt.getIndex()));
	dto.setSpPuchaseGroup(excel.getStrValue(MigErpDiePartEnum.spPuchaseGroup.getIndex()));
	dto.setSpPlant(excel.getStrValue(MigErpDiePartEnum.spPlant.getIndex()));
	dto.setSpDieWhere(excel.getStrValue(MigErpDiePartEnum.spDieWhere.getIndex()));
	dto.setSpDevManNm(excel.getStrValue(MigErpDiePartEnum.spDevManNm.getIndex()));
	dto.setSpDesignManNm(excel.getStrValue(MigErpDiePartEnum.spDesignManNm.getIndex()));
	dto.setSpManufacManNm(excel.getStrValue(MigErpDiePartEnum.spManufacManNm.getIndex()));
	dto.setSpObjectiveCT(excel.getStrValue(MigErpDiePartEnum.spObjectiveCT.getIndex()));
	dto.setSpCavityStd(excel.getStrValue(MigErpDiePartEnum.spCavityStd.getIndex()));
	dto.setSpIsDeleted(excel.getStrValue(MigErpDiePartEnum.spIsDeleted.getIndex()));
	dto.setPartHalb(excel.getStrValue(MigErpDiePartEnum.PART_HALB.getIndex()));

	return dto;
    }

    private ErpMoldPartDTO makeMoldValue(String fileName) throws Exception {

	ErpMoldPartDTO dto = new ErpMoldPartDTO();

	dto.setPartNo(excel.getStrValue(MigErpMoldPartEnum.PART_NO.getIndex()));
	dto.setPartName(excel.getStrValue(MigErpMoldPartEnum.PART_NAME.getIndex()));
	dto.setPartUnit(excel.getStrValue(MigErpMoldPartEnum.PART_UNIT.getIndex()));
	dto.setPartType(excel.getStrValue(MigErpMoldPartEnum.spPartType.getIndex()));
	dto.setSpMaterDie(excel.getStrValue(MigErpMoldPartEnum.spMaterDie.getIndex()));
	dto.setSpIsDeleted(excel.getStrValue(MigErpMoldPartEnum.spIsDeleted.getIndex()));
	dto.setExcelFileName(fileName);
	
	return dto;
    }

    public static void main(String[] args) throws Exception {

	ErpPartLoader loader = new ErpPartLoader();
	String filePath = "E:\\MigrationData\\erp_part";
	String[] fileTypeArray = new String[] { "FERT", "HALB", "HAWA", "ROH", "PACK", "SCRP", "CAST"
		    , "DIEM_01", "DIEM_02", "DIEM_03", "DIEM_04", "DIEM_05", "DIEM_06", "DIEM_07", "DIEM_08"
		    , "DIEM_09", "DIEM_10", "DIEM_11", "DIEM_12", "DIEM_13", "DIEM_14" };
	for (String fileType : fileTypeArray) {
	    loader.load(filePath, fileType);
	}
	    
    }

    public List<ErpProdPartDTO> getProdList() {
        return prodList;
    }

    public List<ErpDiePartDTO> getDieList() {
        return dieList;
    }

    public List<ErpMoldPartDTO> getMoldList() {
        return moldList;
    }
    
}