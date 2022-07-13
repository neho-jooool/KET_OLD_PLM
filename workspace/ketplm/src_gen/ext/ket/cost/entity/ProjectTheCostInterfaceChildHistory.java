package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceChildHistory"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProjectMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceChildHistory",
      type=ext.ket.cost.entity.CostInterfaceChildHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheCostInterfaceChildHistory extends _ProjectTheCostInterfaceChildHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheCostInterfaceChildHistory newProjectTheCostInterfaceChildHistory( e3ps.project.E3PSProjectMaster project, ext.ket.cost.entity.CostInterfaceChildHistory theCostInterfaceChildHistory ) throws wt.util.WTException {
      ProjectTheCostInterfaceChildHistory ProjectTheCostInterfaceChildHistory_instance = new ProjectTheCostInterfaceChildHistory();
      ProjectTheCostInterfaceChildHistory_instance.initialize(project, theCostInterfaceChildHistory);
      return ProjectTheCostInterfaceChildHistory_instance;
   }
}
