package ext.ket.arm.service;

import org.springframework.ui.Model;

import wt.method.RemoteInterface;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import ext.ket.arm.entity.KETAnalysisRequestDTO;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface KETAnalysisRequestService extends CommonServiceInterface<KETAnalysisRequestDTO, KETAnalysisRequestMaster> {

    public String getProjectOidLink(String analysisOid) throws Exception;

    public String getCustomerDeptsNameLink(String analysisOid) throws Exception;

    public String getCustomerDeptsCodeLink(String analysisOid) throws Exception;

    public String getAnalysisDivisionNameLink(String analysisOid) throws Exception;

    public String getAnalysisDivisionCodeLink(String analysisOid) throws Exception;

    public KETAnalysisRequestDTO KETAnalysisRequestView(KETAnalysisRequestDTO paramObject, Model model) throws Exception;

    public String getAnalysisMasterClassName(String analysisOid) throws Exception;

    public KETWfmApprovalMaster approvalMastermodify(String analysisOid, String appUserOid) throws Exception;

}