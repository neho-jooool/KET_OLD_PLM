package ext.ket.part.base.service.internal.associate;

import java.util.ArrayList;
import java.util.List;

import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartClassificationRelation extends AbsAssociator implements AssociateClaz {

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    // 부품생성, 복사생성
    @Override
    public void associateCreate(WTPart part, KETPartClassification partClassification) throws Exception {

	if (part == null || partClassification == null)
	    return;
	KETPartClassLink oldClassLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class, ((WTPartMaster) part.getMaster()));
	if (oldClassLink == null) {
	    // 분류체계 생성
	    KETPartClassLink newClassLink = KETPartClassLink.newKETPartClassLink(partClassification, (WTPartMaster) part.getMaster());
	    PersistenceHelper.manager.save(newClassLink);
	}
    }

    // 부품 수정
    @Override
    public void associateUpdate(WTPart part, KETPartClassification partClassification) throws Exception {
	// nothing to do
    }
    
    // 부품 삭제 - Mast 삭제 시만 호출됨
    @Override
    public void deAssociate(WTPart part) throws Exception {

	if (part != null) {
	    // 분류체계 삭제
	    List<PartClassAttrLinkDTO> attrLinkList = new ArrayList<PartClassAttrLinkDTO>();
	    KETPartClassLink classLink = query.queryForFirstLinkByRoleB(KETPartClassLink.class, WTPartMaster.class, ((WTPartMaster) part.getMaster()));
	    if (classLink != null) {
		PersistenceHelper.manager.delete(classLink);
	    }
	}
    }
    
    // 부품 개정
    @Override
    public void reviseAssociate(WTPart beforePart, WTPart afterPart) throws Exception {
	// nothing to do
    }

}
