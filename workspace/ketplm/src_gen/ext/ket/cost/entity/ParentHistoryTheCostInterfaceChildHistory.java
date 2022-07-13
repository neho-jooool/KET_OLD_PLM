package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceChildHistory"),
   roleA=@GeneratedRole(
      name="parentHistory",
      type=ext.ket.cost.entity.CostInterfaceHistory.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceChildHistory",
      type=ext.ket.cost.entity.CostInterfaceChildHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class ParentHistoryTheCostInterfaceChildHistory extends _ParentHistoryTheCostInterfaceChildHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static ParentHistoryTheCostInterfaceChildHistory newParentHistoryTheCostInterfaceChildHistory( ext.ket.cost.entity.CostInterfaceHistory parentHistory, ext.ket.cost.entity.CostInterfaceChildHistory theCostInterfaceChildHistory ) throws wt.util.WTException {
      ParentHistoryTheCostInterfaceChildHistory ParentHistoryTheCostInterfaceChildHistory_instance = new ParentHistoryTheCostInterfaceChildHistory();
      ParentHistoryTheCostInterfaceChildHistory_instance.initialize(parentHistory, theCostInterfaceChildHistory);
      return ParentHistoryTheCostInterfaceChildHistory_instance;
   }
}
