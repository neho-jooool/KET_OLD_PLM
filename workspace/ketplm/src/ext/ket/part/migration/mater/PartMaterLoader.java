package ext.ket.part.migration.mater;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.migration.AbstractExcelLoader;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.shared.log.Kogger;

public class PartMaterLoader extends AbstractExcelLoader {

    private ExcelHandle excel;

    private List<PartMaterDTO> materList = new ArrayList<PartMaterDTO>();

    public void printDTO(PartMaterDTO dto) throws Exception {
	Kogger.debug(getClass(), dto.toString());
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

	    if (StringUtils.isEmpty(excel.getStrValue(MigPartMaterEnum.PART_NO.getIndex()))) {
		continue;
	    }

	    PartMaterDTO dto = makeProdValue();
	    Kogger.debug(getClass(), dto.toString());
	    materList.add(dto);
	}
    }

    private PartMaterDTO makeProdValue() throws Exception {

	PartMaterDTO dto = new PartMaterDTO();

	dto.setPartNo(excel.getStrValue(MigPartMaterEnum.PART_NO.getIndex()));
	dto.setPartName(excel.getStrValue(MigPartMaterEnum.PART_NAME.getIndex()));
	dto.setPartRevision(excel.getStrValue(MigPartMaterEnum.PART_REVISION.getIndex()));
	dto.setPartAppState(excel.getStrValue(MigPartMaterEnum.PART_STATE.getIndex()));
	dto.setPartType(excel.getStrValue(MigPartMaterEnum.spPartType.getIndex()));
	dto.setPartMaterGroup(excel.getStrValue(MigPartMaterEnum.PART_MATER_GROUP.getIndex()));
	dto.setPartOwner(excel.getStrValue(MigPartMaterEnum.PART_OWNER.getIndex()));
	dto.setPartSujiGubun(excel.getStrValue(MigPartMaterEnum.PART_SUJI_GUBUN.getIndex()));
	dto.setPartMaterMaker(excel.getStrValue(MigPartMaterEnum.PART_MATER_MAKER.getIndex()));
	dto.setPartMaterType(excel.getStrValue(MigPartMaterEnum.PART_MATER_TYPE.getIndex()));
	dto.setPartMaterGrade(excel.getStrValue(MigPartMaterEnum.PART_MATER_GRADE.getIndex()));
	dto.setPartMaterCharacteristics(excel.getStrValue(MigPartMaterEnum.PART_MATER_CHARACTERS.getIndex()));

	return dto;
    }

    public static void main(String[] args) throws Exception {

	PartMaterLoader loader = new PartMaterLoader();
	String filePath = "E:\\MigrationData\\part_mater\\Migration Data_MATER.xlsx";
	loader.load(filePath);

    }

    public List<PartMaterDTO> getMaterList() {
	return materList;
    }

}