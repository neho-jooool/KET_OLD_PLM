package ext.ket.edm.approval;

import wt.change2.WTChangeOrder2;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.method.RemoteInterface;
import wt.util.WTException;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import ext.ket.edm.entity.KETEpmApprovalHis;

@RemoteInterface
public interface KetEpmApprovalService {
    
    // 관련 ECO Oid를 가져온다.
    public String getEcoPboOid(EPMDocument epmDoc) throws Exception;
    
    // ECO PBO 객체를 가져온다.
    public WTChangeOrder2 getEcoPbo(EPMDocument epmDoc) throws Exception;
    
    // PBO 객체를 가져온다.
    public Persistable getPbo(EPMDocument epmDoc) throws Exception;
    
    // PBO 객체를 가져온다.
    public Persistable getSavedPbo(EPMDocument epmDoc) throws Exception;
    
    // PBO 객체를 가져온다.
    public KETEpmApprovalHis getApprovalHis(EPMDocument epmDoc) throws Exception;
    
    // 관련된 결재상태에 무관한 ECO 리스트를 가져온다.
    public Persistable[] getRelatedEcoList(String epmDocOid);
    
    // 도면과 결재 테이블을 가져오기
    public QueryResult getEpmApprovalLink(EPMDocument epmDoc, KETEpmApprovalHis approvalHis) throws WTException;
    
    // ECO 결재완료 시점에 결재정보를 넣어준다.
    public void updateEpmApprovalInfoWhenEcoApproved(KETProdChangeOrder changeOrder2) throws Exception;
    public void updateEpmApprovalInfoWhenEcoApproved(KETMoldChangeOrder changeOrder2) throws Exception;
    public void updateEpmApprovalInfoWhenEcoApproved(EPMDocument epmDoc) throws Exception;
    
    
}
