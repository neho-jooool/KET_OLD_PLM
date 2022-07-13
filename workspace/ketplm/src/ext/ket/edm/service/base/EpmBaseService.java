package ext.ket.edm.service.base;

import java.util.Map;

import wt.epm.EPMDocument;
import wt.method.RemoteInterface;

@RemoteInterface
public interface EpmBaseService {
    // ECO Validation : 개정 버튼 누르면, 연계 부품이 먼저 개정되지 않으면 Validation Message 전달
    public String validCheckoutInfoEpm(EPMDocument epmDoc) throws Exception;

    public void updateIbaCadApptype(EPMDocument epmDoc) throws Exception;

    public Map<String, Object> uploadBatchMold(Map<String, Object> reqMap) throws Exception;
}