package ext.ket.project.atft.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import ext.ket.project.atft.entity.KETATFTMainSheet;
import ext.ket.project.atft.entity.KETAtftDTO;
import ext.ket.shared.service.CommonServiceInterface;

/**
 * 
 * @클래스명 : AtftService
 * @작성자 : 황정태
 * @작성일 : 2016. 12. 28.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@RemoteInterface
public interface AtftService extends CommonServiceInterface<KETAtftDTO, KETATFTMainSheet> {

    public void createAtft(KETAtftDTO paramObject) throws Exception;
    
    public void modifyAtft(KETAtftDTO paramObject) throws Exception;
    
    public void completeAtft(KETAtftDTO paramObject) throws Exception;
    
    public void reviseAtft(KETAtftDTO paramObject) throws Exception;
    
    public List<Map<String, Object>> lastestAtftList(String ProjectNo) throws Exception;
    
    public List<Map<String, String>> autoCheckData(String projectOid, String sheetdivision) throws Exception;
    
    public ArrayList<HashMap<String, Object>> getCostPriceBySap(String partNo, String plant) throws Exception;
}
