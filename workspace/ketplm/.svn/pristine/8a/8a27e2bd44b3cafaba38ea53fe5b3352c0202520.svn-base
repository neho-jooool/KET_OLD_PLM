package ext.ket.part.classify.service;

import java.util.List;

import org.junit.Test;

import ext.ket.part.classify.service.internal.PartClazBuilder;
import ext.ket.part.classify.util.PartClassificType;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.test.AbstractUnitTest;

public class StandardPartClazServiceTest extends AbstractUnitTest {

    @Test
    public void testSearchFullList() throws Exception {

	// List<PartClassificationDTO> fullList = PartClazHelper.service.searchFullList();
	// for (PartClassificationDTO dto : fullList) {
	// Kogger.debug(getClass(), dto.getLev() + " > " + dto.getClassKrName());
	// }
	//
	// ClazOxmParser parser = new ClazOxmParser();
	// String fullXml = parser.getClazXmlObject(fullList);
	//
	// Kogger.debug(getClass(), "fullXml:" + fullXml);

    }

    @Test
    public void testCompareList() throws Exception {

	// List<KETPartClassification> oldAttrList = new ArrayList<KETPartClassification>();
	// oldAttrList.add((KETPartClassification) CommonUtil.getObject("ext.ket.part.entity.KETPartClassification:897873125"));
	// oldAttrList.add((KETPartClassification) CommonUtil.getObject("ext.ket.part.entity.KETPartClassification:897873128"));
	// oldAttrList.add((KETPartClassification) CommonUtil.getObject("ext.ket.part.entity.KETPartClassification:897873130"));
	// oldAttrList.add((KETPartClassification) CommonUtil.getObject("ext.ket.part.entity.KETPartClassification:897873133"));
	// Kogger.debug(getClass(), "oldAttrList:");
	// printClaz(oldAttrList);
	//
	// List<KETPartClassification> newAttrList = new ArrayList<KETPartClassification>();
	// newAttrList.add((KETPartClassification) CommonUtil.getObject("ext.ket.part.entity.KETPartClassification:897873128"));
	// newAttrList.add((KETPartClassification) CommonUtil.getObject("ext.ket.part.entity.KETPartClassification:897873130"));
	// newAttrList.add((KETPartClassification) CommonUtil.getObject("ext.ket.part.entity.KETPartClassification:897873137"));
	// Kogger.debug(getClass(), "newAttrList:");
	// printClaz(newAttrList);
	//
	// PersistableCompareUtil<KETPartClassification> comparator = new PersistableCompareUtil<KETPartClassification>();
	// comparator.compare(oldAttrList, newAttrList);
	//
	// List<KETPartClassification> sameList = comparator.getSameList();
	// Kogger.debug(getClass(), "sameList:");
	// printClaz(sameList);
	// List<KETPartClassification> addList = comparator.getAddList();
	// Kogger.debug(getClass(), "addList:");
	// printClaz(addList);
	// List<KETPartClassification> deleteList = comparator.getDeleteList();
	// Kogger.debug(getClass(), "deleteList:");
	// printClaz(deleteList);

    }

    /*
     * -- SORT NO UPDATE 구문 UPDATE KETPartClassification T SET SORTNO = ( SELECT RANK*10 FROM ( SELECT LEV, IDA2A2, ROW_NUMBER() OVER ( PARTITION BY LEV ORDER BY LEV ASC ) AS rank FROM ( SELECT LEVEL
     * LEV, C.IDA2A2 FROM KETPartClassification C WHERE USECLAZ = 1 START WITH IDA2A2 = 897873122 CONNECT BY PRIOR IDA2A2 = IDA3A3 ) A ) WHERE IDA2A2 = T.IDA2A2 )
     */
    @Test
    public void testMigration() throws Exception {

	String noneClassType = PartClassificType.NONE.getCode();

	// root
	KETPartClassification root = new PartClazBuilder.Builder("부품분류체계").setClassEnName("Part Classification").setClassZhName("部品分类体系").setUseClaz(true).setSortNo(10)
	        .setPartClassificType(PartClassificType.NONE.getCode()).build();

	root = PartClazHelper.service.insertMigration(root, null);

	// sub1
	KETPartClassification motor1 = new PartClazBuilder.Builder("자동차사업부", null, true, noneClassType).build();
	motor1 = PartClazHelper.service.insertMigration(motor1, root);

	KETPartClassification elec1 = new PartClazBuilder.Builder("전자사업부", null, true, noneClassType).build();
	elec1 = PartClazHelper.service.insertMigration(elec1, root);

	KETPartClassification else1 = new PartClazBuilder.Builder("기타", null, true, noneClassType).build();
	else1 = PartClazHelper.service.insertMigration(else1, root);

	KETPartClassification mold1 = new PartClazBuilder.Builder("금형", null, true, noneClassType).build();
	mold1 = PartClazHelper.service.insertMigration(mold1, root);

	// sub2 MOTOR =======================================
	// ----
	KETPartClassification level21 = new PartClazBuilder.Builder("Connector(저전압)", null, true, noneClassType).build();
	level21 = PartClazHelper.service.insertMigration(level21, motor1);

	// ##########################################

	KETPartClassification level31 = new PartClazBuilder.Builder("Female HSG", null, true, noneClassType).build();
	level31 = PartClazHelper.service.insertMigration(level31, level21);

	KETPartClassification level32 = new PartClazBuilder.Builder("Male HSG", null, true, noneClassType).build();
	level32 = PartClazHelper.service.insertMigration(level32, level21);

	// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	KETPartClassification level41 = new PartClazBuilder.Builder("W to W", null, true, PartClassificType.ASSY.getCode()).build();
	level41 = PartClazHelper.service.insertMigration(level41, level32);

	// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	KETPartClassification level33 = new PartClazBuilder.Builder("Clip & Holder", null, true, noneClassType).build();
	level33 = PartClazHelper.service.insertMigration(level33, level21);

	KETPartClassification level34 = new PartClazBuilder.Builder("Female HSG Assy", null, true, noneClassType).build();
	level34 = PartClazHelper.service.insertMigration(level34, level21);

	KETPartClassification level35 = new PartClazBuilder.Builder("기타 Assy", null, true, noneClassType).build();
	level35 = PartClazHelper.service.insertMigration(level35, level21);

	// ##########################################

	// ----
	KETPartClassification level22 = new PartClazBuilder.Builder("Connector(고전압)", null, true, noneClassType).build();
	level22 = PartClazHelper.service.insertMigration(level22, motor1);

	// ----
	KETPartClassification level23 = new PartClazBuilder.Builder("Terminal", null, true, noneClassType).build();
	level23 = PartClazHelper.service.insertMigration(level23, motor1);

	// ----
	KETPartClassification level24 = new PartClazBuilder.Builder("자동차모듈", null, true, noneClassType).build();
	level24 = PartClazHelper.service.insertMigration(level24, motor1);

	// ----
	KETPartClassification level25 = new PartClazBuilder.Builder("전장모듈", null, true, noneClassType).build();
	level25 = PartClazHelper.service.insertMigration(level25, motor1);

	// ----
	KETPartClassification level26 = new PartClazBuilder.Builder("W/H", null, true, noneClassType).build();
	level26 = PartClazHelper.service.insertMigration(level26, motor1);

	// sub2 ELEC =======================================
	// ----
	KETPartClassification levelE21 = new PartClazBuilder.Builder("생활가전 커넥터", null, true, noneClassType).build();
	levelE21 = PartClazHelper.service.insertMigration(levelE21, elec1);

	// ----
	KETPartClassification levelE22 = new PartClazBuilder.Builder("Mobile", null, true, noneClassType).build();
	levelE22 = PartClazHelper.service.insertMigration(levelE22, elec1);

	// ----
	KETPartClassification levelE23 = new PartClazBuilder.Builder("Display", null, true, noneClassType).build();
	levelE23 = PartClazHelper.service.insertMigration(levelE23, elec1);

	// ----
	KETPartClassification levelE24 = new PartClazBuilder.Builder("Connector(전장파인피치그룹)", null, true, noneClassType).build();
	levelE24 = PartClazHelper.service.insertMigration(levelE24, elec1);

	// ----
	KETPartClassification levelE25 = new PartClazBuilder.Builder("Connector(전장멀티그룹)", null, true, noneClassType).build();
	levelE25 = PartClazHelper.service.insertMigration(levelE25, elec1);

	// ----
	KETPartClassification levelE26 = new PartClazBuilder.Builder("광통신", null, true, noneClassType).build();
	levelE26 = PartClazHelper.service.insertMigration(levelE26, elec1);

    }
    
    private void printClaz(List<KETPartClassification> list) {

	for (KETPartClassification claz : list) {
	    Kogger.debug(getClass(), "claz:" + claz.getClassKrName());
	}
    }
}
