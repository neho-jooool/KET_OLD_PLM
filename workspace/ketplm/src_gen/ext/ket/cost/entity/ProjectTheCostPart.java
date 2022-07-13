package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPart"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProjectMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostPart",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheCostPart extends _ProjectTheCostPart implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheCostPart newProjectTheCostPart( e3ps.project.E3PSProjectMaster project, ext.ket.cost.entity.CostPart theCostPart ) throws wt.util.WTException {
      ProjectTheCostPart ProjectTheCostPart_instance = new ProjectTheCostPart();
      ProjectTheCostPart_instance.initialize(project, theCostPart);
      return ProjectTheCostPart_instance;
   }
}
