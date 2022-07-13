package ext.ket.issue.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETIssueTask"),
   roleA=@GeneratedRole(
      name="worker",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETIssueTask",
      type=ext.ket.issue.entity.KETIssueTask.class,
      cardinality=Cardinality.ONE
   )
)

public class WorkerTheKETIssueTask extends _WorkerTheKETIssueTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static WorkerTheKETIssueTask newWorkerTheKETIssueTask( wt.org.WTUser worker, ext.ket.issue.entity.KETIssueTask theKETIssueTask ) throws wt.util.WTException {
      WorkerTheKETIssueTask WorkerTheKETIssueTask_instance = new WorkerTheKETIssueTask();
      WorkerTheKETIssueTask_instance.initialize(worker, theKETIssueTask);
      return WorkerTheKETIssueTask_instance;
   }
}
