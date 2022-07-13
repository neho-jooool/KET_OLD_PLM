package ext.ket.arm.service;

import wt.method.RemoteInterface;
import ext.ket.arm.entity.KETAnalysisInfoDTO;
import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface KETAnalysisInfoService extends CommonServiceInterface<KETAnalysisInfoDTO, KETAnalysisRequestInfo> {

    public String getAnalysisInfoOid(String analysisOid) throws Exception;

    public String getAnalysisInfoState(String analysisOid) throws Exception;

    public String getAnalysisInfoStateName(String analysisOid) throws Exception;

    public String getAnalysisInfoTitle(long analysisInfoOid) throws Exception;

    public String getAnalysisInfoEndDate(String analysisOid) throws Exception;

    public String getAnalysisMasterOid(String analysisInfoOid) throws Exception;

    public String getKETProjectDocumentOid(String analysisOid) throws Exception;

}