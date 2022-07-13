package ext.ket.part.base.service.internal.associate;

import wt.part.WTPart;
import e3ps.dms.entity.KETProjectDocument;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

// KETDocumentPartLink
public class PartDocumentRelation extends AbsAssociator implements AssociateDoc {

	private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

	// 부품 생성
	@Override
	public void associateCreate(WTPart part, KETProjectDocument projectDocument) throws Exception {
		// nothing
	}

	// 부품 수정
	@Override
	public void associateUpdate(WTPart part, KETProjectDocument projectDocument) throws Exception {
		// nothing
	}

	// 부품 삭제
	@Override
	public void deAssociate(WTPart part) throws Exception {
		// nothing
	}

	// 부품 개정 : 무조건 최신 문서와 연결한다.
	@Override
	public void reviseAssociate(WTPart beforePart, WTPart afterPart) throws Exception {
		associateCreateCopy(beforePart, afterPart);
	}

	// 부품 복사 생성 : 무조건 최신 문서와 연결한다.
	@Override
	public void associateCreateCopy(WTPart beforePart, WTPart afterPart) throws Exception {

		// ########################## 2019.03.06 주석처리 ##########################################
		// Map<String, KETProjectDocument> docMap = new HashMap<String, KETProjectDocument>();
		// QueryResult result = VersionControlHelper.service.allIterationsFrom(beforePart);
		// String revision = beforePart.getVersionIdentifier().getValue();
		// while(result.hasMoreElements()){
		// WTPart temp = (WTPart)result.nextElement();
		// if(revision.equals(temp.getVersionIdentifier().getValue())){
		// Kogger.debug(getClass(), "# Iteration:" + temp.getVersionIdentifier().getValue() + "." + temp.getIterationIdentifier().getValue());
		// QueryResult r = PersistenceHelper.manager.navigate(temp, "document", KETDocumentPartLink.class, false);
		// while (r.hasMoreElements()) {
		// KETDocumentPartLink DpLink = (KETDocumentPartLink) r.nextElement();
		// KETProjectDocument document = DpLink.getDocument();
		// if(docMap.containsKey(document.getNumber())){
		// KETProjectDocument oldDoc = docMap.get(document.getNumber());
		// if (document.getVersionIdentifier().getSeries().greaterThan(oldDoc.getVersionIdentifier().getSeries())) {
		// Kogger.debug(getClass(), "# Iteration:" + temp.getVersionIdentifier().getValue() + "." + temp.getIterationIdentifier().getValue());
		// docMap.put(document.getNumber(), document);
		// }
		// }else{
		// docMap.put(document.getNumber(), document);
		// }
		// }
		// }
		// }
		//
		// Iterator<String> it = docMap.keySet().iterator();
		// while(it.hasNext()){
		// String docNo = it.next();
		// KETProjectDocument document = docMap.get(docNo);
		//
		// // latest
		// if(VersionControlHelper.isLatestIteration(document)){
		// KETDocumentPartLink link = KETDocumentPartLink.newKETDocumentPartLink(document, afterPart);
		// PersistenceHelper.manager.save(link);
		// }else{
		// document = (KETProjectDocument)VersionControlHelper.getLatestIteration(document, false);
		// KETDocumentPartLink link = KETDocumentPartLink.newKETDocumentPartLink(document, afterPart);
		// PersistenceHelper.manager.save(link);
		// }
		//
		// }

	}

}
