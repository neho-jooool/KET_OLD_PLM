package ext.ket.sample.service;

import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import e3ps.common.web.PageControl;
import ext.ket.sample.entity.Sample;
import ext.ket.sample.entity.SampleDTO;
import ext.ket.sample.entity.SampleRequestDTO;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface SampleService extends CommonServiceInterface<SampleDTO, Sample> {
    public SampleRequestDTO sampleRequestSave(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO sampleRequestUpdate(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO samplePartListUpdate(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO sampleDelete(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO reRequest(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO sampleComplete(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO samplePartWorkComplete(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO samplePartReception(SampleRequestDTO paramObject) throws Exception;

    public SampleRequestDTO sampleRequestView(SampleRequestDTO paramObject) throws Exception;

    public List<Map<String, Object>> samplePartListView(SampleRequestDTO paramObject) throws Exception;

    public List<Map<String, Object>> reRequsetCreateSamplePartListView(SampleRequestDTO paramObject) throws Exception;

    public List<Map<String, Object>> samplePartReceptionListView(SampleRequestDTO paramObject) throws Exception;

    public List<Map<String, Object>> samplePartReceptionListOnlyView(SampleRequestDTO paramObject) throws Exception;

    public PageControl sampleRequestFindPaging(SampleRequestDTO paramObject) throws Exception;

    public List<Map<String, String>> projectInfo(String partOid) throws Exception;

    public Map<String, String> projectPMUserInfo(String pjtOid) throws Exception;

}
