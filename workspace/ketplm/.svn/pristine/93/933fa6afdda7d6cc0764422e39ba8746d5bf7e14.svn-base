package ext.ket.project.purchase.service;

import java.util.Map;

import wt.part.WTPart;
import e3ps.project.MoldItemInfo;

public interface KETPurchaseService {
    public Map<String, Object> findPagingList(Map<String, Object> reqMap) throws Exception;

    public Map<String, Object> savePurchasePart(Map<String, Object> reqMap) throws Exception;

    public Map<String, Object> addPartList(Map<String, Object> reqMap) throws Exception;

    public void deletePurchasePart(Map<String, Object> reqMap) throws Exception;

    public void outSourcingSync(String partNo, WTPart part, MoldItemInfo miInfo) throws Exception;

    public Map<String, Object> findDocList(Map<String, Object> reqMap) throws Exception;
}
