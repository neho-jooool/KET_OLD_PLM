package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="rank",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectLankLink extends _ProjectLankLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectLankLink newProjectLankLink( e3ps.common.code.NumberCode rank, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectLankLink ProjectLankLink_instance = new ProjectLankLink();
      ProjectLankLink_instance.initialize(rank, project);
      return ProjectLankLink_instance;
   }
}
