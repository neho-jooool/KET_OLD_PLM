package ext.ket.part.spec.service;

import java.util.List;

import org.junit.Test;

import e3ps.common.util.StringUtil;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.spec.service.internal.PartSpecBuilder;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.test.AbstractUnitTest;

public class StandardPartSpcServiceTest extends AbstractUnitTest {

    // @Test
    // public void insertPartAttrLinkList() throws Exception {
    //
    // PartClassAttrLinkDTO partClassAttrLinkDTO = new PartClassAttrLinkDTO();
    // partClassAttrLinkDTO.setPartAttrOid("ext.ket.part.entity.KETPartAttribute:897903429");
    // partClassAttrLinkDTO.setPartClazOid("ext.ket.part.entity.KETPartClassification:897873132");
    // partClassAttrLinkDTO.setChecked(false);
    // partClassAttrLinkDTO.setEssential(false);
    // partClassAttrLinkDTO.setIndexRow(10);
    // partClassAttrLinkDTO.setIndexCol(10);
    //
    // boolean result = PartSpecHelper.service.insertPartAttrLinkList(partClassAttrLinkDTO);
    // }

    @Test
    public void testMigration() throws Exception {

	PartSpecEnum[] specEnumArry = PartSpecEnum.values();
	for (PartSpecEnum partSpecEnum : specEnumArry) {

	    String attrCode = partSpecEnum.getAttrCode();
	    String attrName = partSpecEnum.getAttrName();
	    String columnName = partSpecEnum.getColumnName();
	    String attrInputType = partSpecEnum.getAttrInputType();
	    String attrUnit = partSpecEnum.getAttrUnit();
	    boolean sendErp = partSpecEnum.getSendReceiveErp();
	    String erpDesc = partSpecEnum.getErpDesc();
	    boolean useNumbering = partSpecEnum.getUseNumbering();

	    KETPartAttribute partAttr = new PartSpecBuilder.Builder().setAttrCode(attrCode).setAttrName(attrName).setAttrOotbName(attrName).setColumnName(columnName)
		    .setAttrInputType(attrInputType).setAttrDesc(attrUnit).setSendErp(sendErp).setErpDesc(erpDesc).setUseNumbering(useNumbering).build();

	    partAttr = PartSpecHelper.service.insert(partAttr, true /* exist Not Insert */);
	}
    }

    @Test
    public void testPrintAttrCode() throws Exception {

	List<KETPartAttribute> partAttrList = PartSpecHelper.service.searchFullList();

	Kogger.debug(getClass(), "#################### Start ######################");

	String AND = "\", \"";
	String ABOOL = ", ";

	for (KETPartAttribute partAttr : partAttrList) {

	    String attrCode = partAttr.getAttrCode();
	    String attrName = partAttr.getAttrName();
	    String attrOotbName = partAttr.getAttrOotbName();
	    String columnName = partAttr.getColumnName();
	    String attrInputType = partAttr.getAttrInputType();
	    boolean useNumbering = partAttr.isUseNumbering();
	    boolean sendErp = partAttr.isSendErp();
	    boolean receiveErp = partAttr.isReceiveErp();
	    String erpDesc = StringUtil.checkNull(partAttr.getErpDesc());
	    String attrDesc = StringUtil.checkNull(partAttr.getAttrDesc());
	    Kogger.debug(getClass(), "// " + attrName + " - " + attrDesc);
	    Kogger.debug(getClass(), attrCode.substring(0, 1).toUpperCase() + attrCode.substring(1) + "(\"" + attrCode + AND + attrName + AND + attrOotbName + AND + columnName + AND + attrInputType + "\""
		    + ABOOL + useNumbering + ABOOL + "\"" + "" + "\"" + ABOOL + sendErp + ABOOL + receiveErp + ABOOL + "\"" + erpDesc + "\"),");

	    // SpecPartType("specPartType", "부품유형", "부품유형", "PTC_STR_1TypeInfoWTPart", "SELECT", false, "", true, false, ""),
	    // SpecPartType("specPartType", "부품유형", "부품유형", "PTC_STR_1TypeInfoWTPart", "SELECT, false, ,"", true, false, ,""),
	}

	Kogger.debug(getClass(), "####################  End  ######################");

    }
}