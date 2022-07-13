package ext.ket.invest.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETInvestDateHistory"),
   roleA=@GeneratedRole(
      name="worker",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETInvestDateHistory",
      type=ext.ket.invest.entity.KETInvestDateHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class WorkerTheKETInvestDateHistory extends _WorkerTheKETInvestDateHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static WorkerTheKETInvestDateHistory newWorkerTheKETInvestDateHistory( wt.org.WTUser worker, ext.ket.invest.entity.KETInvestDateHistory theKETInvestDateHistory ) throws wt.util.WTException {
      WorkerTheKETInvestDateHistory WorkerTheKETInvestDateHistory_instance = new WorkerTheKETInvestDateHistory();
      WorkerTheKETInvestDateHistory_instance.initialize(worker, theKETInvestDateHistory);
      return WorkerTheKETInvestDateHistory_instance;
   }
}
