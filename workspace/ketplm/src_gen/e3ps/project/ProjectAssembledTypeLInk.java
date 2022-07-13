package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="assembledType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectAssembledTypeLInk extends _ProjectAssembledTypeLInk implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectAssembledTypeLInk newProjectAssembledTypeLInk( e3ps.common.code.NumberCode assembledType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectAssembledTypeLInk ProjectAssembledTypeLInk_instance = new ProjectAssembledTypeLInk();
      ProjectAssembledTypeLInk_instance.initialize(assembledType, project);
      return ProjectAssembledTypeLInk_instance;
   }
}
