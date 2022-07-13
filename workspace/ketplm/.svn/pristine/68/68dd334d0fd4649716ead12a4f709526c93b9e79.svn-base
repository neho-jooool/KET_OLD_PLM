package ext.ket.part.base.service.internal;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.part.WTPart;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.project.ProjectMaster;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.base.service.internal.associate.Associatable;
import ext.ket.part.base.service.internal.associate.AssociateClaz;
import ext.ket.part.base.service.internal.associate.AssociateEpmDoc;
import ext.ket.part.base.service.internal.associate.AssociateProject;
import ext.ket.part.base.service.internal.associate.PartClassificationRelation;
import ext.ket.part.base.service.internal.associate.PartDocumentRelation;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.part.base.service.internal.associate.PartProjectRelation;
import ext.ket.part.entity.KETPartClassification;

public class PartAssociationUtil {

	// 부품 생성시 호출
	public void associateWTPartCreate(WTPart part, KETPartClassification partClassification, KETProjectDocument projectDocument, EPMDocument epmDocument,
			ProjectMaster projectMaster) throws Exception {

		// 분류
		AssociateClaz ac = new PartClassificationRelation();
		ac.associateCreate(part, partClassification);
		// 문서 - 불필요
		// AssociateDoc ad = new PartDocumentRelation();
		// ad.associateCreate(part, projectDocument);
		// 도면 - 불필요
		// AssociateEpmDoc ae = new PartEpmRelation();
		// ae.associateCreate(part, epmDocument);
		// 프로젝트
		AssociateProject ap = new PartProjectRelation();
		ap.associateCreate(part, projectMaster);

	}

	// 부품 복사 생성시 호출
	public void associateWTPartCopyCreate(WTPart beforePart, WTPart afterPart, KETPartClassification partClassification, ProjectMaster projectMaster, String partCopyEpm,
			String partCopyDoc, String partCopyBom, String ecoNo) throws Exception {

		// 분류 - 필수
		AssociateClaz ac = new PartClassificationRelation();
		ac.associateCreate(afterPart, partClassification);

		// 문서
		// if ("Y".equals(partCopyDoc)) {
		// AssociateDoc ad = new PartDocumentRelation();
		// ad.associateCreateCopy(beforePart, afterPart);
		// }

		// 도면
		if ("Y".equals(partCopyEpm)) {
			AssociateEpmDoc ae = new PartEpmRelation();
			ae.associateCreateCopy(beforePart, afterPart);
		}

		// 프로젝트 - 필수
		AssociateProject ap = new PartProjectRelation();
		ap.associateCreate(afterPart, projectMaster);

		// BOM
		if ("Y".equals(partCopyBom)) {
			KETBOMQueryBean bean = new KETBOMQueryBean();
			if (StringUtils.isEmpty(ecoNo))
				throw new Exception("ECO NO는 null일 수가 없습니다.");

			bean.copyBom(ecoNo, beforePart, afterPart);
		}

	}

	// 부품 수정시 호출 + 부품 결재완료후 수정에서 호출
	public void associateWTPartModify(WTPart part, KETPartClassification partClassification, KETProjectDocument projectDocument, EPMDocument epmDocument,
			ProjectMaster projectMaster) throws Exception {

		AssociateClaz ac = new PartClassificationRelation();
		// 분류 - 불필요
		// ac.associateUpdate(part, partClassification);
		// 문서 - 불필요
		// AssociateDoc ad = new PartDocumentRelation();
		// ad.associateUpdate(part, projectDocument);
		// 도면 - 불필요
		// AssociateEpmDoc ae = new PartEpmRelation();
		// ae.associateUpdate(part, epmDocument);
		// 프로젝트
		AssociateProject ap = new PartProjectRelation();
		ap.associateUpdate(part, projectMaster);

	}

	// 부품 삭제시 호출 - '0' 리비전 삭제시(Mast 삭제시) 호출
	public void deAssociateWTPartMast(WTPart part) throws Exception {

		// 분류
		Associatable as = new PartClassificationRelation();
		as.deAssociate(part);
		// 문서 - 불필요
		// as = new PartDocumentRelation();
		// as.deAssociate(part);
		// 도면 - 불필요
		// as = new PartEpmRelation();
		// as.deAssociate(part);
		// 프로젝트
		as = new PartProjectRelation();
		as.deAssociate(part);
	}

	// 부품 개정시 호출
	public void reviseAssociateWTPart(WTPart beforePart, WTPart afterPart) throws Exception {

		Associatable as = new PartClassificationRelation();
		// 분류 - 불필요
		// as = new PartClassificationRelation();
		// as.reviseAssociate(beforePart, afterPart);
		// 문서
		as = new PartDocumentRelation();
		as.reviseAssociate(beforePart, afterPart);
		// 도면
		as = new PartEpmRelation();
		as.reviseAssociate(beforePart, afterPart);
		// 프로젝트
		as = new PartProjectRelation();
		as.reviseAssociate(beforePart, afterPart);

	}

	// 부품 삭제시 호출 - 개정 삭제시 호출
	public void deAssociateWTPartRev(WTPart part) throws Exception {

		// 분류 : 삭제하면 안됨
		// Associatable as = new PartClassificationRelation();
		// as.deAssociate(part);
		// 문서 - 불필요
		// as = new PartDocumentRelation();
		// as.deAssociate(part);
		// 도면 - 불필요
		// as = new PartEpmRelation();
		// as.deAssociate(part);
		// 프로젝트
		Associatable as = new PartProjectRelation();
		as.deAssociate(part);

	}

}
