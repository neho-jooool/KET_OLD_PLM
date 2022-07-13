package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectIssue"),
   roleA=@GeneratedRole(
      name="task",
      type=e3ps.project.E3PSTask.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="issue",
      type=e3ps.project.ProjectIssue.class
   )
)

public class TaskIssueLink extends _TaskIssueLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TaskIssueLink newTaskIssueLink( e3ps.project.E3PSTask task, e3ps.project.ProjectIssue issue ) throws wt.util.WTException {
      TaskIssueLink TaskIssueLink_instance = new TaskIssueLink();
      TaskIssueLink_instance.initialize(task, issue);
      return TaskIssueLink_instance;
   }
}
