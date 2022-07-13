package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="developentType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectDevelopentTypeLink extends _ProjectDevelopentTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectDevelopentTypeLink newProjectDevelopentTypeLink( e3ps.common.code.NumberCode developentType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectDevelopentTypeLink ProjectDevelopentTypeLink_instance = new ProjectDevelopentTypeLink();
      ProjectDevelopentTypeLink_instance.initialize(developentType, project);
      return ProjectDevelopentTypeLink_instance;
   }
}
