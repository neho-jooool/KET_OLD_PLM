package ext.ket.project.bom.service;

import java.util.Map;

import wt.method.RemoteInterface;

@RemoteInterface
public interface BomCheckService {
    public Map<String, Object> findPagingList(Map<String, Object> reqMap) throws Exception;

    public Map<String, String> getMoldTaskOid(String pjtNo) throws Exception;
}
