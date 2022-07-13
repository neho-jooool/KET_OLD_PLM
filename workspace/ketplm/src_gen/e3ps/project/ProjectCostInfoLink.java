package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInfo"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.ProductProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="costInfo",
      type=e3ps.project.CostInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectCostInfoLink extends _ProjectCostInfoLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectCostInfoLink newProjectCostInfoLink( e3ps.project.ProductProject project, e3ps.project.CostInfo costInfo ) throws wt.util.WTException {
      ProjectCostInfoLink ProjectCostInfoLink_instance = new ProjectCostInfoLink();
      ProjectCostInfoLink_instance.initialize(project, costInfo);
      return ProjectCostInfoLink_instance;
   }
}
