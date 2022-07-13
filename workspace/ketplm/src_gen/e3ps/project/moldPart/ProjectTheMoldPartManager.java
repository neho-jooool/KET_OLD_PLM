package e3ps.project.moldPart;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldPartManager"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.MoldProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldPartManager",
      type=e3ps.project.moldPart.MoldPartManager.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheMoldPartManager extends _ProjectTheMoldPartManager implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheMoldPartManager newProjectTheMoldPartManager( e3ps.project.MoldProject project, e3ps.project.moldPart.MoldPartManager theMoldPartManager ) throws wt.util.WTException {
      ProjectTheMoldPartManager ProjectTheMoldPartManager_instance = new ProjectTheMoldPartManager();
      ProjectTheMoldPartManager_instance.initialize(project, theMoldPartManager);
      return ProjectTheMoldPartManager_instance;
   }
}
