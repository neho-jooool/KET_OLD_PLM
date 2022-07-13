package ext.ket.edm.cad2bom.service;

import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import wt.part.WTPart;

@RemoteInterface
public interface Cad2BomService {
    
    // CAD2BOM 최상위 정보 Validation
    public String checkRootValid(String modelOid) throws Exception;
    
    // CAD2BOM UI Data
    public List<Map<String, Object>> makeCadStructure(String modelOid) throws Exception;
    
}
