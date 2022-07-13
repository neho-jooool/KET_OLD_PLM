package ext.ket.material.service;

import java.util.List;
import java.util.Map;

import e3ps.common.web.PageControl;
import ext.ket.material.entity.KETPartMaterialDTO;
import wt.method.RemoteInterface;

@RemoteInterface
public interface MaterialService {
    public PageControl findPagingList(KETPartMaterialDTO mater) throws Exception;
    
    public Map<String, Object> saveMaterial(Map<String, Object> reqMap) throws Exception;
    
    public void syncMaterialForPartLink() throws Exception;
    
    public KETPartMaterialDTO getMaterialDtoByOid(KETPartMaterialDTO mater) throws Exception;
    
    public List<Map<String, Object>> getMaterialPartMapList(Map<String, Object> reqMap) throws Exception;
}