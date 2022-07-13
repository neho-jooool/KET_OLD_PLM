package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceHistory"),
   roleA=@GeneratedRole(
      name="task",
      type=e3ps.project.E3PSTask.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceHistory",
      type=ext.ket.cost.entity.CostInterfaceHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class TaskTheCostInterfaceHistory extends _TaskTheCostInterfaceHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static TaskTheCostInterfaceHistory newTaskTheCostInterfaceHistory( e3ps.project.E3PSTask task, ext.ket.cost.entity.CostInterfaceHistory theCostInterfaceHistory ) throws wt.util.WTException {
      TaskTheCostInterfaceHistory TaskTheCostInterfaceHistory_instance = new TaskTheCostInterfaceHistory();
      TaskTheCostInterfaceHistory_instance.initialize(task, theCostInterfaceHistory);
      return TaskTheCostInterfaceHistory_instance;
   }
}
