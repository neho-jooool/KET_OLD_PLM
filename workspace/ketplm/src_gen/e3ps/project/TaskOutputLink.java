package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectOutput"),
   roleA=@GeneratedRole(
      name="task",
      type=e3ps.project.TemplateTask.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="output",
      type=e3ps.project.ProjectOutput.class
   )
)

public class TaskOutputLink extends _TaskOutputLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TaskOutputLink newTaskOutputLink( e3ps.project.TemplateTask task, e3ps.project.ProjectOutput output ) throws wt.util.WTException {
      TaskOutputLink TaskOutputLink_instance = new TaskOutputLink();
      TaskOutputLink_instance.initialize(task, output);
      return TaskOutputLink_instance;
   }
}
