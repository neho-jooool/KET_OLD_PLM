package ext.ket.issue.entity;

import java.util.List;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.cost.util.StringUtil;
import ext.ket.issue.util.IssueUtil;
import ext.ket.shared.dto.BaseDTO;
import wt.fc.Persistable;
import wt.org.WTUser;

public class KETIssueToDoDTO extends BaseDTO{

    private static final long serialVersionUID = 1L;
    
    private String oid;
    private String workName;
    private String title;
    private String reqUserOid;
    private String reqUserName;
    private String receiveDate;
    private String requestDate;
    
    public KETIssueToDoDTO() {
        
    }
    
    public KETIssueToDoDTO(Persistable issue) throws Exception {
        
        this.oid = CommonUtil.getOIDString(issue);
        
        KETGeneralissueLink link = null;
        if(issue instanceof KETIssueMaster) {
            
//          1. 주담당자
//          요청자 : 요청서의 주 담당자
//          수신일 : 세부항목중 최종완료일자
//          완료 요청일 : 요청서의 완료 요청일 
            
            KETIssueMaster im = (KETIssueMaster) issue;
            WTUser manager = im.getManager();
            
            if(IssueUtil.INWORK.equals(im.getIssueState())) {
                this.workName = "요청서 작성";
                this.receiveDate = DateUtil.getDateString(im.getCreateTimestamp(), "d");
                
            }else {
                
                this.workName = "고객 요구 대응 완료";
                
                List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(im);
                
                long tempTime = 0;
                for(KETIssueTask it : itList) {
                    if(IssueUtil.COMPLETE.equals(it.getIssueState())) {
                        
                        long compTime = it.getCompleteDate().getTime();
                        
                        if(compTime > tempTime) {
                            this.receiveDate = DateUtil.getDateString(it.getCompleteDate(), "d");
                            tempTime = compTime;
                        }
                    }
                }
            }
            
            this.title = im.getReqName();
            this.requestDate = DateUtil.getDateString(im.getRequestDate(), "d");
            
            if(manager != null) {
                this.reqUserOid = CommonUtil.getOIDString(manager);
                this.reqUserName = manager.getFullName();
            }
            
            link = IssueUtil.manager.getPBOLink(im);
            
        }else{
//            2. 세부항목 담당자
//            요청자 : 세부항목 진행요청자
//            수신일 : 세부항목 진행요청일
//            완료 요청일 : 세부항목 완료 요청일 
            
            KETIssueTask it = (KETIssueTask) issue;
            KETIssueMaster im = it.getIssueMaster();
            WTUser manager = im.getManager();
            
            
            this.workName = "진행현황 등록";
            this.title = it.getSubject();
            this.receiveDate = DateUtil.getDateString(it.getProgressDate(), "d");
            this.requestDate = DateUtil.getDateString(it.getRequestDate(), "d");
            
            if(manager != null) {
                this.reqUserOid = CommonUtil.getOIDString(manager);
                this.reqUserName = manager.getFullName();
            }
            
            link = IssueUtil.manager.getPBOLink(im);
        }
        
        if(link != null) this.title += " [" + StringUtil.checkNull(link.getPboName()) + "]";
    }

    /**
     * @return the oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * @param oid the oid to set
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * @return the workName
     */
    public String getWorkName() {
        return workName;
    }

    /**
     * @param workName the workName to set
     */
    public void setWorkName(String workName) {
        this.workName = workName;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the reqUserOid
     */
    public String getReqUserOid() {
        return reqUserOid;
    }

    /**
     * @param reqUserOid the reqUserOid to set
     */
    public void setReqUserOid(String reqUserOid) {
        this.reqUserOid = reqUserOid;
    }

    /**
     * @return the reqUserName
     */
    public String getReqUserName() {
        return reqUserName;
    }

    /**
     * @param reqUserName the reqUserName to set
     */
    public void setReqUserName(String reqUserName) {
        this.reqUserName = reqUserName;
    }

    /**
     * @return the receiveDate
     */
    public String getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate the receiveDate to set
     */
    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * @return the requestDate
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
    
}
