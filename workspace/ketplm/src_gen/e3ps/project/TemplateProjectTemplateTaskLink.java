package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TemplateTask"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.TemplateProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="task",
      type=e3ps.project.TemplateTask.class
   )
)

public class TemplateProjectTemplateTaskLink extends _TemplateProjectTemplateTaskLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TemplateProjectTemplateTaskLink newTemplateProjectTemplateTaskLink( e3ps.project.TemplateProject project, e3ps.project.TemplateTask task ) throws wt.util.WTException {
      TemplateProjectTemplateTaskLink TemplateProjectTemplateTaskLink_instance = new TemplateProjectTemplateTaskLink();
      TemplateProjectTemplateTaskLink_instance.initialize(project, task);
      return TemplateProjectTemplateTaskLink_instance;
   }
}
