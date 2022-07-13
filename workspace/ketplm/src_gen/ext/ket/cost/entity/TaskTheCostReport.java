package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostReport"),
   roleA=@GeneratedRole(
      name="task",
      type=e3ps.project.E3PSTask.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostReport",
      type=ext.ket.cost.entity.CostReport.class,
      cardinality=Cardinality.ONE
   )
)

public class TaskTheCostReport extends _TaskTheCostReport implements Externalizable {
   static final long serialVersionUID = 1;
   public static TaskTheCostReport newTaskTheCostReport( e3ps.project.E3PSTask task, ext.ket.cost.entity.CostReport theCostReport ) throws wt.util.WTException {
      TaskTheCostReport TaskTheCostReport_instance = new TaskTheCostReport();
      TaskTheCostReport_instance.initialize(task, theCostReport);
      return TaskTheCostReport_instance;
   }
}
