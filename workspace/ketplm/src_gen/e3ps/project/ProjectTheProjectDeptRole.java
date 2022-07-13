package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectDeptRole"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.TemplateProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theProjectDeptRole",
      type=e3ps.project.ProjectDeptRole.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheProjectDeptRole extends _ProjectTheProjectDeptRole implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheProjectDeptRole newProjectTheProjectDeptRole( e3ps.project.TemplateProject project, e3ps.project.ProjectDeptRole theProjectDeptRole ) throws wt.util.WTException {
      ProjectTheProjectDeptRole ProjectTheProjectDeptRole_instance = new ProjectTheProjectDeptRole();
      ProjectTheProjectDeptRole_instance.initialize(project, theProjectDeptRole);
      return ProjectTheProjectDeptRole_instance;
   }
}
