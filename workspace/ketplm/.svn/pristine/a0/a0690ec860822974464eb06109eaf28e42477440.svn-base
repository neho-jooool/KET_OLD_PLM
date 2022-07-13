package ext.ket.wfm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.method.RemoteInterface;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.workflow.work.WorkItem;
import ext.ket.shared.service.CommonServiceInterface;
import ext.ket.wfm.entity.KETFavorApprovalUser;
import ext.ket.wfm.entity.WorkItemDTO;

/**
 * @클래스명 : WorkflowService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@RemoteInterface
public interface KETWorkflowService extends CommonServiceInterface<WorkItemDTO, WorkItemDTO> {

    public QuerySpec getFilterUncompletedWorkItems(WorkItemDTO workItemDTO, boolean isTodo) throws Exception;

    public boolean createMyFavorApprovalUser(String[] userids) throws Exception;

    public boolean deleteMyFavorApprovalUser(String[] userids) throws Exception;

    public KETFavorApprovalUser getMyFavorApprovalUser() throws Exception;

    public boolean doBatchCompleteWorkItem(String[] workItemoids, String comments) throws Exception;

    public boolean isCancelApproval(String pbooid) throws Exception;

    public boolean doCancelApproval(String pbooid) throws Exception;

    public boolean doRequestDistribute(HashMap<String, Object> request) throws Exception;

    public boolean delegateWorkItem(WorkItem workItem, WTPrincipal newOwner) throws Exception;

    public Map<String, String> getMyTodoCnt() throws Exception;

    public List<WorkItemDTO> getUncompletedWorkItems() throws Exception;

    public void deleteWfProcessTest() throws Exception;

    public int getFilterUncompletedWorkItemCount() throws Exception;

    public WorkItem getWorkItem(Persistable pbo, WTUser user) throws Exception;

    public void initWfProcessInfo(ObjectReference self);

    public void doAfterApprovalAction(ObjectReference self);

    public boolean getStart_flag(ObjectReference self);

    StatementSpec getMyTodoQuery(WorkItemDTO workItemDTO, boolean isTodo) throws Exception;

}
