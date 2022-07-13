package ext.ket.sales.service;

import java.util.List;
import java.util.Map;

import wt.fc.QueryResult;
import wt.method.RemoteInterface;
import wt.query.QuerySpec;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.Department;
import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesCSMeetingManage;
import ext.ket.sales.entity.KETSalesProjectDTO;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface SalesService extends CommonServiceInterface<KETSalesProjectDTO, KETSalesProject> {

    public String saveSales(KETSalesProjectDTO paramObject) throws Exception;
    
    public KETSalesProjectDTO viewSalesProjectForm(KETSalesProjectDTO paramObject) throws Exception;
    
    public KETSalesProjectDTO salesProjectRevise(KETSalesProjectDTO paramObject) throws Exception;
    
    public KETSalesProject getLatestSalesProject(KETSalesProject paramObject) throws Exception;
    
    public void copyProjectChild(KETSalesProject source, KETSalesProject target) throws Exception;
    
    public QueryResult getCSlist() throws Exception;
    
    public String CSDegreeCreate(KETSalesProjectDTO paramObject) throws Exception;
    
    public PageControl findPagingCSList(KETSalesProjectDTO paramObject) throws Exception;
    
    public QuerySpec getCSManagelistQuery(KETSalesProjectDTO paramObject) throws Exception;
    
    public void csMeetingClose(KETSalesProjectDTO paramObject) throws Exception;
    
    public List<Map<String, Object>> getCsprojectSortedList(boolean isFile) throws Exception;
    
    public QueryResult getCsprojectSortedResult(String classKey) throws Exception;
    
    public void CSMeetingChasuCreate(String arroid, String gubun) throws Exception;
    
    public String getCSprojectSortInfo(Department targetDept) throws Exception;
    
    public KETSalesCSMeetingApproval getCSApprovalTarget(KETSalesCSMeetingManage manage, String DeptSortNo) throws Exception;
    
    public boolean getProjectViewAuthInfo(KETSalesProject pjt) throws Exception;
    
    public void lastProjectDelete(String arroid) throws Exception;
    
    public List<Map<String, String>> csMeetingFileVaultUpload(KETSalesProjectDTO paramObject) throws Exception;
    
    public List<Map<String, String>> salesPresentList() throws Exception;

}