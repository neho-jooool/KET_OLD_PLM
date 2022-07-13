package ext.ket.invest.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETInvestTask"),
   roleA=@GeneratedRole(
      name="worker",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETInvestTask",
      type=ext.ket.invest.entity.KETInvestTask.class,
      cardinality=Cardinality.ONE
   )
)

public class WorkerTheKETInvestTask extends _WorkerTheKETInvestTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static WorkerTheKETInvestTask newWorkerTheKETInvestTask( wt.org.WTUser worker, ext.ket.invest.entity.KETInvestTask theKETInvestTask ) throws wt.util.WTException {
      WorkerTheKETInvestTask WorkerTheKETInvestTask_instance = new WorkerTheKETInvestTask();
      WorkerTheKETInvestTask_instance.initialize(worker, theKETInvestTask);
      return WorkerTheKETInvestTask_instance;
   }
}
