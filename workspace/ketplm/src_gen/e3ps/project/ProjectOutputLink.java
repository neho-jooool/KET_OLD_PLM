package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectOutput"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.TemplateProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="output",
      type=e3ps.project.ProjectOutput.class
   )
)

public class ProjectOutputLink extends _ProjectOutputLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectOutputLink newProjectOutputLink( e3ps.project.TemplateProject project, e3ps.project.ProjectOutput output ) throws wt.util.WTException {
      ProjectOutputLink ProjectOutputLink_instance = new ProjectOutputLink();
      ProjectOutputLink_instance.initialize(project, output);
      return ProjectOutputLink_instance;
   }
}
