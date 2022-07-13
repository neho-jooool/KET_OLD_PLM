package e3ps.cost.service;

import java.util.List;

import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import e3ps.cost.entity.PartBomInfoDTO;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartClassificationDTO;
import ext.ket.part.util.PartSpecEnum;

@RemoteInterface
public interface KetCostInfoService {// extends CommonServiceInterface<PartClassificationDTO, KETPartClassification> {    
    // public KETPartClassification findSameClassification(PartClassificationDTO clazDTO) throws Exception;

    // public KETPartClassification insertWithParent(PartClassificationDTO clazDTO) throws Exception;
    //
    // public KETPartClassification insertRoot(PartClassificationDTO clazDTO) throws Exception;

    // public KETPartClassification modify(PartClassificationDTO clazDTO) throws Exception;
    //
    // public KETPartClassification delete(PartClassificationDTO clazDTO) throws Exception;

    // public KETPartClassification insertMigration(KETPartClassification child, KETPartClassTreeLink link) throws Exception;
    
    public List<PartBomInfoDTO> searchPlmFullList(String partOid,String GroupNo) throws Exception;

}
