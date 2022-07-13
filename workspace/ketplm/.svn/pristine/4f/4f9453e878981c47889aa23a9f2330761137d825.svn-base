package ext.ket.part.classify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import wt.method.RemoteInterface;

/**
 * 
 * @클래스명 : ActualWeightServise
 * @작성자 : KKW
 * @작성일 : 2014. 9. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@RemoteInterface
public interface ActualWeightServise {
    public List<HashMap<String, String>> getPartList(String part_oid, Locale locale) throws Exception;

    public boolean actualWeightBomSave(String ecoOid, String part_no[], String part_sp_net_weight[], String part_sp_total_weight[], String part_oid[], String part_sp_scrab_weight[]) throws Exception;
}
