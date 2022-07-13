package ext.ket.part.base.service.internal.associate;

import wt.part.WTPart;
import e3ps.project.ProjectMaster;

public interface AssociateProject extends Associatable {

    public void associateCreate(WTPart part, ProjectMaster projectMaster ) throws Exception;
    
    public void associateUpdate(WTPart part, ProjectMaster projectMaster ) throws Exception;
    
}
