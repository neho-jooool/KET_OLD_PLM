package ext.ket.edm.service;

import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import e3ps.ecm.entity.KETProdChangeOrder;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETDrawingDownHis;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface DrawingDistService extends CommonServiceInterface<KETDrawingDistReqDTO, KETDrawingDistRequest> {
    public KETDrawingDistReqDTO detailView(KETDrawingDistReqDTO paramObject) throws Exception;

    public List<Map<String, Object>> getDistReqEpmDocList(KETDrawingDistReqDTO paramObject) throws Exception;

    public List<Map<String, Object>> getDistReqWtDocList(KETDrawingDistReqDTO paramObject) throws Exception;

    public KETDrawingDistRequest ReApproved(KETDrawingDistReqDTO paramObject) throws Exception;

    public KETDrawingDistReqDTO getDistDeptList(KETDrawingDistReqDTO paramObject) throws Exception;

    public KETDrawingDownHis saveReason(KETDrawingDistReqDTO paramObject) throws Exception;

    public String drawingDistTotalDown(String oid) throws Exception;

    public List<Map<String, Object>> getDrawingDownHisry(KETDrawingDistReqDTO paramObject) throws Exception;

    public List<Map<String, Object>> sendHpList(KETDrawingDistReqDTO paramObject) throws Exception;

    public void backgroundSave(KETProdChangeOrder edo) throws Exception;

    public void test() throws Exception;

    public void reStamping(String reqOid) throws Exception;

    public boolean sendHp(KETDrawingDistReqDTO paramObject) throws Exception;

    public boolean sendPartAttr() throws Exception;

    public List<Map<String, Object>> getPlmHpIfList(KETDrawingDistReqDTO paramObject) throws Exception;

    public List<Map<String, Object>> getPlmHpIfListByOid(String oid, String partOid) throws Exception;

    public List<Map<String, Object>> sendPartAttrList() throws Exception;

    public boolean saveHpIfFile(KETDrawingDistReqDTO paramObject) throws Exception;

}
