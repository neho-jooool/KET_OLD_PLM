package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="process",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectProcessLink extends _ProjectProcessLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectProcessLink newProjectProcessLink( e3ps.common.code.NumberCode process, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectProcessLink ProjectProcessLink_instance = new ProjectProcessLink();
      ProjectProcessLink_instance.initialize(process, project);
      return ProjectProcessLink_instance;
   }
}
