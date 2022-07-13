package ext.ket.dqm.service;

import java.util.HashMap;
import java.util.List;

import wt.fc.QueryResult;
import wt.method.RemoteInterface;
import wt.org.WTUser;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface DqmService extends CommonServiceInterface<KETDqmDTO, KETDqmHeader> {

    public KETDqmDTO modifyRaise(KETDqmDTO paramObject) throws Exception;

    public KETDqmDTO modifyAction(KETDqmDTO paramObject) throws Exception;

    public boolean authCheck(String oid, String type) throws Exception;

    public KETDqmDTO modifyClose(KETDqmDTO paramObject) throws Exception;

    public KETDqmDTO saveRaise(KETDqmDTO paramObject) throws Exception;

    public KETDqmDTO saveAction(KETDqmDTO paramObject) throws Exception;

    public KETDqmDTO saveClose(KETDqmDTO paramObject) throws Exception;

    public void saveReason(KETDqmDTO paramObject) throws Exception;

    public void reReview(KETDqmDTO paramObject) throws Exception;

    public String migration(List<HashMap<String, String>> paramList) throws Exception;

    public void close(KETDqmDTO paramObject) throws Exception;

    public void deleteRaise(KETDqmDTO paramObject) throws Exception;

    public void deleteAction(KETDqmDTO paramObject) throws Exception;

    public void deleteClose(KETDqmDTO paramObject) throws Exception;

    public void deleteAll() throws Exception;

    public void dqmTodoComplete() throws Exception;

    public QueryResult getSearchList(KETDqmDTO paramObject) throws Exception;

    public void dqmDelayMailSend(String IsMailsend) throws Exception;

    public void actionComplete(KETDqmDTO paramObject) throws Exception;

    public void deleteDQMWorkItemByInwork(KETDqmHeader ketDqmHeader) throws Exception;

    public void createDQMWorkItemByInwork(KETDqmHeader ketDqmHeader) throws Exception;

    public KETDqmRaise setDqmRaiseCreatorUser(KETDqmRaise obj, WTUser user) throws Exception;

    public KETDqmRaise setDqmRaiseActionUser(KETDqmRaise obj, WTUser user) throws Exception;

    public List<KETDqmDTO> findDqmByProject(String pjtNo) throws Exception;
}