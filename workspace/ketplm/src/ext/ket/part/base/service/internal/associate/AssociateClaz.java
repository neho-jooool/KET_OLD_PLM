package ext.ket.part.base.service.internal.associate;

import wt.part.WTPart;
import ext.ket.part.entity.KETPartClassification;

public interface AssociateClaz extends Associatable {

    public void associateCreate(WTPart part, KETPartClassification partClassification ) throws Exception;
    
    public void associateUpdate(WTPart part, KETPartClassification partClassification ) throws Exception;
    
}
