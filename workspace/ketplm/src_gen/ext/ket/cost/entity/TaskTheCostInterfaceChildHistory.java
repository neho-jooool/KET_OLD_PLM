package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceChildHistory"),
   roleA=@GeneratedRole(
      name="task",
      type=e3ps.project.E3PSTask.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceChildHistory",
      type=ext.ket.cost.entity.CostInterfaceChildHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class TaskTheCostInterfaceChildHistory extends _TaskTheCostInterfaceChildHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static TaskTheCostInterfaceChildHistory newTaskTheCostInterfaceChildHistory( e3ps.project.E3PSTask task, ext.ket.cost.entity.CostInterfaceChildHistory theCostInterfaceChildHistory ) throws wt.util.WTException {
      TaskTheCostInterfaceChildHistory TaskTheCostInterfaceChildHistory_instance = new TaskTheCostInterfaceChildHistory();
      TaskTheCostInterfaceChildHistory_instance.initialize(task, theCostInterfaceChildHistory);
      return TaskTheCostInterfaceChildHistory_instance;
   }
}
