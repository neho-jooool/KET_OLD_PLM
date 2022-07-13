package ext.ket.edm.approval.internal;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Vector;

import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.util.ComparatorUtil;

public class EpmApprovalHistoryHandler {
    
    private Timestamp requestDate;
    private Timestamp approvalDate;
    private String approverId;
    private String approverName;
    private String approverDelegate;
    private String approvalMastOid;

    public void getApprovalHistory(Persistable targetObj) throws WTException {
	
	ReferenceFactory rf = new ReferenceFactory();
	    
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	if (master == null) return;
	
	approvalMastOid = CommonUtil.getOIDString(master);
	    
	Vector vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	if(vec == null) return;
	
	Collections.sort(vec, ComparatorUtil.APPROVALHISTORYSORT);
	    
	String activityName = "";
	Timestamp completeDate = null;
	for (int i = vec.size()-1; i >= 0 ; i--) {
	    
	    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
	    activityName = StringUtil.checkNull(history.getActivityName());
	    if (history.isLast() && activityName.equals("검토")) {
		activityName = "승인";
	    }
	    
//	    People people = PeopleHelper.manager.getPeople(history.getOwner().getName());
	    
	    if (history.getCompletedDate() != null){
		completeDate = history.getCompletedDate();
	    }
	    
	    if(activityName.equals("승인")){ // && history.isLast() == true
		approvalDate =  completeDate;
		
		approverName = history.getOwner().getFullName();
		approverId = history.getOwner().getName();
		if (history.getDelegate() != null){
		    approverDelegate = history.getDelegate();
		}
		
	    }else if(activityName.equals("요청")){
        	requestDate =  completeDate;
            }
	}
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public Timestamp getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Timestamp approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverDelegate() {
        return approverDelegate;
    }

    public void setApproverDelegate(String approverDelegate) {
        this.approverDelegate = approverDelegate;
    }

    public String getApprovalMastOid() {
        return approvalMastOid;
    }

    public void setApprovalMastOid(String approvalMastOid) {
        this.approvalMastOid = approvalMastOid;
    }
    
    

}
