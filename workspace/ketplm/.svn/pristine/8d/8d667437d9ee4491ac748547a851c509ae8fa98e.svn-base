package ext.ket.dms.service;

import wt.method.RemoteInterface;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.project.E3PSProject;
import ext.ket.dms.entity.ProjectDocumentDTO;
import ext.ket.shared.service.CommonServiceInterface;

/**
 * @클래스명 : KETProjectDocumentService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@RemoteInterface
public interface KETProjectDocumentService extends CommonServiceInterface<ProjectDocumentDTO, KETProjectDocument> {

	public StatementSpec getListProjectDocumentQuerySpec(ProjectDocumentDTO paramObject) throws Exception;

	public StatementSpec getProjectDocumentQuerySpec(ProjectDocumentDTO paramObject) throws Exception;

	public StatementSpec getDocumentProjectLinkJoinQuery(QuerySpec spec, ProjectDocumentDTO paramObject) throws Exception;

	public StatementSpec getDocumentOutputLinkJoinQuery(QuerySpec spec, ProjectDocumentDTO paramObject) throws Exception;

	public void changeProjectRankModify(E3PSProject project, String rank) throws Exception;

	KETProjectDocument revise(KETProjectDocument doc) throws Exception;
}
