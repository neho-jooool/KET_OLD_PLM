package ext.ket.part.naming.service;

import org.junit.Test;

import ext.ket.part.entity.KETPartNaming;
import ext.ket.part.naming.service.internal.PartNamingBuilder;
import ext.ket.shared.test.AbstractUnitTest;

public class StandardPartNamingServiceTest extends AbstractUnitTest {

    @Test
    public void insertPartNamingLinkList() throws Exception {

	// PartClassNamingLinkDTO partClassNamingLinkDTO = new PartClassNamingLinkDTO();
	// partClassNamingLinkDTO.setPartNamingOid("ext.ket.part.entity.KETPartNamingibute:897903429");
	// partClassNamingLinkDTO.setPartClazOid("ext.ket.part.entity.KETPartClassification:897873132");
	// partClassNamingLinkDTO.setChecked(false);
	// partClassNamingLinkDTO.setEssential(false);
	// partClassNamingLinkDTO.setIndexRow(10);
	// partClassNamingLinkDTO.setIndexCol(10);
	//
	// boolean result = PartSpecHelper.service.insertPartAttrLinkList(partClassAttrLinkDTO);
    }

    @Test
    public void testMigration() throws Exception {

	// Housing
	KETPartNaming partNaming = new PartNamingBuilder.Builder().setNamingCode("1001").setNamingName("Housing").setUseNaming(true).build();
	PartNamingcHelper.service.insert(partNaming, true);

	// Terminal
	partNaming = new PartNamingBuilder.Builder().setNamingCode("1002").setNamingName("Terminal").setUseNaming(true).build();
	PartNamingcHelper.service.insert(partNaming, true);

	// PCB
	partNaming = new PartNamingBuilder.Builder().setNamingCode("1003").setNamingName("PCB").setUseNaming(true).build();
	PartNamingcHelper.service.insert(partNaming, true);
    }

    @Test
    public void testPrintAttrCode() throws Exception {

	// List<KETPartAttribute> partAttrList = PartSpecHelper.service.searchFullList();
	//
	// Kogger.debug(getClass(), "#################### Start ######################");
	//
	// String AND = "\", \"";
	// String ABOOL = ", ";
	//
	// for (KETPartAttribute partAttr : partAttrList) {
	//
	// String attrCode = partAttr.getAttrCode();
	// String attrName = partAttr.getAttrName();
	// String attrOotbName = partAttr.getAttrOotbName();
	// String columnName = partAttr.getColumnName();
	// String attrInputType = partAttr.getAttrInputType();
	// boolean useNumbering = partAttr.isUseNumbering();
	// boolean sendErp = partAttr.isSendErp();
	// boolean receiveErp = partAttr.isReceiveErp();
	// String erpDesc = StringUtil.checkNull(partAttr.getErpDesc());
	// String attrDesc = StringUtil.checkNull(partAttr.getAttrDesc());
	// Kogger.debug(getClass(), "// " + attrName + " - " + attrDesc);
	// Kogger.debug(getClass(), attrCode.substring(0, 1).toUpperCase() + attrCode.substring(1) + "(\"" + attrCode + AND + attrName + AND + attrOotbName + AND + columnName + AND + attrInputType + "\""
	// + ABOOL + useNumbering + ABOOL + "\"" + "" + "\"" + ABOOL + sendErp + ABOOL + receiveErp + ABOOL + "\"" + erpDesc + "\"),");
	//
	// // SpecPartType("specPartType", "부품유형", "부품유형", "PTC_STR_1TypeInfoWTPart", "SELECT", false, "", true, false, ""),
	// // SpecPartType("specPartType", "부품유형", "부품유형", "PTC_STR_1TypeInfoWTPart", "SELECT, false, ,"", true, false, ,""),
	// }
	//
	// Kogger.debug(getClass(), "####################  End  ######################");

    }
}