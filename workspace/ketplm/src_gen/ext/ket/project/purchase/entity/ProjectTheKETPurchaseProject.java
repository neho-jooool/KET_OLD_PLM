package ext.ket.project.purchase.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPurchaseProject"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProjectMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETPurchaseProject",
      type=ext.ket.project.purchase.entity.KETPurchaseProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheKETPurchaseProject extends _ProjectTheKETPurchaseProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheKETPurchaseProject newProjectTheKETPurchaseProject( e3ps.project.E3PSProjectMaster project, ext.ket.project.purchase.entity.KETPurchaseProject theKETPurchaseProject ) throws wt.util.WTException {
      ProjectTheKETPurchaseProject ProjectTheKETPurchaseProject_instance = new ProjectTheKETPurchaseProject();
      ProjectTheKETPurchaseProject_instance.initialize(project, theKETPurchaseProject);
      return ProjectTheKETPurchaseProject_instance;
   }
}
