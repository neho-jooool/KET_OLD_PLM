package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="assemblyPlace",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectAssemblyPlaceLink extends _ProjectAssemblyPlaceLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectAssemblyPlaceLink newProjectAssemblyPlaceLink( e3ps.common.code.NumberCode assemblyPlace, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectAssemblyPlaceLink ProjectAssemblyPlaceLink_instance = new ProjectAssemblyPlaceLink();
      ProjectAssemblyPlaceLink_instance.initialize(assemblyPlace, project);
      return ProjectAssemblyPlaceLink_instance;
   }
}
