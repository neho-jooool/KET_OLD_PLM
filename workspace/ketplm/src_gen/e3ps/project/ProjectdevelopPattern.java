package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="developPattern",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectdevelopPattern extends _ProjectdevelopPattern implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectdevelopPattern newProjectdevelopPattern( e3ps.common.code.NumberCode developPattern, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectdevelopPattern ProjectdevelopPattern_instance = new ProjectdevelopPattern();
      ProjectdevelopPattern_instance.initialize(developPattern, project);
      return ProjectdevelopPattern_instance;
   }
}
