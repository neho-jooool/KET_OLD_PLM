package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostReport"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProjectMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostReport",
      type=ext.ket.cost.entity.CostReport.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheCostReport extends _ProjectTheCostReport implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheCostReport newProjectTheCostReport( e3ps.project.E3PSProjectMaster project, ext.ket.cost.entity.CostReport theCostReport ) throws wt.util.WTException {
      ProjectTheCostReport ProjectTheCostReport_instance = new ProjectTheCostReport();
      ProjectTheCostReport_instance.initialize(project, theCostReport);
      return ProjectTheCostReport_instance;
   }
}
