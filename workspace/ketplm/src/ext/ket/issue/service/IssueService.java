package ext.ket.issue.service;

import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import e3ps.common.web.PageControl;
import ext.ket.issue.entity.KETIssueMasterDTO;
import ext.ket.issue.entity.KETIssueTaskDTO;
import ext.ket.issue.entity.KETIssueToDoDTO;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 11. 17. 오전 10:49:30
 * @Pakage ext.ket.project.cost.service
 * @class CostBomService
 *********************************************************/
@RemoteInterface
public interface IssueService {

    /**
     * <pre>
     * @description 고객 요구사항 저장
     * @author dhkim
     * @date 2018. 5. 25. 오후 12:10:12
     * @method saveIssueMaster
     * @param issueDTO
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> saveIssueMaster(KETIssueMasterDTO issueDTO, Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description 세부 요청사항 저장
     * @author dhkim
     * @date 2018. 5. 28. 오후 3:38:58
     * @method saveIssueTask
     * @param itDTO
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> saveIssueTask(KETIssueTaskDTO itDTO, Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description 고객대응관리 검색 목록
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:34:56
     * @method findPagingList
     * @param im
     * @return PageControl
     * @throws Exception
     * </pre>
     */
    PageControl findPagingList(KETIssueMasterDTO im) throws Exception;

    /**
     * <pre>
     * @description 고객대응관리 To-Do 검색 목록
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:35:24
     * @method findToDoPagingList
     * @param itd
     * @return PageControl
     * @throws Exception
     * </pre>
     */
    PageControl findToDoPagingList(KETIssueToDoDTO itd) throws Exception;

    /**
     * <pre>
     * @description 고객 요구사항 완료
     * @author dhkim
     * @date 2018. 6. 5. 오후 2:35:37
     * @method completeIssueMaster
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> completeIssueMaster(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 7. 오후 4:00:44
     * @method getIssueMasterList
     * @param pboOid
     * @return List<KETIssueMasterDTO>
     * @throws Exception
     * </pre>
     */
    List<KETIssueMasterDTO> getIssueMasterList(String pboOid) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 11. 오후 4:56:53
     * @method getIssueTodoListCnt
     * @return int
     * </pre>
     * 
     * @throws Exception
     */
    int getIssueTodoListCnt() throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 21. 오전 11:00:33
     * @method changeIssuePBO
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> changeIssuePBO(Map<String, Object> reqMap) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 27. 오후 4:55:40
     * @method findTaskPagingList
     * @param im
     * @return PageControl
     * @throws Exception
     * </pre>
     */
    PageControl findTaskPagingList(KETIssueMasterDTO im) throws Exception;

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 8. 8. 오후 4:29:09
     * @method deleteIssue
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    Map<String, Object> deleteIssue(Map<String, Object> reqMap) throws Exception;

    /**
     * 
     * 
     * @param itDTO
     * @return
     * @throws Exception
     * @메소드명 : rejectIssueTask
     * @작성자 : neho
     * @작성일 : 2020. 3. 18.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Map<String, Object> rejectIssueTask(KETIssueTaskDTO itDTO) throws Exception;

    /**
     * 
     * 
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : reStartIssueTask
     * @작성자 : neho
     * @작성일 : 2020. 3. 18.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Map<String, Object> reStartIssueTask(Map<String, Object> reqMap) throws Exception;

    /**
     * 
     * 
     * @param reqMap
     * @return
     * @throws Exception
     * @메소드명 : meetingTarget
     * @작성자 : admin
     * @작성일 : 2021. 12. 8.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Map<String, Object> meetingTarget(Map<String, Object> reqMap) throws Exception;

}