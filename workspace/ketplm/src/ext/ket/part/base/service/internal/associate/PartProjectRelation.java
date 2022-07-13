package ext.ket.part.base.service.internal.associate;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import e3ps.project.ProjectMaster;
import ext.ket.part.entity.KETPartProjectLink;
import ext.ket.part.util.PartUtil;

public class PartProjectRelation extends AbsAssociator implements AssociateProject {

    // 부품 생성
    @Override
    public void associateCreate(WTPart part, ProjectMaster projectMaster) throws Exception {

	if(part == null || projectMaster == null) return;
	
	KETPartProjectLink currentProjectLink = null;
	QueryResult qr = PartUtil.getPartProjectLink(part, null);
	while (qr.hasMoreElements()) {
	    Object[] objs = (Object[]) qr.nextElement();
	    currentProjectLink = (KETPartProjectLink) objs[0];
	}

	if (currentProjectLink == null) { // 추가

	    KETPartProjectLink newPartProjectLink = KETPartProjectLink.newKETPartProjectLink(projectMaster, part);
	    PersistenceHelper.manager.save(newPartProjectLink);
	}
    }
    
    // 부품 수정
    @Override
    public void associateUpdate(WTPart part, ProjectMaster projectMaster) throws Exception {
	
	if(part == null) return;
	
	KETPartProjectLink currentProjectLink = null;
	QueryResult qr = PartUtil.getPartProjectLink(part, null);
	while (qr.hasMoreElements()) {
	    Object[] objs = (Object[]) qr.nextElement();
	    currentProjectLink = (KETPartProjectLink) objs[0];
	}

	if (currentProjectLink == null && projectMaster != null) { // 추가
	    
	    KETPartProjectLink newPartProjectLink = KETPartProjectLink.newKETPartProjectLink(projectMaster, part);
	    PersistenceHelper.manager.save(newPartProjectLink);
	    
	}else if (currentProjectLink != null && projectMaster == null){ // 삭제
	    
	    PersistenceHelper.manager.delete(currentProjectLink);
	    
	}else if (currentProjectLink != null && projectMaster != null){ // 변경
	    
	    PersistenceHelper.manager.delete(currentProjectLink);
	    KETPartProjectLink newPartProjectLink = KETPartProjectLink.newKETPartProjectLink(projectMaster, part);
	    PersistenceHelper.manager.save(newPartProjectLink);
	    
	}
	
    }

    // 부품 삭제
    @Override
    public void deAssociate(WTPart part) throws Exception {

	KETPartProjectLink currentProjectLink = null;
	QueryResult qr = PartUtil.getPartProjectLink(part, null);
	while (qr.hasMoreElements()) {
	    Object[] objs = (Object[]) qr.nextElement();
	    currentProjectLink = (KETPartProjectLink) objs[0];
	}

	if (currentProjectLink != null) { // 현재 연관이 있으면 삭제
	    PersistenceHelper.manager.delete(currentProjectLink);
	}

    }

    // 부품 개정
    @Override
    public void reviseAssociate(WTPart beforePart, WTPart afterPart) throws Exception {

	// 이전 연결 확인
	KETPartProjectLink beforeProjectLink = null;
	QueryResult qr = PartUtil.getPartProjectLink(beforePart, null);
	while (qr.hasMoreElements()) {
	    Object[] objs = (Object[]) qr.nextElement();
	    beforeProjectLink = (KETPartProjectLink) objs[0];
	}

	KETPartProjectLink afterProjectLink = null;
	
	if (beforeProjectLink == null) { // 이전에 연관이 없으면

	    qr = PartUtil.getPartProjectLink(afterPart, null);
	    while (qr.hasMoreElements()) {
		Object[] objs = (Object[]) qr.nextElement();
		afterProjectLink = (KETPartProjectLink) objs[0];
	    }

	    // 기존에 없으면 삭제
	    if (afterProjectLink != null) {
		PersistenceHelper.manager.delete(afterProjectLink);
	    }

	} else { // 이전에 연관이 있으면

	    qr = PartUtil.getPartProjectLink(afterPart, null);
	    while (qr.hasMoreElements()) {
		Object[] objs = (Object[]) qr.nextElement();
		afterProjectLink = (KETPartProjectLink) objs[0];
	    }

	    // 기존에 있는데 없으면 생성
	    if (afterProjectLink == null) {
		KETPartProjectLink newPartProjectLink = KETPartProjectLink.newKETPartProjectLink(beforeProjectLink.getProject(), afterPart);
		PersistenceHelper.manager.save(newPartProjectLink);
	    }
	}

    }

}
