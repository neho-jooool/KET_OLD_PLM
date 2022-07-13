package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TemplateTask"),
   roleA=@GeneratedRole(
      name="taskWork",
      type=e3ps.project.PlanTaskWork.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateTask",
      type=e3ps.project.TemplateTask.class,
      cardinality=Cardinality.ONE
   )
)

public class TaskWorkTheTemplateTask extends _TaskWorkTheTemplateTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static TaskWorkTheTemplateTask newTaskWorkTheTemplateTask( e3ps.project.PlanTaskWork taskWork, e3ps.project.TemplateTask theTemplateTask ) throws wt.util.WTException {
      TaskWorkTheTemplateTask TaskWorkTheTemplateTask_instance = new TaskWorkTheTemplateTask();
      TaskWorkTheTemplateTask_instance.initialize(taskWork, theTemplateTask);
      return TaskWorkTheTemplateTask_instance;
   }
}
