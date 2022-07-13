package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceHistory"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProjectMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceHistory",
      type=ext.ket.cost.entity.CostInterfaceHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheCostInterfaceHistory extends _ProjectTheCostInterfaceHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheCostInterfaceHistory newProjectTheCostInterfaceHistory( e3ps.project.E3PSProjectMaster project, ext.ket.cost.entity.CostInterfaceHistory theCostInterfaceHistory ) throws wt.util.WTException {
      ProjectTheCostInterfaceHistory ProjectTheCostInterfaceHistory_instance = new ProjectTheCostInterfaceHistory();
      ProjectTheCostInterfaceHistory_instance.initialize(project, theCostInterfaceHistory);
      return ProjectTheCostInterfaceHistory_instance;
   }
}
