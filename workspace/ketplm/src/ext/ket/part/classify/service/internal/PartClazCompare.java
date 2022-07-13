package ext.ket.part.classify.service.internal;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import ext.ket.part.classify.oxm.ClazXmlBodyObject;
import ext.ket.part.classify.oxm.ClazXmlGridObject;
import ext.ket.part.classify.oxm.ClazXmlPageObject;
import ext.ket.part.classify.oxm.ClazXmlRowObject;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassNamingLink;
import ext.ket.part.entity.KETPartClassTreeLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartNaming;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartClazCompare {

    private PartClazBuilder builder = new PartClazBuilder();
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public void compare(ClazXmlGridObject grid) throws Exception {

	ClazXmlBodyObject body = grid.getClazXmlBodyObject();
	ClazXmlPageObject page = body.getClazXmlPageObject();
	List<ClazXmlRowObject> rowList = page.getClazXmlRowObjectList();

	if (rowList.size() > 0) {
	    // 최상위 분류체계
	    ClazXmlRowObject root = rowList.get(0);
	    KETPartClassification myClassification = calCompare(root, null, true);
	    makeSubCompare(root, myClassification, false);
	    Kogger.debug(getClass(), "root PartClassification:" + myClassification.getClassKrName() + " " + myClassification);
	}

    }

    // 재귀함수로 Tree를 순회하면서 전체 데이터를 처리한다.
    private void makeSubCompare(ClazXmlRowObject parentRow, KETPartClassification parent, boolean isRoot) throws Exception {

	List<ClazXmlRowObject> subList = parentRow.getClazXmlRowObjectList();
	for (int k = 0; k < subList.size(); k++) {
	    ClazXmlRowObject sonRow = subList.get(k);
	    KETPartClassification myClassification = calCompare(sonRow, parent, false);
	    if (myClassification == null) {
		Kogger.debug(getClass(), "sub PartClassification: is null");
	    } else {
		Kogger.debug(getClass(), "sub PartClassification:" + myClassification.getClassKrName() + " " + myClassification);
	    }

	    makeSubCompare(sonRow, myClassification, false);
	}
    }

    // 하나의 classification에 대해서 속성, 부모 자식 관계, 속성등을 처리한다.
    private KETPartClassification calCompare(ClazXmlRowObject row, KETPartClassification parent, boolean isRoot) throws Exception {

	Kogger.debug(getClass(), "calCompare PartClassification start!");
	String oid = row.getClazOid();
	boolean isClazNew = StringUtils.isEmpty(oid);

	KETPartClassification newPartClassification = null;
	KETPartClassification modPartClassification = null;
	KETPartClassification partClassification = null;

	// TKLEE 분류체계 삭제 처리
	boolean isDeleted = "1".equals(row.getDeleteFlag());

	// 삭제를 처리한다.
	if (isDeleted) {

	    Kogger.debug(getClass(), "Deleted Part Classification name :" + row.getClassKrName());
	    Kogger.debug(getClass(), "Deleted Part Classification Oid :" + oid);

	    // Client에서 garbage가 넘어올 수 있다. => 즉 신규 생성 후 삭제한 data도 넘어온다.
	    if (StringUtils.isEmpty(oid) || oid.indexOf(":") == -1) {
		return null;
	    }

	    KETPartClassification delPartClassification = (KETPartClassification) CommonUtil.getObject(oid);

	    // 연결된 부품이 있으면 삭제 Exception 발생하도록 함.
	    KETPartClassLink partClassLink = query.queryForFirstLinkByRoleA(KETPartClassification.class, KETPartClassLink.class, delPartClassification);
	    if (partClassLink != null)
		throw new Exception("분류체계에 연결된 부품이 존재하여, 삭제할 수 없습니다.");

	    // 연관 링크 삭제
	    // Attribute 삭제
	    List<KETPartClassAttrLink> oldAttrLinkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class, delPartClassification);
	    for (KETPartClassAttrLink partAttrLink : oldAttrLinkList) {
		Kogger.debug(getClass(), "delete KETPartClassAttrLink :" + partAttrLink);
	    }
	    ObjectUtil.deletePersistableList(oldAttrLinkList);

	    // Naming 삭제
	    KETPartClassNamingLink oldNamingLink = query.queryForFirstLinkByRoleB(KETPartClassNamingLink.class, KETPartClassification.class, delPartClassification);
	    if (oldNamingLink != null) {
		KETPartNaming oldPartNaming = oldNamingLink.getNaming();
		Kogger.debug(getClass(), "del Naming :" + oldPartNaming.getNamingCode() + " " + oldPartNaming.getNamingName());
		ObjectUtil.deletePersistableWithAdmin(oldNamingLink);
	    }

	    // 삭제 부품분류
	    Kogger.debug(getClass(), "delete :" + row.getClassKrName());
	    delPartClassification = (KETPartClassification) PersistenceHelper.manager.delete(delPartClassification);
	    return delPartClassification;
	}

	// 추가, 수정
	if (isClazNew) {

	    Kogger.debug(getClass(), "new :" + row.getClassKrName());
	    newPartClassification = KETPartClassification.newKETPartClassification();
	    builder.buildXml2Pers(row, newPartClassification);
	    newPartClassification = (KETPartClassification) PersistenceHelper.manager.save(newPartClassification);
	    partClassification = newPartClassification;
	    oid = CommonUtil.getOIDString(newPartClassification);

	} else {

	    Kogger.debug(getClass(), "modify :" + row.getClassKrName());
	    modPartClassification = (KETPartClassification) CommonUtil.getObject(oid);
	    builder.buildXml2Pers(row, modPartClassification);
	    modPartClassification = (KETPartClassification) PersistenceHelper.manager.save(modPartClassification);
	    partClassification = modPartClassification;
	}

	// Parent Link - Classification간의 Link
	if (!isRoot) {
	    if (isClazNew) {
		Kogger.debug(getClass(), "new partClassTreeLink");
		KETPartClassTreeLink partClassTreeLink = KETPartClassTreeLink.newKETPartClassTreeLink(parent, partClassification);
		partClassTreeLink = (KETPartClassTreeLink) PersistenceHelper.manager.save(partClassTreeLink);
	    }
	}

	// -. Attribute 연결되는 essential, checked, index(Row), index(Col) 가져오기
	// Part AttrubuteOid 처리 - 별도로 처리함, 부품분류 삭제 시에만 속성link 삭제 해 줌.
	// String partAttributeOidArray = row.getPartAttributeOid();
	// if (!StringUtils.isEmpty(partAttributeOidArray)) {
	//
	// Kogger.debug(getClass(), "Part AttrubuteOid 처리");
	// // 화면에 등록된 AttrList 가져오기
	// List<KETPartAttribute> newAttrList = new ArrayList<KETPartAttribute>();
	// String[] partAttributeItems = partAttributeOidArray.split(",");
	// for (String partAttrItem : partAttributeItems) {
	// KETPartAttribute attr = (KETPartAttribute) CommonUtil.getObject(partAttrItem);
	// if (attr != null)
	// newAttrList.add(attr);
	// }
	//
	// if (isClazNew) {
	// // 추가
	// for (KETPartAttribute partAttribute : newAttrList) {
	// KETPartClassAttrLink partClassAttrLink = KETPartClassAttrLink.newKETPartClassAttrLink(partAttribute, partClassification);
	// Kogger.debug(getClass(), "add Attrubute :" + partAttribute.getAttrCode() + " " + partAttribute.getAttrName());
	// PersistenceHelper.manager.save(partClassAttrLink);
	// }
	// } else {
	//
	// List<KETPartAttribute> oldAttrList = query.queryForListAByRoleB(KETPartAttribute.class, KETPartClassAttrLink.class, KETPartClassification.class, partClassification);
	//
	// PersistableCompareUtil<KETPartAttribute> comparator = new PersistableCompareUtil<KETPartAttribute>();
	// comparator.compare(oldAttrList, newAttrList);
	// // List<KETPartAttribute> sameList = comparator.getSameList();
	// List<KETPartAttribute> addList = comparator.getAddList();
	// List<KETPartAttribute> deleteList = comparator.getDeleteList();
	//
	// // 추가
	// for (KETPartAttribute partAttribute : addList) {
	// KETPartClassAttrLink partClassAttrLink = KETPartClassAttrLink.newKETPartClassAttrLink(partAttribute, partClassification);
	// Kogger.debug(getClass(), "add Attrubute :" + partAttribute.getAttrCode() + " " + partAttribute.getAttrName());
	// PersistenceHelper.manager.save(partClassAttrLink);
	// }
	// // 삭제
	// for (KETPartAttribute partAttribute : deleteList) {
	// Kogger.debug(getClass(), "delete Attrubute :" + partAttribute.getAttrCode() + " " + partAttribute.getAttrName());
	// }
	// ObjectUtil.deletePersistableList(deleteList);
	// }
	//
	// }

	// Part Naming 처리
	// TKLEE PartNaming 처리
	String partNamingOid = row.getPartNamingName();
	Kogger.debug(getClass(), "Part Naming 처리");
	if (isClazNew) {

	    if (!StringUtils.isEmpty(partNamingOid)) { // 신규 등록

		KETPartNaming partNaming = (KETPartNaming) CommonUtil.getObject(partNamingOid);
		KETPartClassNamingLink namingLink = KETPartClassNamingLink.newKETPartClassNamingLink(partNaming, partClassification);
		Kogger.debug(getClass(), "add Naming :" + partNaming.getNamingCode() + " " + partNaming.getNamingName());
		PersistenceHelper.manager.save(namingLink);
	    }

	} else {

	    if (!StringUtils.isEmpty(partNamingOid)) { // 수정모드 체크

		KETPartNaming newPartNaming = (KETPartNaming) CommonUtil.getObject(partNamingOid);

		// LINK 체크
		KETPartClassNamingLink oldNamingLink = query.queryForFirstLinkByRoleB(KETPartClassNamingLink.class, KETPartClassification.class, partClassification);
		if(oldNamingLink == null){ // 신규 등록
		   KETPartClassNamingLink namingLink = KETPartClassNamingLink.newKETPartClassNamingLink(newPartNaming, partClassification);
		   Kogger.debug(getClass(), "add Naming :" + newPartNaming.getNamingCode() + " " + newPartNaming.getNamingName());
		   namingLink = (KETPartClassNamingLink)PersistenceHelper.manager.save(namingLink);
		}else{
        		KETPartNaming oldPartNaming = oldNamingLink.getNaming();
        
        		if (PersistenceHelper.isEquivalent(newPartNaming, oldPartNaming)) {
        		    // SAME --
        		} else {
        		    // 추가
        		    KETPartClassNamingLink newNamingLink = KETPartClassNamingLink.newKETPartClassNamingLink(newPartNaming, partClassification);
        		    Kogger.debug(getClass(), "add Naming :" + newPartNaming.getNamingCode() + " " + newPartNaming.getNamingName());
        		    PersistenceHelper.manager.save(newNamingLink);
        		    // 삭제
        		    Kogger.debug(getClass(), "del Naming :" + oldPartNaming.getNamingCode() + " " + oldPartNaming.getNamingName());
        		    ObjectUtil.deletePersistableWithAdmin(oldNamingLink);
        		}
		}
	    } else { // 삭제체크

		// LINK 체크
		KETPartClassNamingLink oldNamingLink = query.queryForFirstLinkByRoleB(KETPartClassNamingLink.class, KETPartClassification.class, partClassification);
		// 삭제
		if (oldNamingLink != null) {
		    Kogger.debug(getClass(), "del Naming :" + oldNamingLink.getNaming().getNamingCode() + " " + oldNamingLink.getNaming().getNamingName());
		    ObjectUtil.delete(oldNamingLink);
		}
	    }
	}

	return partClassification;
    }

    public static void main(String[] args) {

    }
}
