package ext.ket.wfm.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.fc.QueryResult;
import wt.lifecycle.LifeCycleManaged;
import wt.method.RemoteInterface;
import wt.org.WTPrincipal;
import wt.workflow.work.WorkItem;
import e3ps.project.TemplateProject;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.service.CommonServiceInterface;

/**
 * @클래스명 : KETWorkspaceService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 9.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@RemoteInterface
public interface KETWorkspaceService extends CommonServiceInterface<BaseDTO, BaseDTO> {

    /**
     * @param dto
     * @return
     * @throws Exception
     * @메소드명 : getMyProjectList
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public QueryResult getMyProjectList(BaseDTO dto) throws Exception;

    /**
     * @param project
     * @param withPM
     * @return
     * @메소드명 : getTeamUsers
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public QueryResult getTeamUsers(TemplateProject project, String withPM);

    /**
     * @param lcm
     * @return
     * @throws Exception
     * @메소드명 : getReceiptDate
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public String getReceiptDate(LifeCycleManaged lcm) throws Exception;

    /**
     * @param lcm
     * @param receiptDate
     * @return
     * @throws Exception
     * @메소드명 : saveReceiptDate
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public boolean saveReceiptDate(LifeCycleManaged lcm, Timestamp receiptDate) throws Exception;

    /**
     * @param workitem
     * @param newOwner
     * @param reason
     * @return
     * @throws Exception
     * @메소드명 : delegateTodoWorkItem
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 3.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public boolean delegateTodoWorkItem(WorkItem workitem, WTPrincipal newOwner, String reason) throws Exception;

    /**
     * @return
     * @throws Exception
     * @메소드명 : getMainContentsMyTask
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public HashMap<String, String> getMainContentsMyTask() throws Exception;

    /**
     * 
     * @return
     * @throws Exception
     * @메소드명 : getFilterUncompletedTodoCount
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public int getFilterUncompletedTodoCount() throws Exception;
    
    /**
     * 부품의 도면과 부품에 대해서만 TO-DO를 하나로 처리한다. 즉 위임을 하나로 처리하려고 메소드 만듬
     * 
     * @param workItem
     * @return
     * @throws Exception
     * @메소드명 : getRelatedWorkItem
     * @작성자 : yjlee
     * @작성일 : 2015. 1. 29.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public List<WorkItem> getRelatedWorkItem(WorkItem workItem, String prodType) throws Exception;
    
    /**
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : getMyRetireUserList
     * @작성자 : 황정태
     * @작성일 : 2017. 11. 20.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public List<Map<String, String>> getMyRetireUserList(String id) throws Exception;
    
    /**
     * 
     * 
     * @return
     * @throws Exception
     * @메소드명 : sendMailDelayWorkItem
     * @작성자 : 황정태
     * @작성일 : 2017. 12. 12.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    
    public void sendMailDelayWorkItem() throws Exception;
    
    /**
     * 
     * 
     * @throws Exception
     * @메소드명 : completeExceedReceive
     * @작성자 : 황정태
     * @작성일 : 2018. 10. 11.
     * @설명 : 한달 지난 수신목록에 대해 일괄완료처리
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public void completeExceedReceive() throws Exception;
    
    /**
     * 
     * 
     * @throws Exception
     * @메소드명 : sendMailRetireTargetList
     * @작성자 : 황정태
     * @작성일 : 2020. 4. 13.
     * @설명 : 퇴사예정자의 팀장에게 퇴사 전까지 알람메일을 발송한다
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public void sendMailRetireTargetList() throws Exception;
}
